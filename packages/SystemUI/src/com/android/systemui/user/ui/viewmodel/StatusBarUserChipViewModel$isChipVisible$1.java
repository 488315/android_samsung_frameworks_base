package com.android.systemui.user.ui.viewmodel;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class StatusBarUserChipViewModel$isChipVisible$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public StatusBarUserChipViewModel$isChipVisible$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarUserChipViewModel$isChipVisible$1 statusBarUserChipViewModel$isChipVisible$1 = new StatusBarUserChipViewModel$isChipVisible$1(continuation);
        statusBarUserChipViewModel$isChipVisible$1.L$0 = obj;
        return statusBarUserChipViewModel$isChipVisible$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarUserChipViewModel$isChipVisible$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((List) this.L$0).size() > 1);
    }
}
