package com.android.systemui.keyguard;

import android.os.SystemProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1 implements Runnable {
    public final /* synthetic */ KeyguardViewMediatorHelperImpl this$0;

    public KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl) {
        this.this$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SystemProperties.set("sys.locksecured", this.this$0.isShowing$1() ? "true" : "false");
    }
}
