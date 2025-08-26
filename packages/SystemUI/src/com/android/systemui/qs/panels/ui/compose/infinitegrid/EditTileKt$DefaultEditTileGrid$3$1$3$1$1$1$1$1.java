package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.model.AvailableTileGridCell;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class EditTileKt$DefaultEditTileGrid$3$1$3$1$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SnapshotStateList<AvailableTileGridCell> $availableTiles;
    final /* synthetic */ EditTileListState $listState;
    final /* synthetic */ List<SizedTile> $otherTiles;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public EditTileKt$DefaultEditTileGrid$3$1$3$1$1$1$1$1(SnapshotStateList<AvailableTileGridCell> snapshotStateList, EditTileListState editTileListState, List<? extends SizedTile> list, Continuation continuation) {
        super(2, continuation);
        this.$availableTiles = snapshotStateList;
        this.$listState = editTileListState;
        this.$otherTiles = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$DefaultEditTileGrid$3$1$3$1$1$1$1$1(this.$availableTiles, this.$listState, this.$otherTiles, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$DefaultEditTileGrid$3$1$3$1$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SnapshotStateList<AvailableTileGridCell> snapshotStateList = this.$availableTiles;
        EditTileListState editTileListState = this.$listState;
        List<SizedTile> list = this.$otherTiles;
        snapshotStateList.clear();
        snapshotStateList.addAll(EditTileKt.access$toAvailableTiles(editTileListState._tiles.getReadable$runtime_release().list, list));
        return Unit.INSTANCE;
    }
}
