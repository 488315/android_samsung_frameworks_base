package androidx.transition;

import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class TransitionPropagation {
    public abstract void captureValues(TransitionValues transitionValues);

    public abstract void getPropagationProperties();

    public abstract long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2);
}
