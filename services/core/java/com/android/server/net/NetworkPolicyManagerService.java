package com.android.server.net;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.IUidObserver;
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
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.INetworkManagementEventObserver;
import android.net.INetworkPolicyListener;
import android.net.INetworkPolicyManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkIdentity;
import android.net.NetworkInfo;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
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
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Message;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.PowerWhitelistManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.IInstalld;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.SubscriptionPlan;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.DataUnit;
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
import android.util.Xml;
import android.widget.Toast;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.StatLogger;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.net.module.util.NetworkIdentityUtils;
import com.android.net.module.util.PermissionUtils;
import com.android.server.EventLogTags;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemConfig;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.connectivity.MultipathPolicyTracker;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.usage.AppStandbyInternal;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
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
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class NetworkPolicyManagerService extends INetworkPolicyManager.Stub {
  public static final int TYPE_LIMIT = 35;
  public static final int TYPE_LIMIT_SNOOZED = 36;
  public static final int TYPE_RAPID = 45;
  public static final int TYPE_WARNING = 34;
  public volatile SparseArray isOffPeakObserverRegisted;
  public int mActiveNetworkType;
  public final ArraySet mActiveNotifs;
  public final IActivityManager mActivityManager;
  public ActivityManagerInternal mActivityManagerInternal;
  public final CountDownLatch mAdminDataAvailableLatch;
  public final INetworkManagementEventObserver mAlertObserver;
  public final SparseBooleanArray mAppIdleTempWhitelistAppIds;
  public final AppOpsManager mAppOps;
  public AppStandbyInternal mAppStandby;
  public CallAttributesListener mCallAttributesListener;
  public final CarrierConfigManager mCarrierConfigManager;
  public BroadcastReceiver mCarrierConfigReceiver;
  public boolean mChargingState;
  public final BroadcastReceiver mChargingStateReceiver;
  public final Clock mClock;
  public ConnectivityManager mConnManager;
  public BroadcastReceiver mConnReceiver;
  public final Context mContext;
  public BroadcastReceiver mDdsChangedReceiver;
  public int mDefaultDataPhoneId;
  public final SparseBooleanArray mDefaultRestrictBackgroundAllowlistUids;
  public final Dependencies mDeps;
  public volatile boolean mDeviceIdleMode;
  public final SparseBooleanArray mFirewallChainStates;
  public final AtomicFile mFirewallPolicyFile;
  public ConcurrentHashMap mForegroundActivitiesPidMap;
  public final Handler mHandler;
  public final Handler.Callback mHandlerCallback;
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
  public final ConnectivityManager.NetworkCallback mNetworkCallback;
  public final INetworkManagementService mNetworkManager;
  public volatile boolean mNetworkManagerReady;
  public final SparseBooleanArray mNetworkMetered;
  public final Object mNetworkPoliciesSecondLock;
  public final ArrayMap mNetworkPolicy;
  public final SparseBooleanArray mNetworkRoaming;
  public NetworkStatsManager mNetworkStats;
  public SparseSetArray mNetworkToIfaces;
  public ContentObserver mOffPeakContentObserver;
  public final ArraySet mOverLimitNotified;
  public final BroadcastReceiver mPackageReceiver;
  public final AtomicFile mPolicyFile;
  public PowerManagerInternal mPowerManagerInternal;
  public final SparseBooleanArray mPowerSaveTempWhitelistAppIds;
  public final SparseBooleanArray mPowerSaveWhitelistAppIds;
  public final SparseBooleanArray mPowerSaveWhitelistExceptIdleAppIds;
  public final BroadcastReceiver mPowerSaveWhitelistReceiver;
  public PowerWhitelistManager mPowerWhitelistManager;
  public volatile boolean mRestrictBackground;
  public final SparseBooleanArray mRestrictBackgroundAllowlistRevokedUids;
  public boolean mRestrictBackgroundBeforeBsm;
  public volatile boolean mRestrictBackgroundChangedInBsm;
  public boolean mRestrictBackgroundLowPowerMode;
  public volatile boolean mRestrictPower;
  public RestrictedModeObserver mRestrictedModeObserver;
  public volatile boolean mRestrictedNetworkingMode;
  public int mSetSubscriptionPlansIdCounter;
  public final SparseIntArray mSetSubscriptionPlansIds;
  public final BroadcastReceiver mSnoozeReceiver;
  public final StatLogger mStatLogger;
  public final StatsCallback mStatsCallback;
  public final SparseArray mSubIdToCarrierConfig;
  public final SparseArray mSubIdToSubscriberId;
  public ConcurrentHashMap mSubscriberIdToSlotId;
  public final SparseLongArray mSubscriptionOpportunisticQuota;
  public final SparseArray mSubscriptionPlans;
  public final SparseArray mSubscriptionPlansOwner;
  public final boolean mSuppressDefaultPolicy;
  public volatile boolean mSystemReady;
  public TelephonyManager mTelephonyManager;
  public final TetheringManager.TetheringEventCallback mTetherListener;
  public long mTetheringNotiSnooze;
  public boolean mTetheringState;
  public ConcurrentHashMap mTetheringWarningBytes;
  public ContentObserver mTetheringWarningObserver;
  public final SparseArray mTmpUidBlockedState;
  public ConcurrentHashMap mToastCheckedUidMap;
  public final SparseArray mUidBlockedState;
  final Handler mUidEventHandler;
  public final Handler.Callback mUidEventHandlerCallback;
  public final ServiceThread mUidEventThread;
  public final SparseIntArray mUidFirewallDozableRules;
  public final SparseIntArray mUidFirewallLowPowerStandbyModeRules;
  public final SparseIntArray mUidFirewallOemDenyRules;
  public final SparseIntArray mUidFirewallPowerSaveRules;
  public final SparseIntArray mUidFirewallRestrictedModeRules;
  public final SparseIntArray mUidFirewallStandbyRules;
  public final IUidObserver mUidObserver;
  public final SparseIntArray mUidPolicy;
  public final BroadcastReceiver mUidRemovedReceiver;
  public final Object mUidRulesFirstLock;
  public final SparseArray mUidState;
  public final SparseArray mUidStateCallbackInfos;
  public UsageStatsManagerInternal mUsageStats;
  public final UserManager mUserManager;
  public final BroadcastReceiver mUserReceiver;
  public boolean mVideoCallLimitAlreadySent;
  public boolean mVideoCallWarningAlreadySent;
  public final BroadcastReceiver mWifiReceiver;
  public long preTotalBytes;
  public static final boolean LOGD = NetworkPolicyLogger.LOGD;
  public static final boolean LOGV = NetworkPolicyLogger.LOGV;
  public static final Object mFirewallPoliciesLock = new Object();
  public static final long QUOTA_UNLIMITED_DEFAULT = DataUnit.MEBIBYTES.toBytes(20);
  public static final SparseIntArray mFirewallRules = new SparseIntArray();
  public static ConcurrentHashMap isOffPeakEnable = new ConcurrentHashMap();
  public static String KEY_UNLIMITED_DATA_PLAN_WARN_SWITCH = "unlimited_data_plan_warn_switch";
  public static String KEY_USAGE_PLAN_LIST = "usage_plan_choose";
  public static String KEY_OFF_PEAK_DATA_START_TIME = "off_peak_start_time";
  public static String KEY_OFF_PEAK_DATA_END_TIME = "off_peak_end_time";
  public static String KEY_OFF_PEAK_DATA_SWITCH = "off_peak_data_switch";
  public static String KEY_IS_IN_OFF_PEAK_TIME = "is_in_off_peak_time";
  public static String KEY_OFF_PEAK_DATA_LIMIT = "off_peak_data_limit";
  public static String KEY_SM_PROVIDER_CONTENT_URI = "content://com.samsung.android.sm.dcapi";
  public static String KEY_SM_EXTRAS_SUBID = "subId";
  public static String KEY_SM_PROVIDER_METHOR_UPDATE_POLICY = "updatePolicyFromSM";
  public static String KEY_SM_PROVIDER_METHOR_UPDATE_ALARM = "updateAlarmFromSM";
  public static String KEY_ONLY_SHOW_LIMIT_ALERT = "only_show_limit_alert";

  public static int getRestrictedModeFirewallRule(int i) {
    return (i & 8) != 0 ? 0 : 1;
  }

  public static boolean isSystem(int i) {
    return i < 10000;
  }

  public final long adjustOffPeakEndTime(long j, long j2, long j3, long j4) {
    if (j2 >= j) {
      return j2;
    }
    long j5 = 86400000;
    long j6 = j2 + j5;
    return j3 + j6 > j4 ? j5 : j6;
  }

  public final long getPlatformDefaultLimitBytes() {
    return -1L;
  }

  public class RestrictedModeObserver extends ContentObserver {
    public final Context mContext;
    public final RestrictedModeListener mListener;

    public interface RestrictedModeListener {
      void onChange(boolean z);
    }

    public RestrictedModeObserver(Context context, RestrictedModeListener restrictedModeListener) {
      super(null);
      this.mContext = context;
      this.mListener = restrictedModeListener;
      context
          .getContentResolver()
          .registerContentObserver(
              Settings.Global.getUriFor("restricted_networking_mode"), false, this);
    }

    public boolean isRestrictedModeEnabled() {
      return Settings.Global.getInt(
              this.mContext.getContentResolver(), "restricted_networking_mode", 0)
          != 0;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      this.mListener.onChange(isRestrictedModeEnabled());
    }
  }

  public NetworkPolicyManagerService(
      Context context,
      IActivityManager iActivityManager,
      INetworkManagementService iNetworkManagementService) {
    this(
        context,
        iActivityManager,
        iNetworkManagementService,
        AppGlobals.getPackageManager(),
        getDefaultClock(),
        getDefaultSystemDir(),
        false,
        new Dependencies(context));
  }

  public static File getDefaultSystemDir() {
    return new File(Environment.getDataDirectory(), "system");
  }

  public static Clock getDefaultClock() {
    return new BestClock(
        ZoneOffset.UTC, new Clock[] {SystemClock.currentNetworkTimeClock(), Clock.systemUTC()});
  }

  public class Dependencies {
    public final Context mContext;
    public final NetworkStatsManager mNetworkStatsManager;

    public Dependencies(Context context) {
      this.mContext = context;
      NetworkStatsManager networkStatsManager =
          (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
      this.mNetworkStatsManager = networkStatsManager;
      networkStatsManager.setPollOnOpen(false);
    }

    public long getNetworkTotalBytes(NetworkTemplate networkTemplate, long j, long j2) {
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

    public List getNetworkUidBytes(NetworkTemplate networkTemplate, long j, long j2) {
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

  /* JADX WARN: Multi-variable type inference failed */
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
    this.isOffPeakObserverRegisted = new SparseArray();
    this.mDefaultDataPhoneId = 0;
    this.mSubscriberIdToSlotId = new ConcurrentHashMap();
    this.mTetheringWarningBytes = new ConcurrentHashMap();
    this.mTetheringState = false;
    this.mStatLogger =
        new StatLogger(new String[] {"updateNetworkEnabledNL()", "isUidNetworkingBlocked()"});
    this.mUidObserver =
        new UidObserver() { // from class: com.android.server.net.NetworkPolicyManagerService.4
          public void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (NetworkPolicyManagerService.this.mUidStateCallbackInfos) {
              UidStateCallbackInfo uidStateCallbackInfo =
                  (UidStateCallbackInfo)
                      NetworkPolicyManagerService.this.mUidStateCallbackInfos.get(i);
              if (uidStateCallbackInfo == null) {
                uidStateCallbackInfo = new UidStateCallbackInfo();
                NetworkPolicyManagerService.this.mUidStateCallbackInfos.put(
                    i, uidStateCallbackInfo);
              }
              UidStateCallbackInfo uidStateCallbackInfo2 = uidStateCallbackInfo;
              long j2 = uidStateCallbackInfo2.procStateSeq;
              if (j2 == -1 || j > j2) {
                uidStateCallbackInfo2.update(i, i2, j, i3);
              }
              if (!uidStateCallbackInfo2.isPending) {
                NetworkPolicyManagerService.this
                    .mUidEventHandler
                    .obtainMessage(100, uidStateCallbackInfo2)
                    .sendToTarget();
                uidStateCallbackInfo2.isPending = true;
              }
            }
          }

          public void onUidGone(int i, boolean z2) {
            NetworkPolicyManagerService.this
                .mUidEventHandler
                .obtainMessage(101, i, 0)
                .sendToTarget();
          }
        };
    this.mPowerSaveWhitelistReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.5
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
              NetworkPolicyManagerService.this.updatePowerSaveWhitelistUL();
              NetworkPolicyManagerService.this.updateRulesForRestrictPowerUL();
              NetworkPolicyManagerService.this.updateRulesForAppIdleUL();
            }
          }
        };
    this.mPackageReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.6
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra != -1 && "android.intent.action.PACKAGE_ADDED".equals(action)) {
              if (NetworkPolicyManagerService.LOGV) {
                Slog.v("NetworkPolicy", "ACTION_PACKAGE_ADDED for uid=" + intExtra);
              }
              synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                NetworkPolicyManagerService.this.mInternetPermissionMap.delete(intExtra);
                NetworkPolicyManagerService.this.updateRestrictionRulesForUidUL(intExtra);
              }
            }
          }
        };
    this.mUidRemovedReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.7
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra == -1) {
              return;
            }
            if (NetworkPolicyManagerService.LOGV) {
              Slog.v("NetworkPolicy", "ACTION_UID_REMOVED for uid=" + intExtra);
            }
            synchronized (NetworkPolicyManagerService.mFirewallPoliciesLock) {
              NetworkPolicyManagerService.this.removeFirewallPolicyNL(intExtra);
            }
            synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
              NetworkPolicyManagerService.this.onUidDeletedUL(intExtra);
              synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                NetworkPolicyManagerService.this.writePolicyAL();
              }
            }
          }
        };
    this.mUserReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.8
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            if (intExtra == -1) {
              return;
            }
            action.hashCode();
            if (action.equals("android.intent.action.USER_REMOVED")
                || action.equals("android.intent.action.USER_ADDED")) {
              synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                NetworkPolicyManagerService.this.removeUserStateUL(intExtra, true, false);
                NetworkPolicyManagerService.this.mMeteredRestrictedUids.remove(intExtra);
                if (action == "android.intent.action.USER_ADDED") {
                  NetworkPolicyManagerService.this.addDefaultRestrictBackgroundAllowlistUidsUL(
                      intExtra);
                }
                synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                  NetworkPolicyManagerService.this.updateRulesForGlobalChangeAL(true);
                }
              }
            }
          }
        };
    this.mStatsCallback = new StatsCallback();
    this.mSnoozeReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.9
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            NetworkTemplate networkTemplate =
                (NetworkTemplate)
                    intent.getParcelableExtra(
                        "android.net.NETWORK_TEMPLATE", NetworkTemplate.class);
            if ("com.android.server.net.action.SNOOZE_WARNING".equals(intent.getAction())) {
              NetworkPolicyManagerService.this.performSnooze(networkTemplate, 34);
              return;
            }
            if ("com.android.server.net.action.SNOOZE_RAPID".equals(intent.getAction())) {
              NetworkPolicyManagerService.this.performSnooze(networkTemplate, 45);
              return;
            }
            if ("com.android.server.net.action.SNOOZE_TETHERING_WARNING"
                .equals(intent.getAction())) {
              NetworkPolicyManagerService networkPolicyManagerService =
                  NetworkPolicyManagerService.this;
              networkPolicyManagerService.mTetheringNotiSnooze =
                  networkPolicyManagerService.mClock.millis();
              Message obtainMessage = NetworkPolicyManagerService.this.mHandler.obtainMessage(1007);
              obtainMessage.arg1 = 0;
              obtainMessage.sendToTarget();
            }
          }
        };
    this.mWifiReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.10
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            NetworkPolicyManagerService.this.upgradeWifiMeteredOverride();
            NetworkPolicyManagerService.this.mContext.unregisterReceiver(this);
          }
        };
    this.mNetworkCallback =
        new ConnectivityManager
            .NetworkCallback() { // from class:
                                 // com.android.server.net.NetworkPolicyManagerService.11
          /* JADX WARN: Code restructure failed: missing block: B:17:0x0039, code lost:

             r6.this$0.mLogger.meterednessChanged(r7.getNetId(), r1);
          */
          @Override // android.net.ConnectivityManager.NetworkCallback
          /*
              Code decompiled incorrectly, please refer to instructions dump.
          */
          public void onCapabilitiesChanged(
              Network network, NetworkCapabilities networkCapabilities) {
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
              boolean z2 = true;
              boolean z3 = !networkCapabilities.hasCapability(11);
              boolean updateCapabilityChange =
                  NetworkPolicyManagerService.updateCapabilityChange(
                      NetworkPolicyManagerService.this.mNetworkMetered, z3, network);
              boolean z4 = !networkCapabilities.hasCapability(18);
              boolean updateCapabilityChange2 =
                  NetworkPolicyManagerService.updateCapabilityChange(
                      NetworkPolicyManagerService.this.mNetworkRoaming, z4, network);
              if (!updateCapabilityChange && !updateCapabilityChange2) {
                z2 = false;
              }
              if (updateCapabilityChange2) {
                NetworkPolicyManagerService.this.mLogger.roamingChanged(network.getNetId(), z4);
              }
              if (z2) {
                NetworkPolicyManagerService.this.updateNetworkRulesNL();
              }
            }
          }

          @Override // android.net.ConnectivityManager.NetworkCallback
          public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
              ArraySet arraySet = new ArraySet(linkProperties.getAllInterfaceNames());
              if (NetworkPolicyManagerService.this.updateNetworkToIfacesNL(
                  network.getNetId(), arraySet)) {
                NetworkPolicyManagerService.this.mLogger.interfacesChanged(
                    network.getNetId(), arraySet);
                NetworkPolicyManagerService.this.updateNetworkRulesNL();
              }
            }
          }

          @Override // android.net.ConnectivityManager.NetworkCallback
          public void onLost(Network network) {
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
              NetworkPolicyManagerService.this.mNetworkToIfaces.remove(network.getNetId());
            }
          }
        };
    this.mAlertObserver =
        new BaseNetworkObserver() { // from class:
                                    // com.android.server.net.NetworkPolicyManagerService.12
          public void limitReached(String str, String str2) {
            NetworkStack.checkNetworkStackPermission(NetworkPolicyManagerService.this.mContext);
            if ("globalAlert".equals(str)) {
              return;
            }
            NetworkPolicyManagerService.this.mHandler.obtainMessage(5, str2).sendToTarget();
          }
        };
    this.mConnReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.13
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            NetworkPolicyManagerService.this.updateNetworksInternal();
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo != null && networkInfo.isConnected()) {
              NetworkPolicyManagerService.this.mActiveNetworkType = networkInfo.getType();
            } else {
              NetworkPolicyManagerService.this.mActiveNetworkType = -1;
            }
            Slog.d(
                "NetworkPolicy",
                "mActiveNetworkType : "
                    + NetworkPolicyManagerService.this.mActiveNetworkType
                    + ", networkInfo : "
                    + networkInfo);
          }
        };
    this.mCarrierConfigReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.14
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if (intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
              int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
              int intExtra2 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
              Slog.v(
                  "NetworkPolicy",
                  "mCarrierConfigReceiver() - subId: " + intExtra + ", slotId: " + intExtra2);
              NetworkPolicyManagerService.this.updateSubscriptions();
              synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                  String str =
                      (String)
                          NetworkPolicyManagerService.this.mSubIdToSubscriberId.get(intExtra, null);
                  if (str != null) {
                    NetworkPolicyManagerService.this.ensureActiveCarrierPolicyAL(intExtra, str);
                    NetworkPolicyManagerService.this.maybeUpdateCarrierPolicyCycleAL(intExtra, str);
                    NetworkPolicyManagerService.this.mSubscriberIdToSlotId.put(
                        str, Integer.valueOf(intExtra2));
                  } else {
                    Slog.wtf("NetworkPolicy", "Missing subscriberId for subId " + intExtra);
                  }
                  if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {
                    NetworkPolicyManagerService.this.checkToInitOffPeakConfig(intExtra);
                  }
                  NetworkPolicyManagerService.this.handleNetworkPoliciesUpdateAL(true);
                }
              }
            }
          }
        };
    Handler.Callback callback =
        new Handler
            .Callback() { // from class: com.android.server.net.NetworkPolicyManagerService.15
          /* JADX WARN: Multi-variable type inference failed */
          @Override // android.os.Handler.Callback
          public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
              int i2 = message.arg1;
              int i3 = message.arg2;
              if (NetworkPolicyManagerService.LOGV) {
                Slog.v(
                    "NetworkPolicy",
                    "Dispatching rules="
                        + NetworkPolicyManager.uidRulesToString(i3)
                        + " for uid="
                        + i2);
              }
              int beginBroadcast = NetworkPolicyManagerService.this.mListeners.beginBroadcast();
              for (int i4 = 0; i4 < beginBroadcast; i4++) {
                NetworkPolicyManagerService.this.dispatchUidRulesChanged(
                    NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i4), i2, i3);
              }
              NetworkPolicyManagerService.this.mListeners.finishBroadcast();
              return true;
            }
            if (i == 2) {
              String[] strArr = (String[]) message.obj;
              int beginBroadcast2 = NetworkPolicyManagerService.this.mListeners.beginBroadcast();
              for (int i5 = 0; i5 < beginBroadcast2; i5++) {
                NetworkPolicyManagerService.this.dispatchMeteredIfacesChanged(
                    NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i5), strArr);
              }
              NetworkPolicyManagerService.this.mListeners.finishBroadcast();
              return true;
            }
            if (i == 5) {
              String str = (String) message.obj;
              synchronized (NetworkPolicyManagerService.this.mMeteredIfacesLock) {
                if (!NetworkPolicyManagerService.this.mMeteredIfaces.contains(str)) {
                  return true;
                }
                NetworkPolicyManagerService.this.mNetworkStats.forceUpdate();
                synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                  NetworkPolicyManagerService.this.updateNetworkRulesNL();
                  NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                  NetworkPolicyManagerService.this.updateNotificationsNL();
                }
                return true;
              }
            }
            if (i == 6) {
              boolean z2 = message.arg1 != 0;
              int beginBroadcast3 = NetworkPolicyManagerService.this.mListeners.beginBroadcast();
              for (int i6 = 0; i6 < beginBroadcast3; i6++) {
                NetworkPolicyManagerService.this.dispatchRestrictBackgroundChanged(
                    NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i6), z2);
              }
              NetworkPolicyManagerService.this.mListeners.finishBroadcast();
              Intent intent = new Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
              intent.setFlags(1073741824);
              NetworkPolicyManagerService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
              return true;
            }
            if (i == 7) {
              NetworkPolicyManagerService.this.mNetworkStats.setDefaultGlobalAlert(
                  ((Long) message.obj).longValue() / 1000);
              return true;
            }
            if (i == 10) {
              IfaceQuotas ifaceQuotas = (IfaceQuotas) message.obj;
              NetworkPolicyManagerService.this.removeInterfaceLimit(ifaceQuotas.iface);
              NetworkPolicyManagerService.this.setInterfaceLimit(
                  ifaceQuotas.iface, ifaceQuotas.limit);
              NetworkPolicyManagerService.this.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                  ifaceQuotas.iface, ifaceQuotas.warning, ifaceQuotas.limit);
              return true;
            }
            if (i == 11) {
              String str2 = (String) message.obj;
              NetworkPolicyManagerService.this.removeInterfaceLimit(str2);
              NetworkPolicyManagerService.this.mNetworkStats.setStatsProviderWarningAndLimitAsync(
                  str2, -1L, -1L);
              return true;
            }
            if (i == 13) {
              int i7 = message.arg1;
              int i8 = message.arg2;
              Boolean bool = (Boolean) message.obj;
              int beginBroadcast4 = NetworkPolicyManagerService.this.mListeners.beginBroadcast();
              for (int i9 = 0; i9 < beginBroadcast4; i9++) {
                NetworkPolicyManagerService.this.dispatchUidPoliciesChanged(
                    NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i9), i7, i8);
              }
              NetworkPolicyManagerService.this.mListeners.finishBroadcast();
              if (bool.booleanValue()) {
                NetworkPolicyManagerService.this.broadcastRestrictBackgroundChanged(i7, bool);
              }
              return true;
            }
            if (i == 51) {
              NetworkPolicyManagerService.this.handleCheckFireWallPermission(
                  message.arg1, message.arg2);
              return true;
            }
            if (i == 1005) {
              if (NetworkPolicyManagerService.this.changePowerSaveMode()) {
                synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                  NetworkPolicyManagerService.this.updatePowerSaveWhitelistUL();
                  NetworkPolicyManagerService.this.updateRulesForRestrictPowerUL();
                  NetworkPolicyManagerService.this.updateRulesForAppIdleUL();
                }
              }
              return true;
            }
            if (i != 1007) {
              switch (i) {
                case 15:
                  NetworkPolicyManagerService.this.resetUidFirewallRules(message.arg1);
                  return true;
                case 16:
                  SomeArgs someArgs = (SomeArgs) message.obj;
                  int intValue = ((Integer) someArgs.arg1).intValue();
                  int intValue2 = ((Integer) someArgs.arg2).intValue();
                  int intValue3 = ((Integer) someArgs.arg3).intValue();
                  int[] iArr = (int[]) someArgs.arg4;
                  int beginBroadcast5 =
                      NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                  for (int i10 = 0; i10 < beginBroadcast5; i10++) {
                    NetworkPolicyManagerService.this.dispatchSubscriptionOverride(
                        NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i10),
                        intValue,
                        intValue2,
                        intValue3,
                        iArr);
                  }
                  NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                  return true;
                case 17:
                  NetworkPolicyManagerService.this.setMeteredRestrictedPackagesInternal(
                      (Set) message.obj, message.arg1);
                  return true;
                case 18:
                  NetworkPolicyManagerService.this.setNetworkTemplateEnabledInner(
                      (NetworkTemplate) message.obj, message.arg1 != 0);
                  return true;
                case 19:
                  SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) message.obj;
                  int i11 = message.arg1;
                  int beginBroadcast6 =
                      NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                  for (int i12 = 0; i12 < beginBroadcast6; i12++) {
                    NetworkPolicyManagerService.this.dispatchSubscriptionPlansChanged(
                        NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i12),
                        i11,
                        subscriptionPlanArr);
                  }
                  NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                  return true;
                case 20:
                  NetworkPolicyManagerService.this.mNetworkStats.forceUpdate();
                  synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    NetworkPolicyManagerService.this.updateNetworkRulesNL();
                    NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                    NetworkPolicyManagerService.this.updateNotificationsNL();
                  }
                  return true;
                case 21:
                  int i13 = message.arg1;
                  int i14 = message.arg2;
                  int intValue4 = ((Integer) message.obj).intValue();
                  int beginBroadcast7 =
                      NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                  for (int i15 = 0; i15 < beginBroadcast7; i15++) {
                    NetworkPolicyManagerService.this.dispatchBlockedReasonChanged(
                        NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i15),
                        i13,
                        intValue4,
                        i14);
                  }
                  NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                  return true;
                case 22:
                  synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                      int i16 = message.arg1;
                      if (message.arg2
                          == NetworkPolicyManagerService.this.mSetSubscriptionPlansIds.get(i16)) {
                        if (NetworkPolicyManagerService.LOGD) {
                          Slog.d("NetworkPolicy", "Clearing expired subscription plans.");
                        }
                        NetworkPolicyManagerService.this.setSubscriptionPlansInternal(
                            i16, new SubscriptionPlan[0], 0L, (String) message.obj);
                      } else if (NetworkPolicyManagerService.LOGD) {
                        Slog.d("NetworkPolicy", "Ignoring stale CLEAR_SUBSCRIPTION_PLANS.");
                      }
                    }
                  }
                  return true;
                case 23:
                  SparseArray sparseArray = (SparseArray) message.obj;
                  int size = sparseArray.size();
                  int beginBroadcast8 =
                      NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                  for (int i17 = 0; i17 < beginBroadcast8; i17++) {
                    INetworkPolicyListener broadcastItem =
                        NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i17);
                    for (int i18 = 0; i18 < size; i18++) {
                      int keyAt = sparseArray.keyAt(i18);
                      SomeArgs someArgs2 = (SomeArgs) sparseArray.valueAt(i18);
                      int i19 = someArgs2.argi1;
                      int i20 = someArgs2.argi2;
                      int i21 = someArgs2.argi3;
                      NetworkPolicyManagerService.this.dispatchBlockedReasonChanged(
                          broadcastItem, keyAt, i19, i20);
                      if (NetworkPolicyManagerService.LOGV) {
                        Slog.v(
                            "NetworkPolicy",
                            "Dispatching rules="
                                + NetworkPolicyManager.uidRulesToString(i21)
                                + " for uid="
                                + keyAt);
                      }
                      NetworkPolicyManagerService.this.dispatchUidRulesChanged(
                          broadcastItem, keyAt, i21);
                    }
                  }
                  NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                  for (int i22 = 0; i22 < size; i22++) {
                    ((SomeArgs) sparseArray.valueAt(i22)).recycle();
                  }
                  return true;
                default:
                  return false;
              }
            }
            Object[] objArr = message.arg1 != 0;
            NetworkPolicyManagerService.this.mTetheringWarningBytes.put(
                0,
                Long.valueOf(
                    NetworkPolicyManagerService.this.getTetheringWarningBytes(
                        "tethering_data_warning_sim_slot_0")));
            NetworkPolicyManagerService.this.mTetheringWarningBytes.put(
                1,
                Long.valueOf(
                    NetworkPolicyManagerService.this.getTetheringWarningBytes(
                        "tethering_data_warning_sim_slot_1")));
            Slog.v(
                "NetworkPolicy",
                "MSG_CHECK_TETHERING_WARNING_CHANGED() - mTetheringWarningBytes[0]: "
                    + NetworkPolicyManagerService.this.mTetheringWarningBytes.getOrDefault(0, -1L)
                    + ", mTetheringWarningBytes[1]: "
                    + NetworkPolicyManagerService.this.mTetheringWarningBytes.getOrDefault(1, -1L));
            if (objArr != false) {
              NetworkPolicyManagerService.this.mTetheringNotiSnooze = -1L;
            }
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
              NetworkPolicyManagerService.this.updateNotificationsNL();
            }
            return true;
          }
        };
    this.mHandlerCallback = callback;
    Handler.Callback callback2 =
        new Handler
            .Callback() { // from class: com.android.server.net.NetworkPolicyManagerService.16
          @Override // android.os.Handler.Callback
          public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 100) {
              NetworkPolicyManagerService.this.handleUidChanged((UidStateCallbackInfo) message.obj);
              return true;
            }
            if (i != 101) {
              return false;
            }
            NetworkPolicyManagerService.this.handleUidGone(message.arg1);
            return true;
          }
        };
    this.mUidEventHandlerCallback = callback2;
    this.mChargingStateReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.17
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            boolean z2;
            boolean z3 =
                NetworkPolicyManagerService.this.mPowerManagerInternal.getLowPowerState(6)
                    .batterySaverEnabled;
            if (intent.getAction().equals("android.os.action.CHARGING")) {
              z2 = true;
            } else {
              intent.getAction().equals("android.os.action.DISCHARGING");
              z2 = false;
            }
            Slog.d(
                "NetworkPolicy",
                "ChargingState: new("
                    + z2
                    + "), old("
                    + NetworkPolicyManagerService.this.mChargingState
                    + ")");
            if (z2 != NetworkPolicyManagerService.this.mChargingState) {
              NetworkPolicyManagerService.this.mChargingState = z2;
              if (z3) {
                NetworkPolicyManagerService.this.mHandler.obtainMessage(1005).sendToTarget();
              }
            }
          }
        };
    this.mTetherListener =
        new TetheringManager
            .TetheringEventCallback() { // from class:
                                        // com.android.server.net.NetworkPolicyManagerService.18
          public void onUpstreamChanged(Network network) {
            Log.d("NetworkPolicy", "onUpstreamChanged() network : " + network);
            NetworkCapabilities networkCapabilities =
                NetworkPolicyManagerService.this.mConnManager.getNetworkCapabilities(network);
            Message obtainMessage = NetworkPolicyManagerService.this.mHandler.obtainMessage(1007);
            if (networkCapabilities != null && networkCapabilities.hasTransport(0)) {
              obtainMessage.arg1 = !NetworkPolicyManagerService.this.mTetheringState ? 1 : 0;
              NetworkPolicyManagerService.this.mTetheringState = true;
            } else {
              obtainMessage.arg1 = NetworkPolicyManagerService.this.mTetheringState ? 1 : 0;
              NetworkPolicyManagerService.this.mTetheringState = false;
            }
            obtainMessage.sendToTarget();
          }
        };
    this.mDdsChangedReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.net.NetworkPolicyManagerService.20
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            int intExtra = intent.getIntExtra("phone", 0);
            Slog.v(
                "NetworkPolicy",
                "mDdsChangedReceiver() - phoneId: "
                    + intExtra
                    + ", mDefaultDataPhoneId: "
                    + NetworkPolicyManagerService.this.mDefaultDataPhoneId);
            if (NetworkPolicyManagerService.this.mDefaultDataPhoneId == intExtra) {
              return;
            }
            NetworkPolicyManagerService.this.mDefaultDataPhoneId = intExtra;
          }
        };
    Objects.requireNonNull(context, "missing context");
    this.mContext = context;
    Objects.requireNonNull(iActivityManager, "missing activityManager");
    this.mActivityManager = iActivityManager;
    Objects.requireNonNull(iNetworkManagementService, "missing networkManagement");
    this.mNetworkManager = iNetworkManagementService;
    this.mPowerWhitelistManager =
        (PowerWhitelistManager) context.getSystemService(PowerWhitelistManager.class);
    Objects.requireNonNull(clock, "missing Clock");
    this.mClock = clock;
    this.mUserManager = (UserManager) context.getSystemService("user");
    this.mCarrierConfigManager =
        (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
    this.mIPm = iPackageManager;
    HandlerThread handlerThread = new HandlerThread("NetworkPolicy");
    handlerThread.start();
    Handler handler = new Handler(handlerThread.getLooper(), callback);
    this.mHandler = handler;
    ServiceThread serviceThread = new ServiceThread("NetworkPolicy.uid", -2, false);
    this.mUidEventThread = serviceThread;
    serviceThread.start();
    this.mUidEventHandler = new Handler(serviceThread.getLooper(), callback2);
    this.mSuppressDefaultPolicy = z;
    Objects.requireNonNull(dependencies, "missing Dependencies");
    this.mDeps = dependencies;
    this.mPolicyFile = new AtomicFile(new File(file, "netpolicy.xml"), "net-policy");
    this.mFirewallPolicyFile =
        new AtomicFile(new File(file, "firewallpolicy.xml"), "firewall-policy");
    this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    this.mNetworkStats = (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
    this.mMultipathPolicyTracker = new MultipathPolicyTracker(context, handler);
    LocalServices.addService(
        NetworkPolicyManagerInternal.class, new NetworkPolicyManagerInternalImpl());
  }

  public void bindConnectivityManager() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
    Objects.requireNonNull(connectivityManager, "missing ConnectivityManager");
    this.mConnManager = connectivityManager;
  }

  public final void updatePowerSaveWhitelistUL() {
    int[] whitelistedAppIds = this.mPowerWhitelistManager.getWhitelistedAppIds(false);
    this.mPowerSaveWhitelistExceptIdleAppIds.clear();
    for (int i : whitelistedAppIds) {
      this.mPowerSaveWhitelistExceptIdleAppIds.put(i, true);
    }
    int[] whitelistedAppIds2 = this.mPowerWhitelistManager.getWhitelistedAppIds(true);
    this.mPowerSaveWhitelistAppIds.clear();
    for (int i2 : whitelistedAppIds2) {
      this.mPowerSaveWhitelistAppIds.put(i2, true);
    }
  }

  public boolean addDefaultRestrictBackgroundAllowlistUidsUL() {
    List users = this.mUserManager.getUsers();
    int size = users.size();
    boolean z = false;
    for (int i = 0; i < size; i++) {
      z = addDefaultRestrictBackgroundAllowlistUidsUL(((UserInfo) users.get(i)).id) || z;
    }
    return z;
  }

  public final boolean addDefaultRestrictBackgroundAllowlistUidsUL(int i) {
    SystemConfig systemConfig = SystemConfig.getInstance();
    PackageManager packageManager = this.mContext.getPackageManager();
    ArraySet allowInDataUsageSave = systemConfig.getAllowInDataUsageSave();
    boolean z = false;
    for (int i2 = 0; i2 < allowInDataUsageSave.size(); i2++) {
      String str = (String) allowInDataUsageSave.valueAt(i2);
      boolean z2 = LOGD;
      if (z2) {
        Slog.d(
            "NetworkPolicy",
            "checking restricted background exemption for package " + str + " and user " + i);
      }
      try {
        ApplicationInfo applicationInfoAsUser =
            packageManager.getApplicationInfoAsUser(str, 1048576, i);
        if (!applicationInfoAsUser.isPrivilegedApp()) {
          Slog.e(
              "NetworkPolicy",
              "addDefaultRestrictBackgroundAllowlistUidsUL(): skipping non-privileged app  " + str);
        }
        int uid = UserHandle.getUid(i, applicationInfoAsUser.uid);
        if (uid != 0) {
          this.mDefaultRestrictBackgroundAllowlistUids.append(uid, true);
          if (z2) {
            Slog.d(
                "NetworkPolicy",
                "Adding uid "
                    + uid
                    + " (user "
                    + i
                    + ") to default restricted background allowlist. Revoked status: "
                    + this.mRestrictBackgroundAllowlistRevokedUids.get(uid));
          }
          if (!this.mRestrictBackgroundAllowlistRevokedUids.get(uid)) {
            if (z2) {
              Slog.d(
                  "NetworkPolicy",
                  "adding default package "
                      + str
                      + " (uid "
                      + uid
                      + " for user "
                      + i
                      + ") to restrict background allowlist");
            }
            setUidPolicyUncheckedUL(uid, 4, false);
            z = true;
          }
        }
      } catch (PackageManager.NameNotFoundException unused) {
        if (LOGD) {
          Slog.d("NetworkPolicy", "No ApplicationInfo for package " + str);
        }
      }
    }
    return z;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* renamed from: initService, reason: merged with bridge method [inline-methods] */
  public final void lambda$networkScoreAndNetworkManagementServiceReady$1(
      CountDownLatch countDownLatch) {
    Trace.traceBegin(2097152L, "systemReady");
    int threadPriority = Process.getThreadPriority(Process.myTid());
    try {
      Process.setThreadPriority(-2);
      if (!isBandwidthControlEnabled()) {
        Slog.w("NetworkPolicy", "bandwidth controls disabled, unable to enforce policy");
        return;
      }
      try {
        INetworkManagementService iNetworkManagementService = this.mNetworkManager;
        if (iNetworkManagementService != null) {
          iNetworkManagementService.buildFirewall();
        }
      } catch (Exception e) {
        Log.wtf("NetworkPolicy", "buildFirewall exception", e);
      }
      if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {
        initOffPeakContentObserver();
      }
      this.mUsageStats =
          (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
      this.mAppStandby = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
      this.mActivityManagerInternal =
          (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
      registerProcessListener();
      synchronized (this.mUidRulesFirstLock) {
        synchronized (this.mNetworkPoliciesSecondLock) {
          updatePowerSaveWhitelistUL();
          PowerManagerInternal powerManagerInternal =
              (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
          this.mPowerManagerInternal = powerManagerInternal;
          powerManagerInternal.registerLowPowerModeObserver(
              new PowerManagerInternal
                  .LowPowerModeListener() { // from class:
                                            // com.android.server.net.NetworkPolicyManagerService.1
                public int getServiceType() {
                  return 6;
                }

                public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                  boolean z = powerSaveState.batterySaverEnabled;
                  if (NetworkPolicyManagerService.LOGD) {
                    Slog.d("NetworkPolicy", "onLowPowerModeChanged(" + z + ")");
                  }
                  synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    if (NetworkPolicyManagerService.this.mRestrictPower != z) {
                      NetworkPolicyManagerService.this.mRestrictPower = z;
                      NetworkPolicyManagerService.this.updateRulesForRestrictPowerUL();
                    }
                  }
                }
              });
          this.mRestrictPower = this.mPowerManagerInternal.getLowPowerState(6).batterySaverEnabled;
          RestrictedModeObserver restrictedModeObserver =
              new RestrictedModeObserver(
                  this.mContext,
                  new RestrictedModeObserver
                      .RestrictedModeListener() { // from class:
                                                  // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda10
                    @Override // com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver.RestrictedModeListener
                    public final void onChange(boolean z) {
                      NetworkPolicyManagerService.this.lambda$initService$0(z);
                    }
                  });
          this.mRestrictedModeObserver = restrictedModeObserver;
          this.mRestrictedNetworkingMode = restrictedModeObserver.isRestrictedModeEnabled();
          this.mSystemReady = true;
          waitForAdminData();
          readPolicyAL();
          this.mRestrictBackgroundBeforeBsm = this.mLoadedRestrictBackground;
          boolean z = this.mPowerManagerInternal.getLowPowerState(10).batterySaverEnabled;
          this.mRestrictBackgroundLowPowerMode = z;
          if (z && !this.mLoadedRestrictBackground) {
            this.mLoadedRestrictBackground = true;
          }
          this.mPowerManagerInternal.registerLowPowerModeObserver(
              new PowerManagerInternal
                  .LowPowerModeListener() { // from class:
                                            // com.android.server.net.NetworkPolicyManagerService.2
                public int getServiceType() {
                  return 10;
                }

                public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                  synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    NetworkPolicyManagerService.this.updateRestrictBackgroundByLowPowerModeUL(
                        powerSaveState);
                  }
                }
              });
          if (addDefaultRestrictBackgroundAllowlistUidsUL()) {
            writePolicyAL();
          }
          setRestrictBackgroundUL(this.mLoadedRestrictBackground, "init_service");
          updateRulesForGlobalChangeAL(false);
          updateNotificationsNL();
        }
      }
      synchronized (mFirewallPoliciesLock) {
        readFirewallPolicyAL();
      }
      try {
        this.mActivityManagerInternal.registerNetworkPolicyUidObserver(
            this.mUidObserver, 35, 5, "android");
        this.mNetworkManager.registerObserver(this.mAlertObserver);
      } catch (RemoteException unused) {
      }
      this.mContext.registerReceiver(
          this.mPowerSaveWhitelistReceiver,
          new IntentFilter("android.os.action.POWER_SAVE_WHITELIST_CHANGED"),
          null,
          this.mHandler);
      this.mContext.registerReceiver(
          this.mConnReceiver,
          new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"),
          "android.permission.NETWORK_STACK",
          this.mHandler);
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter.addDataScheme("package");
      this.mContext.registerReceiverForAllUsers(
          this.mPackageReceiver, intentFilter, null, this.mHandler);
      this.mContext.registerReceiverForAllUsers(
          this.mUidRemovedReceiver,
          new IntentFilter("android.intent.action.UID_REMOVED"),
          null,
          this.mHandler);
      IntentFilter intentFilter2 = new IntentFilter();
      intentFilter2.addAction("android.intent.action.USER_ADDED");
      intentFilter2.addAction("android.intent.action.USER_REMOVED");
      this.mContext.registerReceiver(this.mUserReceiver, intentFilter2, null, this.mHandler);
      Executor handlerExecutor = new HandlerExecutor(this.mHandler);
      this.mNetworkStats.registerUsageCallback(
          new NetworkTemplate.Builder(1).build(), 0L, handlerExecutor, this.mStatsCallback);
      this.mNetworkStats.registerUsageCallback(
          new NetworkTemplate.Builder(4).build(), 0L, handlerExecutor, this.mStatsCallback);
      this.mContext.registerReceiver(
          this.mSnoozeReceiver,
          new IntentFilter("com.android.server.net.action.SNOOZE_WARNING"),
          "android.permission.MANAGE_NETWORK_POLICY",
          this.mHandler);
      this.mContext.registerReceiver(
          this.mSnoozeReceiver,
          new IntentFilter("com.android.server.net.action.SNOOZE_RAPID"),
          "android.permission.MANAGE_NETWORK_POLICY",
          this.mHandler);
      this.mContext.registerReceiver(
          this.mWifiReceiver,
          new IntentFilter("android.net.wifi.CONFIGURED_NETWORKS_CHANGE"),
          null,
          this.mHandler);
      this.mContext.registerReceiver(
          this.mCarrierConfigReceiver,
          new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"),
          null,
          this.mHandler);
      this.mConnManager.registerNetworkCallback(
          new NetworkRequest.Builder().build(), this.mNetworkCallback);
      this.mAppStandby.addListener(new NetPolicyAppIdleStateChangeListener());
      synchronized (this.mUidRulesFirstLock) {
        updateRulesForAppIdleParoleUL();
      }
      ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
          .addOnSubscriptionsChangedListener(
              new HandlerExecutor(this.mHandler),
              new SubscriptionManager
                  .OnSubscriptionsChangedListener() { // from class:
                                                      // com.android.server.net.NetworkPolicyManagerService.3
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public void onSubscriptionsChanged() {
                  NetworkPolicyManagerService.this.updateNetworksInternal();
                }
              });
      countDownLatch.countDown();
      IntentFilter intentFilter3 = new IntentFilter();
      intentFilter3.addAction("android.os.action.CHARGING");
      intentFilter3.addAction("android.os.action.DISCHARGING");
      this.mContext.registerReceiver(this.mChargingStateReceiver, intentFilter3);
      this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
      this.mCallAttributesListener = new CallAttributesListener();
      TelephonyManager telephonyManager = this.mTelephonyManager;
      if (telephonyManager != null) {
        telephonyManager.registerTelephonyCallback(
            new HandlerExecutor(this.mHandler), this.mCallAttributesListener);
      }
      this.preTotalBytes = 0L;
      if (isKorOperator()) {
        initTetheringWarningObserver();
        this.mContext
            .getContentResolver()
            .registerContentObserver(
                Settings.Global.getUriFor("tethering_data_warning_sim_slot_0"),
                true,
                this.mTetheringWarningObserver);
        this.mContext
            .getContentResolver()
            .registerContentObserver(
                Settings.Global.getUriFor("tethering_data_warning_sim_slot_1"),
                true,
                this.mTetheringWarningObserver);
        this.mContext.registerReceiver(
            this.mDdsChangedReceiver,
            new IntentFilter(
                "com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS"),
            null,
            this.mHandler);
        this.mTetheringWarningBytes.put(
            0, Long.valueOf(getTetheringWarningBytes("tethering_data_warning_sim_slot_0")));
        this.mTetheringWarningBytes.put(
            1, Long.valueOf(getTetheringWarningBytes("tethering_data_warning_sim_slot_1")));
        this.mContext.registerReceiver(
            this.mSnoozeReceiver,
            new IntentFilter("com.android.server.net.action.SNOOZE_TETHERING_WARNING"),
            "android.permission.MANAGE_NETWORK_POLICY",
            this.mHandler);
        this.mTetheringNotiSnooze = -1L;
      }
    } finally {
      Process.setThreadPriority(threadPriority);
      Trace.traceEnd(2097152L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$initService$0(boolean z) {
    synchronized (this.mUidRulesFirstLock) {
      this.mRestrictedNetworkingMode = z;
      updateRestrictedModeAllowlistUL();
    }
  }

  public CountDownLatch networkScoreAndNetworkManagementServiceReady() {
    this.mNetworkManagerReady = true;
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            NetworkPolicyManagerService.this.lambda$networkScoreAndNetworkManagementServiceReady$1(
                countDownLatch);
          }
        });
    return countDownLatch;
  }

  public void systemReady(CountDownLatch countDownLatch) {
    try {
      if (!countDownLatch.await(30L, TimeUnit.SECONDS)) {
        throw new IllegalStateException("Service NetworkPolicy init timeout");
      }
      this.mMultipathPolicyTracker.start();
      if (isKorOperator()) {
        ((TetheringManager) this.mContext.getSystemService(TetheringManager.class))
            .registerTetheringEventCallback(
                new Executor() { // from class:
                                 // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda8
                  @Override // java.util.concurrent.Executor
                  public final void execute(Runnable runnable) {
                    NetworkPolicyManagerService.this.lambda$systemReady$2(runnable);
                  }
                },
                this.mTetherListener);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Service NetworkPolicy init interrupted", e);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$2(Runnable runnable) {
    this.mHandler.post(runnable);
  }

  public final class UidStateCallbackInfo {
    public int capability;
    public boolean isPending;
    public int procState;
    public long procStateSeq;
    public int uid;

    public UidStateCallbackInfo() {
      this.procState = 20;
      this.procStateSeq = -1L;
    }

    public void update(int i, int i2, long j, int i3) {
      this.uid = i;
      this.procState = i2;
      this.procStateSeq = j;
      this.capability = i3;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      sb.append("uid=");
      sb.append(this.uid);
      sb.append(",");
      sb.append("proc_state=");
      sb.append(ActivityManager.procStateToString(this.procState));
      sb.append(",");
      sb.append("seq=");
      sb.append(this.procStateSeq);
      sb.append(",");
      sb.append("cap=");
      ActivityManager.printCapabilitiesSummary(sb, this.capability);
      sb.append(",");
      sb.append("pending=");
      sb.append(this.isPending);
      sb.append("}");
      return sb.toString();
    }
  }

  public class StatsCallback extends NetworkStatsManager.UsageCallback {
    public boolean mIsAnyCallbackReceived;

    public StatsCallback() {
      this.mIsAnyCallbackReceived = false;
    }

    @Override // android.app.usage.NetworkStatsManager.UsageCallback
    public void onThresholdReached(int i, String str) {
      this.mIsAnyCallbackReceived = true;
      synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
        NetworkPolicyManagerService.this.updateNetworkRulesNL();
        NetworkPolicyManagerService.this.updateNetworkEnabledNL();
        NetworkPolicyManagerService.this.updateNotificationsNL();
      }
    }

    public boolean isAnyCallbackReceived() {
      return this.mIsAnyCallbackReceived;
    }
  }

  public static boolean updateCapabilityChange(
      SparseBooleanArray sparseBooleanArray, boolean z, Network network) {
    boolean z2 =
        sparseBooleanArray.get(network.getNetId(), false) != z
            || sparseBooleanArray.indexOfKey(network.getNetId()) < 0;
    if (z2) {
      sparseBooleanArray.put(network.getNetId(), z);
    }
    return z2;
  }

  public final boolean updateNetworkToIfacesNL(int i, ArraySet arraySet) {
    ArraySet arraySet2 = this.mNetworkToIfaces.get(i);
    boolean z = true;
    if (arraySet2 != null && arraySet2.equals(arraySet)) {
      z = false;
    }
    if (z) {
      this.mNetworkToIfaces.remove(i);
      Iterator it = arraySet.iterator();
      while (it.hasNext()) {
        this.mNetworkToIfaces.add(i, (String) it.next());
      }
    }
    return z;
  }

  public void updateNotificationsNL() {
    long j;
    NetworkPolicy networkPolicy;
    int i;
    NetworkPolicy networkPolicy2;
    long j2;
    if (LOGV) {
      Slog.v("NetworkPolicy", "updateNotificationsNL()");
    }
    Trace.traceBegin(2097152L, "updateNotificationsNL");
    ArraySet arraySet = new ArraySet(this.mActiveNotifs);
    this.mActiveNotifs.clear();
    long millis = this.mClock.millis();
    for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size = i - 1) {
      NetworkPolicy networkPolicy3 = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
      int findRelevantSubIdNL = findRelevantSubIdNL(networkPolicy3.template);
      if (findRelevantSubIdNL != -1 && networkPolicy3.hasCycle()) {
        Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy3).next();
        long epochMilli = ((ZonedDateTime) pair.first).toInstant().toEpochMilli();
        long epochMilli2 = ((ZonedDateTime) pair.second).toInstant().toEpochMilli();
        long totalBytes = getTotalBytes(networkPolicy3.template, epochMilli, epochMilli2);
        if (totalBytes > this.preTotalBytes + 100000) {
          Slog.v(
              "NetworkPolicy",
              "updateNotificationsNL() - totalBytes: "
                  + totalBytes
                  + ", warningBytes= "
                  + networkPolicy3.warningBytes
                  + ", limitBytes= "
                  + networkPolicy3.limitBytes);
          this.preTotalBytes = totalBytes;
        }
        PersistableBundle persistableBundle =
            (PersistableBundle) this.mSubIdToCarrierConfig.get(findRelevantSubIdNL);
        if (!CarrierConfigManager.isConfigForIdentifiedCarrier(persistableBundle)) {
          if (LOGV) {
            Slog.v("NetworkPolicy", "isConfigForIdentifiedCarrier returned false");
          }
        } else {
          boolean booleanDefeatingNullable =
              getBooleanDefeatingNullable(
                  persistableBundle, "data_warning_notification_bool", true);
          boolean booleanDefeatingNullable2 =
              getBooleanDefeatingNullable(persistableBundle, "data_limit_notification_bool", true);
          boolean booleanDefeatingNullable3 =
              getBooleanDefeatingNullable(persistableBundle, "data_rapid_notification_bool", true);
          if (isKorOperator()) {
            String str =
                networkPolicy3.template.getSubscriberIds().isEmpty()
                    ? null
                    : (String) networkPolicy3.template.getSubscriberIds().iterator().next();
            int intValue =
                str != null
                    ? ((Integer) this.mSubscriberIdToSlotId.getOrDefault(str, -1)).intValue()
                    : -1;
            long longValue =
                ((Long) this.mTetheringWarningBytes.getOrDefault(Integer.valueOf(intValue), -1L))
                    .longValue();
            if (this.mTetheringState
                && isKorOperator()
                && longValue != -1
                && this.mDefaultDataPhoneId == intValue
                && this.mTetheringNotiSnooze == -1) {
              j = totalBytes;
              networkPolicy = networkPolicy3;
              i = size;
              j2 = getUidBytes(networkPolicy3.template, epochMilli, epochMilli2, -5, 2);
              if (longValue < j2) {
                enqueueNotification(networkPolicy, -5, j2, null);
              }
            } else {
              j = totalBytes;
              networkPolicy = networkPolicy3;
              i = size;
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
            j = totalBytes;
            networkPolicy = networkPolicy3;
            i = size;
          }
          if (booleanDefeatingNullable) {
            long j3 = j;
            networkPolicy2 = networkPolicy;
            if (networkPolicy2.isOverWarning(j3) && !networkPolicy2.isOverLimit(j3)) {
              if (!(networkPolicy2.lastWarningSnooze >= epochMilli)) {
                j = j3;
                enqueueNotification(networkPolicy2, 34, j3, null);
              }
            }
            j = j3;
          } else {
            networkPolicy2 = networkPolicy;
          }
          if (booleanDefeatingNullable2) {
            long j4 = j;
            if (!networkPolicy2.isOverLimit(j4) || this.mIsVideoCall) {
              notifyUnderLimitNL(networkPolicy2.template);
            } else if (networkPolicy2.lastLimitSnooze >= epochMilli) {
              enqueueNotification(networkPolicy2, 36, j4, null);
            } else {
              enqueueNotification(networkPolicy2, 35, j4, null);
              notifyOverLimitNL(networkPolicy2.template);
            }
          }
          if (booleanDefeatingNullable3 && networkPolicy2.limitBytes != -1) {
            long millis2 = TimeUnit.DAYS.toMillis(4L);
            long j5 = millis - millis2;
            long totalBytes2 = getTotalBytes(networkPolicy2.template, j5, millis);
            long j6 = ((epochMilli2 - epochMilli) * totalBytes2) / millis2;
            long j7 = (networkPolicy2.limitBytes * 3) / 2;
            if (LOGD) {
              Slog.d(
                  "NetworkPolicy",
                  "Rapid usage considering recent "
                      + totalBytes2
                      + " projected "
                      + j6
                      + " alert "
                      + j7);
            }
            boolean z =
                networkPolicy2.lastRapidSnooze
                    >= millis - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            if (j6 > j7 && !z) {
              enqueueNotification(
                  networkPolicy2, 45, 0L, findRapidBlame(networkPolicy2.template, j5, millis));
            }
          }
        }
      }
      i = size;
    }
    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
      NotificationId notificationId = (NotificationId) arraySet.valueAt(size2);
      if (!this.mActiveNotifs.contains(notificationId)) {
        cancelNotification(notificationId);
      }
    }
    Trace.traceEnd(2097152L);
  }

  public final ApplicationInfo findRapidBlame(NetworkTemplate networkTemplate, long j, long j2) {
    String[] packagesForUid;
    if (!this.mStatsCallback.isAnyCallbackReceived()) {
      return null;
    }
    long j3 = 0;
    long j4 = 0;
    int i = 0;
    for (NetworkStats.Bucket bucket : this.mDeps.getNetworkUidBytes(networkTemplate, j, j2)) {
      long rxBytes = bucket.getRxBytes() + bucket.getTxBytes();
      j4 += rxBytes;
      if (rxBytes > j3) {
        i = bucket.getUid();
        j3 = rxBytes;
      }
    }
    if (j3 > 0
        && j3 > j4 / 2
        && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null
        && packagesForUid.length == 1) {
      try {
        return this.mContext.getPackageManager().getApplicationInfo(packagesForUid[0], 4989440);
      } catch (PackageManager.NameNotFoundException unused) {
      }
    }
    return null;
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

  public final void notifyOverLimitNL(NetworkTemplate networkTemplate) {
    if (this.mOverLimitNotified.contains(networkTemplate)) {
      return;
    }
    Context context = this.mContext;
    context.startActivity(buildNetworkOverLimitIntent(context.getResources(), networkTemplate));
    this.mOverLimitNotified.add(networkTemplate);
  }

  public final void notifyUnderLimitNL(NetworkTemplate networkTemplate) {
    this.mOverLimitNotified.remove(networkTemplate);
  }

  /* JADX WARN: Removed duplicated region for block: B:17:0x0074  */
  /* JADX WARN: Removed duplicated region for block: B:30:0x00c6  */
  /* JADX WARN: Removed duplicated region for block: B:32:0x00cd  */
  /* JADX WARN: Removed duplicated region for block: B:43:0x0163  */
  /* JADX WARN: Removed duplicated region for block: B:46:0x017f  */
  /* JADX WARN: Removed duplicated region for block: B:49:0x0201  */
  /* JADX WARN: Removed duplicated region for block: B:52:0x0221  */
  /* JADX WARN: Removed duplicated region for block: B:55:0x0236 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0252  */
  /* JADX WARN: Removed duplicated region for block: B:59:0x0270  */
  /* JADX WARN: Removed duplicated region for block: B:64:0x0299  */
  /* JADX WARN: Removed duplicated region for block: B:67:0x0223  */
  /* JADX WARN: Removed duplicated region for block: B:68:0x0208  */
  /* JADX WARN: Removed duplicated region for block: B:69:0x01ca  */
  /* JADX WARN: Removed duplicated region for block: B:74:0x0165  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void enqueueNotification(
      NetworkPolicy networkPolicy, int i, long j, ApplicationInfo applicationInfo) {
    CharSequence text;
    CharSequence charSequence;
    Intent buildViewDataUsageIntent;
    boolean z;
    Intent buildNetworkOverLimitIntent;
    NotificationId notificationId = new NotificationId(networkPolicy, i);
    Notification.Builder builder =
        new Notification.Builder(this.mContext, SystemNotificationChannels.NETWORK_ALERTS);
    builder.setOnlyAlertOnce(true);
    builder.setWhen(0L);
    builder.setColor(this.mContext.getColor(R.color.system_notification_accent_color));
    Resources resources = this.mContext.getResources();
    if (i == -5) {
      text = resources.getText(R.string.fileSizeSuffix);
      charSequence =
          resources.getString(
                  R.string.fast_scroll_numeric_alphabet,
                  Formatter.formatFileSize(this.mContext, j, 4))
              + " "
              + resources.getString(R.string.fcComplete);
      builder.setSmallIcon(R.drawable.stat_notify_error);
      builder.setDeleteIntent(
          PendingIntent.getBroadcast(
              this.mContext,
              0,
              buildSnoozeTetheringWarningIntent(this.mContext.getPackageName()),
              201326592));
      Intent buildViewDataUsageIntent2 =
          buildViewDataUsageIntent(resources, networkPolicy.template);
      if (UserManager.isHeadlessSystemUserMode()) {
        builder.setContentIntent(
            PendingIntent.getActivityAsUser(
                this.mContext, 0, buildViewDataUsageIntent2, 201326592, null, UserHandle.CURRENT));
      } else {
        builder.setContentIntent(
            PendingIntent.getActivity(this.mContext, 0, buildViewDataUsageIntent2, 201326592));
      }
    } else if (i != 45) {
      String str =
          "com.samsung.android.sm_cn/com.samsung.android.sm.datausage.ui.BillingCycle.BillingCycleSettingsActivity";
      switch (i) {
        case 34:
          text = resources.getText(R.string.fileSizeSuffix);
          String string =
              resources.getString(R.string.fcError, Formatter.formatFileSize(this.mContext, j, 4));
          builder.setSmallIcon(R.drawable.stat_notify_error);
          String string2 = resources.getString(R.string.crossSimFormat_spn_cross_sim_calling);
          if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {
            int findRelevantSubIdNL = findRelevantSubIdNL(networkPolicy.template);
            if (Settings.System.getInt(
                    this.mContext.getContentResolver(),
                    KEY_UNLIMITED_DATA_PLAN_WARN_SWITCH + findRelevantSubIdNL,
                    0)
                == 1) {
              if (Settings.System.getString(
                      this.mContext.getContentResolver(), KEY_USAGE_PLAN_LIST + findRelevantSubIdNL)
                  .equals("unlimited")) {
                z = true;
                ContentResolver contentResolver = this.mContext.getContentResolver();
                StringBuilder sb = new StringBuilder();
                sb.append(KEY_ONLY_SHOW_LIMIT_ALERT);
                sb.append(findRelevantSubIdNL);
                boolean z2 = Settings.System.getInt(contentResolver, sb.toString(), 0) != 1;
                charSequence =
                    resources.getString(
                        R.string.fcError, Formatter.formatFileSize(this.mContext, j, 8));
                if (!isOffPeakEnable(findRelevantSubIdNL)) {
                  Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy).next();
                  text =
                      this.mContext
                          .getResources()
                          .getString(
                              R.string.file_count,
                              Formatter.formatFileSize(
                                  this.mContext,
                                  getTotalBytesForOffPeak(
                                      findRelevantSubIdNL,
                                      networkPolicy,
                                      ((ZonedDateTime) pair.first).toInstant().toEpochMilli(),
                                      ((ZonedDateTime) pair.second).toInstant().toEpochMilli()),
                                  8));
                  charSequence = "";
                  str =
                      "com.samsung.android.sm_cn/com.samsung.android.sm.datausage.ui.BillingCycle.OffPeakDataWarningActivity";
                } else if (z) {
                  text = this.mContext.getResources().getString(R.string.find);
                } else if (z2) {
                  text = resources.getText(R.string.factorytest_failed);
                }
                builder.setDeleteIntent(
                    PendingIntent.getBroadcast(
                        this.mContext,
                        0,
                        buildSnoozeWarningIntent(
                            networkPolicy.template, this.mContext.getPackageName()),
                        201326592));
                if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {
                  buildViewDataUsageIntent =
                      buildViewDataUsageIntentSM(str, networkPolicy.template);
                } else {
                  buildViewDataUsageIntent =
                      buildViewDataUsageIntent(resources, networkPolicy.template);
                }
                setContentIntent(builder, buildViewDataUsageIntent);
                boolean z3 =
                    Settings.System.getInt(this.mContext.getContentResolver(), "udsState", 0) == 1;
                if (SemCscFeature.getInstance()
                        .getString("CscFeature_SmartManager_ConfigSubFeatures")
                        .contains("UDS2")
                    && !z3) {
                  Log.d("NetworkPolicy", "Ultra data saving mode enabled");
                  new Intent();
                  if (isPackageInstalled("com.samsung.android.uds")) {
                    Log.d(
                        "NetworkPolicy",
                        "Ultra data saving mode didnt install then go to Galaxy app");
                    builder
                        .addAction(
                            17304598,
                            resources.getString(17043029),
                            PendingIntent.getActivity(
                                this.mContext,
                                0,
                                jumpToSamsungApps("com.samsung.android.uds"),
                                201326592))
                        .setOngoing(true);
                  } else if (isSystemApp("com.samsung.android.uds")) {
                    Log.d(
                        "NetworkPolicy", "Ultra data saving mode installed then go to Devicecare");
                    builder
                        .addAction(
                            17304598,
                            resources.getString(17043029),
                            PendingIntent.getActivity(
                                this.mContext,
                                0,
                                buildViewDataUsageIntentUDS(networkPolicy.template),
                                201326592))
                        .setOngoing(true);
                  }
                }
                if (!this.mVideoCallWarningAlreadySent && this.mIsVideoCall) {
                  this.mContext.sendBroadcast(
                      new Intent("com.samsung.intent.action.DATAUSAGE_REACH_TO_WARNING"));
                  this.mVideoCallWarningAlreadySent = true;
                  break;
                }
              }
            }
            z = false;
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(KEY_ONLY_SHOW_LIMIT_ALERT);
            sb2.append(findRelevantSubIdNL);
            if (Settings.System.getInt(contentResolver2, sb2.toString(), 0) != 1) {}
            charSequence =
                resources.getString(
                    R.string.fcError, Formatter.formatFileSize(this.mContext, j, 8));
            if (!isOffPeakEnable(findRelevantSubIdNL)) {}
            builder.setDeleteIntent(
                PendingIntent.getBroadcast(
                    this.mContext,
                    0,
                    buildSnoozeWarningIntent(
                        networkPolicy.template, this.mContext.getPackageName()),
                    201326592));
            if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {}
            setContentIntent(builder, buildViewDataUsageIntent);
            if (Settings.System.getInt(this.mContext.getContentResolver(), "udsState", 0) == 1) {}
            if (SemCscFeature.getInstance()
                .getString("CscFeature_SmartManager_ConfigSubFeatures")
                .contains("UDS2")) {
              Log.d("NetworkPolicy", "Ultra data saving mode enabled");
              new Intent();
              if (isPackageInstalled("com.samsung.android.uds")) {}
            }
            if (!this.mVideoCallWarningAlreadySent) {
              this.mContext.sendBroadcast(
                  new Intent("com.samsung.intent.action.DATAUSAGE_REACH_TO_WARNING"));
              this.mVideoCallWarningAlreadySent = true;
            }
          } else {
            charSequence = string;
          }
          str = string2;
          builder.setDeleteIntent(
              PendingIntent.getBroadcast(
                  this.mContext,
                  0,
                  buildSnoozeWarningIntent(networkPolicy.template, this.mContext.getPackageName()),
                  201326592));
          if (CoreRune.SUPPORT_TRAFFIC_MANAGER) {}
          setContentIntent(builder, buildViewDataUsageIntent);
          if (Settings.System.getInt(this.mContext.getContentResolver(), "udsState", 0) == 1) {}
          if (SemCscFeature.getInstance()
              .getString("CscFeature_SmartManager_ConfigSubFeatures")
              .contains("UDS2")) {}
          if (!this.mVideoCallWarningAlreadySent) {}
          break;
        case 35:
          int matchRule = networkPolicy.template.getMatchRule();
          if (matchRule != 1) {
            if (matchRule == 4) {
              text = resources.getText(R.string.find_on_page);
              charSequence = resources.getText(R.string.faceunlock_multiple_failures);
              builder.setOngoing(true);
              builder.setSmallIcon(17304205);
              if (!CoreRune.SUPPORT_TRAFFIC_MANAGER) {
                buildNetworkOverLimitIntent =
                    buildViewDataUsageIntentSM(
                        "com.samsung.android.sm_cn/com.samsung.android.sm.datausage.ui.BillingCycle.BillingCycleSettingsActivity",
                        networkPolicy.template);
              } else {
                buildNetworkOverLimitIntent =
                    buildNetworkOverLimitIntent(resources, networkPolicy.template);
              }
              setContentIntent(builder, buildNetworkOverLimitIntent);
              break;
            } else if (matchRule != 10) {
              return;
            }
          }
          text = resources.getText(R.string.factorytest_failed);
          charSequence = resources.getText(R.string.faceunlock_multiple_failures);
          builder.setOngoing(true);
          builder.setSmallIcon(17304205);
          if (!CoreRune.SUPPORT_TRAFFIC_MANAGER) {}
          setContentIntent(builder, buildNetworkOverLimitIntent);
        case 36:
          int matchRule2 = networkPolicy.template.getMatchRule();
          if (matchRule2 != 1) {
            if (matchRule2 == 4) {
              text = resources.getText(R.string.find_next);
              Object[] objArr = new Object[1];
              objArr[0] =
                  Formatter.formatFileSize(
                      this.mContext,
                      j - networkPolicy.limitBytes,
                      CoreRune.SUPPORT_TRAFFIC_MANAGER ? 8 : 4);
              charSequence = resources.getString(R.string.factory_reset_message, objArr);
              builder.setOngoing(true);
              builder.setSmallIcon(R.drawable.stat_notify_error);
              builder.setChannelId(SystemNotificationChannels.NETWORK_STATUS);
              setContentIntent(
                  builder, buildViewDataUsageIntent(resources, networkPolicy.template));
              break;
            } else if (matchRule2 != 10) {
              return;
            }
          }
          text = resources.getText(R.string.factory_reset_warning);
          Object[] objArr2 = new Object[1];
          objArr2[0] =
              Formatter.formatFileSize(
                  this.mContext,
                  j - networkPolicy.limitBytes,
                  CoreRune.SUPPORT_TRAFFIC_MANAGER ? 8 : 4);
          charSequence = resources.getString(R.string.factory_reset_message, objArr2);
          builder.setOngoing(true);
          builder.setSmallIcon(R.drawable.stat_notify_error);
          builder.setChannelId(SystemNotificationChannels.NETWORK_STATUS);
          setContentIntent(builder, buildViewDataUsageIntent(resources, networkPolicy.template));
        default:
          return;
      }
    } else {
      text = resources.getText(R.string.factorytest_reboot);
      if (applicationInfo != null) {
        charSequence =
            resources.getString(
                R.string.factorytest_no_action,
                applicationInfo.loadLabel(this.mContext.getPackageManager()));
      } else {
        charSequence = resources.getString(R.string.factorytest_not_system);
      }
      builder.setSmallIcon(R.drawable.stat_notify_error);
      builder.setDeleteIntent(
          PendingIntent.getBroadcast(
              this.mContext,
              0,
              buildSnoozeRapidIntent(networkPolicy.template, this.mContext.getPackageName()),
              201326592));
      setContentIntent(builder, buildViewDataUsageIntent(resources, networkPolicy.template));
    }
    builder.setTicker(text);
    builder.setContentTitle(text);
    builder.setContentText(charSequence);
    builder.setStyle(new Notification.BigTextStyle().bigText(charSequence));
    ((NotificationManager) this.mContext.getSystemService(NotificationManager.class))
        .notifyAsUser(
            notificationId.getTag(), notificationId.getId(), builder.build(), UserHandle.ALL);
    this.mActiveNotifs.add(notificationId);
  }

  public final void setContentIntent(Notification.Builder builder, Intent intent) {
    if (UserManager.isHeadlessSystemUserMode()) {
      builder.setContentIntent(
          PendingIntent.getActivityAsUser(
              this.mContext, 0, intent, 201326592, null, UserHandle.CURRENT));
    } else {
      builder.setContentIntent(PendingIntent.getActivity(this.mContext, 0, intent, 201326592));
    }
  }

  public Intent jumpToSamsungApps(String str) {
    Intent intent = new Intent();
    intent.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
    intent.putExtra("directcall", true);
    intent.putExtra("CallerType", 1);
    intent.putExtra("GUID", str);
    intent.addFlags(335544352);
    return intent;
  }

  public boolean isPackageInstalled(String str) {
    PackageManager packageManager = this.mContext.getPackageManager();
    if (packageManager == null) {
      return false;
    }
    try {
      packageManager.getPackageInfo(str, 0);
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      Log.d("NetworkPolicy", "Package isnt existed");
      return false;
    }
  }

  public boolean isSystemApp(String str) {
    try {
      ApplicationInfo applicationInfo =
          this.mContext.getPackageManager().getApplicationInfo(str, 0);
      int i = applicationInfo.flags;
      if ((i & 1) == 0 && (i & 128) == 0) {
        if (applicationInfo.uid >= 10000) {
          return false;
        }
      }
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      Slog.e("NetworkPolicy", "isSystemApp NameNotFoundException : " + e);
      return false;
    }
  }

  public final void cancelNotification(NotificationId notificationId) {
    ((NotificationManager) this.mContext.getSystemService(NotificationManager.class))
        .cancel(notificationId.getTag(), notificationId.getId());
  }

  public final void updateNetworksInternal() {
    updateSubscriptions();
    synchronized (this.mUidRulesFirstLock) {
      synchronized (this.mNetworkPoliciesSecondLock) {
        ensureActiveCarrierPolicyAL();
        normalizePoliciesNL();
        updateNetworkEnabledNL();
        updateNetworkRulesNL();
        updateNotificationsNL();
      }
    }
  }

  public void updateNetworks() {
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

  public Handler getHandlerForTesting() {
    return this.mHandler;
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
        z = updateDefaultCarrierPolicyAL(i, (NetworkPolicy) this.mNetworkPolicy.valueAt(size)) | z;
      }
    }
    return z;
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
    Slog.e(
        "NetworkPolicy",
        "Invalid date in CarrierConfigManager.KEY_MONTHLY_DATA_CYCLE_DAY_INT: " + i2);
    return i;
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
        "Invalid value in CarrierConfigManager.KEY_DATA_WARNING_THRESHOLD_BYTES_LONG; expected a"
            + " non-negative value but got: "
            + j2);
    return j;
  }

  public long getLimitBytesFromCarrierConfig(PersistableBundle persistableBundle, long j) {
    if (persistableBundle == null) {
      return j;
    }
    long j2 = persistableBundle.getLong("data_limit_threshold_bytes_long");
    if (j2 == -2) {
      return -1L;
    }
    if (j2 == -1) {
      return getPlatformDefaultLimitBytes();
    }
    if (j2 >= 0) {
      return j2;
    }
    Slog.e(
        "NetworkPolicy",
        "Invalid value in CarrierConfigManager.KEY_DATA_LIMIT_THRESHOLD_BYTES_LONG; expected a"
            + " non-negative value but got: "
            + j2);
    return j;
  }

  public void handleNetworkPoliciesUpdateAL(boolean z) {
    if (z) {
      normalizePoliciesNL();
    }
    updateNetworkEnabledNL();
    updateNetworkRulesNL();
    updateNotificationsNL();
    writePolicyAL();
  }

  public void updateNetworkEnabledNL() {
    if (LOGV) {
      Slog.v("NetworkPolicy", "updateNetworkEnabledNL()");
    }
    Trace.traceBegin(2097152L, "updateNetworkEnabledNL");
    String string =
        SemCarrierFeature.getInstance()
            .getString(0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
    if ((string != null && string.startsWith("VZW-")) || isNaGsm("ATT") || isNaGsm("TMO")) {
      updateVideoCallLocked();
    }
    long time = this.mStatLogger.getTime();
    for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
      NetworkPolicy networkPolicy = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
      if (networkPolicy.limitBytes == -1 || !networkPolicy.hasCycle()) {
        setNetworkTemplateEnabled(networkPolicy.template, true);
      } else {
        Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicy).next();
        long epochMilli = ((ZonedDateTime) pair.first).toInstant().toEpochMilli();
        setNetworkTemplateEnabled(
            networkPolicy.template,
            !(networkPolicy.isOverLimit(
                    getTotalBytes(
                        networkPolicy.template,
                        epochMilli,
                        ((ZonedDateTime) pair.second).toInstant().toEpochMilli()))
                && !this.mIsVideoCall
                && networkPolicy.lastLimitSnooze < epochMilli));
      }
    }
    this.mStatLogger.logDurationStat(0, time);
    Trace.traceEnd(2097152L);
  }

  public final void setNetworkTemplateEnabled(NetworkTemplate networkTemplate, boolean z) {
    this.mHandler.obtainMessage(18, z ? 1 : 0, 0, networkTemplate).sendToTarget();
  }

  public final void setNetworkTemplateEnabledInner(NetworkTemplate networkTemplate, boolean z) {
    int i;
    if (networkTemplate.getMatchRule() == 1 || networkTemplate.getMatchRule() == 10) {
      IntArray intArray = new IntArray();
      synchronized (this.mNetworkPoliciesSecondLock) {
        for (int i2 = 0; i2 < this.mSubIdToSubscriberId.size(); i2++) {
          int keyAt = this.mSubIdToSubscriberId.keyAt(i2);
          if (networkTemplate.matches(
              new NetworkIdentity.Builder()
                  .setType(0)
                  .setSubscriberId((String) this.mSubIdToSubscriberId.valueAt(i2))
                  .setMetered(true)
                  .setDefaultNetwork(true)
                  .setSubId(keyAt)
                  .build())) {
            intArray.add(keyAt);
          }
        }
      }
      TelephonyManager telephonyManager =
          (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
      for (i = 0; i < intArray.size(); i++) {
        telephonyManager.createForSubscriptionId(intArray.get(i)).setPolicyDataEnabled(z);
      }
    }
  }

  public static void collectIfaces(ArraySet arraySet, NetworkStateSnapshot networkStateSnapshot) {
    arraySet.addAll(networkStateSnapshot.getLinkProperties().getAllInterfaceNames());
  }

  public void updateSubscriptions() {
    if (LOGV) {
      Slog.v("NetworkPolicy", "updateSubscriptions()");
    }
    Trace.traceBegin(2097152L, "updateSubscriptions");
    TelephonyManager telephonyManager =
        (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
    List emptyIfNull =
        CollectionUtils.emptyIfNull(
            ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
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
            "Missing subscriberId for subId " + createForSubscriptionId.getSubscriptionId());
      } else {
        sparseArray.put(createForSubscriptionId.getSubscriptionId(), subscriberId);
      }
      arrayList.add(ArrayUtils.defeatNullable(createForSubscriptionId.getMergedImsisFromGroup()));
      PersistableBundle configForSubId =
          this.mCarrierConfigManager.getConfigForSubId(subscriptionId);
      if (configForSubId != null) {
        sparseArray2.put(subscriptionId, configForSubId);
      } else {
        Slog.e("NetworkPolicy", "Missing CarrierConfig for subId " + subscriptionId);
      }
    }
    synchronized (this.mNetworkPoliciesSecondLock) {
      this.mSubIdToSubscriberId.clear();
      for (int i = 0; i < sparseArray.size(); i++) {
        this.mSubIdToSubscriberId.put(sparseArray.keyAt(i), (String) sparseArray.valueAt(i));
      }
      this.mMergedSubscriberIds = arrayList;
      this.mSubIdToCarrierConfig.clear();
      for (int i2 = 0; i2 < sparseArray2.size(); i2++) {
        this.mSubIdToCarrierConfig.put(
            sparseArray2.keyAt(i2), (PersistableBundle) sparseArray2.valueAt(i2));
      }
    }
    Trace.traceEnd(2097152L);
  }

  public void updateNetworkRulesNL() {
    int i;
    String[] strArr;
    int subIdLocked;
    SubscriptionPlan primarySubscriptionPlanLocked;
    long max;
    int i2;
    NetworkPolicy networkPolicy;
    long max2;
    long max3;
    long j;
    long j2;
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
    long j3 = Long.MAX_VALUE;
    while (true) {
      if (size < 0) {
        break;
      }
      NetworkPolicy networkPolicy2 = (NetworkPolicy) this.mNetworkPolicy.valueAt(size);
      arraySet2.clear();
      for (int size2 = arrayMap.size() - i; size2 >= 0; size2--) {
        if (networkPolicy2.template.matches((NetworkIdentity) arrayMap.valueAt(size2))) {
          collectIfaces(arraySet2, (NetworkStateSnapshot) arrayMap.keyAt(size2));
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
        i2 = size;
        networkPolicy = networkPolicy2;
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
        i2 = size;
        networkPolicy = networkPolicy2;
        max3 = Long.MAX_VALUE;
        max2 = Long.MAX_VALUE;
      }
      if (i3 != 0 || i4 != 0 || networkPolicy.metered) {
        if (arraySet2.size() > i) {
          Slog.w("NetworkPolicy", "shared quota unsupported; generating rule for each iface");
        }
        if (this.mIsVideoCall && this.mVideoCallLimitAlreadySent) {
          j = Long.MAX_VALUE;
          j2 = Long.MAX_VALUE;
        } else {
          j = max3;
          j2 = max2;
        }
        for (int size3 = arraySet2.size() - i; size3 >= 0; size3--) {
          String str = (String) arraySet2.valueAt(size3);
          setInterfaceQuotasAsync(str, j, j2);
          arraySet.add(str);
        }
      }
      if (i3 != 0) {
        long j4 = networkPolicy.warningBytes;
        if (j4 < j3) {
          j3 = j4;
        }
      }
      if (i4 != 0) {
        long j5 = networkPolicy.limitBytes;
        if (j5 < j3) {
          j3 = j5;
        }
      }
      size = i2 - 1;
      i = 1;
    }
    for (NetworkStateSnapshot networkStateSnapshot2 : allNetworkStateSnapshots) {
      if (!networkStateSnapshot2.getNetworkCapabilities().hasCapability(11)) {
        arraySet2.clear();
        collectIfaces(arraySet2, networkStateSnapshot2);
        for (int size4 = arraySet2.size() - 1; size4 >= 0; size4--) {
          String str2 = (String) arraySet2.valueAt(size4);
          if (!arraySet.contains(str2)) {
            setInterfaceQuotasAsync(str2, Long.MAX_VALUE, Long.MAX_VALUE);
            arraySet.add(str2);
          }
        }
      }
    }
    synchronized (this.mMeteredIfacesLock) {
      for (int size5 = this.mMeteredIfaces.size() - 1; size5 >= 0; size5--) {
        String str3 = (String) this.mMeteredIfaces.valueAt(size5);
        if (!arraySet.contains(str3)) {
          removeInterfaceQuotasAsync(str3);
        }
      }
      this.mMeteredIfaces = arraySet;
    }
    ContentResolver contentResolver = this.mContext.getContentResolver();
    boolean z = Settings.Global.getInt(contentResolver, "netpolicy_quota_enabled", 1) != 0;
    long j6 =
        Settings.Global.getLong(
            contentResolver, "netpolicy_quota_unlimited", QUOTA_UNLIMITED_DEFAULT);
    float f = Settings.Global.getFloat(contentResolver, "netpolicy_quota_limited", 0.1f);
    this.mSubscriptionOpportunisticQuota.clear();
    for (NetworkStateSnapshot networkStateSnapshot3 : allNetworkStateSnapshots) {
      if (z
          && networkStateSnapshot3.getNetwork() != null
          && (subIdLocked = getSubIdLocked(networkStateSnapshot3.getNetwork())) != -1
          && (primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(subIdLocked))
              != null) {
        long dataLimitBytes = primarySubscriptionPlanLocked.getDataLimitBytes();
        if (!networkStateSnapshot3.getNetworkCapabilities().hasCapability(18)) {
          max = 0;
        } else if (dataLimitBytes == -1) {
          max = -1;
        } else {
          if (dataLimitBytes == Long.MAX_VALUE) {
            max = j6;
          } else {
            Range<ZonedDateTime> next = primarySubscriptionPlanLocked.cycleIterator().next();
            long epochMilli2 = next.getLower().toInstant().toEpochMilli();
            long epochMilli3 = next.getUpper().toInstant().toEpochMilli();
            long epochMilli4 =
                ZonedDateTime.ofInstant(this.mClock.instant(), next.getLower().getZone())
                    .truncatedTo(ChronoUnit.DAYS)
                    .toInstant()
                    .toEpochMilli();
            max =
                Math.max(
                    0L,
                    (long)
                        (((dataLimitBytes
                                    - (networkStateSnapshot3.getSubscriberId() == null
                                        ? 0L
                                        : getTotalBytes(
                                            buildTemplateCarrierMetered(r1),
                                            epochMilli2,
                                            epochMilli4)))
                                / ((((epochMilli3 - r15.toEpochMilli()) - 1)
                                        / TimeUnit.DAYS.toMillis(1L))
                                    + 1))
                            * f));
          }
          this.mSubscriptionOpportunisticQuota.put(subIdLocked, max);
        }
        this.mSubscriptionOpportunisticQuota.put(subIdLocked, max);
      }
    }
    synchronized (this.mMeteredIfacesLock) {
      ArraySet arraySet3 = this.mMeteredIfaces;
      strArr = (String[]) arraySet3.toArray(new String[arraySet3.size()]);
    }
    this.mHandler.obtainMessage(2, strArr).sendToTarget();
    this.mHandler.obtainMessage(7, Long.valueOf(j3)).sendToTarget();
    Trace.traceEnd(2097152L);
  }

  public final void ensureActiveCarrierPolicyAL() {
    if (LOGV) {
      Slog.v("NetworkPolicy", "ensureActiveCarrierPolicyAL()");
    }
    if (this.mSuppressDefaultPolicy) {
      return;
    }
    for (int i = 0; i < this.mSubIdToSubscriberId.size(); i++) {
      ensureActiveCarrierPolicyAL(
          this.mSubIdToSubscriberId.keyAt(i), (String) this.mSubIdToSubscriberId.valueAt(i));
    }
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
    addNetworkPolicyAL(buildDefaultCarrierPolicy(i, str));
    return true;
  }

  public final long getPlatformDefaultWarningBytes() {
    if (this.mContext
            .getResources()
            .getInteger(R.integer.config_notificationsBatteryNearlyFullLevel)
        == -1) {
      return -1L;
    }
    return DataUnit.GIGABYTES.toBytes(r4 / 1024);
  }

  public NetworkPolicy buildDefaultCarrierPolicy(int i, String str) {
    NetworkPolicy networkPolicy =
        new NetworkPolicy(
            buildTemplateCarrierMetered(str),
            NetworkPolicy.buildRule(ZonedDateTime.now().getDayOfMonth(), ZoneId.systemDefault()),
            getPlatformDefaultWarningBytes(),
            getPlatformDefaultLimitBytes(),
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

  public static NetworkTemplate buildTemplateCarrierMetered(String str) {
    Objects.requireNonNull(str);
    return new NetworkTemplate.Builder(10).setSubscriberIds(Set.of(str)).setMeteredness(1).build();
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
    SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
    if (!ArrayUtils.isEmpty(subscriptionPlanArr)) {
      PersistableBundle persistableBundle = (PersistableBundle) this.mSubIdToCarrierConfig.get(i);
      SubscriptionPlan subscriptionPlan = subscriptionPlanArr[0];
      networkPolicy.cycleRule =
          NetworkPolicy.buildRule(
              getCycleDayFromCarrierConfig(
                  persistableBundle,
                  networkPolicy.cycleRule.isMonthly()
                      ? networkPolicy.cycleRule.start.getDayOfMonth()
                      : -1),
              ZoneId.systemDefault());
      long dataLimitBytes = subscriptionPlan.getDataLimitBytes();
      if (dataLimitBytes == -1) {
        networkPolicy.warningBytes =
            getWarningBytesFromCarrierConfig(persistableBundle, networkPolicy.warningBytes);
        networkPolicy.limitBytes =
            getLimitBytesFromCarrierConfig(persistableBundle, networkPolicy.limitBytes);
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
    } else {
      PersistableBundle persistableBundle2 = (PersistableBundle) this.mSubIdToCarrierConfig.get(i);
      networkPolicy.cycleRule =
          NetworkPolicy.buildRule(
              getCycleDayFromCarrierConfig(
                  persistableBundle2,
                  networkPolicy.cycleRule.isMonthly()
                      ? networkPolicy.cycleRule.start.getDayOfMonth()
                      : -1),
              ZoneId.systemDefault());
      networkPolicy.warningBytes =
          getWarningBytesFromCarrierConfig(persistableBundle2, networkPolicy.warningBytes);
      networkPolicy.limitBytes =
          getLimitBytesFromCarrierConfig(persistableBundle2, networkPolicy.limitBytes);
    }
    if (networkPolicy.equals(networkPolicy2)) {
      return false;
    }
    Slog.d("NetworkPolicy", "Updated " + networkPolicy2 + " to " + networkPolicy);
    return true;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:42:0x0124 A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0138 A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:47:0x0147 A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:50:0x015d A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:52:0x016a A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:55:0x017b A[Catch: all -> 0x02c9, Exception -> 0x02cc, FileNotFoundException -> 0x02cf, TryCatch #5 {FileNotFoundException -> 0x02cf, Exception -> 0x02cc, all -> 0x02c9, blocks: (B:8:0x0029, B:9:0x0035, B:11:0x003b, B:14:0x0046, B:16:0x004f, B:18:0x0058, B:21:0x0064, B:25:0x006a, B:27:0x0073, B:29:0x0085, B:32:0x0092, B:35:0x00b2, B:36:0x00f7, B:38:0x010a, B:42:0x0124, B:44:0x0138, B:47:0x0147, B:48:0x0152, B:50:0x015d, B:52:0x016a, B:53:0x0171, B:55:0x017b, B:66:0x0117, B:68:0x00d9, B:70:0x00e2, B:71:0x00ed, B:74:0x00a3, B:78:0x018b, B:81:0x01a0, B:83:0x01ae, B:84:0x01b3, B:85:0x01c9, B:87:0x01d1, B:89:0x01e6, B:90:0x01ea, B:91:0x0200, B:94:0x0209, B:97:0x0214, B:98:0x021d, B:101:0x0228, B:105:0x0237, B:112:0x0242, B:114:0x0249, B:116:0x0258, B:119:0x027a, B:121:0x0280, B:123:0x0286, B:124:0x02a7, B:126:0x02ac), top: B:7:0x0029 }] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0150  */
  /* JADX WARN: Removed duplicated region for block: B:59:0x0142  */
  /* JADX WARN: Removed duplicated region for block: B:60:0x012e  */
  /* JADX WARN: Type inference failed for: r7v23 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void readPolicyAL() {
    FileInputStream openRead;
    int i;
    String str;
    int i2;
    int i3;
    Object obj;
    String str2;
    RecurrenceRule buildRule;
    Object obj2;
    long j;
    long readLongAttribute;
    boolean z;
    NetworkTemplate build;
    if (LOGV) {
      Slog.v("NetworkPolicy", "readPolicyAL()");
    }
    this.mNetworkPolicy.clear();
    this.mSubscriptionPlans.clear();
    this.mSubscriptionPlansOwner.clear();
    this.mUidPolicy.clear();
    FileInputStream fileInputStream = null;
    String str3 = null;
    fileInputStream = null;
    fileInputStream = null;
    fileInputStream = null;
    try {
      try {
        openRead = this.mPolicyFile.openRead();
      } catch (Throwable th) {
        th = th;
      }
      try {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int i4 = 1;
        int i5 = 1;
        boolean z2 = false;
        while (true) {
          int next = resolvePullParser.next();
          if (next == i4) {
            break;
          }
          String name = resolvePullParser.getName();
          if (next == 2) {
            if ("policy-list".equals(name)) {
              i5 = XmlUtils.readIntAttribute(resolvePullParser, "version");
              this.mLoadedRestrictBackground =
                  (i5 < 3
                          || !XmlUtils.readBooleanAttribute(
                              resolvePullParser, "restrictBackground"))
                      ? 0
                      : i4;
              i = i4;
              str = str3;
            } else {
              if ("network-policy".equals(name)) {
                int readIntAttribute =
                    XmlUtils.readIntAttribute(resolvePullParser, "networkTemplate");
                String attributeValue = resolvePullParser.getAttributeValue(str3, "subscriberId");
                String attributeValue2 =
                    i5 >= 9 ? resolvePullParser.getAttributeValue(str3, "networkId") : str3;
                if (i5 >= 13) {
                  i3 = XmlUtils.readIntAttribute(resolvePullParser, "subscriberIdMatchRule");
                  i2 = XmlUtils.readIntAttribute(resolvePullParser, "templateMetered");
                } else {
                  if (readIntAttribute == i4) {
                    Log.d(
                        "NetworkPolicy",
                        "Update template match rule from mobile to carrier and force to metered");
                    readIntAttribute = 10;
                    i2 = i4;
                  } else {
                    i2 = -1;
                  }
                  i3 = 0;
                }
                if (i5 >= 11) {
                  buildRule =
                      new RecurrenceRule(
                          RecurrenceRule.convertZonedDateTime(
                              XmlUtils.readStringAttribute(resolvePullParser, "cycleStart")),
                          RecurrenceRule.convertZonedDateTime(
                              XmlUtils.readStringAttribute(resolvePullParser, "cycleEnd")),
                          RecurrenceRule.convertPeriod(
                              XmlUtils.readStringAttribute(resolvePullParser, "cyclePeriod")));
                  obj2 = null;
                } else {
                  int readIntAttribute2 = XmlUtils.readIntAttribute(resolvePullParser, "cycleDay");
                  if (i5 >= 6) {
                    obj = null;
                    str2 = resolvePullParser.getAttributeValue((String) null, "cycleTimezone");
                  } else {
                    obj = null;
                    str2 = "UTC";
                  }
                  buildRule = NetworkPolicy.buildRule(readIntAttribute2, ZoneId.of(str2));
                  obj2 = obj;
                }
                long readLongAttribute2 =
                    XmlUtils.readLongAttribute(resolvePullParser, "warningBytes");
                long readLongAttribute3 =
                    XmlUtils.readLongAttribute(resolvePullParser, "limitBytes");
                if (i5 >= 5) {
                  readLongAttribute =
                      XmlUtils.readLongAttribute(resolvePullParser, "lastLimitSnooze");
                } else if (i5 >= 2) {
                  readLongAttribute = XmlUtils.readLongAttribute(resolvePullParser, "lastSnooze");
                } else {
                  j = -1;
                  if (i5 < 4) {
                    z = XmlUtils.readBooleanAttribute(resolvePullParser, "metered");
                  } else {
                    z = readIntAttribute == 1;
                  }
                  long readLongAttribute4 =
                      i5 < 5
                          ? XmlUtils.readLongAttribute(resolvePullParser, "lastWarningSnooze")
                          : -1L;
                  boolean readBooleanAttribute =
                      i5 < 7 ? XmlUtils.readBooleanAttribute(resolvePullParser, "inferred") : false;
                  NetworkTemplate.Builder meteredness =
                      new NetworkTemplate.Builder(readIntAttribute).setMeteredness(i2);
                  if (i3 == 0) {
                    ArraySet arraySet = new ArraySet();
                    arraySet.add(attributeValue);
                    meteredness.setSubscriberIds(arraySet);
                  }
                  if (attributeValue2 != null) {
                    meteredness.setWifiNetworkKeys(Set.of(attributeValue2));
                  }
                  build = meteredness.build();
                  str = obj2;
                  if (NetworkPolicy.isTemplatePersistable(build)) {
                    this.mNetworkPolicy.put(
                        build,
                        new NetworkPolicy(
                            build,
                            buildRule,
                            readLongAttribute2,
                            readLongAttribute3,
                            readLongAttribute4,
                            j,
                            z,
                            readBooleanAttribute));
                    str = obj2;
                  }
                }
                j = readLongAttribute;
                if (i5 < 4) {}
                if (i5 < 5) {}
                if (i5 < 7) {}
                NetworkTemplate.Builder meteredness2 =
                    new NetworkTemplate.Builder(readIntAttribute).setMeteredness(i2);
                if (i3 == 0) {}
                if (attributeValue2 != null) {}
                build = meteredness2.build();
                str = obj2;
                if (NetworkPolicy.isTemplatePersistable(build)) {}
              } else {
                str = str3;
                if ("uid-policy".equals(name)) {
                  int readIntAttribute3 = XmlUtils.readIntAttribute(resolvePullParser, "uid");
                  int readIntAttribute4 = XmlUtils.readIntAttribute(resolvePullParser, "policy");
                  if (UserHandle.isApp(readIntAttribute3)) {
                    setUidPolicyUncheckedUL(readIntAttribute3, readIntAttribute4, false);
                    str = str;
                  } else {
                    Slog.w(
                        "NetworkPolicy",
                        "unable to apply policy to UID " + readIntAttribute3 + "; ignoring");
                    str = str;
                  }
                } else if ("app-policy".equals(name)) {
                  int readIntAttribute5 = XmlUtils.readIntAttribute(resolvePullParser, "appId");
                  int readIntAttribute6 = XmlUtils.readIntAttribute(resolvePullParser, "policy");
                  int uid = UserHandle.getUid(0, readIntAttribute5);
                  if (UserHandle.isApp(uid)) {
                    setUidPolicyUncheckedUL(uid, readIntAttribute6, false);
                    str = str;
                  } else {
                    Slog.w("NetworkPolicy", "unable to apply policy to UID " + uid + "; ignoring");
                    str = str;
                  }
                } else if ("whitelist".equals(name)) {
                  z2 = true;
                  str = str;
                } else if ("restrict-background".equals(name) && z2) {
                  sparseBooleanArray.append(
                      XmlUtils.readIntAttribute(resolvePullParser, "uid"), true);
                  str = str;
                } else {
                  str = str;
                  str = str;
                  if ("revoked-restrict-background".equals(name) && z2) {
                    i = 1;
                    this.mRestrictBackgroundAllowlistRevokedUids.put(
                        XmlUtils.readIntAttribute(resolvePullParser, "uid"), true);
                  }
                }
              }
              i = 1;
            }
          } else {
            i = i4;
            str = str3;
            if (next == 3 && "whitelist".equals(name)) {
              z2 = false;
            }
          }
          str3 = str;
          i4 = i;
        }
        int size = sparseBooleanArray.size();
        for (int i6 = 0; i6 < size; i6++) {
          int keyAt = sparseBooleanArray.keyAt(i6);
          int i7 = this.mUidPolicy.get(keyAt, 0);
          if ((i7 & 1) != 0) {
            Slog.w(
                "NetworkPolicy",
                "ignoring restrict-background-allowlist for "
                    + keyAt
                    + " because its policy is "
                    + NetworkPolicyManager.uidPoliciesToString(i7));
          } else if (UserHandle.isApp(keyAt)) {
            int i8 = i7 | 4;
            if (LOGV) {
              Log.v(
                  "NetworkPolicy",
                  "new policy for " + keyAt + ": " + NetworkPolicyManager.uidPoliciesToString(i8));
            }
            setUidPolicyUncheckedUL(keyAt, i8, false);
          } else {
            Slog.w("NetworkPolicy", "unable to update policy on UID " + keyAt);
          }
        }
        IoUtils.closeQuietly(openRead);
      } catch (FileNotFoundException unused) {
        fileInputStream = openRead;
        upgradeDefaultBackgroundDataUL();
        IoUtils.closeQuietly(fileInputStream);
      } catch (Exception e) {
        e = e;
        fileInputStream = openRead;
        Log.wtf("NetworkPolicy", "problem reading network policy", e);
        IoUtils.closeQuietly(fileInputStream);
      } catch (Throwable th2) {
        th = th2;
        fileInputStream = openRead;
        IoUtils.closeQuietly(fileInputStream);
        throw th;
      }
    } catch (FileNotFoundException unused2) {
    } catch (Exception e2) {
      e = e2;
    } catch (Throwable th3) {
      th = th3;
    }
  }

  public final void upgradeDefaultBackgroundDataUL() {
    this.mLoadedRestrictBackground =
        Settings.Global.getInt(
                this.mContext.getContentResolver(), "default_restrict_background_data", 0)
            == 1;
  }

  public final void upgradeWifiMeteredOverride() {
    int i;
    ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mNetworkPoliciesSecondLock) {
      int i2 = 0;
      while (i2 < this.mNetworkPolicy.size()) {
        NetworkPolicy networkPolicy = (NetworkPolicy) this.mNetworkPolicy.valueAt(i2);
        if (networkPolicy.template.getMatchRule() != 4 || networkPolicy.inferred) {
          i2++;
        } else {
          this.mNetworkPolicy.removeAt(i2);
          Set wifiNetworkKeys = networkPolicy.template.getWifiNetworkKeys();
          arrayMap.put(
              wifiNetworkKeys.isEmpty() ? null : (String) wifiNetworkKeys.iterator().next(),
              Boolean.valueOf(networkPolicy.metered));
        }
      }
    }
    if (arrayMap.isEmpty()) {
      return;
    }
    WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
    List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
    for (i = 0; i < configuredNetworks.size(); i++) {
      WifiConfiguration wifiConfiguration = configuredNetworks.get(i);
      Iterator it = wifiConfiguration.getAllNetworkKeys().iterator();
      while (true) {
        if (it.hasNext()) {
          String str = (String) it.next();
          Boolean bool = (Boolean) arrayMap.get(str);
          if (bool != null) {
            Slog.d("NetworkPolicy", "Found network " + str + "; upgrading metered hint");
            wifiConfiguration.meteredOverride = bool.booleanValue() ? 1 : 2;
            wifiManager.updateNetwork(wifiConfiguration);
          }
        }
      }
    }
    synchronized (this.mUidRulesFirstLock) {
      synchronized (this.mNetworkPoliciesSecondLock) {
        writePolicyAL();
      }
    }
  }

  public void writePolicyAL() {
    if (LOGV) {
      Slog.v("NetworkPolicy", "writePolicyAL()");
    }
    FileOutputStream fileOutputStream = null;
    try {
      FileOutputStream startWrite = this.mPolicyFile.startWrite();
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
            XmlUtils.writeLongAttribute(resolveSerializer, "limitBytes", networkPolicy.limitBytes);
            XmlUtils.writeLongAttribute(
                resolveSerializer, "lastWarningSnooze", networkPolicy.lastWarningSnooze);
            XmlUtils.writeLongAttribute(
                resolveSerializer, "lastLimitSnooze", networkPolicy.lastLimitSnooze);
            XmlUtils.writeBooleanAttribute(resolveSerializer, "metered", networkPolicy.metered);
            XmlUtils.writeBooleanAttribute(resolveSerializer, "inferred", networkPolicy.inferred);
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
      } catch (IOException unused) {
        fileOutputStream = startWrite;
        if (fileOutputStream != null) {
          this.mPolicyFile.failWrite(fileOutputStream);
        }
      }
    } catch (IOException unused2) {
    }
  }

  public void setUidPolicy(int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    if (!UserHandle.isApp(i)) {
      throw new IllegalArgumentException("cannot apply policy to UID " + i);
    }
    synchronized (this.mUidRulesFirstLock) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        int i3 = this.mUidPolicy.get(i, 0);
        if (i3 != i2) {
          setUidPolicyUncheckedUL(i, i3, i2, true);
          this.mLogger.uidPolicyChanged(i, i3, i2);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void addUidPolicy(int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    if (!UserHandle.isApp(i)) {
      throw new IllegalArgumentException("cannot apply policy to UID " + i);
    }
    synchronized (this.mUidRulesFirstLock) {
      int i3 = this.mUidPolicy.get(i, 0);
      int i4 = i2 | i3;
      if (i3 != i4) {
        setUidPolicyUncheckedUL(i, i3, i4, true);
        this.mLogger.uidPolicyChanged(i, i3, i4);
      }
    }
  }

  public void removeUidPolicy(int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    if (!UserHandle.isApp(i)) {
      throw new IllegalArgumentException("cannot apply policy to UID " + i);
    }
    synchronized (this.mUidRulesFirstLock) {
      int i3 = this.mUidPolicy.get(i, 0);
      int i4 = (~i2) & i3;
      if (i3 != i4) {
        setUidPolicyUncheckedUL(i, i3, i4, true);
        this.mLogger.uidPolicyChanged(i, i3, i4);
      }
    }
  }

  public final void setUidPolicyUncheckedUL(int i, int i2, int i3, boolean z) {
    boolean z2 = false;
    setUidPolicyUncheckedUL(i, i3, false);
    if (isUidValidForAllowlistRulesUL(i)) {
      boolean z3 = i2 == 1;
      boolean z4 = i3 == 1;
      boolean z5 = i2 == 4;
      boolean z6 = i3 == 4;
      boolean z7 = z3 || (this.mRestrictBackground && !z5);
      boolean z8 = z4 || (this.mRestrictBackground && !z6);
      if (z5
          && ((!z6 || z4)
              && this.mDefaultRestrictBackgroundAllowlistUids.get(i)
              && !this.mRestrictBackgroundAllowlistRevokedUids.get(i))) {
        if (LOGD) {
          Slog.d("NetworkPolicy", "Adding uid " + i + " to revoked restrict background allowlist");
        }
        this.mRestrictBackgroundAllowlistRevokedUids.append(i, true);
      }
      if (z7 != z8) {
        z2 = true;
      }
    }
    this.mHandler.obtainMessage(13, i, i3, Boolean.valueOf(z2)).sendToTarget();
    if (z) {
      synchronized (this.mNetworkPoliciesSecondLock) {
        writePolicyAL();
      }
    }
  }

  public final void setUidPolicyUncheckedUL(int i, int i2, boolean z) {
    if (i2 == 0) {
      this.mUidPolicy.delete(i);
    } else {
      this.mUidPolicy.put(i, i2);
    }
    lambda$updateRulesForRestrictBackgroundUL$7(i);
    if (z) {
      synchronized (this.mNetworkPoliciesSecondLock) {
        writePolicyAL();
      }
    }
  }

  public int getUidPolicy(int i) {
    int i2;
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (this.mUidRulesFirstLock) {
      i2 = this.mUidPolicy.get(i, 0);
    }
    return i2;
  }

  public int[] getUidsWithPolicy(int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    int[] iArr = new int[0];
    synchronized (this.mUidRulesFirstLock) {
      for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
        int keyAt = this.mUidPolicy.keyAt(i2);
        int valueAt = this.mUidPolicy.valueAt(i2);
        if ((i == 0 && valueAt == 0) || (valueAt & i) != 0) {
          iArr = ArrayUtils.appendInt(iArr, keyAt);
        }
      }
    }
    return iArr;
  }

  public boolean removeUserStateUL(int i, boolean z, boolean z2) {
    this.mLogger.removingUserState(i);
    boolean z3 = false;
    for (int size = this.mRestrictBackgroundAllowlistRevokedUids.size() - 1; size >= 0; size--) {
      if (UserHandle.getUserId(this.mRestrictBackgroundAllowlistRevokedUids.keyAt(size)) == i) {
        this.mRestrictBackgroundAllowlistRevokedUids.removeAt(size);
        z3 = true;
      }
    }
    int[] iArr = new int[0];
    for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
      int keyAt = this.mUidPolicy.keyAt(i2);
      if (UserHandle.getUserId(keyAt) == i) {
        iArr = ArrayUtils.appendInt(iArr, keyAt);
      }
    }
    if (iArr.length > 0) {
      for (int i3 : iArr) {
        this.mUidPolicy.delete(i3);
      }
      z3 = true;
    }
    synchronized (this.mNetworkPoliciesSecondLock) {
      if (z2) {
        try {
          updateRulesForGlobalChangeAL(true);
        } catch (Throwable th) {
          throw th;
        }
      }
      if (z && z3) {
        writePolicyAL();
      }
    }
    return z3;
  }

  public final boolean checkAnyPermissionOf(String... strArr) {
    for (String str : strArr) {
      if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
        return true;
      }
    }
    return false;
  }

  public final void enforceAnyPermissionOf(String... strArr) {
    if (checkAnyPermissionOf(strArr)) {
      return;
    }
    throw new SecurityException(
        "Requires one of the following permissions: " + String.join(", ", strArr) + ".");
  }

  public void registerListener(INetworkPolicyListener iNetworkPolicyListener) {
    Objects.requireNonNull(iNetworkPolicyListener);
    enforceAnyPermissionOf(
        "android.permission.CONNECTIVITY_INTERNAL", "android.permission.OBSERVE_NETWORK_POLICY");
    this.mListeners.register(iNetworkPolicyListener);
  }

  public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) {
    Objects.requireNonNull(iNetworkPolicyListener);
    enforceAnyPermissionOf(
        "android.permission.CONNECTIVITY_INTERNAL", "android.permission.OBSERVE_NETWORK_POLICY");
    this.mListeners.unregister(iNetworkPolicyListener);
  }

  public void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
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

  public void addNetworkPolicyAL(NetworkPolicy networkPolicy) {
    setNetworkPolicies(
        (NetworkPolicy[])
            ArrayUtils.appendElement(
                NetworkPolicy.class,
                getNetworkPolicies(this.mContext.getOpPackageName()),
                networkPolicy));
  }

  public NetworkPolicy[] getNetworkPolicies(String str) {
    NetworkPolicy[] networkPolicyArr;
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
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
      int size = this.mNetworkPolicy.size();
      networkPolicyArr = new NetworkPolicy[size];
      for (int i = 0; i < size; i++) {
        networkPolicyArr[i] = (NetworkPolicy) this.mNetworkPolicy.valueAt(i);
      }
    }
    return networkPolicyArr;
  }

  public final void normalizePoliciesNL() {
    normalizePoliciesNL(getNetworkPolicies(this.mContext.getOpPackageName()));
  }

  public final void normalizePoliciesNL(NetworkPolicy[] networkPolicyArr) {
    this.mNetworkPolicy.clear();
    for (NetworkPolicy networkPolicy : networkPolicyArr) {
      if (networkPolicy != null) {
        NetworkTemplate normalizeTemplate =
            normalizeTemplate(networkPolicy.template, this.mMergedSubscriberIds);
        networkPolicy.template = normalizeTemplate;
        NetworkPolicy networkPolicy2 = (NetworkPolicy) this.mNetworkPolicy.get(normalizeTemplate);
        if (networkPolicy2 == null || networkPolicy2.compareTo(networkPolicy) > 0) {
          if (networkPolicy2 != null) {
            Slog.d(
                "NetworkPolicy",
                "Normalization replaced " + networkPolicy2 + " with " + networkPolicy);
          }
          this.mNetworkPolicy.put(networkPolicy.template, networkPolicy);
        }
      }
    }
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
        Log.wtf("NetworkPolicy", "Duplicated merged list detected: " + Arrays.toString(strArr));
      }
      Iterator it2 = networkTemplate.getSubscriberIds().iterator();
      while (it2.hasNext()) {
        if (com.android.net.module.util.CollectionUtils.contains(strArr, (String) it2.next())) {
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

  public void snoozeLimit(NetworkTemplate networkTemplate) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      performSnooze(networkTemplate, 35);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void performSnooze(NetworkTemplate networkTemplate, int i) {
    long millis = this.mClock.millis();
    synchronized (this.mUidRulesFirstLock) {
      synchronized (this.mNetworkPoliciesSecondLock) {
        NetworkPolicy networkPolicy = (NetworkPolicy) this.mNetworkPolicy.get(networkTemplate);
        if (networkPolicy == null) {
          throw new IllegalArgumentException("unable to find policy for " + networkTemplate);
        }
        if (i == 34) {
          networkPolicy.lastWarningSnooze = millis;
        } else if (i == 35) {
          networkPolicy.lastLimitSnooze = millis;
        } else if (i == 45) {
          networkPolicy.lastRapidSnooze = millis;
        } else {
          throw new IllegalArgumentException("unexpected type");
        }
        handleNetworkPoliciesUpdateAL(true);
      }
    }
  }

  public void setRestrictBackground(boolean z) {
    Trace.traceBegin(2097152L, "setRestrictBackground");
    try {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (this.mUidRulesFirstLock) {
          setRestrictBackgroundUL(z, "uid:" + callingUid);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void setRestrictBackgroundUL(boolean z, String str) {
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
              "Could not change Data Saver Mode on NMS to " + this.mRestrictBackground);
          this.mRestrictBackground = z2;
          return;
        }
      } catch (RemoteException unused) {
      }
      sendRestrictBackgroundChangedMsg();
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

  public final void sendRestrictBackgroundChangedMsg() {
    this.mHandler.removeMessages(6);
    this.mHandler.obtainMessage(6, this.mRestrictBackground ? 1 : 0, 0).sendToTarget();
  }

  public int getRestrictBackgroundByCaller() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.ACCESS_NETWORK_STATE", "NetworkPolicy");
    return getRestrictBackgroundStatusInternal(Binder.getCallingUid());
  }

  public int getRestrictBackgroundStatus(int i) {
    PermissionUtils.enforceNetworkStackPermission(this.mContext);
    return getRestrictBackgroundStatusInternal(i);
  }

  public final int getRestrictBackgroundStatusInternal(int i) {
    synchronized (this.mUidRulesFirstLock) {
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
    }
  }

  public boolean getRestrictBackground() {
    boolean z;
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (this.mUidRulesFirstLock) {
      z = this.mRestrictBackground;
    }
    return z;
  }

  public void setDeviceIdleMode(boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    Trace.traceBegin(2097152L, "setDeviceIdleMode");
    try {
      synchronized (this.mUidRulesFirstLock) {
        if (this.mDeviceIdleMode == z) {
          return;
        }
        this.mDeviceIdleMode = z;
        this.mLogger.deviceIdleModeEnabled(z);
        if (this.mSystemReady) {
          handleDeviceIdleModeChangedUL(z);
        }
        if (z) {
          EventLogTags.writeDeviceIdleOnPhase("net");
        } else {
          EventLogTags.writeDeviceIdleOffPhase("net");
        }
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void setWifiMeteredOverride(String str, int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
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

  public final void enforceSubscriptionPlanAccess(int i, int i2, String str) {
    this.mAppOps.checkPackage(i2, str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      PersistableBundle configForSubId = this.mCarrierConfigManager.getConfigForSubId(i);
      TelephonyManager telephonyManager =
          (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
      if (telephonyManager == null || !telephonyManager.hasCarrierPrivileges(i)) {
        if (configForSubId != null) {
          String string = configForSubId.getString("config_plans_package_override_string", null);
          if (!TextUtils.isEmpty(string) && Objects.equals(string, str)) {
            return;
          }
        }
        String defaultCarrierServicePackageName =
            this.mCarrierConfigManager.getDefaultCarrierServicePackageName();
        if (TextUtils.isEmpty(defaultCarrierServicePackageName)
            || !Objects.equals(defaultCarrierServicePackageName, str)) {
          String str2 = SystemProperties.get("persist.sys.sub_plan_owner." + i, (String) null);
          if (TextUtils.isEmpty(str2) || !Objects.equals(str2, str)) {
            String str3 = SystemProperties.get("fw.sub_plan_owner." + i, (String) null);
            if (TextUtils.isEmpty(str3) || !Objects.equals(str3, str)) {
              this.mContext.enforceCallingOrSelfPermission(
                  "android.permission.MANAGE_SUBSCRIPTION_PLANS", "NetworkPolicy");
            }
          }
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void enforceSubscriptionPlanValidity(SubscriptionPlan[] subscriptionPlanArr) {
    if (subscriptionPlanArr.length == 0) {
      Log.d("NetworkPolicy", "Received empty plans list. Clearing existing SubscriptionPlans.");
      return;
    }
    int[] allNetworkTypes = TelephonyManager.getAllNetworkTypes();
    ArraySet arraySet = new ArraySet();
    addAll(arraySet, allNetworkTypes);
    ArraySet arraySet2 = new ArraySet();
    boolean z = false;
    for (SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
      int[] networkTypes = subscriptionPlan.getNetworkTypes();
      ArraySet arraySet3 = new ArraySet();
      for (int i = 0; i < networkTypes.length; i++) {
        if (arraySet.contains(Integer.valueOf(networkTypes[i]))) {
          if (!arraySet3.add(Integer.valueOf(networkTypes[i]))) {
            throw new IllegalArgumentException(
                "Subscription plan contains duplicate network types.");
          }
        } else {
          throw new IllegalArgumentException("Invalid network type: " + networkTypes[i]);
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

  public static boolean addAll(ArraySet arraySet, int... iArr) {
    boolean z = true;
    for (int i : iArr) {
      z &= arraySet.add(Integer.valueOf(i));
    }
    return z;
  }

  public SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) {
    SubscriptionPlan primarySubscriptionPlanLocked;
    enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
    synchronized (this.mNetworkPoliciesSecondLock) {
      primarySubscriptionPlanLocked =
          getPrimarySubscriptionPlanLocked(findRelevantSubIdNL(networkTemplate));
    }
    return primarySubscriptionPlanLocked;
  }

  public void notifyStatsProviderWarningOrLimitReached() {
    enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
    synchronized (this.mNetworkPoliciesSecondLock) {
      if (this.mSystemReady) {
        this.mHandler.obtainMessage(20).sendToTarget();
      }
    }
  }

  public SubscriptionPlan[] getSubscriptionPlans(int i, String str) {
    enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
    String str2 = SystemProperties.get("fw.fake_plan");
    if (!TextUtils.isEmpty(str2)) {
      ArrayList arrayList = new ArrayList();
      if ("month_hard".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 1)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(1L),
                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile Happy")
                .setDataLimit(Long.MAX_VALUE, 1)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile, Charged after limit")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 1)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli())
                .build());
      } else if ("month_soft".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile is the carriers name who this plan belongs to")
                .setSummary(
                    "Crazy unlimited bandwidth plan with incredibly long title that should be cut"
                        + " off to prevent UI from looking terrible")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 2)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(1L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile, Throttled after limit")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 2)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile, No data connection after limit")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 0)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
      } else if ("month_over".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile is the carriers name who this plan belongs to")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 2)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(6L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile, Throttled after limit")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 2)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2017-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile, No data connection after limit")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 0)
                .setDataUsage(
                    DataUnit.GIBIBYTES.toBytes(5L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
      } else if ("month_none".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createRecurringMonthly(
                    ZonedDateTime.parse("2007-03-14T00:00:00.000Z"))
                .setTitle("G-Mobile")
                .build());
      } else if ("prepaid".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createNonrecurring(
                    ZonedDateTime.now().minusDays(20L), ZonedDateTime.now().plusDays(10L))
                .setTitle("G-Mobile")
                .setDataLimit(DataUnit.MEBIBYTES.toBytes(512L), 0)
                .setDataUsage(
                    DataUnit.MEBIBYTES.toBytes(100L),
                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                .build());
      } else if ("prepaid_crazy".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createNonrecurring(
                    ZonedDateTime.now().minusDays(20L), ZonedDateTime.now().plusDays(10L))
                .setTitle("G-Mobile Anytime")
                .setDataLimit(DataUnit.MEBIBYTES.toBytes(512L), 0)
                .setDataUsage(
                    DataUnit.MEBIBYTES.toBytes(100L),
                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createNonrecurring(
                    ZonedDateTime.now().minusDays(10L), ZonedDateTime.now().plusDays(20L))
                .setTitle("G-Mobile Nickel Nights")
                .setSummary("5¢/GB between 1-5AM")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(5L), 2)
                .setDataUsage(
                    DataUnit.MEBIBYTES.toBytes(15L),
                    ZonedDateTime.now().minusHours(30L).toInstant().toEpochMilli())
                .build());
        arrayList.add(
            SubscriptionPlan.Builder.createNonrecurring(
                    ZonedDateTime.now().minusDays(10L), ZonedDateTime.now().plusDays(20L))
                .setTitle("G-Mobile Bonus 3G")
                .setSummary("Unlimited 3G data")
                .setDataLimit(DataUnit.GIBIBYTES.toBytes(1L), 2)
                .setDataUsage(
                    DataUnit.MEBIBYTES.toBytes(300L),
                    ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli())
                .build());
      } else if ("unlimited".equals(str2)) {
        arrayList.add(
            SubscriptionPlan.Builder.createNonrecurring(
                    ZonedDateTime.now().minusDays(20L), ZonedDateTime.now().plusDays(10L))
                .setTitle("G-Mobile Awesome")
                .setDataLimit(Long.MAX_VALUE, 2)
                .setDataUsage(
                    DataUnit.MEBIBYTES.toBytes(50L),
                    ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli())
                .build());
      }
      return (SubscriptionPlan[]) arrayList.toArray(new SubscriptionPlan[arrayList.size()]);
    }
    synchronized (this.mNetworkPoliciesSecondLock) {
      String str3 = (String) this.mSubscriptionPlansOwner.get(i);
      if (!Objects.equals(str3, str)
          && UserHandle.getCallingAppId() != 1000
          && UserHandle.getCallingAppId() != 1001) {
        Log.w(
            "NetworkPolicy",
            "Not returning plans because caller " + str + " doesn't match owner " + str3);
        return null;
      }
      return (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
    }
  }

  public void setSubscriptionPlans(
      int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) {
    enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
    enforceSubscriptionPlanValidity(subscriptionPlanArr);
    for (SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
      Objects.requireNonNull(subscriptionPlan);
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
        Intent intent = new Intent("android.telephony.action.SUBSCRIPTION_PLANS_CHANGED");
        intent.addFlags(1073741824);
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        this.mContext.sendBroadcast(intent, "android.permission.MANAGE_SUBSCRIPTION_PLANS");
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(19, i, 0, subscriptionPlanArr));
        int i2 = this.mSetSubscriptionPlansIdCounter;
        this.mSetSubscriptionPlansIdCounter = i2 + 1;
        this.mSetSubscriptionPlansIds.put(i, i2);
        if (j > 0) {
          Handler handler2 = this.mHandler;
          handler2.sendMessageDelayed(handler2.obtainMessage(22, i, i2, str), j);
        }
      }
    }
  }

  public void setSubscriptionPlansOwner(int i, String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.NETWORK_SETTINGS", "NetworkPolicy");
    SystemProperties.set("persist.sys.sub_plan_owner." + i, str);
  }

  public String getSubscriptionPlansOwner(int i) {
    String str;
    if (UserHandle.getCallingAppId() != 1000) {
      throw new SecurityException();
    }
    synchronized (this.mNetworkPoliciesSecondLock) {
      str = (String) this.mSubscriptionPlansOwner.get(i);
    }
    return str;
  }

  public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str) {
    enforceSubscriptionPlanAccess(i, Binder.getCallingUid(), str);
    ArraySet arraySet = new ArraySet();
    addAll(arraySet, TelephonyManager.getAllNetworkTypes());
    IntArray intArray = new IntArray();
    for (int i4 : iArr) {
      if (arraySet.contains(Integer.valueOf(i4))) {
        intArray.add(i4);
      } else {
        Log.d("NetworkPolicy", "setSubscriptionOverride removing invalid network type: " + i4);
      }
    }
    synchronized (this.mNetworkPoliciesSecondLock) {
      SubscriptionPlan primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(i);
      if ((i2 != 1 && primarySubscriptionPlanLocked == null)
          || primarySubscriptionPlanLocked.getDataLimitBehavior() == -1) {
        throw new IllegalStateException("Must provide valid SubscriptionPlan to enable overriding");
      }
    }
    if ((Settings.Global.getInt(this.mContext.getContentResolver(), "netpolicy_override_enabled", 1)
            != 0)
        || i3 == 0) {
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
  }

  public int getMultipathPreference(Network network) {
    PermissionUtils.enforceNetworkStackPermission(this.mContext);
    Integer multipathPreference = this.mMultipathPolicyTracker.getMultipathPreference(network);
    if (multipathPreference != null) {
      return multipathPreference.intValue();
    }
    return 0;
  }

  @NeverCompile
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "NetworkPolicy", printWriter)) {
      IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
      ArraySet arraySet = new ArraySet(strArr.length);
      for (String str : strArr) {
        arraySet.add(str);
      }
      writeNetstatsFiles();
      synchronized (this.mUidRulesFirstLock) {
        synchronized (this.mNetworkPoliciesSecondLock) {
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
          indentingPrintWriter.println(
              "mRestrictBackgroundLowPowerMode: " + this.mRestrictBackgroundLowPowerMode);
          indentingPrintWriter.println(
              "mRestrictBackgroundBeforeBsm: " + this.mRestrictBackgroundBeforeBsm);
          indentingPrintWriter.println(
              "mLoadedRestrictBackground: " + this.mLoadedRestrictBackground);
          indentingPrintWriter.println(
              "mRestrictBackgroundChangedInBsm: " + this.mRestrictBackgroundChangedInBsm);
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
                "Subscriber ID "
                    + this.mSubscriptionPlans.keyAt(i2)
                    + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
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
                        (String) this.mSubIdToSubscriberId.valueAt(i3)));
          }
          indentingPrintWriter.decreaseIndent();
          indentingPrintWriter.println();
          Iterator it = this.mMergedSubscriberIds.iterator();
          while (it.hasNext()) {
            indentingPrintWriter.println(
                "Merged subscriptions: "
                    + Arrays.toString(
                        NetworkIdentityUtils.scrubSubscriberIds((String[]) it.next())));
          }
          if (this.mTetheringWarningBytes.size() > 0) {
            indentingPrintWriter.println();
            indentingPrintWriter.println(
                "mTetheringWarningBytes: " + this.mTetheringWarningBytes.toString());
            indentingPrintWriter.println("mTetheringNotiSnooze: " + this.mTetheringNotiSnooze);
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
            indentingPrintWriter.print(NetworkPolicyManager.uidPoliciesToString(valueAt));
            indentingPrintWriter.println();
          }
          indentingPrintWriter.decreaseIndent();
          int size3 = this.mPowerSaveWhitelistExceptIdleAppIds.size();
          if (size3 > 0) {
            indentingPrintWriter.println("Power save whitelist (except idle) app ids:");
            indentingPrintWriter.increaseIndent();
            for (int i5 = 0; i5 < size3; i5++) {
              indentingPrintWriter.print("UID=");
              indentingPrintWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i5));
              indentingPrintWriter.print(": ");
              indentingPrintWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.valueAt(i5));
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
              indentingPrintWriter.print(this.mPowerSaveWhitelistAppIds.keyAt(i6));
              indentingPrintWriter.print(": ");
              indentingPrintWriter.print(this.mPowerSaveWhitelistAppIds.valueAt(i6));
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
              indentingPrintWriter.print(this.mAppIdleTempWhitelistAppIds.keyAt(i7));
              indentingPrintWriter.print(": ");
              indentingPrintWriter.print(this.mAppIdleTempWhitelistAppIds.valueAt(i7));
              indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
          }
          int size6 = this.mDefaultRestrictBackgroundAllowlistUids.size();
          if (size6 > 0) {
            indentingPrintWriter.println("Default restrict background allowlist uids:");
            indentingPrintWriter.increaseIndent();
            for (int i8 = 0; i8 < size6; i8++) {
              indentingPrintWriter.print("UID=");
              indentingPrintWriter.print(this.mDefaultRestrictBackgroundAllowlistUids.keyAt(i8));
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
              indentingPrintWriter.print(this.mRestrictBackgroundAllowlistRevokedUids.keyAt(i9));
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
              indentingPrintWriter.print(this.mLowPowerStandbyAllowlistUids.keyAt(i10));
              indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
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
          int size9 = sparseBooleanArray.size();
          for (int i11 = 0; i11 < size9; i11++) {
            int keyAt2 = sparseBooleanArray.keyAt(i11);
            indentingPrintWriter.print("UID", Integer.valueOf(keyAt2));
            indentingPrintWriter.print(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN,
                (NetworkPolicyManager.UidState) this.mUidState.get(keyAt2));
            synchronized (this.mUidBlockedState) {
              indentingPrintWriter.print(
                  "blocked_state", (UidBlockedState) this.mUidBlockedState.get(keyAt2));
            }
            synchronized (this.mUidStateCallbackInfos) {
              UidStateCallbackInfo uidStateCallbackInfo =
                  (UidStateCallbackInfo) this.mUidStateCallbackInfos.get(keyAt2);
              indentingPrintWriter.println();
              indentingPrintWriter.increaseIndent();
              indentingPrintWriter.print("callback_info", uidStateCallbackInfo);
              indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
          }
          indentingPrintWriter.decreaseIndent();
          indentingPrintWriter.println("Admin restricted uids for metered data:");
          indentingPrintWriter.increaseIndent();
          int size10 = this.mMeteredRestrictedUids.size();
          for (int i12 = 0; i12 < size10; i12++) {
            indentingPrintWriter.print("u" + this.mMeteredRestrictedUids.keyAt(i12) + ": ");
            indentingPrintWriter.println(this.mMeteredRestrictedUids.valueAt(i12));
          }
          indentingPrintWriter.decreaseIndent();
          indentingPrintWriter.println("Network to interfaces:");
          indentingPrintWriter.increaseIndent();
          for (int i13 = 0; i13 < this.mNetworkToIfaces.size(); i13++) {
            int keyAt3 = this.mNetworkToIfaces.keyAt(i13);
            indentingPrintWriter.println(keyAt3 + ": " + this.mNetworkToIfaces.get(keyAt3));
          }
          indentingPrintWriter.decreaseIndent();
          indentingPrintWriter.println();
          this.mStatLogger.dump(indentingPrintWriter);
          this.mLogger.dumpLogs(indentingPrintWriter);
          indentingPrintWriter.println();
          this.mMultipathPolicyTracker.dump(indentingPrintWriter);
        }
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public int handleShellCommand(
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

  public void setDebugUid(int i) {
    this.mLogger.setDebugUid(i);
  }

  public boolean isUidForegroundOnRestrictBackgroundUL(int i) {
    NetworkPolicyManager.UidState uidState = (NetworkPolicyManager.UidState) this.mUidState.get(i);
    if (NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)) {
      return true;
    }
    synchronized (this.mUidStateCallbackInfos) {
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
    }
  }

  public boolean isUidForegroundOnRestrictPowerUL(int i) {
    NetworkPolicyManager.UidState uidState = (NetworkPolicyManager.UidState) this.mUidState.get(i);
    if (NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState)) {
      return true;
    }
    synchronized (this.mUidStateCallbackInfos) {
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
    }
  }

  public final boolean isUidTop(int i) {
    return NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(
        (NetworkPolicyManager.UidState) this.mUidState.get(i));
  }

  public final boolean updateUidStateUL(int i, int i2, long j, int i3) {
    Trace.traceBegin(2097152L, "updateUidStateUL");
    try {
      NetworkPolicyManager.UidState uidState =
          (NetworkPolicyManager.UidState) this.mUidState.get(i);
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
      NetworkPolicyManager.UidState uidState2 = new NetworkPolicyManager.UidState(i, i2, j, i3);
      this.mUidState.put(i, uidState2);
      updateRestrictBackgroundRulesOnUidStatusChangedUL(i, uidState, uidState2);
      boolean z =
          NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState)
              != NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState2);
      if (z) {
        updateRuleForAppIdleUL(i, i2);
        if (this.mDeviceIdleMode) {
          updateRuleForDeviceIdleUL(i);
        }
        if (this.mRestrictPower) {
          updateRuleForRestrictPowerUL(i);
        }
        updateRulesForPowerRestrictionsUL(i, i2);
      }
      if (this.mLowPowerStandbyActive) {
        if (NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(uidState)
            != NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(uidState2)) {
          if (!z) {
            updateRulesForPowerRestrictionsUL(i, i2);
          }
          updateRuleForLowPowerStandbyUL(i);
        }
      }
      return true;
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final boolean removeUidStateUL(int i) {
    int indexOfKey = this.mUidState.indexOfKey(i);
    if (indexOfKey < 0) {
      return false;
    }
    NetworkPolicyManager.UidState uidState =
        (NetworkPolicyManager.UidState) this.mUidState.valueAt(indexOfKey);
    this.mUidState.removeAt(indexOfKey);
    if (uidState == null) {
      return false;
    }
    updateRestrictBackgroundRulesOnUidStatusChangedUL(i, uidState, null);
    if (this.mDeviceIdleMode) {
      updateRuleForDeviceIdleUL(i);
    }
    if (this.mRestrictPower) {
      updateRuleForRestrictPowerUL(i);
    }
    lambda$updateRulesForRestrictPowerUL$6(i);
    if (!this.mLowPowerStandbyActive) {
      return true;
    }
    updateRuleForLowPowerStandbyUL(i);
    return true;
  }

  public final void updateNetworkStats(int i, boolean z) {
    if (Trace.isTagEnabled(2097152L)) {
      StringBuilder sb = new StringBuilder();
      sb.append("updateNetworkStats: ");
      sb.append(i);
      sb.append("/");
      sb.append(z ? "F" : "B");
      Trace.traceBegin(2097152L, sb.toString());
    }
    try {
      this.mNetworkStats.noteUidForeground(i, z);
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void updateRestrictBackgroundRulesOnUidStatusChangedUL(
      int i, NetworkPolicyManager.UidState uidState, NetworkPolicyManager.UidState uidState2) {
    if (NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)
        != NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState2)) {
      lambda$updateRulesForRestrictBackgroundUL$7(i);
    }
  }

  public boolean isRestrictedModeEnabled() {
    boolean z;
    synchronized (this.mUidRulesFirstLock) {
      z = this.mRestrictedNetworkingMode;
    }
    return z;
  }

  public void updateRestrictedModeAllowlistUL() {
    this.mUidFirewallRestrictedModeRules.clear();
    forEachUid(
        "updateRestrictedModeAllowlist",
        new IntConsumer() { // from class:
                            // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda0
          @Override // java.util.function.IntConsumer
          public final void accept(int i) {
            NetworkPolicyManagerService.this.lambda$updateRestrictedModeAllowlistUL$4(i);
          }
        });
    if (this.mRestrictedNetworkingMode) {
      setUidFirewallRulesUL(4, this.mUidFirewallRestrictedModeRules);
    }
    enableFirewallChainUL(4, this.mRestrictedNetworkingMode);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateRestrictedModeAllowlistUL$4(int i) {
    synchronized (this.mUidRulesFirstLock) {
      int restrictedModeFirewallRule =
          getRestrictedModeFirewallRule(updateBlockedReasonsForRestrictedModeUL(i));
      if (restrictedModeFirewallRule != 0) {
        this.mUidFirewallRestrictedModeRules.append(i, restrictedModeFirewallRule);
      }
    }
  }

  public void updateRestrictedModeForUidUL(int i) {
    int updateBlockedReasonsForRestrictedModeUL = updateBlockedReasonsForRestrictedModeUL(i);
    if (this.mRestrictedNetworkingMode) {
      setUidFirewallRuleUL(
          4, i, getRestrictedModeFirewallRule(updateBlockedReasonsForRestrictedModeUL));
    }
  }

  public final int updateBlockedReasonsForRestrictedModeUL(int i) {
    int i2;
    int i3;
    int deriveUidRules;
    boolean hasRestrictedModeAccess = hasRestrictedModeAccess(i);
    synchronized (this.mUidBlockedState) {
      UidBlockedState orCreateUidBlockedStateForUid =
          getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
      i2 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
      if (this.mRestrictedNetworkingMode) {
        orCreateUidBlockedStateForUid.blockedReasons |= 8;
      } else {
        orCreateUidBlockedStateForUid.blockedReasons &= -9;
      }
      if (hasRestrictedModeAccess) {
        orCreateUidBlockedStateForUid.allowedReasons |= 16;
      } else {
        orCreateUidBlockedStateForUid.allowedReasons &= -17;
      }
      orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
      i3 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
      deriveUidRules = i2 == i3 ? 0 : orCreateUidBlockedStateForUid.deriveUidRules();
    }
    if (i2 != i3) {
      handleBlockedReasonsChanged(i, i3, i2);
      postUidRulesChangedMsg(i, deriveUidRules);
    }
    return i3;
  }

  public final boolean hasRestrictedModeAccess(int i) {
    try {
      if (this.mIPm.checkUidPermission("android.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS", i)
              != 0
          && this.mIPm.checkUidPermission("android.permission.NETWORK_STACK", i) != 0) {
        if (this.mIPm.checkUidPermission("android.permission.MAINLINE_NETWORK_STACK", i) != 0) {
          return false;
        }
      }
      return true;
    } catch (RemoteException unused) {
      return false;
    }
  }

  public void updateRulesForPowerSaveUL() {
    Trace.traceBegin(2097152L, "updateRulesForPowerSaveUL");
    try {
      updateRulesForWhitelistedPowerSaveUL(this.mRestrictPower, 3, this.mUidFirewallPowerSaveRules);
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void updateRuleForRestrictPowerUL(int i) {
    updateRulesForWhitelistedPowerSaveUL(i, this.mRestrictPower, 3);
  }

  public void updateRulesForDeviceIdleUL() {
    Trace.traceBegin(2097152L, "updateRulesForDeviceIdleUL");
    try {
      updateRulesForWhitelistedPowerSaveUL(this.mDeviceIdleMode, 1, this.mUidFirewallDozableRules);
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void updateRuleForDeviceIdleUL(int i) {
    updateRulesForWhitelistedPowerSaveUL(i, this.mDeviceIdleMode, 1);
  }

  public final void updateRulesForWhitelistedPowerSaveUL(
      boolean z, int i, SparseIntArray sparseIntArray) {
    if (z) {
      sparseIntArray.clear();
      List users = this.mUserManager.getUsers();
      for (int size = users.size() - 1; size >= 0; size--) {
        UserInfo userInfo = (UserInfo) users.get(size);
        updateRulesForWhitelistedAppIds(
            sparseIntArray, this.mPowerSaveTempWhitelistAppIds, userInfo.id);
        updateRulesForWhitelistedAppIds(
            sparseIntArray, this.mPowerSaveWhitelistAppIds, userInfo.id);
        if (i == 3) {
          updateRulesForWhitelistedAppIds(
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
      return;
    }
    setUidFirewallRulesUL(i, null, 2);
  }

  public final void updateRulesForWhitelistedAppIds(
      SparseIntArray sparseIntArray, SparseBooleanArray sparseBooleanArray, int i) {
    for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
      if (sparseBooleanArray.valueAt(size)) {
        sparseIntArray.put(UserHandle.getUid(i, sparseBooleanArray.keyAt(size)), 1);
      }
    }
  }

  public void updateRulesForLowPowerStandbyUL() {
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
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void updateRuleForLowPowerStandbyUL(int i) {
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

  public final boolean isWhitelistedFromPowerSaveUL(int i, boolean z) {
    int appId = UserHandle.getAppId(i);
    boolean z2 =
        this.mPowerSaveTempWhitelistAppIds.get(appId) || this.mPowerSaveWhitelistAppIds.get(appId);
    if (z) {
      return z2;
    }
    return z2 || isWhitelistedFromPowerSaveExceptIdleUL(i);
  }

  public final boolean isWhitelistedFromPowerSaveExceptIdleUL(int i) {
    return this.mPowerSaveWhitelistExceptIdleAppIds.get(UserHandle.getAppId(i));
  }

  public final boolean isAllowlistedFromLowPowerStandbyUL(int i) {
    return this.mLowPowerStandbyAllowlistUids.get(i);
  }

  public final void updateRulesForWhitelistedPowerSaveUL(int i, boolean z, int i2) {
    if (z) {
      if (isWhitelistedFromPowerSaveUL(i, i2 == 1) || isUidForegroundOnRestrictPowerUL(i)) {
        setUidFirewallRuleUL(i2, i, 1);
      } else {
        setUidFirewallRuleUL(i2, i, 0);
      }
    }
  }

  public void updateRulesForAppIdleUL() {
    Trace.traceBegin(2097152L, "updateRulesForAppIdleUL");
    try {
      SparseIntArray sparseIntArray = this.mUidFirewallStandbyRules;
      sparseIntArray.clear();
      List users = this.mUserManager.getUsers();
      for (int size = users.size() - 1; size >= 0; size--) {
        for (int i : this.mUsageStats.getIdleUidsForUser(((UserInfo) users.get(size)).id)) {
          if (!this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(i), false)
              && hasInternetPermissionUL(i)
              && !isUidForegroundOnRestrictPowerUL(i)) {
            sparseIntArray.put(i, 2);
          }
        }
      }
      setUidFirewallRulesUL(2, sparseIntArray, 0);
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void updateRuleForAppIdleUL(int i, int i2) {
    if (isUidValidForDenylistRulesUL(i)) {
      if (Trace.isTagEnabled(2097152L)) {
        Trace.traceBegin(2097152L, "updateRuleForAppIdleUL: " + i);
      }
      try {
        if (!this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(i))
            && isUidIdle(i, i2)
            && !isUidForegroundOnRestrictPowerUL(i)) {
          setUidFirewallRuleUL(2, i, 2);
          if (LOGD) {
            Log.d("NetworkPolicy", "updateRuleForAppIdleUL DENY " + i);
          }
        } else {
          setUidFirewallRuleUL(2, i, 0);
          if (LOGD) {
            Log.d("NetworkPolicy", "updateRuleForAppIdleUL " + i + " to DEFAULT");
          }
        }
      } finally {
        Trace.traceEnd(2097152L);
      }
    }
  }

  public final void updateRulesForAppIdleParoleUL() {
    boolean isInParole = this.mAppStandby.isInParole();
    boolean z = !isInParole;
    int size = this.mUidFirewallStandbyRules.size();
    SparseIntArray sparseIntArray = new SparseIntArray();
    int i = 0;
    while (true) {
      if (i >= size) {
        break;
      }
      int keyAt = this.mUidFirewallStandbyRules.keyAt(i);
      if (isUidValidForDenylistRulesUL(keyAt)) {
        int blockedReasons = getBlockedReasons(keyAt);
        if (z || (blockedReasons & GnssNative.GNSS_AIDING_TYPE_ALL) != 0) {
          boolean z2 = !isInParole && isUidIdle(keyAt);
          if (z2
              && !this.mPowerSaveTempWhitelistAppIds.get(UserHandle.getAppId(keyAt))
              && !isUidForegroundOnRestrictPowerUL(keyAt)) {
            this.mUidFirewallStandbyRules.put(keyAt, 2);
            sparseIntArray.put(keyAt, 2);
          } else {
            this.mUidFirewallStandbyRules.put(keyAt, 0);
          }
          updateRulesForPowerRestrictionsUL(keyAt, z2);
        }
      }
      i++;
    }
    setUidFirewallRulesUL(2, sparseIntArray, z ? 1 : 2);
  }

  public final void updateRulesForGlobalChangeAL(boolean z) {
    if (Trace.isTagEnabled(2097152L)) {
      StringBuilder sb = new StringBuilder();
      sb.append("updateRulesForGlobalChangeAL: ");
      sb.append(z ? "R" : PackageManagerShellCommandDataLoader.STDIN_PATH);
      Trace.traceBegin(2097152L, sb.toString());
    }
    try {
      updateRulesForAppIdleUL();
      updateRulesForRestrictPowerUL();
      updateRulesForRestrictBackgroundUL();
      updateRestrictedModeAllowlistUL();
      if (z) {
        normalizePoliciesNL();
        updateNetworkRulesNL();
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void handleDeviceIdleModeChangedUL(boolean z) {
    Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
    try {
      updateRulesForDeviceIdleUL();
      if (z) {
        forEachUid(
            "updateRulesForRestrictPower",
            new IntConsumer() { // from class:
                                // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda4
              @Override // java.util.function.IntConsumer
              public final void accept(int i) {
                NetworkPolicyManagerService.this.lambda$handleDeviceIdleModeChangedUL$5(i);
              }
            });
      } else {
        handleDeviceIdleModeDisabledUL();
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleDeviceIdleModeChangedUL$5(int i) {
    synchronized (this.mUidRulesFirstLock) {
      lambda$updateRulesForRestrictPowerUL$6(i);
    }
  }

  public final void handleDeviceIdleModeDisabledUL() {
    Trace.traceBegin(2097152L, "handleDeviceIdleModeDisabledUL");
    try {
      SparseArray sparseArray = new SparseArray();
      synchronized (this.mUidBlockedState) {
        int size = this.mUidBlockedState.size();
        for (int i = 0; i < size; i++) {
          int keyAt = this.mUidBlockedState.keyAt(i);
          UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.valueAt(i);
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
      }
      if (sparseArray.size() != 0) {
        this.mHandler.obtainMessage(23, sparseArray).sendToTarget();
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void updateRulesForRestrictPowerUL() {
    Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
    try {
      updateRulesForDeviceIdleUL();
      updateRulesForPowerSaveUL();
      forEachUid(
          "updateRulesForRestrictPower",
          new IntConsumer() { // from class:
                              // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
              NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$6(i);
            }
          });
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void updateRulesForRestrictBackgroundUL() {
    Trace.traceBegin(2097152L, "updateRulesForRestrictBackgroundUL");
    try {
      forEachUid(
          "updateRulesForRestrictBackground",
          new IntConsumer() { // from class:
                              // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
              NetworkPolicyManagerService.this.lambda$updateRulesForRestrictBackgroundUL$7(i);
            }
          });
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:27:0x0069, code lost:

     r7 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x006d, code lost:

     throw r7;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void forEachUid(String str, final IntConsumer intConsumer) {
    if (Trace.isTagEnabled(2097152L)) {
      Trace.traceBegin(2097152L, "forEachUid-" + str);
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
            new Consumer() { // from class:
                             // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda6
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                NetworkPolicyManagerService.lambda$forEachUid$8(
                    sparseBooleanArray, i2, intConsumer, (AndroidPackage) obj);
              }
            },
            i2);
      }
      Trace.traceEnd(2097152L);
    } catch (Throwable th) {
      throw th;
    } finally {
    }
  }

  public static /* synthetic */ void lambda$forEachUid$8(
      SparseBooleanArray sparseBooleanArray,
      int i,
      IntConsumer intConsumer,
      AndroidPackage androidPackage) {
    int uid = androidPackage.getUid();
    if (androidPackage.getSharedUserId() != null) {
      if (sparseBooleanArray.indexOfKey(uid) >= 0) {
        return;
      } else {
        sparseBooleanArray.put(uid, true);
      }
    }
    intConsumer.accept(UserHandle.getUid(i, uid));
  }

  public final void updateRulesForTempWhitelistChangeUL(int i) {
    List users = this.mUserManager.getUsers();
    int size = users.size();
    for (int i2 = 0; i2 < size; i2++) {
      int uid = UserHandle.getUid(((UserInfo) users.get(i2)).id, i);
      updateRuleForAppIdleUL(uid, -1);
      updateRuleForDeviceIdleUL(uid);
      updateRuleForRestrictPowerUL(uid);
      lambda$updateRulesForRestrictPowerUL$6(uid);
    }
  }

  public final boolean isUidValidForDenylistRulesUL(int i) {
    return i == 1013 || i == 1019 || isUidValidForAllowlistRulesUL(i);
  }

  public final boolean isUidValidForAllowlistRulesUL(int i) {
    return UserHandle.isApp(i) && hasInternetPermissionUL(i);
  }

  public void setAppIdleWhitelist(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (this.mUidRulesFirstLock) {
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
        lambda$updateRulesForRestrictPowerUL$6(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public int[] getAppIdleWhitelist() {
    int[] iArr;
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (this.mUidRulesFirstLock) {
      int size = this.mAppIdleTempWhitelistAppIds.size();
      iArr = new int[size];
      for (int i = 0; i < size; i++) {
        iArr[i] = this.mAppIdleTempWhitelistAppIds.keyAt(i);
      }
    }
    return iArr;
  }

  public boolean isUidIdle(int i) {
    return isUidIdle(i, -1);
  }

  public final boolean isUidIdle(int i, int i2) {
    synchronized (this.mUidRulesFirstLock) {
      if (i2 != -1) {
        if (ActivityManager.isProcStateConsideredInteraction(i2)) {
          return false;
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
        if (!this.mUsageStats.isAppIdle(str, i, userId)) {
          return false;
        }
      }
      return true;
    }
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

  public final void onUidDeletedUL(int i) {
    synchronized (this.mUidBlockedState) {
      this.mUidBlockedState.delete(i);
    }
    this.mUidState.delete(i);
    this.mActivityManagerInternal.onUidBlockedReasonsChanged(i, 0);
    this.mUidPolicy.delete(i);
    this.mUidFirewallStandbyRules.delete(i);
    this.mUidFirewallDozableRules.delete(i);
    this.mUidFirewallPowerSaveRules.delete(i);
    this.mUidFirewallOemDenyRules.delete(i);
    this.mPowerSaveWhitelistExceptIdleAppIds.delete(i);
    this.mPowerSaveWhitelistAppIds.delete(i);
    this.mPowerSaveTempWhitelistAppIds.delete(i);
    this.mAppIdleTempWhitelistAppIds.delete(i);
    this.mUidFirewallRestrictedModeRules.delete(i);
    this.mUidFirewallLowPowerStandbyModeRules.delete(i);
    synchronized (this.mUidStateCallbackInfos) {
      this.mUidStateCallbackInfos.remove(i);
    }
    this.mHandler.obtainMessage(15, i, 0).sendToTarget();
  }

  public final void updateRestrictionRulesForUidUL(int i) {
    updateRuleForDeviceIdleUL(i);
    updateRuleForAppIdleUL(i, -1);
    updateRuleForRestrictPowerUL(i);
    updateRestrictedModeForUidUL(i);
    lambda$updateRulesForRestrictPowerUL$6(i);
    lambda$updateRulesForRestrictBackgroundUL$7(i);
  }

  /* renamed from: updateRulesForDataUsageRestrictionsUL, reason: merged with bridge method [inline-methods] */
  public final void lambda$updateRulesForRestrictBackgroundUL$7(int i) {
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
    int i5;
    if (!isUidValidForAllowlistRulesUL(i)) {
      if (LOGD) {
        Slog.d("NetworkPolicy", "no need to update restrict data rules for uid " + i);
        return;
      }
      return;
    }
    int i6 = 0;
    int i7 = this.mUidPolicy.get(i, 0);
    boolean isUidForegroundOnRestrictBackgroundUL = isUidForegroundOnRestrictBackgroundUL(i);
    boolean isRestrictedByAdminUL = isRestrictedByAdminUL(i);
    boolean z = (i7 & 1) != 0;
    boolean z2 = (i7 & 4) != 0;
    int i8 = (isRestrictedByAdminUL ? 262144 : 0) | 0 | (this.mRestrictBackground ? 65536 : 0);
    int i9 = IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
    int i10 = i8 | (z ? 131072 : 0);
    if (!isSystem(i)) {
      i9 = 0;
    }
    int i11 = (isUidForegroundOnRestrictBackgroundUL ? 262144 : 0) | i9 | 0 | (z2 ? 65536 : 0);
    synchronized (this.mUidBlockedState) {
      UidBlockedState orCreateUidBlockedStateForUid =
          getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
      UidBlockedState orCreateUidBlockedStateForUid2 =
          getOrCreateUidBlockedStateForUid(this.mTmpUidBlockedState, i);
      orCreateUidBlockedStateForUid2.copyFrom(orCreateUidBlockedStateForUid);
      orCreateUidBlockedStateForUid.blockedReasons =
          (orCreateUidBlockedStateForUid.blockedReasons & GnssNative.GNSS_AIDING_TYPE_ALL) | i10;
      orCreateUidBlockedStateForUid.allowedReasons =
          (orCreateUidBlockedStateForUid.allowedReasons & GnssNative.GNSS_AIDING_TYPE_ALL) | i11;
      orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
      i2 = orCreateUidBlockedStateForUid2.effectiveBlockedReasons;
      i3 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
      int i12 = orCreateUidBlockedStateForUid2.allowedReasons;
      if (i2 != i3) {
        i6 = orCreateUidBlockedStateForUid.deriveUidRules();
      }
      if (LOGV) {
        i5 = i12;
        StringBuilder sb = new StringBuilder();
        i4 = i6;
        sb.append("updateRuleForRestrictBackgroundUL(");
        sb.append(i);
        sb.append("): isForeground=");
        sb.append(isUidForegroundOnRestrictBackgroundUL);
        sb.append(", isDenied=");
        sb.append(z);
        sb.append(", isAllowed=");
        sb.append(z2);
        sb.append(", isRestrictedByAdmin=");
        sb.append(isRestrictedByAdminUL);
        sb.append(", oldBlockedState=");
        sb.append(orCreateUidBlockedStateForUid2);
        sb.append(", newBlockedState=");
        sb.append(orCreateUidBlockedStateForUid);
        sb.append(", newBlockedMeteredReasons=");
        sb.append(NetworkPolicyManager.blockedReasonsToString(i10));
        sb.append(", newAllowedMeteredReasons=");
        sb.append(NetworkPolicyManager.allowedReasonsToString(i11));
        Log.v("NetworkPolicy", sb.toString());
      } else {
        i4 = i6;
        i5 = i12;
      }
    }
    if (i2 != i3) {
      handleBlockedReasonsChanged(i, i3, i2);
      postUidRulesChangedMsg(i, i4);
    }
    if ((i2 & 393216) != 0 || (i3 & 393216) != 0) {
      setMeteredNetworkDenylist(i, (393216 & i3) != 0);
    }
    if ((i5 & 327680) == 0 && (i11 & 327680) == 0) {
      return;
    }
    setMeteredNetworkAllowlist(i, (327680 & i11) != 0);
  }

  /* renamed from: updateRulesForPowerRestrictionsUL, reason: merged with bridge method [inline-methods] */
  public final void lambda$updateRulesForRestrictPowerUL$6(int i) {
    updateRulesForPowerRestrictionsUL(i, -1);
  }

  public final void updateRulesForPowerRestrictionsUL(int i, int i2) {
    updateRulesForPowerRestrictionsUL(i, isUidIdle(i, i2));
  }

  public final void updateRulesForPowerRestrictionsUL(int i, boolean z) {
    if (Trace.isTagEnabled(2097152L)) {
      StringBuilder sb = new StringBuilder();
      sb.append("updateRulesForPowerRestrictionsUL: ");
      sb.append(i);
      sb.append("/");
      sb.append(z ? "I" : PackageManagerShellCommandDataLoader.STDIN_PATH);
      Trace.traceBegin(2097152L, sb.toString());
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
        Slog.d("NetworkPolicy", "no need to update restrict power rules for uid " + i);
        return;
      }
      return;
    }
    boolean isUidForegroundOnRestrictPowerUL = isUidForegroundOnRestrictPowerUL(i);
    boolean isUidTop = isUidTop(i);
    boolean isWhitelistedFromPowerSaveUL = isWhitelistedFromPowerSaveUL(i, this.mDeviceIdleMode);
    synchronized (this.mUidBlockedState) {
      UidBlockedState orCreateUidBlockedStateForUid =
          getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
      UidBlockedState orCreateUidBlockedStateForUid2 =
          getOrCreateUidBlockedStateForUid(this.mTmpUidBlockedState, i);
      orCreateUidBlockedStateForUid2.copyFrom(orCreateUidBlockedStateForUid);
      i2 = 0;
      int i5 = 2;
      int i6 = 32;
      int i7 = 4;
      int i8 = 8;
      int i9 =
          (this.mRestrictPower ? 1 : 0)
              | 0
              | (this.mDeviceIdleMode ? 2 : 0)
              | (this.mLowPowerStandbyActive ? 32 : 0)
              | (z ? 4 : 0)
              | (orCreateUidBlockedStateForUid.blockedReasons & 8);
      int i10 = (isSystem(i) ? 1 : 0) | 0;
      if (!isUidForegroundOnRestrictPowerUL) {
        i5 = 0;
      }
      int i11 = i5 | i10;
      if (!isUidTop) {
        i6 = 0;
      }
      int i12 = i11 | i6;
      if (!isWhitelistedFromPowerSaveUL(i, true)) {
        i7 = 0;
      }
      int i13 = i12 | i7;
      if (!isWhitelistedFromPowerSaveExceptIdleUL(i)) {
        i8 = 0;
      }
      int i14 =
          i13
              | i8
              | (orCreateUidBlockedStateForUid.allowedReasons & 16)
              | (isAllowlistedFromLowPowerStandbyUL(i) ? 64 : 0);
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
                + isUidTop
                + ", isWhitelisted="
                + isWhitelistedFromPowerSaveUL
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
    }
    if (i3 != i4) {
      handleBlockedReasonsChanged(i, i4, i3);
      postUidRulesChangedMsg(i, i2);
    }
  }

  public class NetPolicyAppIdleStateChangeListener
      extends AppStandbyInternal.AppIdleStateChangeListener {
    public NetPolicyAppIdleStateChangeListener() {}

    public void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
      try {
        int packageUidAsUser =
            NetworkPolicyManagerService.this
                .mContext
                .getPackageManager()
                .getPackageUidAsUser(str, IInstalld.FLAG_FORCE, i);
        synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
          NetworkPolicyManagerService.this.mLogger.appIdleStateChanged(packageUidAsUser, z);
          NetworkPolicyManagerService.this.updateRuleForAppIdleUL(packageUidAsUser, -1);
          NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$6(packageUidAsUser);
        }
      } catch (PackageManager.NameNotFoundException unused) {
      }
    }

    public void onParoleStateChanged(boolean z) {
      synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
        NetworkPolicyManagerService.this.mLogger.paroleStateChanged(z);
        NetworkPolicyManagerService.this.updateRulesForAppIdleParoleUL();
      }
    }
  }

  public final void handleBlockedReasonsChanged(int i, int i2, int i3) {
    this.mActivityManagerInternal.onUidBlockedReasonsChanged(i, i2);
    postBlockedReasonsChangedMsg(i, i2, i3);
  }

  public final void postBlockedReasonsChangedMsg(int i, int i2, int i3) {
    this.mHandler.obtainMessage(21, i, i2, Integer.valueOf(i3)).sendToTarget();
  }

  public final void postUidRulesChangedMsg(int i, int i2) {
    this.mHandler.obtainMessage(1, i, i2).sendToTarget();
  }

  public final void dispatchUidRulesChanged(
      INetworkPolicyListener iNetworkPolicyListener, int i, int i2) {
    try {
      iNetworkPolicyListener.onUidRulesChanged(i, i2);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchMeteredIfacesChanged(
      INetworkPolicyListener iNetworkPolicyListener, String[] strArr) {
    try {
      iNetworkPolicyListener.onMeteredIfacesChanged(strArr);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchRestrictBackgroundChanged(
      INetworkPolicyListener iNetworkPolicyListener, boolean z) {
    try {
      iNetworkPolicyListener.onRestrictBackgroundChanged(z);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchUidPoliciesChanged(
      INetworkPolicyListener iNetworkPolicyListener, int i, int i2) {
    try {
      iNetworkPolicyListener.onUidPoliciesChanged(i, i2);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchSubscriptionOverride(
      INetworkPolicyListener iNetworkPolicyListener, int i, int i2, int i3, int[] iArr) {
    try {
      iNetworkPolicyListener.onSubscriptionOverride(i, i2, i3, iArr);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchSubscriptionPlansChanged(
      INetworkPolicyListener iNetworkPolicyListener,
      int i,
      SubscriptionPlan[] subscriptionPlanArr) {
    try {
      iNetworkPolicyListener.onSubscriptionPlansChanged(i, subscriptionPlanArr);
    } catch (RemoteException unused) {
    }
  }

  public final void dispatchBlockedReasonChanged(
      INetworkPolicyListener iNetworkPolicyListener, int i, int i2, int i3) {
    try {
      iNetworkPolicyListener.onBlockedReasonChanged(i, i2, i3);
    } catch (RemoteException unused) {
    }
  }

  public void handleUidChanged(UidStateCallbackInfo uidStateCallbackInfo) {
    int i;
    int i2;
    long j;
    int i3;
    boolean updateUidStateUL;
    Trace.traceBegin(2097152L, "onUidStateChanged");
    try {
      synchronized (this.mUidRulesFirstLock) {
        synchronized (this.mUidStateCallbackInfos) {
          i = uidStateCallbackInfo.uid;
          i2 = uidStateCallbackInfo.procState;
          j = uidStateCallbackInfo.procStateSeq;
          i3 = uidStateCallbackInfo.capability;
          uidStateCallbackInfo.isPending = false;
        }
        this.mLogger.uidStateChanged(i, i2, j, i3);
        updateUidStateUL = updateUidStateUL(i, i2, j, i3);
        this.mActivityManagerInternal.notifyNetworkPolicyRulesUpdated(i, j);
      }
      if (updateUidStateUL) {
        updateNetworkStats(
            i, NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(i2, i3));
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public void handleUidGone(int i) {
    boolean removeUidStateUL;
    Trace.traceBegin(2097152L, "onUidGone");
    try {
      synchronized (this.mUidRulesFirstLock) {
        removeUidStateUL = removeUidStateUL(i);
      }
      if (removeUidStateUL) {
        updateNetworkStats(i, false);
      }
    } finally {
      Trace.traceEnd(2097152L);
    }
  }

  public final void broadcastRestrictBackgroundChanged(int i, Boolean bool) {
    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
    if (packagesForUid != null) {
      int userId = UserHandle.getUserId(i);
      for (String str : packagesForUid) {
        Intent intent = new Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
        intent.setPackage(str);
        intent.setFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.of(userId));
      }
    }
  }

  public final class IfaceQuotas {
    public final String iface;
    public final long limit;
    public final long warning;

    public IfaceQuotas(String str, long j, long j2) {
      this.iface = str;
      this.warning = j;
      this.limit = j2;
    }
  }

  public final void setInterfaceQuotasAsync(String str, long j, long j2) {
    this.mHandler.obtainMessage(10, new IfaceQuotas(str, j, j2)).sendToTarget();
  }

  public final void setInterfaceLimit(String str, long j) {
    try {
      this.mNetworkManager.setInterfaceQuota(str, j);
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem setting interface quota", e);
    }
  }

  public final void removeInterfaceQuotasAsync(String str) {
    this.mHandler.obtainMessage(11, str).sendToTarget();
  }

  public final void removeInterfaceLimit(String str) {
    try {
      this.mNetworkManager.removeInterfaceQuota(str);
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem removing interface quota", e);
    }
  }

  public final void setMeteredNetworkDenylist(int i, boolean z) {
    if (LOGV) {
      Slog.v("NetworkPolicy", "setMeteredNetworkDenylist " + i + ": " + z);
    }
    try {
      this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, z);
      this.mLogger.meteredDenylistChanged(i, z);
      if (Process.isApplicationUid(i)) {
        int sdkSandboxUid = Process.toSdkSandboxUid(i);
        this.mNetworkManager.setUidOnMeteredNetworkDenylist(sdkSandboxUid, z);
        this.mLogger.meteredDenylistChanged(sdkSandboxUid, z);
      }
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem setting denylist (" + z + ") rules for " + i, e);
    }
  }

  public final void setMeteredNetworkAllowlist(int i, boolean z) {
    if (LOGV) {
      Slog.v("NetworkPolicy", "setMeteredNetworkAllowlist " + i + ": " + z);
    }
    try {
      this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, z);
      this.mLogger.meteredAllowlistChanged(i, z);
      if (Process.isApplicationUid(i)) {
        int sdkSandboxUid = Process.toSdkSandboxUid(i);
        this.mNetworkManager.setUidOnMeteredNetworkAllowlist(sdkSandboxUid, z);
        this.mLogger.meteredAllowlistChanged(sdkSandboxUid, z);
      }
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem setting allowlist (" + z + ") rules for " + i, e);
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

  public final void addSdkSandboxUidsIfNeeded(SparseIntArray sparseIntArray) {
    int size = sparseIntArray.size();
    SparseIntArray sparseIntArray2 = new SparseIntArray();
    for (int i = 0; i < size; i++) {
      int keyAt = sparseIntArray.keyAt(i);
      int valueAt = sparseIntArray.valueAt(i);
      if (Process.isApplicationUid(keyAt)) {
        sparseIntArray2.put(Process.toSdkSandboxUid(keyAt), valueAt);
      }
    }
    for (int i2 = 0; i2 < sparseIntArray2.size(); i2++) {
      sparseIntArray.put(sparseIntArray2.keyAt(i2), sparseIntArray2.valueAt(i2));
    }
  }

  public final void setUidFirewallRulesUL(int i, SparseIntArray sparseIntArray) {
    addSdkSandboxUidsIfNeeded(sparseIntArray);
    try {
      int size = sparseIntArray.size();
      int[] iArr = new int[size];
      int[] iArr2 = new int[size];
      for (int i2 = size - 1; i2 >= 0; i2--) {
        iArr[i2] = sparseIntArray.keyAt(i2);
        iArr2[i2] = sparseIntArray.valueAt(i2);
      }
      this.mNetworkManager.setFirewallUidRules(i, iArr, iArr2);
      this.mLogger.firewallRulesChanged(i, iArr, iArr2);
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem setting firewall uid rules", e);
    }
  }

  public final void setUidFirewallRuleUL(int i, int i2, int i3) {
    if (Trace.isTagEnabled(2097152L)) {
      Trace.traceBegin(2097152L, "setUidFirewallRuleUL: " + i + "/" + i2 + "/" + i3);
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

  public final void resetUidFirewallRules(int i) {
    try {
      this.mNetworkManager.setFirewallUidRule(1, i, 0);
      this.mNetworkManager.setFirewallUidRule(2, i, 0);
      this.mNetworkManager.setFirewallUidRule(3, i, 0);
      this.mNetworkManager.setFirewallUidRule(4, i, 0);
      this.mNetworkManager.setFirewallUidRule(5, i, 0);
      this.mNetworkManager.setFirewallUidRule(7, i, 0);
      this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, false);
      this.mLogger.meteredAllowlistChanged(i, false);
      this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, false);
      this.mLogger.meteredDenylistChanged(i, false);
    } catch (RemoteException unused) {
    } catch (IllegalStateException e) {
      Log.wtf("NetworkPolicy", "problem resetting firewall uid rules for " + i, e);
    }
    if (Process.isApplicationUid(i)) {
      resetUidFirewallRules(Process.toSdkSandboxUid(i));
    }
  }

  public final long getTotalBytes(NetworkTemplate networkTemplate, long j, long j2) {
    if (this.mStatsCallback.isAnyCallbackReceived()) {
      return this.mDeps.getNetworkTotalBytes(networkTemplate, j, j2);
    }
    return 0L;
  }

  public final long getUidBytes(NetworkTemplate networkTemplate, long j, long j2, int i, int i2) {
    long j3 = 0;
    if (!this.mStatsCallback.isAnyCallbackReceived()) {
      return 0L;
    }
    for (NetworkStats.Bucket bucket : this.mDeps.getNetworkUidBytes(networkTemplate, j, j2)) {
      if (bucket.getUid() == i && bucket.getMetered() == i2) {
        j3 += bucket.getRxBytes() + bucket.getTxBytes();
      }
    }
    return j3;
  }

  public final boolean isBandwidthControlEnabled() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      boolean isBandwidthControlEnabled = this.mNetworkManager.isBandwidthControlEnabled();
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return isBandwidthControlEnabled;
    } catch (RemoteException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public static Intent buildSnoozeWarningIntent(NetworkTemplate networkTemplate, String str) {
    Intent intent = new Intent("com.android.server.net.action.SNOOZE_WARNING");
    intent.addFlags(268435456);
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    intent.setPackage(str);
    return intent;
  }

  public static Intent buildSnoozeRapidIntent(NetworkTemplate networkTemplate, String str) {
    Intent intent = new Intent("com.android.server.net.action.SNOOZE_RAPID");
    intent.addFlags(268435456);
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    intent.setPackage(str);
    return intent;
  }

  public static Intent buildNetworkOverLimitIntent(
      Resources resources, NetworkTemplate networkTemplate) {
    Intent intent = new Intent();
    intent.setComponent(
        ComponentName.unflattenFromString(
            resources.getString(R.string.dynamic_mode_notification_summary_v2)));
    intent.addFlags(268435456);
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    return intent;
  }

  public static Intent buildViewDataUsageIntent(
      Resources resources, NetworkTemplate networkTemplate) {
    Intent intent = new Intent();
    intent.setComponent(
        ComponentName.unflattenFromString(
            resources.getString(R.string.crossSimFormat_spn_cross_sim_calling)));
    intent.addFlags(268435456);
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    return intent;
  }

  public static Intent buildViewDataUsageIntentUDS(NetworkTemplate networkTemplate) {
    Intent intent = new Intent();
    intent.setAction("com.samsung.android.uds.SHOW_UDS_ACTIVITY");
    intent.addFlags(268435456);
    intent.setPackage("com.samsung.android.uds");
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    return intent;
  }

  public static Intent buildViewDataUsageIntentSM(String str, NetworkTemplate networkTemplate) {
    Intent intent = new Intent();
    intent.setComponent(ComponentName.unflattenFromString(str));
    intent.addFlags(268435456);
    intent.putExtra("android.net.NETWORK_TEMPLATE", (Parcelable) networkTemplate);
    return intent;
  }

  public void addIdleHandler(MessageQueue.IdleHandler idleHandler) {
    this.mHandler.getLooper().getQueue().addIdleHandler(idleHandler);
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
      setRestrictBackgroundUL(z4, "low_power");
    }
    this.mRestrictBackgroundChangedInBsm = z2;
  }

  public static void collectKeys(SparseArray sparseArray, SparseBooleanArray sparseBooleanArray) {
    int size = sparseArray.size();
    for (int i = 0; i < size; i++) {
      sparseBooleanArray.put(sparseArray.keyAt(i), true);
    }
  }

  public void factoryReset(String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.NETWORK_SETTINGS", "NetworkPolicy");
    if (this.mUserManager.hasUserRestriction("no_network_reset")) {
      return;
    }
    NetworkPolicy[] networkPolicies = getNetworkPolicies(this.mContext.getOpPackageName());
    NetworkTemplate buildTemplateCarrierMetered =
        str != null ? buildTemplateCarrierMetered(str) : null;
    NetworkTemplate build =
        str != null
            ? new NetworkTemplate.Builder(1).setSubscriberIds(Set.of(str)).setMeteredness(1).build()
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
    removeTagFileInNetstats();
  }

  public boolean isUidNetworkingBlocked(int i, boolean z) {
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
        if (this.mActiveNetworkType == 1 && (mFirewallRules.get(i, 0) & 2) != 0) {
          i2 &= 256;
        }
        if (this.mActiveNetworkType == 0 && (mFirewallRules.get(i, 0) & 1) != 0) {
          i2 &= 512;
        }
      }
      this.mLogger.networkBlocked(i, uidBlockedState);
    }
    this.mStatLogger.logDurationStat(1, time);
    return i2 != 0;
  }

  public boolean isUidRestrictedOnMeteredNetworks(int i) {
    boolean z;
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.OBSERVE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (this.mUidBlockedState) {
      UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
      z = ((uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons) & (-65536)) != 0;
    }
    return z;
  }

  public class NetworkPolicyManagerInternalImpl extends NetworkPolicyManagerInternal {
    public NetworkPolicyManagerInternalImpl() {}

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001b, code lost:

       r6 = r5.this$0.mNetworkPoliciesSecondLock;
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001f, code lost:

       monitor-enter(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0020, code lost:

       r5.this$0.writePolicyAL();
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:

       monitor-exit(r6);
    */
    @Override // com.android.server.net.NetworkPolicyManagerInternal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetUserState(int i) {
      synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
        boolean z = true;
        boolean removeUserStateUL =
            NetworkPolicyManagerService.this.removeUserStateUL(i, false, true);
        if (!NetworkPolicyManagerService.this.addDefaultRestrictBackgroundAllowlistUidsUL(i)
            && !removeUserStateUL) {
          z = false;
        }
      }
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void onTempPowerSaveWhitelistChange(int i, boolean z, int i2, String str) {
      synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
        if (NetworkPolicyManagerService.this.mSystemReady) {
          NetworkPolicyManagerService.this.mLogger.tempPowerSaveWlChanged(i, z, i2, str);
          if (z) {
            NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.put(i, true);
          } else {
            NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.delete(i);
          }
          NetworkPolicyManagerService.this.updateRulesForTempWhitelistChangeUL(i);
        }
      }
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public long getSubscriptionOpportunisticQuota(Network network, int i) {
      long j;
      float f;
      float f2;
      synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
        NetworkPolicyManagerService networkPolicyManagerService = NetworkPolicyManagerService.this;
        j =
            networkPolicyManagerService.mSubscriptionOpportunisticQuota.get(
                networkPolicyManagerService.getSubIdLocked(network), -1L);
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
      return (long) (f * f2);
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void onAdminDataAvailable() {
      NetworkPolicyManagerService.this.mAdminDataAvailableLatch.countDown();
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void setAppIdleWhitelist(int i, boolean z) {
      NetworkPolicyManagerService.this.setAppIdleWhitelist(i, z);
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void setMeteredRestrictedPackages(Set set, int i) {
      NetworkPolicyManagerService.this.setMeteredRestrictedPackagesInternal(set, i);
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void setMeteredRestrictedPackagesAsync(Set set, int i) {
      NetworkPolicyManagerService.this.mHandler.obtainMessage(17, i, 0, set).sendToTarget();
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void setLowPowerStandbyActive(boolean z) {
      Trace.traceBegin(2097152L, "setLowPowerStandbyActive");
      try {
        synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
          if (NetworkPolicyManagerService.this.mLowPowerStandbyActive == z) {
            return;
          }
          NetworkPolicyManagerService.this.mLowPowerStandbyActive = z;
          synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
            if (NetworkPolicyManagerService.this.mSystemReady) {
              NetworkPolicyManagerService.this.forEachUid(
                  "updateRulesForRestrictPower",
                  new IntConsumer() { // from class:
                                      // com.android.server.net.NetworkPolicyManagerService$NetworkPolicyManagerInternalImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                      NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.this
                          .lambda$setLowPowerStandbyActive$0(i);
                    }
                  });
              NetworkPolicyManagerService.this.updateRulesForLowPowerStandbyUL();
            }
          }
        }
      } finally {
        Trace.traceEnd(2097152L);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLowPowerStandbyActive$0(int i) {
      NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$6(i);
    }

    @Override // com.android.server.net.NetworkPolicyManagerInternal
    public void setLowPowerStandbyAllowlist(int[] iArr) {
      synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        for (int i = 0;
            i < NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.size();
            i++) {
          int keyAt = NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.keyAt(i);
          if (!ArrayUtils.contains(iArr, keyAt)) {
            sparseBooleanArray.put(keyAt, true);
          }
        }
        for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
          NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.delete(
              sparseBooleanArray.keyAt(i2));
        }
        for (int i3 : iArr) {
          if (NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.indexOfKey(i3) < 0) {
            sparseBooleanArray.append(i3, true);
            NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.append(i3, true);
          }
        }
        if (NetworkPolicyManagerService.this.mLowPowerStandbyActive) {
          synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
            if (NetworkPolicyManagerService.this.mSystemReady) {
              for (int i4 = 0; i4 < sparseBooleanArray.size(); i4++) {
                int keyAt2 = sparseBooleanArray.keyAt(i4);
                NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$6(keyAt2);
                NetworkPolicyManagerService.this.updateRuleForLowPowerStandbyUL(keyAt2);
              }
            }
          }
        }
      }
    }
  }

  public final void setMeteredRestrictedPackagesInternal(Set set, int i) {
    synchronized (this.mUidRulesFirstLock) {
      ArraySet arraySet = new ArraySet();
      Iterator it = set.iterator();
      while (it.hasNext()) {
        int uidForPackage = getUidForPackage((String) it.next(), i);
        if (uidForPackage >= 0) {
          arraySet.add(Integer.valueOf(uidForPackage));
        }
      }
      Set set2 = (Set) this.mMeteredRestrictedUids.get(i);
      this.mMeteredRestrictedUids.put(i, arraySet);
      handleRestrictedPackagesChangeUL(set2, arraySet);
      this.mLogger.meteredRestrictedPkgsChanged(arraySet);
    }
  }

  public final int getUidForPackage(String str, int i) {
    try {
      return this.mContext.getPackageManager().getPackageUidAsUser(str, 4202496, i);
    } catch (PackageManager.NameNotFoundException unused) {
      return -1;
    }
  }

  public final int getSubIdLocked(Network network) {
    return this.mNetIdToSubId.get(network.getNetId(), -1);
  }

  public final SubscriptionPlan getPrimarySubscriptionPlanLocked(int i) {
    SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) this.mSubscriptionPlans.get(i);
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

  public final void waitForAdminData() {
    if (this.mContext.getPackageManager().hasSystemFeature("android.software.device_admin")) {
      ConcurrentUtils.waitForCountDownNoInterrupt(
          this.mAdminDataAvailableLatch, 10000L, "Wait for admin data");
    }
  }

  public final void handleRestrictedPackagesChangeUL(Set set, Set set2) {
    if (this.mNetworkManagerReady) {
      if (set == null) {
        Iterator it = set2.iterator();
        while (it.hasNext()) {
          lambda$updateRulesForRestrictBackgroundUL$7(((Integer) it.next()).intValue());
        }
        return;
      }
      Iterator it2 = set.iterator();
      while (it2.hasNext()) {
        int intValue = ((Integer) it2.next()).intValue();
        if (!set2.contains(Integer.valueOf(intValue))) {
          lambda$updateRulesForRestrictBackgroundUL$7(intValue);
        }
      }
      Iterator it3 = set2.iterator();
      while (it3.hasNext()) {
        int intValue2 = ((Integer) it3.next()).intValue();
        if (!set.contains(Integer.valueOf(intValue2))) {
          lambda$updateRulesForRestrictBackgroundUL$7(intValue2);
        }
      }
    }
  }

  public final boolean isRestrictedByAdminUL(int i) {
    Set set = (Set) this.mMeteredRestrictedUids.get(UserHandle.getUserId(i));
    return set != null && set.contains(Integer.valueOf(i));
  }

  public static boolean getBooleanDefeatingNullable(
      PersistableBundle persistableBundle, String str, boolean z) {
    return persistableBundle != null ? persistableBundle.getBoolean(str, z) : z;
  }

  public static UidBlockedState getOrCreateUidBlockedStateForUid(SparseArray sparseArray, int i) {
    UidBlockedState uidBlockedState = (UidBlockedState) sparseArray.get(i);
    if (uidBlockedState != null) {
      return uidBlockedState;
    }
    UidBlockedState uidBlockedState2 = new UidBlockedState();
    sparseArray.put(i, uidBlockedState2);
    return uidBlockedState2;
  }

  public final int getEffectiveBlockedReasons(int i) {
    int i2;
    synchronized (this.mUidBlockedState) {
      UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
      i2 = uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons;
    }
    return i2;
  }

  public final int getBlockedReasons(int i) {
    int i2;
    synchronized (this.mUidBlockedState) {
      UidBlockedState uidBlockedState = (UidBlockedState) this.mUidBlockedState.get(i);
      i2 = uidBlockedState == null ? 0 : uidBlockedState.blockedReasons;
    }
    return i2;
  }

  final class UidBlockedState {
    public int allowedReasons;
    public int blockedReasons;
    public int effectiveBlockedReasons;
    public static final int[] BLOCKED_REASONS = {
      1, 2, 4, 8, 32, 65536, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, 262144, 256, 512
    };
    public static final int[] ALLOWED_REASONS = {
      1, 2, 32, 4, 8, 16, 64, 65536, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, 262144
    };

    public static int getAllowedReasonsForProcState(int i) {
      if (i > 5) {
        return 0;
      }
      return i <= 3 ? 262178 : 262146;
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
        i = i & (-2) & (-3) & (-5);
      }
      if ((262144 & i2) != 0) {
        i = i & (-65537) & (-131073);
      }
      if ((i2 & 32) != 0) {
        i &= -33;
      }
      if ((i2 & 4) != 0) {
        i = i & (-2) & (-3) & (-5);
      }
      if ((i2 & 8) != 0) {
        i = i & (-2) & (-5);
      }
      if ((i2 & 16) != 0) {
        i &= -9;
      }
      if ((65536 & i2) != 0) {
        i &= -65537;
      }
      return (i2 & 64) != 0 ? i & (-33) : i;
    }

    public UidBlockedState(int i, int i2, int i3) {
      this.blockedReasons = i;
      this.allowedReasons = i2;
      this.effectiveBlockedReasons = i3;
    }

    public UidBlockedState() {
      this(0, 0, 0);
    }

    public void updateEffectiveBlockedReasons() {
      if (NetworkPolicyManagerService.LOGV && this.blockedReasons == 0) {
        Log.v("NetworkPolicy", "updateEffectiveBlockedReasons(): no blocked reasons");
      }
      this.effectiveBlockedReasons =
          getEffectiveBlockedReasons(this.blockedReasons, this.allowedReasons);
      if (NetworkPolicyManagerService.LOGV) {
        Log.v(
            "NetworkPolicy",
            "updateEffectiveBlockedReasons(): blockedReasons="
                + Integer.toBinaryString(this.blockedReasons)
                + ", effectiveReasons="
                + Integer.toBinaryString(this.effectiveBlockedReasons));
      }
    }

    public String toString() {
      return toString(this.blockedReasons, this.allowedReasons, this.effectiveBlockedReasons);
    }

    public static String toString(int i, int i2, int i3) {
      return "{blocked="
          + blockedReasonsToString(i)
          + ",allowed="
          + allowedReasonsToString(i2)
          + ",effective="
          + blockedReasonsToString(i3)
          + "}";
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

    public static String blockedReasonsToString(int i) {
      int i2 = 0;
      if (i == 0) {
        return blockedReasonToString(0);
      }
      StringBuilder sb = new StringBuilder();
      int[] iArr = BLOCKED_REASONS;
      int length = iArr.length;
      while (true) {
        if (i2 >= length) {
          break;
        }
        int i3 = iArr[i2];
        if ((i & i3) != 0) {
          sb.append(sb.length() != 0 ? "|" : "");
          sb.append(blockedReasonToString(i3));
          i &= ~i3;
        }
        i2++;
      }
      if (i != 0) {
        sb.append(sb.length() != 0 ? "|" : "");
        sb.append(String.valueOf(i));
        Slog.wtfStack("NetworkPolicy", "Unknown blockedReasons: " + i);
      }
      return sb.toString();
    }

    public static String allowedReasonsToString(int i) {
      int i2 = 0;
      if (i == 0) {
        return allowedReasonToString(0);
      }
      StringBuilder sb = new StringBuilder();
      int[] iArr = ALLOWED_REASONS;
      int length = iArr.length;
      while (true) {
        if (i2 >= length) {
          break;
        }
        int i3 = iArr[i2];
        if ((i & i3) != 0) {
          sb.append(sb.length() != 0 ? "|" : "");
          sb.append(allowedReasonToString(i3));
          i &= ~i3;
        }
        i2++;
      }
      if (i != 0) {
        sb.append(sb.length() != 0 ? "|" : "");
        sb.append(String.valueOf(i));
        Slog.wtfStack("NetworkPolicy", "Unknown allowedReasons: " + i);
      }
      return sb.toString();
    }

    public void copyFrom(UidBlockedState uidBlockedState) {
      this.blockedReasons = uidBlockedState.blockedReasons;
      this.allowedReasons = uidBlockedState.allowedReasons;
      this.effectiveBlockedReasons = uidBlockedState.effectiveBlockedReasons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x004c, code lost:

       if ((r0 & 262144) != 0) goto L25;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int deriveUidRules() {
      int i = this.effectiveBlockedReasons;
      int i2 = (i & 8) != 0 ? 1024 : 0;
      if ((i & 39) != 0) {
        i2 |= 64;
      } else if ((this.blockedReasons & 39) != 0) {
        i2 |= 32;
      }
      if ((i & 256) != 0) {
        i2 |= 64;
      }
      if ((i & 512) != 0) {
        i2 |= 64;
      }
      if ((i & 393216) != 0) {
        i2 |= 4;
      } else {
        int i3 = this.blockedReasons;
        if ((131072 & i3) == 0 || (this.allowedReasons & 262144) == 0) {
          if ((i3 & 65536) != 0) {
            int i4 = this.allowedReasons;
            if ((65536 & i4) != 0) {
              i2 |= 32;
            }
          }
        }
        i2 |= 2;
      }
      if (NetworkPolicyManagerService.LOGV) {
        Slog.v(
            "NetworkPolicy",
            "uidBlockedState=" + this + " -> uidRule=" + NetworkPolicyManager.uidRulesToString(i2));
      }
      return i2;
    }
  }

  public class NotificationId {
    public final int mId;
    public final String mTag;

    public NotificationId(NetworkPolicy networkPolicy, int i) {
      this.mTag = buildNotificationTag(networkPolicy, i);
      this.mId = i;
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj instanceof NotificationId) {
        return Objects.equals(this.mTag, ((NotificationId) obj).mTag);
      }
      return false;
    }

    public int hashCode() {
      return Objects.hash(this.mTag);
    }

    public final String buildNotificationTag(NetworkPolicy networkPolicy, int i) {
      return "NetworkPolicy:"
          + networkPolicy.template.hashCode()
          + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR
          + i;
    }

    public String getTag() {
      return this.mTag;
    }

    public int getId() {
      return this.mId;
    }
  }

  public final boolean isNaGsm(String str) {
    String string =
        SemCarrierFeature.getInstance()
            .getString(0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
    if (str == null || "ALL".equals(str)) {
      if (string != null && string.endsWith("GSM-USA")) {
        return true;
      }
    } else if (("ATT".equals(str) || "TMO".equals(str) || "MTR".equals(str) || "ASR".equals(str))
        && string != null
        && string.contains(str)) {
      return true;
    }
    return false;
  }

  public class CallAttributesListener extends TelephonyCallback
      implements TelephonyCallback.CallAttributesListener {
    public CallAttributesListener() {}

    public void onCallStatesChanged(List list) {
      String string =
          SemCarrierFeature.getInstance()
              .getString(0, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", false);
      if ((string != null && string.startsWith("VZW-"))
          || NetworkPolicyManagerService.this.isNaGsm("ATT")
          || NetworkPolicyManagerService.this.isNaGsm("TMO")) {
        boolean hasCall = NetworkPolicyManagerService.this.mTelephonyManager.hasCall("video");
        boolean hasCall2 =
            NetworkPolicyManagerService.this.mTelephonyManager.hasCall("activevideo");
        NetworkPolicyManagerService networkPolicyManagerService = NetworkPolicyManagerService.this;
        networkPolicyManagerService.mHasEpdgCall =
            networkPolicyManagerService.mTelephonyManager.hasCall("epdg");
        Log.d(
            "NetworkPolicy",
            "onCallStatesChanged - hasVideoCall: "
                + hasCall
                + ", hasActiveVideoCall: "
                + hasCall2
                + ", mHasEpdgCall: "
                + NetworkPolicyManagerService.this.mHasEpdgCall);
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
          NetworkPolicyManagerService.this.mVideoCallLimitAlreadySent = false;
          NetworkPolicyManagerService.this.mVideoCallWarningAlreadySent = false;
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
  }

  public final void updateVideoCallLocked() {
    if (LOGV) {
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
        Log.d(
            "NetworkPolicy",
            "mIsVideoCall: " + this.mIsVideoCall + ", mHasEpdgCall:" + this.mHasEpdgCall);
        if (!this.mHasEpdgCall && this.mIsVideoCall && networkPolicy.isOverLimit(totalBytes)) {
          notifyVideoCallOverLimit(networkPolicy.template);
        }
      }
    }
  }

  public final void notifyVideoCallOverLimit(NetworkTemplate networkTemplate) {
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
    if ((matchRule == 1 || matchRule == 10) && !this.mVideoCallLimitAlreadySent) {
      this.mContext.sendBroadcast(
          new Intent("com.android.intent.action.VIDEO_DATAUSAGE_REACH_TO_LIMIT"));
      this.mVideoCallLimitAlreadySent = true;
      synchronized (this.mNetworkPoliciesSecondLock) {
        updateNotificationsNL();
      }
    }
  }

  public final boolean getRestrictBackgroundInLowerPowerMode() {
    boolean z = this.mContext.getResources().getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable);
    if (SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)
            .equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)
        && !z) {
      Log.d("NetworkPolicy", "CHN don't use RestrictBackgroundData during PowerSave mode");
      return false;
    }
    boolean z2 = this.mPowerManagerInternal.getLowPowerState(6).batterySaverEnabled;
    Slog.v("NetworkPolicy", "getRestrictBackgroundInLowerPowerMode: enabled(" + z2 + ")");
    return z2;
  }

  public final boolean changePowerSaveMode() {
    boolean z = this.mRestrictPower;
    this.mRestrictPower = getRestrictBackgroundInLowerPowerMode() && !this.mChargingState;
    return z != this.mRestrictPower;
  }

  public final void initTetheringWarningObserver() {
    Slog.v("NetworkPolicy", "initTetheringWarningObserver");
    this.mTetheringWarningObserver =
        new ContentObserver(
            this.mHandler) { // from class: com.android.server.net.NetworkPolicyManagerService.19
          @Override // android.database.ContentObserver
          public void onChange(boolean z) {
            Slog.v("NetworkPolicy", "mTetheringWarningObserver");
            Message obtainMessage = NetworkPolicyManagerService.this.mHandler.obtainMessage(1007);
            obtainMessage.arg1 = 1;
            obtainMessage.sendToTarget();
          }
        };
  }

  public final boolean isKorOperator() {
    final String str = SystemProperties.get("ro.csc.sales_code", "NONE");
    return Arrays.asList(
            "SKC", "KTC", "LUC", "KOO", "SKT", "SKO", "KTT", "KTO", "LGT", "LUO", "K06", "K01")
        .stream()
        .anyMatch(
            new Predicate() { // from class:
                              // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda3
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean contains;
                contains = str.contains((String) obj);
                return contains;
              }
            });
  }

  public final long getTetheringWarningBytes(String str) {
    String string = Settings.Global.getString(this.mContext.getContentResolver(), str);
    if (TextUtils.isEmpty(string)) {
      return -1L;
    }
    return Long.parseLong(string);
  }

  public static Intent buildSnoozeTetheringWarningIntent(String str) {
    Intent intent = new Intent("com.android.server.net.action.SNOOZE_TETHERING_WARNING");
    intent.addFlags(268435456);
    intent.setPackage(str);
    return intent;
  }

  public final void writeNetstatsFiles() {
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
              file2.toPath(), path.resolve(file2.getName()), StandardCopyOption.REPLACE_EXISTING);
        }
      }
    } catch (Exception e) {
      Slog.v("NetworkPolicy", "writeNetstatsFiles, Copy error: ");
      e.printStackTrace();
    }
  }

  public final void removeTagFileInNetstats() {
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

  public final long getTotalBytesForOffPeak(int i, NetworkPolicy networkPolicy, long j, long j2) {
    NetworkPolicyManagerService networkPolicyManagerService = this;
    long j3 =
        Settings.System.getLong(
            networkPolicyManagerService.mContext.getContentResolver(),
            KEY_OFF_PEAK_DATA_START_TIME + i,
            82800000L);
    long j4 =
        Settings.System.getLong(
            networkPolicyManagerService.mContext.getContentResolver(),
            KEY_OFF_PEAK_DATA_END_TIME + i,
            25200000L);
    long currentTimeMillis = System.currentTimeMillis();
    long rawOffset =
        currentTimeMillis
            - ((((long) TimeZone.getDefault().getRawOffset()) + currentTimeMillis)
                % BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
    long j5 = j;
    long j6 = 0;
    while (j5 <= rawOffset) {
      j6 +=
          networkPolicyManagerService.mDeps.getNetworkTotalBytes(
              networkPolicy.template, j3 + j5, j5 + adjustOffPeakEndTime(j3, j4, rawOffset, j2));
      j5 += BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
      rawOffset = rawOffset;
      j3 = j3;
      networkPolicyManagerService = this;
    }
    long j7 = j6;
    Slog.v(
        "NetworkPolicy",
        "getTotalBytesForOffPeak() for offpeak todayStartTime " + rawOffset + " totalBytes " + j7);
    return j7;
  }

  public final boolean isOffPeakEnable(int i) {
    if (i < 0) {
      return false;
    }
    if (!isOffPeakEnable.containsKey(Integer.valueOf(i))) {
      checkOffPeakEnable(i);
    }
    Slog.v("NetworkPolicy", "current subid: " + i + " isoffpeakEnable:" + isOffPeakEnable);
    return ((Boolean) isOffPeakEnable.getOrDefault(Integer.valueOf(i), Boolean.FALSE))
        .booleanValue();
  }

  public final void initOffPeakContentObserver() {
    this.mOffPeakContentObserver =
        new ContentObserver(
            this.mHandler) { // from class: com.android.server.net.NetworkPolicyManagerService.21
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
              int parseInt =
                  Integer.parseInt(
                      uri.toString()
                          .replace(
                              Settings.System.getUriFor(
                                      NetworkPolicyManagerService.KEY_IS_IN_OFF_PEAK_TIME)
                                  .toString(),
                              ""));
              if (parseInt < 0) {
                return;
              }
              NetworkPolicyManagerService.this.checkOffPeakEnable(parseInt);
              Slog.v(
                  "NetworkPolicy",
                  "off peak change subid: "
                      + parseInt
                      + " isoffpeakEnable:"
                      + NetworkPolicyManagerService.isOffPeakEnable);
              Iterator it = NetworkPolicyManagerService.this.mActiveNotifs.iterator();
              while (it.hasNext()) {
                NotificationId notificationId = (NotificationId) it.next();
                if (notificationId.getId() == 34) {
                  NetworkPolicyManagerService.this.cancelNotification(notificationId);
                }
              }
              Bundle bundle = new Bundle();
              bundle.putInt(NetworkPolicyManagerService.KEY_SM_EXTRAS_SUBID, parseInt);
              try {
                NetworkPolicyManagerService.this
                    .mContext
                    .getContentResolver()
                    .call(
                        Uri.parse(NetworkPolicyManagerService.KEY_SM_PROVIDER_CONTENT_URI),
                        NetworkPolicyManagerService.KEY_SM_PROVIDER_METHOR_UPDATE_POLICY,
                        (String) null,
                        bundle);
              } catch (IllegalArgumentException e) {
                Log.e("NetworkPolicy", " call to smart manager has exception ", e);
              }
            }
          }
        };
  }

  public final void checkToInitOffPeakConfig(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            NetworkPolicyManagerService.this.lambda$checkToInitOffPeakConfig$10(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$checkToInitOffPeakConfig$10(int i) {
    if (i != -1) {
      if (!((Boolean) this.isOffPeakObserverRegisted.get(i, Boolean.FALSE)).booleanValue()) {
        this.isOffPeakObserverRegisted.put(i, Boolean.TRUE);
        this.mContext
            .getContentResolver()
            .registerContentObserver(
                Settings.System.getUriFor(KEY_IS_IN_OFF_PEAK_TIME + i),
                false,
                this.mOffPeakContentObserver);
      }
      checkOffPeakEnable(i);
      Bundle bundle = new Bundle();
      bundle.putInt(KEY_SM_EXTRAS_SUBID, i);
      try {
        this.mContext
            .getContentResolver()
            .call(
                Uri.parse(KEY_SM_PROVIDER_CONTENT_URI),
                KEY_SM_PROVIDER_METHOR_UPDATE_ALARM,
                (String) null,
                bundle);
      } catch (IllegalArgumentException e) {
        Log.e("NetworkPolicy", " call to smart manager has exception ", e);
      }
    }
    Slog.v(
        "NetworkPolicy",
        "updateOffPeakPlanConfig SubscriptionId: "
            + i
            + " isoffpeakEnable:"
            + isOffPeakEnable.get(Integer.valueOf(i)));
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
            int readIntAttribute2 = XmlUtils.readIntAttribute(resolvePullParser, "policy");
            if (UserHandle.isApp(readIntAttribute)) {
              setFirewallPolicyNL(readIntAttribute, readIntAttribute2, false);
              applyFirewallRules(readIntAttribute, readIntAttribute2);
            } else {
              Slog.w(
                  "NetworkPolicy",
                  "unable to apply policy to UID " + readIntAttribute + "; ignoring");
            }
          }
        }
      } catch (FileNotFoundException unused) {
        Slog.w("NetworkPolicy", "miss firewall policy file");
      } catch (Exception e) {
        Log.wtf("NetworkPolicy", "problem reading firewall policy", e);
      }
    } finally {
      IoUtils.closeQuietly(fileInputStream);
    }
  }

  public void writeFirewallPolicyAL() {
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
        if (i < sparseIntArray.size()) {
          int keyAt = sparseIntArray.keyAt(i);
          int valueAt = sparseIntArray.valueAt(i);
          if (valueAt != 0) {
            resolveSerializer.startTag((String) null, "firewall-policy");
            XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt);
            XmlUtils.writeIntAttribute(resolveSerializer, "policy", valueAt);
            resolveSerializer.endTag((String) null, "firewall-policy");
          }
          i++;
        } else {
          resolveSerializer.endDocument();
          this.mFirewallPolicyFile.finishWrite(startWrite);
          return;
        }
      }
    } catch (IOException unused2) {
      fileOutputStream = startWrite;
      if (fileOutputStream != null) {
        this.mFirewallPolicyFile.failWrite(fileOutputStream);
      }
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

  public final void removeFirewallPolicyNL(int i) {
    SparseIntArray sparseIntArray = mFirewallRules;
    int i2 = sparseIntArray.get(i, 0);
    if (i2 != 0) {
      sparseIntArray.delete(i);
      writeFirewallPolicyAL();
      removeFirewallRules(i, i2);
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

  public void setFirewallRuleMobileData(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (mFirewallPoliciesLock) {
      int i2 = mFirewallRules.get(i, 0);
      Slog.v(
          "NetworkPolicy", "setFirewallRuleMobileData UID:" + i + " allow:" + z + " policy:" + i2);
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
    }
  }

  public int[] getAllFirewallRuleMobileData() {
    ArrayList arrayList = new ArrayList();
    synchronized (mFirewallPoliciesLock) {
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
    }
    return arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
  }

  public void setFirewallRuleWifi(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    synchronized (mFirewallPoliciesLock) {
      int i2 = mFirewallRules.get(i, 0);
      Slog.v("NetworkPolicy", "setFirewallRuleWifi UID:" + i + " allow:" + z + " policy:" + i2);
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
    }
  }

  public void setFirewallRuleMobileDataMap(Map map) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    for (Map.Entry entry : map.entrySet()) {
      setFirewallRuleMobileData(
          ((Integer) entry.getKey()).intValue(), ((Boolean) entry.getValue()).booleanValue());
    }
  }

  public void setFirewallRuleWifiMap(Map map) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
    for (Map.Entry entry : map.entrySet()) {
      setFirewallRuleWifi(
          ((Integer) entry.getKey()).intValue(), ((Boolean) entry.getValue()).booleanValue());
    }
  }

  public boolean getFirewallRuleMobileData(int i) {
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

  public boolean getFirewallRuleWifi(int i) {
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

  public final void handleCheckFireWallPermission(int i, int i2) {
    String str;
    PackageManager packageManager = this.mContext.getPackageManager();
    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.mContext.getSystemService("connectivity");
    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
    if (packageManager == null || connectivityManager == null || activityManager == null) {
      return;
    }
    try {
      ApplicationInfo applicationInfoAsUser =
          packageManager.getApplicationInfoAsUser(
              activityManager.getPackageFromAppProcesses(i), 1, UserHandle.getUserId(i2));
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetworkInfo == null || applicationInfoAsUser == null) {
        return;
      }
      String charSequence = applicationInfoAsUser.loadLabel(packageManager).toString();
      Resources resources = this.mContext.getResources();
      if (activeNetworkInfo.getType() == 0
          && !getFirewallRuleMobileData(applicationInfoAsUser.uid)) {
        str = resources.getString(17042430, charSequence);
      } else if (activeNetworkInfo.getType() != 1) {
        str = "";
      } else {
        if (getFirewallRuleWifi(applicationInfoAsUser.uid)) {
          return;
        }
        if (getFirewallRuleMobileData(applicationInfoAsUser.uid)) {
          NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
          for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo.getType() == 0
                && TelephonyManager.from(this.mContext).getDataEnabled()) {
              return;
            }
          }
        }
        str = resources.getString(17042431, charSequence, charSequence);
      }
      if (str.isEmpty()) {
        return;
      }
      Toast.makeText(this.mContext, str, 1).show();
    } catch (Exception e) {
      Log.e("NetworkPolicy", "handleCheckFireWallPermission", e);
    }
  }

  public final void registerProcessListener() {
    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
    if (activityManager == null) {
      Log.e("NetworkPolicy", "registerProcessListener activityManager is null");
    } else {
      activityManager.semRegisterProcessListener(
          new ActivityManager
              .SemProcessListener() { // from class:
                                      // com.android.server.net.NetworkPolicyManagerService.22
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
              if (z) {
                if (((Boolean)
                        NetworkPolicyManagerService.this.mToastCheckedUidMap.getOrDefault(
                            Integer.valueOf(i2), Boolean.FALSE))
                    .booleanValue()) {
                  Log.d("NetworkPolicy", "already checked " + i2);
                  return;
                }
                ConcurrentHashMap concurrentHashMap =
                    NetworkPolicyManagerService.this.mForegroundActivitiesPidMap;
                Integer valueOf = Integer.valueOf(i);
                Boolean bool = Boolean.TRUE;
                concurrentHashMap.put(valueOf, bool);
                NetworkPolicyManagerService.this.mToastCheckedUidMap.put(Integer.valueOf(i2), bool);
                Handler handler = NetworkPolicyManagerService.this.mHandler;
                if (handler == null) {
                  Log.i("NetworkPolicy", "handler is null return");
                  return;
                }
                Message obtainMessage = handler.obtainMessage(51);
                obtainMessage.arg1 = i;
                obtainMessage.arg2 = i2;
                NetworkPolicyManagerService.this.mHandler.sendMessage(obtainMessage);
              }
            }

            public void onProcessDied(int i, int i2) {
              if (((Boolean)
                      NetworkPolicyManagerService.this.mForegroundActivitiesPidMap.getOrDefault(
                          Integer.valueOf(i), Boolean.FALSE))
                  .booleanValue()) {
                NetworkPolicyManagerService.this.mForegroundActivitiesPidMap.remove(
                    Integer.valueOf(i));
                NetworkPolicyManagerService.this.mToastCheckedUidMap.remove(Integer.valueOf(i2));
              }
            }
          });
    }
  }
}
