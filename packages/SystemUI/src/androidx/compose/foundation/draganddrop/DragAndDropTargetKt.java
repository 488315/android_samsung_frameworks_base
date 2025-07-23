package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.draganddrop.DragAndDropTarget;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DragAndDropTargetKt {
    public static final Modifier dragAndDropTarget(Modifier modifier, Function1 function1, DragAndDropTarget dragAndDropTarget) {
        return modifier.then(new DropTargetElement(function1, dragAndDropTarget));
    }
}
