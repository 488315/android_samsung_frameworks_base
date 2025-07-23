package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UserTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _tiles;
    public final BufferedChannel _tilesUpgradePath;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl changeEvents;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;
    public final BufferedChannel tilesUpgradePath;
    public final int userId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ChangeAction {
        List apply(List list);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChangeTiles implements ChangeAction {
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
            return arrayList;
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
        UserTileSpecRepository create(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PrependDefault implements ChangeAction {
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
    public final class RemoveTiles implements ChangeAction {
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
    public final class ResetToDefault implements ChangeAction {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RestoreTiles implements ChangeAction {
        public final Set currentAutoAdded;
        public final RestoreData restoreData;

        public RestoreTiles(RestoreData restoreData, Set<? extends TileSpec> set) {
            this.restoreData = restoreData;
            this.currentAutoAdded = set;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            Companion companion = UserTileSpecRepository.Companion;
            Set set = this.currentAutoAdded;
            companion.getClass();
            RestoreData restoreData = this.restoreData;
            ArrayList arrayList = new ArrayList(restoreData.restoredTiles);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : set) {
                if (!restoreData.restoredAutoAddedTiles.contains((TileSpec) obj)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                TileSpec tileSpec = (TileSpec) obj2;
                if (list.contains(tileSpec) && !restoreData.restoredTiles.contains(tileSpec)) {
                    arrayList3.add(obj2);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            int size2 = arrayList3.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj3 = arrayList3.get(i3);
                i3++;
                TileSpec tileSpec2 = (TileSpec) obj3;
                arrayList4.add(new Pair(tileSpec2, Integer.valueOf(list.indexOf(tileSpec2))));
            }
            for (Object obj4 : CollectionsKt___CollectionsKt.sortedWith(arrayList4, new Comparator() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion$reconcileTiles$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj5, Object obj6) {
                    return ComparisonsKt__ComparisonsKt.compareValues((Integer) ((Pair) obj5).getSecond(), (Integer) ((Pair) obj6).getSecond());
                }
            })) {
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                Pair pair = (Pair) obj4;
                TileSpec tileSpec3 = (TileSpec) pair.component1();
                int intValue = ((Number) pair.component2()).intValue() + i;
                if (intValue > arrayList.size()) {
                    arrayList.add(tileSpec3);
                } else {
                    arrayList.add(intValue, tileSpec3);
                }
                i = i4;
            }
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RestoreTiles)) {
                return false;
            }
            RestoreTiles restoreTiles = (RestoreTiles) obj;
            return Intrinsics.areEqual(this.restoreData, restoreTiles.restoreData) && Intrinsics.areEqual(this.currentAutoAdded, restoreTiles.currentAutoAdded);
        }

        public final int hashCode() {
            return this.currentAutoAdded.hashCode() + (this.restoreData.hashCode() * 31);
        }

        public final String toString() {
            return "RestoreTiles(restoreData=" + this.restoreData + ", currentAutoAdded=" + this.currentAutoAdded + ")";
        }
    }

    public UserTileSpecRepository(int i, Resources resources, DefaultTilesRepository defaultTilesRepository, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.userId = i;
        this.resources = resources;
        this.defaultTilesRepository = defaultTilesRepository;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
        BufferedChannel Channel$default = ChannelKt.Channel$default(3, null, null, 6);
        this._tilesUpgradePath = Channel$default;
        this.tilesUpgradePath = Channel$default;
        this.changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    }

    public static final Object access$storeTiles(UserTileSpecRepository userTileSpecRepository, int i, List list, Continuation continuation) {
        userTileSpecRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object withContext = BuildersKt.withContext(userTileSpecRepository.backgroundDispatcher, new UserTileSpecRepository$storeTiles$2(userTileSpecRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$storeTiles$toStore$2
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
            boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1 r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1 r0 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r6 = r0.L$1
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion r6 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.Companion) r6
            java.lang.Object r7 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r7 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r7
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
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion r4 = com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.Companion
            if (r8 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository r8 = r6.testTileDataRepository
            com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl r8 = (com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl) r8
            boolean r5 = r8.isFotaTest()
            if (r5 == 0) goto L63
            com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource r7 = r8.tileDataSource
            if (r7 == 0) goto L50
            r2 = r7
        L50:
            java.lang.String r7 = r2.getTiles()
            android.content.res.Resources r6 = r6.resources
            r4.getClass()
            com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter r8 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.INSTANCE
            r8.getClass()
            java.util.List r6 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.toTilesList(r6, r7)
            return r6
        L63:
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$2 r8 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$2
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.loadTilesFromSettings(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x007e, code lost:
    
        if (r2.send(r3, r0) == r1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0081, code lost:
    
        r0 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0097, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0095, code lost:
    
        if (r2.send(r4, r0) == r1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0057, code lost:
    
        if (r8 == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTilesFromSettingsAndParse(int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1 r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1 r0 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L4a
            if (r2 == r5) goto L40
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            goto L32
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            int r6 = r0.I$0
            java.lang.Object r7 = r0.L$1
            java.util.List r7 = (java.util.List) r7
            java.lang.Object r0 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L98
        L40:
            int r7 = r0.I$0
            java.lang.Object r6 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r6 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5a
        L4a:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.L$0 = r6
            r0.I$0 = r7
            r0.label = r5
            java.lang.Object r8 = r6.loadTilesFromSettings(r7, r0)
            if (r8 != r1) goto L5a
            goto L97
        L5a:
            java.util.List r8 = (java.util.List) r8
            r2 = r8
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L85
            kotlinx.coroutines.channels.BufferedChannel r2 = r6._tilesUpgradePath
            r3 = r8
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.util.Set r3 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r3)
            com.android.systemui.qs.pipeline.shared.TilesUpgradePath$ReadFromSettings r3 = com.android.systemui.qs.pipeline.shared.TilesUpgradePath.ReadFromSettings.m2896boximpl(r3)
            r0.L$0 = r6
            r0.L$1 = r8
            r0.I$0 = r7
            r0.label = r4
            java.lang.Object r0 = r2.send(r3, r0)
            if (r0 != r1) goto L81
            goto L97
        L81:
            r0 = r6
            r6 = r7
            r7 = r8
            goto L98
        L85:
            kotlinx.coroutines.channels.BufferedChannel r2 = r6._tilesUpgradePath
            com.android.systemui.qs.pipeline.shared.TilesUpgradePath$DefaultSet r4 = com.android.systemui.qs.pipeline.shared.TilesUpgradePath.DefaultSet.INSTANCE
            r0.L$0 = r6
            r0.L$1 = r8
            r0.I$0 = r7
            r0.label = r3
            java.lang.Object r0 = r2.send(r4, r0)
            if (r0 != r1) goto L81
        L97:
            return r1
        L98:
            r0.getClass()
            r8 = r7
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r8 = r8.isEmpty()
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r1 = r0.logger
            if (r8 != 0) goto Lab
            r8 = 0
            r1.logParsedTiles(r7, r8, r6)
            return r7
        Lab:
            com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository r7 = r0.defaultTilesRepository
            java.util.List r7 = r7.getDefaultTiles()
            r1.logParsedTiles(r7, r5, r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.loadTilesFromSettingsAndParse(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00a5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a6 A[RETURN] */
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
            boolean r0 = r9 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1 r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1 r0 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1
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
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r8 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r8
            java.lang.Object r0 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r0
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
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r2 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r2
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r5 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r5
            kotlin.ResultKt.throwOnFailure(r9)
            goto L69
        L4b:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.flow.StateFlow r9 = r8._tiles
            if (r9 != 0) goto La1
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
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$2 r6 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$2
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
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1 r1 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1
            r1.<init>(r9, r0, r3)
            kotlinx.coroutines.CoroutineScope r2 = r0.applicationScope
            r4 = 5
            kotlinx.coroutines.CoroutineDispatcher r5 = r0.backgroundDispatcher
            com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r2, r5, r3, r1, r4)
            r8._tiles = r9
            r8 = r0
        La1:
            kotlinx.coroutines.flow.StateFlow r8 = r8._tiles
            if (r8 != 0) goto La6
            return r3
        La6:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.tiles(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AddTile implements ChangeAction {
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
