package com.android.server.wm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.server.packagefeature.PackageFeature;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiWindowSupportPolicyController implements IController {
    public MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository mAllowListRepository;
    public final ActivityTaskManagerService mAtm;
    public MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository mBlockListRepository;

    public MultiWindowSupportPolicyController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        if (this.mBlockListRepository == null) {
            MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = new MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository(1, activityTaskManagerService, "BlockList");
            PackageFeature.DISPLAY_COMPAT.registerCallback(multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository);
            this.mBlockListRepository = multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository;
        }
    }

    public static boolean isIgnoreDevSettingForNonResizable(ActivityInfo activityInfo) {
        Bundle bundle;
        if (activityInfo == null) {
            return false;
        }
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && bundle.getBoolean("com.samsung.android.multiwindow.ignore.nonresizable.setting", false)) {
            return true;
        }
        Bundle bundle2 = activityInfo.metaData;
        return bundle2 != null && bundle2.getBoolean("com.samsung.android.multiwindow.ignore.nonresizable.setting", false);
    }

    public static boolean supportsNonResizableMultiWindow(TaskDisplayArea taskDisplayArea, boolean z) {
        if (z) {
            return false;
        }
        ActivityTaskManagerService activityTaskManagerService = taskDisplayArea.mAtmService;
        int i = activityTaskManagerService.mSupportsNonResizableMultiWindow;
        return activityTaskManagerService.mDevEnableNonResizableMultiWindow || i == 1 || (i != -1 && taskDisplayArea.getConfiguration().smallestScreenWidthDp >= 600);
    }

    public final int checkSupportPolicyLocked(int i, String str) {
        int i2;
        if (this.mAtm.mForceResizableActivities) {
            return i & (-3145729);
        }
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        int i3 = i & (-3145729);
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = this.mAllowListRepository;
        if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository == null || !multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.containsPackage(str, false)) {
            MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 = this.mBlockListRepository;
            if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 == null || !multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2.containsPackage(str, true)) {
                return i3;
            }
            i2 = 2097152;
        } else {
            i2 = 1048576;
        }
        return i3 | i2;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[MultiWindowSupportPolicyController]");
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = this.mAllowListRepository;
        if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository != null) {
            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.dump(printWriter);
        }
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 = this.mBlockListRepository;
        if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 != null) {
            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2.dump(printWriter);
        }
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
    }

    public final boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea, int i, boolean z, boolean z2) {
        if (i == 10) {
            return false;
        }
        if (this.mAtm.mForceResizableActivities || (1048576 & i) != 0) {
            return true;
        }
        if ((2097152 & i) == 0 && z) {
            return true;
        }
        return supportsNonResizableMultiWindow(taskDisplayArea, z2);
    }

    public final void updateAllTasksLocked() {
        Iterator it = this.mAtm.mRecentTasks.getRawTasks().iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            updateSupportPolicyLocked(null, task);
            task.forAllActivities(new Consumer() { // from class: com.android.server.wm.MultiWindowSupportPolicyController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MultiWindowSupportPolicyController multiWindowSupportPolicyController = MultiWindowSupportPolicyController.this;
                    ActivityRecord activityRecord = (ActivityRecord) obj;
                    multiWindowSupportPolicyController.getClass();
                    activityRecord.mIgnoreDevSettingForNonResizable = MultiWindowSupportPolicyController.isIgnoreDevSettingForNonResizable(activityRecord.info);
                    ActivityInfo activityInfo = activityRecord.info;
                    activityInfo.resizeMode = multiWindowSupportPolicyController.checkSupportPolicyLocked(activityInfo.resizeMode, activityRecord.packageName);
                }
            });
        }
    }

    public final void updateSupportPolicyLocked(ActivityInfo activityInfo, Task task) {
        if (activityInfo != null) {
            task.mIgnoreDevSettingForNonResizable = isIgnoreDevSettingForNonResizable(activityInfo);
        }
        ComponentName componentName = task.realActivity;
        task.mResizeMode = checkSupportPolicyLocked(task.mResizeMode, componentName != null ? componentName.getPackageName() : null);
    }
}
