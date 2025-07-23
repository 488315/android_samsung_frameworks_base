package com.android.systemui.keyguard.ui.view.layout.sections.transitions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.customization.R$id;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.ClockController;
import com.google.android.material.math.MathUtils;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ClockSizeTransition extends TransitionSet {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ClockFaceInTransition extends ClockFaceTransition {
        public static final Interpolator CLOCK_IN_INTERPOLATOR;
        public final boolean isLargeClock;
        public final float smallClockMoveScale;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            CLOCK_IN_INTERPOLATOR = Interpolators.LINEAR_OUT_SLOW_IN;
        }

        public ClockFaceInTransition(IntraBlueprintTransition.Config config, KeyguardClockViewModel keyguardClockViewModel, LogBuffer logBuffer) {
            super(config, keyguardClockViewModel, logBuffer);
            this.isLargeClock = ((Boolean) keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue();
            this.smallClockMoveScale = 0.3576017f;
            setDuration(167L);
            setStartDelay(133L);
            setInterpolator(CLOCK_IN_INTERPOLATOR);
            addTargets();
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.ClockFaceTransition
        public final float getSmallClockMoveScale() {
            return this.smallClockMoveScale;
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.ClockFaceTransition
        public final boolean isLargeClock() {
            return this.isLargeClock;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ClockFaceOutTransition extends ClockFaceTransition {
        public static final Interpolator CLOCK_OUT_INTERPOLATOR;
        public final boolean isLargeClock;
        public final float smallClockMoveScale;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            CLOCK_OUT_INTERPOLATOR = Interpolators.LINEAR;
        }

        public ClockFaceOutTransition(IntraBlueprintTransition.Config config, KeyguardClockViewModel keyguardClockViewModel, LogBuffer logBuffer) {
            super(config, keyguardClockViewModel, logBuffer);
            this.isLargeClock = !((Boolean) keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue();
            this.smallClockMoveScale = 0.13753878f;
            setDuration(133L);
            setInterpolator(CLOCK_OUT_INTERPOLATOR);
            addTargets();
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.ClockFaceTransition
        public final float getSmallClockMoveScale() {
            return this.smallClockMoveScale;
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.ClockFaceTransition
        public final boolean isLargeClock() {
            return this.isLargeClock;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ClockFaceTransition extends VisibilityBoundsTransition {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final KeyguardClockViewModel viewModel;

        public ClockFaceTransition(IntraBlueprintTransition.Config config, KeyguardClockViewModel keyguardClockViewModel, LogBuffer logBuffer) {
            super(logBuffer);
            this.viewModel = keyguardClockViewModel;
        }

        public final void addTargets() {
            if (!isLargeClock()) {
                Logger.i$default(this.logger, "Adding small clock", null, 2, null);
                addTarget(R$id.lockscreen_clock_view);
                KeyguardClockViewModel keyguardClockViewModel = this.viewModel;
                keyguardClockViewModel.getClass();
                KeyguardSmartspaceViewModel.Companion companion = KeyguardSmartspaceViewModel.Companion;
                keyguardClockViewModel.context.getResources().getConfiguration();
                companion.getClass();
                return;
            }
            ClockController clockController = (ClockController) this.viewModel.currentClock.$$delegate_0.getValue();
            if (clockController == null) {
                Logger.e$default(this.logger, "No large clock set, falling back", null, 2, null);
                addTarget(R$id.lockscreen_clock_view_large);
                return;
            }
            Logger logger = this.logger;
            ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 = new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(0);
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0, null);
            obtain.setStr1(String.valueOf(clockController.getLargeClock().getLayout().getViews()));
            logger.getBuffer().commit(obtain);
            Iterator<T> it = clockController.getLargeClock().getLayout().getViews().iterator();
            while (it.hasNext()) {
                addTarget((View) it.next());
            }
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.VisibilityBoundsTransition
        public final boolean getCaptureSmartspace() {
            return !isLargeClock();
        }

        public abstract float getSmallClockMoveScale();

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.VisibilityBoundsTransition
        public final void initTargets(VisibilityBoundsTransition.Target target, VisibilityBoundsTransition.Target target2) {
            Rect rect;
            if (target.isVisible == target2.isVisible) {
                return;
            }
            target.bounds.set(target2.bounds);
            if (isLargeClock()) {
                return;
            }
            Rect rect2 = target2.ssBounds;
            if (rect2 == null || (rect = target.ssBounds) == null) {
                Logger.e$default(this.logger, "initTargets: smallClock received no smartspace bounds", null, 2, null);
                return;
            }
            int i = rect2.top;
            rect.getClass();
            int abs = (int) Math.abs(getSmallClockMoveScale() * (i - rect.top));
            Rect rect3 = target.bounds;
            Rect rect4 = target2.bounds;
            rect3.top = rect4.top - abs;
            rect3.bottom = rect4.bottom - abs;
        }

        public abstract boolean isLargeClock();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SmartspaceMoveTransition extends VisibilityBoundsTransition {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final KeyguardClockViewModel viewModel;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public SmartspaceMoveTransition(IntraBlueprintTransition.Config config, KeyguardClockViewModel keyguardClockViewModel, LogBuffer logBuffer) {
            super(logBuffer);
            this.viewModel = keyguardClockViewModel;
            setDuration(((Boolean) keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue() ? 967L : 467L);
            setInterpolator(Interpolators.EMPHASIZED);
            KeyguardSmartspaceViewModel.Companion companion = KeyguardSmartspaceViewModel.Companion;
            keyguardClockViewModel.context.getResources().getConfiguration();
            companion.getClass();
            addTarget(R.id.date_smartspace_view);
            addTarget(R.id.bc_smartspace_view);
            addTarget(R.id.aod_notification_icon_container);
            addTarget(R.id.status_view_media_container);
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.VisibilityBoundsTransition
        public final boolean getCaptureSmartspace() {
            return false;
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.VisibilityBoundsTransition
        public final void initTargets(VisibilityBoundsTransition.Target target, VisibilityBoundsTransition.Target target2) {
            if (target.isVisible == target2.isVisible) {
                return;
            }
            Logger logger = this.logger;
            ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 = new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(1);
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0, null);
            obtain.setInt1(target2.view.getId());
            logger.getBuffer().commit(obtain);
            if (target.isVisible) {
                target2.bounds.set(target.bounds);
            } else {
                target.bounds.set(target2.bounds);
            }
        }

        @Override // com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition.VisibilityBoundsTransition
        public final void mutateTargets(VisibilityBoundsTransition.Target target) {
            if (target.view.getId() == R.id.date_smartspace_view) {
                boolean booleanValue = ((Boolean) this.viewModel.hasCustomWeatherDataDisplay.$$delegate_0.getValue()).booleanValue();
                target.isVisible = !booleanValue;
                target.visibility = !booleanValue ? 0 : 8;
                target.alpha = !booleanValue ? 1.0f : 0.0f;
            }
        }
    }

    public ClockSizeTransition(IntraBlueprintTransition.Config config, KeyguardClockViewModel keyguardClockViewModel, LogBuffer logBuffer) {
        setOrdering(0);
        if (config.type != IntraBlueprintTransition.Type.SmartspaceVisibility) {
            addTransition(new ClockFaceOutTransition(config, keyguardClockViewModel, logBuffer));
            addTransition(new ClockFaceInTransition(config, keyguardClockViewModel, logBuffer));
        }
        addTransition(new SmartspaceMoveTransition(config, keyguardClockViewModel, logBuffer));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class VisibilityBoundsTransition extends Transition {
        public static final String[] TRANSITION_PROPERTIES;
        public final Logger logger;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Target {
            public static final Companion Companion = new Companion(null);
            public float alpha;
            public final Rect bounds;
            public boolean isVisible;
            public final Rect ssBounds;
            public final View view;
            public int visibility;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }
            }

            public Target(View view, int i, boolean z, float f, Rect rect, Rect rect2) {
                this.view = view;
                this.visibility = i;
                this.isVisible = z;
                this.alpha = f;
                this.bounds = rect;
                this.ssBounds = rect2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Target)) {
                    return false;
                }
                Target target = (Target) obj;
                return Intrinsics.areEqual(this.view, target.view) && this.visibility == target.visibility && this.isVisible == target.isVisible && Float.compare(this.alpha, target.alpha) == 0 && Intrinsics.areEqual(this.bounds, target.bounds) && Intrinsics.areEqual(this.ssBounds, target.ssBounds);
            }

            public final int hashCode() {
                int hashCode = (this.bounds.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.visibility, this.view.hashCode() * 31, 31), 31, this.isVisible), 31)) * 31;
                Rect rect = this.ssBounds;
                return hashCode + (rect == null ? 0 : rect.hashCode());
            }

            public final String toString() {
                return "Target(view=" + this.view + ", visibility=" + this.visibility + ", isVisible=" + this.isVisible + ", alpha=" + this.alpha + ", bounds=" + this.bounds + ", ssBounds=" + this.ssBounds + ")";
            }
        }

        static {
            new Companion(null);
            TRANSITION_PROPERTIES = new String[]{"ClockSizeTransition:Visibility", "ClockSizeTransition:Alpha", "ClockSizeTransition:Bounds", "ClockSizeTransition:SSBounds"};
        }

        public VisibilityBoundsTransition(LogBuffer logBuffer) {
            String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
            simpleName.getClass();
            this.logger = new Logger(logBuffer, simpleName);
        }

        public static final void createAnimator$assignAnimValues(VisibilityBoundsTransition visibilityBoundsTransition, Target target, Target target2, String str, float f, Integer num, boolean z) {
            visibilityBoundsTransition.mutateTargets(target2);
            Rect rect = new Rect((int) MathUtils.lerp(target.bounds.left, target2.bounds.left, f), (int) MathUtils.lerp(target.bounds.top, target2.bounds.top, f), (int) MathUtils.lerp(target.bounds.right, target2.bounds.right, f), (int) MathUtils.lerp(target.bounds.bottom, target2.bounds.bottom, f));
            float lerp = MathUtils.lerp(target.alpha, target2.alpha, f);
            if (z) {
                Logger logger = visibilityBoundsTransition.logger;
                LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(6), null);
                obtain.setStr1(str);
                obtain.setStr2(String.valueOf(target2.view));
                obtain.setInt1((int) (f * 100));
                obtain.setDouble1(lerp);
                obtain.setInt2(num != null ? num.intValue() : 0);
                obtain.setStr3(String.valueOf(rect));
                logger.getBuffer().commit(obtain);
            }
            target2.view.setVisibility(num != null ? num.intValue() : 0);
            target2.view.setAlpha(lerp);
            target2.view.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
        }

        @Override // android.transition.Transition
        public final void captureEndValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        @Override // android.transition.Transition
        public final void captureStartValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        public final void captureValues(TransitionValues transitionValues) {
            View view = transitionValues.view;
            transitionValues.values.put("ClockSizeTransition:Visibility", Integer.valueOf(view.getVisibility()));
            transitionValues.values.put("ClockSizeTransition:Alpha", Float.valueOf(view.getAlpha()));
            transitionValues.values.put("ClockSizeTransition:Bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            if (getCaptureSmartspace()) {
                View view2 = (View) view.getParent();
                View findViewById = view2.findViewById(R.id.bc_smartspace_view);
                if (findViewById == null) {
                    findViewById = view2.findViewById(R.id.keyguard_slice_view);
                }
                if (findViewById != null) {
                    transitionValues.values.put("ClockSizeTransition:SSBounds", new Rect(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom()));
                    return;
                }
                Logger logger = this.logger;
                ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 = new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(2);
                LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, clockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0, null);
                obtain.setStr1(String.valueOf(view2));
                logger.getBuffer().commit(obtain);
            }
        }

        @Override // android.transition.Transition
        public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            int i;
            boolean z;
            float f;
            int i2 = 4;
            if (transitionValues == null || transitionValues2 == null) {
                Logger logger = this.logger;
                LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(3), null);
                obtain.setStr1(String.valueOf(transitionValues));
                obtain.setStr2(String.valueOf(transitionValues2));
                logger.getBuffer().commit(obtain);
                return null;
            }
            Target.Companion.getClass();
            int intValue = ((Integer) transitionValues.values.get("ClockSizeTransition:Visibility")).intValue();
            boolean z2 = intValue == 0;
            float floatValue = ((Float) transitionValues.values.get("ClockSizeTransition:Alpha")).floatValue();
            if (z2) {
                if (floatValue <= 0.0f) {
                    i = 4;
                    z = false;
                } else {
                    i = intValue;
                    z = z2;
                }
                f = floatValue;
            } else {
                i = intValue;
                z = z2;
                f = 0.0f;
            }
            final Target target = new Target(transitionValues.view, i, z, f, (Rect) transitionValues.values.get("ClockSizeTransition:Bounds"), (Rect) transitionValues.values.get("ClockSizeTransition:SSBounds"));
            int intValue2 = ((Integer) transitionValues2.values.get("ClockSizeTransition:Visibility")).intValue();
            boolean z3 = intValue2 == 0;
            final Target target2 = new Target(transitionValues2.view, intValue2, z3, z3 ? 1.0f : 0.0f, (Rect) transitionValues2.values.get("ClockSizeTransition:Bounds"), (Rect) transitionValues2.values.get("ClockSizeTransition:SSBounds"));
            initTargets(target, target2);
            mutateTargets(target2);
            if (target.isVisible == target2.isVisible && target.bounds.equals(target2.bounds)) {
                Logger logger2 = this.logger;
                LogMessage obtain2 = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.WARNING, new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(i2), null);
                obtain2.setStr1(String.valueOf(target2.view));
                obtain2.setInt1(target.visibility);
                obtain2.setInt2(target2.visibility);
                obtain2.setStr2(target.alpha + " -> " + target2.alpha);
                obtain2.setStr3(target.bounds + " -> " + target2.bounds);
                logger2.getBuffer().commit(obtain2);
                return null;
            }
            final boolean z4 = target.isVisible && !target2.isVisible;
            Logger logger3 = this.logger;
            LogMessage obtain3 = logger3.getBuffer().obtain(logger3.getTag(), LogLevel.INFO, new ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(5), null);
            obtain3.setStr1(String.valueOf(target2.view));
            obtain3.setInt1(target.visibility);
            obtain3.setInt2(target2.visibility);
            obtain3.setStr2(target.alpha + " -> " + target2.alpha);
            obtain3.setStr3(target.bounds + " -> " + target2.bounds);
            logger3.getBuffer().commit(obtain3);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition$VisibilityBoundsTransition$createAnimator$7$predrawCallback$1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public final boolean onPreDraw() {
                    ClockSizeTransition.VisibilityBoundsTransition.createAnimator$assignAnimValues(this, target, target2, "predraw", ofFloat.getAnimatedFraction(), null, false);
                    return true;
                }
            };
            addListener(new TransitionListenerAdapter() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition$VisibilityBoundsTransition$createAnimator$7$1
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition) {
                    ClockSizeTransition.VisibilityBoundsTransition.Target.this.view.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public final void onTransitionStart(Transition transition) {
                    ClockSizeTransition.VisibilityBoundsTransition.Target.this.view.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition$VisibilityBoundsTransition$createAnimator$7$listener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ClockSizeTransition.VisibilityBoundsTransition visibilityBoundsTransition = this;
                    ClockSizeTransition.VisibilityBoundsTransition.Target target3 = ClockSizeTransition.VisibilityBoundsTransition.Target.this;
                    ClockSizeTransition.VisibilityBoundsTransition.Target target4 = target2;
                    ClockSizeTransition.VisibilityBoundsTransition.createAnimator$assignAnimValues(visibilityBoundsTransition, target3, target4, NetworkAnalyticsConstants.DataPoints.CLOSE_TIME, 1.0f, Integer.valueOf(target4.visibility), true);
                    if (z4) {
                        target2.view.setTranslationZ(0.0f);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    ClockSizeTransition.VisibilityBoundsTransition visibilityBoundsTransition = this;
                    ClockSizeTransition.VisibilityBoundsTransition.Target target3 = ClockSizeTransition.VisibilityBoundsTransition.Target.this;
                    ClockSizeTransition.VisibilityBoundsTransition.createAnimator$assignAnimValues(visibilityBoundsTransition, target3, target2, NetworkAnalyticsConstants.DataPoints.OPEN_TIME, 0.0f, Integer.valueOf(target3.visibility), true);
                }
            });
            createAnimator$assignAnimValues(this, target, target2, "init", 0.0f, Integer.valueOf(target.visibility), true);
            return ofFloat;
        }

        public abstract boolean getCaptureSmartspace();

        @Override // android.transition.Transition
        public final String[] getTransitionProperties() {
            return TRANSITION_PROPERTIES;
        }

        public void mutateTargets(Target target) {
        }

        public void initTargets(Target target, Target target2) {
        }
    }
}
