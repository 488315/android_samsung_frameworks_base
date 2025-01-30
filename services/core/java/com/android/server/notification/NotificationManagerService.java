package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.AutomaticZenRule;
import android.app.IActivityManager;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.app.ITransientNotificationCallback;
import android.app.IUriGrantsManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationHistory;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatsManager;
import android.app.UriGrantsManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.backup.BackupManager;
import android.app.compat.CompatChanges;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.UsageStatsManagerInternal;
import android.companion.ICompanionDeviceManager;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.pm.VersionedPackage;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioManagerInternal;
import android.media.IRingtonePlayer;
import android.media.RingtoneManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.DeviceIdleManager;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.os.WorkSource;
import android.p005os.IInstalld;
import android.permission.PermissionManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.ConversationChannelWrapper;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.IStatusBarNotificationHolder;
import android.service.notification.NotificationListenerFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.NotificationStats;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IntArray;
import android.util.JsonWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import android.view.HapticFeedbackConstants;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.widget.LockPatternUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DeviceIdleInternal;
import com.android.server.EventLogTags;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LogicalLight;
import com.android.server.notification.GroupHelper;
import com.android.server.notification.ManagedServices;
import com.android.server.notification.NotificationManagerService;
import com.android.server.notification.NotificationRecord;
import com.android.server.notification.NotificationRecordLogger;
import com.android.server.notification.ShortcutHelper;
import com.android.server.notification.SnoozeHelper;
import com.android.server.notification.ZenModeHelper;
import com.android.server.notification.edgelighting.EdgeLightingManager;
import com.android.server.notification.sec.DisplayToast;
import com.android.server.notification.sec.runestone.CollectionContract;
import com.android.server.notification.sec.runestone.CollectionContract$Notification$Log;
import com.android.server.notification.sec.runestone.RunestoneStateContract;
import com.android.server.notification.sec.runestone.RunestoneSupportContract;
import com.android.server.notification.toast.CustomToastRecord;
import com.android.server.notification.toast.TextToastRecord;
import com.android.server.notification.toast.ToastRecord;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.BackgroundActivityStartCallback;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.policy.PermissionPolicyInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.utils.quota.MultiRateLimiter;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.sepunion.SemGoodCatchManager;
import com.samsung.android.view.SemWindowManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import libcore.io.IoUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class NotificationManagerService extends SystemService {
    public static SemGoodCatchManager mSemAudioGoodCatchManager;
    public final int DELAYED_WAKELOCK_DURATION;
    public SimpleDateFormat dayTime;
    public AccessibilityManager mAccessibilityManager;
    public ActivityManager mActivityManager;
    public AlarmManager mAlarmManager;
    public TriPredicate mAllowedManagedServicePackages;
    public Set mAllowedPackage;
    public IActivityManager mAm;
    public ActivityManagerInternal mAmi;
    public AppOpsManager mAppOps;
    public UsageStatsManagerInternal mAppUsageStats;
    public IApplicationPolicy mApplicationPolicyService;
    public Archive mArchive;
    public NotificationAssistants mAssistants;
    public ActivityTaskManagerInternal mAtm;
    public LogicalLight mAttentionLight;
    public SemGoodCatchManager.OnStateChangeListener mAudioGoodCatchStateListener;
    public AudioManager mAudioManager;
    public AudioManagerInternal mAudioManagerInternal;
    public int mAutoGroupAtCount;
    public final ArrayMap mAutobundledSummaries;
    public boolean mCMCinCallState;
    public Binder mCallNotificationToken;
    public int mCallState;
    public final List mCancelLogs;
    public ICompanionDeviceManager mCompanionManager;
    public ConditionProviders mConditionProviders;
    public List mConversationAppList;
    public AtomicFile mConversationAppPolicyFile;
    public long mConversationAppPolicyVersion;
    public List mConversationHistoryAppList;
    public int mCountForOverflowAppList;
    public CoverManager mCoverManager;
    public String mDefaultSearchSelectorPkg;
    public ConcurrentList mDelayedWakeUpList;
    public ConcurrentList mDelayedWakelockList;
    public DeviceConfig.OnPropertiesChangedListener mDeviceConfigChangedListener;
    public DeviceIdleManager mDeviceIdleManager;
    public boolean mDisableNotificationEffects;
    public boolean mDisableVibration;
    public DevicePolicyManagerInternal mDpm;
    public EasyMuteController mEasyMuteController;
    public final String mEdgeAgainedWakelockTag;
    public EdgeLightingManager mEdgeLightingManager;
    public List mEffectsSuppressors;
    public final ArrayMap mEnqueueLogs;
    public final ArrayList mEnqueuedNotifications;
    public SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    public ArrayList mFloatingPackageList;
    public boolean mFoldState;
    public SemWindowManager.FoldStateListener mFoldStateListener;
    public final IBinder mForegroundToken;
    public boolean mGoodCatchNotiBlockedOn;
    public boolean mGoodCatchNotiMutedOn;
    public boolean mGoodCatchSoundPlayedOn;
    public SemGoodCatchManager.OnStateChangeListener mGoodCatchStateListener;
    public boolean mGoodCatchViToastOn;
    public GroupHelper mGroupHelper;
    public WorkerHandler mHandler;
    public boolean mHasLight;
    public ArrayList mHighDataSizeNotificaitonList;
    public NotificationHistoryManager mHistoryManager;
    public AudioAttributes mInCallNotificationAudioAttributes;
    public Uri mInCallNotificationUri;
    public float mInCallNotificationVolume;
    public boolean mInCallStateOffHook;
    public final ArrayMap mInlineReplyRecordsByKey;
    public final BroadcastReceiver mIntentReceiver;
    public final NotificationManagerInternal mInternalService;
    public int mInterruptionFilter;
    public boolean mIsAutomotive;
    public boolean mIsCurrentToastShown;
    public boolean mIsDisableHunByCall;
    public boolean mIsEnableAlertByCall;
    public final boolean mIsFactoryBinary;
    public boolean mIsInterruptivePostNotif;
    public boolean mIsMaxNotiLimitEnabled;
    public int mIsMutedByWearableApps;
    public boolean mIsOverflowAppListLoaded;
    public boolean mIsRuneStoneEnabled;
    public boolean mIsRuneStoneSupported;
    public boolean mIsTelevision;
    public KeyguardManager mKeyguardManager;
    public long mLastOverRateLogTime;
    public ArrayList mLights;
    public List mLimitNotificationsForOverflowAppList;
    public int mListenerHints;
    public NotificationListeners mListeners;
    public final SparseArray mListenersDisablingEffects;
    public final BroadcastReceiver mLocaleChangeReceiver;
    public boolean mLockScreenAllowSecureNotifications;
    public int mMaxNotiLimitCount;
    public float mMaxPackageEnqueueRate;
    public MetricsLogger mMetricsLogger;
    public Set mMsgPkgsAllowedAsConvos;
    public boolean mMultiStarEnable;
    public boolean mNeedDeletePrevHistory;
    public SemGoodCatchManager.OnStateChangeListener mNotiGoodCatchStateListener;
    public ArrayList mNotiPermissionHistoryList;
    public SemGoodCatchManager mNotiSemGoodCatchManager;
    public NotificationChannelLogger mNotificationChannelLogger;
    final NotificationDelegate mNotificationDelegate;
    public boolean mNotificationEffectsEnabledForAutomotive;
    public InstanceIdSequence mNotificationInstanceIdSequence;
    public LogicalLight mNotificationLight;
    public final ArrayList mNotificationList;
    public final Object mNotificationLock;
    public boolean mNotificationPulseEnabled;
    public NotificationRecordLogger mNotificationRecordLogger;
    public NotificationReminder mNotificationReminder;
    public final BroadcastReceiver mNotificationTimeoutReceiver;
    public final ArrayMap mNotificationsByKey;
    public List mOngoingDismissExceptionKeyList;
    public AtomicFile mOngoingDismissExceptionPolicyFile;
    public long mOngoingDismissExceptionPolicyVersion;
    public ConcurrentHashMap mOverflowNotiUpdateTimeMap;
    public final BroadcastReceiver mPackageIntentReceiver;
    public IPackageManager mPackageManager;
    public PackageManager mPackageManagerClient;
    public PackageManagerInternal mPackageManagerInternal;
    public PermissionHelper mPermissionHelper;
    public PermissionManager mPermissionManager;
    public PermissionPolicyInternal mPermissionPolicyInternal;
    public IPlatformCompat mPlatformCompat;
    public AtomicFile mPolicyFile;
    public PostNotificationTrackerFactory mPostNotificationTrackerFactory;
    public PowerManager mPowerManager;
    PreferencesHelper mPreferencesHelper;
    public StatsPullAtomCallbackImpl mPullAtomCallback;
    public RankingHandler mRankingHandler;
    RankingHelper mRankingHelper;
    public final HandlerThread mRankingThread;
    public final BroadcastReceiver mRestoreReceiver;
    public ReviewNotificationPermissionsReceiver mReviewNotificationPermissionsReceiver;
    public volatile RoleObserver mRoleObserver;
    public final SaveConversationPackagePolicyFileRunnable mSaveConversationPackagePolicyFile;
    public final SaveOngoingDismissExceptionPolicyFileRunnable mSaveOngoingDismissExceptionPolicyFile;
    public final SavePolicyFileRunnable mSavePolicyFile;
    public final BroadcastReceiver mScpmIntentReceiver;
    public boolean mScreenOn;
    public final BroadcastReceiver mSecIntentReceiver;
    public SemGoodCatchManager mSemGoodCatchManager;
    final IBinder mService;
    public SettingsObserver mSettingsObserver;
    public ShortcutHelper mShortcutHelper;
    public ShortcutHelper.ShortcutListener mShortcutListener;
    protected boolean mShowReviewPermissionsNotification;
    public SmartAlertController mSmartAlertController;
    public boolean mSmartPopupEnable;
    public SnoozeHelper mSnoozeHelper;
    public String mSoundNotificationKey;
    public final ContentObserver mStateContentObserver;
    public StatsManager mStatsManager;
    public StatusBarManagerInternal mStatusBar;
    public int mStripRemoteViewsSizeBytes;
    public StrongAuthTracker mStrongAuthTracker;
    public final ArrayMap mSummaryByGroupKey;
    public boolean mSystemExemptFromDismissal;
    public boolean mSystemReady;
    public TelecomManager mTelecomManager;
    public final ArrayMap mTimeoutPendingIntent;
    public final ArrayList mToastQueue;
    public MultiRateLimiter mToastRateLimiter;
    public final Set mToastRateLimitingDisabledUids;
    public IUriGrantsManager mUgm;
    public UriGrantsManagerInternal mUgmInternal;
    public UserManager mUm;
    public UserManagerInternal mUmInternal;
    public NotificationUsageStats mUsageStats;
    public UsageStatsManagerInternal mUsageStatsManagerInternal;
    public boolean mUseAttentionLight;
    public final ManagedServices.UserProfiles mUserProfiles;
    public String mVibrateNotificationKey;
    public int mVibrationIndex;
    public VibratorHelper mVibratorHelper;
    public PowerManager.WakeLock mWakeLockForAssistantDelay;
    public int mWarnRemoteViewsSizeBytes;
    public WindowManagerInternal mWindowManagerInternal;
    public ZenModeHelper mZenModeHelper;
    public static final boolean DBG = Log.isLoggable("NotificationService", 3);
    public static final boolean ENABLE_CHILD_NOTIFICATIONS = SystemProperties.getBoolean("debug.child_notifs", true);
    public static final boolean DEBUG_INTERRUPTIVENESS = SystemProperties.getBoolean("debug.notification.interruptiveness", false);
    public static final String[] ALLOWED_ADJUSTMENTS = {"key_people", "key_snooze_criteria", "key_user_sentiment", "key_contextual_actions", "key_text_replies", "key_importance", "key_importance_proposal", "key_sensitive_content", "key_ranking_score", "key_not_conversation"};
    public static final String[] NON_BLOCKABLE_DEFAULT_ROLES = {"android.app.role.DIALER", "android.app.role.EMERGENCY"};
    public static final MultiRateLimiter.RateLimit[] TOAST_RATE_LIMITS = {MultiRateLimiter.RateLimit.create(3, Duration.ofSeconds(20)), MultiRateLimiter.RateLimit.create(5, Duration.ofSeconds(42)), MultiRateLimiter.RateLimit.create(6, Duration.ofSeconds(68))};
    public static final String ACTION_NOTIFICATION_TIMEOUT = NotificationManagerService.class.getSimpleName() + ".TIMEOUT";
    public static final int MY_UID = Process.myUid();
    public static final int MY_PID = Process.myPid();
    public static final IBinder ALLOWLIST_TOKEN = new Binder();
    public static final ComponentName EDGE_NOTIFICATION_COMPONENT = new ComponentName("com.samsung.android.service.peoplestripe", "com.samsung.android.service.peoplestripe.PeopleNotiListenerService");
    public static final List HEALTH_KEY_LIST = Arrays.asList("14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a", "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6");

    public interface FlagChecker {
        boolean apply(int i);
    }

    public static int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static /* synthetic */ boolean lambda$handleGroupedNotificationLocked$10(int i) {
        return (i & 64) == 0 && (i & 32768) == 0;
    }

    public int correctCategory(int i, int i2, int i3) {
        int i4 = i & i2;
        return (i4 == 0 || (i3 & i2) != 0) ? (i4 != 0 || (i3 & i2) == 0) ? i : i | i2 : i & (~i2);
    }

    public final int getRealUserId(int i) {
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public final IApplicationPolicy getApplicationPolicyService() {
        if (this.mApplicationPolicyService == null) {
            this.mApplicationPolicyService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return this.mApplicationPolicyService;
    }

    public class Archive {
        public final int mBufferSize;
        public final Object mBufferLock = new Object();
        public final LinkedList mBuffer = new LinkedList();
        public final SparseArray mEnabled = new SparseArray();

        public Archive(int i) {
            this.mBufferSize = i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int size = this.mBuffer.size();
            sb.append("Archive (");
            sb.append(size);
            sb.append(" notification");
            sb.append(size == 1 ? ")" : "s)");
            return sb.toString();
        }

        public void record(StatusBarNotification statusBarNotification, int i) {
            if (((Boolean) this.mEnabled.get(statusBarNotification.getNormalizedUserId(), Boolean.FALSE)).booleanValue()) {
                synchronized (this.mBufferLock) {
                    if (this.mBuffer.size() == this.mBufferSize) {
                        this.mBuffer.removeFirst();
                    }
                    this.mBuffer.addLast(new Pair(statusBarNotification.cloneLight(), Integer.valueOf(i)));
                }
            }
        }

        public Iterator descendingIterator() {
            return this.mBuffer.descendingIterator();
        }

        public StatusBarNotification[] getArray(final UserManager userManager, int i, boolean z) {
            StatusBarNotification[] statusBarNotificationArr;
            final ArrayList arrayList = new ArrayList();
            arrayList.add(-1);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$Archive$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    NotificationManagerService.Archive.lambda$getArray$0(userManager, arrayList);
                }
            });
            synchronized (this.mBufferLock) {
                if (i == 0) {
                    i = this.mBufferSize;
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator descendingIterator = descendingIterator();
                int i2 = 0;
                while (descendingIterator.hasNext() && i2 < i) {
                    Pair pair = (Pair) descendingIterator.next();
                    if (((Integer) pair.second).intValue() != 18 || z) {
                        if (arrayList.contains(Integer.valueOf(((StatusBarNotification) pair.first).getUserId()))) {
                            i2++;
                            arrayList2.add((StatusBarNotification) pair.first);
                        }
                    }
                }
                statusBarNotificationArr = (StatusBarNotification[]) arrayList2.toArray(new StatusBarNotification[arrayList2.size()]);
            }
            return statusBarNotificationArr;
        }

        public static /* synthetic */ void lambda$getArray$0(UserManager userManager, ArrayList arrayList) {
            for (int i : userManager.getProfileIds(ActivityManager.getCurrentUser(), false)) {
                arrayList.add(Integer.valueOf(i));
            }
        }

        public void updateHistoryEnabled(int i, boolean z) {
            this.mEnabled.put(i, Boolean.valueOf(z));
            if (z) {
                return;
            }
            synchronized (this.mBufferLock) {
                for (int size = this.mBuffer.size() - 1; size >= 0; size--) {
                    if (i == ((StatusBarNotification) ((Pair) this.mBuffer.get(size)).first).getNormalizedUserId()) {
                        this.mBuffer.remove(size);
                    }
                }
            }
        }

        public void removeChannelNotifications(String str, int i, String str2) {
            synchronized (this.mBufferLock) {
                Iterator descendingIterator = descendingIterator();
                while (descendingIterator.hasNext()) {
                    Pair pair = (Pair) descendingIterator.next();
                    Object obj = pair.first;
                    if (obj != null && i == ((StatusBarNotification) obj).getNormalizedUserId() && str != null && str.equals(((StatusBarNotification) pair.first).getPackageName()) && ((StatusBarNotification) pair.first).getNotification() != null && Objects.equals(str2, ((StatusBarNotification) pair.first).getNotification().getChannelId())) {
                        descendingIterator.remove();
                    }
                }
            }
        }

        public void dumpImpl(PrintWriter printWriter, DumpFilter dumpFilter) {
            synchronized (this.mBufferLock) {
                Iterator descendingIterator = descendingIterator();
                int i = 0;
                while (true) {
                    if (!descendingIterator.hasNext()) {
                        break;
                    }
                    StatusBarNotification statusBarNotification = (StatusBarNotification) ((Pair) descendingIterator.next()).first;
                    if (dumpFilter == null || dumpFilter.matches(statusBarNotification)) {
                        printWriter.println("    " + statusBarNotification);
                        i++;
                        if (i >= 5) {
                            if (descendingIterator.hasNext()) {
                                printWriter.println("    ...");
                            }
                        }
                    }
                }
            }
        }
    }

    public void loadDefaultApprovedServices(int i) {
        this.mListeners.loadDefaultsFromConfig();
        this.mConditionProviders.loadDefaultsFromConfig();
        this.mAssistants.loadDefaultsFromConfig();
    }

    public void allowDefaultApprovedServices(int i) {
        ArraySet defaultComponents = this.mListeners.getDefaultComponents();
        for (int i2 = 0; i2 < defaultComponents.size(); i2++) {
            allowNotificationListener(i, (ComponentName) defaultComponents.valueAt(i2));
        }
        ArraySet defaultPackages = this.mConditionProviders.getDefaultPackages();
        for (int i3 = 0; i3 < defaultPackages.size(); i3++) {
            allowDndPackage(i, (String) defaultPackages.valueAt(i3));
        }
        setDefaultAssistantForUser(i);
    }

    public void migrateDefaultNAS() {
        for (UserInfo userInfo : this.mUm.getUsers()) {
            int identifier = userInfo.getUserHandle().getIdentifier();
            if (!isNASMigrationDone(identifier) && !userInfo.isManagedProfile() && !userInfo.isCloneProfile()) {
                if (this.mAssistants.getAllowedComponents(identifier).size() == 0) {
                    Slog.d("NotificationService", "NAS Migration: user set to none, disable new NAS setting");
                    setNASMigrationDone(identifier);
                    this.mAssistants.clearDefaults();
                } else {
                    Slog.d("NotificationService", "Reset NAS setting and migrate to new default");
                    resetAssistantUserSet(identifier);
                    this.mAssistants.resetDefaultAssistantsIfNecessary();
                }
            }
        }
    }

    public void setNASMigrationDone(int i) {
        for (int i2 : this.mUm.getProfileIds(i, false)) {
            Settings.Secure.putIntForUser(getContext().getContentResolver(), "nas_settings_updated", 1, i2);
        }
    }

    public boolean isNASMigrationDone(int i) {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "nas_settings_updated", 0, i) == 1;
    }

    public void setDefaultAssistantForUser(int i) {
        String property = DeviceConfig.getProperty("systemui", "nas_default_service");
        if (property != null) {
            ArraySet queryPackageForServices = this.mAssistants.queryPackageForServices(property, 786432, i);
            for (int i2 = 0; i2 < queryPackageForServices.size(); i2++) {
                if (allowAssistant(i, (ComponentName) queryPackageForServices.valueAt(i2))) {
                    return;
                }
            }
        }
        ArraySet defaultComponents = this.mAssistants.getDefaultComponents();
        for (int i3 = 0; i3 < defaultComponents.size() && !allowAssistant(i, (ComponentName) defaultComponents.valueAt(i3)); i3++) {
        }
    }

    public void updateAutobundledSummaryFlags(int i, String str, int i2, boolean z) {
        String str2;
        NotificationRecord notificationRecord;
        ArrayMap arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(i));
        if (arrayMap == null || (str2 = (String) arrayMap.get(str)) == null || (notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str2)) == null || notificationRecord.getSbn().getNotification().flags == i2) {
            return;
        }
        notificationRecord.getSbn().getNotification().flags = i2;
        this.mHandler.post(new EnqueueNotificationRunnable(i, notificationRecord, z, this.mPostNotificationTrackerFactory.newTracker(null)));
    }

    public final void allowDndPackage(int i, String str) {
        try {
            getBinderService().setNotificationPolicyAccessGrantedForUser(str, i, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void allowNotificationListener(int i, ComponentName componentName) {
        try {
            getBinderService().setNotificationListenerAccessGrantedForUser(componentName, i, true, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final boolean allowAssistant(int i, ComponentName componentName) {
        ArraySet queryPackageForServices = this.mAssistants.queryPackageForServices(null, 786432, i);
        if (componentName == null || !queryPackageForServices.contains(componentName)) {
            return false;
        }
        setNotificationAssistantAccessGrantedForUserInternal(componentName, i, true, false);
        return true;
    }

    public void readPolicyXml(InputStream inputStream, boolean z, int i) {
        TypedXmlPullParser resolvePullParser;
        NotificationReminder notificationReminder;
        if (z) {
            resolvePullParser = Xml.newFastPullParser();
            resolvePullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        } else {
            resolvePullParser = Xml.resolvePullParser(inputStream);
        }
        XmlUtils.beginDocument(resolvePullParser, "notification-policy");
        UserInfo userInfo = this.mUmInternal.getUserInfo(i);
        boolean z2 = false;
        boolean z3 = z && (userInfo.isManagedProfile() || userInfo.isCloneProfile());
        int depth = resolvePullParser.getDepth();
        int i2 = -1;
        while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
            if ("zen".equals(resolvePullParser.getName())) {
                this.mZenModeHelper.readXml(resolvePullParser, z, i);
                if (z) {
                    List exceptionContacts = this.mZenModeHelper.getNotificationPolicy().getExceptionContacts();
                    ValidateNotificationPeople validateNotificationPeople = (ValidateNotificationPeople) this.mRankingHelper.findExtractor(ValidateNotificationPeople.class);
                    if (validateNotificationPeople != null) {
                        validateNotificationPeople.fixExceptionContacts(getContext(), exceptionContacts);
                    }
                }
            } else if ("ranking".equals(resolvePullParser.getName())) {
                i2 = resolvePullParser.getAttributeInt((String) null, "version", -1);
                this.mPreferencesHelper.readXml(resolvePullParser, z, i);
            }
            if (this.mListeners.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                if (!z3) {
                    this.mListeners.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                    z2 = true;
                }
            } else if (this.mAssistants.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                if (!z3) {
                    this.mAssistants.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                    z2 = true;
                }
            } else if (this.mConditionProviders.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                if (!z3) {
                    this.mConditionProviders.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                    z2 = true;
                }
            } else if ("snoozed-notifications".equals(resolvePullParser.getName())) {
                this.mSnoozeHelper.readXml(resolvePullParser, System.currentTimeMillis());
            }
            if ("allow-secure-notifications-on-lockscreen".equals(resolvePullParser.getName()) && (!z || i == 0)) {
                this.mLockScreenAllowSecureNotifications = resolvePullParser.getAttributeBoolean((String) null, "value", true);
            }
        }
        if (z && (notificationReminder = this.mNotificationReminder) != null && i2 < 4) {
            notificationReminder.updatePackageListForRestore();
        }
        if (!z2) {
            this.mListeners.migrateToXml();
            this.mAssistants.migrateToXml();
            this.mConditionProviders.migrateToXml();
            handleSavePolicyFile();
        }
        this.mAssistants.resetDefaultAssistantsIfNecessary();
    }

    public void loadPolicyFile() {
        if (DBG) {
            Slog.d("NotificationService", "loadPolicyFile");
        }
        synchronized (this.mPolicyFile) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    try {
                        fileInputStream = this.mPolicyFile.openRead();
                        readPolicyXml(fileInputStream, false, -1);
                    } catch (FileNotFoundException unused) {
                        loadDefaultApprovedServices(0);
                        allowDefaultApprovedServices(0);
                    } catch (XmlPullParserException e) {
                        Log.wtf("NotificationService", "Unable to parse notification policy", e);
                    }
                } catch (IOException e2) {
                    Log.wtf("NotificationService", "Unable to read notification policy", e2);
                } catch (ArrayIndexOutOfBoundsException e3) {
                    Log.wtf("NotificationService", "Unable to parse notification policy", e3);
                    this.mPolicyFile = new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), "notification_policy.xml"), "notification-policy");
                    loadDefaultApprovedServices(0);
                    allowDefaultApprovedServices(0);
                } catch (NumberFormatException e4) {
                    Log.wtf("NotificationService", "Unable to parse notification policy", e4);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    public void handleSavePolicyFile() {
        if (IoThread.getHandler().hasCallbacks(this.mSavePolicyFile)) {
            return;
        }
        IoThread.getHandler().postDelayed(this.mSavePolicyFile, 250L);
    }

    public final class SavePolicyFileRunnable implements Runnable {
        public SavePolicyFileRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "handleSavePolicyFile");
            }
            synchronized (NotificationManagerService.this.mPolicyFile) {
                try {
                    FileOutputStream startWrite = NotificationManagerService.this.mPolicyFile.startWrite();
                    try {
                        NotificationManagerService.this.writePolicyXml(startWrite, false, -1);
                        NotificationManagerService.this.mPolicyFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w("NotificationService", "Failed to save policy file, restoring backup", e);
                        NotificationManagerService.this.mPolicyFile.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("NotificationService", "Failed to save policy file", e2);
                    return;
                }
            }
            BackupManager.dataChanged(NotificationManagerService.this.getContext().getPackageName());
        }
    }

    public final void writePolicyXml(OutputStream outputStream, boolean z, int i) {
        TypedXmlSerializer resolveSerializer;
        if (z) {
            resolveSerializer = Xml.newFastSerializer();
            resolveSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        } else {
            resolveSerializer = Xml.resolveSerializer(outputStream);
        }
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, "notification-policy");
        resolveSerializer.attributeInt((String) null, "version", 1);
        this.mZenModeHelper.writeXml(resolveSerializer, z, null, i);
        this.mPreferencesHelper.writeXml(resolveSerializer, z, i);
        this.mListeners.writeXml(resolveSerializer, z, i);
        this.mAssistants.writeXml(resolveSerializer, z, i);
        this.mSnoozeHelper.writeXml(resolveSerializer);
        this.mConditionProviders.writeXml(resolveSerializer, z, i);
        if (!z || i == 0) {
            writeSecureNotificationsPolicy(resolveSerializer);
        }
        resolveSerializer.endTag((String) null, "notification-policy");
        resolveSerializer.endDocument();
    }

    public final void handleApplyRestore(byte[] bArr, int i) {
        if (DBG) {
            StringBuilder sb = new StringBuilder();
            sb.append("handleApplyRestore u=");
            sb.append(i);
            sb.append(" payload=");
            sb.append(bArr != null ? new String(bArr, StandardCharsets.UTF_8) : null);
            Slog.d("NotificationService", sb.toString());
        }
        try {
            getBinderService().applyRestore(bArr, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void loadConversationPackagePolicyFile() {
        Slog.d("NotificationService", "loadConversationPackagePolicyFile");
        synchronized (this.mConversationAppPolicyFile) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = this.mConversationAppPolicyFile.openRead();
                    readConversationAppPolicyJson(fileInputStream, false);
                } catch (FileNotFoundException e) {
                    Log.wtf("NotificationService", "Conversation apps policy file doesn't exist", e);
                    loadDefaultConversationPackageList();
                    loadDefaultConversationHistoryPackageList();
                    handleSaveConversationPackagePolicyFile();
                } catch (IOException e2) {
                    Log.wtf("NotificationService", "Unable to read conversation apps policy", e2);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:131:0x017e -> B:44:0x01e7). Please report as a decompilation issue!!! */
    public final void readConversationAppPolicyJson(FileInputStream fileInputStream, boolean z) {
        ?? r1;
        Slog.d("NotificationService", "readConversationAppPolicyJson");
        StringBuilder sb = new StringBuilder();
        ?? r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        try {
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                sb.append(readLine);
                            }
                        } catch (IOException e) {
                            e = e;
                            r4 = bufferedReader;
                            e.printStackTrace();
                            if (r4 != 0) {
                                try {
                                    try {
                                        r4.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                        r4.close();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            if (r4 != 0) {
                                r4.close();
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            r4 = bufferedReader;
                            e.printStackTrace();
                            try {
                                if (r4 != 0) {
                                    try {
                                        r4.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        r4.close();
                                    }
                                }
                                if (r4 != 0) {
                                    r4.close();
                                }
                            } finally {
                                try {
                                    r4.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            r4 = bufferedReader;
                            try {
                                if (r4 != 0) {
                                    try {
                                        try {
                                            r4.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                            r4.close();
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        try {
                                            r4.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                        throw th3;
                                    }
                                }
                                if (r4 != 0) {
                                    r4.close();
                                }
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                            throw th;
                        }
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    long j = jSONObject.getLong("policy_version");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("readConversationAppPolicyJson - current version = ");
                    sb2.append(this.mConversationAppPolicyVersion);
                    r4 = " new version = ";
                    sb2.append(" new version = ");
                    sb2.append(j);
                    Slog.d("NotificationService", sb2.toString());
                    if (j > this.mConversationAppPolicyVersion) {
                        this.mConversationAppPolicyVersion = j;
                        JSONArray jSONArray = jSONObject.getJSONArray("appList");
                        this.mConversationAppList.clear();
                        int length = jSONArray.length();
                        for (int i = 0; i < length; i++) {
                            this.mConversationAppList.add(jSONArray.get(i).toString());
                            if (DBG) {
                                Slog.d("NotificationService", "readConversationAppPolicyJson - packageList = " + jSONArray.get(i).toString());
                            }
                        }
                        Slog.d("NotificationService", "readConversationAppPolicyJson - size = " + length);
                        r4 = 1;
                        if (jSONObject.has("historyAppList")) {
                            JSONArray jSONArray2 = jSONObject.getJSONArray("historyAppList");
                            int length2 = jSONArray2.length();
                            this.mConversationHistoryAppList.clear();
                            for (int i2 = 0; i2 < length2; i2++) {
                                this.mConversationHistoryAppList.add(jSONArray2.get(i2).toString());
                                if (DBG) {
                                    Slog.d("NotificationService", "readConversationAppPolicyJson - historyPackageList = " + jSONArray2.get(i2).toString());
                                }
                            }
                            Slog.d("NotificationService", "readConversationAppPolicyJson - history size = " + length2);
                            r1 = false;
                        } else {
                            loadDefaultConversationHistoryPackageList();
                            r1 = true;
                        }
                        if (jSONObject.has("maxNotiLimitPolicy")) {
                            JSONArray jSONArray3 = jSONObject.getJSONArray("maxNotiLimitPolicy");
                            int length3 = jSONArray3.length();
                            if (length3 == 2) {
                                this.mIsMaxNotiLimitEnabled = jSONArray3.getBoolean(0);
                                this.mMaxNotiLimitCount = jSONArray3.getInt(1);
                            }
                            Slog.d("NotificationService", "readConversationAppPolicyJson - max noti policy size = " + length3 + " enabled = " + this.mIsMaxNotiLimitEnabled + " max count = " + this.mMaxNotiLimitCount);
                            r4 = r1;
                        }
                        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
                            this.mPreferencesHelper.setHideContentAllowList(this.mConversationAppList);
                        }
                        if (z || r4 != 0) {
                            handleSaveConversationPackagePolicyFile();
                        }
                    }
                    try {
                        try {
                            bufferedReader.close();
                            bufferedReader.close();
                        } catch (Throwable th4) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e9) {
                                e9.printStackTrace();
                            }
                            throw th4;
                        }
                    } catch (IOException e10) {
                        e10.printStackTrace();
                        bufferedReader.close();
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (IOException e11) {
                e = e11;
            } catch (JSONException e12) {
                e = e12;
            }
        } catch (IOException e13) {
            e13.printStackTrace();
        }
    }

    public final void loadDefaultConversationPackageList() {
        Slog.d("NotificationService", "loadDefaultConversationPackageList");
        this.mConversationAppPolicyVersion = 1L;
        String string = getContext().getResources().getString(R.string.config_wearServiceComponent);
        if (string != null) {
            this.mConversationAppList.clear();
            this.mConversationAppList = (List) Arrays.stream(string.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultConversationPackageList - size = " + this.mConversationAppList.size());
        }
        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
            this.mPreferencesHelper.setHideContentAllowList(this.mConversationAppList);
        }
    }

    public final void loadDefaultConversationHistoryPackageList() {
        Slog.d("NotificationService", "loadDefaultConversationHistoryPackageList");
        String string = getContext().getResources().getString(R.string.config_wearSysUiMainActivity);
        if (string != null) {
            this.mConversationHistoryAppList.clear();
            this.mConversationHistoryAppList = (List) Arrays.stream(string.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultConversationHistoryPackageList - size = " + this.mConversationHistoryAppList.size());
        }
    }

    public final void handleSaveConversationPackagePolicyFile() {
        Slog.d("NotificationService", "handleSaveConversationPackagePolicyFile");
        if (IoThread.getHandler().hasCallbacks(this.mSaveConversationPackagePolicyFile)) {
            return;
        }
        IoThread.getHandler().postDelayed(this.mSaveConversationPackagePolicyFile, 250L);
    }

    public final class SaveConversationPackagePolicyFileRunnable implements Runnable {
        public SaveConversationPackagePolicyFileRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "handleSaveConversationPackagePolicyFile runnable");
            }
            synchronized (NotificationManagerService.this.mConversationAppPolicyFile) {
                try {
                    try {
                        FileOutputStream startWrite = NotificationManagerService.this.mConversationAppPolicyFile.startWrite();
                        try {
                            NotificationManagerService.this.writeConversationAppPolicyJson(startWrite);
                            NotificationManagerService.this.mConversationAppPolicyFile.finishWrite(startWrite);
                        } catch (Exception e) {
                            Slog.w("NotificationService", "Failed to save conversation package policy file, restoring backup", e);
                            NotificationManagerService.this.mConversationAppPolicyFile.failWrite(startWrite);
                        }
                    } catch (IOException e2) {
                        Slog.w("NotificationService", "Failed to save conversation package policy file", e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.OutputStreamWriter, java.io.Writer] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x00b8 -> B:19:0x00bb). Please report as a decompilation issue!!! */
    public final void writeConversationAppPolicyJson(FileOutputStream fileOutputStream) {
        JsonWriter jsonWriter;
        ?? r1 = "writeConversationAppPolicyJson";
        Slog.d("NotificationService", "writeConversationAppPolicyJson");
        JsonWriter jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        try {
            try {
                try {
                    r1 = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    try {
                        jsonWriter = new JsonWriter(r1);
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    jsonWriter2 = jsonWriter2;
                    r1 = r1;
                }
                try {
                    jsonWriter.setIndent(" ");
                    jsonWriter.beginObject();
                    jsonWriter.name("policy_version").value(this.mConversationAppPolicyVersion);
                    jsonWriter.name("appList");
                    jsonWriter.beginArray();
                    int size = this.mConversationAppList.size();
                    for (int i = 0; i < size; i++) {
                        jsonWriter.value(this.mConversationAppList.get(i).toString());
                    }
                    jsonWriter.endArray();
                    jsonWriter.name("historyAppList");
                    jsonWriter.beginArray();
                    int size2 = this.mConversationHistoryAppList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        jsonWriter.value(this.mConversationHistoryAppList.get(i2).toString());
                    }
                    jsonWriter.endArray();
                    jsonWriter.name("maxNotiLimitPolicy");
                    jsonWriter.beginArray();
                    boolean z = this.mIsMaxNotiLimitEnabled;
                    jsonWriter.value(z);
                    jsonWriter.value(this.mMaxNotiLimitCount);
                    jsonWriter.endArray();
                    jsonWriter.endObject();
                    try {
                        jsonWriter.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    r1.close();
                    jsonWriter2 = z;
                    r1 = r1;
                } catch (IOException e4) {
                    e = e4;
                    jsonWriter2 = jsonWriter;
                    e.printStackTrace();
                    if (jsonWriter2 != null) {
                        try {
                            jsonWriter2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (r1 != 0) {
                        r1.close();
                        jsonWriter2 = jsonWriter2;
                        r1 = r1;
                    }
                } catch (Throwable th) {
                    th = th;
                    jsonWriter2 = jsonWriter;
                    if (jsonWriter2 != null) {
                        try {
                            jsonWriter2.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (r1 != 0) {
                        try {
                            r1.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (IOException e8) {
                e = e8;
                r1 = 0;
            } catch (Throwable th2) {
                th = th2;
                r1 = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* renamed from: registerConversationAppPolicyScpm, reason: merged with bridge method [inline-methods] */
    public final void lambda$onBootPhase$2() {
        Slog.d("NotificationService", "registerConversationAppPolicyScpm");
        if (isScpmAvailable()) {
            try {
                Bundle scpmBundle = getScpmBundle(0, null, Uri.parse("content://com.samsung.android.scpm.policy/"));
                if (scpmBundle != null) {
                    int i = scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1);
                    String string = scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                    int i2 = scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                    String string2 = scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                    if (i == 1) {
                        writeScpmTokenSharedPreference(string);
                        Slog.d("NotificationService", "registerConversationAppPolicyScpm - success");
                    } else {
                        Slog.e("NotificationService", "failed to registerConversationAppPolicyScpm: rcode = " + i2 + ", rmsg = " + string2);
                    }
                }
            } catch (Exception e) {
                Slog.e("NotificationService", "cannot registerConversationAppPolicyScpm : " + e.getMessage());
            }
        }
    }

    public final void writeScpmTokenSharedPreference(String str) {
        SharedPreferences.Editor edit = getContext().getSharedPreferences("conversation_app_ploicy_pref", 0).edit();
        edit.putString("ConversationAppPloicyToken", str);
        edit.commit();
    }

    public final void getConversationAppPolicyScpmData() {
        ParcelFileDescriptor parcelFileDescriptor;
        Slog.d("NotificationService", "getConversationAppPolicyScpmData");
        if (isScpmAvailable()) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    String string = getContext().getSharedPreferences("conversation_app_ploicy_pref", 0).getString("ConversationAppPloicyToken", "");
                    Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/" + string + "/NSF_CONVERSATION_APPS");
                    parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(parse, "r");
                    try {
                        if (parcelFileDescriptor == null) {
                            Bundle scpmBundle = getScpmBundle(1, string, parse);
                            if (scpmBundle != null) {
                                Slog.d("NotificationService", "getConversationAppPolicyScpmData - cannot get new policy : " + scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE) + ", " + scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
                            }
                        } else {
                            Bundle scpmBundle2 = getScpmBundle(2, string, parse);
                            if (scpmBundle2 != null) {
                                Slog.d("NotificationService", "policy filterId : " + scpmBundle2.getString("filterId"));
                                FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                                try {
                                    readConversationAppPolicyJson(fileInputStream2, true);
                                    fileInputStream = fileInputStream2;
                                } catch (FileNotFoundException e) {
                                    e = e;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("NotificationService", "getConversationAppPolicyScpmData - File not found");
                                    e.printStackTrace();
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor == null) {
                                        return;
                                    }
                                    parcelFileDescriptor.close();
                                } catch (Exception e2) {
                                    e = e2;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("NotificationService", "getConversationAppPolicyScpmData - File open fail", e);
                                    e.printStackTrace();
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor == null) {
                                        return;
                                    }
                                    parcelFileDescriptor.close();
                                } catch (Throwable th) {
                                    th = th;
                                    fileInputStream = fileInputStream2;
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor != null) {
                                        try {
                                            parcelFileDescriptor.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    throw th;
                                }
                            }
                        }
                        IoUtils.closeQuietly(fileInputStream);
                        if (parcelFileDescriptor == null) {
                            return;
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                    } catch (Exception e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                parcelFileDescriptor = null;
            } catch (Exception e6) {
                e = e6;
                parcelFileDescriptor = null;
            } catch (Throwable th3) {
                th = th3;
                parcelFileDescriptor = null;
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused2) {
            }
        }
    }

    public final void loadOngoingDismissExceptionPolicyFile() {
        Slog.d("NotificationService", "loadOngoingDismissExceptionPolicyFile");
        synchronized (this.mOngoingDismissExceptionPolicyFile) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = this.mOngoingDismissExceptionPolicyFile.openRead();
                    readOngoingDismissExceptionPolicyJson(fileInputStream, false);
                } catch (FileNotFoundException e) {
                    Log.wtf("NotificationService", "Ongoing dismiss exception file doesn't exist", e);
                    loadDefaultOngoingDismissExceptionKeyList();
                    loadDefaultLimitNotificationsForOverflowAppList();
                    handleSaveOngoingDismissExceptionPolicyFile();
                } catch (IOException e2) {
                    Log.wtf("NotificationService", "Unable to read ongoing dismiss exception policy", e2);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v21, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public final void readOngoingDismissExceptionPolicyJson(FileInputStream fileInputStream, boolean z) {
        Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson");
        StringBuilder sb = new StringBuilder();
        ?? r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        try {
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                sb.append(readLine);
                            }
                        } catch (IOException e) {
                            e = e;
                            r3 = bufferedReader;
                            e.printStackTrace();
                            try {
                                if (r3 != 0) {
                                    try {
                                        r3.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                        r3.close();
                                    }
                                }
                                if (r3 != 0) {
                                    r3.close();
                                }
                            } finally {
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            r3 = bufferedReader;
                            e.printStackTrace();
                            if (r3 != 0) {
                                try {
                                    try {
                                        r3.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        r3.close();
                                    }
                                } finally {
                                }
                            }
                            if (r3 != 0) {
                                r3.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            r3 = bufferedReader;
                            try {
                                try {
                                    if (r3 != 0) {
                                        try {
                                            r3.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            r3.close();
                                            throw th;
                                        }
                                    }
                                    if (r3 != 0) {
                                        r3.close();
                                    }
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                                throw th;
                            } finally {
                                try {
                                    r3.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                        }
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    long j = jSONObject.getLong("policy_version");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("readOngoingDismissExceptionPolicyJson - current version = ");
                    sb2.append(this.mOngoingDismissExceptionPolicyVersion);
                    r3 = " new version = ";
                    sb2.append(" new version = ");
                    sb2.append(j);
                    Slog.d("NotificationService", sb2.toString());
                    if (j > this.mOngoingDismissExceptionPolicyVersion) {
                        this.mOngoingDismissExceptionPolicyVersion = j;
                        JSONArray jSONArray = jSONObject.getJSONArray("keyList");
                        this.mOngoingDismissExceptionKeyList.clear();
                        int length = jSONArray.length();
                        boolean z2 = false;
                        for (int i = 0; i < length; i++) {
                            this.mOngoingDismissExceptionKeyList.add(jSONArray.get(i).toString());
                            if (DBG) {
                                Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - packageList = " + jSONArray.get(i).toString());
                            }
                        }
                        Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - size = " + length);
                        if (jSONObject.has("limitNotificationAppList")) {
                            JSONArray jSONArray2 = jSONObject.getJSONArray("limitNotificationAppList");
                            this.mLimitNotificationsForOverflowAppList.clear();
                            int length2 = jSONArray2.length();
                            int i2 = 0;
                            int i3 = length;
                            while (i2 < length2) {
                                this.mLimitNotificationsForOverflowAppList.add(jSONArray2.get(i2).toString());
                                ?? r32 = DBG;
                                if (r32 != 0) {
                                    r32 = "readOngoingDismissExceptionPolicyJson - limit notification List = " + jSONArray2.get(i2).toString();
                                    Slog.d("NotificationService", (String) r32);
                                }
                                i2++;
                                i3 = r32;
                            }
                            Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - limit notification size = " + length2);
                            r3 = i3;
                        } else {
                            loadDefaultLimitNotificationsForOverflowAppList();
                            z2 = true;
                            r3 = length;
                        }
                        if (z || z2) {
                            handleSaveOngoingDismissExceptionPolicyFile();
                        }
                    }
                    try {
                        try {
                            bufferedReader.close();
                            bufferedReader.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                            bufferedReader.close();
                        }
                    } catch (Throwable th2) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                        throw th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (IOException e10) {
                e = e10;
            } catch (JSONException e11) {
                e = e11;
            }
        } catch (IOException e12) {
            e12.printStackTrace();
        }
    }

    public final void loadDefaultOngoingDismissExceptionKeyList() {
        Slog.d("NotificationService", "loadDefaultOngoingDismissExceptionKeyList");
        this.mOngoingDismissExceptionPolicyVersion = 1L;
        String string = getContext().getResources().getString(R.string.edit_accessibility_shortcut_menu_button);
        if (string != null) {
            this.mOngoingDismissExceptionKeyList.clear();
            this.mOngoingDismissExceptionKeyList = (List) Arrays.stream(string.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultOngoingDismissExceptionKeyList - size = " + this.mOngoingDismissExceptionKeyList.size());
        }
    }

    public final void loadDefaultLimitNotificationsForOverflowAppList() {
        Slog.d("NotificationService", "loadDefaultLimitNotificationsForOverflowAppList");
        String string = getContext().getResources().getString(R.string.duration_days_shortest);
        if (string != null) {
            this.mLimitNotificationsForOverflowAppList.clear();
            this.mLimitNotificationsForOverflowAppList = (List) Arrays.stream(string.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultLimitNotificationsForOverflowAppList - size = " + this.mLimitNotificationsForOverflowAppList.size());
        }
    }

    public final void getOngoingDismissExceptionPolicyScpmData() {
        ParcelFileDescriptor parcelFileDescriptor;
        Slog.d("NotificationService", "getOngoingDismissExceptionPolicyScpmData");
        if (isScpmAvailable()) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    String string = getContext().getSharedPreferences("conversation_app_ploicy_pref", 0).getString("ConversationAppPloicyToken", "");
                    Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/" + string + "/nsf-ongoing-dismiss-exception-keys");
                    parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(parse, "r");
                    try {
                        if (parcelFileDescriptor == null) {
                            Bundle scpmBundle = getScpmBundle(1, string, parse);
                            if (scpmBundle != null) {
                                Slog.d("NotificationService", "getOngoingDismissExceptionPolicyScpmData - cannot get new policy : " + scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE) + ", " + scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
                            }
                        } else {
                            Bundle scpmBundle2 = getScpmBundle(2, string, parse);
                            if (scpmBundle2 != null) {
                                Slog.d("NotificationService", "OngoingDismissException policy filterId : " + scpmBundle2.getString("filterId"));
                                FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                                try {
                                    readOngoingDismissExceptionPolicyJson(fileInputStream2, true);
                                    fileInputStream = fileInputStream2;
                                } catch (FileNotFoundException e) {
                                    e = e;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("NotificationService", "getOngoingDismissExceptionPolicyScpmData - File not found");
                                    e.printStackTrace();
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor == null) {
                                        return;
                                    }
                                    parcelFileDescriptor.close();
                                } catch (Exception e2) {
                                    e = e2;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("NotificationService", "getOngoingDismissExceptionPolicyScpmData - File open fail", e);
                                    e.printStackTrace();
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor == null) {
                                        return;
                                    }
                                    parcelFileDescriptor.close();
                                } catch (Throwable th) {
                                    th = th;
                                    fileInputStream = fileInputStream2;
                                    IoUtils.closeQuietly(fileInputStream);
                                    if (parcelFileDescriptor != null) {
                                        try {
                                            parcelFileDescriptor.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    throw th;
                                }
                            }
                        }
                        IoUtils.closeQuietly(fileInputStream);
                        if (parcelFileDescriptor == null) {
                            return;
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                    } catch (Exception e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                parcelFileDescriptor = null;
            } catch (Exception e6) {
                e = e6;
                parcelFileDescriptor = null;
            } catch (Throwable th3) {
                th = th3;
                parcelFileDescriptor = null;
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused2) {
            }
        }
    }

    public final void handleSaveOngoingDismissExceptionPolicyFile() {
        Slog.d("NotificationService", "handleSaveOngoingDismissExceptionPolicyFile");
        if (IoThread.getHandler().hasCallbacks(this.mSaveOngoingDismissExceptionPolicyFile)) {
            return;
        }
        IoThread.getHandler().postDelayed(this.mSaveOngoingDismissExceptionPolicyFile, 250L);
    }

    public final class SaveOngoingDismissExceptionPolicyFileRunnable implements Runnable {
        public SaveOngoingDismissExceptionPolicyFileRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "handleSaveOngoingDismissExceptionPolicyFile runnable");
            }
            synchronized (NotificationManagerService.this.mOngoingDismissExceptionPolicyFile) {
                try {
                    try {
                        FileOutputStream startWrite = NotificationManagerService.this.mOngoingDismissExceptionPolicyFile.startWrite();
                        try {
                            NotificationManagerService.this.writeOngoingDismissExceptionPolicyJson(startWrite);
                            NotificationManagerService.this.mOngoingDismissExceptionPolicyFile.finishWrite(startWrite);
                        } catch (Exception e) {
                            Slog.w("NotificationService", "Failed to save ongoing dismiss exception policy file, restoring backup", e);
                            NotificationManagerService.this.mOngoingDismissExceptionPolicyFile.failWrite(startWrite);
                        }
                    } catch (IOException e2) {
                        Slog.w("NotificationService", "Failed to save ongoing dismiss exception policy file", e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v18, types: [int] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.OutputStreamWriter, java.io.Writer] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x00a7 -> B:19:0x00aa). Please report as a decompilation issue!!! */
    public final void writeOngoingDismissExceptionPolicyJson(FileOutputStream fileOutputStream) {
        JsonWriter jsonWriter;
        ?? r1 = "writeOngoingDismissExceptionPolicyJson";
        Slog.d("NotificationService", "writeOngoingDismissExceptionPolicyJson");
        JsonWriter jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        jsonWriter2 = null;
        try {
            try {
                try {
                    r1 = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    try {
                        jsonWriter = new JsonWriter(r1);
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    jsonWriter2 = jsonWriter2;
                    r1 = r1;
                }
                try {
                    jsonWriter.setIndent(" ");
                    jsonWriter.beginObject();
                    jsonWriter.name("policy_version").value(this.mOngoingDismissExceptionPolicyVersion);
                    jsonWriter.name("keyList");
                    jsonWriter.beginArray();
                    int size = this.mOngoingDismissExceptionKeyList.size();
                    for (int i = 0; i < size; i++) {
                        jsonWriter.value(((String) this.mOngoingDismissExceptionKeyList.get(i)).toString());
                    }
                    jsonWriter.endArray();
                    jsonWriter.name("limitNotificationAppList");
                    jsonWriter.beginArray();
                    ?? size2 = this.mLimitNotificationsForOverflowAppList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        jsonWriter.value(((String) this.mLimitNotificationsForOverflowAppList.get(i2)).toString());
                    }
                    jsonWriter.endArray();
                    jsonWriter.endObject();
                    try {
                        jsonWriter.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    r1.close();
                    jsonWriter2 = size2;
                    r1 = r1;
                } catch (IOException e4) {
                    e = e4;
                    jsonWriter2 = jsonWriter;
                    e.printStackTrace();
                    if (jsonWriter2 != null) {
                        try {
                            jsonWriter2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (r1 != 0) {
                        r1.close();
                        jsonWriter2 = jsonWriter2;
                        r1 = r1;
                    }
                } catch (Throwable th) {
                    th = th;
                    jsonWriter2 = jsonWriter;
                    if (jsonWriter2 != null) {
                        try {
                            jsonWriter2.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (r1 != 0) {
                        try {
                            r1.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (IOException e8) {
                e = e8;
                r1 = 0;
            } catch (Throwable th2) {
                th = th2;
                r1 = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final Bundle getScpmBundle(int i, String str, Uri uri) {
        Bundle bundle = new Bundle();
        if (i == 0) {
            bundle.putString("packageName", "android");
            bundle.putString("appId", "zcbtj3qbt1");
            bundle.putString("version", "0.0.0");
            bundle.putString("receiverPackageName", "android");
            return getContext().getContentResolver().call(uri, "register", "android", bundle);
        }
        if (i != 1 && i != 2) {
            return null;
        }
        bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, str);
        if (i == 1) {
            return getContext().getContentResolver().call(uri, "getLastError", "android", bundle);
        }
        return getContext().getContentResolver().call(uri, "getStatus", "android", bundle);
    }

    public final boolean isScpmAvailable() {
        return getContext().getPackageManager().resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    public final void handleSendNotificationForOverflow(NotificationRecord notificationRecord, int i, int i2) {
        String packageName = notificationRecord.getSbn().getPackageName();
        NotificationRecord notificationRecord2 = new NotificationRecord(getContext(), notificationRecord.getSbn(), notificationRecord.getChannel());
        boolean z = this.mActivityManager.getPackageImportance(packageName) == 100;
        PostNotificationTracker acquireWakeLockForPost = acquireWakeLockForPost(packageName, i2);
        long currentTimeMillis = System.currentTimeMillis();
        this.mOverflowNotiUpdateTimeMap.put(notificationRecord2.getSbn().getKey(), Long.valueOf(currentTimeMillis));
        Slog.w("NotificationService", "handle the notification for overflow uid=" + notificationRecord2.getSbn().getUid() + " pkg=" + notificationRecord2.getSbn().getPackageName() + " channelId=" + notificationRecord2.getNotification().getChannelId() + " postTime=" + currentTimeMillis);
        this.mHandler.post(new EnqueueNotificationRunnable(i, notificationRecord2, z, acquireWakeLockForPost));
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$2 */
    public class C19982 implements NotificationDelegate {
        public C19982() {
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void prepareForPossibleShutdown() {
            NotificationManagerService.this.mHistoryManager.triggerWriteToDisk();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onSetDisabled(int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.mDisableNotificationEffects = (i & 262144) != 0;
                if (NotificationManagerService.this.disableNotificationEffects(null) != null) {
                    NotificationManagerService.this.clearSoundLocked();
                    NotificationManagerService.this.clearVibrateLocked();
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onClearAll(int i, int i2, int i3) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.cancelAllLocked(i, i2, i3, 3, null, true);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationClick(int i, int i2, String str, NotificationVisibility notificationVisibility) {
            NotificationManagerService.this.exitIdle();
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord == null) {
                    Slog.w("NotificationService", "No notification with key: " + str);
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                MetricsLogger.action(notificationRecord.getItemLogMaker().setType(4).addTaggedData(798, Integer.valueOf(notificationVisibility.rank)).addTaggedData(1395, Integer.valueOf(notificationVisibility.count)));
                NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_CLICKED, notificationRecord);
                EventLogTags.writeNotificationClicked(str, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), notificationVisibility.rank, notificationVisibility.count);
                StatusBarNotification sbn = notificationRecord.getSbn();
                NotificationManagerService.this.cancelNotification(i, i2, sbn.getPackageName(), sbn.getTag(), sbn.getId(), 16, 36928, false, notificationRecord.getUserId(), 1, notificationVisibility.rank, notificationVisibility.count, null);
                NotificationManagerService.this.mHistoryManager.updateCancelEvent(sbn.getNormalizedUserId(), str, false);
                notificationVisibility.recycle();
                NotificationManagerService.this.reportUserInteraction(notificationRecord);
                NotificationManagerService.this.mAssistants.notifyAssistantNotificationClicked(notificationRecord);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationActionClick(int i, int i2, String str, int i3, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) {
            NotificationManagerService.this.exitIdle();
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord == null) {
                    Slog.w("NotificationService", "No notification with key: " + str);
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                int i4 = 1;
                LogMaker addTaggedData = notificationRecord.getLogMaker(currentTimeMillis).setCategory(129).setType(4).setSubtype(i3).addTaggedData(798, Integer.valueOf(notificationVisibility.rank)).addTaggedData(1395, Integer.valueOf(notificationVisibility.count)).addTaggedData(1601, Integer.valueOf(action.isContextual() ? 1 : 0));
                if (!z) {
                    i4 = 0;
                }
                MetricsLogger.action(addTaggedData.addTaggedData(1600, Integer.valueOf(i4)).addTaggedData(1629, Integer.valueOf(notificationVisibility.location.toMetricsEventEnum())));
                NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.fromAction(i3, z, action.isContextual()), notificationRecord);
                EventLogTags.writeNotificationActionClicked(str, i3, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), notificationVisibility.rank, notificationVisibility.count);
                notificationVisibility.recycle();
                NotificationManagerService.this.reportUserInteraction(notificationRecord);
                NotificationManagerService.this.mAssistants.notifyAssistantActionClicked(notificationRecord, action, z);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationClear(int i, int i2, String str, int i3, String str2, int i4, int i5, NotificationVisibility notificationVisibility) {
            String str3;
            int i6;
            int i7;
            String str4;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str2);
                if (notificationRecord != null) {
                    notificationRecord.recordDismissalSurface(i4);
                    notificationRecord.recordDismissalSentiment(i5);
                    str3 = notificationRecord.getSbn().getTag();
                    i6 = notificationRecord.getSbn().getId();
                } else {
                    str3 = null;
                    i6 = 0;
                }
                i7 = i6;
                str4 = str3;
            }
            NotificationManagerService.this.cancelNotification(i, i2, str, str4, i7, 0, NotificationManagerService.this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.ALLOW_DISMISS_ONGOING) ? IInstalld.FLAG_FORCE : 2, true, i3, 2, notificationVisibility.rank, notificationVisibility.count, null);
            notificationVisibility.recycle();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onPanelRevealed(boolean z, int i) {
            MetricsLogger.visible(NotificationManagerService.this.getContext(), 127);
            MetricsLogger.histogram(NotificationManagerService.this.getContext(), "note_load", i);
            NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN);
            EventLogTags.writeNotificationPanelRevealed(i);
            if (z) {
                clearEffects();
            }
            NotificationManagerService.this.mAssistants.onPanelRevealed(i);
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onPanelHidden() {
            MetricsLogger.hidden(NotificationManagerService.this.getContext(), 127);
            NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_CLOSE);
            EventLogTags.writeNotificationPanelHidden();
            NotificationManagerService.this.mAssistants.onPanelHidden();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void clearEffects() {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                if (NotificationManagerService.DBG) {
                    Slog.d("NotificationService", "clearEffects");
                }
                NotificationManagerService.this.clearSoundLocked();
                NotificationManagerService.this.clearVibrateLocked();
                NotificationManagerService.this.clearLightsLocked();
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationError(int i, int i2, final String str, final String str2, final int i3, final int i4, final int i5, final String str3, int i6) {
            boolean z;
            boolean z2;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(str, str2, i3, i6);
                z = (findNotificationLocked == null || (findNotificationLocked.getNotification().flags & 64) == 0) ? false : true;
                z2 = (findNotificationLocked == null || (findNotificationLocked.getNotification().flags & 32768) == 0) ? false : true;
            }
            NotificationManagerService.this.cancelNotification(i, i2, str, str2, i3, 0, 0, false, i6, 4, null);
            if (z || z2) {
                final int i7 = z ? 3 : 6;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$2$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        NotificationManagerService.C19982.this.lambda$onNotificationError$0(i4, i5, str, str2, i3, str3, i7);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotificationError$0(int i, int i2, String str, String str2, int i3, String str3, int i4) {
            NotificationManagerService.this.mAm.crashApplicationWithType(i, i2, str, -1, "Bad notification(tag=" + str2 + ", id=" + i3 + ") posted from package " + str + ", crashing app(uid=" + i + ", pid=" + i2 + "): " + str3, true, i4);
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                for (NotificationVisibility notificationVisibility : notificationVisibilityArr) {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(notificationVisibility.key);
                    if (notificationRecord != null) {
                        if (!notificationRecord.isSeen()) {
                            if (NotificationManagerService.DBG) {
                                Slog.d("NotificationService", "Marking notification as visible " + notificationVisibility.key);
                            }
                            NotificationManagerService.this.reportSeen(notificationRecord);
                        }
                        NotificationManagerService.this.shownNotificationLog(notificationRecord);
                        boolean z = true;
                        notificationRecord.setVisibility(true, notificationVisibility.rank, notificationVisibility.count, NotificationManagerService.this.mNotificationRecordLogger);
                        NotificationManagerService.this.mAssistants.notifyAssistantVisibilityChangedLocked(notificationRecord, true);
                        if (notificationVisibility.location != NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP) {
                            z = false;
                        }
                        if (z || notificationRecord.hasBeenVisiblyExpanded()) {
                            NotificationManagerService.this.logSmartSuggestionsVisible(notificationRecord, notificationVisibility.location.toMetricsEventEnum());
                        }
                        NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                        notificationVisibility.recycle();
                    }
                }
                for (NotificationVisibility notificationVisibility2 : notificationVisibilityArr2) {
                    NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(notificationVisibility2.key);
                    if (notificationRecord2 != null) {
                        notificationRecord2.setVisibility(false, notificationVisibility2.rank, notificationVisibility2.count, NotificationManagerService.this.mNotificationRecordLogger);
                        NotificationManagerService.this.mAssistants.notifyAssistantVisibilityChangedLocked(notificationRecord2, false);
                        notificationVisibility2.recycle();
                    }
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.stats.onExpansionChanged(z, z2);
                    if (notificationRecord.hasBeenVisiblyExpanded()) {
                        NotificationManagerService.this.logSmartSuggestionsVisible(notificationRecord, i);
                    }
                    if (z) {
                        MetricsLogger.action(notificationRecord.getItemLogMaker().setType(z2 ? 3 : 14));
                        NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.fromExpanded(z2, z), notificationRecord);
                    }
                    if (z2 && z) {
                        notificationRecord.recordExpanded();
                        NotificationManagerService.this.reportUserInteraction(notificationRecord);
                        NotificationManagerService.this.expandedNotificationLog(notificationRecord);
                    }
                    NotificationManagerService.this.mAssistants.notifyAssistantExpansionChangedLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), z, z2);
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationDirectReplied(String str) {
            NotificationManagerService.this.exitIdle();
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.recordDirectReplied();
                    NotificationManagerService.this.mMetricsLogger.write(notificationRecord.getLogMaker().setCategory(1590).setType(4));
                    NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_DIRECT_REPLIED, notificationRecord);
                    NotificationManagerService.this.reportUserInteraction(notificationRecord);
                    NotificationManagerService.this.mAssistants.notifyAssistantNotificationDirectReplyLocked(notificationRecord);
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.setNumSmartRepliesAdded(i);
                    notificationRecord.setNumSmartActionsAdded(i2);
                    notificationRecord.setSuggestionsGeneratedByAssistant(z);
                    notificationRecord.setEditChoicesBeforeSending(z2);
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    int i3 = 1;
                    LogMaker addTaggedData = notificationRecord.getLogMaker().setCategory(1383).setSubtype(i).addTaggedData(1600, Integer.valueOf(notificationRecord.getSuggestionsGeneratedByAssistant() ? 1 : 0)).addTaggedData(1629, Integer.valueOf(i2)).addTaggedData(1647, Integer.valueOf(notificationRecord.getEditChoicesBeforeSending() ? 1 : 0));
                    if (!z) {
                        i3 = 0;
                    }
                    NotificationManagerService.this.mMetricsLogger.write(addTaggedData.addTaggedData(1648, Integer.valueOf(i3)));
                    NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLIED, notificationRecord);
                    NotificationManagerService.this.reportUserInteraction(notificationRecord);
                    NotificationManagerService.this.mAssistants.notifyAssistantSuggestedReplySent(notificationRecord.getSbn(), notificationRecord.getNotificationType(), charSequence, notificationRecord.getSuggestionsGeneratedByAssistant());
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSettingsViewed(String str) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.recordViewedSettings();
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationBubbleChanged(String str, boolean z, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    if (!z) {
                        notificationRecord.getNotification().flags &= -4097;
                        notificationRecord.setFlagBubbleRemoved(true);
                    } else {
                        notificationRecord.getNotification().flags |= 8;
                        notificationRecord.setFlagBubbleRemoved(false);
                        if (notificationRecord.getNotification().getBubbleMetadata() != null) {
                            notificationRecord.getNotification().getBubbleMetadata().setFlags(i);
                        }
                        NotificationManagerService.this.mHandler.post(NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord.getUser().getIdentifier(), notificationRecord, true, NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                    }
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onBubbleMetadataFlagChanged(String str, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
                    if (bubbleMetadata == null) {
                        return;
                    }
                    if (i != bubbleMetadata.getFlags()) {
                        if (((bubbleMetadata.getFlags() ^ i) & 2) != 0) {
                            NotificationManagerService.this.clearEffectsLocked(str);
                        }
                        bubbleMetadata.setFlags(i);
                        notificationRecord.getNotification().flags |= 8;
                        NotificationManagerService.this.mHandler.post(NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord.getUser().getIdentifier(), notificationRecord, true, NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                    }
                    if (NotificationManagerService.this.mSmartAlertController != null) {
                        NotificationManagerService.this.mSmartAlertController.checkMissedEvent(NotificationManagerService.this.mNotificationList);
                    }
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                InlineReplyUriRecord inlineReplyUriRecord = (InlineReplyUriRecord) NotificationManagerService.this.mInlineReplyRecordsByKey.get(str);
                if (inlineReplyUriRecord == null) {
                    inlineReplyUriRecord = new InlineReplyUriRecord(NotificationManagerService.this.mUgmInternal.newUriPermissionOwner("INLINE_REPLY:" + str), userHandle, str2, str);
                    NotificationManagerService.this.mInlineReplyRecordsByKey.put(str, inlineReplyUriRecord);
                }
                IBinder permissionOwner = inlineReplyUriRecord.getPermissionOwner();
                int userId = inlineReplyUriRecord.getUserId();
                if (UserHandle.getUserId(i) != userId) {
                    try {
                        String[] packagesForUid = NotificationManagerService.this.mPackageManager.getPackagesForUid(i);
                        if (packagesForUid == null) {
                            Log.e("NotificationService", "Cannot grant uri permission to unknown UID: " + i);
                        }
                        i = NotificationManagerService.this.mPackageManager.getPackageUid(packagesForUid[0], 0L, userId);
                    } catch (RemoteException e) {
                        Log.e("NotificationService", "Cannot talk to package manager", e);
                    }
                }
                inlineReplyUriRecord.addUri(uri);
                NotificationManagerService.this.grantUriPermission(permissionOwner, uri, i, inlineReplyUriRecord.getPackageName(), userId);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void clearInlineReplyUriPermissions(String str, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                InlineReplyUriRecord inlineReplyUriRecord = (InlineReplyUriRecord) NotificationManagerService.this.mInlineReplyRecordsByKey.get(str);
                if (inlineReplyUriRecord != null) {
                    NotificationManagerService.this.destroyPermissionOwner(inlineReplyUriRecord.getPermissionOwner(), inlineReplyUriRecord.getUserId(), "INLINE_REPLY: " + inlineReplyUriRecord.getKey());
                    NotificationManagerService.this.mInlineReplyRecordsByKey.remove(str);
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationFeedbackReceived(String str, Bundle bundle) {
            NotificationManagerService.this.exitIdle();
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord == null) {
                    if (NotificationManagerService.DBG) {
                        Slog.w("NotificationService", "No notification with key: " + str);
                    }
                    return;
                }
                NotificationManagerService.this.mAssistants.notifyAssistantFeedbackReceived(notificationRecord, bundle);
            }
        }
    }

    public void logSmartSuggestionsVisible(NotificationRecord notificationRecord, int i) {
        if ((notificationRecord.getNumSmartRepliesAdded() > 0 || notificationRecord.getNumSmartActionsAdded() > 0) && !notificationRecord.hasSeenSmartReplies()) {
            notificationRecord.setSeenSmartReplies(true);
            this.mMetricsLogger.write(notificationRecord.getLogMaker().setCategory(1382).addTaggedData(1384, Integer.valueOf(notificationRecord.getNumSmartRepliesAdded())).addTaggedData(1599, Integer.valueOf(notificationRecord.getNumSmartActionsAdded())).addTaggedData(1600, Integer.valueOf(notificationRecord.getSuggestionsGeneratedByAssistant() ? 1 : 0)).addTaggedData(1629, Integer.valueOf(i)).addTaggedData(1647, Integer.valueOf(notificationRecord.getEditChoicesBeforeSending() ? 1 : 0)));
            this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLY_VISIBLE, notificationRecord);
        }
    }

    public void clearSoundLocked() {
        EasyMuteController easyMuteController;
        this.mSoundNotificationKey = null;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
            if (ringtonePlayer != null) {
                ringtonePlayer.stopAsync();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            easyMuteController = this.mEasyMuteController;
            if (easyMuteController == null) {
                return;
            }
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            easyMuteController = this.mEasyMuteController;
            if (easyMuteController == null) {
                return;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            EasyMuteController easyMuteController2 = this.mEasyMuteController;
            if (easyMuteController2 != null) {
                easyMuteController2.unregisterListener();
            }
            throw th;
        }
        easyMuteController.unregisterListener();
    }

    public void clearVibrateLocked() {
        this.mVibrateNotificationKey = null;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mVibratorHelper.cancelVibration();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearLightsLocked() {
        this.mLights.clear();
        updateLightsLocked();
    }

    public final void clearEffectsLocked(String str) {
        if (str.equals(this.mSoundNotificationKey)) {
            clearSoundLocked();
        }
        if (str.equals(this.mVibrateNotificationKey)) {
            clearVibrateLocked();
        }
        if (this.mLights.remove(str)) {
            updateLightsLocked();
        }
    }

    public final boolean checkNotificationAccessSetting(String str, int i) {
        return ActivityManager.checkComponentPermission("com.samsung.android.permission.SEM_AUTO_BIND_NOTIFICATION_LISTENER_SERVICE", getContext().getPackageManager().getPackageUidAsUser(str, i), -1, true) == 0;
    }

    public final void enableNotificationListener(ComponentName componentName, int i) {
        Log.d("NotificationService", " component:" + componentName + " user:" + i);
        try {
            getBinderService().setNotificationListenerAccessGrantedForUser(componentName, i, true, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$8 */
    public class C20138 extends BroadcastReceiver {
        public C20138() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.samsung.android.scpm.policy.UPDATE.NSF_CONVERSATION_APPS")) {
                Slog.d("NotificationService", "Receiving SCPM update intent - conversation");
                NotificationManagerService.this.getConversationAppPolicyScpmData();
            } else if (action.equals("com.samsung.android.scpm.policy.UPDATE.nsf-ongoing-dismiss-exception-keys")) {
                Slog.d("NotificationService", "Receiving SCPM update intent - ongoing dismiss exception");
                NotificationManagerService.this.getOngoingDismissExceptionPolicyScpmData();
            } else if (action.equals(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA)) {
                Slog.d("NotificationService", "Receiving SCPM clear data intent");
                NotificationManagerService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.C20138.this.lambda$onReceive$0();
                    }
                }, 60000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            NotificationManagerService.this.lambda$onBootPhase$2();
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri ALERTONCALL_MODE_URI;
        public final Uri DISABLE_HUN_FOR_CALLUI_URI;
        public final Uri FLOATING_NOTI_PACKAGE_LIST_URI;
        public final Uri LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS;
        public final Uri LOCK_SCREEN_SHOW_NOTIFICATIONS;
        public final Uri NOTIFICATION_BADGING_URI;
        public final Uri NOTIFICATION_BUBBLES_URI;
        public final Uri NOTIFICATION_HISTORY_ENABLED;
        public final Uri NOTIFICATION_LIGHT_PULSE_URI;
        public final Uri NOTIFICATION_RATE_LIMIT_URI;
        public final Uri NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI;
        public final Uri NOTIFICATION_VIBRATION_SEP_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.NOTIFICATION_BADGING_URI = Settings.Secure.getUriFor("notification_badging");
            this.NOTIFICATION_BUBBLES_URI = Settings.Secure.getUriFor("notification_bubbles");
            this.NOTIFICATION_LIGHT_PULSE_URI = Settings.System.getUriFor("led_indicator_missed_event");
            this.NOTIFICATION_RATE_LIMIT_URI = Settings.Global.getUriFor("max_notification_enqueue_rate");
            this.NOTIFICATION_HISTORY_ENABLED = Settings.Secure.getUriFor("notification_history_enabled");
            this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI = Settings.Global.getUriFor("qs_media_controls");
            this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
            this.LOCK_SCREEN_SHOW_NOTIFICATIONS = Settings.Secure.getUriFor("lock_screen_show_notifications");
            this.NOTIFICATION_VIBRATION_SEP_URI = Settings.System.getUriFor("notification_vibration_sep_index");
            this.DISABLE_HUN_FOR_CALLUI_URI = Settings.System.getUriFor("disable_hun_for_callui");
            this.ALERTONCALL_MODE_URI = Settings.System.getUriFor("alertoncall_mode");
            this.FLOATING_NOTI_PACKAGE_LIST_URI = Settings.Secure.getUriFor("floating_noti_package_list");
        }

        public void observe() {
            ContentResolver contentResolver = NotificationManagerService.this.getContext().getContentResolver();
            contentResolver.registerContentObserver(this.NOTIFICATION_BADGING_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_LIGHT_PULSE_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_RATE_LIMIT_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_BUBBLES_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_HISTORY_ENABLED, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI, false, this, -1);
            contentResolver.registerContentObserver(this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, false, this, -1);
            contentResolver.registerContentObserver(this.LOCK_SCREEN_SHOW_NOTIFICATIONS, false, this, -1);
            if (NmRune.NM_POLICY_VIB_PICKER_CONCEPT) {
                contentResolver.registerContentObserver(this.NOTIFICATION_VIBRATION_SEP_URI, false, this, -1);
            }
            contentResolver.registerContentObserver(this.DISABLE_HUN_FOR_CALLUI_URI, false, this, -1);
            contentResolver.registerContentObserver(this.ALERTONCALL_MODE_URI, false, this, -1);
            if (NotificationManagerService.this.mMultiStarEnable) {
                contentResolver.registerContentObserver(this.FLOATING_NOTI_PACKAGE_LIST_URI, false, this, -1);
            }
            update(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            update(uri);
        }

        public void update(Uri uri) {
            ContentResolver contentResolver = NotificationManagerService.this.getContext().getContentResolver();
            if (uri == null || this.NOTIFICATION_LIGHT_PULSE_URI.equals(uri)) {
                boolean z = Settings.System.getIntForUser(contentResolver, "led_indicator_missed_event", 0, -2) != 0;
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.mNotificationPulseEnabled != z) {
                    notificationManagerService.mNotificationPulseEnabled = z;
                    notificationManagerService.updateNotificationPulse();
                }
            }
            if (uri == null || this.NOTIFICATION_RATE_LIMIT_URI.equals(uri)) {
                NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                notificationManagerService2.mMaxPackageEnqueueRate = Settings.Global.getFloat(contentResolver, "max_notification_enqueue_rate", notificationManagerService2.mMaxPackageEnqueueRate);
            }
            if (uri == null || this.NOTIFICATION_BADGING_URI.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateBadgingEnabled();
            }
            if (uri == null || this.NOTIFICATION_BUBBLES_URI.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateBubblesEnabled();
                if (Settings.Secure.getIntForUser(contentResolver, "notification_bubbles", 0, -2) == 2) {
                    NotificationManagerService.this.mSmartPopupEnable = true;
                } else {
                    NotificationManagerService.this.mSmartPopupEnable = false;
                }
            }
            if (uri == null || this.NOTIFICATION_HISTORY_ENABLED.equals(uri)) {
                IntArray currentProfileIds = NotificationManagerService.this.mUserProfiles.getCurrentProfileIds();
                for (int i = 0; i < currentProfileIds.size(); i++) {
                    NotificationManagerService.this.mArchive.updateHistoryEnabled(currentProfileIds.get(i), Settings.Secure.getIntForUser(contentResolver, "notification_history_enabled", 1, currentProfileIds.get(i)) == 1);
                }
            }
            if (uri == null || this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateMediaNotificationFilteringEnabled();
            }
            if (uri == null || this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateLockScreenPrivateNotifications();
            }
            if (uri == null || this.LOCK_SCREEN_SHOW_NOTIFICATIONS.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateLockScreenShowNotifications();
            }
            if (NmRune.NM_POLICY_VIB_PICKER_CONCEPT && (uri == null || this.NOTIFICATION_VIBRATION_SEP_URI.equals(uri))) {
                try {
                    NotificationManagerService.this.mVibrationIndex = Settings.System.getIntForUser(contentResolver, "notification_vibration_sep_index", -2);
                } catch (Settings.SettingNotFoundException e) {
                    Slog.w("NotificationService", "Failed to find VibrationIndex Settings", e);
                }
            }
            if (uri == null || this.DISABLE_HUN_FOR_CALLUI_URI.equals(uri)) {
                NotificationManagerService.this.mIsDisableHunByCall = Settings.System.getIntForUser(contentResolver, "disable_hun_for_callui", 0, -2) == 1;
            }
            if (uri == null || this.ALERTONCALL_MODE_URI.equals(uri)) {
                NotificationManagerService.this.mIsEnableAlertByCall = Settings.System.getIntForUser(contentResolver, "alertoncall_mode", 1, -2) == 1;
            }
            if (NotificationManagerService.this.mMultiStarEnable) {
                if (uri == null || this.FLOATING_NOTI_PACKAGE_LIST_URI.equals(uri)) {
                    String stringForUser = Settings.Secure.getStringForUser(NotificationManagerService.this.getContext().getContentResolver(), "floating_noti_package_list", 0);
                    ArrayList arrayList = new ArrayList();
                    NotificationManagerService.this.mFloatingPackageList = null;
                    if (stringForUser != null) {
                        for (String str : stringForUser.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)) {
                            if (!"".equals(str)) {
                                arrayList.add(str);
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        NotificationManagerService.this.mFloatingPackageList = arrayList;
                    }
                }
            }
        }
    }

    public class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public boolean mIsInLockDownMode;
        public SparseBooleanArray mUserInLockDownMode;

        public final boolean containsFlag(int i, int i2) {
            return (i & i2) != 0;
        }

        public StrongAuthTracker(Context context) {
            super(context);
            this.mUserInLockDownMode = new SparseBooleanArray();
            this.mIsInLockDownMode = false;
        }

        public boolean isInLockDownMode(int i) {
            return this.mUserInLockDownMode.get(i, false);
        }

        public synchronized void onStrongAuthRequiredChanged(int i) {
            boolean containsFlag = containsFlag(getStrongAuthForUser(i), 32);
            if (containsFlag == isInLockDownMode(i)) {
                return;
            }
            if (containsFlag) {
                NotificationManagerService.this.cancelNotificationsWhenEnterLockDownMode(i);
            }
            this.mUserInLockDownMode.put(i, containsFlag);
            if (!containsFlag) {
                NotificationManagerService.this.postNotificationsWhenExitLockDownMode(i);
            }
        }
    }

    public NotificationManagerService(Context context) {
        this(context, new NotificationRecordLoggerImpl(), new InstanceIdSequence(IInstalld.FLAG_FORCE));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public NotificationManagerService(Context context, NotificationRecordLogger notificationRecordLogger, InstanceIdSequence instanceIdSequence) {
        super(context);
        this.mIsMutedByWearableApps = 0;
        this.mForegroundToken = new Binder();
        this.mRankingThread = new HandlerThread("ranker", 10);
        this.mHasLight = true;
        this.mListenersDisablingEffects = new SparseArray();
        this.mEffectsSuppressors = new ArrayList();
        this.mInterruptionFilter = 0;
        this.mScreenOn = true;
        this.mInCallStateOffHook = false;
        this.mCallNotificationToken = null;
        this.mNotificationLock = new Object();
        this.mNotificationList = new ArrayList();
        this.mNotificationsByKey = new ArrayMap();
        this.mInlineReplyRecordsByKey = new ArrayMap();
        this.mEnqueuedNotifications = new ArrayList();
        this.mAutobundledSummaries = new ArrayMap();
        this.mToastQueue = new ArrayList();
        this.mToastRateLimitingDisabledUids = new ArraySet();
        this.mSummaryByGroupKey = new ArrayMap();
        this.mIsCurrentToastShown = false;
        this.mLights = new ArrayList();
        this.mUserProfiles = new ManagedServices.UserProfiles();
        this.mLockScreenAllowSecureNotifications = true;
        this.mSystemExemptFromDismissal = false;
        this.mMaxPackageEnqueueRate = 5.0f;
        this.mSavePolicyFile = new SavePolicyFileRunnable();
        this.mMsgPkgsAllowedAsConvos = new HashSet();
        this.mDelayedWakelockList = new ConcurrentList(new ArrayList());
        this.mDelayedWakeUpList = new ConcurrentList(new ArrayList());
        this.mEdgeAgainedWakelockTag = "EDGELIGHTING:";
        this.DELAYED_WAKELOCK_DURATION = 500;
        this.mIsFactoryBinary = FactoryTest.isFactoryBinary();
        this.mSaveConversationPackagePolicyFile = new SaveConversationPackagePolicyFileRunnable();
        this.mConversationAppList = new ArrayList();
        this.mConversationHistoryAppList = new ArrayList();
        this.mConversationAppPolicyVersion = 0L;
        this.mIsMaxNotiLimitEnabled = true;
        this.mMaxNotiLimitCount = 300;
        this.mSaveOngoingDismissExceptionPolicyFile = new SaveOngoingDismissExceptionPolicyFileRunnable();
        this.mOngoingDismissExceptionKeyList = new ArrayList();
        this.mOngoingDismissExceptionPolicyVersion = 0L;
        this.mLimitNotificationsForOverflowAppList = new ArrayList();
        this.mOverflowNotiUpdateTimeMap = new ConcurrentHashMap();
        this.mIsOverflowAppListLoaded = false;
        this.mCountForOverflowAppList = 0;
        this.mApplicationPolicyService = null;
        this.mVibrationIndex = 50034;
        this.mIsEnableAlertByCall = false;
        this.mIsDisableHunByCall = false;
        this.mFloatingPackageList = new ArrayList();
        this.mMultiStarEnable = false;
        this.mSmartPopupEnable = false;
        this.mIsInterruptivePostNotif = false;
        this.mNeedDeletePrevHistory = false;
        this.mFoldState = false;
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.server.notification.NotificationManagerService.1
            public void onTableModeChanged(boolean z) {
            }

            public void onFoldStateChanged(boolean z) {
                Slog.d("NotificationService", "mFoldState = " + NotificationManagerService.this.mFoldState + ", isFolded = " + z);
                NotificationManagerService.this.mFoldState = z;
            }
        };
        this.mNotificationDelegate = new C19982();
        this.mLocaleChangeReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    SystemNotificationChannels.createAll(context2);
                    NotificationManagerService.this.mZenModeHelper.updateDefaultZenRules(Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
                    NotificationManagerService.this.mPreferencesHelper.onLocaleChanged(context2, ActivityManager.getCurrentUser());
                }
            }
        };
        this.mRestoreReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.os.action.SETTING_RESTORED".equals(intent.getAction())) {
                    try {
                        String stringExtra = intent.getStringExtra("setting_name");
                        String stringExtra2 = intent.getStringExtra("new_value");
                        int intExtra = intent.getIntExtra("restored_from_sdk_int", 0);
                        NotificationManagerService.this.mListeners.onSettingRestored(stringExtra, stringExtra2, intExtra, getSendingUserId());
                        NotificationManagerService.this.mConditionProviders.onSettingRestored(stringExtra, stringExtra2, intExtra, getSendingUserId());
                    } catch (Exception e) {
                        Slog.wtf("NotificationService", "Cannot restore managed services from settings", e);
                    }
                }
            }
        };
        this.mNotificationTimeoutReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                NotificationRecord findNotificationByKeyLocked;
                String action = intent.getAction();
                if (action != null && NotificationManagerService.ACTION_NOTIFICATION_TIMEOUT.equals(action)) {
                    synchronized (NotificationManagerService.this.mNotificationLock) {
                        findNotificationByKeyLocked = NotificationManagerService.this.findNotificationByKeyLocked(intent.getStringExtra("key"));
                    }
                    if (findNotificationByKeyLocked != null) {
                        NotificationManagerService.this.cancelNotification(findNotificationByKeyLocked.getSbn().getUid(), findNotificationByKeyLocked.getSbn().getInitialPid(), findNotificationByKeyLocked.getSbn().getPackageName(), findNotificationByKeyLocked.getSbn().getTag(), findNotificationByKeyLocked.getSbn().getId(), 0, 32832, true, findNotificationByKeyLocked.getUserId(), 19, null);
                    }
                }
            }
        };
        this.mPackageIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.6
            /* JADX WARN: Removed duplicated region for block: B:106:0x00b6  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x00a7  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x015e  */
            /* JADX WARN: Removed duplicated region for block: B:49:0x0200  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x0213  */
            /* JADX WARN: Removed duplicated region for block: B:59:0x021e  */
            /* JADX WARN: Removed duplicated region for block: B:61:0x0224 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:92:0x0220  */
            /* JADX WARN: Removed duplicated region for block: B:93:0x0218  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onReceive(Context context2, Intent intent) {
                boolean z;
                boolean z2;
                boolean z3;
                Object obj;
                boolean z4;
                String schemeSpecificPart;
                int applicationEnabledSetting;
                boolean z5;
                int[] iArr;
                String[] strArr;
                String[] stringArrayExtra;
                int[] iArr2;
                String[] stringArrayExtra2;
                int[] intArrayExtra;
                boolean z6;
                boolean z7;
                Object obj2;
                int[] iArr3;
                int i;
                int i2;
                String str;
                boolean z8;
                Object obj3;
                String schemeSpecificPart2;
                int i3;
                Bundle bundle;
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                int i4 = 0;
                if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                    z = false;
                    z2 = false;
                } else {
                    z = action.equals("android.intent.action.PACKAGE_REMOVED");
                    if (!z && !action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        z2 = action.equals("android.intent.action.PACKAGE_CHANGED");
                        if (z2) {
                            z3 = false;
                        } else {
                            boolean equals = action.equals("android.intent.action.QUERY_PACKAGE_RESTART");
                            if (!equals && !action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE") && !action.equals("android.intent.action.PACKAGES_SUSPENDED") && !action.equals("android.intent.action.PACKAGES_UNSUSPENDED") && !action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                                return;
                            } else {
                                z3 = equals;
                            }
                        }
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (z || intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            obj = "android.intent.action.PACKAGE_ADDED";
                            z4 = false;
                        } else {
                            obj = "android.intent.action.PACKAGE_ADDED";
                            z4 = true;
                        }
                        Object obj4 = "android.intent.action.PACKAGE_CHANGED";
                        String str2 = "NotificationService";
                        if (NotificationManagerService.DBG) {
                            Slog.i("NotificationService", "action=" + action + " removing=" + z4);
                        }
                        if (!action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                            stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                            iArr2 = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                        } else {
                            if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                                strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                                iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                                z7 = true;
                                z5 = false;
                                z6 = false;
                            } else if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                                strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                                iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                                z6 = true;
                                z5 = false;
                                z7 = false;
                            } else if (action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                                if ((intent.getIntExtra("android.intent.extra.distraction_restrictions", 0) & 2) != 0) {
                                    stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                                    intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                                    z7 = true;
                                    z6 = false;
                                } else {
                                    stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                                    intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                                    z6 = true;
                                    z7 = false;
                                }
                                strArr = stringArrayExtra2;
                                iArr = intArrayExtra;
                                z5 = false;
                            } else if (z3) {
                                stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
                                iArr2 = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                            } else {
                                Uri data = intent.getData();
                                if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                                    return;
                                }
                                if (z2) {
                                    try {
                                        applicationEnabledSetting = NotificationManagerService.this.mPackageManager.getApplicationEnabledSetting(schemeSpecificPart, intExtra != -1 ? intExtra : 0);
                                    } catch (RemoteException unused) {
                                    } catch (IllegalArgumentException e) {
                                        if (NotificationManagerService.DBG) {
                                            Slog.i("NotificationService", "Exception trying to look up app enabled setting", e);
                                        }
                                    }
                                    if (applicationEnabledSetting == 1 || applicationEnabledSetting == 0) {
                                        z5 = false;
                                        iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                                        strArr = new String[]{schemeSpecificPart};
                                        z7 = false;
                                        z6 = false;
                                    }
                                }
                                z5 = true;
                                iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                                strArr = new String[]{schemeSpecificPart};
                                z7 = false;
                                z6 = false;
                            }
                            if (strArr != null || strArr.length <= 0) {
                                obj2 = obj;
                                iArr3 = iArr;
                                i = intExtra;
                                i2 = 0;
                                str = "NotificationService";
                                z8 = z4;
                                obj3 = obj4;
                            } else if (z5) {
                                int length = strArr.length;
                                int i5 = 0;
                                while (i5 < length) {
                                    int i6 = intExtra;
                                    NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, strArr[i5], null, 0, 0, !z3, i6, 5, null);
                                    i5++;
                                    z4 = z4;
                                    intExtra = i6;
                                    i4 = i4;
                                    iArr = iArr;
                                    obj4 = obj4;
                                    str2 = str2;
                                }
                                obj2 = obj;
                                i = intExtra;
                                i2 = i4;
                                str = str2;
                                z8 = z4;
                                obj3 = obj4;
                                iArr3 = iArr;
                            } else {
                                obj2 = obj;
                                i = intExtra;
                                i2 = 0;
                                str = "NotificationService";
                                z8 = z4;
                                obj3 = obj4;
                                iArr3 = iArr;
                                if (z7 && iArr3 != null && iArr3.length > 0) {
                                    NotificationManagerService.this.hideNotificationsForPackages(strArr, iArr3);
                                } else if (z6 && iArr3 != null && iArr3.length > 0) {
                                    NotificationManagerService.this.unhideNotificationsForPackages(strArr, iArr3);
                                }
                            }
                            if (!action.equals(obj2) || action.equals(obj3)) {
                                Uri data2 = intent.getData();
                                schemeSpecificPart2 = data2 != null ? data2.getSchemeSpecificPart() : null;
                                i3 = i;
                                int i7 = i3 != -1 ? i3 : i2;
                                if (schemeSpecificPart2 != null) {
                                    try {
                                        ApplicationInfo applicationInfo = NotificationManagerService.this.mPackageManager.getApplicationInfo(schemeSpecificPart2, 128L, i7);
                                        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && "user".equals(bundle.getString("com.samsung.android.notification.listener.autobind", "default"))) {
                                            Slog.i(str, "Notification listener autobind, pkg = " + schemeSpecificPart2);
                                            if (NotificationManagerService.this.checkNotificationAccessSetting(schemeSpecificPart2, i7)) {
                                                Iterator it = NotificationManagerService.this.mListeners.queryPackageForServices(schemeSpecificPart2, 786432, i7).iterator();
                                                while (it.hasNext()) {
                                                    try {
                                                        try {
                                                            NotificationManagerService.this.getBinderService().setNotificationListenerAccessGrantedForUser((ComponentName) it.next(), i7, true, true);
                                                        } catch (RemoteException e2) {
                                                            e = e2;
                                                            e.printStackTrace();
                                                        }
                                                    } catch (RemoteException e3) {
                                                        e = e3;
                                                    }
                                                }
                                            }
                                        }
                                    } catch (RemoteException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            } else {
                                i3 = i;
                            }
                            NotificationManagerService.this.mHandler.scheduleOnPackageChanged(z8, i3, strArr, iArr3);
                        }
                        strArr = stringArrayExtra;
                        iArr = iArr2;
                        z5 = true;
                        z7 = false;
                        z6 = false;
                        if (strArr != null) {
                        }
                        obj2 = obj;
                        iArr3 = iArr;
                        i = intExtra;
                        i2 = 0;
                        str = "NotificationService";
                        z8 = z4;
                        obj3 = obj4;
                        if (action.equals(obj2)) {
                        }
                        Uri data22 = intent.getData();
                        if (data22 != null) {
                        }
                        i3 = i;
                        if (i3 != -1) {
                        }
                        if (schemeSpecificPart2 != null) {
                        }
                        NotificationManagerService.this.mHandler.scheduleOnPackageChanged(z8, i3, strArr, iArr3);
                    }
                    z2 = false;
                }
                z3 = z2;
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (z) {
                }
                obj = "android.intent.action.PACKAGE_ADDED";
                z4 = false;
                Object obj42 = "android.intent.action.PACKAGE_CHANGED";
                String str22 = "NotificationService";
                if (NotificationManagerService.DBG) {
                }
                if (!action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                }
                strArr = stringArrayExtra;
                iArr = iArr2;
                z5 = true;
                z7 = false;
                z6 = false;
                if (strArr != null) {
                }
                obj2 = obj;
                iArr3 = iArr;
                i = intExtra2;
                i2 = 0;
                str = "NotificationService";
                z8 = z4;
                obj3 = obj42;
                if (action.equals(obj2)) {
                }
                Uri data222 = intent.getData();
                if (data222 != null) {
                }
                i3 = i;
                if (i3 != -1) {
                }
                if (schemeSpecificPart2 != null) {
                }
                NotificationManagerService.this.mHandler.scheduleOnPackageChanged(z8, i3, strArr, iArr3);
            }
        };
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    notificationManagerService.mScreenOn = true;
                    notificationManagerService.updateNotificationPulse();
                    NotificationManagerService.this.clearDelayedWakelock();
                    return;
                }
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    notificationManagerService2.mScreenOn = false;
                    notificationManagerService2.updateNotificationPulse();
                    return;
                }
                if (action.equals("android.intent.action.PHONE_STATE")) {
                    NotificationManagerService.this.mInCallStateOffHook = TelephonyManager.EXTRA_STATE_OFFHOOK.equals(intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN));
                    NotificationManagerService.this.updateNotificationPulse();
                    return;
                }
                if (action.equals("android.intent.action.USER_STOPPED")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra >= 0) {
                        NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, null, null, 0, 0, true, intExtra, 6, null);
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra2 < 0 || NotificationManagerService.this.mDpm.isKeepProfilesRunningEnabled()) {
                        return;
                    }
                    NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, null, null, 0, 0, true, intExtra2, 15, null);
                    NotificationManagerService.this.mSnoozeHelper.clearData(intExtra2);
                    return;
                }
                if (action.equals("android.intent.action.USER_PRESENT")) {
                    if (NotificationManagerService.this.mNotificationLight != null) {
                        NotificationManagerService.this.mNotificationLight.turnOff();
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.USER_SWITCHED")) {
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    if (!NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra3)) {
                        NotificationManagerService.this.mSettingsObserver.update(null);
                        NotificationManagerService.this.mConditionProviders.onUserSwitched(intExtra3);
                        NotificationManagerService.this.mListeners.onUserSwitched(intExtra3);
                        NotificationManagerService.this.mZenModeHelper.onUserSwitched(intExtra3);
                    }
                    NotificationManagerService.this.mAssistants.onUserSwitched(intExtra3);
                    return;
                }
                if (action.equals("android.intent.action.USER_ADDED")) {
                    int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra4 != -10000) {
                        NotificationManagerService.this.mUserProfiles.updateCache(context2);
                        if (!NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra4)) {
                            NotificationManagerService.this.allowDefaultApprovedServices(intExtra4);
                        }
                        NotificationManagerService.this.mHistoryManager.onUserAdded(intExtra4);
                        NotificationManagerService.this.mArchive.updateHistoryEnabled(intExtra4, true);
                    }
                    NotificationManagerService.this.mEdgeLightingManager.updateCurrentProfilesCache();
                    return;
                }
                if (action.equals("android.intent.action.USER_REMOVED")) {
                    int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    NotificationManagerService.this.mZenModeHelper.onUserRemoved(intExtra5);
                    NotificationManagerService.this.mPreferencesHelper.onUserRemoved(intExtra5);
                    NotificationManagerService.this.mListeners.onUserRemoved(intExtra5);
                    NotificationManagerService.this.mConditionProviders.onUserRemoved(intExtra5);
                    NotificationManagerService.this.mAssistants.onUserRemoved(intExtra5);
                    NotificationManagerService.this.mHistoryManager.onUserRemoved(intExtra5);
                    NotificationManagerService.this.handleSavePolicyFile();
                    NotificationManagerService.this.mEdgeLightingManager.updateCurrentProfilesCache();
                    return;
                }
                if (action.equals("android.intent.action.USER_UNLOCKED")) {
                    int intExtra6 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    NotificationManagerService.this.mAssistants.onUserUnlocked(intExtra6);
                    if (NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra6)) {
                        return;
                    }
                    NotificationManagerService.this.mConditionProviders.onUserUnlocked(intExtra6);
                    NotificationManagerService.this.mListeners.onUserUnlocked(intExtra6);
                    NotificationManagerService.this.mZenModeHelper.onUserUnlocked(intExtra6);
                    return;
                }
                if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                    if (NotificationManagerService.this.mSemGoodCatchManager == null) {
                        NotificationManagerService.this.mSemGoodCatchManager = new SemGoodCatchManager(NotificationManagerService.this.getContext(), "AccessibilityManagerService", new String[]{"toast"}, NotificationManagerService.this.mGoodCatchStateListener);
                        Log.d("NotificationService", "SemGoodCatchManager is created");
                    }
                    if (NotificationManagerService.this.mNotiSemGoodCatchManager == null) {
                        NotificationManagerService.this.mNotiSemGoodCatchManager = new SemGoodCatchManager(NotificationManagerService.this.getContext(), "NotificationManagerService", new String[]{"noti_blocked", "noti_muted"}, NotificationManagerService.this.mNotiGoodCatchStateListener);
                        Log.d("NotificationService", "mNotiSemGoodCatchManager is created");
                    }
                    if (NotificationManagerService.mSemAudioGoodCatchManager == null) {
                        NotificationManagerService.mSemAudioGoodCatchManager = new SemGoodCatchManager(NotificationManagerService.this.getContext(), "NotificationManagerService", new String[]{"playback"}, NotificationManagerService.this.mAudioGoodCatchStateListener);
                        Log.d("NotificationService", "SemAudioGoodCatchManager is created");
                        return;
                    }
                    return;
                }
                if (!"samsung.intent.action.PHONE_STATE".equals(action) || TelephonyManager.EXTRA_STATE_IDLE == intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) {
                    return;
                }
                NotificationManagerService.this.mCMCinCallState = true;
            }
        };
        this.mScpmIntentReceiver = new C20138();
        this.mSecIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("com.samsung.notification.REQUEST_REBIND_LISTENER")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -2);
                    NotificationManagerService.this.enableNotificationListener(new ComponentName(intent.getStringExtra("packageName"), intent.getStringExtra("className")), intExtra);
                }
            }
        };
        this.mService = new INotificationManagerStubC199315();
        this.mNotiPermissionHistoryList = new ArrayList();
        this.mInternalService = new C199416();
        this.mShortcutListener = new ShortcutHelper.ShortcutListener() { // from class: com.android.server.notification.NotificationManagerService.20
            @Override // com.android.server.notification.ShortcutHelper.ShortcutListener
            public void onShortcutRemoved(String str) {
                String packageName;
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    packageName = notificationRecord != null ? notificationRecord.getSbn().getPackageName() : null;
                }
                boolean z = packageName != null && NotificationManagerService.this.mActivityManager.getPackageImportance(packageName) == 100;
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord2 != null) {
                        notificationRecord2.setShortcutInfo(null);
                        notificationRecord2.getNotification().flags |= 8;
                        NotificationManagerService.this.mHandler.post(NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord2.getUser().getIdentifier(), notificationRecord2, z, NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                    }
                }
            }
        };
        this.mTimeoutPendingIntent = new ArrayMap();
        this.mHighDataSizeNotificaitonList = new ArrayList();
        this.dayTime = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ");
        this.mEnqueueLogs = new ArrayMap();
        this.mCancelLogs = new ArrayList();
        this.mAllowedPackage = new HashSet();
        this.mIsRuneStoneSupported = false;
        this.mIsRuneStoneEnabled = false;
        this.mStateContentObserver = new ContentObserver(0 == true ? 1 : 0) { // from class: com.android.server.notification.NotificationManagerService.25
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                if (uri == null) {
                    return;
                }
                NotificationManagerService.this.mIsRuneStoneEnabled = "true".equalsIgnoreCase(uri.getQueryParameter("enabled"));
                Slog.d("NotificationService", "RuneStone State change mIsRuneStoneEnabled = " + NotificationManagerService.this.mIsRuneStoneEnabled);
            }
        };
        this.mGoodCatchViToastOn = false;
        this.mGoodCatchStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.android.server.notification.NotificationManagerService.26
            public void onStart(String str) {
                Log.d("NotificationService", "onStart(), " + str);
                NotificationManagerService.this.mGoodCatchViToastOn = true;
            }

            public void onStop(String str) {
                Log.d("NotificationService", "onStop()," + str);
                NotificationManagerService.this.mGoodCatchViToastOn = false;
            }
        };
        this.mGoodCatchNotiBlockedOn = false;
        this.mGoodCatchNotiMutedOn = false;
        this.mNotiGoodCatchStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.android.server.notification.NotificationManagerService.27
            public void onStart(String str) {
                Log.d("NotificationService", "onStart(), " + str);
                if ("noti_blocked".equals(str)) {
                    NotificationManagerService.this.mGoodCatchNotiBlockedOn = true;
                } else if ("noti_muted".equals(str)) {
                    NotificationManagerService.this.mGoodCatchNotiMutedOn = true;
                }
            }

            public void onStop(String str) {
                Log.d("NotificationService", "onStop()," + str);
                if ("noti_blocked".equals(str)) {
                    NotificationManagerService.this.mGoodCatchNotiBlockedOn = false;
                } else if ("noti_muted".equals(str)) {
                    NotificationManagerService.this.mGoodCatchNotiMutedOn = false;
                }
            }
        };
        this.mGoodCatchSoundPlayedOn = false;
        this.mAudioGoodCatchStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.android.server.notification.NotificationManagerService.28
            public void onStart(String str) {
                Log.d("NotificationService", "onStart(), " + str);
                NotificationManagerService.this.mGoodCatchSoundPlayedOn = true;
            }

            public void onStop(String str) {
                Log.d("NotificationService", "onStop()," + str);
                NotificationManagerService.this.mGoodCatchSoundPlayedOn = false;
            }
        };
        this.mNotificationRecordLogger = notificationRecordLogger;
        this.mNotificationInstanceIdSequence = instanceIdSequence;
        Notification.processAllowlistToken = ALLOWLIST_TOKEN;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.mAudioManager = audioManager;
    }

    public void setStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        this.mStrongAuthTracker = strongAuthTracker;
    }

    public void setKeyguardManager(KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    public ShortcutHelper getShortcutHelper() {
        return this.mShortcutHelper;
    }

    public void setShortcutHelper(ShortcutHelper shortcutHelper) {
        this.mShortcutHelper = shortcutHelper;
    }

    public VibratorHelper getVibratorHelper() {
        return this.mVibratorHelper;
    }

    public void setVibratorHelper(VibratorHelper vibratorHelper) {
        this.mVibratorHelper = vibratorHelper;
    }

    public void setHints(int i) {
        this.mListenerHints = i;
    }

    public void setLights(LogicalLight logicalLight) {
        this.mNotificationLight = logicalLight;
        this.mAttentionLight = logicalLight;
        this.mNotificationPulseEnabled = true;
    }

    public void setScreenOn(boolean z) {
        this.mScreenOn = z;
    }

    public int getNotificationRecordCount() {
        int size;
        synchronized (this.mNotificationLock) {
            size = this.mNotificationList.size() + this.mNotificationsByKey.size() + this.mSummaryByGroupKey.size() + this.mEnqueuedNotifications.size();
            Iterator it = this.mNotificationList.iterator();
            while (it.hasNext()) {
                NotificationRecord notificationRecord = (NotificationRecord) it.next();
                if (this.mNotificationsByKey.containsKey(notificationRecord.getKey())) {
                    size--;
                }
                if (notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().isGroupSummary()) {
                    size--;
                }
            }
        }
        return size;
    }

    public void clearNotifications() {
        synchronized (this.mNotificationList) {
            this.mEnqueuedNotifications.clear();
            this.mNotificationList.clear();
            this.mNotificationsByKey.clear();
            this.mSummaryByGroupKey.clear();
        }
    }

    public void addNotification(NotificationRecord notificationRecord) {
        this.mNotificationList.add(notificationRecord);
        this.mNotificationsByKey.put(notificationRecord.getSbn().getKey(), notificationRecord);
        if (notificationRecord.getSbn().isGroup()) {
            this.mSummaryByGroupKey.put(notificationRecord.getGroupKey(), notificationRecord);
        }
    }

    public void addEnqueuedNotification(NotificationRecord notificationRecord) {
        this.mEnqueuedNotifications.add(notificationRecord);
    }

    public NotificationRecord getNotificationRecord(String str) {
        return (NotificationRecord) this.mNotificationsByKey.get(str);
    }

    public void setSystemReady(boolean z) {
        this.mSystemReady = z;
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mHandler = workerHandler;
    }

    public void setRankingHelper(RankingHelper rankingHelper) {
        this.mRankingHelper = rankingHelper;
    }

    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    public void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }

    public void setIsAutomotive(boolean z) {
        this.mIsAutomotive = z;
    }

    public void setNotificationEffectsEnabledForAutomotive(boolean z) {
        this.mNotificationEffectsEnabledForAutomotive = z;
    }

    public void setIsTelevision(boolean z) {
        this.mIsTelevision = z;
    }

    public void setUsageStats(NotificationUsageStats notificationUsageStats) {
        this.mUsageStats = notificationUsageStats;
    }

    public void setAccessibilityManager(AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    public void setTelecomManager(TelecomManager telecomManager) {
        this.mTelecomManager = telecomManager;
    }

    public final void notifyZenPolicy() {
        if (this.mZenModeHelper == null) {
            Slog.e("NotificationService", "notifyZenPolicy : mZenModeHelper is null");
        } else {
            this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.10
                @Override // java.lang.Runnable
                public void run() {
                    int i = (NotificationManagerService.this.mZenModeHelper.getZenMode() == 0 || NotificationManagerService.this.mZenModeHelper.getNotificationPolicy() == null) ? 0 : NotificationManagerService.this.mZenModeHelper.getNotificationPolicy().suppressedVisualEffects;
                    Slog.d("NotificationService", "notifyZenPolicy : suppressed = " + i);
                    NotificationManagerService.this.mEdgeLightingManager.setSuppressed(i);
                }
            });
        }
    }

    public void init(WorkerHandler workerHandler, RankingHandler rankingHandler, IPackageManager iPackageManager, PackageManager packageManager, LightsManager lightsManager, NotificationListeners notificationListeners, NotificationAssistants notificationAssistants, ConditionProviders conditionProviders, ICompanionDeviceManager iCompanionDeviceManager, SnoozeHelper snoozeHelper, NotificationUsageStats notificationUsageStats, AtomicFile atomicFile, ActivityManager activityManager, GroupHelper groupHelper, IActivityManager iActivityManager, ActivityTaskManagerInternal activityTaskManagerInternal, UsageStatsManagerInternal usageStatsManagerInternal, DevicePolicyManagerInternal devicePolicyManagerInternal, IUriGrantsManager iUriGrantsManager, UriGrantsManagerInternal uriGrantsManagerInternal, AppOpsManager appOpsManager, UserManager userManager, NotificationHistoryManager notificationHistoryManager, StatsManager statsManager, TelephonyManager telephonyManager, ActivityManagerInternal activityManagerInternal, MultiRateLimiter multiRateLimiter, PermissionHelper permissionHelper, UsageStatsManagerInternal usageStatsManagerInternal2, TelecomManager telecomManager, NotificationChannelLogger notificationChannelLogger, SystemUiSystemPropertiesFlags.FlagResolver flagResolver, PermissionManager permissionManager, PowerManager powerManager, PostNotificationTrackerFactory postNotificationTrackerFactory, EdgeLightingManager edgeLightingManager, AtomicFile atomicFile2, AtomicFile atomicFile3) {
        String[] strArr;
        this.mHandler = workerHandler;
        Resources resources = getContext().getResources();
        this.mMaxPackageEnqueueRate = Settings.Global.getFloat(getContext().getContentResolver(), "max_notification_enqueue_rate", 5.0f);
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.mAm = iActivityManager;
        this.mAtm = activityTaskManagerInternal;
        activityTaskManagerInternal.setBackgroundActivityStartCallback(new NotificationTrampolineCallback());
        this.mUgm = iUriGrantsManager;
        this.mUgmInternal = uriGrantsManagerInternal;
        this.mPackageManager = iPackageManager;
        this.mPackageManagerClient = packageManager;
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mPermissionManager = permissionManager;
        this.mPermissionPolicyInternal = (PermissionPolicyInternal) LocalServices.getService(PermissionPolicyInternal.class);
        this.mUmInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mUsageStatsManagerInternal = usageStatsManagerInternal2;
        this.mAppOps = appOpsManager;
        this.mAppUsageStats = usageStatsManagerInternal;
        this.mAlarmManager = (AlarmManager) getContext().getSystemService("alarm");
        this.mCompanionManager = iCompanionDeviceManager;
        this.mActivityManager = activityManager;
        this.mAmi = activityManagerInternal;
        this.mDeviceIdleManager = (DeviceIdleManager) getContext().getSystemService(DeviceIdleManager.class);
        this.mDpm = devicePolicyManagerInternal;
        this.mUm = userManager;
        this.mTelecomManager = telecomManager;
        this.mPowerManager = powerManager;
        this.mPostNotificationTrackerFactory = postNotificationTrackerFactory;
        this.mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        this.mStrongAuthTracker = new StrongAuthTracker(getContext());
        try {
            strArr = resources.getStringArray(17236261);
        } catch (Resources.NotFoundException unused) {
            strArr = new String[0];
        }
        this.mUsageStats = notificationUsageStats;
        this.mMetricsLogger = new MetricsLogger();
        this.mRankingHandler = rankingHandler;
        this.mConditionProviders = conditionProviders;
        ZenModeHelper zenModeHelper = new ZenModeHelper(getContext(), this.mHandler.getLooper(), this.mConditionProviders, new SysUiStatsEvent$BuilderFactory(), flagResolver, new ZenModeEventLogger(this.mPackageManagerClient));
        this.mZenModeHelper = zenModeHelper;
        zenModeHelper.addCallback(new C198911());
        this.mPermissionHelper = permissionHelper;
        this.mNotificationChannelLogger = notificationChannelLogger;
        this.mPreferencesHelper = new PreferencesHelper(getContext(), this.mPackageManagerClient, this.mRankingHandler, this.mZenModeHelper, this.mPermissionHelper, this.mNotificationChannelLogger, this.mAppOps, new SysUiStatsEvent$BuilderFactory(), this.mShowReviewPermissionsNotification);
        this.mRankingHelper = new RankingHelper(getContext(), this.mRankingHandler, this.mPreferencesHelper, this.mZenModeHelper, this.mUsageStats, strArr);
        this.mSnoozeHelper = snoozeHelper;
        this.mGroupHelper = groupHelper;
        this.mVibratorHelper = new VibratorHelper(getContext());
        this.mHistoryManager = notificationHistoryManager;
        this.mListeners = notificationListeners;
        this.mAssistants = notificationAssistants;
        this.mAllowedManagedServicePackages = new TriPredicate() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda1
            public final boolean test(Object obj, Object obj2, Object obj3) {
                return NotificationManagerService.this.canUseManagedServices((String) obj, (Integer) obj2, (String) obj3);
            }
        };
        this.mPolicyFile = atomicFile;
        loadPolicyFile();
        StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) getLocalService(StatusBarManagerInternal.class);
        this.mStatusBar = statusBarManagerInternal;
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.setNotificationDelegate(this.mNotificationDelegate);
        }
        this.mConversationAppPolicyFile = atomicFile2;
        loadConversationPackagePolicyFile();
        this.mOngoingDismissExceptionPolicyFile = atomicFile3;
        loadOngoingDismissExceptionPolicyFile();
        loadDefaultLimitNotificationsForOverflowAppList();
        this.mNotificationLight = lightsManager.getLight(4);
        this.mAttentionLight = lightsManager.getLight(5);
        this.mInCallNotificationUri = Uri.parse("file://" + resources.getString(R.string.dump_heap_system_text));
        this.mInCallNotificationAudioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        this.mInCallNotificationVolume = resources.getFloat(R.dimen.config_defaultBinderHeavyHitterWatcherThreshold);
        this.mUseAttentionLight = resources.getBoolean(R.bool.config_supportDoubleTapWake);
        boolean z = true;
        this.mHasLight = new File("/sys/class/sec/led/led_pattern").isFile();
        if (Settings.Global.getInt(getContext().getContentResolver(), "device_provisioned", 0) == 0) {
            this.mDisableNotificationEffects = true;
        }
        this.mZenModeHelper.initZenMode();
        this.mInterruptionFilter = this.mZenModeHelper.getZenModeListenerInterruptionFilter();
        this.mUserProfiles.updateCache(getContext());
        if (this.mPackageManagerClient.hasSystemFeature("android.hardware.telephony")) {
            telephonyManager.listen(new PhoneStateListener() { // from class: com.android.server.notification.NotificationManagerService.12
                @Override // android.telephony.PhoneStateListener
                public void onCallStateChanged(int i, String str) {
                    if (NotificationManagerService.this.mCallState == i) {
                        return;
                    }
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "Call state changed: " + NotificationManagerService.callStateToString(i));
                    }
                    NotificationManagerService.this.mCallState = i;
                }
            }, 32);
        }
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        this.mArchive = new Archive(resources.getInteger(R.integer.config_oem_enabled_satellite_location_fresh_duration));
        if (!this.mPackageManagerClient.hasSystemFeature("android.software.leanback") && !this.mPackageManagerClient.hasSystemFeature("android.hardware.type.television")) {
            z = false;
        }
        this.mIsTelevision = z;
        this.mIsAutomotive = this.mPackageManagerClient.hasSystemFeature("android.hardware.type.automotive", 0);
        this.mNotificationEffectsEnabledForAutomotive = resources.getBoolean(R.bool.config_enableBurnInProtection);
        this.mPreferencesHelper.lockChannelsForOEM(getContext().getResources().getStringArray(17236300));
        String string = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigBlockNotiAppList");
        if (string != null && string.length() > 0) {
            this.mPreferencesHelper.lockChannelsForOEM(string.split(","));
        }
        ZenModeHelper zenModeHelper2 = this.mZenModeHelper;
        zenModeHelper2.setPriorityOnlyDndExemptPackages(zenModeHelper2.getExceptionPackages());
        this.mWarnRemoteViewsSizeBytes = getContext().getResources().getInteger(R.integer.config_pauseRotationWhenUnfolding_displaySwitchTimeout);
        this.mStripRemoteViewsSizeBytes = getContext().getResources().getInteger(R.integer.config_overrideHasPermanentMenuKey);
        this.mMsgPkgsAllowedAsConvos = Set.of((Object[]) getStringArrayResource(17236260));
        this.mDefaultSearchSelectorPkg = getContext().getString(getContext().getResources().getIdentifier("config_defaultSearchSelectorPackageName", "string", "android"));
        this.mFlagResolver = flagResolver;
        this.mStatsManager = statsManager;
        this.mToastRateLimiter = multiRateLimiter;
        this.mEdgeLightingManager = edgeLightingManager;
        this.mMultiStarEnable = this.mPackageManagerClient.hasSystemFeature("android.software.freeform_window_management");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
        intentFilter.addAction("samsung.intent.action.PHONE_STATE");
        getContext().registerReceiverAsUser(this.mIntentReceiver, UserHandle.ALL, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter2.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter2.addDataScheme("package");
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, UserHandle.ALL, intentFilter2, null, null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter3.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        intentFilter3.addAction("android.intent.action.DISTRACTING_PACKAGES_CHANGED");
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, UserHandle.ALL, intentFilter3, null, null);
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, UserHandle.ALL, new IntentFilter("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"), null, null);
        IntentFilter intentFilter4 = new IntentFilter(ACTION_NOTIFICATION_TIMEOUT);
        intentFilter4.addDataScheme("timeout");
        getContext().registerReceiver(this.mNotificationTimeoutReceiver, intentFilter4, 2);
        getContext().registerReceiver(this.mRestoreReceiver, new IntentFilter("android.os.action.SETTING_RESTORED"));
        getContext().registerReceiver(this.mLocaleChangeReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("com.samsung.android.scpm.policy.UPDATE.NSF_CONVERSATION_APPS");
        intentFilter5.addAction("com.samsung.android.scpm.policy.UPDATE.nsf-ongoing-dismiss-exception-keys");
        intentFilter5.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        getContext().registerReceiverAsUser(this.mScpmIntentReceiver, UserHandle.ALL, intentFilter5, null, null);
        this.mReviewNotificationPermissionsReceiver = new ReviewNotificationPermissionsReceiver();
        getContext().registerReceiver(this.mReviewNotificationPermissionsReceiver, ReviewNotificationPermissionsReceiver.getFilter(), 4);
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("com.samsung.notification.REQUEST_REBIND_LISTENER");
        getContext().registerReceiverAsUser(this.mSecIntentReceiver, UserHandle.ALL, intentFilter6, "com.samsung.android.launcher.permission.WRITE_SETTINGS", null);
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$11 */
    public class C198911 extends ZenModeHelper.Callback {
        public C198911() {
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onConfigChanged() {
            NotificationManagerService.this.handleSavePolicyFile();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onZenModeChanged() {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$11$$ExternalSyntheticLambda3
                public final void runOrThrow() {
                    NotificationManagerService.C198911.this.lambda$onZenModeChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onZenModeChanged$0() {
            NotificationManagerService.this.sendRegisteredOnlyBroadcast("android.app.action.INTERRUPTION_FILTER_CHANGED");
            NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL").addFlags(67108864), UserHandle.ALL, "android.permission.MANAGE_NOTIFICATIONS");
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.updateInterruptionFilterLocked();
            }
            int callingUid = Binder.getCallingUid();
            if (NotificationManagerService.this.mZenModeHelper.getAppsToBypassDndForEnabledLifeStyle() != null) {
                if (NotificationManagerService.DBG) {
                    Slog.d("NotificationService", "LifeStyle rule is added when DND is on.");
                }
                if (NotificationManagerService.this.mZenModeHelper.getZenMode() != 0) {
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    notificationManagerService.mPreferencesHelper.setChannelsBypassingDndForLifeStyle(true, callingUid, notificationManagerService.isCallerIsSystemOrSystemUi());
                } else {
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    notificationManagerService2.mPreferencesHelper.setChannelsBypassingDndForLifeStyle(false, callingUid, notificationManagerService2.isCallerIsSystemOrSystemUi());
                }
            } else {
                if (NotificationManagerService.DBG) {
                    Slog.d("NotificationService", "There is not a LifeStyle rule.");
                }
                NotificationManagerService notificationManagerService3 = NotificationManagerService.this;
                notificationManagerService3.mPreferencesHelper.setChannelsBypassingDndForLifeStyle(false, callingUid, notificationManagerService3.isCallerIsSystemOrSystemUi());
            }
            NotificationManagerService.this.mRankingHandler.requestSort();
            NotificationManagerService.this.notifyZenPolicy();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onPolicyChanged() {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$11$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    NotificationManagerService.C198911.this.lambda$onPolicyChanged$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPolicyChanged$1() {
            NotificationManagerService.this.sendRegisteredOnlyBroadcast("android.app.action.NOTIFICATION_POLICY_CHANGED");
            NotificationManagerService.this.mRankingHandler.requestSort();
            NotificationManagerService.this.notifyZenPolicy();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onConsolidatedPolicyChanged() {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$11$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    NotificationManagerService.C198911.this.lambda$onConsolidatedPolicyChanged$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConsolidatedPolicyChanged$2() {
            NotificationManagerService.this.mRankingHandler.requestSort();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onAutomaticRuleStatusChanged(final int i, final String str, final String str2, final int i2) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$11$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    NotificationManagerService.C198911.this.lambda$onAutomaticRuleStatusChanged$3(str, str2, i2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAutomaticRuleStatusChanged$3(String str, String str2, int i, int i2) {
            Intent intent = new Intent("android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED");
            intent.setPackage(str);
            intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_ID", str2);
            intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_STATUS", i);
            NotificationManagerService.this.getContext().sendBroadcastAsUser(intent, UserHandle.of(i2));
        }
    }

    public void onDestroy() {
        getContext().unregisterReceiver(this.mIntentReceiver);
        getContext().unregisterReceiver(this.mPackageIntentReceiver);
        getContext().unregisterReceiver(this.mNotificationTimeoutReceiver);
        getContext().unregisterReceiver(this.mRestoreReceiver);
        getContext().unregisterReceiver(this.mLocaleChangeReceiver);
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = this.mDeviceConfigChangedListener;
        if (onPropertiesChangedListener != null) {
            DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
        }
    }

    public String[] getStringArrayResource(int i) {
        return getContext().getResources().getStringArray(i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        SnoozeHelper snoozeHelper = new SnoozeHelper(getContext(), new SnoozeHelper.Callback() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda0
            @Override // com.android.server.notification.SnoozeHelper.Callback
            public final void repost(int i, NotificationRecord notificationRecord, boolean z) {
                NotificationManagerService.this.lambda$onStart$0(i, notificationRecord, z);
            }
        }, this.mUserProfiles);
        File file = new File(Environment.getDataDirectory(), "system");
        this.mRankingThread.start();
        WorkerHandler workerHandler = new WorkerHandler(Looper.myLooper());
        this.mShowReviewPermissionsNotification = getContext().getResources().getBoolean(R.bool.config_lockUiMode);
        RankingHandlerWorker rankingHandlerWorker = new RankingHandlerWorker(this.mRankingThread.getLooper());
        IPackageManager packageManager = AppGlobals.getPackageManager();
        PackageManager packageManager2 = getContext().getPackageManager();
        LightsManager lightsManager = (LightsManager) getLocalService(LightsManager.class);
        NotificationListeners notificationListeners = new NotificationListeners(this, getContext(), this.mNotificationLock, this.mUserProfiles, AppGlobals.getPackageManager());
        NotificationAssistants notificationAssistants = new NotificationAssistants(getContext(), this.mNotificationLock, this.mUserProfiles, AppGlobals.getPackageManager());
        ConditionProviders conditionProviders = new ConditionProviders(getContext(), this.mUserProfiles, AppGlobals.getPackageManager());
        NotificationUsageStats notificationUsageStats = new NotificationUsageStats(getContext());
        AtomicFile atomicFile = new AtomicFile(new File(file, "notification_policy.xml"), "notification-policy");
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService("activity");
        GroupHelper groupHelper = getGroupHelper();
        IActivityManager service = ActivityManager.getService();
        ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        IUriGrantsManager service2 = UriGrantsManager.getService();
        UriGrantsManagerInternal uriGrantsManagerInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        AppOpsManager appOpsManager = (AppOpsManager) getContext().getSystemService(AppOpsManager.class);
        UserManager userManager = (UserManager) getContext().getSystemService(UserManager.class);
        NotificationHistoryManager notificationHistoryManager = new NotificationHistoryManager(getContext(), workerHandler);
        StatsManager statsManager = (StatsManager) getContext().getSystemService("stats");
        this.mStatsManager = statsManager;
        init(workerHandler, rankingHandlerWorker, packageManager, packageManager2, lightsManager, notificationListeners, notificationAssistants, conditionProviders, null, snoozeHelper, notificationUsageStats, atomicFile, activityManager, groupHelper, service, activityTaskManagerInternal, usageStatsManagerInternal, devicePolicyManagerInternal, service2, uriGrantsManagerInternal, appOpsManager, userManager, notificationHistoryManager, statsManager, (TelephonyManager) getContext().getSystemService(TelephonyManager.class), (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class), createToastRateLimiter(), new PermissionHelper(getContext(), AppGlobals.getPackageManager(), AppGlobals.getPermissionManager()), (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class), (TelecomManager) getContext().getSystemService(TelecomManager.class), new NotificationChannelLoggerImpl(), SystemUiSystemPropertiesFlags.getResolver(), (PermissionManager) getContext().getSystemService(PermissionManager.class), (PowerManager) getContext().getSystemService(PowerManager.class), new PostNotificationTrackerFactory() { // from class: com.android.server.notification.NotificationManagerService.13
        }, new EdgeLightingManager(getContext()), new AtomicFile(new File(file, "notification_conversation_apps_policy.json"), "notification-conversation-apps-policy"), new AtomicFile(new File(file, "notification_ongoing_dismiss_exception_policy.json"), "notification-ongoing-dismiss-exception-policy"));
        publishBinderService("notification", this.mService, false, 5);
        publishLocalService(NotificationManagerInternal.class, this.mInternalService);
        publishLocalService(EdgeManagerInternal.class, new EdgeLightingLocalService());
        this.mNotificationReminder = new NotificationReminder(getContext(), Looper.myLooper(), this.mPreferencesHelper, this.mAlarmManager, this.mVibratorHelper);
        this.mSmartAlertController = new SmartAlertController(getContext());
        this.mEasyMuteController = new EasyMuteController(getContext());
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(int i, NotificationRecord notificationRecord, boolean z) {
        try {
            if (DBG) {
                Slog.d("NotificationService", "Reposting " + notificationRecord.getKey() + " " + z);
            }
            notificationRecord.getSbn().getNotification().semFlags &= -129;
            enqueueNotificationInternal(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getOpPkg(), notificationRecord.getSbn().getUid(), notificationRecord.getSbn().getInitialPid(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), notificationRecord.getSbn().getNotification(), i, z, false);
        } catch (Exception e) {
            Slog.e("NotificationService", "Cannot un-snooze notification", e);
        }
    }

    public void registerDeviceConfigChange() {
        this.mDeviceConfigChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda9
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                NotificationManagerService.this.lambda$registerDeviceConfigChange$1(properties);
            }
        };
        this.mSystemExemptFromDismissal = DeviceConfig.getBoolean("device_policy_manager", "application_exemptions", true);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new HandlerExecutor(this.mHandler), this.mDeviceConfigChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDeviceConfigChange$1(DeviceConfig.Properties properties) {
        if ("systemui".equals(properties.getNamespace())) {
            Iterator it = properties.getKeyset().iterator();
            while (it.hasNext()) {
                if ("nas_default_service".equals((String) it.next())) {
                    this.mAssistants.resetDefaultAssistantsIfNecessary();
                }
            }
        }
    }

    public final void registerNotificationPreferencesPullers() {
        StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new StatsPullAtomCallbackImpl();
        this.mPullAtomCallback = statsPullAtomCallbackImpl;
        this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
        this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
        this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
        this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.DND_MODE_RULE, (StatsManager.PullAtomMetadata) null, BackgroundThread.getExecutor(), this.mPullAtomCallback);
    }

    public class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, List list) {
            if (i != 10084) {
                switch (i) {
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown tagId=" + i);
                }
            }
            return NotificationManagerService.this.pullNotificationStates(i, list);
        }
    }

    public final int pullNotificationStates(int i, List list) {
        if (i != 10084) {
            switch (i) {
                case FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                    this.mPreferencesHelper.pullPackagePreferencesStats(list, getAllUsersNotificationPermissions());
                    break;
                case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                    this.mPreferencesHelper.pullPackageChannelPreferencesStats(list);
                    break;
                case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                    this.mPreferencesHelper.pullPackageChannelGroupPreferencesStats(list);
                    break;
            }
            return 0;
        }
        this.mZenModeHelper.pullRules(list);
        return 0;
    }

    public final GroupHelper getGroupHelper() {
        this.mAutoGroupAtCount = 4;
        return new GroupHelper(4, new GroupHelper.Callback() { // from class: com.android.server.notification.NotificationManagerService.14
            @Override // com.android.server.notification.GroupHelper.Callback
            public void addAutoGroup(String str) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.addAutogroupKeyLocked(str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void removeAutoGroup(String str) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.removeAutogroupKeyLocked(str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void addAutoGroupSummary(int i, String str, String str2, int i2) {
                NotificationRecord createAutoGroupSummary = NotificationManagerService.this.createAutoGroupSummary(i, str, str2, i2);
                if (createAutoGroupSummary != null) {
                    boolean z = NotificationManagerService.this.mActivityManager.getPackageImportance(str) == 100;
                    WorkerHandler workerHandler = NotificationManagerService.this.mHandler;
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    workerHandler.post(notificationManagerService.new EnqueueNotificationRunnable(i, createAutoGroupSummary, z, notificationManagerService.mPostNotificationTrackerFactory.newTracker(null)));
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void removeAutoGroupSummary(int i, String str) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.clearAutogroupSummaryLocked(i, str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void updateAutogroupSummary(int i, String str, int i2) {
                boolean z = str != null && NotificationManagerService.this.mActivityManager.getPackageImportance(str) == 100;
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.updateAutobundledSummaryFlags(i, str, i2, z);
                }
            }
        });
    }

    public final void sendRegisteredOnlyBroadcast(String str) {
        int[] profileIds = this.mUmInternal.getProfileIds(this.mAmi.getCurrentUserId(), true);
        Intent addFlags = new Intent(str).addFlags(1073741824);
        for (int i : profileIds) {
            getContext().sendBroadcastAsUser(addFlags, UserHandle.of(i), null);
        }
        for (int i2 : profileIds) {
            Iterator it = this.mConditionProviders.getAllowedPackages(i2).iterator();
            while (it.hasNext()) {
                getContext().sendBroadcastAsUser(new Intent(str).setPackage((String) it.next()).setFlags(67108864), UserHandle.of(i2));
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        onBootPhase(i, Looper.getMainLooper());
    }

    public void onBootPhase(int i, Looper looper) {
        if (i == 500) {
            this.mSystemReady = true;
            this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
            this.mAudioManagerInternal = (AudioManagerInternal) getLocalService(AudioManagerInternal.class);
            this.mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
            this.mKeyguardManager = (KeyguardManager) getContext().getSystemService(KeyguardManager.class);
            this.mZenModeHelper.onSystemReady();
            RoleObserver roleObserver = new RoleObserver(getContext(), (RoleManager) getContext().getSystemService(RoleManager.class), this.mPackageManager, looper);
            roleObserver.init();
            this.mRoleObserver = roleObserver;
            this.mShortcutHelper = new ShortcutHelper((LauncherApps) getContext().getSystemService("launcherapps"), this.mShortcutListener, (ShortcutServiceInternal) getLocalService(ShortcutServiceInternal.class), (UserManager) getContext().getSystemService("user"));
            BubbleExtractor bubbleExtractor = (BubbleExtractor) this.mRankingHelper.findExtractor(BubbleExtractor.class);
            if (bubbleExtractor != null) {
                bubbleExtractor.setShortcutHelper(this.mShortcutHelper);
            }
            ImportanceExtractor importanceExtractor = (ImportanceExtractor) this.mRankingHelper.findExtractor(ImportanceExtractor.class);
            if (importanceExtractor != null) {
                importanceExtractor.setPreferenceHelper(this.mPreferencesHelper);
            }
            VisibilityExtractor visibilityExtractor = (VisibilityExtractor) this.mRankingHelper.findExtractor(VisibilityExtractor.class);
            if (visibilityExtractor != null) {
                visibilityExtractor.setPreferenceHelper(this.mPreferencesHelper);
            }
            registerNotificationPreferencesPullers();
            new LockPatternUtils(getContext()).registerStrongAuthTracker(this.mStrongAuthTracker);
            return;
        }
        if (i != 600) {
            if (i == 550) {
                this.mSnoozeHelper.scheduleRepostsForPersistedNotifications(System.currentTimeMillis());
                return;
            }
            if (i == 520) {
                this.mPreferencesHelper.updateFixedImportance(this.mUm.getUsers());
                this.mPreferencesHelper.migrateNotificationPermissions(this.mUm.getUsers());
                return;
            } else {
                if (i == 1000) {
                    this.mEdgeLightingManager.onBootCompleted();
                    this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.this.lambda$onBootPhase$2();
                        }
                    }, 60000L);
                    return;
                }
                return;
            }
        }
        this.mSettingsObserver.observe();
        this.mListeners.onBootPhaseAppsCanStart();
        this.mAssistants.onBootPhaseAppsCanStart();
        this.mConditionProviders.onBootPhaseAppsCanStart();
        this.mHistoryManager.onBootPhaseAppsCanStart();
        registerDeviceConfigChange();
        migrateDefaultNAS();
        maybeShowInitialReviewPermissionsNotification();
        this.mEdgeLightingManager.onBootPhaseAppsCanStart();
        notifyZenPolicy();
        boolean isRuneStoneSupported = isRuneStoneSupported();
        this.mIsRuneStoneSupported = isRuneStoneSupported;
        if (isRuneStoneSupported) {
            try {
                getContext().getContentResolver().registerContentObserver(RunestoneStateContract.ENABLE_CONTENT_URI, false, this.mStateContentObserver);
            } catch (SecurityException e) {
                Slog.w("NotificationService", "Failed to find provider RuneStone", e);
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(final SystemService.TargetUser targetUser) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerService.this.lambda$onUserUnlocked$3(targetUser);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserUnlocked$3(SystemService.TargetUser targetUser) {
        Trace.traceBegin(524288L, "notifHistoryUnlockUser");
        try {
            this.mHistoryManager.onUserUnlocked(targetUser.getUserIdentifier());
            this.mEdgeLightingManager.onUnlockUser(targetUser.getUserIdentifier());
        } finally {
            Trace.traceEnd(524288L);
        }
    }

    public final void sendAppBlockStateChangedBroadcast(final String str, final int i, final boolean z) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerService.this.lambda$sendAppBlockStateChangedBroadcast$4(z, str, i);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAppBlockStateChangedBroadcast$4(boolean z, String str, int i) {
        try {
            getContext().sendBroadcastAsUser(new Intent("android.app.action.APP_BLOCK_STATE_CHANGED").putExtra("android.app.extra.BLOCKED_STATE", z).addFlags(268435456).setPackage(str), UserHandle.of(UserHandle.getUserId(i)), null);
        } catch (SecurityException e) {
            Slog.w("NotificationService", "Can't notify app about app block change", e);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(final SystemService.TargetUser targetUser) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerService.this.lambda$onUserStopping$5(targetUser);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserStopping$5(SystemService.TargetUser targetUser) {
        Trace.traceBegin(524288L, "notifHistoryStopUser");
        try {
            this.mHistoryManager.onUserStopped(targetUser.getUserIdentifier());
        } finally {
            Trace.traceEnd(524288L);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        int userIdentifier = targetUser2.getUserIdentifier();
        this.mEdgeLightingManager.onSwitchUser(userIdentifier);
        Slog.d("NotificationService", "onUserSwitching: " + userIdentifier);
    }

    public final void updateListenerHintsLocked() {
        int calculateHints = calculateHints();
        int i = this.mListenerHints;
        if (calculateHints == i) {
            return;
        }
        ZenLog.traceListenerHintsChanged(i, calculateHints, this.mEffectsSuppressors.size());
        this.mListenerHints = calculateHints;
        scheduleListenerHintsChanged(calculateHints);
    }

    public final void updateEffectsSuppressorLocked() {
        long calculateSuppressedEffects = calculateSuppressedEffects();
        if (calculateSuppressedEffects == this.mZenModeHelper.getSuppressedEffects()) {
            return;
        }
        ArrayList suppressors = getSuppressors();
        ZenLog.traceEffectsSuppressorChanged(this.mEffectsSuppressors, suppressors, calculateSuppressedEffects);
        this.mEffectsSuppressors = suppressors;
        this.mZenModeHelper.setSuppressedEffects(calculateSuppressedEffects);
        sendRegisteredOnlyBroadcast("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
    }

    public final void exitIdle() {
        DeviceIdleManager deviceIdleManager = this.mDeviceIdleManager;
        if (deviceIdleManager != null) {
            deviceIdleManager.endIdle("notification interaction");
        }
    }

    public void updateNotificationChannelInt(String str, int i, NotificationChannel notificationChannel, boolean z) {
        if (notificationChannel.isDeleted()) {
            Log.d("NotificationService", " channel already deleted. no update for " + notificationChannel.getId());
            return;
        }
        if (notificationChannel.getImportance() == 0) {
            cancelAllNotificationsInt(MY_UID, MY_PID, str, notificationChannel.getId(), 0, 0, true, UserHandle.getUserId(i), 17, null);
            if (isUidSystemOrPhone(i)) {
                IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
                int i2 = 0;
                for (int size = currentProfileIds.size(); i2 < size; size = size) {
                    cancelAllNotificationsInt(MY_UID, MY_PID, str, notificationChannel.getId(), 0, 0, true, currentProfileIds.get(i2), 17, null);
                    i2++;
                }
            }
        }
        NotificationChannel notificationChannel2 = this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), true);
        this.mPreferencesHelper.updateNotificationChannel(str, i, notificationChannel, true, Binder.getCallingUid(), isCallerIsSystemOrSystemUi());
        if (this.mPreferencesHelper.onlyHasDefaultChannel(str, i)) {
            this.mPermissionHelper.setNotificationPermission(str, UserHandle.getUserId(i), notificationChannel.getImportance() != 0, true);
        }
        maybeNotifyChannelOwner(str, i, notificationChannel2, notificationChannel);
        if (!z) {
            this.mListeners.notifyNotificationChannelChanged(str, UserHandle.getUserHandleForUid(i), this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), false), 2);
        }
        handleSavePolicyFile();
    }

    public final void maybeNotifyChannelOwner(String str, int i, NotificationChannel notificationChannel, NotificationChannel notificationChannel2) {
        try {
            if ((notificationChannel.getImportance() != 0 || notificationChannel2.getImportance() == 0) && (notificationChannel.getImportance() == 0 || notificationChannel2.getImportance() != 0)) {
                return;
            }
            getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED").putExtra("android.app.extra.NOTIFICATION_CHANNEL_ID", notificationChannel2.getId()).putExtra("android.app.extra.BLOCKED_STATE", notificationChannel2.getImportance() == 0).addFlags(268435456).setPackage(str), UserHandle.of(UserHandle.getUserId(i)), null);
        } catch (SecurityException e) {
            Slog.w("NotificationService", "Can't notify app about channel change", e);
        }
    }

    public void createNotificationChannelGroup(String str, int i, NotificationChannelGroup notificationChannelGroup, boolean z, boolean z2) {
        Objects.requireNonNull(notificationChannelGroup);
        Objects.requireNonNull(str);
        NotificationChannelGroup notificationChannelGroup2 = this.mPreferencesHelper.getNotificationChannelGroup(notificationChannelGroup.getId(), str, i);
        this.mPreferencesHelper.createNotificationChannelGroup(str, i, notificationChannelGroup, z, Binder.getCallingUid(), isCallerIsSystemOrSystemUi());
        if (!z) {
            maybeNotifyChannelGroupOwner(str, i, notificationChannelGroup2, notificationChannelGroup);
        }
        if (z2) {
            return;
        }
        this.mListeners.notifyNotificationChannelGroupChanged(str, UserHandle.of(UserHandle.getCallingUserId()), notificationChannelGroup, 1);
    }

    public final void maybeNotifyChannelGroupOwner(String str, int i, NotificationChannelGroup notificationChannelGroup, NotificationChannelGroup notificationChannelGroup2) {
        try {
            if (notificationChannelGroup.isBlocked() != notificationChannelGroup2.isBlocked()) {
                getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED").putExtra("android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID", notificationChannelGroup2.getId()).putExtra("android.app.extra.BLOCKED_STATE", notificationChannelGroup2.isBlocked()).addFlags(268435456).setPackage(str), UserHandle.of(UserHandle.getUserId(i)), null);
            }
        } catch (SecurityException e) {
            Slog.w("NotificationService", "Can't notify app about group change", e);
        }
    }

    public final ArrayList getSuppressors() {
        ArrayList arrayList = new ArrayList();
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            Iterator it = ((ArraySet) this.mListenersDisablingEffects.valueAt(size)).iterator();
            while (it.hasNext()) {
                arrayList.add((ComponentName) it.next());
            }
        }
        return arrayList;
    }

    public final boolean removeDisabledHints(ManagedServices.ManagedServiceInfo managedServiceInfo) {
        return removeDisabledHints(managedServiceInfo, 0);
    }

    public final boolean removeDisabledHints(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        boolean z = false;
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            int keyAt = this.mListenersDisablingEffects.keyAt(size);
            ArraySet arraySet = (ArraySet) this.mListenersDisablingEffects.valueAt(size);
            if (i == 0 || (keyAt & i) == keyAt) {
                z |= arraySet.remove(managedServiceInfo.component);
            }
        }
        return z;
    }

    public final void addDisabledHints(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        if ((i & 1) != 0) {
            addDisabledHint(managedServiceInfo, 1);
        }
        if ((i & 2) != 0) {
            addDisabledHint(managedServiceInfo, 2);
        }
        if ((i & 4) != 0) {
            addDisabledHint(managedServiceInfo, 4);
        }
    }

    public final void addDisabledHint(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        if (this.mListenersDisablingEffects.indexOfKey(i) < 0) {
            this.mListenersDisablingEffects.put(i, new ArraySet());
        }
        ((ArraySet) this.mListenersDisablingEffects.get(i)).add(managedServiceInfo.component);
    }

    public final int calculateHints() {
        int i = 0;
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            int keyAt = this.mListenersDisablingEffects.keyAt(size);
            if (!((ArraySet) this.mListenersDisablingEffects.valueAt(size)).isEmpty()) {
                i |= keyAt;
            }
        }
        return i;
    }

    public final long calculateSuppressedEffects() {
        int calculateHints = calculateHints();
        long j = (calculateHints & 1) != 0 ? 3L : 0L;
        if ((calculateHints & 2) != 0) {
            j |= 1;
        }
        return (calculateHints & 4) != 0 ? j | 2 : j;
    }

    public final void updateInterruptionFilterLocked() {
        int zenModeListenerInterruptionFilter = this.mZenModeHelper.getZenModeListenerInterruptionFilter();
        if (zenModeListenerInterruptionFilter == this.mInterruptionFilter) {
            return;
        }
        this.mInterruptionFilter = zenModeListenerInterruptionFilter;
        scheduleInterruptionFilterChanged(zenModeListenerInterruptionFilter);
    }

    public INotificationManager getBinderService() {
        return INotificationManager.Stub.asInterface(this.mService);
    }

    public void reportSeen(NotificationRecord notificationRecord) {
        if (notificationRecord.isProxied()) {
            return;
        }
        this.mAppUsageStats.reportEvent(notificationRecord.getSbn().getPackageName(), getRealUserId(notificationRecord.getSbn().getUserId()), 10);
    }

    public int calculateSuppressedVisualEffects(NotificationManager.Policy policy, NotificationManager.Policy policy2, int i) {
        int i2 = policy.suppressedVisualEffects;
        if (i2 == -1) {
            return i2;
        }
        int[] iArr = {4, 8, 16, 32, 64, 128, 256};
        if (i < 28) {
            while (r2 < 7) {
                int i3 = iArr[r2];
                i2 = (i2 & (~i3)) | (i3 & policy2.suppressedVisualEffects);
                r2++;
            }
            if ((i2 & 1) != 0) {
                i2 = i2 | 8 | 4;
            }
            if ((i2 & 2) == 0) {
                return i2;
            }
        } else {
            if (((i2 + (-2)) - 1 > 0 ? 1 : 0) != 0) {
                int i4 = i2 & (-4);
                if ((i4 & 16) != 0) {
                    i4 |= 2;
                }
                return ((i4 & 8) == 0 || (i4 & 4) == 0 || (i4 & 128) == 0) ? i4 : i4 | 1;
            }
            if ((i2 & 1) != 0) {
                i2 = i2 | 8 | 4 | 128;
            }
            if ((i2 & 2) == 0) {
                return i2;
            }
        }
        return i2 | 16;
    }

    public void maybeRecordInterruptionLocked(NotificationRecord notificationRecord) {
        if (notificationRecord.isInterruptive() && !notificationRecord.hasRecordedInterruption()) {
            this.mAppUsageStats.reportInterruptiveNotification(notificationRecord.getSbn().getPackageName(), notificationRecord.getChannel().getId(), getRealUserId(notificationRecord.getSbn().getUserId()));
        }
        if (this.mIsInterruptivePostNotif) {
            Trace.traceBegin(524288L, "notifHistoryAddItem");
            try {
                if (notificationRecord.getNotification().getSmallIcon() != null) {
                    Uri historyDataUri = getHistoryDataUri(notificationRecord.getSbn().getPackageContext(getContext()), notificationRecord);
                    NotificationHistory.HistoricalNotification build = new NotificationHistory.HistoricalNotification.Builder().setPackage(notificationRecord.getSbn().getPackageName()).setUid(notificationRecord.getSbn().getUid()).setUserId(notificationRecord.getSbn().getNormalizedUserId()).setChannelId(notificationRecord.getChannel().getId()).setChannelName(notificationRecord.getChannel().getName().toString()).setPostedTimeMs(System.currentTimeMillis()).setTitle(getHistoryTitle(notificationRecord.getSbn().getPackageContext(getContext()), notificationRecord.getNotification())).setText(getHistoryText(notificationRecord.getSbn().getPackageContext(getContext()), notificationRecord.getNotification())).setIcon(notificationRecord.getNotification().getSmallIcon()).setUri(historyDataUri).setType(historyDataUri == null ? 0 : 2).setSbnKey(notificationRecord.getKey()).setWhen(notificationRecord.getNotification().when).build();
                    if (this.mNeedDeletePrevHistory) {
                        this.mHistoryManager.updateNotificationItems(0, notificationRecord.getSbn().getNormalizedUserId(), build);
                    }
                    if (checkNotificationHistoryData(notificationRecord, build)) {
                        this.mHistoryManager.addNotification(build);
                    }
                }
                Trace.traceEnd(524288L);
                notificationRecord.setRecordedInterruption(true);
                this.mIsInterruptivePostNotif = false;
                this.mNeedDeletePrevHistory = false;
            } catch (Throwable th) {
                Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    public final boolean checkNotificationHistoryData(NotificationRecord notificationRecord, NotificationHistory.HistoricalNotification historicalNotification) {
        if (historicalNotification.getPackage() != null && historicalNotification.getChannelId() != null && historicalNotification.getChannelName() != null) {
            return !doNotSaveDuplicatedAnswer(notificationRecord, historicalNotification);
        }
        Slog.d("NotificationService", "History data has null value, can not be saved " + historicalNotification);
        return false;
    }

    public final String findConversationUserName(NotificationRecord notificationRecord) {
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(notificationRecord.getSbn().getPackageContext(getContext()), notificationRecord.getSbn().getNotification());
        String name = recoverBuilder.getStyle() instanceof Notification.MessagingStyle ? ((Notification.MessagingStyle) recoverBuilder.getStyle()).getUser().getName() : "";
        return name != null ? name.toString() : "";
    }

    public final boolean doNotSaveDuplicatedAnswer(NotificationRecord notificationRecord, NotificationHistory.HistoricalNotification historicalNotification) {
        String findConversationTitle;
        if (!Notification.MessagingStyle.class.equals(notificationRecord.getNotification().getNotificationStyle())) {
            return false;
        }
        NotificationHistory readFilteredNotificationHistoryForPackage = this.mHistoryManager.readFilteredNotificationHistoryForPackage(historicalNotification.getUserId(), historicalNotification.getPackage(), historicalNotification.getSbnKey(), 30);
        boolean z = notificationRecord.getNotification().extras.getBoolean("android.isGroupConversation");
        if (z) {
            findConversationTitle = findConversationSender(readFilteredNotificationHistoryForPackage, notificationRecord);
        } else {
            findConversationTitle = findConversationTitle(readFilteredNotificationHistoryForPackage, notificationRecord);
        }
        String findConversationUserName = findConversationUserName(notificationRecord);
        if (historicalNotification.getType() != 1) {
            if (z) {
                if (historicalNotification.getTitle() != null && findConversationUserName.equals(historicalNotification.getTitle())) {
                    Log.d("NotificationService", historicalNotification.getSbnKey() + " : " + historicalNotification.getPostedTimeMs() + " is duplicated infomation in Group conversation. will not be saved!");
                    return true;
                }
            } else if (TextUtils.isEmpty(findConversationTitle) || (historicalNotification.getTitle() != null && !findConversationTitle.equals(historicalNotification.getTitle()))) {
                Log.d("NotificationService", historicalNotification.getSbnKey() + " : " + historicalNotification.getPostedTimeMs() + " is duplicated infomation. will not be saved!");
                return true;
            }
        }
        return false;
    }

    public final String findConversationTitle(NotificationHistory notificationHistory, NotificationRecord notificationRecord) {
        CharSequence label;
        ShortcutInfo shortcutInfo = notificationRecord.getShortcutInfo();
        if (shortcutInfo != null && (label = shortcutInfo.getLabel()) != null && !TextUtils.isEmpty(label)) {
            return label.toString();
        }
        return findConversationSender(notificationHistory, notificationRecord);
    }

    public final String findConversationSender(NotificationHistory notificationHistory, NotificationRecord notificationRecord) {
        StatusBarNotification sbn = notificationRecord.getSbn();
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(sbn.getPackageContext(getContext()), sbn.getNotification());
        if (notificationHistory != null) {
            for (NotificationHistory.HistoricalNotification historicalNotification : notificationHistory.getNotificationsToWrite()) {
                int type = historicalNotification.getType();
                String title = historicalNotification.getTitle();
                if (type != 1 && title != null && !TextUtils.isEmpty(title) && !title.equals("")) {
                    return title;
                }
            }
        }
        if (recoverBuilder.getStyle() instanceof Notification.MessagingStyle) {
            Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) recoverBuilder.getStyle();
            CharSequence name = messagingStyle.getUser().getName();
            for (Notification.MessagingStyle.Message message : messagingStyle.getMessages()) {
                if (name != null && !TextUtils.isEmpty(name) && !name.equals(message.getSender())) {
                    return message.getSender() == null ? "" : message.getSender().toString();
                }
            }
        }
        Log.d("NotificationService", "can't find sender properly so ,, return empty");
        return "";
    }

    public final Uri getHistoryDataUri(Context context, NotificationRecord notificationRecord) {
        List<Notification.MessagingStyle.Message> messages;
        if (notificationRecord == null) {
            return null;
        }
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notificationRecord.getNotification());
        if (!(recoverBuilder.getStyle() instanceof Notification.MessagingStyle) || (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) == null || messages.size() <= 0) {
            return null;
        }
        Notification.MessagingStyle.Message message = messages.get(messages.size() - 1);
        if (message.getDataUri() == null || message.getDataMimeType() == null || !message.getDataMimeType().startsWith("image/")) {
            return null;
        }
        return message.getDataUri();
    }

    public final boolean isNeedSaveHistory(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, boolean z) {
        if (notificationRecord2.getSbn().isGroup() && notificationRecord2.getSbn().getNotification().isGroupSummary()) {
            return false;
        }
        if (notificationRecord == null) {
            return true;
        }
        Notification notification = notificationRecord.getSbn().getNotification();
        Notification notification2 = notificationRecord2.getSbn().getNotification();
        if (notification.extras == null || notification2.extras == null || (notificationRecord2.getSbn().getNotification().flags & 64) != 0) {
            return false;
        }
        return z || (notification2.flags & 8) == 0;
    }

    public final boolean isNeedDeletePrevHistory(NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
        if (notificationRecord != null && notificationRecord2 != null) {
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(notificationRecord.getSbn().getPackageContext(getContext()), notificationRecord.getNotification());
            Notification.Builder recoverBuilder2 = Notification.Builder.recoverBuilder(notificationRecord2.getSbn().getPackageContext(getContext()), notificationRecord2.getNotification());
            if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle) && (recoverBuilder2.getStyle() instanceof Notification.MessagingStyle)) {
                Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) recoverBuilder.getStyle();
                Notification.MessagingStyle messagingStyle2 = (Notification.MessagingStyle) recoverBuilder2.getStyle();
                List<Notification.MessagingStyle.Message> messages = messagingStyle.getMessages();
                List<Notification.MessagingStyle.Message> messages2 = messagingStyle2.getMessages();
                if (messages != null && messages.size() > 0 && messages2 != null && messages2.size() > 0) {
                    Notification.MessagingStyle.Message message = messages.get(messages.size() - 1);
                    Notification.MessagingStyle.Message message2 = messages2.get(messages2.size() - 1);
                    if ((message.getDataUri() != null || message.getTimestamp() != message2.getTimestamp()) && message2.getDataUri() != null) {
                        Slog.d("NotificationService", "isNeedDeletePrevHistory  newMs.getDataUri() = " + message2.getDataUri() + ", oldMs.getDataUri() = " + message.getDataUri() + ", newMs.getTimestamp() = " + message2.getTimestamp() + ", oldMs.getTimestamp() = " + message.getTimestamp());
                        if (!message2.getDataUri().equals(message.getDataUri()) && message.getTimestamp() == message2.getTimestamp()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void reportForegroundServiceUpdate(final boolean z, final Notification notification, final int i, final String str, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerService.this.lambda$reportForegroundServiceUpdate$6(z, notification, i, str, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportForegroundServiceUpdate$6(boolean z, Notification notification, int i, String str, int i2) {
        this.mAmi.onForegroundServiceNotificationUpdate(z, notification, i, str, i2);
    }

    public void maybeReportForegroundServiceUpdate(NotificationRecord notificationRecord, boolean z) {
        if (notificationRecord.isForegroundService()) {
            StatusBarNotification sbn = notificationRecord.getSbn();
            reportForegroundServiceUpdate(z, sbn.getNotification(), sbn.getId(), sbn.getPackageName(), sbn.getUser().getIdentifier());
        }
    }

    public final String getHistoryTitle(Context context, Notification notification) {
        Bundle bundle;
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notification);
        CharSequence charSequence = null;
        if (recoverBuilder.getStyle() instanceof Notification.MessagingStyle) {
            Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) recoverBuilder.getStyle();
            List<Notification.MessagingStyle.Message> messages = messagingStyle.getMessages();
            if (messages != null && messages.size() > 0) {
                charSequence = messages.get(messages.size() - 1).getSender();
            }
            if (charSequence == null) {
                charSequence = messagingStyle.getUser().getName();
            }
        }
        if (charSequence == null && (bundle = notification.extras) != null && (charSequence = bundle.getCharSequence("android.title")) == null) {
            charSequence = notification.extras.getCharSequence("android.title.big");
        }
        if (charSequence == null) {
            return getContext().getResources().getString(R.string.resolver_no_personal_apps_available);
        }
        return String.valueOf(charSequence);
    }

    public final String getHistoryText(Context context, Notification notification) {
        CharSequence charSequence;
        List<Notification.MessagingStyle.Message> messages;
        Bundle bundle = notification.extras;
        if (bundle != null) {
            charSequence = bundle.getCharSequence("android.text");
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notification);
            if (recoverBuilder.getStyle() instanceof Notification.BigTextStyle) {
                charSequence = ((Notification.BigTextStyle) recoverBuilder.getStyle()).getBigText();
            } else if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle) && (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) != null && messages.size() > 0) {
                charSequence = messages.get(messages.size() - 1).getText();
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence("android.text");
            }
        } else {
            charSequence = null;
        }
        if (charSequence == null) {
            return null;
        }
        return String.valueOf(charSequence);
    }

    public void maybeRegisterMessageSent(NotificationRecord notificationRecord) {
        if (notificationRecord.isConversation()) {
            if (notificationRecord.getShortcutInfo() != null) {
                if (this.mPreferencesHelper.setValidMessageSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                    handleSavePolicyFile();
                    return;
                } else {
                    if (notificationRecord.getNotification().getBubbleMetadata() == null || !this.mPreferencesHelper.setValidBubbleSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                        return;
                    }
                    handleSavePolicyFile();
                    return;
                }
            }
            if (this.mPreferencesHelper.setInvalidMessageSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                handleSavePolicyFile();
            }
        }
    }

    public void reportUserInteraction(NotificationRecord notificationRecord) {
        this.mAppUsageStats.reportEvent(notificationRecord.getSbn().getPackageName(), getRealUserId(notificationRecord.getSbn().getUserId()), 7);
    }

    public final ToastRecord getToastRecord(int i, int i2, String str, boolean z, IBinder iBinder, CharSequence charSequence, ITransientNotification iTransientNotification, int i3, Binder binder, int i4, ITransientNotificationCallback iTransientNotificationCallback, String str2) {
        if (iTransientNotification == null) {
            return new TextToastRecord(this, this.mStatusBar, i, i2, str, z, iBinder, charSequence, i3, binder, i4, iTransientNotificationCallback, str2);
        }
        return new CustomToastRecord(this, i, i2, str, z, iBinder, iTransientNotification, i3, binder, i4, str2);
    }

    public NotificationManagerInternal getInternalService() {
        return this.mInternalService;
    }

    public final boolean hasFollowedNotification(String str) {
        for (String str2 : this.mNotificationsByKey.keySet()) {
            if (str2 != null && str2.contains(str) && ((NotificationRecord) this.mNotificationsByKey.get(str2)).getImportance() >= 4 && ((NotificationRecord) this.mNotificationsByKey.get(str2)).getLifespanMs(System.currentTimeMillis()) < 500 && ((NotificationRecord) this.mNotificationsByKey.get(str2)).getSbn().getNotification().fullScreenIntent == null) {
                return true;
            }
        }
        return false;
    }

    public final void clearDelayedWakelock() {
        Slog.d("NotificationService", "     WAKELOCK/UP clear all delayed lists..");
        this.mDelayedWakelockList.clear();
        this.mDelayedWakeUpList.clear();
    }

    public final void receiveFollowedNotification(String str) {
        Iterator it = this.mDelayedWakelockList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2.contains(str)) {
                Slog.d("NotificationService", "     WAKELOCK received by notification after Delayed Wakelock : " + str2);
                this.mHandler.removeCallbacksAndMessages(str2);
                this.mDelayedWakelockList.remove(str2);
            }
        }
        Iterator it2 = this.mDelayedWakeUpList.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            if (str3.contains(str)) {
                Slog.d("NotificationService", "     WAKEUP received by notification after Delayed Wakelock : " + str3);
                this.mHandler.removeCallbacksAndMessages(str3);
                this.mDelayedWakeUpList.remove(str3);
            }
        }
    }

    public final MultiRateLimiter createToastRateLimiter() {
        return new MultiRateLimiter.Builder(getContext()).addRateLimits(TOAST_RATE_LIMITS).build();
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$15 */
    public class INotificationManagerStubC199315 extends INotificationManager.Stub {
        public INotificationManagerStubC199315() {
        }

        public boolean dispatchDelayedWakelockAndBlocked(final int i, final String str, final String str2, final int i2) {
            int i3;
            boolean z;
            CoverState coverState;
            Slog.d("NotificationService", "dispatchDelayedWakelockAndBlocked : " + str2);
            if (NotificationManagerService.this.mIsFactoryBinary || str2.equals("com.sec.factory") || str2.equals("com.sec.facatfunction")) {
                Slog.d("NotificationService", "fatory mode");
                return false;
            }
            if (str2.equals("android")) {
                NotificationManagerService.this.clearDelayedWakelock();
                return false;
            }
            if (NotificationManagerService.this.mCoverManager == null) {
                NotificationManagerService.this.mCoverManager = new CoverManager(NotificationManagerService.this.getContext());
            }
            if (NotificationManagerService.this.mCoverManager.getCoverState() == null || (coverState = NotificationManagerService.this.mCoverManager.getCoverState()) == null) {
                i3 = 2;
                z = false;
            } else {
                z = !coverState.getSwitchState();
                i3 = coverState.getType();
                Slog.e("NotificationService", "isCoverClosed =  " + z + " coverType = " + i3);
            }
            if (z) {
                if (i3 == 17) {
                    Slog.e("NotificationService", "cover is closed and it is a clear view cover, so don't turn on screen");
                    return true;
                }
                Slog.e("NotificationService", "cover is closed and it is not a clear view cover, so don't delay wakelock");
                return false;
            }
            if (Settings.System.getInt(NotificationManagerService.this.getContext().getContentResolver(), "edge_lighting", 1) != 1) {
                Slog.d("NotificationService", "edge_lighting turned off");
                return false;
            }
            try {
                if (!isEdgeLightingAllowed(str2, NotificationManagerService.this.mPackageManagerClient.getPackageUid(str2, 0))) {
                    Slog.d("NotificationService", "edgelighting is not allowed for this package");
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("NotificationService", "Package " + str2 + " not found.", e);
            }
            if (NotificationManagerService.this.hasFollowedNotification(str2)) {
                Slog.d("NotificationService", "     WAKELOCK canceled by edgelighting notification - B : " + str2);
                return false;
            }
            String str3 = str + str2 + i;
            if (NotificationManagerService.this.mDelayedWakelockList.contains(str3)) {
                Slog.d("NotificationService", "     WAKELOCK acquired : " + str2);
                NotificationManagerService.this.mDelayedWakelockList.remove(str3);
                return false;
            }
            Slog.d("NotificationService", "     WAKELOCK isDelayed by edgelighting : " + str2);
            NotificationManagerService.this.mDelayedWakelockList.add(str3);
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.15.1
                @Override // java.lang.Runnable
                public void run() {
                    PowerManager.WakeLock wakeLock = NotificationManagerService.this.mWakeLockForAssistantDelay;
                    if (wakeLock != null && wakeLock.isHeld()) {
                        NotificationManagerService.this.mWakeLockForAssistantDelay.release();
                    }
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    if (notificationManagerService.mWakeLockForAssistantDelay == null) {
                        notificationManagerService.mWakeLockForAssistantDelay = ((PowerManager) notificationManagerService.getContext().getSystemService(PowerManager.class)).newWakeLock(1, "Prevent Sleep by AssistantDelay");
                    }
                    NotificationManagerService.this.mWakeLockForAssistantDelay.acquire(500L);
                }
            });
            NotificationManagerService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$15$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.INotificationManagerStubC199315.this.lambda$dispatchDelayedWakelockAndBlocked$0(i, str, i2, str2);
                }
            }, str3, 500L);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchDelayedWakelockAndBlocked$0(int i, String str, int i2, String str2) {
            PowerManager.WakeLock newWakeLock = ((PowerManager) NotificationManagerService.this.getContext().getSystemService(PowerManager.class)).newWakeLock(i, "EDGELIGHTING:" + str);
            newWakeLock.setWorkSource(new WorkSource(i2, str2));
            newWakeLock.acquire();
            newWakeLock.release();
        }

        public boolean dispatchDelayedWakeUpAndBlocked(final int i, final String str, String str2) {
            if (str2.equals("android")) {
                NotificationManagerService.this.clearDelayedWakelock();
                return false;
            }
            if (Settings.System.getInt(NotificationManagerService.this.getContext().getContentResolver(), "edge_lighting", 1) != 1) {
                return false;
            }
            try {
                if (!isEdgeLightingAllowed(str2, NotificationManagerService.this.mPackageManagerClient.getPackageUid(str2, 0))) {
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("NotificationService", "Package " + str2 + " not found.", e);
            }
            if (NotificationManagerService.this.hasFollowedNotification(str2)) {
                Slog.d("NotificationService", "     WAKEUP canceled by edgelighting notification - B : " + str2);
                return false;
            }
            String str3 = str + str2 + i;
            if (NotificationManagerService.this.mDelayedWakeUpList.contains(str3)) {
                Slog.d("NotificationService", "     WAKEUP acquired : " + str2);
                NotificationManagerService.this.mDelayedWakeUpList.remove(str3);
                return false;
            }
            Slog.d("NotificationService", "     WAKEUP isDelayed by notification : " + str2);
            NotificationManagerService.this.mDelayedWakeUpList.add(str3);
            NotificationManagerService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$15$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.INotificationManagerStubC199315.this.lambda$dispatchDelayedWakeUpAndBlocked$1(i, str);
                }
            }, str3, 500L);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchDelayedWakeUpAndBlocked$1(int i, String str) {
            ((PowerManager) NotificationManagerService.this.getContext().getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), i, "EDGELIGHTING:" + str);
        }

        public void enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
            enqueueToast(str, iBinder, charSequence, null, i, z, i2, iTransientNotificationCallback);
        }

        public void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) {
            enqueueToastForDex(str, iBinder, charSequence, null, i, z, i2, iTransientNotificationCallback, str2, i3);
        }

        public void enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) {
            enqueueToast(str, iBinder, null, iTransientNotification, i, z, i2, null);
        }

        public void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) {
            enqueueToastForDex(str, iBinder, null, iTransientNotification, i, z, i2, null, str2, i3);
        }

        public final void enqueueToast(String str, IBinder iBinder, CharSequence charSequence, ITransientNotification iTransientNotification, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
            enqueueToastForDex(str, iBinder, null, iTransientNotification, i, z, i2, null, null, -1);
        }

        public final void enqueueToastForDex(String str, IBinder iBinder, CharSequence charSequence, ITransientNotification iTransientNotification, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) {
            int i4;
            ArrayList arrayList;
            int userId;
            int mainDisplayAssignedToUser;
            boolean z2 = NotificationManagerService.DBG;
            if (z2) {
                Slog.i("NotificationService", "enqueueToast pkg=" + str + " token=" + iBinder + " duration=" + i + " isUiContext=" + z + " displayId=" + i2);
            }
            if (str == null || ((charSequence == null && iTransientNotification == null) || ((charSequence != null && iTransientNotification != null) || iBinder == null))) {
                Slog.e("NotificationService", "Not enqueuing toast. pkg=" + str + " text=" + ((Object) charSequence) + " callback= token=" + iBinder);
                return;
            }
            int callingUid = Binder.getCallingUid();
            if (z || i2 != 0 || !NotificationManagerService.this.mUm.isVisibleBackgroundUsersSupported() || i2 == (mainDisplayAssignedToUser = NotificationManagerService.this.mUmInternal.getMainDisplayAssignedToUser((userId = UserHandle.getUserId(callingUid))))) {
                i4 = i2;
            } else {
                if (z2) {
                    Slogf.m88d("NotificationService", "Changing display id from %d to %d on user %d", Integer.valueOf(i2), Integer.valueOf(mainDisplayAssignedToUser), Integer.valueOf(userId));
                }
                i4 = mainDisplayAssignedToUser;
            }
            NotificationManagerService.this.checkCallerIsSameApp(str);
            boolean z3 = NotificationManagerService.this.isCallerIsSystemOrSystemUi() || "android".equals(str);
            if (!checkCanEnqueueToast(str, callingUid, i4, iTransientNotification != null, z3)) {
                return;
            }
            if (str2 != null) {
                DisplayToast.out(str, str2, i3, NotificationManagerService.this.getContext());
            }
            ArrayList arrayList2 = NotificationManagerService.this.mToastQueue;
            synchronized (arrayList2) {
                try {
                    try {
                        int callingPid = Binder.getCallingPid();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            int indexOfToastLocked = NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                            if (indexOfToastLocked >= 0) {
                                ToastRecord toastRecord = (ToastRecord) NotificationManagerService.this.mToastQueue.get(indexOfToastLocked);
                                addEventLogTags(i3, str, str2);
                                toastRecord.update(i);
                                arrayList = arrayList2;
                            } else {
                                int size = NotificationManagerService.this.mToastQueue.size();
                                int i5 = 0;
                                for (int i6 = 0; i6 < size; i6++) {
                                    if (((ToastRecord) NotificationManagerService.this.mToastQueue.get(i6)).pkg.equals(str) && (i5 = i5 + 1) >= 5) {
                                        Slog.e("NotificationService", "Package has already queued " + i5 + " toasts. Not showing more. Package=" + str);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return;
                                    }
                                }
                                Binder binder = new Binder();
                                NotificationManagerService.this.mWindowManagerInternal.addWindowToken(binder, 2005, i4, null);
                                arrayList = arrayList2;
                                try {
                                    ToastRecord toastRecord2 = NotificationManagerService.this.getToastRecord(callingUid, callingPid, str, z3, iBinder, charSequence, iTransientNotification, i, binder, i4, iTransientNotificationCallback, str2);
                                    addEventLogTags(i3, str, str2);
                                    int size2 = NotificationManagerService.this.mToastQueue.size();
                                    if (z3) {
                                        size2 = getInsertIndexForSystemToastLocked();
                                    }
                                    if (size2 < NotificationManagerService.this.mToastQueue.size()) {
                                        NotificationManagerService.this.mToastQueue.add(size2, toastRecord2);
                                        indexOfToastLocked = size2;
                                    } else {
                                        NotificationManagerService.this.mToastQueue.add(toastRecord2);
                                        indexOfToastLocked = NotificationManagerService.this.mToastQueue.size() - 1;
                                    }
                                    NotificationManagerService.this.keepProcessAliveForToastIfNeededLocked(callingPid);
                                } catch (Throwable th) {
                                    th = th;
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            }
                            if (indexOfToastLocked == 0) {
                                NotificationManagerService.this.showNextToastLocked(false);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
        }

        public final void addEventLogTags(int i, String str, String str2) {
            if (str2 == null || str2.length() <= 0) {
                return;
            }
            String str3 = ((char) (str2.charAt(0) + 1)) + str2.substring(1, str2.length());
            if (str3.length() > 3) {
                EventLogTags.writeNotificationEnqueueToast(i, str, str3.subSequence(0, 3).toString());
            } else {
                EventLogTags.writeNotificationEnqueueToast(i, str, str3.subSequence(0, str3.length()).toString());
            }
        }

        public final int getInsertIndexForSystemToastLocked() {
            Iterator it = NotificationManagerService.this.mToastQueue.iterator();
            int i = 0;
            while (it.hasNext()) {
                ToastRecord toastRecord = (ToastRecord) it.next();
                if ((i != 0 || !NotificationManagerService.this.mIsCurrentToastShown) && !toastRecord.isSystemToast) {
                    return i;
                }
                i++;
            }
            return i;
        }

        public final boolean checkCanEnqueueToast(String str, int i, int i2, boolean z, boolean z2) {
            boolean isPackagePaused = isPackagePaused(str);
            boolean z3 = !areNotificationsEnabledForPackage(str, i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean z4 = NotificationManagerService.this.mActivityManager.getUidImportance(i) == 100;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (!z2 && ((z3 && !z4) || isPackagePaused)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Suppressing toast from package ");
                    sb.append(str);
                    sb.append(isPackagePaused ? " due to package suspended." : " by user request.");
                    Slog.e("NotificationService", sb.toString());
                    return false;
                }
                int userId = UserHandle.getUserId(i);
                boolean z5 = !NotificationManagerService.this.mToastRateLimitingDisabledUids.contains(Integer.valueOf(i));
                boolean z6 = NotificationManagerService.this.mToastRateLimiter.isWithinQuota(userId, str, "toast_quota_tag") || NotificationManagerService.this.isExemptFromRateLimiting(str, userId);
                boolean isPackageInForegroundForToast = NotificationManagerService.this.isPackageInForegroundForToast(i);
                if (z5 && !z6 && !isPackageInForegroundForToast) {
                    NotificationManagerService.this.reportCompatRateLimitingToastsChange(i);
                    Slog.w("NotificationService", "Package " + str + " is above allowed toast quota at time the toast was posted, the following toast was blocked");
                    return false;
                }
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.blockToast(i, z2, z, notificationManagerService.isPackageInForegroundForToast(i))) {
                    Slog.w("NotificationService", "Blocking custom toast from package " + str + " due to package not in the foreground at time the toast was posted");
                    return false;
                }
                if (z2 || NotificationManagerService.this.mUmInternal.isUserVisible(userId, i2)) {
                    return true;
                }
                Slog.e("NotificationService", "Suppressing toast from package " + str + "/" + i + " as user " + userId + " is not visible on display " + i2);
                return false;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void cancelToast(String str, IBinder iBinder) {
            Slog.i("NotificationService", "cancelToast pkg=" + str + " token=" + iBinder);
            if (str == null || iBinder == null) {
                Slog.e("NotificationService", "Not cancelling notification. pkg=" + str + " token=" + iBinder);
                return;
            }
            synchronized (NotificationManagerService.this.mToastQueue) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int indexOfToastLocked = NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                    if (indexOfToastLocked >= 0) {
                        NotificationManagerService.this.cancelToastLocked(indexOfToastLocked);
                    } else {
                        Slog.w("NotificationService", "Toast already cancelled. pkg=" + str + " token=" + iBinder);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void setToastRateLimitingEnabled(boolean z) {
            super.setToastRateLimitingEnabled_enforcePermission();
            synchronized (NotificationManagerService.this.mToastQueue) {
                int callingUid = Binder.getCallingUid();
                int userId = UserHandle.getUserId(callingUid);
                if (z) {
                    NotificationManagerService.this.mToastRateLimitingDisabledUids.remove(Integer.valueOf(callingUid));
                    try {
                        String[] packagesForUid = NotificationManagerService.this.mPackageManager.getPackagesForUid(callingUid);
                        if (packagesForUid == null) {
                            Slog.e("NotificationService", "setToastRateLimitingEnabled method haven't found any packages for the  given uid: " + callingUid + ", toast rate limiter not reset for that uid.");
                            return;
                        }
                        for (String str : packagesForUid) {
                            NotificationManagerService.this.mToastRateLimiter.clear(userId, str);
                        }
                    } catch (RemoteException e) {
                        Slog.e("NotificationService", "Failed to reset toast rate limiter for given uid", e);
                    }
                } else {
                    NotificationManagerService.this.mToastRateLimitingDisabledUids.add(Integer.valueOf(callingUid));
                }
            }
        }

        public void finishToken(String str, IBinder iBinder) {
            synchronized (NotificationManagerService.this.mToastQueue) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int indexOfToastLocked = NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                    if (indexOfToastLocked >= 0) {
                        ToastRecord toastRecord = (ToastRecord) NotificationManagerService.this.mToastQueue.get(indexOfToastLocked);
                        NotificationManagerService.this.finishWindowTokenLocked(toastRecord.windowToken, toastRecord.displayId);
                    } else {
                        Slog.w("NotificationService", "Toast already killed. pkg=" + str + " token=" + iBinder);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) {
            if (NmRune.NM_SUPPORT_PUSH_SERVICE && NotificationManagerService.this.checkCallerIsPushService(str, str2)) {
                try {
                    NotificationManagerService.this.enqueueNotificationInternal(str, str, NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i2), Binder.getCallingPid(), str3, i, notification, i2, false);
                    return;
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e("NotificationService", "Cannot get a uid for target package", e);
                    return;
                }
            }
            NotificationManagerService.this.enqueueNotificationInternal(str, str2, Binder.getCallingUid(), Binder.getCallingPid(), str3, i, notification, i2, false);
        }

        public void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) {
            NotificationManagerService.this.cancelNotificationInternal(str, str2, Binder.getCallingUid(), Binder.getCallingPid(), str3, i, i2);
        }

        public void cancelAllNotifications(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            NotificationManagerService.this.cancelAllNotificationsInt(Binder.getCallingUid(), Binder.getCallingPid(), str, null, 0, 32832, true, ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, false, "cancelAllNotifications", str), 9, null);
        }

        public void silenceNotificationSound() {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mNotificationDelegate.clearEffects();
        }

        public void setNotificationsEnabledForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setNotificationsEnabledForPackage");
            if (NotificationManagerService.this.mPermissionHelper.hasPermission(i) == z) {
                return;
            }
            NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, UserHandle.getUserId(i), z, true);
            NotificationManagerService.this.sendAppBlockStateChangedBroadcast(str, i, !z);
            NotificationManagerService.this.mMetricsLogger.write(new LogMaker(147).setType(4).setPackageName(str).setSubtype(z ? 1 : 0));
            NotificationManagerService.this.mNotificationChannelLogger.logAppNotificationsAllowed(i, str, z);
            if (!z) {
                NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, str, null, 0, 0, true, UserHandle.getUserId(i), 7, null);
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.makeNotiPermissonHistory(notificationManagerService.isCallingUidSystem() ? 1 : 0, str, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) {
            setNotificationsEnabledForPackage(str, i, z);
        }

        public boolean areNotificationsEnabled(String str) {
            return areNotificationsEnabledForPackage(str, Binder.getCallingUid());
        }

        public boolean areNotificationsEnabledForPackage(String str, int i) {
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            if (!NotificationManagerService.this.isCallingUidSystem() && UserHandle.getCallingUserId() != UserHandle.getUserId(i)) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "canNotifyAsPackage for uid " + i);
            }
            return NotificationManagerService.this.areNotificationsEnabledForPackageInt(str, i);
        }

        public boolean areBubblesAllowed(String str) {
            return getBubblePreferenceForPackage(str, Binder.getCallingUid()) == 1;
        }

        public boolean areBubblesEnabled(UserHandle userHandle) {
            if (UserHandle.getCallingUserId() != userHandle.getIdentifier()) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "areBubblesEnabled for user " + userHandle.getIdentifier());
            }
            return NotificationManagerService.this.mPreferencesHelper.bubblesEnabled(userHandle);
        }

        public int getBubblePreferenceForPackage(String str, int i) {
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            if (UserHandle.getCallingUserId() != UserHandle.getUserId(i)) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "getBubblePreferenceForPackage for uid " + i);
            }
            return NotificationManagerService.this.mPreferencesHelper.getBubblePreference(str, i);
        }

        public void setBubblesAllowed(String str, int i, int i2) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            NotificationManagerService.this.mPreferencesHelper.setBubblesAllowed(str, i, i2);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean shouldHideSilentStatusIcons(String str) {
            NotificationManagerService.this.checkCallerIsSameApp(str);
            if (NotificationManagerService.this.isCallerSystemOrPhone() || NotificationManagerService.this.mListeners.isListenerPackage(str)) {
                return NotificationManagerService.this.mPreferencesHelper.shouldHideSilentStatusIcons();
            }
            throw new SecurityException("Only available for notification listeners");
        }

        public void setHideSilentStatusIcons(boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.setHideSilentStatusIcons(z);
            NotificationManagerService.this.handleSavePolicyFile();
            NotificationManagerService.this.mListeners.onStatusBarIconsBehaviorChanged(z);
        }

        public void deleteNotificationHistoryItem(String str, int i, long j) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mHistoryManager.deleteNotificationHistoryItem(str, i, j);
        }

        public NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.getNotificationListenerFilter(Pair.create(componentName, Integer.valueOf(i)));
        }

        public void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mListeners.setNotificationListenerFilter(Pair.create(componentName, Integer.valueOf(i)), notificationListenerFilter);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public int getPackageImportance(String str) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return NotificationManagerService.this.mPermissionHelper.hasPermission(Binder.getCallingUid()) ? 3 : 0;
        }

        public boolean isImportanceLocked(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.isImportanceLocked(str, i);
        }

        public boolean canShowBadge(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.canShowBadge(str, i);
        }

        public void setShowBadge(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.setShowBadge(str, i, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean hasSentValidMsg(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasSentValidMsg(str, i);
        }

        public boolean isInInvalidMsgState(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.isInInvalidMsgState(str, i);
        }

        public boolean hasUserDemotedInvalidMsgApp(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, i);
        }

        public void setInvalidMsgAppDemoted(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.setInvalidMsgAppDemoted(str, i, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean hasSentValidBubble(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasSentValidBubble(str, i);
        }

        public void setNotificationDelegate(String str, String str2) {
            NotificationManagerService.this.checkCallerIsSameApp(str);
            int callingUid = Binder.getCallingUid();
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(callingUid);
            if (str2 == null) {
                NotificationManagerService.this.mPreferencesHelper.revokeNotificationDelegate(str, Binder.getCallingUid());
                NotificationManagerService.this.handleSavePolicyFile();
                return;
            }
            try {
                ApplicationInfo applicationInfo = NotificationManagerService.this.mPackageManager.getApplicationInfo(str2, 786432L, userHandleForUid.getIdentifier());
                if (applicationInfo != null) {
                    NotificationManagerService.this.mPreferencesHelper.setNotificationDelegate(str, callingUid, str2, applicationInfo.uid);
                    NotificationManagerService.this.handleSavePolicyFile();
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public String getNotificationDelegate(String str) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationDelegate(str, Binder.getCallingUid());
        }

        public boolean canNotifyAsPackage(String str, String str2, int i) {
            NotificationManagerService.this.checkCallerIsSameApp(str);
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getUserHandleForUid(callingUid).getIdentifier() != i) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "canNotifyAsPackage for user " + i);
            }
            if (str.equals(str2)) {
                return true;
            }
            try {
                ApplicationInfo applicationInfo = NotificationManagerService.this.mPackageManager.getApplicationInfo(str2, 786432L, i);
                if (applicationInfo != null) {
                    return NotificationManagerService.this.mPreferencesHelper.isDelegateAllowed(str2, applicationInfo.uid, str, callingUid);
                }
                return false;
            } catch (RemoteException unused) {
                return false;
            }
        }

        public boolean canUseFullScreenIntent(AttributionSource attributionSource) {
            String packageName = attributionSource.getPackageName();
            int uid = attributionSource.getUid();
            int userId = UserHandle.getUserId(uid);
            NotificationManagerService.this.checkCallerIsSameApp(packageName, uid, userId);
            try {
                return NotificationManagerService.this.checkUseFullScreenIntentPermission(attributionSource, NotificationManagerService.this.mPackageManagerClient.getApplicationInfoAsUser(packageName, 268435456, userId), NotificationManagerService.this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.SHOW_STICKY_HUN_FOR_DENIED_FSI), false);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("NotificationService", "Failed to getApplicationInfo() in canUseFullScreenIntent()", e);
                return false;
            }
        }

        public void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) {
            enforceSystemOrSystemUI("Caller not system or systemui");
            NotificationManagerService.this.createNotificationChannelGroup(str, i, notificationChannelGroup, false, false);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            List list = parceledListSlice.getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationManagerService.this.createNotificationChannelGroup(str, Binder.getCallingUid(), (NotificationChannelGroup) list.get(i), true, false);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void createNotificationChannelsImpl(String str, int i, ParceledListSlice parceledListSlice) {
            createNotificationChannelsImpl(str, i, parceledListSlice, -1);
        }

        public final void createNotificationChannelsImpl(String str, int i, ParceledListSlice parceledListSlice, int i2) {
            List list = parceledListSlice.getList();
            int size = list.size();
            ParceledListSlice notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, true);
            boolean z = (notificationChannels == null || notificationChannels.getList().isEmpty()) ? false : true;
            boolean z2 = false;
            int i3 = 0;
            boolean z3 = false;
            while (i3 < size) {
                NotificationChannel notificationChannel = (NotificationChannel) list.get(i3);
                Objects.requireNonNull(notificationChannel, "channel in list is null");
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                int i4 = i3;
                z2 = notificationManagerService.mPreferencesHelper.createNotificationChannel(str, i, notificationChannel, true, notificationManagerService.mConditionProviders.isPackageOrComponentAllowed(str, UserHandle.getUserId(i)), Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
                if (z2) {
                    NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, UserHandle.getUserHandleForUid(i), NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), false), 1);
                    boolean z4 = z || z3;
                    if (!z4) {
                        ParceledListSlice notificationChannels2 = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, true);
                        z4 = (notificationChannels2 == null || notificationChannels2.getList().isEmpty()) ? false : true;
                    }
                    if (!z && z4 && !z3 && i2 != -1) {
                        if (NotificationManagerService.this.mPermissionPolicyInternal == null) {
                            NotificationManagerService.this.mPermissionPolicyInternal = (PermissionPolicyInternal) LocalServices.getService(PermissionPolicyInternal.class);
                        }
                        NotificationManagerService.this.mHandler.post(new ShowNotificationPermissionPromptRunnable(str, UserHandle.getUserId(i), i2, NotificationManagerService.this.mPermissionPolicyInternal));
                        z3 = true;
                    }
                }
                i3 = i4 + 1;
            }
            if (z2) {
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public void createNotificationChannels(String str, ParceledListSlice parceledListSlice) {
            int i;
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            try {
                i = NotificationManagerService.this.mAtm.getTaskToShowPermissionDialogOn(str, NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, UserHandle.getUserId(Binder.getCallingUid())));
            } catch (RemoteException unused) {
                i = -1;
            }
            createNotificationChannelsImpl(str, Binder.getCallingUid(), parceledListSlice, i);
        }

        public void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) {
            enforceSystemOrSystemUI("only system can call this");
            createNotificationChannelsImpl(str, i, parceledListSlice);
        }

        public void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) {
            enforceSystemOrSystemUI("only system can call this");
            Preconditions.checkNotNull(notificationChannel);
            Preconditions.checkNotNull(str2);
            String id = notificationChannel.getId();
            notificationChannel.setId(String.format("%1$s : %2$s", id, str2));
            notificationChannel.setConversationId(id, str2);
            createNotificationChannelsImpl(str, i, new ParceledListSlice(Arrays.asList(notificationChannel)));
            NotificationManagerService.this.mRankingHandler.requestSort();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) {
            return getConversationNotificationChannel(str, i, str2, str3, true, null);
        }

        public NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) {
            int i2;
            if (canNotifyAsPackage(str, str2, i) || NotificationManagerService.this.isCallerIsSystemOrSysemUiOrShell()) {
                try {
                    i2 = NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    i2 = -1;
                }
                return NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str2, i2, str3, str4, z, false);
            }
            throw new SecurityException("Pkg " + str + " cannot read channels for " + str2 + " in " + i);
        }

        public NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str, i, str2, str3, true, z);
        }

        public final void enforceDeletingChannelHasNoFgService(String str, int i, String str2) {
            if (NotificationManagerService.this.mAmi.hasForegroundServiceNotification(str, i, str2)) {
                Slog.w("NotificationService", "Package u" + i + "/" + str + " may not delete notification channel '" + str2 + "' with fg service");
                throw new SecurityException("Not allowed to delete channel " + str2 + " with a foreground service");
            }
        }

        public final void enforceDeletingChannelHasNoUserInitiatedJob(String str, int i, String str2) {
            JobSchedulerInternal jobSchedulerInternal = (JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class);
            if (jobSchedulerInternal == null || !jobSchedulerInternal.isNotificationChannelAssociatedWithAnyUserInitiatedJobs(str2, i, str)) {
                return;
            }
            Slog.w("NotificationService", "Package u" + i + "/" + str + " may not delete notification channel '" + str2 + "' with user-initiated job");
            throw new SecurityException("Not allowed to delete channel " + str2 + " with a user-initiated job");
        }

        public void deleteNotificationChannel(String str, String str2) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            int userId = UserHandle.getUserId(callingUid);
            if ("miscellaneous".equals(str2)) {
                throw new IllegalArgumentException("Cannot delete default channel");
            }
            enforceDeletingChannelHasNoFgService(str, userId, str2);
            enforceDeletingChannelHasNoUserInitiatedJob(str, userId, str2);
            NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, str, str2, 0, 0, true, userId, 20, null);
            if (NotificationManagerService.this.mPreferencesHelper.deleteNotificationChannel(str, callingUid, str2, callingUid, isCallerIsSystemOrSystemUi)) {
                NotificationManagerService.this.mArchive.removeChannelNotifications(str, userId, str2);
                NotificationManagerService.this.mHistoryManager.deleteNotificationChannel(str, callingUid, str2);
                NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, UserHandle.getUserHandleForUid(callingUid), NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, callingUid, str2, true), 3);
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public NotificationChannelGroup getNotificationChannelGroup(String str, String str2) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, Binder.getCallingUid(), str2, false);
        }

        public ParceledListSlice getNotificationChannelGroups(String str) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, Binder.getCallingUid(), false, false, true);
        }

        public void deleteNotificationChannelGroup(String str, String str2) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            NotificationChannelGroup notificationChannelGroupWithChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, callingUid, str2, false);
            if (notificationChannelGroupWithChannels != null) {
                int userId = UserHandle.getUserId(callingUid);
                List<NotificationChannel> channels = notificationChannelGroupWithChannels.getChannels();
                for (int i = 0; i < channels.size(); i++) {
                    String id = channels.get(i).getId();
                    enforceDeletingChannelHasNoFgService(str, userId, id);
                    enforceDeletingChannelHasNoUserInitiatedJob(str, userId, id);
                }
                int i2 = 0;
                for (List deleteNotificationChannelGroup = NotificationManagerService.this.mPreferencesHelper.deleteNotificationChannelGroup(str, callingUid, str2, callingUid, isCallerIsSystemOrSystemUi); i2 < deleteNotificationChannelGroup.size(); deleteNotificationChannelGroup = deleteNotificationChannelGroup) {
                    NotificationChannel notificationChannel = (NotificationChannel) deleteNotificationChannelGroup.get(i2);
                    NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, str, notificationChannel.getId(), 0, 0, true, userId, 20, null);
                    NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, UserHandle.getUserHandleForUid(callingUid), notificationChannel, 3);
                    i2++;
                }
                NotificationManagerService.this.mListeners.notifyNotificationChannelGroupChanged(str, UserHandle.getUserHandleForUid(callingUid), notificationChannelGroupWithChannels, 3);
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            Objects.requireNonNull(notificationChannel);
            NotificationManagerService.this.updateNotificationChannelInt(str, i, notificationChannel, false);
        }

        public void unlockNotificationChannel(String str, int i, String str2) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            NotificationManagerService.this.mPreferencesHelper.unlockNotificationChannelImportance(str, i, str2);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public void unlockAllNotificationChannels() {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.unlockAllNotificationChannels();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelsForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, z);
        }

        public int getNumNotificationChannelsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNumNotificationChannelsForPackage");
            return NotificationManagerService.this.getNumNotificationChannelsForPackage(str, i, z);
        }

        public boolean onlyHasDefaultChannel(String str, int i) {
            enforceSystemOrSystemUI("onlyHasDefaultChannel");
            return NotificationManagerService.this.mPreferencesHelper.onlyHasDefaultChannel(str, i);
        }

        public int getDeletedChannelCount(String str, int i) {
            enforceSystemOrSystemUI("getDeletedChannelCount");
            return NotificationManagerService.this.mPreferencesHelper.getDeletedChannelCount(str, i);
        }

        public int getBlockedChannelCount(String str, int i) {
            enforceSystemOrSystemUI("getBlockedChannelCount");
            return NotificationManagerService.this.mPreferencesHelper.getBlockedChannelCount(str, i);
        }

        public ParceledListSlice getConversations(boolean z) {
            enforceSystemOrSystemUI("getConversations");
            ArrayList conversations = NotificationManagerService.this.mPreferencesHelper.getConversations(NotificationManagerService.this.mUserProfiles.getCurrentProfileIds(), z);
            Iterator it = conversations.iterator();
            while (it.hasNext()) {
                ConversationChannelWrapper conversationChannelWrapper = (ConversationChannelWrapper) it.next();
                if (NotificationManagerService.this.mShortcutHelper == null) {
                    conversationChannelWrapper.setShortcutInfo((ShortcutInfo) null);
                } else {
                    conversationChannelWrapper.setShortcutInfo(NotificationManagerService.this.mShortcutHelper.getValidShortcutInfo(conversationChannelWrapper.getNotificationChannel().getConversationId(), conversationChannelWrapper.getPkg(), UserHandle.of(UserHandle.getUserId(conversationChannelWrapper.getUid()))));
                }
            }
            return new ParceledListSlice(conversations);
        }

        public ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelGroupsForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, i, z, true, false);
        }

        public ParceledListSlice getConversationsForPackage(String str, int i) {
            enforceSystemOrSystemUI("getConversationsForPackage");
            ArrayList conversations = NotificationManagerService.this.mPreferencesHelper.getConversations(str, i);
            Iterator it = conversations.iterator();
            while (it.hasNext()) {
                ConversationChannelWrapper conversationChannelWrapper = (ConversationChannelWrapper) it.next();
                if (NotificationManagerService.this.mShortcutHelper == null) {
                    conversationChannelWrapper.setShortcutInfo((ShortcutInfo) null);
                } else {
                    conversationChannelWrapper.setShortcutInfo(NotificationManagerService.this.mShortcutHelper.getValidShortcutInfo(conversationChannelWrapper.getNotificationChannel().getConversationId(), str, UserHandle.of(UserHandle.getUserId(i))));
                }
            }
            return new ParceledListSlice(conversations);
        }

        public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) {
            enforceSystemOrSystemUI("getPopulatedNotificationChannelGroupForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, i, str2, z);
        }

        public NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) {
            enforceSystemOrSystemUI("getNotificationChannelGroupForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroup(str, str2, i);
        }

        public ParceledListSlice getNotificationChannels(String str, String str2, int i) {
            int i2;
            if (canNotifyAsPackage(str, str2, i) || NotificationManagerService.this.isCallingUidSystem()) {
                try {
                    i2 = NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    i2 = -1;
                }
                return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str2, i2, false);
            }
            throw new SecurityException("Pkg " + str + " cannot read channels for " + str2 + " in " + i);
        }

        public ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            if (!areNotificationsEnabledForPackage(str, i)) {
                return ParceledListSlice.emptyList();
            }
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelsBypassingDnd(str, i);
        }

        public boolean areChannelsBypassingDnd() {
            return NotificationManagerService.this.mPreferencesHelper.areChannelsBypassingDnd();
        }

        public void clearData(String str, int i, boolean z) {
            Boolean bool;
            Boolean bool2;
            NotificationManagerService.this.checkCallerIsSystem();
            int userId = UserHandle.getUserId(i);
            NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, str, null, 0, 0, true, UserHandle.getUserId(Binder.getCallingUid()), 21, null);
            boolean resetPackage = NotificationManagerService.this.mConditionProviders.resetPackage(str, userId) | false;
            ArrayMap resetComponents = NotificationManagerService.this.mListeners.resetComponents(str, userId);
            boolean z2 = resetPackage | (((ArrayList) resetComponents.get(Boolean.TRUE)).size() > 0 || ((ArrayList) resetComponents.get(Boolean.FALSE)).size() > 0);
            int i2 = 0;
            while (true) {
                bool = Boolean.TRUE;
                if (i2 >= ((ArrayList) resetComponents.get(bool)).size()) {
                    break;
                }
                NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(((ComponentName) ((ArrayList) resetComponents.get(bool)).get(i2)).getPackageName(), userId, false, true);
                i2++;
            }
            ArrayMap resetComponents2 = NotificationManagerService.this.mAssistants.resetComponents(str, userId);
            boolean z3 = z2 | (((ArrayList) resetComponents2.get(bool)).size() > 0 || ((ArrayList) resetComponents2.get(Boolean.FALSE)).size() > 0);
            int i3 = 1;
            while (true) {
                bool2 = Boolean.TRUE;
                if (i3 >= ((ArrayList) resetComponents2.get(bool2)).size()) {
                    break;
                }
                NotificationManagerService.this.mAssistants.setPackageOrComponentEnabled(((ComponentName) ((ArrayList) resetComponents2.get(bool2)).get(i3)).flattenToString(), userId, true, false);
                i3++;
            }
            if (((ArrayList) resetComponents2.get(bool2)).size() > 0) {
                NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(((ComponentName) ((ArrayList) resetComponents2.get(bool2)).get(0)).getPackageName(), userId, false, true);
            }
            NotificationManagerService.this.mSnoozeHelper.clearData(UserHandle.getUserId(i), str);
            if (!z) {
                NotificationManagerService.this.mPreferencesHelper.clearData(str, i);
            }
            if (z3) {
                NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), UserHandle.of(userId), null);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public List getAllowedAssistantAdjustments(String str) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            if (!NotificationManagerService.this.isCallerSystemOrPhone() && !NotificationManagerService.this.mAssistants.isPackageAllowed(str, UserHandle.getCallingUserId())) {
                throw new SecurityException("Not currently an assistant");
            }
            return NotificationManagerService.this.mAssistants.getAllowedAssistantAdjustments();
        }

        public StatusBarNotification[] getActiveNotifications(String str) {
            return getActiveNotificationsWithAttribution(str, null);
        }

        public StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) {
            NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_NOTIFICATIONS", "NotificationManagerService.getActiveNotifications");
            ArrayList arrayList = new ArrayList();
            int callingUid = Binder.getCallingUid();
            final ArrayList arrayList2 = new ArrayList();
            arrayList2.add(-1);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$15$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    NotificationManagerService.INotificationManagerStubC199315.this.lambda$getActiveNotificationsWithAttribution$2(arrayList2);
                }
            });
            if (NotificationManagerService.this.mAppOps.noteOpNoThrow(25, callingUid, str, str2, (String) null) == 0) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    int size = NotificationManagerService.this.mNotificationList.size();
                    for (int i = 0; i < size; i++) {
                        StatusBarNotification sbn = ((NotificationRecord) NotificationManagerService.this.mNotificationList.get(i)).getSbn();
                        if (arrayList2.contains(Integer.valueOf(sbn.getUserId()))) {
                            arrayList.add(sbn);
                        }
                    }
                }
            }
            return (StatusBarNotification[]) arrayList.toArray(new StatusBarNotification[arrayList.size()]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getActiveNotificationsWithAttribution$2(ArrayList arrayList) {
            for (int i : NotificationManagerService.this.mUm.getProfileIds(ActivityManager.getCurrentUser(), false)) {
                arrayList.add(Integer.valueOf(i));
            }
        }

        public ParceledListSlice getAppActiveNotifications(String str, int i) {
            ParceledListSlice parceledListSlice;
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, false, "getAppActiveNotifications", str);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ArrayMap arrayMap = new ArrayMap(NotificationManagerService.this.mNotificationList.size() + NotificationManagerService.this.mEnqueuedNotifications.size());
                int size = NotificationManagerService.this.mNotificationList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    StatusBarNotification sanitizeSbn = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) NotificationManagerService.this.mNotificationList.get(i2)).getSbn());
                    if (sanitizeSbn != null) {
                        arrayMap.put(sanitizeSbn.getKey(), sanitizeSbn);
                    }
                }
                Iterator it = NotificationManagerService.this.mSnoozeHelper.getSnoozed(handleIncomingUser, str).iterator();
                while (it.hasNext()) {
                    StatusBarNotification sanitizeSbn2 = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) it.next()).getSbn());
                    if (sanitizeSbn2 != null) {
                        arrayMap.put(sanitizeSbn2.getKey(), sanitizeSbn2);
                    }
                }
                int size2 = NotificationManagerService.this.mEnqueuedNotifications.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    StatusBarNotification sanitizeSbn3 = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i3)).getSbn());
                    if (sanitizeSbn3 != null) {
                        arrayMap.put(sanitizeSbn3.getKey(), sanitizeSbn3);
                    }
                }
                ArrayList arrayList = new ArrayList(arrayMap.size());
                arrayList.addAll(arrayMap.values());
                parceledListSlice = new ParceledListSlice(arrayList);
            }
            return parceledListSlice;
        }

        public final StatusBarNotification sanitizeSbn(String str, int i, StatusBarNotification statusBarNotification) {
            if (statusBarNotification.getUserId() != i) {
                return null;
            }
            if (!statusBarNotification.getPackageName().equals(str) && !statusBarNotification.getOpPkg().equals(str)) {
                return null;
            }
            Notification clone = statusBarNotification.getNotification().clone();
            clone.clearAllowlistToken();
            return new StatusBarNotification(statusBarNotification.getPackageName(), statusBarNotification.getOpPkg(), statusBarNotification.getId(), statusBarNotification.getTag(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), clone, statusBarNotification.getUser(), statusBarNotification.getOverrideGroupKey(), statusBarNotification.getPostTime());
        }

        public StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) {
            return getHistoricalNotificationsWithAttribution(str, null, i, z);
        }

        public StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) {
            StatusBarNotification[] array;
            NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_NOTIFICATIONS", "NotificationManagerService.getHistoricalNotifications");
            if (NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null) != 0) {
                return null;
            }
            synchronized (NotificationManagerService.this.mArchive) {
                array = NotificationManagerService.this.mArchive.getArray(NotificationManagerService.this.mUm, i, z);
            }
            return array;
        }

        public NotificationHistory getNotificationHistory(String str, String str2) {
            NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_NOTIFICATIONS", "NotificationManagerService.getNotificationHistory");
            if (NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null) == 0) {
                IntArray currentProfileIds = NotificationManagerService.this.mUserProfiles.getCurrentProfileIds();
                Trace.traceBegin(524288L, "notifHistoryReadHistory");
                try {
                    return NotificationManagerService.this.mHistoryManager.readNotificationHistory(currentProfileIds.toArray());
                } finally {
                    Trace.traceEnd(524288L);
                }
            }
            return new NotificationHistory();
        }

        public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) {
            enforceSystemOrSystemUI("INotificationManager.registerListener");
            NotificationManagerService.this.mListeners.registerSystemService(iNotificationListener, componentName, i, Binder.getCallingUid());
        }

        public void unregisterListener(INotificationListener iNotificationListener, int i) {
            NotificationManagerService.this.mListeners.unregisterService((IInterface) iNotificationListener, i);
        }

        public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) {
            int userId;
            int i;
            int i2;
            ManagedServices.ManagedServiceInfo managedServiceInfo;
            String[] strArr2 = strArr;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    int i3 = NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(iNotificationListener) ? 22 : 10;
                    if (strArr2 != null) {
                        int length = strArr2.length;
                        int i4 = 0;
                        while (i4 < length) {
                            NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(strArr2[i4]);
                            if (notificationRecord != null && ((userId = notificationRecord.getSbn().getUserId()) == checkServiceTokenLocked.userid || userId == -1 || NotificationManagerService.this.mUserProfiles.isCurrentProfile(userId))) {
                                i = i4;
                                i2 = length;
                                managedServiceInfo = checkServiceTokenLocked;
                                cancelNotificationFromListenerLocked(checkServiceTokenLocked, callingUid, callingPid, notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), userId, i3);
                                i4 = i + 1;
                                checkServiceTokenLocked = managedServiceInfo;
                                length = i2;
                                strArr2 = strArr;
                            }
                            i = i4;
                            i2 = length;
                            managedServiceInfo = checkServiceTokenLocked;
                            i4 = i + 1;
                            checkServiceTokenLocked = managedServiceInfo;
                            length = i2;
                            strArr2 = strArr;
                        }
                    } else {
                        NotificationManagerService.this.cancelAllLocked(callingUid, callingPid, checkServiceTokenLocked.userid, 11, checkServiceTokenLocked, checkServiceTokenLocked.supportsProfiles());
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBindListener(ComponentName componentName) {
            ManagedServices managedServices;
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (NotificationManagerService.this.mAssistants.isComponentEnabledForCurrentProfiles(componentName)) {
                    managedServices = NotificationManagerService.this.mAssistants;
                } else {
                    managedServices = NotificationManagerService.this.mListeners;
                }
                managedServices.setComponentState(componentName, UserHandle.getUserId(callingUid), true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestUnbindListener(INotificationListener iNotificationListener) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    checkServiceTokenLocked.getOwner().setComponentState(checkServiceTokenLocked.component, UserHandle.getUserId(callingUid), false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestUnbindListenerComponent(ComponentName componentName) {
            ManagedServices managedServices;
            NotificationManagerService.this.checkCallerIsSameApp(componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    if (NotificationManagerService.this.mAssistants.isComponentEnabledForCurrentProfiles(componentName)) {
                        managedServices = NotificationManagerService.this.mAssistants;
                    } else {
                        managedServices = NotificationManagerService.this.mListeners;
                    }
                    if (managedServices.isPackageOrComponentAllowed(componentName.flattenToString(), UserHandle.getUserId(callingUid))) {
                        managedServices.setComponentState(componentName, UserHandle.getUserId(callingUid), false);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) {
            int userId;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if (strArr == null) {
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(strArr[i]);
                        if (notificationRecord != null && ((userId = notificationRecord.getSbn().getUserId()) == checkServiceTokenLocked.userid || userId == -1 || NotificationManagerService.this.mUserProfiles.isCurrentProfile(userId))) {
                            arrayList.add(notificationRecord);
                            if (!notificationRecord.isSeen()) {
                                if (NotificationManagerService.DBG) {
                                    Slog.d("NotificationService", "Marking notification as seen " + strArr[i]);
                                }
                                NotificationManagerService.this.reportSeen(notificationRecord);
                                notificationRecord.setSeen();
                                NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        NotificationManagerService.this.mAssistants.onNotificationsSeenLocked(arrayList);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void cancelNotificationFromListenerLocked(ManagedServices.ManagedServiceInfo managedServiceInfo, int i, int i2, String str, String str2, int i3, int i4, int i5) {
            NotificationManagerService.this.cancelNotification(i, i2, str, str2, i3, 0, 2, true, i4, i5, managedServiceInfo);
        }

        public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.snoozeNotificationInt(str, -1L, str2, NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.snoozeNotificationInt(str, j, null, NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.unsnoozeNotificationInt(str, NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener), false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if (!checkServiceTokenLocked.isSystem) {
                        throw new SecurityException("Not allowed to unsnooze before deadline");
                    }
                    NotificationManagerService.this.unsnoozeNotificationInt(str, checkServiceTokenLocked, true);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List list) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    Pair create = Pair.create(checkServiceTokenLocked.component, Integer.valueOf(checkServiceTokenLocked.userid));
                    NotificationListenerFilter notificationListenerFilter = NotificationManagerService.this.mListeners.getNotificationListenerFilter(create);
                    if (notificationListenerFilter == null) {
                        notificationListenerFilter = new NotificationListenerFilter();
                    }
                    if (notificationListenerFilter.getDisallowedPackages().isEmpty() && list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            for (int i2 : NotificationManagerService.this.mUm.getProfileIds(checkServiceTokenLocked.userid, false)) {
                                try {
                                    int uidForPackageAndUser = getUidForPackageAndUser(str, UserHandle.of(i2));
                                    if (uidForPackageAndUser != -1) {
                                        notificationListenerFilter.addPackage(new VersionedPackage(str, uidForPackageAndUser));
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                    if (notificationListenerFilter.areAllTypesAllowed()) {
                        notificationListenerFilter.setTypes(i);
                    }
                    NotificationManagerService.this.mListeners.setNotificationListenerFilter(create, notificationListenerFilter);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    int i2 = NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(iNotificationListener) ? 22 : 10;
                    if (checkServiceTokenLocked.supportsProfiles()) {
                        Slog.e("NotificationService", "Ignoring deprecated cancelNotification(pkg, tag, id) from " + checkServiceTokenLocked.component + " use cancelNotification(key) instead.");
                    } else {
                        cancelNotificationFromListenerLocked(checkServiceTokenLocked, callingUid, callingPid, str, str2, i, checkServiceTokenLocked.userid, i2);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) {
            ParceledListSlice parceledListSlice;
            NotificationRecord notificationRecord;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                boolean z = strArr != null;
                int length = z ? strArr.length : NotificationManagerService.this.mNotificationList.size();
                ArrayList arrayList = new ArrayList(length);
                for (int i2 = 0; i2 < length; i2++) {
                    if (z) {
                        notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(strArr[i2]);
                    } else {
                        notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationList.get(i2);
                    }
                    if (notificationRecord != null) {
                        StatusBarNotification sbn = notificationRecord.getSbn();
                        if (NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), checkServiceTokenLocked)) {
                            if (i != 0) {
                                sbn = sbn.cloneLight();
                            }
                            arrayList.add(sbn);
                        }
                    }
                }
                parceledListSlice = new ParceledListSlice(arrayList);
            }
            return parceledListSlice;
        }

        public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) {
            ParceledListSlice parceledListSlice;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                List snoozed = NotificationManagerService.this.mSnoozeHelper.getSnoozed();
                int size = snoozed.size();
                ArrayList arrayList = new ArrayList(size);
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationRecord notificationRecord = (NotificationRecord) snoozed.get(i2);
                    if (notificationRecord != null) {
                        StatusBarNotification sbn = notificationRecord.getSbn();
                        if (NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), checkServiceTokenLocked)) {
                            if (i != 0) {
                                sbn = sbn.cloneLight();
                            }
                            arrayList.add(sbn);
                        }
                    }
                }
                parceledListSlice = new ParceledListSlice(arrayList);
            }
            return parceledListSlice;
        }

        public void clearRequestedListenerHints(INotificationListener iNotificationListener) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.removeDisabledHints(NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener));
                    NotificationManagerService.this.updateListenerHintsLocked();
                    NotificationManagerService.this.updateEffectsSuppressorLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestHintsFromListener(INotificationListener iNotificationListener, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if ((i & 7) != 0) {
                        NotificationManagerService.this.addDisabledHints(checkServiceTokenLocked, i);
                    } else {
                        NotificationManagerService.this.removeDisabledHints(checkServiceTokenLocked, i);
                    }
                    NotificationManagerService.this.updateListenerHintsLocked();
                    NotificationManagerService.this.updateEffectsSuppressorLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getHintsFromListener(INotificationListener iNotificationListener) {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public int getHintsFromListenerNoToken() {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) {
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.mZenModeHelper.requestFromListener(NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener).component, i, callingUid, isCallerIsSystemOrSystemUi);
                    NotificationManagerService.this.updateInterruptionFilterLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mInterruptionFilter;
            }
            return i;
        }

        public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                if (checkServiceTokenLocked == null) {
                    return;
                }
                NotificationManagerService.this.mListeners.setOnNotificationPostedTrimLocked(checkServiceTokenLocked, i);
            }
        }

        public int getZenMode() {
            return NotificationManagerService.this.mZenModeHelper.getZenMode();
        }

        public ZenModeConfig getZenModeConfig() {
            enforceSystemOrSystemUI("INotificationManager.getZenModeConfig");
            return NotificationManagerService.this.mZenModeHelper.getConfig();
        }

        public void setZenMode(int i, Uri uri, String str) {
            enforceSystemOrSystemUI("INotificationManager.setZenMode");
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            String nameForUid = NotificationManagerService.this.getContext().getPackageManager().getNameForUid(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mZenModeHelper.setManualZenMode(i, uri, null, "(pkg-" + nameForUid + ")" + str, callingUid, isCallerIsSystemOrSystemUi);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getZenRules() {
            enforcePolicyAccess(Binder.getCallingUid(), "getAutomaticZenRules");
            return NotificationManagerService.this.mZenModeHelper.getZenRules();
        }

        public AutomaticZenRule getAutomaticZenRule(String str) {
            Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(Binder.getCallingUid(), "getAutomaticZenRule");
            return NotificationManagerService.this.mZenModeHelper.getAutomaticZenRule(str);
        }

        public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str) {
            Objects.requireNonNull(automaticZenRule, "automaticZenRule is null");
            Objects.requireNonNull(automaticZenRule.getName(), "Name is null");
            if (automaticZenRule.getOwner() == null && automaticZenRule.getConfigurationActivity() == null) {
                throw new NullPointerException("Rule must have a conditionproviderservice and/or configuration activity");
            }
            Objects.requireNonNull(automaticZenRule.getConditionId(), "ConditionId is null");
            NotificationManagerService.this.checkCallerIsSameApp(str);
            if (automaticZenRule.getZenPolicy() != null && automaticZenRule.getInterruptionFilter() != 2) {
                throw new IllegalArgumentException("ZenPolicy is only applicable to INTERRUPTION_FILTER_PRIORITY filters");
            }
            enforcePolicyAccess(Binder.getCallingUid(), "addAutomaticZenRule");
            if (NotificationManagerService.this.isCallingAppIdSystem() && automaticZenRule.getOwner() != null) {
                str = automaticZenRule.getOwner().getPackageName();
            }
            return NotificationManagerService.this.mZenModeHelper.addAutomaticZenRule(str, automaticZenRule, "addAutomaticZenRule", Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
        }

        public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule) {
            Objects.requireNonNull(automaticZenRule, "automaticZenRule is null");
            Objects.requireNonNull(automaticZenRule.getName(), "Name is null");
            if (automaticZenRule.getOwner() == null && automaticZenRule.getConfigurationActivity() == null) {
                throw new NullPointerException("Rule must have a conditionproviderservice and/or configuration activity");
            }
            Objects.requireNonNull(automaticZenRule.getConditionId(), "ConditionId is null");
            enforcePolicyAccess(Binder.getCallingUid(), "updateAutomaticZenRule");
            return NotificationManagerService.this.mZenModeHelper.updateAutomaticZenRule(str, automaticZenRule, "updateAutomaticZenRule", Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
        }

        public boolean removeAutomaticZenRule(String str) {
            Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(Binder.getCallingUid(), "removeAutomaticZenRule");
            return NotificationManagerService.this.mZenModeHelper.removeAutomaticZenRule(str, "removeAutomaticZenRule", Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
        }

        public boolean removeAutomaticZenRules(String str) {
            Objects.requireNonNull(str, "Package name is null");
            enforceSystemOrSystemUI("removeAutomaticZenRules");
            return NotificationManagerService.this.mZenModeHelper.removeAutomaticZenRules(str, str + "|removeAutomaticZenRules", Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
        }

        public int getRuleInstanceCount(ComponentName componentName) {
            Objects.requireNonNull(componentName, "Owner is null");
            enforceSystemOrSystemUI("getRuleInstanceCount");
            return NotificationManagerService.this.mZenModeHelper.getCurrentInstanceCount(componentName);
        }

        public void setAutomaticZenRuleState(String str, Condition condition) {
            Objects.requireNonNull(str, "id is null");
            Objects.requireNonNull(condition, "Condition is null");
            enforcePolicyAccess(Binder.getCallingUid(), "setAutomaticZenRuleState");
            NotificationManagerService.this.mZenModeHelper.setAutomaticZenRuleState(str, condition, Binder.getCallingUid(), NotificationManagerService.this.isCallerIsSystemOrSystemUi());
        }

        public void setInterruptionFilter(String str, int i) {
            enforcePolicyAccess(str, "setInterruptionFilter");
            int zenModeFromInterruptionFilter = NotificationManager.zenModeFromInterruptionFilter(i, -1);
            if (zenModeFromInterruptionFilter == -1) {
                throw new IllegalArgumentException("Invalid filter: " + i);
            }
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mZenModeHelper.setManualZenMode(zenModeFromInterruptionFilter, null, str, "(pkg-" + str + ")setInterruptionFilter", callingUid, isCallerIsSystemOrSystemUi);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyConditions(final String str, IConditionProvider iConditionProvider, final Condition[] conditionArr) {
            final ManagedServices.ManagedServiceInfo checkServiceToken = NotificationManagerService.this.mConditionProviders.checkServiceToken(iConditionProvider);
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.15.2
                @Override // java.lang.Runnable
                public void run() {
                    NotificationManagerService.this.mConditionProviders.notifyConditions(str, checkServiceToken, conditionArr);
                }
            });
        }

        public void requestUnbindProvider(IConditionProvider iConditionProvider) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ManagedServices.ManagedServiceInfo checkServiceToken = NotificationManagerService.this.mConditionProviders.checkServiceToken(iConditionProvider);
                checkServiceToken.getOwner().setComponentState(checkServiceToken.component, UserHandle.getUserId(callingUid), false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBindProvider(ComponentName componentName) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mConditionProviders.setComponentState(componentName, UserHandle.getUserId(callingUid), true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void enforceSystemOrSystemUI(String str) {
            if (NotificationManagerService.this.isCallerSystemOrPhone()) {
                return;
            }
            NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
        }

        public final void enforceSystemOrSystemUIOrSamePackage(String str, String str2) {
            try {
                NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            } catch (SecurityException unused) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str2);
            }
        }

        public final void enforcePolicyAccess(int i, String str) {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
                return;
            }
            boolean z = false;
            for (String str2 : NotificationManagerService.this.mPackageManagerClient.getPackagesForUid(i)) {
                if (NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(str2, UserHandle.getUserId(i))) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            Slog.w("NotificationService", "Notification policy access denied calling " + str);
            throw new SecurityException("Notification policy access denied");
        }

        public final void enforcePolicyAccess(String str, String str2) {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
                return;
            }
            NotificationManagerService.this.checkCallerIsSameApp(str);
            if (checkPolicyAccess(str)) {
                return;
            }
            Slog.w("NotificationService", "Notification policy access denied calling " + str2);
            throw new SecurityException("Notification policy access denied");
        }

        public final boolean checkPackagePolicyAccess(String str) {
            return NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(str, INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public final boolean checkPolicyAccess(String str) {
            try {
                if (ActivityManager.checkComponentPermission("android.permission.MANAGE_NOTIFICATIONS", NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, UserHandle.getCallingUserId()), -1, true) == 0) {
                    return true;
                }
                if (!checkPackagePolicyAccess(str) && !NotificationManagerService.this.mListeners.isComponentEnabledForPackage(str)) {
                    if (NotificationManagerService.this.mDpm == null) {
                        return false;
                    }
                    if (!NotificationManagerService.this.mDpm.isActiveProfileOwner(Binder.getCallingUid()) && !NotificationManagerService.this.mDpm.isActiveDeviceOwner(Binder.getCallingUid())) {
                        return false;
                    }
                }
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(NotificationManagerService.this.getContext(), "NotificationService", printWriter)) {
                DumpFilter parseFromArguments = DumpFilter.parseFromArguments(strArr);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ArrayMap allUsersNotificationPermissions = NotificationManagerService.this.getAllUsersNotificationPermissions();
                    if (parseFromArguments.stats) {
                        NotificationManagerService.this.dumpJson(printWriter, parseFromArguments, allUsersNotificationPermissions);
                    } else if (parseFromArguments.rvStats) {
                        NotificationManagerService.this.dumpRemoteViewStats(printWriter, parseFromArguments);
                    } else if (parseFromArguments.proto) {
                        NotificationManagerService.this.dumpProto(fileDescriptor, parseFromArguments, allUsersNotificationPermissions);
                    } else if (parseFromArguments.criticalPriority) {
                        NotificationManagerService.this.dumpNotificationRecords(printWriter, parseFromArguments);
                    } else {
                        NotificationManagerService.this.dumpImpl(printWriter, parseFromArguments, allUsersNotificationPermissions);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (parseFromArguments.proto || NotificationManagerService.this.mEdgeLightingManager == null) {
                        return;
                    }
                    NotificationManagerService.this.mEdgeLightingManager.dump(fileDescriptor, printWriter, strArr);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public ComponentName getEffectsSuppressor() {
            if (NotificationManagerService.this.mEffectsSuppressors.isEmpty()) {
                return null;
            }
            return (ComponentName) NotificationManagerService.this.mEffectsSuppressors.get(0);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x003a, code lost:
        
            if (r5 == 0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
        
            r9.this$0.getContext().enforceCallingPermission("android.permission.READ_CONTACTS", "matchesCallFilter requires listener permission, contacts read access, or system level access");
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
        
            if (r2 == 0) goto L25;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean matchesCallFilter(Bundle bundle) {
            boolean z;
            int i = 0;
            try {
                enforceSystemOrSystemUI("INotificationManager.matchesCallFilter");
                z = true;
            } catch (SecurityException unused) {
                z = false;
            }
            try {
                String[] packagesForUid = NotificationManagerService.this.mPackageManager.getPackagesForUid(Binder.getCallingUid());
                int i2 = 0;
                while (i < packagesForUid.length) {
                    try {
                        i2 |= NotificationManagerService.this.mListeners.hasAllowedListener(packagesForUid[i], Binder.getCallingUserHandle().getIdentifier()) ? 1 : 0;
                        i++;
                    } catch (RemoteException unused2) {
                        i = i2;
                        if (!z) {
                        }
                        return NotificationManagerService.this.mZenModeHelper.matchesCallFilter(Binder.getCallingUserHandle(), bundle, (ValidateNotificationPeople) NotificationManagerService.this.mRankingHelper.findExtractor(ValidateNotificationPeople.class), 3000, 1.0f, Binder.getCallingUid());
                    } catch (Throwable th) {
                        th = th;
                        i = i2;
                        if (!z && i == 0) {
                            NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.READ_CONTACTS", "matchesCallFilter requires listener permission, contacts read access, or system level access");
                        }
                        throw th;
                    }
                }
                if (!z) {
                }
            } catch (RemoteException unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
            return NotificationManagerService.this.mZenModeHelper.matchesCallFilter(Binder.getCallingUserHandle(), bundle, (ValidateNotificationPeople) NotificationManagerService.this.mRankingHelper.findExtractor(ValidateNotificationPeople.class), 3000, 1.0f, Binder.getCallingUid());
        }

        public void cleanUpCallersAfter(long j) {
            enforceSystemOrSystemUI("INotificationManager.cleanUpCallersAfter");
            NotificationManagerService.this.mZenModeHelper.cleanUpCallersAfter(j);
        }

        public boolean isSystemConditionProviderEnabled(String str) {
            enforceSystemOrSystemUI("INotificationManager.isSystemConditionProviderEnabled");
            return NotificationManagerService.this.mConditionProviders.isSystemProviderEnabled(str);
        }

        public byte[] getBackupPayload(int i) {
            if (!NotificationManagerService.this.checkCallerIsSystemUI()) {
                NotificationManagerService.this.checkCallerIsSystem();
            }
            Binder.clearCallingIdentity();
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "getBackupPayload u=" + i);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                NotificationManagerService.this.writePolicyXml(byteArrayOutputStream, true, i);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                Slog.w("NotificationService", "getBackupPayload: error writing payload for user " + i, e);
                return null;
            }
        }

        public void applyRestore(byte[] bArr, int i) {
            if (NotificationManagerService.this.checkCallerIsSystemUI()) {
                NotificationManagerService.this.mHandler.removeMessages(9);
                NotificationManagerService.this.mHandler.obtainMessage(9, i, 0, bArr).sendToTarget();
                return;
            }
            NotificationManagerService.this.checkCallerIsSystem();
            if (NotificationManagerService.DBG) {
                StringBuilder sb = new StringBuilder();
                sb.append("applyRestore u=");
                sb.append(i);
                sb.append(" payload=");
                sb.append(bArr != null ? new String(bArr, StandardCharsets.UTF_8) : null);
                Slog.d("NotificationService", sb.toString());
            }
            if (bArr == null) {
                Slog.w("NotificationService", "applyRestore: no payload to restore for user " + i);
                return;
            }
            try {
                NotificationManagerService.this.readPolicyXml(new ByteArrayInputStream(bArr), true, i);
                NotificationManagerService.this.handleSavePolicyFile();
            } catch (IOException | NumberFormatException | XmlPullParserException e) {
                Slog.w("NotificationService", "applyRestore: error reading payload", e);
            }
        }

        public boolean isNotificationPolicyAccessGranted(String str) {
            return checkPolicyAccess(str);
        }

        public boolean isNotificationPolicyAccessGrantedForPackage(String str) {
            enforceSystemOrSystemUIOrSamePackage(str, "request policy access status for another package");
            return checkPolicyAccess(str);
        }

        public void setNotificationPolicyAccessGranted(String str, boolean z) {
            setNotificationPolicyAccessGrantedForUser(str, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystemOrShell();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (NotificationManagerService.this.mAllowedManagedServicePackages.test(str, Integer.valueOf(i), NotificationManagerService.this.mConditionProviders.getRequiredPermission())) {
                    NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(str, i, true, z);
                    NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), UserHandle.of(i), null);
                    NotificationManagerService.this.handleSavePolicyFile();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public NotificationManager.Policy getNotificationPolicy(String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public NotificationManager.Policy getConsolidatedNotificationPolicy() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mZenModeHelper.getConsolidatedNotificationPolicy();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNotificationPolicy(String str, NotificationManager.Policy policy) {
            NotificationManager.Policy policy2 = policy;
            enforcePolicyAccess(str, "setNotificationPolicy");
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ApplicationInfo applicationInfo = NotificationManagerService.this.mPackageManager.getApplicationInfo(str, 0L, UserHandle.getUserId(callingUid));
                NotificationManager.Policy notificationPolicy = NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
                if (applicationInfo.targetSdkVersion < 28) {
                    int i = policy2.priorityCategories & (-33) & (-65) & (-129);
                    int i2 = notificationPolicy.priorityCategories;
                    policy2 = new NotificationManager.Policy(i | (i2 & 32) | (i2 & 64) | (i2 & 128), policy2.priorityCallSenders, policy2.priorityMessageSenders, policy2.suppressedVisualEffects);
                }
                if (applicationInfo.targetSdkVersion < 30) {
                    policy2 = new NotificationManager.Policy(NotificationManagerService.this.correctCategory(policy2.priorityCategories, 256, notificationPolicy.priorityCategories), policy2.priorityCallSenders, policy2.priorityMessageSenders, policy2.suppressedVisualEffects, notificationPolicy.priorityConversationSenders);
                }
                NotificationManager.Policy policy3 = new NotificationManager.Policy(policy2.priorityCategories, policy2.priorityCallSenders, policy2.priorityMessageSenders, NotificationManagerService.this.calculateSuppressedVisualEffects(policy2, notificationPolicy, applicationInfo.targetSdkVersion), -1, policy2.priorityConversationSenders, policy2.getExceptionContacts(), policy2.getAppBypassDndList());
                ZenLog.traceSetNotificationPolicy(str, applicationInfo.targetSdkVersion, policy3);
                NotificationManagerService.this.mZenModeHelper.setNotificationPolicy(policy3, callingUid, isCallerIsSystemOrSystemUi);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }

        public List getEnabledNotificationListenerPackages() {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.getAllowedPackages(INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public List getEnabledNotificationListeners(int i) {
            NotificationManagerService.this.checkNotificationListenerAccess();
            return NotificationManagerService.this.mListeners.getAllowedComponents(i);
        }

        public ComponentName getAllowedNotificationAssistantForUser(int i) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            List allowedComponents = NotificationManagerService.this.mAssistants.getAllowedComponents(i);
            if (allowedComponents.size() > 1) {
                throw new IllegalStateException("At most one NotificationAssistant: " + allowedComponents.size());
            }
            return (ComponentName) CollectionUtils.firstOrNull(allowedComponents);
        }

        public ComponentName getAllowedNotificationAssistant() {
            return getAllowedNotificationAssistantForUser(INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public ComponentName getDefaultNotificationAssistant() {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mAssistants.getDefaultFromConfig();
        }

        public void setNASMigrationDoneAndResetDefault(int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.setNASMigrationDone(i);
            if (z) {
                NotificationManagerService.this.mAssistants.resetDefaultFromConfig();
            } else {
                NotificationManagerService.this.mAssistants.clearDefaults();
            }
        }

        public boolean hasEnabledNotificationListener(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.isPackageAllowed(str, i);
        }

        public boolean isNotificationListenerAccessGranted(ComponentName componentName) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            return NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(componentName.flattenToString(), INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(componentName.flattenToString(), i);
        }

        public boolean isNotificationAssistantAccessGranted(ComponentName componentName) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            return NotificationManagerService.this.mAssistants.isPackageOrComponentAllowed(componentName.flattenToString(), INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) {
            setNotificationListenerAccessGrantedForUser(componentName, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z, z2);
        }

        public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) {
            setNotificationAssistantAccessGrantedForUser(componentName, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.this.checkNotificationListenerAccess();
            if (z && componentName.flattenToString().length() > NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH) {
                throw new IllegalArgumentException("Component name too long: " + componentName.flattenToString());
            }
            if (z2 || !isNotificationListenerAccessUserSet(componentName)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (NotificationManagerService.this.mAllowedManagedServicePackages.test(componentName.getPackageName(), Integer.valueOf(i), NotificationManagerService.this.mListeners.getRequiredPermission())) {
                        NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(componentName.flattenToString(), i, false, z, z2);
                        NotificationManagerService.this.mListeners.setPackageOrComponentEnabled(componentName.flattenToString(), i, true, z, z2);
                        NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), UserHandle.of(i), null);
                        NotificationManagerService.this.handleSavePolicyFile();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final boolean isNotificationListenerAccessUserSet(ComponentName componentName) {
            return NotificationManagerService.this.mListeners.isPackageOrComponentUserSet(componentName.flattenToString(), INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            Iterator it = NotificationManagerService.this.mUm.getEnabledProfiles(i).iterator();
            while (it.hasNext()) {
                NotificationManagerService.this.mAssistants.setUserSet(((UserInfo) it.next()).id, true);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, z, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                    int size = NotificationManagerService.this.mEnqueuedNotifications.size();
                    boolean z = false;
                    for (int i = 0; i < size; i++) {
                        NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i);
                        if (Objects.equals(adjustment.getKey(), notificationRecord.getKey()) && Objects.equals(Integer.valueOf(adjustment.getUser()), Integer.valueOf(notificationRecord.getUserId())) && NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.getUserId())) {
                            NotificationManagerService.this.applyAdjustment(notificationRecord, adjustment);
                            notificationRecord.applyAdjustments();
                            notificationRecord.calculateImportance();
                            z = true;
                        }
                    }
                    if (!z) {
                        applyAdjustmentFromAssistant(iNotificationListener, adjustment);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(adjustment);
            applyAdjustmentsFromAssistant(iNotificationListener, arrayList);
        }

        public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List list) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                    Iterator it = list.iterator();
                    z = false;
                    while (it.hasNext()) {
                        Adjustment adjustment = (Adjustment) it.next();
                        NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(adjustment.getKey());
                        if (notificationRecord != null && NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.getUserId())) {
                            NotificationManagerService.this.applyAdjustment(notificationRecord, adjustment);
                            if (adjustment.getSignals().containsKey("key_importance") && adjustment.getSignals().getInt("key_importance") == 0) {
                                cancelNotificationsFromListener(iNotificationListener, new String[]{notificationRecord.getKey()});
                            } else {
                                notificationRecord.setPendingLogUpdate(true);
                                z = true;
                            }
                        }
                    }
                }
                if (z) {
                    NotificationManagerService.this.mRankingHandler.requestSort();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) {
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            NotificationManagerService.this.createNotificationChannelGroup(str, getUidForPackageAndUser(str, userHandle), notificationChannelGroup, false, true);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) {
            Objects.requireNonNull(notificationChannel);
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            verifyPrivilegedListenerUriPermission(Binder.getCallingUid(), notificationChannel, NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, getUidForPackageAndUser(str, userHandle), notificationChannel.getId(), true));
            NotificationManagerService.this.updateNotificationChannelInt(str, getUidForPackageAndUser(str, userHandle), notificationChannel, true);
        }

        public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, getUidForPackageAndUser(str, userHandle), false);
        }

        public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, getUidForPackageAndUser(str, userHandle)));
            return new ParceledListSlice(arrayList);
        }

        public boolean isInCall(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            return NotificationManagerService.this.isCallNotification(str, i);
        }

        public void setPrivateNotificationsAllowed(boolean z) {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") != 0) {
                throw new SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
            }
            if (z != NotificationManagerService.this.mLockScreenAllowSecureNotifications) {
                NotificationManagerService.this.mLockScreenAllowSecureNotifications = z;
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public boolean getPrivateNotificationsAllowed() {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") != 0) {
                throw new SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
            }
            return NotificationManagerService.this.mLockScreenAllowSecureNotifications;
        }

        public boolean isPackagePaused(String str) {
            Objects.requireNonNull(str);
            NotificationManagerService.this.checkCallerIsSameApp(str);
            return NotificationManagerService.this.isPackagePausedOrSuspended(str, Binder.getCallingUid());
        }

        public boolean isPermissionFixed(String str, int i) {
            enforceSystemOrSystemUI("isPermissionFixed");
            return NotificationManagerService.this.mPermissionHelper.isPermissionFixed(str, i);
        }

        public final void verifyPrivilegedListener(INotificationListener iNotificationListener, UserHandle userHandle, boolean z) {
            ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
            }
            if (!NotificationManagerService.this.hasCompanionDevice(checkServiceTokenLocked)) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    if (z) {
                        if (NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(checkServiceTokenLocked.service)) {
                        }
                    }
                    throw new SecurityException(checkServiceTokenLocked + " does not have access");
                }
            }
            if (checkServiceTokenLocked.enabledAndUserMatches(userHandle.getIdentifier())) {
                return;
            }
            throw new SecurityException(checkServiceTokenLocked + " does not have access");
        }

        public final void verifyPrivilegedListenerUriPermission(final int i, NotificationChannel notificationChannel, NotificationChannel notificationChannel2) {
            final Uri sound = notificationChannel.getSound();
            Uri sound2 = notificationChannel2 != null ? notificationChannel2.getSound() : null;
            if (sound == null || Objects.equals(sound2, sound)) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$15$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    NotificationManagerService.INotificationManagerStubC199315.this.lambda$verifyPrivilegedListenerUriPermission$3(i, sound);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$verifyPrivilegedListenerUriPermission$3(int i, Uri uri) {
            NotificationManagerService.this.mUgmInternal.checkGrantUriPermission(i, null, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
        }

        public final int getUidForPackageAndUser(String str, UserHandle userHandle) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, userHandle.getIdentifier());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new NotificationShellCmd(NotificationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public long pullStats(long j, int i, boolean z, List list) {
            NotificationManagerService.this.checkCallerIsSystemOrShell();
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            TimeUnit timeUnit2 = TimeUnit.NANOSECONDS;
            long convert = timeUnit.convert(j, timeUnit2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 1) {
                    Slog.e("NotificationService", "pullStats REPORT_REMOTE_VIEWS from: " + convert + "  with " + z);
                    PulledStats remoteViewStats = NotificationManagerService.this.mUsageStats.remoteViewStats(convert, z);
                    if (remoteViewStats != null) {
                        list.add(remoteViewStats.toParcelFileDescriptor(i));
                        Slog.e("NotificationService", "exiting pullStats with: " + list.size());
                        return timeUnit2.convert(remoteViewStats.endTimeMs(), timeUnit);
                    }
                    Slog.e("NotificationService", "null stats for: " + i);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Slog.e("NotificationService", "exiting pullStats: bad request");
                return 0L;
            } catch (IOException e) {
                Slog.e("NotificationService", "exiting pullStats: on error", e);
                return 0L;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) {
            NotificationManagerService.this.mEdgeLightingManager.bindService(iBinder, i, componentName);
        }

        public void unbindEdgeLightingService(IBinder iBinder, String str) {
            NotificationManagerService.this.mEdgeLightingManager.unbindService(iBinder, str);
        }

        public void updateEdgeLightingPackageList(String str, List list) {
            NotificationManagerService.this.mEdgeLightingManager.updateEdgeLightingPackageList(str, list);
        }

        public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) {
            NotificationManagerService.this.mEdgeLightingManager.registerListener(iBinder, componentName);
        }

        public void unregisterEdgeLightingListener(IBinder iBinder, String str) {
            NotificationManagerService.this.mEdgeLightingManager.unregisterListener(iBinder, str);
        }

        public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) {
            NotificationManagerService.this.mEdgeLightingManager.updateEdgeLightingPolicy(str, edgeLightingPolicy);
        }

        public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) {
            Slog.e("NotificationService", "startEdgeLighting");
            NotificationManagerService.this.mEdgeLightingManager.startEdgeLighting(str, semEdgeLightingInfo, iBinder);
        }

        public void stopEdgeLighting(String str, IBinder iBinder) {
            Slog.e("NotificationService", "stopEdgeLighting");
            NotificationManagerService.this.mEdgeLightingManager.stopEdgeLighting(str, iBinder);
        }

        public int getEdgeLightingState() {
            return NotificationManagerService.this.mEdgeLightingManager.getEdgeLightingState();
        }

        public boolean isEdgeLightingNotificationAllowed(String str) {
            return NotificationManagerService.this.mEdgeLightingManager.isEdgeLightingNotificationAllowed(str);
        }

        public void disable(int i, String str, IBinder iBinder) {
            if (i == 0 || i == 1) {
                NotificationManagerService.this.mEdgeLightingManager.disable(i, str, iBinder);
            }
        }

        public void disableEdgeLightingNotification(String str, boolean z) {
            NotificationManagerService.this.mEdgeLightingManager.disableEdgeLightingNotification(str, z);
        }

        public boolean isPackageEnabled(String str, int i) {
            return NotificationManagerService.this.mEdgeLightingManager.isPackageEnabled(str, i);
        }

        public void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) {
            NotificationManagerService.this.mEdgeLightingManager.cancelNotification(str, str2, i, i2, str3);
        }

        public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) {
            NotificationManagerService.this.mEdgeLightingManager.cancelNotificationByGroupKey(str, str2, i, i2, str3, str4);
        }

        public void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            NotificationManagerService.this.enqueueEdgeNotificationInternal(str, str2, Binder.getCallingUid(), Binder.getCallingPid(), i, bundle, i2);
        }

        public void removeEdgeNotification(String str, int i, Bundle bundle, int i2) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            NotificationManagerService.this.removeEdgeNotificationInternal(Binder.getCallingUid(), Binder.getCallingPid(), str, i, bundle, ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, true, false, "cancelNotificationWithTag", str));
        }

        public boolean isEdgeLightingAllowed(String str, int i) {
            enforceSystemOrSystemUI("isEdgeLightingAllowed");
            return NotificationManagerService.this.mPreferencesHelper.isEdgeLightingAllowed(str, i);
        }

        public void setAllowEdgeLighting(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setAllowEdgeLighting");
            NotificationManagerService.this.mPreferencesHelper.setAllowEdgeLighting(str, i, z);
        }

        public void resetDefaultAllowEdgeLighting() {
            enforceSystemOrSystemUI("resetDefaultAllowEdgeLighting");
            NotificationManagerService.this.mPreferencesHelper.resetDefaultAllowEdgeLighting();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean isSubDisplayNotificationAllowed(String str, int i) {
            enforceSystemOrSystemUI("isSubDisplayNotificationAllowed");
            return NotificationManagerService.this.mPreferencesHelper.isSubDisplayNotificationAllowed(str, i);
        }

        public void setAllowSubDisplayNotification(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setAllowSubDisplayNotification");
            NotificationManagerService.this.mPreferencesHelper.setAllowSubDisplayNotification(str, i, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean isAlertsAllowed(String str, int i, String str2, int i2) {
            if (getZenMode() != 0) {
                Log.w("NotificationService", "Screen on NOT allowed while DnD turned ON : " + str);
                return false;
            }
            if (!areNotificationsEnabledForPackage(str, i)) {
                Log.w("NotificationService", "Screen on NOT allowed for notification blocked apps : " + str);
                return false;
            }
            if (!NotificationManagerService.this.isPackageSuspendedForUser(str, i)) {
                return true;
            }
            Log.w("NotificationService", "Screen on NOT allowed for package suspended : " + str);
            return false;
        }

        public void registerNotificationListener(ComponentName componentName, int i, boolean z) {
            Slog.v("NotificationService", "registerNotificationListener component=" + componentName + " enabled =" + z);
            NotificationManagerService.this.checkNotificationListenerAccess("registerNotificationListener");
            Binder.clearCallingIdentity();
            if (z) {
                NotificationManagerService.this.mListeners.registerSystemService(componentName, i);
            } else {
                NotificationManagerService.this.mListeners.unregisterService(componentName, i);
            }
        }

        public void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) {
            NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int callingUid = Binder.getCallingUid();
            if (checkUpdateNotificationChannelAccess(str)) {
                List list = parceledListSlice.getList();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    NotificationChannel notificationChannel = (NotificationChannel) list.get(i);
                    Objects.requireNonNull(notificationChannel, "channel in list is null");
                    Log.d("NotificationService", "updateNotificationChannels from " + str + " channel:" + notificationChannel);
                    NotificationManagerService.this.updateNotificationChannelInt(str, callingUid, notificationChannel, false);
                }
            }
        }

        public final boolean checkUpdateNotificationChannelAccess(String str) {
            if (NotificationManagerService.this.mPackageManagerClient.checkPermission("com.samsung.android.permission.SEM_UPDATE_NOTIFICATION_CHANNELS", str) == 0) {
                return true;
            }
            throw new SecurityException("Requires SEM_UPDATE_NOTIFICATION_CHANNELS permission");
        }

        public boolean getNotificationAlertsEnabledForPackage(String str, int i) {
            enforceSystemOrSystemUI("getNotificationAlertsEnabledForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(str, i);
        }

        public void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setNotificationAlertsEnabledForPackage");
            NotificationManagerService.this.mPreferencesHelper.setNotificationAlertsEnabledForPackage(str, i, z);
        }

        public boolean setWearableAppList(int i, List list) {
            NotificationManagerService.this.mPreferencesHelper.clearWearableMutedAppList(i);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                try {
                    int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                    Log.d("NotificationService", "NMS : setWearableAppList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                    NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, str, 1);
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            }
            return true;
        }

        public boolean addWearableAppToList(int i, String str) {
            try {
                int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                Log.d("NotificationService", "NMS : addWearableAppToList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, str, 1);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public boolean removeWearableAppFromList(int i, String str) {
            try {
                int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                Log.d("NotificationService", "NMS : removeWearableAppFromList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, str, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public List getWearableAppList(int i) {
            Log.d("NotificationService", "NMS : getWearableAppList uid=" + i);
            return NotificationManagerService.this.mPreferencesHelper.getWearableMutedAppList(i);
        }

        public boolean requestListenerHintsForWearable(int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                Log.d("NotificationService", "NMS : requestListenerHintsForWearable state=" + i);
                NotificationManagerService.this.mIsMutedByWearableApps = i;
            }
            return true;
        }

        public int getLockScreenNotificationVisibilityForPackage(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(str, i);
        }

        public void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) {
            NotificationManagerService.this.checkCallerIsSystem();
            Log.d("NotificationService", "setLockScreenNotificationVisibilityForPackage: pkg=" + str + " uid=" + i + " lockscreenVisibility=" + i2);
            NotificationManagerService.this.mPreferencesHelper.setLockScreenNotificationVisibilityForPackage(str, i, i2);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean isAllowNotificationPopUpForPackage(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.isAllowNotificationPopUpForPackage(str, i);
        }

        public void setAllowNotificationPopUpForPackage(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            Log.d("NotificationService", "setAllowNotificationPopUpForPackage: pkg=" + str + " uid=" + i + " allow=" + z);
            NotificationManagerService.this.mPreferencesHelper.setAllowNotificationPopUpForPackage(str, i, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean isReminderEnabled(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.isReminderEnabled(str, i);
        }

        public void setReminderEnabledForPackage(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.setReminderEnabled(str, i, z);
            NotificationManagerService.this.mNotificationReminder.setReminderAppEnabled(str, i, z);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public void setReminderEnabled(int i, boolean z, List list) {
            NotificationManagerService.this.checkCallerIsSystem();
            if (z) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    try {
                        NotificationManagerService.this.mPreferencesHelper.setReminderEnabled(str, NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i), true);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.d("NotificationService", "setReminderEnabledtoList NameNotFoundException");
                    }
                }
            } else {
                NotificationManagerService.this.mPreferencesHelper.removeAllReminderSetting(i);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public int getBlockedAppCount(int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getBlockedAppCount(i);
        }

        public boolean canAppBypassDnd(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.canAppBypassDnd(str, i);
        }

        public void setAppBypassDnd(String str, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystem();
            int callingUid = Binder.getCallingUid();
            boolean isCallerIsSystemOrSystemUi = NotificationManagerService.this.isCallerIsSystemOrSystemUi();
            Log.d("NotificationService", "setAppBypassDnd: pkg=" + str + " uid=" + i + " allow=" + z);
            NotificationManagerService.this.mPreferencesHelper.setAppBypassDnd(str, i, z, callingUid, isCallerIsSystemOrSystemUi);
            NotificationManager.Policy notificationPolicy = NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
            notificationPolicy.addAppBypassDnd(str, i, z);
            NotificationManagerService.this.mZenModeHelper.setNotificationPolicy(new NotificationManager.Policy(notificationPolicy.priorityCategories, notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects, -1, notificationPolicy.priorityConversationSenders, notificationPolicy.getExceptionContacts(), notificationPolicy.getAppBypassDndList()), callingUid, isCallerIsSystemOrSystemUi);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public int getAppsBypassingDndCount(int i) {
            NotificationManagerService.this.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getAppsBypassingDndCount(i);
        }

        public void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) {
            enforcePolicyAccess(Binder.getCallingUid(), "addReplyHistory");
            if (str2 == null) {
                Slog.d("NotificationService", "pkg data null value, addReplyHistory can not be saved.");
            } else {
                NotificationManagerService.this.mHistoryManager.addNotification(new NotificationHistory.HistoricalNotification.Builder().setPackage(str2).setUid(Binder.getCallingUid()).setUserId(i2).setPostedTimeMs(System.currentTimeMillis()).setChannelId("setChannelId").setChannelName("setChannelName").setTitle(str3).setText(str4).setIcon(Icon.createWithResource(NotificationManagerService.this.getContext().getResources(), 17304219)).setSbnKey(str).setType(i).build());
            }
        }

        public void updateCancelEvent(int i, String str, boolean z) {
            enforcePolicyAccess(Binder.getCallingUid(), "updateCancelEvent");
            NotificationManagerService.this.mHistoryManager.updateCancelEvent(i, str, z);
        }

        public NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) {
            NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_NOTIFICATIONS", "NotificationManagerService.getNotificationHistory");
            if (NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null) == 0) {
                Trace.traceBegin(524288L, "notifHistoryReadHistoryForPackage");
                Slog.d("NotificationService", "getNotificationHistory start key=" + str4);
                try {
                    return NotificationManagerService.this.mHistoryManager.readFilteredNotificationHistoryForPackage(i, str3, str4, i2);
                } finally {
                    Trace.traceEnd(524288L);
                    Log.d("NotificationService", "getNotificationHistory end pkg=" + str3);
                }
            }
            return new NotificationHistory();
        }

        public List getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) {
            enforcePolicyAccess(Binder.getCallingUid(), "getNotificationHistoryDataForPackage");
            ArrayList arrayList = new ArrayList();
            if (!NotificationManagerService.this.mConversationHistoryAppList.contains(str3)) {
                return arrayList;
            }
            for (NotificationHistory.HistoricalNotification historicalNotification : getNotificationHistoryForPackage(str, str2, i, str3, str4, i2).getNotificationsToWrite()) {
                Bundle bundle = new Bundle();
                bundle.putString("package", historicalNotification.getPackage());
                bundle.putInt("uid", historicalNotification.getUid());
                bundle.putInt("userId", historicalNotification.getUserId());
                bundle.putLong("postedTime", historicalNotification.getPostedTimeMs());
                bundle.putString(KnoxCustomManagerService.SHORTCUT_TITLE, historicalNotification.getTitle());
                bundle.putString("text", historicalNotification.getText());
                bundle.putString("conversationId", historicalNotification.getConversationId());
                bundle.putString("sbnKey", historicalNotification.getSbnKey());
                bundle.putInt("type", historicalNotification.getType());
                bundle.putBoolean("isChecked", historicalNotification.getChecked());
                Uri uri = historicalNotification.getUri();
                bundle.putString("uri", uri != null ? uri.toString() : null);
                arrayList.add(bundle);
            }
            return arrayList;
        }

        public void setRestoreBlockListForSS(List list) {
            enforceSystemOrSystemUI("setRestoreBlockListForSS");
            NotificationManagerService.this.mPreferencesHelper.setRestoreBlockListForSS(list);
        }

        public int getAllNotificationListenersCount() {
            NotificationManagerService.this.checkNotificationListenerAccess();
            return NotificationManagerService.this.mListeners.getServices().size();
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0044, code lost:
        
            if (java.util.Arrays.stream(r4).noneMatch(new com.android.server.notification.NotificationManagerService$15$$ExternalSyntheticLambda0()) != false) goto L17;
         */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0073  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int isNotificationTurnedOff(String str, int i) {
            boolean z;
            PackageInfo packageInfo;
            ParceledListSlice notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, true);
            boolean z2 = (notificationChannels == null || notificationChannels.getList().isEmpty()) ? false : true;
            try {
                packageInfo = NotificationManagerService.this.getContext().getPackageManager().getPackageInfo(str, 4160);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (packageInfo != null && packageInfo.applicationInfo.targetSdkVersion > 32) {
                String[] strArr = packageInfo.requestedPermissions;
                if (strArr != null) {
                }
                z = false;
                if (areNotificationsEnabledForPackage(str, i)) {
                    if (z) {
                        Slog.d("NotificationService", "Grants notification permissions");
                        NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, UserHandle.getUserId(i), true, true);
                        return 8;
                    }
                    Slog.d("NotificationService", "No permissions");
                    return 2;
                }
                if (z2) {
                    int size = notificationChannels.getList().size();
                    boolean z3 = false;
                    for (int i2 = 0; i2 < size; i2++) {
                        NotificationChannel notificationChannel = (NotificationChannel) notificationChannels.getList().get(i2);
                        if (notificationChannel.getImportance() == 0 && (notificationChannel.getUserLockedFields() & 4) != 0) {
                            notificationChannel.setImportance(notificationChannel.getOriginalImportance());
                            z3 = true;
                        }
                    }
                    if (z3) {
                        Slog.d("NotificationService", "Turned on notification channel");
                        return 16;
                    }
                    Slog.d("NotificationService", "Already turned on and nothing has changed");
                    return 32;
                }
                Slog.d("NotificationService", "No channel");
                return 4;
            }
            z = true;
            if (areNotificationsEnabledForPackage(str, i)) {
            }
        }

        public static /* synthetic */ boolean lambda$isNotificationTurnedOff$4(String str) {
            return str.equals("android.permission.POST_NOTIFICATIONS");
        }

        public int getNotificationSoundStatus(String str) {
            int i;
            ParceledListSlice notificationChannels;
            if (str != null) {
                try {
                    i = NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, ActivityManager.getCurrentUser());
                } catch (Exception e) {
                    Slog.e("NotificationService", "Cannot get package uid ", e);
                    i = 0;
                }
                notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, false);
            } else {
                i = 0;
                notificationChannels = null;
            }
            if (getZenMode() != 0) {
                try {
                    setZenMode(0, null, "It is turned off by Bixby");
                } catch (Exception unused) {
                    Slog.d("NotificationService", "Failed to turn off DND by bixby.");
                }
                Slog.d("NotificationService", "DND is turned off by bixby. ret = 0x" + Integer.toHexString(IInstalld.FLAG_USE_QUOTA));
                return IInstalld.FLAG_USE_QUOTA;
            }
            if (NotificationManagerService.this.mAudioManager.getRingerModeInternal() != 2) {
                NotificationManagerService.this.mAudioManager.setRingerModeInternal(2);
                Slog.d("NotificationService", "Ringer mode has been changed to sound. ret = 0x" + Integer.toHexString(IInstalld.FLAG_FORCE));
                return IInstalld.FLAG_FORCE;
            }
            if (NotificationManagerService.this.mAudioManager.getRingerModeInternal() == 2 && NotificationManagerService.this.mAudioManager.getStreamVolume(5) == 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    NotificationManagerService.this.mAudioManager.setStreamVolume(5, 11, 4);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.d("NotificationService", "notification volume has been restored to default(11). ret = 0x" + Integer.toHexString(16384));
                    return 16384;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            if (str != null && !NotificationManagerService.this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(str, i)) {
                NotificationManagerService.this.mPreferencesHelper.setNotificationAlertsEnabledForPackage(str, i, true);
                Slog.d("NotificationService", "App notification alerts has been changed to \"Allow sound and vibration\". ret = 0x" + Integer.toHexString(32768));
                return 32768;
            }
            if (str == null || notificationChannels == null || !hasUserLockedChanged(notificationChannels)) {
                return 0;
            }
            int i2 = 0;
            for (NotificationChannel notificationChannel : notificationChannels.getList()) {
                if (notificationChannel.hasUserSetImportance() && notificationChannel.getImportance() <= 2) {
                    notificationChannel.setImportance(notificationChannel.getOriginalImportance());
                    i2 = 65536;
                }
                if (notificationChannel.hasUserSetSound() && notificationChannel.getSound() == null) {
                    notificationChannel.setSound(RingtoneManager.getDefaultUri(2), new AudioAttributes.Builder().setContentType(4).setUsage(5).build());
                    i2 = 65536;
                }
                NotificationManagerService.this.updateNotificationChannelInt(str, i, notificationChannel, false);
            }
            Slog.d("NotificationService", "App notification category settings have been restored to original or default. ret = 0x" + Integer.toHexString(i2));
            return i2;
        }

        public final boolean hasUserLockedChanged(ParceledListSlice parceledListSlice) {
            for (NotificationChannel notificationChannel : parceledListSlice.getList()) {
                if (notificationChannel.hasUserSetImportance() || notificationChannel.hasUserSetSound()) {
                    return true;
                }
            }
            return false;
        }

        public boolean setNotificationTurnOff(String str, int i) {
            if (!areNotificationsEnabledForPackage(str, i)) {
                return false;
            }
            NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, UserHandle.getUserId(i), false, true);
            Slog.d("NotificationService", "Revoke notification permissions");
            return true;
        }
    }

    public final void checkNotificationListenerAccess(String str) {
        if (getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
            return;
        }
        Slog.w("NotificationService", "Notification listener access denied calling " + str);
        throw new SecurityException("Notification listener access denied");
    }

    public void checkNotificationListenerAccess() {
        if (isCallerSystemOrPhone()) {
            return;
        }
        getContext().enforceCallingPermission("android.permission.MANAGE_NOTIFICATION_LISTENERS", "Caller must hold android.permission.MANAGE_NOTIFICATION_LISTENERS");
    }

    public void setNotificationAssistantAccessGrantedForUserInternal(ComponentName componentName, int i, boolean z, boolean z2) {
        List enabledProfiles = this.mUm.getEnabledProfiles(i);
        if (enabledProfiles != null) {
            Iterator it = enabledProfiles.iterator();
            while (it.hasNext()) {
                int i2 = ((UserInfo) it.next()).id;
                if (componentName == null) {
                    ComponentName componentName2 = (ComponentName) CollectionUtils.firstOrNull(this.mAssistants.getAllowedComponents(i2));
                    if (componentName2 != null) {
                        setNotificationAssistantAccessGrantedForUserInternal(componentName2, i2, false, z2);
                    }
                } else if (!z || this.mAllowedManagedServicePackages.test(componentName.getPackageName(), Integer.valueOf(i2), this.mAssistants.getRequiredPermission())) {
                    this.mConditionProviders.setPackageOrComponentEnabled(componentName.flattenToString(), i2, false, z);
                    this.mAssistants.setPackageOrComponentEnabled(componentName.flattenToString(), i2, true, z, z2);
                    getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), UserHandle.of(i2), null);
                    handleSavePolicyFile();
                }
            }
        }
    }

    public final void applyAdjustment(NotificationRecord notificationRecord, Adjustment adjustment) {
        if (notificationRecord == null || adjustment.getSignals() == null) {
            return;
        }
        Bundle signals = adjustment.getSignals();
        Bundle.setDefusable(signals, true);
        ArrayList arrayList = new ArrayList();
        for (String str : signals.keySet()) {
            if (!this.mAssistants.isAdjustmentAllowed(str)) {
                arrayList.add(str);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            signals.remove((String) it.next());
        }
        notificationRecord.addAdjustment(adjustment);
    }

    public void addAutogroupKeyLocked(String str) {
        NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str);
        if (notificationRecord != null && notificationRecord.getSbn().getOverrideGroupKey() == null) {
            addAutoGroupAdjustment(notificationRecord, "ranker_group");
            EventLogTags.writeNotificationAutogrouped(str);
            this.mRankingHandler.requestSort();
        }
    }

    public void removeAutogroupKeyLocked(String str) {
        NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str);
        if (notificationRecord == null) {
            Slog.w("NotificationService", "Failed to remove autogroup " + str);
            return;
        }
        if (notificationRecord.getSbn().getOverrideGroupKey() != null) {
            addAutoGroupAdjustment(notificationRecord, null);
            EventLogTags.writeNotificationUnautogrouped(str);
            this.mRankingHandler.requestSort();
        }
    }

    public final void addAutoGroupAdjustment(NotificationRecord notificationRecord, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("key_group_key", str);
        notificationRecord.addAdjustment(new Adjustment(notificationRecord.getSbn().getPackageName(), notificationRecord.getKey(), bundle, "", notificationRecord.getSbn().getUserId()));
    }

    public void clearAutogroupSummaryLocked(int i, String str) {
        NotificationRecord findNotificationByKeyLocked;
        ArrayMap arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(i));
        if (arrayMap == null || !arrayMap.containsKey(str) || (findNotificationByKeyLocked = findNotificationByKeyLocked((String) arrayMap.remove(str))) == null) {
            return;
        }
        StatusBarNotification sbn = findNotificationByKeyLocked.getSbn();
        cancelNotification(MY_UID, MY_PID, str, sbn.getTag(), sbn.getId(), 0, 0, false, i, 16, null);
    }

    public final boolean hasAutoGroupSummaryLocked(StatusBarNotification statusBarNotification) {
        ArrayMap arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(statusBarNotification.getUserId()));
        return arrayMap != null && arrayMap.containsKey(statusBarNotification.getPackageName());
    }

    public NotificationRecord createAutoGroupSummary(int i, String str, String str2, int i2) {
        NotificationRecord notificationRecord;
        Notification notification;
        ArrayMap arrayMap;
        boolean isPermissionFixed = this.mPermissionHelper.isPermissionFixed(str, i);
        synchronized (this.mNotificationLock) {
            NotificationRecord notificationRecord2 = (NotificationRecord) this.mNotificationsByKey.get(str2);
            if (notificationRecord2 == null) {
                return null;
            }
            StatusBarNotification sbn = notificationRecord2.getSbn();
            int identifier = sbn.getUser().getIdentifier();
            int uid = sbn.getUid();
            ArrayMap arrayMap2 = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(identifier));
            if (arrayMap2 == null) {
                arrayMap2 = new ArrayMap();
            }
            ArrayMap arrayMap3 = arrayMap2;
            this.mAutobundledSummaries.put(Integer.valueOf(identifier), arrayMap3);
            if (arrayMap3.containsKey(str)) {
                notificationRecord = null;
            } else {
                ApplicationInfo applicationInfo = (ApplicationInfo) sbn.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("android.appInfo", applicationInfo);
                Notification build = new Notification.Builder(getContext(), notificationRecord2.getChannel().getId()).setSmallIcon(sbn.getNotification().getSmallIcon()).setGroupSummary(true).setGroupAlertBehavior(2).setGroup("ranker_group").setFlag(i2, true).setColor(sbn.getNotification().color).build();
                build.extras.putAll(bundle);
                Intent launchIntentForPackage = getContext().getPackageManager().getLaunchIntentForPackage(str);
                if (launchIntentForPackage != null) {
                    notification = build;
                    arrayMap = arrayMap3;
                    notification.contentIntent = this.mAmi.getPendingIntentActivityAsApp(0, launchIntentForPackage, 67108864, (Bundle) null, str, applicationInfo.uid);
                } else {
                    notification = build;
                    arrayMap = arrayMap3;
                }
                StatusBarNotification statusBarNotification = new StatusBarNotification(sbn.getPackageName(), sbn.getOpPkg(), Integer.MAX_VALUE, "ranker_group", sbn.getUid(), sbn.getInitialPid(), notification, sbn.getUser(), "ranker_group", System.currentTimeMillis());
                NotificationRecord notificationRecord3 = new NotificationRecord(getContext(), statusBarNotification, notificationRecord2.getChannel());
                notificationRecord3.setImportanceFixed(isPermissionFixed);
                notificationRecord3.setIsAppImportanceLocked(notificationRecord2.getIsAppImportanceLocked());
                arrayMap.put(str, statusBarNotification.getKey());
                notificationRecord = notificationRecord3;
            }
            if (notificationRecord == null || !checkDisqualifyingFeatures(identifier, uid, notificationRecord.getSbn().getId(), notificationRecord.getSbn().getTag(), notificationRecord, true, false)) {
                return null;
            }
            return notificationRecord;
        }
    }

    public final String disableNotificationEffects(NotificationRecord notificationRecord) {
        if (this.mDisableNotificationEffects) {
            return "booleanState";
        }
        if ((this.mListenerHints & 1) != 0) {
            return "listenerHints";
        }
        if (notificationRecord != null && notificationRecord.getAudioAttributes() != null) {
            if ((this.mListenerHints & 2) != 0 && notificationRecord.getAudioAttributes().getUsage() != 6) {
                return "listenerNoti";
            }
            if ((this.mListenerHints & 4) != 0 && notificationRecord.getAudioAttributes().getUsage() == 6) {
                return "listenerCall";
            }
        }
        if (this.mCallState == 0 || this.mZenModeHelper.isCall(notificationRecord)) {
            return null;
        }
        return "callState";
    }

    public ArrayMap getAllUsersNotificationPermissions() {
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = this.mUm.getUsers().iterator();
        while (it.hasNext()) {
            ArrayMap notificationPermissionValues = this.mPermissionHelper.getNotificationPermissionValues(((UserInfo) it.next()).getUserHandle().getIdentifier());
            for (Pair pair : notificationPermissionValues.keySet()) {
                arrayMap.put(pair, (Pair) notificationPermissionValues.get(pair));
            }
        }
        return arrayMap;
    }

    public final void dumpJson(PrintWriter printWriter, DumpFilter dumpFilter, ArrayMap arrayMap) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("service", "Notification Manager");
            jSONObject.put("bans", this.mPreferencesHelper.dumpBansJson(dumpFilter, arrayMap));
            jSONObject.put("ranking", this.mPreferencesHelper.dumpJson(dumpFilter, arrayMap));
            jSONObject.put("stats", this.mUsageStats.dumpJson(dumpFilter));
            jSONObject.put("channels", this.mPreferencesHelper.dumpChannelsJson(dumpFilter));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        printWriter.println(jSONObject);
    }

    public final void dumpRemoteViewStats(PrintWriter printWriter, DumpFilter dumpFilter) {
        PulledStats remoteViewStats = this.mUsageStats.remoteViewStats(dumpFilter.since, true);
        if (remoteViewStats == null) {
            printWriter.println("no remote view stats reported.");
        } else {
            remoteViewStats.dump(1, printWriter, dumpFilter);
        }
    }

    public final void dumpProto(FileDescriptor fileDescriptor, DumpFilter dumpFilter, ArrayMap arrayMap) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                    notificationRecord.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 1);
                }
            }
            int size2 = this.mEnqueuedNotifications.size();
            for (int i2 = 0; i2 < size2; i2++) {
                NotificationRecord notificationRecord2 = (NotificationRecord) this.mEnqueuedNotifications.get(i2);
                if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord2.getSbn())) {
                    notificationRecord2.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 0);
                }
            }
            List snoozed = this.mSnoozeHelper.getSnoozed();
            int size3 = snoozed.size();
            for (int i3 = 0; i3 < size3; i3++) {
                NotificationRecord notificationRecord3 = (NotificationRecord) snoozed.get(i3);
                if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord3.getSbn())) {
                    notificationRecord3.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 2);
                }
            }
            long start = protoOutputStream.start(1146756268034L);
            this.mZenModeHelper.dump(protoOutputStream);
            Iterator it = this.mEffectsSuppressors.iterator();
            while (it.hasNext()) {
                ((ComponentName) it.next()).dumpDebug(protoOutputStream, 2246267895812L);
            }
            protoOutputStream.end(start);
            long start2 = protoOutputStream.start(1146756268035L);
            this.mListeners.dump(protoOutputStream, dumpFilter);
            protoOutputStream.end(start2);
            protoOutputStream.write(1120986464260L, this.mListenerHints);
            for (int i4 = 0; i4 < this.mListenersDisablingEffects.size(); i4++) {
                long start3 = protoOutputStream.start(2246267895813L);
                protoOutputStream.write(1120986464257L, this.mListenersDisablingEffects.keyAt(i4));
                ArraySet arraySet = (ArraySet) this.mListenersDisablingEffects.valueAt(i4);
                for (int i5 = 0; i5 < arraySet.size(); i5++) {
                    ((ComponentName) arraySet.valueAt(i5)).dumpDebug(protoOutputStream, 2246267895811L);
                }
                protoOutputStream.end(start3);
            }
            long start4 = protoOutputStream.start(1146756268038L);
            this.mAssistants.dump(protoOutputStream, dumpFilter);
            protoOutputStream.end(start4);
            long start5 = protoOutputStream.start(1146756268039L);
            this.mConditionProviders.dump(protoOutputStream, dumpFilter);
            protoOutputStream.end(start5);
            long start6 = protoOutputStream.start(1146756268040L);
            this.mRankingHelper.dump(protoOutputStream, dumpFilter);
            this.mPreferencesHelper.dump(protoOutputStream, dumpFilter, arrayMap);
            protoOutputStream.end(start6);
        }
        protoOutputStream.flush();
    }

    public final void dumpNotificationRecords(PrintWriter printWriter, DumpFilter dumpFilter) {
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            if (size > 0) {
                printWriter.println("  Notification List:");
                for (int i = 0; i < size; i++) {
                    NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                        notificationRecord.dump(printWriter, "    ", getContext(), dumpFilter.redact);
                    }
                }
                printWriter.println("  ");
            }
        }
    }

    public final void makeNotiPermissonHistory(int i, String str, boolean z) {
        String str2;
        if (i == 0) {
            str2 = "NOTI_PANEL";
        } else if (i != 1) {
            return;
        } else {
            str2 = "SETTINGS";
        }
        this.mNotiPermissionHistoryList.add(makeTime() + " category = " + str2 + ", pkg = " + str + ", enabled= " + z);
        while (this.mNotiPermissionHistoryList.size() > 100) {
            this.mNotiPermissionHistoryList.remove(0);
        }
    }

    public void dumpImpl(PrintWriter printWriter, DumpFilter dumpFilter, ArrayMap arrayMap) {
        printWriter.print("Current Notification Manager state");
        if (dumpFilter.filtered) {
            printWriter.print(" (filtered to ");
            printWriter.print(dumpFilter);
            printWriter.print(")");
        }
        printWriter.println(':');
        boolean z = dumpFilter.filtered && dumpFilter.zen;
        if (!z) {
            synchronized (this.mToastQueue) {
                int size = this.mToastQueue.size();
                if (size > 0) {
                    printWriter.println("  Toast Queue:");
                    for (int i = 0; i < size; i++) {
                        ((ToastRecord) this.mToastQueue.get(i)).dump(printWriter, "    ", dumpFilter);
                    }
                    printWriter.println("  ");
                }
            }
        }
        synchronized (this.mNotificationLock) {
            if (!z) {
                if (!dumpFilter.normalPriority) {
                    dumpNotificationRecords(printWriter, dumpFilter);
                }
                int size2 = this.mHighDataSizeNotificaitonList.size();
                if (size2 > 0) {
                    printWriter.println("  HighDataSizeNotificaitonList, size=" + size2);
                    for (int i2 = 0; i2 < size2; i2++) {
                        printWriter.println("    [" + i2 + "] " + ((String) this.mHighDataSizeNotificaitonList.get(i2)));
                    }
                    printWriter.println("  ");
                }
                printWriter.println("  TimeoutPendingIntent:");
                if (this.mTimeoutPendingIntent.isEmpty()) {
                    printWriter.println("    None");
                } else {
                    for (Map.Entry entry : this.mTimeoutPendingIntent.entrySet()) {
                        printWriter.println("    key : " + ((String) entry.getKey()) + ", intent : " + entry.getValue());
                    }
                }
                this.mNotificationReminder.dump(printWriter);
                if (!dumpFilter.filtered) {
                    int size3 = this.mLights.size();
                    if (size3 > 0) {
                        printWriter.println("  Lights List:");
                        for (int i3 = 0; i3 < size3; i3++) {
                            if (i3 == size3 - 1) {
                                printWriter.print("  > ");
                            } else {
                                printWriter.print("    ");
                            }
                            printWriter.println((String) this.mLights.get(i3));
                        }
                        printWriter.println("  ");
                    }
                    printWriter.println("  mUseAttentionLight=" + this.mUseAttentionLight);
                    printWriter.println("  mHasLight=" + this.mHasLight);
                    printWriter.println("  mNotificationPulseEnabled=" + this.mNotificationPulseEnabled);
                    printWriter.println("  mSoundNotificationKey=" + this.mSoundNotificationKey);
                    printWriter.println("  mVibrateNotificationKey=" + this.mVibrateNotificationKey);
                    printWriter.println("  mDisableNotificationEffects=" + this.mDisableNotificationEffects);
                    printWriter.println("  mCallState=" + callStateToString(this.mCallState));
                    printWriter.println("  mSystemReady=" + this.mSystemReady);
                    printWriter.println("  mMaxPackageEnqueueRate=" + this.mMaxPackageEnqueueRate);
                    printWriter.println("  hideSilentStatusBar=" + this.mPreferencesHelper.shouldHideSilentStatusIcons());
                }
                printWriter.println("  mArchive=" + this.mArchive.toString());
                this.mArchive.dumpImpl(printWriter, dumpFilter);
                if (!z) {
                    int size4 = this.mEnqueuedNotifications.size();
                    if (size4 > 0) {
                        printWriter.println("  Enqueued Notification List:");
                        for (int i4 = 0; i4 < size4; i4++) {
                            NotificationRecord notificationRecord = (NotificationRecord) this.mEnqueuedNotifications.get(i4);
                            if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                                notificationRecord.dump(printWriter, "    ", getContext(), dumpFilter.redact);
                            }
                        }
                        printWriter.println("  ");
                    }
                    this.mSnoozeHelper.dump(printWriter, dumpFilter);
                }
            }
            printWriter.println("\n  SCPM Version Info:");
            printWriter.println("      Conversation App SCPM Version=" + this.mConversationAppPolicyVersion);
            printWriter.println("      Ongoing Dismiss SCPM Version=" + this.mOngoingDismissExceptionPolicyVersion);
            printWriter.println("\n  History Notification List:");
            int size5 = this.mConversationHistoryAppList.size();
            for (int i5 = 0; i5 < size5; i5++) {
                String obj = this.mConversationHistoryAppList.get(i5).toString();
                try {
                    NotificationHistory readFilteredNotificationHistoryForDump = this.mHistoryManager.readFilteredNotificationHistoryForDump(0, obj, 20);
                    List<NotificationHistory.HistoricalNotification> notificationsToWrite = readFilteredNotificationHistoryForDump.getNotificationsToWrite();
                    if (!notificationsToWrite.isEmpty()) {
                        printWriter.print("    ");
                        printWriter.print(obj);
                        printWriter.print("(");
                        printWriter.print(notificationsToWrite.size());
                        printWriter.println(")");
                        printWriter.println("        Notifications:");
                    }
                    for (NotificationHistory.HistoricalNotification historicalNotification : notificationsToWrite) {
                        printWriter.print("            ");
                        printWriter.println(historicalNotification.toString());
                    }
                    String[] pooledStringsToWrite = readFilteredNotificationHistoryForDump.getPooledStringsToWrite();
                    if (pooledStringsToWrite.length > 0) {
                        printWriter.println("        Pooled strings:");
                    }
                    for (int i6 = 0; i6 < pooledStringsToWrite.length; i6++) {
                        printWriter.print("            ");
                        printWriter.print(i6);
                        printWriter.print(") ");
                        printWriter.println(pooledStringsToWrite[i6]);
                    }
                    Trace.traceEnd(524288L);
                    Log.d("NotificationService", "getNotificationHistory for dump pkg=" + obj);
                } catch (Throwable th) {
                    Trace.traceEnd(524288L);
                    Log.d("NotificationService", "getNotificationHistory for dump pkg=" + obj);
                    throw th;
                }
            }
            int size6 = this.mNotiPermissionHistoryList.size();
            printWriter.println("  mNotiPermissionHistoryList.size=" + size6);
            for (int i7 = 0; i7 < size6; i7++) {
                printWriter.println("    [" + i7 + "] " + ((String) this.mNotiPermissionHistoryList.get(i7)));
            }
            printWriter.println("  mIsOverflowAppListLoaded=" + this.mIsOverflowAppListLoaded);
            printWriter.println("  mCountForOverflowAppList=" + this.mCountForOverflowAppList);
            if (!z) {
                printWriter.println("\n  Ranking Config:");
                this.mRankingHelper.dump(printWriter, "    ", dumpFilter);
                printWriter.println("\n Notification Preferences:");
                this.mPreferencesHelper.dump(printWriter, "    ", dumpFilter, arrayMap);
                printWriter.println("\n  Notification listeners:");
                this.mListeners.dump(printWriter, dumpFilter);
                printWriter.print("    mListenerHints: ");
                printWriter.println(this.mListenerHints);
                printWriter.print("    mListenersDisablingEffects: (");
                int size7 = this.mListenersDisablingEffects.size();
                for (int i8 = 0; i8 < size7; i8++) {
                    int keyAt = this.mListenersDisablingEffects.keyAt(i8);
                    if (i8 > 0) {
                        printWriter.print(';');
                    }
                    printWriter.print("hint[" + keyAt + "]:");
                    ArraySet arraySet = (ArraySet) this.mListenersDisablingEffects.valueAt(i8);
                    int size8 = arraySet.size();
                    for (int i9 = 0; i9 < size8; i9++) {
                        if (i9 > 0) {
                            printWriter.print(',');
                        }
                        ComponentName componentName = (ComponentName) arraySet.valueAt(i9);
                        if (componentName != null) {
                            printWriter.print(componentName);
                        }
                    }
                }
                printWriter.println(')');
                printWriter.println("\n  Notification assistant services:");
                this.mAssistants.dump(printWriter, dumpFilter);
            }
            if (!dumpFilter.filtered || z) {
                printWriter.println("\n  Zen Mode:");
                printWriter.print("    mInterruptionFilter=");
                printWriter.println(this.mInterruptionFilter);
                this.mZenModeHelper.dump(printWriter, "    ");
                printWriter.println("\n  Zen Log:");
                ZenLog.dump(printWriter, "    ");
            }
            printWriter.println("\n  Condition providers:");
            this.mConditionProviders.dump(printWriter, dumpFilter);
            printWriter.println("\n  Group summaries:");
            for (Map.Entry entry2 : this.mSummaryByGroupKey.entrySet()) {
                NotificationRecord notificationRecord2 = (NotificationRecord) entry2.getValue();
                printWriter.println("    " + ((String) entry2.getKey()) + " -> " + notificationRecord2.getKey());
                if (this.mNotificationsByKey.get(notificationRecord2.getKey()) != notificationRecord2) {
                    printWriter.println("!!!!!!LEAK: Record not found in mNotificationsByKey.");
                    notificationRecord2.dump(printWriter, "      ", getContext(), dumpFilter.redact);
                }
            }
            if (!z) {
                printWriter.println("\n  Usage Stats:");
                this.mUsageStats.dump(printWriter, "    ", dumpFilter);
            }
        }
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$16 */
    public class C199416 implements NotificationManagerInternal {
        public C199416() {
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public NotificationChannel getNotificationChannel(String str, int i, String str2) {
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, i, str2, false);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public NotificationChannelGroup getNotificationChannelGroup(String str, int i, String str2) {
            return NotificationManagerService.this.mPreferencesHelper.getGroupForChannel(str, i, str2);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void enqueueNotification(String str, String str2, int i, int i2, String str3, int i3, Notification notification, int i4) {
            NotificationManagerService.this.enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, false);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void enqueueNotification(String str, String str2, int i, int i2, String str3, int i3, Notification notification, int i4, boolean z) {
            NotificationManagerService.this.enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, z);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void cancelNotification(String str, String str2, int i, int i2, String str3, int i3, int i4) {
            NotificationManagerService.this.cancelNotificationInternal(str, str2, i, i2, str3, i3, i4);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void removeForegroundServiceFlagFromNotification(final String str, final int i, final int i2) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.C199416.this.lambda$removeForegroundServiceFlagFromNotification$0(str, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeForegroundServiceFlagFromNotification$0(String str, int i, int i2) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                removeFlagFromNotificationLocked(str, i, i2, 64);
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void removeUserInitiatedJobFlagFromNotification(final String str, final int i, final int i2) {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.C199416.this.lambda$removeUserInitiatedJobFlagFromNotification$1(str, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeUserInitiatedJobFlagFromNotification$1(String str, int i, int i2) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                removeFlagFromNotificationLocked(str, i, i2, 32768);
            }
        }

        public final void removeFlagFromNotificationLocked(String str, int i, int i2, int i3) {
            boolean z;
            if (NotificationManagerService.this.getNotificationCount(str, i2) > 25) {
                NotificationManagerService.this.mUsageStats.registerOverCountQuota(str);
                z = true;
            } else {
                z = false;
            }
            Slog.d("NotificationService", "removeFlagFromNotificationLocked pkg = " + str + " notificationId = " + i + ", flag = " + i3);
            if (z) {
                NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(str, null, i, i2);
                if (findNotificationLocked != null) {
                    if (NotificationManagerService.DBG) {
                        String str2 = i3 == 64 ? "FGS" : "UIJ";
                        Slog.d("NotificationService", "Remove " + str2 + " flag not allow. Cancel " + str2 + " notification");
                    }
                    NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked);
                    NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, false, 8, true, null, SystemClock.elapsedRealtime());
                    return;
                }
                return;
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            List findNotificationsByListLocked = notificationManagerService.findNotificationsByListLocked(notificationManagerService.mEnqueuedNotifications, str, null, i, i2);
            for (int i4 = 0; i4 < findNotificationsByListLocked.size(); i4++) {
                NotificationRecord notificationRecord = (NotificationRecord) findNotificationsByListLocked.get(i4);
                if (notificationRecord != null) {
                    StatusBarNotification sbn = notificationRecord.getSbn();
                    sbn.getNotification().flags = notificationRecord.mOriginalFlags & (~i3);
                    Slog.d("NotificationService", "removeFlag from mEnqueuedNotifications , flag = " + sbn.getNotification().flags);
                }
            }
            NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
            NotificationRecord findNotificationByListLocked = notificationManagerService2.findNotificationByListLocked(notificationManagerService2.mNotificationList, str, null, i, i2);
            if (findNotificationByListLocked != null) {
                StatusBarNotification sbn2 = findNotificationByListLocked.getSbn();
                sbn2.getNotification().flags = findNotificationByListLocked.mOriginalFlags & (~i3);
                NotificationManagerService notificationManagerService3 = NotificationManagerService.this;
                notificationManagerService3.mRankingHelper.sort(notificationManagerService3.mNotificationList);
                NotificationManagerService.this.mListeners.notifyPostedLocked(findNotificationByListLocked, findNotificationByListLocked);
                Slog.d("NotificationService", "removeFlag from mNotificationList , flag = " + sbn2.getNotification().flags);
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void onConversationRemoved(String str, int i, Set set) {
            NotificationManagerService.this.onConversationRemovedInternal(str, i, set);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public int getNumNotificationChannelsForPackage(String str, int i, boolean z) {
            return NotificationManagerService.this.getNumNotificationChannelsForPackage(str, i, z);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public boolean areNotificationsEnabledForPackage(String str, int i) {
            return NotificationManagerService.this.areNotificationsEnabledForPackageInt(str, i);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void sendReviewPermissionsNotification() {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            if (notificationManagerService.mShowReviewPermissionsNotification) {
                notificationManagerService.checkCallerIsSystem();
                ((NotificationManager) NotificationManagerService.this.getContext().getSystemService(NotificationManager.class)).notify("NotificationService", 71, NotificationManagerService.this.createReviewPermissionsNotification());
                Settings.Global.putInt(NotificationManagerService.this.getContext().getContentResolver(), "review_permissions_notification_state", 3);
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void cleanupHistoryFiles() {
            NotificationManagerService.this.checkCallerIsSystem();
            NotificationManagerService.this.mHistoryManager.cleanupHistoryFiles();
        }
    }

    public int getNumNotificationChannelsForPackage(String str, int i, boolean z) {
        return this.mPreferencesHelper.getNotificationChannels(str, i, z).getList().size();
    }

    public void cancelNotificationInternal(String str, String str2, int i, int i2, String str3, int i3, int i4) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(i2, i, i4, true, false, "cancelNotificationWithTag", str);
        int resolveNotificationUid = resolveNotificationUid(str2, str, i, handleIncomingUser);
        if (resolveNotificationUid == -1) {
            Slog.w("NotificationService", str2 + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + i + " trying to cancel notification for nonexistent pkg " + str + " in user " + handleIncomingUser);
            return;
        }
        if (!Objects.equals(str, str2)) {
            synchronized (this.mNotificationLock) {
                NotificationRecord findNotificationLocked = findNotificationLocked(str, str3, i3, handleIncomingUser);
                if (findNotificationLocked != null && !Objects.equals(str2, findNotificationLocked.getSbn().getOpPkg())) {
                    throw new SecurityException(str2 + " does not have permission to cancel a notification they did not post " + str3 + " " + i3);
                }
            }
        }
        cancelNotification(resolveNotificationUid, i2, str, str3, i3, 0, isCallingUidSystem() ? 0 : 33856, false, handleIncomingUser, 8, null);
    }

    public boolean isNotificationShownInternal(String str, String str2, int i, int i2) {
        boolean z;
        synchronized (this.mNotificationLock) {
            z = findNotificationLocked(str, str2, i, i2) != null;
        }
        return z;
    }

    public void enqueueEdgeNotificationInternal(final String str, String str2, int i, int i2, final int i3, final Bundle bundle, int i4) {
        if (DBG) {
            Slog.v("NotificationService", "enqueueEdgeNotificationInternal: pkg=" + str + " id=" + i3 + " extra=" + bundle);
        }
        final int handleIncomingUser = ActivityManager.handleIncomingUser(i2, i, i4, true, false, "enqueueNotificationExtra", str);
        new UserHandle(handleIncomingUser);
        if (str == null || bundle == null) {
            throw new IllegalArgumentException("null not allowed: pkg=" + str + " id=" + i3 + " notification=" + bundle);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.17
            @Override // java.lang.Runnable
            public void run() {
                NotificationManagerService.this.mListeners.enqueueEdgeNotification(str, i3, bundle, handleIncomingUser);
            }
        });
    }

    public void removeEdgeNotificationInternal(int i, int i2, final String str, final int i3, final Bundle bundle, final int i4) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.18
            @Override // java.lang.Runnable
            public void run() {
                NotificationManagerService.this.mListeners.removeEdgeNotification(str, i3, bundle, i4);
            }
        });
    }

    public void enqueueNotificationInternal(String str, String str2, int i, int i2, String str3, int i3, Notification notification, int i4, boolean z) {
        enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, false, z);
    }

    public void enqueueNotificationInternal(String str, String str2, int i, int i2, String str3, int i3, Notification notification, int i4, boolean z, boolean z2) {
        PostNotificationTracker acquireWakeLockForPost = acquireWakeLockForPost(str, i);
        try {
            if (enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, z, acquireWakeLockForPost, z2)) {
            }
        } finally {
            acquireWakeLockForPost.cancel();
        }
    }

    public final PostNotificationTracker acquireWakeLockForPost(final String str, final int i) {
        if (this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.WAKE_LOCK_FOR_POSTING_NOTIFICATION) && ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda14
            public final Object getOrThrow() {
                Boolean lambda$acquireWakeLockForPost$7;
                lambda$acquireWakeLockForPost$7 = NotificationManagerService.lambda$acquireWakeLockForPost$7();
                return lambda$acquireWakeLockForPost$7;
            }
        })).booleanValue()) {
            return (PostNotificationTracker) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda15
                public final Object getOrThrow() {
                    NotificationManagerService.PostNotificationTracker lambda$acquireWakeLockForPost$8;
                    lambda$acquireWakeLockForPost$8 = NotificationManagerService.this.lambda$acquireWakeLockForPost$8(str, i);
                    return lambda$acquireWakeLockForPost$8;
                }
            });
        }
        return this.mPostNotificationTrackerFactory.newTracker(null);
    }

    public static /* synthetic */ Boolean lambda$acquireWakeLockForPost$7() {
        return Boolean.valueOf(DeviceConfig.getBoolean("systemui", "nms_notify_wakelock", false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ PostNotificationTracker lambda$acquireWakeLockForPost$8(String str, int i) {
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "NotificationManagerService:post:" + str);
        newWakeLock.setWorkSource(new WorkSource(i, str));
        newWakeLock.acquire(30000L);
        return this.mPostNotificationTrackerFactory.newTracker(newWakeLock);
    }

    /* JADX WARN: Removed duplicated region for block: B:156:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0451 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0453  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean enqueueNotificationInternal(String str, String str2, int i, int i2, String str3, int i3, Notification notification, int i4, boolean z, PostNotificationTracker postNotificationTracker, boolean z2) {
        Context context;
        Notification notification2;
        ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotification;
        Notification notification3;
        NotificationChannel conversationNotificationChannel;
        NotificationRecord notificationRecord;
        ShortcutHelper shortcutHelper;
        UserHandle userHandle;
        ShortcutInfo shortcutInfo;
        char c;
        NotificationRecord notificationRecord2;
        char c2;
        String str4;
        int size;
        if (DBG) {
            Slog.v("NotificationService", "enqueueNotificationInternal: pkg=" + str + " id=" + i3 + " notification=" + notification);
        }
        if (notification == null || isSHealthFgsNotification(str, i3, notification.flags)) {
            return false;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.19
            @Override // java.lang.Runnable
            public void run() {
                PowerManager.WakeLock wakeLock = NotificationManagerService.this.mWakeLockForAssistantDelay;
                if (wakeLock != null && wakeLock.isHeld()) {
                    NotificationManagerService.this.mWakeLockForAssistantDelay.release();
                }
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.mWakeLockForAssistantDelay == null) {
                    notificationManagerService.mWakeLockForAssistantDelay = ((PowerManager) notificationManagerService.getContext().getSystemService(PowerManager.class)).newWakeLock(1, "Prevent Sleep by AssistantDelay");
                }
                NotificationManagerService.this.mWakeLockForAssistantDelay.acquire(500L);
            }
        });
        if (notification.parcelDataSize > 100000) {
            Log.e("NotificationService", "notification pkg : " + str + " has too high data size(" + notification.parcelDataSize + ") above 100000");
        }
        if (notification.parcelDataSize > 450000) {
            Log.e("NotificationService", "parcel data size is too large. pkg : " + str + " has too high data size(" + notification.parcelDataSize + ") above 450000");
            notification.contentView = null;
            notification.bigContentView = null;
            notification.headsUpContentView = null;
            Notification notification4 = notification.publicVersion;
            if (notification4 != null) {
                notification4.contentView = null;
                notification4.bigContentView = null;
                notification4.headsUpContentView = null;
            }
        }
        if (str == null) {
            throw new IllegalArgumentException("null not allowed: pkg=" + str + " id=" + i3 + " notification=" + notification);
        }
        int handleIncomingUser = ActivityManager.handleIncomingUser(i2, i, i4, true, false, "enqueueNotification", str);
        UserHandle of = UserHandle.of(handleIncomingUser);
        int resolveNotificationUid = resolveNotificationUid(str2, str, i, handleIncomingUser);
        if (resolveNotificationUid == -1) {
            throw new SecurityException("Caller " + str2 + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + i + " trying to post for invalid pkg " + str + " in user " + i4);
        }
        if (notification.parcelDataSize > 100000) {
            makeHighDataSizeLog(of.getIdentifier() + "|" + str + "|" + i3 + "|" + str3 + "|" + resolveNotificationUid, notification);
        }
        checkRestrictedCategories(notification);
        try {
            if (SemPersonaManager.isNotificationSanitizePolicyForSF(getContext(), handleIncomingUser, str)) {
                try {
                    context = getContext().createApplicationContext(getContext().getPackageManager().getApplicationInfoAsUser(str, IInstalld.FLAG_FORCE, handleIncomingUser), 4);
                } catch (Exception unused) {
                    context = null;
                }
                if (context != null) {
                    String string = getContext().getResources().getString(R.string.resolver_cross_profile_blocked);
                    String string2 = getContext().getResources().getString(17042510);
                    Notification.Builder builder = new Notification.Builder(context, notification.getChannelId());
                    builder.setContentTitle(string).setContentText(string2).setColor(notification.color).setGroup(notification.getGroup()).setContentIntent(notification.contentIntent).setSmallIcon(notification.getSmallIcon());
                    int i5 = notification.flags;
                    Notification build = builder.build();
                    build.flags = i5 | build.flags;
                    Bundle bundle = new Bundle();
                    build.extras = bundle;
                    bundle.putCharSequence("android.title", Notification.safeCharSequence(string));
                    build.extras.putCharSequence("android.text", Notification.safeCharSequence(string2));
                    notification2 = build;
                    applyForegroundServiceNotification = this.mAmi.applyForegroundServiceNotification(notification2, str3, i3, str, handleIncomingUser);
                    fixNotification(notification2, str, str3, i3, handleIncomingUser, resolveNotificationUid, applyForegroundServiceNotification, ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class)) == null ? !r1.isNotificationAssociatedWithAnyUserInitiatedJobs(i3, handleIncomingUser, str) : true);
                    if (applyForegroundServiceNotification != ActivityManagerInternal.ServiceNotificationPolicy.UPDATE_ONLY && !isNotificationShownInternal(str, str3, i3, handleIncomingUser)) {
                        reportForegroundServiceUpdate(false, notification2, i3, str, handleIncomingUser);
                        return false;
                    }
                    Notification notification5 = notification2;
                    StatusBarNotification statusBarNotification = new StatusBarNotification(str, str2, i3, str3, resolveNotificationUid, i2, notification2, of, (String) null, System.currentTimeMillis());
                    String channelId = notification5.getChannelId();
                    if (this.mIsTelevision) {
                        notification3 = notification5;
                    } else {
                        notification3 = notification5;
                        if (new Notification.TvExtender(notification3).getChannelId() != null) {
                            channelId = new Notification.TvExtender(notification3).getChannelId();
                        }
                    }
                    String str5 = channelId;
                    conversationNotificationChannel = this.mPreferencesHelper.getConversationNotificationChannel(str, resolveNotificationUid, str5, statusBarNotification.getShortcutId(), true, false);
                    if (conversationNotificationChannel != null) {
                        Slog.e("NotificationService", "No Channel found for pkg=" + str + ", channelId=" + str5 + ", id=" + i3 + ", tag=" + str3 + ", opPkg=" + str2 + ", callingUid=" + i + ", userId=" + handleIncomingUser + ", incomingUserId=" + i4 + ", notificationUid=" + resolveNotificationUid + ", notification=" + notification3);
                        if (!this.mPermissionHelper.hasPermission(resolveNotificationUid)) {
                            return false;
                        }
                        doChannelWarningToast(resolveNotificationUid, "Developer warning for package \"" + str + "\"\nFailed to post notification on channel \"" + str5 + "\"\nSee log for more details");
                        return false;
                    }
                    NotificationRecord notificationRecord3 = new NotificationRecord(getContext(), statusBarNotification, conversationNotificationChannel);
                    notificationRecord3.setIsAppImportanceLocked(this.mPermissionHelper.isPermissionUserSet(str, handleIncomingUser));
                    IApplicationPolicy applicationPolicyService = getApplicationPolicyService();
                    if (applicationPolicyService != null) {
                        try {
                            if (!applicationPolicyService.isStatusBarNotificationAllowedAsUser(str, handleIncomingUser) && !applicationPolicyService.handleStatusBarNotificationNotAllowedAsUser(str, handleIncomingUser, notification3)) {
                                Log.d("NotificationService", "notification was blocked by policy, pkg - " + str);
                                return false;
                            }
                        } catch (RemoteException unused2) {
                            Log.d("NotificationService", "Failed in ApplicationPolicy ");
                        }
                    }
                    notificationRecord3.setPostSilently(z);
                    notificationRecord3.setFlagBubbleRemoved(false);
                    notificationRecord3.setPkgAllowedAsConvo(this.mMsgPkgsAllowedAsConvos.contains(str));
                    notificationRecord3.setImportanceFixed(this.mPermissionHelper.isPermissionFixed(str, handleIncomingUser));
                    if (notification3.isFgsOrUij()) {
                        if (((conversationNotificationChannel.getUserLockedFields() & 4) == 0 || !conversationNotificationChannel.isUserVisibleTaskShown()) && (notificationRecord3.getImportance() == 1 || notificationRecord3.getImportance() == 0)) {
                            conversationNotificationChannel.setImportance(2);
                            notificationRecord3.setSystemImportance(2);
                            if (!conversationNotificationChannel.isUserVisibleTaskShown()) {
                                conversationNotificationChannel.unlockFields(4);
                                conversationNotificationChannel.setUserVisibleTaskShown(true);
                            }
                            notificationRecord = notificationRecord3;
                            this.mPreferencesHelper.updateNotificationChannel(str, resolveNotificationUid, conversationNotificationChannel, false, i, isCallerIsSystemOrSystemUi());
                            notificationRecord.updateNotificationChannel(conversationNotificationChannel);
                            shortcutHelper = this.mShortcutHelper;
                            if (shortcutHelper == null) {
                                userHandle = of;
                                shortcutInfo = shortcutHelper.getValidShortcutInfo(notification3.getShortcutId(), str, userHandle);
                            } else {
                                userHandle = of;
                                shortcutInfo = null;
                            }
                            if (notification3.getShortcutId() != null && shortcutInfo == null) {
                                Slog.w("NotificationService", "notification " + notificationRecord.getKey() + " added an invalid shortcut");
                            }
                            notificationRecord.setShortcutInfo(shortcutInfo);
                            notificationRecord.setHasSentValidMsg(this.mPreferencesHelper.hasSentValidMsg(str, resolveNotificationUid));
                            notificationRecord.userDemotedAppFromConvoSpace(this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, resolveNotificationUid));
                            ShortcutInfo shortcutInfo2 = shortcutInfo;
                            if (checkDisqualifyingFeatures(handleIncomingUser, resolveNotificationUid, i3, str3, notificationRecord, notificationRecord.getSbn().getOverrideGroupKey() == null, z2)) {
                                return false;
                            }
                            if (shortcutInfo2 != null) {
                                this.mShortcutHelper.cacheShortcut(shortcutInfo2, userHandle);
                            }
                            ArraySet arraySet = notification3.allPendingIntents;
                            if (arraySet != null && (size = arraySet.size()) > 0) {
                                long notificationAllowlistDuration = ((DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class)).getNotificationAllowlistDuration();
                                for (int i6 = 0; i6 < size; i6++) {
                                    PendingIntent pendingIntent = (PendingIntent) notification3.allPendingIntents.valueAt(i6);
                                    if (pendingIntent != null) {
                                        ActivityManagerInternal activityManagerInternal = this.mAmi;
                                        IIntentSender target = pendingIntent.getTarget();
                                        IBinder iBinder = ALLOWLIST_TOKEN;
                                        activityManagerInternal.setPendingIntentAllowlistDuration(target, iBinder, notificationAllowlistDuration, 0, 310, "NotificationManagerService");
                                        this.mAmi.setPendingIntentAllowBgActivityStarts(pendingIntent.getTarget(), iBinder, 7);
                                    }
                                }
                            }
                            if (this.mIsOverflowAppListLoaded) {
                                c = 1;
                            } else {
                                c = 1;
                                this.mIsOverflowAppListLoaded = true;
                                this.mCountForOverflowAppList = this.mPreferencesHelper.getAppCountOfSendingExcessiveNotifications(this.mLimitNotificationsForOverflowAppList);
                                Slog.i("NotificationService", "OverflowAppList has been loaded. mCountForOverflowAppList=" + this.mCountForOverflowAppList);
                            }
                            if (this.mCountForOverflowAppList >= 3) {
                                Iterator it = this.mLimitNotificationsForOverflowAppList.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        c2 = 0;
                                        break;
                                    }
                                    String[] split = ((String) it.next()).split(",");
                                    if (split[0].equals(notificationRecord.getSbn().getPackageName()) && split[c].equals(notificationRecord.getNotification().getChannelId())) {
                                        notificationRecord.getSbn().getNotification().semFlags |= 32768;
                                        c2 = c;
                                        break;
                                    }
                                }
                                if (c2 != 0) {
                                    String key = notificationRecord.getSbn().getKey();
                                    if (this.mOverflowNotiUpdateTimeMap.containsKey(key)) {
                                        if (this.mHandler.hasMessages(notificationRecord.getUid())) {
                                            this.mHandler.removeMessages(notificationRecord.getUid());
                                        }
                                        long longValue = ((Long) this.mOverflowNotiUpdateTimeMap.get(key)).longValue();
                                        long updateTimeMs = notificationRecord.getUpdateTimeMs() - longValue;
                                        long min = Math.min(this.mCountForOverflowAppList, 10) * 1000;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("for overflow delayed diffTime=");
                                        sb.append(updateTimeMs);
                                        sb.append(" delayTime=");
                                        sb.append(min);
                                        sb.append(" lastUpdateTime=");
                                        sb.append(longValue);
                                        sb.append(" updateTime=");
                                        sb.append(notificationRecord.getUpdateTimeMs());
                                        sb.append(" key=");
                                        str4 = key;
                                        sb.append(str4);
                                        sb.append(" record=");
                                        sb.append(notificationRecord);
                                        Slog.d("NotificationService", sb.toString());
                                        if (updateTimeMs < min) {
                                            long j = min - updateTimeMs;
                                            this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, notificationRecord.getUid(), handleIncomingUser, i, notificationRecord), j);
                                            if (!DBG) {
                                                return true;
                                            }
                                            Slog.d("NotificationService", "for overflow delayed time=" + j);
                                            return true;
                                        }
                                    } else {
                                        str4 = key;
                                    }
                                    notificationRecord2 = notificationRecord;
                                    Slog.d("NotificationService", "for overflow update time key=" + str4 + " updateTime=" + notificationRecord2.getUpdateTimeMs() + " record=" + notificationRecord2);
                                    this.mOverflowNotiUpdateTimeMap.put(str4, Long.valueOf(notificationRecord2.getUpdateTimeMs()));
                                } else {
                                    notificationRecord2 = notificationRecord;
                                    if (DBG) {
                                        Slog.d("NotificationService", "for overflow  isOverflowPackage is false - pkg = " + notificationRecord2.getSbn().getPackageName());
                                    }
                                }
                            } else {
                                notificationRecord2 = notificationRecord;
                            }
                            this.mUsageStats.registerEnqueuedByApp(str);
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                boolean z3 = this.mActivityManager.getPackageImportance(str) == 100;
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                this.mHandler.post(new EnqueueNotificationRunnable(handleIncomingUser, notificationRecord2, z3, postNotificationTracker));
                                return true;
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                        if (!conversationNotificationChannel.isUserVisibleTaskShown() && !TextUtils.isEmpty(str5) && !"miscellaneous".equals(str5)) {
                            conversationNotificationChannel.setUserVisibleTaskShown(true);
                            notificationRecord3.updateNotificationChannel(conversationNotificationChannel);
                        }
                    }
                    notificationRecord = notificationRecord3;
                    shortcutHelper = this.mShortcutHelper;
                    if (shortcutHelper == null) {
                    }
                    if (notification3.getShortcutId() != null) {
                        Slog.w("NotificationService", "notification " + notificationRecord.getKey() + " added an invalid shortcut");
                    }
                    notificationRecord.setShortcutInfo(shortcutInfo);
                    notificationRecord.setHasSentValidMsg(this.mPreferencesHelper.hasSentValidMsg(str, resolveNotificationUid));
                    notificationRecord.userDemotedAppFromConvoSpace(this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, resolveNotificationUid));
                    ShortcutInfo shortcutInfo22 = shortcutInfo;
                    if (checkDisqualifyingFeatures(handleIncomingUser, resolveNotificationUid, i3, str3, notificationRecord, notificationRecord.getSbn().getOverrideGroupKey() == null, z2)) {
                    }
                } else {
                    Slog.d("NotificationService", "pkg = " + str + " Context is null, Notification can't be sanitized pkg = ");
                }
            }
            fixNotification(notification2, str, str3, i3, handleIncomingUser, resolveNotificationUid, applyForegroundServiceNotification, ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class)) == null ? !r1.isNotificationAssociatedWithAnyUserInitiatedJobs(i3, handleIncomingUser, str) : true);
            if (applyForegroundServiceNotification != ActivityManagerInternal.ServiceNotificationPolicy.UPDATE_ONLY) {
            }
            Notification notification52 = notification2;
            StatusBarNotification statusBarNotification2 = new StatusBarNotification(str, str2, i3, str3, resolveNotificationUid, i2, notification2, of, (String) null, System.currentTimeMillis());
            String channelId2 = notification52.getChannelId();
            if (this.mIsTelevision) {
            }
            String str52 = channelId2;
            conversationNotificationChannel = this.mPreferencesHelper.getConversationNotificationChannel(str, resolveNotificationUid, str52, statusBarNotification2.getShortcutId(), true, false);
            if (conversationNotificationChannel != null) {
            }
        } catch (Exception e) {
            if (notification2.isForegroundService()) {
                throw new SecurityException("Invalid FGS notification", e);
            }
            Slog.e("NotificationService", "Cannot fix notification", e);
            return false;
        }
        notification2 = notification;
        applyForegroundServiceNotification = this.mAmi.applyForegroundServiceNotification(notification2, str3, i3, str, handleIncomingUser);
    }

    public final void onConversationRemovedInternal(String str, int i, Set set) {
        checkCallerIsSystem();
        Preconditions.checkStringNotEmpty(str);
        this.mHistoryManager.deleteConversations(str, i, set);
        Iterator it = this.mPreferencesHelper.deleteConversations(str, i, set, 1000, true).iterator();
        while (it.hasNext()) {
            cancelAllNotificationsInt(MY_UID, MY_PID, str, (String) it.next(), 0, 0, true, UserHandle.getUserId(i), 20, null);
        }
        handleSavePolicyFile();
    }

    public final void makeStickyHun(Notification notification, String str, int i) {
        if (this.mPermissionHelper.hasRequestedPermission("android.permission.USE_FULL_SCREEN_INTENT", str, i)) {
            notification.flags |= 16384;
        }
        if (notification.contentIntent == null) {
            notification.contentIntent = notification.fullScreenIntent;
        }
        notification.fullScreenIntent = null;
    }

    public void fixNotification(Notification notification, String str, String str2, int i, int i2, int i3, ActivityManagerInternal.ServiceNotificationPolicy serviceNotificationPolicy, boolean z) {
        ApplicationInfo applicationInfoAsUser = this.mPackageManagerClient.getApplicationInfoAsUser(str, 268435456, i2 == -1 ? 0 : i2);
        Notification.addFieldsFromContext(applicationInfoAsUser, notification);
        if (notification.isForegroundService() && serviceNotificationPolicy == ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE) {
            notification.flags &= -65;
        }
        if (notification.isUserInitiatedJob() && z) {
            notification.flags &= -32769;
        }
        if (notification.isFgsOrUij()) {
            notification.flags &= -17;
        }
        if (this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.ALLOW_DISMISS_ONGOING)) {
            if ((notification.flags & 2) > 0 && (canBeNonDismissible(applicationInfoAsUser, notification) || isOngoingDismissExceptionPackage(applicationInfoAsUser.packageName, i, str2))) {
                notification.flags |= IInstalld.FLAG_FORCE;
            } else {
                notification.flags &= -8193;
            }
        }
        int checkPermission = getContext().checkPermission("android.permission.USE_COLORIZED_NOTIFICATIONS", -1, i3);
        if (checkPermission == 0) {
            notification.flags |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
        } else {
            notification.flags &= -2049;
        }
        Notification notification2 = notification.publicVersion;
        if (notification2 != null) {
            if (checkPermission == 0) {
                notification2.flags |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
            } else {
                notification2.flags &= -2049;
            }
        }
        if (notification.extras.getBoolean("android.allowDuringSetup", false) && getContext().checkPermission("android.permission.NOTIFICATION_DURING_SETUP", -1, i3) != 0) {
            notification.extras.remove("android.allowDuringSetup");
            if (DBG) {
                Slog.w("NotificationService", "warning: pkg " + str + " attempting to show during setup without holding perm android.permission.NOTIFICATION_DURING_SETUP");
            }
        }
        notification.flags &= -16385;
        boolean z2 = true;
        if (notification.fullScreenIntent != null) {
            if (this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.FSI_FORCE_DEMOTE)) {
                makeStickyHun(notification, str, i2);
            } else {
                AttributionSource build = new AttributionSource.Builder(i3).setPackageName(str).build();
                boolean isEnabled = this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.SHOW_STICKY_HUN_FOR_DENIED_FSI);
                if (!checkUseFullScreenIntentPermission(build, applicationInfoAsUser, isEnabled, true)) {
                    if (isEnabled) {
                        makeStickyHun(notification, str, i2);
                    } else {
                        notification.fullScreenIntent = null;
                        Slog.w("NotificationService", "Package " + str + ": Use of fullScreenIntent requires theUSE_FULL_SCREEN_INTENT permission");
                    }
                }
            }
        }
        Notification.Action[] actionArr = notification.actions;
        if (actionArr != null) {
            int length = actionArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    z2 = false;
                    break;
                } else if (notification.actions[i4] == null) {
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                ArrayList arrayList = new ArrayList();
                for (int i5 = 0; i5 < length; i5++) {
                    Notification.Action action = notification.actions[i5];
                    if (action != null) {
                        arrayList.add(action);
                    }
                }
                if (arrayList.size() != 0) {
                    notification.actions = (Notification.Action[]) arrayList.toArray(new Notification.Action[0]);
                } else {
                    notification.actions = null;
                }
            }
        }
        if (notification.isStyle(Notification.CallStyle.class)) {
            ArrayList actionsListWithSystemActions = ((Notification.CallStyle) Notification.Builder.recoverBuilder(getContext(), notification).getStyle()).getActionsListWithSystemActions();
            Notification.Action[] actionArr2 = new Notification.Action[actionsListWithSystemActions.size()];
            notification.actions = actionArr2;
            actionsListWithSystemActions.toArray(actionArr2);
        }
        if ((notification.isStyle(Notification.MediaStyle.class) || notification.isStyle(Notification.DecoratedMediaCustomViewStyle.class)) && getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", -1, i3) != 0) {
            notification.extras.remove("android.mediaRemoteDevice");
            notification.extras.remove("android.mediaRemoteIcon");
            notification.extras.remove("android.mediaRemoteIntent");
            if (DBG) {
                Slog.w("NotificationService", "Package " + str + ": Use of setRemotePlayback requires the MEDIA_CONTENT_CONTROL permission");
            }
        }
        if (notification.extras.containsKey("android.substName") && getContext().checkPermission("android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME", -1, i3) != 0) {
            notification.extras.remove("android.substName");
            if (DBG) {
                Slog.w("NotificationService", "warning: pkg " + str + " attempting to substitute app name without holding perm android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME");
            }
        }
        checkRemoteViews(str, str2, i, notification);
    }

    public final boolean canBeNonDismissible(ApplicationInfo applicationInfo, Notification notification) {
        return isEnterpriseExempted(applicationInfo) || notification.isStyle(Notification.CallStyle.class) || isDefaultSearchSelectorPackage(applicationInfo.packageName);
    }

    public final boolean isDefaultSearchSelectorPackage(String str) {
        return Objects.equals(this.mDefaultSearchSelectorPkg, str);
    }

    public final boolean isOngoingDismissExceptionPackage(String str, int i, String str2) {
        String str3 = str + "|" + i + "|" + str2;
        Iterator it = this.mOngoingDismissExceptionKeyList.iterator();
        while (it.hasNext()) {
            if (str3.contains((String) it.next())) {
                Slog.d("NotificationService", "isOngoingDismissExceptionPackage - key = " + str3);
                return true;
            }
        }
        return false;
    }

    public final boolean isSHealthFgsNotification(String str, int i, int i2) {
        if ("com.sec.android.app.shealth".equals(str) && i == 1105 && (i2 & 64) != 0) {
            Slog.d("NotificationService", "enqueueNotificationInternal: SHealth FGS noti so block - pkg=" + str + " id=" + i);
            ArrayList arrayList = new ArrayList();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                for (Signature signature : getContext().getPackageManager().getPackageInfo("com.sec.android.app.shealth", 64).signatures) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : messageDigest.digest(signature.toCharsString().getBytes())) {
                        int i3 = (b >> 4) & 15;
                        sb.append((char) (i3 >= 10 ? (i3 + 97) - 10 : i3 + 48));
                        int i4 = b & 15;
                        sb.append((char) (i4 >= 10 ? (i4 + 97) - 10 : i4 + 48));
                    }
                    arrayList.add(sb.toString());
                }
            } catch (Exception e) {
                Log.e("NotificationService", "hasValidSignature : " + e);
            }
            Iterator it = HEALTH_KEY_LIST.iterator();
            while (it.hasNext()) {
                if (arrayList.contains((String) it.next())) {
                    Log.i("NotificationService", "key matched");
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isEnterpriseExempted(ApplicationInfo applicationInfo) {
        DevicePolicyManagerInternal devicePolicyManagerInternal = this.mDpm;
        if (devicePolicyManagerInternal == null || !(devicePolicyManagerInternal.isActiveProfileOwner(applicationInfo.uid) || this.mDpm.isActiveDeviceOwner(applicationInfo.uid))) {
            return this.mSystemExemptFromDismissal && this.mAppOps.checkOpNoThrow(125, applicationInfo.uid, applicationInfo.packageName) == 0;
        }
        return true;
    }

    public final boolean checkUseFullScreenIntentPermission(AttributionSource attributionSource, ApplicationInfo applicationInfo, boolean z, boolean z2) {
        int checkPermissionForPreflight;
        if (applicationInfo.targetSdkVersion < 29) {
            return true;
        }
        if (!z) {
            return getContext().checkPermission("android.permission.USE_FULL_SCREEN_INTENT", attributionSource.getPid(), attributionSource.getUid()) == 0;
        }
        if (z2) {
            checkPermissionForPreflight = this.mPermissionManager.checkPermissionForDataDelivery("android.permission.USE_FULL_SCREEN_INTENT", attributionSource, (String) null);
        } else {
            checkPermissionForPreflight = this.mPermissionManager.checkPermissionForPreflight("android.permission.USE_FULL_SCREEN_INTENT", attributionSource);
            Slog.d("NotificationService", "checkUseFullScreenIntentPermission - permissionResult = " + checkPermissionForPreflight);
        }
        return checkPermissionForPreflight == 0;
    }

    public final void checkRemoteViews(String str, String str2, int i, Notification notification) {
        if (removeRemoteView(str, str2, i, notification.contentView)) {
            notification.contentView = null;
        }
        if (removeRemoteView(str, str2, i, notification.bigContentView)) {
            notification.bigContentView = null;
        }
        if (removeRemoteView(str, str2, i, notification.headsUpContentView)) {
            notification.headsUpContentView = null;
        }
        Notification notification2 = notification.publicVersion;
        if (notification2 != null) {
            if (removeRemoteView(str, str2, i, notification2.contentView)) {
                notification.publicVersion.contentView = null;
            }
            if (removeRemoteView(str, str2, i, notification.publicVersion.bigContentView)) {
                notification.publicVersion.bigContentView = null;
            }
            if (removeRemoteView(str, str2, i, notification.publicVersion.headsUpContentView)) {
                notification.publicVersion.headsUpContentView = null;
            }
        }
    }

    public final boolean removeRemoteView(String str, String str2, int i, RemoteViews remoteViews) {
        if (remoteViews == null) {
            return false;
        }
        int estimateMemoryUsage = remoteViews.estimateMemoryUsage();
        if (estimateMemoryUsage > this.mWarnRemoteViewsSizeBytes && estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            Slog.w("NotificationService", "RemoteViews too large on pkg: " + str + " tag: " + str2 + " id: " + i + " this might be stripped in a future release");
        }
        if (estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            return false;
        }
        this.mUsageStats.registerImageRemoved(str);
        Slog.w("NotificationService", "Removed too large RemoteViews (" + estimateMemoryUsage + " bytes) on pkg: " + str + " tag: " + str2 + " id: " + i);
        return true;
    }

    public final void updateNotificationBubbleFlags(NotificationRecord notificationRecord, boolean z) {
        Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
        if (bubbleMetadata == null) {
            return;
        }
        if (!z) {
            bubbleMetadata.setFlags(bubbleMetadata.getFlags() & (-2));
        }
        if (bubbleMetadata.isBubbleSuppressable()) {
            return;
        }
        bubbleMetadata.setFlags(bubbleMetadata.getFlags() & (-9));
    }

    public void doChannelWarningToast(int i, final CharSequence charSequence) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                NotificationManagerService.this.lambda$doChannelWarningToast$9(charSequence);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doChannelWarningToast$9(CharSequence charSequence) {
        if (Settings.Global.getInt(getContext().getContentResolver(), "show_notification_channel_warnings", 0) != 0) {
            Toast.makeText(getContext(), this.mHandler.getLooper(), charSequence, 0).show();
        }
    }

    public int resolveNotificationUid(String str, String str2, int i, int i2) {
        int i3 = -1;
        if (i2 == -1) {
            i2 = 0;
        }
        if (isCallerSameApp(str2, i, i2) && (TextUtils.equals(str, str2) || isCallerSameApp(str, i, i2))) {
            return i;
        }
        try {
            i3 = this.mPackageManagerClient.getPackageUidAsUser(str2, i2);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (isCallerAndroid(str, i) || this.mPreferencesHelper.isDelegateAllowed(str2, i3, str, i)) {
            return i3;
        }
        throw new SecurityException("Caller " + str + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + i + " cannot post for pkg " + str2 + " in user " + i2);
    }

    public boolean checkDisqualifyingFeatures(int i, int i2, int i3, String str, NotificationRecord notificationRecord, boolean z, boolean z2) {
        boolean isRecordBlockedLocked;
        int notificationCount;
        Notification notification = notificationRecord.getNotification();
        String packageName = notificationRecord.getSbn().getPackageName();
        boolean z3 = isUidSystemOrPhone(i2) || "android".equals(packageName);
        boolean isSystemListenerPackage = this.mListeners.isSystemListenerPackage(packageName);
        if (!z3 && !isSystemListenerPackage) {
            int callingUid = Binder.getCallingUid();
            synchronized (this.mNotificationLock) {
                if (this.mNotificationsByKey.get(notificationRecord.getSbn().getKey()) == null && isCallerInstantApp(callingUid, i)) {
                    throw new SecurityException("Instant app " + packageName + " cannot create notifications");
                }
                if (!notificationRecord.getNotification().hasCompletedProgress() && !z) {
                    float appEnqueueRate = this.mUsageStats.getAppEnqueueRate(packageName);
                    if (notification.isMediaNotification()) {
                        if (appEnqueueRate > 20.0f) {
                            this.mUsageStats.registerOverRateQuota(packageName);
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            Slog.e("NotificationService", "Media noti enqueue rate is " + appEnqueueRate + ". Shedding " + notificationRecord.getSbn().getKey() + ". package=" + packageName);
                            this.mLastOverRateLogTime = elapsedRealtime;
                            return false;
                        }
                    } else if (appEnqueueRate > this.mMaxPackageEnqueueRate) {
                        this.mUsageStats.registerOverRateQuota(packageName);
                        long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        Slog.e("NotificationService", "Package enqueue rate is " + appEnqueueRate + ". Shedding " + notificationRecord.getSbn().getKey() + ". package=" + packageName);
                        this.mLastOverRateLogTime = elapsedRealtime2;
                        return false;
                    }
                }
                if (!notification.isFgsOrUij() && (notificationCount = getNotificationCount(packageName, i, i3, str)) >= 25) {
                    this.mUsageStats.registerOverCountQuota(packageName);
                    Slog.e("NotificationService", "Package has already posted or enqueued " + notificationCount + " notifications.  Not showing more.  package=" + packageName);
                    return false;
                }
            }
        }
        if (notification.getBubbleMetadata() != null && notification.getBubbleMetadata().getIntent() != null && hasFlag(this.mAmi.getPendingIntentFlags(notification.getBubbleMetadata().getIntent().getTarget()), 67108864)) {
            throw new IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to bubbles must be mutable");
        }
        Notification.Action[] actionArr = notification.actions;
        if (actionArr != null) {
            for (Notification.Action action : actionArr) {
                if (!(action.getRemoteInputs() == null && action.getDataOnlyRemoteInputs() == null) && hasFlag(this.mAmi.getPendingIntentFlags(action.actionIntent.getTarget()), 67108864)) {
                    throw new IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to actions with remote inputs must be mutable");
                }
            }
        }
        if (notificationRecord.getSystemGeneratedSmartActions() != null) {
            Iterator it = notificationRecord.getSystemGeneratedSmartActions().iterator();
            while (it.hasNext()) {
                Notification.Action action2 = (Notification.Action) it.next();
                if (action2.getRemoteInputs() != null || action2.getDataOnlyRemoteInputs() != null) {
                    if (hasFlag(this.mAmi.getPendingIntentFlags(action2.actionIntent.getTarget()), 67108864)) {
                        throw new IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to contextual actions with remote inputs must be mutable");
                    }
                }
            }
        }
        if (notification.isStyle(Notification.CallStyle.class)) {
            boolean z4 = notification.fullScreenIntent != null;
            boolean z5 = (notification.flags & 16384) != 0;
            if (!notification.isFgsOrUij() && !z4 && !z5 && !z2) {
                Slog.d("NotificationService", "FGS call notification = " + notification);
                Slog.d("NotificationService", "FGS call notification FullScreenIntent = " + notification.fullScreenIntent);
                Slog.d("NotificationService", "FGS call notification fgs = " + notification.isFgsOrUij());
                throw new IllegalArgumentException(notificationRecord.getKey() + " Not posted. CallStyle notifications must be for a foreground service or user initated job or use a fullScreenIntent.");
            }
        }
        if (this.mSnoozeHelper.isSnoozed(i, packageName, notificationRecord.getKey())) {
            MetricsLogger.action(notificationRecord.getLogMaker().setType(6).setCategory(831));
            this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_NOT_POSTED_SNOOZED, notificationRecord);
            if (DBG) {
                Slog.d("NotificationService", "Ignored enqueue for snoozed notification " + notificationRecord.getKey());
            }
            Notification.BubbleMetadata bubbleMetadata = notificationRecord.getSbn().getNotification().getBubbleMetadata();
            if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
                return false;
            }
            this.mSnoozeHelper.update(i, notificationRecord);
            handleSavePolicyFile();
            return false;
        }
        boolean z6 = !areNotificationsEnabledForPackageInt(packageName, i2);
        synchronized (this.mNotificationLock) {
            isRecordBlockedLocked = isRecordBlockedLocked(notificationRecord);
        }
        boolean z7 = z6 | isRecordBlockedLocked;
        if (z7) {
            Slog.e("NotificationService", "isPkgBlocked = " + z6);
            Slog.e("NotificationService", "isRecordBlocked = " + isRecordBlockedLocked);
        }
        if (!z7 || notification.isMediaNotification() || isCallNotification(packageName, i2, notification)) {
            return true;
        }
        Slog.e("NotificationService", "Suppressing notification from package " + notificationRecord.getSbn().getPackageName() + " by user request.");
        this.mUsageStats.registerBlocked(notificationRecord);
        if (this.mNotiSemGoodCatchManager != null && this.mGoodCatchNotiBlockedOn && !notification.isGroupSummary()) {
            this.mNotiSemGoodCatchManager.update("noti_blocked", notificationRecord.getSbn().getPackageName(), 0, (String) null, (String) null);
        }
        return false;
    }

    public final boolean isCallNotification(String str, int i, Notification notification) {
        if (notification.isStyle(Notification.CallStyle.class)) {
            return isCallNotification(str, i);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
    
        if (r4.mTelecomManager.isInSelfManagedCall(r5, android.os.UserHandle.getUserHandleForUid(r6)) != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isCallNotification(String str, int i) {
        TelecomManager telecomManager;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z = false;
            if (!this.mPackageManagerClient.hasSystemFeature("android.software.telecom") || (telecomManager = this.mTelecomManager) == null) {
                return false;
            }
            try {
                if (!telecomManager.isInManagedCall() && !this.mCMCinCallState) {
                }
                z = true;
                return z;
            } catch (IllegalStateException unused) {
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean areNotificationsEnabledForPackageInt(String str, int i) {
        return this.mPermissionHelper.hasPermission(i);
    }

    public final int getNotificationCount(String str, int i) {
        int i2;
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i3);
                if (notificationRecord.getSbn().getPackageName().equals(str) && notificationRecord.getSbn().getUserId() == i) {
                    i2++;
                }
            }
            int size2 = this.mEnqueuedNotifications.size();
            for (int i4 = 0; i4 < size2; i4++) {
                NotificationRecord notificationRecord2 = (NotificationRecord) this.mEnqueuedNotifications.get(i4);
                if (notificationRecord2.getSbn().getPackageName().equals(str) && notificationRecord2.getSbn().getUserId() == i) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public int getNotificationCount(String str, int i, int i2, String str2) {
        int i3;
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i4);
                if (notificationRecord.getSbn().getPackageName().equals(str) && notificationRecord.getSbn().getUserId() == i && (notificationRecord.getSbn().getId() != i2 || !TextUtils.equals(notificationRecord.getSbn().getTag(), str2))) {
                    i3++;
                }
            }
            int size2 = this.mEnqueuedNotifications.size();
            for (int i5 = 0; i5 < size2; i5++) {
                NotificationRecord notificationRecord2 = (NotificationRecord) this.mEnqueuedNotifications.get(i5);
                if (notificationRecord2.getSbn().getPackageName().equals(str) && notificationRecord2.getSbn().getUserId() == i) {
                    i3++;
                }
            }
        }
        return i3;
    }

    public boolean isRecordBlockedLocked(NotificationRecord notificationRecord) {
        return this.mPreferencesHelper.isGroupBlocked(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getUid(), notificationRecord.getChannel().getGroup()) || notificationRecord.getImportance() == 0;
    }

    public class SnoozeNotificationRunnable implements Runnable {
        public final long mDuration;
        public final String mKey;
        public final String mSnoozeCriterionId;

        public SnoozeNotificationRunnable(String str, long j, String str2) {
            this.mKey = str;
            this.mDuration = j;
            this.mSnoozeCriterionId = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked = NotificationManagerService.this.findInCurrentAndSnoozedNotificationByKeyLocked(this.mKey);
                if (findInCurrentAndSnoozedNotificationByKeyLocked != null) {
                    snoozeLocked(findInCurrentAndSnoozedNotificationByKeyLocked);
                }
            }
        }

        public void snoozeLocked(NotificationRecord notificationRecord) {
            ArrayList arrayList = new ArrayList();
            if (notificationRecord.getSbn().isGroup()) {
                List findCurrentAndSnoozedGroupNotificationsLocked = NotificationManagerService.this.findCurrentAndSnoozedGroupNotificationsLocked(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getGroupKey(), notificationRecord.getSbn().getUserId());
                if (notificationRecord.getNotification().isGroupSummary()) {
                    for (int i = 0; i < findCurrentAndSnoozedGroupNotificationsLocked.size(); i++) {
                        if (!this.mKey.equals(((NotificationRecord) findCurrentAndSnoozedGroupNotificationsLocked.get(i)).getKey())) {
                            arrayList.add((NotificationRecord) findCurrentAndSnoozedGroupNotificationsLocked.get(i));
                        }
                    }
                } else if (NotificationManagerService.this.mSummaryByGroupKey.containsKey(notificationRecord.getSbn().getGroupKey()) && findCurrentAndSnoozedGroupNotificationsLocked.size() == 2) {
                    for (int i2 = 0; i2 < findCurrentAndSnoozedGroupNotificationsLocked.size(); i2++) {
                        if (!this.mKey.equals(((NotificationRecord) findCurrentAndSnoozedGroupNotificationsLocked.get(i2)).getKey())) {
                            arrayList.add((NotificationRecord) findCurrentAndSnoozedGroupNotificationsLocked.get(i2));
                        }
                    }
                }
            }
            arrayList.add(notificationRecord);
            if (NotificationManagerService.this.mSnoozeHelper.canSnooze(arrayList.size())) {
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    snoozeNotificationLocked((NotificationRecord) arrayList.get(i3));
                }
            } else {
                Log.w("NotificationService", "Cannot snooze " + notificationRecord.getKey() + ": too many snoozed notifications");
            }
        }

        public void snoozeNotificationLocked(NotificationRecord notificationRecord) {
            MetricsLogger.action(notificationRecord.getLogMaker().setCategory(831).setType(2).addTaggedData(1139, Long.valueOf(this.mDuration)).addTaggedData(832, Integer.valueOf(this.mSnoozeCriterionId == null ? 0 : 1)));
            NotificationManagerService.this.mNotificationRecordLogger.log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SNOOZED, notificationRecord);
            NotificationManagerService.this.reportUserInteraction(notificationRecord);
            NotificationManagerService.this.cancelNotificationLocked(notificationRecord, false, 18, NotificationManagerService.this.removeFromNotificationListsLocked(notificationRecord), null, SystemClock.elapsedRealtime());
            NotificationManagerService.this.updateLightsLocked();
            notificationRecord.getSbn().getNotification().semFlags |= 128;
            if (isSnoozable(notificationRecord)) {
                if (this.mSnoozeCriterionId != null) {
                    NotificationManagerService.this.mAssistants.notifyAssistantSnoozedLocked(notificationRecord, this.mSnoozeCriterionId);
                    NotificationManagerService.this.mSnoozeHelper.snooze(notificationRecord, this.mSnoozeCriterionId);
                } else {
                    NotificationManagerService.this.mSnoozeHelper.snooze(notificationRecord, this.mDuration);
                }
                notificationRecord.recordSnoozed();
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public final boolean isSnoozable(NotificationRecord notificationRecord) {
            return (notificationRecord.getNotification().isGroupSummary() && "ranker_group".equals(notificationRecord.getNotification().getGroup())) ? false : true;
        }
    }

    public class CancelNotificationRunnable implements Runnable {
        public final int mCallingPid;
        public final int mCallingUid;
        public final long mCancellationElapsedTimeMs;
        public final int mCount;
        public final int mId;
        public final ManagedServices.ManagedServiceInfo mListener;
        public final int mMustHaveFlags;
        public final int mMustNotHaveFlags;
        public final String mPkg;
        public final int mRank;
        public final int mReason;
        public final boolean mSendDelete;
        public final String mTag;
        public final int mUserId;

        public CancelNotificationRunnable(int i, int i2, String str, String str2, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, int i9, ManagedServices.ManagedServiceInfo managedServiceInfo, long j) {
            this.mCallingUid = i;
            this.mCallingPid = i2;
            this.mPkg = str;
            this.mTag = str2;
            this.mId = i3;
            this.mMustHaveFlags = i4;
            this.mMustNotHaveFlags = i5;
            this.mSendDelete = z;
            this.mUserId = i6;
            this.mReason = i7;
            this.mRank = i8;
            this.mCount = i9;
            this.mListener = managedServiceInfo;
            this.mCancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            ManagedServices.ManagedServiceInfo managedServiceInfo = this.mListener;
            String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
            boolean z = NotificationManagerService.DBG;
            EventLogTags.writeNotificationCancel(this.mCallingUid, this.mCallingPid, this.mPkg, this.mId, this.mTag, this.mUserId, this.mMustHaveFlags, this.mMustNotHaveFlags, this.mReason, shortString);
            if (!NotificationManagerService.this.mFoldState && NotificationManagerService.this.mPreferencesHelper.isContainAllowList(this.mPkg)) {
                if (NotificationManagerService.this.mActivityManager.getPackageImportance(this.mPkg) == 100) {
                    NotificationManagerService.this.mHistoryManager.updateCancelEvent(this.mUserId, this.mUserId + "|" + this.mPkg + "|" + this.mId + "|" + this.mTag + "|" + this.mCallingUid, false);
                }
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(this.mPkg, this.mTag, this.mId, this.mUserId);
                if (findNotificationLocked != null) {
                    if (this.mReason == 1) {
                        NotificationManagerService.this.mUsageStats.registerClickedByUser(findNotificationLocked);
                    }
                    if ((this.mReason == 10 && findNotificationLocked.getNotification().isBubbleNotification()) || (this.mReason == 1 && findNotificationLocked.canBubble() && findNotificationLocked.isFlagBubbleRemoved())) {
                        NotificationManagerService.this.mNotificationDelegate.onBubbleMetadataFlagChanged(findNotificationLocked.getKey(), (findNotificationLocked.getNotification().getBubbleMetadata() != null ? findNotificationLocked.getNotification().getBubbleMetadata().getFlags() : 0) | 2);
                        return;
                    }
                    int i = findNotificationLocked.getNotification().flags;
                    int i2 = this.mMustHaveFlags;
                    if ((i & i2) != i2) {
                        return;
                    }
                    if ((findNotificationLocked.getNotification().flags & this.mMustNotHaveFlags) != 0) {
                        return;
                    }
                    FlagChecker flagChecker = new FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$CancelNotificationRunnable$$ExternalSyntheticLambda0
                        @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                        public final boolean apply(int i3) {
                            boolean lambda$run$0;
                            lambda$run$0 = NotificationManagerService.CancelNotificationRunnable.this.lambda$run$0(i3);
                            return lambda$run$0;
                        }
                    };
                    NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, this.mSendDelete, this.mReason, this.mRank, this.mCount, NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked), shortString, this.mCancellationElapsedTimeMs);
                    NotificationManagerService.this.cancelGroupSummaryLocked(findNotificationLocked, this.mCallingUid, this.mCallingPid, shortString, this.mSendDelete, this.mReason, this.mCancellationElapsedTimeMs);
                    NotificationManagerService.this.cancelGroupChildrenLocked(findNotificationLocked, this.mCallingUid, this.mCallingPid, shortString, this.mSendDelete, flagChecker, this.mReason, this.mCancellationElapsedTimeMs);
                    NotificationManagerService.this.updateLightsLocked();
                    if (NotificationManagerService.this.mShortcutHelper != null) {
                        NotificationManagerService.this.mShortcutHelper.maybeListenForShortcutChangesForBubbles(findNotificationLocked, true, NotificationManagerService.this.mHandler);
                    }
                    Slog.d("NotificationService", "CancelNotificationRunnable end r = " + findNotificationLocked.getKey() + ", reason = " + this.mReason);
                } else if (this.mReason != 18 && NotificationManagerService.this.mSnoozeHelper.cancel(this.mUserId, this.mPkg, this.mTag, this.mId)) {
                    NotificationManagerService.this.handleSavePolicyFile();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$run$0(int i) {
            int i2 = this.mReason;
            if (i2 == 2 || i2 == 1 || i2 == 3) {
                if ((i & IInstalld.FLAG_USE_QUOTA) != 0) {
                    return false;
                }
            } else if (i2 == 8 && ((i & 64) != 0 || (32768 & i) != 0)) {
                return false;
            }
            return (this.mMustNotHaveFlags & i) == 0;
        }
    }

    public class ShowNotificationPermissionPromptRunnable implements Runnable {
        public final String mPkgName;
        public final PermissionPolicyInternal mPpi;
        public final int mTaskId;
        public final int mUserId;

        public ShowNotificationPermissionPromptRunnable(String str, int i, int i2, PermissionPolicyInternal permissionPolicyInternal) {
            this.mPkgName = str;
            this.mUserId = i;
            this.mTaskId = i2;
            this.mPpi = permissionPolicyInternal;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ShowNotificationPermissionPromptRunnable)) {
                return false;
            }
            ShowNotificationPermissionPromptRunnable showNotificationPermissionPromptRunnable = (ShowNotificationPermissionPromptRunnable) obj;
            return Objects.equals(this.mPkgName, showNotificationPermissionPromptRunnable.mPkgName) && this.mUserId == showNotificationPermissionPromptRunnable.mUserId && this.mTaskId == showNotificationPermissionPromptRunnable.mTaskId;
        }

        public int hashCode() {
            return Objects.hash(this.mPkgName, Integer.valueOf(this.mUserId), Integer.valueOf(this.mTaskId));
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mPpi.showNotificationPromptIfNeeded(this.mPkgName, this.mUserId, this.mTaskId);
        }
    }

    public class EnqueueNotificationRunnable implements Runnable {
        public final boolean isAppForeground;
        public final PostNotificationTracker mTracker;

        /* renamed from: r */
        public final NotificationRecord f1716r;
        public final int userId;

        public EnqueueNotificationRunnable(int i, NotificationRecord notificationRecord, boolean z, PostNotificationTracker postNotificationTracker) {
            this.userId = i;
            this.f1716r = notificationRecord;
            this.isAppForeground = z;
            this.mTracker = (PostNotificationTracker) Preconditions.checkNotNull(postNotificationTracker);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (enqueueNotification()) {
                }
            } finally {
                this.mTracker.cancel();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:56:0x0176, code lost:
        
            if (android.util.Log.isLoggable("DownloadManager", 2) != false) goto L39;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean enqueueNotification() {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            int i = this.userId;
            int uid = this.f1716r.getSbn().getUid();
            int id = this.f1716r.getSbn().getId();
            String tag = this.f1716r.getSbn().getTag();
            NotificationRecord notificationRecord = this.f1716r;
            if (!notificationManagerService.checkDisqualifyingFeatures(i, uid, id, tag, notificationRecord, notificationRecord.getSbn().getOverrideGroupKey() != null, true)) {
                return false;
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                long longValue = NotificationManagerService.this.mSnoozeHelper.getSnoozeTimeForUnpostedNotification(this.f1716r.getUser().getIdentifier(), this.f1716r.getSbn().getPackageName(), this.f1716r.getSbn().getKey()).longValue();
                long currentTimeMillis = System.currentTimeMillis();
                if (longValue > currentTimeMillis) {
                    NotificationManagerService.this.new SnoozeNotificationRunnable(this.f1716r.getSbn().getKey(), longValue - currentTimeMillis, null).snoozeLocked(this.f1716r);
                    return false;
                }
                String snoozeContextForUnpostedNotification = NotificationManagerService.this.mSnoozeHelper.getSnoozeContextForUnpostedNotification(this.f1716r.getUser().getIdentifier(), this.f1716r.getSbn().getPackageName(), this.f1716r.getSbn().getKey());
                if (snoozeContextForUnpostedNotification != null) {
                    NotificationManagerService.this.new SnoozeNotificationRunnable(this.f1716r.getSbn().getKey(), 0L, snoozeContextForUnpostedNotification).snoozeLocked(this.f1716r);
                    return false;
                }
                NotificationManagerService.this.mEnqueuedNotifications.add(this.f1716r);
                NotificationManagerService.this.scheduleTimeoutLocked(this.f1716r);
                StatusBarNotification sbn = this.f1716r.getSbn();
                if (NotificationManagerService.DBG) {
                    Slog.d("NotificationService", "EnqueueNotificationRunnable.run for: " + sbn.getKey());
                }
                NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(sbn.getKey());
                if (notificationRecord2 != null) {
                    this.f1716r.copyRankingInformation(notificationRecord2);
                }
                int uid2 = sbn.getUid();
                int initialPid = sbn.getInitialPid();
                Notification notification = sbn.getNotification();
                String packageName = sbn.getPackageName();
                int id2 = sbn.getId();
                String tag2 = sbn.getTag();
                NotificationManagerService.this.updateNotificationBubbleFlags(this.f1716r, this.isAppForeground);
                NotificationManagerService.this.handleGroupedNotificationLocked(this.f1716r, notificationRecord2, uid2, initialPid);
                if (sbn.isGroup() && notification.isGroupChild()) {
                    NotificationManagerService.this.mSnoozeHelper.repostGroupSummary(packageName, this.f1716r.getUserId(), sbn.getGroupKey());
                }
                if (!NmRune.NM_ETC_LOG_DEBUG) {
                    if (packageName.equals("com.android.providers.downloads")) {
                    }
                }
                int i2 = notificationRecord2 != null ? 1 : 0;
                PendingIntent pendingIntent = notification.fullScreenIntent;
                EventLogTags.writeNotificationEnqueue(uid2, initialPid, packageName, id2, tag2, this.userId, notification.toString(), i2, pendingIntent == null ? "NULL" : pendingIntent.toString());
                if (NotificationManagerService.this.mAssistants.isEnabled() && !notification.isMediaNotification() && (notification.semFlags & 32768) == 0) {
                    NotificationManagerService.this.mAssistants.onNotificationEnqueuedLocked(this.f1716r);
                    NotificationManagerService.this.mHandler.postDelayed(NotificationManagerService.this.new PostNotificationRunnable(this.f1716r.getKey(), this.f1716r.getSbn().getPackageName(), this.f1716r.getUid(), this.mTracker), 200L);
                } else {
                    NotificationManagerService.this.mHandler.post(NotificationManagerService.this.new PostNotificationRunnable(this.f1716r.getKey(), this.f1716r.getSbn().getPackageName(), this.f1716r.getUid(), this.mTracker));
                }
                return true;
            }
        }
    }

    public boolean isPackagePausedOrSuspended(String str, int i) {
        return isPackageSuspendedForUser(str, i) | ((((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getDistractingPackageRestrictions(str, Binder.getCallingUserHandle().getIdentifier()) & 2) != 0);
    }

    public class PostNotificationRunnable implements Runnable {
        public final String key;
        public final PostNotificationTracker mTracker;
        public final String pkg;
        public final int uid;

        public PostNotificationRunnable(String str, String str2, int i, PostNotificationTracker postNotificationTracker) {
            this.key = str;
            this.pkg = str2;
            this.uid = i;
            this.mTracker = (PostNotificationTracker) Preconditions.checkNotNull(postNotificationTracker);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (postNotification()) {
                }
            } finally {
                this.mTracker.cancel();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:143:0x0462 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:144:0x0471 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:186:0x05b5 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:189:0x05db A[Catch: all -> 0x0615, TRY_LEAVE, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:192:0x05f2 A[Catch: all -> 0x0641, TryCatch #0 {, blocks: (B:15:0x005e, B:17:0x0069, B:21:0x007f, B:19:0x0087, B:22:0x008a, B:40:0x00d8, B:42:0x00e3, B:46:0x00f9, B:44:0x0101, B:47:0x0104, B:57:0x014b, B:59:0x0156, B:63:0x016c, B:61:0x0174, B:64:0x0177, B:190:0x05e8, B:192:0x05f2, B:196:0x0608, B:194:0x0610, B:197:0x0613, B:225:0x0616, B:227:0x0620, B:229:0x0636, B:231:0x0639, B:232:0x0640, B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:199:0x0613 A[EDGE_INSN: B:199:0x0613->B:197:0x0613 BREAK  A[LOOP:4: B:191:0x05f0->B:194:0x0610], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:205:0x055b A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:211:0x0236 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x01d7 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:91:0x02fb A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0320 A[Catch: all -> 0x0615, TryCatch #1 {all -> 0x0615, blocks: (B:5:0x001c, B:8:0x0028, B:14:0x0046, B:26:0x008c, B:28:0x0096, B:31:0x00a1, B:35:0x00ab, B:37:0x00b3, B:39:0x00c0, B:50:0x0106, B:52:0x0110, B:54:0x011d, B:56:0x0131, B:67:0x0179, B:69:0x0190, B:70:0x0199, B:72:0x01a7, B:75:0x01b2, B:76:0x01cb, B:78:0x01d7, B:80:0x021f, B:81:0x0228, B:83:0x0230, B:84:0x02a1, B:86:0x02b4, B:88:0x02c4, B:89:0x02f5, B:91:0x02fb, B:92:0x02ff, B:94:0x0320, B:96:0x0328, B:98:0x032e, B:100:0x0340, B:102:0x0348, B:104:0x0350, B:106:0x0358, B:108:0x0382, B:110:0x0396, B:112:0x039e, B:114:0x03a6, B:116:0x03ae, B:118:0x03b6, B:121:0x03c6, B:123:0x03d8, B:126:0x03e9, B:128:0x0412, B:130:0x0418, B:132:0x0420, B:133:0x0428, B:134:0x0437, B:136:0x0450, B:138:0x0458, B:143:0x0462, B:144:0x0471, B:146:0x0477, B:148:0x0483, B:149:0x04a0, B:151:0x04a4, B:153:0x04ac, B:155:0x04b8, B:157:0x04c4, B:161:0x04cf, B:165:0x04db, B:169:0x042e, B:172:0x04ef, B:174:0x04f5, B:175:0x04fe, B:177:0x0521, B:179:0x0527, B:181:0x0535, B:184:0x05ad, B:186:0x05b5, B:187:0x05c4, B:189:0x05db, B:200:0x0543, B:202:0x054b, B:205:0x055b, B:207:0x0573, B:209:0x0577, B:210:0x0592, B:211:0x0236, B:213:0x028f, B:214:0x0294, B:216:0x029c, B:217:0x01be, B:10:0x0040), top: B:4:0x001c, outer: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean postNotification() {
            NotificationRecord notificationRecord;
            int indexOfNotificationLocked;
            int i;
            boolean z;
            int size;
            boolean z2;
            boolean z3;
            int size2;
            boolean z4 = !NotificationManagerService.this.areNotificationsEnabledForPackageInt(this.pkg, this.uid);
            boolean isCallNotification = NotificationManagerService.this.isCallNotification(this.pkg, this.uid);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                int i2 = 0;
                try {
                    int size3 = NotificationManagerService.this.mEnqueuedNotifications.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size3) {
                            notificationRecord = null;
                            break;
                        }
                        NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i3);
                        if (Objects.equals(this.key, notificationRecord2.getKey())) {
                            notificationRecord = notificationRecord2;
                            break;
                        }
                        i3++;
                    }
                    if (notificationRecord == null) {
                        Slog.i("NotificationService", "Cannot find enqueued record for key: " + this.key);
                        int size4 = NotificationManagerService.this.mEnqueuedNotifications.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size4) {
                                break;
                            }
                            if (Objects.equals(this.key, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i4)).getKey())) {
                                NotificationManagerService.this.mEnqueuedNotifications.remove(i4);
                                break;
                            }
                            i4++;
                        }
                        return false;
                    }
                    final StatusBarNotification sbn = notificationRecord.getSbn();
                    Notification notification = sbn.getNotification();
                    boolean z5 = isCallNotification && notification.isStyle(Notification.CallStyle.class);
                    if (!notification.isMediaNotification() && !z5 && (z4 || NotificationManagerService.this.isRecordBlockedLocked(notificationRecord))) {
                        NotificationManagerService.this.mUsageStats.registerBlocked(notificationRecord);
                        if (NotificationManagerService.DBG) {
                            Slog.e("NotificationService", "Suppressing notification from package " + this.pkg);
                        }
                        int size5 = NotificationManagerService.this.mEnqueuedNotifications.size();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= size5) {
                                break;
                            }
                            if (Objects.equals(this.key, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i5)).getKey())) {
                                NotificationManagerService.this.mEnqueuedNotifications.remove(i5);
                                break;
                            }
                            i5++;
                        }
                        return false;
                    }
                    String packageName = sbn.getPackageName();
                    if (notification.isGroupSummary() && "ranker_group".equals(notification.getGroup()) && NotificationManagerService.this.findGroupNotificationsLocked(packageName, notificationRecord.getGroupKey(), sbn.getUserId()).isEmpty()) {
                        Slog.i("NotificationService", "There are not grouped notifications. groupKey:" + notificationRecord.getGroupKey());
                        int size6 = NotificationManagerService.this.mEnqueuedNotifications.size();
                        int i6 = 0;
                        while (true) {
                            if (i6 >= size6) {
                                break;
                            }
                            if (Objects.equals(this.key, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i6)).getKey())) {
                                NotificationManagerService.this.mEnqueuedNotifications.remove(i6);
                                break;
                            }
                            i6++;
                        }
                        return false;
                    }
                    boolean isPackagePausedOrSuspended = NotificationManagerService.this.isPackagePausedOrSuspended(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid());
                    notificationRecord.setHidden(isPackagePausedOrSuspended);
                    if (isPackagePausedOrSuspended) {
                        NotificationManagerService.this.mUsageStats.registerSuspendedByAdmin(notificationRecord);
                    }
                    NotificationRecord notificationRecord3 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(this.key);
                    if (notificationRecord3 != null && notificationRecord3.getSbn().getInstanceId() != null) {
                        sbn.setInstanceId(notificationRecord3.getSbn().getInstanceId());
                        indexOfNotificationLocked = NotificationManagerService.this.indexOfNotificationLocked(sbn.getKey());
                        if (indexOfNotificationLocked >= 0) {
                            NotificationManagerService.this.mNotificationList.add(notificationRecord);
                            NotificationManagerService.this.mUsageStats.registerPostedByApp(notificationRecord);
                            NotificationManagerService.this.mUsageStatsManagerInternal.reportNotificationPosted(notificationRecord.getSbn().getOpPkg(), notificationRecord.getSbn().getUser(), this.mTracker.getStartTime());
                            boolean isVisuallyInterruptive = NotificationManagerService.this.isVisuallyInterruptive(null, notificationRecord);
                            notificationRecord.setInterruptive(isVisuallyInterruptive);
                            notificationRecord.setTextChanged(isVisuallyInterruptive);
                            NotificationManagerService.this.postedNotificationLog(notificationRecord);
                            if (NotificationManagerService.this.mNotificationReminder != null) {
                                NotificationManagerService.this.mNotificationReminder.addNotificationRecord(notificationRecord);
                            }
                            if (NotificationManagerService.this.isNeedSaveHistory(null, notificationRecord, isVisuallyInterruptive)) {
                                NotificationManagerService.this.mIsInterruptivePostNotif = true;
                            }
                        } else {
                            notificationRecord3 = (NotificationRecord) NotificationManagerService.this.mNotificationList.get(indexOfNotificationLocked);
                            NotificationManagerService.this.mNotificationList.set(indexOfNotificationLocked, notificationRecord);
                            NotificationManagerService.this.mUsageStats.registerUpdatedByApp(notificationRecord, notificationRecord3);
                            NotificationManagerService.this.mUsageStatsManagerInternal.reportNotificationUpdated(notificationRecord.getSbn().getOpPkg(), notificationRecord.getSbn().getUser(), this.mTracker.getStartTime());
                            notification.flags |= notificationRecord3.getNotification().flags & 64;
                            notificationRecord.isUpdate = true;
                            boolean isVisuallyInterruptive2 = NotificationManagerService.this.isVisuallyInterruptive(notificationRecord3, notificationRecord);
                            notificationRecord.setTextChanged(isVisuallyInterruptive2);
                            if (NotificationManagerService.this.isNeedSaveHistory(notificationRecord3, notificationRecord, isVisuallyInterruptive2)) {
                                NotificationManagerService.this.mIsInterruptivePostNotif = true;
                            }
                            if (NotificationManagerService.this.isNeedDeletePrevHistory(notificationRecord3, notificationRecord)) {
                                NotificationManagerService.this.mNeedDeletePrevHistory = true;
                            }
                        }
                        NotificationManagerService.this.mNotificationsByKey.put(sbn.getKey(), notificationRecord);
                        if (NotificationManagerService.this.mIsMaxNotiLimitEnabled && (size2 = NotificationManagerService.this.mNotificationsByKey.size()) >= NotificationManagerService.this.mMaxNotiLimitCount) {
                            Slog.d("NotificationService", "postNotification postAtFrontOfQueue key = " + notificationRecord.getKey() + ", count = " + size2);
                            NotificationManagerService.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationRunnable$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotificationManagerService.PostNotificationRunnable.this.lambda$postNotification$0();
                                }
                            });
                        }
                        i = notification.flags;
                        if ((i & 64) != 0) {
                            notification.flags = i | 32;
                        }
                        NotificationManagerService.this.mRankingHelper.extractSignals(notificationRecord);
                        NotificationManagerService notificationManagerService = NotificationManagerService.this;
                        notificationManagerService.mRankingHelper.sort(notificationManagerService.mNotificationList);
                        NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                        int indexOf = notificationManagerService2.mRankingHelper.indexOf(notificationManagerService2.mNotificationList, notificationRecord);
                        if (notification.getSmallIcon() == null) {
                            if (NotificationManagerService.this.isInCall() && NotificationManagerService.this.mIsDisableHunByCall) {
                                Slog.d("NotificationService", "Disable HeadsUp by Call");
                                notification.semFlags |= 8;
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z2 && NotificationManagerService.this.mSmartPopupEnable && NotificationManagerService.this.mMultiStarEnable && NotificationManagerService.this.mFloatingPackageList != null) {
                                Slog.d("NotificationService", "MultiStar Ghost Floatiog on, List = " + NotificationManagerService.this.mFloatingPackageList);
                                if ((NotificationManagerService.this.mFloatingPackageList.contains("all") || NotificationManagerService.this.mFloatingPackageList.contains(notificationRecord.getSbn().getPackageName())) && !notificationRecord.isCategory("call") && !notification.isStyle(Notification.CallStyle.class)) {
                                    notification.semFlags |= 8;
                                }
                            }
                            if (!z2 && NotificationManagerService.this.mEdgeLightingManager != null) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("isHeadUp", notificationRecord.getImportance() >= 4);
                                bundle.putBoolean("isUpdate", notificationRecord.isUpdate);
                                bundle.putBoolean("isInterrupt", notificationRecord.isIntercepted() && (NotificationManagerService.this.mZenModeHelper.getNotificationPolicy().suppressedVisualEffects & 16) != 0);
                                bundle.putInt(LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, notificationRecord.getChannel().getLockscreenVisibility());
                                bundle.putParcelable("sound", notificationRecord.getSound());
                                bundle.putParcelable("vibrate", notificationRecord.getVibration());
                                if (NmRune.NM_SUPPORT_SUB_DISPLAY_EDGE_LIGHTING) {
                                    boolean canBubble = notificationRecord.canBubble();
                                    if (canBubble && NotificationManagerService.this.mFoldState) {
                                        Slog.d("NotificationService", "Support sub display edge lighting and folded so edge lighting working");
                                        canBubble = false;
                                    }
                                    bundle.putBoolean("bubble", canBubble);
                                } else {
                                    bundle.putBoolean("bubble", notificationRecord.canBubble());
                                }
                                bundle.putBoolean("smartPopup", false);
                                bundle.putBoolean("canBypassDnd", notificationRecord.getChannel().canBypassDnd());
                                if (notificationRecord.getImportance() >= 4) {
                                    if (!notificationRecord.isCategory("call") && notification.headsUpContentView == null) {
                                        z3 = false;
                                        if (!z3) {
                                            Slog.d("NotificationService", "Category call notification, so make not work edgelighting");
                                            notification.semFlags |= IInstalld.FLAG_USE_QUOTA;
                                        } else if (!notificationRecord.isHidden() && NotificationManagerService.this.mEdgeLightingManager.showForNotification(sbn, bundle)) {
                                            Slog.d("NotificationService", "Changed notification by edgelighting.");
                                            notification.semFlags = notification.semFlags | 8 | IInstalld.FLAG_FORCE;
                                            NotificationManagerService.this.receiveFollowedNotification(notificationRecord.getSbn().getPackageName());
                                        } else if (NmRune.NM_SUPPORT_CLEAR_COVER_NOTI_LIST && NotificationManagerService.this.mCoverManager != null && NotificationManagerService.this.mCoverManager.getCoverState() != null) {
                                            CoverState coverState = NotificationManagerService.this.mCoverManager.getCoverState();
                                            boolean z6 = (coverState == null || coverState.getSwitchState()) ? false : true;
                                            int type = coverState != null ? coverState.getType() : 2;
                                            if (z6 && type == 17) {
                                                Slog.e("NotificationService", "clear view cover is closed, so cancel delayed wakelock");
                                                NotificationManagerService.this.receiveFollowedNotification(notificationRecord.getSbn().getPackageName());
                                            }
                                        }
                                    }
                                    z3 = true;
                                    if (!z3) {
                                    }
                                }
                            }
                            NotificationManagerService.this.notifyListenersPostedAndLogLocked(notificationRecord, notificationRecord3, this.mTracker, NotificationManagerService.this.mNotificationRecordLogger.prepareToLogNotificationPosted(notificationRecord, notificationRecord3, indexOf, !notificationRecord.isHidden() ? NotificationManagerService.this.buzzBeepBlinkLocked(notificationRecord) : 0, NotificationManagerService.this.getGroupInstanceId(notificationRecord.getSbn().getGroupKey())));
                            StatusBarNotification sbn2 = notificationRecord3 != null ? notificationRecord3.getSbn() : null;
                            if ((sbn2 == null || !Objects.equals(sbn2.getGroup(), sbn.getGroup()) || sbn2.getNotification().flags != sbn.getNotification().flags) && !NotificationManagerService.this.isCritical(notificationRecord)) {
                                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationRunnable$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NotificationManagerService.PostNotificationRunnable.this.lambda$postNotification$1(sbn);
                                    }
                                });
                            }
                            z = true;
                        } else {
                            Slog.e("NotificationService", "Not posting notification without small icon: " + notification);
                            if (notificationRecord3 != null && !notificationRecord3.isCanceled) {
                                NotificationManagerService.this.mListeners.notifyRemovedLocked(notificationRecord, 4, notificationRecord.getStats());
                                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.PostNotificationRunnable.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        NotificationManagerService.this.mGroupHelper.onNotificationRemoved(sbn);
                                    }
                                });
                            }
                            Slog.e("NotificationService", "WARNING: In a future release this will crash the app: " + sbn.getPackageName());
                            z = false;
                        }
                        if (NotificationManagerService.this.mShortcutHelper != null) {
                            NotificationManagerService.this.mShortcutHelper.maybeListenForShortcutChangesForBubbles(notificationRecord, false, NotificationManagerService.this.mHandler);
                        }
                        NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                        NotificationManagerService.this.maybeRegisterMessageSent(notificationRecord);
                        NotificationManagerService.this.maybeReportForegroundServiceUpdate(notificationRecord, true);
                        if (NotificationManagerService.this.mSmartAlertController != null) {
                            NotificationManagerService.this.mSmartAlertController.checkMissedEvent(NotificationManagerService.this.mNotificationList);
                        }
                        size = NotificationManagerService.this.mEnqueuedNotifications.size();
                        while (true) {
                            if (i2 < size) {
                                break;
                            }
                            if (Objects.equals(this.key, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i2)).getKey())) {
                                NotificationManagerService.this.mEnqueuedNotifications.remove(i2);
                                break;
                            }
                            i2++;
                        }
                        return z;
                    }
                    sbn.setInstanceId(NotificationManagerService.this.mNotificationInstanceIdSequence.newInstanceId());
                    indexOfNotificationLocked = NotificationManagerService.this.indexOfNotificationLocked(sbn.getKey());
                    if (indexOfNotificationLocked >= 0) {
                    }
                    NotificationManagerService.this.mNotificationsByKey.put(sbn.getKey(), notificationRecord);
                    if (NotificationManagerService.this.mIsMaxNotiLimitEnabled) {
                        Slog.d("NotificationService", "postNotification postAtFrontOfQueue key = " + notificationRecord.getKey() + ", count = " + size2);
                        NotificationManagerService.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationRunnable$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NotificationManagerService.PostNotificationRunnable.this.lambda$postNotification$0();
                            }
                        });
                    }
                    i = notification.flags;
                    if ((i & 64) != 0) {
                    }
                    NotificationManagerService.this.mRankingHelper.extractSignals(notificationRecord);
                    NotificationManagerService notificationManagerService3 = NotificationManagerService.this;
                    notificationManagerService3.mRankingHelper.sort(notificationManagerService3.mNotificationList);
                    NotificationManagerService notificationManagerService22 = NotificationManagerService.this;
                    int indexOf2 = notificationManagerService22.mRankingHelper.indexOf(notificationManagerService22.mNotificationList, notificationRecord);
                    if (notification.getSmallIcon() == null) {
                    }
                    if (NotificationManagerService.this.mShortcutHelper != null) {
                    }
                    NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                    NotificationManagerService.this.maybeRegisterMessageSent(notificationRecord);
                    NotificationManagerService.this.maybeReportForegroundServiceUpdate(notificationRecord, true);
                    if (NotificationManagerService.this.mSmartAlertController != null) {
                    }
                    size = NotificationManagerService.this.mEnqueuedNotifications.size();
                    while (true) {
                        if (i2 < size) {
                        }
                        i2++;
                    }
                    return z;
                } catch (Throwable th) {
                    int size7 = NotificationManagerService.this.mEnqueuedNotifications.size();
                    while (true) {
                        if (i2 >= size7) {
                            break;
                        }
                        if (Objects.equals(this.key, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i2)).getKey())) {
                            NotificationManagerService.this.mEnqueuedNotifications.remove(i2);
                            break;
                        }
                        i2++;
                    }
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postNotification$0() {
            NotificationManagerService.this.cancelOldestNotification();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postNotification$1(StatusBarNotification statusBarNotification) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.mGroupHelper.onNotificationPosted(statusBarNotification, NotificationManagerService.this.hasAutoGroupSummaryLocked(statusBarNotification));
            }
        }
    }

    public InstanceId getGroupInstanceId(String str) {
        NotificationRecord notificationRecord;
        if (str == null || (notificationRecord = (NotificationRecord) this.mSummaryByGroupKey.get(str)) == null) {
            return null;
        }
        return notificationRecord.getSbn().getInstanceId();
    }

    public boolean isVisuallyInterruptive(NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
        Notification.Builder recoverBuilder;
        Notification.Builder recoverBuilder2;
        if (notificationRecord2.getSbn().isGroup() && notificationRecord2.getSbn().getNotification().isGroupSummary()) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: summary");
            }
            return false;
        }
        if (notificationRecord == null) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: new notification");
            }
            return true;
        }
        Notification notification = notificationRecord.getSbn().getNotification();
        Notification notification2 = notificationRecord2.getSbn().getNotification();
        if (notification.extras == null || notification2.extras == null) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: no extras");
            }
            return false;
        }
        if ((notificationRecord2.getSbn().getNotification().flags & 64) != 0) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: foreground service");
            }
            return false;
        }
        String valueOf = String.valueOf(notification.extras.get("android.title"));
        String valueOf2 = String.valueOf(notification2.extras.get("android.title"));
        if (!valueOf.equals(valueOf2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed title");
                StringBuilder sb = new StringBuilder();
                sb.append("INTERRUPTIVENESS: ");
                sb.append(String.format("   old title: %s (%s@0x%08x)", valueOf, valueOf.getClass(), Integer.valueOf(valueOf.hashCode())));
                Slog.v("NotificationService", sb.toString());
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + String.format("   new title: %s (%s@0x%08x)", valueOf2, valueOf2.getClass(), Integer.valueOf(valueOf2.hashCode())));
            }
            return true;
        }
        String valueOf3 = String.valueOf(notification.extras.get("android.text"));
        String valueOf4 = String.valueOf(notification2.extras.get("android.text"));
        if (!valueOf3.equals(valueOf4)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed text");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INTERRUPTIVENESS: ");
                sb2.append(String.format("   old text: %s (%s@0x%08x)", valueOf3, valueOf3.getClass(), Integer.valueOf(valueOf3.hashCode())));
                Slog.v("NotificationService", sb2.toString());
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + String.format("   new text: %s (%s@0x%08x)", valueOf4, valueOf4.getClass(), Integer.valueOf(valueOf4.hashCode())));
            }
            return true;
        }
        if (notification.hasCompletedProgress() != notification2.hasCompletedProgress()) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: completed progress");
            }
            return true;
        }
        if (Notification.areIconsDifferent(notification, notification2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: icons differ");
            }
            return true;
        }
        if (notificationRecord2.canBubble()) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: bubble");
            }
            return false;
        }
        if (Notification.areActionsVisiblyDifferent(notification, notification2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed actions");
            }
            return true;
        }
        try {
            recoverBuilder = Notification.Builder.recoverBuilder(getContext(), notification);
            recoverBuilder2 = Notification.Builder.recoverBuilder(getContext(), notification2);
        } catch (Exception e) {
            Slog.w("NotificationService", "error recovering builder", e);
        }
        if (Notification.areStyledNotificationsVisiblyDifferent(recoverBuilder, recoverBuilder2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: styles differ");
            }
            return true;
        }
        if (Notification.areRemoteViewsChanged(recoverBuilder, recoverBuilder2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: remoteviews differ");
            }
            return true;
        }
        return false;
    }

    public final boolean isCritical(NotificationRecord notificationRecord) {
        return notificationRecord.getCriticality() < 2;
    }

    public final void handleGroupedNotificationLocked(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, int i, int i2) {
        NotificationRecord notificationRecord3;
        StatusBarNotification sbn = notificationRecord.getSbn();
        Notification notification = sbn.getNotification();
        if (notification.isGroupSummary() && !sbn.isAppGroup()) {
            notification.flags &= -513;
        }
        String groupKey = sbn.getGroupKey();
        boolean isGroupSummary = notification.isGroupSummary();
        Notification notification2 = notificationRecord2 != null ? notificationRecord2.getSbn().getNotification() : null;
        String groupKey2 = notificationRecord2 != null ? notificationRecord2.getSbn().getGroupKey() : null;
        boolean z = notificationRecord2 != null && notification2.isGroupSummary();
        if (z && (notificationRecord3 = (NotificationRecord) this.mSummaryByGroupKey.remove(groupKey2)) != notificationRecord2) {
            Slog.w("NotificationService", "Removed summary didn't match old notification: old=" + notificationRecord2.getKey() + ", removed=" + (notificationRecord3 != null ? notificationRecord3.getKey() : "<null>"));
        }
        if (isGroupSummary) {
            this.mSummaryByGroupKey.put(groupKey, notificationRecord);
        }
        FlagChecker flagChecker = new FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda16
            @Override // com.android.server.notification.NotificationManagerService.FlagChecker
            public final boolean apply(int i3) {
                boolean lambda$handleGroupedNotificationLocked$10;
                lambda$handleGroupedNotificationLocked$10 = NotificationManagerService.lambda$handleGroupedNotificationLocked$10(i3);
                return lambda$handleGroupedNotificationLocked$10;
            }
        };
        if (z) {
            if (isGroupSummary && groupKey2.equals(groupKey)) {
                return;
            }
            cancelGroupChildrenLocked(notificationRecord2, i, i2, null, false, flagChecker, 8, SystemClock.elapsedRealtime());
        }
    }

    public void scheduleTimeoutLocked(NotificationRecord notificationRecord) {
        if (notificationRecord.getNotification().getTimeoutAfter() > 0) {
            PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 1, new Intent(ACTION_NOTIFICATION_TIMEOUT).setPackage("android").setData(new Uri.Builder().scheme("timeout").appendPath(notificationRecord.getKey()).build()).addFlags(268435456).putExtra("key", notificationRecord.getKey()), 201326592);
            PendingIntent pendingIntent = (PendingIntent) this.mTimeoutPendingIntent.get(notificationRecord.getKey());
            if (pendingIntent != null) {
                this.mTimeoutPendingIntent.remove(notificationRecord.getKey());
                this.mAlarmManager.cancel(pendingIntent);
            }
            this.mTimeoutPendingIntent.put(notificationRecord.getKey(), broadcast);
            this.mAlarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + notificationRecord.getNotification().getTimeoutAfter(), broadcast);
        }
    }

    public void cancelScheduleTimeoutLocked(NotificationRecord notificationRecord) {
        if (notificationRecord.getNotification().getTimeoutAfter() > 0) {
            String key = notificationRecord.getKey();
            PendingIntent pendingIntent = (PendingIntent) this.mTimeoutPendingIntent.get(key);
            if (pendingIntent != null) {
                this.mTimeoutPendingIntent.remove(key);
                this.mAlarmManager.cancel(pendingIntent);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x020a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int buzzBeepBlinkLocked(NotificationRecord notificationRecord) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        LogicalLight logicalLight;
        boolean z7;
        boolean z8;
        if (this.mIsAutomotive && !this.mNotificationEffectsEnabledForAutomotive) {
            return 0;
        }
        String key = notificationRecord.getKey();
        boolean z9 = !this.mIsAutomotive ? notificationRecord.getImportance() < 3 : notificationRecord.getImportance() <= 3;
        boolean z10 = key != null && key.equals(this.mSoundNotificationKey);
        boolean z11 = key != null && key.equals(this.mVibrateNotificationKey);
        boolean z12 = notificationRecord.isIntercepted() && (notificationRecord.getSuppressedVisualEffects() & 32) != 0;
        if (notificationRecord.isUpdate || notificationRecord.getImportance() <= 1 || z12 || !isNotificationForCurrentUser(notificationRecord)) {
            z = false;
        } else {
            sendAccessibilityEvent(notificationRecord);
            z = true;
        }
        this.mDisableVibration = false;
        if (z9 && isNotificationForCurrentUser(notificationRecord) && this.mSystemReady && this.mAudioManager != null) {
            Uri sound = notificationRecord.getSound();
            z5 = (sound == null || Uri.EMPTY.equals(sound)) ? false : true;
            VibrationEffect vibration = notificationRecord.getVibration();
            boolean z13 = (notificationRecord.getFlags() & 4) != 0;
            if (vibration == null && z5 && this.mAudioManager.getRingerModeInternal() == 1 && this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) {
                vibration = this.mVibratorHelper.createFallbackVibration(z13);
            }
            z2 = vibration != null;
            if ((z5 || z2) && !shouldMuteNotificationLocked(notificationRecord)) {
                boolean z14 = this.mAudioManager.getRingerModeInternal() == 0;
                boolean z15 = z2 && !z14;
                if (!z) {
                    sendAccessibilityEvent(notificationRecord);
                }
                if (DBG) {
                    Slog.v("NotificationService", "Interrupting!");
                }
                boolean isInsistentUpdate = isInsistentUpdate(notificationRecord);
                if (!z5) {
                    z7 = false;
                } else if (isInsistentUpdate) {
                    z7 = true;
                } else {
                    if (isInCall()) {
                        if (this.mIsEnableAlertByCall) {
                            playInCallNotification();
                            z7 = true;
                        } else {
                            z7 = false;
                        }
                    } else {
                        z7 = playSound(notificationRecord, sound, z15);
                    }
                    if (z7) {
                        this.mSoundNotificationKey = key;
                        SemGoodCatchManager semGoodCatchManager = mSemAudioGoodCatchManager;
                        if (semGoodCatchManager != null && this.mGoodCatchSoundPlayedOn) {
                            semGoodCatchManager.update("playback", notificationRecord.getSbn().getOpPkg(), AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes()), "", "");
                            Slog.i("NotificationService", "Playing sound : " + notificationRecord.getSbn().getOpPkg() + "/" + notificationRecord.getSbn().getUid());
                        }
                    }
                }
                if (NmRune.NM_POLICY_VIB_PICKER_CONCEPT && notificationRecord.getChannel().getVibrationPattern() == null) {
                    vibration = VibrationEffect.semCreateHaptic(this.mVibrationIndex, z13 ? 0 : -1, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
                }
                if (isInCall() || !z2 || z14 || this.mDisableVibration) {
                    z8 = false;
                } else if (isInsistentUpdate) {
                    z8 = true;
                } else {
                    boolean playVibration = playVibration(notificationRecord, vibration, z5);
                    z8 = playVibration;
                    if (playVibration) {
                        this.mVibrateNotificationKey = key;
                        z8 = playVibration;
                    }
                }
                boolean z16 = z8;
                z16 = z8;
                z16 = z8;
                if (isInCall() && z2 && !z14) {
                    z16 = z8;
                    if (this.mIsEnableAlertByCall) {
                        boolean playInCallVibration = playInCallVibration(notificationRecord, z5);
                        z16 = playInCallVibration;
                        if (playInCallVibration) {
                            this.mVibrateNotificationKey = key;
                            z16 = playInCallVibration;
                        }
                    }
                }
                this.mAccessibilityManager.startFlashNotificationEvent(getContext(), 3, notificationRecord.getSbn().getPackageName());
                z4 = z7;
                i = 4;
                z3 = z16;
            } else {
                i = 4;
                if ((notificationRecord.getFlags() & 4) == 0) {
                    z3 = 0;
                    z4 = false;
                }
            }
            if (z10 && !z5) {
                clearSoundLocked();
            }
            if (z11 && !z2) {
                clearVibrateLocked();
            }
            boolean remove = this.mLights.remove(key);
            if (canShowLightsLocked(notificationRecord, z9)) {
                if (remove) {
                    updateLightsLocked();
                }
                z6 = false;
            } else {
                this.mLights.add(key);
                updateLightsLocked();
                if (this.mUseAttentionLight && (logicalLight = this.mAttentionLight) != null) {
                    logicalLight.pulse();
                }
                z6 = true;
            }
            int i2 = (!z4 ? 2 : 0) | z3 | (!z6 ? i : 0);
            logBuzzBeepBlink(notificationRecord, z3, z4, z6);
            notificationRecord.setAudiblyAlerted(z3 == 0 || z4);
            return i2;
        }
        i = 4;
        z2 = false;
        z3 = 0;
        z4 = false;
        z5 = false;
        if (z10) {
            clearSoundLocked();
        }
        if (z11) {
            clearVibrateLocked();
        }
        boolean remove2 = this.mLights.remove(key);
        if (canShowLightsLocked(notificationRecord, z9)) {
        }
        int i22 = (!z4 ? 2 : 0) | z3 | (!z6 ? i : 0);
        logBuzzBeepBlink(notificationRecord, z3, z4, z6);
        notificationRecord.setAudiblyAlerted(z3 == 0 || z4);
        return i22;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int logBuzzBeepBlink(NotificationRecord notificationRecord, boolean z, boolean z2, boolean z3) {
        int i;
        int i2;
        int i3;
        Uri sound = notificationRecord.getSound();
        boolean z4 = (notificationRecord.getChannel().getUserLockedFields() & 32) != 0 && notificationRecord.getChannel().getSound() == null;
        boolean z5 = (sound == null || Uri.EMPTY.equals(sound)) ? false : true;
        VibrationEffect vibration = notificationRecord.getVibration();
        if (vibration == null && z5 && this.mAudioManager.getRingerModeInternal() == 1 && this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) {
            vibration = this.mVibratorHelper.createFallbackVibration((notificationRecord.getFlags() & 4) != 0);
        }
        boolean z6 = ((notificationRecord.getChannel().getUserLockedFields() & 16) == 0 || notificationRecord.getChannel().shouldVibrate()) ? false : true;
        boolean z7 = vibration != null;
        boolean z8 = ((z5 || z4) && !z2) || ((z7 || z6) && !z);
        if (z8) {
            Notification notification = notificationRecord.getNotification();
            String packageName = notificationRecord.getSbn().getPackageName();
            int uid = notificationRecord.getSbn().getUid();
            int i4 = 14;
            if (notificationRecord.getImportance() < 3) {
                if ((notificationRecord.getChannel().getUserLockedFields() & 4) != 0) {
                    i4 = 3;
                } else if (this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(packageName, uid)) {
                    z8 = false;
                    i4 = 0;
                }
                if ((z5 && !z4) || z2 || i4 != 0) {
                    i = 2;
                    i2 = i4;
                } else if (this.mAudioManager.isAudioFocusExclusive()) {
                    i = 2;
                    if (this.mAudioManager.getRingerModeInternal() != 2) {
                        i2 = 11;
                    } else {
                        i2 = this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) <= 0 ? 9 : z4 ? 17 : 10;
                    }
                } else {
                    i2 = 8;
                    i = 2;
                }
                if ((!z7 || z6) && !z && i4 == 0) {
                    if (this.mAudioManager.getRingerModeInternal() != 0) {
                        i3 = 11;
                    } else if (this.mDisableVibration) {
                        i3 = 15;
                    } else if (z6) {
                        i3 = 16;
                    }
                }
                i3 = i4;
            } else {
                if (disableNotificationEffects(notificationRecord) != null) {
                    i4 = 2;
                } else if (notificationRecord.isIntercepted()) {
                    i4 = 4;
                } else if (this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(packageName, uid)) {
                    if (!this.mSystemReady || this.mAudioManager == null) {
                        i4 = 5;
                    } else if (!isNotificationForCurrentUser(notificationRecord)) {
                        i4 = 12;
                    } else if (notificationRecord.isUpdate && (notification.flags & 8) != 0) {
                        i4 = 6;
                    } else if (notificationRecord.getSbn().isGroup() && notification.suppressAlertingDueToGrouping()) {
                        i4 = 7;
                    } else if (notificationRecord.getAlertLimited()) {
                        i4 = 13;
                    } else {
                        int i5 = this.mIsMutedByWearableApps;
                        if ((i5 == 1 || i5 == 2) && this.mPreferencesHelper.getWearableAppMuted(uid, packageName) == 1) {
                            i4 = 18;
                        }
                        i4 = 0;
                    }
                }
                if (z5) {
                }
                if (this.mAudioManager.isAudioFocusExclusive()) {
                }
                if (!z7) {
                }
                if (this.mAudioManager.getRingerModeInternal() != 0) {
                }
            }
        } else {
            i = 2;
            i2 = 0;
            i3 = 0;
        }
        int i6 = (z ? 1 : 0) | (z2 ? i : 0) | (z3 ? 4 : 0);
        if (i6 > 0 || z8) {
            if (notificationRecord.getSbn().isGroup() && notificationRecord.getSbn().getNotification().isGroupSummary()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: summary");
                }
            } else if (notificationRecord.canBubble()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: bubble");
                }
            } else if (i6 > 0) {
                notificationRecord.setInterruptive(true);
                if (DEBUG_INTERRUPTIVENESS) {
                    Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is interruptive: alerted");
                }
            }
            MetricsLogger.action(notificationRecord.getLogMaker().setCategory(FrameworkStatsLog.f690xa4f1b8b3).setType(1).setSubtype(i6));
            EventLogTags.writeNotificationAlert(notificationRecord.getKey(), z ? 1 : i3, z2 ? 1 : i2, z3 ? 1 : 0);
        }
        Notification notification2 = notificationRecord.getNotification();
        if (((!notificationRecord.isUpdate || (notification2.flags & 8) == 0) && (notification2.flags & 512) == 0) && (!z2 || !z)) {
            int i7 = z2 ? i3 : i2;
            SemGoodCatchManager semGoodCatchManager = this.mNotiSemGoodCatchManager;
            if (semGoodCatchManager != null && this.mGoodCatchNotiMutedOn) {
                semGoodCatchManager.update("noti_muted", notificationRecord.getSbn().getPackageName(), i7, (String) null, (String) null);
            }
        }
        return i6;
    }

    public void makeHighDataSizeLog(String str, Notification notification) {
        Log.e("NotificationService", "notification key : " + str + " has too high data size(" + notification.parcelDataSize + ") above 100000");
        this.mHighDataSizeNotificaitonList.add(makeTime() + " key : " + str + " size :" + notification.parcelDataSize);
        while (this.mHighDataSizeNotificaitonList.size() > 20) {
            this.mHighDataSizeNotificaitonList.remove(0);
        }
    }

    public final String makeTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
    }

    public boolean canShowLightsLocked(NotificationRecord notificationRecord, boolean z) {
        if (!this.mHasLight || !this.mNotificationPulseEnabled || notificationRecord.getLight() == null || !z || (notificationRecord.getSuppressedVisualEffects() & 8) != 0) {
            return false;
        }
        Notification notification = notificationRecord.getNotification();
        if (!notificationRecord.isUpdate || (notification.flags & 8) == 0) {
            return !(notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().suppressAlertingDueToGrouping()) && isNotificationForCurrentUser(notificationRecord);
        }
        return false;
    }

    public boolean isInsistentUpdate(NotificationRecord notificationRecord) {
        return (Objects.equals(notificationRecord.getKey(), this.mSoundNotificationKey) || Objects.equals(notificationRecord.getKey(), this.mVibrateNotificationKey)) && isCurrentlyInsistent();
    }

    public boolean isCurrentlyInsistent() {
        return isLoopingRingtoneNotification((NotificationRecord) this.mNotificationsByKey.get(this.mSoundNotificationKey)) || isLoopingRingtoneNotification((NotificationRecord) this.mNotificationsByKey.get(this.mVibrateNotificationKey));
    }

    public boolean shouldMuteNotificationLocked(NotificationRecord notificationRecord) {
        Notification notification = notificationRecord.getNotification();
        if ((notificationRecord.isUpdate && (notification.flags & 8) != 0) || notificationRecord.shouldPostSilently()) {
            return true;
        }
        String disableNotificationEffects = disableNotificationEffects(notificationRecord);
        if (disableNotificationEffects != null) {
            ZenLog.traceDisableEffects(notificationRecord, disableNotificationEffects);
            return true;
        }
        if (notificationRecord.isIntercepted()) {
            return true;
        }
        if (notificationRecord.getSbn().isGroup() && notification.suppressAlertingDueToGrouping()) {
            return true;
        }
        if (this.mUsageStats.isAlertRateLimited(notificationRecord.getSbn().getPackageName())) {
            Slog.e("NotificationService", "Muting recently noisy " + notificationRecord.getKey());
            notificationRecord.setAlertLimited(true);
            return true;
        }
        notificationRecord.setAlertLimited(false);
        if (isCurrentlyInsistent() && !isInsistentUpdate(notificationRecord)) {
            return true;
        }
        boolean z = notificationRecord.canBubble() && (notificationRecord.isFlagBubbleRemoved() || notificationRecord.getNotification().isBubbleNotification());
        if ((notificationRecord.isUpdate && !notificationRecord.isInterruptive() && z && notificationRecord.getNotification().getBubbleMetadata() != null && notificationRecord.getNotification().getBubbleMetadata().isNotificationSuppressed()) || !this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
            return true;
        }
        String packageName = notificationRecord.getSbn().getPackageName();
        int uid = notificationRecord.getSbn().getUid();
        int i = this.mIsMutedByWearableApps;
        if ((i != 1 && i != 2) || this.mPreferencesHelper.getWearableAppMuted(uid, packageName) != 1) {
            return false;
        }
        Log.d("NotificationService", "NMS : muted by wearable app condition : ByListenerHint=" + this.mIsMutedByWearableApps + ", ByWearableAppList=" + this.mPreferencesHelper.getWearableAppMuted(uid, packageName));
        return true;
    }

    public final boolean isLoopingRingtoneNotification(NotificationRecord notificationRecord) {
        return (notificationRecord == null || notificationRecord.getAudioAttributes().getUsage() != 6 || (notificationRecord.getNotification().flags & 4) == 0) ? false : true;
    }

    public final boolean playSound(NotificationRecord notificationRecord, Uri uri, boolean z) {
        boolean z2;
        IRingtonePlayer ringtonePlayer;
        boolean z3 = (notificationRecord.getNotification().flags & 4) != 0;
        boolean z4 = this.mAudioManager.getRingerModeInternal() == 2;
        int streamVolume = this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes()));
        boolean z5 = this.mAudioManager.getRingerModeInternal() == 1 || (streamVolume == 0 && z4);
        if (!this.mAudioManager.isAudioFocusExclusive() && (((z2 = NmRune.NM_POLICY_VIB_PICKER_CONCEPT) && z5) || (streamVolume != 0 && z4))) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ringtonePlayer = this.mAudioManager.getRingtonePlayer();
                } catch (RemoteException e) {
                    Slog.e("NotificationService", "Couldn't play notification sound!! : ", e);
                }
                if (ringtonePlayer != null) {
                    if (DBG) {
                        Slog.v("NotificationService", "Playing sound " + uri + " with attributes " + notificationRecord.getAudioAttributes());
                    }
                    if (z2) {
                        Uri actualAchRingtoneUriIfAvailable = RingtoneManager.getActualAchRingtoneUriIfAvailable(getContext(), uri, notificationRecord.getSbn().getUser());
                        if (z && notificationRecord.getChannel().getVibrationPattern() == null && actualAchRingtoneUriIfAvailable != null && AudioManager.hasHapticChannels(getContext(), actualAchRingtoneUriIfAvailable)) {
                            this.mDisableVibration = true;
                            AudioAttributes build = new AudioAttributes.Builder(notificationRecord.getAudioAttributes()).setHapticChannelsMuted(false).semAddAudioTag("RINGTONE_HAPTIC").build();
                            Slog.v("NotificationService", "Playing sound with new AudioAttributes - " + build);
                            ringtonePlayer.playAsync(actualAchRingtoneUriIfAvailable, notificationRecord.getSbn().getUser(), z3, build);
                        } else if (!z5) {
                            ringtonePlayer.playAsync(uri, notificationRecord.getSbn().getUser(), z3, notificationRecord.getAudioAttributes());
                        }
                        if (!z5 && this.mEasyMuteController != null) {
                            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotificationManagerService.this.lambda$playSound$11();
                                }
                            });
                            Slog.i("NotificationService", "Easymute controller is registered.");
                        }
                    } else {
                        ringtonePlayer.playAsync(uri, notificationRecord.getSbn().getUser(), z3, notificationRecord.getAudioAttributes());
                        if (this.mEasyMuteController != null) {
                            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda8
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotificationManagerService.this.lambda$playSound$12();
                                }
                            });
                        }
                    }
                    return true;
                }
                Slog.e("NotificationService", "Couldn't get a ringtone player!!");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playSound$11() {
        this.mEasyMuteController.registerListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playSound$12() {
        this.mEasyMuteController.registerListener();
    }

    public final boolean playVibration(final NotificationRecord notificationRecord, final VibrationEffect vibrationEffect, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                new Thread(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.this.lambda$playVibration$13(notificationRecord, vibrationEffect);
                    }
                }).start();
            } else {
                vibrate(notificationRecord, vibrationEffect, false);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playVibration$13(NotificationRecord notificationRecord, VibrationEffect vibrationEffect) {
        int focusRampTimeMs = this.mAudioManager.getFocusRampTimeMs(3, notificationRecord.getAudioAttributes());
        if (DBG) {
            Slog.v("NotificationService", "Delaying vibration for notification " + notificationRecord.getKey() + " by " + focusRampTimeMs + "ms");
        }
        try {
            Thread.sleep(focusRampTimeMs);
        } catch (InterruptedException unused) {
        }
        synchronized (this.mNotificationLock) {
            if (this.mNotificationsByKey.get(notificationRecord.getKey()) != null) {
                if (notificationRecord.getKey().equals(this.mVibrateNotificationKey)) {
                    vibrate(notificationRecord, vibrationEffect, true);
                } else if (DBG) {
                    Slog.v("NotificationService", "No vibration for notification " + notificationRecord.getKey() + ": a new notification is vibrating, or effects were cleared while waiting");
                }
            } else {
                Slog.w("NotificationService", "No vibration for canceled notification " + notificationRecord.getKey());
            }
        }
    }

    public final void vibrate(NotificationRecord notificationRecord, VibrationEffect vibrationEffect, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("Notification (");
        sb.append(notificationRecord.getSbn().getOpPkg());
        sb.append(" ");
        sb.append(notificationRecord.getSbn().getUid());
        sb.append(") ");
        sb.append(z ? "(Delayed)" : "");
        this.mVibratorHelper.vibrate(vibrationEffect, notificationRecord.getAudioAttributes(), sb.toString());
    }

    public final boolean isNotificationForCurrentUser(NotificationRecord notificationRecord) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int currentUser = ActivityManager.getCurrentUser();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return notificationRecord.getUserId() == -1 || notificationRecord.getUserId() == currentUser || this.mUserProfiles.isCurrentProfile(notificationRecord.getUserId());
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void playInCallNotification() {
        ContentResolver contentResolver = getContext().getContentResolver();
        if (this.mAudioManager.getRingerModeInternal() != 2 || Settings.Secure.getIntForUser(contentResolver, "in_call_notification_enabled", 1, contentResolver.getUserId()) == 0) {
            return;
        }
        new Thread() { // from class: com.android.server.notification.NotificationManagerService.21
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    IRingtonePlayer ringtonePlayer = NotificationManagerService.this.mAudioManager.getRingtonePlayer();
                    if (ringtonePlayer != null) {
                        if (NotificationManagerService.this.mCallNotificationToken != null) {
                            ringtonePlayer.stop(NotificationManagerService.this.mCallNotificationToken);
                        }
                        NotificationManagerService.this.mCallNotificationToken = new Binder();
                        ringtonePlayer.play(NotificationManagerService.this.mCallNotificationToken, NotificationManagerService.this.mInCallNotificationUri, NotificationManagerService.this.mInCallNotificationAudioAttributes, NotificationManagerService.this.mInCallNotificationVolume, false);
                    }
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }.start();
    }

    public final boolean playInCallVibration(final NotificationRecord notificationRecord, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final VibrationEffect semCreateWaveform = VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(3), -1, VibrationEffect.SemMagnitudeType.TYPE_MIN);
            if (z) {
                new Thread(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.this.lambda$playInCallVibration$14(notificationRecord, semCreateWaveform);
                    }
                }).start();
            } else {
                vibrate(notificationRecord, semCreateWaveform, false);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playInCallVibration$14(NotificationRecord notificationRecord, VibrationEffect vibrationEffect) {
        int focusRampTimeMs = this.mAudioManager.getFocusRampTimeMs(3, this.mInCallNotificationAudioAttributes);
        if (DBG) {
            Slog.v("NotificationService", "Delaying vibration by " + focusRampTimeMs + "ms");
        }
        try {
            Thread.sleep(focusRampTimeMs);
        } catch (InterruptedException unused) {
        }
        synchronized (this.mNotificationLock) {
            if (this.mNotificationsByKey.get(notificationRecord.getKey()) != null) {
                vibrate(notificationRecord, vibrationEffect, true);
            } else {
                Slog.e("NotificationService", "No vibration for canceled notification : " + notificationRecord.getKey());
            }
        }
    }

    public void showNextToastLocked(boolean z) {
        if (this.mIsCurrentToastShown) {
            return;
        }
        ToastRecord toastRecord = (ToastRecord) this.mToastQueue.get(0);
        while (toastRecord != null) {
            int userId = UserHandle.getUserId(toastRecord.uid);
            boolean z2 = !this.mToastRateLimitingDisabledUids.contains(Integer.valueOf(toastRecord.uid));
            boolean z3 = this.mToastRateLimiter.isWithinQuota(userId, toastRecord.pkg, "toast_quota_tag") || isExemptFromRateLimiting(toastRecord.pkg, userId);
            boolean isPackageInForegroundForToast = isPackageInForegroundForToast(toastRecord.uid);
            if (tryShowToast(toastRecord, z2, z3, isPackageInForegroundForToast)) {
                if (this.mGoodCatchViToastOn) {
                    String str = toastRecord.message;
                    if (userId != 0) {
                        str = "";
                    }
                    this.mSemGoodCatchManager.update("toast", toastRecord.pkg, (String) null, str, (String) null);
                }
                scheduleDurationReachedLocked(toastRecord, z);
                this.mIsCurrentToastShown = true;
                if (!z2 || isPackageInForegroundForToast) {
                    return;
                }
                this.mToastRateLimiter.noteEvent(userId, toastRecord.pkg, "toast_quota_tag");
                return;
            }
            int indexOf = this.mToastQueue.indexOf(toastRecord);
            if (indexOf >= 0) {
                ToastRecord toastRecord2 = (ToastRecord) this.mToastQueue.remove(indexOf);
                this.mWindowManagerInternal.removeWindowToken(toastRecord2.windowToken, true, toastRecord2.displayId);
            }
            toastRecord = this.mToastQueue.size() > 0 ? (ToastRecord) this.mToastQueue.get(0) : null;
        }
    }

    public final boolean tryShowToast(ToastRecord toastRecord, boolean z, boolean z2, boolean z3) {
        if (z && !z2 && !z3) {
            reportCompatRateLimitingToastsChange(toastRecord.uid);
            Slog.w("NotificationService", "Package " + toastRecord.pkg + " is above allowed toast quota, the following toast was blocked and discarded: " + toastRecord);
            return false;
        }
        if (blockToast(toastRecord.uid, toastRecord.isSystemToast, toastRecord.isAppRendered(), z3)) {
            Slog.w("NotificationService", "Blocking custom toast from package " + toastRecord.pkg + " due to package not in the foreground at the time of showing the toast");
            return false;
        }
        return toastRecord.show();
    }

    public final boolean isExemptFromRateLimiting(String str, int i) {
        try {
            return this.mPackageManager.checkPermission("android.permission.UNLIMITED_TOASTS", str, i) == 0;
        } catch (RemoteException unused) {
            Slog.e("NotificationService", "Failed to connect with package manager");
            return false;
        }
    }

    public final void reportCompatRateLimitingToastsChange(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mPlatformCompat.reportChangeByUid(174840628L, i);
            } catch (RemoteException e) {
                Slog.e("NotificationService", "Unexpected exception while reporting toast was blocked due to rate limiting", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelToastLocked(int i) {
        ToastRecord toastRecord = (ToastRecord) this.mToastQueue.get(i);
        toastRecord.hide();
        if (i == 0) {
            this.mIsCurrentToastShown = false;
        }
        ToastRecord toastRecord2 = (ToastRecord) this.mToastQueue.remove(i);
        scheduleKillTokenTimeout(toastRecord2);
        keepProcessAliveForToastIfNeededLocked(toastRecord.pid);
        if (this.mToastQueue.size() > 0) {
            showNextToastLocked(toastRecord2 instanceof TextToastRecord);
        }
    }

    public void finishWindowTokenLocked(IBinder iBinder, int i) {
        this.mHandler.removeCallbacksAndMessages(iBinder);
        this.mWindowManagerInternal.removeWindowToken(iBinder, true, i);
    }

    public final void scheduleDurationReachedLocked(ToastRecord toastRecord, boolean z) {
        this.mHandler.removeCallbacksAndMessages(toastRecord);
        Message obtain = Message.obtain(this.mHandler, 2, toastRecord);
        int recommendedTimeoutMillis = this.mAccessibilityManager.getRecommendedTimeoutMillis(toastRecord.getDuration() == 1 ? 3500 : 2000, 2);
        if (z) {
            recommendedTimeoutMillis += FrameworkStatsLog.f446x58c6c32;
        }
        if (toastRecord instanceof TextToastRecord) {
            recommendedTimeoutMillis += FrameworkStatsLog.DEVICE_ROTATED;
        }
        this.mHandler.sendMessageDelayed(obtain, recommendedTimeoutMillis);
    }

    public final void handleDurationReached(ToastRecord toastRecord) {
        if (DBG) {
            Slog.d("NotificationService", "Timeout pkg=" + toastRecord.pkg + " token=" + toastRecord.token);
        }
        synchronized (this.mToastQueue) {
            int indexOfToastLocked = indexOfToastLocked(toastRecord.pkg, toastRecord.token);
            if (indexOfToastLocked >= 0) {
                cancelToastLocked(indexOfToastLocked);
            }
        }
    }

    public final void scheduleKillTokenTimeout(ToastRecord toastRecord) {
        this.mHandler.removeCallbacksAndMessages(toastRecord);
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 7, toastRecord), 11000L);
    }

    public final void handleKillTokenTimeout(ToastRecord toastRecord) {
        if (DBG) {
            Slog.d("NotificationService", "Kill Token Timeout token=" + toastRecord.windowToken);
        }
        synchronized (this.mToastQueue) {
            finishWindowTokenLocked(toastRecord.windowToken, toastRecord.displayId);
        }
    }

    public int indexOfToastLocked(String str, IBinder iBinder) {
        ArrayList arrayList = this.mToastQueue;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ToastRecord toastRecord = (ToastRecord) arrayList.get(i);
            if (toastRecord.pkg.equals(str) && toastRecord.token == iBinder) {
                return i;
            }
        }
        return -1;
    }

    public void keepProcessAliveForToastIfNeeded(int i) {
        synchronized (this.mToastQueue) {
            keepProcessAliveForToastIfNeededLocked(i);
        }
    }

    public final void keepProcessAliveForToastIfNeededLocked(int i) {
        ArrayList arrayList = this.mToastQueue;
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            ToastRecord toastRecord = (ToastRecord) arrayList.get(i3);
            if (toastRecord.pid == i && toastRecord.keepProcessAlive()) {
                i2++;
            }
        }
        try {
            this.mAm.setProcessImportant(this.mForegroundToken, i, i2 > 0, "toast");
        } catch (RemoteException unused) {
        }
    }

    public final boolean isPackageInForegroundForToast(int i) {
        return this.mAtm.hasResumedActivity(i);
    }

    public final boolean blockToast(int i, boolean z, boolean z2, boolean z3) {
        return z2 && !z && !z3 && CompatChanges.isChangeEnabled(128611929L, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0078, code lost:
    
        if (r1.isIntercepted() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0082, code lost:
    
        if (r1.isNewEnoughForAlerting(java.lang.System.currentTimeMillis()) == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0084, code lost:
    
        buzzBeepBlinkLocked(r1);
        com.android.server.notification.ZenLog.traceAlertOnUpdatedIntercept(r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleRankingReconsideration(Message message) {
        Object obj = message.obj;
        if (obj instanceof RankingReconsideration) {
            RankingReconsideration rankingReconsideration = (RankingReconsideration) obj;
            rankingReconsideration.run();
            synchronized (this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(rankingReconsideration.getKey());
                if (notificationRecord == null) {
                    return;
                }
                int findNotificationRecordIndexLocked = findNotificationRecordIndexLocked(notificationRecord);
                boolean isIntercepted = notificationRecord.isIntercepted();
                int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
                boolean isInterruptive = notificationRecord.isInterruptive();
                rankingReconsideration.applyChangesLocked(notificationRecord);
                applyZenModeLocked(notificationRecord);
                this.mRankingHelper.sort(this.mNotificationList);
                boolean z = true;
                boolean z2 = findNotificationRecordIndexLocked != findNotificationRecordIndexLocked(notificationRecord);
                boolean z3 = isIntercepted != notificationRecord.isIntercepted();
                boolean z4 = packageVisibilityOverride != notificationRecord.getPackageVisibilityOverride();
                boolean z5 = notificationRecord.canBubble() && isInterruptive != notificationRecord.isInterruptive();
                if (!z2 && !z3 && !z4 && !z5) {
                    z = false;
                }
                if (z) {
                    this.mHandler.scheduleSendRankingUpdate();
                }
            }
        }
    }

    public void handleRankingSort() {
        if (this.mRankingHelper == null) {
            return;
        }
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            ArrayMap arrayMap = new ArrayMap(size);
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                arrayMap.put(notificationRecord.getKey(), new NotificationRecordExtractorData(i, notificationRecord.getPackageVisibilityOverride(), notificationRecord.canShowBadge(), notificationRecord.canBubble(), notificationRecord.getNotification().isBubbleNotification(), notificationRecord.getChannel(), notificationRecord.getGroupKey(), notificationRecord.getPeopleOverride(), notificationRecord.getSnoozeCriteria(), Integer.valueOf(notificationRecord.getUserSentiment()), Integer.valueOf(notificationRecord.getSuppressedVisualEffects()), notificationRecord.getSystemGeneratedSmartActions(), notificationRecord.getSmartReplies(), notificationRecord.getImportance(), notificationRecord.getRankingScore(), notificationRecord.isConversation(), notificationRecord.getProposedImportance(), notificationRecord.hasSensitiveContent()));
                this.mRankingHelper.extractSignals(notificationRecord);
            }
            this.mRankingHelper.sort(this.mNotificationList);
            for (int i2 = 0; i2 < size; i2++) {
                NotificationRecord notificationRecord2 = (NotificationRecord) this.mNotificationList.get(i2);
                if (arrayMap.containsKey(notificationRecord2.getKey())) {
                    if (((NotificationRecordExtractorData) arrayMap.get(notificationRecord2.getKey())).hasDiffForRankingLocked(notificationRecord2, i2)) {
                        this.mHandler.scheduleSendRankingUpdate();
                    }
                    if (notificationRecord2.hasPendingLogUpdate()) {
                        if (((NotificationRecordExtractorData) arrayMap.get(notificationRecord2.getKey())).hasDiffForLoggingLocked(notificationRecord2, i2)) {
                            this.mNotificationRecordLogger.logNotificationAdjusted(notificationRecord2, i2, 0, getGroupInstanceId(notificationRecord2.getSbn().getGroupKey()));
                        }
                        notificationRecord2.setPendingLogUpdate(false);
                    }
                }
            }
        }
    }

    public final void recordCallerLocked(NotificationRecord notificationRecord) {
        if (this.mZenModeHelper.isCall(notificationRecord)) {
            this.mZenModeHelper.recordCaller(notificationRecord);
        }
    }

    public final void applyZenModeLocked(NotificationRecord notificationRecord) {
        notificationRecord.setIntercepted(this.mZenModeHelper.shouldIntercept(notificationRecord));
        if (notificationRecord.isIntercepted()) {
            notificationRecord.setSuppressedVisualEffects(this.mZenModeHelper.getConsolidatedNotificationPolicy().suppressedVisualEffects);
        } else {
            notificationRecord.setSuppressedVisualEffects(0);
        }
    }

    public final int findNotificationRecordIndexLocked(NotificationRecord notificationRecord) {
        return this.mRankingHelper.indexOf(this.mNotificationList, notificationRecord);
    }

    public final void handleSendRankingUpdate() {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyRankingUpdateLocked(null);
        }
    }

    public final void scheduleListenerHintsChanged(int i) {
        this.mHandler.removeMessages(5);
        this.mHandler.obtainMessage(5, i, 0).sendToTarget();
    }

    public final void scheduleInterruptionFilterChanged(int i) {
        this.mHandler.removeMessages(6);
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }

    public final void handleListenerHintsChanged(int i) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyListenerHintsChangedLocked(i);
        }
    }

    public final void handleListenerInterruptionFilterChanged(int i) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyInterruptionFilterChanged(i);
        }
    }

    public void handleOnPackageChanged(boolean z, int i, String[] strArr, int[] iArr) {
        this.mListeners.onPackagesChanged(z, strArr, iArr);
        this.mAssistants.onPackagesChanged(z, strArr, iArr);
        this.mConditionProviders.onPackagesChanged(z, strArr, iArr);
        boolean onPackagesChanged = this.mPreferencesHelper.onPackagesChanged(z, i, strArr, iArr) | z;
        if (z) {
            int min = Math.min(strArr.length, iArr.length);
            for (int i2 = 0; i2 < min; i2++) {
                this.mHistoryManager.onPackageRemoved(UserHandle.getUserId(iArr[i2]), strArr[i2]);
            }
        }
        if (onPackagesChanged) {
            handleSavePolicyFile();
        }
    }

    public class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 2:
                    NotificationManagerService.this.handleDurationReached((ToastRecord) message.obj);
                    break;
                case 3:
                default:
                    Object obj = message.obj;
                    if (obj instanceof NotificationRecord) {
                        NotificationManagerService.this.handleSendNotificationForOverflow((NotificationRecord) obj, message.arg1, message.arg2);
                        break;
                    }
                    break;
                case 4:
                    NotificationManagerService.this.handleSendRankingUpdate();
                    break;
                case 5:
                    NotificationManagerService.this.handleListenerHintsChanged(message.arg1);
                    break;
                case 6:
                    NotificationManagerService.this.handleListenerInterruptionFilterChanged(message.arg1);
                    break;
                case 7:
                    NotificationManagerService.this.handleKillTokenTimeout((ToastRecord) message.obj);
                    break;
                case 8:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    NotificationManagerService.this.handleOnPackageChanged(((Boolean) someArgs.arg1).booleanValue(), someArgs.argi1, (String[]) someArgs.arg2, (int[]) someArgs.arg3);
                    someArgs.recycle();
                    break;
                case 9:
                    NotificationManagerService.this.handleApplyRestore((byte[]) message.obj, message.arg1);
                    break;
            }
        }

        public void scheduleSendRankingUpdate() {
            if (hasMessages(4)) {
                return;
            }
            sendMessage(Message.obtain(this, 4));
        }

        public void scheduleCancelNotification(CancelNotificationRunnable cancelNotificationRunnable) {
            if (hasCallbacks(cancelNotificationRunnable)) {
                return;
            }
            sendMessage(Message.obtain(this, cancelNotificationRunnable));
        }

        public void scheduleOnPackageChanged(boolean z, int i, String[] strArr, int[] iArr) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = Boolean.valueOf(z);
            obtain.argi1 = i;
            obtain.arg2 = strArr;
            obtain.arg3 = iArr;
            sendMessage(Message.obtain(this, 8, obtain));
        }
    }

    public final class RankingHandlerWorker extends Handler implements RankingHandler {
        public RankingHandlerWorker(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1000) {
                NotificationManagerService.this.handleRankingReconsideration(message);
            } else {
                if (i != 1001) {
                    return;
                }
                NotificationManagerService.this.handleRankingSort();
            }
        }

        @Override // com.android.server.notification.RankingHandler
        public void requestSort() {
            removeMessages(1001);
            Message obtain = Message.obtain();
            obtain.what = 1001;
            sendMessage(obtain);
        }

        @Override // com.android.server.notification.RankingHandler
        public void requestReconsideration(RankingReconsideration rankingReconsideration) {
            sendMessageDelayed(Message.obtain(this, 1000, rankingReconsideration), rankingReconsideration.getDelay(TimeUnit.MILLISECONDS));
        }
    }

    public void sendAccessibilityEvent(NotificationRecord notificationRecord) {
        if (this.mAccessibilityManager.isEnabled()) {
            Notification notification = notificationRecord.getNotification();
            String packageName = notificationRecord.getSbn().getPackageName();
            AccessibilityEvent obtain = AccessibilityEvent.obtain(64);
            obtain.setPackageName(packageName);
            obtain.setClassName(Notification.class.getName());
            int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
            if (packageVisibilityOverride == -1000) {
                packageVisibilityOverride = notification.visibility;
            }
            int identifier = notificationRecord.getUser().getIdentifier();
            if ((identifier >= 0 && this.mKeyguardManager.isDeviceLocked(identifier)) && packageVisibilityOverride != 1) {
                obtain.setParcelableData(notification.publicVersion);
            } else {
                obtain.setParcelableData(notification);
            }
            CharSequence charSequence = notification.tickerText;
            if (!TextUtils.isEmpty(charSequence)) {
                obtain.getText().add(charSequence);
            }
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public final boolean removeFromNotificationListsLocked(NotificationRecord notificationRecord) {
        boolean z;
        NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, notificationRecord.getKey());
        if (findNotificationByListLocked != null) {
            this.mNotificationList.remove(findNotificationByListLocked);
            this.mNotificationsByKey.remove(findNotificationByListLocked.getSbn().getKey());
            z = true;
        } else {
            z = false;
        }
        while (true) {
            NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, notificationRecord.getKey());
            if (findNotificationByListLocked2 == null) {
                return z;
            }
            this.mEnqueuedNotifications.remove(findNotificationByListLocked2);
        }
    }

    public final void cancelNotificationLocked(NotificationRecord notificationRecord, boolean z, int i, boolean z2, String str, long j) {
        cancelNotificationLocked(notificationRecord, z, i, -1, -1, z2, str, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01d8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void cancelNotificationLocked(final NotificationRecord notificationRecord, boolean z, int i, int i2, int i3, boolean z2, String str, long j) {
        String groupKey;
        NotificationRecord notificationRecord2;
        ArrayMap arrayMap;
        LogMaker subtype;
        SemGoodCatchManager semGoodCatchManager;
        SmartAlertController smartAlertController;
        PendingIntent pendingIntent;
        String key = notificationRecord.getKey();
        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 1, new Intent(ACTION_NOTIFICATION_TIMEOUT).setData(new Uri.Builder().scheme("timeout").appendPath(notificationRecord.getKey()).build()).addFlags(268435456), 603979776);
        if (broadcast != null) {
            this.mAlarmManager.cancel(broadcast);
        }
        cancelScheduleTimeoutLocked(notificationRecord);
        recordCallerLocked(notificationRecord);
        if (notificationRecord.getStats().getDismissalSurface() == -1) {
            notificationRecord.recordDismissalSurface(0);
        }
        if (z && (pendingIntent = notificationRecord.getNotification().deleteIntent) != null) {
            try {
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).clearPendingIntentAllowBgActivityStarts(pendingIntent.getTarget(), ALLOWLIST_TOKEN);
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Slog.w("NotificationService", "canceled PendingIntent for " + notificationRecord.getSbn().getPackageName(), e);
            }
        }
        if (z2) {
            if (notificationRecord.getNotification().getSmallIcon() != null) {
                if (i != 18) {
                    notificationRecord.isCanceled = true;
                }
                this.mListeners.notifyRemovedLocked(notificationRecord, i, notificationRecord.getStats());
                EdgeLightingManager edgeLightingManager = this.mEdgeLightingManager;
                if (edgeLightingManager != null) {
                    edgeLightingManager.hideForNotification(notificationRecord.getSbn(), Binder.getCallingUid());
                }
                this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.22
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationManagerService.this.mGroupHelper.onNotificationRemoved(notificationRecord.getSbn());
                    }
                });
            }
            if (key.equals(this.mSoundNotificationKey)) {
                clearSoundLocked();
            }
            if (key.equals(this.mVibrateNotificationKey)) {
                clearVibrateLocked();
            }
            this.mLights.remove(key);
        }
        if (i != 2 && i != 3) {
            switch (i) {
                case 8:
                case 9:
                    this.mUsageStats.registerRemovedByApp(notificationRecord);
                    this.mUsageStatsManagerInternal.reportNotificationRemoved(notificationRecord.getSbn().getOpPkg(), notificationRecord.getUser(), j);
                    break;
            }
            groupKey = notificationRecord.getGroupKey();
            notificationRecord2 = (NotificationRecord) this.mSummaryByGroupKey.get(groupKey);
            if (notificationRecord2 != null && notificationRecord2.getKey().equals(key)) {
                this.mSummaryByGroupKey.remove(groupKey);
            }
            arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(notificationRecord.getSbn().getUserId()));
            if (arrayMap != null && notificationRecord.getSbn().getKey().equals(arrayMap.get(notificationRecord.getSbn().getPackageName()))) {
                arrayMap.remove(notificationRecord.getSbn().getPackageName());
            }
            if (i != 20) {
                this.mArchive.record(notificationRecord.getSbn(), i);
            }
            long currentTimeMillis = System.currentTimeMillis();
            subtype = notificationRecord.getItemLogMaker().setType(5).setSubtype(i);
            if (i2 != -1 && i3 != -1) {
                subtype.addTaggedData(798, Integer.valueOf(i2)).addTaggedData(1395, Integer.valueOf(i3));
            }
            MetricsLogger.action(subtype);
            EventLogTags.writeNotificationCanceled(key, i, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), i2, i3, str);
            semGoodCatchManager = this.mNotiSemGoodCatchManager;
            if (semGoodCatchManager != null && this.mGoodCatchNotiBlockedOn) {
                semGoodCatchManager.update("noti_blocked", notificationRecord.getSbn().getPackageName(), i, (String) null, str);
            }
            if (z2) {
                try {
                    this.mNotificationRecordLogger.logNotificationCancelled(notificationRecord, i, notificationRecord.getStats().getDismissalSurface());
                } catch (IllegalArgumentException e2) {
                    Slog.d("NotificationService", "logNotificationCancelled IllegalArgumentException ", e2);
                }
                canceledNotificationLog(notificationRecord, i);
                NotificationReminder notificationReminder = this.mNotificationReminder;
                if (notificationReminder != null) {
                    notificationReminder.removeFromNotificationList(notificationRecord);
                }
            }
            smartAlertController = this.mSmartAlertController;
            if (smartAlertController == null) {
                smartAlertController.checkMissedEvent(this.mNotificationList);
                return;
            }
            return;
        }
        this.mUsageStats.registerDismissedByUser(notificationRecord);
        groupKey = notificationRecord.getGroupKey();
        notificationRecord2 = (NotificationRecord) this.mSummaryByGroupKey.get(groupKey);
        if (notificationRecord2 != null) {
            this.mSummaryByGroupKey.remove(groupKey);
        }
        arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(notificationRecord.getSbn().getUserId()));
        if (arrayMap != null) {
            arrayMap.remove(notificationRecord.getSbn().getPackageName());
        }
        if (i != 20) {
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        subtype = notificationRecord.getItemLogMaker().setType(5).setSubtype(i);
        if (i2 != -1) {
            subtype.addTaggedData(798, Integer.valueOf(i2)).addTaggedData(1395, Integer.valueOf(i3));
        }
        MetricsLogger.action(subtype);
        EventLogTags.writeNotificationCanceled(key, i, notificationRecord.getLifespanMs(currentTimeMillis2), notificationRecord.getFreshnessMs(currentTimeMillis2), notificationRecord.getExposureMs(currentTimeMillis2), i2, i3, str);
        semGoodCatchManager = this.mNotiSemGoodCatchManager;
        if (semGoodCatchManager != null) {
            semGoodCatchManager.update("noti_blocked", notificationRecord.getSbn().getPackageName(), i, (String) null, str);
        }
        if (z2) {
        }
        smartAlertController = this.mSmartAlertController;
        if (smartAlertController == null) {
        }
    }

    public void updateUriPermissions(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, String str, int i) {
        updateUriPermissions(notificationRecord, notificationRecord2, str, i, false);
    }

    public void updateUriPermissions(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, String str, int i, boolean z) {
        IBinder iBinder;
        String key = notificationRecord != null ? notificationRecord.getKey() : notificationRecord2.getKey();
        boolean z2 = DBG;
        if (z2) {
            Slog.d("NotificationService", key + ": updating permissions");
        }
        ArraySet grantableUris = notificationRecord != null ? notificationRecord.getGrantableUris() : null;
        ArraySet grantableUris2 = notificationRecord2 != null ? notificationRecord2.getGrantableUris() : null;
        if (grantableUris == null && grantableUris2 == null) {
            return;
        }
        IBinder iBinder2 = notificationRecord != null ? notificationRecord.permissionOwner : null;
        if (notificationRecord2 != null && iBinder2 == null) {
            iBinder2 = notificationRecord2.permissionOwner;
        }
        if (grantableUris != null && iBinder2 == null) {
            if (z2) {
                Slog.d("NotificationService", key + ": creating owner");
            }
            iBinder2 = this.mUgmInternal.newUriPermissionOwner("NOTIF:" + key);
        }
        if (grantableUris != null || iBinder2 == null || z) {
            iBinder = iBinder2;
        } else {
            destroyPermissionOwner(iBinder2, UserHandle.getUserId(notificationRecord2.getUid()), key);
            iBinder = null;
        }
        if (grantableUris != null && iBinder != null) {
            for (int i2 = 0; i2 < grantableUris.size(); i2++) {
                Uri uri = (Uri) grantableUris.valueAt(i2);
                if (grantableUris2 == null || !grantableUris2.contains(uri)) {
                    if (DBG) {
                        Slog.d("NotificationService", key + ": granting " + uri);
                    }
                    grantUriPermission(iBinder, uri, notificationRecord.getUid(), str, i);
                }
            }
        }
        if (grantableUris2 != null && iBinder != null) {
            for (int i3 = 0; i3 < grantableUris2.size(); i3++) {
                Uri uri2 = (Uri) grantableUris2.valueAt(i3);
                if (grantableUris == null || !grantableUris.contains(uri2)) {
                    if (DBG) {
                        Slog.d("NotificationService", key + ": revoking " + uri2);
                    }
                    if (z) {
                        revokeUriPermission(iBinder, uri2, UserHandle.getUserId(notificationRecord2.getUid()), str, i);
                    } else {
                        revokeUriPermission(iBinder, uri2, UserHandle.getUserId(notificationRecord2.getUid()), null, -1);
                    }
                }
            }
        }
        if (notificationRecord != null) {
            notificationRecord.permissionOwner = iBinder;
        }
    }

    public final void grantUriPermission(IBinder iBinder, Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mUgm.grantUriPermissionFromOwner(iBinder, i, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), i2);
            } catch (RemoteException unused) {
            } catch (SecurityException unused2) {
                Slog.e("NotificationService", "Cannot grant uri access; " + i + " does not own " + uri);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void revokeUriPermission(IBinder iBinder, Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.revokeUriPermissionFromOwner(iBinder, ContentProvider.getUriWithoutUserId(uri), 1, userIdFromUri, str, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void destroyPermissionOwner(IBinder iBinder, int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (DBG) {
                Slog.d("NotificationService", str + ": destroying owner");
            }
            this.mUgmInternal.revokeUriPermissionFromOwner(iBinder, null, -1, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelNotification(int i, int i2, String str, String str2, int i3, int i4, int i5, boolean z, int i6, int i7, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        cancelNotification(i, i2, str, str2, i3, i4, i5, z, i6, i7, -1, -1, managedServiceInfo);
    }

    public void cancelNotification(int i, int i2, String str, String str2, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, int i9, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        this.mHandler.scheduleCancelNotification(new CancelNotificationRunnable(i, i2, str, str2, i3, i4, i5, z, i6, i7, i8, i9, managedServiceInfo, SystemClock.elapsedRealtime()));
    }

    public final boolean notificationMatchesUserId(NotificationRecord notificationRecord, int i) {
        return i == -1 || notificationRecord.getUserId() == -1 || notificationRecord.getUserId() == i;
    }

    public final boolean notificationMatchesCurrentProfiles(NotificationRecord notificationRecord, int i) {
        return notificationMatchesUserId(notificationRecord, i) || this.mUserProfiles.isCurrentProfile(notificationRecord.getUserId());
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$23 */
    public class RunnableC200223 implements Runnable {
        public final /* synthetic */ int val$callingPid;
        public final /* synthetic */ int val$callingUid;
        public final /* synthetic */ long val$cancellationElapsedTimeMs;
        public final /* synthetic */ String val$channelId;
        public final /* synthetic */ boolean val$doit;
        public final /* synthetic */ ManagedServices.ManagedServiceInfo val$listener;
        public final /* synthetic */ int val$mustHaveFlags;
        public final /* synthetic */ int val$mustNotHaveFlags;
        public final /* synthetic */ String val$pkg;
        public final /* synthetic */ int val$reason;
        public final /* synthetic */ int val$userId;

        public static /* synthetic */ boolean lambda$run$0(int i, int i2, int i3) {
            return (i3 & i) == i && (i3 & i2) == 0;
        }

        public RunnableC200223(ManagedServices.ManagedServiceInfo managedServiceInfo, int i, int i2, String str, int i3, int i4, int i5, int i6, boolean z, String str2, long j) {
            this.val$listener = managedServiceInfo;
            this.val$callingUid = i;
            this.val$callingPid = i2;
            this.val$pkg = str;
            this.val$userId = i3;
            this.val$mustHaveFlags = i4;
            this.val$mustNotHaveFlags = i5;
            this.val$reason = i6;
            this.val$doit = z;
            this.val$channelId = str2;
            this.val$cancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            ManagedServices.ManagedServiceInfo managedServiceInfo = this.val$listener;
            String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
            EventLogTags.writeNotificationCancelAll(this.val$callingUid, this.val$callingPid, this.val$pkg, this.val$userId, this.val$mustHaveFlags, this.val$mustNotHaveFlags, this.val$reason, shortString);
            if (this.val$doit) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        try {
                            final int i = this.val$mustHaveFlags;
                            final int i2 = this.val$mustNotHaveFlags;
                            FlagChecker flagChecker = new FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$23$$ExternalSyntheticLambda0
                                @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                                public final boolean apply(int i3) {
                                    boolean lambda$run$0;
                                    lambda$run$0 = NotificationManagerService.RunnableC200223.lambda$run$0(i, i2, i3);
                                    return lambda$run$0;
                                }
                            };
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            notificationManagerService.cancelAllNotificationsByListLocked(notificationManagerService.mNotificationList, this.val$callingUid, this.val$callingPid, this.val$pkg, true, this.val$channelId, flagChecker, false, this.val$userId, false, this.val$reason, shortString, true, this.val$cancellationElapsedTimeMs);
                            NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                            notificationManagerService2.cancelAllNotificationsByListLocked(notificationManagerService2.mEnqueuedNotifications, this.val$callingUid, this.val$callingPid, this.val$pkg, true, this.val$channelId, flagChecker, false, this.val$userId, false, this.val$reason, shortString, false, this.val$cancellationElapsedTimeMs);
                            NotificationManagerService.this.mSnoozeHelper.cancel(this.val$userId, this.val$pkg);
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
            }
        }
    }

    public void cancelAllNotificationsInt(int i, int i2, String str, String str2, int i3, int i4, boolean z, int i5, int i6, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        this.mHandler.post(new RunnableC200223(managedServiceInfo, i, i2, str, i5, i3, i4, i6, z, str2, SystemClock.elapsedRealtime()));
    }

    public final void cancelAllNotificationsByListLocked(ArrayList arrayList, int i, int i2, String str, boolean z, String str2, FlagChecker flagChecker, boolean z2, int i3, boolean z3, int i4, String str3, boolean z4, long j) {
        HashSet hashSet = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(size);
            if (z2) {
                if (!notificationMatchesCurrentProfiles(notificationRecord, i3)) {
                }
                if ((z || str != null || notificationRecord.getUserId() != -1) && flagChecker.apply(notificationRecord.getFlags()) && ((str == null || notificationRecord.getSbn().getPackageName().equals(str)) && (str2 == null || str2.equals(notificationRecord.getChannel().getId())))) {
                    if (!notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().isGroupChild()) {
                        if (hashSet == null) {
                            hashSet = new HashSet();
                        }
                        hashSet.add(notificationRecord.getKey());
                    } else {
                        arrayList.remove(size);
                        this.mNotificationsByKey.remove(notificationRecord.getKey());
                        notificationRecord.recordDismissalSentiment(1);
                        cancelNotificationLocked(notificationRecord, z3, i4, z4, str3, j);
                    }
                }
            } else {
                if (!notificationMatchesUserId(notificationRecord, i3)) {
                }
                if (z) {
                }
                if (!notificationRecord.getSbn().isGroup()) {
                }
                arrayList.remove(size);
                this.mNotificationsByKey.remove(notificationRecord.getKey());
                notificationRecord.recordDismissalSentiment(1);
                cancelNotificationLocked(notificationRecord, z3, i4, z4, str3, j);
            }
        }
        if (hashSet != null) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                NotificationRecord notificationRecord2 = (NotificationRecord) arrayList.get(size2);
                if (hashSet.contains(notificationRecord2.getKey())) {
                    arrayList.remove(size2);
                    this.mNotificationsByKey.remove(notificationRecord2.getKey());
                    notificationRecord2.recordDismissalSentiment(1);
                    cancelNotificationLocked(notificationRecord2, z3, i4, z4, str3, j);
                }
            }
            updateLightsLocked();
        }
    }

    public void snoozeNotificationInt(String str, long j, String str2, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (managedServiceInfo == null) {
            return;
        }
        String shortString = managedServiceInfo.component.toShortString();
        if ((j > 0 || str2 != null) && str != null) {
            synchronized (this.mNotificationLock) {
                NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked = findInCurrentAndSnoozedNotificationByKeyLocked(str);
                if (findInCurrentAndSnoozedNotificationByKeyLocked == null) {
                    return;
                }
                if (managedServiceInfo.enabledAndUserMatches(findInCurrentAndSnoozedNotificationByKeyLocked.getSbn().getNormalizedUserId())) {
                    if (DBG) {
                        Slog.d("NotificationService", String.format("snooze event(%s, %d, %s, %s)", str, Long.valueOf(j), str2, shortString));
                    }
                    this.mHandler.post(new SnoozeNotificationRunnable(str, j, str2));
                }
            }
        }
    }

    public void unsnoozeNotificationInt(String str, ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
        String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
        if (DBG) {
            Slog.d("NotificationService", String.format("unsnooze event(%s, %s)", str, shortString));
        }
        this.mSnoozeHelper.repost(str, z);
        handleSavePolicyFile();
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$24 */
    public class RunnableC200324 implements Runnable {
        public final /* synthetic */ int val$callingPid;
        public final /* synthetic */ int val$callingUid;
        public final /* synthetic */ long val$cancellationElapsedTimeMs;
        public final /* synthetic */ boolean val$includeCurrentProfiles;
        public final /* synthetic */ ManagedServices.ManagedServiceInfo val$listener;
        public final /* synthetic */ int val$reason;
        public final /* synthetic */ int val$userId;

        public static /* synthetic */ boolean lambda$run$0(int i, int i2) {
            return (((11 == i || 3 == i) ? 4130 : 34) & i2) == 0;
        }

        public RunnableC200324(ManagedServices.ManagedServiceInfo managedServiceInfo, int i, int i2, int i3, int i4, boolean z, long j) {
            this.val$listener = managedServiceInfo;
            this.val$callingUid = i;
            this.val$callingPid = i2;
            this.val$userId = i3;
            this.val$reason = i4;
            this.val$includeCurrentProfiles = z;
            this.val$cancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = this.val$listener;
                String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
                EventLogTags.writeNotificationCancelAll(this.val$callingUid, this.val$callingPid, null, this.val$userId, 0, 0, this.val$reason, shortString);
                final int i = this.val$reason;
                FlagChecker flagChecker = new FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$24$$ExternalSyntheticLambda0
                    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                    public final boolean apply(int i2) {
                        boolean lambda$run$0;
                        lambda$run$0 = NotificationManagerService.RunnableC200324.lambda$run$0(i, i2);
                        return lambda$run$0;
                    }
                };
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                notificationManagerService.cancelAllNotificationsByListLocked(notificationManagerService.mNotificationList, this.val$callingUid, this.val$callingPid, null, false, null, flagChecker, this.val$includeCurrentProfiles, this.val$userId, true, this.val$reason, shortString, true, this.val$cancellationElapsedTimeMs);
                NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                notificationManagerService2.cancelAllNotificationsByListLocked(notificationManagerService2.mEnqueuedNotifications, this.val$callingUid, this.val$callingPid, null, false, null, flagChecker, this.val$includeCurrentProfiles, this.val$userId, true, this.val$reason, shortString, false, this.val$cancellationElapsedTimeMs);
                NotificationManagerService.this.mSnoozeHelper.cancel(this.val$userId, this.val$includeCurrentProfiles);
            }
        }
    }

    public void cancelAllLocked(int i, int i2, int i3, int i4, ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
        this.mHandler.post(new RunnableC200324(managedServiceInfo, i, i2, i3, i4, z, SystemClock.elapsedRealtime()));
    }

    public final void cancelGroupSummaryLocked(NotificationRecord notificationRecord, int i, int i2, String str, boolean z, int i3, long j) {
        if (notificationRecord.getNotification().isGroupChild()) {
            String packageName = notificationRecord.getSbn().getPackageName();
            if (packageName == null) {
                if (DBG) {
                    Slog.e("NotificationService", "No package for group summary: " + notificationRecord.getKey());
                    return;
                }
                return;
            }
            List findGroupNotificationsLocked = findGroupNotificationsLocked(packageName, notificationRecord.getGroupKey(), notificationRecord.getSbn().getUserId());
            if (findGroupNotificationsLocked.size() == 1) {
                NotificationRecord notificationRecord2 = (NotificationRecord) findGroupNotificationsLocked.get(0);
                if (notificationRecord2.getNotification().isGroupSummary() && notificationRecord2.getGroupKey().equals(notificationRecord.getGroupKey())) {
                    cancelNotificationLocked(notificationRecord2, z, 24, removeFromNotificationListsLocked(notificationRecord2), str, j);
                }
            }
        }
    }

    public final void cancelGroupChildrenLocked(NotificationRecord notificationRecord, int i, int i2, String str, boolean z, FlagChecker flagChecker, int i3, long j) {
        if (notificationRecord.getNotification().isGroupSummary()) {
            if (notificationRecord.getSbn().getPackageName() == null) {
                if (DBG) {
                    Slog.e("NotificationService", "No package for group summary: " + notificationRecord.getKey());
                    return;
                }
                return;
            }
            cancelGroupChildrenByListLocked(this.mNotificationList, notificationRecord, i, i2, str, z, true, flagChecker, i3, j);
            cancelGroupChildrenByListLocked(this.mEnqueuedNotifications, notificationRecord, i, i2, str, z, false, flagChecker, i3, j);
        }
    }

    public final void cancelGroupChildrenByListLocked(ArrayList arrayList, NotificationRecord notificationRecord, int i, int i2, String str, boolean z, boolean z2, FlagChecker flagChecker, int i3, long j) {
        String packageName = notificationRecord.getSbn().getPackageName();
        int userId = notificationRecord.getUserId();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationRecord notificationRecord2 = (NotificationRecord) arrayList.get(size);
            StatusBarNotification sbn = notificationRecord2.getSbn();
            if (!sbn.isGroup() || sbn.getNotification().isGroupSummary() || !notificationRecord2.getGroupKey().equals(notificationRecord.getGroupKey()) || ((flagChecker != null && !flagChecker.apply(notificationRecord2.getFlags())) || (notificationRecord2.getChannel().isImportantConversation() && i3 == 2))) {
            }
            EventLogTags.writeNotificationCancel(i, i2, packageName, sbn.getId(), sbn.getTag(), userId, 0, 0, 12, str);
            arrayList.remove(size);
            this.mNotificationsByKey.remove(notificationRecord2.getKey());
            cancelNotificationLocked(notificationRecord2, z, 12, z2, str, j);
        }
    }

    public void updateLightsLocked() {
        if (this.mNotificationLight == null) {
            return;
        }
        NotificationRecord notificationRecord = null;
        while (notificationRecord == null && !this.mLights.isEmpty()) {
            ArrayList arrayList = this.mLights;
            String str = (String) arrayList.get(arrayList.size() - 1);
            NotificationRecord notificationRecord2 = (NotificationRecord) this.mNotificationsByKey.get(str);
            if (notificationRecord2 == null) {
                Slog.wtfStack("NotificationService", "LED Notification does not exist: " + str);
                this.mLights.remove(str);
            }
            notificationRecord = notificationRecord2;
        }
        if (notificationRecord == null || isInCall() || this.mScreenOn) {
            this.mNotificationLight.turnOff();
            return;
        }
        NotificationRecord.Light light = notificationRecord.getLight();
        if (light == null || !this.mNotificationPulseEnabled) {
            return;
        }
        this.mNotificationLight.setFlashing(light.color, 1, light.onMs, light.offMs);
    }

    public final void cancelOldestNotification() {
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            Slog.d("NotificationService", "cancelOldestNotification start N = " + size);
            NotificationRecord notificationRecord = null;
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord2 = (NotificationRecord) this.mNotificationList.get(i);
                if (isDeleteable(notificationRecord2) && (notificationRecord == null || notificationRecord.getUpdateTimeMs() > notificationRecord2.getUpdateTimeMs())) {
                    notificationRecord = notificationRecord2;
                }
            }
            if (notificationRecord == null) {
                return;
            }
            final StatusBarNotification sbn = notificationRecord.getSbn();
            final int identifier = notificationRecord.getUser().getIdentifier();
            Slog.d("NotificationService", "cancelOldestNotification end oldest = " + notificationRecord.getKey() + ", time = " + this.dayTime.format(new Date(notificationRecord.getUpdateTimeMs())));
            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.this.lambda$cancelOldestNotification$15(sbn, identifier);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelOldestNotification$15(StatusBarNotification statusBarNotification, int i) {
        cancelNotification(MY_UID, MY_PID, statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), 0, 0, false, i, 25, null);
    }

    public final boolean isDeleteable(NotificationRecord notificationRecord) {
        StatusBarNotification sbn = notificationRecord.getSbn();
        Notification notification = sbn.getNotification();
        return ((sbn.isGroup() && notification.isGroupSummary()) || (notification.flags & IInstalld.FLAG_FORCE) != 0 || notification.isFgsOrUij()) ? false : true;
    }

    public List findCurrentAndSnoozedGroupNotificationsLocked(String str, String str2, int i) {
        ArrayList notifications = this.mSnoozeHelper.getNotifications(str, str2, Integer.valueOf(i));
        notifications.addAll(findGroupNotificationsLocked(str, str2, i));
        return notifications;
    }

    public List findGroupNotificationsLocked(String str, String str2, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(findGroupNotificationByListLocked(this.mNotificationList, str, str2, i));
        arrayList.addAll(findGroupNotificationByListLocked(this.mEnqueuedNotifications, str, str2, i));
        return arrayList;
    }

    public final NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked(String str) {
        NotificationRecord findNotificationByKeyLocked = findNotificationByKeyLocked(str);
        return findNotificationByKeyLocked == null ? this.mSnoozeHelper.getNotification(str) : findNotificationByKeyLocked;
    }

    public final List findGroupNotificationByListLocked(ArrayList arrayList, String str, String str2, int i) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i2);
            if (notificationMatchesUserId(notificationRecord, i) && notificationRecord.getGroupKey().equals(str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                arrayList2.add(notificationRecord);
            }
        }
        return arrayList2;
    }

    public final NotificationRecord findNotificationByKeyLocked(String str) {
        NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, str);
        if (findNotificationByListLocked != null) {
            return findNotificationByListLocked;
        }
        NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, str);
        if (findNotificationByListLocked2 != null) {
            return findNotificationByListLocked2;
        }
        return null;
    }

    public NotificationRecord findNotificationLocked(String str, String str2, int i, int i2) {
        NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, str, str2, i, i2);
        if (findNotificationByListLocked != null) {
            return findNotificationByListLocked;
        }
        NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, str, str2, i, i2);
        if (findNotificationByListLocked2 != null) {
            return findNotificationByListLocked2;
        }
        return null;
    }

    public final NotificationRecord findNotificationByListLocked(ArrayList arrayList, String str, String str2, int i, int i2) {
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i3);
            if (notificationMatchesUserId(notificationRecord, i2) && notificationRecord.getSbn().getId() == i && TextUtils.equals(notificationRecord.getSbn().getTag(), str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                return notificationRecord;
            }
        }
        return null;
    }

    public final List findNotificationsByListLocked(ArrayList arrayList, String str, String str2, int i, int i2) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i3);
            if (notificationMatchesUserId(notificationRecord, i2) && notificationRecord.getSbn().getId() == i && TextUtils.equals(notificationRecord.getSbn().getTag(), str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                arrayList2.add(notificationRecord);
            }
        }
        return arrayList2;
    }

    public final NotificationRecord findNotificationByListLocked(ArrayList arrayList, String str) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(((NotificationRecord) arrayList.get(i)).getKey())) {
                return (NotificationRecord) arrayList.get(i);
            }
        }
        return null;
    }

    public int indexOfNotificationLocked(String str) {
        int size = this.mNotificationList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(((NotificationRecord) this.mNotificationList.get(i)).getKey())) {
                return i;
            }
        }
        return -1;
    }

    public final void hideNotificationsForPackages(String[] strArr, int[] iArr) {
        synchronized (this.mNotificationLock) {
            Set set = (Set) Arrays.stream(iArr).boxed().collect(Collectors.toSet());
            List asList = Arrays.asList(strArr);
            ArrayList arrayList = new ArrayList();
            int size = this.mNotificationList.size();
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                if (asList.contains(notificationRecord.getSbn().getPackageName()) && set.contains(Integer.valueOf(notificationRecord.getUid()))) {
                    notificationRecord.setHidden(true);
                    arrayList.add(notificationRecord);
                }
            }
            this.mListeners.notifyHiddenLocked(arrayList);
        }
    }

    public final void unhideNotificationsForPackages(String[] strArr, int[] iArr) {
        synchronized (this.mNotificationLock) {
            Set set = (Set) Arrays.stream(iArr).boxed().collect(Collectors.toSet());
            List asList = Arrays.asList(strArr);
            ArrayList arrayList = new ArrayList();
            int size = this.mNotificationList.size();
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                if (asList.contains(notificationRecord.getSbn().getPackageName()) && set.contains(Integer.valueOf(notificationRecord.getUid()))) {
                    notificationRecord.setHidden(false);
                    arrayList.add(notificationRecord);
                }
            }
            this.mListeners.notifyUnhiddenLocked(arrayList);
        }
    }

    public final void cancelNotificationsWhenEnterLockDownMode(int i) {
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            for (int i2 = 0; i2 < size; i2++) {
                NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i2);
                if (notificationRecord.getUser().getIdentifier() == i) {
                    this.mListeners.notifyRemovedLocked(notificationRecord, 23, notificationRecord.getStats());
                }
            }
        }
    }

    public final void postNotificationsWhenExitLockDownMode(int i) {
        synchronized (this.mNotificationLock) {
            int size = this.mNotificationList.size();
            long j = 0;
            for (int i2 = 0; i2 < size; i2++) {
                final NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i2);
                if (notificationRecord.getUser().getIdentifier() == i) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda17
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.this.lambda$postNotificationsWhenExitLockDownMode$16(notificationRecord);
                        }
                    }, j);
                    j += 20;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postNotificationsWhenExitLockDownMode$16(NotificationRecord notificationRecord) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyPostedLocked(notificationRecord, notificationRecord);
        }
    }

    public final void updateNotificationPulse() {
        synchronized (this.mNotificationLock) {
            updateLightsLocked();
        }
    }

    public boolean isCallingUidSystem() {
        return Binder.getCallingUid() == 1000;
    }

    public boolean isCallingAppIdSystem() {
        return UserHandle.getAppId(Binder.getCallingUid()) == 1000;
    }

    public boolean isUidSystemOrPhone(int i) {
        int appId = UserHandle.getAppId(i);
        return appId == 1000 || appId == 1001 || i == 0;
    }

    public boolean isCallerSystemOrPhone() {
        return isUidSystemOrPhone(Binder.getCallingUid());
    }

    public boolean isCallerIsSystemOrSystemUi() {
        return isCallerSystemOrPhone() || getContext().checkCallingPermission("android.permission.STATUS_BAR_SERVICE") == 0;
    }

    public final boolean isCallerIsSystemOrSysemUiOrShell() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return true;
        }
        return isCallerIsSystemOrSystemUi();
    }

    public final void checkCallerIsSystemOrShell() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return;
        }
        checkCallerIsSystem();
    }

    public final void checkCallerIsSystem() {
        if (isCallerSystemOrPhone()) {
            return;
        }
        throw new SecurityException("Disallowed call for uid " + Binder.getCallingUid());
    }

    public final void checkCallerIsSystemOrSystemUiOrShell() {
        checkCallerIsSystemOrSystemUiOrShell(null);
    }

    public final void checkCallerIsSystemOrSystemUiOrShell(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0 || isCallerSystemOrPhone()) {
            return;
        }
        getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
    }

    public final void checkCallerIsSystemOrSameApp(String str) {
        if (isCallerSystemOrPhone()) {
            return;
        }
        checkCallerIsSameApp(str);
    }

    public final boolean isCallerAndroid(String str, int i) {
        return isUidSystemOrPhone(i) && str != null && "android".equals(str);
    }

    public final void checkRestrictedCategories(Notification notification) {
        try {
            if (!this.mPackageManager.hasSystemFeature("android.hardware.type.automotive", 0)) {
                return;
            }
        } catch (RemoteException unused) {
            if (DBG) {
                Slog.e("NotificationService", "Unable to confirm if it's safe to skip category restrictions check thus the check will be done anyway");
            }
        }
        if ("car_emergency".equals(notification.category) || "car_warning".equals(notification.category) || "car_information".equals(notification.category)) {
            getContext().enforceCallingPermission("android.permission.SEND_CATEGORY_CAR_NOTIFICATIONS", String.format("Notification category %s restricted", notification.category));
        }
    }

    public boolean isCallerInstantApp(int i, int i2) {
        if (isUidSystemOrPhone(i)) {
            return false;
        }
        if (i2 == -1) {
            i2 = 0;
        }
        try {
            String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                throw new SecurityException("Unknown uid " + i);
            }
            String str = packagesForUid[0];
            this.mAppOps.checkPackage(i, str);
            ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 0L, i2);
            if (applicationInfo == null) {
                throw new SecurityException("Unknown package " + str);
            }
            return applicationInfo.isInstantApp();
        } catch (RemoteException e) {
            throw new SecurityException("Unknown uid " + i, e);
        }
    }

    public final void checkCallerIsSameApp(String str) {
        checkCallerIsSameApp(str, Binder.getCallingUid(), UserHandle.getCallingUserId());
    }

    public final void checkCallerIsSameApp(String str, int i, int i2) {
        if ((i == 0 && "root".equals(str)) || this.mPackageManagerInternal.isSameApp(str, i, i2)) {
            return;
        }
        throw new SecurityException("Package " + str + " is not owned by uid " + i);
    }

    public final boolean isCallerSameApp(String str, int i, int i2) {
        try {
            checkCallerIsSameApp(str, i, i2);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public final boolean checkCallerIsPushService(String str, String str2) {
        return isCallerSystemOrPhone() && str2 != null && "com.samsung.android.pushservice".equals(str2) && !str2.equals(str);
    }

    public final boolean checkCallerIsSystemUI() {
        try {
            String nameForUid = getContext().getPackageManager().getNameForUid(Binder.getCallingUid());
            Slog.d("NotificationService", "checkCallerIsSystemUI() caller " + nameForUid);
            int lastIndexOf = nameForUid.lastIndexOf(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            Slog.d("NotificationService", "isCallerSystemUI caller " + nameForUid);
            if (nameForUid != null) {
                if (nameForUid.equals("android.uid.systemui")) {
                    return true;
                }
            }
        } catch (Exception e) {
            Slog.d("NotificationService", "The exception occurs " + e.getMessage());
        }
        return false;
    }

    public static String callStateToString(int i) {
        if (i == 0) {
            return "CALL_STATE_IDLE";
        }
        if (i == 1) {
            return "CALL_STATE_RINGING";
        }
        if (i == 2) {
            return "CALL_STATE_OFFHOOK";
        }
        return "CALL_STATE_UNKNOWN_" + i;
    }

    public NotificationRankingUpdate makeRankingUpdateLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
        int size = this.mNotificationList.size();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
            if (!isInLockDownMode(notificationRecord.getUser().getIdentifier()) && isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo)) {
                String key = notificationRecord.getSbn().getKey();
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                ranking.populate(key, arrayList.size(), !notificationRecord.isIntercepted(), notificationRecord.getPackageVisibilityOverride(), notificationRecord.getSuppressedVisualEffects(), notificationRecord.getImportance(), notificationRecord.getImportanceExplanation(), notificationRecord.getSbn().getOverrideGroupKey(), notificationRecord.getChannel(), notificationRecord.getPeopleOverride(), notificationRecord.getSnoozeCriteria(), notificationRecord.canShowBadge(), notificationRecord.getUserSentiment(), notificationRecord.isHidden(), notificationRecord.getLastAudiblyAlertedMs(), (notificationRecord.getSound() == null && notificationRecord.getVibration() == null) ? false : true, notificationRecord.getSystemGeneratedSmartActions(), notificationRecord.getSmartReplies(), notificationRecord.canBubble(), notificationRecord.isTextChanged(), notificationRecord.isConversation(), notificationRecord.getShortcutInfo(), notificationRecord.getRankingScore() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? 0 : notificationRecord.getRankingScore() > DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? 1 : -1, notificationRecord.getNotification().isBubbleNotification(), notificationRecord.getProposedImportance(), notificationRecord.hasSensitiveContent());
                arrayList.add(ranking);
            }
        }
        return new NotificationRankingUpdate((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
    }

    public boolean isInLockDownMode(int i) {
        return this.mStrongAuthTracker.isInLockDownMode(i);
    }

    public boolean hasCompanionDevice(ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (this.mCompanionManager == null) {
            this.mCompanionManager = getCompanionManager();
        }
        if (this.mCompanionManager == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (!ArrayUtils.isEmpty(this.mCompanionManager.getAssociations(managedServiceInfo.component.getPackageName(), managedServiceInfo.userid))) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            } catch (RemoteException e) {
                Slog.e("NotificationService", "Cannot reach companion device service", e);
            } catch (SecurityException unused) {
            } catch (Exception e2) {
                Slog.e("NotificationService", "Cannot verify listener " + managedServiceInfo, e2);
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ICompanionDeviceManager getCompanionManager() {
        return ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService("companiondevice"));
    }

    public boolean isVisibleToListener(StatusBarNotification statusBarNotification, int i, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (!managedServiceInfo.enabledAndUserMatches(statusBarNotification.getUserId()) || !isInteractionVisibleToListener(managedServiceInfo, statusBarNotification.getUserId())) {
            return false;
        }
        NotificationListenerFilter notificationListenerFilter = this.mListeners.getNotificationListenerFilter(managedServiceInfo.mKey);
        if (notificationListenerFilter != null) {
            return notificationListenerFilter.isTypeAllowed(i) && notificationListenerFilter.isPackageAllowed(new VersionedPackage(statusBarNotification.getPackageName(), statusBarNotification.getUid()));
        }
        return true;
    }

    public boolean isInteractionVisibleToListener(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        return !isServiceTokenValid(managedServiceInfo.getService()) || managedServiceInfo.isSameUser(i);
    }

    public final boolean isServiceTokenValid(IInterface iInterface) {
        boolean isServiceTokenValidLocked;
        synchronized (this.mNotificationLock) {
            isServiceTokenValidLocked = this.mAssistants.isServiceTokenValidLocked(iInterface);
        }
        return isServiceTokenValidLocked;
    }

    public final boolean isPackageSuspendedForUser(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mPackageManager.isPackageSuspendedForUser(str, UserHandle.getUserId(i));
            } catch (RemoteException unused) {
                throw new SecurityException("Could not talk to package manager service");
            } catch (IllegalArgumentException unused2) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean canUseManagedServices(String str, Integer num, String str2) {
        if (str2 == null) {
            return true;
        }
        try {
            return this.mPackageManager.checkPermission(str2, str, num.intValue()) == 0;
        } catch (RemoteException e) {
            Slog.e("NotificationService", "can't talk to pm", e);
            return true;
        }
    }

    public class TrimCache {
        public StatusBarNotification heavy;
        public StatusBarNotification sbnClone;
        public StatusBarNotification sbnCloneLight;

        public TrimCache(StatusBarNotification statusBarNotification) {
            this.heavy = statusBarNotification;
        }

        public StatusBarNotification ForListener(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            if (NotificationManagerService.this.mListeners.getOnNotificationPostedTrim(managedServiceInfo) == 1) {
                if (this.sbnCloneLight == null) {
                    this.sbnCloneLight = this.heavy.cloneLight();
                }
                return this.sbnCloneLight;
            }
            if (this.sbnClone == null) {
                this.sbnClone = this.heavy.clone();
            }
            return this.sbnClone;
        }
    }

    public final boolean isInCall() {
        int modeInternal;
        return this.mInCallStateOffHook || (modeInternal = this.mAudioManager.getModeInternal()) == 2 || modeInternal == 3;
    }

    public class NotificationAssistants extends ManagedServices {
        public Set mAllowedAdjustments;
        public ComponentName mDefaultFromConfig;
        public final Object mLock;

        @Override // com.android.server.notification.ManagedServices
        public void ensureFilters(ServiceInfo serviceInfo, int i) {
        }

        @Override // com.android.server.notification.ManagedServices
        public String getRequiredPermission() {
            return "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE";
        }

        @Override // com.android.server.notification.ManagedServices
        public void loadDefaultsFromConfig() {
            loadDefaultsFromConfig(true);
        }

        public void loadDefaultsFromConfig(boolean z) {
            ArraySet arraySet = new ArraySet();
            arraySet.addAll(Arrays.asList(this.mContext.getResources().getString(R.string.data_usage_limit_snoozed_body).split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR)));
            for (int i = 0; i < arraySet.size(); i++) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString((String) arraySet.valueAt(i));
                String str = (String) arraySet.valueAt(i);
                if (unflattenFromString != null) {
                    str = unflattenFromString.getPackageName();
                }
                if (!TextUtils.isEmpty(str) && queryPackageForServices(str, 786432, 0).contains(unflattenFromString)) {
                    if (z) {
                        addDefaultComponentOrPackage(unflattenFromString.flattenToString());
                    } else {
                        this.mDefaultFromConfig = unflattenFromString;
                    }
                }
            }
        }

        public ComponentName getDefaultFromConfig() {
            if (this.mDefaultFromConfig == null) {
                loadDefaultsFromConfig(false);
            }
            return this.mDefaultFromConfig;
        }

        @Override // com.android.server.notification.ManagedServices
        public void upgradeUserSet() {
            Iterator it = this.mApproved.keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ArraySet arraySet = (ArraySet) this.mUserSetServices.get(Integer.valueOf(intValue));
                this.mIsUserChanged.put(Integer.valueOf(intValue), Boolean.valueOf(arraySet != null && arraySet.size() > 0));
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void addApprovedList(String str, int i, boolean z, String str2) {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                if (split.length > 1) {
                    Slog.d(this.TAG, "More than one approved assistants");
                    str = split[0];
                }
            }
            super.addApprovedList(str, i, z, str2);
        }

        @Override // com.android.server.notification.ManagedServices
        public void clearApprovedList(String str) {
            try {
                NotificationManagerService.this.getBinderService().setNotificationAssistantAccessGranted((ComponentName) null, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public NotificationAssistants(Context context, Object obj, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager) {
            super(context, obj, userProfiles, iPackageManager);
            this.mLock = new Object();
            this.mAllowedAdjustments = new ArraySet();
            this.mDefaultFromConfig = null;
            int i = 0;
            while (true) {
                String[] strArr = NotificationManagerService.ALLOWED_ADJUSTMENTS;
                if (i >= strArr.length) {
                    return;
                }
                this.mAllowedAdjustments.add(strArr[i]);
                i++;
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public ManagedServices.Config getConfig() {
            ManagedServices.Config config = new ManagedServices.Config();
            config.caption = "notification assistant";
            config.serviceInterface = "android.service.notification.NotificationAssistantService";
            config.xmlTag = "enabled_assistants";
            config.secureSettingName = "enabled_notification_assistant";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_ASSISTANT_SERVICE";
            config.settingsAction = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";
            config.clientLabel = R.string.resolver_turn_on_work_apps;
            return config;
        }

        @Override // com.android.server.notification.ManagedServices
        public IInterface asInterface(IBinder iBinder) {
            return INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        public boolean checkType(IInterface iInterface) {
            return iInterface instanceof INotificationListener;
        }

        @Override // com.android.server.notification.ManagedServices
        public void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            NotificationManagerService.this.mListeners.registerGuestService(managedServiceInfo);
        }

        @Override // com.android.server.notification.ManagedServices
        public void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            NotificationManagerService.this.mListeners.unregisterService(managedServiceInfo.service, managedServiceInfo.userid);
        }

        @Override // com.android.server.notification.ManagedServices
        public void onUserUnlocked(int i) {
            if (this.DEBUG) {
                Slog.d(this.TAG, "onUserUnlocked u=" + i);
            }
            rebindServices(true, i);
        }

        public List getAllowedAssistantAdjustments() {
            ArrayList arrayList;
            synchronized (this.mLock) {
                arrayList = new ArrayList();
                arrayList.addAll(this.mAllowedAdjustments);
            }
            return arrayList;
        }

        public boolean isAdjustmentAllowed(String str) {
            boolean contains;
            synchronized (this.mLock) {
                contains = this.mAllowedAdjustments.contains(str);
            }
            return contains;
        }

        public void onNotificationsSeenLocked(ArrayList arrayList) {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                final ArrayList arrayList2 = new ArrayList(arrayList.size());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    NotificationRecord notificationRecord = (NotificationRecord) it.next();
                    if (NotificationManagerService.this.isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.getUserId())) {
                        arrayList2.add(notificationRecord.getKey());
                    }
                }
                if (!arrayList2.isEmpty()) {
                    NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationAssistants.this.lambda$onNotificationsSeenLocked$0(managedServiceInfo, arrayList2);
                        }
                    });
                }
            }
        }

        public void onPanelRevealed(final int i) {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.NotificationAssistants.this.lambda$onPanelRevealed$1(managedServiceInfo, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPanelRevealed$1(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onPanelRevealed(i);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (panel revealed): " + managedServiceInfo, e);
            }
        }

        public void onPanelHidden() {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.NotificationAssistants.this.lambda$onPanelHidden$2(managedServiceInfo);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPanelHidden$2(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            try {
                managedServiceInfo.service.onPanelHidden();
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (panel hidden): " + managedServiceInfo, e);
            }
        }

        public boolean hasUserSet(int i) {
            Boolean bool = (Boolean) this.mIsUserChanged.get(Integer.valueOf(i));
            return bool != null && bool.booleanValue();
        }

        public void setUserSet(int i, boolean z) {
            this.mIsUserChanged.put(Integer.valueOf(i), Boolean.valueOf(z));
        }

        /* renamed from: notifySeen, reason: merged with bridge method [inline-methods] */
        public final void lambda$onNotificationsSeenLocked$0(ManagedServices.ManagedServiceInfo managedServiceInfo, ArrayList arrayList) {
            try {
                managedServiceInfo.service.onNotificationsSeen(arrayList);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (seen): " + managedServiceInfo, e);
            }
        }

        public final void onNotificationEnqueuedLocked(NotificationRecord notificationRecord) {
            boolean isVerboseLogEnabled = isVerboseLogEnabled();
            if (isVerboseLogEnabled) {
                Slog.v(this.TAG, "onNotificationEnqueuedLocked() called with: r = [" + notificationRecord + "]");
            }
            StatusBarNotification sbn = notificationRecord.getSbn();
            for (ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.getUserId())) {
                    TrimCache trimCache = NotificationManagerService.this.new TrimCache(sbn);
                    INotificationListener iNotificationListener = managedServiceInfo.service;
                    StatusBarNotificationHolder statusBarNotificationHolder = new StatusBarNotificationHolder(trimCache.ForListener(managedServiceInfo));
                    if (isVerboseLogEnabled) {
                        try {
                            Slog.v(this.TAG, "calling onNotificationEnqueuedWithChannel " + statusBarNotificationHolder);
                        } catch (RemoteException e) {
                            Slog.e(this.TAG, "unable to notify assistant (enqueued): " + iNotificationListener, e);
                        }
                    }
                    iNotificationListener.onNotificationEnqueuedWithChannel(statusBarNotificationHolder, notificationRecord.getChannel(), NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo));
                }
            }
        }

        public void notifyAssistantVisibilityChangedLocked(NotificationRecord notificationRecord, final boolean z) {
            final String key = notificationRecord.getSbn().getKey();
            if (NotificationManagerService.DBG) {
                Slog.d(this.TAG, "notifyAssistantVisibilityChangedLocked: " + key);
            }
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantVisibilityChangedLocked$3(key, z, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantVisibilityChangedLocked$3(String str, boolean z, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationVisibilityChanged(str, z);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (visible): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantExpansionChangedLocked(StatusBarNotification statusBarNotification, int i, final boolean z, final boolean z2) {
            final String key = statusBarNotification.getKey();
            notifyAssistantLocked(statusBarNotification, i, true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantExpansionChangedLocked$4(key, z, z2, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantExpansionChangedLocked$4(String str, boolean z, boolean z2, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationExpansionChanged(str, z, z2);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (expanded): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantNotificationDirectReplyLocked(NotificationRecord notificationRecord) {
            final String key = notificationRecord.getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantNotificationDirectReplyLocked$5(key, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantNotificationDirectReplyLocked$5(String str, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationDirectReply(str);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (expanded): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantSuggestedReplySent(StatusBarNotification statusBarNotification, int i, final CharSequence charSequence, final boolean z) {
            final String key = statusBarNotification.getKey();
            notifyAssistantLocked(statusBarNotification, i, true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantSuggestedReplySent$6(key, charSequence, z, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantSuggestedReplySent$6(String str, CharSequence charSequence, boolean z, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onSuggestedReplySent(str, charSequence, z ? 1 : 0);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantActionClicked(NotificationRecord notificationRecord, final Notification.Action action, final boolean z) {
            final String key = notificationRecord.getSbn().getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda7
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantActionClicked$7(key, action, z, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantActionClicked$7(String str, Notification.Action action, boolean z, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onActionClicked(str, action, z ? 1 : 0);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        public final void notifyAssistantSnoozedLocked(NotificationRecord notificationRecord, final String str) {
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda10
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantSnoozedLocked$8(str, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantSnoozedLocked$8(String str, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationSnoozedUntilContext(statusBarNotificationHolder, str);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantNotificationClicked(NotificationRecord notificationRecord) {
            final String key = notificationRecord.getSbn().getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantNotificationClicked$9(key, (INotificationListener) obj, (NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantNotificationClicked$9(String str, INotificationListener iNotificationListener, StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationClicked(str);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify assistant (clicked): " + iNotificationListener, e);
            }
        }

        public void notifyAssistantFeedbackReceived(NotificationRecord notificationRecord, Bundle bundle) {
            StatusBarNotification sbn = notificationRecord.getSbn();
            for (ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.getUserId())) {
                    INotificationListener iNotificationListener = managedServiceInfo.service;
                    try {
                        iNotificationListener.onNotificationFeedbackReceived(sbn.getKey(), NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo), bundle);
                    } catch (RemoteException e) {
                        Slog.e(this.TAG, "unable to notify assistant (feedback): " + iNotificationListener, e);
                    }
                }
            }
        }

        public final void notifyAssistantLocked(StatusBarNotification statusBarNotification, int i, boolean z, final BiConsumer biConsumer) {
            TrimCache trimCache = NotificationManagerService.this.new TrimCache(statusBarNotification);
            boolean isVerboseLogEnabled = isVerboseLogEnabled();
            if (isVerboseLogEnabled) {
                Slog.v(this.TAG, "notifyAssistantLocked() called with: sbn = [" + statusBarNotification + "], sameUserOnly = [" + z + "], callback = [" + biConsumer + "]");
            }
            for (ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                boolean z2 = NotificationManagerService.this.isVisibleToListener(statusBarNotification, i, managedServiceInfo) && (!z || managedServiceInfo.isSameUser(statusBarNotification.getUserId()));
                if (isVerboseLogEnabled) {
                    Slog.v(this.TAG, "notifyAssistantLocked info=" + managedServiceInfo + " snbVisible=" + z2);
                }
                if (z2) {
                    final INotificationListener iNotificationListener = managedServiceInfo.service;
                    final StatusBarNotificationHolder statusBarNotificationHolder = new StatusBarNotificationHolder(trimCache.ForListener(managedServiceInfo));
                    NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            biConsumer.accept(iNotificationListener, statusBarNotificationHolder);
                        }
                    });
                }
            }
        }

        public boolean isEnabled() {
            return !getServices().isEmpty();
        }

        public void resetDefaultAssistantsIfNecessary() {
            Iterator it = this.mUm.getAliveUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                if (!hasUserSet(identifier)) {
                    if (!NotificationManagerService.this.isNASMigrationDone(identifier)) {
                        resetDefaultFromConfig();
                        NotificationManagerService.this.setNASMigrationDone(identifier);
                    }
                    Slog.d(this.TAG, "Approving default notification assistant for user " + identifier);
                    NotificationManagerService.this.setDefaultAssistantForUser(identifier);
                }
            }
        }

        public void resetDefaultFromConfig() {
            clearDefaults();
            loadDefaultsFromConfig();
        }

        public void clearDefaults() {
            this.mDefaultComponents.clear();
            this.mDefaultPackages.clear();
        }

        @Override // com.android.server.notification.ManagedServices
        public void setPackageOrComponentEnabled(String str, int i, boolean z, boolean z2, boolean z3) {
            if (z2) {
                List allowedComponents = getAllowedComponents(i);
                if (!allowedComponents.isEmpty()) {
                    ComponentName componentName = (ComponentName) CollectionUtils.firstOrNull(allowedComponents);
                    if (componentName.flattenToString().equals(str)) {
                        return;
                    } else {
                        NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, false, z3);
                    }
                }
            }
            super.setPackageOrComponentEnabled(str, i, z, z2, z3);
        }

        public final boolean isVerboseLogEnabled() {
            return Log.isLoggable("notification_assistant", 2);
        }
    }

    public final void notifyListenersPostedAndLogLocked(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, final PostNotificationTracker postNotificationTracker, final NotificationRecordLogger.NotificationReported notificationReported) {
        final List prepareNotifyPostedLocked = this.mListeners.prepareNotifyPostedLocked(notificationRecord, notificationRecord2, true);
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerService.this.lambda$notifyListenersPostedAndLogLocked$17(prepareNotifyPostedLocked, postNotificationTracker, notificationReported);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyListenersPostedAndLogLocked$17(List list, PostNotificationTracker postNotificationTracker, NotificationRecordLogger.NotificationReported notificationReported) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        long finish = postNotificationTracker.finish();
        if (notificationReported != null) {
            notificationReported.post_duration_millis = finish;
            this.mNotificationRecordLogger.logNotificationPosted(notificationReported);
        }
    }

    public class NotificationListeners extends ManagedServices {
        public final boolean mIsHeadlessSystemUserMode;
        public final ArraySet mLightTrimListeners;
        public final ArrayMap mRequestedNotificationListeners;

        @Override // com.android.server.notification.ManagedServices
        public int getBindFlags() {
            return 83886337;
        }

        @Override // com.android.server.notification.ManagedServices
        public String getRequiredPermission() {
            return null;
        }

        @Override // com.android.server.notification.ManagedServices
        public boolean shouldReflectToSettings() {
            return true;
        }

        public NotificationListeners(NotificationManagerService notificationManagerService, Context context, Object obj, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager) {
            this(context, obj, userProfiles, iPackageManager, UserManager.isHeadlessSystemUserMode());
        }

        public NotificationListeners(Context context, Object obj, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager, boolean z) {
            super(context, obj, userProfiles, iPackageManager);
            this.mLightTrimListeners = new ArraySet();
            this.mRequestedNotificationListeners = new ArrayMap();
            this.mIsHeadlessSystemUserMode = z;
        }

        @Override // com.android.server.notification.ManagedServices
        public void setPackageOrComponentEnabled(String str, int i, boolean z, boolean z2, boolean z3) {
            super.setPackageOrComponentEnabled(str, i, z, z2, z3);
            this.mContext.sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED").addFlags(1073741824), UserHandle.of(i), null);
        }

        @Override // com.android.server.notification.ManagedServices
        public void loadDefaultsFromConfig() {
            String str = this.mContext.getResources().getString(R.string.date_picker_decrement_year_button) + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + this.mContext.getResources().getString(R.string.ext_media_checking_notification_message);
            if (str != null) {
                String[] split = str.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                for (int i = 0; i < split.length; i++) {
                    if (!TextUtils.isEmpty(split[i])) {
                        ArraySet queryPackageForServices = queryPackageForServices(split[i], this.mIsHeadlessSystemUserMode ? 4980736 : 786432, 0);
                        for (int i2 = 0; i2 < queryPackageForServices.size(); i2++) {
                            addDefaultComponentOrPackage(((ComponentName) queryPackageForServices.valueAt(i2)).flattenToString());
                        }
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public ManagedServices.Config getConfig() {
            ManagedServices.Config config = new ManagedServices.Config();
            config.caption = "notification listener";
            config.serviceInterface = "android.service.notification.NotificationListenerService";
            config.xmlTag = "enabled_listeners";
            config.secureSettingName = "enabled_notification_listeners";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
            config.settingsAction = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
            config.clientLabel = R.string.resolver_personal_tab;
            return config;
        }

        @Override // com.android.server.notification.ManagedServices
        public IInterface asInterface(IBinder iBinder) {
            return INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        public boolean checkType(IInterface iInterface) {
            return iInterface instanceof INotificationListener;
        }

        @Override // com.android.server.notification.ManagedServices
        public void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            NotificationRankingUpdate makeRankingUpdateLocked;
            INotificationListener iNotificationListener = managedServiceInfo.service;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                makeRankingUpdateLocked = NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, true);
            }
            try {
                iNotificationListener.onListenerConnected(makeRankingUpdateLocked);
                if (managedServiceInfo.isSystem) {
                    try {
                        ApplicationInfo applicationInfo = NotificationManagerService.this.getContext().getPackageManager().getApplicationInfo(managedServiceInfo.component.getPackageName(), 128);
                        if (applicationInfo != null && applicationInfo.metaData != null) {
                            Log.d(this.TAG, "Trim appInfo.metaData = " + applicationInfo.metaData);
                            if (applicationInfo.metaData.getInt("com.samsung.android.notification.listener.trim", 0) == 1) {
                                setOnNotificationPostedTrimLocked(managedServiceInfo, 1);
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RemoteException unused) {
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, false);
            if (NotificationManagerService.this.removeDisabledHints(managedServiceInfo)) {
                NotificationManagerService.this.updateListenerHintsLocked();
                NotificationManagerService.this.updateEffectsSuppressorLocked();
            }
            this.mLightTrimListeners.remove(managedServiceInfo);
        }

        @Override // com.android.server.notification.ManagedServices
        public void onUserRemoved(int i) {
            super.onUserRemoved(i);
            synchronized (this.mRequestedNotificationListeners) {
                for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                    if (((Integer) ((Pair) this.mRequestedNotificationListeners.keyAt(size)).second).intValue() == i) {
                        this.mRequestedNotificationListeners.removeAt(size);
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
            super.onPackagesChanged(z, strArr, iArr);
            synchronized (this.mRequestedNotificationListeners) {
                if (z) {
                    for (int i = 0; i < strArr.length; i++) {
                        String str = strArr[i];
                        int userId = UserHandle.getUserId(iArr[i]);
                        for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                            Pair pair = (Pair) this.mRequestedNotificationListeners.keyAt(size);
                            if (((Integer) pair.second).intValue() == userId && ((ComponentName) pair.first).getPackageName().equals(str)) {
                                this.mRequestedNotificationListeners.removeAt(size);
                            }
                        }
                    }
                }
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    String str2 = strArr[i2];
                    for (int size2 = this.mRequestedNotificationListeners.size() - 1; size2 >= 0; size2--) {
                        ((NotificationListenerFilter) this.mRequestedNotificationListeners.valueAt(size2)).removePackage(new VersionedPackage(str2, iArr[i2]));
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void readExtraTag(String str, TypedXmlPullParser typedXmlPullParser) {
            if ("request_listeners".equals(str)) {
                int depth = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    if ("listener".equals(typedXmlPullParser.getName())) {
                        int readIntAttribute = XmlUtils.readIntAttribute(typedXmlPullParser, "user");
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(XmlUtils.readStringAttribute(typedXmlPullParser, "component"));
                        ArraySet arraySet = new ArraySet();
                        int depth2 = typedXmlPullParser.getDepth();
                        int i = 15;
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                            if ("allowed".equals(typedXmlPullParser.getName())) {
                                i = XmlUtils.readIntAttribute(typedXmlPullParser, "types");
                            } else if ("disallowed".equals(typedXmlPullParser.getName())) {
                                String readStringAttribute = XmlUtils.readStringAttribute(typedXmlPullParser, "pkg");
                                int readIntAttribute2 = XmlUtils.readIntAttribute(typedXmlPullParser, "uid");
                                if (!TextUtils.isEmpty(readStringAttribute)) {
                                    arraySet.add(new VersionedPackage(readStringAttribute, readIntAttribute2));
                                }
                            }
                        }
                        NotificationListenerFilter notificationListenerFilter = new NotificationListenerFilter(i, arraySet);
                        synchronized (this.mRequestedNotificationListeners) {
                            this.mRequestedNotificationListeners.put(Pair.create(unflattenFromString, Integer.valueOf(readIntAttribute)), notificationListenerFilter);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void writeExtraXmlTags(TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.startTag((String) null, "request_listeners");
            synchronized (this.mRequestedNotificationListeners) {
                for (Pair pair : this.mRequestedNotificationListeners.keySet()) {
                    NotificationListenerFilter notificationListenerFilter = (NotificationListenerFilter) this.mRequestedNotificationListeners.get(pair);
                    typedXmlSerializer.startTag((String) null, "listener");
                    XmlUtils.writeStringAttribute(typedXmlSerializer, "component", ((ComponentName) pair.first).flattenToString());
                    XmlUtils.writeIntAttribute(typedXmlSerializer, "user", ((Integer) pair.second).intValue());
                    typedXmlSerializer.startTag((String) null, "allowed");
                    XmlUtils.writeIntAttribute(typedXmlSerializer, "types", notificationListenerFilter.getTypes());
                    typedXmlSerializer.endTag((String) null, "allowed");
                    Iterator it = notificationListenerFilter.getDisallowedPackages().iterator();
                    while (it.hasNext()) {
                        VersionedPackage versionedPackage = (VersionedPackage) it.next();
                        if (!TextUtils.isEmpty(versionedPackage.getPackageName())) {
                            typedXmlSerializer.startTag((String) null, "disallowed");
                            XmlUtils.writeStringAttribute(typedXmlSerializer, "pkg", versionedPackage.getPackageName());
                            XmlUtils.writeIntAttribute(typedXmlSerializer, "uid", versionedPackage.getVersionCode());
                            typedXmlSerializer.endTag((String) null, "disallowed");
                        }
                    }
                    typedXmlSerializer.endTag((String) null, "listener");
                }
            }
            typedXmlSerializer.endTag((String) null, "request_listeners");
        }

        public NotificationListenerFilter getNotificationListenerFilter(Pair pair) {
            NotificationListenerFilter notificationListenerFilter;
            synchronized (this.mRequestedNotificationListeners) {
                notificationListenerFilter = (NotificationListenerFilter) this.mRequestedNotificationListeners.get(pair);
            }
            return notificationListenerFilter;
        }

        public void setNotificationListenerFilter(Pair pair, NotificationListenerFilter notificationListenerFilter) {
            synchronized (this.mRequestedNotificationListeners) {
                this.mRequestedNotificationListeners.put(pair, notificationListenerFilter);
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public void ensureFilters(ServiceInfo serviceInfo, int i) {
            int typesFromStringList;
            String obj;
            Pair create = Pair.create(serviceInfo.getComponentName(), Integer.valueOf(i));
            synchronized (this.mRequestedNotificationListeners) {
                NotificationListenerFilter notificationListenerFilter = (NotificationListenerFilter) this.mRequestedNotificationListeners.get(create);
                Bundle bundle = serviceInfo.metaData;
                if (bundle != null) {
                    if (notificationListenerFilter == null && bundle.containsKey("android.service.notification.default_filter_types") && (obj = serviceInfo.metaData.get("android.service.notification.default_filter_types").toString()) != null) {
                        this.mRequestedNotificationListeners.put(create, new NotificationListenerFilter(getTypesFromStringList(obj), new ArraySet()));
                    }
                    if (serviceInfo.metaData.containsKey("android.service.notification.disabled_filter_types") && (typesFromStringList = getTypesFromStringList(serviceInfo.metaData.get("android.service.notification.disabled_filter_types").toString())) != 0) {
                        NotificationListenerFilter notificationListenerFilter2 = (NotificationListenerFilter) this.mRequestedNotificationListeners.getOrDefault(create, new NotificationListenerFilter());
                        notificationListenerFilter2.setTypes((~typesFromStringList) & notificationListenerFilter2.getTypes());
                        this.mRequestedNotificationListeners.put(create, notificationListenerFilter2);
                    }
                }
            }
        }

        public final int getTypesFromStringList(String str) {
            if (str == null) {
                return 0;
            }
            int i = 0;
            for (String str2 : str.split("\\|")) {
                if (!TextUtils.isEmpty(str2)) {
                    if (str2.equalsIgnoreCase("ONGOING")) {
                        i |= 8;
                    } else if (str2.equalsIgnoreCase("CONVERSATIONS")) {
                        i |= 1;
                    } else if (str2.equalsIgnoreCase("SILENT")) {
                        i |= 4;
                    } else if (str2.equalsIgnoreCase("ALERTING")) {
                        i |= 2;
                    } else {
                        try {
                            i |= Integer.parseInt(str2);
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
            return i;
        }

        public void setOnNotificationPostedTrimLocked(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            if (i == 1) {
                this.mLightTrimListeners.add(managedServiceInfo);
            } else {
                this.mLightTrimListeners.remove(managedServiceInfo);
            }
        }

        public int getOnNotificationPostedTrim(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            return this.mLightTrimListeners.contains(managedServiceInfo) ? 1 : 0;
        }

        public void onStatusBarIconsBehaviorChanged(final boolean z) {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.NotificationListeners.this.lambda$onStatusBarIconsBehaviorChanged$0(managedServiceInfo, z);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusBarIconsBehaviorChanged$0(ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
            try {
                managedServiceInfo.service.onStatusBarIconsBehaviorChanged(z);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify listener (hideSilentStatusIcons): " + managedServiceInfo, e);
            }
        }

        public void notifyPostedLocked(NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
            notifyPostedLocked(notificationRecord, notificationRecord2, true);
        }

        public void notifyPostedLocked(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, boolean z) {
            Iterator it = prepareNotifyPostedLocked(notificationRecord, notificationRecord2, z).iterator();
            while (it.hasNext()) {
                NotificationManagerService.this.mHandler.post((Runnable) it.next());
            }
        }

        public List prepareNotifyPostedLocked(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, boolean z) {
            if (NotificationManagerService.this.isInLockDownMode(notificationRecord.getUser().getIdentifier())) {
                return new ArrayList();
            }
            ArrayList arrayList = new ArrayList();
            try {
                StatusBarNotification sbn = notificationRecord.getSbn();
                StatusBarNotification sbn2 = notificationRecord2 != null ? notificationRecord2.getSbn() : null;
                TrimCache trimCache = NotificationManagerService.this.new TrimCache(sbn);
                for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                    boolean isVisibleToListener = NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo);
                    boolean z2 = sbn2 != null && NotificationManagerService.this.isVisibleToListener(sbn2, notificationRecord2.getNotificationType(), managedServiceInfo);
                    if (z2 || isVisibleToListener) {
                        if (!notificationRecord.isHidden() || managedServiceInfo.targetSdkVersion >= 28) {
                            if (z || managedServiceInfo.targetSdkVersion < 28) {
                                final NotificationRankingUpdate makeRankingUpdateLocked = NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                                if (z2 && !isVisibleToListener) {
                                    final StatusBarNotification cloneLight = sbn2.cloneLight();
                                    arrayList.add(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            NotificationManagerService.NotificationListeners.this.lambda$prepareNotifyPostedLocked$1(managedServiceInfo, cloneLight, makeRankingUpdateLocked);
                                        }
                                    });
                                } else {
                                    int i = managedServiceInfo.userid;
                                    int i2 = i == -1 ? 0 : i;
                                    try {
                                        NotificationManagerService.this.updateUriPermissions(notificationRecord, notificationRecord2, managedServiceInfo.component.getPackageName(), i2);
                                        NotificationManagerService.this.mPackageManagerInternal.grantImplicitAccess(i2, null, UserHandle.getAppId(managedServiceInfo.uid), sbn.getUid(), false, false);
                                        final StatusBarNotification ForListener = trimCache.ForListener(managedServiceInfo);
                                        arrayList.add(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                NotificationManagerService.NotificationListeners.this.lambda$prepareNotifyPostedLocked$2(managedServiceInfo, ForListener, makeRankingUpdateLocked);
                                            }
                                        });
                                    } catch (Exception e) {
                                        e = e;
                                        Slog.e(this.TAG, "Could not notify listeners for " + notificationRecord.getKey(), e);
                                        return arrayList;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$prepareNotifyPostedLocked$1(ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) {
            lambda$notifyRemovedLocked$3(managedServiceInfo, statusBarNotification, notificationRankingUpdate, null, 6);
        }

        public final void updateUriPermissionsForActiveNotificationsLocked(ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
            try {
                Iterator it = NotificationManagerService.this.mNotificationList.iterator();
                while (it.hasNext()) {
                    NotificationRecord notificationRecord = (NotificationRecord) it.next();
                    if (!z || NotificationManagerService.this.isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo)) {
                        if (!notificationRecord.isHidden() || managedServiceInfo.targetSdkVersion >= 28) {
                            int i = managedServiceInfo.userid;
                            if (i == -1) {
                                i = 0;
                            }
                            int i2 = i;
                            if (z) {
                                NotificationManagerService.this.updateUriPermissions(notificationRecord, null, managedServiceInfo.component.getPackageName(), i2);
                            } else {
                                NotificationManagerService.this.updateUriPermissions(null, notificationRecord, managedServiceInfo.component.getPackageName(), i2, true);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Could not ");
                sb.append(z ? "grant" : "revoke");
                sb.append(" Uri permissions to ");
                sb.append(managedServiceInfo.component);
                Slog.e(str, sb.toString(), e);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
        
            if (r3.targetSdkVersion < 28) goto L34;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void notifyRemovedLocked(final NotificationRecord notificationRecord, final int i, NotificationStats notificationStats) {
            if (NotificationManagerService.this.isInLockDownMode(notificationRecord.getUser().getIdentifier())) {
                return;
            }
            StatusBarNotification sbn = notificationRecord.getSbn();
            final StatusBarNotification cloneLight = sbn.cloneLight();
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo) && (!notificationRecord.isHidden() || i == 14 || managedServiceInfo.targetSdkVersion >= 28)) {
                    final NotificationStats notificationStats2 = NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(managedServiceInfo.service) ? notificationStats : null;
                    final NotificationRankingUpdate makeRankingUpdateLocked = NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                    NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationListeners.this.lambda$notifyRemovedLocked$3(managedServiceInfo, cloneLight, makeRankingUpdateLocked, notificationStats2, i);
                        }
                    });
                }
            }
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.NotificationListeners.this.lambda$notifyRemovedLocked$4(notificationRecord);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRemovedLocked$4(NotificationRecord notificationRecord) {
            NotificationManagerService.this.updateUriPermissions(null, notificationRecord, null, 0);
        }

        public void notifyRankingUpdateLocked(List list) {
            boolean z;
            boolean z2 = list != null && list.size() > 0;
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, ActivityManager.getCurrentUser())) {
                    if (z2 && managedServiceInfo.targetSdkVersion >= 28) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            NotificationRecord notificationRecord = (NotificationRecord) it.next();
                            if (NotificationManagerService.this.isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    if (z || !z2) {
                        final NotificationRankingUpdate makeRankingUpdateLocked = NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                        NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                NotificationManagerService.NotificationListeners.this.lambda$notifyRankingUpdateLocked$5(managedServiceInfo, makeRankingUpdateLocked);
                            }
                        });
                    }
                }
            }
        }

        public void notifyListenerHintsChangedLocked(final int i) {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, ActivityManager.getCurrentUser())) {
                    NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationListeners.this.lambda$notifyListenerHintsChangedLocked$6(managedServiceInfo, i);
                        }
                    });
                }
            }
        }

        public void notifyHiddenLocked(List list) {
            if (list == null || list.size() == 0) {
                return;
            }
            notifyRankingUpdateLocked(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) list.get(i);
                NotificationManagerService.this.mListeners.notifyRemovedLocked(notificationRecord, 14, notificationRecord.getStats());
            }
        }

        public void notifyUnhiddenLocked(List list) {
            if (list == null || list.size() == 0) {
                return;
            }
            notifyRankingUpdateLocked(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationRecord notificationRecord = (NotificationRecord) list.get(i);
                notifyPostedLocked(notificationRecord, notificationRecord, false);
            }
        }

        public void notifyInterruptionFilterChanged(final int i) {
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, ActivityManager.getCurrentUser())) {
                    NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationListeners.this.lambda$notifyInterruptionFilterChanged$7(managedServiceInfo, i);
                        }
                    });
                }
            }
        }

        public void notifyNotificationChannelChanged(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
            if (notificationChannel == null) {
                return;
            }
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.enabledAndUserMatches(UserHandle.getCallingUserId()) && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, UserHandle.getCallingUserId())) {
                    BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationListeners.this.lambda$notifyNotificationChannelChanged$8(managedServiceInfo, str, userHandle, notificationChannel, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyNotificationChannelChanged$8(ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            if (managedServiceInfo.isSystem || NotificationManagerService.this.hasCompanionDevice(managedServiceInfo) || NotificationManagerService.this.isServiceTokenValid(managedServiceInfo.service)) {
                notifyNotificationChannelChanged(managedServiceInfo, str, userHandle, notificationChannel, i);
            }
        }

        public void notifyNotificationChannelGroupChanged(final String str, final UserHandle userHandle, final NotificationChannelGroup notificationChannelGroup, final int i) {
            if (notificationChannelGroup == null) {
                return;
            }
            for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.enabledAndUserMatches(UserHandle.getCallingUserId()) && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, UserHandle.getCallingUserId())) {
                    BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.NotificationListeners.this.lambda$notifyNotificationChannelGroupChanged$9(managedServiceInfo, str, userHandle, notificationChannelGroup, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyNotificationChannelGroupChanged$9(ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
            if (managedServiceInfo.isSystem() || NotificationManagerService.this.hasCompanionDevice(managedServiceInfo)) {
                notifyNotificationChannelGroupChanged(managedServiceInfo, str, userHandle, notificationChannelGroup, i);
            }
        }

        /* renamed from: notifyPosted, reason: merged with bridge method [inline-methods] */
        public final void lambda$prepareNotifyPostedLocked$2(ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) {
            try {
                managedServiceInfo.service.onNotificationPosted(new StatusBarNotificationHolder(statusBarNotification), notificationRankingUpdate);
            } catch (DeadObjectException e) {
                Slog.wtf(this.TAG, "unable to notify listener (posted): " + managedServiceInfo, e);
            } catch (RemoteException e2) {
                Slog.e(this.TAG, "unable to notify listener (posted): " + managedServiceInfo, e2);
            }
        }

        /* renamed from: notifyRemoved, reason: merged with bridge method [inline-methods] */
        public final void lambda$notifyRemovedLocked$3(ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) {
            INotificationListener iNotificationListener = managedServiceInfo.service;
            StatusBarNotificationHolder statusBarNotificationHolder = new StatusBarNotificationHolder(statusBarNotification);
            try {
                if (!CompatChanges.isChangeEnabled(175319604L, managedServiceInfo.uid) && (i == 20 || i == 21)) {
                    i = 17;
                }
                if (!CompatChanges.isChangeEnabled(195579280L, managedServiceInfo.uid) && i == 22) {
                    i = 10;
                }
                iNotificationListener.onNotificationRemoved(statusBarNotificationHolder, notificationRankingUpdate, notificationStats, i);
            } catch (DeadObjectException e) {
                Slog.wtf(this.TAG, "unable to notify listener (removed): " + managedServiceInfo, e);
            } catch (RemoteException e2) {
                Slog.e(this.TAG, "unable to notify listener (removed): " + managedServiceInfo, e2);
            }
        }

        /* renamed from: notifyRankingUpdate, reason: merged with bridge method [inline-methods] */
        public final void lambda$notifyRankingUpdateLocked$5(ManagedServices.ManagedServiceInfo managedServiceInfo, NotificationRankingUpdate notificationRankingUpdate) {
            try {
                managedServiceInfo.service.onNotificationRankingUpdate(notificationRankingUpdate);
            } catch (DeadObjectException e) {
                Slog.wtf(this.TAG, "unable to notify listener (ranking update): " + managedServiceInfo, e);
            } catch (RemoteException e2) {
                Slog.e(this.TAG, "unable to notify listener (ranking update): " + managedServiceInfo, e2);
            }
        }

        /* renamed from: notifyListenerHintsChanged, reason: merged with bridge method [inline-methods] */
        public final void lambda$notifyListenerHintsChangedLocked$6(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onListenerHintsChanged(i);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify listener (listener hints): " + managedServiceInfo, e);
            }
        }

        /* renamed from: notifyInterruptionFilterChanged, reason: merged with bridge method [inline-methods] */
        public final void lambda$notifyInterruptionFilterChanged$7(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onInterruptionFilterChanged(i);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify listener (interruption filter): " + managedServiceInfo, e);
            }
        }

        public void notifyNotificationChannelChanged(ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            try {
                managedServiceInfo.service.onNotificationChannelModification(str, userHandle, notificationChannel, i);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify listener (channel changed): " + managedServiceInfo, e);
            }
        }

        public final void notifyNotificationChannelGroupChanged(ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
            try {
                managedServiceInfo.getService().onNotificationChannelGroupModification(str, userHandle, notificationChannelGroup, i);
            } catch (RemoteException e) {
                Slog.e(this.TAG, "unable to notify listener (channel group changed): " + managedServiceInfo, e);
            }
        }

        public boolean isListenerPackage(String str) {
            if (str == null) {
                return false;
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                Iterator it = getServices().iterator();
                while (it.hasNext()) {
                    if (str.equals(((ManagedServices.ManagedServiceInfo) it.next()).component.getPackageName())) {
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean isSystemListenerPackage(String str) {
            if (str == null) {
                return false;
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                for (ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                    if (managedServiceInfo.isSystem && str.equals(managedServiceInfo.component.getPackageName())) {
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean hasAllowedListener(String str, int i) {
            if (str == null) {
                return false;
            }
            List allowedComponents = getAllowedComponents(i);
            for (int i2 = 0; i2 < allowedComponents.size(); i2++) {
                if (((ComponentName) allowedComponents.get(i2)).getPackageName().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public void removeEdgeNotification(final String str, final int i, final Bundle bundle, int i2) {
            synchronized (NotificationManagerService.this.mNotificationList) {
                for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                    if (NotificationManagerService.EDGE_NOTIFICATION_COMPONENT.equals(managedServiceInfo.component) && managedServiceInfo.userid == i2) {
                        NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.NotificationListeners.1
                            @Override // java.lang.Runnable
                            public void run() {
                                INotificationListener iNotificationListener = managedServiceInfo.service;
                                try {
                                    iNotificationListener.onEdgeNotificationRemoved(str, i, bundle);
                                } catch (RemoteException e) {
                                    Log.e(NotificationListeners.this.TAG, "unable to notify listener (posted): " + iNotificationListener, e);
                                }
                            }
                        });
                    }
                }
            }
        }

        public void enqueueEdgeNotification(final String str, final int i, final Bundle bundle, int i2) {
            synchronized (NotificationManagerService.this.mNotificationList) {
                for (final ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                    if (NotificationManagerService.EDGE_NOTIFICATION_COMPONENT.equals(managedServiceInfo.component) && managedServiceInfo.userid == i2) {
                        NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.NotificationListeners.2
                            @Override // java.lang.Runnable
                            public void run() {
                                INotificationListener iNotificationListener = managedServiceInfo.service;
                                try {
                                    iNotificationListener.onEdgeNotificationPosted(str, i, bundle);
                                } catch (RemoteException e) {
                                    Log.e(NotificationListeners.this.TAG, "unable to notify listener (posted): " + iNotificationListener, e);
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public class RoleObserver implements OnRoleHoldersChangedListener {
        public final Executor mExecutor;
        public final Looper mMainLooper;
        public ArrayMap mNonBlockableDefaultApps;
        public final IPackageManager mPm;
        public final RoleManager mRm;
        public volatile ArraySet mTrampolineExemptUids = new ArraySet();

        public RoleObserver(Context context, RoleManager roleManager, IPackageManager iPackageManager, Looper looper) {
            this.mRm = roleManager;
            this.mPm = iPackageManager;
            this.mExecutor = context.getMainExecutor();
            this.mMainLooper = looper;
        }

        public void init() {
            List userHandles = NotificationManagerService.this.mUm.getUserHandles(true);
            this.mNonBlockableDefaultApps = new ArrayMap();
            int i = 0;
            while (true) {
                String[] strArr = NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES;
                if (i < strArr.length) {
                    ArrayMap arrayMap = new ArrayMap();
                    this.mNonBlockableDefaultApps.put(strArr[i], arrayMap);
                    for (int i2 = 0; i2 < userHandles.size(); i2++) {
                        Integer valueOf = Integer.valueOf(((UserHandle) userHandles.get(i2)).getIdentifier());
                        ArraySet arraySet = new ArraySet(this.mRm.getRoleHoldersAsUser(NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES[i], UserHandle.of(valueOf.intValue())));
                        ArraySet arraySet2 = new ArraySet();
                        Iterator it = arraySet.iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            arraySet2.add(new Pair(str, Integer.valueOf(getUidForPackage(str, valueOf.intValue()))));
                        }
                        arrayMap.put(valueOf, arraySet);
                        NotificationManagerService.this.mPreferencesHelper.updateDefaultApps(valueOf.intValue(), null, arraySet2);
                    }
                    i++;
                } else {
                    updateTrampolineExemptUidsForUsers((UserHandle[]) userHandles.toArray(new UserHandle[0]));
                    this.mRm.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, UserHandle.ALL);
                    return;
                }
            }
        }

        public boolean isApprovedPackageForRoleForUser(String str, String str2, int i) {
            return ((ArraySet) ((ArrayMap) this.mNonBlockableDefaultApps.get(str)).get(Integer.valueOf(i))).contains(str2);
        }

        public boolean isUidExemptFromTrampolineRestrictions(int i) {
            return this.mTrampolineExemptUids.contains(Integer.valueOf(i));
        }

        public void onRoleHoldersChanged(String str, UserHandle userHandle) {
            onRoleHoldersChangedForNonBlockableDefaultApps(str, userHandle);
            onRoleHoldersChangedForTrampolines(str, userHandle);
        }

        public final void onRoleHoldersChangedForNonBlockableDefaultApps(String str, UserHandle userHandle) {
            boolean z = false;
            int i = 0;
            while (true) {
                String[] strArr = NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES;
                if (i >= strArr.length) {
                    break;
                }
                if (strArr[i].equals(str)) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                ArraySet arraySet = new ArraySet(this.mRm.getRoleHoldersAsUser(str, userHandle));
                ArrayMap arrayMap = (ArrayMap) this.mNonBlockableDefaultApps.getOrDefault(str, new ArrayMap());
                ArraySet arraySet2 = (ArraySet) arrayMap.getOrDefault(Integer.valueOf(userHandle.getIdentifier()), new ArraySet());
                ArraySet arraySet3 = new ArraySet();
                ArraySet arraySet4 = new ArraySet();
                Iterator it = arraySet2.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (!arraySet.contains(str2)) {
                        arraySet3.add(str2);
                    }
                }
                Iterator it2 = arraySet.iterator();
                while (it2.hasNext()) {
                    String str3 = (String) it2.next();
                    if (!arraySet2.contains(str3)) {
                        arraySet4.add(new Pair(str3, Integer.valueOf(getUidForPackage(str3, userHandle.getIdentifier()))));
                    }
                }
                arrayMap.put(Integer.valueOf(userHandle.getIdentifier()), arraySet);
                this.mNonBlockableDefaultApps.put(str, arrayMap);
                NotificationManagerService.this.mPreferencesHelper.updateDefaultApps(userHandle.getIdentifier(), arraySet3, arraySet4);
            }
        }

        public final void onRoleHoldersChangedForTrampolines(String str, UserHandle userHandle) {
            if ("android.app.role.BROWSER".equals(str)) {
                updateTrampolineExemptUidsForUsers(userHandle);
            }
        }

        public final void updateTrampolineExemptUidsForUsers(UserHandle... userHandleArr) {
            Preconditions.checkState(this.mMainLooper.isCurrentThread());
            ArraySet arraySet = this.mTrampolineExemptUids;
            ArraySet arraySet2 = new ArraySet();
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                int intValue = ((Integer) arraySet.valueAt(i)).intValue();
                if (!ArrayUtils.contains(userHandleArr, UserHandle.of(UserHandle.getUserId(intValue)))) {
                    arraySet2.add(Integer.valueOf(intValue));
                }
            }
            for (UserHandle userHandle : userHandleArr) {
                for (String str : this.mRm.getRoleHoldersAsUser("android.app.role.BROWSER", userHandle)) {
                    int uidForPackage = getUidForPackage(str, userHandle.getIdentifier());
                    if (uidForPackage != -1) {
                        arraySet2.add(Integer.valueOf(uidForPackage));
                    } else {
                        Slog.e("NotificationService", "Bad uid (-1) for browser package " + str);
                    }
                }
            }
            this.mTrampolineExemptUids = arraySet2;
        }

        public final int getUidForPackage(String str, int i) {
            try {
                return this.mPm.getPackageUid(str, 131072L, i);
            } catch (RemoteException unused) {
                Slog.e("NotificationService", "role manager has bad default " + str + " " + i);
                return -1;
            }
        }
    }

    public final class DumpFilter {
        public String pkgFilter;
        public boolean rvStats;
        public long since;
        public boolean stats;
        public boolean zen;
        public boolean filtered = false;
        public boolean redact = true;
        public boolean proto = false;
        public boolean criticalPriority = false;
        public boolean normalPriority = false;

        public static DumpFilter parseFromArguments(String[] strArr) {
            DumpFilter dumpFilter = new DumpFilter();
            int i = 0;
            while (i < strArr.length) {
                String str = strArr[i];
                if ("--proto".equals(str)) {
                    dumpFilter.proto = true;
                } else if ("--noredact".equals(str) || "--reveal".equals(str)) {
                    dumpFilter.redact = false;
                } else if (KnoxAnalyticsDataConverter.PAYLOAD.equals(str) || "pkg".equals(str) || "--package".equals(str)) {
                    if (i < strArr.length - 1) {
                        i++;
                        String lowerCase = strArr[i].trim().toLowerCase();
                        dumpFilter.pkgFilter = lowerCase;
                        if (lowerCase.isEmpty()) {
                            dumpFilter.pkgFilter = null;
                        } else {
                            dumpFilter.filtered = true;
                        }
                    }
                } else if ("--zen".equals(str) || "zen".equals(str)) {
                    dumpFilter.filtered = true;
                    dumpFilter.zen = true;
                } else if ("--stats".equals(str)) {
                    dumpFilter.stats = true;
                    if (i < strArr.length - 1) {
                        i++;
                        dumpFilter.since = Long.parseLong(strArr[i]);
                    } else {
                        dumpFilter.since = 0L;
                    }
                } else if ("--remote-view-stats".equals(str)) {
                    dumpFilter.rvStats = true;
                    if (i < strArr.length - 1) {
                        i++;
                        dumpFilter.since = Long.parseLong(strArr[i]);
                    } else {
                        dumpFilter.since = 0L;
                    }
                } else if ("--dump-priority".equals(str) && i < strArr.length - 1) {
                    i++;
                    String str2 = strArr[i];
                    str2.hashCode();
                    if (str2.equals("NORMAL")) {
                        dumpFilter.normalPriority = true;
                    } else if (str2.equals("CRITICAL")) {
                        dumpFilter.criticalPriority = true;
                    }
                }
                i++;
            }
            return dumpFilter;
        }

        public boolean matches(StatusBarNotification statusBarNotification) {
            if (this.filtered && !this.zen) {
                return statusBarNotification != null && (matches(statusBarNotification.getPackageName()) || matches(statusBarNotification.getOpPkg()));
            }
            return true;
        }

        public boolean matches(ComponentName componentName) {
            if (this.filtered && !this.zen) {
                return componentName != null && matches(componentName.getPackageName());
            }
            return true;
        }

        public boolean matches(String str) {
            if (this.filtered && !this.zen) {
                return str != null && str.toLowerCase().contains(this.pkgFilter);
            }
            return true;
        }

        public String toString() {
            if (this.stats) {
                return "stats";
            }
            if (this.zen) {
                return "zen";
            }
            return '\'' + this.pkgFilter + '\'';
        }
    }

    public void resetAssistantUserSet(int i) {
        checkCallerIsSystemOrShell();
        this.mAssistants.setUserSet(i, false);
        handleSavePolicyFile();
    }

    public ComponentName getApprovedAssistant(int i) {
        checkCallerIsSystemOrShell();
        return (ComponentName) CollectionUtils.firstOrNull(this.mAssistants.getAllowedComponents(i));
    }

    public final class StatusBarNotificationHolder extends IStatusBarNotificationHolder.Stub {
        public StatusBarNotification mValue;

        public StatusBarNotificationHolder(StatusBarNotification statusBarNotification) {
            this.mValue = statusBarNotification;
        }

        public StatusBarNotification get() {
            StatusBarNotification statusBarNotification = this.mValue;
            this.mValue = null;
            return statusBarNotification;
        }
    }

    public final void writeSecureNotificationsPolicy(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startTag((String) null, "allow-secure-notifications-on-lockscreen");
        typedXmlSerializer.attributeBoolean((String) null, "value", this.mLockScreenAllowSecureNotifications);
        typedXmlSerializer.endTag((String) null, "allow-secure-notifications-on-lockscreen");
    }

    public Notification createReviewPermissionsNotification() {
        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS_FOR_REVIEW");
        Intent intent2 = new Intent("REVIEW_NOTIF_ACTION_REMIND");
        Intent intent3 = new Intent("REVIEW_NOTIF_ACTION_DISMISS");
        Intent intent4 = new Intent("REVIEW_NOTIF_ACTION_CANCELED");
        Notification.Action build = new Notification.Action.Builder((Icon) null, getContext().getResources().getString(17042458), PendingIntent.getBroadcast(getContext(), 0, intent2, 201326592)).build();
        return new Notification.Builder(getContext(), SystemNotificationChannels.SYSTEM_CHANGES).setSmallIcon(17304219).setContentTitle(getContext().getResources().getString(17042460)).setContentText(getContext().getResources().getString(17042459)).setContentIntent(PendingIntent.getActivity(getContext(), 0, intent, 201326592)).setStyle(new Notification.BigTextStyle()).setFlag(32, true).setAutoCancel(true).addAction(build).addAction(new Notification.Action.Builder((Icon) null, getContext().getResources().getString(17042457), PendingIntent.getBroadcast(getContext(), 0, intent3, 201326592)).build()).setDeleteIntent(PendingIntent.getBroadcast(getContext(), 0, intent4, 201326592)).build();
    }

    public void maybeShowInitialReviewPermissionsNotification() {
        if (this.mShowReviewPermissionsNotification) {
            int i = Settings.Global.getInt(getContext().getContentResolver(), "review_permissions_notification_state", -1);
            if (i == 0 || i == 3) {
                ((NotificationManager) getContext().getSystemService(NotificationManager.class)).notify("NotificationService", 71, createReviewPermissionsNotification());
            }
        }
    }

    public final class EdgeLightingLocalService extends EdgeManagerInternal {
        public EdgeLightingLocalService() {
        }

        public boolean showForNotification(StatusBarNotification statusBarNotification, Bundle bundle) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForNotification(statusBarNotification, bundle);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean hideForNotification(StatusBarNotification statusBarNotification) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.hideForNotification(statusBarNotification, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean showForToast(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForToast(str, str2, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean showForWakeUp(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeUp(str, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean showForWakeLock(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeLock(str, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void hideForWakeLock(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.hideForWakeLock(str, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean showForWakeUpByWindow(String str, String str2, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeUpByWindow(str, str2, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean showForWakeLockByWindow(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeLockByWindow(str, str2, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void hideForWakeLockByWindow(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.hideForWakeLockByWindow(str, str2, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void showForResumedActivity(ComponentName componentName) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.showForResumedActivity(componentName);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void statusBarDisabled(int i, int i2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.statusBarDisabled(i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setSuppressed(int i) {
            NotificationManagerService.this.mEdgeLightingManager.setSuppressed(i);
        }
    }

    public final synchronized void postedNotificationLog(NotificationRecord notificationRecord) {
        if (canSendLoggingData(notificationRecord)) {
            getOrCreateNotificationLogLocked(notificationRecord);
        }
    }

    public final synchronized void canceledNotificationLog(NotificationRecord notificationRecord, int i) {
        if (canSendLoggingData(notificationRecord)) {
            CollectionContract$Notification$Log orCreateNotificationLogLocked = getOrCreateNotificationLogLocked(notificationRecord);
            orCreateNotificationLogLocked.cancelReason = i;
            orCreateNotificationLogLocked.canceledTimeMs = System.currentTimeMillis();
            this.mEnqueueLogs.remove(notificationRecord.getSbn().getPackageName() + "|" + notificationRecord.getSbn().getId());
            this.mCancelLogs.add(orCreateNotificationLogLocked);
            if (this.mCancelLogs.size() == 10) {
                final ArrayList arrayList = new ArrayList(this.mCancelLogs);
                if (this.mIsRuneStoneEnabled) {
                    BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationManagerService.this.lambda$canceledNotificationLog$18(arrayList);
                        }
                    });
                }
                this.mCancelLogs.clear();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$canceledNotificationLog$18(List list) {
        Log.d("NotificationService", "start sending log");
        try {
            CollectionContract.API.putLogs(getContext(), list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final synchronized void expandedNotificationLog(NotificationRecord notificationRecord) {
        if (canSendLoggingData(notificationRecord)) {
            CollectionContract$Notification$Log orCreateNotificationLogLocked = getOrCreateNotificationLogLocked(notificationRecord);
            if (orCreateNotificationLogLocked.firstExpandedTimeMs == -1) {
                orCreateNotificationLogLocked.firstExpandedTimeMs = System.currentTimeMillis();
            }
        }
    }

    public final synchronized void shownNotificationLog(NotificationRecord notificationRecord) {
        if (canSendLoggingData(notificationRecord)) {
            CollectionContract$Notification$Log orCreateNotificationLogLocked = getOrCreateNotificationLogLocked(notificationRecord);
            if (orCreateNotificationLogLocked.firstShownTimeMs == -1) {
                orCreateNotificationLogLocked.firstShownTimeMs = System.currentTimeMillis();
            }
        }
    }

    public final boolean canSendLoggingData(NotificationRecord notificationRecord) {
        Bundle bundle;
        if (this.mIsRuneStoneSupported && this.mIsRuneStoneEnabled) {
            String packageName = notificationRecord.getSbn().getPackageName();
            if (this.mAllowedPackage.contains(packageName)) {
                return true;
            }
            try {
                ApplicationInfo applicationInfo = getContext().getPackageManager().getApplicationInfo(packageName, 128);
                if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && "runestone".equals(bundle.getString("com.samsung.android.notification.logging", "default"))) {
                    Slog.i("NotificationService", "Notification listener logging, pkg = " + packageName);
                    this.mAllowedPackage.add(packageName);
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public final CollectionContract$Notification$Log getOrCreateNotificationLogLocked(NotificationRecord notificationRecord) {
        String str = notificationRecord.getSbn().getPackageName() + "|" + notificationRecord.getSbn().getId();
        CollectionContract$Notification$Log collectionContract$Notification$Log = (CollectionContract$Notification$Log) this.mEnqueueLogs.get(str);
        if (collectionContract$Notification$Log != null) {
            return collectionContract$Notification$Log;
        }
        CollectionContract$Notification$Log collectionContract$Notification$Log2 = new CollectionContract$Notification$Log();
        collectionContract$Notification$Log2.pkg = notificationRecord.getSbn().getPackageName();
        collectionContract$Notification$Log2.f1718id = notificationRecord.getSbn().getId();
        collectionContract$Notification$Log2.category = notificationRecord.getSbn().getNotification().category;
        collectionContract$Notification$Log2.channelId = notificationRecord.getChannel().getId();
        collectionContract$Notification$Log2.tag = notificationRecord.getSbn().getTag();
        collectionContract$Notification$Log2.enqueuedTimeMs = System.currentTimeMillis();
        this.mEnqueueLogs.put(str, collectionContract$Notification$Log2);
        return collectionContract$Notification$Log2;
    }

    public final boolean isRuneStoneSupported() {
        return RunestoneSupportContract.API.isSupportVersion(getContext(), "com.samsung.android.rubin.app", 0L) && RunestoneSupportContract.API.isPlatformSignedPackage(getContext(), "com.samsung.android.rubin.app");
    }

    public class NotificationTrampolineCallback implements BackgroundActivityStartCallback {
        public NotificationTrampolineCallback() {
        }

        @Override // com.android.server.wm.BackgroundActivityStartCallback
        public boolean isActivityStartAllowed(Collection collection, int i, String str) {
            Preconditions.checkArgument(!collection.isEmpty());
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (((IBinder) it.next()) != NotificationManagerService.ALLOWLIST_TOKEN) {
                    return true;
                }
            }
            String str2 = "Indirect notification activity start (trampoline) from " + str;
            if (blockTrampoline(i)) {
                Slog.e("NotificationService", str2 + " blocked");
                return false;
            }
            Slog.w("NotificationService", str2 + ", this should be avoided for performance reasons");
            return true;
        }

        public final boolean blockTrampoline(int i) {
            if (NotificationManagerService.this.mRoleObserver != null && NotificationManagerService.this.mRoleObserver.isUidExemptFromTrampolineRestrictions(i)) {
                return CompatChanges.isChangeEnabled(227752274L, i);
            }
            return CompatChanges.isChangeEnabled(167676448L, i);
        }

        @Override // com.android.server.wm.BackgroundActivityStartCallback
        public boolean canCloseSystemDialogs(Collection collection, int i) {
            return collection.contains(NotificationManagerService.ALLOWLIST_TOKEN) && !CompatChanges.isChangeEnabled(167676448L, i);
        }
    }

    public interface PostNotificationTrackerFactory {
        default PostNotificationTracker newTracker(PowerManager.WakeLock wakeLock) {
            return new PostNotificationTracker(wakeLock);
        }
    }

    public class PostNotificationTracker {
        public final PowerManager.WakeLock mWakeLock;
        public final long mStartTime = SystemClock.elapsedRealtime();
        public boolean mOngoing = true;

        public PostNotificationTracker(PowerManager.WakeLock wakeLock) {
            this.mWakeLock = wakeLock;
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "PostNotification: Started");
            }
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public boolean isOngoing() {
            return this.mOngoing;
        }

        public void cancel() {
            if (!isOngoing()) {
                Log.wtfStack("NotificationService", "cancel() called on already-finished tracker");
                return;
            }
            this.mOngoing = false;
            if (this.mWakeLock != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationTracker$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        NotificationManagerService.PostNotificationTracker.this.lambda$cancel$0();
                    }
                });
            }
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", TextUtils.formatSimple("PostNotification: Abandoned after %d ms", new Object[]{Long.valueOf(SystemClock.elapsedRealtime() - this.mStartTime)}));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancel$0() {
            this.mWakeLock.release();
        }

        public long finish() {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartTime;
            if (!isOngoing()) {
                Log.wtfStack("NotificationService", "finish() called on already-finished tracker");
                return elapsedRealtime;
            }
            this.mOngoing = false;
            if (this.mWakeLock != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationTracker$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        NotificationManagerService.PostNotificationTracker.this.lambda$finish$1();
                    }
                });
            }
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", TextUtils.formatSimple("PostNotification: Finished in %d ms", new Object[]{Long.valueOf(elapsedRealtime)}));
            }
            return elapsedRealtime;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$finish$1() {
            this.mWakeLock.release();
        }
    }
}
