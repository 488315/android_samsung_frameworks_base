package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.ProtoDumpable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public interface CurrentTilesInteractor extends ProtoDumpable {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int POSITION_AT_END = -1;

        private Companion() {
        }
    }

    void addTile(TileSpec tileSpec, int i);

    QSTile createTileSync(TileSpec tileSpec);

    default TileModel getBarTileBySpecString() {
        return null;
    }

    default ArrayList getBarTilesByType(int i, int i2) {
        return null;
    }

    StateFlowImpl getCurrentBarTileList();

    default List getCurrentQSTiles() {
        Iterable iterable = (Iterable) getCurrentTiles().getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile);
        }
        return arrayList;
    }

    StateFlow getCurrentTiles();

    default List getCurrentTilesSpecs() {
        Iterable iterable = (Iterable) getCurrentTiles().getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).spec);
        }
        return arrayList;
    }

    List getDefaultTiles();

    default QSTile getTileBySpecString(String str) {
        return null;
    }

    Flow getTilesUpdatedFlow();

    StateFlow getUserContext();

    StateFlow getUserId();

    default boolean isBarTile(TileSpec tileSpec) {
        return false;
    }

    default boolean isLargeBarTile(String str) {
        return false;
    }

    boolean isUnsupportedTile(TileSpec tileSpec);

    void refreshCurrentTiles();

    void removeTiles(Collection collection);

    void resetTiles();

    void setTiles(List list);
}
