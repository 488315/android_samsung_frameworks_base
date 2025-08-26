package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
final class PromptViewModel$legacyFingerprintSensorHeight$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$legacyFingerprintSensorHeight$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int iIntValue = ((Number) obj2).intValue();
        PromptViewModel$legacyFingerprintSensorHeight$1 promptViewModel$legacyFingerprintSensorHeight$1 = new PromptViewModel$legacyFingerprintSensorHeight$1(this.this$0, (Continuation) obj3);
        promptViewModel$legacyFingerprintSensorHeight$1.L$0 = (BiometricModalities) obj;
        promptViewModel$legacyFingerprintSensorHeight$1.I$0 = iIntValue;
        return promptViewModel$legacyFingerprintSensorHeight$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BiometricModalities biometricModalities = (BiometricModalities) this.L$0;
        int i = this.I$0;
        if (!biometricModalities.getHasUdfps()) {
            i = this.this$0.fingerprintIconHeight;
        }
        return new Integer(i);
    }
}
