package androidx.compose.ui.draganddrop;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DragAndDropTarget {
    boolean onDrop(DragAndDropEvent dragAndDropEvent);

    void onMoved(DragAndDropEvent dragAndDropEvent);

    default void onEnded(DragAndDropEvent dragAndDropEvent) {
    }

    default void onEntered(DragAndDropEvent dragAndDropEvent) {
    }

    default void onExited(DragAndDropEvent dragAndDropEvent) {
    }

    default void onStarted(DragAndDropEvent dragAndDropEvent) {
    }
}
