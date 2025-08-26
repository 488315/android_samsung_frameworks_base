package com.samsung.android.globalactions.util;

import android.content.Context;
import android.provider.Settings;

/* loaded from: classes6.dex */
public class SemReserveBatteryWrapper {
    private final Context mContext;
    private boolean mConfigYuvaFeature = initYuvaFeature();
    private boolean mConfigYuvaDownloadable = initYuvaDownloadable();

    private boolean initYuvaDownloadable() {
        return false;
    }

    private boolean initYuvaFeature() {
        return false;
    }

    public SemReserveBatteryWrapper(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026 A[PHI: r0
      0x0026: PHI (r0v9 boolean) = (r0v5 boolean), (r0v14 boolean) binds: [B:17:0x0043, B:9:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0028 A[PHI: r0
      0x0028: PHI (r0v7 boolean) = (r0v5 boolean), (r0v14 boolean) binds: [B:17:0x0043, B:9:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isReserveBatteryMode() {
        boolean z;
        boolean z2;
        if (this.mConfigYuvaDownloadable) {
            z = Settings.Secure.getInt(this.mContext.getContentResolver(), "reserve_battery_on", 0) != 0;
            z2 = Settings.Secure.getInt(this.mContext.getContentResolver(), "enable_reserve_max_mode", 0) != 0;
        } else {
            z = Settings.System.getInt(this.mContext.getContentResolver(), "reserve_battery_on", 0) != 0;
            if (Settings.System.getInt(this.mContext.getContentResolver(), "enable_reserve_max_mode", 0) != 0) {
            }
        }
        return this.mConfigYuvaFeature && z && z2;
    }
}
