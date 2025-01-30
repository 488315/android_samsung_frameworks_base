package com.android.p038wm.shell.draganddrop;

import android.util.Log;
import android.util.Slog;
import android.view.DragEvent;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.logging.InstanceId;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.draganddrop.DragAndDropController;
import com.android.p038wm.shell.draganddrop.DragAndDropEventLogger;
import com.android.p038wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MimeTypeDropTargetController implements IDropTargetUiController {
    public final DragAndDropController mController;
    public final DisplayController mDisplayController;
    public boolean mIgnoreActionDragLocation = false;
    public final InputMethodManager mInputMethodManager;
    public final DragAndDropEventLogger mLogger;

    public MimeTypeDropTargetController(DragAndDropController dragAndDropController, DisplayController displayController, SplitScreenController splitScreenController, DragAndDropEventLogger dragAndDropEventLogger) {
        this.mController = dragAndDropController;
        this.mDisplayController = displayController;
        this.mLogger = dragAndDropEventLogger;
        this.mInputMethodManager = (InputMethodManager) dragAndDropController.mContext.getSystemService(InputMethodManager.class);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.p038wm.shell.draganddrop.IDropTargetUiController
    public final boolean onDrag(DragEvent dragEvent, int i, final DragAndDropController.PerDisplay perDisplay) {
        int action = dragEvent.getAction();
        DragAndDropEventLogger dragAndDropEventLogger = this.mLogger;
        DragAndDropController dragAndDropController = this.mController;
        switch (action) {
            case 1:
                if (perDisplay.activeDragCount != 0) {
                    Slog.w("DragAndDropController_Mime", "Unexpected drag start during an active drag=" + perDisplay.activeDragCount);
                    return false;
                }
                this.mIgnoreActionDragLocation = false;
                InstanceId logStart = dragAndDropEventLogger.logStart(dragEvent);
                perDisplay.activeDragCount++;
                ((DropTargetLayout) perDisplay.dragLayout).prepare(this.mDisplayController.getDisplayLayout(i), dragEvent.getClipData(), logStart, dragEvent.getDragSurface(), perDisplay.executableAppHolder, perDisplay.visibleTasks);
                dragAndDropController.getClass();
                DragAndDropController.setDropTargetWindowVisibility(perDisplay, 0);
                dragAndDropController.notifyDragStarted();
                IDropTargetUiController.performDragStartedHapticAndSound(perDisplay);
                ((DropTargetLayout) perDisplay.dragLayout).show();
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                return true;
            case 2:
                if (this.mIgnoreActionDragLocation) {
                    Slog.d("DragAndDropController_Mime", "Ignore ACTION_DRAG_LOCATION");
                    return false;
                }
                dragAndDropController.getClass();
                if (!perDisplay.mHiddenDropTargetArea.isEmpty()) {
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay, perDisplay.mHiddenDropTargetArea.contains((int) dragEvent.getX(), (int) dragEvent.getY()) ? 4 : 0);
                }
                InputMethodManager inputMethodManager = this.mInputMethodManager;
                if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
                    ((HandlerExecutor) dragAndDropController.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MimeTypeDropTargetController.this.mInputMethodManager.semForceHideSoftInput();
                            Log.i("DragAndDropController_Mime", "Hide the Ime when Drag Layout is shown");
                        }
                    });
                }
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                return true;
            case 3:
                this.mIgnoreActionDragLocation = true;
                return dragAndDropController.handleDrop(dragEvent, perDisplay);
            case 4:
                this.mIgnoreActionDragLocation = true;
                IDragLayout iDragLayout = perDisplay.dragLayout;
                if (((DropTargetLayout) iDragLayout).mHasDropped) {
                    dragAndDropEventLogger.getClass();
                    dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_DROPPED, dragAndDropEventLogger.mActivityInfo);
                } else {
                    perDisplay.activeDragCount--;
                    iDragLayout.hide(dragEvent, new Runnable() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MimeTypeDropTargetController mimeTypeDropTargetController = MimeTypeDropTargetController.this;
                            DragAndDropController.PerDisplay perDisplay2 = perDisplay;
                            mimeTypeDropTargetController.getClass();
                            if (perDisplay2.activeDragCount == 0) {
                                mimeTypeDropTargetController.mController.getClass();
                                DragAndDropController.setDropTargetWindowVisibility(perDisplay2, 4);
                            }
                        }
                    });
                }
                dragAndDropEventLogger.getClass();
                dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_END, dragAndDropEventLogger.mActivityInfo);
                return true;
            case 5:
                DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                if (dropTargetLayout.mIsShowing) {
                    Slog.w("DragAndDropController_Mime", "dragLayout is showing");
                    return true;
                }
                dropTargetLayout.show();
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                return true;
            case 6:
                perDisplay.dragLayout.hide(dragEvent, null);
                return true;
            default:
                return true;
        }
    }
}
