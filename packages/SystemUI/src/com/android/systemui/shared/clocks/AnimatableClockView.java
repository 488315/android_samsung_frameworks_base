package com.android.systemui.shared.clocks;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.TextView;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextInterpolator;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.customization.R$styleable;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.LogcatOnlyMessageBuffer;
import com.android.systemui.log.core.Logger;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AnimatableClockView extends TextView {
    public static final Logger DEFAULT_LOGGER;
    public static final Interpolator MOVE_INTERPOLATOR;
    public static final List MOVE_LEFT_DELAYS;
    public static final List MOVE_RIGHT_DELAYS;
    public static final String TAG;
    public final int chargeAnimationDelay;
    public CharSequence descFormat;
    public int dozingColor;
    public final int dozingWeightInternal;
    public CharSequence format;
    public final Function2 glyphFilter;
    public final List glyphOffsets;
    public boolean hasCustomPositionUpdatedAnimation;
    public final boolean isAnimationEnabled;
    public final boolean isSingleLineInternal;
    public float lastUnconstrainedTextSize;
    public int lockScreenColor;
    public final int lockScreenWeightInternal;
    public Logger logger;
    public boolean migratedClocks;
    public Function1 onTextAnimatorInitialized;
    public TextAnimator textAnimator;
    public final Function2 textAnimatorFactory;
    public final Calendar time;
    public boolean translateForCenterAnimation;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Patterns {
        public static final Patterns INSTANCE = new Patterns();
        public static String sCacheKey;
        public static String sClockView12;
        public static String sClockView24;

        private Patterns() {
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(AnimatableClockView.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
        DEFAULT_LOGGER = new Logger(new LogcatOnlyMessageBuffer(LogLevel.WARNING), simpleName);
        MOVE_INTERPOLATOR = Interpolators.EMPHASIZED;
        MOVE_LEFT_DELAYS = CollectionsKt__CollectionsKt.listOf(0, 1, 2, 3);
        MOVE_RIGHT_DELAYS = CollectionsKt__CollectionsKt.listOf(1, 0, 3, 2);
    }

    public AnimatableClockView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final void animateDoze(boolean z, boolean z2) {
        Logger.d$default(getLogger(), "animateDoze", null, 2, null);
        setTextStyle(z ? getDozingWeight() : getLockScreenWeight(), Integer.valueOf(z ? this.dozingColor : this.lockScreenColor), z2, null, 300L, 0L, null);
    }

    public final void animateFoldAppear(boolean z) {
        if (this.textAnimator == null) {
            return;
        }
        Logger.d$default(getLogger(), "animateFoldAppear", null, 2, null);
        setTextStyle(this.lockScreenWeightInternal, Integer.valueOf(this.lockScreenColor), false, null, 0L, 0L, null);
        setTextStyle(this.dozingWeightInternal, Integer.valueOf(this.dozingColor), z, Interpolators.EMPHASIZED_DECELERATE, 600L, 0L, null);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println(String.valueOf(this));
        printWriter.println("    alpha=" + getAlpha());
        printWriter.println("    measuredWidth=" + getMeasuredWidth());
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("    measuredHeight=", getMeasuredHeight(), printWriter);
        printWriter.println("    singleLineInternal=" + this.isSingleLineInternal);
        printWriter.println("    currText=" + ((Object) getText()));
        printWriter.println("    currTimeContextDesc=" + ((Object) getContentDescription()));
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("    dozingWeightInternal=", this.dozingWeightInternal, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("    lockScreenWeightInternal=", this.lockScreenWeightInternal, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("    dozingColor=", this.dozingColor, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("    lockScreenColor=", this.lockScreenColor, printWriter);
        printWriter.println("    time=" + this.time);
    }

    public final float getDigitFraction(float f, boolean z, int i) {
        float floatValue = ((Number) (z ? isLayoutRtl() ? MOVE_LEFT_DELAYS : MOVE_RIGHT_DELAYS : isLayoutRtl() ? MOVE_RIGHT_DELAYS : MOVE_LEFT_DELAYS).get(i)).floatValue() * 0.033f;
        return ((PathInterpolator) MOVE_INTERPOLATOR).getInterpolation(MathUtils.constrainedMap(0.0f, 1.0f, floatValue, 0.901f + floatValue, f));
    }

    public final int getDozingWeight() {
        return getResources().getConfiguration().fontWeightAdjustment > 100 ? this.dozingWeightInternal + 100 : this.dozingWeightInternal;
    }

    public final int getLockScreenWeight() {
        return getResources().getConfiguration().fontWeightAdjustment > 100 ? this.lockScreenWeightInternal + 100 : this.lockScreenWeightInternal;
    }

    public final Logger getLogger() {
        Logger logger = this.logger;
        return logger == null ? DEFAULT_LOGGER : logger;
    }

    @Override // android.view.View
    public final void invalidate() {
        Logger.d$default(getLogger(), "invalidate", null, 2, null);
        super.invalidate();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        Logger.d$default(getLogger(), "onAttachedToWindow", null, 2, null);
        super.onAttachedToWindow();
        refreshFormat(DateFormat.is24HourFormat(getContext()));
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.save();
        if (this.translateForCenterAnimation) {
            canvas.translate(((View) getParent()).getMeasuredWidth() / 4.0f, 0.0f);
        }
        Logger logger = getLogger();
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$onDraw$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("onDraw(", ((LogMessage) obj).getStr1(), ")");
            }
        }, null);
        obtain.setStr1(getText().toString());
        logger.getBuffer().commit(obtain);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            TextInterpolator textInterpolator = textAnimator.textInterpolator;
            TextInterpolator.lerp(textInterpolator.basePaint, textInterpolator.targetPaint, textInterpolator.progress, textInterpolator.tmpPaint);
            int i = 0;
            for (Object obj : textInterpolator.lines) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                for (TextInterpolator.Run run : ((TextInterpolator.Line) obj).runs) {
                    canvas.save();
                    try {
                        Layout layout = textInterpolator.layout;
                        canvas.translate(layout.getParagraphDirection(i) == 1 ? layout.getLineLeft(i) : layout.getLineRight(i), textInterpolator.layout.getLineBaseline(i));
                        Iterator it = run.fontRuns.iterator();
                        while (it.hasNext()) {
                            textInterpolator.drawFontRun(canvas, run, (TextInterpolator.FontRun) it.next(), i, textInterpolator.tmpPaint);
                        }
                    } finally {
                        canvas.restore();
                    }
                }
                i = i2;
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        Unit unit;
        Logger.d$default(getLogger(), "onMeasure", null, 2, null);
        if (this.migratedClocks && !this.isSingleLineInternal && View.MeasureSpec.getMode(i2) == 1073741824) {
            super.setTextSize(0, Math.min(this.lastUnconstrainedTextSize, View.MeasureSpec.getSize(i2) / 2.0f));
        }
        super.onMeasure(i, i2);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            textAnimator.updateLayout(getLayout(), getTextSize());
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            TextAnimator textAnimator2 = (TextAnimator) ((AnimatableClockView$textAnimatorFactory$1) this.textAnimatorFactory).invoke(getLayout(), new AnimatableClockView$onMeasure$2$1(this));
            Function1 function1 = this.onTextAnimatorInitialized;
            if (function1 != null) {
                ((AnimatableClockView$setTextStyle$2$1) function1).invoke(textAnimator2);
            }
            this.onTextAnimatorInitialized = null;
            this.textAnimator = textAnimator2;
        }
        if (!this.migratedClocks || !this.hasCustomPositionUpdatedAnimation) {
            this.translateForCenterAnimation = false;
            return;
        }
        int size = (View.MeasureSpec.getSize(i) / 2) + getMeasuredWidth();
        boolean z = ((View) getParent()).getMeasuredWidth() > size;
        this.translateForCenterAnimation = z;
        if (z) {
            setMeasuredDimension(size, getMeasuredHeight());
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        if (this.migratedClocks) {
            if (i == 1) {
                setTextAlignment(3);
            } else {
                setTextAlignment(2);
            }
        }
        super.onRtlPropertiesChanged(i);
    }

    @Override // android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Logger logger = getLogger();
        AnimatableClockView$onTextChanged$1 animatableClockView$onTextChanged$1 = new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$onTextChanged$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("onTextChanged(", ((LogMessage) obj).getStr1(), ")");
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, animatableClockView$onTextChanged$1, null);
        obtain.setStr1(charSequence.toString());
        logger.getBuffer().commit(obtain);
        super.onTextChanged(charSequence, i, i2, i3);
    }

    public final void refreshFormat(boolean z) {
        String str;
        String str2;
        Patterns patterns = Patterns.INSTANCE;
        Context context = getContext();
        patterns.getClass();
        Locale locale = Locale.getDefault();
        String string = context.getResources().getString(R.string.clock_12hr_format);
        String string2 = context.getResources().getString(R.string.clock_24hr_format);
        String str3 = locale + string + string2;
        if (!Intrinsics.areEqual(str3, Patterns.sCacheKey)) {
            String bestDateTimePattern = DateFormat.getBestDateTimePattern(locale, string);
            if (!StringsKt__StringsKt.contains(string, "a", false)) {
                Intrinsics.checkNotNull(bestDateTimePattern);
                String replace = new Regex("a").replace(bestDateTimePattern, "");
                int length = replace.length() - 1;
                int i = 0;
                boolean z2 = false;
                while (i <= length) {
                    boolean z3 = Intrinsics.compare(replace.charAt(!z2 ? i : length), 32) <= 0;
                    if (z2) {
                        if (!z3) {
                            break;
                        } else {
                            length--;
                        }
                    } else if (z3) {
                        i++;
                    } else {
                        z2 = true;
                    }
                }
                bestDateTimePattern = replace.subSequence(i, length + 1).toString();
            }
            Patterns.sClockView12 = bestDateTimePattern;
            Patterns.sClockView24 = DateFormat.getBestDateTimePattern(locale, string2);
            Patterns.sCacheKey = str3;
        }
        boolean z4 = this.isSingleLineInternal;
        if (z4 && z) {
            Patterns.INSTANCE.getClass();
            str = Patterns.sClockView24;
        } else if (!z4 && z) {
            str = "HH\nmm";
        } else if (!z4 || z) {
            str = "hh\nmm";
        } else {
            Patterns.INSTANCE.getClass();
            str = Patterns.sClockView12;
        }
        this.format = str;
        Logger logger = getLogger();
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshFormat$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("refreshFormat(", ((LogMessage) obj).getStr1(), ")");
            }
        }, null);
        CharSequence charSequence = this.format;
        obtain.setStr1(charSequence != null ? charSequence.toString() : null);
        logger.getBuffer().commit(obtain);
        if (z) {
            Patterns.INSTANCE.getClass();
            str2 = Patterns.sClockView24;
        } else {
            Patterns.INSTANCE.getClass();
            str2 = Patterns.sClockView12;
        }
        this.descFormat = str2;
        refreshTime();
    }

    public final void refreshTime() {
        this.time.setTimeInMillis(System.currentTimeMillis());
        setContentDescription(DateFormat.format(this.descFormat, this.time));
        CharSequence format = DateFormat.format(this.format, this.time);
        Logger logger = getLogger();
        AnimatableClockView$refreshTime$1 animatableClockView$refreshTime$1 = new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshTime$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("refreshTime: new formattedText=", ((LogMessage) obj).getStr1());
            }
        };
        LogLevel logLevel = LogLevel.DEBUG;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), logLevel, animatableClockView$refreshTime$1, null);
        obtain.setStr1(format != null ? format.toString() : null);
        logger.getBuffer().commit(obtain);
        if (TextUtils.equals(getText(), format)) {
            return;
        }
        setText(format);
        Logger logger2 = getLogger();
        LogMessage obtain2 = logger2.getBuffer().obtain(logger2.getTag(), logLevel, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshTime$3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("refreshTime: done setting new time text to: ", ((LogMessage) obj).getStr1());
            }
        }, null);
        obtain2.setStr1(format != null ? format.toString() : null);
        logger2.getBuffer().commit(obtain2);
        if (getLayout() != null) {
            TextAnimator textAnimator = this.textAnimator;
            if (textAnimator != null) {
                Layout layout = getLayout();
                String str = TextAnimator.TAG;
                textAnimator.updateLayout(layout, -1.0f);
            }
            Logger.d$default(getLogger(), "refreshTime: done updating textAnimator layout", null, 2, null);
        }
        requestLayout();
        Logger.d$default(getLogger(), "refreshTime: after requestLayout", null, 2, null);
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        if (i != 0) {
            f = Float.MAX_VALUE;
        }
        this.lastUnconstrainedTextSize = f;
    }

    public final void setTextStyle(int i, Integer num, boolean z, TimeInterpolator timeInterpolator, long j, long j2, AnimatableClockView$animateCharge$startAnimPhase2$1 animatableClockView$animateCharge$startAnimPhase2$1) {
        Unit unit;
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            TextAnimator.setTextStyle$default(textAnimator, i, num, z && this.isAnimationEnabled, j, timeInterpolator, j2, animatableClockView$animateCharge$startAnimPhase2$1);
            textAnimator.textInterpolator.glyphFilter = this.glyphFilter;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            this.onTextAnimatorInitialized = new AnimatableClockView$setTextStyle$2$1(i, num, j, timeInterpolator, j2, animatableClockView$animateCharge$startAnimPhase2$1, this);
        }
    }

    public AnimatableClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public AnimatableClockView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ AnimatableClockView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public AnimatableClockView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.logger = DEFAULT_LOGGER;
        this.time = Calendar.getInstance();
        this.lastUnconstrainedTextSize = Float.MAX_VALUE;
        this.textAnimatorFactory = AnimatableClockView$textAnimatorFactory$1.INSTANCE;
        this.isAnimationEnabled = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AnimatableClockView, i, i2);
        try {
            this.dozingWeightInternal = obtainStyledAttributes.getInt(1, 100);
            this.lockScreenWeightInternal = obtainStyledAttributes.getInt(2, 300);
            this.chargeAnimationDelay = obtainStyledAttributes.getInt(0, 200);
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.TextView, i, i2);
            try {
                this.isSingleLineInternal = obtainStyledAttributes.getBoolean(32, false);
                obtainStyledAttributes.recycle();
                refreshFormat(DateFormat.is24HourFormat(getContext()));
                this.glyphOffsets = CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f));
                CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f));
                this.glyphFilter = new Function2() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$glyphFilter$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        TextAnimator.PositionedGlyph positionedGlyph = (TextAnimator.PositionedGlyph) obj;
                        ((Number) obj2).floatValue();
                        int glyphIndex = positionedGlyph.getGlyphIndex() + (positionedGlyph.lineNo * 2);
                        if (glyphIndex < AnimatableClockView.this.glyphOffsets.size()) {
                            positionedGlyph.x = ((Number) AnimatableClockView.this.glyphOffsets.get(glyphIndex)).floatValue() + positionedGlyph.x;
                        }
                        return Unit.INSTANCE;
                    }
                };
            } finally {
            }
        } finally {
        }
    }

    public static /* synthetic */ void getTextAnimatorFactory$annotations() {
    }

    public static /* synthetic */ void getTimeOverrideInMillis$annotations() {
    }

    public static /* synthetic */ void isAnimationEnabled$annotations() {
    }
}
