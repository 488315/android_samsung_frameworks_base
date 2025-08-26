package com.android.systemui.qs;

import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSImpl$onComponentCreated$3$1 implements Runnable {
    public final /* synthetic */ SecQSImpl $tmp0;

    public SecQSImpl$onComponentCreated$3$1(SecQSImpl secQSImpl) {
        this.$tmp0 = secQSImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SecQSImplAnimatorManager secQSImplAnimatorManager = this.$tmp0.secQSImplAnimatorManager;
        if (secQSImplAnimatorManager != null) {
            secQSImplAnimatorManager.updateAnimators();
            Unit unit = Unit.INSTANCE;
        }
    }
}
