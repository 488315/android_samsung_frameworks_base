package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.SpecialEffectsController;

/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) this.f$0;
                SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) this.f$1;
                DefaultSpecialEffectsController.TransitionEffect transitionEffect = (DefaultSpecialEffectsController.TransitionEffect) this.f$2;
                Fragment fragment = operation.fragment;
                Fragment fragment2 = operation2.fragment;
                boolean z = transitionEffect.isPop;
                FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
                if (!z) {
                    fragment.getClass();
                    break;
                } else {
                    fragment2.getClass();
                    break;
                }
            case 1:
                ViewGroup viewGroup = (ViewGroup) this.f$0;
                View view = (View) this.f$1;
                DefaultSpecialEffectsController.AnimationEffect animationEffect = (DefaultSpecialEffectsController.AnimationEffect) this.f$2;
                viewGroup.endViewTransition(view);
                animationEffect.animationInfo.operation.completeEffect(animationEffect);
                break;
            default:
                FragmentTransitionImpl fragmentTransitionImpl = (FragmentTransitionImpl) this.f$0;
                View view2 = (View) this.f$1;
                Rect rect = (Rect) this.f$2;
                fragmentTransitionImpl.getClass();
                FragmentTransitionImpl.getBoundsOnScreen(rect, view2);
                break;
        }
    }
}
