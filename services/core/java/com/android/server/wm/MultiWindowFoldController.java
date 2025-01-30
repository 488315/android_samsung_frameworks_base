package com.android.server.wm;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class MultiWindowFoldController implements IController {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public static final String TAG = "MultiWindowFoldController";
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public HandlerC2945H f1745mH;
    public int mFoldingState = 0;
    public boolean mIsLidOpened = true;
    public final Rect[] mMainDisplayBounds = {new Rect(), new Rect()};
    public final Rect[] mCoverDisplayBounds = {new Rect(), new Rect()};

    public MultiWindowFoldController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1745mH = new HandlerC2945H(this.mAtm.f1734mH.getLooper());
    }

    public boolean isLidOpened() {
        return this.mIsLidOpened;
    }

    public void initFoldingState(String str) {
        setFoldingState(0, str);
    }

    public boolean isHoldingSplitScreen() {
        return this.mFoldingState == 1;
    }

    public final void setFoldingState(int i, String str) {
        if (this.mFoldingState != i) {
            Slog.d(TAG, "setFoldingState: " + this.mFoldingState + "->" + i + ", reason=" + str);
            this.mFoldingState = i;
        }
    }

    public void onDisplayDeviceTypeChanged(int i) {
        boolean z = i == 0;
        Slog.d(TAG, "onDisplayDeviceTypeChanged opened : " + z);
        if (z) {
            setFoldingState(0, "displayDevice(" + i + ")");
            return;
        }
        if (isSplitScreenActivated()) {
            applyFoldingPolicy();
        }
    }

    public final void applyFoldingPolicy() {
        TaskDisplayArea defaultTaskDisplayArea = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
        if ((defaultTaskDisplayArea.getRootMainStageTask() != null ? defaultTaskDisplayArea.getRootMainStageTask().getTopMostTask() : null) == null) {
            setFoldingState(0, "reset");
        } else {
            setFoldingState(1, "apply_folding_policy");
        }
    }

    public void onStartWakeUpInFoldingState() {
        if (this.mFoldingState == 1 && isSplitScreenActivated() && !this.mAtm.mTaskSupervisor.getKeyguardController().isKeyguardShowing(0)) {
            if (getTopMostTaskWindowingMode() != 1) {
                ActivityTaskManagerService activityTaskManagerService = this.mAtm;
                activityTaskManagerService.mRootWindowContainer.startHomeOnDisplay(activityTaskManagerService.mAmInternal.getCurrentUserId(), "MultiWindowFoldController:wakeup", 0);
            }
            setFoldingState(0, "start_home_by_wakeup");
        }
    }

    public void onKeyguardGoingAway() {
        if (this.mIsLidOpened) {
            return;
        }
        if (this.mFoldingState == 1 && isSplitScreenActivated() && getTopMostTaskWindowingMode() != 1) {
            Task stageRootTask = getStageRootTask();
            if (stageRootTask != null) {
                stageRootTask.setReparentLeafTaskIfRelaunch(true);
            }
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            activityTaskManagerService.mRootWindowContainer.startHomeOnDisplay(activityTaskManagerService.mAmInternal.getCurrentUserId(), "MultiWindowFoldController:keyguard_going_away", 0);
        }
        setFoldingState(0, "keyguard_going_away");
    }

    public final boolean isSplitScreenActivated() {
        return this.mAtm.mRootWindowContainer.getDefaultDisplay().getDefaultTaskDisplayArea().isSplitScreenModeActivated();
    }

    public final Task getStageRootTask() {
        Task rootMainStageTask = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea().getRootMainStageTask();
        if (rootMainStageTask != null) {
            return rootMainStageTask.getRootTask();
        }
        return null;
    }

    public final int getTopMostTaskWindowingMode() {
        Task topMostTask = this.mAtm.mRootWindowContainer.getDefaultDisplay().getDefaultTaskDisplayArea().getTopMostTask();
        if (topMostTask != null) {
            return topMostTask.getWindowingMode();
        }
        return 0;
    }

    public final void initDisplayBounds(boolean z) {
        Display display = this.mAtm.mWindowManager.mDisplayManager.getDisplay(1);
        if (display == null) {
            Slog.w(TAG, "initDisplayBounds: cannot find display!");
            return;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (z) {
            updateCoverDisplayBounds(displayInfo.logicalWidth, displayInfo.logicalHeight);
        } else {
            updateMainDisplayBounds(displayInfo.logicalWidth, displayInfo.logicalHeight);
        }
        Slog.d(TAG, "initDisplayBounds: " + displayInfo + ", isCoverDisplay=" + z);
    }

    public void updateMainDisplayBounds(int i, int i2) {
        boolean z = i <= i2;
        this.mMainDisplayBounds[0].set(0, 0, z ? i : i2, z ? i2 : i);
        Rect rect = this.mMainDisplayBounds[1];
        int i3 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        rect.set(0, 0, i3, i);
    }

    public void updateCoverDisplayBounds(int i, int i2) {
        boolean z = i <= i2;
        this.mCoverDisplayBounds[0].set(0, 0, z ? i : i2, z ? i2 : i);
        Rect rect = this.mCoverDisplayBounds[1];
        int i3 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        rect.set(0, 0, i3, i);
    }

    public Rect getMainDisplayBounds(boolean z) {
        if (this.mMainDisplayBounds[0].isEmpty()) {
            initDisplayBounds(false);
        }
        Rect[] rectArr = this.mMainDisplayBounds;
        return z ? rectArr[0] : rectArr[1];
    }

    public Rect getCoverDisplayBounds(boolean z) {
        if (this.mCoverDisplayBounds[0].isEmpty()) {
            initDisplayBounds(true);
        }
        Rect[] rectArr = this.mCoverDisplayBounds;
        return z ? rectArr[0] : rectArr[1];
    }

    public void scheduleWakeUpInFoldingState(boolean z) {
        this.f1745mH.sendMessageDelayed(this.f1745mH.obtainMessage(1), z ? 0 : 150);
    }

    public void removeWakeUpInFoldingState() {
        if (this.f1745mH.hasMessages(1)) {
            this.f1745mH.removeMessages(1);
        }
    }

    /* renamed from: com.android.server.wm.MultiWindowFoldController$H */
    public final class HandlerC2945H extends Handler {
        public HandlerC2945H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = MultiWindowFoldController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    MultiWindowFoldController.this.onStartWakeUpInFoldingState();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
