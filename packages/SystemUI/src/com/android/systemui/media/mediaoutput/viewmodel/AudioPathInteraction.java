package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;

public interface AudioPathInteraction {
    void adjustVolume(AudioDevice audioDevice, int i);

    MutableLiveData getAudioDevices();

    default boolean isBroadcasting() {
        return false;
    }

    void transfer(AudioDevice audioDevice);

    default void cancel(AudioDevice audioDevice) {
    }

    default void deselect(AudioDevice audioDevice) {
    }

    default void select(AudioDevice audioDevice) {
    }
}
