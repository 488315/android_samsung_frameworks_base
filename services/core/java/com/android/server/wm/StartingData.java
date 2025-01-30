package com.android.server.wm;

import com.android.server.wm.StartingSurfaceController;

/* loaded from: classes3.dex */
public abstract class StartingData {
    public Task mAssociatedTask;
    public boolean mIsDisplayed;
    public boolean mIsTransitionForward;
    public boolean mPrepareRemoveAnimation;
    public boolean mRemoveAfterTransaction;
    public final WindowManagerService mService;
    public final int mTypeParams;
    public boolean mWaitForSyncTransactionCommit;

    public abstract StartingSurfaceController.StartingSurface createStartingSurface(ActivityRecord activityRecord);

    public boolean hasImeSurface() {
        return false;
    }

    public abstract boolean needRevealAnimation();

    public StartingData(WindowManagerService windowManagerService, int i) {
        this.mService = windowManagerService;
        this.mTypeParams = i;
    }
}
