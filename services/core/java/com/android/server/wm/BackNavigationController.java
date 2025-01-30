package com.android.server.wm;

import android.R;
import android.content.res.ResourceId;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.BackAnimationAdapter;
import android.window.BackNavigationInfo;
import android.window.IBackAnimationFinishedCallback;
import android.window.OnBackInvokedCallbackInfo;
import android.window.TaskSnapshot;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.LocalServices;
import com.android.server.wm.utils.InsetUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class BackNavigationController {
  public static int sDefaultAnimationResId;
  public static final boolean sPredictBackEnable =
      SystemProperties.getBoolean("persist.wm.debug.predictive_back", true);
  public AnimationHandler mAnimationHandler;
  public boolean mBackAnimationInProgress;
  public int mLastBackType;
  public Runnable mPendingAnimation;
  public AnimationHandler.ScheduleAnimationBuilder mPendingAnimationBuilder;
  public boolean mShowWallpaper;
  public Transition mWaitTransitionFinish;
  public WindowManagerService mWindowManagerService;
  public final NavigationMonitor mNavigationMonitor = new NavigationMonitor();
  public final ArrayList mTmpOpenApps = new ArrayList();
  public final ArrayList mTmpCloseApps = new ArrayList();

  public static boolean isScreenshotEnabled() {
    return SystemProperties.getInt("persist.wm.debug.predictive_back_screenshot", 0) != 0;
  }

  public void onFocusChanged(WindowState windowState) {
    this.mNavigationMonitor.onFocusWindowChanged(windowState);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public BackNavigationInfo startBackNavigation(
      RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) {
    Task task;
    ActivityRecord activityRecord;
    OnBackInvokedCallbackInfo onBackInvokedCallbackInfo;
    int i;
    Task task2;
    Task task3;
    if (!sPredictBackEnable) {
      return null;
    }
    WindowManagerService windowManagerService = this.mWindowManagerService;
    BackNavigationInfo.Builder builder = new BackNavigationInfo.Builder();
    WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (isMonitoringTransition()) {
          Slog.w(
              "CoreBackPreview",
              "Previous animation hasn't finish, status: " + this.mAnimationHandler);
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        IBinder focusedWindowToken =
            ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class))
                .getFocusedWindowToken();
        WindowState focusedWindowLocked = windowManagerService.getFocusedWindowLocked();
        if (focusedWindowLocked == null
            && windowManagerService.mEmbeddedWindowController.getByFocusToken(focusedWindowToken)
                != null) {
          if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                -1717147904,
                0,
                "Current focused window is embeddedWindow. Dispatch KEYCODE_BACK.",
                (Object[]) null);
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        if (focusedWindowLocked != null && ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
              -997565097,
              0,
              "Focused window found using getFocusedWindowToken",
              (Object[]) null);
        }
        if (focusedWindowLocked != null) {
          RecentsAnimationController recentsAnimationController =
              windowManagerService.getRecentsAnimationController();
          ActivityRecord activityRecord2 = focusedWindowLocked.mActivityRecord;
          if ((activityRecord2 != null
                  && activityRecord2.isActivityTypeHomeOrRecents()
                  && activityRecord2.mTransitionController.isTransientLaunch(activityRecord2))
              || (recentsAnimationController != null
                  && recentsAnimationController.shouldApplyInputConsumer(activityRecord2))) {
            if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
              ProtoLogImpl.d(
                  ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                  -451552570,
                  0,
                  "Current focused window being animated by recents. Overriding back callback to"
                      + " recents controller callback.",
                  (Object[]) null);
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
          if (!focusedWindowLocked.isDrawn()) {
            if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
              ProtoLogImpl.d(
                  ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                  1945495497,
                  0,
                  "Focused window didn't have a valid surface drawn.",
                  (Object[]) null);
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
        }
        if (focusedWindowLocked == null) {
          if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
            ProtoLogImpl.w(
                ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                1264179654,
                0,
                "No focused window, defaulting to top current task's window",
                (Object[]) null);
          }
          task = windowManagerService.mAtmService.getTopDisplayFocusedRootTask();
          if (task != null) {
            focusedWindowLocked =
                task.getWindow(
                    new Predicate() { // from class:
                                      // com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda0
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return ((WindowState) obj).isFocused();
                      }
                    });
          }
        } else {
          task = null;
        }
        final int i2 = 4;
        if (focusedWindowLocked != null) {
          ActivityRecord activityRecord3 = focusedWindowLocked.mActivityRecord;
          Task task4 = focusedWindowLocked.getTask();
          onBackInvokedCallbackInfo = focusedWindowLocked.getOnBackInvokedCallbackInfo();
          if (onBackInvokedCallbackInfo == null) {
            Slog.e("CoreBackPreview", "No callback registered, returning null.");
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
          i = !onBackInvokedCallbackInfo.isSystemCallback() ? 4 : -1;
          builder.setOnBackInvokedCallback(onBackInvokedCallbackInfo.getCallback());
          builder.setAnimationCallback(onBackInvokedCallbackInfo.isAnimationCallback());
          this.mNavigationMonitor.startMonitor(focusedWindowLocked, remoteCallback);
          activityRecord = activityRecord3;
          task = task4;
        } else {
          activityRecord = 0;
          onBackInvokedCallbackInfo = null;
          i = -1;
        }
        if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
              -1277068810,
              0,
              "startBackNavigation currentTask=%s, topRunningActivity=%s, callbackInfo=%s,"
                  + " currentFocus=%s",
              new Object[] {
                String.valueOf(task),
                String.valueOf(activityRecord),
                String.valueOf(onBackInvokedCallbackInfo),
                String.valueOf(focusedWindowLocked)
              });
        }
        if (focusedWindowLocked == null) {
          Slog.e("CoreBackPreview", "Window is null, returning null.");
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        if (i != 4
            && activityRecord != 0
            && task != null
            && !activityRecord.isActivityTypeHome()
            && !activityRecord.mHasSceneTransition) {
          ActivityRecord activityRecord4 = task.topRunningActivity(activityRecord.token, -1);
          boolean isKeyguardOccluded = isKeyguardOccluded(focusedWindowLocked);
          if (focusedWindowLocked.getParent().getChildCount() > 1
              && focusedWindowLocked.getParent().getChildAt(0) != focusedWindowLocked) {
            task2 = null;
            i2 = 0;
          } else if (activityRecord4 != null) {
            if (!isKeyguardOccluded || activityRecord4.canShowWhenLocked()) {
              WindowContainer parent = activityRecord.getParent();
              if (parent != null
                  && (parent.asTask() != null
                      || (parent.asTaskFragment() != null && parent.canCustomizeAppTransition()))) {
                if (isCustomizeExitAnimation(focusedWindowLocked)) {
                  WindowManager.LayoutParams layoutParams = focusedWindowLocked.mAttrs;
                  builder.setWindowAnimations(
                      layoutParams.packageName, layoutParams.windowAnimations);
                }
                ActivityRecord.CustomAppTransition customAnimation =
                    activityRecord.getCustomAnimation(false);
                if (customAnimation != null) {
                  builder.setCustomAnimation(
                      activityRecord.packageName,
                      customAnimation.mEnterAnim,
                      customAnimation.mExitAnim,
                      customAnimation.mBackgroundColor);
                }
              }
              task2 = activityRecord4.getTask();
              focusedWindowLocked = activityRecord;
              i2 = 2;
            }
            focusedWindowLocked = null;
            task2 = null;
          } else {
            if (task.returnsToHomeRootTask()) {
              if (!isKeyguardOccluded) {
                task3 = task.getDisplayArea().getRootHomeTask();
                this.mShowWallpaper = true;
              }
              focusedWindowLocked = null;
              task2 = null;
            } else if (activityRecord.isRootOfTask()) {
              task3 =
                  task.mRootWindowContainer.getTask(
                      new Predicate() { // from class:
                                        // com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return ((Task) obj).showToCurrentUser();
                        }
                      },
                      task,
                      false,
                      true);
              if (task3 != null
                  && !task3.inMultiWindowMode()
                  && (activityRecord4 = task3.getTopNonFinishingActivity()) != null
                  && (!isKeyguardOccluded || activityRecord4.canShowWhenLocked())) {
                if (task3.isActivityTypeHome()) {
                  this.mShowWallpaper = true;
                } else {
                  task2 = task3;
                  i2 = 3;
                  focusedWindowLocked = task;
                }
              }
              task2 = task3;
              focusedWindowLocked = task;
            } else {
              focusedWindowLocked = null;
              task2 = null;
              i2 = i;
            }
            task2 = task3;
            i2 = 1;
            focusedWindowLocked = task;
          }
          builder.setType(i2);
          if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                531891870,
                0,
                "Previous Destination is Activity:%s Task:%s removedContainer:%s, backType=%s",
                new Object[] {
                  String.valueOf(
                      activityRecord4 != null ? activityRecord4.mActivityComponent : null),
                  String.valueOf(task2 != null ? task2.getName() : null),
                  String.valueOf(focusedWindowLocked),
                  String.valueOf(BackNavigationInfo.typeToString(i2))
                });
          }
          boolean z = (i2 == 1 || i2 == 3 || i2 == 2) && backAnimationAdapter != null;
          if (z) {
            AnimationHandler.ScheduleAnimationBuilder prepareAnimation =
                this.mAnimationHandler.prepareAnimation(
                    i2, backAnimationAdapter, task, task2, activityRecord, activityRecord4);
            boolean z2 = prepareAnimation != null;
            this.mBackAnimationInProgress = z2;
            if (z2) {
              if (!focusedWindowLocked.hasCommittedReparentToAnimationLeash()
                  && !focusedWindowLocked.mTransitionController.inTransition()
                  && !this.mWindowManagerService.mSyncEngine.hasPendingSyncSets()) {
                scheduleAnimation(prepareAnimation);
              }
              if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
                ProtoLogImpl.w(
                    ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                    -1868518158,
                    0,
                    "Pending back animation due to another animation is running",
                    (Object[]) null);
              }
              this.mPendingAnimationBuilder = prepareAnimation;
              if (activityRecord4 != null) {
                activityRecord4.setDeferHidingClient(true);
              }
            }
          }
          builder.setPrepareRemoteAnimation(z);
          WindowManagerService.resetPriorityAfterLockedSection();
          if (focusedWindowLocked != null) {
            builder.setOnBackNavigationDone(
                new RemoteCallback(
                    new RemoteCallback
                        .OnResultListener() { // from class:
                                              // com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda3
                      public final void onResult(Bundle bundle) {
                        BackNavigationController.this.lambda$startBackNavigation$1(i2, bundle);
                      }
                    }));
          }
          this.mLastBackType = i2;
          return builder.build();
        }
        builder.setType(4);
        builder.setOnBackNavigationDone(
            new RemoteCallback(
                new RemoteCallback
                    .OnResultListener() { // from class:
                                          // com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda1
                  public final void onResult(Bundle bundle) {
                    BackNavigationController.this.lambda$startBackNavigation$0(bundle);
                  }
                }));
        this.mLastBackType = 4;
        BackNavigationInfo build = builder.build();
        WindowManagerService.resetPriorityAfterLockedSection();
        return build;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startBackNavigation$0(Bundle bundle) {
    lambda$startBackNavigation$1(bundle, 4);
  }

  public boolean isMonitoringTransition() {
    return this.mAnimationHandler.mComposed || this.mNavigationMonitor.isMonitorForRemote();
  }

  public final void scheduleAnimation(
      AnimationHandler.ScheduleAnimationBuilder scheduleAnimationBuilder) {
    this.mPendingAnimation = scheduleAnimationBuilder.build();
    this.mWindowManagerService.mWindowPlacerLocked.requestTraversal();
    if (this.mShowWallpaper) {
      this.mWindowManagerService
          .getDefaultDisplayContentLocked()
          .mWallpaperController
          .adjustWallpaperWindows();
    }
  }

  public final boolean isWaitBackTransition() {
    return this.mAnimationHandler.mComposed && this.mAnimationHandler.mWaitTransition;
  }

  public boolean isKeyguardOccluded(WindowState windowState) {
    KeyguardController keyguardController =
        this.mWindowManagerService.mAtmService.mKeyguardController;
    int displayId = windowState.getDisplayId();
    return keyguardController.isKeyguardLocked(displayId)
        && keyguardController.isDisplayOccluded(displayId);
  }

  public static boolean isCustomizeExitAnimation(WindowState windowState) {
    if (Objects.equals(windowState.mAttrs.packageName, "android")
        || windowState.mAttrs.windowAnimations == 0) {
      return false;
    }
    TransitionAnimation transitionAnimation =
        windowState.getDisplayContent().mAppTransition.mTransitionAnimation;
    int animationResId = transitionAnimation.getAnimationResId(windowState.mAttrs, 7, 0);
    if (!ResourceId.isValid(animationResId)) {
      return false;
    }
    if (sDefaultAnimationResId == 0) {
      sDefaultAnimationResId = transitionAnimation.getDefaultAnimationResId(7, 0);
    }
    return sDefaultAnimationResId != animationResId;
  }

  public boolean removeIfContainsBackAnimationTargets(ArraySet arraySet, ArraySet arraySet2) {
    if (!isMonitoringTransition()) {
      return false;
    }
    this.mTmpCloseApps.addAll(arraySet2);
    boolean removeIfWaitForBackTransition = removeIfWaitForBackTransition(arraySet, arraySet2);
    if (!removeIfWaitForBackTransition) {
      this.mNavigationMonitor.onTransitionReadyWhileNavigate(this.mTmpOpenApps, this.mTmpCloseApps);
    }
    this.mTmpCloseApps.clear();
    return removeIfWaitForBackTransition;
  }

  public boolean removeIfWaitForBackTransition(ArraySet arraySet, ArraySet arraySet2) {
    if (!isWaitBackTransition()
        || !this.mAnimationHandler.containsBackAnimationTargets(
            this.mTmpOpenApps, this.mTmpCloseApps)) {
      return false;
    }
    for (int size = arraySet.size() - 1; size >= 0; size--) {
      if (this.mAnimationHandler.isTarget((ActivityRecord) arraySet.valueAt(size), true)) {
        arraySet.removeAt(size);
        this.mAnimationHandler.mOpenTransitionTargetMatch = true;
      }
    }
    for (int size2 = arraySet2.size() - 1; size2 >= 0; size2--) {
      if (this.mAnimationHandler.isTarget((ActivityRecord) arraySet2.valueAt(size2), false)) {
        arraySet2.removeAt(size2);
      }
    }
    return true;
  }

  public class NavigationMonitor {
    public WindowState mNavigatingWindow;
    public RemoteCallback mObserver;

    public NavigationMonitor() {}

    public void startMonitor(WindowState windowState, RemoteCallback remoteCallback) {
      this.mNavigatingWindow = windowState;
      this.mObserver = remoteCallback;
    }

    public void stopMonitorForRemote() {
      this.mObserver = null;
    }

    public void stopMonitorTransition() {
      this.mNavigatingWindow = null;
    }

    public boolean isMonitorForRemote() {
      return (this.mNavigatingWindow == null || this.mObserver == null) ? false : true;
    }

    public boolean isMonitorAnimationOrTransition() {
      return this.mNavigatingWindow != null
          && (BackNavigationController.this.mAnimationHandler.mComposed
              || BackNavigationController.this.mAnimationHandler.mWaitTransition);
    }

    public final void onFocusWindowChanged(WindowState windowState) {
      WindowState windowState2;
      if (atSameDisplay(windowState)) {
        if ((!isMonitorForRemote() && !isMonitorAnimationOrTransition())
            || windowState == null
            || windowState == (windowState2 = this.mNavigatingWindow)) {
          return;
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord == null || activityRecord == windowState2.mActivityRecord) {
          cancelBackNavigating("focusWindowChanged");
        }
      }
    }

    public final void onTransitionReadyWhileNavigate(ArrayList arrayList, ArrayList arrayList2) {
      if (isMonitorForRemote() || isMonitorAnimationOrTransition()) {
        ArrayList arrayList3 = new ArrayList(arrayList);
        arrayList3.addAll(arrayList2);
        for (int size = arrayList3.size() - 1; size >= 0; size--) {
          if (((WindowContainer) arrayList3.get(size)).hasChild(this.mNavigatingWindow)) {
            cancelBackNavigating("transitionHappens");
            return;
          }
        }
      }
    }

    public final boolean atSameDisplay(WindowState windowState) {
      WindowState windowState2 = this.mNavigatingWindow;
      if (windowState2 == null) {
        return false;
      }
      return windowState == null || windowState.getDisplayId() == windowState2.getDisplayId();
    }

    public final void cancelBackNavigating(String str) {
      EventLogTags.writeWmBackNaviCanceled(str);
      if (isMonitorForRemote()) {
        this.mObserver.sendResult((Bundle) null);
      }
      if (isMonitorAnimationOrTransition()) {
        BackNavigationController.this.clearBackAnimations();
      }
      BackNavigationController.this.cancelPendingAnimation();
    }
  }

  public void onTransactionReady(Transition transition, ArrayList arrayList) {
    int i;
    if (isMonitoringTransition()) {
      for (int size = arrayList.size() - 1; size >= 0; size--) {
        WindowContainer windowContainer = ((Transition.ChangeInfo) arrayList.get(size)).mContainer;
        if (windowContainer.asActivityRecord() != null
            || windowContainer.asTask() != null
            || windowContainer.asTaskFragment() != null) {
          if (windowContainer.isVisibleRequested()) {
            this.mTmpOpenApps.add(windowContainer);
          } else {
            this.mTmpCloseApps.add(windowContainer);
          }
        }
      }
      boolean z =
          isWaitBackTransition()
              && ((i = transition.mType) == 2 || i == 4)
              && this.mAnimationHandler.containsBackAnimationTargets(
                  this.mTmpOpenApps, this.mTmpCloseApps);
      if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
            -1258739769,
            192,
            "onTransactionReady, opening: %s, closing: %s, animating: %s, match: %b",
            new Object[] {
              String.valueOf(this.mTmpOpenApps),
              String.valueOf(this.mTmpCloseApps),
              String.valueOf(this.mAnimationHandler),
              Boolean.valueOf(z)
            });
      }
      if (!z) {
        this.mNavigationMonitor.onTransitionReadyWhileNavigate(
            this.mTmpOpenApps, this.mTmpCloseApps);
      } else {
        if (this.mWaitTransitionFinish != null) {
          Slog.e("CoreBackPreview", "Gesture animation is applied on another transition?");
        }
        this.mWaitTransitionFinish = transition;
      }
      this.mTmpOpenApps.clear();
      this.mTmpCloseApps.clear();
    }
  }

  public boolean isMonitorTransitionTarget(WindowContainer windowContainer) {
    if (!isWaitBackTransition() || this.mWaitTransitionFinish == null) {
      return false;
    }
    return this.mAnimationHandler.isTarget(windowContainer, windowContainer.isVisibleRequested());
  }

  public void clearBackAnimations() {
    this.mAnimationHandler.clearBackAnimateTarget();
    this.mNavigationMonitor.stopMonitorTransition();
    this.mWaitTransitionFinish = null;
  }

  public boolean onTransitionFinish(ArrayList arrayList, Transition transition) {
    boolean z;
    if (transition == this.mWaitTransitionFinish) {
      clearBackAnimations();
    }
    if (!this.mBackAnimationInProgress || this.mPendingAnimationBuilder == null) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
          -692907078,
          0,
          "Handling the deferred animation after transition finished",
          (Object[]) null);
    }
    int i = 0;
    while (true) {
      if (i >= transition.mParticipants.size()) {
        z = false;
        break;
      }
      WindowContainer windowContainer = (WindowContainer) transition.mParticipants.valueAt(i);
      if (!(windowContainer.asActivityRecord() == null
              && windowContainer.asTask() == null
              && windowContainer.asTaskFragment() == null)
          && this.mPendingAnimationBuilder.containTarget(windowContainer)) {
        z = true;
        break;
      }
      i++;
    }
    if (!z) {
      Slog.w(
          "CoreBackPreview",
          "Finished transition didn't include the targets open: "
              + this.mPendingAnimationBuilder.mOpenTarget
              + " close: "
              + this.mPendingAnimationBuilder.mCloseTarget);
      cancelPendingAnimation();
      return false;
    }
    for (int i2 = 0; i2 < arrayList.size(); i2++) {
      ((Transition.ChangeInfo) arrayList.get(i2)).mContainer.prepareSurfaces();
    }
    scheduleAnimation(this.mPendingAnimationBuilder);
    this.mPendingAnimationBuilder = null;
    return true;
  }

  public final void cancelPendingAnimation() {
    AnimationHandler.ScheduleAnimationBuilder scheduleAnimationBuilder =
        this.mPendingAnimationBuilder;
    if (scheduleAnimationBuilder == null) {
      return;
    }
    try {
      scheduleAnimationBuilder.mBackAnimationAdapter.getRunner().onAnimationCancelled();
    } catch (RemoteException e) {
      Slog.e("CoreBackPreview", "Remote animation gone", e);
    }
    this.mPendingAnimationBuilder = null;
  }

  public class AnimationHandler {
    public BackWindowAnimationAdaptor mCloseAdaptor;
    public boolean mComposed;
    public ActivityRecord mOpenActivity;
    public BackWindowAnimationAdaptor mOpenAdaptor;
    public boolean mOpenTransitionTargetMatch;
    public int mRequestedStartingSurfaceTaskId;
    public final boolean mShowWindowlessSurface;
    public SurfaceControl mStartingSurface;
    public int mSwitchType = 0;
    public boolean mWaitTransition;
    public final WindowManagerService mWindowManagerService;

    public AnimationHandler(WindowManagerService windowManagerService) {
      this.mWindowManagerService = windowManagerService;
      this.mShowWindowlessSurface =
          windowManagerService
              .mContext
              .getResources()
              .getBoolean(R.bool.config_multiuserDelayUserDataLocking);
    }

    public static boolean isActivitySwitch(
        WindowContainer windowContainer, WindowContainer windowContainer2) {
      return (windowContainer.asActivityRecord() == null
              || windowContainer2.asActivityRecord() == null
              || windowContainer.asActivityRecord().getTask()
                  != windowContainer2.asActivityRecord().getTask())
          ? false
          : true;
    }

    public static boolean isTaskSwitch(
        WindowContainer windowContainer, WindowContainer windowContainer2) {
      return (windowContainer.asTask() == null
              || windowContainer2.asTask() == null
              || windowContainer.asTask() == windowContainer2.asTask())
          ? false
          : true;
    }

    public final void initiate(
        WindowContainer windowContainer,
        WindowContainer windowContainer2,
        ActivityRecord activityRecord) {
      ActivityRecord topNonFinishingActivity;
      if (isActivitySwitch(windowContainer, windowContainer2)) {
        this.mSwitchType = 2;
        topNonFinishingActivity = windowContainer.asActivityRecord();
      } else if (isTaskSwitch(windowContainer, windowContainer2)) {
        this.mSwitchType = 1;
        topNonFinishingActivity = windowContainer.asTask().getTopNonFinishingActivity();
      } else {
        this.mSwitchType = 0;
        return;
      }
      this.mCloseAdaptor = createAdaptor(topNonFinishingActivity, false);
      this.mOpenAdaptor = createAdaptor(windowContainer2, true);
      this.mOpenActivity = activityRecord;
      if (this.mCloseAdaptor.mAnimationTarget == null
          || this.mOpenAdaptor.mAnimationTarget == null) {
        Slog.w("CoreBackPreview", "composeNewAnimations fail, skip");
        clearBackAnimateTarget();
      }
    }

    public boolean composeAnimations(
        WindowContainer windowContainer,
        WindowContainer windowContainer2,
        ActivityRecord activityRecord) {
      if (this.mComposed || this.mWaitTransition) {
        Slog.e("CoreBackPreview", "Previous animation is running " + this);
        return false;
      }
      clearBackAnimateTarget();
      if (windowContainer == null || windowContainer2 == null || activityRecord == null) {
        Slog.e(
            "CoreBackPreview",
            "reset animation with null target close: "
                + windowContainer
                + " open: "
                + windowContainer2);
        return false;
      }
      initiate(windowContainer, windowContainer2, activityRecord);
      if (this.mSwitchType == 0) {
        return false;
      }
      this.mComposed = true;
      this.mWaitTransition = false;
      return true;
    }

    public RemoteAnimationTarget[] getAnimationTargets() {
      if (this.mComposed) {
        return new RemoteAnimationTarget[] {
          this.mCloseAdaptor.mAnimationTarget, this.mOpenAdaptor.mAnimationTarget
        };
      }
      return null;
    }

    public boolean isSupportWindowlessSurface() {
      return this.mWindowManagerService.mAtmService.mTaskOrganizerController
          .isSupportWindowlessStartingSurface();
    }

    public void createStartingSurface(TaskSnapshot taskSnapshot) {
      if (this.mComposed && getTopOpenActivity() == null) {
        Slog.e("CoreBackPreview", "createStartingSurface fail, no open activity: " + this);
      }
    }

    public final ActivityRecord getTopOpenActivity() {
      int i = this.mSwitchType;
      if (i == 2) {
        return this.mOpenAdaptor.mTarget.asActivityRecord();
      }
      if (i == 1) {
        return this.mOpenAdaptor.mTarget.asTask().getTopNonFinishingActivity();
      }
      return null;
    }

    public boolean containTarget(ArrayList arrayList, boolean z) {
      for (int size = arrayList.size() - 1; size >= 0; size--) {
        if (isTarget((WindowContainer) arrayList.get(size), z)) {
          return true;
        }
      }
      return arrayList.isEmpty();
    }

    public boolean isTarget(WindowContainer windowContainer, boolean z) {
      if (!this.mComposed) {
        return false;
      }
      WindowContainer windowContainer2 = (z ? this.mOpenAdaptor : this.mCloseAdaptor).mTarget;
      int i = this.mSwitchType;
      if (i == 1) {
        return windowContainer == windowContainer2
            || (windowContainer.asTask() != null && windowContainer.hasChild(windowContainer2))
            || (windowContainer.asActivityRecord() != null
                && windowContainer2.hasChild(windowContainer));
      }
      if (i == 2) {
        return windowContainer == windowContainer2
            || (windowContainer.asTaskFragment() != null
                && windowContainer.hasChild(windowContainer2));
      }
      return false;
    }

    public void finishPresentAnimations() {
      if (this.mComposed) {
        cleanUpWindowlessSurface();
        BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mCloseAdaptor;
        if (backWindowAnimationAdaptor != null) {
          backWindowAnimationAdaptor.mTarget.cancelAnimation();
          this.mCloseAdaptor = null;
        }
        BackWindowAnimationAdaptor backWindowAnimationAdaptor2 = this.mOpenAdaptor;
        if (backWindowAnimationAdaptor2 != null) {
          backWindowAnimationAdaptor2.mTarget.cancelAnimation();
          this.mOpenAdaptor = null;
        }
        ActivityRecord activityRecord = this.mOpenActivity;
        if (activityRecord == null || !activityRecord.mLaunchTaskBehind) {
          return;
        }
        BackNavigationController.restoreLaunchBehind(activityRecord);
      }
    }

    public final void cleanUpWindowlessSurface() {
      SurfaceControl.Transaction pendingTransaction;
      ActivityRecord topOpenActivity = getTopOpenActivity();
      if (topOpenActivity == null) {
        Slog.w("CoreBackPreview", "finishPresentAnimations without top activity: " + this);
      }
      if (topOpenActivity != null) {
        pendingTransaction = topOpenActivity.getPendingTransaction();
      } else {
        pendingTransaction = this.mOpenAdaptor.mTarget.getPendingTransaction();
      }
      boolean z = this.mOpenTransitionTargetMatch & (topOpenActivity != null);
      this.mOpenTransitionTargetMatch = z;
      if (z) {
        pendingTransaction.show(topOpenActivity.getSurfaceControl());
      }
      if (this.mRequestedStartingSurfaceTaskId != 0) {
        SurfaceControl surfaceControl = this.mStartingSurface;
        if (surfaceControl != null && this.mOpenTransitionTargetMatch) {
          pendingTransaction.reparent(surfaceControl, topOpenActivity.getSurfaceControl());
        }
        this.mStartingSurface = null;
        this.mRequestedStartingSurfaceTaskId = 0;
      }
    }

    public void clearBackAnimateTarget() {
      finishPresentAnimations();
      this.mComposed = false;
      this.mWaitTransition = false;
      this.mOpenTransitionTargetMatch = false;
      this.mRequestedStartingSurfaceTaskId = 0;
      this.mSwitchType = 0;
      this.mOpenActivity = null;
    }

    public boolean containsBackAnimationTargets(ArrayList arrayList, ArrayList arrayList2) {
      if (containTarget(arrayList2, false)) {
        return containTarget(arrayList, true) || containTarget(arrayList, false);
      }
      return false;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("AnimationTargets{ openTarget= ");
      BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mOpenAdaptor;
      sb.append(backWindowAnimationAdaptor != null ? backWindowAnimationAdaptor.mTarget : "null");
      sb.append(" closeTarget= ");
      BackWindowAnimationAdaptor backWindowAnimationAdaptor2 = this.mCloseAdaptor;
      sb.append(backWindowAnimationAdaptor2 != null ? backWindowAnimationAdaptor2.mTarget : "null");
      sb.append(" mSwitchType= ");
      sb.append(this.mSwitchType);
      sb.append(" mComposed= ");
      sb.append(this.mComposed);
      sb.append(" mWaitTransition= ");
      sb.append(this.mWaitTransition);
      sb.append('}');
      return sb.toString();
    }

    public static BackWindowAnimationAdaptor createAdaptor(
        WindowContainer windowContainer, boolean z) {
      TaskFragment taskFragment;
      BackWindowAnimationAdaptor backWindowAnimationAdaptor =
          new BackWindowAnimationAdaptor(windowContainer, z);
      SurfaceControl.Transaction pendingTransaction = windowContainer.getPendingTransaction();
      windowContainer.startAnimation(pendingTransaction, backWindowAnimationAdaptor, false, 256);
      if (z
          && windowContainer.asActivityRecord() != null
          && (taskFragment = windowContainer.asActivityRecord().getTaskFragment()) != null) {
        pendingTransaction.show(taskFragment.mSurfaceControl);
      }
      return backWindowAnimationAdaptor;
    }

    public class BackWindowAnimationAdaptor implements AnimationAdapter {
      public RemoteAnimationTarget mAnimationTarget;
      public final Rect mBounds;
      public SurfaceControl mCapturedLeash;
      public final boolean mIsOpen;
      public final WindowContainer mTarget;

      @Override // com.android.server.wm.AnimationAdapter
      public void dumpDebug(ProtoOutputStream protoOutputStream) {}

      @Override // com.android.server.wm.AnimationAdapter
      public long getDurationHint() {
        return 0L;
      }

      @Override // com.android.server.wm.AnimationAdapter
      public boolean getShowWallpaper() {
        return false;
      }

      @Override // com.android.server.wm.AnimationAdapter
      public long getStatusBarTransitionsStartTime() {
        return 0L;
      }

      public BackWindowAnimationAdaptor(WindowContainer windowContainer, boolean z) {
        Rect rect = new Rect();
        this.mBounds = rect;
        rect.set(windowContainer.getBounds());
        this.mTarget = windowContainer;
        this.mIsOpen = z;
      }

      @Override // com.android.server.wm.AnimationAdapter
      public void startAnimation(
          SurfaceControl surfaceControl,
          SurfaceControl.Transaction transaction,
          int i,
          SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        this.mCapturedLeash = surfaceControl;
        createRemoteAnimationTarget(this.mIsOpen);
      }

      @Override // com.android.server.wm.AnimationAdapter
      public void onAnimationCancelled(SurfaceControl surfaceControl) {
        if (this.mCapturedLeash == surfaceControl) {
          this.mCapturedLeash = null;
        }
      }

      @Override // com.android.server.wm.AnimationAdapter
      public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str + "BackWindowAnimationAdaptor mCapturedLeash=");
        printWriter.print(this.mCapturedLeash);
        printWriter.println();
      }

      public RemoteAnimationTarget createRemoteAnimationTarget(boolean z) {
        ActivityRecord asActivityRecord;
        Rect rect;
        RemoteAnimationTarget remoteAnimationTarget = this.mAnimationTarget;
        if (remoteAnimationTarget != null) {
          return remoteAnimationTarget;
        }
        Task asTask = this.mTarget.asTask();
        if (asTask != null) {
          asActivityRecord = asTask.getTopNonFinishingActivity();
        } else {
          asActivityRecord = this.mTarget.asActivityRecord();
        }
        if (asTask == null && asActivityRecord != null) {
          asTask = asActivityRecord.getTask();
        }
        if (asTask == null || asActivityRecord == null) {
          Slog.e("CoreBackPreview", "createRemoteAnimationTarget fail " + this.mTarget);
          return null;
        }
        WindowState findMainWindow = asActivityRecord.findMainWindow();
        if (findMainWindow != null) {
          rect =
              findMainWindow
                  .getInsetsStateWithVisibilityOverride()
                  .calculateInsets(this.mBounds, WindowInsets.Type.systemBars(), false)
                  .toRect();
          InsetUtils.addInsets(rect, findMainWindow.mActivityRecord.getLetterboxInsets());
        } else {
          rect = new Rect();
        }
        Rect rect2 = rect;
        int i = !z ? 1 : 0;
        int i2 = asTask.mTaskId;
        SurfaceControl surfaceControl = this.mCapturedLeash;
        boolean z2 = !asActivityRecord.fillsParent();
        Rect rect3 = new Rect();
        int prefixOrderIndex = asActivityRecord.getPrefixOrderIndex();
        Rect rect4 = this.mBounds;
        Point point = new Point(rect4.left, rect4.top);
        Rect rect5 = this.mBounds;
        RemoteAnimationTarget remoteAnimationTarget2 =
            new RemoteAnimationTarget(
                i2,
                i,
                surfaceControl,
                z2,
                rect3,
                rect2,
                prefixOrderIndex,
                point,
                rect5,
                rect5,
                asTask.getWindowConfiguration(),
                true,
                (SurfaceControl) null,
                (Rect) null,
                asTask.getTaskInfo(),
                asActivityRecord.checkEnterPictureInPictureAppOpsState());
        this.mAnimationTarget = remoteAnimationTarget2;
        return remoteAnimationTarget2;
      }
    }

    public ScheduleAnimationBuilder prepareAnimation(
        int i,
        BackAnimationAdapter backAnimationAdapter,
        Task task,
        Task task2,
        ActivityRecord activityRecord,
        ActivityRecord activityRecord2) {
      if (i == 1) {
        return new ScheduleAnimationBuilder(i, backAnimationAdapter)
            .setIsLaunchBehind(true)
            .setComposeTarget(task, task2);
      }
      if (i == 2) {
        return new ScheduleAnimationBuilder(i, backAnimationAdapter)
            .setComposeTarget(activityRecord, activityRecord2)
            .setIsLaunchBehind(false);
      }
      if (i != 3) {
        return null;
      }
      return new ScheduleAnimationBuilder(i, backAnimationAdapter)
          .setComposeTarget(task, task2)
          .setIsLaunchBehind(false);
    }

    public class ScheduleAnimationBuilder {
      public final BackAnimationAdapter mBackAnimationAdapter;
      public WindowContainer mCloseTarget;
      public boolean mIsLaunchBehind;
      public WindowContainer mOpenTarget;
      public final int mType;

      public ScheduleAnimationBuilder(int i, BackAnimationAdapter backAnimationAdapter) {
        this.mType = i;
        this.mBackAnimationAdapter = backAnimationAdapter;
      }

      public ScheduleAnimationBuilder setComposeTarget(
          WindowContainer windowContainer, WindowContainer windowContainer2) {
        this.mCloseTarget = windowContainer;
        this.mOpenTarget = windowContainer2;
        return this;
      }

      public ScheduleAnimationBuilder setIsLaunchBehind(boolean z) {
        this.mIsLaunchBehind = z;
        return this;
      }

      public boolean containTarget(WindowContainer windowContainer) {
        WindowContainer windowContainer2 = this.mOpenTarget;
        return windowContainer == windowContainer2
            || windowContainer == this.mCloseTarget
            || windowContainer2.hasChild(windowContainer)
            || this.mCloseTarget.hasChild(windowContainer);
      }

      public final void applyPreviewStrategy(
          WindowContainer windowContainer, ActivityRecord activityRecord) {
        if (AnimationHandler.this.isSupportWindowlessSurface()
            && AnimationHandler.this.mShowWindowlessSurface
            && !this.mIsLaunchBehind) {
          AnimationHandler.this.createStartingSurface(
              BackNavigationController.getSnapshot(windowContainer));
        } else {
          BackNavigationController.setLaunchBehind(activityRecord);
        }
      }

      public Runnable build() {
        ActivityRecord asActivityRecord;
        WindowContainer windowContainer = this.mOpenTarget;
        if (windowContainer == null || this.mCloseTarget == null) {
          return null;
        }
        if (windowContainer.asTask() != null) {
          asActivityRecord = this.mOpenTarget.asTask().getTopNonFinishingActivity();
        } else {
          asActivityRecord =
              this.mOpenTarget.asActivityRecord() != null
                  ? this.mOpenTarget.asActivityRecord()
                  : null;
        }
        if (asActivityRecord == null) {
          Slog.e("CoreBackPreview", "No opening activity");
          return null;
        }
        if (!AnimationHandler.this.composeAnimations(
            this.mCloseTarget, this.mOpenTarget, asActivityRecord)) {
          return null;
        }
        applyPreviewStrategy(this.mOpenTarget, asActivityRecord);
        final IBackAnimationFinishedCallback makeAnimationFinishedCallback =
            makeAnimationFinishedCallback();
        final RemoteAnimationTarget[] animationTargets =
            AnimationHandler.this.getAnimationTargets();
        return new Runnable() { // from class:
                                // com.android.server.wm.BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            BackNavigationController.AnimationHandler.ScheduleAnimationBuilder.this.lambda$build$0(
                animationTargets, makeAnimationFinishedCallback);
          }
        };
      }

      /* JADX INFO: Access modifiers changed from: private */
      public /* synthetic */ void lambda$build$0(
          RemoteAnimationTarget[] remoteAnimationTargetArr,
          IBackAnimationFinishedCallback iBackAnimationFinishedCallback) {
        try {
          this.mBackAnimationAdapter
              .getRunner()
              .onAnimationStart(
                  remoteAnimationTargetArr,
                  (RemoteAnimationTarget[]) null,
                  (RemoteAnimationTarget[]) null,
                  iBackAnimationFinishedCallback);
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }

      public final IBackAnimationFinishedCallback makeAnimationFinishedCallback() {
        return new IBackAnimationFinishedCallback
            .Stub() { // from class:
                      // com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder.1
          public void onAnimationFinished(boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                AnimationHandler.this.mWindowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                if (!AnimationHandler.this.mComposed) {
                  WindowManagerService.resetPriorityAfterLockedSection();
                  return;
                }
                if (!z) {
                  AnimationHandler.this.clearBackAnimateTarget();
                } else {
                  AnimationHandler.this.mWaitTransition = true;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
          }
        };
      }
    }
  }

  public static void setLaunchBehind(ActivityRecord activityRecord) {
    if (!activityRecord.isVisibleRequested()) {
      activityRecord.setVisibility(true);
      activityRecord.commitVisibility(true, true);
    }
    activityRecord.mLaunchTaskBehind = true;
    DisplayContent displayContent = activityRecord.mDisplayContent;
    displayContent.rotateInDifferentOrientationIfNeeded(activityRecord);
    if (activityRecord.hasFixedRotationTransform()) {
      displayContent.setFixedRotationLaunchingApp(
          activityRecord, activityRecord.getWindowConfiguration().getRotation());
    }
    if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
          948208142,
          0,
          "Setting Activity.mLauncherTaskBehind to true. Activity=%s",
          new Object[] {String.valueOf(activityRecord)});
    }
    activityRecord.mTaskSupervisor.mStoppingActivities.remove(activityRecord);
    activityRecord.getDisplayContent().ensureActivitiesVisible(null, 0, false, true);
  }

  public static void restoreLaunchBehind(ActivityRecord activityRecord) {
    activityRecord.mDisplayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
    activityRecord.mTaskSupervisor.scheduleLaunchTaskBehindComplete(activityRecord.token);
    activityRecord.mLaunchTaskBehind = false;
    if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
          -711194343,
          0,
          "Setting Activity.mLauncherTaskBehind to false. Activity=%s",
          new Object[] {String.valueOf(activityRecord)});
    }
  }

  public void checkAnimationReady(WallpaperController wallpaperController) {
    if (this.mBackAnimationInProgress) {
      if (!(!this.mShowWallpaper
              || (wallpaperController.getWallpaperTarget() != null
                  && wallpaperController.wallpaperTransitionReady()))
          || this.mPendingAnimation == null) {
        return;
      }
      startAnimation();
    }
  }

  public void startAnimation() {
    if (!this.mBackAnimationInProgress) {
      if (this.mPendingAnimation != null) {
        clearBackAnimations();
        this.mPendingAnimation = null;
        return;
      }
      return;
    }
    Runnable runnable = this.mPendingAnimation;
    if (runnable != null) {
      runnable.run();
      this.mPendingAnimation = null;
    }
  }

  /* renamed from: onBackNavigationDone, reason: merged with bridge method [inline-methods] */
  public final void lambda$startBackNavigation$1(Bundle bundle, int i) {
    boolean z = bundle != null && bundle.getBoolean("TriggerBack");
    if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
          -1033630971,
          12,
          "onBackNavigationDone backType=%s, triggerBack=%b",
          new Object[] {String.valueOf(i), Boolean.valueOf(z)});
    }
    this.mNavigationMonitor.stopMonitorForRemote();
    this.mBackAnimationInProgress = false;
    this.mShowWallpaper = false;
    this.mPendingAnimationBuilder = null;
  }

  public static TaskSnapshot getSnapshot(WindowContainer windowContainer) {
    if (!isScreenshotEnabled()) {
      return null;
    }
    if (windowContainer.asTask() != null) {
      Task asTask = windowContainer.asTask();
      return asTask.mRootWindowContainer.mWindowManager.mTaskSnapshotController.getSnapshot(
          asTask.mTaskId, asTask.mUserId, false, false);
    }
    windowContainer.asActivityRecord();
    return null;
  }

  public void setWindowManager(WindowManagerService windowManagerService) {
    this.mWindowManagerService = windowManagerService;
    this.mAnimationHandler = new AnimationHandler(windowManagerService);
  }

  public boolean isWallpaperVisible(WindowState windowState) {
    ActivityRecord activityRecord;
    return this.mAnimationHandler.mComposed
        && this.mShowWallpaper
        && windowState.mAttrs.type == 1
        && (activityRecord = windowState.mActivityRecord) != null
        && this.mAnimationHandler.isTarget(activityRecord, true);
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1133871366145L, this.mBackAnimationInProgress);
    protoOutputStream.write(1120986464258L, this.mLastBackType);
    protoOutputStream.write(1133871366147L, this.mShowWallpaper);
    protoOutputStream.end(start);
  }
}
