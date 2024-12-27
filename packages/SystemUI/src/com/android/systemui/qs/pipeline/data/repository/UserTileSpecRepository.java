package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class UserTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _tiles;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineLogger logger;
    public final SecureSettings secureSettings;
    public final int userId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        UserTileSpecRepository create(int i);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RestoreTiles {
        public final Set currentAutoAdded;
        public final RestoreData restoreData;

        public RestoreTiles(RestoreData restoreData, Set<? extends TileSpec> set) {
            this.restoreData = restoreData;
            this.currentAutoAdded = set;
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

    public UserTileSpecRepository(int i, DefaultTilesRepository defaultTilesRepository, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userId = i;
        this.defaultTilesRepository = defaultTilesRepository;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTilesFromSettings(int r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1
            if (r0 == 0) goto L13
            r0 = r6
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
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion r4 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.Companion) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4c
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$2 r6 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$2
            r2 = 0
            r6.<init>(r4, r5, r2)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion r5 = com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.Companion
            r0.L$0 = r5
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r4, r6, r0)
            if (r6 != r1) goto L4b
            return r1
        L4b:
            r4 = r5
        L4c:
            java.lang.String r6 = (java.lang.String) r6
            r4.getClass()
            com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter r4 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.INSTANCE
            r4.getClass()
            java.util.List r4 = com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter.toTilesList(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.loadTilesFromSettings(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTilesFromSettingsAndParse(int r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1
            if (r0 == 0) goto L13
            r0 = r7
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
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            int r6 = r0.I$0
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r5 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L45
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.I$0 = r6
            r0.label = r3
            java.lang.Object r7 = r5.loadTilesFromSettings(r6, r0)
            if (r7 != r1) goto L45
            return r1
        L45:
            java.util.List r7 = (java.util.List) r7
            r5.getClass()
            r0 = r7
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            r0 = r0 ^ r3
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r1 = r5.logger
            if (r0 == 0) goto L5b
            r5 = 0
            r1.logParsedTiles(r7, r5, r6)
            goto Lb6
        L5b:
            com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository r5 = r5.defaultTilesRepository
            com.android.systemui.qs.pipeline.data.repository.DefaultTilesQSHostRepository r5 = (com.android.systemui.qs.pipeline.data.repository.DefaultTilesQSHostRepository) r5
            android.content.res.Resources r5 = r5.resources
            java.util.List r5 = com.android.systemui.qs.QSHost.getDefaultSpecs(r5)
            com.android.systemui.qs.pipeline.shared.TileSpec$Companion r7 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion
            java.util.ArrayList r0 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r5, r2)
            r0.<init>(r2)
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            java.util.Iterator r5 = r5.iterator()
        L78:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L8f
            java.lang.Object r2 = r5.next()
            java.lang.String r2 = (java.lang.String) r2
            r7.getClass()
            com.android.systemui.qs.pipeline.shared.TileSpec r2 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion.create(r2)
            r0.add(r2)
            goto L78
        L8f:
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Iterator r5 = r0.iterator()
        L98:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto Lb3
            java.lang.Object r0 = r5.next()
            r2 = r0
            com.android.systemui.qs.pipeline.shared.TileSpec r2 = (com.android.systemui.qs.pipeline.shared.TileSpec) r2
            com.android.systemui.qs.pipeline.shared.TileSpec$Invalid r4 = com.android.systemui.qs.pipeline.shared.TileSpec.Invalid.INSTANCE
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
            r2 = r2 ^ 1
            if (r2 == 0) goto L98
            r7.add(r0)
            goto L98
        Lb3:
            r1.logParsedTiles(r7, r3, r6)
        Lb6:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.loadTilesFromSettingsAndParse(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0089 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tiles(kotlin.coroutines.Continuation r9) {
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
            if (r9 != 0) goto La0
            kotlinx.coroutines.flow.SharedFlowImpl r9 = r8.changeEvents
            r0.L$0 = r8
            r0.L$1 = r8
            r0.L$2 = r9
            r0.label = r5
            int r2 = r8.userId
            java.lang.Object r2 = r8.loadTilesFromSettingsAndParse(r2, r0)
            if (r2 != r1) goto L65
            return r1
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
            return r1
        L8a:
            r8 = r2
            r0 = r5
        L8c:
            kotlinx.coroutines.flow.StateFlow r9 = (kotlinx.coroutines.flow.StateFlow) r9
            r0.getClass()
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1 r1 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1
            r1.<init>(r9, r0, r3)
            kotlinx.coroutines.CoroutineDispatcher r2 = r0.backgroundDispatcher
            kotlinx.coroutines.CoroutineScope r5 = r0.applicationScope
            kotlinx.coroutines.BuildersKt.launch$default(r5, r2, r3, r1, r4)
            r8._tiles = r9
            r8 = r0
        La0:
            kotlinx.coroutines.flow.StateFlow r8 = r8._tiles
            if (r8 != 0) goto La5
            goto La6
        La5:
            r3 = r8
        La6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.tiles(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
