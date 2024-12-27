package com.android.server.pm.dex;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.util.Log;
import android.util.Slog;
import android.util.jar.StrictJarFile;

import com.android.server.pm.PackageDexOptimizer;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;

public final class DexManager {
    public static final boolean DEBUG = Log.isLoggable("DexManager", 3);
    public final Context mContext;
    public final int mCriticalBatteryLevel;
    public final DynamicCodeLogger mDynamicCodeLogger;
    public final PowerManager mPowerManager;
    public BatteryManager mBatteryManager = null;
    public final Map mPackageCodeLocationsCache = new HashMap();
    public final PackageDexUsage mPackageDexUsage = new PackageDexUsage();

    public final class DexSearchResult {
        public final int mOutcome;
        public final String mOwningPackageName;

        public DexSearchResult(String str, int i) {
            this.mOwningPackageName = str;
            this.mOutcome = i;
        }

        public final String toString() {
            return this.mOwningPackageName
                    + PackageManagerShellCommandDataLoader.STDIN_PATH
                    + this.mOutcome;
        }
    }

    public final class PackageCodeLocations {
        public final Map mAppDataDirs;
        public String mBaseCodePath;
        public final String mPackageName;
        public final Set mSplitCodePaths;

        public PackageCodeLocations(String[] strArr, String str, String str2) {
            this.mPackageName = str;
            HashSet hashSet = new HashSet();
            this.mSplitCodePaths = hashSet;
            this.mAppDataDirs = new HashMap();
            this.mBaseCodePath = str2;
            hashSet.clear();
            if (strArr != null) {
                for (String str3 : strArr) {
                    ((HashSet) this.mSplitCodePaths).add(str3);
                }
            }
        }

        public final int searchDex(int i, String str) {
            Set set = (Set) ((HashMap) this.mAppDataDirs).get(Integer.valueOf(i));
            if (set == null) {
                return 0;
            }
            if (this.mBaseCodePath.equals(str)) {
                return 1;
            }
            if (((HashSet) this.mSplitCodePaths).contains(str)) {
                return 2;
            }
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (str.startsWith((String) it.next())) {
                    return 3;
                }
            }
            return 0;
        }
    }

    public final class RegisterDexModuleResult {}

    public DexManager(
            Context context,
            PackageDexOptimizer packageDexOptimizer,
            DynamicCodeLogger dynamicCodeLogger,
            IPackageManager iPackageManager) {
        this.mPowerManager = null;
        this.mContext = context;
        this.mDynamicCodeLogger = dynamicCodeLogger;
        if (context == null) {
            this.mCriticalBatteryLevel = 0;
            return;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPowerManager = powerManager;
        if (powerManager == null) {
            Slog.wtf("DexManager", "Power Manager is unavailable at time of Dex Manager start");
        }
        this.mCriticalBatteryLevel =
                context.getResources().getInteger(R.integer.config_defaultRefreshRate);
    }

    public static boolean auditUncompressedDexInApk(String str) {
        StrictJarFile strictJarFile = null;
        try {
            try {
                StrictJarFile strictJarFile2 = new StrictJarFile(str, false, false);
                try {
                    Iterator it = strictJarFile2.iterator();
                    boolean z = true;
                    while (it.hasNext()) {
                        ZipEntry zipEntry = (ZipEntry) it.next();
                        if (zipEntry.getName().endsWith(".dex")) {
                            if (zipEntry.getMethod() != 0) {
                                Slog.w(
                                        "DexManager",
                                        "APK "
                                                + str
                                                + " has compressed dex code "
                                                + zipEntry.getName());
                            } else if ((zipEntry.getDataOffset() & 3) != 0) {
                                Slog.w(
                                        "DexManager",
                                        "APK "
                                                + str
                                                + " has unaligned dex code "
                                                + zipEntry.getName());
                            }
                            z = false;
                        }
                    }
                    try {
                        strictJarFile2.close();
                    } catch (IOException unused) {
                    }
                    return z;
                } catch (IOException unused2) {
                    strictJarFile = strictJarFile2;
                    Slog.wtf("DexManager", "Error when parsing APK " + str);
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (IOException unused3) {
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    strictJarFile = strictJarFile2;
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (IOException unused4) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused5) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void cachePackageCodeLocation(
            String str, String str2, String[] strArr, String[] strArr2, int i) {
        synchronized (this.mPackageCodeLocationsCache) {
            try {
                Map map = this.mPackageCodeLocationsCache;
                PackageCodeLocations packageCodeLocations =
                        new PackageCodeLocations(strArr, str, str2);
                Object putIfAbsent = ((HashMap) map).putIfAbsent(str, packageCodeLocations);
                if (putIfAbsent != 0) {
                    packageCodeLocations = putIfAbsent;
                }
                PackageCodeLocations packageCodeLocations2 = packageCodeLocations;
                packageCodeLocations2.mBaseCodePath = str2;
                ((HashSet) packageCodeLocations2.mSplitCodePaths).clear();
                if (strArr != null) {
                    for (String str3 : strArr) {
                        ((HashSet) packageCodeLocations2.mSplitCodePaths).add(str3);
                    }
                }
                if (strArr2 != null) {
                    for (String str4 : strArr2) {
                        if (str4 != null) {
                            Map map2 = packageCodeLocations2.mAppDataDirs;
                            Integer valueOf = Integer.valueOf(i);
                            HashSet hashSet = new HashSet();
                            Object putIfAbsent2 = ((HashMap) map2).putIfAbsent(valueOf, hashSet);
                            if (putIfAbsent2 != 0) {
                                hashSet = putIfAbsent2;
                            }
                            hashSet.add(str4);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasInfoOnPackage(String str) {
        return this.mPackageDexUsage.getPackageUseInfo(str) != null;
    }

    public final void load(Map map) {
        try {
            loadInternal(map);
        } catch (RuntimeException e) {
            PackageDexUsage packageDexUsage = this.mPackageDexUsage;
            synchronized (packageDexUsage.mPackageUseInfoMap) {
                ((HashMap) packageDexUsage.mPackageUseInfoMap).clear();
                Slog.w("DexManager", "Exception while loading. Starting with a fresh state.", e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void loadInternal(Map map) {
        PackageDexUsage packageDexUsage = this.mPackageDexUsage;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            List<PackageInfo> list = (List) entry.getValue();
            Integer num = (Integer) entry.getKey();
            int intValue = num.intValue();
            for (PackageInfo packageInfo : list) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                cachePackageCodeLocation(
                        packageInfo.packageName,
                        applicationInfo.sourceDir,
                        applicationInfo.splitSourceDirs,
                        new String[] {
                            applicationInfo.dataDir,
                            applicationInfo.deviceProtectedDataDir,
                            applicationInfo.credentialProtectedDataDir
                        },
                        intValue);
                String str = packageInfo.packageName;
                HashSet hashSet = new HashSet();
                Object putIfAbsent = hashMap.putIfAbsent(str, hashSet);
                if (putIfAbsent != 0) {
                    hashSet = putIfAbsent;
                }
                hashSet.add(num);
                String str2 = packageInfo.packageName;
                HashSet hashSet2 = new HashSet();
                Object putIfAbsent2 = hashMap2.putIfAbsent(str2, hashSet2);
                if (putIfAbsent2 != 0) {
                    hashSet2 = putIfAbsent2;
                }
                HashSet hashSet3 = hashSet2;
                hashSet3.add(packageInfo.applicationInfo.sourceDir);
                String[] strArr = packageInfo.applicationInfo.splitSourceDirs;
                if (strArr != null) {
                    Collections.addAll(hashSet3, strArr);
                }
            }
        }
        try {
            packageDexUsage.read(null);
            packageDexUsage.syncData(hashMap, hashMap2, new ArrayList());
        } catch (RuntimeException e) {
            synchronized (packageDexUsage.mPackageUseInfoMap) {
                ((HashMap) packageDexUsage.mPackageUseInfoMap).clear();
                Slog.w(
                        "DexManager",
                        "Exception while loading package dex usage. Starting with a fresh state.",
                        e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x01d6, code lost:

       r8 = r20.mDynamicCodeLogger;
       r16 = 0;
       r19 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01f0, code lost:

       if (r8.mPackageDynamicCodeLoading.record(68, r24, r0.mOwningPackageName, r9, r21.packageName) == false) goto L106;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01f2, code lost:

       r8.mPackageDynamicCodeLoading.maybeWriteAsync(null);
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:139:0x035b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void notifyDexLoadInternal(
            android.content.pm.ApplicationInfo r21,
            java.util.Map r22,
            java.lang.String r23,
            int r24,
            boolean r25) {
        /*
            Method dump skipped, instructions count: 920
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.dex.DexManager.notifyDexLoadInternal(android.content.pm.ApplicationInfo,"
                    + " java.util.Map, java.lang.String, int, boolean):void");
    }

    public final void notifyPackageUpdated(String[] strArr, String str, String str2) {
        boolean z;
        cachePackageCodeLocation(str, str2, strArr, null, -1);
        PackageDexUsage packageDexUsage = this.mPackageDexUsage;
        synchronized (packageDexUsage.mPackageUseInfoMap) {
            try {
                PackageDexUsage.PackageUseInfo packageUseInfo =
                        (PackageDexUsage.PackageUseInfo)
                                ((HashMap) packageDexUsage.mPackageUseInfoMap).get(str);
                z = false;
                if (packageUseInfo != null) {
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(packageUseInfo.mPackageName);
                    Iterator it =
                            ((HashMap) packageUseInfo.mPrimaryCodePaths).entrySet().iterator();
                    while (it.hasNext()) {
                        if (((Set) ((Map.Entry) it.next()).getValue()).retainAll(arrayList)) {
                            z = true;
                        }
                    }
                }
            } finally {
            }
        }
        if (z) {
            this.mPackageDexUsage.maybeWriteAsync(null);
        }
    }
}
