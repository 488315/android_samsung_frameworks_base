package com.android.server.display;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public final class VolumeController {
    public final WifiDisplayController mDisplayController;
    public final DlnaController mDlnaController;
    public final Handler mHandler;
    public final Map mKeyMap =
            new HashMap() { // from class: com.android.server.display.VolumeController.1
                {
                    put(-1, 12);
                    put(1, 13);
                    put(2, 14);
                    put(3, 15);
                }
            };
    public int mMaxVolume;
    public int mMinVolume;
    public boolean mMuted;
    public int mVolume;

    /* renamed from: com.android.server.display.VolumeController$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {}

        @Override // java.lang.Runnable
        public final void run() {
            Message obtainMessage = VolumeController.this.mHandler.obtainMessage(22, 11, 0);
            Bundle bundle = new Bundle();
            bundle.putInt("minVol", 0);
            bundle.putInt("maxVol", 100);
            bundle.putInt("curVol", VolumeController.this.mVolume);
            bundle.putBoolean("isMute", VolumeController.this.mMuted);
            obtainMessage.setData(bundle);
            VolumeController.this.mHandler.sendMessage(obtainMessage);
        }
    }

    public VolumeController(
            Handler handler,
            WifiDisplayController wifiDisplayController,
            DlnaController dlnaController) {
        this.mHandler = handler;
        this.mDisplayController = wifiDisplayController;
        this.mDlnaController = dlnaController;
    }

    public final void setVolumeKeyEvent(int i) {
        Log.d(
                "VolumeController",
                "sendDeviceVolumeKeyEvent : "
                        .concat(
                                i == -1
                                        ? "VOLUME_KEY_DOWN"
                                        : i == 1
                                                ? "VOLUME_KEY_UP"
                                                : i == 2
                                                        ? "VOLUME_MUTE"
                                                        : i == 3 ? "VOLUME_UNMUTE" : ""));
        if (i != 0 && this.mDlnaController.mDevice.isConnected()) {
            final int intValue =
                    ((Integer) ((HashMap) this.mKeyMap).getOrDefault(Integer.valueOf(i), 0))
                            .intValue();
            this.mHandler.post(
                    new Runnable() { // from class: com.android.server.display.VolumeController.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            VolumeController.this.mHandler.sendMessage(
                                    VolumeController.this.mHandler.obtainMessage(23, intValue, 0));
                        }
                    });
            return;
        }
        WifiDisplayController wifiDisplayController = this.mDisplayController;
        if (wifiDisplayController.mAdvertisedDisplay != null) {
            if (i == -1) {
                wifiDisplayController.setParam("vkev", -1);
                return;
            }
            if (i == 1) {
                wifiDisplayController.setParam("vkev", 1);
            } else if (i == 2) {
                wifiDisplayController.setParam("mkev", Boolean.TRUE);
            } else if (i == 3) {
                wifiDisplayController.setParam("mkev", Boolean.FALSE);
            }
        }
    }
}
