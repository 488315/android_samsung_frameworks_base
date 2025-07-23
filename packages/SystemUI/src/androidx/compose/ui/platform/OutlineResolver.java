package androidx.compose.ui.platform;

import android.graphics.Outline;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0073, code lost:
    
        if (java.lang.Float.intBitsToFloat((int) (r5.topLeftCornerRadius >> 32)) == r0) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void clipToOutline(androidx.compose.ui.graphics.Canvas r14) {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.OutlineResolver.clipToOutline(androidx.compose.ui.graphics.Canvas):void");
    }

    public final Outline getAndroidOutline() {
        updateCache();
        if (this.outlineNeeded) {
            return this.cachedOutline;
        }
        return null;
    }

    /* renamed from: isInOutline-k-4lQ0M, reason: not valid java name */
    public final boolean m707isInOutlinek4lQ0M(long j) {
        androidx.compose.ui.graphics.Outline outline;
        if (this.outlineNeeded && (outline = this.outline) != null) {
            return ShapeContainingUtilKt.isInOutline(outline, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        }
        return true;
    }

    /* renamed from: update-S_szKao, reason: not valid java name */
    public final boolean m708updateS_szKao(androidx.compose.ui.graphics.Outline outline, float f, boolean z, float f2, long j) {
        this.cachedOutline.setAlpha(f);
        boolean areEqual = Intrinsics.areEqual(this.outline, outline);
        boolean z2 = !areEqual;
        if (!areEqual) {
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
                long floatToRawIntBits = Float.floatToRawIntBits(rect.left);
                float f = rect.top;
                this.rectTopLeft = (floatToRawIntBits << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
                float f2 = rect.right;
                float f3 = rect.left;
                float f4 = rect.bottom;
                long floatToRawIntBits2 = Float.floatToRawIntBits(f2 - f3);
                Size.Companion companion = Size.Companion;
                this.rectSize = (Float.floatToRawIntBits(f4 - f) & 4294967295L) | (floatToRawIntBits2 << 32);
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
            float intBitsToFloat = Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius >> 32));
            float f5 = roundRect.left;
            long floatToRawIntBits3 = Float.floatToRawIntBits(f5);
            float f6 = roundRect.top;
            this.rectTopLeft = (floatToRawIntBits3 << 32) | (Float.floatToRawIntBits(f6) & 4294967295L);
            float width = roundRect.getWidth();
            float height = roundRect.getHeight();
            long floatToRawIntBits4 = Float.floatToRawIntBits(width);
            Size.Companion companion2 = Size.Companion;
            this.rectSize = (Float.floatToRawIntBits(height) & 4294967295L) | (floatToRawIntBits4 << 32);
            if (RoundRectKt.isSimple(roundRect)) {
                this.cachedOutline.setRoundRect(Math.round(f5), Math.round(f6), Math.round(roundRect.right), Math.round(roundRect.bottom), intBitsToFloat);
                this.roundedCornerRadius = intBitsToFloat;
                return;
            }
            AndroidPath androidPath = this.cachedRrectPath;
            if (androidPath == null) {
                androidPath = AndroidPath_androidKt.Path();
                this.cachedRrectPath = androidPath;
            }
            androidPath.reset();
            Path.addRoundRect$default(androidPath, roundRect);
            OutlineVerificationHelper.INSTANCE.setPath(this.cachedOutline, androidPath);
            this.usePathForClip = !this.cachedOutline.canClip();
            this.outlinePath = androidPath;
        }
    }
}
