package com.android.systemui.common.ui.domain.interactor;

import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class ConfigurationInteractor$dimensionPixelSize$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $resourceId;
    int label;
    final /* synthetic */ ConfigurationInteractor this$0;

    public ConfigurationInteractor$dimensionPixelSize$1(ConfigurationInteractor configurationInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = configurationInteractor;
        this.$resourceId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConfigurationInteractor$dimensionPixelSize$1(this.this$0, this.$resourceId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationInteractor$dimensionPixelSize$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ConfigurationRepository configurationRepository = this.this$0.repository;
        return new Integer(((ConfigurationRepositoryImpl) configurationRepository).context.getResources().getDimensionPixelSize(this.$resourceId));
    }
}
