package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.android.systemui.flags.FeatureFlags;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MessageContainerController {
    public Animator animateOut;
    public ViewGroup container;
    public ViewGroup detectionNoticeView;
    public Guideline guideline;
    public ViewGroup workProfileFirstRunView;
    public final WorkProfileMessageController workProfileMessageController;

    public MessageContainerController(WorkProfileMessageController workProfileMessageController, ScreenshotDetectionController screenshotDetectionController, FeatureFlags featureFlags) {
        this.workProfileMessageController = workProfileMessageController;
    }

    public final Animator getAnimator(boolean z) {
        ViewGroup viewGroup = this.container;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
        ViewGroup viewGroup2 = this.container;
        final int height = (viewGroup2 != null ? viewGroup2 : null).getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
        ValueAnimator ofFloat = z ? ValueAnimator.ofFloat(0.0f, 1.0f) : ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(400L);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.MessageContainerController$getAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Guideline guideline = MessageContainerController.this.guideline;
                if (guideline == null) {
                    guideline = null;
                }
                int i = (int) (height * floatValue);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
                if (!guideline.mFilterRedundantCalls || layoutParams.guideEnd != i) {
                    layoutParams.guideEnd = i;
                    guideline.setLayoutParams(layoutParams);
                }
                ViewGroup viewGroup3 = MessageContainerController.this.container;
                (viewGroup3 != null ? viewGroup3 : null).setAlpha(floatValue);
            }
        });
        return ofFloat;
    }
}
