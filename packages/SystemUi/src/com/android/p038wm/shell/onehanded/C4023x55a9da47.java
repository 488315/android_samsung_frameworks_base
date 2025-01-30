package com.android.p038wm.shell.onehanded;

import com.android.p038wm.shell.onehanded.OneHandedAnimationController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C4023x55a9da47 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OneHandedAnimationController.OneHandedTransitionAnimator f$0;

    public /* synthetic */ C4023x55a9da47(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = oneHandedTransitionAnimator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = this.f$0;
                oneHandedTransitionAnimator.getClass();
                ((OneHandedAnimationCallback) obj).onOneHandedAnimationStart(oneHandedTransitionAnimator);
                break;
            default:
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator2 = this.f$0;
                oneHandedTransitionAnimator2.getClass();
                ((OneHandedAnimationCallback) obj).onOneHandedAnimationCancel(oneHandedTransitionAnimator2);
                break;
        }
    }
}
