package com.samsung.android.knox.restriction;

import android.content.Context;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SPDControlPolicy {
    public static final int SPD_ENFORCE_NONE = 0;
    public static final int SPD_ENFORCE_OFF = 2;
    public static final int SPD_ENFORCE_ON = 1;
    public static final int SPD_FAILED = -1;
    public static final int SPD_OFF = 4;
    public static final int SPD_ON = 3;
    public static String TAG = "SPDControlPolicy";

    public SPDControlPolicy(ContextInfo contextInfo, Context context) {
    }

    public final int getAutoSecurityPolicyUpdateMode() {
        return -1;
    }

    public final boolean setAutoSecurityPolicyUpdateMode(int i) {
        return false;
    }
}
