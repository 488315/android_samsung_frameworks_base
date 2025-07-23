package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.qs.panels.shared.model.TileRowKt;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCellKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda13 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function2 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda13(Function2 function2, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = function2;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ResizingState.ResizeOperation resizeOperation = (ResizingState.ResizeOperation) obj;
                if (resizeOperation instanceof ResizingState.ResizeOperation.TemporaryResizeOperation) {
                    EditTileListState editTileListState = (EditTileListState) ((MutableState) this.f$1).getValue();
                    int indexOf = editTileListState.indexOf(resizeOperation.spec);
                    if (indexOf != -1) {
                        SnapshotStateList snapshotStateList = editTileListState._tiles;
                        TileGridCell tileGridCell = (TileGridCell) snapshotStateList.get(indexOf);
                        boolean isIcon = tileGridCell.isIcon();
                        boolean z = resizeOperation.toIcon;
                        if (isIcon != z) {
                            snapshotStateList.remove(indexOf);
                            snapshotStateList.add(indexOf, new TileGridCell(tileGridCell.tile, tileGridCell.row, z ? 1 : editTileListState.largeTilesSpan, tileGridCell.span, tileGridCell.column, null));
                            int row = ((GridCell) snapshotStateList.get(indexOf)).getRow();
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            ListIterator listIterator = snapshotStateList.listIterator();
                            while (listIterator.hasNext()) {
                                Object next = listIterator.next();
                                if (((GridCell) next).getRow() < row) {
                                    arrayList.add(next);
                                } else {
                                    arrayList2.add(next);
                                }
                            }
                            Pair pair = new Pair(arrayList, arrayList2);
                            List list = (List) pair.component1();
                            List list2 = (List) pair.component2();
                            ArrayList arrayList3 = new ArrayList();
                            for (Object obj2 : list2) {
                                if (obj2 instanceof TileGridCell) {
                                    arrayList3.add(obj2);
                                }
                            }
                            int i = editTileListState.columns;
                            List list3 = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.flatMapIndexedIterable(TileRowKt.splitInRowsSequence(i, arrayList3), new TileGridCellKt$$ExternalSyntheticLambda0(row, i)));
                            snapshotStateList.clear();
                            snapshotStateList.addAll(list);
                            snapshotStateList.addAll(list3);
                        }
                    }
                } else {
                    if (!(resizeOperation instanceof ResizingState.ResizeOperation.FinalResizeOperation)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    this.f$0.invoke(resizeOperation.spec, Boolean.valueOf(resizeOperation.toIcon));
                }
                return Unit.INSTANCE;
            default:
                this.f$0.invoke((TileSpec) obj, Integer.valueOf(((ArrayList) ((EditTileListState) this.f$1).tileSpecs()).size()));
                return Unit.INSTANCE;
        }
    }
}
