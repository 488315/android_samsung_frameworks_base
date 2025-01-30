package com.samsung.android.media;

import android.p009os.ParcelFileDescriptor;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class QdioJNI {
  private static final String TAG = "QdioJNI";

  private static native int AddSoundInQdioFile(String str, byte[] bArr, int i, String str2, int i2);

  public static native int DeleteQdioFromFd(int i);

  public static native int DeleteQdioFromFile(String str);

  public static native int[] ParseQdioFd(int i);

  private static native int[] ParseQdioFile(String str);

  private static native long[] ParseQdioFile64(String str);

  private static native int copyAdioData(String str, String str2);

  private static native int getNativeVersion();

  public static native int isQdioFd(int i);

  private static native int isQdioFile(String str);

  static {
    System.loadLibrary("SEF.quram");
  }

  public static boolean checkFileString(String filename) {
    return filename != null && filename.length() > 0;
  }

  public static long[] getAudioDataPositionArray(String filename) {
    if (!checkFileString(filename)) {
      Log.m96e(TAG, "getAudioPositionArray input parameter is null : filename = " + filename);
      return null;
    }
    int[] getParsedData = ParseQdioFile(filename);
    if (getParsedData == null) {
      return null;
    }
    if (getParsedData.length % 2 != 0) {
      Log.m96e(TAG, "Some Sound Data is broken");
      return null;
    }
    long[] ret = new long[2];
    ret[0] = getParsedData[0];
    ret[1] = getParsedData[1] - ret[0];
    return ret;
  }

  public static SemExtendedFormat.AudioJPEGData getAudioDataInJPEG(String filename) {
    if (!checkFileString(filename)) {
      Log.m96e(TAG, "getAudioDataInJPEG input parameter is null : filename = " + filename);
      return null;
    }
    int[] getParsedData = ParseQdioFile(filename);
    if (getParsedData == null) {
      return null;
    }
    if (getParsedData.length % 2 != 0) {
      Log.m96e(TAG, "Some Sound Data is broken");
      return null;
    }
    SemExtendedFormat.AudioJPEGData ajData = new SemExtendedFormat.AudioJPEGData();
    for (int i = 0; i < getParsedData.length / 2; i++) {
      if (getParsedData[i] <= 0 || getParsedData[i + 1] <= 0) {
        Log.m96e(TAG, "Some Sound Data stream is broken");
        return null;
      }
      ajData.startOffset.add(Integer.valueOf(getParsedData[i]));
      ajData.endOffset.add(Integer.valueOf(getParsedData[i + 1]));
      ajData.audio_count++;
      ajData.filename = filename;
    }
    return ajData;
  }

  public static SemExtendedFormat.QdioJPEGData checkAudioInJPEG(String filename) {
    if (!checkFileString(filename)) {
      Log.m96e(TAG, "checkAudioInJPEG input parameter is null : filename = " + filename);
      return null;
    }
    int[] getParsedData = ParseQdioFile(filename);
    if (getParsedData == null) {
      return null;
    }
    if (getParsedData.length % 2 != 0) {
      Log.m96e(TAG, "Some Sound Data is broken");
      return null;
    }
    SemExtendedFormat.QdioJPEGData qjData = new SemExtendedFormat.QdioJPEGData();
    for (int i = 0; i < getParsedData.length / 2; i++) {
      if (getParsedData[i] <= 0 || getParsedData[i + 1] <= 0) {
        Log.m96e(TAG, "Some Sound Data stream is broken");
        return null;
      }
      qjData.startOffset.add(Integer.valueOf(getParsedData[i]));
      qjData.endOffset.add(Integer.valueOf(getParsedData[i + 1]));
      qjData.audio_count++;
      qjData.filename = filename;
    }
    return qjData;
  }

  public static byte[] getAudioStreamBuffer(
      SemExtendedFormat.AudioJPEGData audioJpegData, int index) throws IOException {
    int startOffset;
    int endOffset;
    byte[] ret = (byte[]) null;
    if (audioJpegData == null) {
      Log.m96e(TAG, "qdioJpegData is null");
      return null;
    }
    if (audioJpegData.audio_count <= index) {
      Log.m96e(
          TAG,
          "invalid index. file : "
              + audioJpegData.getFileName()
              + " has "
              + audioJpegData.audio_count
              + " audio streams but index = "
              + index);
      return null;
    }
    FileInputStream fis = new FileInputStream(audioJpegData.getFileName());
    try {
      startOffset = audioJpegData.getStartOffset(index);
      endOffset = audioJpegData.getLength(index) + startOffset;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      fis.close();
    }
    if (fis.available() < endOffset) {
      Log.m96e(
          TAG,
          "fis.available is smaller then audio stream end : fileLen = "
              + fis.available()
              + ", audio strema end on "
              + endOffset);
      return null;
    }
    Log.m98i(TAG, "fis.avaliable = " + fis.available());
    Log.m98i(TAG, "s = " + startOffset + ", " + endOffset);
    ret = new byte[endOffset - startOffset];
    if (startOffset < 0) {
      return null;
    }
    long skipCheck = fis.skip(startOffset);
    if (skipCheck == 0) {
      return null;
    }
    int length = fis.read(ret);
    if (length == 0) {
      return null;
    }
    return ret;
  }

  public static byte[] getAudioStreamBuffer(SemExtendedFormat.QdioJPEGData qdioJpegData, int index)
      throws IOException {
    int startOffset;
    int endOffset;
    byte[] ret = (byte[]) null;
    if (qdioJpegData == null) {
      Log.m96e(TAG, "qdioJpegData is null");
      return null;
    }
    if (qdioJpegData.audio_count <= index) {
      Log.m96e(
          TAG,
          "invalid index. file : "
              + qdioJpegData.getFileName()
              + " has "
              + qdioJpegData.audio_count
              + " audio streams but index = "
              + index);
      return null;
    }
    FileInputStream fis = new FileInputStream(qdioJpegData.getFileName());
    try {
      startOffset = qdioJpegData.getStartOffset(index);
      endOffset = qdioJpegData.getLength(index) + startOffset;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      fis.close();
    }
    if (fis.available() < endOffset) {
      Log.m96e(
          TAG,
          "fis.available is smaller then audio stream end : fileLen = "
              + fis.available()
              + ", audio strema end on "
              + endOffset);
      return null;
    }
    Log.m98i(TAG, "fis.avaliable = " + fis.available());
    Log.m98i(TAG, "s = " + startOffset + ", " + endOffset);
    ret = new byte[endOffset - startOffset];
    if (startOffset < 0) {
      return null;
    }
    long skipCheck = fis.skip(startOffset);
    if (skipCheck == 0) {
      return null;
    }
    int length = fis.read(ret);
    if (length == 0) {
      return null;
    }
    return ret;
  }

  public static int isJPEG(String filename) {
    if (checkFileString(filename)) {
      return isQdioFile(filename) != 0 ? 1 : -1;
    }
    Log.m96e(TAG, "isJPEG input parameter is null : filename = " + filename);
    return -1;
  }

  public static int copyAdioInJPEGtoPNG(String srcFilename, String dstFilename) {
    if (!checkFileString(srcFilename) || !checkFileString(dstFilename)) {
      return 0;
    }
    return copyAdioData(srcFilename, dstFilename);
  }

  public static String getVersion() {
    int native_version = getNativeVersion();
    return "1.02_" + native_version;
  }

  public static int isJPEGfd(ParcelFileDescriptor Pfd) {
    return isQdioFd(Pfd.getFd()) != 0 ? 1 : -1;
  }

  public static boolean checkFileDescriptor(ParcelFileDescriptor Pfd) {
    if (Pfd.getFd() < 0) {
      return false;
    }
    return true;
  }

  public static SemExtendedFormat.QdioJPEGData checkAudioInJPEGfd(ParcelFileDescriptor Pfd) {
    if (!checkFileDescriptor(Pfd)) {
      Log.m96e(
          TAG,
          "checkAudioInJPEGfd input parameter is null : file no = "
              + Integer.toString(Pfd.getFd()));
      return null;
    }
    int[] getParsedData = ParseQdioFd(Pfd.getFd());
    if (getParsedData == null) {
      return null;
    }
    if (getParsedData.length % 2 != 0) {
      Log.m96e(TAG, "Some Sound Data is broken");
      return null;
    }
    SemExtendedFormat.QdioJPEGData qjData = new SemExtendedFormat.QdioJPEGData();
    for (int i = 0; i < getParsedData.length / 2; i++) {
      if (getParsedData[i] <= 0 || getParsedData[i + 1] <= 0) {
        Log.m96e(TAG, "Some Sound Data stream is broken");
        return null;
      }
      qjData.startOffset.add(Integer.valueOf(getParsedData[i]));
      qjData.endOffset.add(Integer.valueOf(getParsedData[i + 1]));
      qjData.audio_count++;
    }
    return qjData;
  }

  public static long[] getAudioDataPositionArrayFd(ParcelFileDescriptor Pfd) {
    if (!checkFileDescriptor(Pfd)) {
      Log.m96e(
          TAG,
          "getAudioPositionArray input parameter is error : file no = "
              + Integer.toString(Pfd.getFd()));
      return null;
    }
    int[] getParsedData = ParseQdioFd(Pfd.getFd());
    if (getParsedData == null) {
      return null;
    }
    if (getParsedData.length % 2 != 0) {
      Log.m96e(TAG, "Some Sound Data is broken");
      return null;
    }
    long[] ret = new long[2];
    ret[0] = getParsedData[0];
    ret[1] = getParsedData[1] - ret[0];
    return ret;
  }

  public static int deleteQdioFromFileDescriptor(ParcelFileDescriptor Pfd) {
    if (!checkFileDescriptor(Pfd)) {
      Log.m96e(
          TAG,
          "deleteQdioFromFileDescriptor input parameter is error : file no = "
              + Integer.toString(Pfd.getFd()));
      return 0;
    }
    return DeleteQdioFromFd(Pfd.getFd());
  }
}
