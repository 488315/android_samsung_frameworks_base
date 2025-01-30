package com.android.server.wm;

import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.view.DisplayInfo;
import com.android.server.wm.LaunchParamsController;
import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public abstract class SizeCompatMultiTaskingPolicy extends SizeCompatPolicy {
    public static final boolean DEBUG_CONFIG = CoreRune.SAFE_DEBUG;
    public final Rect mTmpContainingBounds;
    public final Rect mTmpFullScreenBounds;
    public final Rect mTmpStableBounds;
    public int mUserOrientation;
    public float mUserScale;

    public abstract boolean canUpdateTaskOrientation();

    public abstract float getDefaultScale();

    public final boolean isSmallerContainer(int i, int i2, int i3, int i4) {
        return i3 > i || i4 > i2;
    }

    public abstract boolean shouldUseAspectRatio(int i, int i2, int i3, int i4);

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsFreeform() {
        return true;
    }

    public SizeCompatMultiTaskingPolicy(Task task, int i, int i2, DisplayPolicy displayPolicy) {
        super(task, i, i2, displayPolicy);
        this.mUserOrientation = 0;
        this.mTmpStableBounds = new Rect();
        this.mTmpFullScreenBounds = new Rect();
        this.mTmpContainingBounds = new Rect();
        setUserScale(getDefaultScale());
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public void ensureConfiguration() {
        if (this.mTask.inFullscreenWindowingMode()) {
            return;
        }
        int topOrientationInTask = getTopOrientationInTask();
        if (this.mUserOrientation != 0 && topOrientationInTask != 0) {
            setUserOrientation(0);
        }
        DisplayContent displayContent = this.mTask.mDisplayContent;
        if (displayContent == null) {
            Slog.w("SizeCompatPolicy", "ensureConfiguration: DisplayContent is null. task=" + this.mTask);
            return;
        }
        Rect bounds = displayContent.getBounds();
        Rect taskBounds = getTaskBounds(bounds, topOrientationInTask);
        int width = taskBounds.width();
        int height = taskBounds.height();
        Rect bounds2 = this.mTask.getBounds();
        if (width == bounds2.width() && height == bounds2.height()) {
            return;
        }
        if (taskBounds.isEmpty() || (width == bounds.width() && height == bounds.height())) {
            if (!supportsFullScreen()) {
                logWindowingModeWarning("ensureConfiguration", this.mTask, 1);
            }
            this.mTask.setWindowingMode(1);
        } else {
            if (!this.mTask.inFreeformWindowingMode()) {
                if (!supportsFreeform()) {
                    logWindowingModeWarning("ensureConfiguration", this.mTask, 5);
                }
                this.mTask.setWindowingMode(5);
            }
            taskBounds.offsetTo(bounds2.left, bounds2.top);
            this.mTask.setBounds(taskBounds);
        }
        if (DEBUG_CONFIG) {
            Task task = this.mTask;
            logDebugConfig("ensureConfiguration", task, task.getConfiguration().windowConfiguration);
        }
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public void ensureTransaction(int i, WindowConfiguration windowConfiguration, int i2) {
        SizeCompatDragPolicy asSizeCompatDragPolicy;
        Task task = this.mTask;
        if (task.mDisplayContent == null) {
            Slog.w("SizeCompatPolicy", "ensureTransaction: DisplayContent is null. task=" + this.mTask);
            return;
        }
        int i3 = 0;
        float f = 1.0f;
        if (i >= 0 && i != task.getWindowingMode()) {
            if (i != 1 || !supportsFullScreen()) {
                if (i == 5 && supportsFreeform()) {
                    f = getDefaultScale();
                } else {
                    logWindowingModeWarning("ensureTransaction", this.mTask, i);
                    setEnabled(false);
                    return;
                }
            }
            i3 = this.mUserOrientation;
        } else if (supportsFreeform() && this.mTask.inFreeformWindowingMode()) {
            if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && i2 != 0 && supportsDragResizable() && (asSizeCompatDragPolicy = asSizeCompatDragPolicy()) != null) {
                asSizeCompatDragPolicy.ensureDragBounds(windowConfiguration.getBounds());
            }
            f = this.mUserScale;
            i3 = this.mUserOrientation;
        }
        setUserScale(f);
        setUserOrientation(i3);
        if (DEBUG_CONFIG) {
            logDebugConfig("ensureTransaction", this.mTask, windowConfiguration);
        }
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public void fillTaskInfo(TaskInfo taskInfo, SizeCompatInfo sizeCompatInfo) {
        super.fillTaskInfo(taskInfo, sizeCompatInfo);
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && supportsDragResizable()) {
            asSizeCompatDragPolicy().fillSizeCompatInfoForDrag(sizeCompatInfo);
        }
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public boolean shouldRotateBounds() {
        return this.mTask.inFreeformWindowingMode() && supportsFreeform();
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public void onOrientationChanged(DisplayContent displayContent, Configuration configuration) {
        setUserOrientation(0);
        Configuration requestedOverrideConfiguration = this.mTask.getRequestedOverrideConfiguration();
        if (shouldRotateBounds()) {
            final Rect bounds = displayContent.getBounds();
            final Rect bounds2 = configuration.windowConfiguration.getBounds();
            setFreeformConfiguration(requestedOverrideConfiguration, bounds2, new BiConsumer() { // from class: com.android.server.wm.SizeCompatMultiTaskingPolicy$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SizeCompatMultiTaskingPolicy.lambda$onOrientationChanged$0(bounds, bounds2, (Rect) obj, (Rect) obj2);
                }
            });
        }
        if (DEBUG_CONFIG) {
            logDebugConfig("onOrientationChanged", this.mTask, requestedOverrideConfiguration.windowConfiguration);
        }
    }

    public static /* synthetic */ void lambda$onOrientationChanged$0(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        int i;
        int i2;
        rect4.offsetTo(rect3.left, rect3.top);
        int width = rect.width();
        int width2 = rect2.width();
        int width3 = rect4.width();
        if (width3 >= width2) {
            i = rect2.left + ((width2 - width3) >> 1);
        } else {
            int i3 = rect4.right;
            int i4 = rect2.left;
            if (i3 < i4) {
                i = i4;
            } else {
                int i5 = rect4.left;
                int i6 = rect2.right;
                if (i5 > i6) {
                    i = i6 - width3;
                } else {
                    i = (int) (i5 * (width2 / width));
                }
            }
        }
        int height = rect.height();
        int height2 = rect2.height();
        int height3 = rect4.height();
        if (height3 >= height2) {
            i2 = rect2.top + ((height2 - height3) >> 1);
        } else {
            int i7 = rect4.bottom;
            int i8 = rect2.top;
            if (i7 < i8) {
                i2 = i8;
            } else {
                int i9 = rect4.top;
                int i10 = rect2.bottom;
                if (i9 > i10) {
                    i2 = i10 - height3;
                } else {
                    i2 = (int) (i9 * (height2 / height));
                }
            }
        }
        if (i < rect2.left || i2 < rect2.top || i + width3 > rect2.right || i2 + height3 > rect2.bottom) {
            i = (width2 - width3) >> 1;
            i2 = (height2 - height3) >> 1;
        }
        rect4.offsetTo(i, i2);
    }

    public Rect getTaskBounds(Rect rect, int i) {
        return getTaskBounds(rect, i, getUserScale());
    }

    public Rect getTaskBounds(Rect rect, int i, float f) {
        return getTaskBounds(rect, i, f, true);
    }

    public final Rect getTaskBounds(Rect rect, int i, float f, boolean z) {
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
        int desiredOrientation = getDesiredOrientation(i, width, height);
        Rect fullScreenBounds = getFullScreenBounds(desiredOrientation);
        boolean shouldUseAspectRatio = shouldUseAspectRatio(i, width, height, desiredOrientation);
        this.mTmpContainingBounds.set(0, 0, shouldUseAspectRatio ? fullScreenBounds.height() : fullScreenBounds.width(), shouldUseAspectRatio ? fullScreenBounds.width() : fullScreenBounds.height());
        CompatUtils.getScaledBounds(this.mTmpContainingBounds, f, shouldUseAspectRatio);
        if (z && !shouldUseAspectRatio && isSmallerContainer(width, height, this.mTmpContainingBounds.width(), this.mTmpContainingBounds.height())) {
            float stableBoundsScale = getStableBoundsScale(fullScreenBounds, rect);
            this.mUserScale = stableBoundsScale;
            return getTaskBounds(rect, i, stableBoundsScale, false);
        }
        return this.mTmpContainingBounds;
    }

    public int getDesiredOrientation(int i, int i2, int i3) {
        if (i != 0) {
            return i;
        }
        int i4 = this.mUserOrientation;
        return i4 != 0 ? i4 : CompatUtils.getConfigurationOrientation(i2, i3);
    }

    public final float getStableBoundsScale(Rect rect, Rect rect2) {
        return CompatUtils.adjustFloorScale(CompatUtils.getCompatScale(rect, rect2) - 0.01f);
    }

    @Override // com.android.server.wm.SizeCompatPolicy
    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("UserScale=" + getUserScale());
        printWriter.print(", UserOrientation=" + CompatUtils.orientationToString(getUserOrientation()));
        printWriter.println();
    }

    public void setFreeformConfiguration(Configuration configuration, Rect rect, BiConsumer biConsumer) {
        Rect bounds = this.mTask.getBounds();
        Rect taskBounds = getTaskBounds(rect, getTopOrientationInTask());
        if (!this.mTask.inFreeformWindowingMode()) {
            CompatUtils.adjustBoundsToCenter(rect, taskBounds);
        } else if (biConsumer != null) {
            biConsumer.accept(bounds, taskBounds);
        } else {
            taskBounds.offsetTo(bounds.left, bounds.top);
        }
        configuration.windowConfiguration.setBounds(taskBounds);
        configuration.windowConfiguration.setWindowingMode(5);
        if (supportsFreeform()) {
            return;
        }
        logWindowingModeWarning("setFreeformConfiguration", this.mTask, 5);
    }

    public int getTopOrientationInTask() {
        if (canUpdateTaskOrientation()) {
            this.mLastTaskOrientation = this.mTask.getRequestedConfigurationOrientationInChildren();
        }
        return this.mLastTaskOrientation;
    }

    public float getUserScale() {
        return this.mUserScale;
    }

    public void setUserScale(float f) {
        this.mUserScale = CompatUtils.adjustRoundScale(f);
    }

    public int getUserOrientation() {
        return this.mUserOrientation;
    }

    public void setUserOrientation(int i) {
        this.mUserOrientation = i;
    }

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsSandboxDisplay(ActivityRecord activityRecord) {
        return supportsSandbox(activityRecord);
    }

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsSandboxViewBounds(ActivityRecord activityRecord) {
        return supportsSandbox(activityRecord);
    }

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsSandboxMotionEvent(ActivityRecord activityRecord) {
        return supportsSandbox(activityRecord);
    }

    @Override // com.android.server.wm.SizeCompatPolicySupports
    public boolean supportsMockFullScreen() {
        return !this.mTask.inFullscreenWindowingMode();
    }

    public final boolean supportsSandbox(ActivityRecord activityRecord) {
        TaskDisplayArea taskDisplayArea;
        Rect bounds = this.mTask.getBounds();
        if (bounds.isEmpty()) {
            return false;
        }
        SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
        return (sizeCompatAttributes != null && sizeCompatAttributes.hasBounds()) || !this.mTask.inFullscreenWindowingMode() || (taskDisplayArea = this.mTask.getTaskDisplayArea()) == null || !taskDisplayArea.getBounds().equals(bounds);
    }

    public boolean supportsDragResizable() {
        return this.mTask.inFreeformWindowingMode();
    }

    public Rect getFullScreenBounds(int i) {
        getFrameByOrientation(this.mTmpFullScreenBounds, i);
        return this.mTmpFullScreenBounds;
    }

    public Rect getStableBounds(DisplayContent displayContent) {
        DisplayInfo displayInfo = displayContent.getDisplayInfo();
        this.mTmpStableBounds.set(displayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mConfigFrame);
        return this.mTmpStableBounds;
    }

    public static void logDebugConfig(String str, Task task, WindowConfiguration windowConfiguration) {
        Slog.d("SizeCompatPolicy", str + ": bounds=" + windowConfiguration.getBounds() + ", mode=" + WindowConfiguration.windowingModeToString(windowConfiguration.getWindowingMode()) + ", task=" + task);
    }

    public static void logDebugConfig(String str, Task task, LaunchParamsController.LaunchParams launchParams) {
        Slog.d("SizeCompatPolicy", str + ": bounds=" + launchParams.mBounds + ", mode=" + WindowConfiguration.windowingModeToString(launchParams.mWindowingMode) + ", task=" + task);
    }

    public static void logWindowingModeWarning(String str, Task task, int i) {
        Slog.w("SizeCompatPolicy", str + ": Unsupported windowing mode, mode=" + WindowConfiguration.windowingModeToString(i) + ", task=" + task);
    }
}
