package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import java.util.List;

/* loaded from: classes2.dex */
public class DefaultAudioManagerWrapper implements AudioManagerWrapper {
  public final AudioManager mAudioManager;

  public DefaultAudioManagerWrapper(Context context) {
    this.mAudioManager = (AudioManager) context.getSystemService("audio");
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void adjustStreamVolume(int i, int i2, int i3) {
    this.mAudioManager.adjustStreamVolume(i, i2, i3);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void setStreamVolume(int i, int i2, int i3) {
    this.mAudioManager.setStreamVolume(i, i2, i3);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public int getStreamVolume(int i) {
    return this.mAudioManager.getStreamVolume(i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public int getStreamMinVolume(int i) {
    return this.mAudioManager.getStreamMinVolume(i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public int getStreamMaxVolume(int i) {
    return this.mAudioManager.getStreamMaxVolume(i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public boolean isStreamMute(int i) {
    return this.mAudioManager.isStreamMute(i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void setStreamMute(int i, boolean z) {
    this.mAudioManager.setStreamMute(i, z);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public int setHdmiSystemAudioSupported(boolean z) {
    return this.mAudioManager.setHdmiSystemAudioSupported(z);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i) {
    this.mAudioManager.setWiredDeviceConnectionState(audioDeviceAttributes, i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void setWiredDeviceConnectionState(int i, int i2, String str, String str2) {
    this.mAudioManager.setWiredDeviceConnectionState(i, i2, str, str2);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
    return this.mAudioManager.getDeviceVolumeBehavior(audioDeviceAttributes);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i) {
    this.mAudioManager.setDeviceVolumeBehavior(audioDeviceAttributes, i);
  }

  @Override // com.android.server.hdmi.AudioManagerWrapper
  public List getDevicesForAttributes(AudioAttributes audioAttributes) {
    return this.mAudioManager.getDevicesForAttributes(audioAttributes);
  }
}
