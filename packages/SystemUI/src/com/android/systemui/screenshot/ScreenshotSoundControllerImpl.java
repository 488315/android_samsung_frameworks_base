package com.android.systemui.screenshot;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;

public final class ScreenshotSoundControllerImpl implements ScreenshotSoundController {
    public final CoroutineDispatcher bgDispatcher;
    public final CoroutineScope coroutineScope;
    public final DeferredCoroutine player;
    public final ScreenshotSoundProvider soundProvider;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotSoundControllerImpl(ScreenshotSoundProvider screenshotSoundProvider, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.soundProvider = screenshotSoundProvider;
        this.coroutineScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.player = BuildersKt.async$default(coroutineScope, coroutineDispatcher, null, new ScreenshotSoundControllerImpl$special$$inlined$async$1("loadScreenshotSound", null, this), 2);
    }
}
