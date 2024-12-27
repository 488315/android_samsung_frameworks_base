package com.android.systemui.util.animation;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import com.android.app.animation.Interpolators;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public class TransitionLayoutController {
    public static final int $stable = 8;
    private TransitionViewState animationStartState;
    private ValueAnimator animator;
    private int currentHeight;
    private int currentWidth;
    private boolean isGutsAnimation;
    private Function2 sizeChangedListener;
    private TransitionLayout transitionLayout;
    private TransitionViewState currentState = new TransitionViewState();
    private TransitionViewState state = new TransitionViewState();

    public TransitionLayoutController() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.animator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.util.animation.TransitionLayoutController$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TransitionLayoutController.this.updateStateFromAnimation();
            }
        });
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
    }

    private final void applyStateToLayout(TransitionViewState transitionViewState) {
        TransitionLayout transitionLayout = this.transitionLayout;
        if (transitionLayout != null) {
            transitionLayout.setState(transitionViewState);
        }
        if (this.currentHeight == transitionViewState.getHeight() && this.currentWidth == transitionViewState.getWidth()) {
            return;
        }
        this.currentHeight = transitionViewState.getHeight();
        int width = transitionViewState.getWidth();
        this.currentWidth = width;
        Function2 function2 = this.sizeChangedListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(width), Integer.valueOf(this.currentHeight));
        }
    }

    public static /* synthetic */ TransitionViewState getGoneState$default(TransitionLayoutController transitionLayoutController, TransitionViewState transitionViewState, DisappearParameters disappearParameters, float f, TransitionViewState transitionViewState2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getGoneState");
        }
        if ((i & 8) != 0) {
            transitionViewState2 = null;
        }
        return transitionLayoutController.getGoneState(transitionViewState, disappearParameters, f, transitionViewState2);
    }

    public static /* synthetic */ TransitionViewState getInterpolatedState$default(TransitionLayoutController transitionLayoutController, TransitionViewState transitionViewState, TransitionViewState transitionViewState2, float f, TransitionViewState transitionViewState3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getInterpolatedState");
        }
        if ((i & 8) != 0) {
            transitionViewState3 = null;
        }
        return transitionLayoutController.getInterpolatedState(transitionViewState, transitionViewState2, f, transitionViewState3);
    }

    public static /* synthetic */ void setState$default(TransitionLayoutController transitionLayoutController, TransitionViewState transitionViewState, boolean z, boolean z2, long j, long j2, boolean z3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setState");
        }
        transitionLayoutController.setState(transitionViewState, z, z2, (i & 8) != 0 ? 0L : j, (i & 16) != 0 ? 0L : j2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateStateFromAnimation() {
        if (this.animationStartState == null || !this.animator.isRunning()) {
            return;
        }
        TransitionViewState transitionViewState = this.animationStartState;
        Intrinsics.checkNotNull(transitionViewState);
        TransitionViewState interpolatedState = getInterpolatedState(transitionViewState, this.state, this.animator.getAnimatedFraction(), this.currentState);
        this.currentState = interpolatedState;
        applyStateToLayout(interpolatedState);
    }

    public final void attach(TransitionLayout transitionLayout) {
        this.transitionLayout = transitionLayout;
    }

    public final TransitionViewState getGoneState(TransitionViewState transitionViewState, DisappearParameters disappearParameters, float f, TransitionViewState transitionViewState2) {
        float constrain = MathUtils.constrain(MathUtils.map(disappearParameters.getDisappearStart(), disappearParameters.getDisappearEnd(), 0.0f, 1.0f, f), 0.0f, 1.0f);
        TransitionViewState copy = transitionViewState.copy(transitionViewState2);
        copy.setWidth((int) MathUtils.lerp(transitionViewState.getWidth(), transitionViewState.getWidth() * disappearParameters.getDisappearSize().x, constrain));
        copy.setHeight((int) MathUtils.lerp(transitionViewState.getHeight(), transitionViewState.getHeight() * disappearParameters.getDisappearSize().y, constrain));
        copy.getTranslation().x = (transitionViewState.getWidth() - copy.getWidth()) * disappearParameters.getGonePivot().x;
        copy.getTranslation().y = (transitionViewState.getHeight() - copy.getHeight()) * disappearParameters.getGonePivot().y;
        copy.getContentTranslation().x = (disappearParameters.getContentTranslationFraction().x - 1.0f) * copy.getTranslation().x;
        copy.getContentTranslation().y = (disappearParameters.getContentTranslationFraction().y - 1.0f) * copy.getTranslation().y;
        copy.setAlpha(MathUtils.constrain(MathUtils.map(disappearParameters.getFadeStartPosition(), 1.0f, 1.0f, 0.0f, constrain), 0.0f, 1.0f));
        return copy;
    }

    public final TransitionViewState getInterpolatedState(TransitionViewState transitionViewState, TransitionViewState transitionViewState2, float f, TransitionViewState transitionViewState3) {
        WidgetState widgetState;
        TransitionLayout transitionLayout;
        int measureWidth;
        int measureHeight;
        float lerp;
        float lerp2;
        float lerp3;
        float f2;
        float f3;
        boolean z;
        float f4;
        TransitionLayoutController transitionLayoutController = this;
        TransitionViewState transitionViewState4 = transitionViewState3 == null ? new TransitionViewState() : transitionViewState3;
        TransitionLayout transitionLayout2 = transitionLayoutController.transitionLayout;
        if (transitionLayout2 == null) {
            return transitionViewState4;
        }
        int childCount = transitionLayout2.getChildCount();
        int i = 0;
        while (i < childCount) {
            int id = transitionLayout2.getChildAt(i).getId();
            WidgetState widgetState2 = transitionViewState4.getWidgetStates().get(Integer.valueOf(id));
            if (widgetState2 == null) {
                widgetState2 = new WidgetState(0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 511, null);
            }
            WidgetState widgetState3 = transitionViewState.getWidgetStates().get(Integer.valueOf(id));
            if (widgetState3 == null || (widgetState = transitionViewState2.getWidgetStates().get(Integer.valueOf(id))) == null) {
                transitionLayout = transitionLayout2;
            } else {
                if (widgetState3.getGone() != widgetState.getGone()) {
                    lerp = 1.0f;
                    if (widgetState3.getGone()) {
                        measureWidth = widgetState.getMeasureWidth();
                        measureHeight = widgetState.getMeasureHeight();
                        if (transitionLayoutController.isGutsAnimation) {
                            f3 = MathUtils.map(0.286f, 1.0f, 0.0f, 1.0f, f);
                            boolean z2 = f < 0.286f;
                            lerp2 = widgetState3.getX();
                            lerp3 = widgetState3.getY();
                            transitionLayout = transitionLayout2;
                            f4 = 1.0f;
                            z = z2;
                        } else {
                            f3 = MathUtils.map(0.8f, 1.0f, 0.0f, 1.0f, f);
                            boolean z3 = f < 0.8f;
                            float scale = widgetState.getScale();
                            float lerp4 = MathUtils.lerp(0.8f * scale, scale, f);
                            float lerp5 = MathUtils.lerp(widgetState3.getX() - (measureWidth / 2.0f), widgetState.getX(), f);
                            transitionLayout = transitionLayout2;
                            lerp3 = MathUtils.lerp(widgetState3.getY() - (measureHeight / 2.0f), widgetState.getY(), f);
                            z = z3;
                            f4 = 1.0f;
                            lerp2 = lerp5;
                            lerp = lerp4;
                        }
                    } else {
                        transitionLayout = transitionLayout2;
                        measureWidth = widgetState3.getMeasureWidth();
                        measureHeight = widgetState3.getMeasureHeight();
                        if (transitionLayoutController.isGutsAnimation) {
                            lerp = 1.0f;
                            float map = MathUtils.map(0.0f, 0.355f, 0.0f, 1.0f, f);
                            boolean z4 = f > 0.355f;
                            float x = widgetState.getX();
                            lerp3 = widgetState.getY();
                            f4 = 0.0f;
                            f3 = map;
                            z = z4;
                            lerp2 = x;
                        } else {
                            float map2 = MathUtils.map(0.0f, 0.19999999f, 0.0f, 1.0f, f);
                            boolean z5 = f > 0.19999999f;
                            float scale2 = widgetState3.getScale();
                            float lerp6 = MathUtils.lerp(scale2, 0.8f * scale2, f);
                            lerp2 = MathUtils.lerp(widgetState3.getX(), widgetState.getX() - (measureWidth / 2.0f), f);
                            lerp3 = MathUtils.lerp(widgetState3.getY(), widgetState.getY() - (measureHeight / 2.0f), f);
                            f3 = map2;
                            z = z5;
                            f4 = 0.0f;
                            lerp = lerp6;
                        }
                    }
                    widgetState2.setGone(z);
                    f2 = f4;
                } else {
                    transitionLayout = transitionLayout2;
                    widgetState2.setGone(widgetState3.getGone());
                    measureWidth = widgetState.getMeasureWidth();
                    measureHeight = widgetState.getMeasureHeight();
                    lerp = MathUtils.lerp(widgetState3.getScale(), widgetState.getScale(), f);
                    lerp2 = MathUtils.lerp(widgetState3.getX(), widgetState.getX(), f);
                    lerp3 = MathUtils.lerp(widgetState3.getY(), widgetState.getY(), f);
                    f2 = f;
                    f3 = f2;
                }
                widgetState2.setX(lerp2);
                widgetState2.setY(lerp3);
                widgetState2.setAlpha(MathUtils.lerp(widgetState3.getAlpha(), widgetState.getAlpha(), f3));
                widgetState2.setWidth((int) MathUtils.lerp(widgetState3.getWidth(), widgetState.getWidth(), f2));
                widgetState2.setHeight((int) MathUtils.lerp(widgetState3.getHeight(), widgetState.getHeight(), f2));
                widgetState2.setScale(lerp);
                widgetState2.setMeasureWidth(measureWidth);
                widgetState2.setMeasureHeight(measureHeight);
                transitionViewState4.getWidgetStates().put(Integer.valueOf(id), widgetState2);
            }
            i++;
            transitionLayoutController = this;
            transitionLayout2 = transitionLayout;
        }
        transitionViewState4.setWidth((int) MathUtils.lerp(transitionViewState.getWidth(), transitionViewState2.getWidth(), f));
        transitionViewState4.setHeight((int) MathUtils.lerp(transitionViewState.getHeight(), transitionViewState2.getHeight(), f));
        if (f == 0.0f) {
            transitionViewState4.setMeasureWidth(transitionViewState.getMeasureWidth());
            transitionViewState4.setMeasureHeight(transitionViewState.getMeasureHeight());
        } else {
            transitionViewState4.setMeasureWidth(transitionViewState2.getMeasureWidth());
            transitionViewState4.setMeasureHeight(transitionViewState2.getMeasureHeight());
        }
        transitionViewState4.getTranslation().x = MathUtils.lerp(transitionViewState.getTranslation().x, transitionViewState2.getTranslation().x, f);
        transitionViewState4.getTranslation().y = MathUtils.lerp(transitionViewState.getTranslation().y, transitionViewState2.getTranslation().y, f);
        transitionViewState4.setAlpha(MathUtils.lerp(transitionViewState.getAlpha(), transitionViewState2.getAlpha(), f));
        transitionViewState4.getContentTranslation().x = MathUtils.lerp(transitionViewState.getContentTranslation().x, transitionViewState2.getContentTranslation().x, f);
        transitionViewState4.getContentTranslation().y = MathUtils.lerp(transitionViewState.getContentTranslation().y, transitionViewState2.getContentTranslation().y, f);
        return transitionViewState4;
    }

    public final Function2 getSizeChangedListener() {
        return this.sizeChangedListener;
    }

    public final void setMeasureState(TransitionViewState transitionViewState) {
        TransitionLayout transitionLayout = this.transitionLayout;
        if (transitionLayout == null) {
            return;
        }
        transitionLayout.setMeasureState(transitionViewState);
    }

    public final void setSizeChangedListener(Function2 function2) {
        this.sizeChangedListener = function2;
    }

    public final void setState(TransitionViewState transitionViewState, boolean z, boolean z2, long j, long j2, boolean z3) {
        this.isGutsAnimation = z3;
        boolean z4 = z2 && this.currentState.getWidth() != 0;
        this.state = TransitionViewState.copy$default(transitionViewState, null, 1, null);
        if (z || this.transitionLayout == null) {
            this.animator.cancel();
            applyStateToLayout(this.state);
            this.currentState = transitionViewState.copy(this.currentState);
        } else {
            if (!z4) {
                if (this.animator.isRunning()) {
                    return;
                }
                applyStateToLayout(this.state);
                this.currentState = transitionViewState.copy(this.currentState);
                return;
            }
            this.animationStartState = TransitionViewState.copy$default(this.currentState, null, 1, null);
            this.animator.setDuration(j);
            this.animator.setStartDelay(j2);
            this.animator.setInterpolator(this.isGutsAnimation ? Interpolators.LINEAR : Interpolators.FAST_OUT_SLOW_IN);
            this.animator.start();
        }
    }
}
