package com.android.app.displaylib;

import android.util.Log;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class DisplayRepositoryImpl$enabledDisplays$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public DisplayRepositoryImpl$enabledDisplays$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryImpl$enabledDisplays$2 displayRepositoryImpl$enabledDisplays$2 = new DisplayRepositoryImpl$enabledDisplays$2(continuation);
        displayRepositoryImpl$enabledDisplays$2.L$0 = obj;
        return displayRepositoryImpl$enabledDisplays$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryImpl$enabledDisplays$2) create((Set) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((Set) this.L$0).isEmpty()) {
            Log.wtf("DisplayRepository", "No enabled displays. This should never happen.");
        }
        return Unit.INSTANCE;
    }
}
