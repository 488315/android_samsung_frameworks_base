package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.os.Handler;

public final class MediaControllerInteractorImpl implements MediaControllerInteractor {
    public final Handler backgroundHandler;

    public MediaControllerInteractorImpl(Handler handler) {
        this.backgroundHandler = handler;
    }
}
