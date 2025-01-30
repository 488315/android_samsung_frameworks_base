package com.samsung.android.server.pm.install;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Xml;
import com.android.server.pm.PackageInstallerService;
import com.samsung.android.server.pm.install.PrePackageInstallerBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class ChnPrePackageInstaller extends PrePackageInstallerBase {
    public List mCscAddedAPKList;
    public List mCscInstallOnceAPKList;
    public List mCscUninstallPKGList;
    public List mCscUpdateAPKList;
    public HashSet mInstalledAppListDeviceInfo;
    public boolean mLoaded;
    public boolean mStorePreloadAppList;

    public ChnPrePackageInstaller(Context context, PackageInstallerService packageInstallerService, boolean z, boolean z2) {
        super(context, packageInstallerService, z, z2);
        this.mInstalledAppListDeviceInfo = new HashSet();
        this.mLoaded = false;
        this.mStorePreloadAppList = false;
        this.mCscAddedAPKList = new ArrayList();
        this.mCscUpdateAPKList = new ArrayList();
        this.mCscInstallOnceAPKList = new ArrayList();
        this.mCscUninstallPKGList = new ArrayList();
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void prepareInstall() {
        prepareSettings();
        checkNeedPreloadAppList();
        if (this.mIsUpgrade) {
            if (this.mStorePreloadAppList) {
                loadInstallAppListHashSet();
            }
            loadChinaCSCConfig();
            if (!this.mLoaded) {
                this.mLogMsg.out("CHN - loadChinaCSCConfig - no items to load -> setDisabled");
                setDisabled();
            } else {
                this.mLogMsg.out("fota - mount hidden area using persist values.");
                SystemProperties.set("persist.sys.storage_preload", "1");
                SystemClock.sleep(1000L);
                scanUninstallPkgChinaCSC();
            }
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void uninstallPackage() {
        super.uninstallPackage();
        this.mUninstallPackageList.forEach(new Consumer() { // from class: com.samsung.android.server.pm.install.ChnPrePackageInstaller$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ChnPrePackageInstaller.this.lambda$uninstallPackage$0((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uninstallPackage$0(String str) {
        if (this.mStorePreloadAppList) {
            this.mInstalledAppListDeviceInfo.remove(str);
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void putInstallHistory(String str) {
        super.putInstallHistory(str);
        if (this.mStorePreloadAppList) {
            this.mInstalledAppListDeviceInfo.add(str);
        }
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void setDisabled() {
        storeInstallAppListHashSet();
        super.setDisabled();
    }

    @Override // com.samsung.android.server.pm.install.PrePackageInstallerBase
    public void addInstallPackageList(File[] fileArr) {
        if (this.mLoaded) {
            for (File file : fileArr) {
                PrePackageInstallerBase.ApkFile apkFile = new PrePackageInstallerBase.ApkFile(file);
                if (needInstall(apkFile)) {
                    this.mInstallPackageList.add(apkFile);
                } else {
                    apkFile.removeCacheFile();
                }
            }
        }
    }

    public void storeInstallAppListHashSet() {
        if (this.mStorePreloadAppList) {
            this.mLogMsg.out("storeInstallAppListHashSet");
            if (this.mInstalledAppListDeviceInfo.isEmpty()) {
                return;
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/efs/sec_efs/preloadinstalled.lst");
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    try {
                        objectOutputStream.writeObject(this.mInstalledAppListDeviceInfo);
                        this.mLogMsg.out("SUCCESS : Stored INSTALLED_APPLIST file");
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Exception unused) {
                this.mLogMsg.out("FAIL : Error ocurred during storeInstallApplistHashSet for INSTALLED_APPLIST");
            }
        }
    }

    public final void checkNeedPreloadAppList() {
        this.mStorePreloadAppList = false;
    }

    public final void loadInstallAppListHashSet() {
        this.mLogMsg.out("loadInstallApplistHashSet");
        try {
            FileInputStream fileInputStream = new FileInputStream("/efs/sec_efs/preloadinstalled.lst");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    Object readObject = objectInputStream.readObject();
                    if (readObject instanceof HashSet) {
                        this.mInstalledAppListDeviceInfo = (HashSet) readObject;
                        this.mLogMsg.out("SUCCESS : Load INSTALLED_APPLIST file from previous one");
                    }
                    objectInputStream.close();
                    fileInputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception unused) {
            this.mLogMsg.out("FAIL : Error occurred during loadInstallApplistHashSet for INSTALLED_APPLIST");
        }
        if (this.mInstalledAppListDeviceInfo == null) {
            this.mLogMsg.out("Failed to load INSTALLED_APPLIST, Create new INSTALLED_APPLIST HashSet");
            this.mInstalledAppListDeviceInfo = new HashSet();
        }
    }

    public final boolean needInstall(PrePackageInstallerBase.ApkFile apkFile) {
        String apkFileName = apkFile.getApkFileName();
        if (apkFileName == null) {
            return false;
        }
        return (this.mCscAddedAPKList.contains(apkFileName) && !isInstalled(apkFile.getFile())) || (this.mCscUpdateAPKList.contains(apkFileName) && isInstalled(apkFile.getFile())) || !(!this.mCscInstallOnceAPKList.contains(apkFileName) || isInstalled(apkFile.getFile()) || isInstalledInHistory(apkFile.getFile()));
    }

    public boolean isInstalled(File file) {
        if (file != null && file.exists()) {
            PackageInfo packageArchiveInfo = this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0);
            if (packageArchiveInfo == null) {
                this.mLogMsg.out("pkgInfo is null");
                return false;
            }
            try {
                this.mPackageManager.getApplicationInfo(packageArchiveInfo.packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                this.mLogMsg.out("PackageManager can't search - " + packageArchiveInfo.packageName);
            }
        }
        return false;
    }

    public final boolean isInstalledInHistory(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        PackageInfo packageArchiveInfo = this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0);
        if (packageArchiveInfo == null) {
            this.mLogMsg.out("pkgInfo is null on isInstalledInHistory()");
            return false;
        }
        return this.mInstallHistory.contains(packageArchiveInfo.packageName);
    }

    public void loadChinaCSCConfig() {
        loadConfigApkListInCsc();
        this.mLogMsg.out("mCscAddedAPKList = " + this.mCscAddedAPKList.toString());
        this.mLogMsg.out("mCscUninstallPKGList = " + this.mCscUninstallPKGList.toString());
        this.mLogMsg.out("mCscInstallOnceAPKList = " + this.mCscInstallOnceAPKList.toString());
        this.mLogMsg.out("mCscUpdateAPKList = " + this.mCscUpdateAPKList.toString());
        if (this.mCscAddedAPKList.size() > 0 || this.mCscUpdateAPKList.size() > 0 || this.mCscInstallOnceAPKList.size() > 0 || this.mCscUninstallPKGList.size() > 0) {
            this.mLogMsg.out("items are loaded.");
            this.mLoaded = true;
        } else {
            this.mLogMsg.out("empty apk list, call setDisabled");
        }
    }

    public void loadConfigApkListInCsc() {
        parsePreloadPackages(getApkListPath());
    }

    public String getApkListPath() {
        String str = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
        if (str == null || TextUtils.isEmpty(str)) {
            this.mLogMsg.out("No file is existed for ChnPreloadApkList");
            return "";
        }
        String str2 = str + "/sysconfig/chn_preload_package_list.xml";
        this.mLogMsg.out("omcPath = " + str);
        return str2;
    }

    public void parsePreloadPackages(String str) {
        this.mLogMsg.out("Parsing the packages at " + str);
        File file = new File(str);
        if (!file.exists()) {
            this.mLogMsg.out("No XML file exists");
            return;
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parsePreloadPackagesInternal(newPullParser);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            this.mLogMsg.out("Failed to parse PreloadApkList. FileNotFoundException" + e);
        } catch (IOException e2) {
            this.mLogMsg.out("Failed to parse PreloadApkList. IOException " + e2);
        } catch (XmlPullParserException e3) {
            this.mLogMsg.out("Failed to parse PreloadApkList. XmlPullParserException " + e3);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x004b, code lost:
    
        if (r1.equals("add-apk") == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parsePreloadPackagesInternal(XmlPullParser xmlPullParser) {
        xmlPullParser.next();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            char c = 1;
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                name.hashCode();
                switch (name.hashCode()) {
                    case -1349104643:
                        if (name.equals("remove-package")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1149751568:
                        break;
                    case -297100520:
                        if (name.equals("update-apk")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -116432702:
                        if (name.equals("install-once-apk")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        this.mCscUninstallPKGList.addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case 1:
                        this.mCscAddedAPKList.addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case 2:
                        this.mCscUpdateAPKList.addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    case 3:
                        this.mCscInstallOnceAPKList.addAll(parsePackagesOrApks(xmlPullParser));
                        break;
                    default:
                        this.mLogMsg.out("Invalid element name: " + name);
                        break;
                }
            }
        }
    }

    public final List parsePackagesOrApks(XmlPullParser xmlPullParser) {
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

    public final void scanUninstallPkgChinaCSC() {
        ApplicationInfo applicationInfo;
        if (this.mCscUninstallPKGList.size() == 0) {
            return;
        }
        for (String str : this.mCscUninstallPKGList) {
            try {
                str = str.replaceAll("\\r\\n|\\r|\\n", "");
                applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                this.mLogMsg.out("Failed to getApplicationInfo :" + str);
                applicationInfo = null;
            }
            if (applicationInfo != null && !this.mUninstallPackageList.contains(str)) {
                this.mLogMsg.out("add to unInstallPackageList from csc xml pkg:" + str);
                this.mUninstallPackageList.add(str);
            }
        }
    }
}
