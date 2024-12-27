package com.android.systemui.volume.panel.component.mediaoutput.data.repository;

import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.systemui.media.controls.util.LocalMediaManagerFactory;

public final class LocalMediaRepositoryFactoryImpl implements LocalMediaRepositoryFactory {
    public final AudioManagerEventsReceiver eventsReceiver;
    public final LocalMediaManagerFactory localMediaManagerFactory;

    public LocalMediaRepositoryFactoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, LocalMediaManagerFactory localMediaManagerFactory) {
        this.eventsReceiver = audioManagerEventsReceiver;
        this.localMediaManagerFactory = localMediaManagerFactory;
    }
}
