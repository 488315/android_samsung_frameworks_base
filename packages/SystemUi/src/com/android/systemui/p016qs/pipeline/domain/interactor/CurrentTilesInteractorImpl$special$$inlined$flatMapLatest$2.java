package com.android.systemui.p016qs.pipeline.domain.interactor;

import com.android.systemui.p016qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2", m277f = "CurrentTilesInteractor.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2(Continuation continuation, CurrentTilesInteractorImpl currentTilesInteractorImpl) {
        super(3, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2 currentTilesInteractorImpl$special$$inlined$flatMapLatest$2 = new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return currentTilesInteractorImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow installedTilesComponents = ((InstalledTilesComponentRepositoryImpl) this.this$0.installedTilesComponentRepository).getInstalledTilesComponents(((Number) this.L$1).intValue());
            this.label = 1;
            if (FlowKt.emitAll(this, installedTilesComponents, flowCollector) == coroutineSingletons) {
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
