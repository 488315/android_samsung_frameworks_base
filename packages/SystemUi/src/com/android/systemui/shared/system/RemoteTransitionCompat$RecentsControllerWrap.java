package com.android.systemui.shared.system;

import android.app.ActivityTaskManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRecentsAnimationController;
import android.view.SurfaceControl;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskSnapshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class RemoteTransitionCompat$RecentsControllerWrap extends IRecentsAnimationController.Default {
    public PictureInPictureSurfaceTransaction mPipTransaction = null;
    public boolean mWillFinishToHome = false;

    public final void detachNavigationBarFromApp(boolean z) {
        try {
            ActivityTaskManager.getService().detachNavigationBarFromApp((IBinder) null);
        } catch (RemoteException e) {
            Log.e("RemoteTransitionCompat", "Failed to detach the navigation bar from app", e);
        }
    }

    public final void finish(boolean z, boolean z2) {
        Log.e("RemoteTransitionCompat", "Duplicate call to finish", new RuntimeException());
    }

    public final boolean removeTask(int i) {
        return false;
    }

    public final TaskSnapshot screenshotTask(int i) {
        try {
            return ActivityTaskManager.getService().takeTaskSnapshot(i, true);
        } catch (RemoteException e) {
            Log.e("RemoteTransitionCompat", "Failed to screenshot task", e);
            return null;
        }
    }

    public final void setFinishTaskTransaction(int i, PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, SurfaceControl surfaceControl) {
        this.mPipTransaction = pictureInPictureSurfaceTransaction;
    }

    public final void setInputConsumerEnabled(boolean z) {
        if (z) {
            try {
                ActivityTaskManager.getService().setFocusedTask(0);
            } catch (RemoteException e) {
                Log.e("RemoteTransitionCompat", "Failed to set focused task", e);
            }
        }
    }

    public final void setWillFinishToHome(boolean z) {
        this.mWillFinishToHome = z;
    }

    public final void animateNavigationBarToApp(long j) {
    }

    public final void setAnimationTargetsBehindSystemBars(boolean z) {
    }

    public final void cleanupScreenshot() {
    }

    public final void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
    }
}
