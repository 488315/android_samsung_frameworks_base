package com.android.systemui.shared.clocks;

import android.R;
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
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.customization.R$string;
import com.android.systemui.customization.R$styleable;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.LogcatOnlyMessageBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.ClockLogger;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimatableClockView extends TextView {
    public static final ClockLogger DEFAULT_LOGGER;
    public static final Interpolator MOVE_INTERPOLATOR;
    public static final List MOVE_LEFT_DELAYS;
    public static final List MOVE_RIGHT_DELAYS;
    public static final String TAG;
    public final int chargeAnimationDelay;
    public CharSequence descFormat;
    public int dozingColor;
    public final int dozingWeightInternal;
    public CharSequence format;
    public final AnimatableClockView$$ExternalSyntheticLambda3 glyphFilter;
    public final List glyphOffsets;
    public boolean hasCustomPositionUpdatedAnimation;
    public final boolean isAnimationEnabled;
    public final boolean isSingleLineInternal;
    public float lastUnconstrainedTextSize;
    public int lockScreenColor;
    public final int lockScreenWeightInternal;
    public ClockLogger logger;
    public AnimatableClockView$$ExternalSyntheticLambda6 onTextAnimatorInitialized;
    public TextAnimator textAnimator;
    public final AnimatableClockView$$ExternalSyntheticLambda2 textAnimatorFactory;
    public final Calendar time;
    public boolean translateForCenterAnimation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        simpleName.getClass();
        TAG = simpleName;
        DEFAULT_LOGGER = new ClockLogger(null, new LogcatOnlyMessageBuffer(LogLevel.DEBUG), simpleName);
        MOVE_INTERPOLATOR = Interpolators.EMPHASIZED;
        MOVE_LEFT_DELAYS = Arrays.asList(0, 1, 2, 3);
        MOVE_RIGHT_DELAYS = Arrays.asList(1, 0, 3, 2);
    }

    public AnimatableClockView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final void animateDoze(boolean z, boolean z2) {
        getLogger().animateDoze(z, z2);
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
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    measuredHeight=", getMeasuredHeight(), printWriter);
        printWriter.println("    singleLineInternal=" + this.isSingleLineInternal);
        printWriter.println("    currText=" + ((Object) getText()));
        printWriter.println("    currTimeContextDesc=" + ((Object) getContentDescription()));
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    dozingWeightInternal=", this.dozingWeightInternal, printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    lockScreenWeightInternal=", this.lockScreenWeightInternal, printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    dozingColor=", this.dozingColor, printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    lockScreenColor=", this.lockScreenColor, printWriter);
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

    public final ClockLogger getLogger() {
        ClockLogger clockLogger = this.logger;
        return clockLogger == null ? DEFAULT_LOGGER : clockLogger;
    }

    @Override // android.view.View
    public final void invalidate() {
        getLogger().invalidate();
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
        getLogger().onDraw(String.valueOf(getText()));
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            textAnimator.draw(canvas);
        }
        canvas.restore();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        getLogger().onMeasure(i, i2);
        if (!this.isSingleLineInternal && View.MeasureSpec.getMode(i2) == 1073741824) {
            super.setTextSize(0, Math.min(this.lastUnconstrainedTextSize, View.MeasureSpec.getSize(i2) / 2.0f));
        }
        super.onMeasure(i, i2);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator != null) {
            textAnimator.updateLayout(getLayout(), getTextSize());
        } else {
            TextAnimator textAnimator2 = (TextAnimator) this.textAnimatorFactory.invoke(getLayout(), new AnimatableClockView$onMeasure$2$1(this));
            AnimatableClockView$$ExternalSyntheticLambda6 animatableClockView$$ExternalSyntheticLambda6 = this.onTextAnimatorInitialized;
            if (animatableClockView$$ExternalSyntheticLambda6 != null) {
                animatableClockView$$ExternalSyntheticLambda6.mo779invoke(textAnimator2);
            }
            this.onTextAnimatorInitialized = null;
            this.textAnimator = textAnimator2;
        }
        if (!this.hasCustomPositionUpdatedAnimation) {
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
        if (i == 1) {
            setTextAlignment(3);
        } else {
            setTextAlignment(2);
        }
        super.onRtlPropertiesChanged(i);
    }

    @Override // android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        ClockLogger logger = getLogger();
        AnimatableClockView$$ExternalSyntheticLambda0 animatableClockView$$ExternalSyntheticLambda0 = new AnimatableClockView$$ExternalSyntheticLambda0(1);
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, animatableClockView$$ExternalSyntheticLambda0, null);
        obtain.setStr1(String.valueOf(charSequence));
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
        String string = context.getResources().getString(R$string.clock_12hr_format);
        String string2 = context.getResources().getString(R$string.clock_24hr_format);
        String str3 = locale + string + string2;
        if (!Intrinsics.areEqual(str3, Patterns.sCacheKey)) {
            String bestDateTimePattern = DateFormat.getBestDateTimePattern(locale, string);
            if (!StringsKt__StringsKt.contains(string, "a", false)) {
                bestDateTimePattern.getClass();
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
        ClockLogger logger = getLogger();
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new AnimatableClockView$$ExternalSyntheticLambda0(0), null);
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
        ClockLogger logger = getLogger();
        AnimatableClockView$$ExternalSyntheticLambda0 animatableClockView$$ExternalSyntheticLambda0 = new AnimatableClockView$$ExternalSyntheticLambda0(2);
        LogLevel logLevel = LogLevel.DEBUG;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), logLevel, animatableClockView$$ExternalSyntheticLambda0, null);
        obtain.setStr1(format != null ? format.toString() : null);
        logger.getBuffer().commit(obtain);
        if (TextUtils.equals(getText(), format)) {
            return;
        }
        setText(format);
        ClockLogger logger2 = getLogger();
        LogMessage obtain2 = logger2.getBuffer().obtain(logger2.getTag(), logLevel, new AnimatableClockView$$ExternalSyntheticLambda0(3), null);
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
        TextAnimator.Style style = new TextAnimator.Style(null, null, num, null, 11, null);
        boolean z2 = z && this.isAnimationEnabled;
        TimeInterpolator timeInterpolator2 = timeInterpolator == null ? Interpolators.LINEAR : timeInterpolator;
        timeInterpolator2.getClass();
        TextAnimator.Animation animation = new TextAnimator.Animation(z2, j2, j, timeInterpolator2, animatableClockView$animateCharge$startAnimPhase2$1);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            this.onTextAnimatorInitialized = new AnimatableClockView$$ExternalSyntheticLambda6(style, i, animation, this);
        } else {
            textAnimator.setTextStyle(TextAnimator.Style.withUpdatedFVar$default(style, textAnimator.fontVariationUtils, i), animation);
            textAnimator.textInterpolator.glyphFilter = this.glyphFilter;
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
        this.textAnimatorFactory = new AnimatableClockView$$ExternalSyntheticLambda2();
        this.isAnimationEnabled = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AnimatableClockView, i, i2);
        try {
            this.dozingWeightInternal = obtainStyledAttributes.getInt(R$styleable.AnimatableClockView_dozeWeight, 100);
            this.lockScreenWeightInternal = obtainStyledAttributes.getInt(R$styleable.AnimatableClockView_lockScreenWeight, 300);
            this.chargeAnimationDelay = obtainStyledAttributes.getInt(R$styleable.AnimatableClockView_chargeAnimationDelay, 200);
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextView, i, i2);
            try {
                this.isSingleLineInternal = obtainStyledAttributes.getBoolean(32, false);
                obtainStyledAttributes.recycle();
                refreshFormat(DateFormat.is24HourFormat(getContext()));
                this.glyphOffsets = CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f));
                CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f));
                this.glyphFilter = new AnimatableClockView$$ExternalSyntheticLambda3(this);
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
