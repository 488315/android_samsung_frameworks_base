package com.samsung.android.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Trace;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class SemBitmapFactory {
    private static final int IMAGE_TYPE_COVER = 0;
    private static final int IMAGE_TYPE_GAINMAP = 2;
    private static final int IMAGE_TYPE_THUMBNAIL = 1;
    private static final String TAG = "SemBitmapFactory";
    private static boolean mLibraryLoaded = false;

    private static native Bitmap native_decodeByteArray(
            byte[] bArr, int i, int i2, BitmapFactory.Options options, int i3);

    private static native Bitmap native_decodeFile(
            String str, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodeFileDescriptor(
            FileDescriptor fileDescriptor, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodePhotoHdrByteArray(
            byte[] bArr, int i, int i2, BitmapFactory.Options options);

    private static native Bitmap native_decodePhotoHdrFile(
            String str, BitmapFactory.Options options);

    private static native Bitmap native_decodeStream(
            InputStream inputStream, BitmapFactory.Options options, int i);

    private static native byte[] native_getExifData(String str);

    private static native byte[] native_getExifDataByteArray(byte[] bArr, int i, int i2);

    private static native byte[] native_getIccData(String str, int i);

    private static native byte[] native_getIccDataByteArray(byte[] bArr, int i, int i2, int i3);

    private static native byte[] native_getXmpData(String str, int i);

    private static native byte[] native_getXmpDataByteArray(byte[] bArr, int i, int i2, int i3);

    static {
        Trace.traceBegin(2L, "loadLibrary");
        loadLibrary();
        Trace.traceEnd(2L);
    }

    private static void loadLibrary() {
        if (!mLibraryLoaded) {
            try {
                System.loadLibrary("sembitmapfactory_jni");
                mLibraryLoaded = true;
            } catch (UnsatisfiedLinkError ule) {
                Log.e(TAG, "Unable to load the native library : " + ule);
            }
        }
    }

    public static Bitmap decodeFile(String path, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeFile - mLibraryLoaded is false");
            return null;
        }
        Log.d(TAG, "decodeFile e");
        if (opts != null) {
            Log.d(
                    TAG,
                    "opts.semInApplyPhotoHdr:"
                            + opts.semInApplyPhotoHdr
                            + "  opts.semInCreateGainmap:"
                            + opts.semInCreateGainmap);
            if (opts.inBitmap != null) {
                if (opts.inBitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException(
                            "Bitmaps with Config.HARDWARE are always immutable");
                }
                if (opts.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
                if (opts.inBitmap.hasGainmap()) {
                    Log.d(TAG, "set inBitmap Gainmap to null");
                    opts.inBitmap.setGainmap(null);
                }
            }
        } else {
            Log.d(TAG, "opts null");
        }
        if (path == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        if (opts != null && opts.semInApplyPhotoHdr) {
            Log.d(TAG, "decodeFile opts.semInApplyPhotoHdr true");
            return decodePhotoHdrFile(path, opts);
        }
        Log.d(TAG, "decodeFile opts.semInApplyPhotoHdr x");
        return native_decodeFile(path, opts, 0);
    }

    private static Bitmap decodePhotoHdrFile(String path, BitmapFactory.Options opts) {
        try {
            Bitmap coverBitmap = native_decodePhotoHdrFile(path, opts);
            if (coverBitmap != null) {
                return coverBitmap;
            }
            Log.e(TAG, "coverBitmap null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap decodePhotoHdrByteArray(
            byte[] data, int offset, int length, BitmapFactory.Options opts) {
        try {
            Bitmap coverBitmap = native_decodePhotoHdrByteArray(data, offset, length, opts);
            if (coverBitmap != null) {
                return coverBitmap;
            }
            Log.e(TAG, "coverBitmap null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fd == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        Bitmap bm = null;
        try {
            FileInputStream fis = new FileInputStream(fd);
            try {
                bm = decodeStream(fis, opts);
                fis.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap decodeStream(InputStream is, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeStream - mLibraryLoaded is false");
            return null;
        }
        if (is == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            try {
                int read = is.read(buffer);
                if (read != -1) {
                    bos.write(buffer, 0, read);
                } else {
                    byte[] inBytes = bos.toByteArray();
                    bos.close();
                    return decodeByteArray(inBytes, 0, inBytes.length, opts);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap decodeByteArray(
            byte[] data, int offset, int length, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeByteArray - mLibraryLoaded is false");
            return null;
        }
        Log.d(TAG, "decodeByteArray e");
        if (opts != null) {
            Log.d(
                    TAG,
                    "opts.semInApplyPhotoHdr:"
                            + opts.semInApplyPhotoHdr
                            + "  opts.semInCreateGainmap:"
                            + opts.semInCreateGainmap);
            if (opts.inBitmap != null) {
                if (opts.inBitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException(
                            "Bitmaps with Config.HARDWARE are always immutable");
                }
                if (opts.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
                if (opts.inBitmap.hasGainmap()) {
                    Log.d(TAG, "set inBitmap Gainmap to null");
                    opts.inBitmap.setGainmap(null);
                }
            }
        } else {
            Log.d(TAG, "opts null");
        }
        if (data == null) {
            return null;
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        if (opts != null && opts.semInApplyPhotoHdr) {
            Log.d(TAG, "decodeByteArray opts.semInApplyPhotoHdr true");
            return decodePhotoHdrByteArray(data, offset, length, opts);
        }
        Log.d(TAG, "decodeByteArray opts.semInApplyPhotoHdr decode x");
        return native_decodeByteArray(data, offset, length, opts, 0);
    }

    public static Bitmap decodeThumbnailFile(String path, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailFile - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        Bitmap thumbBitmap = native_decodeFile(path, opts, 1);
        return thumbBitmap;
    }

    public static Bitmap decodeThumbnailFileDescriptor(
            FileDescriptor fd, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fd == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        Bitmap bm = null;
        try {
            FileInputStream fis = new FileInputStream(fd);
            try {
                bm = decodeThumbnailStream(fis, opts);
                fis.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap decodeThumbnailStream(InputStream is, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailStream - mLibraryLoaded is false");
            return null;
        }
        if (is == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            try {
                int read = is.read(buffer);
                if (read != -1) {
                    bos.write(buffer, 0, read);
                } else {
                    byte[] inBytes = bos.toByteArray();
                    bos.close();
                    return decodeThumbnailByteArray(inBytes, 0, inBytes.length, opts);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap decodeThumbnailByteArray(
            byte[] data, int offset, int length, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailByteArray - mLibraryLoaded is false");
            return null;
        }
        if (data == null) {
            return null;
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        Bitmap thumbBitmap = native_decodeByteArray(data, offset, length, opts, 1);
        return thumbBitmap;
    }

    public static byte[] getExifDataFile(String path) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataFile - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        byte[] exifData = native_getExifData(path);
        return exifData;
    }

    public static byte[] getExifDataFileDescriptor(FileDescriptor fd) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fd == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(fd);
            try {
                byte[] exifDataStream = getExifDataStream(fis);
                fis.close();
                return exifDataStream;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getExifDataStream(InputStream is) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataStream - mLibraryLoaded is false");
            return null;
        }
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            try {
                int read = is.read(buffer);
                if (read != -1) {
                    bos.write(buffer, 0, read);
                } else {
                    byte[] inBytes = bos.toByteArray();
                    bos.close();
                    return getExifDataByteArray(inBytes, 0, inBytes.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] getExifDataByteArray(byte[] data, int offset, int length) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataByteArray - mLibraryLoaded is false");
            return null;
        }
        if (data == null) {
            return null;
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        byte[] exifData = native_getExifDataByteArray(data, offset, length);
        return exifData;
    }

    public static byte[] getIccDataFile(String path) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getIccDataFile - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        byte[] iccData = native_getIccData(path, 0);
        return iccData;
    }

    public static byte[] getIccDataByteArray(byte[] data, int offset, int length) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getIccDataByteArray - mLibraryLoaded is false");
            return null;
        }
        if (data == null) {
            return null;
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        byte[] iccData = native_getIccDataByteArray(data, offset, length, 0);
        return iccData;
    }
}
