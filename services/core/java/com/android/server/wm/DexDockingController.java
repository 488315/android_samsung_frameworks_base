package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DexDockingController implements IController {
    public static final String TAG = "DexDockingController";
    public final ActivityTaskManagerService mAtm;
    public int mDisplayWidth;
    public WeakReference mCandidateTask = null;
    public final SparseArray mDockingBounds = new SparseArray();
    public final ArrayList mSkipMoveToFrontList = new ArrayList();

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
    }

    public int getOtherPosition(int i) {
        return i == 1 ? 2 : 1;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
    }

    public DexDockingController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public void initDockingBounds(Rect rect, Rect rect2, int i) {
        this.mDockingBounds.put(1, rect);
        this.mDockingBounds.put(2, rect2);
        this.mDisplayWidth = i;
    }

    public void setCandidateTask(Task task) {
        Slog.d(TAG, "setCandidateTask t=" + task);
        this.mCandidateTask = new WeakReference(task);
    }

    public void setOtherTaskIfNeeded(Task task, ActivityOptions activityOptions) {
        if (!task.isDexMode() || activityOptions == null || activityOptions.getLaunchBounds() == null || activityOptions.getLaunchBounds().isEmpty()) {
            return;
        }
        WeakReference weakReference = this.mCandidateTask;
        if (weakReference == null || weakReference.get() == null) {
            Slog.d(TAG, "CandidateTask is null. t=" + task);
            return;
        }
        Rect launchBounds = activityOptions.getLaunchBounds();
        for (int size = this.mDockingBounds.size() - 1; size >= 0; size--) {
            Rect rect = (Rect) this.mDockingBounds.valueAt(size);
            int keyAt = this.mDockingBounds.keyAt(size);
            if (rect.equals(launchBounds)) {
                task.setDexTaskDocking(keyAt);
                int otherPosition = getOtherPosition(keyAt);
                Task task2 = (Task) this.mCandidateTask.get();
                task2.setDexTaskDocking(otherPosition);
                this.mCandidateTask = null;
                Slog.d(TAG, "addOtherTask - [" + WindowConfiguration.dexTaskDockingStateToString(otherPosition) + "]=" + task2 + "\n[" + WindowConfiguration.dexTaskDockingStateToString(keyAt) + "]=" + task);
                return;
            }
        }
    }

    public void resizeOtherTaskIfNeeded(final Task task, final Rect rect) {
        String str = TAG;
        Slog.d(str, "Resize task=" + task + "  bounds=" + rect);
        int dexTaskDockingState = task.getDexTaskDockingState();
        if (!isValidDockingBounds(dexTaskDockingState, rect)) {
            Slog.d(str, "Invalid task=" + task);
            clearAllTasks("invalid bounds");
            return;
        }
        final int otherPosition = getOtherPosition(dexTaskDockingState);
        this.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexDockingController.this.lambda$resizeOtherTaskIfNeeded$0(otherPosition, task, rect, (Task) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resizeOtherTaskIfNeeded$0(int i, Task task, Rect rect, Task task2) {
        if (task2.getDexTaskDockingState() != i) {
            return;
        }
        Rect calcOtherBounds = calcOtherBounds(rect, i, task.getFreeformThickness());
        if (calcOtherBounds.isEmpty()) {
            return;
        }
        Slog.d(TAG, "Resize other task=" + task2 + "  bounds=" + calcOtherBounds);
        this.mAtm.resizeTask(task2.mTaskId, calcOtherBounds, 3);
    }

    public void moveTaskToFrontIfNeeded(Task task) {
        Slog.d(TAG, "moveTaskToFrontIfNeeded=" + task);
        if (this.mSkipMoveToFrontList.contains(task)) {
            return;
        }
        this.mSkipMoveToFrontList.add(task);
        final int otherPosition = getOtherPosition(task.getDexTaskDockingState());
        this.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexDockingController.this.lambda$moveTaskToFrontIfNeeded$1(otherPosition, (Task) obj);
            }
        });
        this.mSkipMoveToFrontList.remove(task);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTaskToFrontIfNeeded$1(int i, Task task) {
        if (task.getDexTaskDockingState() != i || task.getRootTask() == null || this.mSkipMoveToFrontList.contains(task)) {
            return;
        }
        task.getRootTask().moveToFront("DexDocking", task);
    }

    public void clearAllDockingTasks(String str) {
        clearAllTasks(str);
    }

    public void clearAllTasks(String str) {
        Slog.d(TAG, "clearAllTasks reason=" + str);
        this.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexDockingController.lambda$clearAllTasks$2((Task) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$clearAllTasks$2(Task task) {
        if (task.isDexTaskDocked()) {
            Slog.d(TAG, "clear task=" + task);
            task.setDexTaskDocking(0);
        }
    }

    public void updateDexDockingIfNeeded(final Task task) {
        int dexTaskDockingState = task.getDexTaskDockingState();
        Rect bounds = task.getBounds();
        if (isValidDockingBounds(dexTaskDockingState, bounds) && task.inFreeformWindowingMode()) {
            return;
        }
        Slog.d(TAG, "updateDexDockingIfNeeded - invalid task=" + task + "  bounds=" + bounds);
        this.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexDockingController.lambda$updateDexDockingIfNeeded$3(Task.this, (Task) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$updateDexDockingIfNeeded$3(Task task, Task task2) {
        if (task2.isDexTaskDocked()) {
            Slog.d(TAG, "updateDexDockingIfNeeded - clear task=" + task);
            task2.setDexTaskDocking(0);
        }
    }

    public int calculateMaxWidth(int i, int i2, int i3) {
        final int otherPosition = getOtherPosition(i);
        Task task = this.mAtm.mRootWindowContainer.getTask(new Predicate() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$calculateMaxWidth$4;
                lambda$calculateMaxWidth$4 = DexDockingController.lambda$calculateMaxWidth$4(otherPosition, (Task) obj);
                return lambda$calculateMaxWidth$4;
            }
        });
        if (task == null) {
            return -1;
        }
        int i4 = task.mMinWidth;
        if (i4 >= 0) {
            i3 = i4;
        }
        if (CoreRune.MW_CAPTION_SHELL_DEX) {
            i3 += task.getFreeformThickness();
        }
        return i2 - i3;
    }

    public static /* synthetic */ boolean lambda$calculateMaxWidth$4(int i, Task task) {
        return task.getDexTaskDockingState() == i;
    }

    public boolean isValidDockingBounds(int i, Rect rect) {
        Rect rect2 = (Rect) this.mDockingBounds.get(i);
        if (rect2 == null) {
            Slog.d(TAG, "dockingBounds is null. docking=" + i);
            return false;
        }
        if (rect.top == rect2.top && rect.bottom == rect2.bottom && ((i != 1 || rect.left == rect2.left) && (i != 2 || rect.right == rect2.right))) {
            return true;
        }
        Slog.d(TAG, "isValidDockingBounds docking=" + WindowConfiguration.dexTaskDockingStateToString(i) + "   bounds=" + rect + "   dockingBounds=" + rect2);
        return false;
    }

    public Rect calcOtherBounds(Rect rect, int i, int i2) {
        Rect rect2 = new Rect(rect);
        if (i == 1) {
            rect2.left = 0;
            rect2.right = rect.left - i2;
            return rect2;
        }
        if (i != 2) {
            return rect2;
        }
        rect2.left = rect.right + i2;
        rect2.right = this.mDisplayWidth;
        return rect2;
    }
}
