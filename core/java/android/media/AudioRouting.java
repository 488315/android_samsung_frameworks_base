package android.media;

import android.p009os.Handler;

/* loaded from: classes2.dex */
public interface AudioRouting {

  public interface OnRoutingChangedListener {
    void onRoutingChanged(AudioRouting audioRouting);
  }

  void addOnRoutingChangedListener(
      OnRoutingChangedListener onRoutingChangedListener, Handler handler);

  AudioDeviceInfo getPreferredDevice();

  AudioDeviceInfo getRoutedDevice();

  void removeOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener);

  boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo);
}
