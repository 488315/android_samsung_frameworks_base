package com.android.systemui.shared.clocks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.text.NumberFormat;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.plugins.ClockConfig;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockEvents;
import com.android.systemui.plugins.ClockFaceConfig;
import com.android.systemui.plugins.ClockFaceController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.plugins.ClockSettings;
import com.android.systemui.plugins.WeatherData;
import com.android.systemui.shared.clocks.DefaultClockController;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultClockController implements ClockController {
    public final float burmeseLineSpacing;
    public final String burmeseNumerals;
    public final List clocks;
    public final ClockConfig config;
    public final float defaultLineSpacing;
    public final DefaultClockEvents events;
    public final boolean hasStepClockAnimation;
    public final LargeClockFaceController largeClock;
    public final Resources resources;
    public final DefaultClockFaceController smallClock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationState {
        public float fraction;
        public boolean isActive;

        public AnimationState(float f) {
            this.fraction = f;
            this.isActive = f > 0.5f;
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
        
            if ((r8 == 1.0f) == false) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:
        
            r4 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0042, code lost:
        
            if ((r8 == 0.0f) != false) goto L30;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Pair update(float f) {
            float f2 = this.fraction;
            if (f == f2) {
                return new Pair(Boolean.valueOf(this.isActive), Boolean.FALSE);
            }
            boolean z = this.isActive;
            if (f2 == 0.0f) {
            }
            if (f2 == 1.0f) {
            }
            boolean z2 = false;
            boolean z3 = f > f2;
            this.isActive = z3;
            this.fraction = f;
            return new Pair(Boolean.valueOf(z != z3), Boolean.valueOf(z2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LargeClockAnimations extends DefaultClockAnimations {
        public LargeClockAnimations(AnimatableClockView animatableClockView, float f, float f2) {
            super(DefaultClockController.this, animatableClockView, f, f2);
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockAnimations, com.android.systemui.plugins.ClockAnimations
        public final void onPositionUpdated(int i, int i2, float f) {
            AnimatableClockView animatableClockView = DefaultClockController.this.largeClock.view;
            boolean z = !animatableClockView.isLayoutRtl() ? i2 <= 0 : i2 >= 0;
            int left = animatableClockView.getLeft() - i;
            int i3 = animatableClockView.isLayoutRtl() ? -1 : 1;
            for (int i4 = 0; i4 < 4; i4++) {
                float floatValue = (z ? ((Number) (animatableClockView.isLayoutRtl() ? AnimatableClockView.MOVE_LEFT_DELAYS : AnimatableClockView.MOVE_RIGHT_DELAYS).get(i4)).floatValue() : ((Number) (animatableClockView.isLayoutRtl() ? AnimatableClockView.MOVE_RIGHT_DELAYS : AnimatableClockView.MOVE_LEFT_DELAYS).get(i4)).floatValue()) * 0.033f;
                float f2 = left;
                animatableClockView.glyphOffsets.set(i4, Float.valueOf(i3 * ((((PathInterpolator) AnimatableClockView.MOVE_INTERPOLATOR).getInterpolation(MathUtils.constrainedMap(0.0f, 1.0f, floatValue, 0.901f + floatValue, f)) * f2) - f2)));
            }
            animatableClockView.invalidate();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LargeClockFaceController extends DefaultClockFaceController {
        public final ClockFaceConfig config;

        public LargeClockFaceController(DefaultClockController defaultClockController, AnimatableClockView animatableClockView, Integer num) {
            super(animatableClockView, num);
            this.config = new ClockFaceConfig(null, false, defaultClockController.hasStepClockAnimation, 3, null);
            this.animations = defaultClockController.new LargeClockAnimations(animatableClockView, 0.0f, 0.0f);
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockFaceController, com.android.systemui.plugins.ClockFaceController
        public final ClockFaceConfig getConfig() {
            return this.config;
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockFaceController
        public final void recomputePadding(Rect rect) {
            float f;
            TextPaint paint;
            Paint.FontMetrics fontMetrics;
            AnimatableClockView animatableClockView = this.view;
            Object parent = animatableClockView.getParent();
            float f2 = 0.0f;
            if (rect != null && (parent instanceof View)) {
                if (((View) parent).isLaidOut()) {
                    f = rect.centerY() - (r0.getHeight() / 2.0f);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatableClockView.getLayoutParams();
                    paint = animatableClockView.getPaint();
                    if (paint != null && (fontMetrics = paint.getFontMetrics()) != null) {
                        f2 = fontMetrics.bottom;
                    }
                    layoutParams.topMargin = (int) ((f2 * (-0.5f)) + f);
                    animatableClockView.setLayoutParams(layoutParams);
                }
            }
            f = 0.0f;
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) animatableClockView.getLayoutParams();
            paint = animatableClockView.getPaint();
            if (paint != null) {
                f2 = fontMetrics.bottom;
            }
            layoutParams2.topMargin = (int) ((f2 * (-0.5f)) + f);
            animatableClockView.setLayoutParams(layoutParams2);
        }
    }

    static {
        new Companion(null);
    }

    public DefaultClockController(Context context, LayoutInflater layoutInflater, Resources resources, ClockSettings clockSettings, boolean z) {
        this.resources = resources;
        this.hasStepClockAnimation = z;
        this.burmeseNumerals = NumberFormat.getInstance(Locale.forLanguageTag("my")).format(1234567890L);
        this.burmeseLineSpacing = resources.getFloat(R.dimen.keyguard_clock_line_spacing_scale_burmese);
        this.defaultLineSpacing = resources.getFloat(R.dimen.keyguard_clock_line_spacing_scale);
        this.config = new ClockConfig(false, false, 3, null);
        FrameLayout frameLayout = new FrameLayout(context);
        DefaultClockFaceController defaultClockFaceController = new DefaultClockFaceController((AnimatableClockView) layoutInflater.inflate(R.layout.clock_default_small, (ViewGroup) frameLayout, false), clockSettings != null ? clockSettings.getSeedColor() : null);
        this.smallClock = defaultClockFaceController;
        LargeClockFaceController largeClockFaceController = new LargeClockFaceController(this, (AnimatableClockView) layoutInflater.inflate(R.layout.clock_default_large, (ViewGroup) frameLayout, false), clockSettings != null ? clockSettings.getSeedColor() : null);
        this.largeClock = largeClockFaceController;
        this.clocks = CollectionsKt__CollectionsKt.listOf(defaultClockFaceController.view, largeClockFaceController.view);
        DefaultClockEvents defaultClockEvents = new DefaultClockEvents();
        this.events = defaultClockEvents;
        defaultClockEvents.onLocaleChanged(Locale.getDefault());
    }

    @Override // com.android.systemui.plugins.ClockController
    public final void dump(PrintWriter printWriter) {
        printWriter.print("smallClock=");
        this.smallClock.view.dump(printWriter);
        printWriter.print("largeClock=");
        this.largeClock.view.dump(printWriter);
    }

    @Override // com.android.systemui.plugins.ClockController
    public final ClockConfig getConfig() {
        return this.config;
    }

    @Override // com.android.systemui.plugins.ClockController
    public final ClockEvents getEvents() {
        return this.events;
    }

    @Override // com.android.systemui.plugins.ClockController
    public final ClockFaceController getLargeClock() {
        return this.largeClock;
    }

    @Override // com.android.systemui.plugins.ClockController
    public final ClockFaceController getSmallClock() {
        return this.smallClock;
    }

    @Override // com.android.systemui.plugins.ClockController
    public final void initialize(Resources resources, float f, float f2) {
        LargeClockFaceController largeClockFaceController = this.largeClock;
        largeClockFaceController.recomputePadding(null);
        largeClockFaceController.animations = new LargeClockAnimations(largeClockFaceController.view, f, f2);
        DefaultClockFaceController defaultClockFaceController = this.smallClock;
        defaultClockFaceController.animations = new DefaultClockAnimations(this, defaultClockFaceController.view, f, f2);
        DefaultClockEvents defaultClockEvents = this.events;
        defaultClockEvents.onColorPaletteChanged(resources);
        defaultClockEvents.onTimeZoneChanged(TimeZone.getDefault());
        defaultClockFaceController.events.onTimeTick();
        largeClockFaceController.events.onTimeTick();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultClockEvents implements ClockEvents {
        public DefaultClockEvents() {
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onColorPaletteChanged(Resources resources) {
            DefaultClockController defaultClockController = DefaultClockController.this;
            defaultClockController.largeClock.updateColor();
            defaultClockController.smallClock.updateColor();
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onLocaleChanged(Locale locale) {
            String format = NumberFormat.getInstance(locale).format(1234567890L);
            DefaultClockController defaultClockController = DefaultClockController.this;
            boolean areEqual = Intrinsics.areEqual(format, defaultClockController.burmeseNumerals);
            List<AnimatableClockView> list = defaultClockController.clocks;
            if (areEqual) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((AnimatableClockView) it.next()).setLineSpacing(0.0f, defaultClockController.burmeseLineSpacing);
                }
            } else {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    ((AnimatableClockView) it2.next()).setLineSpacing(0.0f, defaultClockController.defaultLineSpacing);
                }
            }
            for (AnimatableClockView animatableClockView : list) {
                animatableClockView.refreshFormat(DateFormat.is24HourFormat(animatableClockView.getContext()));
            }
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onSeedColorChanged(Integer num) {
            DefaultClockController defaultClockController = DefaultClockController.this;
            LargeClockFaceController largeClockFaceController = defaultClockController.largeClock;
            largeClockFaceController.seedColor = num;
            defaultClockController.smallClock.seedColor = num;
            largeClockFaceController.updateColor();
            defaultClockController.smallClock.updateColor();
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onTimeFormatChanged(boolean z) {
            Iterator it = DefaultClockController.this.clocks.iterator();
            while (it.hasNext()) {
                ((AnimatableClockView) it.next()).refreshFormat(z);
            }
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onTimeZoneChanged(TimeZone timeZone) {
            for (AnimatableClockView animatableClockView : DefaultClockController.this.clocks) {
                animatableClockView.time.setTimeZone(timeZone);
                animatableClockView.refreshFormat(DateFormat.is24HourFormat(animatableClockView.getContext()));
                LogBuffer logBuffer = animatableClockView.logBuffer;
                if (logBuffer != null) {
                    LogMessage obtain = logBuffer.obtain(AnimatableClockView.TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$onTimeZoneChanged$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("onTimeZoneChanged newTimeZone=", ((LogMessage) obj).getStr1());
                        }
                    }, null);
                    obtain.setStr1(timeZone.toString());
                    logBuffer.commit(obtain);
                }
            }
        }

        @Override // com.android.systemui.plugins.ClockEvents
        public final void onWeatherDataChanged(WeatherData weatherData) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class DefaultClockFaceController implements ClockFaceController {
        public DefaultClockAnimations animations;
        public final ClockFaceConfig config = new ClockFaceConfig(null, false, false, 7, null);
        public int currentColor;
        public final DefaultClockController$DefaultClockFaceController$events$1 events;
        public boolean isRegionDark;
        public Integer seedColor;
        public Rect targetRegion;
        public final AnimatableClockView view;

        public DefaultClockFaceController(AnimatableClockView animatableClockView, Integer num) {
            this.view = animatableClockView;
            this.seedColor = num;
            this.currentColor = -65281;
            this.animations = new DefaultClockAnimations(DefaultClockController.this, animatableClockView, 0.0f, 0.0f);
            Integer num2 = this.seedColor;
            if (num2 != null) {
                this.currentColor = num2.intValue();
            }
            int i = this.currentColor;
            animatableClockView.dozingColor = -1;
            animatableClockView.lockScreenColor = i;
            this.events = new DefaultClockController$DefaultClockFaceController$events$1(this);
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public final ClockAnimations getAnimations() {
            return this.animations;
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public ClockFaceConfig getConfig() {
            return this.config;
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public final ClockFaceEvents getEvents() {
            return this.events;
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public final LogBuffer getLogBuffer() {
            return this.view.logBuffer;
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public final View getView() {
            return this.view;
        }

        @Override // com.android.systemui.plugins.ClockFaceController
        public final void setLogBuffer(LogBuffer logBuffer) {
            this.view.logBuffer = logBuffer;
        }

        public final void updateColor() {
            int color;
            Integer num = this.seedColor;
            if (num != null) {
                color = num.intValue();
            } else {
                boolean z = this.isRegionDark;
                DefaultClockController defaultClockController = DefaultClockController.this;
                color = z ? defaultClockController.resources.getColor(android.R.color.system_accent1_100) : defaultClockController.resources.getColor(android.R.color.system_accent2_600);
            }
            if (this.currentColor == color) {
                return;
            }
            this.currentColor = color;
            AnimatableClockView animatableClockView = this.view;
            animatableClockView.dozingColor = -1;
            animatableClockView.lockScreenColor = color;
            if (this.animations.dozeState.isActive) {
                return;
            }
            LogBuffer logBuffer = animatableClockView.logBuffer;
            if (logBuffer != null) {
                LogBuffer.log$default(logBuffer, AnimatableClockView.TAG, LogLevel.DEBUG, "animateColorChange", null, 8, null);
            }
            animatableClockView.setTextStyle(animatableClockView.getLockScreenWeight(), null, false, 0L, 0L, null);
            animatableClockView.setTextStyle(animatableClockView.getLockScreenWeight(), Integer.valueOf(animatableClockView.lockScreenColor), true, 400L, 0L, null);
        }

        public void recomputePadding(Rect rect) {
        }
    }

    public /* synthetic */ DefaultClockController(Context context, LayoutInflater layoutInflater, Resources resources, ClockSettings clockSettings, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, resources, clockSettings, (i & 16) != 0 ? false : z);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getDOZE_COLOR$annotations() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class DefaultClockAnimations implements ClockAnimations {
        public final AnimationState dozeState;
        public final AnimationState foldState;
        public final AnimatableClockView view;

        public DefaultClockAnimations(DefaultClockController defaultClockController, AnimatableClockView animatableClockView, float f, float f2) {
            this.view = animatableClockView;
            AnimationState animationState = new AnimationState(f);
            this.dozeState = animationState;
            AnimationState animationState2 = new AnimationState(f2);
            this.foldState = animationState2;
            if (animationState2.isActive) {
                animatableClockView.animateFoldAppear(false);
            } else {
                animatableClockView.animateDoze(animationState.isActive, false);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.shared.clocks.AnimatableClockView$animateCharge$startAnimPhase2$1] */
        @Override // com.android.systemui.plugins.ClockAnimations
        public final void charge() {
            int i;
            final Function0 function0 = new Function0() { // from class: com.android.systemui.shared.clocks.DefaultClockController$DefaultClockAnimations$charge$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(DefaultClockController.DefaultClockAnimations.this.dozeState.isActive);
                }
            };
            final AnimatableClockView animatableClockView = this.view;
            TextAnimator textAnimator = animatableClockView.textAnimator;
            if (textAnimator == null || textAnimator.animator.isRunning()) {
                return;
            }
            LogBuffer logBuffer = animatableClockView.logBuffer;
            if (logBuffer != null) {
                LogBuffer.log$default(logBuffer, AnimatableClockView.TAG, LogLevel.DEBUG, "animateCharge", null, 8, null);
            }
            ?? r9 = new Runnable() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$animateCharge$startAnimPhase2$1
                @Override // java.lang.Runnable
                public final void run() {
                    int lockScreenWeight;
                    AnimatableClockView animatableClockView2 = AnimatableClockView.this;
                    if (((Boolean) function0.invoke()).booleanValue()) {
                        AnimatableClockView animatableClockView3 = AnimatableClockView.this;
                        boolean z = animatableClockView3.getResources().getConfiguration().fontWeightAdjustment > 100;
                        lockScreenWeight = animatableClockView3.dozingWeightInternal;
                        if (z) {
                            lockScreenWeight += 100;
                        }
                    } else {
                        lockScreenWeight = AnimatableClockView.this.getLockScreenWeight();
                    }
                    animatableClockView2.setTextStyle(lockScreenWeight, null, AnimatableClockView.this.isAnimationEnabled, 1000L, 0L, null);
                }
            };
            if (((Boolean) function0.invoke()).booleanValue()) {
                i = animatableClockView.getLockScreenWeight();
            } else {
                boolean z = animatableClockView.getResources().getConfiguration().fontWeightAdjustment > 100;
                int i2 = animatableClockView.dozingWeightInternal;
                if (z) {
                    i2 += 100;
                }
                i = i2;
            }
            animatableClockView.setTextStyle(i, null, animatableClockView.isAnimationEnabled, 500L, animatableClockView.chargeAnimationDelay, r9);
        }

        @Override // com.android.systemui.plugins.ClockAnimations
        public final void doze(float f) {
            AnimationState animationState = this.dozeState;
            Pair update = animationState.update(f);
            boolean booleanValue = ((Boolean) update.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
            if (booleanValue) {
                this.view.animateDoze(animationState.isActive, !booleanValue2);
            }
        }

        @Override // com.android.systemui.plugins.ClockAnimations
        public final void enter() {
            if (this.dozeState.isActive) {
                return;
            }
            AnimatableClockView animatableClockView = this.view;
            LogBuffer logBuffer = animatableClockView.logBuffer;
            if (logBuffer != null) {
                LogBuffer.log$default(logBuffer, AnimatableClockView.TAG, LogLevel.DEBUG, "animateAppearOnLockscreen", null, 8, null);
            }
            boolean z = animatableClockView.getResources().getConfiguration().fontWeightAdjustment > 100;
            int i = animatableClockView.dozingWeightInternal;
            if (z) {
                i += 100;
            }
            animatableClockView.setTextStyle(i, Integer.valueOf(animatableClockView.lockScreenColor), false, 0L, 0L, null);
            animatableClockView.setTextStyle(animatableClockView.getLockScreenWeight(), Integer.valueOf(animatableClockView.lockScreenColor), animatableClockView.isAnimationEnabled, Interpolators.EMPHASIZED_DECELERATE, 833L, 0L, null);
        }

        @Override // com.android.systemui.plugins.ClockAnimations
        public final void fold(float f) {
            Pair update = this.foldState.update(f);
            boolean booleanValue = ((Boolean) update.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
            if (booleanValue) {
                this.view.animateFoldAppear(!booleanValue2);
            }
        }

        @Override // com.android.systemui.plugins.ClockAnimations
        public final void onPickerCarouselSwiping(float f) {
            Paint.FontMetrics fontMetrics;
            AnimatableClockView animatableClockView = this.view;
            TextPaint paint = animatableClockView.getPaint();
            animatableClockView.setTranslationY((1 - f) * ((paint == null || (fontMetrics = paint.getFontMetrics()) == null) ? 0.0f : fontMetrics.bottom) * 0.5f);
        }

        @Override // com.android.systemui.plugins.ClockAnimations
        public void onPositionUpdated(int i, int i2, float f) {
        }
    }
}
