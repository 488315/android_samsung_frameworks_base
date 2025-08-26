package androidx.compose.ui.graphics.painter;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class Painter {
    public ColorFilter colorFilter;
    public AndroidPaint layerPaint;
    public boolean useLayer;
    public float alpha = 1.0f;
    public LayoutDirection layoutDirection = LayoutDirection.Ltr;

    public Painter() {
        new Function1() { // from class: androidx.compose.ui.graphics.painter.Painter$drawLambda$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                this.this$0.onDraw((DrawScope) obj);
                return Unit.INSTANCE;
            }
        };
    }

    public boolean applyAlpha(float f) {
        return false;
    }

    public boolean applyColorFilter(ColorFilter colorFilter) {
        return false;
    }

    /* renamed from: draw-x_KDEd0, reason: not valid java name */
    public final void m564drawx_KDEd0(DrawScope drawScope, long j, float f, ColorFilter colorFilter) {
        if (this.alpha != f) {
            if (!applyAlpha(f)) {
                if (f == 1.0f) {
                    AndroidPaint androidPaint = this.layerPaint;
                    if (androidPaint != null) {
                        androidPaint.setAlpha(f);
                    }
                    this.useLayer = false;
                } else {
                    AndroidPaint androidPaint2 = this.layerPaint;
                    if (androidPaint2 == null) {
                        androidPaint2 = new AndroidPaint();
                        this.layerPaint = androidPaint2;
                    }
                    androidPaint2.setAlpha(f);
                    this.useLayer = true;
                }
            }
            this.alpha = f;
        }
        if (!Intrinsics.areEqual(this.colorFilter, colorFilter)) {
            if (!applyColorFilter(colorFilter)) {
                if (colorFilter == null) {
                    AndroidPaint androidPaint3 = this.layerPaint;
                    if (androidPaint3 != null) {
                        androidPaint3.setColorFilter(null);
                    }
                    this.useLayer = false;
                } else {
                    AndroidPaint androidPaint4 = this.layerPaint;
                    if (androidPaint4 == null) {
                        androidPaint4 = new AndroidPaint();
                        this.layerPaint = androidPaint4;
                    }
                    androidPaint4.setColorFilter(colorFilter);
                    this.useLayer = true;
                }
            }
            this.colorFilter = colorFilter;
        }
        LayoutDirection layoutDirection = drawScope.getLayoutDirection();
        if (this.layoutDirection != layoutDirection) {
            applyLayoutDirection(layoutDirection);
            this.layoutDirection = layoutDirection;
        }
        int i = (int) (j >> 32);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) - Float.intBitsToFloat(i);
        int i2 = (int) (j & 4294967295L);
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - Float.intBitsToFloat(i2);
        drawScope.getDrawContext().transform.inset(0.0f, 0.0f, fIntBitsToFloat, fIntBitsToFloat2);
        if (f > 0.0f) {
            try {
                if (Float.intBitsToFloat(i) > 0.0f && Float.intBitsToFloat(i2) > 0.0f) {
                    if (this.useLayer) {
                        Offset.Companion.getClass();
                        float fIntBitsToFloat3 = Float.intBitsToFloat(i);
                        float fIntBitsToFloat4 = Float.intBitsToFloat(i2);
                        long jFloatToRawIntBits = Float.floatToRawIntBits(fIntBitsToFloat3);
                        Size.Companion companion = Size.Companion;
                        Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(0L, (Float.floatToRawIntBits(fIntBitsToFloat4) & 4294967295L) | (jFloatToRawIntBits << 32));
                        Canvas canvas = drawScope.getDrawContext().getCanvas();
                        AndroidPaint androidPaint5 = this.layerPaint;
                        if (androidPaint5 == null) {
                            androidPaint5 = new AndroidPaint();
                            this.layerPaint = androidPaint5;
                        }
                        try {
                            canvas.saveLayer(rectM413Recttz77jQw, androidPaint5);
                            onDraw(drawScope);
                            canvas.restore();
                        } catch (Throwable th) {
                            canvas.restore();
                            throw th;
                        }
                    } else {
                        onDraw(drawScope);
                    }
                }
            } catch (Throwable th2) {
                drawScope.getDrawContext().transform.inset(-0.0f, -0.0f, -fIntBitsToFloat, -fIntBitsToFloat2);
                throw th2;
            }
        }
        drawScope.getDrawContext().transform.inset(-0.0f, -0.0f, -fIntBitsToFloat, -fIntBitsToFloat2);
    }

    /* renamed from: getIntrinsicSize-NH-jbRc */
    public abstract long mo563getIntrinsicSizeNHjbRc();

    public abstract void onDraw(DrawScope drawScope);

    public void applyLayoutDirection(LayoutDirection layoutDirection) {
    }
}
