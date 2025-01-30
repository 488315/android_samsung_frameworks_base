package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.RotationUtils;
import android.util.Size;
import android.util.Slog;
import android.window.WindowContainerToken;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.cover.CoverState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class MultiTaskingTaskLaunchParamsModifier
    implements LaunchParamsController.LaunchParamsModifier {
  public int mDefaultFreeformStepHorizontal;
  public int mDefaultFreeformStepVertical;
  public StringBuilder mLogBuilder;
  public final ActivityTaskSupervisor mSupervisor;
  public TaskDisplayArea mTmpDisplayArea;
  public static final PointF DEX_DEFAULT_SIZE_RATIO = new PointF(0.42f, 0.56f);
  public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE = new PointF(0.55f, 0.66f);
  public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX = new PointF(0.541f, 0.65f);
  public final Rect mTmpBounds = new Rect();
  public final Rect mTmpStableBounds = new Rect();
  public final int[] mTmpDirections = new int[2];
  public final Rect mTmpBounds2 = new Rect();
  public ActivityInfo.WindowLayout mTmpLayout = null;
  public final Rect mSnappingBounds = new Rect();

  public final int convertOrientationToScreenOrientation(int i) {
    if (i != 1) {
      return i != 2 ? -1 : 0;
    }
    return 1;
  }

  public MultiTaskingTaskLaunchParamsModifier(ActivityTaskSupervisor activityTaskSupervisor) {
    this.mSupervisor = activityTaskSupervisor;
  }

  @Override // com.android.server.wm.LaunchParamsController.LaunchParamsModifier
  public int onCalculate(
      Task task,
      ActivityInfo.WindowLayout windowLayout,
      ActivityRecord activityRecord,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions,
      ActivityStarter.Request request,
      int i,
      LaunchParamsController.LaunchParams launchParams,
      LaunchParamsController.LaunchParams launchParams2) {
    initLogBuilder(task, activityRecord);
    int calculate =
        calculate(
            task,
            windowLayout,
            activityRecord,
            activityRecord2,
            activityOptions,
            request,
            i,
            launchParams,
            launchParams2);
    outputLog();
    return calculate;
  }

  /* JADX WARN: Removed duplicated region for block: B:146:0x065e  */
  /* JADX WARN: Removed duplicated region for block: B:156:0x06be  */
  /* JADX WARN: Removed duplicated region for block: B:158:0x06ce  */
  /* JADX WARN: Removed duplicated region for block: B:177:0x0787 A[RETURN] */
  /* JADX WARN: Removed duplicated region for block: B:178:0x0789  */
  /* JADX WARN: Removed duplicated region for block: B:247:0x080f  */
  /* JADX WARN: Removed duplicated region for block: B:249:0x0844  */
  /* JADX WARN: Removed duplicated region for block: B:258:0x06c1  */
  /* JADX WARN: Removed duplicated region for block: B:265:0x069f  */
  /* JADX WARN: Removed duplicated region for block: B:269:0x0586  */
  /* JADX WARN: Removed duplicated region for block: B:301:0x0650  */
  /* JADX WARN: Removed duplicated region for block: B:315:0x02aa  */
  /* JADX WARN: Removed duplicated region for block: B:330:0x03d4  */
  /* JADX WARN: Removed duplicated region for block: B:334:0x03d6  */
  /* JADX WARN: Removed duplicated region for block: B:83:0x0211  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int calculate(
      Task task,
      ActivityInfo.WindowLayout windowLayout,
      ActivityRecord activityRecord,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions,
      ActivityStarter.Request request,
      int i,
      LaunchParamsController.LaunchParams launchParams,
      LaunchParamsController.LaunchParams launchParams2) {
    ActivityRecord activityRecord3;
    Resources resources;
    int i2;
    boolean z;
    DisplayContent displayContent;
    LaunchParamsController.LaunchParams launchParams3;
    ActivityRecord activityRecord4;
    char c;
    int i3;
    boolean z2;
    int i4;
    boolean z3;
    int i5;
    boolean z4;
    int i6;
    int i7;
    int i8;
    ActivityRecord activityRecord5;
    int i9;
    LaunchParamsController.LaunchParams launchParams4;
    TaskDisplayArea taskDisplayArea;
    boolean z5;
    int i10;
    TaskDisplayArea taskDisplayArea2;
    ActivityRecord activityRecord6;
    boolean z6;
    int i11;
    int i12;
    ActivityInfo.WindowLayout windowLayout2;
    int i13;
    int i14;
    int i15;
    ActivityRecord activityRecord7;
    int i16;
    TaskDisplayArea taskDisplayArea3;
    TaskDisplayArea taskDisplayArea4;
    boolean z7;
    int i17;
    Task rootTask;
    TaskDisplayArea taskDisplayArea5;
    int i18;
    boolean z8;
    boolean z9;
    int i19;
    Intent intent;
    int launchOverTargetTaskId;
    Task anyTaskForId;
    int i20 = i;
    if (task != null) {
      activityRecord3 = task.getRootActivity() == null ? activityRecord : task.getRootActivity();
    } else {
      activityRecord3 = activityRecord;
    }
    if (activityRecord3 == null) {
      return 0;
    }
    ActivityRecord activityRecord8 = activityRecord3;
    TaskDisplayArea preferredLaunchTaskDisplayArea =
        getPreferredLaunchTaskDisplayArea(
            task, activityOptions, activityRecord2, launchParams, activityRecord, request);
    launchParams2.mPreferredTaskDisplayArea = preferredLaunchTaskDisplayArea;
    DisplayContent displayContent2 = preferredLaunchTaskDisplayArea.mDisplayContent;
    appendLog(
        "display-id="
            + displayContent2.getDisplayId()
            + " task-display-area-windowing-mode="
            + preferredLaunchTaskDisplayArea.getWindowingMode()
            + " suggested-display-area="
            + preferredLaunchTaskDisplayArea);
    if (activityRecord != null
        && (intent = activityRecord.intent) != null
        && (launchOverTargetTaskId = intent.getLaunchOverTargetTaskId()) != -1) {
      if (launchOverTargetTaskId == -10000
          && preferredLaunchTaskDisplayArea.getFocusedRootTask() != null) {
        anyTaskForId = preferredLaunchTaskDisplayArea.getFocusedRootTask().getTopMostTask();
      } else {
        anyTaskForId =
            this.mSupervisor.mRootWindowContainer.anyTaskForId(launchOverTargetTaskId, 0);
      }
      TaskDisplayArea displayArea = anyTaskForId != null ? anyTaskForId.getDisplayArea() : null;
      if (displayArea != null
          && (displayArea.getDisplayId() != 2
              || this.mSupervisor.mService.mDexController.getDexModeLocked() == 2)) {
        launchParams2.mPreferredTaskDisplayArea = displayArea;
        appendLog(
            "tda-from-lott="
                + displayArea
                + " dc="
                + displayArea.getDisplayContent()
                + " display-windowing-mode="
                + displayArea.getWindowingMode());
      }
    }
    if (i20 == 0) {
      return 2;
    }
    if (displayContent2.isDexMode() && displayContent2.getDisplayId() == 2) {
      resources = this.mSupervisor.mService.mDexController.getDisplayContext(2).getResources();
    } else {
      resources = this.mSupervisor.mService.mContext.getResources();
    }
    if (displayContent2.isDesktopModeEnabled()) {
      this.mDefaultFreeformStepHorizontal =
          Math.round(resources.getDimension(R.dimen.dialog_fixed_height_minor));
      this.mDefaultFreeformStepVertical =
          Math.round(resources.getDimension(R.dimen.dialog_fixed_height_major));
    } else {
      this.mDefaultFreeformStepHorizontal =
          Math.round(resources.getDimension(R.dimen.timepicker_ampm_label_size));
      this.mDefaultFreeformStepVertical =
          Math.round(resources.getDimension(R.dimen.timepicker_ampm_horizontal_padding));
    }
    int launchWindowingMode =
        activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
    if (displayContent2.isRemoteAppDisplay() || displayContent2.isAppCastingDisplay()) {
      launchWindowingMode = 1;
    }
    if (launchWindowingMode == 0
        && canInheritWindowingModeFromSource(
            displayContent2, preferredLaunchTaskDisplayArea, activityRecord2, task)) {
      if (activityRecord2.getTask().inPinnedWindowingMode()
          && !activityRecord2.inPinnedWindowingMode()) {
        launchWindowingMode = activityRecord2.getWindowingMode();
      } else {
        launchWindowingMode = activityRecord2.getTask().getWindowingMode();
      }
      appendLog(
          "inherit-from-source=" + WindowConfiguration.windowingModeToString(launchWindowingMode));
    }
    if (launchWindowingMode == 0
        && task != null
        && task.getTaskDisplayArea() == preferredLaunchTaskDisplayArea
        && !task.getRootTask().mReparentLeafTaskIfRelaunch
        && (!task.inFreeformWindowingMode()
            || preferredLaunchTaskDisplayArea.getDisplayId() != 0)) {
      launchWindowingMode = task.getWindowingMode();
      appendLog(
          "inherit-from-task=" + WindowConfiguration.windowingModeToString(launchWindowingMode));
    }
    int i21 = launchWindowingMode;
    boolean canApplyDexPolicy = canApplyDexPolicy(displayContent2, i21);
    boolean canCalculateBoundsForFullscreenTask =
        canCalculateBoundsForFullscreenTask(preferredLaunchTaskDisplayArea, i21);
    boolean canApplyFreeformWindowPolicy =
        canApplyFreeformWindowPolicy(
            preferredLaunchTaskDisplayArea, i21, activityRecord8, activityOptions, activityRecord2);
    boolean z10 =
        windowLayout != null
            && (canApplyFreeformWindowPolicy || canCalculateBoundsForFullscreenTask);
    if (this.mSupervisor.canUseActivityOptionsLaunchBounds(activityOptions)) {
      i2 = i21;
      if (canApplyFreeformWindowPolicy
          || canApplyPipWindowPolicy(i2)
          || canCalculateBoundsForFullscreenTask) {
        z = true;
        if (z) {
          displayContent = displayContent2;
          if (canApplyDexPolicy) {
            this.mTmpBounds.setEmpty();
            if (canApplyFreeformWindowPolicy) {
              int dexModeLocked = this.mSupervisor.mService.mDexController.getDexModeLocked();
              launchParams3 = launchParams;
              i7 = 0;
              if (canApplyDexPersistentBounds(displayContent, launchParams3)
                  && getPersistentDexBounds(
                      dexModeLocked,
                      displayContent.getDisplayId(),
                      launchParams3,
                      this.mTmpBounds)) {
                if (!this.mTmpBounds.isEmpty()) {
                  adjustBoundsToFitInDisplayArea(
                      preferredLaunchTaskDisplayArea, this.mTmpLayout, this.mTmpBounds);
                  i6 = 1;
                } else {
                  i6 = 1;
                  launchParams2.mDexPersistBoundsParam.mDexWindowingMode = 1;
                }
                appendLog("dex-persistent-bounds=" + this.mTmpBounds);
              } else {
                i6 = 1;
                if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS
                    && canApplyNewDexPersistentBounds(displayContent, launchParams3)
                    && getPersistentNewDexBounds(dexModeLocked, launchParams3, this.mTmpBounds)) {
                  if (!this.mTmpBounds.isEmpty()) {
                    adjustBoundsToFitInDisplayArea(
                        preferredLaunchTaskDisplayArea, this.mTmpLayout, this.mTmpBounds);
                  } else {
                    launchParams2.mNewDexPersistBoundsParam.mNewDexWindowingMode = 1;
                  }
                  appendLog("new-dex-persistent-bounds=" + this.mTmpBounds);
                } else {
                  if (task == null || !task.mTakeInitBounds) {
                    activityRecord4 = activityRecord8;
                    if (getDexMetaDataBounds(
                        preferredLaunchTaskDisplayArea, task, activityRecord4, this.mTmpBounds)) {
                      appendLog("dex-metadata-bounds");
                    }
                  } else {
                    activityRecord4 = activityRecord8;
                  }
                  if (windowLayout != null && windowLayout.hasSpecifiedSize()) {
                    getLayoutBounds(
                        preferredLaunchTaskDisplayArea,
                        activityRecord4,
                        recalculateLayout(
                            preferredLaunchTaskDisplayArea, windowLayout, activityRecord),
                        this.mTmpBounds);
                    appendLog("dex-layout-bounds=" + this.mTmpBounds);
                    if (this.mTmpBounds.isEmpty()) {
                      getInitialDexBounds(
                          preferredLaunchTaskDisplayArea, activityRecord4, this.mTmpBounds);
                      appendLog("dex-initial-bounds" + this.mTmpBounds);
                    }
                  } else {
                    getInitialDexBounds(
                        preferredLaunchTaskDisplayArea, activityRecord4, this.mTmpBounds);
                    appendLog("dex-initial_bounds" + this.mTmpBounds);
                  }
                }
              }
              i8 = i6;
              activityRecord4 = activityRecord8;
              int i22 = !this.mTmpBounds.isEmpty() ? i6 : 5;
              launchParams2.mBounds.set(this.mTmpBounds);
              i3 = i22;
              i4 = i8;
              c = 5;
              z2 = true;
            } else {
              launchParams3 = launchParams;
              activityRecord4 = activityRecord8;
              i6 = 1;
              i7 = 0;
            }
            i8 = i7;
            if (!this.mTmpBounds.isEmpty()) {}
            launchParams2.mBounds.set(this.mTmpBounds);
            i3 = i22;
            i4 = i8;
            c = 5;
            z2 = true;
          } else {
            launchParams3 = launchParams;
            activityRecord4 = activityRecord8;
            if (z10) {
              this.mTmpBounds.set(launchParams3.mBounds);
              getLayoutBounds(
                  preferredLaunchTaskDisplayArea,
                  activityRecord4,
                  recalculateLayout(preferredLaunchTaskDisplayArea, windowLayout, activityRecord),
                  this.mTmpBounds);
              if (!this.mTmpBounds.isEmpty()) {
                int i23 = canApplyFreeformWindowPolicy ? 5 : i2;
                launchParams2.mBounds.set(this.mTmpBounds);
                appendLog("bounds-from-layout=" + launchParams2.mBounds);
                i3 = i23;
                c = 5;
                z2 = true;
                i4 = 0;
                z3 = true;
                i5 = 1;
                z4 = false;
              } else {
                appendLog("empty-window-layout");
                c = 5;
              }
            } else if (i2 == 6
                && activityOptions != null
                && activityOptions.getLaunchBounds() != null) {
              launchParams2.mBounds.set(activityOptions.getLaunchBounds());
              appendLog("multiwindow-activity-options-bounds=" + launchParams2.mBounds);
              i3 = i2;
              c = 5;
              z2 = true;
              i4 = 0;
              z3 = false;
              i5 = 1;
              z4 = false;
            } else if (canApplyFreeformWindowPolicy
                && canApplyFreeformPersistentBounds(displayContent, launchParams3)) {
              adjustPersistFreeformBounds(
                  task, displayContent, launchParams3, launchParams2.mBounds);
              appendLog("freeform-persist-bounds=" + launchParams2.mBounds);
              i3 = i2;
              c = 5;
              z2 = true;
              i4 = 0;
            } else {
              c = 5;
              if (canApplyFreeformWindowPolicy && i2 == 5) {
                launchParams3.mWindowingMode = 5;
              }
            }
            i3 = i2;
            z2 = false;
            i4 = 0;
            z3 = false;
            i5 = 1;
            z4 = false;
          }
          z3 = false;
          i5 = 0;
          z4 = false;
        } else {
          i3 =
              (displayContent2.isDexMode()
                      && activityOptions.getLaunchBounds() != null
                      && activityOptions.getLaunchBounds().isEmpty())
                  ? 1
                  : (i2 == 2 && !(i2 == 0 && canApplyFreeformWindowPolicy)) ? i2 : 5;
          Rect launchBounds = activityOptions.getLaunchBounds();
          if (!displayContent2.isDexMode() || launchBounds == null || launchBounds.isEmpty()) {
            z9 = false;
            i19 = 1;
          } else {
            z9 = true;
            i19 = 0;
          }
          if (!canApplyFreeformWindowPolicy || launchBounds == null) {
            displayContent = displayContent2;
          } else {
            Rect rect = new Rect();
            displayContent = displayContent2;
            displayContent.getStableRect(rect);
            if (displayContent.isDexMode()) {
              rect.top = 0;
            }
            int i24 = launchBounds.top;
            int i25 = rect.top;
            if (i24 < i25) {
              launchBounds.offsetTo(launchBounds.left, i25 + displayContent.getFreeformTopInset());
            }
            if (task != null) {
              task.setLastNonFullscreenBounds(launchBounds);
            }
          }
          launchParams2.mBounds.set(activityOptions.getLaunchBounds());
          appendLog("activity-options-bounds=" + launchParams2.mBounds);
          launchParams3 = launchParams;
          z4 = z9;
          i5 = i19;
          activityRecord4 = activityRecord8;
          c = 5;
          z2 = true;
          i4 = 0;
          z3 = false;
        }
        if (task == null && task.isDesktopModeEnabled() && i3 == 1) {
          activityRecord5 = activityRecord4;
          i9 = i3;
          launchParams4 = launchParams3;
          taskDisplayArea = preferredLaunchTaskDisplayArea;
          updateSnappingBounds(
              preferredLaunchTaskDisplayArea,
              task,
              activityRecord5,
              launchParams,
              windowLayout,
              this.mSnappingBounds);
          task.setSnappingGuideBounds(this.mSnappingBounds);
        } else {
          activityRecord5 = activityRecord4;
          i9 = i3;
          launchParams4 = launchParams3;
          taskDisplayArea = preferredLaunchTaskDisplayArea;
        }
        if (launchParams.isEmpty()
            && !z2
            && ((taskDisplayArea5 = launchParams4.mPreferredTaskDisplayArea) == null
                || taskDisplayArea5.getDisplayId() == displayContent.getDisplayId())) {
          if (launchParams.hasWindowingMode() && taskDisplayArea.inFreeformWindowingMode()) {
            i18 = (canApplyFreeformWindowPolicy && i9 == 5) ? i9 : launchParams4.mWindowingMode;
            z8 = i18 != 5;
            appendLog("inherit-" + WindowConfiguration.windowingModeToString(i18));
          } else {
            launchParams4.mBounds.setEmpty();
            i18 = i9;
            z8 = false;
          }
          if (launchParams4.mBounds.isEmpty()
              || launchParams4.mOrientation != displayContent.getConfiguration().orientation) {
            i9 = i18;
            z5 = z8;
          } else {
            launchParams2.mBounds.set(launchParams4.mBounds);
            if (i18 == 5) {
              appendLog("inherit-bounds=" + launchParams2.mBounds);
            }
            i9 = i18;
            z5 = true;
          }
        } else {
          z5 = false;
        }
        if (displayContent.getConfiguration().dexMode == 1 || activityRecord5.isResizeable()) {
          if (taskDisplayArea.inFreeformWindowingMode()) {
            i10 = i9;
            taskDisplayArea2 = taskDisplayArea;
            activityRecord6 = activityRecord5;
            appendLog("non-freeform-task-display-area");
          } else if (i9 == 2) {
            appendLog("picture-in-picture");
          } else if (CoreRune.MT_DEX_SIZE_COMPAT_MODE
              && interceptCalculateIfDexSizeCompatMode(
                  activityRecord5,
                  taskDisplayArea,
                  launchParams2,
                  task,
                  activityRecord,
                  activityOptions)) {
            if (!launchParams2.mBounds.isEmpty()) {
              adjustBoundsToAvoidConflictInDisplayArea(
                  taskDisplayArea, launchParams2.mBounds, null, null, task);
            }
            if (CoreRune.SAFE_DEBUG) {
              StringBuilder sb = new StringBuilder();
              sb.append("dex-size-compat-mode-");
              sb.append(WindowConfiguration.windowingModeToString(i9));
              sb.append(activityRecord5.isResizeable() ? "-resizeable" : "");
              sb.append("-bounds=");
              sb.append(launchParams2.mBounds);
              appendLog(sb.toString());
            }
            if (i20 != 1) {
              i10 = i9;
              taskDisplayArea2 = taskDisplayArea;
              z6 = false;
              i20 = 2;
              activityRecord6 = activityRecord5;
              if (i10 == 0) {
                if (!taskDisplayArea2.isUnderHomeTask(rootTask)) {}
                if (displayContent.equals(rootTask.getDisplayContent())) {}
              }
              i11 = 5;
              i12 = i10;
              if (i12 != i11) {}
              if (task == null) {}
              windowLayout2 = windowLayout;
              i13 = 0;
              i14 = i12;
              if (i20 != 1) {}
            }
          } else if (!activityRecord5.isResizeable() && taskDisplayArea.getDisplayId() != 2) {
            ActivityRecord activityRecord9 = activityRecord5;
            TaskDisplayArea taskDisplayArea6 = taskDisplayArea;
            if (shouldLaunchUnresizableAppInFreeform(
                activityRecord9, taskDisplayArea6, activityOptions)) {
              if (launchParams2.mBounds.isEmpty()) {
                taskDisplayArea2 = taskDisplayArea6;
                activityRecord6 = activityRecord9;
                getTaskBounds(
                    activityRecord9, taskDisplayArea6, windowLayout, 5, z2, launchParams2.mBounds);
                z6 = true;
              } else {
                taskDisplayArea2 = taskDisplayArea6;
                activityRecord6 = activityRecord9;
                z6 = false;
              }
              appendLog("unresizable-freeform");
              i10 = 5;
              if (i10 == 0
                  && (rootTask = activityRecord6.getRootTask()) != null
                  && rootTask.inFreeformWindowingMode()) {
                if (!taskDisplayArea2.isUnderHomeTask(rootTask) && !rootTask.isMinimized()) {
                  i12 = displayContent.getWindowingMode();
                  appendLog(
                      "notMinimizedFreeform-to-display-windowing-mode="
                          + WindowConfiguration.windowingModeToString(i12));
                  i11 = 5;
                } else if (displayContent.equals(rootTask.getDisplayContent())) {
                  StringBuilder sb2 = new StringBuilder();
                  sb2.append("freeform-task");
                  i11 = 5;
                  sb2.append(WindowConfiguration.windowingModeToString(5));
                  appendLog(sb2.toString());
                  i12 = 5;
                }
                if (i12 != i11) {
                  launchParams2.mWindowingMode = i12;
                } else {
                  launchParams2.mWindowingMode =
                      i12 == taskDisplayArea2.getWindowingMode() ? 0 : i12;
                }
                if (task == null && task.isDexCompatEnabled() && displayContent.isDexMode()) {
                  Rect rect2 = this.mTmpBounds;
                  int i26 = rect2.left;
                  int i27 = rect2.top;
                  boolean isEmpty = rect2.isEmpty();
                  this.mSupervisor.mService.mDexCompatController.getCompatBounds(
                      task, this.mTmpBounds, activityRecord);
                  if (this.mTmpBounds.isEmpty()) {
                    windowLayout2 = windowLayout;
                    i13 = 0;
                  } else {
                    if (i4 != 0 && !isEmpty && !task.isDexCompatUiFullscreen()) {
                      this.mTmpBounds.offsetTo(i26, i27);
                    } else {
                      int width = this.mTmpBounds.width();
                      int height = this.mTmpBounds.height();
                      this.mTmpBounds.setEmpty();
                      centerBounds(taskDisplayArea2, width, height, this.mTmpBounds);
                      if (task.isDexCompatUiFullscreen()) {
                        Rect rect3 = this.mTmpBounds;
                        i17 = 0;
                        rect3.offset(0, -rect3.top);
                        windowLayout2 = windowLayout;
                        i13 = i17;
                        adjustBoundsToFitInDisplayArea(
                            taskDisplayArea2, windowLayout2, this.mTmpBounds);
                      }
                    }
                    i17 = 0;
                    windowLayout2 = windowLayout;
                    i13 = i17;
                    adjustBoundsToFitInDisplayArea(
                        taskDisplayArea2, windowLayout2, this.mTmpBounds);
                  }
                  int i28 = this.mTmpBounds.isEmpty() ? 1 : 5;
                  launchParams2.mWindowingMode = i28;
                  launchParams2.mBounds.set(this.mTmpBounds);
                  appendLog(
                      "dex-compat-windowing-mode="
                          + WindowConfiguration.windowingModeToString(i28));
                  appendLog("dex-compat-bounds=" + this.mTmpBounds);
                  i5 = i13;
                  i14 = i28;
                } else {
                  windowLayout2 = windowLayout;
                  i13 = 0;
                  i14 = i12;
                }
                if (i20 != 1) {
                  return 2;
                }
                final int windowingMode = i14 != 0 ? i14 : taskDisplayArea2.getWindowingMode();
                if (activityOptions == null
                    || (activityOptions.getLaunchTaskDisplayArea() == null
                        && activityOptions.getLaunchTaskDisplayAreaFeatureId() == -1)) {
                  ActivityRecord activityRecord10 = activityRecord6;
                  final int resolveActivityType =
                      this.mSupervisor.mRootWindowContainer.resolveActivityType(
                          activityRecord10, activityOptions, task);
                  displayContent.forAllTaskDisplayAreas(
                      new Predicate() { // from class:
                                        // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          boolean lambda$calculate$0;
                          lambda$calculate$0 =
                              MultiTaskingTaskLaunchParamsModifier.this.lambda$calculate$0(
                                  windowingMode, resolveActivityType, (TaskDisplayArea) obj);
                          return lambda$calculate$0;
                        }
                      });
                  TaskDisplayArea taskDisplayArea7 = this.mTmpDisplayArea;
                  if (taskDisplayArea7 != null && taskDisplayArea7 != taskDisplayArea2) {
                    if (i14 != taskDisplayArea7.getWindowingMode()) {
                      i13 = i14;
                    }
                    launchParams2.mWindowingMode = i13;
                    if (z3) {
                      launchParams2.mBounds.setEmpty();
                      getLayoutBounds(
                          this.mTmpDisplayArea,
                          activityRecord10,
                          windowLayout2,
                          launchParams2.mBounds);
                      z2 = !launchParams2.mBounds.isEmpty();
                    } else if (z6) {
                      launchParams2.mBounds.setEmpty();
                      i15 = resolveActivityType;
                      activityRecord7 = activityRecord10;
                      i16 = windowingMode;
                      getTaskBounds(
                          activityRecord10,
                          this.mTmpDisplayArea,
                          windowLayout,
                          i14,
                          z2,
                          launchParams2.mBounds);
                      taskDisplayArea3 = this.mTmpDisplayArea;
                      if (taskDisplayArea3 == null) {
                        this.mTmpDisplayArea = null;
                        appendLog(
                            "overridden-display-area=["
                                + WindowConfiguration.activityTypeToString(i15)
                                + ", "
                                + WindowConfiguration.windowingModeToString(i16)
                                + ", "
                                + taskDisplayArea3
                                + "]");
                        taskDisplayArea4 = taskDisplayArea3;
                      } else {
                        taskDisplayArea4 = taskDisplayArea2;
                      }
                      z7 = z2;
                    }
                  }
                  i15 = resolveActivityType;
                  activityRecord7 = activityRecord10;
                  i16 = windowingMode;
                  taskDisplayArea3 = this.mTmpDisplayArea;
                  if (taskDisplayArea3 == null) {}
                  z7 = z2;
                } else {
                  i16 = windowingMode;
                  taskDisplayArea4 = taskDisplayArea2;
                  z7 = z2;
                  activityRecord7 = activityRecord6;
                }
                appendLog("display-area=" + taskDisplayArea4);
                launchParams2.mPreferredTaskDisplayArea = taskDisplayArea4;
                if (i20 == 2) {
                  return 2;
                }
                if (z5) {
                  if (i16 != 5) {
                    return 2;
                  }
                  if (launchParams4.mPreferredTaskDisplayArea != taskDisplayArea4) {
                    adjustBoundsToFitInDisplayArea(
                        taskDisplayArea4, windowLayout2, launchParams2.mBounds);
                  }
                  adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea4, launchParams2.mBounds);
                  return 2;
                }
                int i29 = i16;
                if (taskDisplayArea4.getDisplayId() == 0
                    && activityOptions != null
                    && activityOptions.isApplyBigFreeformSize()
                    && launchParams2.mBounds.isEmpty()) {
                  Rect rect4 = this.mTmpStableBounds;
                  displayContent.getBounds(rect4);
                  if (activityRecord7.isResizeable()) {
                    rect4.scale(0.95f);
                  }
                  launchParams2.mBounds.set(rect4);
                  appendLog("big-freeform-bounds=" + launchParams2.mBounds);
                  return 2;
                }
                if (activityRecord2 != null
                    && activityRecord2.inFreeformWindowingMode()
                    && i29 == 5
                    && launchParams2.mBounds.isEmpty()
                    && activityRecord2.getDisplayArea() == taskDisplayArea4
                    && !activityRecord2.mIsAliasActivity) {
                  cascadeBounds(
                      activityRecord2.getConfiguration().windowConfiguration.getBounds(),
                      taskDisplayArea4,
                      launchParams2.mBounds);
                }
                if (i5 != 0) {
                  getTaskBounds(
                      activityRecord7,
                      taskDisplayArea4,
                      windowLayout,
                      i29,
                      z7,
                      launchParams2.mBounds,
                      activityRecord2);
                }
                if (!displayContent.isDesktopModeEnabled() || i5 != 0 || z4) {
                  return 2;
                }
                adjustBoundsToAvoidConflictInDisplayArea(
                    taskDisplayArea2, launchParams2.mBounds, null, null, task);
                return 2;
              }
              i11 = 5;
              i12 = i10;
              if (i12 != i11) {}
              if (task == null) {}
              windowLayout2 = windowLayout;
              i13 = 0;
              i14 = i12;
              if (i20 != 1) {}
            } else {
              taskDisplayArea2 = taskDisplayArea6;
              activityRecord6 = activityRecord9;
              launchParams2.mBounds.setEmpty();
              appendLog("unresizable-forced-maximize");
              i10 = 1;
            }
          }
          z6 = false;
          if (i10 == 0) {}
          i11 = 5;
          i12 = i10;
          if (i12 != i11) {}
          if (task == null) {}
          windowLayout2 = windowLayout;
          i13 = 0;
          i14 = i12;
          if (i20 != 1) {}
        }
        i10 = i9;
        taskDisplayArea2 = taskDisplayArea;
        activityRecord6 = activityRecord5;
        z6 = false;
        if (i10 == 0) {}
        i11 = 5;
        i12 = i10;
        if (i12 != i11) {}
        if (task == null) {}
        windowLayout2 = windowLayout;
        i13 = 0;
        i14 = i12;
        if (i20 != 1) {}
      }
    } else {
      i2 = i21;
    }
    z = false;
    if (z) {}
    if (task == null) {}
    activityRecord5 = activityRecord4;
    i9 = i3;
    launchParams4 = launchParams3;
    taskDisplayArea = preferredLaunchTaskDisplayArea;
    if (launchParams.isEmpty()) {}
    z5 = false;
    if (displayContent.getConfiguration().dexMode == 1) {}
    if (taskDisplayArea.inFreeformWindowingMode()) {}
    z6 = false;
    if (i10 == 0) {}
    i11 = 5;
    i12 = i10;
    if (i12 != i11) {}
    if (task == null) {}
    windowLayout2 = windowLayout;
    i13 = 0;
    i14 = i12;
    if (i20 != 1) {}
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$calculate$0(
      int i, int i2, TaskDisplayArea taskDisplayArea) {
    if (taskDisplayArea.getLaunchRootTask(i, i2, null, null, 0) == null) {
      return false;
    }
    this.mTmpDisplayArea = taskDisplayArea;
    return true;
  }

  public final TaskDisplayArea getPreferredLaunchTaskDisplayArea(
      Task task,
      ActivityOptions activityOptions,
      ActivityRecord activityRecord,
      LaunchParamsController.LaunchParams launchParams,
      ActivityRecord activityRecord2,
      ActivityStarter.Request request) {
    TaskDisplayArea taskDisplayArea;
    ActivityRecord activityRecord3;
    int callerDisplayId;
    DisplayContent displayContent;
    DisplayContent displayContent2;
    final int launchTaskDisplayAreaFeatureId;
    Task task2 = null;
    WindowContainerToken launchTaskDisplayArea =
        activityOptions != null ? activityOptions.getLaunchTaskDisplayArea() : null;
    if (launchTaskDisplayArea != null) {
      taskDisplayArea =
          (TaskDisplayArea) WindowContainer.fromBinder(launchTaskDisplayArea.asBinder());
      appendLog("display-area-token-from-option=" + taskDisplayArea);
    } else {
      taskDisplayArea = null;
    }
    if (taskDisplayArea == null
        && activityOptions != null
        && (launchTaskDisplayAreaFeatureId = activityOptions.getLaunchTaskDisplayAreaFeatureId())
            != -1) {
      DisplayContent displayContent3 =
          this.mSupervisor.mRootWindowContainer.getDisplayContent(
              activityOptions.getLaunchDisplayId() == -1
                  ? 0
                  : activityOptions.getLaunchDisplayId());
      if (displayContent3 != null) {
        taskDisplayArea =
            (TaskDisplayArea)
                displayContent3.getItemFromTaskDisplayAreas(
                    new Function() { // from class:
                                     // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda2
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        TaskDisplayArea lambda$getPreferredLaunchTaskDisplayArea$1;
                        lambda$getPreferredLaunchTaskDisplayArea$1 =
                            MultiTaskingTaskLaunchParamsModifier
                                .lambda$getPreferredLaunchTaskDisplayArea$1(
                                    launchTaskDisplayAreaFeatureId, (TaskDisplayArea) obj);
                        return lambda$getPreferredLaunchTaskDisplayArea$1;
                      }
                    });
        appendLog("display-area-feature-from-option=" + taskDisplayArea);
      }
    }
    if (taskDisplayArea == null) {
      int launchDisplayId = activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
      if (launchDisplayId != -1
          && (displayContent2 =
                  this.mSupervisor.mRootWindowContainer.getDisplayContent(launchDisplayId))
              != null) {
        taskDisplayArea = displayContent2.getDefaultTaskDisplayArea();
        appendLog("display-from-option=" + launchDisplayId);
      }
    }
    if (taskDisplayArea == null && activityRecord != null && activityRecord.noDisplay) {
      taskDisplayArea = activityRecord.mHandoverTaskDisplayArea;
      if (taskDisplayArea != null) {
        appendLog("display-area-from-no-display-source=" + taskDisplayArea);
      } else {
        int i = activityRecord.mHandoverLaunchDisplayId;
        DisplayContent displayContent4 = this.mSupervisor.mRootWindowContainer.getDisplayContent(i);
        if (displayContent4 != null) {
          taskDisplayArea = displayContent4.getDefaultTaskDisplayArea();
          appendLog("display-from-no-display-source=" + i);
        }
      }
    }
    TaskDisplayArea taskDisplayArea2 = taskDisplayArea;
    if (task != null) {
      activityRecord3 = task.getRootActivity() == null ? activityRecord2 : task.getRootActivity();
    } else {
      activityRecord3 = activityRecord2;
    }
    if (taskDisplayArea2 == null
        || taskDisplayArea2.getDisplayId() == 2
        || taskDisplayArea2.getDisplayId() == -1) {
      TaskDisplayArea dexPreferredLaunchDisplay =
          getDexPreferredLaunchDisplay(
              task,
              activityRecord2,
              activityRecord,
              launchParams,
              taskDisplayArea2 == null ? -1 : taskDisplayArea2.getDisplayId());
      if (activityOptions != null && activityOptions.getLaunchTaskId() != -1) {
        Task anyTaskForId =
            this.mSupervisor.mRootWindowContainer.anyTaskForId(
                activityOptions.getLaunchTaskId(), 0, null, false);
        if (anyTaskForId != null && anyTaskForId.getDisplayArea() != null) {
          dexPreferredLaunchDisplay = anyTaskForId.getDisplayArea();
          appendLog("display-from-launch-target-task" + dexPreferredLaunchDisplay.getDisplayId());
        } else if (dexPreferredLaunchDisplay != null) {
          appendLog("display-from-dex-policy" + dexPreferredLaunchDisplay.getDisplayId());
        }
        taskDisplayArea2 = dexPreferredLaunchDisplay;
      } else if (dexPreferredLaunchDisplay != null) {
        appendLog("display-from-dex-policy" + dexPreferredLaunchDisplay.getDisplayId());
        taskDisplayArea2 = dexPreferredLaunchDisplay;
      }
    }
    if (taskDisplayArea2 == null
        && this.mSupervisor.mService.mRemoteAppController.isRemoteAppDisplayRunningLocked()) {
      taskDisplayArea2 =
          getPreferredLaunchTaskDisplayAreaForRemoteApp(activityRecord3, activityRecord);
    }
    if (taskDisplayArea2 == null && activityRecord != null) {
      taskDisplayArea2 = activityRecord.getDisplayArea();
      appendLog("display-area-from-source=" + taskDisplayArea2);
    }
    if (taskDisplayArea2 == null && task != null) {
      task2 = task.getRootTask();
    }
    if (task2 != null) {
      if (WmCoverState.isEnabled()
          && ((CoverState) WmCoverState.getInstance()).attached
          && !WmCoverState.getInstance().isCoverClosed()) {
        taskDisplayArea2 = getFocusedDisplayAreaIfNeeded(activityRecord3);
      }
      if (taskDisplayArea2 == null) {
        appendLog("display-from-task=" + task2.getDisplayId());
        taskDisplayArea2 = task2.getDisplayArea();
      }
    }
    if (taskDisplayArea2 == null
        && activityOptions != null
        && (displayContent =
                this.mSupervisor.mRootWindowContainer.getDisplayContent(
                    (callerDisplayId = activityOptions.getCallerDisplayId())))
            != null) {
      taskDisplayArea2 = displayContent.getDefaultTaskDisplayArea();
      appendLog("display-from-caller=" + callerDisplayId);
    }
    if (taskDisplayArea2 == null) {
      taskDisplayArea2 = launchParams.mPreferredTaskDisplayArea;
    }
    if (taskDisplayArea2 != null
        && !this.mSupervisor.mService.mSupportsMultiDisplay
        && taskDisplayArea2.getDisplayId() != 0) {
      taskDisplayArea2 = this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
    }
    if (taskDisplayArea2 != null
        && activityRecord2 != null
        && activityRecord2.isActivityTypeHome()
        && !this.mSupervisor.mRootWindowContainer.canStartHomeOnDisplayArea(
            activityRecord2.info, taskDisplayArea2, false)) {
      taskDisplayArea2 = this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
    }
    if (taskDisplayArea2 != null) {
      return taskDisplayArea2;
    }
    TaskDisplayArea fallbackDisplayAreaForActivity =
        getFallbackDisplayAreaForActivity(activityRecord2, request);
    if (fallbackDisplayAreaForActivity.getDisplayId() != 2
        || this.mSupervisor.mService.mDexController.isDexDisplayActivated()) {
      return this.mSupervisor.mService.mWindowManager.mExt.mExtraDisplayPolicy
              .shouldChooseDefaultTaskDisplayArea(fallbackDisplayAreaForActivity.getDisplayId())
          ? this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea()
          : fallbackDisplayAreaForActivity;
    }
    return this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
  }

  public static /* synthetic */ TaskDisplayArea lambda$getPreferredLaunchTaskDisplayArea$1(
      int i, TaskDisplayArea taskDisplayArea) {
    if (taskDisplayArea.mFeatureId == i) {
      return taskDisplayArea;
    }
    return null;
  }

  public final TaskDisplayArea getFocusedDisplayAreaIfNeeded(ActivityRecord activityRecord) {
    if (activityRecord.isActivityTypeHomeOrRecents()) {
      return null;
    }
    DisplayContent topFocusedDisplayContent =
        this.mSupervisor.mService.mRootWindowContainer.getTopFocusedDisplayContent();
    if (topFocusedDisplayContent.getDisplayId() != 0) {
      return null;
    }
    appendLog("display-from-focus=" + topFocusedDisplayContent.getDisplayId());
    return topFocusedDisplayContent.getDefaultTaskDisplayArea();
  }

  public final TaskDisplayArea getFallbackDisplayAreaForActivity(
      ActivityRecord activityRecord, ActivityStarter.Request request) {
    WindowProcessController processController =
        this.mSupervisor.mService.getProcessController(
            activityRecord.launchedFromPid, activityRecord.launchedFromUid);
    TaskDisplayArea topActivityDisplayArea =
        processController == null ? null : processController.getTopActivityDisplayArea();
    if (topActivityDisplayArea != null) {
      return topActivityDisplayArea;
    }
    WindowProcessController processController2 =
        this.mSupervisor.mService.getProcessController(
            activityRecord.getProcessName(), activityRecord.getUid());
    TaskDisplayArea topActivityDisplayArea2 =
        processController2 == null ? null : processController2.getTopActivityDisplayArea();
    if (topActivityDisplayArea2 != null) {
      return topActivityDisplayArea2;
    }
    WindowProcessController processController3 =
        request == null
            ? null
            : this.mSupervisor.mService.getProcessController(
                request.realCallingPid, request.realCallingUid);
    TaskDisplayArea topActivityDisplayArea3 =
        processController3 != null ? processController3.getTopActivityDisplayArea() : null;
    return topActivityDisplayArea3 != null
        ? topActivityDisplayArea3
        : this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
  }

  public final boolean canInheritWindowingModeFromSource(
      DisplayContent displayContent,
      TaskDisplayArea taskDisplayArea,
      ActivityRecord activityRecord,
      Task task) {
    ActivityRecord rootActivity;
    if (activityRecord == null || taskDisplayArea.inFreeformWindowingMode()) {
      return false;
    }
    int windowingMode = activityRecord.getWindowingMode();
    if (windowingMode == 1 || windowingMode == 5) {
      return (windowingMode != 5
              || task == null
              || !task.inPinnedWindowingMode()
              || (rootActivity = task.getRootActivity()) == null
              || rootActivity.getLastParentBeforePip() == null)
          && displayContent.getDisplayId() == activityRecord.getDisplayId();
    }
    return false;
  }

  public final boolean canCalculateBoundsForFullscreenTask(TaskDisplayArea taskDisplayArea, int i) {
    return this.mSupervisor.mService.mSupportsFreeformWindowManagement
        && ((taskDisplayArea.getWindowingMode() == 1 && i == 0) || i == 1);
  }

  public final boolean canApplyFreeformWindowPolicy(
      TaskDisplayArea taskDisplayArea,
      int i,
      ActivityRecord activityRecord,
      ActivityOptions activityOptions,
      ActivityRecord activityRecord2) {
    if (taskDisplayArea.isDexMode() && activityRecord.isActivityTypeHome()) {
      return false;
    }
    if (activityOptions != null
        && activityOptions.getLaunchBounds() != null
        && i != 6
        && !activityOptions.getLaunchBounds().isEmpty()
        && !activityOptions.isLaunchIntoPip()) {
      return true;
    }
    if (!(taskDisplayArea.isDexMode()
            && activityRecord.isResolverOrDelegateActivity()
            && activityRecord2 != null
            && activityRecord2.isActivityTypeHome()
            && i == 0)
        && this.mSupervisor.mService.mSupportsFreeformWindowManagement) {
      return (taskDisplayArea.inFreeformWindowingMode() && i == 0) || i == 5;
    }
    return false;
  }

  public final boolean canApplyPipWindowPolicy(int i) {
    return this.mSupervisor.mService.mSupportsPictureInPicture && i == 2;
  }

  public final void getLayoutBounds(
      TaskDisplayArea taskDisplayArea,
      ActivityRecord activityRecord,
      ActivityInfo.WindowLayout windowLayout,
      Rect rect) {
    int i;
    int i2;
    int i3 = windowLayout.gravity;
    int i4 = i3 & 112;
    int i5 = i3 & 7;
    if (!windowLayout.hasSpecifiedSize() && i4 == 0 && i5 == 0) {
      rect.setEmpty();
      return;
    }
    Rect rect2 = this.mTmpStableBounds;
    taskDisplayArea.getStableRect(rect2);
    int width = rect2.width();
    int height = rect2.height();
    float f = 1.0f;
    if (!windowLayout.hasSpecifiedSize()) {
      if (!rect.isEmpty()) {
        i = rect.width();
        i2 = rect.height();
      } else {
        getTaskBounds(activityRecord, taskDisplayArea, windowLayout, 5, false, rect);
        i = rect.width();
        i2 = rect.height();
      }
    } else {
      i = windowLayout.width;
      if (i < 0 || i >= width) {
        float f2 = windowLayout.widthFraction;
        i =
            (f2 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f2 >= 1.0f)
                ? width
                : (int) (width * f2);
      }
      i2 = windowLayout.height;
      if (i2 < 0 || i2 >= height) {
        float f3 = windowLayout.heightFraction;
        i2 =
            (f3 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f3 >= 1.0f)
                ? height
                : (int) (height * f3);
      }
    }
    float f4 = i5 != 3 ? i5 != 5 ? 0.5f : 1.0f : 0.0f;
    if (i4 == 48) {
      f = 0.0f;
    } else if (i4 != 80) {
      f = 0.5f;
    }
    rect.set(0, 0, i, i2);
    rect.offset(rect2.left, rect2.top);
    rect.offset((int) (f4 * (width - i)), (int) (f * (height - i2)));
  }

  public final boolean shouldLaunchUnresizableAppInFreeform(
      ActivityRecord activityRecord,
      TaskDisplayArea taskDisplayArea,
      ActivityOptions activityOptions) {
    if ((activityOptions == null || activityOptions.getLaunchWindowingMode() != 1)
        && activityRecord.supportsFreeformInDisplayArea(taskDisplayArea)
        && !activityRecord.isResizeable()) {
      int orientationFromBounds = orientationFromBounds(taskDisplayArea.getBounds());
      int resolveOrientation =
          resolveOrientation(activityRecord, taskDisplayArea, taskDisplayArea.getBounds());
      if (taskDisplayArea.getWindowingMode() == 5 && orientationFromBounds != resolveOrientation) {
        return true;
      }
    }
    return false;
  }

  public final int resolveOrientation(ActivityRecord activityRecord) {
    int i = activityRecord.info.screenOrientation;
    if (i != 0) {
      if (i != 1) {
        if (i != 11) {
          if (i != 12) {
            if (i != 14) {
              switch (i) {
                case 5:
                  break;
                case 6:
                case 8:
                  break;
                case 7:
                case 9:
                  break;
                default:
                  return -1;
              }
            }
            return 14;
          }
        }
      }
      appendLog("activity-requested-portrait");
      return 1;
    }
    appendLog("activity-requested-landscape");
    return 0;
  }

  public final void cascadeBounds(Rect rect, TaskDisplayArea taskDisplayArea, Rect rect2) {
    rect2.set(rect);
    int i = taskDisplayArea.getConfiguration().densityDpi;
    taskDisplayArea.getBounds(this.mTmpBounds);
    rect2.offset(
        Math.min(
            this.mDefaultFreeformStepHorizontal, Math.max(0, this.mTmpBounds.right - rect.right)),
        Math.min(
            this.mDefaultFreeformStepVertical, Math.max(0, this.mTmpBounds.bottom - rect.bottom)));
  }

  public final void getTaskBounds(
      ActivityRecord activityRecord,
      TaskDisplayArea taskDisplayArea,
      ActivityInfo.WindowLayout windowLayout,
      int i,
      boolean z,
      Rect rect) {
    getTaskBounds(activityRecord, taskDisplayArea, windowLayout, i, z, rect, null);
  }

  public final void getTaskBounds(
      ActivityRecord activityRecord,
      TaskDisplayArea taskDisplayArea,
      ActivityInfo.WindowLayout windowLayout,
      int i,
      boolean z,
      Rect rect,
      ActivityRecord activityRecord2) {
    String str;
    if (i != 5 && i != 1) {
      appendLog("skip-bounds-" + WindowConfiguration.windowingModeToString(i));
      return;
    }
    int resolveOrientation = resolveOrientation(activityRecord, taskDisplayArea, rect);
    if (resolveOrientation != 1 && resolveOrientation != 0) {
      throw new IllegalStateException(
          "Orientation must be one of portrait or landscape, but it's "
              + ActivityInfo.screenOrientationToString(resolveOrientation));
    }
    taskDisplayArea.getStableRect(this.mTmpStableBounds);
    Size customFreeformSize = getCustomFreeformSize(taskDisplayArea, this.mTmpStableBounds);
    this.mTmpBounds.set(0, 0, customFreeformSize.getWidth(), customFreeformSize.getHeight());
    if (z || sizeMatches(rect, this.mTmpBounds)) {
      if (resolveOrientation == orientationFromBounds(rect)) {
        appendLog("freeform-size-orientation-match=" + rect);
      } else if (sizeMatches(rect, this.mTmpBounds)) {
        taskDisplayArea.getBounds(this.mTmpBounds2);
        centerBounds(
            taskDisplayArea, this.mTmpBounds.width(), this.mTmpBounds.height(), this.mTmpBounds2);
        rect.set(this.mTmpBounds2);
        appendLog("freeform-orientation-ignore=" + rect);
      } else {
        LaunchParamsUtil.centerBounds(taskDisplayArea, rect.height(), rect.width(), rect);
        appendLog("freeform-orientation-mismatch=" + rect);
      }
    } else {
      adjustBoundsToFitInDisplayArea(taskDisplayArea, windowLayout, this.mTmpBounds);
      rect.setEmpty();
      LaunchParamsUtil.centerBounds(
          taskDisplayArea, this.mTmpBounds.width(), this.mTmpBounds.height(), rect);
      appendLog("freeform-size-mismatch=" + rect);
    }
    if (activityRecord2 != null
        && (str = activityRecord.taskAffinityWithoutUid) != null
        && str.equals(activityRecord2.packageName)) {
      adjustBoundsToAvoidConflictInDisplayArea(
          taskDisplayArea,
          rect,
          activityRecord.taskAffinityWithoutUid,
          activityRecord2.getBounds(),
          null);
    } else {
      adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea, rect);
    }
  }

  public final int resolveOrientation(
      ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea, Rect rect) {
    String str;
    String str2;
    int resolveOrientation = resolveOrientation(activityRecord);
    if (resolveOrientation == 14) {
      if (rect.isEmpty()) {
        resolveOrientation =
            convertOrientationToScreenOrientation(taskDisplayArea.getConfiguration().orientation);
      } else {
        resolveOrientation = orientationFromBounds(rect);
      }
      if (rect.isEmpty()) {
        str2 = "locked-orientation-from-display=" + resolveOrientation;
      } else {
        str2 = "locked-orientation-from-bounds=" + rect;
      }
      appendLog(str2);
    }
    if (resolveOrientation == -1) {
      resolveOrientation = rect.isEmpty() ? 1 : orientationFromBounds(rect);
      if (rect.isEmpty()) {
        str = "default-portrait";
      } else {
        str = "orientation-from-bounds=" + rect;
      }
      appendLog(str);
    }
    return resolveOrientation;
  }

  public final void centerBounds(TaskDisplayArea taskDisplayArea, int i, int i2, Rect rect) {
    if (rect.isEmpty()) {
      taskDisplayArea.getStableRect(rect);
    }
    int centerX = rect.centerX() - (i / 2);
    int centerY = rect.centerY() - (i2 / 2);
    rect.set(centerX, centerY, i + centerX, i2 + centerY);
  }

  public final void adjustBoundsToFitInDisplayArea(
      TaskDisplayArea taskDisplayArea, ActivityInfo.WindowLayout windowLayout, Rect rect) {
    LaunchParamsUtil.adjustBoundsToFitInDisplayArea(
        taskDisplayArea,
        this.mSupervisor.mRootWindowContainer.getConfiguration().getLayoutDirection(),
        windowLayout,
        rect);
  }

  public final void adjustBoundsToAvoidConflictInDisplayArea(
      TaskDisplayArea taskDisplayArea, Rect rect) {
    adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea, rect, null, null, null);
  }

  public final void adjustBoundsToAvoidConflictInDisplayArea(
      TaskDisplayArea taskDisplayArea,
      Rect rect,
      final String str,
      final Rect rect2,
      final Task task) {
    final ArrayList arrayList = new ArrayList();
    taskDisplayArea.forAllRootTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            MultiTaskingTaskLaunchParamsModifier.lambda$adjustBoundsToAvoidConflictInDisplayArea$2(
                str, rect2, task, arrayList, (Task) obj);
          }
        },
        false);
    adjustBoundsToAvoidConflict(
        taskDisplayArea.getBounds(), arrayList, rect, taskDisplayArea.getDisplayId() == 2);
  }

  public static /* synthetic */ void lambda$adjustBoundsToAvoidConflictInDisplayArea$2(
      String str, Rect rect, Task task, List list, Task task2) {
    if (task2.inFreeformWindowingMode()
        && !task2.isUnderHomeRootTask()
        && !task2.isMinimized()
        && task2.isVisible()) {
      PooledPredicate obtainPredicate =
          PooledLambda.obtainPredicate(
              new TriPredicate() { // from class:
                                   // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda3
                public final boolean test(Object obj, Object obj2, Object obj3) {
                  boolean filterCheckBounds;
                  filterCheckBounds =
                      MultiTaskingTaskLaunchParamsModifier.filterCheckBounds(
                          (Task) obj, (String) obj2, (Rect) obj3);
                  return filterCheckBounds;
                }
              },
              PooledLambda.__(Task.class),
              str,
              rect);
      Task task3 = task2.getTask(obtainPredicate);
      obtainPredicate.recycle();
      if (task3 != null) {
        if (task == null || !task.equals(task2)) {
          list.add(task2.getBounds());
        }
      }
    }
  }

  public void adjustBoundsToAvoidConflict(Rect rect, List list, Rect rect2, boolean z) {
    if (rect.contains(rect2) && boundsConflict(list, rect2)) {
      calculateCandidateShiftDirections(rect, rect2, z);
      for (int i : this.mTmpDirections) {
        if (i == 0) {
          return;
        }
        this.mTmpBounds.set(rect2);
        int i2 = 0;
        while (true) {
          if (!boundsConflict(list, this.mTmpBounds) || (!rect.contains(this.mTmpBounds) && !z)) {
            break;
          }
          shiftFreeformBounds(i, this.mTmpBounds);
          if (z && !rect.contains(this.mTmpBounds)) {
            offsetTaskBounds(this.mTmpBounds, rect);
          }
          int i3 = i2 + 1;
          if (i2 > 200) {
            Slog.w(
                "ActivityTaskManager",
                "TaskLaunchParamsModifier.position: max_bounds_conflict_count, inOutBounds="
                    + rect2
                    + ", mTmpBounds="
                    + this.mTmpBounds
                    + ", displayBounds="
                    + rect
                    + ", mDefaultFreeformStepHorizontal="
                    + this.mDefaultFreeformStepHorizontal
                    + ", mDefaultFreeformStepVertical="
                    + this.mDefaultFreeformStepVertical
                    + ", Callers="
                    + Debug.getCallers(8));
            rect2.set(rect);
            StringBuilder sb = new StringBuilder();
            sb.append(
                "TaskLaunchParamsModifier.position: max_bounds_conflict_count, adjusted proposal=");
            sb.append(rect2);
            sb.append(", break!");
            Slog.w("ActivityTaskManager", sb.toString());
            break;
          }
          i2 = i3;
        }
        if (!boundsConflict(list, this.mTmpBounds) && rect.contains(this.mTmpBounds)) {
          rect2.set(this.mTmpBounds);
          appendLog("avoid-bounds-conflict=" + rect2);
          return;
        }
      }
    }
  }

  public final void calculateCandidateShiftDirections(Rect rect, Rect rect2, boolean z) {
    int[] iArr;
    int i = 0;
    while (true) {
      iArr = this.mTmpDirections;
      if (i >= iArr.length) {
        break;
      }
      iArr[i] = 0;
      i++;
    }
    if (z) {
      iArr[0] = 85;
      return;
    }
    int i2 = rect.left;
    int i3 = rect.right;
    int i4 = ((i2 * 2) + i3) / 3;
    int i5 = (i2 + (i3 * 2)) / 3;
    int centerX = rect2.centerX();
    if (centerX < i4) {
      this.mTmpDirections[0] = 5;
      return;
    }
    if (centerX > i5) {
      this.mTmpDirections[0] = 3;
      return;
    }
    int i6 = rect.top;
    int i7 = rect.bottom;
    int i8 = ((i6 * 2) + i7) / 3;
    int i9 = (i6 + (i7 * 2)) / 3;
    int centerY = rect2.centerY();
    if (centerY < i8 || centerY > i9) {
      int[] iArr2 = this.mTmpDirections;
      iArr2[0] = 5;
      iArr2[1] = 3;
    } else {
      int[] iArr3 = this.mTmpDirections;
      iArr3[0] = 85;
      iArr3[1] = 51;
    }
  }

  public final boolean boundsConflict(List list, Rect rect) {
    Iterator it = list.iterator();
    while (true) {
      if (!it.hasNext()) {
        return false;
      }
      Rect rect2 = (Rect) it.next();
      boolean z = Math.abs(rect2.left - rect.left) < 4;
      boolean z2 = Math.abs(rect2.top - rect.top) < 4;
      boolean z3 = Math.abs(rect2.right - rect.right) < 4;
      boolean z4 = Math.abs(rect2.bottom - rect.bottom) < 4;
      if ((!z || !z2) && ((!z || !z4) && ((!z3 || !z2) && (!z3 || !z4)))) {}
    }
    return true;
  }

  public final void initLogBuilder(Task task, ActivityRecord activityRecord) {
    this.mLogBuilder =
        new StringBuilder("TaskLaunchParamsModifier:task=" + task + " activity=" + activityRecord);
  }

  public final void appendLog(String str) {
    StringBuilder sb = this.mLogBuilder;
    sb.append(" ");
    sb.append(str);
  }

  public final void outputLog() {
    Slog.d("ActivityTaskManager", this.mLogBuilder.toString());
  }

  public static int orientationFromBounds(Rect rect) {
    return rect.width() > rect.height() ? 0 : 1;
  }

  public static boolean sizeMatches(Rect rect, Rect rect2) {
    return Math.abs(rect2.width() - rect.width()) < 2
        && Math.abs(rect2.height() - rect.height()) < 2;
  }

  public final boolean interceptCalculateIfDexSizeCompatMode(
      ActivityRecord activityRecord,
      TaskDisplayArea taskDisplayArea,
      LaunchParamsController.LaunchParams launchParams,
      Task task,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions) {
    if (DexSizeCompatController.getInstance().isResizableAllowed()
        || !(activityRecord.isResizeable()
            || shouldLaunchUnresizableAppInFreeform(
                activityRecord, taskDisplayArea, activityOptions)
            || activityRecord.isForceNonResizeable())) {
      return DexSizeCompatController.getInstance()
          .interceptCalculateIfPossible(
              activityRecord, taskDisplayArea, launchParams, task, activityRecord2);
    }
    return false;
  }

  public final TaskDisplayArea getDexPreferredLaunchDisplay(
      Task task,
      ActivityRecord activityRecord,
      ActivityRecord activityRecord2,
      LaunchParamsController.LaunchParams launchParams,
      int i) {
    DisplayContent topFocusedDisplayContent;
    TaskDisplayArea taskDisplayArea;
    int i2 = -1;
    int i3 = 2;
    if (this.mSupervisor.mService.mDexController.getDexModeLocked() == 2) {
      Bundle bundle = activityRecord != null ? activityRecord.info.applicationInfo.metaData : null;
      if (i == -1 && bundle != null && (task == null || !task.isAttached())) {
        i =
            this.mSupervisor.mService.mDexController.getDisplayIdFromPrimaryMetaDataLocked(
                bundle.getString("com.samsung.android.multidisplay.primarydisplay"));
        appendLog("display-from-metaData=" + i);
      }
      if (i == -1 && activityRecord2 != null) {
        i = activityRecord2.getDisplayId();
        appendLog("display-from-source=" + i);
      }
      if (i == -1
          && task != null
          && task.getDisplayId() == 2
          && (taskDisplayArea = launchParams.mPreferredTaskDisplayArea) != null
          && taskDisplayArea.getDisplayId() == 2) {
        appendLog("display-from-task=2");
      } else {
        i3 = i;
      }
      if (i3 != -1
          || activityRecord == null
          || activityRecord.isActivityTypeHome()
          || activityRecord.isActivityTypeRecents()
          || (topFocusedDisplayContent =
                  this.mSupervisor.mService.mRootWindowContainer.getTopFocusedDisplayContent())
              == null) {
        i2 = i3;
      } else {
        int displayId = topFocusedDisplayContent.getDisplayId();
        appendLog("display-from-focused-stack=" + displayId);
        i2 = displayId;
      }
    } else {
      TaskDisplayArea taskDisplayArea2 = launchParams.mPreferredTaskDisplayArea;
      if ((taskDisplayArea2 != null && taskDisplayArea2.getDisplayId() == 2)
          || (task != null && task.getDisplayId() == 2)) {
        i2 = 0;
      }
    }
    DisplayContent displayContent = this.mSupervisor.mRootWindowContainer.getDisplayContent(i2);
    if (displayContent != null) {
      return displayContent.getDefaultTaskDisplayArea();
    }
    return null;
  }

  public final TaskDisplayArea getPreferredLaunchTaskDisplayAreaForRemoteApp(
      ActivityRecord activityRecord, ActivityRecord activityRecord2) {
    Task topDisplayFocusedRootTask;
    TaskDisplayArea displayArea = activityRecord2 != null ? activityRecord2.getDisplayArea() : null;
    if (displayArea == null
        && (topDisplayFocusedRootTask = this.mSupervisor.mService.getTopDisplayFocusedRootTask())
            != null) {
      displayArea = topDisplayFocusedRootTask.getDisplayArea();
    }
    if (displayArea != null) {
      DisplayContent displayContent = displayArea.mDisplayContent;
      boolean z = true;
      boolean z2 = displayContent != null && displayContent.getDisplayId() == 0;
      boolean z3 = displayContent != null && displayContent.isRemoteAppDisplay();
      if (!z2 && (!z3 || !RemoteAppController.isValidActivityTypeLocked(activityRecord))) {
        z = false;
      }
      if (!z) {
        return null;
      }
      appendLog("display-from-remote-app-policy=" + displayContent.getDisplayId());
    }
    return displayArea;
  }

  public final ActivityInfo.WindowLayout recalculateLayout(
      TaskDisplayArea taskDisplayArea,
      ActivityInfo.WindowLayout windowLayout,
      ActivityRecord activityRecord) {
    int i;
    if (!windowLayout.hasSpecifiedSize()) {
      return windowLayout;
    }
    float initialDisplayDensity =
        this.mSupervisor.mService.mWindowManager.getInitialDisplayDensity(0);
    if (initialDisplayDensity <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return windowLayout;
    }
    if (activityRecord != null && "android.server.wm.app".equals(activityRecord.packageName)) {
      i = DisplayMetrics.DENSITY_DEVICE_STABLE;
    } else {
      i = taskDisplayArea.getConfiguration().densityDpi;
    }
    float f = i / initialDisplayDensity;
    int i2 = windowLayout.width;
    int i3 = i2 < 0 ? -1 : (int) ((i2 * f) + 0.5f);
    int i4 = windowLayout.height;
    int i5 = i4 < 0 ? -1 : (int) ((i4 * f) + 0.5f);
    int i6 = windowLayout.minWidth;
    int i7 = i6 < 0 ? -1 : (int) ((i6 * f) + 0.5f);
    int i8 = windowLayout.minHeight;
    return new ActivityInfo.WindowLayout(
        i3,
        windowLayout.widthFraction,
        i5,
        windowLayout.heightFraction,
        windowLayout.gravity,
        i7,
        i8 >= 0 ? (int) ((i8 * f) + 0.5f) : -1);
  }

  public Size getCustomFreeformSize(TaskDisplayArea taskDisplayArea, Rect rect) {
    Rect bounds;
    if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY
        && !this.mSupervisor.mService.mMultiWindowFoldController.isLidOpened()) {
      Rect bounds2 = taskDisplayArea.getBounds();
      bounds =
          this.mSupervisor.mService.mMultiWindowFoldController.getMainDisplayBounds(
              bounds2.width() <= bounds2.height());
    } else {
      bounds = taskDisplayArea.getBounds();
    }
    Rect rect2 = new Rect();
    MultiWindowUtils.getDefaultFreeformBounds(bounds, rect2);
    taskDisplayArea.getStableRect(rect);
    if (rect.width() < rect2.width()) {
      rect2.right = rect.width();
    }
    if (rect.height() < rect2.height()) {
      rect2.bottom = rect.height();
    }
    return new Size(rect2.width(), rect2.height());
  }

  public static boolean filterCheckBounds(Task task, String str, Rect rect) {
    ActivityRecord topNonFinishingActivity;
    if (task.getChildCount() <= 0) {
      return false;
    }
    return str == null
        || rect == null
        || (topNonFinishingActivity = task.getTopNonFinishingActivity()) == null
        || !topNonFinishingActivity.packageName.equals(str)
        || !task.getBounds().equals(rect);
  }

  public final void shiftFreeformBounds(int i, Rect rect) {
    int i2;
    int i3 = i & 7;
    int i4 = 0;
    if (i3 == 3) {
      i2 = -this.mDefaultFreeformStepHorizontal;
    } else {
      i2 = i3 != 5 ? 0 : this.mDefaultFreeformStepHorizontal;
    }
    int i5 = i & 112;
    if (i5 == 48) {
      i4 = -this.mDefaultFreeformStepVertical;
    } else if (i5 == 80) {
      i4 = this.mDefaultFreeformStepVertical;
    }
    rect.offset(i2, i4);
  }

  public final void adjustPersistFreeformBounds(
      Task task,
      DisplayContent displayContent,
      LaunchParamsController.LaunchParams launchParams,
      Rect rect) {
    FreeformPersistBoundsParams freeformPersistBoundsParams =
        launchParams.mFreeformPersistBoundsParam;
    rect.set(freeformPersistBoundsParams.mFreeformBounds);
    int i = freeformPersistBoundsParams.mRotation;
    int i2 = displayContent.getDisplayInfo().rotation;
    Rect rect2 = new Rect();
    displayContent.getBaseDisplayRect(rect2);
    if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY
        && task != null
        && displayContent.isDefaultDisplay
        && !displayContent.isDexMode()) {
      MultiWindowUtils.adjustBoundsForScreenRatio(
          freeformPersistBoundsParams.mDisplayBounds,
          rect2,
          freeformPersistBoundsParams.mFreeformBounds,
          rect);
      return;
    }
    rotateDisplayRect(rect2, i2, i);
    float width = rect2.width() / freeformPersistBoundsParams.mDisplayBounds.width();
    float height = rect2.height() / freeformPersistBoundsParams.mDisplayBounds.height();
    if (width != 1.0f) {
      rect.left = (int) ((rect.left * width) + 0.5f);
      rect.right = (int) ((rect.right * width) + 0.5f);
    }
    if (height != 1.0f) {
      rect.top = (int) ((rect.top * height) + 0.5f);
      rect.bottom = (int) ((rect.bottom * height) + 0.5f);
    }
    if (i != i2) {
      displayContent.rotateBounds(i, i2, rect);
    }
  }

  public final void rotateDisplayRect(Rect rect, int i, int i2) {
    int deltaRotation = RotationUtils.deltaRotation(i, i2);
    if (deltaRotation == 1 || deltaRotation == 3) {
      rect.set(0, 0, rect.height(), rect.width());
    }
  }

  public final boolean canApplyFreeformPersistentBounds(
      DisplayContent displayContent, LaunchParamsController.LaunchParams launchParams) {
    return displayContent.isDefaultDisplay
        && !displayContent.isDexMode()
        && (!CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY
            || displayContent.getConfiguration().semDisplayDeviceType
                == launchParams.mDisplayDeviceType)
        && launchParams.hasValidFreeformPersistBounds();
  }

  public final boolean getDexMetaDataBounds(
      TaskDisplayArea taskDisplayArea, Task task, ActivityRecord activityRecord, Rect rect) {
    Point dexMetadataLaunchSizeLocked;
    if (task != null) {
      dexMetadataLaunchSizeLocked = task.getDexMetadataLaunchSize();
    } else {
      dexMetadataLaunchSizeLocked =
          this.mSupervisor.mService.mDexController.getDexMetadataLaunchSizeLocked(
              DexController.parseDexMetadata(activityRecord.info),
              taskDisplayArea.getDisplayId(),
              activityRecord.mIsDexCompatEnabled,
              activityRecord.getWindowingMode());
    }
    if (dexMetadataLaunchSizeLocked == null) {
      return false;
    }
    int i = dexMetadataLaunchSizeLocked.x;
    if (i == 0 && dexMetadataLaunchSizeLocked.y == 0) {
      rect.setEmpty();
      appendLog("dex-fullscreen-metadata-bounds");
      return true;
    }
    rect.set(0, 0, i, dexMetadataLaunchSizeLocked.y);
    ActivityInfo.WindowLayout windowLayout = this.mTmpLayout;
    if (windowLayout == null
        || windowLayout.width != dexMetadataLaunchSizeLocked.x
        || windowLayout.height != dexMetadataLaunchSizeLocked.y) {
      this.mTmpLayout =
          new ActivityInfo.WindowLayout(
              dexMetadataLaunchSizeLocked.x,
              -1.0f,
              dexMetadataLaunchSizeLocked.y,
              -1.0f,
              17,
              -1,
              -1);
    }
    getLayoutBounds(taskDisplayArea, activityRecord, this.mTmpLayout, rect);
    return true;
  }

  public final void getInitialDexBounds(
      TaskDisplayArea taskDisplayArea, ActivityRecord activityRecord, Rect rect) {
    int i;
    float f;
    float f2;
    int width = taskDisplayArea.getBounds().width();
    int height = taskDisplayArea.getBounds().height();
    if (CoreRune.MT_NEW_DEX_BOUNDS_POLICY && taskDisplayArea.isNewDexMode()) {
      if (width > height) {
        PointF pointF = DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
        i = (int) (width * pointF.x);
        f = height;
        f2 = pointF.y;
      } else {
        PointF pointF2 = DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
        i = (int) (width * pointF2.y);
        f = height;
        f2 = pointF2.x;
      }
    } else if (this.mSupervisor.mService.mDexController.getDexModeLocked() == 1) {
      PointF pointF3 = DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE;
      i = (int) (width * pointF3.x);
      f = height;
      f2 = pointF3.y;
    } else {
      PointF pointF4 = DEX_DEFAULT_SIZE_RATIO;
      i = (int) (width * pointF4.x);
      f = height;
      f2 = pointF4.y;
    }
    int i2 = (int) (f * f2);
    int i3 = i;
    appendLog("from-default-size");
    ActivityInfo.WindowLayout windowLayout = this.mTmpLayout;
    if (windowLayout == null || windowLayout.width != i3 || windowLayout.height != i2) {
      this.mTmpLayout = new ActivityInfo.WindowLayout(i3, -1.0f, i2, -1.0f, 17, -1, -1);
    }
    getLayoutBounds(taskDisplayArea, activityRecord, this.mTmpLayout, rect);
  }

  public final boolean canApplyDexPolicy(DisplayContent displayContent, int i) {
    return (displayContent.isDexMode() || (CoreRune.MT_NEW_DEX && displayContent.isNewDexMode()))
        && (i == 0 || i == 5);
  }

  public final boolean getPersistentDexBounds(
      int i, int i2, LaunchParamsController.LaunchParams launchParams, Rect rect) {
    Rect rect2;
    if (i == 0) {
      return false;
    }
    DexPersistBoundsParams dexPersistBoundsParams = launchParams.mDexPersistBoundsParam;
    if (dexPersistBoundsParams.mDexWindowingMode == 1) {
      rect.setEmpty();
      return true;
    }
    if (i != 2) {
      rect2 = dexPersistBoundsParams.mDexStandAloneBounds;
    } else if (i2 == 2) {
      rect2 = dexPersistBoundsParams.mDexDualBounds;
    } else {
      rect2 = launchParams.mBounds;
    }
    if (rect2.isEmpty()) {
      return false;
    }
    rect.set(rect2);
    return true;
  }

  public final boolean canApplyDexPersistentBounds(
      DisplayContent displayContent, LaunchParamsController.LaunchParams launchParams) {
    return displayContent.isDexMode() && launchParams.hasValidDexPersistBounds();
  }

  public final boolean getPersistentNewDexBounds(
      int i, LaunchParamsController.LaunchParams launchParams, Rect rect) {
    if (i == 0) {
      return false;
    }
    Rect rect2 = launchParams.mNewDexPersistBoundsParam.mNewDexNextGenBounds;
    if (rect2.isEmpty()) {
      return false;
    }
    rect.set(rect2);
    return true;
  }

  public final boolean canApplyNewDexPersistentBounds(
      DisplayContent displayContent, LaunchParamsController.LaunchParams launchParams) {
    return displayContent.isNewDexMode() && launchParams.hasValidNewDexPersistBounds();
  }

  public final void updateSnappingBounds(
      TaskDisplayArea taskDisplayArea,
      Task task,
      ActivityRecord activityRecord,
      LaunchParamsController.LaunchParams launchParams,
      ActivityInfo.WindowLayout windowLayout,
      Rect rect) {
    int dexModeLocked = this.mSupervisor.mService.mDexController.getDexModeLocked();
    if (task.isDexMode()) {
      getPersistentDexBounds(dexModeLocked, taskDisplayArea.getDisplayId(), launchParams, rect);
    } else if (task.isNewDexMode()) {
      getPersistentNewDexBounds(dexModeLocked, launchParams, rect);
    }
    if (rect.isEmpty()) {
      getDexMetaDataBounds(taskDisplayArea, task, activityRecord, rect);
      if (rect.isEmpty()) {
        if (windowLayout != null) {
          getLayoutBounds(
              taskDisplayArea,
              activityRecord,
              recalculateLayout(taskDisplayArea, windowLayout, activityRecord),
              rect);
        }
        if (rect.isEmpty()) {
          getInitialDexBounds(taskDisplayArea, activityRecord, rect);
        }
      }
    }
  }

  public final void offsetTaskBounds(Rect rect, Rect rect2) {
    int width = rect.width();
    int height = rect.height();
    if (rect.bottom > rect2.bottom) {
      int i = rect2.top;
      rect.top = i;
      rect.bottom = i + height;
    }
    if (rect.right > rect2.right) {
      int i2 = rect2.left;
      rect.left = i2;
      rect.right = i2 + width;
    }
  }
}
