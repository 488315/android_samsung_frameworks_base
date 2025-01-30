package com.samsung.android.desktopsystemui.sharedlib.system;

import android.os.RemoteException;
import android.util.Log;
import android.view.IRecentsAnimationController;
import android.view.SurfaceControl;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskSnapshot;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.ThumbnailData;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RecentsAnimationControllerCompat {
    private static final String TAG = "[DS]RecentsAnimationControllerCompat";
    private IRecentsAnimationController mAnimationController;

    public RecentsAnimationControllerCompat() {
    }

    public RecentsAnimationControllerCompat(IRecentsAnimationController iRecentsAnimationController) {
        this.mAnimationController = iRecentsAnimationController;
    }

    public void animateNavigationBarToApp(long j) {
        try {
            this.mAnimationController.animateNavigationBarToApp(j);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to animate the navigation bar to app", e);
        }
    }

    public void cleanupScreenshot() {
        try {
            this.mAnimationController.cleanupScreenshot();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to clean up screenshot of recents animation", e);
        }
    }

    public void detachNavigationBarFromApp(boolean z) {
        try {
            this.mAnimationController.detachNavigationBarFromApp(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to detach the navigation bar from app", e);
        }
    }

    public void finish(boolean z, boolean z2) {
        try {
            this.mAnimationController.finish(z, z2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to finish recents animation", e);
        }
    }

    public boolean removeTask(int i) {
        try {
            return this.mAnimationController.removeTask(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to remove remote animation target", e);
            return false;
        }
    }

    public ThumbnailData screenshotTask(int i) {
        try {
            TaskSnapshot screenshotTask = this.mAnimationController.screenshotTask(i);
            if (screenshotTask != null) {
                return new ThumbnailData(screenshotTask);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to screenshot task", e);
        }
        return new ThumbnailData();
    }

    public void setAnimationTargetsBehindSystemBars(boolean z) {
        try {
            this.mAnimationController.setAnimationTargetsBehindSystemBars(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set whether animation targets are behind system bars", e);
        }
    }

    public void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
        try {
            this.mAnimationController.setDeferCancelUntilNextTransition(z, z2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set deferred cancel with screenshot", e);
        }
    }

    public void setFinishTaskTransaction(int i, PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, SurfaceControl surfaceControl) {
        try {
            this.mAnimationController.setFinishTaskTransaction(i, pictureInPictureSurfaceTransaction, surfaceControl);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to set finish task bounds", e);
        }
    }

    public void setInputConsumerEnabled(boolean z) {
        try {
            this.mAnimationController.setInputConsumerEnabled(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set input consumer enabled state", e);
        }
    }

    public void setWillFinishToHome(boolean z) {
        try {
            this.mAnimationController.setWillFinishToHome(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set overview reached state", e);
        }
    }

    public void hideCurrentInputMethod() {
    }
}
