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

    private static native Bitmap native_decodeByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options, int i3);

    private static native Bitmap native_decodeFile(String str, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodeFileDescriptor(FileDescriptor fileDescriptor, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodePhotoHdrByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options);

    private static native Bitmap native_decodePhotoHdrFile(String str, BitmapFactory.Options options);

    private static native Bitmap native_decodeStream(InputStream inputStream, BitmapFactory.Options options, int i);

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
        if (mLibraryLoaded) {
            return;
        }
        try {
            System.loadLibrary("sembitmapfactory_jni");
            mLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Unable to load the native library : " + e);
        }
    }

    public static Bitmap decodeFile(String str, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeFile - mLibraryLoaded is false");
            return null;
        }
        Log.d(TAG, "decodeFile e");
        if (options != null) {
            Log.d(TAG, "opts.semInApplyPhotoHdr:" + options.semInApplyPhotoHdr + "  opts.semInCreateGainmap:" + options.semInCreateGainmap);
            if (options.inBitmap != null) {
                if (options.inBitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
                }
                if (options.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
                if (options.inBitmap.hasGainmap()) {
                    Log.d(TAG, "set inBitmap Gainmap to null");
                    options.inBitmap.setGainmap(null);
                }
            }
        } else {
            Log.d(TAG, "opts null");
        }
        if (str == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        if (options != null && options.semInApplyPhotoHdr) {
            Log.d(TAG, "decodeFile opts.semInApplyPhotoHdr true");
            return decodePhotoHdrFile(str, options);
        }
        Log.d(TAG, "decodeFile opts.semInApplyPhotoHdr x");
        return native_decodeFile(str, options, 0);
    }

    private static Bitmap decodePhotoHdrFile(String str, BitmapFactory.Options options) {
        try {
            Bitmap bitmapNative_decodePhotoHdrFile = native_decodePhotoHdrFile(str, options);
            if (bitmapNative_decodePhotoHdrFile != null) {
                return bitmapNative_decodePhotoHdrFile;
            }
            Log.e(TAG, "coverBitmap null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap decodePhotoHdrByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options) {
        try {
            Bitmap bitmapNative_decodePhotoHdrByteArray = native_decodePhotoHdrByteArray(bArr, i, i2, options);
            if (bitmapNative_decodePhotoHdrByteArray != null) {
                return bitmapNative_decodePhotoHdrByteArray;
            }
            Log.e(TAG, "coverBitmap null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, BitmapFactory.Options options) throws IOException {
        Bitmap bitmapDecodeStream = null;
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fileDescriptor == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                bitmapDecodeStream = decodeStream(fileInputStream, options);
                fileInputStream.close();
                return bitmapDecodeStream;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return bitmapDecodeStream;
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, BitmapFactory.Options options) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeStream - mLibraryLoaded is false");
            return null;
        }
        if (inputStream == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return decodeByteArray(byteArray, 0, byteArray.length, options);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeByteArray - mLibraryLoaded is false");
            return null;
        }
        Log.d(TAG, "decodeByteArray e");
        if (options != null) {
            Log.d(TAG, "opts.semInApplyPhotoHdr:" + options.semInApplyPhotoHdr + "  opts.semInCreateGainmap:" + options.semInCreateGainmap);
            if (options.inBitmap != null) {
                if (options.inBitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
                }
                if (options.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
                if (options.inBitmap.hasGainmap()) {
                    Log.d(TAG, "set inBitmap Gainmap to null");
                    options.inBitmap.setGainmap(null);
                }
            }
        } else {
            Log.d(TAG, "opts null");
        }
        if (bArr == null) {
            return null;
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        if (options != null && options.semInApplyPhotoHdr) {
            Log.d(TAG, "decodeByteArray opts.semInApplyPhotoHdr true");
            return decodePhotoHdrByteArray(bArr, i, i2, options);
        }
        Log.d(TAG, "decodeByteArray opts.semInApplyPhotoHdr decode x");
        return native_decodeByteArray(bArr, i, i2, options, 0);
    }

    public static Bitmap decodeThumbnailFile(String str, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailFile - mLibraryLoaded is false");
            return null;
        }
        if (str == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        return native_decodeFile(str, options, 1);
    }

    public static Bitmap decodeThumbnailFileDescriptor(FileDescriptor fileDescriptor, BitmapFactory.Options options) throws IOException {
        Bitmap bitmapDecodeThumbnailStream = null;
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fileDescriptor == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                bitmapDecodeThumbnailStream = decodeThumbnailStream(fileInputStream, options);
                fileInputStream.close();
                return bitmapDecodeThumbnailStream;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return bitmapDecodeThumbnailStream;
        }
    }

    public static Bitmap decodeThumbnailStream(InputStream inputStream, BitmapFactory.Options options) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailStream - mLibraryLoaded is false");
            return null;
        }
        if (inputStream == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return decodeThumbnailByteArray(byteArray, 0, byteArray.length, options);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap decodeThumbnailByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeThumbnailByteArray - mLibraryLoaded is false");
            return null;
        }
        if (bArr == null) {
            return null;
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        return native_decodeByteArray(bArr, i, i2, options, 1);
    }

    public static byte[] getExifDataFile(String str) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataFile - mLibraryLoaded is false");
            return null;
        }
        if (str == null) {
            return null;
        }
        return native_getExifData(str);
    }

    public static byte[] getExifDataFileDescriptor(FileDescriptor fileDescriptor) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataFileDescriptor - mLibraryLoaded is false");
            return null;
        }
        if (fileDescriptor == null) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                byte[] exifDataStream = getExifDataStream(fileInputStream);
                fileInputStream.close();
                return exifDataStream;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getExifDataStream(InputStream inputStream) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataStream - mLibraryLoaded is false");
            return null;
        }
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return getExifDataByteArray(byteArray, 0, byteArray.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] getExifDataByteArray(byte[] bArr, int i, int i2) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifDataByteArray - mLibraryLoaded is false");
            return null;
        }
        if (bArr == null) {
            return null;
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return native_getExifDataByteArray(bArr, i, i2);
    }

    public static byte[] getIccDataFile(String str) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getIccDataFile - mLibraryLoaded is false");
            return null;
        }
        if (str == null) {
            return null;
        }
        return native_getIccData(str, 0);
    }

    public static byte[] getIccDataByteArray(byte[] bArr, int i, int i2) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getIccDataByteArray - mLibraryLoaded is false");
            return null;
        }
        if (bArr == null) {
            return null;
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return native_getIccDataByteArray(bArr, i, i2, 0);
    }
}
