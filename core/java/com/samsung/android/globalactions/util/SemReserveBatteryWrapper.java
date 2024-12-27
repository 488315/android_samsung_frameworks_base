package com.samsung.android.globalactions.util;

import android.content.Context;
import android.provider.Settings;

/* loaded from: classes6.dex */
public class SemReserveBatteryWrapper {
    private final Context mContext;
    private boolean mConfigYuvaFeature = initYuvaFeature();
    private boolean mConfigYuvaDownloadable = initYuvaDownloadable();

    public SemReserveBatteryWrapper(Context context) {
        this.mContext = context;
    }

    private boolean initYuvaFeature() {
        return false;
    }

    private boolean initYuvaDownloadable() {
        return false;
    }

    public boolean isReserveBatteryMode() {
        boolean mRBM;
        boolean mEnableRBM;
        if (this.mConfigYuvaDownloadable) {
            mRBM =
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(), "reserve_battery_on", 0)
                            != 0;
            mEnableRBM =
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "enable_reserve_max_mode",
                                    0)
                            != 0;
        } else {
            mRBM =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(), "reserve_battery_on", 0)
                            != 0;
            mEnableRBM =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "enable_reserve_max_mode",
                                    0)
                            != 0;
        }
        return this.mConfigYuvaFeature && mRBM && mEnableRBM;
    }
}
