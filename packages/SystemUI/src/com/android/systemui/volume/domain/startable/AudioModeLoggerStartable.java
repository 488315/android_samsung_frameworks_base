package com.android.systemui.volume.domain.startable;

import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
