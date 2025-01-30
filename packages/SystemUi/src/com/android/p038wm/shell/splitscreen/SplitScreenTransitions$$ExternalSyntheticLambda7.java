package com.android.p038wm.shell.splitscreen;

import android.animation.ValueAnimator;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ValueAnimator f$1;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda7(Object obj, ValueAnimator valueAnimator, int i) {
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
                splitScreenTransitions.onFinish(null, null);
                break;
            default:
                SplitScreenTransitions.C41131 c41131 = (SplitScreenTransitions.C41131) this.f$0;
                c41131.this$0.mAnimations.remove(this.f$1);
                c41131.this$0.onFinish(null, null);
                break;
        }
    }
}
