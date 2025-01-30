package com.samsung.android.server.pm.install;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageManagerServiceUtils;
import com.samsung.android.server.pm.PmLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class PrePackageInstallerBase {
    public Context mContext;
    public String mFingerprint;
    public boolean mIsFirstBoot;
    public boolean mIsUpgrade;
    public PackageInstallerService mPackageInstallerService;
    public PackageManager mPackageManager;
    public SettingsProviderProxy mSettingsProviderProxy;
    public final ArrayList mInstallPackageList = new ArrayList();
    public final ArrayList mUninstallPackageList = new ArrayList();
    public String mHistoryForSettingProvider = "";
    public Collection mInstallHistory = new HashSet();
    public PrePackageInstallLogMsg mLogMsg = new PrePackageInstallLogMsg();

    public abstract void addInstallPackageList(File[] fileArr);

    public abstract void prepareInstall();

    public PrePackageInstallerBase(Context context, PackageInstallerService packageInstallerService, boolean z, boolean z2) {
        this.mContext = context;
        this.mPackageInstallerService = packageInstallerService;
        this.mIsFirstBoot = z;
        this.mIsUpgrade = z2;
        SettingsProviderProxy settingsProviderProxy = new SettingsProviderProxy(context);
        this.mSettingsProviderProxy = settingsProviderProxy;
        this.mFingerprint = settingsProviderProxy.getFingerprint();
        this.mPackageManager = this.mContext.getPackageManager();
    }

    public Future runPrePackageInstaller() {
        if (this.mIsFirstBoot || this.mIsUpgrade || !Build.FINGERPRINT.equals(this.mFingerprint)) {
            this.mLogMsg.outAndLogPackageFile("!@mIsUpgrade : " + this.mIsUpgrade + ", mIsFirstBoot : " + this.mIsFirstBoot + ", mFingerprint : " + this.mFingerprint);
            HandlerThread handlerThread = new HandlerThread("PrePackageInstallThread");
            handlerThread.start();
            final CompletableFuture completableFuture = new CompletableFuture();
            handlerThread.getThreadHandler().post(new Runnable() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PrePackageInstallerBase.this.lambda$runPrePackageInstaller$0(completableFuture);
                }
            });
            return completableFuture;
        }
        cleanupTempDir();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runPrePackageInstaller$0(CompletableFuture completableFuture) {
        startPrePackageInstall();
        cleanupTempDir();
        completableFuture.complete(Boolean.TRUE);
    }

    public final void cleanupTempDir() {
        File file = new File("/data/user_de/0/android/cache/PrePackageInstaller");
        if (file.exists()) {
            FileUtils.deleteContentsAndDir(file);
        }
    }

    public void startPrePackageInstall() {
        if (((DevicePolicyManager) this.mContext.getSystemService("device_policy")).getDeviceOwner() != null) {
            this.mLogMsg.out("DeviceOwner abnormal case!! -> setDisabled");
            setDisabled();
            return;
        }
        loadInstallHistory();
        prepareInstall();
        makeInstallPackageList();
        installPreloadPackageList();
        uninstallPackage();
        setDisabled();
    }

    public void prepareSettings() {
        if (!this.mIsFirstBoot && !this.mIsUpgrade) {
            String str = this.mFingerprint;
            if (str == null || str.isEmpty()) {
                this.mIsFirstBoot = true;
            } else {
                this.mIsUpgrade = true;
            }
        }
        String str2 = SystemProperties.get("persist.sys.storage_preload");
        this.mLogMsg.out("persist.sys.storage_preload : " + str2);
        if (!this.mIsFirstBoot || str2.equalsIgnoreCase("1")) {
            return;
        }
        this.mLogMsg.out("previously, Something's wrong.. mounting hidden as first booting");
        SystemProperties.set("persist.sys.storage_preload", "1");
        SystemClock.sleep(1000L);
    }

    public static boolean isChinaModel() {
        return Arrays.asList("CHZ", "CHN", "CHM", "CHU", "CTC", "CHC").contains(SystemProperties.get("ro.csc.sales_code"));
    }

    public void setDisabled() {
        SystemProperties.set("persist.sys.storage_preload", "0");
        this.mLogMsg.write("Set package state to disabled");
        if (this.mInstallPackageList.size() != 0) {
            this.mLogMsg.write("Waiting for cache flush...");
            SystemClock.sleep(1000L);
        }
        SystemProperties.set("persist.sys.storage_preload", "2");
        if (this.mIsFirstBoot) {
            setGrantPermissions();
            this.mLogMsg.out("call setGrantPermissions");
        }
        this.mSettingsProviderProxy.setFingerprint(Build.FINGERPRINT);
        Intent intent = new Intent("com.samsung.intent.action.PREINSTALLER_FINISH");
        intent.addFlags(16777216);
        this.mContext.sendBroadcast(intent);
        this.mLogMsg.writeAndLogPackageFile("!@setDisabled() is FINISHED");
    }

    /* renamed from: addPackageLocation, reason: merged with bridge method [inline-methods] */
    public final void lambda$makeInstallPackageList$2(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || (listFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                lambda$makeInstallPackageList$2(file2.getPath());
            }
        }
        File[] listFiles2 = file.listFiles(new FilenameFilter() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda2
            @Override // java.io.FilenameFilter
            public final boolean accept(File file3, String str2) {
                boolean lambda$addPackageLocation$1;
                lambda$addPackageLocation$1 = PrePackageInstallerBase.lambda$addPackageLocation$1(file3, str2);
                return lambda$addPackageLocation$1;
            }
        });
        if (listFiles2 == null) {
            return;
        }
        addInstallPackageList(listFiles2);
    }

    public static /* synthetic */ boolean lambda$addPackageLocation$1(File file, String str) {
        return str.endsWith(".apk") || str.endsWith(".apk.gz");
    }

    public final void installPreloadPackageList() {
        if (this.mInstallPackageList.size() == 0) {
            this.mLogMsg.out("apk count is 0. call setDisabled()");
            setDisabled();
            return;
        }
        Iterator it = this.mInstallPackageList.iterator();
        while (it.hasNext()) {
            ApkFile apkFile = (ApkFile) it.next();
            File file = apkFile.getFile();
            if (!isValidApkFile(file)) {
                this.mLogMsg.out(" Invalid file: " + file);
            } else {
                if (this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0) != null) {
                    installPreloadPackage(file);
                } else {
                    this.mLogMsg.out(" Skip..." + file.getAbsolutePath());
                }
                apkFile.removeCacheFile();
            }
        }
        this.mLogMsg.out("installPreloadPackageList() --------------------- COMPLETE");
    }

    public final void installPreloadPackage(File file) {
        FileInputStream fileInputStream;
        this.mLogMsg.outAndLogPackageFile("!@INSTALL ------------------ " + file.getName());
        try {
            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
            setInstallFlags(sessionParams);
            PackageInstaller.Session session = new PackageInstaller.Session(this.mPackageInstallerService.openSession(this.mPackageInstallerService.createSession(sessionParams, "PrePackageInstaller", this.mContext.getAttributionTag(), 0)));
            long currentTimeMillis = System.currentTimeMillis();
            this.mLogMsg.out("Write : " + file.getName());
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e) {
                this.mLogMsg.out(e + " : ignored");
            }
            try {
                OutputStream openWrite = session.openWrite(file.getName(), 0L, file.length());
                try {
                    byte[] bArr = new byte[65536];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        } else {
                            openWrite.write(bArr, 0, read);
                        }
                    }
                    session.fsync(openWrite);
                    if (openWrite != null) {
                        openWrite.close();
                    }
                    fileInputStream.close();
                    this.mLogMsg.out("Write Done :" + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                    LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                    session.commit(localIntentReceiver.getIntentSender());
                    session.close();
                    Intent result = localIntentReceiver.getResult();
                    if (result.getIntExtra("android.content.pm.extra.STATUS", 1) == 0) {
                        this.mLogMsg.out("SUCCESS install, " + file);
                        putInstallHistory(result.getStringExtra("android.content.pm.extra.PACKAGE_NAME"));
                        return;
                    }
                    this.mLogMsg.out("FAIL install, " + file + ", msg = " + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE"));
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            this.mLogMsg.out("error #2, " + e2);
        }
    }

    public void putInstallHistory(String str) {
        if (this.mInstallHistory.contains(str)) {
            return;
        }
        this.mInstallHistory.add(str);
        String concat = this.mHistoryForSettingProvider.concat(str + KnoxVpnFirewallHelper.DELIMITER);
        this.mHistoryForSettingProvider = concat;
        this.mSettingsProviderProxy.setInstallHistory(concat);
        this.mLogMsg.write("saveInstallHistory() : " + str);
    }

    public final void setInstallFlags(PackageInstaller.SessionParams sessionParams) {
        sessionParams.installFlags |= 67108864;
        sessionParams.sessionFlags |= 33554432;
    }

    public class LocalIntentReceiver {
        public IIntentSender.Stub mLocalSender;
        public final LinkedBlockingQueue mResult;

        public LocalIntentReceiver() {
            this.mResult = new LinkedBlockingQueue();
            this.mLocalSender = new IIntentSender.Stub() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase.LocalIntentReceiver.1
                public void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) {
                    try {
                        LocalIntentReceiver.this.mResult.offer(intent, 60L, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }

        public IntentSender getIntentSender() {
            return new IntentSender(this.mLocalSender);
        }

        public Intent getResult() {
            try {
                return (Intent) this.mResult.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final void makeInstallPackageList() {
        String cscCode = getCscCode();
        this.mLogMsg.out("cscCode : " + cscCode);
        ArrayList arrayList = new ArrayList(Arrays.asList("/system/preload", "/prism/preload/" + cscCode + "/hidden_app", "/system/carrier/preload", "/product/preload/" + cscCode + "/hidden_app", "/product/preload/Common_app"));
        if (!this.mIsUpgrade) {
            arrayList.add("/system/preloadFactoryResetOnly");
        }
        arrayList.forEach(new Consumer() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrePackageInstallerBase.this.lambda$makeInstallPackageList$2((String) obj);
            }
        });
        removeEmptyApks();
        removeInstalledPkgFromList();
        this.mLogMsg.out("[CONFIRMED INSTALLING LIST]");
        Iterator it = this.mInstallPackageList.iterator();
        while (it.hasNext()) {
            File file = ((ApkFile) it.next()).getFile();
            if (isValidApkFile(file)) {
                this.mLogMsg.out("TO INSTALL :: " + file.getAbsolutePath());
            }
        }
    }

    public boolean isValidApkFile(File file) {
        return file != null && file.exists() && file.getName().endsWith(".apk");
    }

    public final void removeEmptyApks() {
        Iterator it = new ArrayList(this.mInstallPackageList).iterator();
        while (it.hasNext()) {
            ApkFile apkFile = (ApkFile) it.next();
            File file = apkFile.getFile();
            if (file == null || file.length() == 0) {
                this.mLogMsg.out("remove a null or an empty file: " + apkFile);
                removeApkFileFromInstallList(apkFile);
            }
        }
    }

    public final void setGrantPermissions() {
        String str = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
        if (str == null || TextUtils.isEmpty(str)) {
            this.mLogMsg.out("No file is existed for granting permissions");
            return;
        }
        String str2 = str + "/grantpermissionslist.txt";
        this.mLogMsg.out("grantPermission file path : " + str2);
        File file = new File(str2);
        if (!file.exists()) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                if (readLine.length() > 0 && !readLine.startsWith("#")) {
                                    String[] split = readLine.split("/");
                                    if (split.length == 2) {
                                        try {
                                            this.mPackageManager.grantRuntimePermission(split[0], split[1], UserHandle.OWNER);
                                            this.mLogMsg.out("grantPermission Pkg : " + split[0] + " , Request : " + split[1]);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            this.mLogMsg.out("[Error] grantPermission Pkg : " + split[0] + " , Request : " + split[1]);
                                        }
                                    }
                                }
                            } else {
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                return;
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            this.mLogMsg.out("file error on " + str2);
            e2.printStackTrace();
        }
    }

    public void removeApkFileFromInstallList(ApkFile apkFile) {
        apkFile.removeCacheFile();
        this.mInstallPackageList.remove(apkFile);
    }

    public void removeInstalledPkgFromList() {
        Iterator it = new ArrayList(this.mInstallPackageList).iterator();
        while (it.hasNext()) {
            ApkFile apkFile = (ApkFile) it.next();
            File file = apkFile.getFile();
            if (isValidApkFile(file)) {
                try {
                    PackageInfo packageArchiveInfo = this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0);
                    if (packageArchiveInfo != null) {
                        String str = packageArchiveInfo.packageName;
                        PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (this.mIsUpgrade) {
                            if (isExistHigherVersionPkg(packageArchiveInfo, packageInfo)) {
                                this.mLogMsg.out("exists higher Version: " + file.getAbsolutePath());
                                removeApkFileFromInstallList(apkFile);
                                putInstallHistory(str);
                            }
                        } else if (packageInfo != null) {
                            this.mLogMsg.out("already installed : " + file.getAbsolutePath());
                            removeApkFileFromInstallList(apkFile);
                            putInstallHistory(str);
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    this.mLogMsg.out("new : " + file.getAbsolutePath());
                } catch (NullPointerException e) {
                    removeApkFileFromInstallList(apkFile);
                    this.mLogMsg.out("something wrong occurred " + file.getAbsolutePath() + ", e = " + e);
                }
            }
        }
        this.mLogMsg.out("fin");
    }

    public final boolean isExistHigherVersionPkg(PackageInfo packageInfo, PackageInfo packageInfo2) {
        String str;
        String str2;
        if (packageInfo2 == null || (str = packageInfo2.versionName) == null || (str2 = packageInfo.versionName) == null || packageInfo.versionCode > packageInfo2.versionCode) {
            return false;
        }
        return str.compareTo(str2) > 0 || packageInfo2.versionName.compareTo(packageInfo.versionName) == 0;
    }

    public void uninstallPackage() {
        addUninstallPkgList(new File("/system/etc/uninstall_preloadpkg.lst"));
        if (isOmcModel()) {
            String str = SystemProperties.get("mdc.sys.omc_etcpath") + "/uninstall_preloadpkg.lst";
            File file = new File(str);
            this.mLogMsg.out("omcEtcPath : " + str);
            addUninstallPkgList(file);
        }
        Iterator it = this.mUninstallPackageList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            this.mLogMsg.out("deletePackage :" + str2);
            try {
                this.mPackageManager.deletePackage(str2, null, 2);
            } catch (Exception e) {
                this.mLogMsg.out(e.toString());
            }
        }
    }

    public final void addUninstallPkgList(File file) {
        if (!file.exists()) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                if (readLine.length() > 0 && !readLine.startsWith("#") && !this.mUninstallPackageList.contains(readLine)) {
                                    this.mLogMsg.out("add to unInstallPackageList from list omcFile - pkg:" + readLine);
                                    this.mUninstallPackageList.add(readLine);
                                }
                            } else {
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                return;
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            this.mLogMsg.out(e.toString());
        }
    }

    public final void loadInstallHistory() {
        this.mInstallHistory.clear();
        this.mHistoryForSettingProvider = this.mSettingsProviderProxy.getInstallHistory();
        this.mLogMsg.write("loadInstallHistory() : " + this.mHistoryForSettingProvider);
        String str = this.mHistoryForSettingProvider;
        if (str != null) {
            for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER)) {
                this.mInstallHistory.add(str2);
            }
            return;
        }
        this.mHistoryForSettingProvider = "";
    }

    public final String getCscCode() {
        try {
            String str = SystemProperties.get("ro.csc.sales_code");
            return TextUtils.isEmpty(str) ? SystemProperties.get("ril.sales_code") : str;
        } catch (Exception unused) {
            this.mLogMsg.out("sales_code reading failed");
            return "";
        }
    }

    public final boolean isOmcModel() {
        return SystemProperties.get("persist.sys.omc_support").equals("true");
    }

    public class SettingsProviderProxy {
        public static Context mContext;

        public SettingsProviderProxy(Context context) {
            mContext = context;
        }

        public String getFingerprint() {
            return Settings.System.getString(mContext.getContentResolver(), "preload_fingerprint");
        }

        public void setFingerprint(String str) {
            Settings.System.putString(mContext.getContentResolver(), "preload_fingerprint", str);
        }

        public String getInstallHistory() {
            return Settings.System.getString(mContext.getContentResolver(), "preload_install_history");
        }

        public void setInstallHistory(String str) {
            Settings.System.putString(mContext.getContentResolver(), "preload_install_history", str);
        }
    }

    public void setPackageManager(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    public class PrePackageInstallLogMsg {
        public StringBuffer outputContents = new StringBuffer();
        public SimpleDateFormat sdfNow = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

        public PrePackageInstallLogMsg() {
            out("");
        }

        public void outAndLogPackageFile(String str) {
            PmLog.logDebugInfo(str);
            out(str);
        }

        public void out(String str) {
            String format = this.sdfNow.format(new Date(System.currentTimeMillis()));
            Slog.i("PrePackageInstaller", str);
            synchronized (this.outputContents) {
                this.outputContents.append(format + " : " + str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            }
        }

        public void writeAndLogPackageFile(String str) {
            outAndLogPackageFile(str);
            flush();
        }

        public void write(String str) {
            out(str);
            flush();
        }

        public final void flush() {
            File file = new File("/data/log/PreloadInstaller.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    try {
                        synchronized (this.outputContents) {
                            outputStreamWriter.write(this.outputContents.toString());
                            this.outputContents.setLength(0);
                        }
                        file.setReadable(true, false);
                        outputStreamWriter.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.w("PrePackageInstaller", e);
            }
        }
    }

    public class ApkFile {
        public File mDecompressedFile;
        public File mFile;

        public ApkFile(File file) {
            this.mFile = file;
        }

        public String getApkFileName() {
            File file = this.mFile;
            if (file != null && file.exists()) {
                String name = this.mFile.getName();
                if (name.endsWith(".apk")) {
                    return name;
                }
                if (name.endsWith(".apk.gz")) {
                    return name.replace(".gz", "");
                }
            }
            return null;
        }

        public File getFile() {
            File file = this.mFile;
            if (file != null && file.exists()) {
                if (this.mFile.getName().endsWith(".apk")) {
                    return this.mFile;
                }
                if (this.mFile.getName().endsWith(".apk.gz")) {
                    File decompressedApkFile = getDecompressedApkFile(this.mFile.getName());
                    if (decompressedApkFile.exists()) {
                        return decompressedApkFile;
                    }
                    File parentFile = decompressedApkFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    try {
                        Slog.d("PrePackageInstaller", "Decompressing " + this.mFile + " to " + decompressedApkFile);
                        PackageManagerServiceUtils.decompressFile(this.mFile, decompressedApkFile);
                        this.mDecompressedFile = decompressedApkFile;
                    } catch (Exception unused) {
                        Slog.d("PrePackageInstaller", "Failed to decompress " + this.mFile);
                        this.mDecompressedFile = null;
                    }
                    return this.mDecompressedFile;
                }
            }
            return null;
        }

        public void removeCacheFile() {
            File file = this.mFile;
            if (file != null && file.exists() && this.mFile.getName().endsWith(".apk.gz")) {
                if (this.mDecompressedFile == null) {
                    File decompressedApkFile = getDecompressedApkFile(this.mFile.getName());
                    if (decompressedApkFile.exists()) {
                        this.mDecompressedFile = decompressedApkFile;
                    }
                }
                File file2 = this.mDecompressedFile;
                if (file2 == null || !file2.exists()) {
                    return;
                }
                Slog.d("PrePackageInstaller", "Removing " + this.mDecompressedFile);
                this.mDecompressedFile.delete();
            }
        }

        public final File getDecompressedApkFile(String str) {
            String replace = str.replace(".apk.gz", "");
            return new File(new File("/data/user_de/0/android/cache/PrePackageInstaller", replace), str.replace(".gz", ""));
        }

        public String toString() {
            return "ApkFile{origin: " + this.mFile + ", decompressed: " + this.mDecompressedFile + "}";
        }
    }
}
