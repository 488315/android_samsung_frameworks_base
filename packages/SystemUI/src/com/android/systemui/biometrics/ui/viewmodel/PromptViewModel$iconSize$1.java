package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PromptViewModel$iconSize$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$iconSize$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj3).intValue();
        int intValue2 = ((Number) obj4).intValue();
        PromptViewModel$iconSize$1 promptViewModel$iconSize$1 = new PromptViewModel$iconSize$1(this.this$0, (Continuation) obj5);
        promptViewModel$iconSize$1.L$0 = (PromptIconViewModel.AuthType) obj;
        promptViewModel$iconSize$1.L$1 = (BiometricModalities) obj2;
        promptViewModel$iconSize$1.I$0 = intValue;
        promptViewModel$iconSize$1.I$1 = intValue2;
        return promptViewModel$iconSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((PromptIconViewModel.AuthType) this.L$0) == PromptIconViewModel.AuthType.Face ? new Pair(new Integer(this.this$0.faceIconWidth), new Integer(this.this$0.faceIconHeight)) : ((BiometricModalities) this.L$1).getHasUdfps() ? new Pair(new Integer(this.I$0), new Integer(this.I$1)) : new Pair(new Integer(this.this$0.fingerprintIconWidth), new Integer(this.this$0.fingerprintIconHeight));
    }
}
