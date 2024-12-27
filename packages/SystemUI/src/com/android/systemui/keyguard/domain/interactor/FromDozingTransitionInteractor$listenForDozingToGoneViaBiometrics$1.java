package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDozingTransitionInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return new Pair(bool, (BiometricUnlockModel) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1(FromDozingTransitionInteractor fromDozingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDozingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
            Flow sample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromDozingTransitionInteractor.powerInteractor.isAwake, fromDozingTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    return bool;
                }
            }), this.this$0.keyguardInteractor.biometricUnlockState, AnonymousClass3.INSTANCE);
            final FromDozingTransitionInteractor fromDozingTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) ((Pair) obj2).component2();
                    BiometricUnlockMode.Companion companion = BiometricUnlockMode.Companion;
                    BiometricUnlockMode biometricUnlockMode = biometricUnlockModel.mode;
                    companion.getClass();
                    if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode)) {
                        return Unit.INSTANCE;
                    }
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromDozingTransitionInteractor.this, KeyguardState.GONE, null, null, "biometric wake and unlock", continuation, 6);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
