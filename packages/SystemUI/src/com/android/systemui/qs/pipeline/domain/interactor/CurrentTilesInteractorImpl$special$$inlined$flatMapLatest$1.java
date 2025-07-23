package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    int I$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1(Continuation continuation, CurrentTilesInteractorImpl currentTilesInteractorImpl) {
        super(3, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1 currentTilesInteractorImpl$special$$inlined$flatMapLatest$1 = new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0074, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, r10, r9) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L22
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r10)
            goto L77
        L10:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L18:
            int r1 = r9.I$0
            java.lang.Object r3 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L45
        L22:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r1 = r9.L$1
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r4 = r9.this$0
            com.android.systemui.qs.pipeline.data.repository.TileSpecRepository r4 = r4.tileSpecRepository
            r9.L$0 = r10
            r9.I$0 = r1
            r9.label = r3
            java.lang.Object r3 = r4.tilesSpecs(r1, r9)
            if (r3 != r0) goto L42
            goto L76
        L42:
            r8 = r3
            r3 = r10
            r10 = r8
        L45:
            kotlinx.coroutines.flow.Flow r10 = (kotlinx.coroutines.flow.Flow) r10
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r4 = r9.this$0
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository r4 = r4.installedTilesComponentRepository
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl r4 = (com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl) r4
            java.util.Map r5 = r4.userMap
            monitor-enter(r5)
            kotlinx.coroutines.flow.StateFlow r4 = r4.getForUserLocked(r1)     // Catch: java.lang.Throwable -> L7a
            monitor-exit(r5)
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1 r5 = new com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1
            r5.<init>(r4)
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r4 = r9.this$0
            com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository r4 = r4.knoxPolicyTilesRepository
            com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl r4 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl) r4
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r4.knoxBlockedTiles
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$1$1 r6 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$1$1
            r7 = 0
            r6.<init>(r1, r7)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 r10 = kotlinx.coroutines.flow.FlowKt.combine(r10, r5, r4, r6)
            r9.L$0 = r7
            r9.label = r2
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.emitAll(r3, r10, r9)
            if (r9 != r0) goto L77
        L76:
            return r0
        L77:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L7a:
            r9 = move-exception
            monitor-exit(r5)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
