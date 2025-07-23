package com.samsung.android.globalactions.util;

import android.content.Context;

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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0028, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0043, code lost:
    
        if (android.provider.Settings.System.getInt(r5.mContext.getContentResolver(), "enable_reserve_max_mode", 0) != 0) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0024, code lost:
    
        if (android.provider.Settings.Secure.getInt(r5.mContext.getContentResolver(), "enable_reserve_max_mode", 0) != 0) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
    
        r1 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isReserveBatteryMode() {
        /*
            r5 = this;
            boolean r0 = r5.mConfigYuvaDownloadable
            java.lang.String r1 = "enable_reserve_max_mode"
            java.lang.String r2 = "reserve_battery_on"
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L2a
            android.content.Context r0 = r5.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            int r0 = android.provider.Settings.Secure.getInt(r0, r2, r4)
            if (r0 == 0) goto L19
            r0 = r3
            goto L1a
        L19:
            r0 = r4
        L1a:
            android.content.Context r2 = r5.mContext
            android.content.ContentResolver r2 = r2.getContentResolver()
            int r1 = android.provider.Settings.Secure.getInt(r2, r1, r4)
            if (r1 == 0) goto L28
        L26:
            r1 = r3
            goto L46
        L28:
            r1 = r4
            goto L46
        L2a:
            android.content.Context r0 = r5.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            int r0 = android.provider.Settings.System.getInt(r0, r2, r4)
            if (r0 == 0) goto L38
            r0 = r3
            goto L39
        L38:
            r0 = r4
        L39:
            android.content.Context r2 = r5.mContext
            android.content.ContentResolver r2 = r2.getContentResolver()
            int r1 = android.provider.Settings.System.getInt(r2, r1, r4)
            if (r1 == 0) goto L28
            goto L26
        L46:
            boolean r5 = r5.mConfigYuvaFeature
            if (r5 == 0) goto L4f
            if (r0 == 0) goto L4f
            if (r1 == 0) goto L4f
            return r3
        L4f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.globalactions.util.SemReserveBatteryWrapper.isReserveBatteryMode():boolean");
    }
}
