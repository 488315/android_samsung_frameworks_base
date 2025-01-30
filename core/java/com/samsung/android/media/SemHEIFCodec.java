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
/* loaded from: classes5.dex */
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
        if (!mLibraryLoaded) {
            try {
                System.loadLibrary("heifcodec_jni");
                mLibraryLoaded = true;
            } catch (UnsatisfiedLinkError ule) {
                Log.m96e(TAG, "Unable to load the native library : " + ule);
            }
        }
    }

    public static boolean transcode(String imageSrcPath, String imageDstPath, int encodingType) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "transcode - mLibraryLoaded is false");
            return false;
        }
        if (imageSrcPath == null || imageDstPath == null) {
            return false;
        }
        return native_transcode(imageSrcPath, imageDstPath, encodingType);
    }

    public static Bitmap decodeFile(String path, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "decodeFile - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        return native_decodeFile(path, opts, 0);
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "decodeFileDescriptor - mLibraryLoaded is false");
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
            Log.m96e(TAG, "decodeStream - mLibraryLoaded is false");
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
                    return native_decodeByteArray(inBytes, 0, inBytes.length, opts, 0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "decodeByteArray - mLibraryLoaded is false");
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
        return native_decodeByteArray(data, offset, length, opts, 0);
    }

    public static Bitmap getThumbnail(String path, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getThumbnail - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        if (opts != null && opts.inSampleSize < 0) {
            opts.inSampleSize = 1;
        }
        return native_decodeFile(path, opts, 1);
    }

    public static Bitmap getThumbnail(FileDescriptor fd, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getThumbnail - mLibraryLoaded is false");
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
                bm = getThumbnail(fis, opts);
                fis.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap getThumbnail(InputStream is, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getThumbnail - mLibraryLoaded is false");
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
                    return native_decodeByteArray(inBytes, 0, inBytes.length, opts, 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap getThumbnail(byte[] data, int offset, int length, BitmapFactory.Options opts) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getThumbnail - mLibraryLoaded is false");
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
        return native_decodeByteArray(data, offset, length, opts, 1);
    }

    public static byte[] getExifData(String path) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getExifData - mLibraryLoaded is false");
            return null;
        }
        if (path == null) {
            return null;
        }
        return native_getExifData(path);
    }

    public static byte[] getExifData(FileDescriptor fd) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getExifData - mLibraryLoaded is false");
            return null;
        }
        if (fd == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(fd);
            try {
                byte[] exifData = getExifData(fis);
                fis.close();
                return exifData;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getExifData(InputStream is) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getExifData - mLibraryLoaded is false");
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
                    return native_getExifDataByteArray(inBytes, 0, inBytes.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] getExifData(byte[] data, int offset, int length) {
        if (!mLibraryLoaded) {
            Log.m96e(TAG, "getExifData - mLibraryLoaded is false");
            return null;
        }
        if (data == null) {
            return null;
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return native_getExifDataByteArray(data, offset, length);
    }
}
