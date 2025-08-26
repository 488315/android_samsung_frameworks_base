package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import com.android.systemui.facewidget.plugin.KeyguardStatusViewAlphaChangeControllerWrapper;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class DragViewController extends ViewAnimationController {
    public final List dragViews;
    public final KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper;
    public final List onlyAlphaDragViews;
    public AnimatorSet restoreAnimatorSet;
    public AnimatorSet unlockViewHideAnimatorSet;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DragViewController(KeyguardTouchAnimator keyguardTouchAnimator, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper) {
        super(keyguardTouchAnimator);
        this.keyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
        this.dragViews = Arrays.asList(1, 14, 2, 4, 5, 6, 7, 8, 9, 10, 12, 13);
        this.onlyAlphaDragViews = Arrays.asList(6, 7, 9, 13);
        CollectionsKt__CollectionsKt.listOf(1, 4, 5, 6, 7, 8, 9, 10, 12, 13);
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
            return animatorSet;
        }
        if (i != 1) {
            return animatorSet;
        }
        animatorSet.setDuration(400L);
        animatorSet.setInterpolator(dragViewController.SINE_OUT_33);
        dragViewController.restoreAnimatorSet = animatorSet;
        return animatorSet;
    }
}
