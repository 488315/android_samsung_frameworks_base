package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

final class KeyguardBlueprintInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardBlueprintInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBlueprintInteractor$start$1(KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardBlueprintInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardBlueprintInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBlueprintInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.this$0;
            KeyguardBlueprintInteractor$special$$inlined$map$1 keyguardBlueprintInteractor$special$$inlined$map$1 = keyguardBlueprintInteractor.blueprintId;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardBlueprintInteractor.this.keyguardBlueprintRepository.applyBlueprint((String) obj2);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (keyguardBlueprintInteractor$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
