package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1 homeStatusBarViewModelImpl$canShowOngoingActivityChips$1 = new HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1((Continuation) obj4);
        homeStatusBarViewModelImpl$canShowOngoingActivityChips$1.Z$0 = booleanValue;
        homeStatusBarViewModelImpl$canShowOngoingActivityChips$1.Z$1 = booleanValue2;
        homeStatusBarViewModelImpl$canShowOngoingActivityChips$1.Z$2 = booleanValue3;
        return homeStatusBarViewModelImpl$canShowOngoingActivityChips$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((!this.Z$0 || this.Z$1 || this.Z$2) ? false : true);
    }
}
