package com.android.systemui.dreams;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.ui.viewmodel.DreamViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CrossFadeHelper;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class DreamOverlayAnimationsController {
    public final DreamViewModel dreamViewModel;
    public final Logger logger;
    public Animator mAnimator;
    public final BlurUtils mBlurUtils;
    public final ComplicationHostViewController mComplicationHostViewController;
    public final Map mCurrentAlphaAtPosition = new LinkedHashMap();
    public float mCurrentBlurRadius;
    public final int mDreamBlurRadius;
    public final long mDreamInBlurAnimDurationMs;
    public final long mDreamInComplicationsAnimDurationMs;
    public final int mDreamInTranslationYDistance;
    public final long mDreamInTranslationYDurationMs;
    public RepeatWhenAttachedKt.C09181 mLifecycleFlowHandle;
    public final DreamOverlayStateController mOverlayStateController;
    public final AmbientStatusBarViewController mStatusBarViewController;
    public View view;

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

    public DreamOverlayAnimationsController(BlurUtils blurUtils, ComplicationHostViewController complicationHostViewController, AmbientStatusBarViewController ambientStatusBarViewController, DreamOverlayStateController dreamOverlayStateController, int i, DreamViewModel dreamViewModel, long j, long j2, int i2, long j3, LogBuffer logBuffer) {
        this.mBlurUtils = blurUtils;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mStatusBarViewController = ambientStatusBarViewController;
        this.mOverlayStateController = dreamOverlayStateController;
        this.mDreamBlurRadius = i;
        this.dreamViewModel = dreamViewModel;
        this.mDreamInBlurAnimDurationMs = j;
        this.mDreamInComplicationsAnimDurationMs = j2;
        this.mDreamInTranslationYDistance = i2;
        this.mDreamInTranslationYDurationMs = j3;
        this.logger = new Logger(logBuffer, "DreamOverlayAnimationsController");
    }

    public static final void access$setElementsAlphaAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i, boolean z) {
        dreamOverlayAnimationsController.mCurrentAlphaAtPosition.put(Integer.valueOf(i), Float.valueOf(f));
        for (View view : dreamOverlayAnimationsController.mComplicationHostViewController.getViewsAtPosition(i)) {
            if (z) {
                CrossFadeHelper.fadeOut(view, 1 - f, false);
            } else {
                CrossFadeHelper.fadeIn(view, f, false);
            }
        }
        if (i == 1) {
            dreamOverlayAnimationsController.mStatusBarViewController.setFadeAmount(f, z);
        }
    }

    public static final void access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i) {
        Iterator it = dreamOverlayAnimationsController.mComplicationHostViewController.getViewsAtPosition(i).iterator();
        while (it.hasNext()) {
            ((View) it.next()).setTranslationY(f);
        }
        if (i == 1) {
            dreamOverlayAnimationsController.mStatusBarViewController.setTranslationY(f);
        }
    }

    public static Animator alphaAnimator$default(final DreamOverlayAnimationsController dreamOverlayAnimationsController, final float f, final float f2, long j, final int i, Interpolator interpolator, int i2) {
        if ((i2 & 16) != 0) {
            i = 3;
        }
        if ((i2 & 32) != 0) {
            interpolator = Interpolators.LINEAR;
        }
        dreamOverlayAnimationsController.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setStartDelay(0L);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                final float f3 = f2;
                final float f4 = f;
                ComplicationLayoutParams.iteratePositions(i, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DreamOverlayAnimationsController.access$setElementsAlphaAtPosition(dreamOverlayAnimationsController2, ((Float) valueAnimator.getAnimatedValue()).floatValue(), ((Number) obj).intValue(), f3 < f4);
                    }
                });
            }
        });
        return valueAnimatorOfFloat;
    }

    public static Animator translationYAnimator$default(final DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, float f2, long j, Interpolator interpolator, int i) {
        dreamOverlayAnimationsController.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setStartDelay(0L);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        final int i2 = 3;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                ComplicationLayoutParams.iteratePositions(i2, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DreamOverlayAnimationsController.access$setElementsTranslationYAtPosition(dreamOverlayAnimationsController2, ((Float) valueAnimator.getAnimatedValue()).floatValue(), ((Number) obj).intValue());
                    }
                });
            }
        });
        return valueAnimatorOfFloat;
    }

    public final void cancelAnimations() {
        Animator animator = this.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.mAnimator = null;
        DreamOverlayStateController dreamOverlayStateController = this.mOverlayStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(1, 8);
    }
}
