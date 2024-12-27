package com.android.server.wm;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.view.RemoteAnimationDefinition;
import android.view.SurfaceControl;
import android.view.WindowManager;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class AppTransitionController {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final WallpaperController mWallpaperControllerLocked;
    public RemoteAnimationDefinition mRemoteAnimationDefinition = null;
    public final ArrayMap mTempTransitionReasons = new ArrayMap();
    public final ArrayList mTempTransitionWindows = new ArrayList();

    public AppTransitionController(
            WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mWallpaperControllerLocked = displayContent.mWallpaperController;
    }

    public static void applyAnimations(
            ArraySet arraySet,
            ArraySet arraySet2,
            int i,
            boolean z,
            WindowManager.LayoutParams layoutParams,
            boolean z2) {
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            WindowContainer windowContainer = (WindowContainer) arraySet.valueAt(i2);
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < arraySet2.size(); i3++) {
                ActivityRecord activityRecord = (ActivityRecord) arraySet2.valueAt(i3);
                if (activityRecord.isDescendantOf(windowContainer)) {
                    arrayList.add(activityRecord);
                }
            }
            windowContainer.applyAnimation(layoutParams, i, z, z2, arrayList);
        }
    }

    public static ArraySet getAnimationTargets(ArraySet arraySet, ArraySet arraySet2, boolean z) {
        boolean z2;
        ArrayDeque arrayDeque = new ArrayDeque();
        ArraySet arraySet3 = z ? arraySet : arraySet2;
        for (int i = 0; i < arraySet3.size(); i++) {
            ActivityRecord activityRecord = (ActivityRecord) arraySet3.valueAt(i);
            boolean z3 = activityRecord.mVisible;
            if (z3 != z
                    || activityRecord.mRequestForceTransition
                    || (!z3 && activityRecord.mIsExiting)) {
                arrayDeque.add(activityRecord);
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                            -3156084190956669377L,
                            60,
                            null,
                            String.valueOf(activityRecord),
                            Boolean.valueOf(activityRecord.mVisible),
                            Boolean.FALSE);
                }
            }
        }
        if (z) {
            arraySet = arraySet2;
        }
        ArraySet arraySet4 = new ArraySet();
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            for (WindowContainer windowContainer = (WindowContainer) arraySet.valueAt(i2);
                    windowContainer != null;
                    windowContainer = windowContainer.getParent()) {
                arraySet4.add(windowContainer);
            }
        }
        ArraySet arraySet5 = new ArraySet();
        ArrayList arrayList = new ArrayList();
        while (!arrayDeque.isEmpty()) {
            WindowContainer windowContainer2 = (WindowContainer) arrayDeque.removeFirst();
            WindowContainer parent = windowContainer2.getParent();
            arrayList.clear();
            arrayList.add(windowContainer2);
            if (!isTaskViewTask(windowContainer2)) {
                if (parent == null
                        || !parent.canCreateRemoteAnimationTarget()
                        || ((windowContainer2.asTask() != null
                                        && windowContainer2.asTask().mInRemoveTask)
                                || parent.isChangingAppTransition())) {
                    z2 = false;
                } else {
                    z2 = !arraySet4.contains(parent);
                    if (windowContainer2.asTask() != null
                            && windowContainer2.asTask().getAdjacentTask() != null) {
                        z2 = false;
                    }
                    for (int i3 = 0; i3 < parent.getChildCount(); i3++) {
                        WindowContainer childAt = parent.getChildAt(i3);
                        if (arrayDeque.remove(childAt)) {
                            if (!isTaskViewTask(childAt)) {
                                arrayList.add(childAt);
                            }
                        } else if (childAt != windowContainer2 && childAt.isVisible()) {
                            z2 = false;
                        }
                    }
                }
                if (z2) {
                    arrayDeque.add(parent);
                } else {
                    arraySet5.addAll(arrayList);
                }
            }
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM,
                    -8226278785414579647L,
                    0,
                    null,
                    String.valueOf(arraySet3),
                    String.valueOf(arraySet5));
        }
        return arraySet5;
    }

    public static ActivityRecord getAppFromContainer(WindowContainer windowContainer) {
        return windowContainer.asTaskFragment() != null
                ? windowContainer.asTaskFragment().getTopNonFinishingActivity(true, true)
                : windowContainer.asActivityRecord();
    }

    public static ActivityRecord getTopApp(ArraySet arraySet, boolean z) {
        int prefixOrderIndex;
        int i = Integer.MIN_VALUE;
        ActivityRecord activityRecord = null;
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            ActivityRecord appFromContainer =
                    getAppFromContainer((WindowContainer) arraySet.valueAt(size));
            if (appFromContainer != null
                    && ((!z || appFromContainer.mVisible)
                            && (prefixOrderIndex = appFromContainer.getPrefixOrderIndex()) > i)) {
                activityRecord = appFromContainer;
                i = prefixOrderIndex;
            }
        }
        return activityRecord;
    }

    public static int getTransitContainerType(WindowContainer windowContainer) {
        if (windowContainer == null) {
            return 0;
        }
        if (windowContainer.asTask() != null) {
            return 3;
        }
        if (windowContainer.asTaskFragment() != null) {
            return 2;
        }
        return windowContainer.asActivityRecord() != null ? 1 : 0;
    }

    public static boolean isTaskViewTask(WindowContainer windowContainer) {
        if ((windowContainer instanceof Task)
                && ((Task) windowContainer).mRemoveWithTaskOrganizer) {
            return true;
        }
        WindowContainer parent = windowContainer.getParent();
        return parent != null
                && (parent instanceof Task)
                && ((Task) parent).mRemoveWithTaskOrganizer;
    }

    public static ActivityRecord lookForHighestTokenWithFilter(
            ArraySet arraySet, ArraySet arraySet2, ArraySet arraySet3, Predicate predicate) {
        int size = arraySet.size();
        int size2 = arraySet2.size() + size;
        int size3 = arraySet3.size() + size2;
        int i = Integer.MIN_VALUE;
        ActivityRecord activityRecord = null;
        int i2 = 0;
        while (i2 < size3) {
            WindowContainer windowContainer =
                    i2 < size
                            ? (WindowContainer) arraySet.valueAt(i2)
                            : i2 < size2
                                    ? (WindowContainer) arraySet2.valueAt(i2 - size)
                                    : (WindowContainer) arraySet3.valueAt(i2 - size2);
            int prefixOrderIndex = windowContainer.getPrefixOrderIndex();
            ActivityRecord appFromContainer = getAppFromContainer(windowContainer);
            if (appFromContainer != null
                    && predicate.test(appFromContainer)
                    && prefixOrderIndex > i) {
                activityRecord = appFromContainer;
                i = prefixOrderIndex;
            }
            i2++;
        }
        return activityRecord;
    }

    public final void applyAnimations(
            ArraySet arraySet,
            ArraySet arraySet2,
            int i,
            WindowManager.LayoutParams layoutParams,
            boolean z) {
        RecentsAnimationController recentsAnimationController =
                this.mService.mRecentsAnimationController;
        if (i == -1 || (arraySet.isEmpty() && arraySet2.isEmpty())) {
            if (recentsAnimationController != null) {
                recentsAnimationController.sendTasksAppeared();
                return;
            }
            return;
        }
        ArrayList arrayList = AppTransition.sFlagToString;
        if (i == 6 || i == 7 || i == 18) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                ActivityRecord activityRecord = (ActivityRecord) arraySet2.valueAt(i2);
                if (activityRecord.areBoundsLetterboxed()) {
                    arrayList2.add(new Pair(activityRecord, activityRecord.getLetterboxInsets()));
                }
            }
            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                ActivityRecord activityRecord2 = (ActivityRecord) arraySet.valueAt(i3);
                if (activityRecord2.areBoundsLetterboxed()) {
                    Rect letterboxInsets = activityRecord2.getLetterboxInsets();
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        if (letterboxInsets.equals((Rect) pair.second)) {
                            ActivityRecord activityRecord3 = (ActivityRecord) pair.first;
                            activityRecord2.mNeedsLetterboxedAnimation = true;
                            activityRecord3.mNeedsLetterboxedAnimation = true;
                        }
                    }
                }
            }
        }
        ArraySet animationTargets = getAnimationTargets(arraySet, arraySet2, true);
        ArraySet animationTargets2 = getAnimationTargets(arraySet, arraySet2, false);
        applyAnimations(animationTargets, arraySet, i, true, layoutParams, z);
        applyAnimations(animationTargets2, arraySet2, i, false, layoutParams, z);
        if (recentsAnimationController != null) {
            recentsAnimationController.sendTasksAppeared();
        }
        for (int i4 = 0; i4 < arraySet.size(); i4++) {
            ((ActivityRecord) arraySet.valueAtUnchecked(i4)).mOverrideTaskTransition = false;
        }
        for (int i5 = 0; i5 < arraySet2.size(); i5++) {
            ((ActivityRecord) arraySet2.valueAtUnchecked(i5)).mOverrideTaskTransition = false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        AccessibilityController accessibilityController =
                displayContent.mWmService.mAccessibilityController;
        if (accessibilityController.hasCallbacks()) {
            int i6 = displayContent.mDisplayId;
            AccessibilityController.AccessibilityControllerInternalImpl
                    accessibilityControllerInternalImpl =
                            accessibilityController.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl.logTrace(
                        "AccessibilityController.onAppWindowTransition",
                        2048L,
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i6, i, "displayId=", "; transition="));
            }
            AccessibilityController.DisplayMagnifier displayMagnifier =
                    (AccessibilityController.DisplayMagnifier)
                            accessibilityController.mDisplayMagnifiers.get(i6);
            if (displayMagnifier != null) {
                AccessibilityController.AccessibilityControllerInternalImpl
                        accessibilityControllerInternalImpl2 =
                                displayMagnifier.mAccessibilityTracing;
                if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
                    accessibilityControllerInternalImpl2.logTrace(
                            "WindowManager.onAppWindowTransition",
                            2048L,
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i6, i, "displayId=", "; transition="));
                }
                if (displayMagnifier.isFullscreenMagnificationActivated()) {
                    if (i != 6 && i != 8 && i != 10 && i != 28) {
                        switch (i) {
                        }
                    }
                    displayMagnifier.mUserContextChangedNotifier
                            .sendUserContextChangedNotification();
                }
            }
        }
    }

    public final void handleClosingApps() {
        DisplayContent displayContent = this.mDisplayContent;
        ArraySet arraySet = displayContent.mClosingApps;
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            ActivityRecord activityRecord = (ActivityRecord) arraySet.valueAt(i);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                        -8367738619313176909L,
                        0,
                        null,
                        String.valueOf(activityRecord));
            }
            activityRecord.commitVisibility(false, false, false);
            activityRecord.updateReportedVisibilityLocked();
            activityRecord.allDrawn = true;
            WindowState windowState = activityRecord.mStartingWindow;
            if (windowState != null && !windowState.mAnimatingExit) {
                activityRecord.removeStartingWindow();
            }
            int i2 = displayContent.mAppTransition.mNextAppTransitionType;
            if (i2 == 4 || i2 == 6) {
                activityRecord.attachThumbnailAnimation();
            }
        }
    }

    public final void handleOpeningApps() {
        DisplayContent displayContent = this.mDisplayContent;
        ArraySet arraySet = displayContent.mOpeningApps;
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            ActivityRecord activityRecord = (ActivityRecord) arraySet.valueAt(i);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                        4418653408751596915L,
                        0,
                        null,
                        String.valueOf(activityRecord));
            }
            activityRecord.commitVisibility(true, false, false);
            WindowContainer animatingContainer = activityRecord.getAnimatingContainer(2, 1);
            if (animatingContainer == null
                    || !animatingContainer.getAnimationSources().contains(activityRecord)) {
                ((ArrayList) displayContent.mNoAnimationNotifyOnTransitionFinished)
                        .add(activityRecord.token);
            }
            activityRecord.updateReportedVisibilityLocked();
            activityRecord.forAllWindows(
                    (Consumer) new ActivityRecord$$ExternalSyntheticLambda3(0), false);
            int i2 = displayContent.mAppTransition.mNextAppTransitionType;
            if (i2 == 3 || i2 == 5) {
                activityRecord.attachThumbnailAnimation();
            } else if (i2 == 9 && activityRecord.isAnimating(2, 1)) {
                activityRecord.clearThumbnail();
                WindowState findMainWindow = activityRecord.findMainWindow(true);
                if (findMainWindow != null) {
                    Rect rect = findMainWindow.mWindowFrames.mRelFrame;
                    Context context = activityRecord.mAtmService.mUiContext;
                    HardwareBuffer createCrossProfileAppsThumbnail =
                            activityRecord
                                    .getDisplayContent()
                                    .mAppTransition
                                    .mTransitionAnimation
                                    .createCrossProfileAppsThumbnail(
                                            activityRecord.task.mUserId
                                                            == activityRecord
                                                                    .mWmService
                                                                    .mCurrentUserId
                                                    ? context.getDrawable(
                                                            R.drawable.ic_btn_search_go)
                                                    : ((DevicePolicyManager)
                                                                    context.getSystemService(
                                                                            DevicePolicyManager
                                                                                    .class))
                                                            .getResources()
                                                            .getDrawable(
                                                                    "WORK_PROFILE_ICON",
                                                                    "OUTLINE",
                                                                    "PROFILE_SWITCH_ANIMATION",
                                                                    new ActivityRecord$$ExternalSyntheticLambda23(
                                                                            1, context)),
                                            rect);
                    if (createCrossProfileAppsThumbnail != null) {
                        SurfaceControl.Transaction pendingTransaction =
                                activityRecord.getPendingTransaction();
                        activityRecord.mThumbnail =
                                new WindowContainerThumbnail(
                                        pendingTransaction,
                                        activityRecord.task,
                                        createCrossProfileAppsThumbnail);
                        activityRecord.mThumbnail.startAnimation(
                                pendingTransaction,
                                activityRecord
                                        .getDisplayContent()
                                        .mAppTransition
                                        .mTransitionAnimation
                                        .createCrossProfileAppsThumbnailAnimationLocked(rect),
                                new Point(rect.left, rect.top));
                    }
                }
            }
        }
    }

    public boolean isTransitWithinTask(int i, Task task) {
        if (task != null) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mChangingContainers.isEmpty()) {
                if (i != 6 && i != 7 && i != 18) {
                    return false;
                }
                Iterator it = displayContent.mOpeningApps.iterator();
                while (it.hasNext()) {
                    if (((ActivityRecord) it.next()).task != task) {
                        return false;
                    }
                }
                Iterator it2 = displayContent.mClosingApps.iterator();
                while (it2.hasNext()) {
                    if (((ActivityRecord) it2.next()).task != task) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final boolean transitionGoodToGo(ArraySet arraySet, ArrayMap arrayMap) {
        ScreenRotationAnimation.SurfaceRotationAnimationController
                surfaceRotationAnimationController;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled;
        boolean z = zArr[1];
        WindowManagerService windowManagerService = this.mService;
        DisplayContent displayContent = this.mDisplayContent;
        if (z) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                    2951634988136738868L,
                    61,
                    null,
                    Long.valueOf(arraySet.size()),
                    Boolean.valueOf(windowManagerService.mDisplayFrozen),
                    Boolean.valueOf(displayContent.mAppTransition.mAppTransitionState == 3));
        }
        if (displayContent.mAppTransition.mAppTransitionState == 3) {
            return true;
        }
        ScreenRotationAnimation screenRotationAnimation =
                windowManagerService.mRoot.getDisplayContent(0).mScreenRotationAnimation;
        if (screenRotationAnimation != null
                && (surfaceRotationAnimationController =
                                screenRotationAnimation.mSurfaceRotationAnimationController)
                        != null
                && surfaceRotationAnimationController.isAnimating()) {
            DisplayRotation displayRotation = displayContent.mDisplayRotation;
            int i = displayRotation.mRotation;
            if (i != displayRotation.rotationForOrientation(displayRotation.mLastOrientation, i)) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                            4963754906024950916L,
                            0,
                            null,
                            null);
                }
                return false;
            }
        }
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            ActivityRecord appFromContainer =
                    getAppFromContainer((WindowContainer) arraySet.valueAt(i2));
            if (appFromContainer != null) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                            5073676463280304697L,
                            1020,
                            null,
                            String.valueOf(appFromContainer),
                            Boolean.valueOf(appFromContainer.allDrawn),
                            Boolean.valueOf(appFromContainer.isStartingWindowDisplayed()),
                            Boolean.valueOf(appFromContainer.startingMoved),
                            Boolean.valueOf(appFromContainer.isRelaunching()),
                            String.valueOf(appFromContainer.mStartingWindow));
                }
                boolean z2 = appFromContainer.allDrawn && !appFromContainer.isRelaunching();
                if (!z2
                        && !appFromContainer.isStartingWindowDisplayed()
                        && !appFromContainer.startingMoved) {
                    return false;
                }
                if (z2) {
                    arrayMap.put(appFromContainer, 2);
                } else {
                    arrayMap.put(
                            appFromContainer,
                            Integer.valueOf(
                                    appFromContainer.mStartingData
                                                    instanceof SplashScreenStartingData
                                            ? 1
                                            : 4));
                }
            }
        }
        if (displayContent.mAppTransition.mNextAppTransitionAnimationsSpecsPending) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                        3437142041296647115L,
                        0,
                        null,
                        null);
            }
            return false;
        }
        if (displayContent.mUnknownAppVisibilityController.mUnknownApps.isEmpty()) {
            WallpaperController wallpaperController = this.mWallpaperControllerLocked;
            return !wallpaperController.isWallpaperVisible()
                    || wallpaperController.wallpaperTransitionReady();
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                    1461079689316480707L,
                    0,
                    null,
                    String.valueOf(
                            displayContent.mUnknownAppVisibilityController.getDebugMessage()));
        }
        return false;
    }
}
