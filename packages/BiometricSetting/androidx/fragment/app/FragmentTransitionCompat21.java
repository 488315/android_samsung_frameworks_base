package androidx.fragment.app;

import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import java.util.ArrayList;

/* loaded from: classes.dex */
final class FragmentTransitionCompat21 extends FragmentTransitionImpl {
    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final boolean canHandle(Object obj) {
        return obj instanceof Transition;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final Object mergeTransitionsInSequence() {
        return null;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void setListenerForTransitionEnd(Object obj, final DefaultSpecialEffectsController$$ExternalSyntheticLambda1 defaultSpecialEffectsController$$ExternalSyntheticLambda1) {
        ((Transition) obj).addListener(new Transition.TransitionListener() { // from class: androidx.fragment.app.FragmentTransitionCompat21.4
            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                defaultSpecialEffectsController$$ExternalSyntheticLambda1.run();
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionPause(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionResume(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public final void swapSharedElementTargets(ArrayList arrayList, ArrayList arrayList2) {
    }
}
