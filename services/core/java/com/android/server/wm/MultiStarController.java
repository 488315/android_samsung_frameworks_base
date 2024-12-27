package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.graphics.Rect;
import android.os.Binder;
import android.os.RemoteException;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiStarController implements IController {
    public static final List DEFAULT_ALLOW_LIST = Arrays.asList("com.samsung.android.multistar", Constants.SYSTEMUI_PACKAGE_NAME, KnoxCustomManagerService.SETTING_PKG_NAME);
    public ActivityTaskManagerService mAtm;

    public static void checkMultiStarPackageAndPermission(String str, List list) {
        if (list == null) {
            list = new ArrayList();
        }
        list.addAll(DEFAULT_ALLOW_LIST);
        try {
            String[] packagesForUid = AppGlobals.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            for (String str2 : list) {
                for (String str3 : packagesForUid) {
                    if (str2.equals(str3)) {
                        ActivityTaskManagerService.enforceTaskPermission(str);
                        return;
                    }
                }
            }
        } catch (RemoteException unused) {
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Permission Denial: ", str, " from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        throw new SecurityException(m.toString());
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
    }

    public final void setCustomDensityEnabled(int i) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.mMultiWindowEnableController.dismissMultiWindowMode();
        MultiWindowEnableController multiWindowEnableController = activityTaskManagerService.mMultiWindowEnableController;
        ArrayList arrayList = multiWindowEnableController.mCDRequestLogs;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "(", ", ");
        m.append(multiWindowEnableController.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        arrayList.add(m.toString());
        if (multiWindowEnableController.mCDRequestLogs.size() > 50) {
            multiWindowEnableController.mCDRequestLogs.remove(0);
        }
        Iterator it = multiWindowEnableController.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            multiWindowEnableController.mCoreStateController.setVolatileState("custom_density", Integer.valueOf(i), ((Integer) it.next()).intValue(), true, true, null);
        }
    }

    public final boolean toggleFreeformWindowingMode() {
        ActivityRecord activityRecord;
        Rect rect;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        Task rootTask = activityTaskManagerService.mWindowManager.getDefaultDisplayContentLocked().getRootTask(1, 1);
        if (rootTask == null || (activityRecord = rootTask.topRunningActivityLocked()) == null || activityRecord.task == null || !activityRecord.supportsFreeform()) {
            return false;
        }
        activityTaskManagerService.mActivityClientController.toggleFreeformWindowingMode(activityRecord.token);
        if (activityRecord.inFreeformWindowingMode() && ((rect = activityRecord.task.mLastNonFullscreenBounds) == null || rect.isEmpty())) {
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchWindowingMode(5);
            LaunchParamsController launchParamsController = activityTaskManagerService.mTaskSupervisor.mLaunchParamsController;
            Task task = activityRecord.task;
            launchParamsController.layoutTask(task, activityRecord.info.windowLayout, activityRecord, null, makeBasic, task.getDisplayId());
        }
        return true;
    }
}
