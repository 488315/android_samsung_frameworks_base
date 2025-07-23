package com.android.systemui.screenshot;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotSoundControllerImpl implements ScreenshotSoundController {
    public final CoroutineDispatcher bgDispatcher;
    public final CoroutineScope coroutineScope;
    public final DeferredCoroutine player;
    public final ScreenshotSoundProvider soundProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotSoundControllerImpl(ScreenshotSoundProvider screenshotSoundProvider, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.soundProvider = screenshotSoundProvider;
        this.coroutineScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.player = CoroutineTracingKt.asyncTraced$default(coroutineScope, coroutineDispatcher, null, new ScreenshotSoundControllerImpl$player$1(this, null), 4);
    }
}
