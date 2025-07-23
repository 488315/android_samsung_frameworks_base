package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedAnimationController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OneHandedAnimationController.OneHandedTransitionAnimator f$0;

    public /* synthetic */ OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda0(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = oneHandedTransitionAnimator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = this.f$0;
        OneHandedAnimationCallback oneHandedAnimationCallback = (OneHandedAnimationCallback) obj;
        switch (i) {
            case 0:
                int i2 = OneHandedAnimationController.OneHandedTransitionAnimator.$r8$clinit;
                oneHandedAnimationCallback.onOneHandedAnimationCancel(oneHandedTransitionAnimator);
                break;
            default:
                int i3 = OneHandedAnimationController.OneHandedTransitionAnimator.$r8$clinit;
                oneHandedAnimationCallback.onOneHandedAnimationStart(oneHandedTransitionAnimator);
                break;
        }
    }
}
