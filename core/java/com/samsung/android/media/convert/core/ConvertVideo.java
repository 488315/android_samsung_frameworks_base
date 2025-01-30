package com.samsung.android.media.convert.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.GLES20;
import android.p009os.SemSystemProperties;
import android.provider.MediaStore;
import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.media.convert.surfaces.InputSurface;
import com.samsung.android.media.convert.surfaces.OutputSurface;
import com.samsung.android.media.convert.util.CodecsHelper;
import com.samsung.android.media.convert.util.Constants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/* loaded from: classes5.dex */
public class ConvertVideo extends Convert {
  protected static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SP_M = 261;
  private static final int HEADER_SIZE = 500;
  private static final int IMAGE_WAIT_TIMEOUT_MS = 1000;
  private static final String KEY_ERROR_TYPE = "error-type";
  private static final String KEY_MUXER_AUTHOR = "param-meta-author";
  private static final String KEY_MUXER_RECORDINGMODE = "param-meta-recording-mode";
  private static final String KEY_MUXER_TRANSCODING = "param-meta-transcoding";
  protected static final int OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m = 2141391876;
  private static final int REWRITE_AUDIO_BUFFER_SIZE = 131072;
  private static final long TIMEOUT_USEC = 10000;
  private static final int VIDEO_FPS_BUF_COUNT = 5;
  private MediaFormat inputAudioFormat;
  private MediaExtractor mAudioExtractor;
  private Context mContext;
  private boolean mCopyAudio;
  private String mInputFilePath;
  private InputSurface mInputSurface;
  private Uri mInputUri;
  private OutputSurface mOutputSurface;
  private long mTrimAudioEndUs;
  private long mTrimAudioStartUs;
  private long mTrimVideoEndUs;
  private long mTrimVideoStartUs;
  private MediaExtractor mVideoExtractor;
  private static boolean mUseUri = false;
  private static byte[] creationTime = new byte[4];
  private int mInputOrientationDegrees = 0;
  private Object mStopLock = new Object();
  private int mVideoFrameCount = 0;
  private boolean formatupdated = false;
  private boolean mUpdateCreationTime = false;
  private int mInputBitdepth = 8;
  private int mAuthor = -1;
  private int mHDRType = 0;
  private int mRecordingMode = 1;
  private boolean skipBufferInfo = false;
  private boolean isCcodec = false;

  public boolean initialize(String outputFilePath, String inputFilePath) {
    if (outputFilePath == null) {
      Log.m96e(Constants.TAG, "output file path cannot be null");
      return false;
    }
    if (inputFilePath == null) {
      Log.m96e(Constants.TAG, "input file path cannot be null");
      return false;
    }
    mUseUri = false;
    if (!CheckVideoFormat(inputFilePath)) {
      Log.m96e(Constants.TAG, "Not a valid video format.");
      return false;
    }
    if (!CheckVideoCodec(inputFilePath, false)) {
      Log.m96e(Constants.TAG, "Not a valid video codec.");
      return false;
    }
    this.mOutputFilePath = outputFilePath;
    this.mInputFilePath = inputFilePath;
    return true;
  }

  public boolean initialize(String outputFilePath, Context context, Uri inputUri) {
    if (outputFilePath == null) {
      Log.m96e(Constants.TAG, "output file path cannot be null");
      return false;
    }
    if (inputUri == null) {
      Log.m96e(Constants.TAG, "input uri cannot be null");
      return false;
    }
    if (context == null) {
      Log.m96e(Constants.TAG, "context cannot be null");
      return false;
    }
    mUseUri = true;
    if (!CheckVideoFormat(context, inputUri)) {
      Log.m96e(Constants.TAG, "Not a valid video format.");
      return false;
    }
    if (!CheckVideoCodec(context, inputUri, false)) {
      Log.m96e(Constants.TAG, "Not a valid video codec.");
      return false;
    }
    this.mOutputFilePath = outputFilePath;
    this.mInputUri = inputUri;
    this.mContext = context;
    return true;
  }

  @Override // com.samsung.android.media.convert.core.Convert
  protected boolean prepare() {
    if (this.mConverting) {
      Log.m94d(Constants.TAG, "already started converting");
      return false;
    }
    if (mUseUri) {
      if (this.mContext == null || this.mInputUri == null) {
        Log.m94d(Constants.TAG, "mInputUri or mContext  is NULL");
        return false;
      }
      return true;
    }
    if (this.mInputFilePath == null) {
      Log.m94d(Constants.TAG, "mInputFilePath is NULL");
      return false;
    }
    return true;
  }

  protected boolean prepareVideoCodec() {
    int width;
    int height;
    int x;
    int y;
    MediaMetadataRetriever metaData = new MediaMetadataRetriever();
    try {
      try {
        if (mUseUri) {
          metaData.setDataSource(this.mContext, this.mInputUri);
        } else {
          metaData.setDataSource(this.mInputFilePath);
        }
        String inputOrientation = metaData.extractMetadata(24);
        String auth = metaData.extractMetadata(1015);
        if (inputOrientation != null) {
          int degree = 0;
          try {
            degree = Integer.parseInt(inputOrientation);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
          switch (degree) {
            case 0:
              this.mInputOrientationDegrees = 0;
              break;
            case 90:
              this.mInputOrientationDegrees = 90;
              break;
            case 180:
              this.mInputOrientationDegrees = 180;
              break;
            case 270:
              this.mInputOrientationDegrees = 270;
              break;
          }
        } else {
          this.mInputOrientationDegrees = 0;
        }
        if (auth != null) {
          this.mAuthor = Integer.parseInt(auth);
        }
      } catch (IllegalArgumentException e2) {
        e2.printStackTrace();
      }
      try {
        if (mUseUri) {
          this.mVideoExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
        } else {
          this.mVideoExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
        }
        int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        MediaFormat inputFormat = this.mVideoExtractor.getTrackFormat(videoTrack);
        inputFormat.setInteger("force-hdr2sdr-enable", 1);
        inputFormat.setInteger("HDR-OFF", 1);
        this.isCcodec = false;
        if (!CodecsHelper.isSupportOMX()) {
          Log.m94d(Constants.TAG, "use c2 codec - filter");
          this.isCcodec = true;
          String chipName = SemSystemProperties.get("ro.hardware.chipname").toLowerCase();
          String boardName = SemSystemProperties.get("ro.product.board").toLowerCase();
          String socModelName = SemSystemProperties.get("ro.soc.model").toLowerCase();
          String chipset = chipName;
          if (chipset == null || chipset.equals("")) {
            chipset = boardName;
          }
          if (chipset == null || chipset.equals("")) {
            chipset = socModelName;
          }
          if (chipset != null) {
            if (chipset.contains("exynos") || chipset.contains("s5e")) {
              inputFormat.setInteger("vendor.sec-dec-output.image-convert.value", 1);
              inputFormat.setInteger("vendor.sec-ext-imageformat-filter-enableInplace.value", 0);
              inputFormat.setInteger("vendor.sec-dec-output.image-convert-pixel-format.value", 261);
            } else {
              inputFormat.setInteger("vendor.qti-ext-dec-forceNonUBWC.value", 1);
              inputFormat.setInteger("vendor.qti-ext-imageformat-filter-enabled.value", 1);
              inputFormat.setInteger("vendor.qti-ext-imageformat-filter-enableInplace.value", 0);
              inputFormat.setInteger(
                  "vendor.qti-ext-imageformat-filter-clientcolorformat.value",
                  OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m);
            }
          }
        }
        Log.m94d(Constants.TAG, "input video format: " + inputFormat);
        if (this.mTrimVideoEndUs == 0) {
          this.mTrimVideoEndUs = inputFormat.getLong(MediaFormat.KEY_DURATION);
          Log.m94d(
              Constants.TAG,
              "mTrimVideoEndUs was 0 but updated  mTrimVideoEndUs : " + this.mTrimVideoEndUs);
        }
        this.mSourceFrameRate = 0;
        try {
          this.mSourceFrameRate = inputFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e3) {
        }
        if (this.mSourceFrameRate == 0 || this.mSourceFrameRate > 250) {
          this.mSourceFrameRate =
              getVideoFramerate(this.mInputFilePath, this.mContext, this.mInputUri);
        }
        this.mOutputVideoFrameRate = this.mSourceFrameRate;
        Log.m94d(
            Constants.TAG,
            "mSourceFrameRate :"
                + this.mSourceFrameRate
                + ", mOutputVideoFrameRate :"
                + this.mOutputVideoFrameRate);
        this.mOutputVideoBitRate =
            CodecsHelper.suggestBitRate(
                    this.mOutputWidth, this.mOutputHeight, this.mOutputVideoFrameRate)
                * 1000;
        MediaFormat outputVideoFormat =
            MediaFormat.createVideoFormat(
                this.mOutputVideoMimeType, this.mOutputWidth, this.mOutputHeight);
        outputVideoFormat.setInteger(
            MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        outputVideoFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mOutputVideoBitRate);
        outputVideoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, this.mOutputVideoFrameRate);
        outputVideoFormat.setInteger(
            MediaFormat.KEY_I_FRAME_INTERVAL, this.mOutputVideoIFrameInterval);
        outputVideoFormat.setInteger("priority", 1);
        Log.m94d(Constants.TAG, "output video format " + outputVideoFormat);
        try {
          this.mOutputVideoEncoder = MediaCodec.createEncoderByType(this.mOutputVideoMimeType);
          this.mOutputVideoEncoder.configure(
              outputVideoFormat, (Surface) null, (MediaCrypto) null, 1);
          this.mInputSurface = new InputSurface(this.mOutputVideoEncoder.createInputSurface());
          this.mOutputVideoEncoder.start();
          this.mInputSurface.makeCurrent();
          try {
            int origin_width = inputFormat.getInteger("width");
            int origin_height = inputFormat.getInteger("height");
            float originAspectRatio = origin_width / origin_height;
            float targetAspectRatio = this.mOutputWidth / this.mOutputHeight;
            if (originAspectRatio > targetAspectRatio) {
              int width2 = this.mOutputWidth;
              int height2 = (this.mOutputWidth * origin_height) / origin_width;
              width = width2;
              height = height2;
              x = 0;
              y = (this.mOutputHeight - height2) / 2;
            } else {
              int width3 = this.mOutputHeight;
              int width4 = (this.mOutputHeight * origin_width) / origin_height;
              int x2 = (this.mOutputWidth - width4) / 2;
              width = width4;
              height = width3;
              x = x2;
              y = 0;
            }
            this.mOutputSurface =
                new OutputSurface(
                    this.mInputOrientationDegrees,
                    x,
                    y,
                    width,
                    height,
                    origin_width,
                    origin_height,
                    false);
          } catch (Exception e4) {
            Log.m94d(Constants.TAG, "Can't get input video resolution");
            this.mOutputSurface = new OutputSurface(this.mInputOrientationDegrees);
          }
          try {
            this.mInputVideoDecoder =
                CodecsHelper.createVideoDecoder(inputFormat, this.mOutputSurface.getSurface());
          } catch (Exception e5) {
          }
          if (this.mInputVideoDecoder == null) {
            Log.m94d(Constants.TAG, "can't set VideoDecoder");
            return false;
          }
          return true;
        } catch (Exception e6) {
          Log.m94d(Constants.TAG, "createEncoder error");
          return false;
        }
      } catch (IOException e7) {
        Log.m94d(Constants.TAG, "createExtractor error");
        return false;
      }
    } finally {
      metaData.release();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:41:0x02f9 A[Catch: Exception -> 0x0350, TryCatch #0 {Exception -> 0x0350, blocks: (B:39:0x02ed, B:41:0x02f9, B:42:0x02fc, B:44:0x0332, B:45:0x0336, B:47:0x033c, B:49:0x0347), top: B:38:0x02ed }] */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0332 A[Catch: Exception -> 0x0350, TryCatch #0 {Exception -> 0x0350, blocks: (B:39:0x02ed, B:41:0x02f9, B:42:0x02fc, B:44:0x0332, B:45:0x0336, B:47:0x033c, B:49:0x0347), top: B:38:0x02ed }] */
  /* JADX WARN: Removed duplicated region for block: B:47:0x033c A[Catch: Exception -> 0x0350, TryCatch #0 {Exception -> 0x0350, blocks: (B:39:0x02ed, B:41:0x02f9, B:42:0x02fc, B:44:0x0332, B:45:0x0336, B:47:0x033c, B:49:0x0347), top: B:38:0x02ed }] */
  /* JADX WARN: Removed duplicated region for block: B:49:0x0347 A[Catch: Exception -> 0x0350, TRY_LEAVE, TryCatch #0 {Exception -> 0x0350, blocks: (B:39:0x02ed, B:41:0x02f9, B:42:0x02fc, B:44:0x0332, B:45:0x0336, B:47:0x033c, B:49:0x0347), top: B:38:0x02ed }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  protected boolean prepareAudioCodec() {
    ConvertVideo convertVideo;
    ByteBuffer[] audioDecoderOutputBuffers;
    int audioInputTrack;
    MediaCodecInfo audioinputCodecInfo;
    ByteBuffer[] audioDecoderOutputBuffers2;
    MediaCodec.BufferInfo audioDecoderOutputBufferInfo;
    int pendingAudioDecoderOutputBufferIndex;
    int maxInputSize;
    int error;
    ConvertVideo convertVideo2 = this;
    try {
      if (mUseUri) {
        convertVideo2.mAudioExtractor =
            CodecsHelper.createExtractor(convertVideo2.mContext, convertVideo2.mInputUri);
      } else {
        convertVideo2.mAudioExtractor = CodecsHelper.createExtractor(convertVideo2.mInputFilePath);
      }
      int audioInputTrack2 =
          CodecsHelper.getAndSelectAudioTrackIndex(convertVideo2.mAudioExtractor);
      if (audioInputTrack2 == -1) {
        convertVideo2.mCopyAudio = false;
        return true;
      }
      MediaFormat inputAudioFormat = convertVideo2.mAudioExtractor.getTrackFormat(audioInputTrack2);
      if ("audio/unknown".equals(inputAudioFormat.getString(MediaFormat.KEY_MIME))) {
        Log.m94d(Constants.TAG, "Audio mime type is unknown. Ignore audio track.");
        convertVideo2.mCopyAudio = false;
        return true;
      }
      if (inputAudioFormat.containsKey("error-type")
          && (error = inputAudioFormat.getInteger("error-type")) != 0) {
        Log.m94d(Constants.TAG, "Audio codec error appear : " + error);
        convertVideo2.mCopyAudio = false;
        return true;
      }
      convertVideo2.mCopyAudio = true;
      if (convertVideo2.mTrimAudioEndUs == 0) {
        convertVideo2.mTrimAudioEndUs = inputAudioFormat.getLong(MediaFormat.KEY_DURATION);
        Log.m94d(
            Constants.TAG,
            "mTrimAudioEndUs was 0 but updated mTrimAudioEndUs :" + convertVideo2.mTrimAudioEndUs);
      }
      Log.m94d(Constants.TAG, "Audio input format " + inputAudioFormat);
      convertVideo2.mOutputAudioSampleRateHZ =
          inputAudioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
      convertVideo2.mOutputAudioChannelCount =
          inputAudioFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
      String mimeType = inputAudioFormat.getString(MediaFormat.KEY_MIME);
      if ("audio/mp4a-latm".equals(mimeType)) {
        try {
          MediaCodecInfo audioinputCodecInfo2 = CodecsHelper.getDecoderCodec(mimeType);
          convertVideo2.mInputAudioDecoder =
              CodecsHelper.createAudioDecoder(audioinputCodecInfo2, inputAudioFormat);
          if (convertVideo2.mCopyAudio) {
            try {
              audioDecoderOutputBuffers = convertVideo2.mInputAudioDecoder.getOutputBuffers();
            } catch (IOException e) {
              e = e;
              convertVideo = convertVideo2;
              e.printStackTrace();
              maxInputSize = 0;
              maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
              MediaFormat outputAudioFormat =
                  MediaFormat.createAudioFormat(
                      convertVideo.mOutputAudioMimeType,
                      convertVideo.mOutputAudioSampleRateHZ,
                      convertVideo.mOutputAudioChannelCount);
              if (maxInputSize != 0) {}
              outputAudioFormat.setInteger(
                  MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
              outputAudioFormat.setInteger(
                  MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
              Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat);
              MediaCodecInfo audioCodecInfo =
                  CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
              convertVideo.mOutputAudioEncoder =
                  CodecsHelper.createAudioEncoder(audioCodecInfo, outputAudioFormat);
              if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
              if ("audio/mp4a-latm".equals(mimeType)) {}
            } catch (InterruptedException e2) {
              e = e2;
              convertVideo = convertVideo2;
              e.printStackTrace();
              maxInputSize = 0;
              maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
              MediaFormat outputAudioFormat2 =
                  MediaFormat.createAudioFormat(
                      convertVideo.mOutputAudioMimeType,
                      convertVideo.mOutputAudioSampleRateHZ,
                      convertVideo.mOutputAudioChannelCount);
              if (maxInputSize != 0) {}
              outputAudioFormat2.setInteger(
                  MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
              outputAudioFormat2.setInteger(
                  MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
              Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat2);
              MediaCodecInfo audioCodecInfo2 =
                  CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
              convertVideo.mOutputAudioEncoder =
                  CodecsHelper.createAudioEncoder(audioCodecInfo2, outputAudioFormat2);
              if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
              if ("audio/mp4a-latm".equals(mimeType)) {}
            } catch (ExecutionException e3) {
              e = e3;
              convertVideo = convertVideo2;
              e.printStackTrace();
              maxInputSize = 0;
              maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
              MediaFormat outputAudioFormat22 =
                  MediaFormat.createAudioFormat(
                      convertVideo.mOutputAudioMimeType,
                      convertVideo.mOutputAudioSampleRateHZ,
                      convertVideo.mOutputAudioChannelCount);
              if (maxInputSize != 0) {}
              outputAudioFormat22.setInteger(
                  MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
              outputAudioFormat22.setInteger(
                  MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
              Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat22);
              MediaCodecInfo audioCodecInfo22 =
                  CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
              convertVideo.mOutputAudioEncoder =
                  CodecsHelper.createAudioEncoder(audioCodecInfo22, outputAudioFormat22);
              if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
              if ("audio/mp4a-latm".equals(mimeType)) {}
            }
          } else {
            audioDecoderOutputBuffers = null;
          }
          ByteBuffer[] audioDecoderInputBuffers =
              convertVideo2.mCopyAudio ? convertVideo2.mInputAudioDecoder.getInputBuffers() : null;
          MediaCodec.BufferInfo audioDecoderOutputBufferInfo2 = new MediaCodec.BufferInfo();
          Runnable schedulerCallback =
              new Runnable() { // from class: com.samsung.android.media.convert.core.ConvertVideo.1
                @Override // java.lang.Runnable
                public void run() {
                  ConvertVideo.this.formatupdated = true;
                }
              };
          int pendingAudioDecoderOutputBufferIndex2 = -1;
          while (true) {
            boolean z = convertVideo2.formatupdated;
            if (z) {
              break;
            }
            if (z) {
              convertVideo = this;
              audioInputTrack = audioInputTrack2;
              audioinputCodecInfo = audioinputCodecInfo2;
              audioDecoderOutputBuffers2 = audioDecoderOutputBuffers;
            } else {
              convertVideo = this;
              try {
                MediaCodecInfo audioinputCodecInfo3 = audioinputCodecInfo2;
                audioDecoderOutputBuffers2 = audioDecoderOutputBuffers;
                int audioDecoderInputBufferIndex =
                    convertVideo.mInputAudioDecoder.dequeueInputBuffer(10000L);
                if (audioDecoderInputBufferIndex == -1) {
                  try {
                    Log.m94d(
                        Constants.TAG,
                        "audio decoder input try again later while preparing audio codec");
                    audioinputCodecInfo = audioinputCodecInfo3;
                    audioInputTrack = audioInputTrack2;
                  } catch (IOException e4) {
                    e = e4;
                    e.printStackTrace();
                    maxInputSize = 0;
                    maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    MediaFormat outputAudioFormat222 =
                        MediaFormat.createAudioFormat(
                            convertVideo.mOutputAudioMimeType,
                            convertVideo.mOutputAudioSampleRateHZ,
                            convertVideo.mOutputAudioChannelCount);
                    if (maxInputSize != 0) {}
                    outputAudioFormat222.setInteger(
                        MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                    outputAudioFormat222.setInteger(
                        MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                    Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat222);
                    MediaCodecInfo audioCodecInfo222 =
                        CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                    convertVideo.mOutputAudioEncoder =
                        CodecsHelper.createAudioEncoder(audioCodecInfo222, outputAudioFormat222);
                    if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                    if ("audio/mp4a-latm".equals(mimeType)) {}
                  } catch (InterruptedException e5) {
                    e = e5;
                    e.printStackTrace();
                    maxInputSize = 0;
                    maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    MediaFormat outputAudioFormat2222 =
                        MediaFormat.createAudioFormat(
                            convertVideo.mOutputAudioMimeType,
                            convertVideo.mOutputAudioSampleRateHZ,
                            convertVideo.mOutputAudioChannelCount);
                    if (maxInputSize != 0) {}
                    outputAudioFormat2222.setInteger(
                        MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                    outputAudioFormat2222.setInteger(
                        MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                    Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat2222);
                    MediaCodecInfo audioCodecInfo2222 =
                        CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                    convertVideo.mOutputAudioEncoder =
                        CodecsHelper.createAudioEncoder(audioCodecInfo2222, outputAudioFormat2222);
                    if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                    if ("audio/mp4a-latm".equals(mimeType)) {}
                  } catch (ExecutionException e6) {
                    e = e6;
                    e.printStackTrace();
                    maxInputSize = 0;
                    maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    MediaFormat outputAudioFormat22222 =
                        MediaFormat.createAudioFormat(
                            convertVideo.mOutputAudioMimeType,
                            convertVideo.mOutputAudioSampleRateHZ,
                            convertVideo.mOutputAudioChannelCount);
                    if (maxInputSize != 0) {}
                    outputAudioFormat22222.setInteger(
                        MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                    outputAudioFormat22222.setInteger(
                        MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                    Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat22222);
                    MediaCodecInfo audioCodecInfo22222 =
                        CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                    convertVideo.mOutputAudioEncoder =
                        CodecsHelper.createAudioEncoder(
                            audioCodecInfo22222, outputAudioFormat22222);
                    if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                    if ("audio/mp4a-latm".equals(mimeType)) {}
                  }
                } else {
                  ByteBuffer audioDecoderInputBuffer =
                      audioDecoderInputBuffers[audioDecoderInputBufferIndex];
                  audioinputCodecInfo = audioinputCodecInfo3;
                  int size =
                      convertVideo.mAudioExtractor.readSampleData(audioDecoderInputBuffer, 0);
                  long presentationTimeUs = convertVideo.mAudioExtractor.getSampleTime();
                  if (size > 0) {
                    audioInputTrack = audioInputTrack2;
                    convertVideo.mInputAudioDecoder.queueInputBuffer(
                        audioDecoderInputBufferIndex,
                        0,
                        size,
                        presentationTimeUs,
                        convertVideo.mAudioExtractor.getSampleFlags());
                  } else {
                    audioInputTrack = audioInputTrack2;
                    if (size == -1) {
                      convertVideo.mCopyAudio = false;
                      convertVideo.formatupdated = true;
                      Log.m94d(Constants.TAG, "Audio buffer is empty, size :" + size);
                    }
                  }
                }
              } catch (IOException e7) {
                e = e7;
                e.printStackTrace();
                maxInputSize = 0;
                maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                MediaFormat outputAudioFormat222222 =
                    MediaFormat.createAudioFormat(
                        convertVideo.mOutputAudioMimeType,
                        convertVideo.mOutputAudioSampleRateHZ,
                        convertVideo.mOutputAudioChannelCount);
                if (maxInputSize != 0) {}
                outputAudioFormat222222.setInteger(
                    MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                outputAudioFormat222222.setInteger(
                    MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat222222);
                MediaCodecInfo audioCodecInfo222222 =
                    CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                convertVideo.mOutputAudioEncoder =
                    CodecsHelper.createAudioEncoder(audioCodecInfo222222, outputAudioFormat222222);
                if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                if ("audio/mp4a-latm".equals(mimeType)) {}
              } catch (InterruptedException e8) {
                e = e8;
                e.printStackTrace();
                maxInputSize = 0;
                maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                MediaFormat outputAudioFormat2222222 =
                    MediaFormat.createAudioFormat(
                        convertVideo.mOutputAudioMimeType,
                        convertVideo.mOutputAudioSampleRateHZ,
                        convertVideo.mOutputAudioChannelCount);
                if (maxInputSize != 0) {}
                outputAudioFormat2222222.setInteger(
                    MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                outputAudioFormat2222222.setInteger(
                    MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat2222222);
                MediaCodecInfo audioCodecInfo2222222 =
                    CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                convertVideo.mOutputAudioEncoder =
                    CodecsHelper.createAudioEncoder(
                        audioCodecInfo2222222, outputAudioFormat2222222);
                if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                if ("audio/mp4a-latm".equals(mimeType)) {}
              } catch (ExecutionException e9) {
                e = e9;
                e.printStackTrace();
                maxInputSize = 0;
                maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                MediaFormat outputAudioFormat22222222 =
                    MediaFormat.createAudioFormat(
                        convertVideo.mOutputAudioMimeType,
                        convertVideo.mOutputAudioSampleRateHZ,
                        convertVideo.mOutputAudioChannelCount);
                if (maxInputSize != 0) {}
                outputAudioFormat22222222.setInteger(
                    MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                outputAudioFormat22222222.setInteger(
                    MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat22222222);
                MediaCodecInfo audioCodecInfo22222222 =
                    CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                convertVideo.mOutputAudioEncoder =
                    CodecsHelper.createAudioEncoder(
                        audioCodecInfo22222222, outputAudioFormat22222222);
                if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                if ("audio/mp4a-latm".equals(mimeType)) {}
              }
            }
            CodecsHelper.scheduleAfter(3, schedulerCallback);
            if (convertVideo.formatupdated) {
              audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo2;
              pendingAudioDecoderOutputBufferIndex = pendingAudioDecoderOutputBufferIndex2;
            } else {
              int pendingAudioDecoderOutputBufferIndex3 = pendingAudioDecoderOutputBufferIndex2;
              if (pendingAudioDecoderOutputBufferIndex3 == -1) {
                try {
                  pendingAudioDecoderOutputBufferIndex = pendingAudioDecoderOutputBufferIndex3;
                  audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo2;
                  int audioDecoderOutputBufferIndex =
                      convertVideo.mInputAudioDecoder.dequeueOutputBuffer(
                          audioDecoderOutputBufferInfo, 10000L);
                  if (audioDecoderOutputBufferIndex == -1) {
                    Log.m94d(
                        Constants.TAG,
                        "audio decoder output buffer try again later while preparing audio codec");
                  } else if (audioDecoderOutputBufferIndex == -3) {
                    Log.m94d(Constants.TAG, "audio decoder: output buffers changed ");
                    ByteBuffer[] audioDecoderOutputBuffers3 =
                        convertVideo.mInputAudioDecoder.getOutputBuffers();
                    convertVideo2 = convertVideo;
                    audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                    pendingAudioDecoderOutputBufferIndex2 = pendingAudioDecoderOutputBufferIndex;
                    audioinputCodecInfo2 = audioinputCodecInfo;
                    audioDecoderOutputBuffers = audioDecoderOutputBuffers3;
                    audioInputTrack2 = audioInputTrack;
                  } else if (audioDecoderOutputBufferIndex == -2) {
                    convertVideo.mOutputAudioSampleRateHZ =
                        convertVideo
                            .mInputAudioDecoder
                            .getOutputFormat()
                            .getInteger(MediaFormat.KEY_SAMPLE_RATE);
                    convertVideo.mOutputAudioChannelCount =
                        convertVideo
                            .mInputAudioDecoder
                            .getOutputFormat()
                            .getInteger(MediaFormat.KEY_CHANNEL_COUNT);
                    Log.m94d(
                        Constants.TAG,
                        "audio decoder: output format changed: SampleRate"
                            + convertVideo.mOutputAudioSampleRateHZ
                            + ",ChannelCount"
                            + convertVideo.mOutputAudioChannelCount);
                    convertVideo.formatupdated = true;
                  } else if (audioDecoderOutputBufferIndex < 0) {
                    Log.m94d(
                        Constants.TAG,
                        "Unexpected result from audio decoder dequeue output format.");
                  } else if ((audioDecoderOutputBufferInfo.flags & 2) != 0) {
                    Log.m94d(Constants.TAG, "audio decoder: codec config buffer");
                    convertVideo.mInputAudioDecoder.releaseOutputBuffer(
                        audioDecoderOutputBufferIndex, false);
                  } else {
                    pendingAudioDecoderOutputBufferIndex2 = audioDecoderOutputBufferIndex;
                    convertVideo2 = convertVideo;
                    audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                    audioDecoderOutputBuffers = audioDecoderOutputBuffers2;
                    audioinputCodecInfo2 = audioinputCodecInfo;
                    audioInputTrack2 = audioInputTrack;
                  }
                } catch (IOException e10) {
                  e = e10;
                  e.printStackTrace();
                  maxInputSize = 0;
                  maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                  MediaFormat outputAudioFormat222222222 =
                      MediaFormat.createAudioFormat(
                          convertVideo.mOutputAudioMimeType,
                          convertVideo.mOutputAudioSampleRateHZ,
                          convertVideo.mOutputAudioChannelCount);
                  if (maxInputSize != 0) {}
                  outputAudioFormat222222222.setInteger(
                      MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                  outputAudioFormat222222222.setInteger(
                      MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                  Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat222222222);
                  MediaCodecInfo audioCodecInfo222222222 =
                      CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                  convertVideo.mOutputAudioEncoder =
                      CodecsHelper.createAudioEncoder(
                          audioCodecInfo222222222, outputAudioFormat222222222);
                  if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                  if ("audio/mp4a-latm".equals(mimeType)) {}
                } catch (InterruptedException e11) {
                  e = e11;
                  e.printStackTrace();
                  maxInputSize = 0;
                  maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                  MediaFormat outputAudioFormat2222222222 =
                      MediaFormat.createAudioFormat(
                          convertVideo.mOutputAudioMimeType,
                          convertVideo.mOutputAudioSampleRateHZ,
                          convertVideo.mOutputAudioChannelCount);
                  if (maxInputSize != 0) {}
                  outputAudioFormat2222222222.setInteger(
                      MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                  outputAudioFormat2222222222.setInteger(
                      MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                  Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat2222222222);
                  MediaCodecInfo audioCodecInfo2222222222 =
                      CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                  convertVideo.mOutputAudioEncoder =
                      CodecsHelper.createAudioEncoder(
                          audioCodecInfo2222222222, outputAudioFormat2222222222);
                  if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                  if ("audio/mp4a-latm".equals(mimeType)) {}
                } catch (ExecutionException e12) {
                  e = e12;
                  e.printStackTrace();
                  maxInputSize = 0;
                  maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                  MediaFormat outputAudioFormat22222222222 =
                      MediaFormat.createAudioFormat(
                          convertVideo.mOutputAudioMimeType,
                          convertVideo.mOutputAudioSampleRateHZ,
                          convertVideo.mOutputAudioChannelCount);
                  if (maxInputSize != 0) {}
                  outputAudioFormat22222222222.setInteger(
                      MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
                  outputAudioFormat22222222222.setInteger(
                      MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
                  Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat22222222222);
                  MediaCodecInfo audioCodecInfo22222222222 =
                      CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
                  convertVideo.mOutputAudioEncoder =
                      CodecsHelper.createAudioEncoder(
                          audioCodecInfo22222222222, outputAudioFormat22222222222);
                  if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {}
                  if ("audio/mp4a-latm".equals(mimeType)) {}
                }
              } else {
                pendingAudioDecoderOutputBufferIndex = pendingAudioDecoderOutputBufferIndex3;
                audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo2;
              }
            }
            convertVideo2 = convertVideo;
            audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
            pendingAudioDecoderOutputBufferIndex2 = pendingAudioDecoderOutputBufferIndex;
            audioDecoderOutputBuffers = audioDecoderOutputBuffers2;
            audioinputCodecInfo2 = audioinputCodecInfo;
            audioInputTrack2 = audioInputTrack;
          }
          convertVideo = convertVideo2;
          if (convertVideo.mInputAudioDecoder != null) {
            try {
              convertVideo.mInputAudioDecoder.stop();
              convertVideo.mInputAudioDecoder.release();
              convertVideo.mInputAudioDecoder = null;
            } catch (Exception e13) {
              Log.m94d(Constants.TAG, "Exception in releasing input audio decoder.");
              e13.printStackTrace();
            }
          }
          if (convertVideo.mCopyAudio) {
            convertVideo.mAudioExtractor.seekTo(0L, 0);
          }
        } catch (IOException e14) {
          e = e14;
          convertVideo = convertVideo2;
        } catch (InterruptedException e15) {
          e = e15;
          convertVideo = convertVideo2;
        } catch (ExecutionException e16) {
          e = e16;
          convertVideo = convertVideo2;
        }
      } else {
        convertVideo = convertVideo2;
        if (MediaFormat.MIMETYPE_AUDIO_AC3.equals(mimeType)
            || MediaFormat.MIMETYPE_AUDIO_EAC3.equals(mimeType)
            || MediaFormat.MIMETYPE_AUDIO_EAC3_JOC.equals(mimeType)
            || MediaFormat.MIMETYPE_AUDIO_AC4.equals(mimeType)) {
          if (convertVideo.mOutputAudioChannelCount > 2) {
            convertVideo.mOutputAudioChannelCount = 2;
          }
          Log.m94d(Constants.TAG, "Audio ac3 type :  mOutputAudioChannelCount is changed.");
        }
      }
      maxInputSize = 0;
      try {
        maxInputSize = inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
      } catch (NullPointerException e17) {
        Log.m94d(Constants.TAG, "Audio max input size not defined");
      }
      try {
        MediaFormat outputAudioFormat222222222222 =
            MediaFormat.createAudioFormat(
                convertVideo.mOutputAudioMimeType,
                convertVideo.mOutputAudioSampleRateHZ,
                convertVideo.mOutputAudioChannelCount);
        if (maxInputSize != 0) {
          outputAudioFormat222222222222.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
        }
        outputAudioFormat222222222222.setInteger(
            MediaFormat.KEY_BIT_RATE, convertVideo.mOutputAudioBitRate);
        outputAudioFormat222222222222.setInteger(
            MediaFormat.KEY_AAC_PROFILE, convertVideo.mOutputAudioAACProfile);
        Log.m94d(Constants.TAG, "Audio output format " + outputAudioFormat222222222222);
        MediaCodecInfo audioCodecInfo222222222222 =
            CodecsHelper.getEncoderCodec(convertVideo.mOutputAudioMimeType);
        convertVideo.mOutputAudioEncoder =
            CodecsHelper.createAudioEncoder(
                audioCodecInfo222222222222, outputAudioFormat222222222222);
        if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {
          inputAudioFormat.setInteger(MediaFormat.KEY_ENCODER_DELAY, 0);
        }
        if ("audio/mp4a-latm".equals(mimeType)) {
          convertVideo.mInputAudioDecoder =
              CodecsHelper.createAudioDecoder(
                  CodecsHelper.getDecoderCodec(mimeType), inputAudioFormat);
          return true;
        }
        convertVideo.mInputAudioDecoder = CodecsHelper.createAudioDecoder(inputAudioFormat);
        return true;
      } catch (Exception e18) {
        e18.printStackTrace();
        return false;
      }
    } catch (IOException e19) {
      Log.m94d(Constants.TAG, "createExtractor error");
      return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:267:0x0317, code lost:

     throw new java.lang.RuntimeException(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:46:0x06fd, code lost:

     if (r3 == false) goto L302;
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x0701, code lost:

     if (r52.mUserStop != false) goto L337;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x0703, code lost:

     if (r21 == false) goto L338;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x0705, code lost:

     android.util.Log.m94d(com.samsung.android.media.convert.util.Constants.TAG, "Encoding finished.");
  */
  /* JADX WARN: Code restructure failed: missing block: B:51:0x070a, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:52:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:53:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:54:0x070b, code lost:

     return;
  */
  /* JADX WARN: Removed duplicated region for block: B:105:0x03f5  */
  /* JADX WARN: Removed duplicated region for block: B:157:0x070c A[LOOP:1: B:42:0x00ea->B:157:0x070c, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:158:0x06f5 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:233:0x0679  */
  /* JADX WARN: Removed duplicated region for block: B:236:0x01db  */
  /* JADX WARN: Removed duplicated region for block: B:307:0x01d0 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:79:0x034c  */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0354  */
  @Override // com.samsung.android.media.convert.core.Convert
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  protected void startConverting() throws IOException {
    ByteBuffer[] videoEncoderOutputBuffers;
    ByteBuffer[] videoDecoderInputBuffers;
    long lastAudioSampleWrittenTime;
    ByteBuffer[] audioEncoderOutputBuffers;
    boolean audioEncoderDone;
    boolean audioEncoderDone2;
    ByteBuffer[] audioEncoderOutputBuffers2;
    MediaCodec.BufferInfo audioEncoderOutputBufferInfo;
    MediaCodec.BufferInfo audioDecoderOutputBufferInfo;
    MediaCodec.BufferInfo audioDecoderOutputBufferInfo2;
    MediaFormat audioEncoderOutputMediaFormat;
    MediaCodec.BufferInfo videoEncoderOutputBufferInfo;
    long lastAudioSampleWrittenTime2;
    MediaCodec.BufferInfo audioEncoderOutputBufferInfo2;
    MediaCodec.BufferInfo videoDecoderOutputBufferInfo;
    MediaCodec.BufferInfo audioDecoderOutputBufferInfo3;
    MediaCodec.BufferInfo audioDecoderOutputBufferInfo4;
    MediaFormat audioEncoderOutputMediaFormat2;
    MediaFormat audioEncoderOutputMediaFormat3;
    int encoderOutputBufferIndex;
    int decoderOutputBufferIndex;
    if (this.mUserStop) {
      Log.m94d(Constants.TAG, "Not starting encoding because it is stopped by user.");
      return;
    }
    if (prepareVideoCodec() && prepareAudioCodec()) {
      this.mConverting = true;
      ByteBuffer[] videoEncoderOutputBuffers2 = this.mOutputVideoEncoder.getOutputBuffers();
      ByteBuffer[] videoDecoderInputBuffers2 = this.mInputVideoDecoder.getInputBuffers();
      ByteBuffer[] videoDecoderOutputBuffers = this.mInputVideoDecoder.getOutputBuffers();
      ByteBuffer[] audioEncoderOutputBuffers3 =
          this.mCopyAudio ? this.mOutputAudioEncoder.getOutputBuffers() : null;
      ByteBuffer[] audioEncoderInputBuffers =
          this.mCopyAudio ? this.mOutputAudioEncoder.getInputBuffers() : null;
      ByteBuffer[] audioDecoderOutputBuffers =
          this.mCopyAudio ? this.mInputAudioDecoder.getOutputBuffers() : null;
      ByteBuffer[] audioDecoderInputBuffers =
          this.mCopyAudio ? this.mInputAudioDecoder.getInputBuffers() : null;
      MediaCodec.BufferInfo videoEncoderOutputBufferInfo2 = new MediaCodec.BufferInfo();
      MediaCodec.BufferInfo audioDecoderOutputBufferInfo5 = new MediaCodec.BufferInfo();
      MediaCodec.BufferInfo videoEncoderOutputBufferInfo3 = new MediaCodec.BufferInfo();
      MediaCodec.BufferInfo audioDecoderOutputBufferInfo6 = new MediaCodec.BufferInfo();
      MediaFormat audioEncoderOutputMediaFormat4 = null;
      MediaFormat videoEncoderOutputMediaFormat = null;
      boolean z = this.mCopyAudio;
      boolean audioExtractorDone = !z;
      boolean audioDecoderDone = !z;
      boolean audioEncoderDone3 = !z;
      boolean videoExtractorDone = false;
      boolean videoEncoderDone = false;
      boolean videoDecoderDone = false;
      ByteBuffer[] videoDecoderOutputBuffers2 = videoDecoderOutputBuffers;
      ByteBuffer[] audioEncoderOutputBuffers4 = audioEncoderOutputBuffers3;
      long j = this.mTrimVideoStartUs;
      if (j == 0) {
        videoEncoderOutputBuffers = videoEncoderOutputBuffers2;
      } else {
        videoEncoderOutputBuffers = videoEncoderOutputBuffers2;
        this.mVideoExtractor.seekTo(j, 0);
      }
      if (this.mCopyAudio) {
        long j2 = this.mTrimAudioStartUs;
        if (j2 != 0) {
          this.mAudioExtractor.seekTo(j2, 0);
          while (true) {
            videoDecoderInputBuffers = videoDecoderInputBuffers2;
            if (this.mAudioExtractor.getSampleTime() >= this.mTrimAudioStartUs) {
              break;
            }
            if (this.mAudioExtractor.getSampleTime() == -1) {
              throw new RuntimeException("Invalid File!");
            }
            this.mAudioExtractor.advance();
            videoDecoderInputBuffers2 = videoDecoderInputBuffers;
          }
          lastAudioSampleWrittenTime = -1;
          audioEncoderOutputBuffers = audioEncoderOutputBuffers4;
          audioEncoderDone = audioEncoderDone3;
          ByteBuffer[] audioDecoderOutputBuffers2 = audioDecoderOutputBuffers;
          int pendingAudioDecoderOutputBufferIndex = -1;
          while (true) {
            if (!videoEncoderDone && audioEncoderDone) {
              break;
            }
            long lastAudioSampleWrittenTime3 = lastAudioSampleWrittenTime;
            if (this.mUserStop && !videoExtractorDone) {
              if (videoEncoderOutputMediaFormat == null || this.mMuxerStarted) {
                int decoderInputBufferIndex = this.mInputVideoDecoder.dequeueInputBuffer(10000L);
                if (decoderInputBufferIndex == -1) {
                  Log.m94d(Constants.TAG, "no video decoder input buffer");
                  audioEncoderDone2 = audioEncoderDone;
                  audioEncoderOutputBuffers2 = audioEncoderOutputBuffers;
                  audioEncoderOutputBufferInfo = videoEncoderOutputBufferInfo3;
                  audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo6;
                } else {
                  ByteBuffer decoderInputBuffer = videoDecoderInputBuffers[decoderInputBufferIndex];
                  audioEncoderOutputBuffers2 = audioEncoderOutputBuffers;
                  int size = this.mVideoExtractor.readSampleData(decoderInputBuffer, 0);
                  audioEncoderDone2 = audioEncoderDone;
                  long presentationTime = this.mVideoExtractor.getSampleTime();
                  audioEncoderOutputBufferInfo = videoEncoderOutputBufferInfo3;
                  Log.m94d(
                      Constants.TAG,
                      " video decoder: get input buffer presentationTime :"
                          + presentationTime
                          + ", size : "
                          + size);
                  audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo6;
                  if (presentationTime <= this.mTrimVideoEndUs && size >= 0) {
                    this.mInputVideoDecoder.queueInputBuffer(
                        decoderInputBufferIndex,
                        0,
                        size,
                        presentationTime,
                        this.mVideoExtractor.getSampleFlags());
                    this.mVideoExtractor.advance();
                  } else {
                    videoExtractorDone = true;
                  }
                  if (videoExtractorDone) {
                    Log.m94d(Constants.TAG, "video extractor: EOS");
                    this.mInputVideoDecoder.queueInputBuffer(decoderInputBufferIndex, 0, 0, 0L, 4);
                  }
                }
              } else {
                audioEncoderDone2 = audioEncoderDone;
                audioEncoderOutputBuffers2 = audioEncoderOutputBuffers;
                audioEncoderOutputBufferInfo = videoEncoderOutputBufferInfo3;
                audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo6;
              }
            } else {
              audioEncoderDone2 = audioEncoderDone;
              audioEncoderOutputBuffers2 = audioEncoderOutputBuffers;
              audioEncoderOutputBufferInfo = videoEncoderOutputBufferInfo3;
              audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo6;
            }
            while (!this.mUserStop && !videoDecoderDone) {
              if (videoEncoderOutputMediaFormat == null && !this.mMuxerStarted) {
                audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                break;
              }
              decoderOutputBufferIndex =
                  this.mInputVideoDecoder.dequeueOutputBuffer(
                      audioDecoderOutputBufferInfo5, 10000L);
              if (decoderOutputBufferIndex != -1) {
                Log.m94d(Constants.TAG, "no video decoder output buffer");
                audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                break;
              }
              if (decoderOutputBufferIndex == -3) {
                Log.m94d(Constants.TAG, "video decoder: output buffers changed");
                ByteBuffer[] videoDecoderOutputBuffers3 =
                    this.mInputVideoDecoder.getOutputBuffers();
                videoDecoderOutputBuffers2 = videoDecoderOutputBuffers3;
                audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                break;
              }
              if (decoderOutputBufferIndex == -2) {
                Log.m94d(
                    Constants.TAG,
                    "video decoder: codec info format changed"
                        + this.mInputVideoDecoder.getOutputFormat());
                if (this.isCcodec && !this.skipBufferInfo) {
                  this.skipBufferInfo = true;
                }
                if (this.skipBufferInfo) {
                  Log.m94d(Constants.TAG, "skip info in case of ccodec");
                  this.skipBufferInfo = false;
                } else {
                  Log.m94d(Constants.TAG, "break in case of normal case");
                  audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                  audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                  break;
                }
              } else {
                ByteBuffer byteBuffer = videoDecoderOutputBuffers2[decoderOutputBufferIndex];
                if ((audioDecoderOutputBufferInfo5.flags & 2) != 0) {
                  Log.m94d(Constants.TAG, "video decoder: codec config buffer");
                  this.mInputVideoDecoder.releaseOutputBuffer(decoderOutputBufferIndex, false);
                  audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                  audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                  break;
                }
                Log.m94d(
                    Constants.TAG,
                    "video decoder: returned buffer for time "
                        + audioDecoderOutputBufferInfo5.presentationTimeUs);
                boolean render = audioDecoderOutputBufferInfo5.size != 0;
                this.mInputVideoDecoder.releaseOutputBuffer(decoderOutputBufferIndex, render);
                if (render) {
                  Log.m94d(Constants.TAG, "output surface: await new image");
                  try {
                    if (!this.mOutputSurface.checkForNewImage(1000)) {
                      try {
                        Log.m94d(
                            Constants.TAG,
                            "video decoder: checkForNewImage return false!!  mUserStop : "
                                + this.mUserStop);
                      } catch (RuntimeException e) {
                        r = e;
                        audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                        audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                      }
                    } else {
                      Log.m94d(Constants.TAG, "output surface: draw image");
                      GLES20.glClear(16384);
                      this.mOutputSurface.drawImage();
                      audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                      try {
                        audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                        try {
                          if (audioDecoderOutputBufferInfo5.presentationTimeUs
                              >= this.mTrimVideoStartUs) {
                            this.mInputSurface.setPresentationTime(
                                audioDecoderOutputBufferInfo5.presentationTimeUs * 1000);
                            Log.m94d(
                                Constants.TAG,
                                "input surface: swap buffers time:"
                                    + audioDecoderOutputBufferInfo5.presentationTimeUs);
                            this.mInputSurface.swapBuffers();
                            Log.m94d(Constants.TAG, "video encoder: notified of new frame");
                          }
                        } catch (RuntimeException e2) {
                          r = e2;
                        }
                      } catch (RuntimeException e3) {
                        r = e3;
                        audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                      }
                    }
                  } catch (RuntimeException e4) {
                    r = e4;
                    audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                    audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                  }
                  String msg = r.getMessage();
                  if (!this.mUserStop
                      || msg == null
                      || !msg.equals("Surface frame wait timed out")) {
                    break;
                  }
                } else {
                  audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
                  audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
                }
                if ((audioDecoderOutputBufferInfo5.flags & 4) != 0) {
                  Log.m94d(Constants.TAG, "video decoder: EOS");
                  this.mOutputVideoEncoder.signalEndOfInputStream();
                  videoDecoderDone = true;
                }
              }
            }
            audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
            audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
            if (!this.mUserStop
                && !videoEncoderDone
                && (videoEncoderOutputMediaFormat == null || this.mMuxerStarted)) {
              encoderOutputBufferIndex =
                  this.mOutputVideoEncoder.dequeueOutputBuffer(
                      videoEncoderOutputBufferInfo2, 10000L);
              if (encoderOutputBufferIndex != -1) {
                Log.m94d(Constants.TAG, "no video encoder output buffer");
              } else if (encoderOutputBufferIndex == -3) {
                Log.m94d(Constants.TAG, "video encoder: output buffers changed");
                videoEncoderOutputBuffers = this.mOutputVideoEncoder.getOutputBuffers();
              } else if (encoderOutputBufferIndex == -2) {
                Log.m94d(
                    Constants.TAG,
                    "video encoder: output format changed "
                        + this.mOutputVideoEncoder.getOutputFormat());
                if (this.mVideoTrackIndex >= 0) {
                  throw new RuntimeException(
                      "Video encoder output format changed after muxer has started");
                }
                MediaFormat videoEncoderOutputMediaFormat2 =
                    this.mOutputVideoEncoder.getOutputFormat();
                videoEncoderOutputMediaFormat = videoEncoderOutputMediaFormat2;
              } else if (encoderOutputBufferIndex < 0) {
                Log.m94d(
                    Constants.TAG, "Unexpected result from video encoder dequeue output format.");
              } else {
                ByteBuffer encoderOutputBuffer =
                    videoEncoderOutputBuffers[encoderOutputBufferIndex];
                if ((videoEncoderOutputBufferInfo2.flags & 2) != 0) {
                  Log.m94d(Constants.TAG, "video encoder: codec config buffer");
                  this.mOutputVideoEncoder.releaseOutputBuffer(encoderOutputBufferIndex, false);
                } else {
                  if (videoEncoderOutputBufferInfo2.size != 0) {
                    Log.m94d(
                        Constants.TAG,
                        "video encoder: writing sample data timestamp "
                            + videoEncoderOutputBufferInfo2.presentationTimeUs);
                    this.mMuxer.writeSampleData(
                        this.mVideoTrackIndex, encoderOutputBuffer, videoEncoderOutputBufferInfo2);
                  }
                  if ((videoEncoderOutputBufferInfo2.flags & 4) != 0) {
                    Log.m94d(Constants.TAG, "video encoder: EOS");
                    videoEncoderDone = true;
                  }
                  this.mOutputVideoEncoder.releaseOutputBuffer(encoderOutputBufferIndex, false);
                }
              }
            }
            if (this.mCopyAudio) {
              videoEncoderOutputBufferInfo = videoEncoderOutputBufferInfo2;
              lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
              audioEncoderOutputBufferInfo2 = audioEncoderOutputBufferInfo;
              MediaCodec.BufferInfo bufferInfo = audioDecoderOutputBufferInfo2;
              videoDecoderOutputBufferInfo = audioDecoderOutputBufferInfo5;
              audioDecoderOutputBufferInfo3 = bufferInfo;
              audioEncoderOutputBuffers = audioEncoderOutputBuffers2;
              audioEncoderDone = audioEncoderDone2;
            } else {
              if (!this.mUserStop
                  && !audioExtractorDone
                  && (audioEncoderOutputMediaFormat == null || this.mMuxerStarted)) {
                long presentationTimeUs = this.mAudioExtractor.getSampleTime();
                int audioDecoderInputBufferIndex =
                    this.mInputAudioDecoder.dequeueInputBuffer(10000L);
                if (audioDecoderInputBufferIndex == -1) {
                  Log.m94d(Constants.TAG, "audio decoder input try again later");
                } else {
                  ByteBuffer audioDecoderInputBuffer =
                      audioDecoderInputBuffers[audioDecoderInputBufferIndex];
                  int size2 = this.mAudioExtractor.readSampleData(audioDecoderInputBuffer, 0);
                  Log.m94d(
                      Constants.TAG,
                      "audioDecoderInput  presentationTimeUs :"
                          + presentationTimeUs
                          + ", size:"
                          + size2);
                  if (presentationTimeUs <= this.mTrimAudioEndUs && size2 >= 0) {
                    this.mInputAudioDecoder.queueInputBuffer(
                        audioDecoderInputBufferIndex,
                        0,
                        size2,
                        presentationTimeUs,
                        this.mAudioExtractor.getSampleFlags());
                    this.mAudioExtractor.advance();
                  } else {
                    audioExtractorDone = true;
                  }
                  if (audioExtractorDone) {
                    Log.m94d(Constants.TAG, "audio decoder sending EOS");
                    this.mInputAudioDecoder.queueInputBuffer(
                        audioDecoderInputBufferIndex, 0, 0, 0L, 4);
                  }
                }
              }
              if (!this.mUserStop
                  && !audioDecoderDone
                  && pendingAudioDecoderOutputBufferIndex == -1) {
                if (audioEncoderOutputMediaFormat == null || this.mMuxerStarted) {
                  audioDecoderOutputBufferInfo4 = audioDecoderOutputBufferInfo2;
                  int audioDecoderOutputBufferIndex =
                      this.mInputAudioDecoder.dequeueOutputBuffer(
                          audioDecoderOutputBufferInfo4, 10000L);
                  if (audioDecoderOutputBufferIndex == -1) {
                    Log.m94d(
                        Constants.TAG,
                        "audio decoder output buffer try again later while decoding");
                  } else if (audioDecoderOutputBufferIndex == -3) {
                    Log.m94d(Constants.TAG, "audio decoder: output buffers changed");
                    ByteBuffer[] audioDecoderOutputBuffers3 =
                        this.mInputAudioDecoder.getOutputBuffers();
                    audioDecoderOutputBuffers2 = audioDecoderOutputBuffers3;
                  } else if (audioDecoderOutputBufferIndex == -2) {
                    Log.m94d(Constants.TAG, "audio decoder: output format changed: ");
                  } else if (audioDecoderOutputBufferIndex < 0) {
                    Log.m94d(
                        Constants.TAG,
                        "Unexpected result from audio decoder dequeue output format.");
                  } else if ((audioDecoderOutputBufferInfo4.flags & 2) != 0) {
                    Log.m94d(Constants.TAG, "audio decoder: codec config buffer");
                    this.mInputAudioDecoder.releaseOutputBuffer(
                        audioDecoderOutputBufferIndex, false);
                  } else {
                    pendingAudioDecoderOutputBufferIndex = audioDecoderOutputBufferIndex;
                  }
                } else {
                  audioDecoderOutputBufferInfo4 = audioDecoderOutputBufferInfo2;
                }
              } else {
                audioDecoderOutputBufferInfo4 = audioDecoderOutputBufferInfo2;
              }
              if (this.mUserStop
                  || audioDecoderDone
                  || pendingAudioDecoderOutputBufferIndex == -1) {
                videoEncoderOutputBufferInfo = videoEncoderOutputBufferInfo2;
                videoDecoderOutputBufferInfo = audioDecoderOutputBufferInfo5;
              } else {
                int size3 = audioDecoderOutputBufferInfo4.size;
                long presentationTime2 = audioDecoderOutputBufferInfo4.presentationTimeUs;
                videoEncoderOutputBufferInfo = videoEncoderOutputBufferInfo2;
                videoDecoderOutputBufferInfo = audioDecoderOutputBufferInfo5;
                int audioEncoderInputBufferIndex =
                    this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                if (audioEncoderInputBufferIndex == -1) {
                  Log.m94d(Constants.TAG, "audio encoder input buffer try again later");
                } else {
                  if (size3 >= 0 && presentationTime2 >= 0) {
                    ByteBuffer encoderInputBuffer =
                        audioEncoderInputBuffers[audioEncoderInputBufferIndex];
                    ByteBuffer audioDecoderOutputBuffer =
                        audioDecoderOutputBuffers2[pendingAudioDecoderOutputBufferIndex]
                            .duplicate();
                    audioDecoderOutputBuffer.position(audioDecoderOutputBufferInfo4.offset);
                    audioDecoderOutputBuffer.limit(audioDecoderOutputBufferInfo4.offset + size3);
                    encoderInputBuffer.position(0);
                    encoderInputBuffer.put(audioDecoderOutputBuffer);
                    this.mOutputAudioEncoder.queueInputBuffer(
                        audioEncoderInputBufferIndex,
                        0,
                        size3,
                        presentationTime2,
                        audioDecoderOutputBufferInfo4.flags);
                  }
                  this.mInputAudioDecoder.releaseOutputBuffer(
                      pendingAudioDecoderOutputBufferIndex, false);
                  pendingAudioDecoderOutputBufferIndex = -1;
                  if ((audioDecoderOutputBufferInfo4.flags & 4) != 0) {
                    Log.m94d(Constants.TAG, "audio decoder: EOS");
                    audioDecoderDone = true;
                  }
                }
              }
              if (!this.mUserStop && !audioEncoderDone2) {
                if (audioEncoderOutputMediaFormat == null || this.mMuxerStarted) {
                  audioEncoderOutputBufferInfo2 = audioEncoderOutputBufferInfo;
                  int audioEncoderOutputBufferIndex =
                      this.mOutputAudioEncoder.dequeueOutputBuffer(
                          audioEncoderOutputBufferInfo2, 10000L);
                  if (audioEncoderOutputBufferIndex == -1) {
                    Log.m94d(Constants.TAG, "audio encoder output buffer try again later");
                    audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                    audioEncoderOutputMediaFormat2 = audioEncoderOutputMediaFormat;
                    lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                  } else if (audioEncoderOutputBufferIndex == -3) {
                    Log.m94d(Constants.TAG, "audio encoder: output buffers changed");
                    audioEncoderOutputBuffers = this.mOutputAudioEncoder.getOutputBuffers();
                    audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                    lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                    audioEncoderDone = audioEncoderDone2;
                  } else if (audioEncoderOutputBufferIndex == -2) {
                    if (this.mAudioTrackIndex >= 0) {
                      throw new RuntimeException(
                          "Audio encoder output format changed after muxer is started.");
                    }
                    audioEncoderOutputMediaFormat = this.mOutputAudioEncoder.getOutputFormat();
                    Log.m94d(
                        Constants.TAG,
                        "audio encoder: output format changed " + audioEncoderOutputMediaFormat);
                    audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                    audioEncoderOutputBuffers = audioEncoderOutputBuffers2;
                    lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                    audioEncoderDone = audioEncoderDone2;
                  } else if (audioEncoderOutputBufferIndex < 0) {
                    Log.m94d(
                        Constants.TAG,
                        "Unexpected result from audio encoder dequeue output format.");
                    audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                    audioEncoderOutputMediaFormat2 = audioEncoderOutputMediaFormat;
                    lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                  } else {
                    ByteBuffer audioEncoderOutputBuffer =
                        audioEncoderOutputBuffers2[audioEncoderOutputBufferIndex];
                    if ((audioEncoderOutputBufferInfo2.flags & 2) != 0) {
                      Log.m94d(Constants.TAG, "audio encoder ignoring BUFFER_FLAG_CODEC_CONFIG");
                      this.mOutputAudioEncoder.releaseOutputBuffer(
                          audioEncoderOutputBufferIndex, false);
                      audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                      audioEncoderOutputMediaFormat2 = audioEncoderOutputMediaFormat;
                      lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                    } else {
                      if (audioEncoderOutputBufferInfo2.size == 0) {
                        audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                        audioEncoderOutputMediaFormat3 = audioEncoderOutputMediaFormat;
                        lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                      } else if (lastAudioSampleWrittenTime3
                          <= audioEncoderOutputBufferInfo2.presentationTimeUs) {
                        Log.m94d(
                            Constants.TAG,
                            "audio encoder writing sample data to muxer  time: "
                                + audioEncoderOutputBufferInfo2.presentationTimeUs);
                        long lastAudioSampleWrittenTime4 =
                            audioEncoderOutputBufferInfo2.presentationTimeUs;
                        this.mMuxer.writeSampleData(
                            this.mAudioTrackIndex,
                            audioEncoderOutputBuffer,
                            audioEncoderOutputBufferInfo2);
                        audioEncoderOutputMediaFormat3 = audioEncoderOutputMediaFormat;
                        lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime4;
                        audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                      } else {
                        lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                        audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                        audioEncoderOutputMediaFormat3 = audioEncoderOutputMediaFormat;
                        Log.m94d(
                            Constants.TAG,
                            "lastAudioSampleWrittenTime : "
                                + lastAudioSampleWrittenTime2
                                + " > presentationTime :"
                                + audioEncoderOutputBufferInfo2.presentationTimeUs);
                        if (!audioDecoderDone) {
                          throw new IOException("Audio time stamps are not in increasing order.");
                        }
                      }
                      if ((audioEncoderOutputBufferInfo2.flags & 4) == 0) {
                        audioEncoderDone = audioEncoderDone2;
                      } else {
                        Log.m94d(Constants.TAG, "audio encoder: EOS");
                        audioEncoderDone = true;
                      }
                      this.mOutputAudioEncoder.releaseOutputBuffer(
                          audioEncoderOutputBufferIndex, false);
                      audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat3;
                      audioEncoderOutputBuffers = audioEncoderOutputBuffers2;
                    }
                  }
                } else {
                  audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                  audioEncoderOutputMediaFormat2 = audioEncoderOutputMediaFormat;
                  lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                  audioEncoderOutputBufferInfo2 = audioEncoderOutputBufferInfo;
                }
              } else {
                audioDecoderOutputBufferInfo3 = audioDecoderOutputBufferInfo4;
                audioEncoderOutputMediaFormat2 = audioEncoderOutputMediaFormat;
                lastAudioSampleWrittenTime2 = lastAudioSampleWrittenTime3;
                audioEncoderOutputBufferInfo2 = audioEncoderOutputBufferInfo;
              }
              audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat2;
              audioEncoderOutputBuffers = audioEncoderOutputBuffers2;
              audioEncoderDone = audioEncoderDone2;
            }
            if (this.mUserStop && !this.mMuxerStarted && videoEncoderOutputMediaFormat != null) {
              if (!this.mCopyAudio || audioEncoderOutputMediaFormat != null) {
                String filepath =
                    mUseUri
                        ? getVEEditFilePath(this.mContext, this.mInputUri)
                        : this.mInputFilePath;
                if (updateCreationTime(filepath, false)) {
                  videoEncoderOutputMediaFormat.setInteger(KEY_MUXER_AUTHOR, 8);
                  videoEncoderOutputMediaFormat.setInteger(KEY_MUXER_TRANSCODING, 1);
                }
                this.mVideoTrackIndex = this.mMuxer.addTrack(videoEncoderOutputMediaFormat);
                if (this.mCopyAudio) {
                  this.mAudioTrackIndex = this.mMuxer.addTrack(audioEncoderOutputMediaFormat);
                }
                this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
                this.mMuxer.start();
                this.mMuxerStarted = true;
              }
            }
            if (this.mUserStop) {
              videoEncoderOutputBufferInfo2 = videoEncoderOutputBufferInfo;
              videoEncoderOutputBufferInfo3 = audioEncoderOutputBufferInfo2;
              long j3 = lastAudioSampleWrittenTime2;
              audioEncoderOutputMediaFormat4 = audioEncoderOutputMediaFormat;
              audioDecoderOutputBufferInfo6 = audioDecoderOutputBufferInfo3;
              lastAudioSampleWrittenTime = j3;
              audioDecoderOutputBufferInfo5 = videoDecoderOutputBufferInfo;
            } else {
              Log.m94d(Constants.TAG, "Encoding abruptly stopped.");
              break;
            }
          }
        }
      }
      videoDecoderInputBuffers = videoDecoderInputBuffers2;
      lastAudioSampleWrittenTime = -1;
      audioEncoderOutputBuffers = audioEncoderOutputBuffers4;
      audioEncoderDone = audioEncoderDone3;
      ByteBuffer[] audioDecoderOutputBuffers22 = audioDecoderOutputBuffers;
      int pendingAudioDecoderOutputBufferIndex2 = -1;
      while (true) {
        if (!videoEncoderDone) {}
        long lastAudioSampleWrittenTime32 = lastAudioSampleWrittenTime;
        if (this.mUserStop) {}
        audioEncoderDone2 = audioEncoderDone;
        audioEncoderOutputBuffers2 = audioEncoderOutputBuffers;
        audioEncoderOutputBufferInfo = videoEncoderOutputBufferInfo3;
        audioDecoderOutputBufferInfo = audioDecoderOutputBufferInfo6;
        while (!this.mUserStop) {
          if (videoEncoderOutputMediaFormat == null) {}
          decoderOutputBufferIndex =
              this.mInputVideoDecoder.dequeueOutputBuffer(audioDecoderOutputBufferInfo5, 10000L);
          if (decoderOutputBufferIndex != -1) {}
        }
        audioDecoderOutputBufferInfo2 = audioDecoderOutputBufferInfo;
        audioEncoderOutputMediaFormat = audioEncoderOutputMediaFormat4;
        if (!this.mUserStop) {
          encoderOutputBufferIndex =
              this.mOutputVideoEncoder.dequeueOutputBuffer(videoEncoderOutputBufferInfo2, 10000L);
          if (encoderOutputBufferIndex != -1) {}
        }
        if (this.mCopyAudio) {}
        if (this.mUserStop) {}
        if (this.mUserStop) {}
        videoEncoderOutputBufferInfo2 = videoEncoderOutputBufferInfo;
        videoEncoderOutputBufferInfo3 = audioEncoderOutputBufferInfo2;
        long j32 = lastAudioSampleWrittenTime2;
        audioEncoderOutputMediaFormat4 = audioEncoderOutputMediaFormat;
        audioDecoderOutputBufferInfo6 = audioDecoderOutputBufferInfo3;
        lastAudioSampleWrittenTime = j32;
        audioDecoderOutputBufferInfo5 = videoDecoderOutputBufferInfo;
      }
    }
  }

  @Override // com.samsung.android.media.convert.core.Convert
  protected void release() {
    try {
      Log.m94d(Constants.TAG, "releasing encoder objects");
      if (this.mOutputVideoEncoder != null) {
        try {
          this.mOutputVideoEncoder.stop();
          this.mOutputVideoEncoder.release();
          this.mOutputVideoEncoder = null;
        } catch (Exception e) {
          Log.m94d(Constants.TAG, "Exception in releasing output video encoder.");
          e.printStackTrace();
        }
      }
      if (this.mInputVideoDecoder != null) {
        try {
          this.mInputVideoDecoder.stop();
          this.mInputVideoDecoder.release();
          this.mInputVideoDecoder = null;
        } catch (Exception e2) {
          Log.m94d(Constants.TAG, "Exception in releasing input video decoder.");
          e2.printStackTrace();
        }
      }
      MediaExtractor mediaExtractor = this.mVideoExtractor;
      if (mediaExtractor != null) {
        try {
          mediaExtractor.release();
          this.mVideoExtractor = null;
        } catch (Exception e3) {
          Log.m94d(Constants.TAG, "Exception in releasing video extractor.");
          e3.printStackTrace();
        }
      }
      OutputSurface outputSurface = this.mOutputSurface;
      if (outputSurface != null) {
        try {
          outputSurface.release();
          this.mOutputSurface = null;
        } catch (Exception e4) {
          Log.m94d(Constants.TAG, "Exception in releasing outputSurface.");
          e4.printStackTrace();
        }
      }
      InputSurface inputSurface = this.mInputSurface;
      if (inputSurface != null) {
        try {
          inputSurface.release();
          this.mInputSurface = null;
        } catch (Exception e5) {
          Log.m94d(Constants.TAG, "Exception in releasing input surface.");
          e5.printStackTrace();
        }
      }
      if (this.mOutputAudioEncoder != null) {
        try {
          this.mOutputAudioEncoder.stop();
          this.mOutputAudioEncoder.release();
          this.mOutputAudioEncoder = null;
        } catch (Exception e6) {
          Log.m94d(Constants.TAG, "Exception in releasing output audio encoder.");
          e6.printStackTrace();
        }
      }
      if (this.mInputAudioDecoder != null) {
        try {
          this.mInputAudioDecoder.stop();
          this.mInputAudioDecoder.release();
          this.mInputAudioDecoder = null;
        } catch (Exception e7) {
          Log.m94d(Constants.TAG, "Exception in releasing input audio decoder.");
          e7.printStackTrace();
        }
      }
      MediaExtractor mediaExtractor2 = this.mAudioExtractor;
      if (mediaExtractor2 != null) {
        try {
          mediaExtractor2.release();
          this.mAudioExtractor = null;
        } catch (Exception e8) {
          Log.m94d(Constants.TAG, "Exception in releasing audio extractor.");
          e8.printStackTrace();
        }
      }
      if (this.mMuxer != null) {
        try {
          if (this.mMuxerStarted) {
            this.mMuxer.stop();
          }
          this.mMuxer.release();
          this.mMuxer = null;
        } catch (Exception e9) {
          Log.m94d(Constants.TAG, "Exception in releasing muxer.");
          e9.printStackTrace();
        }
      }
      if (this.mUpdateCreationTime) {
        updateCreationTime(this.mOutputFilePath, true);
      }
      synchronized (this.mStopLock) {
        this.mConverting = false;
        this.mStopLock.notifyAll();
      }
    } catch (Throwable th) {
      synchronized (this.mStopLock) {
        this.mConverting = false;
        this.mStopLock.notifyAll();
        throw th;
      }
    }
  }

  @Override // com.samsung.android.media.convert.core.Convert
  public boolean stop() {
    Log.m94d(
        Constants.TAG,
        "Stop method called  mConverting :" + this.mConverting + ", mUserStop :" + this.mUserStop);
    if (!this.mConverting || this.mUserStop) {
      return false;
    }
    synchronized (this.mStopLock) {
      OutputSurface outputSurface = this.mOutputSurface;
      if (outputSurface != null) {
        outputSurface.notifyFrameSyncObject();
      }
      this.mUserStop = true;
      if (this.mThread != null) {
        this.mThread.interrupt();
      }
      if (!this.mConverting) {
        return false;
      }
      try {
        try {
          Log.m94d(Constants.TAG, "Calling wait on stop lock.");
          this.mStopLock.wait(5000L);
        } catch (InterruptedException e) {
          Log.m94d(Constants.TAG, "Stop lock interrupted.");
          e.printStackTrace();
          Log.m94d(Constants.TAG, "Stop method finally  mConverting :" + this.mConverting);
          if (this.mConverting) {}
          return true;
        }
      } finally {
        Log.m94d(Constants.TAG, "Stop method finally  mConverting :" + this.mConverting);
        if (this.mConverting) {
          release();
        }
      }
    }
  }

  public int getOutputFileSize() {
    MediaExtractor me;
    try {
      if (mUseUri) {
        me = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
      } else {
        me = CodecsHelper.createExtractor(this.mInputFilePath);
      }
      int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(me);
      MediaFormat inputFormat = me.getTrackFormat(videoTrack);
      long trimEndTime = this.mTrimVideoEndUs;
      if (trimEndTime == 0) {
        trimEndTime = inputFormat.getLong(MediaFormat.KEY_DURATION);
        Log.m94d(
            Constants.TAG,
            "getOutputFileSize  trimEndTime was 0 but updated  trimEndTime : " + trimEndTime);
      }
      this.mSourceFrameRate = 0;
      this.mSourceFrameRate = inputFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
      if (this.mSourceFrameRate == 0 || this.mSourceFrameRate > 250) {
        this.mSourceFrameRate =
            getVideoFramerate(this.mInputFilePath, this.mContext, this.mInputUri);
      }
      if (this.mSourceFrameRate > 0) {
        this.mOutputVideoFrameRate = this.mSourceFrameRate;
      }
      Log.m94d(
          Constants.TAG,
          "mSourceFrameRate :"
              + this.mSourceFrameRate
              + ", mOutputVideoFrameRate :"
              + this.mOutputVideoFrameRate);
      me.release();
      int outputVideBitRate =
          CodecsHelper.suggestBitRate(
                  this.mOutputWidth, this.mOutputHeight, this.mOutputVideoFrameRate)
              * 1000;
      int size =
          (int)
              (((trimEndTime - this.mTrimVideoStartUs) / 8000000.0d)
                  * ((this.mOutputAudioBitRate + outputVideBitRate) / 1000.0d));
      return ((int) this.mSizeFraction) * size;
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    } catch (NullPointerException e2) {
      e2.printStackTrace();
      return -1;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:

     if (r5 != null) goto L33;
  */
  /* JADX WARN: Code restructure failed: missing block: B:53:0x005b, code lost:

     if (0 == 0) goto L34;
  */
  /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
  /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean CheckVideoCodec(String filepath, boolean isRewrite) {
    int width = 0;
    int height = 0;
    int error = 0;
    int codecrsc = isRewrite ? 0 : CodecsHelper.getRemainedResourceCapacity();
    MediaExtractor me = null;
    try {
      try {
        try {
          me = CodecsHelper.createExtractor(filepath);
          if (me == null) {
            Log.m94d(Constants.TAG, "Can't create Extractor");
            if (me != null) {
              me.release();
            }
            return false;
          }
          int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(me);
          if (videoTrack < 0) {
            error = videoTrack;
            Log.m94d(Constants.TAG, "Can't get VideoTrack");
          } else {
            MediaFormat inputFormat = me.getTrackFormat(videoTrack);
            width = inputFormat.getInteger("width");
            height = inputFormat.getInteger("height");
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
          if (0 != 0) {
            me.release();
          }
          if (error == 0) {
            Log.m94d(Constants.TAG, "Extactor Error appear : " + error);
            return false;
          }
          if (width <= 0 || height <= 0) {
            Log.m94d(
                Constants.TAG,
                "Resolution Error appear : width = " + width + ", height= " + height);
            return false;
          }
          if (!isRewrite && codecrsc >= 0 && codecrsc < width * height * 2) {
            Log.m94d(Constants.TAG, "Codec resource is not enough");
            return false;
          }
          this.mOutputWidth = width;
          this.mOutputHeight = height;
          if (this.mOutputWidth > 1920 && this.mOutputHeight > 1080) {
            this.mOutputWidth = 1920;
            this.mOutputHeight = 1080;
          }
          Log.m94d(
              Constants.TAG,
              "mOutputWidth :" + this.mOutputWidth + ", mOutputHeight :" + this.mOutputHeight);
          return true;
        }
      } catch (IOException e2) {
        e2.printStackTrace();
      } catch (NullPointerException e3) {
        e3.printStackTrace();
        if (0 != 0) {
          me.release();
        }
        if (error == 0) {}
      }
    } catch (Throwable th) {
      if (0 != 0) {
        me.release();
      }
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:

     if (r5 != null) goto L33;
  */
  /* JADX WARN: Code restructure failed: missing block: B:53:0x005b, code lost:

     if (0 == 0) goto L34;
  */
  /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
  /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean CheckVideoCodec(Context context, Uri uri, boolean isRewrite) {
    int width = 0;
    int height = 0;
    int error = 0;
    int codecrsc = isRewrite ? 0 : CodecsHelper.getRemainedResourceCapacity();
    MediaExtractor me = null;
    try {
      try {
        try {
          me = CodecsHelper.createExtractor(context, uri);
          if (me == null) {
            Log.m94d(Constants.TAG, "Can't create Extractor");
            if (me != null) {
              me.release();
            }
            return false;
          }
          int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(me);
          if (videoTrack < 0) {
            error = videoTrack;
            Log.m94d(Constants.TAG, "Can't get VideoTrack");
          } else {
            MediaFormat inputFormat = me.getTrackFormat(videoTrack);
            width = inputFormat.getInteger("width");
            height = inputFormat.getInteger("height");
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
          if (0 != 0) {
            me.release();
          }
          if (error == 0) {
            Log.m94d(Constants.TAG, "Extactor Error appear : " + error);
            return false;
          }
          if (width <= 0 || height <= 0) {
            Log.m94d(
                Constants.TAG,
                "Resolution Error appear : width = " + width + ", height= " + height);
            return false;
          }
          if (!isRewrite && codecrsc >= 0 && codecrsc < width * height * 2) {
            Log.m94d(Constants.TAG, "Codec resource is not enough");
            return false;
          }
          this.mOutputWidth = width;
          this.mOutputHeight = height;
          if (this.mOutputWidth > 1920 && this.mOutputHeight > 1080) {
            this.mOutputWidth = 1920;
            this.mOutputHeight = 1080;
          }
          Log.m94d(
              Constants.TAG,
              "mOutputWidth :" + this.mOutputWidth + ", mOutputHeight :" + this.mOutputHeight);
          return true;
        }
      } catch (IOException e2) {
        e2.printStackTrace();
      } catch (NullPointerException e3) {
        e3.printStackTrace();
        if (0 != 0) {
          me.release();
        }
        if (error == 0) {}
      }
    } catch (Throwable th) {
      if (0 != 0) {
        me.release();
      }
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:

     if (r1 != null) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x0046, code lost:

     return r2;
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x0041, code lost:

     r1.release();
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x003f, code lost:

     if (r1 == null) goto L26;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean CheckVideoFormat(String filepath) {
    MediaExtractor me = null;
    MediaMetadataRetriever mr = null;
    boolean ret = true;
    try {
      try {
        me = CodecsHelper.createExtractor(filepath);
        mr = CodecsHelper.createMediaMetadataRetriever(filepath);
        int index = CodecsHelper.getAndSelectVideoTrackIndex(me);
        if (index == -1 || !CodecsHelper.isSupportedFormat(mr)) {
          Log.m94d(Constants.TAG, "Video Format is not supported");
          ret = false;
        }
        if (!getHDRMode(mr)) {
          ret = false;
        }
        if (me != null) {
          me.release();
        }
      } catch (IOException e) {
        e.printStackTrace();
        ret = false;
        if (me != null) {
          me.release();
        }
      }
    } catch (Throwable th) {
      if (me != null) {
        me.release();
      }
      if (mr != null) {
        mr.release();
      }
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:

     if (r1 != null) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x0046, code lost:

     return r2;
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x0041, code lost:

     r1.release();
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x003f, code lost:

     if (r1 == null) goto L26;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean CheckVideoFormat(Context context, Uri uri) {
    MediaExtractor me = null;
    MediaMetadataRetriever mr = null;
    boolean ret = true;
    try {
      try {
        me = CodecsHelper.createExtractor(context, uri);
        mr = CodecsHelper.createMediaMetadataRetriever(context, uri);
        int index = CodecsHelper.getAndSelectVideoTrackIndex(me);
        if (index == -1 || !CodecsHelper.isSupportedFormat(mr)) {
          Log.m94d(Constants.TAG, "Video Format is not supported");
          ret = false;
        }
        if (!getHDRMode(mr)) {
          ret = false;
        }
        if (me != null) {
          me.release();
        }
      } catch (IOException e) {
        e.printStackTrace();
        ret = false;
        if (me != null) {
          me.release();
        }
      }
    } catch (Throwable th) {
      if (me != null) {
        me.release();
      }
      if (mr != null) {
        mr.release();
      }
      throw th;
    }
  }

  private int getVideoSampleSize(MediaFormat format) {
    if (format.getString(MediaFormat.KEY_MIME).startsWith(BnRConstants.VIDEO_DIR_PATH)) {
      int width = format.getInteger("width");
      int height = format.getInteger("height");
      return width * height;
    }
    return 0;
  }

  private static final long unsignedIntToLong(byte[] b) {
    long l = 0 | (b[0] & 255);
    return (((((l << 8) | (b[1] & 255)) << 8) | (b[2] & 255)) << 8) | (b[3] & 255);
  }

  public static boolean isSupportedFormat(String filePath) {
    return CodecsHelper.isSupportedFormat(filePath);
  }

  public static boolean isSupportedFormat(String filePath, Context context, Uri uri) {
    if (mUseUri) {
      return CodecsHelper.isSupportedFormat(context, uri);
    }
    return CodecsHelper.isSupportedFormat(filePath);
  }

  public static boolean isSupportedFormat(Context context, Uri uri) {
    return CodecsHelper.isSupportedFormat(context, uri);
  }

  public int getVideoFramerate(String filePath, Context context, Uri uri) {
    long avgTime;
    int frameRate;
    int videoFPS;
    MediaExtractor videoExtractor = null;
    try {
      if (mUseUri) {
        videoExtractor = CodecsHelper.createExtractor(context, uri);
      } else {
        MediaExtractor videoExtractor2 = CodecsHelper.createExtractor(filePath);
        videoExtractor = videoExtractor2;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    long avgTime2 = 0;
    long previousTime = 0;
    int frameCount = 0;
    videoExtractor.getTrackCount();
    int trackVideo = CodecsHelper.getAndSelectVideoTrackIndex(videoExtractor);
    if (trackVideo == -1) {
      Log.m94d(Constants.TAG, "Valid video track absent");
      videoFPS = 30;
    } else {
      MediaFormat videoFormat = videoExtractor.getTrackFormat(trackVideo);
      int bufferSizeV = getVideoSampleSize(videoFormat);
      ByteBuffer dstBufV = ByteBuffer.allocate(bufferSizeV);
      MediaCodec.BufferInfo bufferInfoV = new MediaCodec.BufferInfo();
      int i = 0;
      while (true) {
        MediaFormat videoFormat2 = videoFormat;
        if (i > 5) {
          break;
        }
        bufferInfoV.size = videoExtractor.readSampleData(dstBufV, 0);
        long presentationTimeUs = videoExtractor.getSampleTime();
        videoExtractor.advance();
        if (i == 0) {
          previousTime = presentationTimeUs;
        } else {
          avgTime2 += presentationTimeUs - previousTime;
          previousTime = presentationTimeUs;
          frameCount++;
        }
        i++;
        videoFormat = videoFormat2;
      }
      if (((int) (1000 / ((avgTime2 / 1000) / frameCount))) > 0) {
        long j = avgTime2 / 1000;
        avgTime = avgTime2;
        long avgTime3 = frameCount;
        frameRate = (int) (1000 / (j / avgTime3));
      } else {
        avgTime = avgTime2;
        frameRate = 30;
      }
      videoFPS = frameRate;
    }
    if (videoExtractor != null) {
      videoExtractor.release();
    }
    return videoFPS;
  }

  public boolean updateCreationTime(String filepath, boolean mode) {
    Throwable th;
    String[] parentContainer;
    ConvertVideo convertVideo = this;
    boolean ret = false;
    Log.m94d(Constants.TAG, "updateCreationTime mode : " + mode + ", filepath : " + filepath);
    if (convertVideo.mAuthor == -1 || (!convertVideo.mUpdateCreationTime && mode)) {
      Log.m94d(Constants.TAG, "Do not update CreationTime");
      return false;
    }
    File file = new File(filepath);
    byte[] atomSizeBuf = new byte[4];
    byte[] atomNameBuf = new byte[4];
    byte[] temp = new byte[4];
    long fileSize = file.length();
    if (fileSize <= 0) {
      Log.m94d(Constants.TAG, "file size is same or less than 0");
      return false;
    }
    String[] parentContainer2 = {"mdia", "minf", "moov", "stbl", "trak"};
    RandomAccessFile fileObj = null;
    try {
      try {
        if (mode) {
          try {
            fileObj = new RandomAccessFile(file, "rw");
          } catch (Exception e) {
            e = e;
            e.printStackTrace();
            fileObj.close();
            return ret;
          } catch (Throwable th2) {
            th = th2;
            try {
              fileObj.close();
              throw th;
            } catch (IOException e1) {
              e1.printStackTrace();
              throw th;
            }
          }
        } else {
          try {
            fileObj = new RandomAccessFile(file, "r");
          } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            fileObj.close();
            return ret;
          } catch (Throwable th3) {
            th = th3;
            fileObj.close();
            throw th;
          }
        }
        long filePointer = 0;
        while (true) {
          if (filePointer >= fileSize) {
            break;
          }
          try {
            Log.m94d(Constants.TAG, "filePointer: " + filePointer);
            fileObj.seek(filePointer);
          } catch (IOException e12) {
            e12.printStackTrace();
          }
          fileObj.read(atomSizeBuf, 0, atomSizeBuf.length);
          long atomSize = unsignedIntToLong(atomSizeBuf);
          File file2 = file;
          byte[] atomSizeBuf2 = atomSizeBuf;
          try {
            Log.m94d(Constants.TAG, "Atom Size: " + atomSize);
            try {
              fileObj.read(atomNameBuf, 0, atomNameBuf.length);
              String atomName = new String(atomNameBuf);
              byte[] atomNameBuf2 = atomNameBuf;
              try {
                Log.m94d(Constants.TAG, "Atom Box: " + atomName);
                int tmpAtomPosition = Arrays.binarySearch(parentContainer2, atomName);
                if (tmpAtomPosition >= 0) {
                  parentContainer = parentContainer2;
                  try {
                    Log.m94d(
                        Constants.TAG,
                        "Found parent: " + atomName + " move to position: " + tmpAtomPosition);
                    filePointer += 8;
                  } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    fileObj.close();
                    return ret;
                  }
                } else {
                  parentContainer = parentContainer2;
                  if (atomName.equals("mvhd")) {
                    Log.m94d(Constants.TAG, "Found: mvhd");
                    ret = true;
                    if (mode) {
                      fileObj.read(temp, 0, temp.length);
                      byte[] bArr = creationTime;
                      fileObj.write(bArr, 0, bArr.length);
                    } else {
                      byte[] bArr2 = creationTime;
                      fileObj.read(bArr2, 0, bArr2.length);
                      byte[] bArr3 = creationTime;
                      fileObj.read(bArr3, 0, bArr3.length);
                      convertVideo.mUpdateCreationTime = true;
                    }
                  } else if (atomSize == 1) {
                    fileObj.seek(filePointer + 8);
                    byte[] atomLargeSizeBuf = new byte[8];
                    fileObj.read(atomLargeSizeBuf, 0, atomLargeSizeBuf.length);
                    BigInteger atomTmpLargeSize = new BigInteger(atomLargeSizeBuf);
                    long atomLargeSize = atomTmpLargeSize.longValue();
                    filePointer += atomLargeSize;
                    Log.m94d(Constants.TAG, "64bit: " + atomLargeSize);
                  } else {
                    if (atomSize == 0) {
                      Log.m94d(Constants.TAG, "filePointer does not go forward. Exit.");
                      ret = false;
                      break;
                    }
                    filePointer += atomSize;
                    Log.m94d(Constants.TAG, "move: " + filePointer + " atomsize " + atomSize);
                  }
                }
                convertVideo = this;
                atomSizeBuf = atomSizeBuf2;
                file = file2;
                atomNameBuf = atomNameBuf2;
                parentContainer2 = parentContainer;
              } catch (Exception e4) {
                e = e4;
              } catch (Throwable th4) {
                th = th4;
                fileObj.close();
                throw th;
              }
            } catch (Exception e5) {
              e = e5;
            } catch (Throwable th5) {
              th = th5;
            }
          } catch (Exception e6) {
            e = e6;
          } catch (Throwable th6) {
            th = th6;
          }
        }
        fileObj.close();
      } catch (Throwable th7) {
        th = th7;
      }
    } catch (IOException e13) {
      e13.printStackTrace();
    }
    return ret;
  }

  public boolean getHDRMode(MediaMetadataRetriever retriever) {
    try {
      String bitdepth = retriever.extractMetadata(1028);
      String auth = retriever.extractMetadata(1015);
      String mode = retriever.extractMetadata(1022);
      if (auth != null) {
        this.mAuthor = Integer.parseInt(auth);
        if (mode != null) {
          this.mRecordingMode = Integer.parseInt(mode);
        }
      }
      Log.m94d(
          Constants.TAG,
          "getHDRMode  mAuthor : " + this.mAuthor + ", mRecordingMode : " + this.mRecordingMode);
      if ("10".equals(bitdepth)) {
        this.mInputBitdepth = 10;
        int i = this.mAuthor;
        if ((i == 0 || i == 8) && this.mRecordingMode == 10) {
          this.mHDRType = 2;
        } else {
          this.mHDRType = 1;
        }
      } else {
        this.mHDRType = 0;
      }
      Log.m94d(Constants.TAG, "getHDRMode  mHDRType : " + this.mHDRType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this.mHDRType == 2;
  }

  private static byte[] getByteArrayFromByteBuffer(ByteBuffer byteBuffer) {
    byte[] bytesArray = new byte[byteBuffer.remaining()];
    byteBuffer.get(bytesArray, 0, bytesArray.length);
    return bytesArray;
  }

  private static String byteArrayToHex(byte[] a) {
    StringBuilder sb = new StringBuilder();
    for (byte b : a) {
      sb.append(String.format("%02x ", Integer.valueOf(b & 255)));
    }
    return sb.toString();
  }

  private String getVEEditFilePath(Context context, Uri mediaUri) {
    String lEditPath = null;
    if (mediaUri == null || mediaUri.toString().length() <= 0) {
      return null;
    }
    String lFileName = mediaUri.toString();
    Log.m94d(Constants.TAG, "lFileName :" + lFileName);
    if (lFileName.startsWith(SecContentProviderURI.CONTENT)) {
      if (lFileName.startsWith(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString())
          || lFileName.startsWith(MediaStore.Video.Media.INTERNAL_CONTENT_URI.toString())) {
        Cursor lCursor = getVideoFileInfoByUri(mediaUri, context);
        if (lCursor != null && lCursor.getCount() > 0) {
          lCursor.moveToFirst();
          lEditPath = lCursor.getString(lCursor.getColumnIndex("_data"));
        }
        if (lCursor != null) {
          lCursor.close();
          return lEditPath;
        }
        return lEditPath;
      }
      return mediaUri.getPath();
    }
    if (lFileName.startsWith("file://")) {
      return mediaUri.getPath();
    }
    return lFileName;
  }

  private static Cursor getVideoFileInfoByUri(Uri uri, Context context) {
    String[] cols = {"_data", "duration"};
    try {
      Cursor c = context.getContentResolver().query(uri, cols, null, null);
      return c;
    } catch (SQLiteException e) {
      return null;
    } catch (Exception e2) {
      return null;
    }
  }
}
