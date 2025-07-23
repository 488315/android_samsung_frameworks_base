package androidx.compose.ui.platform;

import android.view.View;
import androidx.compose.ui.draganddrop.ComposeDragShadowBuilder;
import androidx.compose.ui.draganddrop.DragAndDropTransferData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidComposeViewStartDragAndDropN {
    public static final AndroidComposeViewStartDragAndDropN INSTANCE = new AndroidComposeViewStartDragAndDropN();

    private AndroidComposeViewStartDragAndDropN() {
    }

    public final boolean startDragAndDrop(View view, DragAndDropTransferData dragAndDropTransferData, ComposeDragShadowBuilder composeDragShadowBuilder) {
        return view.startDragAndDrop(dragAndDropTransferData.clipData, composeDragShadowBuilder, dragAndDropTransferData.localState, dragAndDropTransferData.flags);
    }
}
