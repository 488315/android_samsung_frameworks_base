package com.samsung.android.media.imagecrop;

import android.graphics.Rect;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.util.Log;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class SemImageCrop {
    private static final int DEFAULT_HEADER_SIZE = 32;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final int IMAGE_FILE_FORMAT_HEIC = 2;
    public static final int IMAGE_FILE_FORMAT_JPEG = 1;
    private static final String TAG = "SemImageCrop";
    private static boolean hasHevcEncoder = false;
    private static SemImageCrop instance = null;
    private static boolean isSupport = false;
    private static boolean isSupportHeifCapture = true;
    private long mNativeHandle;

    private native void nativeFinalize();

    private native int nativeProcess(ByteBuffer byteBuffer, int i, SemCroppedImageInfo semCroppedImageInfo, int i2, int i3, int i4, int i5);

    private native void nativeSetup();

    private SemImageCrop() {
        hasHevcEncoder = hasCodec("video/hevc");
        nativeSetup();
        Log.i(TAG, "setup : " + this.mNativeHandle);
    }

    public static SemImageCrop getInstance() {
        if (isSupport) {
            if (instance == null) {
                instance = new SemImageCrop();
            }
        } else {
            Log.w(TAG, "is not supported");
        }
        return instance;
    }

    public SemCroppedImageInfo crop(ByteBuffer byteBuffer, Rect rect) {
        if (byteBuffer == null) {
            Log.e(TAG, "in is null!");
            return null;
        }
        if (rect == null) {
            Log.e(TAG, "rect is null!");
            return null;
        }
        if (!isValidRect(rect)) {
            Log.e(TAG, "rect is not valid, check rect properties has negative value or width/height is less than or equal to zero!");
            return null;
        }
        if (isHeicFormat(byteBuffer)) {
            if (!hasHevcEncoder || !isSupportHeifCapture) {
                Log.e(TAG, "is heic format, but hevcEncoder : " + hasHevcEncoder + ", supportHeifCapture : " + isSupportHeifCapture);
                return null;
            }
        } else if (!isJpegFormat(byteBuffer)) {
            byte[] bArr = new byte[8];
            byteBuffer.position(0);
            byteBuffer.get(bArr);
            byteBuffer.rewind();
            Log.e(TAG, "image format is not supported!, {" + bytesToHex(bArr, 16) + "}");
            return null;
        }
        SemCroppedImageInfo semCroppedImageInfo = new SemCroppedImageInfo(byteBuffer.limit() * 3);
        int iNativeProcess = nativeProcess(byteBuffer, byteBuffer.limit(), semCroppedImageInfo, rect.left, rect.top, rect.right, rect.bottom);
        Log.d(TAG, "outLength : " + iNativeProcess);
        semCroppedImageInfo.reAllocInJavaBuffer(iNativeProcess);
        if (semCroppedImageInfo.getWidth() > 0 && semCroppedImageInfo.getHeight() > 0) {
            return semCroppedImageInfo;
        }
        Log.e(TAG, "cropping is failed!");
        return null;
    }

    public SemCroppedImageInfo crop(FileDescriptor fileDescriptor, Rect rect) throws IOException {
        SemCroppedImageInfo semCroppedImageInfoCrop = null;
        if (fileDescriptor == null) {
            Log.e(TAG, "fd is null!");
            return null;
        }
        if (rect == null) {
            Log.e(TAG, "rect is null!");
            return null;
        }
        if (!isValidRect(rect)) {
            Log.e(TAG, "rect is not valid, check rect properties has negative value or width/height is less than or equal to zero!");
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                FileChannel channel = fileInputStream.getChannel();
                ByteBuffer byteBufferAllocNativeBuffer = NativeBuffer.allocNativeBuffer(channel.size());
                int i = 0;
                while (byteBufferAllocNativeBuffer.hasRemaining()) {
                    i += channel.read(byteBufferAllocNativeBuffer);
                    Log.d(TAG, "read : " + i);
                }
                channel.close();
                semCroppedImageInfoCrop = crop(byteBufferAllocNativeBuffer, rect);
                NativeBuffer.freeNativeBuffer(byteBufferAllocNativeBuffer);
                fileInputStream.close();
                return semCroppedImageInfoCrop;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return semCroppedImageInfoCrop;
        }
    }

    public int[] getSupportedFormat() {
        if (!hasHevcEncoder || !isSupportHeifCapture) {
            return new int[]{1};
        }
        return new int[]{1, 2};
    }

    protected void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
        Log.d(TAG, "finalize");
    }

    private boolean isValidRect(Rect rect) {
        return rect.left >= 0 && rect.right >= 0 && rect.top >= 0 && rect.bottom >= 0 && rect.width() > 0 && rect.height() > 0;
    }

    private static boolean isHeicFormat(ByteBuffer byteBuffer) {
        byte[] bArr = {102, 116, 121, SprAttributeBase.TYPE_SHADOW};
        byte[] bArr2 = {104, 101, 105, 99};
        byte[] bArr3 = new byte[4];
        byte[] bArr4 = new byte[4];
        byteBuffer.position(4);
        byteBuffer.get(bArr3);
        if (!Arrays.equals(bArr3, bArr)) {
            byteBuffer.rewind();
            return false;
        }
        for (int i = 8; i <= 28; i += 4) {
            byteBuffer.get(bArr4);
            if (Arrays.equals(bArr4, bArr2)) {
                byteBuffer.rewind();
                return true;
            }
        }
        byteBuffer.rewind();
        return false;
    }

    private static boolean isJpegFormat(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[3];
        byteBuffer.position(0);
        byteBuffer.get(bArr);
        byteBuffer.rewind();
        return Arrays.equals(bArr, new byte[]{-1, -40, -1});
    }

    private static boolean hasCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static {
        try {
            System.loadLibrary("semimagecrop_jni.media.samsung");
            isSupport = true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            isSupport = false;
        }
    }

    private static String bytesToHex(byte[] bArr, int i) {
        if (bArr == null || bArr.length == 0) {
            return "0:null";
        }
        try {
            if (bArr.length < i) {
                i = bArr.length;
            }
            int i2 = i * 3;
            char[] cArr = new char[i2 + 12];
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                byte b = bArr[i3];
                char[] cArr2 = HEX_ARRAY;
                cArr[i4] = cArr2[(b & 255) >>> 4];
                cArr[i4 + 1] = cArr2[b & 15];
                cArr[i4 + 2] = ' ';
                i3++;
                i4 += 3;
            }
            return bArr.length + ":" + new String(cArr, 0, i2 - 1);
        } catch (Exception unused) {
            return bArr.length + ":ERROR";
        }
    }
}
