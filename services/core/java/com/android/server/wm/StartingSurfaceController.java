package com.android.server.wm;

import android.app.ActivityOptions;
import android.util.Slog;
import android.window.ITaskOrganizer;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StartingSurfaceController {
    public final ArrayList mDeferringAddStartActivities = new ArrayList();
    public boolean mDeferringAddStartingWindow;
    public boolean mInitNewTask;
    public boolean mInitProcessRunning;
    public boolean mInitTaskSwitch;
    public final WindowManagerService mService;
    public final SplashScreenExceptionList mSplashScreenExceptionsList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartingSurface {
        public final Task mTask;
        public final ITaskOrganizer mTaskOrganizer;

        public StartingSurface(Task task, ITaskOrganizer iTaskOrganizer) {
            this.mTask = task;
            this.mTaskOrganizer = iTaskOrganizer;
        }
    }

    public StartingSurfaceController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mSplashScreenExceptionsList = new SplashScreenExceptionList(windowManagerService.mContext.getMainExecutor());
    }

    public final void endDeferAddStartingWindow(ActivityOptions activityOptions) {
        this.mDeferringAddStartingWindow = false;
        int size = this.mDeferringAddStartActivities.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            DeferringStartingWindowRecord deferringStartingWindowRecord = (DeferringStartingWindowRecord) this.mDeferringAddStartActivities.get(size);
            ActivityRecord activityRecord = deferringStartingWindowRecord.mDeferring;
            if (activityRecord.task == null) {
                Slog.e("WindowManager", "No task exists: " + activityRecord.shortComponentName + " parent: " + activityRecord.getParent());
            } else {
                activityRecord.showStartingWindow(deferringStartingWindowRecord.mPrev, this.mInitNewTask, this.mInitTaskSwitch, this.mInitProcessRunning, true, deferringStartingWindowRecord.mSource, activityOptions);
                if (activityRecord.mStartingData != null) {
                    break;
                }
            }
        }
        this.mDeferringAddStartActivities.clear();
        Slog.d("WindowManager", "deferringAddStartActivities clear");
    }
}
