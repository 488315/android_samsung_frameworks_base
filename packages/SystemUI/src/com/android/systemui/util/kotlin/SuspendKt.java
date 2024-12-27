package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SuspendKt {
    public static final <R> Object race(Function1[] function1Arr, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new SuspendKt$race$2(function1Arr, null), continuation);
    }
}
