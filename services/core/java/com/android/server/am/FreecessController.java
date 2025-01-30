package com.android.server.am;

import android.app.AppGlobals;
import android.app.IProcessObserver;
import android.app.IUidObserver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.location.ILocationManager;
import android.media.AudioManager;
import android.net.INetd;
import android.net.util.SocketUtils;
import android.os.Binder;
import android.os.CustomFrequencyManager;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AlarmManagerInternal;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.input.InputManagerService;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsFreezeStateRecord;
import com.android.server.am.mars.database.MARsExemptionManager;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import com.android.server.am.mars.netlink.FreecessNetlinkMessage;
import com.android.server.am.mars.netlink.NetlinkSocket;
import com.android.server.am.mars.netlink.StructFreeCessMsg;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.wm.WindowManagerService;
import java.com.android.server.p028am.mars.database.MARsListManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class FreecessController {
    public static int DEFAULT_RECV_BUFSIZE = 0;
    public static boolean IS_MINOR_PROJECT = false;
    public static boolean IS_READ_ACCT_FILE_ERROR_PREVENTION = false;
    public static boolean IS_SUPPORT_CALM_MODE = false;
    public static boolean IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE = false;
    public static int NETLINK_KFREECESS = 0;
    public static String TAG = "FreecessController";
    public static final String mCountry;
    public static String mPlatform;
    public static String productModel;
    public static boolean sProcessFreezerEnabled;
    public final int FREECESS_DEFAULT_CONFIG_TRUE;
    public boolean FREECESS_ENHANCEMENT;
    public boolean FREECESS_LRS_ENABLED;
    public final int FREECESS_PACKET_ADD_UID_CMD;
    public final int FREECESS_PACKET_CLR_UID_CMD;
    public final int FREECESS_PACKET_DEL_UID_CMD;
    public final int INVALID_USERID;
    public final int MOD_BINDER;
    public final int MOD_CFB;
    public final int MOD_PKG;
    public final int MOD_SIG;
    public final int MSG_LOOPBACK;
    public final int MSG_TO_KERN;
    public Set calmModeFilterList;
    public int cntFailFreeze;
    public int cntFailUnfreeze;
    public int cntFailUnfreezePilot;
    public Set filterList;
    public long lastUpdateTimeProcessAllowList;
    public AlarmManagerInternal mAlarmManagerInternal;
    public ActivityManagerService mAm;
    public FreecessPkgMap mCalmModeAllowListFromGameUI;
    public final Set mCalmModeDefaultAllowList;
    public boolean mCalmModeEnabled;
    public boolean mCarModeOn;
    public int mConfigFreecess;
    public Context mContext;
    public CustomFrequencyManager mCustomFreqManager;
    public boolean mEmergencyModeOn;
    public FreecessPkgMap mFreecessManagedPackages;
    public final AtomicBoolean mFreecessOlafUpdate;
    public FreecessPolicy mFreecessPolicy;
    public final Set mFreezeExcludePackages;
    public FreecessPkgMap mFreezedPackages;
    public HashSet mFrozenPidList;
    public List mGPSAllowList;
    public InputManagerService mInputManagerService;
    public boolean mIsDumpstateWorking;
    public boolean mIsFreecessEnable;
    public boolean mIsFreecessSkipTimeEnabled;
    public boolean mIsKernelSupportFreecess;
    public boolean mIsOLAFEnabled;
    public boolean mIsQuickFreezeEnabled;
    public boolean mIsScreenOnFreecessEnabled;
    public boolean mIsSmartSwitchWorking;
    public long mKilledTimeInterval;
    public PowerManagerInternal mLocalPowerManager;
    public ILocationManager mLocationManager;
    public ConcurrentHashMap mMapFrozenProcRecord;
    public final HashMap mMapFrozenUidPidList;
    public boolean mMismatchFlag;
    public ArrayList mMonitorFreezedList;
    public INetworkManagementService mNMs;
    public ArrayList mOLAFAllowListForDebug;
    public final Set mOLAFAllowPackages;
    public ArrayList mOLAFBlockList;
    public boolean mOLAFOn;
    public String mOlafTargetPkg;
    public int mOlafTargetUserId;
    public ArrayList mPendingBlocklistForGPS;
    public ArrayList mPendingIntents;
    public ArrayList mPendingIntentsIdle;
    public boolean mPidUnfreezeEnabled;
    public ArrayList mProcessAllowListContains;
    public ArrayList mProcessAllowListEndsWith;
    public ArrayList mProcessAllowListEquals;
    public ArrayList mProcessAllowListStartsWith;
    public IProcessObserver mProcessObserver;
    public long mRecordingEndTime;
    public long mRecordingStartTime;
    public FreecessPkgMap mRestrictedPackages;
    public int mRestrictionFlagFromDC;
    public boolean mScreenOn;
    public FileDescriptor mSendRecvNetLinkFd;
    public final Set mSetUidsNeedToReleaseRestriction;
    public boolean mSkipTriggerLcdOnFreeze;
    public final Set mSsrmAllowPackages;
    public boolean mUidIdleCheck;
    public final IUidObserver mUidObserver;
    public Set olafUfzList;
    public Long olafUnfreezeEstimatedTime;
    public static boolean IS_PILOT_PROJECT = SystemProperties.getBoolean("sys.config.mars_pilot_project", true);
    public static boolean IS_MINIMIZE_OLAF_LOCK = SystemProperties.getBoolean("sys.config.mars_min_olaf_lock", true);

    public abstract class FreecessControllerHolder {
        public static final FreecessController INSTANCE = new FreecessController();
    }

    interface checkResultCallback {
        void freezeSkipFrozen(String str);
    }

    public String convertUnfreezeTypeToReason(int i) {
        return i != 1 ? i != 2 ? "default" : "SmartSwitch" : "BugReport";
    }

    public final String covertLcdOnFreezedState(int i) {
        return i != 1 ? i != 2 ? "Unknown" : "Frozen" : "Initial";
    }

    public void destroySocketsForTargetUids(int[] iArr) {
    }

    static {
        IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE = false;
        productModel = null;
        String str = SystemProperties.get("ro.product.model");
        productModel = str;
        if (str != null) {
            if (!str.startsWith("SM-G98") && !productModel.startsWith("SM-G99") && !productModel.startsWith("SM-N98")) {
                IS_MINOR_PROJECT = true;
            }
            if (productModel.startsWith("SM-A32")) {
                IS_READ_ACCT_FILE_ERROR_PREVENTION = true;
            }
            if (productModel.startsWith("SM-G99") || productModel.startsWith("SM-S91") || productModel.startsWith("SM-S92")) {
                IS_SUPPORT_CALM_MODE = true;
            }
            if (productModel.startsWith("SM-A5560")) {
                IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE = true;
            }
        }
        sProcessFreezerEnabled = false;
        mCountry = SemSystemProperties.getCountryCode();
        NETLINK_KFREECESS = 27;
        mPlatform = null;
        String str2 = SystemProperties.get("ro.board.platform", "");
        mPlatform = str2;
        if (str2 != null && str2.startsWith("mt")) {
            NETLINK_KFREECESS = 31;
        }
        DEFAULT_RECV_BUFSIZE = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS;
    }

    public FreecessController() {
        this.FREECESS_PACKET_ADD_UID_CMD = 0;
        this.FREECESS_PACKET_DEL_UID_CMD = 1;
        this.FREECESS_PACKET_CLR_UID_CMD = 2;
        this.MSG_LOOPBACK = 1;
        this.MSG_TO_KERN = 2;
        this.MOD_BINDER = 1;
        this.MOD_SIG = 2;
        this.MOD_PKG = 3;
        this.MOD_CFB = 4;
        this.FREECESS_DEFAULT_CONFIG_TRUE = 4;
        this.mKilledTimeInterval = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        this.mFreecessManagedPackages = new FreecessPkgMap();
        this.mFreezedPackages = new FreecessPkgMap();
        this.mRestrictedPackages = new FreecessPkgMap();
        this.mFrozenPidList = new HashSet();
        this.mMapFrozenProcRecord = new ConcurrentHashMap();
        this.mMapFrozenUidPidList = new HashMap();
        this.mMonitorFreezedList = new ArrayList();
        this.mIsFreecessEnable = false;
        this.mIsKernelSupportFreecess = false;
        this.mPidUnfreezeEnabled = true;
        this.mIsFreecessSkipTimeEnabled = false;
        this.mIsQuickFreezeEnabled = true;
        this.FREECESS_LRS_ENABLED = SystemProperties.getBoolean("sys.config.mars_freecess_lrs", true);
        this.mSetUidsNeedToReleaseRestriction = new HashSet();
        this.FREECESS_ENHANCEMENT = false;
        this.mConfigFreecess = Integer.parseInt("4");
        this.mRestrictionFlagFromDC = 0;
        this.mIsScreenOnFreecessEnabled = false;
        this.mUidIdleCheck = true;
        this.mSkipTriggerLcdOnFreeze = false;
        this.mScreenOn = true;
        this.mCarModeOn = false;
        this.mEmergencyModeOn = false;
        this.mIsDumpstateWorking = false;
        this.mIsSmartSwitchWorking = false;
        this.mIsOLAFEnabled = false;
        this.mOLAFOn = false;
        this.olafUnfreezeEstimatedTime = 0L;
        this.mSsrmAllowPackages = MARsListManager.getInstance().getSsrmAllowPackages();
        this.mOLAFAllowPackages = MARsListManager.getInstance().getOLAFAllowPackages();
        this.mFreezeExcludePackages = MARsListManager.getInstance().getFreezeExcludePackages();
        this.mOLAFAllowListForDebug = new ArrayList();
        this.mOLAFBlockList = new ArrayList();
        this.mPendingIntents = MARsListManager.getInstance().getPendingIntentList();
        this.mPendingIntentsIdle = MARsListManager.getInstance().getPendingIntentIdleList();
        this.mPendingBlocklistForGPS = MARsListManager.getInstance().getPendingBlockListForGPS();
        this.mProcessAllowListEquals = new ArrayList();
        this.mProcessAllowListContains = new ArrayList();
        this.mProcessAllowListStartsWith = new ArrayList();
        this.mProcessAllowListEndsWith = new ArrayList();
        this.filterList = MARsListManager.getInstance().getFilterList();
        this.olafUfzList = MARsListManager.getInstance().getOlafUfzList();
        this.calmModeFilterList = MARsListManager.getInstance().getCalmModefilterList();
        this.INVALID_USERID = -10;
        this.mFreecessOlafUpdate = new AtomicBoolean(false);
        this.mOlafTargetUserId = -10;
        this.mGPSAllowList = new ArrayList();
        this.mCalmModeEnabled = false;
        this.mCalmModeDefaultAllowList = MARsListManager.getInstance().getCalmModeDefaultList();
        this.mCalmModeAllowListFromGameUI = null;
        this.mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.am.FreecessController.2
            public void onProcessDied(int i, int i2) {
            }

            /* JADX WARN: Code restructure failed: missing block: B:35:0x002e, code lost:
            
                if (android.os.UserHandle.isApp(r5) != false) goto L16;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                boolean z2;
                if (z) {
                    FreecessController.this.unFreezePackage(i2, "FGActivity");
                    if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                        FreecessController.this.unRestrictPackage(i2);
                        return;
                    }
                    return;
                }
                if (FreecessController.this.mSkipTriggerLcdOnFreeze) {
                    return;
                }
                synchronized (MARsPolicyManager.MARsLock) {
                    if (i2 < 100000) {
                    }
                    FreecessPkgStatus packageStatus = FreecessController.this.getPackageStatus(i2);
                    if (packageStatus == null) {
                        return;
                    }
                    if (MARsPolicyManager.getInstance().isMARsTarget(packageStatus.name, packageStatus.userId)) {
                        FreecessHandler freecessHandler = FreecessHandler.getInstance();
                        String str = packageStatus.name;
                        if (!FreecessController.this.isQuickFreezeEnabled() && !FreecessController.this.mUidIdleCheck) {
                            z2 = false;
                            freecessHandler.sendUidLcdOnQuickFzMsg(i2, str, z2);
                        }
                        z2 = true;
                        freecessHandler.sendUidLcdOnQuickFzMsg(i2, str, z2);
                    }
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:19:0x000c, code lost:
            
                if (android.os.UserHandle.isApp(r4) != false) goto L8;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onForegroundServicesChanged(int i, int i2, int i3) {
                synchronized (MARsPolicyManager.MARsLock) {
                    if (i2 < 100000) {
                    }
                    FreecessPkgStatus packageStatus = FreecessController.this.getPackageStatus(i2);
                    if (packageStatus == null) {
                        return;
                    }
                    if (MARsPolicyManager.getInstance().isMARsTarget(packageStatus.name, packageStatus.userId)) {
                        packageStatus.serviceTypes = i3;
                    }
                }
            }
        };
        this.mUidObserver = new IUidObserver.Stub() { // from class: com.android.server.am.FreecessController.3
            public void onUidCachedChanged(int i, boolean z) {
            }

            public void onUidIdle(int i, boolean z) {
            }

            public void onUidProcAdjChanged(int i, int i2) {
            }

            public void onUidStateChanged(int i, int i2, long j, int i3) {
                if (i2 == 2) {
                    FreecessController.this.unFreezePackage(i, "UID_TOP");
                }
            }

            public void onUidGone(int i, boolean z) {
                synchronized (MARsPolicyManager.MARsLock) {
                    FreecessPkgStatus packageStatus = FreecessController.this.getPackageStatus(i);
                    if (packageStatus == null) {
                        return;
                    }
                    if (MARsPolicyManager.getInstance().isMARsTarget(packageStatus.name, packageStatus.userId)) {
                        if (FreecessController.this.mScreenOn) {
                            packageStatus.freezedState = 1;
                            FreecessHandler.getInstance().removeBgTriggerMsgByObj(1, packageStatus.name);
                            FreecessHandler.getInstance().removeBgTriggerMsgByObj(2, packageStatus.name);
                            FreecessHandler.getInstance().removeBgTriggerMsgByObj(28, packageStatus.name);
                            FreecessHandler.getInstance().removeBgTriggerMsgByObj(3, packageStatus.name);
                            FreecessHandler.getInstance().removeBgTriggerMsgByObj(4, packageStatus.name);
                        }
                    }
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:32:0x001d, code lost:
            
                if (android.os.UserHandle.isApp(r4) != false) goto L13;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onUidActive(int i) {
                synchronized (MARsPolicyManager.MARsLock) {
                    if (FreecessController.this.mIsScreenOnFreecessEnabled) {
                        if (!FreecessController.this.mSkipTriggerLcdOnFreeze) {
                            if (i < 100000) {
                            }
                            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) FreecessController.this.mFreecessManagedPackages.getByUid(i);
                            if (freecessPkgStatus == null) {
                                return;
                            }
                            FreecessController freecessController = FreecessController.this;
                            if (freecessController.mCalmModeEnabled) {
                                FreecessHandler.getInstance().sendCalmModeRepeatMsg(freecessPkgStatus.name, freecessPkgStatus.userId, "CalmMode for restart");
                                return;
                            }
                            if (freecessPkgStatus.freezedState == 2 || (freecessController.mScreenOn && freecessPkgStatus.freezedRecord.isLcdOffFreezed)) {
                                if (MARsDebugConfig.DEBUG_ENG) {
                                    Slog.d(FreecessController.TAG, "onUidActive...uid:" + freecessPkgStatus.uid + ", packageName: " + freecessPkgStatus.name + ", freezedState: " + freecessPkgStatus.freezedState);
                                }
                                FreecessHandler.getInstance().sendResetStateMsg(freecessPkgStatus.name, freecessPkgStatus.userId);
                            }
                        }
                    }
                }
            }
        };
        this.mRecordingStartTime = 0L;
        this.mRecordingEndTime = 0L;
        this.mSendRecvNetLinkFd = null;
        this.mMismatchFlag = false;
    }

    public static FreecessController getInstance() {
        return FreecessControllerHolder.INSTANCE;
    }

    public void init(Context context, ActivityManagerService activityManagerService) {
        this.mContext = context;
        this.mAm = activityManagerService;
        setScreenOnFreecessEnabled(false);
        this.FREECESS_ENHANCEMENT = false;
        this.mIsOLAFEnabled = false;
        this.mIsFreecessEnable = false;
        if (this.mConfigFreecess == 4) {
            FreecessHandler.getInstance().init(this.mContext);
        }
    }

    public void init(Context context, ActivityManagerService activityManagerService, HandlerThread handlerThread, HandlerThread handlerThread2) {
        this.mContext = context;
        this.mAm = activityManagerService;
        setScreenOnFreecessEnabled(false);
        this.FREECESS_ENHANCEMENT = false;
        this.mIsOLAFEnabled = false;
        this.mIsFreecessEnable = false;
        reportSocketResult(true);
        FreecessHandler.getInstance().init(this.mContext, handlerThread, handlerThread2);
    }

    public void registerFreecessTrigger() {
        this.mAm.registerUidObserver(this.mUidObserver, 15, -1, null);
        if (this.mIsScreenOnFreecessEnabled) {
            this.mAm.registerProcessObserver(this.mProcessObserver);
        }
        FreecessTrigger.getInstance().init(this.mContext);
        this.mInputManagerService = (InputManagerService) ServiceManager.getService("input");
        if (getFreecessEnhancementEnabledState()) {
            setFirewallChainEnabled(7, true);
        }
    }

    public boolean getFreecessEnabledConfig() {
        Slog.d(TAG, "getFreecessEnabledConfig mIsKernelSupportFreecess = " + this.mIsKernelSupportFreecess);
        return this.mIsKernelSupportFreecess;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x009f A[Catch: IOException -> 0x00a3, TRY_ENTER, TRY_LEAVE, TryCatch #5 {IOException -> 0x00a3, blocks: (B:23:0x009f, B:55:0x0066), top: B:6:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0095 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void recoverFreezerStateIfTHAWED() {
        BufferedReader bufferedReader;
        FileOutputStream fileOutputStream;
        Throwable th;
        IOException e;
        File file = new File("/dev/freezer/frozen/freezer.state");
        if (!file.exists() || !file.canWrite()) {
            Slog.e(TAG, "recoverFreezerStateIfTHAWED error: file doesn't exist or can't write");
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/dev/freezer/frozen/freezer.state"));
            } catch (IOException e2) {
                e2.printStackTrace();
                return;
            }
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            fileOutputStream = null;
        }
        try {
            String readLine = bufferedReader.readLine();
            Slog.d(TAG, "freezer.state == " + readLine);
            if (readLine != null && readLine.equals("THAWED")) {
                fileOutputStream = new FileOutputStream("/dev/freezer/frozen/freezer.state", true);
                try {
                    try {
                        fileOutputStream.write("FROZEN".getBytes("UTF-8"));
                        fileOutputStream.flush();
                        fileOutputStream2 = fileOutputStream;
                    } catch (IOException e4) {
                        e = e4;
                        Slog.e(TAG, "can't read or write /dev/freezer/frozen/freezer.state" + e.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            fileOutputStream.close();
                        }
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileOutputStream == null) {
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            }
            try {
                bufferedReader.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (IOException e9) {
            e = e9;
            fileOutputStream = null;
            e = e;
            Slog.e(TAG, "can't read or write /dev/freezer/frozen/freezer.state" + e.getMessage());
            if (bufferedReader != null) {
            }
            if (fileOutputStream == null) {
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            th = th;
            if (bufferedReader != null) {
            }
            if (fileOutputStream == null) {
            }
        }
        if (fileOutputStream2 != null) {
            fileOutputStream2.close();
        }
    }

    public void turnOnOffFreecessMonitor(boolean z) {
        setFreecessEnabled(z);
        setScreenOnFreecessEnabled(z);
    }

    public void setFreecessEnabled(boolean z) {
        this.mIsFreecessEnable = z;
        this.mIsOLAFEnabled = z;
        if (z) {
            return;
        }
        unFreezePackage("DisableFC");
    }

    public boolean isQuickFreezeEnabled() {
        return this.mIsQuickFreezeEnabled;
    }

    public boolean isPidUfzEnabled() {
        return this.mPidUnfreezeEnabled;
    }

    public boolean getFreecessEnabled() {
        return this.mIsFreecessEnable;
    }

    public void setRestrictionFlagFromDC(int i) {
        this.mRestrictionFlagFromDC = i;
        FreecessTrigger.getInstance().registerReceiverForEnhancedFreecess();
    }

    public void setCarModeOnState(boolean z) {
        this.mCarModeOn = z;
    }

    public void setEmergencyModeOnState(boolean z) {
        this.mEmergencyModeOn = z;
    }

    public void setFreeceeEnhanceEnabled(boolean z) {
        this.FREECESS_ENHANCEMENT = z;
    }

    public boolean getFreecessEnhancementEnabledState() {
        return this.FREECESS_ENHANCEMENT;
    }

    public void setOLAFEnabled(boolean z) {
        this.mIsOLAFEnabled = z;
    }

    public void setOLAFBlockList(String str) {
        ArrayList arrayList = this.mOLAFBlockList;
        if (arrayList == null || arrayList.contains(str)) {
            return;
        }
        this.mOLAFBlockList.add(str);
    }

    public boolean isMARsTargetDeferrable(int i, String str, ProcessRecord processRecord, BroadcastRecord broadcastRecord) {
        int userId = UserHandle.getUserId(i);
        boolean isChinaPolicyEnabled = MARsPolicyManager.getInstance().isChinaPolicyEnabled();
        boolean isAutoRunOn = MARsPolicyManager.getInstance().isAutoRunOn(str, userId);
        if (!isFreecessTarget(i)) {
            return false;
        }
        if (MARsPolicyManager.getInstance().isMARsTarget(str, userId) && isPendingIntent(broadcastRecord.intent.getAction(), str, userId, i, processRecord)) {
            return true;
        }
        if (!isFreezedPackage(i)) {
            return false;
        }
        if (isChinaPolicyEnabled) {
            if (!isChinaPolicyEnabled) {
                return false;
            }
            if (!this.mScreenOn && !isAutoRunOn) {
                return false;
            }
        }
        unFreezePackage(i, INetd.IF_FLAG_BROADCAST);
        return false;
    }

    public boolean isPendingIntent(String str, String str2, int i, int i2, ProcessRecord processRecord) {
        ProcessStateRecord processStateRecord;
        boolean isChinaPolicyEnabled = MARsPolicyManager.getInstance().isChinaPolicyEnabled();
        boolean isAutoRunOn = MARsPolicyManager.getInstance().isAutoRunOn(str2, i);
        boolean isUidIdle = UidStateMgr.getInstance().isUidIdle(i2);
        boolean z = (processRecord == null || (processStateRecord = processRecord.mState) == null || processStateRecord.getForcingToImportant() == null) ? false : true;
        if (!isChinaPolicyEnabled || (isChinaPolicyEnabled && (this.mScreenOn || isAutoRunOn))) {
            ArrayList arrayList = this.mPendingIntents;
            if (arrayList != null && arrayList.contains(str)) {
                return true;
            }
            ArrayList arrayList2 = this.mPendingIntentsIdle;
            if (arrayList2 != null && arrayList2.contains(str) && isUidIdle) {
                if (!z) {
                    return true;
                }
                if (isFreezedPackage(str2, i)) {
                    unFreezePackage(str2, i, INetd.IF_FLAG_BROADCAST);
                }
                return false;
            }
        }
        return false;
    }

    public void setPendingIntentList(String str, String str2) {
        ArrayList arrayList = this.mPendingIntents;
        if (arrayList != null) {
            if (arrayList.contains(str2)) {
                return;
            }
            if ("block".equals(str) || productModel.startsWith(str)) {
                this.mPendingIntents.add(str2);
            }
            if (MARsPolicyManager.getInstance().isChinaPolicyEnabled() && "blockchn".equals(str)) {
                this.mPendingIntents.add(str2);
            }
        }
        if (this.mPendingIntentsIdle == null || !"idle".equals(str)) {
            return;
        }
        this.mPendingIntentsIdle.add(str2);
    }

    public void setPendingBlocklistForGPS(String str) {
        ArrayList arrayList = this.mPendingBlocklistForGPS;
        if (arrayList == null || arrayList.contains(str)) {
            return;
        }
        this.mPendingBlocklistForGPS.add(str);
    }

    public synchronized void setProcessAllowList(String str, String str2) {
        ArrayList arrayList;
        if (str == null || str2 == null) {
            return;
        }
        if ("equals".equals(str2)) {
            ArrayList arrayList2 = this.mProcessAllowListEquals;
            if (arrayList2 != null) {
                if (arrayList2.contains(str)) {
                    return;
                } else {
                    this.mProcessAllowListEquals.add(str);
                }
            }
        } else if ("contains".equals(str2)) {
            ArrayList arrayList3 = this.mProcessAllowListContains;
            if (arrayList3 != null) {
                if (arrayList3.contains(str)) {
                    return;
                } else {
                    this.mProcessAllowListContains.add(str);
                }
            }
        } else if ("startsWith".equals(str2)) {
            ArrayList arrayList4 = this.mProcessAllowListStartsWith;
            if (arrayList4 != null) {
                if (arrayList4.contains(str)) {
                    return;
                } else {
                    this.mProcessAllowListStartsWith.add(str);
                }
            }
        } else if ("endsWith".equals(str2) && (arrayList = this.mProcessAllowListEndsWith) != null) {
            if (arrayList.contains(str)) {
                return;
            } else {
                this.mProcessAllowListEndsWith.add(str);
            }
        }
        this.lastUpdateTimeProcessAllowList = SystemClock.uptimeMillis();
    }

    public void setLRsEnabled(boolean z) {
        this.FREECESS_LRS_ENABLED = z;
    }

    public boolean getLRsEnabled() {
        return this.FREECESS_LRS_ENABLED;
    }

    public void setFreecessEnableForDump(boolean z) {
        if (this.mIsDumpstateWorking) {
            return;
        }
        if (!z) {
            this.mIsDumpstateWorking = true;
        }
        setFreecessEnableForSpecificReason(z, 1);
    }

    public void setFreecessEnableForSmartSwitch(boolean z) {
        if (this.mIsSmartSwitchWorking) {
            return;
        }
        if (!z) {
            this.mIsSmartSwitchWorking = true;
        }
        setFreecessEnableForSpecificReason(z, 2);
    }

    public void setFreecessEnableForSpecificReason(boolean z, int i) {
        String convertUnfreezeTypeToReason = convertUnfreezeTypeToReason(i);
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(z ? "ON" : "OFF");
        sb.append(" by ");
        sb.append(convertUnfreezeTypeToReason);
        mARsPolicyManager.addDebugInfoToHistory("FRZ", sb.toString());
        if (z) {
            setScreenOnFreecessEnabled(z);
            setOLAFEnabled(z);
            this.mIsFreecessEnable = z;
            if (MARsPolicyManager.getInstance().getScreenOnState()) {
                return;
            }
            MARsHandler.getInstance().sendRepeatTriggerMsgToMainHandler();
            return;
        }
        FreecessHandler.getInstance().sendSetFreecessEnableDelayedMsg(i);
        if (!MARsPolicyManager.getInstance().getScreenOnState()) {
            MARsHandler.getInstance().removeMessages(2);
        }
        FreecessHandler.getInstance().removeBgTriggerMsg();
        setScreenOnFreecessEnabled(z);
        FreecessHandler.getInstance().removeMessages(9);
        setOLAFEnabled(z);
        this.mIsFreecessEnable = z;
        unFreezePackage(convertUnfreezeTypeToReason);
    }

    public boolean getIsDumpstateWorking() {
        return this.mIsDumpstateWorking;
    }

    public void setIsDumpstateWorking(boolean z) {
        this.mIsDumpstateWorking = z;
    }

    public boolean getIsSmartSwitchWorking() {
        return this.mIsSmartSwitchWorking;
    }

    public void setIsSmartSwitchWorking(boolean z) {
        this.mIsSmartSwitchWorking = z;
    }

    public void setIsDoingGC(int i, boolean z) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(i);
            if (packageStatus != null) {
                if (z) {
                    packageStatus.isDoingGC++;
                } else {
                    packageStatus.isDoingGC--;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getPidProcessName(int i) {
        ?? r0;
        IOException e;
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "/proc/";
        sb.append("/proc/");
        sb.append(i);
        sb.append("/comm");
        String sb2 = sb.toString();
        ?? r6 = null;
        try {
            try {
                r0 = new BufferedReader(new FileReader(sb2));
                try {
                    str = r0.readLine();
                    try {
                        r0.close();
                        str2 = r0;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        str2 = r0;
                    }
                } catch (IOException e3) {
                    e = e3;
                    Slog.e(TAG, "can't read " + sb2 + e.getMessage());
                    if (r0 != 0) {
                        try {
                            r0.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    str = "";
                    str2 = r0;
                    return str;
                }
            } catch (Throwable th) {
                th = th;
                r6 = str2;
                if (r6 != null) {
                    try {
                        r6.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            r0 = 0;
            e = e6;
        } catch (Throwable th2) {
            th = th2;
            if (r6 != null) {
            }
            throw th;
        }
        return str;
    }

    public final FreecessPkgStatus getFrozenPackageStatus(String str, int i) {
        FreecessPkgStatus freecessPkgStatus;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgMap freecessPkgMap = this.mFreezedPackages;
            freecessPkgStatus = (freecessPkgMap == null || freecessPkgMap.sizeByUserId() <= 0) ? null : (FreecessPkgStatus) this.mFreezedPackages.getByUserId(i, str);
        }
        return freecessPkgStatus;
    }

    public final FreecessPkgStatus getFrozenPackageStatus(int i) {
        FreecessPkgStatus freecessPkgStatus;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgMap freecessPkgMap = this.mFreezedPackages;
            freecessPkgStatus = (freecessPkgMap == null || freecessPkgMap.size() <= 0) ? null : (FreecessPkgStatus) this.mFreezedPackages.getByUid(i);
        }
        return freecessPkgStatus;
    }

    public final FreecessPkgStatus getRestrictedPackageStatus(int i) {
        FreecessPkgStatus freecessPkgStatus;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgMap freecessPkgMap = this.mRestrictedPackages;
            freecessPkgStatus = (freecessPkgMap == null || freecessPkgMap.size() <= 0) ? null : (FreecessPkgStatus) this.mRestrictedPackages.getByUid(i);
        }
        return freecessPkgStatus;
    }

    public final FreecessPkgStatus getPackageStatus(String str, int i) {
        ApplicationInfo applicationInfo;
        FreecessPkgStatus freecessPkgStatus = null;
        if (str != null && str.contains(".cts")) {
            return null;
        }
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            if (this.mFreecessManagedPackages.sizeByUserId() > 0 && (freecessPkgStatus = (FreecessPkgStatus) this.mFreecessManagedPackages.getByUserId(i, str)) != null) {
                return freecessPkgStatus;
            }
            if (freecessPkgStatus != null) {
                return freecessPkgStatus;
            }
            try {
                PackageInfo packageInfo = AppGlobals.getPackageManager().getPackageInfo(str, 4111, i);
                if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
                    return freecessPkgStatus;
                }
                int i2 = applicationInfo.uid;
                FreecessPkgStatus freecessPkgStatus2 = new FreecessPkgStatus(str, i2, i, AppGlobals.getPackageManager().getTargetSdkVersion(str));
                try {
                    String str2 = packageInfo.sharedUserId;
                    if (str2 != null) {
                        freecessPkgStatus2.sharedUidName = str2;
                    }
                    synchronized (lock) {
                        this.mFreecessManagedPackages.put(i2, str, freecessPkgStatus2);
                        updateAllowListForFreecess(freecessPkgStatus2);
                    }
                    return freecessPkgStatus2;
                } catch (RemoteException e) {
                    e = e;
                    freecessPkgStatus = freecessPkgStatus2;
                    Slog.e(TAG, "getPackageStatus() failed to create ps " + e);
                    return freecessPkgStatus;
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        }
    }

    public FreecessPkgStatus getPackageStatus(int i) {
        FreecessPkgStatus freecessPkgStatus;
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            FreecessPkgMap freecessPkgMap = this.mFreecessManagedPackages;
            if (freecessPkgMap == null || freecessPkgMap.size() <= 0) {
                freecessPkgStatus = null;
            } else {
                freecessPkgStatus = (FreecessPkgStatus) this.mFreecessManagedPackages.getByUid(i);
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
                PackageInfo packageInfo = AppGlobals.getPackageManager().getPackageInfo(str, 4111, UserHandle.getUserId(i));
                FreecessPkgStatus freecessPkgStatus2 = new FreecessPkgStatus(str, i, UserHandle.getUserId(i), AppGlobals.getPackageManager().getTargetSdkVersion(str));
                if (packageInfo != null) {
                    try {
                        String str2 = packageInfo.sharedUserId;
                        if (str2 != null) {
                            freecessPkgStatus2.sharedUidName = str2;
                        }
                    } catch (RemoteException e) {
                        e = e;
                        freecessPkgStatus = freecessPkgStatus2;
                        Slog.e(TAG, "getPackageStatus() failed to create ps " + e);
                        return freecessPkgStatus;
                    }
                }
                synchronized (lock) {
                    this.mFreecessManagedPackages.put(i, str, freecessPkgStatus2);
                    updateAllowListForFreecess(freecessPkgStatus2);
                }
                return freecessPkgStatus2;
            } catch (RemoteException e2) {
                e = e2;
            }
        }
    }

    public void deleteRemovedPackage(String str, int i) {
        int userId = UserHandle.getUserId(i);
        synchronized (MARsPolicyManager.MARsLock) {
            if (this.mFreezedPackages.getByUserId(userId, str) != null) {
                unFreezePackage(str, userId, "PkgRemoved");
            }
            this.mFreecessManagedPackages.remove(i, str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v18, types: [java.lang.Integer, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v32 */
    /* JADX WARN: Type inference failed for: r0v33 */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r6v15, types: [java.util.HashSet] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x0082 -> B:27:0x00dd). Please report as a decompilation issue!!! */
    public final void readAcctFile(String str, ArrayList arrayList, boolean z) {
        BufferedReader bufferedReader;
        ?? r0 = 0;
        r0 = 0;
        r0 = 0;
        r0 = 0;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader(str));
                } catch (IOException e) {
                    e.printStackTrace();
                    r0 = r0;
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            String readLine = bufferedReader.readLine();
            boolean z2 = false;
            while (readLine != null) {
                if (!arrayList.contains(Integer.valueOf(readLine))) {
                    arrayList.add(Integer.valueOf(readLine));
                    if (!z2) {
                        z2 = true;
                    }
                }
                readLine = bufferedReader.readLine();
                z2 = z2;
            }
            boolean z3 = IS_READ_ACCT_FILE_ERROR_PREVENTION;
            boolean z4 = z3;
            z4 = z3;
            z4 = z3;
            if (z3 && !z && !z2) {
                int parseInt = Integer.parseInt(str.split("pid_")[1].split("/")[0]);
                boolean contains = arrayList.contains(Integer.valueOf(parseInt));
                z4 = contains;
                if (!contains) {
                    ?? r6 = this.mFrozenPidList;
                    ?? valueOf = Integer.valueOf(parseInt);
                    boolean contains2 = r6.contains(valueOf);
                    z4 = valueOf;
                    if (contains2) {
                        arrayList.add(Integer.valueOf(parseInt));
                        Slog.d(TAG, "cannot get pid from uid. but bring it in a different way. pid: " + parseInt);
                        z4 = "cannot get pid from uid. but bring it in a different way. pid: ";
                    }
                }
            }
            bufferedReader.close();
            r0 = z4;
        } catch (IOException e4) {
            e = e4;
            r0 = bufferedReader;
            Slog.e(TAG, "can't read " + str + e.getMessage());
            if (r0 != 0) {
                r0.close();
                r0 = r0;
            }
        } catch (Exception e5) {
            e = e5;
            r0 = bufferedReader;
            Slog.e(TAG, "Error occurred when reading " + str + " - " + e.getMessage());
            if (r0 != 0) {
                r0.close();
                r0 = r0;
            }
        } catch (Throwable th2) {
            th = th2;
            r0 = bufferedReader;
            if (r0 != 0) {
                try {
                    r0.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    public final ArrayList getAllRunningPackagePids(String str, int i, boolean z) {
        String[] list;
        String str2 = "/sys/fs/cgroup/uid_" + i;
        File file = new File(str2);
        ArrayList arrayList = new ArrayList();
        if (file.isDirectory() && (list = file.list()) != null) {
            for (int i2 = 0; i2 < list.length; i2++) {
                if (list[i2].contains("pid")) {
                    readAcctFile(str2 + "/" + list[i2] + "/cgroup.procs", arrayList, z);
                }
            }
        }
        return arrayList;
    }

    public boolean isProcessFrozen(int i) {
        synchronized (this.mFrozenPidList) {
            if (this.mFrozenPidList.isEmpty()) {
                return false;
            }
            return this.mFrozenPidList.contains(Integer.valueOf(i));
        }
    }

    public final boolean isPkgRunningService(String str, int i) {
        return this.mAm.checkProcDiedOrComponentExecutingForFreeze(getAllRunningPackagePids(str, i, true), new ArrayList()) == 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x0269 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:140:0x0253, B:141:0x0265, B:143:0x0269, B:144:0x026b, B:157:0x02a7, B:158:0x02a8, B:211:0x02ae, B:146:0x026c, B:148:0x0278, B:149:0x0286, B:151:0x029c, B:152:0x02a3), top: B:139:0x0253, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean writeDevFile(String str, int i, ArrayList arrayList, String str2, boolean z, checkResultCallback checkresultcallback) {
        boolean z2;
        ArrayList arrayList2;
        long j;
        ArrayList arrayList3;
        int i2;
        boolean z3;
        Iterator it;
        boolean z4;
        ProcessRecord processRecordFromPidLocked;
        Iterator it2;
        int i3;
        MARsPolicyManager mARsPolicyManager;
        StringBuilder sb;
        if ("/dev/freezer/frozen/cgroup.procs".equals(str2)) {
            z2 = true;
        } else {
            if (!"/dev/freezer/thaw/cgroup.procs".equals(str2)) {
                return false;
            }
            z2 = false;
        }
        ArrayList allRunningPackagePids = getAllRunningPackagePids(str, i, z2);
        if (arrayList != null) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                allRunningPackagePids.add((Integer) it3.next());
            }
        }
        ArrayList arrayList4 = new ArrayList();
        if (IS_PILOT_PROJECT && !z2) {
            synchronized (this.mFrozenPidList) {
                if (this.mMapFrozenUidPidList.containsKey(Integer.valueOf(i))) {
                    Iterator it4 = ((ArrayList) this.mMapFrozenUidPidList.get(Integer.valueOf(i))).iterator();
                    while (it4.hasNext()) {
                        Integer num = (Integer) it4.next();
                        if (!allRunningPackagePids.contains(num)) {
                            arrayList4.add(num);
                        }
                    }
                    this.mMapFrozenUidPidList.remove(Integer.valueOf(i));
                }
            }
        }
        if (z2) {
            arrayList2 = new ArrayList();
            int checkProcDiedOrComponentExecutingForFreeze = this.mAm.checkProcDiedOrComponentExecutingForFreeze(allRunningPackagePids, arrayList2);
            if (checkProcDiedOrComponentExecutingForFreeze != 0) {
                String str3 = "";
                switch (checkProcDiedOrComponentExecutingForFreeze) {
                    case 1:
                        str3 = "AD";
                        break;
                    case 2:
                        str3 = "ES";
                        break;
                    case 3:
                        str3 = "RI";
                        break;
                    case 4:
                        str3 = "LP";
                        break;
                    case 5:
                        str3 = "SP";
                        break;
                    case 6:
                        str3 = "FG";
                        break;
                    case 7:
                        str3 = "RL";
                        break;
                    case 8:
                        str3 = "BTOP";
                        break;
                }
                if (checkresultcallback != null) {
                    checkresultcallback.freezeSkipFrozen(str3);
                }
                return false;
            }
        } else {
            arrayList2 = null;
        }
        ArrayList arrayList5 = arrayList2;
        long uptimeMillis = SystemClock.uptimeMillis();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i4 = -1;
            for (Iterator it5 = arrayList4.iterator(); it5.hasNext(); it5 = it2) {
                int intValue = ((Integer) it5.next()).intValue();
                try {
                    Integer valueOf = Integer.valueOf(intValue);
                    if (z2) {
                        it2 = it5;
                    } else {
                        if (this.mOLAFOn) {
                            it2 = it5;
                        } else {
                            String str4 = TAG;
                            StringBuilder sb2 = new StringBuilder();
                            it2 = it5;
                            try {
                                sb2.append("UFZ : ");
                                sb2.append(str);
                                sb2.append("(");
                                sb2.append(intValue);
                                sb2.append("), cached_pid: Y");
                                Slog.d(str4, sb2.toString());
                            } catch (Exception e) {
                                e = e;
                                e.printStackTrace();
                            }
                        }
                        synchronized (this.mFrozenPidList) {
                            if (this.mFrozenPidList.contains(valueOf)) {
                                this.mFrozenPidList.remove(valueOf);
                            }
                        }
                        ProcessRecord processRecord = (ProcessRecord) this.mMapFrozenProcRecord.get(Integer.valueOf(intValue));
                        if (processRecord != null) {
                            processRecord.frozenMARs = false;
                            processRecord.freezeUnfreezeTimeMARs = uptimeMillis;
                            this.mMapFrozenProcRecord.remove(Integer.valueOf(intValue));
                        }
                    }
                    if (this.mCustomFreqManager == null) {
                        this.mCustomFreqManager = (CustomFrequencyManager) this.mContext.getSystemService("CustomFrequencyManagerService");
                    }
                    int requestFreezeSlowdown = this.mCustomFreqManager.requestFreezeSlowdown(intValue, z2, "freeze");
                    if (requestFreezeSlowdown == -1) {
                        try {
                            Slog.d(TAG, "Fail to set Freeze. uid=" + i + ", pid=" + intValue + ", isFreeze=" + z2 + ", cached_pid: Y");
                            this.cntFailUnfreezePilot = this.cntFailUnfreezePilot + 1;
                            mARsPolicyManager = MARsPolicyManager.getInstance();
                            sb = new StringBuilder();
                            i3 = requestFreezeSlowdown;
                        } catch (Exception e2) {
                            e = e2;
                            i3 = requestFreezeSlowdown;
                        }
                        try {
                            sb.append("cntFailUnfreezePilot=");
                            sb.append(this.cntFailUnfreezePilot);
                            sb.append(", uid=");
                            sb.append(i);
                            sb.append(", fpid=");
                            sb.append(intValue);
                            mARsPolicyManager.addDebugInfoToHistory("DEV", sb.toString());
                        } catch (Exception e3) {
                            e = e3;
                            i4 = i3;
                            e.printStackTrace();
                        }
                    } else {
                        i3 = requestFreezeSlowdown;
                    }
                    i4 = i3;
                } catch (Exception e4) {
                    e = e4;
                    it2 = it5;
                }
            }
            if (!arrayList4.isEmpty() && allRunningPackagePids.isEmpty()) {
                i4 = 0;
            }
            Iterator it6 = allRunningPackagePids.iterator();
            boolean z5 = false;
            while (true) {
                if (!it6.hasNext()) {
                    arrayList3 = arrayList5;
                    j = clearCallingIdentity;
                    i2 = i4;
                    break;
                }
                int intValue2 = ((Integer) it6.next()).intValue();
                Integer valueOf2 = Integer.valueOf(intValue2);
                if (z2) {
                    if (this.mOLAFOn) {
                        it = it6;
                    } else {
                        String str5 = TAG;
                        StringBuilder sb3 = new StringBuilder();
                        it = it6;
                        sb3.append("FZ : ");
                        sb3.append(str);
                        sb3.append("(");
                        sb3.append(intValue2);
                        sb3.append(")");
                        Slog.d(str5, sb3.toString());
                    }
                    synchronized (this.mFrozenPidList) {
                        try {
                            if (!this.mFrozenPidList.contains(valueOf2)) {
                                this.mFrozenPidList.add(valueOf2);
                                if (this.mAm.mBooted && (processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(intValue2)) != null) {
                                    arrayList3 = arrayList5;
                                    j = clearCallingIdentity;
                                    try {
                                        PerProcessNandswap.getInstance().onProcessFrozen(processRecordFromPidLocked.getPid(), processRecordFromPidLocked.processName, processRecordFromPidLocked.mState.getCurAdj(), processRecordFromPidLocked.hasActivities());
                                        if (IS_PILOT_PROJECT) {
                                            synchronized (this.mMapFrozenUidPidList) {
                                                if (!this.mMapFrozenUidPidList.containsKey(Integer.valueOf(i))) {
                                                    this.mMapFrozenUidPidList.put(Integer.valueOf(i), new ArrayList());
                                                }
                                                ArrayList arrayList6 = (ArrayList) this.mMapFrozenUidPidList.get(Integer.valueOf(i));
                                                if (!arrayList6.contains(Integer.valueOf(intValue2))) {
                                                    arrayList6.add(Integer.valueOf(intValue2));
                                                }
                                            }
                                        }
                                        z4 = false;
                                    } catch (Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                }
                            }
                            arrayList3 = arrayList5;
                            j = clearCallingIdentity;
                            if (IS_PILOT_PROJECT) {
                            }
                            z4 = false;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                } else {
                    it = it6;
                    arrayList3 = arrayList5;
                    j = clearCallingIdentity;
                    if (!this.mOLAFOn) {
                        Slog.d(TAG, "UFZ : " + str + "(" + intValue2 + ")");
                    }
                    synchronized (this.mFrozenPidList) {
                        if (this.mFrozenPidList.contains(valueOf2)) {
                            this.mFrozenPidList.remove(valueOf2);
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                    }
                    ProcessRecord processRecord2 = (ProcessRecord) this.mMapFrozenProcRecord.get(Integer.valueOf(intValue2));
                    if (processRecord2 != null) {
                        processRecord2.frozenMARs = false;
                        processRecord2.freezeUnfreezeTimeMARs = uptimeMillis;
                        this.mMapFrozenProcRecord.remove(Integer.valueOf(intValue2));
                    }
                }
                if (this.mCustomFreqManager == null) {
                    try {
                        this.mCustomFreqManager = (CustomFrequencyManager) this.mContext.getSystemService("CustomFrequencyManagerService");
                    } catch (Throwable th3) {
                        th = th3;
                        Binder.restoreCallingIdentity(j);
                        throw th;
                    }
                }
                i2 = this.mCustomFreqManager.requestFreezeSlowdown(intValue2, z2, "freeze");
                if (i2 == -1) {
                    Slog.d(TAG, "Fail to set Freeze. uid=" + i + ", pid=" + intValue2 + ", isFreeze=" + z2);
                    if (z2) {
                        this.cntFailFreeze++;
                        String str6 = "cntFailFreeze=" + this.cntFailFreeze + ", uid=" + i + ", fpid=" + intValue2 + " (pids";
                        Iterator it7 = allRunningPackagePids.iterator();
                        while (it7.hasNext()) {
                            str6 = str6 + " " + ((Integer) it7.next()).intValue();
                        }
                        MARsPolicyManager.getInstance().addDebugInfoToHistory("DEV", str6 + ")");
                    } else {
                        this.cntFailUnfreeze++;
                        String str7 = "cntFailUnfreeze=" + this.cntFailUnfreeze + ", uid=" + i + ", fpid=" + intValue2 + " (pids";
                        Iterator it8 = allRunningPackagePids.iterator();
                        while (it8.hasNext()) {
                            str7 = str7 + " " + ((Integer) it8.next()).intValue();
                        }
                        MARsPolicyManager.getInstance().addDebugInfoToHistory("DEV", str7 + ") hasPidInFrozenPidList=" + z4);
                        if (z4 && !z5) {
                            z5 = true;
                        }
                    }
                }
                it6 = it;
                i4 = i2;
                clearCallingIdentity = j;
                arrayList5 = arrayList3;
            }
            Binder.restoreCallingIdentity(j);
            onFreezeChanged(z2, allRunningPackagePids);
            if (!z2 || arrayList3.isEmpty()) {
                z3 = true;
            } else {
                Iterator it9 = arrayList3.iterator();
                while (it9.hasNext()) {
                    ProcessRecord processRecord3 = (ProcessRecord) it9.next();
                    processRecord3.frozenMARs = true;
                    processRecord3.freezeUnfreezeTimeMARs = uptimeMillis;
                    this.mMapFrozenProcRecord.put(Integer.valueOf(processRecord3.getPid()), processRecord3);
                }
                z3 = true;
                FreecessHandler.getInstance().sendProcPostMonitoringMsg();
            }
            if (z5 || i2 == -1) {
                return false;
            }
            return z3;
        } catch (Throwable th4) {
            th = th4;
            j = clearCallingIdentity;
        }
    }

    public void onFreezeChanged(boolean z, ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        FreecessHandler.getInstance().sendReportToBroadcastQueueMsg(arrayList);
    }

    public void postMonitoringFrozenProcs() {
        long uptimeMillis = SystemClock.uptimeMillis();
        int i = 0;
        for (ProcessRecord processRecord : this.mMapFrozenProcRecord.values()) {
            if (!processRecord.frozenMARs) {
                this.mMapFrozenProcRecord.remove(Integer.valueOf(processRecord.getPid()));
            } else if (uptimeMillis - processRecord.freezeUnfreezeTimeMARs < 2500) {
                i++;
                int reasonProcShouldNotBeFrozen = this.mAm.getReasonProcShouldNotBeFrozen(processRecord);
                if (reasonProcShouldNotBeFrozen > 0) {
                    switch (reasonProcShouldNotBeFrozen) {
                        case 1:
                            unFreezePackage(processRecord.uid, "AlreadyDied");
                            break;
                        case 2:
                            unFreezePackage(processRecord.uid, "ExecutingService");
                            break;
                        case 3:
                            unFreezePackage(processRecord.uid, "ReceivingIntent");
                            break;
                        case 4:
                            unFreezePackage(processRecord.uid, "LaunchingProvider");
                            break;
                        case 5:
                            unFreezePackage(processRecord.uid, "StartProcess");
                            break;
                        case 6:
                            unFreezePackage(processRecord.uid, "ForegroundAdj");
                            break;
                        case 7:
                            unFreezePackage(processRecord.uid, "RunningLogcat");
                            break;
                        default:
                            unFreezePackage(processRecord.uid, "Post-Monitoring");
                            break;
                    }
                }
            }
        }
        if (i > 0) {
            FreecessHandler.getInstance().sendProcPostMonitoringMsg();
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "postMonitoringFrozenProcs() numProcessNeedToCheck is " + i);
        }
    }

    public void releaseFreezedAppPid(int i) {
        boolean z;
        Integer valueOf = Integer.valueOf(i);
        synchronized (this.mFrozenPidList) {
            if (this.mFrozenPidList.contains(valueOf)) {
                this.mFrozenPidList.remove(valueOf);
                z = true;
            } else {
                z = false;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mCustomFreqManager == null) {
                this.mCustomFreqManager = (CustomFrequencyManager) this.mContext.getSystemService("CustomFrequencyManagerService");
            }
            CustomFrequencyManager customFrequencyManager = this.mCustomFreqManager;
            if (customFrequencyManager != null && z) {
                if (customFrequencyManager.requestFreezeSlowdown(i, false, "freeze") == -1) {
                    Slog.d(TAG, "UFZ error : pid " + i + " (when app died or watchdog half)");
                } else {
                    Slog.d(TAG, "UFZ : release pid " + i + " (when app died or watchdog half)");
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00d8 A[Catch: all -> 0x010e, TryCatch #3 {all -> 0x010e, blocks: (B:36:0x00d4, B:38:0x00d8, B:39:0x00e4), top: B:35:0x00d4 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean writeDevFile(String str, int i, int i2) {
        boolean z;
        ProcessRecord processRecordFromPidLocked;
        long clearCallingIdentity;
        ProcessRecord processRecordFromPidLocked2;
        new File(str);
        long uptimeMillis = SystemClock.uptimeMillis();
        Integer valueOf = Integer.valueOf(i2);
        try {
            if ("/dev/freezer/thaw/cgroup.procs".equals(str)) {
                synchronized (this.mFrozenPidList) {
                    if (this.mFrozenPidList.contains(valueOf)) {
                        this.mFrozenPidList.remove(valueOf);
                    }
                }
                if (IS_PILOT_PROJECT) {
                    synchronized (this.mFrozenPidList) {
                        if (this.mMapFrozenUidPidList.containsKey(Integer.valueOf(i))) {
                            ArrayList arrayList = (ArrayList) this.mMapFrozenUidPidList.get(Integer.valueOf(i));
                            if (arrayList.contains(Integer.valueOf(i2))) {
                                if (arrayList.size() > 1) {
                                    arrayList.remove(Integer.valueOf(i2));
                                } else {
                                    this.mMapFrozenUidPidList.remove(Integer.valueOf(i));
                                }
                            }
                        }
                    }
                }
                ProcessRecord processRecord = (ProcessRecord) this.mMapFrozenProcRecord.get(Integer.valueOf(i2));
                if (processRecord != null) {
                    processRecord.frozenMARs = false;
                    processRecord.freezeUnfreezeTimeMARs = uptimeMillis;
                    this.mMapFrozenProcRecord.remove(Integer.valueOf(i2));
                }
            } else if ("/dev/freezer/frozen/cgroup.procs".equals(str)) {
                synchronized (this.mFrozenPidList) {
                    if (!this.mFrozenPidList.contains(valueOf)) {
                        this.mFrozenPidList.add(valueOf);
                        if (this.mAm.mBooted && (processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(i2)) != null) {
                            PerProcessNandswap.getInstance().onProcessFrozen(processRecordFromPidLocked.getPid(), processRecordFromPidLocked.processName, processRecordFromPidLocked.mState.getCurAdj(), processRecordFromPidLocked.hasActivities());
                        }
                    }
                }
                z = true;
                clearCallingIdentity = Binder.clearCallingIdentity();
                if (this.mCustomFreqManager == null) {
                    this.mCustomFreqManager = (CustomFrequencyManager) this.mContext.getSystemService("CustomFrequencyManagerService");
                }
                int requestFreezeSlowdown = this.mCustomFreqManager.requestFreezeSlowdown(i2, z, "freeze");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (z && requestFreezeSlowdown != -1 && (processRecordFromPidLocked2 = this.mAm.getProcessRecordFromPidLocked(i2)) != null) {
                    processRecordFromPidLocked2.frozenMARs = true;
                    processRecordFromPidLocked2.freezeUnfreezeTimeMARs = uptimeMillis;
                    this.mMapFrozenProcRecord.put(Integer.valueOf(i2), processRecordFromPidLocked2);
                }
                return requestFreezeSlowdown == -1;
            }
            if (this.mCustomFreqManager == null) {
            }
            int requestFreezeSlowdown2 = this.mCustomFreqManager.requestFreezeSlowdown(i2, z, "freeze");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (z) {
                processRecordFromPidLocked2.frozenMARs = true;
                processRecordFromPidLocked2.freezeUnfreezeTimeMARs = uptimeMillis;
                this.mMapFrozenProcRecord.put(Integer.valueOf(i2), processRecordFromPidLocked2);
            }
            if (requestFreezeSlowdown2 == -1) {
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        z = false;
        clearCallingIdentity = Binder.clearCallingIdentity();
    }

    public final boolean sendFreecessSignal(String str, int i, ArrayList arrayList, int i2) {
        return sendFreecessSignal(str, i, arrayList, i2, false, null);
    }

    public boolean sendFreecessSignal(String str, int i, ArrayList arrayList, int i2, boolean z, checkResultCallback checkresultcallback) {
        if (i2 == 1) {
            return writeDevFile(str, i, arrayList, "/dev/freezer/frozen/cgroup.procs", z, checkresultcallback);
        }
        if (i2 != 2) {
            return false;
        }
        boolean writeDevFile = writeDevFile(str, i, arrayList, "/dev/freezer/thaw/cgroup.procs", z, checkresultcallback);
        releaseRestriction(i);
        return writeDevFile;
    }

    public final boolean sendFreecessSignal(int i, int i2, int i3) {
        if (i == 1) {
            return writeDevFile("/dev/freezer/frozen/cgroup.procs", i2, i3);
        }
        if (i != 2) {
            return false;
        }
        return writeDevFile("/dev/freezer/thaw/cgroup.procs", i2, i3);
    }

    public void updateAllowListForFreecess(FreecessPkgStatus freecessPkgStatus) {
        if (freecessPkgStatus == null) {
            return;
        }
        if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
            synchronized (this.mFreezeExcludePackages) {
                if (this.mFreezeExcludePackages.contains(freecessPkgStatus.name)) {
                    freecessPkgStatus.isInAllowList = true;
                }
            }
        }
        if (this.mSsrmAllowPackages.contains(freecessPkgStatus.name)) {
            freecessPkgStatus.isInAllowList = true;
        }
        if (this.mOLAFAllowPackages.contains(freecessPkgStatus.name)) {
            freecessPkgStatus.isOlafAllowList = true;
        }
    }

    public void updateRunningLocationPackages() {
        Map gPSUsingApps;
        List list = this.mGPSAllowList;
        if (list != null) {
            list.clear();
        }
        try {
            if (this.mLocationManager == null) {
                this.mLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
            }
            ILocationManager iLocationManager = this.mLocationManager;
            if (iLocationManager == null || (gPSUsingApps = iLocationManager.getGPSUsingApps()) == null) {
                return;
            }
            Iterator it = gPSUsingApps.keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (intValue == 1) {
                    this.mGPSAllowList = (List) gPSUsingApps.get(Integer.valueOf(intValue));
                }
            }
        } catch (RemoteException unused) {
            Slog.e(TAG, "failed to updateRunningLocationPackages!");
        }
    }

    public boolean isInGPSAllowList(int i) {
        Integer valueOf = Integer.valueOf(i);
        List list = this.mGPSAllowList;
        return list != null && list.contains(valueOf);
    }

    public boolean isInFreecessExcludeList(FreecessPkgStatus freecessPkgStatus) {
        return isInFreecessExcludeList(freecessPkgStatus, false);
    }

    public boolean isInFreecessExcludeList(FreecessPkgStatus freecessPkgStatus, boolean z) {
        if (freecessPkgStatus != null) {
            if (freecessPkgStatus.targetSdkVersion >= 34 && freecessPkgStatus.serviceTypes != 0) {
                if (!IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE || !MARsPolicyManager.getInstance().checkIsChinaModel() || !z) {
                    return true;
                }
                Slog.w(TAG, freecessPkgStatus.name + " china model lcd on , freeze fgs app exclude important task. ");
                return false;
            }
            if (!freecessPkgStatus.isInAllowList && UserHandle.isApp(freecessPkgStatus.uid) && freecessPkgStatus.sharedUidName == null) {
                if (freecessPkgStatus.isDoingGC != 0) {
                    Slog.w(TAG, freecessPkgStatus.name + " is doing GC, skip to freeze it.");
                    return true;
                }
                if (BlueToothConnectedFilter.getInstance().isInBTAllowList(freecessPkgStatus.uid)) {
                }
            }
            return true;
        }
        return false;
    }

    public boolean isInFreecessExcludeList(MARsPackageInfo mARsPackageInfo) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(mARsPackageInfo.getUid());
            if (packageStatus == null) {
                return false;
            }
            return isInFreecessExcludeList(packageStatus);
        }
    }

    public boolean isImportantFGSTypeBySys(FreecessPkgStatus freecessPkgStatus) {
        if (freecessPkgStatus == null || (1073749537 & freecessPkgStatus.serviceTypes) == 0) {
            return false;
        }
        Slog.i(TAG, freecessPkgStatus.name + " has important fgs type according Google fg Api , skip to freeze it. ");
        return true;
    }

    public final boolean checkFgsPkgSkipToFreeze(FreecessPkgStatus freecessPkgStatus) {
        boolean isForegroundServicePkg = MARsPolicyManager.getInstance().isForegroundServicePkg(freecessPkgStatus.uid);
        if (!MARsPolicyManager.getInstance().checkIsChinaModel()) {
            if (!isForegroundServicePkg) {
                return false;
            }
            Slog.d(TAG, freecessPkgStatus.name + "(" + freecessPkgStatus.uid + ") is important[fg-service]");
            return true;
        }
        if (!isForegroundServicePkg) {
            return false;
        }
        if (IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE && !isImportantFGSTypeBySys(freecessPkgStatus)) {
            return false;
        }
        Slog.d(TAG, freecessPkgStatus.name + "(" + freecessPkgStatus.uid + ") is important[fg-service]");
        return true;
    }

    public final boolean checkImportantPackage(FreecessPkgStatus freecessPkgStatus) {
        if (!isQuickFreezeEnabled() && !UidStateMgr.getInstance().isUidIdle(freecessPkgStatus.uid)) {
            Slog.d(TAG, freecessPkgStatus.name + "(" + freecessPkgStatus.uid + ") is important[!isUidIdle]");
            return true;
        }
        synchronized (MARsPolicyManager.MARsLock) {
            if (isInFreecessExcludeList(freecessPkgStatus, true)) {
                return true;
            }
            String str = freecessPkgStatus.name;
            int i = freecessPkgStatus.userId;
            int i2 = freecessPkgStatus.uid;
            int filterForSpecificPolicy = FilterManager.getInstance().filterForSpecificPolicy(15, str, i, i2);
            if (filterForSpecificPolicy == 0) {
                return false;
            }
            Slog.d(TAG, str + "(" + i2 + ") is important[" + filterForSpecificPolicy + "]");
            return true;
        }
    }

    public void updateFreezedUidFirewall(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        if (iNetworkManagementService != null) {
            try {
                try {
                    if (z) {
                        iNetworkManagementService.setFirewallUidRule(7, i, 1);
                        MARsPolicyManager.getInstance().addNetDebugInfoToHistory("NET", "[UFZ] " + i + ",7,1");
                    } else {
                        iNetworkManagementService.setFirewallUidRule(7, i, 2);
                        MARsPolicyManager.getInstance().addNetDebugInfoToHistory("NET", "[FRZ] " + i + ",7,2");
                    }
                } catch (Exception e) {
                    Slog.e(TAG, "Error occured while updateFreezedUidFirewall: " + e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void updateAbnormalAppFirewall(int i, boolean z) {
        Slog.d(TAG, "updateAbnormalAppFirewall uid " + i + ", allow " + z);
        updateFreezedUidFirewall(i, z);
    }

    public void closeSocketsForFreecessFirewallChain() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        try {
            if (iNetworkManagementService != null) {
                try {
                    iNetworkManagementService.closeSocketsForFreecess(7, "fw_oem_deny_1");
                } catch (Exception e) {
                    Slog.e(TAG, "Error occured while closeSocketsForFreecessFirewallChain: " + e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setFirewallChainEnabled(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mNMs == null) {
            this.mNMs = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        }
        INetworkManagementService iNetworkManagementService = this.mNMs;
        try {
            if (iNetworkManagementService != null) {
                try {
                    iNetworkManagementService.setFirewallChainEnabled(i, z);
                } catch (Exception e) {
                    Slog.e(TAG, "Error occured while setFirewallChainEnabled: " + e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateFreezedStatusByFreezeType(FreecessPkgStatus freecessPkgStatus, boolean z) {
        int i = freecessPkgStatus.freezedType;
        if (i == 1) {
            freecessPkgStatus.freezedRecord.isOLAFFreezed = z;
        } else if (i == 2) {
            freecessPkgStatus.freezedRecord.isLcdOffFreezed = z;
        } else {
            if (i != 3) {
                return;
            }
            freecessPkgStatus.freezedRecord.isLcdOnFreezed = z;
        }
    }

    public void checkFrozenBinder(int i) {
        if (sendFreecessMsg2kernel(4, i, 0, 0) < 0) {
            unFreezePackage("FBException");
        }
    }

    public void configPacketMonitoredUid(int i, String str, int i2) {
        if (sendFreecessMsg2kernel(3, -1, 0, i) > 0) {
            synchronized (MARsPolicyManager.MARsLock) {
                ArrayList arrayList = this.mMonitorFreezedList;
                if (arrayList != null && !arrayList.contains(Integer.valueOf(i))) {
                    this.mMonitorFreezedList.add(Integer.valueOf(i));
                }
            }
            return;
        }
        unFreezePackage(str, i2, "RegException");
    }

    public void deletePacketMonitoredUid(int i) {
        if (sendFreecessMsg2kernel(3, -1, 1, i) > 0) {
            synchronized (MARsPolicyManager.MARsLock) {
                ArrayList arrayList = this.mMonitorFreezedList;
                if (arrayList != null && arrayList.contains(Integer.valueOf(i))) {
                    this.mMonitorFreezedList.remove(Integer.valueOf(i));
                }
            }
            return;
        }
        Slog.e(TAG, "deletePacketMonitoredUid Exception");
    }

    public final void cleanPacketMonitoredUids() {
        if (sendFreecessMsg2kernel(3, -1, 2, -1) > 0) {
            synchronized (MARsPolicyManager.MARsLock) {
                ArrayList arrayList = this.mMonitorFreezedList;
                if (arrayList != null) {
                    arrayList.clear();
                }
            }
            return;
        }
        Slog.e(TAG, "cleanPacketMonitoredUids Exception");
    }

    public final void reportSignal(int i, int i2) {
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d(TAG, "Receive signal-module info(uid: " + i + ",pid: " + i2 + ") from kernel");
        }
        boolean z = false;
        if (IS_PILOT_PROJECT) {
            synchronized (this.mFrozenPidList) {
                if (this.mMapFrozenUidPidList.containsKey(Integer.valueOf(i))) {
                    ArrayList arrayList = (ArrayList) this.mMapFrozenUidPidList.get(Integer.valueOf(i));
                    if (arrayList.contains(Integer.valueOf(i2))) {
                        if (arrayList.size() > 1) {
                            z = true;
                        }
                    } else {
                        Slog.e(TAG, "Pid ufz error: Process id=" + i2 + " is not frozen or doesn't belong to uid=" + i);
                    }
                }
            }
        } else {
            ArrayList allRunningPackagePids = getAllRunningPackagePids("", i, false);
            if (i2 > 0 && allRunningPackagePids.size() > 1 && allRunningPackagePids.contains(Integer.valueOf(i2))) {
                z = true;
            }
        }
        if (isPidUfzEnabled() && z) {
            unFreezePackageForProc(i, i2, "Signal");
        } else {
            protectFreezePackage(i, "Signal", 1000L);
        }
    }

    public boolean isPacketMonitoredApp(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            ArrayList arrayList = this.mMonitorFreezedList;
            return arrayList != null && arrayList.contains(Integer.valueOf(i));
        }
    }

    public void reportAsyncBinder(int i, int i2, int i3, int i4, String str, int i5) {
        int i6;
        boolean z;
        String str2 = "";
        if (str != null) {
            synchronized (MARsPolicyManager.MARsLock) {
                FreecessPkgStatus packageStatus = getPackageStatus(i2);
                i6 = 0;
                z = packageStatus != null;
                if (z) {
                    str2 = packageStatus.name;
                    i6 = packageStatus.userId;
                }
            }
            if (("free_buffer_full".equals(str) && i4 == -1) || "mismatch".equals(str)) {
                if (killPkgForCalmMode(i2, "Binder(1)-")) {
                    return;
                }
                if (z && MARsPolicyManager.getInstance().isMARsTarget(str2, i6)) {
                    unFreezePackage(i2, "Binder(1)-" + str);
                    return;
                }
                ProcessRecord processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(i3);
                if (processRecordFromPidLocked != null) {
                    ActivityManagerService activityManagerService = this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            if (freezeTargetProcess(processRecordFromPidLocked.info != null ? processRecordFromPidLocked.info.packageName : null, processRecordFromPidLocked.uid)) {
                                Slog.d(TAG, "u=" + i2 + " is not mars target, try google freezer ufz p=" + i3);
                                this.mAm.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(processRecordFromPidLocked, 25);
                            }
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e(TAG, "ProcessRecord of p=" + i3 + " not found or not target of FAS.");
                return;
            }
            if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(9, null, null, str)) {
                if (MARsPolicyManager.getInstance().checkIsChinaModel() && str.equals("android.media.IAudioPolicyServiceClient")) {
                    if (((AudioManager) this.mContext.getSystemService("audio")).semGetAudioFocusedPackageName().equals(str2)) {
                        unFreezePackage(i2, "Binder(1)- focus audio" + str);
                        return;
                    }
                    return;
                }
                unFreezePackage(i2, "Binder(1)-" + str);
                return;
            }
            if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(17, null, null, str)) {
                if (UidStateMgr.getInstance().isUidForegroundService(i2)) {
                    unFreezePackage(i2, "Binder(1)-" + str);
                    return;
                }
            } else if (z && MARsVersionManager.getInstance().isAdjustRestrictionMatch(28, str2, null, str)) {
                unFreezePackage(i2, "Binder(1)-" + str);
                return;
            }
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            MARsPolicyManager.getInstance().addDebugInfoToHistory("ABI", i + " " + i2 + " " + str + " " + i4 + " " + i5);
        }
    }

    public boolean protectFreezePackage(String str, int i, String str2, long j) {
        boolean z;
        boolean z2;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(str, i);
            if (packageStatus != null) {
                packageStatus.updateProtectionTime(j);
                if (packageStatus.freezedRecord.isFreezed) {
                    z2 = true;
                    z = unFreezeAction(packageStatus, str2, true);
                }
            }
            z = false;
            z2 = false;
        }
        if (z) {
            MARsPolicyManager.getInstance().reportStatusWithMARs(str, i, str2, false);
        }
        return z2;
    }

    public boolean protectFreezePackage(int i, String str, long j) {
        FreecessPkgStatus packageStatus;
        boolean z;
        boolean z2;
        synchronized (MARsPolicyManager.MARsLock) {
            packageStatus = getPackageStatus(i);
            if (packageStatus != null) {
                packageStatus.updateProtectionTime(j);
                if (packageStatus.freezedRecord.isFreezed) {
                    z2 = true;
                    z = unFreezeAction(packageStatus, str, true);
                }
            }
            z = false;
            z2 = false;
        }
        if (z) {
            MARsPolicyManager.getInstance().reportStatusWithMARs(packageStatus.name, packageStatus.userId, str, false);
        }
        return z2;
    }

    public boolean isFreezedPackage(String str, int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(str, i);
            return frozenPackageStatus != null && frozenPackageStatus.freezedRecord.isFreezed;
        }
    }

    public boolean isFreezedPackage(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i);
            return frozenPackageStatus != null && frozenPackageStatus.freezedRecord.isFreezed;
        }
    }

    public boolean isFreezedByLcdOnPolicy(String str, int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(str, i);
            if (frozenPackageStatus != null) {
                MARsFreezeStateRecord mARsFreezeStateRecord = frozenPackageStatus.freezedRecord;
                if (mARsFreezeStateRecord.isFreezed && (mARsFreezeStateRecord.isLcdOnFreezed || mARsFreezeStateRecord.isOLAFFreezed)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean matchFreezeState(String str, int i, Boolean bool, Boolean bool2, Boolean bool3) {
        boolean matchFreezeStateLocked;
        synchronized (MARsPolicyManager.MARsLock) {
            matchFreezeStateLocked = matchFreezeStateLocked(getFrozenPackageStatus(str, i), bool, bool2, bool3);
        }
        return matchFreezeStateLocked;
    }

    public final boolean matchFreezeStateLocked(FreecessPkgStatus freecessPkgStatus, Boolean bool, Boolean bool2, Boolean bool3) {
        if (freecessPkgStatus == null) {
            return false;
        }
        if (bool != null && bool.booleanValue() != freecessPkgStatus.freezedRecord.isFreezed) {
            return false;
        }
        if (bool2 == null || bool2.booleanValue() == freecessPkgStatus.freezedRecord.isLcdOnFreezed) {
            return bool3 == null || bool3.booleanValue() == freecessPkgStatus.freezedRecord.isOLAFFreezed;
        }
        return false;
    }

    public void onUnfreeze(String str, int i, int i2, boolean z, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            FreecessHandler.getInstance().sendUpdateBTMsg(27, i2);
            if (z2) {
                updateFreezedUidFirewall(i2, true);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            FreecessHandler.getInstance().sendFreecessSettlementMsg(str, i, i2, z ? 0 : -1, 0, false, false, true);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isReceivingTouchEvent(int i) {
        InputManagerService inputManagerService = this.mInputManagerService;
        if (inputManagerService != null) {
            return inputManagerService.isUidTouched(i);
        }
        return false;
    }

    public boolean getMonitorPacketFlag(int i) {
        if (MARsPolicyManager.getInstance().isChinaPolicyEnabled() && i == 1) {
            return true;
        }
        return (MARsPolicyManager.getInstance().isChinaPolicyEnabled() || getFreecessEnhancementEnabledState()) ? false : true;
    }

    public boolean getRestrictNetworkFlag(int i) {
        return getFreecessEnhancementEnabledState() && ((MARsPolicyManager.getInstance().isChinaPolicyEnabled() && i == 0) || !MARsPolicyManager.getInstance().isChinaPolicyEnabled());
    }

    public void freezeBgPackageLocked(String str, int i) {
        if (this.mIsFreecessEnable) {
            boolean monitorPacketFlag = getMonitorPacketFlag(MARsPolicyManager.getInstance().getAutorunForFreezedPackage(str, UserHandle.getUserId(i)));
            boolean restrictNetworkFlag = getRestrictNetworkFlag(MARsPolicyManager.getInstance().getAutorunForFreezedPackage(str, UserHandle.getUserId(i)));
            WindowManagerService windowManagerService = this.mAm.mWindowManager;
            boolean z = windowManagerService != null && windowManagerService.hasFloatingOrOnScreenWindow(i);
            boolean isReceivingTouchEvent = isReceivingTouchEvent(i);
            boolean isPendingBroadcastPackageLocked = this.mAm.isPendingBroadcastPackageLocked(i);
            synchronized (MARsPolicyManager.MARsLock) {
                FreecessPkgStatus packageStatus = getPackageStatus(str, UserHandle.getUserId(i));
                if (packageStatus == null) {
                    return;
                }
                if (checkFgsPkgSkipToFreeze(packageStatus)) {
                    Slog.d(TAG, str + " has important Fg-service , skip to freeze");
                    packageStatus.freezedState = 1;
                    return;
                }
                if (!"com.samsung.android.spay".equals(packageStatus.name) && !"com.samsung.android.app.sreminder".equals(packageStatus.name)) {
                    if (isQuickFreezeEnabled()) {
                        MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                        if (mARsFreezeStateRecord.isFreezed && mARsFreezeStateRecord.isOLAFFreezed) {
                            overrideOlafForBgFreeze(packageStatus);
                            Slog.d(TAG, str + " is OLAF FZed, override it with bg fz.");
                            return;
                        }
                    }
                    if (packageStatus.freezedRecord.isFreezed) {
                        return;
                    }
                    if (!MARsPolicyManager.getInstance().isMARsTarget(packageStatus.name, packageStatus.userId)) {
                        Slog.d(TAG, str + " is not MARs target, skip to freeze");
                        packageStatus.freezedState = 1;
                        return;
                    }
                    if (z) {
                        Slog.d(TAG, str + " has floating or onScreen window, skip to freeze");
                        packageStatus.freezedState = 1;
                        return;
                    }
                    if (isReceivingTouchEvent) {
                        Slog.d(TAG, str + " has Touch Event, skip to freeze");
                        packageStatus.freezedState = 1;
                        return;
                    }
                    if (isPendingBroadcastPackageLocked) {
                        Slog.d(TAG, str + " has pending broadcast, skip to freeze");
                        packageStatus.freezedState = 1;
                        return;
                    }
                    boolean freezeAction = freezeAction(packageStatus, "Bg", 3, monitorPacketFlag, restrictNetworkFlag);
                    if (freezeAction) {
                        MARsFreezeStateRecord mARsFreezeStateRecord2 = packageStatus.freezedRecord;
                        mARsFreezeStateRecord2.isLcdOnFreezed = true;
                        mARsFreezeStateRecord2.freezedTime = System.currentTimeMillis();
                    } else {
                        packageStatus.freezedRecord.isLcdOnFreezed = false;
                        packageStatus.freezedState = 1;
                        FreecessHandler.getInstance().sendUidLcdOnQuickFzMsg(packageStatus.uid, packageStatus.name, isQuickFreezeEnabled() || this.mUidIdleCheck);
                    }
                    if (freezeAction) {
                        MARsPolicyManager.getInstance().addDebugInfoToHistory("FRZ", "Bg " + str + " " + i);
                        return;
                    }
                    return;
                }
                packageStatus.freezedState = 1;
            }
        }
    }

    public boolean shouldDropServiceRestart(String str, int i) {
        boolean z = false;
        if (!MARsPolicyManager.getInstance().checkIsChinaModel() || MARsPolicyManager.getInstance().isAutoRunBlockedApp(str, i)) {
            synchronized (MARsPolicyManager.MARsLock) {
                FreecessPkgStatus packageStatus = getPackageStatus(str, i);
                if (packageStatus != null) {
                    MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                    if (!mARsFreezeStateRecord.isFreezed && "Signal".equals(mARsFreezeStateRecord.unfreezedReason)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public boolean isPackageRestricted(String str, int i) {
        boolean z = false;
        if (!MARsPolicyManager.getInstance().checkIsChinaModel() || MARsPolicyManager.getInstance().isAutoRunBlockedApp(str, i)) {
            synchronized (MARsPolicyManager.MARsLock) {
                FreecessPkgStatus packageStatus = getPackageStatus(str, i);
                if (packageStatus != null) {
                    MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                    if (mARsFreezeStateRecord.isFreezed || "Signal".equals(mARsFreezeStateRecord.unfreezedReason)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public boolean isPackageFreezed(String str, int i) {
        if (!MARsPolicyManager.getInstance().checkIsChinaModel() || MARsPolicyManager.getInstance().isAutoRunBlockedApp(str, i)) {
            synchronized (MARsPolicyManager.MARsLock) {
                FreecessPkgStatus packageStatus = getPackageStatus(str, i);
                r1 = packageStatus != null ? packageStatus.freezedRecord.isFreezed : false;
            }
        }
        return r1;
    }

    public boolean freezePackage(String str, int i, String str2, int i2, boolean z, boolean z2) {
        boolean z3 = false;
        if (!this.mIsFreecessEnable) {
            return false;
        }
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(str, i);
            if (packageStatus != null && !packageStatus.freezedRecord.isFreezed && !isInFreecessExcludeList(packageStatus)) {
                z3 = freezeAction(packageStatus, str2, i2, z, z2);
            }
        }
        if (z3) {
            MARsPolicyManager.getInstance().reportStatusWithMARs(str, i, str2, true);
        }
        return z3;
    }

    public boolean freezePackage(int i, String str, int i2, boolean z, boolean z2) {
        String str2;
        int i3;
        boolean z3 = false;
        if (!this.mIsFreecessEnable) {
            return false;
        }
        boolean isReceivingTouchEvent = isReceivingTouchEvent(i);
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(i);
            if (packageStatus == null || packageStatus.freezedRecord.isFreezed) {
                str2 = null;
                i3 = 0;
            } else {
                if (isReceivingTouchEvent) {
                    Slog.d(TAG, packageStatus.name + " has Touch Event, skip to freeze");
                    packageStatus.freezedState = 1;
                    return false;
                }
                str2 = packageStatus.name;
                i3 = packageStatus.userId;
                if (!isInFreecessExcludeList(packageStatus)) {
                    z3 = freezeAction(packageStatus, str, i2, z, z2);
                }
            }
            if (z3 && str2 != null) {
                MARsPolicyManager.getInstance().reportStatusWithMARs(str2, i3, str, true);
            }
            return z3;
        }
    }

    public void unFreezePackage(String str, int i, String str2) {
        boolean unFreezeAction;
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(str, i);
            unFreezeAction = (frozenPackageStatus == null || !frozenPackageStatus.freezedRecord.isFreezed) ? false : unFreezeAction(frozenPackageStatus, str2, true);
        }
        if (unFreezeAction) {
            MARsPolicyManager.getInstance().reportStatusWithMARs(str, i, str2, false);
        }
    }

    public void unFreezePackage(int i, String str) {
        String str2;
        boolean z;
        int i2;
        synchronized (MARsPolicyManager.MARsLock) {
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
        }
        if (!z || str2 == null) {
            return;
        }
        MARsPolicyManager.getInstance().reportStatusWithMARs(str2, i2, str, false);
    }

    public void unFreezePackage(String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray clone = this.mFreezedPackages.getUidMap().clone();
            for (int i = 0; i < clone.size(); i++) {
                unFreezeAction((FreecessPkgStatus) clone.valueAt(i), str, false);
            }
        }
    }

    public void unFreezeActivePackages(String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray clone = this.mFreezedPackages.getUidMap().clone();
            for (int i = 0; i < clone.size(); i++) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) clone.valueAt(i);
                if (freecessPkgStatus.freezedRecord.isLcdOffFreezed && !UidStateMgr.getInstance().isUidIdle(freecessPkgStatus.uid)) {
                    unFreezeAction(freecessPkgStatus, str, false);
                }
            }
        }
    }

    public void unFreezeWidgetPackages(String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray clone = this.mFreezedPackages.getUidMap().clone();
            for (int i = 0; i < clone.size(); i++) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) clone.valueAt(i);
                if (WidgetPkgFilter.getInstance().filter(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, 4) > 0) {
                    unFreezeAction(freecessPkgStatus, str, false);
                }
            }
        }
    }

    public void unFreezeSpecialPackage(int i, String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(i);
            if (packageStatus == null) {
                return;
            }
            MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
            if (mARsFreezeStateRecord != null && mARsFreezeStateRecord.isFreezed) {
                unFreezeAction(packageStatus, str, false);
            }
            if (MARsPolicyManager.getInstance().isMARsTarget(packageStatus.name, packageStatus.userId)) {
                if (this.mScreenOn) {
                    packageStatus.freezedState = 1;
                    FreecessHandler.getInstance().removeBgTriggerMsgByObj(2, packageStatus.name);
                    FreecessHandler.getInstance().removeBgTriggerMsgByObj(3, packageStatus.name);
                    FreecessHandler.getInstance().removeBgTriggerMsgByObj(4, packageStatus.name);
                    FreecessHandler.getInstance().removeBgTriggerMsgByObj(28, packageStatus.name);
                    stepLcdOnFreezedState(packageStatus.freezedState, 1, str, packageStatus);
                }
            }
        }
    }

    public void unFreezePackageForProc(int i, int i2, String str) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i);
            if (isPidUfzEnabled()) {
                unFreezeActionForPid(frozenPackageStatus, str, i, i2);
            } else {
                unFreezeAction(frozenPackageStatus, str, false);
            }
        }
    }

    public void unRestrictPackage(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus restrictedPackageStatus = getRestrictedPackageStatus(i);
            if (restrictedPackageStatus != null) {
                restrictedPackageStatus.freezedRecord.cancelRestrictState();
            }
        }
    }

    public boolean isSkipFrozen(String str, int i, String str2) {
        if (str2.equals("default")) {
            return false;
        }
        if (!MARsDebugConfig.DEBUG_ENG) {
            return true;
        }
        Slog.d(TAG, " pkgName  = " + str + " uid = " + i + " reason =" + str2);
        return true;
    }

    public final boolean freezeAction(FreecessPkgStatus freecessPkgStatus, String str, int i, boolean z, boolean z2) {
        boolean z3 = i == 3;
        final String[] strArr = {"default"};
        synchronized (MARsPolicyManager.MARsLock) {
            if (freecessPkgStatus.isKilledByChimera) {
                if (System.currentTimeMillis() - freecessPkgStatus.killedTime < this.mKilledTimeInterval) {
                    Slog.d(TAG, "isKilledBy Chimera : " + freecessPkgStatus.uid);
                    return false;
                }
                freecessPkgStatus.isKilledByChimera = false;
                freecessPkgStatus.killedTime = 0L;
            }
            ArrayList arrayList = this.mMonitorFreezedList;
            if (arrayList != null && arrayList.size() > 64) {
                Slog.d(TAG, "monitored size exception(64)...");
                return false;
            }
            if (freecessPkgStatus.isFreezeProtected()) {
                Slog.d(TAG, "It has not been able to freeze yet. name=" + freecessPkgStatus.name + ", uid=" + freecessPkgStatus.uid);
                if (z3) {
                    int i2 = freecessPkgStatus.freezedState;
                    freecessPkgStatus.freezedState = 1;
                    if (this.mScreenOn) {
                        stepLcdOnFreezedState(i2, 1, str, freecessPkgStatus);
                    }
                }
                return false;
            }
            boolean sendFreecessSignal = sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 1, z3, new checkResultCallback() { // from class: com.android.server.am.FreecessController.1
                @Override // com.android.server.am.FreecessController.checkResultCallback
                public void freezeSkipFrozen(String str2) {
                    strArr[0] = " " + str2;
                    Slog.d(FreecessController.TAG, " reason = " + str2);
                }
            });
            if (isSkipFrozen(freecessPkgStatus.name, freecessPkgStatus.uid, strArr[0])) {
                return false;
            }
            if (sendFreecessSignal) {
                freecessPkgStatus.freezedRecord.updateFreezeState(System.currentTimeMillis(), str);
                freecessPkgStatus.freezedRecord.isFreezed = true;
                freecessPkgStatus.isUidFreezed = true;
                freecessPkgStatus.freezedType = i;
                freecessPkgStatus.monitorPacketFlag = z;
                freecessPkgStatus.restrictNetworkFlag = z2;
                updateFreezedStatusByFreezeType(freecessPkgStatus, true);
                if (this.mFreezedPackages.getByUid(freecessPkgStatus.uid) == null) {
                    this.mFreezedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
                }
                Slog.d(TAG, "FZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
                FreecessHandler.getInstance().sendUpdateBTMsg(26, freecessPkgStatus.uid);
                if (z2) {
                    updateFreezedUidFirewall(freecessPkgStatus.uid, false);
                    MARsPolicyManager.getInstance().closeSocketsForUid(freecessPkgStatus.uid);
                }
                if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) == null) {
                    this.mFreezedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
                }
            } else {
                Slog.e(TAG, "FZ error : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + ")");
                boolean sendFreecessSignal2 = sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2);
                if (sendFreecessSignal2) {
                    freecessPkgStatus.freezedRecord.updateUnfreezeState(System.currentTimeMillis(), "FZ-fail");
                    freecessPkgStatus.freezedRecord.isFreezed = false;
                }
                Slog.d(TAG, "UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + ") because failed to freeze, success : " + sendFreecessSignal2);
            }
            if (sendFreecessSignal) {
                FreecessHandler.getInstance().sendFreecessSettlementMsg(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, z ? 1 : -1, sendFreecessSignal ? 1 : -1, true, z3, false);
                addUidToReleaseRestrictionList(freecessPkgStatus.uid);
                FreecessHandler.getInstance().sendOnFreezeStateChanged(true, freecessPkgStatus.uid);
            }
            return sendFreecessSignal;
        }
    }

    public void unpendingScheduleServiceRestartUid(int i, boolean z) {
        this.mAm.unpendingScheduleServiceRestart(i, z);
    }

    public final boolean unFreezeAction(FreecessPkgStatus freecessPkgStatus, String str, boolean z) {
        Set set;
        boolean z2;
        Set set2;
        boolean equals = "Packet".equals(str);
        if (freecessPkgStatus == null || !freecessPkgStatus.freezedRecord.isFreezed) {
            return false;
        }
        if (getInstance().isPidUfzEnabled()) {
            FreecessHandler.getInstance().sendUnpendingScheduleServiceRestartMsg(freecessPkgStatus.uid, "Signal".equals(str));
        }
        if (this.mIsOLAFEnabled && this.mOLAFOn) {
            Set set3 = this.olafUfzList;
            if (set3 != null && set3.contains(str) && freecessPkgStatus.freezedRecord.isOLAFFreezed) {
                unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str);
                return false;
            }
            if (z && (set2 = this.olafUfzList) != null && !set2.contains(str)) {
                freecessPkgStatus.isPendingUFZ = true;
                freecessPkgStatus.freezedRecord.unfreezedReason = str;
                return false;
            }
        }
        boolean sendFreecessSignal = sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2);
        if (sendFreecessSignal) {
            freecessPkgStatus.freezedRecord.updateUnfreezeState(System.currentTimeMillis(), str);
            freecessPkgStatus.freezedRecord.isFreezed = false;
            freecessPkgStatus.freezedState = 1;
            MARsPolicyManager.getInstance().addDebugInfoToHistory("UFZ", str + " " + freecessPkgStatus.name + " " + freecessPkgStatus.uid + " " + MARsPolicyManager.getInstance().formatDateTimeWithoutYear(freecessPkgStatus.freezedRecord.freezedTime));
            if ("Watchdog_HALF".equals(str) || "Watchdog".equals(str)) {
                Slog.d(TAG, "UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), FZ-time:" + freecessPkgStatus.freezedRecord.freezedTime + " reason: " + str);
            } else {
                Slog.d(TAG, "UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
            }
            onUnfreeze(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, freecessPkgStatus.monitorPacketFlag, freecessPkgStatus.restrictNetworkFlag);
            freecessPkgStatus.monitorPacketFlag = false;
            freecessPkgStatus.restrictNetworkFlag = false;
            freecessPkgStatus.freezedState = 1;
            if (this.mCalmModeEnabled) {
                freecessPkgStatus.isFreezedByCalm = false;
                if (!this.calmModeFilterList.contains(str)) {
                    FreecessHandler.getInstance().sendCalmModeRepeatMsg(freecessPkgStatus.name, freecessPkgStatus.userId, "CalmMode");
                }
            } else {
                MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
                if ((mARsFreezeStateRecord.isLcdOnFreezed || mARsFreezeStateRecord.isLcdOffFreezed) && (set = this.filterList) != null && !set.contains(str)) {
                    int i = freecessPkgStatus.freezedState;
                    if (freecessPkgStatus.freezedRecord.isLcdOnFreezed && MARsPolicyManager.getInstance().checkIsChinaModel()) {
                        z2 = IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE ? MARsPolicyManager.getInstance().isForegroundServicePkg(freecessPkgStatus.uid) : false;
                    } else {
                        z2 = true;
                    }
                    if (this.mScreenOn) {
                        stepLcdOnFreezedState(i, freecessPkgStatus.freezedState, str, freecessPkgStatus, z2);
                    }
                }
            }
            updateFreezedStatusByFreezeType(freecessPkgStatus, false);
            protectFreezePackage(freecessPkgStatus.name, freecessPkgStatus.userId, str, 1500L);
            if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) != null) {
                this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
            }
        } else {
            if (equals && freecessPkgStatus.monitorPacketFlag) {
                FreecessHandler.getInstance().sendFreecessSettlementMsg(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, 1, -1, false, false, true);
            }
            Slog.e(TAG, "UFZ error : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + ")");
        }
        return sendFreecessSignal;
    }

    public final void unFreezeActionForPid(FreecessPkgStatus freecessPkgStatus, String str, int i, int i2) {
        if (freecessPkgStatus != null && freecessPkgStatus.freezedRecord.isFreezed && sendFreecessSignal(2, i, i2)) {
            freecessPkgStatus.isUidFreezed = false;
            freecessPkgStatus.isPidFreezed = true;
            MARsPolicyManager.getInstance().addDebugInfoToHistory("UFZ", "PROC: " + str + " <u=" + i + ", p=" + i2 + ">");
        }
    }

    public void unfreezeAllPackages(String str) {
        unFreezePackage(str);
        cleanPacketMonitoredUids();
    }

    public void enterOLAF(int i) {
        enterOLAF(null, -1, i);
    }

    public void enterOLAF(String str, int i) {
        enterOLAF(str, i, "com.sec.android.app.camera".equals(str) ? 5000 : 0);
    }

    public final void enterOLAF(String str, int i, int i2) {
        if (!this.mIsOLAFEnabled || this.mOLAFOn || !this.mScreenOn || this.mCarModeOn || this.mEmergencyModeOn || this.mCalmModeEnabled || "com.google.android.youtube".equals(str) || "com.jingdong.app.mall".equals(str)) {
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
                if (uptimeMillis <= this.olafUnfreezeEstimatedTime.longValue()) {
                    return;
                } else {
                    this.olafUnfreezeEstimatedTime = Long.valueOf(uptimeMillis);
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            Slog.d(TAG, "Enter OLAF! pkgName: " + str + ", uid: " + i + ", mills: " + i2);
        }
        this.mOLAFOn = true;
        FreecessHandler.getInstance().sendOLAFMsg(true, str, i);
    }

    public void updateTargetPkgForOLAF(boolean z, String str, int i) {
        if (z) {
            this.mFreecessOlafUpdate.set(true);
            this.mOlafTargetPkg = str;
            this.mOlafTargetUserId = UserHandle.getUserId(i);
        }
    }

    public void clearTargetPkgForOLAF() {
        this.mFreecessOlafUpdate.set(false);
        this.mOlafTargetPkg = null;
        this.mOlafTargetUserId = -10;
    }

    public void exitOLAF() {
        if (MARsDebugConfig.DEBUG_OLAF) {
            Slog.d(TAG, "Exit OLAF!");
        }
        if (this.mIsOLAFEnabled && this.mOLAFOn) {
            synchronized (this.olafUnfreezeEstimatedTime) {
                if (SystemClock.uptimeMillis() < this.olafUnfreezeEstimatedTime.longValue()) {
                    return;
                }
                FreecessHandler.getInstance().removeOLAFTimeOutMsg();
                FreecessHandler.getInstance().sendOLAFMsg(false, null, -1);
            }
        }
    }

    public void triggerOLAF(String str, int i) {
        long j;
        String str2;
        FreecessPkgStatus packageStatus;
        Iterator it;
        long j2;
        MARsFreezeStateRecord mARsFreezeStateRecord;
        String str3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        MARsPolicyManager.getInstance().onAppUsed(str, UserHandle.getUserId(i));
        unFreezePackage(str, UserHandle.getUserId(i), "activity");
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceBegin(64L, "Trigger OLAF");
        }
        ArrayList arrayList = new ArrayList();
        WindowManagerService windowManagerService = this.mAm.mWindowManager;
        if (windowManagerService != null) {
            arrayList = windowManagerService.getVisibleWinSurfacePkgList();
        }
        ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
        int i2 = 0;
        if (IS_MINIMIZE_OLAF_LOCK) {
            ArrayList arrayList2 = new ArrayList();
            synchronized (MARsPolicyManager.MARsLock) {
                SparseArray uidMap = this.mFreecessManagedPackages.getUidMap();
                while (i2 < uidMap.size()) {
                    arrayList2.add((FreecessPkgStatus) uidMap.valueAt(i2));
                    i2++;
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) it2.next();
                MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
                synchronized (lock) {
                    if (freecessPkgStatus != null) {
                        MARsFreezeStateRecord mARsFreezeStateRecord2 = freecessPkgStatus.freezedRecord;
                        if (mARsFreezeStateRecord2 != null) {
                            if (mARsFreezeStateRecord2 == null || !mARsFreezeStateRecord2.isFreezed) {
                                if (str == null || !freecessPkgStatus.name.equals(str) || freecessPkgStatus.uid != i) {
                                    if (freecessPkgStatus.sharedUidName == null) {
                                        it = it2;
                                        if (MARsPolicyManager.getInstance().isInPolicyExceptionList(freecessPkgStatus.name, freecessPkgStatus.userId, 4)) {
                                            if (MARsDebugConfig.DEBUG_ENG) {
                                                Slog.d(TAG, "skip isInPolicyExceptionList app:" + freecessPkgStatus.name);
                                            }
                                        } else if (MARsPolicyManager.getInstance().checkIsChinaModel() && (str3 = freecessPkgStatus.name) != null && str3.equals(DefaultAppFilter.getInstance().getDefaultHomePackage())) {
                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                Slog.d(TAG, "skip to freeze prev app:" + freecessPkgStatus.name + ", uid : " + freecessPkgStatus.uid);
                                            }
                                        } else if (arrayList.size() > 0 && arrayList.contains(freecessPkgStatus.name)) {
                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                Slog.d(TAG, freecessPkgStatus.name + " has window surface, skip to freeze");
                                            }
                                        } else if (freecessPkgStatus.isOlafAllowList) {
                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                Slog.d(TAG, freecessPkgStatus.name + " olaf allowlist, skip to freeze");
                                            }
                                        } else {
                                            if (!this.mOLAFAllowListForDebug.contains(freecessPkgStatus.name) && !this.mOLAFAllowListForDebug.contains(String.valueOf(freecessPkgStatus.uid))) {
                                                if (UidStateMgr.getInstance().isUidTop(freecessPkgStatus.uid)) {
                                                    Slog.d(TAG, freecessPkgStatus.name + " is top app, skip to freeze");
                                                } else if (isQuickFreezeEnabled() && (mARsFreezeStateRecord = freecessPkgStatus.freezedRecord) != null && mARsFreezeStateRecord.isLcdOnFreezed) {
                                                    Slog.d(TAG, "BG freezed, skip OLAF freeze for (" + freecessPkgStatus.name + ", " + freecessPkgStatus.uid + ").");
                                                } else {
                                                    j2 = currentTimeMillis;
                                                    int filterForSpecificPolicy = FilterManager.getInstance().filterForSpecificPolicy(11, freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid);
                                                    if (filterForSpecificPolicy == 0) {
                                                        freezeOLAFPackage(freecessPkgStatus, sb);
                                                    } else {
                                                        sb2.append(" " + freecessPkgStatus.uid + XmlUtils.STRING_ARRAY_SEPARATOR + filterForSpecificPolicy);
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
                                            j2 = currentTimeMillis;
                                            if (MARsDebugConfig.DEBUG_OLAF) {
                                                Slog.d(TAG, freecessPkgStatus.name + " olaf debug allowlist, skip to freeze");
                                            }
                                        }
                                        j2 = currentTimeMillis;
                                    }
                                }
                            }
                            j2 = currentTimeMillis;
                            it = it2;
                        }
                    }
                    j2 = currentTimeMillis;
                    it = it2;
                }
                it2 = it;
                currentTimeMillis = j2;
            }
            j = currentTimeMillis;
        } else {
            j = currentTimeMillis;
            synchronized (MARsPolicyManager.MARsLock) {
                SparseArray uidMap2 = this.mFreecessManagedPackages.getUidMap();
                while (i2 < uidMap2.size()) {
                    FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) uidMap2.valueAt(i2);
                    MARsFreezeStateRecord mARsFreezeStateRecord3 = freecessPkgStatus2.freezedRecord;
                    if ((mARsFreezeStateRecord3 == null || !mARsFreezeStateRecord3.isFreezed) && ((str == null || !freecessPkgStatus2.name.equals(str) || freecessPkgStatus2.uid != i) && freecessPkgStatus2.sharedUidName == null)) {
                        if (MARsPolicyManager.getInstance().isInPolicyExceptionList(freecessPkgStatus2.name, freecessPkgStatus2.userId, 4)) {
                            if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d(TAG, "skip isInPolicyExceptionList app:" + freecessPkgStatus2.name);
                            }
                        } else if (MARsPolicyManager.getInstance().checkIsChinaModel() && (str2 = freecessPkgStatus2.name) != null && str2.equals(DefaultAppFilter.getInstance().getDefaultHomePackage())) {
                            if (MARsDebugConfig.DEBUG_OLAF) {
                                Slog.d(TAG, "skip to freeze prev app:" + freecessPkgStatus2.name + ", uid : " + freecessPkgStatus2.uid);
                            }
                        } else if (arrayList.size() > 0 && arrayList.contains(freecessPkgStatus2.name)) {
                            if (MARsDebugConfig.DEBUG_OLAF) {
                                Slog.d(TAG, freecessPkgStatus2.name + " has window surface, skip to freeze");
                            }
                        } else if (freecessPkgStatus2.isOlafAllowList) {
                            if (MARsDebugConfig.DEBUG_OLAF) {
                                Slog.d(TAG, freecessPkgStatus2.name + " olaf allowlist, skip to freeze");
                            }
                        } else {
                            if (!this.mOLAFAllowListForDebug.contains(freecessPkgStatus2.name) && !this.mOLAFAllowListForDebug.contains(String.valueOf(freecessPkgStatus2.uid))) {
                                if (UidStateMgr.getInstance().isUidTop(freecessPkgStatus2.uid)) {
                                    Slog.d(TAG, freecessPkgStatus2.name + " is top app, skip to freeze");
                                } else {
                                    int filterForSpecificPolicy2 = FilterManager.getInstance().filterForSpecificPolicy(11, freecessPkgStatus2.name, freecessPkgStatus2.userId, freecessPkgStatus2.uid);
                                    if (filterForSpecificPolicy2 == 0) {
                                        freezeOLAFPackage(freecessPkgStatus2, sb);
                                    } else {
                                        sb2.append(" " + freecessPkgStatus2.uid + XmlUtils.STRING_ARRAY_SEPARATOR + filterForSpecificPolicy2);
                                    }
                                    i2++;
                                }
                            }
                            if (MARsDebugConfig.DEBUG_OLAF) {
                                Slog.d(TAG, freecessPkgStatus2.name + " olaf debug allowlist, skip to freeze");
                            }
                            i2++;
                        }
                        i2++;
                    }
                    i2++;
                }
            }
        }
        int userId = this.mContext.getUserId();
        Iterator it3 = this.mOLAFBlockList.iterator();
        while (it3.hasNext()) {
            String str4 = (String) it3.next();
            if (str4 != null && !str4.equals(str)) {
                synchronized (MARsPolicyManager.MARsLock) {
                    if (MARsPolicyManager.getInstance().isMARsTarget(str4, userId) && (packageStatus = getPackageStatus(str4, userId)) != null) {
                        freezeOLAFPackage(packageStatus, sb);
                    }
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceEnd(64L);
        }
        MARsPolicyManager.getInstance().addDebugInfoToHistory("OLAF " + i + " [" + MARsPolicyManager.getInstance().formatDateTimeWithoutYear(j) + "]", convertLevelChangeInfoToString(sb, sb2));
        FreecessHandler.getInstance().sendOLAFTimeOutMsg(this.olafUnfreezeEstimatedTime.longValue());
    }

    public final String convertLevelChangeInfoToString(StringBuilder sb, StringBuilder sb2) {
        StringBuilder sb3 = new StringBuilder();
        if (sb != null && sb.length() > 0) {
            sb3.append("[OLAF] ");
            sb3.append(((Object) sb) + " ");
        }
        if (sb2 != null && sb2.length() > 0) {
            sb3.append("[IMP] ");
            sb3.append(((Object) sb2) + " ");
        }
        return sb3.toString();
    }

    public final void freezeOLAFPackage(FreecessPkgStatus freecessPkgStatus, StringBuilder sb) {
        if (!MARsPolicyManager.getInstance().checkIsChinaModel() && (freecessPkgStatus == null || freecessPkgStatus.freezedRecord.isFreezed || freecessPkgStatus.isFreezeProtected())) {
            if (MARsDebugConfig.DEBUG_OLAF) {
                String str = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Skip olaf fz for <");
                sb2.append(freecessPkgStatus != null ? freecessPkgStatus.name : "null");
                sb2.append(", ");
                sb2.append(freecessPkgStatus != null ? Integer.valueOf(freecessPkgStatus.uid) : "null");
                sb2.append(">: fzed=");
                sb2.append(freecessPkgStatus != null ? Boolean.valueOf(freecessPkgStatus.freezedRecord.isFreezed) : "null");
                sb2.append(", protect=");
                sb2.append(freecessPkgStatus != null ? Boolean.valueOf(freecessPkgStatus.isFreezeProtected()) : "null");
                Slog.d(str, sb2.toString());
                return;
            }
            return;
        }
        if (freezeForOLAF(freecessPkgStatus, "OLAF")) {
            sb.append(" " + freecessPkgStatus.uid);
            freecessPkgStatus.freezedType = 1;
            freecessPkgStatus.freezedRecord.isOLAFFreezed = true;
            if (!isQuickFreezeEnabled() && freecessPkgStatus.freezedState == 2) {
                freecessPkgStatus.freezedState = 1;
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(2, freecessPkgStatus.name);
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(3, freecessPkgStatus.name);
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(4, freecessPkgStatus.name);
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(28, freecessPkgStatus.name);
            }
            if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) == null) {
                this.mFreezedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
            }
        }
    }

    public final boolean freezeForOLAF(FreecessPkgStatus freecessPkgStatus, String str) {
        ArrayList arrayList = freecessPkgStatus.isolatedPids;
        if (arrayList == null) {
            freecessPkgStatus.isolatedPids = new ArrayList();
        } else {
            arrayList.clear();
        }
        boolean sendFreecessSignal = sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 1, false, null);
        if (sendFreecessSignal) {
            freecessPkgStatus.freezedRecord.isFreezed = true;
            if (MARsDebugConfig.DEBUG_OLAF) {
                Slog.d(TAG, "OLAF FZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
            }
        } else if (sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2)) {
            MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
            mARsFreezeStateRecord.isFreezed = false;
            mARsFreezeStateRecord.isOLAFFreezed = false;
        }
        return sendFreecessSignal;
    }

    public void unfreezePackageForOLAF(FreecessPkgStatus freecessPkgStatus, String str) {
        if (sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2)) {
            freecessPkgStatus.freezedRecord.isFreezed = false;
            freecessPkgStatus.freezedType = 0;
            if (!isQuickFreezeEnabled()) {
                freecessPkgStatus.freezedState = 1;
            }
            freecessPkgStatus.freezedRecord.isOLAFFreezed = false;
            if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) != null) {
                this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
            }
            MARsPolicyManager.getInstance().addDebugInfoToHistory("UFZ", str + " " + freecessPkgStatus.name + " " + freecessPkgStatus.uid + " " + MARsPolicyManager.getInstance().formatDateTimeWithoutYear(freecessPkgStatus.freezedRecord.freezedTime));
            if (MARsDebugConfig.DEBUG_OLAF) {
                Slog.d(TAG, "OLAF UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
            }
        }
    }

    public void unfreezePackageForOLAF(FreecessPkgStatus freecessPkgStatus, String str, StringBuilder sb) {
        if (sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2)) {
            freecessPkgStatus.freezedRecord.isFreezed = false;
            freecessPkgStatus.freezedType = 0;
            if (!isQuickFreezeEnabled()) {
                freecessPkgStatus.freezedState = 1;
            }
            freecessPkgStatus.freezedRecord.isOLAFFreezed = false;
            sb.append(" " + freecessPkgStatus.uid);
            if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) != null) {
                this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
            }
            if (MARsDebugConfig.DEBUG_OLAF) {
                Slog.d(TAG, "OLAF UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
            }
        }
    }

    public void triggerCalmMode(ArrayList arrayList) {
        this.mCalmModeEnabled = true;
        StringBuilder sb = new StringBuilder();
        FreecessHandler.getInstance().removeBgTriggerMsg();
        if (arrayList != null) {
            this.mCalmModeAllowListFromGameUI = getCalmModeAllowList(arrayList);
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList2 = new ArrayList();
        WindowManagerService windowManagerService = this.mAm.mWindowManager;
        if (windowManagerService != null) {
            arrayList2 = windowManagerService.getVisibleWinSurfacePkgList();
        }
        ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
        ActiveMusicRecordFilter.getInstance().setUsingAudioList(true);
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray uidMap = this.mFreecessManagedPackages.getUidMap();
            for (int i = 0; i < uidMap.size(); i++) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) uidMap.valueAt(i);
                if (arrayList2.size() > 0 && arrayList2.contains(freecessPkgStatus.name)) {
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d(TAG, freecessPkgStatus.name + " has window surface, skip to freeze CalmMode");
                    }
                } else if (freezeForCalmMode(freecessPkgStatus, "CalmMode First trigger") && freecessPkgStatus.isFreezedByCalm) {
                    sb.append(" " + freecessPkgStatus.uid);
                } else if (freecessPkgStatus.freezedRecord.isFreezed && !isExceptionListAppForCalmMode(freecessPkgStatus)) {
                    freecessPkgStatus.isFreezedByCalm = true;
                }
            }
        }
        ActiveMusicRecordFilter.getInstance().setUsingAudioList(false);
        sb.append(" [" + MARsPolicyManager.getInstance().formatDateTimeWithoutYear(currentTimeMillis) + "]");
        MARsPolicyManager.getInstance().addDebugInfoToHistory("CalmMode ", sb.toString());
    }

    public final FreecessPkgMap getCalmModeAllowList(ArrayList arrayList) {
        String str;
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
                    Slog.e(TAG, "CalmMode parseInt error!");
                }
            } else {
                str = null;
            }
            freecessPkgMap.put(userId, str, 1);
        }
        return freecessPkgMap;
    }

    public void quickFreezeForCalmMode(String str, int i, String str2) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(str, i);
            if (packageStatus != null && !freezeForCalmMode(packageStatus, str2)) {
                FreecessHandler.getInstance().sendCalmModeRepeatMsg(str, i, str2);
            }
        }
    }

    public void cancelCalmMode() {
        StringBuilder sb = new StringBuilder();
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray clone = this.mFreezedPackages.getUidMap().clone();
            for (int i = 0; i < clone.size(); i++) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) clone.valueAt(i);
                boolean isAutoRunOn = MARsPolicyManager.getInstance().isAutoRunOn(freecessPkgStatus.name, freecessPkgStatus.userId);
                if (MARsPolicyManager.getInstance().isMARsTarget(freecessPkgStatus.name, freecessPkgStatus.userId) && !isAutoRunOn) {
                    if (freecessPkgStatus.isFreezedByCalm) {
                        freecessPkgStatus.isFreezedByCalm = false;
                    }
                }
                unFreezeForCalmMode(freecessPkgStatus, "CalmMode");
                sb.append(freecessPkgStatus.uid + " ");
            }
        }
        FreecessHandler.getInstance().removeCalmModeFreezeMsg();
        this.mCalmModeEnabled = false;
        MARsPolicyManager.getInstance().removeRestrictListForCalmMode(sb);
    }

    public boolean freezeForCalmMode(FreecessPkgStatus freecessPkgStatus, String str) {
        int autorunForFreezedPackage = MARsPolicyManager.getInstance().getAutorunForFreezedPackage(freecessPkgStatus.name, freecessPkgStatus.userId);
        if (freecessPkgStatus.freezedRecord.isFreezed || isExceptionListAppForCalmMode(freecessPkgStatus) || MARsPolicyManager.getInstance().isInPolicyExceptionList(freecessPkgStatus.name, freecessPkgStatus.userId, 4) || !UserHandle.isApp(freecessPkgStatus.uid) || freecessPkgStatus.sharedUidName != null || FilterManager.getInstance().filterForSpecificPolicy(18, freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid) > 0) {
            return false;
        }
        if (freezeAction(freecessPkgStatus, str, 3, getMonitorPacketFlag(autorunForFreezedPackage), getRestrictNetworkFlag(autorunForFreezedPackage))) {
            freecessPkgStatus.isFreezedByCalm = true;
            MARsPolicyManager.getInstance().addDebugInfoToHistory("FZ", str + " " + freecessPkgStatus.name + " " + freecessPkgStatus.uid);
        }
        return true;
    }

    public boolean unFreezeForCalmMode(FreecessPkgStatus freecessPkgStatus, String str) {
        boolean unFreezeAction = unFreezeAction(freecessPkgStatus, str, false);
        if (unFreezeAction) {
            freecessPkgStatus.isFreezedByCalm = false;
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "CalmMode UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
        }
        return unFreezeAction;
    }

    public boolean killPkgForCalmMode(int i, String str) {
        FreecessPkgStatus frozenPackageStatus;
        boolean unFreezeForCalmMode;
        if (!this.mCalmModeEnabled) {
            return false;
        }
        synchronized (MARsPolicyManager.MARsLock) {
            frozenPackageStatus = getFrozenPackageStatus(i);
            unFreezeForCalmMode = frozenPackageStatus != null ? unFreezeForCalmMode(frozenPackageStatus, str) : false;
        }
        if (!unFreezeForCalmMode || frozenPackageStatus == null || isExceptionListAppForCalmMode(frozenPackageStatus)) {
            return true;
        }
        killProcessForCalmMode(frozenPackageStatus.name, frozenPackageStatus.uid, frozenPackageStatus.userId, "CalmMode");
        return true;
    }

    public boolean isExceptionListAppForCalmMode(FreecessPkgStatus freecessPkgStatus) {
        return freecessPkgStatus == null || this.mSsrmAllowPackages.contains(freecessPkgStatus.name) || this.mOLAFAllowPackages.contains(freecessPkgStatus.name) || this.mCalmModeDefaultAllowList.contains(freecessPkgStatus.name) || this.mCalmModeAllowListFromGameUI.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) != null || 2 == LatestProtectedPackageFilter.getInstance().filter(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, 18);
    }

    public void killProcessForCalmMode(String str, int i, int i2, String str2) {
        this.mAm.killProcessForCalmMode(str, i, i2, "CalmMode");
        MARsPolicyManager.getInstance().addRestrictListForCalmMode(str, i2, str2);
    }

    public boolean isFrozenByCalmMode(int i) {
        if (!this.mCalmModeEnabled) {
            return false;
        }
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus frozenPackageStatus = getFrozenPackageStatus(i);
            if (frozenPackageStatus == null) {
                return false;
            }
            return frozenPackageStatus.isFreezedByCalm;
        }
    }

    public boolean isCalmModeOnoff() {
        return this.mCalmModeEnabled;
    }

    public final void unfreezePackageForPending(FreecessPkgStatus freecessPkgStatus, String str) {
        boolean equals = "Packet".equals(str);
        if (sendFreecessSignal(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.isolatedPids, 2)) {
            MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
            mARsFreezeStateRecord.isFreezed = false;
            mARsFreezeStateRecord.isOLAFFreezed = false;
            MARsPolicyManager.getInstance().addDebugInfoToHistory("UFZ", "OLAF:P-" + str + " " + freecessPkgStatus.name + " " + freecessPkgStatus.uid + " " + MARsPolicyManager.getInstance().formatDateTimeWithoutYear(freecessPkgStatus.freezedRecord.freezedTime));
            onUnfreeze(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, freecessPkgStatus.monitorPacketFlag, freecessPkgStatus.restrictNetworkFlag);
            if (!isQuickFreezeEnabled()) {
                freecessPkgStatus.freezedState = 1;
            }
            if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) != null) {
                this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
            }
            if (MARsDebugConfig.DEBUG_OLAF) {
                Slog.d(TAG, "OLAF Pending UFZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: " + str);
                return;
            }
            return;
        }
        if (equals && freecessPkgStatus.monitorPacketFlag) {
            FreecessHandler.getInstance().sendFreecessSettlementMsg(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, 1, -1, false, false, true);
        }
        Slog.e(TAG, "UFZ error : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + ")");
    }

    public void unFreezeForOLAF(String str) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        if (MARsDebugConfig.DEBUG_OLAF) {
            Slog.d(TAG, "OLAF unfreeze for " + str);
            Trace.traceBegin(64L, "Unfreeze OLAF");
        }
        ArrayList arrayList2 = new ArrayList();
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray uidMap = this.mFreezedPackages.getUidMap();
            for (int i = 0; i < uidMap.size(); i++) {
                arrayList2.add((FreecessPkgStatus) uidMap.valueAt(i));
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) it.next();
            MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
            synchronized (lock) {
                if (freecessPkgStatus != null) {
                    if (freecessPkgStatus.freezedRecord != null) {
                        if (isQuickFreezeEnabled() && freecessPkgStatus.freezedRecord.isLcdOnFreezed) {
                            Slog.d(TAG, "BG freezed, skip OLAF unfreeze for (" + freecessPkgStatus.name + ", " + freecessPkgStatus.uid + ").");
                        } else if (this.mCalmModeEnabled && freecessPkgStatus.isFreezedByCalm) {
                            freecessPkgStatus.freezedRecord.isOLAFFreezed = false;
                            freecessPkgStatus.freezedType = 3;
                            freecessPkgStatus.freezedState = 2;
                        } else {
                            if (!MARsPolicyManager.getInstance().isMARsTarget(freecessPkgStatus.name, freecessPkgStatus.userId)) {
                                if (freecessPkgStatus.freezedRecord.isOLAFFreezed) {
                                    unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str, sb);
                                    this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
                                } else {
                                    if (unFreezeAction(freecessPkgStatus, str + ":nMARsTG", false)) {
                                        this.mFreezedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
                                    }
                                }
                            } else {
                                if (freecessPkgStatus.isPendingUFZ) {
                                    unfreezePackageForPending(freecessPkgStatus, freecessPkgStatus.freezedRecord.unfreezedReason);
                                    freecessPkgStatus.isPendingUFZ = false;
                                } else if (freecessPkgStatus.freezedRecord.isOLAFFreezed) {
                                    unfreezePackageForOLAF(freecessPkgStatus, "OLAF:" + str, sb);
                                }
                                arrayList.add(freecessPkgStatus);
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
                }
            }
        }
        if (MARsDebugConfig.DEBUG_OLAF) {
            Trace.traceEnd(64L);
        }
        MARsPolicyManager.getInstance().addDebugInfoToHistory("UFA", "OLAF:" + str + " " + sb.toString());
        if (!this.mSkipTriggerLcdOnFreeze) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) arrayList.get(i2);
                if (freecessPkgStatus2 != null) {
                    triggerLcdOnFreeze(freecessPkgStatus2.uid, freecessPkgStatus2.name);
                }
            }
        }
        synchronized (this.olafUnfreezeEstimatedTime) {
            this.olafUnfreezeEstimatedTime = 0L;
        }
        this.mOLAFOn = false;
    }

    public boolean getScreenOnFreecessEnabled() {
        return this.mIsScreenOnFreecessEnabled;
    }

    public void setScreenOnFreecessEnabled(boolean z) {
        if (this.mIsScreenOnFreecessEnabled != z) {
            this.mIsScreenOnFreecessEnabled = z;
            this.mSkipTriggerLcdOnFreeze = !z;
        }
    }

    public boolean getUidIdleCheckMode() {
        return this.mUidIdleCheck && !isQuickFreezeEnabled();
    }

    public void setUidIdleCheckMode(boolean z) {
        this.mUidIdleCheck = z;
        if (MARsDebugConfig.DEBUG_FREECESS) {
            Slog.d(TAG, "FRECESS DEBUGGING MODE CHANGED ! Uid Idle Checking will be " + this.mUidIdleCheck);
        }
    }

    public boolean getScreenOnState() {
        return this.mScreenOn;
    }

    public void setScreenOnState(boolean z) {
        ActivityManagerService activityManagerService;
        ProcessRecord topApp;
        this.mScreenOn = z;
        if (!z || (activityManagerService = this.mAm) == null || (topApp = activityManagerService.getTopApp()) == null || topApp.info == null) {
            return;
        }
        protectFreezePackage(topApp.info.packageName, topApp.userId, "TopApp-ScreenOn", 10000L);
        unfreezeWallPaperPackage();
    }

    public void unfreezeWallPaperPackage() {
        ArrayList wallPaperPackages = WallPaperFilter.getInstance().getWallPaperPackages(this.mContext.getUserId());
        for (int i = 0; i < wallPaperPackages.size(); i++) {
            getInstance().unFreezePackage((String) wallPaperPackages.get(i), this.mContext.getUserId(), "WallPaper");
        }
    }

    public void setFreezeExcludeList(ArrayList arrayList) {
        synchronized (this.mFreezeExcludePackages) {
            this.mFreezeExcludePackages.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!this.mSsrmAllowPackages.contains(str)) {
                    this.mFreezeExcludePackages.add(MARsVersionManager.toNormalText(str));
                }
            }
        }
        synchronized (MARsPolicyManager.MARsLock) {
            SparseArray uidMap = this.mFreecessManagedPackages.getUidMap();
            for (int i = 0; i < uidMap.size(); i++) {
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) uidMap.valueAt(i);
                if (freecessPkgStatus != null && MARsPolicyManager.getInstance().isMARsTarget(freecessPkgStatus.name, freecessPkgStatus.userId)) {
                    freecessPkgStatus.isInAllowList = false;
                    updateAllowListForFreecess(freecessPkgStatus);
                }
            }
        }
    }

    public void removeBgTriggerMsg() {
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "removeBgTriggerMsg....");
        }
        FreecessHandler.getInstance().removeBgTriggerMsg();
    }

    public void triggerLcdOnFreeze(int i, String str) {
        boolean z = (getUidIdleCheckMode() && this.mUidIdleCheck && !UidStateMgr.getInstance().isUidIdle(i)) ? false : true;
        BlueToothConnectedFilter.getInstance().updateBTUsingPackages();
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) this.mFreecessManagedPackages.getByUid(i);
            if (freecessPkgStatus == null) {
                return;
            }
            if (!isQuickFreezeEnabled() && !z) {
                FreecessHandler.getInstance().sendUidLcdOnQuickFzMsg(i, str, true);
            }
            stepLcdOnFreezedState(freecessPkgStatus.freezedState, 1, "uidIdle", freecessPkgStatus);
        }
    }

    public final void overrideOlafForBgFreeze(FreecessPkgStatus freecessPkgStatus) {
        boolean monitorPacketFlag = getMonitorPacketFlag(MARsPolicyManager.getInstance().getAutorunForFreezedPackage(freecessPkgStatus.name, UserHandle.getUserId(freecessPkgStatus.uid)));
        boolean restrictNetworkFlag = getRestrictNetworkFlag(MARsPolicyManager.getInstance().getAutorunForFreezedPackage(freecessPkgStatus.name, UserHandle.getUserId(freecessPkgStatus.uid)));
        MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
        mARsFreezeStateRecord.isLcdOnFreezed = true;
        mARsFreezeStateRecord.freezedTime = System.currentTimeMillis();
        MARsFreezeStateRecord mARsFreezeStateRecord2 = freecessPkgStatus.freezedRecord;
        if (mARsFreezeStateRecord2.isOLAFFreezed) {
            mARsFreezeStateRecord2.isOLAFFreezed = false;
        }
        freecessPkgStatus.freezedType = 3;
        freecessPkgStatus.freezedState = 2;
        mARsFreezeStateRecord2.freezedReason = "Bg";
        freecessPkgStatus.isUidFreezed = true;
        freecessPkgStatus.monitorPacketFlag = monitorPacketFlag;
        freecessPkgStatus.restrictNetworkFlag = restrictNetworkFlag;
        MARsPolicyManager.getInstance().addDebugInfoToHistory("OVR", "Olaf2Bg " + freecessPkgStatus.name + " " + freecessPkgStatus.uid);
        updateFreezedStatusByFreezeType(freecessPkgStatus, true);
        if (this.mFreezedPackages.getByUid(freecessPkgStatus.uid) == null) {
            this.mFreezedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
        }
        Slog.d(TAG, "FZ : " + freecessPkgStatus.name + "(" + freecessPkgStatus.userId + "), reason: Bg");
        FreecessHandler.getInstance().sendUpdateBTMsg(26, freecessPkgStatus.uid);
        if (restrictNetworkFlag) {
            updateFreezedUidFirewall(freecessPkgStatus.uid, false);
            closeSocketsForFreecessFirewallChain();
        }
        if (this.mFreezedPackages.getByUserId(freecessPkgStatus.userId, freecessPkgStatus.name) == null) {
            this.mFreezedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
        }
        freecessPkgStatus.freezedRecord.updateFreezeState(System.currentTimeMillis(), "Bg(override)");
        FreecessHandler.getInstance().sendFreecessSettlementMsg(freecessPkgStatus.name, freecessPkgStatus.userId, freecessPkgStatus.uid, monitorPacketFlag ? 1 : -1, 1, true, true, false);
        addUidToReleaseRestrictionList(freecessPkgStatus.uid);
        FreecessHandler.getInstance().sendOnFreezeStateChanged(true, freecessPkgStatus.uid);
        postFreezeWatchDog(freecessPkgStatus.uid);
    }

    public final void stepLcdOnFreezedState(int i, int i2, String str, FreecessPkgStatus freecessPkgStatus) {
        stepLcdOnFreezedState(i, i2, str, freecessPkgStatus, true);
    }

    public final void stepLcdOnFreezedState(int i, int i2, String str, FreecessPkgStatus freecessPkgStatus, boolean z) {
        if (i != i2) {
            Slog.d(TAG, freecessPkgStatus.name + "(state: " + covertLcdOnFreezedState(i) + " -> " + covertLcdOnFreezedState(i2) + ", Reason: " + str + ")");
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            FreecessHandler.getInstance().sendLcdOnFreezeTriggerMsg(freecessPkgStatus.name, freecessPkgStatus.uid);
        } else {
            if (z) {
                if (checkImportantPackage(freecessPkgStatus)) {
                    FreecessHandler.getInstance().sendCheckImportantMsg(freecessPkgStatus.name, freecessPkgStatus.userId, str);
                    return;
                } else {
                    sendMsgAfterCheckImportant(freecessPkgStatus, str);
                    return;
                }
            }
            sendMsgAfterCheckImportant(freecessPkgStatus, str);
        }
    }

    public void sendMsgAfterCheckImportant(FreecessPkgStatus freecessPkgStatus, String str) {
        if (isQuickFreezeEnabled() && MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
            FreecessHandler.getInstance().sendMakePkgIdleMsg(freecessPkgStatus.name, freecessPkgStatus.uid, freecessPkgStatus.userId, str, false);
        } else {
            FreecessHandler.getInstance().sendChangeToFrozenMsg(freecessPkgStatus.name, freecessPkgStatus.userId, str);
        }
    }

    public void makePkgIdleForLcdOn(String str, int i, int i2, String str2, boolean z) {
        try {
            FreecessPkgStatus packageStatus = getPackageStatus(str, i2);
            if (packageStatus == null) {
                return;
            }
            if (this.mAm != null && isQuickFreezeEnabled() && !UidStateMgr.getInstance().isUidIdle(packageStatus.uid) && !z) {
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d(TAG, "Try to make pkg idle for fz: (" + packageStatus.name + ", " + i2 + ").");
                }
                this.mAm.makePackageIdle(packageStatus.name, i2);
            }
            if (isQuickFreezeEnabled() && isPkgRunningService(packageStatus.name, i)) {
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d(TAG, "<" + i + ", " + packageStatus.name + "> is still ES after making idle, scheduling retry.");
                }
                FreecessHandler.getInstance().sendMakePkgIdleMsg(packageStatus.name, i, i2, str2, true);
                return;
            }
            if (getAllRunningPackagePids("", i, false).isEmpty()) {
                return;
            }
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(TAG, "<" + i + ", " + str + "> Service stopped, continue.");
            }
            FreecessHandler.getInstance().sendChangeToFrozenMsg(packageStatus.name, i2, str2);
        } catch (Exception e) {
            Slog.e(TAG, "makePkgIdleForLcdOn Exception : " + e);
        }
    }

    public void makePkgIdleIfNeeded(int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(i);
            if (packageStatus == null) {
                return;
            }
            String str = packageStatus.name;
            int i2 = packageStatus.userId;
            try {
                if (this.mAm == null || UidStateMgr.getInstance().isUidIdle(i)) {
                    return;
                }
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d(TAG, "Try to make pkg idle: <" + str + ", " + i + ">.");
                }
                this.mAm.makePackageIdle(str, i2);
            } catch (Exception e) {
                Slog.e(TAG, "makePkgIdleIfNeeded Exception : " + e);
            }
        }
    }

    public void lcdOnFreezedStateChange(int i, String str, String str2, int i2) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(str2, i2);
            if (packageStatus == null) {
                return;
            }
            int i3 = packageStatus.freezedState;
            if (i == 2 && i3 == 1) {
                packageStatus.freezedState = 2;
            }
            stepLcdOnFreezedState(i3, packageStatus.freezedState, str, packageStatus);
        }
    }

    public final boolean postFreezeWatchDog(int i) {
        String str;
        FreecessPkgStatus packageStatus = getPackageStatus(i);
        if (packageStatus == null || !packageStatus.freezedRecord.isFreezed) {
            Slog.d(TAG, "postFreezeWatchDog: u=" + i + " not fzed, return.");
            return false;
        }
        ArrayList allRunningPackagePids = getAllRunningPackagePids(packageStatus.name, packageStatus.uid, true);
        ArrayList arrayList = packageStatus.isolatedPids;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                allRunningPackagePids.add((Integer) it.next());
            }
        }
        int checkProcDiedOrComponentExecutingForFreeze = this.mAm.checkProcDiedOrComponentExecutingForFreeze(allRunningPackagePids, new ArrayList());
        if (checkProcDiedOrComponentExecutingForFreeze == 0) {
            return false;
        }
        switch (checkProcDiedOrComponentExecutingForFreeze) {
            case 1:
                str = "WD-AlreadyDied";
                break;
            case 2:
                str = "WD-ExecutingService";
                break;
            case 3:
                str = "WD-ReceivingIntent";
                break;
            case 4:
                str = "WD-LaunchingProvider";
                break;
            case 5:
                str = "WD-StartProcess";
                break;
            case 6:
                str = "WD-ForegroundAdj";
                break;
            case 7:
                str = "WD-RunningLogcat";
                break;
            default:
                str = "WD-PostCheck";
                break;
        }
        unFreezePackage(packageStatus.uid, str);
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "Freecess WD bites! u=" + packageStatus.uid + ", Reason: " + str);
        }
        return true;
    }

    public void handleLcdOnResetState(String str, int i) {
        synchronized (MARsPolicyManager.MARsLock) {
            FreecessPkgStatus packageStatus = getPackageStatus(str, i);
            if (packageStatus == null) {
                return;
            }
            if (packageStatus.freezedState != 2 && (!this.mScreenOn || !packageStatus.freezedRecord.isLcdOffFreezed)) {
                packageStatus.freezedState = 1;
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(1, packageStatus.name);
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(3, packageStatus.name);
                FreecessHandler.getInstance().removeBgTriggerMsgByObj(28, packageStatus.name);
            }
            if (packageStatus.freezedRecord.isFreezed) {
                unFreezePackage(packageStatus.name, packageStatus.userId, "UidActive");
            } else {
                packageStatus.freezedState = 1;
            }
            FreecessHandler.getInstance().removeBgTriggerMsgByObj(4, packageStatus.name);
        }
    }

    public final void resetFreecessStateForLcdOnFreeze() {
        if (this.mIsScreenOnFreecessEnabled) {
            synchronized (MARsPolicyManager.MARsLock) {
                SparseArray uidMap = this.mFreecessManagedPackages.getUidMap();
                for (int i = 0; i < uidMap.size(); i++) {
                    FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) uidMap.valueAt(i);
                    if (freecessPkgStatus != null) {
                        freecessPkgStatus.freezedState = 1;
                    }
                }
            }
        }
    }

    public void handleResetAllState(String str) {
        Slog.w(TAG, "!@*** unFreezeAllPackages for watchdog : Start reset all state unfreezing!!! - MARs FW Side (reason: " + str + ")");
        if ("SoftReset".equals(str)) {
            ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, str).acquire(2000L);
        }
        unfreezeAllPackages(str);
        resetFreecessStateForLcdOnFreeze();
        Slog.w(TAG, "!@*** unFreezeAllPackages for watchdog : End reset all state unfreezing!!! - MARs FW Side (reason: " + str + ")");
    }

    public void handleResetAllPreAction() {
        Slog.w(TAG, "!@*** unFreezeAllPackages for watchdog : Start thread for preaction unfreezing!!! cntFail(FZ/UFZ/UFZ_P) : " + this.cntFailFreeze + "/" + this.cntFailUnfreeze + "/" + this.cntFailUnfreezePilot);
        final long uptimeMillis = SystemClock.uptimeMillis();
        new Thread("MARsWatchdogUnfreezer") { // from class: com.android.server.am.FreecessController.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (!FreecessController.this.mFrozenPidList.isEmpty()) {
                        Iterator it = new ArrayList(FreecessController.this.mFrozenPidList).iterator();
                        while (it.hasNext()) {
                            Integer num = (Integer) it.next();
                            if (SystemClock.uptimeMillis() - uptimeMillis >= 5000) {
                                break;
                            } else if (num != null) {
                                FreecessController.this.releaseFreezedAppPid(num.intValue());
                            }
                        }
                    }
                } catch (Exception e) {
                    Slog.e(FreecessController.TAG, "Error occurred while handleResetAllPreAction: " + e);
                }
                Slog.w(FreecessController.TAG, "!@*** unFreezeAllPackages for watchdog : End thread for preaction unfreezing!!!");
            }
        }.start();
    }

    public void handleUnfreezeActivePackages(String str) {
        if ("screenOn-widget".equals(str)) {
            unFreezeWidgetPackages(str);
        } else {
            unFreezeActivePackages(str);
        }
    }

    public void handleUnfreezeRequestFocusPackage() {
        String requestFocusPkg;
        WindowManagerService windowManagerService;
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerService activityManagerService2 = this.mAm;
                requestFocusPkg = (activityManagerService2 == null || (windowManagerService = activityManagerService2.mWindowManager) == null) ? null : windowManagerService.getRequestFocusPkg();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        if (isFreezedPackage(requestFocusPkg, this.mContext.getUserId())) {
            unFreezePackage(requestFocusPkg, this.mContext.getUserId(), "has Focus");
        }
    }

    public void lcdOnFreezePackage(String str, int i) {
        if (this.mIsScreenOnFreecessEnabled) {
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    freezeBgPackageLocked(str, i);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public void setWakeLockEnableDisable(String str, int i, boolean z, boolean z2) {
        if (this.mLocalPowerManager == null) {
            this.mLocalPowerManager = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        }
        int wakeLockEnableDisable = this.mLocalPowerManager.setWakeLockEnableDisable(i, z);
        if (wakeLockEnableDisable == 1) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(z ? "disable" : "enable");
            sb.append(" frozen app (");
            sb.append(str);
            sb.append(",");
            sb.append(i);
            sb.append(") wakelock.");
            Slog.d(str2, sb.toString());
            return;
        }
        if (wakeLockEnableDisable == 2) {
            if (z2) {
                if (this.mCalmModeEnabled) {
                    return;
                }
                unFreezePackage(str, UserHandle.getUserId(i), "Wakelock");
            } else {
                if (UidStateMgr.getInstance().isUidIdle(i)) {
                    return;
                }
                unFreezePackage(str, UserHandle.getUserId(i), "Wakelock");
            }
        }
    }

    public void onFreezeStateChanged(boolean z, int i) {
        if (this.mAlarmManagerInternal == null) {
            this.mAlarmManagerInternal = (AlarmManagerInternal) LocalServices.getService(AlarmManagerInternal.class);
        }
        this.mAlarmManagerInternal.onFreezeStateChanged(z, i);
        if (getLRsEnabled()) {
            try {
                if (this.mLocationManager == null) {
                    this.mLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
                }
                ILocationManager iLocationManager = this.mLocationManager;
                if (iLocationManager != null) {
                    iLocationManager.onFreezeStateChanged(z, i);
                }
            } catch (RemoteException e) {
                Slog.e(TAG, "Error occurred while setLRs(" + z + ", " + i + "): " + e);
            }
        }
        if (z) {
            addUidToReleaseRestrictionList(i);
        }
    }

    public void addUidToReleaseRestrictionList(int i) {
        synchronized (this.mSetUidsNeedToReleaseRestriction) {
            if (!this.mSetUidsNeedToReleaseRestriction.contains(Integer.valueOf(i))) {
                this.mSetUidsNeedToReleaseRestriction.add(Integer.valueOf(i));
            }
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "add uid " + i + " to release restriction list");
        }
    }

    public void addRestrictedPackages(FreecessPkgStatus freecessPkgStatus) {
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "addRestrictedPackages uid: " + freecessPkgStatus.uid);
        }
        synchronized (MARsPolicyManager.MARsLock) {
            if (this.mRestrictedPackages.getByUid(freecessPkgStatus.uid) == null) {
                this.mRestrictedPackages.put(freecessPkgStatus.uid, freecessPkgStatus.name, freecessPkgStatus);
            }
        }
    }

    public void removeRestrictedPackages(FreecessPkgStatus freecessPkgStatus) {
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(TAG, "removeRestrictedPackages uid: " + freecessPkgStatus.uid);
        }
        synchronized (MARsPolicyManager.MARsLock) {
            if (this.mRestrictedPackages.getByUid(freecessPkgStatus.uid) != null) {
                this.mRestrictedPackages.remove(freecessPkgStatus.uid, freecessPkgStatus.name);
            }
        }
    }

    public void releaseRestriction(int i) {
        synchronized (this.mSetUidsNeedToReleaseRestriction) {
            if (this.mSetUidsNeedToReleaseRestriction.contains(Integer.valueOf(i))) {
                this.mSetUidsNeedToReleaseRestriction.remove(Integer.valueOf(i));
                if (MARsDebugConfig.DEBUG_ENG) {
                    Slog.d(TAG, "release restriction for uid " + i);
                }
                FreecessHandler.getInstance().sendOnFreezeStateChanged(false, i);
            }
        }
    }

    public boolean freezeTargetProcess(String str, int i) {
        return (str != null && ("android.app.stubs".equals(str) || str.contains("com.android.app.cts."))) || UserHandle.isCore(i);
    }

    public void dumpFreecess(FileDescriptor fileDescriptor, PrintWriter printWriter) {
        String str;
        char c;
        boolean isChinaPolicyEnabled = MARsPolicyManager.getInstance().isChinaPolicyEnabled();
        synchronized (MARsPolicyManager.MARsLock) {
            printWriter.println("FREECESS STATE");
            printWriter.print("-FreecessEnabled : ");
            printWriter.print(getFreecessEnabled() ? "Y" : "N");
            printWriter.print("-KernelSupport : ");
            printWriter.print(this.mIsKernelSupportFreecess ? "Y" : "N");
            printWriter.print(" -FreecessEnhancementEnabled : ");
            printWriter.print(getFreecessEnhancementEnabledState() ? "Y" : "N");
            printWriter.print(" -ChinaPolicyEnabled : ");
            printWriter.print(isChinaPolicyEnabled ? "Y" : "N");
            printWriter.print(" -NewPILOT : ");
            printWriter.print(IS_PILOT_PROJECT ? "Y" : "N");
            printWriter.print(" -Mimimize_OLAF_lock : ");
            printWriter.print(IS_MINIMIZE_OLAF_LOCK ? "Y" : "N");
            printWriter.print(" -QuickFreezeEnabled : ");
            printWriter.print(isQuickFreezeEnabled() ? "Y" : "N");
            printWriter.print(" -PidUfzEnabled : ");
            printWriter.print(isPidUfzEnabled() ? "Y" : "N");
            printWriter.print(" -LRsEnabled : ");
            printWriter.print(this.FREECESS_LRS_ENABLED ? "Y" : "N");
            printWriter.print(" -AUFAllowBucketSize : ");
            printWriter.print(MARsPolicyManager.AUFAllowBucketSize);
            printWriter.print(" -cntFail(FZ/UFZ/UFZ_P) : ");
            printWriter.print("" + this.cntFailFreeze + "/" + this.cntFailUnfreeze + "/" + this.cntFailUnfreezePilot);
            printWriter.println("");
            printWriter.println("ACTIVITY MANAGER Freecess (dumpsys activity freecess)");
            printWriter.print("mFreecessManagedPackages --- size ");
            printWriter.println(this.mFreecessManagedPackages.totalSize());
            int i = 7;
            int i2 = 0;
            char c2 = 6;
            printWriter.println(String.format("%-9s%-6s%-5s%-4s%-2s|%s %s", "Uid", "Share", "Idle", "Top", "S", MARsFreezeStateRecord.getDumpUnfreezeTitle(), "Pkg"));
            int i3 = 0;
            while (i3 < this.mFreecessManagedPackages.getUserIdMap().size()) {
                SparseArray sparseArray = (SparseArray) this.mFreecessManagedPackages.getUserIdMap().valueAt(i3);
                int i4 = i2;
                while (i4 < sparseArray.size()) {
                    FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) sparseArray.valueAt(i4);
                    if (freecessPkgStatus == null) {
                        c = c2;
                    } else {
                        Object[] objArr = new Object[i];
                        objArr[i2] = Integer.valueOf(freecessPkgStatus.uid);
                        objArr[1] = freecessPkgStatus.sharedUidName != null ? "Y" : PackageManagerShellCommandDataLoader.STDIN_PATH;
                        objArr[2] = UidStateMgr.getInstance().isUidIdle(freecessPkgStatus.uid) ? "Y" : PackageManagerShellCommandDataLoader.STDIN_PATH;
                        objArr[3] = UidStateMgr.getInstance().isUidTop(freecessPkgStatus.uid) ? "Y" : PackageManagerShellCommandDataLoader.STDIN_PATH;
                        if (freecessPkgStatus.freezedRecord.isFreezed) {
                            str = "F";
                        } else {
                            str = UidStateMgr.getInstance().isUidRunning(freecessPkgStatus.uid) ? "R" : PackageManagerShellCommandDataLoader.STDIN_PATH;
                        }
                        objArr[4] = str;
                        objArr[5] = freecessPkgStatus.freezedRecord.dumpUfzContent();
                        c = 6;
                        objArr[6] = freecessPkgStatus.name;
                        printWriter.println(String.format("%-9d%3s%5s%5s%3s |%s %s", objArr));
                    }
                    i4++;
                    c2 = c;
                    i = 7;
                    i2 = 0;
                }
                i3++;
                i = 7;
                i2 = 0;
            }
            for (int i5 = 0; i5 < this.mFreecessManagedPackages.getUserIdMap().size(); i5++) {
                SparseArray sparseArray2 = (SparseArray) this.mFreecessManagedPackages.getUserIdMap().valueAt(i5);
                for (int i6 = 0; i6 < sparseArray2.size(); i6++) {
                    FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) sparseArray2.valueAt(i6);
                    if (freecessPkgStatus2.freezedRecord.hasUnfreezeAbuseHistory()) {
                        printWriter.println(freecessPkgStatus2.name + "since " + formatDateTime(freecessPkgStatus2.freezedRecord.getInitialStateTime()) + " has abuse unfreeze " + freecessPkgStatus2.freezedRecord.getUnfreezeAbuseDetections().size() + " ---last abnormal time: " + formatDateTime(freecessPkgStatus2.freezedRecord.getLastUnfreezeAbuseTime()));
                    }
                }
            }
            printWriter.print("mFreezedPackages --- size ");
            printWriter.println(this.mFreezedPackages.totalSize());
            for (int i7 = 0; i7 < this.mFreezedPackages.getUserIdMap().size(); i7++) {
                SparseArray sparseArray3 = (SparseArray) this.mFreezedPackages.getUserIdMap().valueAt(i7);
                for (int i8 = 0; i8 < sparseArray3.size(); i8++) {
                    FreecessPkgStatus freecessPkgStatus3 = (FreecessPkgStatus) sparseArray3.valueAt(i8);
                    printWriter.print("-FZT ");
                    printWriter.print(formatDateTime(freecessPkgStatus3.freezedRecord.freezedTime));
                    printWriter.print("-UFZT ");
                    printWriter.print(formatDateTime(freecessPkgStatus3.freezedRecord.unfreezedTime));
                    printWriter.print("-UFZR ");
                    printWriter.print(String.format("%10s", freecessPkgStatus3.freezedRecord.unfreezedReason));
                    printWriter.print("-UserId ");
                    printWriter.print(String.format("%3d", Integer.valueOf(freecessPkgStatus3.userId)));
                    printWriter.print("-Pkg ");
                    printWriter.print(freecessPkgStatus3.name);
                    printWriter.println("");
                }
            }
            printWriter.print("mMonitorFreezedList --- size ");
            printWriter.println(this.mMonitorFreezedList.size());
            for (int i9 = 0; i9 < this.mMonitorFreezedList.size(); i9++) {
                printWriter.print(String.format("%d ", this.mMonitorFreezedList.get(i9)));
            }
            printWriter.println("");
        }
        if (FreecessHandler.getInstance().mMainHandler != null) {
            FreecessHandler.getInstance().mMainHandler.dump(new PrintWriterPrinter(printWriter), "Freecess");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void dumpFreecessCommand(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        StringBuilder sb;
        SparseArray sparseArray;
        StringBuilder sb2;
        int i2;
        int i3;
        WindowManagerService windowManagerService;
        boolean z = true;
        if ("list".equals(strArr[1])) {
            dumpFreecess(fileDescriptor, printWriter);
        }
        int i4 = 0;
        if ("freecess".equals(strArr[1])) {
            if (strArr.length < 3) {
                Slog.e(TAG, "dumpMARsCommand(freecess ) !" + strArr[1]);
                turnOnOffFreecessMonitor(false);
                printWriter.println("MARstest -- freecess enabled has turned to false");
            } else if ("on".equals(strArr[2])) {
                if ("1".equals(strArr[3])) {
                    turnOnOffFreecessMonitor(true);
                } else if ("0".equals(strArr[3])) {
                    Slog.e(TAG, "dumpMARsCommand(freecess off) ! +   " + strArr[2]);
                    FreecessHandler.getInstance().removeBgTriggerMsg();
                    FreecessHandler.getInstance().sendResetAllStateMsg("Debug");
                    turnOnOffFreecessMonitor(false);
                }
            } else if ("off".equals(strArr[2])) {
                if ("1".equals(strArr[3])) {
                    turnOnOffFreecessMonitor(true);
                } else if ("0".equals(strArr[3])) {
                    turnOnOffFreecessMonitor(false);
                }
            }
        }
        if (KnoxVpnFirewallHelper.NETD_SERVICE_NAME.equals(strArr[1])) {
            if ("on".equals(strArr[2])) {
                try {
                    setFirewallChainEnabled(7, true);
                    updateFreezedUidFirewall(Integer.parseInt(strArr[3]), false);
                    closeSocketsForFreecessFirewallChain();
                    Slog.e(TAG, "dumpMARsCommand(netd on)uid = " + Integer.parseInt(strArr[3]));
                } catch (NumberFormatException unused) {
                    Slog.e(TAG, "dumpMARsCommand(netd on) parseInt error!");
                }
            } else if ("off".equals(strArr[2])) {
                try {
                    updateFreezedUidFirewall(Integer.parseInt(strArr[3]), true);
                    setFirewallChainEnabled(7, false);
                    Slog.e(TAG, "dumpMARsCommand(netd off)uid = " + Integer.parseInt(strArr[3]));
                } catch (NumberFormatException unused2) {
                    Slog.e(TAG, "dumpMARsCommand(netd off) parseInt error!");
                }
            }
        }
        if ("olaf".equals(strArr[1])) {
            if ("on".equals(strArr[2])) {
                this.mIsOLAFEnabled = true;
            } else if ("off".equals(strArr[2])) {
                this.mIsOLAFEnabled = false;
            } else if ("debug".equals(strArr[2])) {
                MARsDebugConfig.DEBUG_OLAF = !MARsDebugConfig.DEBUG_OLAF;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("olaf debugging mode is ");
                sb3.append(MARsDebugConfig.DEBUG_OLAF ? "on" : "off");
                sb3.append(" now!");
                printWriter.println(sb3.toString());
            } else if ("enter".equals(strArr[2])) {
                enterOLAF(Integer.parseInt(strArr[3]));
            } else if ("exit".equals(strArr[2])) {
                exitOLAF();
            } else if ("allowlist".equals(strArr[2])) {
                if ("add".equals(strArr[3])) {
                    this.mOLAFAllowListForDebug.add(strArr[4]);
                } else if ("remove".equals(strArr[3])) {
                    this.mOLAFAllowListForDebug.remove(strArr[4]);
                } else if ("clear".equals(strArr[3])) {
                    this.mOLAFAllowListForDebug.clear();
                } else if ("list".equals(strArr[3])) {
                    printWriter.println("list size: " + this.mOLAFAllowListForDebug.size());
                    Iterator it = this.mOLAFAllowListForDebug.iterator();
                    while (it.hasNext()) {
                        printWriter.print(((String) it.next()) + " ");
                    }
                    printWriter.println("");
                }
            }
        }
        if ("qkfz".equals(strArr[1])) {
            if ("switch".equals(strArr[2])) {
                this.mIsQuickFreezeEnabled = !this.mIsQuickFreezeEnabled;
            } else if ("on".equals(strArr[2])) {
                this.mIsQuickFreezeEnabled = true;
            } else if ("off".equals(strArr[2])) {
                this.mIsQuickFreezeEnabled = false;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Quick fz is ");
            sb4.append(this.mIsQuickFreezeEnabled ? "on" : "off");
            sb4.append(" now!");
            printWriter.println(sb4.toString());
        }
        if ("frz".equals(strArr[1])) {
            freezePackage(strArr[2], 0, "force", 2, false, false);
        }
        if ("ufz".equals(strArr[1])) {
            unFreezePackage(strArr[2], 0, "force");
        }
        if ("lrs".equals(strArr[1])) {
            if ("on".equals(strArr[2])) {
                setLRsEnabled(true);
                printWriter.println("lrs set enable");
            } else if ("off".equals(strArr[2])) {
                setLRsEnabled(false);
                printWriter.println("lrs set disable");
            }
        }
        if ("uid_idle".equals(strArr[1])) {
            if (strArr.length < 3) {
                setUidIdleCheckMode(!getUidIdleCheckMode());
                printWriter.println("MARstest -- uid idle check mode has turned to " + getInstance().getUidIdleCheckMode());
            } else if ("on".equals(strArr[2])) {
                setUidIdleCheckMode(true);
            } else if ("off".equals(strArr[2])) {
                setUidIdleCheckMode(false);
            }
        }
        if ("window".equals(strArr[1])) {
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    try {
                        int parseInt = Integer.parseInt(strArr[2]);
                        ActivityManagerService activityManagerService2 = this.mAm;
                        boolean z2 = (activityManagerService2 == null || (windowManagerService = activityManagerService2.mWindowManager) == null || !windowManagerService.hasFloatingOrOnScreenWindow(parseInt)) ? false : true;
                        Slog.d(TAG, "dumpMARsCommand(window) " + parseInt + " ishasFloatingWindow:" + z2);
                    } catch (NumberFormatException unused3) {
                        Slog.e(TAG, "dumpMARsCommand(window) parseInt error!");
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
        if ("mfsr".equals(strArr[1])) {
            for (int i5 = 0; i5 < this.mFreecessManagedPackages.getUserIdMap().size(); i5++) {
                SparseArray sparseArray2 = (SparseArray) this.mFreecessManagedPackages.getUserIdMap().valueAt(i5);
                for (int i6 = 0; i6 < sparseArray2.size(); i6++) {
                    ((FreecessPkgStatus) sparseArray2.valueAt(i6)).freezedRecord.dumpCmd(printWriter, strArr);
                }
            }
        }
        if ("startrecording".equals(strArr[1])) {
            this.mRecordingStartTime = System.currentTimeMillis();
        }
        if ("endrecording".equals(strArr[1])) {
            this.mRecordingEndTime = System.currentTimeMillis();
            StringBuilder sb5 = new StringBuilder();
            long j = 64;
            if (MARsDebugConfig.DEBUG_ENG) {
                Trace.traceBegin(64L, "getFrozenTimeForAllPackages");
            }
            synchronized (MARsPolicyManager.MARsLock) {
                SparseArray uidMap = this.mFreecessManagedPackages.getUidMap();
                int i7 = 0;
                while (i7 < uidMap.size()) {
                    FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) uidMap.valueAt(i7);
                    if (freecessPkgStatus == null || freecessPkgStatus.freezedRecord == null) {
                        sparseArray = uidMap;
                        sb2 = sb5;
                        i2 = i4;
                        i3 = i7;
                    } else {
                        if (MARsDebugConfig.DEBUG_ENG) {
                            Trace.traceBegin(j, "getFrozenTimeFor " + freecessPkgStatus.uid);
                        }
                        MARsFreezeStateRecord mARsFreezeStateRecord = freecessPkgStatus.freezedRecord;
                        mARsFreezeStateRecord.compute(this.mRecordingStartTime, this.mRecordingEndTime, (!mARsFreezeStateRecord.isFreezed || mARsFreezeStateRecord.isOLAFFreezed) ? i4 : z);
                        long longValue = freecessPkgStatus.freezedRecord.getUidRunningTime().longValue();
                        long longValue2 = freecessPkgStatus.freezedRecord.getFrozenTime().longValue();
                        ArrayList unfreezeCounts = freecessPkgStatus.freezedRecord.getUnfreezeCounts();
                        i3 = i7;
                        sparseArray = uidMap;
                        double frozenProportion = getFrozenProportion(longValue, longValue2, freecessPkgStatus.freezedRecord.mFreezeEventRecorder, freecessPkgStatus.uid);
                        String printUnfreezeCounts = printUnfreezeCounts(unfreezeCounts);
                        sb2 = sb5;
                        sb2.append(freecessPkgStatus.name);
                        sb2.append(" ");
                        i2 = 0;
                        sb2.append(String.format("%.5f", Double.valueOf(frozenProportion)));
                        sb2.append(" ");
                        sb2.append(longValue2);
                        sb2.append(" ");
                        sb2.append(longValue);
                        sb2.append(" ");
                        sb2.append(printUnfreezeCounts);
                        sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                        if (MARsDebugConfig.DEBUG_ENG) {
                            Trace.traceEnd(64L);
                        }
                    }
                    i7 = i3 + 1;
                    i4 = i2;
                    sb5 = sb2;
                    uidMap = sparseArray;
                    z = true;
                    j = 64;
                }
                sb = sb5;
                i = i4;
            }
            if (MARsDebugConfig.DEBUG_ENG) {
                Trace.traceEnd(64L);
            }
            printWriter.println("From " + new Date(this.mRecordingStartTime) + " to " + new Date(this.mRecordingEndTime) + "(" + (this.mRecordingEndTime - this.mRecordingStartTime) + "ms)");
            printWriter.println(sb);
        } else {
            i = 0;
        }
        if ("debugrecord".equals(strArr[1])) {
            synchronized (MARsPolicyManager.MARsLock) {
                SparseArray uidMap2 = this.mFreecessManagedPackages.getUidMap();
                for (int i8 = i; i8 < uidMap2.size(); i8++) {
                    FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) uidMap2.valueAt(i8);
                    if (freecessPkgStatus2 != null && freecessPkgStatus2.freezedRecord != null) {
                        Slog.d(TAG, "name : " + freecessPkgStatus2.name + " uid : " + freecessPkgStatus2.uid);
                        freecessPkgStatus2.freezedRecord.mFreezeEventRecorder.printAllEvents();
                    }
                }
            }
        }
    }

    public final double getFrozenProportion(double d, double d2, MARsFreezeStateRecord.EventRecorder eventRecorder, int i) {
        double d3 = d == 0.0d ? -1.0d : d2 / d;
        if (d3 > 1.0d) {
            Slog.e("MARsFreezeStateRecord", "calculation error. " + i + "was frozen more than running time");
            if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
                Slog.d("MARsFreezeStateRecord", "From " + new Date(this.mRecordingStartTime) + " to " + new Date(this.mRecordingEndTime) + "(" + (this.mRecordingEndTime - this.mRecordingStartTime) + "ms)");
                eventRecorder.printAllEvents();
            }
        }
        return d3;
    }

    public static String printUnfreezeCounts(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append((Integer) it.next());
            sb.append(" ");
        }
        return sb.toString();
    }

    public final String formatDateTime(long j) {
        if (j == 0) {
            return String.format("%23s", "null");
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date(j));
    }

    public void readSysfs() {
        String str;
        StringBuilder sb;
        String readLine;
        Set set;
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                BufferedReader bufferedReader4 = new BufferedReader(new FileReader("/data/system/ssrm_local_freecess"));
                while (true) {
                    try {
                        readLine = bufferedReader4.readLine();
                        if (readLine == null) {
                            try {
                                break;
                            } catch (IOException e) {
                                e = e;
                                str = TAG;
                                sb = new StringBuilder();
                                sb.append("e = ");
                                sb.append(e.getMessage());
                                Slog.e(str, sb.toString());
                                return;
                            }
                        }
                        if (readLine.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                            r4 = false;
                            boolean z = false;
                            r4 = false;
                            boolean z2 = false;
                            r4 = false;
                            boolean z3 = false;
                            r4 = false;
                            boolean z4 = false;
                            String[] split = readLine.split(XmlUtils.STRING_ARRAY_SEPARATOR, 0);
                            if (split.length > 2) {
                                String str2 = split[0];
                                String substring = str2 != null ? str2.substring(0, str2.indexOf(PackageManagerShellCommandDataLoader.STDIN_PATH)) : "";
                                String str3 = split[1];
                                if (str3 == null) {
                                    str3 = "";
                                }
                                String str4 = split[2];
                                String str5 = str4 != null ? str4 : "";
                                if ("ALL".equals(substring)) {
                                    if ("WL".equals(str3)) {
                                        if (str5 != null) {
                                            for (String str6 : str5.split("@", 0)) {
                                                if (str6 != null && !this.mSsrmAllowPackages.contains(str6)) {
                                                    this.mSsrmAllowPackages.add(MARsVersionManager.toNormalText(str6));
                                                }
                                            }
                                        }
                                    } else if ("Switch".equals(str3)) {
                                        if ("1".equals(str5) && getFreecessEnabled()) {
                                            z4 = true;
                                        }
                                        turnOnOffFreecessMonitor(z4);
                                    } else if ("Enhance_Switch".equals(str3)) {
                                        if ("1".equals(str5) && getFreecessEnhancementEnabledState()) {
                                            z3 = true;
                                        }
                                        setFreeceeEnhanceEnabled(z3);
                                    }
                                } else if (mCountry.equals(substring)) {
                                    if ("WL".equals(str3)) {
                                        if (str5 != null && (set = this.mSsrmAllowPackages) != null) {
                                            set.clear();
                                            for (String str7 : str5.split("@", 0)) {
                                                if (str7 != null && !this.mSsrmAllowPackages.contains(str7)) {
                                                    this.mSsrmAllowPackages.add(MARsVersionManager.toNormalText(str7));
                                                }
                                            }
                                        }
                                    } else if ("Switch".equals(str3)) {
                                        if ("1".equals(str5) && getFreecessEnabled()) {
                                            z2 = true;
                                        }
                                        turnOnOffFreecessMonitor(z2);
                                    } else if ("Enhance_Switch".equals(str3)) {
                                        if ("1".equals(str5) && getFreecessEnhancementEnabledState()) {
                                            z = true;
                                        }
                                        setFreeceeEnhanceEnabled(z);
                                    }
                                }
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader4;
                        Slog.e(TAG, "e = " + e.getMessage());
                        bufferedReader = bufferedReader2;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                                bufferedReader = bufferedReader2;
                            } catch (IOException e3) {
                                e = e3;
                                str = TAG;
                                sb = new StringBuilder();
                                sb.append("e = ");
                                sb.append(e.getMessage());
                                Slog.e(str, sb.toString());
                                return;
                            }
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedReader3 = bufferedReader4;
                        Slog.e(TAG, "e = " + e.getMessage());
                        bufferedReader = bufferedReader3;
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                                bufferedReader = bufferedReader3;
                            } catch (IOException e5) {
                                e = e5;
                                str = TAG;
                                sb = new StringBuilder();
                                sb.append("e = ");
                                sb.append(e.getMessage());
                                Slog.e(str, sb.toString());
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
                                Slog.e(TAG, "e = " + e6.getMessage());
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader4.close();
                bufferedReader = readLine;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e7) {
            e = e7;
        } catch (Exception e8) {
            e = e8;
        }
    }

    public void reportProcessFreezableChangedLocked(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ProcessRecord processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(((Integer) it.next()).intValue());
            if (processRecordFromPidLocked != null) {
                ActivityManagerService activityManagerService = this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        this.mAm.onProcessFreezableChangedLocked(processRecordFromPidLocked);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public boolean initSendRecvMsgNetLink() {
        try {
            FileDescriptor forProto = NetlinkSocket.forProto(NETLINK_KFREECESS);
            this.mSendRecvNetLinkFd = forProto;
            NetlinkSocket.connectToKernel(forProto);
            DEFAULT_RECV_BUFSIZE = FreecessNetlinkMessage.getFreecessNetlinkMessageSize();
            for (int i = 1; i <= 4; i++) {
                byte[] newFreecessRequest = FreecessNetlinkMessage.newFreecessRequest(1, i, 0, 0, 0);
                if (NetlinkSocket.sendMessage(this.mSendRecvNetLinkFd, newFreecessRequest, 0, newFreecessRequest.length, 500L) < 0) {
                    closeSocketNetLink(this.mSendRecvNetLinkFd);
                    this.mSendRecvNetLinkFd = null;
                    Slog.e(TAG, "initSendRecvMsgNetLink sendMessage error");
                    return false;
                }
                StructFreeCessMsg parse = StructFreeCessMsg.parse(NetlinkSocket.recvMessage(this.mSendRecvNetLinkFd, DEFAULT_RECV_BUFSIZE, 500L));
                if (parse != null) {
                    if (parse.version != 268435456) {
                        this.mMismatchFlag = true;
                    }
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d(TAG, "initSendRecvNetlink type:" + parse.type + " mod:" + parse.mod + " src_portid:" + parse.src_portid + " dst_portid:" + parse.dst_portid + " version:" + parse.version + " target_uid:" + parse.target_uid + " flag:" + parse.flag + " code:" + parse.code + " cmd:" + parse.cmd + " uid:" + parse.uid);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            closeSocketNetLink(this.mSendRecvNetLinkFd);
            this.mSendRecvNetLinkFd = null;
            return false;
        }
    }

    public final void closeSocketNetLink(FileDescriptor fileDescriptor) {
        if (fileDescriptor != null) {
            try {
                SocketUtils.closeSocket(fileDescriptor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int sendFreecessMsg2kernel(int i, int i2, int i3, int i4) {
        try {
            byte[] newFreecessRequest = FreecessNetlinkMessage.newFreecessRequest(2, i, i2, i3, i4);
            FileDescriptor fileDescriptor = this.mSendRecvNetLinkFd;
            if (fileDescriptor != null) {
                return NetlinkSocket.sendMessage(fileDescriptor, newFreecessRequest, 0, newFreecessRequest.length, 500L);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void recvNetLinkAction() {
        StructFreeCessMsg parse;
        try {
            FileDescriptor fileDescriptor = this.mSendRecvNetLinkFd;
            if (fileDescriptor == null || (parse = StructFreeCessMsg.parse(NetlinkSocket.recvMessage(fileDescriptor, DEFAULT_RECV_BUFSIZE, 5000000000L))) == null) {
                return;
            }
            if (MARsDebugConfig.DEBUG_NETLINK) {
                Slog.d(TAG, "RecvNetlink type:" + parse.type + " mod:" + parse.mod + " src_portid:" + parse.src_portid + " dst_portid:" + parse.dst_portid + " version:" + parse.version + " target_uid:" + parse.target_uid + " flag:" + parse.flag + " code:" + parse.code + " cmd:" + parse.cmd + " uid:" + parse.uid);
            }
            kernelFreecessReport(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void kernelFreecessReport(StructFreeCessMsg structFreeCessMsg) {
        String trim;
        int i = structFreeCessMsg.mod;
        int i2 = 1;
        if (i != 1) {
            if (i == 2) {
                reportSignal(structFreeCessMsg.target_uid, structFreeCessMsg.flag);
                return;
            } else if (i == 3) {
                reportPacketUid(structFreeCessMsg.target_uid);
                return;
            } else {
                if (i != 4) {
                    return;
                }
                reportCFBUid(structFreeCessMsg.target_uid);
                return;
            }
        }
        if (this.mMismatchFlag) {
            trim = "mismatch";
        } else {
            trim = new String(structFreeCessMsg.rpcname).trim();
            i2 = 0;
        }
        reportBinderUid(0, structFreeCessMsg.target_uid, structFreeCessMsg.cmd, structFreeCessMsg.code, trim, structFreeCessMsg.flag, i2);
    }

    public final void reportBinderUid(int i, int i2, int i3, int i4, String str, int i5, int i6) {
        if (i5 == 1) {
            reportAsyncBinder(i, i2, i3, i4, str, i6);
            return;
        }
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d(TAG, "Receive binder-module info(callerPid:" + i + ",uid:" + i2 + ",code:" + i4 + ",interfaceToken:" + str + ",flag:" + i5 + ") from kernel");
        }
        if (killPkgForCalmMode(i2, "Binder(0)")) {
            return;
        }
        unFreezePackage(i2, "Binder(0)");
    }

    public final void reportPacketUid(int i) {
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d(TAG, "Receive packet-module info(uid:" + i + ") from kernel");
        }
        if (isFrozenByCalmMode(i)) {
            return;
        }
        unFreezePackage(i, "Packet");
    }

    public void reportSocketResult(boolean z) {
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d(TAG, "Receive socket exception from kernel");
        }
        this.FREECESS_ENHANCEMENT = z;
        this.mIsOLAFEnabled = z;
        this.mIsFreecessEnable = z;
        turnOnOffFreecessMonitor(z);
        this.mIsKernelSupportFreecess = z;
        if (z) {
            registerFreecessTrigger();
        }
    }

    public final void reportCFBUid(int i) {
        if (MARsDebugConfig.DEBUG_NETLINK) {
            Slog.d(TAG, "Receive cfb-module info(uid:" + i + ") from kernel");
        }
        if (killPkgForCalmMode(i, "Cfb")) {
            return;
        }
        unFreezePackage(i, "Cfb");
    }

    public void setFreecessPolicyFromSCPM(String str) {
        try {
            this.mFreecessPolicy = new FreecessPolicy();
            JSONObject jSONObject = new JSONObject(str);
            this.mFreecessPolicy.setMasterSwitch(jSONObject.getBoolean("master_switch"));
            JSONArray jSONArray = jSONObject.getJSONArray("freeze_enhanced_mode");
            this.mFreecessPolicy.setQuickFreezeEnabled("true".equals(jSONArray.get(0).toString()));
            this.mFreecessPolicy.setLessUnfreezeEnabled("true".equals(jSONArray.get(1).toString()));
            JSONArray jSONArray2 = jSONObject.getJSONArray("freeze_timeout");
            this.mFreecessPolicy.setQuickFreezeCheckTime(Integer.parseInt(jSONArray2.get(0).toString()));
            this.mFreecessPolicy.setQuickFreezeIntervalTime(Integer.parseInt(jSONArray2.get(1).toString()));
            this.mFreecessPolicy.setGoogleFreezeTime(Integer.parseInt(jSONObject.getJSONArray("google_freeze_timeout").get(0).toString()));
            JSONArray jSONArray3 = jSONObject.getJSONArray("google_freezer_exemption_list");
            for (int i = 0; i < jSONArray3.length(); i++) {
                this.mFreecessPolicy.addGoogleFreezeExemptionPackage(jSONArray3.get(i).toString());
            }
            this.mIsFreecessEnable = this.mFreecessPolicy.getMasterSwitch();
            this.mIsQuickFreezeEnabled = this.mFreecessPolicy.getQuickFreezeEnabled();
            this.mPidUnfreezeEnabled = this.mFreecessPolicy.getLessUnfreezeEnabled();
            FreecessHandler.getInstance().setQuickFrecessCheckTime(this.mFreecessPolicy.getQuickFreezeCheckTime());
            FreecessHandler.getInstance().setQuickFrecessIntervalTime(this.mFreecessPolicy.getQuickFreezeIntervalTime());
            this.mAm.mOomAdjuster.mCachedAppOptimizer.setFreezerDebounceTimeout(this.mFreecessPolicy.getGoogleFreezeTime());
            if (this.mFreecessPolicy.getGoogleFreezeExemptionList() != null) {
                MARsExemptionManager.getInstance().setExemptionList(this.mFreecessPolicy.getGoogleFreezeExemptionList());
            }
        } catch (Exception e) {
            Slog.e(TAG, "setFreecessPolicyFromSCPM Exception error!" + e);
        }
    }

    public boolean isFreecessTarget(int i) {
        boolean z;
        synchronized (MARsPolicyManager.MARsLock) {
            z = getPackageStatus(i) != null;
        }
        return z;
    }
}
