package androidx.compose.ui.platform;

import android.graphics.Outline;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class OutlineResolver {
    public boolean cacheIsDirty;
    public final Outline cachedOutline;
    public AndroidPath cachedRrectPath;
    public androidx.compose.ui.graphics.Outline outline;
    public boolean outlineNeeded;
    public Path outlinePath;
    public long rectSize;
    public long rectTopLeft;
    public float roundedCornerRadius;
    public Path tmpPath;
    public RoundRect tmpRoundRect;
    public boolean usePathForClip;

    public OutlineResolver() {
        Outline outline = new Outline();
        outline.setAlpha(1.0f);
        this.cachedOutline = outline;
        Offset.Companion.getClass();
        this.rectTopLeft = 0L;
        Size.Companion.getClass();
        this.rectSize = 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void clipToOutline(Canvas canvas) {
        updateCache();
        Path path = this.outlinePath;
        if (path != null) {
            Canvas.m454clipPathmtrdDE$default(canvas, path);
            return;
        }
        float f = this.roundedCornerRadius;
        if (f <= 0.0f) {
            float fIntBitsToFloat = Float.intBitsToFloat((int) (this.rectTopLeft >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L));
            float fIntBitsToFloat3 = Float.intBitsToFloat((int) (this.rectSize >> 32)) + Float.intBitsToFloat((int) (this.rectTopLeft >> 32));
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (this.rectSize & 4294967295L)) + Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L));
            ClipOp.Companion.getClass();
            canvas.mo426clipRectN_I0leg(fIntBitsToFloat, fIntBitsToFloat2, fIntBitsToFloat3, fIntBitsToFloat4, ClipOp.Intersect);
            return;
        }
        Path Path = this.tmpPath;
        RoundRect roundRect = this.tmpRoundRect;
        if (Path != null) {
            long j = this.rectTopLeft;
            long j2 = this.rectSize;
            if (roundRect == null || !RoundRectKt.isSimple(roundRect)) {
                float fIntBitsToFloat5 = Float.intBitsToFloat((int) (this.rectTopLeft >> 32));
                float fIntBitsToFloat6 = Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L));
                float fIntBitsToFloat7 = Float.intBitsToFloat((int) (this.rectSize >> 32)) + Float.intBitsToFloat((int) (this.rectTopLeft >> 32));
                float fIntBitsToFloat8 = Float.intBitsToFloat((int) (this.rectSize & 4294967295L)) + Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L));
                float f2 = this.roundedCornerRadius;
                long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) << 32) | (4294967295L & Float.floatToRawIntBits(f2));
                CornerRadius.Companion companion = CornerRadius.Companion;
                RoundRect roundRectM414RoundRectgG7oq9Y = RoundRectKt.m414RoundRectgG7oq9Y(fIntBitsToFloat5, fIntBitsToFloat6, fIntBitsToFloat7, fIntBitsToFloat8, jFloatToRawIntBits);
                if (Path == null) {
                    Path = AndroidPath_androidKt.Path();
                } else {
                    ((AndroidPath) Path).reset();
                }
                Path.addRoundRect$default(Path, roundRectM414RoundRectgG7oq9Y);
                this.tmpRoundRect = roundRectM414RoundRectgG7oq9Y;
                this.tmpPath = Path;
            } else {
                int i = (int) (j >> 32);
                if (roundRect.left == Float.intBitsToFloat(i)) {
                    int i2 = (int) (j & 4294967295L);
                    if (roundRect.top == Float.intBitsToFloat(i2)) {
                        if (roundRect.right == Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i)) {
                            if (roundRect.bottom != Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i2) || Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius >> 32)) != f) {
                            }
                        }
                    }
                }
            }
        }
        Canvas.m454clipPathmtrdDE$default(canvas, Path);
    }

    public final Outline getAndroidOutline() {
        updateCache();
        if (this.outlineNeeded) {
            return this.cachedOutline;
        }
        return null;
    }

    /* renamed from: isInOutline-k-4lQ0M, reason: not valid java name */
    public final boolean m709isInOutlinek4lQ0M(long j) {
        androidx.compose.ui.graphics.Outline outline;
        if (this.outlineNeeded && (outline = this.outline) != null) {
            return ShapeContainingUtilKt.isInOutline(outline, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        }
        return true;
    }

    /* renamed from: update-S_szKao, reason: not valid java name */
    public final boolean m710updateS_szKao(androidx.compose.ui.graphics.Outline outline, float f, boolean z, float f2, long j) {
        this.cachedOutline.setAlpha(f);
        boolean zAreEqual = Intrinsics.areEqual(this.outline, outline);
        boolean z2 = !zAreEqual;
        if (!zAreEqual) {
            this.outline = outline;
            this.cacheIsDirty = true;
        }
        this.rectSize = j;
        boolean z3 = outline != null && (z || f2 > 0.0f);
        if (this.outlineNeeded != z3) {
            this.outlineNeeded = z3;
            this.cacheIsDirty = true;
        }
        return z2;
    }

    public final void updateCache() {
        if (this.cacheIsDirty) {
            Offset.Companion.getClass();
            this.rectTopLeft = 0L;
            this.roundedCornerRadius = 0.0f;
            this.outlinePath = null;
            this.cacheIsDirty = false;
            this.usePathForClip = false;
            androidx.compose.ui.graphics.Outline outline = this.outline;
            if (outline == null || !this.outlineNeeded || Float.intBitsToFloat((int) (this.rectSize >> 32)) <= 0.0f || Float.intBitsToFloat((int) (this.rectSize & 4294967295L)) <= 0.0f) {
                this.cachedOutline.setEmpty();
                return;
            }
            if (outline instanceof Outline.Rectangle) {
                Rect rect = ((Outline.Rectangle) outline).rect;
                long jFloatToRawIntBits = Float.floatToRawIntBits(rect.left);
                float f = rect.top;
                this.rectTopLeft = (jFloatToRawIntBits << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
                float f2 = rect.right;
                float f3 = rect.left;
                float f4 = rect.bottom;
                long jFloatToRawIntBits2 = Float.floatToRawIntBits(f2 - f3);
                Size.Companion companion = Size.Companion;
                this.rectSize = (Float.floatToRawIntBits(f4 - f) & 4294967295L) | (jFloatToRawIntBits2 << 32);
                this.cachedOutline.setRect(Math.round(f3), Math.round(f), Math.round(f2), Math.round(f4));
                return;
            }
            if (!(outline instanceof Outline.Rounded)) {
                if (outline instanceof Outline.Generic) {
                    OutlineVerificationHelper outlineVerificationHelper = OutlineVerificationHelper.INSTANCE;
                    android.graphics.Outline outline2 = this.cachedOutline;
                    Path path = ((Outline.Generic) outline).path;
                    outlineVerificationHelper.setPath(outline2, path);
                    this.usePathForClip = !this.cachedOutline.canClip();
                    this.outlinePath = path;
                    return;
                }
                return;
            }
            RoundRect roundRect = ((Outline.Rounded) outline).roundRect;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius >> 32));
            float f5 = roundRect.left;
            long jFloatToRawIntBits3 = Float.floatToRawIntBits(f5);
            float f6 = roundRect.top;
            this.rectTopLeft = (jFloatToRawIntBits3 << 32) | (Float.floatToRawIntBits(f6) & 4294967295L);
            float width = roundRect.getWidth();
            float height = roundRect.getHeight();
            long jFloatToRawIntBits4 = Float.floatToRawIntBits(width);
            Size.Companion companion2 = Size.Companion;
            this.rectSize = (Float.floatToRawIntBits(height) & 4294967295L) | (jFloatToRawIntBits4 << 32);
            if (RoundRectKt.isSimple(roundRect)) {
                this.cachedOutline.setRoundRect(Math.round(f5), Math.round(f6), Math.round(roundRect.right), Math.round(roundRect.bottom), fIntBitsToFloat);
                this.roundedCornerRadius = fIntBitsToFloat;
                return;
            }
            AndroidPath androidPathPath = this.cachedRrectPath;
            if (androidPathPath == null) {
                androidPathPath = AndroidPath_androidKt.Path();
                this.cachedRrectPath = androidPathPath;
            }
            androidPathPath.reset();
            Path.addRoundRect$default(androidPathPath, roundRect);
            OutlineVerificationHelper.INSTANCE.setPath(this.cachedOutline, androidPathPath);
            this.usePathForClip = !this.cachedOutline.canClip();
            this.outlinePath = androidPathPath;
        }
    }
}
