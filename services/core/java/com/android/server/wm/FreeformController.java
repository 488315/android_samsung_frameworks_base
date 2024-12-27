package com.android.server.wm;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.android.internal.os.SomeArgs;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.google.android.collect.Sets;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public final class FreeformController implements IController {
    public final ActivityTaskManagerService mAtm;
    public boolean mBlockToAddForceHideFreeformTasks;
    public boolean mDeferMinimizeCallback;
    public WindowState mForceHideFreeformRequester;
    public WindowState mForceHideMinimizeRequester;
    public final WindowManagerGlobalLock mGlobalLock;
    public H mH;
    public boolean mIsForceHideWithoutAnimation;
    public final MinimizeContainerServiceBinder mMinimizeContainerServiceBinder;
    public final SmartPopupViewServiceBinder mSmartPopupViewServiceBinder;
    public WindowState mTmpForceHideFreeformRequester;
    public WindowState mTmpForceHideMinimizeRequester;
    public WindowState mTmpWindow;
    public final TransitionController mTransitionController;
    public final Rect mTmpRect = new Rect();
    public int mMaxFreeformOverWrittenCnt = -1;
    public int mMaxDexFreeformOverWrittenCnt = -1;
    public final ArrayList mDeferredCallbacks = new ArrayList();
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final PointF mFreeformContainerPoint = new PointF(-1.0f, -1.0f);
    public int mFreeformCaptionType = -1;
    public final ArrayList mForceHiddenFreeformContainers = new ArrayList();
    public final ArrayList mForceHiddenFreeformTasks = new ArrayList();
    public final SparseArray mFreeformCornerRadius = new SparseArray();

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            boolean z = true;
            int i2 = 0;
            if (i == 1) {
                Toast.makeText(
                                new ContextThemeWrapper(
                                        FreeformController.this.mAtm.mContext,
                                        R.style.Theme.DeviceDefault.Light),
                                FreeformController.this
                                        .mAtm
                                        .mContext
                                        .getResources()
                                        .getString(R.string.indeterminate_progress_11),
                                0)
                        .show();
                return;
            }
            switch (i) {
                case 101:
                    FreeformController.this.mMinimizeContainerServiceBinder.bindServiceIfNeeded(
                            (String) message.obj);
                    return;
                case 102:
                    FreeformController.this.mMinimizeContainerServiceBinder.unbindServiceIfNeeded(
                            (String) message.obj);
                    return;
                case 103:
                    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                        FreeformController.this.mSmartPopupViewServiceBinder.bindServiceIfNeeded(
                                (String) message.obj);
                        return;
                    }
                    return;
                case 104:
                    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                        FreeformController.this.mSmartPopupViewServiceBinder.unbindServiceIfNeeded(
                                (String) message.obj);
                        return;
                    }
                    return;
                default:
                    switch (i) {
                        case 201:
                            synchronized (FreeformController.this.mCallbacks) {
                                SomeArgs someArgs = (SomeArgs) message.obj;
                                ComponentName componentName = (ComponentName) someArgs.arg1;
                                int i3 = someArgs.argi1;
                                int i4 = someArgs.argi2;
                                int i5 = someArgs.argi3;
                                int i6 = someArgs.argi4;
                                if (someArgs.argi5 != 1) {
                                    z = false;
                                }
                                int beginBroadcast =
                                        FreeformController.this.mCallbacks.beginBroadcast();
                                while (i2 < beginBroadcast) {
                                    int i7 = i6;
                                    try {
                                        FreeformController.this
                                                .mCallbacks
                                                .getBroadcastItem(i2)
                                                .onMinimized(componentName, i3, i4, i5, i6, z);
                                    } catch (RemoteException unused) {
                                        Slog.e(
                                                "FreeformController",
                                                "onMinimized, RemoteException occurred");
                                    }
                                    i2++;
                                    i6 = i7;
                                }
                                FreeformController.this.mCallbacks.finishBroadcast();
                            }
                            return;
                        case 202:
                            synchronized (FreeformController.this.mCallbacks) {
                                int i8 = ((SomeArgs) message.obj).argi1;
                                int beginBroadcast2 =
                                        FreeformController.this.mCallbacks.beginBroadcast();
                                while (i2 < beginBroadcast2) {
                                    try {
                                        FreeformController.this
                                                .mCallbacks
                                                .getBroadcastItem(i2)
                                                .onUnminimized(i8);
                                    } catch (RemoteException unused2) {
                                        Slog.e(
                                                "FreeformController",
                                                "onRestored, RemoteException occurred");
                                    }
                                    i2++;
                                }
                                FreeformController.this.mCallbacks.finishBroadcast();
                            }
                            return;
                        case 203:
                            synchronized (FreeformController.this.mCallbacks) {
                                int i9 = ((SomeArgs) message.obj).argi1;
                                int beginBroadcast3 =
                                        FreeformController.this.mCallbacks.beginBroadcast();
                                while (i2 < beginBroadcast3) {
                                    try {
                                        FreeformController.this
                                                .mCallbacks
                                                .getBroadcastItem(i2)
                                                .onMinimizeAnimationEnd(i9);
                                    } catch (RemoteException unused3) {
                                        Slog.e(
                                                "FreeformController",
                                                "onMinimizeAnimationEnd, RemoteException occurred");
                                    }
                                    i2++;
                                }
                                FreeformController.this.mCallbacks.finishBroadcast();
                            }
                            return;
                        case 204:
                            synchronized (FreeformController.this.mCallbacks) {
                                SomeArgs someArgs2 = (SomeArgs) message.obj;
                                int i10 = someArgs2.argi1;
                                Point point = (Point) someArgs2.arg1;
                                int beginBroadcast4 =
                                        FreeformController.this.mCallbacks.beginBroadcast();
                                while (i2 < beginBroadcast4) {
                                    try {
                                        FreeformController.this
                                                .mCallbacks
                                                .getBroadcastItem(i2)
                                                .onTaskMoveStarted(i10, point);
                                    } catch (RemoteException unused4) {
                                        Slog.e(
                                                "FreeformController",
                                                "onTaskMoveStarted, RemoteException occurred");
                                    }
                                    i2++;
                                }
                                FreeformController.this.mCallbacks.finishBroadcast();
                            }
                            return;
                        case 205:
                            synchronized (FreeformController.this.mCallbacks) {
                                SomeArgs someArgs3 = (SomeArgs) message.obj;
                                int i11 = someArgs3.argi1;
                                IRemoteCallback iRemoteCallback = (IRemoteCallback) someArgs3.arg1;
                                int beginBroadcast5 =
                                        FreeformController.this.mCallbacks.beginBroadcast();
                                while (i2 < beginBroadcast5) {
                                    try {
                                        FreeformController.this
                                                .mCallbacks
                                                .getBroadcastItem(i2)
                                                .onTaskMoveEnded(i11, iRemoteCallback);
                                    } catch (RemoteException unused5) {
                                        Slog.e(
                                                "FreeformController",
                                                "onTaskMoveEnded, RemoteException occurred");
                                    }
                                    i2++;
                                }
                                FreeformController.this.mCallbacks.finishBroadcast();
                            }
                            return;
                        default:
                            return;
                    }
            }
        }
    }

    public FreeformController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mTransitionController =
                activityTaskManagerService.mWindowOrganizerController.mTransitionController;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        MinimizeContainerServiceBinder minimizeContainerServiceBinder =
                new MinimizeContainerServiceBinder(activityTaskManagerService);
        minimizeContainerServiceBinder.mService.setComponent(
                new ComponentName(
                        Constants.SYSTEMUI_PACKAGE_NAME,
                        "com.android.wm.shell.freeform.MinimizeContainerService"));
        this.mMinimizeContainerServiceBinder = minimizeContainerServiceBinder;
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            SmartPopupViewServiceBinder smartPopupViewServiceBinder =
                    new SmartPopupViewServiceBinder(activityTaskManagerService);
            smartPopupViewServiceBinder.mService.setComponent(
                    new ComponentName(
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            "com.android.wm.shell.freeform.SmartPopupViewService"));
            this.mSmartPopupViewServiceBinder = smartPopupViewServiceBinder;
        }
    }

    public static boolean useAlwaysOnTopFreeform(int i, DisplayContent displayContent) {
        return (displayContent == null || displayContent.isDesktopModeEnabled() || i != 5)
                ? false
                : true;
    }

    public final void continueMinimizeStateChangedCallbacks() {
        if (this.mDeferMinimizeCallback) {
            this.mDeferMinimizeCallback = false;
            Iterator it = this.mDeferredCallbacks.iterator();
            while (it.hasNext()) {
                this.mH.sendMessage((Message) it.next());
            }
            this.mDeferredCallbacks.clear();
        }
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[FreeformController]");
        if (!this.mForceHiddenFreeformContainers.isEmpty()) {
            printWriter.println(
                    "  mForceHiddenFreeformWindows=" + this.mForceHiddenFreeformContainers);
        }
        if (this.mForceHideFreeformRequester != null) {
            printWriter.println(
                    "  mForceHideFreeformRequester=" + this.mForceHideFreeformRequester);
        }
        if (this.mForceHideMinimizeRequester != null) {
            printWriter.println(
                    "  mForceHideMinimizeRequester=" + this.mForceHideMinimizeRequester);
        }
        if (!this.mForceHiddenFreeformTasks.isEmpty()) {
            printWriter.println("  mForceHiddenFreeformTasks=" + this.mForceHiddenFreeformTasks);
        }
        if (this.mBlockToAddForceHideFreeformTasks) {
            printWriter.println("  mBlockToAddForceHideFreeformTasks=true");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mMaxFreeformOverWrittenCnt="),
                        this.mMaxFreeformOverWrittenCnt,
                        printWriter,
                        "  mMaxDexFreeformOverWrittenCnt="),
                this.mMaxDexFreeformOverWrittenCnt,
                printWriter);
        this.mMinimizeContainerServiceBinder.dumpLocked(printWriter);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            this.mSmartPopupViewServiceBinder.dumpLocked(printWriter);
        }
        printWriter.println();
    }

    public final ParceledListSlice getMinimizedFreeformTasksForCurrentUser() {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.mDefaultDisplay;
                if (displayContent != null) {
                    ArrayList arrayList2 = new ArrayList();
                    displayContent.forAllRootTasks(
                            new DisplayContent$$ExternalSyntheticLambda8(arrayList2, false));
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        Task task = (Task) it.next();
                        if (task.isMinimized()
                                && this.mAtm.mAmInternal.isCurrentProfile(task.mUserId)) {
                            arrayList.add(task.getTaskInfo());
                        }
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    public final void minimizeExcessiveVisibleFreeformLocked(TaskDisplayArea taskDisplayArea) {
        int i;
        ActivityTaskManagerService activityTaskManagerService;
        int indexOf = taskDisplayArea.mChildren.indexOf(taskDisplayArea.mRootHomeTask);
        boolean isDesktopModeEnabled =
                taskDisplayArea.mDisplayContent.getConfiguration().isDesktopModeEnabled();
        int i2 = 0;
        boolean z = taskDisplayArea.mDisplayContent.getConfiguration().dexMode == 3;
        if (!isDesktopModeEnabled) {
            i = this.mMaxFreeformOverWrittenCnt;
            if (i <= 0) {
                i = 5;
            }
        } else if (CoreRune.MT_NEW_DEX_LIMIT_RUNNING_APPS && z) {
            i = 15;
        } else {
            i = this.mMaxDexFreeformOverWrittenCnt;
            if (i <= 0) {
                i = 20;
            }
        }
        int[] iArr = new int[1];
        taskDisplayArea.forAllRootTasks(new TaskDisplayArea$$ExternalSyntheticLambda0(2, iArr));
        int i3 = iArr[0] - 1;
        while (true) {
            activityTaskManagerService = this.mAtm;
            if (i3 <= indexOf) {
                break;
            }
            Task asTask = taskDisplayArea.getChildAt(i3).asTask();
            if (asTask != null
                    && !asTask.mCreatedByOrganizer
                    && asTask.shouldBeVisible(null)
                    && asTask.getWindowingMode() == 5
                    && (i2 = i2 + 1) > i) {
                activityTaskManagerService.mMultiTaskingController.minimizeTaskLocked(
                        -1, -1, asTask, true);
            }
            i3--;
        }
        if (i2 > i) {
            final Context createDisplayContext =
                    new ContextThemeWrapper(
                                    activityTaskManagerService.mContext,
                                    R.style.Theme.DeviceDefault.Light)
                            .createDisplayContext(taskDisplayArea.getDisplayContent().mDisplay);
            final String quantityString =
                    isDesktopModeEnabled
                            ? activityTaskManagerService
                                    .mContext
                                    .getResources()
                                    .getQuantityString(
                                            R.plurals.duration_days_shortest_future,
                                            i,
                                            Integer.valueOf(i))
                            : activityTaskManagerService
                                    .mContext
                                    .getResources()
                                    .getString(R.string.mediasize_iso_a6, Integer.valueOf(i));
            this.mH.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.FreeformController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Toast.makeText(createDisplayContext, quantityString, 0).show();
                        }
                    });
        }
    }

    public final void notifyFreeformMinimizeAnimationEnd(Task task) {
        if (task.isDexMode()) {
            return;
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = task.mTaskId;
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            task.updateMinimizeChangeInfo(0, -1, -1);
        }
        H h = this.mH;
        h.sendMessage(h.obtainMessage(203, obtain));
    }

    public final void notifyFreeformMinimizeStateChanged(int i, int i2, Task task, boolean z) {
        if (task.isDexMode()) {
            return;
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = task.realActivity;
        obtain.argi1 = task.mTaskId;
        obtain.argi2 = task.mUserId;
        obtain.argi3 = i;
        obtain.argi4 = i2;
        obtain.argi5 = z ? 1 : 0;
        Message obtainMessage = this.mH.obtainMessage(task.mIsMinimized ? 201 : 202, obtain);
        if (this.mDeferMinimizeCallback) {
            this.mDeferredCallbacks.add(0, obtainMessage);
        } else {
            this.mH.sendMessage(obtainMessage);
        }
    }

    public final void releaseForceHidePolicyIfNeededLocked(WindowState windowState) {
        Task task = windowState.getTask();
        if (this.mForceHiddenFreeformContainers.contains(windowState)) {
            this.mForceHiddenFreeformContainers.remove(windowState);
            windowState.show(false);
        }
        if (!this.mForceHiddenFreeformTasks.contains(task) || task == null) {
            return;
        }
        this.mForceHiddenFreeformTasks.remove(task);
        if (this.mIsForceHideWithoutAnimation) {
            requestForceHideTransition(4, task);
        } else {
            requestForceHideTransition(2, task);
        }
        task.dispatchTaskInfoChangedIfNeeded(true);
        if (task.isMinimized()) {
            this.mAtm.mWindowManager.mTaskSnapshotController.addSkipClosingAppSnapshotTasks(
                    Sets.newArraySet(new Task[] {task}));
        }
        Slog.d("FreeformController", "releaseForceHidePolicyIfNeededLocked: " + task);
    }

    public final void releaseForceHideTaskLocked(Task task) {
        if (this.mForceHiddenFreeformTasks.contains(task)) {
            Slog.d("FreeformController", "releaseForceHideTaskLocked: " + task + "");
            task.forAllWindows(
                    (Consumer) new FreeformController$$ExternalSyntheticLambda0(this, 0), true);
            this.mAtm.mWindowManager.requestTraversal();
        }
    }

    public final void requestForceHideTransition(int i, Task task) {
        Transition.ChangeInfo changeInfo;
        TransitionController transitionController = this.mTransitionController;
        boolean z = false;
        Transition createTransition =
                !transitionController.isCollecting()
                        ? transitionController.createTransition(6, 0)
                        : null;
        Transition transition = transitionController.mCollectingTransition;
        Transition.ChangeInfo changeInfo2 =
                transitionController.isCollecting()
                        ? (Transition.ChangeInfo)
                                transitionController.mCollectingTransition.mChanges.get(task)
                        : null;
        if (changeInfo2 != null) {
            Rect bounds = task.getBounds();
            Rect rect = changeInfo2.mAbsoluteBounds;
            if (task.mSyncGroup == null
                    && ((i == 2 || i == 4)
                            && changeInfo2.mVisible
                            && task.isVisibleRequested()
                            && (bounds.width() != rect.width()
                                    || bounds.height() != rect.height()))) {
                z = true;
            }
        }
        Slog.d(
                "FreeformController",
                "requestForceHideTransition: tid #"
                        + task.mTaskId
                        + ", type="
                        + MultiWindowManager.forceHidingTransitToString(i)
                        + ", forceSync="
                        + z
                        + ", newTransit="
                        + createTransition);
        if (transition != null) {
            transition.collect(task, z);
            Transition transition2 = transitionController.mCollectingTransition;
            if (transition2 != null
                    && (changeInfo = (Transition.ChangeInfo) transition2.mChanges.get(task))
                            != null) {
                changeInfo.mForceHidingTransit = i;
            }
        }
        if (createTransition != null) {
            transitionController.requestStartTransition(createTransition, task, null, null);
            createTransition.setReady(task, true);
        }
    }

    public final void scheduleUnbindMinimizeContainerService(String str) {
        Message obtainMessage = this.mH.obtainMessage(102);
        obtainMessage.obj = str;
        this.mH.sendMessage(obtainMessage);
    }

    public final void setBlockToAddForceHideFreeformTasks(boolean z) {
        if (this.mBlockToAddForceHideFreeformTasks != z) {
            this.mBlockToAddForceHideFreeformTasks = z;
            Slog.d(
                    "FreeformController",
                    "setBlockToAddForceHideFreeformTasks: blockToAddForceHide="
                            + this.mBlockToAddForceHideFreeformTasks
                            + ", Caller="
                            + Debug.getCaller());
        }
    }

    public final void setFreeformWindowingModeByCornerGestureLocked(int i, Rect rect, int i2) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.deferWindowLayout();
        try {
            Task anyTaskForId =
                    activityTaskManagerService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
            if (anyTaskForId == null) {
                Slog.w(
                        "FreeformController",
                        "setFreeformWindowingModeByCornerGestureLocked: taskId="
                                + i
                                + " not found");
                return;
            }
            if (activityTaskManagerService.mLockTaskController.isTaskLocked(anyTaskForId)) {
                activityTaskManagerService.mLockTaskController.showLockTaskToast();
                Slog.w(
                        "FreeformController",
                        "setFreeformWindowingModeByCornerGestureLocked: task is locked");
                return;
            }
            DisplayContent displayContent = anyTaskForId.getDisplayContent();
            if (displayContent == null) {
                Slog.w(
                        "FreeformController",
                        "setFreeformWindowingModeByCornerGestureLocked: cannot find display");
                return;
            }
            if (anyTaskForId.inSplitScreenWindowingMode()) {
                if (anyTaskForId.getRootActivity(true, false) == null) {
                    Slog.w(
                            "FreeformController",
                            "setFreeformWindowingModeByCornerGestureLocked: root activity not"
                                + " found");
                    return;
                }
                DisplayContent displayContent2 = anyTaskForId.getDisplayContent();
                if (displayContent2 != null && displayContent2.mDisplayId == 0) {
                    anyTaskForId.reparent(
                            displayContent2.getDefaultTaskDisplayArea(), Integer.MAX_VALUE);
                    anyTaskForId.setWindowingMode(5);
                    anyTaskForId.setBounds(rect);
                }
                return;
            }
            if (anyTaskForId.getWindowingMode() != 5) {
                anyTaskForId.mLastNonFullscreenBounds = new Rect(rect);
                anyTaskForId.setWindowingMode(5);
                ActivityRecord topNonFinishingActivity =
                        anyTaskForId.getTopNonFinishingActivity(true, true);
                if (topNonFinishingActivity != null
                        && !topNonFinishingActivity.noDisplay
                        && topNonFinishingActivity.canForceResizeNonResizable(
                                anyTaskForId.getWindowingMode())) {
                    activityTaskManagerService.mTaskChangeNotificationController
                            .notifyActivityForcedResizable(
                                    anyTaskForId.mTaskId,
                                    3,
                                    topNonFinishingActivity.info.applicationInfo.packageName);
                }
                if (rect == null || rect.isEmpty()) {
                    rect = anyTaskForId.getLaunchBounds();
                }
            }
            int rotation = anyTaskForId.getWindowConfiguration().getRotation();
            if (i2 != rotation) {
                if (rect != null) {
                    displayContent.rotateBounds(i2, rect, rotation);
                } else {
                    Slog.w(
                            "FreeformController",
                            "setFreeformWindowingModeByCornerGestureLocked: bounds is null");
                }
            }
            anyTaskForId.resize(2, rect);
            activityTaskManagerService.mRootWindowContainer.ensureActivitiesVisible(true, null);
            activityTaskManagerService.mRootWindowContainer.resumeFocusedTasksTopActivities();
        } finally {
            activityTaskManagerService.continueWindowLayout();
        }
    }

    public final void setMaxVisibleFreeformCountForDex(int i, int i2) {
        if (i <= 0) {
            i = -1;
        }
        this.mMaxFreeformOverWrittenCnt = i;
        if (i2 <= 0) {
            i2 = -1;
        }
        this.mMaxDexFreeformOverWrittenCnt = i2;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        int childCount = activityTaskManagerService.mRootWindowContainer.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            DisplayContent displayContent =
                    (DisplayContent) activityTaskManagerService.mRootWindowContainer.getChildAt(i3);
            if (displayContent == null) {
                Slog.w("FreeformController", "minimizeAllFreeformLocked: activityDisplay is null.");
                return;
            }
            for (int childCount2 = displayContent.getChildCount() - 1;
                    childCount2 >= 0;
                    childCount2--) {
                TaskDisplayArea asTaskDisplayArea =
                        ((DisplayArea) displayContent.getChildAt(childCount2)).asTaskDisplayArea();
                if (asTaskDisplayArea != null) {
                    minimizeExcessiveVisibleFreeformLocked(asTaskDisplayArea);
                }
            }
        }
    }

    public final void updateFreeformBoundsForDisplayDeviceTypeChanged(Task task) {
        Rect rect;
        Rect rect2;
        int i;
        int i2;
        if (task == null || task.getRootTask() == null) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        Configuration globalConfiguration = activityTaskManagerService.getGlobalConfiguration();
        Task rootTask = task.getRootTask();
        int i3 = rootTask.mLastMinimizedDisplayType;
        int i4 = rootTask.mLastMinimizedRotation;
        if (i3 == -1 || i3 == globalConfiguration.semDisplayDeviceType) {
            return;
        }
        DisplayContent displayContent =
                activityTaskManagerService.mRootWindowContainer.mDefaultDisplay;
        int rotation = globalConfiguration.windowConfiguration.getRotation();
        Rect bounds = displayContent.getBounds();
        boolean z = bounds.width() <= bounds.height();
        boolean z2 = globalConfiguration.semDisplayDeviceType == 0;
        Rect bounds2 = task.getBounds();
        Rect rect3 = new Rect();
        if (z2) {
            MultiWindowFoldController multiWindowFoldController =
                    activityTaskManagerService.mMultiWindowFoldController;
            if (multiWindowFoldController.mCoverDisplayBounds[0].isEmpty()) {
                multiWindowFoldController.initDisplayBounds(true);
            }
            Rect[] rectArr = multiWindowFoldController.mCoverDisplayBounds;
            rect = z ? rectArr[0] : rectArr[1];
        } else {
            MultiWindowFoldController multiWindowFoldController2 =
                    activityTaskManagerService.mMultiWindowFoldController;
            if (multiWindowFoldController2.mMainDisplayBounds[0].isEmpty()) {
                multiWindowFoldController2.initDisplayBounds(false);
            }
            Rect[] rectArr2 = multiWindowFoldController2.mMainDisplayBounds;
            rect = z ? rectArr2[0] : rectArr2[1];
        }
        if (z2) {
            MultiWindowFoldController multiWindowFoldController3 =
                    activityTaskManagerService.mMultiWindowFoldController;
            if (multiWindowFoldController3.mMainDisplayBounds[0].isEmpty()) {
                multiWindowFoldController3.initDisplayBounds(false);
            }
            Rect[] rectArr3 = multiWindowFoldController3.mMainDisplayBounds;
            rect2 = z ? rectArr3[0] : rectArr3[1];
        } else {
            MultiWindowFoldController multiWindowFoldController4 =
                    activityTaskManagerService.mMultiWindowFoldController;
            if (multiWindowFoldController4.mCoverDisplayBounds[0].isEmpty()) {
                multiWindowFoldController4.initDisplayBounds(true);
            }
            Rect[] rectArr4 = multiWindowFoldController4.mCoverDisplayBounds;
            rect2 = z ? rectArr4[0] : rectArr4[1];
        }
        if (i4 != -1 && rotation != -1 && i4 != rotation) {
            displayContent.mDisplayContent.rotateBounds(i4, bounds2, rotation);
        }
        task.updateMinMaxSizeIfNeeded();
        if (!bounds2.isEmpty()) {
            rect3.setEmpty();
            boolean z3 = rect2.width() < bounds2.width();
            boolean z4 = rect2.height() < bounds2.height();
            if (z3 || z4) {
                Rect rect4 = this.mTmpRect;
                activityTaskManagerService.mWindowManager.getStableInsetsLocked(0, rect4);
                if (z3) {
                    rect3.left = rect2.left + rect4.left + 8;
                    rect3.right = (rect2.right - rect4.right) - 8;
                } else {
                    int width =
                            (int)
                                    ((rect2.width() - bounds2.width())
                                            * (bounds2.left / (rect.width() - bounds2.width())));
                    rect3.left = width;
                    rect3.right = bounds2.width() + width;
                }
                if (z4) {
                    rect3.top = rect2.top + rect4.top + 8;
                    rect3.bottom = (rect2.bottom - rect4.bottom) - 8;
                } else {
                    int height =
                            (int)
                                    ((rect2.height() - bounds2.height())
                                            * (bounds2.top / (rect.height() - bounds2.height())));
                    rect3.top = height;
                    rect3.bottom = bounds2.height() + height;
                }
                int width2 = rect3.width();
                int height2 = rect3.height();
                task.adjustForMinimalTaskDimensions(
                        rect3, new Rect(), task.getParent().getConfiguration());
                rect3.offset(
                        width2 != rect3.width() ? 0 - ((rect3.width() - width2) / 2) : 0,
                        height2 != rect3.height() ? 0 - ((rect3.height() - height2) / 2) : 0);
            } else {
                rect3.set(0, 0, bounds2.width(), bounds2.height());
                rect3.offset(
                        (int)
                                ((rect2.width() - bounds2.width())
                                        * (bounds2.left / (rect.width() - bounds2.width()))),
                        (int)
                                ((rect2.height() - bounds2.height())
                                        * (bounds2.top / (rect.height() - bounds2.height()))));
            }
            int i5 = rect3.left;
            if (8 > i5) {
                i = 8 - i5;
            } else {
                int i6 = rect2.right - 8;
                int i7 = rect3.right;
                i = i6 < i7 ? i6 - i7 : 0;
            }
            int i8 = rect3.top;
            if (8 > i8) {
                i2 = 8 - i8;
            } else {
                int i9 = rect2.bottom - 8;
                int i10 = rect3.bottom;
                i2 = i9 < i10 ? i9 - i10 : 0;
            }
            rect3.offset(i, i2);
            if (task.isFreeformStashed()) {
                Rect stashedBounds = task.getStashedBounds();
                rect3.offsetTo(
                        task.isLeftStash()
                                ? stashedBounds.right - rect3.width()
                                : stashedBounds.left,
                        rect3.top);
            }
        }
        if (rect3.isEmpty()) {
            return;
        }
        task.resize(0, rect3);
    }

    public final void updateFreeformCaptionType(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFreeformCaptionType != i) {
                    this.mFreeformCaptionType = i;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
