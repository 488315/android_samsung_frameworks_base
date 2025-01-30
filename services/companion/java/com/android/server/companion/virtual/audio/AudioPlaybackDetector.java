package com.android.server.companion.virtual.audio;

import android.content.Context;
import android.media.AudioManager;
import java.util.List;

/* loaded from: classes.dex */
public final class AudioPlaybackDetector extends AudioManager.AudioPlaybackCallback {
  public final AudioManager mAudioManager;
  public AudioPlaybackCallback mAudioPlaybackCallback;

  public interface AudioPlaybackCallback {
    void onPlaybackConfigChanged(List list);
  }

  public AudioPlaybackDetector(Context context) {
    this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
  }

  public void register(AudioPlaybackCallback audioPlaybackCallback) {
    this.mAudioPlaybackCallback = audioPlaybackCallback;
    this.mAudioManager.registerAudioPlaybackCallback(this, null);
  }

  public void unregister() {
    if (this.mAudioPlaybackCallback != null) {
      this.mAudioPlaybackCallback = null;
      this.mAudioManager.unregisterAudioPlaybackCallback(this);
    }
  }

  @Override // android.media.AudioManager.AudioPlaybackCallback
  public void onPlaybackConfigChanged(List list) {
    super.onPlaybackConfigChanged(list);
    AudioPlaybackCallback audioPlaybackCallback = this.mAudioPlaybackCallback;
    if (audioPlaybackCallback != null) {
      audioPlaybackCallback.onPlaybackConfigChanged(list);
    }
  }
}
