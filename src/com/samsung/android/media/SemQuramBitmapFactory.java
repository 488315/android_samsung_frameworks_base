package com.samsung.android.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.scontext.SContextConstants;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.samsung.android.media.SemQrBitmapFactory;
import com.samsung.android.media.SemQuramImageBufferData;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes6.dex */
public class SemQuramBitmapFactory {
    private static final boolean DEBUG = false;
    public static final int DEC_CANCELED = 6;
    public static final int DEC_FAIL = 0;
    public static final int DEC_PROGRESS = 4;
    public static final int DEC_SUCCESS = 1;
    public static final int LENGTH_OF_MID_POINTER = 68;
    public static final String Quram_JPEG = "Quram_JPEG";
    private static final String TAG = "SemQuramBitmapFactory";
    protected static final int USE_AUTO_BUFFERMODE = 2;
    protected static final int USE_AUTO_FILEMODE = 0;
    public static final int USE_FULLSIZE_BUFFER = 0;
    public static final int USE_ITERSIZE_BUFFER = 1;
    public static final int USE_MAKE_REGIONMAP = 2;
    protected static final int USE_POWER_PROCESS = 1;

    public static native double CompareJPEG(Bitmap bitmap, Bitmap bitmap2, String str, int i, int i2, long j, long j2, long j3, int i3);

    public static native void DecodeCancel(long j);

    public static native int DecodeJPEGThumbnail(long j, Bitmap bitmap, int i, int i2, int i3);

    static native int DecodeJPEGThumbnailToNativeBuffer(long j, long[] jArr, int i, int i2);

    public static native int PDecodeJPEGFromFile(long j, Bitmap bitmap, int i, int i2, int i3);

    public static native void RegionMapCancel(long j);

    public static native void asyncTest(int i);

    public static native int nativeCreateDecBufferInfo(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options);

    public static native int nativeCreateDecFileInfo(String str, SemQrBitmapFactory.Options options, int i);

    public static native Bitmap nativeDecodeByteArray2(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options);

    public static native Bitmap nativeDecodeByteArray3(byte[] bArr, int i, int i2, int i3, SemQrBitmapFactory.Options options);

    public static native int nativeDecodeByteArrayToBuffer(byte[] bArr, ByteBuffer byteBuffer, int i, int i2, SemQrBitmapFactory.Options options);

    public static native Bitmap nativeDecodeFile2(String str, SemQrBitmapFactory.Options options);

    public static native int nativeDecodeFileToBuffer(String str, ByteBuffer byteBuffer, SemQrBitmapFactory.Options options);

    public static native int nativeDecodeJPEG(long j, Bitmap bitmap, int i, int i2, int i3);

    public static native int nativeEncodeByteArray(Bitmap bitmap, byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options);

    public static native int nativeEncodeFile(Bitmap bitmap, String str, int i, int i2, int i3, SemQrBitmapFactory.Options options);

    public static native int nativeEncodeFile2(Bitmap bitmap, byte[] bArr, int i, String str, int i2, int i3, int i4, SemQrBitmapFactory.Options options);

    public static native int nativeEncodeFileFromByte(byte[] bArr, String str, int i, int i2, int i3, SemQrBitmapFactory.Options options);

    public static native int nativeGetExifData(String str, long j, SemQrBitmapFactory.Options options);

    public static native byte[] nativeGetICCProfile(String str);

    public static native int nativeGetImageInfoFromBuffer(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options);

    public static native int nativeGetImageInfoFromFile(String str, SemQrBitmapFactory.Options options);

    public static native Bitmap nativePartialDecodeByteArray(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, SemQrBitmapFactory.Options options);

    public static native int nativePartialDecodeByteArrayToBuffer(byte[] bArr, ByteBuffer byteBuffer, int i, int i2, int i3, int i4, int i5, int i6, SemQrBitmapFactory.Options options);

    public static native Bitmap nativePartialDecodeFile(String str, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options);

    public static native int nativeResizeEncodeByteArray(Bitmap bitmap, byte[] bArr, int i, int i2, int i3, int i4, int i5, SemQrBitmapFactory.Options options);

    public static native int nativeResizeEncodeFD(Bitmap bitmap, FileDescriptor fileDescriptor, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options);

    public static native int nativeResizeEncodeFile(Bitmap bitmap, String str, int i, int i2, int i3, int i4, int i5, int i6, SemQrBitmapFactory.Options options);

    public static native int nativeResizeEncodeFile2(Bitmap bitmap, byte[] bArr, int i, String str, int i2, int i3, int i4, int i5, int i6, int i7, SemQrBitmapFactory.Options options);

    public static native void recycleNativeBuffer(long j);

    static {
        try {
            Log.i(TAG, " start loadLibrary ");
            System.loadLibrary(SemQuramValue.QRV_LIBRARY_NAME);
            Log.i(TAG, " end loadLibrary");
        } catch (Exception e) {
            Log.e(TAG, "Load library fail : " + e.toString());
        }
    }

    public static int round(float f) {
        return Math.round(f);
    }

    public static Bitmap decodeFileFromThumbnail(String str, SemQrBitmapFactory.Options options, int i, int i2, int i3) {
        if (options.getHandle() != 0) {
            return null;
        }
        options.setWidth(i);
        options.setHeight(i2);
        Bitmap bitmapNativeDecodeFile2 = nativeDecodeFile2(str, options);
        options.setHandle(0L);
        return bitmapNativeDecodeFile2;
    }

    public static ByteBuffer decodeFileToBuffer(String str, SemQrBitmapFactory.Options options, int i, int i2) {
        return decodeFileToBuffer(str, options, i, i2, 0);
    }

    public static ByteBuffer decodeFileToBuffer(String str, SemQrBitmapFactory.Options options, int i, int i2, int i3) {
        ByteBuffer byteBufferAllocateDirect;
        if (options.getHandle() != 0) {
            return null;
        }
        if (options.inPreferredConfig == 7) {
            byteBufferAllocateDirect = ByteBuffer.allocateDirect(i * i2 * 4);
        } else if (options.inPreferredConfig == 0) {
            byteBufferAllocateDirect = ByteBuffer.allocateDirect(i * i2 * 2);
        } else if (options.inPreferredConfig == 2) {
            byteBufferAllocateDirect = ByteBuffer.allocateDirect((i * i2) + (((i + 1) >> 1) * ((i2 + 1) >> 1) * 2));
        } else {
            options.setHandle(0L);
            return null;
        }
        byteBufferAllocateDirect.order(ByteOrder.BIG_ENDIAN);
        options.setWidth(i);
        options.setHeight(i2);
        int iNativeDecodeFileToBuffer = nativeDecodeFileToBuffer(str, byteBufferAllocateDirect, options);
        options.setHandle(0L);
        if (iNativeDecodeFileToBuffer == 0) {
            return null;
        }
        return byteBufferAllocateDirect;
    }

    public static byte[] getICCProfile(String str) {
        return nativeGetICCProfile(str);
    }

    public static Bitmap decodeFile(String str, SemQrBitmapFactory.Options options, int i, int i2, int i3) {
        if (options.getHandle() != 0) {
            return null;
        }
        options.setWidth(i);
        options.setHeight(i2);
        Bitmap bitmapNativeDecodeFile2 = nativeDecodeFile2(str, options);
        options.setHandle(0L);
        return bitmapNativeDecodeFile2;
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options) {
        if (i < 0 || i2 <= 0 || bArr.length < i2 + i || options.getHandle() != 0) {
            return null;
        }
        return nativeDecodeByteArray2(bArr, i, i2, options);
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options) {
        if (i < 0 || i2 <= 0 || bArr.length < i2 + i || options.getHandle() != 0) {
            return null;
        }
        options.setWidth(i3);
        options.setHeight(i4);
        return nativeDecodeByteArray2(bArr, i, i2, options);
    }

    public static Bitmap decodeStream(InputStream inputStream, int i, int i2, SemQrBitmapFactory.Options options) throws IOException {
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return null;
        }
        try {
            int iAvailable = inputStream.available();
            if (iAvailable <= 0) {
                Log.e(TAG, "inpustream open fail");
                return null;
            }
            byte[] bArr = new byte[iAvailable];
            if (inputStream.read(bArr) == -1) {
                return null;
            }
            if (options.getHandle() != 0) {
                Log.e(TAG, "option Fail");
                return null;
            }
            options.setWidth(i);
            options.setHeight(i2);
            return nativeDecodeByteArray2(bArr, 0, iAvailable, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, int i, int i2, int i3, SemQrBitmapFactory.Options options) throws IOException {
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return null;
        }
        try {
            int iAvailable = inputStream.available();
            if (iAvailable <= 0) {
                Log.e(TAG, "inpustream open fail");
                return null;
            }
            byte[] bArr = new byte[iAvailable];
            if (inputStream.read(bArr) == -1) {
                return null;
            }
            if (options.getHandle() != 0) {
                Log.e(TAG, "option Fail");
                return null;
            }
            options.setWidth(i);
            options.setHeight(i2);
            return nativeDecodeByteArray3(bArr, 0, iAvailable, i3, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static int decodeThumbnailByteArrayToBuffer(byte[] bArr, int i, int i2, SemQuramImageBufferData semQuramImageBufferData, SemQrBitmapFactory.Options options) {
        if (i >= 0 && i2 > 0 && bArr.length >= i2 + i && semQuramImageBufferData != null && options.getHandle() == 0) {
            int iNativeGetImageInfoFromBuffer = nativeGetImageInfoFromBuffer(bArr, i, i2, options);
            if (options.inSampleSize == 0) {
                options.inSampleSize = 1;
            }
            int iRound = round(options.getWidth() / options.inSampleSize);
            int iRound2 = round(options.getHeight() / options.inSampleSize);
            if (iNativeGetImageInfoFromBuffer == 0) {
                options.setHandle(0L);
                return 0;
            }
            if (options.inPreferredConfig == 7) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect(iRound * iRound2 * 4);
            } else if (options.inPreferredConfig == 0) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect(iRound * iRound2 * 2);
            } else if (options.inPreferredConfig == 2) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect((iRound * iRound2) + (((iRound + 1) >> 1) * ((iRound2 + 1) >> 1) * 2));
            } else {
                options.setHandle(0L);
            }
            semQuramImageBufferData.buffer.order(ByteOrder.BIG_ENDIAN);
            options.setWidth(iRound);
            options.setHeight(iRound2);
            nativeDecodeByteArrayToBuffer(bArr, semQuramImageBufferData.buffer, i, i2, options);
            if (semQuramImageBufferData.buffer == null) {
                return 0;
            }
            semQuramImageBufferData.width = options.getWidth();
            semQuramImageBufferData.height = options.getHeight();
            options.setHandle(0L);
            return 1;
        }
        return 0;
    }

    public static int decodeImageToBuffer(String str, int i, int i2, SemQrBitmapFactory.Options options, SemQuramImageBufferData semQuramImageBufferData) {
        if (str == null || i <= 0 || i2 <= 0 || semQuramImageBufferData == null || options == null || options.getHandle() != 0) {
            return 0;
        }
        int iNativeCreateDecFileInfo = nativeCreateDecFileInfo(str, options, 2);
        if (iNativeCreateDecFileInfo != 0) {
            long[] jArr = new long[1];
            int iDecodeJPEGThumbnailToNativeBuffer = DecodeJPEGThumbnailToNativeBuffer(options.getHandle(), jArr, i, i2);
            if (iDecodeJPEGThumbnailToNativeBuffer == 1) {
                semQuramImageBufferData.type = SemQuramImageBufferData.Type.HANDLE;
                semQuramImageBufferData.width = i;
                semQuramImageBufferData.height = i2;
                semQuramImageBufferData.handle = jArr[0];
            }
            iNativeCreateDecFileInfo = iDecodeJPEGThumbnailToNativeBuffer;
        }
        options.setHandle(0L);
        return iNativeCreateDecFileInfo;
    }

    public static Bitmap decodeFile(String str, SemQrBitmapFactory.Options options) {
        Bitmap bitmapNativeDecodeFile2 = null;
        if (options.getHandle() != 0) {
            return null;
        }
        if (options.inSampleSize > 8) {
            int i = options.inSampleSize;
            options.inSampleSize = 8;
            Bitmap bitmapNativeDecodeFile22 = nativeDecodeFile2(str, options);
            if (bitmapNativeDecodeFile22 != null) {
                if (options.getWidth() < i || options.getHeight() < i) {
                    return null;
                }
                bitmapNativeDecodeFile2 = Bitmap.createScaledBitmap(bitmapNativeDecodeFile22, (int) ((options.getWidth() / i) + 0.5d), (int) ((options.getHeight() / i) + 0.5d), false);
                options.inSampleSize = i;
            }
        } else {
            bitmapNativeDecodeFile2 = nativeDecodeFile2(str, options);
        }
        options.setHandle(0L);
        return bitmapNativeDecodeFile2;
    }

    public static Bitmap partialDecodeByteArray(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options, int i3, int i4, int i5, int i6) {
        int i7 = options.inSampleSize;
        if (i < 0 || i2 <= 0 || bArr.length < i2 + i || options.getHandle() != 0) {
            return null;
        }
        if (options.inSampleSize == 0) {
            options.inSampleSize = 1;
        } else if (options.inSampleSize > 8) {
            options.inSampleSize = 8;
        }
        Bitmap bitmapNativePartialDecodeByteArray = nativePartialDecodeByteArray(bArr, i, i2, i3, i5, i4 - i3, i6 - i5, options);
        options.setHandle(0L);
        int width = options.getWidth();
        int height = options.getHeight();
        if (bitmapNativePartialDecodeByteArray == null) {
            return null;
        }
        if (options.inSampleSize >= i7) {
            return bitmapNativePartialDecodeByteArray;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapNativePartialDecodeByteArray, (width * options.inSampleSize) / i7, (height * options.inSampleSize) / i7, false);
        bitmapNativePartialDecodeByteArray.recycle();
        options.inSampleSize = i7;
        return bitmapCreateScaledBitmap;
    }

    public static int partialDecodeByteArrayToBuffer(byte[] bArr, int i, int i2, SemQrBitmapFactory.Options options, int i3, int i4, int i5, int i6, SemQuramImageBufferData semQuramImageBufferData) {
        if (i >= 0 && i2 > 0 && bArr.length >= i2 + i && options.getHandle() == 0 && options.inSampleSize <= 8 && options.inSampleSize >= 0 && semQuramImageBufferData != null) {
            if (options.inSampleSize == 0) {
                options.inSampleSize = 1;
            } else if (options.inSampleSize > 8) {
                options.inSampleSize = 8;
            }
            int iNativeGetImageInfoFromBuffer = nativeGetImageInfoFromBuffer(bArr, i, i2, options);
            int iRound = round(options.getWidth() / options.inSampleSize);
            int iRound2 = round(options.getHeight() / options.inSampleSize);
            if (iNativeGetImageInfoFromBuffer == 0) {
                options.setHandle(0L);
                return 0;
            }
            if (options.inPreferredConfig == 7) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect(iRound * iRound2 * 4);
            } else if (options.inPreferredConfig == 0) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect(iRound * iRound2 * 2);
            } else if (options.inPreferredConfig == 2) {
                semQuramImageBufferData.buffer = ByteBuffer.allocateDirect((iRound * iRound2) + (((iRound + 1) >> 1) * ((iRound2 + 1) >> 1) * 2));
            } else {
                options.setHandle(0L);
            }
            semQuramImageBufferData.buffer.order(ByteOrder.BIG_ENDIAN);
            options.setWidth(iRound);
            options.setHeight(iRound2);
            nativePartialDecodeByteArrayToBuffer(bArr, semQuramImageBufferData.buffer, i, i2, i3, i5, i4 - i3, i6 - i5, options);
            options.setHandle(0L);
            return semQuramImageBufferData.buffer == null ? 0 : 1;
        }
        return 0;
    }

    public static int getExifData(String str, SemQrBitmapFactory.Options options) {
        if (str == null || options == null) {
            return 0;
        }
        int iNativeGetExifData = nativeGetExifData(str, options.getHandle(), options);
        if (iNativeGetExifData == 0) {
            options.setExif(0L);
        }
        return iNativeGetExifData;
    }

    public static Bitmap partialDecodeFile(String str, SemQrBitmapFactory.Options options, int i, int i2, int i3, int i4) {
        int i5 = options.inSampleSize;
        if (options.getHandle() != 0) {
            return null;
        }
        if (options.inSampleSize == 0) {
            options.inSampleSize = 1;
        } else if (options.inSampleSize > 8) {
            options.inSampleSize = 8;
        }
        int i6 = i2 - i;
        int i7 = i4 - i3;
        Bitmap bitmapNativePartialDecodeFile = nativePartialDecodeFile(str, i, i3, i6, i7, options);
        options.setHandle(0L);
        if (bitmapNativePartialDecodeFile == null) {
            return null;
        }
        options.getWidth();
        options.getHeight();
        if (options.inSampleSize >= i5) {
            return bitmapNativePartialDecodeFile;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapNativePartialDecodeFile, ((i6 / options.inSampleSize) * options.inSampleSize) / i5, ((i7 / options.inSampleSize) * options.inSampleSize) / i5, false);
        bitmapNativePartialDecodeFile.recycle();
        options.inSampleSize = i5;
        return bitmapCreateScaledBitmap;
    }

    public static int compressToByte(Bitmap bitmap, String str, byte[] bArr, int i, int i2) {
        if (str.equals("Quram_JPEG")) {
            return nativeEncodeByteArray(bitmap, bArr, i, i2, null);
        }
        return 0;
    }

    public static int resizeCompressToByte(Bitmap bitmap, String str, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        if (str.equals("Quram_JPEG")) {
            return nativeResizeEncodeByteArray(bitmap, bArr, i, i2, i3, i4, i5, null);
        }
        return 0;
    }

    public static int resizeCompressToFD(Bitmap bitmap, String str, FileDescriptor fileDescriptor, int i, int i2, int i3, int i4) {
        if (str.equals("Quram_JPEG")) {
            return nativeResizeEncodeFD(bitmap, fileDescriptor, i, i2, i3, i4, null);
        }
        return 0;
    }

    public static int resizeCompressToURI(Bitmap bitmap, String str, Context context, Uri uri, int i, int i2, int i3, int i4) {
        int iNativeResizeEncodeFD = 0;
        if (!str.equals("Quram_JPEG")) {
            return 0;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "w");
            try {
                iNativeResizeEncodeFD = nativeResizeEncodeFD(bitmap, parcelFileDescriptorOpenFileDescriptor.getFileDescriptor(), i, i2, i3, i4, null);
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
                return iNativeResizeEncodeFD;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return iNativeResizeEncodeFD;
        }
    }

    public static int compressToFile(Bitmap bitmap, String str, String str2, int i, SemQrBitmapFactory.Options options) {
        if (str.equals("Quram_JPEG")) {
            return nativeEncodeFile(bitmap, str2, bitmap.getWidth(), bitmap.getHeight(), i, options);
        }
        return 0;
    }

    public static int compressToFile(Bitmap bitmap, byte[] bArr, String str, String str2, int i, SemQrBitmapFactory.Options options) {
        return nativeEncodeFile2(bitmap, bArr, bArr != null ? bArr.length : 0, str2, bitmap.getWidth(), bitmap.getHeight(), i, options);
    }

    public static int resizeCompressToFile(Bitmap bitmap, String str, String str2, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options) {
        if (str.equals("Quram_JPEG")) {
            return nativeResizeEncodeFile(bitmap, str2, bitmap.getWidth(), bitmap.getHeight(), i, i2, i3, i4, options);
        }
        return 0;
    }

    public static int resizeCompressToFile(Bitmap bitmap, byte[] bArr, String str, String str2, int i, int i2, int i3, int i4, SemQrBitmapFactory.Options options) {
        int length = bArr != null ? bArr.length : 0;
        if (str.equals("Quram_JPEG")) {
            return nativeResizeEncodeFile2(bitmap, bArr, length, str2, bitmap.getWidth(), bitmap.getHeight(), i, i2, i3, i4, options);
        }
        return 0;
    }

    public static int compressToFile(byte[] bArr, String str, String str2, int i, int i2, int i3, SemQrBitmapFactory.Options options) {
        if (str.equals("Quram_JPEG")) {
            return nativeEncodeFileFromByte(bArr, str2, i, i2, i3, options);
        }
        return 0;
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        return decodeFileDescriptor(fileDescriptor, null, new SemQrBitmapFactory.Options());
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, SemQrBitmapFactory.Options options) {
        int iAvailable;
        byte[] bArr;
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
        if (options.getHandle() != 0) {
            Log.e(TAG, "option Fail");
            try {
                fileInputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "" + e.toString());
            }
            return null;
        }
        try {
            iAvailable = fileInputStream.available();
        } catch (IOException e2) {
            e = e2;
            iAvailable = 0;
        }
        try {
            bArr = new byte[iAvailable];
        } catch (IOException e3) {
            e = e3;
            bArr = null;
            e.printStackTrace();
            Bitmap bitmapNativeDecodeByteArray2 = nativeDecodeByteArray2(bArr, 0, iAvailable, options);
            options.setHandle(0L);
            fileInputStream.close();
            return bitmapNativeDecodeByteArray2;
        }
        try {
            if (fileInputStream.read(bArr) == -1) {
                fileInputStream.close();
                return null;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            Bitmap bitmapNativeDecodeByteArray22 = nativeDecodeByteArray2(bArr, 0, iAvailable, options);
            options.setHandle(0L);
            fileInputStream.close();
            return bitmapNativeDecodeByteArray22;
        }
        Bitmap bitmapNativeDecodeByteArray222 = nativeDecodeByteArray2(bArr, 0, iAvailable, options);
        options.setHandle(0L);
        try {
            fileInputStream.close();
            return bitmapNativeDecodeByteArray222;
        } catch (Exception e5) {
            Log.e(TAG, "" + e5.toString());
            return null;
        }
    }

    public static double CompareJPEGs(Bitmap bitmap, Bitmap bitmap2, String str, int i, int i2, long j, long j2, long j3, int i3) {
        if (bitmap == null || bitmap2 == null) {
            Log.e("QURAM", "bm1 = " + bitmap + ", bm2 = " + bitmap2);
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        if (str != null) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        Log.e("QURAM", "fileName = " + str);
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }
}
