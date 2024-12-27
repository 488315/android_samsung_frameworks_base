package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import com.android.systemui.facewidget.plugin.KeyguardStatusViewAlphaChangeControllerWrapper;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DragViewController extends ViewAnimationController {
    public final List dragViews;
    public final KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper;
    public final List onlyAlphaDragViews;
    public AnimatorSet restoreAnimatorSet;
    public AnimatorSet unlockViewHideAnimatorSet;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DragViewController(KeyguardTouchAnimator keyguardTouchAnimator, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper) {
        super(keyguardTouchAnimator);
        this.keyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
        this.dragViews = CollectionsKt__CollectionsKt.listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12);
        this.onlyAlphaDragViews = CollectionsKt__CollectionsKt.listOf(6, 7, 9);
        CollectionsKt__CollectionsKt.listOf(1, 3, 4, 5, 6, 7, 8, 9, 10, 12);
        this.unlockViewHideAnimatorSet = new AnimatorSet();
        this.restoreAnimatorSet = new AnimatorSet();
    }

    public static AnimatorSet createAnimatorSet$default(DragViewController dragViewController, int i) {
        dragViewController.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        if (i == 0) {
            animatorSet.setInterpolator(dragViewController.SINE_IN_33);
            animatorSet.setDuration(100L);
            dragViewController.unlockViewHideAnimatorSet = animatorSet;
        } else if (i == 1) {
            animatorSet.setDuration(400L);
            animatorSet.setInterpolator(dragViewController.SINE_OUT_33);
            dragViewController.restoreAnimatorSet = animatorSet;
        }
        return animatorSet;
    }
}
