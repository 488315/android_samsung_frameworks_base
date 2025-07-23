package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SuspendKt {
    public static final <R> Object race(Function1[] function1Arr, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new SuspendKt$race$2(function1Arr, null), continuation);
    }
}
