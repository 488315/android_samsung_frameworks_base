package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.UiModeManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.IDeviceIdleController;
import android.os.INetworkManagementService;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SemHqmManager;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsHandler;
import com.android.server.am.MARsTrigger;
import com.android.server.am.mars.EventRecorder;
import com.android.server.am.mars.ForegroundServiceRecord;
import com.android.server.am.mars.HistoryBuffer;
import com.android.server.am.mars.MARsBigData;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsHistoryBuffer;
import com.android.server.am.mars.MARsHistoryLog;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.database.FASEntity;
import com.android.server.am.mars.database.FASEntityBuilder;
import com.android.server.am.mars.database.FASTableContract;
import com.android.server.am.mars.database.MARsComponentTracker;
import com.android.server.am.mars.database.MARsDBHandler;
import com.android.server.am.mars.database.MARsDBManager;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.CarConnectedFilter;
import com.android.server.am.mars.filter.filter.CarConnectedFilter$$ExternalSyntheticLambda0;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter;
import com.android.server.am.mars.filter.filter.TopPackageFilter;
import com.android.server.am.mars.filter.filter.VPNPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import com.android.server.am.mars.util.ConcurrentList;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.appop.AppOpsService;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.usage.AppStandbyInternal;
import com.att.iqi.lib.BuildConfig;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.sdhms.SemBatteryStats;
import com.samsung.android.sdhms.SemDeviceHealthManager;
import java.com.android.server.am.mars.database.MARsListManager;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MARsPolicyManager {
    public static boolean MARs_ENABLE;
    public ActivityManagerService mAm;
    public AppOpsService mAppOpsService;
    public AppStandbyInternal mAppStandby;
    public Context mContext;
    public boolean mDualAppEnabled;
    public int mDualAppUserId;
    public HistoryBuffer mFilterHistoryBufferArray;
    public HistoryBuffer mHistoryBufferArray;
    public boolean mInitDisabledPackage;
    public String mLastPkgName;
    public INetworkManagementService mNMs;
    public HistoryBuffer mNetHistoryBufferArray;
    public static final String SMART_MANAGER_PKG_NAME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool");
    public static final Lock MARsLock = new Lock();
    public static int AUFAllowBucketSize = Integer.parseInt(SystemProperties.get("sys.config.mars_auf_bucket", "0"));
    public static boolean ENABLE_KILL_LONG_RUNNING_PROCESS = true;
    public static boolean GlobalModelWithChinaSIM = false;
    public static boolean isChinaModel = false;
    public static boolean App_StartUp_History = false;
    public static final int FGS_BATTERY_USAGE_THRESHOLD = 100;
    public int mCurrentUserId = 0;
    public int mLastUid = -10;
    public long mLastUpdateTime = 0;
    public int[] mEnabledProfileUserIds = new int[0];
    public final HashMap mFGServiceStartTimeMap = new HashMap();
    public final HashMap mBackupExpirationUptimeMap = new HashMap();
    public final HashSet mScpmList = new HashSet();
    public final ArrayList mNeedtoDisablePackages = new ArrayList();
    public final Set mCtsGtsList = MARsListManager.ListManagerHolder.INSTANCE.mCtsGtsList;
    public boolean ENABLE_RESTRICTED_BUCKET = true;
    public long KEEP_NO_FILTER_MIN_DURATION = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
    public boolean mScreenOn = false;
    public boolean mCarModeOn = false;
    public boolean mIsDeviceIdleMode = false;
    public boolean mFirstTimeUpdatePackages = true;
    public int mAllPoliciesOn = 1;
    public boolean mIsManualMode = false;
    public final MARsPkgMap mMARsTargetPackages = new MARsPkgMap();
    public final MARsPkgMap mMARsRestrictedPackages = new MARsPkgMap();
    public final long mAutoDisabledLockingTime = 1382400000;
    public long mLastNotiSentTimeForDisabled = 0;
    public boolean mIsLastNotiSentTimeForDisabledDismiss = false;
    public double THRESHOLD_POWER_USAGE = 1.0d;
    public double THRESHOLD_POWER_USAGE_BACKUP = 1.0d;
    public boolean isTimeChangedForDebug = false;
    public long mAutoDeepSleepTimeForDebug = 0;
    public Policy appLockerPolicy = null;
    public Policy autoRunPolicy = null;
    public Policy freecessPolicy = null;
    public Policy sbikePolicy = null;
    public Policy disablePolicy = null;
    public Policy gamePolicy = null;

    public final class Lock {
    }

    public final class MARsAppIdleStateChangeListener extends AppStandbyInternal.AppIdleStateChangeListener {
        public MARsAppIdleStateChangeListener() {
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onAppIdleStateChanged(java.lang.String r6, int r7, boolean r8, int r9, int r10) {
            /*
                Method dump skipped, instructions count: 256
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.MARsAppIdleStateChangeListener.onAppIdleStateChanged(java.lang.String, int, boolean, int, int):void");
        }
    }

    public abstract class MARsPolicyManagerHolder {
        public static final MARsPolicyManager INSTANCE = new MARsPolicyManager();
    }

    public final class PkgStatusInfo {
        public final String name;
        public final int uid;
        public final int userId;
        public int fasType = 0;
        public int state = 1;
        public int currentLevel = 0;
        public int possibleLevel = 1;
        public int maxLevel = 1;
        public boolean isFASEnabled = false;
        public boolean isInRestrictedBucket = false;
        public boolean isDisabled = false;
        public int disableType = -1;
        public int disableReason = 0;

        public PkgStatusInfo(int i, int i2, String str) {
            this.name = str;
            this.uid = i;
            this.userId = i2;
        }
    }

    public final class Policy {
        public final int action;
        public boolean enabled;
        public final String name;
        public final int num;

        public Policy(int i, int i2, String str, boolean z) {
            this.name = str;
            this.num = i;
            this.enabled = z;
            this.action = i2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.name);
            sb.append("(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.num, sb, ")");
        }
    }

    public MARsPolicyManager() {
        SystemProperties.get("persist.sys.bub_onoff", "1");
    }

    public static String convertLevelChangeInfoToString(String[] strArr, String[] strArr2) {
        StringBuilder sb = new StringBuilder();
        if (!strArr[1].equals("[FRZ]")) {
            sb.append(strArr[1] + " ");
        }
        if (!strArr[2].equals("[FAS]")) {
            sb.append(strArr[2] + " ");
        }
        if (!strArr[3].equals("[FOS]")) {
            sb.append(strArr[3] + " ");
        }
        if (!strArr[4].equals("[DIS]")) {
            sb.append(strArr[4]);
        }
        if (!strArr2[1].equals("[FRZ]") || !strArr2[2].equals("[FAS]") || !strArr2[3].equals("[FOS]") || !strArr2[4].equals("[DIS]")) {
            sb.append(" [IMP] ");
        }
        if (!strArr2[1].equals("[FRZ]")) {
            sb.append(strArr2[1] + " ");
        }
        if (!strArr2[2].equals("[FAS]")) {
            sb.append(strArr2[2] + " ");
        }
        if (!strArr2[3].equals("[FOS]")) {
            sb.append(strArr2[3] + " ");
        }
        if (!strArr2[4].equals("[DIS]")) {
            sb.append(strArr2[4]);
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0024, code lost:
    
        if (r3.isPackageSuspendedForUser(r2, r1) == false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean disableAction(com.android.server.am.MARsPolicyManager.PkgStatusInfo r6) {
        /*
            boolean r0 = r6.isDisabled
            int r1 = r6.userId
            java.lang.String r2 = r6.name
            if (r0 != 0) goto L40
            boolean r0 = r6.isFASEnabled
            if (r0 == 0) goto L40
            r0 = 1
            java.lang.String r3 = "package"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)     // Catch: java.lang.Exception -> L27
            android.content.pm.IPackageManager r3 = android.content.pm.IPackageManager.Stub.asInterface(r3)     // Catch: java.lang.Exception -> L27
            int r4 = r3.getApplicationEnabledSetting(r2, r1)     // Catch: java.lang.Exception -> L27
            if (r4 == 0) goto L20
            if (r4 != r0) goto L2f
        L20:
            boolean r3 = r3.isPackageSuspendedForUser(r2, r1)     // Catch: java.lang.Exception -> L27
            if (r3 != 0) goto L2f
            goto L30
        L27:
            r3 = move-exception
            java.lang.String r4 = "Error occurred in getEnabledStateIfCanBeDisabled()"
            java.lang.String r5 = "MARsPolicyManager"
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r3, r4, r5)
        L2f:
            r4 = -1
        L30:
            if (r4 < 0) goto L40
            r3 = 4
            r5 = 0
            boolean r1 = setEnabledSetting(r1, r3, r5, r2)
            if (r1 == 0) goto L40
            r6.disableType = r4
            r6.isDisabled = r0
            r6.currentLevel = r3
        L40:
            boolean r6 = r6.isDisabled
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.disableAction(com.android.server.am.MARsPolicyManager$PkgStatusInfo):boolean");
    }

    public static boolean enableAction(PkgStatusInfo pkgStatusInfo) {
        if (pkgStatusInfo.isDisabled) {
            int i = pkgStatusInfo.disableType;
            if (i == -1 || i == 4) {
                i = 0;
            }
            if (setEnabledSetting(pkgStatusInfo.userId, i, 1, pkgStatusInfo.name)) {
                pkgStatusInfo.disableType = -1;
                pkgStatusInfo.isDisabled = false;
                pkgStatusInfo.currentLevel = 0;
            }
        }
        return !pkgStatusInfo.isDisabled;
    }

    public static String formatDateTimeWithoutYear(long j) {
        return j == 0 ? String.format("%18s", "null") : new SimpleDateFormat("MM/dd HH:mm:ss.SSS").format(new Date(j));
    }

    public static boolean getChangedByUserFromReason(String str) {
        return "added_from_user_manual".equals(str) || "added_from_anomaly_manual".equals(str) || "deleted_from_user_manual".equals(str);
    }

    public static MARsPolicyManager getInstance() {
        return MARsPolicyManagerHolder.INSTANCE;
    }

    public static MARsPackageInfo getMARsPackageInfo(MARsPkgMap mARsPkgMap, String str, int i) {
        if (mARsPkgMap == null || mARsPkgMap.totalSize() == 0) {
            return null;
        }
        return (MARsPackageInfo) mARsPkgMap.get(i, str);
    }

    public static boolean hasPermission(String str) {
        int callingUid = Binder.getCallingUid();
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        try {
            if (1000 == UserHandle.getAppId(callingUid) || asInterface == null) {
                return true;
            }
            return asInterface.checkUidPermission(str, callingUid) == 0;
        } catch (RemoteException e) {
            Slog.e("MARsPolicyManager", "hasPermission exception:" + e);
            return true;
        }
    }

    public static boolean isAnomalyFGSPackage(int i) {
        ForegroundServiceRecord foregroundServiceRecord;
        int i2 = ForegroundServiceMgr.$r8$clinit;
        ConcurrentHashMap concurrentHashMap = ForegroundServiceMgr.ForegroundServiceMgrHolder.INSTANCE.mMapFGSRecord;
        if (concurrentHashMap == null || (foregroundServiceRecord = (ForegroundServiceRecord) concurrentHashMap.get(Integer.valueOf(i))) == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("type : ");
        sb.append(foregroundServiceRecord.mForegroundType);
        sb.append(", using : ");
        sb.append(foregroundServiceRecord.mUsingForegroundType);
        sb.append(" isAnomalyFGS : ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("MARsPolicyManager", sb, ((~foregroundServiceRecord.mForegroundType) & foregroundServiceRecord.mUsingForegroundType) != 0);
        return (foregroundServiceRecord.mUsingForegroundType & (~foregroundServiceRecord.mForegroundType)) != 0;
    }

    public static boolean isChinaPolicyEnabled() {
        return isChinaModel || GlobalModelWithChinaSIM;
    }

    public static boolean isDisabledByUser(int i) {
        return i == 2 || i == 32;
    }

    public static boolean isTargetMatch(String str, String str2, String str3) {
        if ("equals".equals(str2)) {
            return str.equals(str3);
        }
        if ("contains".equals(str2)) {
            return str.contains(str3);
        }
        if ("startsWith".equals(str2)) {
            return str.startsWith(str3);
        }
        if ("endsWith".equals(str2)) {
            return str.endsWith(str3);
        }
        if ("equalsIgnoreCase".equals(str2)) {
            return str.equalsIgnoreCase(str3);
        }
        return false;
    }

    public static void onSpecialUnBindServiceActions(int i, String str, String str2, String str3) {
        if (str == null) {
            return;
        }
        if ("com.google.android.projection.gearhead".equals(str3)) {
            CarConnectedFilter carConnectedFilter = CarConnectedFilter.AndroidAutoFilterHolder.INSTANCE;
            synchronized (carConnectedFilter.userIdPackageListSelfLocked) {
                try {
                    ArrayList arrayList = (ArrayList) carConnectedFilter.userIdPackageListSelfLocked.get(Integer.valueOf(i));
                    if (arrayList != null) {
                        if (arrayList.contains(str)) {
                            arrayList.remove(str);
                            if (!carConnectedFilter.logExcludeList.contains(str)) {
                                MARsUtils.addFilterDebugInfoToHistory("FILTER 32 remove", str);
                            }
                        }
                    }
                } finally {
                }
            }
        }
        if (str2 == null) {
            return;
        }
        if ("android.intent.action.TTS_SERVICE".equals(str2) && str3 != null) {
            ActiveMusicRecordFilter activeMusicRecordFilter = ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
            Integer valueOf = Integer.valueOf(i);
            synchronized (activeMusicRecordFilter.mTTSPkgs) {
                try {
                    ArrayList arrayList2 = (ArrayList) activeMusicRecordFilter.mTTSPkgs.get(valueOf);
                    if (arrayList2 != null && arrayList2.contains(str3)) {
                        arrayList2.remove(str3);
                        activeMusicRecordFilter.mTTSPkgs.put(valueOf, arrayList2);
                    }
                } finally {
                }
            }
        }
        if ("android.net.VpnService".equals(str2)) {
            VPNPackageFilter vPNPackageFilter = VPNPackageFilter.VPNPackageFilterHolder.INSTANCE;
            Integer valueOf2 = Integer.valueOf(i);
            synchronized (vPNPackageFilter.mVpnPkgs) {
                try {
                    ArrayList arrayList3 = (ArrayList) vPNPackageFilter.mVpnPkgs.get(valueOf2);
                    if (arrayList3 != null && arrayList3.contains(str)) {
                        arrayList3.remove(str);
                        vPNPackageFilter.mVpnPkgs.put(valueOf2, arrayList3);
                    }
                } finally {
                }
            }
        }
        if ("android.service.notification.NotificationListenerService".equals(str2)) {
            OngoingNotiPackageFilter ongoingNotiPackageFilter = OngoingNotiPackageFilter.OngoingNotiPackageFilterHolder.INSTANCE;
            Integer valueOf3 = Integer.valueOf(i);
            ArrayList arrayList4 = (ArrayList) ongoingNotiPackageFilter.mNLSPkgMap.get(valueOf3);
            if (arrayList4 != null) {
                arrayList4.remove(str);
            }
            ongoingNotiPackageFilter.mNLSPkgMap.put(valueOf3, arrayList4);
        }
        if (("android.service.wallpaper.WallpaperService".equals(str2) || "com.samsung.android.service.wallpaper.LiveWallpaperService".equals(str2) || "com.samsung.android.service.wallpaper.CoverWallpaperService".equals(str2)) && "android".equals(str3)) {
            WallPaperFilter wallPaperFilter = WallPaperFilter.WallPaperFilterHolder.INSTANCE;
            synchronized (wallPaperFilter.mWallpaperPackageList) {
                wallPaperFilter.mWallpaperPackageList.remove(str);
                MARsUtils.addFilterDebugInfoToHistory("FILTER 10 remove", str);
            }
        }
    }

    public static void resetAbusiveFlag(MARsPackageInfo mARsPackageInfo) {
        StringBuilder sb = new StringBuilder("resetAbusiveFlag uid:");
        sb.append(mARsPackageInfo.uid);
        sb.append(" pkgname:");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, mARsPackageInfo.name, "MARsPolicyManager");
        synchronized (MARsLock) {
            int i = mARsPackageInfo.optionFlag;
            if ((i & 8) == 8) {
                mARsPackageInfo.optionFlag = i & (-9);
            }
            int i2 = mARsPackageInfo.optionFlag;
            if ((i2 & 16) == 16) {
                mARsPackageInfo.optionFlag = i2 & (-17);
                boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController.FreecessControllerHolder.INSTANCE.updateAbnormalAppFirewall(mARsPackageInfo.uid, true);
            }
        }
    }

    public static boolean setEnabledSetting(int i, int i2, int i3, String str) {
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                asInterface.setApplicationEnabledSetting(str, i2, i3, i, "auto_disabler");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Slog.e("MARsPolicyManager", "Error occurred in setEnabledSetting()" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void updateInfoToMARsPkgStatus(MARsPackageInfo mARsPackageInfo, PkgStatusInfo pkgStatusInfo) {
        mARsPackageInfo.fasType = pkgStatusInfo.fasType;
        mARsPackageInfo.state = pkgStatusInfo.state;
        mARsPackageInfo.isDisabled = pkgStatusInfo.isDisabled;
        mARsPackageInfo.disableType = pkgStatusInfo.disableType;
        mARsPackageInfo.curLevel = pkgStatusInfo.currentLevel;
        mARsPackageInfo.maxLevel = pkgStatusInfo.maxLevel;
        mARsPackageInfo.isFASEnabled = pkgStatusInfo.isFASEnabled;
        mARsPackageInfo.isInRestrictedBucket = pkgStatusInfo.isInRestrictedBucket;
        mARsPackageInfo.disableReason = pkgStatusInfo.disableReason;
    }

    public static void updateInfoToPkgStatus(MARsPackageInfo mARsPackageInfo, PkgStatusInfo pkgStatusInfo) {
        pkgStatusInfo.fasType = mARsPackageInfo.fasType;
        pkgStatusInfo.state = mARsPackageInfo.state;
        pkgStatusInfo.isDisabled = mARsPackageInfo.isDisabled;
        pkgStatusInfo.disableType = mARsPackageInfo.disableType;
        pkgStatusInfo.currentLevel = mARsPackageInfo.curLevel;
        pkgStatusInfo.maxLevel = mARsPackageInfo.maxLevel;
        pkgStatusInfo.isFASEnabled = mARsPackageInfo.isFASEnabled;
        pkgStatusInfo.isInRestrictedBucket = mARsPackageInfo.isInRestrictedBucket;
        pkgStatusInfo.disableReason = mARsPackageInfo.disableReason;
    }

    public final void addDebugInfoToHistory(String str, String str2) {
        if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
            StringBuilder sb = new StringBuilder();
            sb.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
            sb.append("[" + str + "] ");
            sb.append("[" + str2 + "]\n");
            MARsHistoryBuffer.MARsHistoryBufferHolder.INSTANCE.put(sb.toString());
            return;
        }
        if (this.mHistoryBufferArray != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
            sb2.append("[" + str + "] ");
            sb2.append("[" + str2 + "]\n");
            this.mHistoryBufferArray.put(sb2.toString());
        }
    }

    public final void addNetDebugInfoToHistory(String str) {
        if (this.mNetHistoryBufferArray != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
            sb.append("[NET] ");
            sb.append("[" + str + "]\n");
            this.mNetHistoryBufferArray.put(sb.toString());
        }
    }

    public final void addRestrictListAvoidAssoicationLaunch(int i, String str, String str2, String str3) {
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo != null) {
                    mARsPackageInfo.appliedPolicy = this.autoRunPolicy;
                    mARsPackageInfo.curLevel = 3;
                    if (this.mMARsRestrictedPackages.get(mARsPackageInfo.userId, mARsPackageInfo.name) == null) {
                        this.mMARsRestrictedPackages.put(mARsPackageInfo.name, mARsPackageInfo.userId, mARsPackageInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        addDebugInfoToHistory(str3, str2 + " " + str);
    }

    public final List awakePackageList(String str, List list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it.next();
            String packageName = appRestrictionInfo.getPackageName();
            int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
            if (isCurrentUser(userId)) {
                synchronized (MARsLock) {
                    try {
                        MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                        if (mARsPackageInfo != null) {
                            int i = mARsPackageInfo.maxLevel;
                            if (i == 4) {
                                arrayList2.add(appRestrictionInfo);
                            } else {
                                if (mARsPackageInfo.isFASEnabled || i > 1) {
                                    levelChange(256, mARsPackageInfo);
                                    MARsHandler.MARsHandlerHolder.INSTANCE.sendCallSetModeMsgToMainHandler(mARsPackageInfo.uid, 0, mARsPackageInfo.name);
                                }
                                mARsPackageInfo.fasType = FASTableContract.convertFASReasonToValue(str);
                                mARsPackageInfo.fasReason = str;
                                String str2 = null;
                                mARsPackageInfo.appliedPolicy = null;
                                this.mMARsRestrictedPackages.remove(mARsPackageInfo.userId, mARsPackageInfo.name);
                                FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                                fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                                boolean z = mARsPackageInfo.isFASEnabled;
                                fASEntityBuilder.strMode = z ? "1" : "0";
                                if (!z) {
                                    str2 = FASTableContract.convertFASTypeToReason(mARsPackageInfo.fasType);
                                }
                                fASEntityBuilder.strFasReason = str2;
                                fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo.resetTime);
                                fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                fASEntityBuilder.strDisableType = Integer.toString(mARsPackageInfo.disableType);
                                fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
                                arrayList.add(fASEntityBuilder.build());
                                arrayList3.add(Integer.valueOf(mARsPackageInfo.uid));
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            arrayList3.addAll(enablePackageList(str, list));
        }
        if (!arrayList.isEmpty()) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
        }
        return arrayList3;
    }

    public final boolean canRestrictBySEPWithoutPermissionCheck(int i, int i2, String str) {
        int userId = UserHandle.getUserId(i2);
        if (isCurrentUser(userId) && str != null && isMARsTarget(userId, str)) {
            if (i == 0) {
                String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
                if (MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(21, str, null, null)) {
                    return false;
                }
                try {
                    int applicationEnabledSetting = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getApplicationEnabledSetting(str, userId);
                    if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                        return false;
                    }
                    FilterManager.FilterManagerHolder.INSTANCE.getClass();
                    if (FilterManager.filterForSpecificPolicy(8, userId, i2, str) == 0) {
                        return true;
                    }
                } catch (Exception e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(e, "Exception ", "MARsPolicyManager");
                    return false;
                }
            } else {
                if (i == 1 || i == 3) {
                    return true;
                }
                if (i == 2) {
                    synchronized (MARsLock) {
                        MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
                        if (mARsPackageInfo != null && !AllowListFilter.AllowListFilterHolder.INSTANCE.isInDefaultAllowList(mARsPackageInfo.name) && mARsPackageInfo.hasAppIcon) {
                            FilterManager.FilterManagerHolder.INSTANCE.getClass();
                            if (FilterManager.filterForSpecificPolicy(7, userId, i2, str) == 0) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public final boolean cancelDisablePolicy(String str, int i, int i2) {
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
                if (mARsPackageInfo == null) {
                    return false;
                }
                Slog.d("MARsPolicyManager", "cancelDisablePolicy " + str + "(" + i + ") " + mARsPackageInfo.isDisabled + "|" + mARsPackageInfo.disableReason + "|" + mARsPackageInfo.appliedPolicy);
                Policy policy = mARsPackageInfo.appliedPolicy;
                if (policy != null && policy.num == 8) {
                    if (!mARsPackageInfo.isDisabled && isDisabledByUser(mARsPackageInfo.disableReason)) {
                        return true;
                    }
                    int i3 = mARsPackageInfo.disableType;
                    if (i3 == -1 || i3 == 4) {
                        i3 = 0;
                    }
                    if (!setEnabledSetting(i, i3, i2, str)) {
                        return false;
                    }
                    changeAutoDisabledAppState(i, str, i2 != 0);
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    public final void cancelPolicy(int i, int i2, String str, boolean z) {
        int i3;
        String str2;
        ArrayList arrayList = new ArrayList();
        synchronized (MARsLock) {
            try {
                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                if (freecessController.mIsFreecessEnable) {
                    if (freecessController.protectFreezePackage(i2, str, (z && FreecessController.FASTOLAF_REDUCE_PROTECT_TIME) ? 1500L : 3000L, "CancelPolicy")) {
                        return;
                    }
                }
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i2);
                if (mARsPackageInfo != null) {
                    Policy policy = mARsPackageInfo.appliedPolicy;
                    if (policy != null && policy.num == i) {
                        if (i != 8) {
                            mARsPackageInfo.appliedPolicy = null;
                            mARsPackageInfo.curLevel = 0;
                        }
                        if (i == 6) {
                            mARsPackageInfo.sbike = 0;
                        }
                        if (i == 8 && (mARsPackageInfo.isDisabled || isDisabledByUser(mARsPackageInfo.disableReason))) {
                            arrayList.add(mARsPackageInfo);
                        }
                    }
                    if (mARsPackageInfo.appliedPolicy == null && i != 8) {
                        this.mMARsRestrictedPackages.remove(mARsPackageInfo.userId, mARsPackageInfo.name);
                    }
                }
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    synchronized (MARsLock) {
                        MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i4);
                        if (mARsPackageInfo2 != null) {
                            str2 = mARsPackageInfo2.name;
                            i3 = mARsPackageInfo2.userId;
                        } else {
                            i3 = -1;
                            str2 = null;
                        }
                    }
                    cancelDisablePolicy(str2, i3, 0);
                }
            } finally {
            }
        }
    }

    public final void changeAutoDisabledAppState(int i, String str, boolean z) {
        FASEntity fASEntity;
        Policy policy;
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
                fASEntity = null;
                if (mARsPackageInfo != null && (policy = mARsPackageInfo.appliedPolicy) != null && policy.num == 8) {
                    if (isDisabledByUser(mARsPackageInfo.disableReason)) {
                        levelChange(1024, mARsPackageInfo);
                    } else {
                        if (z) {
                            levelChange(2048, mARsPackageInfo);
                            mARsPackageInfo.disableReason = 8;
                        } else {
                            levelChange(1024, mARsPackageInfo);
                            mARsPackageInfo.disableReason = 4;
                        }
                        if (z && isChinaPolicyEnabled()) {
                            mARsPackageInfo.curLevel = 2;
                            mARsPackageInfo.appliedPolicy = this.autoRunPolicy;
                        } else {
                            mARsPackageInfo.curLevel = 0;
                            mARsPackageInfo.appliedPolicy = null;
                            this.mMARsRestrictedPackages.remove(mARsPackageInfo.userId, mARsPackageInfo.name);
                        }
                        mARsPackageInfo.disableType = -1;
                        FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                        fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                        fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                        boolean z2 = mARsPackageInfo.isFASEnabled;
                        fASEntityBuilder.strMode = z2 ? "1" : "0";
                        fASEntityBuilder.strNew = z2 ? "1" : "0";
                        fASEntityBuilder.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo.fasType);
                        fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo.resetTime);
                        fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                        fASEntityBuilder.strDisableType = Integer.toString(mARsPackageInfo.disableType);
                        fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
                        fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                        fASEntity = fASEntityBuilder.build();
                    }
                    mARsPackageInfo.isDisabled = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (fASEntity != null) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkUnusedTargetForDeepSleep() {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.checkUnusedTargetForDeepSleep():void");
    }

    public final void closeSocketsForUid(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        try {
            if (iNetworkManagementService != null) {
                try {
                    iNetworkManagementService.closeSocketsForUid(i);
                } catch (Exception e) {
                    Slog.e("MARsPolicyManager", "Error occurred while closeSocketsForUid: " + e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deletePkgInfoInMARs(ArrayList arrayList) {
        int i;
        if (arrayList.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Slog.d("MARsPolicyManager", "deletePkgInfoInMARs size = " + arrayList.size());
        synchronized (MARsLock) {
            try {
                int size = this.mMARsTargetPackages.mMap.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(size);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
                        int i3 = 0;
                        while (true) {
                            if (i3 >= arrayList.size()) {
                                arrayList2.add(mARsPackageInfo);
                                break;
                            }
                            MARsPackageInfo mARsPackageInfo2 = new MARsPackageInfo((FASEntity) arrayList.get(i3));
                            if (mARsPackageInfo.name.equals(mARsPackageInfo2.name) && mARsPackageInfo.uid == mARsPackageInfo2.uid) {
                                mARsPackageInfo2.isDisabled = mARsPackageInfo.isDisabled;
                                mARsPackageInfo.updatePackageInfo(mARsPackageInfo2);
                                break;
                            }
                            i3++;
                        }
                    }
                    size--;
                }
                for (i = 0; i < arrayList2.size(); i++) {
                    MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) arrayList2.get(i);
                    this.mMARsTargetPackages.remove(mARsPackageInfo3.userId, mARsPackageInfo3.name);
                    this.mMARsRestrictedPackages.remove(mARsPackageInfo3.userId, mARsPackageInfo3.name);
                }
            } finally {
            }
        }
    }

    public final boolean disablePackageForSpecific(int i, String str, String str2) {
        FASEntity fASEntity;
        boolean z;
        PkgStatusInfo pkgStatusInfo;
        boolean z2;
        PkgStatusInfo pkgStatusInfo2;
        long clearCallingIdentity;
        int userId = UserHandle.getUserId(i);
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
                fASEntity = null;
                fASEntity = null;
                fASEntity = null;
                z = false;
                if (mARsPackageInfo != null) {
                    if (!"added_from_anomaly_manual".equals(str2)) {
                        mARsPackageInfo.isSCPMTarget = true;
                    }
                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                    if (freecessController.mIsFreecessEnable) {
                        if (freecessController.isFreezedPackage(mARsPackageInfo.userId, mARsPackageInfo.name)) {
                            freecessController.unFreezePackage(mARsPackageInfo.userId, mARsPackageInfo.name, "specificDisable");
                        }
                    }
                    PkgStatusInfo pkgStatusInfo3 = new PkgStatusInfo(mARsPackageInfo.uid, mARsPackageInfo.userId, mARsPackageInfo.name);
                    z2 = levelChange(8192, mARsPackageInfo);
                    mARsPackageInfo.disableReason = FASTableContract.convertDBValueToDisableReason(str2);
                    if (android.app.Flags.appRestrictionsApi()) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        boolean equals = "added_from_anomaly_manual".equals(str2);
                        ActivityManagerService activityManagerService = this.mAm;
                        int i2 = equals ? 90 : 70;
                        int i3 = equals ? 5 : 6;
                        String str3 = equals ? "anomaly" : "cleaner";
                        pkgStatusInfo2 = pkgStatusInfo3;
                        activityManagerService.noteAppRestrictionEnabled(str, i, i2, true, i3, str3, 3, 0L);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } else {
                        pkgStatusInfo2 = pkgStatusInfo3;
                    }
                    updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo2);
                    MARsPolicyManagerHolder.INSTANCE.getClass();
                    if (isChinaPolicyEnabled()) {
                        resetAbusiveFlag(mARsPackageInfo);
                    }
                    pkgStatusInfo = pkgStatusInfo2;
                } else {
                    pkgStatusInfo = null;
                    z2 = false;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            } finally {
            }
        }
        if (z2 && pkgStatusInfo != null) {
            try {
                IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                int applicationEnabledSetting = asInterface.getApplicationEnabledSetting(pkgStatusInfo.name, pkgStatusInfo.userId);
                if (applicationEnabledSetting != 0 && applicationEnabledSetting != 1) {
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d("MARsPolicyManager", "pkg " + pkgStatusInfo.name + " uid " + pkgStatusInfo.uid + " is already disabled, so we will not disable");
                    }
                }
                if (!asInterface.isPackageSuspendedForUser(pkgStatusInfo.name, pkgStatusInfo.userId)) {
                    asInterface.setApplicationEnabledSetting(pkgStatusInfo.name, 4, 0, pkgStatusInfo.userId, "auto_disabler");
                    pkgStatusInfo.disableType = applicationEnabledSetting;
                    pkgStatusInfo.isDisabled = true;
                }
                z = true;
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(e, "Error occurred in disable package : ", "MARsPolicyManager");
            }
        }
        if (pkgStatusInfo != null && z) {
            synchronized (MARsLock) {
                try {
                    MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, pkgStatusInfo.name, pkgStatusInfo.userId);
                    if (mARsPackageInfo2 != null) {
                        updateInfoToMARsPkgStatus(mARsPackageInfo2, pkgStatusInfo);
                        mARsPackageInfo2.appliedPolicy = this.disablePolicy;
                        if (this.mMARsRestrictedPackages.get(mARsPackageInfo2.userId, mARsPackageInfo2.name) == null) {
                            this.mMARsRestrictedPackages.put(mARsPackageInfo2.name, mARsPackageInfo2.userId, mARsPackageInfo2);
                        }
                        FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                        fASEntityBuilder.strPkgName = mARsPackageInfo2.name;
                        fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo2.uid);
                        boolean z4 = mARsPackageInfo2.isFASEnabled;
                        fASEntityBuilder.strMode = z4 ? "1" : null;
                        fASEntityBuilder.strNew = z4 ? "1" : null;
                        int i4 = mARsPackageInfo2.fasType;
                        fASEntityBuilder.strFasReason = i4 == 1 ? FASTableContract.convertFASTypeToReason(i4) : null;
                        fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo2.disableReason);
                        fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.state);
                        fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo2.maxLevel);
                        fASEntity = fASEntityBuilder.build();
                    }
                } finally {
                }
            }
        }
        FASEntity fASEntity2 = fASEntity;
        if (fASEntity2 != null) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity2);
        }
        return z;
    }

    public final List disablePackageList(String str, List list) {
        boolean z;
        PkgStatusInfo pkgStatusInfo;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = getChangedByUserFromReason(str) ? 128 : 8;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it.next();
            String packageName = appRestrictionInfo.getPackageName();
            int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
            if (isCurrentUser(userId) && isMARsTarget(userId, packageName)) {
                Lock lock = MARsLock;
                synchronized (lock) {
                    try {
                        MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                        if (mARsPackageInfo == null) {
                            z = false;
                            pkgStatusInfo = null;
                        } else if (!mARsPackageInfo.isDisabled || !str.equals(Integer.valueOf(mARsPackageInfo.disableReason))) {
                            z = levelChange(i, mARsPackageInfo);
                            if (z) {
                                pkgStatusInfo = new PkgStatusInfo(mARsPackageInfo.uid, mARsPackageInfo.userId, mARsPackageInfo.name);
                                updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
                            } else if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d("MARsPolicyManager", "pkg " + mARsPackageInfo.name + " uid " + mARsPackageInfo.uid + " can't be disabled, so we will not disable");
                            }
                        } else if (MARsDebugConfig.DEBUG_ENG) {
                            Slog.d("MARsPolicyManager", "pkg : " + mARsPackageInfo.name + " uid : " + mARsPackageInfo.uid + " is already disabled, so we will not disable");
                        }
                        if (z && pkgStatusInfo != null) {
                            boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                            FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                            if (freecessController.mIsFreecessEnable && freecessController.isFreezedPackage(userId, packageName)) {
                                freecessController.unFreezePackage(userId, packageName, "manualDisable");
                            }
                            boolean disableAction = disableAction(pkgStatusInfo);
                            synchronized (lock) {
                                try {
                                    MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                                    if (mARsPackageInfo2 != null) {
                                        updateInfoToMARsPkgStatus(mARsPackageInfo2, pkgStatusInfo);
                                        if (disableAction) {
                                            mARsPackageInfo2.appliedPolicy = this.disablePolicy;
                                            if (this.mMARsRestrictedPackages.get(mARsPackageInfo2.userId, mARsPackageInfo2.name) == null) {
                                                this.mMARsRestrictedPackages.put(mARsPackageInfo2.name, mARsPackageInfo2.userId, mARsPackageInfo2);
                                            }
                                        }
                                        FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                        fASEntityBuilder.strPkgName = mARsPackageInfo2.name;
                                        fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo2.uid);
                                        boolean z3 = mARsPackageInfo2.isFASEnabled;
                                        fASEntityBuilder.strMode = z3 ? "1" : null;
                                        fASEntityBuilder.strFasReason = z3 ? FASTableContract.convertFASTypeToReason(mARsPackageInfo2.fasType) : null;
                                        fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.state);
                                        fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo2.resetTime);
                                        fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo2.maxLevel);
                                        fASEntityBuilder.strDisableType = Integer.toString(mARsPackageInfo2.disableType);
                                        fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo2.disableReason);
                                        arrayList.add(fASEntityBuilder.build());
                                        arrayList2.add(Integer.valueOf(mARsPackageInfo2.uid));
                                    }
                                } finally {
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
        }
        return arrayList2;
    }

    public final List disablePackageListForSpecific(String str, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it.next();
            String packageName = appRestrictionInfo.getPackageName();
            int uid = appRestrictionInfo.getUid();
            if (disablePackageForSpecific(uid, packageName, str)) {
                arrayList.add(Integer.valueOf(uid));
            }
        }
        if (!list.isEmpty()) {
            addDebugInfoToHistory("SEP", "DIS_SPE " + arrayList.toString() + " f: " + (list.size() - arrayList.size()));
        }
        return arrayList;
    }

    public final void dumpMARs(PrintWriter printWriter) {
        printWriter.append("ACTIVITY MANAGER MARs (dumpsys activity mars)\n");
        synchronized (MARsLock) {
            try {
                printWriter.println("mLastNotiSentTimeForDisabled : " + formatDateTimeWithoutYear(this.mLastNotiSentTimeForDisabled));
                printWriter.println("ENABLE_ALARM_WAKEUP_BLOCK=".concat(MARsDebugConfig.ENABLE_ALARM_WAKEUP_BLOCK ? "Y" : "N"));
                printWriter.println("ENABLE_KILL_LONG_RUNNING_PROCESS=".concat(ENABLE_KILL_LONG_RUNNING_PROCESS ? "Y" : "N"));
                printWriter.println("mMARsTargetPackages --- size " + this.mMARsTargetPackages.totalSize());
                for (int i = 0; i < this.mMARsTargetPackages.mMap.size(); i++) {
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
                        if (mARsPackageInfo != null) {
                            printWriter.print("-RST ");
                            long j = mARsPackageInfo.disableResetTime;
                            printWriter.print(j == 0 ? String.format("%23s", "null") : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date(j)));
                            printWriter.print("-PT ");
                            printWriter.print(String.format("%6d", Integer.valueOf(mARsPackageInfo.packageType)));
                            printWriter.print("-ST ");
                            printWriter.print(String.format("%2d", Integer.valueOf(mARsPackageInfo.state)));
                            printWriter.print("-SC ");
                            printWriter.print(mARsPackageInfo.isSCPMTarget ? "T" : "F");
                            printWriter.print("-DT ");
                            printWriter.print(String.format("%2d", Integer.valueOf(mARsPackageInfo.disableType)));
                            printWriter.print("-DR ");
                            printWriter.print(String.format("%2d", Integer.valueOf(mARsPackageInfo.disableReason)));
                            printWriter.print("-DD ");
                            printWriter.print(mARsPackageInfo.isDisabled ? "T" : "F");
                            printWriter.print("-Uid ");
                            printWriter.print(String.format("%8d", Integer.valueOf(mARsPackageInfo.uid)));
                            printWriter.print("(");
                            if (mARsPackageInfo.sharedUidName != null) {
                                printWriter.print("S");
                            } else {
                                printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                            }
                            printWriter.print(")");
                            printWriter.print("-Idle");
                            printWriter.print("(");
                            printWriter.print(")");
                            if (MARsDebugConfig.DEBUG_ENG) {
                                printWriter.print("-curLv ");
                            }
                            printWriter.print(mARsPackageInfo.curLevel);
                            printWriter.print("-maxLv ");
                            printWriter.print(mARsPackageInfo.maxLevel);
                            printWriter.print("-FAS ");
                            StringBuilder sb = new StringBuilder();
                            sb.append(mARsPackageInfo.isFASEnabled ? "Y" : "N");
                            sb.append("/");
                            sb.append(String.format("%4d", Integer.valueOf(mARsPackageInfo.fasType)));
                            printWriter.print(sb.toString());
                            printWriter.print("(");
                            printWriter.print((mARsPackageInfo.fasType & 129) != 0 ? "A" : PackageManagerShellCommandDataLoader.STDIN_PATH);
                            printWriter.print(")");
                            printWriter.print("-BU ");
                            printWriter.print(String.format("%10f", Double.valueOf(mARsPackageInfo.BatteryUsage)));
                            printWriter.print("-Pkg ");
                            printWriter.print(mARsPackageInfo.name);
                            if (UidStateMgr.UidStateMgrHolder.INSTANCE.isUidRunning(mARsPackageInfo.uid)) {
                                printWriter.print("--(R)");
                            }
                            printWriter.println("");
                        }
                    }
                }
                printWriter.println("mMARsRestrictedPackages --- size " + this.mMARsRestrictedPackages.totalSize());
                for (int i3 = 0; i3 < this.mMARsRestrictedPackages.mMap.size(); i3++) {
                    SparseArray sparseArray2 = (SparseArray) this.mMARsRestrictedPackages.mMap.valueAt(i3);
                    for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
                        MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) sparseArray2.valueAt(i4);
                        printWriter.print("-Uid ");
                        printWriter.print(String.format("%8d", Integer.valueOf(mARsPackageInfo2.uid)));
                        printWriter.print("-Pkg ");
                        printWriter.print(mARsPackageInfo2.name);
                        printWriter.print("-POL ");
                        Policy policy = mARsPackageInfo2.appliedPolicy;
                        if (policy != null) {
                            String policy2 = policy.toString();
                            printWriter.print(policy2.substring(policy2.length() - 3, policy2.length()));
                        } else {
                            printWriter.print("null");
                        }
                        printWriter.println("");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("");
        MARsHandler.MainHandler mainHandler = MARsHandler.MARsHandlerHolder.INSTANCE.mMainHandler;
        if (mainHandler != null) {
            mainHandler.dump(new PrintWriterPrinter(printWriter), "MARsHandler");
        }
    }

    public final void dumpMARsCommand(PrintWriter printWriter, String[] strArr) {
        MARsPackageInfo mARsPackageInfo;
        int i;
        boolean z;
        if (strArr.length == 1) {
            dumpMARs(printWriter);
            dumpMARsHistory(printWriter);
            boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
            if (freecessController.mIsFreecessEnable) {
                freecessController.dumpFreecess(printWriter);
                return;
            }
            return;
        }
        if ("dbtest".equals(strArr[1])) {
            MARsDBManager.MARsDBManagerHolder.INSTANCE.getSCPMList();
        }
        if ("ct_on".equals(strArr[1])) {
            MARsComponentTracker mARsComponentTracker = MARsComponentTracker.MARsComponentTrackerHolder.INSTANCE;
            mARsComponentTracker.isEnabledCT = true;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("ComponentTracker : "), mARsComponentTracker.isEnabledCT, printWriter);
        }
        if ("ct_off".equals(strArr[1])) {
            MARsComponentTracker mARsComponentTracker2 = MARsComponentTracker.MARsComponentTrackerHolder.INSTANCE;
            mARsComponentTracker2.isEnabledCT = false;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("ComponentTracker : "), mARsComponentTracker2.isEnabledCT, printWriter);
        }
        if ("ct_onoff".equals(strArr[1])) {
            MARsComponentTracker mARsComponentTracker3 = MARsComponentTracker.MARsComponentTrackerHolder.INSTANCE;
            mARsComponentTracker3.isEnabledCT = !mARsComponentTracker3.isEnabledCT;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("ComponentTracker : "), mARsComponentTracker3.isEnabledCT, printWriter);
        }
        if ("restrict_onoff".equals(strArr[1])) {
            this.ENABLE_RESTRICTED_BUCKET = !this.ENABLE_RESTRICTED_BUCKET;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("ENABLE_RESTRICTED_BUCKET : "), this.ENABLE_RESTRICTED_BUCKET, printWriter);
        }
        if ("bigdata".equals(strArr[1])) {
            MARsBigData mARsBigData = MARsBigData.MARsBigDataHolder.INSTANCE;
            mARsBigData.getClass();
            try {
                mARsBigData.updateBigdataInfo();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                mARsBigData.PLEVdata = new MARsBigData.UsageInfo().toString();
            }
            String str = mARsBigData.PLEVdata;
            if (str != null) {
                mARsBigData.sendBigData("PLEV", str);
            }
        }
        if ("bstat".equals(strArr[1])) {
            getBatteryStats();
        }
        if ("disable".equals(strArr[1])) {
            if (strArr.length < 3) {
                if (this.disablePolicy != null) {
                    setPackageDisablerEnabled(!r0.enabled);
                    printWriter.println("Disabler policy has been turned ".concat(getPackageDisablerEnabled() ? "on" : "off"));
                }
            } else if (strArr.length == 3) {
                String str2 = strArr[2];
                if (str2 == null) {
                    printWriter.println("Disable will not work, please input proper packageName!");
                    return;
                }
                synchronized (MARsLock) {
                    MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, str2, this.mContext.getUserId());
                    if (mARsPackageInfo2 != null) {
                        int i2 = mARsPackageInfo2.uid;
                        z = mARsPackageInfo2.isDisabled;
                        i = i2;
                    } else {
                        i = -1;
                        z = false;
                    }
                }
                if (i != -1) {
                    boolean restrictBySEP = z ? restrictBySEP(0, 2, str2, i, false) : restrictBySEP(0, 1, str2, i, false);
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(BootReceiver$$ExternalSyntheticOutline0.m(str2), restrictBySEP ? " has been ".concat(z ? "enabled" : "disabled!") : " is not in our target, we will not manage it!", printWriter);
                } else {
                    printWriter.println(str2.concat("is not in our target, we will not manage it!"));
                }
            }
        }
        if ("filter".equals(strArr[1])) {
            try {
                StringBuilder sb = new StringBuilder("Filter -- freecess ");
                FilterManager filterManager = FilterManager.FilterManagerHolder.INSTANCE;
                int parseInt = Integer.parseInt(strArr[2]);
                String str3 = strArr[3];
                int parseInt2 = Integer.parseInt(strArr[4]);
                int parseInt3 = Integer.parseInt(strArr[5]);
                filterManager.getClass();
                sb.append(FilterManager.filterForSpecificPolicy(parseInt, parseInt2, parseInt3, str3));
                printWriter.println(sb.toString());
            } catch (NumberFormatException unused) {
                Slog.e("MARsPolicyManager", "dumpMARsCommand(filter) parseInt error!");
            }
        }
        if ("aufbucket".equals(strArr[1])) {
            try {
                AUFAllowBucketSize = Integer.parseInt(strArr[2]);
                printWriter.println("AUFAllowBucketSize set " + AUFAllowBucketSize);
            } catch (NumberFormatException unused2) {
                Slog.e("MARsPolicyManager", "dumpMARsCommand(aufbucket) parseInt error!");
            }
        }
        if ("longrunning".equals(strArr[1])) {
            if (strArr.length >= 3) {
                if ("on".equals(strArr[2])) {
                    ENABLE_KILL_LONG_RUNNING_PROCESS = true;
                } else if ("off".equals(strArr[2])) {
                    ENABLE_KILL_LONG_RUNNING_PROCESS = false;
                } else if ("duration".equals(strArr[2]) && strArr.length >= 4) {
                    try {
                        this.KEEP_NO_FILTER_MIN_DURATION = Integer.parseInt(strArr[3]);
                    } catch (NumberFormatException unused3) {
                        Slog.e("MARsPolicyManager", "dumpMARsCommand(longrunning) parseInt error!");
                    }
                }
            }
            printWriter.println("enable=" + ENABLE_KILL_LONG_RUNNING_PROCESS + ", d=" + this.KEEP_NO_FILTER_MIN_DURATION);
        }
        if ("fgsRunningTime".equals(strArr[1])) {
            long uptimeMillis = SystemClock.uptimeMillis();
            synchronized (this.mFGServiceStartTimeMap) {
                try {
                    for (Integer num : this.mFGServiceStartTimeMap.keySet()) {
                        long longValue = ((Long) this.mFGServiceStartTimeMap.get(num)).longValue();
                        printWriter.println("uid=" + num + ", fgsRunningTime=" + (uptimeMillis - longValue) + ", fgsStartTime=" + longValue);
                    }
                } finally {
                }
            }
        }
        if ("history".equals(strArr[1])) {
            dumpMARsHistory(printWriter);
        }
        if ("hold".equals(strArr[1]) || BuildConfig.BUILD_TYPE.equals(strArr[1])) {
            if (strArr.length < 2) {
                printWriter.println("hold/release requires at least 1 argument: uid");
                return;
            }
            int parseInt4 = Integer.parseInt(strArr[2]);
            boolean equals = "hold".equals(strArr[1]);
            printWriter.println(ActiveServices$$ExternalSyntheticOutline0.m(parseInt4, equals ? "Hold" : "Release", " jobs by u=", " calling by MARs.", new StringBuilder()));
            restrictJobsByUid(parseInt4, equals);
        }
        if ("level".equals(strArr[1])) {
            if (strArr.length < 3) {
                printWriter.println("MARstest -- need levelNum and packageName");
            } else if (strArr.length == 4) {
                try {
                    int parseInt5 = Integer.parseInt(strArr[2]);
                    if ("-a".equals(strArr[3])) {
                        synchronized (MARsLock) {
                            for (int i3 = 0; i3 < this.mMARsTargetPackages.mMap.size(); i3++) {
                                try {
                                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i3);
                                    for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                                        MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) sparseArray.valueAt(i4);
                                        if (mARsPackageInfo3 != null) {
                                            setMaxLevel(parseInt5, mARsPackageInfo3.name);
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    } else {
                        setMaxLevel(parseInt5, strArr[3]);
                    }
                } catch (NumberFormatException unused4) {
                    printWriter.println("MARstest -- NumberFormatException!");
                }
            }
        }
        if ("list".equals(strArr[1])) {
            dumpMARs(printWriter);
        }
        if ("close_socket".equals(strArr[1])) {
            if (strArr.length < 3) {
                printWriter.println("usage: close_socket <uid>");
            } else {
                int parseInt6 = Integer.parseInt(strArr[2]);
                printWriter.println("Calling closeSocketsForUid: u=" + parseInt6);
                closeSocketsForUid(parseInt6);
            }
        }
        if ("time_disable".equals(strArr[1]) && (mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, strArr[2], this.mContext.getUserId())) != null) {
            mARsPackageInfo.resetTime = System.currentTimeMillis() - this.mAutoDisabledLockingTime;
        }
        if ("update".equals(strArr[1])) {
            updateFromMARsMainThread();
        }
        if ("widget".equals(strArr[1])) {
            WidgetPkgFilter.WidgetPkgFilterHolder.INSTANCE.getBoundAppWidgetPackages();
        }
        if ("deepsleep_target".equals(strArr[1])) {
            checkUnusedTargetForDeepSleep();
        }
        if ("candidate".equals(strArr[1])) {
            if (strArr.length < 3) {
                printWriter.println("MARstest -- need type and packageName");
            } else if (strArr.length == 4) {
                try {
                    synchronized (MARsLock) {
                        try {
                            MARsPackageInfo mARsPackageInfo4 = getMARsPackageInfo(this.mMARsTargetPackages, strArr[3], this.mContext.getUserId());
                            if (mARsPackageInfo4 == null) {
                                printWriter.println("MARstest -- not mars target!");
                            } else if ("-s".equals(strArr[2])) {
                                levelChange(1, mARsPackageInfo4);
                            } else if ("-d".equals(strArr[2])) {
                                levelChange(4, mARsPackageInfo4);
                            }
                        } finally {
                        }
                    }
                } catch (NumberFormatException unused5) {
                    printWriter.println("MARstest -- NumberFormatException!");
                }
            }
        }
        if ("debug".equals(strArr[1])) {
            if ("help".equals(strArr[2])) {
                BatteryService$$ExternalSyntheticOutline0.m(printWriter, "MARs debug options commands:", "  help", "     Print this help text.", "  all");
                BatteryService$$ExternalSyntheticOutline0.m(printWriter, "     Enable/Disable all mars debug message.", "  olaf", "     Enable/Disable olaf debug message.", "  freecess");
                BatteryService$$ExternalSyntheticOutline0.m(printWriter, "     Enable/Disable freecess debug message.", "  database", "     Enable/Disable MARs database debug message.", "  filter");
                printWriter.println("     Enable/Disable MARs filter debug message.");
                return;
            }
            if ("all".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_MARs = !MARsDebugConfig.DEBUG_MARs;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_MARs ? "on" : "off", " now!", new StringBuilder(" mars debugging mode is "));
                return;
            }
            if ("olaf".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_OLAF = !MARsDebugConfig.DEBUG_OLAF;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_OLAF ? "on" : "off", " now!", new StringBuilder(" olaf debugging mode is "));
                return;
            }
            if ("freecess".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_FREECESS = !MARsDebugConfig.DEBUG_FREECESS;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_FREECESS ? "on" : "off", " now!", new StringBuilder(" freecess debugging mode is "));
                return;
            }
            if ("database".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_DATABASE = !MARsDebugConfig.DEBUG_DATABASE;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_DATABASE ? "on" : "off", " now!", new StringBuilder(" database debugging mode is "));
            } else if ("filter".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_FILTER = !MARsDebugConfig.DEBUG_FILTER;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_FILTER ? "on" : "off", " now!", new StringBuilder(" filter debugging mode is "));
            } else {
                if (!"netlink".equals(strArr[2])) {
                    printWriter.println("Error: debug command requires argument");
                    return;
                }
                MARsDebugConfig.DEBUG_NETLINK = !MARsDebugConfig.DEBUG_NETLINK;
                ProxyManager$$ExternalSyntheticOutline0.m(printWriter, MARsDebugConfig.DEBUG_NETLINK ? "on" : "off", " now!", new StringBuilder(" netlink debugging mode is "));
            }
        }
    }

    public final void dumpMARsHistory(PrintWriter printWriter) {
        ArrayList log;
        printWriter.println("");
        printWriter.println("ACTIVITY MANAGER MARs HISTORY (dumpsys activity mars history)");
        if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
            synchronized (MARsHistoryBuffer.MARsHistoryBufferHolder.INSTANCE) {
                log = MARsHistoryLog.MARsHistoryLogHolder.INSTANCE.getLog();
            }
            for (int i = 0; i < log.size(); i++) {
                printWriter.print((String) log.get(i));
            }
            printWriter.println("");
        } else {
            HistoryBuffer historyBuffer = this.mHistoryBufferArray;
            if (historyBuffer != null) {
                String[] buffer = historyBuffer.getBuffer();
                for (int i2 = 0; i2 < this.mHistoryBufferArray.size; i2++) {
                    printWriter.print(buffer[i2]);
                }
                printWriter.println("");
            }
        }
        printWriter.println("");
        printWriter.println("ACTIVITY MANAGER NET HISTORY (dumpsys activity net history)");
        HistoryBuffer historyBuffer2 = this.mNetHistoryBufferArray;
        if (historyBuffer2 != null) {
            String[] buffer2 = historyBuffer2.getBuffer();
            for (int i3 = 0; i3 < this.mNetHistoryBufferArray.size; i3++) {
                printWriter.print(buffer2[i3]);
            }
            printWriter.println("");
        }
        printWriter.println("");
        printWriter.println("ACTIVITY MANAGER FILTER HISTORY");
        HistoryBuffer historyBuffer3 = this.mFilterHistoryBufferArray;
        if (historyBuffer3 != null) {
            String[] buffer3 = historyBuffer3.getBuffer();
            for (int i4 = 0; i4 < this.mFilterHistoryBufferArray.size; i4++) {
                printWriter.print(buffer3[i4]);
            }
            printWriter.println("");
        }
    }

    public final List enablePackageList(String str, List list) {
        String str2;
        PkgStatusInfo pkgStatusInfo;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = isChinaModel ? 2048 : getChangedByUserFromReason(str) ? 512 : 1024;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it.next();
            String packageName = appRestrictionInfo.getPackageName();
            int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
            if (isCurrentUser(userId) && isMARsTarget(userId, packageName)) {
                Lock lock = MARsLock;
                synchronized (lock) {
                    try {
                        MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                        str2 = null;
                        if (mARsPackageInfo != null) {
                            pkgStatusInfo = new PkgStatusInfo(mARsPackageInfo.uid, mARsPackageInfo.userId, mARsPackageInfo.name);
                            updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
                        } else {
                            pkgStatusInfo = null;
                        }
                    } finally {
                    }
                }
                if (pkgStatusInfo != null) {
                    boolean enableAction = enableAction(pkgStatusInfo);
                    synchronized (lock) {
                        try {
                            MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                            if (mARsPackageInfo2 != null) {
                                updateInfoToMARsPkgStatus(mARsPackageInfo2, pkgStatusInfo);
                                if (enableAction && levelChange(i, mARsPackageInfo2)) {
                                    if (isChinaModel) {
                                        mARsPackageInfo2.appliedPolicy = this.autoRunPolicy;
                                    } else {
                                        mARsPackageInfo2.appliedPolicy = null;
                                        this.mMARsRestrictedPackages.remove(mARsPackageInfo2.userId, mARsPackageInfo2.name);
                                    }
                                    mARsPackageInfo2.disableReason = FASTableContract.convertDBValueToDisableReason(str);
                                }
                                FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                fASEntityBuilder.strPkgName = mARsPackageInfo2.name;
                                fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo2.uid);
                                boolean z = mARsPackageInfo2.isFASEnabled;
                                fASEntityBuilder.strMode = z ? null : "0";
                                if (!z) {
                                    str2 = FASTableContract.convertFASTypeToReason(mARsPackageInfo2.fasType);
                                }
                                fASEntityBuilder.strFasReason = str2;
                                fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.state);
                                fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo2.resetTime);
                                fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo2.maxLevel);
                                fASEntityBuilder.strDisableType = Integer.toString(mARsPackageInfo2.disableType);
                                fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo2.disableReason);
                                arrayList.add(fASEntityBuilder.build());
                                arrayList2.add(Integer.valueOf(mARsPackageInfo2.uid));
                            }
                        } finally {
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        if (!arrayList.isEmpty()) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
        }
        return arrayList2;
    }

    public final boolean forceKillPackage(String str, Policy policy, int i, int i2) {
        String str2 = "MARs #" + policy.num;
        boolean z = true;
        int i3 = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i4 = 1; i4 <= 16; i4 <<= 1) {
            int i5 = policy.action & i4;
            if (i5 != 1) {
                if (i5 != 2) {
                    if (i5 == 4) {
                        i3 = 500;
                        z3 = true;
                    } else if (i5 == 8) {
                        z4 = true;
                    }
                }
                i3 = 0;
                z3 = false;
            } else {
                z2 = true;
            }
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("forceKillPackage: pkgName: ", str, "MARsPolicyManager");
        }
        closeSocketsForUid(i2);
        if (z2) {
            this.mAm.forceStopPackage(str, i);
        } else {
            z = this.mAm.forceStopPackageForMARsLocked(i3, i, i2, z3, z4, str, str2);
            if (!z && MARsDebugConfig.DEBUG_MARs) {
                Slog.d("MARsPolicyManager", "forceKillPackage: don't force stop package = " + str + ", userId = " + i);
            }
        }
        return z;
    }

    public final boolean forceRunPolicyForRecentKill(String str) {
        String str2 = str;
        StringBuilder sb = new StringBuilder();
        Policy policy = this.autoRunPolicy;
        if (policy == null || !policy.enabled) {
            Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
            return false;
        }
        synchronized (MARsLock) {
            int userId = this.mContext.getUserId();
            if (str2 != null && str2.contains(",")) {
                String[] split = str2.split(",");
                if (split != null && split.length == 2) {
                    String str3 = split[0];
                    r12 = str3 != null ? str3 : null;
                    try {
                        String str4 = split[1];
                        if (str4 != null) {
                            userId = Integer.parseInt(str4);
                        }
                    } catch (NumberFormatException unused) {
                        Slog.e("MARsPolicyManager", "forceRunPolicyForRecentKill parseInt error!");
                    }
                }
                str2 = r12;
            }
            MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str2, userId);
            if (mARsPackageInfo == null) {
                return false;
            }
            String str5 = mARsPackageInfo.name;
            int i = mARsPackageInfo.uid;
            int i2 = mARsPackageInfo.userId;
            int i3 = mARsPackageInfo.fasType;
            int i4 = mARsPackageInfo.state;
            boolean z = mARsPackageInfo.isDisabled;
            int i5 = mARsPackageInfo.disableType;
            int i6 = mARsPackageInfo.maxLevel;
            boolean z2 = mARsPackageInfo.isFASEnabled;
            boolean z3 = mARsPackageInfo.isInRestrictedBucket;
            int i7 = mARsPackageInfo.disableReason;
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                    if (freecessController.mIsFreecessEnable && freecessController.isFreezedPackage(i2, str5)) {
                        freecessController.unFreezePackage(i2, str5, "RecentKill");
                    }
                    if (!forceKillPackage(str5, policy, i2, i)) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    sb.append(" " + i);
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    synchronized (MARsLock) {
                        try {
                            mARsPackageInfo.fasType = i3;
                            mARsPackageInfo.state = i4;
                            mARsPackageInfo.isDisabled = z;
                            mARsPackageInfo.disableType = i5;
                            mARsPackageInfo.curLevel = 3;
                            mARsPackageInfo.maxLevel = i6;
                            mARsPackageInfo.isFASEnabled = z2;
                            mARsPackageInfo.isInRestrictedBucket = z3;
                            mARsPackageInfo.disableReason = i7;
                            if (mARsPackageInfo.appliedPolicy != this.disablePolicy) {
                                mARsPackageInfo.appliedPolicy = policy;
                                levelChange(2, mARsPackageInfo);
                            }
                            if (this.mMARsRestrictedPackages.get(mARsPackageInfo.userId, mARsPackageInfo.name) == null) {
                                this.mMARsRestrictedPackages.put(mARsPackageInfo.name, mARsPackageInfo.userId, mARsPackageInfo);
                            } else if (mARsPackageInfo.appliedPolicy == this.disablePolicy && isDisabledByUser(mARsPackageInfo.disableReason)) {
                                mARsPackageInfo.curLevel = 4;
                                this.mMARsRestrictedPackages.put(mARsPackageInfo.name, mARsPackageInfo.userId, mARsPackageInfo);
                            }
                            addDebugInfoToHistory("EXE", "Recent " + sb.toString());
                            Slog.v("MARsPolicyManager", "Recent_Kill: add mRestrictedPackages " + mARsPackageInfo.name + " policy --" + mARsPackageInfo.appliedPolicy);
                        } finally {
                        }
                    }
                    return true;
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final void forceRunPolicyForSpecificPolicy(ArrayList arrayList, int i) {
        String str;
        int i2 = 1;
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        Policy policy = getPolicy(i);
        if (policy == null || !policy.enabled) {
            Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
            return;
        }
        int i3 = policy.num;
        int i4 = 9;
        int i5 = 2;
        int i6 = (i3 == 1 || i3 == 2) ? 3 : i3 != 4 ? i3 != 6 ? i3 != 8 ? i3 != 9 ? 0 : 8 : 4 : 7 : 1;
        ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE.getUidListUsingAudio();
        synchronized (MARsLock) {
            if (arrayList != null) {
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    String str2 = (String) arrayList.get(i7);
                    int userId = this.mContext.getUserId();
                    if (str2 != null && str2.contains(", ")) {
                        String[] split = str2.split(", ");
                        if (split == null || split.length != 2) {
                            str = null;
                        } else {
                            str = split[0];
                            if (str == null) {
                                str = null;
                            }
                            try {
                                String str3 = split[1];
                                if (str3 != null) {
                                    userId = UserHandle.getUserId(Integer.parseInt(str3));
                                }
                            } catch (NumberFormatException unused) {
                                Slog.e("MARsPolicyManager", "forceRunPolicyForGamePolicy parseInt error!");
                            }
                        }
                        SparseArray sparseArray = (SparseArray) arrayMap.get(str);
                        if (sparseArray == null) {
                            sparseArray = new SparseArray(2);
                            arrayMap.put(str, sparseArray);
                        }
                        sparseArray.put(userId, 1);
                    }
                }
            }
            int i8 = 0;
            while (i8 < this.mMARsTargetPackages.mMap.size()) {
                SparseArray sparseArray2 = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i8);
                int i9 = 0;
                while (i9 < sparseArray2.size()) {
                    MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray2.valueAt(i9);
                    if (i == i4) {
                        if (!isChinaPolicyEnabled() || mARsPackageInfo.isFASEnabled) {
                            String str4 = mARsPackageInfo.name;
                            int i10 = mARsPackageInfo.userId;
                            SparseArray sparseArray3 = (SparseArray) arrayMap.get(str4);
                            if ((sparseArray3 == null ? null : sparseArray3.get(i10)) != null) {
                                if (MARsDebugConfig.DEBUG_ENG) {
                                    Slog.v("MARsPolicyManager", "package " + mARsPackageInfo.name + " in survivePkgs, don't execute this policy " + policy);
                                }
                            }
                        } else if (MARsDebugConfig.DEBUG_ENG) {
                            Slog.v("MARsPolicyManager", "package " + mARsPackageInfo.name + " is not game policy target, don't execute this policy " + policy);
                        }
                        i9 += i2;
                        i5 = 2;
                        i4 = 9;
                    }
                    if (!isInPolicyExceptionList(mARsPackageInfo.userId, policy.num, mARsPackageInfo.name)) {
                        FilterManager filterManager = FilterManager.FilterManagerHolder.INSTANCE;
                        int i11 = policy.num;
                        int i12 = i11 != 0 ? i11 != i2 ? i11 != i5 ? i11 != 4 ? i11 != 6 ? i11 != 8 ? i11 != 9 ? -1 : 9 : 7 : 6 : 4 : 3 : 2 : i2;
                        String str5 = mARsPackageInfo.name;
                        int i13 = mARsPackageInfo.userId;
                        int i14 = mARsPackageInfo.uid;
                        filterManager.getClass();
                        if (FilterManager.filterForSpecificPolicy(i12, i13, i14, str5) > 0) {
                            i2 = 1;
                        } else {
                            PkgStatusInfo pkgStatusInfo = new PkgStatusInfo(mARsPackageInfo.uid, mARsPackageInfo.userId, mARsPackageInfo.name);
                            updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
                            if (i != 6) {
                                arrayList2.add(pkgStatusInfo);
                            } else if (mARsPackageInfo.sbike == 1) {
                                arrayList2.add(pkgStatusInfo);
                            } else {
                                i2 = 1;
                                i9 += i2;
                                i5 = 2;
                                i4 = 9;
                            }
                            i2 = 1;
                            i9 += i2;
                            i5 = 2;
                            i4 = 9;
                        }
                    } else if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.v("MARsPolicyManager", "package " + mARsPackageInfo.name + " inPolicyAllowList, don't execute this policy " + policy);
                    }
                    i9 += i2;
                    i5 = 2;
                    i4 = 9;
                }
                i8 += i2;
                i5 = 2;
                i4 = 9;
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z = false;
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            if (SystemClock.uptimeMillis() - uptimeMillis >= 50) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uptimeMillis = SystemClock.uptimeMillis();
            }
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) arrayList2.get(size);
                    boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                    if (freecessController.mIsFreecessEnable) {
                        if (freecessController.isFreezedPackage(pkgStatusInfo2.userId, pkgStatusInfo2.name)) {
                            freecessController.unFreezePackage(pkgStatusInfo2.userId, pkgStatusInfo2.name, policy.name);
                        }
                    }
                    if (forceKillPackage(pkgStatusInfo2.name, policy, pkgStatusInfo2.userId, pkgStatusInfo2.uid)) {
                        pkgStatusInfo2.currentLevel = i6;
                        sb.append(" " + pkgStatusInfo2.uid);
                        z = true;
                    } else {
                        arrayList2.remove(size);
                    }
                } finally {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
        synchronized (MARsLock) {
            for (int i15 = 0; i15 < arrayList2.size(); i15++) {
                try {
                    PkgStatusInfo pkgStatusInfo3 = (PkgStatusInfo) arrayList2.get(i15);
                    MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, pkgStatusInfo3.name, pkgStatusInfo3.userId);
                    if (mARsPackageInfo2 != null) {
                        updateInfoToMARsPkgStatus(mARsPackageInfo2, pkgStatusInfo3);
                        if (mARsPackageInfo2.appliedPolicy != this.disablePolicy) {
                            mARsPackageInfo2.appliedPolicy = policy;
                        }
                        if (this.mMARsRestrictedPackages.get(mARsPackageInfo2.userId, mARsPackageInfo2.name) == null) {
                            this.mMARsRestrictedPackages.put(mARsPackageInfo2.name, mARsPackageInfo2.userId, mARsPackageInfo2);
                        }
                        if (MARsDebugConfig.DEBUG_ENG) {
                            Slog.v("MARsPolicyManager", "add mRestrictedPackages " + mARsPackageInfo2.name + " policy --" + policy);
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            addDebugInfoToHistory("EXE", policy.name + " " + sb.toString());
        }
    }

    public final int getAutorunForFreezedPackage(int i, String str) {
        if (!isChinaPolicyEnabled()) {
            return -1;
        }
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo == null) {
                    return -1;
                }
                return !mARsPackageInfo.isFASEnabled ? 1 : 0;
            } finally {
            }
        }
    }

    public final void getBatteryStats() {
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d("MARsPolicyManager", "getBatteryStats called!");
        }
        ArrayMap arrayMap = new ArrayMap();
        SemDeviceHealthManager semDeviceHealthManager = new SemDeviceHealthManager();
        long currentTimeMillis = System.currentTimeMillis();
        List<SemBatteryStats> batteryStats = semDeviceHealthManager.getBatteryStats(1, 0L, currentTimeMillis, true);
        if (batteryStats != null) {
            for (SemBatteryStats semBatteryStats : batteryStats) {
                if (semBatteryStats != null) {
                    long endTimestamp = semBatteryStats.getEndTimestamp();
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTimeInMillis(endTimestamp);
                    GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
                    gregorianCalendar2.setTimeInMillis(currentTimeMillis);
                    if (gregorianCalendar.get(1) != gregorianCalendar2.get(1) || gregorianCalendar.get(2) != gregorianCalendar2.get(2) || gregorianCalendar.get(5) != gregorianCalendar2.get(5)) {
                        List<SemBatteryStats.AppDetailUsage> appDetailUsages = semBatteryStats.getAppDetailUsages();
                        if (appDetailUsages != null) {
                            for (SemBatteryStats.AppDetailUsage appDetailUsage : appDetailUsages) {
                                arrayMap.put(Integer.valueOf(appDetailUsage.getUid()), Double.valueOf(appDetailUsage.getPowerUsage()));
                                if (MARsDebugConfig.DEBUG_ENG) {
                                    Slog.d("MARsPolicyManager", semBatteryStats.getEndTimestamp() + " getBatteryStats(D) app : " + appDetailUsage.getUid() + "|" + appDetailUsage.getPowerUsage() + "|" + appDetailUsage.getScreenPowerUsage());
                                }
                            }
                        }
                    }
                }
            }
        }
        synchronized (MARsLock) {
            for (int i = 0; i < this.mMARsTargetPackages.mMap.size(); i++) {
                try {
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
                        if (arrayMap.size() > 0) {
                            Double d = (Double) arrayMap.get(Integer.valueOf(mARsPackageInfo.uid));
                            if (d != null) {
                                mARsPackageInfo.BatteryUsage = d.doubleValue();
                            } else {
                                mARsPackageInfo.BatteryUsage = -1.0d;
                            }
                        } else {
                            mARsPackageInfo.BatteryUsage = -1.0d;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final long getForegroundServiceStartTime(int i) {
        long longValue;
        synchronized (this.mFGServiceStartTimeMap) {
            longValue = ((Long) this.mFGServiceStartTimeMap.getOrDefault(Integer.valueOf(i), 0L)).longValue();
        }
        return longValue;
    }

    public boolean getMARsEnabled() {
        return MARs_ENABLE;
    }

    public final boolean getPackageDisablerEnabled() {
        Policy policy = this.disablePolicy;
        if (policy != null) {
            return policy.enabled;
        }
        return false;
    }

    public final Policy getPolicy(int i) {
        if (i == 1) {
            return this.appLockerPolicy;
        }
        if (i == 2) {
            return this.autoRunPolicy;
        }
        if (i == 4) {
            return this.freecessPolicy;
        }
        if (i == 6) {
            return this.sbikePolicy;
        }
        if (i == 8) {
            return this.disablePolicy;
        }
        if (i == 9) {
            return this.gamePolicy;
        }
        return null;
    }

    public final synchronized boolean getScreenOnState() {
        return this.mScreenOn;
    }

    public final String getSharedUidName(int i, String str) {
        try {
            PackageInfo packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(str, 4202496, i);
            if (packageInfoAsUser != null) {
                return packageInfoAsUser.sharedUserId;
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m("NameNotFoundException occurred for package : ", str, "MARsPolicyManager");
            return null;
        } catch (NullPointerException e) {
            Slog.e("MARsPolicyManager", "NullPointerException occurred in getSharedUidName() " + e.getMessage());
            return null;
        }
    }

    public final boolean isAutoRunBlockedApp(String str, int i) {
        if (!isChinaPolicyEnabled()) {
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.i("MARsPolicyManager", "AR not enabled");
            }
            return false;
        }
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo != null && mARsPackageInfo.isFASEnabled) {
                    if (isInPolicyExceptionList(i, 2, str)) {
                        return false;
                    }
                    FilterManager filterManager = FilterManager.FilterManagerHolder.INSTANCE;
                    int i2 = mARsPackageInfo.uid;
                    filterManager.getClass();
                    if (FilterManager.filterForSpecificPolicy(19, i, i2, str) > 0) {
                        return false;
                    }
                    if (!MARsDebugConfig.DEBUG_ENG) {
                        return true;
                    }
                    Slog.i("MARsPolicyManager", str + " Auto run OFF, userId = " + i);
                    return true;
                }
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.i("MARsPolicyManager", str + " Auto run ON, userId = " + i);
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isAutoRunOn(int i, String str) {
        boolean z;
        synchronized (MARsLock) {
            MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
            z = (mARsPackageInfo == null || mARsPackageInfo.isFASEnabled) ? false : true;
        }
        return z;
    }

    public final boolean isCurrentUser(int i) {
        boolean z;
        boolean z2;
        if (this.mCurrentUserId != 0) {
            z = false;
            z2 = false;
        } else if (!this.mDualAppEnabled || i < 95 || i > 99) {
            synchronized (this.mEnabledProfileUserIds) {
                try {
                    int[] iArr = this.mEnabledProfileUserIds;
                    int length = iArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z2 = false;
                            break;
                        }
                        if (i == iArr[i2]) {
                            z2 = true;
                            break;
                        }
                        i2++;
                    }
                } finally {
                }
            }
            z = false;
        } else {
            z = true;
            z2 = false;
        }
        return this.mCurrentUserId == i || z || z2;
    }

    public final boolean isFirstTimeTriggerAutorun() {
        boolean z;
        if (isChinaPolicyEnabled()) {
            synchronized (this) {
                z = this.mFirstTimeUpdatePackages;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean isForegroundPackage(int i, String str) {
        if (str == null) {
            return false;
        }
        if (!isMARsTarget(i, str)) {
            return true;
        }
        TopPackageFilter topPackageFilter = TopPackageFilter.TopPackageFilterHolder.INSTANCE;
        synchronized (topPackageFilter.mTopPkg) {
            try {
                ArrayList arrayList = (ArrayList) topPackageFilter.mTopPkg.get(Integer.valueOf(i));
                return arrayList != null && arrayList.contains(str);
            } finally {
            }
        }
    }

    public final boolean isInPolicyExceptionList(int i, int i2, String str) {
        MARsPackageInfo mARsPackageInfo;
        int i3 = 0;
        boolean z = false;
        while (true) {
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            MARsVersionManager mARsVersionManager = MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
            if (i3 >= mARsVersionManager.mExcludeTargetList.size()) {
                return false;
            }
            int i4 = ((MARsVersionManager.AdjustTargetExcludePackage) mARsVersionManager.mExcludeTargetList.get(i3)).policyNum;
            int i5 = ((MARsVersionManager.AdjustTargetExcludePackage) mARsVersionManager.mExcludeTargetList.get(i3)).condition;
            String str2 = ((MARsVersionManager.AdjustTargetExcludePackage) mARsVersionManager.mExcludeTargetList.get(i3)).pkgNameMatchType;
            String str3 = ((MARsVersionManager.AdjustTargetExcludePackage) mARsVersionManager.mExcludeTargetList.get(i3)).packageName;
            if (i4 == 0) {
                z = isTargetMatch(str, str2, str3);
            } else if (i4 != 2) {
                if (i4 == 4 && i2 == 4) {
                    z = isTargetMatch(str, str2, str3);
                }
            } else if (i2 == 2 && (mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i)) != null && i5 == (!mARsPackageInfo.isFASEnabled ? 1 : 0)) {
                z = isTargetMatch(mARsPackageInfo.name, str2, str3);
            }
            if (z) {
                return true;
            }
            i3++;
        }
    }

    public final boolean isMARsTarget(int i, String str) {
        boolean z;
        synchronized (MARsLock) {
            z = getMARsPackageInfo(this.mMARsTargetPackages, str, i) != null;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x01c3, code lost:
    
        if ((java.lang.System.currentTimeMillis() - r12.mLastNotiSentTimeForDisabled) > (r12.isTimeChangedForDebug ? r12.mAutoDeepSleepTimeForDebug * 2 : 1296000000)) goto L90;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean levelChange(int r13, com.android.server.am.MARsPackageInfo r14) {
        /*
            Method dump skipped, instructions count: 576
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.levelChange(int, com.android.server.am.MARsPackageInfo):boolean");
    }

    public final void noteAppRestriction(int i, MARsPackageInfo mARsPackageInfo) {
        if (android.app.Flags.appRestrictionsApi()) {
            String str = mARsPackageInfo.name;
            int i2 = mARsPackageInfo.uid;
            if (i == 8) {
                this.mAm.noteAppRestrictionEnabled(str, i2, 70, true, 2, null, 2, 1382400000L);
                return;
            }
            if (i == 64) {
                this.mAm.noteAppRestrictionEnabled(str, i2, 50, true, 4, "settings", 1, 0L);
                return;
            }
            if (i == 128) {
                this.mAm.noteAppRestrictionEnabled(str, i2, 70, true, 4, "settings", 1, 0L);
                return;
            }
            if (i == 256) {
                this.mAm.noteAppRestrictionEnabled(str, i2, 50, false, 4, "settings", 1, 0L);
                return;
            }
            if (i != 1024) {
                return;
            }
            int i3 = mARsPackageInfo.disableReason;
            if (i3 == 0) {
                if (128 == FASTableContract.convertFASReasonToValue(mARsPackageInfo.fasReason)) {
                    this.mAm.noteAppRestrictionEnabled(str, i2, 70, false, 4, "settings", 1, 0L);
                }
            } else if (i3 == 1 || i3 == 16) {
                this.mAm.noteAppRestrictionEnabled(str, i2, 70, false, 3, "usage", 1, 0L);
            }
        }
    }

    public final void onAppUsed(int i, String str, boolean z) {
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo != null) {
                    if (getScreenOnState()) {
                        mARsPackageInfo.lastUsedTime = SystemClock.elapsedRealtime();
                        mARsPackageInfo.disableResetTime = System.currentTimeMillis();
                        LatestProtectedPackageFilter.LatestProtectedPackageFilterHolder.INSTANCE.setLatestProtectedPkg(i, str);
                    }
                    onAppUsed(mARsPackageInfo, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAppUsed(MARsPackageInfo mARsPackageInfo, boolean z) {
        FASEntity fASEntity;
        levelChange(1024, mARsPackageInfo);
        int i = mARsPackageInfo.fasType;
        String str = mARsPackageInfo.name;
        if (i == 128) {
            FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
            fASEntityBuilder.strPkgName = str;
            fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
            fASEntityBuilder.strMode = mARsPackageInfo.isFASEnabled ? "1" : "0";
            fASEntityBuilder.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo.fasType);
            fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
            fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
            fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo.resetTime);
            fASEntityBuilder.strDisableResetTime = Long.toString(mARsPackageInfo.disableResetTime);
            fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
            fASEntity = fASEntityBuilder.build();
        } else {
            fASEntity = null;
        }
        Policy policy = mARsPackageInfo.appliedPolicy;
        cancelPolicy(policy != null ? policy.num : 0, mARsPackageInfo.userId, str, z);
        if (fASEntity != null) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
        }
        if (mARsPackageInfo.nextKillTimeForLongRunningProcess != 0) {
            mARsPackageInfo.nextKillTimeForLongRunningProcess = SystemClock.uptimeMillis() + this.KEEP_NO_FILTER_MIN_DURATION;
        }
    }

    public final void onAppUsedForTimeChanged(long j) {
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.v("MARsPolicyManager", "onAppUsedForTimeChanged -- SystemTime Changed : " + j);
        }
        if (Math.abs(j) < 1800000) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.v("MARsPolicyManager", "SystemTime Changed Less than 30 min, didn't care!!");
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (MARsLock) {
            for (int i = 0; i < this.mMARsTargetPackages.mMap.size(); i++) {
                try {
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
                        if (mARsPackageInfo.resetTime != 0) {
                            mARsPackageInfo.resetTime = j;
                            mARsPackageInfo.disableResetTime = j;
                            FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                            fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                            fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                            fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                            fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo.resetTime);
                            arrayList.add(fASEntityBuilder.build());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        MARsDBHandler.getInstance();
        MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
    }

    public final void onSpecialBindServiceActions(int i, String str, String str2, String str3) {
        if (str == null) {
            return;
        }
        if ("com.google.android.projection.gearhead".equals(str3)) {
            CarConnectedFilter carConnectedFilter = CarConnectedFilter.AndroidAutoFilterHolder.INSTANCE;
            synchronized (carConnectedFilter.userIdPackageListSelfLocked) {
                try {
                    ArrayList arrayList = (ArrayList) carConnectedFilter.userIdPackageListSelfLocked.computeIfAbsent(Integer.valueOf(i), new CarConnectedFilter$$ExternalSyntheticLambda0());
                    if (!arrayList.contains(str)) {
                        arrayList.add(str);
                        if (!carConnectedFilter.logExcludeList.contains(str)) {
                            MARsUtils.addFilterDebugInfoToHistory("FILTER 32", str);
                        }
                    }
                } finally {
                }
            }
        }
        if (str2 == null) {
            return;
        }
        if ("android.intent.action.TTS_SERVICE".equals(str2) && str3 != null) {
            ActiveMusicRecordFilter activeMusicRecordFilter = ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
            Integer valueOf = Integer.valueOf(i);
            synchronized (activeMusicRecordFilter.mTTSPkgs) {
                try {
                    ArrayList arrayList2 = (ArrayList) activeMusicRecordFilter.mTTSPkgs.get(valueOf);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    if (!arrayList2.contains(str3)) {
                        arrayList2.add(str3);
                        activeMusicRecordFilter.mTTSPkgs.put(valueOf, arrayList2);
                    }
                } finally {
                }
            }
        }
        if ("android.net.VpnService".equals(str2)) {
            VPNPackageFilter vPNPackageFilter = VPNPackageFilter.VPNPackageFilterHolder.INSTANCE;
            Integer valueOf2 = Integer.valueOf(i);
            synchronized (vPNPackageFilter.mVpnPkgs) {
                try {
                    ArrayList arrayList3 = (ArrayList) vPNPackageFilter.mVpnPkgs.get(valueOf2);
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                        vPNPackageFilter.mVpnPkgs.put(valueOf2, arrayList3);
                    }
                    arrayList3.add(str);
                } finally {
                }
            }
        }
        if ("android.service.notification.NotificationListenerService".equals(str2)) {
            OngoingNotiPackageFilter ongoingNotiPackageFilter = OngoingNotiPackageFilter.OngoingNotiPackageFilterHolder.INSTANCE;
            Integer valueOf3 = Integer.valueOf(i);
            ArrayList arrayList4 = (ArrayList) ongoingNotiPackageFilter.mNLSPkgMap.get(valueOf3);
            if (arrayList4 == null) {
                arrayList4 = new ArrayList();
            }
            arrayList4.add(str);
            ongoingNotiPackageFilter.mNLSPkgMap.put(valueOf3, arrayList4);
        }
        if (("android.service.wallpaper.WallpaperService".equals(str2) || "com.samsung.android.service.wallpaper.LiveWallpaperService".equals(str2) || "com.samsung.android.service.wallpaper.CoverWallpaperService".equals(str2)) && "android".equals(str3)) {
            WallPaperFilter.WallPaperFilterHolder.INSTANCE.onWallPaperPkgBound(str);
        }
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo != null) {
                    if (!"android.view.InputMethod".equals(str2)) {
                        if ("org.androidprinting.intent.ACTION_GET_PRINT_SERVICE".equals(str2)) {
                        }
                    }
                    onAppUsed(mARsPackageInfo, false);
                }
            } finally {
            }
        }
    }

    public final void postInit() {
        int i;
        if (MARs_ENABLE) {
            return;
        }
        MARsTrigger mARsTrigger = MARsTrigger.MARsTriggerHolder.INSTANCE;
        if (!mARsTrigger.mEmStateReceiverRegistered) {
            mARsTrigger.mContext.registerReceiver(mARsTrigger.mEmergencyStateChangedReceiver, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), 2);
            mARsTrigger.mEmStateReceiverRegistered = true;
        }
        if (!mARsTrigger.mSMDBChangedReceiverRegistered) {
            mARsTrigger.mContext.registerReceiver(mARsTrigger.mSMDBChangedReceiver, BatteryService$$ExternalSyntheticOutline0.m("MARS_REQUEST_PKG_INFO"), 2);
            mARsTrigger.mSMDBChangedReceiverRegistered = true;
        }
        MARsDBManager mARsDBManager = MARsDBManager.MARsDBManagerHolder.INSTANCE;
        mARsDBManager.getClass();
        try {
            Cursor query = mARsDBManager.mCurrentContext.getContentResolver().query(FASTableContract.SMART_MGR_SETTINGS_URI, null, null, null, null);
            if (query != null) {
                query.close();
                MARs_ENABLE = true;
                try {
                    SystemProperties.set("sys.config.mars_version", "9.00");
                } catch (IllegalArgumentException unused) {
                    Slog.e("MARsPolicyManager", "init(), we cannot set system property");
                }
                if ("CHINA".equalsIgnoreCase(SemSystemProperties.getCountryCode())) {
                    isChinaModel = true;
                }
                FreecessController.FASTOLAF_FEATURE_DEALY_SERVICE = isChinaPolicyEnabled();
                FreecessController.FEATURE_SERVICE_GUARD = isChinaPolicyEnabled();
                Slog.v("MARsPolicyManager", "isChinaModel = " + isChinaModel);
                FreecessController.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE = isChinaPolicyEnabled();
                if (!MARsDebugConfig.DEBUG_MID && !MARsDebugConfig.DEBUG_HIGH) {
                    this.mHistoryBufferArray = new HistoryBuffer();
                }
                this.mNetHistoryBufferArray = new HistoryBuffer();
                this.mFilterHistoryBufferArray = new HistoryBuffer();
                setScreenOnState(((PowerManager) this.mContext.getSystemService("power")).isInteractive());
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d("MARsPolicyManager", "mScreenOn : " + getScreenOnState());
                }
                MARsDBHandler.getInstance();
                MARsDBHandler mARsDBHandler = MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                MARsDBHandler.MainHandler mainHandler = mARsDBHandler.mMainHandler;
                if (mainHandler != null) {
                    Message obtainMessage = mainHandler.obtainMessage(5);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("boot", true);
                    obtainMessage.setData(bundle);
                    mARsDBHandler.mMainHandler.sendMessage(obtainMessage);
                }
                FilterManager.FilterManagerHolder.INSTANCE.init(this.mContext);
                MARsTrigger mARsTrigger2 = MARsTrigger.MARsTriggerHolder.INSTANCE;
                if (!mARsTrigger2.mReceiverRegistered) {
                    if (mARsTrigger2.mAlarm == null) {
                        mARsTrigger2.mAlarm = (AlarmManager) mARsTrigger2.mContext.getSystemService("alarm");
                    }
                    IntentFilter m = BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_ON");
                    ActivityManagerService$$ExternalSyntheticOutline0.m(m, UiModeManager.ACTION_ENTER_CAR_MODE, "MARS_REQUEST_POLICY_INFO", "MARS_REQUEST_DB_COMPLETE", "android.net.conn.CONNECTIVITY_CHANGE");
                    m.addAction(Constants.SIM_STATE_CHANGED);
                    m.addAction("com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED");
                    m.setPriority(999);
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mIntentReceiver, m, 2);
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    intentFilter.addAction(UiModeManager.ACTION_EXIT_CAR_MODE);
                    intentFilter.setPriority(1000);
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mTriggerIntentReceiver, intentFilter, 2);
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("FIRST_ALARM_TRIGGER_ACTION");
                    intentFilter2.addAction("com.android.server.am.ACTION_UI_SET_ALWAYS_OPTIMIZING");
                    intentFilter2.addAction("com.android.server.am.ACTION_PACKAGE_NOTUSED_RECENTLY");
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mPolicyIntentReceiver, intentFilter2, 2);
                    IntentFilter intentFilter3 = new IntentFilter();
                    intentFilter3.addAction("android.intent.action.USER_ADDED");
                    intentFilter3.addAction("android.intent.action.USER_STOPPED");
                    intentFilter3.addAction("android.intent.action.USER_STARTED");
                    mARsTrigger2.mContext.registerReceiverForAllUsers(mARsTrigger2.mUserActionReceiver, intentFilter3, null, null, 4);
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mPolicyIntentReceiver, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY"), "android.permission.WRITE_SECURE_SETTINGS", null, 2);
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mAppStartUpIntentReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SHUTDOWN", "android.intent.action.REBOOT"), 4);
                    IntentFilter intentFilter4 = new IntentFilter();
                    intentFilter4.addAction("android.intent.action.USER_SWITCHED");
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mUserIntentReceiver, intentFilter4, 4);
                    IntentFilter intentFilter5 = new IntentFilter();
                    intentFilter5.addAction("android.intent.action.TIME_SET");
                    mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mTimeIntentReceiver, intentFilter5, 4);
                    mARsTrigger2.mReceiverRegistered = true;
                }
                MARsDBHandler.getInstance();
                mARsDBHandler.sendInitSettingMsgToDBHandler();
                MARsDBManager.MARsDBManagerHolder.INSTANCE.registerContentObservers(this.mContext);
                boolean z = EventRecorder.FEATURE_ENABLE;
                EventRecorder eventRecorder = EventRecorder.EventRecorderHolder.INSTANCE;
                eventRecorder.mContext = this.mContext;
                ReentrantLock reentrantLock = EventRecorder.mFileLock;
                reentrantLock.lock();
                try {
                    File file = new File("/data/log/mars/stats.txt");
                    EventRecorder.file = file;
                    EventRecorder.FEATURE_ENABLE = EventRecorder.createNewEmptyFile(file);
                    reentrantLock.unlock();
                    if (EventRecorder.FEATURE_ENABLE) {
                        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(eventRecorder.mContext, (Class<?>) EventRecorder.RemoveOutdatedJobService.class));
                        builder.setPeriodic(43200000L).setRequiresDeviceIdle(true).setRequiresCharging(true);
                        JobScheduler jobScheduler = (JobScheduler) eventRecorder.mContext.getSystemService("jobscheduler");
                        if (jobScheduler != null) {
                            i = jobScheduler.forNamespace("MARsEventRecorderNamespace").schedule(builder.build());
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "RemoveOutdatedJobService schedule result : ", "EventRecorder");
                        } else {
                            i = 0;
                        }
                        EventRecorder.FEATURE_ENABLE = i == 1;
                    }
                    UidStateMgr uidStateMgr = UidStateMgr.UidStateMgrHolder.INSTANCE;
                    ActivityManagerService activityManagerService = this.mAm;
                    uidStateMgr.getClass();
                    boolean z2 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                    for (Map.Entry entry : ((HashMap) MARsPolicyManagerHolder.INSTANCE.mAm.getActiveUids()).entrySet()) {
                        Integer num = (Integer) entry.getKey();
                        uidStateMgr.addToRunningList(num.intValue());
                        if (Boolean.TRUE.equals(entry.getValue())) {
                            ConcurrentList concurrentList = uidStateMgr.mUidIdleList;
                            if (!concurrentList.contains(num)) {
                                concurrentList.add(num);
                            }
                        }
                    }
                    activityManagerService.registerUidObserverForUids(uidStateMgr.mUidObserver, 31, -1, null, null);
                    int i2 = ForegroundServiceMgr.$r8$clinit;
                    ForegroundServiceMgr foregroundServiceMgr = ForegroundServiceMgr.ForegroundServiceMgrHolder.INSTANCE;
                    foregroundServiceMgr.getClass();
                    ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                    foregroundServiceMgr.mActivityManagerInternal = activityManagerInternal;
                    if (activityManagerInternal != null) {
                        activityManagerInternal.addForegroundServiceStateListener(foregroundServiceMgr);
                        foregroundServiceMgr.mActivityManagerInternal.registerProcessObserver(foregroundServiceMgr.mProcessObserver);
                    } else {
                        Slog.i("ForegroundServiceMgr", "AMI is null");
                    }
                    MARsBigData mARsBigData = MARsBigData.MARsBigDataHolder.INSTANCE;
                    Context context = this.mContext;
                    mARsBigData.mContext = context;
                    if (mARsBigData.mHQM == null) {
                        mARsBigData.mHQM = (SemHqmManager) context.getSystemService("HqmManagerService");
                    }
                    mARsBigData.mContext.registerReceiverForAllUsers(mARsBigData.mIntentReceiver, BatteryService$$ExternalSyntheticOutline0.m("com.sec.android.intent.action.HQM_UPDATE_REQ"), "com.samsung.android.permission.HQM_NOTIFICATION_PERMISSION", null, 2);
                    if (!isChinaPolicyEnabled()) {
                        AppStandbyInternal appStandbyInternal = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
                        this.mAppStandby = appStandbyInternal;
                        if (appStandbyInternal != null) {
                            appStandbyInternal.addListener(new MARsAppIdleStateChangeListener());
                            if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d("MARsPolicyManager", "registerAppIdleStateReceiver");
                            }
                        }
                    }
                    MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
                    mARsHandler.sendUpdatePkgMsgToMainHandler(true);
                    MARsHandler.MainHandler mainHandler2 = mARsHandler.mMainHandler;
                    if (mainHandler2 != null) {
                        mainHandler2.removeMessages(4);
                        mARsHandler.mMainHandler.sendMessageDelayed(mARsHandler.mMainHandler.obtainMessage(4), 0L);
                    }
                    mARsHandler.sendUpdateDisableMsgToMainHandler(false);
                    MARsDBHandler.getInstance();
                    MARsDBHandler mARsDBHandler2 = MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                    MARsDBHandler.MainHandler mainHandler3 = mARsDBHandler2.mMainHandler;
                    if (mainHandler3 != null) {
                        mARsDBHandler2.mMainHandler.sendMessageDelayed(mainHandler3.obtainMessage(12), 43200000L);
                    }
                    Slog.d("MARsPolicyManager", "sendUpdateDisableResetTimeToDBHandler enter");
                    try {
                        int parseInt = Integer.parseInt(SystemProperties.get("sys.dualapp.profile_id", "-1"));
                        this.mDualAppUserId = parseInt;
                        if (parseInt >= 95 && parseInt <= 99) {
                            this.mDualAppEnabled = true;
                            mARsHandler.sendInitDisabledMsgToMainHandler(parseInt);
                        }
                    } catch (NumberFormatException unused2) {
                        Slog.e("MARsPolicyManager", "init() get DualAppUserId failed!");
                    }
                    setSubUserIds();
                    return;
                } catch (Throwable th) {
                    EventRecorder.mFileLock.unlock();
                    throw th;
                }
            }
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception occurred in isSMProviderExist : " + e.getMessage());
            e.printStackTrace();
        }
        MARs_ENABLE = false;
    }

    public final List removeRestrictedInfo(String str, List list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it.next();
            String packageName = appRestrictionInfo.getPackageName();
            int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
            if (isCurrentUser(userId)) {
                synchronized (MARsLock) {
                    try {
                        MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                        if (mARsPackageInfo != null) {
                            if (mARsPackageInfo.maxLevel == 4) {
                                mARsPackageInfo.disableReason = FASTableContract.convertDBValueToDisableReason(str);
                                mARsPackageInfo.fasType = FASTableContract.convertFASReasonToValue(str);
                                arrayList.add(appRestrictionInfo);
                            } else if (!mARsPackageInfo.isFASEnabled || isChinaModel) {
                                if (!isChinaModel) {
                                    mARsPackageInfo.fasType = FASTableContract.convertFASReasonToValue(str);
                                    mARsPackageInfo.fasReason = str;
                                }
                                mARsPackageInfo.disableReason = 0;
                                mARsPackageInfo.state = 1;
                                FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                                fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                                fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                fASEntityBuilder.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo.fasType);
                                fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
                                fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                arrayList3.add(fASEntityBuilder.build());
                                arrayList4.add(Integer.valueOf(mARsPackageInfo.uid));
                            } else {
                                mARsPackageInfo.disableReason = FASTableContract.convertDBValueToDisableReason(str);
                                arrayList2.add(appRestrictionInfo);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList4.addAll(enablePackageList(str, arrayList));
        }
        if (!arrayList2.isEmpty()) {
            arrayList4.addAll(awakePackageList(str, arrayList2));
        }
        if (!arrayList3.isEmpty()) {
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList3);
        }
        return arrayList4;
    }

    public final void reportStatusWithMARs(int i, String str, boolean z) {
        synchronized (MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
                if (mARsPackageInfo != null && !z) {
                    mARsPackageInfo.curLevel = 0;
                    if (isChinaModel) {
                        mARsPackageInfo.checkJobRunningCount = 0;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean restrictBySEP(int i, int i2, String str, int i3, boolean z) {
        if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION") || !isCurrentUser(UserHandle.getUserId(i3))) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        SemAppRestrictionManager.RestrictionInfo restrictionInfo = new SemAppRestrictionManager.RestrictionInfo(i, i2, i2 == 1 ? z ? "added_from_user_manual" : "added_from_mars_auto" : i2 == 2 ? z ? "deleted_from_user_manual" : "deleted_from_mars_auto" : "default");
        arrayList.add(new SemAppRestrictionManager.AppRestrictionInfo(str, i3, restrictionInfo));
        return updateRestrictionInfo(restrictionInfo, arrayList);
    }

    public final void restrictJobsByUid(int i, boolean z) {
        if (isChinaModel && this.mContext != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.ACTION_JOB_RESTRICT_UID");
            intent.putExtra("uid", i);
            intent.putExtra("restrict", z);
            Slog.d("MARsPolicyManager", "restrictJobsByUid: u=" + i + ", restrict=" + z);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
        }
    }

    public final synchronized void setIsManualMode(boolean z) {
        try {
            this.mIsManualMode = z;
            if (!z) {
                this.mLastNotiSentTimeForDisabled = 0L;
                MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
                mARsHandler.sendUpdateDisableMsgToMainHandler(false);
                mARsHandler.sendUpdatePkgMsgToMainHandler(true);
            }
            addDebugInfoToHistory("DEV", "ManualMode ".concat(z ? "ON" : "OFF"));
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void setManagedProfileEnabled(int i, boolean z) {
        synchronized (this.mEnabledProfileUserIds) {
            try {
                if (z) {
                    this.mEnabledProfileUserIds = ArrayUtils.appendInt(this.mEnabledProfileUserIds, i);
                } else {
                    this.mEnabledProfileUserIds = ArrayUtils.removeInt(this.mEnabledProfileUserIds, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMaxLevel(int r11, java.lang.String r12) {
        /*
            r10 = this;
            com.android.server.am.MARsPolicyManager$Lock r0 = com.android.server.am.MARsPolicyManager.MARsLock
            monitor-enter(r0)
            com.android.server.am.MARsPkgMap r1 = r10.mMARsTargetPackages     // Catch: java.lang.Throwable -> L41
            android.content.Context r2 = r10.mContext     // Catch: java.lang.Throwable -> L41
            int r2 = r2.getUserId()     // Catch: java.lang.Throwable -> L41
            com.android.server.am.MARsPackageInfo r1 = getMARsPackageInfo(r1, r12, r2)     // Catch: java.lang.Throwable -> L41
            if (r1 == 0) goto L9d
            r2 = 1
            if (r11 == r2) goto L43
            r2 = 2
            r3 = 0
            if (r11 == r2) goto L20
            r12 = 4
            if (r11 == r12) goto L1d
            r2 = r3
            goto L45
        L1d:
            r2 = 8
            goto L45
        L20:
            boolean r11 = r1.isDisabled     // Catch: java.lang.Throwable -> L41
            if (r11 == 0) goto L2e
            int r8 = r1.uid     // Catch: java.lang.Throwable -> L41
            r6 = 2
            r9 = 0
            r5 = 0
            r4 = r10
            r7 = r12
            r4.restrictBySEP(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L41
        L2e:
            com.android.server.am.MARsHandler r11 = com.android.server.am.MARsHandler.MARsHandlerHolder.INSTANCE     // Catch: java.lang.Throwable -> L41
            java.lang.String r12 = r1.name     // Catch: java.lang.Throwable -> L41
            int r4 = r1.userId     // Catch: java.lang.Throwable -> L41
            boolean r5 = r10.ENABLE_RESTRICTED_BUCKET     // Catch: java.lang.Throwable -> L41
            if (r5 == 0) goto L3b
            r5 = 45
            goto L3d
        L3b:
            r5 = 40
        L3d:
            r11.sendCallSetAppStandbyBucketMsgToMainHandler(r4, r5, r12, r3)     // Catch: java.lang.Throwable -> L41
            goto L45
        L41:
            r10 = move-exception
            goto Laa
        L43:
            r2 = 1024(0x400, float:1.435E-42)
        L45:
            if (r2 != 0) goto L49
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return
        L49:
            boolean r10 = r10.levelChange(r2, r1)     // Catch: java.lang.Throwable -> L41
            if (r10 == 0) goto L9d
            com.android.server.am.mars.database.FASEntityBuilder r10 = new com.android.server.am.mars.database.FASEntityBuilder     // Catch: java.lang.Throwable -> L41
            r10.<init>()     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = r1.name     // Catch: java.lang.Throwable -> L41
            r10.strPkgName = r11     // Catch: java.lang.Throwable -> L41
            int r11 = r1.uid     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = java.lang.Integer.toString(r11)     // Catch: java.lang.Throwable -> L41
            r10.strUid = r11     // Catch: java.lang.Throwable -> L41
            boolean r11 = r1.isFASEnabled     // Catch: java.lang.Throwable -> L41
            if (r11 == 0) goto L67
            java.lang.String r12 = "1"
            goto L69
        L67:
            java.lang.String r12 = "0"
        L69:
            r10.strMode = r12     // Catch: java.lang.Throwable -> L41
            if (r11 == 0) goto L70
            java.lang.String r11 = "1"
            goto L72
        L70:
            java.lang.String r11 = "0"
        L72:
            r10.strNew = r11     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = r1.fasReason     // Catch: java.lang.Throwable -> L41
            r10.strFasReason = r11     // Catch: java.lang.Throwable -> L41
            int r11 = r1.state     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = com.android.server.am.mars.database.FASTableContract.convertStateToDBExtrasValue(r11)     // Catch: java.lang.Throwable -> L41
            r10.strExtras = r11     // Catch: java.lang.Throwable -> L41
            long r11 = r1.resetTime     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = java.lang.Long.toString(r11)     // Catch: java.lang.Throwable -> L41
            r10.strResetTime = r11     // Catch: java.lang.Throwable -> L41
            int r11 = r1.maxLevel     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = java.lang.Integer.toString(r11)     // Catch: java.lang.Throwable -> L41
            r10.strLevel = r11     // Catch: java.lang.Throwable -> L41
            int r11 = r1.disableReason     // Catch: java.lang.Throwable -> L41
            java.lang.String r11 = com.android.server.am.mars.database.FASTableContract.convertDisableReasonToDBValue(r11)     // Catch: java.lang.Throwable -> L41
            r10.strDisableReason = r11     // Catch: java.lang.Throwable -> L41
            com.android.server.am.mars.database.FASEntity r10 = r10.build()     // Catch: java.lang.Throwable -> L41
            goto L9e
        L9d:
            r10 = 0
        L9e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            if (r10 == 0) goto La9
            com.android.server.am.mars.database.MARsDBHandler.getInstance()
            com.android.server.am.mars.database.MARsDBHandler r11 = com.android.server.am.mars.database.MARsDBHandler.MARsDBHandlerHolder.INSTANCE
            r11.sendUpdateResetTimeSpecificMsgToDBHandler(r10)
        La9:
            return
        Laa:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.setMaxLevel(int, java.lang.String):void");
    }

    public final void setPackageDisablerEnabled(boolean z) {
        Policy policy = this.disablePolicy;
        if (policy == null || policy.enabled == z) {
            return;
        }
        policy.enabled = z;
        if (MARsDebugConfig.DEBUG_ENG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m("MARsPolicyManager", new StringBuilder("setPackageDisablerEnabled "), this.disablePolicy.enabled);
        }
        addDebugInfoToHistory("DEV", "disabler_switch : " + z);
    }

    public final synchronized void setScreenOnState(boolean z) {
        try {
            this.mScreenOn = z;
            addDebugInfoToHistory("SYS", "SCR ".concat(z ? "ON" : "OFF"));
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void setSubUserIds() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            Iterator it = userManager.getEnabledProfiles().iterator();
            while (it.hasNext()) {
                int identifier = ((UserHandle) it.next()).getIdentifier();
                if (identifier != 0) {
                    synchronized (this.mEnabledProfileUserIds) {
                        this.mEnabledProfileUserIds = ArrayUtils.appendInt(this.mEnabledProfileUserIds, identifier);
                    }
                    MARsHandler.MARsHandlerHolder.INSTANCE.sendInitDisabledMsgToMainHandler(identifier);
                }
            }
        }
    }

    public final boolean skipTriggerAction(MARsPackageInfo mARsPackageInfo) {
        boolean z;
        int i = mARsPackageInfo.maxLevel;
        if (mARsPackageInfo.isSCPMTarget) {
            return false;
        }
        if (((HashSet) this.mCtsGtsList).contains(mARsPackageInfo.name)) {
            return true;
        }
        if ((i > 2 && i == mARsPackageInfo.curLevel) || i > 4) {
            return true;
        }
        int i2 = mARsPackageInfo.uid;
        if (i == 4) {
            if (isChinaPolicyEnabled()) {
                return false;
            }
            return mARsPackageInfo.isDisabled || UidStateMgr.UidStateMgrHolder.INSTANCE.isUidActive(i2);
        }
        if (isFirstTimeTriggerAutorun()) {
            return false;
        }
        if (isChinaPolicyEnabled()) {
            if (!UidStateMgr.UidStateMgrHolder.INSTANCE.isUidRunning(i2)) {
                if (mARsPackageInfo.curLevel > 2 || !mARsPackageInfo.isFASEnabled) {
                    return true;
                }
                mARsPackageInfo.maxLevel = 3;
            }
            return false;
        }
        UidStateMgr uidStateMgr = UidStateMgr.UidStateMgrHolder.INSTANCE;
        if (!uidStateMgr.isUidRunning(i2)) {
            return true;
        }
        if (!uidStateMgr.isUidActive(i2)) {
            return false;
        }
        synchronized (this) {
            z = this.mIsDeviceIdleMode;
        }
        return (z && i < 3 && (mARsPackageInfo.optionFlag & 4) == 0) ? false : true;
    }

    public final void switchPolicies(int i, boolean z) {
        Policy policy;
        if (i == 2) {
            Policy policy2 = this.autoRunPolicy;
            if (policy2 != null && (policy = this.appLockerPolicy) != null) {
                policy.enabled = false;
            }
            if (policy2 != null) {
                policy2.enabled = true;
            }
        } else if (i == 1) {
            Policy policy3 = this.appLockerPolicy;
            if (policy3 != null) {
                policy3.enabled = true;
            }
            Policy policy4 = this.autoRunPolicy;
            if (policy4 != null) {
                policy4.enabled = false;
            }
        }
        if (z) {
            return;
        }
        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
        FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
        if (freecessController.mIsFreecessEnable) {
            freecessController.unFreezePackage("switchPolicy");
        }
        synchronized (MARsLock) {
            MARsPkgMap mARsPkgMap = this.mMARsTargetPackages;
            if (mARsPkgMap != null) {
                mARsPkgMap.mMap.clear();
            }
            MARsPkgMap mARsPkgMap2 = this.mMARsRestrictedPackages;
            if (mARsPkgMap2 != null) {
                mARsPkgMap2.mMap.clear();
            }
        }
        MARsDBHandler.getInstance();
        MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendInitSettingMsgToDBHandler();
    }

    /* JADX WARN: Code restructure failed: missing block: B:342:0x011b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x06b9, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void triggerAction() {
        /*
            Method dump skipped, instructions count: 1726
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.MARsPolicyManager.triggerAction():void");
    }

    public final void updateForegroundPackageToPkgStatus(int i, boolean z) {
        synchronized (this.mFGServiceStartTimeMap) {
            try {
                if (z) {
                    this.mFGServiceStartTimeMap.putIfAbsent(Integer.valueOf(i), Long.valueOf(SystemClock.uptimeMillis()));
                } else {
                    this.mFGServiceStartTimeMap.remove(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateFromMARsMainThread() {
        boolean z;
        Lock lock;
        int i;
        long j;
        int i2 = 0;
        int i3 = 4;
        String[] strArr = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
        String[] strArr2 = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
        synchronized (this) {
            z = this.mIsManualMode;
        }
        if (z) {
            Slog.d("MARsPolicyManager", "Now manual mode is on, we will not update anything!");
            return;
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.v("MARsPolicyManager", "updateFromMARsThread");
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        Lock lock2 = MARsLock;
        synchronized (lock2) {
            int i4 = 0;
            boolean z2 = false;
            while (i4 < this.mMARsTargetPackages.mMap.size()) {
                try {
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(i4);
                    int i5 = i2;
                    while (i5 < sparseArray.size()) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i5);
                        if (isCurrentUser(mARsPackageInfo.userId) && mARsPackageInfo.fasType != 256 && (mARsPackageInfo.maxLevel != i3 || !isDisabledByUser(mARsPackageInfo.disableReason))) {
                            if (!mARsPackageInfo.isDisabled && mARsPackageInfo.isSCPMTarget && mARsPackageInfo.disableReason != 8 && levelChange(8192, mARsPackageInfo)) {
                                mARsPackageInfo.disableReason = 16;
                                if (!z2) {
                                    z2 = true;
                                }
                                strArr[i3] = strArr[i3] + " " + mARsPackageInfo.uid;
                                FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                                fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                                boolean z3 = mARsPackageInfo.isFASEnabled;
                                fASEntityBuilder.strMode = z3 ? "1" : null;
                                fASEntityBuilder.strNew = z3 ? "1" : null;
                                int i6 = mARsPackageInfo.fasType;
                                fASEntityBuilder.strFasReason = i6 == 1 ? FASTableContract.convertFASTypeToReason(i6) : null;
                                fASEntityBuilder.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
                                fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                arrayList.add(fASEntityBuilder.build());
                            }
                            long j2 = this.mLastNotiSentTimeForDisabled;
                            if (j2 == 0 || mARsPackageInfo.state != 8) {
                                lock = lock2;
                            } else {
                                long j3 = currentTimeMillis - j2;
                                if (this.isTimeChangedForDebug) {
                                    lock = lock2;
                                    try {
                                        j = this.mAutoDeepSleepTimeForDebug;
                                    } catch (Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                } else {
                                    lock = lock2;
                                    j = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                                }
                                if (j3 > j) {
                                    FilterManager filterManager = FilterManager.FilterManagerHolder.INSTANCE;
                                    String str = mARsPackageInfo.name;
                                    int i7 = mARsPackageInfo.userId;
                                    int i8 = mARsPackageInfo.uid;
                                    filterManager.getClass();
                                    int filterForSpecificPolicy = FilterManager.filterForSpecificPolicy(7, i7, i8, str);
                                    if (filterForSpecificPolicy != 0) {
                                        strArr2[4] = strArr2[4] + " " + mARsPackageInfo.uid + ":" + filterForSpecificPolicy;
                                    } else if (levelChange(8, mARsPackageInfo)) {
                                        if (!z2) {
                                            z2 = true;
                                        }
                                        strArr[4] = strArr[4] + " " + mARsPackageInfo.uid;
                                        FASEntityBuilder fASEntityBuilder2 = new FASEntityBuilder();
                                        fASEntityBuilder2.strPkgName = mARsPackageInfo.name;
                                        fASEntityBuilder2.strUid = Integer.toString(mARsPackageInfo.uid);
                                        boolean z4 = mARsPackageInfo.isFASEnabled;
                                        fASEntityBuilder2.strMode = z4 ? "1" : null;
                                        fASEntityBuilder2.strNew = z4 ? "1" : null;
                                        int i9 = mARsPackageInfo.fasType;
                                        fASEntityBuilder2.strFasReason = i9 == 1 ? FASTableContract.convertFASTypeToReason(i9) : null;
                                        fASEntityBuilder2.strDisableReason = FASTableContract.convertDisableReasonToDBValue(1);
                                        fASEntityBuilder2.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                        fASEntityBuilder2.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                        arrayList.add(fASEntityBuilder2.build());
                                        if (!this.mIsLastNotiSentTimeForDisabledDismiss) {
                                            this.mIsLastNotiSentTimeForDisabledDismiss = true;
                                            MARsHandler.MARsHandlerHolder.INSTANCE.sendNotifyDeviceCareMsgToMainHandler("deepsleepdismiss", null, null);
                                        }
                                    } else {
                                        strArr2[4] = strArr2[4] + " " + mARsPackageInfo.uid;
                                    }
                                    i = 4;
                                    i5++;
                                    lock2 = lock;
                                    i3 = i;
                                }
                            }
                            if (mARsPackageInfo.maxLevel == 2 && mARsPackageInfo.isFASEnabled && 1 == mARsPackageInfo.fasType && levelChange(1024, mARsPackageInfo) && mARsPackageInfo.fasType == 128) {
                                FASEntityBuilder fASEntityBuilder3 = new FASEntityBuilder();
                                fASEntityBuilder3.strPkgName = mARsPackageInfo.name;
                                fASEntityBuilder3.strUid = Integer.toString(mARsPackageInfo.uid);
                                fASEntityBuilder3.strMode = mARsPackageInfo.isFASEnabled ? "1" : "0";
                                fASEntityBuilder3.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo.fasType);
                                fASEntityBuilder3.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.disableReason);
                                fASEntityBuilder3.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                fASEntityBuilder3.strResetTime = Long.toString(mARsPackageInfo.resetTime);
                                fASEntityBuilder3.strDisableResetTime = Long.toString(mARsPackageInfo.disableResetTime);
                                fASEntityBuilder3.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                arrayList.add(fASEntityBuilder3.build());
                            }
                            i = 4;
                            if (mARsPackageInfo.state < 4 && !isChinaPolicyEnabled()) {
                                UidStateMgr.UidStateMgrHolder.INSTANCE.isUidActive(mARsPackageInfo.uid);
                            }
                            i5++;
                            lock2 = lock;
                            i3 = i;
                        }
                        i = i3;
                        lock = lock2;
                        i5++;
                        lock2 = lock;
                        i3 = i;
                    }
                    i4++;
                    i2 = 0;
                    i3 = i3;
                } catch (Throwable th2) {
                    th = th2;
                    lock = lock2;
                }
            }
            if (z2) {
                addDebugInfoToHistory("LVU", convertLevelChangeInfoToString(strArr, strArr2));
            }
            if (arrayList.isEmpty()) {
                return;
            }
            MARsDBHandler.getInstance();
            MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
        }
    }

    public final void updateResetTime() {
        ArrayList arrayList = new ArrayList();
        synchronized (MARsLock) {
            try {
                for (int size = this.mMARsTargetPackages.mMap.size() - 1; size >= 0; size--) {
                    SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.mMap.valueAt(size);
                    for (int i = 0; i < sparseArray.size(); i++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i);
                        if (mARsPackageInfo != null) {
                            FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                            fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                            fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                            fASEntityBuilder.strResetTime = Long.toString(mARsPackageInfo.resetTime);
                            fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                            fASEntityBuilder.strDisableResetTime = Long.toString(mARsPackageInfo.disableResetTime);
                            arrayList.add(fASEntityBuilder.build());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slog.d("MARsPolicyManager", "updateResetTime");
        if (arrayList.isEmpty()) {
            return;
        }
        MARsDBHandler.getInstance();
        MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
    }

    public final boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List list) {
        ?? r6;
        List list2;
        boolean z;
        Iterator it;
        if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION") || restrictionInfo == null || list == null) {
            return false;
        }
        int type = restrictionInfo.getType();
        int state = restrictionInfo.getState();
        String reason = restrictionInfo.getReason();
        boolean z2 = true;
        if (type == 0) {
            r6 = null;
            if (state != 1) {
                list2 = state == 2 ? enablePackageList(reason, list) : removeRestrictedInfo("default", list);
            } else if ("added_from_anomaly_manual".equals(reason)) {
                list2 = disablePackageListForSpecific(reason, list);
            } else if ("added_from_mars_auto_specific".equals(reason) || "added_from_mars_manual_specific".equals(reason)) {
                IDeviceIdleController asInterface = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
                Iterator it2 = list.iterator();
                boolean z3 = false;
                while (it2.hasNext()) {
                    String packageName = ((SemAppRestrictionManager.AppRestrictionInfo) it2.next()).getPackageName();
                    try {
                        if (asInterface.isPowerSaveWhitelistApp(packageName)) {
                            try {
                                asInterface.removePowerSaveWhitelistApp(packageName);
                                z3 = true;
                            } catch (Exception e) {
                                e = e;
                                z3 = true;
                                Slog.e("MARsPolicyManager", "Doze not available : " + e.toString());
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                if (z3) {
                    MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
                    if (mARsHandler.mMainHandler != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableList("AppRestrictionInfo", list);
                        Message obtainMessage = mARsHandler.mMainHandler.obtainMessage(18);
                        obtainMessage.setData(bundle);
                        mARsHandler.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
                    }
                    list2 = r6;
                } else {
                    list2 = disablePackageListForSpecific(reason, list);
                }
            } else {
                list2 = disablePackageList(reason, list);
            }
            if (list2 != null) {
            }
            return false;
        }
        int i = 4;
        if (type != 1) {
            if (type != 2) {
                if (type == 3) {
                    list2 = state == 1 ? removeRestrictedInfo("deleted_from_user_manual", list) : removeRestrictedInfo("default", list);
                }
                r6 = null;
            } else if (state == 1) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                this.mLastNotiSentTimeForDisabled = 0L;
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo = (SemAppRestrictionManager.AppRestrictionInfo) it3.next();
                    String packageName2 = appRestrictionInfo.getPackageName();
                    int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
                    if (isCurrentUser(userId)) {
                        synchronized (MARsLock) {
                            try {
                                MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, packageName2, userId);
                                if (mARsPackageInfo != null && mARsPackageInfo.maxLevel < 4) {
                                    FilterManager filterManager = FilterManager.FilterManagerHolder.INSTANCE;
                                    String str = mARsPackageInfo.name;
                                    int i2 = mARsPackageInfo.userId;
                                    int i3 = mARsPackageInfo.uid;
                                    filterManager.getClass();
                                    if (FilterManager.filterForSpecificPolicy(7, i2, i3, str) == 0 && levelChange(4, mARsPackageInfo)) {
                                        FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                                        fASEntityBuilder.strPkgName = mARsPackageInfo.name;
                                        fASEntityBuilder.strUid = Integer.toString(mARsPackageInfo.uid);
                                        fASEntityBuilder.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.state);
                                        fASEntityBuilder.strLevel = Integer.toString(mARsPackageInfo.maxLevel);
                                        arrayList.add(fASEntityBuilder.build());
                                        arrayList2.add(Integer.valueOf(mARsPackageInfo.uid));
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
                list2 = arrayList2;
                if (!arrayList.isEmpty()) {
                    MARsDBHandler.getInstance();
                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList);
                    list2 = arrayList2;
                }
            } else {
                if (state == 2) {
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    Iterator it4 = list.iterator();
                    while (it4.hasNext()) {
                        SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo2 = (SemAppRestrictionManager.AppRestrictionInfo) it4.next();
                        String packageName3 = appRestrictionInfo2.getPackageName();
                        int userId2 = UserHandle.getUserId(appRestrictionInfo2.getUid());
                        if (isCurrentUser(userId2)) {
                            synchronized (MARsLock) {
                                try {
                                    MARsPackageInfo mARsPackageInfo2 = getMARsPackageInfo(this.mMARsTargetPackages, packageName3, userId2);
                                    if (mARsPackageInfo2 != null) {
                                        mARsPackageInfo2.disableReason = 8;
                                        mARsPackageInfo2.state = mARsPackageInfo2.isFASEnabled ? 4 : 1;
                                        FASEntityBuilder fASEntityBuilder2 = new FASEntityBuilder();
                                        fASEntityBuilder2.strPkgName = mARsPackageInfo2.name;
                                        fASEntityBuilder2.strUid = Integer.toString(mARsPackageInfo2.uid);
                                        fASEntityBuilder2.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.state);
                                        fASEntityBuilder2.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo2.disableReason);
                                        fASEntityBuilder2.strLevel = Integer.toString(mARsPackageInfo2.maxLevel);
                                        arrayList3.add(fASEntityBuilder2.build());
                                        arrayList4.add(Integer.valueOf(mARsPackageInfo2.uid));
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                    list2 = arrayList4;
                    if (!arrayList3.isEmpty()) {
                        MARsDBHandler.getInstance();
                        MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList3);
                        list2 = arrayList4;
                    }
                }
                r6 = null;
            }
            list2 = r6;
        } else if (state == 1) {
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList();
            int i4 = getChangedByUserFromReason(reason) ? 64 : 2;
            Iterator it5 = list.iterator();
            while (it5.hasNext()) {
                SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo3 = (SemAppRestrictionManager.AppRestrictionInfo) it5.next();
                String packageName4 = appRestrictionInfo3.getPackageName();
                int uid = appRestrictionInfo3.getUid();
                int userId3 = UserHandle.getUserId(uid);
                if (isCurrentUser(userId3)) {
                    boolean containsKey = ((HashMap) this.mAm.getActiveUids()).containsKey(Integer.valueOf(uid)) ^ z2;
                    synchronized (MARsLock) {
                        try {
                            MARsPackageInfo mARsPackageInfo3 = getMARsPackageInfo(this.mMARsTargetPackages, packageName4, userId3);
                            if (mARsPackageInfo3 != null) {
                                int i5 = mARsPackageInfo3.maxLevel;
                                if (i5 == i) {
                                    PkgStatusInfo pkgStatusInfo = new PkgStatusInfo(mARsPackageInfo3.uid, mARsPackageInfo3.userId, mARsPackageInfo3.name);
                                    updateInfoToPkgStatus(mARsPackageInfo3, pkgStatusInfo);
                                    arrayList5.add(pkgStatusInfo);
                                } else {
                                    if (!mARsPackageInfo3.isFASEnabled || i5 < 2) {
                                        levelChange(i4, mARsPackageInfo3);
                                    }
                                    mARsPackageInfo3.fasType = FASTableContract.convertFASReasonToValue(reason);
                                    mARsPackageInfo3.fasReason = reason;
                                    FASEntityBuilder fASEntityBuilder3 = new FASEntityBuilder();
                                    fASEntityBuilder3.strPkgName = mARsPackageInfo3.name;
                                    fASEntityBuilder3.strUid = Integer.toString(mARsPackageInfo3.uid);
                                    fASEntityBuilder3.strMode = mARsPackageInfo3.isFASEnabled ? "1" : "0";
                                    fASEntityBuilder3.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo3.fasType);
                                    fASEntityBuilder3.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo3.state);
                                    it = it5;
                                    fASEntityBuilder3.strResetTime = Long.toString(mARsPackageInfo3.resetTime);
                                    fASEntityBuilder3.strLevel = Integer.toString(mARsPackageInfo3.maxLevel);
                                    arrayList6.add(fASEntityBuilder3.build());
                                    arrayList7.add(Integer.valueOf(mARsPackageInfo3.uid));
                                    if (isChinaPolicyEnabled()) {
                                        if (i4 == 64 && containsKey) {
                                            addRestrictListAvoidAssoicationLaunch(userId3, packageName4, "auto run off", "UserChangeFAS");
                                        }
                                    }
                                }
                            }
                            it = it5;
                        } finally {
                        }
                    }
                    it5 = it;
                    z2 = true;
                    i = 4;
                }
            }
            Iterator it6 = arrayList5.iterator();
            while (it6.hasNext()) {
                PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) it6.next();
                boolean enableAction = enableAction(pkgStatusInfo2);
                synchronized (MARsLock) {
                    try {
                        MARsPackageInfo mARsPackageInfo4 = getMARsPackageInfo(this.mMARsTargetPackages, pkgStatusInfo2.name, pkgStatusInfo2.userId);
                        if (mARsPackageInfo4 != null) {
                            updateInfoToMARsPkgStatus(mARsPackageInfo4, pkgStatusInfo2);
                            if (enableAction && levelChange(i4, mARsPackageInfo4)) {
                                if (mARsPackageInfo4.state > 4) {
                                    mARsPackageInfo4.state = 4;
                                }
                                mARsPackageInfo4.fasType = FASTableContract.convertFASReasonToValue(reason);
                                mARsPackageInfo4.fasReason = reason;
                                mARsPackageInfo4.disableReason = 4;
                                if (isChinaModel) {
                                    mARsPackageInfo4.appliedPolicy = this.autoRunPolicy;
                                } else {
                                    mARsPackageInfo4.appliedPolicy = null;
                                    this.mMARsRestrictedPackages.remove(mARsPackageInfo4.userId, mARsPackageInfo4.name);
                                }
                                FASEntityBuilder fASEntityBuilder4 = new FASEntityBuilder();
                                fASEntityBuilder4.strPkgName = mARsPackageInfo4.name;
                                fASEntityBuilder4.strUid = Integer.toString(mARsPackageInfo4.uid);
                                fASEntityBuilder4.strMode = mARsPackageInfo4.isFASEnabled ? "1" : "0";
                                fASEntityBuilder4.strFasReason = FASTableContract.convertFASTypeToReason(mARsPackageInfo4.fasType);
                                fASEntityBuilder4.strExtras = FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo4.state);
                                fASEntityBuilder4.strResetTime = Long.toString(mARsPackageInfo4.resetTime);
                                fASEntityBuilder4.strLevel = Integer.toString(mARsPackageInfo4.maxLevel);
                                fASEntityBuilder4.strDisableReason = FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo4.disableReason);
                                arrayList6.add(fASEntityBuilder4.build());
                                arrayList7.add(Integer.valueOf(mARsPackageInfo4.uid));
                            }
                        }
                    } finally {
                    }
                }
            }
            if (!arrayList6.isEmpty()) {
                MARsDBHandler.getInstance();
                MARsDBHandler.MARsDBHandlerHolder.INSTANCE.sendUpdateResetTimeMsgToDBHandler(arrayList6);
            }
            list2 = arrayList7;
        } else {
            list2 = state == 2 ? awakePackageList(reason, list) : removeRestrictedInfo("default", list);
        }
        if (list2 != null || list.isEmpty()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int type2 = restrictionInfo.getType();
        if (type2 == 0) {
            sb2.append("DIS");
        } else if (type2 == 1) {
            sb2.append("SLP");
        } else if (type2 == 2) {
            sb2.append("DIS-C");
        } else if (type2 == 3) {
            sb2.append("NSLP");
        }
        int state2 = restrictionInfo.getState();
        if (state2 != 0) {
            z = true;
            if (state2 == 1) {
                sb2.append(" ON");
            } else if (state2 == 2) {
                sb2.append(" OFF");
            }
        } else {
            z = true;
            sb2.append(" NONE");
        }
        sb.append(sb2.toString());
        sb.append(" ");
        sb.append(list2.toString());
        sb.append(" f: ");
        sb.append(list.size() - list2.size());
        addDebugInfoToHistory("SEP", sb.toString());
        if (list2.size() == list.size()) {
            return z;
        }
        return false;
    }
}
