package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DragViewController extends ViewAnimationController {
    public final List dragViews;
    public final List onlyAlphaDragViews;
    public AnimatorSet restoreAnimatorSet;
    public AnimatorSet unlockViewHideAnimatorSet;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public DragViewController(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
        this.dragViews = CollectionsKt__CollectionsKt.listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        this.onlyAlphaDragViews = CollectionsKt__CollectionsKt.listOf(6, 7, 9);
        this.unlockViewHideAnimatorSet = new AnimatorSet();
        this.restoreAnimatorSet = new AnimatorSet();
    }

    public static AnimatorSet createAnimatorSet$default(DragViewController dragViewController, int i) {
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
