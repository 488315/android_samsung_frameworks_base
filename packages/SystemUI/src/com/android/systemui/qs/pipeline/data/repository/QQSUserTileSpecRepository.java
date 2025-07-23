package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QQSUserTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _tiles;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;
    public final int userId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChangeTiles implements UserTileSpecRepository.ChangeAction {
        public final List newTiles;

        public ChangeTiles(List<? extends TileSpec> list) {
            this.newTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            List list2 = this.newTiles;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list2) {
                if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                    arrayList.add(obj);
                }
            }
            return !arrayList.isEmpty() ? arrayList : list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ChangeTiles) && Intrinsics.areEqual(this.newTiles, ((ChangeTiles) obj).newTiles);
        }

        public final int hashCode() {
            return this.newTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ChangeTiles(newTiles=", this.newTiles, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QQSUserTileSpecRepository create(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PrependDefault implements UserTileSpecRepository.ChangeAction {
        public final List defaultTiles;

        public PrependDefault(List<? extends TileSpec> list) {
            this.defaultTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            return CollectionsKt___CollectionsKt.plus((Iterable) list, (Collection) this.defaultTiles);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PrependDefault) && Intrinsics.areEqual(this.defaultTiles, ((PrependDefault) obj).defaultTiles);
        }

        public final int hashCode() {
            return this.defaultTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("PrependDefault(defaultTiles=", this.defaultTiles, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoveTiles implements UserTileSpecRepository.ChangeAction {
        public final Collection tileSpecs;

        public RemoveTiles(Collection<? extends TileSpec> collection) {
            this.tileSpecs = collection;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            ArrayList arrayList = new ArrayList(list);
            arrayList.removeAll(this.tileSpecs);
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RemoveTiles) && Intrinsics.areEqual(this.tileSpecs, ((RemoveTiles) obj).tileSpecs);
        }

        public final int hashCode() {
            return this.tileSpecs.hashCode();
        }

        public final String toString() {
            return "RemoveTiles(tileSpecs=" + this.tileSpecs + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResetToDefault implements UserTileSpecRepository.ChangeAction {
        public final List defaultTiles;

        public ResetToDefault(List<? extends TileSpec> list) {
            this.defaultTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            return this.defaultTiles;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ResetToDefault) && Intrinsics.areEqual(this.defaultTiles, ((ResetToDefault) obj).defaultTiles);
        }

        public final int hashCode() {
            return this.defaultTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ResetToDefault(defaultTiles=", this.defaultTiles, ")");
        }
    }

    public QQSUserTileSpecRepository(int i, Resources resources, DefaultTilesRepository defaultTilesRepository, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.userId = i;
        this.resources = resources;
        this.defaultTilesRepository = defaultTilesRepository;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
    }

    public static final Object access$storeTiles(QQSUserTileSpecRepository qQSUserTileSpecRepository, int i, List list, Continuation continuation) {
        qQSUserTileSpecRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object withContext = BuildersKt.withContext(qQSUserTileSpecRepository.backgroundDispatcher, new QQSUserTileSpecRepository$storeTiles$2(qQSUserTileSpecRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$storeTiles$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), i, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
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
            boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r6 = r0.L$1
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$Companion r6 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository.Companion) r6
            java.lang.Object r7 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r7 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r7
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
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$Companion r4 = com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository.Companion
            if (r8 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository r8 = r6.testTileDataRepository
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl r8 = (com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl) r8
            boolean r5 = r8.isFotaTest()
            if (r5 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource r7 = r8.tileDataSource
            if (r7 == 0) goto L50
            r2 = r7
        L50:
            java.lang.String r7 = r2.getQqsTiles()
            android.content.res.Resources r6 = r6.resources
            r4.getClass()
            com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter r8 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.INSTANCE
            r8.getClass()
            java.util.List r6 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.toTilesList(r6, r7)
            return r6
        L63:
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$2 r8 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$2
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository.loadTilesFromSettings(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTilesFromSettingsAndParse(int r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            int r5 = r0.I$0
            java.lang.Object r4 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r4 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L45
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.I$0 = r5
            r0.label = r3
            java.lang.Object r6 = r4.loadTilesFromSettings(r5, r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            java.util.List r6 = (java.util.List) r6
            r4.getClass()
            r0 = r6
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r1 = r4.logger
            if (r0 != 0) goto L5c
            r4 = 0
            com.android.systemui.qs.pipeline.dagger.QSType r0 = com.android.systemui.qs.pipeline.dagger.QSType.QQS
            r1.logParsedTiles(r6, r4, r5, r0)
            return r6
        L5c:
            com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository r4 = r4.defaultTilesRepository
            java.util.List r4 = r4.getDefaultTiles()
            com.android.systemui.qs.pipeline.dagger.QSType r6 = com.android.systemui.qs.pipeline.dagger.QSType.QQS
            r1.logParsedTiles(r4, r3, r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository.loadTilesFromSettingsAndParse(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00a4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tiles(kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1
            r0.<init>(r8, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L4b
            if (r2 == r5) goto L3b
            if (r2 != r4) goto L33
            java.lang.Object r8 = r0.L$1
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r8 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r8
            java.lang.Object r0 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r0 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r0
            kotlin.ResultKt.throwOnFailure(r9)
            goto L8c
        L33:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3b:
            java.lang.Object r8 = r0.L$2
            kotlinx.coroutines.flow.Flow r8 = (kotlinx.coroutines.flow.Flow) r8
            java.lang.Object r2 = r0.L$1
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r2 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r2
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository r5 = (com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository) r5
            kotlin.ResultKt.throwOnFailure(r9)
            goto L69
        L4b:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.flow.StateFlow r9 = r8._tiles
            if (r9 != 0) goto La0
            kotlinx.coroutines.flow.SharedFlowImpl r9 = r8.changeEvents
            r0.L$0 = r8
            r0.L$1 = r8
            r0.L$2 = r9
            r0.label = r5
            int r2 = r8.userId
            java.lang.Object r2 = r8.loadTilesFromSettingsAndParse(r2, r0)
            if (r2 != r1) goto L65
            goto L89
        L65:
            r5 = r8
            r8 = r9
            r9 = r2
            r2 = r5
        L69:
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$2 r6 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$2
            r6.<init>(r5, r3)
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 r7 = new kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1
            r7.<init>(r9, r8, r6)
            kotlinx.coroutines.CoroutineDispatcher r8 = r5.backgroundDispatcher
            kotlinx.coroutines.flow.Flow r8 = kotlinx.coroutines.flow.FlowKt.flowOn(r7, r8)
            r0.L$0 = r5
            r0.L$1 = r2
            r0.L$2 = r3
            r0.label = r4
            kotlinx.coroutines.CoroutineScope r9 = r5.applicationScope
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.stateIn(r8, r9, r0)
            if (r9 != r1) goto L8a
        L89:
            return r1
        L8a:
            r8 = r2
            r0 = r5
        L8c:
            kotlinx.coroutines.flow.StateFlow r9 = (kotlinx.coroutines.flow.StateFlow) r9
            r0.getClass()
            com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$startFlowCollections$1 r1 = new com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$startFlowCollections$1
            r1.<init>(r9, r0, r3)
            kotlinx.coroutines.CoroutineDispatcher r2 = r0.backgroundDispatcher
            kotlinx.coroutines.CoroutineScope r5 = r0.applicationScope
            kotlinx.coroutines.BuildersKt.launch$default(r5, r2, r3, r1, r4)
            r8._tiles = r9
            r8 = r0
        La0:
            kotlinx.coroutines.flow.StateFlow r8 = r8._tiles
            if (r8 != 0) goto La5
            return r3
        La5:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository.tiles(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AddTile implements UserTileSpecRepository.ChangeAction {
        public final int position;
        public final TileSpec tileSpec;

        public AddTile(TileSpec tileSpec, int i) {
            this.tileSpec = tileSpec;
            this.position = i;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            ArrayList arrayList = new ArrayList(list);
            TileSpec tileSpec = this.tileSpec;
            if (!arrayList.contains(tileSpec)) {
                int i = this.position;
                if (i >= 0 && i < arrayList.size()) {
                    arrayList.add(i, tileSpec);
                    return arrayList;
                }
                arrayList.add(tileSpec);
            }
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AddTile)) {
                return false;
            }
            AddTile addTile = (AddTile) obj;
            return Intrinsics.areEqual(this.tileSpec, addTile.tileSpec) && this.position == addTile.position;
        }

        public final int hashCode() {
            return Integer.hashCode(this.position) + (this.tileSpec.hashCode() * 31);
        }

        public final String toString() {
            return "AddTile(tileSpec=" + this.tileSpec + ", position=" + this.position + ")";
        }

        public /* synthetic */ AddTile(TileSpec tileSpec, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSpec, (i2 & 2) != 0 ? -1 : i);
        }
    }
}
