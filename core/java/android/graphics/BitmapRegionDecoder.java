package android.graphics;

import android.content.res.AssetManager;
import android.drm.DrmInfo;
import android.drm.DrmInfoRequest;
import android.drm.DrmManagerClient;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.google.android.mms.ContentType;

import java.io.ByteArrayInputStream;
import java.io.File;
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

    private static native Bitmap nativeDecodeRegion(
            long j, int i, int i2, int i3, int i4, BitmapFactory.Options options, long j2, long j3);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native BitmapRegionDecoder nativeNewInstance(long j);

    private static native BitmapRegionDecoder nativeNewInstance(FileDescriptor fileDescriptor);

    private static native BitmapRegionDecoder nativeNewInstance(
            InputStream inputStream, byte[] bArr);

    private static native BitmapRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    @Deprecated
    public static BitmapRegionDecoder newInstance(
            byte[] data, int offset, int length, boolean isShareable) throws IOException {
        return newInstance(data, offset, length);
    }

    public static BitmapRegionDecoder newInstance(byte[] data, int offset, int length)
            throws IOException {
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(data, offset, length);
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(FileDescriptor fd, boolean isShareable)
            throws IOException {
        return nativeNewInstance(fd);
    }

    public static BitmapRegionDecoder newInstance(ParcelFileDescriptor pfd) throws IOException {
        return nativeNewInstance(pfd.getFileDescriptor());
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(InputStream is, boolean isShareable)
            throws IOException {
        return newInstance(is);
    }

    public static BitmapRegionDecoder newInstance(InputStream is) throws IOException {
        if (is instanceof AssetManager.AssetInputStream) {
            return nativeNewInstance(((AssetManager.AssetInputStream) is).getNativeAsset());
        }
        byte[] tempStorage = new byte[16384];
        return nativeNewInstance(is, tempStorage);
    }

    @Deprecated
    public static BitmapRegionDecoder newInstance(String pathName, boolean isShareable)
            throws IOException {
        BitmapRegionDecoder decoder = null;
        InputStream stream = null;
        if (pathName.endsWith(".dcf")) {
            DrmManagerClient drmClient = new DrmManagerClient(null);
            int rightStatus = drmClient.checkRightsStatus(pathName, 7);
            if (rightStatus != 0) {
                Log.i(
                        "BitmapRegionDecoder",
                        "newInstance Rights not present. rightStatus = " + rightStatus);
            } else {
                long fileLength = new File(pathName).length();
                DrmInfoRequest drmInfoRequest = new DrmInfoRequest(10, ContentType.APP_DRM_CONTENT);
                drmInfoRequest.put(DrmInfoRequest.SEM_DRM_PATH, pathName);
                drmInfoRequest.put("LENGTH", Long.valueOf(fileLength).toString());
                DrmInfo resultInfo = drmClient.acquireDrmInfo(drmInfoRequest);
                String status = resultInfo.get("status").toString();
                Log.i("BitmapRegionDecoder", "newInstance acquireDrmInfo status is " + status);
                if (!status.equals("success")) {
                    Log.e(
                            "BitmapRegionDecoder",
                            "newInstance FAIL status = " + resultInfo.get("INFO"));
                } else if (resultInfo.getData() == null) {
                    Log.e("BitmapRegionDecoder", "newInstance acquireDrmInfo resultInfo is null");
                } else {
                    InputStream stream2 = new ByteArrayInputStream(resultInfo.getData());
                    decoder = newInstance(stream2, isShareable);
                }
            }
            drmClient.release();
        } else {
            try {
                stream = new FileInputStream(pathName);
                decoder = newInstance(stream, isShareable);
                try {
                    stream.close();
                } catch (IOException e) {
                }
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        }
        return decoder;
    }

    public static BitmapRegionDecoder newInstance(
            String pathName, boolean isShareable, boolean isPreview) throws IOException {
        return newInstance(pathName, isShareable);
    }

    public static BitmapRegionDecoder newInstance(String pathName) throws IOException {
        InputStream stream = null;
        try {
            stream = new FileInputStream(pathName);
            BitmapRegionDecoder decoder = newInstance(stream);
            try {
                stream.close();
            } catch (IOException e) {
            }
            return decoder;
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
    }

    private BitmapRegionDecoder(long decoder) {
        this.mNativeBitmapRegionDecoder = decoder;
    }

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        Bitmap nativeDecodeRegion;
        BitmapFactory.Options.validate(options);
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0
                    || rect.bottom <= 0
                    || rect.left >= getWidth()
                    || rect.top >= getHeight()) {
                throw new IllegalArgumentException("rectangle is outside the image");
            }
            nativeDecodeRegion =
                    nativeDecodeRegion(
                            this.mNativeBitmapRegionDecoder,
                            rect.left,
                            rect.top,
                            rect.right - rect.left,
                            rect.bottom - rect.top,
                            options,
                            BitmapFactory.Options.nativeInBitmap(options),
                            BitmapFactory.Options.nativeColorSpace(options));
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
