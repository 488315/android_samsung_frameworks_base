package com.android.compose.gesture;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.gesture.NestedDraggableNode;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        Offset offset = (Offset) obj;
        switch (this.$r8$classId) {
            case 0:
                long j = offset.packedValue;
                NestedDraggableNode nestedDraggableNode = this.f$0;
                float m932toFloatk4lQ0M$1 = nestedDraggableNode.m932toFloatk4lQ0M$1(j);
                NestedDraggableNode.NestedScrollController nestedScrollController = (NestedDraggableNode.NestedScrollController) this.f$1;
                float onDrag = nestedScrollController.controller.onDrag(m932toFloatk4lQ0M$1);
                nestedScrollController.controller.getClass();
                return Offset.m393boximpl(nestedDraggableNode.m933toOffsettuRUvjQ$1(onDrag));
            default:
                long j2 = offset.packedValue;
                NestedDraggableNode nestedDraggableNode2 = this.f$0;
                nestedDraggableNode2.getClass();
                NestedScrollSource.Companion.getClass();
                int i = NestedScrollSource.UserInput;
                long m583dispatchPreScrollOzD1aCk = nestedDraggableNode2.nestedScrollDispatcher.m583dispatchPreScrollOzD1aCk(i, j2);
                long m400minusMKHz9U = Offset.m400minusMKHz9U(j2, m583dispatchPreScrollOzD1aCk);
                long j3 = Offset.m393boximpl(nestedDraggableNode2.m933toOffsettuRUvjQ$1(((NestedDraggable.Controller) this.f$1).onDrag(nestedDraggableNode2.m932toFloatk4lQ0M$1(Offset.m393boximpl(m400minusMKHz9U).packedValue)))).packedValue;
                return Offset.m393boximpl(Offset.m401plusMKHz9U(Offset.m401plusMKHz9U(j3, m583dispatchPreScrollOzD1aCk), nestedDraggableNode2.nestedScrollDispatcher.m581dispatchPostScrollDzOQY0M(i, j3, Offset.m400minusMKHz9U(m400minusMKHz9U, j3))));
        }
    }
}
