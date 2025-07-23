package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RemovedTileSpecRepositoryImpl implements RemovedTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public RemovedTileSpecRepositoryImpl(Resources resources, SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.resources = resources;
        this.secureSettings = secureSettings;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTilesFromSettings(int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1 r0 = (com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1 r0 = new com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r6 = r0.L$1
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$Companion r6 = (com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl.Companion) r6
            java.lang.Object r7 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl r7 = (com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L79
        L2f:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L37:
            kotlin.ResultKt.throwOnFailure(r8)
            boolean r8 = com.android.systemui.ScRune.QUICK_MANAGE_TILE_LIST_TEST
            r2 = 0
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$Companion r4 = com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl.Companion
            if (r8 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository r8 = r6.testTileDataRepository
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl r8 = (com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl) r8
            boolean r5 = r8.isFotaTest()
            if (r5 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource r7 = r8.tileDataSource
            if (r7 == 0) goto L50
            r2 = r7
        L50:
            java.lang.String r7 = r2.getRemovedTiles()
            android.content.res.Resources r6 = r6.resources
            r4.getClass()
            com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter r8 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.INSTANCE
            r8.getClass()
            java.util.List r6 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.toTilesList(r6, r7)
            return r6
        L63:
            com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$2 r8 = new com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$2
            r8.<init>(r6, r7, r2)
            r0.L$0 = r6
            r0.L$1 = r4
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r7 = r6.backgroundDispatcher
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r7, r8, r0)
            if (r8 != r1) goto L77
            return r1
        L77:
            r7 = r6
            r6 = r4
        L79:
            java.lang.String r8 = (java.lang.String) r8
            android.content.res.Resources r7 = r7.resources
            r6.getClass()
            com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter r6 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.INSTANCE
            r6.getClass()
            java.util.List r6 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.toTilesList(r7, r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl.loadTilesFromSettings(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object storeTiles(int i, List list, SuspendLambda suspendLambda) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new RemovedTileSpecRepositoryImpl$storeTiles$2(this, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$storeTiles$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), i, null), suspendLambda);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
