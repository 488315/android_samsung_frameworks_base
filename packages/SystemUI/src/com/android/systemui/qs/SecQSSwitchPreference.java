package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;

public class SecQSSwitchPreference extends LinearLayout {
    public SecQSSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static SecQSSwitchPreference inflateSwitch(Context context, ViewGroup viewGroup) {
        return (SecQSSwitchPreference) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_item_summary_switch, viewGroup, false);
    }

    public SecQSSwitchPreference(Context context) {
        super(context);
    }
}
