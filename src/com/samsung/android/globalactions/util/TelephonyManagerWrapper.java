package com.samsung.android.globalactions.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.TelephonyProperties;

/* loaded from: classes6.dex */
public class TelephonyManagerWrapper {
    private final boolean mHasTelephonyRadio;
    private final TelephonyManager mTelephonyManager;

    public TelephonyManagerWrapper(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        this.mHasTelephonyRadio = telephonyManager.isVoiceCapable();
    }

    public boolean hasTelephonyRadio() {
        return this.mHasTelephonyRadio;
    }

    public boolean isDataEnabled() {
        return this.mTelephonyManager.isDataEnabled();
    }

    public boolean hasAnySim() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            int activeModemCount = telephonyManager.getActiveModemCount();
            for (int i = 0; i < activeModemCount; i++) {
                int simState = this.mTelephonyManager.getSimState(i);
                if (simState != 1 && simState != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSimLock() {
        for (int i = 0; i < this.mTelephonyManager.getActiveModemCount(); i++) {
            int simState = getSimState(i);
            if (simState == 2 || simState == 3 || simState == 12) {
                return true;
            }
        }
        return false;
    }

    public int getSimState(int i) {
        int simState = this.mTelephonyManager.getSimState(i);
        return simState == 4 ? getPersoLockedState(i) : simState;
    }

    private int getPersoLockedState(int i) {
        return "PERSO_LOCKED".equals(getSimStateSystemProperty(TelephonyProperties.PROPERTY_SIM_STATE, i, "NOT_READY")) ? 12 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:
    
        if (r0 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getSimStateSystemProperty(java.lang.String r1, int r2, java.lang.String r3) {
        /*
            r0 = this;
            java.lang.String r0 = android.os.SystemProperties.get(r1)
            if (r0 == 0) goto L1c
            int r1 = r0.length()
            if (r1 <= 0) goto L1c
            java.lang.String r1 = ","
            java.lang.String[] r0 = r0.split(r1)
            if (r2 < 0) goto L1c
            int r1 = r0.length
            if (r2 >= r1) goto L1c
            r0 = r0[r2]
            if (r0 == 0) goto L1c
            goto L1d
        L1c:
            r0 = 0
        L1d:
            if (r0 != 0) goto L20
            return r3
        L20:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.globalactions.util.TelephonyManagerWrapper.getSimStateSystemProperty(java.lang.String, int, java.lang.String):java.lang.String");
    }
}
