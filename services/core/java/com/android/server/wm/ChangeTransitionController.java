package com.android.server.wm;

import android.content.Intent;
import android.graphics.Rect;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Slog;
import android.window.TransitionRequestInfo;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;

import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ChangeTransitionController implements IController {
    public static final boolean DISABLE_LEGACY_RESIZE_TRANSITION =
            SystemProperties.getBoolean("persist.mt.debug.disable_legacy_resize_transition", false);
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;
    public H mH;
    public TransitionController mTransitionController;
    public WindowManagerService mWm;
    public final ArraySet mSyncDeferredAllDrawnApps = new ArraySet();
    public final ArraySet mSyncDeferredTrampolineApps = new ArraySet();
    public final HashMap mTransitionToChangingPipTask = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Intent intent;
            int i = message.what;
            if (i == 1) {
                WindowManagerGlobalLock windowManagerGlobalLock =
                        ChangeTransitionController.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        Slog.w(
                                "ChangeTransitionController",
                                "SYNC_ACTIVITY_TIMEOUT!! "
                                        + ChangeTransitionController.this
                                                .mSyncDeferredAllDrawnApps);
                        ChangeTransitionController.this.mSyncDeferredAllDrawnApps.clear();
                        ChangeTransitionController.this.mWm.requestTraversal();
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 2) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock2 =
                    ChangeTransitionController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    Slog.w(
                            "ChangeTransitionController",
                            "SYNC_ACTIVITY_TIMEOUT_FOR_TRAMPOLINE!! "
                                    + ChangeTransitionController.this.mSyncDeferredTrampolineApps);
                    Iterator it =
                            ChangeTransitionController.this.mSyncDeferredTrampolineApps.iterator();
                    while (it.hasNext()) {
                        ActivityRecord activityRecord = (ActivityRecord) it.next();
                        if (activityRecord != null && (intent = activityRecord.intent) != null) {
                            intent.setAiKeyAppLaunch(false);
                        }
                    }
                    ChangeTransitionController.this.mSyncDeferredTrampolineApps.clear();
                    ChangeTransitionController.this.mWm.requestTraversal();
                } catch (Throwable th) {
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public ChangeTransitionController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public static void handleEnteringPipIfNeeded(
            ActivityRecord activityRecord, Transition transition) {
        if (!activityRecord.mAutoEnteringPip
                && activityRecord.mState == ActivityRecord.State.PAUSED
                && transition != null
                && transition.mType == 10
                && activityRecord.getDisplayContent().hasTopFixedRotationLaunchingApp()) {
            Slog.d("ChangeTransitionController", "handleEnteringPipIfNeeded: r=" + activityRecord);
            activityRecord.setHiddenWhileEnteringPinnedMode("fixed_rotation(paused)", true);
        }
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[ChangeTransitionController]");
        if (!this.mSyncDeferredAllDrawnApps.isEmpty()) {
            printWriter.println("  mSyncDeferredAllDrawnApps=" + this.mSyncDeferredAllDrawnApps);
        }
        if (this.mTransitionToChangingPipTask.isEmpty()) {
            return;
        }
        printWriter.println("  mTransitionToChangingPipTask=" + this.mTransitionToChangingPipTask);
    }

    public final Transition.ChangeInfo findCollectingChangeInfo(WindowContainer windowContainer) {
        if (this.mTransitionController.isCollecting()) {
            return (Transition.ChangeInfo)
                    this.mTransitionController.mCollectingTransition.mChanges.get(windowContainer);
        }
        return null;
    }

    public final void handleRequestFullscreenToSplitScreen() {
        TaskDisplayArea defaultTaskDisplayArea =
                this.mWm.mRoot.mDefaultDisplay.getDefaultTaskDisplayArea();
        DragState dragState = this.mWm.mDragDropController.mDragState;
        final int i = dragState != null ? dragState.mCallingTaskIdToHide : -1;
        Task rootTask =
                defaultTaskDisplayArea.getRootTask(
                        new Predicate() { // from class:
                                          // com.android.server.wm.ChangeTransitionController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Task task = (Task) obj;
                                return task.inFullscreenWindowingMode()
                                        && task.isActivityTypeStandard()
                                        && !task.isFullscreenRootForStageTask()
                                        && task.isVisible()
                                        && task.mTaskId != i;
                            }
                        });
        if (rootTask == null
                || !rootTask.supportsMultiWindowInDisplayArea(defaultTaskDisplayArea, false)
                || rootTask.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Slog.d(
                "ChangeTransitionController",
                "handleRequestFullscreenToSplitScreen: #" + rootTask.mTaskId);
        ActivityRecord topMostActivity = rootTask.getTopMostActivity();
        boolean z =
                rootTask.getWindow(new ChangeTransitionController$$ExternalSyntheticLambda4(2))
                        == null;
        boolean z2 =
                (topMostActivity == null || topMostActivity.finishing)
                        && rootTask.getTopVisibleActivity(true, false) == null;
        if (z || z2) {
            requestDisplayChangeTransition(
                    defaultTaskDisplayArea.mDisplayContent.mDisplayId,
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder("full_to_split_requested("),
                            z ? "translucent" : "finishing",
                            ")"),
                    false);
        } else {
            requestChangeTransition(
                    rootTask,
                    this.mAtm.mNaturalSwitchingController.mNaturalSwitchingRunning ? 4 : 1,
                    rootTask.getWindowingMode(),
                    new Rect(rootTask.getBounds()),
                    "request(full_to_split)",
                    0);
        }
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(DisplayThread.get().getLooper());
    }

    public final boolean isInChangeTransition(WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mTransitionController.mCollectingTransition;
        return (transition == null
                        || (changeInfo =
                                        (Transition.ChangeInfo)
                                                transition.mChanges.get(windowContainer))
                                == null
                        || changeInfo.mChangeLeash == null)
                ? false
                : true;
    }

    public final void onActivityLocalRelaunched(ActivityRecord activityRecord) {
        Task task = activityRecord.task;
        if (task == null || activityRecord.allDrawn || !isInChangeTransition(task)) {
            Slog.w(
                    "ChangeTransitionController",
                    "onActivityLocalRelaunched: failed, r=" + activityRecord);
        } else {
            if (this.mSyncDeferredAllDrawnApps.contains(activityRecord)) {
                return;
            }
            this.mSyncDeferredAllDrawnApps.add(activityRecord);
            Slog.d(
                    "ChangeTransitionController",
                    "addToSyncDeferredForAllDrawn: r="
                            + activityRecord
                            + ", reason=local_relaunch, num_remain="
                            + this.mSyncDeferredAllDrawnApps.size());
            if (this.mSyncDeferredAllDrawnApps.size() == 1) {
                this.mH.removeMessages(1);
                this.mH.sendEmptyMessageDelayed(1, 2000L);
            }
        }
    }

    public final void removeFromSyncDeferredForAllDrawn(ActivityRecord activityRecord, String str) {
        if (this.mSyncDeferredAllDrawnApps.contains(activityRecord)) {
            this.mSyncDeferredAllDrawnApps.remove(activityRecord);
            Slog.d(
                    "ChangeTransitionController",
                    "removeFromSyncDeferredForAllDrawn: r="
                            + activityRecord
                            + ", reason="
                            + str
                            + ", num_remain="
                            + this.mSyncDeferredAllDrawnApps.size());
            if (this.mSyncDeferredAllDrawnApps.isEmpty()) {
                this.mH.removeMessages(1);
            }
        }
    }

    public final void requestChangeTransition(
            Task task, int i, int i2, Rect rect, String str, int i3) {
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(task);
        if (findCollectingChangeInfo != null && findCollectingChangeInfo.mChangeLeash != null) {
            Slog.d(
                    "ChangeTransitionController",
                    "requestStartChangeTransition: skip, already collecting, #"
                            + task.mTaskId
                            + ", reason="
                            + str);
            return;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && task.isSplitAdjustedMinimalBounds()) {
            rect =
                    task.isSplitAdjustedMinimalBounds()
                            ? new Rect(task.getParent().getBounds())
                            : new Rect(task.getBounds());
        }
        Rect rect2 = rect;
        Transition createTransition =
                !this.mTransitionController.isCollecting()
                        ? this.mTransitionController.createTransition(6, 0)
                        : null;
        StringBuilder sb = new StringBuilder("requestStartChangeTransition: tid #");
        sb.append(task.mTaskId);
        sb.append(", mode=");
        sb.append(MultiWindowManager.changeTransitModeToString(i));
        sb.append(
                i3 != 0
                        ? AudioChannelMask$$ExternalSyntheticOutline0.m(
                                new StringBuilder(", flags=0x"), i3)
                        : "");
        sb.append(", newTransit=");
        sb.append(createTransition);
        sb.append(", reason=");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "ChangeTransitionController");
        this.mTransitionController.collect(task);
        updateChangeInfo(task, i, i2, rect2, i3);
        this.mTransitionController.collectVisibleChange(task);
        if (createTransition != null) {
            this.mTransitionController.requestStartTransition(createTransition, task, null, null);
            createTransition.setReady(task, true);
        }
    }

    public final void requestDisplayChangeTransition(int i, String str, boolean z) {
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
            if (displayContent == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i,
                        "requestDisplayChangeTransition: cannot find display #",
                        "ChangeTransitionController");
                return;
            }
            int i2 = displayContent.mDisplayRotation.mRotation;
            boolean isCollecting = this.mTransitionController.isCollecting();
            StringBuilder m =
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i,
                            "requestDisplayChangeTransition: #",
                            ", reason=",
                            str,
                            ", isCollecting=");
            m.append(isCollecting);
            m.append("");
            Slog.d("ChangeTransitionController", m.toString());
            if (isCollecting) {
                Transition transition = this.mTransitionController.mCollectingTransition;
                Transition.ChangeInfo changeInfo =
                        (Transition.ChangeInfo) transition.mChanges.get(displayContent);
                if (changeInfo != null) {
                    changeInfo.mKnownConfigChanges = 536870912;
                }
                displayContent.collectDisplayChange(transition);
            } else {
                displayContent.requestChangeTransition(
                        536870912,
                        new TransitionRequestInfo.DisplayChange(displayContent.mDisplayId, i2, i2));
            }
            Transition transition2 = this.mTransitionController.mCollectingTransition;
            if (transition2 == null) {
                Slog.e(
                        "ChangeTransitionController",
                        "requestDisplayChangeTransition: failed, collecting transition is null!");
                return;
            }
            Transition.ChangeInfo changeInfo2 =
                    (Transition.ChangeInfo) transition2.mChanges.get(displayContent);
            if (changeInfo2 != null) {
                changeInfo2.mKnownConfigChanges = 536870912;
            }
            Transition.ChangeInfo changeInfo3 =
                    (Transition.ChangeInfo) transition2.mChanges.get(displayContent);
            if (changeInfo3 != null) {
                int i3 = changeInfo3.mFlags;
                changeInfo3.mFlags = 65536 | i3;
                if (z) {
                    changeInfo3.mFlags = i3 | 196608;
                }
            }
            this.mTransitionController.collectForDisplayAreaChange(displayContent);
        }
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
        this.mTransitionController = this.mAtm.mWindowOrganizerController.mTransitionController;
    }

    public final void updateChangeInfo(
            WindowContainer windowContainer, int i, int i2, Rect rect, int i3) {
        String str;
        if (!this.mTransitionController.isCollecting()) {
            Slog.w(
                    "ChangeTransitionController",
                    "updateChangeInfo: failed, collecting false, wc=" + windowContainer);
            return;
        }
        boolean z = windowContainer.asTask() != null && i2 == 5;
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(windowContainer);
        if (findCollectingChangeInfo == null) {
            Slog.w(
                    "ChangeTransitionController",
                    "updateChangeInfo: failed, changeInfo is null, wc=" + windowContainer);
            return;
        }
        findCollectingChangeInfo.mChangeTransitMode = i;
        findCollectingChangeInfo.mChangeTransitFlags = i3;
        findCollectingChangeInfo.mAbsoluteBounds.set(rect);
        if (i2 != 0) {
            findCollectingChangeInfo.mWindowingMode = i2;
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z) {
            int freeformThickness = windowContainer.getFreeformThickness();
            findCollectingChangeInfo.mFreezeOutsets.set(
                    freeformThickness, freeformThickness, freeformThickness, freeformThickness);
            if (windowContainer.inSplitScreenWindowingMode()
                    && windowContainer.getTaskDisplayArea() != null) {
                findCollectingChangeInfo.mCommonAncestor = windowContainer.getTaskDisplayArea();
            }
        }
        StringBuilder sb = new StringBuilder("updateChangeInfo: wc=");
        sb.append(windowContainer);
        sb.append(", ");
        sb.append(MultiWindowManager.changeTransitModeToString(i));
        if (z) {
            str = ", outsets=" + findCollectingChangeInfo.mFreezeOutsets;
        } else {
            str = "";
        }
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "ChangeTransitionController");
    }
}
