package com.android.server.wm;

import android.util.secutil.Slog;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class FreeformTaskPinningController {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public Task mPinnedTask;
    public final TaskDisplayArea mTaskDisplayArea;

    public FreeformTaskPinningController(TaskDisplayArea taskDisplayArea) {
        this.mTaskDisplayArea = taskDisplayArea;
        taskDisplayArea.setFreeformTaskPinning(1);
    }

    public void startPinning(Task task) {
        DisplayContent displayContent;
        if (hasTaskPinned() || (CoreRune.MT_NEW_DEX_TASK_PINNING && task.getConfiguration().windowConfiguration.isAlwaysOnTop())) {
            Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, already pinned");
            return;
        }
        if (!task.inFreeformWindowingMode()) {
            Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, task isn't in freeform.");
            return;
        }
        if (!this.mTaskDisplayArea.isDesktopModeEnabled()) {
            Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, it's not in dex mode.");
            return;
        }
        if (DEBUG) {
            Slog.d("FreeformTaskPinningController", "startFreeformTaskPinning : taskId #" + task.mTaskId);
        }
        task.setFreeformTaskPinning(2);
        if (CoreRune.MT_NEW_DEX_TASK_PINNING && !this.mTaskDisplayArea.isNewDexMode()) {
            this.mTaskDisplayArea.setFreeformTaskPinning(3);
        }
        if (!task.isForceHidden()) {
            this.mTaskDisplayArea.positionChildAt(Integer.MAX_VALUE, task, false);
            if (CoreRune.MT_NEW_DEX_TASK_PINNING && (displayContent = this.mTaskDisplayArea.getDisplayContent()) != null) {
                displayContent.assignWindowLayers(false);
            }
        }
        this.mTaskDisplayArea.ensureActivitiesVisible(null, 0, false, true);
        this.mPinnedTask = task;
    }

    public void stopPinning(Task task, boolean z, String str) {
        if (this.mPinnedTask != task && CoreRune.MT_NEW_DEX_TASK_PINNING && !this.mTaskDisplayArea.isNewDexMode()) {
            Slog.d("FreeformTaskPinningController", "Failed to stop freeform task pinning, task requested isn't in pinning mode.");
            return;
        }
        if (DEBUG) {
            Slog.d("FreeformTaskPinningController", "stopFreeformTaskPinning : taskId #" + task.mTaskId + ", reason : " + str);
        }
        if (CoreRune.MT_NEW_DEX_TASK_PINNING && !this.mTaskDisplayArea.isNewDexMode()) {
            this.mTaskDisplayArea.setFreeformTaskPinning(1);
        }
        task.setFreeformTaskPinning(0);
        if (z) {
            this.mTaskDisplayArea.ensureActivitiesVisible(null, 0, false, true);
        }
        this.mPinnedTask = null;
    }

    public boolean hasTaskPinned() {
        return ((CoreRune.MT_NEW_DEX_TASK_PINNING && this.mTaskDisplayArea.isNewDexMode()) || this.mPinnedTask == null) ? false : true;
    }

    public void dump(PrintWriter printWriter, String str) {
        if (this.mPinnedTask == null) {
            return;
        }
        printWriter.println(str + "FreeformTaskPinningController :");
        printWriter.println((str + "  ") + "mPinnedTask :" + this.mPinnedTask);
    }
}
