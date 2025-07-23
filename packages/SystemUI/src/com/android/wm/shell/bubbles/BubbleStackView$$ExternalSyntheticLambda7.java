package com.android.wm.shell.bubbles;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.View;
import com.android.systemui.R;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.DismissView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda7 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda7(BubbleStackView bubbleStackView, float f) {
        this.$r8$classId = 1;
        this.f$0 = bubbleStackView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        int i = this.$r8$classId;
        BubbleStackView bubbleStackView = this.f$0;
        switch (i) {
            case 0:
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleExpandedView expandedView = bubbleStackView.getExpandedView();
                if (expandedView != null) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    expandedView.setContentAlpha(floatValue);
                    expandedView.mPointerView.setAlpha(floatValue);
                    expandedView.setAlpha(floatValue);
                    break;
                }
                break;
            case 1:
                PhysicsAnimator.SpringConfig springConfig2 = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                DismissView dismissView = bubbleStackView.mDismissView;
                if (dismissView != null) {
                    if (floatValue2 <= 0.2d) {
                        dismissView.circle.setup(R.drawable.dismiss_circle_background, R.drawable.bubble_delete_ic_drop, R.dimen.sec_noti_bubble_dismiss_button_width);
                        f = 1.15f;
                    } else {
                        Resources.getSystem().getConfiguration();
                        bubbleStackView.resetCircle();
                        f = 1.0f;
                    }
                    bubbleStackView.mDismissView.circle.setScaleX(f);
                    bubbleStackView.mDismissView.circle.setScaleY(f);
                }
                View view = bubbleStackView.mViewBeingDismissed;
                if (view != null) {
                    view.setAlpha(Math.max(floatValue2, 0.7f));
                    break;
                }
                break;
            default:
                if (!bubbleStackView.mExpandedViewTemporarilyHidden) {
                    bubbleStackView.mAnimatingOutSurfaceView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
                }
                break;
        }
    }

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda7(BubbleStackView bubbleStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
    }
}
