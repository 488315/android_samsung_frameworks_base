package com.android.internal.accessibility.dialog;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

abstract class TargetAdapter extends BaseAdapter {

    static class ViewHolder {
        CheckBox mCheckBoxView;
        ImageView mIconView;
        TextView mLabelView;
        TextView mStatusView;

        ViewHolder() {}
    }

    TargetAdapter() {}
}
