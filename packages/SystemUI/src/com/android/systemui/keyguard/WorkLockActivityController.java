package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

/* loaded from: classes2.dex */
public class WorkLockActivityController {
    public final Context mContext;
    public final IActivityTaskManager mIatm;
    public final AnonymousClass1 mLockListener;
    public final UserTracker mUserTracker;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.WorkLockActivityController$1, com.android.systemui.shared.system.TaskStackChangeListener] */
    public WorkLockActivityController(Context context, UserTracker userTracker, TaskStackChangeListeners taskStackChangeListeners, IActivityTaskManager iActivityTaskManager) {
        ?? r0 = new TaskStackChangeListener() { // from class: com.android.systemui.keyguard.WorkLockActivityController.1
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
                String strFlattenToShortString;
                WorkLockActivityController workLockActivityController = WorkLockActivityController.this;
                workLockActivityController.getClass();
                ComponentName componentName = runningTaskInfo.baseActivity;
                Intent intentAddFlags = new Intent("android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER").setComponent(new ComponentName(workLockActivityController.mContext, (Class<?>) WorkLockActivity.class)).putExtra("android.intent.extra.USER_ID", i).putExtra("android.intent.extra.PACKAGE_NAME", componentName != null ? componentName.getPackageName() : "").addFlags(67239936);
                try {
                    strFlattenToShortString = runningTaskInfo.baseIntent.getComponent().flattenToShortString();
                } catch (Exception e) {
                    android.util.Log.d("WorkLockActivityController", "ActivityTaskManager.getTasks() raise Exception!! " + e);
                    RecyclerView$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "WorkLockActivityController", new StringBuilder("getComponentFromTaskId() failed!! "));
                    strFlattenToShortString = null;
                }
                intentAddFlags.putExtra("componentName", strFlattenToShortString);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchTaskId(runningTaskInfo.taskId);
                activityOptionsMakeBasic.setTaskOverlay(true, false);
                Bundle bundle = activityOptionsMakeBasic.toBundle();
                int iStartActivityAsUser = -96;
                try {
                    iStartActivityAsUser = workLockActivityController.mIatm.startActivityAsUser(workLockActivityController.mContext.getIApplicationThread(), workLockActivityController.mContext.getBasePackageName(), workLockActivityController.mContext.getAttributionTag(), intentAddFlags, intentAddFlags.resolveTypeIfNeeded(workLockActivityController.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, bundle, ((UserTrackerImpl) workLockActivityController.mUserTracker).getUserId());
                } catch (RemoteException | Exception unused) {
                }
                if (ActivityManager.isStartResultSuccessful(iStartActivityAsUser)) {
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
