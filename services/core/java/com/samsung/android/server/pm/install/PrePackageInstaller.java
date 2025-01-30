package com.samsung.android.server.pm.install;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.server.pm.PackageInstallerService;
import com.samsung.android.server.pm.install.PrePackageInstallerBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class PrePackageInstaller extends PrePackageInstallerBase {
    public PrePackageInstaller(Context context, PackageInstallerService packageInstallerService, boolean z, boolean z2) {
        super(context, packageInstallerService, z, z2);
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void prepareInstall() {
        prepareSettings();
        this.mLogMsg.out("fota - mount hidden area using persist values.");
        SystemProperties.set("persist.sys.storage_preload", "1");
        SystemClock.sleep(1000L);
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void addInstallPackageList(File[] fileArr) {
        List readEnforceSkippingPackageList = readEnforceSkippingPackageList();
        for (File file : fileArr) {
            PrePackageInstallerBase.ApkFile apkFile = new PrePackageInstallerBase.ApkFile(file);
            String apkFileName = apkFile.getApkFileName();
            if (apkFileName != null) {
                if (readEnforceSkippingPackageList.contains(apkFileName)) {
                    this.mLogMsg.out(file.getName() + " is skipped by EnforceSkippingPackage");
                } else {
                    this.mLogMsg.out(file.getName() + " is Added on mInstallPackageList");
                    this.mInstallPackageList.add(apkFile);
                }
            }
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void removeInstalledPkgFromList() {
        super.removeInstalledPkgFromList();
        ArrayList arrayList = new ArrayList(this.mInstallPackageList);
        if (this.mIsUpgrade) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PrePackageInstallerBase.ApkFile apkFile = (PrePackageInstallerBase.ApkFile) it.next();
                File file = apkFile.getFile();
                if (isValidApkFile(file)) {
                    PackageInfo packageArchiveInfo = this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0);
                    try {
                        this.mPackageManager.getPackageInfo(packageArchiveInfo.packageName, 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        if (this.mInstallHistory.contains(packageArchiveInfo.packageName)) {
                            this.mLogMsg.out("removed by user : " + file.getAbsolutePath());
                            removeApkFileFromInstallList(apkFile);
                        }
                    }
                }
            }
        }
    }

    public List readEnforceSkippingPackageList() {
        ArrayList arrayList = new ArrayList();
        String str = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
        if (str == null || TextUtils.isEmpty(str)) {
            this.mLogMsg.out("No file is existed for enforced skip");
            return arrayList;
        }
        String[] strArr = {"/enforceskippingpackages.txt", "/enforcedeletepackage.txt"};
        for (int i = 0; i < 2; i++) {
            File file = new File(str + strArr[i]);
            if (file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
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
                    this.mLogMsg.out("file error on " + file);
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }
}
