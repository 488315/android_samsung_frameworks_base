package com.android.server.wm;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;

public final class MultiWindowFoldController implements IController {
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;
    public H mH;
    public int mFoldingState = 0;
    public final Rect[] mMainDisplayBounds = {new Rect(), new Rect()};
    public final Rect[] mCoverDisplayBounds = {new Rect(), new Rect()};

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock =
                    MultiWindowFoldController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    MultiWindowFoldController multiWindowFoldController =
                            MultiWindowFoldController.this;
                    if (multiWindowFoldController.mFoldingState == 1
                            && multiWindowFoldController
                                    .mAtm
                                    .mRootWindowContainer
                                    .mDefaultDisplay
                                    .getDefaultTaskDisplayArea()
                                    .isSplitScreenModeActivated()) {
                        ActivityTaskManagerService activityTaskManagerService =
                                multiWindowFoldController.mAtm;
                        if (!activityTaskManagerService.mTaskSupervisor.mKeyguardController
                                .isKeyguardShowing(0)) {
                            Task topMostTask =
                                    multiWindowFoldController
                                            .mAtm
                                            .mRootWindowContainer
                                            .mDefaultDisplay
                                            .getDefaultTaskDisplayArea()
                                            .getTopMostTask();
                            if ((topMostTask != null ? topMostTask.getWindowingMode() : 0) != 1) {
                                activityTaskManagerService.mRootWindowContainer.startHomeOnDisplay(
                                        "MultiWindowFoldController:wakeup",
                                        activityTaskManagerService.mAmInternal.getCurrentUserId(),
                                        0,
                                        false,
                                        false);
                            }
                            multiWindowFoldController.setFoldingState(0, "start_home_by_wakeup");
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public MultiWindowFoldController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public final void initDisplayBounds(boolean z) {
        Display display = this.mAtm.mWindowManager.mDisplayManager.getDisplay(1);
        if (display == null) {
            Slog.w("MultiWindowFoldController", "initDisplayBounds: cannot find display!");
            return;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (z) {
            updateCoverDisplayBounds(displayInfo.logicalWidth, displayInfo.logicalHeight);
        } else {
            updateMainDisplayBounds(displayInfo.logicalWidth, displayInfo.logicalHeight);
        }
        Slog.d(
                "MultiWindowFoldController",
                "initDisplayBounds: " + displayInfo + ", isCoverDisplay=" + z);
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    public final void setFoldingState(int i, String str) {
        if (this.mFoldingState != i) {
            StringBuilder sb = new StringBuilder("setFoldingState: ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    this.mFoldingState, i, "->", ", reason=", sb);
            BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "MultiWindowFoldController");
            this.mFoldingState = i;
        }
    }

    public final void updateCoverDisplayBounds(int i, int i2) {
        boolean z = i <= i2;
        this.mCoverDisplayBounds[0].set(0, 0, z ? i : i2, z ? i2 : i);
        Rect rect = this.mCoverDisplayBounds[1];
        int i3 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        rect.set(0, 0, i3, i);
    }

    public final void updateMainDisplayBounds(int i, int i2) {
        boolean z = i <= i2;
        this.mMainDisplayBounds[0].set(0, 0, z ? i : i2, z ? i2 : i);
        Rect rect = this.mMainDisplayBounds[1];
        int i3 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        rect.set(0, 0, i3, i);
    }
}
