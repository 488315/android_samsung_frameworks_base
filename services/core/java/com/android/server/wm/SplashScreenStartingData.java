package com.android.server.wm;

import com.android.server.wm.StartingSurfaceController.StartingSurface;

public final class SplashScreenStartingData extends StartingData {
    public final int mTheme;

    public SplashScreenStartingData(WindowManagerService windowManagerService, int i, int i2) {
        super(windowManagerService, i2);
        this.mTheme = i;
    }

    @Override // com.android.server.wm.StartingData
    public final StartingSurfaceController.StartingSurface createStartingSurface(
            ActivityRecord activityRecord) {
        StartingSurfaceController startingSurfaceController =
                this.mService.mStartingSurfaceController;
        startingSurfaceController.getClass();
        Task task = activityRecord.task;
        TaskOrganizerController taskOrganizerController =
                startingSurfaceController.mService.mAtmService.mTaskOrganizerController;
        if (task == null
                || !taskOrganizerController.addStartingWindow(
                        task, activityRecord, this.mTheme, null)) {
            return null;
        }
        return startingSurfaceController
        .new StartingSurface(task, taskOrganizerController.getTaskOrganizer());
    }

    @Override // com.android.server.wm.StartingData
    public final boolean needRevealAnimation() {
        return true;
    }
}
