package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

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
        int iIntValue = ((Number) obj).intValue();
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        MultiSIMViewModelImpl$simInfoPrimaryId$1 multiSIMViewModelImpl$simInfoPrimaryId$1 = new MultiSIMViewModelImpl$simInfoPrimaryId$1((Continuation) obj3);
        multiSIMViewModelImpl$simInfoPrimaryId$1.I$0 = iIntValue;
        multiSIMViewModelImpl$simInfoPrimaryId$1.Z$0 = zBooleanValue;
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
