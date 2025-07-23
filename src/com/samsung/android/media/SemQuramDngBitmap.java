package com.samsung.android.media;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.media.SemQrBitmapFactory;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/* loaded from: classes6.dex */
public class SemQuramDngBitmap {
    int size = 0;
    int decodedSize = 0;

    public static class UseInfoType {
        public static final int FILE = 0;
        public static final int USER = 1;
    }

    public static native int DecodeDNGImageBufferJNI(byte[] bArr, Bitmap bitmap, int i, double[] dArr, double[] dArr2, SemQrBitmapFactory.Options options);

    public static native int DecodeDNGImageJNI(String str, Bitmap bitmap, double[] dArr, double[] dArr2, SemQrBitmapFactory.Options options);

    public static native int DecodeDNGPreviewFileJNI(String str, Bitmap bitmap, SemQrBitmapFactory.Options options);

    public static native int DecodeDNGPreviewImageBufferJNI(byte[] bArr, Bitmap bitmap, int i, SemQrBitmapFactory.Options options);

    public static native int cancelDecodingDNGImageBufferJNI(SemQrBitmapFactory.Options options);

    public static native int finalizeRegionDecoderHandle(SemQrBitmapFactory.Options options);

    public static native int getSECDngVersionJNI(byte[] bArr, int i);

    public static native byte[] nativeGetDNGPreviewImageFromByteArray(byte[] bArr, int i, int i2);

    public static native byte[] nativeGetDNGPreviewImageFromFile(String str);

    public static native String nativeGetDNGPrivateDataFromByteArray(byte[] bArr, int i, int i2);

    public static native String nativeGetDNGPrivateDataFromFile(String str);

    public static native int parseDNGImageBufferJNI(byte[] bArr, int i, double[] dArr, double[] dArr2, SemQrBitmapFactory.Options options);

    public static native int parseDNGImageJNI(String str, double[] dArr, double[] dArr2, SemQrBitmapFactory.Options options);

    public static native int parseDNGPreviewFileJNI(String str, SemQrBitmapFactory.Options options);

    public static native int parseDNGPreviewImageBufferJNI(byte[] bArr, int i, SemQrBitmapFactory.Options options);

    public static native int parseExifInfoJNI(byte[] bArr, int i, SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive);

    public static native int parseMetadataJNI(byte[] bArr, int i, SemQuramDngJavaMetadataPrimitive semQuramDngJavaMetadataPrimitive, SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive, SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive2);

    public static native int parseXMPBufferJNI(byte[] bArr, int i, long[] jArr, int[] iArr, SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive);

    static {
        try {
            System.loadLibrary(SemQuramValue.QRV_LIBRARY_NAME);
        } catch (Exception unused) {
        }
    }

    public static void SetFileBlackLevel(double[] dArr, SemQrBitmapFactory.Options options) {
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            for (int i3 = 0; i3 < 8; i3++) {
                System.arraycopy(dArr, i, options.FileBlackLevel[i2][i3], 0, options.FileBlackLevel[i2][i3].length);
                i += options.FileBlackLevel[i2][i3].length;
            }
        }
    }

    public static void SetUserBlackLevel(double[] dArr, SemQrBitmapFactory.Options options) {
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            for (int i3 = 0; i3 < 8; i3++) {
                System.arraycopy(options.UserBlackLevel[i2][i3], 0, dArr, i, options.UserBlackLevel[i2][i3].length);
                i += options.UserBlackLevel[i2][i3].length;
            }
        }
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options) {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (options == null) {
            options = new SemQrBitmapFactory.Options(0);
        }
        SemQrBitmapFactory.Options options2 = options;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        double[] dArr = new double[256];
        if (parseDNGImageBufferJNI(bArr, i2, dArr, options2.FileWhiteLevel, options2) < 0) {
            return null;
        }
        if (options2.rd_t != 0 || options2.rd_b != 0 || options2.rd_l != 0 || options2.rd_r != 0) {
            if (options2.rd_t < options2.rd_b && options2.rd_l < options2.rd_r) {
                options2.width = options2.rd_r - options2.rd_l;
                options2.height = options2.rd_b - options2.rd_t;
                if (options2.mCropOriginX != 0 || options2.mCropOriginY != 0) {
                    options2.rd_t += options2.mCropOriginY;
                    options2.rd_b += options2.mCropOriginY;
                    options2.rd_l += options2.mCropOriginX;
                    options2.rd_r += options2.mCropOriginX;
                }
            }
            return null;
        }
        options2.width = options2.mCropWidth >= 0 ? options2.mCropWidth : options2.width;
        options2.height = options2.mCropHeight >= 0 ? options2.mCropHeight : options2.height;
        options2.outWidth = options2.width;
        options2.outHeight = options2.height;
        if (!options2.inJustDecodeBounds && options2.width > 0 && options2.height > 0) {
            SetFileBlackLevel(dArr, options2);
            Bitmap createBitmap = Bitmap.createBitmap(options2.width, options2.height, config);
            double[] dArr2 = options2.UserWhiteLevel;
            SetUserBlackLevel(dArr, options2);
            if (DecodeDNGImageBufferJNI(bArr, createBitmap, i2, dArr, dArr2, options2) < 0) {
                return null;
            }
            float f = options2.width;
            float f2 = options2.height;
            if (options2.inSampleSize == 0) {
                options2.inSampleSize = 1;
            } else if (options2.inSampleSize < 0) {
                return null;
            }
            return 1 != options2.inSampleSize ? Bitmap.createScaledBitmap(createBitmap, (int) (f / options2.inSampleSize), (int) (f2 / options2.inSampleSize), true) : createBitmap;
        }
        return null;
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options) {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (options == null) {
            options = new SemQrBitmapFactory.Options(0);
        }
        SemQrBitmapFactory.Options options2 = options;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        double[] dArr = new double[256];
        if (parseDNGImageBufferJNI(bArr, i2, dArr, options2.FileWhiteLevel, options2) < 0) {
            return null;
        }
        if (options2.rd_t != 0 || options2.rd_b != 0 || options2.rd_l != 0 || options2.rd_r != 0) {
            if (options2.rd_t < options2.rd_b && options2.rd_l < options2.rd_r) {
                options2.width = options2.rd_r - options2.rd_l;
                options2.height = options2.rd_b - options2.rd_t;
                if (options2.mCropOriginX != 0 || options2.mCropOriginY != 0) {
                    options2.rd_t += options2.mCropOriginY;
                    options2.rd_b += options2.mCropOriginY;
                    options2.rd_l += options2.mCropOriginX;
                    options2.rd_r += options2.mCropOriginX;
                }
            }
            return null;
        }
        options2.width = options2.mCropWidth >= 0 ? options2.mCropWidth : options2.width;
        options2.height = options2.mCropHeight >= 0 ? options2.mCropHeight : options2.height;
        options2.outWidth = options2.width;
        options2.outHeight = options2.height;
        if (!options2.inJustDecodeBounds && options2.width > 0 && options2.height > 0) {
            SetFileBlackLevel(dArr, options2);
            Bitmap createBitmap = Bitmap.createBitmap(options2.width, options2.height, config);
            double[] dArr2 = options2.UserWhiteLevel;
            SetUserBlackLevel(dArr, options2);
            if (DecodeDNGImageBufferJNI(bArr, createBitmap, i2, dArr, dArr2, options2) < 0) {
                return null;
            }
            if (options2.inSampleSize == 0) {
                options2.inSampleSize = 1;
            } else if (options2.inSampleSize < 0) {
                return null;
            }
            return (i3 == options2.width && i4 == options2.height) ? createBitmap : Bitmap.createScaledBitmap(createBitmap, i3, i4, true);
        }
        return null;
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2) {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        SemQrBitmapFactory.Options options = new SemQrBitmapFactory.Options(0);
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        double[] dArr = new double[256];
        if (parseDNGImageBufferJNI(bArr, i2, dArr, options.FileWhiteLevel, options) < 0) {
            return null;
        }
        SetFileBlackLevel(dArr, options);
        Bitmap createBitmap = Bitmap.createBitmap(options.width, options.height, config);
        double[] dArr2 = options.UserWhiteLevel;
        SetUserBlackLevel(dArr, options);
        if (DecodeDNGImageBufferJNI(bArr, createBitmap, i2, dArr, dArr2, options) < 0) {
            return null;
        }
        return createBitmap;
    }

    public static int cancelDecoding(SemQrBitmapFactory.Options options) throws IOException {
        int cancelDecodingDNGImageBufferJNI = cancelDecodingDNGImageBufferJNI(options);
        if (cancelDecodingDNGImageBufferJNI >= 0) {
            return cancelDecodingDNGImageBufferJNI;
        }
        Log.i("QURAM_DNG", "cancelDecoding fail ");
        return -1;
    }

    public static Bitmap decodeFile(String str, SemQrBitmapFactory.Options options) throws IOException {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (options == null) {
            options = new SemQrBitmapFactory.Options(0);
        }
        double[] dArr = new double[256];
        if (parseDNGImageJNI(str, dArr, options.FileWhiteLevel, options) < 0) {
            return null;
        }
        if (options.rd_t != 0 || options.rd_b != 0 || options.rd_l != 0 || options.rd_r != 0) {
            if (options.rd_t < options.rd_b && options.rd_l < options.rd_r) {
                options.width = options.rd_r - options.rd_l;
                options.height = options.rd_b - options.rd_t;
                if (options.mCropOriginX != 0 || options.mCropOriginY != 0) {
                    options.rd_t += options.mCropOriginY;
                    options.rd_b += options.mCropOriginY;
                    options.rd_l += options.mCropOriginX;
                    options.rd_r += options.mCropOriginX;
                }
            }
            return null;
        }
        options.width = options.mCropWidth >= 0 ? options.mCropWidth : options.width;
        options.height = options.mCropHeight >= 0 ? options.mCropHeight : options.height;
        options.outWidth = options.width;
        options.outHeight = options.height;
        if (options.width > 0 && options.height > 0 && !options.inJustDecodeBounds) {
            SetFileBlackLevel(dArr, options);
            Bitmap createBitmap = Bitmap.createBitmap(options.width, options.height, config);
            double[] dArr2 = options.UserWhiteLevel;
            SetUserBlackLevel(dArr, options);
            if (DecodeDNGImageJNI(str, createBitmap, dArr, dArr2, options) < 0) {
                return null;
            }
            float f = options.width;
            float f2 = options.height;
            if (options.inSampleSize == 0) {
                options.inSampleSize = 1;
            } else if (options.inSampleSize < 0) {
                return null;
            }
            return 1 != options.inSampleSize ? Bitmap.createScaledBitmap(createBitmap, (int) (f / options.inSampleSize), (int) (f2 / options.inSampleSize), true) : createBitmap;
        }
        return null;
    }

    public static byte[] dumpXMPfromFile(String str, SemQuramDngJavaExif semQuramDngJavaExif) throws IOException {
        long[] jArr = new long[1];
        int[] iArr = new int[1];
        long length = new File(str).length();
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        int i = (int) length;
        try {
            byte[] bArr = new byte[i];
            randomAccessFile.read(bArr, 0, i);
            SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive = new SemQuramDngJavaExifPrimitive();
            if (parseXMPBufferJNI(bArr, i, jArr, iArr, semQuramDngJavaExifPrimitive) < 0) {
                randomAccessFile.close();
                randomAccessFile.close();
                return null;
            }
            semQuramDngJavaExif.buildExif(semQuramDngJavaExifPrimitive);
            byte[] bArr2 = new byte[iArr[0]];
            randomAccessFile.seek(0L);
            randomAccessFile.seek(jArr[0]);
            randomAccessFile.read(bArr2, 0, iArr[0]);
            randomAccessFile.close();
            randomAccessFile.close();
            return bArr2;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static int getSECDngVersionFromFile(String str) throws IOException {
        long length = new File(str).length();
        FileInputStream fileInputStream = new FileInputStream(str);
        int i = (int) length;
        try {
            byte[] bArr = new byte[i];
            if (fileInputStream.read(bArr, 0, i) == -1) {
                fileInputStream.close();
                fileInputStream.close();
                return -1;
            }
            int sECDngVersionJNI = getSECDngVersionJNI(bArr, i);
            fileInputStream.close();
            return sECDngVersionJNI;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static int getExifInfoFromFile(String str, SemQuramDngJavaExif semQuramDngJavaExif) throws IOException {
        long length = new File(str).length();
        FileInputStream fileInputStream = new FileInputStream(str);
        int i = (int) length;
        try {
            byte[] bArr = new byte[i];
            if (fileInputStream.read(bArr, 0, i) == -1) {
                fileInputStream.close();
                fileInputStream.close();
                return -1;
            }
            SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive = new SemQuramDngJavaExifPrimitive();
            if (parseExifInfoJNI(bArr, i, semQuramDngJavaExifPrimitive) >= 0) {
                semQuramDngJavaExif.buildExif(semQuramDngJavaExifPrimitive);
                fileInputStream.close();
                return 1;
            }
            fileInputStream.close();
            return -1;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, SemQrBitmapFactory.Options options) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        try {
            int available = fileInputStream.available();
            byte[] bArr = new byte[available];
            if (fileInputStream.read(bArr, 0, available) == -1) {
                fileInputStream.close();
                fileInputStream.close();
                return null;
            }
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            if (options == null) {
                options = new SemQrBitmapFactory.Options(0);
            }
            SemQrBitmapFactory.Options options2 = options;
            double[] dArr = new double[256];
            if (parseDNGImageBufferJNI(bArr, available, dArr, options2.FileWhiteLevel, options2) < 0) {
                fileInputStream.close();
                return null;
            }
            SetFileBlackLevel(dArr, options2);
            Bitmap createBitmap = Bitmap.createBitmap(options2.width, options2.height, config);
            if (options2.inJustDecodeBounds) {
                fileInputStream.close();
                return createBitmap;
            }
            double[] dArr2 = options2.UserWhiteLevel;
            SetUserBlackLevel(dArr, options2);
            if (DecodeDNGImageBufferJNI(bArr, createBitmap, available, dArr, dArr2, options2) < 0) {
                fileInputStream.close();
                return null;
            }
            float f = options2.width;
            float f2 = options2.height;
            if (options2.inSampleSize == 0) {
                options2.inSampleSize = 1;
            } else if (options2.inSampleSize < 0) {
                fileInputStream.close();
                return null;
            }
            if (1 != options2.inSampleSize) {
                createBitmap = Bitmap.createScaledBitmap(createBitmap, (int) (f / options2.inSampleSize), (int) (f2 / options2.inSampleSize), true);
            }
            fileInputStream.close();
            return createBitmap;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        try {
            int available = fileInputStream.available();
            byte[] bArr = new byte[available];
            if (fileInputStream.read(bArr, 0, available) == -1) {
                fileInputStream.close();
                fileInputStream.close();
                return null;
            }
            SemQrBitmapFactory.Options options = new SemQrBitmapFactory.Options(0);
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            double[] dArr = new double[256];
            if (parseDNGImageBufferJNI(bArr, available, dArr, options.FileWhiteLevel, options) >= 0) {
                SetFileBlackLevel(dArr, options);
                Bitmap createBitmap = Bitmap.createBitmap(options.width, options.height, config);
                double[] dArr2 = options.UserWhiteLevel;
                SetUserBlackLevel(dArr, options);
                if (DecodeDNGImageBufferJNI(bArr, createBitmap, available, dArr, dArr2, options) < 0) {
                    fileInputStream.close();
                    return null;
                }
                fileInputStream.close();
                return createBitmap;
            }
            fileInputStream.close();
            return null;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, SemQrBitmapFactory.Options options) throws IOException {
        int available = inputStream.available();
        byte[] bArr = new byte[available];
        if (inputStream.read(bArr, 0, available) == -1) {
            inputStream.close();
            return null;
        }
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (options == null) {
            options = new SemQrBitmapFactory.Options(0);
        }
        SemQrBitmapFactory.Options options2 = options;
        double[] dArr = new double[256];
        if (parseDNGImageBufferJNI(bArr, available, dArr, options2.FileWhiteLevel, options2) < 0) {
            return null;
        }
        SetFileBlackLevel(dArr, options2);
        Bitmap createBitmap = Bitmap.createBitmap(options2.width, options2.height, config);
        if (!options2.inJustDecodeBounds) {
            double[] dArr2 = options2.UserWhiteLevel;
            SetUserBlackLevel(dArr, options2);
            if (DecodeDNGImageBufferJNI(bArr, createBitmap, available, dArr, dArr2, options2) < 0) {
                return null;
            }
            float f = options2.width;
            float f2 = options2.height;
            if (options2.inSampleSize == 0) {
                options2.inSampleSize = 1;
            } else if (options2.inSampleSize < 0) {
                return null;
            }
            if (1 != options2.inSampleSize) {
                return Bitmap.createScaledBitmap(createBitmap, (int) (f / options2.inSampleSize), (int) (f2 / options2.inSampleSize), true);
            }
        }
        return createBitmap;
    }

    public static Bitmap decodeStream(InputStream inputStream, Rect rect, SemQrBitmapFactory.Options options) throws IOException {
        int available = inputStream.available();
        byte[] bArr = new byte[available];
        if (inputStream.read(bArr, 0, available) == -1) {
            inputStream.close();
            return null;
        }
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (options == null) {
            options = new SemQrBitmapFactory.Options(0);
        }
        SemQrBitmapFactory.Options options2 = options;
        double[] dArr = new double[256];
        if (parseDNGImageBufferJNI(bArr, available, dArr, options2.FileWhiteLevel, options2) < 0) {
            return null;
        }
        SetFileBlackLevel(dArr, options2);
        Bitmap createBitmap = Bitmap.createBitmap(options2.width, options2.height, config);
        if (!options2.inJustDecodeBounds) {
            double[] dArr2 = options2.UserWhiteLevel;
            SetUserBlackLevel(dArr, options2);
            if (DecodeDNGImageBufferJNI(bArr, createBitmap, available, dArr, dArr2, options2) < 0) {
                return null;
            }
            float f = options2.width;
            float f2 = options2.height;
            if (options2.inSampleSize == 0) {
                options2.inSampleSize = 1;
            } else if (options2.inSampleSize < 0) {
                return null;
            }
            if (1 != options2.inSampleSize) {
                return Bitmap.createScaledBitmap(createBitmap, (int) (f / options2.inSampleSize), (int) (f2 / options2.inSampleSize), true);
            }
        }
        return createBitmap;
    }

    public static byte[] getDNGPreviewImage(String str) {
        return nativeGetDNGPreviewImageFromFile(str);
    }

    public static byte[] getDNGPreviewImage(byte[] bArr, int i, int i2) {
        return nativeGetDNGPreviewImageFromByteArray(bArr, i, i2);
    }

    public static String getDNGPrivateData(String str) {
        return nativeGetDNGPrivateDataFromFile(str);
    }

    public static String getDNGPrivateData(byte[] bArr, int i, int i2) {
        return nativeGetDNGPrivateDataFromByteArray(bArr, i, i2);
    }
}
