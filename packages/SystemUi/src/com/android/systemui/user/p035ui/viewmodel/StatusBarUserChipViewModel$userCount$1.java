package com.android.systemui.user.p035ui.viewmodel;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel$userCount$1", m277f = "StatusBarUserChipViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class StatusBarUserChipViewModel$userCount$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public StatusBarUserChipViewModel$userCount$1(Continuation<? super StatusBarUserChipViewModel$userCount$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarUserChipViewModel$userCount$1 statusBarUserChipViewModel$userCount$1 = new StatusBarUserChipViewModel$userCount$1(continuation);
        statusBarUserChipViewModel$userCount$1.L$0 = obj;
        return statusBarUserChipViewModel$userCount$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarUserChipViewModel$userCount$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Integer(((List) this.L$0).size());
    }
}
