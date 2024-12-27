package com.samsung.android.server.pm.install;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PrePackageInstaller extends PrePackageInstallerBase {
    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public final void addInstallPackageList(File[] fileArr) {
        List readEnforceSkippingPackageList = readEnforceSkippingPackageList();
        for (File file : fileArr) {
            PrePackageInstallerBase.ApkFile apkFile = new PrePackageInstallerBase.ApkFile(file);
            String apkFileName = apkFile.getApkFileName();
            if (apkFileName != null) {
                boolean contains = readEnforceSkippingPackageList.contains(apkFileName);
                PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
                if (contains) {
                    localIntentReceiver.out(
                            file.getName() + " is skipped by EnforceSkippingPackage");
                } else {
                    localIntentReceiver.out(file.getName() + " is Added on mInstallPackageList");
                    this.mInstallPackageList.add(apkFile);
                }
            }
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public final void prepareInstall() {
        prepareSettings();
        this.mLogMsg.out("fota - mount hidden area using persist values.");
        SystemProperties.set("persist.sys.storage_preload", "1");
        SystemClock.sleep(1000L);
    }

    public List readEnforceSkippingPackageList() {
        ArrayList arrayList = new ArrayList();
        String str = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
        PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        if (str == null || TextUtils.isEmpty(str)) {
            localIntentReceiver.out("No file is existed for enforced skip");
            return arrayList;
        }
        String[] strArr = {"/enforceskippingpackages.txt", "/enforcedeletepackage.txt"};
        for (int i = 0; i < 2; i++) {
            File file =
                    new File(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                    str, strArr[i]));
            if (file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        InputStreamReader inputStreamReader =
                                new InputStreamReader(fileInputStream, "UTF-8");
                        try {
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            while (true) {
                                try {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        break;
                                    }
                                    if (!arrayList.contains(readLine)) {
                                        arrayList.add(readLine.trim());
                                    }
                                } catch (Throwable th) {
                                    try {
                                        bufferedReader.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            }
                            bufferedReader.close();
                            inputStreamReader.close();
                            fileInputStream.close();
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    localIntentReceiver.out("file error on " + file);
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public final void removeInstalledPkgFromList() {
        super.removeInstalledPkgFromList();
        ArrayList arrayList = new ArrayList(this.mInstallPackageList);
        if (this.mIsUpgrade) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PrePackageInstallerBase.ApkFile apkFile =
                        (PrePackageInstallerBase.ApkFile) it.next();
                File file = apkFile.getFile();
                if (PrePackageInstallerBase.isValidApkFile(file)) {
                    PackageInfo cachedPackageArchiveInfo = getCachedPackageArchiveInfo(file);
                    try {
                        this.mPackageManager.getPackageInfo(
                                cachedPackageArchiveInfo.packageName, 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        if (((HashSet) this.mInstallHistory)
                                .contains(cachedPackageArchiveInfo.packageName)) {
                            this.mLogMsg.out("removed by user : " + file.getAbsolutePath());
                            removeApkFileFromInstallList(apkFile);
                        }
                    }
                }
            }
        }
    }
}
