package com.android.systemui.shared.clocks.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.icu.text.NumberFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import androidx.core.view.ViewGroupKt$children$1;
import com.android.app.animation.Interpolators;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.customization.R$id;
import com.android.systemui.plugins.clocks.ClockLogger;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.plugins.clocks.VRectF;
import com.android.systemui.shared.clocks.CanvasUtil;
import com.android.systemui.shared.clocks.ClockContext;
import com.android.systemui.shared.clocks.DigitTranslateAnimator;
import com.android.systemui.shared.clocks.ViewUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes3.dex */
public final class FlexClockView extends ViewGroup {
    public List _childViews;
    public long aodTranslate;
    public final Map digitOffsets;
    public float dozeFraction;
    public final boolean isAnimationEnabled;
    public boolean isDozeReadyToAnimate;
    public boolean isReactiveTouchInteractionEnabled;
    public long layoutBounds;
    public final long lockscreenTranslate;
    public final ClockLogger logger;
    public long maxChildSize;
    public FlexClockView$$ExternalSyntheticLambda1 onAnimateDoze;
    public Function1 onViewBoundsChanged;
    public static final Companion Companion = new Companion(null);
    public static final long AOD_TRANSITION_DURATION = 750;
    public static final long CHARGING_TRANSITION_DURATION = 300;
    public static final List FIDGET_DELAYS = Arrays.asList(0L, 75L, 150L, 225L);
    public static final List MOVE_LEFT_DELAYS = Arrays.asList(0, 1, 2, 3);
    public static final List MOVE_RIGHT_DELAYS = Arrays.asList(1, 0, 3, 2);
    public static final Interpolator MOVE_INTERPOLATOR = Interpolators.EMPHASIZED;
    public static final Set NON_MONO_VERTICAL_NUMERIC_LINE_SPACING_LANGUAGES = Collections.singleton("my");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: updateDirectionalTargetTranslate-NvxBqkk, reason: not valid java name */
        public static long m2950updateDirectionalTargetTranslateNvxBqkk(int i, long j) {
            return VPointF.m2778timesb2IjXjg(j, i == R$id.HOUR_FIRST_DIGIT ? VPointF.m2752constructorimpl(-1, -1) : i == R$id.HOUR_SECOND_DIGIT ? VPointF.m2752constructorimpl(1, -1) : i == R$id.MINUTE_FIRST_DIGIT ? VPointF.m2752constructorimpl(-1, 1) : i == R$id.MINUTE_SECOND_DIGIT ? VPointF.m2752constructorimpl(1, 1) : i == R$id.HOUR_DIGIT_PAIR ? VPointF.m2752constructorimpl(-1, -1) : i == R$id.MINUTE_DIGIT_PAIR ? VPointF.m2752constructorimpl(-1, 1) : VPointF.m2752constructorimpl(1, 1));
        }

        private Companion() {
        }
    }

    public FlexClockView(ClockContext clockContext) {
        super(clockContext.context);
        String simpleName = Reflection.getOrCreateKotlinClass(FlexClockView.class).getSimpleName();
        simpleName.getClass();
        this.logger = new ClockLogger(this, clockContext.messageBuffer, simpleName);
        this.isAnimationEnabled = true;
        this.maxChildSize = VPointF.m2752constructorimpl(-1, -1);
        VPointF.Companion companion = VPointF.Companion;
        this.lockscreenTranslate = companion.m2790getZEROJv7bpU8();
        this.aodTranslate = companion.m2790getZEROJv7bpU8();
        setWillNotDraw(false);
        setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        updateLocale(Locale.getDefault());
        this.digitOffsets = new LinkedHashMap();
        this.layoutBounds = VRectF.Companion.m2851getZERO3Hl7r_E();
    }

    public static final void animateDoze$executeDozeAnimation(FlexClockView flexClockView, boolean z, boolean z2) {
        Iterator it = flexClockView.getChildViews().iterator();
        while (it.hasNext()) {
            ((SimpleDigitalClockTextView) it.next()).animateDoze(z, z2);
        }
        if (VPointF.m2762getXimpl(flexClockView.maxChildSize) < 0.0f || VPointF.m2763getYimpl(flexClockView.maxChildSize) < 0.0f) {
            flexClockView.measure(0, 0);
        }
        Iterator it2 = flexClockView.getChildViews().iterator();
        while (it2.hasNext()) {
            DigitTranslateAnimator digitTranslateAnimator = ((SimpleDigitalClockTextView) it2.next()).digitTranslateAnimator;
            if (digitTranslateAnimator != null) {
                if (z) {
                    boolean z3 = z2 && flexClockView.isAnimationEnabled;
                    Interpolator interpolator = Interpolators.EMPHASIZED;
                    long j = AOD_TRANSITION_DURATION;
                    Companion companion = Companion;
                    int id = flexClockView.getId();
                    long j2 = flexClockView.aodTranslate;
                    companion.getClass();
                    DigitTranslateAnimator.m2949animatePositionWofAHi4$default(digitTranslateAnimator, z3, j, interpolator, Companion.m2950updateDirectionalTargetTranslateNvxBqkk(id, j2), null, 2);
                } else {
                    boolean z4 = z2 && flexClockView.isAnimationEnabled;
                    Interpolator interpolator2 = Interpolators.EMPHASIZED;
                    long j3 = AOD_TRANSITION_DURATION;
                    Companion companion2 = Companion;
                    int id2 = flexClockView.getId();
                    long j4 = flexClockView.lockscreenTranslate;
                    companion2.getClass();
                    DigitTranslateAnimator.m2949animatePositionWofAHi4$default(digitTranslateAnimator, z4, j3, interpolator2, Companion.m2950updateDirectionalTargetTranslateNvxBqkk(id2, j4), null, 34);
                }
            }
        }
    }

    public static void updateLocale(Locale locale) {
        Set set = NON_MONO_VERTICAL_NUMERIC_LINE_SPACING_LANGUAGES;
        if ((set instanceof Collection) && set.isEmpty()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(NumberFormat.getInstance(locale).format(1234567890L), NumberFormat.getInstance(Locale.forLanguageTag((String) it.next())).format(1234567890L))) {
                return;
            }
        }
    }

    public final List getChildViews() {
        List list = this._childViews;
        if (list != null) {
            return list;
        }
        List list2 = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filterNot(new TransformingSequence(new ViewGroupKt$children$1(this), new FlexClockView$$ExternalSyntheticLambda0()), new SequencesKt___SequencesKt$$ExternalSyntheticLambda1()));
        this._childViews = list2;
        return list2;
    }

    public final ClockLogger getLogger() {
        ClockLogger clockLogger = this.logger;
        return clockLogger == null ? ClockLogger.Companion.getINIT_LOGGER() : clockLogger;
    }

    @Override // android.view.View
    public final void invalidate() {
        getLogger().invalidate();
        super.invalidate();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        getLogger().onDraw();
        for (SimpleDigitalClockTextView simpleDigitalClockTextView : getChildViews()) {
            CanvasUtil.INSTANCE.getClass();
            int iSave = canvas.save();
            canvas.translate(((Number) ((LinkedHashMap) this.digitOffsets).getOrDefault(Integer.valueOf(simpleDigitalClockTextView.getId()), Float.valueOf(0.0f))).floatValue(), 0.0f);
            canvas.translate(simpleDigitalClockTextView.getLeft(), simpleDigitalClockTextView.getTop());
            simpleDigitalClockTextView.draw(canvas);
            Unit unit = Unit.INSTANCE;
            canvas.restoreToCount(iSave);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        getLogger().onLayout(z, i, i2, i3, i4);
        this.layoutBounds = VRectF.m2828constructorimpl(i, i2, i3, i4);
        updateChildFrames(true);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        getLogger().onMeasure(i, i2);
        updateMeasuredSize(true);
        this.isDozeReadyToAnimate = true;
        FlexClockView$$ExternalSyntheticLambda1 flexClockView$$ExternalSyntheticLambda1 = this.onAnimateDoze;
        if (flexClockView$$ExternalSyntheticLambda1 != null) {
            flexClockView$$ExternalSyntheticLambda1.invoke();
        }
        this.onAnimateDoze = null;
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        if (view == null) {
            return;
        }
        getLogger().onViewAdded(view);
        super.onViewAdded(view);
        SimpleDigitalClockTextView simpleDigitalClockTextView = view instanceof SimpleDigitalClockTextView ? (SimpleDigitalClockTextView) view : null;
        if (simpleDigitalClockTextView != null) {
            simpleDigitalClockTextView.digitTranslateAnimator = new DigitTranslateAnimator(new Function1() { // from class: com.android.systemui.shared.clocks.view.FlexClockView$onViewAdded$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ((VPointF) obj).m2784unboximpl();
                    this.this$0.invalidate();
                    return Unit.INSTANCE;
                }
            });
        }
        view.setWillNotDraw(true);
        this._childViews = null;
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this._childViews = null;
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        getLogger().requestLayout();
        super.requestLayout();
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        getLogger().setAlpha(f);
        super.setAlpha(f);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        getLogger().setVisibility(i);
        super.setVisibility(i);
    }

    public final void updateChildFrames(boolean z) throws Resources.NotFoundException {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.clock_vertical_digit_buffer);
        for (SimpleDigitalClockTextView simpleDigitalClockTextView : getChildViews()) {
            long j = this.maxChildSize;
            int id = simpleDigitalClockTextView.getId();
            long jM2749constructorimpl = id == R$id.HOUR_FIRST_DIGIT ? VPointF.Companion.m2790getZEROJv7bpU8() : id == R$id.HOUR_SECOND_DIGIT ? VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j), 0.0f) : id == R$id.HOUR_DIGIT_PAIR ? VPointF.Companion.m2790getZEROJv7bpU8() : (id == R$id.MINUTE_DIGIT_PAIR || id == R$id.MINUTE_FIRST_DIGIT) ? VPointF.m2749constructorimpl(0.0f, VPointF.m2763getYimpl(j) + dimensionPixelSize) : id == R$id.MINUTE_SECOND_DIGIT ? VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j), VPointF.m2763getYimpl(j) + dimensionPixelSize) : VPointF.Companion.m2790getZEROJv7bpU8();
            ViewUtils.INSTANCE.getClass();
            long jM2752constructorimpl = VPointF.m2752constructorimpl(simpleDigitalClockTextView.getMeasuredWidth(), simpleDigitalClockTextView.getMeasuredHeight());
            long jM2774plusb2IjXjg = VPointF.m2774plusb2IjXjg(VPointF.m2774plusb2IjXjg(jM2749constructorimpl, VPointF.m2745absJv7bpU8(this.aodTranslate)), VPointF.m2749constructorimpl((getChildViews().size() < 4 ? getMeasuredWidth() / 2.0f : getMeasuredWidth() / 4.0f) - (VPointF.m2762getXimpl(jM2752constructorimpl) / 2.0f), 0.0f));
            (z ? new FlexClockView$updateChildFrames$1$setPos$1(simpleDigitalClockTextView) : new FlexClockView$updateChildFrames$1$setPos$2(simpleDigitalClockTextView)).invoke(Integer.valueOf(MathKt__MathJVMKt.roundToInt(VPointF.m2762getXimpl(jM2774plusb2IjXjg))), Integer.valueOf(MathKt__MathJVMKt.roundToInt(VPointF.m2763getYimpl(jM2774plusb2IjXjg))), Integer.valueOf(MathKt__MathJVMKt.roundToInt(VPointF.m2762getXimpl(jM2752constructorimpl) + VPointF.m2762getXimpl(jM2774plusb2IjXjg))), Integer.valueOf(MathKt__MathJVMKt.roundToInt(VPointF.m2763getYimpl(jM2752constructorimpl) + VPointF.m2763getYimpl(jM2774plusb2IjXjg))));
        }
    }

    public final void updateLocation() {
        long j = this.layoutBounds;
        VRectF.Companion companion = VRectF.Companion;
        long jM2835getCenterJv7bpU8 = VRectF.m2835getCenterJv7bpU8(j);
        ViewUtils.INSTANCE.getClass();
        long jM2848fromCentercwyIbD4 = companion.m2848fromCentercwyIbD4(jM2835getCenterJv7bpU8, VPointF.m2752constructorimpl(getMeasuredWidth(), getMeasuredHeight()));
        setFrame(MathKt__MathJVMKt.roundToInt(VRectF.m2837getLeftimpl(jM2848fromCentercwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2840getTopimpl(jM2848fromCentercwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2838getRightimpl(jM2848fromCentercwyIbD4)), MathKt__MathJVMKt.roundToInt(VRectF.m2834getBottomimpl(jM2848fromCentercwyIbD4)));
        updateChildFrames(false);
        Function1 function1 = this.onViewBoundsChanged;
        if (function1 != null) {
            function1.mo781invoke(VRectF.m2827boximpl(jM2848fromCentercwyIbD4));
        }
    }

    public final void updateMeasuredSize(boolean z) {
        this.maxChildSize = VPointF.m2752constructorimpl(-1, -1);
        for (SimpleDigitalClockTextView simpleDigitalClockTextView : getChildViews()) {
            if (z) {
                simpleDigitalClockTextView.measure(0, 0);
            }
            VPointF.Companion companion = VPointF.Companion;
            long j = this.maxChildSize;
            ViewUtils.INSTANCE.getClass();
            this.maxChildSize = companion.m2791max5C5yMpM(j, VPointF.m2752constructorimpl(simpleDigitalClockTextView.getMeasuredWidth(), simpleDigitalClockTextView.getMeasuredHeight()));
        }
        this.aodTranslate = VPointF.Companion.m2790getZEROJv7bpU8();
        long jM2774plusb2IjXjg = VPointF.m2774plusb2IjXjg(VPointF.m2778timesb2IjXjg(VPointF.m2774plusb2IjXjg(this.maxChildSize, VPointF.m2745absJv7bpU8(this.aodTranslate)), VPointF.m2749constructorimpl(getChildViews().size() < 4 ? 1.0f : 2.0f, 2.0f)), VPointF.m2750constructorimpl(0.0f, getContext().getResources().getDimensionPixelSize(R$dimen.clock_vertical_digit_buffer)));
        setMeasuredDimension(MathKt__MathJVMKt.roundToInt(VPointF.m2762getXimpl(jM2774plusb2IjXjg)), MathKt__MathJVMKt.roundToInt(VPointF.m2763getYimpl(jM2774plusb2IjXjg)));
    }

    public static /* synthetic */ void isAnimationEnabled$annotations() {
    }
}
