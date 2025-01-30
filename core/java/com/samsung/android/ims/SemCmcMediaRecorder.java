package com.samsung.android.ims;

import android.media.tv.interactive.TvInteractiveAppService;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.ims.cmc.ISemCmcRecordingListener;
import com.samsung.android.ims.cmc.SemCmcRecordingInfo;

/* loaded from: classes5.dex */
public class SemCmcMediaRecorder {
  public static final int CMC_RECORDER_ERROR_UNKNOWN = 1;
  public static final int CMC_RECORDER_INFO_DURATION_IN_PROGRESS = 901;
  public static final int CMC_RECORDER_INFO_FILESIZE_IN_PROGRESS = 900;
  public static final int CMC_RECORDER_INFO_MAX_DURATION_REACHED = 800;
  public static final int CMC_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
  public static final int CMC_RECORDER_SUCCESS = 0;
  private static final int EVENT_PAUSE = 4;
  private static final int EVENT_PREPARE = 1;
  private static final int EVENT_RELEASE = 7;
  private static final int EVENT_RESET = 6;
  private static final int EVENT_RESUME = 5;
  private static final int EVENT_START = 2;
  private static final int EVENT_STOP = 3;
  private static final String LOG_TAG = SemCmcMediaRecorder.class.getSimpleName();
  private static final int STATE_DATASOURCE_CONFIGURED = 3;
  private static final int STATE_ERROR = 7;
  private static final int STATE_INITIAL = 1;
  private static final int STATE_INITIALIZED = 2;
  private static final int STATE_PREPARED = 4;
  private static final int STATE_RECORDING = 5;
  private static final int STATE_RELEASED = 6;
  private static final int STATE_UNKNOWN = 8;
  SemImsService mImsService;
  private OnErrorListener mOnErrorListener;
  private OnInfoListener mOnInfoListener;
  private int mPhoneId;
  private SemCmcRecordingInfo mSemCmcRecordingInfo = new SemCmcRecordingInfo();
  private int mState;

  public interface OnErrorListener {
    void onError(SemCmcMediaRecorder semCmcMediaRecorder, int i, int i2);
  }

  public interface OnInfoListener {
    void onInfo(SemCmcMediaRecorder semCmcMediaRecorder, int i, int i2);
  }

  private SemCmcMediaRecorder() {}

  SemCmcMediaRecorder(SemImsService imsService, int phoneId) {
    this.mImsService = imsService;
    this.mPhoneId = phoneId;
    try {
      imsService.registerSemCmcRecordingListener(
          new ISemCmcRecordingListener
              .Stub() { // from class: com.samsung.android.ims.SemCmcMediaRecorder.1
            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onInfo(int what, int extra) {
              Log.m94d(
                  SemCmcMediaRecorder.LOG_TAG,
                  "ISemCmcRecordingListener onInfo : " + what + " " + extra);
              if (SemCmcMediaRecorder.this.mOnInfoListener != null) {
                SemCmcMediaRecorder.this.mOnInfoListener.onInfo(
                    SemCmcMediaRecorder.this, what, extra);
              }
            }

            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onError(int what, int extra) {
              Log.m94d(
                  SemCmcMediaRecorder.LOG_TAG,
                  "ISemCmcRecordingListener onError : " + what + " " + extra);
              SemCmcMediaRecorder.this.mState = 7;
              if (SemCmcMediaRecorder.this.mOnErrorListener != null) {
                SemCmcMediaRecorder.this.mOnErrorListener.onError(
                    SemCmcMediaRecorder.this, what, extra);
              }
            }
          },
          this.mPhoneId);
      this.mState = 1;
    } catch (RemoteException e) {
      Log.m97e(LOG_TAG, e.getMessage(), e);
    }
  }

  public void prepare() throws IllegalStateException {
    Log.m94d(LOG_TAG, "prepare");
    if (this.mState != 3) {
      throw new IllegalStateException("Current stats is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 1, this.mPhoneId);
      this.mState = 4;
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void start() throws IllegalStateException {
    Log.m94d(LOG_TAG, "start");
    if (this.mState != 4) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 2, this.mPhoneId);
      this.mState = 5;
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void stop() throws IllegalStateException {
    Log.m94d(LOG_TAG, "stop");
    if (this.mState != 5) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 3, this.mPhoneId);
      this.mState = 1;
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void pause() throws IllegalStateException {
    Log.m94d(LOG_TAG, TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE);
    if (this.mState != 5) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 4, this.mPhoneId);
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void resume() throws IllegalStateException {
    Log.m94d(LOG_TAG, "resume");
    if (this.mState != 5) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 5, this.mPhoneId);
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void reset() throws IllegalStateException {
    Log.m94d(LOG_TAG, "reset");
    if (this.mState == 6) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 6, this.mPhoneId);
      this.mState = 1;
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void release() throws IllegalStateException {
    Log.m94d(LOG_TAG, "release");
    if (this.mState != 1) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    try {
      this.mImsService.sendSemCmcRecordingEvent(this.mSemCmcRecordingInfo, 7, this.mPhoneId);
      this.mState = 6;
    } catch (RemoteException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void setAudioSource(int audio_source) throws IllegalStateException {
    Log.m94d(LOG_TAG, "setAudioSource");
    int i = this.mState;
    if (i != 1 && i != 2) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    this.mSemCmcRecordingInfo.setAudioSource(audio_source);
    this.mState = 2;
  }

  public void setOutputFormat(int output_format) throws IllegalStateException {
    Log.m94d(LOG_TAG, "setOutputFormat");
    if (this.mState != 2) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    this.mSemCmcRecordingInfo.setOutputFormat(output_format);
    this.mState = 3;
  }

  public void setMaxFileSize(long max_filesize_bytes)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setMaxFileSize");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (max_filesize_bytes <= 0) {
      throw new IllegalArgumentException("Max file size is not positive");
    }
    this.mSemCmcRecordingInfo.setMaxFileSize(max_filesize_bytes);
  }

  public void setMaxDuration(int max_duration_ms)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setMaxDuration");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (max_duration_ms <= 0) {
      throw new IllegalArgumentException("Max duration is not positive");
    }
    this.mSemCmcRecordingInfo.setMaxDuration(max_duration_ms);
  }

  public void setOutputPath(String output_absolute_path)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setOutputPath");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (output_absolute_path == null || output_absolute_path.length() == 0) {
      throw new IllegalArgumentException("Output absolute path is empty");
    }
    this.mSemCmcRecordingInfo.setOutputPath(output_absolute_path);
  }

  public void setAudioEncodingBitRate(int bitRate)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setAudioEncodingBitRate");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (bitRate <= 0) {
      throw new IllegalArgumentException("Audio encoding bit rate is not positive");
    }
    this.mSemCmcRecordingInfo.setAudioEncodingBitRate(bitRate);
  }

  public void setAudioChannels(int numChannels)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setAudioChannels");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (numChannels <= 0) {
      throw new IllegalArgumentException("Number of channels is not positive");
    }
    this.mSemCmcRecordingInfo.setAudioChannels(numChannels);
  }

  public void setAudioSamplingRate(int samplingRate)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setAudioSamplingRate");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (samplingRate <= 0) {
      throw new IllegalArgumentException("Audio sampling rate is not positive");
    }
    this.mSemCmcRecordingInfo.setAudioSamplingRate(samplingRate);
  }

  public void setAudioEncoder(int audio_encoder) throws IllegalStateException {
    Log.m94d(LOG_TAG, "setAudioEncoder");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    this.mSemCmcRecordingInfo.setAudioEncoder(audio_encoder);
  }

  public void setDurationInterval(int duration_inverval_ms)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setDurationInterval");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (duration_inverval_ms <= 0) {
      throw new IllegalArgumentException("Duration interval is not positive");
    }
    this.mSemCmcRecordingInfo.setDurationInterval(duration_inverval_ms);
  }

  public void setFileSizeInterval(long file_size_interval)
      throws IllegalStateException, IllegalArgumentException {
    Log.m94d(LOG_TAG, "setFileSizeInterval");
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    if (file_size_interval <= 0) {
      throw new IllegalArgumentException("File size interval is not positive");
    }
    this.mSemCmcRecordingInfo.setFileSizeInterval(file_size_interval);
  }

  public void setAuthor(int author) throws IllegalStateException {
    if (this.mState != 3) {
      throw new IllegalStateException("Current state is " + this.mState);
    }
    this.mSemCmcRecordingInfo.setAuthor("param-meta-author=" + author);
  }

  public void setOnInfoListener(OnInfoListener listener) {
    Log.m94d(LOG_TAG, "setOnInfoListener");
    this.mOnInfoListener = listener;
  }

  public void setOnErrorListener(OnErrorListener listener) {
    Log.m94d(LOG_TAG, "setOnErrorListener");
    this.mOnErrorListener = listener;
  }

  SemCmcRecordingInfo getSemCmcRecordingInfo() {
    return this.mSemCmcRecordingInfo;
  }
}
