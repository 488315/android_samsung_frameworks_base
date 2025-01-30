package com.android.systemui.shared.clocks;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextInterpolator;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.customization.R$styleable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AnimatableClockView extends TextView {
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
    public final boolean isAnimationEnabled;
    public final boolean isSingleLineInternal;
    public int lockScreenColor;
    public final int lockScreenWeightInternal;
    public LogBuffer logBuffer;
    public AnimatableClockView$setTextStyle$1 onTextAnimatorInitialized;
    public TextAnimator textAnimator;
    public final Function2 textAnimatorFactory;
    public final Calendar time;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        MOVE_INTERPOLATOR = Interpolators.EMPHASIZED;
        MOVE_LEFT_DELAYS = CollectionsKt__CollectionsKt.listOf(0, 1, 2, 3);
        MOVE_RIGHT_DELAYS = CollectionsKt__CollectionsKt.listOf(1, 0, 3, 2);
    }

    public AnimatableClockView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final void animateDoze(boolean z, boolean z2) {
        int lockScreenWeight;
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "animateDoze", null, 8, null);
        }
        if (z) {
            boolean z3 = getResources().getConfiguration().fontWeightAdjustment > 100;
            lockScreenWeight = this.dozingWeightInternal;
            if (z3) {
                lockScreenWeight += 100;
            }
        } else {
            lockScreenWeight = getLockScreenWeight();
        }
        setTextStyle(lockScreenWeight, Integer.valueOf(z ? this.dozingColor : this.lockScreenColor), z2 && this.isAnimationEnabled, 300L, 0L, null);
    }

    public final void animateFoldAppear(boolean z) {
        if (this.isAnimationEnabled && this.textAnimator == null) {
            return;
        }
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "animateFoldAppear", null, 8, null);
        }
        setTextStyle(this.lockScreenWeightInternal, Integer.valueOf(this.lockScreenColor), false, 0L, 0L, null);
        setTextStyle(this.dozingWeightInternal, Integer.valueOf(this.dozingColor), z && this.isAnimationEnabled, Interpolators.EMPHASIZED_DECELERATE, 600L, 0L, null);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println(String.valueOf(this));
        printWriter.println("    alpha=" + getAlpha());
        printWriter.println("    measuredWidth=" + getMeasuredWidth());
        SideFpsController$$ExternalSyntheticOutline0.m105m("    measuredHeight=", getMeasuredHeight(), printWriter);
        printWriter.println("    singleLineInternal=" + this.isSingleLineInternal);
        printWriter.println("    currText=" + ((Object) getText()));
        printWriter.println("    currTimeContextDesc=" + ((Object) getContentDescription()));
        SideFpsController$$ExternalSyntheticOutline0.m105m("    dozingWeightInternal=", this.dozingWeightInternal, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("    lockScreenWeightInternal=", this.lockScreenWeightInternal, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("    dozingColor=", this.dozingColor, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("    lockScreenColor=", this.lockScreenColor, printWriter);
        printWriter.println("    time=" + this.time);
    }

    public final int getLockScreenWeight() {
        boolean z = getResources().getConfiguration().fontWeightAdjustment > 100;
        int i = this.lockScreenWeightInternal;
        return z ? i + 100 : i;
    }

    @Override // android.view.View
    public final void invalidate() {
        super.invalidate();
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "invalidate", null, 8, null);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "onAttachedToWindow", null, 8, null);
        }
        refreshFormat(DateFormat.is24HourFormat(getContext()));
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.isAnimationEnabled) {
            TextAnimator textAnimator = this.textAnimator;
            if (textAnimator != null) {
                TextInterpolator textInterpolator = textAnimator.textInterpolator;
                TextPaint textPaint = textInterpolator.basePaint;
                TextPaint textPaint2 = textInterpolator.targetPaint;
                float f = textInterpolator.progress;
                TextPaint textPaint3 = textInterpolator.tmpPaint;
                TextInterpolator.lerp(textPaint, textPaint2, f, textPaint3);
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
                                textInterpolator.drawFontRun(canvas, run, (TextInterpolator.FontRun) it.next(), i, textPaint3);
                            }
                        } finally {
                            canvas.restore();
                        }
                    }
                    i = i2;
                }
            }
        } else {
            super.onDraw(canvas);
        }
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "onDraw", null, 8, null);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            this.textAnimator = (TextAnimator) ((AnimatableClockView$textAnimatorFactory$1) this.textAnimatorFactory).invoke(getLayout(), new AnimatableClockView$onMeasure$1(this));
            AnimatableClockView$setTextStyle$1 animatableClockView$setTextStyle$1 = this.onTextAnimatorInitialized;
            if (animatableClockView$setTextStyle$1 != null) {
                animatableClockView$setTextStyle$1.run();
            }
            this.onTextAnimatorInitialized = null;
        } else {
            Layout layout = getLayout();
            TextInterpolator textInterpolator = textAnimator.textInterpolator;
            textInterpolator.layout = layout;
            textInterpolator.shapeText(layout);
        }
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, TAG, LogLevel.DEBUG, "onMeasure", null, 8, null);
        }
    }

    @Override // android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogMessage obtain = logBuffer.obtain(TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$onTextChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("onTextChanged text=", ((LogMessage) obj).getStr1());
                }
            }, null);
            obtain.setStr1(charSequence.toString());
            logBuffer.commit(obtain);
        }
    }

    public final void refreshFormat(boolean z) {
        String str;
        String str2;
        Patterns patterns = Patterns.INSTANCE;
        Context context = getContext();
        patterns.getClass();
        Locale locale = Locale.getDefault();
        Resources resources = context.getResources();
        String string = resources.getString(R.string.clock_12hr_format);
        String string2 = resources.getString(R.string.clock_24hr_format);
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(locale.toString(), string, string2);
        if (!Intrinsics.areEqual(m15m, Patterns.sCacheKey)) {
            String bestDateTimePattern = DateFormat.getBestDateTimePattern(locale, string);
            Patterns.sClockView12 = bestDateTimePattern;
            if (!StringsKt__StringsKt.contains(string, "a", false)) {
                String replace = new Regex("a").replace(bestDateTimePattern);
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
                Patterns.sClockView12 = replace.subSequence(i, length + 1).toString();
            }
            Patterns.sClockView24 = DateFormat.getBestDateTimePattern(locale, string2);
            Patterns.sCacheKey = m15m;
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
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogMessage obtain = logBuffer.obtain(TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshFormat$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("refreshFormat format=", ((LogMessage) obj).getStr1());
                }
            }, null);
            CharSequence charSequence = this.format;
            obtain.setStr1(charSequence != null ? charSequence.toString() : null);
            logBuffer.commit(obtain);
        }
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
        LogBuffer logBuffer = this.logBuffer;
        if (logBuffer != null) {
            LogMessage obtain = logBuffer.obtain(TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshTime$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("refreshTime: new formattedText=", ((LogMessage) obj).getStr1());
                }
            }, null);
            obtain.setStr1(format != null ? format.toString() : null);
            logBuffer.commit(obtain);
        }
        if (TextUtils.equals(getText(), format)) {
            return;
        }
        setText(format);
        LogBuffer logBuffer2 = this.logBuffer;
        if (logBuffer2 != null) {
            LogMessage obtain2 = logBuffer2.obtain(TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$refreshTime$4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("refreshTime: done setting new time text to: ", ((LogMessage) obj).getStr1());
                }
            }, null);
            obtain2.setStr1(format != null ? format.toString() : null);
            logBuffer2.commit(obtain2);
        }
        if (getLayout() != null) {
            TextAnimator textAnimator = this.textAnimator;
            if (textAnimator != null) {
                Layout layout = getLayout();
                TextInterpolator textInterpolator = textAnimator.textInterpolator;
                textInterpolator.layout = layout;
                textInterpolator.shapeText(layout);
            }
            LogBuffer logBuffer3 = this.logBuffer;
            if (logBuffer3 != null) {
                LogBuffer.log$default(logBuffer3, TAG, LogLevel.DEBUG, "refreshTime: done updating textAnimator layout", null, 8, null);
            }
        }
        requestLayout();
        LogBuffer logBuffer4 = this.logBuffer;
        if (logBuffer4 != null) {
            LogBuffer.log$default(logBuffer4, TAG, LogLevel.DEBUG, "refreshTime: after requestLayout", null, 8, null);
        }
    }

    public final void setTextStyle(int i, Integer num, boolean z, TimeInterpolator timeInterpolator, long j, long j2, AnimatableClockView$animateCharge$startAnimPhase2$1 animatableClockView$animateCharge$startAnimPhase2$1) {
        TextAnimator textAnimator = this.textAnimator;
        if (textAnimator == null) {
            this.onTextAnimatorInitialized = new AnimatableClockView$setTextStyle$1(this, i, -1.0f, num, j, timeInterpolator, j2, animatableClockView$animateCharge$startAnimPhase2$1);
            return;
        }
        TextAnimator.setTextStyle$default(textAnimator, i, -1.0f, num, z && this.isAnimationEnabled, j, timeInterpolator, j2, animatableClockView$animateCharge$startAnimPhase2$1);
        TextAnimator textAnimator2 = this.textAnimator;
        if (textAnimator2 != null) {
            textAnimator2.textInterpolator.glyphFilter = this.glyphFilter;
        }
        if (num == null || this.isAnimationEnabled) {
            return;
        }
        setTextColor(num.intValue());
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
        this.time = Calendar.getInstance();
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
                boolean z = obtainStyledAttributes.getBoolean(32, false);
                obtainStyledAttributes.recycle();
                this.isSingleLineInternal = z;
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
                            positionedGlyph.f227x = ((Number) AnimatableClockView.this.glyphOffsets.get(glyphIndex)).floatValue() + positionedGlyph.f227x;
                        }
                        return Unit.INSTANCE;
                    }
                };
            } finally {
            }
        } finally {
        }
    }

    public final void setTextStyle(int i, Integer num, boolean z, long j, long j2, AnimatableClockView$animateCharge$startAnimPhase2$1 animatableClockView$animateCharge$startAnimPhase2$1) {
        setTextStyle(i, num, z && this.isAnimationEnabled, null, j, j2, animatableClockView$animateCharge$startAnimPhase2$1);
    }

    public static /* synthetic */ void getTextAnimatorFactory$annotations() {
    }

    public static /* synthetic */ void getTimeOverrideInMillis$annotations() {
    }

    public static /* synthetic */ void isAnimationEnabled$annotations() {
    }
}
