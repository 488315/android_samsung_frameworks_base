package com.android.systemui.subscreen;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import java.util.List;
import java.util.Stack;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubScreenManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubScreenManager$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ComponentName componentName;
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                SubScreenManager subScreenManager = (SubScreenManager) this.f$0;
                subScreenManager.getClass();
                Log.d("SubScreenManager", "requestPluginConnection() PluginFaceWidget is connected");
                subScreenManager.addPluginListener();
                break;
            case 1:
                SubScreenManager subScreenManager2 = (SubScreenManager) this.f$0;
                boolean z2 = true;
                List<ActivityManager.RunningTaskInfo> runningTasks = subScreenManager2.mActivityManager.getRunningTasks(1);
                if (!runningTasks.isEmpty() && (componentName = ((TaskInfo) runningTasks.get(0)).topActivity) != null) {
                    z2 = true ^ subScreenManager2.isShowWhenCoverLocked(componentName);
                }
                if (z2) {
                    subScreenManager2.startSubHomeActivity();
                    break;
                }
                break;
            case 2:
                SubScreenManager subScreenManager3 = (SubScreenManager) this.f$0;
                List<ActivityManager.RunningTaskInfo> runningTasks2 = subScreenManager3.mActivityManager.getRunningTasks(10);
                Stack stack = subScreenManager3.mTaskStack;
                stack.clear();
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks2) {
                    if ("com.android.systemui.subscreen.SubHomeActivity".equals(runningTaskInfo.topActivity.getClassName())) {
                        subScreenManager3.startSubHomeActivity();
                        break;
                    } else {
                        String packageName = runningTaskInfo.topActivity.getPackageName();
                        try {
                            z = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(packageName, UserHandle.semGetCallingUserId());
                            Log.i("SubScreenManager", "Cover Launcher Check Top App pkg  " + packageName + " cover app Enabled  : " + z);
                        } catch (RemoteException e) {
                            Log.i("SubScreenManager", "isCoverLauncherActivity RemoteException " + e.getMessage());
                            z = false;
                        }
                        if (z) {
                            Log.i("SubScreenManager", "Push to Stack task : " + runningTaskInfo.topActivity);
                            stack.push(runningTaskInfo);
                        }
                    }
                }
                subScreenManager3.startSubHomeActivity();
            default:
                SubScreenManager.C34624 c34624 = (SubScreenManager.C34624) this.f$0;
                SubScreenManager subScreenManager4 = SubScreenManager.this;
                SubScreenManager.HandlerC34635 handlerC34635 = subScreenManager4.mHandler;
                if (handlerC34635.hasMessages(1000)) {
                    handlerC34635.removeMessages(1000);
                }
                int intValue = ((SettingsHelper) subScreenManager4.mSettingsHelperLazy.get()).mItemLists.get("cover_screen_timeout").getIntValue() * 1000;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("sendTurnOffScreenSmartCover() send Message screen after ", intValue, " , ");
                m1m.append(subScreenManager4.mActivity);
                Log.i("SubScreenManager", m1m.toString());
                handlerC34635.sendMessageDelayed(handlerC34635.obtainMessage(1000), intValue);
                SubScreenManager subScreenManager5 = SubScreenManager.this;
                subScreenManager5.startSubHomeActivity(subScreenManager5.mSubDisplay);
                break;
        }
    }
}
