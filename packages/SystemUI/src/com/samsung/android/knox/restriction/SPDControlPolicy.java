package com.samsung.android.knox.restriction;

import android.content.Context;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SPDControlPolicy {
    public static final int SPD_ENFORCE_NONE = 0;
    public static final int SPD_ENFORCE_OFF = 2;
    public static final int SPD_ENFORCE_ON = 1;
    public static final int SPD_FAILED = -1;
    public static final int SPD_OFF = 4;
    public static final int SPD_ON = 3;
    public static String TAG = "SPDControlPolicy";

    public SPDControlPolicy(ContextInfo contextInfo, Context context) {
    }

    public int getAutoSecurityPolicyUpdateMode() {
        return -1;
    }

    public boolean setAutoSecurityPolicyUpdateMode(int i) {
        return false;
    }
}
