package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSSwitchPreference extends LinearLayout {
    public SecQSSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static SecQSSwitchPreference inflateSwitch(Context context, ViewGroup viewGroup) {
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return (i <= 320 && (configuration.fontScale > 1.1f ? 1 : (configuration.fontScale == 1.1f ? 0 : -1)) >= 0) || (i < 411 && (configuration.fontScale > 1.3f ? 1 : (configuration.fontScale == 1.3f ? 0 : -1)) >= 0) ? (SecQSSwitchPreference) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_item_summary_switch_large, viewGroup, false) : (SecQSSwitchPreference) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_item_summary_switch, viewGroup, false);
    }

    public SecQSSwitchPreference(Context context) {
        super(context);
    }
}
