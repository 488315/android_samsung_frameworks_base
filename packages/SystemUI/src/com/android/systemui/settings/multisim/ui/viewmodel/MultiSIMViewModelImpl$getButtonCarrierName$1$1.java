package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiSIMViewModelImpl$getButtonCarrierName$1$1 extends SuspendLambda implements Function5 {
    final /* synthetic */ ButtonType $type;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiSIMViewModelImpl$getButtonCarrierName$1$1(ButtonType buttonType, Continuation continuation) {
        super(5, continuation);
        this.$type = buttonType;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        MultiSIMViewModelImpl$getButtonCarrierName$1$1 multiSIMViewModelImpl$getButtonCarrierName$1$1 = new MultiSIMViewModelImpl$getButtonCarrierName$1$1(this.$type, (Continuation) obj5);
        multiSIMViewModelImpl$getButtonCarrierName$1$1.I$0 = intValue;
        multiSIMViewModelImpl$getButtonCarrierName$1$1.L$0 = (String) obj2;
        multiSIMViewModelImpl$getButtonCarrierName$1$1.L$1 = (String) obj3;
        multiSIMViewModelImpl$getButtonCarrierName$1$1.Z$0 = booleanValue;
        return multiSIMViewModelImpl$getButtonCarrierName$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        String str = (String) this.L$0;
        String str2 = (String) this.L$1;
        boolean z = this.Z$0;
        ButtonType buttonType = this.$type;
        if (buttonType == ButtonType.VOICE) {
            i--;
        }
        return (buttonType != ButtonType.SIMINFO1 ? buttonType != ButtonType.SIMINFO2 ? i != 1 : z : !z) ? str : str2;
    }
}
