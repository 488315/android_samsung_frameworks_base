package com.android.systemui.shared.clocks;

import android.graphics.Rect;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.VPoint;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.shared.clocks.DefaultClockController;
import com.android.systemui.shared.clocks.DigitTranslateAnimator;
import com.android.systemui.shared.clocks.ViewUtils;
import com.android.systemui.shared.clocks.view.FlexClockView;
import com.android.systemui.shared.clocks.view.FlexClockView$$ExternalSyntheticLambda1;
import com.android.systemui.shared.clocks.view.HorizontalAlignment;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextViewKt;
import com.android.systemui.shared.clocks.view.VerticalAlignment;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ComposedDigitalLayerController implements SimpleClockLayerController {
    public final ComposedDigitalLayerController$animations$1 animations;
    public final ClockContext clockCtx;
    public final DefaultClockController.AnimationState dozeState;
    public final ComposedDigitalLayerController$events$1 events;
    public final ComposedDigitalLayerController$faceEvents$1 faceEvents;
    public final List layerControllers;
    public final FlexClockView onViewBoundsChanged$receiver;
    public final FlexClockView view;

    /* JADX WARN: Type inference failed for: r14v6, types: [com.android.systemui.shared.clocks.ComposedDigitalLayerController$events$1] */
    /* JADX WARN: Type inference failed for: r14v7, types: [com.android.systemui.shared.clocks.ComposedDigitalLayerController$animations$1] */
    /* JADX WARN: Type inference failed for: r14v8, types: [com.android.systemui.shared.clocks.ComposedDigitalLayerController$faceEvents$1] */
    public ComposedDigitalLayerController(ClockContext clockContext) {
        this.clockCtx = clockContext;
        MessageBuffer messageBuffer = clockContext.messageBuffer;
        String simpleName = Reflection.getOrCreateKotlinClass(ComposedDigitalLayerController.class).getSimpleName();
        simpleName.getClass();
        new Logger(messageBuffer, simpleName);
        this.layerControllers = new ArrayList();
        this.dozeState = new DefaultClockController.AnimationState(1.0f);
        FlexClockView flexClockView = new FlexClockView(clockContext);
        this.view = flexClockView;
        this.onViewBoundsChanged$receiver = flexClockView;
        LayerConfig layerConfig = new LayerConfig(new FontTextStyle(Float.valueOf(147.25f), null, 0L, null, 14, null), new FontTextStyle(null, null, 750L, Interpolators.EMPHASIZED, 3, null), new DigitalAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER), DigitalTimespec.TIME_FULL_FORMAT, "hh:mm");
        DigitalTimespec digitalTimespec = DigitalTimespec.FIRST_DIGIT;
        _init_$createController(this, LayerConfig.copy$default(layerConfig, digitalTimespec, "hh"));
        DigitalTimespec digitalTimespec2 = DigitalTimespec.SECOND_DIGIT;
        _init_$createController(this, LayerConfig.copy$default(layerConfig, digitalTimespec2, "hh"));
        _init_$createController(this, LayerConfig.copy$default(layerConfig, digitalTimespec, "mm"));
        _init_$createController(this, LayerConfig.copy$default(layerConfig, digitalTimespec2, "mm"));
        this.events = new ClockEvents() { // from class: com.android.systemui.shared.clocks.ComposedDigitalLayerController$events$1
            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final boolean isReactiveTouchInteractionEnabled() {
                return ComposedDigitalLayerController.this.view.isReactiveTouchInteractionEnabled;
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onLocaleChanged(Locale locale) {
                ComposedDigitalLayerController composedDigitalLayerController = ComposedDigitalLayerController.this;
                ArrayList arrayList = (ArrayList) composedDigitalLayerController.layerControllers;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SimpleClockLayerController) obj).getEvents().onLocaleChanged(locale);
                }
                FlexClockView flexClockView2 = composedDigitalLayerController.view;
                flexClockView2.getClass();
                FlexClockView.updateLocale(locale);
                flexClockView2.requestLayout();
                ComposedDigitalLayerController.access$refreshTime(composedDigitalLayerController);
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onTimeFormatChanged(boolean z) {
                ComposedDigitalLayerController composedDigitalLayerController = ComposedDigitalLayerController.this;
                ArrayList arrayList = (ArrayList) composedDigitalLayerController.layerControllers;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SimpleClockLayerController) obj).getEvents().onTimeFormatChanged(z);
                }
                ComposedDigitalLayerController.access$refreshTime(composedDigitalLayerController);
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void onTimeZoneChanged(TimeZone timeZone) {
                ComposedDigitalLayerController composedDigitalLayerController = ComposedDigitalLayerController.this;
                ArrayList arrayList = (ArrayList) composedDigitalLayerController.layerControllers;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SimpleClockLayerController) obj).getEvents().onTimeZoneChanged(timeZone);
                }
                ComposedDigitalLayerController.access$refreshTime(composedDigitalLayerController);
            }

            @Override // com.android.systemui.plugins.clocks.ClockEvents
            public final void setReactiveTouchInteractionEnabled(boolean z) {
                ComposedDigitalLayerController.this.view.isReactiveTouchInteractionEnabled = z;
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
        this.animations = new ClockAnimations() { // from class: com.android.systemui.shared.clocks.ComposedDigitalLayerController$animations$1
            public boolean hasFontAxes;

            /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.shared.clocks.view.FlexClockView$animateCharge$2$1$1] */
            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void charge() {
                final FlexClockView flexClockView2 = ComposedDigitalLayerController.this.view;
                Iterator it = flexClockView2.getChildViews().iterator();
                while (it.hasNext()) {
                    ((SimpleDigitalClockTextView) it.next()).animateCharge();
                }
                for (final SimpleDigitalClockTextView simpleDigitalClockTextView : flexClockView2.getChildViews()) {
                    final DigitTranslateAnimator digitTranslateAnimator = simpleDigitalClockTextView.digitTranslateAnimator;
                    if (digitTranslateAnimator != null) {
                        boolean z = flexClockView2.isAnimationEnabled;
                        Interpolator interpolator = Interpolators.EMPHASIZED;
                        long j = FlexClockView.CHARGING_TRANSITION_DURATION;
                        ?? r9 = new Runnable() { // from class: com.android.systemui.shared.clocks.view.FlexClockView$animateCharge$2$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                boolean z2 = FlexClockView.this.isAnimationEnabled;
                                Interpolator interpolator2 = Interpolators.EMPHASIZED;
                                FlexClockView.Companion.getClass();
                                long j2 = FlexClockView.CHARGING_TRANSITION_DURATION;
                                int id = simpleDigitalClockTextView.getId();
                                FlexClockView flexClockView3 = FlexClockView.this;
                                DigitTranslateAnimator.m2932animatePositionWofAHi4$default(digitTranslateAnimator, z2, j2, interpolator2, FlexClockView.Companion.m2933updateDirectionalTargetTranslateNvxBqkk(id, flexClockView3.dozeFraction == 1.0f ? flexClockView3.aodTranslate : flexClockView3.lockscreenTranslate), null, 34);
                            }
                        };
                        FlexClockView.Companion companion = FlexClockView.Companion;
                        int id = simpleDigitalClockTextView.getId();
                        long j2 = flexClockView2.dozeFraction == 1.0f ? flexClockView2.lockscreenTranslate : flexClockView2.aodTranslate;
                        companion.getClass();
                        DigitTranslateAnimator.m2932animatePositionWofAHi4$default(digitTranslateAnimator, z, j, interpolator, FlexClockView.Companion.m2933updateDirectionalTargetTranslateNvxBqkk(id, j2), r9, 2);
                    }
                }
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void doze(float f) {
                ComposedDigitalLayerController composedDigitalLayerController = ComposedDigitalLayerController.this;
                Pair update = composedDigitalLayerController.dozeState.update(f);
                boolean booleanValue = ((Boolean) update.component1()).booleanValue();
                boolean booleanValue2 = ((Boolean) update.component2()).booleanValue();
                FlexClockView flexClockView2 = composedDigitalLayerController.view;
                if (booleanValue) {
                    boolean z = composedDigitalLayerController.dozeState.isActive;
                    boolean z2 = !booleanValue2;
                    if (flexClockView2.isDozeReadyToAnimate) {
                        FlexClockView.animateDoze$executeDozeAnimation(flexClockView2, z, z2);
                    } else {
                        flexClockView2.onAnimateDoze = new FlexClockView$$ExternalSyntheticLambda1(flexClockView2, z, z2);
                    }
                }
                flexClockView2.dozeFraction = f;
                for (SimpleDigitalClockTextView simpleDigitalClockTextView : flexClockView2.getChildViews()) {
                    simpleDigitalClockTextView.dozeFraction = flexClockView2.dozeFraction;
                    simpleDigitalClockTextView.invalidate();
                }
                flexClockView2.invalidate();
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void enter() {
                ComposedDigitalLayerController.access$refreshTime(ComposedDigitalLayerController.this);
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void fold(float f) {
                ComposedDigitalLayerController.access$refreshTime(ComposedDigitalLayerController.this);
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onFidgetTap(float f, float f2) {
                FlexClockView flexClockView2 = ComposedDigitalLayerController.this.view;
                flexClockView2.getClass();
                final long m2731constructorimpl = VPointF.m2731constructorimpl(f, f2);
                int i = 0;
                final int[] iArr = {0, 0};
                for (Object obj : CollectionsKt___CollectionsKt.sortedWith(flexClockView2.getChildViews(), new Comparator() { // from class: com.android.systemui.shared.clocks.view.FlexClockView$animateFidget$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        SimpleDigitalClockTextView simpleDigitalClockTextView = (SimpleDigitalClockTextView) obj2;
                        simpleDigitalClockTextView.getLocationInWindow(iArr);
                        int[] iArr2 = iArr;
                        long m2685constructorimpl = VPoint.m2685constructorimpl(iArr2[0], iArr2[1]);
                        ViewUtils viewUtils = ViewUtils.INSTANCE;
                        viewUtils.getClass();
                        Float valueOf = Float.valueOf(VPointF.m2747lengthimpl(VPointF.m2751minusb2IjXjg(VPoint.m2703plusb2IjXjg(m2685constructorimpl, VPointF.m2737divAsyRdg(VPointF.m2734constructorimpl(simpleDigitalClockTextView.getMeasuredWidth(), simpleDigitalClockTextView.getMeasuredHeight()), 2.0f)), m2731constructorimpl)));
                        SimpleDigitalClockTextView simpleDigitalClockTextView2 = (SimpleDigitalClockTextView) obj3;
                        simpleDigitalClockTextView2.getLocationInWindow(iArr);
                        int[] iArr3 = iArr;
                        long m2685constructorimpl2 = VPoint.m2685constructorimpl(iArr3[0], iArr3[1]);
                        viewUtils.getClass();
                        return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Float.valueOf(VPointF.m2747lengthimpl(VPointF.m2751minusb2IjXjg(VPoint.m2703plusb2IjXjg(m2685constructorimpl2, VPointF.m2737divAsyRdg(VPointF.m2734constructorimpl(simpleDigitalClockTextView2.getMeasuredWidth(), simpleDigitalClockTextView2.getMeasuredHeight()), 2.0f)), m2731constructorimpl))));
                    }
                })) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    ((SimpleDigitalClockTextView) obj).animateFidget(((Number) FlexClockView.FIDGET_DELAYS.get(Math.min(i, r1.size() - 1))).longValue());
                    i = i2;
                }
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onFontAxesChanged(ClockAxisStyle clockAxisStyle) {
                FlexClockView flexClockView2 = ComposedDigitalLayerController.this.view;
                boolean z = this.hasFontAxes;
                Iterator it = flexClockView2.getChildViews().iterator();
                while (it.hasNext()) {
                    ((SimpleDigitalClockTextView) it.next()).updateAxes(clockAxisStyle, z);
                }
                flexClockView2.requestLayout();
                this.hasFontAxes = true;
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPositionUpdated(float f, float f2) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPositionUpdated(int i, int i2, float f) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockAnimations
            public final void onPickerCarouselSwiping(float f) {
            }
        };
        this.faceEvents = new ClockFaceEvents() { // from class: com.android.systemui.shared.clocks.ComposedDigitalLayerController$faceEvents$1
            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onFontSettingChanged(float f) {
                Iterator it = ComposedDigitalLayerController.this.view.getChildViews().iterator();
                while (it.hasNext()) {
                    SimpleDigitalClockTextView.applyTextSize$default((SimpleDigitalClockTextView) it.next(), Float.valueOf(f));
                }
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onThemeChanged(ThemeConfig themeConfig) {
                ComposedDigitalLayerController composedDigitalLayerController = ComposedDigitalLayerController.this;
                FlexClockView flexClockView2 = composedDigitalLayerController.view;
                int defaultColor = themeConfig.getDefaultColor(composedDigitalLayerController.clockCtx.context);
                Iterator it = flexClockView2.getChildViews().iterator();
                while (it.hasNext()) {
                    ((SimpleDigitalClockTextView) it.next()).updateColor(defaultColor);
                }
                flexClockView2.invalidate();
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onTimeTick() {
                ComposedDigitalLayerController.access$refreshTime(ComposedDigitalLayerController.this);
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onSecondaryDisplayChanged(boolean z) {
            }

            @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
            public final void onTargetRegionChanged(Rect rect) {
            }
        };
        new ClockFaceConfig(null, false, true, false, 9, null);
    }

    public static final void _init_$createController(ComposedDigitalLayerController composedDigitalLayerController, LayerConfig layerConfig) {
        SimpleDigitalHandLayerController simpleDigitalHandLayerController = new SimpleDigitalHandLayerController(composedDigitalLayerController.clockCtx, layerConfig, true);
        composedDigitalLayerController.view.addView(simpleDigitalHandLayerController.view);
        ((ArrayList) composedDigitalLayerController.layerControllers).add(simpleDigitalHandLayerController);
    }

    public static final void access$refreshTime(ComposedDigitalLayerController composedDigitalLayerController) {
        ArrayList arrayList = (ArrayList) composedDigitalLayerController.layerControllers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SimpleClockLayerController) obj).getFaceEvents().onTimeTick();
        }
        FlexClockView flexClockView = composedDigitalLayerController.view;
        flexClockView.getLogger().refreshTime();
        for (SimpleDigitalClockTextView simpleDigitalClockTextView : flexClockView.getChildViews()) {
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

    @Override // com.android.systemui.shared.clocks.SimpleClockLayerController
    public final void setOnViewBoundsChanged(Function1 function1) {
        this.onViewBoundsChanged$receiver.onViewBoundsChanged = function1;
    }

    public static /* synthetic */ void getFakeTimeMills$annotations() {
    }
}
