package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import com.android.wm.shell.windowdecor.widget.CaptionAnimationButton;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MenuPopupAnimator {
    public final List animators = new ArrayList();
    public final float captionHeight;
    public final View menuPopupView;
    public final int menuWidth;
    public AnimatorSet runningAnimation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public MenuPopupAnimator(View view, int i, float f) {
        this.menuPopupView = view;
        this.menuWidth = i;
        this.captionHeight = f;
    }

    public final void animateOpen(final CaptionAnimationButton captionAnimationButton, final HandleMenu$$ExternalSyntheticLambda0 handleMenu$$ExternalSyntheticLambda0) {
        this.menuPopupView.setAlpha(0.0f);
        this.menuPopupView.setPivotX(this.menuWidth / 2.0f);
        this.menuPopupView.setPivotY(0.0f);
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.menuPopupView, (Property<View, Float>) View.ALPHA, 1.0f);
        ofFloat.setDuration(100L);
        ((ArrayList) list).add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.menuPopupView, (Property<View, Float>) View.SCALE_X, 1.0f);
        ofFloat2.setDuration(250L);
        ((ArrayList) list2).add(ofFloat2);
        List list3 = this.animators;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.menuPopupView, (Property<View, Float>) View.SCALE_Y, 1.0f);
        ofFloat3.setDuration(250L);
        ((ArrayList) list3).add(ofFloat3);
        float f = (-this.captionHeight) / 2;
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.menuPopupView, (Property<View, Float>) View.TRANSLATION_Y, f, 0.0f);
        ofFloat4.setDuration(100L);
        ((ArrayList) list4).add(ofFloat4);
        runAnimations(new Function0() { // from class: com.android.wm.shell.windowdecor.MenuPopupAnimator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final MenuPopupAnimator menuPopupAnimator = MenuPopupAnimator.this;
                menuPopupAnimator.menuPopupView.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MenuPopupAnimator$animateOpen$5$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MenuPopupAnimator.this.menuPopupView.sendAccessibilityEvent(8);
                    }
                });
                CaptionAnimationButton captionAnimationButton2 = captionAnimationButton;
                if (captionAnimationButton2 != null) {
                    captionAnimationButton2.playAnimation();
                }
                HandleMenu$$ExternalSyntheticLambda0 handleMenu$$ExternalSyntheticLambda02 = handleMenu$$ExternalSyntheticLambda0;
                if (handleMenu$$ExternalSyntheticLambda02 != null) {
                    handleMenu$$ExternalSyntheticLambda02.invoke();
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void runAnimations(final Function0 function0) {
        AnimatorSet animatorSet = this.runningAnimation;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(this.animators);
        ((ArrayList) this.animators).clear();
        animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MenuPopupAnimator$runAnimations$lambda$11$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Function0 function02 = Function0.this;
                if (function02 != null) {
                    function02.invoke();
                }
                this.runningAnimation = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet2.start();
        this.runningAnimation = animatorSet2;
    }
}
