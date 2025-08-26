package android.animation;

import android.content.res.ConstantState;
import android.util.StateSet;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes.dex */
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
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: android.animation.StateListAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.setTarget(null);
                if (StateListAnimator.this.mRunningAnimator == animator) {
                    StateListAnimator.this.mRunningAnimator = null;
                }
            }
        };
    }

    public void addState(int[] iArr, Animator animator) {
        Tuple tuple = new Tuple(iArr, animator);
        tuple.mAnimator.addListener(this.mAnimatorListener);
        this.mTuples.add(tuple);
        this.mChangingConfigurations |= animator.getChangingConfigurations();
    }

    public Animator getRunningAnimator() {
        return this.mRunningAnimator;
    }

    public View getTarget() {
        WeakReference<View> weakReference = this.mViewRef;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public void setTarget(View view) {
        View target = getTarget();
        if (target == view) {
            return;
        }
        if (target != null) {
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
    public StateListAnimator m125clone() {
        try {
            StateListAnimator stateListAnimator = (StateListAnimator) super.clone();
            stateListAnimator.mTuples = new ArrayList<>(this.mTuples.size());
            stateListAnimator.mLastMatch = null;
            stateListAnimator.mRunningAnimator = null;
            stateListAnimator.mViewRef = null;
            stateListAnimator.mAnimatorListener = null;
            stateListAnimator.initAnimatorListener();
            int size = this.mTuples.size();
            for (int i = 0; i < size; i++) {
                Tuple tuple = this.mTuples.get(i);
                Animator animatorMo76clone = tuple.mAnimator.mo76clone();
                animatorMo76clone.removeListener(this.mAnimatorListener);
                stateListAnimator.addState(tuple.mSpecs, animatorMo76clone);
            }
            stateListAnimator.setChangingConfigurations(getChangingConfigurations());
            return stateListAnimator;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("cannot clone state list animator", e);
        }
    }

    public void setState(int[] iArr) {
        Tuple tuple;
        int size = this.mTuples.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                tuple = null;
                break;
            }
            tuple = this.mTuples.get(i);
            if (StateSet.stateSetMatches(tuple.mSpecs, iArr)) {
                break;
            } else {
                i++;
            }
        }
        Tuple tuple2 = this.mLastMatch;
        if (tuple == tuple2) {
            return;
        }
        if (tuple2 != null) {
            cancel();
        }
        this.mLastMatch = tuple;
        if (tuple != null) {
            start(tuple);
        }
    }

    private void start(Tuple tuple) {
        tuple.mAnimator.setTarget(getTarget());
        Animator animator = tuple.mAnimator;
        this.mRunningAnimator = animator;
        animator.start();
    }

    private void cancel() {
        Animator animator = this.mRunningAnimator;
        if (animator != null) {
            animator.cancel();
            this.mRunningAnimator = null;
        }
    }

    public ArrayList<Tuple> getTuples() {
        return this.mTuples;
    }

    public void jumpToCurrentState() {
        Animator animator = this.mRunningAnimator;
        if (animator != null) {
            animator.end();
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void setChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public void appendChangingConfigurations(int i) {
        this.mChangingConfigurations = i | this.mChangingConfigurations;
    }

    public ConstantState<StateListAnimator> createConstantState() {
        return new StateListAnimatorConstantState(this);
    }

    public static class Tuple {
        final Animator mAnimator;
        final int[] mSpecs;

        private Tuple(int[] iArr, Animator animator) {
            this.mSpecs = iArr;
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

        public StateListAnimatorConstantState(StateListAnimator stateListAnimator) {
            this.mAnimator = stateListAnimator;
            stateListAnimator.mConstantState = this;
            this.mChangingConf = stateListAnimator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public StateListAnimator newInstance2() {
            StateListAnimator stateListAnimatorM125clone = this.mAnimator.m125clone();
            stateListAnimatorM125clone.mConstantState = this;
            return stateListAnimatorM125clone;
        }
    }
}
