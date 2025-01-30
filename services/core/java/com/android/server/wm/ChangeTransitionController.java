package com.android.server.wm;

import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.server.DisplayThread;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.Transition;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class ChangeTransitionController implements IController {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public static final boolean DISABLE_LEGACY_RESIZE_TRANSITION = SystemProperties.getBoolean("persist.mt.debug.disable_legacy_resize_transition", false);
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public HandlerC2887H f1735mH;
    public TransitionController mTransitionController;
    public WindowManagerService mWm;
    public final ArraySet mSyncDeferredAllDrawnApps = new ArraySet();
    public final HashMap mTransitionToChangingPipTask = new HashMap();

    public final void handleRequestSplitScreenToFullscreen() {
    }

    public ChangeTransitionController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1735mH = new HandlerC2887H(DisplayThread.get().getLooper());
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
        this.mTransitionController = this.mAtm.getTransitionController();
    }

    public void handleWindowContainerTransaction(WindowContainer windowContainer, WindowContainerTransaction.Change change) {
        Task asTask = windowContainer.asTask();
        int changeTransitMode = change.getChangeTransitMode();
        int changeTransitFlags = change.getChangeTransitFlags();
        Rect changeTransitStartBounds = change.getChangeTransitStartBounds();
        String changeTransitReason = change.getChangeTransitReason();
        if (asTask == null) {
            Slog.w("ChangeTransitionController", "handleWindowContainerTransaction: failed, we support Task only now!, wc=" + windowContainer + ", reason=" + changeTransitReason);
            return;
        }
        if (asTask.inPinnedWindowingMode() && "pip_to_split".equals(changeTransitReason)) {
            asTask.mIsChangingPipToSplit = true;
        }
        Task task = asTask.isLeafTask() ? asTask : asTask.getTask(new Predicate() { // from class: com.android.server.wm.ChangeTransitionController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$handleWindowContainerTransaction$0;
                lambda$handleWindowContainerTransaction$0 = ChangeTransitionController.lambda$handleWindowContainerTransaction$0((Task) obj);
                return lambda$handleWindowContainerTransaction$0;
            }
        });
        if (task == null || task.isChangeTransitionBlockedByCommonPolicy()) {
            StringBuilder sb = new StringBuilder();
            sb.append("handleWindowContainerTransaction: failed, tid #");
            sb.append(task != null ? Integer.valueOf(task.mTaskId) : "null");
            sb.append(", reason=");
            sb.append(changeTransitReason);
            Slog.w("ChangeTransitionController", sb.toString());
            return;
        }
        Slog.d("ChangeTransitionController", "handleWindowContainerTransaction: requested #" + asTask.mTaskId + ", target=" + task.mTaskId + ", startBounds=" + changeTransitStartBounds + ", reason=" + changeTransitReason);
        if (changeTransitStartBounds == null) {
            changeTransitStartBounds = task.getBounds();
        }
        requestChangeTransition(task, changeTransitMode, task.getWindowingMode(), new Rect(changeTransitStartBounds), changeTransitReason, changeTransitFlags);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && task.inPinnedWindowingMode()) {
            addToChangingPipTaskIfPossible(task);
        }
    }

    public static /* synthetic */ boolean lambda$handleWindowContainerTransaction$0(Task task) {
        return task.isVisible() && task.isLeafTask();
    }

    public void handleWindowingModeChanged(Task task, int i, int i2, Rect rect) {
        String str;
        if (i == i2 || task.isChangeTransitionBlockedByCommonPolicy() || i == 2 || i2 == 2) {
            return;
        }
        int i3 = 1;
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && isDisplayChangeTransitionNeeded(task, i, i2)) {
            requestDisplayChangeTransition(task.getDisplayId(), "maximize_force_hidden_freeform", true);
            return;
        }
        if (i == 5 || i2 == 5) {
            addHidingContainerLeashIfNeeded(task, i, i2);
            str = "freeform-mode-changed";
        } else {
            i3 = 0;
            str = null;
        }
        String str2 = str;
        int i4 = i3;
        if (i4 != 0) {
            requestChangeTransition(task, i4, i, rect, str2);
        }
    }

    public void handleTaskSizeChanged(Task task, Rect rect) {
        if (DISABLE_LEGACY_RESIZE_TRANSITION || task.isChangeTransitionBlockedByCommonPolicy() || !CoreRune.MW_FREEFORM_SHELL_TRANSITION || !task.inFreeformWindowingMode()) {
            return;
        }
        Slog.d("ChangeTransitionController", "handleTaskSizeChanged: #" + task.mTaskId);
        requestChangeTransition(task, 1, task.getWindowingMode(), rect, "freeform_resize");
    }

    public void handleChangeTransitionRequest(int i) {
        Slog.d("ChangeTransitionController", "handleChangeTransitionRequest: " + WindowContainerTransaction.changeTransitRequestToString(i));
        if (i == 1) {
            handleRequestFullscreenToSplitScreen();
        } else if (i == 2) {
            handleRequestSplitScreenToFullscreen();
        }
    }

    public void handlePositionTaskBehindHome(Task task) {
        if (task.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Slog.d("ChangeTransitionController", "handlePositionTaskBehindHome: #" + task.mTaskId);
        requestChangeTransition(task, 6, task.getWindowingMode(), new Rect(task.getBounds()), "position_behind_home");
    }

    public final void handleRequestFullscreenToSplitScreen() {
        TaskDisplayArea defaultTaskDisplayArea = this.mWm.mRoot.getDefaultTaskDisplayArea();
        Task rootTask = defaultTaskDisplayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.ChangeTransitionController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$handleRequestFullscreenToSplitScreen$1;
                lambda$handleRequestFullscreenToSplitScreen$1 = ChangeTransitionController.lambda$handleRequestFullscreenToSplitScreen$1((Task) obj);
                return lambda$handleRequestFullscreenToSplitScreen$1;
            }
        });
        if (rootTask == null || !rootTask.supportsMultiWindowInDisplayArea(defaultTaskDisplayArea) || rootTask.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Slog.d("ChangeTransitionController", "handleRequestFullscreenToSplitScreen: #" + rootTask.mTaskId);
        ActivityRecord topMostActivity = rootTask.getTopMostActivity();
        if ((topMostActivity == null || topMostActivity.finishing) && rootTask.getTopVisibleActivity() == null) {
            requestDisplayChangeTransition(defaultTaskDisplayArea.getDisplayId(), "request(full_to_split)");
        } else {
            requestChangeTransition(rootTask, this.mAtm.mNaturalSwitchingController.isRunning() ? 4 : 1, rootTask.getWindowingMode(), new Rect(rootTask.getBounds()), "request(full_to_split)");
        }
    }

    public static /* synthetic */ boolean lambda$handleRequestFullscreenToSplitScreen$1(Task task) {
        return task.inFullscreenWindowingMode() && task.isActivityTypeStandard() && !task.isFullscreenRootForStageTask() && task.isVisible();
    }

    public void handlePopOverChangeTransitionRequest(ActivityRecord activityRecord, Rect rect) {
        DisplayContent displayContent = activityRecord.mDisplayContent;
        Task task = activityRecord.getTask();
        if (displayContent == null || task == null || !activityRecord.mPopOverState.isActivated() || task.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(activityRecord);
        if (findCollectingChangeInfo == null || findCollectingChangeInfo.mChangeLeash == null) {
            Transition createTransition = !this.mTransitionController.isCollecting() ? this.mTransitionController.createTransition(6) : null;
            this.mTransitionController.collect(activityRecord);
            updateChangeInfo(activityRecord, 5, 1, rect, "popover", 0);
            this.mTransitionController.collectVisibleChange(activityRecord);
            if (createTransition != null) {
                this.mTransitionController.requestStartTransition(createTransition, task, null, null);
                createTransition.setReady(task, true);
            }
        }
    }

    public void handleActivityBoundsChanged(ActivityRecord activityRecord, Rect rect) {
        Task task = activityRecord.getTask();
        if (task == null || task.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(activityRecord);
        if (findCollectingChangeInfo == null || findCollectingChangeInfo.mChangeLeash == null) {
            Transition.ChangeInfo findCollectingChangeInfo2 = findCollectingChangeInfo(task);
            if (findCollectingChangeInfo2 == null || !findCollectingChangeInfo2.hasChanged()) {
                Transition createTransition = !this.mTransitionController.isCollecting() ? this.mTransitionController.createTransition(6) : null;
                this.mTransitionController.collect(activityRecord);
                updateChangeInfo(activityRecord, 5, 0, rect, "activity_resize_by_bounds_compat", 0);
                this.mTransitionController.collectVisibleChange(activityRecord);
                if (createTransition != null) {
                    this.mTransitionController.requestStartTransition(createTransition, task, null, null);
                    createTransition.setReady(task, true);
                }
            }
        }
    }

    public final void addHidingContainerLeashIfNeeded(Task task, int i, int i2) {
        Transition collectingTransition;
        Transition.ChangeInfo changeInfo;
        ActivityRecord activity;
        Transition.ChangeInfo changeInfo2;
        if (!CoreRune.FW_CUSTOM_LETTERBOX || i != 1 || i2 != 5 || (collectingTransition = this.mTransitionController.getCollectingTransition()) == null || (changeInfo = (Transition.ChangeInfo) collectingTransition.mChanges.get(task)) == null || (activity = task.getActivity(new ChangeTransitionController$$ExternalSyntheticLambda1())) == null) {
            return;
        }
        Rect letterboxInsets = activity.mLetterboxUiController.getLetterboxInsets();
        if ((letterboxInsets.left == 0 && letterboxInsets.top == 0 && letterboxInsets.right == 0 && letterboxInsets.bottom == 0) || (changeInfo2 = (Transition.ChangeInfo) collectingTransition.mChanges.get(activity)) == null) {
            return;
        }
        if (changeInfo2.mAbsoluteBounds.width() == changeInfo.mAbsoluteBounds.width() && changeInfo2.mAbsoluteBounds.height() == changeInfo.mAbsoluteBounds.height()) {
            return;
        }
        changeInfo.mHidingContainerLeash = true;
    }

    public final void requestChangeTransition(Task task, int i, int i2, Rect rect, String str) {
        requestChangeTransition(task, i, i2, rect, str, 0);
    }

    public final void requestChangeTransition(Task task, int i, int i2, Rect rect, String str, int i3) {
        String str2;
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(task);
        if (findCollectingChangeInfo != null && findCollectingChangeInfo.mChangeLeash != null) {
            Slog.d("ChangeTransitionController", "requestStartChangeTransition: skip, already collecting, #" + task.mTaskId + ", reason=" + str);
            return;
        }
        Transition createTransition = !this.mTransitionController.isCollecting() ? this.mTransitionController.createTransition(6) : null;
        StringBuilder sb = new StringBuilder();
        sb.append("requestStartChangeTransition: tid #");
        sb.append(task.mTaskId);
        sb.append(", mode=");
        sb.append(MultiWindowManager.changeTransitModeToString(i));
        if (i3 != 0) {
            str2 = ", flags=0x" + Integer.toHexString(i3);
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(", newTransit=");
        sb.append(createTransition);
        sb.append(", reason=");
        sb.append(str);
        Slog.d("ChangeTransitionController", sb.toString());
        this.mTransitionController.collect(task);
        updateChangeInfo(task, i, i2, rect, str, i3);
        this.mTransitionController.collectVisibleChange(task);
        if (createTransition != null) {
            this.mTransitionController.requestStartTransition(createTransition, task, null, null);
            createTransition.setReady(task, true);
        }
    }

    public final void updateChangeInfo(WindowContainer windowContainer, int i, int i2, Rect rect, String str, int i3) {
        String str2;
        if (!this.mTransitionController.isCollecting()) {
            Slog.w("ChangeTransitionController", "updateChangeInfo: failed, collecting false, wc=" + windowContainer);
            return;
        }
        boolean z = windowContainer.asTask() != null && i2 == 5;
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(windowContainer);
        if (findCollectingChangeInfo == null) {
            Slog.w("ChangeTransitionController", "updateChangeInfo: failed, changeInfo is null, wc=" + windowContainer);
            return;
        }
        findCollectingChangeInfo.mChangeTransitMode = i;
        findCollectingChangeInfo.mChangeTransitFlags = i3;
        findCollectingChangeInfo.mAbsoluteBounds.set(rect);
        if (i2 != 0) {
            findCollectingChangeInfo.mWindowingMode = i2;
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z) {
            int stringToFreeformCaptionType = (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && i == 3) ? MultiWindowManager.stringToFreeformCaptionType(str) : -1;
            int freeformThickness = windowContainer.getFreeformThickness();
            findCollectingChangeInfo.mFreezeOutsets.set(freeformThickness, windowContainer.getCaptionHeight(true, stringToFreeformCaptionType) + freeformThickness, freeformThickness, freeformThickness);
            if (windowContainer.inSplitScreenWindowingMode() && windowContainer.getTaskDisplayArea() != null) {
                findCollectingChangeInfo.mCommonAncestor = windowContainer.getTaskDisplayArea();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("updateChangeInfo: wc=");
        sb.append(windowContainer);
        sb.append(", ");
        sb.append(MultiWindowManager.changeTransitModeToString(i));
        if (z) {
            str2 = ", outsets=" + findCollectingChangeInfo.mFreezeOutsets;
        } else {
            str2 = "";
        }
        sb.append(str2);
        Slog.d("ChangeTransitionController", sb.toString());
    }

    public boolean isInChangeTransition(WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition collectingTransition = this.mTransitionController.getCollectingTransition();
        return (collectingTransition == null || (changeInfo = (Transition.ChangeInfo) collectingTransition.mChanges.get(windowContainer)) == null || changeInfo.mChangeLeash == null) ? false : true;
    }

    public void adjustFreezeBoundsIfNeeded(WindowContainer windowContainer, Rect rect) {
        Transition.ChangeInfo changeInfo;
        Transition collectingTransition = this.mTransitionController.getCollectingTransition();
        if (collectingTransition == null || (changeInfo = (Transition.ChangeInfo) collectingTransition.mChanges.get(windowContainer)) == null || !hasFreezeOutsets(changeInfo)) {
            return;
        }
        int i = rect.left;
        Rect rect2 = changeInfo.mFreezeOutsets;
        rect.left = i - rect2.left;
        rect.top -= rect2.top;
        rect.right += rect2.right;
        rect.bottom += rect2.bottom;
        Slog.d("ChangeTransitionController", "adjustFreezeBoundsIfNeeded: wc=" + windowContainer + ", freezeBounds=" + rect + ", outset=" + changeInfo.mFreezeOutsets);
    }

    public static boolean hasFreezeOutsets(Transition.ChangeInfo changeInfo) {
        Rect rect = changeInfo.mFreezeOutsets;
        return rect.left > 0 || rect.top > 0 || rect.right > 0 || rect.bottom > 0;
    }

    public void createChangeLeashIfNeeded(WindowContainer windowContainer, SurfaceControl.Transaction transaction, Rect rect) {
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(windowContainer);
        WindowContainer parent = windowContainer.getParent();
        TaskDisplayArea taskDisplayArea = windowContainer.getTaskDisplayArea();
        if (parent == null || taskDisplayArea == null || findCollectingChangeInfo == null || findCollectingChangeInfo.mChangeTransitMode == 0) {
            return;
        }
        SurfaceControl anchorLayer = getAnchorLayer(findCollectingChangeInfo, taskDisplayArea);
        SurfaceControl build = windowContainer.makeAnimationLeash().setName(getChangeLeashName(windowContainer)).setParent(anchorLayer).build();
        boolean z = anchorLayer == findCollectingChangeInfo.mContainer.getParentSurfaceControl();
        int i = z ? 0 : rect.left;
        int i2 = z ? 0 : rect.top;
        float cornerRadiusForChangeLeash = getCornerRadiusForChangeLeash(windowContainer);
        transaction.setLayer(build, windowContainer.getLastLayer());
        transaction.setPosition(build, i, i2);
        transaction.setCrop(build, new Rect(0, 0, rect.width(), rect.height()));
        if (cornerRadiusForChangeLeash > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            transaction.setCornerRadius(build, cornerRadiusForChangeLeash);
        }
        transaction.show(build);
        boolean canHideContainerLeash = canHideContainerLeash(findCollectingChangeInfo);
        SurfaceControl surfaceControl = windowContainer.getSurfaceControl();
        transaction.reparent(surfaceControl, build);
        transaction.setLayer(surfaceControl, 0);
        transaction.setPosition(surfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        transaction.setShadowRadius(surfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        if (canHideContainerLeash) {
            transaction.setAlpha(surfaceControl, 0.001f);
        }
        SurfaceControl surfaceControl2 = findCollectingChangeInfo.mSnapshot;
        transaction.reparent(surfaceControl2, build);
        transaction.setLayer(surfaceControl2, Integer.MAX_VALUE);
        float f = findCollectingChangeInfo.mCornerRadius;
        if (f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            transaction.setCornerRadius(build, f);
        }
        findCollectingChangeInfo.mChangeLeash = build;
        StringBuilder sb = new StringBuilder();
        sb.append("createChangeTransitionLeashIfNeeded: ");
        sb.append(build);
        sb.append(", wc=");
        sb.append(windowContainer);
        sb.append(", Z=");
        sb.append(windowContainer.getLastLayer());
        sb.append(", freezeBounds=");
        sb.append(rect);
        sb.append(", anchorLayer=");
        sb.append(anchorLayer);
        sb.append(canHideContainerLeash ? ", c=hidden" : "");
        Slog.d("ChangeTransitionController", sb.toString());
    }

    public static float getCornerRadiusForChangeLeash(WindowContainer windowContainer) {
        int dipToPixel;
        if (windowContainer.asActivityRecord() != null && windowContainer.asActivityRecord().mPopOverState.isActivated() && windowContainer.getDisplayContent() != null) {
            dipToPixel = WindowManagerService.dipToPixel(26, windowContainer.mDisplayContent.getDisplayMetrics());
        } else {
            if (!windowContainer.inPinnedWindowingMode() || windowContainer.getDisplayContent() == null) {
                return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            }
            dipToPixel = WindowManagerService.dipToPixel(12, windowContainer.mDisplayContent.getDisplayMetrics());
        }
        return dipToPixel;
    }

    public static boolean canHideContainerLeash(Transition.ChangeInfo changeInfo) {
        if (changeInfo.mSnapshot == null || changeInfo.mContainer.asTask() == null) {
            return false;
        }
        return changeInfo.mWindowingMode == 5 || changeInfo.mHidingContainerLeash;
    }

    public void updateChangeOutsetsIfNeeded(TransitionInfo.Change change, Transition.ChangeInfo changeInfo, WindowContainer windowContainer) {
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            return;
        }
        if (hasFreezeOutsets(changeInfo)) {
            change.setChangeStartOutsets(changeInfo.mFreezeOutsets);
        }
        if (asTask.inFreeformWindowingMode()) {
            int captionHeight = windowContainer.getCaptionHeight();
            int freeformThickness = windowContainer.getFreeformThickness();
            change.setChangeEndOutsets(new Rect(freeformThickness, captionHeight + freeformThickness, freeformThickness, freeformThickness));
        }
        if (change.hasChangeStartOutsets() || change.hasChangeEndOutsets()) {
            Slog.d("ChangeTransitionController", "updateChangeOutsetsIfNeeded: s=" + change.getChangeStartOutsets() + ", e=" + change.getChangeEndOutsets() + ", wc=" + windowContainer);
        }
    }

    public static SurfaceControl getAnchorLayer(Transition.ChangeInfo changeInfo, TaskDisplayArea taskDisplayArea) {
        NaturalSwitchingController naturalSwitchingController;
        DisplayArea dragTargetArea;
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && (changeInfo.mWindowingMode == 5 || (changeInfo.mChangeTransitFlags & 1) != 0)) {
            return taskDisplayArea.mFloatingLeashAnchor;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && changeInfo.mContainer.getParentSurfaceControl() != null && changeInfo.mChangeTransitMode == 6) {
            return changeInfo.mContainer.getParentSurfaceControl();
        }
        Task asTask = changeInfo.mContainer.asTask();
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && asTask != null && (dragTargetArea = (naturalSwitchingController = asTask.mAtmService.mNaturalSwitchingController).getDragTargetArea()) != null && dragTargetArea.getSurfaceControl() != null && naturalSwitchingController.isNaturalSwitchingPipTask(asTask)) {
            return dragTargetArea.getSurfaceControl();
        }
        return taskDisplayArea.mChangeLeashAnchor;
    }

    public static String getChangeLeashName(WindowContainer windowContainer) {
        return "Change Leash: " + windowContainer.getName();
    }

    public static boolean isTransparentSnapshotTarget(Transition.ChangeInfo changeInfo) {
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && changeInfo.mChangeTransitMode != 0 && changeInfo.mWindowingMode == 5) {
            return true;
        }
        WindowContainer windowContainer = changeInfo.mContainer;
        return isWallpaperTargetTask(windowContainer) && windowContainer.inSplitScreenWindowingMode();
    }

    public static boolean isWallpaperTargetTask(WindowContainer windowContainer) {
        DisplayContent displayContent = windowContainer.getDisplayContent();
        Task asTask = windowContainer.asTask();
        return (asTask == null || displayContent == null || !displayContent.mWallpaperController.isWallpaperVisible() || displayContent.mWallpaperController.getWallpaperTarget() == null || displayContent.mWallpaperController.getWallpaperTarget().getTask() != asTask) ? false : true;
    }

    public void collectExcludeLayersFromSnapshot(WindowContainer windowContainer, ArrayList arrayList) {
        WindowState window;
        DisplayContent displayContent = windowContainer.getDisplayContent();
        Task asTask = windowContainer.asTask();
        if (asTask == null || displayContent == null || (window = asTask.getWindow(new Predicate() { // from class: com.android.server.wm.ChangeTransitionController$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$collectExcludeLayersFromSnapshot$2;
                lambda$collectExcludeLayersFromSnapshot$2 = ChangeTransitionController.lambda$collectExcludeLayersFromSnapshot$2((WindowState) obj);
                return lambda$collectExcludeLayersFromSnapshot$2;
            }
        })) == null || arrayList.contains(window.mSurfaceControl)) {
            return;
        }
        arrayList.add(window.mSurfaceControl);
        Slog.d("ChangeTransitionController", "collectExcludeLayersFromSnapshot: #" + asTask.mTaskId + ", exclude=" + window);
    }

    public static /* synthetic */ boolean lambda$collectExcludeLayersFromSnapshot$2(WindowState windowState) {
        return windowState.isChildWindow() && windowState.isOnScreen() && windowState.mAnimatingExit;
    }

    public void onSnapshotSurfaceCreated(WindowContainer windowContainer, Rect rect, Transition.ChangeInfo changeInfo) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("onSnapshotSurfaceCreated: wc=");
        sb.append(windowContainer);
        sb.append(", cropBounds=");
        sb.append(rect);
        sb.append(", changeInfo=");
        sb.append(changeInfo);
        if (DEBUG) {
            str = ", Callers=" + Debug.getCallers(5);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.d("ChangeTransitionController", sb.toString());
    }

    public void onTransactionReady(Transition transition) {
        Slog.d("ChangeTransitionController", "onTransactionReady: " + transition);
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            applyForceChangeIfNeeded(transition);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            collectFreeformTasksIfNeeded(transition);
        }
    }

    public void onTransitionAborted(Transition transition) {
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            if (this.mTransitionToChangingPipTask.containsKey(transition)) {
                Slog.d("ChangeTransitionController", "onTransitionAborted: " + transition + ", changingPipTasks=" + this.mTransitionToChangingPipTask);
                ((Task) this.mTransitionToChangingPipTask.get(transition)).mIsChangingPipToSplit = false;
            }
            this.mTransitionToChangingPipTask.clear();
        }
    }

    public void onTransitionFinished(Transition transition) {
        Slog.d("ChangeTransitionController", "onTransitionFinished: " + transition);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            restoreLastParentBeforePip(transition);
        }
    }

    public void onActivityLocalRelaunched(ActivityRecord activityRecord) {
        Task task = activityRecord.getTask();
        if (task == null || activityRecord.allDrawn || !isInChangeTransition(task)) {
            Slog.w("ChangeTransitionController", "onActivityLocalRelaunched: failed, r=" + activityRecord);
            return;
        }
        addToSyncDeferredForAllDrawn(activityRecord, "local_relaunch");
    }

    public void onActivityAllDrawn(ActivityRecord activityRecord) {
        removeFromSyncDeferredForAllDrawn(activityRecord, "all_drawn");
    }

    public void onActivityFinished(ActivityRecord activityRecord) {
        removeFromSyncDeferredForAllDrawn(activityRecord, "finished");
    }

    public void addToSyncDeferredForAllDrawn(ActivityRecord activityRecord, String str) {
        if (this.mSyncDeferredAllDrawnApps.contains(activityRecord)) {
            return;
        }
        this.mSyncDeferredAllDrawnApps.add(activityRecord);
        Slog.d("ChangeTransitionController", "addToSyncDeferredForAllDrawn: r=" + activityRecord + ", reason=" + str + ", num_remain=" + this.mSyncDeferredAllDrawnApps.size());
        if (this.mSyncDeferredAllDrawnApps.size() == 1) {
            this.f1735mH.removeMessages(1);
            this.f1735mH.sendEmptyMessageDelayed(1, 2000L);
        }
    }

    public void removeFromSyncDeferredForAllDrawn(ActivityRecord activityRecord, String str) {
        if (this.mSyncDeferredAllDrawnApps.contains(activityRecord)) {
            this.mSyncDeferredAllDrawnApps.remove(activityRecord);
            Slog.d("ChangeTransitionController", "removeFromSyncDeferredForAllDrawn: r=" + activityRecord + ", reason=" + str + ", num_remain=" + this.mSyncDeferredAllDrawnApps.size());
            if (this.mSyncDeferredAllDrawnApps.isEmpty()) {
                this.f1735mH.removeMessages(1);
            }
        }
    }

    public boolean isSyncDeferredForAllDrawn(ActivityRecord activityRecord) {
        return this.mSyncDeferredAllDrawnApps.contains(activityRecord);
    }

    public void requestDisplayChangeTransition(int i, String str) {
        requestDisplayChangeTransition(i, str, false);
    }

    public void requestDisplayChangeTransition(int i, String str, boolean z) {
        String str2;
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
            if (displayContent == null) {
                Slog.w("ChangeTransitionController", "requestDisplayChangeTransition: cannot find display #" + i);
                return;
            }
            int rotation = displayContent.getRotation();
            boolean z2 = !this.mTransitionController.isCollecting();
            TransitionRequestInfo.DisplayChange displayChange = z2 ? new TransitionRequestInfo.DisplayChange(displayContent.getDisplayId(), rotation, rotation) : null;
            StringBuilder sb = new StringBuilder();
            sb.append("requestDisplayChangeTransition: #");
            sb.append(i);
            sb.append(", reason=");
            sb.append(str);
            sb.append(", createDisplayChange=");
            sb.append(z2);
            if (DEBUG) {
                str2 = ", Callers=" + Debug.getCallers(10);
            } else {
                str2 = "";
            }
            sb.append(str2);
            Slog.d("ChangeTransitionController", sb.toString());
            displayContent.requestChangeTransitionIfNeeded(536870912, displayChange);
            Transition collectingTransition = this.mTransitionController.getCollectingTransition();
            if (collectingTransition == null) {
                Slog.e("ChangeTransitionController", "requestDisplayChangeTransition: failed, collecting transition is null!");
                return;
            }
            collectingTransition.setKnownConfigChanges(displayContent, 536870912);
            collectingTransition.setDisplayChangeTransitionFlag(displayContent, z);
            this.mTransitionController.collectForDisplayAreaChange(displayContent);
        }
    }

    public static void applyForceChangeIfNeeded(Transition transition) {
        ArrayMap arrayMap = transition.mChanges;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) arrayMap.valueAt(size);
            WindowContainer windowContainer = changeInfo.mContainer;
            Transition.ChangeInfo changeInfo2 = (Transition.ChangeInfo) arrayMap.get(windowContainer.getDisplayContent());
            if (changeInfo2 != null && (changeInfo2.mFlags & 65536) != 0 && arrayMap.get(windowContainer.getTaskDisplayArea()) != null) {
                changeInfo.mForceChanged = true;
                Slog.d("ChangeTransitionController", "applyForceChangeIfNeeded: " + changeInfo);
            }
        }
    }

    public static void collectFreeformTasksIfNeeded(final Transition transition) {
        Task taskBelow;
        ArrayList arrayList = new ArrayList();
        ArrayMap arrayMap = transition.mChanges;
        WindowContainer windowContainer = null;
        boolean z = false;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) arrayMap.valueAt(size);
            if (changeInfo.mContainer.asTask() != null) {
                if (changeInfo.mContainer.inFreeformWindowingMode()) {
                    windowContainer = changeInfo.mContainer;
                }
                if (changeInfo.mContainer.inFullscreenWindowingMode()) {
                    z = true;
                }
            }
            if (isFreeformChangeTransition(changeInfo)) {
                TaskDisplayArea taskDisplayArea = changeInfo.mContainer.getTaskDisplayArea();
                if (!arrayList.contains(taskDisplayArea)) {
                    arrayList.add(taskDisplayArea);
                }
            }
        }
        if (z && windowContainer != null && windowContainer.getTaskDisplayArea() != null && (taskBelow = windowContainer.getTaskDisplayArea().getTaskBelow(windowContainer.asTask())) != null && taskBelow.inFreeformWindowingMode()) {
            TaskDisplayArea taskDisplayArea2 = taskBelow.getTaskDisplayArea();
            if (!arrayList.contains(taskDisplayArea2)) {
                arrayList.add(taskDisplayArea2);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((TaskDisplayArea) it.next()).forAllRootTasks(new Consumer() { // from class: com.android.server.wm.ChangeTransitionController$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ChangeTransitionController.lambda$collectFreeformTasksIfNeeded$3(Transition.this, (Task) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$collectFreeformTasksIfNeeded$3(Transition transition, Task task) {
        if (!task.isVisible() || !task.isVisibleRequested() || !task.inFreeformWindowingMode() || transition.mChanges.containsKey(task) || transition.mParticipants.contains(task) || task.isFreeformForceHidden()) {
            return;
        }
        Transition.ChangeInfo changeInfo = new Transition.ChangeInfo(task);
        changeInfo.mForceChanged = true;
        transition.mChanges.put(task, changeInfo);
        transition.mParticipants.add(task);
        Slog.d("ChangeTransitionController", "collectFreeformTasksIfNeeded: " + task);
    }

    public static boolean isFreeformChangeTransition(Transition.ChangeInfo changeInfo) {
        int i = changeInfo.mWindowingMode;
        int windowingMode = changeInfo.mContainer.getWindowingMode();
        if ((i != 5 && windowingMode != 5) || i == windowingMode || changeInfo.mChangeLeash == null || changeInfo.mChangeTransitMode == 0 || changeInfo.mContainer.asTask() == null || changeInfo.mContainer.getTaskDisplayArea() == null) {
            return false;
        }
        Slog.d("ChangeTransitionController", "isFreeformChangeTransition: " + changeInfo.mContainer + " (" + i + "->" + windowingMode + "), " + MultiWindowManager.changeTransitModeToString(changeInfo.mChangeTransitMode));
        return true;
    }

    public static boolean isDisplayChangeTransitionNeeded(Task task, int i, int i2) {
        return task.isFreeformForceHidden() && i != i2 && i == 5;
    }

    public static float getFreeformCornerRadius(DisplayMetrics displayMetrics) {
        return WindowManagerService.dipToPixel(14, displayMetrics);
    }

    public void handleEnteringPipIfNeeded(ActivityRecord activityRecord, Transition transition) {
        if (!activityRecord.mAutoEnteringPip && activityRecord.getState() == ActivityRecord.State.PAUSED && transition != null && transition.mType == 10 && activityRecord.getDisplayContent().hasTopFixedRotationLaunchingApp()) {
            Slog.d("ChangeTransitionController", "handleEnteringPipIfNeeded: r=" + activityRecord);
            activityRecord.setHiddenWhileEnteringPinnedMode(true, "fixed_rotation(paused)");
        }
    }

    public final void addToChangingPipTaskIfPossible(Task task) {
        Transition.ChangeInfo findCollectingChangeInfo = findCollectingChangeInfo(task);
        Transition collectingTransition = this.mTransitionController.getCollectingTransition();
        if (collectingTransition == null || findCollectingChangeInfo == null || findCollectingChangeInfo.mChangeLeash == null) {
            return;
        }
        this.mTransitionToChangingPipTask.put(collectingTransition, task);
        Slog.d("ChangeTransitionController", "addToChangingPipTaskIfPossible: tid #" + task.mTaskId);
    }

    public final void restoreLastParentBeforePip(Transition transition) {
        Task task = (Task) this.mTransitionToChangingPipTask.remove(transition);
        if (task != null && task.isAttached() && task.isVisible()) {
            task.mIsChangingPipToSplit = false;
            TaskDisplayArea taskDisplayArea = task.getTaskDisplayArea();
            ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
            Task lastParentBeforePip = topVisibleActivity != null ? topVisibleActivity.getLastParentBeforePip() : null;
            if (lastParentBeforePip == null || !lastParentBeforePip.isAttached() || lastParentBeforePip == topVisibleActivity.getTask()) {
                return;
            }
            Task topRootTaskInStageType = taskDisplayArea.getTopRootTaskInStageType(task.getStageType());
            if (topRootTaskInStageType == null || !taskDisplayArea.isSplitScreenVisible()) {
                Slog.w("ChangeTransitionController", "restoreLastParentBeforePip: invalid stage, " + topRootTaskInStageType + "r=" + topVisibleActivity);
                return;
            }
            this.mAtm.deferWindowLayout();
            task.mIsPipReparetingToLastParent = true;
            try {
                Slog.d("ChangeTransitionController", "restoreLastParentBeforePip: r=" + topVisibleActivity + ", lastParent=" + lastParentBeforePip);
                lastParentBeforePip.reparent(topRootTaskInStageType, Integer.MAX_VALUE, false, "restoreLastParentBeforePip");
                topVisibleActivity.reparent(lastParentBeforePip, lastParentBeforePip.getChildCount(), "restoreLastParentBeforePip");
                lastParentBeforePip.moveToFront("restoreLastParentBeforePip");
            } finally {
                task.mIsPipReparetingToLastParent = false;
                this.mAtm.continueWindowLayout();
            }
        }
    }

    public final Transition.ChangeInfo findCollectingChangeInfo(WindowContainer windowContainer) {
        if (this.mTransitionController.isCollecting()) {
            return (Transition.ChangeInfo) this.mTransitionController.getCollectingTransition().mChanges.get(windowContainer);
        }
        return null;
    }

    public static void restoreFromChangeLeash(Transition.ChangeInfo changeInfo, BiFunction biFunction, Function function) {
        WindowContainer windowContainer;
        try {
            if (changeInfo.mChangeLeash != null && (windowContainer = changeInfo.mContainer) != null && windowContainer.getParent() != null) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) windowContainer.mWmService.mTransactionFactory.get();
                SurfaceControl surfaceControl = (SurfaceControl) biFunction.apply(windowContainer, null);
                SurfaceControl surfaceControl2 = (SurfaceControl) function.apply(windowContainer);
                if (surfaceControl != null && surfaceControl2 != null) {
                    transaction.reparent(surfaceControl, surfaceControl2);
                    transaction.setLayer(surfaceControl, windowContainer.getLastLayer());
                    transaction.setCornerRadius(surfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    transaction.setShadowRadius(surfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    transaction.setAlpha(surfaceControl, 1.0f);
                    transaction.apply();
                    transaction.close();
                    Slog.d("ChangeTransitionController", "restoreFromChangeLeash. cause wc is not target for transition.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.android.server.wm.ChangeTransitionController$H */
    public final class HandlerC2887H extends Handler {
        public HandlerC2887H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = ChangeTransitionController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Slog.w("ChangeTransitionController", "SYNC_ACTIVITY_TIMEOUT!! " + ChangeTransitionController.this.mSyncDeferredAllDrawnApps);
                    ChangeTransitionController.this.mSyncDeferredAllDrawnApps.clear();
                    ChangeTransitionController.this.mWm.requestTraversal();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[ChangeTransitionController]");
        if (!this.mSyncDeferredAllDrawnApps.isEmpty()) {
            printWriter.println(str + "mSyncDeferredAllDrawnApps=" + this.mSyncDeferredAllDrawnApps);
        }
        if (this.mTransitionToChangingPipTask.isEmpty()) {
            return;
        }
        printWriter.println(str + "mTransitionToChangingPipTask=" + this.mTransitionToChangingPipTask);
    }
}
