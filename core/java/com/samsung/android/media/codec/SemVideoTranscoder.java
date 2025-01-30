package com.samsung.android.media.codec;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import com.samsung.android.transcode.core.Encode;
import com.samsung.android.transcode.core.EncodeVideo;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SemVideoTranscoder {
  private EncodeVideo mEncodeVideo = new EncodeVideo();
  private ProgressEventListener mProgressEventListener;
  private ProgressListener mProgressListener;
  private IVideoTranscodingServiceCallback mVideoTranscodingServiceCallback;

  public interface ProgressEventListener {
    void onCompleted();

    void onStarted();
  }

  public interface ProgressListener {
    void onCompleted();

    void onProgressChanged(int i);

    void onStarted();
  }

  public static final class CodecType {
    public static final int AUDIO_CODEC_AAC = 2;
    public static final int AUDIO_CODEC_AMR = 1;
    public static final int VIDEO_CODEC_H263 = 3;
    public static final int VIDEO_CODEC_H264 = 4;
    public static final int VIDEO_CODEC_H265 = 5;

    private CodecType() {}
  }

  public static final class ConfigType {
    public static final int audioCodec = 2;
    public static final int audioMute = 7;
    public static final int bitDepth = 4;
    public static final int bitRate = 5;
    public static final int frameRate = 6;
    public static final int maxSize = 3;
    public static final int videoCodec = 1;

    private ConfigType() {}
  }

  public void encode() throws IOException {
    this.mEncodeVideo.encode();
  }

  public void rewrite() throws IOException {
    this.mEncodeVideo.rewrite();
  }

  public void stop() {
    this.mEncodeVideo.stop();
  }

  public void initialize(
      String outputFilePath, int outputWidth, int outputHeight, String inputFilePath)
      throws IOException {
    this.mEncodeVideo.initialize(outputFilePath, outputWidth, outputHeight, inputFilePath);
  }

  public void initialize(
      String outputFilePath, int outputWidth, int outputHeight, Context context, Uri inputUri)
      throws IOException {
    this.mEncodeVideo.initialize(outputFilePath, outputWidth, outputHeight, context, inputUri);
  }

  public void setTrimTime(long startMs, long endMs) {
    this.mEncodeVideo.setTrimTime(startMs, endMs);
  }

  public void setEncodingCodecs(int videoCodecType, int audioCodecType) {
    this.mEncodeVideo.setEncodingCodecs(videoCodecType, audioCodecType);
  }

  public void setMaxOutputSize(int kilobytes) {
    this.mEncodeVideo.setMaxOutputSize(kilobytes);
  }

  public int getOutputFileSize() {
    return this.mEncodeVideo.getOutputFileSize();
  }

  public void setProgressEventListener(ProgressEventListener listner) {
    this.mProgressEventListener = listner;
    this.mEncodeVideo.setProgressUpdateListener(
        new Encode
            .EncodeEventListener() { // from class:
                                     // com.samsung.android.media.codec.SemVideoTranscoder.1
          @Override // com.samsung.android.transcode.core.Encode.EncodeEventListener
          public void onStarted() {
            SemVideoTranscoder.this.mProgressEventListener.onStarted();
          }

          @Override // com.samsung.android.transcode.core.Encode.EncodeEventListener
          public void onCompleted() {
            SemVideoTranscoder.this.mProgressEventListener.onCompleted();
          }
        });
  }

  public void setProgressListener(ProgressListener listener) {
    this.mProgressListener = listener;
    this.mEncodeVideo.setEncodeProgressListener(
        new Encode
            .EncodeProgressListener() { // from class:
                                        // com.samsung.android.media.codec.SemVideoTranscoder.2
          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onStarted() {
            SemVideoTranscoder.this.mProgressListener.onStarted();
          }

          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onProgressChanged(int progress) {
            SemVideoTranscoder.this.mProgressListener.onProgressChanged(progress);
          }

          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onCompleted() {
            SemVideoTranscoder.this.mProgressListener.onCompleted();
          }
        });
  }

  public void setVideoTranscodingServiceCallback(
      SemVideoTranscodingService.ProgressCallback callback) {
    this.mVideoTranscodingServiceCallback = callback;
    this.mEncodeVideo.setEncodeProgressListener(
        new Encode
            .EncodeProgressListener() { // from class:
                                        // com.samsung.android.media.codec.SemVideoTranscoder.3
          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onStarted() {
            try {
              SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onStarted();
            } catch (RemoteException e) {
              e.printStackTrace();
            }
          }

          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onProgressChanged(int progress) {
            try {
              SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onProgressChanged(progress);
            } catch (RemoteException e) {
              e.printStackTrace();
            }
          }

          @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
          public void onCompleted() {
            try {
              SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onCompleted();
            } catch (RemoteException e) {
              e.printStackTrace();
            }
          }
        });
  }

  public static int getMaxEncodingDuration(
      int maxSizeKB, int width, int height, int audioCodecType) {
    return EncodeVideo.getMaxEncodingDuration(maxSizeKB, width, height, audioCodecType);
  }

  public boolean setOutputBitdepth(int bitdepth) {
    return this.mEncodeVideo.setOutputBitdepth(bitdepth);
  }

  public void setOutputConfig(int type, int value) {
    this.mEncodeVideo.setOutputConfig(type, value);
  }
}
