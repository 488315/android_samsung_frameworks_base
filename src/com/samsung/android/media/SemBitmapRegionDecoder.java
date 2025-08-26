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
public class SemBitmapRegionDecoder {
    private static final String TAG = "SemBitmapRegionDecoder";
    private static boolean mLibraryLoaded = false;
    private long mNativeBitmapRegionDecoder;
    private InternalRegionDecoder mRegionDecoder = null;
    private InternalRegionDecoder mGainRD = null;
    private byte[] mGainBuf = null;
    private int mWidth = 0;
    private int mHeight = 0;
    private final Object mNativeLock = new Object();
    private boolean mRecycled = false;

    static {
        loadLibrary();
    }

    private static void loadLibrary() {
        if (mLibraryLoaded) {
            return;
        }
        try {
            mLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Unable to load the native library : " + e);
        }
    }

    public static SemBitmapRegionDecoder newInstance(String str) throws IOException {
        if (str == null) {
            throw new IOException("pathName is null");
        }
        Log.d(TAG, "newInstance file e");
        SemBitmapRegionDecoder semBitmapRegionDecoder = new SemBitmapRegionDecoder();
        InternalRegionDecoder internalRegionDecoderNewInstance = InternalRegionDecoder.newInstance(str);
        semBitmapRegionDecoder.mRegionDecoder = internalRegionDecoderNewInstance;
        if (internalRegionDecoderNewInstance != null) {
            return semBitmapRegionDecoder;
        }
        Log.e(TAG, "newInstance file fail");
        return null;
    }

    public static SemBitmapRegionDecoder newInstance(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            throw new IOException("data is null");
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Log.d(TAG, "newInstance byteArray e");
        SemBitmapRegionDecoder semBitmapRegionDecoder = new SemBitmapRegionDecoder();
        InternalRegionDecoder internalRegionDecoderNewInstance = InternalRegionDecoder.newInstance(bArr, i, i2);
        semBitmapRegionDecoder.mRegionDecoder = internalRegionDecoderNewInstance;
        if (internalRegionDecoderNewInstance != null) {
            return semBitmapRegionDecoder;
        }
        Log.e(TAG, "newInstance byteArray fail");
        return null;
    }

    public static SemBitmapRegionDecoder newInstance(FileDescriptor fileDescriptor) throws IOException {
        if (fileDescriptor == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        try {
            SemBitmapRegionDecoder semBitmapRegionDecoderNewInstance = newInstance(fileInputStream);
            fileInputStream.close();
            return semBitmapRegionDecoderNewInstance;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static SemBitmapRegionDecoder newInstance(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("inputStream is null");
        }
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

    private SemBitmapRegionDecoder(long j) {
        this.mNativeBitmapRegionDecoder = j;
    }

    private SemBitmapRegionDecoder() {
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        Bitmap bitmapDecodeRegion;
        Log.d(TAG, "decode regioin:e");
        checkRecycled("decodeRegion called on recycled region decoder");
        if (this.mRegionDecoder == null) {
            Log.e(TAG, "mRegionDecoder is null");
            return null;
        }
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
        if (options != null) {
            Log.d(TAG, "opt.semInApplyPhotoHdr:" + options.semInApplyPhotoHdr + ", opt.semInCreateGainmap:" + options.semInCreateGainmap);
        }
        Log.d(TAG, "decode region");
        if (options != null && options.semInApplyPhotoHdr) {
            Log.d(TAG, "decodePhotoHdrRegion opt.semInApplyPhotoHdr true");
            bitmapDecodeRegion = this.mRegionDecoder.decodePhotoHdrRegion(rect, options);
        } else {
            Log.d(TAG, "decodeRegion opt.semInApplyPhotoHdr false");
            bitmapDecodeRegion = this.mRegionDecoder.decodeRegion(rect, options);
        }
        if (bitmapDecodeRegion == null) {
            Log.e(TAG, "coverBitmap null");
            return null;
        }
        Log.d(TAG, "decode regioin:x");
        return bitmapDecodeRegion;
    }

    public int getWidth() {
        checkRecycled("getWidth called on recycled region decoder");
        int i = this.mWidth;
        if (i > 0) {
            return i;
        }
        int width = this.mRegionDecoder.getWidth();
        this.mWidth = width;
        return width;
    }

    public int getHeight() {
        checkRecycled("getHeight called on recycled region decoder");
        int i = this.mHeight;
        if (i > 0) {
            return i;
        }
        int height = this.mRegionDecoder.getHeight();
        this.mHeight = height;
        return height;
    }

    public void recycle() {
        if (this.mRecycled) {
            return;
        }
        this.mRegionDecoder.recycle();
        InternalRegionDecoder internalRegionDecoder = this.mGainRD;
        if (internalRegionDecoder != null) {
            internalRegionDecoder.recycle();
        }
        this.mRecycled = true;
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
