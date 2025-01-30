package com.android.server.wm;

import android.app.TaskInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;
import android.window.WindowContainerTransaction;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class SizeCompatPolicyManager {
    public int mCompatPolicyCount;
    public final SparseArray mDisplayIdsForActiveMode;
    public int mLaunchPolicy;

    public abstract class LazyHolder {
        public static final SizeCompatPolicyManager sManager = new SizeCompatPolicyManager();
    }

    public static SizeCompatPolicyManager get() {
        return LazyHolder.sManager;
    }

    public SizeCompatPolicyManager() {
        this.mLaunchPolicy = 1;
        this.mDisplayIdsForActiveMode = new SparseArray();
    }

    public void setLaunchPolicy(int i) {
        if (i == this.mLaunchPolicy || i < 0 || i > 2) {
            return;
        }
        Slog.d("SizeCompatPolicy", "LaunchPolicy is changed from " + this.mLaunchPolicy + " to " + i);
        this.mLaunchPolicy = i;
    }

    public int getLaunchPolicy() {
        return this.mLaunchPolicy;
    }

    public void setActiveMode(int i, int i2) {
        int intValue = ((Integer) this.mDisplayIdsForActiveMode.get(i, 0)).intValue();
        if (i2 == intValue) {
            return;
        }
        Slog.d("SizeCompatPolicy", "ActiveMode is changed from " + intValue + " to " + i2 + ", displayId=" + i);
        this.mDisplayIdsForActiveMode.put(i, Integer.valueOf(i2));
    }

    public int getActiveMode(int i) {
        return ((Integer) this.mDisplayIdsForActiveMode.get(i, 0)).intValue();
    }

    public boolean isModeEnabled(Task task, int i) {
        int displayId;
        if (this.mLaunchPolicy == 0 || (displayId = task.getDisplayId()) == -1) {
            return false;
        }
        int intValue = ((Integer) this.mDisplayIdsForActiveMode.get(displayId, 0)).intValue();
        return intValue == 0 || intValue == i;
    }

    public boolean shouldCreateCompatPolicy(Task task, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea, int i) {
        return shouldCreateCompatPolicy(task, activityRecord, taskDisplayArea, i, null);
    }

    public boolean shouldCreateCompatPolicy(Task task, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea, int i, Function function) {
        if (this.mLaunchPolicy == 0 || taskDisplayArea == null || taskDisplayArea.getDisplayContent() == null) {
            return false;
        }
        if (task != null && task.getRootActivity() != null) {
            activityRecord = task.getRootActivity();
        }
        if (activityRecord == null || !activityRecord.isActivityTypeStandardOrUndefined()) {
            return false;
        }
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (compatPolicy != null && compatPolicy.getMode() == i) {
            return false;
        }
        if (this.mLaunchPolicy == 2) {
            return true;
        }
        if (function != null) {
            return ((Boolean) function.apply(activityRecord)).booleanValue();
        }
        return (activityRecord.isResizeable() || activityRecord.supportsFreeformInDisplayArea(taskDisplayArea)) ? false : true;
    }

    public void setCompatPolicy(Task task, SizeCompatPolicy sizeCompatPolicy) {
        SizeCompatPolicy sizeCompatPolicy2 = task.mSizeCompatPolicy;
        if (sizeCompatPolicy2 == sizeCompatPolicy) {
            return;
        }
        if (sizeCompatPolicy2 != null) {
            sizeCompatPolicy2.cleanUp();
            this.mCompatPolicyCount--;
        }
        if (sizeCompatPolicy != null) {
            this.mCompatPolicyCount++;
        }
        task.mSizeCompatPolicy = sizeCompatPolicy;
    }

    public boolean isCompatPolicyEnabled() {
        return this.mCompatPolicyCount > 0;
    }

    public SizeCompatPolicy getCompatPolicy(Task task) {
        return getCompatPolicy(task, false);
    }

    public SizeCompatPolicy getCompatPolicy(Task task, boolean z) {
        SizeCompatPolicy sizeCompatPolicy;
        if (task == null || (sizeCompatPolicy = task.mSizeCompatPolicy) == null) {
            return null;
        }
        if (z || sizeCompatPolicy.isEnabled()) {
            return task.mSizeCompatPolicy;
        }
        return null;
    }

    public int applySizeScaleCompatPolicy(ActivityRecord activityRecord, Configuration configuration, int i) {
        Configuration resolvedOverrideConfiguration = activityRecord.getResolvedOverrideConfiguration();
        SizeCompatPolicy compatPolicy = getCompatPolicy(activityRecord.getTask());
        if (compatPolicy != null) {
            return compatPolicy.resolveOverrideConfiguration(activityRecord, configuration) | 0 | compatPolicy.updateResolvedBoundsPosition(activityRecord, configuration);
        }
        SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
        if (sizeCompatAttributes != null) {
            sizeCompatAttributes.cleanUp(null);
        }
        if ((i & 512) == 0 || resolvedOverrideConfiguration.uiMode != 0) {
            return 0;
        }
        resolvedOverrideConfiguration.uiMode = configuration.uiMode;
        return 0;
    }

    public void performDisplayOverrideConfigUpdate(final DisplayContent displayContent, final Configuration configuration) {
        if (isCompatPolicyEnabled()) {
            Rect bounds = configuration.windowConfiguration.getBounds();
            Rect bounds2 = displayContent.getBounds();
            if (bounds.width() == bounds2.height() && bounds.height() == bounds2.width()) {
                displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.SizeCompatPolicyManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SizeCompatPolicyManager.this.lambda$performDisplayOverrideConfigUpdate$0(displayContent, configuration, (Task) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDisplayOverrideConfigUpdate$0(DisplayContent displayContent, Configuration configuration, Task task) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (compatPolicy == null) {
            return;
        }
        compatPolicy.onOrientationChanged(displayContent, configuration);
    }

    public boolean interceptToRotateBoundsIfNeeded(Task task) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (compatPolicy == null) {
            return false;
        }
        return compatPolicy.shouldRotateBounds();
    }

    public void ensureConfiguration(Task task) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (compatPolicy == null) {
            return;
        }
        compatPolicy.ensureConfiguration();
    }

    public void ensureTransaction(Task task, WindowContainerTransaction.Change change, int i) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (compatPolicy == null) {
            return;
        }
        compatPolicy.ensureTransaction(change.getWindowingMode(), change.getConfiguration().windowConfiguration, i);
    }

    public void fillTaskInfo(Task task, TaskInfo taskInfo) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task);
        if (!(compatPolicy != null)) {
            taskInfo.sizeCompatInfo = null;
            return;
        }
        taskInfo.topActivityInSizeCompat = false;
        SizeCompatInfo sizeCompatInfo = taskInfo.sizeCompatInfo;
        if (sizeCompatInfo == null) {
            sizeCompatInfo = new SizeCompatInfo();
            taskInfo.sizeCompatInfo = sizeCompatInfo;
        } else {
            sizeCompatInfo.clear();
        }
        compatPolicy.fillTaskInfo(taskInfo, sizeCompatInfo);
    }

    public void dumpCompatPolicy(Task task, PrintWriter printWriter, String str) {
        SizeCompatPolicy compatPolicy = getCompatPolicy(task, true);
        if (compatPolicy == null) {
            return;
        }
        compatPolicy.dumpInternal(printWriter, str);
    }

    public void dump(PrintWriter printWriter, String str) {
        if (isCompatPolicyEnabled()) {
            printWriter.println();
            printWriter.println(str + "SIZE COMPAT POLICY MANAGER");
            String str2 = str + "  ";
            printWriter.println(str2 + "mLaunchPolicy=" + launchPolicyToString(this.mLaunchPolicy));
            int size = this.mDisplayIdsForActiveMode.size();
            if (size > 0) {
                printWriter.print(str2 + "mActiveMode={ ");
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mDisplayIdsForActiveMode.keyAt(i);
                    int intValue = ((Integer) this.mDisplayIdsForActiveMode.valueAt(i)).intValue();
                    printWriter.print(keyAt);
                    printWriter.print("=");
                    printWriter.print(SizeCompatInfo.sizeCompatModeToString(intValue));
                    printWriter.print(" ");
                }
                printWriter.println("}");
            }
            printWriter.println(str2 + "mCompatPolicyCount=" + this.mCompatPolicyCount);
        }
    }

    public static String launchPolicyToString(int i) {
        if (!CoreRune.SAFE_DEBUG) {
            return Integer.toString(i);
        }
        if (i == 0) {
            return "DISABLED";
        }
        if (i == 1) {
            return "ENABLED_AUTO";
        }
        if (i == 2) {
            return "ENABLED_ALL_APPS";
        }
        return "Unknown(" + i + ")";
    }
}
