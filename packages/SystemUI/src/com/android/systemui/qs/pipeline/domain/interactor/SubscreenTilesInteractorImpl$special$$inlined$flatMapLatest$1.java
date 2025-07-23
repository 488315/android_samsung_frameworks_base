package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, r1, r8) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L22
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r9)
            goto L70
        L10:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L18:
            int r1 = r8.I$0
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L45
        L22:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r1 = r8.L$1
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl r4 = r8.this$0
            com.android.systemui.qs.pipeline.data.repository.TileSpecRepository r4 = r4.tileSpecRepository
            r8.L$0 = r9
            r8.I$0 = r1
            r8.label = r3
            java.lang.Object r3 = r4.tilesSpecs(r1, r8)
            if (r3 != r0) goto L42
            goto L6f
        L42:
            r7 = r3
            r3 = r9
            r9 = r7
        L45:
            kotlinx.coroutines.flow.Flow r9 = (kotlinx.coroutines.flow.Flow) r9
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl r4 = r8.this$0
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository r4 = r4.installedTilesComponentRepository
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl r4 = (com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl) r4
            java.util.Map r5 = r4.userMap
            monitor-enter(r5)
            kotlinx.coroutines.flow.StateFlow r4 = r4.getForUserLocked(r1)     // Catch: java.lang.Throwable -> L73
            monitor-exit(r5)
            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1 r5 = new com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1
            r5.<init>(r4)
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$userAndTiles$1$1 r4 = new com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$userAndTiles$1$1
            r6 = 0
            r4.<init>(r1, r6)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r1 = new kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            r1.<init>(r9, r5, r4)
            r8.L$0 = r6
            r8.label = r2
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.emitAll(r3, r1, r8)
            if (r8 != r0) goto L70
        L6f:
            return r0
        L70:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L73:
            r8 = move-exception
            monitor-exit(r5)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
