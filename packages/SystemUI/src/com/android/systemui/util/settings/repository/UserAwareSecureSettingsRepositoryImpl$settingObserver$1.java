package com.android.systemui.util.settings.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class UserAwareSecureSettingsRepositoryImpl$settingObserver$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public UserAwareSecureSettingsRepositoryImpl$settingObserver$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserAwareSecureSettingsRepositoryImpl$settingObserver$1 userAwareSecureSettingsRepositoryImpl$settingObserver$1 = new UserAwareSecureSettingsRepositoryImpl$settingObserver$1(continuation);
        userAwareSecureSettingsRepositoryImpl$settingObserver$1.L$0 = obj;
        return userAwareSecureSettingsRepositoryImpl$settingObserver$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Unit unit = Unit.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(unit, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((UserAwareSecureSettingsRepositoryImpl$settingObserver$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
