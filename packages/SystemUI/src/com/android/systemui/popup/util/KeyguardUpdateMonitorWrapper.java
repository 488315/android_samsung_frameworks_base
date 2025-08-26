package com.android.systemui.popup.util;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;

/* loaded from: classes2.dex */
public class KeyguardUpdateMonitorWrapper {
    private final Context mContext;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public KeyguardUpdateMonitorWrapper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public void setDialogStateForInDisplayFingerprint(boolean z) {
    }
}
