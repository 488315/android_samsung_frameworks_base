package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.os.Build;
import android.util.Log;
import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.user.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RemovedTilesInteractorImpl implements RemovedTilesInteractor {
    public static final boolean DEBUG;
    public final StateFlowImpl _removedTiles;
    public final RemovedTileSpecRepository removedTileSpecRepository;
    public final ReadonlyStateFlow removedTiles;
    public final CoroutineScope scope;
    public int userId;
    public final UserRepository userRepository;

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
        DEBUG = Build.IS_DEBUGGABLE;
    }

    public RemovedTilesInteractorImpl(CoroutineScope coroutineScope, RemovedTileSpecRepository removedTileSpecRepository, UserRepository userRepository) {
        this.scope = coroutineScope;
        this.removedTileSpecRepository = removedTileSpecRepository;
        this.userRepository = userRepository;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._removedTiles = MutableStateFlow;
        this.removedTiles = FlowKt.asStateFlow(MutableStateFlow);
        this.userId = -1;
        BuildersKt.launch$default(coroutineScope, null, null, new RemovedTilesInteractorImpl$startTileCollection$1(this, null), 3);
    }

    public final void resetRemovedTiles() {
        if (DEBUG) {
            Log.d("RemovedTilesInteractor", "resetRemovedTiles");
        }
        this._removedTiles.setValue(EmptyList.INSTANCE);
        BuildersKt.launch$default(this.scope, null, null, new RemovedTilesInteractorImpl$resetRemovedTiles$1(this, null), 3);
    }

    public final void setChangedTiles(List list, List list2) {
        StateFlowImpl stateFlowImpl = this._removedTiles;
        List list3 = (List) stateFlowImpl.getValue();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list3) {
            if (!((ArrayList) list2).contains((TileSpec) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (!((ArrayList) list2).contains((TileSpec) obj2)) {
                arrayList3.add(obj2);
            }
        }
        arrayList2.addAll(arrayList3);
        if (DEBUG) {
            Log.d("RemovedTilesInteractor", "RemovedTiles  " + list3 + "  to  " + arrayList2);
        }
        stateFlowImpl.updateState(null, arrayList2);
        BuildersKt.launch$default(this.scope, null, null, new RemovedTilesInteractorImpl$setChangedTiles$1(this, arrayList2, null), 3);
    }
}
