package com.android.wm.shell.onehanded;

import android.view.SurfaceControl;
import com.android.wm.shell.onehanded.OneHandedAnimationController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OneHandedAnimationController.OneHandedTransitionAnimator f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;

    public /* synthetic */ OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda2(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator, SurfaceControl.Transaction transaction, int i) {
        this.$r8$classId = i;
        this.f$0 = oneHandedTransitionAnimator;
        this.f$1 = transaction;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = this.f$0;
        OneHandedAnimationCallback oneHandedAnimationCallback = (OneHandedAnimationCallback) obj;
        switch (i) {
            case 0:
                int i2 = OneHandedAnimationController.OneHandedTransitionAnimator.$r8$clinit;
                oneHandedAnimationCallback.onOneHandedAnimationEnd(oneHandedTransitionAnimator);
                break;
            default:
                oneHandedAnimationCallback.onAnimationUpdate(oneHandedTransitionAnimator.mCurrentValue);
                break;
        }
    }
}
