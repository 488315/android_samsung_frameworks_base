package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AuthController $authController$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2(Continuation continuation, AuthController authController) {
        super(3, continuation);
        this.$authController$inlined = authController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$authController$inlined);
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1(this.$authController$inlined, ((Number) this.L$1).intValue(), null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, conflatedCallbackFlow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
