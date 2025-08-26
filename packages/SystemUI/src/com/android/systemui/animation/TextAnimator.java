package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.Log;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.TextInterpolator;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class TextAnimator {
    public static final String TAG;
    public ValueAnimator animator;
    public final TextAnimator$$ExternalSyntheticLambda0 createAnimator;
    public final FontVariationUtils fontVariationUtils;
    public final TextAnimatorListener listener;
    public final TextInterpolator textInterpolator;
    public final TypefaceVariantCache typefaceCache;

    public final class Animation {
        public static final Companion Companion = new Companion(null);
        public static final Animation DISABLED = new Animation(false, 0, 0, null, null, 30, null);
        public final boolean animate;
        public final long duration;
        public final TimeInterpolator interpolator;
        public final Runnable onAnimationEnd;
        public final long startDelay;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Animation() {
            this(false, 0L, 0L, null, null, 31, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Animation)) {
                return false;
            }
            Animation animation = (Animation) obj;
            return this.animate == animation.animate && this.startDelay == animation.startDelay && this.duration == animation.duration && Intrinsics.areEqual(this.interpolator, animation.interpolator) && Intrinsics.areEqual(this.onAnimationEnd, animation.onAnimationEnd);
        }

        public final int hashCode() {
            int iHashCode = (this.interpolator.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.animate) * 31, 31, this.startDelay), 31, this.duration)) * 31;
            Runnable runnable = this.onAnimationEnd;
            return iHashCode + (runnable == null ? 0 : runnable.hashCode());
        }

        public final String toString() {
            return "Animation(animate=" + this.animate + ", startDelay=" + this.startDelay + ", duration=" + this.duration + ", interpolator=" + this.interpolator + ", onAnimationEnd=" + this.onAnimationEnd + ")";
        }

        public Animation(boolean z, long j, long j2, TimeInterpolator timeInterpolator, Runnable runnable) {
            this.animate = z;
            this.startDelay = j;
            this.duration = j2;
            this.interpolator = timeInterpolator;
            this.onAnimationEnd = runnable;
        }

        public /* synthetic */ Animation(boolean z, long j, long j2, TimeInterpolator timeInterpolator, Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? true : z, (i & 2) != 0 ? 0L : j, (i & 4) != 0 ? 300L : j2, (i & 8) != 0 ? Interpolators.LINEAR : timeInterpolator, (i & 16) != 0 ? null : runnable);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract class PositionedGlyph {
        public int color;
        public int lineNo;
        public float textSize;
        public float x;
        public float y;

        public /* synthetic */ PositionedGlyph(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract int getGlyphIndex();

        private PositionedGlyph() {
        }
    }

    public final class Style {
        public final Integer color;
        public final String fVar;
        public final Float strokeWidth;
        public final Float textSize;

        public Style() {
            this(null, null, null, null, 15, null);
        }

        public static Style withUpdatedFVar$default(Style style, FontVariationUtils fontVariationUtils, int i) {
            String string;
            if (i < 0 || fontVariationUtils.mWeight == i) {
                string = fontVariationUtils.mCurrentFVar;
            } else {
                fontVariationUtils.mWeight = i;
                StringBuilder sb = new StringBuilder();
                if (fontVariationUtils.mWeight >= 0) {
                    if (!StringsKt__StringsKt.isBlank(sb)) {
                        sb.append(", ");
                    }
                    sb.append("'" + GSFAxes.WEIGHT.tag + "' " + fontVariationUtils.mWeight);
                }
                string = sb.toString();
                fontVariationUtils.mCurrentFVar = string;
            }
            return new Style(string, style.textSize, style.color, style.strokeWidth);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Style)) {
                return false;
            }
            Style style = (Style) obj;
            return Intrinsics.areEqual(this.fVar, style.fVar) && Intrinsics.areEqual(this.textSize, style.textSize) && Intrinsics.areEqual(this.color, style.color) && Intrinsics.areEqual(this.strokeWidth, style.strokeWidth);
        }

        public final int hashCode() {
            String str = this.fVar;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            Float f = this.textSize;
            int iHashCode2 = (iHashCode + (f == null ? 0 : f.hashCode())) * 31;
            Integer num = this.color;
            int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
            Float f2 = this.strokeWidth;
            return iHashCode3 + (f2 != null ? f2.hashCode() : 0);
        }

        public final String toString() {
            return "Style(fVar=" + this.fVar + ", textSize=" + this.textSize + ", color=" + this.color + ", strokeWidth=" + this.strokeWidth + ")";
        }

        public Style(String str, Float f, Integer num, Float f2) {
            this.fVar = str;
            this.textSize = f;
            this.color = num;
            this.strokeWidth = f2;
        }

        public /* synthetic */ Style(String str, Float f, Integer num, Float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : f, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : f2);
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(TextAnimator.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public TextAnimator(Layout layout, TypefaceVariantCache typefaceVariantCache, TextAnimatorListener textAnimatorListener) {
        this.typefaceCache = typefaceVariantCache;
        this.listener = textAnimatorListener;
        this.textInterpolator = new TextInterpolator(layout, typefaceVariantCache, textAnimatorListener);
        this.createAnimator = new TextAnimator$$ExternalSyntheticLambda0();
        this.fontVariationUtils = new FontVariationUtils();
    }

    public static void setTextStyle$default(TextAnimator textAnimator, Style style) {
        Animation.Companion.getClass();
        textAnimator.setTextStyle(style, Animation.DISABLED);
    }

    public final void draw(Canvas canvas) throws Throwable {
        Canvas canvas2;
        Throwable th;
        float lineLeft;
        TextInterpolator textInterpolator = this.textInterpolator;
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
                    if (layout.getParagraphDirection(i) == 1) {
                        try {
                            lineLeft = layout.getLineLeft(i);
                        } catch (Throwable th2) {
                            th = th2;
                            canvas2 = canvas;
                            canvas2.restore();
                            throw th;
                        }
                    } else {
                        lineLeft = layout.getLineRight(i);
                    }
                    canvas.translate(lineLeft, textInterpolator.layout.getLineBaseline(i));
                    Iterator it = run.fontRuns.iterator();
                    while (it.hasNext()) {
                        canvas2 = canvas;
                        try {
                            textInterpolator.drawFontRun(canvas2, run, (TextInterpolator.FontRun) it.next(), i, textInterpolator.tmpPaint);
                            canvas = canvas2;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            canvas2.restore();
                            throw th;
                        }
                    }
                    Canvas canvas3 = canvas;
                    canvas3.restore();
                    canvas = canvas3;
                } catch (Throwable th4) {
                    th = th4;
                    canvas2 = canvas;
                }
            }
            i = i2;
        }
    }

    public final void setTextStyle(Style style, final Animation animation) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        setTextStyleInternal(style, animation.animate, true);
        if (!animation.animate) {
            TextInterpolator textInterpolator = this.textInterpolator;
            textInterpolator.progress = 1.0f;
            textInterpolator.linearProgress = 1.0f;
            textInterpolator.rebase();
            TextAnimatorListener textAnimatorListener = this.listener;
            if (textAnimatorListener != null) {
                textAnimatorListener.onInvalidate();
                return;
            }
            return;
        }
        ValueAnimator valueAnimator2 = (ValueAnimator) this.createAnimator.invoke();
        valueAnimator2.setDuration(300L);
        valueAnimator2.setStartDelay(animation.startDelay);
        valueAnimator2.setDuration(animation.duration);
        valueAnimator2.setInterpolator(animation.interpolator);
        if (animation.onAnimationEnd != null) {
            valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$Animation$configureAnimator$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    animation.onAnimationEnd.run();
                }
            });
        }
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.TextAnimator$buildAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                this.this$0.textInterpolator.progress = ((Float) valueAnimator3.getAnimatedValue()).floatValue();
                this.this$0.textInterpolator.linearProgress = valueAnimator3.getCurrentPlayTime() / valueAnimator3.getDuration();
                TextAnimatorListener textAnimatorListener2 = this.this$0.listener;
                if (textAnimatorListener2 != null) {
                    textAnimatorListener2.onInvalidate();
                }
            }
        });
        valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$buildAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.this$0.textInterpolator.rebase();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                this.this$0.textInterpolator.rebase();
            }
        });
        valueAnimator2.start();
        this.animator = valueAnimator2;
    }

    public final void setTextStyleInternal(Style style, boolean z, boolean z2) {
        TextInterpolator textInterpolator = this.textInterpolator;
        if (z) {
            try {
                textInterpolator.rebase();
            } catch (IllegalArgumentException e) {
                if (!z2) {
                    throw e;
                }
                Log.e(TAG, "setTextStyleInternal: Exception caught but retrying. This is usually due to the layout having changed unexpectedly without being notified.", e);
                updateLayout(textInterpolator.layout, -1.0f);
                setTextStyleInternal(style, z, false);
                return;
            }
        }
        Integer num = style.color;
        if (num != null) {
            textInterpolator.targetPaint.setColor(num.intValue());
        }
        Float f = style.textSize;
        if (f != null) {
            textInterpolator.targetPaint.setTextSize(f.floatValue());
        }
        Float f2 = style.strokeWidth;
        if (f2 != null) {
            textInterpolator.targetPaint.setStrokeWidth(f2.floatValue());
        }
        String str = style.fVar;
        if (str != null) {
            textInterpolator.targetPaint.setTypeface(this.typefaceCache.getTypefaceForVariant(str));
        }
        textInterpolator.onTargetPaintModified();
    }

    public final void updateLayout(Layout layout, float f) {
        TextInterpolator textInterpolator = this.textInterpolator;
        textInterpolator.layout = layout;
        textInterpolator.shapeText(layout);
        if (f >= 0.0f) {
            textInterpolator.targetPaint.setTextSize(f);
            textInterpolator.basePaint.setTextSize(f);
            textInterpolator.onTargetPaintModified();
            textInterpolator.updatePositionsAndFonts(textInterpolator.shapeText(textInterpolator.layout, textInterpolator.basePaint), true);
            TextInterpolatorListener textInterpolatorListener = textInterpolator.listener;
            if (textInterpolatorListener != null) {
                textInterpolatorListener.onPaintModified();
            }
        }
    }

    public /* synthetic */ TextAnimator(Layout layout, TypefaceVariantCache typefaceVariantCache, TextAnimatorListener textAnimatorListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(layout, typefaceVariantCache, (i & 4) != 0 ? null : textAnimatorListener);
    }

    public static /* synthetic */ void getCreateAnimator$annotations() {
    }
}
