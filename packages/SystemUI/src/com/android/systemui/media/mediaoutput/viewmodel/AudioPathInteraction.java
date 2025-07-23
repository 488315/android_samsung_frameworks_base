package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Intent;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface AudioPathInteraction {
    void adjustVolume(AudioDevice audioDevice, int i);

    ReadonlyStateFlow getAudioDevices();

    default boolean isBroadcasting() {
        return false;
    }

    void transfer(AudioDevice audioDevice);

    default void cancel(AudioDevice audioDevice) {
    }

    default void deselect(AudioDevice audioDevice) {
    }

    default void goToApp(Intent intent) {
    }

    default void select(AudioDevice audioDevice) {
    }
}
