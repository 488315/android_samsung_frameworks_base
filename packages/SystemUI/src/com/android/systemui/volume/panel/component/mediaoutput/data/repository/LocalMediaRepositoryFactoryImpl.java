package com.android.systemui.volume.panel.component.mediaoutput.data.repository;

import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.systemui.media.controls.util.LocalMediaManagerFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LocalMediaRepositoryFactoryImpl implements LocalMediaRepositoryFactory {
    public final AudioManagerEventsReceiver eventsReceiver;
    public final LocalMediaManagerFactory localMediaManagerFactory;

    public LocalMediaRepositoryFactoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, LocalMediaManagerFactory localMediaManagerFactory) {
        this.eventsReceiver = audioManagerEventsReceiver;
        this.localMediaManagerFactory = localMediaManagerFactory;
    }
}
