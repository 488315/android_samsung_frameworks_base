package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.viewmodel.AvailableEditActions;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ListIterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class EditTileKt$rememberEditModeState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<EditModeHeaderState> $editGridHeaderState;
    final /* synthetic */ EditTileListState $listState;
    final /* synthetic */ MutableSelectionState $selectionState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$rememberEditModeState$1$1(EditTileListState editTileListState, MutableSelectionState mutableSelectionState, MutableState<EditModeHeaderState> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$listState = editTileListState;
        this.$selectionState = mutableSelectionState;
        this.$editGridHeaderState = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$rememberEditModeState$1$1(this.$listState, this.$selectionState, this.$editGridHeaderState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$rememberEditModeState$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        Object next;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$listState.isDraggedCellRemovable()) {
            z = true;
        } else {
            TileSpec selection = this.$selectionState.getSelection();
            if (selection != null) {
                ListIterator listIterator = this.$listState._tiles.listIterator();
                while (true) {
                    if (!listIterator.hasNext()) {
                        next = null;
                        break;
                    }
                    next = listIterator.next();
                    GridCell gridCell = (GridCell) next;
                    if (gridCell instanceof TileGridCell) {
                        TileGridCell tileGridCell = (TileGridCell) gridCell;
                        if (Intrinsics.areEqual(tileGridCell.tile.tileSpec, selection) && tileGridCell.tile.availableEditActions.contains(AvailableEditActions.REMOVE)) {
                            break;
                        }
                    }
                }
                if (next != null) {
                }
            }
            z = false;
        }
        this.$editGridHeaderState.setValue(this.$selectionState.getPlacementEnabled() ? EditModeHeaderState.Place : z ? EditModeHeaderState.Remove : EditModeHeaderState.Idle);
        return Unit.INSTANCE;
    }
}
