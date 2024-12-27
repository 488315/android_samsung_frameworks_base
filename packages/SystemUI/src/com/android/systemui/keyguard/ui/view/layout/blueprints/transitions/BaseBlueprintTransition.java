package com.android.systemui.keyguard.ui.view.layout.blueprints.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.helper.widget.Layer;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.clocks.ClockController;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BaseBlueprintTransition extends TransitionSet {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AlphaInVisibility extends Visibility {
        @Override // android.transition.Visibility
        public final Animator onAppear(ViewGroup viewGroup, final View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.BaseBlueprintTransition$AlphaInVisibility$onAppear$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.start();
            return ofFloat;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AlphaOutVisibility extends Visibility {
        @Override // android.transition.Visibility
        public final Animator onDisappear(ViewGroup viewGroup, final View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.BaseBlueprintTransition$AlphaOutVisibility$onDisappear$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.start();
            return ofFloat;
        }
    }

    public BaseBlueprintTransition(KeyguardClockViewModel keyguardClockViewModel) {
        setOrdering(1);
        addTransition(new AlphaOutVisibility()).addTransition(new ChangeBounds()).addTransition(new AlphaInVisibility());
        excludeTarget(Layer.class, true);
        excludeTarget(BcSmartspaceDataPlugin.SmartspaceView.class, true);
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController != null) {
            Iterator<T> it = clockController.getLargeClock().getLayout().getViews().iterator();
            while (it.hasNext()) {
                excludeTarget((View) it.next(), true);
            }
            Iterator<T> it2 = clockController.getSmallClock().getLayout().getViews().iterator();
            while (it2.hasNext()) {
                excludeTarget((View) it2.next(), true);
            }
        }
    }
}
