package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ EditTileListState f$0;
    public final /* synthetic */ MutableSelectionState f$1;
    public final /* synthetic */ Function1 f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda2(EditTileListState editTileListState, MutableSelectionState mutableSelectionState, Function1 function1, Modifier modifier, int i) {
        this.f$0 = editTileListState;
        this.f$1 = mutableSelectionState;
        this.f$2 = function1;
        this.f$3 = modifier;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3073);
                Function1 function1 = this.f$2;
                Modifier modifier = (Modifier) this.f$3;
                EditTileKt.CurrentTilesGridHeader(this.f$0, this.f$1, function1, modifier, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                TileSpec tileSpec = (TileSpec) obj;
                if (((Boolean) obj2).booleanValue()) {
                    this.f$2.mo781invoke(tileSpec);
                } else {
                    ((Function1) this.f$3).mo781invoke(this.f$0.tileSpecs());
                    this.f$1.setSelection(tileSpec);
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda2(Function1 function1, Function1 function12, EditTileListState editTileListState, MutableSelectionState mutableSelectionState) {
        this.f$2 = function1;
        this.f$3 = function12;
        this.f$0 = editTileListState;
        this.f$1 = mutableSelectionState;
    }
}
