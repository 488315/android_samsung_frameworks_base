package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexDockingController implements IController {
    public final ActivityTaskManagerService mAtm;
    public int mDisplayWidth;
    public WeakReference mCandidateTask = null;
    public final SparseArray mDockingBounds = new SparseArray();
    public final ArrayList mSkipMoveToFrontList = new ArrayList();

    public DexDockingController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final void clearAllTasks(String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m("clearAllTasks reason=", str, "DexDockingController");
        this.mAtm.mRootWindowContainer.forAllTasks(new DexDockingController$$ExternalSyntheticLambda4());
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
    }

    public final boolean isValidDockingBounds(int i, Rect rect) {
        Rect rect2 = (Rect) this.mDockingBounds.get(i);
        if (rect2 == null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "dockingBounds is null. docking=", "DexDockingController");
            return false;
        }
        if (rect.top == rect2.top && rect.bottom == rect2.bottom && ((i != 1 || rect.left == rect2.left) && (i != 2 || rect.right == rect2.right))) {
            return true;
        }
        Slog.d("DexDockingController", "isValidDockingBounds docking=" + WindowConfiguration.dexTaskDockingStateToString(i) + "   bounds=" + rect + "   dockingBounds=" + rect2);
        return false;
    }

    public final void resizeOtherTaskIfNeeded(final Task task, final Rect rect) {
        Slog.d("DexDockingController", "Resize task=" + task + "  bounds=" + rect);
        int dexTaskDockingState = task.getDexTaskDockingState();
        if (isValidDockingBounds(dexTaskDockingState, rect)) {
            final int i = dexTaskDockingState == 1 ? 2 : 1;
            this.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DexDockingController dexDockingController = DexDockingController.this;
                    int i2 = i;
                    Task task2 = task;
                    Rect rect2 = rect;
                    Task task3 = (Task) obj;
                    dexDockingController.getClass();
                    if (task3.getDexTaskDockingState() != i2) {
                        return;
                    }
                    int freeformThickness = task2.getFreeformThickness();
                    Rect rect3 = new Rect(rect2);
                    if (i2 == 1) {
                        rect3.left = 0;
                        rect3.right = rect2.left - freeformThickness;
                    } else if (i2 == 2) {
                        rect3.left = rect2.right + freeformThickness;
                        rect3.right = dexDockingController.mDisplayWidth;
                    }
                    if (rect3.isEmpty()) {
                        return;
                    }
                    Slog.d("DexDockingController", "Resize other task=" + task3 + "  bounds=" + rect3);
                    dexDockingController.mAtm.resizeTask(task3.mTaskId, rect3, 3);
                }
            });
        } else {
            Slog.d("DexDockingController", "Invalid task=" + task);
            clearAllTasks("invalid bounds");
        }
    }

    public final void setOtherTaskIfNeeded(Task task, ActivityOptions activityOptions) {
        if (!task.isDexMode() || activityOptions == null || activityOptions.getLaunchBounds() == null || activityOptions.getLaunchBounds().isEmpty()) {
            return;
        }
        WeakReference weakReference = this.mCandidateTask;
        if (weakReference == null || weakReference.get() == null) {
            Slog.d("DexDockingController", "CandidateTask is null. t=" + task);
            return;
        }
        Rect launchBounds = activityOptions.getLaunchBounds();
        for (int size = this.mDockingBounds.size() - 1; size >= 0; size--) {
            Rect rect = (Rect) this.mDockingBounds.valueAt(size);
            int keyAt = this.mDockingBounds.keyAt(size);
            if (rect.equals(launchBounds)) {
                task.setDexTaskDocking(keyAt);
                int i = keyAt == 1 ? 2 : 1;
                Task task2 = (Task) this.mCandidateTask.get();
                task2.setDexTaskDocking(i);
                this.mCandidateTask = null;
                Slog.d("DexDockingController", "addOtherTask - [" + WindowConfiguration.dexTaskDockingStateToString(i) + "]=" + task2 + "\n[" + WindowConfiguration.dexTaskDockingStateToString(keyAt) + "]=" + task);
                return;
            }
        }
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
    }
}
