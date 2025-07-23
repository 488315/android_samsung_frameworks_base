package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import com.android.wm.shell.common.DisplayLayout;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskVisibility {
    public final Context mContext;
    public DisplayLayout mDisplayLayout;
    public final SparseArray mRunningTaskInfo = new SparseArray();
    public boolean mSupportOnlyTwoUpMode;

    public TaskVisibility(Context context) {
        this.mContext = context;
    }

    public final Rect getTaskBounds(int i) {
        Rect rect = new Rect();
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mRunningTaskInfo.get(i);
        if (runningTaskInfo != null) {
            rect.set(runningTaskInfo.configuration.windowConfiguration.getBounds());
        }
        return rect;
    }

    public final boolean isMultiSplit() {
        if (CoreRune.MW_MULTI_SPLIT) {
            return isTaskVisible(12);
        }
        return false;
    }

    public final boolean isTaskVisible(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mRunningTaskInfo.get(i);
        return runningTaskInfo != null && runningTaskInfo.isVisible;
    }

    public final boolean isTwoUp() {
        return CoreRune.MW_MULTI_SPLIT ? isTaskVisible(4) && !isMultiSplit() : isTaskVisible(4);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TaskVisibility{");
        int size = this.mRunningTaskInfo.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mRunningTaskInfo.keyAt(i);
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mRunningTaskInfo.valueAt(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt + "=[TaskId=" + runningTaskInfo.taskId);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
