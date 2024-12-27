package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.view.DisplayInfo;

import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.SizeCompatInfo;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexSizeCompatController {
    public float mDefaultScale = 0.72f;
    public float mAspectRatioScale = 0.72f;
    public final LaunchParamsController.LaunchParams mTmpParams =
            new LaunchParamsController.LaunchParams();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexSizeCompatPolicy {
        public boolean mEnabled;
        public final int mHeight;
        public int mLastTaskOrientation;
        public final Rect[] mNonDecorInsets;
        public final Rect[] mStableInsets;
        public final Task mTask;
        public final Rect mTmpContainingBounds;
        public final Rect mTmpFullScreenBounds;
        public final Rect mTmpRect;
        public final Rect mTmpStableBounds;
        public int mUserOrientation;
        public float mUserScale;
        public final int mWidth;

        public DexSizeCompatPolicy(Task task, DisplayContent displayContent) {
            int i = displayContent.mBaseDisplayWidth;
            int i2 = displayContent.mBaseDisplayHeight;
            DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
            this.mEnabled = true;
            this.mLastTaskOrientation = 0;
            this.mNonDecorInsets = new Rect[4];
            this.mStableInsets = new Rect[4];
            this.mTask = task;
            this.mWidth = i;
            this.mHeight = i2;
            int i3 = 0;
            while (i3 < 4) {
                this.mNonDecorInsets[i3] = new Rect();
                this.mStableInsets[i3] = new Rect();
                if (displayPolicy != null) {
                    boolean z = i3 == 1 || i3 == 3;
                    DisplayPolicy.DecorInsets.Info decorInsetsInfo =
                            displayPolicy.getDecorInsetsInfo(
                                    i3,
                                    z ? this.mHeight : this.mWidth,
                                    z ? this.mWidth : this.mHeight);
                    this.mNonDecorInsets[i3].set(decorInsetsInfo.mNonDecorInsets);
                    this.mStableInsets[i3].set(decorInsetsInfo.mConfigInsets);
                }
                i3++;
            }
            this.mUserOrientation = 0;
            this.mTmpStableBounds = new Rect();
            this.mTmpFullScreenBounds = new Rect();
            this.mTmpContainingBounds = new Rect();
            setUserScale(LazyHolder.sInstance.mDefaultScale);
            this.mTmpRect = new Rect();
        }

        public static boolean isRotatable(int i) {
            return i == 0;
        }

        public final void ensureDragBounds(Rect rect) {
            float f;
            int i;
            int topOrientationInTask = getTopOrientationInTask();
            if (isRotatable(topOrientationInTask)) {
                f = LazyHolder.sInstance.mDefaultScale;
                i = CompatUtils.getConfigurationOrientation(rect.width(), rect.height());
            } else {
                getFrameByOrientation(topOrientationInTask, this.mTmpFullScreenBounds);
                Rect rect2 = this.mTmpFullScreenBounds;
                float adjustRoundScale =
                        CompatUtils.adjustRoundScale(CompatUtils.getCompatScale(rect2, rect));
                Task task = this.mTask;
                DisplayContent displayContent = task.mDisplayContent;
                if (displayContent != null) {
                    Rect taskBounds =
                            getTaskBounds(
                                    displayContent.getBounds(),
                                    topOrientationInTask,
                                    adjustRoundScale,
                                    true);
                    float compatScale = CompatUtils.getCompatScale(rect2, taskBounds);
                    if (rect.width() != taskBounds.width()
                            || rect.height() != taskBounds.height()) {
                        taskBounds.offsetTo(rect.left, rect.top);
                        rect.set(taskBounds);
                        Slog.w(
                                "SizeCompatPolicy",
                                "ensureDragBounds: userScale="
                                        + compatScale
                                        + ", taskScale="
                                        + compatScale);
                        adjustRoundScale = compatScale;
                    }
                } else {
                    Slog.w("SizeCompatPolicy", "ensureDragBounds: Display is null. mTask=" + task);
                }
                f = adjustRoundScale;
                i = 0;
            }
            setUserScale(f);
            this.mUserOrientation = i;
        }

        public final void fillSizeCompatInfoForDrag(SizeCompatInfo sizeCompatInfo) {
            Task task = this.mTask;
            DisplayContent displayContent = task.mDisplayContent;
            if (displayContent == null) {
                Slog.w(
                        "SizeCompatPolicy",
                        "fillSizeCompatInfoForDrag: Display is null. mTask=" + task);
                return;
            }
            Rect bounds = displayContent.getBounds();
            int width = bounds.width();
            int height = bounds.height();
            int configurationOrientation = CompatUtils.getConfigurationOrientation(width, height);
            int topOrientationInTask = getTopOrientationInTask();
            boolean isRotatable = isRotatable(topOrientationInTask);
            sizeCompatInfo.setDragMode(isRotatable ? 2 : 1);
            sizeCompatInfo.setDisplaySize(width, height);
            Rect bounds2 = task.getBounds();
            int configurationOrientation2 =
                    isRotatable
                            ? CompatUtils.getConfigurationOrientation(
                                    bounds2.width(), bounds2.height())
                            : topOrientationInTask;
            getFrameByOrientation(configurationOrientation2, this.mTmpFullScreenBounds);
            Rect rect = this.mTmpFullScreenBounds;
            boolean z = isRotatable && configurationOrientation != configurationOrientation2;
            int height2 = z ? rect.height() : rect.width();
            int width2 = z ? rect.width() : rect.height();
            if (isRotatable) {
                this.mTmpRect.set(0, 0, height2, width2);
                CompatUtils.getScaledBounds(this.mTmpRect, LazyHolder.sInstance.mDefaultScale, !z);
                sizeCompatInfo.setMinSize(this.mTmpRect.width(), this.mTmpRect.height());
                sizeCompatInfo.setMaxSize(height2, width2);
                return;
            }
            Rect stableBounds = getStableBounds(task.mDisplayContent);
            float minScale = getMinScale(topOrientationInTask, stableBounds, rect);
            sizeCompatInfo.setMinSize(
                    CompatUtils.applyScale(height2, minScale),
                    CompatUtils.applyScale(width2, minScale));
            float adjustFloorScale =
                    (height2 > width || width2 > height)
                            ? CompatUtils.adjustFloorScale(
                                    CompatUtils.getCompatScale(rect, stableBounds) - 0.01f)
                            : 1.0f;
            sizeCompatInfo.setMaxSize(
                    CompatUtils.applyScale(height2, adjustFloorScale),
                    CompatUtils.applyScale(width2, adjustFloorScale));
        }

        public final void getFrameByOrientation(int i, Rect rect) {
            int i2 = this.mWidth;
            int i3 = this.mHeight;
            int max = Math.max(i2, i3);
            int min = Math.min(i2, i3);
            boolean z = i == 2;
            int i4 = z ? max : min;
            if (z) {
                max = min;
            }
            rect.set(0, 0, i4, max);
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

        public final Rect getStableBounds(DisplayContent displayContent) {
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            this.mTmpStableBounds.set(
                    displayContent.mDisplayPolicy.getDecorInsetsInfo(
                                    displayInfo.rotation,
                                    displayInfo.logicalWidth,
                                    displayInfo.logicalHeight)
                            .mConfigFrame);
            Rect rect = this.mTmpStableBounds;
            rect.top = this.mTask.getCaptionHeight() + rect.top;
            return rect;
        }

        public final Rect getTaskBounds(Rect rect, int i, float f, boolean z) {
            int i2;
            if (f == 1.0f) {
                this.mTmpContainingBounds.set(rect);
                return this.mTmpContainingBounds;
            }
            DisplayContent displayContent = this.mTask.mDisplayContent;
            if (displayContent != null) {
                rect = getStableBounds(displayContent);
            }
            int width = rect.width();
            int height = rect.height();
            if (i != 0) {
                i2 = i;
            } else {
                i2 = this.mUserOrientation;
                if (i2 == 0) {
                    i2 = CompatUtils.getConfigurationOrientation(width, height);
                }
            }
            getFrameByOrientation(i2, this.mTmpFullScreenBounds);
            Rect rect2 = this.mTmpFullScreenBounds;
            boolean z2 =
                    isRotatable(i) && i2 != CompatUtils.getConfigurationOrientation(width, height);
            this.mTmpContainingBounds.set(
                    0, 0, z2 ? rect2.height() : rect2.width(), z2 ? rect2.width() : rect2.height());
            CompatUtils.getScaledBounds(this.mTmpContainingBounds, f, z2);
            if (z && !z2) {
                int width2 = this.mTmpContainingBounds.width();
                int height2 = this.mTmpContainingBounds.height();
                if (width2 > width || height2 > height) {
                    float adjustFloorScale =
                            CompatUtils.adjustFloorScale(
                                    CompatUtils.getCompatScale(rect2, rect) - 0.01f);
                    this.mUserScale = adjustFloorScale;
                    return getTaskBounds(rect, i, adjustFloorScale, false);
                }
            }
            return this.mTmpContainingBounds;
        }

        public final int getTopOrientationInTask() {
            Task task = this.mTask;
            if (task.shouldBeVisible(null)) {
                this.mLastTaskOrientation = task.getRequestedConfigurationOrientationInChildren();
            }
            return this.mLastTaskOrientation;
        }

        public final boolean isEnabled() {
            Task task;
            int displayId;
            int intValue;
            if (this.mEnabled) {
                SizeCompatPolicyManager sizeCompatPolicyManager =
                        SizeCompatPolicyManager.LazyHolder.sManager;
                if (sizeCompatPolicyManager.mLaunchPolicy != 0
                        && (displayId = (task = this.mTask).getDisplayId()) != -1
                        && (((intValue =
                                                        ((Integer)
                                                                        sizeCompatPolicyManager
                                                                                .mDisplayIdsForActiveMode
                                                                                .get(displayId, 0))
                                                                .intValue())
                                                == 0
                                        || intValue == 1)
                                && (task.inFullscreenWindowingMode()
                                        || task.inFreeformWindowingMode()))) {
                    return true;
                }
            }
            return false;
        }

        public final void setFreeformConfiguration(
                Configuration configuration, Rect rect, BiConsumer biConsumer) {
            Task task = this.mTask;
            Rect bounds = task.getBounds();
            Rect taskBounds =
                    getTaskBounds(
                            rect,
                            getTopOrientationInTask(),
                            isRotatable(getTopOrientationInTask())
                                    ? LazyHolder.sInstance.mDefaultScale
                                    : this.mUserScale,
                            true);
            if (task.inFreeformWindowingMode()) {
                biConsumer.accept(bounds, taskBounds);
            } else {
                CompatUtils.adjustBoundsToCenter(rect, taskBounds);
            }
            configuration.windowConfiguration.setBounds(taskBounds);
            configuration.windowConfiguration.setWindowingMode(5);
        }

        public final void setUserScale(float f) {
            if (isRotatable(getTopOrientationInTask())) {
                f = LazyHolder.sInstance.mDefaultScale;
            }
            this.mUserScale = CompatUtils.adjustRoundScale(f);
        }

        public final boolean supportsSandbox(ActivityRecord activityRecord) {
            TaskDisplayArea taskDisplayArea;
            Task task = this.mTask;
            Rect bounds = task.getBounds();
            if (bounds.isEmpty()) {
                return false;
            }
            SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
            return (sizeCompatAttributes != null && sizeCompatAttributes.hasBounds())
                    || !task.inFullscreenWindowingMode()
                    || (taskDisplayArea = task.getTaskDisplayArea()) == null
                    || !taskDisplayArea.getBounds().equals(bounds);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final DexSizeCompatController sInstance = new DexSizeCompatController();
    }

    public static DexSizeCompatPolicy createCompatPolicy(
            Task task, TaskDisplayArea taskDisplayArea) {
        DisplayContent displayContent = taskDisplayArea.getDisplayContent();
        if (displayContent != null) {
            DexSizeCompatPolicy dexSizeCompatPolicy = new DexSizeCompatPolicy(task, displayContent);
            SizeCompatPolicyManager.LazyHolder.sManager.setCompatPolicy(task, dexSizeCompatPolicy);
            return dexSizeCompatPolicy;
        }
        Slog.w(
                "SizeCompatPolicy",
                "createCompatPolicy: Display is null, task=" + task + ", tda=" + taskDisplayArea);
        return null;
    }

    public static DexSizeCompatPolicy getCompatPolicy(Task task) {
        SizeCompatPolicyManager.LazyHolder.sManager.getClass();
        DexSizeCompatPolicy compatPolicy = SizeCompatPolicyManager.getCompatPolicy(task, false);
        if (compatPolicy != null) {
            return compatPolicy;
        }
        return null;
    }

    public static boolean shouldCreateCompatPolicy(
            Task task, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea) {
        SizeCompatPolicyManager sizeCompatPolicyManager =
                SizeCompatPolicyManager.LazyHolder.sManager;
        if (sizeCompatPolicyManager.mLaunchPolicy == 0
                || taskDisplayArea.getDisplayContent() == null) {
            return false;
        }
        if (task != null && task.getRootActivity(true, false) != null) {
            activityRecord = task.getRootActivity(true, false);
        }
        if (activityRecord != null
                && activityRecord.isActivityTypeStandardOrUndefined()
                && SizeCompatPolicyManager.getCompatPolicy(task, false) == null) {
            return sizeCompatPolicyManager.mLaunchPolicy == 2
                    || !(activityRecord.isResizeable(true)
                            || activityRecord.supportsFreeformInDisplayArea(taskDisplayArea));
        }
        return false;
    }
}
