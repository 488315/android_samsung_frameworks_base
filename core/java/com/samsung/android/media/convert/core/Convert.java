package com.samsung.android.media.convert.core;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import android.media.audiofx.SemDolbyAudioEffect;
import android.util.Log;
import com.samsung.android.media.convert.util.Constants;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class Convert {
  private static final long ONE_BILLION = 1000000000;
  protected static final int ORIENTATION_0 = 0;
  protected static final int ORIENTATION_180 = 180;
  protected static final int ORIENTATION_270 = 270;
  protected static final int ORIENTATION_90 = 90;
  private static final String VERSION = "2.02";
  protected ConvertEventListener mConvertEventListener;
  protected MediaCodec mInputAudioDecoder;
  protected MediaCodec mInputVideoDecoder;
  protected MediaMuxer mMuxer;
  protected boolean mMuxerStarted;
  protected MediaCodec mOutputAudioEncoder;
  protected String mOutputFilePath;
  protected int mOutputHeight;
  protected int mOutputVideoBitRate;
  protected MediaCodec mOutputVideoEncoder;
  protected int mOutputWidth;
  protected int mOutputFormat = 0;
  protected String mOutputVideoMimeType = "video/avc";
  protected int mOutputVideoFrameRate = 30;
  protected int mOutputVideoIFrameInterval = 1;
  protected int mOutputVideoProfile = -1;
  protected int mOutputBitdepth = 8;
  protected String mOutputAudioMimeType = "audio/mp4a-latm";
  protected int mOutputAudioChannelCount = 2;
  protected int mOutputAudioBitRate = 128000;
  protected int mOutputAudioAACProfile = 2;
  protected int mOutputAudioSampleRateHZ = SemDolbyAudioEffect.SAMPLE_RATE_44100;
  protected int mVideoTrackIndex = -1;
  protected int mAudioTrackIndex = -1;
  protected volatile boolean mUserStop = false;
  protected volatile boolean mError = false;
  protected volatile boolean mConverting = false;
  protected float mSizeFraction = 1.0f;
  protected long mOutputMaxSizeKB = -1;
  protected int mSourceFrameRate = 30;
  protected Thread mThread = null;

  public static final class BitRate {
    public static final int AUDIO_AAC_BITRATE = 128;
    public static final int VIDEO_D1_BITRATE = 3449;
    public static final int VIDEO_FHD60_BITRATE = 28000;
    public static final int VIDEO_FHD_BITRATE = 17000;
    public static final int VIDEO_HD_BITRATE = 12000;
    public static final int VIDEO_QHD_BITRATE = 20000;
    public static final int VIDEO_UHD60_BITRATE = 72000;
    public static final int VIDEO_UHD_BITRATE = 48000;
    public static final int VIDEO_VGA_BITRATE = 3078;
  }

  public static final class CodecType {
    public static final int AUDIO_CODEC_AAC = 2;
    public static final int AUDIO_CODEC_AMR = 1;
    public static final int VIDEO_CODEC_H263 = 3;
    public static final int VIDEO_CODEC_H264 = 4;
    public static final int VIDEO_CODEC_H265 = 5;
  }

  public static final class CodecsMime {
    public static final String AUDIO_CODEC_AAC = "audio/mp4a-latm";
    public static final String AUDIO_CODEC_AMR = "audio/3gpp";
    public static final String VIDEO_CODEC_H263 = "video/3gpp";
    public static final String VIDEO_CODEC_H264 = "video/avc";
    public static final String VIDEO_CODEC_H265 = "video/hevc";
  }

  public interface ConvertEventListener {
    void onCancelled();

    void onCompleted();

    void onFailed();

    void onStarted();
  }

  public static final class HDRType {
    public static final int HDR_10 = 1;
    public static final int HDR_10_PLUS = 2;
    public static final int NONE_HDR = 0;
  }

  protected abstract boolean prepare();

  protected abstract void release();

  protected abstract void startConverting() throws IOException;

  public abstract boolean stop();

  public boolean convert() {
    printVersionInfo();
    Log.m94d(Constants.TAG, "starting to convert");
    if (!prepare()) {
      Log.m94d(Constants.TAG, "convert preparation done.");
      return false;
    }
    Thread thread = new Thread() { // from class: com.samsung.android.media.convert.core.Convert.1
          @Override // java.lang.Thread, java.lang.Runnable
          public void run() {
            try {
              try {
                Convert.this.mMuxer =
                    new MediaMuxer(Convert.this.mOutputFilePath, Convert.this.mOutputFormat);
                Convert.this.mMuxerStarted = false;
                Convert.this.mVideoTrackIndex = -1;
                Convert.this.mAudioTrackIndex = -1;
                if (Convert.this.mConvertEventListener != null) {
                  Convert.this.mConvertEventListener.onStarted();
                }
                Convert.this.startConverting();
                Log.m94d(Constants.TAG, "encoding finished.");
              } catch (Exception e) {
                e.printStackTrace();
                Convert.this.mError = true;
              }
              Convert.this.release();
              if (Convert.this.mConvertEventListener != null) {
                if (Convert.this.mError) {
                  Log.m94d(Constants.TAG, "Stopped by error");
                  Convert.this.mConvertEventListener.onFailed();
                } else if (!Convert.this.mUserStop) {
                  Log.m94d(Constants.TAG, "calling onCompleted");
                  Convert.this.mConvertEventListener.onCompleted();
                } else {
                  Log.m94d(Constants.TAG, "user stopped. Not calling onCompleted");
                  Convert.this.mConvertEventListener.onCancelled();
                }
                Convert.this.mConvertEventListener = null;
              }
            } catch (Throwable th) {
              Convert.this.release();
              throw th;
            }
          }
        };
    this.mThread = thread;
    thread.start();
    return true;
  }

  public void setProgressUpdateListener(ConvertEventListener updateListner) {
    this.mConvertEventListener = updateListner;
  }

  protected long computePresentationTimeNsec(int frameIndex) {
    return (frameIndex * 1000000000) / this.mOutputVideoFrameRate;
  }

  public static final class EncodeResolution {

    /* renamed from: D1 */
    public static final int f3028D1 = 4;
    public static final int FULL_HD = 6;

    /* renamed from: HD */
    public static final int f3029HD = 5;
    public static final int QCIF = 1;
    public static final int QHD = 7;
    public static final int QVGA = 2;
    public static final int UHD = 8;
    public static final int VGA = 3;

    public static boolean isSupportedResolution(int resolution) {
      if (resolution >= 1 && resolution <= 8) {
        return true;
      }
      return false;
    }
  }

  private void printVersionInfo() {
    Log.m96e(Constants.TAG, "Transcode Framework v.2.02");
  }

  public static final class ContentType {
    public static final String VIDEO_3G2 = "video/3gpp2";
    public static final String VIDEO_3GP = "video/3gp";
    public static final String VIDEO_3GPP = "video/3gpp";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MP4V_ES = "video/mp4v-es";
    public static final ArrayList<String> sSupportedVideoTypes;

    static {
      ArrayList<String> arrayList = new ArrayList<>();
      sSupportedVideoTypes = arrayList;
      arrayList.add("video/3gpp");
      arrayList.add("video/3gpp2");
      arrayList.add("video/mp4");
      arrayList.add("video/mp4v-es");
      arrayList.add("video/3gp");
    }
  }
}
