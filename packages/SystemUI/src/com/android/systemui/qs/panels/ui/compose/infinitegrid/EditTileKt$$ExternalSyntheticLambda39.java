package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda39 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda39(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((MutableSelectionState) this.f$0).unSelect();
                ((Function0) this.f$1).invoke();
                break;
            case 1:
                MutableSelectionState mutableSelectionState = (MutableSelectionState) this.f$0;
                TileSpec selection = mutableSelectionState.getSelection();
                if (selection != null) {
                    mutableSelectionState.unSelect();
                    ((Function1) this.f$1).mo781invoke(selection);
                }
                break;
            case 2:
                ((Function1) this.f$0).mo781invoke(new ResizingState.ResizeOperation.FinalResizeOperation(((TileGridCell) this.f$1).tile.tileSpec, !r1.isIcon()));
                break;
            default:
                TileSpec tileSpec = ((TileGridCell) this.f$1).tile.tileSpec;
                MutableSelectionState mutableSelectionState2 = (MutableSelectionState) this.f$0;
                if (mutableSelectionState2.getPlacementEnabled()) {
                    mutableSelectionState2.setPlacementEnabled(false);
                } else {
                    mutableSelectionState2.setSelection(tileSpec);
                    mutableSelectionState2.setPlacementEnabled(true);
                }
                break;
        }
        return Boolean.TRUE;
    }
}
