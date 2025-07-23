package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiSIMViewModelImpl$simInfoPrimaryId$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;

    public MultiSIMViewModelImpl$simInfoPrimaryId$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        MultiSIMViewModelImpl$simInfoPrimaryId$1 multiSIMViewModelImpl$simInfoPrimaryId$1 = new MultiSIMViewModelImpl$simInfoPrimaryId$1((Continuation) obj3);
        multiSIMViewModelImpl$simInfoPrimaryId$1.I$0 = intValue;
        multiSIMViewModelImpl$simInfoPrimaryId$1.Z$0 = booleanValue;
        return multiSIMViewModelImpl$simInfoPrimaryId$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        if (this.Z$0) {
            i = 1 - i;
        }
        return new Integer(i);
    }
}
