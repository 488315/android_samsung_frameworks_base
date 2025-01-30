package com.android.server;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.incremental.IncrementalManager;
import android.os.IInstalld;
import android.util.Slog;
import com.android.internal.util.IndentingPrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class HeimdAllFsService {
    public static Boolean heimdAllFSSupported = null;
    public static boolean mDebugMode = true;
    public static boolean mDryrun = false;
    public final Context mContext;
    public volatile IInstalld mInstalld;
    public SortedMap mUsageStats = null;
    public List mPackagesInfo = null;
    public Thread mHeimdallFsThread = null;
    public CountDownLatch mHeimdallFsLatch = null;

    enum PKG_LIST_TYPE_IDX {
        COMPRESS,
        AI_MODEL
    }

    public HeimdAllFsService(Context context) {
        this.mContext = context;
        connectInstalld();
        mDryrun = SystemProperties.get("persist.sys.heimdallfs.dryrun").equals("true");
        StringBuilder sb = new StringBuilder();
        sb.append("DEBUG: ");
        sb.append(mDebugMode ? "TRUE" : "FALSE");
        sb.append(" Dry-run: ");
        sb.append(mDryrun ? "TRUE" : "FALSE");
        sb.append(" Last-run: ");
        sb.append(SystemProperties.get("sys.heimdallfs.todayinfo"));
        Slog.i("HeimdAllFS", sb.toString());
    }

    public static boolean checkSysfsPath(String str) {
        return new File(str).exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a2  */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isServiceActivate() {
        boolean z;
        FileReader fileReader;
        boolean z2;
        ?? readLine;
        ?? r5;
        boolean z3 = true;
        if (SystemProperties.get("persist.sys.heimdallfs.force").equals("true")) {
            return true;
        }
        Boolean bool = heimdAllFSSupported;
        if (bool != null) {
            return bool.booleanValue();
        }
        File file = new File("/sys/fs/f2fs/userdata/features");
        if (file.exists()) {
            z = true;
        } else {
            file = new File("/sys/fs/f2fs/userdata/feature_list/compression");
            if (!file.exists()) {
                Slog.w("HeimdAllFS", "getServiceLevel: [/sys/fs/f2fs/userdata/feature_list/compression] or legacy path does not exist");
                heimdAllFSSupported = Boolean.FALSE;
                return false;
            }
            z = false;
        }
        BufferedReader bufferedReader = null;
        r5 = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        try {
            try {
                fileReader = new FileReader(file);
                try {
                    BufferedReader bufferedReader3 = new BufferedReader(fileReader);
                    while (true) {
                        try {
                            readLine = bufferedReader3.readLine();
                            if (readLine == 0) {
                                z2 = false;
                                r5 = readLine;
                                break;
                            }
                            if ((!z || !readLine.contains("compression")) && (z || (readLine = readLine.contains("supported")) == 0)) {
                            }
                        } catch (IOException unused) {
                            bufferedReader2 = bufferedReader3;
                            Slog.w("HeimdAllFS", "Error while reading [/sys/fs/f2fs/userdata/feature_list/compression]");
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException unused2) {
                                }
                            }
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (IOException unused3) {
                                }
                            }
                            z2 = false;
                            bufferedReader = bufferedReader2;
                            if (z2) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException unused4) {
                                }
                            }
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                    throw th;
                                } catch (IOException unused5) {
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    }
                    if (heimdAllFSSupported == null) {
                        heimdAllFSSupported = Boolean.TRUE;
                    }
                    z2 = true;
                    r5 = readLine;
                    if (!z2) {
                        r5 = "[/sys/fs/f2fs/userdata/feature_list/compression] does not have [compression] feature";
                        Slog.w("HeimdAllFS", "[/sys/fs/f2fs/userdata/feature_list/compression] does not have [compression] feature");
                    }
                    try {
                        bufferedReader3.close();
                    } catch (IOException unused6) {
                    }
                    try {
                        fileReader.close();
                        bufferedReader = r5;
                    } catch (IOException unused7) {
                    }
                } catch (IOException unused8) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused9) {
            fileReader = null;
        } catch (Throwable th3) {
            th = th3;
            fileReader = null;
        }
        if (z2) {
            return z2;
        }
        if (!checkSysfsPath("/sys/fs/f2fs/features/sec_heimdallfs") && !checkSysfsPath("/sys/fs/f2fs/features/sec_dnode_relocation")) {
            z3 = false;
        }
        if (z3) {
            return z2;
        }
        heimdAllFSSupported = Boolean.FALSE;
        Slog.w("HeimdAllFS", "[/sys/fs/f2fs/features/sec_dnode_relocation] does not have [dnode_relocation] feature");
        Slog.w("HeimdAllFS", "[/sys/fs/f2fs/features/sec_heimdallfs] does not have [sec_heimdallfs] feature");
        return false;
    }

    public static String getMetadata(Context context, String str, String str2) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(str, 128).metaData;
            if (bundle != null && bundle.get(str2) != null) {
                return bundle.get(str2).toString();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x014b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0133 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List getPackagesOnUserdata() {
        Stream<Path> walk;
        List list;
        List<PackageInfo> installedPackages = this.mContext.getPackageManager().getInstalledPackages(0);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (PackageInfo packageInfo : installedPackages) {
            if (packageInfo.applicationInfo.sourceDir.startsWith("/data/")) {
                if (mDebugMode) {
                    Slog.d("HeimdAllFS", "Package Info = " + packageInfo.packageName);
                    Slog.d("HeimdAllFS", "Package source Dir = " + packageInfo.applicationInfo.sourceDir);
                    Slog.d("HeimdAllFS", "Incremental Delivery? = " + IncrementalManager.isIncrementalPath(packageInfo.applicationInfo.publicSourceDir));
                    File file = new File(packageInfo.applicationInfo.sourceDir);
                    Slog.d("HeimdAllFS", "OAT Dir = " + file.getParent() + "/oat");
                    StringBuilder sb = new StringBuilder();
                    sb.append(file.getParent());
                    sb.append("/oat");
                    boolean isDirectory = new File(sb.toString()).isDirectory();
                    List list2 = null;
                    if (isDirectory) {
                        try {
                            walk = Files.walk(Paths.get(file.getParent() + "/oat", new String[0]), 2, new FileVisitOption[0]);
                            try {
                                list = (List) walk.filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean lambda$getPackagesOnUserdata$0;
                                        lambda$getPackagesOnUserdata$0 = HeimdAllFsService.lambda$getPackagesOnUserdata$0((Path) obj);
                                        return lambda$getPackagesOnUserdata$0;
                                    }
                                }).filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean lambda$getPackagesOnUserdata$1;
                                        lambda$getPackagesOnUserdata$1 = HeimdAllFsService.lambda$getPackagesOnUserdata$1((Path) obj);
                                        return lambda$getPackagesOnUserdata$1;
                                    }
                                }).collect(Collectors.toList());
                            } catch (Throwable th) {
                                if (walk != null) {
                                    try {
                                        walk.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException unused) {
                        }
                        try {
                            walk.close();
                            list2 = list;
                        } catch (IOException unused2) {
                            list2 = list;
                            Slog.w("HeimdAllFS", "Error while reading oat path");
                            if (list2 != null) {
                            }
                            if (!IncrementalManager.isIncrementalPath(packageInfo.applicationInfo.publicSourceDir)) {
                            }
                        }
                    }
                    if (list2 != null) {
                        list2.forEach(new Consumer() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                HeimdAllFsService.lambda$getPackagesOnUserdata$2((Path) obj);
                            }
                        });
                    }
                }
                if (!IncrementalManager.isIncrementalPath(packageInfo.applicationInfo.publicSourceDir)) {
                    Slog.d("HeimdAllFS", "Incremental Delivery APK: SKIP! : " + packageInfo.packageName);
                } else if (getMetadata(this.mContext, packageInfo.packageName, "com.android.aimodel") == "true") {
                    Slog.d("HeimdAllFS", "AI model APK: SKIP! : " + packageInfo.packageName);
                    arrayList2.add(packageInfo);
                } else {
                    arrayList3.add(packageInfo);
                }
            }
        }
        arrayList.add(arrayList3);
        arrayList.add(arrayList2);
        return arrayList;
    }

    public static /* synthetic */ boolean lambda$getPackagesOnUserdata$0(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }

    public static /* synthetic */ boolean lambda$getPackagesOnUserdata$1(Path path) {
        return path.getFileName().toString().matches(".*\\.[ov]dex");
    }

    public static /* synthetic */ void lambda$getPackagesOnUserdata$2(Path path) {
        Slog.d("HeimdAllFS", path.toString());
    }

    public SortedMap getAppUsageStats() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - 1296000000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(2, calendar2.getTimeInMillis(), calendar.getTimeInMillis());
        TreeMap treeMap = new TreeMap();
        if (queryUsageStats != null) {
            for (UsageStats usageStats : queryUsageStats) {
                if (mDebugMode) {
                    Slog.d("HeimdAllFS", "Used package : " + usageStats.getPackageName() + " - " + usageStats.getLastTimeUsed());
                }
                treeMap.put(usageStats.getPackageName(), Long.valueOf(usageStats.getLastTimeUsed()));
            }
        }
        return treeMap;
    }

    public List getCandidatePackages(List list, SortedMap sortedMap, boolean z) {
        ArrayList arrayList = new ArrayList();
        boolean equals = SystemProperties.get("persist.sys.heimdallfs.force").equals("true");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (z && (equals || sortedMap.get(packageInfo.packageName) == null)) {
                if (mDebugMode) {
                    Slog.d("HeimdAllFS", "compressCandidate App : " + packageInfo.packageName);
                }
                arrayList.add(packageInfo);
            } else if (!z && (equals || sortedMap.get(packageInfo.packageName) != null)) {
                if (mDebugMode) {
                    Slog.d("HeimdAllFS", "de-compressCandidate App : " + packageInfo.packageName);
                }
                arrayList.add(packageInfo);
            }
        }
        return arrayList;
    }

    public final void connectInstalld() {
        this.mInstalld = IInstalld.Stub.asInterface(ServiceManager.getService("installd"));
    }

    public void doCompressAction(String str, boolean z) {
        try {
            if (mDebugMode) {
                StringBuilder sb = new StringBuilder();
                sb.append("doCompressAction: ");
                sb.append(z ? "Comp" : "Decomp");
                sb.append(": ");
                sb.append(str);
                Slog.d("HeimdAllFS", sb.toString());
            }
            if (mDryrun) {
                return;
            }
            this.mInstalld.compressFile(str, z);
        } catch (RemoteException unused) {
            Slog.w("HeimdAllFS", "Error: Compress/Decompress RemoteException, " + str);
        } catch (Exception unused2) {
            Slog.e("HeimdAllFS", "Error: Exception!! " + str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008f A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doCompressFilesInDir(String str) {
        List<Path> list;
        Thread currentThread = Thread.currentThread();
        Slog.d("HeimdAllFS", "doCompressFilesInDir Start!! " + str);
        List list2 = null;
        try {
            Stream<Path> walk = Files.walk(Paths.get(str, new String[0]), new FileVisitOption[0]);
            try {
                list = (List) walk.filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$doCompressFilesInDir$3;
                        lambda$doCompressFilesInDir$3 = HeimdAllFsService.lambda$doCompressFilesInDir$3((Path) obj);
                        return lambda$doCompressFilesInDir$3;
                    }
                }).filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$doCompressFilesInDir$4;
                        lambda$doCompressFilesInDir$4 = HeimdAllFsService.lambda$doCompressFilesInDir$4((Path) obj);
                        return lambda$doCompressFilesInDir$4;
                    }
                }).collect(Collectors.toList());
                try {
                    walk.close();
                } catch (IOException unused) {
                    list2 = list;
                    Slog.w("HeimdAllFS", "Error while reading dir: " + str);
                    list = list2;
                    if (list != null) {
                    }
                }
            } finally {
            }
        } catch (IOException unused2) {
            Slog.w("HeimdAllFS", "Error while reading dir: " + str);
            list = list2;
            if (list != null) {
            }
        }
        if (list != null) {
            for (Path path : list) {
                if (!this.mHeimdallFsThread.equals(currentThread)) {
                    return;
                } else {
                    doCompressAction(path.toString(), true);
                }
            }
        }
    }

    public static /* synthetic */ boolean lambda$doCompressFilesInDir$3(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }

    public static /* synthetic */ boolean lambda$doCompressFilesInDir$4(Path path) {
        return !path.getFileName().toString().matches(".*\\.(zip|gz)$");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0038 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doCompressPackages(List list, boolean z) {
        Thread currentThread = Thread.currentThread();
        StringBuilder sb = new StringBuilder();
        sb.append("doCompressPackages Start!! : ");
        sb.append(z ? "Compress" : "Decompress");
        Slog.d("HeimdAllFS", sb.toString());
        if (mDebugMode) {
            Slog.d("HeimdAllFS", "Get Package Info!!");
        }
        SortedMap appUsageStats = getAppUsageStats();
        this.mUsageStats = appUsageStats;
        for (PackageInfo packageInfo : getCandidatePackages(list, appUsageStats, z)) {
            if (!this.mHeimdallFsThread.equals(currentThread)) {
                return;
            }
            doCompressAction(packageInfo.applicationInfo.sourceDir, z);
            String[] strArr = packageInfo.applicationInfo.splitSourceDirs;
            if (strArr != null) {
                for (String str : strArr) {
                    doCompressAction(str, z);
                }
            }
            File file = new File(packageInfo.applicationInfo.sourceDir);
            List list2 = null;
            if (new File(file.getParent() + "/oat").isDirectory()) {
                try {
                    Stream<Path> walk = Files.walk(Paths.get(file.getParent() + "/oat", new String[0]), 2, new FileVisitOption[0]);
                    try {
                        List list3 = (List) walk.filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$doCompressPackages$5;
                                lambda$doCompressPackages$5 = HeimdAllFsService.lambda$doCompressPackages$5((Path) obj);
                                return lambda$doCompressPackages$5;
                            }
                        }).filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$doCompressPackages$6;
                                lambda$doCompressPackages$6 = HeimdAllFsService.lambda$doCompressPackages$6((Path) obj);
                                return lambda$doCompressPackages$6;
                            }
                        }).collect(Collectors.toList());
                        try {
                            walk.close();
                            list2 = list3;
                        } catch (IOException unused) {
                            list2 = list3;
                            Slog.w("HeimdAllFS", "Error while reading oat path");
                            if (list2 == null) {
                            }
                        }
                    } catch (Throwable th) {
                        if (walk != null) {
                            try {
                                walk.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused2) {
                }
            }
            if (list2 == null) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    doCompressAction(((Path) it.next()).toString(), z);
                }
            }
        }
    }

    public static /* synthetic */ boolean lambda$doCompressPackages$5(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }

    public static /* synthetic */ boolean lambda$doCompressPackages$6(Path path) {
        return path.getFileName().toString().matches(".*\\.[ov]dex");
    }

    public void scanCompressedFileAction(String str, int i) {
        if (mDebugMode) {
            Slog.d("HeimdAllFS", "Scan Compressed File!! (" + i + "): " + str);
        }
        try {
            this.mInstalld.scanApkStats(str, i);
        } catch (RemoteException unused) {
            Slog.w("HeimdAllFS", "Error: scanApkStats RemoteException, " + str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void scanApkPackagesForBigdata() {
        String str;
        int i;
        Thread currentThread = Thread.currentThread();
        List<PackageInfo> installedPackages = this.mContext.getPackageManager().getInstalledPackages(0);
        int i2 = 0;
        boolean z = false;
        String str2 = "";
        String str3 = str2;
        while (true) {
            if (i2 >= installedPackages.size() || !this.mHeimdallFsThread.equals(currentThread)) {
                break;
            }
            if (!this.mHeimdallFsThread.equals(currentThread)) {
                if (mDebugMode) {
                    Slog.d("HeimdAllFS", "Break!!");
                }
            } else {
                if (installedPackages.get(i2).applicationInfo.sourceDir.startsWith("/data/")) {
                    str2 = installedPackages.get(i2).applicationInfo.sourceDir;
                    if (IncrementalManager.isIncrementalPath(str2)) {
                        if (mDebugMode) {
                            Slog.d("HeimdAllFS", "Incremental APK in bigdata scan!!" + str2);
                        }
                    } else {
                        if ("".equals(str3)) {
                            i = 3;
                            str = str2;
                        } else {
                            str = str3;
                            i = 2;
                        }
                        if (i2 == installedPackages.size() - 1) {
                            i |= 4;
                            z = true;
                        }
                        scanCompressedFileAction(str2, i);
                        int i3 = i & (-2);
                        File file = new File(str2);
                        List list = null;
                        if (new File(file.getParent() + "/oat").isDirectory()) {
                            try {
                                Stream<Path> walk = Files.walk(Paths.get(file.getParent() + "/oat", new String[0]), 2, new FileVisitOption[0]);
                                try {
                                    List list2 = (List) walk.filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda7
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean lambda$scanApkPackagesForBigdata$7;
                                            lambda$scanApkPackagesForBigdata$7 = HeimdAllFsService.lambda$scanApkPackagesForBigdata$7((Path) obj);
                                            return lambda$scanApkPackagesForBigdata$7;
                                        }
                                    }).filter(new Predicate() { // from class: com.android.server.HeimdAllFsService$$ExternalSyntheticLambda8
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean lambda$scanApkPackagesForBigdata$8;
                                            lambda$scanApkPackagesForBigdata$8 = HeimdAllFsService.lambda$scanApkPackagesForBigdata$8((Path) obj);
                                            return lambda$scanApkPackagesForBigdata$8;
                                        }
                                    }).collect(Collectors.toList());
                                    try {
                                        walk.close();
                                        list = list2;
                                    } catch (IOException unused) {
                                        list = list2;
                                        Slog.w("HeimdAllFS", "Error while reading oat path");
                                        if (list != null) {
                                        }
                                        str3 = str;
                                        i2++;
                                    }
                                } catch (Throwable th) {
                                    if (walk != null) {
                                        try {
                                            walk.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException unused2) {
                                Slog.w("HeimdAllFS", "Error while reading oat path");
                                if (list != null) {
                                }
                                str3 = str;
                                i2++;
                            }
                        }
                        if (list != null) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                scanCompressedFileAction(((Path) it.next()).toString(), i3);
                            }
                        }
                        str3 = str;
                    }
                }
                i2++;
            }
        }
        if (z || "".equals(str3)) {
            return;
        }
        if (mDebugMode) {
            Slog.d("HeimdAllFS", "Last commit APK!!" + str2);
        }
        scanCompressedFileAction(str3, 4);
    }

    public static /* synthetic */ boolean lambda$scanApkPackagesForBigdata$7(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }

    public static /* synthetic */ boolean lambda$scanApkPackagesForBigdata$8(Path path) {
        return path.getFileName().toString().matches(".*\\.[ov]dex");
    }

    public void start() {
        Thread thread = new Thread("HeimdAllFS") { // from class: com.android.server.HeimdAllFsService.1
            /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                String format;
                try {
                    try {
                        Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread Start");
                        HeimdAllFsService.this.mHeimdallFsLatch = new CountDownLatch(1);
                        format = new SimpleDateFormat("yyMMdd").format(new Date());
                    } catch (Exception e) {
                        Slog.e("HeimdAllFS", "Exception!!");
                        Slog.wtf("HeimdAllFS", e);
                        Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                        if (HeimdAllFsService.this.mHeimdallFsLatch == null) {
                            return;
                        }
                    }
                    if (!HeimdAllFsService.isServiceActivate()) {
                        Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                        if (HeimdAllFsService.this.mHeimdallFsLatch != null) {
                            HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                            return;
                        }
                        return;
                    }
                    if (!SystemProperties.get("persist.sys.heimdallfs.force").equals("true") && SystemProperties.get("sys.heimdallfs.todayinfo").equals(format)) {
                        Slog.i("HeimdAllFS", "Once a day, bye bye");
                        Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                        if (HeimdAllFsService.this.mHeimdallFsLatch != null) {
                            HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                            return;
                        }
                        return;
                    }
                    SystemProperties.set("sys.heimdallfs.todayinfo", format);
                    String str = SystemProperties.get("persist.sys.heimdallfs.force.mode");
                    StatFs statFs = new StatFs("/data/");
                    HeimdAllFsService heimdAllFsService = HeimdAllFsService.this;
                    heimdAllFsService.mPackagesInfo = heimdAllFsService.getPackagesOnUserdata();
                    if (!"compress".equals(str) && statFs.getAvailableBytes() >= statFs.getTotalBytes() * 0.1d) {
                        if (!"decompress".equals(str) && statFs.getAvailableBytes() <= statFs.getTotalBytes() * 0.15d) {
                            Slog.i("HeimdAllFS", "Do nothing!! available : " + statFs.getAvailableBytes() + ", total : " + statFs.getTotalBytes());
                            Slog.i("HeimdAllFS", "HeimdAllFS Comp/Decomp process complete");
                            HeimdAllFsService.this.scanApkPackagesForBigdata();
                            Slog.i("HeimdAllFS", "HeimdAllFS Thread End Normally");
                            Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                            if (HeimdAllFsService.this.mHeimdallFsLatch == null) {
                                return;
                            }
                            HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                        }
                        Slog.i("HeimdAllFS", "Decompress!! " + statFs.getAvailableBytes());
                        HeimdAllFsService heimdAllFsService2 = HeimdAllFsService.this;
                        heimdAllFsService2.doCompressPackages((List) heimdAllFsService2.mPackagesInfo.get(PKG_LIST_TYPE_IDX.COMPRESS.ordinal()), false);
                        Slog.i("HeimdAllFS", "HeimdAllFS Comp/Decomp process complete");
                        HeimdAllFsService.this.scanApkPackagesForBigdata();
                        Slog.i("HeimdAllFS", "HeimdAllFS Thread End Normally");
                        Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                        if (HeimdAllFsService.this.mHeimdallFsLatch == null) {
                        }
                        HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                    }
                    Slog.i("HeimdAllFS", "Compress!! " + statFs.getAvailableBytes());
                    HeimdAllFsService heimdAllFsService3 = HeimdAllFsService.this;
                    heimdAllFsService3.doCompressPackages((List) heimdAllFsService3.mPackagesInfo.get(PKG_LIST_TYPE_IDX.COMPRESS.ordinal()), true);
                    HeimdAllFsService.this.doCompressFilesInDir("/data/log");
                    Slog.i("HeimdAllFS", "HeimdAllFS Comp/Decomp process complete");
                    HeimdAllFsService.this.scanApkPackagesForBigdata();
                    Slog.i("HeimdAllFS", "HeimdAllFS Thread End Normally");
                    Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                    if (HeimdAllFsService.this.mHeimdallFsLatch == null) {
                    }
                    HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                } catch (Throwable th) {
                    Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                    if (HeimdAllFsService.this.mHeimdallFsLatch != null) {
                        HeimdAllFsService.this.mHeimdallFsLatch.countDown();
                    }
                    throw th;
                }
            }
        };
        this.mHeimdallFsThread = thread;
        thread.start();
    }

    public void waitForFinished() {
        try {
            if (this.mHeimdallFsLatch != null) {
                Slog.i("HeimdAllFS", "waitForFinished, HeimdAllLatch await");
                this.mHeimdallFsLatch.await();
                this.mHeimdallFsLatch = null;
            }
            Slog.i("HeimdAllFS", "waitForFinished, HeimdAllLatch await End");
        } catch (InterruptedException unused) {
            Slog.w("HeimdAllFS", "Interrupt while waiting for heimdallFsLatch:CountDownLatch(1)");
        }
    }

    public void abort() {
        Slog.w("HeimdAllFS", "Abort()");
        this.mHeimdallFsThread = null;
    }

    public long[] dumpPackageState(IndentingPrintWriter indentingPrintWriter, String str) {
        long[] jArr = {0, 0};
        indentingPrintWriter.println("path: " + str);
        try {
        } catch (RemoteException unused) {
            indentingPrintWriter.println("Error: getCompressedStats RemoteException, " + str);
            Slog.w("HeimdAllFS", "Error: getCompressedStats RemoteException, " + str);
        } catch (Exception unused2) {
            indentingPrintWriter.println("Error: Exception, " + str);
            Slog.w("HeimdAllFS", "Error: Exception, " + str);
        }
        if (IncrementalManager.isIncrementalPath(str)) {
            indentingPrintWriter.println("Incremental Delivery APK: SKIP! : " + str);
            return jArr;
        }
        indentingPrintWriter.print("Compressed? ");
        if (this.mInstalld.getCompressedStats(str, jArr) && jArr[0] > jArr[1]) {
            indentingPrintWriter.println("True");
            indentingPrintWriter.println("Size info: " + jArr[0] + ", " + jArr[1]);
        } else if (jArr[0] == 4303) {
            indentingPrintWriter.println("Unknown - failed to acquire installd mLock");
        } else {
            indentingPrintWriter.println("False");
        }
        return jArr;
    }
}
