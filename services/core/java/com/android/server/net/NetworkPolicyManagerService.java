package com.android.server.net;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.INetworkPolicyListener;
import android.net.INetworkPolicyManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkIdentity;
import android.net.NetworkInfo;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkStack;
import android.net.NetworkStateSnapshot;
import android.net.NetworkTemplate;
import android.net.TetheringManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.BestClock;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.INetworkManagementService;
import android.os.Message;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerExemptionManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.SubscriptionPlan;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.DataUnit;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.RecurrenceRule;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.SparseSetArray;
import android.util.TimeUtils;
import android.util.Xml;
import android.widget.Toast;

import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.StatLogger;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.net.module.util.CollectionUtils;
import com.android.net.module.util.NetworkIdentityUtils;
import com.android.net.module.util.PermissionUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.connectivity.MultipathPolicyTracker;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.usage.AppStandbyInternal;
import com.android.server.usage.UsageStatsService;

import dalvik.annotation.optimization.NeverCompile;

import libcore.io.IoUtils;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NetworkPolicyManagerService extends INetworkPolicyManager.Stub {
    public static final int TYPE_LIMIT = 35;
    public static final int TYPE_LIMIT_SNOOZED = 36;
    public static final int TYPE_RAPID = 45;
    public static final int TYPE_WARNING = 34;
    static final int UID_MSG_STATE_CHANGED = 100;
    public volatile SparseArray isOffPeakObserverRegisted;
    public final ActiveDataSubIdListener mActiveDataSubIdListener;
    public int mActiveNetworkType;
    public final ArraySet mActiveNotifs;
    public ActivityManagerInternal mActivityManagerInternal;
    public final CountDownLatch mAdminDataAvailableLatch;
    public final AnonymousClass12 mAlertObserver;
    public final SparseBooleanArray mAppIdleTempWhitelistAppIds;
    public final AppOpsManager mAppOps;
    public AppStandbyInternal mAppStandby;
    public boolean mBackgroundNetworkRestricted;
    long mBackgroundRestrictionDelayMs;
    long mBackgroundRestrictionLongDelayMs;
    long mBackgroundRestrictionShortDelayMs;
    public final SparseLongArray mBackgroundTransitioningUids;
    public CallAttributesListener mCallAttributesListener;
    public final CarrierConfigManager mCarrierConfigManager;
    public final AnonymousClass5 mCarrierConfigReceiver;
    public boolean mChargingState;
    public final AnonymousClass5 mChargingStateReceiver;
    public final Clock mClock;
    public ConnectivityManager mConnManager;
    public final AnonymousClass5 mConnReceiver;
    public final Context mContext;
    public final AnonymousClass5 mDdsChangedReceiver;
    public int mDefaultDataPhoneId;
    public final SparseBooleanArray mDefaultRestrictBackgroundAllowlistUids;
    public final Dependencies mDeps;
    public volatile boolean mDeviceIdleMode;
    public final SparseBooleanArray mFirewallChainStates;
    public final AtomicFile mFirewallPolicyFile;
    public final ConcurrentHashMap mForegroundActivitiesPidMap;
    public final Handler mHandler;
    public final AnonymousClass15 mHandlerCallback;
    public boolean mHasEpdgCall;
    public final IPackageManager mIPm;
    public final SparseBooleanArray mInternetPermissionMap;
    public volatile boolean mIsVideoCall;
    public final RemoteCallbackList mListeners;
    public boolean mLoadedRestrictBackground;
    public final NetworkPolicyLogger mLogger;
    public volatile boolean mLowPowerStandbyActive;
    public final SparseBooleanArray mLowPowerStandbyAllowlistUids;
    public List mMergedSubscriberIds;
    public ArraySet mMeteredIfaces;
    public final Object mMeteredIfacesLock;
    public final SparseArray mMeteredRestrictedUids;
    public final MultipathPolicyTracker mMultipathPolicyTracker;
    public final SparseIntArray mNetIdToSubId;
    public final AnonymousClass11 mNetworkCallback;
    public final INetworkManagementService mNetworkManager;
    public volatile boolean mNetworkManagerReady;
    public final SparseBooleanArray mNetworkMetered;
    public final Object mNetworkPoliciesSecondLock;
    public final ArrayMap mNetworkPolicy;
    public final SparseBooleanArray mNetworkRoaming;
    public final NetworkStatsManager mNetworkStats;
    public final SparseSetArray mNetworkToIfaces;
    public long mNextProcessBackgroundUidsTime;
    public AnonymousClass19 mOffPeakContentObserver;
    public final ArraySet mOverLimitNotified;
    public final AnonymousClass5 mPackageReceiver;
    public final AtomicFile mPolicyFile;
    public final PowerExemptionManager mPowerExemptionManager;
    public PowerManagerInternal mPowerManagerInternal;
    public final AnonymousClass5 mPowerSaveAllowlistReceiver;
    public final SparseBooleanArray mPowerSaveTempWhitelistAppIds;
    public final SparseBooleanArray mPowerSaveWhitelistAppIds;
    public final SparseBooleanArray mPowerSaveWhitelistExceptIdleAppIds;
    public volatile boolean mRestrictBackground;
    public final SparseBooleanArray mRestrictBackgroundAllowlistRevokedUids;
    public boolean mRestrictBackgroundBeforeBsm;
    public volatile boolean mRestrictBackgroundChangedInBsm;
    public boolean mRestrictBackgroundLowPowerMode;
    public volatile boolean mRestrictPower;
    public volatile boolean mRestrictedNetworkingMode;
    public int mSetSubscriptionPlansIdCounter;
    public final SparseIntArray mSetSubscriptionPlansIds;
    public final AnonymousClass5 mSnoozeReceiver;
    public final StatLogger mStatLogger;
    public final StatsCallback mStatsCallback;
    public final SparseArray mSubIdToCarrierConfig;
    public final SparseArray mSubIdToSubscriberId;
    public final ConcurrentHashMap mSubscriberIdToSlotId;
    public final SparseLongArray mSubscriptionOpportunisticQuota;
    public final SparseArray mSubscriptionPlans;
    public final SparseArray mSubscriptionPlansOwner;
    public final boolean mSupportSmartManagerForChina;
    public final boolean mSuppressDefaultPolicy;
    public volatile boolean mSystemReady;
    public TelephonyManager mTelephonyManager;
    public final AnonymousClass18 mTetherListener;
    public long mTetheringNotiSnooze;
    public boolean mTetheringState;
    public final ConcurrentHashMap mTetheringWarningBytes;
    public AnonymousClass19 mTetheringWarningObserver;
    public final SparseArray mTmpUidBlockedState;
    public final ConcurrentHashMap mToastCheckedUidMap;
    public final SparseArray mUidBlockedState;
    final Handler mUidEventHandler;
    public final AnonymousClass15 mUidEventHandlerCallback;
    public final SparseIntArray mUidFirewallBackgroundRules;
    public final SparseIntArray mUidFirewallDozableRules;
    public final SparseIntArray mUidFirewallLowPowerStandbyModeRules;
    public final SparseIntArray mUidFirewallOemDenyRules;
    public final SparseIntArray mUidFirewallPowerSaveRules;
    public final SparseIntArray mUidFirewallRestrictedModeRules;
    public final SparseIntArray mUidFirewallStandbyRules;
    public final AnonymousClass4 mUidObserver;
    public final SparseIntArray mUidPolicy;
    public final AnonymousClass5 mUidRemovedReceiver;
    public final Object mUidRulesFirstLock;
    public final SparseArray mUidState;
    public final SparseArray mUidStateCallbackInfos;
    public UsageStatsManagerInternal mUsageStats;
    public boolean mUseMeteredFirewallChains;
    public final UserManager mUserManager;
    public final AnonymousClass5 mUserReceiver;
    public boolean mVideoCallLimitAlreadySent;
    public boolean mVideoCallWarningAlreadySent;
    public final AnonymousClass5 mWifiReceiver;
    public long preTotalBytes;
    public static final boolean LOGD = NetworkPolicyLogger.LOGD;
    public static final boolean LOGV = NetworkPolicyLogger.LOGV;
    public static final Object mFirewallPoliciesLock = new Object();
    public static final long QUOTA_UNLIMITED_DEFAULT = DataUnit.MEBIBYTES.toBytes(20);
    public static final SparseIntArray mFirewallRules = new SparseIntArray();
    public static final ConcurrentHashMap isOffPeakEnable = new ConcurrentHashMap();
    public static final String KEY_UNLIMITED_DATA_PLAN_WARN_SWITCH =
            "unlimited_data_plan_warn_switch";
    public static final String KEY_USAGE_PLAN_LIST = "usage_plan_choose";
    public static final String KEY_OFF_PEAK_DATA_START_TIME = "off_peak_start_time";
    public static final String KEY_OFF_PEAK_DATA_END_TIME = "off_peak_end_time";
    public static final String KEY_OFF_PEAK_DATA_SWITCH = "off_peak_data_switch";
    public static final String KEY_IS_IN_OFF_PEAK_TIME = "is_in_off_peak_time";
    public static final String KEY_OFF_PEAK_DATA_LIMIT = "off_peak_data_limit";
    public static final String KEY_SM_PROVIDER_CONTENT_URI =
            "content://com.samsung.android.sm.dcapi";
    public static final String KEY_SM_EXTRAS_SUBID = "subId";
    public static final String KEY_SM_PROVIDER_METHOR_UPDATE_POLICY = "updatePolicyFromSM";
    public static final String KEY_SM_PROVIDER_METHOR_UPDATE_ALARM = "updateAlarmFromSM";
    public static final String KEY_ONLY_SHOW_LIMIT_ALERT = "only_show_limit_alert";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActiveDataSubIdListener extends TelephonyCallback
            implements TelephonyCallback.ActiveDataSubscriptionIdListener {
        public int mActiveDataSubId;
        public int mDefaultDataSubId;

        public ActiveDataSubIdListener() {
            NetworkPolicyManagerService.this.mDeps.getClass();
            this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
            NetworkPolicyManagerService.this.mDeps.getClass();
            this.mActiveDataSubId = SubscriptionManager.getActiveDataSubscriptionId();
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public final void onActiveDataSubscriptionIdChanged(int i) {
            this.mActiveDataSubId = i;
            NetworkPolicyManagerService.this.mDeps.getClass();
            this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                NetworkPolicyManagerService.this.updateNotificationsNL();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallAttributesListener extends TelephonyCallback
            implements TelephonyCallback.CallAttributesListener {
        public CallAttributesListener() {}

        public final void onCallStatesChanged(List list) {
            String string =
                    SemCarrierFeature.getInstance()
                            .getString(
                                    0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
            if (string == null || !string.startsWith("VZW-")) {
                NetworkPolicyManagerService.this.getClass();
                if (!NetworkPolicyManagerService.isNaGsm("ATT")) {
                    NetworkPolicyManagerService.this.getClass();
                    if (!NetworkPolicyManagerService.isNaGsm("TMO")) {
                        return;
                    }
                }
            }
            boolean hasCall = NetworkPolicyManagerService.this.mTelephonyManager.hasCall("video");
            boolean hasCall2 =
                    NetworkPolicyManagerService.this.mTelephonyManager.hasCall("activevideo");
            NetworkPolicyManagerService networkPolicyManagerService =
                    NetworkPolicyManagerService.this;
            networkPolicyManagerService.mHasEpdgCall =
                    networkPolicyManagerService.mTelephonyManager.hasCall("epdg");
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    "NetworkPolicy",
                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                            "onCallStatesChanged - hasVideoCall: ",
                            hasCall,
                            ", hasActiveVideoCall: ",
                            hasCall2,
                            ", mHasEpdgCall: "),
                    NetworkPolicyManagerService.this.mHasEpdgCall);
            if (NetworkPolicyManagerService.this.mIsVideoCall != hasCall) {
                if (hasCall2) {
                    NetworkPolicyManagerService.this.mIsVideoCall = hasCall2;
                    Log.d("NetworkPolicy", "Video call start.");
                    synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                        NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                    }
                    return;
                }
                if (hasCall) {
                    return;
                }
                NetworkPolicyManagerService.this.mIsVideoCall = hasCall;
                NetworkPolicyManagerService networkPolicyManagerService2 =
                        NetworkPolicyManagerService.this;
                networkPolicyManagerService2.mVideoCallLimitAlreadySent = false;
                networkPolicyManagerService2.mVideoCallWarningAlreadySent = false;
                Log.d("NetworkPolicy", "Video call end.");
                synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                    NetworkPolicyManagerService.this.updateNetworkRulesNL();
                    NetworkPolicyManagerService.this.updateNotificationsNL();
                    NetworkPolicyManagerService.this.writePolicyAL();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Dependencies {
        public final NetworkStatsManager mNetworkStatsManager;

        public Dependencies(Context context) {
            NetworkStatsManager networkStatsManager =
                    (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
            this.mNetworkStatsManager = networkStatsManager;
            networkStatsManager.setPollOnOpen(false);
        }

        public final long getNetworkTotalBytes(NetworkTemplate networkTemplate, long j, long j2) {
            Trace.traceBegin(2097152L, "getNetworkTotalBytes");
            try {
                try {
                    NetworkStats.Bucket querySummaryForDevice =
                            this.mNetworkStatsManager.querySummaryForDevice(networkTemplate, j, j2);
                    return querySummaryForDevice.getRxBytes() + querySummaryForDevice.getTxBytes();
                } catch (RuntimeException e) {
                    Slog.w("NetworkPolicy", "Failed to read network stats: " + e);
                    Trace.traceEnd(2097152L);
                    return 0L;
                }
            } finally {
                Trace.traceEnd(2097152L);
            }
        }

        public final List getNetworkUidBytes(NetworkTemplate networkTemplate, long j, long j2) {
            Trace.traceBegin(2097152L, "getNetworkUidBytes");
            ArrayList arrayList = new ArrayList();
            try {
                try {
                    NetworkStats querySummary =
                            this.mNetworkStatsManager.querySummary(networkTemplate, j, j2);
                    while (querySummary.hasNextBucket()) {
                        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                        querySummary.getNextBucket(bucket);
                        arrayList.add(bucket);
                    }
                } catch (RuntimeException e) {
                    Slog.w("NetworkPolicy", "Failed to read network stats: " + e);
                }
                return arrayList;
            } finally {
                Trace.traceEnd(2097152L);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IfaceQuotas {
        public final String iface;
        public final long limit;
        public final long warning;

        public IfaceQuotas(long j, long j2, String str) {
            this.iface = str;
            this.warning = j;
            this.limit = j2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetPolicyAppIdleStateChangeListener
            extends AppStandbyInternal.AppIdleStateChangeListener {
        public NetPolicyAppIdleStateChangeListener() {}

        public final void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
            try {
                int packageUidAsUser =
                        NetworkPolicyManagerService.this
                                .mContext
                                .getPackageManager()
                                .getPackageUidAsUser(str, 8192, i);
                synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    NetworkPolicyManagerService.this.mLogger.appIdleStateChanged(
                            packageUidAsUser, z);
                    NetworkPolicyManagerService.this.updateRuleForAppIdleUL(packageUidAsUser, -1);
                    NetworkPolicyManagerService.this.updateRulesForPowerRestrictionsUL(
                            packageUidAsUser, -1);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }

        public final void onParoleStateChanged(boolean z) {
            synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                NetworkPolicyManagerService.this.mLogger.paroleStateChanged(z);
                NetworkPolicyManagerService.this.updateRulesForAppIdleParoleUL();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkPolicyManagerInternalImpl {
        public NetworkPolicyManagerInternalImpl() {}

        public static int updateBlockedReasonsWithProcState(int i) {
            return UidBlockedState.getEffectiveBlockedReasons(i, 262306);
        }

        public final long getSubscriptionOpportunisticQuota(Network network, int i) {
            long j;
            float f;
            float f2;
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                NetworkPolicyManagerService networkPolicyManagerService =
                        NetworkPolicyManagerService.this;
                j =
                        networkPolicyManagerService.mSubscriptionOpportunisticQuota.get(
                                networkPolicyManagerService.mNetIdToSubId.get(
                                        network.getNetId(), -1),
                                -1L);
            }
            if (j == -1) {
                return -1L;
            }
            if (i == 1) {
                f = j;
                f2 =
                        Settings.Global.getFloat(
                                NetworkPolicyManagerService.this.mContext.getContentResolver(),
                                "netpolicy_quota_frac_jobs",
                                0.5f);
            } else {
                if (i != 2) {
                    return -1L;
                }
                f = j;
                f2 =
                        Settings.Global.getFloat(
                                NetworkPolicyManagerService.this.mContext.getContentResolver(),
                                "netpolicy_quota_frac_multipath",
                                0.5f);
            }
            return (long) (f2 * f);
        }

        public final void onTempPowerSaveWhitelistChange(int i, int i2, String str, boolean z) {
            synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                try {
                    if (NetworkPolicyManagerService.this.mSystemReady) {
                        NetworkPolicyManagerService.this.mLogger.tempPowerSaveWlChanged(
                                i, i2, str, z);
                        if (z) {
                            NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.put(
                                    i, true);
                        } else {
                            NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.delete(
                                    i);
                        }
                        NetworkPolicyManagerService networkPolicyManagerService =
                                NetworkPolicyManagerService.this;
                        List users = networkPolicyManagerService.mUserManager.getUsers();
                        int size = users.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            int uid = UserHandle.getUid(((UserInfo) users.get(i3)).id, i);
                            networkPolicyManagerService.updateRuleForAppIdleUL(uid, -1);
                            if (networkPolicyManagerService.mDeviceIdleMode) {
                                if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                uid, true)
                                        && !networkPolicyManagerService
                                                .isUidForegroundOnRestrictPowerUL(uid)) {
                                    networkPolicyManagerService.setUidFirewallRuleUL(1, uid, 0);
                                }
                                networkPolicyManagerService.setUidFirewallRuleUL(1, uid, 1);
                            }
                            if (networkPolicyManagerService.mRestrictPower) {
                                if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                uid, false)
                                        && !networkPolicyManagerService
                                                .isUidForegroundOnRestrictPowerUL(uid)) {
                                    networkPolicyManagerService.setUidFirewallRuleUL(3, uid, 0);
                                }
                                networkPolicyManagerService.setUidFirewallRuleUL(3, uid, 1);
                            }
                            if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                networkPolicyManagerService.updateRuleForBackgroundUL(uid);
                            }
                            networkPolicyManagerService.updateRulesForPowerRestrictionsUL(uid, -1);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationId {
        public final int mId;
        public final String mTag;

        public NotificationId(int i, NetworkPolicy networkPolicy) {
            this.mTag = "NetworkPolicy:" + networkPolicy.template.hashCode() + ":" + i;
            this.mId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof NotificationId) {
                return Objects.equals(this.mTag, ((NotificationId) obj).mTag);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mTag);
        }

        public final String toString() {
            return this.mTag;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RestrictedModeObserver extends ContentObserver {
        public final Context mContext;
        public final NetworkPolicyManagerService$$ExternalSyntheticLambda8 mListener;

        public RestrictedModeObserver(
                Context context,
                NetworkPolicyManagerService$$ExternalSyntheticLambda8
                        networkPolicyManagerService$$ExternalSyntheticLambda8) {
            super(null);
            this.mContext = context;
            this.mListener = networkPolicyManagerService$$ExternalSyntheticLambda8;
            context.getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor("restricted_networking_mode"), false, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            NetworkPolicyManagerService$$ExternalSyntheticLambda8
                    networkPolicyManagerService$$ExternalSyntheticLambda8 = this.mListener;
            boolean z2 =
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(),
                                    "restricted_networking_mode",
                                    0)
                            != 0;
            NetworkPolicyManagerService networkPolicyManagerService =
                    networkPolicyManagerService$$ExternalSyntheticLambda8.f$0;
            synchronized (networkPolicyManagerService.mUidRulesFirstLock) {
                networkPolicyManagerService.mRestrictedNetworkingMode = z2;
                networkPolicyManagerService.updateRestrictedModeAllowlistUL();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsCallback extends NetworkStatsManager.UsageCallback {
        public boolean mIsAnyCallbackReceived = false;

        public StatsCallback() {}

        @Override // android.app.usage.NetworkStatsManager.UsageCallback
        public final void onThresholdReached(int i, String str) {
            this.mIsAnyCallbackReceived = true;
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                NetworkPolicyManagerService.this.updateNetworkRulesNL();
                NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                NetworkPolicyManagerService.this.updateNotificationsNL();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class UidBlockedState {
        public static final int[] BLOCKED_REASONS = {
            1,
            2,
            4,
            8,
            32,
            64,
            EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT,
            131072,
            262144,
            256,
            512
        };
        public static final int[] ALLOWED_REASONS = {
            1,
            2,
            32,
            4,
            8,
            16,
            64,
            128,
            EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT,
            131072,
            262144
        };
        public int blockedReasons = 0;
        public int allowedReasons = 0;
        public int effectiveBlockedReasons = 0;

        public static String allowedReasonToString(int i) {
            if (i == 0) {
                return "NONE";
            }
            if (i == 1) {
                return "SYSTEM";
            }
            if (i == 2) {
                return "FOREGROUND";
            }
            if (i == 4) {
                return "POWER_SAVE_ALLOWLIST";
            }
            if (i == 8) {
                return "POWER_SAVE_EXCEPT_IDLE_ALLOWLIST";
            }
            if (i == 16) {
                return "RESTRICTED_MODE_PERMISSIONS";
            }
            if (i == 32) {
                return "TOP";
            }
            if (i == 64) {
                return "LOW_POWER_STANDBY_ALLOWLIST";
            }
            if (i == 128) {
                return "NOT_IN_BACKGROUND";
            }
            if (i == 65536) {
                return "METERED_USER_EXEMPTED";
            }
            if (i == 131072) {
                return "METERED_SYSTEM";
            }
            if (i == 262144) {
                return "METERED_FOREGROUND";
            }
            Slog.wtfStack("NetworkPolicy", "Unknown allowedReason: " + i);
            return String.valueOf(i);
        }

        public static String blockedReasonToString(int i) {
            if (i == 0) {
                return "NONE";
            }
            if (i == 1) {
                return "BATTERY_SAVER";
            }
            if (i == 2) {
                return "DOZE";
            }
            if (i == 4) {
                return "APP_STANDBY";
            }
            if (i == 8) {
                return "RESTRICTED_MODE";
            }
            if (i == 32) {
                return "LOW_POWER_STANDBY";
            }
            if (i == 64) {
                return "APP_BACKGROUND";
            }
            if (i == 256) {
                return "FIREWALL_WIFI";
            }
            if (i == 512) {
                return "FIREWALL_DATA";
            }
            if (i == 65536) {
                return "DATA_SAVER";
            }
            if (i == 131072) {
                return "METERED_USER_RESTRICTED";
            }
            if (i == 262144) {
                return "METERED_ADMIN_DISABLED";
            }
            Slog.wtfStack("NetworkPolicy", "Unknown blockedReason: " + i);
            return String.valueOf(i);
        }

        public static String blockedReasonsToString(int i) {
            int i2 = 0;
            if (i == 0) {
                return blockedReasonToString(0);
            }
            StringBuilder sb = new StringBuilder();
            int[] iArr = BLOCKED_REASONS;
            while (true) {
                if (i2 >= 11) {
                    break;
                }
                int i3 = iArr[i2];
                if ((i & i3) != 0) {
                    sb.append(sb.length() == 0 ? "" : "|");
                    sb.append(blockedReasonToString(i3));
                    i &= ~i3;
                }
                i2++;
            }
            if (i != 0) {
                sb.append(sb.length() == 0 ? "" : "|");
                sb.append(String.valueOf(i));
                Slog.wtfStack("NetworkPolicy", "Unknown blockedReasons: " + i);
            }
            return sb.toString();
        }

        public static int getEffectiveBlockedReasons(int i, int i2) {
            if (i == 0) {
                return i;
            }
            if ((i2 & 1) != 0) {
                i &= -65536;
            }
            if ((131072 & i2) != 0) {
                i &= GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            if ((i2 & 2) != 0) {
                i &= -8;
            }
            if ((262144 & i2) != 0) {
                i &= -196609;
            }
            if ((i2 & 32) != 0) {
                i &= -33;
            }
            if ((i2 & 4) != 0) {
                i &= -72;
            }
            if ((i2 & 8) != 0) {
                i &= -70;
            }
            if ((i2 & 16) != 0) {
                i &= -9;
            }
            if ((65536 & i2) != 0) {
                i &= -65537;
            }
            if ((i2 & 64) != 0) {
                i &= -33;
            }
            return (i2 & 128) != 0 ? i & (-65) : i;
        }

        public static String toString(int i, int i2, int i3) {
            String sb;
            StringBuilder sb2 = new StringBuilder("{blocked=");
            sb2.append(blockedReasonsToString(i));
            sb2.append(",allowed=");
            int i4 = 0;
            if (i2 == 0) {
                sb = allowedReasonToString(0);
            } else {
                StringBuilder sb3 = new StringBuilder();
                int[] iArr = ALLOWED_REASONS;
                while (true) {
                    if (i4 >= 11) {
                        break;
                    }
                    int i5 = iArr[i4];
                    if ((i2 & i5) != 0) {
                        sb3.append(sb3.length() == 0 ? "" : "|");
                        sb3.append(allowedReasonToString(i5));
                        i2 &= ~i5;
                    }
                    i4++;
                }
                if (i2 != 0) {
                    sb3.append(sb3.length() == 0 ? "" : "|");
                    sb3.append(String.valueOf(i2));
                    Slog.wtfStack("NetworkPolicy", "Unknown allowedReasons: " + i2);
                }
                sb = sb3.toString();
            }
            sb2.append(sb);
            sb2.append(",effective=");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    sb2, blockedReasonsToString(i3), "}");
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x004c, code lost:

           if ((r0 & 262144) != 0) goto L25;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int deriveUidRules() {
            /*
                r4 = this;
                int r0 = r4.effectiveBlockedReasons
                r1 = r0 & 8
                if (r1 == 0) goto L9
                r1 = 1024(0x400, float:1.435E-42)
                goto La
            L9:
                r1 = 0
            La:
                r2 = r0 & 103(0x67, float:1.44E-43)
                if (r2 == 0) goto L11
                r1 = r1 | 64
                goto L19
            L11:
                int r2 = r4.blockedReasons
                r2 = r2 & 103(0x67, float:1.44E-43)
                if (r2 == 0) goto L19
                r1 = r1 | 32
            L19:
                r2 = r0 & 256(0x100, float:3.59E-43)
                if (r2 == 0) goto L1f
                r1 = r1 | 64
            L1f:
                r2 = r0 & 512(0x200, float:7.175E-43)
                if (r2 == 0) goto L25
                r1 = r1 | 64
            L25:
                r2 = 393216(0x60000, float:5.51013E-40)
                r0 = r0 & r2
                if (r0 == 0) goto L2d
                r1 = r1 | 4
                goto L4f
            L2d:
                int r0 = r4.blockedReasons
                r2 = 131072(0x20000, float:1.83671E-40)
                r2 = r2 & r0
                r3 = 262144(0x40000, float:3.67342E-40)
                if (r2 == 0) goto L3e
                int r2 = r4.allowedReasons
                r2 = r2 & r3
                if (r2 == 0) goto L3e
            L3b:
                r1 = r1 | 2
                goto L4f
            L3e:
                r2 = 65536(0x10000, float:9.18355E-41)
                r0 = r0 & r2
                if (r0 == 0) goto L4f
                int r0 = r4.allowedReasons
                r2 = r2 & r0
                if (r2 == 0) goto L4b
                r1 = r1 | 32
                goto L4f
            L4b:
                r0 = r0 & r3
                if (r0 == 0) goto L4f
                goto L3b
            L4f:
                boolean r0 = com.android.server.net.NetworkPolicyManagerService.LOGV
                if (r0 == 0) goto L73
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r2 = "uidBlockedState="
                r0.<init>(r2)
                r0.append(r4)
                java.lang.String r4 = " -> uidRule="
                r0.append(r4)
                java.lang.String r4 = android.net.NetworkPolicyManager.uidRulesToString(r1)
                r0.append(r4)
                java.lang.String r4 = r0.toString()
                java.lang.String r0 = "NetworkPolicy"
                android.util.Slog.v(r0, r4)
            L73:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.net.NetworkPolicyManagerService.UidBlockedState.deriveUidRules():int");
        }

        public final String toString() {
            return toString(this.blockedReasons, this.allowedReasons, this.effectiveBlockedReasons);
        }

        public final void updateEffectiveBlockedReasons() {
            boolean z = NetworkPolicyManagerService.LOGV;
            if (z && this.blockedReasons == 0) {
                Log.v("NetworkPolicy", "updateEffectiveBlockedReasons(): no blocked reasons");
            }
            this.effectiveBlockedReasons =
                    getEffectiveBlockedReasons(this.blockedReasons, this.allowedReasons);
            if (z) {
                Log.v(
                        "NetworkPolicy",
                        "updateEffectiveBlockedReasons(): blockedReasons="
                                + Integer.toBinaryString(this.blockedReasons)
                                + ", effectiveReasons="
                                + Integer.toBinaryString(this.effectiveBlockedReasons));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidStateCallbackInfo {
        public int capability;
        public boolean isPending;
        public int procState;
        public long procStateSeq;
        public int uid;

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("{uid=");
            m.append(this.uid);
            m.append(",proc_state=");
            m.append(ActivityManager.procStateToString(this.procState));
            m.append(",seq=");
            m.append(this.procStateSeq);
            m.append(",cap=");
            ActivityManager.printCapabilitiesSummary(m, this.capability);
            m.append(",");
            m.append("pending=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", m, this.isPending);
        }
    }

    /* renamed from: -$$Nest$monUidDeletedUL, reason: not valid java name */
    public static void m686$$Nest$monUidDeletedUL(
            NetworkPolicyManagerService networkPolicyManagerService, int i) {
        synchronized (networkPolicyManagerService.mUidBlockedState) {
            networkPolicyManagerService.mUidBlockedState.delete(i);
        }
        networkPolicyManagerService.mUidState.delete(i);
        networkPolicyManagerService.mActivityManagerInternal.onUidBlockedReasonsChanged(i, 0);
        networkPolicyManagerService.mUidPolicy.delete(i);
        networkPolicyManagerService.mUidFirewallStandbyRules.delete(i);
        networkPolicyManagerService.mUidFirewallDozableRules.delete(i);
        networkPolicyManagerService.mUidFirewallPowerSaveRules.delete(i);
        networkPolicyManagerService.mUidFirewallBackgroundRules.delete(i);
        networkPolicyManagerService.mBackgroundTransitioningUids.delete(i);
        networkPolicyManagerService.mUidFirewallOemDenyRules.delete(i);
        networkPolicyManagerService.mPowerSaveWhitelistExceptIdleAppIds.delete(i);
        networkPolicyManagerService.mPowerSaveWhitelistAppIds.delete(i);
        networkPolicyManagerService.mPowerSaveTempWhitelistAppIds.delete(i);
        networkPolicyManagerService.mAppIdleTempWhitelistAppIds.delete(i);
        networkPolicyManagerService.mUidFirewallRestrictedModeRules.delete(i);
        networkPolicyManagerService.mUidFirewallLowPowerStandbyModeRules.delete(i);
        synchronized (networkPolicyManagerService.mUidStateCallbackInfos) {
            networkPolicyManagerService.mUidStateCallbackInfos.remove(i);
        }
        networkPolicyManagerService.mHandler.obtainMessage(15, i, 0).sendToTarget();
    }

    /* renamed from: -$$Nest$msetMeteredRestrictedPackagesInternal, reason: not valid java name */
    public static void m687$$Nest$msetMeteredRestrictedPackagesInternal(
            NetworkPolicyManagerService networkPolicyManagerService, Set set, int i) {
        int i2;
        synchronized (networkPolicyManagerService.mUidRulesFirstLock) {
            try {
                ArraySet arraySet = new ArraySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    try {
                        i2 =
                                networkPolicyManagerService
                                        .mContext
                                        .getPackageManager()
                                        .getPackageUidAsUser((String) it.next(), 4202496, i);
                    } catch (PackageManager.NameNotFoundException unused) {
                        i2 = -1;
                    }
                    if (i2 >= 0) {
                        arraySet.add(Integer.valueOf(i2));
                    }
                }
                Set set2 = (Set) networkPolicyManagerService.mMeteredRestrictedUids.get(i);
                networkPolicyManagerService.mMeteredRestrictedUids.put(i, arraySet);
                networkPolicyManagerService.handleRestrictedPackagesChangeUL(set2, arraySet);
                networkPolicyManagerService.mLogger.meteredRestrictedPkgsChanged(arraySet);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$msetNetworkTemplateEnabledInner, reason: not valid java name */
    public static void m688$$Nest$msetNetworkTemplateEnabledInner(
            NetworkPolicyManagerService networkPolicyManagerService,
            NetworkTemplate networkTemplate,
            boolean z) {
        int i;
        networkPolicyManagerService.getClass();
        if (networkTemplate.getMatchRule() == 1 || networkTemplate.getMatchRule() == 10) {
            IntArray intArray = new IntArray();
            synchronized (networkPolicyManagerService.mNetworkPoliciesSecondLock) {
                for (int i2 = 0;
                        i2 < networkPolicyManagerService.mSubIdToSubscriberId.size();
                        i2++) {
                    try {
                        int keyAt = networkPolicyManagerService.mSubIdToSubscriberId.keyAt(i2);
                        if (networkTemplate.matches(
                                new NetworkIdentity.Builder()
                                        .setType(0)
                                        .setSubscriberId(
                                                (String)
                                                        networkPolicyManagerService
                                                                .mSubIdToSubscriberId.valueAt(i2))
                                        .setMetered(true)
                                        .setDefaultNetwork(true)
                                        .setSubId(keyAt)
                                        .build())) {
                            intArray.add(keyAt);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            TelephonyManager telephonyManager =
                    (TelephonyManager)
                            networkPolicyManagerService.mContext.getSystemService(
                                    TelephonyManager.class);
            for (i = 0; i < intArray.size(); i++) {
                telephonyManager.createForSubscriptionId(intArray.get(i)).setPolicyDataEnabled(z);
            }
        }
    }

    /* renamed from: -$$Nest$smupdateCapabilityChange, reason: not valid java name */
    public static boolean m689$$Nest$smupdateCapabilityChange(
            SparseBooleanArray sparseBooleanArray, boolean z, Network network) {
        boolean z2 =
                sparseBooleanArray.get(network.getNetId(), false) != z
                        || sparseBooleanArray.indexOfKey(network.getNetId()) < 0;
        if (z2) {
            sparseBooleanArray.put(network.getNetId(), z);
        }
        return z2;
    }

    /* JADX WARN: Type inference failed for: r1v49, types: [com.android.server.net.NetworkPolicyManagerService$4] */
    /* JADX WARN: Type inference failed for: r1v50, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v51, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v52, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v53, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v55, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v56, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v57, types: [com.android.server.net.NetworkPolicyManagerService$11] */
    /* JADX WARN: Type inference failed for: r1v58, types: [com.android.server.net.NetworkPolicyManagerService$12] */
    /* JADX WARN: Type inference failed for: r1v59, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r1v60, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.net.NetworkPolicyManagerService$18] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.server.net.NetworkPolicyManagerService$5] */
    public NetworkPolicyManagerService(
            Context context,
            IActivityManager iActivityManager,
            INetworkManagementService iNetworkManagementService,
            IPackageManager iPackageManager,
            Clock clock,
            File file,
            boolean z,
            Dependencies dependencies) {
        this.mToastCheckedUidMap = new ConcurrentHashMap();
        this.mForegroundActivitiesPidMap = new ConcurrentHashMap();
        this.mUidRulesFirstLock = new Object();
        this.mNetworkPoliciesSecondLock = new Object();
        this.mAdminDataAvailableLatch = new CountDownLatch(1);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.mBackgroundRestrictionDelayMs = timeUnit.toMillis(5L);
        this.mBackgroundRestrictionShortDelayMs = timeUnit.toMillis(2L);
        this.mBackgroundRestrictionLongDelayMs = timeUnit.toMillis(20L);
        this.mNextProcessBackgroundUidsTime = Long.MAX_VALUE;
        this.mNetworkPolicy = new ArrayMap();
        this.mSubscriptionPlans = new SparseArray();
        this.mSubscriptionPlansOwner = new SparseArray();
        this.mSetSubscriptionPlansIds = new SparseIntArray();
        this.mSetSubscriptionPlansIdCounter = 0;
        this.mSubscriptionOpportunisticQuota = new SparseLongArray();
        this.mUidPolicy = new SparseIntArray();
        this.mUidFirewallStandbyRules = new SparseIntArray();
        this.mUidFirewallDozableRules = new SparseIntArray();
        this.mUidFirewallPowerSaveRules = new SparseIntArray();
        this.mUidFirewallBackgroundRules = new SparseIntArray();
        this.mUidFirewallRestrictedModeRules = new SparseIntArray();
        this.mUidFirewallLowPowerStandbyModeRules = new SparseIntArray();
        this.mUidFirewallOemDenyRules = new SparseIntArray();
        this.mFirewallChainStates = new SparseBooleanArray();
        this.mPowerSaveWhitelistExceptIdleAppIds = new SparseBooleanArray();
        this.mPowerSaveWhitelistAppIds = new SparseBooleanArray();
        this.mPowerSaveTempWhitelistAppIds = new SparseBooleanArray();
        this.mLowPowerStandbyAllowlistUids = new SparseBooleanArray();
        this.mAppIdleTempWhitelistAppIds = new SparseBooleanArray();
        this.mDefaultRestrictBackgroundAllowlistUids = new SparseBooleanArray();
        this.mRestrictBackgroundAllowlistRevokedUids = new SparseBooleanArray();
        this.mMeteredIfacesLock = new Object();
        this.mMeteredIfaces = new ArraySet();
        this.mOverLimitNotified = new ArraySet();
        this.mActiveNotifs = new ArraySet();
        this.mUidState = new SparseArray();
        this.mUidBlockedState = new SparseArray();
        this.mTmpUidBlockedState = new SparseArray();
        this.mBackgroundTransitioningUids = new SparseLongArray();
        this.mNetworkMetered = new SparseBooleanArray();
        this.mNetworkRoaming = new SparseBooleanArray();
        this.mNetworkToIfaces = new SparseSetArray();
        this.mNetIdToSubId = new SparseIntArray();
        this.mSubIdToSubscriberId = new SparseArray();
        this.mMergedSubscriberIds = new ArrayList();
        this.mSubIdToCarrierConfig = new SparseArray();
        this.mMeteredRestrictedUids = new SparseArray();
        this.mListeners = new RemoteCallbackList();
        this.mLogger = new NetworkPolicyLogger();
        this.mInternetPermissionMap = new SparseBooleanArray();
        this.mUidStateCallbackInfos = new SparseArray();
        this.mActiveNetworkType = -1;
        this.mTelephonyManager = null;
        this.mIsVideoCall = false;
        this.mVideoCallLimitAlreadySent = false;
        this.mVideoCallWarningAlreadySent = false;
        this.mHasEpdgCall = false;
        this.mChargingState = false;
        this.mSupportSmartManagerForChina = false;
        this.isOffPeakObserverRegisted = new SparseArray();
        this.mDefaultDataPhoneId = 0;
        this.mSubscriberIdToSlotId = new ConcurrentHashMap();
        this.mTetheringWarningBytes = new ConcurrentHashMap();
        this.mTetheringState = false;
        this.mStatLogger =
                new StatLogger(
                        new String[] {"updateNetworkEnabledNL()", "isUidNetworkingBlocked()"});
        this.mUidObserver =
                new UidObserver() { // from class:
                    // com.android.server.net.NetworkPolicyManagerService.4
                    public final void onUidGone(int i, boolean z2) {
                        synchronized (NetworkPolicyManagerService.this.mUidStateCallbackInfos) {
                            NetworkPolicyManagerService.this.mUidStateCallbackInfos.remove(i);
                        }
                        NetworkPolicyManagerService.this
                                .mUidEventHandler
                                .obtainMessage(101, i, 0)
                                .sendToTarget();
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:43:0x0068, code lost:

                       if ((r1.capability & 40) != (r12 & 40)) goto L41;
                    */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onUidStateChanged(int r8, int r9, long r10, int r12) {
                        /*
                            r7 = this;
                            com.android.server.net.NetworkPolicyManagerService r0 = com.android.server.net.NetworkPolicyManagerService.this
                            android.util.SparseArray r0 = r0.mUidStateCallbackInfos
                            monitor-enter(r0)
                            com.android.server.net.NetworkPolicyManagerService r1 = com.android.server.net.NetworkPolicyManagerService.this     // Catch: java.lang.Throwable -> L26
                            android.util.SparseArray r1 = r1.mUidStateCallbackInfos     // Catch: java.lang.Throwable -> L26
                            java.lang.Object r1 = r1.get(r8)     // Catch: java.lang.Throwable -> L26
                            com.android.server.net.NetworkPolicyManagerService$UidStateCallbackInfo r1 = (com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo) r1     // Catch: java.lang.Throwable -> L26
                            r2 = -1
                            if (r1 != 0) goto L29
                            com.android.server.net.NetworkPolicyManagerService$UidStateCallbackInfo r1 = new com.android.server.net.NetworkPolicyManagerService$UidStateCallbackInfo     // Catch: java.lang.Throwable -> L26
                            r1.<init>()     // Catch: java.lang.Throwable -> L26
                            r4 = 20
                            r1.procState = r4     // Catch: java.lang.Throwable -> L26
                            r1.procStateSeq = r2     // Catch: java.lang.Throwable -> L26
                            com.android.server.net.NetworkPolicyManagerService r4 = com.android.server.net.NetworkPolicyManagerService.this     // Catch: java.lang.Throwable -> L26
                            android.util.SparseArray r4 = r4.mUidStateCallbackInfos     // Catch: java.lang.Throwable -> L26
                            r4.put(r8, r1)     // Catch: java.lang.Throwable -> L26
                            goto L29
                        L26:
                            r7 = move-exception
                            goto L87
                        L29:
                            long r4 = r1.procStateSeq     // Catch: java.lang.Throwable -> L26
                            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                            r3 = 0
                            r6 = 1
                            if (r2 != 0) goto L32
                            goto L6a
                        L32:
                            int r2 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
                            if (r2 > 0) goto L37
                            goto L85
                        L37:
                            int r2 = r1.procState     // Catch: java.lang.Throwable -> L26
                            r4 = 3
                            if (r2 <= r4) goto L6a
                            if (r9 > r4) goto L3f
                            goto L6a
                        L3f:
                            r4 = 5
                            if (r2 > r4) goto L44
                            r5 = r6
                            goto L45
                        L44:
                            r5 = r3
                        L45:
                            if (r9 > r4) goto L49
                            r4 = r6
                            goto L4a
                        L49:
                            r4 = r3
                        L4a:
                            if (r5 == r4) goto L4d
                            goto L6a
                        L4d:
                            com.android.server.net.NetworkPolicyManagerService r4 = com.android.server.net.NetworkPolicyManagerService.this     // Catch: java.lang.Throwable -> L26
                            boolean r4 = r4.mBackgroundNetworkRestricted     // Catch: java.lang.Throwable -> L26
                            if (r4 == 0) goto L62
                            r4 = 12
                            if (r2 < r4) goto L59
                            r2 = r6
                            goto L5a
                        L59:
                            r2 = r3
                        L5a:
                            if (r9 < r4) goto L5e
                            r4 = r6
                            goto L5f
                        L5e:
                            r4 = r3
                        L5f:
                            if (r2 == r4) goto L62
                            goto L6a
                        L62:
                            int r2 = r1.capability     // Catch: java.lang.Throwable -> L26
                            r2 = r2 & 40
                            r4 = r12 & 40
                            if (r2 == r4) goto L85
                        L6a:
                            r1.uid = r8     // Catch: java.lang.Throwable -> L26
                            r1.procState = r9     // Catch: java.lang.Throwable -> L26
                            r1.procStateSeq = r10     // Catch: java.lang.Throwable -> L26
                            r1.capability = r12     // Catch: java.lang.Throwable -> L26
                            boolean r9 = r1.isPending     // Catch: java.lang.Throwable -> L26
                            if (r9 != 0) goto L85
                            com.android.server.net.NetworkPolicyManagerService r7 = com.android.server.net.NetworkPolicyManagerService.this     // Catch: java.lang.Throwable -> L26
                            android.os.Handler r7 = r7.mUidEventHandler     // Catch: java.lang.Throwable -> L26
                            r9 = 100
                            android.os.Message r7 = r7.obtainMessage(r9, r8, r3)     // Catch: java.lang.Throwable -> L26
                            r7.sendToTarget()     // Catch: java.lang.Throwable -> L26
                            r1.isPending = r6     // Catch: java.lang.Throwable -> L26
                        L85:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
                            return
                        L87:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
                            throw r7
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.net.NetworkPolicyManagerService.AnonymousClass4.onUidStateChanged(int,"
                                    + " int, long, int):void");
                    }
                };
        final int i = 0;
        this.mPowerSaveAllowlistReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i2 = intExtra;
                                                            if (i2 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i2,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i2, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i2),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i2);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i2);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i2,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i2)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i2 = sparseIntArray.get(intExtra, 0);
                            if (i2 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i2);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i2;
                        boolean z2;
                        switch (i) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i3 = 0;
                                    while (i3
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i3);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i3++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i3);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i2 = 0; i2 < configuredNetworks.size(); i2++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i2);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i2 = 6;
        this.mPackageReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i2) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i3 = 0;
                                    while (i3
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i3);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i3++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i3);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i3 = 7;
        this.mUidRemovedReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i3) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i4 = 8;
        this.mUserReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i4) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        this.mStatsCallback = new StatsCallback();
        final int i5 = 9;
        this.mSnoozeReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i5) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i6 = 1;
        this.mWifiReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i6) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.server.net.NetworkPolicyManagerService.11
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            try {
                                boolean z2 = !networkCapabilities.hasCapability(11);
                                boolean m689$$Nest$smupdateCapabilityChange =
                                        NetworkPolicyManagerService
                                                .m689$$Nest$smupdateCapabilityChange(
                                                        NetworkPolicyManagerService.this
                                                                .mNetworkMetered,
                                                        z2,
                                                        network);
                                boolean z3 = !networkCapabilities.hasCapability(18);
                                boolean m689$$Nest$smupdateCapabilityChange2 =
                                        NetworkPolicyManagerService
                                                .m689$$Nest$smupdateCapabilityChange(
                                                        NetworkPolicyManagerService.this
                                                                .mNetworkRoaming,
                                                        z3,
                                                        network);
                                boolean z4 =
                                        m689$$Nest$smupdateCapabilityChange
                                                || m689$$Nest$smupdateCapabilityChange2;
                                if (m689$$Nest$smupdateCapabilityChange) {
                                    NetworkPolicyManagerService.this.mLogger.meterednessChanged(
                                            network.getNetId(), z2);
                                }
                                if (m689$$Nest$smupdateCapabilityChange2) {
                                    NetworkPolicyManagerService.this.mLogger.roamingChanged(
                                            network.getNetId(), z3);
                                }
                                if (z4) {
                                    NetworkPolicyManagerService.this.updateNetworkRulesNL();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            try {
                                ArraySet arraySet =
                                        new ArraySet(linkProperties.getAllInterfaceNames());
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        NetworkPolicyManagerService.this;
                                int netId = network.getNetId();
                                ArraySet arraySet2 =
                                        networkPolicyManagerService.mNetworkToIfaces.get(netId);
                                boolean z2 = true;
                                if (arraySet2 != null && arraySet2.equals(arraySet)) {
                                    z2 = false;
                                }
                                if (z2) {
                                    networkPolicyManagerService.mNetworkToIfaces.remove(netId);
                                    Iterator it = arraySet.iterator();
                                    while (it.hasNext()) {
                                        networkPolicyManagerService.mNetworkToIfaces.add(
                                                netId, (String) it.next());
                                    }
                                }
                                if (z2) {
                                    NetworkPolicyManagerService.this.mLogger.interfacesChanged(
                                            network.getNetId(), arraySet);
                                    NetworkPolicyManagerService.this.updateNetworkRulesNL();
                                }
                            } finally {
                            }
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            NetworkPolicyManagerService.this.mNetworkToIfaces.remove(
                                    network.getNetId());
                        }
                    }
                };
        this.mAlertObserver =
                new BaseNetworkObserver() { // from class:
                                            // com.android.server.net.NetworkPolicyManagerService.12
                    public final void limitReached(String str, String str2) {
                        NetworkStack.checkNetworkStackPermission(
                                NetworkPolicyManagerService.this.mContext);
                        if ("globalAlert".equals(str)) {
                            return;
                        }
                        NetworkPolicyManagerService.this
                                .mHandler
                                .obtainMessage(5, str2)
                                .sendToTarget();
                    }
                };
        final int i7 = 2;
        this.mConnReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i7) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i8 = 3;
        this.mCarrierConfigReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i8) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i9 = 0;
        Handler.Callback callback =
                new Handler.Callback(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.15
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    private final boolean
                            handleMessage$com$android$server$net$NetworkPolicyManagerService$15(
                                    Message message) {
                        String str;
                        boolean z2;
                        int i10 = message.what;
                        r2 = false;
                        boolean z3 = false;
                        if (i10 == 1) {
                            int i11 = message.arg1;
                            int i12 = message.arg2;
                            if (NetworkPolicyManagerService.LOGV) {
                                Slog.v(
                                        "NetworkPolicy",
                                        "Dispatching rules="
                                                + NetworkPolicyManager.uidRulesToString(i12)
                                                + " for uid="
                                                + i11);
                            }
                            int beginBroadcast = this.this$0.mListeners.beginBroadcast();
                            for (int i13 = 0; i13 < beginBroadcast; i13++) {
                                INetworkPolicyListener broadcastItem =
                                        this.this$0.mListeners.getBroadcastItem(i13);
                                this.this$0.getClass();
                                try {
                                    broadcastItem.onUidRulesChanged(i11, i12);
                                } catch (RemoteException unused) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            return true;
                        }
                        if (i10 == 2) {
                            String[] strArr = (String[]) message.obj;
                            int beginBroadcast2 = this.this$0.mListeners.beginBroadcast();
                            for (int i14 = 0; i14 < beginBroadcast2; i14++) {
                                INetworkPolicyListener broadcastItem2 =
                                        this.this$0.mListeners.getBroadcastItem(i14);
                                this.this$0.getClass();
                                try {
                                    broadcastItem2.onMeteredIfacesChanged(strArr);
                                } catch (RemoteException unused2) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            return true;
                        }
                        if (i10 == 5) {
                            String str2 = (String) message.obj;
                            synchronized (this.this$0.mMeteredIfacesLock) {
                                try {
                                    if (!this.this$0.mMeteredIfaces.contains(str2)) {
                                        return true;
                                    }
                                    this.this$0.mNetworkStats.forceUpdate();
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateNetworkRulesNL();
                                        this.this$0.updateNetworkEnabledNL();
                                        this.this$0.updateNotificationsNL();
                                    }
                                    return true;
                                } finally {
                                }
                            }
                        }
                        if (i10 == 6) {
                            boolean z4 = message.arg1 != 0;
                            int beginBroadcast3 = this.this$0.mListeners.beginBroadcast();
                            for (int i15 = 0; i15 < beginBroadcast3; i15++) {
                                INetworkPolicyListener broadcastItem3 =
                                        this.this$0.mListeners.getBroadcastItem(i15);
                                this.this$0.getClass();
                                try {
                                    broadcastItem3.onRestrictBackgroundChanged(z4);
                                } catch (RemoteException unused3) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            Intent intent =
                                    new Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                            intent.setFlags(1073741824);
                            this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                            return true;
                        }
                        if (i10 == 7) {
                            this.this$0.mNetworkStats.setDefaultGlobalAlert(
                                    ((Long) message.obj).longValue() / 1000);
                            return true;
                        }
                        if (i10 == 10) {
                            IfaceQuotas ifaceQuotas = (IfaceQuotas) message.obj;
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            String str3 = ifaceQuotas.iface;
                            networkPolicyManagerService.getClass();
                            try {
                                networkPolicyManagerService.mNetworkManager.removeInterfaceQuota(
                                        str3);
                            } catch (RemoteException unused4) {
                            } catch (IllegalStateException e) {
                                Log.wtf("NetworkPolicy", "problem removing interface quota", e);
                            }
                            NetworkPolicyManagerService networkPolicyManagerService2 = this.this$0;
                            String str4 = ifaceQuotas.iface;
                            long j = ifaceQuotas.limit;
                            networkPolicyManagerService2.getClass();
                            try {
                                networkPolicyManagerService2.mNetworkManager.setInterfaceQuota(
                                        str4, j);
                            } catch (RemoteException unused5) {
                            } catch (IllegalStateException e2) {
                                Log.wtf("NetworkPolicy", "problem setting interface quota", e2);
                            }
                            this.this$0.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                                    ifaceQuotas.iface, ifaceQuotas.warning, ifaceQuotas.limit);
                            return true;
                        }
                        if (i10 == 11) {
                            String str5 = (String) message.obj;
                            NetworkPolicyManagerService networkPolicyManagerService3 = this.this$0;
                            networkPolicyManagerService3.getClass();
                            try {
                                networkPolicyManagerService3.mNetworkManager.removeInterfaceQuota(
                                        str5);
                            } catch (RemoteException unused6) {
                            } catch (IllegalStateException e3) {
                                Log.wtf("NetworkPolicy", "problem removing interface quota", e3);
                            }
                            this.this$0.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                                    str5, -1L, -1L);
                            return true;
                        }
                        if (i10 == 13) {
                            int i16 = message.arg1;
                            int i17 = message.arg2;
                            Boolean bool = (Boolean) message.obj;
                            int beginBroadcast4 = this.this$0.mListeners.beginBroadcast();
                            for (int i18 = 0; i18 < beginBroadcast4; i18++) {
                                INetworkPolicyListener broadcastItem4 =
                                        this.this$0.mListeners.getBroadcastItem(i18);
                                this.this$0.getClass();
                                try {
                                    broadcastItem4.onUidPoliciesChanged(i16, i17);
                                } catch (RemoteException unused7) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            if (bool.booleanValue()) {
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                String[] packagesForUid =
                                        networkPolicyManagerService4
                                                .mContext
                                                .getPackageManager()
                                                .getPackagesForUid(i16);
                                if (packagesForUid != null) {
                                    int userId = UserHandle.getUserId(i16);
                                    for (String str6 : packagesForUid) {
                                        Intent intent2 =
                                                new Intent(
                                                        "android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                                        intent2.setPackage(str6);
                                        intent2.setFlags(1073741824);
                                        networkPolicyManagerService4.mContext.sendBroadcastAsUser(
                                                intent2, UserHandle.of(userId));
                                    }
                                }
                            }
                            return true;
                        }
                        if (i10 == 51) {
                            int i19 = message.arg1;
                            int i20 = message.arg2;
                            NetworkPolicyManagerService networkPolicyManagerService5 = this.this$0;
                            PackageManager packageManager =
                                    networkPolicyManagerService5.mContext.getPackageManager();
                            ConnectivityManager connectivityManager =
                                    (ConnectivityManager)
                                            networkPolicyManagerService5.mContext.getSystemService(
                                                    "connectivity");
                            ActivityManager activityManager =
                                    (ActivityManager)
                                            networkPolicyManagerService5.mContext.getSystemService(
                                                    "activity");
                            if (packageManager != null
                                    && connectivityManager != null
                                    && activityManager != null) {
                                try {
                                    ApplicationInfo applicationInfoAsUser =
                                            packageManager.getApplicationInfoAsUser(
                                                    activityManager.getPackageFromAppProcesses(i19),
                                                    1,
                                                    UserHandle.getUserId(i20));
                                    NetworkInfo activeNetworkInfo =
                                            connectivityManager.getActiveNetworkInfo();
                                    if (activeNetworkInfo != null
                                            && applicationInfoAsUser != null) {
                                        String charSequence =
                                                applicationInfoAsUser
                                                        .loadLabel(packageManager)
                                                        .toString();
                                        Resources resources =
                                                networkPolicyManagerService5.mContext
                                                        .getResources();
                                        if (activeNetworkInfo.getType() == 0
                                                && !networkPolicyManagerService5
                                                        .getFirewallRuleMobileData(
                                                                applicationInfoAsUser.uid)) {
                                            str = resources.getString(17042588, charSequence);
                                        } else if (activeNetworkInfo.getType() != 1) {
                                            str = "";
                                        } else if (!networkPolicyManagerService5
                                                .getFirewallRuleWifi(applicationInfoAsUser.uid)) {
                                            if (networkPolicyManagerService5
                                                    .getFirewallRuleMobileData(
                                                            applicationInfoAsUser.uid)) {
                                                for (NetworkInfo networkInfo :
                                                        connectivityManager.getAllNetworkInfo()) {
                                                    if (networkInfo.getType() == 0
                                                            && TelephonyManager.from(
                                                                            networkPolicyManagerService5
                                                                                    .mContext)
                                                                    .getDataEnabled()) {
                                                        break;
                                                    }
                                                }
                                            }
                                            str =
                                                    resources.getString(
                                                            17042589, charSequence, charSequence);
                                        }
                                        if (!str.isEmpty()) {
                                            Toast.makeText(
                                                            networkPolicyManagerService5.mContext,
                                                            str,
                                                            1)
                                                    .show();
                                        }
                                    }
                                } catch (Exception e4) {
                                    Log.e("NetworkPolicy", "handleCheckFireWallPermission", e4);
                                }
                            }
                            return true;
                        }
                        if (i10 == 1005) {
                            NetworkPolicyManagerService networkPolicyManagerService6 = this.this$0;
                            boolean z5 = networkPolicyManagerService6.mRestrictPower;
                            boolean z6 =
                                    networkPolicyManagerService6
                                            .mContext
                                            .getResources()
                                            .getBoolean(
                                                    R.bool
                                                            .config_enableDefaultHdrConversionPassthrough);
                            if (!SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)
                                            .equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)
                                    || z6) {
                                z2 =
                                        networkPolicyManagerService6.mPowerManagerInternal
                                                .getLowPowerState(6)
                                                .batterySaverEnabled;
                                Slog.v(
                                        "NetworkPolicy",
                                        "getRestrictBackgroundInLowerPowerMode: enabled("
                                                + z2
                                                + ")");
                            } else {
                                Log.d(
                                        "NetworkPolicy",
                                        "CHN don't use RestrictBackgroundData during PowerSave"
                                            + " mode");
                                z2 = false;
                            }
                            if (z2 && !networkPolicyManagerService6.mChargingState) {
                                z3 = true;
                            }
                            networkPolicyManagerService6.mRestrictPower = z3;
                            if (z5 != networkPolicyManagerService6.mRestrictPower) {
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                            }
                            return true;
                        }
                        if (i10 == 1007) {
                            byte b = message.arg1 != 0;
                            this.this$0.mTetheringWarningBytes.put(
                                    0,
                                    Long.valueOf(
                                            this.this$0.getTetheringWarningBytes(
                                                    "tethering_data_warning_sim_slot_0")));
                            this.this$0.mTetheringWarningBytes.put(
                                    1,
                                    Long.valueOf(
                                            this.this$0.getTetheringWarningBytes(
                                                    "tethering_data_warning_sim_slot_1")));
                            Slog.v(
                                    "NetworkPolicy",
                                    "MSG_CHECK_TETHERING_WARNING_CHANGED() -"
                                        + " mTetheringWarningBytes[0]: "
                                            + this.this$0.mTetheringWarningBytes.getOrDefault(
                                                    0, -1L)
                                            + ", mTetheringWarningBytes[1]: "
                                            + this.this$0.mTetheringWarningBytes.getOrDefault(
                                                    1, -1L));
                            if (b != false) {
                                this.this$0.mTetheringNotiSnooze = -1L;
                            }
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.updateNotificationsNL();
                            }
                            return true;
                        }
                        switch (i10) {
                            case 15:
                                this.this$0.resetUidFirewallRules(message.arg1);
                                return true;
                            case 16:
                                SomeArgs someArgs = (SomeArgs) message.obj;
                                int intValue = ((Integer) someArgs.arg1).intValue();
                                int intValue2 = ((Integer) someArgs.arg2).intValue();
                                int intValue3 = ((Integer) someArgs.arg3).intValue();
                                int[] iArr = (int[]) someArgs.arg4;
                                int beginBroadcast5 = this.this$0.mListeners.beginBroadcast();
                                for (int i21 = 0; i21 < beginBroadcast5; i21++) {
                                    INetworkPolicyListener broadcastItem5 =
                                            this.this$0.mListeners.getBroadcastItem(i21);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem5.onSubscriptionOverride(
                                                intValue, intValue2, intValue3, iArr);
                                    } catch (RemoteException unused8) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 17:
                                NetworkPolicyManagerService
                                        .m687$$Nest$msetMeteredRestrictedPackagesInternal(
                                                this.this$0, (Set) message.obj, message.arg1);
                                return true;
                            case 18:
                                NetworkPolicyManagerService
                                        .m688$$Nest$msetNetworkTemplateEnabledInner(
                                                this.this$0,
                                                (NetworkTemplate) message.obj,
                                                message.arg1 != 0);
                                return true;
                            case 19:
                                SubscriptionPlan[] subscriptionPlanArr =
                                        (SubscriptionPlan[]) message.obj;
                                int i22 = message.arg1;
                                int beginBroadcast6 = this.this$0.mListeners.beginBroadcast();
                                for (int i23 = 0; i23 < beginBroadcast6; i23++) {
                                    INetworkPolicyListener broadcastItem6 =
                                            this.this$0.mListeners.getBroadcastItem(i23);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem6.onSubscriptionPlansChanged(
                                                i22, subscriptionPlanArr);
                                    } catch (RemoteException unused9) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 20:
                                this.this$0.mNetworkStats.forceUpdate();
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    this.this$0.updateNetworkRulesNL();
                                    this.this$0.updateNetworkEnabledNL();
                                    this.this$0.updateNotificationsNL();
                                }
                                return true;
                            case 21:
                                int i24 = message.arg1;
                                int i25 = message.arg2;
                                int intValue4 = ((Integer) message.obj).intValue();
                                int beginBroadcast7 = this.this$0.mListeners.beginBroadcast();
                                for (int i26 = 0; i26 < beginBroadcast7; i26++) {
                                    INetworkPolicyListener broadcastItem7 =
                                            this.this$0.mListeners.getBroadcastItem(i26);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem7.onBlockedReasonChanged(i24, intValue4, i25);
                                    } catch (RemoteException unused10) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 22:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        try {
                                            int i27 = message.arg1;
                                            if (message.arg2
                                                    == this.this$0.mSetSubscriptionPlansIds.get(
                                                            i27)) {
                                                if (NetworkPolicyManagerService.LOGD) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Clearing expired subscription plans.");
                                                }
                                                this.this$0.setSubscriptionPlansInternal(
                                                        i27,
                                                        new SubscriptionPlan[0],
                                                        0L,
                                                        (String) message.obj);
                                            } else if (NetworkPolicyManagerService.LOGD) {
                                                Slog.d(
                                                        "NetworkPolicy",
                                                        "Ignoring stale CLEAR_SUBSCRIPTION_PLANS.");
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                return true;
                            case 23:
                                SparseArray sparseArray = (SparseArray) message.obj;
                                int size = sparseArray.size();
                                int beginBroadcast8 = this.this$0.mListeners.beginBroadcast();
                                for (int i28 = 0; i28 < beginBroadcast8; i28++) {
                                    INetworkPolicyListener broadcastItem8 =
                                            this.this$0.mListeners.getBroadcastItem(i28);
                                    for (int i29 = 0; i29 < size; i29++) {
                                        int keyAt = sparseArray.keyAt(i29);
                                        SomeArgs someArgs2 = (SomeArgs) sparseArray.valueAt(i29);
                                        int i30 = someArgs2.argi1;
                                        int i31 = someArgs2.argi2;
                                        int i32 = someArgs2.argi3;
                                        this.this$0.getClass();
                                        try {
                                            broadcastItem8.onBlockedReasonChanged(keyAt, i30, i31);
                                        } catch (RemoteException unused11) {
                                        }
                                        if (NetworkPolicyManagerService.LOGV) {
                                            Slog.v(
                                                    "NetworkPolicy",
                                                    "Dispatching rules="
                                                            + NetworkPolicyManager.uidRulesToString(
                                                                    i32)
                                                            + " for uid="
                                                            + keyAt);
                                        }
                                        this.this$0.getClass();
                                        try {
                                            broadcastItem8.onUidRulesChanged(keyAt, i32);
                                        } catch (RemoteException unused12) {
                                        }
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                for (int i33 = 0; i33 < size; i33++) {
                                    ((SomeArgs) sparseArray.valueAt(i33)).recycle();
                                }
                                return true;
                            case 24:
                                long uptimeMillis = SystemClock.uptimeMillis();
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    try {
                                        long j2 = Long.MAX_VALUE;
                                        for (int size2 =
                                                        this.this$0.mBackgroundTransitioningUids
                                                                        .size()
                                                                - 1;
                                                size2 >= 0;
                                                size2--) {
                                            long valueAt =
                                                    this.this$0.mBackgroundTransitioningUids
                                                            .valueAt(size2);
                                            if (valueAt > uptimeMillis) {
                                                j2 = Math.min(j2, valueAt);
                                            } else {
                                                int keyAt2 =
                                                        this.this$0.mBackgroundTransitioningUids
                                                                .keyAt(size2);
                                                this.this$0.mBackgroundTransitioningUids.removeAt(
                                                        size2);
                                                this.this$0.updateRuleForBackgroundUL(keyAt2);
                                                this.this$0.updateRulesForPowerRestrictionsUL(
                                                        keyAt2, false);
                                            }
                                        }
                                        NetworkPolicyManagerService networkPolicyManagerService7 =
                                                this.this$0;
                                        networkPolicyManagerService7
                                                        .mNextProcessBackgroundUidsTime =
                                                j2;
                                        if (j2 < Long.MAX_VALUE) {
                                            networkPolicyManagerService7.mHandler
                                                    .sendEmptyMessageAtTime(24, j2);
                                        }
                                    } finally {
                                    }
                                }
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        boolean updateUidStateUL;
                        boolean removeUidStateUL;
                        switch (i9) {
                            case 0:
                                return handleMessage$com$android$server$net$NetworkPolicyManagerService$15(
                                        message);
                            default:
                                int i10 = message.arg1;
                                int i11 = message.what;
                                if (i11 != 100) {
                                    if (i11 != 101) {
                                        return false;
                                    }
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    networkPolicyManagerService.getClass();
                                    Trace.traceBegin(2097152L, "onUidGone");
                                    try {
                                        synchronized (
                                                networkPolicyManagerService
                                                        .mUidStateCallbackInfos) {
                                            try {
                                                if (!networkPolicyManagerService
                                                        .mUidStateCallbackInfos.contains(i10)) {
                                                    synchronized (
                                                            networkPolicyManagerService
                                                                    .mUidRulesFirstLock) {
                                                        removeUidStateUL =
                                                                networkPolicyManagerService
                                                                        .removeUidStateUL(i10);
                                                    }
                                                    if (removeUidStateUL) {
                                                        networkPolicyManagerService
                                                                .updateNetworkStats(i10, false);
                                                    }
                                                }
                                            } finally {
                                            }
                                        }
                                        return true;
                                    } finally {
                                    }
                                }
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                Trace.traceBegin(2097152L, "onUidStateChanged");
                                try {
                                    synchronized (
                                            networkPolicyManagerService2.mUidStateCallbackInfos) {
                                        try {
                                            UidStateCallbackInfo uidStateCallbackInfo =
                                                    (UidStateCallbackInfo)
                                                            networkPolicyManagerService2
                                                                    .mUidStateCallbackInfos.get(
                                                                    i10);
                                            if (uidStateCallbackInfo != null) {
                                                int i12 = uidStateCallbackInfo.procState;
                                                long j = uidStateCallbackInfo.procStateSeq;
                                                int i13 = uidStateCallbackInfo.capability;
                                                uidStateCallbackInfo.isPending = false;
                                                synchronized (
                                                        networkPolicyManagerService2
                                                                .mUidRulesFirstLock) {
                                                    networkPolicyManagerService2.mLogger
                                                            .uidStateChanged(i10, i12, i13, j);
                                                    updateUidStateUL =
                                                            networkPolicyManagerService2
                                                                    .updateUidStateUL(
                                                                            i10, i12, i13, j);
                                                    networkPolicyManagerService2
                                                            .mActivityManagerInternal
                                                            .notifyNetworkPolicyRulesUpdated(
                                                                    i10, j);
                                                }
                                                if (updateUidStateUL) {
                                                    networkPolicyManagerService2.updateNetworkStats(
                                                            i10,
                                                            NetworkPolicyManager
                                                                    .isProcStateAllowedWhileOnRestrictBackground(
                                                                            i12, i13));
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    return true;
                                } finally {
                                }
                        }
                    }
                };
        final int i10 = 1;
        Handler.Callback callback2 =
                new Handler.Callback(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.15
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    private final boolean
                            handleMessage$com$android$server$net$NetworkPolicyManagerService$15(
                                    Message message) {
                        String str;
                        boolean z2;
                        int i102 = message.what;
                        z3 = false;
                        boolean z3 = false;
                        if (i102 == 1) {
                            int i11 = message.arg1;
                            int i12 = message.arg2;
                            if (NetworkPolicyManagerService.LOGV) {
                                Slog.v(
                                        "NetworkPolicy",
                                        "Dispatching rules="
                                                + NetworkPolicyManager.uidRulesToString(i12)
                                                + " for uid="
                                                + i11);
                            }
                            int beginBroadcast = this.this$0.mListeners.beginBroadcast();
                            for (int i13 = 0; i13 < beginBroadcast; i13++) {
                                INetworkPolicyListener broadcastItem =
                                        this.this$0.mListeners.getBroadcastItem(i13);
                                this.this$0.getClass();
                                try {
                                    broadcastItem.onUidRulesChanged(i11, i12);
                                } catch (RemoteException unused) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            return true;
                        }
                        if (i102 == 2) {
                            String[] strArr = (String[]) message.obj;
                            int beginBroadcast2 = this.this$0.mListeners.beginBroadcast();
                            for (int i14 = 0; i14 < beginBroadcast2; i14++) {
                                INetworkPolicyListener broadcastItem2 =
                                        this.this$0.mListeners.getBroadcastItem(i14);
                                this.this$0.getClass();
                                try {
                                    broadcastItem2.onMeteredIfacesChanged(strArr);
                                } catch (RemoteException unused2) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            return true;
                        }
                        if (i102 == 5) {
                            String str2 = (String) message.obj;
                            synchronized (this.this$0.mMeteredIfacesLock) {
                                try {
                                    if (!this.this$0.mMeteredIfaces.contains(str2)) {
                                        return true;
                                    }
                                    this.this$0.mNetworkStats.forceUpdate();
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateNetworkRulesNL();
                                        this.this$0.updateNetworkEnabledNL();
                                        this.this$0.updateNotificationsNL();
                                    }
                                    return true;
                                } finally {
                                }
                            }
                        }
                        if (i102 == 6) {
                            boolean z4 = message.arg1 != 0;
                            int beginBroadcast3 = this.this$0.mListeners.beginBroadcast();
                            for (int i15 = 0; i15 < beginBroadcast3; i15++) {
                                INetworkPolicyListener broadcastItem3 =
                                        this.this$0.mListeners.getBroadcastItem(i15);
                                this.this$0.getClass();
                                try {
                                    broadcastItem3.onRestrictBackgroundChanged(z4);
                                } catch (RemoteException unused3) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            Intent intent =
                                    new Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                            intent.setFlags(1073741824);
                            this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                            return true;
                        }
                        if (i102 == 7) {
                            this.this$0.mNetworkStats.setDefaultGlobalAlert(
                                    ((Long) message.obj).longValue() / 1000);
                            return true;
                        }
                        if (i102 == 10) {
                            IfaceQuotas ifaceQuotas = (IfaceQuotas) message.obj;
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            String str3 = ifaceQuotas.iface;
                            networkPolicyManagerService.getClass();
                            try {
                                networkPolicyManagerService.mNetworkManager.removeInterfaceQuota(
                                        str3);
                            } catch (RemoteException unused4) {
                            } catch (IllegalStateException e) {
                                Log.wtf("NetworkPolicy", "problem removing interface quota", e);
                            }
                            NetworkPolicyManagerService networkPolicyManagerService2 = this.this$0;
                            String str4 = ifaceQuotas.iface;
                            long j = ifaceQuotas.limit;
                            networkPolicyManagerService2.getClass();
                            try {
                                networkPolicyManagerService2.mNetworkManager.setInterfaceQuota(
                                        str4, j);
                            } catch (RemoteException unused5) {
                            } catch (IllegalStateException e2) {
                                Log.wtf("NetworkPolicy", "problem setting interface quota", e2);
                            }
                            this.this$0.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                                    ifaceQuotas.iface, ifaceQuotas.warning, ifaceQuotas.limit);
                            return true;
                        }
                        if (i102 == 11) {
                            String str5 = (String) message.obj;
                            NetworkPolicyManagerService networkPolicyManagerService3 = this.this$0;
                            networkPolicyManagerService3.getClass();
                            try {
                                networkPolicyManagerService3.mNetworkManager.removeInterfaceQuota(
                                        str5);
                            } catch (RemoteException unused6) {
                            } catch (IllegalStateException e3) {
                                Log.wtf("NetworkPolicy", "problem removing interface quota", e3);
                            }
                            this.this$0.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                                    str5, -1L, -1L);
                            return true;
                        }
                        if (i102 == 13) {
                            int i16 = message.arg1;
                            int i17 = message.arg2;
                            Boolean bool = (Boolean) message.obj;
                            int beginBroadcast4 = this.this$0.mListeners.beginBroadcast();
                            for (int i18 = 0; i18 < beginBroadcast4; i18++) {
                                INetworkPolicyListener broadcastItem4 =
                                        this.this$0.mListeners.getBroadcastItem(i18);
                                this.this$0.getClass();
                                try {
                                    broadcastItem4.onUidPoliciesChanged(i16, i17);
                                } catch (RemoteException unused7) {
                                }
                            }
                            this.this$0.mListeners.finishBroadcast();
                            if (bool.booleanValue()) {
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                String[] packagesForUid =
                                        networkPolicyManagerService4
                                                .mContext
                                                .getPackageManager()
                                                .getPackagesForUid(i16);
                                if (packagesForUid != null) {
                                    int userId = UserHandle.getUserId(i16);
                                    for (String str6 : packagesForUid) {
                                        Intent intent2 =
                                                new Intent(
                                                        "android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                                        intent2.setPackage(str6);
                                        intent2.setFlags(1073741824);
                                        networkPolicyManagerService4.mContext.sendBroadcastAsUser(
                                                intent2, UserHandle.of(userId));
                                    }
                                }
                            }
                            return true;
                        }
                        if (i102 == 51) {
                            int i19 = message.arg1;
                            int i20 = message.arg2;
                            NetworkPolicyManagerService networkPolicyManagerService5 = this.this$0;
                            PackageManager packageManager =
                                    networkPolicyManagerService5.mContext.getPackageManager();
                            ConnectivityManager connectivityManager =
                                    (ConnectivityManager)
                                            networkPolicyManagerService5.mContext.getSystemService(
                                                    "connectivity");
                            ActivityManager activityManager =
                                    (ActivityManager)
                                            networkPolicyManagerService5.mContext.getSystemService(
                                                    "activity");
                            if (packageManager != null
                                    && connectivityManager != null
                                    && activityManager != null) {
                                try {
                                    ApplicationInfo applicationInfoAsUser =
                                            packageManager.getApplicationInfoAsUser(
                                                    activityManager.getPackageFromAppProcesses(i19),
                                                    1,
                                                    UserHandle.getUserId(i20));
                                    NetworkInfo activeNetworkInfo =
                                            connectivityManager.getActiveNetworkInfo();
                                    if (activeNetworkInfo != null
                                            && applicationInfoAsUser != null) {
                                        String charSequence =
                                                applicationInfoAsUser
                                                        .loadLabel(packageManager)
                                                        .toString();
                                        Resources resources =
                                                networkPolicyManagerService5.mContext
                                                        .getResources();
                                        if (activeNetworkInfo.getType() == 0
                                                && !networkPolicyManagerService5
                                                        .getFirewallRuleMobileData(
                                                                applicationInfoAsUser.uid)) {
                                            str = resources.getString(17042588, charSequence);
                                        } else if (activeNetworkInfo.getType() != 1) {
                                            str = "";
                                        } else if (!networkPolicyManagerService5
                                                .getFirewallRuleWifi(applicationInfoAsUser.uid)) {
                                            if (networkPolicyManagerService5
                                                    .getFirewallRuleMobileData(
                                                            applicationInfoAsUser.uid)) {
                                                for (NetworkInfo networkInfo :
                                                        connectivityManager.getAllNetworkInfo()) {
                                                    if (networkInfo.getType() == 0
                                                            && TelephonyManager.from(
                                                                            networkPolicyManagerService5
                                                                                    .mContext)
                                                                    .getDataEnabled()) {
                                                        break;
                                                    }
                                                }
                                            }
                                            str =
                                                    resources.getString(
                                                            17042589, charSequence, charSequence);
                                        }
                                        if (!str.isEmpty()) {
                                            Toast.makeText(
                                                            networkPolicyManagerService5.mContext,
                                                            str,
                                                            1)
                                                    .show();
                                        }
                                    }
                                } catch (Exception e4) {
                                    Log.e("NetworkPolicy", "handleCheckFireWallPermission", e4);
                                }
                            }
                            return true;
                        }
                        if (i102 == 1005) {
                            NetworkPolicyManagerService networkPolicyManagerService6 = this.this$0;
                            boolean z5 = networkPolicyManagerService6.mRestrictPower;
                            boolean z6 =
                                    networkPolicyManagerService6
                                            .mContext
                                            .getResources()
                                            .getBoolean(
                                                    R.bool
                                                            .config_enableDefaultHdrConversionPassthrough);
                            if (!SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)
                                            .equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)
                                    || z6) {
                                z2 =
                                        networkPolicyManagerService6.mPowerManagerInternal
                                                .getLowPowerState(6)
                                                .batterySaverEnabled;
                                Slog.v(
                                        "NetworkPolicy",
                                        "getRestrictBackgroundInLowerPowerMode: enabled("
                                                + z2
                                                + ")");
                            } else {
                                Log.d(
                                        "NetworkPolicy",
                                        "CHN don't use RestrictBackgroundData during PowerSave"
                                            + " mode");
                                z2 = false;
                            }
                            if (z2 && !networkPolicyManagerService6.mChargingState) {
                                z3 = true;
                            }
                            networkPolicyManagerService6.mRestrictPower = z3;
                            if (z5 != networkPolicyManagerService6.mRestrictPower) {
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                            }
                            return true;
                        }
                        if (i102 == 1007) {
                            byte b = message.arg1 != 0;
                            this.this$0.mTetheringWarningBytes.put(
                                    0,
                                    Long.valueOf(
                                            this.this$0.getTetheringWarningBytes(
                                                    "tethering_data_warning_sim_slot_0")));
                            this.this$0.mTetheringWarningBytes.put(
                                    1,
                                    Long.valueOf(
                                            this.this$0.getTetheringWarningBytes(
                                                    "tethering_data_warning_sim_slot_1")));
                            Slog.v(
                                    "NetworkPolicy",
                                    "MSG_CHECK_TETHERING_WARNING_CHANGED() -"
                                        + " mTetheringWarningBytes[0]: "
                                            + this.this$0.mTetheringWarningBytes.getOrDefault(
                                                    0, -1L)
                                            + ", mTetheringWarningBytes[1]: "
                                            + this.this$0.mTetheringWarningBytes.getOrDefault(
                                                    1, -1L));
                            if (b != false) {
                                this.this$0.mTetheringNotiSnooze = -1L;
                            }
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.updateNotificationsNL();
                            }
                            return true;
                        }
                        switch (i102) {
                            case 15:
                                this.this$0.resetUidFirewallRules(message.arg1);
                                return true;
                            case 16:
                                SomeArgs someArgs = (SomeArgs) message.obj;
                                int intValue = ((Integer) someArgs.arg1).intValue();
                                int intValue2 = ((Integer) someArgs.arg2).intValue();
                                int intValue3 = ((Integer) someArgs.arg3).intValue();
                                int[] iArr = (int[]) someArgs.arg4;
                                int beginBroadcast5 = this.this$0.mListeners.beginBroadcast();
                                for (int i21 = 0; i21 < beginBroadcast5; i21++) {
                                    INetworkPolicyListener broadcastItem5 =
                                            this.this$0.mListeners.getBroadcastItem(i21);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem5.onSubscriptionOverride(
                                                intValue, intValue2, intValue3, iArr);
                                    } catch (RemoteException unused8) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 17:
                                NetworkPolicyManagerService
                                        .m687$$Nest$msetMeteredRestrictedPackagesInternal(
                                                this.this$0, (Set) message.obj, message.arg1);
                                return true;
                            case 18:
                                NetworkPolicyManagerService
                                        .m688$$Nest$msetNetworkTemplateEnabledInner(
                                                this.this$0,
                                                (NetworkTemplate) message.obj,
                                                message.arg1 != 0);
                                return true;
                            case 19:
                                SubscriptionPlan[] subscriptionPlanArr =
                                        (SubscriptionPlan[]) message.obj;
                                int i22 = message.arg1;
                                int beginBroadcast6 = this.this$0.mListeners.beginBroadcast();
                                for (int i23 = 0; i23 < beginBroadcast6; i23++) {
                                    INetworkPolicyListener broadcastItem6 =
                                            this.this$0.mListeners.getBroadcastItem(i23);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem6.onSubscriptionPlansChanged(
                                                i22, subscriptionPlanArr);
                                    } catch (RemoteException unused9) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 20:
                                this.this$0.mNetworkStats.forceUpdate();
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    this.this$0.updateNetworkRulesNL();
                                    this.this$0.updateNetworkEnabledNL();
                                    this.this$0.updateNotificationsNL();
                                }
                                return true;
                            case 21:
                                int i24 = message.arg1;
                                int i25 = message.arg2;
                                int intValue4 = ((Integer) message.obj).intValue();
                                int beginBroadcast7 = this.this$0.mListeners.beginBroadcast();
                                for (int i26 = 0; i26 < beginBroadcast7; i26++) {
                                    INetworkPolicyListener broadcastItem7 =
                                            this.this$0.mListeners.getBroadcastItem(i26);
                                    this.this$0.getClass();
                                    try {
                                        broadcastItem7.onBlockedReasonChanged(i24, intValue4, i25);
                                    } catch (RemoteException unused10) {
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                return true;
                            case 22:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        try {
                                            int i27 = message.arg1;
                                            if (message.arg2
                                                    == this.this$0.mSetSubscriptionPlansIds.get(
                                                            i27)) {
                                                if (NetworkPolicyManagerService.LOGD) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Clearing expired subscription plans.");
                                                }
                                                this.this$0.setSubscriptionPlansInternal(
                                                        i27,
                                                        new SubscriptionPlan[0],
                                                        0L,
                                                        (String) message.obj);
                                            } else if (NetworkPolicyManagerService.LOGD) {
                                                Slog.d(
                                                        "NetworkPolicy",
                                                        "Ignoring stale CLEAR_SUBSCRIPTION_PLANS.");
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                return true;
                            case 23:
                                SparseArray sparseArray = (SparseArray) message.obj;
                                int size = sparseArray.size();
                                int beginBroadcast8 = this.this$0.mListeners.beginBroadcast();
                                for (int i28 = 0; i28 < beginBroadcast8; i28++) {
                                    INetworkPolicyListener broadcastItem8 =
                                            this.this$0.mListeners.getBroadcastItem(i28);
                                    for (int i29 = 0; i29 < size; i29++) {
                                        int keyAt = sparseArray.keyAt(i29);
                                        SomeArgs someArgs2 = (SomeArgs) sparseArray.valueAt(i29);
                                        int i30 = someArgs2.argi1;
                                        int i31 = someArgs2.argi2;
                                        int i32 = someArgs2.argi3;
                                        this.this$0.getClass();
                                        try {
                                            broadcastItem8.onBlockedReasonChanged(keyAt, i30, i31);
                                        } catch (RemoteException unused11) {
                                        }
                                        if (NetworkPolicyManagerService.LOGV) {
                                            Slog.v(
                                                    "NetworkPolicy",
                                                    "Dispatching rules="
                                                            + NetworkPolicyManager.uidRulesToString(
                                                                    i32)
                                                            + " for uid="
                                                            + keyAt);
                                        }
                                        this.this$0.getClass();
                                        try {
                                            broadcastItem8.onUidRulesChanged(keyAt, i32);
                                        } catch (RemoteException unused12) {
                                        }
                                    }
                                }
                                this.this$0.mListeners.finishBroadcast();
                                for (int i33 = 0; i33 < size; i33++) {
                                    ((SomeArgs) sparseArray.valueAt(i33)).recycle();
                                }
                                return true;
                            case 24:
                                long uptimeMillis = SystemClock.uptimeMillis();
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    try {
                                        long j2 = Long.MAX_VALUE;
                                        for (int size2 =
                                                        this.this$0.mBackgroundTransitioningUids
                                                                        .size()
                                                                - 1;
                                                size2 >= 0;
                                                size2--) {
                                            long valueAt =
                                                    this.this$0.mBackgroundTransitioningUids
                                                            .valueAt(size2);
                                            if (valueAt > uptimeMillis) {
                                                j2 = Math.min(j2, valueAt);
                                            } else {
                                                int keyAt2 =
                                                        this.this$0.mBackgroundTransitioningUids
                                                                .keyAt(size2);
                                                this.this$0.mBackgroundTransitioningUids.removeAt(
                                                        size2);
                                                this.this$0.updateRuleForBackgroundUL(keyAt2);
                                                this.this$0.updateRulesForPowerRestrictionsUL(
                                                        keyAt2, false);
                                            }
                                        }
                                        NetworkPolicyManagerService networkPolicyManagerService7 =
                                                this.this$0;
                                        networkPolicyManagerService7
                                                        .mNextProcessBackgroundUidsTime =
                                                j2;
                                        if (j2 < Long.MAX_VALUE) {
                                            networkPolicyManagerService7.mHandler
                                                    .sendEmptyMessageAtTime(24, j2);
                                        }
                                    } finally {
                                    }
                                }
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        boolean updateUidStateUL;
                        boolean removeUidStateUL;
                        switch (i10) {
                            case 0:
                                return handleMessage$com$android$server$net$NetworkPolicyManagerService$15(
                                        message);
                            default:
                                int i102 = message.arg1;
                                int i11 = message.what;
                                if (i11 != 100) {
                                    if (i11 != 101) {
                                        return false;
                                    }
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    networkPolicyManagerService.getClass();
                                    Trace.traceBegin(2097152L, "onUidGone");
                                    try {
                                        synchronized (
                                                networkPolicyManagerService
                                                        .mUidStateCallbackInfos) {
                                            try {
                                                if (!networkPolicyManagerService
                                                        .mUidStateCallbackInfos.contains(i102)) {
                                                    synchronized (
                                                            networkPolicyManagerService
                                                                    .mUidRulesFirstLock) {
                                                        removeUidStateUL =
                                                                networkPolicyManagerService
                                                                        .removeUidStateUL(i102);
                                                    }
                                                    if (removeUidStateUL) {
                                                        networkPolicyManagerService
                                                                .updateNetworkStats(i102, false);
                                                    }
                                                }
                                            } finally {
                                            }
                                        }
                                        return true;
                                    } finally {
                                    }
                                }
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                Trace.traceBegin(2097152L, "onUidStateChanged");
                                try {
                                    synchronized (
                                            networkPolicyManagerService2.mUidStateCallbackInfos) {
                                        try {
                                            UidStateCallbackInfo uidStateCallbackInfo =
                                                    (UidStateCallbackInfo)
                                                            networkPolicyManagerService2
                                                                    .mUidStateCallbackInfos.get(
                                                                    i102);
                                            if (uidStateCallbackInfo != null) {
                                                int i12 = uidStateCallbackInfo.procState;
                                                long j = uidStateCallbackInfo.procStateSeq;
                                                int i13 = uidStateCallbackInfo.capability;
                                                uidStateCallbackInfo.isPending = false;
                                                synchronized (
                                                        networkPolicyManagerService2
                                                                .mUidRulesFirstLock) {
                                                    networkPolicyManagerService2.mLogger
                                                            .uidStateChanged(i102, i12, i13, j);
                                                    updateUidStateUL =
                                                            networkPolicyManagerService2
                                                                    .updateUidStateUL(
                                                                            i102, i12, i13, j);
                                                    networkPolicyManagerService2
                                                            .mActivityManagerInternal
                                                            .notifyNetworkPolicyRulesUpdated(
                                                                    i102, j);
                                                }
                                                if (updateUidStateUL) {
                                                    networkPolicyManagerService2.updateNetworkStats(
                                                            i102,
                                                            NetworkPolicyManager
                                                                    .isProcStateAllowedWhileOnRestrictBackground(
                                                                            i12, i13));
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    return true;
                                } finally {
                                }
                        }
                    }
                };
        final int i11 = 4;
        this.mChargingStateReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i11) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        this.mTetherListener =
                new TetheringManager
                        .TetheringEventCallback() { // from class:
                                                    // com.android.server.net.NetworkPolicyManagerService.18
                    public final void onUpstreamChanged(Network network) {
                        Log.d("NetworkPolicy", "onUpstreamChanged() network : " + network);
                        NetworkCapabilities networkCapabilities =
                                NetworkPolicyManagerService.this.mConnManager
                                        .getNetworkCapabilities(network);
                        Message obtainMessage =
                                NetworkPolicyManagerService.this.mHandler.obtainMessage(1007);
                        if (networkCapabilities == null || !networkCapabilities.hasTransport(0)) {
                            NetworkPolicyManagerService networkPolicyManagerService =
                                    NetworkPolicyManagerService.this;
                            obtainMessage.arg1 =
                                    networkPolicyManagerService.mTetheringState ? 1 : 0;
                            networkPolicyManagerService.mTetheringState = false;
                        } else {
                            NetworkPolicyManagerService networkPolicyManagerService2 =
                                    NetworkPolicyManagerService.this;
                            obtainMessage.arg1 =
                                    !networkPolicyManagerService2.mTetheringState ? 1 : 0;
                            networkPolicyManagerService2.mTetheringState = true;
                        }
                        obtainMessage.sendToTarget();
                    }
                };
        final int i12 = 5;
        this.mDdsChangedReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.net.NetworkPolicyManagerService.5
                    public final /* synthetic */ NetworkPolicyManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                    Context context2, Intent intent) {
                        if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                            final int intExtra =
                                    intent.getIntExtra(
                                            "android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                            int intExtra2 =
                                    intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            Slog.v(
                                    "NetworkPolicy",
                                    "mCarrierConfigReceiver() - subId: "
                                            + intExtra
                                            + ", slotId: "
                                            + intExtra2);
                            this.this$0.updateSubscriptions();
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                    try {
                                        String str =
                                                (String)
                                                        this.this$0.mSubIdToSubscriberId.get(
                                                                intExtra, null);
                                        if (str != null) {
                                            this.this$0.ensureActiveCarrierPolicyAL(intExtra, str);
                                            this.this$0.maybeUpdateCarrierPolicyCycleAL(
                                                    intExtra, str);
                                            this.this$0.mSubscriberIdToSlotId.put(
                                                    str, Integer.valueOf(intExtra2));
                                        } else {
                                            Slog.wtf(
                                                    "NetworkPolicy",
                                                    "Missing subscriberId for subId " + intExtra);
                                        }
                                        final NetworkPolicyManagerService
                                                networkPolicyManagerService = this.this$0;
                                        if (networkPolicyManagerService
                                                .mSupportSmartManagerForChina) {
                                            networkPolicyManagerService.mHandler.post(
                                                    new Runnable() { // from class:
                                                        // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            NetworkPolicyManagerService
                                                                    networkPolicyManagerService2 =
                                                                            NetworkPolicyManagerService
                                                                                    .this;
                                                            int i22 = intExtra;
                                                            if (i22 != -1) {
                                                                if (!((Boolean)
                                                                                networkPolicyManagerService2
                                                                                        .isOffPeakObserverRegisted
                                                                                        .get(
                                                                                                i22,
                                                                                                Boolean
                                                                                                        .FALSE))
                                                                        .booleanValue()) {
                                                                    networkPolicyManagerService2
                                                                            .isOffPeakObserverRegisted
                                                                            .put(i22, Boolean.TRUE);
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .registerContentObserver(
                                                                                    Settings.System
                                                                                            .getUriFor(
                                                                                                    NetworkPolicyManagerService
                                                                                                                    .KEY_IS_IN_OFF_PEAK_TIME
                                                                                                            + i22),
                                                                                    false,
                                                                                    networkPolicyManagerService2
                                                                                            .mOffPeakContentObserver);
                                                                }
                                                                networkPolicyManagerService2
                                                                        .checkOffPeakEnable(i22);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        NetworkPolicyManagerService
                                                                                .KEY_SM_EXTRAS_SUBID,
                                                                        i22);
                                                                try {
                                                                    networkPolicyManagerService2
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            NetworkPolicyManagerService
                                                                                                    .KEY_SM_PROVIDER_CONTENT_URI),
                                                                                    NetworkPolicyManagerService
                                                                                            .KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                                                                                    (String) null,
                                                                                    bundle);
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    Log.e(
                                                                            "NetworkPolicy",
                                                                            " call to smart manager"
                                                                                + " has exception ",
                                                                            e);
                                                                }
                                                            } else {
                                                                networkPolicyManagerService2
                                                                        .getClass();
                                                            }
                                                            StringBuilder m =
                                                                    BatteryService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    i22,
                                                                                    "updateOffPeakPlanConfig"
                                                                                        + " SubscriptionId:"
                                                                                        + " ",
                                                                                    " isoffpeakEnable:");
                                                            m.append(
                                                                    NetworkPolicyManagerService
                                                                            .isOffPeakEnable
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i22)));
                                                            Slog.v("NetworkPolicy", m.toString());
                                                        }
                                                    });
                                        }
                                        this.this$0.handleNetworkPoliciesUpdateAL(true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra != -1
                                && "android.intent.action.PACKAGE_ADDED".equals(action)) {
                            if (NetworkPolicyManagerService.LOGV) {
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        intExtra, "ACTION_PACKAGE_ADDED for uid=", "NetworkPolicy");
                            }
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                this.this$0.mInternetPermissionMap.delete(intExtra);
                                NetworkPolicyManagerService networkPolicyManagerService =
                                        this.this$0;
                                if (networkPolicyManagerService.mDeviceIdleMode) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, true)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                1, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            1, intExtra, 1);
                                }
                                networkPolicyManagerService.updateRuleForAppIdleUL(intExtra, -1);
                                if (networkPolicyManagerService.mRestrictPower) {
                                    if (!networkPolicyManagerService.isAllowlistedFromPowerSaveUL(
                                                    intExtra, false)
                                            && !networkPolicyManagerService
                                                    .isUidForegroundOnRestrictPowerUL(intExtra)) {
                                        networkPolicyManagerService.setUidFirewallRuleUL(
                                                3, intExtra, 0);
                                    }
                                    networkPolicyManagerService.setUidFirewallRuleUL(
                                            3, intExtra, 1);
                                }
                                if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                    networkPolicyManagerService.updateRuleForBackgroundUL(intExtra);
                                }
                                networkPolicyManagerService.updateRestrictedModeForUidUL(intExtra);
                                networkPolicyManagerService.updateRulesForPowerRestrictionsUL(
                                        intExtra, -1);
                                networkPolicyManagerService.updateRulesForDataUsageRestrictionsUL(
                                        intExtra);
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                    Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Slog.v(
                                    "NetworkPolicy",
                                    "do not remove any uid policy and update rules in case of"
                                        + " replacing");
                            return;
                        }
                        if (NetworkPolicyManagerService.LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra, "ACTION_UID_REMOVED for uid=", "NetworkPolicy");
                        }
                        synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
                            NetworkPolicyManagerService networkPolicyManagerService = this.this$0;
                            networkPolicyManagerService.getClass();
                            SparseIntArray sparseIntArray =
                                    NetworkPolicyManagerService.mFirewallRules;
                            int i22 = sparseIntArray.get(intExtra, 0);
                            if (i22 != 0) {
                                sparseIntArray.delete(intExtra);
                                networkPolicyManagerService.writeFirewallPolicyAL();
                                networkPolicyManagerService.removeFirewallRules(intExtra, i22);
                            }
                        }
                        synchronized (this.this$0.mUidRulesFirstLock) {
                            NetworkPolicyManagerService.m686$$Nest$monUidDeletedUL(
                                    this.this$0, intExtra);
                            synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                this.this$0.writePolicyAL();
                            }
                        }
                    }

                    private final void
                            onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                    Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra == -1) {
                            return;
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_ADDED")) {
                            synchronized (this.this$0.mUidRulesFirstLock) {
                                try {
                                    this.this$0.removeUserStateUL(intExtra, true, false);
                                    this.this$0.mMeteredRestrictedUids.remove(intExtra);
                                    if (action == "android.intent.action.USER_ADDED") {
                                        this.this$0.addDefaultRestrictBackgroundAllowlistUidsUL(
                                                intExtra);
                                    }
                                    synchronized (this.this$0.mNetworkPoliciesSecondLock) {
                                        this.this$0.updateRulesForGlobalChangeAL(true);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22;
                        boolean z2;
                        switch (i12) {
                            case 0:
                                synchronized (this.this$0.mUidRulesFirstLock) {
                                    this.this$0.updatePowerSaveAllowlistUL();
                                    NetworkPolicyManagerService networkPolicyManagerService =
                                            this.this$0;
                                    if (networkPolicyManagerService.mBackgroundNetworkRestricted) {
                                        networkPolicyManagerService
                                                .updateRulesForBackgroundChainUL();
                                    }
                                    this.this$0.updateRulesForRestrictPowerUL();
                                    this.this$0.updateRulesForAppIdleUL();
                                }
                                return;
                            case 1:
                                NetworkPolicyManagerService networkPolicyManagerService2 =
                                        this.this$0;
                                networkPolicyManagerService2.getClass();
                                ArrayMap arrayMap = new ArrayMap();
                                synchronized (
                                        networkPolicyManagerService2.mNetworkPoliciesSecondLock) {
                                    int i32 = 0;
                                    while (i32
                                            < networkPolicyManagerService2.mNetworkPolicy.size()) {
                                        try {
                                            NetworkPolicy networkPolicy =
                                                    (NetworkPolicy)
                                                            networkPolicyManagerService2
                                                                    .mNetworkPolicy.valueAt(i32);
                                            if (networkPolicy.template.getMatchRule() != 4
                                                    || networkPolicy.inferred) {
                                                i32++;
                                            } else {
                                                networkPolicyManagerService2.mNetworkPolicy
                                                        .removeAt(i32);
                                                Set wifiNetworkKeys =
                                                        networkPolicy.template.getWifiNetworkKeys();
                                                arrayMap.put(
                                                        wifiNetworkKeys.isEmpty()
                                                                ? null
                                                                : (String)
                                                                        wifiNetworkKeys
                                                                                .iterator()
                                                                                .next(),
                                                        Boolean.valueOf(networkPolicy.metered));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                if (!arrayMap.isEmpty()) {
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    networkPolicyManagerService2.mContext
                                                            .getSystemService(WifiManager.class);
                                    List<WifiConfiguration> configuredNetworks =
                                            wifiManager.getConfiguredNetworks();
                                    for (i22 = 0; i22 < configuredNetworks.size(); i22++) {
                                        WifiConfiguration wifiConfiguration =
                                                configuredNetworks.get(i22);
                                        Iterator it =
                                                wifiConfiguration.getAllNetworkKeys().iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                Boolean bool = (Boolean) arrayMap.get(str);
                                                if (bool != null) {
                                                    Slog.d(
                                                            "NetworkPolicy",
                                                            "Found network "
                                                                    + str
                                                                    + "; upgrading metered hint");
                                                    wifiConfiguration.meteredOverride =
                                                            bool.booleanValue() ? 1 : 2;
                                                    wifiManager.updateNetwork(wifiConfiguration);
                                                }
                                            }
                                        }
                                    }
                                    synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                                        synchronized (
                                                networkPolicyManagerService2
                                                        .mNetworkPoliciesSecondLock) {
                                            networkPolicyManagerService2.writePolicyAL();
                                        }
                                    }
                                }
                                this.this$0.mContext.unregisterReceiver(this);
                                return;
                            case 2:
                                this.this$0.updateNetworksInternal();
                                NetworkInfo networkInfo =
                                        (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo == null || !networkInfo.isConnected()) {
                                    this.this$0.mActiveNetworkType = -1;
                                } else {
                                    this.this$0.mActiveNetworkType = networkInfo.getType();
                                }
                                Slog.d(
                                        "NetworkPolicy",
                                        "mActiveNetworkType : "
                                                + this.this$0.mActiveNetworkType
                                                + ", networkInfo : "
                                                + networkInfo);
                                return;
                            case 3:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$14(
                                        context2, intent);
                                return;
                            case 4:
                                boolean z3 =
                                        this.this$0.mPowerManagerInternal.getLowPowerState(6)
                                                .batterySaverEnabled;
                                if (intent.getAction().equals("android.os.action.CHARGING")) {
                                    z2 = true;
                                } else {
                                    intent.getAction().equals("android.os.action.DISCHARGING");
                                    z2 = false;
                                }
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "ChargingState: new(", "), old(", z2);
                                m.append(this.this$0.mChargingState);
                                m.append(")");
                                Slog.d("NetworkPolicy", m.toString());
                                NetworkPolicyManagerService networkPolicyManagerService3 =
                                        this.this$0;
                                if (z2 != networkPolicyManagerService3.mChargingState) {
                                    networkPolicyManagerService3.mChargingState = z2;
                                    if (z3) {
                                        networkPolicyManagerService3
                                                .mHandler
                                                .obtainMessage(1005)
                                                .sendToTarget();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 5:
                                int intExtra = intent.getIntExtra("phone", 0);
                                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "mDdsChangedReceiver() - phoneId: ",
                                                ", mDefaultDataPhoneId: "),
                                        this.this$0.mDefaultDataPhoneId,
                                        "NetworkPolicy");
                                NetworkPolicyManagerService networkPolicyManagerService4 =
                                        this.this$0;
                                if (networkPolicyManagerService4.mDefaultDataPhoneId == intExtra) {
                                    return;
                                }
                                networkPolicyManagerService4.mDefaultDataPhoneId = intExtra;
                                return;
                            case 6:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$6(
                                        context2, intent);
                                return;
                            case 7:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$7(
                                        context2, intent);
                                return;
                            case 8:
                                onReceive$com$android$server$net$NetworkPolicyManagerService$8(
                                        context2, intent);
                                return;
                            default:
                                NetworkTemplate networkTemplate =
                                        (NetworkTemplate)
                                                intent.getParcelableExtra(
                                                        "android.net.NETWORK_TEMPLATE",
                                                        NetworkTemplate.class);
                                if ("com.android.server.net.action.SNOOZE_WARNING"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 34);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_RAPID"
                                        .equals(intent.getAction())) {
                                    this.this$0.performSnooze(networkTemplate, 45);
                                    return;
                                }
                                if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                                        .equals(intent.getAction())) {
                                    NetworkPolicyManagerService networkPolicyManagerService5 =
                                            this.this$0;
                                    networkPolicyManagerService5.mTetheringNotiSnooze =
                                            networkPolicyManagerService5.mClock.millis();
                                    Message obtainMessage =
                                            this.this$0.mHandler.obtainMessage(1007);
                                    obtainMessage.arg1 = 0;
                                    obtainMessage.sendToTarget();
                                    return;
                                }
                                return;
                        }
                    }
                };
        Objects.requireNonNull(context, "missing context");
        this.mContext = context;
        Objects.requireNonNull(iActivityManager, "missing activityManager");
        Objects.requireNonNull(iNetworkManagementService, "missing networkManagement");
        this.mNetworkManager = iNetworkManagementService;
        this.mPowerExemptionManager =
                (PowerExemptionManager) context.getSystemService(PowerExemptionManager.class);
        Objects.requireNonNull(clock, "missing Clock");
        this.mClock = clock;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mIPm = iPackageManager;
        Handler handler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("NetworkPolicy")
                                .getLooper(),
                        callback);
        this.mHandler = handler;
        this.mUidEventHandler =
                new Handler(
                        Watchdog$$ExternalSyntheticOutline0.m(-2, "NetworkPolicy.uid", false)
                                .getLooper(),
                        callback2);
        this.mSuppressDefaultPolicy = z;
        Objects.requireNonNull(dependencies, "missing Dependencies");
        this.mDeps = dependencies;
        this.mActiveDataSubIdListener = new ActiveDataSubIdListener();
        this.mPolicyFile = new AtomicFile(new File(file, "netpolicy.xml"), "net-policy");
        this.mSupportSmartManagerForChina =
                "com.samsung.android.sm_cn"
                        .equals(
                                SemFloatingFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME"));
        this.mFirewallPolicyFile =
                new AtomicFile(new File(file, "firewallpolicy.xml"), "firewall-policy");
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mNetworkStats =
                (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
        this.mMultipathPolicyTracker = new MultipathPolicyTracker(context, handler);
        LocalServices.addService(
                NetworkPolicyManagerInternalImpl.class, new NetworkPolicyManagerInternalImpl());
    }

    public NetworkPolicyManagerService(
            Context context,
            IActivityManager iActivityManager,
            NetworkManagementService networkManagementService) {
        this(
                context,
                iActivityManager,
                networkManagementService,
                AppGlobals.getPackageManager(),
                new BestClock(
                        ZoneOffset.UTC,
                        new Clock[] {SystemClock.currentNetworkTimeClock(), Clock.systemUTC()}),
                new File(Environment.getDataDirectory(), "system"),
                false,
                new Dependencies(context));
    }

    public static boolean addAll(ArraySet arraySet, int... iArr) {
        boolean z = true;
        for (int i : iArr) {
            z &= arraySet.add(Integer.valueOf(i));
        }
        return z;
    }

    public static NetworkTemplate buildTemplateCarrierMetered(String str) {
        Objects.requireNonNull(str);
        return new NetworkTemplate.Builder(10)
                .setSubscriberIds(Set.of(str))
                .setMeteredness(1)
                .build();
    }

    public static Intent buildViewDataUsageIntent(
            Resources resources, NetworkTemplate networkTemplate) {
        Intent intent = new Intent();
        intent.setComponent(
                ComponentName.unflattenFromString(
                        resources.getString(R.string.data_usage_restricted_body)));
        intent.addFlags(268435456);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
        return intent;
    }

    public static void collectKeys(SparseArray sparseArray, SparseBooleanArray sparseBooleanArray) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            sparseBooleanArray.put(sparseArray.keyAt(i), true);
        }
    }

    public static UidBlockedState getOrCreateUidBlockedStateForUid(int i, SparseArray sparseArray) {
        UidBlockedState uidBlockedState = (UidBlockedState) sparseArray.get(i);
        if (uidBlockedState != null) {
            return uidBlockedState;
        }
        UidBlockedState uidBlockedState2 = new UidBlockedState();
        sparseArray.put(i, uidBlockedState2);
        return uidBlockedState2;
    }

    public static boolean isKorOperator() {
        final String str = SystemProperties.get("ro.csc.sales_code", "NONE");
        return Arrays.asList(
                        "SKC", "KTC", "LUC", "KOO", "SKT", "SKO", "KTT", "KTO", "LGT", "LUO", "K06",
                        "K01")
                .stream()
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda7
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return str.contains((String) obj);
                            }
                        });
    }

    public static boolean isNaGsm(String str) {
        String string =
                SemCarrierFeature.getInstance()
                        .getString(0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
        if ("ALL".equals(str)) {
            if (string != null && string.endsWith("GSM-USA")) {
                return true;
            }
        } else if (("ATT".equals(str)
                        || "TMO".equals(str)
                        || "MTR".equals(str)
                        || "ASR".equals(str))
                && string != null
                && string.contains(str)) {
            return true;
        }
        return false;
    }

    public static NetworkTemplate normalizeTemplate(NetworkTemplate networkTemplate, List list) {
        if (networkTemplate.getSubscriberIds().isEmpty()) {
            return networkTemplate;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String[] strArr = (String[]) it.next();
            ArraySet arraySet = new ArraySet(strArr);
            if (arraySet.size() != strArr.length) {
                Log.wtf(
                        "NetworkPolicy",
                        "Duplicated merged list detected: " + Arrays.toString(strArr));
            }
            Iterator it2 = networkTemplate.getSubscriberIds().iterator();
            while (it2.hasNext()) {
                if (CollectionUtils.contains(strArr, (String) it2.next())) {
                    return new NetworkTemplate.Builder(networkTemplate.getMatchRule())
                            .setWifiNetworkKeys(networkTemplate.getWifiNetworkKeys())
                            .setSubscriberIds(arraySet)
                            .setMeteredness(networkTemplate.getMeteredness())
                            .build();
                }
            }
        }
        return networkTemplate;
    }

    public static void updateRulesForAllowlistedAppIds(
            SparseIntArray sparseIntArray, SparseBooleanArray sparseBooleanArray, int i) {
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            if (sparseBooleanArray.valueAt(size)) {
                sparseIntArray.put(UserHandle.getUid(i, sparseBooleanArray.keyAt(size)), 1);
            }
        }
    }

    public static void writeNetstatsFiles() {
        Slog.d("NetworkPolicy", "Copy netStats files");
        try {
            Path path = new File("/data/log/netstats").toPath();
            if (!Files.exists(path, new LinkOption[0])) {
                Slog.d("NetworkPolicy", "Dir doesn't exists. Make dir.");
                Files.createDirectory(path, new FileAttribute[0]);
            }
            File file = new File("/data/misc/apexdata/com.android.tethering/netstats");
            if (file.listFiles() != null) {
                for (File file2 : file.listFiles()) {
                    Files.copy(
                            file2.toPath(),
                            path.resolve(file2.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            Slog.v("NetworkPolicy", "writeNetstatsFiles, Copy error: ");
            e.printStackTrace();
        }
    }

    public final boolean addDefaultRestrictBackgroundAllowlistUidsUL(int i) {
        SystemConfig systemConfig = SystemConfig.getInstance();
        PackageManager packageManager = this.mContext.getPackageManager();
        ArraySet arraySet = systemConfig.mAllowInDataUsageSave;
        boolean z = false;
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            String str = (String) arraySet.valueAt(i2);
            boolean z2 = LOGD;
            if (z2) {
                Slog.d(
                        "NetworkPolicy",
                        "checking restricted background exemption for package "
                                + str
                                + " and user "
                                + i);
            }
            try {
                ApplicationInfo applicationInfoAsUser =
                        packageManager.getApplicationInfoAsUser(str, 1048576, i);
                if (!applicationInfoAsUser.isPrivilegedApp()) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "addDefaultRestrictBackgroundAllowlistUidsUL(): skipping non-privileged"
                                + " app  ",
                            str,
                            "NetworkPolicy");
                }
                int uid = UserHandle.getUid(i, applicationInfoAsUser.uid);
                if (uid != 0) {
                    this.mDefaultRestrictBackgroundAllowlistUids.append(uid, true);
                    setUidFirewallRuleUL(10, uid, 1);
                    if (z2) {
                        StringBuilder m =
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        uid,
                                        i,
                                        "Adding uid ",
                                        " (user ",
                                        ") to default restricted background allowlist. Revoked"
                                            + " status: ");
                        m.append(this.mRestrictBackgroundAllowlistRevokedUids.get(uid));
                        Slog.d("NetworkPolicy", m.toString());
                    }
                    if (!this.mRestrictBackgroundAllowlistRevokedUids.get(uid)) {
                        if (z2) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    StorageManagerService$$ExternalSyntheticOutline0.m(
                                            uid,
                                            "adding default package ",
                                            str,
                                            " (uid ",
                                            " for user "),
                                    i,
                                    ") to restrict background allowlist",
                                    "NetworkPolicy");
                        }
                        setUidPolicyUncheckedUL(uid, 4);
                        z = true;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                if (LOGD) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            "No ApplicationInfo for package ", str, "NetworkPolicy");
                }
            }
        }
        return z;
    }

    public void addIdleHandler(MessageQueue.IdleHandler idleHandler) {
        this.mHandler.getLooper().getQueue().addIdleHandler(idleHandler);
    }

    public final void addUidPolicy(int i, int i2) {
        addUidPolicy_enforcePermission();
        if (!UserHandle.isApp(i)) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "cannot apply policy to UID "));
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                int i3 = this.mUidPolicy.get(i, 0);
                int i4 = i2 | i3;
                if (i3 != i4) {
                    setUidPolicyUncheckedUL(i, i3, i4);
                    this.mLogger.uidPolicyChanged(i, i3, i4);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void applyFirewallRules(int i, int i2) {
        if ((i2 & 1) != 0) {
            try {
                this.mNetworkManager.setFirewallRuleMobileData(i, false);
                Slog.v("NetworkPolicy", "apply FIREWALL_POLICY_REJECT_MOBILE_DATA for UID:" + i);
            } catch (RemoteException unused) {
                return;
            }
        }
        if ((i2 & 2) != 0) {
            this.mNetworkManager.setFirewallRuleWifi(i, false);
            Slog.v("NetworkPolicy", "apply FIREWALL_POLICY_REJECT_WIFI for UID:" + i);
        }
    }

    public final void bindConnectivityManager() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        Objects.requireNonNull(connectivityManager, "missing ConnectivityManager");
        this.mConnManager = connectivityManager;
    }

    public NetworkPolicy buildDefaultCarrierPolicy(int i, String str) {
        NetworkPolicy networkPolicy =
                new NetworkPolicy(
                        buildTemplateCarrierMetered(str),
                        NetworkPolicy.buildRule(
                                ZonedDateTime.now().getDayOfMonth(), ZoneId.systemDefault()),
                        getPlatformDefaultWarningBytes(),
                        -1L,
                        -1L,
                        -1L,
                        true,
                        true);
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                updateDefaultCarrierPolicyAL(i, networkPolicy);
            }
        }
        return networkPolicy;
    }

    public final void checkOffPeakEnable(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder sb = new StringBuilder();
        sb.append(KEY_OFF_PEAK_DATA_SWITCH);
        sb.append(i);
        boolean z = false;
        boolean z2 = Settings.System.getInt(contentResolver, sb.toString(), 0) == 1;
        long j =
                Settings.System.getLong(
                        this.mContext.getContentResolver(), KEY_OFF_PEAK_DATA_LIMIT + i, 0L);
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(KEY_IS_IN_OFF_PEAK_TIME);
        sb2.append(i);
        boolean z3 = Settings.System.getInt(contentResolver2, sb2.toString(), 0) == 1;
        ConcurrentHashMap concurrentHashMap = isOffPeakEnable;
        Integer valueOf = Integer.valueOf(i);
        if (z2 && z3 && j > 0) {
            z = true;
        }
        concurrentHashMap.put(valueOf, Boolean.valueOf(z));
    }

    @NeverCompile
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkPolicy", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            ArraySet arraySet = new ArraySet(strArr.length);
            for (String str : strArr) {
                arraySet.add(str);
            }
            writeNetstatsFiles();
            synchronized (this.mUidRulesFirstLock) {
                synchronized (this.mNetworkPoliciesSecondLock) {
                    try {
                        if (arraySet.contains("--unsnooze")) {
                            for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
                                ((NetworkPolicy) this.mNetworkPolicy.valueAt(size)).clearSnooze();
                            }
                            handleNetworkPoliciesUpdateAL(true);
                            indentingPrintWriter.println("Cleared snooze timestamps");
                            return;
                        }
                        indentingPrintWriter.print("System ready: ");
                        indentingPrintWriter.println(this.mSystemReady);
                        indentingPrintWriter.print("Restrict background: ");
                        indentingPrintWriter.println(this.mRestrictBackground);
                        indentingPrintWriter.print("Restrict power: ");
                        indentingPrintWriter.println(this.mRestrictPower);
                        indentingPrintWriter.print("Device idle: ");
                        indentingPrintWriter.println(this.mDeviceIdleMode);
                        indentingPrintWriter.print("Restricted networking mode: ");
                        indentingPrintWriter.println(this.mRestrictedNetworkingMode);
                        indentingPrintWriter.print("Low Power Standby mode: ");
                        indentingPrintWriter.println(this.mLowPowerStandbyActive);
                        synchronized (this.mMeteredIfacesLock) {
                            indentingPrintWriter.print("Metered ifaces: ");
                            indentingPrintWriter.println(this.mMeteredIfaces);
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Flags:");
                        indentingPrintWriter.println(
                                "com.android.server.net.network_blocked_for_top_sleeping_and_above:"
                                    + " "
                                        + this.mBackgroundNetworkRestricted);
                        indentingPrintWriter.println(
                                "com.android.server.net.use_metered_firewall_chains: "
                                        + this.mUseMeteredFirewallChains);
                        indentingPrintWriter.println(
                                "com.android.server.net.use_different_delays_for_background_chain:"
                                    + " false");
                        indentingPrintWriter.println();
                        indentingPrintWriter.println(
                                "mRestrictBackgroundLowPowerMode: "
                                        + this.mRestrictBackgroundLowPowerMode);
                        indentingPrintWriter.println(
                                "mRestrictBackgroundBeforeBsm: "
                                        + this.mRestrictBackgroundBeforeBsm);
                        indentingPrintWriter.println(
                                "mLoadedRestrictBackground: " + this.mLoadedRestrictBackground);
                        indentingPrintWriter.println(
                                "mRestrictBackgroundChangedInBsm: "
                                        + this.mRestrictBackgroundChangedInBsm);
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Network policies:");
                        indentingPrintWriter.increaseIndent();
                        for (int i = 0; i < this.mNetworkPolicy.size(); i++) {
                            indentingPrintWriter.println(
                                    ((NetworkPolicy) this.mNetworkPolicy.valueAt(i)).toString());
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Subscription plans:");
                        indentingPrintWriter.increaseIndent();
                        for (int i2 = 0; i2 < this.mSubscriptionPlans.size(); i2++) {
                            indentingPrintWriter.println(
                                    "Subscriber ID " + this.mSubscriptionPlans.keyAt(i2) + ":");
                            indentingPrintWriter.increaseIndent();
                            SubscriptionPlan[] subscriptionPlanArr =
                                    (SubscriptionPlan[]) this.mSubscriptionPlans.valueAt(i2);
                            if (!ArrayUtils.isEmpty(subscriptionPlanArr)) {
                                for (SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
                                    indentingPrintWriter.println(subscriptionPlan);
                                }
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Active subscriptions:");
                        indentingPrintWriter.increaseIndent();
                        for (int i3 = 0; i3 < this.mSubIdToSubscriberId.size(); i3++) {
                            indentingPrintWriter.println(
                                    this.mSubIdToSubscriberId.keyAt(i3)
                                            + "="
                                            + NetworkIdentityUtils.scrubSubscriberId(
                                                    (String)
                                                            this.mSubIdToSubscriberId.valueAt(i3)));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        Iterator it = ((ArrayList) this.mMergedSubscriberIds).iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter.println(
                                    "Merged subscriptions: "
                                            + Arrays.toString(
                                                    NetworkIdentityUtils.scrubSubscriberIds(
                                                            (String[]) it.next())));
                        }
                        if (this.mTetheringWarningBytes.size() > 0) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println(
                                    "mTetheringWarningBytes: "
                                            + this.mTetheringWarningBytes.toString());
                            indentingPrintWriter.println(
                                    "mTetheringNotiSnooze: " + this.mTetheringNotiSnooze);
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Policy for UIDs:");
                        indentingPrintWriter.increaseIndent();
                        int size2 = this.mUidPolicy.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            int keyAt = this.mUidPolicy.keyAt(i4);
                            int valueAt = this.mUidPolicy.valueAt(i4);
                            indentingPrintWriter.print("UID=");
                            indentingPrintWriter.print(keyAt);
                            indentingPrintWriter.print(" policy=");
                            indentingPrintWriter.print(
                                    NetworkPolicyManager.uidPoliciesToString(valueAt));
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        int size3 = this.mPowerSaveWhitelistExceptIdleAppIds.size();
                        if (size3 > 0) {
                            indentingPrintWriter.println(
                                    "Power save whitelist (except idle) app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i5 = 0; i5 < size3; i5++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i5));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(
                                        this.mPowerSaveWhitelistExceptIdleAppIds.valueAt(i5));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size4 = this.mPowerSaveWhitelistAppIds.size();
                        if (size4 > 0) {
                            indentingPrintWriter.println("Power save whitelist app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i6 = 0; i6 < size4; i6++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mPowerSaveWhitelistAppIds.keyAt(i6));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(
                                        this.mPowerSaveWhitelistAppIds.valueAt(i6));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size5 = this.mAppIdleTempWhitelistAppIds.size();
                        if (size5 > 0) {
                            indentingPrintWriter.println("App idle whitelist app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i7 = 0; i7 < size5; i7++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mAppIdleTempWhitelistAppIds.keyAt(i7));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(
                                        this.mAppIdleTempWhitelistAppIds.valueAt(i7));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size6 = this.mDefaultRestrictBackgroundAllowlistUids.size();
                        if (size6 > 0) {
                            indentingPrintWriter.println(
                                    "Default restrict background allowlist uids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i8 = 0; i8 < size6; i8++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mDefaultRestrictBackgroundAllowlistUids.keyAt(i8));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size7 = this.mRestrictBackgroundAllowlistRevokedUids.size();
                        if (size7 > 0) {
                            indentingPrintWriter.println(
                                    "Default restrict background allowlist uids revoked by users:");
                            indentingPrintWriter.increaseIndent();
                            for (int i9 = 0; i9 < size7; i9++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mRestrictBackgroundAllowlistRevokedUids.keyAt(i9));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size8 = this.mLowPowerStandbyAllowlistUids.size();
                        if (size8 > 0) {
                            indentingPrintWriter.println("Low Power Standby allowlist uids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i10 = 0; i10 < size8; i10++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(
                                        this.mLowPowerStandbyAllowlistUids.keyAt(i10));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        if (this.mBackgroundNetworkRestricted) {
                            indentingPrintWriter.println();
                            int size9 = this.mBackgroundTransitioningUids.size();
                            if (size9 > 0) {
                                long uptimeMillis = SystemClock.uptimeMillis();
                                indentingPrintWriter.println("Uids transitioning to background:");
                                indentingPrintWriter.increaseIndent();
                                for (int i11 = 0; i11 < size9; i11++) {
                                    indentingPrintWriter.print("UID=");
                                    indentingPrintWriter.print(
                                            this.mBackgroundTransitioningUids.keyAt(i11));
                                    indentingPrintWriter.print(", ");
                                    TimeUtils.formatDuration(
                                            this.mBackgroundTransitioningUids.valueAt(i11),
                                            uptimeMillis,
                                            indentingPrintWriter);
                                    indentingPrintWriter.println();
                                }
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.println();
                        }
                        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                        collectKeys(this.mUidState, sparseBooleanArray);
                        synchronized (this.mUidBlockedState) {
                            collectKeys(this.mUidBlockedState, sparseBooleanArray);
                        }
                        synchronized (this.mUidStateCallbackInfos) {
                            collectKeys(this.mUidStateCallbackInfos, sparseBooleanArray);
                        }
                        indentingPrintWriter.println("Status for all known UIDs:");
                        indentingPrintWriter.increaseIndent();
                        int size10 = sparseBooleanArray.size();
                        for (int i12 = 0; i12 < size10; i12++) {
                            int keyAt2 = sparseBooleanArray.keyAt(i12);
                            indentingPrintWriter.print("UID", Integer.valueOf(keyAt2));
                            indentingPrintWriter.print(
                                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN,
                                    (NetworkPolicyManager.UidState) this.mUidState.get(keyAt2));
                            synchronized (this.mUidBlockedState) {
                                indentingPrintWriter.print(
                                        "blocked_state",
                                        (UidBlockedState) this.mUidBlockedState.get(keyAt2));
                            }
                            synchronized (this.mUidStateCallbackInfos) {
                                UidStateCallbackInfo uidStateCallbackInfo =
                                        (UidStateCallbackInfo)
                                                this.mUidStateCallbackInfos.get(keyAt2);
                                indentingPrintWriter.println();
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.print("callback_info", uidStateCallbackInfo);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Admin restricted uids for metered data:");
                        indentingPrintWriter.increaseIndent();
                        int size11 = this.mMeteredRestrictedUids.size();
                        for (int i13 = 0; i13 < size11; i13++) {
                            indentingPrintWriter.print(
                                    "u" + this.mMeteredRestrictedUids.keyAt(i13) + ": ");
                            indentingPrintWriter.println(this.mMeteredRestrictedUids.valueAt(i13));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Network to interfaces:");
                        indentingPrintWriter.increaseIndent();
                        for (int i14 = 0; i14 < this.mNetworkToIfaces.size(); i14++) {
                            int keyAt3 = this.mNetworkToIfaces.keyAt(i14);
                            indentingPrintWriter.println(
                                    keyAt3 + ": " + this.mNetworkToIfaces.get(keyAt3));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.print("Active notifications: ");
                        indentingPrintWriter.println(this.mActiveNotifs);
                        indentingPrintWriter.println();
                        this.mStatLogger.dump(indentingPrintWriter);
                        this.mLogger.dumpLogs(indentingPrintWriter);
                        indentingPrintWriter.println();
                        this.mMultipathPolicyTracker.dump(indentingPrintWriter);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final void enableFirewallChainUL(int i, boolean z) {
        if (this.mFirewallChainStates.indexOfKey(i) < 0 || this.mFirewallChainStates.get(i) != z) {
            this.mFirewallChainStates.put(i, z);
            try {
                this.mNetworkManager.setFirewallChainEnabled(i, z);
                this.mLogger.firewallChainEnabled(i, z);
            } catch (RemoteException unused) {
            } catch (IllegalStateException e) {
                Log.wtf("NetworkPolicy", "problem enable firewall chain", e);
            }
        }
    }

    public final void enforceAnyPermissionOf(String... strArr) {
        for (String str : strArr) {
            if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
                return;
            }
        }
        throw new SecurityException(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Requires one of the following permissions: "),
                        String.join(", ", strArr),
                        "."));
    }

    public final void enforceSubscriptionPlanAccess(int i, int i2, String str) {
        this.mAppOps.checkPackage(i2, str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PersistableBundle configForSubId = this.mCarrierConfigManager.getConfigForSubId(i);
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
            if (telephonyManager == null || !telephonyManager.hasCarrierPrivileges(i)) {
                if (configForSubId != null) {
                    String string =
                            configForSubId.getString("config_plans_package_override_string", null);
                    if (!TextUtils.isEmpty(string) && Objects.equals(string, str)) {
                        return;
                    }
                }
                String defaultCarrierServicePackageName =
                        this.mCarrierConfigManager.getDefaultCarrierServicePackageName();
                if (TextUtils.isEmpty(defaultCarrierServicePackageName)
                        || !Objects.equals(defaultCarrierServicePackageName, str)) {
                    String str2 =
                            SystemProperties.get("persist.sys.sub_plan_owner." + i, (String) null);
                    if (TextUtils.isEmpty(str2) || !Objects.equals(str2, str)) {
                        String str3 = SystemProperties.get("fw.sub_plan_owner." + i, (String) null);
                        if (TextUtils.isEmpty(str3) || !Objects.equals(str3, str)) {
                            this.mContext.enforceCallingOrSelfPermission(
                                    "android.permission.MANAGE_SUBSCRIPTION_PLANS",
                                    "NetworkPolicy");
                        }
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0188  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enqueueNotification(
            android.net.NetworkPolicy r31,
            int r32,
            long r33,
            android.content.pm.ApplicationInfo r35) {
        /*
            Method dump skipped, instructions count: 1130
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyManagerService.enqueueNotification(android.net.NetworkPolicy,"
                    + " int, long, android.content.pm.ApplicationInfo):void");
    }

    public final boolean ensureActiveCarrierPolicyAL(int i, String str) {
        NetworkIdentity build =
                new NetworkIdentity.Builder()
                        .setType(0)
                        .setSubscriberId(str)
                        .setMetered(true)
                        .setDefaultNetwork(true)
                        .setSubId(i)
                        .build();
        for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
            NetworkTemplate networkTemplate = (NetworkTemplate) this.mNetworkPolicy.keyAt(size);
            if (networkTemplate.matches(build)) {
                if (LOGD) {
                    Slog.d(
                            "NetworkPolicy",
                            "Found template "
                                    + networkTemplate
                                    + " which matches subscriber "
                                    + NetworkIdentityUtils.scrubSubscriberId(str));
                }
                return false;
            }
        }
        Slog.i(
                "NetworkPolicy",
                "No policy for subscriber "
                        + NetworkIdentityUtils.scrubSubscriberId(str)
                        + "; generating default policy");
        setNetworkPolicies(
                (NetworkPolicy[])
                        ArrayUtils.appendElement(
                                NetworkPolicy.class,
                                getNetworkPolicies(this.mContext.getOpPackageName()),
                                buildDefaultCarrierPolicy(i, str)));
        return true;
    }

    public final void factoryReset(String str) {
        factoryReset_enforcePermission();
        if (this.mUserManager.hasUserRestriction("no_network_reset")) {
            return;
        }
        NetworkPolicy[] networkPolicies = getNetworkPolicies(this.mContext.getOpPackageName());
        NetworkTemplate buildTemplateCarrierMetered =
                str != null ? buildTemplateCarrierMetered(str) : null;
        NetworkTemplate build =
                str != null
                        ? new NetworkTemplate.Builder(1)
                                .setSubscriberIds(Set.of(str))
                                .setMeteredness(1)
                                .build()
                        : null;
        for (NetworkPolicy networkPolicy : networkPolicies) {
            if (networkPolicy.template.equals(buildTemplateCarrierMetered)
                    || networkPolicy.template.equals(build)) {
                networkPolicy.limitBytes = -1L;
                networkPolicy.inferred = false;
                networkPolicy.clearSnooze();
            }
        }
        setNetworkPolicies(networkPolicies);
        setRestrictBackground(false);
        if (!this.mUserManager.hasUserRestriction("no_control_apps")) {
            for (int i : getUidsWithPolicy(1)) {
                setUidPolicy(i, 0);
            }
        }
        Slog.d("NetworkPolicy", "remove tag file  in netStats");
        try {
            File file = new File("/data/misc/apexdata/com.android.tethering/netstats");
            if (file.listFiles() != null) {
                for (File file2 : file.listFiles()) {
                    if (file2.getName().startsWith("uid_tag")) {
                        Files.delete(file2.toPath());
                    }
                }
            }
        } catch (Exception e) {
            Slog.v("NetworkPolicy", "writeNetstatsFiles, Copy error: ");
            e.printStackTrace();
        }
    }

    public final int findRelevantSubIdNL(NetworkTemplate networkTemplate) {
        for (int i = 0; i < this.mSubIdToSubscriberId.size(); i++) {
            int keyAt = this.mSubIdToSubscriberId.keyAt(i);
            if (networkTemplate.matches(
                    new NetworkIdentity.Builder()
                            .setType(0)
                            .setSubscriberId((String) this.mSubIdToSubscriberId.valueAt(i))
                            .setMetered(true)
                            .setDefaultNetwork(true)
                            .setSubId(keyAt)
                            .build())) {
                return keyAt;
            }
        }
        return -1;
    }

    public final void forEachUid(String str, final IntConsumer intConsumer) {
        if (Trace.isTagEnabled(2097152L)) {
            Trace.traceBegin(2097152L, "forEachUid-".concat(str));
        }
        try {
            Trace.traceBegin(2097152L, "list-users");
            List users = this.mUserManager.getUsers();
            Trace.traceEnd(2097152L);
            Trace.traceBegin(2097152L, "iterate-uids");
            PackageManagerInternal packageManagerInternal =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            int size = users.size();
            for (int i = 0; i < size; i++) {
                final int i2 = ((UserInfo) users.get(i)).id;
                final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                packageManagerInternal.forEachInstalledPackage(
                        i2,
                        new Consumer() { // from class:
                                         // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
                                int i3 = i2;
                                IntConsumer intConsumer2 = intConsumer;
                                AndroidPackage androidPackage = (AndroidPackage) obj;
                                int uid = androidPackage.getUid();
                                if (androidPackage.getSharedUserId() != null) {
                                    if (sparseBooleanArray2.indexOfKey(uid) >= 0) {
                                        return;
                                    } else {
                                        sparseBooleanArray2.put(uid, true);
                                    }
                                }
                                intConsumer2.accept(UserHandle.getUid(i3, uid));
                            }
                        });
            }
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final int[] getAllFirewallRuleMobileData() {
        ArrayList arrayList = new ArrayList();
        synchronized (mFirewallPoliciesLock) {
            try {
                Slog.d("NetworkPolicy", "return WifiOnlyUidList");
                int i = 0;
                while (true) {
                    SparseIntArray sparseIntArray = mFirewallRules;
                    if (i < sparseIntArray.size()) {
                        int keyAt = sparseIntArray.keyAt(i);
                        if ((sparseIntArray.valueAt(i) & 1) == 1) {
                            arrayList.add(Integer.valueOf(keyAt));
                            Slog.d("NetworkPolicy", "wifiOnlyUidList uid : " + keyAt);
                        }
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
    }

    public int[] getAppIdleWhitelist() {
        int[] iArr;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (this.mUidRulesFirstLock) {
            try {
                int size = this.mAppIdleTempWhitelistAppIds.size();
                iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = this.mAppIdleTempWhitelistAppIds.keyAt(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    public int getCycleDayFromCarrierConfig(PersistableBundle persistableBundle, int i) {
        int i2;
        if (persistableBundle == null
                || (i2 = persistableBundle.getInt("monthly_data_cycle_day_int")) == -1) {
            return i;
        }
        Calendar calendar = Calendar.getInstance();
        if (i2 >= calendar.getMinimum(5) && i2 <= calendar.getMaximum(5)) {
            return i2;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(
                i2,
                "Invalid date in CarrierConfigManager.KEY_MONTHLY_DATA_CYCLE_DAY_INT: ",
                "NetworkPolicy");
        return i;
    }

    public final int getEffectiveBlockedReasons(int i) {
        int i2;
        synchronized (this.mUidBlockedState) {
            UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
            i2 = uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons;
        }
        return i2;
    }

    public final boolean getFirewallRuleMobileData(int i) {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (mFirewallPoliciesLock) {
            int i2 = mFirewallRules.get(i, 0);
            Slog.v("NetworkPolicy", "getFirewallRuleMobileData UID:" + i + " policy:" + i2);
            z = (i2 & 1) == 0;
        }
        return z;
    }

    public final boolean getFirewallRuleWifi(int i) {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (mFirewallPoliciesLock) {
            int i2 = mFirewallRules.get(i, 0);
            Slog.v("NetworkPolicy", "getFirewallRuleWifi UID:" + i + " policy:" + i2);
            z = (i2 & 2) == 0;
        }
        return z;
    }

    public Handler getHandlerForTesting() {
        return this.mHandler;
    }

    public long getLimitBytesFromCarrierConfig(PersistableBundle persistableBundle, long j) {
        if (persistableBundle == null) {
            return j;
        }
        long j2 = persistableBundle.getLong("data_limit_threshold_bytes_long");
        if (j2 == -2 || j2 == -1) {
            return -1L;
        }
        if (j2 >= 0) {
            return j2;
        }
        Slog.e(
                "NetworkPolicy",
                "Invalid value in CarrierConfigManager.KEY_DATA_LIMIT_THRESHOLD_BYTES_LONG;"
                    + " expected a non-negative value but got: "
                        + j2);
        return j;
    }

    public final int getMultipathPreference(Network network) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        MultipathPolicyTracker multipathPolicyTracker = this.mMultipathPolicyTracker;
        Integer num = null;
        if (network == null) {
            multipathPolicyTracker.getClass();
        } else {
            MultipathPolicyTracker.MultipathTracker multipathTracker =
                    (MultipathPolicyTracker.MultipathTracker)
                            multipathPolicyTracker.mMultipathTrackers.get(network);
            if (multipathTracker != null) {
                num = Integer.valueOf(multipathTracker.mMultipathBudget > 0 ? 3 : 0);
            }
        }
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public final NetworkPolicy[] getNetworkPolicies(String str) {
        NetworkPolicy[] networkPolicyArr;
        getNetworkPolicies_enforcePermission();
        try {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.READ_PRIVILEGED_PHONE_STATE", "NetworkPolicy");
        } catch (SecurityException unused) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.READ_PHONE_STATE", "NetworkPolicy");
            if (this.mAppOps.noteOp(51, Binder.getCallingUid(), str) != 0) {
                return new NetworkPolicy[0];
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                int size = this.mNetworkPolicy.size();
                networkPolicyArr = new NetworkPolicy[size];
                for (int i = 0; i < size; i++) {
                    networkPolicyArr[i] = (NetworkPolicy) this.mNetworkPolicy.valueAt(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return networkPolicyArr;
    }

    public final long getPlatformDefaultWarningBytes() {
        if (this.mContext.getResources().getInteger(R.integer.config_recentVibrationsDumpSizeLimit)
                == -1) {
            return -1L;
        }
        return DataUnit.GIGABYTES.toBytes(r4 / 1024);
    }

    public final SubscriptionPlan getPrimarySubscriptionPlanLocked(int i) {
        SubscriptionPlan[] subscriptionPlanArr =
                (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
        if (ArrayUtils.isEmpty(subscriptionPlanArr)) {
            return null;
        }
        int length = subscriptionPlanArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            SubscriptionPlan subscriptionPlan = subscriptionPlanArr[i2];
            if (subscriptionPlan.getCycleRule().isRecurring()
                    || subscriptionPlan
                            .cycleIterator()
                            .next()
                            .contains((Range<ZonedDateTime>) ZonedDateTime.now(this.mClock))) {
                return subscriptionPlan;
            }
        }
        return null;
    }

    public final boolean getRestrictBackground() {
        boolean z;
        getRestrictBackground_enforcePermission();
        synchronized (this.mUidRulesFirstLock) {
            z = this.mRestrictBackground;
        }
        return z;
    }

    public final int getRestrictBackgroundByCaller() {
        getRestrictBackgroundByCaller_enforcePermission();
        return getRestrictBackgroundStatusInternal(Binder.getCallingUid());
    }

    public final int getRestrictBackgroundStatus(int i) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        return getRestrictBackgroundStatusInternal(i);
    }

    public final int getRestrictBackgroundStatusInternal(int i) {
        synchronized (this.mUidRulesFirstLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int uidPolicy = getUidPolicy(i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (uidPolicy == 1) {
                        return 3;
                    }
                    if (this.mRestrictBackground) {
                        return (this.mUidPolicy.get(i) & 4) != 0 ? 2 : 3;
                    }
                    return 1;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) {
        SubscriptionPlan primarySubscriptionPlanLocked;
        enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
        synchronized (this.mNetworkPoliciesSecondLock) {
            primarySubscriptionPlanLocked =
                    getPrimarySubscriptionPlanLocked(findRelevantSubIdNL(networkTemplate));
        }
        return primarySubscriptionPlanLocked;
    }

    public final SubscriptionPlan[] getSubscriptionPlans(int i, String str) {
        enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
        String str2 = SystemProperties.get("fw.fake_plan");
        if (TextUtils.isEmpty(str2)) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                try {
                    String str3 = (String) this.mSubscriptionPlansOwner.get(i);
                    if (!Objects.equals(str3, str)
                            && UserHandle.getCallingAppId() != 1000
                            && UserHandle.getCallingAppId() != 1001) {
                        Log.w(
                                "NetworkPolicy",
                                "Not returning plans because caller "
                                        + str
                                        + " doesn't match owner "
                                        + str3);
                        return null;
                    }
                    return (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        if ("month_hard".equals(str2)) {
            SubscriptionPlan.Builder title =
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile");
            DataUnit dataUnit = DataUnit.GIBIBYTES;
            arrayList.add(
                    title.setDataLimit(dataUnit.toBytes(5L), 1)
                            .setDataUsage(
                                    dataUnit.toBytes(1L),
                                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile Happy")
                            .setDataLimit(Long.MAX_VALUE, 1)
                            .setDataUsage(
                                    dataUnit.toBytes(5L),
                                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile, Charged after limit")
                            .setDataLimit(dataUnit.toBytes(5L), 1)
                            .setDataUsage(
                                    dataUnit.toBytes(5L),
                                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                            .build());
        } else if ("month_soft".equals(str2)) {
            SubscriptionPlan.Builder summary =
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile is the carriers name who this plan belongs to")
                            .setSummary(
                                    "Crazy unlimited bandwidth plan with incredibly long title that"
                                        + " should be cut off to prevent UI from looking terrible");
            DataUnit dataUnit2 = DataUnit.GIBIBYTES;
            arrayList.add(
                    summary.setDataLimit(dataUnit2.toBytes(5L), 2)
                            .setDataUsage(
                                    dataUnit2.toBytes(1L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile, Throttled after limit")
                            .setDataLimit(dataUnit2.toBytes(5L), 2)
                            .setDataUsage(
                                    dataUnit2.toBytes(5L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile, No data connection after limit")
                            .setDataLimit(dataUnit2.toBytes(5L), 0)
                            .setDataUsage(
                                    dataUnit2.toBytes(5L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
        } else if ("month_over".equals(str2)) {
            SubscriptionPlan.Builder title2 =
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile is the carriers name who this plan belongs to");
            DataUnit dataUnit3 = DataUnit.GIBIBYTES;
            arrayList.add(
                    title2.setDataLimit(dataUnit3.toBytes(5L), 2)
                            .setDataUsage(
                                    dataUnit3.toBytes(6L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile, Throttled after limit")
                            .setDataLimit(dataUnit3.toBytes(5L), 2)
                            .setDataUsage(
                                    dataUnit3.toBytes(5L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile, No data connection after limit")
                            .setDataLimit(dataUnit3.toBytes(5L), 0)
                            .setDataUsage(
                                    dataUnit3.toBytes(5L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
        } else if ("month_none".equals(str2)) {
            arrayList.add(
                    SubscriptionPlan.Builder.createRecurringMonthly(
                                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                            .setTitle("G-Mobile")
                            .build());
        } else if ("prepaid".equals(str2)) {
            SubscriptionPlan.Builder title3 =
                    SubscriptionPlan.Builder.createNonrecurring(
                                    ZonedDateTime.now().minusDays(20L),
                                    ZonedDateTime.now().plusDays(10L))
                            .setTitle("G-Mobile");
            DataUnit dataUnit4 = DataUnit.MEBIBYTES;
            arrayList.add(
                    title3.setDataLimit(dataUnit4.toBytes(512L), 0)
                            .setDataUsage(
                                    dataUnit4.toBytes(100L),
                                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                            .build());
        } else if ("prepaid_crazy".equals(str2)) {
            SubscriptionPlan.Builder title4 =
                    SubscriptionPlan.Builder.createNonrecurring(
                                    ZonedDateTime.now().minusDays(20L),
                                    ZonedDateTime.now().plusDays(10L))
                            .setTitle("G-Mobile Anytime");
            DataUnit dataUnit5 = DataUnit.MEBIBYTES;
            arrayList.add(
                    title4.setDataLimit(dataUnit5.toBytes(512L), 0)
                            .setDataUsage(
                                    dataUnit5.toBytes(100L),
                                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                            .build());
            SubscriptionPlan.Builder summary2 =
                    SubscriptionPlan.Builder.createNonrecurring(
                                    ZonedDateTime.now().minusDays(10L),
                                    ZonedDateTime.now().plusDays(20L))
                            .setTitle("G-Mobile Nickel Nights")
                            .setSummary("5¢/GB between 1-5AM");
            DataUnit dataUnit6 = DataUnit.GIBIBYTES;
            arrayList.add(
                    summary2.setDataLimit(dataUnit6.toBytes(5L), 2)
                            .setDataUsage(
                                    dataUnit5.toBytes(15L),
                                    ZonedDateTime.now().minusHours(30L).toInstant().toEpochMilli())
                            .build());
            arrayList.add(
                    SubscriptionPlan.Builder.createNonrecurring(
                                    ZonedDateTime.now().minusDays(10L),
                                    ZonedDateTime.now().plusDays(20L))
                            .setTitle("G-Mobile Bonus 3G")
                            .setSummary("Unlimited 3G data")
                            .setDataLimit(dataUnit6.toBytes(1L), 2)
                            .setDataUsage(
                                    dataUnit5.toBytes(300L),
                                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                            .build());
        } else if ("unlimited".equals(str2)) {
            arrayList.add(
                    SubscriptionPlan.Builder.createNonrecurring(
                                    ZonedDateTime.now().minusDays(20L),
                                    ZonedDateTime.now().plusDays(10L))
                            .setTitle("G-Mobile Awesome")
                            .setDataLimit(Long.MAX_VALUE, 2)
                            .setDataUsage(
                                    DataUnit.MEBIBYTES.toBytes(50L),
                                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                            .build());
        }
        return (SubscriptionPlan[]) arrayList.toArray(new SubscriptionPlan[arrayList.size()]);
    }

    public final String getSubscriptionPlansOwner(int i) {
        String str;
        if (UserHandle.getCallingAppId() != 1000) {
            throw new SecurityException();
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            str = (String) this.mSubscriptionPlansOwner.get(i);
        }
        return str;
    }

    public final long getTetheringWarningBytes(String str) {
        String string = Settings.Global.getString(this.mContext.getContentResolver(), str);
        if (TextUtils.isEmpty(string)) {
            return -1L;
        }
        return Long.parseLong(string);
    }

    public final long getTotalBytes(NetworkTemplate networkTemplate, long j, long j2) {
        if (this.mStatsCallback.mIsAnyCallbackReceived) {
            return this.mDeps.getNetworkTotalBytes(networkTemplate, j, j2);
        }
        return 0L;
    }

    public final int getUidPolicy(int i) {
        int i2;
        getUidPolicy_enforcePermission();
        synchronized (this.mUidRulesFirstLock) {
            i2 = this.mUidPolicy.get(i, 0);
        }
        return i2;
    }

    public NetworkPolicyManager.UidState getUidStateForTest(int i) {
        NetworkPolicyManager.UidState uidState;
        synchronized (this.mUidRulesFirstLock) {
            uidState = (NetworkPolicyManager.UidState) this.mUidState.get(i);
        }
        return uidState;
    }

    public final int[] getUidsWithPolicy(int i) {
        getUidsWithPolicy_enforcePermission();
        int[] iArr = new int[0];
        synchronized (this.mUidRulesFirstLock) {
            for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
                try {
                    int keyAt = this.mUidPolicy.keyAt(i2);
                    int valueAt = this.mUidPolicy.valueAt(i2);
                    if ((i == 0 && valueAt == 0) || (valueAt & i) != 0) {
                        iArr = ArrayUtils.appendInt(iArr, keyAt);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return iArr;
    }

    public long getWarningBytesFromCarrierConfig(PersistableBundle persistableBundle, long j) {
        if (persistableBundle == null) {
            return j;
        }
        long j2 = persistableBundle.getLong("data_warning_threshold_bytes_long");
        if (j2 == -2) {
            return -1L;
        }
        if (j2 == -1) {
            return getPlatformDefaultWarningBytes();
        }
        if (j2 >= 0) {
            return j2;
        }
        Slog.e(
                "NetworkPolicy",
                "Invalid value in CarrierConfigManager.KEY_DATA_WARNING_THRESHOLD_BYTES_LONG;"
                    + " expected a non-negative value but got: "
                        + j2);
        return j;
    }

    public final void handleBlockedReasonsChanged(int i, int i2, int i3) {
        this.mActivityManagerInternal.onUidBlockedReasonsChanged(i, i2);
        this.mHandler.obtainMessage(21, i, i2, Integer.valueOf(i3)).sendToTarget();
    }

    public final void handleDeviceIdleModeDisabledUL() {
        Trace.traceBegin(2097152L, "handleDeviceIdleModeDisabledUL");
        try {
            SparseArray sparseArray = new SparseArray();
            synchronized (this.mUidBlockedState) {
                try {
                    int size = this.mUidBlockedState.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = this.mUidBlockedState.keyAt(i);
                        UidBlockedState uidBlockedState =
                                (UidBlockedState) this.mUidBlockedState.valueAt(i);
                        int i2 = uidBlockedState.blockedReasons;
                        if ((i2 & 2) != 0) {
                            uidBlockedState.blockedReasons = i2 & (-3);
                            int i3 = uidBlockedState.effectiveBlockedReasons;
                            uidBlockedState.updateEffectiveBlockedReasons();
                            if (LOGV) {
                                Log.v(
                                        "NetworkPolicy",
                                        "handleDeviceIdleModeDisabled("
                                                + keyAt
                                                + "); newUidBlockedState="
                                                + uidBlockedState
                                                + ", oldEffectiveBlockedReasons="
                                                + i3);
                            }
                            if (i3 != uidBlockedState.effectiveBlockedReasons) {
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.argi1 = i3;
                                obtain.argi2 = uidBlockedState.effectiveBlockedReasons;
                                obtain.argi3 = uidBlockedState.deriveUidRules();
                                sparseArray.append(keyAt, obtain);
                                this.mActivityManagerInternal.onUidBlockedReasonsChanged(
                                        keyAt, uidBlockedState.effectiveBlockedReasons);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (sparseArray.size() != 0) {
                this.mHandler.obtainMessage(23, sparseArray).sendToTarget();
            }
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void handleNetworkPoliciesUpdateAL(boolean z) {
        if (z) {
            normalizePoliciesNL(getNetworkPolicies(this.mContext.getOpPackageName()));
        }
        updateNetworkEnabledNL();
        updateNetworkRulesNL();
        updateNotificationsNL();
        writePolicyAL();
    }

    public final void handleRestrictedPackagesChangeUL(Set set, Set set2) {
        if (this.mNetworkManagerReady) {
            if (set == null) {
                Iterator it = ((ArraySet) set2).iterator();
                while (it.hasNext()) {
                    updateRulesForDataUsageRestrictionsUL(((Integer) it.next()).intValue());
                }
                return;
            }
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                Integer num = (Integer) it2.next();
                int intValue = num.intValue();
                if (!((ArraySet) set2).contains(num)) {
                    updateRulesForDataUsageRestrictionsUL(intValue);
                }
            }
            Iterator it3 = ((ArraySet) set2).iterator();
            while (it3.hasNext()) {
                Integer num2 = (Integer) it3.next();
                int intValue2 = num2.intValue();
                if (!set.contains(num2)) {
                    updateRulesForDataUsageRestrictionsUL(intValue2);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int handleShellCommand(
            ParcelFileDescriptor parcelFileDescriptor,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3,
            String[] strArr) {
        return new NetworkPolicyManagerShellCommand(this.mContext, this)
                .exec(
                        this,
                        parcelFileDescriptor.getFileDescriptor(),
                        parcelFileDescriptor2.getFileDescriptor(),
                        parcelFileDescriptor3.getFileDescriptor(),
                        strArr);
    }

    public final boolean hasInternetPermissionUL(int i) {
        try {
            if (this.mInternetPermissionMap.get(i)) {
                return true;
            }
            boolean z = this.mIPm.checkUidPermission("android.permission.INTERNET", i) == 0;
            this.mInternetPermissionMap.put(i, z);
            return z;
        } catch (RemoteException unused) {
            return true;
        }
    }

    public final boolean isAllowlistedFromPowerSaveUL(int i, boolean z) {
        int appId = UserHandle.getAppId(i);
        boolean z2 = true;
        boolean z3 =
                this.mPowerSaveTempWhitelistAppIds.get(appId)
                        || this.mPowerSaveWhitelistAppIds.get(appId);
        if (z) {
            return z3;
        }
        if (!z3) {
            if (!this.mPowerSaveWhitelistExceptIdleAppIds.get(UserHandle.getAppId(i))) {
                z2 = false;
            }
        }
        return z2;
    }

    public boolean isRestrictedModeEnabled() {
        boolean z;
        synchronized (this.mUidRulesFirstLock) {
            z = this.mRestrictedNetworkingMode;
        }
        return z;
    }

    public boolean isUidForegroundOnRestrictBackgroundUL(int i) {
        NetworkPolicyManager.UidState uidState =
                (NetworkPolicyManager.UidState) this.mUidState.get(i);
        if (NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)) {
            return true;
        }
        synchronized (this.mUidStateCallbackInfos) {
            try {
                UidStateCallbackInfo uidStateCallbackInfo =
                        (UidStateCallbackInfo) this.mUidStateCallbackInfos.get(i);
                long j = uidState != null ? uidState.procStateSeq : -1L;
                if (uidStateCallbackInfo == null
                        || !uidStateCallbackInfo.isPending
                        || uidStateCallbackInfo.procStateSeq < j) {
                    return false;
                }
                return NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(
                        uidStateCallbackInfo.procState, uidStateCallbackInfo.capability);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUidForegroundOnRestrictPowerUL(int i) {
        NetworkPolicyManager.UidState uidState =
                (NetworkPolicyManager.UidState) this.mUidState.get(i);
        if (NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState)) {
            return true;
        }
        synchronized (this.mUidStateCallbackInfos) {
            try {
                UidStateCallbackInfo uidStateCallbackInfo =
                        (UidStateCallbackInfo) this.mUidStateCallbackInfos.get(i);
                long j = uidState != null ? uidState.procStateSeq : -1L;
                if (uidStateCallbackInfo == null
                        || !uidStateCallbackInfo.isPending
                        || uidStateCallbackInfo.procStateSeq < j) {
                    return false;
                }
                return NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(
                        uidStateCallbackInfo.procState, uidStateCallbackInfo.capability);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUidIdle(int i) {
        return isUidIdle(i, -1);
    }

    public final boolean isUidIdle(int i, int i2) {
        synchronized (this.mUidRulesFirstLock) {
            if (i2 != -1) {
                try {
                    if (ActivityManager.isProcStateConsideredInteraction(i2)) {
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mAppIdleTempWhitelistAppIds.get(i)) {
                return false;
            }
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            int userId = UserHandle.getUserId(i);
            if (packagesForUid == null) {
                return true;
            }
            for (String str : packagesForUid) {
                if (!UsageStatsService.this.mAppStandby.isAppIdleFiltered(
                        str, i, userId, SystemClock.elapsedRealtime())) {
                    return false;
                }
            }
            return true;
        }
    }

    public final boolean isUidNetworkingBlocked(int i, boolean z) {
        int i2;
        long time = this.mStatLogger.getTime();
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.OBSERVE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (this.mUidBlockedState) {
            UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
            i2 = uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons;
            if (!z) {
                i2 &= GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            synchronized (mFirewallPoliciesLock) {
                try {
                    if (this.mActiveNetworkType == 1 && (mFirewallRules.get(i, 0) & 2) != 0) {
                        i2 &= 256;
                    }
                    if (this.mActiveNetworkType == 0 && (mFirewallRules.get(i, 0) & 1) != 0) {
                        i2 &= 512;
                    }
                } finally {
                }
            }
            this.mLogger.networkBlocked(i, uidBlockedState);
        }
        this.mStatLogger.logDurationStat(1, time);
        return i2 != 0;
    }

    public final boolean isUidRestrictedOnMeteredNetworks(int i) {
        boolean z;
        isUidRestrictedOnMeteredNetworks_enforcePermission();
        synchronized (this.mUidBlockedState) {
            UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
            z =
                    ((uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons)
                                    & (-65536))
                            != 0;
        }
        return z;
    }

    public final boolean isUidValidForAllowlistRulesUL(int i) {
        return UserHandle.isApp(i) && hasInternetPermissionUL(i);
    }

    public final boolean isUidValidForDenylistRulesUL(int i) {
        return i == 1013 || i == 1019 || isUidValidForAllowlistRulesUL(i);
    }

    public final boolean maybeUpdateCarrierPolicyCycleAL(int i, String str) {
        if (LOGV) {
            Slog.v("NetworkPolicy", "maybeUpdateCarrierPolicyCycleAL()");
        }
        boolean z = false;
        NetworkIdentity build =
                new NetworkIdentity.Builder()
                        .setType(0)
                        .setSubscriberId(str)
                        .setMetered(true)
                        .setDefaultNetwork(true)
                        .setSubId(i)
                        .build();
        for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
            if (((NetworkTemplate) this.mNetworkPolicy.keyAt(size)).matches(build)) {
                z =
                        updateDefaultCarrierPolicyAL(
                                        i, (NetworkPolicy) this.mNetworkPolicy.valueAt(size))
                                | z;
            }
        }
        return z;
    }

    public final void normalizePoliciesNL(NetworkPolicy[] networkPolicyArr) {
        this.mNetworkPolicy.clear();
        for (NetworkPolicy networkPolicy : networkPolicyArr) {
            if (networkPolicy != null) {
                NetworkTemplate normalizeTemplate =
                        normalizeTemplate(networkPolicy.template, this.mMergedSubscriberIds);
                networkPolicy.template = normalizeTemplate;
                NetworkPolicy networkPolicy2 =
                        (NetworkPolicy) this.mNetworkPolicy.get(normalizeTemplate);
                if (networkPolicy2 == null || networkPolicy2.compareTo(networkPolicy) > 0) {
                    if (networkPolicy2 != null) {
                        Slog.d(
                                "NetworkPolicy",
                                "Normalization replaced "
                                        + networkPolicy2
                                        + " with "
                                        + networkPolicy);
                    }
                    this.mNetworkPolicy.put(networkPolicy.template, networkPolicy);
                }
            }
        }
    }

    public final void notifyStatsProviderWarningOrLimitReached() {
        enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                if (this.mSystemReady) {
                    this.mHandler.obtainMessage(20).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void performSnooze(NetworkTemplate networkTemplate, int i) {
        long millis = this.mClock.millis();
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                try {
                    NetworkPolicy networkPolicy =
                            (NetworkPolicy) this.mNetworkPolicy.get(networkTemplate);
                    if (networkPolicy == null) {
                        throw new IllegalArgumentException(
                                "unable to find policy for " + networkTemplate);
                    }
                    if (i == 34) {
                        networkPolicy.lastWarningSnooze = millis;
                    } else if (i == 35) {
                        networkPolicy.lastLimitSnooze = millis;
                    } else {
                        if (i != 45) {
                            throw new IllegalArgumentException("unexpected type");
                        }
                        networkPolicy.lastRapidSnooze = millis;
                    }
                    handleNetworkPoliciesUpdateAL(true);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void readFirewallPolicyAL() {
        if (LOGV) {
            Slog.v("NetworkPolicy", "readFirewallPolicyAL");
        }
        mFirewallRules.clear();
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mFirewallPolicyFile.openRead();
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                while (true) {
                    int next = resolvePullParser.next();
                    if (next == 1) {
                        break;
                    }
                    String name = resolvePullParser.getName();
                    if (next == 2 && "firewall-policy".equals(name)) {
                        int readIntAttribute = XmlUtils.readIntAttribute(resolvePullParser, "uid");
                        int readIntAttribute2 =
                                XmlUtils.readIntAttribute(resolvePullParser, "policy");
                        if (UserHandle.isApp(readIntAttribute)) {
                            setFirewallPolicyNL(readIntAttribute, readIntAttribute2, false);
                            applyFirewallRules(readIntAttribute, readIntAttribute2);
                        } else {
                            Slog.w(
                                    "NetworkPolicy",
                                    "unable to apply policy to UID "
                                            + readIntAttribute
                                            + "; ignoring");
                        }
                    }
                }
            } catch (FileNotFoundException unused) {
                Slog.w("NetworkPolicy", "miss firewall policy file");
            } catch (Exception e) {
                Log.wtf("NetworkPolicy", "problem reading firewall policy", e);
            }
            IoUtils.closeQuietly(fileInputStream);
        } catch (Throwable th) {
            IoUtils.closeQuietly(fileInputStream);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x012c A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0140 A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x014e A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0164 A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0171 A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0182 A[Catch: all -> 0x0063, Exception -> 0x0067, FileNotFoundException -> 0x006b, TryCatch #4 {FileNotFoundException -> 0x006b, Exception -> 0x0067, all -> 0x0063, blocks: (B:9:0x002a, B:10:0x0035, B:12:0x003b, B:15:0x0046, B:17:0x004f, B:19:0x0058, B:22:0x006f, B:26:0x0074, B:28:0x007d, B:30:0x008f, B:33:0x009c, B:36:0x00bd, B:37:0x0102, B:39:0x0114, B:43:0x012c, B:45:0x0140, B:48:0x014e, B:49:0x0159, B:51:0x0164, B:53:0x0171, B:54:0x0178, B:56:0x0182, B:67:0x0120, B:69:0x00e4, B:71:0x00ed, B:72:0x00f8, B:75:0x00ad, B:79:0x0192, B:82:0x01a7, B:84:0x01b5, B:86:0x01ba, B:88:0x01d1, B:90:0x01d9, B:92:0x01ee, B:94:0x01f2, B:96:0x0208, B:100:0x0210, B:103:0x021b, B:105:0x0224, B:108:0x022f, B:114:0x023d, B:121:0x0248, B:123:0x024f, B:125:0x025e, B:128:0x027f, B:130:0x0285, B:132:0x028b, B:133:0x02ac, B:135:0x02b0), top: B:8:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0244 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0136  */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPolicyAL() {
        /*
            Method dump skipped, instructions count: 763
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyManagerService.readPolicyAL():void");
    }

    public final void registerListener(INetworkPolicyListener iNetworkPolicyListener) {
        Objects.requireNonNull(iNetworkPolicyListener);
        enforceAnyPermissionOf(
                "android.permission.CONNECTIVITY_INTERNAL",
                "android.permission.OBSERVE_NETWORK_POLICY");
        this.mListeners.register(iNetworkPolicyListener);
    }

    public final void removeFirewallRules(int i, int i2) {
        if ((i2 & 1) != 0) {
            try {
                this.mNetworkManager.setFirewallRuleMobileData(i, true);
                Slog.v("NetworkPolicy", "remove FIREWALL_POLICY_REJECT_MOBILE_DATA for UID:" + i);
            } catch (RemoteException unused) {
                return;
            }
        }
        if ((i2 & 2) != 0) {
            this.mNetworkManager.setFirewallRuleWifi(i, true);
            Slog.v("NetworkPolicy", "remove FIREWALL_POLICY_REJECT_WIFI for UID:" + i);
        }
    }

    public final void removeUidPolicy(int i, int i2) {
        removeUidPolicy_enforcePermission();
        if (!UserHandle.isApp(i)) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "cannot apply policy to UID "));
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                int i3 = this.mUidPolicy.get(i, 0);
                int i4 = (~i2) & i3;
                if (i3 != i4) {
                    setUidPolicyUncheckedUL(i, i3, i4);
                    this.mLogger.uidPolicyChanged(i, i3, i4);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean removeUidStateUL(int i) {
        int indexOfKey = this.mUidState.indexOfKey(i);
        if (indexOfKey >= 0) {
            NetworkPolicyManager.UidState uidState =
                    (NetworkPolicyManager.UidState) this.mUidState.valueAt(indexOfKey);
            this.mUidState.removeAt(indexOfKey);
            if (uidState != null) {
                if (NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)
                        != NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(
                                (NetworkPolicyManager.UidState) null)) {
                    updateRulesForDataUsageRestrictionsUL(i);
                }
                if (this.mDeviceIdleMode && this.mDeviceIdleMode) {
                    if (isAllowlistedFromPowerSaveUL(i, true)
                            || isUidForegroundOnRestrictPowerUL(i)) {
                        setUidFirewallRuleUL(1, i, 1);
                    } else {
                        setUidFirewallRuleUL(1, i, 0);
                    }
                }
                if (this.mRestrictPower && this.mRestrictPower) {
                    if (isAllowlistedFromPowerSaveUL(i, false)
                            || isUidForegroundOnRestrictPowerUL(i)) {
                        setUidFirewallRuleUL(3, i, 1);
                    } else {
                        setUidFirewallRuleUL(3, i, 0);
                    }
                }
                if (this.mBackgroundNetworkRestricted) {
                    this.mBackgroundTransitioningUids.delete(i);
                    updateRuleForBackgroundUL(i);
                }
                updateRulesForPowerRestrictionsUL(i, -1);
                if (this.mLowPowerStandbyActive) {
                    updateRuleForLowPowerStandbyUL(i);
                }
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x002e A[Catch: all -> 0x000f, TryCatch #1 {all -> 0x000f, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x0025, B:12:0x003c, B:56:0x002e, B:57:0x0012), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean removeUserStateUL(int r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            com.android.server.net.NetworkPolicyLogger r0 = r7.mLogger
            java.lang.Object r1 = r0.mLock
            monitor-enter(r1)
            boolean r2 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Lf
            if (r2 != 0) goto L12
            int r2 = r0.mDebugUid     // Catch: java.lang.Throwable -> Lf
            r3 = -1
            if (r2 == r3) goto L25
            goto L12
        Lf:
            r7 = move-exception
            goto La3
        L12:
            java.lang.String r2 = "NetworkPolicy"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lf
            java.lang.String r4 = "Remove state for u"
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Lf
            r3.append(r8)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lf
            android.util.Slog.d(r2, r3)     // Catch: java.lang.Throwable -> Lf
        L25:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r0 = r0.mEventsBuffer     // Catch: java.lang.Throwable -> Lf
            com.android.server.net.NetworkPolicyLogger$Data r0 = r0.getNextSlot()     // Catch: java.lang.Throwable -> Lf
            if (r0 != 0) goto L2e
            goto L3c
        L2e:
            r2 = 0
            r0.sfield1 = r2     // Catch: java.lang.Throwable -> Lf
            r2 = 5
            r0.type = r2     // Catch: java.lang.Throwable -> Lf
            r0.ifield1 = r8     // Catch: java.lang.Throwable -> Lf
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lf
            r0.timeStamp = r2     // Catch: java.lang.Throwable -> Lf
        L3c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            android.util.SparseBooleanArray r0 = r7.mRestrictBackgroundAllowlistRevokedUids
            int r0 = r0.size()
            r1 = 1
            int r0 = r0 - r1
            r2 = 0
            r3 = r2
        L47:
            if (r0 < 0) goto L5e
            android.util.SparseBooleanArray r4 = r7.mRestrictBackgroundAllowlistRevokedUids
            int r4 = r4.keyAt(r0)
            int r4 = android.os.UserHandle.getUserId(r4)
            if (r4 != r8) goto L5b
            android.util.SparseBooleanArray r3 = r7.mRestrictBackgroundAllowlistRevokedUids
            r3.removeAt(r0)
            r3 = r1
        L5b:
            int r0 = r0 + (-1)
            goto L47
        L5e:
            int[] r0 = new int[r2]
            r4 = r2
        L61:
            android.util.SparseIntArray r5 = r7.mUidPolicy
            int r5 = r5.size()
            if (r4 >= r5) goto L7c
            android.util.SparseIntArray r5 = r7.mUidPolicy
            int r5 = r5.keyAt(r4)
            int r6 = android.os.UserHandle.getUserId(r5)
            if (r6 != r8) goto L79
            int[] r0 = com.android.internal.util.ArrayUtils.appendInt(r0, r5)
        L79:
            int r4 = r4 + 1
            goto L61
        L7c:
            int r8 = r0.length
            if (r8 <= 0) goto L8d
            int r8 = r0.length
        L80:
            if (r2 >= r8) goto L8c
            r3 = r0[r2]
            android.util.SparseIntArray r4 = r7.mUidPolicy
            r4.delete(r3)
            int r2 = r2 + 1
            goto L80
        L8c:
            r3 = r1
        L8d:
            java.lang.Object r8 = r7.mNetworkPoliciesSecondLock
            monitor-enter(r8)
            if (r10 == 0) goto L98
            r7.updateRulesForGlobalChangeAL(r1)     // Catch: java.lang.Throwable -> L96
            goto L98
        L96:
            r7 = move-exception
            goto La1
        L98:
            if (r9 == 0) goto L9f
            if (r3 == 0) goto L9f
            r7.writePolicyAL()     // Catch: java.lang.Throwable -> L96
        L9f:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L96
            return r3
        La1:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L96
            throw r7
        La3:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyManagerService.removeUserStateUL(int,"
                    + " boolean, boolean):boolean");
    }

    public final void resetUidFirewallRules(int i) {
        try {
            this.mNetworkManager.setFirewallUidRule(1, i, 0);
            this.mNetworkManager.setFirewallUidRule(2, i, 0);
            this.mNetworkManager.setFirewallUidRule(3, i, 0);
            this.mNetworkManager.setFirewallUidRule(4, i, 0);
            this.mNetworkManager.setFirewallUidRule(5, i, 0);
            this.mNetworkManager.setFirewallUidRule(6, i, 0);
            if (this.mUseMeteredFirewallChains) {
                this.mNetworkManager.setFirewallUidRule(12, i, 0);
                this.mNetworkManager.setFirewallUidRule(11, i, 0);
                this.mNetworkManager.setFirewallUidRule(10, i, 0);
            } else {
                this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, false);
                this.mLogger.meteredAllowlistChanged(i, false);
                this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, false);
                this.mLogger.meteredDenylistChanged(i, false);
            }
            this.mNetworkManager.setFirewallUidRule(7, i, 0);
        } catch (RemoteException unused) {
        } catch (IllegalStateException e) {
            Log.wtf("NetworkPolicy", "problem resetting firewall uid rules for " + i, e);
        }
        if (Process.isApplicationUid(i)) {
            resetUidFirewallRules(Process.toSdkSandboxUid(i));
        }
    }

    public void setAppIdleWhitelist(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (this.mUidRulesFirstLock) {
            try {
                if (this.mAppIdleTempWhitelistAppIds.get(i) == z) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mLogger.appIdleWlChanged(i, z);
                    if (z) {
                        this.mAppIdleTempWhitelistAppIds.put(i, true);
                    } else {
                        this.mAppIdleTempWhitelistAppIds.delete(i);
                    }
                    updateRuleForAppIdleUL(i, -1);
                    updateRulesForPowerRestrictionsUL(i, -1);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void setContentIntent(Notification.Builder builder, Intent intent) {
        if (UserManager.isHeadlessSystemUserMode()) {
            builder.setContentIntent(
                    PendingIntent.getActivityAsUser(
                            this.mContext, 0, intent, 201326592, null, UserHandle.CURRENT));
        } else {
            builder.setContentIntent(
                    PendingIntent.getActivity(this.mContext, 0, intent, 201326592));
        }
    }

    public final void setDeviceIdleMode(boolean z) {
        setDeviceIdleMode_enforcePermission();
        Trace.traceBegin(2097152L, "setDeviceIdleMode");
        try {
            synchronized (this.mUidRulesFirstLock) {
                if (this.mDeviceIdleMode == z) {
                    Trace.traceEnd(2097152L);
                    return;
                }
                this.mDeviceIdleMode = z;
                this.mLogger.deviceIdleModeEnabled(z);
                if (this.mSystemReady) {
                    Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
                    try {
                        updateRulesForDeviceIdleUL();
                        if (z) {
                            forEachUid(
                                    "updateRulesForRestrictPower",
                                    new NetworkPolicyManagerService$$ExternalSyntheticLambda0(
                                            2, this));
                        } else {
                            handleDeviceIdleModeDisabledUL();
                        }
                        Trace.traceEnd(2097152L);
                    } finally {
                    }
                }
                if (z) {
                    EventLog.writeEvent(34004, "net");
                } else {
                    EventLog.writeEvent(34007, "net");
                }
            }
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void setFirewallPolicyNL(int i, int i2, boolean z) {
        if (i2 == 0) {
            mFirewallRules.delete(i);
        } else {
            mFirewallRules.put(i, i2);
        }
        if (z) {
            writeFirewallPolicyAL();
        }
    }

    public final void setFirewallRuleMobileData(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (mFirewallPoliciesLock) {
            try {
                int i2 = mFirewallRules.get(i, 0);
                Slog.v(
                        "NetworkPolicy",
                        "setFirewallRuleMobileData UID:" + i + " allow:" + z + " policy:" + i2);
                if (!z) {
                    this.mToastCheckedUidMap.remove(Integer.valueOf(i));
                    if ((i2 & 1) == 0) {
                        setFirewallPolicyNL(i, i2 | 1, true);
                        applyFirewallRules(i, 1);
                    }
                } else if ((i2 & 1) != 0) {
                    setFirewallPolicyNL(i, i2 & (-2), true);
                    removeFirewallRules(i, 1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setFirewallRuleMobileDataMap(Map map) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        for (Map.Entry entry : map.entrySet()) {
            setFirewallRuleMobileData(
                    ((Integer) entry.getKey()).intValue(),
                    ((Boolean) entry.getValue()).booleanValue());
        }
    }

    public final void setFirewallRuleWifi(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        synchronized (mFirewallPoliciesLock) {
            try {
                int i2 = mFirewallRules.get(i, 0);
                Slog.v(
                        "NetworkPolicy",
                        "setFirewallRuleWifi UID:" + i + " allow:" + z + " policy:" + i2);
                if (!z) {
                    this.mToastCheckedUidMap.remove(Integer.valueOf(i));
                    if ((i2 & 2) == 0) {
                        setFirewallPolicyNL(i, i2 | 2, true);
                        applyFirewallRules(i, 2);
                    }
                } else if ((i2 & 2) != 0) {
                    setFirewallPolicyNL(i, i2 & (-3), true);
                    removeFirewallRules(i, 2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setFirewallRuleWifiMap(Map map) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        for (Map.Entry entry : map.entrySet()) {
            setFirewallRuleWifi(
                    ((Integer) entry.getKey()).intValue(),
                    ((Boolean) entry.getValue()).booleanValue());
        }
    }

    public final void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) {
        setNetworkPolicies_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mUidRulesFirstLock) {
                synchronized (this.mNetworkPoliciesSecondLock) {
                    normalizePoliciesNL(networkPolicyArr);
                    handleNetworkPoliciesUpdateAL(false);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRestrictBackground(boolean z) {
        Trace.traceBegin(2097152L, "setRestrictBackground");
        try {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mUidRulesFirstLock) {
                    setRestrictBackgroundUL("uid:" + callingUid, z);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void setRestrictBackgroundUL(String str, boolean z) {
        Trace.traceBegin(2097152L, "setRestrictBackgroundUL");
        try {
            if (z == this.mRestrictBackground) {
                Slog.w("NetworkPolicy", "setRestrictBackgroundUL: already " + z);
                return;
            }
            Slog.d("NetworkPolicy", "setRestrictBackgroundUL(): " + z + "; reason: " + str);
            boolean z2 = this.mRestrictBackground;
            this.mRestrictBackground = z;
            updateRulesForRestrictBackgroundUL();
            try {
                if (!this.mNetworkManager.setDataSaverModeEnabled(this.mRestrictBackground)) {
                    Slog.e(
                            "NetworkPolicy",
                            "Could not change Data Saver Mode on NMS to "
                                    + this.mRestrictBackground);
                    this.mRestrictBackground = z2;
                    return;
                }
            } catch (RemoteException unused) {
            }
            this.mHandler.removeMessages(6);
            this.mHandler.obtainMessage(6, this.mRestrictBackground ? 1 : 0, 0).sendToTarget();
            this.mLogger.restrictBackgroundChanged(z2, this.mRestrictBackground);
            if (this.mRestrictBackgroundLowPowerMode) {
                this.mRestrictBackgroundChangedInBsm = true;
            }
            synchronized (this.mNetworkPoliciesSecondLock) {
                updateNotificationsNL();
                writePolicyAL();
            }
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void setSubscriptionOverride(
            int i, int i2, int i3, int[] iArr, long j, String str) {
        enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
        ArraySet arraySet = new ArraySet();
        addAll(arraySet, TelephonyManager.getAllNetworkTypes());
        IntArray intArray = new IntArray();
        for (int i4 : iArr) {
            if (arraySet.contains(Integer.valueOf(i4))) {
                intArray.add(i4);
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i4,
                        "setSubscriptionOverride removing invalid network type: ",
                        "NetworkPolicy");
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                SubscriptionPlan primarySubscriptionPlanLocked =
                        getPrimarySubscriptionPlanLocked(i);
                if (i2 != 1
                        && (primarySubscriptionPlanLocked == null
                                || primarySubscriptionPlanLocked.getDataLimitBehavior() == -1)) {
                    throw new IllegalStateException(
                            "Must provide valid SubscriptionPlan to enable overriding");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (Settings.Global.getInt(
                                this.mContext.getContentResolver(), "netpolicy_override_enabled", 1)
                        == 0
                && i3 != 0) {
            return;
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = Integer.valueOf(i2);
        obtain.arg3 = Integer.valueOf(i3);
        obtain.arg4 = intArray.toArray();
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(16, obtain));
        if (j > 0) {
            obtain.arg3 = 0;
            Handler handler2 = this.mHandler;
            handler2.sendMessageDelayed(handler2.obtainMessage(16, obtain), j);
        }
    }

    public final void setSubscriptionPlans(
            int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) {
        enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
        if (subscriptionPlanArr.length == 0) {
            Log.d(
                    "NetworkPolicy",
                    "Received empty plans list. Clearing existing SubscriptionPlans.");
        } else {
            int[] allNetworkTypes = TelephonyManager.getAllNetworkTypes();
            ArraySet arraySet = new ArraySet();
            addAll(arraySet, allNetworkTypes);
            ArraySet arraySet2 = new ArraySet();
            boolean z = false;
            for (SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
                int[] networkTypes = subscriptionPlan.getNetworkTypes();
                ArraySet arraySet3 = new ArraySet();
                for (int i2 = 0; i2 < networkTypes.length; i2++) {
                    if (!arraySet.contains(Integer.valueOf(networkTypes[i2]))) {
                        throw new IllegalArgumentException(
                                "Invalid network type: " + networkTypes[i2]);
                    }
                    if (!arraySet3.add(Integer.valueOf(networkTypes[i2]))) {
                        throw new IllegalArgumentException(
                                "Subscription plan contains duplicate network types.");
                    }
                }
                if (networkTypes.length == allNetworkTypes.length) {
                    z = true;
                } else if (!addAll(arraySet2, networkTypes)) {
                    throw new IllegalArgumentException(
                            "Multiple subscription plans defined for a single network type.");
                }
            }
            if (!z) {
                throw new IllegalArgumentException(
                        "No generic subscription plan that applies to all network types.");
            }
        }
        for (SubscriptionPlan subscriptionPlan2 : subscriptionPlanArr) {
            Objects.requireNonNull(subscriptionPlan2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            setSubscriptionPlansInternal(i, subscriptionPlanArr, j, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSubscriptionPlansInternal(
            int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) {
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                try {
                    this.mSubscriptionPlans.put(i, subscriptionPlanArr);
                    this.mSubscriptionPlansOwner.put(i, str);
                    String str2 = (String) this.mSubIdToSubscriberId.get(i, null);
                    if (str2 != null) {
                        ensureActiveCarrierPolicyAL(i, str2);
                        maybeUpdateCarrierPolicyCycleAL(i, str2);
                    } else {
                        Slog.wtf("NetworkPolicy", "Missing subscriberId for subId " + i);
                    }
                    handleNetworkPoliciesUpdateAL(true);
                    Intent intent =
                            new Intent("android.telephony.action.SUBSCRIPTION_PLANS_CHANGED");
                    intent.addFlags(1073741824);
                    intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
                    this.mContext.sendBroadcast(
                            intent, "android.permission.MANAGE_SUBSCRIPTION_PLANS");
                    Handler handler = this.mHandler;
                    handler.sendMessage(handler.obtainMessage(19, i, 0, subscriptionPlanArr));
                    int i2 = this.mSetSubscriptionPlansIdCounter;
                    this.mSetSubscriptionPlansIdCounter = i2 + 1;
                    this.mSetSubscriptionPlansIds.put(i, i2);
                    if (j > 0) {
                        Handler handler2 = this.mHandler;
                        handler2.sendMessageDelayed(handler2.obtainMessage(22, i, i2, str), j);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setUidFirewallRuleUL(int i, int i2, int i3) {
        if (Trace.isTagEnabled(2097152L)) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "setUidFirewallRuleUL: ", "/", "/");
            m.append(i3);
            Trace.traceBegin(2097152L, m.toString());
        }
        try {
            if (i == 1) {
                this.mUidFirewallDozableRules.put(i2, i3);
            } else if (i == 2) {
                this.mUidFirewallStandbyRules.put(i2, i3);
            } else if (i == 3) {
                this.mUidFirewallPowerSaveRules.put(i2, i3);
            } else if (i == 4) {
                this.mUidFirewallRestrictedModeRules.put(i2, i3);
            } else if (i == 5) {
                this.mUidFirewallLowPowerStandbyModeRules.put(i2, i3);
            } else if (i == 6) {
                this.mUidFirewallBackgroundRules.put(i2, i3);
            } else if (i == 7) {
                this.mUidFirewallOemDenyRules.put(i2, i3);
            }
            try {
                this.mNetworkManager.setFirewallUidRule(i, i2, i3);
                this.mLogger.uidFirewallRuleChanged(i, i2, i3);
                if (Process.isApplicationUid(i2)) {
                    int sdkSandboxUid = Process.toSdkSandboxUid(i2);
                    this.mNetworkManager.setFirewallUidRule(i, sdkSandboxUid, i3);
                    this.mLogger.uidFirewallRuleChanged(i, sdkSandboxUid, i3);
                }
            } catch (RemoteException unused) {
            } catch (IllegalStateException e) {
                Log.wtf("NetworkPolicy", "problem setting firewall uid rules", e);
            }
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            Trace.traceEnd(2097152L);
            throw th;
        }
    }

    public final void setUidFirewallRulesUL(int i, SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseIntArray.keyAt(i2);
            int valueAt = sparseIntArray.valueAt(i2);
            if (Process.isApplicationUid(keyAt)) {
                sparseIntArray2.put(Process.toSdkSandboxUid(keyAt), valueAt);
            }
        }
        for (int i3 = 0; i3 < sparseIntArray2.size(); i3++) {
            sparseIntArray.put(sparseIntArray2.keyAt(i3), sparseIntArray2.valueAt(i3));
        }
        try {
            int size2 = sparseIntArray.size();
            int[] iArr = new int[size2];
            int[] iArr2 = new int[size2];
            for (int i4 = size2 - 1; i4 >= 0; i4--) {
                iArr[i4] = sparseIntArray.keyAt(i4);
                iArr2[i4] = sparseIntArray.valueAt(i4);
            }
            this.mNetworkManager.setFirewallUidRules(i, iArr, iArr2);
            this.mLogger.firewallRulesChanged(i, iArr, iArr2);
        } catch (RemoteException unused) {
        } catch (IllegalStateException e) {
            Log.wtf("NetworkPolicy", "problem setting firewall uid rules", e);
        }
    }

    public final void setUidFirewallRulesUL(int i, SparseIntArray sparseIntArray, int i2) {
        if (sparseIntArray != null) {
            setUidFirewallRulesUL(i, sparseIntArray);
        }
        if (i2 != 0) {
            enableFirewallChainUL(i, i2 == 1);
        }
    }

    public final void setUidPolicy(int i, int i2) {
        setUidPolicy_enforcePermission();
        if (!UserHandle.isApp(i)) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "cannot apply policy to UID "));
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int i3 = this.mUidPolicy.get(i, 0);
                    if (i3 != i2) {
                        setUidPolicyUncheckedUL(i, i3, i2);
                        this.mLogger.uidPolicyChanged(i, i3, i2);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUidPolicyUncheckedUL(int i, int i2) {
        if (i2 == 0) {
            this.mUidPolicy.delete(i);
        } else {
            this.mUidPolicy.put(i, i2);
        }
        updateRulesForDataUsageRestrictionsUL(i);
    }

    public final void setUidPolicyUncheckedUL(int i, int i2, int i3) {
        setUidPolicyUncheckedUL(i, i3);
        boolean z = false;
        if (isUidValidForAllowlistRulesUL(i)) {
            boolean z2 = i2 == 1;
            boolean z3 = i3 == 1;
            boolean z4 = i2 == 4;
            boolean z5 = i3 == 4;
            boolean z6 = z2 || (this.mRestrictBackground && !z4);
            boolean z7 = z3 || (this.mRestrictBackground && !z5);
            if (z4
                    && ((!z5 || z3)
                            && this.mDefaultRestrictBackgroundAllowlistUids.get(i)
                            && !this.mRestrictBackgroundAllowlistRevokedUids.get(i))) {
                if (LOGD) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i,
                            "Adding uid ",
                            " to revoked restrict background allowlist",
                            "NetworkPolicy");
                }
                this.mRestrictBackgroundAllowlistRevokedUids.append(i, true);
            }
            if (z6 != z7) {
                z = true;
            }
        }
        this.mHandler.obtainMessage(13, i, i3, Boolean.valueOf(z)).sendToTarget();
        synchronized (this.mNetworkPoliciesSecondLock) {
            writePolicyAL();
        }
    }

    public final void setWifiMeteredOverride(String str, int i) {
        setWifiMeteredOverride_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WifiManager wifiManager =
                    (WifiManager) this.mContext.getSystemService(WifiManager.class);
            for (WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                if (Objects.equals(NetworkPolicyManager.resolveNetworkId(wifiConfiguration), str)) {
                    wifiConfiguration.meteredOverride = i;
                    wifiManager.updateNetwork(wifiConfiguration);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void snoozeLimit(NetworkTemplate networkTemplate) {
        snoozeLimit_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            performSnooze(networkTemplate, 35);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void systemReady(CountDownLatch countDownLatch) {
        try {
            if (!countDownLatch.await(30L, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Service NetworkPolicy init timeout");
            }
            this.mMultipathPolicyTracker.start();
            if (isKorOperator()) {
                ((TetheringManager) this.mContext.getSystemService(TetheringManager.class))
                        .registerTetheringEventCallback(
                                new Executor() { // from class:
                                                 // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda6
                                    @Override // java.util.concurrent.Executor
                                    public final void execute(Runnable runnable) {
                                        NetworkPolicyManagerService.this.mHandler.post(runnable);
                                    }
                                },
                                this.mTetherListener);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Service NetworkPolicy init interrupted", e);
        }
    }

    public final void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) {
        Objects.requireNonNull(iNetworkPolicyListener);
        enforceAnyPermissionOf(
                "android.permission.CONNECTIVITY_INTERNAL",
                "android.permission.OBSERVE_NETWORK_POLICY");
        this.mListeners.unregister(iNetworkPolicyListener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001e, code lost:

       if (r7.mIPm.checkUidPermission("android.permission.MAINLINE_NETWORK_STACK", r8) == 0) goto L9;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0026 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int updateBlockedReasonsForRestrictedModeUL(int r8) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            android.content.pm.IPackageManager r2 = r7.mIPm     // Catch: android.os.RemoteException -> L22
            java.lang.String r3 = "android.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS"
            int r2 = r2.checkUidPermission(r3, r8)     // Catch: android.os.RemoteException -> L22
            if (r2 == 0) goto L20
            android.content.pm.IPackageManager r2 = r7.mIPm     // Catch: android.os.RemoteException -> L22
            java.lang.String r3 = "android.permission.NETWORK_STACK"
            int r2 = r2.checkUidPermission(r3, r8)     // Catch: android.os.RemoteException -> L22
            if (r2 == 0) goto L20
            android.content.pm.IPackageManager r2 = r7.mIPm     // Catch: android.os.RemoteException -> L22
            java.lang.String r3 = "android.permission.MAINLINE_NETWORK_STACK"
            int r2 = r2.checkUidPermission(r3, r8)     // Catch: android.os.RemoteException -> L22
            if (r2 != 0) goto L22
        L20:
            r2 = r0
            goto L23
        L22:
            r2 = r1
        L23:
            android.util.SparseArray r3 = r7.mUidBlockedState
            monitor-enter(r3)
            android.util.SparseArray r4 = r7.mUidBlockedState     // Catch: java.lang.Throwable -> L39
            com.android.server.net.NetworkPolicyManagerService$UidBlockedState r4 = getOrCreateUidBlockedStateForUid(r8, r4)     // Catch: java.lang.Throwable -> L39
            int r5 = r4.effectiveBlockedReasons     // Catch: java.lang.Throwable -> L39
            boolean r6 = r7.mRestrictedNetworkingMode     // Catch: java.lang.Throwable -> L39
            if (r6 == 0) goto L3b
            int r6 = r4.blockedReasons     // Catch: java.lang.Throwable -> L39
            r6 = r6 | 8
            r4.blockedReasons = r6     // Catch: java.lang.Throwable -> L39
            goto L41
        L39:
            r7 = move-exception
            goto L6c
        L3b:
            int r6 = r4.blockedReasons     // Catch: java.lang.Throwable -> L39
            r6 = r6 & (-9)
            r4.blockedReasons = r6     // Catch: java.lang.Throwable -> L39
        L41:
            if (r2 == 0) goto L4a
            int r2 = r4.allowedReasons     // Catch: java.lang.Throwable -> L39
            r2 = r2 | 16
            r4.allowedReasons = r2     // Catch: java.lang.Throwable -> L39
            goto L50
        L4a:
            int r2 = r4.allowedReasons     // Catch: java.lang.Throwable -> L39
            r2 = r2 & (-17)
            r4.allowedReasons = r2     // Catch: java.lang.Throwable -> L39
        L50:
            r4.updateEffectiveBlockedReasons()     // Catch: java.lang.Throwable -> L39
            int r2 = r4.effectiveBlockedReasons     // Catch: java.lang.Throwable -> L39
            if (r5 != r2) goto L58
            goto L5c
        L58:
            int r1 = r4.deriveUidRules()     // Catch: java.lang.Throwable -> L39
        L5c:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L39
            if (r5 == r2) goto L6b
            r7.handleBlockedReasonsChanged(r8, r2, r5)
            android.os.Handler r7 = r7.mHandler
            android.os.Message r7 = r7.obtainMessage(r0, r8, r1)
            r7.sendToTarget()
        L6b:
            return r2
        L6c:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L39
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyManagerService.updateBlockedReasonsForRestrictedModeUL(int):int");
    }

    public final boolean updateDefaultCarrierPolicyAL(int i, NetworkPolicy networkPolicy) {
        if (!networkPolicy.inferred) {
            if (LOGD) {
                Slog.d("NetworkPolicy", "Ignoring user-defined policy " + networkPolicy);
            }
            return false;
        }
        NetworkPolicy networkPolicy2 =
                new NetworkPolicy(
                        networkPolicy.template,
                        networkPolicy.cycleRule,
                        networkPolicy.warningBytes,
                        networkPolicy.limitBytes,
                        networkPolicy.lastWarningSnooze,
                        networkPolicy.lastLimitSnooze,
                        networkPolicy.metered,
                        networkPolicy.inferred);
        SubscriptionPlan[] subscriptionPlanArr =
                (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
        if (ArrayUtils.isEmpty(subscriptionPlanArr)) {
            PersistableBundle persistableBundle =
                    (PersistableBundle) this.mSubIdToCarrierConfig.get(i);
            networkPolicy.cycleRule =
                    NetworkPolicy.buildRule(
                            getCycleDayFromCarrierConfig(
                                    persistableBundle,
                                    networkPolicy.cycleRule.isMonthly()
                                            ? networkPolicy.cycleRule.start.getDayOfMonth()
                                            : -1),
                            ZoneId.systemDefault());
            networkPolicy.warningBytes =
                    getWarningBytesFromCarrierConfig(persistableBundle, networkPolicy.warningBytes);
            networkPolicy.limitBytes =
                    getLimitBytesFromCarrierConfig(persistableBundle, networkPolicy.limitBytes);
        } else {
            PersistableBundle persistableBundle2 =
                    (PersistableBundle) this.mSubIdToCarrierConfig.get(i);
            SubscriptionPlan subscriptionPlan = subscriptionPlanArr[0];
            networkPolicy.cycleRule =
                    NetworkPolicy.buildRule(
                            getCycleDayFromCarrierConfig(
                                    persistableBundle2,
                                    networkPolicy.cycleRule.isMonthly()
                                            ? networkPolicy.cycleRule.start.getDayOfMonth()
                                            : -1),
                            ZoneId.systemDefault());
            long dataLimitBytes = subscriptionPlan.getDataLimitBytes();
            if (dataLimitBytes == -1) {
                networkPolicy.warningBytes =
                        getWarningBytesFromCarrierConfig(
                                persistableBundle2, networkPolicy.warningBytes);
                networkPolicy.limitBytes =
                        getLimitBytesFromCarrierConfig(
                                persistableBundle2, networkPolicy.limitBytes);
            } else if (dataLimitBytes == Long.MAX_VALUE) {
                networkPolicy.warningBytes = -1L;
                networkPolicy.limitBytes = -1L;
            } else {
                networkPolicy.warningBytes = (9 * dataLimitBytes) / 10;
                int dataLimitBehavior = subscriptionPlan.getDataLimitBehavior();
                if (dataLimitBehavior == 0 || dataLimitBehavior == 1) {
                    networkPolicy.limitBytes = dataLimitBytes;
                } else {
                    networkPolicy.limitBytes = -1L;
                }
            }
        }
        if (networkPolicy.equals(networkPolicy2)) {
            return false;
        }
        Slog.d("NetworkPolicy", "Updated " + networkPolicy2 + " to " + networkPolicy);
        return true;
    }

    public final void updateNetworkEnabledNL() {
        boolean z = LOGV;
        if (z) {
            Slog.v("NetworkPolicy", "updateNetworkEnabledNL()");
        }
        Trace.traceBegin(2097152L, "updateNetworkEnabledNL");
        String string =
                SemCarrierFeature.getInstance()
                        .getString(0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
        long j = -1;
        if ((string != null && string.startsWith("VZW-")) || isNaGsm("ATT") || isNaGsm("TMO")) {
            if (z) {
                Slog.d("NetworkPolicy", "updateVideoCallLocked()");
            }
            this.mClock.millis();
            for (NetworkPolicy networkPolicy : this.mNetworkPolicy.values()) {
                if (networkPolicy.limitBytes != -1
                        && networkPolicy.hasCycle()
                        && findRelevantSubIdNL(networkPolicy.template) != -1) {
                    Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy).next();
                    long totalBytes =
                            getTotalBytes(
                                    networkPolicy.template,
                                    ((ZonedDateTime) pair.first).toInstant().toEpochMilli(),
                                    ((ZonedDateTime) pair.second).toInstant().toEpochMilli());
                    StringBuilder sb = new StringBuilder("mIsVideoCall: ");
                    sb.append(this.mIsVideoCall);
                    sb.append(", mHasEpdgCall:");
                    RCPManagerService$$ExternalSyntheticOutline0.m(
                            "NetworkPolicy", sb, this.mHasEpdgCall);
                    if (!this.mHasEpdgCall
                            && this.mIsVideoCall
                            && networkPolicy.isOverLimit(totalBytes)) {
                        NetworkTemplate networkTemplate = networkPolicy.template;
                        if (LOGV) {
                            Slog.v("NetworkPolicy", "notifyVideoCallOverLimit()");
                        }
                        Log.d(
                                "NetworkPolicy",
                                "matchRule: "
                                        + networkTemplate.getMatchRule()
                                        + " AlreadySent: "
                                        + this.mVideoCallLimitAlreadySent);
                        int matchRule = networkTemplate.getMatchRule();
                        if (matchRule == 1 || matchRule == 10) {
                            if (this.mVideoCallLimitAlreadySent) {
                                continue;
                            } else {
                                this.mContext.sendBroadcast(
                                        new Intent(
                                                "com.android.intent.action.VIDEO_DATAUSAGE_REACH_TO_LIMIT"));
                                this.mVideoCallLimitAlreadySent = true;
                                synchronized (this.mNetworkPoliciesSecondLock) {
                                    updateNotificationsNL();
                                }
                            }
                        }
                    }
                }
            }
        }
        long time = this.mStatLogger.getTime();
        int size = this.mNetworkPolicy.size() - 1;
        while (size >= 0) {
            NetworkPolicy networkPolicy2 = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
            if (networkPolicy2.limitBytes == j || !networkPolicy2.hasCycle()) {
                this.mHandler.obtainMessage(18, 1, 0, networkPolicy2.template).sendToTarget();
            } else {
                Pair pair2 = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy2).next();
                long epochMilli = ((ZonedDateTime) pair2.first).toInstant().toEpochMilli();
                this.mHandler
                        .obtainMessage(
                                18,
                                ((!networkPolicy2.isOverLimit(
                                                                getTotalBytes(
                                                                        networkPolicy2.template,
                                                                        epochMilli,
                                                                        ((ZonedDateTime)
                                                                                        pair2.second)
                                                                                .toInstant()
                                                                                .toEpochMilli()))
                                                        || this.mIsVideoCall
                                                        || networkPolicy2.lastLimitSnooze
                                                                >= epochMilli)
                                                ? 0
                                                : 1)
                                        ^ 1,
                                0,
                                networkPolicy2.template)
                        .sendToTarget();
            }
            size--;
            j = -1;
        }
        this.mStatLogger.logDurationStat(0, time);
        Trace.traceEnd(2097152L);
    }

    public final void updateNetworkRulesNL() {
        int i;
        String[] strArr;
        SubscriptionPlan primarySubscriptionPlanLocked;
        long max;
        NetworkPolicy networkPolicy;
        int i2;
        long max2;
        long max3;
        if (LOGV) {
            Slog.v("NetworkPolicy", "updateNetworkRulesNL()");
        }
        Trace.traceBegin(2097152L, "updateNetworkRulesNL");
        List<NetworkStateSnapshot> allNetworkStateSnapshots =
                this.mConnManager.getAllNetworkStateSnapshots();
        this.mNetIdToSubId.clear();
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = allNetworkStateSnapshots.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                break;
            }
            NetworkStateSnapshot networkStateSnapshot = (NetworkStateSnapshot) it.next();
            this.mNetIdToSubId.put(
                    networkStateSnapshot.getNetwork().getNetId(), networkStateSnapshot.getSubId());
            arrayMap.put(
                    networkStateSnapshot,
                    new NetworkIdentity.Builder()
                            .setNetworkStateSnapshot(networkStateSnapshot)
                            .setDefaultNetwork(true)
                            .build());
        }
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        int size = this.mNetworkPolicy.size() - 1;
        long j = Long.MAX_VALUE;
        while (size >= 0) {
            NetworkPolicy networkPolicy2 = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
            arraySet2.clear();
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                if (networkPolicy2.template.matches((NetworkIdentity) arrayMap.valueAt(size2))) {
                    arraySet2.addAll(
                            ((NetworkStateSnapshot) arrayMap.keyAt(size2))
                                    .getLinkProperties()
                                    .getAllInterfaceNames());
                }
            }
            if (LOGD) {
                Slog.d("NetworkPolicy", "Applying " + networkPolicy2 + " to ifaces " + arraySet2);
            }
            int i3 = networkPolicy2.warningBytes != -1 ? i : 0;
            int i4 = networkPolicy2.limitBytes != -1 ? i : 0;
            if (!(i4 == 0 && i3 == 0) && networkPolicy2.hasCycle()) {
                Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy2).next();
                long epochMilli = ((ZonedDateTime) pair.first).toInstant().toEpochMilli();
                networkPolicy = networkPolicy2;
                i2 = size;
                long totalBytes =
                        getTotalBytes(
                                networkPolicy2.template,
                                epochMilli,
                                ((ZonedDateTime) pair.second).toInstant().toEpochMilli());
                max2 =
                        (i4 == 0 || networkPolicy.lastLimitSnooze >= epochMilli)
                                ? Long.MAX_VALUE
                                : Math.max(1L, networkPolicy.limitBytes - totalBytes);
                max3 =
                        (i3 == 0
                                        || networkPolicy.lastWarningSnooze >= epochMilli
                                        || networkPolicy.isOverWarning(totalBytes))
                                ? Long.MAX_VALUE
                                : Math.max(1L, networkPolicy.warningBytes - totalBytes);
            } else {
                networkPolicy = networkPolicy2;
                i2 = size;
                max3 = Long.MAX_VALUE;
                max2 = Long.MAX_VALUE;
            }
            if (i3 != 0 || i4 != 0 || networkPolicy.metered) {
                if (arraySet2.size() > i) {
                    Slog.w(
                            "NetworkPolicy",
                            "shared quota unsupported; generating rule for each iface");
                }
                if (this.mIsVideoCall && this.mVideoCallLimitAlreadySent) {
                    max3 = Long.MAX_VALUE;
                    max2 = Long.MAX_VALUE;
                }
                for (int size3 = arraySet2.size() - i; size3 >= 0; size3--) {
                    String str = (String) arraySet2.valueAt(size3);
                    this.mHandler
                            .obtainMessage(10, new IfaceQuotas(max3, max2, str))
                            .sendToTarget();
                    arraySet.add(str);
                }
            }
            if (i3 != 0) {
                long j2 = networkPolicy.warningBytes;
                if (j2 < j) {
                    j = j2;
                }
            }
            if (i4 != 0) {
                long j3 = networkPolicy.limitBytes;
                if (j3 < j) {
                    j = j3;
                }
            }
            size = i2 - 1;
            i = 1;
        }
        for (NetworkStateSnapshot networkStateSnapshot2 : allNetworkStateSnapshots) {
            if (!networkStateSnapshot2.getNetworkCapabilities().hasCapability(11)) {
                arraySet2.clear();
                arraySet2.addAll(networkStateSnapshot2.getLinkProperties().getAllInterfaceNames());
                for (int size4 = arraySet2.size() - 1; size4 >= 0; size4--) {
                    String str2 = (String) arraySet2.valueAt(size4);
                    if (!arraySet.contains(str2)) {
                        this.mHandler
                                .obtainMessage(
                                        10, new IfaceQuotas(Long.MAX_VALUE, Long.MAX_VALUE, str2))
                                .sendToTarget();
                        arraySet.add(str2);
                    }
                }
            }
        }
        synchronized (this.mMeteredIfacesLock) {
            try {
                for (int size5 = this.mMeteredIfaces.size() - 1; size5 >= 0; size5--) {
                    String str3 = (String) this.mMeteredIfaces.valueAt(size5);
                    if (!arraySet.contains(str3)) {
                        this.mHandler.obtainMessage(11, str3).sendToTarget();
                    }
                }
                this.mMeteredIfaces = arraySet;
            } catch (Throwable th) {
                throw th;
            }
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = Settings.Global.getInt(contentResolver, "netpolicy_quota_enabled", 1) != 0;
        long j4 =
                Settings.Global.getLong(
                        contentResolver, "netpolicy_quota_unlimited", QUOTA_UNLIMITED_DEFAULT);
        float f = Settings.Global.getFloat(contentResolver, "netpolicy_quota_limited", 0.1f);
        this.mSubscriptionOpportunisticQuota.clear();
        for (NetworkStateSnapshot networkStateSnapshot3 : allNetworkStateSnapshots) {
            if (z && networkStateSnapshot3.getNetwork() != null) {
                int i5 = this.mNetIdToSubId.get(networkStateSnapshot3.getNetwork().getNetId(), -1);
                if (i5 != -1
                        && (primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(i5))
                                != null) {
                    long dataLimitBytes = primarySubscriptionPlanLocked.getDataLimitBytes();
                    if (!networkStateSnapshot3.getNetworkCapabilities().hasCapability(18)) {
                        max = 0;
                    } else if (dataLimitBytes == -1) {
                        max = -1;
                    } else {
                        if (dataLimitBytes == Long.MAX_VALUE) {
                            max = j4;
                        } else {
                            Range<ZonedDateTime> next =
                                    primarySubscriptionPlanLocked.cycleIterator().next();
                            long epochMilli2 = next.getLower().toInstant().toEpochMilli();
                            long epochMilli3 = next.getUpper().toInstant().toEpochMilli();
                            long epochMilli4 =
                                    ZonedDateTime.ofInstant(
                                                    this.mClock.instant(),
                                                    next.getLower().getZone())
                                            .truncatedTo(ChronoUnit.DAYS)
                                            .toInstant()
                                            .toEpochMilli();
                            max =
                                    Math.max(
                                            0L,
                                            (long)
                                                    (((dataLimitBytes
                                                                            - (networkStateSnapshot3
                                                                                                    .getSubscriberId()
                                                                                            == null
                                                                                    ? 0L
                                                                                    : getTotalBytes(
                                                                                            buildTemplateCarrierMetered(
                                                                                                    r1),
                                                                                            epochMilli2,
                                                                                            epochMilli4)))
                                                                    / ((((epochMilli3
                                                                                                    - r15
                                                                                                            .toEpochMilli())
                                                                                            - 1)
                                                                                    / TimeUnit.DAYS
                                                                                            .toMillis(
                                                                                                    1L))
                                                                            + 1))
                                                            * f));
                        }
                        this.mSubscriptionOpportunisticQuota.put(i5, max);
                    }
                    this.mSubscriptionOpportunisticQuota.put(i5, max);
                }
            }
        }
        synchronized (this.mMeteredIfacesLock) {
            ArraySet arraySet3 = this.mMeteredIfaces;
            strArr = (String[]) arraySet3.toArray(new String[arraySet3.size()]);
        }
        this.mHandler.obtainMessage(2, strArr).sendToTarget();
        this.mHandler.obtainMessage(7, Long.valueOf(j)).sendToTarget();
        Trace.traceEnd(2097152L);
    }

    public final void updateNetworkStats(int i, boolean z) {
        if (Trace.isTagEnabled(2097152L)) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "updateNetworkStats: ", "/");
            m.append(z ? "F" : "B");
            Trace.traceBegin(2097152L, m.toString());
        }
        try {
            this.mNetworkStats.noteUidForeground(i, z);
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public void updateNetworks() throws InterruptedException {
        updateNetworksInternal();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        countDownLatch.countDown();
                    }
                });
        countDownLatch.await(5L, TimeUnit.SECONDS);
    }

    public final void updateNetworksInternal() {
        updateSubscriptions();
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                if (LOGV) {
                    Slog.v("NetworkPolicy", "ensureActiveCarrierPolicyAL()");
                }
                if (!this.mSuppressDefaultPolicy) {
                    for (int i = 0; i < this.mSubIdToSubscriberId.size(); i++) {
                        ensureActiveCarrierPolicyAL(
                                this.mSubIdToSubscriberId.keyAt(i),
                                (String) this.mSubIdToSubscriberId.valueAt(i));
                    }
                }
                normalizePoliciesNL(getNetworkPolicies(this.mContext.getOpPackageName()));
                updateNetworkEnabledNL();
                updateNetworkRulesNL();
                updateNotificationsNL();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateNotificationsNL() {
        int i;
        long j;
        int i2;
        boolean z;
        NetworkPolicy networkPolicy;
        boolean z2;
        String[] packagesForUid;
        long j2;
        if (LOGV) {
            Slog.v("NetworkPolicy", "updateNotificationsNL()");
        }
        Trace.traceBegin(2097152L, "updateNotificationsNL");
        ArraySet arraySet = new ArraySet(this.mActiveNotifs);
        this.mActiveNotifs.clear();
        long millis = this.mClock.millis();
        boolean z3 = 1;
        int size = this.mNetworkPolicy.size() - 1;
        while (size >= 0) {
            NetworkPolicy networkPolicy2 = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
            int findRelevantSubIdNL = findRelevantSubIdNL(networkPolicy2.template);
            if (findRelevantSubIdNL != -1) {
                ActiveDataSubIdListener activeDataSubIdListener = this.mActiveDataSubIdListener;
                if ((findRelevantSubIdNL == activeDataSubIdListener.mDefaultDataSubId
                                || findRelevantSubIdNL == activeDataSubIdListener.mActiveDataSubId)
                        && networkPolicy2.hasCycle()) {
                    Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy2).next();
                    long epochMilli = ((ZonedDateTime) pair.first).toInstant().toEpochMilli();
                    long epochMilli2 = ((ZonedDateTime) pair.second).toInstant().toEpochMilli();
                    long totalBytes =
                            getTotalBytes(networkPolicy2.template, epochMilli, epochMilli2);
                    if (totalBytes > this.preTotalBytes + 100000) {
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        "updateNotificationsNL() - totalBytes: ",
                                        totalBytes,
                                        ", warningBytes= ");
                        m.append(networkPolicy2.warningBytes);
                        m.append(", limitBytes= ");
                        m.append(networkPolicy2.limitBytes);
                        Slog.v("NetworkPolicy", m.toString());
                        this.preTotalBytes = totalBytes;
                    }
                    PersistableBundle persistableBundle =
                            (PersistableBundle) this.mSubIdToCarrierConfig.get(findRelevantSubIdNL);
                    if (CarrierConfigManager.isConfigForIdentifiedCarrier(persistableBundle)) {
                        boolean z4 =
                                persistableBundle != null
                                        ? persistableBundle.getBoolean(
                                                "data_warning_notification_bool", z3)
                                        : z3;
                        boolean z5 =
                                persistableBundle != null
                                        ? persistableBundle.getBoolean(
                                                "data_limit_notification_bool", z3)
                                        : z3;
                        boolean z6 =
                                persistableBundle != null
                                        ? persistableBundle.getBoolean(
                                                "data_rapid_notification_bool", z3)
                                        : z3;
                        ApplicationInfo applicationInfo = null;
                        if (isKorOperator()) {
                            String str =
                                    networkPolicy2.template.getSubscriberIds().isEmpty()
                                            ? null
                                            : (String)
                                                    networkPolicy2
                                                            .template
                                                            .getSubscriberIds()
                                                            .iterator()
                                                            .next();
                            int intValue =
                                    str != null
                                            ? ((Integer)
                                                            this.mSubscriberIdToSlotId.getOrDefault(
                                                                    str, -1))
                                                    .intValue()
                                            : -1;
                            long longValue =
                                    ((Long)
                                                    this.mTetheringWarningBytes.getOrDefault(
                                                            Integer.valueOf(intValue), -1L))
                                            .longValue();
                            if (this.mTetheringState
                                    && isKorOperator()
                                    && longValue != -1
                                    && this.mDefaultDataPhoneId == intValue
                                    && this.mTetheringNotiSnooze == -1) {
                                NetworkTemplate networkTemplate = networkPolicy2.template;
                                if (this.mStatsCallback.mIsAnyCallbackReceived) {
                                    Iterator it =
                                            ((ArrayList)
                                                            this.mDeps.getNetworkUidBytes(
                                                                    networkTemplate,
                                                                    epochMilli,
                                                                    epochMilli2))
                                                    .iterator();
                                    j2 = 0;
                                    while (it.hasNext()) {
                                        NetworkStats.Bucket bucket =
                                                (NetworkStats.Bucket) it.next();
                                        if (bucket.getUid() == -5 && bucket.getMetered() == 2) {
                                            j2 = bucket.getTxBytes() + bucket.getRxBytes() + j2;
                                        }
                                    }
                                } else {
                                    j2 = 0;
                                }
                                if (longValue < j2) {
                                    i = size;
                                    j = totalBytes;
                                    enqueueNotification(networkPolicy2, -5, j2, null);
                                } else {
                                    i = size;
                                    j = totalBytes;
                                }
                            } else {
                                i = size;
                                j = totalBytes;
                                j2 = 0;
                            }
                            if (this.mTetheringState) {
                                Slog.d(
                                        "NetworkPolicy",
                                        "mDefaultDataPhoneId : "
                                                + this.mDefaultDataPhoneId
                                                + ", tetheringTotalBytes : "
                                                + j2);
                            }
                        } else {
                            i = size;
                            j = totalBytes;
                        }
                        if (z4
                                && networkPolicy2.isOverWarning(j)
                                && !networkPolicy2.isOverLimit(j)
                                && networkPolicy2.lastWarningSnooze < epochMilli) {
                            enqueueNotification(networkPolicy2, 34, j, null);
                        }
                        if (z5) {
                            if (!networkPolicy2.isOverLimit(j) || this.mIsVideoCall) {
                                this.mOverLimitNotified.remove(networkPolicy2.template);
                            } else if (networkPolicy2.lastLimitSnooze >= epochMilli) {
                                enqueueNotification(networkPolicy2, 36, j, null);
                            } else {
                                enqueueNotification(networkPolicy2, 35, j, null);
                                Parcelable parcelable = networkPolicy2.template;
                                if (!this.mOverLimitNotified.contains(parcelable)) {
                                    Context context = this.mContext;
                                    Resources resources = context.getResources();
                                    Intent intent = new Intent();
                                    intent.setComponent(
                                            ComponentName.unflattenFromString(
                                                    resources.getString(R.string.eventTypeOther)));
                                    intent.addFlags(268435456);
                                    intent.putExtra("android.net.NETWORK_TEMPLATE", parcelable);
                                    context.startActivity(intent);
                                    this.mOverLimitNotified.add(parcelable);
                                }
                            }
                        }
                        if (z6 && networkPolicy2.limitBytes != -1) {
                            long millis2 = TimeUnit.DAYS.toMillis(4L);
                            long j3 = millis - millis2;
                            long totalBytes2 = getTotalBytes(networkPolicy2.template, j3, millis);
                            long j4 = ((epochMilli2 - epochMilli) * totalBytes2) / millis2;
                            long j5 = (networkPolicy2.limitBytes * 3) / 2;
                            if (LOGD) {
                                StringBuilder m2 =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "Rapid usage considering recent ",
                                                totalBytes2,
                                                " projected ");
                                m2.append(j4);
                                m2.append(" alert ");
                                m2.append(j5);
                                Slog.d("NetworkPolicy", m2.toString());
                            }
                            boolean z7 =
                                    networkPolicy2.lastRapidSnooze
                                            >= millis
                                                    - BackupManagerConstants
                                                            .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                            if (j4 > j5 && !z7) {
                                NetworkTemplate networkTemplate2 = networkPolicy2.template;
                                if (this.mStatsCallback.mIsAnyCallbackReceived) {
                                    networkPolicy = networkPolicy2;
                                    i2 = i;
                                    z2 = true;
                                    Iterator it2 =
                                            ((ArrayList)
                                                            this.mDeps.getNetworkUidBytes(
                                                                    networkTemplate2, j3, millis))
                                                    .iterator();
                                    int i3 = 0;
                                    long j6 = 0;
                                    long j7 = 0;
                                    while (it2.hasNext()) {
                                        NetworkStats.Bucket bucket2 =
                                                (NetworkStats.Bucket) it2.next();
                                        long txBytes = bucket2.getTxBytes() + bucket2.getRxBytes();
                                        j7 += txBytes;
                                        if (txBytes > j6) {
                                            i3 = bucket2.getUid();
                                            j6 = txBytes;
                                        }
                                    }
                                    if (j6 > 0
                                            && j6 > j7 / 2
                                            && (packagesForUid =
                                                            this.mContext
                                                                    .getPackageManager()
                                                                    .getPackagesForUid(i3))
                                                    != null
                                            && packagesForUid.length == 1) {
                                        try {
                                            applicationInfo =
                                                    this.mContext
                                                            .getPackageManager()
                                                            .getApplicationInfo(
                                                                    packagesForUid[0], 4989440);
                                        } catch (PackageManager.NameNotFoundException unused) {
                                        }
                                    }
                                } else {
                                    networkPolicy = networkPolicy2;
                                    i2 = i;
                                    z2 = true;
                                }
                                z = z2;
                                enqueueNotification(networkPolicy, 45, 0L, applicationInfo);
                                size = i2 - 1;
                                z3 = z;
                            }
                        }
                        i2 = i;
                        z = true;
                        size = i2 - 1;
                        z3 = z;
                    } else if (LOGV) {
                        Slog.v("NetworkPolicy", "isConfigForIdentifiedCarrier returned false");
                    }
                }
            }
            i2 = size;
            z = z3;
            size = i2 - 1;
            z3 = z;
        }
        for (int size2 = arraySet.size() - z3; size2 >= 0; size2--) {
            NotificationId notificationId = (NotificationId) arraySet.valueAt(size2);
            if (!this.mActiveNotifs.contains(notificationId)) {
                ((NotificationManager) this.mContext.getSystemService(NotificationManager.class))
                        .cancel(notificationId.mTag, notificationId.mId);
            }
        }
        Trace.traceEnd(2097152L);
    }

    public final void updatePowerSaveAllowlistUL() {
        int[] allowListedAppIds = this.mPowerExemptionManager.getAllowListedAppIds(false);
        this.mPowerSaveWhitelistExceptIdleAppIds.clear();
        for (int i : allowListedAppIds) {
            this.mPowerSaveWhitelistExceptIdleAppIds.put(i, true);
        }
        int[] allowListedAppIds2 = this.mPowerExemptionManager.getAllowListedAppIds(true);
        this.mPowerSaveWhitelistAppIds.clear();
        for (int i2 : allowListedAppIds2) {
            this.mPowerSaveWhitelistAppIds.put(i2, true);
        }
    }

    public void updateRestrictBackgroundByLowPowerModeUL(PowerSaveState powerSaveState) {
        boolean z;
        boolean z2;
        boolean z3 = this.mRestrictBackgroundLowPowerMode;
        boolean z4 = powerSaveState.batterySaverEnabled;
        if (z3 == z4) {
            return;
        }
        this.mRestrictBackgroundLowPowerMode = z4;
        boolean z5 = this.mRestrictBackgroundChangedInBsm;
        if (this.mRestrictBackgroundLowPowerMode) {
            z = !this.mRestrictBackground;
            this.mRestrictBackgroundBeforeBsm = this.mRestrictBackground;
            z2 = false;
        } else {
            z = !this.mRestrictBackgroundChangedInBsm;
            z4 = this.mRestrictBackgroundBeforeBsm;
            z2 = z5;
        }
        if (z) {
            setRestrictBackgroundUL("low_power", z4);
        }
        this.mRestrictBackgroundChangedInBsm = z2;
    }

    public void updateRestrictedModeAllowlistUL() {
        this.mUidFirewallRestrictedModeRules.clear();
        forEachUid(
                "updateRestrictedModeAllowlist",
                new NetworkPolicyManagerService$$ExternalSyntheticLambda0(1, this));
        if (this.mRestrictedNetworkingMode) {
            setUidFirewallRulesUL(4, this.mUidFirewallRestrictedModeRules);
        }
        enableFirewallChainUL(4, this.mRestrictedNetworkingMode);
    }

    public void updateRestrictedModeForUidUL(int i) {
        int updateBlockedReasonsForRestrictedModeUL = updateBlockedReasonsForRestrictedModeUL(i);
        if (this.mRestrictedNetworkingMode) {
            setUidFirewallRuleUL(4, i, (updateBlockedReasonsForRestrictedModeUL & 8) != 0 ? 0 : 1);
        }
    }

    public final void updateRuleForAppIdleUL(int i, int i2) {
        if (isUidValidForDenylistRulesUL(i)) {
            if (Trace.isTagEnabled(2097152L)) {
                Trace.traceBegin(2097152L, "updateRuleForAppIdleUL: " + i);
            }
            try {
                if (this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(i))
                        || !isUidIdle(i, i2)
                        || isUidForegroundOnRestrictPowerUL(i)) {
                    setUidFirewallRuleUL(2, i, 0);
                    if (LOGD) {
                        Log.d("NetworkPolicy", "updateRuleForAppIdleUL " + i + " to DEFAULT");
                    }
                } else {
                    setUidFirewallRuleUL(2, i, 2);
                    if (LOGD) {
                        Log.d("NetworkPolicy", "updateRuleForAppIdleUL DENY " + i);
                    }
                }
                Trace.traceEnd(2097152L);
            } catch (Throwable th) {
                Trace.traceEnd(2097152L);
                throw th;
            }
        }
    }

    public final void updateRuleForBackgroundUL(int i) {
        if (isUidValidForAllowlistRulesUL(i)) {
            Trace.traceBegin(2097152L, "updateRuleForBackgroundUL: " + i);
            try {
                if (!isAllowlistedFromPowerSaveUL(i, false)
                        && this.mBackgroundTransitioningUids.indexOfKey(i) < 0
                        && !NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(
                                (NetworkPolicyManager.UidState) this.mUidState.get(i))) {
                    setUidFirewallRuleUL(6, i, 0);
                    if (LOGD) {
                        Log.d("NetworkPolicy", "updateRuleForBackgroundUL " + i + " to DEFAULT");
                    }
                    Trace.traceEnd(2097152L);
                }
                setUidFirewallRuleUL(6, i, 1);
                if (LOGD) {
                    Log.d("NetworkPolicy", "updateRuleForBackgroundUL ALLOW " + i);
                }
                Trace.traceEnd(2097152L);
            } catch (Throwable th) {
                Trace.traceEnd(2097152L);
                throw th;
            }
        }
    }

    public final void updateRuleForLowPowerStandbyUL(int i) {
        if (hasInternetPermissionUL(i)) {
            int effectiveBlockedReasons = getEffectiveBlockedReasons(i);
            if (this.mUidState.contains(i) && (effectiveBlockedReasons & 32) == 0) {
                this.mUidFirewallLowPowerStandbyModeRules.put(i, 1);
                setUidFirewallRuleUL(5, i, 1);
            } else {
                this.mUidFirewallLowPowerStandbyModeRules.delete(i);
                setUidFirewallRuleUL(5, i, 0);
            }
        }
    }

    public final void updateRulesForAllowlistedPowerSaveUL(
            boolean z, int i, SparseIntArray sparseIntArray) {
        if (!z) {
            setUidFirewallRulesUL(i, null, 2);
            return;
        }
        sparseIntArray.clear();
        List users = this.mUserManager.getUsers();
        for (int size = users.size() - 1; size >= 0; size--) {
            UserInfo userInfo = (UserInfo) users.get(size);
            updateRulesForAllowlistedAppIds(
                    sparseIntArray, this.mPowerSaveTempWhitelistAppIds, userInfo.id);
            updateRulesForAllowlistedAppIds(
                    sparseIntArray, this.mPowerSaveWhitelistAppIds, userInfo.id);
            if (i == 3) {
                updateRulesForAllowlistedAppIds(
                        sparseIntArray, this.mPowerSaveWhitelistExceptIdleAppIds, userInfo.id);
            }
        }
        for (int size2 = this.mUidState.size() - 1; size2 >= 0; size2--) {
            if (NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(
                    (NetworkPolicyManager.UidState) this.mUidState.valueAt(size2))) {
                sparseIntArray.put(this.mUidState.keyAt(size2), 1);
            }
        }
        setUidFirewallRulesUL(i, sparseIntArray, 1);
    }

    public final void updateRulesForAppIdleParoleUL() {
        int i;
        boolean isInParole = this.mAppStandby.isInParole();
        boolean z = !isInParole;
        int size = this.mUidFirewallStandbyRules.size();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            int keyAt = this.mUidFirewallStandbyRules.keyAt(i2);
            if (isUidValidForDenylistRulesUL(keyAt)) {
                synchronized (this.mUidBlockedState) {
                    UidBlockedState uidBlockedState =
                            (UidBlockedState) this.mUidBlockedState.get(keyAt);
                    i = uidBlockedState == null ? 0 : uidBlockedState.blockedReasons;
                }
                if (z || (65535 & i) != 0) {
                    boolean z2 = !isInParole && isUidIdle(keyAt);
                    if (!z2
                            || this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(keyAt))
                            || isUidForegroundOnRestrictPowerUL(keyAt)) {
                        this.mUidFirewallStandbyRules.put(keyAt, 0);
                    } else {
                        this.mUidFirewallStandbyRules.put(keyAt, 2);
                        sparseIntArray.put(keyAt, 2);
                    }
                    updateRulesForPowerRestrictionsUL(keyAt, z2);
                }
            }
            i2++;
        }
        setUidFirewallRulesUL(2, sparseIntArray, z ? 1 : 2);
    }

    public final void updateRulesForAppIdleUL() {
        Trace.traceBegin(2097152L, "updateRulesForAppIdleUL");
        try {
            SparseIntArray sparseIntArray = this.mUidFirewallStandbyRules;
            sparseIntArray.clear();
            List users = this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                for (int i :
                        UsageStatsService.this.mAppStandby.getIdleUidsForUser(
                                ((UserInfo) users.get(size)).id)) {
                    if (!this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(i), false)
                            && hasInternetPermissionUL(i)
                            && !isUidForegroundOnRestrictPowerUL(i)) {
                        sparseIntArray.put(i, 2);
                    }
                }
            }
            setUidFirewallRulesUL(2, sparseIntArray, 0);
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            Trace.traceEnd(2097152L);
            throw th;
        }
    }

    public final void updateRulesForBackgroundChainUL() {
        Trace.traceBegin(2097152L, "updateRulesForBackgroundChainUL");
        try {
            SparseIntArray sparseIntArray = this.mUidFirewallBackgroundRules;
            sparseIntArray.clear();
            List users = this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                UserInfo userInfo = (UserInfo) users.get(size);
                updateRulesForAllowlistedAppIds(
                        sparseIntArray, this.mPowerSaveTempWhitelistAppIds, userInfo.id);
                updateRulesForAllowlistedAppIds(
                        sparseIntArray, this.mPowerSaveWhitelistAppIds, userInfo.id);
                updateRulesForAllowlistedAppIds(
                        sparseIntArray, this.mPowerSaveWhitelistExceptIdleAppIds, userInfo.id);
            }
            for (int size2 = this.mUidState.size() - 1; size2 >= 0; size2--) {
                if (this.mBackgroundTransitioningUids.indexOfKey(this.mUidState.keyAt(size2)) >= 0
                        || NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(
                                (NetworkPolicyManager.UidState) this.mUidState.valueAt(size2))) {
                    sparseIntArray.put(this.mUidState.keyAt(size2), 1);
                }
            }
            setUidFirewallRulesUL(6, sparseIntArray);
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            Trace.traceEnd(2097152L);
            throw th;
        }
    }

    public final void updateRulesForDataUsageRestrictionsUL(int i) {
        if (Trace.isTagEnabled(2097152L)) {
            Trace.traceBegin(2097152L, "updateRulesForDataUsageRestrictionsUL: " + i);
        }
        try {
            updateRulesForDataUsageRestrictionsULInner(i);
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void updateRulesForDataUsageRestrictionsULInner(int i) {
        int i2;
        int i3;
        int i4;
        int deriveUidRules;
        boolean z;
        int i5;
        int i6;
        int i7;
        if (!isUidValidForAllowlistRulesUL(i)) {
            if (LOGD) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "no need to update restrict data rules for uid ", "NetworkPolicy");
                return;
            }
            return;
        }
        int i8 = this.mUidPolicy.get(i, 0);
        boolean isUidForegroundOnRestrictBackgroundUL = isUidForegroundOnRestrictBackgroundUL(i);
        Set set = (Set) this.mMeteredRestrictedUids.get(UserHandle.getUserId(i));
        boolean z2 = set != null && set.contains(Integer.valueOf(i));
        boolean z3 = (i8 & 1) != 0;
        boolean z4 = (i8 & 4) != 0;
        int i9 = z2 ? 262144 : 0;
        boolean z5 = this.mRestrictBackground;
        int i10 = EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        int i11 = i9 | (z5 ? 65536 : 0) | (z3 ? 131072 : 0);
        int i12 = (i < 10000 ? 131072 : 0) | (isUidForegroundOnRestrictBackgroundUL ? 262144 : 0);
        if (!z4) {
            i10 = 0;
        }
        int i13 = i10 | i12;
        synchronized (this.mUidBlockedState) {
            try {
                UidBlockedState orCreateUidBlockedStateForUid =
                        getOrCreateUidBlockedStateForUid(i, this.mUidBlockedState);
                UidBlockedState orCreateUidBlockedStateForUid2 =
                        getOrCreateUidBlockedStateForUid(i, this.mTmpUidBlockedState);
                orCreateUidBlockedStateForUid2.blockedReasons =
                        orCreateUidBlockedStateForUid.blockedReasons;
                orCreateUidBlockedStateForUid2.allowedReasons =
                        orCreateUidBlockedStateForUid.allowedReasons;
                orCreateUidBlockedStateForUid2.effectiveBlockedReasons =
                        orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                orCreateUidBlockedStateForUid.blockedReasons =
                        (orCreateUidBlockedStateForUid.blockedReasons
                                        & GnssNative.GNSS_AIDING_TYPE_ALL)
                                | i11;
                orCreateUidBlockedStateForUid.allowedReasons =
                        (orCreateUidBlockedStateForUid.allowedReasons
                                        & GnssNative.GNSS_AIDING_TYPE_ALL)
                                | i13;
                orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
                i2 = orCreateUidBlockedStateForUid2.effectiveBlockedReasons;
                i3 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                int i14 = orCreateUidBlockedStateForUid2.allowedReasons;
                if (i2 == i3) {
                    i4 = i14;
                    deriveUidRules = 0;
                } else {
                    i4 = i14;
                    deriveUidRules = orCreateUidBlockedStateForUid.deriveUidRules();
                }
                z = LOGV;
                if (z) {
                    i5 = deriveUidRules;
                    Log.v(
                            "NetworkPolicy",
                            "updateRuleForRestrictBackgroundUL("
                                    + i
                                    + "): isForeground="
                                    + isUidForegroundOnRestrictBackgroundUL
                                    + ", isDenied="
                                    + z3
                                    + ", isAllowed="
                                    + z4
                                    + ", isRestrictedByAdmin="
                                    + z2
                                    + ", oldBlockedState="
                                    + orCreateUidBlockedStateForUid2
                                    + ", newBlockedState="
                                    + orCreateUidBlockedStateForUid
                                    + ", newBlockedMeteredReasons="
                                    + NetworkPolicyManager.blockedReasonsToString(i11)
                                    + ", newAllowedMeteredReasons="
                                    + NetworkPolicyManager.allowedReasonsToString(i13));
                } else {
                    i5 = deriveUidRules;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (i2 != i3) {
            handleBlockedReasonsChanged(i, i3, i2);
            this.mHandler.obtainMessage(1, i, i5).sendToTarget();
        }
        if (this.mUseMeteredFirewallChains) {
            if ((262144 & i3) != 0) {
                setUidFirewallRuleUL(12, i, 2);
                i7 = 131072;
                i6 = 0;
            } else {
                i6 = 0;
                setUidFirewallRuleUL(12, i, 0);
                i7 = 131072;
            }
            if ((i7 & i3) != 0) {
                setUidFirewallRuleUL(11, i, 2);
            } else {
                setUidFirewallRuleUL(11, i, i6);
            }
            if ((i13 & 327680) != 0) {
                setUidFirewallRuleUL(10, i, 1);
                return;
            } else {
                setUidFirewallRuleUL(10, i, i6);
                return;
            }
        }
        if ((i2 & 393216) != 0 || (i3 & 393216) != 0) {
            boolean z6 = (393216 & i3) != 0;
            if (z) {
                Slog.v("NetworkPolicy", "setMeteredNetworkDenylist " + i + ": " + z6);
            }
            try {
                this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, z6);
                this.mLogger.meteredDenylistChanged(i, z6);
                if (Process.isApplicationUid(i)) {
                    int sdkSandboxUid = Process.toSdkSandboxUid(i);
                    this.mNetworkManager.setUidOnMeteredNetworkDenylist(sdkSandboxUid, z6);
                    this.mLogger.meteredDenylistChanged(sdkSandboxUid, z6);
                }
            } catch (RemoteException unused) {
            } catch (IllegalStateException e) {
                Log.wtf("NetworkPolicy", "problem setting denylist (" + z6 + ") rules for " + i, e);
            }
        }
        if ((i4 & 327680) == 0 && (i13 & 327680) == 0) {
            return;
        }
        boolean z7 = (i13 & 327680) != 0;
        if (LOGV) {
            Slog.v("NetworkPolicy", "setMeteredNetworkAllowlist " + i + ": " + z7);
        }
        try {
            this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, z7);
            this.mLogger.meteredAllowlistChanged(i, z7);
            if (Process.isApplicationUid(i)) {
                int sdkSandboxUid2 = Process.toSdkSandboxUid(i);
                this.mNetworkManager.setUidOnMeteredNetworkAllowlist(sdkSandboxUid2, z7);
                this.mLogger.meteredAllowlistChanged(sdkSandboxUid2, z7);
            }
        } catch (RemoteException unused2) {
        } catch (IllegalStateException e2) {
            Log.wtf("NetworkPolicy", "problem setting allowlist (" + z7 + ") rules for " + i, e2);
        }
    }

    public final void updateRulesForDeviceIdleUL() {
        Trace.traceBegin(2097152L, "updateRulesForDeviceIdleUL");
        try {
            updateRulesForAllowlistedPowerSaveUL(
                    this.mDeviceIdleMode, 1, this.mUidFirewallDozableRules);
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void updateRulesForGlobalChangeAL(boolean z) {
        if (Trace.isTagEnabled(2097152L)) {
            Trace.traceBegin(
                    2097152L,
                    "updateRulesForGlobalChangeAL: "
                            .concat(z ? "R" : PackageManagerShellCommandDataLoader.STDIN_PATH));
        }
        try {
            if (this.mBackgroundNetworkRestricted) {
                updateRulesForBackgroundChainUL();
            }
            updateRulesForAppIdleUL();
            updateRulesForRestrictPowerUL();
            updateRulesForRestrictBackgroundUL();
            updateRestrictedModeAllowlistUL();
            if (z) {
                normalizePoliciesNL(getNetworkPolicies(this.mContext.getOpPackageName()));
                updateNetworkRulesNL();
            }
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            Trace.traceEnd(2097152L);
            throw th;
        }
    }

    public final void updateRulesForLowPowerStandbyUL() {
        Trace.traceBegin(2097152L, "updateRulesForLowPowerStandbyUL");
        try {
            if (this.mLowPowerStandbyActive) {
                this.mUidFirewallLowPowerStandbyModeRules.clear();
                for (int size = this.mUidState.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidState.keyAt(size);
                    int effectiveBlockedReasons = getEffectiveBlockedReasons(keyAt);
                    if (hasInternetPermissionUL(keyAt) && (effectiveBlockedReasons & 32) == 0) {
                        this.mUidFirewallLowPowerStandbyModeRules.put(keyAt, 1);
                    }
                }
                setUidFirewallRulesUL(5, this.mUidFirewallLowPowerStandbyModeRules, 1);
            } else {
                setUidFirewallRulesUL(5, null, 2);
            }
            Trace.traceEnd(2097152L);
        } catch (Throwable th) {
            Trace.traceEnd(2097152L);
            throw th;
        }
    }

    public final void updateRulesForPowerRestrictionsUL(int i, int i2) {
        updateRulesForPowerRestrictionsUL(i, isUidIdle(i, i2));
    }

    public final void updateRulesForPowerRestrictionsUL(int i, boolean z) {
        if (Trace.isTagEnabled(2097152L)) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "updateRulesForPowerRestrictionsUL: ", "/");
            m.append(z ? "I" : PackageManagerShellCommandDataLoader.STDIN_PATH);
            Trace.traceBegin(2097152L, m.toString());
        }
        try {
            updateRulesForPowerRestrictionsULInner(i, z);
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void updateRulesForPowerRestrictionsULInner(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (!isUidValidForDenylistRulesUL(i)) {
            if (LOGD) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "no need to update restrict power rules for uid ", "NetworkPolicy");
                return;
            }
            return;
        }
        boolean isUidForegroundOnRestrictPowerUL = isUidForegroundOnRestrictPowerUL(i);
        boolean isProcStateAllowedWhileInLowPowerStandby =
                NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(
                        (NetworkPolicyManager.UidState) this.mUidState.get(i));
        boolean isAllowlistedFromPowerSaveUL =
                isAllowlistedFromPowerSaveUL(i, this.mDeviceIdleMode);
        synchronized (this.mUidBlockedState) {
            try {
                UidBlockedState orCreateUidBlockedStateForUid =
                        getOrCreateUidBlockedStateForUid(i, this.mUidBlockedState);
                UidBlockedState orCreateUidBlockedStateForUid2 =
                        getOrCreateUidBlockedStateForUid(i, this.mTmpUidBlockedState);
                orCreateUidBlockedStateForUid2.blockedReasons =
                        orCreateUidBlockedStateForUid.blockedReasons;
                orCreateUidBlockedStateForUid2.allowedReasons =
                        orCreateUidBlockedStateForUid.allowedReasons;
                orCreateUidBlockedStateForUid2.effectiveBlockedReasons =
                        orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                i2 = 0;
                int i5 = 2;
                int i6 = 32;
                int i7 = 8;
                int i8 = 64;
                int i9 =
                        (this.mRestrictPower ? 1 : 0)
                                | (this.mDeviceIdleMode ? 2 : 0)
                                | (this.mLowPowerStandbyActive ? 32 : 0)
                                | (z ? 4 : 0)
                                | (orCreateUidBlockedStateForUid.blockedReasons & 8)
                                | (this.mBackgroundNetworkRestricted ? 64 : 0);
                int i10 = i < 10000 ? 1 : 0;
                if (!isUidForegroundOnRestrictPowerUL) {
                    i5 = 0;
                }
                int i11 = i5 | i10;
                if (!isProcStateAllowedWhileInLowPowerStandby) {
                    i6 = 0;
                }
                int i12 = i11 | i6 | (isAllowlistedFromPowerSaveUL(i, true) ? 4 : 0);
                if (!this.mPowerSaveWhitelistExceptIdleAppIds.get(UserHandle.getAppId(i))) {
                    i7 = 0;
                }
                int i13 = i12 | i7 | (orCreateUidBlockedStateForUid.allowedReasons & 16);
                if (!this.mLowPowerStandbyAllowlistUids.get(i)) {
                    i8 = 0;
                }
                int i14 =
                        i13
                                | i8
                                | ((!this.mBackgroundNetworkRestricted
                                                || (this.mBackgroundTransitioningUids.indexOfKey(i)
                                                                < 0
                                                        && !NetworkPolicyManager
                                                                .isProcStateAllowedNetworkWhileBackground(
                                                                        (NetworkPolicyManager
                                                                                        .UidState)
                                                                                this.mUidState.get(
                                                                                        i))))
                                        ? 0
                                        : 128);
                orCreateUidBlockedStateForUid.blockedReasons =
                        i9 | (orCreateUidBlockedStateForUid.blockedReasons & (-65536));
                orCreateUidBlockedStateForUid.allowedReasons =
                        (orCreateUidBlockedStateForUid.allowedReasons & (-65536)) | i14;
                orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
                if (LOGV) {
                    Log.v(
                            "NetworkPolicy",
                            "updateRulesForPowerRestrictionsUL("
                                    + i
                                    + "), isIdle: "
                                    + z
                                    + ", mRestrictPower: "
                                    + this.mRestrictPower
                                    + ", mDeviceIdleMode: "
                                    + this.mDeviceIdleMode
                                    + ", isForeground="
                                    + isUidForegroundOnRestrictPowerUL
                                    + ", isTop="
                                    + isProcStateAllowedWhileInLowPowerStandby
                                    + ", isWhitelisted="
                                    + isAllowlistedFromPowerSaveUL
                                    + ", oldUidBlockedState="
                                    + orCreateUidBlockedStateForUid2
                                    + ", newUidBlockedState="
                                    + orCreateUidBlockedStateForUid);
                }
                i3 = orCreateUidBlockedStateForUid2.effectiveBlockedReasons;
                i4 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                if (i3 != i4) {
                    i2 = orCreateUidBlockedStateForUid.deriveUidRules();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (i3 != i4) {
            handleBlockedReasonsChanged(i, i4, i3);
            this.mHandler.obtainMessage(1, i, i2).sendToTarget();
        }
    }

    public final void updateRulesForRestrictBackgroundUL() {
        Trace.traceBegin(2097152L, "updateRulesForRestrictBackgroundUL");
        try {
            forEachUid(
                    "updateRulesForRestrictBackground",
                    new NetworkPolicyManagerService$$ExternalSyntheticLambda0(0, this));
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void updateRulesForRestrictPowerUL() {
        Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
        try {
            updateRulesForDeviceIdleUL();
            Trace.traceBegin(2097152L, "updateRulesForPowerSaveUL");
            updateRulesForAllowlistedPowerSaveUL(
                    this.mRestrictPower, 3, this.mUidFirewallPowerSaveRules);
            Trace.traceEnd(2097152L);
            forEachUid(
                    "updateRulesForRestrictPower",
                    new NetworkPolicyManagerService$$ExternalSyntheticLambda0(3, this));
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void updateSubscriptions() {
        if (LOGV) {
            Slog.v("NetworkPolicy", "updateSubscriptions()");
        }
        Trace.traceBegin(2097152L, "updateSubscriptions");
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        List emptyIfNull =
                com.android.internal.util.CollectionUtils.emptyIfNull(
                        ((SubscriptionManager)
                                        this.mContext.getSystemService(SubscriptionManager.class))
                                .getActiveSubscriptionInfoList());
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray(emptyIfNull.size());
        SparseArray sparseArray2 = new SparseArray();
        Iterator it = emptyIfNull.iterator();
        while (it.hasNext()) {
            int subscriptionId = ((SubscriptionInfo) it.next()).getSubscriptionId();
            TelephonyManager createForSubscriptionId =
                    telephonyManager.createForSubscriptionId(subscriptionId);
            String subscriberId = createForSubscriptionId.getSubscriberId();
            if (TextUtils.isEmpty(subscriberId)) {
                Slog.wtf(
                        "NetworkPolicy",
                        "Missing subscriberId for subId "
                                + createForSubscriptionId.getSubscriptionId());
            } else {
                sparseArray.put(createForSubscriptionId.getSubscriptionId(), subscriberId);
            }
            arrayList.add(
                    ArrayUtils.defeatNullable(createForSubscriptionId.getMergedImsisFromGroup()));
            PersistableBundle configForSubId =
                    this.mCarrierConfigManager.getConfigForSubId(subscriptionId);
            if (configForSubId != null) {
                sparseArray2.put(subscriptionId, configForSubId);
            } else {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        subscriptionId, "Missing CarrierConfig for subId ", "NetworkPolicy");
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                this.mSubIdToSubscriberId.clear();
                for (int i = 0; i < sparseArray.size(); i++) {
                    this.mSubIdToSubscriberId.put(
                            sparseArray.keyAt(i), (String) sparseArray.valueAt(i));
                }
                this.mMergedSubscriberIds = arrayList;
                this.mSubIdToCarrierConfig.clear();
                for (int i2 = 0; i2 < sparseArray2.size(); i2++) {
                    this.mSubIdToCarrierConfig.put(
                            sparseArray2.keyAt(i2), (PersistableBundle) sparseArray2.valueAt(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(2097152L);
    }

    public final boolean updateUidStateUL(int i, int i2, int i3, long j) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "updateUidStateUL: ", "/");
        m.append(ActivityManager.procStateToString(i2));
        m.append("/");
        m.append(j);
        m.append("/");
        m.append(ActivityManager.getCapabilitiesSummary(i3));
        Trace.traceBegin(2097152L, m.toString());
        try {
            NetworkPolicyManager.UidState uidState =
                    (NetworkPolicyManager.UidState) this.mUidState.get(i);
            boolean z = false;
            if (uidState != null && j < uidState.procStateSeq) {
                if (LOGV) {
                    Slog.v(
                            "NetworkPolicy",
                            "Ignoring older uid state updates; uid="
                                    + i
                                    + ",procState="
                                    + ActivityManager.procStateToString(i2)
                                    + ",seq="
                                    + j
                                    + ",cap="
                                    + i3
                                    + ",oldUidState="
                                    + uidState);
                }
                return false;
            }
            if (uidState != null && uidState.procState == i2 && uidState.capability == i3) {
                return false;
            }
            NetworkPolicyManager.UidState uidState2 =
                    new NetworkPolicyManager.UidState(i, i2, j, i3);
            this.mUidState.put(i, uidState2);
            if (NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)
                    != NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(
                            uidState2)) {
                updateRulesForDataUsageRestrictionsUL(i);
            }
            if (NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState)
                    != NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState2)) {
                updateRuleForAppIdleUL(i, i2);
                if (this.mDeviceIdleMode && this.mDeviceIdleMode) {
                    if (!isAllowlistedFromPowerSaveUL(i, true)
                            && !isUidForegroundOnRestrictPowerUL(i)) {
                        setUidFirewallRuleUL(1, i, 0);
                    }
                    setUidFirewallRuleUL(1, i, 1);
                }
                if (this.mRestrictPower && this.mRestrictPower) {
                    if (!isAllowlistedFromPowerSaveUL(i, false)
                            && !isUidForegroundOnRestrictPowerUL(i)) {
                        setUidFirewallRuleUL(3, i, 0);
                    }
                    setUidFirewallRuleUL(3, i, 1);
                }
                z = true;
            }
            if (this.mBackgroundNetworkRestricted) {
                boolean isProcStateAllowedNetworkWhileBackground =
                        NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(uidState);
                boolean isProcStateAllowedNetworkWhileBackground2 =
                        NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(uidState2);
                if (!isProcStateAllowedNetworkWhileBackground
                        && isProcStateAllowedNetworkWhileBackground2) {
                    this.mBackgroundTransitioningUids.delete(i);
                    updateRuleForBackgroundUL(i);
                    z = true;
                } else if (!isProcStateAllowedNetworkWhileBackground2) {
                    int indexOfKey = this.mBackgroundTransitioningUids.indexOfKey(i);
                    long uptimeMillis =
                            SystemClock.uptimeMillis() + this.mBackgroundRestrictionDelayMs;
                    if (isProcStateAllowedNetworkWhileBackground && indexOfKey < 0) {
                        this.mBackgroundTransitioningUids.put(i, uptimeMillis);
                        if (uptimeMillis < this.mNextProcessBackgroundUidsTime) {
                            this.mHandler.removeMessages(24);
                            this.mHandler.sendEmptyMessageAtTime(24, uptimeMillis);
                            this.mNextProcessBackgroundUidsTime = uptimeMillis;
                        }
                    }
                }
            }
            if (this.mLowPowerStandbyActive
                    && NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(uidState)
                            != NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(
                                    uidState2)) {
                updateRuleForLowPowerStandbyUL(i);
                z = true;
            }
            if (z) {
                updateRulesForPowerRestrictionsUL(i, i2);
            }
            return true;
        } finally {
            Trace.traceEnd(2097152L);
        }
    }

    public final void writeFirewallPolicyAL() {
        FileOutputStream startWrite;
        if (LOGV) {
            Slog.v("NetworkPolicy", "writeFirewallPolicyAL");
        }
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mFirewallPolicyFile.startWrite();
        } catch (IOException unused) {
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            int i = 0;
            while (true) {
                SparseIntArray sparseIntArray = mFirewallRules;
                if (i >= sparseIntArray.size()) {
                    resolveSerializer.endDocument();
                    this.mFirewallPolicyFile.finishWrite(startWrite);
                    return;
                }
                int keyAt = sparseIntArray.keyAt(i);
                int valueAt = sparseIntArray.valueAt(i);
                if (valueAt != 0) {
                    resolveSerializer.startTag((String) null, "firewall-policy");
                    XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt);
                    XmlUtils.writeIntAttribute(resolveSerializer, "policy", valueAt);
                    resolveSerializer.endTag((String) null, "firewall-policy");
                }
                i++;
            }
        } catch (IOException unused2) {
            fileOutputStream = startWrite;
            if (fileOutputStream != null) {
                this.mFirewallPolicyFile.failWrite(fileOutputStream);
            }
        }
    }

    public final void writePolicyAL() {
        FileOutputStream startWrite;
        if (LOGV) {
            Slog.v("NetworkPolicy", "writePolicyAL()");
        }
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mPolicyFile.startWrite();
        } catch (IOException unused) {
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "policy-list");
            XmlUtils.writeIntAttribute(resolveSerializer, "version", 14);
            XmlUtils.writeBooleanAttribute(
                    resolveSerializer, "restrictBackground", this.mRestrictBackground);
            for (int i = 0; i < this.mNetworkPolicy.size(); i++) {
                NetworkPolicy networkPolicy = (NetworkPolicy) this.mNetworkPolicy.valueAt(i);
                NetworkTemplate networkTemplate = networkPolicy.template;
                if (NetworkPolicy.isTemplatePersistable(networkTemplate)) {
                    resolveSerializer.startTag((String) null, "network-policy");
                    XmlUtils.writeIntAttribute(
                            resolveSerializer, "networkTemplate", networkTemplate.getMatchRule());
                    String str =
                            networkTemplate.getSubscriberIds().isEmpty()
                                    ? null
                                    : (String) networkTemplate.getSubscriberIds().iterator().next();
                    if (str != null) {
                        resolveSerializer.attribute((String) null, "subscriberId", str);
                    }
                    XmlUtils.writeIntAttribute(
                            resolveSerializer,
                            "subscriberIdMatchRule",
                            networkTemplate.getSubscriberIds().isEmpty() ? 1 : 0);
                    if (!networkTemplate.getWifiNetworkKeys().isEmpty()) {
                        resolveSerializer.attribute(
                                (String) null,
                                "networkId",
                                (String) networkTemplate.getWifiNetworkKeys().iterator().next());
                    }
                    XmlUtils.writeIntAttribute(
                            resolveSerializer, "templateMetered", networkTemplate.getMeteredness());
                    XmlUtils.writeStringAttribute(
                            resolveSerializer,
                            "cycleStart",
                            RecurrenceRule.convertZonedDateTime(networkPolicy.cycleRule.start));
                    XmlUtils.writeStringAttribute(
                            resolveSerializer,
                            "cycleEnd",
                            RecurrenceRule.convertZonedDateTime(networkPolicy.cycleRule.end));
                    XmlUtils.writeStringAttribute(
                            resolveSerializer,
                            "cyclePeriod",
                            RecurrenceRule.convertPeriod(networkPolicy.cycleRule.period));
                    XmlUtils.writeLongAttribute(
                            resolveSerializer, "warningBytes", networkPolicy.warningBytes);
                    XmlUtils.writeLongAttribute(
                            resolveSerializer, "limitBytes", networkPolicy.limitBytes);
                    XmlUtils.writeLongAttribute(
                            resolveSerializer,
                            "lastWarningSnooze",
                            networkPolicy.lastWarningSnooze);
                    XmlUtils.writeLongAttribute(
                            resolveSerializer, "lastLimitSnooze", networkPolicy.lastLimitSnooze);
                    XmlUtils.writeBooleanAttribute(
                            resolveSerializer, "metered", networkPolicy.metered);
                    XmlUtils.writeBooleanAttribute(
                            resolveSerializer, "inferred", networkPolicy.inferred);
                    resolveSerializer.endTag((String) null, "network-policy");
                }
            }
            for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
                int keyAt = this.mUidPolicy.keyAt(i2);
                int valueAt = this.mUidPolicy.valueAt(i2);
                if (valueAt != 0) {
                    resolveSerializer.startTag((String) null, "uid-policy");
                    XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt);
                    XmlUtils.writeIntAttribute(resolveSerializer, "policy", valueAt);
                    resolveSerializer.endTag((String) null, "uid-policy");
                }
            }
            resolveSerializer.endTag((String) null, "policy-list");
            resolveSerializer.startTag((String) null, "whitelist");
            int size = this.mRestrictBackgroundAllowlistRevokedUids.size();
            for (int i3 = 0; i3 < size; i3++) {
                int keyAt2 = this.mRestrictBackgroundAllowlistRevokedUids.keyAt(i3);
                resolveSerializer.startTag((String) null, "revoked-restrict-background");
                XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt2);
                resolveSerializer.endTag((String) null, "revoked-restrict-background");
            }
            resolveSerializer.endTag((String) null, "whitelist");
            resolveSerializer.endDocument();
            this.mPolicyFile.finishWrite(startWrite);
        } catch (IOException unused2) {
            fileOutputStream = startWrite;
            if (fileOutputStream != null) {
                this.mPolicyFile.failWrite(fileOutputStream);
            }
        }
    }
}
