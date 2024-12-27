package com.android.server.wm;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

public abstract class StartingData {
    public Task mAssociatedTask;
    public boolean mIsDisplayed;
    public boolean mIsTransitionForward;
    public boolean mPrepareRemoveAnimation;
    public int mRemoveAfterTransaction = 0;
    public boolean mResizedFromTransfer;
    public final WindowManagerService mService;
    public int mTransitionId;
    public final int mTypeParams;
    public boolean mWaitForSyncTransactionCommit;

    public StartingData(WindowManagerService windowManagerService, int i) {
        this.mService = windowManagerService;
        this.mTypeParams = i;
    }

    public abstract StartingSurfaceController.StartingSurface createStartingSurface(
            ActivityRecord activityRecord);

    public boolean hasImeSurface() {
        return false;
    }

    public abstract boolean needRevealAnimation();

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" waitForSyncTransactionCommit=");
        sb.append(this.mWaitForSyncTransactionCommit);
        sb.append(" removeAfterTransaction= ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mRemoveAfterTransaction, sb, "}");
    }
}
