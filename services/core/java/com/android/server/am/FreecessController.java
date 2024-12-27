package com.android.server.am;

import android.app.AppGlobals;
import android.app.IProcessObserver;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.camera2.CameraManager;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.location.ILocationManager;
import android.media.AudioManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.CustomFrequencyManager;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;
import android.util.ArrayMap;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.EventRecorder;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsFreezeStateRecord;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.netlink.FreecessNetlinkMessage;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.input.InputManagerService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.wm.WindowManagerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.com.android.server.am.mars.database.MARsListManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FreecessController {
    public static boolean CACHED_RESTRICTED_BINDER;
    public static int DEFAULT_RECV_BUFSIZE;
    public static boolean FASTOLAF_FEATURE_DEALY_SERVICE;
    public static boolean FASTOLAF_FEATURE_LESSUFZ;
    public static boolean FASTOLAF_FEATURE_MOREFZ;
    public static boolean FASTOLAF_IMPROVE_SPEED;
    public static boolean FASTOLAF_PENDING_JOB;
    public static boolean FASTOLAF_REDUCE_PROTECT_TIME;
    public static boolean FEATURE_SERVICE_GUARD;
    public static final boolean IS_SUPPORT_CALM_MODE;
    public static final int NETLINK_KFREECESS;
    public static final String mCountry;
    public static final String productModel;
    public final Set calmModeFilterList;
    public int cntFailFreeze;
    public int cntFailUnfreeze;
    public final Set fastOlafUfzList;
    public final Set filterList;
    public AlarmManagerService.LocalService mAlarmManagerInternal;
    public ActivityManagerService mAm;
    public FreecessPkgMap mCalmModeAllowListFromGameUI;
    public final Set mCalmModeDefaultAllowList;
    public boolean mCalmModeEnabled;
    public final AnonymousClass1 mCameraDeviceStateCallback;
    public Context mContext;
    public final Set mCtsGtsList;
    public CustomFrequencyManager mCustomFreqManager;
    public final AtomicBoolean mFreecessOlafUpdate;
    public FreecessPolicy mFreecessPolicy;
    public final Set mFreezeExcludePackages;
    public final List mGPSAllowList;
    public boolean mGameModeEnabled;
    public List mGpsDefaultAllowList;
    public InputManagerService mInputManagerService;
    public int mLastTopUid;
    public PowerManagerInternal mLocalPowerManager;
    public ILocationManager mLocationManager;
    public final UidPidMap mMapFrozenUidPidList;
    public boolean mMismatchFlag;
    public INetworkManagementService mNMs;
    public final ArrayList mOLAFAllowListForDebug;
    public final Set mOLAFAllowPackagesCommon;
    public final Set mOLAFAllowPackagesGlobal;
    public final ArrayList mOLAFBlockList;
    public String mOlafTargetPkg;
    public int mOlafTargetUserId;
    public final ArrayList mPendingBlocklistForGPS;
    public final ArrayList mPendingIntents;
    public final ArrayList mPendingIntentsIdle;
    public final ArrayMap mPendingRemoveConnectionMap;
    public final AnonymousClass2 mProcessObserver;
    public long mRecordingEndTime;
    public long mRecordingStartTime;
    public FileDescriptor mSendRecvNetLinkFd;
    public final Set mSsrmAllowPackages;
    public final Set olafUfzList;
    public static final boolean IS_MINIMIZE_OLAF_LOCK =
            SystemProperties.getBoolean("sys.config.mars_min_olaf_lock", true);
    public static boolean IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE = false;
    public final FreecessPkgMap mFreecessManagedPackages = new FreecessPkgMap();
    public final FreecessPkgMap mFreezedPackages = new FreecessPkgMap();
    public final FreecessPkgMap mRestrictedPackages = new FreecessPkgMap();
    public final HashSet mFrozenPidListSelfLocked = new HashSet();
    public final ArrayList mMonitorFreezedList = new ArrayList();
    public boolean mIsFreecessEnable = false;
    public boolean mIsKernelSupportFreecess = false;
    public boolean mPidUnfreezeEnabled = true;
    public boolean mIsQuickFreezeEnabled = true;
    public boolean FREECESS_LRS_ENABLED =
            SystemProperties.getBoolean("sys.config.mars_freecess_lrs", true);
    public final Set mSetUidsNeedToReleaseRestriction = new HashSet();
    public boolean FREECESS_ENHANCEMENT = false;
    public final int mConfigFreecess = Integer.parseInt("4");
    public boolean mIsScreenOnFreecessEnabled = false;
    public boolean mUidIdleCheck = true;
    public boolean mSkipTriggerLcdOnFreeze = false;
    public boolean mScreenOn = true;
    public boolean mCarModeOn = false;
    public boolean mEmergencyModeOn = false;
    public boolean mIsDumpstateWorking = false;
    public boolean mIsSmartSwitchWorking = false;
    public boolean mIsOLAFEnabled = false;
    public final AtomicBoolean mOLAFOn = new AtomicBoolean(false);
    public Long olafUnfreezeEstimatedTime = 0L;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class FreecessControllerHolder {
        public static final FreecessController INSTANCE = new FreecessController();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidPidMap {
        public ArrayMap mUidPidMap;

        public final synchronized ArrayList getPidList(Integer num) {
            ArrayList arrayList;
            arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) this.mUidPidMap.get(num);
            if (arrayList2 != null) {
                arrayList.addAll(arrayList2);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface checkResultCallback {
        void freezeSkipFrozen(String str);
    }

    static {
        productModel = null;
        String str = SystemProperties.get("ro.product.model");
        productModel = str;
        if (str != null) {
            if (!str.startsWith("SM-G98") && !str.startsWith("SM-G99")) {
                str.startsWith("SM-N98");
            }
            if (str.startsWith("SM-G99")
                    || str.startsWith("SM-S91")
                    || str.startsWith("SM-S92")
                    || str.startsWith("SM-S93")) {
                IS_SUPPORT_CALM_MODE = true;
            }
        }
        FASTOLAF_PENDING_JOB = true;
        FASTOLAF_FEATURE_DEALY_SERVICE = true;
        FASTOLAF_FEATURE_MOREFZ = true;
        FASTOLAF_FEATURE_LESSUFZ = true;
        FASTOLAF_REDUCE_PROTECT_TIME = true;
        FASTOLAF_IMPROVE_SPEED = true;
        FEATURE_SERVICE_GUARD = false;
        CACHED_RESTRICTED_BINDER = false;
        mCountry = SemSystemProperties.getCountryCode();
        NETLINK_KFREECESS = 27;
        String str2 = SystemProperties.get("ro.board.platform", "");
        if (str2 != null && str2.startsWith("mt")) {
            NETLINK_KFREECESS = 31;
        }
        DEFAULT_RECV_BUFSIZE =
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS;
    }

    /* JADX WARN: Type inference failed for: r4v19, types: [com.android.server.am.FreecessController$1] */
    /* JADX WARN: Type inference failed for: r4v20, types: [com.android.server.am.FreecessController$2] */
    public FreecessController() {
        MARsListManager mARsListManager = MARsListManager.ListManagerHolder.INSTANCE;
        this.mSsrmAllowPackages = mARsListManager.mSsrmAllowPackages;
        this.mOLAFAllowPackagesCommon = mARsListManager.mOLAFAllowPackages;
        this.mOLAFAllowPackagesGlobal = mARsListManager.mOLAFAllowPackageGlobal;
        this.mFreezeExcludePackages = mARsListManager.mFreezeExcludeList;
        this.mOLAFAllowListForDebug = new ArrayList();
        this.mOLAFBlockList = new ArrayList();
        this.mPendingIntents = mARsListManager.mPendingIntentList;
        this.mPendingIntentsIdle = mARsListManager.mPendingIntentIdleList;
        this.mPendingBlocklistForGPS = mARsListManager.mPendingBlocklistForGPS;
        this.mCtsGtsList = mARsListManager.mCtsGtsList;
        this.mGpsDefaultAllowList = new ArrayList();
        this.filterList = mARsListManager.mFilterList;
        this.olafUfzList = mARsListManager.mOlafUfzList;
        this.fastOlafUfzList = mARsListManager.mFastOlafUfzList;
        this.calmModeFilterList = mARsListManager.mCalmModeFilterList;
        this.mFreecessOlafUpdate = new AtomicBoolean(false);
        this.mOlafTargetUserId = -10;
        this.mGPSAllowList = new ArrayList();
        UidPidMap uidPidMap = new UidPidMap();
        uidPidMap.mUidPidMap = new ArrayMap();
        this.mMapFrozenUidPidList = uidPidMap;
        this.mCameraDeviceStateCallback =
                new CameraManager
                        .SemCameraDeviceStateCallback() { // from class:
                                                          // com.android.server.am.FreecessController.1
                    public final void onCameraDeviceStateChanged(
                            String str, int i, int i2, String str2) {
                        if (i2 == 3 && "com.sec.android.app.camera".equals(str2)) {
                            FreecessController.this.exitOLAF();
                        }
                    }
                };
        this.mCalmModeEnabled = false;
        this.mGameModeEnabled = false;
        this.mCalmModeDefaultAllowList = mARsListManager.mCalmModeDefaultList;
        this.mCalmModeAllowListFromGameUI = null;
        this.mProcessObserver =
                new IProcessObserver
                        .Stub() { // from class: com.android.server.am.FreecessController.2
                    /* JADX WARN: Removed duplicated region for block: B:19:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:67:0x00b3 A[Catch: all -> 0x007c, TryCatch #3 {all -> 0x007c, blocks: (B:72:0x0075, B:68:0x00ba, B:51:0x007e, B:53:0x0086, B:55:0x0088, B:57:0x0094, B:59:0x0096, B:61:0x00a0, B:65:0x00a8, B:67:0x00b3), top: B:71:0x0075 }] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onForegroundActivitiesChanged(int r4, int r5, boolean r6) {
                        /*
                            Method dump skipped, instructions count: 190
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.am.FreecessController.AnonymousClass2.onForegroundActivitiesChanged(int,"
                                    + " int, boolean):void");
                    }

                    public final void onForegroundServicesChanged(int i, int i2, int i3) {
                        synchronized (MARsPolicyManager.MARsLock) {
                            if (i2 < 100000) {
                                try {
                                    if (UserHandle.isApp(i2)) {}
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            FreecessPkgStatus packageStatus =
                                    FreecessController.this.getPackageStatus(i2);
                            if (packageStatus == null) {
                                return;
                            }
                            if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                    packageStatus.userId, packageStatus.name)) {
                                packageStatus.serviceTypes = i3;
                            }
                        }
                    }

                    public final void onProcessDied(int i, int i2) {}

                    public final void onProcessStarted(
                            int i, int i2, int i3, String str, String str2) {}
                };
        this.mRecordingStartTime = 0L;
        this.mRecordingEndTime = 0L;
        this.mSendRecvNetLinkFd = null;
        this.mMismatchFlag = false;
        this.mPendingRemoveConnectionMap = new ArrayMap();
    }

    public static boolean checkFgsPkgSkipToFreeze(FreecessPkgStatus freecessPkgStatus) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        boolean z2 =
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getForegroundServiceStartTime(
                                freecessPkgStatus.uid)
                        != 0;
        boolean z3 = MARsPolicyManager.isChinaModel;
        int i = freecessPkgStatus.uid;
        String str = freecessPkgStatus.name;
        if (z3) {
            if (z2) {
                if (IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE) {
                    if ((freecessPkgStatus.serviceTypes & 1073749537) != 0) {
                        Slog.i(
                                "FreecessController",
                                str
                                        + " has important fgs type according Google fg Api , skip"
                                        + " to freeze it. ");
                    }
                }
                Slog.d("FreecessController", str + "(" + i + ") is important[fg-service]");
                return true;
            }
        } else if (z2) {
            Slog.d("FreecessController", str + "(" + i + ") is important[fg-service]");
            return true;
        }
        return false;
    }

    public static String convertLevelChangeInfoToString(StringBuilder sb, StringBuilder sb2) {
        StringBuilder sb3 = new StringBuilder();
        if (sb.length() > 0) {
            sb3.append("[OLAF] ");
            sb3.append(((Object) sb) + " ");
        }
        if (sb2.length() > 0) {
            sb3.append("[IMP] ");
            sb3.append(((Object) sb2) + " ");
        }
        return sb3.toString();
    }

    public static String formatDateTime(long j) {
        return j == 0
                ? String.format("%23s", "null")
                : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date(j));
    }

    public static boolean freezeTargetProcess(int i, String str) {
        return (str != null
                        && ("android.app.stubs".equals(str)
                                || str.contains("com.android.app.cts.")))
                || UserHandle.isCore(i);
    }

    public static boolean isInFreecessExcludeList(FreecessPkgStatus freecessPkgStatus) {
        if (freecessPkgStatus != null) {
            int i = freecessPkgStatus.targetSdkVersion;
            String str = freecessPkgStatus.name;
            if (i >= 34 && freecessPkgStatus.serviceTypes != 0) {
                if (!IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE) {
                    return true;
                }
                Slog.w(
                        "FreecessController",
                        str + " china model, freeze fgs app exclude important task. ");
                return false;
            }
            if (freecessPkgStatus.isInAllowList) {
                return true;
            }
            int i2 = freecessPkgStatus.uid;
            if (UserHandle.isApp(i2) && freecessPkgStatus.sharedUidName == null) {
                if (freecessPkgStatus.isDoingGC != 0) {
                    Slog.w("FreecessController", str + " is doing GC, skip to freeze it.");
                    return true;
                }
                BlueToothConnectedFilter blueToothConnectedFilter =
                        BlueToothConnectedFilter.BlueToothConnectedFilterHolder.INSTANCE;
                Integer valueOf = Integer.valueOf(i2);
                List list = blueToothConnectedFilter.mBTAllowList;
                if (list == null || !list.contains(valueOf)) {}
            }
            return true;
        }
        return false;
    }

    public static boolean shouldSkipShareUid() {
        return FASTOLAF_FEATURE_MOREFZ && MARsUtils.isChinaPolicyEnabled();
    }

    public static void updateFrozenStatusByFreezeType(
            FreecessPkgStatus freecessPkgStatus, boolean z) {
        int i = freecessPkgStatus.freezedType;
        MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
        if (i == 1) {
            mARsFreezeStateRecord.isOLAFFreezed = z;
            return;
        }
        if (i == 2) {
            mARsFreezeStateRecord.isLcdOffFreezed = z;
        } else {
            if (i == 3) {
                mARsFreezeStateRecord.isLcdOnFreezed = z;
                return;
            }
            mARsFreezeStateRecord.isLcdOffFreezed = z;
            mARsFreezeStateRecord.isLcdOnFreezed = z;
            mARsFreezeStateRecord.isOLAFFreezed = z;
        }
    }

    public boolean canUidBeFrozen(ArrayList arrayList, checkResultCallback checkresultcallback) {
        String str;
        if (arrayList == null || arrayList.isEmpty()) {
            if (checkresultcallback != null) {
                checkresultcallback.freezeSkipFrozen("NP");
            }
            return false;
        }
        int checkProcDiedOrComponentExecutingForFreeze =
                this.mAm.checkProcDiedOrComponentExecutingForFreeze(arrayList, new ArrayList());
        if (checkProcDiedOrComponentExecutingForFreeze == 0) {
            return true;
        }
        switch (checkProcDiedOrComponentExecutingForFreeze) {
            case 1:
                str = "AD";
                break;
            case 2:
                str = "ES";
                break;
            case 3:
                str = "RI";
                break;
            case 4:
                str = "LP";
                break;
            case 5:
                str = "SP";
                break;
            case 6:
                str = "FG";
                break;
            case 7:
                str = "RL";
                break;
            case 8:
                str = "BTOP";
                break;
            default:
                str = "UNKNOWN";
                break;
        }
        if (checkresultcallback != null) {
            checkresultcallback.freezeSkipFrozen(str);
        }
        return false;
    }

    public final void cancelCalmMode() {
        int i;
        StringBuilder sb = new StringBuilder();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray clone = this.mFreezedPackages.mUidMap.clone();
                for (int i2 = 0; i2 < clone.size(); i2++) {
                    FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) clone.valueAt(i2);
                    MARsPolicyManager mARsPolicyManager =
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    boolean isAutoRunOn =
                            mARsPolicyManager.isAutoRunOn(
                                    freecessPkgStatus.userId, freecessPkgStatus.name);
                    if (mARsPolicyManager.isMARsTarget(
                                    freecessPkgStatus.userId, freecessPkgStatus.name)
                            && !isAutoRunOn) {
                        if (freecessPkgStatus.isFreezedByCalm) {
                            freecessPkgStatus.isFreezedByCalm = false;
                        }
                    }
                    unFreezeForCalmMode(freecessPkgStatus, "CalmMode");
                    sb.append(freecessPkgStatus.uid + " ");
                }
            } finally {
            }
        }
        FreecessHandler.MainHandler mainHandler =
                FreecessHandler.FreecessHandlerHolder.INSTANCE.mMainHandler;
        if (mainHandler != null) {
            mainHandler.removeMessages(22);
        }
        this.mCalmModeEnabled = false;
        MARsPolicyManager mARsPolicyManager2 = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager2.getClass();
        StringBuilder sb2 = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        synchronized (MARsPolicyManager.MARsLock) {
            for (int i3 = 0; i3 < mARsPolicyManager2.mMARsRestrictedPackages.mMap.size(); i3++) {
                try {
                    SparseArray sparseArray =
                            (SparseArray)
                                    mARsPolicyManager2.mMARsRestrictedPackages.mMap.valueAt(i3);
                    for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i4);
                        mARsPackageInfo.curLevel = 0;
                        MARsPolicyManager.Policy policy = mARsPackageInfo.appliedPolicy;
                        if (policy != null && policy.num == 9) {
                            mARsPackageInfo.appliedPolicy = null;
                        }
                        if (mARsPackageInfo.appliedPolicy == null) {
                            arrayList.add(mARsPackageInfo);
                        }
                    }
                } finally {
                }
            }
            for (i = 0; i < arrayList.size(); i++) {
                MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i);
                mARsPolicyManager2.mMARsRestrictedPackages.remove(
                        mARsPackageInfo2.userId, mARsPackageInfo2.name);
                sb2.append(mARsPackageInfo2.uid + " ");
            }
        }
        mARsPolicyManager2.addDebugInfoToHistory(
                "Calm", "CancelPolicy-" + sb2.toString() + " UFZ-" + sb.toString());
    }

    public final void closeSocketsForFreecessFirewallChain() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs =
                    INetworkManagementService.Stub.asInterface(
                            ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        try {
            if (iNetworkManagementService != null) {
                try {
                    iNetworkManagementService.closeSocketsForFreecess(7, "fw_oem_deny_1");
                } catch (Exception e) {
                    Slog.e(
                            "FreecessController",
                            "Error occured while closeSocketsForFreecessFirewallChain: " + e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deleteRemovedPackage(int i, String str) {
        int userId = UserHandle.getUserId(i);
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                if (this.mFreezedPackages.getByUserId(userId, str) != null) {
                    unFreezePackage(userId, str, "PkgRemoved");
                }
                this.mFreecessManagedPackages.remove(i, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpFreecess(PrintWriter printWriter) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        boolean isChinaPolicyEnabled = MARsPolicyManager.isChinaPolicyEnabled();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                printWriter.println("FREECESS STATE");
                printWriter.print("-FreecessEnabled : ");
                printWriter.print(this.mIsFreecessEnable ? "Y" : "N");
                printWriter.print("-KernelSupport : ");
                printWriter.print(this.mIsKernelSupportFreecess ? "Y" : "N");
                printWriter.print(" -FreecessEnhancementEnabled : ");
                printWriter.print(this.FREECESS_ENHANCEMENT ? "Y" : "N");
                printWriter.print(" -EnhancedBackgroundRestricted : (Binder:");
                printWriter.print(CACHED_RESTRICTED_BINDER ? "Y," : "N,");
                printWriter.print("Intent:");
                printWriter.print("N)");
                printWriter.print(" -ChinaPolicyEnabled : ");
                printWriter.println(isChinaPolicyEnabled ? "Y" : "N");
                printWriter.print(" -Mimimize_OLAF_lock : ");
                printWriter.print(IS_MINIMIZE_OLAF_LOCK ? "Y" : "N");
                printWriter.print(" -QuickFreezeEnabled : ");
                printWriter.print(this.mIsQuickFreezeEnabled ? "Y" : "N");
                printWriter.print(" -PidUfzEnabled : ");
                printWriter.print(this.mPidUnfreezeEnabled ? "Y" : "N");
                printWriter.print(" -RestrictJobDuringOlaf : ");
                printWriter.print(FASTOLAF_PENDING_JOB ? "Y" : "N");
                printWriter.print(" -DealyServiceDuringOlaf : ");
                printWriter.print(FASTOLAF_FEATURE_DEALY_SERVICE ? "Y" : "N");
                printWriter.print(" -FASTOLAF_FEATURE_MOREFZ : ");
                printWriter.print(FASTOLAF_FEATURE_MOREFZ ? "Y" : "N");
                printWriter.print(" -FASTOLAF_FEATURE_LESSUFZ : ");
                printWriter.print(FASTOLAF_FEATURE_LESSUFZ ? "Y" : "N");
                printWriter.print(" -CALM : ");
                printWriter.print(this.mCalmModeEnabled ? "Y" : "N");
                printWriter.print(" -GAME : ");
                printWriter.print(this.mGameModeEnabled ? "Y" : "N");
                printWriter.print(" -fastolaf_protect_freeze : ");
                printWriter.print(FASTOLAF_REDUCE_PROTECT_TIME ? "Y" : "N");
                printWriter.print(" -FASTOLAF_IMPROVE_SPEED : ");
                printWriter.print(FASTOLAF_IMPROVE_SPEED ? "Y" : "N");
                printWriter.print(" -LRsEnabled : ");
                printWriter.print(this.FREECESS_LRS_ENABLED ? "Y" : "N");
                printWriter.print(" -AUFAllowBucketSize : ");
                printWriter.print(MARsPolicyManager.AUFAllowBucketSize);
                printWriter.print(" -cntFail(FZ/UFZ/UFZ_P) : ");
                printWriter.println("" + this.cntFailFreeze + "/" + this.cntFailUnfreeze);
                printWriter.println("");
                printWriter.println("ACTIVITY MANAGER Freecess (dumpsys activity freecess)");
                printWriter.print("mFreecessManagedPackages --- size ");
                printWriter.println(this.mFreecessManagedPackages.totalSize());
                StringBuilder sb = new StringBuilder();
                for (MARsFreezeStateRecord.UnfreezeReasonType unfreezeReasonType :
                        MARsFreezeStateRecord.UnfreezeReasonType.values()) {
                    sb.append(
                            String.format(
                                    "T%-3d|", Integer.valueOf(unfreezeReasonType.getTypeNum())));
                }
                printWriter.println(
                        String.format(
                                "%-9s%-6s%-5s%-4s%-2s|%s %s",
                                "Uid", "Share", "Idle", "Top", "S", sb.toString(), "Pkg"));
                for (int i = 0; i < this.mFreecessManagedPackages.mUserIdMap.size(); i++) {
                    SparseArray sparseArray =
                            (SparseArray) this.mFreecessManagedPackages.mUserIdMap.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        FreecessPkgStatus freecessPkgStatus =
                                (FreecessPkgStatus) sparseArray.valueAt(i2);
                        if (freecessPkgStatus != null) {
                            Integer valueOf = Integer.valueOf(freecessPkgStatus.uid);
                            String str =
                                    freecessPkgStatus.sharedUidName != null
                                            ? "Y"
                                            : PackageManagerShellCommandDataLoader.STDIN_PATH;
                            UidStateMgr uidStateMgr = UidStateMgr.UidStateMgrHolder.INSTANCE;
                            printWriter.println(
                                    String.format(
                                            "%-9d%3s%5s%5s%3s |%s %s",
                                            valueOf,
                                            str,
                                            uidStateMgr.isUidIdle(freecessPkgStatus.uid)
                                                    ? "Y"
                                                    : PackageManagerShellCommandDataLoader
                                                            .STDIN_PATH,
                                            uidStateMgr.mTopUidList.contains(
                                                            Integer.valueOf(freecessPkgStatus.uid))
                                                    ? "Y"
                                                    : PackageManagerShellCommandDataLoader
                                                            .STDIN_PATH,
                                            freecessPkgStatus.freezedRecord.isFrozen
                                                    ? "F"
                                                    : uidStateMgr.isUidRunning(
                                                                    freecessPkgStatus.uid)
                                                            ? "R"
                                                            : PackageManagerShellCommandDataLoader
                                                                    .STDIN_PATH,
                                            freecessPkgStatus.freezedRecord.dumpUfzContent(),
                                            freecessPkgStatus.name));
                        }
                    }
                }
                for (int i3 = 0; i3 < this.mFreecessManagedPackages.mUserIdMap.size(); i3++) {
                    SparseArray sparseArray2 =
                            (SparseArray) this.mFreecessManagedPackages.mUserIdMap.valueAt(i3);
                    for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
                        FreecessPkgStatus freecessPkgStatus2 =
                                (FreecessPkgStatus) sparseArray2.valueAt(i4);
                        if (!freecessPkgStatus2.freezedRecord.mUnfreezeAbuseDetections.isEmpty()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(freecessPkgStatus2.name);
                            sb2.append("since ");
                            sb2.append(
                                    formatDateTime(
                                            freecessPkgStatus2.freezedRecord.initialStateTime));
                            sb2.append(" has abuse unfreeze ");
                            sb2.append(
                                    freecessPkgStatus2.freezedRecord.mUnfreezeAbuseDetections
                                            .size());
                            sb2.append(" ---last abnormal time: ");
                            sb2.append(
                                    formatDateTime(
                                            ((Long)
                                                            freecessPkgStatus2.freezedRecord
                                                                    .mUnfreezeAbuseDetections
                                                                    .valueAt(r6.size() - 1))
                                                    .longValue()));
                            printWriter.println(sb2.toString());
                        }
                    }
                }
                printWriter.print("mFreezedPackages --- size ");
                printWriter.println(this.mFreezedPackages.totalSize());
                for (int i5 = 0; i5 < this.mFreezedPackages.mUserIdMap.size(); i5++) {
                    SparseArray sparseArray3 =
                            (SparseArray) this.mFreezedPackages.mUserIdMap.valueAt(i5);
                    for (int i6 = 0; i6 < sparseArray3.size(); i6++) {
                        FreecessPkgStatus freecessPkgStatus3 =
                                (FreecessPkgStatus) sparseArray3.valueAt(i6);
                        printWriter.print("-FZT ");
                        printWriter.print(
                                formatDateTime(freecessPkgStatus3.freezedRecord.freezedTime));
                        printWriter.print("-UFZT ");
                        printWriter.print(
                                formatDateTime(freecessPkgStatus3.freezedRecord.unfreezedTime));
                        printWriter.print("-UFZR ");
                        printWriter.print(
                                String.format(
                                        "%10s", freecessPkgStatus3.freezedRecord.unfreezedReason));
                        printWriter.print("-UserId ");
                        printWriter.print(
                                String.format("%3d", Integer.valueOf(freecessPkgStatus3.userId)));
                        printWriter.print("-Pkg ");
                        printWriter.print(freecessPkgStatus3.name);
                        printWriter.println("");
                    }
                }
                printWriter.print("mMonitorFreezedList --- size ");
                printWriter.println(this.mMonitorFreezedList.size());
                for (int i7 = 0; i7 < this.mMonitorFreezedList.size(); i7++) {
                    printWriter.print(String.format("%d ", this.mMonitorFreezedList.get(i7)));
                }
                printWriter.println("");
            } catch (Throwable th) {
                throw th;
            }
        }
        FreecessHandler.MainHandler mainHandler =
                FreecessHandler.FreecessHandlerHolder.INSTANCE.mMainHandler;
        if (mainHandler != null) {
            mainHandler.dump(new PrintWriterPrinter(printWriter), "Freecess");
        }
        ArrayMap targetUidNameMap = getTargetUidNameMap();
        boolean z2 = EventRecorder.FEATURE_ENABLE;
        EventRecorder.EventRecorderHolder.INSTANCE.reportUnfreezeCount(
                targetUidNameMap, printWriter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:316:0x06f1 A[LOOP:4: B:314:0x06e9->B:316:0x06f1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0737 A[LOOP:5: B:319:0x0731->B:321:0x0737, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:326:0x07b8  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x08bf  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x08d7  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0997 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpFreecessCommand(java.io.PrintWriter r27, java.lang.String[] r28) {
        /*
            Method dump skipped, instructions count: 2456
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.FreecessController.dumpFreecessCommand(java.io.PrintWriter,"
                    + " java.lang.String[]):void");
    }

    public final void enterOLAF(int i, int i2, String str) {
        if (!this.mIsOLAFEnabled
                || this.mOLAFOn.get()
                || !this.mScreenOn
                || this.mCarModeOn
                || this.mEmergencyModeOn
                || this.mCalmModeEnabled
                || "com.google.android.youtube".equals(str)
                || "com.jingdong.app.mall".equals(str)) {
            return;
        }
        if (i2 != 0) {
            if (i2 < 100) {
                return;
            }
            if (i2 > 5000) {
                i2 = 5000;
            }
            long uptimeMillis = SystemClock.uptimeMillis() + i2;
            synchronized (this.olafUnfreezeEstimatedTime) {
                try {
                    if (uptimeMillis <= this.olafUnfreezeEstimatedTime.longValue()) {
                        return;
                    } else {
                        this.olafUnfreezeEstimatedTime = Long.valueOf(uptimeMillis);
                    }
                } finally {
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Enter OLAF! pkgName: ", str, ", uid: ", ", mills: "),
                    i2,
                    "FreecessController");
        }
        this.mOLAFOn.set(true);
        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendOLAFMsg(i, str, true);
    }

    public final void exitOLAF() {
        if (MARsDebugConfig.DEBUG_OLAF) {
            Slog.d("FreecessController", "Exit OLAF!");
        }
        if (this.mIsOLAFEnabled && this.mOLAFOn.get()) {
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            if (freecessHandler.mMainHandler != null) {
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d("FreecessHandler", "removeOLAFTimeOutMsg....");
                }
                freecessHandler.mMainHandler.removeMessages(10);
            }
            freecessHandler.sendOLAFMsg(-1, null, false);
        }
    }

    public boolean freezeAllProcesses(
            int i, ArrayList arrayList, checkResultCallback checkresultcallback) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (!requestProcessFrozen(i, num, true)) {
                this.cntFailFreeze++;
                StringBuilder sb = new StringBuilder("cntFailFreeze=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        this.cntFailFreeze, i, ", uid=", ", fpid=", sb);
                sb.append(num);
                sb.append(")");
                String sb2 = sb.toString();
                boolean z = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addDebugInfoToHistory(
                        "DEV", sb2);
                return false;
            }
            ProcessRecord processRecordFromPidLocked =
                    this.mAm.getProcessRecordFromPidLocked(num.intValue());
            if (processRecordFromPidLocked != null) {
                processRecordFromPidLocked.frozenMARs = true;
                SystemClock.uptimeMillis();
            }
            UidPidMap uidPidMap = this.mMapFrozenUidPidList;
            Integer valueOf = Integer.valueOf(i);
            synchronized (uidPidMap) {
                ((ArrayList)
                                uidPidMap.mUidPidMap.computeIfAbsent(
                                        valueOf,
                                        new FreecessController$$ExternalSyntheticLambda6(1)))
                        .add(num);
            }
            synchronized (this.mFrozenPidListSelfLocked) {
                this.mFrozenPidListSelfLocked.add(num);
            }
        }
        return true;
    }

    public final boolean freezeForCalmMode(FreecessPkgStatus freecessPkgStatus, String str) {
        boolean z = false;
        if (freecessPkgStatus.freezedRecord.isFrozen
                || isExceptionListAppForCalmMode(freecessPkgStatus)) {
            return false;
        }
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        String str2 = freecessPkgStatus.name;
        int i = freecessPkgStatus.userId;
        if (mARsPolicyManager.isInPolicyExceptionList(i, 4, str2)) {
            return false;
        }
        int i2 = freecessPkgStatus.uid;
        if (UserHandle.isApp(i2) && freecessPkgStatus.sharedUidName == null) {
            FilterManager.FilterManagerHolder.INSTANCE.getClass();
            if (FilterManager.filterForSpecificPolicy(18, i, i2, str2) > 0) {
                return false;
            }
            z = true;
            if (freezeUid(
                    freecessPkgStatus,
                    str,
                    3,
                    new FreecessController$$ExternalSyntheticLambda1(2, freecessPkgStatus))) {
                freecessPkgStatus.isFreezedByCalm = true;
                mARsPolicyManager.addDebugInfoToHistory("FZ", str + " " + str2 + " " + i2);
            }
        }
        return z;
    }

    public final void freezeOLAFPackage(FreecessPkgStatus freecessPkgStatus, StringBuilder sb) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (!MARsPolicyManager.isChinaModel && freecessPkgStatus.freezedRecord.isFrozen) {
            if (MARsDebugConfig.DEBUG_OLAF) {
                Slog.d(
                        "FreecessController",
                        "Skip olaf fz for <"
                                + freecessPkgStatus.name
                                + ", "
                                + Integer.valueOf(freecessPkgStatus.uid)
                                + ">: fzed="
                                + Boolean.valueOf(freecessPkgStatus.freezedRecord.isFrozen));
                return;
            }
            return;
        }
        if (freezeUid(
                freecessPkgStatus, "OLAF", 1, new FreecessController$$ExternalSyntheticLambda4())) {
            sb.append(" " + freecessPkgStatus.uid);
            if (this.mIsQuickFreezeEnabled || freecessPkgStatus.freezedState != 2) {
                return;
            }
            freecessPkgStatus.freezedState = 1;
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            String str = freecessPkgStatus.name;
            freecessHandler.removeBgTriggerMsgByObj(2, str);
            freecessHandler.removeBgTriggerMsgByObj(3, str);
            freecessHandler.removeBgTriggerMsgByObj(4, str);
            freecessHandler.removeBgTriggerMsgByObj(28, str);
        }
    }

    public final boolean freezePackage(final int i) {
        boolean z = false;
        if (!this.mIsFreecessEnable) {
            return false;
        }
        InputManagerService inputManagerService = this.mInputManagerService;
        boolean isUidTouched =
                inputManagerService != null ? inputManagerService.mNative.isUidTouched(i) : false;
        final StringBuilder sb = new StringBuilder();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                final FreecessPkgStatus packageStatus = getPackageStatus(i);
                if (packageStatus != null && !packageStatus.freezedRecord.isFrozen) {
                    if (isUidTouched) {
                        Slog.d(
                                "FreecessController",
                                packageStatus.name + " has Touch Event, skip to freeze");
                        packageStatus.freezedState = 1;
                        sb.append(" " + i + ":s0");
                    } else if (isInFreecessExcludeList(packageStatus)) {
                        sb.append(" " + i + ":s1");
                    } else {
                        z =
                                freezeUid(
                                        packageStatus,
                                        "LEV",
                                        2,
                                        new checkResultCallback() { // from class:
                                                                    // com.android.server.am.FreecessController$$ExternalSyntheticLambda5
                                            @Override // com.android.server.am.FreecessController.checkResultCallback
                                            public final void freezeSkipFrozen(String str) {
                                                StringBuilder sb2 =
                                                        new StringBuilder("skip lev freeze ");
                                                FreecessPkgStatus freecessPkgStatus =
                                                        FreecessPkgStatus.this;
                                                sb2.append(freecessPkgStatus.name);
                                                sb2.append("(");
                                                sb2.append(freecessPkgStatus.uid);
                                                sb2.append(")");
                                                sb2.append(str);
                                                Slog.d("FreecessController", sb2.toString());
                                                sb.append(" " + i + ":" + str);
                                            }
                                        });
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean freezeUid(
            FreecessPkgStatus freecessPkgStatus,
            String str,
            int i,
            checkResultCallback checkresultcallback) {
        synchronized (freecessPkgStatus.protectionElapsedRealtime) {
            if (SystemClock.elapsedRealtime()
                    < freecessPkgStatus.protectionElapsedRealtime.longValue()) {
                StringBuilder sb = new StringBuilder();
                sb.append(freecessPkgStatus.name);
                sb.append("(");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb, freecessPkgStatus.uid, ") is protected", "FreecessController");
                if (i == 3) {
                    freecessPkgStatus.freezedState = 1;
                    if (this.mScreenOn) {
                        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendUidLcdOnQuickFzMsg(
                                freecessPkgStatus.uid, freecessPkgStatus.name, str, true);
                    }
                }
                checkresultcallback.freezeSkipFrozen("Protect");
                return false;
            }
            ArrayList allRunningPackagePids = getAllRunningPackagePids(freecessPkgStatus.uid, true);
            if (!canUidBeFrozen(allRunningPackagePids, checkresultcallback)) {
                return false;
            }
            if (MARsDebugConfig.DEBUG_FREECESS) {
                Trace.traceBegin(64L, "freezeUid " + freecessPkgStatus.uid + " type : " + i);
            }
            boolean freezeAllProcesses =
                    freezeAllProcesses(
                            freecessPkgStatus.uid, allRunningPackagePids, checkresultcallback);
            if (freezeAllProcesses) {
                onUidFrozen(freecessPkgStatus, i, str);
            } else {
                if (unfreezeAllProcesses(allRunningPackagePids, freecessPkgStatus.uid)) {
                    freecessPkgStatus.freezedRecord.updateUnfreezeState(
                            System.currentTimeMillis(), "FZ-fail");
                    MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
                    mARsFreezeStateRecord.isFrozen = false;
                    mARsFreezeStateRecord.isOLAFFreezed = false;
                    mARsFreezeStateRecord.isLcdOnFreezed = false;
                    mARsFreezeStateRecord.isLcdOffFreezed = false;
                }
                StringBuilder sb2 = new StringBuilder("UFZ : ");
                sb2.append(freecessPkgStatus.name);
                sb2.append("(");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb2,
                        freecessPkgStatus.userId,
                        ") because failed to freeze",
                        "FreecessController");
            }
            if (MARsDebugConfig.DEBUG_FREECESS) {
                Trace.traceEnd(64L);
            }
            return freezeAllProcesses;
        }
    }

    public ArrayList getAllRunningPackagePids(int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "/sys/fs/cgroup/uid_");
            String[] list = new File(m).list();
            if (list != null && list.length > 0) {
                for (String str : list) {
                    if (str.startsWith("pid_")) {
                        try {
                            FileReader fileReader = new FileReader(m + "/" + str + "/cgroup.procs");
                            try {
                                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        arrayList.add(Integer.valueOf(readLine));
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
                                fileReader.close();
                            } catch (Throwable th3) {
                                try {
                                    fileReader.close();
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                }
                                throw th3;
                            }
                        } catch (Exception e) {
                            StringBuilder m2 =
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            i, "Error occurred when reading ", " - ");
                            m2.append(e.toString());
                            Slog.e("FreecessController", m2.toString());
                        }
                    }
                }
            }
        } else {
            arrayList.addAll(this.mMapFrozenUidPidList.getPidList(Integer.valueOf(i)));
        }
        return arrayList;
    }

    public final FreecessPkgStatus getFrozenPackageStatus(int i) {
        FreecessPkgStatus freecessPkgStatus;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgMap freecessPkgMap = this.mFreezedPackages;
                freecessPkgStatus =
                        (freecessPkgMap == null || freecessPkgMap.mUidMap.size() <= 0)
                                ? null
                                : (FreecessPkgStatus) this.mFreezedPackages.mUidMap.get(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return freecessPkgStatus;
    }

    public final FreecessPkgStatus getFrozenPackageStatus(int i, String str) {
        FreecessPkgStatus freecessPkgStatus;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgMap freecessPkgMap = this.mFreezedPackages;
                freecessPkgStatus =
                        (freecessPkgMap == null || freecessPkgMap.mUserIdMap.size() <= 0)
                                ? null
                                : (FreecessPkgStatus) this.mFreezedPackages.getByUserId(i, str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return freecessPkgStatus;
    }

    public final FreecessPkgStatus getPackageStatus(int i) {
        FreecessPkgStatus freecessPkgStatus;
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            try {
                FreecessPkgMap freecessPkgMap = this.mFreecessManagedPackages;
                if (freecessPkgMap == null || freecessPkgMap.mUidMap.size() <= 0) {
                    freecessPkgStatus = null;
                } else {
                    freecessPkgStatus =
                            (FreecessPkgStatus) this.mFreecessManagedPackages.mUidMap.get(i);
                    if (freecessPkgStatus != null && freecessPkgStatus.uid == i) {
                        return freecessPkgStatus;
                    }
                }
                if (freecessPkgStatus != null) {
                    return freecessPkgStatus;
                }
                try {
                    String[] packagesForUid = AppGlobals.getPackageManager().getPackagesForUid(i);
                    if (packagesForUid == null || packagesForUid.length != 1) {
                        return freecessPkgStatus;
                    }
                    String str = packagesForUid[0];
                    if (str != null && str.contains(".cts")) {
                        return null;
                    }
                    PackageInfo packageInfo =
                            AppGlobals.getPackageManager()
                                    .getPackageInfo(str, 4111, UserHandle.getUserId(i));
                    FreecessPkgStatus freecessPkgStatus2 =
                            new FreecessPkgStatus(
                                    str,
                                    i,
                                    UserHandle.getUserId(i),
                                    AppGlobals.getPackageManager().getTargetSdkVersion(str));
                    if (packageInfo != null) {
                        try {
                            String str2 = packageInfo.sharedUserId;
                            if (str2 != null) {
                                freecessPkgStatus2.sharedUidName = str2;
                            }
                        } catch (RemoteException e) {
                            e = e;
                            freecessPkgStatus = freecessPkgStatus2;
                            Slog.e(
                                    "FreecessController",
                                    "getPackageStatus() failed to create ps " + e);
                            return freecessPkgStatus;
                        }
                    }
                    synchronized (lock) {
                        this.mFreecessManagedPackages.put(str, i, freecessPkgStatus2);
                        updateAllowListForFreecess(freecessPkgStatus2);
                    }
                    return freecessPkgStatus2;
                } catch (RemoteException e2) {
                    e = e2;
                }
            } finally {
            }
        }
    }

    public final FreecessPkgStatus getPackageStatus(int i, String str) {
        ApplicationInfo applicationInfo;
        FreecessPkgStatus freecessPkgStatus = null;
        if (str != null && str.contains(".cts")) {
            return null;
        }
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            try {
                if (this.mFreecessManagedPackages.mUserIdMap.size() > 0
                        && (freecessPkgStatus =
                                        (FreecessPkgStatus)
                                                this.mFreecessManagedPackages.getByUserId(i, str))
                                != null) {
                    return freecessPkgStatus;
                }
                if (freecessPkgStatus != null) {
                    return freecessPkgStatus;
                }
                try {
                    PackageInfo packageInfo =
                            AppGlobals.getPackageManager().getPackageInfo(str, 4111, i);
                    if (packageInfo == null
                            || (applicationInfo = packageInfo.applicationInfo) == null) {
                        return freecessPkgStatus;
                    }
                    int i2 = applicationInfo.uid;
                    FreecessPkgStatus freecessPkgStatus2 =
                            new FreecessPkgStatus(
                                    str,
                                    i2,
                                    i,
                                    AppGlobals.getPackageManager().getTargetSdkVersion(str));
                    try {
                        String str2 = packageInfo.sharedUserId;
                        if (str2 != null) {
                            freecessPkgStatus2.sharedUidName = str2;
                        }
                        synchronized (lock) {
                            this.mFreecessManagedPackages.put(str, i2, freecessPkgStatus2);
                            updateAllowListForFreecess(freecessPkgStatus2);
                        }
                        return freecessPkgStatus2;
                    } catch (RemoteException e) {
                        e = e;
                        freecessPkgStatus = freecessPkgStatus2;
                        Slog.e("FreecessController", "getPackageStatus() failed to create ps " + e);
                        return freecessPkgStatus;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                }
            } finally {
            }
        }
    }

    public final ArrayMap getTargetUidNameMap() {
        ArrayMap arrayMap = new ArrayMap();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray sparseArray = this.mFreecessManagedPackages.mUidMap;
                for (int i = 0; i < sparseArray.size(); i++) {
                    FreecessPkgStatus freecessPkgStatus =
                            (FreecessPkgStatus) sparseArray.valueAt(i);
                    freecessPkgStatus.getClass();
                    boolean z = MARsPolicyManager.MARs_ENABLE;
                    if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                            freecessPkgStatus.userId, freecessPkgStatus.name)) {
                        arrayMap.put(
                                Integer.valueOf(freecessPkgStatus.uid), freecessPkgStatus.name);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    public void init(
            Context context,
            ActivityManagerService activityManagerService,
            HandlerThread handlerThread,
            HandlerThread handlerThread2) {
        this.mContext = context;
        this.mAm = activityManagerService;
        setScreenOnFreecessEnabled(false);
        this.FREECESS_ENHANCEMENT = false;
        this.mIsOLAFEnabled = false;
        this.mIsFreecessEnable = false;
        reportSocketResult(true);
        FreecessHandler.FreecessHandlerHolder.INSTANCE.init(
                this.mContext, handlerThread, handlerThread2);
    }

    public final boolean isExceptionListAppForCalmMode(FreecessPkgStatus freecessPkgStatus) {
        if (freecessPkgStatus == null) {
            return true;
        }
        HashSet hashSet = (HashSet) this.mSsrmAllowPackages;
        String str = freecessPkgStatus.name;
        if (hashSet.contains(str)
                || isInOlafAllowList(str)
                || ((HashSet) this.mCalmModeDefaultAllowList).contains(str)) {
            return true;
        }
        FreecessPkgMap freecessPkgMap = this.mCalmModeAllowListFromGameUI;
        int i = freecessPkgStatus.userId;
        return freecessPkgMap.getByUserId(i, str) != null
                || 2
                        == LatestProtectedPackageFilter.LatestProtectedPackageFilterHolder.INSTANCE
                                .filter(i, freecessPkgStatus.uid, 18, str);
    }

    public final boolean isFreezedPackage(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i);
                return frozenPackageStatus != null && frozenPackageStatus.freezedRecord.isFrozen;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isFreezedPackage(int i, String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i, str);
                return frozenPackageStatus != null && frozenPackageStatus.freezedRecord.isFrozen;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isInOlafAllowList(String str) {
        return (FASTOLAF_FEATURE_MOREFZ && MARsUtils.isChinaPolicyEnabled())
                ? ((HashSet) this.mOLAFAllowPackagesCommon).contains(str)
                : ((HashSet) this.mOLAFAllowPackagesGlobal).contains(str)
                        || ((HashSet) this.mOLAFAllowPackagesCommon).contains(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00b5, code lost:

       if (r4 > 1) goto L69;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void kernelFreecessReport(
            com.android.server.am.mars.netlink.StructFreeCessMsg r10) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.FreecessController.kernelFreecessReport(com.android.server.am.mars.netlink.StructFreeCessMsg):void");
    }

    public final boolean killPkgForCalmMode(int i, String str) {
        FreecessPkgStatus frozenPackageStatus;
        boolean unFreezeForCalmMode;
        if (!this.mCalmModeEnabled) {
            return false;
        }
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            try {
                frozenPackageStatus = getFrozenPackageStatus(i);
                unFreezeForCalmMode =
                        frozenPackageStatus != null
                                ? unFreezeForCalmMode(frozenPackageStatus, str)
                                : false;
            } finally {
            }
        }
        if (!unFreezeForCalmMode
                || frozenPackageStatus == null
                || isExceptionListAppForCalmMode(frozenPackageStatus)) {
            return true;
        }
        String str2 = frozenPackageStatus.name;
        int i2 = frozenPackageStatus.uid;
        int i3 = frozenPackageStatus.userId;
        this.mAm.killProcessForMARs(i2, i3, str2, "CalmMode", 0, true);
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        synchronized (lock) {
            try {
                MARsPackageInfo mARsPackageInfo =
                        MARsPolicyManager.getMARsPackageInfo(
                                mARsPolicyManager.mMARsTargetPackages, str2, i3);
                if (mARsPackageInfo != null) {
                    mARsPackageInfo.appliedPolicy = mARsPolicyManager.gamePolicy;
                    mARsPackageInfo.curLevel = 8;
                    if (mARsPolicyManager.mMARsRestrictedPackages.get(
                                    mARsPackageInfo.userId, mARsPackageInfo.name)
                            == null) {
                        mARsPolicyManager.mMARsRestrictedPackages.put(
                                mARsPackageInfo.name, mARsPackageInfo.userId, mARsPackageInfo);
                    }
                }
            } finally {
            }
        }
        mARsPolicyManager.addDebugInfoToHistory("EXE", "CalmMode " + str2);
        return true;
    }

    public final void lcdOnFreezePackage(int i, String str) {
        boolean z;
        String str2;
        if (this.mIsScreenOnFreecessEnabled && this.mIsFreecessEnable) {
            StringBuilder sb = new StringBuilder();
            WindowManagerService windowManagerService = this.mAm.mWindowManager;
            boolean z2 =
                    windowManagerService != null
                            && windowManagerService.hasFloatingOrOnScreenWindow(i);
            InputManagerService inputManagerService = this.mInputManagerService;
            boolean isUidTouched =
                    inputManagerService != null
                            ? inputManagerService.mNative.isUidTouched(i)
                            : false;
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    FreecessPkgStatus packageStatus =
                            getPackageStatus(UserHandle.getUserId(i), str);
                    if (packageStatus == null) {
                        sb.append("s0");
                        return;
                    }
                    if (checkFgsPkgSkipToFreeze(packageStatus)) {
                        Slog.d(
                                "FreecessController",
                                str + " has important Fg-service , skip to freeze");
                        packageStatus.freezedState = 1;
                        sb.append("s1");
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && this.mIsQuickFreezeEnabled) {
                        MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                        if (mARsFreezeStateRecord.isFrozen && mARsFreezeStateRecord.isOLAFFreezed) {
                            overrideOlafForBgFreeze(packageStatus);
                            Slog.d(
                                    "FreecessController",
                                    str + " is OLAF FZed, override it with bg fz.");
                            return;
                        }
                    }
                    if (!z && packageStatus.freezedRecord.isFrozen) {
                        sb.append("s2");
                        return;
                    }
                    if (!z) {
                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                packageStatus.userId, packageStatus.name)) {
                            Slog.d(
                                    "FreecessController",
                                    str + " is not MARs target, skip to freeze");
                            packageStatus.freezedState = 1;
                            sb.append("s3");
                            z = true;
                        }
                    }
                    if (!z && "com.samsung.android.spay".equals(packageStatus.name)) {
                        packageStatus.freezedState = 1;
                        sb.append("s4");
                        return;
                    }
                    if (!z && z2) {
                        Slog.d(
                                "FreecessController",
                                str + " has floating or onScreen window, skip to freeze");
                        packageStatus.freezedState = 1;
                        sb.append("s5");
                        z = true;
                    }
                    if (!z && isUidTouched) {
                        Slog.d("FreecessController", str + " has Touch Event, skip to freeze");
                        packageStatus.freezedState = 1;
                        sb.append("s6");
                        z = true;
                    }
                    if (!(!z
                                    ? freezeUid(
                                            packageStatus,
                                            "Bg",
                                            3,
                                            new FreecessController$$ExternalSyntheticLambda1(1, sb))
                                    : false)
                            && !"NP".equals(sb.toString())
                            && !"NProc".equals(sb.toString())) {
                        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendUidLcdOnQuickFzMsg(
                                packageStatus.uid,
                                packageStatus.name,
                                "Bg-refreeze",
                                this.mIsQuickFreezeEnabled || this.mUidIdleCheck);
                    }
                    MARsPolicyManager mARsPolicyManager =
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    StringBuilder sb2 = new StringBuilder("Bg ");
                    sb2.append(str);
                    sb2.append(" ");
                    sb2.append(i);
                    if (sb.length() > 0) {
                        str2 = " [IMP]" + sb.toString();
                    } else {
                        str2 = "";
                    }
                    sb2.append(str2);
                    mARsPolicyManager.addDebugInfoToHistory("FRZ", sb2.toString());
                } finally {
                }
            }
        }
    }

    public final void lcdOnFreezedStateChange(int i, int i2, String str, String str2) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(i2, str2);
                if (packageStatus == null) {
                    return;
                }
                int i3 = packageStatus.freezedState;
                if (i == 2 && i3 == 1) {
                    packageStatus.freezedState = 2;
                }
                stepLcdOnFreezedState(i3, packageStatus.freezedState, str, packageStatus);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void makePkgIdleIfNeeded(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(i);
                if (packageStatus == null) {
                    return;
                }
                String str = packageStatus.name;
                int i2 = packageStatus.userId;
                try {
                    if (this.mAm == null || UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(i)) {
                        return;
                    }
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d(
                                "FreecessController",
                                "Try to make pkg idle: <" + str + ", " + i + ">.");
                    }
                    this.mAm.makePackageIdle(str, i2);
                } catch (Exception e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            e, "makePkgIdleIfNeeded Exception : ", "FreecessController");
                }
            } finally {
            }
        }
    }

    public final boolean matchFreezeState(int i, String str) {
        boolean z;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i, str);
            z = false;
            if (frozenPackageStatus != null) {
                MARsFreezeStateRecord mARsFreezeStateRecord = frozenPackageStatus.freezedRecord;
                if (true == mARsFreezeStateRecord.isFrozen
                        && !mARsFreezeStateRecord.isOLAFFreezed) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final void onUidFrozen(FreecessPkgStatus freecessPkgStatus, int i, String str) {
        freecessPkgStatus.freezedRecord.isFrozen = true;
        freecessPkgStatus.freezedType = i;
        updateFrozenStatusByFreezeType(freecessPkgStatus, true);
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        if (mARsPolicyManager.isMARsTarget(freecessPkgStatus.userId, freecessPkgStatus.name)) {
            boolean z2 = EventRecorder.FEATURE_ENABLE;
            EventRecorder.EventRecorderHolder.INSTANCE.addFreezeEvent(
                    Integer.valueOf(freecessPkgStatus.uid), System.currentTimeMillis(), str);
        }
        if (this.mFreezedPackages.mUidMap.get(freecessPkgStatus.uid) == null) {
            this.mFreezedPackages.put(
                    freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus);
        }
        FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
        int i2 = freecessPkgStatus.uid;
        if (freecessHandler.mHandleAmsLockHandler != null) {
            Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i2, "uid");
            Message obtainMessage = freecessHandler.mHandleAmsLockHandler.obtainMessage(32);
            obtainMessage.setData(m);
            freecessHandler.mHandleAmsLockHandler.sendMessage(obtainMessage);
        }
        if (i == 1) {
            return;
        }
        MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
        mARsFreezeStateRecord.freezedTime = System.currentTimeMillis();
        int typeNum = MARsFreezeStateRecord.FreezeReasonType.FREEZE_TOTAL.getTypeNum();
        int[] iArr = mARsFreezeStateRecord.mFreezeCounts;
        iArr[typeNum] = iArr[typeNum] + 1;
        boolean z3 =
                (MARsPolicyManager.isChinaPolicyEnabled()
                                && mARsPolicyManager.getAutorunForFreezedPackage(
                                                UserHandle.getUserId(freecessPkgStatus.uid),
                                                freecessPkgStatus.name)
                                        == 1)
                        || !(MARsPolicyManager.isChinaPolicyEnabled() || this.FREECESS_ENHANCEMENT);
        boolean z4 =
                this.FREECESS_ENHANCEMENT
                        && ((MARsPolicyManager.isChinaPolicyEnabled()
                                        && mARsPolicyManager.getAutorunForFreezedPackage(
                                                        UserHandle.getUserId(freecessPkgStatus.uid),
                                                        freecessPkgStatus.name)
                                                == 0)
                                || !MARsPolicyManager.isChinaPolicyEnabled());
        freecessPkgStatus.monitorPacketFlag = z3;
        freecessPkgStatus.restrictNetworkFlag = z4;
        freecessHandler.sendUpdateBTMsg(26, freecessPkgStatus.uid);
        if (z4) {
            updateFreezedUidFirewall(freecessPkgStatus.uid, false);
            mARsPolicyManager.closeSocketsForUid(freecessPkgStatus.uid);
        }
        int i3 = freecessPkgStatus.uid;
        synchronized (this.mSetUidsNeedToReleaseRestriction) {
            try {
                if (!((HashSet) this.mSetUidsNeedToReleaseRestriction)
                        .contains(Integer.valueOf(i3))) {
                    ((HashSet) this.mSetUidsNeedToReleaseRestriction).add(Integer.valueOf(i3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        freecessHandler.sendOnFreezeStateChanged(i3, true);
        freecessHandler.sendFreecessSettlementMsg(
                freecessPkgStatus.userId,
                freecessPkgStatus.uid,
                true,
                freecessPkgStatus.name,
                z3 ? 1 : -1,
                i == 3,
                false,
                1);
        ArrayList pidList =
                this.mMapFrozenUidPidList.getPidList(Integer.valueOf(freecessPkgStatus.uid));
        if (!pidList.isEmpty()) {
            freecessHandler.sendReportToBroadcastQueueMsg(pidList);
            Iterator it = pidList.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                ProcessRecord processRecordFromPidLocked =
                        this.mAm.getProcessRecordFromPidLocked(num.intValue());
                this.mAm.mOomAdjuster.mCachedAppOptimizer.sendMsgForFileCacheReclamation(
                        num.intValue(), freecessPkgStatus.name);
                if (this.mAm.mBooted && processRecordFromPidLocked != null) {
                    PerProcessNandswap.getInstance()
                            .onProcessFrozen(
                                    processRecordFromPidLocked.uid,
                                    processRecordFromPidLocked.mPid,
                                    processRecordFromPidLocked.processName,
                                    processRecordFromPidLocked.mState.mCurAdj,
                                    processRecordFromPidLocked
                                            .mWindowProcessController
                                            .mHasActivities);
                }
            }
        }
        FreecessHandler freecessHandler2 = FreecessHandler.FreecessHandlerHolder.INSTANCE;
        String str2 = freecessPkgStatus.name;
        int i4 = freecessPkgStatus.uid;
        FreecessHandler.MainHandler mainHandler = freecessHandler2.mMainHandler;
        if (mainHandler != null && !mainHandler.hasMessages(15, str2)) {
            Message obtainMessage2 = freecessHandler2.mMainHandler.obtainMessage(15, str2);
            Bundle bundle = new Bundle();
            bundle.putString("packageName", str2);
            bundle.putInt("uid", i4);
            obtainMessage2.setData(bundle);
            freecessHandler2.mMainHandler.sendMessageDelayed(obtainMessage2, 1000L);
        }
        StringBuilder sb = new StringBuilder("FZ : ");
        sb.append(freecessPkgStatus.name);
        sb.append("(");
        sb.append(freecessPkgStatus.uid);
        sb.append(") ");
        sb.append(pidList);
        sb.append(" reason: ");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "FreecessController");
    }

    public final void onUidUnfrozen(
            FreecessPkgStatus freecessPkgStatus, ArrayList arrayList, String str) {
        freecessPkgStatus.freezedRecord.isFrozen = false;
        freecessPkgStatus.freezedState = 1;
        freecessPkgStatus.freezedType = 0;
        updateFrozenStatusByFreezeType(freecessPkgStatus, false);
        if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name)
                != null) {
            this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
        }
        boolean z = MARsPolicyManager.MARs_ENABLE;
        if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                freecessPkgStatus.userId, freecessPkgStatus.name)) {
            boolean z2 = EventRecorder.FEATURE_ENABLE;
            EventRecorder.EventRecorderHolder.INSTANCE.addUnFreezeEvent(
                    Integer.valueOf(freecessPkgStatus.uid),
                    System.currentTimeMillis(),
                    str,
                    freecessPkgStatus.latestProcState);
        }
        if (FEATURE_SERVICE_GUARD) {
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            int i = freecessPkgStatus.uid;
            FreecessHandler.HandleAmsLockHandler handleAmsLockHandler =
                    freecessHandler.mHandleAmsLockHandler;
            if (handleAmsLockHandler != null) {
                Message obtainMessage = handleAmsLockHandler.obtainMessage(34);
                Bundle bundle = new Bundle();
                bundle.putInt("uid", i);
                obtainMessage.setData(bundle);
                freecessHandler.mHandleAmsLockHandler.sendMessage(obtainMessage);
            }
        }
        if (str.startsWith("OLAF:")) {
            return;
        }
        freecessPkgStatus.freezedRecord.updateUnfreezeState(System.currentTimeMillis(), str);
        boolean z3 = freecessPkgStatus.monitorPacketFlag;
        boolean z4 = freecessPkgStatus.restrictNetworkFlag;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            FreecessHandler freecessHandler2 = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            freecessHandler2.sendUpdateBTMsg(27, freecessPkgStatus.uid);
            if (z4) {
                updateFreezedUidFirewall(freecessPkgStatus.uid, true);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int i2 = freecessPkgStatus.uid;
            synchronized (this.mSetUidsNeedToReleaseRestriction) {
                try {
                    if (((HashSet) this.mSetUidsNeedToReleaseRestriction)
                            .contains(Integer.valueOf(i2))) {
                        ((HashSet) this.mSetUidsNeedToReleaseRestriction)
                                .remove(Integer.valueOf(i2));
                        freecessHandler2.sendOnFreezeStateChanged(i2, false);
                    }
                } finally {
                }
            }
            freecessHandler2.sendFreecessSettlementMsg(
                    freecessPkgStatus.userId,
                    freecessPkgStatus.uid,
                    false,
                    freecessPkgStatus.name,
                    z3 ? 0 : -1,
                    false,
                    true,
                    0);
            freecessPkgStatus.monitorPacketFlag = false;
            freecessPkgStatus.restrictNetworkFlag = false;
            if (!arrayList.isEmpty()) {
                freecessHandler2.sendReportToBroadcastQueueMsg(arrayList);
            }
            String str2 = freecessPkgStatus.name;
            FreecessHandler.MainHandler mainHandler = freecessHandler2.mMainHandler;
            if (mainHandler != null && mainHandler.hasMessages(15, str2)) {
                freecessHandler2.mMainHandler.removeMessages(15, str2);
            }
            StringBuilder sb = new StringBuilder("UFZ : ");
            sb.append(freecessPkgStatus.name);
            sb.append("(");
            sb.append(freecessPkgStatus.uid);
            sb.append(") ");
            sb.append(arrayList);
            sb.append(" reason: ");
            BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "FreecessController");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void overrideOlafForBgFreeze(FreecessPkgStatus freecessPkgStatus) {
        if (!FASTOLAF_FEATURE_MOREFZ || freecessPkgStatus.sharedUidName == null) {
            MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
            if (mARsFreezeStateRecord.isOLAFFreezed) {
                mARsFreezeStateRecord.isOLAFFreezed = false;
            }
            onUidFrozen(freecessPkgStatus, 3, "Bg");
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addDebugInfoToHistory(
                    "OVR", "Olaf2Bg " + freecessPkgStatus.name + " " + freecessPkgStatus.uid);
        }
    }

    public final void protectFreezePackage(int i, String str, long j) {
        FreecessPkgStatus packageStatus;
        boolean z;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                packageStatus = getPackageStatus(i);
                if (packageStatus != null) {
                    packageStatus.updateProtectionTime(j);
                    if (packageStatus.freezedRecord.isFrozen) {
                        z = unFreezeAction(packageStatus, str, true);
                    }
                }
                z = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.reportStatusWithMARs(
                    packageStatus.userId, packageStatus.name, false);
        }
    }

    public final boolean protectFreezePackage(int i, String str, long j, String str2) {
        boolean z;
        boolean z2;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(i, str);
                if (packageStatus != null) {
                    packageStatus.updateProtectionTime(j);
                    if (packageStatus.freezedRecord.isFrozen) {
                        z2 = true;
                        z = unFreezeAction(packageStatus, str2, true);
                    }
                }
                z = false;
                z2 = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.reportStatusWithMARs(i, str, false);
        }
        return z2;
    }

    public final void readSysfs() {
        StringBuilder sb;
        String readLine;
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                BufferedReader bufferedReader4 =
                        new BufferedReader(new FileReader("/data/system/ssrm_local_freecess"));
                while (true) {
                    try {
                        readLine = bufferedReader4.readLine();
                        if (readLine == null) {
                            try {
                                break;
                            } catch (IOException e) {
                                e = e;
                                sb = new StringBuilder("e = ");
                                sb.append(e.getMessage());
                                Slog.e("FreecessController", sb.toString());
                                return;
                            }
                        }
                        if (readLine.contains(":")) {
                            r5 = false;
                            boolean z = false;
                            r5 = false;
                            boolean z2 = false;
                            r5 = false;
                            boolean z3 = false;
                            r5 = false;
                            boolean z4 = false;
                            String[] split = readLine.split(":", 0);
                            if (split.length > 2) {
                                String str = split[0];
                                String substring =
                                        str != null
                                                ? str.substring(
                                                        0,
                                                        str.indexOf(
                                                                PackageManagerShellCommandDataLoader
                                                                        .STDIN_PATH))
                                                : "";
                                String str2 = split[1];
                                if (str2 == null) {
                                    str2 = "";
                                }
                                String str3 = split[2];
                                String str4 = str3 != null ? str3 : "";
                                if ("ALL".equals(substring)) {
                                    if ("WL".equals(str2)) {
                                        for (String str5 : str4.split("@", 0)) {
                                            if (str5 != null
                                                    && !((HashSet) this.mSsrmAllowPackages)
                                                            .contains(str5)) {
                                                ((HashSet) this.mSsrmAllowPackages)
                                                        .add(MARsVersionManager.toNormalText(str5));
                                            }
                                        }
                                    } else if ("Switch".equals(str2)) {
                                        if ("1".equals(str4) && this.mIsFreecessEnable) {
                                            z4 = true;
                                        }
                                        turnOnOffFreecessMonitor(z4);
                                    } else if ("Enhance_Switch".equals(str2)) {
                                        if ("1".equals(str4) && this.FREECESS_ENHANCEMENT) {
                                            z3 = true;
                                        }
                                        this.FREECESS_ENHANCEMENT = z3;
                                    }
                                } else if (mCountry.equals(substring)) {
                                    if ("WL".equals(str2)) {
                                        Set set = this.mSsrmAllowPackages;
                                        if (set != null) {
                                            ((HashSet) set).clear();
                                            for (String str6 : str4.split("@", 0)) {
                                                if (str6 != null
                                                        && !((HashSet) this.mSsrmAllowPackages)
                                                                .contains(str6)) {
                                                    ((HashSet) this.mSsrmAllowPackages)
                                                            .add(
                                                                    MARsVersionManager.toNormalText(
                                                                            str6));
                                                }
                                            }
                                        }
                                    } else if ("Switch".equals(str2)) {
                                        if ("1".equals(str4) && this.mIsFreecessEnable) {
                                            z2 = true;
                                        }
                                        turnOnOffFreecessMonitor(z2);
                                    } else if ("Enhance_Switch".equals(str2)) {
                                        if ("1".equals(str4) && this.FREECESS_ENHANCEMENT) {
                                            z = true;
                                        }
                                        this.FREECESS_ENHANCEMENT = z;
                                    }
                                }
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader4;
                        Slog.e("FreecessController", "e = " + e.getMessage());
                        bufferedReader = bufferedReader2;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                                bufferedReader = bufferedReader2;
                            } catch (IOException e3) {
                                e = e3;
                                sb = new StringBuilder("e = ");
                                sb.append(e.getMessage());
                                Slog.e("FreecessController", sb.toString());
                                return;
                            }
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedReader3 = bufferedReader4;
                        Slog.e("FreecessController", "e = " + e.getMessage());
                        bufferedReader = bufferedReader3;
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                                bufferedReader = bufferedReader3;
                            } catch (IOException e5) {
                                e = e5;
                                sb = new StringBuilder("e = ");
                                sb.append(e.getMessage());
                                Slog.e("FreecessController", sb.toString());
                                return;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader4;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e6) {
                                Slog.e("FreecessController", "e = " + e6.getMessage());
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader4.close();
                bufferedReader = readLine;
            } catch (IOException e7) {
                e = e7;
            } catch (Exception e8) {
                e = e8;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void releaseFreezedAppPid(int i) {
        boolean z;
        Integer valueOf = Integer.valueOf(i);
        synchronized (this.mFrozenPidListSelfLocked) {
            try {
                if (this.mFrozenPidListSelfLocked.contains(valueOf)) {
                    this.mFrozenPidListSelfLocked.remove(valueOf);
                    z = true;
                } else {
                    z = false;
                }
            } finally {
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mCustomFreqManager == null) {
                this.mCustomFreqManager =
                        (CustomFrequencyManager)
                                this.mContext.getSystemService("CustomFrequencyManagerService");
            }
            CustomFrequencyManager customFrequencyManager = this.mCustomFreqManager;
            if (customFrequencyManager != null && z) {
                if (customFrequencyManager.requestFreezeSlowdown(i, false, "freeze") == -1) {
                    Slog.d(
                            "FreecessController",
                            "UFZ error : pid " + i + " (when app died or watchdog half)");
                } else {
                    Slog.d(
                            "FreecessController",
                            "UFZ : release pid " + i + " (when app died or watchdog half)");
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void reportBinderUid(int i, int i2, int i3, int i4, int i5, String str) {
        int i6;
        boolean z = true;
        if (i4 != 1) {
            if (MARsDebugConfig.DEBUG_NETLINK) {
                Slog.d(
                        "FreecessController",
                        ActiveServices$$ExternalSyntheticOutline0.m(
                                i4,
                                str,
                                ",flag:",
                                ") from kernel",
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        i,
                                        i3,
                                        "Receive binder-module info(callerPid:0,uid:",
                                        ",code:",
                                        ",interfaceToken:")));
            }
            if (killPkgForCalmMode(i, "Binder(0)")) {
                return;
            }
            reportSyncBinder(i, i2);
            return;
        }
        String str2 = "";
        if (str != null) {
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    FreecessPkgStatus packageStatus = getPackageStatus(i);
                    i6 = 0;
                    if (packageStatus == null) {
                        z = false;
                    }
                    if (z) {
                        str2 = packageStatus.name;
                        i6 = packageStatus.userId;
                    }
                } finally {
                }
            }
            if (("free_buffer_full".equals(str) && i3 == -1) || "mismatch".equals(str)) {
                if (killPkgForCalmMode(i, "Binder(1)-")) {
                    return;
                }
                ProcessRecord processRecordFromPidLocked =
                        this.mAm.getProcessRecordFromPidLocked(i2);
                if (z
                        && MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                i6, str2)) {
                    if (!CACHED_RESTRICTED_BINDER
                            || processRecordFromPidLocked == null
                            || !processRecordFromPidLocked.mState.isCached()) {
                        unFreezePackage(i, "Binder(1)-".concat(str));
                        return;
                    }
                    Slog.d(
                            "FreecessController",
                            AccountManagerService$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "pid ",
                                    " ",
                                    str2,
                                    " received async transactions while frozen, binder buffer"
                                        + " full."));
                    ActivityManagerService activityManagerService = this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            processRecordFromPidLocked.killLocked(
                                    13,
                                    1002,
                                    "AsyncBinderFull while frozen",
                                    "AsyncBinderFull while frozen",
                                    true,
                                    true);
                        } finally {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (processRecordFromPidLocked != null) {
                    ActivityManagerService activityManagerService2 = this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        try {
                            if (freezeTargetProcess(
                                    processRecordFromPidLocked.uid,
                                    processRecordFromPidLocked.info != null
                                            ? processRecordFromPidLocked.info.packageName
                                            : null)) {
                                Slog.d(
                                        "FreecessController",
                                        "u="
                                                + i
                                                + " is not mars target, try google freezer ufz p="
                                                + i2);
                                CachedAppOptimizer cachedAppOptimizer =
                                        this.mAm.mOomAdjuster.mCachedAppOptimizer;
                                cachedAppOptimizer.unfreezeTemporarily(
                                        26,
                                        cachedAppOptimizer.mFreezerDebounceTimeout,
                                        processRecordFromPidLocked);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                return;
            }
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            MARsVersionManager mARsVersionManager =
                    MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
            if (mARsVersionManager.isAdjustRestrictionMatch(9, null, null, str)) {
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                if (MARsPolicyManager.isChinaModel
                        && "android.media.IAudioFocusDispatcher".equals(str)) {
                    if (((AudioManager) this.mContext.getSystemService("audio"))
                            .semGetAudioFocusedPackageName()
                            .equals(str2)) {
                        unFreezePackage(i, "Binder(1)- focus audio".concat(str));
                        return;
                    }
                    return;
                } else if (!str.contains("android.service.notification")) {
                    unFreezePackage(i, "Binder(1)-".concat(str));
                    return;
                } else {
                    if (i3 == 2) {
                        unFreezePackage(i, "Binder(1)-".concat(str));
                        return;
                    }
                    return;
                }
            }
            if (mARsVersionManager.isAdjustRestrictionMatch(17, null, null, str)) {
                if (UidStateMgr.UidStateMgrHolder.INSTANCE.mUidForegroundList.contains(
                        Integer.valueOf(i))) {
                    unFreezePackage(i, "Binder(1)-".concat(str));
                    return;
                }
            } else if (z && mARsVersionManager.isAdjustRestrictionMatch(28, str2, null, str)) {
                unFreezePackage(i, "Binder(1)-".concat(str));
                return;
            }
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            boolean z2 = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager mARsPolicyManager =
                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
            StringBuilder m =
                    DirEncryptService$$ExternalSyntheticOutline0.m(i, "0 ", " ", str, " ");
            m.append(i3);
            m.append(" ");
            m.append(i5);
            mARsPolicyManager.addDebugInfoToHistory("ABI", m.toString());
        }
    }

    public final void reportSocketResult(boolean z) {
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d("FreecessController", "Receive socket exception from kernel");
        }
        this.FREECESS_ENHANCEMENT = z;
        this.mIsOLAFEnabled = z;
        this.mIsFreecessEnable = z;
        turnOnOffFreecessMonitor(z);
        this.mIsKernelSupportFreecess = z;
        if (z) {
            if (this.mIsScreenOnFreecessEnabled) {
                this.mAm.registerProcessObserver(this.mProcessObserver);
            }
            FreecessTrigger freecessTrigger = FreecessTrigger.FreecessTriggerHolder.INSTANCE;
            freecessTrigger.mContext = this.mContext;
            IntentFilter m =
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            "android.intent.action.SCREEN_ON",
                            "android.intent.action.SCREEN_OFF",
                            "sec.app.policy.UPDATE.ssrm_update_freecess");
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    m,
                    UiModeManager.ACTION_ENTER_CAR_MODE,
                    "com.samsung.intent.action.EMERGENCY_STATE_CHANGED",
                    "android.intent.action.ACTION_SHUTDOWN",
                    "android.intent.action.REBOOT");
            m.setPriority(1000);
            freecessTrigger.mContext.registerReceiver(freecessTrigger.mIntentReceiver, m, 2);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            freecessTrigger.mContext.registerReceiverForAllUsers(
                    freecessTrigger.mPkgIntentReceiver, intentFilter, null, null, 4);
            freecessTrigger.mContext.registerReceiver(
                    freecessTrigger.mSmartSwitchIntentReceiver,
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            "com.samsung.android.intent.action.SMARTSWITCH_WORK_START",
                            "com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING",
                            "com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH"),
                    "com.wssnps.permission.COM_WSSNPS",
                    null,
                    2);
            this.mInputManagerService = (InputManagerService) ServiceManager.getService("input");
            if (this.FREECESS_ENHANCEMENT) {
                setFirewallChainEnabled(true);
            }
        }
    }

    public final void reportSyncBinder(int i, int i2) {
        boolean z;
        ProcessRecord processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(i2);
        if (CACHED_RESTRICTED_BINDER
                && processRecordFromPidLocked != null
                && processRecordFromPidLocked.mState.isCached()) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(i2, "pid ", " "),
                    processRecordFromPidLocked.processName,
                    " received sync transactions while frozen, killing",
                    "FreecessController");
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processRecordFromPidLocked.killLocked(
                            13,
                            1002,
                            "Sync transaction while in frozen state",
                            "Sync transaction while in frozen state",
                            true,
                            true);
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            return;
        }
        if (!FASTOLAF_FEATURE_LESSUFZ
                || !this.mIsOLAFEnabled
                || processRecordFromPidLocked == null
                || !this.mOLAFOn.get()) {
            unFreezePackage(i, "Binder(0)");
            return;
        }
        ActivityManagerService activityManagerService2 = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            try {
                z = processRecordFromPidLocked.mState.mCurProcState <= 3;
            } finally {
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        unFreezePackage(i, z ? "BTOP(0)" : "Binder(0)");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:

       if (com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS != false) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0084, code lost:

       android.os.Trace.traceEnd(64);
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0087, code lost:

       android.os.Binder.restoreCallingIdentity(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009f, code lost:

       if (r9 != 2) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a1, code lost:

       return true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:

       return false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009c, code lost:

       if (com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS == false) goto L28;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean requestProcessFrozen(int r13, java.lang.Integer r14, boolean r15) {
        /*
            r12 = this;
            java.lang.String r0 = "FreecessController"
            java.lang.String r1 = "there is an error at "
            long r2 = android.os.Binder.clearCallingIdentity()
            if (r15 == 0) goto Lf
            java.lang.String r4 = "freezeProcess"
            goto L12
        Lf:
            java.lang.String r4 = "unfreezeProcess"
        L12:
            r5 = 0
            r6 = 2
            r7 = 64
            r9 = -1
            boolean r10 = com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            if (r10 == 0) goto L37
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r10.<init>()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r10.append(r4)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r11 = " "
            r10.append(r11)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r10.append(r14)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            android.os.Trace.traceBegin(r7, r10)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            goto L37
        L33:
            r12 = move-exception
            goto La3
        L35:
            r12 = move-exception
            goto L8b
        L37:
            android.os.CustomFrequencyManager r10 = r12.mCustomFreqManager     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            if (r10 != 0) goto L47
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r11 = "CustomFrequencyManagerService"
            java.lang.Object r10 = r10.getSystemService(r11)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            android.os.CustomFrequencyManager r10 = (android.os.CustomFrequencyManager) r10     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r12.mCustomFreqManager = r10     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
        L47:
            android.os.CustomFrequencyManager r12 = r12.mCustomFreqManager     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            int r10 = r14.intValue()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r11 = "freeze"
            int r9 = r12.requestFreezeSlowdown(r10, r15, r11)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            if (r9 == r6) goto L80
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r12.<init>()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r12.append(r4)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r15 = ". uid="
            r12.append(r15)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r12.append(r13)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r13 = ", pid="
            r12.append(r13)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r12.append(r14)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            android.util.Slog.d(r0, r12)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            boolean r12 = com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS
            if (r12 == 0) goto L7c
            android.os.Trace.traceEnd(r7)
        L7c:
            android.os.Binder.restoreCallingIdentity(r2)
            return r5
        L80:
            boolean r12 = com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS
            if (r12 == 0) goto L87
        L84:
            android.os.Trace.traceEnd(r7)
        L87:
            android.os.Binder.restoreCallingIdentity(r2)
            goto L9f
        L8b:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33
            r13.<init>(r1)     // Catch: java.lang.Throwable -> L33
            r13.append(r12)     // Catch: java.lang.Throwable -> L33
            java.lang.String r12 = r13.toString()     // Catch: java.lang.Throwable -> L33
            android.util.Slog.d(r0, r12)     // Catch: java.lang.Throwable -> L33
            boolean r12 = com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS
            if (r12 == 0) goto L87
            goto L84
        L9f:
            if (r9 != r6) goto La2
            r5 = 1
        La2:
            return r5
        La3:
            boolean r13 = com.android.server.am.mars.MARsDebugConfig.DEBUG_FREECESS
            if (r13 == 0) goto Laa
            android.os.Trace.traceEnd(r7)
        Laa:
            android.os.Binder.restoreCallingIdentity(r2)
            throw r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.FreecessController.requestProcessFrozen(int,"
                    + " java.lang.Integer, boolean):boolean");
    }

    public final void restrictJobsByOlaf(int i, boolean z) {
        if (!FASTOLAF_PENDING_JOB || this.mContext == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.intent.action.OLAF_STATE_CHANGED");
        intent.putExtra("job_restriction", z);
        if (i != -1) {
            intent.putExtra("olaf_target_uid", i);
        }
        Slog.d("FreecessController", "restrictJobsByOlaf: restrict=" + z + ", uid=" + i);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mContext.getUserId()));
    }

    public final int sendFreecessMsg2kernel(int i, int i2, int i3, int i4) {
        try {
            byte[] newFreecessRequest = FreecessNetlinkMessage.newFreecessRequest(2, i, i2, i3, i4);
            FileDescriptor fileDescriptor = this.mSendRecvNetLinkFd;
            if (fileDescriptor == null) {
                return -1;
            }
            Os.setsockoptTimeval(
                    fileDescriptor,
                    OsConstants.SOL_SOCKET,
                    OsConstants.SO_SNDTIMEO,
                    StructTimeval.fromMillis(500L));
            return Os.write(
                    fileDescriptor,
                    newFreecessRequest,
                    0,
                    FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final void setFirewallChainEnabled(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs =
                    INetworkManagementService.Stub.asInterface(
                            ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        if (iNetworkManagementService != null) {
            try {
                try {
                    iNetworkManagementService.setFirewallChainEnabled(7, z);
                } catch (Exception e) {
                    Slog.e(
                            "FreecessController",
                            "Error occured while setFirewallChainEnabled: " + e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void setFreecessEnableForSpecificReason(int i, boolean z) {
        MARsHandler.MainHandler mainHandler;
        String str = i != 1 ? i != 2 ? "default" : "SmartSwitch" : "BugReport";
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.addDebugInfoToHistory(
                "FRZ",
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), z ? "ON" : "OFF", " by ", str));
        MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
        if (z) {
            setScreenOnFreecessEnabled(z);
            this.mIsOLAFEnabled = z;
            this.mIsFreecessEnable = z;
            if (mARsPolicyManager.getScreenOnState()) {
                return;
            }
            mARsHandler.sendRepeatTriggerMsgToMainHandler();
            return;
        }
        FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
        freecessHandler.sendSetFreecessEnableDelayedMsg(i);
        if (!mARsPolicyManager.getScreenOnState()
                && (mainHandler = mARsHandler.mMainHandler) != null) {
            mainHandler.removeMessages(2);
        }
        freecessHandler.removeBgTriggerMsg();
        setScreenOnFreecessEnabled(z);
        FreecessHandler.MainHandler mainHandler2 = freecessHandler.mMainHandler;
        if (mainHandler2 != null) {
            mainHandler2.removeMessages(9);
        }
        this.mIsOLAFEnabled = z;
        this.mIsFreecessEnable = z;
        unFreezePackage(str);
    }

    public final void setFreecessPolicyFromSCPM(String str) {
        try {
            FreecessPolicy freecessPolicy = new FreecessPolicy();
            freecessPolicy.googleFreezeExemptionList = new ArrayList();
            this.mFreecessPolicy = freecessPolicy;
            JSONObject jSONObject = new JSONObject(str);
            this.mFreecessPolicy.masterSwitch = jSONObject.getBoolean("master_switch");
            JSONArray jSONArray = jSONObject.getJSONArray("freeze_enhanced_mode");
            this.mFreecessPolicy.quickFreezeEnabled = "true".equals(jSONArray.get(0).toString());
            this.mFreecessPolicy.lessUnfreezeEnabled = "true".equals(jSONArray.get(1).toString());
            JSONArray jSONArray2 = jSONObject.getJSONArray("freeze_timeout");
            this.mFreecessPolicy.quickFreezeCheckTime =
                    Integer.parseInt(jSONArray2.get(0).toString());
            FreecessPolicy freecessPolicy2 = this.mFreecessPolicy;
            Integer.parseInt(jSONArray2.get(1).toString());
            freecessPolicy2.getClass();
            this.mFreecessPolicy.googleFreezeTime =
                    Integer.parseInt(
                            jSONObject.getJSONArray("google_freeze_timeout").get(0).toString());
            JSONArray jSONArray3 = jSONObject.getJSONArray("google_freezer_exemption_list");
            for (int i = 0; i < jSONArray3.length(); i++) {
                this.mFreecessPolicy.googleFreezeExemptionList.add(jSONArray3.get(i).toString());
            }
            FreecessPolicy freecessPolicy3 = this.mFreecessPolicy;
            this.mIsFreecessEnable = freecessPolicy3.masterSwitch;
            this.mIsQuickFreezeEnabled = freecessPolicy3.quickFreezeEnabled;
            this.mPidUnfreezeEnabled = freecessPolicy3.lessUnfreezeEnabled;
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            int i2 = freecessPolicy3.quickFreezeCheckTime;
            FreecessHandler.mScreenOnQuickFreezeCheckDelay = i2;
            FreecessHandler.mScreenOnQuickFreezeDelayInterval = i2;
            this.mAm.mOomAdjuster.mCachedAppOptimizer.mFreezerDebounceTimeout =
                    freecessPolicy3.googleFreezeTime;
            ArrayList arrayList = this.mFreecessPolicy.googleFreezeExemptionList;
            if (arrayList != null) {
                MARsListManager mARsListManager = MARsListManager.ListManagerHolder.INSTANCE;
                mARsListManager.getClass();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    synchronized (mARsListManager.mGoogleFreezerExemptionList) {
                        ((HashSet) mARsListManager.mGoogleFreezerExemptionList).add(str2);
                    }
                }
            }
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e, "setFreecessPolicyFromSCPM Exception error!", "FreecessController");
        }
    }

    public final void setIsDoingGC(int i, boolean z) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(i);
                if (packageStatus != null) {
                    if (z) {
                        packageStatus.isDoingGC++;
                    } else {
                        packageStatus.isDoingGC--;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setScreenOnFreecessEnabled(boolean z) {
        if (this.mIsScreenOnFreecessEnabled != z) {
            this.mIsScreenOnFreecessEnabled = z;
            this.mSkipTriggerLcdOnFreeze = !z;
        }
    }

    public final void setScreenOnState(boolean z) {
        ActivityManagerService activityManagerService;
        ProcessRecord topApp;
        this.mScreenOn = z;
        if (z
                && (activityManagerService = this.mAm) != null
                && (topApp = activityManagerService.getTopApp()) != null
                && topApp.info != null) {
            protectFreezePackage(topApp.userId, topApp.info.packageName, 10000L, "TopApp-ScreenOn");
            unfreezeWallPaperPackage();
        }
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(this.mLastTopUid);
                if (packageStatus != null) {
                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                    if (MARsPolicyManager.isChinaPolicyEnabled()) {
                        if (this.mScreenOn) {
                            FreecessHandler.FreecessHandlerHolder.INSTANCE
                                    .sendSlientAudioDeactivated(
                                            packageStatus.uid, packageStatus.name, 0L);
                        } else {
                            FreecessHandler.FreecessHandlerHolder.INSTANCE
                                    .sendSlientAudioActiveTrigger(
                                            packageStatus.uid, packageStatus.name);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUidIdleCheckMode(boolean z) {
        this.mUidIdleCheck = z;
        if (MARsDebugConfig.DEBUG_FREECESS) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "FreecessController",
                    new StringBuilder(
                            "FRECESS DEBUGGING MODE CHANGED ! Uid Idle Checking will be "),
                    this.mUidIdleCheck);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0090, code lost:

       if (r6.packageName.equals(r6.mRecentCallingPackage) != false) goto L35;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldDelayService(com.android.server.am.ServiceRecord r6) {
        /*
            r5 = this;
            boolean r0 = r5.mIsFreecessEnable
            r1 = 0
            if (r0 == 0) goto La4
            boolean r0 = com.android.server.am.FreecessController.FASTOLAF_FEATURE_DEALY_SERVICE
            if (r0 == 0) goto La4
            android.content.pm.ServiceInfo r0 = r6.serviceInfo
            int r0 = r0.flags
            r2 = r0 & 2
            if (r2 != 0) goto La4
            r0 = r0 & 4
            if (r0 == 0) goto L17
            goto La4
        L17:
            com.android.server.am.ActivityManagerService r0 = r5.mAm
            com.android.server.am.ProcessList r0 = r0.mProcessList
            java.lang.String r2 = r6.processName
            android.content.pm.ApplicationInfo r3 = r6.appInfo
            int r4 = r3.uid
            java.lang.String r3 = r3.packageName
            com.android.server.am.ProcessRecord r0 = r0.getSharedIsolatedProcess(r4, r2, r3)
            if (r0 != 0) goto La4
            com.android.server.am.ActivityManagerService r0 = r5.mAm
            java.lang.String r2 = r6.processName
            android.content.pm.ApplicationInfo r3 = r6.appInfo
            int r3 = r3.uid
            com.android.server.am.ProcessList r0 = r0.mProcessList
            com.android.server.am.ProcessRecord r0 = r0.getProcessRecordLocked(r3, r2)
            if (r0 == 0) goto L3a
            goto La4
        L3a:
            java.lang.String r0 = r6.packageName
            if (r0 == 0) goto L93
            java.lang.String r2 = "com.samsung"
            boolean r0 = r0.startsWith(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.lang.String r2 = "com.sec"
            boolean r0 = r0.startsWith(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.lang.String r2 = "com.google"
            boolean r0 = r0.startsWith(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.lang.String r2 = "com.android"
            boolean r0 = r0.startsWith(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.lang.String r2 = ".cts."
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.lang.String r2 = ".cts"
            boolean r0 = r0.endsWith(r2)
            if (r0 != 0) goto L92
            java.lang.String r0 = r6.packageName
            java.util.Set r5 = r5.mCtsGtsList
            java.util.HashSet r5 = (java.util.HashSet) r5
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L92
            java.lang.String r5 = r6.packageName
            java.lang.String r0 = r6.mRecentCallingPackage
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L93
        L92:
            return r1
        L93:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "delay service: "
            r5.<init>(r0)
            java.lang.String r6 = r6.shortInstanceName
            java.lang.String r0 = "FreecessController"
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r5, r6, r0)
            r5 = 1
            return r5
        La4:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.FreecessController.shouldDelayService(com.android.server.am.ServiceRecord):boolean");
    }

    public void stepLcdOnFreezedState(
            int i, int i2, String str, FreecessPkgStatus freecessPkgStatus) {
        if (i != i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(freecessPkgStatus.name);
            sb.append("(state: ");
            sb.append(i != 1 ? i != 2 ? "Unknown" : "Frozen" : "Initial");
            sb.append(" -> ");
            Slog.d(
                    "FreecessController",
                    OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                            sb,
                            i2 != 1 ? i2 != 2 ? "Unknown" : "Frozen" : "Initial",
                            ", Reason: ",
                            str,
                            ")"));
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            String str2 = freecessPkgStatus.name;
            int i3 = freecessPkgStatus.uid;
            if (freecessHandler.mMainHandler == null) {
                return;
            }
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(i3, "packageName", str2, "uid");
            Message obtainMessage = freecessHandler.mMainHandler.obtainMessage(4);
            obtainMessage.setData(m);
            freecessHandler.mMainHandler.sendMessage(obtainMessage);
            return;
        }
        if (this.mIsQuickFreezeEnabled
                || UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(freecessPkgStatus.uid)) {
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    if (!isInFreecessExcludeList(freecessPkgStatus)) {
                        String str3 = freecessPkgStatus.name;
                        int i4 = freecessPkgStatus.userId;
                        int i5 = freecessPkgStatus.uid;
                        FilterManager.FilterManagerHolder.INSTANCE.getClass();
                        int filterForSpecificPolicy =
                                FilterManager.filterForSpecificPolicy(15, i4, i5, str3);
                        if (filterForSpecificPolicy == 0) {
                            if (this.mIsQuickFreezeEnabled) {
                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                                if (MARsPolicyManager.isChinaPolicyEnabled()) {
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendMakePkgIdleMsg(
                                                    freecessPkgStatus.name,
                                                    freecessPkgStatus.uid,
                                                    false,
                                                    freecessPkgStatus.userId,
                                                    str);
                                    return;
                                }
                            }
                            FreecessHandler.FreecessHandlerHolder.INSTANCE.sendChangeToFrozenMsg(
                                    freecessPkgStatus.userId, freecessPkgStatus.name, str);
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append("(");
                        sb2.append(i5);
                        sb2.append(") is important[");
                        sb2.append(filterForSpecificPolicy);
                        BootReceiver$$ExternalSyntheticOutline0.m(sb2, "]", "FreecessController");
                    }
                } finally {
                }
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(freecessPkgStatus.name);
            sb3.append("(");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb3, freecessPkgStatus.uid, ") is important[!isUidIdle]", "FreecessController");
        }
        FreecessHandler freecessHandler2 = FreecessHandler.FreecessHandlerHolder.INSTANCE;
        String str4 = freecessPkgStatus.name;
        int i6 = freecessPkgStatus.userId;
        if (freecessHandler2.mMainHandler == null) {
            return;
        }
        Bundle m2 =
                FreecessController$$ExternalSyntheticOutline0.m(i6, "packageName", str4, "userId");
        m2.putString("reason", str);
        Message obtainMessage2 = freecessHandler2.mMainHandler.obtainMessage(2, str4);
        obtainMessage2.setData(m2);
        long j =
                FreecessControllerHolder.INSTANCE.mIsQuickFreezeEnabled
                        ? FreecessHandler.mScreenOnQuickFreezeDelayInterval
                        : 10000L;
        if (freecessHandler2.mMainHandler.hasMessages(2, str4)) {
            return;
        }
        freecessHandler2.mMainHandler.sendMessageDelayed(obtainMessage2, j);
    }

    public final void triggerCalmMode(ArrayList arrayList) {
        String str;
        this.mCalmModeEnabled = true;
        StringBuilder sb = new StringBuilder();
        FreecessHandler.FreecessHandlerHolder.INSTANCE.removeBgTriggerMsg();
        if (arrayList != null) {
            FreecessPkgMap freecessPkgMap = new FreecessPkgMap();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                int userId = this.mContext.getUserId();
                String[] split = str2.split(", ");
                if (split.length == 2) {
                    str = split[0];
                    try {
                        userId = UserHandle.getUserId(Integer.parseInt(split[1]));
                    } catch (NumberFormatException unused) {
                        Slog.e("FreecessController", "CalmMode parseInt error!");
                    }
                } else {
                    str = null;
                }
                freecessPkgMap.put(str, userId, 1);
            }
            this.mCalmModeAllowListFromGameUI = freecessPkgMap;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList2 = new ArrayList();
        WindowManagerService windowManagerService = this.mAm.mWindowManager;
        if (windowManagerService != null) {
            arrayList2 = windowManagerService.getVisibleWinSurfacePkgList();
        }
        ActiveMusicRecordFilter activeMusicRecordFilter =
                ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
        activeMusicRecordFilter.getUidListUsingAudio();
        activeMusicRecordFilter.mIsUsingAudioList = true;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray sparseArray = this.mFreecessManagedPackages.mUidMap;
                for (int i = 0; i < sparseArray.size(); i++) {
                    FreecessPkgStatus freecessPkgStatus =
                            (FreecessPkgStatus) sparseArray.valueAt(i);
                    if (arrayList2.size() <= 0 || !arrayList2.contains(freecessPkgStatus.name)) {
                        if (freezeForCalmMode(freecessPkgStatus, "CalmMode First trigger")
                                && freecessPkgStatus.isFreezedByCalm) {
                            sb.append(" " + freecessPkgStatus.uid);
                        } else if (freecessPkgStatus.freezedRecord.isFrozen
                                && !isExceptionListAppForCalmMode(freecessPkgStatus)) {
                            freecessPkgStatus.isFreezedByCalm = true;
                        }
                    } else if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d(
                                "FreecessController",
                                freecessPkgStatus.name
                                        + " has window surface, skip to freeze CalmMode");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE.mIsUsingAudioList = false;
        StringBuilder sb2 = new StringBuilder(" [");
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        sb2.append(MARsPolicyManager.formatDateTimeWithoutYear(currentTimeMillis));
        sb2.append("]");
        sb.append(sb2.toString());
        mARsPolicyManager.addDebugInfoToHistory("CalmMode ", sb.toString());
    }

    public final void triggerLcdOnFreeze(int i, String str, String str2) {
        boolean z = this.mUidIdleCheck;
        boolean z2 =
                ((z && !this.mIsQuickFreezeEnabled)
                                && z
                                && !UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(i))
                        ? false
                        : true;
        BlueToothConnectedFilter.BlueToothConnectedFilterHolder.INSTANCE.updateBTUsingPackages();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus freecessPkgStatus =
                        (FreecessPkgStatus) this.mFreecessManagedPackages.mUidMap.get(i);
                if (freecessPkgStatus == null) {
                    return;
                }
                if (str2 == null) {
                    str2 = "uidIdle";
                }
                if (!this.mIsQuickFreezeEnabled && !z2) {
                    FreecessHandler.FreecessHandlerHolder.INSTANCE.sendUidLcdOnQuickFzMsg(
                            i, str, str2, true);
                }
                stepLcdOnFreezedState(freecessPkgStatus.freezedState, 1, str2, freecessPkgStatus);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void triggerOLAF(int i, String str) {
        String str2;
        FreecessPkgStatus packageStatus;
        Iterator it;
        MARsFreezeStateRecord mARsFreezeStateRecord;
        String str3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        MARsPolicyManager.getInstance().onAppUsed(UserHandle.getUserId(i), str, false);
        unFreezePackage(UserHandle.getUserId(i), str, "activity");
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceBegin(64L, "Trigger OLAF");
        }
        ArrayList arrayList = new ArrayList();
        WindowManagerService windowManagerService = this.mAm.mWindowManager;
        if (windowManagerService != null) {
            arrayList = windowManagerService.getVisibleWinSurfacePkgList();
        }
        ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE.getUidListUsingAudio();
        if (IS_MINIMIZE_OLAF_LOCK) {
            ArrayList arrayList2 = new ArrayList();
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    SparseArray sparseArray = this.mFreecessManagedPackages.mUidMap;
                    while (r9 < sparseArray.size()) {
                        FreecessPkgStatus freecessPkgStatus =
                                (FreecessPkgStatus) sparseArray.valueAt(r9);
                        if (FASTOLAF_IMPROVE_SPEED && freecessPkgStatus != null) {
                            r9 =
                                    UidStateMgr.UidStateMgrHolder.INSTANCE.mUidGoneList.contains(
                                                    Integer.valueOf(freecessPkgStatus.uid))
                                            ? r9 + 1
                                            : 0;
                        }
                        arrayList2.add(freecessPkgStatus);
                    }
                } finally {
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) it2.next();
                MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
                synchronized (lock) {
                    if (freecessPkgStatus2 != null) {
                        try {
                            if (freecessPkgStatus2.freezedRecord != null) {
                                if (!UserHandle.isCore(freecessPkgStatus2.uid)) {
                                    if (freecessPkgStatus2.freezedRecord.isFrozen) {
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d(
                                                    "FreecessController",
                                                    "skip freezed app:"
                                                            + freecessPkgStatus2.name
                                                            + "("
                                                            + freecessPkgStatus2.uid
                                                            + ")");
                                        }
                                        sb2.append(" " + freecessPkgStatus2.uid + ":s0");
                                    } else if (str == null
                                            || !freecessPkgStatus2.name.equals(str)
                                            || freecessPkgStatus2.uid != i) {
                                        if (freecessPkgStatus2.sharedUidName == null
                                                || shouldSkipShareUid()) {
                                            it = it2;
                                            if (MARsPolicyManager.getInstance()
                                                    .isInPolicyExceptionList(
                                                            freecessPkgStatus2.userId,
                                                            4,
                                                            freecessPkgStatus2.name)) {
                                                if (MARsDebugConfig.DEBUG_ENG) {
                                                    Slog.d(
                                                            "FreecessController",
                                                            "skip isInPolicyExceptionList app:"
                                                                    + freecessPkgStatus2.name
                                                                    + "("
                                                                    + freecessPkgStatus2.uid
                                                                    + ")");
                                                }
                                                sb2.append(" " + freecessPkgStatus2.uid + ":s2");
                                            } else {
                                                MARsPolicyManager.getInstance().getClass();
                                                if (MARsPolicyManager.isChinaModel
                                                        && (str3 = freecessPkgStatus2.name) != null
                                                        && str3.equals(
                                                                DefaultAppFilter
                                                                        .DefaultAppFilterHolder
                                                                        .INSTANCE
                                                                        .mDefaultHomePackage)) {
                                                    if (MARsDebugConfig.DEBUG_OLAF) {
                                                        Slog.d(
                                                                "FreecessController",
                                                                "skip to freeze prev app:"
                                                                        + freecessPkgStatus2.name
                                                                        + ", uid : "
                                                                        + freecessPkgStatus2.uid);
                                                    }
                                                    sb2.append(
                                                            " " + freecessPkgStatus2.uid + ":s3");
                                                } else if (arrayList.size() > 0
                                                        && arrayList.contains(
                                                                freecessPkgStatus2.name)) {
                                                    if (MARsDebugConfig.DEBUG_OLAF) {
                                                        Slog.d(
                                                                "FreecessController",
                                                                freecessPkgStatus2.name
                                                                        + "("
                                                                        + freecessPkgStatus2.uid
                                                                        + ") has window surface,"
                                                                        + " skip to freeze");
                                                    }
                                                    sb2.append(
                                                            " " + freecessPkgStatus2.uid + ":s4");
                                                } else if (freecessPkgStatus2.isOlafAllowList) {
                                                    if (MARsDebugConfig.DEBUG_OLAF) {
                                                        Slog.d(
                                                                "FreecessController",
                                                                freecessPkgStatus2.name
                                                                        + "("
                                                                        + freecessPkgStatus2.uid
                                                                        + ") olaf allowlist, skip"
                                                                        + " to freeze");
                                                    }
                                                    sb2.append(
                                                            " " + freecessPkgStatus2.uid + ":s5");
                                                } else {
                                                    if (!this.mOLAFAllowListForDebug.contains(
                                                                    freecessPkgStatus2.name)
                                                            && !this.mOLAFAllowListForDebug
                                                                    .contains(
                                                                            String.valueOf(
                                                                                    freecessPkgStatus2
                                                                                            .uid))) {
                                                        if (UidStateMgr.UidStateMgrHolder.INSTANCE
                                                                .mTopUidList.contains(
                                                                Integer.valueOf(
                                                                        freecessPkgStatus2.uid))) {
                                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                                Slog.d(
                                                                        "FreecessController",
                                                                        freecessPkgStatus2.name
                                                                                + "("
                                                                                + freecessPkgStatus2
                                                                                        .uid
                                                                                + ") is top app,"
                                                                                + " skip to"
                                                                                + " freeze");
                                                            }
                                                            sb2.append(
                                                                    " "
                                                                            + freecessPkgStatus2.uid
                                                                            + ":s7");
                                                        } else if (this.mIsQuickFreezeEnabled
                                                                && (mARsFreezeStateRecord =
                                                                                freecessPkgStatus2
                                                                                        .freezedRecord)
                                                                        != null
                                                                && mARsFreezeStateRecord
                                                                        .isLcdOnFreezed) {
                                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                                Slog.d(
                                                                        "FreecessController",
                                                                        "BG freezed, skip OLAF"
                                                                            + " freeze for "
                                                                                + freecessPkgStatus2
                                                                                        .name
                                                                                + "("
                                                                                + freecessPkgStatus2
                                                                                        .uid
                                                                                + ")");
                                                            }
                                                            sb2.append(
                                                                    " "
                                                                            + freecessPkgStatus2.uid
                                                                            + ":s8");
                                                        } else {
                                                            FilterManager filterManager =
                                                                    FilterManager
                                                                            .FilterManagerHolder
                                                                            .INSTANCE;
                                                            String str4 = freecessPkgStatus2.name;
                                                            int i2 = freecessPkgStatus2.userId;
                                                            int i3 = freecessPkgStatus2.uid;
                                                            filterManager.getClass();
                                                            int filterForSpecificPolicy =
                                                                    FilterManager
                                                                            .filterForSpecificPolicy(
                                                                                    11, i2, i3,
                                                                                    str4);
                                                            if (filterForSpecificPolicy == 0) {
                                                                freezeOLAFPackage(
                                                                        freecessPkgStatus2, sb);
                                                            } else {
                                                                sb2.append(
                                                                        " "
                                                                                + freecessPkgStatus2
                                                                                        .uid
                                                                                + ":"
                                                                                + filterForSpecificPolicy);
                                                            }
                                                            if (SystemClock.uptimeMillis()
                                                                            - uptimeMillis
                                                                    >= 5) {
                                                                try {
                                                                    lock.wait(1L);
                                                                } catch (
                                                                        InterruptedException
                                                                                unused) {
                                                                }
                                                                uptimeMillis =
                                                                        SystemClock.uptimeMillis();
                                                            }
                                                        }
                                                    }
                                                    if (MARsDebugConfig.DEBUG_OLAF) {
                                                        Slog.d(
                                                                "FreecessController",
                                                                freecessPkgStatus2.name
                                                                        + "("
                                                                        + freecessPkgStatus2.uid
                                                                        + ") olaf debug allowlist,"
                                                                        + " skip to freeze");
                                                    }
                                                    sb2.append(
                                                            " " + freecessPkgStatus2.uid + ":s6");
                                                }
                                            }
                                        } else {
                                            if (MARsDebugConfig.DEBUG_ENG) {
                                                Slog.d(
                                                        "FreecessController",
                                                        "skip sharedUidName app:"
                                                                + freecessPkgStatus2.name
                                                                + "("
                                                                + freecessPkgStatus2.uid
                                                                + ")");
                                            }
                                            sb2.append(" " + freecessPkgStatus2.uid + ":s1");
                                        }
                                    }
                                }
                                it = it2;
                            }
                        } finally {
                        }
                    }
                    it = it2;
                }
                it2 = it;
            }
        } else {
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    SparseArray sparseArray2 = this.mFreecessManagedPackages.mUidMap;
                    while (r9 < sparseArray2.size()) {
                        FreecessPkgStatus freecessPkgStatus3 =
                                (FreecessPkgStatus) sparseArray2.valueAt(r9);
                        MARsFreezeStateRecord mARsFreezeStateRecord2 =
                                freecessPkgStatus3.freezedRecord;
                        if ((mARsFreezeStateRecord2 == null || !mARsFreezeStateRecord2.isFrozen)
                                && ((str == null
                                                || !freecessPkgStatus3.name.equals(str)
                                                || freecessPkgStatus3.uid != i)
                                        && freecessPkgStatus3.sharedUidName == null)) {
                            if (!MARsPolicyManager.getInstance()
                                    .isInPolicyExceptionList(
                                            freecessPkgStatus3.userId,
                                            4,
                                            freecessPkgStatus3.name)) {
                                MARsPolicyManager.getInstance().getClass();
                                if (MARsPolicyManager.isChinaModel
                                        && (str2 = freecessPkgStatus3.name) != null
                                        && str2.equals(
                                                DefaultAppFilter.DefaultAppFilterHolder.INSTANCE
                                                        .mDefaultHomePackage)) {
                                    if (MARsDebugConfig.DEBUG_OLAF) {
                                        Slog.d(
                                                "FreecessController",
                                                "skip to freeze prev app:"
                                                        + freecessPkgStatus3.name
                                                        + ", uid : "
                                                        + freecessPkgStatus3.uid);
                                    }
                                } else if (arrayList.size() <= 0
                                        || !arrayList.contains(freecessPkgStatus3.name)) {
                                    if (!freecessPkgStatus3.isOlafAllowList) {
                                        if (!this.mOLAFAllowListForDebug.contains(
                                                        freecessPkgStatus3.name)
                                                && !this.mOLAFAllowListForDebug.contains(
                                                        String.valueOf(freecessPkgStatus3.uid))) {
                                            if (UidStateMgr.UidStateMgrHolder.INSTANCE.mTopUidList
                                                    .contains(
                                                            Integer.valueOf(
                                                                    freecessPkgStatus3.uid))) {
                                                Slog.d(
                                                        "FreecessController",
                                                        freecessPkgStatus3.name
                                                                + " is top app, skip to freeze");
                                            } else {
                                                FilterManager filterManager2 =
                                                        FilterManager.FilterManagerHolder.INSTANCE;
                                                String str5 = freecessPkgStatus3.name;
                                                int i4 = freecessPkgStatus3.userId;
                                                int i5 = freecessPkgStatus3.uid;
                                                filterManager2.getClass();
                                                int filterForSpecificPolicy2 =
                                                        FilterManager.filterForSpecificPolicy(
                                                                11, i4, i5, str5);
                                                if (filterForSpecificPolicy2 == 0) {
                                                    freezeOLAFPackage(freecessPkgStatus3, sb);
                                                } else {
                                                    sb2.append(
                                                            " "
                                                                    + freecessPkgStatus3.uid
                                                                    + ":"
                                                                    + filterForSpecificPolicy2);
                                                }
                                            }
                                        } else if (MARsDebugConfig.DEBUG_OLAF) {
                                            Slog.d(
                                                    "FreecessController",
                                                    freecessPkgStatus3.name
                                                            + " olaf debug allowlist, skip to"
                                                            + " freeze");
                                        }
                                        r9++;
                                    } else if (MARsDebugConfig.DEBUG_OLAF) {
                                        Slog.d(
                                                "FreecessController",
                                                freecessPkgStatus3.name
                                                        + " olaf allowlist, skip to freeze");
                                    }
                                } else if (MARsDebugConfig.DEBUG_OLAF) {
                                    Slog.d(
                                            "FreecessController",
                                            freecessPkgStatus3.name
                                                    + " has window surface, skip to freeze");
                                }
                            } else if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d(
                                        "FreecessController",
                                        "skip isInPolicyExceptionList app:"
                                                + freecessPkgStatus3.name);
                            }
                        }
                        r9++;
                    }
                } finally {
                }
            }
        }
        int userId = this.mContext.getUserId();
        Iterator it3 = this.mOLAFBlockList.iterator();
        while (it3.hasNext()) {
            String str6 = (String) it3.next();
            if (str6 != null && !str6.equals(str)) {
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        if (MARsPolicyManager.getInstance().isMARsTarget(userId, str6)
                                && (packageStatus = getPackageStatus(userId, str6)) != null) {
                            freezeOLAFPackage(packageStatus, sb);
                        }
                    } finally {
                    }
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceEnd(64L);
        }
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "OLAF ", " [");
        MARsPolicyManager.getInstance().getClass();
        m.append(MARsPolicyManager.formatDateTimeWithoutYear(currentTimeMillis));
        m.append("]");
        mARsPolicyManager.addDebugInfoToHistory(
                m.toString(), convertLevelChangeInfoToString(sb, sb2));
        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendOLAFTimeOutMsg(
                this.olafUnfreezeEstimatedTime.longValue());
    }

    public final void turnOnOffFreecessMonitor(boolean z) {
        this.mIsFreecessEnable = z;
        this.mIsOLAFEnabled = z;
        if (!z) {
            unFreezePackage("DisableFC");
        }
        setScreenOnFreecessEnabled(z);
    }

    public final boolean unFreezeAction(
            FreecessPkgStatus freecessPkgStatus, String str, boolean z) {
        String str2;
        Set set;
        Set set2;
        boolean equals = "Packet".equals(str);
        if (freecessPkgStatus != null) {
            MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
            if (mARsFreezeStateRecord.isFrozen) {
                boolean z2 = FreecessControllerHolder.INSTANCE.mPidUnfreezeEnabled;
                final int i = freecessPkgStatus.uid;
                if (z2) {
                    FreecessHandler freecessHandler =
                            FreecessHandler.FreecessHandlerHolder.INSTANCE;
                    final boolean equals2 = "Signal".equals(str);
                    freecessHandler.mMainHandler.postAtFrontOfQueue(
                            new Runnable() { // from class:
                                // com.android.server.am.FreecessHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2 = i;
                                    boolean z3 = equals2;
                                    boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    ActivityManagerService activityManagerService =
                                            FreecessController.FreecessControllerHolder.INSTANCE
                                                    .mAm;
                                    activityManagerService.getClass();
                                    Slog.d(
                                            "ActivityManager",
                                            "unpendingScheduleServiceRestart: u="
                                                    + i2
                                                    + ", drop="
                                                    + z3);
                                    ActivityManagerService.boostPriorityForLockedSection();
                                    synchronized (activityManagerService) {
                                        try {
                                            synchronized (activityManagerService.mPendingSches) {
                                                try {
                                                    List list =
                                                            (List)
                                                                    ((HashMap)
                                                                                    activityManagerService
                                                                                            .mPendingSches)
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i2));
                                                    if (list != null) {
                                                        if (z3) {
                                                            list.clear();
                                                        } else {
                                                            Iterator it = list.iterator();
                                                            while (it.hasNext()) {
                                                                ServiceRecord serviceRecord =
                                                                        (ServiceRecord) it.next();
                                                                Slog.d(
                                                                        "ActivityManager",
                                                                        "up tryScheduleServiceRestartLocked("
                                                                                + i2
                                                                                + "): sr="
                                                                                + serviceRecord
                                                                                        .toString());
                                                                try {
                                                                    activityManagerService.mServices
                                                                            .tryScheduleServiceRestartLocked(
                                                                                    serviceRecord);
                                                                } catch (
                                                                        IllegalStateException
                                                                                unused) {
                                                                    Slog.e(
                                                                            "ActivityManager",
                                                                            "ISE while USR of "
                                                                                    + serviceRecord
                                                                                            .toString());
                                                                }
                                                                it.remove();
                                                            }
                                                        }
                                                        if (list.isEmpty()) {
                                                            ((HashMap)
                                                                            activityManagerService
                                                                                    .mPendingSches)
                                                                    .remove(Integer.valueOf(i2));
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                        } catch (Throwable th2) {
                                            ActivityManagerService
                                                    .resetPriorityAfterLockedSection();
                                            throw th2;
                                        }
                                    }
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                }
                            });
                }
                if (this.mIsOLAFEnabled && this.mOLAFOn.get()) {
                    if (FASTOLAF_FEATURE_LESSUFZ) {
                        Set set3 = this.fastOlafUfzList;
                        if ((set3 == null || !((HashSet) set3).contains(str))
                                && !str.equals("BTOP(0)")) {
                            freecessPkgStatus.isPendingUFZ = true;
                            mARsFreezeStateRecord.unfreezedReason = str;
                            return false;
                        }
                        if (mARsFreezeStateRecord.isOLAFFreezed) {
                            StringBuilder sb = new StringBuilder();
                            boolean unfreezePackageForOLAF =
                                    unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str, sb);
                            boolean z3 = MARsPolicyManager.MARs_ENABLE;
                            MARsPolicyManager mARsPolicyManager =
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m("OLAF:", str, " ");
                            m.append(sb.toString());
                            mARsPolicyManager.addDebugInfoToHistory("UFA", m.toString());
                            return unfreezePackageForOLAF;
                        }
                    } else {
                        Set set4 = this.olafUfzList;
                        if (set4 != null
                                && ((HashSet) set4).contains(str)
                                && mARsFreezeStateRecord.isOLAFFreezed) {
                            StringBuilder sb2 = new StringBuilder();
                            boolean unfreezePackageForOLAF2 =
                                    unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str, sb2);
                            boolean z4 = MARsPolicyManager.MARs_ENABLE;
                            MARsPolicyManager mARsPolicyManager2 =
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                            StringBuilder m2 =
                                    DumpUtils$$ExternalSyntheticOutline0.m("OLAF:", str, " ");
                            m2.append(sb2.toString());
                            mARsPolicyManager2.addDebugInfoToHistory("UFA", m2.toString());
                            return unfreezePackageForOLAF2;
                        }
                        if (z
                                && (set2 = this.olafUfzList) != null
                                && !((HashSet) set2).contains(str)) {
                            freecessPkgStatus.isPendingUFZ = true;
                            mARsFreezeStateRecord.unfreezedReason = str;
                            return false;
                        }
                    }
                }
                ArrayList pidList = this.mMapFrozenUidPidList.getPidList(Integer.valueOf(i));
                boolean unfreezeAllProcesses = unfreezeAllProcesses(pidList, i);
                String str3 = freecessPkgStatus.name;
                if (unfreezeAllProcesses) {
                    boolean z5 =
                            mARsFreezeStateRecord.isLcdOnFreezed
                                    || mARsFreezeStateRecord.isLcdOffFreezed;
                    onUidUnfrozen(freecessPkgStatus, pidList, str);
                    MARsPolicyManager mARsPolicyManager3 =
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(" ");
                    sb3.append(str3);
                    sb3.append(" ");
                    sb3.append(i);
                    sb3.append(" ");
                    long j = mARsFreezeStateRecord.freezedTime;
                    mARsPolicyManager3.getClass();
                    sb3.append(MARsPolicyManager.formatDateTimeWithoutYear(j));
                    mARsPolicyManager3.addDebugInfoToHistory("UFZ", sb3.toString());
                    if (this.mCalmModeEnabled) {
                        freecessPkgStatus.isFreezedByCalm = false;
                        if (!((HashSet) this.calmModeFilterList).contains(str)) {
                            FreecessHandler.FreecessHandlerHolder.INSTANCE.sendCalmModeRepeatMsg(
                                    freecessPkgStatus.userId, str3, "CalmMode");
                        }
                    } else if (z5
                            && (set = this.filterList) != null
                            && !((HashSet) set).contains(str)
                            && this.mScreenOn) {
                        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendUidLcdOnQuickFzMsg(
                                i, str3, str, true);
                    }
                    protectFreezePackage(
                            freecessPkgStatus.userId, freecessPkgStatus.name, 1500L, str);
                } else {
                    if (equals && freecessPkgStatus.monitorPacketFlag) {
                        str2 = str3;
                        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendFreecessSettlementMsg(
                                freecessPkgStatus.userId,
                                freecessPkgStatus.uid,
                                false,
                                str3,
                                1,
                                false,
                                true,
                                -1);
                    } else {
                        str2 = str3;
                    }
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    i, "UFZ error : ", str2, "(", ") "),
                            str,
                            "FreecessController");
                }
                return unfreezeAllProcesses;
            }
        }
        return false;
    }

    public final boolean unFreezeForCalmMode(FreecessPkgStatus freecessPkgStatus, String str) {
        boolean unFreezeAction = unFreezeAction(freecessPkgStatus, str, false);
        if (unFreezeAction) {
            freecessPkgStatus.isFreezedByCalm = false;
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(
                    "FreecessController",
                    "CalmMode UFZ : "
                            + freecessPkgStatus.name
                            + "("
                            + freecessPkgStatus.userId
                            + "), reason: "
                            + str);
        }
        return unFreezeAction;
    }

    public final void unFreezeForOLAF(String str) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        this.mOLAFOn.set(false);
        if (MARsDebugConfig.DEBUG_OLAF) {
            Slog.d("FreecessController", "OLAF unfreeze for ".concat(str));
            Trace.traceBegin(64L, "Unfreeze OLAF");
        }
        ArrayList arrayList2 = new ArrayList();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray sparseArray = this.mFreezedPackages.mUidMap;
                for (int i = 0; i < sparseArray.size(); i++) {
                    arrayList2.add((FreecessPkgStatus) sparseArray.valueAt(i));
                }
            } finally {
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) it.next();
            MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
            synchronized (lock) {
                if (freecessPkgStatus != null) {
                    try {
                        MARsFreezeStateRecord mARsFreezeStateRecord =
                                freecessPkgStatus.freezedRecord;
                        if (mARsFreezeStateRecord != null) {
                            if (this.mIsQuickFreezeEnabled
                                    && mARsFreezeStateRecord.isLcdOnFreezed
                                    && !freecessPkgStatus.isPendingUFZ) {
                                Slog.d(
                                        "FreecessController",
                                        "BG freezed, skip OLAF unfreeze for ("
                                                + freecessPkgStatus.name
                                                + ", "
                                                + freecessPkgStatus.uid
                                                + ")");
                            } else if (this.mCalmModeEnabled && freecessPkgStatus.isFreezedByCalm) {
                                mARsFreezeStateRecord.isOLAFFreezed = false;
                                freecessPkgStatus.freezedType = 3;
                                freecessPkgStatus.freezedState = 2;
                            } else {
                                if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                        freecessPkgStatus.userId, freecessPkgStatus.name)) {
                                    if (freecessPkgStatus.isPendingUFZ) {
                                        unfreezePackageForPending(
                                                freecessPkgStatus,
                                                freecessPkgStatus.freezedRecord.unfreezedReason);
                                        freecessPkgStatus.isPendingUFZ = false;
                                    } else if (freecessPkgStatus.freezedRecord.isOLAFFreezed) {
                                        unfreezePackageForOLAF(
                                                freecessPkgStatus, "OLAF:" + str, sb);
                                    }
                                    arrayList.add(freecessPkgStatus);
                                } else if (freecessPkgStatus.freezedRecord.isOLAFFreezed) {
                                    unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str, sb);
                                } else {
                                    unFreezeAction(freecessPkgStatus, str + ":nMARsTG", false);
                                }
                                if (SystemClock.uptimeMillis() - uptimeMillis >= 5) {
                                    try {
                                        lock.wait(1L);
                                    } catch (InterruptedException unused) {
                                    }
                                    uptimeMillis = SystemClock.uptimeMillis();
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceEnd(64L);
        }
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("OLAF:", str, " ");
        m.append(sb.toString());
        mARsPolicyManager.addDebugInfoToHistory("UFA", m.toString());
        if (!this.mSkipTriggerLcdOnFreeze) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) arrayList.get(i2);
                if (freecessPkgStatus2 != null) {
                    triggerLcdOnFreeze(freecessPkgStatus2.uid, freecessPkgStatus2.name, "uidIdle");
                }
            }
        }
        synchronized (this.olafUnfreezeEstimatedTime) {
            this.olafUnfreezeEstimatedTime = 0L;
        }
    }

    public final void unFreezePackage(int i, String str) {
        String str2;
        boolean z;
        int i2;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i);
                if (frozenPackageStatus != null) {
                    str2 = frozenPackageStatus.name;
                    i2 = frozenPackageStatus.userId;
                    z = unFreezeAction(frozenPackageStatus, str, true);
                } else {
                    str2 = null;
                    z = false;
                    i2 = 0;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z || str2 == null) {
            return;
        }
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.reportStatusWithMARs(i2, str2, false);
    }

    public final void unFreezePackage(int i, String str, String str2) {
        boolean unFreezeAction;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i, str);
                unFreezeAction =
                        (frozenPackageStatus == null || !frozenPackageStatus.freezedRecord.isFrozen)
                                ? false
                                : unFreezeAction(frozenPackageStatus, str2, true);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (unFreezeAction) {
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.reportStatusWithMARs(i, str, false);
        }
    }

    public final void unFreezePackage(String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray clone = this.mFreezedPackages.mUidMap.clone();
                for (int i = 0; i < clone.size(); i++) {
                    unFreezeAction((FreecessPkgStatus) clone.valueAt(i), str, false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unFreezeSpecialPackage(int i, String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                FreecessPkgStatus packageStatus = getPackageStatus(i);
                if (packageStatus == null) {
                    return;
                }
                MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                if (mARsFreezeStateRecord != null && mARsFreezeStateRecord.isFrozen) {
                    unFreezeAction(packageStatus, str, false);
                }
                if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                        packageStatus.userId, packageStatus.name)) {
                    if (this.mScreenOn) {
                        packageStatus.freezedState = 1;
                        FreecessHandler freecessHandler =
                                FreecessHandler.FreecessHandlerHolder.INSTANCE;
                        freecessHandler.removeBgTriggerMsgByObj(2, packageStatus.name);
                        freecessHandler.removeBgTriggerMsgByObj(3, packageStatus.name);
                        freecessHandler.removeBgTriggerMsgByObj(4, packageStatus.name);
                        freecessHandler.removeBgTriggerMsgByObj(28, packageStatus.name);
                        stepLcdOnFreezedState(packageStatus.freezedState, 1, str, packageStatus);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean unfreezeAllProcesses(ArrayList arrayList, int i) {
        Iterator it = arrayList.iterator();
        boolean z = true;
        while (it.hasNext()) {
            z &= unfreezeProcess(i, (Integer) it.next());
        }
        return z;
    }

    public final boolean unfreezePackageForOLAF(
            FreecessPkgStatus freecessPkgStatus, String str, StringBuilder sb) {
        ArrayList pidList =
                this.mMapFrozenUidPidList.getPidList(Integer.valueOf(freecessPkgStatus.uid));
        int i = freecessPkgStatus.uid;
        boolean unfreezeAllProcesses = unfreezeAllProcesses(pidList, i);
        if (unfreezeAllProcesses) {
            onUidUnfrozen(freecessPkgStatus, pidList, str);
            sb.append(" ");
            sb.append(i);
            if (MARsDebugConfig.DEBUG_OLAF) {
                StringBuilder sb2 = new StringBuilder("OLAF UFZ : ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i, freecessPkgStatus.name, "(", ") ", sb2);
                sb2.append(pidList);
                sb2.append(" reason: ");
                sb2.append(str);
                Slog.d("FreecessController", sb2.toString());
            }
        }
        return unfreezeAllProcesses;
    }

    public final void unfreezePackageForPending(FreecessPkgStatus freecessPkgStatus, String str) {
        String str2;
        String str3;
        String str4;
        boolean equals = "Packet".equals(str);
        ArrayList pidList =
                this.mMapFrozenUidPidList.getPidList(Integer.valueOf(freecessPkgStatus.uid));
        int i = freecessPkgStatus.uid;
        boolean unfreezeAllProcesses = unfreezeAllProcesses(pidList, i);
        String str5 = freecessPkgStatus.name;
        if (!unfreezeAllProcesses) {
            if (equals && freecessPkgStatus.monitorPacketFlag) {
                str2 = "FreecessController";
                str3 = "(";
                str4 = str5;
                FreecessHandler.FreecessHandlerHolder.INSTANCE.sendFreecessSettlementMsg(
                        freecessPkgStatus.userId,
                        freecessPkgStatus.uid,
                        false,
                        str5,
                        1,
                        false,
                        true,
                        -1);
            } else {
                str2 = "FreecessController";
                str3 = "(";
                str4 = str5;
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "UFZ error : ", str4, str3, ")"),
                    str,
                    str2);
            return;
        }
        onUidUnfrozen(freecessPkgStatus, pidList, str);
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m("OLAF:P-", str, " ", str5, " ");
        m.append(i);
        m.append(" ");
        long j = freecessPkgStatus.freezedRecord.freezedTime;
        mARsPolicyManager.getClass();
        m.append(MARsPolicyManager.formatDateTimeWithoutYear(j));
        mARsPolicyManager.addDebugInfoToHistory("UFZ", m.toString());
        if (MARsDebugConfig.DEBUG_OLAF) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "OLAF Pending UFZ : ", str5, "(", "), reason: "),
                    str,
                    "FreecessController");
        }
    }

    public final boolean unfreezeProcess(int i, Integer num) {
        if (!requestProcessFrozen(i, num, false)) {
            this.cntFailUnfreeze++;
            StringBuilder sb = new StringBuilder("cntFailUnfreeze=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    this.cntFailUnfreeze, i, ", uid=", ", fpid=", sb);
            sb.append(num);
            sb.append(")");
            String sb2 = sb.toString();
            boolean z = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addDebugInfoToHistory("DEV", sb2);
            return false;
        }
        ProcessRecord processRecordFromPidLocked =
                this.mAm.getProcessRecordFromPidLocked(num.intValue());
        if (processRecordFromPidLocked != null) {
            processRecordFromPidLocked.frozenMARs = false;
            SystemClock.uptimeMillis();
        }
        UidPidMap uidPidMap = this.mMapFrozenUidPidList;
        Integer valueOf = Integer.valueOf(i);
        synchronized (uidPidMap) {
            ArrayList arrayList = (ArrayList) uidPidMap.mUidPidMap.get(valueOf);
            if (arrayList != null) {
                arrayList.remove(num);
                if (arrayList.isEmpty()) {
                    uidPidMap.mUidPidMap.remove(valueOf);
                }
            }
        }
        synchronized (this.mFrozenPidListSelfLocked) {
            this.mFrozenPidListSelfLocked.remove(num);
        }
        return true;
    }

    public final void unfreezeWallPaperPackage() {
        ArrayList arrayList;
        WallPaperFilter wallPaperFilter = WallPaperFilter.WallPaperFilterHolder.INSTANCE;
        synchronized (wallPaperFilter) {
            arrayList = wallPaperFilter.mWallpaperPackageList;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FreecessControllerHolder.INSTANCE.unFreezePackage(
                    this.mContext.getUserId(), (String) it.next(), "WallPaper");
        }
    }

    public final void updateAbnormalAppFirewall(int i, boolean z) {
        Slog.d("FreecessController", "updateAbnormalAppFirewall uid " + i + ", allow " + z);
        updateFreezedUidFirewall(i, z);
    }

    public final void updateAllowListForFreecess(FreecessPkgStatus freecessPkgStatus) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaPolicyEnabled()) {
            synchronized (this.mFreezeExcludePackages) {
                try {
                    if (((HashSet) this.mFreezeExcludePackages).contains(freecessPkgStatus.name)) {
                        freecessPkgStatus.isInAllowList = true;
                    }
                } finally {
                }
            }
        }
        if (((HashSet) this.mSsrmAllowPackages).contains(freecessPkgStatus.name)) {
            freecessPkgStatus.isInAllowList = true;
        }
        if (isInOlafAllowList(freecessPkgStatus.name)) {
            freecessPkgStatus.isOlafAllowList = true;
        }
        if (this.mGpsDefaultAllowList.contains(freecessPkgStatus.name)) {
            freecessPkgStatus.isInAllowList = true;
        }
        if (((HashSet) this.mCtsGtsList).contains(freecessPkgStatus.name)) {
            freecessPkgStatus.isOlafAllowList = true;
            freecessPkgStatus.isInAllowList = true;
        }
    }

    public void updateFreezedUidFirewall(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs =
                    INetworkManagementService.Stub.asInterface(
                            ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        if (iNetworkManagementService != null) {
            try {
                try {
                    if (z) {
                        iNetworkManagementService.setFirewallUidRule(7, i, 1);
                        String str = "[UFZ] " + i + ",7,1";
                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addNetDebugInfoToHistory(
                                str);
                    } else {
                        iNetworkManagementService.setFirewallUidRule(7, i, 2);
                        String str2 = "[FRZ] " + i + ",7,2";
                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addNetDebugInfoToHistory(
                                str2);
                    }
                } catch (Exception e) {
                    Slog.e(
                            "FreecessController",
                            "Error occured while updateFreezedUidFirewall: " + e);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void updateRunningLocationPackages() {
        List<Integer> gPSUsingApps;
        List list = this.mGPSAllowList;
        if (list != null) {
            ((ArrayList) list).clear();
        }
        try {
            if (this.mLocationManager == null) {
                this.mLocationManager =
                        ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
            }
            ILocationManager iLocationManager = this.mLocationManager;
            if (iLocationManager == null
                    || (gPSUsingApps = iLocationManager.getGPSUsingApps()) == null) {
                return;
            }
            for (Integer num : gPSUsingApps) {
                if (!((ArrayList) this.mGPSAllowList).contains(num)) {
                    ((ArrayList) this.mGPSAllowList).add(num);
                }
            }
            Slog.d("FreecessController", "mGPSAllowList: " + this.mGPSAllowList);
        } catch (RemoteException unused) {
            Slog.e("FreecessController", "failed to updateRunningLocationPackages!");
        }
    }
}
