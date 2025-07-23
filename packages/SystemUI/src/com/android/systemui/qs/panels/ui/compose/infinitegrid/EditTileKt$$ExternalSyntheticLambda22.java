package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.QSDragAnchor;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import com.android.systemui.qs.panels.ui.compose.selection.TileState;
import com.android.systemui.qs.panels.ui.model.AvailableTileGridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda22 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda22(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float f;
        switch (this.$r8$classId) {
            case 0:
                AvailableTileGridCell availableTileGridCell = (AvailableTileGridCell) this.f$1;
                ((Function1) this.f$0).mo779invoke(availableTileGridCell.tile.tileSpec);
                ((MutableSelectionState) this.f$2).setSelection(availableTileGridCell.tile.tileSpec);
                return Unit.INSTANCE;
            default:
                if (((TileState) ((MutableState) this.f$2).getValue()) == TileState.Selected) {
                    ResizingState resizingState = (ResizingState) this.f$0;
                    resizingState.getClass();
                    f = resizingState.anchoredDraggableState.progress(QSDragAnchor.Icon, QSDragAnchor.Large);
                } else {
                    f = ((TileGridCell) this.f$1).isIcon() ? 0.0f : 1.0f;
                }
                return Float.valueOf(f);
        }
    }
}
