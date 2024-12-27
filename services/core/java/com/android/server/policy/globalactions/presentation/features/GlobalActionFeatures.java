package com.android.server.policy.globalactions.presentation.features;

import android.R;
import android.content.Context;
import android.os.SystemProperties;
import com.samsung.android.globalactions.presentation.features.Features;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GlobalActionFeatures implements Features {
    public Context mContext;

    public final boolean isEnabled(String str) {
        if (str.equals("SF_EFFECT")) {
            return true;
        }
        if (str.equals("NAV_BAR")) {
            return this.mContext.getResources().getBoolean(R.bool.config_sms_decode_gsm_8bit_data);
        }
        if (str.equals("DESKTOP_MODE")) {
            return true;
        }
        if (str.equals("DATA_MODE")) {
            return List.of("GLB", "XTC", "SMA", "XTE").contains(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase());
        }
        if (str.equals("LOCKDOWN_MODE")) {
            return true;
        }
        if (str.equals("FORCE_RESTART_MESSAGE")) {
            return List.of("CHC", "CHM", "CHN", "CBK", "CTC", "CHU", "BNZ").contains(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase());
        }
        str.equals("RESERVE_BATTERY_MODE");
        return false;
    }
}
