package com.android.server.display;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class VolumeController {
  public WifiDisplayController mDisplayController;
  public DlnaController mDlnaController;
  public final Handler mHandler;
  public Map mKeyMap = new HashMap() { // from class: com.android.server.display.VolumeController.1
        {
          put(-1, 10);
          put(1, 11);
          put(2, 12);
          put(3, 13);
        }
      };
  public int mMaxVolume;
  public int mMinVolume;
  public boolean mMuted;
  public int mVolume;

  public String keyEventToString(int i) {
    return i == -1
        ? "VOLUME_KEY_DOWN"
        : i == 1 ? "VOLUME_KEY_UP" : i == 2 ? "VOLUME_MUTE" : i == 3 ? "VOLUME_UNMUTE" : "";
  }

  public VolumeController(
      Handler handler, WifiDisplayController wifiDisplayController, DlnaController dlnaController) {
    this.mHandler = handler;
    this.mDisplayController = wifiDisplayController;
    this.mDlnaController = dlnaController;
  }

  public void setVolume(int i) {
    Log.d("VolumeController", "setVolume : volume = " + this.mVolume);
    this.mVolume = i;
    this.mMuted = false;
    sendVolumeChangedEvent();
  }

  public void setVolumeKeyEvent(int i) {
    Log.d("VolumeController", "sendDeviceVolumeKeyEvent : " + keyEventToString(i));
    if (i != 0 && this.mDlnaController.isConnected()) {
      sendVolumeKeyEvent(((Integer) this.mKeyMap.getOrDefault(Integer.valueOf(i), 0)).intValue());
      return;
    }
    if (this.mDisplayController.isConnected()) {
      if (i == -1) {
        this.mDisplayController.setParam("vkev", -1);
        return;
      }
      if (i == 1) {
        this.mDisplayController.setParam("vkev", 1);
      } else if (i == 2) {
        this.mDisplayController.setParam("mkev", Boolean.TRUE);
      } else if (i == 3) {
        this.mDisplayController.setParam("mkev", Boolean.FALSE);
      }
    }
  }

  public int getMaxVolume() {
    if (this.mDlnaController.isConnected()) {
      return 100;
    }
    return this.mMaxVolume;
  }

  public int getMinVolume() {
    if (this.mDlnaController.isConnected()) {
      return 0;
    }
    return this.mMinVolume;
  }

  public boolean isVolumeMuted() {
    return this.mMuted;
  }

  public void setVolumeMuted(boolean z) {
    Log.d(
        "VolumeController",
        "setVolumeMuted :  muted = " + this.mMuted + ", volume = " + this.mVolume);
    this.mMuted = z;
    sendVolumeChangedEvent();
  }

  public void notifyDisplayVolumeEvnet(Bundle bundle) {
    this.mMinVolume = bundle.getInt("minVol");
    this.mMaxVolume = bundle.getInt("maxVol");
    this.mVolume = bundle.getInt("curVol");
    this.mMuted = bundle.getBoolean("isMute", false);
    Log.d(
        "VolumeController",
        "notifyDisplayVolumeEvnet: max="
            + this.mMaxVolume
            + ", min="
            + this.mMinVolume
            + ", vol="
            + this.mVolume
            + ", muted="
            + this.mMuted);
  }

  public final void sendVolumeKeyEvent(final int i) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.VolumeController.2
          @Override // java.lang.Runnable
          public void run() {
            VolumeController.this.mHandler.sendMessage(
                VolumeController.this.mHandler.obtainMessage(23, i, 0));
          }
        });
  }

  public final void sendVolumeChangedEvent() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.VolumeController.3
          @Override // java.lang.Runnable
          public void run() {
            Message obtainMessage = VolumeController.this.mHandler.obtainMessage(22, 9, 0);
            Bundle bundle = new Bundle();
            bundle.putInt("minVol", 0);
            bundle.putInt("maxVol", 100);
            bundle.putInt("curVol", VolumeController.this.mVolume);
            bundle.putBoolean("isMute", VolumeController.this.mMuted);
            obtainMessage.setData(bundle);
            VolumeController.this.mHandler.sendMessage(obtainMessage);
          }
        });
  }
}
