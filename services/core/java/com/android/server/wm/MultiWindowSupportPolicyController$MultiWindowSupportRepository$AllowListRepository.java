package com.android.server.wm;

import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final
class MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
        implements PackageFeatureCallback {
    public final /* synthetic */ int $r8$classId;
    public final ActivityTaskManagerService mAtm;
    public Set mDeferredPackages;
    public Set mTempPackages;
    public final String mTitle;
    public final Object mLock = new Object();
    public final Set mPackages = new HashSet();
    public final
    MultiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0
            mUpdateRunnable =
                    new Runnable() { // from class:
                                     // com.android.server.wm.MultiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository =
                                            MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                    .this;
                            switch (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                    .$r8$classId) {
                                case 0:
                                    WindowManagerGlobalLock windowManagerGlobalLock =
                                            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                    .mAtm
                                                    .mGlobalLock;
                                    WindowManagerService.boostPriorityForLockedSection();
                                    synchronized (windowManagerGlobalLock) {
                                        try {
                                            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                    .mAtm.mMwSupportPolicyController
                                                    .updateAllTasksLocked();
                                        } finally {
                                        }
                                    }
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                default:
                                    WindowManagerGlobalLock windowManagerGlobalLock2 =
                                            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                    .mAtm
                                                    .mGlobalLock;
                                    WindowManagerService.boostPriorityForLockedSection();
                                    synchronized (windowManagerGlobalLock2) {
                                        try {
                                            synchronized (
                                                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                            .mLock) {
                                                try {
                                                    if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                                    .mDeferredPackages
                                                            == null) {
                                                        multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                                        .mDeferredPackages =
                                                                new HashSet();
                                                    }
                                                    Set set =
                                                            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                                    .mDeferredPackages;
                                                    Iterator it =
                                                            multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                                    .mAtm
                                                                    .mRecentTasks
                                                                    .getRawTasks()
                                                                    .iterator();
                                                    while (it.hasNext()) {
                                                        Task task = (Task) it.next();
                                                        if (task.realActivity != null
                                                                && task.getRootTask() != null) {
                                                            String packageName =
                                                                    task.realActivity
                                                                            .getPackageName();
                                                            HashSet hashSet = (HashSet) set;
                                                            if (!hashSet.contains(packageName)) {
                                                                if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                                                                .containsPackage(
                                                                                        packageName,
                                                                                        false)
                                                                        != ((task.mResizeMode
                                                                                        & 2097152)
                                                                                != 0)) {
                                                                    hashSet.add(packageName);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                            }
                        }
                    };

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.wm.MultiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0] */
    public MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository(
            int i, ActivityTaskManagerService activityTaskManagerService, String str) {
        this.$r8$classId = i;
        this.mAtm = activityTaskManagerService;
        this.mTitle = str;
    }

    public static void dumpPackages(PrintWriter printWriter, String str, Set set) {
        printWriter.print(str);
        HashSet hashSet = (HashSet) set;
        if (hashSet.isEmpty()) {
            printWriter.print("Empty");
        } else {
            Iterator it = hashSet.iterator();
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

    public final void addPackage(String str) {
        if (str == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (((HashSet) this.mPackages).add(str)) {
                    scheduleUpdate();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean containsPackage(String str, boolean z) {
        boolean contains;
        if (str == null) {
            return false;
        }
        synchronized (this.mLock) {
            contains = getPackages(z).contains(str);
        }
        return contains;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("  " + this.mTitle + "=" + ((HashSet) this.mPackages).size());
                Set set = this.mDeferredPackages;
                if (set != null && !((HashSet) set).isEmpty()) {
                    printWriter.println(
                            "  " + this.mTitle + "(Deferred)=" + ((HashSet) this.mPackages).size());
                    dumpPackages(printWriter, "    ", this.mDeferredPackages);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println();
    }

    public final Set getPackages(boolean z) {
        Set set;
        if (!z || (set = this.mDeferredPackages) == null || ((HashSet) set).isEmpty()) {
            return this.mPackages;
        }
        if (this.mTempPackages == null) {
            this.mTempPackages = new HashSet();
        }
        HashSet hashSet = (HashSet) this.mTempPackages;
        hashSet.clear();
        hashSet.addAll(this.mPackages);
        Iterator it = ((HashSet) this.mDeferredPackages).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (hashSet.contains(str)) {
                hashSet.remove(str);
            } else {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
    public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
        switch (this.$r8$classId) {
            case 0:
                replaceAllPackages(packageFeatureData.keySet());
                return;
            default:
                synchronized (this.mLock) {
                    try {
                        if (this.mTempPackages == null) {
                            this.mTempPackages = new HashSet();
                        }
                        HashSet hashSet = (HashSet) this.mTempPackages;
                        hashSet.clear();
                        for (Map.Entry entry : packageFeatureData.entrySet()) {
                            if ("b".equals(entry.getValue())) {
                                hashSet.add((String) entry.getKey());
                            }
                        }
                        replaceAllPackages(hashSet);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }

    public final void removePackage(String str) {
        if (str == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (((HashSet) this.mPackages).remove(str)) {
                    scheduleUpdate();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void replaceAllPackages(Set set) {
        synchronized (this.mLock) {
            ((HashSet) this.mPackages).clear();
            this.mPackages.addAll(set);
            scheduleUpdate();
        }
    }

    public final void scheduleUpdate() {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        ActivityTaskManagerService.H h = activityTaskManagerService.mH;
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0
                multiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0 =
                        this.mUpdateRunnable;
        h.removeCallbacks(
                multiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0);
        activityTaskManagerService.mH.post(
                multiWindowSupportPolicyController$MultiWindowSupportRepository$$ExternalSyntheticLambda0);
    }
}
