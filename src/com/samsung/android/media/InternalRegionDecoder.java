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

/* loaded from: classes6.dex */
public class InternalRegionDecoder {
    public static final int IMAGE_TYPE_COVER = 0;
    public static final int IMAGE_TYPE_GAINMAP = 2;
    public static final int IMAGE_TYPE_THUMBNAIL = 1;
    private static final String TAG = "InternalRegionDecoder";
    private static boolean mLibraryLoaded = false;
    private long mNativeBitmapRegionDecoder;
    private int mWidth = 0;
    private int mHeight = 0;
    private byte[] mXmpBuf = null;
    private byte[] mGainXmpBuf = null;
    private final Object mNativeLock = new Object();
    private boolean mRecycled = false;

    private static native void nativeClean(long j);

    private static native Bitmap nativeDecodeGainRegion(long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native Bitmap nativeDecodePhotoHdrRegion(long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native Bitmap nativeDecodeRegion(long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native InternalRegionDecoder nativeNewInstance(String str);

    private static native InternalRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    static {
        loadLibrary();
    }

    private static void loadLibrary() {
        if (mLibraryLoaded) {
            return;
        }
        try {
            System.loadLibrary("sembitmapregiondec_jni");
            mLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Unable to load the native library : " + e);
        }
    }

    public static InternalRegionDecoder newInstance(String str) throws IOException {
        Log.d(TAG, "newInstance File e");
        if (str == null) {
            throw new IOException("pathName is null");
        }
        return nativeNewInstance(str);
    }

    public static InternalRegionDecoder newInstance(byte[] bArr, int i, int i2) throws IOException {
        Log.d(TAG, "newInstance ByteArray e");
        if (bArr == null) {
            throw new IOException("data is null");
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(bArr, i, i2);
    }

    public static InternalRegionDecoder newInstance(FileDescriptor fileDescriptor) throws IOException {
        Log.d(TAG, "newInstance FD e");
        if (fileDescriptor == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        try {
            InternalRegionDecoder internalRegionDecoderNewInstance = newInstance(fileInputStream);
            fileInputStream.close();
            return internalRegionDecoderNewInstance;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static InternalRegionDecoder newInstance(InputStream inputStream) throws IOException {
        Log.d(TAG, "newInstance Stream e");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return newInstance(byteArray, 0, byteArray.length);
            }
        }
    }

    private InternalRegionDecoder(long j) {
        this.mNativeBitmapRegionDecoder = j;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        Log.d(TAG, "decodeRegion e");
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
                int iWidth = ((rect.width() + i) - 1) / i;
                int iHeight = ((rect.height() + i) - 1) / i;
                if (options.inBitmap != null && (options.inBitmap.getWidth() != iWidth || options.inBitmap.getHeight() != iHeight)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return options.inBitmap;
                }
            }
            return nativeDecodeRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options);
        }
    }

    public Bitmap decodeGainRegion(Rect rect, BitmapFactory.Options options) {
        Log.d(TAG, "decodeGainRegion e");
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
                int iWidth = ((rect.width() + i) - 1) / i;
                int iHeight = ((rect.height() + i) - 1) / i;
                if (options.inBitmap != null && (options.inBitmap.getWidth() != iWidth || options.inBitmap.getHeight() != iHeight)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return options.inBitmap;
                }
            }
            return nativeDecodeGainRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options);
        }
    }

    public Bitmap decodePhotoHdrRegion(Rect rect, BitmapFactory.Options options) {
        Log.d(TAG, "decodeGainRegion e");
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
                int iWidth = ((rect.width() + i) - 1) / i;
                int iHeight = ((rect.height() + i) - 1) / i;
                if (options.inBitmap != null && (options.inBitmap.getWidth() != iWidth || options.inBitmap.getHeight() != iHeight)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return options.inBitmap;
                }
            }
            return nativeDecodePhotoHdrRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options);
        }
    }

    public int getWidth() {
        synchronized (this.mNativeLock) {
            checkRecycled("getWidth called on recycled region decoder");
            int i = this.mWidth;
            if (i > 0) {
                return i;
            }
            int iNativeGetWidth = nativeGetWidth(this.mNativeBitmapRegionDecoder);
            this.mWidth = iNativeGetWidth;
            return iNativeGetWidth;
        }
    }

    public int getHeight() {
        synchronized (this.mNativeLock) {
            checkRecycled("getHeight called on recycled region decoder");
            int i = this.mHeight;
            if (i > 0) {
                return i;
            }
            int iNativeGetHeight = nativeGetHeight(this.mNativeBitmapRegionDecoder);
            this.mHeight = iNativeGetHeight;
            return iNativeGetHeight;
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
