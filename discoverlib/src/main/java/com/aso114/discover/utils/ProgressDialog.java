package com.aso114.discover.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aso114.discover.DiscoverLib;
import com.aso114.discover.R;

@SuppressLint("DefaultLocale")
public class ProgressDialog {
    private PopupWindow pop;
    private ProgressBar pb;
    private View locView;
    private TextView tvSpeed;
    private TextView tvProgress;

    public ProgressDialog(View locView, PopupWindow.OnDismissListener dl) {
        this.locView = locView;
        View contentView = LayoutInflater.from(DiscoverLib.getContext()).inflate(R.layout.dialog_pregress, null);
        pb = contentView.findViewById(R.id.pb);
        View btnCancel = contentView.findViewById(R.id.btnCancel);
        tvSpeed = contentView.findViewById(R.id.tvSpeed);
        tvProgress = contentView.findViewById(R.id.tvProgress);
        pop = new PopupWindow(contentView, DiscoverLib.screenWidth(), DiscoverLib.screenHeight());
        pop.setClippingEnabled(false);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(178, 0, 0, 0)));
        pop.setOnDismissListener(dl);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public void updateProgress(int progress, long speed) {
        pb.setProgress(progress);
        tvProgress.setText(String.format("%d%%", progress));
        tvSpeed.setText(String.format("%d byte/s", speed));
    }

    public void showDialog() {
        pop.showAtLocation(locView, Gravity.NO_GRAVITY, 0, 0);
    }

    public void dismissDialog() {
        tvSpeed.setText("");
        tvProgress.setText("");
        pb.setProgress(0);
        pop.dismiss();
    }

}
