package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.draganddrop.DragAndDropTarget;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class DragAndDropTargetKt {
    public static final Modifier dragAndDropTarget(Modifier modifier, Function1 function1, DragAndDropTarget dragAndDropTarget) {
        return modifier.then(new DropTargetElement(function1, dragAndDropTarget));
    }
}
