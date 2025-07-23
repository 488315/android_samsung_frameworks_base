package androidx.compose.ui.text;

import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Layout;
import android.text.TextUtils;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.BrushKt$ShaderBrush$1;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.android.LayoutHelper;
import androidx.compose.ui.text.android.TextAndroidCanvas;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.android.TextLayout_androidKt;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.platform.AndroidMultiParagraphDraw_androidKt;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MultiParagraph {
    public final boolean didExceedMaxLines;
    public final float height;
    public final MultiParagraphIntrinsics intrinsics;
    public final int lineCount;
    public final int maxLines;
    public final List paragraphInfoList;
    public final List placeholderRects;
    public final float width;

    public /* synthetic */ MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List list, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, j, density, resolver, (List<AnnotatedString.Range<Placeholder>>) list, i, i2);
    }

    /* renamed from: paint-LG529CI$default, reason: not valid java name */
    public static void m731paintLG529CI$default(MultiParagraph multiParagraph, Canvas canvas, long j, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle) {
        DrawScope.Companion.getClass();
        int i = DrawScope.Companion.DefaultBlendMode;
        multiParagraph.getClass();
        canvas.save();
        ArrayList arrayList = (ArrayList) multiParagraph.paragraphInfoList;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
            ((AndroidParagraph) paragraphInfo.paragraph).m727paintLG529CI(canvas, j, shadow, textDecoration, drawStyle, i);
            canvas.translate(0.0f, ((AndroidParagraph) paragraphInfo.paragraph).getHeight());
        }
        canvas.restore();
    }

    /* renamed from: paint-hn5TExg$default, reason: not valid java name */
    public static void m732painthn5TExg$default(MultiParagraph multiParagraph, Canvas canvas, Brush brush, float f, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle) {
        DrawScope.Companion.getClass();
        int i = DrawScope.Companion.DefaultBlendMode;
        multiParagraph.getClass();
        canvas.save();
        if (((ArrayList) multiParagraph.paragraphInfoList).size() <= 1) {
            AndroidMultiParagraphDraw_androidKt.m780drawParagraphs7AXcY_I(multiParagraph, canvas, brush, f, shadow, textDecoration, drawStyle, i);
        } else if (brush instanceof SolidColor) {
            AndroidMultiParagraphDraw_androidKt.m780drawParagraphs7AXcY_I(multiParagraph, canvas, brush, f, shadow, textDecoration, drawStyle, i);
        } else if (brush instanceof ShaderBrush) {
            ArrayList arrayList = (ArrayList) multiParagraph.paragraphInfoList;
            int size = arrayList.size();
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (int i2 = 0; i2 < size; i2++) {
                ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
                f3 += ((AndroidParagraph) paragraphInfo.paragraph).getHeight();
                f2 = Math.max(f2, ((AndroidParagraph) paragraphInfo.paragraph).getWidth());
            }
            Size.Companion companion = Size.Companion;
            Shader mo451createShaderuvyYCjk = ((ShaderBrush) brush).mo451createShaderuvyYCjk((Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f3) & 4294967295L));
            Matrix matrix = new Matrix();
            mo451createShaderuvyYCjk.getLocalMatrix(matrix);
            ArrayList arrayList2 = (ArrayList) multiParagraph.paragraphInfoList;
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ParagraphInfo paragraphInfo2 = (ParagraphInfo) arrayList2.get(i3);
                ((AndroidParagraph) paragraphInfo2.paragraph).m728painthn5TExg(canvas, new BrushKt$ShaderBrush$1(mo451createShaderuvyYCjk), f, shadow, textDecoration, drawStyle, i);
                AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo2.paragraph;
                canvas.translate(0.0f, androidParagraph.getHeight());
                matrix.setTranslate(0.0f, -androidParagraph.getHeight());
                mo451createShaderuvyYCjk.setLocalMatrix(matrix);
            }
        }
        canvas.restore();
    }

    /* renamed from: fillBoundingBoxes-8ffj60Q, reason: not valid java name */
    public final void m733fillBoundingBoxes8ffj60Q(final long j, final float[] fArr) {
        requireIndexInRange(TextRange.m750getMinimpl(j));
        requireIndexInRangeInclusiveEnd(TextRange.m749getMaximpl(j));
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = 0;
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        MultiParagraphKt.m736findParagraphsByRangeSbBc2M(this.paragraphInfoList, j, new Function1() { // from class: androidx.compose.ui.text.MultiParagraph$fillBoundingBoxes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ParagraphInfo paragraphInfo = (ParagraphInfo) obj;
                long j2 = j;
                float[] fArr2 = fArr;
                Ref$IntRef ref$IntRef2 = ref$IntRef;
                Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
                int m750getMinimpl = paragraphInfo.startIndex > TextRange.m750getMinimpl(j2) ? paragraphInfo.startIndex : TextRange.m750getMinimpl(j2);
                int m749getMaximpl = TextRange.m749getMaximpl(j2);
                int i = paragraphInfo.endIndex;
                if (i >= m749getMaximpl) {
                    i = TextRange.m749getMaximpl(j2);
                }
                long TextRange = TextRangeKt.TextRange(paragraphInfo.toLocalIndex(m750getMinimpl), paragraphInfo.toLocalIndex(i));
                int i2 = ref$IntRef2.element;
                AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo.paragraph;
                androidParagraph.getClass();
                androidParagraph.layout.fillBoundingBoxes(TextRange.m750getMinimpl(TextRange), TextRange.m749getMaximpl(TextRange), i2, fArr2);
                int m748getLengthimpl = (TextRange.m748getLengthimpl(TextRange) * 4) + ref$IntRef2.element;
                for (int i3 = ref$IntRef2.element; i3 < m748getLengthimpl; i3 += 4) {
                    int i4 = i3 + 1;
                    float f = fArr2[i4];
                    float f2 = ref$FloatRef2.element;
                    fArr2[i4] = f + f2;
                    int i5 = i3 + 3;
                    fArr2[i5] = fArr2[i5] + f2;
                }
                ref$IntRef2.element = m748getLengthimpl;
                ref$FloatRef2.element = androidParagraph.getHeight() + ref$FloatRef2.element;
                return Unit.INSTANCE;
            }
        });
    }

    public final float getLineBottom(int i) {
        requireLineIndexInRange(i);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByLineIndex(i, this.paragraphInfoList));
        Paragraph paragraph = paragraphInfo.paragraph;
        return ((AndroidParagraph) paragraph).layout.getLineBottom(i - paragraphInfo.startLineIndex) + paragraphInfo.top;
    }

    public final int getLineEnd(int i, boolean z) {
        int lineEnd;
        requireLineIndexInRange(i);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByLineIndex(i, this.paragraphInfoList));
        Paragraph paragraph = paragraphInfo.paragraph;
        int i2 = i - paragraphInfo.startLineIndex;
        TextLayout textLayout = ((AndroidParagraph) paragraph).layout;
        if (z) {
            Layout layout = textLayout.layout;
            TextAndroidCanvas textAndroidCanvas = TextLayout_androidKt.SharedTextAndroidCanvas;
            if (layout.getEllipsisCount(i2) <= 0 || textLayout.ellipsize != TextUtils.TruncateAt.END) {
                LayoutHelper layoutHelper = textLayout.getLayoutHelper();
                lineEnd = layoutHelper.lineEndToVisibleEnd(layoutHelper.layout.getLineEnd(i2), layoutHelper.layout.getLineStart(i2));
            } else {
                lineEnd = textLayout.layout.getEllipsisStart(i2) + textLayout.layout.getLineStart(i2);
            }
        } else {
            lineEnd = textLayout.getLineEnd(i2);
        }
        return lineEnd + paragraphInfo.startIndex;
    }

    public final int getLineForOffset(int i) {
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(i >= this.intrinsics.annotatedString.text.length() ? CollectionsKt__CollectionsKt.getLastIndex(this.paragraphInfoList) : i < 0 ? 0 : MultiParagraphKt.findParagraphByIndex(i, this.paragraphInfoList));
        return ((AndroidParagraph) paragraphInfo.paragraph).layout.layout.getLineForOffset(paragraphInfo.toLocalIndex(i)) + paragraphInfo.startLineIndex;
    }

    public final int getLineForVerticalPosition(float f) {
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByY(this.paragraphInfoList, f));
        int i = paragraphInfo.endIndex - paragraphInfo.startIndex;
        int i2 = paragraphInfo.startLineIndex;
        if (i == 0) {
            return i2;
        }
        float f2 = f - paragraphInfo.top;
        TextLayout textLayout = ((AndroidParagraph) paragraphInfo.paragraph).layout;
        return textLayout.layout.getLineForVertical(((int) f2) - textLayout.topPadding) + i2;
    }

    public final float getLineTop(int i) {
        requireLineIndexInRange(i);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByLineIndex(i, this.paragraphInfoList));
        Paragraph paragraph = paragraphInfo.paragraph;
        return ((AndroidParagraph) paragraph).layout.getLineTop(i - paragraphInfo.startLineIndex) + paragraphInfo.top;
    }

    /* renamed from: getOffsetForPosition-k-4lQ0M, reason: not valid java name */
    public final int m734getOffsetForPositionk4lQ0M(long j) {
        int i = (int) (j & 4294967295L);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByY(this.paragraphInfoList, Float.intBitsToFloat(i)));
        int i2 = paragraphInfo.endIndex;
        int i3 = paragraphInfo.startIndex;
        if (i2 - i3 == 0) {
            return i3;
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat(i) - paragraphInfo.top;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo.paragraph;
        androidParagraph.getClass();
        int intBitsToFloat3 = (int) Float.intBitsToFloat((int) (4294967295L & floatToRawIntBits));
        TextLayout textLayout = androidParagraph.layout;
        int lineForVertical = textLayout.layout.getLineForVertical(intBitsToFloat3 - textLayout.topPadding);
        return textLayout.layout.getOffsetForHorizontal(lineForVertical, (textLayout.getHorizontalPadding(lineForVertical) * (-1)) + Float.intBitsToFloat((int) (floatToRawIntBits >> 32))) + i3;
    }

    /* renamed from: getRangeForRect-8-6BmAI, reason: not valid java name */
    public final long m735getRangeForRect86BmAI(Rect rect, int i, TextInclusionStrategy textInclusionStrategy) {
        long j;
        long j2;
        int findParagraphByY = MultiParagraphKt.findParagraphByY(this.paragraphInfoList, rect.top);
        float f = ((ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(findParagraphByY)).bottom;
        float f2 = rect.bottom;
        if (f >= f2 || findParagraphByY == CollectionsKt__CollectionsKt.getLastIndex(this.paragraphInfoList)) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(findParagraphByY);
            return paragraphInfo.m737toGlobalxdX6G0(((AndroidParagraph) paragraphInfo.paragraph).m726getRangeForRect86BmAI(paragraphInfo.toLocal(rect), i, textInclusionStrategy), true);
        }
        int findParagraphByY2 = MultiParagraphKt.findParagraphByY(this.paragraphInfoList, f2);
        TextRange.Companion.getClass();
        long j3 = TextRange.Zero;
        while (true) {
            TextRange.Companion.getClass();
            j = TextRange.Zero;
            if (!TextRange.m746equalsimpl0(j3, j) || findParagraphByY > findParagraphByY2) {
                break;
            }
            ParagraphInfo paragraphInfo2 = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(findParagraphByY);
            j3 = paragraphInfo2.m737toGlobalxdX6G0(((AndroidParagraph) paragraphInfo2.paragraph).m726getRangeForRect86BmAI(paragraphInfo2.toLocal(rect), i, textInclusionStrategy), true);
            findParagraphByY++;
        }
        if (TextRange.m746equalsimpl0(j3, j)) {
            return j;
        }
        while (true) {
            TextRange.Companion.getClass();
            j2 = TextRange.Zero;
            if (!TextRange.m746equalsimpl0(j, j2) || findParagraphByY > findParagraphByY2) {
                break;
            }
            ParagraphInfo paragraphInfo3 = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(findParagraphByY2);
            j = paragraphInfo3.m737toGlobalxdX6G0(((AndroidParagraph) paragraphInfo3.paragraph).m726getRangeForRect86BmAI(paragraphInfo3.toLocal(rect), i, textInclusionStrategy), true);
            findParagraphByY2--;
        }
        return TextRange.m746equalsimpl0(j, j2) ? j3 : TextRangeKt.TextRange((int) (j3 >> 32), (int) (4294967295L & j));
    }

    public final void requireIndexInRange(int i) {
        boolean z = false;
        MultiParagraphIntrinsics multiParagraphIntrinsics = this.intrinsics;
        if (i >= 0 && i < multiParagraphIntrinsics.annotatedString.text.length()) {
            z = true;
        }
        if (z) {
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "offset(", ") is out of bounds [0, ");
        m.append(multiParagraphIntrinsics.annotatedString.text.length());
        m.append(')');
        InlineClassHelperKt.throwIllegalArgumentException(m.toString());
    }

    public final void requireIndexInRangeInclusiveEnd(int i) {
        boolean z = false;
        MultiParagraphIntrinsics multiParagraphIntrinsics = this.intrinsics;
        if (i >= 0 && i <= multiParagraphIntrinsics.annotatedString.text.length()) {
            z = true;
        }
        if (z) {
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "offset(", ") is out of bounds [0, ");
        m.append(multiParagraphIntrinsics.annotatedString.text.length());
        m.append(']');
        InlineClassHelperKt.throwIllegalArgumentException(m.toString());
    }

    public final void requireLineIndexInRange(int i) {
        boolean z = false;
        int i2 = this.lineCount;
        if (i >= 0 && i < i2) {
            z = true;
        }
        if (z) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("lineIndex(" + i + ") is out of bounds [0, " + i2 + ')');
    }

    public /* synthetic */ MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List list, int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, j, density, resolver, (List<AnnotatedString.Range<Placeholder>>) list, i, z);
    }

    public /* synthetic */ MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(multiParagraphIntrinsics, j, i, i2);
    }

    public /* synthetic */ MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(multiParagraphIntrinsics, j, i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.util.List] */
    private MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, int i2) {
        boolean z;
        int m820getMaxHeightimpl;
        this.intrinsics = multiParagraphIntrinsics;
        this.maxLines = i;
        if (Constraints.m823getMinWidthimpl(j) != 0 || Constraints.m822getMinHeightimpl(j) != 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Setting Constraints.minWidth and Constraints.minHeight is not supported, these should be the default zero values instead.");
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) multiParagraphIntrinsics.infoList;
        int size = arrayList2.size();
        int i3 = 0;
        float f = 0.0f;
        int i4 = 0;
        while (i4 < size) {
            ParagraphIntrinsicInfo paragraphIntrinsicInfo = (ParagraphIntrinsicInfo) arrayList2.get(i4);
            ParagraphIntrinsics paragraphIntrinsics = paragraphIntrinsicInfo.intrinsics;
            int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
            if (Constraints.m816getHasBoundedHeightimpl(j)) {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j) - ParagraphKt.ceilToInt(f);
                if (m820getMaxHeightimpl < 0) {
                    m820getMaxHeightimpl = 0;
                }
            } else {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
            }
            AndroidParagraph androidParagraph = new AndroidParagraph((AndroidParagraphIntrinsics) paragraphIntrinsics, this.maxLines - i3, i2, ConstraintsKt.Constraints$default(0, m821getMaxWidthimpl, 0, m820getMaxHeightimpl, 5), null);
            float height = androidParagraph.getHeight() + f;
            TextLayout textLayout = androidParagraph.layout;
            int i5 = i3 + textLayout.lineCount;
            arrayList.add(new ParagraphInfo(androidParagraph, paragraphIntrinsicInfo.startIndex, paragraphIntrinsicInfo.endIndex, i3, i5, f, height));
            if (textLayout.didExceedMaxLines || (i5 == this.maxLines && i4 != CollectionsKt__CollectionsKt.getLastIndex(this.intrinsics.infoList))) {
                z = true;
                i3 = i5;
                f = height;
                break;
            } else {
                i4++;
                i3 = i5;
                f = height;
            }
        }
        z = false;
        this.height = f;
        this.lineCount = i3;
        this.didExceedMaxLines = z;
        this.paragraphInfoList = arrayList;
        this.width = Constraints.m821getMaxWidthimpl(j);
        ArrayList arrayList3 = new ArrayList(arrayList.size());
        int size2 = arrayList.size();
        for (int i6 = 0; i6 < size2; i6++) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i6);
            List list = ((AndroidParagraph) paragraphInfo.paragraph).placeholderRects;
            ArrayList arrayList4 = new ArrayList(list.size());
            int size3 = list.size();
            for (int i7 = 0; i7 < size3; i7++) {
                Rect rect = (Rect) list.get(i7);
                arrayList4.add(rect != null ? paragraphInfo.toGlobal(rect) : null);
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList4, arrayList3);
        }
        int size4 = arrayList3.size();
        ArrayList arrayList5 = arrayList3;
        if (size4 < this.intrinsics.placeholders.size()) {
            int size5 = this.intrinsics.placeholders.size() - arrayList3.size();
            ArrayList arrayList6 = new ArrayList(size5);
            for (int i8 = 0; i8 < size5; i8++) {
                arrayList6.add(null);
            }
            arrayList5 = CollectionsKt___CollectionsKt.plus((Iterable) arrayList6, (Collection) arrayList3);
        }
        this.placeholderRects = arrayList5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraph(androidx.compose.ui.text.MultiParagraphIntrinsics r8, long r9, int r11, int r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 4
            if (r14 == 0) goto L7
            r11 = 2147483647(0x7fffffff, float:NaN)
        L7:
            r4 = r11
            r11 = r13 & 8
            if (r11 == 0) goto L13
            androidx.compose.ui.text.style.TextOverflow$Companion r11 = androidx.compose.ui.text.style.TextOverflow.Companion
            r11.getClass()
            int r12 = androidx.compose.ui.text.style.TextOverflow.Clip
        L13:
            r5 = r12
            r6 = 0
            r0 = r7
            r1 = r8
            r2 = r9
            r0.<init>(r1, r2, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.MultiParagraphIntrinsics, long, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, float f, Density density, FontFamily.Resolver resolver, List list, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, f, density, resolver, (List<AnnotatedString.Range<Placeholder>>) ((i2 & 32) != 0 ? EmptyList.INSTANCE : list), (i2 & 64) != 0 ? Integer.MAX_VALUE : i, (i2 & 128) != 0 ? false : z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraph(androidx.compose.ui.text.AnnotatedString r14, androidx.compose.ui.text.TextStyle r15, long r16, androidx.compose.ui.unit.Density r18, androidx.compose.ui.text.font.FontFamily.Resolver r19, java.util.List r20, int r21, int r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r13 = this;
            r0 = r23
            r1 = r0 & 32
            if (r1 == 0) goto La
            kotlin.collections.EmptyList r1 = kotlin.collections.EmptyList.INSTANCE
            r9 = r1
            goto Lc
        La:
            r9 = r20
        Lc:
            r1 = r0 & 64
            if (r1 == 0) goto L15
            r1 = 2147483647(0x7fffffff, float:NaN)
            r10 = r1
            goto L17
        L15:
            r10 = r21
        L17:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L24
            androidx.compose.ui.text.style.TextOverflow$Companion r0 = androidx.compose.ui.text.style.TextOverflow.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextOverflow.Clip
            r11 = r0
            goto L26
        L24:
            r11 = r22
        L26:
            r12 = 0
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r7 = r18
            r8 = r19
            r2.<init>(r3, r4, r5, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, long, androidx.compose.ui.unit.Density, androidx.compose.ui.text.font.FontFamily$Resolver, java.util.List, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List list, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, j, density, resolver, (i2 & 32) != 0 ? EmptyList.INSTANCE : list, (i2 & 64) != 0 ? Integer.MAX_VALUE : i, (i2 & 128) != 0 ? false : z, (DefaultConstructorMarker) null);
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, List list, int i, boolean z, float f, Density density, Font.ResourceLoader resourceLoader, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, (List<AnnotatedString.Range<Placeholder>>) ((i2 & 4) != 0 ? EmptyList.INSTANCE : list), (i2 & 8) != 0 ? Integer.MAX_VALUE : i, (i2 & 16) != 0 ? false : z, f, density, resourceLoader);
    }

    public /* synthetic */ MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(multiParagraphIntrinsics, j, (i2 & 4) != 0 ? Integer.MAX_VALUE : i, (i2 & 8) != 0 ? false : z, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private MultiParagraph(androidx.compose.ui.text.MultiParagraphIntrinsics r8, long r9, int r11, boolean r12) {
        /*
            r7 = this;
            if (r12 == 0) goto Lb
            androidx.compose.ui.text.style.TextOverflow$Companion r12 = androidx.compose.ui.text.style.TextOverflow.Companion
            r12.getClass()
            int r12 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        L9:
            r5 = r12
            goto L13
        Lb:
            androidx.compose.ui.text.style.TextOverflow$Companion r12 = androidx.compose.ui.text.style.TextOverflow.Companion
            r12.getClass()
            int r12 = androidx.compose.ui.text.style.TextOverflow.Clip
            goto L9
        L13:
            r6 = 0
            r0 = r7
            r1 = r8
            r2 = r9
            r4 = r11
            r0.<init>(r1, r2, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.MultiParagraphIntrinsics, long, int, boolean):void");
    }

    public /* synthetic */ MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, int i, boolean z, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(multiParagraphIntrinsics, (i2 & 2) != 0 ? Integer.MAX_VALUE : i, (i2 & 4) != 0 ? false : z, f);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraph(androidx.compose.ui.text.MultiParagraphIntrinsics r10, int r11, boolean r12, float r13) {
        /*
            r9 = this;
            int r13 = androidx.compose.ui.text.ParagraphKt.ceilToInt(r13)
            r0 = 13
            r1 = 0
            long r4 = androidx.compose.ui.unit.ConstraintsKt.Constraints$default(r1, r13, r1, r1, r0)
            if (r12 == 0) goto L16
            androidx.compose.ui.text.style.TextOverflow$Companion r12 = androidx.compose.ui.text.style.TextOverflow.Companion
            r12.getClass()
            int r12 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        L14:
            r7 = r12
            goto L1e
        L16:
            androidx.compose.ui.text.style.TextOverflow$Companion r12 = androidx.compose.ui.text.style.TextOverflow.Companion
            r12.getClass()
            int r12 = androidx.compose.ui.text.style.TextOverflow.Clip
            goto L14
        L1e:
            r8 = 0
            r2 = r9
            r3 = r10
            r6 = r11
            r2.<init>(r3, r4, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.MultiParagraphIntrinsics, int, boolean, float):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraph(androidx.compose.ui.text.AnnotatedString r7, androidx.compose.ui.text.TextStyle r8, java.util.List<androidx.compose.ui.text.AnnotatedString.Range<androidx.compose.ui.text.Placeholder>> r9, int r10, boolean r11, float r12, androidx.compose.ui.unit.Density r13, androidx.compose.ui.text.font.Font.ResourceLoader r14) {
        /*
            r6 = this;
            androidx.compose.ui.text.MultiParagraphIntrinsics r0 = new androidx.compose.ui.text.MultiParagraphIntrinsics
            androidx.compose.ui.text.font.FontFamilyResolverImpl r5 = androidx.compose.ui.text.font.DelegatingFontLoaderForDeprecatedUsage_androidKt.createFontFamilyResolver(r14)
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r13
            r0.<init>(r1, r2, r3, r4, r5)
            r7 = r0
            androidx.compose.ui.text.style.TextOverflow$Companion r8 = androidx.compose.ui.text.style.TextOverflow.Companion
            r8.getClass()
            if (r11 == 0) goto L19
            int r8 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        L17:
            r11 = r8
            goto L1c
        L19:
            int r8 = androidx.compose.ui.text.style.TextOverflow.Clip
            goto L17
        L1c:
            int r8 = androidx.compose.ui.text.ParagraphKt.ceilToInt(r12)
            r9 = 13
            r12 = 0
            long r8 = androidx.compose.ui.unit.ConstraintsKt.Constraints$default(r12, r8, r12, r12, r9)
            r12 = 0
            r6.<init>(r7, r8, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, java.util.List, int, boolean, float, androidx.compose.ui.unit.Density, androidx.compose.ui.text.font.Font$ResourceLoader):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraph(androidx.compose.ui.text.AnnotatedString r7, androidx.compose.ui.text.TextStyle r8, float r9, androidx.compose.ui.unit.Density r10, androidx.compose.ui.text.font.FontFamily.Resolver r11, java.util.List<androidx.compose.ui.text.AnnotatedString.Range<androidx.compose.ui.text.Placeholder>> r12, int r13, boolean r14) {
        /*
            r6 = this;
            androidx.compose.ui.text.MultiParagraphIntrinsics r0 = new androidx.compose.ui.text.MultiParagraphIntrinsics
            r1 = r7
            r2 = r8
            r4 = r10
            r5 = r11
            r3 = r12
            r0.<init>(r1, r2, r3, r4, r5)
            r7 = r0
            androidx.compose.ui.text.style.TextOverflow$Companion r8 = androidx.compose.ui.text.style.TextOverflow.Companion
            r8.getClass()
            if (r14 == 0) goto L16
            int r8 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        L14:
            r11 = r8
            goto L19
        L16:
            int r8 = androidx.compose.ui.text.style.TextOverflow.Clip
            goto L14
        L19:
            int r8 = androidx.compose.ui.text.ParagraphKt.ceilToInt(r9)
            r9 = 13
            r10 = 0
            long r8 = androidx.compose.ui.unit.ConstraintsKt.Constraints$default(r10, r8, r10, r10, r9)
            r12 = 0
            r10 = r13
            r6.<init>(r7, r8, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, float, androidx.compose.ui.unit.Density, androidx.compose.ui.text.font.FontFamily$Resolver, java.util.List, int, boolean):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private MultiParagraph(androidx.compose.ui.text.AnnotatedString r7, androidx.compose.ui.text.TextStyle r8, long r9, androidx.compose.ui.unit.Density r11, androidx.compose.ui.text.font.FontFamily.Resolver r12, java.util.List<androidx.compose.ui.text.AnnotatedString.Range<androidx.compose.ui.text.Placeholder>> r13, int r14, boolean r15) {
        /*
            r6 = this;
            androidx.compose.ui.text.MultiParagraphIntrinsics r0 = new androidx.compose.ui.text.MultiParagraphIntrinsics
            r1 = r7
            r2 = r8
            r4 = r11
            r5 = r12
            r3 = r13
            r0.<init>(r1, r2, r3, r4, r5)
            r7 = r0
            androidx.compose.ui.text.style.TextOverflow$Companion r8 = androidx.compose.ui.text.style.TextOverflow.Companion
            r8.getClass()
            if (r15 == 0) goto L16
            int r8 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        L14:
            r11 = r8
            goto L19
        L16:
            int r8 = androidx.compose.ui.text.style.TextOverflow.Clip
            goto L14
        L19:
            r12 = 0
            r8 = r9
            r10 = r14
            r6.<init>(r7, r8, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.MultiParagraph.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, long, androidx.compose.ui.unit.Density, androidx.compose.ui.text.font.FontFamily$Resolver, java.util.List, int, boolean):void");
    }

    private MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List<AnnotatedString.Range<Placeholder>> list, int i, int i2) {
        this(new MultiParagraphIntrinsics(annotatedString, textStyle, list, density, resolver), j, i, i2, (DefaultConstructorMarker) null);
    }
}
