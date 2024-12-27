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
        if (!mLibraryLoaded) {
            try {
                System.loadLibrary("heifregiondec_jni");
                mLibraryLoaded = true;
            } catch (UnsatisfiedLinkError ule) {
                Log.e(TAG, "Unable to load the native library : " + ule);
            }
        }
    }

    public static SemHEIFRegionDecoder newInstance(String pathName) throws IOException {
        if (pathName == null) {
            throw new IOException("pathName is null");
        }
        return nativeNewInstance(pathName);
    }

    public static SemHEIFRegionDecoder newInstance(byte[] data, int offset, int length, boolean isShareable) throws IOException {
        if (data == null) {
            throw new IOException("data is null");
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(data, offset, length);
    }

    public static SemHEIFRegionDecoder newInstance(FileDescriptor fd, boolean isShareable) throws IOException {
        if (fd == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fis = new FileInputStream(fd);
        try {
            SemHEIFRegionDecoder instance = newInstance(fis, isShareable);
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

    public static SemHEIFRegionDecoder newInstance(InputStream is, boolean isShareable) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            int read = is.read(buffer);
            if (read != -1) {
                bos.write(buffer, 0, read);
            } else {
                byte[] inBytes = bos.toByteArray();
                bos.close();
                return newInstance(inBytes, 0, inBytes.length, isShareable);
            }
        }
    }

    private SemHEIFRegionDecoder(long decoder) {
        this.mNativeBitmapRegionDecoder = decoder;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options opt) {
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0 || rect.bottom <= 0 || rect.left >= getWidth() || rect.top >= getHeight()) {
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
                if (opt.inBitmap != null && (opt.inBitmap.getWidth() != tile_width2 || opt.inBitmap.getHeight() != tile_height2)) {
                    Log.w(TAG, "RegionDecode Input Bitmap error");
                    return opt.inBitmap;
                }
            }
            return nativeDecodeRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, opt);
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
