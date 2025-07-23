package com.android.wm.shell.splitscreen;

import android.animation.ValueAnimator;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ValueAnimator f$1;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda13(Object obj, ValueAnimator valueAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = valueAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenTransitions splitScreenTransitions = (SplitScreenTransitions) this.f$0;
                splitScreenTransitions.mAnimations.remove(this.f$1);
                splitScreenTransitions.onFinish(null);
                break;
            default:
                SplitScreenTransitions.AnonymousClass1 anonymousClass1 = (SplitScreenTransitions.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.mAnimations.remove(this.f$1);
                anonymousClass1.this$0.onFinish(null);
                break;
        }
    }
}
