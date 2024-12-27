package com.android.systemui.keyguard;

import android.os.SystemProperties;

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
