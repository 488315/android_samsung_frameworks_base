package com.android.server.hdmi;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import java.util.List;

/* loaded from: classes2.dex */
public interface AudioManagerWrapper {
  void adjustStreamVolume(int i, int i2, int i3);

  int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes);

  List getDevicesForAttributes(AudioAttributes audioAttributes);

  int getStreamMaxVolume(int i);

  int getStreamMinVolume(int i);

  int getStreamVolume(int i);

  boolean isStreamMute(int i);

  void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i);

  int setHdmiSystemAudioSupported(boolean z);

  void setStreamMute(int i, boolean z);

  void setStreamVolume(int i, int i2, int i3);

  void setWiredDeviceConnectionState(int i, int i2, String str, String str2);

  void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i);
}
