package androidx.compose.ui.text;

import android.graphics.Paint;
import android.graphics.RectF;
import android.text.GraphemeClusterSegmentFinder;
import android.text.Layout;
import android.text.SegmentFinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.android.AndroidLayoutApi34;
import androidx.compose.ui.text.android.TextAndroidCanvas;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.android.TextLayout_androidKt;
import androidx.compose.ui.text.android.selection.Api34SegmentFinder;
import androidx.compose.ui.text.android.selection.WordSegmentFinder;
import androidx.compose.ui.text.android.style.IndentationFixSpan;
import androidx.compose.ui.text.android.style.PlaceholderSpan;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.platform.AndroidParagraphHelper_androidKt;
import androidx.compose.ui.text.platform.AndroidParagraphHelper_androidKt$NoopSpan$1;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.platform.AndroidTextPaint;
import androidx.compose.ui.text.platform.style.ShaderBrushSpan;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AndroidParagraph implements Paragraph {
    public final CharSequence charSequence;
    public final long constraints;
    public final TextLayout layout;
    public final int maxLines;
    public final AndroidParagraphIntrinsics paragraphIntrinsics;
    public final List placeholderRects;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ResolvedTextDirection.values().length];
            try {
                iArr[ResolvedTextDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ResolvedTextDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ AndroidParagraph(AndroidParagraphIntrinsics androidParagraphIntrinsics, int i, int i2, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(androidParagraphIntrinsics, i, i2, j);
    }

    public final float getHeight() {
        return this.layout.getHeight();
    }

    /* renamed from: getRangeForRect-8-6BmAI, reason: not valid java name */
    public final long m728getRangeForRect86BmAI(Rect rect, int i, final TextInclusionStrategy textInclusionStrategy) {
        SegmentFinder graphemeClusterSegmentFinder;
        RectF androidRectF = RectHelper_androidKt.toAndroidRectF(rect);
        TextGranularity.Companion.getClass();
        boolean z = i != 0 && i == TextGranularity.Word;
        final Function2 function2 = new Function2() { // from class: androidx.compose.ui.text.AndroidParagraph$getRangeForRect$range$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(textInclusionStrategy.isIncluded(RectHelper_androidKt.toComposeRect((RectF) obj), RectHelper_androidKt.toComposeRect((RectF) obj2)));
            }
        };
        TextLayout textLayout = this.layout;
        textLayout.getClass();
        AndroidLayoutApi34.INSTANCE.getClass();
        if (z) {
            Api34SegmentFinder api34SegmentFinder = Api34SegmentFinder.INSTANCE;
            final WordSegmentFinder wordSegmentFinder = new WordSegmentFinder(textLayout.layout.getText(), textLayout.getWordIterator());
            api34SegmentFinder.getClass();
            graphemeClusterSegmentFinder = new SegmentFinder() { // from class: androidx.compose.ui.text.android.selection.Api34SegmentFinder$toAndroidSegmentFinder$1
                @Override // android.text.SegmentFinder
                public final int nextEndBoundary(int i2) {
                    return wordSegmentFinder.nextEndBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int nextStartBoundary(int i2) {
                    return wordSegmentFinder.nextStartBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int previousEndBoundary(int i2) {
                    return wordSegmentFinder.previousEndBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int previousStartBoundary(int i2) {
                    return wordSegmentFinder.previousStartBoundary(i2);
                }
            };
        } else {
            graphemeClusterSegmentFinder = new GraphemeClusterSegmentFinder(textLayout.layout.getText(), textLayout.textPaint);
        }
        int[] rangeForRect = textLayout.layout.getRangeForRect(androidRectF, graphemeClusterSegmentFinder, new Layout.TextInclusionStrategy() { // from class: androidx.compose.ui.text.android.AndroidLayoutApi34$$ExternalSyntheticLambda0
            @Override // android.text.Layout.TextInclusionStrategy
            public final boolean isSegmentInside(RectF rectF, RectF rectF2) {
                Function2 function22 = function2;
                AndroidLayoutApi34 androidLayoutApi34 = AndroidLayoutApi34.INSTANCE;
                return ((Boolean) function22.invoke(rectF, rectF2)).booleanValue();
            }
        });
        if (rangeForRect != null) {
            return TextRangeKt.TextRange(rangeForRect[0], rangeForRect[1]);
        }
        TextRange.Companion.getClass();
        return TextRange.Zero;
    }

    public final float getWidth() {
        return Constraints.m823getMaxWidthimpl(this.constraints);
    }

    public final void paint(Canvas canvas) {
        android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        android.graphics.Canvas canvas3 = ((AndroidCanvas) canvas).internalCanvas;
        TextLayout textLayout = this.layout;
        if (textLayout.didExceedMaxLines) {
            canvas3.save();
            canvas3.clipRect(0.0f, 0.0f, getWidth(), getHeight());
        }
        if (canvas3.getClipBounds(textLayout.rect)) {
            int i = textLayout.topPadding;
            if (i != 0) {
                canvas3.translate(0.0f, i);
            }
            TextAndroidCanvas textAndroidCanvas = TextLayout_androidKt.SharedTextAndroidCanvas;
            textAndroidCanvas.nativeCanvas = canvas3;
            textLayout.layout.draw(textAndroidCanvas);
            if (i != 0) {
                canvas3.translate(0.0f, (-1) * i);
            }
        }
        if (textLayout.didExceedMaxLines) {
            canvas3.restore();
        }
    }

    /* renamed from: paint-LG529CI, reason: not valid java name */
    public final void m729paintLG529CI(Canvas canvas, long j, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle, int i) {
        AndroidParagraphIntrinsics androidParagraphIntrinsics = this.paragraphIntrinsics;
        AndroidTextPaint androidTextPaint = androidParagraphIntrinsics.textPaint;
        int i2 = androidTextPaint.backingBlendMode;
        androidTextPaint.m785setColor8_81llA(j);
        androidTextPaint.setShadow(shadow);
        androidTextPaint.setTextDecoration(textDecoration);
        androidTextPaint.setDrawStyle(drawStyle);
        androidTextPaint.m783setBlendModes9anfk8(i);
        paint(canvas);
        androidParagraphIntrinsics.textPaint.m783setBlendModes9anfk8(i2);
    }

    /* renamed from: paint-hn5TExg, reason: not valid java name */
    public final void m730painthn5TExg(Canvas canvas, Brush brush, float f, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle, int i) {
        AndroidParagraphIntrinsics androidParagraphIntrinsics = this.paragraphIntrinsics;
        AndroidTextPaint androidTextPaint = androidParagraphIntrinsics.textPaint;
        int i2 = androidTextPaint.backingBlendMode;
        float width = getWidth();
        float height = getHeight();
        long jFloatToRawIntBits = (Float.floatToRawIntBits(height) & 4294967295L) | (Float.floatToRawIntBits(width) << 32);
        Size.Companion companion = Size.Companion;
        androidTextPaint.m784setBrush12SF9DM(brush, jFloatToRawIntBits, f);
        androidTextPaint.setShadow(shadow);
        androidTextPaint.setTextDecoration(textDecoration);
        androidTextPaint.setDrawStyle(drawStyle);
        androidTextPaint.m783setBlendModes9anfk8(i);
        paint(canvas);
        androidParagraphIntrinsics.textPaint.m783setBlendModes9anfk8(i2);
    }

    public /* synthetic */ AndroidParagraph(String str, TextStyle textStyle, List list, List list2, int i, int i2, long j, FontFamily.Resolver resolver, Density density, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, list, list2, i, i2, j, resolver, density);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0131  */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r1v19, types: [android.text.Spanned] */
    /* JADX WARN: Type inference failed for: r7v13, types: [androidx.compose.ui.text.android.TextLayout] */
    /* JADX WARN: Type inference failed for: r7v18, types: [androidx.compose.ui.text.android.TextLayout] */
    /* JADX WARN: Type inference failed for: r9v34, types: [android.text.Spannable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AndroidParagraph(AndroidParagraphIntrinsics androidParagraphIntrinsics, int i, int i2, long j) {
        CharSequence charSequence;
        int i3;
        int i4;
        int i5;
        TextUtils.TruncateAt truncateAt;
        TextUtils.TruncateAt truncateAt2;
        TextLayout textLayout;
        AndroidParagraph androidParagraph;
        TextLayout textLayout2;
        ?? r13;
        ShaderBrushSpan[] shaderBrushSpanArr;
        CharSequence charSequence2;
        List list;
        Rect rect;
        ResolvedTextDirection resolvedTextDirection;
        int i6;
        float primaryHorizontal;
        float lineBaseline;
        int heightPx;
        float lineTop;
        float heightPx2;
        float lineBaseline2;
        int i7;
        TextLayout textLayout3;
        PlatformParagraphStyle platformParagraphStyle;
        PlatformParagraphStyle platformParagraphStyle2;
        this.paragraphIntrinsics = androidParagraphIntrinsics;
        this.maxLines = i;
        this.constraints = j;
        if (Constraints.m824getMinHeightimpl(j) != 0 || Constraints.m825getMinWidthimpl(j) != 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Setting Constraints.minWidth and Constraints.minHeight is not supported, these should be the default zero values instead.");
        }
        if (i < 1) {
            InlineClassHelperKt.throwIllegalArgumentException("maxLines should be greater than 0");
        }
        TextStyle textStyle = androidParagraphIntrinsics.style;
        TextOverflow.Companion.getClass();
        int i8 = TextOverflow.Ellipsis;
        if (i2 == i8 && !TextUnit.m868equalsimpl0(textStyle.spanStyle.letterSpacing, TextUnitKt.getSp(0))) {
            long j2 = textStyle.spanStyle.letterSpacing;
            TextUnit.Companion.getClass();
            if (!TextUnit.m868equalsimpl0(j2, TextUnit.Unspecified)) {
                ParagraphStyle paragraphStyle = textStyle.paragraphStyle;
                int i9 = paragraphStyle.textAlign;
                TextAlign.Companion.getClass();
                if (i9 != TextAlign.Unspecified) {
                    int i10 = TextAlign.Start;
                    int i11 = paragraphStyle.textAlign;
                    if (i11 != i10 && i11 != TextAlign.Justify) {
                        CharSequence charSequence3 = androidParagraphIntrinsics.charSequence;
                        int length = charSequence3.length();
                        charSequence = charSequence3;
                        if (length != 0) {
                            SpannableString spannableString = charSequence3 instanceof Spannable ? (Spannable) charSequence3 : new SpannableString(charSequence3);
                            spannableString.setSpan(new IndentationFixSpan(), spannableString.length() - 1, spannableString.length() - 1, 33);
                            charSequence = spannableString;
                        }
                    }
                }
            }
        } else {
            charSequence = androidParagraphIntrinsics.charSequence;
        }
        this.charSequence = charSequence;
        int i12 = textStyle.paragraphStyle.textAlign;
        TextAlign.Companion.getClass();
        if (i12 == TextAlign.Left) {
            i3 = 3;
        } else if (i12 == TextAlign.Right) {
            i3 = 4;
        } else if (i12 == TextAlign.Center) {
            i3 = 2;
        } else {
            i3 = (i12 != TextAlign.Start && i12 == TextAlign.End) ? 1 : 0;
        }
        ParagraphStyle paragraphStyle2 = textStyle.paragraphStyle;
        int i13 = paragraphStyle2.textAlign == TextAlign.Justify ? 1 : 0;
        Hyphens.Companion.getClass();
        int i14 = paragraphStyle2.hyphens == Hyphens.Auto ? 4 : 0;
        LineBreak.Companion companion = LineBreak.Companion;
        int i15 = paragraphStyle2.lineBreak;
        int i16 = i15 & 255;
        LineBreak.Strategy.Companion.getClass();
        if (i16 != LineBreak.Strategy.Simple) {
            if (i16 == LineBreak.Strategy.HighQuality) {
                i4 = 1;
            } else {
                i4 = i16 == LineBreak.Strategy.Balanced ? 2 : 0;
            }
        }
        int i17 = (i15 >> 8) & 255;
        LineBreak.Strictness.Companion.getClass();
        if (i17 != LineBreak.Strictness.Default) {
            if (i17 == LineBreak.Strictness.Loose) {
                i5 = 1;
            } else if (i17 == LineBreak.Strictness.Normal) {
                i5 = 2;
            } else {
                i5 = i17 == LineBreak.Strictness.Strict ? 3 : 0;
            }
        }
        int i18 = (i15 >> 16) & 255;
        LineBreak.WordBreak.Companion.getClass();
        int i19 = (i18 != LineBreak.WordBreak.Default && i18 == LineBreak.WordBreak.Phrase) ? 1 : 0;
        if (i2 == i8) {
            truncateAt2 = TextUtils.TruncateAt.END;
        } else if (i2 == TextOverflow.MiddleEllipsis) {
            truncateAt2 = TextUtils.TruncateAt.MIDDLE;
        } else if (i2 == TextOverflow.StartEllipsis) {
            truncateAt2 = TextUtils.TruncateAt.START;
        } else {
            truncateAt = null;
            float width = getWidth();
            AndroidTextPaint androidTextPaint = androidParagraphIntrinsics.textPaint;
            AndroidParagraphHelper_androidKt$NoopSpan$1 androidParagraphHelper_androidKt$NoopSpan$1 = AndroidParagraphHelper_androidKt.NoopSpan;
            PlatformTextStyle platformTextStyle = androidParagraphIntrinsics.style.platformStyle;
            boolean z = (platformTextStyle != null || (platformParagraphStyle2 = platformTextStyle.paragraphStyle) == null) ? false : platformParagraphStyle2.includeFontPadding;
            int i20 = i3;
            TextUtils.TruncateAt truncateAt3 = truncateAt;
            textLayout = new TextLayout(charSequence, width, androidTextPaint, i20, truncateAt3, androidParagraphIntrinsics.textDirectionHeuristic, 1.0f, 0.0f, z, true, i, i4, i5, i19, i14, i13, null, null, androidParagraphIntrinsics.layoutIntrinsics, 196736, null);
            if (i2 != i8 && textLayout.getHeight() > Constraints.m822getMaxHeightimpl(j) && i > 1) {
                int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                int i21 = 0;
                while (true) {
                    i7 = textLayout.lineCount;
                    if (i21 >= i7) {
                        break;
                    }
                    if (textLayout.getLineBottom(i21) > iM822getMaxHeightimpl) {
                        i7 = i21;
                        break;
                    }
                    i21++;
                }
                androidParagraph = this;
                if (i7 < 0 || i7 == androidParagraph.maxLines) {
                    textLayout3 = textLayout;
                } else {
                    int i22 = i7 < 1 ? 1 : i7;
                    CharSequence charSequence4 = androidParagraph.charSequence;
                    float width2 = androidParagraph.getWidth();
                    AndroidParagraphIntrinsics androidParagraphIntrinsics2 = androidParagraph.paragraphIntrinsics;
                    AndroidTextPaint androidTextPaint2 = androidParagraphIntrinsics2.textPaint;
                    AndroidParagraphHelper_androidKt$NoopSpan$1 androidParagraphHelper_androidKt$NoopSpan$12 = AndroidParagraphHelper_androidKt.NoopSpan;
                    PlatformTextStyle platformTextStyle2 = androidParagraphIntrinsics2.style.platformStyle;
                    textLayout3 = new TextLayout(charSequence4, width2, androidTextPaint2, i20, truncateAt3, androidParagraphIntrinsics2.textDirectionHeuristic, 1.0f, 0.0f, (platformTextStyle2 == null || (platformParagraphStyle = platformTextStyle2.paragraphStyle) == null) ? false : platformParagraphStyle.includeFontPadding, true, i22, i4, i5, i19, i14, i13, null, null, androidParagraphIntrinsics2.layoutIntrinsics, 196736, null);
                }
                androidParagraph.layout = textLayout3;
            } else {
                androidParagraph = this;
                androidParagraph.layout = textLayout;
            }
            AndroidTextPaint androidTextPaint3 = androidParagraph.paragraphIntrinsics.textPaint;
            SpanStyle spanStyle = textStyle.spanStyle;
            Size.Companion companion2 = Size.Companion;
            androidTextPaint3.m784setBrush12SF9DM(spanStyle.textForegroundStyle.getBrush(), (Float.floatToRawIntBits(androidParagraph.getHeight()) & 4294967295L) | (Float.floatToRawIntBits(androidParagraph.getWidth()) << 32), spanStyle.textForegroundStyle.getAlpha());
            textLayout2 = androidParagraph.layout;
            if (textLayout2.layout.getText() instanceof Spanned) {
                shaderBrushSpanArr = null;
                r13 = 0;
            } else {
                Spanned spanned = (Spanned) textLayout2.layout.getText();
                if (spanned.nextSpanTransition(-1, spanned.length(), ShaderBrushSpan.class) != spanned.length()) {
                    r13 = 0;
                    shaderBrushSpanArr = (ShaderBrushSpan[]) ((Spanned) textLayout2.layout.getText()).getSpans(0, textLayout2.layout.getText().length(), ShaderBrushSpan.class);
                } else {
                    r13 = 0;
                    shaderBrushSpanArr = null;
                }
            }
            if (shaderBrushSpanArr != null) {
                ArrayIterator arrayIterator = new ArrayIterator(shaderBrushSpanArr);
                while (arrayIterator.hasNext()) {
                    ((SnapshotMutableStateImpl) ((ShaderBrushSpan) arrayIterator.next()).size$delegate).setValue(Size.m415boximpl((Float.floatToRawIntBits(androidParagraph.getHeight()) & 4294967295L) | (Float.floatToRawIntBits(androidParagraph.getWidth()) << 32)));
                }
            }
            charSequence2 = androidParagraph.charSequence;
            if (charSequence2 instanceof Spanned) {
                list = EmptyList.INSTANCE;
            } else {
                ?? r1 = (Spanned) charSequence2;
                Object[] spans = r1.getSpans(r13, charSequence2.length(), PlaceholderSpan.class);
                ArrayList arrayList = new ArrayList(spans.length);
                int length2 = spans.length;
                for (int i23 = r13; i23 < length2; i23++) {
                    PlaceholderSpan placeholderSpan = (PlaceholderSpan) spans[i23];
                    int spanStart = r1.getSpanStart(placeholderSpan);
                    int spanEnd = r1.getSpanEnd(placeholderSpan);
                    int lineForOffset = androidParagraph.layout.layout.getLineForOffset(spanStart);
                    boolean z2 = lineForOffset >= androidParagraph.maxLines ? true : r13;
                    boolean z3 = (androidParagraph.layout.layout.getEllipsisCount(lineForOffset) <= 0 || spanEnd <= androidParagraph.layout.layout.getEllipsisStart(lineForOffset)) ? r13 : true;
                    boolean z4 = spanEnd > androidParagraph.layout.getLineEnd(lineForOffset) ? true : r13;
                    if (z3 || z4 || z2) {
                        rect = null;
                    } else {
                        if (androidParagraph.layout.layout.isRtlCharAt(spanStart)) {
                            resolvedTextDirection = ResolvedTextDirection.Rtl;
                        } else {
                            resolvedTextDirection = ResolvedTextDirection.Ltr;
                        }
                        int i24 = WhenMappings.$EnumSwitchMapping$0[resolvedTextDirection.ordinal()];
                        if (i24 != 1) {
                            i6 = 2;
                            if (i24 == 2) {
                                float primaryHorizontal2 = androidParagraph.layout.getPrimaryHorizontal(spanStart, r13);
                                if (!placeholderSpan.isLaidOut) {
                                    InlineClassHelperKt.throwIllegalStateException("PlaceholderSpan is not laid out yet.");
                                }
                                primaryHorizontal = primaryHorizontal2 - placeholderSpan.widthPx;
                            } else {
                                throw new NoWhenBranchMatchedException();
                            }
                        } else {
                            i6 = 2;
                            primaryHorizontal = androidParagraph.layout.getPrimaryHorizontal(spanStart, r13);
                        }
                        if (!placeholderSpan.isLaidOut) {
                            InlineClassHelperKt.throwIllegalStateException("PlaceholderSpan is not laid out yet.");
                        }
                        float f = placeholderSpan.widthPx + primaryHorizontal;
                        TextLayout textLayout4 = androidParagraph.layout;
                        switch (placeholderSpan.verticalAlign) {
                            case 0:
                                lineBaseline = textLayout4.getLineBaseline(lineForOffset);
                                heightPx = placeholderSpan.getHeightPx();
                                lineTop = lineBaseline - heightPx;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 1:
                                lineTop = textLayout4.getLineTop(lineForOffset);
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 2:
                                lineBaseline = textLayout4.getLineBottom(lineForOffset);
                                heightPx = placeholderSpan.getHeightPx();
                                lineTop = lineBaseline - heightPx;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 3:
                                lineTop = ((textLayout4.getLineBottom(lineForOffset) + textLayout4.getLineTop(lineForOffset)) - placeholderSpan.getHeightPx()) / i6;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 4:
                                Paint.FontMetricsInt fontMetricsInt = placeholderSpan.fontMetrics;
                                heightPx2 = (fontMetricsInt == null ? null : fontMetricsInt).ascent;
                                lineBaseline2 = textLayout4.getLineBaseline(lineForOffset);
                                lineTop = lineBaseline2 + heightPx2;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 5:
                                lineBaseline = textLayout4.getLineBaseline(lineForOffset) + (placeholderSpan.fontMetrics == null ? null : r12).descent;
                                heightPx = placeholderSpan.getHeightPx();
                                lineTop = lineBaseline - heightPx;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            case 6:
                                Paint.FontMetricsInt fontMetricsInt2 = placeholderSpan.fontMetrics;
                                fontMetricsInt2 = fontMetricsInt2 == null ? null : fontMetricsInt2;
                                heightPx2 = ((fontMetricsInt2.ascent + fontMetricsInt2.descent) - placeholderSpan.getHeightPx()) / i6;
                                lineBaseline2 = textLayout4.getLineBaseline(lineForOffset);
                                lineTop = lineBaseline2 + heightPx2;
                                rect = new Rect(primaryHorizontal, lineTop, f, placeholderSpan.getHeightPx() + lineTop);
                                break;
                            default:
                                throw new IllegalStateException("unexpected verticalAlignment");
                        }
                    }
                    arrayList.add(rect);
                }
                list = arrayList;
            }
            androidParagraph.placeholderRects = list;
        }
        truncateAt = truncateAt2;
        float width3 = getWidth();
        AndroidTextPaint androidTextPaint4 = androidParagraphIntrinsics.textPaint;
        AndroidParagraphHelper_androidKt$NoopSpan$1 androidParagraphHelper_androidKt$NoopSpan$13 = AndroidParagraphHelper_androidKt.NoopSpan;
        PlatformTextStyle platformTextStyle3 = androidParagraphIntrinsics.style.platformStyle;
        if (platformTextStyle3 != null) {
        }
        int i202 = i3;
        TextUtils.TruncateAt truncateAt32 = truncateAt;
        textLayout = new TextLayout(charSequence, width3, androidTextPaint4, i202, truncateAt32, androidParagraphIntrinsics.textDirectionHeuristic, 1.0f, 0.0f, z, true, i, i4, i5, i19, i14, i13, null, null, androidParagraphIntrinsics.layoutIntrinsics, 196736, null);
        if (i2 != i8) {
            androidParagraph = this;
            androidParagraph.layout = textLayout;
        }
        AndroidTextPaint androidTextPaint32 = androidParagraph.paragraphIntrinsics.textPaint;
        SpanStyle spanStyle2 = textStyle.spanStyle;
        Size.Companion companion22 = Size.Companion;
        androidTextPaint32.m784setBrush12SF9DM(spanStyle2.textForegroundStyle.getBrush(), (Float.floatToRawIntBits(androidParagraph.getHeight()) & 4294967295L) | (Float.floatToRawIntBits(androidParagraph.getWidth()) << 32), spanStyle2.textForegroundStyle.getAlpha());
        textLayout2 = androidParagraph.layout;
        if (textLayout2.layout.getText() instanceof Spanned) {
        }
        if (shaderBrushSpanArr != null) {
        }
        charSequence2 = androidParagraph.charSequence;
        if (charSequence2 instanceof Spanned) {
        }
        androidParagraph.placeholderRects = list;
    }

    private AndroidParagraph(String str, TextStyle textStyle, List<? extends AnnotatedString.Range<? extends AnnotatedString.Annotation>> list, List<AnnotatedString.Range<Placeholder>> list2, int i, int i2, long j, FontFamily.Resolver resolver, Density density) {
        this(new AndroidParagraphIntrinsics(str, textStyle, list, list2, resolver, density), i, i2, j, null);
    }
}
