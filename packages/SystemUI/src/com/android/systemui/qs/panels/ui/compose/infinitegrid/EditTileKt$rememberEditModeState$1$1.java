package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
    
        if (r1 != null) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r4.label
            if (r0 != 0) goto L6c
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.qs.panels.ui.compose.EditTileListState r5 = r4.$listState
            boolean r5 = r5.isDraggedCellRemovable()
            if (r5 != 0) goto L51
            com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r5 = r4.$selectionState
            com.android.systemui.qs.pipeline.shared.TileSpec r5 = r5.getSelection()
            if (r5 == 0) goto L4f
            com.android.systemui.qs.panels.ui.compose.EditTileListState r0 = r4.$listState
            androidx.compose.runtime.snapshots.SnapshotStateList r0 = r0._tiles
            java.util.ListIterator r0 = r0.listIterator()
        L21:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L4b
            java.lang.Object r1 = r0.next()
            r2 = r1
            com.android.systemui.qs.panels.ui.model.GridCell r2 = (com.android.systemui.qs.panels.ui.model.GridCell) r2
            boolean r3 = r2 instanceof com.android.systemui.qs.panels.ui.model.TileGridCell
            if (r3 == 0) goto L21
            com.android.systemui.qs.panels.ui.model.TileGridCell r2 = (com.android.systemui.qs.panels.ui.model.TileGridCell) r2
            com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel r3 = r2.tile
            com.android.systemui.qs.pipeline.shared.TileSpec r3 = r3.tileSpec
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r5)
            if (r3 == 0) goto L21
            com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel r2 = r2.tile
            java.util.Set r2 = r2.availableEditActions
            com.android.systemui.qs.panels.ui.viewmodel.AvailableEditActions r3 = com.android.systemui.qs.panels.ui.viewmodel.AvailableEditActions.REMOVE
            boolean r2 = r2.contains(r3)
            if (r2 == 0) goto L21
            goto L4c
        L4b:
            r1 = 0
        L4c:
            if (r1 == 0) goto L4f
            goto L51
        L4f:
            r5 = 0
            goto L52
        L51:
            r5 = 1
        L52:
            androidx.compose.runtime.MutableState<com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState> r0 = r4.$editGridHeaderState
            com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r4 = r4.$selectionState
            boolean r4 = r4.getPlacementEnabled()
            if (r4 == 0) goto L5f
            com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState r4 = com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState.Place
            goto L66
        L5f:
            if (r5 == 0) goto L64
            com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState r4 = com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState.Remove
            goto L66
        L64:
            com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState r4 = com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState.Idle
        L66:
            r0.setValue(r4)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L6c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$rememberEditModeState$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
