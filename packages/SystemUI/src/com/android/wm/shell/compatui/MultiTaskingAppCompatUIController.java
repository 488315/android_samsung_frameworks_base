package com.android.wm.shell.compatui;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class MultiTaskingAppCompatUIController {
    public final AccessibilityManager mAccessibilityManager;
    public final Consumer mCallback;
    public final Context mContext;
    public final CompatUIController mController;
    public boolean mIsRotationFrozen;
    public final MultiTaskingAppCompatUIWindowManager mMultiTaskingAppCompatUIWindowManager;
    public TaskInfo mTaskInfo;
    public int mOrientationPolicy = 31;
    public int mAlignment = 17;
    public final IWindowManager mWindowManager = WindowManagerGlobal.getWindowManagerService();
    public final IActivityTaskManager mActivityTaskManager = ActivityTaskManager.getService();
    public final Handler mHandler = new Handler(Looper.myLooper());

    public MultiTaskingAppCompatUIController(Context context, MultiTaskingAppCompatUIWindowManager multiTaskingAppCompatUIWindowManager, TaskInfo taskInfo, Consumer<CompatUIEvents> consumer, CompatUIController compatUIController) {
        this.mContext = context;
        this.mMultiTaskingAppCompatUIWindowManager = multiTaskingAppCompatUIWindowManager;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mTaskInfo = taskInfo;
        this.mCallback = consumer;
        this.mController = compatUIController;
    }

    public static boolean isAlignedVertically(TaskInfo taskInfo) {
        return taskInfo.appCompatTaskInfo.topActivityBounds.width() > taskInfo.appCompatTaskInfo.topActivityBounds.height();
    }

    public final boolean isInSizeCompat() {
        return (!this.mTaskInfo.appCompatTaskInfo.isTopActivityInSizeCompat() || this.mTaskInfo.appCompatTaskInfo.hasMinAspectRatioOverride() || this.mTaskInfo.appCompatTaskInfo.isRotationCompatModeEnabled() || this.mTaskInfo.appCompatTaskInfo.topActivityInDisplayCompat) ? false : true;
    }

    public final void setMultiTaskingAppCompatAlignment(int i) {
        int i2 = this.mAlignment;
        int i3 = i2 & 112;
        int i4 = i2 & 7;
        if (i == 48) {
            this.mAlignment = (i2 & 80) == 80 ? i4 | 16 : i4 | 48;
        } else if (i == 80) {
            this.mAlignment = (i2 & 48) == 48 ? i4 | 16 : i4 | 80;
        } else if (i == 3) {
            this.mAlignment = (i2 & 5) == 5 ? i3 | 1 : i3 | 3;
        } else {
            if (i != 5) {
                Log.i("MultiTaskingAppCompatUIController", "Nothing to do");
                return;
            }
            this.mAlignment = (i2 & 3) == 3 ? i3 | 1 : i3 | 5;
        }
        try {
            this.mActivityTaskManager.setAppCompatAlignment(this.mAlignment);
        } catch (RemoteException e) {
            Log.e("MultiTaskingAppCompatUIController", "Failed to set app compat alignment", e);
        }
    }

    public final void setOrientationControlPolicy(int i) {
        this.mOrientationPolicy = i;
        try {
            IActivityTaskManager iActivityTaskManager = this.mActivityTaskManager;
            int callingUserId = UserHandle.getCallingUserId();
            ComponentName componentName = this.mTaskInfo.topActivity;
            iActivityTaskManager.setOrientationControlPolicy(callingUserId, componentName != null ? componentName.getPackageName() : null, i);
            IWindowManager iWindowManager = this.mWindowManager;
            iWindowManager.setWindowingMode(0, iWindowManager.getWindowingMode(0));
        } catch (RemoteException e) {
            Log.e("MultiTaskingAppCompatUIController", "enableOrientationControlPolicy failed", e);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MultiTaskingAppCompatUIController{taskId=");
        sb.append(this.mTaskInfo.taskId);
        sb.append("(");
        sb.append(this.mTaskInfo.baseActivity.getPackageName());
        sb.append("), taskConfig=");
        sb.append(this.mTaskInfo.getConfiguration());
        sb.append(", inSizeCompat=");
        sb.append(isInSizeCompat());
        sb.append(", inDisplayCompat=");
        sb.append(this.mTaskInfo.appCompatTaskInfo.topActivityInDisplayCompat);
        sb.append(", rotationFrozen=");
        sb.append(this.mIsRotationFrozen);
        sb.append(", orientationPolicy=");
        sb.append(this.mOrientationPolicy);
        sb.append(", activityBounds=");
        sb.append(this.mTaskInfo.appCompatTaskInfo.topActivityBounds);
        sb.append(", taskOrientation=");
        sb.append(this.mTaskInfo.getConfiguration().orientation);
        sb.append(", contextOrientation=");
        sb.append(this.mContext.getResources().getConfiguration().orientation);
        sb.append(", alignment=0x");
        sb.append(Integer.toHexString(this.mAlignment));
        sb.append(", resizeable=");
        sb.append(this.mTaskInfo.isResizeable);
        sb.append(", visible=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mTaskInfo.isVisible, "}");
    }
}
