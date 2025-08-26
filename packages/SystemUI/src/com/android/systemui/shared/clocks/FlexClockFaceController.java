package com.android.systemui.shared.clocks;

import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.icu.util.ULocale;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.customization.R$id;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.plugins.clocks.DefaultClockFaceLayout;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.VPoint;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.shared.clocks.DigitalTimespecHandler;
import com.android.systemui.shared.clocks.view.HorizontalAlignment;
import com.android.systemui.shared.clocks.view.VerticalAlignment;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public final class FlexClockFaceController implements ClockFaceController {
    public static final Companion Companion = new Companion(null);
    public static final float SMALL_CLOCK_MAX_WDTH = 120.0f;
    public static final LayerConfig SMALL_LAYER_CONFIG = new LayerConfig(new FontTextStyle(null, Float.valueOf(0.98f), 0, null, 13, null), new FontTextStyle(null, null, 0, null, 15, null), new DigitalAlignment(HorizontalAlignment.START, VerticalAlignment.CENTER), DigitalTimespec.TIME_FULL_FORMAT, "h:mm");
    public final FlexClockFaceController$animations$1 animations;
    public final FlexClockFaceEvents events;
    public final boolean isLargeClock;
    public final int keyguardLargeClockTopMargin;
    public final SimpleClockLayerController layerController;
    public final DefaultClockFaceLayout layout;
    public ThemeConfig theme;
    public final ClockFaceConfig config = new ClockFaceConfig(null, false, true, false, 11, null);
    public final DigitalTimespecHandler timespecHandler = new DigitalTimespecHandler(DigitalTimespec.TIME_FULL_FORMAT, "hh:mm", null, 4, null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public FlexClockFaceController(ClockContext clockContext, boolean z) {
        this.isLargeClock = z;
        this.theme = new ThemeConfig(true, clockContext.settings.getSeedColor());
        this.keyguardLargeClockTopMargin = clockContext.resources.getDimensionPixelSize(R$dimen.keyguard_large_clock_top_margin);
        SimpleClockLayerController composedDigitalLayerController = z ? new ComposedDigitalLayerController(clockContext) : new SimpleDigitalHandLayerController(clockContext, SMALL_LAYER_CONFIG, z);
        this.layerController = composedDigitalLayerController;
        View view = composedDigitalLayerController.getView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
        DefaultClockFaceLayout defaultClockFaceLayout = new DefaultClockFaceLayout(composedDigitalLayerController.getView());
        defaultClockFaceLayout.getViews().get(0).setId(z ? R$id.lockscreen_clock_view_large : R$id.lockscreen_clock_view);
        this.layout = defaultClockFaceLayout;
        this.events = new FlexClockFaceEvents();
        this.animations = new FlexClockFaceController$animations$1(this, clockContext);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final ClockAnimations getAnimations() {
        return this.animations;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final ClockFaceConfig getConfig() {
        return this.config;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final ClockFaceEvents getEvents() {
        return this.events;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final ClockFaceLayout getLayout() {
        return this.layout;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final ThemeConfig getTheme() {
        return this.theme;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceController
    public final View getView() {
        return this.layerController.getView();
    }

    public final class FlexClockFaceEvents implements ClockEvents, ClockFaceEvents {
        public boolean isReactiveTouchInteractionEnabled;

        public FlexClockFaceEvents() {
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final boolean isReactiveTouchInteractionEnabled() {
            return this.isReactiveTouchInteractionEnabled;
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onAlarmDataChanged(AlarmData alarmData) {
            FlexClockFaceController.this.layerController.getEvents().onAlarmDataChanged(alarmData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
        public final void onFontSettingChanged(float f) {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            flexClockFaceController.layerController.getFaceEvents().onFontSettingChanged(f);
            flexClockFaceController.layerController.getView().requestLayout();
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onLocaleChanged(Locale locale) {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            DigitalTimespecHandler digitalTimespecHandler = flexClockFaceController.timespecHandler;
            digitalTimespecHandler.getClass();
            boolean zEquals = locale.getLanguage().equals(Locale.ENGLISH.getLanguage());
            String str = digitalTimespecHandler.timeFormat;
            digitalTimespecHandler.dateFormat = zEquals ? new SimpleDateFormat(str, str, ULocale.forLocale(locale)) : SimpleDateFormat.getInstanceForSkeleton(str, locale);
            digitalTimespecHandler.contentDescriptionFormat = DigitalTimespecHandler.WhenMappings.$EnumSwitchMapping$0[digitalTimespecHandler.timespec.ordinal()] == 1 ? SimpleDateFormat.getInstanceForSkeleton("hh:mm", locale) : null;
            digitalTimespecHandler.onTimeZoneChanged();
            flexClockFaceController.layerController.getEvents().onLocaleChanged(locale);
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00b2  */
        @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onTargetRegionChanged(Rect rect) {
            FrameLayout.LayoutParams layoutParams;
            long jM2717minusb2IjXjg;
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            flexClockFaceController.layerController.getFaceEvents().onTargetRegionChanged(rect);
            SimpleClockLayerController simpleClockLayerController = flexClockFaceController.layerController;
            float fMax = Math.max(0.0f, simpleClockLayerController.getView().getLayoutParams().width);
            float fMax2 = Math.max(0.0f, simpleClockLayerController.getView().getLayoutParams().height);
            if (fMax2 <= 0.0f || fMax <= 0.0f || rect == null) {
                layoutParams = new FrameLayout.LayoutParams(-1, -1);
            } else {
                float fWidth = fMax / fMax2 > ((float) rect.width()) / ((float) rect.height()) ? rect.width() / fMax : rect.height() / fMax2;
                layoutParams = new FrameLayout.LayoutParams(MathKt__MathJVMKt.roundToInt(fMax * fWidth), MathKt__MathJVMKt.roundToInt(fMax2 * fWidth));
            }
            layoutParams.gravity = 17;
            simpleClockLayerController.getView().setLayoutParams(layoutParams);
            if (rect != null) {
                ViewUtils viewUtils = ViewUtils.INSTANCE;
                View view = simpleClockLayerController.getView();
                viewUtils.getClass();
                Object parent = view.getParent();
                if (parent instanceof View) {
                    View view2 = (View) parent;
                    jM2717minusb2IjXjg = (view2.isLaidOut() && flexClockFaceController.isLargeClock) ? VPoint.m2717minusb2IjXjg(VPoint.Companion.m2734getCenterDO4cnVw(rect), VPointF.m2755divAsyRdg(VPointF.m2752constructorimpl(view2.getWidth(), view2.getHeight()), 2.0f)) : VPointF.Companion.m2790getZEROJv7bpU8();
                }
                simpleClockLayerController.getView().setTranslationX(VPointF.m2762getXimpl(jM2717minusb2IjXjg));
                simpleClockLayerController.getView().setTranslationY(VPointF.m2763getYimpl(jM2717minusb2IjXjg));
            }
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
        public final void onThemeChanged(ThemeConfig themeConfig) {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            flexClockFaceController.theme = themeConfig;
            flexClockFaceController.layerController.getFaceEvents().onThemeChanged(themeConfig);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeFormatChanged(boolean z) {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            DigitalTimespecHandler digitalTimespecHandler = flexClockFaceController.timespecHandler;
            digitalTimespecHandler.is24Hr = z;
            digitalTimespecHandler.applyPattern();
            flexClockFaceController.layerController.getEvents().onTimeFormatChanged(z);
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
        public final void onTimeTick() {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            DigitalTimespecHandler digitalTimespecHandler = flexClockFaceController.timespecHandler;
            digitalTimespecHandler.getClass();
            digitalTimespecHandler.cal.setTimeInMillis((long) (System.currentTimeMillis() * 1.0d));
            SimpleClockLayerController simpleClockLayerController = flexClockFaceController.layerController;
            simpleClockLayerController.getView().setContentDescription(flexClockFaceController.timespecHandler.getContentDescription());
            simpleClockLayerController.getFaceEvents().onTimeTick();
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeZoneChanged(TimeZone timeZone) {
            FlexClockFaceController flexClockFaceController = FlexClockFaceController.this;
            DigitalTimespecHandler digitalTimespecHandler = flexClockFaceController.timespecHandler;
            digitalTimespecHandler.cal.setTimeZone(timeZone);
            digitalTimespecHandler.onTimeZoneChanged();
            flexClockFaceController.layerController.getEvents().onTimeZoneChanged(timeZone);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onWeatherDataChanged(WeatherData weatherData) {
            FlexClockFaceController.this.layerController.getEvents().onWeatherDataChanged(weatherData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onZenDataChanged(ZenData zenData) {
            FlexClockFaceController.this.layerController.getEvents().onZenDataChanged(zenData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void setReactiveTouchInteractionEnabled(boolean z) {
            this.isReactiveTouchInteractionEnabled = z;
            FlexClockFaceController.this.layerController.getEvents().setReactiveTouchInteractionEnabled(z);
        }

        @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
        public final void onSecondaryDisplayChanged(boolean z) {
        }
    }
}
