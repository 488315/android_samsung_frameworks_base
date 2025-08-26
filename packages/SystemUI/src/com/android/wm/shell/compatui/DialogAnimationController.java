package com.android.wm.shell.compatui;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.IntProperty;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Animation;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.transition.Transitions;

/* loaded from: classes3.dex */
public class DialogAnimationController {
    public static final AnonymousClass3 DRAWABLE_ALPHA;
    public static final int ENTER_ANIM_START_DELAY_MILLIS;
    public final int mAnimStyleResId;
    public Animator mBackgroundDimAnimator;
    public Animation mDialogAnimation;
    public final String mPackageName;
    public final String mTag;
    public final TransitionAnimation mTransitionAnimation;

    /* renamed from: com.android.wm.shell.compatui.DialogAnimationController$2, reason: invalid class name */
    public class AnonymousClass2 extends AnimatorListenerAdapter {
        public AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            DialogAnimationController.this.mBackgroundDimAnimator = null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.compatui.DialogAnimationController$3] */
    static {
        ENTER_ANIM_START_DELAY_MILLIS = Transitions.ENABLE_SHELL_TRANSITIONS ? 300 : 500;
        DRAWABLE_ALPHA = new IntProperty("alpha") { // from class: com.android.wm.shell.compatui.DialogAnimationController.3
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((Drawable) obj).getAlpha());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((Drawable) obj).setAlpha(i);
            }
        };
    }

    public DialogAnimationController(Context context, String str) {
        this.mTransitionAnimation = new TransitionAnimation(context, false, str);
        this.mAnimStyleResId = new ContextThemeWrapper(context, R.style.ThemeOverlay.Material.Dialog).getTheme().obtainStyledAttributes(com.android.internal.R.styleable.Window).getResourceId(8, 0);
        this.mPackageName = context.getPackageName();
        this.mTag = str;
    }

    public final void cancelAnimation() {
        Animation animation = this.mDialogAnimation;
        if (animation != null) {
            animation.cancel();
            this.mDialogAnimation = null;
        }
        Animator animator = this.mBackgroundDimAnimator;
        if (animator != null) {
            animator.cancel();
            this.mBackgroundDimAnimator = null;
        }
    }

    public final Animation loadAnimation(int i) {
        Animation animationLoadAnimationAttr = this.mTransitionAnimation.loadAnimationAttr(this.mPackageName, this.mAnimStyleResId, i, false);
        if (animationLoadAnimationAttr == null) {
            Log.e(this.mTag, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Failed to load animation "));
        }
        return animationLoadAnimationAttr;
    }

    public final void startEnterAnimation(DialogContainerSupplier dialogContainerSupplier, final Runnable runnable) {
        cancelAnimation();
        final View dialogContainerView = dialogContainerSupplier.getDialogContainerView();
        Animation animationLoadAnimation = loadAnimation(0);
        this.mDialogAnimation = animationLoadAnimation;
        if (animationLoadAnimation == null) {
            runnable.run();
            return;
        }
        animationLoadAnimation.setAnimationListener(new AnonymousClass1(this, new Runnable() { // from class: com.android.wm.shell.compatui.DialogAnimationController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                View view = dialogContainerView;
                int i = DialogAnimationController.ENTER_ANIM_START_DELAY_MILLIS;
                view.setAlpha(1.0f);
            }
        }, new Runnable() { // from class: com.android.wm.shell.compatui.DialogAnimationController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DialogAnimationController dialogAnimationController = this.f$0;
                Runnable runnable2 = runnable;
                dialogAnimationController.mDialogAnimation = null;
                runnable2.run();
            }
        }));
        Drawable backgroundDimDrawable = dialogContainerSupplier.getBackgroundDimDrawable();
        long duration = this.mDialogAnimation.getDuration();
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(backgroundDimDrawable, DRAWABLE_ALPHA, 204);
        objectAnimatorOfInt.setDuration(duration);
        this.mBackgroundDimAnimator = objectAnimatorOfInt;
        objectAnimatorOfInt.addListener(new AnonymousClass2());
        Animation animation = this.mDialogAnimation;
        long j = ENTER_ANIM_START_DELAY_MILLIS;
        animation.setStartOffset(j);
        this.mBackgroundDimAnimator.setStartDelay(j);
        dialogContainerView.startAnimation(this.mDialogAnimation);
        this.mBackgroundDimAnimator.start();
    }

    public final void startExitAnimation(DialogContainerSupplier dialogContainerSupplier, final Runnable runnable) {
        cancelAnimation();
        final View dialogContainerView = dialogContainerSupplier.getDialogContainerView();
        Animation animationLoadAnimation = loadAnimation(1);
        this.mDialogAnimation = animationLoadAnimation;
        if (animationLoadAnimation == null) {
            runnable.run();
            return;
        }
        animationLoadAnimation.setAnimationListener(new AnonymousClass1(this, new DialogAnimationController$$ExternalSyntheticLambda2(), new Runnable() { // from class: com.android.wm.shell.compatui.DialogAnimationController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DialogAnimationController dialogAnimationController = this.f$0;
                View view = dialogContainerView;
                Runnable runnable2 = runnable;
                int i = DialogAnimationController.ENTER_ANIM_START_DELAY_MILLIS;
                dialogAnimationController.getClass();
                view.setAlpha(0.0f);
                dialogAnimationController.mDialogAnimation = null;
                runnable2.run();
            }
        }));
        Drawable backgroundDimDrawable = dialogContainerSupplier.getBackgroundDimDrawable();
        long duration = this.mDialogAnimation.getDuration();
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(backgroundDimDrawable, DRAWABLE_ALPHA, 0);
        objectAnimatorOfInt.setDuration(duration);
        this.mBackgroundDimAnimator = objectAnimatorOfInt;
        objectAnimatorOfInt.addListener(new AnonymousClass2());
        dialogContainerView.startAnimation(this.mDialogAnimation);
        this.mBackgroundDimAnimator.start();
    }

    /* renamed from: com.android.wm.shell.compatui.DialogAnimationController$1, reason: invalid class name */
    public class AnonymousClass1 implements Animation.AnimationListener {
        public final /* synthetic */ Runnable val$endCallback;
        public final /* synthetic */ Runnable val$startCallback;

        public AnonymousClass1(DialogAnimationController dialogAnimationController, Runnable runnable, Runnable runnable2) {
            this.val$startCallback = runnable;
            this.val$endCallback = runnable2;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationEnd(Animation animation) {
            this.val$endCallback.run();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationStart(Animation animation) {
            this.val$startCallback.run();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationRepeat(Animation animation) {
        }
    }
}
