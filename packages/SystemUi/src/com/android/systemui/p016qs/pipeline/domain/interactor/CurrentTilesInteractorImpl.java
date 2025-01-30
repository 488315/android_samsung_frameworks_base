package com.android.systemui.p016qs.pipeline.domain.interactor;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p016qs.TileStateToProtoKt;
import com.android.systemui.p016qs.external.CustomTileStatePersister;
import com.android.systemui.p016qs.external.TileLifecycleManager;
import com.android.systemui.p016qs.nano.QsTileState;
import com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.p016qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.p016qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.p016qs.pipeline.domain.model.TileModel;
import com.android.systemui.p016qs.pipeline.shared.TileSpec;
import com.android.systemui.p016qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.plugins.p013qs.QSFactory;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl implements CurrentTilesInteractor {
    public final StateFlowImpl _currentSpecsAndTiles;
    public final StateFlowImpl _userContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow currentTiles;
    public final StateFlowImpl currentUser;
    public final CustomTileAddedRepository customTileAddedRepository;
    public final CustomTileStatePersister customTileStatePersister;
    public final ChannelFlowTransformLatest installedPackagesWithTiles;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final QSPipelineLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final Map specsToTiles;
    public final QSFactory tileFactory;
    public final TileLifecycleManager.Factory tileLifecycleManagerFactory;
    public final TileSpecRepository tileSpecRepository;
    public final Flow userAndTiles;
    public final ReadonlyStateFlow userContext;
    public final ReadonlyStateFlow userId;
    public final UserRepository userRepository;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserAndTiles {
        public final List tiles;
        public final int userId;

        public UserAndTiles(int i, List<? extends TileSpec> list) {
            this.userId = i;
            this.tiles = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UserAndTiles)) {
                return false;
            }
            UserAndTiles userAndTiles = (UserAndTiles) obj;
            return this.userId == userAndTiles.userId && Intrinsics.areEqual(this.tiles, userAndTiles.tiles);
        }

        public final int hashCode() {
            return this.tiles.hashCode() + (Integer.hashCode(this.userId) * 31);
        }

        public final String toString() {
            return "UserAndTiles(userId=" + this.userId + ", tiles=" + this.tiles + ")";
        }
    }

    public CurrentTilesInteractorImpl(TileSpecRepository tileSpecRepository, InstalledTilesComponentRepository installedTilesComponentRepository, UserRepository userRepository, CustomTileStatePersister customTileStatePersister, QSFactory qSFactory, CustomTileAddedRepository customTileAddedRepository, TileLifecycleManager.Factory factory, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, QSPipelineLogger qSPipelineLogger, FeatureFlags featureFlags) {
        this.tileSpecRepository = tileSpecRepository;
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userRepository = userRepository;
        this.customTileStatePersister = customTileStatePersister;
        this.tileFactory = qSFactory;
        this.customTileAddedRepository = customTileAddedRepository;
        this.tileLifecycleManagerFactory = factory;
        this.userTracker = userTracker;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.logger = qSPipelineLogger;
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
        this.userAndTiles = FlowKt.flowOn(com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.distinctUntilChanged(FlowKt.transformLatest(MutableStateFlow2, new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1(null, this))), new UserAndTiles(-1, emptyList)), coroutineDispatcher2);
        this.installedPackagesWithTiles = FlowKt.transformLatest(MutableStateFlow2, new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$2(null, this));
        Flags flags = Flags.INSTANCE;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CurrentTileInteractorImpl:");
        printWriter.println("User: " + this.userId.getValue());
        Iterable iterable = (Iterable) this.currentTiles.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next = it2.next();
            if (next instanceof Dumpable) {
                arrayList2.add(next);
            }
        }
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            ((Dumpable) it3.next()).dump(printWriter, strArr);
        }
    }

    @Override // com.android.systemui.ProtoDumpable
    public final void dumpProto(SystemUIProtoDump systemUIProtoDump) {
        Iterable iterable = (Iterable) this.currentTiles.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile.getState());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            QsTileState proto = TileStateToProtoKt.toProto((QSTile.State) it2.next());
            if (proto != null) {
                arrayList2.add(proto);
            }
        }
        systemUIProtoDump.tiles = (QsTileState[]) arrayList2.toArray(new QsTileState[0]);
    }
}
