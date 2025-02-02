package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import java.util.ArrayList;

@SuppressLint({"UnknownNullness"})
/* loaded from: classes.dex */
public abstract class FragmentTransitionImpl {
    public abstract void beginDelayedTransition(ViewGroup viewGroup, Object obj);

    public abstract boolean canHandle(Object obj);

    public abstract Object mergeTransitionsInSequence();

    public void setListenerForTransitionEnd(Object obj, DefaultSpecialEffectsController$$ExternalSyntheticLambda1 defaultSpecialEffectsController$$ExternalSyntheticLambda1) {
        defaultSpecialEffectsController$$ExternalSyntheticLambda1.run();
    }

    public abstract void swapSharedElementTargets(ArrayList arrayList, ArrayList arrayList2);
}
