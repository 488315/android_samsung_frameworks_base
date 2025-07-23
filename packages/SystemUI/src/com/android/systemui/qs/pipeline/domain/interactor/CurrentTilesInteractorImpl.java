package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
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
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersisterImpl;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.external.TileServiceKey;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedSharedPrefsRepository;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.DeviceType;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl implements CurrentTilesInteractor {
    public final StateFlowImpl _currentBarTileList;
    public final StateFlowImpl _currentSpecsAndTiles;
    public final StateFlowImpl _userContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public List bottomBarTileList;
    public List brightnessVolumeBarTileList;
    public final StateFlowImpl currentBarTileList;
    public final ReadonlyStateFlow currentTiles;
    public final StateFlowImpl currentUser;
    public final CustomTileAddedRepository customTileAddedRepository;
    public final CustomTileStatePersister customTileStatePersister;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineFlagsRepository featureFlags;
    public final SharedFlowImpl forceUIUpdate;
    public final FotaUpdateInteractor fotaUpdateInteractor;
    public final ArrayList hiddenTilesByKnoxInTopBottomBar;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final KnoxPolicyTilesRepository knoxPolicyTilesRepository;
    public final QSPipelineLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MinimumTilesRepository minimumTilesRepository;
    public final SharedFlowImpl refreshTiles;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 refreshUserAndTiles;
    public final RemovedTilesInteractor removedTilesInteractor;
    public final SecQSPanelResourcePicker resourcePicker;
    public final RetailModeRepository retailModeRepository;
    public final CoroutineScope scope;
    public List smartViewBarTileList;
    public final Map specsToTiles;
    public final QSFactory tileFactory;
    public final TileFeatureChecker tileFeatureChecker;
    public final SecQSTileInstanceManager tileInstanceManager;
    public final TileLifecycleManager.Factory tileLifecycleManagerFactory;
    public final TileSpecRepository tileSpecRepository;
    public final String tileUsingByBar;
    public final String tileUsingByPanel;
    public final Flow tilesUpdatedFlow;
    public List topBarTile;
    public final ReadonlyStateFlow userContext;
    public final ReadonlyStateFlow userId;
    public final UserRepository userRepository;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = !DeviceType.isShipBuild();
    public static final int BAR_TILE_NUM_DEFAULT = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final List access$toTileList(Companion companion, List list, Resources resources) {
            companion.getClass();
            List<String> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (String str : list2) {
                TileSpec.Companion companion2 = TileSpec.Companion;
                TileNameConverter.INSTANCE.getClass();
                String tileSpec = TileNameConverter.toTileSpec(resources, str);
                companion2.getClass();
                arrayList.add(TileSpec.Companion.create(tileSpec));
            }
            return arrayList;
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
            public static final /* synthetic */ Tile m2893boximpl(QSTile qSTile) {
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

    public CurrentTilesInteractorImpl(TileSpecRepository tileSpecRepository, InstalledTilesComponentRepository installedTilesComponentRepository, UserRepository userRepository, MinimumTilesRepository minimumTilesRepository, RetailModeRepository retailModeRepository, CustomTileStatePersister customTileStatePersister, Lazy lazy, QSFactory qSFactory, CustomTileAddedRepository customTileAddedRepository, TileLifecycleManager.Factory factory, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, QSPipelineLogger qSPipelineLogger, QSPipelineFlagsRepository qSPipelineFlagsRepository, BootAnimationFinishedCache bootAnimationFinishedCache, SecQSTileInstanceManager secQSTileInstanceManager, SecQSPanelResourcePicker secQSPanelResourcePicker, KnoxPolicyTilesRepository knoxPolicyTilesRepository, DefaultTilesRepository defaultTilesRepository, RemovedTilesInteractor removedTilesInteractor, TileFeatureChecker tileFeatureChecker, FotaUpdateInteractor fotaUpdateInteractor) {
        this.tileSpecRepository = tileSpecRepository;
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userRepository = userRepository;
        this.minimumTilesRepository = minimumTilesRepository;
        this.retailModeRepository = retailModeRepository;
        this.customTileStatePersister = customTileStatePersister;
        this.tileFactory = qSFactory;
        this.customTileAddedRepository = customTileAddedRepository;
        this.tileLifecycleManagerFactory = factory;
        this.userTracker = userTracker;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.scope = coroutineScope;
        this.logger = qSPipelineLogger;
        this.featureFlags = qSPipelineFlagsRepository;
        this.tileInstanceManager = secQSTileInstanceManager;
        this.resourcePicker = secQSPanelResourcePicker;
        this.knoxPolicyTilesRepository = knoxPolicyTilesRepository;
        this.defaultTilesRepository = defaultTilesRepository;
        this.removedTilesInteractor = removedTilesInteractor;
        this.tileFeatureChecker = tileFeatureChecker;
        this.fotaUpdateInteractor = fotaUpdateInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(emptyList);
        this._currentSpecsAndTiles = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.currentTiles = asStateFlow;
        this.specsToTiles = new LinkedHashMap();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(userTrackerImpl.getUserId()));
        this.currentUser = MutableStateFlow2;
        this.userId = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(userTrackerImpl.getUserContext());
        this._userContext = MutableStateFlow3;
        this.userContext = FlowKt.asStateFlow(MutableStateFlow3);
        this.topBarTile = new ArrayList();
        this.brightnessVolumeBarTileList = new ArrayList();
        this.bottomBarTileList = new ArrayList();
        this.smartViewBarTileList = new ArrayList();
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.forceUIUpdate = MutableSharedFlow$default;
        this.tileUsingByBar = "Bar";
        this.tileUsingByPanel = "Panel";
        this.tilesUpdatedFlow = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CurrentTilesInteractorImpl$tilesUpdatedFlow$4(this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesRepository).knoxBlockedTiles, new CurrentTilesInteractorImpl$tilesUpdatedFlow$1(this, null)), asStateFlow, new CurrentTilesInteractorImpl$tilesUpdatedFlow$2(null)), MutableSharedFlow$default, new CurrentTilesInteractorImpl$tilesUpdatedFlow$3(null))), coroutineDispatcher2);
        this.hiddenTilesByKnoxInTopBottomBar = new ArrayList();
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(emptyList);
        this._currentBarTileList = MutableStateFlow4;
        this.currentBarTileList = MutableStateFlow4;
        Flow flowOn = FlowKt.flowOn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(FlowKt.distinctUntilChanged(FlowKt.transformLatest(MutableStateFlow2, new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1(null, this))), new UserTilesAndComponents(-1, emptyList, EmptySet.INSTANCE, null, 8, null), new CurrentTilesInteractorImpl$userAndTiles$2(null)), coroutineDispatcher2);
        SharedFlowImpl MutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.refreshTiles = MutableSharedFlow$default2;
        this.refreshUserAndTiles = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CurrentTilesInteractorImpl$refreshUserAndTiles$2(this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowOn, MutableSharedFlow$default2, new CurrentTilesInteractorImpl$refreshUserAndTiles$1(null)));
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("CurrentTilesInteractor", "CurrentTilesInteractor not initialized for non-primary user, just return");
            return;
        }
        BootAnimationFinishedCacheImpl bootAnimationFinishedCacheImpl = (BootAnimationFinishedCacheImpl) bootAnimationFinishedCache;
        if (bootAnimationFinishedCacheImpl.bootAnimationFinished.get()) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new CurrentTilesInteractorImpl$startTileCollection$1(this, null), 7);
        } else {
            bootAnimationFinishedCacheImpl.addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl.1
                @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                public final void onBootAnimationFinished() {
                    Companion companion = CurrentTilesInteractorImpl.Companion;
                    CurrentTilesInteractorImpl currentTilesInteractorImpl = CurrentTilesInteractorImpl.this;
                    currentTilesInteractorImpl.getClass();
                    CoroutineTracingKt.launchTraced$default(currentTilesInteractorImpl.scope, null, null, new CurrentTilesInteractorImpl$startTileCollection$1(currentTilesInteractorImpl, null), 7);
                }
            });
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
    public static final java.lang.Object access$createTile(com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r5, com.android.systemui.qs.pipeline.shared.TileSpec r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$1 r0 = (com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$1 r0 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$1
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
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl r5 = (com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L53
        L34:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3c:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$tile$1 r7 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$createTile$tile$1
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
            java.lang.String r5 = r5.tileUsingByPanel
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl.access$createTile(com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl, com.android.systemui.qs.pipeline.shared.TileSpec, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void addTile(TileSpec tileSpec, int i) {
        CoroutineTracingKt.launchTraced$default(this.scope, this.backgroundDispatcher, null, new CurrentTilesInteractorImpl$addTile$1(this, tileSpec, i, null), 5);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile createTileSync(TileSpec tileSpec) {
        this.featureFlags.getClass();
        return this.tileFactory.createTile(tileSpec.getSpec());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CurrentTileInteractorImpl:");
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
        printWriter.println("KnoxBlockedTileList:");
        printWriter.println("   " + ((List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()));
        SecQSTileInstanceManager secQSTileInstanceManager = this.tileInstanceManager;
        secQSTileInstanceManager.getClass();
        printWriter.println("SecQSTileInstanceManager:");
        printWriter.println("  mTileInstances[" + secQSTileInstanceManager.mTileInstances.size() + "] : " + secQSTileInstanceManager.mTileInstances);
        StringBuilder sb = new StringBuilder("  mTileUsingHosts : ");
        sb.append(secQSTileInstanceManager.mTileUsingHosts);
        printWriter.println(sb.toString());
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
    public final TileModel getBarTileBySpecString() {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create("custom(com.samsung.android.mydevice/.quicksettings.MyDeviceTileService)");
        Iterator it = Arrays.asList(this.topBarTile, this.brightnessVolumeBarTileList, this.bottomBarTileList, this.smartViewBarTileList).iterator();
        while (it.hasNext()) {
            for (TileModel tileModel : (List) it.next()) {
                if (Intrinsics.areEqual(tileModel.spec, create)) {
                    return tileModel;
                }
            }
        }
        return null;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final ArrayList getBarTilesByType(int i, int i2) {
        int i3;
        String topBarTileList;
        String m;
        int i4;
        QSTile requestTileUsing;
        Collection collection;
        int i5;
        Collection collection2;
        int i6;
        int i7;
        Collection collection3;
        Collection collection4;
        int i8;
        int i9;
        int i10;
        String[] strArr;
        int i11;
        ArrayList arrayList = new ArrayList();
        List list = (List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        ReadonlyStateFlow readonlyStateFlow = this.userContext;
        if (i2 != 0) {
            m = "";
            if (i2 == 1) {
                i3 = 1;
                topBarTileList = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getString(R.string.sec_brightness_volume_bar_tiles_default);
                Unit unit = Unit.INSTANCE;
            } else if (i2 == 2) {
                i3 = 1;
                List split = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                if (!split.isEmpty()) {
                    ListIterator listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (((String) listIterator.previous()).length() != 0) {
                            collection2 = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                collection2 = EmptyList.INSTANCE;
                String[] strArr2 = (String[]) collection2.toArray(new String[0]);
                int length = strArr2.length;
                topBarTileList = "";
                int i12 = 0;
                while (i12 < length) {
                    String str = strArr2[i12];
                    str.getClass();
                    int length2 = str.length() - 1;
                    String[] strArr3 = strArr2;
                    int i13 = 0;
                    boolean z = false;
                    while (true) {
                        i6 = length;
                        if (i13 > length2) {
                            i7 = i12;
                            break;
                        }
                        i7 = i12;
                        boolean z2 = Intrinsics.compare(str.charAt(!z ? i13 : length2), 32) <= 0;
                        if (z) {
                            if (!z2) {
                                break;
                            }
                            length2--;
                        } else if (z2) {
                            i13++;
                        } else {
                            z = true;
                        }
                        length = i6;
                        i12 = i7;
                    }
                    if (str.subSequence(i13, length2 + 1).toString().length() != 0) {
                        TileSpec.Companion.getClass();
                        TileSpec create = TileSpec.Companion.create(str);
                        List list2 = this.topBarTile;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(((TileModel) it.next()).spec);
                        }
                        if (!arrayList2.contains(create)) {
                            List list3 = this.smartViewBarTileList;
                            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                            Iterator it2 = list3.iterator();
                            while (it2.hasNext()) {
                                arrayList3.add(((TileModel) it2.next()).spec);
                            }
                            if (!arrayList3.contains(create)) {
                                topBarTileList = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(topBarTileList, str, ",");
                            }
                            i12 = i7 + 1;
                            strArr2 = strArr3;
                            length = i6;
                        }
                    }
                    i12 = i7 + 1;
                    strArr2 = strArr3;
                    length = i6;
                }
                Unit unit2 = Unit.INSTANCE;
            } else {
                if (i2 != 3) {
                    return null;
                }
                List split2 = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getSmartViewBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                if (!split2.isEmpty()) {
                    ListIterator listIterator2 = split2.listIterator(split2.size());
                    while (listIterator2.hasPrevious()) {
                        if (((String) listIterator2.previous()).length() != 0) {
                            collection3 = CollectionsKt___CollectionsKt.take(split2, listIterator2.nextIndex() + 1);
                            break;
                        }
                    }
                }
                collection3 = EmptyList.INSTANCE;
                String[] strArr4 = (String[]) collection3.toArray(new String[0]);
                int length3 = strArr4.length;
                i3 = 1;
                int i14 = 0;
                topBarTileList = "";
                while (i14 < length3) {
                    String str2 = strArr4[i14];
                    str2.getClass();
                    int length4 = str2.length() - 1;
                    int i15 = 0;
                    boolean z3 = false;
                    while (true) {
                        i10 = i14;
                        if (i15 > length4) {
                            strArr = strArr4;
                            break;
                        }
                        strArr = strArr4;
                        boolean z4 = Intrinsics.compare(str2.charAt(!z3 ? i15 : length4), 32) <= 0;
                        if (z3) {
                            if (!z4) {
                                break;
                            }
                            length4--;
                        } else if (z4) {
                            i15++;
                        } else {
                            z3 = true;
                        }
                        i14 = i10;
                        strArr4 = strArr;
                    }
                    if (str2.subSequence(i15, length4 + 1).toString().length() == 0) {
                        i11 = length3;
                    } else {
                        TileSpec.Companion.getClass();
                        TileSpec create2 = TileSpec.Companion.create(str2);
                        List list4 = this.topBarTile;
                        i11 = length3;
                        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                        Iterator it3 = list4.iterator();
                        while (it3.hasNext()) {
                            arrayList4.add(((TileModel) it3.next()).spec);
                        }
                        if (!arrayList4.contains(create2)) {
                            topBarTileList = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(topBarTileList, str2, ",");
                        }
                    }
                    i14 = i10 + 1;
                    length3 = i11;
                    strArr4 = strArr;
                }
                List split3 = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                if (!split3.isEmpty()) {
                    ListIterator listIterator3 = split3.listIterator(split3.size());
                    while (listIterator3.hasPrevious()) {
                        if (((String) listIterator3.previous()).length() != 0) {
                            collection4 = CollectionsKt___CollectionsKt.take(split3, listIterator3.nextIndex() + 1);
                            break;
                        }
                    }
                }
                collection4 = EmptyList.INSTANCE;
                String[] strArr5 = (String[]) collection4.toArray(new String[0]);
                int length5 = strArr5.length;
                int i16 = 0;
                while (i16 < length5) {
                    String str3 = strArr5[i16];
                    str3.getClass();
                    int length6 = str3.length() - 1;
                    String[] strArr6 = strArr5;
                    int i17 = 0;
                    boolean z5 = false;
                    while (true) {
                        i8 = length5;
                        if (i17 > length6) {
                            i9 = i16;
                            break;
                        }
                        i9 = i16;
                        boolean z6 = Intrinsics.compare(str3.charAt(!z5 ? i17 : length6), 32) <= 0;
                        if (z5) {
                            if (!z6) {
                                break;
                            }
                            length6--;
                        } else if (z6) {
                            i17++;
                        } else {
                            z5 = true;
                        }
                        length5 = i8;
                        i16 = i9;
                    }
                    if (str3.subSequence(i17, length6 + 1).toString().length() != 0) {
                        TileSpec.Companion.getClass();
                        TileSpec create3 = TileSpec.Companion.create(str3);
                        List list5 = this.topBarTile;
                        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
                        Iterator it4 = list5.iterator();
                        while (it4.hasNext()) {
                            arrayList5.add(((TileModel) it4.next()).spec);
                        }
                        if (!arrayList5.contains(create3)) {
                            m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, str3, ",");
                        }
                    }
                    i16 = i9 + 1;
                    strArr5 = strArr6;
                    length5 = i8;
                }
                Unit unit3 = Unit.INSTANCE;
            }
        } else {
            i3 = 1;
            topBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue());
            StateFlow stateFlow = readonlyStateFlow.$$delegate_0;
            Context context = (Context) stateFlow.getValue();
            SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
            m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(secQSPanelResourcePickHelper.getTargetPicker().getSmartViewBarTileList(i, context), ",", secQSPanelResourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) stateFlow.getValue()));
            Unit unit4 = Unit.INSTANCE;
        }
        ArrayList arrayList6 = new ArrayList();
        List split4 = new Regex(",").split(topBarTileList);
        ArrayList arrayList7 = new ArrayList();
        for (Object obj : split4) {
            if (((String) obj).length() > 0) {
                arrayList7.add(obj);
            }
        }
        int i18 = 0;
        for (String str4 : (String[]) arrayList7.toArray(new String[0])) {
            str4.getClass();
            TileSpec.Companion.getClass();
            TileSpec create4 = TileSpec.Companion.create(str4);
            if ((this.hiddenTilesByKnoxInTopBottomBar.isEmpty() || !list.contains(create4)) && (!(create4 instanceof TileSpec.CustomTileSpec) || isAvailableBarTile(create4))) {
                arrayList6.add(create4);
            } else {
                i18++;
            }
        }
        if (i2 == 3 && i18 == 0 && arrayList6.size() == (i5 = i3)) {
            i18 += i5;
        }
        int topBarTileNum = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileNum(i, (Context) readonlyStateFlow.$$delegate_0.getValue());
        boolean z7 = ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") != 1;
        if (i2 == 0 && topBarTileNum > BAR_TILE_NUM_DEFAULT) {
            TileSpec.Companion.getClass();
            if (isAvailableBarTile(TileSpec.Companion.create("custom(com.samsung.android.smartmirroring/.tile.SmartMirroringTile)")) && !z7) {
                i18++;
            }
        }
        if (i18 >= topBarTileNum || i18 <= 0 || m.length() <= 0) {
            i4 = 0;
        } else {
            List split5 = new Regex(",").split(m);
            if (!split5.isEmpty()) {
                ListIterator listIterator4 = split5.listIterator(split5.size());
                while (listIterator4.hasPrevious()) {
                    if (((String) listIterator4.previous()).length() != 0) {
                        collection = CollectionsKt___CollectionsKt.take(split5, listIterator4.nextIndex() + 1);
                        break;
                    }
                }
            }
            collection = EmptyList.INSTANCE;
            i4 = 0;
            for (String str5 : (String[]) collection.toArray(new String[0])) {
                str5.getClass();
                TileSpec.Companion.getClass();
                TileSpec create5 = TileSpec.Companion.create(str5);
                boolean z8 = false;
                int length7 = str5.length() - 1;
                int i19 = 0;
                while (i19 <= length7) {
                    boolean z9 = Intrinsics.compare(str5.charAt(!z8 ? i19 : length7), 32) <= 0;
                    if (z8) {
                        if (!z9) {
                            break;
                        }
                        length7--;
                    } else if (z9) {
                        i19++;
                    } else {
                        z8 = true;
                    }
                }
                if (str5.subSequence(i19, length7 + 1).toString().length() != 0 && !arrayList6.contains(create5) && isAvailableBarTile(create5)) {
                    arrayList6.add(create5);
                    if (arrayList6.size() == topBarTileNum) {
                        break;
                    }
                }
            }
        }
        ArrayList arrayList8 = new ArrayList();
        int size = arrayList6.size();
        int i20 = i4;
        while (i20 < size) {
            Object obj2 = arrayList6.get(i20);
            i20++;
            TileSpec tileSpec = (TileSpec) obj2;
            if (!(tileSpec instanceof TileSpec.Invalid) && (requestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingByBar, tileSpec)) != null) {
                arrayList.add(requestTileUsing);
                arrayList8.add(new TileModel(tileSpec, requestTileUsing));
            }
        }
        if (i2 == 0) {
            this.topBarTile = arrayList8;
        } else if (i2 == 1) {
            this.brightnessVolumeBarTileList = arrayList8;
        } else if (i2 == 2) {
            if (!this.bottomBarTileList.equals(arrayList8)) {
                List plus = CollectionsKt___CollectionsKt.plus((Iterable) this.smartViewBarTileList, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) this.bottomBarTileList, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) this.brightnessVolumeBarTileList, (Collection) this.topBarTile)));
                Log.d("CurrentTilesInteractor", "updateCurrentBarTileList= " + plus);
                this._currentBarTileList.updateState(null, plus);
            }
            this.bottomBarTileList = arrayList8;
        } else if (i2 == 3) {
            this.smartViewBarTileList = arrayList8;
        }
        StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(i2, arrayList.size(), "getBarTilesByType type=", ", tiles.size =", ", tiles=");
        m2.append(arrayList);
        Log.d("CurrentTilesInteractor", m2.toString());
        return arrayList;
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
            TileSpec tileSpec = (TileSpec) obj;
            if (!isBarTile(tileSpec) && this.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile getTileBySpecString(String str) {
        if (StringsKt__StringsKt.contains(((List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()).toString(), str, false)) {
            return null;
        }
        TileSpec.Companion.getClass();
        return this.tileInstanceManager.requestTileUsing(this.tileUsingByBar, TileSpec.Companion.create(str));
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

    public final boolean isAvailableBarTile(TileSpec tileSpec) {
        List list;
        if (!this.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
            return false;
        }
        InstalledTilesComponentRepository installedTilesComponentRepository = this.installedTilesComponentRepository;
        int userId = ((UserTrackerImpl) this.userTracker).getUserId();
        InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = (InstalledTilesComponentRepositoryImpl) installedTilesComponentRepository;
        synchronized (installedTilesComponentRepositoryImpl.userMap) {
            list = (List) installedTilesComponentRepositoryImpl.getForUserLocked(userId).getValue();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((ServiceInfo) it.next()).getComponentName());
        }
        if (tileSpec instanceof TileSpec.CustomTileSpec) {
            return linkedHashSet.contains(((TileSpec.CustomTileSpec) tileSpec).componentName);
        }
        return true;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isBarTile(TileSpec tileSpec) {
        ReadonlyStateFlow readonlyStateFlow = this.userContext;
        Context context = (Context) readonlyStateFlow.$$delegate_0.getValue();
        int i = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        String topBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, context);
        String string = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getString(R.string.sec_brightness_volume_bar_tiles_default);
        Context context2 = (Context) readonlyStateFlow.$$delegate_0.getValue();
        int i2 = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation;
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        String smartViewBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getSmartViewBarTileList(i2, context2);
        Context context3 = (Context) readonlyStateFlow.$$delegate_0.getValue();
        String bottomBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getBottomBarTileList(((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation, context3);
        StringBuilder sb = new StringBuilder();
        sb.append(topBarTileList);
        sb.append(",");
        sb.append(string);
        sb.append(",");
        sb.append(smartViewBarTileList);
        return CollectionsKt___CollectionsKt.toHashSet(Companion.access$toTileList(Companion, StringsKt__StringsKt.split$default(TransitionKt$$ExternalSyntheticOutline0.m(sb, ",", bottomBarTileList), new String[]{","}, 0, 6), ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources())).contains(tileSpec);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isLargeBarTile(String str) {
        if (str == null) {
            return false;
        }
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(str);
        List list = this.topBarTile;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).spec);
        }
        if (arrayList.contains(create)) {
            return true;
        }
        List list2 = this.bottomBarTileList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((TileModel) it2.next()).spec);
        }
        if (arrayList2.contains(create)) {
            return true;
        }
        List list3 = this.smartViewBarTileList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it3 = list3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((TileModel) it3.next()).spec);
        }
        return arrayList3.contains(create);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isUnsupportedTile(TileSpec tileSpec) {
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        String string = ((Context) this.userContext.$$delegate_0.getValue()).getString(R.string.quick_settings_unsupported_tiles);
        tilesSettingConverter.getClass();
        if (!((ArrayList) TilesSettingConverter.toTilesList(string)).contains(tileSpec)) {
            return false;
        }
        Log.d("CurrentTilesInteractor", "isUnsupportedTile " + tileSpec);
        return true;
    }

    public final void onCustomTileRemoved(TileSpec.CustomTileSpec customTileSpec, ComponentName componentName, int i) {
        boolean z = ScRune.QUICK_MANAGE_MULTI_QSHOST;
        CustomTileAddedRepository customTileAddedRepository = this.customTileAddedRepository;
        CustomTileStatePersister customTileStatePersister = this.customTileStatePersister;
        if (!z) {
            TileLifecycleManager create = this.tileLifecycleManagerFactory.create(new Intent().setComponent(componentName), UserHandle.of(i));
            create.onStopListening();
            create.onTileRemoved();
            ((CustomTileStatePersisterImpl) customTileStatePersister).sharedPreferences.edit().remove(new TileServiceKey(componentName, i).string).apply();
            ((CustomTileAddedSharedPrefsRepository) customTileAddedRepository).setTileAdded(componentName, false, i);
            create.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(create, 3));
            return;
        }
        QSTile requestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingByPanel, customTileSpec);
        if (requestTileUsing instanceof CustomTile) {
            TileLifecycleManager tileLifecycleManager = ((CustomTile) requestTileUsing).mServiceManager.mStateManager;
            tileLifecycleManager.onStopListening();
            tileLifecycleManager.onTileRemoved();
            tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
            ((CustomTileStatePersisterImpl) customTileStatePersister).sharedPreferences.edit().remove(new TileServiceKey(componentName, i).string).apply();
            ((CustomTileAddedSharedPrefsRepository) customTileAddedRepository).setTileAdded(componentName, false, i);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void refreshCurrentTiles() {
        Log.d("CurrentTilesInteractor", "refreshCurrentTiles");
        this.refreshTiles.tryEmit(Unit.INSTANCE);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void removeTiles(Collection collection) {
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
            TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) obj2;
            onCustomTileRemoved(customTileSpec, customTileSpec.componentName, intValue);
        }
        if (CollectionsKt___CollectionsKt.intersect(set2, collection2).isEmpty()) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new CurrentTilesInteractorImpl$removeTiles$2(this, intValue, collection, null), 7);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void resetTiles() {
        ((RemovedTilesInteractorImpl) this.removedTilesInteractor).resetRemovedTiles();
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new CurrentTilesInteractorImpl$resetTiles$1(this, null), 7);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void setTiles(List list) {
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
            TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) obj2;
            onCustomTileRemoved(customTileSpec, customTileSpec.componentName, intValue);
        }
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new CurrentTilesInteractorImpl$setTiles$2(this, intValue, list, null), 7);
    }
}
