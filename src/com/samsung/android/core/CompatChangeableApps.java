package com.samsung.android.core;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Singleton;
import com.android.internal.compat.IPlatformCompat;
import com.samsung.android.core.CompatChangeablePackageInfo;
import com.samsung.android.core.ICompatChangeableManager;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    @Deprecated
    public @interface AspectRatioAndOrientationOverride {
        public static final int APP_DEFAULT = 2;
        public static final int CUSTOM_16_9_AND_FORCE_ROTATION_OFF = 5;
        public static final int CUSTOM_16_9_AND_FORCE_ROTATION_ON = 6;
        public static final int CUSTOM_4_3_AND_FORCE_ROTATION_OFF = 7;
        public static final int CUSTOM_4_3_AND_FORCE_ROTATION_ON = 8;
        public static final int CUSTOM_APP_DEFAULT_AND_FORCE_ROTATION_OFF = 3;
        public static final int CUSTOM_APP_DEFAULT_AND_FORCE_ROTATION_ON = 4;
        public static final int FIT_TO_SCREEN = 1;
        public static final int NONE = 0;
    }

    @Deprecated
    public static boolean isValidAspectRatioAndOrientationOverride(int i) {
        return false;
    }

    @Deprecated
    public int getAspectRatioAndOrientationOverride(ApplicationInfo applicationInfo) {
        return 0;
    }

    @Deprecated
    public int getAspectRatioAndOrientationOverride(ApplicationInfo applicationInfo, boolean z) {
        return 0;
    }

    @Deprecated
    public void resetAspectRatioAndOrientationOverride() {
    }

    @Deprecated
    public void setAspectRatioAndOrientationOverride(ApplicationInfo applicationInfo, int i) {
    }

    public CompatChangeableApps(int i) {
        this(i, false);
    }

    public CompatChangeableApps(int i, boolean z) {
        this.mDummyInfo = new CompatChangeablePackageInfo.Builder().build();
        this.mCache = new ConcurrentHashMap();
        this.mPlatformCompat = new Singleton<IPlatformCompat>(this) { // from class: com.samsung.android.core.CompatChangeableApps.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public IPlatformCompat create() {
                return IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE));
            }
        };
        this.mUserId = i;
        if (z) {
            updateCompatChangeablePackageInfoList(null, null);
        }
    }

    private void updateCompatChangeablePackageInfoList(String str, List<String> list) {
        try {
            List<CompatChangeablePackageInfo> list2 = ActivityTaskManager.getService().getCompatChangeablePackageInfoList(str, this.mUserId).getList();
            synchronized (this.mCache) {
                for (CompatChangeablePackageInfo compatChangeablePackageInfo : list2) {
                    String str2 = compatChangeablePackageInfo.mPackageName;
                    this.mCache.put(str2, compatChangeablePackageInfo);
                    if (list != null) {
                        list.add(str2);
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private CompatChangeablePackageInfo getCachedInfo(String str) {
        CompatChangeablePackageInfo compatChangeablePackageInfo;
        if (str == null) {
            updateCompatChangeablePackageInfoList(null, null);
            return this.mDummyInfo;
        }
        synchronized (this.mCache) {
            compatChangeablePackageInfo = this.mCache.get(str);
        }
        if (compatChangeablePackageInfo == null) {
            updateCompatChangeablePackageInfoList(str, null);
            synchronized (this.mCache) {
                compatChangeablePackageInfo = this.mCache.get(str);
            }
        }
        return compatChangeablePackageInfo != null ? compatChangeablePackageInfo : this.mDummyInfo;
    }

    public void removeCache(String str) {
        synchronized (this.mCache) {
            this.mCache.remove(str);
        }
    }

    public boolean containsCache(String str) {
        boolean zContainsKey;
        synchronized (this.mCache) {
            zContainsKey = this.mCache.containsKey(str);
        }
        return zContainsKey;
    }

    public void dump(PrintWriter printWriter, String str) {
        ArrayList arrayList;
        synchronized (this.mCache) {
            if (this.mCache.isEmpty()) {
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            synchronized (this.mCache) {
                for (Map.Entry<String, CompatChangeablePackageInfo> entry : this.mCache.entrySet()) {
                    String key = entry.getKey();
                    CompatChangeablePackageInfo value = entry.getValue();
                    if (value.mHasGameCategory) {
                        arrayList = arrayList3;
                    } else {
                        arrayList = value.mHasLauncherActivity ? arrayList2 : arrayList6;
                    }
                    arrayList.add(key);
                    if (value.mIsOrientationOverrideDisallowed) {
                        arrayList4.add(key);
                    }
                    if (value.mIsMinAspectRatioOverrideDisallowed) {
                        arrayList5.add(key);
                    }
                }
            }
            int i = this.mUserId;
            printList(printWriter, str, "LauncherActivities(u" + i + NavigationBarInflaterView.KEY_CODE_END, arrayList2);
            printList(printWriter, str, "Games(u" + i + NavigationBarInflaterView.KEY_CODE_END, arrayList3);
            printList(printWriter, str, "Others(u" + i + NavigationBarInflaterView.KEY_CODE_END, arrayList6);
            printList(printWriter, str, "OrientationOverrideDisallowedList(u" + i + NavigationBarInflaterView.KEY_CODE_END, arrayList4);
            printList(printWriter, str, "MinAspectRatioOverrideDisallowedList(u" + i + NavigationBarInflaterView.KEY_CODE_END, arrayList5);
        }
    }

    private static void printList(PrintWriter printWriter, String str, String str2, List<String> list) {
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        printWriter.print(str + str2 + ": " + size);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        String string = sb.toString();
        for (int i = 0; i < size; i++) {
            String str3 = list.get(i);
            printWriter.print(((i == 0 || (i + 1) % 5 == 0) ? ShaderAssembler.NEWLINE + string : ", ") + str3);
        }
        printWriter.println();
    }

    public static boolean isSamsungPackage(String str) {
        if (str != null) {
            return str.startsWith("com.samsung.") || str.startsWith("com.sec.");
        }
        return false;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public List<String> getCompatChangeablePackageNameList() {
        ArrayList arrayList = new ArrayList();
        updateCompatChangeablePackageInfoList(null, arrayList);
        return arrayList;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public int getUid(String str) {
        return getCachedInfo(str).mUid;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasLauncherActivity(String str) {
        return getCachedInfo(str).mHasLauncherActivity;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasGameCategory(String str) {
        return getCachedInfo(str).mHasGameCategory;
    }

    public boolean isResizeableActivityOverrideDisallowed(String str) {
        return getCachedInfo(str).mIsResizeableActivityOverrideDisallowed || containsOverride(ActivityInfo.FORCE_RESIZE_APP, str) || containsOverride(ActivityInfo.FORCE_NON_RESIZE_APP, str);
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isOrientationOverrideDisallowed(String str) {
        return isSamsungPackage(str) || getCachedInfo(str).mIsOrientationOverrideDisallowed || containsOverride(ActivityInfo.OVERRIDE_RESPECT_REQUESTED_ORIENTATION, str) || containsOverride(ActivityInfo.OVERRIDE_USE_DISPLAY_LANDSCAPE_NATURAL_ORIENTATION, str);
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isMinAspectRatioOverrideDisallowed(String str) {
        if (CoreRune.MT_APP_COMPAT_ASPECT_RATIO_SUPPORTED && !isSamsungPackage(str)) {
            CompatChangeablePackageInfo cachedInfo = getCachedInfo(str);
            if (!cachedInfo.mIsMinAspectRatioOverrideDisallowed && !cachedInfo.mIsActivityEmbeddingSplitsEnabled && !containsOverride(ActivityInfo.OVERRIDE_MIN_ASPECT_RATIO, str) && !containsOverride(ActivityInfo.OVERRIDE_MIN_ASPECT_RATIO_ONLY_FOR_CAMERA, str)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean readComponentProperty(PackageManager packageManager, String str, String str2) {
        try {
            return Boolean.valueOf(packageManager.getProperty(str2, str).getBoolean());
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public boolean containsOverride(long j, String str) {
        if (str == null) {
            Log.w(TAG, "containsOverride: PackageName is null.");
            return false;
        }
        IPlatformCompat iPlatformCompat = this.mPlatformCompat.get();
        if (iPlatformCompat == null) {
            Log.w(TAG, "containsOverride: PlatformCompat is null.");
            return false;
        }
        try {
            return iPlatformCompat.containsOverride(j, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public static String aspectRatioAndOrientationOverrideToString(int i) {
        return String.valueOf(i);
    }
}
