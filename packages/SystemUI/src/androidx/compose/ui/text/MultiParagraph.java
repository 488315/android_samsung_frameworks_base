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
import androidx.compose.ui.text.font.DelegatingFontLoaderForDeprecatedUsage_androidKt;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.platform.AndroidMultiParagraphDraw_androidKt;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
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
    public static void m733paintLG529CI$default(MultiParagraph multiParagraph, Canvas canvas, long j, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle) {
        DrawScope.Companion.getClass();
        int i = DrawScope.Companion.DefaultBlendMode;
        multiParagraph.getClass();
        canvas.save();
        ArrayList arrayList = (ArrayList) multiParagraph.paragraphInfoList;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
            ((AndroidParagraph) paragraphInfo.paragraph).m729paintLG529CI(canvas, j, shadow, textDecoration, drawStyle, i);
            canvas.translate(0.0f, ((AndroidParagraph) paragraphInfo.paragraph).getHeight());
        }
        canvas.restore();
    }

    /* renamed from: paint-hn5TExg$default, reason: not valid java name */
    public static void m734painthn5TExg$default(MultiParagraph multiParagraph, Canvas canvas, Brush brush, float f, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle) {
        DrawScope.Companion.getClass();
        int i = DrawScope.Companion.DefaultBlendMode;
        multiParagraph.getClass();
        canvas.save();
        if (((ArrayList) multiParagraph.paragraphInfoList).size() <= 1 || (brush instanceof SolidColor)) {
            AndroidMultiParagraphDraw_androidKt.m782drawParagraphs7AXcY_I(multiParagraph, canvas, brush, f, shadow, textDecoration, drawStyle, i);
        } else if (brush instanceof ShaderBrush) {
            ArrayList arrayList = (ArrayList) multiParagraph.paragraphInfoList;
            int size = arrayList.size();
            float fMax = 0.0f;
            float height = 0.0f;
            for (int i2 = 0; i2 < size; i2++) {
                ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
                height += ((AndroidParagraph) paragraphInfo.paragraph).getHeight();
                fMax = Math.max(fMax, ((AndroidParagraph) paragraphInfo.paragraph).getWidth());
            }
            Size.Companion companion = Size.Companion;
            Shader shaderMo453createShaderuvyYCjk = ((ShaderBrush) brush).mo453createShaderuvyYCjk((Float.floatToRawIntBits(fMax) << 32) | (Float.floatToRawIntBits(height) & 4294967295L));
            Matrix matrix = new Matrix();
            shaderMo453createShaderuvyYCjk.getLocalMatrix(matrix);
            ArrayList arrayList2 = (ArrayList) multiParagraph.paragraphInfoList;
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ParagraphInfo paragraphInfo2 = (ParagraphInfo) arrayList2.get(i3);
                ((AndroidParagraph) paragraphInfo2.paragraph).m730painthn5TExg(canvas, new BrushKt$ShaderBrush$1(shaderMo453createShaderuvyYCjk), f, shadow, textDecoration, drawStyle, i);
                AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo2.paragraph;
                canvas.translate(0.0f, androidParagraph.getHeight());
                matrix.setTranslate(0.0f, -androidParagraph.getHeight());
                shaderMo453createShaderuvyYCjk.setLocalMatrix(matrix);
            }
        }
        canvas.restore();
    }

    /* renamed from: fillBoundingBoxes-8ffj60Q, reason: not valid java name */
    public final void m735fillBoundingBoxes8ffj60Q(final long j, final float[] fArr) {
        requireIndexInRange(TextRange.m752getMinimpl(j));
        requireIndexInRangeInclusiveEnd(TextRange.m751getMaximpl(j));
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = 0;
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        MultiParagraphKt.m738findParagraphsByRangeSbBc2M(this.paragraphInfoList, j, new Function1() { // from class: androidx.compose.ui.text.MultiParagraph$fillBoundingBoxes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ParagraphInfo paragraphInfo = (ParagraphInfo) obj;
                long j2 = j;
                float[] fArr2 = fArr;
                Ref$IntRef ref$IntRef2 = ref$IntRef;
                Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
                int iM752getMinimpl = paragraphInfo.startIndex > TextRange.m752getMinimpl(j2) ? paragraphInfo.startIndex : TextRange.m752getMinimpl(j2);
                int iM751getMaximpl = TextRange.m751getMaximpl(j2);
                int iM751getMaximpl2 = paragraphInfo.endIndex;
                if (iM751getMaximpl2 >= iM751getMaximpl) {
                    iM751getMaximpl2 = TextRange.m751getMaximpl(j2);
                }
                long jTextRange = TextRangeKt.TextRange(paragraphInfo.toLocalIndex(iM752getMinimpl), paragraphInfo.toLocalIndex(iM751getMaximpl2));
                int i = ref$IntRef2.element;
                AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo.paragraph;
                androidParagraph.getClass();
                androidParagraph.layout.fillBoundingBoxes(TextRange.m752getMinimpl(jTextRange), TextRange.m751getMaximpl(jTextRange), i, fArr2);
                int iM750getLengthimpl = (TextRange.m750getLengthimpl(jTextRange) * 4) + ref$IntRef2.element;
                for (int i2 = ref$IntRef2.element; i2 < iM750getLengthimpl; i2 += 4) {
                    int i3 = i2 + 1;
                    float f = fArr2[i3];
                    float f2 = ref$FloatRef2.element;
                    fArr2[i3] = f + f2;
                    int i4 = i2 + 3;
                    fArr2[i4] = fArr2[i4] + f2;
                }
                ref$IntRef2.element = iM750getLengthimpl;
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
    public final int m736getOffsetForPositionk4lQ0M(long j) {
        int i = (int) (j & 4294967295L);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(MultiParagraphKt.findParagraphByY(this.paragraphInfoList, Float.intBitsToFloat(i)));
        int i2 = paragraphInfo.endIndex;
        int i3 = paragraphInfo.startIndex;
        if (i2 - i3 == 0) {
            return i3;
        }
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat(i) - paragraphInfo.top;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        AndroidParagraph androidParagraph = (AndroidParagraph) paragraphInfo.paragraph;
        androidParagraph.getClass();
        int iIntBitsToFloat = (int) Float.intBitsToFloat((int) (4294967295L & jFloatToRawIntBits));
        TextLayout textLayout = androidParagraph.layout;
        int lineForVertical = textLayout.layout.getLineForVertical(iIntBitsToFloat - textLayout.topPadding);
        return textLayout.layout.getOffsetForHorizontal(lineForVertical, (textLayout.getHorizontalPadding(lineForVertical) * (-1)) + Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))) + i3;
    }

    /* renamed from: getRangeForRect-8-6BmAI, reason: not valid java name */
    public final long m737getRangeForRect86BmAI(Rect rect, int i, TextInclusionStrategy textInclusionStrategy) {
        long jM739toGlobalxdX6G0;
        long j;
        int iFindParagraphByY = MultiParagraphKt.findParagraphByY(this.paragraphInfoList, rect.top);
        float f = ((ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(iFindParagraphByY)).bottom;
        float f2 = rect.bottom;
        if (f >= f2 || iFindParagraphByY == CollectionsKt__CollectionsKt.getLastIndex(this.paragraphInfoList)) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(iFindParagraphByY);
            return paragraphInfo.m739toGlobalxdX6G0(((AndroidParagraph) paragraphInfo.paragraph).m728getRangeForRect86BmAI(paragraphInfo.toLocal(rect), i, textInclusionStrategy), true);
        }
        int iFindParagraphByY2 = MultiParagraphKt.findParagraphByY(this.paragraphInfoList, f2);
        TextRange.Companion.getClass();
        long jM739toGlobalxdX6G02 = TextRange.Zero;
        while (true) {
            TextRange.Companion.getClass();
            jM739toGlobalxdX6G0 = TextRange.Zero;
            if (!TextRange.m748equalsimpl0(jM739toGlobalxdX6G02, jM739toGlobalxdX6G0) || iFindParagraphByY > iFindParagraphByY2) {
                break;
            }
            ParagraphInfo paragraphInfo2 = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(iFindParagraphByY);
            jM739toGlobalxdX6G02 = paragraphInfo2.m739toGlobalxdX6G0(((AndroidParagraph) paragraphInfo2.paragraph).m728getRangeForRect86BmAI(paragraphInfo2.toLocal(rect), i, textInclusionStrategy), true);
            iFindParagraphByY++;
        }
        if (TextRange.m748equalsimpl0(jM739toGlobalxdX6G02, jM739toGlobalxdX6G0)) {
            return jM739toGlobalxdX6G0;
        }
        while (true) {
            TextRange.Companion.getClass();
            j = TextRange.Zero;
            if (!TextRange.m748equalsimpl0(jM739toGlobalxdX6G0, j) || iFindParagraphByY > iFindParagraphByY2) {
                break;
            }
            ParagraphInfo paragraphInfo3 = (ParagraphInfo) ((ArrayList) this.paragraphInfoList).get(iFindParagraphByY2);
            jM739toGlobalxdX6G0 = paragraphInfo3.m739toGlobalxdX6G0(((AndroidParagraph) paragraphInfo3.paragraph).m728getRangeForRect86BmAI(paragraphInfo3.toLocal(rect), i, textInclusionStrategy), true);
            iFindParagraphByY2--;
        }
        return TextRange.m748equalsimpl0(jM739toGlobalxdX6G0, j) ? jM739toGlobalxdX6G02 : TextRangeKt.TextRange((int) (jM739toGlobalxdX6G02 >> 32), (int) (4294967295L & jM739toGlobalxdX6G0));
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "offset(", ") is out of bounds [0, ");
        sbM.append(multiParagraphIntrinsics.annotatedString.text.length());
        sbM.append(')');
        InlineClassHelperKt.throwIllegalArgumentException(sbM.toString());
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "offset(", ") is out of bounds [0, ");
        sbM.append(multiParagraphIntrinsics.annotatedString.text.length());
        sbM.append(']');
        InlineClassHelperKt.throwIllegalArgumentException(sbM.toString());
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
        int iM822getMaxHeightimpl;
        this.intrinsics = multiParagraphIntrinsics;
        this.maxLines = i;
        if (Constraints.m825getMinWidthimpl(j) != 0 || Constraints.m824getMinHeightimpl(j) != 0) {
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
            int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
            if (Constraints.m818getHasBoundedHeightimpl(j)) {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j) - ParagraphKt.ceilToInt(f);
                if (iM822getMaxHeightimpl < 0) {
                    iM822getMaxHeightimpl = 0;
                }
            } else {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
            }
            AndroidParagraph androidParagraph = new AndroidParagraph((AndroidParagraphIntrinsics) paragraphIntrinsics, this.maxLines - i3, i2, ConstraintsKt.Constraints$default(0, iM823getMaxWidthimpl, 0, iM822getMaxHeightimpl, 5), null);
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
        this.width = Constraints.m823getMaxWidthimpl(j);
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
        ArrayList arrayListPlus = arrayList3;
        if (size4 < this.intrinsics.placeholders.size()) {
            int size5 = this.intrinsics.placeholders.size() - arrayList3.size();
            ArrayList arrayList5 = new ArrayList(size5);
            for (int i8 = 0; i8 < size5; i8++) {
                arrayList5.add(null);
            }
            arrayListPlus = CollectionsKt___CollectionsKt.plus((Iterable) arrayList5, (Collection) arrayList3);
        }
        this.placeholderRects = arrayListPlus;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        int i4 = (i3 & 4) != 0 ? Integer.MAX_VALUE : i;
        if ((i3 & 8) != 0) {
            TextOverflow.Companion.getClass();
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, j, i4, i2, (DefaultConstructorMarker) null);
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, float f, Density density, FontFamily.Resolver resolver, List list, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, f, density, resolver, (List<AnnotatedString.Range<Placeholder>>) ((i2 & 32) != 0 ? EmptyList.INSTANCE : list), (i2 & 64) != 0 ? Integer.MAX_VALUE : i, (i2 & 128) != 0 ? false : z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List list, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        int i4;
        List list2 = (i3 & 32) != 0 ? EmptyList.INSTANCE : list;
        int i5 = (i3 & 64) != 0 ? Integer.MAX_VALUE : i;
        if ((i3 & 128) != 0) {
            TextOverflow.Companion.getClass();
            i4 = TextOverflow.Clip;
        } else {
            i4 = i2;
        }
        this(annotatedString, textStyle, j, density, resolver, list2, i5, i4, (DefaultConstructorMarker) null);
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
    private MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, long j, int i, boolean z) {
        int i2;
        if (z) {
            TextOverflow.Companion.getClass();
            i2 = TextOverflow.Ellipsis;
        } else {
            TextOverflow.Companion.getClass();
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, j, i, i2, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, int i, boolean z, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(multiParagraphIntrinsics, (i2 & 2) != 0 ? Integer.MAX_VALUE : i, (i2 & 4) != 0 ? false : z, f);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MultiParagraph(MultiParagraphIntrinsics multiParagraphIntrinsics, int i, boolean z, float f) {
        int i2;
        long jConstraints$default = ConstraintsKt.Constraints$default(0, ParagraphKt.ceilToInt(f), 0, 0, 13);
        if (z) {
            TextOverflow.Companion.getClass();
            i2 = TextOverflow.Ellipsis;
        } else {
            TextOverflow.Companion.getClass();
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, jConstraints$default, i, i2, (DefaultConstructorMarker) null);
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z, float f, Density density, Font.ResourceLoader resourceLoader) {
        int i2;
        MultiParagraphIntrinsics multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, textStyle, list, density, DelegatingFontLoaderForDeprecatedUsage_androidKt.createFontFamilyResolver(resourceLoader));
        TextOverflow.Companion.getClass();
        if (z) {
            i2 = TextOverflow.Ellipsis;
        } else {
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, ConstraintsKt.Constraints$default(0, ParagraphKt.ceilToInt(f), 0, 0, 13), i, i2, (DefaultConstructorMarker) null);
    }

    public MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, float f, Density density, FontFamily.Resolver resolver, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z) {
        int i2;
        MultiParagraphIntrinsics multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, textStyle, list, density, resolver);
        TextOverflow.Companion.getClass();
        if (z) {
            i2 = TextOverflow.Ellipsis;
        } else {
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, ConstraintsKt.Constraints$default(0, ParagraphKt.ceilToInt(f), 0, 0, 13), i, i2, (DefaultConstructorMarker) null);
    }

    private MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z) {
        int i2;
        MultiParagraphIntrinsics multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, textStyle, list, density, resolver);
        TextOverflow.Companion.getClass();
        if (z) {
            i2 = TextOverflow.Ellipsis;
        } else {
            i2 = TextOverflow.Clip;
        }
        this(multiParagraphIntrinsics, j, i, i2, (DefaultConstructorMarker) null);
    }

    private MultiParagraph(AnnotatedString annotatedString, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List<AnnotatedString.Range<Placeholder>> list, int i, int i2) {
        this(new MultiParagraphIntrinsics(annotatedString, textStyle, list, density, resolver), j, i, i2, (DefaultConstructorMarker) null);
    }
}
