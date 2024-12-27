package com.android.server.wm;

import android.os.Handler;
import android.os.Message;
import android.os.ServiceManager;
import android.util.Slog;

import com.android.server.policy.WindowManagerPolicy;

import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;

import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoverPolicy {
    public HashSet mAppsToBeHiddenBySViewCover;
    public ICoverManager mCoverManager;
    public WindowState mCoverWindow;
    public DisplayContent mDisplayContent;
    public DisplayPolicyExt mDisplayPolicyExt;
    public CoverPolicyHandler mHandler;
    public WindowState mHideSViewCoverWindow;
    public boolean mLastClearCoverState;
    public boolean mLastCoverAppCovered;
    public DisplayContent mViewCoverDisplay;
    public boolean mWallpaperTargetMayChange;
    public WindowManagerPolicy mWindowPolicy;
    public WindowManagerService mWmService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverDisplayPolicy implements ExtraDisplayPolicy {
        public CoverDisplayPolicy() {}

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final int getOtherDisplayId(int i) {
            return isCoverClosed() ? i == 4 ? 0 : -1 : i == 0 ? 4 : -1;
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean hasCoverHome(int i) {
            return i == 4;
        }

        public final boolean isCoverClosed() {
            return CoverPolicy.this.mViewCoverDisplay != null
                    && (((CoverState) WmCoverState.getInstance()).switchState ^ true);
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean isDisplayControlledByPolicy(int i) {
            return i == 4;
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean shouldChooseDefaultTaskDisplayArea(int i) {
            return i == 4;
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean shouldNotHandleForcedResizableTaskIfNeeded(int i, int i2) {
            return i == 4 && i2 == 2;
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean shouldNotTopDisplay(int i) {
            if (isCoverClosed()) {
                if (i != 0) {
                    return false;
                }
            } else if (i != 4) {
                return false;
            }
            return true;
        }

        @Override // com.android.server.wm.ExtraDisplayPolicy
        public final boolean shouldSkipAppTransition(int i) {
            return i == 4 || isCoverClosed();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverPolicyHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {}
    }

    public final synchronized ICoverManager getCoverManager() {
        try {
            if (this.mCoverManager == null) {
                ICoverManager asInterface =
                        ICoverManager.Stub.asInterface(ServiceManager.getService("cover"));
                this.mCoverManager = asInterface;
                if (asInterface == null) {
                    Slog.w("CoverPolicy", "warning: no COVER_MANAGER_SERVICE");
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mCoverManager;
    }

    public boolean shouldApplyAodPolicy() {
        return this.mDisplayContent.isDefaultDisplay;
    }

    public final void updateCoverStateLocked(CoverState coverState) {
        WindowState windowState;
        WmCoverState wmCoverState = WmCoverState.getInstance();
        boolean z = ((CoverState) wmCoverState).switchState;
        boolean z2 = !z;
        if (coverState.type != 2 || coverState.switchState != z) {
            wmCoverState.copyFrom(coverState);
        }
        switch (((CoverState) wmCoverState).type) {
            case 15:
            case 16:
            case 17:
                if (!(!((CoverState) wmCoverState).switchState)
                        && (windowState = this.mHideSViewCoverWindow) != null) {
                    windowState.mDisableHideSViewOnce = true;
                    break;
                }
                break;
        }
        DisplayContent displayContent = this.mViewCoverDisplay;
        WindowManagerService windowManagerService = this.mWmService;
        if (displayContent != null) {
            windowManagerService.mExt.moveDisplayToTop(
                    ((CoverState) WmCoverState.getInstance()).switchState ^ true ? 4 : 0);
        }
        if (!z2 && (!((CoverState) wmCoverState).switchState)) {
            windowManagerService.mTaskSnapshotController.snapshotForSleeping(0);
        }
        DisplayContent displayContent2 = this.mDisplayContent;
        if (displayContent2.updateOrientation(false)) {
            displayContent2.sendNewConfiguration();
        }
        displayContent2.setLayoutNeeded();
        windowManagerService.mWindowPlacerLocked.performSurfacePlacement(false);
        this.mHandler.post(new CoverPolicy$$ExternalSyntheticLambda0(this));
    }
}
