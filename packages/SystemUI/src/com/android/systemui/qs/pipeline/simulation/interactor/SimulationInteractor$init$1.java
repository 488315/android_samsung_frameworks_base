package com.android.systemui.qs.pipeline.simulation.interactor;

import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractorImpl;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SimulationInteractor$init$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SimulationInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.simulation.interactor.SimulationInteractor$init$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SimulationInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SimulationInteractor simulationInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = simulationInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SimulationInteractor simulationInteractor = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor.backUpRestoreInteractor).setRestoreData(simulationInteractor.makeRestoreData("sep_version"));
            SimulationInteractor simulationInteractor2 = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor2.backUpRestoreInteractor).setRestoreData(simulationInteractor2.makeRestoreData("has_edited"));
            SimulationInteractor simulationInteractor3 = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor3.backUpRestoreInteractor).setRestoreData(simulationInteractor3.makeRestoreData("removed_tile_list"));
            SimulationInteractor simulationInteractor4 = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor4.backUpRestoreInteractor).setRestoreData(simulationInteractor4.makeRestoreData("tile_list"));
            SimulationInteractor simulationInteractor5 = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor5.backUpRestoreInteractor).setRestoreData(simulationInteractor5.makeRestoreData("qqs_has_edited"));
            SimulationInteractor simulationInteractor6 = this.this$0;
            ((TilesBackUpRestoreInteractorImpl) simulationInteractor6.backUpRestoreInteractor).setRestoreData(simulationInteractor6.makeRestoreData("qqs_tile_list"));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimulationInteractor$init$1(SimulationInteractor simulationInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simulationInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimulationInteractor$init$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimulationInteractor$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SimulationInteractor simulationInteractor = this.this$0;
            SharedFlowImpl sharedFlowImpl = ((TestTileDataRepositoryImpl) simulationInteractor.testTileDataRepository).doRestore;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(simulationInteractor, null);
            this.label = 1;
            if (FlowKt.collectLatest(sharedFlowImpl, anonymousClass1, this) == coroutineSingletons) {
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
