package androidx.compose.ui.text;

import android.graphics.RectF;
import android.text.GraphemeClusterSegmentFinder;
import android.text.Layout;
import android.text.SegmentFinder;
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
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.platform.AndroidTextPaint;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidParagraph implements Paragraph {
    public final CharSequence charSequence;
    public final long constraints;
    public final TextLayout layout;
    public final int maxLines;
    public final AndroidParagraphIntrinsics paragraphIntrinsics;
    public final List placeholderRects;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final long m726getRangeForRect86BmAI(Rect rect, int i, final TextInclusionStrategy textInclusionStrategy) {
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
                return Boolean.valueOf(TextInclusionStrategy.this.isIncluded(RectHelper_androidKt.toComposeRect((RectF) obj), RectHelper_androidKt.toComposeRect((RectF) obj2)));
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
                    return SegmentFinder.this.nextEndBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int nextStartBoundary(int i2) {
                    return SegmentFinder.this.nextStartBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int previousEndBoundary(int i2) {
                    return SegmentFinder.this.previousEndBoundary(i2);
                }

                @Override // android.text.SegmentFinder
                public final int previousStartBoundary(int i2) {
                    return SegmentFinder.this.previousStartBoundary(i2);
                }
            };
        } else {
            graphemeClusterSegmentFinder = new GraphemeClusterSegmentFinder(textLayout.layout.getText(), textLayout.textPaint);
        }
        int[] rangeForRect = textLayout.layout.getRangeForRect(androidRectF, graphemeClusterSegmentFinder, new Layout.TextInclusionStrategy() { // from class: androidx.compose.ui.text.android.AndroidLayoutApi34$$ExternalSyntheticLambda0
            @Override // android.text.Layout.TextInclusionStrategy
            public final boolean isSegmentInside(RectF rectF, RectF rectF2) {
                Function2 function22 = Function2.this;
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
        return Constraints.m821getMaxWidthimpl(this.constraints);
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
    public final void m727paintLG529CI(Canvas canvas, long j, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle, int i) {
        AndroidParagraphIntrinsics androidParagraphIntrinsics = this.paragraphIntrinsics;
        AndroidTextPaint androidTextPaint = androidParagraphIntrinsics.textPaint;
        int i2 = androidTextPaint.backingBlendMode;
        androidTextPaint.m783setColor8_81llA(j);
        androidTextPaint.setShadow(shadow);
        androidTextPaint.setTextDecoration(textDecoration);
        androidTextPaint.setDrawStyle(drawStyle);
        androidTextPaint.m781setBlendModes9anfk8(i);
        paint(canvas);
        androidParagraphIntrinsics.textPaint.m781setBlendModes9anfk8(i2);
    }

    /* renamed from: paint-hn5TExg, reason: not valid java name */
    public final void m728painthn5TExg(Canvas canvas, Brush brush, float f, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle, int i) {
        AndroidParagraphIntrinsics androidParagraphIntrinsics = this.paragraphIntrinsics;
        AndroidTextPaint androidTextPaint = androidParagraphIntrinsics.textPaint;
        int i2 = androidTextPaint.backingBlendMode;
        float width = getWidth();
        float height = getHeight();
        long floatToRawIntBits = (Float.floatToRawIntBits(height) & 4294967295L) | (Float.floatToRawIntBits(width) << 32);
        Size.Companion companion = Size.Companion;
        androidTextPaint.m782setBrush12SF9DM(brush, floatToRawIntBits, f);
        androidTextPaint.setShadow(shadow);
        androidTextPaint.setTextDecoration(textDecoration);
        androidTextPaint.setDrawStyle(drawStyle);
        androidTextPaint.m781setBlendModes9anfk8(i);
        paint(canvas);
        androidParagraphIntrinsics.textPaint.m781setBlendModes9anfk8(i2);
    }

    public /* synthetic */ AndroidParagraph(String str, TextStyle textStyle, List list, List list2, int i, int i2, long j, FontFamily.Resolver resolver, Density density, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, list, list2, i, i2, j, resolver, density);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02e6  */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private AndroidParagraph(androidx.compose.ui.text.platform.AndroidParagraphIntrinsics r42, int r43, int r44, long r45) {
        /*
            Method dump skipped, instructions count: 1076
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.AndroidParagraph.<init>(androidx.compose.ui.text.platform.AndroidParagraphIntrinsics, int, int, long):void");
    }

    private AndroidParagraph(String str, TextStyle textStyle, List<? extends AnnotatedString.Range<? extends AnnotatedString.Annotation>> list, List<AnnotatedString.Range<Placeholder>> list2, int i, int i2, long j, FontFamily.Resolver resolver, Density density) {
        this(new AndroidParagraphIntrinsics(str, textStyle, list, list2, resolver, density), i, i2, j, null);
    }
}
