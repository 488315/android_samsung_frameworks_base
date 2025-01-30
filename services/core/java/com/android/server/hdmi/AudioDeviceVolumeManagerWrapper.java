package com.android.server.hdmi;

import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceVolumeManager;
import android.media.VolumeInfo;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public interface AudioDeviceVolumeManagerWrapper {
    void addOnDeviceVolumeBehaviorChangedListener(Executor executor, AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener);

    void setDeviceAbsoluteVolumeAdjustOnlyBehavior(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, Executor executor, AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z);

    void setDeviceAbsoluteVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, Executor executor, AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z);
}
