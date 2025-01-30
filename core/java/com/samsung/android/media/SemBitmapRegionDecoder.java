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

/* loaded from: classes5.dex */
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
        if (!mLibraryLoaded) {
            try {
                mLibraryLoaded = true;
            } catch (UnsatisfiedLinkError ule) {
                Log.m96e(TAG, "Unable to load the native library : " + ule);
            }
        }
    }

    public static SemBitmapRegionDecoder newInstance(String pathName) throws IOException {
        if (pathName == null) {
            throw new IOException("pathName is null");
        }
        Log.m94d(TAG, "newInstance file e");
        SemBitmapRegionDecoder rd = new SemBitmapRegionDecoder();
        InternalRegionDecoder newInstance = InternalRegionDecoder.newInstance(pathName);
        rd.mRegionDecoder = newInstance;
        if (newInstance == null) {
            Log.m96e(TAG, "newInstance file fail");
            return null;
        }
        return rd;
    }

    public static SemBitmapRegionDecoder newInstance(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            throw new IOException("data is null");
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Log.m94d(TAG, "newInstance byteArray e");
        SemBitmapRegionDecoder rd = new SemBitmapRegionDecoder();
        InternalRegionDecoder newInstance = InternalRegionDecoder.newInstance(data, offset, length);
        rd.mRegionDecoder = newInstance;
        if (newInstance == null) {
            Log.m96e(TAG, "newInstance byteArray fail");
            return null;
        }
        return rd;
    }

    public static SemBitmapRegionDecoder newInstance(FileDescriptor fd) throws IOException {
        if (fd == null) {
            throw new IOException("fd is null");
        }
        FileInputStream fis = new FileInputStream(fd);
        try {
            SemBitmapRegionDecoder instance = newInstance(fis);
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

    public static SemBitmapRegionDecoder newInstance(InputStream is) throws IOException {
        if (is == null) {
            throw new IOException("inputStream is null");
        }
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

    private SemBitmapRegionDecoder(long decoder) {
        this.mNativeBitmapRegionDecoder = decoder;
    }

    private SemBitmapRegionDecoder() {
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options opt) {
        Bitmap coverBitmap;
        Log.m94d(TAG, "decode regioin:e");
        checkRecycled("decodeRegion called on recycled region decoder");
        if (this.mRegionDecoder == null) {
            Log.m96e(TAG, "mRegionDecoder is null");
            return null;
        }
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
            int tile_width3 = tile_height + sample_size;
            int tile_height2 = (tile_width3 - 1) / sample_size;
            if (opt.inBitmap != null && (opt.inBitmap.getWidth() != tile_width2 || opt.inBitmap.getHeight() != tile_height2)) {
                Log.m102w(TAG, "RegionDecode Input Bitmap error");
                return opt.inBitmap;
            }
        }
        if (opt != null) {
            Log.m94d(TAG, "opt.semInApplyPhotoHdr:" + opt.semInApplyPhotoHdr + ", opt.semInCreateGainmap:" + opt.semInCreateGainmap);
        }
        Log.m94d(TAG, "decode region");
        if (opt != null && opt.semInApplyPhotoHdr) {
            Log.m94d(TAG, "decodePhotoHdrRegion opt.semInApplyPhotoHdr true");
            coverBitmap = this.mRegionDecoder.decodePhotoHdrRegion(rect, opt);
        } else {
            Log.m94d(TAG, "decodeRegion opt.semInApplyPhotoHdr false");
            coverBitmap = this.mRegionDecoder.decodeRegion(rect, opt);
        }
        if (coverBitmap == null) {
            Log.m96e(TAG, "coverBitmap null");
            return null;
        }
        Log.m94d(TAG, "decode regioin:x");
        return coverBitmap;
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
        if (!this.mRecycled) {
            this.mRegionDecoder.recycle();
            InternalRegionDecoder internalRegionDecoder = this.mGainRD;
            if (internalRegionDecoder != null) {
                internalRegionDecoder.recycle();
            }
            this.mRecycled = true;
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
