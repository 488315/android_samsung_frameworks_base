package androidx.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class TransitionSet extends Transition {
    public int mChangeFlags;
    public int mCurrentListeners;
    public boolean mPlayTogether;
    public boolean mStarted;
    public ArrayList mTransitions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionSetListener extends TransitionListenerAdapter {
        public final TransitionSet mTransitionSet;

        public TransitionSetListener(TransitionSet transitionSet) {
            this.mTransitionSet = transitionSet;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            TransitionSet transitionSet = this.mTransitionSet;
            int i = transitionSet.mCurrentListeners - 1;
            transitionSet.mCurrentListeners = i;
            if (i == 0) {
                transitionSet.mStarted = false;
                transitionSet.end();
            }
            transition.removeListener(this);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
            TransitionSet transitionSet = this.mTransitionSet;
            if (transitionSet.mStarted) {
                return;
            }
            transitionSet.start();
            transitionSet.mStarted = true;
        }
    }

    public TransitionSet() {
        this.mTransitions = new ArrayList();
        this.mPlayTogether = true;
        this.mStarted = false;
        this.mChangeFlags = 0;
    }

    @Override // androidx.transition.Transition
    public final void addListener(Transition.TransitionListener transitionListener) {
        super.addListener(transitionListener);
    }

    @Override // androidx.transition.Transition
    public final void addTarget(View view) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            ((Transition) this.mTransitions.get(i)).addTarget(view);
        }
        this.mTargets.add(view);
    }

    public final void addTransition(Transition transition) {
        this.mTransitions.add(transition);
        transition.mParent = this;
        long j = this.mDuration;
        if (j >= 0) {
            transition.setDuration(j);
        }
        if ((this.mChangeFlags & 1) != 0) {
            transition.setInterpolator(this.mInterpolator);
        }
        if ((this.mChangeFlags & 2) != 0) {
            transition.setPropagation(this.mPropagation);
        }
        if ((this.mChangeFlags & 4) != 0) {
            transition.setPathMotion(this.mPathMotion);
        }
        if ((this.mChangeFlags & 8) != 0) {
            transition.setEpicenterCallback(this.mEpicenterCallback);
        }
    }

    @Override // androidx.transition.Transition
    public final void cancel() {
        super.cancel();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).cancel();
        }
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (isValidTarget(view)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                Transition transition = (Transition) it.next();
                if (transition.isValidTarget(view)) {
                    transition.captureEndValues(transitionValues);
                    transitionValues.mTargetedTransitions.add(transition);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    public final void capturePropagationValues(TransitionValues transitionValues) {
        super.capturePropagationValues(transitionValues);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).capturePropagationValues(transitionValues);
        }
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (isValidTarget(view)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                Transition transition = (Transition) it.next();
                if (transition.isValidTarget(view)) {
                    transition.captureStartValues(transitionValues);
                    transitionValues.mTargetedTransitions.add(transition);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    public final void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList arrayList, ArrayList arrayList2) {
        long j = this.mStartDelay;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            Transition transition = (Transition) this.mTransitions.get(i);
            if (j > 0 && (this.mPlayTogether || i == 0)) {
                long j2 = transition.mStartDelay;
                if (j2 > 0) {
                    transition.setStartDelay(j2 + j);
                } else {
                    transition.setStartDelay(j);
                }
            }
            transition.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
        }
    }

    @Override // androidx.transition.Transition
    public final void pause(View view) {
        super.pause(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).pause(view);
        }
    }

    @Override // androidx.transition.Transition
    public final void removeListener(Transition.TransitionListener transitionListener) {
        super.removeListener(transitionListener);
    }

    @Override // androidx.transition.Transition
    public final void removeTarget(View view) {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            ((Transition) this.mTransitions.get(i)).removeTarget(view);
        }
        this.mTargets.remove(view);
    }

    @Override // androidx.transition.Transition
    public final void resume(View view) {
        super.resume(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).resume(view);
        }
    }

    @Override // androidx.transition.Transition
    public final void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            start();
            end();
            return;
        }
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            ((Transition) it.next()).addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
        if (this.mPlayTogether) {
            Iterator it2 = this.mTransitions.iterator();
            while (it2.hasNext()) {
                ((Transition) it2.next()).runAnimators();
            }
            return;
        }
        for (int i = 1; i < this.mTransitions.size(); i++) {
            Transition transition = (Transition) this.mTransitions.get(i - 1);
            final Transition transition2 = (Transition) this.mTransitions.get(i);
            transition.addListener(new TransitionListenerAdapter(this) { // from class: androidx.transition.TransitionSet.1
                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition3) {
                    transition2.runAnimators();
                    transition3.removeListener(this);
                }
            });
        }
        Transition transition3 = (Transition) this.mTransitions.get(0);
        if (transition3 != null) {
            transition3.runAnimators();
        }
    }

    @Override // androidx.transition.Transition
    /* renamed from: setDuration$1, reason: merged with bridge method [inline-methods] */
    public final void setDuration(long j) {
        ArrayList arrayList;
        this.mDuration = j;
        if (j < 0 || (arrayList = this.mTransitions) == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).setDuration(j);
        }
    }

    @Override // androidx.transition.Transition
    public final void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
        this.mChangeFlags |= 8;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).setEpicenterCallback(epicenterCallback);
        }
    }

    @Override // androidx.transition.Transition
    /* renamed from: setInterpolator$1, reason: merged with bridge method [inline-methods] */
    public final void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mChangeFlags |= 1;
        ArrayList arrayList = this.mTransitions;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((Transition) this.mTransitions.get(i)).setInterpolator(timeInterpolator);
            }
        }
        this.mInterpolator = timeInterpolator;
    }

    public final void setOrdering(int i) {
        if (i == 0) {
            this.mPlayTogether = true;
        } else {
            if (i != 1) {
                throw new AndroidRuntimeException(AbstractC0000x2c234b15.m0m("Invalid parameter for TransitionSet ordering: ", i));
            }
            this.mPlayTogether = false;
        }
    }

    @Override // androidx.transition.Transition
    public final void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.mChangeFlags |= 4;
        if (this.mTransitions != null) {
            for (int i = 0; i < this.mTransitions.size(); i++) {
                ((Transition) this.mTransitions.get(i)).setPathMotion(pathMotion);
            }
        }
    }

    @Override // androidx.transition.Transition
    public final void setPropagation(TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
        this.mChangeFlags |= 2;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).setPropagation(transitionPropagation);
        }
    }

    @Override // androidx.transition.Transition
    public final void setStartDelay(long j) {
        this.mStartDelay = j;
    }

    @Override // androidx.transition.Transition
    public final String toString(String str) {
        String transition = super.toString(str);
        for (int i = 0; i < this.mTransitions.size(); i++) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(transition, "\n");
            m2m.append(((Transition) this.mTransitions.get(i)).toString(str + "  "));
            transition = m2m.toString();
        }
        return transition;
    }

    @Override // androidx.transition.Transition
    /* renamed from: clone */
    public final Transition mo348clone() {
        TransitionSet transitionSet = (TransitionSet) super.mo348clone();
        transitionSet.mTransitions = new ArrayList();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            Transition mo348clone = ((Transition) this.mTransitions.get(i)).mo348clone();
            transitionSet.mTransitions.add(mo348clone);
            mo348clone.mParent = transitionSet;
        }
        return transitionSet;
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTransitions = new ArrayList();
        this.mPlayTogether = true;
        this.mStarted = false;
        this.mChangeFlags = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.TRANSITION_SET);
        setOrdering(TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionOrdering", 0, 0));
        obtainStyledAttributes.recycle();
    }
}
