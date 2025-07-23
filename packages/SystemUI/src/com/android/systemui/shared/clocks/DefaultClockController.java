package com.android.systemui.shared.clocks;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.text.NumberFormat;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.customization.R$layout;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockEventListener;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.plugins.clocks.ClockLogger;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.plugins.clocks.DefaultClockFaceLayout;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultClockController implements ClockController {
    public final float burmeseLineSpacing;
    public final String burmeseNumerals;
    public final List clocks;
    public final Lazy config$delegate;
    public final Context ctx;
    public final float defaultLineSpacing;
    public final DefaultClockEvents events;
    public final LargeClockFaceController largeClock;
    public final Resources resources;
    public final DefaultClockFaceController smallClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimationState {
        public float fraction;
        public boolean isActive;

        public AnimationState(float f) {
            this.fraction = f;
            this.isActive = f > 0.5f;
        }

        public final Pair update(float f) {
            float f2 = this.fraction;
            if (f == f2) {
                return new Pair(Boolean.valueOf(this.isActive), Boolean.FALSE);
            }
            boolean z = this.isActive;
            boolean z2 = (f2 == 0.0f && f == 1.0f) || (f2 == 1.0f && f == 0.0f);
            boolean z3 = f > f2;
            this.isActive = z3;
            this.fraction = f;
            return new Pair(Boolean.valueOf(z != z3), Boolean.valueOf(z2));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getDOZE_COLOR$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.shared.clocks.AnimatableClockView$animateCharge$startAnimPhase2$1] */
        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void charge() {
            final DefaultClockController$$ExternalSyntheticLambda0 defaultClockController$$ExternalSyntheticLambda0 = new DefaultClockController$$ExternalSyntheticLambda0(this, 1);
            final AnimatableClockView animatableClockView = this.view;
            TextAnimator textAnimator = animatableClockView.textAnimator;
            if (textAnimator != null) {
                ValueAnimator valueAnimator = textAnimator.animator;
                if (valueAnimator != null ? valueAnimator.isRunning() : false) {
                    return;
                }
                animatableClockView.getLogger().animateCharge();
                animatableClockView.setTextStyle(((Boolean) defaultClockController$$ExternalSyntheticLambda0.invoke()).booleanValue() ? animatableClockView.getLockScreenWeight() : animatableClockView.getDozingWeight(), null, true, null, 500L, animatableClockView.chargeAnimationDelay, new Runnable() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$animateCharge$startAnimPhase2$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AnimatableClockView animatableClockView2 = AnimatableClockView.this;
                        boolean booleanValue = ((Boolean) defaultClockController$$ExternalSyntheticLambda0.invoke()).booleanValue();
                        AnimatableClockView animatableClockView3 = AnimatableClockView.this;
                        int dozingWeight = booleanValue ? animatableClockView3.getDozingWeight() : animatableClockView3.getLockScreenWeight();
                        String str = AnimatableClockView.TAG;
                        animatableClockView2.setTextStyle(dozingWeight, null, true, null, 1000L, 0L, null);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void doze(float f) {
            AnimationState animationState = this.dozeState;
            Pair update = animationState.update(f);
            boolean booleanValue = ((Boolean) update.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
            if (booleanValue) {
                this.view.animateDoze(animationState.isActive, !booleanValue2);
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void enter() {
            if (this.dozeState.isActive) {
                return;
            }
            AnimatableClockView animatableClockView = this.view;
            Logger.d$default(animatableClockView.getLogger(), "animateAppearOnLockscreen", null, 2, null);
            animatableClockView.setTextStyle(animatableClockView.getDozingWeight(), Integer.valueOf(animatableClockView.lockScreenColor), false, null, 0L, 0L, null);
            int lockScreenWeight = animatableClockView.getLockScreenWeight();
            int i = animatableClockView.lockScreenColor;
            animatableClockView.setTextStyle(lockScreenWeight, Integer.valueOf(i), true, Interpolators.EMPHASIZED_DECELERATE, 833L, 0L, null);
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void fold(float f) {
            Pair update = this.foldState.update(f);
            boolean booleanValue = ((Boolean) update.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
            if (booleanValue) {
                this.view.animateFoldAppear(!booleanValue2);
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void onPickerCarouselSwiping(float f) {
            Paint.FontMetrics fontMetrics;
            AnimatableClockView animatableClockView = this.view;
            TextPaint paint = animatableClockView.getPaint();
            animatableClockView.setTranslationY((1 - f) * ((paint == null || (fontMetrics = paint.getFontMetrics()) == null) ? 0.0f : fontMetrics.bottom) * 0.5f);
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public void onPositionUpdated(float f, float f2) {
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public void onPositionUpdated(int i, int i2, float f) {
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void onFontAxesChanged(ClockAxisStyle clockAxisStyle) {
        }

        @Override // com.android.systemui.plugins.clocks.ClockAnimations
        public final void onFidgetTap(float f, float f2) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultClockFaceController implements ClockFaceController {
        public DefaultClockAnimations animations;
        public final ClockFaceConfig config;
        public int currentColor;
        public final DefaultClockController$DefaultClockFaceController$events$1 events;
        public final DefaultClockFaceLayout layout;
        public Rect targetRegion;
        public ThemeConfig theme;
        public final AnimatableClockView view;

        public DefaultClockFaceController(DefaultClockController defaultClockController, AnimatableClockView animatableClockView, Integer num, MessageBuffer messageBuffer) {
            this.view = animatableClockView;
            this.currentColor = num != null ? num.intValue() : -65281;
            this.config = new ClockFaceConfig(null, false, false, false, 15, null);
            this.theme = new ThemeConfig(true, num);
            DefaultClockFaceLayout defaultClockFaceLayout = new DefaultClockFaceLayout(animatableClockView);
            defaultClockFaceLayout.getViews().get(0).setId(defaultClockController.resources.getIdentifier("lockscreen_clock_view", "id", defaultClockController.ctx.getPackageName()));
            this.layout = defaultClockFaceLayout;
            this.animations = new DefaultClockAnimations(defaultClockController, animatableClockView, 0.0f, 0.0f);
            int i = this.currentColor;
            animatableClockView.dozingColor = -1;
            animatableClockView.lockScreenColor = i;
            if (messageBuffer != null) {
                animatableClockView.logger = new ClockLogger(animatableClockView, messageBuffer, AnimatableClockView.TAG);
            }
            this.events = new DefaultClockController$DefaultClockFaceController$events$1(this, defaultClockController);
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public final ClockAnimations getAnimations() {
            return this.animations;
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public ClockFaceConfig getConfig() {
            return this.config;
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public final ClockFaceEvents getEvents() {
            return this.events;
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public final ThemeConfig getTheme() {
            return this.theme;
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public final View getView() {
            return this.view;
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceController
        public DefaultClockFaceLayout getLayout() {
            return this.layout;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LargeClockFaceController extends DefaultClockFaceController {
        public final ClockFaceConfig config;
        public final DefaultClockFaceLayout layout;

        public LargeClockFaceController(DefaultClockController defaultClockController, AnimatableClockView animatableClockView, Integer num, MessageBuffer messageBuffer) {
            super(defaultClockController, animatableClockView, num, messageBuffer);
            DefaultClockFaceLayout defaultClockFaceLayout = new DefaultClockFaceLayout(animatableClockView);
            defaultClockFaceLayout.getViews().get(0).setId(defaultClockController.resources.getIdentifier("lockscreen_clock_view_large", "id", defaultClockController.ctx.getPackageName()));
            this.layout = defaultClockFaceLayout;
            this.config = new ClockFaceConfig(null, false, true, false, 11, null);
            animatableClockView.hasCustomPositionUpdatedAnimation = true;
            this.animations = defaultClockController.new LargeClockAnimations(animatableClockView, 0.0f, 0.0f);
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockFaceController, com.android.systemui.plugins.clocks.ClockFaceController
        public final ClockFaceConfig getConfig() {
            return this.config;
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockFaceController, com.android.systemui.plugins.clocks.ClockFaceController
        public final ClockFaceLayout getLayout() {
            return this.layout;
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockFaceController, com.android.systemui.plugins.clocks.ClockFaceController
        public final DefaultClockFaceLayout getLayout() {
            return this.layout;
        }
    }

    static {
        new Companion(null);
    }

    public DefaultClockController(Context context, LayoutInflater layoutInflater, Resources resources, ClockSettings clockSettings, ClockMessageBuffers clockMessageBuffers) {
        this.ctx = context;
        this.resources = resources;
        this.burmeseNumerals = NumberFormat.getInstance(Locale.forLanguageTag("my")).format(1234567890L);
        this.burmeseLineSpacing = resources.getFloat(R$dimen.keyguard_clock_line_spacing_scale_burmese);
        this.defaultLineSpacing = resources.getFloat(R$dimen.keyguard_clock_line_spacing_scale);
        this.config$delegate = LazyKt__LazyJVMKt.lazy(new DefaultClockController$$ExternalSyntheticLambda0(this, 0));
        FrameLayout frameLayout = new FrameLayout(context);
        DefaultClockFaceController defaultClockFaceController = new DefaultClockFaceController(this, (AnimatableClockView) layoutInflater.inflate(R$layout.clock_default_small, (ViewGroup) frameLayout, false), clockSettings != null ? clockSettings.getSeedColor() : null, clockMessageBuffers != null ? clockMessageBuffers.getSmallClockMessageBuffer() : null);
        this.smallClock = defaultClockFaceController;
        LargeClockFaceController largeClockFaceController = new LargeClockFaceController(this, (AnimatableClockView) layoutInflater.inflate(R$layout.clock_default_large, (ViewGroup) frameLayout, false), clockSettings != null ? clockSettings.getSeedColor() : null, clockMessageBuffers != null ? clockMessageBuffers.getLargeClockMessageBuffer() : null);
        this.largeClock = largeClockFaceController;
        this.clocks = Arrays.asList(defaultClockFaceController.view, largeClockFaceController.view);
        DefaultClockEvents defaultClockEvents = new DefaultClockEvents();
        this.events = defaultClockEvents;
        defaultClockEvents.onLocaleChanged(Locale.getDefault());
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final void dump(PrintWriter printWriter) {
        printWriter.print("smallClock=");
        this.smallClock.view.dump(printWriter);
        printWriter.print("largeClock=");
        this.largeClock.view.dump(printWriter);
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final ClockConfig getConfig() {
        return (ClockConfig) this.config$delegate.getValue();
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final ClockEvents getEvents() {
        return this.events;
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final ClockFaceController getLargeClock() {
        return this.largeClock;
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final ClockFaceController getSmallClock() {
        return this.smallClock;
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final void initialize(boolean z, float f, float f2, ClockEventListener clockEventListener) {
        LargeClockFaceController largeClockFaceController = this.largeClock;
        largeClockFaceController.getClass();
        largeClockFaceController.animations = new LargeClockAnimations(largeClockFaceController.view, f, f2);
        DefaultClockFaceController defaultClockFaceController = this.smallClock;
        defaultClockFaceController.animations = new DefaultClockAnimations(this, defaultClockFaceController.view, f, f2);
        ThemeConfig copy$default = ThemeConfig.copy$default(largeClockFaceController.theme, z, null, 2, null);
        DefaultClockController$DefaultClockFaceController$events$1 defaultClockController$DefaultClockFaceController$events$1 = largeClockFaceController.events;
        defaultClockController$DefaultClockFaceController$events$1.onThemeChanged(copy$default);
        ThemeConfig copy$default2 = ThemeConfig.copy$default(defaultClockFaceController.theme, z, null, 2, null);
        DefaultClockController$DefaultClockFaceController$events$1 defaultClockController$DefaultClockFaceController$events$12 = defaultClockFaceController.events;
        defaultClockController$DefaultClockFaceController$events$12.onThemeChanged(copy$default2);
        this.events.onTimeZoneChanged(TimeZone.getDefault());
        defaultClockController$DefaultClockFaceController$events$12.onTimeTick();
        defaultClockController$DefaultClockFaceController$events$1.onTimeTick();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LargeClockAnimations extends DefaultClockAnimations {
        public LargeClockAnimations(AnimatableClockView animatableClockView, float f, float f2) {
            super(DefaultClockController.this, animatableClockView, f, f2);
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockAnimations, com.android.systemui.plugins.clocks.ClockAnimations
        public final void onPositionUpdated(float f, float f2) {
            LargeClockFaceController largeClockFaceController = DefaultClockController.this.largeClock;
            int i = 0;
            while (true) {
                AnimatableClockView animatableClockView = largeClockFaceController.view;
                if (i >= 4) {
                    animatableClockView.invalidate();
                    return;
                }
                float f3 = (animatableClockView.isLayoutRtl() ? -1 : 1) * f;
                ((ArrayList) animatableClockView.glyphOffsets).set(i, Float.valueOf(animatableClockView.getDigitFraction(f2, f > 0.0f, i) * f3));
                if (f > 0.0f) {
                    ArrayList arrayList = (ArrayList) animatableClockView.glyphOffsets;
                    arrayList.set(i, Float.valueOf(((Number) arrayList.get(i)).floatValue() - f3));
                }
                i++;
            }
        }

        @Override // com.android.systemui.shared.clocks.DefaultClockController.DefaultClockAnimations, com.android.systemui.plugins.clocks.ClockAnimations
        public final void onPositionUpdated(int i, int i2, float f) {
            AnimatableClockView animatableClockView = DefaultClockController.this.largeClock.view;
            boolean z = true;
            if (!animatableClockView.isLayoutRtl() ? i2 <= 0 : i2 >= 0) {
                z = false;
            }
            int left = animatableClockView.getLeft() - i;
            for (int i3 = 0; i3 < 4; i3++) {
                float f2 = left;
                ((ArrayList) animatableClockView.glyphOffsets).set(i3, Float.valueOf((animatableClockView.getDigitFraction(f, z, i3) * f2) - f2));
            }
            animatableClockView.invalidate();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultClockEvents implements ClockEvents {
        public boolean isReactiveTouchInteractionEnabled;

        public DefaultClockEvents() {
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final boolean isReactiveTouchInteractionEnabled() {
            return this.isReactiveTouchInteractionEnabled;
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onLocaleChanged(Locale locale) {
            String format = NumberFormat.getInstance(locale).format(1234567890L);
            DefaultClockController defaultClockController = DefaultClockController.this;
            if (Intrinsics.areEqual(format, defaultClockController.burmeseNumerals)) {
                Iterator it = defaultClockController.clocks.iterator();
                while (it.hasNext()) {
                    ((AnimatableClockView) it.next()).setLineSpacing(0.0f, defaultClockController.burmeseLineSpacing);
                }
            } else {
                Iterator it2 = defaultClockController.clocks.iterator();
                while (it2.hasNext()) {
                    ((AnimatableClockView) it2.next()).setLineSpacing(0.0f, defaultClockController.defaultLineSpacing);
                }
            }
            for (AnimatableClockView animatableClockView : defaultClockController.clocks) {
                animatableClockView.refreshFormat(DateFormat.is24HourFormat(animatableClockView.getContext()));
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeFormatChanged(boolean z) {
            Iterator it = DefaultClockController.this.clocks.iterator();
            while (it.hasNext()) {
                ((AnimatableClockView) it.next()).refreshFormat(z);
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeZoneChanged(TimeZone timeZone) {
            for (AnimatableClockView animatableClockView : DefaultClockController.this.clocks) {
                ClockLogger logger = animatableClockView.getLogger();
                AnimatableClockView$$ExternalSyntheticLambda0 animatableClockView$$ExternalSyntheticLambda0 = new AnimatableClockView$$ExternalSyntheticLambda0(4);
                String str = null;
                LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, animatableClockView$$ExternalSyntheticLambda0, null);
                if (timeZone != null) {
                    str = timeZone.toString();
                }
                obtain.setStr1(str);
                logger.getBuffer().commit(obtain);
                animatableClockView.time.setTimeZone(timeZone);
                animatableClockView.refreshFormat(DateFormat.is24HourFormat(animatableClockView.getContext()));
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void setReactiveTouchInteractionEnabled(boolean z) {
            this.isReactiveTouchInteractionEnabled = z;
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onAlarmDataChanged(AlarmData alarmData) {
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onWeatherDataChanged(WeatherData weatherData) {
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onZenDataChanged(ZenData zenData) {
        }
    }

    public /* synthetic */ DefaultClockController(Context context, LayoutInflater layoutInflater, Resources resources, ClockSettings clockSettings, ClockMessageBuffers clockMessageBuffers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, resources, clockSettings, (i & 16) != 0 ? null : clockMessageBuffers);
    }
}
