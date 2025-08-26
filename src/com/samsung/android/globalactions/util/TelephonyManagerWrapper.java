package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.SystemProperties;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String getSimStateSystemProperty(String str, int i, String str2) {
        String str3;
        String str4 = SystemProperties.get(str);
        if (str4 == null || str4.length() <= 0) {
            str3 = null;
        } else {
            String[] strArrSplit = str4.split(",");
            if (i < 0 || i >= strArrSplit.length || (str3 = strArrSplit[i]) == null) {
            }
        }
        return str3 == null ? str2 : str3;
    }
}
