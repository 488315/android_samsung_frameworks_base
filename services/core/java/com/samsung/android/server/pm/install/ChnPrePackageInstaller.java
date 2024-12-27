package com.samsung.android.server.pm.install;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public final class ChnPrePackageInstaller extends PrePackageInstallerBase {
    public List mCscAddedAPKList;
    public List mCscInstallOnceAPKList;
    public List mCscUninstallPKGList;
    public List mCscUpdateAPKList;
    public HashSet mInstalledAppListDeviceInfo;
    public boolean mLoaded;

    public static List parsePackagesOrApks(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if ("apk".equals(name) || "package".equals(name)) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (!TextUtils.isEmpty(attributeValue) && !arrayList.contains(attributeValue)) {
                        arrayList.add(attributeValue);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x007b, code lost:

       if (r3 != false) goto L8;
    */
    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addInstallPackageList(java.io.File[] r7) {
        /*
            r6 = this;
            boolean r0 = r6.mLoaded
            if (r0 == 0) goto L8d
            int r0 = r7.length
            r1 = 0
            r2 = r1
        L7:
            if (r2 >= r0) goto L8d
            r3 = r7[r2]
            com.samsung.android.server.pm.install.PrePackageInstallerBase$ApkFile r4 = new com.samsung.android.server.pm.install.PrePackageInstallerBase$ApkFile
            r4.<init>(r3)
            java.lang.String r3 = r4.getApkFileName()
            if (r3 != 0) goto L18
        L16:
            r3 = r1
            goto L7e
        L18:
            java.util.List r5 = r6.mCscAddedAPKList
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            boolean r5 = r5.contains(r3)
            if (r5 == 0) goto L2c
            java.io.File r5 = r4.getFile()
            boolean r5 = r6.isInstalled(r5)
            if (r5 == 0) goto L7d
        L2c:
            java.util.List r5 = r6.mCscUpdateAPKList
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            boolean r5 = r5.contains(r3)
            if (r5 == 0) goto L40
            java.io.File r5 = r4.getFile()
            boolean r5 = r6.isInstalled(r5)
            if (r5 != 0) goto L7d
        L40:
            java.util.List r5 = r6.mCscInstallOnceAPKList
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            boolean r3 = r5.contains(r3)
            if (r3 == 0) goto L16
            java.io.File r3 = r4.getFile()
            boolean r3 = r6.isInstalled(r3)
            if (r3 != 0) goto L16
            java.io.File r3 = r4.getFile()
            if (r3 == 0) goto L6f
            boolean r5 = r3.exists()
            if (r5 != 0) goto L61
            goto L6f
        L61:
            android.content.pm.PackageInfo r3 = r6.getCachedPackageArchiveInfo(r3)
            if (r3 != 0) goto L71
            com.samsung.android.server.pm.install.PrePackageInstallerBase$LocalIntentReceiver r3 = r6.mLogMsg
            java.lang.String r5 = "pkgInfo is null on isInstalledInHistory()"
            r3.out(r5)
        L6f:
            r3 = r1
            goto L7b
        L71:
            java.lang.String r3 = r3.packageName
            java.util.Collection r5 = r6.mInstallHistory
            java.util.HashSet r5 = (java.util.HashSet) r5
            boolean r3 = r5.contains(r3)
        L7b:
            if (r3 != 0) goto L16
        L7d:
            r3 = 1
        L7e:
            if (r3 == 0) goto L86
            java.util.ArrayList r3 = r6.mInstallPackageList
            r3.add(r4)
            goto L89
        L86:
            r4.removeCacheFile()
        L89:
            int r2 = r2 + 1
            goto L7
        L8d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.install.ChnPrePackageInstaller.addInstallPackageList(java.io.File[]):void");
    }

    public boolean isInstalled(File file) {
        if (file != null && file.exists()) {
            PackageInfo cachedPackageArchiveInfo = getCachedPackageArchiveInfo(file);
            PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
            if (cachedPackageArchiveInfo == null) {
                localIntentReceiver.out("pkgInfo is null");
                return false;
            }
            try {
                this.mPackageManager.getApplicationInfo(cachedPackageArchiveInfo.packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                localIntentReceiver.out(
                        "PackageManager can't search - " + cachedPackageArchiveInfo.packageName);
            }
        }
        return false;
    }

    public void loadChinaCSCConfig() {
        loadConfigApkListInCsc();
        String str = "mCscAddedAPKList = " + this.mCscAddedAPKList.toString();
        PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        localIntentReceiver.out(str);
        localIntentReceiver.out("mCscUninstallPKGList = " + this.mCscUninstallPKGList.toString());
        localIntentReceiver.out(
                "mCscInstallOnceAPKList = " + this.mCscInstallOnceAPKList.toString());
        localIntentReceiver.out("mCscUpdateAPKList = " + this.mCscUpdateAPKList.toString());
        if (((ArrayList) this.mCscAddedAPKList).size() <= 0
                && ((ArrayList) this.mCscUpdateAPKList).size() <= 0
                && ((ArrayList) this.mCscInstallOnceAPKList).size() <= 0
                && ((ArrayList) this.mCscUninstallPKGList).size() <= 0) {
            localIntentReceiver.out("empty apk list, call setDisabled");
        } else {
            localIntentReceiver.out("items are loaded.");
            this.mLoaded = true;
        }
    }

    public void loadConfigApkListInCsc() {
        String str;
        String str2 = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
        PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        if (str2 == null || TextUtils.isEmpty(str2)) {
            localIntentReceiver.out("No file is existed for ChnPreloadApkList");
            str = "";
        } else {
            str = str2.concat("/sysconfig/chn_preload_package_list.xml");
            localIntentReceiver.out("omcPath = ".concat(str2));
        }
        localIntentReceiver.out("Parsing the packages at " + str);
        File file = new File(str);
        if (!file.exists()) {
            localIntentReceiver.out("No XML file exists");
            return;
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parsePreloadPackagesInternal(newPullParser);
                fileInputStream.close();
            } finally {
            }
        } catch (FileNotFoundException e) {
            localIntentReceiver.out("Failed to parse PreloadApkList. FileNotFoundException" + e);
        } catch (IOException e2) {
            localIntentReceiver.out("Failed to parse PreloadApkList. IOException " + e2);
        } catch (XmlPullParserException e3) {
            localIntentReceiver.out("Failed to parse PreloadApkList. XmlPullParserException " + e3);
        }
    }

    public final void parsePreloadPackagesInternal(XmlPullParser xmlPullParser) {
        String name;
        xmlPullParser.next();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                name = xmlPullParser.getName();
                name.getClass();
                switch (name) {
                    case "remove-package":
                        ((ArrayList) this.mCscUninstallPKGList)
                                .addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case "add-apk":
                        ((ArrayList) this.mCscAddedAPKList)
                                .addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case "update-apk":
                        ((ArrayList) this.mCscUpdateAPKList)
                                .addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case "install-once-apk":
                        ((ArrayList) this.mCscInstallOnceAPKList)
                                .addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    default:
                        this.mLogMsg.out("Invalid element name: ".concat(name));
                        break;
                }
            }
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public final void prepareInstall() {
        ApplicationInfo applicationInfo;
        prepareSettings();
        if (this.mIsUpgrade) {
            loadChinaCSCConfig();
            boolean z = this.mLoaded;
            PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver = this.mLogMsg;
            if (!z) {
                localIntentReceiver.out(
                        "CHN - loadChinaCSCConfig - no items to load -> setDisabled");
                super.setDisabled();
                return;
            }
            localIntentReceiver.out("fota - mount hidden area using persist values.");
            SystemProperties.set("persist.sys.storage_preload", "1");
            SystemClock.sleep(1000L);
            if (((ArrayList) this.mCscUninstallPKGList).size() == 0) {
                return;
            }
            Iterator it = ((ArrayList) this.mCscUninstallPKGList).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                try {
                    str = str.replaceAll("\\r\\n|\\r|\\n", "");
                    applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    localIntentReceiver.out("Failed to getApplicationInfo :" + str);
                    applicationInfo = null;
                }
                if (applicationInfo != null && !this.mUninstallPackageList.contains(str)) {
                    localIntentReceiver.out("add to unInstallPackageList from csc xml pkg:" + str);
                    this.mUninstallPackageList.add(str);
                }
            }
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public final void uninstallPackage() {
        super.uninstallPackage();
        this.mUninstallPackageList.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.server.pm.install.ChnPrePackageInstaller$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ChnPrePackageInstaller.this.getClass();
                    }
                });
    }
}
