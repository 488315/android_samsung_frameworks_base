package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WorkLockActivityController {
    public final Context mContext;
    public final IActivityTaskManager mIatm;
    public final C15191 mLockListener;
    public final UserTracker mUserTracker;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.WorkLockActivityController$1, com.android.systemui.shared.system.TaskStackChangeListener] */
    public WorkLockActivityController(Context context, UserTracker userTracker, TaskStackChangeListeners taskStackChangeListeners, IActivityTaskManager iActivityTaskManager) {
        ?? r0 = new TaskStackChangeListener() { // from class: com.android.systemui.keyguard.WorkLockActivityController.1
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
                String str;
                int i2;
                WorkLockActivityController workLockActivityController = WorkLockActivityController.this;
                workLockActivityController.getClass();
                ComponentName componentName = runningTaskInfo.baseActivity;
                String packageName = componentName != null ? componentName.getPackageName() : "";
                Intent intent = new Intent("android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER");
                Context context2 = workLockActivityController.mContext;
                Intent addFlags = intent.setComponent(new ComponentName(context2, (Class<?>) WorkLockActivity.class)).putExtra("android.intent.extra.USER_ID", i).putExtra("android.intent.extra.PACKAGE_NAME", packageName).addFlags(67239936);
                try {
                    str = runningTaskInfo.baseIntent.getComponent().flattenToShortString();
                } catch (Exception e) {
                    android.util.Log.d("WorkLockActivityController", "ActivityTaskManager.getTasks() raise Exception!! " + e);
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("getComponentFromTaskId() failed!! "), runningTaskInfo.taskId, "WorkLockActivityController");
                    str = null;
                }
                addFlags.putExtra("componentName", str);
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchTaskId(runningTaskInfo.taskId);
                makeBasic.setTaskOverlay(true, false);
                try {
                    i2 = workLockActivityController.mIatm.startActivityAsUser(context2.getIApplicationThread(), context2.getBasePackageName(), context2.getAttributionTag(), addFlags, addFlags.resolveTypeIfNeeded(context2.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), ((UserTrackerImpl) workLockActivityController.mUserTracker).getUserId());
                } catch (RemoteException | Exception unused) {
                    i2 = -96;
                }
                if (ActivityManager.isStartResultSuccessful(i2)) {
                    return;
                }
                android.util.Log.w("WorkLockActivityController", "Failed to start work lock activity, will remove task=" + runningTaskInfo.taskId);
                try {
                    workLockActivityController.mIatm.removeTask(runningTaskInfo.taskId);
                } catch (RemoteException unused2) {
                    android.util.Log.e("WorkLockActivityController", "Failed to remove task=" + runningTaskInfo.taskId);
                }
            }
        };
        this.mLockListener = r0;
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mIatm = iActivityTaskManager;
        taskStackChangeListeners.registerTaskStackListener(r0);
    }

    public WorkLockActivityController(Context context, UserTracker userTracker) {
        this(context, userTracker, TaskStackChangeListeners.INSTANCE, ActivityTaskManager.getService());
    }
}
