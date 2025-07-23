package com.android.systemui.shared.clocks;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.icu.util.ULocale;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.customization.R$id;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.shared.clocks.DefaultClockController;
import com.android.systemui.shared.clocks.DigitalTimespecHandler;
import com.android.systemui.shared.clocks.view.HorizontalAlignment;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextViewKt;
import com.android.systemui.shared.clocks.view.VerticalAlignment;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SimpleDigitalHandLayerController implements SimpleClockLayerController {
    public final SimpleDigitalHandLayerController$animations$1 animations;
    public final ClockContext clockCtx;
    public DefaultClockController.AnimationState dozeState;
    public final SimpleDigitalHandLayerController$events$1 events;
    public final SimpleDigitalHandLayerController$faceEvents$1 faceEvents;
    public final LayerConfig layerCfg;
    public final Logger logger;
    public final SimpleDigitalClockTextView onViewBoundsChanged$receiver;
    public final DigitalTimespecHandler timespec;
    public final SimpleDigitalClockTextView view;

    /* JADX WARN: Type inference failed for: r12v17, types: [com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$events$1] */
    /* JADX WARN: Type inference failed for: r12v18, types: [com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$animations$1] */
    /* JADX WARN: Type inference failed for: r12v19, types: [com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$faceEvents$1] */
    public SimpleDigitalHandLayerController(ClockContext clockContext, LayerConfig layerConfig, boolean z) {
        String str;
        this.clockCtx = clockContext;
        this.layerCfg = layerConfig;
        SimpleDigitalClockTextView simpleDigitalClockTextView = new SimpleDigitalClockTextView(clockContext, z, null, 4, null);
        this.view = simpleDigitalClockTextView;
        this.logger = new Logger(clockContext.messageBuffer, SimpleDigitalHandLayerControllerKt.TAG);
        this.timespec = new DigitalTimespecHandler(layerConfig.timespec, layerConfig.dateTimeFormat, null, 4, null);
        this.onViewBoundsChanged$receiver = simpleDigitalClockTextView;
        new ClockFaceConfig(null, false, false, false, 15, null);
        simpleDigitalClockTextView.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        DigitalAlignment digitalAlignment = layerConfig.alignment;
        VerticalAlignment verticalAlignment = digitalAlignment.verticalAlignment;
        if (verticalAlignment != null) {
            simpleDigitalClockTextView.verticalAlignment = verticalAlignment;
        }
        HorizontalAlignment horizontalAlignment = digitalAlignment.horizontalAlignment;
        if (horizontalAlignment != null) {
            simpleDigitalClockTextView.horizontalAlignment = horizontalAlignment;
        }
        FontTextStyle fontTextStyle = layerConfig.style;
        simpleDigitalClockTextView.textStyle = fontTextStyle;
        simpleDigitalClockTextView.lockScreenPaint.setStrokeJoin(Paint.Join.ROUND);
        simpleDigitalClockTextView.lockScreenPaint.setTypeface(simpleDigitalClockTextView.typefaceCache.getTypefaceForVariant(simpleDigitalClockTextView.lsFontVariation));
        simpleDigitalClockTextView.setTypeface(simpleDigitalClockTextView.lockScreenPaint.getTypeface());
        Float f = fontTextStyle.lineHeight;
        if (f != null) {
            simpleDigitalClockTextView.setLineHeight(MathKt__MathJVMKt.roundToInt(f.floatValue()));
        }
        FontTextStyle fontTextStyle2 = layerConfig.aodStyle;
        if (fontTextStyle2 == null) {
            fontTextStyle2 = new FontTextStyle(fontTextStyle.lineHeight, fontTextStyle.fontSizeScale, fontTextStyle.transitionDuration, fontTextStyle.transitionInterpolator);
        }
        simpleDigitalClockTextView.aodStyle = fontTextStyle2;
        Interpolator interpolator = fontTextStyle2.transitionInterpolator;
        simpleDigitalClockTextView.aodDozingInterpolator = interpolator == null ? Interpolators.LINEAR : interpolator;
        simpleDigitalClockTextView.lockScreenPaint.setStrokeWidth(0.0f);
        simpleDigitalClockTextView.measure(0, 0);
        simpleDigitalClockTextView.setInterpolatorPaint();
        simpleDigitalClockTextView.recomputeMaxSingleDigitSizes();
        simpleDigitalClockTextView.invalidate();
        Resources resources = clockContext.resources;
        DigitalTimespec digitalTimespec = DigitalTimespec.TIME_FULL_FORMAT;
        DigitalTimespec digitalTimespec2 = layerConfig.timespec;
        if (digitalTimespec2 == digitalTimespec) {
            str = String.valueOf(digitalTimespec2);
        } else if (StringsKt__StringsKt.contains(layerConfig.dateTimeFormat, "h", false)) {
            str = "HOUR_" + digitalTimespec2;
        } else {
            str = "MINUTE_" + digitalTimespec2;
        }
        simpleDigitalClockTextView.setId(resources.getIdentifier(str, "id", clockContext.context.getPackageName()));
        this.events = new ClockEvents() { // from class: com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$events$1
            public boolean isReactiveTouchInteractionEnabled;

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final boolean isReactiveTouchInteractionEnabled() {
                return this.isReactiveTouchInteractionEnabled;
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onLocaleChanged(Locale locale) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                DigitalTimespecHandler digitalTimespecHandler = simpleDigitalHandLayerController.timespec;
                digitalTimespecHandler.getClass();
                boolean equals = locale.getLanguage().equals(Locale.ENGLISH.getLanguage());
                String str2 = digitalTimespecHandler.timeFormat;
                digitalTimespecHandler.dateFormat = equals ? new SimpleDateFormat(str2, str2, ULocale.forLocale(locale)) : SimpleDateFormat.getInstanceForSkeleton(str2, locale);
                digitalTimespecHandler.contentDescriptionFormat = DigitalTimespecHandler.WhenMappings.$EnumSwitchMapping$0[digitalTimespecHandler.timespec.ordinal()] == 1 ? SimpleDateFormat.getInstanceForSkeleton("hh:mm", locale) : null;
                digitalTimespecHandler.onTimeZoneChanged();
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onTimeFormatChanged(boolean z2) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                DigitalTimespecHandler digitalTimespecHandler = simpleDigitalHandLayerController.timespec;
                digitalTimespecHandler.is24Hr = z2;
                digitalTimespecHandler.applyPattern();
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onTimeZoneChanged(TimeZone timeZone) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                DigitalTimespecHandler digitalTimespecHandler = simpleDigitalHandLayerController.timespec;
                digitalTimespecHandler.cal.setTimeZone(timeZone);
                digitalTimespecHandler.onTimeZoneChanged();
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void setReactiveTouchInteractionEnabled(boolean z2) {
                this.isReactiveTouchInteractionEnabled = z2;
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
        };
        this.animations = new ClockAnimations() { // from class: com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$animations$1
            public boolean hasFontAxes;

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void charge() {
                SimpleDigitalHandLayerController.this.view.animateCharge();
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void doze(float f2) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                DefaultClockController.AnimationState animationState = simpleDigitalHandLayerController.dozeState;
                SimpleDigitalClockTextView simpleDigitalClockTextView2 = simpleDigitalHandLayerController.view;
                if (animationState == null) {
                    DefaultClockController.AnimationState animationState2 = new DefaultClockController.AnimationState(f2);
                    simpleDigitalHandLayerController.dozeState = animationState2;
                    simpleDigitalClockTextView2.animateDoze(animationState2.isActive, false);
                } else {
                    Pair update = animationState.update(f2);
                    boolean booleanValue = ((Boolean) update.component1()).booleanValue();
                    boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
                    if (booleanValue) {
                        DefaultClockController.AnimationState animationState3 = simpleDigitalHandLayerController.dozeState;
                        animationState3.getClass();
                        simpleDigitalClockTextView2.animateDoze(animationState3.isActive, !booleanValue2);
                    }
                }
                simpleDigitalClockTextView2.dozeFraction = f2;
                simpleDigitalClockTextView2.invalidate();
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void enter() {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                SimpleDigitalHandLayerController.access$applyLayout(simpleDigitalHandLayerController);
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void fold(float f2) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                SimpleDigitalHandLayerController.access$applyLayout(simpleDigitalHandLayerController);
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onFidgetTap(float f2, float f3) {
                SimpleDigitalHandLayerController.this.view.animateFidget(0L);
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onFontAxesChanged(ClockAxisStyle clockAxisStyle) {
                SimpleDigitalHandLayerController.this.view.updateAxes(clockAxisStyle, this.hasFontAxes);
                this.hasFontAxes = true;
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPositionUpdated(float f2, float f3) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPositionUpdated(int i, int i2, float f2) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPickerCarouselSwiping(float f2) {
            }
        };
        this.faceEvents = new ClockFaceEvents() { // from class: com.android.systemui.shared.clocks.SimpleDigitalHandLayerController$faceEvents$1
            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onFontSettingChanged(float f2) {
                SimpleDigitalClockTextView.applyTextSize$default(SimpleDigitalHandLayerController.this.view, Float.valueOf(f2));
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onThemeChanged(ThemeConfig themeConfig) {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                simpleDigitalHandLayerController.view.updateColor(themeConfig.getDefaultColor(simpleDigitalHandLayerController.clockCtx.context));
                simpleDigitalHandLayerController.refreshTime$1();
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onTimeTick() {
                SimpleDigitalHandLayerController simpleDigitalHandLayerController = SimpleDigitalHandLayerController.this;
                simpleDigitalHandLayerController.refreshTime$1();
                if (simpleDigitalHandLayerController.layerCfg.timespec == DigitalTimespec.TIME_FULL_FORMAT) {
                    simpleDigitalHandLayerController.view.setContentDescription(simpleDigitalHandLayerController.timespec.getContentDescription());
                }
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onSecondaryDisplayChanged(boolean z2) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onTargetRegionChanged(Rect rect) {
            }
        };
    }

    public static final void access$applyLayout(SimpleDigitalHandLayerController simpleDigitalHandLayerController) {
        SimpleDigitalClockTextView simpleDigitalClockTextView = simpleDigitalHandLayerController.view;
        if (simpleDigitalClockTextView.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) simpleDigitalClockTextView.getLayoutParams();
            layoutParams.addRule(4);
            int id = simpleDigitalClockTextView.getId();
            int i = R$id.HOUR_DIGIT_PAIR;
            if (id == i) {
                layoutParams.addRule(15);
                layoutParams.addRule(20);
            } else {
                if (id != R$id.MINUTE_DIGIT_PAIR) {
                    throw new Exception(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(simpleDigitalClockTextView.getId(), "cannot apply two pairs layout to view "));
                }
                layoutParams.addRule(15);
                layoutParams.addRule(17, i);
            }
            simpleDigitalClockTextView.setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final ClockAnimations getAnimations() {
        return this.animations;
    }

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final ClockEvents getEvents() {
        return this.events;
    }

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final ClockFaceEvents getFaceEvents() {
        return this.faceEvents;
    }

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final View getView() {
        return this.view;
    }

    public final void refreshTime$1() {
        String str;
        DigitalTimespecHandler digitalTimespecHandler = this.timespec;
        digitalTimespecHandler.getClass();
        digitalTimespecHandler.cal.setTimeInMillis((long) (System.currentTimeMillis() * 1.0d));
        int i = DigitalTimespecHandler.WhenMappings.$EnumSwitchMapping$0[digitalTimespecHandler.timespec.ordinal()];
        if (i == 1) {
            str = digitalTimespecHandler.dateFormat.format(digitalTimespecHandler.cal.getTime()).toString();
        } else if (i == 2) {
            str = digitalTimespecHandler.dateFormat.format(digitalTimespecHandler.cal.getTime()).toString().substring(0, 1);
        } else if (i == 3) {
            str = digitalTimespecHandler.dateFormat.format(digitalTimespecHandler.cal.getTime()).toString().substring(1, 2);
        } else {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            str = digitalTimespecHandler.dateFormat.format(digitalTimespecHandler.cal.getTime()).toString();
        }
        SimpleDigitalClockTextView simpleDigitalClockTextView = this.view;
        if (Intrinsics.areEqual(simpleDigitalClockTextView.getText(), str)) {
            return;
        }
        simpleDigitalClockTextView.setText(str);
        simpleDigitalClockTextView.getLogger().refreshTime();
        simpleDigitalClockTextView.textBounds = SimpleDigitalClockTextViewKt.access$getTextBounds(simpleDigitalClockTextView.lockScreenPaint, simpleDigitalClockTextView.getText());
        simpleDigitalClockTextView.updateAnimationTextBounds();
        if (simpleDigitalClockTextView.getLayout() == null) {
            simpleDigitalClockTextView.requestLayout();
        } else {
            TextAnimator textAnimator = simpleDigitalClockTextView.textAnimator;
            if (textAnimator == null) {
                textAnimator = null;
            }
            textAnimator.updateLayout(simpleDigitalClockTextView.getLayout(), -1.0f);
        }
        Logger logger = this.logger;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new SimpleDigitalHandLayerController$$ExternalSyntheticLambda0(), null);
        obtain.setStr1(str);
        logger.getBuffer().commit(obtain);
    }

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final void setOnViewBoundsChanged(Function1 function1) {
        this.onViewBoundsChanged$receiver.onViewBoundsChanged = function1;
    }

    public static /* synthetic */ void getFakeTimeMills$annotations() {
    }
}
