package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.geometry.Offset;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.shared.model.TileRowKt;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCellKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.viewmodel.AvailableEditActions;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes2.dex */
public final class EditTileListState implements DragAndDropState {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SnapshotStateList _tiles;
    public final int columns;
    public final MutableState dragType$delegate;
    public final MutableState draggedCell$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState draggedPosition$delegate;
    public final int largeTilesSpan;

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

    public EditTileListState(List<? extends SizedTile> list, int i, int i2) {
        this.columns = i;
        this.largeTilesSpan = i2;
        Offset.Companion.getClass();
        this.draggedPosition$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl(Offset.Unspecified));
        this.dragType$delegate = SnapshotStateKt.mutableStateOf$default(null);
        List list2 = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.flatMapIndexedIterable(TileRowKt.splitInRowsSequence(i, list), new TileGridCellKt$$ExternalSyntheticLambda0(0, i)));
        SnapshotStateList snapshotStateList = new SnapshotStateList();
        snapshotStateList.addAll(list2);
        this._tiles = snapshotStateList;
    }

    public final SizedTile getDraggedCell() {
        return (SizedTile) ((SnapshotMutableStateImpl) this.draggedCell$delegate).getValue();
    }

    public final int indexOf(TileSpec tileSpec) {
        int i = 0;
        for (GridCell gridCell : this._tiles) {
            if ((gridCell instanceof TileGridCell) && Intrinsics.areEqual(((TileGridCell) gridCell).tile.tileSpec, tileSpec)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public final boolean isDraggedCellRemovable() {
        EditTileViewModel editTileViewModel;
        if (((DragType) ((SnapshotMutableStateImpl) this.dragType$delegate).getValue()) == DragType.Add) {
            return true;
        }
        SizedTile draggedCell = getDraggedCell();
        return (draggedCell == null || (editTileViewModel = (EditTileViewModel) draggedCell.getTile()) == null) ? false : editTileViewModel.availableEditActions.contains(AvailableEditActions.REMOVE);
    }

    public final void onDrop() {
        ((SnapshotMutableStateImpl) this.draggedCell$delegate).setValue(null);
        Offset.Companion.getClass();
        m2905setDraggedPositionk4lQ0M(Offset.Unspecified);
        ((SnapshotMutableStateImpl) this.dragType$delegate).setValue(null);
        regenerateGrid();
    }

    public final void regenerateGrid() {
        ArrayList arrayList = new ArrayList();
        SnapshotStateList snapshotStateList = this._tiles;
        ListIterator listIterator = snapshotStateList.listIterator();
        while (listIterator.hasNext()) {
            Object next = listIterator.next();
            if (next instanceof TileGridCell) {
                arrayList.add(next);
            }
        }
        int i = this.columns;
        List list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.flatMapIndexedIterable(TileRowKt.splitInRowsSequence(i, arrayList), new TileGridCellKt$$ExternalSyntheticLambda0(0, i)));
        snapshotStateList.clear();
        snapshotStateList.addAll(list);
    }

    /* renamed from: setDraggedPosition-k-4lQ0M, reason: not valid java name */
    public final void m2905setDraggedPositionk4lQ0M(long j) {
        ((SnapshotMutableStateImpl) this.draggedPosition$delegate).setValue(Offset.m395boximpl(j));
    }

    public final List tileSpecs() {
        ArrayList arrayList = new ArrayList();
        for (Object obj : this._tiles) {
            if (obj instanceof TileGridCell) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            arrayList2.add(((TileGridCell) obj2).tile.tileSpec);
        }
        return arrayList2;
    }
}
