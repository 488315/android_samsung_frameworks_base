package com.samsung.android.sesl.transparentvideo.renderer;

import android.os.Handler;
import android.os.HandlerThread;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GLCoroutineDispatcher extends CoroutineDispatcher {
    public static final Companion Companion = new Companion(null);
    public static final GLCoroutineDispatcher instance = new GLCoroutineDispatcher();
    public final Handler glHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public GLCoroutineDispatcher() {
        HandlerThread handlerThread = new HandlerThread("GLCoroutineThread");
        handlerThread.start();
        this.glHandler = new Handler(handlerThread.getLooper());
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        this.glHandler.post(runnable);
    }
}
