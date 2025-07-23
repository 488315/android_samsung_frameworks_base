package com.android.systemui.volume.panel.component.mediaoutput.data.repository;

import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.systemui.media.controls.util.LocalMediaManagerFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LocalMediaRepositoryFactoryImpl implements LocalMediaRepositoryFactory {
    public final AudioManagerEventsReceiver eventsReceiver;
    public final LocalMediaManagerFactory localMediaManagerFactory;

    public LocalMediaRepositoryFactoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, LocalMediaManagerFactory localMediaManagerFactory) {
        this.eventsReceiver = audioManagerEventsReceiver;
        this.localMediaManagerFactory = localMediaManagerFactory;
    }
}
