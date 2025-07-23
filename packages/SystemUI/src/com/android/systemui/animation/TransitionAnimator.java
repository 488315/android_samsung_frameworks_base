package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.animation.Interpolator;
import android.window.WindowAnimationState;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.systemui.animation.TransitionAnimator;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransitionAnimator {
    public final float[] cornerRadii;
    public final Interpolators interpolators;
    public final Executor mainExecutor;
    public final Interpolators springInterpolators;
    public final SpringParams springParams;
    public final SpringTimings springTimings;
    public final Timings timings;
    public final int[] transitionContainerLocation;
    public static final Companion Companion = new Companion(null);
    public static final PorterDuffXfermode SRC_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    public static final SpringParams DEFAULT_SPRING_PARAMS = new SpringParams(450.0f, 0.965f, 400.0f, 0.95f, 500.0f, 0.99f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Animation {
        void cancel();

        void start();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static float getProgress(Timings timings, float f, long j, long j2) {
            return getProgressInternal(timings.totalDuration, f, j, j2);
        }

        public static float getProgressInternal(float f, float f2, float f3, float f4) {
            return MathUtils.constrain(((f2 * f) - f3) / f4, 0.0f, 1.0f);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InterpolatedAnimation implements Animation {
        public final Animator animator;

        public InterpolatedAnimation(Animator animator) {
            this.animator = animator;
        }

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public final void cancel() {
            this.animator.cancel();
        }

        public final Animator getAnimator() {
            return this.animator;
        }

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public final void start() {
            this.animator.start();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MultiSpringAnimation implements Animation {
        public final Runnable onAnimationStart;
        public final SpringAnimation springScale;
        public final SpringState springState;
        public final SpringAnimation springX;
        public final SpringAnimation springY;
        public final long startFrameTime;

        public MultiSpringAnimation(SpringAnimation springAnimation, SpringAnimation springAnimation2, SpringAnimation springAnimation3, SpringState springState, long j, Runnable runnable) {
            this.springX = springAnimation;
            this.springY = springAnimation2;
            this.springScale = springAnimation3;
            this.springState = springState;
            this.startFrameTime = j;
            this.onAnimationStart = runnable;
        }

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public final void cancel() {
            this.springX.cancel();
            this.springY.cancel();
            this.springScale.cancel();
        }

        public final SpringAnimation getSpringScale() {
            return this.springScale;
        }

        public final SpringAnimation getSpringX() {
            return this.springX;
        }

        public final SpringAnimation getSpringY() {
            return this.springY;
        }

        public final void initAndStartSprings(long j) {
            SpringAnimation springAnimation = this.springX;
            long j2 = this.startFrameTime;
            springAnimation.doAnimationFrame(j2);
            this.springY.doAnimationFrame(j2);
            this.springScale.doAnimationFrame(j2);
            this.springX.doAnimationFrame(j);
            this.springY.doAnimationFrame(j);
            this.springScale.doAnimationFrame(j);
            this.springX.start();
            this.springY.start();
            this.springScale.start();
        }

        public final boolean isDone() {
            SpringState springState = this.springState;
            return springState.isCenterXDone && springState.isCenterYDone && springState.isScaleDone;
        }

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public final void start() {
            this.onAnimationStart.run();
            if (this.startFrameTime < 0) {
                this.springX.start();
                this.springY.start();
                this.springScale.start();
            } else {
                try {
                    initAndStartSprings(Choreographer.getInstance().getFrameTime());
                } catch (IllegalStateException unused) {
                    Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.systemui.animation.TransitionAnimator$MultiSpringAnimation$start$2
                        @Override // android.view.Choreographer.FrameCallback
                        public final void doFrame(long j) {
                            TransitionAnimator.MultiSpringAnimation.this.initAndStartSprings(j / 1000000);
                        }
                    });
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpringParams {
        public final float centerXDampingRatio;
        public final float centerXStiffness;
        public final float centerYDampingRatio;
        public final float centerYStiffness;
        public final float scaleDampingRatio;
        public final float scaleStiffness;

        public SpringParams(float f, float f2, float f3, float f4, float f5, float f6) {
            this.centerXStiffness = f;
            this.centerXDampingRatio = f2;
            this.centerYStiffness = f3;
            this.centerYDampingRatio = f4;
            this.scaleStiffness = f5;
            this.scaleDampingRatio = f6;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpringParams)) {
                return false;
            }
            SpringParams springParams = (SpringParams) obj;
            return Float.compare(this.centerXStiffness, springParams.centerXStiffness) == 0 && Float.compare(this.centerXDampingRatio, springParams.centerXDampingRatio) == 0 && Float.compare(this.centerYStiffness, springParams.centerYStiffness) == 0 && Float.compare(this.centerYDampingRatio, springParams.centerYDampingRatio) == 0 && Float.compare(this.scaleStiffness, springParams.scaleStiffness) == 0 && Float.compare(this.scaleDampingRatio, springParams.scaleDampingRatio) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.scaleDampingRatio) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleStiffness, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerYDampingRatio, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerYStiffness, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerXDampingRatio, Float.hashCode(this.centerXStiffness) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SpringParams(centerXStiffness=");
            sb.append(this.centerXStiffness);
            sb.append(", centerXDampingRatio=");
            sb.append(this.centerXDampingRatio);
            sb.append(", centerYStiffness=");
            sb.append(this.centerYStiffness);
            sb.append(", centerYDampingRatio=");
            sb.append(this.centerYDampingRatio);
            sb.append(", scaleStiffness=");
            sb.append(this.scaleStiffness);
            sb.append(", scaleDampingRatio=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.scaleDampingRatio, ")", sb);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    abstract class SpringProperty {
        public static final /* synthetic */ SpringProperty[] $VALUES;
        public static final CENTER_X CENTER_X;
        public static final CENTER_Y CENTER_Y;
        public static final SCALE SCALE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        final class CENTER_X extends SpringProperty {
            public CENTER_X(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final float get(SpringState springState) {
                return springState.centerX;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final void setValue(SpringState springState, float f) {
                springState.centerX = f;
                springState.isCenterXUpdated = true;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        final class CENTER_Y extends SpringProperty {
            public CENTER_Y(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final float get(SpringState springState) {
                return springState.centerY;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final void setValue(SpringState springState, float f) {
                springState.centerY = f;
                springState.isCenterYUpdated = true;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        final class SCALE extends SpringProperty {
            public SCALE(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final float get(SpringState springState) {
                return springState.scale;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.SpringProperty
            public final void setValue(SpringState springState, float f) {
                springState.scale = f;
                springState.isScaleUpdated = true;
            }
        }

        static {
            CENTER_X center_x = new CENTER_X("CENTER_X", 0);
            CENTER_X = center_x;
            CENTER_Y center_y = new CENTER_Y("CENTER_Y", 1);
            CENTER_Y = center_y;
            SCALE scale = new SCALE("SCALE", 2);
            SCALE = scale;
            SpringProperty[] springPropertyArr = {center_x, center_y, scale};
            $VALUES = springPropertyArr;
            EnumEntriesKt.enumEntries(springPropertyArr);
        }

        public /* synthetic */ SpringProperty(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i);
        }

        public static SpringProperty valueOf(String str) {
            return (SpringProperty) Enum.valueOf(SpringProperty.class, str);
        }

        public static SpringProperty[] values() {
            return (SpringProperty[]) $VALUES.clone();
        }

        public abstract float get(SpringState springState);

        public abstract void setValue(SpringState springState, float f);

        private SpringProperty(String str, int i) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpringTimings {
        public final float contentAfterFadeInDelay;
        public final float contentAfterFadeInDuration;
        public final float contentBeforeFadeOutDelay;
        public final float contentBeforeFadeOutDuration;

        public SpringTimings(float f, float f2, float f3, float f4) {
            this.contentBeforeFadeOutDelay = f;
            this.contentBeforeFadeOutDuration = f2;
            this.contentAfterFadeInDelay = f3;
            this.contentAfterFadeInDuration = f4;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class State {
        public int bottom;
        public float bottomCornerRadius;
        public int left;
        public int right;
        public int top;
        public float topCornerRadius;
        public boolean visible;

        public State() {
            this(0, 0, 0, 0, 0.0f, 0.0f, 63, null);
        }

        public final float getCenterX() {
            return (getWidth() / 2.0f) + this.left;
        }

        public final float getCenterY() {
            return (getHeight() / 2.0f) + this.top;
        }

        public final int getHeight() {
            return this.bottom - this.top;
        }

        public final int getWidth() {
            return this.right - this.left;
        }

        public State(int i, int i2, int i3, int i4, float f, float f2) {
            this.top = i;
            this.bottom = i2;
            this.left = i3;
            this.right = i4;
            this.topCornerRadius = f;
            this.bottomCornerRadius = f2;
            this.visible = true;
        }

        public /* synthetic */ State(int i, int i2, int i3, int i4, float f, float f2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4, (i5 & 16) != 0 ? 0.0f : f, (i5 & 32) != 0 ? 0.0f : f2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Timings {
        public final long contentAfterFadeInDelay;
        public final long contentAfterFadeInDuration;
        public final long contentBeforeFadeOutDelay;
        public final long contentBeforeFadeOutDuration;
        public final long totalDuration;

        public Timings(long j, long j2, long j3, long j4, long j5) {
            this.totalDuration = j;
            this.contentBeforeFadeOutDelay = j2;
            this.contentBeforeFadeOutDuration = j3;
            this.contentAfterFadeInDelay = j4;
            this.contentAfterFadeInDuration = j5;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Timings)) {
                return false;
            }
            Timings timings = (Timings) obj;
            return this.totalDuration == timings.totalDuration && this.contentBeforeFadeOutDelay == timings.contentBeforeFadeOutDelay && this.contentBeforeFadeOutDuration == timings.contentBeforeFadeOutDuration && this.contentAfterFadeInDelay == timings.contentAfterFadeInDelay && this.contentAfterFadeInDuration == timings.contentAfterFadeInDuration;
        }

        public final int hashCode() {
            return Long.hashCode(this.contentAfterFadeInDuration) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.totalDuration) * 31, 31, this.contentBeforeFadeOutDelay), 31, this.contentBeforeFadeOutDuration), 31, this.contentAfterFadeInDelay);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Timings(totalDuration=");
            sb.append(this.totalDuration);
            sb.append(", contentBeforeFadeOutDelay=");
            sb.append(this.contentBeforeFadeOutDelay);
            sb.append(", contentBeforeFadeOutDuration=");
            sb.append(this.contentBeforeFadeOutDuration);
            sb.append(", contentAfterFadeInDelay=");
            sb.append(this.contentAfterFadeInDelay);
            sb.append(", contentAfterFadeInDuration=");
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.contentAfterFadeInDuration, ")", sb);
        }
    }

    public TransitionAnimator(Executor executor, Timings timings, Interpolators interpolators, SpringTimings springTimings, Interpolators interpolators2, SpringParams springParams) {
        this.mainExecutor = executor;
        this.timings = timings;
        this.interpolators = interpolators;
        this.springTimings = springTimings;
        this.springInterpolators = interpolators2;
        this.springParams = springParams;
        this.transitionContainerLocation = new int[2];
        this.cornerRadii = new float[8];
        if ((springTimings == null) != (interpolators2 == null)) {
            throw new IllegalStateException("Check failed.");
        }
    }

    public static boolean checkVisibility(Timings timings, float f, boolean z) {
        Companion companion = Companion;
        if (z) {
            long j = timings.contentBeforeFadeOutDelay;
            companion.getClass();
            return Companion.getProgressInternal((float) timings.totalDuration, f, (float) j, (float) timings.contentBeforeFadeOutDuration) < 1.0f;
        }
        long j2 = timings.contentAfterFadeInDelay;
        companion.getClass();
        return Companion.getProgressInternal((float) timings.totalDuration, f, (float) j2, (float) timings.contentAfterFadeInDuration) > 0.0f;
    }

    public static final void createSpringAnimation$updateProgress(State state, State state2, Ref$BooleanRef ref$BooleanRef, TransitionAnimator transitionAnimator, Controller controller, GradientDrawable gradientDrawable, View view, ViewGroupOverlay viewGroupOverlay, View view2, ViewOverlay viewOverlay, boolean z, boolean z2, boolean z3, Ref$FloatRef ref$FloatRef, Ref$FloatRef ref$FloatRef2, Ref$ObjectRef ref$ObjectRef, Ref$ObjectRef ref$ObjectRef2, SpringState springState) {
        View transitionContainer;
        if (springState.isCenterXUpdated || springState.isCenterXDone) {
            if (springState.isCenterYUpdated || springState.isCenterYDone) {
                if (springState.isScaleUpdated || springState.isScaleDone) {
                    springState.isCenterXUpdated = false;
                    springState.isCenterYUpdated = false;
                    springState.isScaleUpdated = false;
                    float lerp = MathUtils.lerp(state.getWidth(), state2.getWidth(), springState.scale);
                    float lerp2 = MathUtils.lerp(state.getHeight(), state2.getHeight(), springState.scale);
                    float f = springState.centerX;
                    float f2 = 2;
                    float f3 = lerp / f2;
                    float f4 = springState.centerY;
                    float f5 = lerp2 / f2;
                    State state3 = new State((int) (f4 - f5), (int) (f4 + f5), (int) (f - f3), (int) (f + f3), MathUtils.lerp(state.topCornerRadius, state2.topCornerRadius, springState.scale), MathUtils.lerp(state.bottomCornerRadius, state2.bottomCornerRadius, springState.scale));
                    state3.visible = checkVisibility(transitionAnimator.timings, springState.scale, controller.isLaunching());
                    if (!ref$BooleanRef.element) {
                        ref$BooleanRef.element = maybeMoveBackgroundLayer(controller, state3, gradientDrawable, view, viewGroupOverlay, view2, viewOverlay, z);
                    }
                    if (ref$BooleanRef.element) {
                        view2.getClass();
                        transitionContainer = view2;
                    } else {
                        transitionContainer = controller.getTransitionContainer();
                    }
                    transitionAnimator.applyStateToWindowBackgroundLayer(gradientDrawable, state3, springState.scale, transitionContainer, z2, z3, false, true);
                    float f6 = springState.scale;
                    controller.onTransitionAnimationProgress(state3, f6, f6);
                    if (state2.getCenterX() == ref$FloatRef.element || state2.getCenterY() == ref$FloatRef2.element) {
                        return;
                    }
                    ref$FloatRef.element = state2.getCenterX();
                    ref$FloatRef2.element = state2.getCenterY();
                    SpringAnimation springAnimation = (SpringAnimation) ref$ObjectRef.element;
                    if (springAnimation != null) {
                        springAnimation.animateToFinalPosition(ref$FloatRef.element);
                    }
                    SpringAnimation springAnimation2 = (SpringAnimation) ref$ObjectRef2.element;
                    if (springAnimation2 != null) {
                        springAnimation2.animateToFinalPosition(ref$FloatRef2.element);
                    }
                }
            }
        }
    }

    public static boolean maybeMoveBackgroundLayer(Controller controller, State state, GradientDrawable gradientDrawable, View view, ViewGroupOverlay viewGroupOverlay, View view2, ViewOverlay viewOverlay, boolean z) {
        if (controller.isLaunching() && z && !state.visible) {
            viewGroupOverlay.remove(gradientDrawable);
            viewOverlay.getClass();
            viewOverlay.add(gradientDrawable);
            ViewRootSync viewRootSync = ViewRootSync.INSTANCE;
            view2.getClass();
            final int i = 0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.animation.TransitionAnimator$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i) {
                        case 0:
                            TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                            break;
                        default:
                            TransitionAnimator.Companion companion2 = TransitionAnimator.Companion;
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
            viewRootSync.getClass();
            ViewRootSync.synchronizeNextDraw(view, view2, function0);
            return true;
        }
        if (controller.isLaunching() || !z || !state.visible) {
            return false;
        }
        viewOverlay.getClass();
        viewOverlay.remove(gradientDrawable);
        viewGroupOverlay.add(gradientDrawable);
        ViewRootSync viewRootSync2 = ViewRootSync.INSTANCE;
        view2.getClass();
        final int i2 = 1;
        Function0 function02 = new Function0() { // from class: com.android.systemui.animation.TransitionAnimator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                        break;
                    default:
                        TransitionAnimator.Companion companion2 = TransitionAnimator.Companion;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        viewRootSync2.getClass();
        ViewRootSync.synchronizeNextDraw(view2, view, function02);
        return true;
    }

    public final void applyStateToWindowBackgroundLayer(GradientDrawable gradientDrawable, State state, float f, View view, boolean z, boolean z2, boolean z3, boolean z4) {
        float progressInternal;
        Interpolators interpolators;
        float f2;
        int[] iArr = this.transitionContainerLocation;
        view.getLocationOnScreen(iArr);
        int i = state.left;
        int i2 = iArr[0];
        int i3 = state.top;
        int i4 = iArr[1];
        gradientDrawable.setBounds(i - i2, i3 - i4, state.right - i2, state.bottom - i4);
        float f3 = state.topCornerRadius;
        float[] fArr = this.cornerRadii;
        fArr[0] = f3;
        fArr[1] = f3;
        fArr[2] = f3;
        fArr[3] = f3;
        float f4 = state.bottomCornerRadius;
        fArr[4] = f4;
        fArr[5] = f4;
        fArr[6] = f4;
        fArr[7] = f4;
        gradientDrawable.setCornerRadii(fArr);
        Companion companion = Companion;
        if (z4) {
            interpolators = this.springInterpolators;
            interpolators.getClass();
            SpringTimings springTimings = this.springTimings;
            springTimings.getClass();
            companion.getClass();
            progressInternal = Companion.getProgressInternal(1.0f, f, springTimings.contentBeforeFadeOutDelay, springTimings.contentBeforeFadeOutDuration);
            f2 = Companion.getProgressInternal(1.0f, f, springTimings.contentAfterFadeInDelay, springTimings.contentAfterFadeInDuration);
        } else {
            long j = this.timings.contentBeforeFadeOutDelay;
            companion.getClass();
            progressInternal = Companion.getProgressInternal(r15.totalDuration, f, j, r15.contentBeforeFadeOutDuration);
            float progressInternal2 = Companion.getProgressInternal(r15.totalDuration, f, r15.contentAfterFadeInDelay, r15.contentAfterFadeInDuration);
            interpolators = this.interpolators;
            f2 = progressInternal2;
        }
        if (!z3) {
            if (progressInternal >= 1.0f || !z) {
                gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt((1 - interpolators.contentAfterFadeInInterpolator.getInterpolation(f2)) * 255));
                gradientDrawable.setXfermode(null);
                return;
            } else {
                gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt(interpolators.contentBeforeFadeOutInterpolator.getInterpolation(progressInternal) * 255));
                if (z2) {
                    gradientDrawable.setXfermode(SRC_MODE);
                    return;
                }
                return;
            }
        }
        if (progressInternal < 1.0f) {
            gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt(interpolators.contentBeforeFadeOutInterpolator.getInterpolation(progressInternal) * 255));
            return;
        }
        if (!z) {
            gradientDrawable.setAlpha(255);
            return;
        }
        gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt((1 - interpolators.contentAfterFadeInInterpolator.getInterpolation(f2)) * 255));
        if (z2) {
            gradientDrawable.setXfermode(SRC_MODE);
        }
    }

    /* JADX WARN: Type inference failed for: r0v23, types: [T, com.android.internal.dynamicanimation.animation.SpringAnimation] */
    /* JADX WARN: Type inference failed for: r5v4, types: [T, com.android.internal.dynamicanimation.animation.SpringAnimation] */
    public final Animation createAnimation(final Controller controller, final State state, final State state2, final GradientDrawable gradientDrawable, final boolean z, final boolean z2, PointF pointF, long j) {
        final ViewGroup transitionContainer = controller.getTransitionContainer();
        final ViewGroupOverlay overlay = transitionContainer.getOverlay();
        final View openingWindowSyncView = controller.getOpeningWindowSyncView();
        final ViewOverlay overlay2 = openingWindowSyncView != null ? openingWindowSyncView.getOverlay() : null;
        final boolean z3 = (openingWindowSyncView == null || Intrinsics.areEqual(openingWindowSyncView.getViewRootImpl(), controller.getTransitionContainer().getViewRootImpl())) ? false : true;
        if (pointF == null || this.springTimings == null || this.springInterpolators == null) {
            overlay.getClass();
            final int i = state.top;
            final int i2 = state.bottom;
            int i3 = state.left;
            final float f = (i3 + r2) / 2.0f;
            final int i4 = state.right - i3;
            final float f2 = state.topCornerRadius;
            final float f3 = state.bottomCornerRadius;
            final Ref$IntRef ref$IntRef = new Ref$IntRef();
            ref$IntRef.element = state2.top;
            final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            ref$IntRef2.element = state2.bottom;
            final Ref$IntRef ref$IntRef3 = new Ref$IntRef();
            ref$IntRef3.element = state2.left;
            final Ref$IntRef ref$IntRef4 = new Ref$IntRef();
            ref$IntRef4.element = state2.right;
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef.element = (ref$IntRef3.element + ref$IntRef4.element) / 2.0f;
            final Ref$IntRef ref$IntRef5 = new Ref$IntRef();
            ref$IntRef5.element = ref$IntRef4.element - ref$IntRef3.element;
            final float f4 = state2.topCornerRadius;
            final float f5 = state2.bottomCornerRadius;
            final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionContainer, state2);
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(this.timings.totalDuration);
            ofFloat.setInterpolator(com.android.app.animation.Interpolators.LINEAR);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TransitionAnimator$createInterpolatedAnimation$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    TransitionAnimator transitionAnimator = TransitionAnimator.this;
                    TransitionAnimator.Controller controller2 = controller;
                    boolean z4 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib;
                    GradientDrawable gradientDrawable2 = gradientDrawable;
                    ViewGroupOverlay viewGroupOverlay = overlay;
                    ViewOverlay viewOverlay = overlay2;
                    boolean z5 = z3;
                    TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                    transitionAnimator.getClass();
                    TransitionAnimator$$ExternalSyntheticLambda0 transitionAnimator$$ExternalSyntheticLambda0 = new TransitionAnimator$$ExternalSyntheticLambda0(controller2, z4, viewGroupOverlay, gradientDrawable2, z5, viewOverlay);
                    if (controller2.isLaunching()) {
                        transitionAnimator$$ExternalSyntheticLambda0.invoke();
                    } else {
                        transitionAnimator.mainExecutor.execute(new TransitionAnimator$onAnimationEnd$1(transitionAnimator$$ExternalSyntheticLambda0));
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator, boolean z4) {
                    TransitionAnimator transitionAnimator = TransitionAnimator.this;
                    TransitionAnimator.Controller controller2 = controller;
                    boolean z5 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib;
                    GradientDrawable gradientDrawable2 = gradientDrawable;
                    ViewGroupOverlay viewGroupOverlay = overlay;
                    ViewOverlay viewOverlay = overlay2;
                    TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                    transitionAnimator.getClass();
                    controller2.onTransitionAnimationStart(z5);
                    if (controller2.isLaunching() || viewOverlay == null) {
                        viewGroupOverlay.add(gradientDrawable2);
                    } else {
                        viewOverlay.add(gradientDrawable2);
                    }
                }
            });
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.TransitionAnimator$createInterpolatedAnimation$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    View transitionContainer2;
                    Ref$IntRef ref$IntRef6 = ref$IntRef;
                    TransitionAnimator.State state3 = state2;
                    Ref$IntRef ref$IntRef7 = ref$IntRef2;
                    Ref$IntRef ref$IntRef8 = ref$IntRef3;
                    Ref$IntRef ref$IntRef9 = ref$IntRef4;
                    Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
                    Ref$IntRef ref$IntRef10 = ref$IntRef5;
                    TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                    int i5 = ref$IntRef6.element;
                    int i6 = state3.top;
                    if (i5 != i6 || ref$IntRef7.element != state3.bottom || ref$IntRef8.element != state3.left || ref$IntRef9.element != state3.right) {
                        ref$IntRef6.element = i6;
                        ref$IntRef7.element = state3.bottom;
                        ref$IntRef8.element = state3.left;
                        int i7 = state3.right;
                        ref$IntRef9.element = i7;
                        int i8 = ref$IntRef8.element;
                        ref$FloatRef2.element = (i8 + i7) / 2.0f;
                        ref$IntRef10.element = i7 - i8;
                    }
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float interpolation = TransitionAnimator.this.interpolators.positionInterpolator.getInterpolation(animatedFraction);
                    float lerp = MathUtils.lerp(f, ref$FloatRef.element, TransitionAnimator.this.interpolators.positionXInterpolator.getInterpolation(animatedFraction));
                    float lerp2 = MathUtils.lerp(i4, ref$IntRef5.element, interpolation) / 2.0f;
                    state.top = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i, ref$IntRef.element, interpolation));
                    state.bottom = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i2, ref$IntRef2.element, interpolation));
                    state.left = MathKt__MathJVMKt.roundToInt(lerp - lerp2);
                    state.right = MathKt__MathJVMKt.roundToInt(lerp + lerp2);
                    state.topCornerRadius = MathUtils.lerp(f2, f4, interpolation);
                    state.bottomCornerRadius = MathUtils.lerp(f3, f5, interpolation);
                    state.visible = TransitionAnimator.checkVisibility(TransitionAnimator.this.timings, animatedFraction, controller.isLaunching());
                    Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                    if (!ref$BooleanRef2.element) {
                        TransitionAnimator transitionAnimator = TransitionAnimator.this;
                        TransitionAnimator.Controller controller2 = controller;
                        TransitionAnimator.State state4 = state;
                        GradientDrawable gradientDrawable2 = gradientDrawable;
                        View view = transitionContainer;
                        ViewGroupOverlay viewGroupOverlay = overlay;
                        View view2 = openingWindowSyncView;
                        ViewOverlay viewOverlay = overlay2;
                        boolean z4 = z3;
                        transitionAnimator.getClass();
                        ref$BooleanRef2.element = TransitionAnimator.maybeMoveBackgroundLayer(controller2, state4, gradientDrawable2, view, viewGroupOverlay, view2, viewOverlay, z4);
                    }
                    if (ref$BooleanRef.element) {
                        transitionContainer2 = openingWindowSyncView;
                        transitionContainer2.getClass();
                    } else {
                        transitionContainer2 = controller.getTransitionContainer();
                    }
                    TransitionAnimator.this.applyStateToWindowBackgroundLayer(gradientDrawable, state, animatedFraction, transitionContainer2, z, z2, controller.isLaunching(), false);
                    controller.onTransitionAnimationProgress(state, interpolation, animatedFraction);
                }
            });
            return new InterpolatedAnimation(ofFloat);
        }
        overlay.getClass();
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
        ref$FloatRef2.element = state2.getCenterX();
        final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
        ref$FloatRef3.element = state2.getCenterY();
        final Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        final SpringState springState = new SpringState(state.getCenterX(), state.getCenterY(), 0.0f, false, false, false, false, false, false, 508, null);
        final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib2 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionContainer, state2);
        final SpringProperty.CENTER_X center_x = SpringProperty.CENTER_X;
        final int i5 = 0;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.animation.TransitionAnimator$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i5) {
                    case 0:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    case 1:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    default:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        Companion.getClass();
        final String name = center_x.name();
        ?? springAnimation = new SpringAnimation(springState, new FloatProperty(name) { // from class: com.android.systemui.animation.TransitionAnimator$Companion$buildProperty$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(TransitionAnimator.SpringProperty.this.get((TransitionAnimator.SpringState) obj));
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f6) {
                TransitionAnimator.SpringState springState2 = (TransitionAnimator.SpringState) obj;
                TransitionAnimator.SpringProperty.this.setValue(springState2, f6);
                function1.mo779invoke(springState2);
            }
        });
        SpringForce springForce = new SpringForce(state2.getCenterX());
        SpringParams springParams = this.springParams;
        springForce.setStiffness(springParams.centerXStiffness);
        springForce.setDampingRatio(springParams.centerXDampingRatio);
        springAnimation.setSpring(springForce);
        springAnimation.setStartValue(state.getCenterX());
        springAnimation.setStartVelocity(pointF.x);
        springAnimation.setMinValue(Math.min(state.getCenterX(), state2.getCenterX()));
        springAnimation.setMaxValue(Math.max(state.getCenterX(), state2.getCenterX()));
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.animation.TransitionAnimator$createSpringAnimation$2$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z4, float f6, float f7) {
                TransitionAnimator.SpringState springState2 = TransitionAnimator.SpringState.this;
                springState2.isCenterXDone = true;
                TransitionAnimator transitionAnimator = this;
                TransitionAnimator.Controller controller2 = controller;
                boolean z5 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib2;
                GradientDrawable gradientDrawable2 = gradientDrawable;
                ViewGroupOverlay viewGroupOverlay = overlay;
                ViewOverlay viewOverlay = overlay2;
                boolean z6 = z3;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                if (springState2.isCenterYDone && springState2.isScaleDone) {
                    transitionAnimator.getClass();
                    TransitionAnimator$$ExternalSyntheticLambda0 transitionAnimator$$ExternalSyntheticLambda0 = new TransitionAnimator$$ExternalSyntheticLambda0(controller2, z5, viewGroupOverlay, gradientDrawable2, z6, viewOverlay);
                    if (controller2.isLaunching()) {
                        transitionAnimator$$ExternalSyntheticLambda0.invoke();
                    } else {
                        transitionAnimator.mainExecutor.execute(new TransitionAnimator$onAnimationEnd$1(transitionAnimator$$ExternalSyntheticLambda0));
                    }
                }
            }
        });
        ref$ObjectRef.element = springAnimation;
        final SpringProperty.CENTER_Y center_y = SpringProperty.CENTER_Y;
        final int i6 = 1;
        final Function1 function12 = new Function1() { // from class: com.android.systemui.animation.TransitionAnimator$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i6) {
                    case 0:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    case 1:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    default:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final String name2 = center_y.name();
        ?? springAnimation2 = new SpringAnimation(springState, new FloatProperty(name2) { // from class: com.android.systemui.animation.TransitionAnimator$Companion$buildProperty$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(TransitionAnimator.SpringProperty.this.get((TransitionAnimator.SpringState) obj));
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f6) {
                TransitionAnimator.SpringState springState2 = (TransitionAnimator.SpringState) obj;
                TransitionAnimator.SpringProperty.this.setValue(springState2, f6);
                function12.mo779invoke(springState2);
            }
        });
        SpringForce springForce2 = new SpringForce(state2.getCenterY());
        springForce2.setStiffness(springParams.centerYStiffness);
        springForce2.setDampingRatio(springParams.centerYDampingRatio);
        springAnimation2.setSpring(springForce2);
        springAnimation2.setStartValue(state.getCenterY());
        springAnimation2.setStartVelocity(pointF.y);
        springAnimation2.setMinValue(Math.min(state.getCenterY(), state2.getCenterY()));
        springAnimation2.setMaxValue(Math.max(state.getCenterY(), state2.getCenterY()));
        springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.animation.TransitionAnimator$createSpringAnimation$4$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z4, float f6, float f7) {
                TransitionAnimator.SpringState springState2 = TransitionAnimator.SpringState.this;
                springState2.isCenterYDone = true;
                TransitionAnimator transitionAnimator = this;
                TransitionAnimator.Controller controller2 = controller;
                boolean z5 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib2;
                GradientDrawable gradientDrawable2 = gradientDrawable;
                ViewGroupOverlay viewGroupOverlay = overlay;
                ViewOverlay viewOverlay = overlay2;
                boolean z6 = z3;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                if (springState2.isCenterXDone && springState2.isScaleDone) {
                    transitionAnimator.getClass();
                    TransitionAnimator$$ExternalSyntheticLambda0 transitionAnimator$$ExternalSyntheticLambda0 = new TransitionAnimator$$ExternalSyntheticLambda0(controller2, z5, viewGroupOverlay, gradientDrawable2, z6, viewOverlay);
                    if (controller2.isLaunching()) {
                        transitionAnimator$$ExternalSyntheticLambda0.invoke();
                    } else {
                        transitionAnimator.mainExecutor.execute(new TransitionAnimator$onAnimationEnd$1(transitionAnimator$$ExternalSyntheticLambda0));
                    }
                }
            }
        });
        ref$ObjectRef2.element = springAnimation2;
        final SpringProperty.SCALE scale = SpringProperty.SCALE;
        final int i7 = 2;
        final Function1 function13 = new Function1() { // from class: com.android.systemui.animation.TransitionAnimator$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i7) {
                    case 0:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    case 1:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                    default:
                        TransitionAnimator.createSpringAnimation$updateProgress(state, state2, ref$BooleanRef2, this, controller, gradientDrawable, transitionContainer, overlay, openingWindowSyncView, overlay2, z3, z, z2, ref$FloatRef2, ref$FloatRef3, ref$ObjectRef, ref$ObjectRef2, (TransitionAnimator.SpringState) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final String name3 = scale.name();
        SpringAnimation springAnimation3 = new SpringAnimation(springState, new FloatProperty(name3) { // from class: com.android.systemui.animation.TransitionAnimator$Companion$buildProperty$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(TransitionAnimator.SpringProperty.this.get((TransitionAnimator.SpringState) obj));
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f6) {
                TransitionAnimator.SpringState springState2 = (TransitionAnimator.SpringState) obj;
                TransitionAnimator.SpringProperty.this.setValue(springState2, f6);
                function13.mo779invoke(springState2);
            }
        });
        SpringForce springForce3 = new SpringForce(1.0f);
        springForce3.setStiffness(springParams.scaleStiffness);
        springForce3.setDampingRatio(springParams.scaleDampingRatio);
        springAnimation3.setSpring(springForce3);
        springAnimation3.setStartValue(0.0f);
        springAnimation3.setMaxValue(1.0f);
        springAnimation3.setMinimumVisibleChange(Math.abs(1.0f / state.getHeight()));
        springAnimation3.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.animation.TransitionAnimator$createSpringAnimation$springScale$2$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z4, float f6, float f7) {
                TransitionAnimator.SpringState springState2 = TransitionAnimator.SpringState.this;
                springState2.isScaleDone = true;
                TransitionAnimator transitionAnimator = this;
                TransitionAnimator.Controller controller2 = controller;
                boolean z5 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib2;
                GradientDrawable gradientDrawable2 = gradientDrawable;
                ViewGroupOverlay viewGroupOverlay = overlay;
                ViewOverlay viewOverlay = overlay2;
                boolean z6 = z3;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                if (springState2.isCenterXDone && springState2.isCenterYDone) {
                    transitionAnimator.getClass();
                    TransitionAnimator$$ExternalSyntheticLambda0 transitionAnimator$$ExternalSyntheticLambda0 = new TransitionAnimator$$ExternalSyntheticLambda0(controller2, z5, viewGroupOverlay, gradientDrawable2, z6, viewOverlay);
                    if (controller2.isLaunching()) {
                        transitionAnimator$$ExternalSyntheticLambda0.invoke();
                    } else {
                        transitionAnimator.mainExecutor.execute(new TransitionAnimator$onAnimationEnd$1(transitionAnimator$$ExternalSyntheticLambda0));
                    }
                }
            }
        });
        return new MultiSpringAnimation((SpringAnimation) ref$ObjectRef.element, (SpringAnimation) ref$ObjectRef2.element, springAnimation3, springState, j, new Runnable() { // from class: com.android.systemui.animation.TransitionAnimator$createSpringAnimation$5
            @Override // java.lang.Runnable
            public final void run() {
                TransitionAnimator transitionAnimator = TransitionAnimator.this;
                TransitionAnimator.Controller controller2 = controller;
                boolean z4 = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib2;
                GradientDrawable gradientDrawable2 = gradientDrawable;
                ViewGroupOverlay viewGroupOverlay = overlay;
                ViewOverlay viewOverlay = overlay2;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                transitionAnimator.getClass();
                controller2.onTransitionAnimationStart(z4);
                if (controller2.isLaunching() || viewOverlay == null) {
                    viewGroupOverlay.add(gradientDrawable2);
                } else {
                    viewOverlay.add(gradientDrawable2);
                }
            }
        });
    }

    public final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(View view, State state) {
        int[] iArr = this.transitionContainerLocation;
        view.getLocationOnScreen(iArr);
        int i = state.top;
        int i2 = iArr[1];
        if (i <= i2 && state.bottom >= view.getHeight() + i2) {
            int i3 = state.left;
            int i4 = iArr[0];
            if (i3 <= i4 && state.right >= view.getWidth() + i4) {
                return true;
            }
        }
        return false;
    }

    public final Animation startAnimation(Controller controller, State state, int i, boolean z, boolean z2, PointF pointF, long j) {
        boolean isLaunching = controller.isLaunching();
        Companion companion = Companion;
        if (!isLaunching) {
            companion.getClass();
        }
        if (pointF != null) {
            companion.getClass();
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setAlpha(0);
        Animation createAnimation = createAnimation(controller, controller.createAnimatorState(), state, gradientDrawable, z, z2, pointF, j);
        createAnimation.start();
        return createAnimation;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Interpolators {
        public final Interpolator contentAfterFadeInInterpolator;
        public final Interpolator contentBeforeFadeOutInterpolator;
        public final Interpolator positionInterpolator;
        public final Interpolator positionXInterpolator;

        public Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            this.positionInterpolator = interpolator;
            this.positionXInterpolator = interpolator2;
            this.contentBeforeFadeOutInterpolator = interpolator3;
            this.contentAfterFadeInInterpolator = interpolator4;
        }

        public static Interpolators copy$default(Interpolators interpolators, Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, int i) {
            Interpolator interpolator4 = interpolators.positionInterpolator;
            if ((i & 2) != 0) {
                interpolator = interpolators.positionXInterpolator;
            }
            if ((i & 4) != 0) {
                interpolator2 = interpolators.contentBeforeFadeOutInterpolator;
            }
            if ((i & 8) != 0) {
                interpolator3 = interpolators.contentAfterFadeInInterpolator;
            }
            interpolators.getClass();
            return new Interpolators(interpolator4, interpolator, interpolator2, interpolator3);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Interpolators)) {
                return false;
            }
            Interpolators interpolators = (Interpolators) obj;
            return Intrinsics.areEqual(this.positionInterpolator, interpolators.positionInterpolator) && Intrinsics.areEqual(this.positionXInterpolator, interpolators.positionXInterpolator) && Intrinsics.areEqual(this.contentBeforeFadeOutInterpolator, interpolators.contentBeforeFadeOutInterpolator) && Intrinsics.areEqual(this.contentAfterFadeInInterpolator, interpolators.contentAfterFadeInInterpolator);
        }

        public final int hashCode() {
            return this.contentAfterFadeInInterpolator.hashCode() + ((this.contentBeforeFadeOutInterpolator.hashCode() + ((this.positionXInterpolator.hashCode() + (this.positionInterpolator.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "Interpolators(positionInterpolator=" + this.positionInterpolator + ", positionXInterpolator=" + this.positionXInterpolator + ", contentBeforeFadeOutInterpolator=" + this.contentBeforeFadeOutInterpolator + ", contentAfterFadeInInterpolator=" + this.contentAfterFadeInInterpolator + ")";
        }

        public /* synthetic */ Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(interpolator, (i & 2) != 0 ? interpolator : interpolator2, interpolator3, interpolator4);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpringState {
        public float centerX;
        public float centerY;
        public boolean isCenterXDone;
        public boolean isCenterXUpdated;
        public boolean isCenterYDone;
        public boolean isCenterYUpdated;
        public boolean isScaleDone;
        public boolean isScaleUpdated;
        public float scale;

        public SpringState(float f, float f2, float f3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.centerX = f;
            this.centerY = f2;
            this.scale = f3;
            this.isCenterXUpdated = z;
            this.isCenterYUpdated = z2;
            this.isScaleUpdated = z3;
            this.isCenterXDone = z4;
            this.isCenterYDone = z5;
            this.isScaleDone = z6;
        }

        /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
            java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
            	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
            	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
            	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
            */
        public /* synthetic */ SpringState(float r11, float r12, float r13, boolean r14, boolean r15, boolean r16, boolean r17, boolean r18, boolean r19, int r20, kotlin.jvm.internal.DefaultConstructorMarker r21) {
            /*
                r10 = this;
                r0 = r20
                r1 = r0 & 4
                if (r1 == 0) goto L7
                r13 = 0
            L7:
                r3 = r13
                r13 = r0 & 8
                r1 = 0
                if (r13 == 0) goto Lf
                r4 = r1
                goto L10
            Lf:
                r4 = r14
            L10:
                r13 = r0 & 16
                if (r13 == 0) goto L16
                r5 = r1
                goto L17
            L16:
                r5 = r15
            L17:
                r13 = r0 & 32
                if (r13 == 0) goto L1d
                r6 = r1
                goto L1f
            L1d:
                r6 = r16
            L1f:
                r13 = r0 & 64
                if (r13 == 0) goto L25
                r7 = r1
                goto L27
            L25:
                r7 = r17
            L27:
                r13 = r0 & 128(0x80, float:1.8E-43)
                if (r13 == 0) goto L2d
                r8 = r1
                goto L2f
            L2d:
                r8 = r18
            L2f:
                r13 = r0 & 256(0x100, float:3.59E-43)
                if (r13 == 0) goto L38
                r9 = r1
                r0 = r10
                r2 = r12
                r1 = r11
                goto L3d
            L38:
                r9 = r19
                r0 = r10
                r1 = r11
                r2 = r12
            L3d:
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.TransitionAnimator.SpringState.<init>(float, float, float, boolean, boolean, boolean, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    public /* synthetic */ TransitionAnimator(Executor executor, Timings timings, Interpolators interpolators, SpringTimings springTimings, Interpolators interpolators2, SpringParams springParams, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, timings, interpolators, (i & 8) != 0 ? null : springTimings, (i & 16) != 0 ? null : interpolators2, (i & 32) != 0 ? DEFAULT_SPRING_PARAMS : springParams);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Controller {
        State createAnimatorState();

        default View getOpeningWindowSyncView() {
            return null;
        }

        ViewGroup getTransitionContainer();

        default WindowAnimationState getWindowAnimatorState() {
            return null;
        }

        boolean isLaunching();

        void setTransitionContainer(ViewGroup viewGroup);

        default void onTransitionAnimationEnd(boolean z) {
        }

        default void onTransitionAnimationStart(boolean z) {
        }

        default void onTransitionAnimationProgress(State state, float f, float f2) {
        }
    }
}
