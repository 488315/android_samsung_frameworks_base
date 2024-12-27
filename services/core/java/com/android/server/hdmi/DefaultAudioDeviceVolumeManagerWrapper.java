package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioDeviceVolumeManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DefaultAudioDeviceVolumeManagerWrapper implements AudioDeviceVolumeManagerWrapper {
    public final AudioDeviceVolumeManager mAudioDeviceVolumeManager;

    public DefaultAudioDeviceVolumeManagerWrapper(Context context) {
        this.mAudioDeviceVolumeManager = new AudioDeviceVolumeManager(context);
    }
}
