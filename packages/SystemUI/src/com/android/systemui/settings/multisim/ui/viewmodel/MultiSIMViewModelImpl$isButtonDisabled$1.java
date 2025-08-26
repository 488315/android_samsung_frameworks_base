package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class MultiSIMViewModelImpl$isButtonDisabled$1 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    /* synthetic */ boolean Z$4;
    int label;

    public MultiSIMViewModelImpl$isButtonDisabled$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
        boolean zBooleanValue5 = ((Boolean) obj5).booleanValue();
        MultiSIMViewModelImpl$isButtonDisabled$1 multiSIMViewModelImpl$isButtonDisabled$1 = new MultiSIMViewModelImpl$isButtonDisabled$1((Continuation) obj6);
        multiSIMViewModelImpl$isButtonDisabled$1.Z$0 = zBooleanValue;
        multiSIMViewModelImpl$isButtonDisabled$1.Z$1 = zBooleanValue2;
        multiSIMViewModelImpl$isButtonDisabled$1.Z$2 = zBooleanValue3;
        multiSIMViewModelImpl$isButtonDisabled$1.Z$3 = zBooleanValue4;
        multiSIMViewModelImpl$isButtonDisabled$1.Z$4 = zBooleanValue5;
        return multiSIMViewModelImpl$isButtonDisabled$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || this.Z$1 || this.Z$2 || this.Z$3 || this.Z$4);
    }
}
