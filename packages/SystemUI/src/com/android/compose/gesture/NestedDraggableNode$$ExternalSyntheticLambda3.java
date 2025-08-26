package com.android.compose.gesture;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.gesture.NestedDraggableNode;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class NestedDraggableNode$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NestedDraggableNode f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NestedDraggableNode$$ExternalSyntheticLambda3(NestedDraggableNode nestedDraggableNode, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = nestedDraggableNode;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Offset offset = (Offset) obj;
        switch (this.$r8$classId) {
            case 0:
                long j = offset.packedValue;
                NestedDraggableNode nestedDraggableNode = this.f$0;
                float fM934toFloatk4lQ0M$1 = nestedDraggableNode.m934toFloatk4lQ0M$1(j);
                NestedDraggableNode.NestedScrollController nestedScrollController = (NestedDraggableNode.NestedScrollController) this.f$1;
                float fOnDrag = nestedScrollController.controller.onDrag(fM934toFloatk4lQ0M$1);
                nestedScrollController.controller.getClass();
                return Offset.m395boximpl(nestedDraggableNode.m935toOffsettuRUvjQ$1(fOnDrag));
            default:
                long j2 = offset.packedValue;
                NestedDraggableNode nestedDraggableNode2 = this.f$0;
                nestedDraggableNode2.getClass();
                NestedScrollSource.Companion.getClass();
                int i = NestedScrollSource.UserInput;
                long jM585dispatchPreScrollOzD1aCk = nestedDraggableNode2.nestedScrollDispatcher.m585dispatchPreScrollOzD1aCk(i, j2);
                long jM402minusMKHz9U = Offset.m402minusMKHz9U(j2, jM585dispatchPreScrollOzD1aCk);
                long j3 = Offset.m395boximpl(nestedDraggableNode2.m935toOffsettuRUvjQ$1(((NestedDraggable.Controller) this.f$1).onDrag(nestedDraggableNode2.m934toFloatk4lQ0M$1(Offset.m395boximpl(jM402minusMKHz9U).packedValue)))).packedValue;
                return Offset.m395boximpl(Offset.m403plusMKHz9U(Offset.m403plusMKHz9U(j3, jM585dispatchPreScrollOzD1aCk), nestedDraggableNode2.nestedScrollDispatcher.m583dispatchPostScrollDzOQY0M(i, j3, Offset.m402minusMKHz9U(jM402minusMKHz9U, j3))));
        }
    }
}
