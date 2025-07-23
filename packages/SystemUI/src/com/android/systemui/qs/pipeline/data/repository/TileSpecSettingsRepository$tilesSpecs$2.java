package com.android.systemui.qs.pipeline.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileSpecSettingsRepository$tilesSpecs$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    final /* synthetic */ UserTileSpecRepository $userTileRepository;
    Object L$0;
    int label;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSpecSettingsRepository$tilesSpecs$2(UserTileSpecRepository userTileSpecRepository, TileSpecSettingsRepository tileSpecSettingsRepository, int i, Continuation continuation) {
        super(2, continuation);
        this.$userTileRepository = userTileSpecRepository;
        this.this$0 = tileSpecSettingsRepository;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TileSpecSettingsRepository$tilesSpecs$2(this.$userTileRepository, this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileSpecSettingsRepository$tilesSpecs$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0064, code lost:
    
        if (r4.send(r5, r7) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x003b, code lost:
    
        if (r8 == r0) goto L18;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0064 -> B:6:0x0033). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L33
        L14:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1c:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L3e
        L24:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r8 = r7.$userTileRepository
            kotlinx.coroutines.channels.BufferedChannel r8 = r8.tilesUpgradePath
            r8.getClass()
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = new kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator
            r1.<init>()
        L33:
            r7.L$0 = r1
            r7.label = r3
            java.lang.Object r8 = r1.hasNext(r7)
            if (r8 != r0) goto L3e
            goto L66
        L3e:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L67
            java.lang.Object r8 = r1.next()
            com.android.systemui.qs.pipeline.shared.TilesUpgradePath r8 = (com.android.systemui.qs.pipeline.shared.TilesUpgradePath) r8
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository r4 = r7.this$0
            kotlinx.coroutines.channels.BufferedChannel r4 = r4._tilesUpgradePath
            int r5 = r7.$userId
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r5)
            kotlin.Pair r5 = new kotlin.Pair
            r5.<init>(r8, r6)
            r7.L$0 = r1
            r7.label = r2
            java.lang.Object r8 = r4.send(r5, r7)
            if (r8 != r0) goto L33
        L66:
            return r0
        L67:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
