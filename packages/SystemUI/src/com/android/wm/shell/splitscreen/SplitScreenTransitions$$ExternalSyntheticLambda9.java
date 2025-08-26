package com.android.wm.shell.splitscreen;

import android.animation.ValueAnimator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ SplitScreenTransitions f$0;
    public final /* synthetic */ ValueAnimator f$1;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda9(SplitScreenTransitions splitScreenTransitions, ValueAnimator valueAnimator) {
        this.f$0 = splitScreenTransitions;
        this.f$1 = valueAnimator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SplitScreenTransitions splitScreenTransitions = this.f$0;
        splitScreenTransitions.mAnimations.remove(this.f$1);
        if (((Boolean) obj).booleanValue()) {
            splitScreenTransitions.mTransitions.mMainExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda0(splitScreenTransitions, 1));
        }
    }
}
