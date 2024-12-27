package com.android.systemui.subscreen;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubScreenManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubScreenManager$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        ComponentName componentName;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SubScreenManager subScreenManager = (SubScreenManager) obj;
                subScreenManager.getClass();
                Log.d("SubScreenManager", "requestPluginConnection() PluginFaceWidget is connected");
                subScreenManager.addPluginListener$1$1();
                break;
            case 1:
                SubScreenManager subScreenManager2 = (SubScreenManager) obj;
                List<ActivityManager.RunningTaskInfo> runningTasks = subScreenManager2.mActivityManager.getRunningTasks(10);
                subScreenManager2.mTaskStack.clear();
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                    ComponentName componentName2 = runningTaskInfo.topActivity;
                    if (componentName2 != null) {
                        if ("com.android.systemui.subscreen.SubHomeActivity".equals(componentName2.getClassName())) {
                            subScreenManager2.startSubHomeActivity();
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
                            if (z || SubScreenComponentChecker.isCellBroadCastAlertDialog(runningTaskInfo.topActivity)) {
                                Log.i("SubScreenManager", "Push to Stack task : " + runningTaskInfo.topActivity);
                                subScreenManager2.mTaskStack.push(runningTaskInfo);
                            }
                        }
                    }
                }
                subScreenManager2.startSubHomeActivity();
                break;
            case 2:
                SubScreenManager subScreenManager3 = (SubScreenManager) obj;
                boolean z2 = true;
                List<ActivityManager.RunningTaskInfo> runningTasks2 = subScreenManager3.mActivityManager.getRunningTasks(1);
                if (!runningTasks2.isEmpty() && (componentName = ((TaskInfo) runningTasks2.get(0)).topActivity) != null) {
                    z2 = true ^ subScreenManager3.isShowWhenCoverLocked(componentName);
                }
                if (z2) {
                    subScreenManager3.startSubHomeActivity();
                    break;
                }
                break;
            default:
                SubScreenManager.AnonymousClass4 anonymousClass4 = (SubScreenManager.AnonymousClass4) obj;
                SubScreenManager subScreenManager4 = SubScreenManager.this;
                SubScreenManager.AnonymousClass5 anonymousClass5 = subScreenManager4.mHandler;
                if (anonymousClass5.hasMessages(1000)) {
                    anonymousClass5.removeMessages(1000);
                }
                int coverScreenTimeout = ((SettingsHelper) subScreenManager4.mSettingsHelperLazy.get()).getCoverScreenTimeout() * 1000;
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(coverScreenTimeout, "sendTurnOffScreenSmartCover() send Message screen after ", " , ");
                m.append(subScreenManager4.mActivity);
                Log.i("SubScreenManager", m.toString());
                anonymousClass5.sendMessageDelayed(anonymousClass5.obtainMessage(1000), coverScreenTimeout);
                SubScreenManager subScreenManager5 = SubScreenManager.this;
                subScreenManager5.startSubHomeActivity(subScreenManager5.mSubDisplay);
                break;
        }
    }
}
