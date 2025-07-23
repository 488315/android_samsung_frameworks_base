package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.os.Handler;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaControllerInteractorImpl implements MediaControllerInteractor {
    public final CoroutineContext backgroundCoroutineContext;
    public final Handler backgroundHandler;

    public MediaControllerInteractorImpl(Handler handler, CoroutineContext coroutineContext) {
        this.backgroundHandler = handler;
        this.backgroundCoroutineContext = coroutineContext;
    }
}
