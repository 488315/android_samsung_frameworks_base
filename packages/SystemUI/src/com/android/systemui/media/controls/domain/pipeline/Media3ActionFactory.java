package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.SessionTokenFactory;
import com.android.systemui.util.concurrency.Execution;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Media3ActionFactory {
    public final CoroutineScope bgScope;
    public final Context context;
    public final MediaControllerFactory controllerFactory;
    public final Execution execution;
    public final Handler handler;
    public final ImageLoader imageLoader;
    public final MediaLogger logger;
    public final Looper looper;
    public final SessionTokenFactory tokenFactory;

    public Media3ActionFactory(Context context, ImageLoader imageLoader, MediaControllerFactory mediaControllerFactory, SessionTokenFactory sessionTokenFactory, MediaLogger mediaLogger, Looper looper, Handler handler, CoroutineScope coroutineScope, Execution execution) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.controllerFactory = mediaControllerFactory;
        this.tokenFactory = sessionTokenFactory;
        this.logger = mediaLogger;
        this.looper = looper;
        this.handler = handler;
        this.bgScope = coroutineScope;
        this.execution = execution;
    }
}
