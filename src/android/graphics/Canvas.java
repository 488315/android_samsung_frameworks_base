package android.graphics;

import android.graphics.PorterDuff;
import android.graphics.Region;
import android.graphics.fonts.Font;
import android.graphics.text.MeasuredText;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Canvas extends BaseCanvas {
    public static final int ALL_SAVE_FLAG = 31;
    public static final int CLIP_SAVE_FLAG = 2;
    public static final int CLIP_TO_LAYER_SAVE_FLAG = 16;
    public static final int FULL_COLOR_LAYER_SAVE_FLAG = 8;
    public static final int HAS_ALPHA_LAYER_SAVE_FLAG = 4;
    public static final int MATRIX_SAVE_FLAG = 1;
    private static final int MAXIMUM_BITMAP_SIZE = 32766;
    private static boolean sCompatibilityRestore = false;
    private static boolean sCompatibilitySetBitmap = false;
    private static int sCompatibilityVersion;
    private Bitmap mBitmap;
    private DrawFilter mDrawFilter;
    private Runnable mFinalizer;

    public enum EdgeType {
        BW,
        AA
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Saveflags {
    }

    @CriticalNative
    private static native boolean nClipPath(long j, long j2, int i);

    @CriticalNative
    private static native boolean nClipRect(long j, float f, float f2, float f3, float f4, int i);

    @CriticalNative
    private static native void nClipShader(long j, long j2, int i);

    @CriticalNative
    private static native void nConcat(long j, long j2);

    @FastNative
    private static native void nConcat(long j, float[] fArr);

    private static native void nFreeCaches();

    private static native void nFreeTextLayoutCaches();

    @FastNative
    private static native boolean nGetClipBounds(long j, Rect rect);

    @CriticalNative
    private static native int nGetHeight(long j);

    @CriticalNative
    private static native void nGetMatrix(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    @CriticalNative
    private static native int nGetSaveCount(long j);

    @CriticalNative
    private static native int nGetWidth(long j);

    @FastNative
    private static native long nInitRaster(long j);

    @CriticalNative
    private static native boolean nIsHighContrastText(long j);

    @CriticalNative
    private static native boolean nIsOpaque(long j);

    @CriticalNative
    private static native boolean nQuickReject(long j, float f, float f2, float f3, float f4);

    @CriticalNative
    private static native boolean nQuickReject(long j, long j2);

    @CriticalNative
    private static native boolean nRestore(long j);

    @CriticalNative
    private static native void nRestoreToCount(long j, int i);

    @CriticalNative
    private static native void nRestoreUnclippedLayer(long j, int i, long j2);

    @CriticalNative
    private static native void nRotate(long j, float f);

    @CriticalNative
    private static native int nSave(long j, int i);

    @CriticalNative
    private static native int nSaveLayer(long j, float f, float f2, float f3, float f4, long j2);

    @CriticalNative
    private static native int nSaveLayerAlpha(long j, float f, float f2, float f3, float f4, int i);

    @CriticalNative
    private static native int nSaveUnclippedLayer(long j, int i, int i2, int i3, int i4);

    @CriticalNative
    private static native void nScale(long j, float f, float f2);

    @FastNative
    private static native void nSetBitmap(long j, long j2);

    private static native void nSetCompatibilityVersion(int i);

    @CriticalNative
    private static native void nSetDrawFilter(long j, long j2);

    @CriticalNative
    private static native void nSetMatrix(long j, long j2);

    @CriticalNative
    private static native void nSkew(long j, float f, float f2);

    @CriticalNative
    private static native void nTranslate(long j, float f, float f2);

    @Deprecated
    public boolean clipRegion(Region region) {
        return false;
    }

    @Deprecated
    public boolean clipRegion(Region region, Region.Op op) {
        return false;
    }

    public void disableZ() {
    }

    public void enableZ() {
    }

    public int getMaximumBitmapHeight() {
        return 32766;
    }

    public int getMaximumBitmapWidth() {
        return 32766;
    }

    @Override // android.graphics.BaseCanvas
    public boolean isHardwareAccelerated() {
        return false;
    }

    public long getNativeCanvasWrapper() {
        return this.mNativeCanvasWrapper;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Canvas.class.getClassLoader(), Canvas.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public Canvas() {
        if (!isHardwareAccelerated()) {
            this.mNativeCanvasWrapper = nInitRaster(0L);
            this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        } else {
            this.mFinalizer = null;
        }
    }

    public Canvas(Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            throw new IllegalStateException("Immutable bitmap passed to Canvas constructor");
        }
        throwIfCannotDraw(bitmap);
        bitmap.setGainmap(null);
        this.mNativeCanvasWrapper = nInitRaster(bitmap.getNativeInstance());
        this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        this.mBitmap = bitmap;
        this.mDensity = bitmap.mDensity;
    }

    public Canvas(long j) {
        if (j == 0) {
            throw new IllegalStateException();
        }
        this.mNativeCanvasWrapper = j;
        this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        this.mDensity = Bitmap.getDefaultDensity();
    }

    public boolean isHighContrastTextEnabled() {
        return nIsHighContrastText(this.mNativeCanvasWrapper);
    }

    public void setBitmap(Bitmap bitmap) {
        if (isHardwareAccelerated()) {
            throw new RuntimeException("Can't set a bitmap device on a HW accelerated canvas");
        }
        Matrix matrix = (bitmap == null || !sCompatibilitySetBitmap) ? null : getMatrix();
        if (bitmap == null) {
            nSetBitmap(this.mNativeCanvasWrapper, 0L);
            this.mDensity = 0;
        } else {
            if (!bitmap.isMutable()) {
                throw new IllegalStateException();
            }
            throwIfCannotDraw(bitmap);
            bitmap.setGainmap(null);
            nSetBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance());
            this.mDensity = bitmap.mDensity;
        }
        if (matrix != null) {
            setMatrix(matrix);
        }
        this.mBitmap = bitmap;
    }

    public boolean isOpaque() {
        return nIsOpaque(this.mNativeCanvasWrapper);
    }

    public int getWidth() {
        return nGetWidth(this.mNativeCanvasWrapper);
    }

    public int getHeight() {
        return nGetHeight(this.mNativeCanvasWrapper);
    }

    public int getDensity() {
        return this.mDensity;
    }

    public void setDensity(int i) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.setDensity(i);
        }
        this.mDensity = i;
    }

    public void setScreenDensity(int i) {
        this.mScreenDensity = i;
    }

    private static void checkValidSaveFlags(int i) {
        if (sCompatibilityVersion >= 28 && i != 31) {
            throw new IllegalArgumentException("Invalid Layer Save Flag - only ALL_SAVE_FLAGS is allowed");
        }
    }

    public int save() {
        return nSave(this.mNativeCanvasWrapper, 3);
    }

    public int save(int i) {
        return nSave(this.mNativeCanvasWrapper, i);
    }

    public int saveLayer(RectF rectF, Paint paint, int i) {
        if (rectF == null) {
            rectF = new RectF(getClipBounds());
        }
        checkValidSaveFlags(i);
        return saveLayer(rectF.left, rectF.top, rectF.right, rectF.bottom, paint, 31);
    }

    public int saveLayer(RectF rectF, Paint paint) {
        return saveLayer(rectF, paint, 31);
    }

    public int saveUnclippedLayer(int i, int i2, int i3, int i4) {
        return nSaveUnclippedLayer(this.mNativeCanvasWrapper, i, i2, i3, i4);
    }

    public void restoreUnclippedLayer(int i, Paint paint) {
        nRestoreUnclippedLayer(this.mNativeCanvasWrapper, i, paint.getNativeInstance());
    }

    public int saveLayer(float f, float f2, float f3, float f4, Paint paint, int i) {
        checkValidSaveFlags(i);
        return nSaveLayer(this.mNativeCanvasWrapper, f, f2, f3, f4, paint != null ? paint.getNativeInstance() : 0L);
    }

    public int saveLayer(float f, float f2, float f3, float f4, Paint paint) {
        return saveLayer(f, f2, f3, f4, paint, 31);
    }

    public int saveLayerAlpha(RectF rectF, int i, int i2) {
        if (rectF == null) {
            rectF = new RectF(getClipBounds());
        }
        checkValidSaveFlags(i2);
        return saveLayerAlpha(rectF.left, rectF.top, rectF.right, rectF.bottom, i, 31);
    }

    public int saveLayerAlpha(RectF rectF, int i) {
        return saveLayerAlpha(rectF, i, 31);
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int i, int i2) {
        checkValidSaveFlags(i2);
        return nSaveLayerAlpha(this.mNativeCanvasWrapper, f, f2, f3, f4, Math.min(255, Math.max(0, i)));
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int i) {
        return saveLayerAlpha(f, f2, f3, f4, i, 31);
    }

    public void restore() {
        if (nRestore(this.mNativeCanvasWrapper)) {
            return;
        }
        if (!sCompatibilityRestore || !isHardwareAccelerated()) {
            throw new IllegalStateException("Underflow in restore - more restores than saves");
        }
    }

    public int getSaveCount() {
        return nGetSaveCount(this.mNativeCanvasWrapper);
    }

    public void restoreToCount(int i) {
        if (i < 1) {
            if (!sCompatibilityRestore || !isHardwareAccelerated()) {
                throw new IllegalArgumentException("Underflow in restoreToCount - more restores than saves");
            }
            i = 1;
        }
        nRestoreToCount(this.mNativeCanvasWrapper, i);
    }

    public void translate(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        nTranslate(this.mNativeCanvasWrapper, f, f2);
    }

    public void scale(float f, float f2) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        nScale(this.mNativeCanvasWrapper, f, f2);
    }

    public final void scale(float f, float f2, float f3, float f4) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        translate(f3, f4);
        scale(f, f2);
        translate(-f3, -f4);
    }

    public void rotate(float f) {
        if (f == 0.0f) {
            return;
        }
        nRotate(this.mNativeCanvasWrapper, f);
    }

    public final void rotate(float f, float f2, float f3) {
        if (f == 0.0f) {
            return;
        }
        translate(f2, f3);
        rotate(f);
        translate(-f2, -f3);
    }

    public void skew(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        nSkew(this.mNativeCanvasWrapper, f, f2);
    }

    public void concat(Matrix matrix) {
        if (matrix != null) {
            nConcat(this.mNativeCanvasWrapper, matrix.ni());
        }
    }

    public void concat(Matrix44 matrix44) {
        if (matrix44 != null) {
            nConcat(this.mNativeCanvasWrapper, matrix44.mBackingArray);
        }
    }

    public void setMatrix(Matrix matrix) {
        nSetMatrix(this.mNativeCanvasWrapper, matrix == null ? 0L : matrix.ni());
    }

    @Deprecated
    public void getMatrix(Matrix matrix) {
        nGetMatrix(this.mNativeCanvasWrapper, matrix.ni());
    }

    @Deprecated
    public final Matrix getMatrix() {
        Matrix matrix = new Matrix();
        getMatrix(matrix);
        return matrix;
    }

    private static void checkValidClipOp(Region.Op op) {
        if (sCompatibilityVersion >= 28 && op != Region.Op.INTERSECT && op != Region.Op.DIFFERENCE) {
            throw new IllegalArgumentException("Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed");
        }
    }

    @Deprecated
    public boolean clipRect(RectF rectF, Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, op.nativeInt);
    }

    @Deprecated
    public boolean clipRect(Rect rect, Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, op.nativeInt);
    }

    public boolean clipRectUnion(Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.UNION.nativeInt);
    }

    public boolean clipRect(RectF rectF) {
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(RectF rectF) {
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipRect(Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.DIFFERENCE.nativeInt);
    }

    @Deprecated
    public boolean clipRect(float f, float f2, float f3, float f4, Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, op.nativeInt);
    }

    public boolean clipRect(float f, float f2, float f3, float f4) {
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(float f, float f2, float f3, float f4) {
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipRect(int i, int i2, int i3, int i4) {
        return nClipRect(this.mNativeCanvasWrapper, i, i2, i3, i4, Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(int i, int i2, int i3, int i4) {
        return nClipRect(this.mNativeCanvasWrapper, i, i2, i3, i4, Region.Op.DIFFERENCE.nativeInt);
    }

    @Deprecated
    public boolean clipPath(Path path, Region.Op op) {
        checkValidClipOp(op);
        return nClipPath(this.mNativeCanvasWrapper, path.readOnlyNI(), op.nativeInt);
    }

    public boolean clipPath(Path path) {
        return clipPath(path, Region.Op.INTERSECT);
    }

    public boolean clipOutPath(Path path) {
        return clipPath(path, Region.Op.DIFFERENCE);
    }

    public void clipShader(Shader shader) {
        nClipShader(this.mNativeCanvasWrapper, shader.getNativeInstance(), Region.Op.INTERSECT.nativeInt);
    }

    public void clipOutShader(Shader shader) {
        nClipShader(this.mNativeCanvasWrapper, shader.getNativeInstance(), Region.Op.DIFFERENCE.nativeInt);
    }

    public DrawFilter getDrawFilter() {
        return this.mDrawFilter;
    }

    public void setDrawFilter(DrawFilter drawFilter) {
        long j = drawFilter != null ? drawFilter.mNativeInt : 0L;
        this.mDrawFilter = drawFilter;
        nSetDrawFilter(this.mNativeCanvasWrapper, j);
    }

    @Deprecated
    public boolean quickReject(RectF rectF, EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public boolean quickReject(RectF rectF) {
        return nQuickReject(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @Deprecated
    public boolean quickReject(Path path, EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, path.readOnlyNI());
    }

    public boolean quickReject(Path path) {
        return nQuickReject(this.mNativeCanvasWrapper, path.readOnlyNI());
    }

    @Deprecated
    public boolean quickReject(float f, float f2, float f3, float f4, EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, f, f2, f3, f4);
    }

    public boolean quickReject(float f, float f2, float f3, float f4) {
        return nQuickReject(this.mNativeCanvasWrapper, f, f2, f3, f4);
    }

    public boolean getClipBounds(Rect rect) {
        return nGetClipBounds(this.mNativeCanvasWrapper, rect);
    }

    public final Rect getClipBounds() {
        Rect rect = new Rect();
        getClipBounds(rect);
        return rect;
    }

    public void drawPicture(Picture picture) {
        picture.endRecording();
        int save = save();
        picture.draw(this);
        restoreToCount(save);
    }

    public void drawPicture(Picture picture, RectF rectF) {
        save();
        translate(rectF.left, rectF.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rectF.width() / picture.getWidth(), rectF.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    public void drawPicture(Picture picture, Rect rect) {
        save();
        translate(rect.left, rect.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rect.width() / picture.getWidth(), rect.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    public enum VertexMode {
        TRIANGLES(0),
        TRIANGLE_STRIP(1),
        TRIANGLE_FAN(2);

        final int nativeInt;

        VertexMode(int i) {
            this.nativeInt = i;
        }
    }

    public void release() {
        this.mNativeCanvasWrapper = 0L;
        Runnable runnable = this.mFinalizer;
        if (runnable != null) {
            runnable.run();
            this.mFinalizer = null;
        }
    }

    public static void freeCaches() {
        nFreeCaches();
    }

    public static void freeTextLayoutCaches() {
        nFreeTextLayoutCaches();
    }

    static void setCompatibilityVersion(int i) {
        sCompatibilityVersion = i;
        sCompatibilityRestore = i < 23;
        sCompatibilitySetBitmap = i < 26;
        nSetCompatibilityVersion(i);
    }

    @Override // android.graphics.BaseCanvas
    public void drawArc(RectF rectF, float f, float f2, boolean z, Paint paint) {
        super.drawArc(rectF, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean z, Paint paint) {
        super.drawArc(f, f2, f3, f4, f5, f6, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawARGB(int i, int i2, int i3, int i4) {
        super.drawARGB(i, i2, i3, i4);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(Bitmap bitmap, float f, float f2, Paint paint) {
        super.drawBitmap(bitmap, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(Bitmap bitmap, Rect rect, RectF rectF, Paint paint) {
        super.drawBitmap(bitmap, rect, rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(Bitmap bitmap, Rect rect, Rect rect2, Paint paint) {
        super.drawBitmap(bitmap, rect, rect2, paint);
    }

    @Override // android.graphics.BaseCanvas
    @Deprecated
    public void drawBitmap(int[] iArr, int i, int i2, float f, float f2, int i3, int i4, boolean z, Paint paint) {
        super.drawBitmap(iArr, i, i2, f, f2, i3, i4, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    @Deprecated
    public void drawBitmap(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, Paint paint) {
        super.drawBitmap(iArr, i, i2, i3, i4, i5, i6, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        super.drawBitmap(bitmap, matrix, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmapMesh(Bitmap bitmap, int i, int i2, float[] fArr, int i3, int[] iArr, int i4, Paint paint) {
        super.drawBitmapMesh(bitmap, i, i2, fArr, i3, iArr, i4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawCircle(float f, float f2, float f3, Paint paint) {
        super.drawCircle(f, f2, f3, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i) {
        super.drawColor(i);
    }

    public void drawColor(long j) {
        super.drawColor(j, BlendMode.SRC_OVER);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i, PorterDuff.Mode mode) {
        super.drawColor(i, mode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i, BlendMode blendMode) {
        super.drawColor(i, blendMode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(long j, BlendMode blendMode) {
        super.drawColor(j, blendMode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLine(float f, float f2, float f3, float f4, Paint paint) {
        super.drawLine(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLines(float[] fArr, int i, int i2, Paint paint) {
        super.drawLines(fArr, i, i2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLines(float[] fArr, Paint paint) {
        super.drawLines(fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawOval(RectF rectF, Paint paint) {
        super.drawOval(rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawOval(float f, float f2, float f3, float f4, Paint paint) {
        super.drawOval(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPaint(Paint paint) {
        super.drawPaint(paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPatch(NinePatch ninePatch, Rect rect, Paint paint) {
        super.drawPatch(ninePatch, rect, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPatch(NinePatch ninePatch, RectF rectF, Paint paint) {
        super.drawPatch(ninePatch, rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPath(Path path, Paint paint) {
        super.drawPath(path, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRegion(Region region, Paint paint) {
        super.drawRegion(region, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoint(float f, float f2, Paint paint) {
        super.drawPoint(f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoints(float[] fArr, int i, int i2, Paint paint) {
        super.drawPoints(fArr, i, i2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoints(float[] fArr, Paint paint) {
        super.drawPoints(fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    @Deprecated
    public void drawPosText(char[] cArr, int i, int i2, float[] fArr, Paint paint) {
        super.drawPosText(cArr, i, i2, fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    @Deprecated
    public void drawPosText(String str, float[] fArr, Paint paint) {
        super.drawPosText(str, fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(RectF rectF, Paint paint) {
        super.drawRect(rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(Rect rect, Paint paint) {
        super.drawRect(rect, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(float f, float f2, float f3, float f4, Paint paint) {
        super.drawRect(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRGB(int i, int i2, int i3) {
        super.drawRGB(i, i2, i3);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRoundRect(RectF rectF, float f, float f2, Paint paint) {
        super.drawRoundRect(rectF, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        super.drawRoundRect(f, f2, f3, f4, f5, f6, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawDoubleRoundRect(RectF rectF, float f, float f2, RectF rectF2, float f3, float f4, Paint paint) {
        super.drawDoubleRoundRect(rectF, f, f2, rectF2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawDoubleRoundRect(RectF rectF, float[] fArr, RectF rectF2, float[] fArr2, Paint paint) {
        super.drawDoubleRoundRect(rectF, fArr, rectF2, fArr2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawGlyphs(int[] iArr, int i, float[] fArr, int i2, int i3, Font font, Paint paint) {
        super.drawGlyphs(iArr, i, fArr, i2, i3, font, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(char[] cArr, int i, int i2, float f, float f2, Paint paint) {
        super.drawText(cArr, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(String str, float f, float f2, Paint paint) {
        super.drawText(str, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(String str, int i, int i2, float f, float f2, Paint paint) {
        super.drawText(str, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(CharSequence charSequence, int i, int i2, float f, float f2, Paint paint) {
        super.drawText(charSequence, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextOnPath(char[] cArr, int i, int i2, Path path, float f, float f2, Paint paint) {
        super.drawTextOnPath(cArr, i, i2, path, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextOnPath(String str, Path path, float f, float f2, Paint paint) {
        super.drawTextOnPath(str, path, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        super.drawTextRun(cArr, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(CharSequence charSequence, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        super.drawTextRun(charSequence, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(MeasuredText measuredText, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        super.drawTextRun(measuredText, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawVertices(VertexMode vertexMode, int i, float[] fArr, int i2, float[] fArr2, int i3, int[] iArr, int i4, short[] sArr, int i5, int i6, Paint paint) {
        super.drawVertices(vertexMode, i, fArr, i2, fArr2, i3, iArr, i4, sArr, i5, i6, paint);
    }

    public void drawRenderNode(RenderNode renderNode) {
        throw new IllegalArgumentException("Software rendering doesn't support drawRenderNode");
    }
}
