package com.android.systemui.kairos;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public interface KairosNetwork {
    Object activateSpec(Function1 function1, Continuation continuation);

    Object transact(Function1 function1, Continuation continuation);
}
