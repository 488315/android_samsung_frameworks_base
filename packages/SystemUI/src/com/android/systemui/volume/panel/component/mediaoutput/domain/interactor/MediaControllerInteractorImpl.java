package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.os.Handler;
import kotlin.coroutines.CoroutineContext;

/* loaded from: classes3.dex */
public final class MediaControllerInteractorImpl implements MediaControllerInteractor {
    public final CoroutineContext backgroundCoroutineContext;
    public final Handler backgroundHandler;

    public MediaControllerInteractorImpl(Handler handler, CoroutineContext coroutineContext) {
        this.backgroundHandler = handler;
        this.backgroundCoroutineContext = coroutineContext;
    }
}
