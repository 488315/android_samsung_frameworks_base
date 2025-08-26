package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class ActivityTransitionAnimator$Runner$createDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public ActivityTransitionAnimator$Runner$createDelegate$1(Object obj) {
        super(0, obj, ActivityTransitionAnimator.Runner.class, "dispose", "dispose()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ActivityTransitionAnimator.Runner runner = (ActivityTransitionAnimator.Runner) this.receiver;
        ActivityTransitionAnimator.this.mainExecutor.execute(new ActivityTransitionAnimator$Runner$dispose$1(runner));
        return Unit.INSTANCE;
    }
}
