package com.android.server.companion.virtual.audio;

import android.content.Context;
import android.media.AudioManager;
import java.util.List;

/* loaded from: classes.dex */
public final class AudioRecordingDetector extends AudioManager.AudioRecordingCallback {
  public final AudioManager mAudioManager;
  public AudioRecordingCallback mAudioRecordingCallback;

  public interface AudioRecordingCallback {
    void onRecordingConfigChanged(List list);
  }

  public AudioRecordingDetector(Context context) {
    this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
  }

  public void register(AudioRecordingCallback audioRecordingCallback) {
    this.mAudioRecordingCallback = audioRecordingCallback;
    this.mAudioManager.registerAudioRecordingCallback(this, null);
  }

  public void unregister() {
    if (this.mAudioRecordingCallback != null) {
      this.mAudioRecordingCallback = null;
      this.mAudioManager.unregisterAudioRecordingCallback(this);
    }
  }

  @Override // android.media.AudioManager.AudioRecordingCallback
  public void onRecordingConfigChanged(List list) {
    super.onRecordingConfigChanged(list);
    AudioRecordingCallback audioRecordingCallback = this.mAudioRecordingCallback;
    if (audioRecordingCallback != null) {
      audioRecordingCallback.onRecordingConfigChanged(list);
    }
  }
}
