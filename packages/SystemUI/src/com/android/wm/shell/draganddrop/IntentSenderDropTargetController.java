package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Rect;
import android.os.PersistableBundle;
import android.util.Slog;
import android.view.DragEvent;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiWindowOverheatUI;
import com.android.wm.shell.draganddrop.DragAndDropController;

/* loaded from: classes3.dex */
public class IntentSenderDropTargetController implements IDropTargetUiController {
    public static final Rect sFullscreenHitRegion = new Rect();
    public final Context mContext;
    public final DragAndDropController mController;
    public final DisplayController mDisplayController;
    public int mEdgeFlags;
    public boolean mShowDropTarget;
    public boolean mDragStartedWithinThreshold = false;
    public boolean mIgnoreActionDragLocation = false;

    public IntentSenderDropTargetController(Context context, DragAndDropController dragAndDropController, DisplayController displayController) {
        this.mContext = context;
        this.mController = dragAndDropController;
        this.mDisplayController = displayController;
    }

    public static boolean isInThreshold(DragEvent dragEvent, DragAndDropController.PerDisplay perDisplay) {
        Rect bounds = perDisplay.wm.getCurrentWindowMetrics().getBounds();
        int iMin = (int) ((Math.min(bounds.width(), bounds.height()) * 0.056f) + 0.5f);
        sFullscreenHitRegion.set(iMin, 0, bounds.right - iMin, iMin);
        return dragEvent.getX() < ((float) iMin) || dragEvent.getX() > ((float) (bounds.right - iMin));
    }

    @Override // com.android.wm.shell.draganddrop.IDropTargetUiController
    public final boolean onDrag(DragEvent dragEvent, int i, final DragAndDropController.PerDisplay perDisplay) {
        PersistableBundle extras;
        int action = dragEvent.getAction();
        DragAndDropController dragAndDropController = this.mController;
        if (action == 1) {
            if (perDisplay.activeDragCount != 0) {
                Slog.w("IntentSenderDropTargetController", "Unexpected drag start during an active drag=" + perDisplay.activeDragCount);
                return false;
            }
            this.mIgnoreActionDragLocation = false;
            perDisplay.dragSession.initialize();
            perDisplay.activeDragCount++;
            ((DropTargetLayout) perDisplay.dragLayout).prepare(perDisplay.dragSession, null, dragEvent.getDragSurface(), perDisplay.mHiddenDropTargetArea, true);
            this.mDragStartedWithinThreshold = isInThreshold(dragEvent, perDisplay);
            this.mEdgeFlags = 7;
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(perDisplay.displayId);
            if (displayLayout != null) {
                int iNavigationBarPosition = DisplayLayout.navigationBarPosition(this.mContext.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
                if (iNavigationBarPosition == 1) {
                    this.mEdgeFlags &= -2;
                } else if (iNavigationBarPosition == 2) {
                    this.mEdgeFlags &= -3;
                }
            }
            ClipData clipData = dragEvent.getClipData();
            if (clipData == null) {
                Slog.d("IntentSenderDropTargetController", "setIgnoreEdgeFlags. clipData null.");
            } else {
                ClipDescription description = clipData.getDescription();
                if (description == null || (extras = description.getExtras()) == null) {
                    Slog.d("IntentSenderDropTargetController", "setIgnoreEdgeFlags. description null.");
                } else {
                    if (extras.getBoolean("com.samsung.android.content.clipdescription.extra.IGNORE_LEFT_EDGE")) {
                        this.mEdgeFlags &= -2;
                    }
                    if (extras.getBoolean("com.samsung.android.content.clipdescription.extra.IGNORE_RIGHT_EDGE")) {
                        this.mEdgeFlags &= -3;
                    }
                }
            }
            ((DropTargetLayout) perDisplay.dragLayout).show();
            dragAndDropController.notifyListeners(new IntentSenderDropTargetController$$ExternalSyntheticLambda0());
            return true;
        }
        if (action != 2) {
            if (action == 3) {
                this.mIgnoreActionDragLocation = true;
                return dragAndDropController.handleDrop(dragEvent, perDisplay);
            }
            if (action == 4) {
                this.mIgnoreActionDragLocation = true;
                DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                if (!dropTargetLayout.mHasDropped) {
                    perDisplay.activeDragCount--;
                    if (this.mShowDropTarget) {
                        dropTargetLayout.hide(new Runnable() { // from class: com.android.wm.shell.draganddrop.IntentSenderDropTargetController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                IntentSenderDropTargetController intentSenderDropTargetController = this.f$0;
                                DragAndDropController.PerDisplay perDisplay2 = perDisplay;
                                Rect rect = IntentSenderDropTargetController.sFullscreenHitRegion;
                                intentSenderDropTargetController.getClass();
                                if (perDisplay2.activeDragCount == 0) {
                                    intentSenderDropTargetController.mController.getClass();
                                    DragAndDropController.setDropTargetWindowVisibility(perDisplay2, 4);
                                }
                            }
                        }, true);
                    }
                }
                this.mShowDropTarget = false;
                perDisplay.smartTipController.dismissHelpTipIfPossible();
                return true;
            }
            if (action == 6) {
                ((DropTargetLayout) perDisplay.dragLayout).hide(null, false);
                return true;
            }
        } else {
            if (this.mIgnoreActionDragLocation) {
                Slog.d("IntentSenderDropTargetController", "Ignore ACTION_DRAG_LOCATION");
                return false;
            }
            if (!this.mDragStartedWithinThreshold) {
                Rect bounds = perDisplay.wm.getCurrentWindowMetrics().getBounds();
                int x = (int) dragEvent.getX();
                int y = (int) dragEvent.getY();
                int iMin = (int) ((Math.min(bounds.width(), bounds.height()) * 0.056f) + 0.5f);
                int i2 = bounds.right - iMin;
                int i3 = this.mEdgeFlags;
                if (((i3 & 1) == 0 || x >= iMin) && (((i3 & 2) == 0 || x <= i2) && ((i3 & 4) == 0 || !sFullscreenHitRegion.contains(x, y)))) {
                    ((DropTargetLayout) perDisplay.dragLayout).hide(null, false);
                    this.mShowDropTarget = false;
                    return true;
                }
                if (MultiWindowOverheatUI.showIfNeeded(this.mContext)) {
                    this.mIgnoreActionDragLocation = true;
                    return false;
                }
                dragAndDropController.getClass();
                DragAndDropController.setDropTargetWindowVisibility(perDisplay, 0);
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                this.mShowDropTarget = true;
                return true;
            }
            if (!isInThreshold(dragEvent, perDisplay)) {
                this.mDragStartedWithinThreshold = false;
            }
        }
        return true;
    }
}
