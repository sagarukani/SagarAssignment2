package com.example.sagarassignment2.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

//for displaying recyclerview with circular rotation
public class CustomScrollingLayoutCallback extends WearableLinearLayoutManager.LayoutCallback {
    @Override
    public void onLayoutFinished(View child, RecyclerView parent) {
        final float MAX_ICON_PROGRESS = 0.65f;

        try {
            float centerOffset = ((float) child.getHeight() / 2.0f) / (float) parent.getHeight();
            float yRelativeToCenterOffset = (child.getY() / parent.getHeight()) + centerOffset;


            float progressToCenter = Math.min(Math.abs(0.5f - yRelativeToCenterOffset), MAX_ICON_PROGRESS);

            progressToCenter = (float) (Math.cos(progressToCenter * Math.PI * 0.70f));

            child.setScaleX(progressToCenter);
            child.setScaleY(progressToCenter);
        } catch (Exception ignored) {
        }
    }
}
