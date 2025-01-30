package com.android.server.wm;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.multiwindow.DexSizeCompatResizeGuide;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DexSizeCompatController {
  public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
  public float mAspectRatioScale;
  public float mDefaultScale;
  public final LaunchParamsController.LaunchParams mTmpParams;

  public abstract class LazyHolder {
    public static final DexSizeCompatController sInstance = new DexSizeCompatController();
  }

  public static DexSizeCompatController getInstance() {
    return LazyHolder.sInstance;
  }

  public DexSizeCompatController() {
    this.mDefaultScale = 0.72f;
    this.mAspectRatioScale = 0.72f;
    this.mTmpParams = new LaunchParamsController.LaunchParams();
  }

  public boolean isEnabled(TaskDisplayArea taskDisplayArea) {
    return taskDisplayArea != null
        && SizeCompatPolicyManager.get().getActiveMode(taskDisplayArea.getDisplayId()) == 1;
  }

  public void toggle(final TaskDisplayArea taskDisplayArea, final boolean z) {
    int displayId = taskDisplayArea.getDisplayId();
    if (SizeCompatPolicyManager.get().getActiveMode(displayId) == z) {
      if (SAFE_DEBUG) {
        Slog.d(
            "SizeCompatPolicy",
            "The Mode is already changed. enabled=" + z + ", tda=" + taskDisplayArea);
        return;
      }
      return;
    }
    SizeCompatPolicyManager.get().setActiveMode(displayId, z ? 1 : 0);
    taskDisplayArea.forAllTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DexSizeCompatController$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DexSizeCompatController.this.lambda$toggle$1(z, taskDisplayArea, (Task) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$toggle$1(
      boolean z, TaskDisplayArea taskDisplayArea, Task task) {
    if (!z) {
      DexSizeCompatPolicy compatPolicy = getCompatPolicy(task);
      if (compatPolicy != null) {
        compatPolicy.setEnabled(false);
        compatPolicy.mTask.setWindowingMode(1);
        return;
      }
      return;
    }
    DexSizeCompatPolicy createCompatPolicy =
        shouldCreateCompatPolicy(task, null, taskDisplayArea)
            ? createCompatPolicy(task, taskDisplayArea)
            : null;
    if (createCompatPolicy == null) {
      return;
    }
    this.mTmpParams.mBounds.setEmpty();
    task.mTaskSupervisor
        .getLaunchParamsController()
        .calculate(task, null, null, null, null, null, 3, this.mTmpParams);
    Configuration requestedOverrideConfiguration = task.getRequestedOverrideConfiguration();
    if (!this.mTmpParams.mBounds.isEmpty()) {
      requestedOverrideConfiguration.windowConfiguration.setBounds(this.mTmpParams.mBounds);
      requestedOverrideConfiguration.windowConfiguration.setWindowingMode(5);
    } else {
      Slog.d("SizeCompatPolicy", "toggle: task=" + task + ", tda=" + taskDisplayArea);
      createCompatPolicy.setFreeformConfiguration(
          requestedOverrideConfiguration,
          taskDisplayArea.getBounds(),
          new BiConsumer() { // from class:
                             // com.android.server.wm.DexSizeCompatController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              CompatUtils.adjustBoundsToCenter((Rect) obj, (Rect) obj2);
            }
          });
    }
    if (SizeCompatMultiTaskingPolicy.DEBUG_CONFIG) {
      SizeCompatMultiTaskingPolicy.logDebugConfig(
          "toggle", task, requestedOverrideConfiguration.windowConfiguration);
    }
  }

  public boolean interceptCalculateIfPossible(
      ActivityRecord activityRecord,
      TaskDisplayArea taskDisplayArea,
      LaunchParamsController.LaunchParams launchParams,
      Task task,
      ActivityRecord activityRecord2) {
    int topOrientationInTask;
    if (!isEnabled(taskDisplayArea)) {
      return false;
    }
    DexSizeCompatPolicy compatPolicy = getCompatPolicy(task);
    if (!(compatPolicy != null
        || shouldCreateCompatPolicy(null, activityRecord, taskDisplayArea))) {
      Slog.w(
          "SizeCompatPolicy",
          "interceptCalculateIfPossible: root="
              + activityRecord
              + ", task="
              + task
              + ", tda="
              + taskDisplayArea);
      return false;
    }
    if (task == null) {
      return true;
    }
    if (compatPolicy == null
        && (compatPolicy = createCompatPolicy(task, taskDisplayArea)) == null) {
      return false;
    }
    Rect rect = launchParams.mBounds;
    Rect bounds = taskDisplayArea.getBounds();
    int width = bounds.width();
    int height = bounds.height();
    if (activityRecord2 != null) {
      topOrientationInTask = activityRecord2.getRequestedConfigurationOrientation();
    } else {
      topOrientationInTask = compatPolicy.getTopOrientationInTask();
    }
    boolean z = !rect.isEmpty();
    if (z) {
      int width2 = rect.width();
      int height2 = rect.height();
      if (compatPolicy.isRotatable(topOrientationInTask)) {
        topOrientationInTask = CompatUtils.getConfigurationOrientation(width2, height2);
        compatPolicy.setUserOrientation(topOrientationInTask);
      }
      DisplayContent displayContent = task.mDisplayContent;
      Rect stableBounds =
          displayContent != null ? compatPolicy.getStableBounds(displayContent) : bounds;
      if (compatPolicy.isSmallerContainer(
          stableBounds.width(), stableBounds.height(), width2, height2)) {
        rect.setEmpty();
      } else {
        Rect fullScreenBounds = compatPolicy.getFullScreenBounds(topOrientationInTask);
        float adjustRoundScale =
            CompatUtils.adjustRoundScale(CompatUtils.getCompatScale(fullScreenBounds, rect));
        if (adjustRoundScale < getDefaultScale()) {
          float minScale =
              compatPolicy.getMinScale(topOrientationInTask, stableBounds, fullScreenBounds);
          if (adjustRoundScale < minScale) {
            adjustRoundScale = minScale;
          }
        }
        compatPolicy.setUserScaleInternal(adjustRoundScale);
      }
    }
    if (rect.isEmpty()) {
      rect.set(compatPolicy.getTaskBounds(bounds, topOrientationInTask));
    }
    if (rect.isEmpty() || (rect.width() == width && rect.height() == height)) {
      rect.setEmpty();
      launchParams.mWindowingMode = 1;
    } else {
      if (!z) {
        CompatUtils.adjustBoundsToCenter(bounds, rect);
      }
      launchParams.mWindowingMode = 5;
    }
    if (SizeCompatMultiTaskingPolicy.DEBUG_CONFIG) {
      SizeCompatMultiTaskingPolicy.logDebugConfig(
          "interceptCalculateIfPossible", task, launchParams);
    }
    return true;
  }

  public DexSizeCompatPolicy getCompatPolicy(Task task) {
    return getCompatPolicy(task, false);
  }

  public DexSizeCompatPolicy getCompatPolicy(Task task, boolean z) {
    SizeCompatPolicy compatPolicy = SizeCompatPolicyManager.get().getCompatPolicy(task, z);
    if (compatPolicy != null) {
      return compatPolicy.asDexSizeCompatPolicy();
    }
    return null;
  }

  public final boolean shouldCreateCompatPolicy(
      Task task, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea) {
    return SizeCompatPolicyManager.get()
        .shouldCreateCompatPolicy(task, activityRecord, taskDisplayArea, 1);
  }

  public final DexSizeCompatPolicy createCompatPolicy(Task task, TaskDisplayArea taskDisplayArea) {
    DisplayContent displayContent = taskDisplayArea.getDisplayContent();
    if (displayContent == null) {
      Slog.w(
          "SizeCompatPolicy",
          "createCompatPolicy: Display is null, task=" + task + ", tda=" + taskDisplayArea);
      return null;
    }
    DexSizeCompatPolicy dexSizeCompatPolicy = new DexSizeCompatPolicy(task, displayContent);
    SizeCompatPolicyManager.get().setCompatPolicy(task, dexSizeCompatPolicy);
    return dexSizeCompatPolicy;
  }

  public boolean isResizableAllowed() {
    return SizeCompatPolicyManager.get().getLaunchPolicy() == 2;
  }

  public void setDefaultScale(float f) {
    this.mDefaultScale = f;
  }

  public float getDefaultScale() {
    return this.mDefaultScale;
  }

  public void setAspectRatioScale(float f) {
    this.mAspectRatioScale = f;
  }

  public float getAspectRatioScale() {
    return this.mAspectRatioScale;
  }

  public void dump(DisplayContent displayContent, PrintWriter printWriter, String str) {
    if (isEnabled(displayContent.getDefaultTaskDisplayArea())) {
      printWriter.println(str + "DEX SIZE COMPAT CONTROLLER");
      printWriter.print(str + "  ");
      printWriter.print("DisplayId=" + displayContent.getDisplayId());
      printWriter.print(", DefaultScale=" + this.mDefaultScale);
      printWriter.print(", AspectRatioScale=" + this.mAspectRatioScale);
      if (isResizableAllowed()) {
        printWriter.print(", ResizableAllowed=true");
      }
      printWriter.println();
      printWriter.println();
    }
  }

  public class DexSizeCompatPolicy extends SizeCompatMultiTaskingPolicy
      implements SizeCompatDragPolicy {
    public Rect mTmpRect;

    @Override // com.android.server.wm.SizeCompatPolicyCasting
    public DexSizeCompatPolicy asDexSizeCompatPolicy() {
      return this;
    }

    public final float getLeftPositionMultiplier() {
      return 0.5f;
    }

    @Override // com.android.server.wm.SizeCompatPolicyCasting
    public int getMode() {
      return 1;
    }

    public boolean isRotatable(int i) {
      return i == 0;
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public boolean shouldIgnoreMinimalTaskDimensions() {
      return true;
    }

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsIgnoreOrientationRequest() {
      return true;
    }

    public DexSizeCompatPolicy(Task task, DisplayContent displayContent) {
      super(
          task,
          displayContent.mBaseDisplayWidth,
          displayContent.mBaseDisplayHeight,
          displayContent.getDisplayPolicy());
      this.mTmpRect = new Rect();
    }

    public float getRotatableScale() {
      return getDefaultScale();
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public float getDefaultScale() {
      return DexSizeCompatController.getInstance().getDefaultScale();
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public float getUserScale() {
      if (isRotatable(getTopOrientationInTask())) {
        return getRotatableScale();
      }
      return super.getUserScale();
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public void setUserScale(float f) {
      if (isRotatable(getTopOrientationInTask())) {
        f = getRotatableScale();
      }
      setUserScaleInternal(f);
    }

    public final void setUserScaleInternal(float f) {
      super.setUserScale(f);
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public boolean shouldUseAspectRatio(int i, int i2, int i3, int i4) {
      return isRotatable(i) && i4 != CompatUtils.getConfigurationOrientation(i2, i3);
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public boolean canUpdateTaskOrientation() {
      return this.mTask.shouldBeVisible(null);
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public int resolveOverrideConfiguration(
        ActivityRecord activityRecord, Configuration configuration) {
      int rotation;
      int compatRotationForOrientation;
      Configuration resolvedOverrideConfiguration =
          activityRecord.getResolvedOverrideConfiguration();
      int requestedConfigurationOrientation = activityRecord.getRequestedConfigurationOrientation();
      if (CoreRune.MT_SUPPORT_SIZE_COMPAT_COORDINATION && CoreRune.FW_ORIENTATION_CONTROL) {
        BoundsCompatRecord boundsCompatRecord = activityRecord.mCompatRecord;
        if (boundsCompatRecord.mSupportsIgnoreOrientationRequest
            && boundsCompatRecord.mIsTaskOrientationMismatched
            && boundsCompatRecord.isCompatModeEnabled()
            && activityRecord.mAtmService.mExt.mOrientationController
                == activityRecord.mCompatRecord.getController()
            && activityRecord.mAtmService.mExt.mOrientationController.useBehindOrientation(
                activityRecord)) {
          requestedConfigurationOrientation =
              activityRecord.mAtmService.mExt.mOrientationController
                  .getPreferredConfigurationOrientation(
                      activityRecord, this.mTask.getConfiguration().orientation);
        }
      }
      boolean z = requestedConfigurationOrientation != 0;
      int i = z ? requestedConfigurationOrientation : configuration.orientation;
      if (z
          && this.mTask.mDisplayContent != null
          && (compatRotationForOrientation =
                  this.mTask
                      .mDisplayContent
                      .getDisplayRotation()
                      .getCompatRotationForOrientation(
                          requestedConfigurationOrientation,
                          (rotation = configuration.windowConfiguration.getRotation())))
              != rotation) {
        resolvedOverrideConfiguration.windowConfiguration.setRotation(compatRotationForOrientation);
      }
      getFrameByOrientation(resolvedOverrideConfiguration.windowConfiguration.getBounds(), i);
      activityRecord
          .getTaskFragment()
          .computeConfigResourceOverrides(
              resolvedOverrideConfiguration, configuration, null, null, this);
      resolvedOverrideConfiguration.screenLayout =
          WindowContainer.computeScreenLayout(
              activityRecord.getConfiguration().screenLayout,
              resolvedOverrideConfiguration.screenWidthDp,
              resolvedOverrideConfiguration.screenHeightDp);
      if (resolvedOverrideConfiguration.screenWidthDp
          == resolvedOverrideConfiguration.screenHeightDp) {
        resolvedOverrideConfiguration.orientation = configuration.orientation;
      }
      Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
      Rect bounds2 = configuration.windowConfiguration.getBounds();
      applyCompatScaleIfNeeded(
          activityRecord,
          activityRecord.getResolvedOverrideBounds(),
          (bounds.width() == bounds2.width() && bounds.height() == bounds2.height())
              ? 1.0f
              : CompatUtils.adjustRoundScale(
                  CompatUtils.getCompatScale(bounds, getViewport(configuration))));
      return 536870912;
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public int updateResolvedBoundsPosition(
        ActivityRecord activityRecord, Configuration configuration) {
      SizeCompatAttributes sizeCompatAttributesOrCreate =
          getSizeCompatAttributesOrCreate(activityRecord);
      if (sizeCompatAttributesOrCreate.getBounds() == null) {
        return 0;
      }
      Rect viewport = getViewport(configuration);
      int max =
          viewport.left
              + Math.max(
                  0,
                  (int) Math.ceil((viewport.width() - r1.width()) * getLeftPositionMultiplier()));
      int max2 =
          viewport.top
              + Math.max(
                  0,
                  (int) Math.ceil((viewport.height() - r1.height()) * getTopPositionMultiplier()));
      Configuration resolvedOverrideConfiguration =
          activityRecord.getResolvedOverrideConfiguration();
      resolvedOverrideConfiguration.windowConfiguration.getBounds().offsetTo(max, max2);
      resolvedOverrideConfiguration.windowConfiguration.getAppBounds().offsetTo(max, max2);
      sizeCompatAttributesOrCreate.updatePosition(max, max2);
      return 536870912;
    }

    public final Rect getViewport(Configuration configuration) {
      return inFullscreenWindowingMode()
          ? getStableBounds(this.mTask.mDisplayContent)
          : configuration.windowConfiguration.getAppBounds();
    }

    public final float getTopPositionMultiplier() {
      if (inFullscreenWindowingMode()) {
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
      return 0.5f;
    }

    public final boolean inFullscreenWindowingMode() {
      Task task = this.mTask;
      return task.mDisplayContent != null && task.inFullscreenWindowingMode();
    }

    @Override // com.android.server.wm.SizeCompatMultiTaskingPolicy
    public Rect getStableBounds(DisplayContent displayContent) {
      Rect stableBounds = super.getStableBounds(displayContent);
      stableBounds.top += this.mTask.getCaptionHeight();
      return stableBounds;
    }

    public final float getMinScale(int i, Rect rect, Rect rect2) {
      boolean z = i == 1;
      DisplayContent displayContent = this.mTask.mDisplayContent;
      int i2 =
          (int)
              (displayContent.mMinSizeOfResizeableTaskDp
                  * (displayContent.getConfiguration().densityDpi / 160.0f));
      Rect rect3 = this.mTmpRect;
      int width = z ? i2 : rect.width();
      if (z) {
        i2 = rect.height();
      }
      rect3.set(0, 0, width, i2);
      return CompatUtils.adjustCeilScale(CompatUtils.getCompatScale(rect2, this.mTmpRect));
    }

    @Override // com.android.server.wm.SizeCompatPolicyCasting
    public SizeCompatDragPolicy asSizeCompatDragPolicy() {
      if (CoreRune.MT_DEX_SIZE_COMPAT_DRAG) {
        return this;
      }
      return null;
    }

    @Override // com.android.server.wm.SizeCompatDragPolicy
    public boolean supportsToFreeformByCornerGesture() {
      return isEnabled() && this.mTask.inFullscreenWindowingMode() && this.mTask.isVisible();
    }

    @Override // com.android.server.wm.SizeCompatDragPolicy
    public boolean ensureDragBounds(Rect rect) {
      float f;
      int i;
      int topOrientationInTask = getTopOrientationInTask();
      if (isRotatable(topOrientationInTask)) {
        f = getRotatableScale();
        i = CompatUtils.getConfigurationOrientation(rect.width(), rect.height());
      } else {
        Rect fullScreenBounds = getFullScreenBounds(topOrientationInTask);
        float adjustRoundScale =
            CompatUtils.adjustRoundScale(CompatUtils.getCompatScale(fullScreenBounds, rect));
        DisplayContent displayContent = this.mTask.mDisplayContent;
        if (displayContent != null) {
          Rect taskBounds =
              getTaskBounds(displayContent.getBounds(), topOrientationInTask, adjustRoundScale);
          float compatScale = CompatUtils.getCompatScale(fullScreenBounds, taskBounds);
          if (rect.width() != taskBounds.width() || rect.height() != taskBounds.height()) {
            taskBounds.offsetTo(rect.left, rect.top);
            rect.set(taskBounds);
            Slog.w(
                "SizeCompatPolicy",
                "ensureDragBounds: userScale=" + compatScale + ", taskScale=" + compatScale);
            adjustRoundScale = compatScale;
          }
        } else {
          Slog.w("SizeCompatPolicy", "ensureDragBounds: Display is null. mTask=" + this.mTask);
        }
        f = adjustRoundScale;
        i = 0;
      }
      setUserScale(f);
      setUserOrientation(i);
      return true;
    }

    @Override // com.android.server.wm.SizeCompatDragPolicy
    public void fillSizeCompatInfoForDrag(SizeCompatInfo sizeCompatInfo) {
      int width;
      int height;
      DisplayContent displayContent = this.mTask.mDisplayContent;
      if (displayContent == null) {
        Slog.w(
            "SizeCompatPolicy", "fillSizeCompatInfoForDrag: Display is null. mTask=" + this.mTask);
        return;
      }
      Rect bounds = displayContent.getBounds();
      int width2 = bounds.width();
      int height2 = bounds.height();
      int configurationOrientation = CompatUtils.getConfigurationOrientation(width2, height2);
      int topOrientationInTask = getTopOrientationInTask();
      boolean isRotatable = isRotatable(topOrientationInTask);
      sizeCompatInfo.setDragMode(isRotatable ? 2 : 1);
      sizeCompatInfo.setDisplaySize(width2, height2);
      Rect bounds2 = this.mTask.getBounds();
      int configurationOrientation2 =
          isRotatable
              ? CompatUtils.getConfigurationOrientation(bounds2.width(), bounds2.height())
              : topOrientationInTask;
      Rect fullScreenBounds = getFullScreenBounds(configurationOrientation2);
      boolean z = isRotatable && configurationOrientation != configurationOrientation2;
      if (z) {
        width = fullScreenBounds.height();
      } else {
        width = fullScreenBounds.width();
      }
      if (z) {
        height = fullScreenBounds.width();
      } else {
        height = fullScreenBounds.height();
      }
      if (isRotatable) {
        this.mTmpRect.set(0, 0, width, height);
        CompatUtils.getScaledBounds(this.mTmpRect, getRotatableScale(), !z);
        sizeCompatInfo.setMinSize(this.mTmpRect.width(), this.mTmpRect.height());
        sizeCompatInfo.setMaxSize(width, height);
        return;
      }
      Rect stableBounds = getStableBounds(this.mTask.mDisplayContent);
      float minScale = getMinScale(topOrientationInTask, stableBounds, fullScreenBounds);
      sizeCompatInfo.setMinSize(
          CompatUtils.applyScale(width, minScale), CompatUtils.applyScale(height, minScale));
      float stableBoundsScale =
          isSmallerContainer(width2, height2, width, height)
              ? getStableBoundsScale(fullScreenBounds, stableBounds)
              : 1.0f;
      sizeCompatInfo.setMaxSize(
          CompatUtils.applyScale(width, stableBoundsScale),
          CompatUtils.applyScale(height, stableBoundsScale));
    }

    @Override // com.android.server.wm.SizeCompatDragPolicy
    public void getTargetDragBounds(Rect rect, Rect rect2, SizeCompatInfo sizeCompatInfo, int i) {
      boolean isDragDexSizeCompatRotatable =
          SizeCompatInfo.isDragDexSizeCompatRotatable(sizeCompatInfo);
      float rotatableScale = isDragDexSizeCompatRotatable ? getRotatableScale() : getDefaultScale();
      this.mTmpRect.set(
          0,
          0,
          CompatUtils.applyScale(sizeCompatInfo.getMaxWidth(), rotatableScale),
          CompatUtils.applyScale(sizeCompatInfo.getMaxHeight(), rotatableScale));
      int i2 = rect2.left;
      int i3 = rect2.top;
      if (!isDragDexSizeCompatRotatable) {
        if (i == 5) {
          i2 += rect2.width() - this.mTmpRect.width();
        }
        i3 += rect2.height() - this.mTmpRect.height();
      } else if (i == 9) {
        i2 += rect2.width() - this.mTmpRect.width();
      }
      this.mTmpRect.offsetTo(i2, i3);
    }

    @Override // com.android.server.wm.SizeCompatDragPolicy
    public FreeformResizeGuide createCompatResizeGuide() {
      return new DexSizeCompatResizeGuide((Context) null, (String) null);
    }
  }
}
