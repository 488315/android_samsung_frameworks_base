package androidx.compose.ui.draganddrop;

import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.InnerNodeCoordinator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DragAndDropNodeKt {
    public static final DragAndDropNode DragAndDropTargetModifierNode(final Function1 function1, final DragAndDropTarget dragAndDropTarget) {
        return new DragAndDropNode(null, new Function1() { // from class: androidx.compose.ui.draganddrop.DragAndDropNodeKt$DragAndDropTargetModifierNode$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                if (((Boolean) Function1.this.mo779invoke((DragAndDropEvent) obj)).booleanValue()) {
                    return dragAndDropTarget;
                }
                return null;
            }
        }, 1, 0 == true ? 1 : 0);
    }

    /* renamed from: access$contains-Uv8p0NA, reason: not valid java name */
    public static final boolean m357access$containsUv8p0NA(DragAndDropNode dragAndDropNode, long j) {
        if (!dragAndDropNode.node.isAttached) {
            return false;
        }
        InnerNodeCoordinator innerNodeCoordinator = DelegatableNodeKt.requireLayoutNode(dragAndDropNode).nodes.innerCoordinator;
        if (!innerNodeCoordinator.tail.isAttached) {
            return false;
        }
        long positionInRoot = LayoutCoordinatesKt.positionInRoot(innerNodeCoordinator);
        float intBitsToFloat = Float.intBitsToFloat((int) (positionInRoot >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (positionInRoot & 4294967295L));
        long j2 = dragAndDropNode.size;
        float f = ((int) (j2 >> 32)) + intBitsToFloat;
        float f2 = ((int) (j2 & 4294967295L)) + intBitsToFloat2;
        float intBitsToFloat3 = Float.intBitsToFloat((int) (j >> 32));
        if (intBitsToFloat > intBitsToFloat3 || intBitsToFloat3 > f) {
            return false;
        }
        float intBitsToFloat4 = Float.intBitsToFloat((int) (j & 4294967295L));
        return intBitsToFloat2 <= intBitsToFloat4 && intBitsToFloat4 <= f2;
    }
}
