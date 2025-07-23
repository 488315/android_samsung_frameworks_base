package com.android.wm.shell.draganddrop;

import android.util.Log;
import android.util.Slog;
import android.view.DragEvent;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.DragAndDropEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MimeTypeDropTargetController implements IDropTargetUiController {
    public final DragAndDropController mController;
    public boolean mIgnoreActionDragLocation = false;
    public final InputMethodManager mInputMethodManager;
    public final DragAndDropEventLogger mLogger;
    public final ShellTaskOrganizer mShellTaskOrganizer;

    public MimeTypeDropTargetController(DragAndDropController dragAndDropController, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, DragAndDropEventLogger dragAndDropEventLogger) {
        this.mController = dragAndDropController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mLogger = dragAndDropEventLogger;
        this.mInputMethodManager = (InputMethodManager) dragAndDropController.mContext.getSystemService(InputMethodManager.class);
    }

    @Override // com.android.wm.shell.draganddrop.IDropTargetUiController
    public final boolean onDrag(DragEvent dragEvent, int i, final DragAndDropController.PerDisplay perDisplay) {
        int i2;
        int action = dragEvent.getAction();
        DragAndDropEventLogger dragAndDropEventLogger = this.mLogger;
        ShellTaskOrganizer shellTaskOrganizer = this.mShellTaskOrganizer;
        DragAndDropController dragAndDropController = this.mController;
        switch (action) {
            case 1:
                if (perDisplay.activeDragCount == 0) {
                    perDisplay.dragSession.initialize();
                    perDisplay.activeDragCount++;
                    DragLayoutProvider dragLayoutProvider = perDisplay.dragLayout;
                    DragSession dragSession = perDisplay.dragSession;
                    ((DropTargetLayout) dragLayoutProvider).prepare(dragSession, dragAndDropEventLogger.logStart(dragSession), dragEvent.getDragSurface(), perDisplay.mHiddenDropTargetArea, false);
                    int i3 = perDisplay.dragSession.hideDragSourceTaskId;
                    if (i3 != -1) {
                        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Hiding task surface: taskId=%d", new Object[]{Integer.valueOf(i3)});
                        shellTaskOrganizer.setTaskSurfaceVisibility(perDisplay.dragSession.hideDragSourceTaskId, false);
                    }
                    dragAndDropController.getClass();
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay, 0);
                    final int i4 = 0;
                    dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj;
                            switch (i4) {
                                case 0:
                                    dragAndDropListener.onDragStarted();
                                    break;
                                default:
                                    dragAndDropListener.getClass();
                                    break;
                            }
                            return Boolean.FALSE;
                        }
                    });
                    this.mIgnoreActionDragLocation = false;
                    IDropTargetUiController.performDragStartedHapticAndSound(perDisplay);
                    ((DropTargetLayout) perDisplay.dragLayout).show();
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    break;
                } else {
                    Slog.w("DragAndDropController_Mime", "Unexpected drag start during an active drag");
                    break;
                }
            case 2:
                if (!this.mIgnoreActionDragLocation) {
                    dragAndDropController.getClass();
                    if (!perDisplay.mHiddenDropTargetArea.isEmpty()) {
                        DragAndDropController.setDropTargetWindowVisibility(perDisplay, perDisplay.mHiddenDropTargetArea.contains((int) dragEvent.getX(), (int) dragEvent.getY()) ? 4 : 0);
                    }
                    InputMethodManager inputMethodManager = this.mInputMethodManager;
                    if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
                        dragAndDropController.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                MimeTypeDropTargetController.this.mInputMethodManager.semForceHideSoftInput();
                                Log.i("DragAndDropController_Mime", "Hide the Ime when Drag Layout is shown");
                            }
                        });
                    }
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    break;
                } else {
                    Slog.d("DragAndDropController_Mime", "Ignore ACTION_DRAG_LOCATION");
                    break;
                }
            case 3:
                this.mIgnoreActionDragLocation = true;
                break;
            case 4:
                this.mIgnoreActionDragLocation = true;
                DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                if (dropTargetLayout.mHasDropped) {
                    if ((dropTargetLayout.mIsIntentSenderDropTarget ? false : dropTargetLayout.mDismissView.mIsEnterDismissButton) && (i2 = perDisplay.dragSession.hideDragSourceTaskId) != -1) {
                        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Re-showing task surface: taskId=%d", new Object[]{Integer.valueOf(i2)});
                        shellTaskOrganizer.setTaskSurfaceVisibility(perDisplay.dragSession.hideDragSourceTaskId, true);
                    }
                    dragAndDropEventLogger.getClass();
                    dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_DROPPED, dragAndDropEventLogger.mActivityInfo);
                } else {
                    int i5 = perDisplay.dragSession.hideDragSourceTaskId;
                    if (i5 != -1) {
                        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Re-showing task surface: taskId=%d", new Object[]{Integer.valueOf(i5)});
                        shellTaskOrganizer.setTaskSurfaceVisibility(perDisplay.dragSession.hideDragSourceTaskId, true);
                    }
                    perDisplay.activeDragCount--;
                    ((DropTargetLayout) perDisplay.dragLayout).hide(new Runnable() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda1
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
                    }, true);
                }
                dragAndDropEventLogger.getClass();
                dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_END, dragAndDropEventLogger.mActivityInfo);
                dragAndDropController.getClass();
                final int i6 = 1;
                dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.MimeTypeDropTargetController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj;
                        switch (i6) {
                            case 0:
                                dragAndDropListener.onDragStarted();
                                break;
                            default:
                                dragAndDropListener.getClass();
                                break;
                        }
                        return Boolean.FALSE;
                    }
                });
                break;
            case 5:
                DropTargetLayout dropTargetLayout2 = (DropTargetLayout) perDisplay.dragLayout;
                if (!dropTargetLayout2.mIsShowing) {
                    dropTargetLayout2.show();
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    break;
                } else {
                    Slog.w("DragAndDropController_Mime", "dragLayout is showing");
                    break;
                }
            case 6:
                ((DropTargetLayout) perDisplay.dragLayout).hide(null, true);
                break;
        }
        return true;
    }
}
