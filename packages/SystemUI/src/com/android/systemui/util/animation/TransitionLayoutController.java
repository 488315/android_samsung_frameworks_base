package com.android.systemui.util.animation;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import com.android.app.animation.Interpolators;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.animator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.util.animation.TransitionLayoutController$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.this$0.updateStateFromAnimation();
            }
        });
        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
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
        if ((i & 8) != 0) {
            j = 0;
        }
        if ((i & 16) != 0) {
            j2 = 0;
        }
        transitionLayoutController.setState(transitionViewState, z, z2, j, j2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateStateFromAnimation() {
        if (this.animationStartState == null || !this.animator.isRunning()) {
            return;
        }
        TransitionViewState transitionViewState = this.animationStartState;
        transitionViewState.getClass();
        TransitionViewState interpolatedState = getInterpolatedState(transitionViewState, this.state, this.animator.getAnimatedFraction(), this.currentState);
        this.currentState = interpolatedState;
        applyStateToLayout(interpolatedState);
    }

    public final void attach(TransitionLayout transitionLayout) {
        this.transitionLayout = transitionLayout;
    }

    public final TransitionViewState getGoneState(TransitionViewState transitionViewState, DisappearParameters disappearParameters, float f, TransitionViewState transitionViewState2) {
        float fConstrain = MathUtils.constrain(MathUtils.map(disappearParameters.getDisappearStart(), disappearParameters.getDisappearEnd(), 0.0f, 1.0f, f), 0.0f, 1.0f);
        TransitionViewState transitionViewStateCopy = transitionViewState.copy(transitionViewState2);
        transitionViewStateCopy.setWidth((int) MathUtils.lerp(transitionViewState.getWidth(), transitionViewState.getWidth() * disappearParameters.getDisappearSize().x, fConstrain));
        transitionViewStateCopy.setHeight((int) MathUtils.lerp(transitionViewState.getHeight(), transitionViewState.getHeight() * disappearParameters.getDisappearSize().y, fConstrain));
        transitionViewStateCopy.getTranslation().x = (transitionViewState.getWidth() - transitionViewStateCopy.getWidth()) * disappearParameters.getGonePivot().x;
        transitionViewStateCopy.getTranslation().y = (transitionViewState.getHeight() - transitionViewStateCopy.getHeight()) * disappearParameters.getGonePivot().y;
        transitionViewStateCopy.getContentTranslation().x = (disappearParameters.getContentTranslationFraction().x - 1.0f) * transitionViewStateCopy.getTranslation().x;
        transitionViewStateCopy.getContentTranslation().y = (disappearParameters.getContentTranslationFraction().y - 1.0f) * transitionViewStateCopy.getTranslation().y;
        transitionViewStateCopy.setAlpha(MathUtils.constrain(MathUtils.map(disappearParameters.getFadeStartPosition(), 1.0f, 1.0f, 0.0f, fConstrain), 0.0f, 1.0f));
        return transitionViewStateCopy;
    }

    public final TransitionViewState getInterpolatedState(TransitionViewState transitionViewState, TransitionViewState transitionViewState2, float f, TransitionViewState transitionViewState3) {
        WidgetState widgetState;
        TransitionLayout transitionLayout;
        int i;
        int measureWidth;
        int measureHeight;
        float fLerp;
        float fLerp2;
        float fLerp3;
        float f2;
        float map;
        boolean z;
        float f3;
        TransitionLayoutController transitionLayoutController = this;
        TransitionViewState transitionViewState4 = transitionViewState3 == null ? new TransitionViewState() : transitionViewState3;
        TransitionLayout transitionLayout2 = transitionLayoutController.transitionLayout;
        if (transitionLayout2 == null) {
            return transitionViewState4;
        }
        int childCount = transitionLayout2.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            int id = transitionLayout2.getChildAt(i2).getId();
            WidgetState widgetState2 = transitionViewState4.getWidgetStates().get(Integer.valueOf(id));
            if (widgetState2 == null) {
                widgetState2 = new WidgetState(0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 511, null);
            }
            WidgetState widgetState3 = transitionViewState.getWidgetStates().get(Integer.valueOf(id));
            if (widgetState3 == null || (widgetState = transitionViewState2.getWidgetStates().get(Integer.valueOf(id))) == null) {
                transitionLayout = transitionLayout2;
                i = childCount;
            } else {
                if (widgetState3.getGone() != widgetState.getGone()) {
                    fLerp = 1.0f;
                    if (widgetState3.getGone()) {
                        measureWidth = widgetState.getMeasureWidth();
                        measureHeight = widgetState.getMeasureHeight();
                        if (transitionLayoutController.isGutsAnimation) {
                            map = MathUtils.map(0.286f, 1.0f, 0.0f, 1.0f, f);
                            z = f < 0.286f;
                            fLerp2 = widgetState3.getX();
                            fLerp3 = widgetState3.getY();
                            transitionLayout = transitionLayout2;
                            i = childCount;
                            f3 = 1.0f;
                        } else {
                            map = MathUtils.map(0.8f, 1.0f, 0.0f, 1.0f, f);
                            z = f < 0.8f;
                            float scale = widgetState.getScale();
                            fLerp = MathUtils.lerp(scale * 0.8f, scale, f);
                            transitionLayout = transitionLayout2;
                            fLerp2 = MathUtils.lerp(widgetState3.getX() - (measureWidth / 2.0f), widgetState.getX(), f);
                            i = childCount;
                            fLerp3 = MathUtils.lerp(widgetState3.getY() - (measureHeight / 2.0f), widgetState.getY(), f);
                            f3 = 1.0f;
                        }
                    } else {
                        transitionLayout = transitionLayout2;
                        i = childCount;
                        measureWidth = widgetState3.getMeasureWidth();
                        measureHeight = widgetState3.getMeasureHeight();
                        if (transitionLayoutController.isGutsAnimation) {
                            float map2 = MathUtils.map(0.0f, 0.355f, 0.0f, 1.0f, f);
                            z = f > 0.355f;
                            fLerp2 = widgetState.getX();
                            fLerp3 = widgetState.getY();
                            f3 = 0.0f;
                            map = map2;
                            fLerp = 1.0f;
                        } else {
                            float map3 = MathUtils.map(0.0f, 0.19999999f, 0.0f, 1.0f, f);
                            z = f > 0.19999999f;
                            float scale2 = widgetState3.getScale();
                            fLerp = MathUtils.lerp(scale2, scale2 * 0.8f, f);
                            f3 = 0.0f;
                            fLerp2 = MathUtils.lerp(widgetState3.getX(), widgetState.getX() - (measureWidth / 2.0f), f);
                            fLerp3 = MathUtils.lerp(widgetState3.getY(), widgetState.getY() - (measureHeight / 2.0f), f);
                            map = map3;
                        }
                    }
                    widgetState2.setGone(z);
                    f2 = f3;
                } else {
                    transitionLayout = transitionLayout2;
                    i = childCount;
                    widgetState2.setGone(widgetState3.getGone());
                    measureWidth = widgetState.getMeasureWidth();
                    measureHeight = widgetState.getMeasureHeight();
                    fLerp = MathUtils.lerp(widgetState3.getScale(), widgetState.getScale(), f);
                    fLerp2 = MathUtils.lerp(widgetState3.getX(), widgetState.getX(), f);
                    fLerp3 = MathUtils.lerp(widgetState3.getY(), widgetState.getY(), f);
                    f2 = f;
                    map = f2;
                }
                float f4 = fLerp3;
                widgetState2.setX(fLerp2);
                widgetState2.setY(f4);
                widgetState2.setAlpha(MathUtils.lerp(widgetState3.getAlpha(), widgetState.getAlpha(), map));
                widgetState2.setWidth((int) MathUtils.lerp(widgetState3.getWidth(), widgetState.getWidth(), f2));
                widgetState2.setHeight((int) MathUtils.lerp(widgetState3.getHeight(), widgetState.getHeight(), f2));
                widgetState2.setScale(fLerp);
                widgetState2.setMeasureWidth(measureWidth);
                widgetState2.setMeasureHeight(measureHeight);
                transitionViewState4.getWidgetStates().put(Integer.valueOf(id), widgetState2);
            }
            i2++;
            transitionLayoutController = this;
            transitionLayout2 = transitionLayout;
            childCount = i;
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
        if (transitionLayout != null) {
            transitionLayout.setMeasureState(transitionViewState);
        }
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
