package com.android.server.pm;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Build;
import android.os.IBinder;
import android.os.ISpegHelperService;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ProfileService;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda9;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SpegService extends ProfileService {
    public static final boolean FORCE_SPEG;
    public static final boolean SPEG_DISABLE_LAUNCH =
            SystemProperties.getBoolean("com.samsung.speg.disable_launch", false);
    public boolean mBlockContinualSpeg;
    public boolean mBlockSpegInstallation;
    public final Context mContext;
    public boolean mIsSpegInOpeartion;
    public PackageManagerService mPm;
    public String mPrevInstalledPkg;
    public volatile ISpegHelperService mService;
    public boolean mSetupWizardFinished;
    public long mSpegBlockStartTime;
    public int mSpegDisplayId;
    public long mSpegFirstLaunchTime;
    public int mSpegLaunchesCount;
    public String mSpegPackage;
    public long mSpegPrevLaunchTime;
    public int mSpegUid;

    static {
        boolean z = false;
        if (!Build.IS_USER && "true".equals(SystemProperties.get("com.samsung.speg.force"))) {
            z = true;
        }
        FORCE_SPEG = z;
    }

    public SpegService(Context context) {
        super(context, "SpegService", "speg_helper");
        this.mSpegPackage = null;
        this.mSpegUid = -1;
        this.mIsSpegInOpeartion = false;
        this.mSpegDisplayId = -1;
        this.mBlockSpegInstallation = false;
        this.mSetupWizardFinished = false;
        this.mBlockContinualSpeg = false;
        this.mSpegLaunchesCount = 0;
        this.mSpegFirstLaunchTime = -1L;
        this.mSpegPrevLaunchTime = -1L;
        this.mSpegBlockStartTime = -1L;
        this.mContext = context;
        this.packageBlockList = initPackageBlockList("/system/etc/speg-package-blocklist.conf");
        try {
            cleanupMarkerFiles();
        } catch (RuntimeException e) {
            Slog.e(this.TAG, "Unexpected failure in cleanup marker files", e);
        }
    }

    public static void waitForProcessDeath(int i) {
        if (i <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = 500 + currentTimeMillis;
        while (currentTimeMillis < j) {
            try {
                Process.waitForProcessDeath(i, (int) (j - currentTimeMillis));
                return;
            } catch (InterruptedException unused) {
                currentTimeMillis = System.currentTimeMillis();
            } catch (TimeoutException unused2) {
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "Timeout occurred during waitForProcessDeath ", "SPEG");
    }

    @Override // com.android.server.ProfileService
    public final boolean checkAppId(int i) {
        return i >= 10000 && i <= 19999;
    }

    public final void cleanupMarkerFiles() {
        List list;
        File file = new File("/data/misc/speg");
        boolean isDirectory = file.isDirectory();
        String str = this.TAG;
        if (!isDirectory || !file.canRead()) {
            Slog.e(str, "Failed to read /data/misc/speg");
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            Slog.e(str, "Failed to get list of files in /data/misc/speg");
            return;
        }
        for (File file2 : listFiles) {
            if (!file2.isDirectory() && file2.getName().startsWith("speg.")) {
                String substring = file2.getName().substring(5);
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Old speg marker file exists for uid ", substring, str);
                try {
                    int parseInt = Integer.parseInt(substring);
                    PackageManagerInternal packageManagerInternal =
                            (PackageManagerInternal)
                                    LocalServices.getService(PackageManagerInternal.class);
                    if (packageManagerInternal == null) {
                        Slog.e(str, "Could not get package manager");
                        list = Collections.emptyList();
                    } else {
                        AndroidPackage androidPackage = packageManagerInternal.getPackage(parseInt);
                        if (androidPackage == null) {
                            Slog.e(str, "Could not find app with uid " + parseInt);
                            list = Collections.emptyList();
                        } else {
                            String[] sharedUserPackagesForPackage =
                                    packageManagerInternal.getSharedUserPackagesForPackage(
                                            UserHandle.getUserId(parseInt),
                                            androidPackage.getPackageName());
                            String str2 = File.separator + "base.speg" + parseInt;
                            if (ArrayUtils.isEmpty(sharedUserPackagesForPackage)) {
                                list = List.of(androidPackage.getPath() + str2);
                            } else {
                                ArrayList arrayList =
                                        new ArrayList(sharedUserPackagesForPackage.length);
                                for (String str3 : sharedUserPackagesForPackage) {
                                    AndroidPackage androidPackage2 =
                                            packageManagerInternal.getPackage(str3);
                                    if (androidPackage2 != null) {
                                        arrayList.add(androidPackage2.getPath() + str2);
                                    }
                                }
                                list = arrayList;
                            }
                        }
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        deleteMarkerFile(new File((String) it.next()));
                    }
                    deleteMarkerFile(file2);
                } catch (NumberFormatException unused) {
                    Slog.e(str, "Failed to convert uid " + substring + " to int");
                }
            }
        }
    }

    public final boolean createOrDeleteMarkerFiles(int i, String str, boolean z) {
        if (!checkUserAndService(i, 0)) {
            return false;
        }
        try {
        } catch (RemoteException | NullPointerException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m("Failed to create file: ", str, this.TAG);
        } catch (UnsupportedOperationException unused2) {
            Slog.wtf(this.TAG, "Trying to use disabled speg_helper");
        }
        return this.mService.createOrDeleteMarkerFiles(str, z, i);
    }

    public final void deleteMarkerFile(File file) {
        String str = this.TAG;
        try {
            if (!file.exists()) {
                Slog.w(str, "Marker file " + file + " does not exist");
            }
            if (file.delete()) {
                return;
            }
            Slog.e(str, "Failed to delete marker file: " + file);
        } catch (SecurityException e) {
            Slog.e(str, "Exception during " + file + " deletion", e);
        }
    }

    @Override // com.android.server.ProfileService
    public final IBinder getBinderOfService() {
        return ServiceManager.getService("speg_helper");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x009f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00bc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPidOf(int r11, java.lang.String r12) {
        /*
            r10 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/sys/fs/cgroup/uid_"
            java.lang.String r11 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r11, r1)
            r0.<init>(r11)
            boolean r11 = r0.isDirectory()
            r1 = -1
            if (r11 == 0) goto Lc0
            boolean r11 = r0.canRead()
            if (r11 == 0) goto Lc0
            java.io.File[] r11 = r0.listFiles()
            int r0 = r11.length
            r2 = 0
            r3 = r2
        L1f:
            if (r3 >= r0) goto Lc0
            r4 = r11[r3]
            boolean r5 = r4.isDirectory()
            if (r5 == 0) goto Lbc
            java.lang.String r5 = r4.getName()
            java.lang.String r6 = "pid_"
            boolean r5 = r5.startsWith(r6)
            if (r5 != 0) goto L38
            goto Lbc
        L38:
            java.lang.String r4 = r4.getName()
            r5 = 4
            java.lang.String r4 = r4.substring(r5)
            java.lang.String r5 = "/proc/"
            java.lang.String r6 = "/cmdline"
            java.lang.String r5 = com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0.m(r5, r4, r6)
            boolean r6 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m45m(r5)
            java.lang.String r7 = r10.TAG
            if (r6 == 0) goto L98
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.io.IOException -> L76
            r6.<init>(r5)     // Catch: java.io.IOException -> L76
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.io.IOException -> L76
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L76
            java.nio.charset.Charset r9 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.io.IOException -> L76
            r8.<init>(r6, r9)     // Catch: java.io.IOException -> L76
            r5.<init>(r8)     // Catch: java.io.IOException -> L76
            java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L78
            if (r6 == 0) goto L7a
            int r8 = r6.indexOf(r2)     // Catch: java.lang.Throwable -> L78
            if (r8 == r1) goto L7a
            java.lang.String r6 = r6.substring(r2, r8)     // Catch: java.lang.Throwable -> L78
            r5.close()     // Catch: java.io.IOException -> L76
            goto L99
        L76:
            r5 = move-exception
            goto L87
        L78:
            r6 = move-exception
            goto L7e
        L7a:
            r5.close()     // Catch: java.io.IOException -> L76
            goto L98
        L7e:
            r5.close()     // Catch: java.lang.Throwable -> L82
            goto L86
        L82:
            r5 = move-exception
            r6.addSuppressed(r5)     // Catch: java.io.IOException -> L76
        L86:
            throw r6     // Catch: java.io.IOException -> L76
        L87:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r8 = "Failed to read process name for pid "
            r6.<init>(r8)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            android.util.Slog.e(r7, r6, r5)
        L98:
            r6 = 0
        L99:
            boolean r5 = r12.equals(r6)
            if (r5 == 0) goto Lbc
            int r10 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.NumberFormatException -> La4
            return r10
        La4:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "Failed to convert pid "
            r11.<init>(r12)
            r11.append(r4)
            java.lang.String r12 = " to int"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            android.util.Slog.e(r7, r11, r10)
            goto Lc0
        Lbc:
            int r3 = r3 + 1
            goto L1f
        Lc0:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.pm.SpegService.getPidOf(int,"
                    + " java.lang.String):int");
    }

    public final void init(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    @Override // com.android.server.ProfileService
    public final void initializeInterfaceOfService() {
        this.mService = null;
    }

    @Override // com.android.server.ProfileService
    public final boolean isServiceRunning() {
        if (this.mService != null) {
            return true;
        }
        Slog.w(this.TAG, "speg_helper is not running");
        return false;
    }

    public final boolean isSpegInOpeartion(String str) {
        return this.mIsSpegInOpeartion && str != null && str.equals(this.mSpegPackage);
    }

    @Override // com.android.server.ProfileService
    public final void setInterfaceOfService(IBinder iBinder) {
        this.mService = ISpegHelperService.Stub.asInterface(iBinder);
    }

    public final void setSpegInOpeartion(int i, int i2, String str) {
        this.mSpegPackage = str;
        this.mSpegUid = i;
        this.mSpegDisplayId = i2;
        this.mIsSpegInOpeartion = str != null;
    }

    public final void spegClearPackage(int i, String str) {
        SpegService spegService;
        boolean z = false;
        try {
            ((ActivityTaskManagerInternal)
                            LocalServices.getService(ActivityTaskManagerInternal.class))
                    .removeRecentTasksByPackageName(0, str);
        } catch (Exception e) {
            Slog.e("SPEG", "Can't remove recent task for " + str + ", error: " + e);
        }
        PackageManagerService packageManagerService = this.mPm;
        packageManagerService.getClass();
        if (CoreRune.SYSFW_APP_SPEG
                && (spegService = packageManagerService.mSpeg) != null
                && spegService.isSpegInOpeartion(str)) {
            z =
                    packageManagerService.clearApplicationUserDataLIF(
                            packageManagerService.snapshotComputer(), str, 0);
        } else {
            Slog.e("SPEG", "Clear package " + str + " is not allowed");
        }
        if (!z) {
            BootReceiver$$ExternalSyntheticOutline0.m("Can't clear app data for ", str, "SPEG");
        }
        try {
            ((UriGrantsManagerService.LocalService)
                            ((UriGrantsManagerInternal)
                                    LocalServices.getService(UriGrantsManagerInternal.class)))
                    .removeUriPermissionsForPackage(i, str, true);
        } catch (Exception e2) {
            Slog.e("SPEG", "Can't restore default permissions for " + str + ", error: " + e2);
        }
        try {
            ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class))
                    .cancelJobsForUid(i, true, 14, 8, "clear data");
        } catch (Exception e3) {
            Slog.e("SPEG", "Can't clear scheduled jobs for " + str + ", error: " + e3);
        }
        AlarmManagerService.LocalService localService =
                (AlarmManagerService.LocalService)
                        LocalServices.getService(AlarmManagerService.LocalService.class);
        try {
            synchronized (AlarmManagerService.this.mLock) {
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                alarmManagerService.getClass();
                if (i != 1000) {
                    alarmManagerService.removeAlarmsInternalLocked(
                            3, new AlarmManagerService$$ExternalSyntheticLambda9(i, 0));
                }
            }
        } catch (Exception e4) {
            Slog.e("SPEG", "Can't clear pending alarms for " + str + ", error: " + e4);
        }
    }

    public final boolean storePrimaryProf(int i, String str, String str2) {
        if (!checkUserAndService(i, 0)) {
            return false;
        }
        try {
        } catch (RemoteException | NullPointerException unused) {
            Slog.e(this.TAG, "Failed to prepare profile");
        } catch (UnsupportedOperationException unused2) {
            Slog.wtf(this.TAG, "Trying to use disabled speg");
        }
        return this.mService.storePrimaryProf(str, str2, i);
    }
}
