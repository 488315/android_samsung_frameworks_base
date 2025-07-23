package android.graphics;

/* loaded from: classes.dex */
public class NinePatch {
    private final Bitmap mBitmap;
    public long mNativeChunk;
    private Paint mPaint;
    private String mSrcName;

    public static native boolean isNinePatchChunk(byte[] bArr);

    private static native void nativeFinalize(long j);

    private static native long nativeGetTransparentRegion(long j, long j2, Rect rect);

    private static native long validateNinePatchChunk(byte[] bArr);

    public static class InsetStruct {
        public final Rect opticalRect;
        public final float outlineAlpha;
        public final float outlineRadius;
        public final Rect outlineRect;

        InsetStruct(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f, int i9, float f2) {
            Rect rect = new Rect(i, i2, i3, i4);
            this.opticalRect = rect;
            rect.scale(f2);
            this.outlineRect = scaleInsets(i5, i6, i7, i8, f2);
            this.outlineRadius = f * f2;
            this.outlineAlpha = i9 / 255.0f;
        }

        public static Rect scaleInsets(int i, int i2, int i3, int i4, float f) {
            if (f == 1.0f) {
                return new Rect(i, i2, i3, i4);
            }
            Rect rect = new Rect();
            rect.left = (int) Math.ceil(i * f);
            rect.top = (int) Math.ceil(i2 * f);
            rect.right = (int) Math.ceil(i3 * f);
            rect.bottom = (int) Math.ceil(i4 * f);
            return rect;
        }
    }

    public NinePatch(Bitmap bitmap, byte[] bArr) {
        this(bitmap, bArr, null);
    }

    public NinePatch(Bitmap bitmap, byte[] bArr, String str) {
        this.mBitmap = bitmap;
        this.mSrcName = str;
        this.mNativeChunk = validateNinePatchChunk(bArr);
    }

    protected void finalize() throws Throwable {
        try {
            long j = this.mNativeChunk;
            if (j != 0) {
                nativeFinalize(j);
                this.mNativeChunk = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public String getName() {
        return this.mSrcName;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void draw(Canvas canvas, RectF rectF) {
        canvas.drawPatch(this, rectF, this.mPaint);
    }

    public void draw(Canvas canvas, Rect rect) {
        canvas.drawPatch(this, rect, this.mPaint);
    }

    public void draw(Canvas canvas, Rect rect, Paint paint) {
        canvas.drawPatch(this, rect, paint);
    }

    public int getDensity() {
        return this.mBitmap.mDensity;
    }

    public int getWidth() {
        return this.mBitmap.getWidth();
    }

    public int getHeight() {
        return this.mBitmap.getHeight();
    }

    public final boolean hasAlpha() {
        return this.mBitmap.hasAlpha();
    }

    public final Region getTransparentRegion(Rect rect) {
        long nativeGetTransparentRegion = nativeGetTransparentRegion(this.mBitmap.getNativeInstance(), this.mNativeChunk, rect);
        if (nativeGetTransparentRegion != 0) {
            return new Region(nativeGetTransparentRegion);
        }
        return null;
    }
}
