package com.samsung.android.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated(forRemoval = true, since = "15.0")
/* loaded from: classes6.dex */
public class SemHEIFCodec {
    public static final int ENCODING_TYPE_JPEG_SQUEEZER = 1;
    private static final int IMAGE_TYPE_COVER = 0;
    private static final int IMAGE_TYPE_THUMBNAIL = 1;
    private static final String TAG = "SemHEIFCodec";
    private static boolean mLibraryLoaded = false;

    private static native Bitmap native_decodeByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options, int i3);

    private static native Bitmap native_decodeFile(String str, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodeFileDescriptor(FileDescriptor fileDescriptor, BitmapFactory.Options options, int i);

    private static native Bitmap native_decodeStream(InputStream inputStream, BitmapFactory.Options options, int i);

    private static native byte[] native_getExifData(String str);

    private static native byte[] native_getExifDataByteArray(byte[] bArr, int i, int i2);

    private static native boolean native_transcode(String str, String str2, int i);

    static {
        loadLibrary();
    }

    private static void loadLibrary() {
        if (mLibraryLoaded) {
            return;
        }
        try {
            System.loadLibrary("heifcodec_jni");
            mLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Unable to load the native library : " + e);
        }
    }

    public static boolean transcode(String str, String str2, int i) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "transcode - mLibraryLoaded is false");
            return false;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return native_transcode(str, str2, i);
    }

    public static Bitmap decodeFile(String str, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "decodeFile - mLibraryLoaded is false");
            return null;
        }
        if (str == null) {
            return null;
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        return native_decodeFile(str, options, 0);
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
                    return native_decodeByteArray(byteArray, 0, byteArray.length, options, 0);
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
        if (bArr == null) {
            return null;
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (options != null && options.inSampleSize < 0) {
            options.inSampleSize = 1;
        }
        return native_decodeByteArray(bArr, i, i2, options, 0);
    }

    public static Bitmap getThumbnail(String str, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getThumbnail - mLibraryLoaded is false");
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

    public static Bitmap getThumbnail(FileDescriptor fileDescriptor, BitmapFactory.Options options) throws IOException {
        Bitmap thumbnail = null;
        if (!mLibraryLoaded) {
            Log.e(TAG, "getThumbnail - mLibraryLoaded is false");
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
                thumbnail = getThumbnail(fileInputStream, options);
                fileInputStream.close();
                return thumbnail;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return thumbnail;
        }
    }

    public static Bitmap getThumbnail(InputStream inputStream, BitmapFactory.Options options) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getThumbnail - mLibraryLoaded is false");
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
                    return native_decodeByteArray(byteArray, 0, byteArray.length, options, 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap getThumbnail(byte[] bArr, int i, int i2, BitmapFactory.Options options) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getThumbnail - mLibraryLoaded is false");
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

    public static byte[] getExifData(String str) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifData - mLibraryLoaded is false");
            return null;
        }
        if (str == null) {
            return null;
        }
        return native_getExifData(str);
    }

    public static byte[] getExifData(FileDescriptor fileDescriptor) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifData - mLibraryLoaded is false");
            return null;
        }
        if (fileDescriptor == null) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                byte[] exifData = getExifData(fileInputStream);
                fileInputStream.close();
                return exifData;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getExifData(InputStream inputStream) throws IOException {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifData - mLibraryLoaded is false");
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
                    return native_getExifDataByteArray(byteArray, 0, byteArray.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] getExifData(byte[] bArr, int i, int i2) {
        if (!mLibraryLoaded) {
            Log.e(TAG, "getExifData - mLibraryLoaded is false");
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
}
