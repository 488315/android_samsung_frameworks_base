package com.android.internal.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class EpicenterTranslateClipReveal extends Visibility {
    private static final String PROPNAME_BOUNDS = "android:epicenterReveal:bounds";
    private static final String PROPNAME_CLIP = "android:epicenterReveal:clip";
    private static final String PROPNAME_TRANSLATE_X = "android:epicenterReveal:translateX";
    private static final String PROPNAME_TRANSLATE_Y = "android:epicenterReveal:translateY";
    private static final String PROPNAME_TRANSLATE_Z = "android:epicenterReveal:translateZ";
    private static final String PROPNAME_Z = "android:epicenterReveal:z";
    private final TimeInterpolator mInterpolatorX;
    private final TimeInterpolator mInterpolatorY;
    private final TimeInterpolator mInterpolatorZ;

    public EpicenterTranslateClipReveal() {
        this.mInterpolatorX = null;
        this.mInterpolatorY = null;
        this.mInterpolatorZ = null;
    }

    public EpicenterTranslateClipReveal(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EpicenterTranslateClipReveal, 0, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            this.mInterpolatorX = AnimationUtils.loadInterpolator(context, resourceId);
        } else {
            this.mInterpolatorX = TransitionConstants.LINEAR_OUT_SLOW_IN;
        }
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 != 0) {
            this.mInterpolatorY = AnimationUtils.loadInterpolator(context, resourceId2);
        } else {
            this.mInterpolatorY = TransitionConstants.FAST_OUT_SLOW_IN;
        }
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(2, 0);
        if (resourceId3 != 0) {
            this.mInterpolatorZ = AnimationUtils.loadInterpolator(context, resourceId3);
        } else {
            this.mInterpolatorZ = TransitionConstants.FAST_OUT_SLOW_IN;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put(PROPNAME_BOUNDS, new Rect(0, 0, view.getWidth(), view.getHeight()));
        transitionValues.values.put(PROPNAME_TRANSLATE_X, Float.valueOf(view.getTranslationX()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Y, Float.valueOf(view.getTranslationY()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Z, Float.valueOf(view.getTranslationZ()));
        transitionValues.values.put(PROPNAME_Z, Float.valueOf(view.getZ()));
        transitionValues.values.put(PROPNAME_CLIP, view.getClipBounds());
    }

    @Override // android.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        Rect epicenterOrCenter = getEpicenterOrCenter(rect);
        float fCenterX = epicenterOrCenter.centerX() - rect.centerX();
        float fCenterY = epicenterOrCenter.centerY() - rect.centerY();
        float fFloatValue = 0.0f - ((Float) transitionValues2.values.get(PROPNAME_Z)).floatValue();
        view.setTranslationX(fCenterX);
        view.setTranslationY(fCenterY);
        view.setTranslationZ(fFloatValue);
        float fFloatValue2 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float fFloatValue3 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float fFloatValue4 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        Rect bestRect = getBestRect(transitionValues2);
        Rect epicenterOrCenter2 = getEpicenterOrCenter(bestRect);
        view.setClipBounds(epicenterOrCenter2);
        return createRectAnimator(view, new State(epicenterOrCenter2.left, epicenterOrCenter2.right, fCenterX), new State(epicenterOrCenter2.top, epicenterOrCenter2.bottom, fCenterY), fFloatValue, new State(bestRect.left, bestRect.right, fFloatValue2), new State(bestRect.top, bestRect.bottom, fFloatValue3), fFloatValue4, transitionValues2, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    @Override // android.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        Rect epicenterOrCenter = getEpicenterOrCenter(rect);
        float fCenterX = epicenterOrCenter.centerX() - rect.centerX();
        float fCenterY = epicenterOrCenter.centerY() - rect.centerY();
        float fFloatValue = 0.0f - ((Float) transitionValues.values.get(PROPNAME_Z)).floatValue();
        float fFloatValue2 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float fFloatValue3 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float fFloatValue4 = ((Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        Rect bestRect = getBestRect(transitionValues);
        Rect epicenterOrCenter2 = getEpicenterOrCenter(bestRect);
        view.setClipBounds(bestRect);
        return createRectAnimator(view, new State(bestRect.left, bestRect.right, fFloatValue2), new State(bestRect.top, bestRect.bottom, fFloatValue3), fFloatValue4, new State(epicenterOrCenter2.left, epicenterOrCenter2.right, fCenterX), new State(epicenterOrCenter2.top, epicenterOrCenter2.bottom, fCenterY), fFloatValue, transitionValues2, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    private Rect getEpicenterOrCenter(Rect rect) {
        Rect epicenter = getEpicenter();
        if (epicenter != null) {
            return epicenter;
        }
        int iCenterX = rect.centerX();
        int iCenterY = rect.centerY();
        return new Rect(iCenterX, iCenterY, iCenterX, iCenterY);
    }

    private Rect getBestRect(TransitionValues transitionValues) {
        Rect rect = (Rect) transitionValues.values.get(PROPNAME_CLIP);
        return rect == null ? (Rect) transitionValues.values.get(PROPNAME_BOUNDS) : rect;
    }

    private static Animator createRectAnimator(final View view, State state, State state2, float f, State state3, State state4, float f2, TransitionValues transitionValues, TimeInterpolator timeInterpolator, TimeInterpolator timeInterpolator2, TimeInterpolator timeInterpolator3) {
        StateEvaluator stateEvaluator = new StateEvaluator();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, f, f2);
        if (timeInterpolator3 != null) {
            objectAnimatorOfFloat.setInterpolator(timeInterpolator3);
        }
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(view, new StateProperty(StateProperty.TARGET_X), stateEvaluator, state, state3);
        if (timeInterpolator != null) {
            objectAnimatorOfObject.setInterpolator(timeInterpolator);
        }
        ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(view, new StateProperty('y'), stateEvaluator, state2, state4);
        if (timeInterpolator2 != null) {
            objectAnimatorOfObject2.setInterpolator(timeInterpolator2);
        }
        final Rect rect = (Rect) transitionValues.values.get(PROPNAME_CLIP);
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.internal.transition.EpicenterTranslateClipReveal.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setClipBounds(rect);
            }
        };
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfObject, objectAnimatorOfObject2, objectAnimatorOfFloat);
        animatorSet.addListener(animatorListenerAdapter);
        return animatorSet;
    }

    private static class State {
        int lower;
        float trans;
        int upper;

        public State() {
        }

        public State(int i, int i2, float f) {
            this.lower = i;
            this.upper = i2;
            this.trans = f;
        }
    }

    private static class StateEvaluator implements TypeEvaluator<State> {
        private final State mTemp;

        private StateEvaluator() {
            this.mTemp = new State();
        }

        @Override // android.animation.TypeEvaluator
        public State evaluate(float f, State state, State state2) {
            this.mTemp.upper = state.upper + ((int) ((state2.upper - state.upper) * f));
            this.mTemp.lower = state.lower + ((int) ((state2.lower - state.lower) * f));
            this.mTemp.trans = state.trans + ((int) ((state2.trans - state.trans) * f));
            return this.mTemp;
        }
    }

    private static class StateProperty extends Property<View, State> {
        public static final char TARGET_X = 'x';
        public static final char TARGET_Y = 'y';
        private final int mTargetDimension;
        private final Rect mTempRect;
        private final State mTempState;

        public StateProperty(char c) {
            super(State.class, "state_" + c);
            this.mTempRect = new Rect();
            this.mTempState = new State();
            this.mTargetDimension = c;
        }

        @Override // android.util.Property
        public State get(View view) {
            Rect rect = this.mTempRect;
            if (!view.getClipBounds(rect)) {
                rect.setEmpty();
            }
            State state = this.mTempState;
            if (this.mTargetDimension == 120) {
                state.trans = view.getTranslationX();
                state.lower = rect.left + ((int) state.trans);
                state.upper = rect.right + ((int) state.trans);
                return state;
            }
            state.trans = view.getTranslationY();
            state.lower = rect.top + ((int) state.trans);
            state.upper = rect.bottom + ((int) state.trans);
            return state;
        }

        @Override // android.util.Property
        public void set(View view, State state) {
            Rect rect = this.mTempRect;
            if (view.getClipBounds(rect)) {
                if (this.mTargetDimension == 120) {
                    rect.left = state.lower - ((int) state.trans);
                    rect.right = state.upper - ((int) state.trans);
                } else {
                    rect.top = state.lower - ((int) state.trans);
                    rect.bottom = state.upper - ((int) state.trans);
                }
                view.setClipBounds(rect);
            }
            if (this.mTargetDimension == 120) {
                view.setTranslationX(state.trans);
            } else {
                view.setTranslationY(state.trans);
            }
        }
    }
}
