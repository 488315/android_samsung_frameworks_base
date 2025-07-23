package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.draganddrop.DragAndDropEvent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DragAndDropTargetNode$createAndAttachDragAndDropModifierNode$1 extends Lambda implements Function1 {
    final /* synthetic */ DragAndDropTargetNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropTargetNode$createAndAttachDragAndDropModifierNode$1(DragAndDropTargetNode dragAndDropTargetNode) {
        super(1);
        this.this$0 = dragAndDropTargetNode;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return (Boolean) this.this$0.shouldStartDragAndDrop.mo779invoke((DragAndDropEvent) obj);
    }
}
