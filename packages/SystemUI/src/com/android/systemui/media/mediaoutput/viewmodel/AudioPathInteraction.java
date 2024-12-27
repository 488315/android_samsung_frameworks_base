package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
