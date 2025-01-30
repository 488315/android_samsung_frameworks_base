package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceVolumeManager;
import android.media.VolumeInfo;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class DefaultAudioDeviceVolumeManagerWrapper implements AudioDeviceVolumeManagerWrapper {
  public final AudioDeviceVolumeManager mAudioDeviceVolumeManager;

  public DefaultAudioDeviceVolumeManagerWrapper(Context context) {
    this.mAudioDeviceVolumeManager = new AudioDeviceVolumeManager(context);
  }

  @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
  public void addOnDeviceVolumeBehaviorChangedListener(
      Executor executor,
      AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener
          onDeviceVolumeBehaviorChangedListener) {
    this.mAudioDeviceVolumeManager.addOnDeviceVolumeBehaviorChangedListener(
        executor, onDeviceVolumeBehaviorChangedListener);
  }

  @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
  public void setDeviceAbsoluteVolumeBehavior(
      AudioDeviceAttributes audioDeviceAttributes,
      VolumeInfo volumeInfo,
      Executor executor,
      AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener
          onAudioDeviceVolumeChangedListener,
      boolean z) {
    this.mAudioDeviceVolumeManager.setDeviceAbsoluteVolumeBehavior(
        audioDeviceAttributes, volumeInfo, executor, onAudioDeviceVolumeChangedListener, z);
  }

  @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
  public void setDeviceAbsoluteVolumeAdjustOnlyBehavior(
      AudioDeviceAttributes audioDeviceAttributes,
      VolumeInfo volumeInfo,
      Executor executor,
      AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener
          onAudioDeviceVolumeChangedListener,
      boolean z) {
    this.mAudioDeviceVolumeManager.setDeviceAbsoluteVolumeAdjustOnlyBehavior(
        audioDeviceAttributes, volumeInfo, executor, onAudioDeviceVolumeChangedListener, z);
  }
}
