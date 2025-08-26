package com.android.systemui.media.mediaoutput.controller.media;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class ColorSchemeLoader {
    public final ContextScope coroutineScope;
    public StandaloneCoroutine processingJob;

    public ColorSchemeLoader() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
    }
}
