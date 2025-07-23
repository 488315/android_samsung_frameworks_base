package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileRequestDialogEventLogger;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.external.TileServiceRequestController$$ExternalSyntheticLambda0;
import com.android.systemui.qs.pipeline.data.domain.interactor.KnoxPolicyTilesInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.KnoxPolicyTilesInteractorImpl;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl;
import com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSHostAdapter implements QSHost {
    public final Map callbacksMap = new LinkedHashMap();
    public final Context context;
    public final CurrentTilesInteractor interactor;
    public final KnoxPolicyTilesInteractor knoxPolicyTilesInteractor;
    public final RemovedTilesInteractor removedInteractor;
    public final CoroutineScope scope;
    public final TileFeatureChecker tileFeatureChecker;
    public final TileSALogHelper tileSALogHelper;
    public final TileSearchInteractor tileSearchInteractor;
    public final TileServiceRequestController.Builder tileServiceRequestControllerBuilder;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.QSHostAdapter$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSHostAdapter.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            QSHostAdapter qSHostAdapter = QSHostAdapter.this;
            TileServiceRequestController.Builder builder = qSHostAdapter.tileServiceRequestControllerBuilder;
            builder.getClass();
            TileServiceRequestController tileServiceRequestController = new TileServiceRequestController(qSHostAdapter, builder.commandQueue, builder.commandRegistry, new TileRequestDialogEventLogger(), builder.iUriGrantsManager, builder.tileRequestDialogComposeDelegateFactory, null, 64, null);
            tileServiceRequestController.commandRegistry.registerCommand("tile-service-add", new TileServiceRequestController$$ExternalSyntheticLambda0(tileServiceRequestController, 0));
            tileServiceRequestController.commandQueue.addCallback((CommandQueue.Callbacks) tileServiceRequestController.commandQueueCallback);
            return Unit.INSTANCE;
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

    static {
        new Companion(null);
    }

    public QSHostAdapter(CurrentTilesInteractor currentTilesInteractor, Context context, TileServiceRequestController.Builder builder, CoroutineScope coroutineScope, DumpManager dumpManager, TileFeatureChecker tileFeatureChecker, RemovedTilesInteractor removedTilesInteractor, TileSearchInteractor tileSearchInteractor, KnoxPolicyTilesInteractor knoxPolicyTilesInteractor, TileSALogHelper tileSALogHelper) {
        this.interactor = currentTilesInteractor;
        this.context = context;
        this.tileServiceRequestControllerBuilder = builder;
        this.scope = coroutineScope;
        this.tileFeatureChecker = tileFeatureChecker;
        this.removedInteractor = removedTilesInteractor;
        this.tileSearchInteractor = tileSearchInteractor;
        this.knoxPolicyTilesInteractor = knoxPolicyTilesInteractor;
        this.tileSALogHelper = tileSALogHelper;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
        dumpManager.registerCriticalDumpable("QSTileHost", currentTilesInteractor);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        StandaloneCoroutine launchTraced$default = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new QSHostAdapter$addCallback$job$1(this, callback, null), 7);
        synchronized (this.callbacksMap) {
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        TileSpec.Companion.getClass();
        this.interactor.addTile(TileSpec.Companion.create(componentName), z ? -1 : 0);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2) {
        changeTilesByUser(list, list2, false);
    }

    @Override // com.android.systemui.qs.QSHost
    public final QSTile createTile(String str) {
        TileSpec.Companion.getClass();
        return this.interactor.createTileSync(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final ArrayList getBarTilesByType(int i, int i2) {
        return this.interactor.getBarTilesByType(i, i2);
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getContext() {
        return this.context;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getDefaultTileList() {
        List defaultTiles = this.interactor.getDefaultTiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(defaultTiles, 10));
        Iterator it = defaultTiles.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileSpec) it.next()).getSpec());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getSpecs() {
        List currentTilesSpecs = this.interactor.getCurrentTilesSpecs();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(currentTilesSpecs, 10));
        ArrayList arrayList2 = (ArrayList) currentTilesSpecs;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((TileSpec) obj).getSpec());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost
    public final Collection getTiles() {
        return this.interactor.getCurrentQSTiles();
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getUserContext() {
        return (Context) this.interactor.getUserContext().getValue();
    }

    @Override // com.android.systemui.qs.QSHost
    public final int getUserId() {
        return ((Number) this.interactor.getUserId().getValue()).intValue();
    }

    @Override // com.android.systemui.qs.QSHost
    public final int indexOf(String str) {
        return ((ArrayList) getSpecs()).indexOf(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableCustomTile(String str) {
        TileSpec.Companion.getClass();
        return this.tileFeatureChecker.isAvailableCustomTile(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableForSearch(String str) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(str);
        String string = ((TileSearchInteractorImpl) this.tileSearchInteractor).context.getResources().getString(R.string.quick_settings_search_allow_list);
        TilesSettingConverter.INSTANCE.getClass();
        return ((ArrayList) TilesSettingConverter.toTilesList(string)).contains(create);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isBarTile(String str) {
        TileSpec.Companion.getClass();
        return this.interactor.isBarTile(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isLargeBarTile(String str) {
        return this.interactor.isLargeBarTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        TileSpec.Companion.getClass();
        return this.interactor.isUnsupportedTile(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void refreshTileList() {
        this.interactor.refreshCurrentTiles();
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        synchronized (this.callbacksMap) {
            Job job = (Job) this.callbacksMap.remove(callback);
            if (job != null) {
                job.cancel(null);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTile(String str) {
        TileSpec.Companion.getClass();
        this.interactor.removeTiles(Collections.singletonList(TileSpec.Companion.create(str)));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        TileSpec.Companion.getClass();
        this.interactor.removeTiles(Collections.singletonList(TileSpec.Companion.create(componentName)));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void sendRunestoneTileEventCDLog(String str, String str2, String str3) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(str);
        TileSALogHelper tileSALogHelper = this.tileSALogHelper;
        tileSALogHelper.getClass();
        String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
        int tileIndex = tileSALogHelper.getTileIndex(create);
        Objects.toString(create);
        SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID, str2, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID()) ? SystemUIAnalytics.QPBE_QS_TILE_ICON_POSITION : SystemUIAnalytics.QPBE_QQS_TILE_ICON_POSITION, String.valueOf(tileIndex), "settings", str3, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void sendTileEventLog(String str, String str2, String str3) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(str);
        TileSALogHelper tileSALogHelper = this.tileSALogHelper;
        tileSALogHelper.getClass();
        List list = (List) tileSALogHelper.tilesMap.get(str3);
        String str4 = list != null ? (String) list.get(0) : null;
        if (str4 != null) {
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), str4, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID()) ? SystemUIAnalytics.QPBE_QS_TILE_INTERACTION : SystemUIAnalytics.QPBE_QQS_TILE_INTERACTION, str2, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID()) ? SystemUIAnalytics.QPBE_QS_TILE_ICON_POSITION : SystemUIAnalytics.QPBE_QQS_TILE_ICON_POSITION, String.valueOf(tileSALogHelper.getTileIndex(create)));
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void sendTileStatusLog(Object obj, String str) {
        TileSALogHelper tileSALogHelper = this.tileSALogHelper;
        tileSALogHelper.getClass();
        List list = (List) tileSALogHelper.tilesMap.get(str);
        String str2 = list != null ? (String) list.get(1) : null;
        if (str2 == null || PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE.equals(str2)) {
            return;
        }
        if (obj instanceof Integer) {
            tileSALogHelper.editor.putInt(str2, ((Number) obj).intValue());
            tileSALogHelper.editor.apply();
        } else if (obj instanceof String) {
            tileSALogHelper.editor.putString(str2, (String) obj);
            tileSALogHelper.editor.apply();
        } else {
            Log.e("TileSALogHelper", "Invalid status value  " + obj);
        }
        if (TileSALogHelper.LOGGING_DEBUG) {
            SystemUIAnalytics.getCurrentScreenID();
            Objects.toString(obj);
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldBeHiddenByKnox(String str) {
        KnoxPolicyTilesInteractorImpl knoxPolicyTilesInteractorImpl = (KnoxPolicyTilesInteractorImpl) this.knoxPolicyTilesInteractor;
        if (((List) ((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesInteractorImpl.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()).isEmpty() || str == null) {
            return false;
        }
        TileSpec.Companion companion = TileSpec.Companion;
        TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
        Resources resources = knoxPolicyTilesInteractorImpl.resources;
        tileNameConverter.getClass();
        String tileSpec = TileNameConverter.toTileSpec(resources, str);
        companion.getClass();
        TileSpec create = TileSpec.Companion.create(tileSpec);
        if (!((List) ((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesInteractorImpl.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()).contains(create)) {
            return false;
        }
        Log.d("KnoxPolicyTilesInteractor", "shouldBeHiddenByKnox name= " + str + ", spec= " + create);
        return true;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldUnavailableByKnox(String str) {
        KnoxPolicyTilesInteractorImpl knoxPolicyTilesInteractorImpl = (KnoxPolicyTilesInteractorImpl) this.knoxPolicyTilesInteractor;
        if (((List) ((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesInteractorImpl.knoxPolicyTilesRepository).knoxUnavailableTiles.$$delegate_0.getValue()).isEmpty()) {
            return false;
        }
        KnoxPolicyTilesRepository knoxPolicyTilesRepository = knoxPolicyTilesInteractorImpl.knoxPolicyTilesRepository;
        Log.d("KnoxPolicyTilesInteractor", "unavailableByKnox= " + ((List) ((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesRepository).knoxUnavailableTiles.$$delegate_0.getValue()));
        if (str == null) {
            return false;
        }
        TileSpec.Companion companion = TileSpec.Companion;
        TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
        Resources resources = knoxPolicyTilesInteractorImpl.resources;
        tileNameConverter.getClass();
        String tileSpec = TileNameConverter.toTileSpec(resources, str);
        companion.getClass();
        TileSpec create = TileSpec.Companion.create(tileSpec);
        Log.d("KnoxPolicyTilesInteractor", "shouldUnavailableByKnox name= " + str + ", spec= " + create);
        return ((List) ((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesRepository).knoxUnavailableTiles.$$delegate_0.getValue()).contains(create);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2, boolean z) {
        RemovedTilesInteractor removedTilesInteractor = this.removedInteractor;
        if (z) {
            ((RemovedTilesInteractorImpl) removedTilesInteractor).resetRemovedTiles();
        } else {
            List<String> list3 = list;
            TileSpec.Companion companion = TileSpec.Companion;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
            for (String str : list3) {
                companion.getClass();
                arrayList.add(TileSpec.Companion.create(str));
            }
            List<String> list4 = list2;
            TileSpec.Companion companion2 = TileSpec.Companion;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
            for (String str2 : list4) {
                companion2.getClass();
                arrayList2.add(TileSpec.Companion.create(str2));
            }
            ((RemovedTilesInteractorImpl) removedTilesInteractor).setChangedTiles(arrayList, arrayList2);
        }
        List<String> list5 = list2;
        TileSpec.Companion companion3 = TileSpec.Companion;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
        for (String str3 : list5) {
            companion3.getClass();
            arrayList3.add(TileSpec.Companion.create(str3));
        }
        this.interactor.setTiles(arrayList3);
    }
}
