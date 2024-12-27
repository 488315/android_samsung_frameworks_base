package com.android.server.pm.dex;

import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.FileUtils;
import android.os.ServiceManager;
import android.util.EventLog;

import com.android.server.pm.Installer;
import com.android.server.utils.WatchedArrayMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DynamicCodeLogger {
    public final Installer mInstaller;
    public final PackageDynamicCodeLoading mPackageDynamicCodeLoading;
    public IPackageManager mPackageManager;

    public DynamicCodeLogger(
            IPackageManager iPackageManager,
            Installer installer,
            PackageDynamicCodeLoading packageDynamicCodeLoading) {
        this.mPackageManager = iPackageManager;
        this.mInstaller = installer;
        this.mPackageDynamicCodeLoading = packageDynamicCodeLoading;
    }

    public DynamicCodeLogger(Installer installer) {
        this.mInstaller = installer;
        this.mPackageDynamicCodeLoading = new PackageDynamicCodeLoading();
    }

    public static boolean fileIsUnder(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        try {
            return FileUtils.contains(
                    new File(str2).getCanonicalPath(), new File(str).getCanonicalPath());
        } catch (IOException unused) {
            return false;
        }
    }

    public PackageDynamicCodeLoading.PackageDynamicCode getPackageDynamicCodeInfo(String str) {
        PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode;
        PackageDynamicCodeLoading packageDynamicCodeLoading = this.mPackageDynamicCodeLoading;
        synchronized (packageDynamicCodeLoading.mLock) {
            PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode2 =
                    (PackageDynamicCodeLoading.PackageDynamicCode)
                            ((HashMap) packageDynamicCodeLoading.mPackageMap).get(str);
            packageDynamicCode =
                    packageDynamicCode2 == null
                            ? null
                            : new PackageDynamicCodeLoading.PackageDynamicCode(packageDynamicCode2);
        }
        return packageDynamicCode;
    }

    public final IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager =
                    IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        }
        return this.mPackageManager;
    }

    public final void load(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            List list = (List) entry.getValue();
            Integer num = (Integer) entry.getKey();
            num.getClass();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((Set)
                                hashMap.computeIfAbsent(
                                        ((PackageInfo) it.next()).packageName,
                                        new DynamicCodeLogger$$ExternalSyntheticLambda0()))
                        .add(num);
            }
        }
        this.mPackageDynamicCodeLoading.read((WatchedArrayMap) null);
        PackageDynamicCodeLoading packageDynamicCodeLoading = this.mPackageDynamicCodeLoading;
        synchronized (packageDynamicCodeLoading.mLock) {
            try {
                Iterator it2 =
                        ((HashMap) packageDynamicCodeLoading.mPackageMap).entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    Set set = (Set) hashMap.get(entry2.getKey());
                    if (set == null) {
                        it2.remove();
                    } else {
                        PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode =
                                (PackageDynamicCodeLoading.PackageDynamicCode) entry2.getValue();
                        PackageDynamicCodeLoading.PackageDynamicCode.m783$$Nest$msyncData(
                                packageDynamicCode, hashMap, set);
                        if (((HashMap) packageDynamicCode.mFileUsageMap).isEmpty()) {
                            it2.remove();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void writeDclEvent(String str, int i, String str2) {
        EventLog.writeEvent(1397638484, str, Integer.valueOf(i), str2);
    }
}
