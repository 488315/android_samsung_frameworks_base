package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.PlacementEvent;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class EditTileKt$DefaultEditTileGrid$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ EditTileListState $listState;
    final /* synthetic */ Function2 $onAddTile;
    final /* synthetic */ MutableSelectionState $selectionState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$DefaultEditTileGrid$1$1(MutableSelectionState mutableSelectionState, EditTileListState editTileListState, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$selectionState = mutableSelectionState;
        this.$listState = editTileListState;
        this.$onAddTile = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$DefaultEditTileGrid$1$1(this.$selectionState, this.$listState, this.$onAddTile, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$DefaultEditTileGrid$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object next;
        int iIndexOf;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PlacementEvent placementEvent = (PlacementEvent) ((SnapshotMutableStateImpl) this.$selectionState.placementEvent$delegate).getValue();
        if (placementEvent != null) {
            EditTileListState editTileListState = this.$listState;
            Function2 function2 = this.$onAddTile;
            List listTileSpecs = editTileListState.tileSpecs();
            if (placementEvent instanceof PlacementEvent.PlaceToTileSpec) {
                iIndexOf = ((ArrayList) listTileSpecs).indexOf(((PlacementEvent.PlaceToTileSpec) placementEvent).targetSpec);
            } else {
                if (!(placementEvent instanceof PlacementEvent.PlaceToIndex)) {
                    throw new NoWhenBranchMatchedException();
                }
                PlacementEvent.PlaceToIndex placeToIndex = (PlacementEvent.PlaceToIndex) placementEvent;
                SnapshotStateList snapshotStateList = editTileListState._tiles;
                int size = snapshotStateList.size();
                int i = placeToIndex.targetIndex;
                if (i >= size) {
                    iIndexOf = ((ArrayList) listTileSpecs).size();
                } else if (i <= 0) {
                    iIndexOf = 0;
                } else {
                    Iterator it = snapshotStateList.subList(i, snapshotStateList.size()).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (((GridCell) next) instanceof TileGridCell) {
                            break;
                        }
                    }
                    TileGridCell tileGridCell = next instanceof TileGridCell ? (TileGridCell) next : null;
                    if (tileGridCell == null) {
                        iIndexOf = ((ArrayList) listTileSpecs).size();
                    } else {
                        ArrayList arrayList = (ArrayList) listTileSpecs;
                        iIndexOf = arrayList.indexOf(tileGridCell.tile.tileSpec);
                        if (arrayList.indexOf(placeToIndex.movingSpec) < iIndexOf) {
                            iIndexOf--;
                        }
                    }
                }
            }
            Integer num = new Integer(iIndexOf);
            Integer num2 = num.intValue() != -1 ? num : null;
            if (num2 != null) {
                function2.invoke(placementEvent.getMovingSpec(), new Integer(num2.intValue()));
            }
        }
        return Unit.INSTANCE;
    }
}
