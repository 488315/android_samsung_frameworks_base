package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

public final class SuspendKt {
    public static final <R> Object race(Function1[] function1Arr, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new SuspendKt$race$2(function1Arr, null), continuation);
    }
}
