package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioDeviceVolumeManager;

public final class DefaultAudioDeviceVolumeManagerWrapper
        implements AudioDeviceVolumeManagerWrapper {
    public final AudioDeviceVolumeManager mAudioDeviceVolumeManager;

    public DefaultAudioDeviceVolumeManagerWrapper(Context context) {
        this.mAudioDeviceVolumeManager = new AudioDeviceVolumeManager(context);
    }
}
