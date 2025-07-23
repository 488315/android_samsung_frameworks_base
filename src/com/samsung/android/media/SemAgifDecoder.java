package com.samsung.android.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class SemAgifDecoder {
    private static final String TAG = "SemAgifDecoder";
    private long mHandle = 0;

    private native int nativeDecodeFrame(long j, Bitmap bitmap);

    private native boolean nativeFinish(long j);

    private native int nativeGetDelay(long j);

    private native int nativeGetHeight(long j);

    private native int nativeGetNumOfFrame(long j);

    private native int nativeGetWidth(long j);

    private native void nativeInitByteArrayHandle(SemAgifDecoder semAgifDecoder, byte[] bArr, int i);

    private native void nativeInitHandle(SemAgifDecoder semAgifDecoder, String str);

    static {
        loadLib();
    }

    public static void loadLib() {
        try {
            System.loadLibrary("agifcodec.media.quram");
            Log.v(TAG, "Load library success : ");
        } catch (Exception e) {
            Log.e(TAG, "Load library fail : " + e.toString());
        }
    }

    public SemAgifDecoder(String str) {
        if (str != null) {
            nativeInitHandle(this, str);
        }
    }

    public SemAgifDecoder(byte[] bArr) {
        if (bArr != null) {
            nativeInitByteArrayHandle(this, bArr, bArr.length);
        }
    }

    public SemAgifDecoder(Context context, int i) {
        try {
            InputStream openRawResource = context.getResources().openRawResource(i);
            try {
                int available = openRawResource.available();
                if (available <= 0) {
                    Log.e(TAG, "inpustream open fail");
                } else {
                    byte[] bArr = new byte[available];
                    openRawResource.read(bArr);
                    nativeInitByteArrayHandle(this, bArr, available);
                }
                if (openRawResource != null) {
                    openRawResource.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException happens");
            e.printStackTrace();
        }
    }

    public SemAgifDecoder(Context context, Uri uri) {
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            try {
                FileInputStream fileInputStream = new FileInputStream(openFileDescriptor.getFileDescriptor());
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr = new byte[65536];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            } else {
                                byteArrayOutputStream.write(bArr, 0, read);
                            }
                        }
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        if (byteArray == null) {
                            Log.e(TAG, "buffer is Null");
                        } else {
                            Log.i(TAG, "buffer size is " + byteArray.length);
                            nativeInitByteArrayHandle(this, byteArray, byteArray.length);
                        }
                        byteArrayOutputStream.close();
                        fileInputStream.close();
                        if (openFileDescriptor != null) {
                            openFileDescriptor.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "IOException happens");
            e.printStackTrace();
        }
    }

    public SemAgifDecoder(InputStream inputStream) {
        int i;
        if (inputStream == null) {
            Log.e(TAG, "inputstream is null");
            return;
        }
        try {
            i = inputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i <= 0) {
            Log.e(TAG, "inpustream open fail");
            return;
        }
        byte[] bArr = new byte[i];
        try {
            inputStream.read(bArr);
        } catch (IOException e2) {
            e2.printStackTrace();
            bArr = null;
        }
        nativeInitByteArrayHandle(this, bArr, bArr.length);
    }

    public int getDelay() {
        return nativeGetDelay(this.mHandle);
    }

    public int getNumOfFrame() {
        return nativeGetNumOfFrame(this.mHandle);
    }

    public int getWidth() {
        return nativeGetWidth(this.mHandle);
    }

    public int getHeight() {
        return nativeGetHeight(this.mHandle);
    }

    public int decodeFrame(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return nativeDecodeFrame(this.mHandle, bitmap);
    }

    public boolean finish() {
        boolean nativeFinish = nativeFinish(this.mHandle);
        this.mHandle = 0L;
        return nativeFinish;
    }
}
