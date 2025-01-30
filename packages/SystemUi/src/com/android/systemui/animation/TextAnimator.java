package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.fonts.Font;
import android.graphics.text.PositionedGlyphs;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Layout;
import com.android.systemui.animation.FontInterpolator;
import com.android.systemui.animation.TextInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TextAnimator {
    public final ValueAnimator animator;
    public final FontVariationUtils fontVariationUtils;
    public final Function0 invalidateCallback;
    public final TextInterpolator textInterpolator;
    public final TypefaceVariantCacheImpl typefaceCache;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PositionedGlyph {
        public int color;
        public int lineNo;
        public float textSize;

        /* renamed from: x */
        public float f227x;

        /* renamed from: y */
        public float f228y;

        private PositionedGlyph() {
        }

        public /* synthetic */ PositionedGlyph(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract int getGlyphIndex();
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

    public static void setTextStyle$default(final TextAnimator textAnimator, int i, float f, Integer num, boolean z, long j, TimeInterpolator timeInterpolator, long j2, final Runnable runnable) {
        FontVariationUtils fontVariationUtils = textAnimator.fontVariationUtils;
        fontVariationUtils.isUpdated = false;
        if (i >= 0 && fontVariationUtils.mWeight != i) {
            fontVariationUtils.isUpdated = true;
            fontVariationUtils.mWeight = i;
        }
        int i2 = fontVariationUtils.mWeight;
        String m0m = i2 >= 0 ? AbstractC0000x2c234b15.m0m("'wght' ", i2) : "";
        if (fontVariationUtils.mWidth >= 0) {
            String str = StringsKt__StringsJVMKt.isBlank(m0m) ? "" : ", ";
            m0m = m0m + str + "'wdth' " + fontVariationUtils.mWidth;
        }
        if (fontVariationUtils.mOpticalSize >= 0) {
            String str2 = StringsKt__StringsJVMKt.isBlank(m0m) ? "" : ", ";
            m0m = m0m + str2 + "'opsz' " + fontVariationUtils.mOpticalSize;
        }
        if (fontVariationUtils.mRoundness >= 0) {
            String str3 = StringsKt__StringsJVMKt.isBlank(m0m) ? "" : ", ";
            m0m = m0m + str3 + "'ROND' " + fontVariationUtils.mRoundness;
        }
        String str4 = fontVariationUtils.isUpdated ? m0m : "";
        ValueAnimator valueAnimator = textAnimator.animator;
        TextInterpolator textInterpolator = textAnimator.textInterpolator;
        if (z) {
            valueAnimator.cancel();
            textInterpolator.rebase();
        }
        if (f >= 0.0f) {
            textInterpolator.targetPaint.setTextSize(f);
        }
        if (!(str4 == null || StringsKt__StringsJVMKt.isBlank(str4))) {
            textInterpolator.targetPaint.setTypeface(textAnimator.typefaceCache.getTypefaceForVariant(str4));
        }
        if (num != null) {
            textInterpolator.targetPaint.setColor(num.intValue());
        }
        List shapeText = TextInterpolator.shapeText(textInterpolator.layout, textInterpolator.targetPaint);
        ArrayList arrayList = (ArrayList) shapeText;
        if (!(arrayList.size() == textInterpolator.lines.size())) {
            throw new IllegalStateException("The new layout result has different line count.".toString());
        }
        List list = textInterpolator.lines;
        Iterator it = list.iterator();
        Iterator it2 = arrayList.iterator();
        int i3 = 10;
        ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(shapeText, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            List list2 = (List) it2.next();
            List list3 = ((TextInterpolator.Line) next).runs;
            Iterator it3 = list3.iterator();
            Iterator it4 = list2.iterator();
            ArrayList arrayList3 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, i3), CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, i3)));
            while (it3.hasNext() && it4.hasNext()) {
                Object next2 = it3.next();
                PositionedGlyphs positionedGlyphs = (PositionedGlyphs) it4.next();
                TextInterpolator.Run run = (TextInterpolator.Run) next2;
                if (!(positionedGlyphs.glyphCount() == run.glyphIds.length)) {
                    throw new IllegalArgumentException("The new layout has different glyph count.".toString());
                }
                Iterator it5 = run.fontRuns.iterator();
                while (it5.hasNext()) {
                    TextInterpolator.FontRun fontRun = (TextInterpolator.FontRun) it5.next();
                    Iterator it6 = it;
                    Font font = positionedGlyphs.getFont(fontRun.start);
                    Iterator it7 = it2;
                    int i4 = fontRun.start;
                    Iterator it8 = it5;
                    Iterator it9 = it3;
                    int i5 = i4;
                    while (i5 < fontRun.end) {
                        Iterator it10 = it4;
                        if (!(positionedGlyphs.getGlyphId(i4) == run.glyphIds[i4])) {
                            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("The new layout has different glyph ID at ", i4).toString());
                        }
                        if (!(font == positionedGlyphs.getFont(i5))) {
                            throw new IllegalArgumentException(("The new layout has different font run. " + font + " vs " + positionedGlyphs.getFont(i5) + " at " + i5).toString());
                        }
                        i5++;
                        it4 = it10;
                    }
                    Iterator it11 = it4;
                    FontInterpolator.Companion companion = FontInterpolator.Companion;
                    Font font2 = fontRun.baseFont;
                    companion.getClass();
                    if (!FontInterpolator.Companion.canInterpolate(font, font2)) {
                        throw new IllegalArgumentException(("New font cannot be interpolated with existing font. " + font + ", " + fontRun.baseFont).toString());
                    }
                    fontRun.targetFont = font;
                    it = it6;
                    it2 = it7;
                    it5 = it8;
                    it3 = it9;
                    it4 = it11;
                }
                Iterator it12 = it;
                Iterator it13 = it2;
                Iterator it14 = it3;
                Iterator it15 = it4;
                int length = run.baseX.length;
                for (int i6 = 0; i6 < length; i6++) {
                    run.targetX[i6] = positionedGlyphs.getGlyphX(i6);
                    run.targetY[i6] = positionedGlyphs.getGlyphY(i6);
                }
                arrayList3.add(Unit.INSTANCE);
                it = it12;
                it2 = it13;
                it3 = it14;
                it4 = it15;
            }
            arrayList2.add(arrayList3);
            i3 = 10;
            it = it;
            it2 = it2;
        }
        if (!z) {
            textInterpolator.progress = 1.0f;
            textInterpolator.rebase();
            textAnimator.invalidateCallback.invoke();
            return;
        }
        valueAnimator.setStartDelay(j2);
        valueAnimator.setDuration(j == -1 ? 300L : j);
        if (timeInterpolator != null) {
            valueAnimator.setInterpolator(timeInterpolator);
        }
        if (runnable != null) {
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$setTextStyle$listener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    textAnimator.animator.removeListener(this);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    textAnimator.animator.removeListener(this);
                }
            });
        }
        valueAnimator.start();
    }

    public /* synthetic */ TextAnimator(Layout layout, Integer num, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(layout, (i & 2) != 0 ? null : num, function0);
    }
}
