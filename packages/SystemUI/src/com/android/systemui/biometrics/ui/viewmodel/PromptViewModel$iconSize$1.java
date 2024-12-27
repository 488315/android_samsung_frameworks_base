package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$iconSize$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$iconSize$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptViewModel$iconSize$1 promptViewModel$iconSize$1 = new PromptViewModel$iconSize$1(this.this$0, (Continuation) obj3);
        promptViewModel$iconSize$1.L$0 = (PromptIconViewModel.AuthType) obj;
        promptViewModel$iconSize$1.L$1 = (BiometricModalities) obj2;
        return promptViewModel$iconSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((PromptIconViewModel.AuthType) this.L$0) == PromptIconViewModel.AuthType.Face ? new Pair(new Integer(this.this$0.faceIconWidth), new Integer(this.this$0.faceIconHeight)) : ((BiometricModalities) this.L$1).getHasUdfps() ? new Pair(new Integer(this.this$0.fingerprintSensorWidth), new Integer(this.this$0.fingerprintSensorHeight)) : new Pair(new Integer(this.this$0.fingerprintIconWidth), new Integer(this.this$0.fingerprintIconHeight));
    }
}
