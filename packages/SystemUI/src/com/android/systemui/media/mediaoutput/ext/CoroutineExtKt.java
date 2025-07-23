package com.android.systemui.media.mediaoutput.ext;

import androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CoroutineExtKt {
    public static final StandaloneCoroutine durationLaunch(CloseableCoroutineScope closeableCoroutineScope, CoroutineDispatcher coroutineDispatcher, Function1 function1) {
        return BuildersKt.launch$default(closeableCoroutineScope, coroutineDispatcher, null, new CoroutineExtKt$durationLaunch$1(function1, 300L, null), 2);
    }
}
