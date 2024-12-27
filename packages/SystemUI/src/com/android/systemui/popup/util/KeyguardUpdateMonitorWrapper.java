package com.android.systemui.popup.util;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
