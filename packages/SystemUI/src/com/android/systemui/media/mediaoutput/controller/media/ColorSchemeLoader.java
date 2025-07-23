package com.android.systemui.media.mediaoutput.controller.media;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ColorSchemeLoader {
    public final ContextScope coroutineScope;
    public StandaloneCoroutine processingJob;

    public ColorSchemeLoader() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
    }
}
