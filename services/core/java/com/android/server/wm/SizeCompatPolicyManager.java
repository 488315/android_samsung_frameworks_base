package com.android.server.wm;

import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SizeCompatPolicyManager {
    public int mCompatPolicyCount;
    public int mLaunchPolicy = 1;
    public final SparseArray mDisplayIdsForActiveMode = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final SizeCompatPolicyManager sManager = new SizeCompatPolicyManager();
    }

    public static void ensureConfiguration(Task task) {
        DexSizeCompatController.DexSizeCompatPolicy compatPolicy = getCompatPolicy(task, false);
        if (compatPolicy == null) {
            return;
        }
        Task task2 = compatPolicy.mTask;
        if (task2.inFullscreenWindowingMode()) {
            return;
        }
        int topOrientationInTask = compatPolicy.getTopOrientationInTask();
        if (compatPolicy.mUserOrientation != 0 && topOrientationInTask != 0) {
            compatPolicy.mUserOrientation = 0;
        }
        DisplayContent displayContent = task2.mDisplayContent;
        if (displayContent == null) {
            Slog.w(
                    "SizeCompatPolicy",
                    "ensureConfiguration: DisplayContent is null. task=" + task2);
            return;
        }
        Rect bounds = displayContent.getBounds();
        Rect taskBounds =
                compatPolicy.getTaskBounds(
                        bounds,
                        topOrientationInTask,
                        DexSizeCompatController.DexSizeCompatPolicy.isRotatable(
                                        compatPolicy.getTopOrientationInTask())
                                ? DexSizeCompatController.LazyHolder.sInstance.mDefaultScale
                                : compatPolicy.mUserScale,
                        true);
        int width = taskBounds.width();
        int height = taskBounds.height();
        Rect bounds2 = task2.getBounds();
        if (width == bounds2.width() && height == bounds2.height()) {
            return;
        }
        if (taskBounds.isEmpty() || (width == bounds.width() && height == bounds.height())) {
            task2.setWindowingMode(1);
            return;
        }
        if (!task2.inFreeformWindowingMode()) {
            task2.setWindowingMode(5);
        }
        taskBounds.offsetTo(bounds2.left, bounds2.top);
        task2.setBounds(taskBounds);
    }

    public static DexSizeCompatController.DexSizeCompatPolicy getCompatPolicy(
            Task task, boolean z) {
        DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy;
        if (task == null || (dexSizeCompatPolicy = task.mSizeCompatPolicy) == null) {
            return null;
        }
        if (z || dexSizeCompatPolicy.isEnabled()) {
            return task.mSizeCompatPolicy;
        }
        return null;
    }

    public final void setCompatPolicy(
            Task task, DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy) {
        final DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy2 =
                task.mSizeCompatPolicy;
        if (dexSizeCompatPolicy2 == dexSizeCompatPolicy) {
            return;
        }
        if (dexSizeCompatPolicy2 != null) {
            Consumer consumer =
                    new Consumer() { // from class:
                                     // com.android.server.wm.SizeCompatPolicy$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy3 =
                                    DexSizeCompatController.DexSizeCompatPolicy.this;
                            dexSizeCompatPolicy3.getClass();
                            SizeCompatAttributes sizeCompatAttributes =
                                    ((ActivityRecord) obj).mSizeCompatAttributes;
                            if (sizeCompatAttributes != null) {
                                sizeCompatAttributes.cleanUp(dexSizeCompatPolicy3);
                            }
                        }
                    };
            Task task2 = dexSizeCompatPolicy2.mTask;
            task2.forAllActivities(consumer);
            task2.mSizeCompatPolicy = null;
            this.mCompatPolicyCount--;
        }
        if (dexSizeCompatPolicy != null) {
            this.mCompatPolicyCount++;
        }
        task.mSizeCompatPolicy = dexSizeCompatPolicy;
    }
}
