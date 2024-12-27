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

    private static native Bitmap nativeDecodeGainRegion(
            long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native Bitmap nativeDecodePhotoHdrRegion(
            long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native Bitmap nativeDecodeRegion(
            long j, int i, int i2, int i3, int i4, BitmapFactory.Options options);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native InternalRegionDecoder nativeNewInstance(String str);

    private static native InternalRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    static {
        loadLibrary();
    }

    private static void loadLibrary() {
        if (!mLibraryLoaded) {
            try {
                System.loadLibrary("sembitmapregiondec_jni");
                mLibraryLoaded = true;
            } catch (UnsatisfiedLinkError ule) {
                Log.e(TAG, "Unable to load the native library : " + ule);
            }
        }
    }

    public static InternalRegionDecoder newInstance(String pathName) throws IOException {
        Log.d(TAG, "newInstance File e");
        if (pathName == null) {
            throw new IOException("pathName is null");
        }
        return nativeNewInstance(pathName);
    }

    public static InternalRegionDecoder newInstance(byte[] data, int offset, int length)
            throws IOException {
        Log.d(TAG, "newInstance ByteArray e");
        if (data == null) {
            throw new IOException("data is null");
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(data, offset, length);
    }

    public static InternalRegionDecoder newInstance(FileDescriptor fd) throws IOException {
        Log.d(TAG, "newInstance FD e");
        if (fd == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fis = new FileInputStream(fd);
        try {
            InternalRegionDecoder instance = newInstance(fis);
            fis.close();
            return instance;
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static InternalRegionDecoder newInstance(InputStream is) throws IOException {
        Log.d(TAG, "newInstance Stream e");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            int read = is.read(buffer);
            if (read != -1) {
                bos.write(buffer, 0, read);
            } else {
                byte[] inBytes = bos.toByteArray();
                bos.close();
                return newInstance(inBytes, 0, inBytes.length);
            }
        }
    }

    private InternalRegionDecoder(long decoder) {
        this.mNativeBitmapRegionDecoder = decoder;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options opt) {
        Log.d(TAG, "decodeRegion e");
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0
                    || rect.bottom <= 0
                    || rect.left >= getWidth()
                    || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            if (opt != null) {
                int sample_size = opt.inSampleSize;
                if (sample_size == 0) {
                    sample_size = 1;
                }
                opt.inSampleSize = sample_size;
                int tile_width = rect.width();
                int tile_height = rect.height();
                int tile_width2 = ((tile_width + sample_size) - 1) / sample_size;
                int tile_height2 = ((tile_height + sample_size) - 1) / sample_size;
                if (opt.inBitmap != null
                        && (opt.inBitmap.getWidth() != tile_width2
                                || opt.inBitmap.getHeight() != tile_height2)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return opt.inBitmap;
                }
            }
            return nativeDecodeRegion(
                    this.mNativeBitmapRegionDecoder,
                    rect.left,
                    rect.top,
                    rect.right - rect.left,
                    rect.bottom - rect.top,
                    opt);
        }
    }

    public Bitmap decodeGainRegion(Rect rect, BitmapFactory.Options opt) {
        Log.d(TAG, "decodeGainRegion e");
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0
                    || rect.bottom <= 0
                    || rect.left >= getWidth()
                    || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            if (opt != null) {
                int sample_size = opt.inSampleSize;
                if (sample_size == 0) {
                    sample_size = 1;
                }
                opt.inSampleSize = sample_size;
                int tile_width = rect.width();
                int tile_height = rect.height();
                int tile_width2 = ((tile_width + sample_size) - 1) / sample_size;
                int tile_height2 = ((tile_height + sample_size) - 1) / sample_size;
                if (opt.inBitmap != null
                        && (opt.inBitmap.getWidth() != tile_width2
                                || opt.inBitmap.getHeight() != tile_height2)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return opt.inBitmap;
                }
            }
            return nativeDecodeGainRegion(
                    this.mNativeBitmapRegionDecoder,
                    rect.left,
                    rect.top,
                    rect.right - rect.left,
                    rect.bottom - rect.top,
                    opt);
        }
    }

    public Bitmap decodePhotoHdrRegion(Rect rect, BitmapFactory.Options opt) {
        Log.d(TAG, "decodeGainRegion e");
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0
                    || rect.bottom <= 0
                    || rect.left >= getWidth()
                    || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            if (opt != null) {
                int sample_size = opt.inSampleSize;
                if (sample_size == 0) {
                    sample_size = 1;
                }
                opt.inSampleSize = sample_size;
                int tile_width = rect.width();
                int tile_height = rect.height();
                int tile_width2 = ((tile_width + sample_size) - 1) / sample_size;
                int tile_height2 = ((tile_height + sample_size) - 1) / sample_size;
                if (opt.inBitmap != null
                        && (opt.inBitmap.getWidth() != tile_width2
                                || opt.inBitmap.getHeight() != tile_height2)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return opt.inBitmap;
                }
            }
            return nativeDecodePhotoHdrRegion(
                    this.mNativeBitmapRegionDecoder,
                    rect.left,
                    rect.top,
                    rect.right - rect.left,
                    rect.bottom - rect.top,
                    opt);
        }
    }

    public int getWidth() {
        synchronized (this.mNativeLock) {
            checkRecycled("getWidth called on recycled region decoder");
            if (this.mWidth > 0) {
                return this.mWidth;
            }
            this.mWidth = nativeGetWidth(this.mNativeBitmapRegionDecoder);
            return this.mWidth;
        }
    }

    public int getHeight() {
        synchronized (this.mNativeLock) {
            checkRecycled("getHeight called on recycled region decoder");
            if (this.mHeight > 0) {
                return this.mHeight;
            }
            this.mHeight = nativeGetHeight(this.mNativeBitmapRegionDecoder);
            return this.mHeight;
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

    private void checkRecycled(String errorMessage) {
        if (this.mRecycled) {
            throw new IllegalStateException(errorMessage);
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
