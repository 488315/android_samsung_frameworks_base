package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.window.shared.model.StatusBarWindowState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarOrchestrator$shouldAnimateNextBarModeChange$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public StatusBarOrchestrator$shouldAnimateNextBarModeChange$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        StatusBarOrchestrator$shouldAnimateNextBarModeChange$1 statusBarOrchestrator$shouldAnimateNextBarModeChange$1 = new StatusBarOrchestrator$shouldAnimateNextBarModeChange$1((Continuation) obj4);
        statusBarOrchestrator$shouldAnimateNextBarModeChange$1.Z$0 = booleanValue;
        statusBarOrchestrator$shouldAnimateNextBarModeChange$1.Z$1 = booleanValue2;
        statusBarOrchestrator$shouldAnimateNextBarModeChange$1.L$0 = (StatusBarWindowState) obj3;
        return statusBarOrchestrator$shouldAnimateNextBarModeChange$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$0 || !this.Z$1 || ((StatusBarWindowState) this.L$0) == StatusBarWindowState.Hidden) ? false : true);
    }
}
