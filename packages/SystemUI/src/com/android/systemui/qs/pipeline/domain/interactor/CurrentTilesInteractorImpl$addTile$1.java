package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CurrentTilesInteractorImpl$addTile$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $position;
    final /* synthetic */ TileSpec $spec;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$addTile$1(CurrentTilesInteractorImpl currentTilesInteractorImpl, TileSpec tileSpec, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = currentTilesInteractorImpl;
        this.$spec = tileSpec;
        this.$position = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CurrentTilesInteractorImpl$addTile$1(this.this$0, this.$spec, this.$position, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CurrentTilesInteractorImpl$addTile$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
    
        if (r1.addTile(r6, r3, r4, r5) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(r1, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4c
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L31
        L1c:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r6 = r5.this$0
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.currentTiles
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1 r1 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1
            r1.<init>()
            r5.label = r3
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r1, r5)
            if (r6 != r0) goto L31
            goto L4b
        L31:
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r6 = r5.this$0
            com.android.systemui.qs.pipeline.data.repository.TileSpecRepository r1 = r6.tileSpecRepository
            com.android.systemui.user.data.repository.UserRepository r6 = r6.userRepository
            com.android.systemui.user.data.repository.UserRepositoryImpl r6 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r6
            android.content.pm.UserInfo r6 = r6.getSelectedUserInfo()
            int r6 = r6.id
            com.android.systemui.qs.pipeline.shared.TileSpec r3 = r5.$spec
            int r4 = r5.$position
            r5.label = r2
            java.lang.Object r5 = r1.addTile(r6, r3, r4, r5)
            if (r5 != r0) goto L4c
        L4b:
            return r0
        L4c:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
