package com.android.server.wm;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.server.wm.MultiWindowSupportPolicyController;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class MultiWindowSupportPolicyController implements IController {
    public MultiWindowSupportRepository mAllowListRepository;
    public final ActivityTaskManagerService mAtm;
    public MultiWindowSupportRepository mBlockListRepository;

    @Override // com.android.server.wm.IController
    public void initialize() {
    }

    public MultiWindowSupportPolicyController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mAllowListRepository = new MultiWindowSupportRepository.AllowListRepository(activityTaskManagerService);
        this.mBlockListRepository = new MultiWindowSupportRepository.BlockListRepository(activityTaskManagerService);
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[MultiWindowSupportPolicyController]");
        MultiWindowSupportRepository multiWindowSupportRepository = this.mAllowListRepository;
        if (multiWindowSupportRepository != null) {
            multiWindowSupportRepository.dump(printWriter, str);
        }
        MultiWindowSupportRepository multiWindowSupportRepository2 = this.mBlockListRepository;
        if (multiWindowSupportRepository2 != null) {
            multiWindowSupportRepository2.dump(printWriter, str);
        }
    }

    public void updateAllTasksLocked() {
        Iterator it = this.mAtm.getRecentTasks().getRawTasks().iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            updateSupportPolicyLocked(task, null);
            task.forAllActivities(new Consumer() { // from class: com.android.server.wm.MultiWindowSupportPolicyController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MultiWindowSupportPolicyController.this.lambda$updateAllTasksLocked$0((ActivityRecord) obj);
                }
            });
        }
    }

    /* renamed from: updateSupportPolicyLocked, reason: merged with bridge method [inline-methods] */
    public void lambda$updateAllTasksLocked$0(ActivityRecord activityRecord) {
        activityRecord.mIgnoreDevSettingForNonResizable = isIgnoreDevSettingForNonResizable(activityRecord.info);
        ActivityInfo activityInfo = activityRecord.info;
        activityInfo.resizeMode = checkSupportPolicyLocked(activityInfo.resizeMode, activityRecord.packageName);
    }

    public void updateSupportPolicyLocked(Task task, ActivityInfo activityInfo) {
        if (activityInfo != null) {
            task.mIgnoreDevSettingForNonResizable = isIgnoreDevSettingForNonResizable(activityInfo);
        }
        ComponentName componentName = task.realActivity;
        task.mResizeMode = checkSupportPolicyLocked(task.mResizeMode, componentName != null ? componentName.getPackageName() : null);
    }

    public final int checkSupportPolicyLocked(int i, String str) {
        int i2;
        if (this.mAtm.mForceResizableActivities) {
            return i & (-1048577) & (-2097153);
        }
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        int i3 = i & (-1048577) & (-2097153);
        if (isAllowListApp(str)) {
            i2 = 1048576;
        } else {
            if (!isBlocklistApp(str)) {
                return i3;
            }
            i2 = 2097152;
        }
        return i3 | i2;
    }

    public boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea, int i, boolean z, boolean z2) {
        if (i == 10) {
            return false;
        }
        if (this.mAtm.mForceResizableActivities || (1048576 & i) != 0) {
            return true;
        }
        if ((2097152 & i) != 0) {
            return supportsNonResizableMultiWindow(taskDisplayArea, z2);
        }
        if (z) {
            return true;
        }
        return supportsNonResizableMultiWindow(taskDisplayArea, z2);
    }

    public static boolean supportsNonResizableMultiWindow(TaskDisplayArea taskDisplayArea, boolean z) {
        if (z) {
            return false;
        }
        return taskDisplayArea.supportsNonResizableMultiWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getResizeMode(ActivityInfo activityInfo) {
        boolean z;
        boolean z2;
        int i;
        if (activityInfo == null || activityInfo.packageName == null) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z = isIgnoreDevSettingForNonResizable(AppGlobals.getPackageManager().getActivityInfo(activityInfo.getComponentName(), 128L, this.mAtm.mContext.getUserId()));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Exception unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            z = false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            int checkSupportPolicyLocked = checkSupportPolicyLocked(activityInfo.resizeMode, activityInfo.packageName);
            if (!ActivityInfo.isResizeableMode(checkSupportPolicyLocked) && !activityInfo.supportsPictureInPicture()) {
                z2 = false;
                i = supportsMultiWindowInDisplayArea(this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea(), checkSupportPolicyLocked, z2, z) ? 2 : 0;
            }
            z2 = true;
            if (supportsMultiWindowInDisplayArea(this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea(), checkSupportPolicyLocked, z2, z)) {
            }
        }
        return i;
    }

    public boolean isAllowListApp(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mAllowListRepository;
        return multiWindowSupportRepository != null && multiWindowSupportRepository.containsPackage(str, false);
    }

    public void addAllowPackage(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mAllowListRepository;
        if (multiWindowSupportRepository == null) {
            return;
        }
        multiWindowSupportRepository.addPackage(str);
    }

    public void removeAllowPackage(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mAllowListRepository;
        if (multiWindowSupportRepository == null) {
            return;
        }
        multiWindowSupportRepository.removePackage(str);
    }

    public List getAllowAppList() {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mAllowListRepository;
        if (multiWindowSupportRepository == null) {
            return Collections.emptyList();
        }
        return multiWindowSupportRepository.getCopiedPackageList(false);
    }

    public boolean isBlocklistApp(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mBlockListRepository;
        return multiWindowSupportRepository != null && multiWindowSupportRepository.containsPackage(str, true);
    }

    public void addBlockPackage(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mBlockListRepository;
        if (multiWindowSupportRepository == null) {
            return;
        }
        multiWindowSupportRepository.addPackage(str);
    }

    public void removeBlockPackage(String str) {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mBlockListRepository;
        if (multiWindowSupportRepository == null) {
            return;
        }
        multiWindowSupportRepository.removePackage(str);
    }

    public List getBlocklistAppList() {
        MultiWindowSupportRepository multiWindowSupportRepository = this.mBlockListRepository;
        if (multiWindowSupportRepository == null) {
            return new ArrayList();
        }
        return multiWindowSupportRepository.getCopiedPackageList(true);
    }

    public void removeFromDeferredBlockListIfNeeedLocked(Task task) {
        MultiWindowSupportRepository multiWindowSupportRepository;
        ComponentName componentName = task.realActivity;
        if (componentName == null || (multiWindowSupportRepository = this.mBlockListRepository) == null || !multiWindowSupportRepository.removeDeferredPackages(componentName.getPackageName())) {
            return;
        }
        updateSupportPolicyLocked(task, null);
    }

    public final boolean isIgnoreDevSettingForNonResizable(ActivityInfo activityInfo) {
        if (activityInfo == null) {
            return false;
        }
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        if (applicationInfo == null || !containsIgnoreNonResizableMetaData(applicationInfo.metaData)) {
            return containsIgnoreNonResizableMetaData(activityInfo.metaData);
        }
        return true;
    }

    public static boolean containsIgnoreNonResizableMetaData(Bundle bundle) {
        return bundle != null && bundle.getBoolean("com.samsung.android.multiwindow.ignore.nonresizable.setting", false);
    }

    public abstract class MultiWindowSupportRepository implements PackageFeatureCallback {
        public final ActivityTaskManagerService mAtm;
        public Set mDeferredPackages;
        public Set mTempPackages;
        public final String mTitle;
        public final Object mLock = new Object();
        public final Set mPackages = new HashSet();
        public final Runnable mUpdateRunnable = new Runnable() { // from class: com.android.server.wm.MultiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MultiWindowSupportPolicyController.MultiWindowSupportRepository.this.onUpdate();
            }
        };

        public abstract void onUpdate();

        public MultiWindowSupportRepository(ActivityTaskManagerService activityTaskManagerService, String str) {
            this.mAtm = activityTaskManagerService;
            this.mTitle = str;
        }

        public void replaceAllPackages(Set set) {
            synchronized (this.mLock) {
                this.mPackages.clear();
                this.mPackages.addAll(set);
                scheduleUpdate();
            }
        }

        public boolean containsPackage(String str, boolean z) {
            boolean contains;
            if (str == null) {
                return false;
            }
            synchronized (this.mLock) {
                contains = getPackages(z).contains(str);
            }
            return contains;
        }

        public void addPackage(String str) {
            if (str == null) {
                return;
            }
            synchronized (this.mLock) {
                if (this.mPackages.add(str)) {
                    scheduleUpdate();
                }
            }
        }

        public void removePackage(String str) {
            if (str == null) {
                return;
            }
            synchronized (this.mLock) {
                if (this.mPackages.remove(str)) {
                    scheduleUpdate();
                }
            }
        }

        public boolean removeDeferredPackages(String str) {
            boolean z = false;
            if (str == null) {
                return false;
            }
            synchronized (this.mLock) {
                Set set = this.mDeferredPackages;
                if (set != null && set.remove(str)) {
                    z = true;
                }
            }
            return z;
        }

        public List getCopiedPackageList(boolean z) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.mLock) {
                arrayList.addAll(getPackages(z));
            }
            return arrayList;
        }

        public final Set getPackages(boolean z) {
            Set set;
            if (!z || (set = this.mDeferredPackages) == null || set.isEmpty()) {
                return this.mPackages;
            }
            Set orCreateTempPackages = getOrCreateTempPackages();
            orCreateTempPackages.clear();
            orCreateTempPackages.addAll(this.mPackages);
            for (String str : this.mDeferredPackages) {
                if (orCreateTempPackages.contains(str)) {
                    orCreateTempPackages.remove(str);
                } else {
                    orCreateTempPackages.add(str);
                }
            }
            return orCreateTempPackages;
        }

        public Set getOrCreateDeferredPackages() {
            if (this.mDeferredPackages == null) {
                this.mDeferredPackages = new HashSet();
            }
            return this.mDeferredPackages;
        }

        public Set getOrCreateTempPackages() {
            if (this.mTempPackages == null) {
                this.mTempPackages = new HashSet();
            }
            return this.mTempPackages;
        }

        public void scheduleUpdate() {
            this.mAtm.f1734mH.removeCallbacks(this.mUpdateRunnable);
            this.mAtm.f1734mH.post(this.mUpdateRunnable);
        }

        public void dump(PrintWriter printWriter, String str) {
            String str2 = str + "  ";
            synchronized (this.mLock) {
                printWriter.println(str + this.mTitle + "=" + this.mPackages.size());
                if (CoreRune.SAFE_DEBUG) {
                    dumpPackages(printWriter, str2, this.mPackages);
                }
                Set set = this.mDeferredPackages;
                if (set != null && !set.isEmpty()) {
                    printWriter.println(str + this.mTitle + "(Deferred)=" + this.mPackages.size());
                    dumpPackages(printWriter, str2, this.mDeferredPackages);
                }
            }
            printWriter.println();
        }

        public final void dumpPackages(PrintWriter printWriter, String str, Set set) {
            printWriter.print(str);
            if (set.isEmpty()) {
                printWriter.print("Empty");
            } else {
                Iterator it = set.iterator();
                int i = 0;
                while (it.hasNext()) {
                    printWriter.print((String) it.next());
                    i++;
                    if (i % 5 == 0) {
                        printWriter.println();
                        printWriter.print(str);
                    }
                    printWriter.print(" ");
                }
            }
            printWriter.println();
        }

        public class AllowListRepository extends MultiWindowSupportRepository {
            public AllowListRepository(ActivityTaskManagerService activityTaskManagerService) {
                super(activityTaskManagerService, "AllowList");
                PackageFeature.ALLOW_MULTI_WINDOW.registerCallback(this);
            }

            @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
            public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
                replaceAllPackages(packageFeatureData.keySet());
            }

            @Override // com.android.server.wm.MultiWindowSupportPolicyController.MultiWindowSupportRepository
            public void onUpdate() {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        this.mAtm.mMwSupportPolicyController.updateAllTasksLocked();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public class BlockListRepository extends MultiWindowSupportRepository {
            public BlockListRepository(ActivityTaskManagerService activityTaskManagerService) {
                super(activityTaskManagerService, "BlockList");
                PackageFeature.DISPLAY_COMPAT.registerCallback(this);
            }

            @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
            public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
                synchronized (this.mLock) {
                    Set orCreateTempPackages = getOrCreateTempPackages();
                    orCreateTempPackages.clear();
                    for (Map.Entry entry : packageFeatureData.entrySet()) {
                        if ("b".equals(entry.getValue())) {
                            orCreateTempPackages.add((String) entry.getKey());
                        }
                    }
                    replaceAllPackages(orCreateTempPackages);
                }
            }

            @Override // com.android.server.wm.MultiWindowSupportPolicyController.MultiWindowSupportRepository
            public void onUpdate() {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        synchronized (this.mLock) {
                            Set orCreateDeferredPackages = getOrCreateDeferredPackages();
                            Iterator it = this.mAtm.getRecentTasks().getRawTasks().iterator();
                            while (it.hasNext()) {
                                Task task = (Task) it.next();
                                if (task.realActivity != null && task.getRootTask() != null) {
                                    String packageName = task.realActivity.getPackageName();
                                    if (!orCreateDeferredPackages.contains(packageName)) {
                                        if (containsPackage(packageName, false) != ((task.mResizeMode & 2097152) != 0)) {
                                            orCreateDeferredPackages.add(packageName);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }
}
