package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.compat.CompatChanges;
import android.os.UserHandle;
import android.util.Slog;
import android.window.TaskSnapshot;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class StartingSurfaceController {
    public static final String TAG = "WindowManager";
    public final ArrayList mDeferringAddStartActivities = new ArrayList();
    public boolean mDeferringAddStartingWindow;
    public boolean mInitNewTask;
    public boolean mInitProcessRunning;
    public boolean mInitTaskSwitch;
    public final WindowManagerService mService;
    public final SplashScreenExceptionList mSplashScreenExceptionsList;

    public StartingSurfaceController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mSplashScreenExceptionsList = new SplashScreenExceptionList(windowManagerService.mContext.getMainExecutor());
    }

    public StartingSurface createSplashScreenStartingSurface(ActivityRecord activityRecord, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task task = activityRecord.getTask();
                if (task == null || !this.mService.mAtmService.mTaskOrganizerController.addStartingWindow(task, activityRecord, i, null)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                StartingSurface startingSurface = new StartingSurface(task);
                WindowManagerService.resetPriorityAfterLockedSection();
                return startingSurface;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean isExceptionApp(String str, int i, Supplier supplier) {
        return this.mSplashScreenExceptionsList.isException(str, i, supplier);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x000f, code lost:
    
        if (r8 == 1) goto L11;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int makeStartingWindowTypeParameter(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, String str, int i2) {
        int i3;
        ?? r0 = z;
        if (z2) {
            r0 = (z ? 1 : 0) | 2;
        }
        if (z3) {
            r0 = (r0 == true ? 1 : 0) | 4;
        }
        if (z4) {
            r0 = (r0 == true ? 1 : 0) | '\b';
        }
        if (!z5) {
            i3 = r0;
        }
        i3 = (r0 == true ? 1 : 0) | 16;
        if (z6) {
            i3 = (i3 == true ? 1 : 0) | 32;
        }
        if (z7) {
            i3 = (i3 == true ? 1 : 0) | Integer.MIN_VALUE;
        }
        if (z8) {
            i3 = (i3 == true ? 1 : 0) | 64;
        }
        return (i == 2 && CompatChanges.isChangeEnabled(205907456L, str, UserHandle.of(i2))) ? i3 | 128 : i3;
    }

    public StartingSurface createTaskSnapshotSurface(ActivityRecord activityRecord, TaskSnapshot taskSnapshot) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task task = activityRecord.getTask();
                if (task == null) {
                    Slog.w(TAG, "TaskSnapshotSurface.create: Failed to find task for activity=" + activityRecord);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                ActivityRecord topFullscreenActivity = activityRecord.getTask().getTopFullscreenActivity();
                if (topFullscreenActivity == null) {
                    Slog.w(TAG, "TaskSnapshotSurface.create: Failed to find top fullscreen for task=" + task);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (topFullscreenActivity.getTopFullscreenOpaqueWindow() == null) {
                    Slog.w(TAG, "TaskSnapshotSurface.create: no opaque window in " + topFullscreenActivity);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (activityRecord.mDisplayContent.getRotation() != taskSnapshot.getRotation()) {
                    activityRecord.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(activityRecord, false);
                }
                this.mService.mAtmService.mTaskOrganizerController.addStartingWindow(task, activityRecord, 0, taskSnapshot);
                StartingSurface startingSurface = new StartingSurface(task);
                WindowManagerService.resetPriorityAfterLockedSection();
                return startingSurface;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final class DeferringStartingWindowRecord {
        public final ActivityRecord mDeferring;
        public final ActivityRecord mPrev;
        public final ActivityRecord mSource;

        public DeferringStartingWindowRecord(ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityRecord activityRecord3) {
            this.mDeferring = activityRecord;
            this.mPrev = activityRecord2;
            this.mSource = activityRecord3;
        }
    }

    public void showStartingWindow(ActivityRecord activityRecord, ActivityRecord activityRecord2, boolean z, boolean z2, ActivityRecord activityRecord3, boolean z3) {
        if (this.mDeferringAddStartingWindow) {
            addDeferringRecord(activityRecord, activityRecord2, z, z2, activityRecord3);
        } else if (z3) {
            activityRecord.showStartingWindow(activityRecord2, z, z2, activityRecord.isProcessRunning(), true, activityRecord3, null, true);
        } else {
            activityRecord.showStartingWindow(activityRecord2, z, z2, true, activityRecord3);
        }
    }

    public final void addDeferringRecord(ActivityRecord activityRecord, ActivityRecord activityRecord2, boolean z, boolean z2, ActivityRecord activityRecord3) {
        if (this.mDeferringAddStartActivities.isEmpty()) {
            this.mInitProcessRunning = activityRecord.isProcessRunning();
            this.mInitNewTask = z;
            this.mInitTaskSwitch = z2;
        }
        this.mDeferringAddStartActivities.add(new DeferringStartingWindowRecord(activityRecord, activityRecord2, activityRecord3));
    }

    public final void showStartingWindowFromDeferringActivities(ActivityOptions activityOptions) {
        for (int size = this.mDeferringAddStartActivities.size() - 1; size >= 0; size--) {
            DeferringStartingWindowRecord deferringStartingWindowRecord = (DeferringStartingWindowRecord) this.mDeferringAddStartActivities.get(size);
            if (deferringStartingWindowRecord.mDeferring.getTask() == null) {
                Slog.e(TAG, "No task exists: " + deferringStartingWindowRecord.mDeferring.shortComponentName + " parent: " + deferringStartingWindowRecord.mDeferring.getParent());
            } else {
                deferringStartingWindowRecord.mDeferring.showStartingWindow(deferringStartingWindowRecord.mPrev, this.mInitNewTask, this.mInitTaskSwitch, this.mInitProcessRunning, true, deferringStartingWindowRecord.mSource, activityOptions);
                if (deferringStartingWindowRecord.mDeferring.mStartingData != null) {
                    break;
                }
            }
        }
        this.mDeferringAddStartActivities.clear();
    }

    public void beginDeferAddStartingWindow() {
        this.mDeferringAddStartingWindow = true;
    }

    public void endDeferAddStartingWindow(ActivityOptions activityOptions) {
        this.mDeferringAddStartingWindow = false;
        showStartingWindowFromDeferringActivities(activityOptions);
    }

    public void cleanupDeferAddStartingWindow() {
        this.mDeferringAddStartingWindow = false;
        this.mDeferringAddStartActivities.clear();
    }

    public final class StartingSurface {
        public final Task mTask;

        public StartingSurface(Task task) {
            this.mTask = task;
        }

        public void remove(boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = StartingSurfaceController.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    StartingSurfaceController.this.mService.mAtmService.mTaskOrganizerController.removeStartingWindow(this.mTask, z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
