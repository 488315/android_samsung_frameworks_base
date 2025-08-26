package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    int I$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SubscreenTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1(Continuation continuation, SubscreenTilesInteractorImpl subscreenTilesInteractorImpl) {
        super(3, continuation);
        this.this$0 = subscreenTilesInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1 subscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1 = new SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        subscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        subscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return subscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006d, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, r1, r8) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int iIntValue;
        FlowCollector flowCollector;
        StateFlow forUserLocked;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            iIntValue = ((Number) this.L$1).intValue();
            TileSpecRepository tileSpecRepository = this.this$0.tileSpecRepository;
            this.L$0 = flowCollector2;
            this.I$0 = iIntValue;
            this.label = 1;
            Object objTilesSpecs = tileSpecRepository.tilesSpecs(iIntValue, this);
            if (objTilesSpecs != coroutineSingletons) {
                flowCollector = flowCollector2;
                obj = objTilesSpecs;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        iIntValue = this.I$0;
        flowCollector = (FlowCollector) this.L$0;
        ResultKt.throwOnFailure(obj);
        Flow flow = (Flow) obj;
        InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = (InstalledTilesComponentRepositoryImpl) this.this$0.installedTilesComponentRepository;
        synchronized (installedTilesComponentRepositoryImpl.userMap) {
            forUserLocked = installedTilesComponentRepositoryImpl.getForUserLocked(iIntValue);
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, new InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1(forUserLocked), new SubscreenTilesInteractorImpl$userAndTiles$1$1(iIntValue, null));
        this.L$0 = null;
        this.label = 2;
    }
}
