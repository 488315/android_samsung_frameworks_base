package com.android.systemui.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.graphics.text.PositionedGlyphs;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextShaper;
import android.util.MathUtils;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.animation.FontInterpolator;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextInterpolator;
import com.android.systemui.shared.clocks.AnimatableClockView$$ExternalSyntheticLambda3;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextInterpolator {
    public final TextPaint basePaint;
    public final FontInterpolator fontInterpolator;
    public AnimatableClockView$$ExternalSyntheticLambda3 glyphFilter;
    public Layout layout;
    public float linearProgress;
    public List lines;
    public final TextInterpolatorListener listener;
    public float progress;
    public String shapedText;
    public final TextPaint targetPaint;
    public final Lazy tmpGlyph$delegate;
    public final TextPaint tmpPaint;
    public final Lazy tmpPaintForGlyph$delegate;
    public float[] tmpPositionArray;
    public final TypefaceVariantCache typefaceCache;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FontRun {
        public Font baseFont;
        public final int end;
        public final int start;
        public Font targetFont;

        public FontRun(int i, int i2, Font font, Font font2) {
            this.start = i;
            this.end = i2;
            this.baseFont = font;
            this.targetFont = font2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FontRun)) {
                return false;
            }
            FontRun fontRun = (FontRun) obj;
            return this.start == fontRun.start && this.end == fontRun.end && Intrinsics.areEqual(this.baseFont, fontRun.baseFont) && Intrinsics.areEqual(this.targetFont, fontRun.targetFont);
        }

        public final int hashCode() {
            return this.targetFont.hashCode() + ((this.baseFont.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.end, Integer.hashCode(this.start) * 31, 31)) * 31);
        }

        public final String toString() {
            return "FontRun(start=" + this.start + ", end=" + this.end + ", baseFont=" + this.baseFont + ", targetFont=" + this.targetFont + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Line {
        public final List runs;

        public Line(List<Run> list) {
            this.runs = list;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MutablePositionedGlyph extends TextAnimator.PositionedGlyph {
        public int glyphIndex;

        public MutablePositionedGlyph() {
            super(null);
        }

        @Override // com.android.systemui.animation.TextAnimator.PositionedGlyph
        public final int getGlyphIndex() {
            return this.glyphIndex;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Run {
        public final float[] baseX;
        public final float[] baseY;
        public final List fontRuns;
        public final int[] glyphIds;
        public final float[] targetX;
        public final float[] targetY;

        public Run(int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, List<FontRun> list) {
            this.glyphIds = iArr;
            this.baseX = fArr;
            this.baseY = fArr2;
            this.targetX = fArr3;
            this.targetY = fArr4;
            this.fontRuns = list;
        }
    }

    public TextInterpolator(Layout layout, TypefaceVariantCache typefaceVariantCache, TextInterpolatorListener textInterpolatorListener) {
        this.typefaceCache = typefaceVariantCache;
        this.listener = textInterpolatorListener;
        this.basePaint = new TextPaint(layout.getPaint());
        this.targetPaint = new TextPaint(layout.getPaint());
        this.lines = EmptyList.INSTANCE;
        this.fontInterpolator = new FontInterpolator(typefaceVariantCache.getFontCache());
        this.tmpPaint = new TextPaint();
        final int i = 0;
        this.tmpPaintForGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.TextInterpolator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return new TextPaint();
                    default:
                        return new TextInterpolator.MutablePositionedGlyph();
                }
            }
        });
        final int i2 = 1;
        this.tmpGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.TextInterpolator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return new TextPaint();
                    default:
                        return new TextInterpolator.MutablePositionedGlyph();
                }
            }
        });
        this.tmpPositionArray = new float[20];
        this.layout = layout;
        this.shapedText = "";
        shapeText(layout);
    }

    public static void lerp(Paint paint, Paint paint2, float f, Paint paint3) {
        paint3.set(paint);
        paint3.setTextSize(MathUtils.lerp(paint.getTextSize(), paint2.getTextSize(), f));
        paint3.setColor(ColorUtils.blendARGB(paint.getColor(), paint2.getColor(), f));
        paint3.setStrokeWidth(MathUtils.lerp(paint.getStrokeWidth(), paint2.getStrokeWidth(), f));
    }

    public final void drawFontRun(Canvas canvas, Run run, FontRun fontRun, int i, Paint paint) {
        int i2;
        int i3;
        Font lerp = this.fontInterpolator.lerp(fontRun.baseFont, fontRun.targetFont, this.progress, this.linearProgress);
        AnimatableClockView$$ExternalSyntheticLambda3 animatableClockView$$ExternalSyntheticLambda3 = this.glyphFilter;
        float[] fArr = run.targetY;
        float[] fArr2 = run.baseY;
        float[] fArr3 = run.targetX;
        float[] fArr4 = run.baseX;
        int i4 = 0;
        int i5 = fontRun.start;
        int i6 = fontRun.end;
        if (animatableClockView$$ExternalSyntheticLambda3 == null) {
            while (i5 < i6) {
                int i7 = i4 + 1;
                this.tmpPositionArray[i4] = MathUtils.lerp(fArr4[i5], fArr3[i5], this.progress);
                i4 += 2;
                this.tmpPositionArray[i7] = MathUtils.lerp(fArr2[i5], fArr[i5], this.progress);
                i5++;
            }
            float[] fArr5 = this.tmpPositionArray;
            int i8 = fontRun.start;
            canvas.drawGlyphs(run.glyphIds, i8, fArr5, 0, i6 - i8, lerp, paint);
            return;
        }
        getTmpGlyph().getClass();
        getTmpGlyph().getClass();
        getTmpGlyph().getClass();
        getTmpGlyph().lineNo = i;
        Lazy lazy = this.tmpPaintForGlyph$delegate;
        Paint paint2 = paint;
        ((TextPaint) lazy.getValue()).set(paint2);
        int i9 = i5;
        int i10 = 0;
        while (i5 < i6) {
            getTmpGlyph().glyphIndex = i5;
            MutablePositionedGlyph tmpGlyph = getTmpGlyph();
            int i11 = run.glyphIds[i5];
            tmpGlyph.getClass();
            Lazy lazy2 = lazy;
            float[] fArr6 = fArr;
            getTmpGlyph().x = MathUtils.lerp(fArr4[i5], fArr3[i5], this.progress);
            getTmpGlyph().y = MathUtils.lerp(fArr2[i5], fArr6[i5], this.progress);
            getTmpGlyph().textSize = paint2.getTextSize();
            getTmpGlyph().color = paint2.getColor();
            animatableClockView$$ExternalSyntheticLambda3.invoke(getTmpGlyph(), Float.valueOf(this.progress));
            if (getTmpGlyph().textSize == paint2.getTextSize() && getTmpGlyph().color == paint2.getColor()) {
                i3 = i5;
                i2 = i6;
            } else {
                ((TextPaint) lazy2.getValue()).setTextSize(getTmpGlyph().textSize);
                ((TextPaint) lazy2.getValue()).setColor(getTmpGlyph().color);
                i2 = i6;
                i3 = i5;
                canvas.drawGlyphs(run.glyphIds, i9, this.tmpPositionArray, 0, i5 - i9, lerp, (TextPaint) lazy2.getValue());
                i9 = i3;
                i10 = 0;
            }
            int i12 = i10 + 1;
            this.tmpPositionArray[i10] = getTmpGlyph().x;
            i10 += 2;
            this.tmpPositionArray[i12] = getTmpGlyph().y;
            i5 = i3 + 1;
            paint2 = paint;
            i6 = i2;
            fArr = fArr6;
            lazy = lazy2;
        }
        canvas.drawGlyphs(run.glyphIds, i9, this.tmpPositionArray, 0, i6 - i9, lerp, (TextPaint) lazy.getValue());
    }

    public final MutablePositionedGlyph getTmpGlyph() {
        return (MutablePositionedGlyph) this.tmpGlyph$delegate.getValue();
    }

    public final void onTargetPaintModified() {
        updatePositionsAndFonts(shapeText(this.layout, this.targetPaint), false);
        TextInterpolatorListener textInterpolatorListener = this.listener;
        if (textInterpolatorListener != null) {
            textInterpolatorListener.onPaintModified();
        }
    }

    public final void rebase() {
        float f = this.progress;
        TextInterpolatorListener textInterpolatorListener = this.listener;
        if (f == 0.0f) {
            if (textInterpolatorListener != null) {
                textInterpolatorListener.onRebased();
                return;
            }
            return;
        }
        if (f == 1.0f) {
            this.basePaint.set(this.targetPaint);
        } else {
            lerp(this.basePaint, this.targetPaint, f, this.tmpPaint);
            this.basePaint.set(this.tmpPaint);
        }
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            for (Run run : ((Line) it.next()).runs) {
                int length = run.baseX.length;
                for (int i = 0; i < length; i++) {
                    float[] fArr = run.baseX;
                    fArr[i] = MathUtils.lerp(fArr[i], run.targetX[i], this.progress);
                    float[] fArr2 = run.baseY;
                    fArr2[i] = MathUtils.lerp(fArr2[i], run.targetY[i], this.progress);
                }
                for (FontRun fontRun : run.fontRuns) {
                    Font lerp = this.fontInterpolator.lerp(fontRun.baseFont, fontRun.targetFont, this.progress, this.linearProgress);
                    fontRun.baseFont = lerp;
                    this.basePaint.setTypeface(this.typefaceCache.getTypefaceForVariant(FontVariationAxis.toFontVariationSettings(lerp.getAxes())));
                }
            }
        }
        this.progress = 0.0f;
        this.linearProgress = 0.0f;
        if (textInterpolatorListener != null) {
            textInterpolatorListener.onRebased();
        }
    }

    public final void shapeText(Layout layout) {
        Iterator it;
        Iterator it2;
        float[] fArr;
        Iterator it3;
        Iterator it4;
        float[] fArr2;
        PositionedGlyphs positionedGlyphs;
        List shapeText = shapeText(layout, this.basePaint);
        List shapeText2 = shapeText(layout, this.targetPaint);
        ArrayList arrayList = (ArrayList) shapeText;
        ArrayList arrayList2 = (ArrayList) shapeText2;
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalArgumentException("The new layout result has different line count.");
        }
        Iterator it5 = arrayList.iterator();
        Iterator it6 = arrayList2.iterator();
        int i = 10;
        ArrayList arrayList3 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(shapeText, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(shapeText2, 10)));
        int i2 = 0;
        while (it5.hasNext() && it6.hasNext()) {
            Object next = it5.next();
            List list = (List) it6.next();
            List list2 = (List) next;
            Iterator it7 = list2.iterator();
            List list3 = list;
            Iterator it8 = list3.iterator();
            ArrayList arrayList4 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, i), CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, i)));
            while (it7.hasNext() && it8.hasNext()) {
                Object next2 = it7.next();
                PositionedGlyphs positionedGlyphs2 = (PositionedGlyphs) it8.next();
                PositionedGlyphs positionedGlyphs3 = (PositionedGlyphs) next2;
                if (positionedGlyphs3.glyphCount() != positionedGlyphs2.glyphCount()) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(this.lines.size(), "Inconsistent glyph count at line ").toString());
                }
                int glyphCount = positionedGlyphs3.glyphCount();
                int[] iArr = new int[glyphCount];
                for (int i3 = 0; i3 < glyphCount; i3++) {
                    int glyphId = positionedGlyphs3.getGlyphId(i3);
                    if (glyphId != positionedGlyphs2.getGlyphId(i3)) {
                        throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i3, this.lines.size(), "Inconsistent glyph ID at ", " in line ").toString());
                    }
                    Unit unit = Unit.INSTANCE;
                    iArr[i3] = glyphId;
                }
                float[] fArr3 = new float[glyphCount];
                for (int i4 = 0; i4 < glyphCount; i4++) {
                    fArr3[i4] = positionedGlyphs3.getGlyphX(i4);
                }
                float[] fArr4 = new float[glyphCount];
                for (int i5 = 0; i5 < glyphCount; i5++) {
                    fArr4[i5] = positionedGlyphs3.getGlyphY(i5);
                }
                float[] fArr5 = new float[glyphCount];
                for (int i6 = 0; i6 < glyphCount; i6++) {
                    fArr5[i6] = positionedGlyphs2.getGlyphX(i6);
                }
                float[] fArr6 = new float[glyphCount];
                for (int i7 = 0; i7 < glyphCount; i7++) {
                    fArr6[i7] = positionedGlyphs2.getGlyphY(i7);
                }
                ArrayList arrayList5 = new ArrayList();
                int i8 = i2;
                if (glyphCount != 0) {
                    Font font = positionedGlyphs3.getFont(0);
                    it = it5;
                    Font font2 = positionedGlyphs2.getFont(0);
                    FontInterpolator.Companion.getClass();
                    it2 = it6;
                    if (!FontInterpolator.Companion.canInterpolate(font, font2)) {
                        throw new IllegalArgumentException(("Cannot interpolate font at 0 (" + font + " vs " + font2 + ")").toString());
                    }
                    fArr = fArr4;
                    it3 = it7;
                    it4 = it8;
                    int i9 = 1;
                    int i10 = 0;
                    Font font3 = font2;
                    int i11 = i8;
                    while (i9 < glyphCount) {
                        float[] fArr7 = fArr5;
                        Font font4 = positionedGlyphs3.getFont(i9);
                        PositionedGlyphs positionedGlyphs4 = positionedGlyphs3;
                        Font font5 = positionedGlyphs2.getFont(i9);
                        if (font == font4) {
                            positionedGlyphs = positionedGlyphs2;
                            if (font3 != font5) {
                                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i9, "Base font is unchanged at ", " but target font has changed.").toString());
                            }
                        } else {
                            if (font3 == font5) {
                                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i9, "Base font has changed at ", " but target font is unchanged.").toString());
                            }
                            positionedGlyphs = positionedGlyphs2;
                            arrayList5.add(new FontRun(i10, i9, font, font3));
                            int max = Math.max(i11, i9 - i10);
                            FontInterpolator.Companion.getClass();
                            if (!FontInterpolator.Companion.canInterpolate(font4, font5)) {
                                throw new IllegalArgumentException(("Cannot interpolate font at " + i9 + " (" + font4 + " vs " + font5 + ")").toString());
                            }
                            i11 = max;
                            font3 = font5;
                            i10 = i9;
                            font = font4;
                        }
                        i9++;
                        fArr5 = fArr7;
                        positionedGlyphs3 = positionedGlyphs4;
                        positionedGlyphs2 = positionedGlyphs;
                    }
                    fArr2 = fArr5;
                    arrayList5.add(new FontRun(i10, glyphCount, font, font3));
                    i2 = Math.max(i11, glyphCount - i10);
                } else {
                    it = it5;
                    it2 = it6;
                    fArr = fArr4;
                    it3 = it7;
                    it4 = it8;
                    fArr2 = fArr5;
                }
                arrayList4.add(new Run(iArr, fArr3, fArr, fArr2, fArr6, arrayList5));
                it5 = it;
                it6 = it2;
                it7 = it3;
                it8 = it4;
            }
            arrayList3.add(new Line(arrayList4));
            i2 = i2;
            it5 = it5;
            it6 = it6;
            i = 10;
        }
        this.lines = arrayList3;
        int i12 = i2 * 2;
        if (this.tmpPositionArray.length < i12) {
            this.tmpPositionArray = new float[i12];
        }
    }

    public final void updatePositionsAndFonts(List list, boolean z) {
        if (((ArrayList) list).size() != this.lines.size()) {
            throw new IllegalStateException("The new layout result has different line count.");
        }
        List list2 = this.lines;
        Iterator it = list2.iterator();
        Iterator it2 = list.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            List list3 = (List) it2.next();
            List list4 = ((Line) next).runs;
            Iterator it3 = list4.iterator();
            List list5 = list3;
            Iterator it4 = list5.iterator();
            ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10)));
            while (it3.hasNext() && it4.hasNext()) {
                Object next2 = it3.next();
                PositionedGlyphs positionedGlyphs = (PositionedGlyphs) it4.next();
                Run run = (Run) next2;
                if (positionedGlyphs.glyphCount() != run.glyphIds.length) {
                    throw new IllegalArgumentException("The new layout has different glyph count.");
                }
                for (FontRun fontRun : run.fontRuns) {
                    Font font = positionedGlyphs.getFont(fontRun.start);
                    int i = fontRun.start;
                    for (int i2 = i; i2 < fontRun.end; i2++) {
                        if (positionedGlyphs.getGlyphId(i) != run.glyphIds[i]) {
                            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "The new layout has different glyph ID at ").toString());
                        }
                        if (font != positionedGlyphs.getFont(i2)) {
                            throw new IllegalArgumentException(("The new layout has different font run. " + font + " vs " + positionedGlyphs.getFont(i2) + " at " + i2).toString());
                        }
                    }
                    FontInterpolator.Companion companion = FontInterpolator.Companion;
                    Font font2 = fontRun.baseFont;
                    companion.getClass();
                    if (!FontInterpolator.Companion.canInterpolate(font, font2)) {
                        throw new IllegalArgumentException(("New font cannot be interpolated with existing font. " + font + ", " + fontRun.baseFont).toString());
                    }
                    if (z) {
                        fontRun.baseFont = font;
                    } else {
                        fontRun.targetFont = font;
                    }
                }
                int i3 = 0;
                float[] fArr = run.baseX;
                if (z) {
                    int length = fArr.length;
                    while (i3 < length) {
                        fArr[i3] = positionedGlyphs.getGlyphX(i3);
                        run.baseY[i3] = positionedGlyphs.getGlyphY(i3);
                        i3++;
                    }
                } else {
                    int length2 = fArr.length;
                    while (i3 < length2) {
                        run.targetX[i3] = positionedGlyphs.getGlyphX(i3);
                        run.targetY[i3] = positionedGlyphs.getGlyphY(i3);
                        i3++;
                    }
                }
                arrayList2.add(Unit.INSTANCE);
            }
            arrayList.add(arrayList2);
        }
    }

    public /* synthetic */ TextInterpolator(Layout layout, TypefaceVariantCache typefaceVariantCache, TextInterpolatorListener textInterpolatorListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(layout, typefaceVariantCache, (i & 4) != 0 ? null : textInterpolatorListener);
    }

    public final List shapeText(Layout layout, TextPaint textPaint) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        int lineCount = layout.getLineCount();
        int i = 0;
        while (i < lineCount) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            int i2 = lineEnd - lineStart;
            int i3 = (lineStart + i2) - 1;
            if (i3 > lineStart && i3 < layout.getText().length() && layout.getText().charAt(i3) == '\n') {
                i2--;
            }
            final ArrayList arrayList2 = new ArrayList();
            TextPaint textPaint2 = textPaint;
            TextShaper.shapeText(layout.getText(), lineStart, i2, layout.getTextDirectionHeuristic(), textPaint2, new TextShaper.GlyphsConsumer() { // from class: com.android.systemui.animation.TextInterpolator$shapeText$3
                @Override // android.text.TextShaper.GlyphsConsumer
                public final void accept(int i4, int i5, PositionedGlyphs positionedGlyphs, TextPaint textPaint3) {
                    arrayList2.add(positionedGlyphs);
                }
            });
            arrayList.add(arrayList2);
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(layout.getText().subSequence(lineStart, lineEnd).toString());
            i++;
            textPaint = textPaint2;
        }
        this.shapedText = sb.toString();
        return arrayList;
    }
}
