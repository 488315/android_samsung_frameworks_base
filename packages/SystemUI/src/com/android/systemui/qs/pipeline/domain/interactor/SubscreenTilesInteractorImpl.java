package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQSTileInstanceManager;
import com.android.systemui.qs.TileFeatureChecker;
import com.android.systemui.qs.TileStateToProtoKt;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractor;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.DeviceType;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SubscreenTilesInteractorImpl implements CurrentTilesInteractor {
    public static final boolean DEBUG;
    public final StateFlowImpl _currentSpecsAndTiles;
    public final StateFlowImpl _userContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final StateFlowImpl currentBarTileList;
    public final ReadonlyStateFlow currentTiles;
    public final StateFlowImpl currentUser;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineFlagsRepository featureFlags;
    public final FotaUpdateInteractor fotaUpdateInteractor;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final QSPipelineLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MinimumTilesRepository minimumTilesRepository;
    public final RetailModeRepository retailModeRepository;
    public final CoroutineScope scope;
    public final Map specsToTiles;
    public final QSFactory tileFactory;
    public final TileFeatureChecker tileFeatureChecker;
    public final SecQSTileInstanceManager tileInstanceManager;
    public final TileSpecRepository tileSpecRepository;
    public final String tileUsingBySubScreen;
    public final SharedFlowImpl tilesUpdatedFlow;
    public final Flow userAndTiles;
    public final ReadonlyStateFlow userContext;
    public final ReadonlyStateFlow userId;
    public final UserRepository userRepository;
    public final UserTracker userTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TileOrNotInstalled {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class NotInstalled implements TileOrNotInstalled {
            public static final NotInstalled INSTANCE = new NotInstalled();

            private NotInstalled() {
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Tile implements TileOrNotInstalled {
            public final QSTile tile;

            private /* synthetic */ Tile(QSTile qSTile) {
                this.tile = qSTile;
            }

            /* renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Tile m2895boximpl(QSTile qSTile) {
                return new Tile(qSTile);
            }

            public final boolean equals(Object obj) {
                if (obj instanceof Tile) {
                    return Intrinsics.areEqual(this.tile, ((Tile) obj).tile);
                }
                return false;
            }

            public final int hashCode() {
                return this.tile.hashCode();
            }

            public final String toString() {
                return "Tile(tile=" + this.tile + ")";
            }
        }
    }

    static {
        new Companion(null);
        DEBUG = !DeviceType.isShipBuild();
    }

    public SubscreenTilesInteractorImpl(TileSpecRepository tileSpecRepository, InstalledTilesComponentRepository installedTilesComponentRepository, UserRepository userRepository, MinimumTilesRepository minimumTilesRepository, RetailModeRepository retailModeRepository, Lazy lazy, QSFactory qSFactory, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, QSPipelineLogger qSPipelineLogger, QSPipelineFlagsRepository qSPipelineFlagsRepository, BootAnimationFinishedCache bootAnimationFinishedCache, SecQSTileInstanceManager secQSTileInstanceManager, SecQSPanelResourcePicker secQSPanelResourcePicker, DefaultTilesRepository defaultTilesRepository, TileFeatureChecker tileFeatureChecker, FotaUpdateInteractor fotaUpdateInteractor) {
        this.tileSpecRepository = tileSpecRepository;
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userRepository = userRepository;
        this.minimumTilesRepository = minimumTilesRepository;
        this.retailModeRepository = retailModeRepository;
        this.tileFactory = qSFactory;
        this.userTracker = userTracker;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.scope = coroutineScope;
        this.logger = qSPipelineLogger;
        this.featureFlags = qSPipelineFlagsRepository;
        this.tileInstanceManager = secQSTileInstanceManager;
        this.defaultTilesRepository = defaultTilesRepository;
        this.tileFeatureChecker = tileFeatureChecker;
        this.fotaUpdateInteractor = fotaUpdateInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(emptyList);
        this._currentSpecsAndTiles = MutableStateFlow;
        this.currentTiles = FlowKt.asStateFlow(MutableStateFlow);
        this.specsToTiles = new LinkedHashMap();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(userTrackerImpl.getUserId()));
        this.currentUser = MutableStateFlow2;
        this.userId = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(userTrackerImpl.getUserContext());
        this._userContext = MutableStateFlow3;
        this.userContext = FlowKt.asStateFlow(MutableStateFlow3);
        this.tileUsingBySubScreen = "Subscreen";
        this.tilesUpdatedFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.currentBarTileList = StateFlowKt.MutableStateFlow(emptyList);
        this.userAndTiles = FlowKt.flowOn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(FlowKt.distinctUntilChanged(FlowKt.transformLatest(MutableStateFlow2, new SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1(null, this))), new UserTilesAndComponents(-1, emptyList, EmptySet.INSTANCE, null, 8, null), new SubscreenTilesInteractorImpl$userAndTiles$2(null)), coroutineDispatcher2);
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("SubscreenTilesInteractor", "SubscreenTilesInteractor not initialized for non-primary user, just return");
            return;
        }
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BootAnimationFinishedCacheImpl bootAnimationFinishedCacheImpl = (BootAnimationFinishedCacheImpl) bootAnimationFinishedCache;
            if (bootAnimationFinishedCacheImpl.bootAnimationFinished.get()) {
                BuildersKt.launch$default(coroutineScope, null, null, new SubscreenTilesInteractorImpl$startTileCollection$1(this, null), 3);
            } else {
                bootAnimationFinishedCacheImpl.addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl.1
                    @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                    public final void onBootAnimationFinished() {
                        boolean z = SubscreenTilesInteractorImpl.DEBUG;
                        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = SubscreenTilesInteractorImpl.this;
                        subscreenTilesInteractorImpl.getClass();
                        BuildersKt.launch$default(subscreenTilesInteractorImpl.scope, null, null, new SubscreenTilesInteractorImpl$startTileCollection$1(subscreenTilesInteractorImpl, null), 3);
                    }
                });
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$createTile(com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl r5, com.android.systemui.qs.pipeline.shared.TileSpec r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$1 r0 = (com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$1 r0 = new com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.qs.pipeline.shared.TileSpec r6 = (com.android.systemui.qs.pipeline.shared.TileSpec) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl r5 = (com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L53
        L34:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3c:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$tile$1 r7 = new com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$createTile$tile$1
            r7.<init>(r5, r6, r4)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r2 = r5.mainDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r2, r7, r0)
            if (r7 != r1) goto L53
            return r1
        L53:
            com.android.systemui.plugins.qs.QSTile r7 = (com.android.systemui.plugins.qs.QSTile) r7
            if (r7 != 0) goto L5d
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r5 = r5.logger
            r5.logTileNotFoundInFactory(r6)
            return r4
        L5d:
            boolean r0 = r7.isAvailable()
            if (r0 != 0) goto L7a
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r0 = r5.logger
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$TileDestroyedReason r1 = com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger.TileDestroyedReason.NEW_TILE_NOT_AVAILABLE
            r0.logTileDestroyed(r6, r1)
            boolean r0 = com.android.systemui.ScRune.QUICK_MANAGE_MULTI_QSHOST
            if (r0 == 0) goto L76
            com.android.systemui.qs.SecQSTileInstanceManager r7 = r5.tileInstanceManager
            java.lang.String r5 = r5.tileUsingBySubScreen
            r7.releaseTileUsing(r5, r6)
            return r4
        L76:
            r7.destroy()
            return r4
        L7a:
            com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger r5 = r5.logger
            r5.logTileCreated(r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl.access$createTile(com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl, com.android.systemui.qs.pipeline.shared.TileSpec, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void addTile(TileSpec tileSpec, int i) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BuildersKt.launch$default(this.scope, this.backgroundDispatcher, null, new SubscreenTilesInteractorImpl$addTile$1(this, tileSpec, i, null), 2);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile createTileSync(TileSpec tileSpec) {
        if (!ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            return null;
        }
        this.featureFlags.getClass();
        return this.tileFactory.createTile(tileSpec.getSpec());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SubscreenTileInteractorImpl:");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("User: ", this.userId.$$delegate_0.getValue(), printWriter);
        Iterable iterable = (Iterable) this.currentTiles.$$delegate_0.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile);
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (obj instanceof Dumpable) {
                arrayList2.add(obj);
            }
        }
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            ((Dumpable) obj2).dump(printWriter, strArr);
        }
    }

    @Override // com.android.systemui.ProtoDumpable
    public final void dumpProto(SystemUIProtoDump systemUIProtoDump) {
        Iterable iterable = (Iterable) this.currentTiles.$$delegate_0.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile.getState());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSTile.State state = (QSTile.State) obj;
            QsTileState proto = state != null ? TileStateToProtoKt.toProto(state) : null;
            if (proto != null) {
                arrayList2.add(proto);
            }
        }
        systemUIProtoDump.tiles = (QsTileState[]) arrayList2.toArray(new QsTileState[0]);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlowImpl getCurrentBarTileList() {
        return this.currentBarTileList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getCurrentTiles() {
        return this.currentTiles;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final List getDefaultTiles() {
        List defaultTiles = this.defaultTilesRepository.getDefaultTiles();
        ArrayList arrayList = new ArrayList();
        for (Object obj : defaultTiles) {
            if (this.tileFeatureChecker.isAvailableCustomTile((TileSpec) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final Flow getTilesUpdatedFlow() {
        return this.tilesUpdatedFlow;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getUserContext() {
        return this.userContext;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getUserId() {
        return this.userId;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isUnsupportedTile(TileSpec tileSpec) {
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        String string = ((Context) this.userContext.$$delegate_0.getValue()).getString(R.string.quick_settings_unsupported_tiles);
        tilesSettingConverter.getClass();
        if (!((ArrayList) TilesSettingConverter.toTilesList(string)).contains(tileSpec)) {
            return false;
        }
        Log.d("SubscreenTilesInteractor", "isUnsupportedTile " + tileSpec);
        return true;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void removeTiles(Collection collection) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            Set set = CollectionsKt___CollectionsKt.toSet(getCurrentTilesSpecs());
            int intValue = ((Number) this.currentUser.getValue()).intValue();
            Set set2 = set;
            Collection collection2 = collection;
            Set intersect = CollectionsKt___CollectionsKt.intersect(set2, collection2);
            ArrayList arrayList = new ArrayList();
            for (Object obj : intersect) {
                if (obj instanceof TileSpec.CustomTileSpec) {
                    arrayList.add(obj);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                QSTile requestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingBySubScreen, (TileSpec.CustomTileSpec) obj2);
                if (requestTileUsing instanceof CustomTile) {
                    TileLifecycleManager tileLifecycleManager = ((CustomTile) requestTileUsing).mServiceManager.mStateManager;
                    tileLifecycleManager.onStopListening();
                    tileLifecycleManager.onTileRemoved();
                    tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
                }
            }
            if (CollectionsKt___CollectionsKt.intersect(set2, collection2).isEmpty()) {
                return;
            }
            BuildersKt.launch$default(this.scope, null, null, new SubscreenTilesInteractorImpl$removeTiles$2(this, intValue, collection, null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void resetTiles() {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BuildersKt.launch$default(this.scope, null, null, new SubscreenTilesInteractorImpl$resetTiles$1(this, null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void setTiles(List list) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            List currentTilesSpecs = getCurrentTilesSpecs();
            int intValue = ((Number) this.currentUser.getValue()).intValue();
            if (currentTilesSpecs.equals(list)) {
                return;
            }
            List minus = CollectionsKt___CollectionsKt.minus((Iterable) currentTilesSpecs, (Iterable) list);
            ArrayList arrayList = new ArrayList();
            for (Object obj : minus) {
                if (obj instanceof TileSpec.CustomTileSpec) {
                    arrayList.add(obj);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                QSTile requestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingBySubScreen, (TileSpec.CustomTileSpec) obj2);
                if (requestTileUsing instanceof CustomTile) {
                    TileLifecycleManager tileLifecycleManager = ((CustomTile) requestTileUsing).mServiceManager.mStateManager;
                    tileLifecycleManager.onStopListening();
                    tileLifecycleManager.onTileRemoved();
                    tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
                }
            }
            BuildersKt.launch$default(this.scope, null, null, new SubscreenTilesInteractorImpl$setTiles$2(this, intValue, list, null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void refreshCurrentTiles() {
    }
}
