package com.samsung.android.media.convert.util;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.media.convert.core.Convert;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class CodecsHelper {
  public static MediaExtractor createExtractor(String inputFilePath) throws IOException {
    MediaExtractor extractor = new MediaExtractor();
    extractor.semSetRunningMode(1);
    extractor.setDataSource(inputFilePath);
    return extractor;
  }

  public static MediaExtractor createExtractor(FileDescriptor descriptor, long offset, long length)
      throws IOException {
    MediaExtractor extractor = new MediaExtractor();
    extractor.semSetRunningMode(1);
    extractor.setDataSource(descriptor, offset, length);
    return extractor;
  }

  public static MediaExtractor createExtractor(Context context, Uri uri) throws IOException {
    MediaExtractor extractor = new MediaExtractor();
    extractor.semSetRunningMode(1);
    extractor.setDataSource(context, uri, (Map<String, String>) null);
    return extractor;
  }

  public static int getAndSelectVideoTrackIndex(MediaExtractor extractor) {
    for (int index = 0; index < extractor.getTrackCount(); index++) {
      if (isVideoFormat(extractor.getTrackFormat(index))) {
        extractor.selectTrack(index);
        return index;
      }
    }
    return -1;
  }

  public static int getAndSelectAudioTrackIndex(MediaExtractor extractor) {
    for (int index = 0; index < extractor.getTrackCount(); index++) {
      if (isAudioFormat(extractor.getTrackFormat(index))) {
        extractor.selectTrack(index);
        return index;
      }
    }
    return -1;
  }

  public static MediaMetadataRetriever createMediaMetadataRetriever(String inputFilePath)
      throws IOException {
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    retriever.setDataSource(inputFilePath);
    return retriever;
  }

  public static MediaMetadataRetriever createMediaMetadataRetriever(Context context, Uri uri)
      throws IOException {
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    retriever.setDataSource(context, uri);
    return retriever;
  }

  private static boolean isVideoFormat(MediaFormat format) {
    return getMimeTypeFor(format).startsWith(BnRConstants.VIDEO_DIR_PATH);
  }

  private static boolean isAudioFormat(MediaFormat format) {
    return getMimeTypeFor(format).startsWith("audio/");
  }

  private static String getMimeTypeFor(MediaFormat format) {
    return format.getString(MediaFormat.KEY_MIME);
  }

  public static boolean isSupportedFormat(MediaMetadataRetriever retriever) {
    String filetype = retriever.extractMetadata(12);
    return Convert.ContentType.sSupportedVideoTypes.contains(filetype);
  }

  public static MediaCodecInfo getEncoderCodec(String mimeType) {
    MediaCodecInfo codec = isSecCodecAvailable(mimeType, true);
    if (codec == null) {
      int numCodecs = MediaCodecList.getCodecCount();
      for (int i = 0; i < numCodecs; i++) {
        MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
        if (codecInfo.isEncoder()) {
          String[] types = codecInfo.getSupportedTypes();
          int j = 0;
          while (true) {
            if (j >= types.length) {
              break;
            }
            if (!types[j].equalsIgnoreCase(mimeType)) {
              j++;
            } else {
              codec = codecInfo;
              break;
            }
          }
        }
      }
    }
    return codec;
  }

  public static MediaCodecInfo getDecoderCodec(String mimeType) {
    MediaCodecInfo codec = isSecCodecAvailable(mimeType, false);
    if (codec == null) {
      int numCodecs = MediaCodecList.getCodecCount();
      for (int i = 0; i < numCodecs; i++) {
        MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
        if (!codecInfo.isEncoder()) {
          String[] types = codecInfo.getSupportedTypes();
          int j = 0;
          while (true) {
            if (j >= types.length) {
              break;
            }
            if (!types[j].equalsIgnoreCase(mimeType)) {
              j++;
            } else {
              codec = codecInfo;
              break;
            }
          }
        }
      }
    }
    return codec;
  }

  public static boolean isSupportedFormat(String filePath) {
    boolean support = false;
    if (filePath == null) {
      return false;
    }
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(filePath);
          retriever.setDataSource(fis.getFD());
          String format = retriever.extractMetadata(12);
          if (format.contains("video/mp4")) {
            support = true;
          }
          fis.close();
          retriever.release();
        } catch (Exception e) {
        }
      } catch (Exception e2) {
        e2.printStackTrace();
        if (fis != null) {
          fis.close();
        }
        retriever.release();
      }
      retriever = null;
      return support;
    } catch (Throwable th) {
      if (fis != null) {
        try {
          fis.close();
        } catch (Exception e3) {
          throw th;
        }
      }
      retriever.release();
      throw th;
    }
  }

  public static boolean isSupportedFormat(Context context, Uri uri) {
    boolean support = false;
    if (context == null || uri == null) {
      return false;
    }
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    try {
      try {
        try {
          retriever.setDataSource(context, uri);
          String format = retriever.extractMetadata(12);
          if (format.contains("video/mp4")) {
            support = true;
          }
          retriever.release();
        } catch (Exception e) {
          e.printStackTrace();
          retriever.release();
        }
        retriever = null;
      } catch (Throwable th) {
        try {
          retriever.release();
        } catch (Exception e2) {
        }
        throw th;
      }
    } catch (Exception e3) {
    }
    return support;
  }

  private static MediaCodecInfo isSecCodecAvailable(String mimeType, boolean isEncoder) {
    MediaCodecInfo codecInfo = null;
    if ("audio/mp4a-latm".equals(mimeType)) {
      for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
        MediaCodecInfo codec = MediaCodecList.getCodecInfoAt(i);
        String codecName = codec.getName();
        if (isEncoder && codec.isEncoder()) {
          if (codecName.equals("OMX.SEC.naac.enc") || codecName.equals("OMX.SEC.aac.enc")) {
            for (String str : codec.getSupportedTypes()) {
              if (str.equalsIgnoreCase(mimeType)) {
                codecInfo = codec;
              }
            }
          }
        } else if (!isEncoder && !codec.isEncoder() && codecName.equals("OMX.SEC.aac.dec")) {
          String[] types = codec.getSupportedTypes();
          for (String str2 : types) {
            if (str2.equalsIgnoreCase(mimeType)) {
              codecInfo = codec;
            }
          }
        }
      }
    }
    return codecInfo;
  }

  public static MediaCodec createAudioEncoder(MediaCodecInfo codecInfo, MediaFormat format)
      throws IOException {
    MediaCodec encoder = MediaCodec.createByCodecName(codecInfo.getName());
    encoder.configure(format, (Surface) null, (MediaCrypto) null, 1);
    encoder.start();
    return encoder;
  }

  public static MediaCodec createAudioDecoder(MediaFormat inputFormat) throws IOException {
    MediaCodec decoder = MediaCodec.createDecoderByType(getMimeTypeFor(inputFormat));
    decoder.configure(inputFormat, (Surface) null, (MediaCrypto) null, 0);
    decoder.start();
    return decoder;
  }

  public static MediaCodec createAudioDecoder(MediaCodecInfo codecInfo, MediaFormat inputFormat)
      throws IOException {
    MediaCodec decoder = MediaCodec.createByCodecName(codecInfo.getName());
    decoder.configure(inputFormat, (Surface) null, (MediaCrypto) null, 0);
    decoder.start();
    return decoder;
  }

  public static MediaCodec createVideoDecoder(MediaFormat inputFormat, Surface surface)
      throws IOException {
    MediaCodec decoder = MediaCodec.createDecoderByType(getMimeTypeFor(inputFormat));
    Log.m94d(Constants.TAG, "createVideoDecoder");
    try {
      decoder.configure(inputFormat, surface, (MediaCrypto) null, 0);
      decoder.start();
      Log.m94d(Constants.TAG, "createVideoDecoder - start");
      return decoder;
    } catch (IllegalStateException e) {
      e.printStackTrace();
      if (decoder != null) {
        decoder.release();
      }
      throw new IOException("createVideoDecode configure error");
    }
  }

  public static void scheduleAfter(int milliSeconds, Runnable schedulerCallback)
      throws InterruptedException, ExecutionException {
    ScheduledThreadPoolExecutor sch =
        (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
    sch.schedule(schedulerCallback, milliSeconds, TimeUnit.SECONDS);
  }

  public static int suggestBitRate(int width, int height, int fps) {
    int framesize = width * height;
    if (framesize >= 8294400) {
      if (fps < 33) {
        return 48000;
      }
      return 72000;
    }
    if (framesize >= 2401920) {
      if (fps < 33) {
        return 20000;
      }
      return 40000;
    }
    if (framesize >= 2073600) {
      if (fps < 33) {
        return 17000;
      }
      return 28000;
    }
    if (framesize >= 921600) {
      if (fps < 33) {
        return 12000;
      }
      return 24000;
    }
    if (framesize >= 345600) {
      if (fps < 33) {
        return Convert.BitRate.VIDEO_D1_BITRATE;
      }
      return 6898;
    }
    if (fps < 33) {
      return Convert.BitRate.VIDEO_VGA_BITRATE;
    }
    return AudioFormat.CHANNEL_OUT_QUAD_SIDE;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x0019, code lost:

     if (r1 == null) goto L13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:

     android.util.Log.m94d(com.samsung.android.media.convert.util.Constants.TAG, "getRemainedResourceCapacity = " + r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:8:0x0035, code lost:

     return r0;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static int getRemainedResourceCapacity() {
    int capacity = -1;
    SemMediaResourceHelper ResourceHelper = SemMediaResourceHelper.createInstance(2, false);
    try {
      try {
        capacity = ResourceHelper.getRemainedVideoCapacity();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      }
    } finally {
      if (ResourceHelper != null) {
        ResourceHelper.release();
      }
    }
  }

  public static boolean isSupportOMX() {
    MediaCodecInfo codecInfo = getMediaCodec("video/avc", false, false);
    Log.m94d(Constants.TAG, "isSupportOMX getMediaCodec : " + codecInfo.getName());
    if (!codecInfo.getName().contains("omx.") && !codecInfo.getName().contains("OMX.")) {
      return false;
    }
    return true;
  }

  public static MediaCodecInfo getMediaCodec(String mimeType, boolean isEncoder, boolean preferSw) {
    MediaCodecList mcl = new MediaCodecList(0);
    MediaCodecInfo[] infos = mcl.getCodecInfos();
    for (MediaCodecInfo info : infos) {
      if (info.isEncoder() == isEncoder && info.isSoftwareOnly() == preferSw) {
        String[] types = info.getSupportedTypes();
        for (String str : types) {
          if (str.equalsIgnoreCase(mimeType)) {
            return info;
          }
        }
      }
    }
    return null;
  }
}
