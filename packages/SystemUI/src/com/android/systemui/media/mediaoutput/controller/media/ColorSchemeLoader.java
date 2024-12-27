package com.android.systemui.media.mediaoutput.controller.media;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ContextScope;

public final class ColorSchemeLoader {
    public final ContextScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.IO);
    public Job processingJob;
}
