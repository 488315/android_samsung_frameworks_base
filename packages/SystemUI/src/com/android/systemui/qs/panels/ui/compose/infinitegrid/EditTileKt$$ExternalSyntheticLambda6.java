package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.qs.panels.ui.model.AvailableTileGridCell;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((MutableState) this.f$0).setValue(Offset.m395boximpl(LayoutCoordinatesKt.positionInRoot((LayoutCoordinates) obj)));
                return Unit.INSTANCE;
            case 1:
                Integer num = (Integer) obj;
                GridCell gridCell = (GridCell) ((PersistentList) this.f$0).get(num.intValue());
                return gridCell instanceof TileGridCell ? ((TileGridCell) gridCell).key : num;
            case 2:
                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                String str = (String) this.f$0;
                if (str != null) {
                    SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, str);
                }
                return Unit.INSTANCE;
            case 3:
                return ((AvailableTileGridCell) this.f$0).tile.icon;
            default:
                return ((EditTileViewModel) this.f$0).icon;
        }
    }
}
