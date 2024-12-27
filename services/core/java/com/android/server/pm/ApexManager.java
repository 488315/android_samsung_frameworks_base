package com.android.server.pm;

import android.apex.ApexInfo;
import android.apex.ApexInfoList;
import android.apex.ApexSessionInfo;
import android.apex.ApexSessionParams;
import android.apex.IApexService;
import android.os.Binder;
import android.os.Environment;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Singleton;
import android.util.Slog;

import com.android.internal.pm.pkg.component.ParsedApexSystemService;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.build.UnboundedSdkLevel;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.utils.TimingsTraceAndSlog;

import com.google.android.collect.Lists;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class ApexManager {
    public static final AnonymousClass1 sApexManagerSingleton = new AnonymousClass1();

    /* renamed from: com.android.server.pm.ApexManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new ApexManagerImpl();
        }
    }

    public final class ActiveApexInfo {
        public final boolean activeApexChanged;
        public final File apexDirectory;
        public final File apexFile;
        public final String apexModuleName;
        public final boolean isFactory;
        public final File preInstalledApexPath;

        public ActiveApexInfo(ApexInfo apexInfo) {
            String str = apexInfo.moduleName;
            File file =
                    new File(Environment.getApexDirectory() + File.separator + apexInfo.moduleName);
            File file2 = new File(apexInfo.preinstalledModulePath);
            boolean z = apexInfo.isFactory;
            File file3 = new File(apexInfo.modulePath);
            boolean z2 = apexInfo.activeApexChanged;
            this.apexModuleName = str;
            this.apexDirectory = file;
            this.preInstalledApexPath = file2;
            this.isFactory = z;
            this.apexFile = file3;
            this.activeApexChanged = z2;
        }
    }

    public class ApexManagerImpl extends ApexManager {
        public Set mActiveApexInfosCache;
        public ArrayMap mApexModuleNameToActivePackageName;
        public ArrayMap mPackageNameToApexModuleName;
        public final Object mLock = new Object();
        public final List mApexSystemServices = new ArrayList();
        public final ArrayMap mApksInApex = new ArrayMap();
        public final Map mErrorWithApkInApex = new ArrayMap();

        @Override // com.android.server.pm.ApexManager
        public final List getActiveApexInfos() {
            TimingsTraceAndSlog timingsTraceAndSlog =
                    new TimingsTraceAndSlog(262144L, "ApexManagerTiming");
            synchronized (this.mLock) {
                if (this.mActiveApexInfosCache == null) {
                    timingsTraceAndSlog.traceBegin("getActiveApexInfos_noCache");
                    try {
                        this.mActiveApexInfosCache = new ArraySet();
                        for (ApexInfo apexInfo : waitForApexService().getActivePackages()) {
                            this.mActiveApexInfosCache.add(new ActiveApexInfo(apexInfo));
                        }
                    } catch (RemoteException e) {
                        Slog.e("ApexManager", "Unable to retrieve packages from apexservice", e);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mActiveApexInfosCache != null) {
                    return new ArrayList(this.mActiveApexInfosCache);
                }
                return Collections.emptyList();
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final String getActiveApexPackageNameContainingPackage(String str) {
            Objects.requireNonNull(str);
            synchronized (this.mLock) {
                try {
                    Preconditions.checkState(
                            this.mPackageNameToApexModuleName != null,
                            "APEX packages have not been scanned");
                    int size = this.mApksInApex.size();
                    for (int i = 0; i < size; i++) {
                        if (((List) this.mApksInApex.valueAt(i)).contains(str)) {
                            String str2 = (String) this.mApksInApex.keyAt(i);
                            int size2 = this.mPackageNameToApexModuleName.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                if (((String) this.mPackageNameToApexModuleName.valueAt(i2))
                                        .equals(str2)) {
                                    return (String) this.mPackageNameToApexModuleName.keyAt(i2);
                                }
                            }
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final String getActivePackageNameForApexModuleName(String str) {
            String str2;
            synchronized (this.mLock) {
                Preconditions.checkState(
                        this.mApexModuleNameToActivePackageName != null,
                        "APEX packages have not been scanned");
                str2 = (String) this.mApexModuleNameToActivePackageName.get(str);
            }
            return str2;
        }

        @Override // com.android.server.pm.ApexManager
        public final String getApexModuleNameForPackageName(String str) {
            String str2;
            synchronized (this.mLock) {
                Preconditions.checkState(
                        this.mPackageNameToApexModuleName != null,
                        "APEX packages have not been scanned");
                str2 = (String) this.mPackageNameToApexModuleName.get(str);
            }
            return str2;
        }

        @Override // com.android.server.pm.ApexManager
        public List getApksInApex(String str) {
            synchronized (this.mLock) {
                try {
                    Preconditions.checkState(
                            this.mPackageNameToApexModuleName != null,
                            "APEX packages have not been scanned");
                    String str2 = (String) this.mPackageNameToApexModuleName.get(str);
                    if (str2 == null) {
                        return Collections.emptyList();
                    }
                    return (List) this.mApksInApex.getOrDefault(str2, Collections.emptyList());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final File getBackingApexFile(File file) {
            Path path = file.toPath();
            if (!path.startsWith(Environment.getApexDirectory().toPath())
                    || path.getNameCount() < 2) {
                return null;
            }
            String path2 = file.toPath().getName(1).toString();
            List activeApexInfos = getActiveApexInfos();
            for (int i = 0; i < activeApexInfos.size(); i++) {
                if (((ActiveApexInfo) activeApexInfos.get(i)).apexModuleName.equals(path2)) {
                    return ((ActiveApexInfo) activeApexInfos.get(i)).apexFile;
                }
            }
            return null;
        }

        @Override // com.android.server.pm.ApexManager
        public final ApexSessionInfo getStagedSessionInfo(int i) {
            try {
                ApexSessionInfo stagedSessionInfo = waitForApexService().getStagedSessionInfo(i);
                if (stagedSessionInfo.isUnknown) {
                    return null;
                }
                return stagedSessionInfo;
            } catch (RemoteException e) {
                Slog.e("ApexManager", "Unable to contact apexservice", e);
                throw new RuntimeException(e);
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final void markStagedSessionSuccessful(int i) {
            try {
                waitForApexService().markStagedSessionSuccessful(i);
            } catch (RemoteException e) {
                Slog.e("ApexManager", "Unable to contact apexservice", e);
                throw new RuntimeException(e);
            } catch (Exception e2) {
                Slog.e("ApexManager", "Failed to mark session " + i + " as successful", e2);
            }
        }

        public final void notifyScanResultLocked(List list) {
            this.mPackageNameToApexModuleName = new ArrayMap();
            this.mApexModuleNameToActivePackageName = new ArrayMap();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                ApexInfo apexInfo = scanResult.apexInfo;
                for (ParsedApexSystemService parsedApexSystemService :
                        scanResult.pkg.getApexSystemServices()) {
                    String minSdkVersion = parsedApexSystemService.getMinSdkVersion();
                    if (minSdkVersion == null || UnboundedSdkLevel.isAtLeast(minSdkVersion)) {
                        String maxSdkVersion = parsedApexSystemService.getMaxSdkVersion();
                        if (maxSdkVersion != null && !UnboundedSdkLevel.isAtMost(maxSdkVersion)) {
                            Slog.d(
                                    "ApexManager",
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "ApexSystemService ",
                                            parsedApexSystemService.getName(),
                                            " with max_sdk_version=",
                                            parsedApexSystemService.getMaxSdkVersion(),
                                            " is skipped"));
                        } else if (apexInfo.isActive) {
                            String name = parsedApexSystemService.getName();
                            for (int i = 0;
                                    i < ((ArrayList) this.mApexSystemServices).size();
                                    i++) {
                                ApexSystemServiceInfo apexSystemServiceInfo =
                                        (ApexSystemServiceInfo)
                                                ((ArrayList) this.mApexSystemServices).get(i);
                                if (apexSystemServiceInfo.mName.equals(name)) {
                                    throw new IllegalStateException(
                                            TextUtils.formatSimple(
                                                    "Duplicate apex-system-service %s from %s, %s",
                                                    new Object[] {
                                                        name,
                                                        apexSystemServiceInfo.mJarPath,
                                                        parsedApexSystemService.getJarPath()
                                                    }));
                                }
                            }
                            ((ArrayList) this.mApexSystemServices)
                                    .add(
                                            new ApexSystemServiceInfo(
                                                    parsedApexSystemService.getInitOrder(),
                                                    parsedApexSystemService.getName(),
                                                    parsedApexSystemService.getJarPath()));
                        } else {
                            continue;
                        }
                    } else {
                        Slog.d(
                                "ApexManager",
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "ApexSystemService ",
                                        parsedApexSystemService.getName(),
                                        " with min_sdk_version=",
                                        parsedApexSystemService.getMinSdkVersion(),
                                        " is skipped"));
                    }
                }
                Collections.sort(this.mApexSystemServices);
                ArrayMap arrayMap = this.mPackageNameToApexModuleName;
                String str = apexInfo.moduleName;
                String str2 = scanResult.packageName;
                arrayMap.put(str2, str);
                if (apexInfo.isActive) {
                    if (this.mApexModuleNameToActivePackageName.containsKey(apexInfo.moduleName)) {
                        throw new IllegalStateException(
                                "Two active packages have the same APEX module name: "
                                        + apexInfo.moduleName);
                    }
                    this.mApexModuleNameToActivePackageName.put(apexInfo.moduleName, str2);
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final void registerApkInApex(AndroidPackage androidPackage) {
            synchronized (this.mLock) {
                try {
                    Iterator it = ((ArraySet) this.mActiveApexInfosCache).iterator();
                    while (it.hasNext()) {
                        ActiveApexInfo activeApexInfo = (ActiveApexInfo) it.next();
                        if (androidPackage
                                .getBaseApkPath()
                                .startsWith(
                                        activeApexInfo.apexDirectory.getAbsolutePath()
                                                + File.separator)) {
                            List list = (List) this.mApksInApex.get(activeApexInfo.apexModuleName);
                            if (list == null) {
                                list = Lists.newArrayList();
                                this.mApksInApex.put(activeApexInfo.apexModuleName, list);
                            }
                            Slog.i(
                                    "ApexManager",
                                    "Registering "
                                            + androidPackage.getPackageName()
                                            + " as apk-in-apex of "
                                            + activeApexInfo.apexModuleName);
                            list.add(androidPackage.getPackageName());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final boolean revertActiveSessions() {
            try {
                waitForApexService().revertActiveSessions();
                return true;
            } catch (RemoteException e) {
                Slog.e("ApexManager", "Unable to contact apexservice", e);
                return false;
            } catch (Exception e2) {
                Slog.e("ApexManager", e2.getMessage(), e2);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public final ApexInfoList submitStagedSession(ApexSessionParams apexSessionParams) {
            try {
                ApexInfoList apexInfoList = new ApexInfoList();
                waitForApexService().submitStagedSession(apexSessionParams, apexInfoList);
                return apexInfoList;
            } catch (RemoteException e) {
                Slog.e("ApexManager", "Unable to contact apexservice", e);
                throw new RuntimeException(e);
            } catch (Exception e2) {
                throw new PackageManagerException(
                        -22, "apexd verification failed : " + e2.getMessage());
            }
        }

        public IApexService waitForApexService() {
            return IApexService.Stub.asInterface(
                    Binder.allowBlocking(ServiceManager.waitForService("apexservice")));
        }
    }

    public final class ScanResult {
        public final ApexInfo apexInfo;
        public final String packageName;
        public final AndroidPackage pkg;

        public ScanResult(ApexInfo apexInfo, AndroidPackage androidPackage, String str) {
            this.apexInfo = apexInfo;
            this.pkg = androidPackage;
            this.packageName = str;
        }
    }

    public static ApexManager getInstance() {
        return (ApexManager) sApexManagerSingleton.get();
    }

    public abstract List getActiveApexInfos();

    public abstract String getActiveApexPackageNameContainingPackage(String str);

    public abstract String getActivePackageNameForApexModuleName(String str);

    public abstract String getApexModuleNameForPackageName(String str);

    public abstract List getApksInApex(String str);

    public abstract File getBackingApexFile(File file);

    public abstract ApexSessionInfo getStagedSessionInfo(int i);

    public abstract void markStagedSessionSuccessful(int i);

    public abstract void registerApkInApex(AndroidPackage androidPackage);

    public abstract boolean revertActiveSessions();

    public abstract ApexInfoList submitStagedSession(ApexSessionParams apexSessionParams);
}
