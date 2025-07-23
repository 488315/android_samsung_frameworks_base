package android.graphics;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class BitmapRegionDecoder {
    private long mNativeBitmapRegionDecoder;
    private final Object mNativeLock = new Object();
    private boolean mRecycled = false;

    private static native void nativeClean(long j);

    private static native Bitmap nativeDecodeRegion(long j, int i, int i2, int i3, int i4, BitmapFactory.Options options, long j2, long j3);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native BitmapRegionDecoder nativeNewInstance(long j);

    private static native BitmapRegionDecoder nativeNewInstance(FileDescriptor fileDescriptor);

    private static native BitmapRegionDecoder nativeNewInstance(InputStream inputStream, byte[] bArr);

    private static native BitmapRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    @Deprecated
    public static BitmapRegionDecoder newInstance(byte[] bArr, int i, int i2, boolean z) throws IOException {
        return newInstance(bArr, i, i2);
    }

    public static BitmapRegionDecoder newInstance(byte[] bArr, int i, int i2) throws IOException {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(bArr, i, i2);
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(FileDescriptor fileDescriptor, boolean z) throws IOException {
        return nativeNewInstance(fileDescriptor);
    }

    public static BitmapRegionDecoder newInstance(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return nativeNewInstance(parcelFileDescriptor.getFileDescriptor());
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(InputStream inputStream, boolean z) throws IOException {
        return newInstance(inputStream);
    }

    public static BitmapRegionDecoder newInstance(InputStream inputStream) throws IOException {
        if (inputStream instanceof AssetManager.AssetInputStream) {
            return nativeNewInstance(((AssetManager.AssetInputStream) inputStream).getNativeAsset());
        }
        return nativeNewInstance(inputStream, new byte[16384]);
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(String str, boolean z) throws IOException {
        return newInstance(str);
    }

    public static BitmapRegionDecoder newInstance(String str) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                BitmapRegionDecoder newInstance = newInstance(fileInputStream2);
                try {
                    fileInputStream2.close();
                } catch (IOException unused) {
                }
                return newInstance;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private BitmapRegionDecoder(long j) {
        this.mNativeBitmapRegionDecoder = j;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        Bitmap nativeDecodeRegion;
        BitmapFactory.Options.validate(options);
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0 || rect.bottom <= 0 || rect.left >= getWidth() || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            nativeDecodeRegion = nativeDecodeRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options, BitmapFactory.Options.nativeInBitmap(options), BitmapFactory.Options.nativeColorSpace(options));
        }
        return nativeDecodeRegion;
    }

    public int getWidth() {
        int nativeGetWidth;
        synchronized (this.mNativeLock) {
            checkRecycled("getWidth called on recycled region decoder");
            nativeGetWidth = nativeGetWidth(this.mNativeBitmapRegionDecoder);
        }
        return nativeGetWidth;
    }

    public int getHeight() {
        int nativeGetHeight;
        synchronized (this.mNativeLock) {
            checkRecycled("getHeight called on recycled region decoder");
            nativeGetHeight = nativeGetHeight(this.mNativeBitmapRegionDecoder);
        }
        return nativeGetHeight;
    }

    public void recycle() {
        synchronized (this.mNativeLock) {
            if (!this.mRecycled) {
                nativeClean(this.mNativeBitmapRegionDecoder);
                this.mRecycled = true;
            }
        }
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    private void checkRecycled(String str) {
        if (this.mRecycled) {
            throw new IllegalStateException(str);
        }
    }

    protected void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }
}
