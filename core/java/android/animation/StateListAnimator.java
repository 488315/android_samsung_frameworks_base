package android.animation;

import android.content.res.ConstantState;
import android.util.StateSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class StateListAnimator implements Cloneable {
    private AnimatorListenerAdapter mAnimatorListener;
    private int mChangingConfigurations;
    private StateListAnimatorConstantState mConstantState;
    private WeakReference<View> mViewRef;
    private ArrayList<Tuple> mTuples = new ArrayList<>();
    private Tuple mLastMatch = null;
    private Animator mRunningAnimator = null;

    public StateListAnimator() {
        initAnimatorListener();
    }

    private void initAnimatorListener() {
        this.mAnimatorListener =
                new AnimatorListenerAdapter() { // from class: android.animation.StateListAnimator.1
                    @Override // android.animation.AnimatorListenerAdapter,
                    // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        animation.setTarget(null);
                        if (StateListAnimator.this.mRunningAnimator == animation) {
                            StateListAnimator.this.mRunningAnimator = null;
                        }
                    }
                };
    }

    public void addState(int[] specs, Animator animator) {
        Tuple tuple = new Tuple(specs, animator);
        tuple.mAnimator.addListener(this.mAnimatorListener);
        this.mTuples.add(tuple);
        this.mChangingConfigurations |= animator.getChangingConfigurations();
    }

    public Animator getRunningAnimator() {
        return this.mRunningAnimator;
    }

    public View getTarget() {
        if (this.mViewRef == null) {
            return null;
        }
        return this.mViewRef.get();
    }

    public void setTarget(View view) {
        View current = getTarget();
        if (current == view) {
            return;
        }
        if (current != null) {
            clearTarget();
        }
        if (view != null) {
            this.mViewRef = new WeakReference<>(view);
        }
    }

    private void clearTarget() {
        int size = this.mTuples.size();
        for (int i = 0; i < size; i++) {
            this.mTuples.get(i).mAnimator.setTarget(null);
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimator = null;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public StateListAnimator m126clone() {
        try {
            StateListAnimator clone = (StateListAnimator) super.clone();
            clone.mTuples = new ArrayList<>(this.mTuples.size());
            clone.mLastMatch = null;
            clone.mRunningAnimator = null;
            clone.mViewRef = null;
            clone.mAnimatorListener = null;
            clone.initAnimatorListener();
            int tupleSize = this.mTuples.size();
            for (int i = 0; i < tupleSize; i++) {
                Tuple tuple = this.mTuples.get(i);
                Animator animatorClone = tuple.mAnimator.mo77clone();
                animatorClone.removeListener(this.mAnimatorListener);
                clone.addState(tuple.mSpecs, animatorClone);
            }
            int i2 = getChangingConfigurations();
            clone.setChangingConfigurations(i2);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("cannot clone state list animator", e);
        }
    }

    public void setState(int[] state) {
        Tuple match = null;
        int count = this.mTuples.size();
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            Tuple tuple = this.mTuples.get(i);
            if (!StateSet.stateSetMatches(tuple.mSpecs, state)) {
                i++;
            } else {
                match = tuple;
                break;
            }
        }
        if (match == this.mLastMatch) {
            return;
        }
        if (this.mLastMatch != null) {
            cancel();
        }
        this.mLastMatch = match;
        if (match != null) {
            start(match);
        }
    }

    private void start(Tuple match) {
        match.mAnimator.setTarget(getTarget());
        this.mRunningAnimator = match.mAnimator;
        this.mRunningAnimator.start();
    }

    private void cancel() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.cancel();
            this.mRunningAnimator = null;
        }
    }

    public ArrayList<Tuple> getTuples() {
        return this.mTuples;
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.end();
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void setChangingConfigurations(int configs) {
        this.mChangingConfigurations = configs;
    }

    public void appendChangingConfigurations(int configs) {
        this.mChangingConfigurations |= configs;
    }

    public ConstantState<StateListAnimator> createConstantState() {
        return new StateListAnimatorConstantState(this);
    }

    public static class Tuple {
        final Animator mAnimator;
        final int[] mSpecs;

        private Tuple(int[] specs, Animator animator) {
            this.mSpecs = specs;
            this.mAnimator = animator;
        }

        public int[] getSpecs() {
            return this.mSpecs;
        }

        public Animator getAnimator() {
            return this.mAnimator;
        }
    }

    private static class StateListAnimatorConstantState extends ConstantState<StateListAnimator> {
        final StateListAnimator mAnimator;
        int mChangingConf;

        public StateListAnimatorConstantState(StateListAnimator animator) {
            this.mAnimator = animator;
            this.mAnimator.mConstantState = this;
            this.mChangingConf = this.mAnimator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public StateListAnimator newInstance2() {
            StateListAnimator clone = this.mAnimator.m126clone();
            clone.mConstantState = this;
            return clone;
        }
    }
}
