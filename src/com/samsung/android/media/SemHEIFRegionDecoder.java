package com.samsung.android.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated(forRemoval = true, since = "15.0")
/* loaded from: classes6.dex */
public class SemHEIFRegionDecoder {
    private static final String TAG = "SemHEIFRegionDecoder";
    private static boolean mLibraryLoaded = false;
    private long mNativeBitmapRegionDecoder;
    private int mWidth = 0;
    private int mHeight = 0;
    private final Object mNativeLock = new Object();
    private boolean mRecycled = false;

    private static native void nativeClean(long j);

    private static native Bitmap nativeDecodeRegion(long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native SemHEIFRegionDecoder nativeNewInstance(String str);

    private static native SemHEIFRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    static {
        loadLibrary();
    }

    private static void loadLibrary() {
        if (mLibraryLoaded) {
            return;
        }
        try {
            System.loadLibrary("heifregiondec_jni");
            mLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Unable to load the native library : " + e);
        }
    }

    public static SemHEIFRegionDecoder newInstance(String str) throws IOException {
        if (str == null) {
            throw new IOException("pathName is null");
        }
        return nativeNewInstance(str);
    }

    public static SemHEIFRegionDecoder newInstance(byte[] bArr, int i, int i2, boolean z) throws IOException {
        if (bArr == null) {
            throw new IOException("data is null");
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(bArr, i, i2);
    }

    public static SemHEIFRegionDecoder newInstance(FileDescriptor fileDescriptor, boolean z) throws IOException {
        if (fileDescriptor == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        try {
            SemHEIFRegionDecoder newInstance = newInstance(fileInputStream, z);
            fileInputStream.close();
            return newInstance;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static SemHEIFRegionDecoder newInstance(InputStream inputStream, boolean z) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return newInstance(byteArray, 0, byteArray.length, z);
            }
        }
    }

    private SemHEIFRegionDecoder(long j) {
        this.mNativeBitmapRegionDecoder = j;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0 || rect.bottom <= 0 || rect.left >= getWidth() || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            if (options != null) {
                int i = options.inSampleSize;
                if (i == 0) {
                    i = 1;
                }
                options.inSampleSize = i;
                int width = ((rect.width() + i) - 1) / i;
                int height = ((rect.height() + i) - 1) / i;
                if (options.inBitmap != null && (options.inBitmap.getWidth() != width || options.inBitmap.getHeight() != height)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return options.inBitmap;
                }
            }
            return nativeDecodeRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options);
        }
    }

    public int getWidth() {
        synchronized (this.mNativeLock) {
            checkRecycled("getWidth called on recycled region decoder");
            int i = this.mWidth;
            if (i > 0) {
                return i;
            }
            int nativeGetWidth = nativeGetWidth(this.mNativeBitmapRegionDecoder);
            this.mWidth = nativeGetWidth;
            return nativeGetWidth;
        }
    }

    public int getHeight() {
        synchronized (this.mNativeLock) {
            checkRecycled("getHeight called on recycled region decoder");
            int i = this.mHeight;
            if (i > 0) {
                return i;
            }
            int nativeGetHeight = nativeGetHeight(this.mNativeBitmapRegionDecoder);
            this.mHeight = nativeGetHeight;
            return nativeGetHeight;
        }
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
