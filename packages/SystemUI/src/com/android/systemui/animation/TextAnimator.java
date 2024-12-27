package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Layout;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class TextAnimator {
    public static final String TAG;
    public final ValueAnimator animator;
    public final FontVariationUtils fontVariationUtils;
    public final Function0 invalidateCallback;
    public final TextInterpolator textInterpolator;
    public final TypefaceVariantCacheImpl typefaceCache;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class PositionedGlyph {
        public int color;
        public int lineNo;
        public float textSize;
        public float x;
        public float y;

        private PositionedGlyph() {
        }

        public abstract int getGlyphIndex();

        public /* synthetic */ PositionedGlyph(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(TextAnimator.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }

    public TextAnimator(Layout layout, final Integer num, Function0 function0) {
        this.invalidateCallback = function0;
        TypefaceVariantCacheImpl typefaceVariantCacheImpl = new TypefaceVariantCacheImpl(layout.getPaint().getTypeface());
        this.typefaceCache = typefaceVariantCacheImpl;
        this.textInterpolator = new TextInterpolator(layout, typefaceVariantCacheImpl, num);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ofFloat.setDuration(300L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.TextAnimator$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TextAnimator textAnimator = TextAnimator.this;
                TextInterpolator textInterpolator = textAnimator.textInterpolator;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Integer num2 = num;
                textAnimator.getClass();
                if (num2 != null) {
                    floatValue = MathKt__MathJVMKt.roundToInt(floatValue * num2.intValue()) / num2.intValue();
                }
                textInterpolator.progress = floatValue;
                TextAnimator.this.invalidateCallback.invoke();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$animator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                TextAnimator.this.textInterpolator.rebase();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TextAnimator.this.textInterpolator.rebase();
            }
        });
        this.animator = ofFloat;
        this.fontVariationUtils = new FontVariationUtils();
    }

    public static void setTextStyle$default(TextAnimator textAnimator, int i, Integer num, boolean z, long j, TimeInterpolator timeInterpolator, long j2, Runnable runnable) {
        FontVariationUtils fontVariationUtils = textAnimator.fontVariationUtils;
        fontVariationUtils.isUpdated = false;
        if (i >= 0 && fontVariationUtils.mWeight != i) {
            fontVariationUtils.isUpdated = true;
            fontVariationUtils.mWeight = i;
        }
        int i2 = fontVariationUtils.mWeight;
        String m = i2 >= 0 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "'wght' ") : "";
        if (fontVariationUtils.mWidth >= 0) {
            String str = StringsKt__StringsJVMKt.isBlank(m) ? "" : ", ";
            m = m + str + "'wdth' " + fontVariationUtils.mWidth;
        }
        if (fontVariationUtils.mOpticalSize >= 0) {
            String str2 = StringsKt__StringsJVMKt.isBlank(m) ? "" : ", ";
            m = m + str2 + "'opsz' " + fontVariationUtils.mOpticalSize;
        }
        if (fontVariationUtils.mRoundness >= 0) {
            String str3 = StringsKt__StringsJVMKt.isBlank(m) ? "" : ", ";
            m = m + str3 + "'ROND' " + fontVariationUtils.mRoundness;
        }
        textAnimator.setTextStyleInternal(fontVariationUtils.isUpdated ? m : "", -1.0f, num, -1.0f, z, j, timeInterpolator, j2, runnable, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003c, code lost:
    
        r4.targetPaint.setColor(r17.intValue());
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setTextStyleInternal(java.lang.String r15, float r16, java.lang.Integer r17, float r18, boolean r19, long r20, android.animation.TimeInterpolator r22, long r23, final java.lang.Runnable r25, boolean r26) {
        /*
            r14 = this;
            r1 = r14
            r2 = r15
            r3 = r16
            r5 = r18
            r9 = r22
            r12 = r25
            com.android.systemui.animation.TextInterpolator r4 = r1.textInterpolator
            if (r19 == 0) goto L1c
            android.animation.ValueAnimator r0 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L17
            r0.cancel()     // Catch: java.lang.IllegalArgumentException -> L17
            r4.rebase()     // Catch: java.lang.IllegalArgumentException -> L17
            goto L1c
        L17:
            r0 = move-exception
            r10 = r23
            goto L9e
        L1c:
            r0 = 0
            int r6 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r6 < 0) goto L26
            android.text.TextPaint r6 = r4.targetPaint     // Catch: java.lang.IllegalArgumentException -> L17
            r6.setTextSize(r3)     // Catch: java.lang.IllegalArgumentException -> L17
        L26:
            if (r2 == 0) goto L3a
            boolean r6 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r15)     // Catch: java.lang.IllegalArgumentException -> L17
            if (r6 == 0) goto L2f
            goto L3a
        L2f:
            android.text.TextPaint r6 = r4.targetPaint     // Catch: java.lang.IllegalArgumentException -> L17
            com.android.systemui.animation.TypefaceVariantCacheImpl r7 = r1.typefaceCache     // Catch: java.lang.IllegalArgumentException -> L17
            android.graphics.Typeface r7 = r7.getTypefaceForVariant(r15)     // Catch: java.lang.IllegalArgumentException -> L17
            r6.setTypeface(r7)     // Catch: java.lang.IllegalArgumentException -> L17
        L3a:
            if (r17 == 0) goto L45
            android.text.TextPaint r6 = r4.targetPaint     // Catch: java.lang.IllegalArgumentException -> L17
            int r7 = r17.intValue()     // Catch: java.lang.IllegalArgumentException -> L17
            r6.setColor(r7)     // Catch: java.lang.IllegalArgumentException -> L17
        L45:
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 < 0) goto L4e
            android.text.TextPaint r0 = r4.targetPaint     // Catch: java.lang.IllegalArgumentException -> L17
            r0.setStrokeWidth(r5)     // Catch: java.lang.IllegalArgumentException -> L17
        L4e:
            android.text.Layout r0 = r4.layout     // Catch: java.lang.IllegalArgumentException -> L17
            android.text.TextPaint r6 = r4.targetPaint     // Catch: java.lang.IllegalArgumentException -> L17
            java.util.List r0 = com.android.systemui.animation.TextInterpolator.shapeText(r0, r6)     // Catch: java.lang.IllegalArgumentException -> L17
            r6 = 0
            r4.updatePositionsAndFonts(r0, r6)     // Catch: java.lang.IllegalArgumentException -> L17
            if (r19 == 0) goto L8f
            android.animation.ValueAnimator r0 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L17
            r10 = r23
            r0.setStartDelay(r10)     // Catch: java.lang.IllegalArgumentException -> L7b
            android.animation.ValueAnimator r0 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L7b
            r6 = -1
            int r6 = (r20 > r6 ? 1 : (r20 == r6 ? 0 : -1))
            if (r6 != 0) goto L6e
            r6 = 300(0x12c, double:1.48E-321)
            goto L70
        L6e:
            r6 = r20
        L70:
            r0.setDuration(r6)     // Catch: java.lang.IllegalArgumentException -> L7b
            if (r9 == 0) goto L7d
            android.animation.ValueAnimator r0 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L7b
            r0.setInterpolator(r9)     // Catch: java.lang.IllegalArgumentException -> L7b
            goto L7d
        L7b:
            r0 = move-exception
            goto L9e
        L7d:
            if (r12 == 0) goto L89
            com.android.systemui.animation.TextAnimator$setTextStyleInternal$listener$1 r0 = new com.android.systemui.animation.TextAnimator$setTextStyleInternal$listener$1     // Catch: java.lang.IllegalArgumentException -> L7b
            r0.<init>()     // Catch: java.lang.IllegalArgumentException -> L7b
            android.animation.ValueAnimator r6 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L7b
            r6.addListener(r0)     // Catch: java.lang.IllegalArgumentException -> L7b
        L89:
            android.animation.ValueAnimator r0 = r1.animator     // Catch: java.lang.IllegalArgumentException -> L7b
            r0.start()     // Catch: java.lang.IllegalArgumentException -> L7b
            goto Lc4
        L8f:
            r10 = r23
            r0 = 1065353216(0x3f800000, float:1.0)
            r4.progress = r0     // Catch: java.lang.IllegalArgumentException -> L7b
            r4.rebase()     // Catch: java.lang.IllegalArgumentException -> L7b
            kotlin.jvm.functions.Function0 r0 = r1.invalidateCallback     // Catch: java.lang.IllegalArgumentException -> L7b
            r0.invoke()     // Catch: java.lang.IllegalArgumentException -> L7b
            goto Lc4
        L9e:
            if (r26 == 0) goto Lc5
            java.lang.String r6 = com.android.systemui.animation.TextAnimator.TAG
            java.lang.String r7 = "setTextStyleInternal: Exception caught but retrying. This is usually due to the layout having changed unexpectedly without being notified."
            android.util.Log.e(r6, r7, r0)
            android.text.Layout r0 = r4.layout
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            r14.updateLayout(r0, r4)
            r13 = 0
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r20
            r9 = r22
            r10 = r23
            r12 = r25
            r1.setTextStyleInternal(r2, r3, r4, r5, r6, r7, r9, r10, r12, r13)
        Lc4:
            return
        Lc5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.TextAnimator.setTextStyleInternal(java.lang.String, float, java.lang.Integer, float, boolean, long, android.animation.TimeInterpolator, long, java.lang.Runnable, boolean):void");
    }

    public final void updateLayout(Layout layout, float f) {
        TextInterpolator textInterpolator = this.textInterpolator;
        textInterpolator.layout = layout;
        textInterpolator.shapeText(layout);
        if (f >= 0.0f) {
            textInterpolator.targetPaint.setTextSize(f);
            textInterpolator.basePaint.setTextSize(f);
            textInterpolator.updatePositionsAndFonts(TextInterpolator.shapeText(textInterpolator.layout, textInterpolator.targetPaint), false);
            textInterpolator.updatePositionsAndFonts(TextInterpolator.shapeText(textInterpolator.layout, textInterpolator.basePaint), true);
        }
    }

    public /* synthetic */ TextAnimator(Layout layout, Integer num, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(layout, (i & 2) != 0 ? null : num, function0);
    }
}
