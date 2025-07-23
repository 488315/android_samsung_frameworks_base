package com.samsung.android.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemQrBitmapFactory {
    private static final boolean DEBUG = false;
    public static final String Quram_JPEG = "Quram_JPEG";
    private static final String TAG = "SemQrBitmapFactory";

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, Options options) {
        Bitmap decodeByteArray = SemQuramBitmapFactory.decodeByteArray(bArr, i, i2, options);
        if ((options.inJustDecodeBounds && (options.outWidth > 0 || options.outHeight > 0)) || decodeByteArray != null) {
            return decodeByteArray;
        }
        Bitmap decodeByteArray2 = SemQuramDngBitmap.decodeByteArray(bArr, i, i2, options);
        if (options.outWidth <= 0 && options.outHeight <= 0) {
            return decodeByteArray2;
        }
        options.outMimeType = "image/dng";
        return decodeByteArray2;
    }

    public static Bitmap decodeStream(InputStream inputStream, Rect rect, Options options) {
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return null;
        }
        try {
            int available = inputStream.available();
            if (available <= 0) {
                Log.e(TAG, "inpustream open fail");
                return null;
            }
            byte[] bArr = new byte[available];
            if (inputStream.read(bArr) == -1) {
                return null;
            }
            Bitmap decodeByteArray = SemQuramBitmapFactory.decodeByteArray(bArr, 0, available, options);
            if ((!options.inJustDecodeBounds || (options.outWidth <= 0 && options.outHeight <= 0)) && decodeByteArray == null) {
                Bitmap decodeByteArray2 = SemQuramDngBitmap.decodeByteArray(bArr, 0, available, options);
                if (options.outWidth <= 0 && options.outHeight <= 0) {
                    return decodeByteArray2;
                }
                options.outMimeType = "image/dng";
                return decodeByteArray2;
            }
            return decodeByteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, Options options) {
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return null;
        }
        try {
            int available = inputStream.available();
            if (available <= 0) {
                Log.e(TAG, "inpustream open fail");
                return null;
            }
            byte[] bArr = new byte[available];
            if (inputStream.read(bArr) == -1) {
                return null;
            }
            Bitmap decodeByteArray = SemQuramBitmapFactory.decodeByteArray(bArr, 0, available, options);
            if ((!options.inJustDecodeBounds || (options.outWidth <= 0 && options.outHeight <= 0)) && decodeByteArray == null) {
                Bitmap decodeByteArray2 = SemQuramDngBitmap.decodeByteArray(bArr, 0, available, options);
                if (options.outWidth <= 0 && options.outHeight <= 0) {
                    return decodeByteArray2;
                }
                options.outMimeType = "image/dng";
                return decodeByteArray2;
            }
            return decodeByteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, int i, int i2, Options options) {
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return null;
        }
        try {
            int available = inputStream.available();
            if (available <= 0) {
                Log.e(TAG, "inpustream open fail");
                return null;
            }
            byte[] bArr = new byte[available];
            if (inputStream.read(bArr) == -1) {
                return null;
            }
            Bitmap decodeByteArray = SemQuramBitmapFactory.decodeByteArray(bArr, 0, available, i, i2, options);
            if ((!options.inJustDecodeBounds || (options.outWidth <= 0 && options.outHeight <= 0)) && decodeByteArray == null) {
                decodeByteArray = SemQuramDngBitmap.decodeByteArray(bArr, 0, available, i, i2, options);
                if (options.outWidth <= 0 && options.outHeight <= 0) {
                    return decodeByteArray;
                }
                options.outMimeType = "image/dng";
            }
            return decodeByteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeFile(String str, Options options) {
        Bitmap decodeFile = SemQuramBitmapFactory.decodeFile(str, options);
        if ((options.inJustDecodeBounds && (options.outWidth > 0 || options.outHeight > 0)) || decodeFile != null) {
            return decodeFile;
        }
        try {
            Bitmap decodeFile2 = SemQuramDngBitmap.decodeFile(str, options);
            if (options.outWidth <= 0 && options.outHeight <= 0) {
                return decodeFile2;
            }
            options.outMimeType = "image/dng";
            return decodeFile2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        Bitmap decodeFileDescriptor = SemQuramBitmapFactory.decodeFileDescriptor(fileDescriptor);
        if (decodeFileDescriptor != null) {
            return decodeFileDescriptor;
        }
        try {
            return SemQuramDngBitmap.decodeFileDescriptor(fileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, Options options) {
        Bitmap decodeFileDescriptor = SemQuramBitmapFactory.decodeFileDescriptor(fileDescriptor, rect, options);
        if ((options.inJustDecodeBounds && (options.outWidth > 0 || options.outHeight > 0)) || decodeFileDescriptor != null) {
            return decodeFileDescriptor;
        }
        try {
            return SemQuramDngBitmap.decodeFileDescriptor(fileDescriptor, rect, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ByteBuffer decodeFileToBuffer(String str, Options options, int i, int i2) {
        return SemQuramBitmapFactory.decodeFileToBuffer(str, options, i, i2);
    }

    public static ByteBuffer decodeFileToBuffer(String str, Options options, int i, int i2, int i3) {
        return SemQuramBitmapFactory.decodeFileToBuffer(str, options, i, i2, i3);
    }

    public static Bitmap decodeFile(String str, Options options, int i, int i2, int i3) {
        return SemQuramBitmapFactory.decodeFile(str, options, i, i2, i3);
    }

    public static int decodeThumbnailByteArrayToBuffer(byte[] bArr, int i, int i2, SemQuramImageBufferData semQuramImageBufferData, Options options) {
        return SemQuramBitmapFactory.decodeThumbnailByteArrayToBuffer(bArr, i, i2, semQuramImageBufferData, options);
    }

    public static int decodeImageToBuffer(String str, int i, int i2, Options options, SemQuramImageBufferData semQuramImageBufferData) {
        return SemQuramBitmapFactory.decodeImageToBuffer(str, i, i2, options, semQuramImageBufferData);
    }

    public static Bitmap partialDecodeByteArray(byte[] bArr, int i, int i2, Options options, int i3, int i4, int i5, int i6) {
        return SemQuramBitmapFactory.partialDecodeByteArray(bArr, i, i2, options, i3, i4, i5, i6);
    }

    public static int partialDecodeByteArrayToBuffer(byte[] bArr, int i, int i2, Options options, int i3, int i4, int i5, int i6, SemQuramImageBufferData semQuramImageBufferData) {
        return SemQuramBitmapFactory.partialDecodeByteArrayToBuffer(bArr, i, i2, options, i3, i4, i5, i6, semQuramImageBufferData);
    }

    public static Bitmap partialDecodeFile(String str, Options options, int i, int i2, int i3, int i4) {
        return SemQuramBitmapFactory.partialDecodeFile(str, options, i, i2, i3, i4);
    }

    public static int compressToByte(Bitmap bitmap, String str, byte[] bArr, int i, int i2) {
        return SemQuramBitmapFactory.compressToByte(bitmap, str, bArr, i, i2);
    }

    public static byte[] getICCProfile(String str) {
        return SemQuramBitmapFactory.getICCProfile(str);
    }

    public static int getExifData(String str, Options options) {
        return SemQuramBitmapFactory.getExifData(str, options);
    }

    public static int resizeCompressToByte(Bitmap bitmap, String str, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        return SemQuramBitmapFactory.resizeCompressToByte(bitmap, str, bArr, i, i2, i3, i4, i5);
    }

    public static int resizeCompressToFD(Bitmap bitmap, String str, FileDescriptor fileDescriptor, int i, int i2, int i3, int i4) {
        return SemQuramBitmapFactory.resizeCompressToFD(bitmap, str, fileDescriptor, i, i2, i3, i4);
    }

    public static int resizeCompressToURI(Bitmap bitmap, String str, Context context, Uri uri, int i, int i2, int i3, int i4) {
        return SemQuramBitmapFactory.resizeCompressToURI(bitmap, str, context, uri, i, i2, i3, i4);
    }

    public static int compressToFile(Bitmap bitmap, String str, String str2, int i, Options options) {
        return SemQuramBitmapFactory.compressToFile(bitmap, str, str2, i, options);
    }

    public static int compressToFile(Bitmap bitmap, byte[] bArr, String str, String str2, int i, Options options) {
        return SemQuramBitmapFactory.compressToFile(bitmap, bArr, str, str2, i, options);
    }

    public static int resizeCompressToFile(Bitmap bitmap, String str, String str2, int i, int i2, int i3, int i4, Options options) {
        return SemQuramBitmapFactory.resizeCompressToFile(bitmap, str, str2, i, i2, i3, i4, options);
    }

    public static int resizeCompressToFile(Bitmap bitmap, byte[] bArr, String str, String str2, int i, int i2, int i3, int i4, Options options) {
        return SemQuramBitmapFactory.resizeCompressToFile(bitmap, bArr, str, str2, i, i2, i3, i4, options);
    }

    public static int compressToFile(byte[] bArr, String str, String str2, int i, int i2, int i3, Options options) {
        return SemQuramBitmapFactory.compressToFile(bArr, str, str2, i, i2, i3, options);
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2) {
        return SemQuramDngBitmap.decodeByteArray(bArr, i, i2);
    }

    public static int cancelDecoding(Options options) {
        try {
            SemQuramDngBitmap.cancelDecoding(options);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getSECDngVersion(String str) {
        try {
            return SemQuramDngBitmap.getSECDngVersionFromFile(str);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getExifInfoFromFile(String str, SemQuramDngJavaExif semQuramDngJavaExif) {
        try {
            return SemQuramDngBitmap.getExifInfoFromFile(str, semQuramDngJavaExif);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static byte[] getDNGPreviewImage(String str) {
        return SemQuramDngBitmap.getDNGPreviewImage(str);
    }

    public static byte[] getDNGPreviewImage(byte[] bArr, int i, int i2) {
        return SemQuramDngBitmap.getDNGPreviewImage(bArr, i, i2);
    }

    public static String getDNGPrivateData(String str) {
        return SemQuramDngBitmap.getDNGPrivateData(str);
    }

    public static String getDNGPrivateData(byte[] bArr, int i, int i2) {
        return SemQuramDngBitmap.getDNGPrivateData(bArr, i, i2);
    }

    public static class Options {
        public double[][][] FileBlackLevel;
        public int FileBlackLevelRepeatCols;
        public int FileBlackLevelRepeatRows;
        public double[] FileWhiteLevel;
        public int SamplesPerPixel;
        public int UseInfo;
        public double[][][] UserBlackLevel;
        public int UserBlackLevelRepeatCols;
        public int UserBlackLevelRepeatRows;
        public double[] UserWhiteLevel;
        public boolean bRegionDecoder;
        public int count_nr;
        public long handle;
        public int height;
        public boolean inCancelingRequested;
        public int inDecodeFromOption;
        public boolean inDither;
        public int inInputType;
        public boolean inJustDecodeBounds;
        public int inOptimizeCoding;
        public int inPreferredConfig;
        public boolean inPremultiplied;
        public int inQualityOverSpeed;
        public int inSampleSize;
        public boolean inScaled;
        public int mCropHeight;
        public int mCropOriginX;
        public int mCropOriginY;
        public int mCropWidth;
        private long mDecodeEndInfo;
        private long mDecodeHandle;
        private long mDecodeReadInfo;
        private long mDecodeStruct;
        private long mExifHandle;
        private int mHeight;
        private int mWidth;
        public int orientation;
        public int outHeight;
        public String outMimeType;
        public int outWidth;
        public int rd_b;
        public int rd_l;
        public int rd_r;
        public int rd_t;
        public int width;

        protected long getStruct() {
            return this.mDecodeStruct;
        }

        protected long getReadInfo() {
            return this.mDecodeReadInfo;
        }

        protected long getEndInfo() {
            return this.mDecodeEndInfo;
        }

        protected void setStruct(int i) {
            this.mDecodeStruct = i;
        }

        protected void setReadInfo(int i) {
            this.mDecodeReadInfo = i;
        }

        protected void setEndInfo(int i) {
            this.mDecodeEndInfo = i;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public void setWidth(int i) {
            this.mWidth = i;
        }

        public void setHeight(int i) {
            this.mHeight = i;
        }

        public long getHandle() {
            return this.mDecodeHandle;
        }

        public void setHandle(long j) {
            this.mDecodeHandle = j;
        }

        protected long getExif() {
            return this.mExifHandle;
        }

        public void setExif(long j) {
            this.mExifHandle = j;
        }

        public class Config {
            public static final int ARGB_8888 = 7;
            public static final int NV12 = 19;
            public static final int NV21 = 18;
            public static final int RGB_565 = 0;
            public static final int RGB_888 = 1;
            public static final int YUV_420 = 2;
            public static final int YUV_422_H1V2 = 14;
            public static final int YUV_422_H2V1 = 3;
            public static final int YUV_444 = 15;
            public static final int YUYV = 17;

            public Config(Options options) {
            }
        }

        public class InputType {
            public static final int Quram_IO_BUFFER = 1;
            public static final int Quram_IO_FILE = 0;

            public InputType(Options options) {
            }
        }

        public class DecodeFromOption {
            public static final int Quram_AUTOTHUMBNAIL = 0;
            public static final int Quram_ROTATETHUMBNAIL = 3;
            public static final int Quram_USEORGIMG = 1;
            public static final int Quram_USETHUMBNAIL = 2;

            public DecodeFromOption(Options options) {
            }
        }

        public Options(int i) {
            this.inPreferredConfig = 7;
            this.inJustDecodeBounds = false;
            this.outWidth = 0;
            this.outHeight = 0;
            this.outMimeType = null;
            this.inSampleSize = 1;
            this.inQualityOverSpeed = 0;
            this.inInputType = 0;
            this.inDecodeFromOption = 0;
            this.inOptimizeCoding = 0;
            this.orientation = 0;
            this.mDecodeHandle = 0L;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mExifHandle = 0L;
            this.inCancelingRequested = false;
            this.mDecodeStruct = 0L;
            this.mDecodeReadInfo = 0L;
            this.mDecodeEndInfo = 0L;
            this.inDither = false;
            this.inScaled = true;
            this.inPremultiplied = true;
            this.UseInfo = i;
            this.SamplesPerPixel = 0;
            this.UserBlackLevelRepeatRows = 0;
            this.UserBlackLevelRepeatCols = 0;
            this.UserBlackLevel = (double[][][]) Array.newInstance((Class<?>) Double.TYPE, 8, 8, 4);
            this.UserWhiteLevel = new double[4];
            this.FileBlackLevelRepeatRows = 0;
            this.FileBlackLevelRepeatCols = 0;
            this.FileBlackLevel = (double[][][]) Array.newInstance((Class<?>) Double.TYPE, 8, 8, 4);
            this.FileWhiteLevel = new double[4];
            this.rd_t = 0;
            this.rd_b = 0;
            this.rd_l = 0;
            this.rd_r = 0;
            this.count_nr = 0;
            this.handle = 0L;
            this.bRegionDecoder = false;
            this.mCropOriginX = 0;
            this.mCropOriginY = 0;
            this.mCropWidth = 0;
            this.mCropHeight = 0;
        }

        public Options() {
            this.inPreferredConfig = 7;
            this.inJustDecodeBounds = false;
            this.outWidth = 0;
            this.outHeight = 0;
            this.outMimeType = null;
            this.inSampleSize = 1;
            this.inQualityOverSpeed = 0;
            this.inInputType = 0;
            this.inDecodeFromOption = 0;
            this.inOptimizeCoding = 0;
            this.orientation = 0;
            this.mDecodeHandle = 0L;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mExifHandle = 0L;
            this.inCancelingRequested = false;
            this.mDecodeStruct = 0L;
            this.mDecodeReadInfo = 0L;
            this.mDecodeEndInfo = 0L;
            this.inDither = false;
            this.inScaled = true;
            this.inPremultiplied = true;
            this.UseInfo = 0;
            this.SamplesPerPixel = 0;
            this.UserBlackLevelRepeatRows = 0;
            this.UserBlackLevelRepeatCols = 0;
            this.UserBlackLevel = (double[][][]) Array.newInstance((Class<?>) Double.TYPE, 8, 8, 4);
            this.UserWhiteLevel = new double[4];
            this.FileBlackLevelRepeatRows = 0;
            this.FileBlackLevelRepeatCols = 0;
            this.FileBlackLevel = (double[][][]) Array.newInstance((Class<?>) Double.TYPE, 8, 8, 4);
            this.FileWhiteLevel = new double[4];
            this.rd_t = 0;
            this.rd_b = 0;
            this.rd_l = 0;
            this.rd_r = 0;
            this.count_nr = 0;
            this.handle = 0L;
            this.bRegionDecoder = false;
            this.mCropOriginX = 0;
            this.mCropOriginY = 0;
            this.mCropWidth = 0;
            this.mCropHeight = 0;
        }

        public void copyFile2User() {
            this.UserBlackLevelRepeatRows = this.FileBlackLevelRepeatRows;
            this.UserBlackLevelRepeatCols = this.FileBlackLevelRepeatCols;
            for (int i = 0; i < this.UserBlackLevelRepeatRows; i++) {
                for (int i2 = 0; i2 < this.FileBlackLevelRepeatCols; i2++) {
                    for (int i3 = 0; i3 < this.SamplesPerPixel; i3++) {
                        this.UserBlackLevel[i][i2][i3] = this.FileBlackLevel[i][i2][i3];
                    }
                }
            }
            for (int i4 = 0; i4 < this.SamplesPerPixel; i4++) {
                this.UserWhiteLevel[i4] = this.FileWhiteLevel[i4];
            }
        }
    }
}
