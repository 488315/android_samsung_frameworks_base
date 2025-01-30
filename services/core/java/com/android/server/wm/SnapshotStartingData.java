package com.android.server.wm;

import android.window.TaskSnapshot;
import com.android.server.wm.StartingSurfaceController;

/* loaded from: classes3.dex */
public class SnapshotStartingData extends StartingData {
    public final WindowManagerService mService;
    public final TaskSnapshot mSnapshot;

    @Override // com.android.server.wm.StartingData
    public boolean needRevealAnimation() {
        return false;
    }

    public SnapshotStartingData(WindowManagerService windowManagerService, TaskSnapshot taskSnapshot, int i) {
        super(windowManagerService, i);
        this.mService = windowManagerService;
        this.mSnapshot = taskSnapshot;
    }

    @Override // com.android.server.wm.StartingData
    public StartingSurfaceController.StartingSurface createStartingSurface(ActivityRecord activityRecord) {
        return this.mService.mStartingSurfaceController.createTaskSnapshotSurface(activityRecord, this.mSnapshot);
    }

    @Override // com.android.server.wm.StartingData
    public boolean hasImeSurface() {
        return this.mSnapshot.hasImeSurface();
    }
}
