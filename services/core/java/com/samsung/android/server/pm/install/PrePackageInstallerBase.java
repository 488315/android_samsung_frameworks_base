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
import android.os.Environment;
import android.os.FileUtils;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageManagerServiceUtils;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.PmSharedPreferences;
import com.samsung.android.server.pm.install.PrePackageInstallerBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PrePackageInstallerBase {
    public final Context mContext;
    public int mFailCount;
    public final String mFingerprint;
    public boolean mIsFirstBoot;
    public boolean mIsUpgrade;
    public final LocalIntentReceiver mLogMsg;
    public final PackageInstallerService mPackageInstallerService;
    public PackageManager mPackageManager;
    public final SettingsProviderProxy mSettingsProviderProxy;
    public int mSkipCount;
    public int mSuccessCount;
    public final ArrayList mInstallPackageList = new ArrayList();
    public final ArrayList mUninstallPackageList = new ArrayList();
    public final HashMap mCachedPackageArchiveInfo = new HashMap();
    public final Object mLock = new Object();
    public String mHistoryForSettingProvider = "";
    public final Collection mInstallHistory = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ApkFile {
        public File mDecompressedFile;
        public final File mFile;

        public ApkFile(File file) {
            this.mFile = file;
        }

        public final String getApkFileName() {
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

        public final File getFile() {
            File file = this.mFile;
            if (file != null && file.exists()) {
                if (this.mFile.getName().endsWith(".apk")) {
                    return this.mFile;
                }
                if (this.mFile.getName().endsWith(".apk.gz")) {
                    String name = this.mFile.getName();
                    File file2 = new File(new File("/data/user_de/0/android/cache/PrePackageInstaller", name.replace(".apk.gz", "")), name.replace(".gz", ""));
                    if (file2.exists()) {
                        return file2;
                    }
                    File parentFile = file2.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    try {
                        Slog.d("PrePackageInstaller", "Decompressing " + this.mFile + " to " + file2);
                        PackageManagerServiceUtils.decompressFile(this.mFile, file2);
                        this.mDecompressedFile = file2;
                    } catch (Exception unused) {
                        Slog.d("PrePackageInstaller", "Failed to decompress " + this.mFile);
                        this.mDecompressedFile = null;
                    }
                    return this.mDecompressedFile;
                }
            }
            return null;
        }

        public final void removeCacheFile() {
            File file = this.mFile;
            if (file != null && file.exists() && this.mFile.getName().endsWith(".apk.gz")) {
                if (this.mDecompressedFile == null) {
                    String name = this.mFile.getName();
                    File file2 = new File(new File("/data/user_de/0/android/cache/PrePackageInstaller", name.replace(".apk.gz", "")), name.replace(".gz", ""));
                    if (file2.exists()) {
                        this.mDecompressedFile = file2;
                    }
                }
                File file3 = this.mDecompressedFile;
                if (file3 == null || !file3.exists()) {
                    return;
                }
                Slog.d("PrePackageInstaller", "Removing " + this.mDecompressedFile);
                this.mDecompressedFile.delete();
            }
        }

        public final String toString() {
            return "ApkFile{origin: " + this.mFile + ", decompressed: " + this.mDecompressedFile + "}";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalIntentReceiver {
        public Object mLocalSender;
        public Object mResult;

        public void flush() {
            File file = new File("/data/log/PreloadInstaller.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    try {
                        synchronized (((StringBuffer) this.mResult)) {
                            outputStreamWriter.write(((StringBuffer) this.mResult).toString());
                            ((StringBuffer) this.mResult).setLength(0);
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

        public void out(String str) {
            String format = ((SimpleDateFormat) this.mLocalSender).format(new Date(System.currentTimeMillis()));
            Slog.i("PrePackageInstaller", str);
            synchronized (((StringBuffer) this.mResult)) {
                ((StringBuffer) this.mResult).append(format + " : " + str + "\n");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsProviderProxy {
        public static Context mContext;
    }

    public PrePackageInstallerBase(Context context, PackageInstallerService packageInstallerService, boolean z, boolean z2) {
        LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
        localIntentReceiver.mResult = new StringBuffer();
        localIntentReceiver.mLocalSender = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        localIntentReceiver.out("");
        this.mLogMsg = localIntentReceiver;
        this.mContext = context;
        this.mPackageInstallerService = packageInstallerService;
        this.mIsFirstBoot = z;
        this.mIsUpgrade = z2;
        this.mSuccessCount = 0;
        this.mFailCount = 0;
        this.mSkipCount = 0;
        SettingsProviderProxy settingsProviderProxy = new SettingsProviderProxy();
        SettingsProviderProxy.mContext = context;
        this.mSettingsProviderProxy = settingsProviderProxy;
        this.mFingerprint = Settings.System.getString(SettingsProviderProxy.mContext.getContentResolver(), "preload_fingerprint");
        this.mPackageManager = context.getPackageManager();
    }

    public static boolean isExistHigherVersionPkg(PackageInfo packageInfo, PackageInfo packageInfo2) {
        String str;
        String str2;
        if (packageInfo2 == null || (str = packageInfo2.versionName) == null || (str2 = packageInfo.versionName) == null || packageInfo.versionCode > packageInfo2.versionCode) {
            return false;
        }
        return str.compareTo(str2) > 0 || packageInfo2.versionName.compareTo(packageInfo.versionName) == 0;
    }

    public static boolean isValidApkFile(File file) {
        return file != null && file.exists() && file.length() != 0 && file.getName().endsWith(".apk");
    }

    public abstract void addInstallPackageList(File[] fileArr);

    public final void addPackageLocation(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || (listFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                addPackageLocation(file2.getPath());
            }
        }
        File[] listFiles2 = file.listFiles(new PrePackageInstallerBase$$ExternalSyntheticLambda5());
        if (listFiles2 == null) {
            return;
        }
        addInstallPackageList(listFiles2);
    }

    public final void addUninstallPkgList(File file) {
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
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
                            if (readLine == null) {
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                return;
                            } else if (readLine.length() > 0 && !readLine.startsWith("#") && !this.mUninstallPackageList.contains(readLine)) {
                                localIntentReceiver.out("add to unInstallPackageList from list omcFile - pkg:" + readLine);
                                this.mUninstallPackageList.add(readLine);
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            localIntentReceiver.out(e.toString());
        }
    }

    public final PackageInfo getCachedPackageArchiveInfo(File file) {
        synchronized (this.mLock) {
            try {
                if (this.mCachedPackageArchiveInfo.containsKey(file)) {
                    return (PackageInfo) this.mCachedPackageArchiveInfo.get(file);
                }
                PackageInfo packageArchiveInfo = this.mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(), 0);
                synchronized (this.mLock) {
                    this.mCachedPackageArchiveInfo.put(file, packageArchiveInfo);
                }
                return packageArchiveInfo;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void prepareInstall();

    public final void prepareSettings() {
        if (!this.mIsFirstBoot && !this.mIsUpgrade) {
            String str = this.mFingerprint;
            if (str == null || str.isEmpty()) {
                this.mIsFirstBoot = true;
            } else {
                this.mIsUpgrade = true;
            }
        }
        String str2 = SystemProperties.get("persist.sys.storage_preload");
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        localIntentReceiver.out("persist.sys.storage_preload : " + str2);
        if (!this.mIsFirstBoot || str2.equalsIgnoreCase("1")) {
            return;
        }
        localIntentReceiver.out("previously, Something's wrong.. mounting hidden as first booting");
        SystemProperties.set("persist.sys.storage_preload", "1");
        SystemClock.sleep(1000L);
    }

    public void putInstallHistory(String str) {
        if (((HashSet) this.mInstallHistory).contains(str)) {
            return;
        }
        ((HashSet) this.mInstallHistory).add(str);
        String concat = this.mHistoryForSettingProvider.concat(str + ";");
        this.mHistoryForSettingProvider = concat;
        this.mSettingsProviderProxy.getClass();
        Settings.System.putString(SettingsProviderProxy.mContext.getContentResolver(), "preload_install_history", concat);
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        localIntentReceiver.out("saveInstallHistory() : " + str);
        localIntentReceiver.flush();
    }

    public final void removeApkFileFromInstallList(ApkFile apkFile) {
        this.mSkipCount++;
        apkFile.removeCacheFile();
        this.mInstallPackageList.remove(apkFile);
    }

    public void removeInstalledPkgFromList() {
        Iterator it = new ArrayList(this.mInstallPackageList).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            LocalIntentReceiver localIntentReceiver = this.mLogMsg;
            if (!hasNext) {
                localIntentReceiver.out("fin");
                return;
            }
            ApkFile apkFile = (ApkFile) it.next();
            File file = apkFile.getFile();
            if (isValidApkFile(file)) {
                try {
                    PackageInfo cachedPackageArchiveInfo = getCachedPackageArchiveInfo(file);
                    if (cachedPackageArchiveInfo != null) {
                        String str = cachedPackageArchiveInfo.packageName;
                        PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (this.mIsUpgrade) {
                            if (isExistHigherVersionPkg(cachedPackageArchiveInfo, packageInfo)) {
                                localIntentReceiver.out("exists higher Version: " + file.getAbsolutePath());
                                removeApkFileFromInstallList(apkFile);
                                putInstallHistory(str);
                            }
                        } else if (packageInfo != null) {
                            localIntentReceiver.out("already installed : " + file.getAbsolutePath());
                            removeApkFileFromInstallList(apkFile);
                            putInstallHistory(str);
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    localIntentReceiver.out("new : " + file.getAbsolutePath());
                } catch (NullPointerException e) {
                    removeApkFileFromInstallList(apkFile);
                    localIntentReceiver.out("something wrong occurred " + file.getAbsolutePath() + ", e = " + e);
                }
            } else {
                removeApkFileFromInstallList(apkFile);
            }
        }
    }

    public final Future runPrePackageInstaller() {
        long j;
        if (this.mIsFirstBoot || this.mIsUpgrade || !Build.FINGERPRINT.equals(this.mFingerprint)) {
            Context context = this.mContext;
            synchronized (PmSharedPreferences.class) {
                j = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getLong("attempt_count", 0L);
            }
            long j2 = j + 1;
            PmSharedPreferences.putLong(this.mContext, j2 % 5);
            if (j2 < 5) {
                LocalIntentReceiver localIntentReceiver = this.mLogMsg;
                String str = "!@mIsUpgrade : " + this.mIsUpgrade + ", mIsFirstBoot : " + this.mIsFirstBoot + ", mFingerprint : " + this.mFingerprint;
                localIntentReceiver.getClass();
                PmLog.logDebugInfo(str);
                localIntentReceiver.out(str);
                HandlerThread handlerThread = new HandlerThread("PrePackageInstallThread");
                handlerThread.start();
                final CompletableFuture completableFuture = new CompletableFuture();
                handlerThread.getThreadHandler().post(new Runnable() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrePackageInstallerBase prePackageInstallerBase = PrePackageInstallerBase.this;
                        CompletableFuture completableFuture2 = completableFuture;
                        prePackageInstallerBase.startPrePackageInstall();
                        File file = new File("/data/user_de/0/android/cache/PrePackageInstaller");
                        if (file.exists()) {
                            FileUtils.deleteContentsAndDir(file);
                        }
                        PmSharedPreferences.putLong(prePackageInstallerBase.mContext, 0L);
                        completableFuture2.complete(Boolean.TRUE);
                    }
                });
                return completableFuture;
            }
        }
        File file = new File("/data/user_de/0/android/cache/PrePackageInstaller");
        if (!file.exists()) {
            return null;
        }
        FileUtils.deleteContentsAndDir(file);
        return null;
    }

    public void setDisabled() {
        SystemProperties.set("persist.sys.storage_preload", "0");
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        localIntentReceiver.out("Set package state to disabled");
        localIntentReceiver.flush();
        if (this.mInstallPackageList.size() != 0) {
            localIntentReceiver.out("Waiting for cache flush...");
            localIntentReceiver.flush();
            SystemClock.sleep(1000L);
        }
        SystemProperties.set("persist.sys.storage_preload", "2");
        if (this.mIsFirstBoot) {
            String str = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
            if (str == null || TextUtils.isEmpty(str)) {
                localIntentReceiver.out("No file is existed for granting permissions");
            } else {
                String concat = str.concat("/grantpermissionslist.txt");
                localIntentReceiver.out("grantPermission file path : " + concat);
                File file = new File(concat);
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
                                        if (readLine.length() > 0 && !readLine.startsWith("#")) {
                                            String[] split = readLine.split("/");
                                            if (split.length == 2) {
                                                try {
                                                    this.mPackageManager.grantRuntimePermission(split[0], split[1], UserHandle.OWNER);
                                                    localIntentReceiver.out("grantPermission Pkg : " + split[0] + " , Request : " + split[1]);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    localIntentReceiver.out("[Error] grantPermission Pkg : " + split[0] + " , Request : " + split[1]);
                                                }
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                            } finally {
                            }
                        } finally {
                        }
                    } catch (Exception e2) {
                        localIntentReceiver.out("file error on " + concat);
                        e2.printStackTrace();
                    }
                }
            }
            localIntentReceiver.out("call setGrantPermissions");
        }
        String str2 = Build.FINGERPRINT;
        this.mSettingsProviderProxy.getClass();
        Settings.System.putString(SettingsProviderProxy.mContext.getContentResolver(), "preload_fingerprint", str2);
        Intent intent = new Intent("com.samsung.intent.action.PREINSTALLER_FINISH");
        intent.addFlags(16777216);
        this.mContext.sendBroadcast(intent);
        PmLog.logDebugInfo("!@setDisabled() is FINISHED");
        localIntentReceiver.out("!@setDisabled() is FINISHED");
        localIntentReceiver.flush();
    }

    public void setPackageManager(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    public final int startInstallSession(PackageInstaller.Session session) {
        final LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
        localIntentReceiver.mResult = new LinkedBlockingQueue();
        localIntentReceiver.mLocalSender = new IIntentSender.Stub() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase.LocalIntentReceiver.1
            public final void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) {
                try {
                    ((LinkedBlockingQueue) LocalIntentReceiver.this.mResult).offer(intent, 60L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        session.commit(new IntentSender((LocalIntentReceiver.AnonymousClass1) localIntentReceiver.mLocalSender));
        session.close();
        try {
            Intent intent = (Intent) ((LinkedBlockingQueue) localIntentReceiver.mResult).take();
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
            String stringExtra = intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
            LocalIntentReceiver localIntentReceiver2 = this.mLogMsg;
            if (intExtra == 0) {
                localIntentReceiver2.out("SUCCESS install, " + stringExtra);
                putInstallHistory(stringExtra);
            } else {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("FAIL install, ", stringExtra, ", msg = ");
                m.append(intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE"));
                localIntentReceiver2.out(m.toString());
            }
            return intExtra;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startPrePackageInstall() {
        String deviceOwner = ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).getDeviceOwner();
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        if (deviceOwner != null) {
            localIntentReceiver.out("DeviceOwner abnormal case!! -> setDisabled");
            setDisabled();
            return;
        }
        ((HashSet) this.mInstallHistory).clear();
        this.mSettingsProviderProxy.getClass();
        this.mHistoryForSettingProvider = Settings.System.getString(SettingsProviderProxy.mContext.getContentResolver(), "preload_install_history");
        localIntentReceiver.out("loadInstallHistory() : " + this.mHistoryForSettingProvider);
        localIntentReceiver.flush();
        String str = this.mHistoryForSettingProvider;
        String str2 = "";
        if (str != null) {
            for (String str3 : str.split(";")) {
                ((HashSet) this.mInstallHistory).add(str3);
            }
        } else {
            this.mHistoryForSettingProvider = "";
        }
        prepareInstall();
        try {
            str2 = SystemProperties.get("ro.csc.sales_code");
            if (TextUtils.isEmpty(str2)) {
                str2 = SystemProperties.get("ril.sales_code");
            }
        } catch (Exception unused) {
            localIntentReceiver.out("sales_code reading failed");
        }
        localIntentReceiver.out("cscCode : " + str2);
        ArrayList arrayList = new ArrayList(Arrays.asList("/system/preload", XmlUtils$$ExternalSyntheticOutline0.m("/prism/preload/", str2, "/hidden_app"), "/prism/preload/Common_app", "/system/carrier/preload", XmlUtils$$ExternalSyntheticOutline0.m("/product/preload/", str2, "/hidden_app"), "/product/preload/Common_app"));
        if (!this.mIsUpgrade) {
            arrayList.add("/system/preloadFactoryResetOnly");
        }
        arrayList.forEach(new Consumer() { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrePackageInstallerBase.this.addPackageLocation((String) obj);
            }
        });
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        Iterator it = this.mInstallPackageList.iterator();
        while (it.hasNext()) {
            final ApkFile apkFile = (ApkFile) it.next();
            final int i = 1;
            newFixedThreadPool.submit(new Callable(this) { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda1
                public final /* synthetic */ PrePackageInstallerBase f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.concurrent.Callable
                public final Object call() {
                    FileInputStream fileInputStream;
                    switch (i) {
                        case 0:
                            File file = (File) apkFile;
                            PrePackageInstallerBase prePackageInstallerBase = this.f$0;
                            PackageInstallerService packageInstallerService = prePackageInstallerBase.mPackageInstallerService;
                            String str4 = "!@INSTALL ------------------ " + file.getName();
                            PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver2 = prePackageInstallerBase.mLogMsg;
                            localIntentReceiver2.getClass();
                            PmLog.logDebugInfo(str4);
                            localIntentReceiver2.out(str4);
                            try {
                                PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
                                sessionParams.installFlags |= 67108864;
                                sessionParams.sessionFlags |= 33554432;
                                PackageInstaller.Session session = new PackageInstaller.Session(packageInstallerService.openSession(packageInstallerService.createSession(sessionParams, "PrePackageInstaller", prePackageInstallerBase.mContext.getAttributionTag(), 0)));
                                long currentTimeMillis = System.currentTimeMillis();
                                localIntentReceiver2.out("Write : " + file.getName());
                                try {
                                    fileInputStream = new FileInputStream(file);
                                } catch (Exception e) {
                                    localIntentReceiver2.out(e + " : ignored");
                                }
                                try {
                                    OutputStream openWrite = session.openWrite(file.getName(), 0L, file.length());
                                    try {
                                        byte[] bArr = new byte[EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
                                        while (true) {
                                            int read = fileInputStream.read(bArr);
                                            if (read == -1) {
                                                session.fsync(openWrite);
                                                if (openWrite != null) {
                                                    openWrite.close();
                                                }
                                                fileInputStream.close();
                                                localIntentReceiver2.out("Write " + file.getName() + " Done :" + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                                                return session;
                                            }
                                            openWrite.write(bArr, 0, read);
                                        }
                                    } finally {
                                    }
                                } finally {
                                }
                            } catch (Exception e2) {
                                localIntentReceiver2.out("error #2, " + e2);
                                return null;
                            }
                        default:
                            PrePackageInstallerBase prePackageInstallerBase2 = this.f$0;
                            prePackageInstallerBase2.getClass();
                            return prePackageInstallerBase2.getCachedPackageArchiveInfo(((PrePackageInstallerBase.ApkFile) apkFile).getFile());
                    }
                }
            });
        }
        newFixedThreadPool.shutdown();
        try {
            newFixedThreadPool.awaitTermination(300L, TimeUnit.SECONDS);
        } catch (Exception e) {
            localIntentReceiver.out("Fail to prepare files, msg : " + e);
        }
        removeInstalledPkgFromList();
        localIntentReceiver.out("[CONFIRMED INSTALLING LIST]");
        Iterator it2 = this.mInstallPackageList.iterator();
        while (it2.hasNext()) {
            localIntentReceiver.out("TO INSTALL :: " + ((ApkFile) it2.next()).getFile().getAbsolutePath());
        }
        if (this.mInstallPackageList.size() == 0) {
            localIntentReceiver.out("apk count is 0. call setDisabled()");
        } else {
            ExecutorService newFixedThreadPool2 = Executors.newFixedThreadPool(4);
            try {
                try {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it3 = this.mInstallPackageList.iterator();
                    while (it3.hasNext()) {
                        final File file = ((ApkFile) it3.next()).getFile();
                        final int i2 = 0;
                        arrayList2.add(newFixedThreadPool2.submit(new Callable(this) { // from class: com.samsung.android.server.pm.install.PrePackageInstallerBase$$ExternalSyntheticLambda1
                            public final /* synthetic */ PrePackageInstallerBase f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                FileInputStream fileInputStream;
                                switch (i2) {
                                    case 0:
                                        File file2 = (File) file;
                                        PrePackageInstallerBase prePackageInstallerBase = this.f$0;
                                        PackageInstallerService packageInstallerService = prePackageInstallerBase.mPackageInstallerService;
                                        String str4 = "!@INSTALL ------------------ " + file2.getName();
                                        PrePackageInstallerBase.LocalIntentReceiver localIntentReceiver2 = prePackageInstallerBase.mLogMsg;
                                        localIntentReceiver2.getClass();
                                        PmLog.logDebugInfo(str4);
                                        localIntentReceiver2.out(str4);
                                        try {
                                            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
                                            sessionParams.installFlags |= 67108864;
                                            sessionParams.sessionFlags |= 33554432;
                                            PackageInstaller.Session session = new PackageInstaller.Session(packageInstallerService.openSession(packageInstallerService.createSession(sessionParams, "PrePackageInstaller", prePackageInstallerBase.mContext.getAttributionTag(), 0)));
                                            long currentTimeMillis = System.currentTimeMillis();
                                            localIntentReceiver2.out("Write : " + file2.getName());
                                            try {
                                                fileInputStream = new FileInputStream(file2);
                                            } catch (Exception e2) {
                                                localIntentReceiver2.out(e2 + " : ignored");
                                            }
                                            try {
                                                OutputStream openWrite = session.openWrite(file2.getName(), 0L, file2.length());
                                                try {
                                                    byte[] bArr = new byte[EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
                                                    while (true) {
                                                        int read = fileInputStream.read(bArr);
                                                        if (read == -1) {
                                                            session.fsync(openWrite);
                                                            if (openWrite != null) {
                                                                openWrite.close();
                                                            }
                                                            fileInputStream.close();
                                                            localIntentReceiver2.out("Write " + file2.getName() + " Done :" + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                                                            return session;
                                                        }
                                                        openWrite.write(bArr, 0, read);
                                                    }
                                                } finally {
                                                }
                                            } finally {
                                            }
                                        } catch (Exception e22) {
                                            localIntentReceiver2.out("error #2, " + e22);
                                            return null;
                                        }
                                    default:
                                        PrePackageInstallerBase prePackageInstallerBase2 = this.f$0;
                                        prePackageInstallerBase2.getClass();
                                        return prePackageInstallerBase2.getCachedPackageArchiveInfo(((PrePackageInstallerBase.ApkFile) file).getFile());
                                }
                            }
                        }));
                    }
                    Iterator it4 = arrayList2.iterator();
                    while (it4.hasNext()) {
                        PackageInstaller.Session session = (PackageInstaller.Session) ((Future) it4.next()).get();
                        if (session != null) {
                            if (startInstallSession(session) == 0) {
                                this.mSuccessCount++;
                            } else {
                                this.mFailCount++;
                            }
                        }
                    }
                    newFixedThreadPool2.shutdownNow();
                    this.mInstallPackageList.forEach(new PrePackageInstallerBase$$ExternalSyntheticLambda2());
                    PmLog.logDebugInfoAndLogcat("PrePackageInstaller Result. SUCCESS : " + this.mSuccessCount + " FAIL : " + this.mFailCount + " SKIP : " + this.mSkipCount, "PrePackageInstaller");
                    localIntentReceiver.out("installPreloadPackageList() --------------------- COMPLETE");
                } catch (InterruptedException | ExecutionException e2) {
                    localIntentReceiver.out("Fail to PrePackageInstall : " + e2);
                    throw new IllegalStateException(e2);
                }
            } catch (Throwable th) {
                newFixedThreadPool2.shutdownNow();
                throw th;
            }
        }
        uninstallPackage();
        setDisabled();
    }

    public void uninstallPackage() {
        addUninstallPkgList(new File("/system/etc/uninstall_preloadpkg.lst"));
        boolean equals = SystemProperties.get("persist.sys.omc_support").equals("true");
        LocalIntentReceiver localIntentReceiver = this.mLogMsg;
        if (equals) {
            String str = SystemProperties.get("mdc.sys.omc_etcpath") + "/uninstall_preloadpkg.lst";
            File file = new File(str);
            localIntentReceiver.out("omcEtcPath : " + str);
            addUninstallPkgList(file);
        }
        Iterator it = this.mUninstallPackageList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            localIntentReceiver.out("deletePackage :" + str2);
            try {
                this.mPackageManager.deletePackage(str2, null, 2);
            } catch (Exception e) {
                localIntentReceiver.out(e.toString());
            }
        }
    }
}
