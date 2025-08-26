package androidx.compose.ui.graphics;

import android.graphics.Paint;
import android.graphics.Shader;
import androidx.compose.ui.graphics.BlendMode;

/* loaded from: classes.dex */
public final class AndroidPaint implements Paint {
    public int _blendMode;
    public ColorFilter internalColorFilter;
    public final android.graphics.Paint internalPaint;
    public Shader internalShader;
    public PathEffect pathEffect;

    public AndroidPaint(android.graphics.Paint paint) {
        this.internalPaint = paint;
        BlendMode.Companion.getClass();
        this._blendMode = BlendMode.SrcOver;
    }

    /* renamed from: getFilterQuality-f-v9h1I, reason: not valid java name */
    public final int m436getFilterQualityfv9h1I() {
        if (this.internalPaint.isFilterBitmap()) {
            FilterQuality.Companion.getClass();
            return FilterQuality.Low;
        }
        FilterQuality.Companion.getClass();
        return 0;
    }

    /* renamed from: getStrokeCap-KaPHkGw, reason: not valid java name */
    public final int m437getStrokeCapKaPHkGw() {
        Paint.Cap strokeCap = this.internalPaint.getStrokeCap();
        int i = strokeCap == null ? -1 : AndroidPaint_androidKt$WhenMappings.$EnumSwitchMapping$1[strokeCap.ordinal()];
        if (i == 1) {
            StrokeCap.Companion.getClass();
            return 0;
        }
        if (i == 2) {
            StrokeCap.Companion.getClass();
            return StrokeCap.Round;
        }
        if (i != 3) {
            StrokeCap.Companion.getClass();
            return 0;
        }
        StrokeCap.Companion.getClass();
        return StrokeCap.Square;
    }

    /* renamed from: getStrokeJoin-LxFBmk8, reason: not valid java name */
    public final int m438getStrokeJoinLxFBmk8() {
        Paint.Join strokeJoin = this.internalPaint.getStrokeJoin();
        int i = strokeJoin == null ? -1 : AndroidPaint_androidKt$WhenMappings.$EnumSwitchMapping$2[strokeJoin.ordinal()];
        if (i == 1) {
            StrokeJoin.Companion.getClass();
            return 0;
        }
        if (i == 2) {
            StrokeJoin.Companion.getClass();
            return StrokeJoin.Bevel;
        }
        if (i != 3) {
            StrokeJoin.Companion.getClass();
            return 0;
        }
        StrokeJoin.Companion.getClass();
        return StrokeJoin.Round;
    }

    public final void setAlpha(float f) {
        this.internalPaint.setAlpha((int) Math.rint(f * 255.0f));
    }

    /* renamed from: setBlendMode-s9anfk8, reason: not valid java name */
    public final void m439setBlendModes9anfk8(int i) {
        int i2 = this._blendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        if (i2 == i) {
            return;
        }
        this._blendMode = i;
        WrapperVerificationHelperMethods.INSTANCE.m508setBlendModeGB0RdKg(this.internalPaint, i);
    }

    /* renamed from: setColor-8_81llA, reason: not valid java name */
    public final void m440setColor8_81llA(long j) {
        WrapperVerificationHelperMethods.INSTANCE.m509setColor4WTKRHQ(this.internalPaint, j);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.internalColorFilter = colorFilter;
        this.internalPaint.setColorFilter(colorFilter != null ? colorFilter.nativeColorFilter : null);
    }

    /* renamed from: setFilterQuality-vDHp3xo, reason: not valid java name */
    public final void m441setFilterQualityvDHp3xo(int i) {
        android.graphics.Paint paint = this.internalPaint;
        FilterQuality.Companion.getClass();
        paint.setFilterBitmap(!(i == 0));
    }

    public final void setPathEffect(PathEffect pathEffect) {
        AndroidPathEffect androidPathEffect = (AndroidPathEffect) pathEffect;
        this.internalPaint.setPathEffect(androidPathEffect != null ? androidPathEffect.nativePathEffect : null);
        this.pathEffect = pathEffect;
    }

    public final void setShader(Shader shader) {
        this.internalShader = shader;
        this.internalPaint.setShader(shader);
    }

    /* renamed from: setStrokeCap-BeK7IIE, reason: not valid java name */
    public final void m442setStrokeCapBeK7IIE(int i) {
        android.graphics.Paint paint = this.internalPaint;
        StrokeCap.Companion.getClass();
        paint.setStrokeCap(i == StrokeCap.Square ? Paint.Cap.SQUARE : i == StrokeCap.Round ? Paint.Cap.ROUND : i == 0 ? Paint.Cap.BUTT : Paint.Cap.BUTT);
    }

    /* renamed from: setStrokeJoin-Ww9F2mQ, reason: not valid java name */
    public final void m443setStrokeJoinWw9F2mQ(int i) {
        android.graphics.Paint paint = this.internalPaint;
        StrokeJoin.Companion.getClass();
        paint.setStrokeJoin(i == 0 ? Paint.Join.MITER : i == StrokeJoin.Bevel ? Paint.Join.BEVEL : i == StrokeJoin.Round ? Paint.Join.ROUND : Paint.Join.MITER);
    }

    public final void setStrokeWidth(float f) {
        this.internalPaint.setStrokeWidth(f);
    }

    /* renamed from: setStyle-k9PVt8s, reason: not valid java name */
    public final void m444setStylek9PVt8s(int i) {
        android.graphics.Paint paint = this.internalPaint;
        PaintingStyle.Companion.getClass();
        paint.setStyle(i == PaintingStyle.Stroke ? Paint.Style.STROKE : Paint.Style.FILL);
    }

    public AndroidPaint() {
        this(new android.graphics.Paint(7));
    }
}
