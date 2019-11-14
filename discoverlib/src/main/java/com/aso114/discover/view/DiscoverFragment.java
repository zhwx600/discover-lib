package com.aso114.discover.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aso114.discover.DiscoverLib;
import com.aso114.discover.utils.*;
import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

import java.io.File;
import java.util.List;

@SuppressLint("DefaultLocale")
public abstract class DiscoverFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener, PopupWindow.OnDismissListener {

    private static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";

    private View mRootView;
    private RecyclerView rv;
    private DiscoverAdapter mAdapter;
    private WebView web;
    private ProgressDialog pd;
    //下载链接
    private String linkUrl;
    private int position;
    private View defaultTitleView;
    private ViewGroup parentView;
    private ImageView imgDefault;
    private boolean isListItemClick;
    private DiscoverAppModel discoverAppModel;

    public abstract void setAd(ViewGroup container, ImageView imgDefault);

    public abstract View getCustomTitleView(ViewGroup parentView);

    public abstract void initData();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_discover, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        rv = mRootView.findViewById(R.id.rv);
        ViewGroup adContainer = mRootView.findViewById(R.id.adContainer);
        defaultTitleView = mRootView.findViewById(R.id.defaultTitleView);
        parentView = mRootView.findViewById(R.id.parentView);
        imgDefault = mRootView.findViewById(R.id.imgDefault);
        setCustomTitleView(getCustomTitleView(parentView));
        setAd(adContainer, imgDefault);
        pd = new ProgressDialog(adContainer, this);
        setupRv();
        initWebView();
    }

    protected void setAppList(List<DiscoverAppModel> list) {
        mAdapter.setNewData(list);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        web = new WebView(getActivity());
//        web = mRootView.findViewById(R.id.web);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setAppCacheEnabled(true);
        settings.setUserAgentString(AGENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        web.addJavascriptInterface(new InJavaScriptLocalObj(), "myJs");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isListItemClick)
                    web.loadUrl(loadParseJs(mAdapter.getData().get(position).getMarketType()));
                else if (discoverAppModel != null)
                    web.loadUrl(loadParseJs(discoverAppModel.getMarketType()));
            }

        });
        web.setWebChromeClient(new WebChromeClient());
    }

    private String loadParseJs(int market) {
        switch (market) {
            case 1://360
                return "javascript:var a=document.getElementsByClassName('js-downLog')[0];" +
                        "window.myJs.showSource(a.href)";
            case 2://应用宝
                return "javascript:var data= document.getElementsByClassName('det-down-btn')[0];\n" +
                        "window.myJs.showSource(data.getAttribute('data-apkurl'))";
            case 3://华为
                return "javascript:var data= document.getElementsByClassName('mkapp-btn')[0];\n" +
                        "window.myJs.showSource(data.getAttribute('onclick'))";
            case 4://小米
                return "javascript:var data= document.getElementsByClassName('app-info-down')[0].children[0];\n" +
                        "window.myJs.showSource(data.getAttribute('href'))";
            case 5://vivo
                return "javascript:var data= document.getElementsByClassName('button-box')[0].children[0];\n" +
                        "window.myJs.showSource(data.getAttribute('data'))";
        }
        return "";
    }

    /**
     * 自定义标题栏
     */
    private void setCustomTitleView(View customTitleView) {
        if (customTitleView == null)
            return;
        defaultTitleView.setVisibility(View.GONE);
        parentView.addView(customTitleView, 0);
    }

    /**
     * 设置recyclerView
     */
    private void setupRv() {
        mAdapter = new DiscoverAdapter();
        rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    /**
     * app点击事件
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        this.position = position;
        checkPermission();
    }

    public void loadUrl(boolean isList, DiscoverAppModel discoverAppModel) {
        String packageName = discoverAppModel.getPackageName();
        if (AppUtils.getAppInfo(packageName) != null) {
            AppUtils.launchApp(packageName);
            return;
        }
        String url = discoverAppModel.getLinkUrl();
        if (TextUtils.equals(url, linkUrl))//防止多次点击
            return;
        linkUrl = url;
        this.discoverAppModel = discoverAppModel;
        this.isListItemClick = isList;
        web.loadUrl(url);
        DiscoverLib.showToast("正在获取下载链接");
    }

    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    loadUrl(true, mAdapter.getData().get(position));
                } else if (permission.shouldShowRequestPermissionRationale) {
                } else {//不要询问
                    DiscoverLib.showToast("请打开存储权限");
                    PermissionSettingPage.start(getActivity(), false);
                }
            }
        });
    }

    /**
     * 下载安装
     */
    private void download(String url) {
        String fName = getDownloadFileName(url);
        System.out.println("**********" + fName);
        OkGo.<File>get(url).tag(linkUrl).execute(new FileCallback(FileUtils.getAdDownloadFile().getPath(), fName) {
            @Override
            public void onSuccess(Response<File> response) {
                pd.dismissDialog();
                File file = response.body();
                System.out.println("**********" + file.getPath());
                AppUtils.installApp(file);
            }

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                pd.showDialog();
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                System.out.println("**********" + progress.fraction + "  speed " + progress.speed);
                pd.updateProgress((int) (progress.fraction * 100), progress.speed);
            }

        });
    }

    /**
     * 从链接提取下载文件名
     */
    private String getDownloadFileName(String url) {
        int marketType = 0;
        if (isListItemClick)
            marketType = mAdapter.getData().get(position).getMarketType();
        else if (discoverAppModel!=null)
            marketType=discoverAppModel.getMarketType();
        switch (marketType) {
            case 2:
                return ParseUtils.parseFileNameFromYingyongbao(url);
            case 1:
                return ParseUtils.parseFileNameFrom360(url);
        }
        return String.format("%d.apk", System.currentTimeMillis());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pd != null)
            pd.dismissDialog();
        if (web != null)
            web.destroy();
    }

    /**
     * 取消下载
     */
    @Override
    public void onDismiss() {
        OkGo.getInstance().cancelTag(linkUrl);
        linkUrl = "";
        web.clearView();
    }

    /**
     * 获取apk下载地址
     */
    class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("**********" + html);
            int marketType = 0;
            if (isListItemClick)
                marketType = mAdapter.getData().get(position).getMarketType();
            else if (discoverAppModel!=null)
                marketType=discoverAppModel.getMarketType();
            switch (marketType) {
                case 1: {
                    String url = ParseUtils.parse360DownloadUrl(html);
                    if (url != null)
                        download(url);
                }
                break;
                case 2:
                case 5:
                    download(html);
                    break;
                case 3: {
                    String url = ParseUtils.parseHuaweiDownloadUrl(html);
                    if (url != null)
                        download(url);
                }
                break;
                case 4:
                    System.out.println("**************   http://app.mi.com" + html);
                    download("http://app.mi.com" + html);
                    break;
                default:
                    break;
            }
        }
    }

}
