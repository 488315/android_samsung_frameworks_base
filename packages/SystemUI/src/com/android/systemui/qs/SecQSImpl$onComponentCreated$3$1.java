package com.android.systemui.qs;

import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
