package androidx.compose.ui.text.platform.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPathEffect;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathEffect;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class CustomBulletSpan implements LeadingMarginSpan {
    public final float alpha;
    public final Brush brush;
    public final float bulletHeightPx;
    public final float bulletWidthPx;
    public final Density density;
    public final int diff;
    public final DrawStyle drawStyle;
    public final int minimumRequiredIndent;
    public final Shape shape;

    public CustomBulletSpan(Shape shape, float f, float f2, float f3, Brush brush, float f4, DrawStyle drawStyle, Density density, float f5) {
        this.shape = shape;
        this.bulletWidthPx = f;
        this.bulletHeightPx = f2;
        this.brush = brush;
        this.alpha = f4;
        this.drawStyle = drawStyle;
        this.density = density;
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(f + f3);
        this.minimumRequiredIndent = iRoundToInt;
        this.diff = MathKt__MathJVMKt.roundToInt(f5) - iRoundToInt;
    }

    @Override // android.text.style.LeadingMarginSpan
    public final void drawLeadingMargin(final Canvas canvas, final Paint paint, int i, final int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        if (canvas == null) {
            return;
        }
        final float f = (i3 + i5) / 2.0f;
        int i8 = i - this.minimumRequiredIndent;
        if (i8 < 0) {
            i8 = 0;
        }
        final int i9 = i8;
        if (((Spanned) charSequence).getSpanStart(this) != i6 || paint == null) {
            return;
        }
        Paint.Style style = paint.getStyle();
        DrawStyle drawStyle = this.drawStyle;
        Integer numValueOf = null;
        if (Intrinsics.areEqual(drawStyle, Fill.INSTANCE)) {
            paint.setStyle(Paint.Style.FILL);
        } else if (drawStyle instanceof Stroke) {
            paint.setStyle(Paint.Style.STROKE);
            Stroke stroke = (Stroke) drawStyle;
            paint.setStrokeWidth(stroke.width);
            paint.setStrokeMiter(stroke.miter);
            paint.setStrokeCap(DrawStyleSpan_androidKt.m791toAndroidCapBeK7IIE(stroke.cap));
            paint.setStrokeJoin(DrawStyleSpan_androidKt.m792toAndroidJoinWw9F2mQ(stroke.join));
            PathEffect pathEffect = stroke.pathEffect;
            paint.setPathEffect(pathEffect != null ? ((AndroidPathEffect) pathEffect).nativePathEffect : null);
        }
        final long jFloatToRawIntBits = (Float.floatToRawIntBits(this.bulletWidthPx) << 32) | (Float.floatToRawIntBits(this.bulletHeightPx) & 4294967295L);
        Size.Companion companion = Size.Companion;
        Brush brush = this.brush;
        float f2 = this.alpha;
        Function0 function0 = new Function0() { // from class: androidx.compose.ui.text.platform.style.CustomBulletSpan$drawLeadingMargin$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CustomBulletSpan customBulletSpan = this.this$0;
                Outline outlineMo41createOutlinePq9zytI = customBulletSpan.shape.mo41createOutlinePq9zytI(jFloatToRawIntBits, i2 > 0 ? LayoutDirection.Ltr : LayoutDirection.Rtl, customBulletSpan.density);
                Canvas canvas2 = canvas;
                Paint paint2 = paint;
                float f3 = i9;
                float f4 = f;
                int i10 = i2;
                if (outlineMo41createOutlinePq9zytI instanceof Outline.Generic) {
                    canvas2.save();
                    Rect bounds = outlineMo41createOutlinePq9zytI.getBounds();
                    canvas2.translate(f3, f4 - ((bounds.bottom - bounds.top) / 2.0f));
                    Path path = ((Outline.Generic) outlineMo41createOutlinePq9zytI).path;
                    if (!(path instanceof AndroidPath)) {
                        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                    }
                    canvas2.drawPath(((AndroidPath) path).internalPath, paint2);
                    canvas2.restore();
                } else if (outlineMo41createOutlinePq9zytI instanceof Outline.Rounded) {
                    Outline.Rounded rounded = (Outline.Rounded) outlineMo41createOutlinePq9zytI;
                    boolean zIsSimple = RoundRectKt.isSimple(rounded.roundRect);
                    RoundRect roundRect = rounded.roundRect;
                    if (zIsSimple) {
                        float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius >> 32));
                        canvas2.drawRoundRect(f3, f4 - (roundRect.getHeight() / 2.0f), (roundRect.getWidth() * i10) + f3, (roundRect.getHeight() / 2.0f) + f4, fIntBitsToFloat, fIntBitsToFloat, paint2);
                    } else {
                        AndroidPath androidPathPath = AndroidPath_androidKt.Path();
                        Path.addRoundRect$default(androidPathPath, roundRect);
                        canvas2.save();
                        canvas2.translate(f3, f4 - (roundRect.getHeight() / 2.0f));
                        canvas2.drawPath(androidPathPath.internalPath, paint2);
                        canvas2.restore();
                    }
                } else if (outlineMo41createOutlinePq9zytI instanceof Outline.Rectangle) {
                    Rect rect = ((Outline.Rectangle) outlineMo41createOutlinePq9zytI).rect;
                    float f5 = (rect.bottom - rect.top) / 2.0f;
                    canvas2.drawRect(f3, f4 - f5, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect.right, rect.left, i10, f3), f4 + f5, paint2);
                }
                return Unit.INSTANCE;
            }
        };
        if (brush == null) {
            if (!Float.isNaN(f2)) {
                numValueOf = Integer.valueOf(paint.getAlpha());
                paint.setAlpha((int) Math.rint(f2 * 255.0f));
            }
            function0.invoke();
            if (numValueOf != null) {
                paint.setAlpha(numValueOf.intValue());
            }
        } else if (brush instanceof SolidColor) {
            int color = paint.getColor();
            if (!Float.isNaN(f2)) {
                numValueOf = Integer.valueOf(paint.getAlpha());
                paint.setAlpha((int) Math.rint(f2 * 255.0f));
            }
            paint.setColor(ColorKt.m469toArgb8_81llA(((SolidColor) brush).value));
            function0.invoke();
            paint.setColor(color);
            if (numValueOf != null) {
                paint.setAlpha(numValueOf.intValue());
            }
        } else if (brush instanceof ShaderBrush) {
            Shader shader = paint.getShader();
            if (!Float.isNaN(f2)) {
                numValueOf = Integer.valueOf(paint.getAlpha());
                paint.setAlpha((int) Math.rint(f2 * 255.0f));
            }
            paint.setShader(((ShaderBrush) brush).mo453createShaderuvyYCjk(jFloatToRawIntBits));
            function0.invoke();
            paint.setShader(shader);
            if (numValueOf != null) {
                paint.setAlpha(numValueOf.intValue());
            }
        }
        paint.setStyle(style);
    }

    @Override // android.text.style.LeadingMarginSpan
    public final int getLeadingMargin(boolean z) {
        int i = this.diff;
        if (i >= 0) {
            return 0;
        }
        return Math.abs(i);
    }
}
