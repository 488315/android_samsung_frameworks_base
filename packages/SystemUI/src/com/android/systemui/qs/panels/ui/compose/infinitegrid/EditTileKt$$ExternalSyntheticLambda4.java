package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Offset;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Offset.m395boximpl(((Offset) ((MutableState) this.f$0).getValue()).packedValue);
            default:
                ((MutableSelectionState) this.f$0).unSelect();
                return Unit.INSTANCE;
        }
    }
}
