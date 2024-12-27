package com.android.systemui.media.mediaoutput.controller.media;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ColorSchemeLoader {
    public final ContextScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.IO);
    public Job processingJob;
}
