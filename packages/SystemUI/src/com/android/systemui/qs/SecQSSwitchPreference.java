package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
