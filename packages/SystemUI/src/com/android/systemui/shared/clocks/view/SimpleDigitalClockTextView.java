package com.android.systemui.shared.clocks.view;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.animation.GSFAxes;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextAnimatorListener;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockLogger;
import com.android.systemui.plugins.clocks.VPoint;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.plugins.clocks.VRectF;
import com.android.systemui.shared.clocks.CanvasUtil;
import com.android.systemui.shared.clocks.ClockContext;
import com.android.systemui.shared.clocks.DigitTranslateAnimator;
import com.android.systemui.shared.clocks.DimensionParser;
import com.android.systemui.shared.clocks.FontTextStyle;
import com.android.systemui.shared.clocks.TypefaceCache;
import com.android.systemui.shared.clocks.TypefaceCache$getVariantCache$1;
import com.android.systemui.shared.clocks.ViewUtils;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public class SimpleDigitalClockTextView extends TextView {
    public static final int AOD_COLOR;
    public static final Pair AOD_WEIGHT_AXIS;
    public static final long AXIS_CHANGE_ANIMATION_DURATION;
    public static final long CHARGE_ANIMATION_DURATION;
    public static final Companion Companion = new Companion(null);
    public static final long FIDGET_ANIMATION_DURATION;
    public static final Map FIDGET_DISTS;
    public static final VibrationEffect FIDGET_HAPTICS;
    public static final PathInterpolator FIDGET_INTERPOLATOR;
    public static final Pair FLEX_AOD_LARGE_WEIGHT_AXIS;
    public static final Pair FLEX_AOD_SMALL_WEIGHT_AXIS;
    public static final Pair FLEX_AOD_WIDTH_AXIS;
    public static final Pair FLEX_LS_WEIGHT_AXIS;
    public static final Pair FLEX_LS_WIDTH_AXIS;
    public static final Pair FLEX_ROUND_AXIS;
    public static final Pair LS_WEIGHT_AXIS;
    public static final Pair ROUND_AXIS;
    public static final Pair SLANT_AXIS;
    public static final Pair WIDTH_AXIS;
    public Interpolator aodDozingInterpolator;
    public float aodFontSizePx;
    public String aodFontVariation;
    public FontTextStyle aodStyle;
    public final ClockContext clockCtx;
    public DigitTranslateAnimator digitTranslateAnimator;
    public float dozeFraction;
    public Float drawnProgress;
    public String fidgetFontVariation;
    public final ClockAxisStyle fixedAodAxes;
    public HorizontalAlignment horizontalAlignment;
    public final Thread initThread;
    public final boolean isAnimationEnabled;
    public float lastUnconstrainedTextSize;
    public long layoutBounds;
    public final TextPaint lockScreenPaint;
    public int lockscreenColor;
    public final ClockLogger logger;
    public String lsFontVariation;
    public float maxSingleDigitHeight;
    public float maxSingleDigitWidth;
    public int measuredBaseline;
    public Function1 onViewBoundsChanged;
    public long prevTextBounds;
    public long targetTextBounds;
    public TextAnimator textAnimator;
    public long textBounds;
    public FontTextStyle textStyle;
    public final TypefaceCache$getVariantCache$1 typefaceCache;
    public VerticalAlignment verticalAlignment;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final ClockAxisStyle access$fromAxes(Companion companion, Pair... pairArr) {
            companion.getClass();
            ArrayList arrayList = new ArrayList(pairArr.length);
            for (Pair pair : pairArr) {
                arrayList.add(new Pair(((AxisDefinition) pair.component1()).tag, Float.valueOf(((Number) pair.component2()).floatValue())));
            }
            return new ClockAxisStyle((Map<String, Float>) MapsKt__MapsKt.toMap(arrayList));
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[XAlignment.values().length];
            try {
                iArr[XAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[XAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[XAlignment.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VerticalAlignment.values().length];
            try {
                iArr2[VerticalAlignment.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[VerticalAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[VerticalAlignment.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VerticalAlignment.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Paint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        FIDGET_HAPTICS = VibrationEffect.startComposition().addPrimitive(2, 1.0f, 0).addPrimitive(4, 1.0f, 43).compose();
        CHARGE_ANIMATION_DURATION = 500L;
        AXIS_CHANGE_ANIMATION_DURATION = 400L;
        FIDGET_ANIMATION_DURATION = 250L;
        Float fValueOf = Float.valueOf(0.0f);
        FIDGET_INTERPOLATOR = new PathInterpolator(0.26873f, 0.0f, 0.45042f, 1.0f);
        AxisDefinition axisDefinition = GSFAxes.WEIGHT;
        Pair pair = new Pair(axisDefinition.tag, new Pair(Float.valueOf(200.0f), Float.valueOf(500.0f)));
        GSFAxes gSFAxes = GSFAxes.INSTANCE;
        gSFAxes.getClass();
        AxisDefinition axisDefinition2 = GSFAxes.WIDTH;
        Pair pair2 = new Pair(axisDefinition2.tag, new Pair(Float.valueOf(30.0f), Float.valueOf(75.0f)));
        gSFAxes.getClass();
        AxisDefinition axisDefinition3 = GSFAxes.ROUND;
        Pair pair3 = new Pair(axisDefinition3.tag, new Pair(fValueOf, Float.valueOf(50.0f)));
        gSFAxes.getClass();
        AxisDefinition axisDefinition4 = GSFAxes.SLANT;
        FIDGET_DISTS = MapsKt__MapsKt.mapOf(pair, pair2, pair3, new Pair(axisDefinition4.tag, new Pair(fValueOf, Float.valueOf(-5.0f))));
        AOD_COLOR = -1;
        LS_WEIGHT_AXIS = new Pair(axisDefinition, Float.valueOf(400.0f));
        AOD_WEIGHT_AXIS = new Pair(axisDefinition, Float.valueOf(200.0f));
        gSFAxes.getClass();
        WIDTH_AXIS = new Pair(axisDefinition2, Float.valueOf(85.0f));
        gSFAxes.getClass();
        ROUND_AXIS = new Pair(axisDefinition3, fValueOf);
        gSFAxes.getClass();
        SLANT_AXIS = new Pair(axisDefinition4, fValueOf);
        FLEX_LS_WEIGHT_AXIS = new Pair(axisDefinition, Float.valueOf(600.0f));
        FLEX_AOD_LARGE_WEIGHT_AXIS = new Pair(axisDefinition, Float.valueOf(74.0f));
        FLEX_AOD_SMALL_WEIGHT_AXIS = new Pair(axisDefinition, Float.valueOf(133.0f));
        gSFAxes.getClass();
        FLEX_LS_WIDTH_AXIS = new Pair(axisDefinition2, Float.valueOf(100.0f));
        gSFAxes.getClass();
        FLEX_AOD_WIDTH_AXIS = new Pair(axisDefinition2, Float.valueOf(43.0f));
        gSFAxes.getClass();
        FLEX_ROUND_AXIS = new Pair(axisDefinition3, Float.valueOf(100.0f));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleDigitalClockTextView(ClockContext clockContext, boolean z, AttributeSet attributeSet) {
        Typeface typeface;
        super(clockContext.context, attributeSet);
        this.clockCtx = clockContext;
        this.lockScreenPaint = new TextPaint();
        boolean zAreEqual = Intrinsics.areEqual(clockContext.settings.getClockId(), "DIGITAL_CLOCK_FLEX");
        ClockAxisStyle clockAxisStyleAccess$fromAxes = !zAreEqual ? Companion.access$fromAxes(Companion, AOD_WEIGHT_AXIS, WIDTH_AXIS) : z ? Companion.access$fromAxes(Companion, FLEX_AOD_LARGE_WEIGHT_AXIS, FLEX_AOD_WIDTH_AXIS) : Companion.access$fromAxes(Companion, FLEX_AOD_SMALL_WEIGHT_AXIS, FLEX_AOD_WIDTH_AXIS);
        this.fixedAodAxes = clockAxisStyleAccess$fromAxes;
        Pair pair = !zAreEqual ? ROUND_AXIS : FLEX_ROUND_AXIS;
        ClockAxisStyle clockAxisStyleAccess$fromAxes2 = !zAreEqual ? Companion.access$fromAxes(Companion, LS_WEIGHT_AXIS, WIDTH_AXIS, ROUND_AXIS, SLANT_AXIS) : Companion.access$fromAxes(Companion, FLEX_LS_WEIGHT_AXIS, FLEX_LS_WIDTH_AXIS, FLEX_ROUND_AXIS, SLANT_AXIS);
        this.lsFontVariation = clockAxisStyleAccess$fromAxes2.toFVar();
        this.aodFontVariation = clockAxisStyleAccess$fromAxes.copyWith(Companion.access$fromAxes(Companion, pair, SLANT_AXIS)).toFVar();
        this.fidgetFontVariation = buildFidgetVariation(clockAxisStyleAccess$fromAxes2).toFVar();
        new DimensionParser(clockContext.context);
        this.maxSingleDigitHeight = -1.0f;
        this.maxSingleDigitWidth = -1.0f;
        this.aodFontSizePx = -1.0f;
        this.lastUnconstrainedTextSize = Float.MAX_VALUE;
        this.initThread = Thread.currentThread();
        VRectF.Companion companion = VRectF.Companion;
        this.textBounds = companion.m2851getZERO3Hl7r_E();
        this.prevTextBounds = companion.m2851getZERO3Hl7r_E();
        this.targetTextBounds = companion.m2851getZERO3Hl7r_E();
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        simpleName.getClass();
        this.logger = new ClockLogger(this, clockContext.messageBuffer, simpleName);
        this.aodDozingInterpolator = Interpolators.LINEAR;
        TypefaceCache typefaceCache = clockContext.typefaceCache;
        typefaceCache.checkQueue();
        TypefaceCache.CacheKey cacheKey = new TypefaceCache.CacheKey("", null);
        TypefaceCache.WeakTypefaceRef weakTypefaceRef = (TypefaceCache.WeakTypefaceRef) ((LinkedHashMap) typefaceCache.cache).get(cacheKey);
        if (weakTypefaceRef == null || (typeface = (Typeface) weakTypefaceRef.get()) == null) {
            typefaceCache.logMiss(cacheKey);
            typeface = (Typeface) typefaceCache.typefaceFactory.mo781invoke("");
            typefaceCache.cache.put(cacheKey, new TypefaceCache.WeakTypefaceRef(typefaceCache, cacheKey, typeface));
        }
        this.typefaceCache = new TypefaceCache$getVariantCache$1(typefaceCache, "", typeface);
        this.verticalAlignment = VerticalAlignment.BASELINE;
        this.horizontalAlignment = HorizontalAlignment.CENTER;
        this.isAnimationEnabled = true;
        this.lockscreenColor = -1;
        this.layoutBounds = companion.m2851getZERO3Hl7r_E();
    }

    public static void applyTextSize$default(SimpleDigitalClockTextView simpleDigitalClockTextView, Float f) {
        simpleDigitalClockTextView.getClass();
        float fFloatValue = f.floatValue();
        simpleDigitalClockTextView.lastUnconstrainedTextSize = fFloatValue;
        FontTextStyle fontTextStyle = simpleDigitalClockTextView.textStyle;
        if (fontTextStyle == null) {
            fontTextStyle = null;
        }
        Float f2 = fontTextStyle.fontSizeScale;
        float fFloatValue2 = 1.0f;
        float fFloatValue3 = (f2 != null ? f2.floatValue() : 1.0f) * fFloatValue;
        FontTextStyle fontTextStyle2 = simpleDigitalClockTextView.aodStyle;
        if (fontTextStyle2 == null) {
            fontTextStyle2 = null;
        }
        Float f3 = fontTextStyle2.fontSizeScale;
        if (f3 != null) {
            fFloatValue2 = f3.floatValue();
        } else {
            FontTextStyle fontTextStyle3 = simpleDigitalClockTextView.textStyle;
            Float f4 = (fontTextStyle3 != null ? fontTextStyle3 : null).fontSizeScale;
            if (f4 != null) {
                fFloatValue2 = f4.floatValue();
            }
        }
        simpleDigitalClockTextView.aodFontSizePx = fFloatValue * fFloatValue2;
        if (fFloatValue3 > 0.0f) {
            simpleDigitalClockTextView.setTextSize(0, fFloatValue3);
            simpleDigitalClockTextView.lockScreenPaint.setTextSize(simpleDigitalClockTextView.getTextSize());
            simpleDigitalClockTextView.textBounds = SimpleDigitalClockTextViewKt.access$getTextBounds(simpleDigitalClockTextView.lockScreenPaint, simpleDigitalClockTextView.getText());
            simpleDigitalClockTextView.updateAnimationTextBounds();
        }
        VRectF.m2836getHeightimpl(simpleDigitalClockTextView.textBounds);
        simpleDigitalClockTextView.lockScreenPaint.getStrokeWidth();
        simpleDigitalClockTextView.lockScreenPaint.setStrokeWidth(0.0f);
        simpleDigitalClockTextView.recomputeMaxSingleDigitSizes();
        TextAnimator textAnimator = simpleDigitalClockTextView.textAnimator;
        if (textAnimator != null) {
            TextAnimator.setTextStyle$default(textAnimator, new TextAnimator.Style(null, Float.valueOf(simpleDigitalClockTextView.lockScreenPaint.getTextSize()), null, null, 13, null));
        }
    }

    public static ClockAxisStyle buildFidgetVariation(ClockAxisStyle clockAxisStyle) {
        Iterable<Map.Entry<String, Float>> items = clockAxisStyle.getItems();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(items, 10));
        for (Map.Entry<String, Float> entry : items) {
            String key = entry.getKey();
            float fFloatValue = entry.getValue().floatValue();
            Pair pair = (Pair) FIDGET_DISTS.get(key);
            arrayList.add(pair != null ? new Pair(key, Float.valueOf((((Number) pair.component1()).floatValue() * (fFloatValue > ((Number) pair.component2()).floatValue() ? -1 : 1)) + fFloatValue)) : new Pair(key, Float.valueOf(fFloatValue)));
        }
        return new ClockAxisStyle((Map<String, Float>) MapsKt__MapsKt.toMap(arrayList));
    }

    public final void animateCharge() {
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            ValueAnimator valueAnimator = textAnimator.animator;
            if (valueAnimator != null ? valueAnimator.isRunning() : false) {
                return;
            }
            getLogger().animateCharge();
            final TextAnimator.Style style = new TextAnimator.Style(this.lsFontVariation, null, null, null, 14, null);
            final TextAnimator.Style style2 = new TextAnimator.Style(this.aodFontVariation, null, null, null, 14, null);
            TextAnimator textAnimator2 = this.textAnimator;
            if (textAnimator2 == null) {
                textAnimator2 = null;
            }
            textAnimator2.setTextStyle(this.dozeFraction == 0.0f ? style2 : style, new TextAnimator.Animation(this.isAnimationEnabled, 0L, CHARGE_ANIMATION_DURATION, null, new Runnable() { // from class: com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView.animateCharge.1
                @Override // java.lang.Runnable
                public final void run() {
                    SimpleDigitalClockTextView simpleDigitalClockTextView = SimpleDigitalClockTextView.this;
                    TextAnimator textAnimator3 = simpleDigitalClockTextView.textAnimator;
                    if (textAnimator3 == null) {
                        textAnimator3 = null;
                    }
                    TextAnimator.Style style3 = simpleDigitalClockTextView.dozeFraction == 0.0f ? style : style2;
                    boolean z = SimpleDigitalClockTextView.this.isAnimationEnabled;
                    SimpleDigitalClockTextView.Companion.getClass();
                    textAnimator3.setTextStyle(style3, new TextAnimator.Animation(z, 0L, SimpleDigitalClockTextView.CHARGE_ANIMATION_DURATION, null, null, 26, null));
                }
            }, 10, null));
        }
    }

    public final void animateDoze(boolean z, boolean z2) {
        if (this.textAnimator == null) {
            return;
        }
        getLogger().animateDoze(z, z2);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            textAnimator = null;
        }
        TextAnimator.Style style = new TextAnimator.Style(z ? this.aodFontVariation : this.lsFontVariation, Float.valueOf(z ? this.aodFontSizePx : this.lockScreenPaint.getTextSize()), Integer.valueOf(z ? AOD_COLOR : this.lockscreenColor), null, 8, null);
        boolean z3 = z2 && this.isAnimationEnabled;
        FontTextStyle fontTextStyle = this.aodStyle;
        if (fontTextStyle == null) {
            fontTextStyle = null;
        }
        textAnimator.setTextStyle(style, new TextAnimator.Animation(z3, 0L, fontTextStyle.transitionDuration, this.aodDozingInterpolator, null, 18, null));
        if (z2) {
            return;
        }
        requestLayout();
        ViewParent parent = getParent();
        FlexClockView flexClockView = parent instanceof FlexClockView ? (FlexClockView) parent : null;
        if (flexClockView != null) {
            flexClockView.requestLayout();
        }
    }

    public final void animateFidget(long j) {
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            ValueAnimator valueAnimator = textAnimator.animator;
            if (valueAnimator != null ? valueAnimator.isRunning() : false) {
                return;
            }
            getLogger().animateFidget(getX(), getY());
            Vibrator vibrator = this.clockCtx.vibrator;
            if (vibrator != null) {
                vibrator.vibrate(FIDGET_HAPTICS);
            }
            TextAnimator textAnimator2 = this.textAnimator;
            if (textAnimator2 == null) {
                textAnimator2 = null;
            }
            textAnimator2.setTextStyle(new TextAnimator.Style(this.fidgetFontVariation, null, null, null, 14, null), new TextAnimator.Animation(this.isAnimationEnabled, j, FIDGET_ANIMATION_DURATION, FIDGET_INTERPOLATOR, new Runnable() { // from class: com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView.animateFidget.1
                @Override // java.lang.Runnable
                public final void run() {
                    SimpleDigitalClockTextView simpleDigitalClockTextView = SimpleDigitalClockTextView.this;
                    TextAnimator textAnimator3 = simpleDigitalClockTextView.textAnimator;
                    if (textAnimator3 == null) {
                        textAnimator3 = null;
                    }
                    TextAnimator.Style style = new TextAnimator.Style(simpleDigitalClockTextView.lsFontVariation, null, null, null, 14, null);
                    boolean z = SimpleDigitalClockTextView.this.isAnimationEnabled;
                    SimpleDigitalClockTextView.Companion.getClass();
                    textAnimator3.setTextStyle(style, new TextAnimator.Animation(z, 0L, SimpleDigitalClockTextView.FIDGET_ANIMATION_DURATION, SimpleDigitalClockTextView.FIDGET_INTERPOLATOR, null, 18, null));
                }
            }));
        }
    }

    /* renamed from: computeMeasuredSize-q_v0amA, reason: not valid java name */
    public final long m2951computeMeasuredSizeq_v0amA(int i, int i2, long j) {
        float fM2841getWidthimpl;
        float strokeWidth;
        long jM2703constructorimpl = VPoint.m2703constructorimpl(View.MeasureSpec.getMode(i), View.MeasureSpec.getMode(i2));
        if (VPoint.m2711getXimpl(jM2703constructorimpl) == 1073741824) {
            fM2841getWidthimpl = View.MeasureSpec.getSize(i);
        } else {
            fM2841getWidthimpl = VRectF.m2841getWidthimpl(j) + (this.lockScreenPaint.getStrokeWidth() * 2);
        }
        if (VPoint.m2712getYimpl(jM2703constructorimpl) == 1073741824) {
            strokeWidth = View.MeasureSpec.getSize(i2);
        } else {
            strokeWidth = (this.lockScreenPaint.getStrokeWidth() * 2) + VRectF.m2836getHeightimpl(j);
        }
        return VPointF.m2749constructorimpl(fM2841getWidthimpl, strokeWidth);
    }

    /* renamed from: getInterpolatedTextBounds-WMibXUk, reason: not valid java name */
    public final long m2952getInterpolatedTextBoundsWMibXUk(float f) {
        if (f <= 0.0f) {
            return this.prevTextBounds;
        }
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            textAnimator = null;
        }
        ValueAnimator valueAnimator = textAnimator.animator;
        if (!(valueAnimator != null ? valueAnimator.isRunning() : false) || f >= 1.0f) {
            return this.targetTextBounds;
        }
        return VRectF.m2828constructorimpl(MathUtils.lerp(VRectF.m2837getLeftimpl(this.prevTextBounds), VRectF.m2837getLeftimpl(this.targetTextBounds), f), MathUtils.lerp(VRectF.m2840getTopimpl(this.prevTextBounds), VRectF.m2840getTopimpl(this.targetTextBounds), f), MathUtils.lerp(VRectF.m2838getRightimpl(this.prevTextBounds), VRectF.m2838getRightimpl(this.targetTextBounds), f), MathUtils.lerp(VRectF.m2834getBottomimpl(this.prevTextBounds), VRectF.m2834getBottomimpl(this.targetTextBounds), f));
    }

    public final ClockLogger getLogger() {
        ClockLogger clockLogger = this.logger;
        return clockLogger == null ? ClockLogger.Companion.getINIT_LOGGER() : clockLogger;
    }

    @Override // android.view.View
    public final void invalidate() {
        getLogger().invalidate();
        super.invalidate();
        ViewParent parent = getParent();
        FlexClockView flexClockView = parent instanceof FlexClockView ? (FlexClockView) parent : null;
        if (flexClockView != null) {
            flexClockView.invalidate();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(final Canvas canvas) {
        float fM2837getLeftimpl;
        float fM2840getTopimpl;
        float fM2763getYimpl;
        float fM2763getYimpl2;
        float fM2763getYimpl3;
        float fM2762getXimpl;
        float fM2762getXimpl2;
        ClockLogger logger = getLogger();
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            textAnimator = null;
        }
        logger.onDraw(textAnimator.textInterpolator.shapedText);
        TextAnimator textAnimator2 = this.textAnimator;
        if (textAnimator2 == null) {
            textAnimator2 = null;
        }
        float f = textAnimator2.textInterpolator.progress;
        final long jM2952getInterpolatedTextBoundsWMibXUk = m2952getInterpolatedTextBoundsWMibXUk(f);
        Float f2 = this.drawnProgress;
        if (f2 == null || f != f2.floatValue()) {
            this.drawnProgress = Float.valueOf(f);
            long jM2951computeMeasuredSizeq_v0amA = m2951computeMeasuredSizeq_v0amA(getMeasuredWidthAndState(), getMeasuredHeightAndState(), jM2952getInterpolatedTextBoundsWMibXUk);
            m2953setInterpolatedSizerQse7s4(getMeasuredWidthAndState(), getMeasuredHeightAndState(), jM2951computeMeasuredSizeq_v0amA);
            ViewParent parent = getParent();
            FlexClockView flexClockView = parent instanceof FlexClockView ? (FlexClockView) parent : null;
            if (flexClockView != null) {
                flexClockView.getMeasuredWidthAndState();
                flexClockView.getMeasuredHeightAndState();
                flexClockView.updateMeasuredSize(false);
                flexClockView.updateLocation();
            } else {
                int i = WhenMappings.$EnumSwitchMapping$0[this.horizontalAlignment.resolveXAlignment(this).ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        fM2762getXimpl = VPointF.m2762getXimpl(VRectF.m2835getCenterJv7bpU8(this.layoutBounds));
                        fM2762getXimpl2 = VPointF.m2762getXimpl(jM2951computeMeasuredSizeq_v0amA) / 2.0f;
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        fM2762getXimpl = VRectF.m2838getRightimpl(this.layoutBounds);
                        fM2762getXimpl2 = VPointF.m2762getXimpl(jM2951computeMeasuredSizeq_v0amA);
                    }
                    fM2837getLeftimpl = fM2762getXimpl - fM2762getXimpl2;
                } else {
                    fM2837getLeftimpl = VRectF.m2837getLeftimpl(this.layoutBounds);
                }
                int i2 = WhenMappings.$EnumSwitchMapping$1[this.verticalAlignment.ordinal()];
                if (i2 != 1) {
                    if (i2 == 2) {
                        fM2763getYimpl = VPointF.m2763getYimpl(VRectF.m2835getCenterJv7bpU8(this.layoutBounds));
                        fM2763getYimpl2 = VPointF.m2763getYimpl(jM2951computeMeasuredSizeq_v0amA);
                    } else if (i2 == 3) {
                        fM2763getYimpl = VRectF.m2834getBottomimpl(this.layoutBounds);
                        fM2763getYimpl3 = VPointF.m2763getYimpl(jM2951computeMeasuredSizeq_v0amA);
                        fM2840getTopimpl = fM2763getYimpl - fM2763getYimpl3;
                    } else {
                        if (i2 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        fM2763getYimpl = VPointF.m2763getYimpl(VRectF.m2835getCenterJv7bpU8(this.layoutBounds));
                        fM2763getYimpl2 = VPointF.m2763getYimpl(jM2951computeMeasuredSizeq_v0amA);
                    }
                    fM2763getYimpl3 = fM2763getYimpl2 / 2.0f;
                    fM2840getTopimpl = fM2763getYimpl - fM2763getYimpl3;
                } else {
                    fM2840getTopimpl = VRectF.m2840getTopimpl(this.layoutBounds);
                }
                long jM2850fromTopLeftcwyIbD4 = VRectF.Companion.m2850fromTopLeftcwyIbD4(VPointF.m2749constructorimpl(fM2837getLeftimpl, fM2840getTopimpl), jM2951computeMeasuredSizeq_v0amA);
                setFrame(MathKt__MathJVMKt.roundToInt(VRectF.m2837getLeftimpl(jM2850fromTopLeftcwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2840getTopimpl(jM2850fromTopLeftcwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2838getRightimpl(jM2850fromTopLeftcwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2834getBottomimpl(jM2850fromTopLeftcwyIbD4)));
                Function1 function1 = this.onViewBoundsChanged;
                if (function1 != null) {
                    function1.mo781invoke(VRectF.m2827boximpl(jM2850fromTopLeftcwyIbD4));
                }
                ClockLogger logger2 = getLogger();
                LogMessage logMessageObtain = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.DEBUG, new SimpleDigitalClockTextView$$ExternalSyntheticLambda1(1), null);
                logMessageObtain.setLong1(VRectF.m2843toLongimpl(jM2850fromTopLeftcwyIbD4));
                logger2.getBuffer().commit(logMessageObtain);
                VRectF.m2827boximpl(jM2850fromTopLeftcwyIbD4);
            }
        }
        CanvasUtil canvasUtil = CanvasUtil.INSTANCE;
        Function1 function12 = new Function1() { // from class: com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                float f3;
                Canvas canvas2 = canvas;
                SimpleDigitalClockTextView simpleDigitalClockTextView = this.f$0;
                DigitTranslateAnimator digitTranslateAnimator = simpleDigitalClockTextView.digitTranslateAnimator;
                if (digitTranslateAnimator != null) {
                    CanvasUtil canvasUtil2 = CanvasUtil.INSTANCE;
                    long j = digitTranslateAnimator.currentTranslation;
                    canvasUtil2.getClass();
                    canvas2.translate(VPointF.m2762getXimpl(j), VPointF.m2763getYimpl(j));
                }
                CanvasUtil canvasUtil3 = CanvasUtil.INSTANCE;
                ViewUtils.INSTANCE.getClass();
                long jM2752constructorimpl = VPointF.m2752constructorimpl(simpleDigitalClockTextView.getMeasuredWidth(), simpleDigitalClockTextView.getMeasuredHeight());
                long j2 = jM2952getInterpolatedTextBoundsWMibXUk;
                long jM2769minusb2IjXjg = VPointF.m2769minusb2IjXjg(jM2752constructorimpl, VRectF.m2839getSizeJv7bpU8(j2));
                int i3 = SimpleDigitalClockTextView.WhenMappings.$EnumSwitchMapping$0[simpleDigitalClockTextView.horizontalAlignment.resolveXAlignment(simpleDigitalClockTextView).ordinal()];
                float f4 = 1.0f;
                if (i3 == 1) {
                    f3 = 0.0f;
                } else if (i3 == 2) {
                    f3 = 0.5f;
                } else {
                    if (i3 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    f3 = 1.0f;
                }
                int i4 = SimpleDigitalClockTextView.WhenMappings.$EnumSwitchMapping$1[simpleDigitalClockTextView.verticalAlignment.ordinal()];
                if (i4 == 1) {
                    f4 = 0.0f;
                } else if (i4 == 2) {
                    f4 = 0.5f;
                } else if (i4 != 3) {
                    if (i4 != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                    f4 = 0.5f;
                }
                long jM2774plusb2IjXjg = VPointF.m2774plusb2IjXjg(VPointF.m2778timesb2IjXjg(jM2769minusb2IjXjg, VPointF.m2749constructorimpl(f3, f4)), VPointF.m2749constructorimpl(-VRectF.m2837getLeftimpl(j2), (-VRectF.m2840getTopimpl(j2)) - (simpleDigitalClockTextView.getBaseline() != -1 ? simpleDigitalClockTextView.getBaseline() : simpleDigitalClockTextView.measuredBaseline)));
                canvasUtil3.getClass();
                canvas2.translate(VPointF.m2762getXimpl(jM2774plusb2IjXjg), VPointF.m2763getYimpl(jM2774plusb2IjXjg));
                if (simpleDigitalClockTextView.isLayoutRtl()) {
                    canvas2.translate(VRectF.m2841getWidthimpl(j2) - VRectF.m2841getWidthimpl(simpleDigitalClockTextView.textBounds), 0.0f);
                }
                TextAnimator textAnimator3 = simpleDigitalClockTextView.textAnimator;
                if (textAnimator3 == null) {
                    textAnimator3 = null;
                }
                textAnimator3.draw(canvas2);
                return Unit.INSTANCE;
            }
        };
        canvasUtil.getClass();
        int iSave = canvas.save();
        function12.mo781invoke(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        getLogger().onLayout(z, i, i2, i3, i4);
        this.layoutBounds = VRectF.m2828constructorimpl(i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        getLogger().onMeasure(i, i2);
        super.onMeasure(i, i2);
        Layout layout = getLayout();
        if (layout != null) {
            TextAnimator textAnimator = this.textAnimator;
            if (textAnimator == null) {
                this.textAnimator = new TextAnimator(layout, this.typefaceCache, new TextAnimatorListener() { // from class: com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView.onMeasure.1
                    @Override // com.android.systemui.animation.TextAnimatorListener
                    public final void onInvalidate() {
                        SimpleDigitalClockTextView.this.invalidate();
                    }

                    @Override // com.android.systemui.animation.TextInterpolatorListener
                    public final void onPaintModified() {
                        Companion companion = SimpleDigitalClockTextView.Companion;
                        SimpleDigitalClockTextView.this.updateAnimationTextBounds();
                    }

                    @Override // com.android.systemui.animation.TextInterpolatorListener
                    public final void onRebased() {
                        Companion companion = SimpleDigitalClockTextView.Companion;
                        SimpleDigitalClockTextView.this.updateAnimationTextBounds();
                    }
                });
                setInterpolatorPaint();
            } else {
                textAnimator.updateLayout(layout, -1.0f);
            }
            this.measuredBaseline = layout.getLineBaseline(0);
        } else {
            Thread threadCurrentThread = Thread.currentThread();
            Log.wtf(SimpleDigitalClockTextViewKt.TAG, "TextView.getLayout() is null after measure! currentThread=" + threadCurrentThread + "; initThread=" + this.initThread);
        }
        TextAnimator textAnimator2 = this.textAnimator;
        if (textAnimator2 == null) {
            textAnimator2 = null;
        }
        m2953setInterpolatedSizerQse7s4(i, i2, m2951computeMeasuredSizeq_v0amA(i, i2, m2952getInterpolatedTextBoundsWMibXUk(textAnimator2.textInterpolator.progress)));
    }

    public final void recomputeMaxSingleDigitSizes() {
        this.maxSingleDigitHeight = 0.0f;
        this.maxSingleDigitWidth = 0.0f;
        for (int i = 0; i < 10; i++) {
            long jAccess$getTextBounds = SimpleDigitalClockTextViewKt.access$getTextBounds(this.lockScreenPaint, String.valueOf(i));
            this.maxSingleDigitHeight = Math.max(this.maxSingleDigitHeight, VRectF.m2836getHeightimpl(jAccess$getTextBounds));
            this.maxSingleDigitWidth = Math.max(this.maxSingleDigitWidth, VRectF.m2841getWidthimpl(jAccess$getTextBounds));
        }
        float f = 2;
        this.maxSingleDigitWidth = (this.lockScreenPaint.getStrokeWidth() * f) + this.maxSingleDigitWidth;
        this.maxSingleDigitHeight = (this.lockScreenPaint.getStrokeWidth() * f) + this.maxSingleDigitHeight;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        getLogger().setAlpha(f);
        super.setAlpha(f);
    }

    /* renamed from: setInterpolatedSize-rQse7s4, reason: not valid java name */
    public final void m2953setInterpolatedSizerQse7s4(int i, int i2, long j) {
        long jM2703constructorimpl = VPoint.m2703constructorimpl(View.MeasureSpec.getMode(i), View.MeasureSpec.getMode(i2));
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(MathKt__MathJVMKt.roundToInt(VPointF.m2762getXimpl(j)), VPoint.m2711getXimpl(jM2703constructorimpl)), View.MeasureSpec.makeMeasureSpec(MathKt__MathJVMKt.roundToInt(VPointF.m2763getYimpl(j)), VPoint.m2712getYimpl(jM2703constructorimpl)));
        ClockLogger logger = getLogger();
        SimpleDigitalClockTextView$$ExternalSyntheticLambda1 simpleDigitalClockTextView$$ExternalSyntheticLambda1 = new SimpleDigitalClockTextView$$ExternalSyntheticLambda1(0);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, simpleDigitalClockTextView$$ExternalSyntheticLambda1, null);
        logMessageObtain.setLong1(VPointF.m2780toLongimpl(j));
        logMessageObtain.setLong2(VPoint.m2726toLongimpl(jM2703constructorimpl));
        logger.getBuffer().commit(logMessageObtain);
    }

    public final void setInterpolatorPaint() {
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            textAnimator.textInterpolator.targetPaint.set(this.lockScreenPaint);
            TextAnimator textAnimator2 = this.textAnimator;
            if (textAnimator2 == null) {
                textAnimator2 = null;
            }
            textAnimator2.textInterpolator.onTargetPaintModified();
            TextAnimator textAnimator3 = this.textAnimator;
            TextAnimator.setTextStyle$default(textAnimator3 != null ? textAnimator3 : null, new TextAnimator.Style(this.lsFontVariation, Float.valueOf(this.lockScreenPaint.getTextSize()), Integer.valueOf(this.lockscreenColor), null, 8, null));
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        getLogger().setVisibility(i);
        super.setVisibility(i);
    }

    public final void updateAnimationTextBounds() {
        this.drawnProgress = null;
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            long j = this.textBounds;
            this.prevTextBounds = j;
            this.targetTextBounds = j;
        } else {
            if (textAnimator == null) {
                textAnimator = null;
            }
            this.prevTextBounds = SimpleDigitalClockTextViewKt.access$getTextBounds(textAnimator.textInterpolator.basePaint, getText());
            TextAnimator textAnimator2 = this.textAnimator;
            this.targetTextBounds = SimpleDigitalClockTextViewKt.access$getTextBounds((textAnimator2 != null ? textAnimator2 : null).textInterpolator.targetPaint, getText());
        }
    }

    public final void updateAxes(ClockAxisStyle clockAxisStyle, boolean z) {
        this.lsFontVariation = clockAxisStyle.toFVar();
        this.aodFontVariation = clockAxisStyle.copyWith(this.fixedAodAxes).toFVar();
        this.fidgetFontVariation = buildFidgetVariation(clockAxisStyle).toFVar();
        getLogger().updateAxes(this.lsFontVariation, this.aodFontVariation, z);
        this.lockScreenPaint.setTypeface(this.typefaceCache.getTypefaceForVariant(this.lsFontVariation));
        setTypeface(this.lockScreenPaint.getTypeface());
        this.textBounds = SimpleDigitalClockTextViewKt.access$getTextBounds(this.lockScreenPaint, getText());
        updateAnimationTextBounds();
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            textAnimator = null;
        }
        textAnimator.setTextStyle(new TextAnimator.Style(this.lsFontVariation, null, null, null, 14, null), new TextAnimator.Animation(z && this.isAnimationEnabled, 0L, AXIS_CHANGE_ANIMATION_DURATION, this.aodDozingInterpolator, null, 18, null));
        measure(0, 0);
        recomputeMaxSingleDigitSizes();
        requestLayout();
        invalidate();
    }

    public final void updateColor(int i) {
        this.lockscreenColor = i;
        this.lockScreenPaint.setColor(i);
        if (this.dozeFraction < 1.0f) {
            TextAnimator textAnimator = this.textAnimator;
            if (textAnimator == null) {
                textAnimator = null;
            }
            TextAnimator.setTextStyle$default(textAnimator, new TextAnimator.Style(null, null, Integer.valueOf(this.lockscreenColor), null, 11, null));
        }
        invalidate();
    }

    public static /* synthetic */ void getFontSizeAdjustFactor$annotations() {
    }

    public static /* synthetic */ void getTextAnimator$annotations() {
    }

    public /* synthetic */ SimpleDigitalClockTextView(ClockContext clockContext, boolean z, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(clockContext, z, (i & 4) != 0 ? null : attributeSet);
    }
}
