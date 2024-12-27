package com.android.server.wm;

import android.util.Slog;
import android.window.TaskSnapshot;
import com.android.server.wm.StartingSurfaceController;
import com.android.server.wm.StartingSurfaceController.StartingSurface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SnapshotStartingData extends StartingData {
    public final WindowManagerService mService;
    public final TaskSnapshot mSnapshot;

    public SnapshotStartingData(WindowManagerService windowManagerService, TaskSnapshot taskSnapshot, int i) {
        super(windowManagerService, i);
        this.mService = windowManagerService;
        this.mSnapshot = taskSnapshot;
    }

    @Override // com.android.server.wm.StartingData
    public final StartingSurfaceController.StartingSurface createStartingSurface(ActivityRecord activityRecord) {
        StartingSurfaceController startingSurfaceController = this.mService.mStartingSurfaceController;
        TaskSnapshot taskSnapshot = this.mSnapshot;
        startingSurfaceController.getClass();
        Task task = activityRecord.task;
        if (task == null) {
            Slog.w("WindowManager", "TaskSnapshotSurface.create: Failed to find task for activity=" + activityRecord);
            return null;
        }
        if (task.getWindow(new Task$$ExternalSyntheticLambda0(7)) == null) {
            Slog.w("WindowManager", "TaskSnapshotSurface.create: no main window in " + activityRecord);
            return null;
        }
        if (activityRecord.mDisplayContent.mDisplayRotation.mRotation != taskSnapshot.getRotation()) {
            activityRecord.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(activityRecord, activityRecord, false);
        }
        TaskOrganizerController taskOrganizerController = startingSurfaceController.mService.mAtmService.mTaskOrganizerController;
        if (taskOrganizerController.addStartingWindow(task, activityRecord, 0, taskSnapshot)) {
            return startingSurfaceController.new StartingSurface(task, taskOrganizerController.getTaskOrganizer());
        }
        return null;
    }

    @Override // com.android.server.wm.StartingData
    public final boolean hasImeSurface() {
        return this.mSnapshot.hasImeSurface();
    }

    @Override // com.android.server.wm.StartingData
    public final boolean needRevealAnimation() {
        return false;
    }
}
