package com.samsung.android.biometrics.app.setting;

import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class TaskStackObserver extends TaskStackListener {
    public boolean mIsWatching;
    public SysUiManager$$ExternalSyntheticLambda3 mListener;

    public final void observe(
            SysUiManager$$ExternalSyntheticLambda3 sysUiManager$$ExternalSyntheticLambda3) {
        this.mListener = sysUiManager$$ExternalSyntheticLambda3;
        boolean z = sysUiManager$$ExternalSyntheticLambda3 != null;
        if (this.mIsWatching == z) {
            return;
        }
        try {
            if (z) {
                ActivityTaskManager.getService().registerTaskStackListener(this);
                this.mIsWatching = true;
            } else {
                ActivityTaskManager.getService().unregisterTaskStackListener(this);
                this.mIsWatching = false;
            }
        } catch (RemoteException e) {
            Log.w("BSS_TaskStackObserver", "observe: " + e.getMessage(), e);
        }
    }

    public final void onTaskFocusChanged(int i, boolean z) {
        SysUiManager$$ExternalSyntheticLambda3 sysUiManager$$ExternalSyntheticLambda3 =
                this.mListener;
        if (sysUiManager$$ExternalSyntheticLambda3 != null) {
            SysUiManager sysUiManager = sysUiManager$$ExternalSyntheticLambda3.f$0;
            sysUiManager.mH.removeMessages(9);
            sysUiManager.mH.sendEmptyMessageDelayed(9, 100L);
        }
    }

    public final void onTaskStackChanged() {
        SysUiManager$$ExternalSyntheticLambda3 sysUiManager$$ExternalSyntheticLambda3 =
                this.mListener;
        if (sysUiManager$$ExternalSyntheticLambda3 != null) {
            SysUiManager sysUiManager = sysUiManager$$ExternalSyntheticLambda3.f$0;
            sysUiManager.mH.removeMessages(9);
            sysUiManager.mH.sendEmptyMessageDelayed(9, 100L);
        }
    }
}
