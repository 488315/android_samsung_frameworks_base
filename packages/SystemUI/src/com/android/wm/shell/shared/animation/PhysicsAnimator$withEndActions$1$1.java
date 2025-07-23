package com.android.wm.shell.shared.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class PhysicsAnimator$withEndActions$1$1 extends FunctionReferenceImpl implements Function0 {
    public PhysicsAnimator$withEndActions$1$1(Object obj) {
        super(0, obj, Runnable.class, "run", "run()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((Runnable) this.receiver).run();
        return Unit.INSTANCE;
    }
}
