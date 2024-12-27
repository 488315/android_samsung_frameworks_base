package com.samsung.android.core;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Singleton;
import com.android.internal.compat.IPlatformCompat;
import com.samsung.android.core.CompatChangeablePackageInfo;
import com.samsung.android.core.ICompatChangeableManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class CompatChangeableApps extends ICompatChangeableManager.Stub {
    public static final String TAG = "CompatChangeableApps";
    private final Map<String, CompatChangeablePackageInfo> mCache;
    private CompatChangeablePackageInfo mDummyInfo;
    private final Singleton<IPlatformCompat> mPlatformCompat;
    private final int mUserId;

    private static class OrientationOverrideDisallowedLazyHolder {
        static final HashSet<String> sList = new HashSet<>(Arrays.asList("com.sec.android.app.camera", "com.samsung.android.globalroaming", "com.samsung.android.app.watchmanager"));

        private OrientationOverrideDisallowedLazyHolder() {
        }
    }

    public CompatChangeableApps(int userId) {
        this(userId, false);
    }

    public CompatChangeableApps(int userId, boolean updateCache) {
        this.mDummyInfo = new CompatChangeablePackageInfo.Builder().build();
        this.mCache = new ConcurrentHashMap();
        this.mPlatformCompat = new Singleton<IPlatformCompat>() { // from class: com.samsung.android.core.CompatChangeableApps.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public IPlatformCompat create() {
                IBinder b = ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE);
                return IPlatformCompat.Stub.asInterface(b);
            }
        };
        this.mUserId = userId;
        if (updateCache) {
            updateCompatChangeablePackageInfoList(null, null);
        }
    }

    private void updateCompatChangeablePackageInfoList(String packageName, List<String> outPackageNameList) {
        try {
            ParceledListSlice<CompatChangeablePackageInfo> parceledListSlice = ActivityTaskManager.getService().getCompatChangeablePackageInfoList(packageName, this.mUserId);
            List<CompatChangeablePackageInfo> list = parceledListSlice.getList();
            synchronized (this.mCache) {
                for (CompatChangeablePackageInfo info : list) {
                    String key = info.mPackageName;
                    this.mCache.put(key, info);
                    if (outPackageNameList != null) {
                        outPackageNameList.add(key);
                    }
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private CompatChangeablePackageInfo getCachedInfo(String packageName) {
        CompatChangeablePackageInfo info;
        if (packageName == null) {
            updateCompatChangeablePackageInfoList(null, null);
            return this.mDummyInfo;
        }
        synchronized (this.mCache) {
            info = this.mCache.get(packageName);
        }
        if (info == null) {
            updateCompatChangeablePackageInfoList(packageName, null);
            synchronized (this.mCache) {
                info = this.mCache.get(packageName);
            }
        }
        return info != null ? info : this.mDummyInfo;
    }

    public void removeCache(String packageName) {
        synchronized (this.mCache) {
            this.mCache.remove(packageName);
        }
    }

    public boolean containsCache(String packageName) {
        boolean containsKey;
        synchronized (this.mCache) {
            containsKey = this.mCache.containsKey(packageName);
        }
        return containsKey;
    }

    public void dump(PrintWriter pw, String prefix) {
        List<String> target;
        synchronized (this.mCache) {
            if (this.mCache.isEmpty()) {
                return;
            }
            List<String> launcherActivities = new ArrayList<>();
            List<String> games = new ArrayList<>();
            List<String> orientationOverrideDisallowedList = new ArrayList<>();
            List<String> minAspectRatioOverrideDisallowedList = new ArrayList<>();
            List<String> others = new ArrayList<>();
            synchronized (this.mCache) {
                for (Map.Entry<String, CompatChangeablePackageInfo> entry : this.mCache.entrySet()) {
                    String packageName = entry.getKey();
                    CompatChangeablePackageInfo info = entry.getValue();
                    if (info.mHasGameCategory) {
                        target = games;
                    } else if (info.mHasLauncherActivity) {
                        target = launcherActivities;
                    } else {
                        target = others;
                    }
                    target.add(packageName);
                    if (info.mIsOrientationOverrideDisallowed) {
                        orientationOverrideDisallowedList.add(packageName);
                    }
                    if (info.mIsMinAspectRatioOverrideDisallowed) {
                        minAspectRatioOverrideDisallowedList.add(packageName);
                    }
                }
            }
            int userId = this.mUserId;
            printList(pw, prefix, "LauncherActivities(u" + userId + NavigationBarInflaterView.KEY_CODE_END, launcherActivities);
            printList(pw, prefix, "Games(u" + userId + NavigationBarInflaterView.KEY_CODE_END, games);
            printList(pw, prefix, "Others(u" + userId + NavigationBarInflaterView.KEY_CODE_END, others);
            printList(pw, prefix, "OrientationOverrideDisallowedList(u" + userId + NavigationBarInflaterView.KEY_CODE_END, orientationOverrideDisallowedList);
            printList(pw, prefix, "MinAspectRatioOverrideDisallowedList(u" + userId + NavigationBarInflaterView.KEY_CODE_END, minAspectRatioOverrideDisallowedList);
        }
    }

    private static void printList(PrintWriter pw, String prefix, String title, List<String> list) {
        String prefix2;
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        pw.print(prefix + title + ": " + size);
        String innerPrefix = prefix + "  ";
        for (int i = 0; i < size; i++) {
            String name = list.get(i);
            if (i == 0 || (i + 1) % 5 == 0) {
                prefix2 = "\n" + innerPrefix;
            } else {
                prefix2 = ", ";
            }
            pw.print(prefix2 + name);
        }
        pw.println();
    }

    public static boolean isSamsungPackage(String packageName) {
        return packageName != null && (packageName.startsWith("com.samsung.") || packageName.startsWith("com.sec."));
    }

    public static boolean isOrientationOverrideDisallowedPackage(String packageName) {
        return OrientationOverrideDisallowedLazyHolder.sList.contains(packageName);
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public List<String> getCompatChangeablePackageNameList() {
        List<String> list = new ArrayList<>();
        updateCompatChangeablePackageInfoList(null, list);
        return list;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public int getUid(String packageName) {
        return getCachedInfo(packageName).mUid;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasLauncherActivity(String packageName) {
        return getCachedInfo(packageName).mHasLauncherActivity;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasGameCategory(String packageName) {
        return getCachedInfo(packageName).mHasGameCategory;
    }

    public boolean isResizeableActivityOverrideDisallowed(String packageName) {
        return getCachedInfo(packageName).mIsResizeableActivityOverrideDisallowed || containsOverride(ActivityInfo.FORCE_RESIZE_APP, packageName) || containsOverride(ActivityInfo.FORCE_NON_RESIZE_APP, packageName);
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isOrientationOverrideDisallowed(String packageName) {
        return isOrientationOverrideDisallowedPackage(packageName) || getCachedInfo(packageName).mIsOrientationOverrideDisallowed || containsOverride(ActivityInfo.OVERRIDE_RESPECT_REQUESTED_ORIENTATION, packageName);
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isMinAspectRatioOverrideDisallowed(String packageName) {
        if (isSamsungPackage(packageName)) {
            return true;
        }
        CompatChangeablePackageInfo info = getCachedInfo(packageName);
        return info.mIsMinAspectRatioOverrideDisallowed || info.mIsActivityEmbeddingSplitsEnabled || containsOverride(ActivityInfo.OVERRIDE_MIN_ASPECT_RATIO, packageName) || containsOverride(ActivityInfo.OVERRIDE_MIN_ASPECT_RATIO_ONLY_FOR_CAMERA, packageName);
    }

    public static Boolean readComponentProperty(PackageManager packageManager, String packageName, String propertyName) {
        try {
            return Boolean.valueOf(packageManager.getProperty(propertyName, packageName).getBoolean());
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private boolean containsOverride(long changeId, String packageName) {
        if (packageName == null) {
            Log.w(TAG, "containsOverride: PackageName is null.");
            return false;
        }
        IPlatformCompat platformCompat = this.mPlatformCompat.get();
        if (platformCompat == null) {
            Log.w(TAG, "containsOverride: PlatformCompat is null.");
            return false;
        }
        try {
            return platformCompat.containsOverride(changeId, packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
