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

/* loaded from: classes5.dex */
public class SemImageCrop {
  private static final int DEFAULT_HEADER_SIZE = 32;
  public static final int IMAGE_FILE_FORMAT_HEIC = 2;
  public static final int IMAGE_FILE_FORMAT_JPEG = 1;
  private static final String TAG = "SemImageCrop";
  private static boolean hasHevcEncoder;
  private static SemImageCrop instance;
  private static boolean isSupport;
  private long mNativeHandle;
  private static boolean isSupportHeifCapture = true;
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  private native void nativeFinalize();

  private native int nativeProcess(
      ByteBuffer byteBuffer,
      int i,
      SemCroppedImageInfo semCroppedImageInfo,
      int i2,
      int i3,
      int i4,
      int i5);

  private native void nativeSetup();

  static {
    try {
      System.loadLibrary("semimagecrop_jni.media.samsung");
      isSupport = true;
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      isSupport = false;
    }
  }

  private SemImageCrop() {
    hasHevcEncoder = hasCodec("video/hevc");
    nativeSetup();
    Log.m98i(TAG, "setup : " + this.mNativeHandle);
  }

  public static SemImageCrop getInstance() {
    if (isSupport) {
      if (instance == null) {
        instance = new SemImageCrop();
      }
    } else {
      Log.m102w(TAG, "is not supported");
    }
    return instance;
  }

  public SemCroppedImageInfo crop(ByteBuffer in, Rect rect) {
    if (in == null) {
      Log.m96e(TAG, "in is null!");
      return null;
    }
    if (rect == null) {
      Log.m96e(TAG, "rect is null!");
      return null;
    }
    if (!isValidRect(rect)) {
      Log.m96e(
          TAG,
          "rect is not valid, check rect properties has negative value or width/height is less than"
              + " or equal to zero!");
      return null;
    }
    if (isHeicFormat(in)) {
      if (!hasHevcEncoder || !isSupportHeifCapture) {
        Log.m96e(
            TAG,
            "is heic format, but hevcEncoder : "
                + hasHevcEncoder
                + ", supportHeifCapture : "
                + isSupportHeifCapture);
        return null;
      }
    } else if (!isJpegFormat(in)) {
      byte[] header = new byte[8];
      in.position(0);
      in.get(header);
      in.rewind();
      Log.m96e(TAG, "image format is not supported!, {" + bytesToHex(header, 16) + "}");
      return null;
    }
    SemCroppedImageInfo outInfo = new SemCroppedImageInfo(in.limit() * 3);
    int outLength =
        nativeProcess(in, in.limit(), outInfo, rect.left, rect.top, rect.right, rect.bottom);
    Log.m94d(TAG, "outLength : " + outLength);
    outInfo.reAllocInJavaBuffer(outLength);
    if (outInfo.getWidth() <= 0 || outInfo.getHeight() <= 0) {
      Log.m96e(TAG, "cropping is failed!");
      return null;
    }
    return outInfo;
  }

  public SemCroppedImageInfo crop(FileDescriptor fd, Rect rect) {
    if (fd == null) {
      Log.m96e(TAG, "fd is null!");
      return null;
    }
    if (rect == null) {
      Log.m96e(TAG, "rect is null!");
      return null;
    }
    if (!isValidRect(rect)) {
      Log.m96e(
          TAG,
          "rect is not valid, check rect properties has negative value or width/height is less than"
              + " or equal to zero!");
      return null;
    }
    SemCroppedImageInfo outInfo = null;
    try {
      FileInputStream inStream = new FileInputStream(fd);
      try {
        FileChannel inChannel = inStream.getChannel();
        ByteBuffer inBuffer = NativeBuffer.allocNativeBuffer(inChannel.size());
        int readSize = 0;
        while (inBuffer.hasRemaining()) {
          readSize += inChannel.read(inBuffer);
          Log.m94d(TAG, "read : " + readSize);
        }
        inChannel.close();
        outInfo = crop(inBuffer, rect);
        NativeBuffer.freeNativeBuffer(inBuffer);
        inStream.close();
      } finally {
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return outInfo;
  }

  public int[] getSupportedFormat() {
    if (!hasHevcEncoder || !isSupportHeifCapture) {
      return new int[] {1};
    }
    return new int[] {1, 2};
  }

  protected void finalize() throws Throwable {
    nativeFinalize();
    super.finalize();
    Log.m94d(TAG, "finalize");
  }

  private boolean isValidRect(Rect rect) {
    return rect.left >= 0
        && rect.right >= 0
        && rect.top >= 0
        && rect.bottom >= 0
        && rect.width() > 0
        && rect.height() > 0;
  }

  private static boolean isHeicFormat(ByteBuffer inBuffer) {
    byte[] ftypSignature = {102, 116, 121, SprAttributeBase.TYPE_SHADOW};
    byte[] heicSignature = {104, 101, 105, 99};
    byte[] ftyp = new byte[4];
    byte[] heic = new byte[4];
    inBuffer.position(4);
    inBuffer.get(ftyp);
    if (!Arrays.equals(ftyp, ftypSignature)) {
      inBuffer.rewind();
      return false;
    }
    for (int offset = 4 + 4; offset <= 28; offset += 4) {
      inBuffer.get(heic);
      if (Arrays.equals(heic, heicSignature)) {
        inBuffer.rewind();
        return true;
      }
    }
    inBuffer.rewind();
    return false;
  }

  private static boolean isJpegFormat(ByteBuffer inBuffer) {
    byte[] jpegSignature = {-1, -40, -1};
    byte[] jpeg = new byte[3];
    inBuffer.position(0);
    inBuffer.get(jpeg);
    inBuffer.rewind();
    return Arrays.equals(jpeg, jpegSignature);
  }

  private static boolean hasCodec(String mimeType) {
    int numCodecs = MediaCodecList.getCodecCount();
    for (int i = 0; i < numCodecs; i++) {
      MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
      if (codecInfo.isEncoder()) {
        String[] types = codecInfo.getSupportedTypes();
        for (String str : types) {
          if (str.equalsIgnoreCase(mimeType)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private static String bytesToHex(byte[] bytes, int limit) {
    if (bytes == null || bytes.length == 0) {
      return "0:null";
    }
    int size = limit;
    try {
      if (bytes.length < limit) {
        size = bytes.length;
      }
      char[] hexChars = new char[(size * 3) + 12];
      int i = 0;
      int base = 0;
      while (i < size) {
        int v = bytes[i] & 255;
        char[] cArr = HEX_ARRAY;
        hexChars[base] = cArr[v >>> 4];
        hexChars[base + 1] = cArr[v & 15];
        hexChars[base + 2] = ' ';
        i++;
        base += 3;
      }
      return bytes.length + ":" + new String(hexChars, 0, (size * 3) - 1);
    } catch (Exception e) {
      return bytes.length + ":ERROR";
    }
  }
}
