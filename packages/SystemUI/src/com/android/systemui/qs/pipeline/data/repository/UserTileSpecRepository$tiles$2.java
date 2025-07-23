package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class UserTileSpecRepository$tiles$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserTileSpecRepository$tiles$2(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserTileSpecRepository$tiles$2 userTileSpecRepository$tiles$2 = new UserTileSpecRepository$tiles$2(this.this$0, (Continuation) obj3);
        userTileSpecRepository$tiles$2.L$0 = (List) obj;
        userTileSpecRepository$tiles$2.L$1 = (UserTileSpecRepository.ChangeAction) obj2;
        return userTileSpecRepository$tiles$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x00d6  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L1a
            if (r1 != r2) goto L12
            java.lang.Object r11 = r11.L$0
            java.util.List r11 = (java.util.List) r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto Lcd
        L12:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1a:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            java.util.List r12 = (java.util.List) r12
            java.lang.Object r1 = r11.L$1
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$ChangeAction r1 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction) r1
            java.util.List r3 = r1.apply(r12)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r4 = r11.this$0
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r3)
            if (r5 != 0) goto Lb0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "changed qs_tiles from "
            r5.<init>(r6)
            r5.append(r12)
            java.lang.String r6 = " to "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "UserTileSpecRepository"
            android.util.Log.d(r6, r5)
            boolean r5 = r1 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.RestoreTiles
            java.lang.String r6 = "QSTileListLog"
            r7 = 0
            if (r5 == 0) goto L7d
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r5 = r4.logger
            r5.getClass()
            com.android.systemui.log.core.LogLevel r8 = com.android.systemui.log.core.LogLevel.DEBUG
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0 r9 = new com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0
            r10 = 6
            r9.<init>(r10)
            com.android.systemui.log.LogBuffer r5 = r5.tileListLogBuffer
            com.android.systemui.log.core.LogMessage r6 = r5.obtain(r6, r8, r9, r7)
            java.lang.String r12 = r12.toString()
            r7 = r6
            com.android.systemui.log.LogMessageImpl r7 = (com.android.systemui.log.LogMessageImpl) r7
            r7.str1 = r12
            java.lang.String r12 = r3.toString()
            r7.str2 = r12
            int r12 = r4.userId
            r7.int1 = r12
            r5.commit(r6)
            goto Lb0
        L7d:
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r12 = r4.logger
            r12.getClass()
            boolean r5 = com.android.systemui.ScRune.QUICK_MANAGE_MULTI_QSHOST
            int r8 = r4.userId
            if (r5 == 0) goto L8e
            com.android.systemui.qs.pipeline.dagger.QSType r5 = com.android.systemui.qs.pipeline.dagger.QSType.QS
            r12.logProcessTileChange(r1, r3, r8, r5)
            goto Lb0
        L8e:
            com.android.systemui.log.core.LogLevel r5 = com.android.systemui.log.core.LogLevel.DEBUG
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0 r9 = new com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0
            r10 = 3
            r9.<init>(r10)
            com.android.systemui.log.LogBuffer r12 = r12.tileListLogBuffer
            com.android.systemui.log.core.LogMessage r5 = r12.obtain(r6, r5, r9, r7)
            java.lang.String r6 = r1.toString()
            r7 = r5
            com.android.systemui.log.LogMessageImpl r7 = (com.android.systemui.log.LogMessageImpl) r7
            r7.str1 = r6
            java.lang.String r6 = r3.toString()
            r7.str2 = r6
            r7.int1 = r8
            r12.commit(r5)
        Lb0:
            boolean r12 = r1 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.RestoreTiles
            if (r12 == 0) goto Lce
            kotlinx.coroutines.channels.BufferedChannel r12 = r4._tilesUpgradePath
            r1 = r3
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Set r1 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r1)
            com.android.systemui.qs.pipeline.shared.TilesUpgradePath$RestoreFromBackup r1 = com.android.systemui.qs.pipeline.shared.TilesUpgradePath.RestoreFromBackup.m2897boximpl(r1)
            r11.L$0 = r3
            r11.label = r2
            java.lang.Object r11 = r12.send(r1, r11)
            if (r11 != r0) goto Lcc
            return r0
        Lcc:
            r11 = r3
        Lcd:
            r3 = r11
        Lce:
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r11 = r3.isEmpty()
            if (r11 == 0) goto Ldc
            com.android.systemui.qs.pipeline.shared.TileSpec$Empty r11 = com.android.systemui.qs.pipeline.shared.TileSpec.Empty.INSTANCE
            java.util.List r3 = java.util.Collections.singletonList(r11)
        Ldc:
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.util.List r11 = kotlin.collections.CollectionsKt___CollectionsKt.distinct(r3)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
