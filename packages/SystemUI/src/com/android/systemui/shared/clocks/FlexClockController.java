package com.android.systemui.shared.clocks;

import android.content.res.Resources;
import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.animation.GSFAxes;
import com.android.systemui.customization.R$drawable;
import com.android.systemui.customization.R$string;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.AxisPresetConfig;
import com.android.systemui.plugins.clocks.AxisType;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockEventListener;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFontAxis;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.VRectF;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.shared.clocks.FlexClockController;
import com.android.systemui.shared.clocks.FlexClockFaceController;
import com.android.systemui.shared.clocks.view.FlexClockView;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes3.dex */
public final class FlexClockController implements ClockController {
    public static final List BASE_PRESETS;
    public static final Companion Companion = new Companion(null);
    public static final List FONT_AXES;
    public static final ClockAxisStyle LEGACY_FLEX_SETTINGS;
    public static final float PRESET_WEIGHT_STEP;
    public static final float PRESET_WIDTH_STEP;
    public final ClockContext clockCtx;
    public final Lazy config$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shared.clocks.FlexClockController$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            FlexClockController flexClockController = this.f$0;
            return new ClockConfig("DEFAULT", flexClockController.clockCtx.resources.getString(R$string.clock_default_name), flexClockController.clockCtx.resources.getString(R$string.clock_default_description), false, false, 24, null);
        }
    });
    public final FlexClockController$events$1 events = new ClockEvents() { // from class: com.android.systemui.shared.clocks.FlexClockController$events$1
        public boolean isReactiveTouchInteractionEnabled;

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final boolean isReactiveTouchInteractionEnabled() {
            return this.isReactiveTouchInteractionEnabled;
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onAlarmDataChanged(AlarmData alarmData) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onAlarmDataChanged(alarmData);
            flexClockController.largeClock.events.onAlarmDataChanged(alarmData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onLocaleChanged(Locale locale) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onLocaleChanged(locale);
            flexClockController.largeClock.events.onLocaleChanged(locale);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeFormatChanged(boolean z) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onTimeFormatChanged(z);
            flexClockController.largeClock.events.onTimeFormatChanged(z);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onTimeZoneChanged(TimeZone timeZone) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onTimeZoneChanged(timeZone);
            flexClockController.largeClock.events.onTimeZoneChanged(timeZone);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onWeatherDataChanged(WeatherData weatherData) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onWeatherDataChanged(weatherData);
            flexClockController.largeClock.events.onWeatherDataChanged(weatherData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void onZenDataChanged(ZenData zenData) {
            FlexClockController flexClockController = this.this$0;
            flexClockController.smallClock.events.onZenDataChanged(zenData);
            flexClockController.largeClock.events.onZenDataChanged(zenData);
        }

        @Override // com.android.systemui.plugins.clocks.ClockEvents
        public final void setReactiveTouchInteractionEnabled(boolean z) {
            this.isReactiveTouchInteractionEnabled = z;
            ((FlexClockView) this.this$0.largeClock.layerController.getView()).isReactiveTouchInteractionEnabled = z;
        }
    };
    public final FlexClockFaceController largeClock;
    public final FlexClockFaceController smallClock;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static AxisPresetConfig.Group buildPresetGroup(Resources resources, boolean z) {
            final float f;
            if (z) {
                GSFAxes.INSTANCE.getClass();
                f = GSFAxes.ROUND.maxValue;
            } else {
                GSFAxes.INSTANCE.getClass();
                f = GSFAxes.ROUND.minValue;
            }
            List list = FlexClockController.BASE_PRESETS;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            ArrayList arrayList2 = (ArrayList) list;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                arrayList.add(((ClockAxisStyle) obj).copy(new Function1() { // from class: com.android.systemui.shared.clocks.FlexClockController$Companion$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        FontUtils fontUtils = FontUtils.INSTANCE;
                        GSFAxes.INSTANCE.getClass();
                        AxisDefinition axisDefinition = GSFAxes.ROUND;
                        fontUtils.getClass();
                        ((ClockAxisStyle) obj2).put(axisDefinition.tag, f);
                        return Unit.INSTANCE;
                    }
                }));
            }
            return new AxisPresetConfig.Group(arrayList, resources.getDrawable(R$drawable.clock_default_thumbnail, null));
        }

        public static List getDefaultAxes(ClockSettings clockSettings) {
            return Intrinsics.areEqual(clockSettings.getClockId(), "DIGITAL_CLOCK_FLEX") ? ClockFontAxis.Companion.merge(FlexClockController.FONT_AXES, FlexClockController.LEGACY_FLEX_SETTINGS) : FlexClockController.FONT_AXES;
        }

        private Companion() {
        }
    }

    static {
        FontUtils fontUtils = FontUtils.INSTANCE;
        AxisDefinition axisDefinition = GSFAxes.WEIGHT;
        AxisType axisType = AxisType.Float;
        Float fValueOf = Float.valueOf(400.0f);
        fontUtils.getClass();
        ClockFontAxis clockAxis = FontUtils.toClockAxis(axisDefinition, axisType, fValueOf, "Weight", "Glyph Weight");
        GSFAxes.INSTANCE.getClass();
        ClockFontAxis clockAxis2 = FontUtils.toClockAxis(GSFAxes.WIDTH, axisType, Float.valueOf(80.0f), "Width", "Glyph Width");
        AxisDefinition axisDefinition2 = GSFAxes.ROUND;
        AxisType axisType2 = AxisType.Boolean;
        FONT_AXES = Arrays.asList(clockAxis, clockAxis2, FontUtils.toClockAxis(axisDefinition2, axisType2, Float.valueOf(100.0f), "Round", "Glyph Roundness"), FontUtils.toClockAxis(GSFAxes.SLANT, axisType2, Float.valueOf(0.0f), "Slant", "Glyph Slant"));
        LEGACY_FLEX_SETTINGS = new ClockAxisStyle(new FlexClockController$$ExternalSyntheticLambda1());
        PRESET_WIDTH_STEP = 12.5f;
        PRESET_WEIGHT_STEP = -100.0f;
        ArrayList arrayList = new ArrayList();
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = 800.0f;
        final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
        ref$FloatRef2.element = 30.0f;
        int i = 1;
        while (true) {
            arrayList.add(new ClockAxisStyle(new Function1() { // from class: com.android.systemui.shared.clocks.FlexClockController$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ClockAxisStyle clockAxisStyle = (ClockAxisStyle) obj;
                    FlexClockController.Companion companion = FlexClockController.Companion;
                    FontUtils fontUtils2 = FontUtils.INSTANCE;
                    AxisDefinition axisDefinition3 = GSFAxes.WEIGHT;
                    float f = ref$FloatRef.element;
                    fontUtils2.getClass();
                    clockAxisStyle.put(axisDefinition3.tag, f);
                    GSFAxes.INSTANCE.getClass();
                    AxisDefinition axisDefinition4 = GSFAxes.WIDTH;
                    clockAxisStyle.put(axisDefinition4.tag, ref$FloatRef2.element);
                    clockAxisStyle.put(GSFAxes.ROUND.tag, 0.0f);
                    clockAxisStyle.put(GSFAxes.SLANT.tag, 0.0f);
                    return Unit.INSTANCE;
                }
            }));
            ref$FloatRef.element += PRESET_WEIGHT_STEP;
            ref$FloatRef2.element += PRESET_WIDTH_STEP;
            if (i == 8) {
                BASE_PRESETS = arrayList;
                return;
            }
            i++;
        }
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.shared.clocks.FlexClockController$events$1] */
    public FlexClockController(ClockContext clockContext) {
        this.clockCtx = clockContext;
        this.smallClock = new FlexClockFaceController(ClockContext.copy$default(clockContext, clockContext.messageBuffers.getSmallClockMessageBuffer()), false);
        this.largeClock = new FlexClockFaceController(ClockContext.copy$default(clockContext, clockContext.messageBuffers.getLargeClockMessageBuffer()), true);
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
    public final void initialize(boolean z, float f, float f2, final ClockEventListener clockEventListener) {
        FlexClockFaceController flexClockFaceController = this.smallClock;
        flexClockFaceController.layerController.setOnViewBoundsChanged(new Function1() { // from class: com.android.systemui.shared.clocks.FlexClockController$initialize$1$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long jM2847unboximpl = ((VRectF) obj).m2847unboximpl();
                ClockEventListener clockEventListener2 = clockEventListener;
                if (clockEventListener2 != null) {
                    clockEventListener2.mo947onBoundsChangedTTAm5xc(jM2847unboximpl);
                }
                return Unit.INSTANCE;
            }
        });
        ThemeConfig themeConfigCopy$default = ThemeConfig.copy$default(flexClockFaceController.theme, z, null, 2, null);
        FlexClockFaceController.FlexClockFaceEvents flexClockFaceEvents = flexClockFaceController.events;
        flexClockFaceEvents.onThemeChanged(themeConfigCopy$default);
        ClockContext clockContext = this.clockCtx;
        ClockAxisStyle axes = clockContext.settings.getAxes();
        FlexClockFaceController$animations$1 flexClockFaceController$animations$1 = flexClockFaceController.animations;
        flexClockFaceController$animations$1.onFontAxesChanged(axes);
        flexClockFaceController$animations$1.doze(f);
        flexClockFaceController$animations$1.fold(f2);
        flexClockFaceEvents.onTimeTick();
        FlexClockFaceController flexClockFaceController2 = this.largeClock;
        flexClockFaceController2.layerController.setOnViewBoundsChanged(new Function1() { // from class: com.android.systemui.shared.clocks.FlexClockController$initialize$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long jM2847unboximpl = ((VRectF) obj).m2847unboximpl();
                ClockEventListener clockEventListener2 = clockEventListener;
                if (clockEventListener2 != null) {
                    clockEventListener2.mo947onBoundsChangedTTAm5xc(jM2847unboximpl);
                }
                return Unit.INSTANCE;
            }
        });
        ThemeConfig themeConfigCopy$default2 = ThemeConfig.copy$default(flexClockFaceController2.theme, z, null, 2, null);
        FlexClockFaceController.FlexClockFaceEvents flexClockFaceEvents2 = flexClockFaceController2.events;
        flexClockFaceEvents2.onThemeChanged(themeConfigCopy$default2);
        ClockAxisStyle axes2 = clockContext.settings.getAxes();
        FlexClockFaceController$animations$1 flexClockFaceController$animations$12 = flexClockFaceController2.animations;
        flexClockFaceController$animations$12.onFontAxesChanged(axes2);
        flexClockFaceController$animations$12.doze(f);
        flexClockFaceController$animations$12.fold(f2);
        flexClockFaceEvents2.onTimeTick();
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public final void dump(PrintWriter printWriter) {
    }
}
