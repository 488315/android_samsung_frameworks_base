package com.android.systemui.volume.domain.startable;

import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class AudioModeLoggerStartable {
    public final AudioModeInteractor audioModeInteractor;
    public final CoroutineScope scope;
    public final UiEventLogger uiEventLogger;

    public AudioModeLoggerStartable(CoroutineScope coroutineScope, UiEventLogger uiEventLogger, AudioModeInteractor audioModeInteractor) {
        this.scope = coroutineScope;
        this.uiEventLogger = uiEventLogger;
        this.audioModeInteractor = audioModeInteractor;
    }

    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new AudioModeLoggerStartable$start$1(this, null), 3);
    }
}
