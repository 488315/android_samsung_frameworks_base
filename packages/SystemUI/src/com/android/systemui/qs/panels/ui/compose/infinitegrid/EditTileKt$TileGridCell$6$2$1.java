package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class EditTileKt$TileGridCell$6$2$1 extends FunctionReferenceImpl implements Function0 {
    public EditTileKt$TileGridCell$6$2$1(Object obj) {
        super(0, obj, MutableSelectionState.class, "unSelect", "unSelect()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((MutableSelectionState) this.receiver).unSelect();
        return Unit.INSTANCE;
    }
}
