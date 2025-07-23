package androidx.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TransitionSet extends Transition {
    public int mChangeFlags;
    public int mCurrentListeners;
    public boolean mPlayTogether;
    public boolean mStarted;
    public ArrayList mTransitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TransitionSetListener extends TransitionListenerAdapter {
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
            transition.setPropagation();
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
        if (isValidTarget(transitionValues.view)) {
            ArrayList arrayList = this.mTransitions;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Transition transition = (Transition) obj;
                if (transition.isValidTarget(transitionValues.view)) {
                    transition.captureEndValues(transitionValues);
                    transitionValues.mTargetedTransitions.add(transition);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    public final void capturePropagationValues(TransitionValues transitionValues) {
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).capturePropagationValues(transitionValues);
        }
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        if (isValidTarget(transitionValues.view)) {
            ArrayList arrayList = this.mTransitions;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Transition transition = (Transition) obj;
                if (transition.isValidTarget(transitionValues.view)) {
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

    public final Transition getTransitionAt(int i) {
        if (i < 0 || i >= this.mTransitions.size()) {
            return null;
        }
        return (Transition) this.mTransitions.get(i);
    }

    @Override // androidx.transition.Transition
    public final boolean hasAnimators() {
        for (int i = 0; i < this.mTransitions.size(); i++) {
            if (((Transition) this.mTransitions.get(i)).hasAnimators()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.transition.Transition
    public final boolean isSeekingSupported() {
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            if (!((Transition) this.mTransitions.get(i)).isSeekingSupported()) {
                return false;
            }
        }
        return true;
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
    public final void prepareAnimatorsForSeeking() {
        this.mTotalDuration = 0L;
        TransitionListenerAdapter transitionListenerAdapter = new TransitionListenerAdapter() { // from class: androidx.transition.TransitionSet.2
            @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                TransitionSet transitionSet = TransitionSet.this;
                transitionSet.mTransitions.remove(transition);
                if (transitionSet.hasAnimators()) {
                    return;
                }
                transitionSet.notifyFromTransition(transitionSet, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_CANCEL, false);
                transitionSet.mEnded = true;
                transitionSet.notifyFromTransition(transitionSet, Transition$TransitionNotification$$ExternalSyntheticLambda0.ON_END, false);
            }
        };
        for (int i = 0; i < this.mTransitions.size(); i++) {
            Transition transition = (Transition) this.mTransitions.get(i);
            transition.addListener(transitionListenerAdapter);
            transition.prepareAnimatorsForSeeking();
            long j = transition.mTotalDuration;
            if (this.mPlayTogether) {
                this.mTotalDuration = Math.max(this.mTotalDuration, j);
            } else {
                long j2 = this.mTotalDuration;
                transition.mSeekOffsetInParent = j2;
                this.mTotalDuration = j2 + j;
            }
        }
    }

    @Override // androidx.transition.Transition
    public final Transition removeListener(Transition.TransitionListener transitionListener) {
        super.removeListener(transitionListener);
        return this;
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
        ArrayList arrayList = this.mTransitions;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Transition) obj).addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
        if (this.mPlayTogether) {
            ArrayList arrayList2 = this.mTransitions;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((Transition) obj2).runAnimators();
            }
            return;
        }
        for (int i3 = 1; i3 < this.mTransitions.size(); i3++) {
            Transition transition = (Transition) this.mTransitions.get(i3 - 1);
            final Transition transition2 = (Transition) this.mTransitions.get(i3);
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    @Override // androidx.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCurrentPlayTimeMillis(long r20, long r22) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionSet.setCurrentPlayTimeMillis(long, long):void");
    }

    @Override // androidx.transition.Transition
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
                throw new AndroidRuntimeException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid parameter for TransitionSet ordering: "));
            }
            this.mPlayTogether = false;
        }
    }

    @Override // androidx.transition.Transition
    public final void setPathMotion(Transition.AnonymousClass1 anonymousClass1) {
        super.setPathMotion(anonymousClass1);
        this.mChangeFlags |= 4;
        if (this.mTransitions != null) {
            for (int i = 0; i < this.mTransitions.size(); i++) {
                ((Transition) this.mTransitions.get(i)).setPathMotion(anonymousClass1);
            }
        }
    }

    @Override // androidx.transition.Transition
    public final void setPropagation() {
        this.mChangeFlags |= 2;
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((Transition) this.mTransitions.get(i)).setPropagation();
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(transition, "\n");
            m.append(((Transition) this.mTransitions.get(i)).toString(str + "  "));
            transition = m.toString();
        }
        return transition;
    }

    @Override // androidx.transition.Transition
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final Transition mo898clone() {
        TransitionSet transitionSet = (TransitionSet) super.mo898clone();
        transitionSet.mTransitions = new ArrayList();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            Transition mo898clone = ((Transition) this.mTransitions.get(i)).mo898clone();
            transitionSet.mTransitions.add(mo898clone);
            mo898clone.mParent = transitionSet;
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
