package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.AutomaticZenRule;
import android.app.IActivityManager;
import android.app.ICallNotificationEventCallback;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.app.ITransientNotificationCallback;
import android.app.IUriGrantsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationHistory;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.StatsManager;
import android.app.UriGrantsManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.backup.BackupManager;
import android.app.compat.CompatChanges;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManagerInternal;
import android.companion.AssociationInfo;
import android.companion.ICompanionDeviceManager;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserInfo;
import android.content.pm.VersionedPackage;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.DeviceIdleManager;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.ConversationChannelWrapper;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.IStatusBarNotificationHolder;
import android.service.notification.NotificationListenerFilter;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.NotificationStats;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.IntArray;
import android.util.JsonWriter;
import android.util.LocalLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import android.view.accessibility.AccessibilityManager;
import android.widget.RemoteViews;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.EventLogTags;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.lights.LightsManager;
import com.android.server.notification.GroupHelper;
import com.android.server.notification.ManagedServices;
import com.android.server.notification.NotificationAttentionHelper;
import com.android.server.notification.NotificationChannelLogger;
import com.android.server.notification.NotificationHistoryDatabase.RemoveChannelRunnable;
import com.android.server.notification.NotificationHistoryDatabase.RemoveConversationRunnable;
import com.android.server.notification.NotificationHistoryDatabase.RemoveNotificationRunnable;
import com.android.server.notification.NotificationManagerService;
import com.android.server.notification.NotificationRecordLogger;
import com.android.server.notification.NotificationUsageStats;
import com.android.server.notification.PreferencesHelper;
import com.android.server.notification.SmartAlertController.AnonymousClass2;
import com.android.server.notification.ZenModeFiltering;
import com.android.server.notification.ZenModeHelper;
import com.android.server.notification.edgelighting.EdgeLightingClientManager;
import com.android.server.notification.edgelighting.EdgeLightingHistory;
import com.android.server.notification.edgelighting.EdgeLightingListenerManager;
import com.android.server.notification.edgelighting.EdgeLightingManager;
import com.android.server.notification.edgelighting.EdgeLightingPolicyManager;
import com.android.server.notification.edgelighting.policy.EdgeLightingPolicyRepository;
import com.android.server.notification.sec.runestone.CollectionContract$Notification$Log;
import com.android.server.notification.sec.timetoleave.TimeToLeaveHelper;
import com.android.server.notification.toast.CustomToastRecord;
import com.android.server.notification.toast.TextToastRecord;
import com.android.server.notification.toast.ToastRecord;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.policy.PermissionPolicyService;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.quota.MultiRateLimiter;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.VisibleActivityProcessTracker;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowProcessController;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import com.samsung.android.knoxguard.service.utils.Constants;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationManagerService extends SystemService {
    public static final String ACTION_NOTIFICATION_TIMEOUT;
    public static final String[] ALLOWED_ADJUSTMENTS;
    public static final IBinder ALLOWLIST_TOKEN;
    public static final Duration BITMAP_DURATION;
    public static final boolean DBG = Log.isLoggable("NotificationService", 3);
    public static final boolean DEBUG_INTERRUPTIVENESS;
    public static final ComponentName EDGE_NOTIFICATION_COMPONENT;
    public static final List HEALTH_KEY_LIST;
    public static final int MY_PID;
    public static final int MY_UID;
    public static final String[] NON_BLOCKABLE_DEFAULT_ROLES;
    public static final Duration POST_WAKE_LOCK_TIMEOUT;
    public static final MultiRateLimiter.RateLimit[] TOAST_RATE_LIMITS;
    public final SimpleDateFormat dayTime;
    public AccessibilityManager mAccessibilityManager;
    public ActivityManager mActivityManager;
    public ModuleInfo mAdservicesModuleInfo;
    public AlarmManager mAlarmManager;
    public NotificationManagerService$$ExternalSyntheticLambda6 mAllowedManagedServicePackages;
    public final Set mAllowedPackage;
    public IActivityManager mAm;
    public ActivityManagerInternal mAmi;
    public AppOpsManager mAppOps;
    public AnonymousClass13 mAppOpsListener;
    public UsageStatsManagerInternal mAppUsageStats;
    public IApplicationPolicy mApplicationPolicyService;
    public Archive mArchive;
    NotificationAssistants mAssistants;
    public ActivityTaskManagerInternal mAtm;
    public NotificationAttentionHelper mAttentionHelper;
    public int mAutoGroupAtCount;
    public final ArrayMap mAutobundledSummaries;
    public boolean mCMCinCallState;
    public final ArrayMap mCallNotificationEventCallbacks;
    public final List mCancelLogs;
    ICompanionDeviceManager mCompanionManager;
    public ConditionProviders mConditionProviders;
    public List mConversationAppList;
    public AtomicFile mConversationAppPolicyFile;
    public long mConversationAppPolicyVersion;
    public List mConversationHistoryAppList;
    public CoverManager mCoverManager;
    public String mDefaultSearchSelectorPkg;
    public final ConcurrentList mDelayedWakeUpList;
    public final ConcurrentList mDelayedWakelockList;
    public DeviceIdleManager mDeviceIdleManager;
    public DevicePolicyManagerInternal mDpm;
    public EdgeLightingManager mEdgeLightingManager;
    public List mEffectsSuppressors;
    public final ArrayMap mEnqueueLogs;
    public final ArrayList mEnqueuedNotifications;
    public SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    public ArrayList mFloatingPackageList;
    public boolean mFoldState;
    public final AnonymousClass1 mFoldStateListener;
    public final IBinder mForegroundToken;
    public boolean mGoodCatchNotiBlockedOn;
    public final AnonymousClass26 mGoodCatchStateListener;
    public boolean mGoodCatchViToastOn;
    public GroupHelper mGroupHelper;
    WorkerHandler mHandler;
    public final ArrayList mHighDataSizeNotificaitonList;
    public NotificationHistoryManager mHistoryManager;
    public final ArrayMap mInlineReplyRecordsByKey;
    public final AnonymousClass5 mIntentReceiver;
    public final AnonymousClass17 mInternalService;
    public int mInterruptionFilter;
    public boolean mIsCurrentToastShown;
    public boolean mIsDisableHunByCall;
    public final boolean mIsFactoryBinary;
    public boolean mIsInterruptivePostNotif;
    public boolean mIsMaxNotiLimitEnabled;
    public boolean mIsRuneStoneEnabled;
    public boolean mIsRuneStoneSupported;
    public boolean mIsTelevision;
    public long mLastOverRateLogTime;
    public List mLimitNotificationsForOverflowAppList;
    public int mListenerHints;
    public NotificationListeners mListeners;
    public final SparseArray mListenersDisablingEffects;
    public final AnonymousClass5 mLocaleChangeReceiver;
    public boolean mLockScreenAllowSecureNotifications;
    public LockPatternUtils mLockUtils;
    public int mMaxNotiLimitCount;
    public float mMaxPackageEnqueueRate;
    public MetricsLogger mMetricsLogger;
    public Set mMsgPkgsAllowedAsConvos;
    public boolean mMultiStarEnable;
    public boolean mNeedDeletePrevHistory;
    public final AnonymousClass26 mNotiGoodCatchStateListener;
    public final ArrayList mNotiListenerHistoryList;
    public final ArrayList mNotiPermissionHistoryList;
    public SemGoodCatchManager mNotiSemGoodCatchManager;
    public NotificationChannelLogger mNotificationChannelLogger;
    final NotificationDelegate mNotificationDelegate;
    public NotificationHighlightCore mNotificationHighlightCore;
    public final InstanceIdSequence mNotificationInstanceIdSequence;
    public final ArrayList mNotificationList;
    public String mNotificationListenerFrom;
    public final Object mNotificationLock;
    public final AnonymousClass2 mNotificationManagerPrivate;
    public final NotificationRecordLogger mNotificationRecordLogger;
    public NotificationReminder mNotificationReminder;
    public final AnonymousClass5 mNotificationTimeoutReceiver;
    public final ArrayMap mNotificationsByKey;
    public List mOngoingActivityAppList;
    public final ArrayMap mOngoingActivitySettingValue;
    public List mOngoingDismissExceptionKeyList;
    public AtomicFile mOngoingDismissExceptionPolicyFile;
    public long mOngoingDismissExceptionPolicyVersion;
    public final AnonymousClass5 mPackageIntentReceiver;
    IPackageManager mPackageManager;
    public PackageManager mPackageManagerClient;
    public PackageManagerInternal mPackageManagerInternal;
    public PermissionHelper mPermissionHelper;
    public PermissionManager mPermissionManager;
    public PermissionPolicyService.Internal mPermissionPolicyInternal;
    public IPlatformCompat mPlatformCompat;
    public AtomicFile mPolicyFile;
    public PostNotificationTrackerFactory mPostNotificationTrackerFactory;
    public PowerManager mPowerManager;
    PreferencesHelper mPreferencesHelper;
    public StatsPullAtomCallbackImpl mPullAtomCallback;
    public RankingHandler mRankingHandler;
    RankingHelper mRankingHelper;
    public final HandlerThread mRankingThread;
    public final AnonymousClass5 mRestoreReceiver;
    public ReviewNotificationPermissionsReceiver mReviewNotificationPermissionsReceiver;
    public volatile RoleObserver mRoleObserver;
    public final AnonymousClass11 mSaveConversationPackagePolicyFile;
    public final AnonymousClass11 mSaveOngoingDismissExceptionPolicyFile;
    public final AnonymousClass11 mSavePolicyFile;
    public final AnonymousClass11 mSaveScpmNotificationPoliciesFile;
    public final AnonymousClass5 mScpmIntentReceiver;
    public AtomicFile mScpmNotificationPoliciesFile;
    public long mScpmNotificationPoliciesVersion;
    public final AnonymousClass5 mSecIntentReceiver;
    public SemGoodCatchManager mSemGoodCatchManager;
    final IBinder mService;
    public SettingsObserver mSettingsObserver;
    public ShortcutHelper mShortcutHelper;
    public final AnonymousClass2 mShortcutListener;
    protected boolean mShowReviewPermissionsNotification;
    public SmartAlertController mSmartAlertController;
    public boolean mSmartPopupEnable;
    public SnoozeHelper mSnoozeHelper;
    public final AnonymousClass25 mStateContentObserver;
    public StatsManager mStatsManager;
    public StatusBarManagerInternal mStatusBar;
    public int mStripRemoteViewsSizeBytes;
    public StrongAuthTracker mStrongAuthTracker;
    public final ArrayMap mSummaryByGroupKey;
    public TelecomManager mTelecomManager;
    public TimeToLeaveHelper mTimeToLeaveHelper;
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
    public final ManagedServices.UserProfiles mUserProfiles;
    public PowerManager.WakeLock mWakeLockForAssistantDelay;
    public int mWarnRemoteViewsSizeBytes;
    public WindowManagerInternal mWindowManagerInternal;
    public ZenModeHelper mZenModeHelper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$11, reason: invalid class name */
    public final class AnonymousClass11 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass11(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    ZenModeHelper zenModeHelper = ((NotificationManagerService) this.this$0).mZenModeHelper;
                    int i = (zenModeHelper.mZenMode == 0 || zenModeHelper.getNotificationPolicy() == null) ? 0 : ((NotificationManagerService) this.this$0).mZenModeHelper.getNotificationPolicy().suppressedVisualEffects;
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "notifyZenPolicy : suppressed = ", "NotificationService");
                    ((NotificationManagerService) this.this$0).mEdgeLightingManager.mEdgeLightingPolicyManager.mSuppressed = i;
                    return;
                case 1:
                    PowerManager.WakeLock wakeLock = ((NotificationManagerService) this.this$0).mWakeLockForAssistantDelay;
                    if (wakeLock != null && wakeLock.isHeld()) {
                        ((NotificationManagerService) this.this$0).mWakeLockForAssistantDelay.release();
                    }
                    NotificationManagerService notificationManagerService = (NotificationManagerService) this.this$0;
                    if (notificationManagerService.mWakeLockForAssistantDelay == null) {
                        notificationManagerService.mWakeLockForAssistantDelay = ((PowerManager) notificationManagerService.getContext().getSystemService(PowerManager.class)).newWakeLock(1, "Prevent Sleep by AssistantDelay");
                    }
                    ((NotificationManagerService) this.this$0).mWakeLockForAssistantDelay.acquire(500L);
                    return;
                case 2:
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "handleSaveConversationPackagePolicyFile runnable");
                    }
                    synchronized (((NotificationManagerService) this.this$0).mConversationAppPolicyFile) {
                        try {
                            FileOutputStream startWrite = ((NotificationManagerService) this.this$0).mConversationAppPolicyFile.startWrite();
                            try {
                                NotificationManagerService.m724$$Nest$mwriteConversationAppPolicyJson((NotificationManagerService) this.this$0, startWrite);
                                ((NotificationManagerService) this.this$0).mConversationAppPolicyFile.finishWrite(startWrite);
                            } catch (Exception e) {
                                Slog.w("NotificationService", "Failed to save conversation package policy file, restoring backup", e);
                                ((NotificationManagerService) this.this$0).mConversationAppPolicyFile.failWrite(startWrite);
                            }
                        } catch (IOException e2) {
                            Slog.w("NotificationService", "Failed to save conversation package policy file", e2);
                            return;
                        }
                    }
                    return;
                case 3:
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "handleSaveOngoingDismissExceptionPolicyFile runnable");
                    }
                    synchronized (((NotificationManagerService) this.this$0).mOngoingDismissExceptionPolicyFile) {
                        try {
                            FileOutputStream startWrite2 = ((NotificationManagerService) this.this$0).mOngoingDismissExceptionPolicyFile.startWrite();
                            try {
                                NotificationManagerService.m725$$Nest$mwriteOngoingDismissExceptionPolicyJson((NotificationManagerService) this.this$0, startWrite2);
                                ((NotificationManagerService) this.this$0).mOngoingDismissExceptionPolicyFile.finishWrite(startWrite2);
                            } catch (Exception e3) {
                                Slog.w("NotificationService", "Failed to save ongoing dismiss exception policy file, restoring backup", e3);
                                ((NotificationManagerService) this.this$0).mOngoingDismissExceptionPolicyFile.failWrite(startWrite2);
                            }
                        } catch (IOException e4) {
                            Slog.w("NotificationService", "Failed to save ongoing dismiss exception policy file", e4);
                            return;
                        }
                    }
                    return;
                case 4:
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "handleSavePolicyFile");
                    }
                    synchronized (((NotificationManagerService) this.this$0).mPolicyFile) {
                        try {
                            FileOutputStream startWrite3 = ((NotificationManagerService) this.this$0).mPolicyFile.startWrite();
                            try {
                                NotificationManagerService.m726$$Nest$mwritePolicyXml((NotificationManagerService) this.this$0, startWrite3, false, -1);
                                ((NotificationManagerService) this.this$0).mPolicyFile.finishWrite(startWrite3);
                            } catch (IOException e5) {
                                Slog.w("NotificationService", "Failed to save policy file, restoring backup", e5);
                                ((NotificationManagerService) this.this$0).mPolicyFile.failWrite(startWrite3);
                            }
                        } catch (IOException e6) {
                            Slog.w("NotificationService", "Failed to save policy file", e6);
                            return;
                        }
                    }
                    BackupManager.dataChanged(((NotificationManagerService) this.this$0).getContext().getPackageName());
                    return;
                case 5:
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "handleSaveScpmNotificationPoliciesFile runnable");
                    }
                    synchronized (((NotificationManagerService) this.this$0).mScpmNotificationPoliciesFile) {
                        try {
                            FileOutputStream startWrite4 = ((NotificationManagerService) this.this$0).mScpmNotificationPoliciesFile.startWrite();
                            try {
                                NotificationManagerService.m727$$Nest$mwriteScpmNotificationPoliciesJson((NotificationManagerService) this.this$0, startWrite4);
                                ((NotificationManagerService) this.this$0).mScpmNotificationPoliciesFile.finishWrite(startWrite4);
                            } catch (Exception e7) {
                                Slog.w("NotificationService", "Failed to save SCPM notification policies file, restoring backup", e7);
                                ((NotificationManagerService) this.this$0).mScpmNotificationPoliciesFile.failWrite(startWrite4);
                            }
                        } catch (IOException e8) {
                            Slog.w("NotificationService", "Failed to save SCPM notification policies file", e8);
                            return;
                        }
                    }
                    return;
                default:
                    PowerManager.WakeLock wakeLock2 = NotificationManagerService.this.mWakeLockForAssistantDelay;
                    if (wakeLock2 != null && wakeLock2.isHeld()) {
                        NotificationManagerService.this.mWakeLockForAssistantDelay.release();
                    }
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    if (notificationManagerService2.mWakeLockForAssistantDelay == null) {
                        notificationManagerService2.mWakeLockForAssistantDelay = ((PowerManager) notificationManagerService2.getContext().getSystemService(PowerManager.class)).newWakeLock(1, "Prevent Sleep by AssistantDelay");
                    }
                    NotificationManagerService.this.mWakeLockForAssistantDelay.acquire(500L);
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$12, reason: invalid class name */
    public final class AnonymousClass12 extends ZenModeHelper.Callback {
        public AnonymousClass12() {
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onAutomaticRuleStatusChanged(final int i, final int i2, final String str, final String str2) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$12$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    NotificationManagerService.AnonymousClass12 anonymousClass12 = NotificationManagerService.AnonymousClass12.this;
                    String str3 = str;
                    String str4 = str2;
                    int i3 = i2;
                    int i4 = i;
                    anonymousClass12.getClass();
                    Intent intent = new Intent("android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED");
                    intent.setPackage(str3);
                    intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_ID", str4);
                    intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_STATUS", i3);
                    NotificationManagerService.this.getContext().sendBroadcastAsUser(intent, UserHandle.of(i4));
                }
            });
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onConfigChanged() {
            NotificationManagerService.this.handleSavePolicyFile();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
            Binder.withCleanCallingIdentity(new NotificationManagerService$12$$ExternalSyntheticLambda0(this, policy, 1));
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onPolicyChanged(NotificationManager.Policy policy) {
            Binder.withCleanCallingIdentity(new NotificationManagerService$12$$ExternalSyntheticLambda0(this, policy, 0));
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onZenModeChanged() {
            Binder.withCleanCallingIdentity(new NotificationManagerService$12$$ExternalSyntheticLambda3(0, this));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$13, reason: invalid class name */
    public final class AnonymousClass13 extends AppOpsManager.OnOpChangedInternalListener {
        public AnonymousClass13() {
        }

        public final void onOpChanged(String str, String str2, int i) {
            NotificationManagerService.this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda16(i, 1, this, str2));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$14, reason: invalid class name */
    public final class AnonymousClass14 implements PostNotificationTrackerFactory {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$16, reason: invalid class name */
    public final class AnonymousClass16 extends INotificationManager.Stub {
        public AnonymousClass16() {
        }

        public static void addEventLogTags(int i, String str, String str2) {
            if (str2 == null || str2.length() <= 0) {
                return;
            }
            String str3 = ((char) (str2.charAt(0) + 1)) + str2.substring(1, str2.length());
            if (str3.length() > 3) {
                EventLog.writeEvent(275535, Integer.valueOf(i), str, str3.subSequence(0, 3).toString());
            } else {
                EventLog.writeEvent(275535, Integer.valueOf(i), str, str3.subSequence(0, str3.length()).toString());
            }
        }

        public static void enforceDeletingChannelHasNoUserInitiatedJob(int i, String str, String str2) {
            JobSchedulerInternal jobSchedulerInternal = (JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class);
            if (jobSchedulerInternal == null || !jobSchedulerInternal.isNotificationChannelAssociatedWithAnyUserInitiatedJobs(str2, i, str)) {
                return;
            }
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Package u", "/", str, " may not delete notification channel '");
            m.append(str2);
            m.append("' with user-initiated job");
            Slog.w("NotificationService", m.toString());
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Not allowed to delete channel ", str2, " with a user-initiated job"));
        }

        public static StatusBarNotification sanitizeSbn(String str, int i, StatusBarNotification statusBarNotification) {
            if (statusBarNotification.getUserId() != i || (!statusBarNotification.getPackageName().equals(str) && !statusBarNotification.getOpPkg().equals(str))) {
                return null;
            }
            Notification clone = statusBarNotification.getNotification().clone();
            clone.overrideAllowlistToken(null);
            return new StatusBarNotification(statusBarNotification.getPackageName(), statusBarNotification.getOpPkg(), statusBarNotification.getId(), statusBarNotification.getTag(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), clone, statusBarNotification.getUser(), statusBarNotification.getOverrideGroupKey(), statusBarNotification.getPostTime());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:82:0x01ea A[Catch: all -> 0x0168, TryCatch #0 {all -> 0x0168, blocks: (B:69:0x0141, B:71:0x0145, B:73:0x0149, B:74:0x016b, B:77:0x01ad, B:80:0x01dd, B:82:0x01ea, B:83:0x01ec, B:85:0x01ee, B:86:0x01f5, B:87:0x01bb, B:89:0x01c2, B:91:0x01ce, B:93:0x01d2, B:95:0x01d8, B:96:0x0186, B:99:0x018d, B:102:0x0198, B:105:0x01a3, B:106:0x01f6, B:107:0x01fd), top: B:68:0x0141 }] */
        /* JADX WARN: Removed duplicated region for block: B:85:0x01ee A[Catch: all -> 0x0168, TryCatch #0 {all -> 0x0168, blocks: (B:69:0x0141, B:71:0x0145, B:73:0x0149, B:74:0x016b, B:77:0x01ad, B:80:0x01dd, B:82:0x01ea, B:83:0x01ec, B:85:0x01ee, B:86:0x01f5, B:87:0x01bb, B:89:0x01c2, B:91:0x01ce, B:93:0x01d2, B:95:0x01d8, B:96:0x0186, B:99:0x018d, B:102:0x0198, B:105:0x01a3, B:106:0x01f6, B:107:0x01fd), top: B:68:0x0141 }] */
        /* JADX WARN: Type inference failed for: r1v32, types: [android.content.pm.ActivityInfo] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule r13, java.lang.String r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 512
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.addAutomaticZenRule(android.app.AutomaticZenRule, java.lang.String, boolean):java.lang.String");
        }

        public final void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) {
            if (str2 == null) {
                Slog.d("NotificationService", "pkg data null value, addReplyHistory can not be saved.");
                return;
            }
            NotificationHistoryManager notificationHistoryManager = NotificationManagerService.this.mHistoryManager;
            NotificationHistory.HistoricalNotification build = new NotificationHistory.HistoricalNotification.Builder().setPackage(str2).setUid(Binder.getCallingUid()).setUserId(i2).setPostedTimeMs(System.currentTimeMillis()).setChannelId("setChannelId").setChannelName("setChannelName").setTitle(str3).setText(str4).setIcon(Icon.createWithResource(NotificationManagerService.this.getContext().getResources(), 17304445)).setSbnKey(str).setType(i).build();
            notificationHistoryManager.getClass();
            Binder.withCleanCallingIdentity(new NotificationHistoryManager$$ExternalSyntheticLambda0(notificationHistoryManager, build));
        }

        public final void addToListIfNeeded(NotificationRecord notificationRecord, ManagedServices.ManagedServiceInfo managedServiceInfo, ArrayList arrayList, int i) {
            if (notificationRecord == null) {
                return;
            }
            StatusBarNotification statusBarNotification = notificationRecord.sbn;
            if (NotificationManagerService.this.isVisibleToListener(statusBarNotification, notificationRecord.getNotificationType(), managedServiceInfo)) {
                NotificationManagerService.this.mListeners.getClass();
                if (NotificationListeners.hasSensitiveContent(notificationRecord) && !NotificationManagerService.this.mListeners.isUidTrusted(managedServiceInfo.uid)) {
                    arrayList.add(NotificationManagerService.this.mListeners.redactStatusBarNotification(statusBarNotification));
                    return;
                }
                if (i != 0) {
                    statusBarNotification = statusBarNotification.cloneLight();
                }
                arrayList.add(statusBarNotification);
            }
        }

        public final boolean addWearableAppToList(int i, String str) {
            try {
                int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                Log.d("NotificationService", "NMS : addWearableAppToList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, 1, str);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public final void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(adjustment);
            applyAdjustmentsFromAssistant(iNotificationListener, arrayList);
        }

        public final void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List list) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                        Iterator it = list.iterator();
                        z = false;
                        while (it.hasNext()) {
                            Adjustment adjustment = (Adjustment) it.next();
                            NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(adjustment.getKey());
                            if (notificationRecord != null && NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.sbn.getUserId())) {
                                NotificationManagerService.m695$$Nest$mapplyAdjustmentLocked(NotificationManagerService.this, notificationRecord, adjustment, true);
                                if (adjustment.getSignals().containsKey("key_importance") && adjustment.getSignals().getInt("key_importance") == 0) {
                                    cancelNotificationsFromListener(iNotificationListener, new String[]{notificationRecord.sbn.getKey()});
                                } else {
                                    notificationRecord.mPendingLogUpdate = true;
                                    z = true;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    ((RankingHandlerWorker) NotificationManagerService.this.mRankingHandler).requestSort();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                        int size = NotificationManagerService.this.mEnqueuedNotifications.size();
                        boolean z = false;
                        for (int i = 0; i < size; i++) {
                            NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i);
                            if (Objects.equals(adjustment.getKey(), notificationRecord.sbn.getKey()) && Integer.valueOf(adjustment.getUser()).equals(Integer.valueOf(notificationRecord.sbn.getUserId())) && NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.sbn.getUserId())) {
                                NotificationManagerService.m695$$Nest$mapplyAdjustmentLocked(NotificationManagerService.this, notificationRecord, adjustment, false);
                                notificationRecord.applyAdjustments();
                                notificationRecord.calculateImportance();
                                z = true;
                            }
                        }
                        if (!z) {
                            applyAdjustmentsFromAssistant(iNotificationListener, List.of(adjustment));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void applyRestore(byte[] bArr, int i) {
            if (NotificationManagerService.m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService.this)) {
                NotificationManagerService.this.mHandler.removeMessages(9);
                NotificationManagerService.this.mHandler.obtainMessage(9, i, 0, bArr).sendToTarget();
                return;
            }
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            if (NotificationManagerService.DBG) {
                BootReceiver$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "applyRestore u=", " payload="), bArr != null ? new String(bArr, StandardCharsets.UTF_8) : null, "NotificationService");
            }
            if (bArr == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "applyRestore: no payload to restore for user ", "NotificationService");
                return;
            }
            try {
                NotificationManagerService.this.readPolicyXml(i, new ByteArrayInputStream(bArr), true);
                NotificationManagerService.this.handleSavePolicyFile();
            } catch (IOException | NumberFormatException | XmlPullParserException e) {
                Slog.w("NotificationService", "applyRestore: error reading payload", e);
            }
        }

        public final boolean areBubblesAllowed(String str) {
            return getBubblePreferenceForPackage(str, Binder.getCallingUid()) == 1;
        }

        public final boolean areBubblesEnabled(UserHandle userHandle) {
            if (UserHandle.getCallingUserId() != userHandle.getIdentifier()) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "areBubblesEnabled for user " + userHandle.getIdentifier());
            }
            return NotificationManagerService.this.mPreferencesHelper.bubblesEnabled(userHandle);
        }

        public final boolean areChannelsBypassingDnd() {
            return android.app.Flags.modesApi() ? NotificationManagerService.this.mZenModeHelper.mConsolidatedPolicy.copy().allowPriorityChannels() && NotificationManagerService.this.mPreferencesHelper.mCurrentUserHasChannelsBypassingDnd : NotificationManagerService.this.mPreferencesHelper.mCurrentUserHasChannelsBypassingDnd;
        }

        public final boolean areNotificationsEnabled(String str) {
            return areNotificationsEnabledForPackage(str, Binder.getCallingUid());
        }

        public final boolean areNotificationsEnabledForPackage(String str, int i) {
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            NotificationManagerService.this.getClass();
            if (!NotificationManagerService.isCallingUidSystem() && UserHandle.getCallingUserId() != UserHandle.getUserId(i)) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "canNotifyAsPackage for uid " + i);
            }
            return NotificationManagerService.this.mPermissionHelper.hasPermission(i);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0081 A[Catch: all -> 0x0047, TryCatch #1 {all -> 0x0047, blocks: (B:13:0x003e, B:15:0x0042, B:18:0x0075, B:19:0x007b, B:21:0x0081, B:24:0x0089, B:27:0x0091, B:28:0x009a, B:36:0x009c, B:37:0x00c0, B:39:0x004a), top: B:12:0x003e }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void bindEdgeLightingService(android.os.IBinder r12, int r13, android.content.ComponentName r14) {
            /*
                r11 = this;
                com.android.server.notification.NotificationManagerService r11 = com.android.server.notification.NotificationManagerService.this
                com.android.server.notification.edgelighting.EdgeLightingManager r11 = r11.mEdgeLightingManager
                com.android.server.notification.edgelighting.EdgeLightingManager$SecurityPolicy r0 = r11.mSecurityPolicy
                r0.enforceCallingPermissionFromHost()
                com.android.server.notification.edgelighting.EdgeLightingManager$SecurityPolicy r0 = r11.mSecurityPolicy
                java.lang.String r1 = r14.getPackageName()
                r0.enforceCallFromPackage(r1)
                com.android.server.notification.edgelighting.EdgeLightingClientManager r0 = r11.mEdgeLightingClientManager
                java.util.ArrayList r1 = r0.mHosts
                monitor-enter(r1)
                java.util.ArrayList r0 = r0.mHosts     // Catch: java.lang.Throwable -> Lc4
                int r0 = r0.size()     // Catch: java.lang.Throwable -> Lc4
                r2 = 1
                if (r0 <= 0) goto L22
                r0 = r2
                goto L23
            L22:
                r0 = 0
            L23:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lc4
                if (r0 != 0) goto L37
                com.android.server.notification.edgelighting.EdgeLightingClientManager r0 = r11.mEdgeLightingClientManager
                android.content.Context r1 = r11.mContext
                android.content.ContentResolver r1 = r1.getContentResolver()
                r0.getClass()
                java.lang.String r0 = "edge_lighting"
                r3 = -2
                android.provider.Settings.System.getIntForUser(r1, r0, r2, r3)
            L37:
                com.android.server.notification.edgelighting.EdgeLightingClientManager r11 = r11.mEdgeLightingClientManager
                java.lang.String r0 = "bind : pkg = "
                java.util.ArrayList r2 = r11.mHosts
                monitor-enter(r2)
                boolean r1 = com.android.server.notification.edgelighting.EdgeLightingHistory.IS_DEV_DEBUG     // Catch: java.lang.Throwable -> L47
                if (r1 != 0) goto L4a
                boolean r1 = com.android.server.notification.edgelighting.EdgeLightingClientManager.DEBUG     // Catch: java.lang.Throwable -> L47
                if (r1 == 0) goto L75
                goto L4a
            L47:
                r11 = move-exception
                goto Lc2
            L4a:
                java.lang.String r1 = "EdgeLightingClientManager"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L47
                r3.<init>(r0)     // Catch: java.lang.Throwable -> L47
                java.lang.String r0 = r14.getPackageName()     // Catch: java.lang.Throwable -> L47
                r3.append(r0)     // Catch: java.lang.Throwable -> L47
                java.lang.String r0 = ", condition = "
                r3.append(r0)     // Catch: java.lang.Throwable -> L47
                r3.append(r13)     // Catch: java.lang.Throwable -> L47
                java.lang.String r0 = ", mHosts = "
                r3.append(r0)     // Catch: java.lang.Throwable -> L47
                java.util.ArrayList r0 = r11.mHosts     // Catch: java.lang.Throwable -> L47
                int r0 = r0.size()     // Catch: java.lang.Throwable -> L47
                r3.append(r0)     // Catch: java.lang.Throwable -> L47
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L47
                android.util.Slog.d(r1, r0)     // Catch: java.lang.Throwable -> L47
            L75:
                java.util.ArrayList r0 = r11.mHosts     // Catch: java.lang.Throwable -> L47
                java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L47
            L7b:
                boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L47
                if (r1 == 0) goto L9c
                java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L47
                com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingHost r1 = (com.android.server.notification.edgelighting.EdgeLightingClientManager.EdgeLightingHost) r1     // Catch: java.lang.Throwable -> L47
                if (r1 == 0) goto L7b
                android.os.IBinder r3 = r1.token     // Catch: java.lang.Throwable -> L47
                boolean r3 = r12.equals(r3)     // Catch: java.lang.Throwable -> L47
                if (r3 == 0) goto L7b
                java.lang.String r11 = "EdgeLightingClientManager"
                java.lang.String r12 = "bind : already registered"
                android.util.Slog.w(r11, r12)     // Catch: java.lang.Throwable -> L47
                r1.condition = r13     // Catch: java.lang.Throwable -> L47
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
                goto Lc1
            L9c:
                com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingHost r0 = new com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingHost     // Catch: java.lang.Throwable -> L47
                int r9 = android.os.Binder.getCallingPid()     // Catch: java.lang.Throwable -> L47
                int r10 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L47
                r4 = r0
                r5 = r11
                r6 = r12
                r7 = r14
                r8 = r13
                r4.<init>(r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L47
                java.util.ArrayList r11 = r11.mHosts     // Catch: java.lang.Throwable -> L47
                r11.add(r0)     // Catch: java.lang.Throwable -> L47
                com.android.server.notification.edgelighting.EdgeLightingHistory r11 = com.android.server.notification.edgelighting.EdgeLightingHistory.getInstance()     // Catch: java.lang.Throwable -> L47
                java.lang.String r12 = r14.getPackageName()     // Catch: java.lang.Throwable -> L47
                java.lang.String r13 = "bind."
                r11.updateHostHistory(r12, r13)     // Catch: java.lang.Throwable -> L47
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
            Lc1:
                return
            Lc2:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
                throw r11
            Lc4:
                r11 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lc4
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.bindEdgeLightingService(android.os.IBinder, int, android.content.ComponentName):void");
        }

        public final boolean canAppBypassDnd(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasNotificationChannelsBypassingDnd(i, str);
        }

        public final boolean canManageGlobalZenPolicy(final int i, String str) {
            return !((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda2
                public final Object getOrThrow() {
                    return Boolean.valueOf(CompatChanges.isChangeEnabled(308670109L, i));
                }
            })).booleanValue() || NotificationManagerService.this.isCallerSystemOrSystemUi() || NotificationManagerService.this.hasCompanionDevice(UserHandle.getUserId(i), str, Set.of("android.app.role.COMPANION_DEVICE_WATCH", "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION"));
        }

        public final boolean canNotifyAsPackage(String str, String str2, int i) {
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
                    return NotificationManagerService.this.mPreferencesHelper.isDelegateAllowed(applicationInfo.uid, callingUid, str2, str);
                }
                return false;
            } catch (RemoteException unused) {
                return false;
            }
        }

        public final boolean canShowBadge(String str, int i) {
            boolean z;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                z = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).showBadge;
            }
            return z;
        }

        public final boolean canUseFullScreenIntent(AttributionSource attributionSource) {
            String packageName = attributionSource.getPackageName();
            int uid = attributionSource.getUid();
            int userId = UserHandle.getUserId(uid);
            NotificationManagerService.this.checkCallerIsSameApp(uid, userId, packageName);
            try {
                return NotificationManagerService.this.checkUseFullScreenIntentPermission(attributionSource, NotificationManagerService.this.mPackageManagerClient.getApplicationInfoAsUser(packageName, 268435456, userId), false);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("NotificationService", "Failed to getApplicationInfo() in canUseFullScreenIntent()", e);
                return false;
            }
        }

        public final void cancelAllNotifications(String str, int i) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, false, "cancelAllNotifications", str);
            if (!android.app.Flags.lifetimeExtensionRefactor()) {
                NotificationManagerService.this.cancelAllNotificationsInt(Binder.getCallingUid(), Binder.getCallingPid(), 32832, str, null, handleIncomingUser, 9);
                return;
            }
            NotificationManagerService.this.cancelAllNotificationsInt(Binder.getCallingUid(), Binder.getCallingPid(), 98368, str, null, handleIncomingUser, 9);
            int packageImportanceWithIdentity = NotificationManagerService.this.getPackageImportanceWithIdentity(str);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                NotificationManagerService.m715$$Nest$mmaybeNotifySystemUiListenerLifetimeExtendedListLocked(notificationManagerService, notificationManagerService.mNotificationList, packageImportanceWithIdentity);
                NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                NotificationManagerService.m715$$Nest$mmaybeNotifySystemUiListenerLifetimeExtendedListLocked(notificationManagerService2, notificationManagerService2.mEnqueuedNotifications, packageImportanceWithIdentity);
            }
        }

        public final void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            edgeLightingManager.getClass();
            boolean z = EdgeLightingHistory.IS_DEV_DEBUG;
            String str4 = EdgeLightingManager.TAG;
            if (z || EdgeLightingManager.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m("cancelNotification : pkg= ", str, str4);
            }
            EdgeLightingHistory.getInstance().updateEdgeLightingHistory("cancelNotification pkg=" + str);
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallingPermissionFromHost();
            EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "cancelNotification");
            if (edgeLightingManager.mIStatusBarService == null) {
                edgeLightingManager.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            }
            if (edgeLightingManager.mIStatusBarService == null) {
                Slog.e(str4, "cancelNotification : mIStatusBarService is null.");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    edgeLightingManager.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, 0, 1, true));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Type inference failed for: r10v15 */
        /* JADX WARN: Type inference failed for: r10v16 */
        /* JADX WARN: Type inference failed for: r10v8 */
        /* JADX WARN: Type inference failed for: r10v9, types: [boolean, int] */
        public final void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) {
            long j;
            EdgeLightingPolicyManager.GroupNotificationData groupNotificationData;
            boolean z;
            EdgeLightingPolicyManager.NotificationData notificationData;
            EdgeLightingPolicyManager.NotificationData notificationData2;
            ?? r10;
            int i3;
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            edgeLightingManager.getClass();
            boolean z2 = EdgeLightingHistory.IS_DEV_DEBUG;
            boolean z3 = EdgeLightingManager.DEBUG;
            String str5 = EdgeLightingManager.TAG;
            if (z2 || z3) {
                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "cancelNotificationByGroupKey : cancel notification  uesrid = ", ", id=  ", " , key=  ");
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str3, " , tag = ", str2, " , group = ");
                DeviceIdleController$$ExternalSyntheticOutline0.m(m, str4, str5);
            }
            EdgeLightingHistory.getInstance().updateEdgeLightingHistory("cancelNotificationByGroupKey pkg=" + str);
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallingPermissionFromHost();
            EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "cancelNotificationByGroupKey");
            if (edgeLightingManager.mIStatusBarService == null) {
                edgeLightingManager.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            }
            if (edgeLightingManager.mIStatusBarService == null) {
                Slog.e(str5, "cancelNotificationByGroupKey : mIStatusBarService is null.");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    e = e;
                }
                if (str4 != null) {
                    try {
                        EdgeLightingPolicyManager.NotificationGroup notificationGroup = edgeLightingManager.mEdgeLightingPolicyManager.mNotificationGroup;
                        synchronized (notificationGroup.mGroupMap) {
                            try {
                                groupNotificationData = (EdgeLightingPolicyManager.GroupNotificationData) notificationGroup.mGroupMap.get(str4);
                            } catch (Throwable th) {
                                th = th;
                                while (true) {
                                    try {
                                        throw th;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                }
                            }
                        }
                    } catch (RemoteException e2) {
                        e = e2;
                        j = clearCallingIdentity;
                        e.printStackTrace();
                        Binder.restoreCallingIdentity(j);
                    } catch (Throwable th3) {
                        th = th3;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                    if (groupNotificationData != null) {
                        if (groupNotificationData.mChildMap.size() > 1) {
                            j = clearCallingIdentity;
                            z = true;
                            i3 = 0;
                            r10 = z;
                            edgeLightingManager.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, i3, (int) r10, (boolean) r10));
                            Binder.restoreCallingIdentity(j);
                        }
                        synchronized (groupNotificationData.mChildMap) {
                            try {
                                notificationData = (EdgeLightingPolicyManager.NotificationData) groupNotificationData.mChildMap.get(str3);
                            } catch (Throwable th4) {
                                th = th4;
                                while (true) {
                                    try {
                                        throw th;
                                    } catch (Throwable th5) {
                                        th = th5;
                                    }
                                }
                            }
                        }
                        if (notificationData != null && (notificationData2 = groupNotificationData.mSumaaryNotification) != null) {
                            int i4 = notificationData2.mNotificationInfo.getInt("user_id", 0);
                            int i5 = notificationData2.mNotificationInfo.getInt("noti_id", 0);
                            String string = notificationData2.mNotificationInfo.getString("noti_key", null);
                            String string2 = notificationData2.mNotificationInfo.getString("noti_tag", null);
                            j = clearCallingIdentity;
                            r10 = 1;
                            r10 = 1;
                            i3 = 0;
                            edgeLightingManager.mIStatusBarService.onNotificationClear(str, i4, string, 0, 0, NotificationVisibility.obtain(string, 0, 1, true));
                            if (z2 || z3) {
                                Slog.i(str5, "cancelNotificationByGroupKey : Child count 1. so cancel summary notification : uesrid = " + i4 + ", id=  " + i5 + " , key=  " + string + " , tag = " + string2);
                            }
                            edgeLightingManager.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, i3, (int) r10, (boolean) r10));
                            Binder.restoreCallingIdentity(j);
                        }
                    }
                }
                j = clearCallingIdentity;
                z = true;
                i3 = 0;
                r10 = z;
                edgeLightingManager.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, i3, (int) r10, (boolean) r10));
                Binder.restoreCallingIdentity(j);
            } catch (Throwable th6) {
                th = th6;
            }
        }

        public final void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) {
            Slog.e("NotificationService", "Ignoring deprecated cancelNotification(pkg, tag, id) use cancelNotification(key) instead.");
        }

        public final void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) {
            NotificationManagerService.this.getClass();
            int i3 = NotificationManagerService.isCallingUidSystem() ? 0 : 33856;
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                i3 |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            }
            NotificationManagerService.this.cancelNotificationInternal(Binder.getCallingUid(), Binder.getCallingPid(), i, i2, i3, str, str2, str3);
        }

        /* JADX WARN: Removed duplicated region for block: B:41:0x00bc  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00c2  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void cancelNotificationsFromListener(android.service.notification.INotificationListener r26, java.lang.String[] r27) {
            /*
                Method dump skipped, instructions count: 396
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.cancelNotificationsFromListener(android.service.notification.INotificationListener, java.lang.String[]):void");
        }

        public final void cancelToast(String str, IBinder iBinder) {
            Slog.i("NotificationService", "cancelToast pkg=" + str + " token=" + iBinder);
            if (str == null || iBinder == null) {
                Slog.e("NotificationService", "Not cancelling notification. pkg=" + str + " token=" + iBinder);
                return;
            }
            synchronized (NotificationManagerService.this.mToastQueue) {
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        int indexOfToastLocked = NotificationManagerService.this.indexOfToastLocked(iBinder, str);
                        if (indexOfToastLocked >= 0) {
                            NotificationManagerService.this.cancelToastLocked(indexOfToastLocked);
                        } else {
                            Slog.w("NotificationService", "Toast already cancelled. pkg=" + str + " token=" + iBinder);
                        }
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

        public final boolean checkPolicyAccess(String str) {
            boolean contains;
            try {
                int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, UserHandle.getCallingUserId());
                NotificationManagerService.this.getClass();
                if (ActivityManager.checkComponentPermission("android.permission.MANAGE_NOTIFICATIONS", packageUidAsUser, -1, true) == 0) {
                    return true;
                }
                if (!NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(INotificationManager.Stub.getCallingUserHandle().getIdentifier(), str)) {
                    NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                    synchronized (notificationListeners.mMutex) {
                        contains = notificationListeners.mEnabledServicesPackageNames.contains(str);
                    }
                    if (!contains) {
                        DevicePolicyManagerInternal devicePolicyManagerInternal = NotificationManagerService.this.mDpm;
                        if (devicePolicyManagerInternal == null) {
                            return false;
                        }
                        if (!devicePolicyManagerInternal.isActiveProfileOwner(packageUidAsUser) && !NotificationManagerService.this.mDpm.isActiveDeviceOwner(packageUidAsUser)) {
                            return false;
                        }
                    }
                }
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public final void cleanUpCallersAfter(long j) {
            enforceSystemOrSystemUI("INotificationManager.cleanUpCallersAfter");
            NotificationManagerService.this.mZenModeHelper.mFiltering.getClass();
            ZenModeFiltering.RepeatCallers repeatCallers = ZenModeFiltering.REPEAT_CALLERS;
            synchronized (repeatCallers) {
                try {
                    for (int size = repeatCallers.mTelCalls.size() - 1; size >= 0; size--) {
                        if (((Long) repeatCallers.mTelCalls.valueAt(size)).longValue() > j) {
                            repeatCallers.mTelCalls.removeAt(size);
                        }
                    }
                    for (int size2 = repeatCallers.mOtherCalls.size() - 1; size2 >= 0; size2--) {
                        if (((Long) repeatCallers.mOtherCalls.valueAt(size2)).longValue() > j) {
                            repeatCallers.mOtherCalls.removeAt(size2);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void clearData(String str, int i, boolean z) {
            boolean contains;
            Boolean bool;
            Boolean bool2;
            ArraySet arraySet;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            int userId = UserHandle.getUserId(i);
            NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, str, null, UserHandle.getUserId(Binder.getCallingUid()), 21);
            ConditionProviders conditionProviders = NotificationManagerService.this.mConditionProviders;
            boolean isPackageOrComponentAllowed = conditionProviders.isPackageOrComponentAllowed(userId, str);
            synchronized (conditionProviders.mDefaultsLock) {
                try {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                    contains = unflattenFromString == null ? conditionProviders.mDefaultPackages.contains(str) : conditionProviders.mDefaultComponents.contains(unflattenFromString);
                } finally {
                }
            }
            boolean z2 = contains;
            if (!isPackageOrComponentAllowed && z2) {
                conditionProviders.setPackageOrComponentEnabled(userId, str, true, true, true);
            }
            if (isPackageOrComponentAllowed && !z2) {
                conditionProviders.setPackageOrComponentEnabled(userId, str, true, false, true);
                synchronized (conditionProviders.mApproved) {
                    try {
                        ArrayMap arrayMap = (ArrayMap) conditionProviders.mApproved.get(Integer.valueOf(userId));
                        if (arrayMap != null && (arraySet = (ArraySet) arrayMap.get(Boolean.FALSE)) != null) {
                            String packageName = ManagedServices.getPackageName(str);
                            if (arraySet.contains(packageName)) {
                                arraySet.remove(packageName);
                            }
                        }
                    } finally {
                    }
                }
            }
            boolean z3 = !isPackageOrComponentAllowed && z2;
            ArrayMap resetComponents = NotificationManagerService.this.mListeners.resetComponents(userId, str);
            boolean z4 = z3 | (((ArrayList) resetComponents.get(Boolean.TRUE)).size() > 0 || ((ArrayList) resetComponents.get(Boolean.FALSE)).size() > 0);
            int i2 = 0;
            while (true) {
                bool = Boolean.TRUE;
                if (i2 >= ((ArrayList) resetComponents.get(bool)).size()) {
                    break;
                }
                NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(userId, ((ComponentName) ((ArrayList) resetComponents.get(bool)).get(i2)).getPackageName(), false, true, true);
                i2++;
            }
            ArrayMap resetComponents2 = NotificationManagerService.this.mAssistants.resetComponents(userId, str);
            boolean z5 = z4 | (((ArrayList) resetComponents2.get(bool)).size() > 0 || ((ArrayList) resetComponents2.get(Boolean.FALSE)).size() > 0);
            int i3 = 1;
            while (true) {
                bool2 = Boolean.TRUE;
                if (i3 >= ((ArrayList) resetComponents2.get(bool2)).size()) {
                    break;
                }
                NotificationManagerService.this.mAssistants.setPackageOrComponentEnabled(userId, ((ComponentName) ((ArrayList) resetComponents2.get(bool2)).get(i3)).flattenToString(), true, false, true);
                i3++;
            }
            if (((ArrayList) resetComponents2.get(bool2)).size() > 0) {
                NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(userId, ((ComponentName) ((ArrayList) resetComponents2.get(bool2)).get(0)).getPackageName(), false, true, true);
            }
            SnoozeHelper snoozeHelper = NotificationManagerService.this.mSnoozeHelper;
            int userId2 = UserHandle.getUserId(i);
            synchronized (snoozeHelper.mLock) {
                try {
                    for (int size = snoozeHelper.mSnoozedNotifications.size() - 1; size >= 0; size--) {
                        NotificationRecord notificationRecord = (NotificationRecord) snoozeHelper.mSnoozedNotifications.valueAt(size);
                        if (notificationRecord.sbn.getUserId() == userId2 && notificationRecord.sbn.getPackageName().equals(str)) {
                            snoozeHelper.mSnoozedNotifications.removeAt(size);
                            String trimmedString = SnoozeHelper.getTrimmedString(notificationRecord.sbn.getKey());
                            snoozeHelper.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                            snoozeHelper.mPersistedSnoozedNotifications.remove(trimmedString);
                            snoozeHelper.mAm.cancel(snoozeHelper.createPendingIntent(notificationRecord.sbn.getKey()));
                            MetricsLogger.action(notificationRecord.getLogMaker().setCategory(FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION).setType(5));
                        }
                    }
                } finally {
                }
            }
            if (!z) {
                PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
                synchronized (preferencesHelper.mLock) {
                    try {
                        PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                        if (packagePreferencesLocked != null) {
                            packagePreferencesLocked.channels = new ArrayMap();
                            packagePreferencesLocked.groups = new ArrayMap();
                            packagePreferencesLocked.delegate = null;
                            packagePreferencesLocked.lockedAppFields = 0;
                            packagePreferencesLocked.bubblePreference = 0;
                            packagePreferencesLocked.importance = -1000;
                            packagePreferencesLocked.priority = 0;
                            packagePreferencesLocked.visibility = -1000;
                            packagePreferencesLocked.showBadge = true;
                            packagePreferencesLocked.allowEdgeLighting = true;
                            packagePreferencesLocked.allowSubDisplayNoti = false;
                            List list = preferencesHelper.mOngoingActivityAllowedAppList;
                            if (list == null || !((ArrayList) list).contains(str)) {
                                packagePreferencesLocked.allowOngoingActivity = -1;
                            } else {
                                packagePreferencesLocked.allowOngoingActivity = 1;
                            }
                            packagePreferencesLocked.isNotificationAlertsEnabled = true;
                            if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE && preferencesHelper.mAllowList.contains(packagePreferencesLocked.pkg)) {
                                packagePreferencesLocked.appLockScreenVisibility = 0;
                            } else {
                                packagePreferencesLocked.appLockScreenVisibility = -1000;
                            }
                            packagePreferencesLocked.isAllowPopup = true;
                            packagePreferencesLocked.reminder = false;
                        }
                    } finally {
                    }
                }
            }
            if (z5) {
                NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), UserHandle.of(userId), null);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void clearRequestedListenerHints(INotificationListener iNotificationListener) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.removeDisabledHints(NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener), 0);
                    NotificationManagerService.m723$$Nest$mupdateListenerHintsLocked(NotificationManagerService.this);
                    NotificationManagerService.m721$$Nest$mupdateEffectsSuppressorLocked(NotificationManagerService.this);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int computeZenOrigin(boolean z) {
            if (android.app.Flags.modesApi() && z) {
                return 3;
            }
            return NotificationManagerService.this.isCallerSystemOrSystemUi() ? 5 : 4;
        }

        public final void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) {
            enforceSystemOrSystemUI("only system can call this");
            Preconditions.checkNotNull(notificationChannel);
            Preconditions.checkNotNull(str2);
            String id = notificationChannel.getId();
            notificationChannel.setId(String.format("%1$s : %2$s", id, str2));
            notificationChannel.setConversationId(id, str2);
            createNotificationChannelsImpl(str, i, new ParceledListSlice(Arrays.asList(notificationChannel)), -1);
            ((RankingHandlerWorker) NotificationManagerService.this.mRankingHandler).requestSort();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            List list = parceledListSlice.getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationManagerService.this.createNotificationChannelGroup(str, Binder.getCallingUid(), (NotificationChannelGroup) list.get(i), true, false);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void createNotificationChannels(String str, ParceledListSlice parceledListSlice) {
            int i;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            try {
                i = NotificationManagerService.this.mAtm.getTaskToShowPermissionDialogOn(NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, UserHandle.getUserId(Binder.getCallingUid())), str);
            } catch (RemoteException unused) {
                i = -1;
            }
            createNotificationChannelsImpl(str, Binder.getCallingUid(), parceledListSlice, i);
        }

        public final void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) {
            enforceSystemOrSystemUI("only system can call this");
            createNotificationChannelsImpl(str, i, parceledListSlice, -1);
        }

        public final void createNotificationChannelsImpl(String str, int i, ParceledListSlice parceledListSlice, int i2) {
            List list;
            List list2 = parceledListSlice.getList();
            int size = list2.size();
            ParceledListSlice notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(i, str, true);
            boolean z = (notificationChannels == null || notificationChannels.getList().isEmpty()) ? false : true;
            boolean z2 = false;
            int i3 = 0;
            boolean z3 = false;
            while (i3 < size) {
                NotificationChannel notificationChannel = (NotificationChannel) list2.get(i3);
                Objects.requireNonNull(notificationChannel, "channel in list is null");
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                int i4 = i3;
                boolean createNotificationChannel = notificationManagerService.mPreferencesHelper.createNotificationChannel(str, i, notificationChannel, notificationManagerService.mConditionProviders.isPackageOrComponentAllowed(UserHandle.getUserId(i), str), Binder.getCallingUid(), NotificationManagerService.this.isCallerSystemOrSystemUi());
                if (createNotificationChannel) {
                    NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                    UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
                    PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
                    String id = notificationChannel.getId();
                    preferencesHelper.getClass();
                    list = list2;
                    notificationListeners.notifyNotificationChannelChanged(str, userHandleForUid, preferencesHelper.getConversationNotificationChannel(str, i, id, null, true, false), 1);
                    boolean z4 = z || z3;
                    if (!z4) {
                        ParceledListSlice notificationChannels2 = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(i, str, true);
                        z4 = (notificationChannels2 == null || notificationChannels2.getList().isEmpty()) ? false : true;
                    }
                    if (!z && z4 && !z3 && i2 != -1) {
                        NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                        if (notificationManagerService2.mPermissionPolicyInternal == null) {
                            notificationManagerService2.mPermissionPolicyInternal = (PermissionPolicyService.Internal) LocalServices.getService(PermissionPolicyService.Internal.class);
                        }
                        NotificationManagerService.this.mHandler.post(new ShowNotificationPermissionPromptRunnable(str, UserHandle.getUserId(i), i2, NotificationManagerService.this.mPermissionPolicyInternal));
                        z3 = true;
                    }
                } else {
                    list = list2;
                }
                i3 = i4 + 1;
                z2 = createNotificationChannel;
                list2 = list;
            }
            if (z2) {
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public final void deleteNotificationChannel(String str, String str2) {
            boolean z;
            boolean z2;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int callingUid = Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = NotificationManagerService.this.isCallerSystemOrSystemUi();
            int userId = UserHandle.getUserId(callingUid);
            if ("miscellaneous".equals(str2)) {
                throw new IllegalArgumentException("Cannot delete default channel");
            }
            enforceDeletingChannelHasNoFgService(userId, str, str2);
            enforceDeletingChannelHasNoUserInitiatedJob(userId, str, str2);
            NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, str, str2, userId, 20);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(callingUid, str);
                    z = false;
                    if (packagePreferencesLocked != null) {
                        NotificationChannel notificationChannel = (NotificationChannel) packagePreferencesLocked.channels.get(str2);
                        if (notificationChannel != null) {
                            z = notificationChannel.canBypassDnd();
                            z2 = preferencesHelper.deleteNotificationChannelLocked(str, callingUid, notificationChannel);
                        } else {
                            z2 = false;
                        }
                        if (z) {
                            preferencesHelper.updateCurrentUserHasChannelsBypassingDnd(callingUid, isCallerSystemOrSystemUi);
                        }
                        z = z2;
                    }
                } finally {
                }
            }
            if (z) {
                Archive archive = NotificationManagerService.this.mArchive;
                synchronized (archive.mBufferLock) {
                    try {
                        Iterator descendingIterator = archive.mBuffer.descendingIterator();
                        while (descendingIterator.hasNext()) {
                            Pair pair = (Pair) descendingIterator.next();
                            Object obj = pair.first;
                            if (obj != null && userId == ((StatusBarNotification) obj).getNormalizedUserId() && str != null && str.equals(((StatusBarNotification) pair.first).getPackageName()) && ((StatusBarNotification) pair.first).getNotification() != null && Objects.equals(str2, ((StatusBarNotification) pair.first).getNotification().getChannelId())) {
                                descendingIterator.remove();
                            }
                        }
                    } finally {
                    }
                }
                NotificationHistoryManager notificationHistoryManager = NotificationManagerService.this.mHistoryManager;
                synchronized (notificationHistoryManager.mLock) {
                    try {
                        int userId2 = UserHandle.getUserId(callingUid);
                        NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = notificationHistoryManager.getUserHistoryAndInitializeIfNeededLocked(userId2);
                        if (userHistoryAndInitializeIfNeededLocked == null) {
                            Slog.w("NotificationHistory", "Attempted to remove channel for locked/gone/disabled user " + userId2);
                        } else {
                            userHistoryAndInitializeIfNeededLocked.mFileWriteHandler.post(userHistoryAndInitializeIfNeededLocked.new RemoveChannelRunnable(str, str2));
                        }
                    } finally {
                    }
                }
                NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(callingUid);
                PreferencesHelper preferencesHelper2 = NotificationManagerService.this.mPreferencesHelper;
                preferencesHelper2.getClass();
                Objects.requireNonNull(str);
                notificationListeners.notifyNotificationChannelChanged(str, userHandleForUid, preferencesHelper2.getConversationNotificationChannel(str, callingUid, str2, null, true, true), 3);
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v1 */
        /* JADX WARN: Type inference failed for: r11v2, types: [int] */
        /* JADX WARN: Type inference failed for: r11v4 */
        /* JADX WARN: Type inference failed for: r11v5, types: [int] */
        /* JADX WARN: Type inference failed for: r11v7 */
        /* JADX WARN: Type inference failed for: r11v8 */
        /* JADX WARN: Type inference failed for: r4v2, types: [android.util.ArrayMap] */
        /* JADX WARN: Type inference failed for: r9v1 */
        /* JADX WARN: Type inference failed for: r9v2, types: [java.util.ArrayList] */
        /* JADX WARN: Type inference failed for: r9v7 */
        /* JADX WARN: Type inference failed for: r9v8 */
        public final void deleteNotificationChannelGroup(String str, String str2) {
            int i;
            NotificationChannelGroup notificationChannelGroup;
            boolean z;
            ?? r9;
            Object obj;
            ArrayList arrayList;
            PreferencesHelper preferencesHelper;
            PreferencesHelper.PackagePreferences packagePreferences;
            ArrayList arrayList2;
            PreferencesHelper preferencesHelper2;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int callingUid = Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = NotificationManagerService.this.isCallerSystemOrSystemUi();
            NotificationChannelGroup notificationChannelGroupWithChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(callingUid, str, str2, false);
            if (notificationChannelGroupWithChannels != null) {
                int userId = UserHandle.getUserId(callingUid);
                List<NotificationChannel> channels = notificationChannelGroupWithChannels.getChannels();
                for (int i2 = 0; i2 < channels.size(); i2++) {
                    String id = channels.get(i2).getId();
                    enforceDeletingChannelHasNoFgService(userId, str, id);
                    enforceDeletingChannelHasNoUserInitiatedJob(userId, str, id);
                }
                PreferencesHelper preferencesHelper3 = NotificationManagerService.this.mPreferencesHelper;
                preferencesHelper3.getClass();
                ArrayList arrayList3 = new ArrayList();
                Object obj2 = preferencesHelper3.mLock;
                synchronized (obj2) {
                    try {
                        try {
                            PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper3.getPackagePreferencesLocked(callingUid, str);
                            if (packagePreferencesLocked == null || TextUtils.isEmpty(str2)) {
                                i = userId;
                                notificationChannelGroup = notificationChannelGroupWithChannels;
                                z = false;
                                r9 = arrayList3;
                            } else {
                                NotificationChannelGroup notificationChannelGroup2 = (NotificationChannelGroup) packagePreferencesLocked.groups.remove(str2);
                                if (notificationChannelGroup2 != null) {
                                    preferencesHelper3.mNotificationChannelLogger.getClass();
                                    obj = obj2;
                                    arrayList = arrayList3;
                                    preferencesHelper = preferencesHelper3;
                                    i = userId;
                                    notificationChannelGroup = notificationChannelGroupWithChannels;
                                    z = false;
                                    FrameworkStatsLog.write(FrameworkStatsLog.NOTIFICATION_CHANNEL_MODIFIED, NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_GROUP_DELETED.getId(), callingUid, str, Math.floorMod(Objects.hashCode(notificationChannelGroup2.getId()), 8192), 3, notificationChannelGroup2.isBlocked() ? 0 : 3, false, 0, false, false);
                                    packagePreferences = packagePreferencesLocked;
                                } else {
                                    obj = obj2;
                                    arrayList = arrayList3;
                                    preferencesHelper = preferencesHelper3;
                                    i = userId;
                                    notificationChannelGroup = notificationChannelGroupWithChannels;
                                    z = false;
                                    packagePreferences = packagePreferencesLocked;
                                }
                                int size = packagePreferences.channels.size();
                                boolean z2 = z;
                                for (?? r11 = z2; r11 < size; r11++) {
                                    NotificationChannel notificationChannel = (NotificationChannel) packagePreferences.channels.valueAt(r11);
                                    if (str2.equals(notificationChannel.getGroup())) {
                                        z2 |= notificationChannel.canBypassDnd();
                                        preferencesHelper2 = preferencesHelper;
                                        preferencesHelper2.deleteNotificationChannelLocked(str, callingUid, notificationChannel);
                                        arrayList2 = arrayList;
                                        arrayList2.add(notificationChannel);
                                    } else {
                                        arrayList2 = arrayList;
                                        preferencesHelper2 = preferencesHelper;
                                    }
                                    preferencesHelper = preferencesHelper2;
                                    arrayList = arrayList2;
                                }
                                ArrayList arrayList4 = arrayList;
                                PreferencesHelper preferencesHelper4 = preferencesHelper;
                                r9 = arrayList4;
                                if (z2) {
                                    preferencesHelper4.updateCurrentUserHasChannelsBypassingDnd(callingUid, isCallerSystemOrSystemUi);
                                    r9 = arrayList4;
                                }
                            }
                            for (?? r112 = z; r112 < r9.size(); r112++) {
                                NotificationChannel notificationChannel2 = (NotificationChannel) r9.get(r112);
                                NotificationManagerService.this.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, str, notificationChannel2.getId(), i, 20);
                                NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, UserHandle.getUserHandleForUid(callingUid), notificationChannel2, 3);
                            }
                            NotificationManagerService.this.mListeners.notifyNotificationChannelGroupChanged(str, UserHandle.getUserHandleForUid(callingUid), notificationChannelGroup, 3);
                            NotificationManagerService.this.handleSavePolicyFile();
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

        public final void deleteNotificationHistoryItem(String str, int i, long j) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationHistoryManager notificationHistoryManager = NotificationManagerService.this.mHistoryManager;
            synchronized (notificationHistoryManager.mLock) {
                try {
                    int userId = UserHandle.getUserId(i);
                    NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = notificationHistoryManager.getUserHistoryAndInitializeIfNeededLocked(userId);
                    if (userHistoryAndInitializeIfNeededLocked != null) {
                        userHistoryAndInitializeIfNeededLocked.mFileWriteHandler.post(userHistoryAndInitializeIfNeededLocked.new RemoveNotificationRunnable(str, j));
                    } else {
                        Slog.w("NotificationHistory", "Attempted to remove notif for locked/gone/disabled user " + userId);
                    }
                } finally {
                }
            }
        }

        public final void disable(int i, String str, IBinder iBinder) {
            if (i == 0 || i == 1) {
                EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
                EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
                securityPolicy.enforceCallFromPackage(str);
                EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "disableEdgeLighting");
                EdgeLightingPolicyManager edgeLightingPolicyManager = edgeLightingManager.mEdgeLightingPolicyManager;
                synchronized (edgeLightingPolicyManager.mDisableRecords) {
                    edgeLightingPolicyManager.manageDisableRecoredLocked(i, iBinder, str);
                }
            }
        }

        public final void disableEdgeLightingNotification(String str, boolean z) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            edgeLightingManager.mSecurityPolicy.enforceCallFromPackage(str);
            EdgeLightingPolicyManager edgeLightingPolicyManager = edgeLightingManager.mEdgeLightingPolicyManager;
            synchronized (edgeLightingPolicyManager.mDisabledPackages) {
                try {
                    ArraySet arraySet = (ArraySet) edgeLightingPolicyManager.mDisabledPackages.get(1);
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                        edgeLightingPolicyManager.mDisabledPackages.put(1, arraySet);
                    }
                    if (z) {
                        arraySet.add(str);
                    } else {
                        arraySet.remove(str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) {
            if (str2.equals("android")) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                notificationManagerService.getClass();
                Slog.d("NotificationService", "     WAKELOCK/UP clear all delayed lists..");
                notificationManagerService.mDelayedWakelockList.clear();
                notificationManagerService.mDelayedWakeUpList.clear();
                return false;
            }
            if (Settings.System.getInt(NotificationManagerService.this.getContext().getContentResolver(), "edge_lighting", 1) != 1) {
                return false;
            }
            try {
                if (!isEdgeLightingAllowed(str2, NotificationManagerService.this.mPackageManagerClient.getPackageUid(str2, 0))) {
                    return false;
                }
                if (NotificationManagerService.m710$$Nest$mhasFollowedNotification(NotificationManagerService.this, str2)) {
                    Slog.d("NotificationService", "     WAKEUP canceled by edgelighting notification - B : ".concat(str2));
                    return false;
                }
                String m = VpnManagerService$$ExternalSyntheticOutline0.m(i, str, str2);
                if (NotificationManagerService.this.mDelayedWakeUpList.contains(m)) {
                    Slog.d("NotificationService", "     WAKEUP acquired : ".concat(str2));
                    NotificationManagerService.this.mDelayedWakeUpList.remove(m);
                    return false;
                }
                Slog.d("NotificationService", "     WAKEUP isDelayed by notification : ".concat(str2));
                NotificationManagerService.this.mDelayedWakeUpList.add(m);
                NotificationManagerService.this.mHandler.postDelayed(new NotificationManagerService$$ExternalSyntheticLambda16(this, i, str), m, 500L);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("NotificationService", "Package " + str2 + " not found.", e);
                return false;
            }
        }

        public final boolean dispatchDelayedWakelockAndBlocked(final int i, final String str, final String str2, final int i2) {
            int i3;
            boolean z;
            CoverState coverState;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("dispatchDelayedWakelockAndBlocked : ", str2, "NotificationService");
            if (NotificationManagerService.this.mIsFactoryBinary || str2.equals("com.sec.factory") || str2.equals("com.sec.facatfunction")) {
                Slog.d("NotificationService", "fatory mode");
                return false;
            }
            if (str2.equals("android")) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                notificationManagerService.getClass();
                Slog.d("NotificationService", "     WAKELOCK/UP clear all delayed lists..");
                notificationManagerService.mDelayedWakelockList.clear();
                notificationManagerService.mDelayedWakeUpList.clear();
                return false;
            }
            NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
            if (notificationManagerService2.mCoverManager == null) {
                notificationManagerService2.mCoverManager = new CoverManager(NotificationManagerService.this.getContext());
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
                if (i3 != 17) {
                    Slog.e("NotificationService", "cover is closed and it is not a clear view cover, so don't delay wakelock");
                    return false;
                }
            } else {
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
                    return false;
                }
            }
            if (NotificationManagerService.m710$$Nest$mhasFollowedNotification(NotificationManagerService.this, str2)) {
                Slog.d("NotificationService", "     WAKELOCK canceled by edgelighting notification - B : ".concat(str2));
                return false;
            }
            String m = VpnManagerService$$ExternalSyntheticOutline0.m(i, str, str2);
            if (NotificationManagerService.this.mDelayedWakelockList.contains(m)) {
                Slog.d("NotificationService", "     WAKELOCK acquired : ".concat(str2));
                NotificationManagerService.this.mDelayedWakelockList.remove(m);
                return false;
            }
            Slog.d("NotificationService", "     WAKELOCK isDelayed by edgelighting : ".concat(str2));
            NotificationManagerService.this.mDelayedWakelockList.add(m);
            NotificationManagerService.this.mHandler.post(new AnonymousClass11(6, this));
            NotificationManagerService.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService.AnonymousClass16 anonymousClass16 = NotificationManagerService.AnonymousClass16.this;
                    int i4 = i;
                    String str3 = str;
                    int i5 = i2;
                    String str4 = str2;
                    anonymousClass16.getClass();
                    try {
                        PowerManager.WakeLock newWakeLock = ((PowerManager) NotificationManagerService.this.getContext().getSystemService(PowerManager.class)).newWakeLock(i4, "EDGELIGHTING:" + str3);
                        newWakeLock.setWorkSource(new WorkSource(i5, str4));
                        newWakeLock.acquire();
                        newWakeLock.release();
                    } catch (IllegalArgumentException unused) {
                        NandswapManager$$ExternalSyntheticOutline0.m(i4, " invalid wakelock flag : ", "NotificationService");
                    }
                }
            }, m, 500L);
            return true;
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            EdgeLightingManager edgeLightingManager;
            if (DumpUtils.checkDumpAndUsageStatsPermission(NotificationManagerService.this.getContext(), "NotificationService", printWriter)) {
                DumpFilter dumpFilter = new DumpFilter();
                dumpFilter.filtered = false;
                dumpFilter.redact = true;
                dumpFilter.proto = false;
                dumpFilter.criticalPriority = false;
                dumpFilter.normalPriority = false;
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
                        str2.getClass();
                        if (str2.equals("NORMAL")) {
                            dumpFilter.normalPriority = true;
                        } else if (str2.equals("CRITICAL")) {
                            dumpFilter.criticalPriority = true;
                        }
                    }
                    i++;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ArrayMap allUsersNotificationPermissions = NotificationManagerService.this.getAllUsersNotificationPermissions();
                    if (dumpFilter.stats) {
                        NotificationManagerService.m701$$Nest$mdumpJson(NotificationManagerService.this, printWriter, dumpFilter, allUsersNotificationPermissions);
                    } else if (dumpFilter.rvStats) {
                        NotificationManagerService.m703$$Nest$mdumpRemoteViewStats(NotificationManagerService.this, printWriter, dumpFilter);
                    } else if (dumpFilter.proto) {
                        NotificationManagerService.m702$$Nest$mdumpProto(NotificationManagerService.this, fileDescriptor, dumpFilter, allUsersNotificationPermissions);
                    } else if (dumpFilter.criticalPriority) {
                        NotificationManagerService.this.dumpNotificationRecords(printWriter, dumpFilter);
                    } else {
                        NotificationManagerService.this.dumpImpl(printWriter, dumpFilter, allUsersNotificationPermissions);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (dumpFilter.proto || (edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager) == null) {
                        return;
                    }
                    printWriter.println("[EdgeLightingManager]");
                    EdgeLightingClientManager edgeLightingClientManager = edgeLightingManager.mEdgeLightingClientManager;
                    edgeLightingClientManager.getClass();
                    printWriter.println("-ClientManager start");
                    synchronized (edgeLightingClientManager.mHosts) {
                        try {
                            Iterator it = edgeLightingClientManager.mHosts.iterator();
                            while (it.hasNext()) {
                                printWriter.println((EdgeLightingClientManager.EdgeLightingHost) it.next());
                            }
                        } finally {
                        }
                    }
                    printWriter.println("");
                    if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
                        synchronized (edgeLightingClientManager.mEdgeLightingList) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append("-EdgeLightingList : [");
                                int size = ((ArrayList) edgeLightingClientManager.mEdgeLightingList).size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    sb.append((String) ((ArrayList) edgeLightingClientManager.mEdgeLightingList).get(i2));
                                    if (i2 != size - 1) {
                                        sb.append(", ");
                                    }
                                }
                                sb.append(']');
                                printWriter.println(sb);
                                printWriter.println("");
                            } finally {
                            }
                        }
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("mCondition="), edgeLightingClientManager.mCondition, printWriter);
                    EdgeLightingListenerManager edgeLightingListenerManager = edgeLightingClientManager.mEdgeLightingListenerManager;
                    edgeLightingListenerManager.getClass();
                    printWriter.println("-ListenerManager start");
                    synchronized (edgeLightingListenerManager.mListeners) {
                        try {
                            Iterator it2 = edgeLightingListenerManager.mListeners.iterator();
                            while (it2.hasNext()) {
                                printWriter.println((EdgeLightingListenerManager.EdgeLightingListener) it2.next());
                            }
                        } finally {
                        }
                    }
                    printWriter.println("");
                    edgeLightingManager.mEdgeLightingPolicyManager.dump(printWriter, strArr);
                    EdgeLightingHistory edgeLightingHistory = EdgeLightingHistory.getInstance();
                    synchronized (edgeLightingHistory.mLock) {
                        try {
                            printWriter.println("-EdgeLightingHistory");
                            printWriter.println("  [Host History] :");
                            Iterator it3 = edgeLightingHistory.mHostHistory.iterator();
                            while (it3.hasNext()) {
                                String str3 = (String) it3.next();
                                if (str3 != null) {
                                    printWriter.println("   " + str3);
                                }
                            }
                            printWriter.println("");
                            printWriter.println("  [Listener History] :");
                            Iterator it4 = edgeLightingHistory.mListenerHistory.iterator();
                            while (it4.hasNext()) {
                                String str4 = (String) it4.next();
                                if (str4 != null) {
                                    printWriter.println("   " + str4);
                                }
                            }
                            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingHistory.DEBUG) {
                                printWriter.println("");
                                printWriter.println("  [EL History] :");
                                Iterator it5 = edgeLightingHistory.mEdgeLightingHistory.iterator();
                                while (it5.hasNext()) {
                                    String str5 = (String) it5.next();
                                    if (str5 != null) {
                                        printWriter.println("   " + str5);
                                    }
                                }
                                printWriter.println("");
                                printWriter.println("  [Event History] :");
                                Iterator it6 = edgeLightingHistory.mEventHistory.iterator();
                                while (it6.hasNext()) {
                                    String str6 = (String) it6.next();
                                    if (str6 != null) {
                                        printWriter.println("   " + str6);
                                    }
                                }
                                printWriter.println("");
                                printWriter.println("  [Reject History] :");
                                Iterator it7 = edgeLightingHistory.mRejectHistory.iterator();
                                while (it7.hasNext()) {
                                    String str7 = (String) it7.next();
                                    if (str7 != null) {
                                        printWriter.println("   " + str7);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void enforceDeletingChannelHasNoFgService(int i, String str, String str2) {
            if (NotificationManagerService.this.mAmi.hasForegroundServiceNotification(str, i, str2)) {
                StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Package u", "/", str, " may not delete notification channel '");
                m.append(str2);
                m.append("' with fg service");
                Slog.w("NotificationService", m.toString());
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Not allowed to delete channel ", str2, " with a foreground service"));
            }
        }

        public final void enforcePolicyAccess(int i, String str) {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
                return;
            }
            boolean z = false;
            for (String str2 : NotificationManagerService.this.mPackageManagerClient.getPackagesForUid(i)) {
                if (NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(UserHandle.getUserId(i), str2)) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            Slog.w("NotificationService", "Notification policy access denied calling ".concat(str));
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
            Slog.w("NotificationService", "Notification policy access denied calling ".concat(str2));
            throw new SecurityException("Notification policy access denied");
        }

        public final void enforceSystemOrSystemUI(String str) {
            NotificationManagerService.this.getClass();
            if (NotificationManagerService.isCallerSystemOrPhone()) {
                return;
            }
            NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
        }

        public final void enforceSystemOrSystemUIOrSamePackage(String str, String str2) {
            try {
                NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            } catch (SecurityException unused) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str2);
            }
        }

        public final void enforceUserOriginOnlyFromSystem(String str, boolean z) {
            if (android.app.Flags.modesApi() && z && !NotificationManagerService.m711$$Nest$misCallerSystemOrSystemUiOrShell(NotificationManagerService.this)) {
                throw new SecurityException(TextUtils.formatSimple("Calling %s with fromUser == true is only allowed for system", new Object[]{str}));
            }
        }

        public final void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            notificationManagerService.getClass();
            if (NotificationManagerService.DBG) {
                StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "enqueueEdgeNotificationInternal: pkg=", str, " id=", " extra=");
                m.append(bundle);
                Slog.v("NotificationService", m.toString());
            }
            int handleIncomingUser = ActivityManager.handleIncomingUser(callingPid, callingUid, i2, true, false, "enqueueNotificationExtra", str);
            new UserHandle(handleIncomingUser);
            if (str != null && bundle != null) {
                notificationManagerService.mHandler.post(new AnonymousClass18(notificationManagerService, str, i, bundle, handleIncomingUser, 0));
            } else {
                StringBuilder m2 = StorageManagerService$$ExternalSyntheticOutline0.m(i, "null not allowed: pkg=", str, " id=", " notification=");
                m2.append(bundle);
                throw new IllegalArgumentException(m2.toString());
            }
        }

        public final void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) {
            if (NmRune.NM_SUPPORT_PUSH_SERVICE) {
                NotificationManagerService.this.getClass();
                if (NotificationManagerService.isCallerSystemOrPhone() && str2 != null && "com.samsung.android.pushservice".equals(str2) && !str2.equals(str)) {
                    try {
                        NotificationManagerService.this.enqueueNotificationInternal(str, str, NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i2), Binder.getCallingPid(), str3, i, notification, i2, false, false);
                        return;
                    } catch (PackageManager.NameNotFoundException e) {
                        Slog.e("NotificationService", "Cannot get a uid for target package", e);
                        return;
                    }
                }
            }
            NotificationManagerService.this.enqueueNotificationInternal(str, str2, Binder.getCallingUid(), Binder.getCallingPid(), str3, i, notification, i2, false, false);
        }

        public final boolean enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
            return enqueueToastForDex(str, iBinder, null, null, i, z, i2, null, null, -1);
        }

        public final void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) {
            enqueueToastForDex(str, iBinder, charSequence, null, i, z, i2, iTransientNotificationCallback, str2, i3);
        }

        public final boolean enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) {
            return enqueueToastForDex(str, iBinder, null, iTransientNotification, i, z, i2, null, null, -1);
        }

        public final void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) {
            enqueueToastForDex(str, iBinder, null, iTransientNotification, i, z, i2, null, str2, i3);
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x0122, code lost:
        
            if (r4.mPackageManager.checkPermission("android.permission.UNLIMITED_TOASTS", r25, r2) == 0) goto L60;
         */
        /* JADX WARN: Removed duplicated region for block: B:72:0x01a1  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0212 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean enqueueToastForDex(java.lang.String r25, android.os.IBinder r26, java.lang.CharSequence r27, android.app.ITransientNotification r28, int r29, boolean r30, int r31, android.app.ITransientNotificationCallback r32, java.lang.String r33, int r34) {
            /*
                Method dump skipped, instructions count: 903
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.enqueueToastForDex(java.lang.String, android.os.IBinder, java.lang.CharSequence, android.app.ITransientNotification, int, boolean, int, android.app.ITransientNotificationCallback, java.lang.String, int):boolean");
        }

        public final void finishToken(String str, IBinder iBinder) {
            synchronized (NotificationManagerService.this.mToastQueue) {
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        int indexOfToastLocked = NotificationManagerService.this.indexOfToastLocked(iBinder, str);
                        if (indexOfToastLocked >= 0) {
                            ToastRecord toastRecord = (ToastRecord) NotificationManagerService.this.mToastQueue.get(indexOfToastLocked);
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            Binder binder = toastRecord.windowToken;
                            int i = toastRecord.displayId;
                            notificationManagerService.mHandler.removeCallbacksAndMessages(binder);
                            notificationManagerService.mWindowManagerInternal.removeWindowToken(binder, true, i);
                        } else {
                            Slog.w("NotificationService", "Toast already killed. pkg=" + str + " token=" + iBinder);
                        }
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

        public final StatusBarNotification[] getActiveNotifications(String str) {
            return getActiveNotificationsWithAttribution(str, null);
        }

        public final ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) {
            ParceledListSlice parceledListSlice;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    boolean z = strArr != null;
                    int length = z ? strArr.length : NotificationManagerService.this.mNotificationList.size();
                    ArrayList arrayList = new ArrayList(length);
                    for (int i2 = 0; i2 < length; i2++) {
                        addToListIfNeeded(z ? (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(strArr[i2]) : (NotificationRecord) NotificationManagerService.this.mNotificationList.get(i2), checkServiceTokenLocked, arrayList, i);
                    }
                    parceledListSlice = new ParceledListSlice(arrayList);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        public final StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) {
            getActiveNotificationsWithAttribution_enforcePermission();
            ArrayList arrayList = new ArrayList();
            int callingUid = Binder.getCallingUid();
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(-1);
            Binder.withCleanCallingIdentity(new NotificationManagerService$$ExternalSyntheticLambda10(1, this, arrayList2));
            int noteOpNoThrow = NotificationManagerService.this.mAppOps.noteOpNoThrow(25, callingUid, str, str2, (String) null);
            if (noteOpNoThrow == 0 || noteOpNoThrow == 3) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        int size = NotificationManagerService.this.mNotificationList.size();
                        for (int i = 0; i < size; i++) {
                            StatusBarNotification statusBarNotification = ((NotificationRecord) NotificationManagerService.this.mNotificationList.get(i)).sbn;
                            if (arrayList2.contains(Integer.valueOf(statusBarNotification.getUserId()))) {
                                arrayList.add(statusBarNotification);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return (StatusBarNotification[]) arrayList.toArray(new StatusBarNotification[arrayList.size()]);
        }

        public final int getAllNotificationListenersCount() {
            NotificationManagerService.this.checkNotificationListenerAccess$1();
            return ((ArrayList) NotificationManagerService.this.mListeners.getServices()).size();
        }

        public final List getAllowedAssistantAdjustments(String str) {
            ArrayList arrayList;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            NotificationManagerService.this.getClass();
            if (!NotificationManagerService.isCallerSystemOrPhone() && !NotificationManagerService.this.mAssistants.isPackageAllowed(UserHandle.getCallingUserId(), str)) {
                throw new SecurityException("Not currently an assistant");
            }
            NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
            synchronized (notificationAssistants.mLock) {
                arrayList = new ArrayList();
                arrayList.addAll(notificationAssistants.mAllowedAdjustments);
            }
            return arrayList;
        }

        public final ComponentName getAllowedNotificationAssistant() {
            return getAllowedNotificationAssistantForUser(INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public final ComponentName getAllowedNotificationAssistantForUser(int i) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell(null);
            List allowedComponents = NotificationManagerService.this.mAssistants.getAllowedComponents(i);
            ArrayList arrayList = (ArrayList) allowedComponents;
            if (arrayList.size() <= 1) {
                return (ComponentName) CollectionUtils.firstOrNull(allowedComponents);
            }
            throw new IllegalStateException("At most one NotificationAssistant: " + arrayList.size());
        }

        public final List getAllowedOngoingActivityAppList() {
            enforceSystemOrSystemUI("getAllowedOngoingActivityAppList");
            Slog.d("NotificationService", "getAllowedOngoingActivityAppList - size = " + NotificationManagerService.this.mOngoingActivityAppList.size());
            return NotificationManagerService.this.mOngoingActivityAppList;
        }

        public final ParceledListSlice getAppActiveNotifications(String str, int i) {
            ParceledListSlice parceledListSlice;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, false, "getAppActiveNotifications", str);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    ArrayMap arrayMap = new ArrayMap(NotificationManagerService.this.mNotificationList.size() + NotificationManagerService.this.mEnqueuedNotifications.size());
                    int size = NotificationManagerService.this.mNotificationList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        StatusBarNotification sanitizeSbn = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) NotificationManagerService.this.mNotificationList.get(i2)).sbn);
                        if (sanitizeSbn != null) {
                            arrayMap.put(sanitizeSbn.getKey(), sanitizeSbn);
                        }
                    }
                    Iterator it = ((ArrayList) NotificationManagerService.this.mSnoozeHelper.getSnoozed(handleIncomingUser, str)).iterator();
                    while (it.hasNext()) {
                        StatusBarNotification sanitizeSbn2 = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) it.next()).sbn);
                        if (sanitizeSbn2 != null) {
                            arrayMap.put(sanitizeSbn2.getKey(), sanitizeSbn2);
                        }
                    }
                    int size2 = NotificationManagerService.this.mEnqueuedNotifications.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        StatusBarNotification sanitizeSbn3 = sanitizeSbn(str, handleIncomingUser, ((NotificationRecord) NotificationManagerService.this.mEnqueuedNotifications.get(i3)).sbn);
                        if (sanitizeSbn3 != null) {
                            arrayMap.put(sanitizeSbn3.getKey(), sanitizeSbn3);
                        }
                    }
                    ArrayList arrayList = new ArrayList(arrayMap.size());
                    arrayList.addAll(arrayMap.values());
                    parceledListSlice = new ParceledListSlice(arrayList);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        public final int getAppNotificationSettingStatus(String str) {
            Slog.d("NotificationService", "getAppNotificationSettingStatus");
            if (!NotificationManagerService.m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService.this)) {
                return -1;
            }
            boolean hasPostNotificationPermission = hasPostNotificationPermission(str);
            ParceledListSlice notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(0, str, true);
            boolean z = (notificationChannels == null || notificationChannels.getList().isEmpty()) ? false : true;
            int i = getZenMode() != 0 ? 1048576 : 0;
            if (!hasPostNotificationPermission) {
                i += 16777216;
            }
            if (!areNotificationsEnabledForPackage(str, 0)) {
                i += 4194304;
            }
            return !z ? i + 8388608 : i;
        }

        public final int getAppsBypassingDndCount(int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getAppsBypassingDndCount(i);
        }

        public final AutomaticZenRule getAutomaticZenRule(String str) {
            Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(Binder.getCallingUid(), "getAutomaticZenRule");
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    if (zenModeConfig == null) {
                        return null;
                    }
                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(str);
                    if (zenRule != null && zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                        return zenModeHelper.zenRuleToAutomaticZenRule(zenRule);
                    }
                    return null;
                } finally {
                }
            }
        }

        public final int getAutomaticZenRuleState(String str) {
            int i;
            Objects.requireNonNull(str, "id is null");
            enforcePolicyAccess(Binder.getCallingUid(), "getAutomaticZenRuleState");
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    i = 2;
                    if (zenModeConfig != null) {
                        ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(str);
                        if (zenRule != null && zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                            Condition condition = zenRule.condition;
                            i = condition != null ? condition.state : 0;
                        }
                    }
                } finally {
                }
            }
            return i;
        }

        public final Map getAutomaticZenRules() {
            if (!android.app.Flags.modesApi()) {
                throw new IllegalStateException("getAutomaticZenRules called with flag off!");
            }
            enforcePolicyAccess(Binder.getCallingUid(), "getAutomaticZenRules");
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            ArrayList arrayList = (ArrayList) zenModeHelper.getZenRules();
            HashMap hashMap = new HashMap(arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) it.next();
                hashMap.put(zenRule.id, zenModeHelper.zenRuleToAutomaticZenRule(zenRule));
            }
            return hashMap;
        }

        public final byte[] getBackupPayload(int i) {
            if (!NotificationManagerService.m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService.this)) {
                NotificationManagerService.this.getClass();
                NotificationManagerService.checkCallerIsSystem();
            }
            Binder.clearCallingIdentity();
            if (NotificationManagerService.DBG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "getBackupPayload u=", "NotificationService");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                NotificationManagerService.m726$$Nest$mwritePolicyXml(NotificationManagerService.this, byteArrayOutputStream, true, i);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                Slog.w("NotificationService", "getBackupPayload: error writing payload for user " + i, e);
                return null;
            }
        }

        public final List getBlockInfoOfNotificationsForOverflow(String str) {
            ArrayList arrayList = new ArrayList();
            Iterator it = NotificationManagerService.this.mLimitNotificationsForOverflowAppList.iterator();
            while (it.hasNext()) {
                String[] split = ((String) it.next()).split(",");
                if (split.length >= 2 && split[0].equals(str)) {
                    arrayList.add(split[1]);
                }
            }
            return arrayList;
        }

        public final int getBlockedAppCount(int i) {
            int i2;
            Pair pair;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            new ArrayMap();
            ArrayMap notificationPermissionValues = preferencesHelper.mPermissionHelper.getNotificationPermissionValues(i);
            synchronized (preferencesHelper.mLock) {
                try {
                    int size = preferencesHelper.mPackagePreferences.size();
                    i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i3);
                        if (!notificationPermissionValues.isEmpty() && (pair = (Pair) notificationPermissionValues.get(new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg))) != null && !((Boolean) pair.first).booleanValue()) {
                            i2++;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "getBlockedAppCount = ", "NotificationPrefHelper");
            return i2;
        }

        public final int getBlockedChannelCount(String str, int i) {
            enforceSystemOrSystemUI("getBlockedChannelCount");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            Objects.requireNonNull(str);
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked == null) {
                        return 0;
                    }
                    int size = packagePreferencesLocked.channels.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        NotificationChannel notificationChannel = (NotificationChannel) packagePreferencesLocked.channels.valueAt(i3);
                        if (!notificationChannel.isDeleted() && notificationChannel.getImportance() == 0) {
                            i2++;
                        }
                    }
                    return i2;
                } finally {
                }
            }
        }

        public final int getBubblePreferenceForPackage(String str, int i) {
            int i2;
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            if (UserHandle.getCallingUserId() != UserHandle.getUserId(i)) {
                NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "getBubblePreferenceForPackage for uid " + i);
            }
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                i2 = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).bubblePreference;
            }
            return i2;
        }

        public final NotificationManager.Policy getConsolidatedNotificationPolicy() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mZenModeHelper.mConsolidatedPolicy.copy();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) {
            int i2;
            if (!canNotifyAsPackage(str, str2, i) && !NotificationManagerService.m711$$Nest$misCallerSystemOrSystemUiOrShell(NotificationManagerService.this)) {
                StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Pkg ", str, " cannot read channels for ", str2, " in ");
                m.append(i);
                throw new SecurityException(m.toString());
            }
            try {
                i2 = NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
            } catch (PackageManager.NameNotFoundException unused) {
                i2 = -1;
            }
            return NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str2, i2, str3, str4, z, false);
        }

        public final ParceledListSlice getConversations(boolean z) {
            ArrayList arrayList;
            NotificationChannelGroup notificationChannelGroup;
            enforceSystemOrSystemUI("getConversations");
            IntArray currentProfileIds = NotificationManagerService.this.mUserProfiles.getCurrentProfileIds();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    arrayList = new ArrayList();
                    for (PreferencesHelper.PackagePreferences packagePreferences : preferencesHelper.mPackagePreferences.values()) {
                        if (currentProfileIds.binarySearch(UserHandle.getUserId(packagePreferences.uid)) >= 0) {
                            int size = packagePreferences.channels.size();
                            for (int i = 0; i < size; i++) {
                                NotificationChannel notificationChannel = (NotificationChannel) packagePreferences.channels.valueAt(i);
                                if (!TextUtils.isEmpty(notificationChannel.getConversationId()) && !notificationChannel.isDeleted() && !notificationChannel.isDemoted() && (notificationChannel.isImportantConversation() || !z)) {
                                    ConversationChannelWrapper conversationChannelWrapper = new ConversationChannelWrapper();
                                    conversationChannelWrapper.setPkg(packagePreferences.pkg);
                                    conversationChannelWrapper.setUid(packagePreferences.uid);
                                    conversationChannelWrapper.setNotificationChannel(notificationChannel);
                                    NotificationChannel notificationChannel2 = (NotificationChannel) packagePreferences.channels.get(notificationChannel.getParentChannelId());
                                    conversationChannelWrapper.setParentChannelLabel(notificationChannel2 == null ? null : notificationChannel2.getName());
                                    if (notificationChannel.getGroup() != null && (notificationChannelGroup = (NotificationChannelGroup) packagePreferences.groups.get(notificationChannel.getGroup())) != null) {
                                        if (!notificationChannelGroup.isBlocked()) {
                                            conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
                                        }
                                    }
                                    arrayList.add(conversationChannelWrapper);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConversationChannelWrapper conversationChannelWrapper2 = (ConversationChannelWrapper) it.next();
                ShortcutHelper shortcutHelper = NotificationManagerService.this.mShortcutHelper;
                if (shortcutHelper == null) {
                    conversationChannelWrapper2.setShortcutInfo((ShortcutInfo) null);
                } else {
                    conversationChannelWrapper2.setShortcutInfo(shortcutHelper.getValidShortcutInfo(conversationChannelWrapper2.getNotificationChannel().getConversationId(), UserHandle.of(UserHandle.getUserId(conversationChannelWrapper2.getUid())), conversationChannelWrapper2.getPkg()));
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public final ParceledListSlice getConversationsForPackage(String str, int i) {
            ArrayList arrayList;
            NotificationChannelGroup notificationChannelGroup;
            enforceSystemOrSystemUI("getConversationsForPackage");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            Objects.requireNonNull(str);
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked == null) {
                        arrayList = new ArrayList();
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        int size = packagePreferencesLocked.channels.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            NotificationChannel notificationChannel = (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
                            if (!TextUtils.isEmpty(notificationChannel.getConversationId()) && !notificationChannel.isDeleted() && !notificationChannel.isDemoted()) {
                                ConversationChannelWrapper conversationChannelWrapper = new ConversationChannelWrapper();
                                conversationChannelWrapper.setPkg(packagePreferencesLocked.pkg);
                                conversationChannelWrapper.setUid(packagePreferencesLocked.uid);
                                conversationChannelWrapper.setNotificationChannel(notificationChannel);
                                conversationChannelWrapper.setParentChannelLabel(((NotificationChannel) packagePreferencesLocked.channels.get(notificationChannel.getParentChannelId())).getName());
                                if (notificationChannel.getGroup() != null && (notificationChannelGroup = (NotificationChannelGroup) packagePreferencesLocked.groups.get(notificationChannel.getGroup())) != null) {
                                    if (!notificationChannelGroup.isBlocked()) {
                                        conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
                                    }
                                }
                                arrayList2.add(conversationChannelWrapper);
                            }
                        }
                        arrayList = arrayList2;
                    }
                } finally {
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConversationChannelWrapper conversationChannelWrapper2 = (ConversationChannelWrapper) it.next();
                ShortcutHelper shortcutHelper = NotificationManagerService.this.mShortcutHelper;
                if (shortcutHelper == null) {
                    conversationChannelWrapper2.setShortcutInfo((ShortcutInfo) null);
                } else {
                    conversationChannelWrapper2.setShortcutInfo(shortcutHelper.getValidShortcutInfo(conversationChannelWrapper2.getNotificationChannel().getConversationId(), UserHandle.of(UserHandle.getUserId(i)), str));
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public final ComponentName getDefaultNotificationAssistant() {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
            if (notificationAssistants.mDefaultFromConfig == null) {
                notificationAssistants.loadDefaultsFromConfig(false);
            }
            return notificationAssistants.mDefaultFromConfig;
        }

        public final ZenPolicy getDefaultZenPolicy() {
            enforceSystemOrSystemUI("INotificationManager.getDefaultZenPolicy");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mZenModeHelper.getDefaultZenPolicy();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getDeletedChannelCount(String str, int i) {
            enforceSystemOrSystemUI("getDeletedChannelCount");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            Objects.requireNonNull(str);
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked == null) {
                        return 0;
                    }
                    int size = packagePreferencesLocked.channels.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        if (((NotificationChannel) packagePreferencesLocked.channels.valueAt(i3)).isDeleted()) {
                            i2++;
                        }
                    }
                    return i2;
                } finally {
                }
            }
        }

        public final int getEdgeLightingState() {
            EdgeLightingClientManager edgeLightingClientManager = NotificationManagerService.this.mEdgeLightingManager.mEdgeLightingClientManager;
            synchronized (edgeLightingClientManager.mEdgeLightingList) {
                try {
                    return ((ArrayList) edgeLightingClientManager.mEdgeLightingList).size() > 1 ? 1 : 0;
                } finally {
                }
            }
        }

        public final ComponentName getEffectsSuppressor() {
            ComponentName componentName = !NotificationManagerService.this.mEffectsSuppressors.isEmpty() ? (ComponentName) NotificationManagerService.this.mEffectsSuppressors.get(0) : null;
            if (!NotificationManagerService.m711$$Nest$misCallerSystemOrSystemUiOrShell(NotificationManagerService.this) && componentName != null) {
                PackageManagerInternal packageManagerInternal = NotificationManagerService.this.mPackageManagerInternal;
                if (!((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).isSameApp(Binder.getCallingUid(), UserHandle.getUserId(Binder.getCallingUid()), 0L, componentName.getPackageName())) {
                    return null;
                }
            }
            return componentName;
        }

        public final List getEnabledNotificationListenerPackages() {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.getAllowedPackages(INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public final List getEnabledNotificationListeners(int i) {
            NotificationManagerService.this.checkNotificationListenerAccess$1();
            return NotificationManagerService.this.mListeners.getAllowedComponents(i);
        }

        public final int getHintsFromListener(INotificationListener iNotificationListener) {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public final int getHintsFromListenerNoToken() {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public final StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) {
            return getHistoricalNotificationsWithAttribution(str, null, i, z);
        }

        public final StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) {
            StatusBarNotification[] array;
            getHistoricalNotificationsWithAttribution_enforcePermission();
            int noteOpNoThrow = NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null);
            if (noteOpNoThrow != 0 && noteOpNoThrow != 3) {
                return null;
            }
            synchronized (NotificationManagerService.this.mArchive) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                array = notificationManagerService.mArchive.getArray(notificationManagerService.mUm, i, z);
            }
            return array;
        }

        public final int getInterruptionFilterFromListener(INotificationListener iNotificationListener) {
            int i;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                i = NotificationManagerService.this.mInterruptionFilter;
            }
            return i;
        }

        public final NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.getNotificationListenerFilter(Pair.create(componentName, Integer.valueOf(i)));
        }

        public final int getLockScreenNotificationVisibilityForPackage(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(str, i);
        }

        public final boolean getNotificationAlertsEnabledForPackage(String str, int i) {
            enforceSystemOrSystemUI("getNotificationAlertsEnabledForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(str, i);
        }

        public final NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) {
            return getConversationNotificationChannel(str, i, str2, str3, true, null);
        }

        public final NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str, i, str2, str3, true, z);
        }

        public final NotificationChannelGroup getNotificationChannelGroup(String str, String str2) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(Binder.getCallingUid(), str, str2, false);
        }

        public final NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) {
            enforceSystemOrSystemUI("getNotificationChannelGroupForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroup(i, str, str2);
        }

        public final ParceledListSlice getNotificationChannelGroups(String str) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(Binder.getCallingUid(), str, null, false, false, true);
        }

        public final ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelGroupsForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(i, str, null, z, true, false);
        }

        public final ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            ArrayList arrayList = new ArrayList();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            int uidForPackageAndUser = getUidForPackageAndUser(str, userHandle);
            preferencesHelper.getClass();
            ArrayList arrayList2 = new ArrayList();
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(uidForPackageAndUser, str);
                    if (packagePreferencesLocked != null) {
                        arrayList2.addAll(packagePreferencesLocked.groups.values());
                    }
                } finally {
                }
            }
            arrayList.addAll(arrayList2);
            return new ParceledListSlice(arrayList);
        }

        public final ParceledListSlice getNotificationChannels(String str, String str2, int i) {
            int i2;
            if (!canNotifyAsPackage(str, str2, i)) {
                NotificationManagerService.this.getClass();
                if (!NotificationManagerService.isCallingUidSystem()) {
                    StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Pkg ", str, " cannot read channels for ", str2, " in ");
                    m.append(i);
                    throw new SecurityException(m.toString());
                }
            }
            try {
                i2 = NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
            } catch (PackageManager.NameNotFoundException unused) {
                i2 = -1;
            }
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(i2, str2, false);
        }

        public final ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            if (!areNotificationsEnabledForPackage(str, i)) {
                return ParceledListSlice.emptyList();
            }
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            ArrayList arrayList = new ArrayList();
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.get(PreferencesHelper.packagePreferencesKey(i, str));
                    if (packagePreferences != null) {
                        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
                            if (preferencesHelper.channelIsLiveLocked(packagePreferences, notificationChannel) && notificationChannel.canBypassDnd()) {
                                arrayList.add(notificationChannel);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public final ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelsForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(i, str, z);
        }

        public final ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(getUidForPackageAndUser(str, userHandle), str, false);
        }

        public final String getNotificationDelegate(String str) {
            String str2;
            PreferencesHelper.Delegate delegate;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            int callingUid = Binder.getCallingUid();
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(callingUid, str);
                    str2 = null;
                    if (packagePreferencesLocked != null && (delegate = packagePreferencesLocked.delegate) != null) {
                        if (delegate.mEnabled) {
                            str2 = delegate.mPkg;
                        }
                    }
                } finally {
                }
            }
            return str2;
        }

        public final NotificationHistory getNotificationHistory(String str, String str2) {
            getNotificationHistory_enforcePermission();
            int noteOpNoThrow = NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null);
            if (noteOpNoThrow != 0 && noteOpNoThrow != 3) {
                return new NotificationHistory();
            }
            IntArray currentProfileIds = NotificationManagerService.this.mUserProfiles.getCurrentProfileIds();
            Trace.traceBegin(524288L, "notifHistoryReadHistory");
            try {
                return NotificationManagerService.this.mHistoryManager.readNotificationHistory(currentProfileIds.toArray());
            } finally {
                Trace.traceEnd(524288L);
            }
        }

        public final List getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) {
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
                bundle.putString(SystemIntentProcessor.KEY_URI, uri != null ? uri.toString() : null);
                bundle.putString("extraTitle", historicalNotification.getExtraTitle());
                arrayList.add(bundle);
            }
            return arrayList;
        }

        public final NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) {
            NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_NOTIFICATIONS", "NotificationManagerService.getNotificationHistory");
            int noteOpNoThrow = NotificationManagerService.this.mAppOps.noteOpNoThrow(25, Binder.getCallingUid(), str, str2, (String) null);
            if (noteOpNoThrow != 0 && noteOpNoThrow != 3) {
                return new NotificationHistory();
            }
            Trace.traceBegin(524288L, "notifHistoryReadHistoryForPackage");
            Slog.d("NotificationService", "getNotificationHistory start key=" + str4);
            try {
                return NotificationManagerService.this.mHistoryManager.readFilteredNotificationHistoryForPackage(i, i2, str3, str4);
            } finally {
                Trace.traceEnd(524288L);
                Log.d("NotificationService", "getNotificationHistory end pkg=" + str3);
            }
        }

        public final NotificationManager.Policy getNotificationPolicy(String str) {
            NotificationManager.Policy notificationPolicy;
            ZenPolicy zenPolicy;
            int callingUid = Binder.getCallingUid();
            if (!android.app.Flags.modesApi() || canManageGlobalZenPolicy(callingUid, str)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            zenModeHelper.getClass();
            if (!android.app.Flags.modesApi()) {
                Log.wtf("ZenModeHelper", "getNotificationPolicyFromImplicitZenRule called with flag off!");
                return zenModeHelper.getNotificationPolicy();
            }
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    if (zenModeConfig == null) {
                        notificationPolicy = null;
                    } else {
                        ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(ZenModeHelper.implicitRuleId(str));
                        notificationPolicy = (zenRule == null || (zenPolicy = zenRule.zenPolicy) == null) ? zenModeHelper.getNotificationPolicy() : zenModeHelper.mConfig.toNotificationPolicy(zenPolicy);
                    }
                } finally {
                }
            }
            return notificationPolicy;
        }

        public final int getNotificationSettingStatus(boolean z) {
            Slog.d("NotificationService", "getNotificationSettingStatus");
            if (!z) {
                AudioManager audioManager = (AudioManager) NotificationManagerService.this.getContext().getSystemService("audio");
                if (audioManager != null && audioManager.getRingerModeInternal() == 2 && audioManager.getStreamVolume(5) == 0) {
                    return 2097152;
                }
            } else if (getZenMode() != 0) {
                return 1048576;
            }
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:83:0x01ed, code lost:
        
            if (java.util.Arrays.stream(r0).noneMatch(new com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda7(1)) != false) goto L91;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getNotificationSoundStatus(java.lang.String r14) {
            /*
                Method dump skipped, instructions count: 518
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.getNotificationSoundStatus(java.lang.String):int");
        }

        public final int getNumNotificationChannelsForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNumNotificationChannelsForPackage");
            return NotificationManagerService.this.getNumNotificationChannelsForPackage(str, i, z);
        }

        public final int getPackageImportance(String str) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            return NotificationManagerService.this.mPermissionHelper.hasPermission(Binder.getCallingUid()) ? 3 : 0;
        }

        public final List getPackagesBypassingDnd(int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            ArraySet arraySet = new ArraySet();
            for (int i2 : NotificationManagerService.this.mUm.getProfileIds(i, false)) {
                for (PackageInfo packageInfo : NotificationManagerService.this.mPackageManagerClient.getInstalledPackagesAsUser(0, i2)) {
                    String str = packageInfo.packageName;
                    for (NotificationChannel notificationChannel : getNotificationChannelsBypassingDnd(str, packageInfo.applicationInfo.uid).getList()) {
                        if (z || TextUtils.isEmpty(notificationChannel.getConversationId()) || notificationChannel.isDemoted()) {
                            arraySet.add(str);
                        }
                    }
                }
            }
            return new ArrayList(arraySet);
        }

        public final NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) {
            enforceSystemOrSystemUI("getPopulatedNotificationChannelGroupForPackage");
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(i, str, str2, z);
        }

        public final boolean getPrivateNotificationsAllowed() {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") == 0) {
                return NotificationManagerService.this.mLockScreenAllowSecureNotifications;
            }
            throw new SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
        }

        public final ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(String str, int i) {
            String str2;
            enforceSystemOrSystemUI("getRecentBlockedNotificationChannelGroupsForPackage");
            HashSet hashSet = new HashSet();
            long currentTimeMillis = System.currentTimeMillis();
            UsageStatsManagerInternal usageStatsManagerInternal = NotificationManagerService.this.mUsageStatsManagerInternal;
            int userId = UserHandle.getUserId(i);
            UsageStatsService usageStatsService = UsageStatsService.this;
            usageStatsService.getClass();
            UsageEvents queryEventsWithQueryFilters = usageStatsService.queryEventsWithQueryFilters(userId, currentTimeMillis - 1209600000, currentTimeMillis, 0, EmptyArray.INT, null);
            if (queryEventsWithQueryFilters != null) {
                UsageEvents.Event event = new UsageEvents.Event();
                while (queryEventsWithQueryFilters.hasNextEvent()) {
                    queryEventsWithQueryFilters.getNextEvent(event);
                    if (event.getEventType() == 12 && str.equals(event.mPackage) && (str2 = event.mNotificationChannelId) != null) {
                        hashSet.add(str2);
                    }
                }
            }
            return NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(i, str, hashSet, false, true, false);
        }

        public final int getRuleInstanceCount(ComponentName componentName) {
            Objects.requireNonNull(componentName, "Owner is null");
            enforceSystemOrSystemUI("getRuleInstanceCount");
            return NotificationManagerService.this.mZenModeHelper.getCurrentInstanceCount(componentName);
        }

        public final ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) {
            ParceledListSlice parceledListSlice;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    ArrayList arrayList = (ArrayList) NotificationManagerService.this.mSnoozeHelper.getSnoozed();
                    int size = arrayList.size();
                    ArrayList arrayList2 = new ArrayList(size);
                    for (int i2 = 0; i2 < size; i2++) {
                        addToListIfNeeded((NotificationRecord) arrayList.get(i2), checkServiceTokenLocked, arrayList2, i);
                    }
                    parceledListSlice = new ParceledListSlice(arrayList2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        public final int getUidForPackageAndUser(String str, UserHandle userHandle) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, userHandle.getIdentifier());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getWearableAppList(int i) {
            ArrayList arrayList;
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "NMS : getWearableAppList uid=", "NotificationService");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    arrayList = new ArrayList();
                    int size = preferencesHelper.mPackagePreferences.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i2);
                        if (UserHandle.getUserId(packagePreferences.uid) == i && packagePreferences.muteByWearable == 1) {
                            arrayList.add(packagePreferences.pkg);
                            Slog.w("NotificationPrefHelper", "getWearableMutedAppList userId=" + i + " / uid=" + packagePreferences.uid + " / pkg=" + packagePreferences.pkg);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final int getZenMode() {
            return NotificationManagerService.this.mZenModeHelper.mZenMode;
        }

        public final ZenModeConfig getZenModeConfig() {
            enforceSystemOrSystemUI("INotificationManager.getZenModeConfig");
            return NotificationManagerService.this.mZenModeHelper.getConfig();
        }

        public final List getZenRules() {
            enforcePolicyAccess(Binder.getCallingUid(), "getZenRules");
            return NotificationManagerService.this.mZenModeHelper.getZenRules();
        }

        public final boolean hasEnabledNotificationListener(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.isPackageAllowed(i, str);
        }

        public final boolean hasPostNotificationPermission(String str) {
            try {
                PackageInfo packageInfo = NotificationManagerService.this.getContext().getPackageManager().getPackageInfo(str, 4160);
                if (packageInfo == null || packageInfo.applicationInfo.targetSdkVersion <= 32) {
                    return true;
                }
                String[] strArr = packageInfo.requestedPermissions;
                if (strArr != null) {
                    if (!Arrays.stream(strArr).noneMatch(new NotificationManagerService$16$$ExternalSyntheticLambda7(0))) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return true;
            }
        }

        public final boolean hasSentValidBubble(String str, int i) {
            boolean z;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                z = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).hasSentValidBubble;
            }
            return z;
        }

        public final boolean hasSentValidMsg(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasSentValidMsg(str, i);
        }

        public final boolean hasUserDemotedInvalidMsgApp(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, i);
        }

        public final boolean isAlertsAllowed(String str, int i, String str2, int i2) {
            if (getZenMode() != 0) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Screen on NOT allowed while DnD turned ON : ", str, "NotificationService");
                return false;
            }
            if (!areNotificationsEnabledForPackage(str, i)) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Screen on NOT allowed for notification blocked apps : ", str, "NotificationService");
                return false;
            }
            if (!NotificationManagerService.this.isPackageSuspendedForUser(str, i)) {
                return true;
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Screen on NOT allowed for package suspended : ", str, "NotificationService");
            return false;
        }

        public final boolean isAllowNotificationPopUpForPackage(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper.PackagePreferences packagePreferencesLocked = NotificationManagerService.this.mPreferencesHelper.getPackagePreferencesLocked(i, str);
            if (packagePreferencesLocked != null) {
                return packagePreferencesLocked.isAllowPopup;
            }
            return true;
        }

        public final boolean isEdgeLightingAllowed(String str, int i) {
            boolean z;
            enforceSystemOrSystemUI("isEdgeLightingAllowed");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            if (!Thread.holdsLock(preferencesHelper.mLock)) {
                synchronized (preferencesHelper.mLock) {
                    z = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).allowEdgeLighting;
                }
                return z;
            }
            Slog.d("NotificationPrefHelper", "holdsLock isEdgeLightingAllowed pac = " + str);
            ArrayList arrayList = new ArrayList(1);
            new Handler(UiThread.get().getLooper()).sendMessage(PooledLambda.obtainMessage(new PreferencesHelper$$ExternalSyntheticLambda0(), preferencesHelper, str, Integer.valueOf(i), arrayList, preferencesHelper.mPackagePreferences));
            while (arrayList.size() == 0) {
                try {
                    preferencesHelper.mPackagePreferences.wait();
                } catch (InterruptedException e) {
                    Slog.d("NotificationPrefHelper", "InterruptedException - " + e);
                }
            }
            Slog.d("NotificationPrefHelper", "isEdgeLightingAllowed = " + arrayList.get(0));
            return ((Boolean) arrayList.get(0)).booleanValue();
        }

        public final boolean isEdgeLightingNotificationAllowed(String str) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            edgeLightingManager.mSecurityPolicy.enforceCallFromPackage(str);
            int callingUserId = UserHandle.getCallingUserId();
            if (!edgeLightingManager.isCallingUserSupported(callingUserId)) {
                return false;
            }
            EdgeLightingPolicyManager edgeLightingPolicyManager = edgeLightingManager.mEdgeLightingPolicyManager;
            if (!edgeLightingPolicyManager.isHUNPeeked() || edgeLightingPolicyManager.isEdgeLightingDisabled()) {
                return false;
            }
            if (edgeLightingPolicyManager.isEdgeLightingRestricted(1) || edgeLightingPolicyManager.isEdgeLightingDisabledByPackage(str)) {
                return false;
            }
            if (edgeLightingManager.mEdgeLightingClientManager.isAvailableEdgeLighting(edgeLightingManager.mPowerManager.isInteractive() ? 1 : 2)) {
                return edgeLightingPolicyManager.isAcceptableApplication(7, callingUserId, str, false);
            }
            return false;
        }

        public final boolean isImportanceLocked(String str, int i) {
            boolean z;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str);
                    z = orCreatePackagePreferencesLocked.fixedImportance || orCreatePackagePreferencesLocked.defaultAppLockedImportance;
                } finally {
                }
            }
            return z;
        }

        public final boolean isInCall(String str, int i) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell(null);
            return NotificationManagerService.this.isCallNotification(str);
        }

        public final boolean isInInvalidMsgState(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mPreferencesHelper.isInInvalidMsgState(str, i);
        }

        public final boolean isNotificationAssistantAccessGranted(ComponentName componentName) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, componentName.getPackageName());
            return NotificationManagerService.this.mAssistants.isPackageOrComponentAllowed(INotificationManager.Stub.getCallingUserHandle().getIdentifier(), componentName.flattenToString());
        }

        public final boolean isNotificationListenerAccessGranted(ComponentName componentName) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, componentName.getPackageName());
            return NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(INotificationManager.Stub.getCallingUserHandle().getIdentifier(), componentName.flattenToString());
        }

        public final boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) {
            Objects.requireNonNull(componentName);
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            return NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(i, componentName.flattenToString());
        }

        public final boolean isNotificationPolicyAccessGranted(String str) {
            return checkPolicyAccess(str);
        }

        public final boolean isNotificationPolicyAccessGrantedForPackage(String str) {
            enforceSystemOrSystemUIOrSamePackage(str, "request policy access status for another package");
            return checkPolicyAccess(str);
        }

        public final int isNotificationTurnedOff(String str, int i) {
            if (!NotificationManagerService.m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService.this)) {
                return -1;
            }
            ParceledListSlice notificationChannels = NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(i, str, true);
            boolean z = (notificationChannels == null || notificationChannels.getList().isEmpty()) ? false : true;
            boolean hasPostNotificationPermission = hasPostNotificationPermission(str);
            if (!areNotificationsEnabledForPackage(str, i)) {
                if (!hasPostNotificationPermission) {
                    Slog.d("NotificationService", "No permissions");
                    return 2;
                }
                Slog.d("NotificationService", "Grants notification permissions");
                NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, true, false, UserHandle.getUserId(i));
                return 8;
            }
            if (!z) {
                Slog.d("NotificationService", "No channel");
                return 4;
            }
            int size = notificationChannels.getList().size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                NotificationChannel notificationChannel = (NotificationChannel) notificationChannels.getList().get(i2);
                if (notificationChannel.getImportance() == 0 && (notificationChannel.getUserLockedFields() & 4) != 0) {
                    notificationChannel.setImportance(notificationChannel.getOriginalImportance());
                    z2 = true;
                }
            }
            if (z2) {
                Slog.d("NotificationService", "Turned on notification channel");
                return 16;
            }
            Slog.d("NotificationService", "Already turned on and nothing has changed");
            return 32;
        }

        public final boolean isOngoingActivityAllowed(String str, int i) {
            enforceSystemOrSystemUI("isOngoingActivityAllowed");
            return NotificationManagerService.this.mPreferencesHelper.getOngoingActivityAllowedState(i, str) > 0;
        }

        public final boolean isPackageEnabled(String str, int i) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            String str2 = EdgeLightingManager.TAG;
            if (str == null) {
                edgeLightingManager.getClass();
                Slog.d(str2, "isPackageEnabled : packageName is null");
                return false;
            }
            EdgeLightingPolicyManager edgeLightingPolicyManager = edgeLightingManager.mEdgeLightingPolicyManager;
            if (!edgeLightingPolicyManager.mPriorityPolicy.contains(str)) {
                return edgeLightingPolicyManager.isAcceptableApplication(1, i, str, false);
            }
            Slog.d(str2, "isPackageEnabled : recommend packageName = ".concat(str));
            return true;
        }

        public final boolean isPackagePaused(String str) {
            Objects.requireNonNull(str);
            NotificationManagerService.this.checkCallerIsSameApp(str);
            return NotificationManagerService.this.isPackagePausedOrSuspended(Binder.getCallingUid(), str);
        }

        public final boolean isPermissionFixed(String str, int i) {
            enforceSystemOrSystemUI("isPermissionFixed");
            return NotificationManagerService.this.mPermissionHelper.isPermissionFixed(str, i);
        }

        public final boolean isReminderEnabled(String str, int i) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked == null) {
                        return false;
                    }
                    return packagePreferencesLocked.reminder;
                } finally {
                }
            }
        }

        public final boolean isSubDisplayNotificationAllowed(String str, int i) {
            boolean z;
            enforceSystemOrSystemUI("isSubDisplayNotificationAllowed");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                z = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).allowSubDisplayNoti;
            }
            return z;
        }

        public final boolean isSystemConditionProviderEnabled(String str) {
            enforceSystemOrSystemUI("INotificationManager.isSystemConditionProviderEnabled");
            return NotificationManagerService.this.mConditionProviders.mSystemConditionProviderNames.contains(str);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:
        
            if (r3 == 0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0038, code lost:
        
            r8.this$0.getContext().enforceCallingPermission("android.permission.READ_CONTACTS", "matchesCallFilter requires listener permission, contacts read access, or system level access");
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005d, code lost:
        
            if (r0 == 0) goto L17;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x007c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean matchesCallFilter(android.os.Bundle r9) {
            /*
                r8 = this;
                r0 = 0
                java.lang.String r1 = "INotificationManager.matchesCallFilter"
                r8.enforceSystemOrSystemUI(r1)     // Catch: java.lang.SecurityException -> L8
                r1 = 1
                goto L9
            L8:
                r1 = r0
            L9:
                com.android.server.notification.NotificationManagerService r2 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L47 android.os.RemoteException -> L5b
                android.content.pm.IPackageManager r2 = r2.mPackageManager     // Catch: java.lang.Throwable -> L47 android.os.RemoteException -> L5b
                int r3 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L47 android.os.RemoteException -> L5b
                java.lang.String[] r2 = r2.getPackagesForUid(r3)     // Catch: java.lang.Throwable -> L47 android.os.RemoteException -> L5b
                r3 = r0
            L16:
                int r4 = r2.length     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                if (r0 >= r4) goto L34
                com.android.server.notification.NotificationManagerService r4 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                com.android.server.notification.NotificationManagerService$NotificationListeners r4 = r4.mListeners     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                r5 = r2[r0]     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                android.os.UserHandle r6 = android.os.Binder.getCallingUserHandle()     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                int r6 = r6.getIdentifier()     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                boolean r4 = r4.hasAllowedListener(r6, r5)     // Catch: java.lang.Throwable -> L2f android.os.RemoteException -> L32
                r3 = r3 | r4
                int r0 = r0 + 1
                goto L16
            L2f:
                r9 = move-exception
                r0 = r3
                goto L48
            L32:
                r0 = r3
                goto L5b
            L34:
                if (r1 != 0) goto L60
                if (r3 != 0) goto L60
            L38:
                com.android.server.notification.NotificationManagerService r0 = com.android.server.notification.NotificationManagerService.this
                android.content.Context r0 = r0.getContext()
                java.lang.String r1 = "android.permission.READ_CONTACTS"
                java.lang.String r2 = "matchesCallFilter requires listener permission, contacts read access, or system level access"
                r0.enforceCallingPermission(r1, r2)
                goto L60
            L47:
                r9 = move-exception
            L48:
                if (r1 != 0) goto L5a
                if (r0 != 0) goto L5a
                com.android.server.notification.NotificationManagerService r8 = com.android.server.notification.NotificationManagerService.this
                android.content.Context r8 = r8.getContext()
                java.lang.String r0 = "android.permission.READ_CONTACTS"
                java.lang.String r1 = "matchesCallFilter requires listener permission, contacts read access, or system level access"
                r8.enforceCallingPermission(r0, r1)
            L5a:
                throw r9
            L5b:
                if (r1 != 0) goto L60
                if (r0 != 0) goto L60
                goto L38
            L60:
                com.android.server.notification.NotificationManagerService r0 = com.android.server.notification.NotificationManagerService.this
                com.android.server.notification.ZenModeHelper r0 = r0.mZenModeHelper
                android.os.UserHandle r4 = android.os.Binder.getCallingUserHandle()
                com.android.server.notification.NotificationManagerService r8 = com.android.server.notification.NotificationManagerService.this
                com.android.server.notification.RankingHelper r8 = r8.mRankingHelper
                java.lang.Class<com.android.server.notification.ValidateNotificationPeople> r1 = com.android.server.notification.ValidateNotificationPeople.class
                com.android.server.notification.NotificationSignalExtractor r8 = r8.findExtractor(r1)
                r6 = r8
                com.android.server.notification.ValidateNotificationPeople r6 = (com.android.server.notification.ValidateNotificationPeople) r6
                int r7 = android.os.Binder.getCallingUid()
                java.lang.Object r8 = r0.mConfigLock
                monitor-enter(r8)
                android.content.Context r1 = r0.mContext     // Catch: java.lang.Throwable -> L89
                int r2 = r0.mZenMode     // Catch: java.lang.Throwable -> L89
                android.app.NotificationManager$Policy r3 = r0.mConsolidatedPolicy     // Catch: java.lang.Throwable -> L89
                r5 = r9
                boolean r9 = com.android.server.notification.ZenModeFiltering.matchesCallFilter(r1, r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L89
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L89
                return r9
            L89:
                r9 = move-exception
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L89
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.matchesCallFilter(android.os.Bundle):boolean");
        }

        public final void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List list) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
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
                        NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                        synchronized (notificationListeners.mRequestedNotificationListeners) {
                            notificationListeners.mRequestedNotificationListeners.put(create, notificationListenerFilter);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyConditions(final String str, IConditionProvider iConditionProvider, final Condition[] conditionArr) {
            final ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            ConditionProviders conditionProviders = NotificationManagerService.this.mConditionProviders;
            synchronized (conditionProviders.mMutex) {
                checkServiceTokenLocked = conditionProviders.checkServiceTokenLocked(iConditionProvider);
            }
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.16.2
                /* JADX WARN: Removed duplicated region for block: B:77:0x0142 A[Catch: all -> 0x00dc, TryCatch #1 {all -> 0x00dc, blocks: (B:56:0x00d5, B:58:0x00d9, B:62:0x00df, B:65:0x00ee, B:68:0x00f3, B:71:0x00fa, B:74:0x0103, B:75:0x013c, B:77:0x0142, B:79:0x0149, B:81:0x0155, B:83:0x0158, B:86:0x015b, B:87:0x015e, B:90:0x0109, B:91:0x0113, B:93:0x0119, B:98:0x0123, B:101:0x0128, B:104:0x012f, B:107:0x0138), top: B:55:0x00d5 }] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 362
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.AnonymousClass2.run():void");
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new NotificationShellCmd(NotificationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final boolean onlyHasDefaultChannel(String str, int i) {
            enforceSystemOrSystemUI("onlyHasDefaultChannel");
            return NotificationManagerService.this.mPreferencesHelper.onlyHasDefaultChannel(str, i);
        }

        public final long pullStats(long j, int i, boolean z, List list) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystemOrShell();
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            TimeUnit timeUnit2 = TimeUnit.NANOSECONDS;
            long convert = timeUnit.convert(j, timeUnit2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i != 1) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.e("NotificationService", "exiting pullStats: bad request");
                    return 0L;
                }
                Slog.e("NotificationService", "pullStats REPORT_REMOTE_VIEWS from: " + convert + "  with " + z);
                PulledStats remoteViewStats = NotificationManagerService.this.mUsageStats.remoteViewStats(convert);
                list.add(remoteViewStats.toParcelFileDescriptor(i));
                Slog.e("NotificationService", "exiting pullStats with: " + list.size());
                return timeUnit2.convert(remoteViewStats.mTimePeriodEndMs, timeUnit);
            } catch (IOException e) {
                Slog.e("NotificationService", "exiting pullStats: on error", e);
                return 0L;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) {
            registerCallNotificationEventListener_enforcePermission();
            int identifier = userHandle.getIdentifier() != -2 ? userHandle.getIdentifier() : NotificationManagerService.this.mAmi.getCurrentUserId();
            synchronized (NotificationManagerService.this.mCallNotificationEventCallbacks) {
                ArrayMap arrayMap = (ArrayMap) NotificationManagerService.this.mCallNotificationEventCallbacks.getOrDefault(str, new ArrayMap());
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) arrayMap.getOrDefault(Integer.valueOf(identifier), new RemoteCallbackList());
                if (!remoteCallbackList.register(iCallNotificationEventCallback)) {
                    Log.e("NotificationService", "registerCallNotificationEventListener failed to register listener: " + str + " " + userHandle + " " + iCallNotificationEventCallback);
                    return;
                }
                arrayMap.put(Integer.valueOf(identifier), remoteCallbackList);
                NotificationManagerService.this.mCallNotificationEventCallbacks.put(str, arrayMap);
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    Iterator it = NotificationManagerService.this.mNotificationList.iterator();
                    while (it.hasNext()) {
                        NotificationRecord notificationRecord = (NotificationRecord) it.next();
                        if (notificationRecord.sbn.getNotification().isStyle(Notification.CallStyle.class) && NotificationManagerService.notificationMatchesUserId(identifier, notificationRecord, false) && notificationRecord.sbn.getPackageName().equals(str)) {
                            try {
                                iCallNotificationEventCallback.onCallNotificationPosted(str, notificationRecord.sbn.getUser());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0062 A[Catch: all -> 0x0030, TryCatch #0 {all -> 0x0030, blocks: (B:4:0x0027, B:6:0x002b, B:9:0x0056, B:10:0x005c, B:12:0x0062, B:15:0x006a, B:30:0x0072, B:31:0x007a, B:17:0x007d, B:20:0x0085, B:22:0x0089, B:24:0x00ad, B:27:0x008d, B:37:0x00b1, B:38:0x00d4, B:55:0x0033), top: B:3:0x0027 }] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00df  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void registerEdgeLightingListener(android.os.IBinder r10, android.content.ComponentName r11) {
            /*
                Method dump skipped, instructions count: 263
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.registerEdgeLightingListener(android.os.IBinder, android.content.ComponentName):void");
        }

        public final void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) {
            enforceSystemOrSystemUI("INotificationManager.registerListener");
            NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            int callingUid = Binder.getCallingUid();
            notificationListeners.checkNotNull(iNotificationListener);
            ManagedServices.ManagedServiceInfo registerServiceImpl = notificationListeners.registerServiceImpl(new ManagedServices.ManagedServiceInfo(iNotificationListener, componentName, i, true, null, 10000, callingUid));
            if (registerServiceImpl != null) {
                notificationListeners.onServiceAdded(registerServiceImpl);
            }
        }

        public final void registerNotificationListener(ComponentName componentName, int i, boolean z) {
            Slog.v("NotificationService", "registerNotificationListener component=" + componentName + " enabled =" + z);
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") != 0) {
                Slog.w("NotificationService", "Notification listener access denied calling registerNotificationListener");
                throw new SecurityException("Notification listener access denied");
            }
            Binder.clearCallingIdentity();
            if (z) {
                NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                synchronized (notificationListeners.mMutex) {
                    notificationListeners.registerServiceLocked(i, componentName, true);
                }
            } else {
                NotificationListeners notificationListeners2 = NotificationManagerService.this.mListeners;
                synchronized (notificationListeners2.mMutex) {
                    notificationListeners2.unregisterServiceLocked(i, componentName);
                }
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.mNotificationListenerFrom = "SEM_BIND";
            NotificationManagerService.m714$$Nest$mmakeNotiListenerHistory(notificationManagerService, componentName.getPackageName(), z);
        }

        public final boolean removeAutomaticZenRule(String str, boolean z) {
            boolean z2;
            Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(Binder.getCallingUid(), "removeAutomaticZenRule");
            enforceUserOriginOnlyFromSystem("removeAutomaticZenRule", z);
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            int computeZenOrigin = computeZenOrigin(z);
            int callingUid = Binder.getCallingUid();
            zenModeHelper.getClass();
            ZenModeHelper.requirePublicOrigin(computeZenOrigin, "removeAutomaticZenRule");
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    z2 = false;
                    if (zenModeConfig != null) {
                        ZenModeConfig copy = zenModeConfig.copy();
                        ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                        if (zenRule != null) {
                            if (!zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                                throw new SecurityException("Cannot delete rules not owned by your condition provider");
                            }
                            copy.automaticRules.remove(str);
                            zenModeHelper.maybePreserveRemovedRule(copy, zenRule, computeZenOrigin);
                            if (zenRule.getPkg() != null && !"android".equals(zenRule.getPkg())) {
                                for (ZenModeConfig.ZenRule zenRule2 : copy.automaticRules.values()) {
                                    if (zenRule2.getPkg() != null && zenRule2.getPkg().equals(zenRule.getPkg())) {
                                        break;
                                    }
                                }
                                zenModeHelper.mRulesUidCache.remove(zenRule.getPkg() + "|" + copy.user);
                            }
                            if (ZenModeHelper.DEBUG) {
                                Log.d("ZenModeHelper", "removeZenRule zenRule=" + str + " reason=removeAutomaticZenRule");
                            }
                            zenModeHelper.dispatchOnAutomaticRuleStatusChanged(zenModeHelper.mConfig.user, 3, zenRule.getPkg(), str);
                            z2 = zenModeHelper.setConfigLocked(copy, computeZenOrigin, "removeAutomaticZenRule", null, true, callingUid);
                        }
                    }
                } finally {
                }
            }
            return z2;
        }

        public final boolean removeAutomaticZenRules(String str, boolean z) {
            Objects.requireNonNull(str, "Package name is null");
            enforceSystemOrSystemUI("removeAutomaticZenRules");
            enforceUserOriginOnlyFromSystem("removeAutomaticZenRules", z);
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            int computeZenOrigin = computeZenOrigin(z);
            String concat = str.concat("|removeAutomaticZenRules");
            int callingUid = Binder.getCallingUid();
            zenModeHelper.getClass();
            ZenModeHelper.requirePublicOrigin(computeZenOrigin, "removeAutomaticZenRules");
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    if (zenModeConfig == null) {
                        return false;
                    }
                    ZenModeConfig copy = zenModeConfig.copy();
                    for (int size = copy.automaticRules.size() - 1; size >= 0; size--) {
                        ArrayMap arrayMap = copy.automaticRules;
                        ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) arrayMap.get(arrayMap.keyAt(size));
                        if (Objects.equals(zenRule.getPkg(), str) && zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                            copy.automaticRules.removeAt(size);
                            zenModeHelper.maybePreserveRemovedRule(copy, zenRule, computeZenOrigin);
                        }
                    }
                    if (computeZenOrigin == 5) {
                        for (int size2 = copy.deletedRules.size() - 1; size2 >= 0; size2--) {
                            ArrayMap arrayMap2 = copy.deletedRules;
                            if (Objects.equals(((ZenModeConfig.ZenRule) arrayMap2.get(arrayMap2.keyAt(size2))).getPkg(), str)) {
                                copy.deletedRules.removeAt(size2);
                            }
                        }
                    }
                    return zenModeHelper.setConfigLocked(copy, computeZenOrigin, concat, null, true, callingUid);
                } finally {
                }
            }
        }

        public final void removeEdgeNotification(String str, int i, Bundle bundle, int i2) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, true, false, "cancelNotificationWithTag", str);
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            Binder.getCallingUid();
            Binder.getCallingPid();
            notificationManagerService.mHandler.post(new AnonymousClass18(notificationManagerService, str, i, bundle, handleIncomingUser, 1));
        }

        public final boolean removeWearableAppFromList(int i, String str) {
            try {
                int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                Log.d("NotificationService", "NMS : removeWearableAppFromList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, 0, str);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public final void requestBindListener(ComponentName componentName) {
            boolean contains;
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                synchronized (notificationAssistants.mMutex) {
                    contains = notificationAssistants.mEnabledServicesForCurrentProfiles.contains(componentName);
                }
                (contains ? NotificationManagerService.this.mAssistants : NotificationManagerService.this.mListeners).setComponentState(UserHandle.getUserId(callingUid), componentName, true);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void requestBindProvider(ComponentName componentName) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mConditionProviders.setComponentState(UserHandle.getUserId(callingUid), componentName, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestHintsFromListener(INotificationListener iNotificationListener, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                        if ((i & 7) != 0) {
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            if ((i & 1) != 0) {
                                notificationManagerService.addDisabledHint(checkServiceTokenLocked, 1);
                            }
                            if ((i & 2) != 0) {
                                notificationManagerService.addDisabledHint(checkServiceTokenLocked, 2);
                            }
                            if ((i & 4) != 0) {
                                notificationManagerService.addDisabledHint(checkServiceTokenLocked, 4);
                            } else {
                                notificationManagerService.getClass();
                            }
                        } else {
                            NotificationManagerService.this.removeDisabledHints(checkServiceTokenLocked, i);
                        }
                        NotificationManagerService.m723$$Nest$mupdateListenerHintsLocked(NotificationManagerService.this);
                        NotificationManagerService.m721$$Nest$mupdateEffectsSuppressorLocked(NotificationManagerService.this);
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) {
            final ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            if (android.app.Flags.modesApi()) {
                final int callingUid = Binder.getCallingUid();
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                }
                final int zenModeFromInterruptionFilter = NotificationManager.zenModeFromInterruptionFilter(i, -1);
                if (zenModeFromInterruptionFilter == -1) {
                    return;
                }
                if (!canManageGlobalZenPolicy(callingUid, checkServiceTokenLocked.component.getPackageName())) {
                    NotificationManagerService.this.mZenModeHelper.applyGlobalZenModeAsImplicitZenRule(callingUid, zenModeFromInterruptionFilter, checkServiceTokenLocked.component.getPackageName());
                    return;
                } else {
                    final int computeZenOrigin = computeZenOrigin(false);
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda3
                        public final void runOrThrow() {
                            NotificationManagerService.AnonymousClass16 anonymousClass16 = NotificationManagerService.AnonymousClass16.this;
                            int i2 = zenModeFromInterruptionFilter;
                            int i3 = computeZenOrigin;
                            ManagedServices.ManagedServiceInfo managedServiceInfo = checkServiceTokenLocked;
                            int i4 = callingUid;
                            NotificationManagerService.this.mZenModeHelper.setManualZenMode(i2, null, i3, "listener:" + managedServiceInfo.component.flattenToShortString(), managedServiceInfo.component.getPackageName(), i4);
                        }
                    });
                    return;
                }
            }
            int callingUid2 = Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = NotificationManagerService.this.isCallerSystemOrSystemUi();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked2 = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
                    ComponentName componentName = checkServiceTokenLocked2.component;
                    zenModeHelper.getClass();
                    int zenModeFromInterruptionFilter2 = NotificationManager.zenModeFromInterruptionFilter(i, -1);
                    if (zenModeFromInterruptionFilter2 != -1) {
                        int i2 = isCallerSystemOrSystemUi ? 5 : 4;
                        StringBuilder sb = new StringBuilder("listener:");
                        sb.append(componentName != null ? componentName.flattenToShortString() : null);
                        zenModeHelper.setManualZenMode(zenModeFromInterruptionFilter2, null, i2, sb.toString(), componentName != null ? componentName.getPackageName() : null, callingUid2);
                    }
                    NotificationManagerService.m722$$Nest$mupdateInterruptionFilterLocked(NotificationManagerService.this);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean requestListenerHintsForWearable(int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                Log.d("NotificationService", "NMS : requestListenerHintsForWearable state=" + i);
                NotificationManagerService.this.mAttentionHelper.mIsMutedByWearableApps = i;
            }
            return true;
        }

        public final void requestUnbindListener(INotificationListener iNotificationListener) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    ManagedServices.this.setComponentState(UserHandle.getUserId(callingUid), checkServiceTokenLocked.component, false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestUnbindListenerComponent(ComponentName componentName) {
            boolean contains;
            NotificationManagerService.this.checkCallerIsSameApp(componentName.getPackageName());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    try {
                        NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                        synchronized (notificationAssistants.mMutex) {
                            contains = notificationAssistants.mEnabledServicesForCurrentProfiles.contains(componentName);
                        }
                        ManagedServices managedServices = contains ? NotificationManagerService.this.mAssistants : NotificationManagerService.this.mListeners;
                        if (managedServices.isPackageOrComponentAllowed(UserHandle.getUserId(callingUid), componentName.flattenToString())) {
                            managedServices.setComponentState(UserHandle.getUserId(callingUid), componentName, false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestUnbindProvider(IConditionProvider iConditionProvider) {
            ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ConditionProviders conditionProviders = NotificationManagerService.this.mConditionProviders;
                synchronized (conditionProviders.mMutex) {
                    checkServiceTokenLocked = conditionProviders.checkServiceTokenLocked(iConditionProvider);
                }
                ManagedServices.this.setComponentState(UserHandle.getUserId(callingUid), checkServiceTokenLocked.component, false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resetDefaultAllowEdgeLighting() {
            enforceSystemOrSystemUI("resetDefaultAllowEdgeLighting");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    int size = preferencesHelper.mPackagePreferences.size();
                    for (int i = 0; i < size; i++) {
                        ((PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i)).allowEdgeLighting = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void resetDefaultAllowOngoingActivity() {
            enforceSystemOrSystemUI("resetDefaultAllowOngoingActivity");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    int size = preferencesHelper.mPackagePreferences.size();
                    for (int i = 0; i < size; i++) {
                        PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i);
                        List list = preferencesHelper.mOngoingActivityAllowedAppList;
                        if (list != null) {
                            if (((ArrayList) list).contains(packagePreferences.pkg)) {
                                packagePreferences.allowOngoingActivity = 1;
                            }
                        }
                        packagePreferences.allowOngoingActivity = -1;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManagerService.this.handleSavePolicyFile();
            for (String str : NotificationManagerService.this.mOngoingActivityAppList) {
                Intent intent = new Intent("com.samsung.intent.action.ONGOING_ACTIVITY_SETTING_CHANGED");
                intent.setPackage(str);
                intent.putExtra("ongoingActivitySettingValue", true);
                NotificationManagerService.this.getContext().sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("resetDefaultAllowOngoingActivity - Send broadcast - pkg = "), str, "NotificationService");
            }
        }

        public final void setAllowEdgeLighting(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setAllowEdgeLighting");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).allowEdgeLighting = z;
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setAllowNotificationPopUpForPackage(String str, int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            Log.d("NotificationService", "setAllowNotificationPopUpForPackage: pkg=" + str + " uid=" + i + " allow=" + z);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).isAllowPopup = z;
            }
            preferencesHelper.updateConfig();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setAllowOngoingActivity(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setAllowOngoingActivity");
            NotificationManagerService.this.mPreferencesHelper.setAllowOngoingActivityState(i, z ? 1 : 0, str);
            if (NotificationManagerService.this.mOngoingActivitySettingValue.containsKey(str)) {
                NotificationManagerService.this.mOngoingActivitySettingValue.replace(str, Boolean.valueOf(z));
            } else {
                NotificationManagerService.this.mOngoingActivitySettingValue.put(str, Boolean.valueOf(z));
            }
            NotificationManagerService.this.mHandler.scheduleSendRankingUpdate();
            NotificationManagerService.this.handleSavePolicyFile();
            Intent intent = new Intent("com.samsung.intent.action.ONGOING_ACTIVITY_SETTING_CHANGED");
            intent.setPackage(str);
            intent.putExtra("ongoingActivitySettingValue", z);
            NotificationManagerService.this.getContext().sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(i), null);
            Slog.d("NotificationService", "setAllowOngoingActivity - Send broadcast - pkg = " + str + " value = " + z);
        }

        public final void setAllowSubDisplayNotification(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setAllowSubDisplayNotification");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).allowSubDisplayNoti = z;
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setAppBypassDnd(String str, int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            int callingUid = Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = NotificationManagerService.this.isCallerSystemOrSystemUi();
            RCPManagerService$$ExternalSyntheticOutline0.m("NotificationService", StorageManagerService$$ExternalSyntheticOutline0.m(i, "setAppBypassDnd: pkg=", str, " uid=", " allow="), z);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    Iterator it = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).channels.values().iterator();
                    while (it.hasNext()) {
                        ((NotificationChannel) it.next()).setBypassDnd(z);
                    }
                    if (preferencesHelper.mCurrentUserHasChannelsBypassingDnd != z) {
                        preferencesHelper.updateCurrentUserHasChannelsBypassingDnd(callingUid, isCallerSystemOrSystemUi);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManager.Policy notificationPolicy = NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
            if (notificationPolicy == null) {
                return;
            }
            notificationPolicy.addAppBypassDnd(str, i, z);
            NotificationManagerService.this.mZenModeHelper.setNotificationPolicy(new NotificationManager.Policy(notificationPolicy.priorityCategories, notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects, -1, notificationPolicy.priorityConversationSenders, notificationPolicy.exceptionContactsFlag, notificationPolicy.getExceptionContacts(), notificationPolicy.appBypassDndFlag, notificationPolicy.getAppBypassDndList()), 5, callingUid);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setAutomaticZenRuleState(String str, Condition condition) {
            Objects.requireNonNull(str, "id is null");
            Objects.requireNonNull(condition, "Condition is null");
            condition.validate();
            enforcePolicyAccess(Binder.getCallingUid(), "setAutomaticZenRuleState");
            boolean z = condition.source == 1;
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            int computeZenOrigin = computeZenOrigin(z);
            int callingUid = Binder.getCallingUid();
            zenModeHelper.getClass();
            ZenModeHelper.requirePublicOrigin(computeZenOrigin, "setAutomaticZenRuleState");
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    ZenModeConfig zenModeConfig = zenModeHelper.mConfig;
                    if (zenModeConfig == null) {
                        return;
                    }
                    ZenModeConfig copy = zenModeConfig.copy();
                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                    if (!android.app.Flags.modesApi()) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(zenRule);
                        zenModeHelper.setAutomaticZenRuleStateLocked(copy, arrayList, condition, computeZenOrigin, callingUid);
                    } else if (zenRule != null && zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                        zenModeHelper.setAutomaticZenRuleStateLocked(copy, Collections.singletonList(zenRule), condition, computeZenOrigin, callingUid);
                    }
                } finally {
                }
            }
        }

        public final void setBubblesAllowed(String str, int i, int i2) {
            boolean z;
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str);
                z = orCreatePackagePreferencesLocked.bubblePreference != i2;
                orCreatePackagePreferencesLocked.bubblePreference = i2;
                orCreatePackagePreferencesLocked.lockedAppFields |= 2;
            }
            if (z) {
                preferencesHelper.updateConfig();
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setHideSilentStatusIcons(final boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.mPreferencesHelper.mHideSilentStatusBarIcons = z;
            notificationManagerService.handleSavePolicyFile();
            final NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            Iterator it = ((ArrayList) notificationListeners.getServices()).iterator();
            while (it.hasNext()) {
                final ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                NotificationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerService.NotificationListeners notificationListeners2 = NotificationManagerService.NotificationListeners.this;
                        ManagedServices.ManagedServiceInfo managedServiceInfo2 = managedServiceInfo;
                        boolean z2 = z;
                        notificationListeners2.getClass();
                        try {
                            managedServiceInfo2.service.onStatusBarIconsBehaviorChanged(z2);
                        } catch (RemoteException e) {
                            Slog.e(notificationListeners2.TAG, "unable to notify listener (hideSilentStatusIcons): " + managedServiceInfo2, e);
                        }
                    }
                });
            }
        }

        public final void setInterruptionFilter(String str, int i, boolean z) {
            enforcePolicyAccess(str, "setInterruptionFilter");
            int zenModeFromInterruptionFilter = NotificationManager.zenModeFromInterruptionFilter(i, -1);
            if (zenModeFromInterruptionFilter == -1) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid filter: "));
            }
            int callingUid = Binder.getCallingUid();
            enforceUserOriginOnlyFromSystem("setInterruptionFilter", z);
            if (android.app.Flags.modesApi() && !canManageGlobalZenPolicy(callingUid, str)) {
                NotificationManagerService.this.mZenModeHelper.applyGlobalZenModeAsImplicitZenRule(callingUid, zenModeFromInterruptionFilter, str);
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mZenModeHelper.setManualZenMode(zenModeFromInterruptionFilter, null, computeZenOrigin(z), "(pkg-" + str + ")setInterruptionFilter", str, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setInvalidMsgAppDemoted(String str, int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).userDemotedMsgApp = z;
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            Pair create = Pair.create(componentName, Integer.valueOf(i));
            synchronized (notificationListeners.mRequestedNotificationListeners) {
                notificationListeners.mRequestedNotificationListeners.put(create, notificationListenerFilter);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            Log.d("NotificationService", "setLockScreenNotificationVisibilityForPackage: pkg=" + str + " uid=" + i + " lockscreenVisibility=" + i2);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).appLockScreenVisibility = i2;
            }
            preferencesHelper.updateConfig();
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            int computeZenOrigin = computeZenOrigin(true);
            int callingUid = Binder.getCallingUid();
            zenModeHelper.getClass();
            if (android.app.Flags.modesUi()) {
                synchronized (zenModeHelper.mConfigLock) {
                    try {
                        if (zenModeHelper.mConfig == null) {
                            return;
                        }
                        if (ZenModeHelper.DEBUG) {
                            Log.d("ZenModeHelper", "updateManualRule " + zenDeviceEffects + " reason=Update manual mode non-policy settings callingUid=" + callingUid);
                        }
                        ZenModeConfig copy = zenModeHelper.mConfig.copy();
                        ZenModeConfig.ZenRule zenRule = copy.manualRule;
                        zenRule.pkg = "android";
                        zenRule.zenDeviceEffects = zenDeviceEffects;
                        zenModeHelper.setConfigLocked(copy, computeZenOrigin, "Update manual mode non-policy settings", null, true, callingUid);
                    } finally {
                    }
                }
            }
        }

        public final void setNASMigrationDoneAndResetDefault(int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationManagerService.this.setNASMigrationDone(i);
            if (!z) {
                NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                notificationAssistants.mDefaultComponents.clear();
                notificationAssistants.mDefaultPackages.clear();
            } else {
                NotificationAssistants notificationAssistants2 = NotificationManagerService.this.mAssistants;
                notificationAssistants2.mDefaultComponents.clear();
                notificationAssistants2.mDefaultPackages.clear();
                notificationAssistants2.loadDefaultsFromConfig(true);
            }
        }

        public final void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) {
            enforceSystemOrSystemUI("setNotificationAlertsEnabledForPackage");
            NotificationManagerService.this.mPreferencesHelper.setNotificationAlertsEnabledForPackage(str, i, z);
        }

        public final void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) {
            setNotificationAssistantAccessGrantedForUser(componentName, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public final void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell(null);
            for (UserInfo userInfo : NotificationManagerService.this.mUm.getEnabledProfiles(i)) {
                NotificationManagerService.this.mAssistants.mIsUserChanged.put(Integer.valueOf(userInfo.id), Boolean.TRUE);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, z, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setNotificationDelegate(String str, String str2) {
            PreferencesHelper.Delegate delegate;
            NotificationManagerService.this.checkCallerIsSameApp(str);
            int callingUid = Binder.getCallingUid();
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(callingUid);
            if (str2 == null) {
                PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
                int callingUid2 = Binder.getCallingUid();
                synchronized (preferencesHelper.mLock) {
                    try {
                        PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(callingUid2, str);
                        if (packagePreferencesLocked != null && (delegate = packagePreferencesLocked.delegate) != null) {
                            delegate.mEnabled = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                NotificationManagerService.this.handleSavePolicyFile();
                return;
            }
            try {
                ApplicationInfo applicationInfo = NotificationManagerService.this.mPackageManager.getApplicationInfo(str2, 786432L, userHandleForUid.getIdentifier());
                if (applicationInfo != null) {
                    PreferencesHelper preferencesHelper2 = NotificationManagerService.this.mPreferencesHelper;
                    int i = applicationInfo.uid;
                    synchronized (preferencesHelper2.mLock) {
                        preferencesHelper2.getOrCreatePackagePreferencesLocked(callingUid, str).delegate = new PreferencesHelper.Delegate(i, str2, true);
                    }
                    NotificationManagerService.this.handleSavePolicyFile();
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public final void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) {
            NotificationManagerService.this.mNotificationListenerFrom = "SETTING";
            setNotificationListenerAccessGrantedForUser(componentName, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z, z2);
        }

        public final void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) {
            boolean z3;
            Objects.requireNonNull(componentName);
            if (UserHandle.getCallingUserId() != i) {
                NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "setNotificationListenerAccessGrantedForUser for user " + i);
            }
            NotificationManagerService.this.checkNotificationListenerAccess$1();
            if (z && componentName.flattenToString().length() > NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH) {
                throw new IllegalArgumentException("Component name too long: " + componentName.flattenToString());
            }
            if (!z2) {
                NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                String flattenToString = componentName.flattenToString();
                synchronized (notificationListeners.mApproved) {
                    try {
                        ArraySet arraySet = (ArraySet) notificationListeners.mUserSetServices.get(Integer.valueOf(i));
                        z3 = arraySet != null && arraySet.contains(flattenToString);
                    } finally {
                    }
                }
                if (z3) {
                    return;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService$$ExternalSyntheticLambda6 notificationManagerService$$ExternalSyntheticLambda6 = NotificationManagerService.this.mAllowedManagedServicePackages;
                String packageName = componentName.getPackageName();
                Integer valueOf = Integer.valueOf(i);
                NotificationManagerService.this.mListeners.getClass();
                if (notificationManagerService$$ExternalSyntheticLambda6.f$0.canUseManagedServices(packageName, valueOf, null)) {
                    NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(i, componentName.flattenToString(), false, z, z2);
                    NotificationManagerService.this.mListeners.setPackageOrComponentEnabled(i, componentName.flattenToString(), true, z, z2);
                    NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), UserHandle.of(i), null);
                    if (TextUtils.isEmpty(NotificationManagerService.this.mNotificationListenerFrom)) {
                        NotificationManagerService.this.mNotificationListenerFrom = "ETC";
                    }
                    NotificationManagerService.m714$$Nest$mmakeNotiListenerHistory(NotificationManagerService.this, componentName.getPackageName(), z);
                    NotificationManagerService.this.handleSavePolicyFile();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:60:0x00df, code lost:
        
            if ((r2 & 2) != 0) goto L43;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setNotificationPolicy(java.lang.String r29, android.app.NotificationManager.Policy r30, boolean r31) {
            /*
                Method dump skipped, instructions count: 538
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.setNotificationPolicy(java.lang.String, android.app.NotificationManager$Policy, boolean):void");
        }

        public final void setNotificationPolicyAccessGranted(String str, boolean z) {
            setNotificationPolicyAccessGrantedForUser(str, INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public final void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystemOrShell();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService$$ExternalSyntheticLambda6 notificationManagerService$$ExternalSyntheticLambda6 = NotificationManagerService.this.mAllowedManagedServicePackages;
                Integer valueOf = Integer.valueOf(i);
                NotificationManagerService.this.mConditionProviders.getClass();
                if (notificationManagerService$$ExternalSyntheticLambda6.f$0.canUseManagedServices(str, valueOf, null)) {
                    NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(i, str, true, z, true);
                    NotificationManagerService.this.getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), UserHandle.of(i), null);
                    NotificationManagerService.this.handleSavePolicyFile();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setNotificationTurnOff(String str, int i) {
            if (!NotificationManagerService.m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService.this) || !areNotificationsEnabledForPackage(str, i)) {
                return false;
            }
            NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, false, false, UserHandle.getUserId(i));
            Slog.d("NotificationService", "Revoke notification permissions");
            return true;
        }

        public final void setNotificationsEnabledForPackage(final String str, final int i, boolean z) {
            String str2;
            enforceSystemOrSystemUI("setNotificationsEnabledForPackage");
            if (NotificationManagerService.this.mPermissionHelper.hasPermission(i) == z) {
                return;
            }
            NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, z, false, UserHandle.getUserId(i));
            final NotificationManagerService notificationManagerService = NotificationManagerService.this;
            final boolean z2 = !z;
            notificationManagerService.mHandler.postDelayed(new Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    boolean z3 = z2;
                    String str3 = str;
                    int i2 = i;
                    notificationManagerService2.getClass();
                    try {
                        notificationManagerService2.getContext().sendBroadcastAsUser(new Intent("android.app.action.APP_BLOCK_STATE_CHANGED").putExtra("android.app.extra.BLOCKED_STATE", z3).addFlags(268435456).setPackage(str3), UserHandle.of(UserHandle.getUserId(i2)), null);
                    } catch (SecurityException e) {
                        Slog.w("NotificationService", "Can't notify app about app block change", e);
                    }
                }
            }, 500L);
            NotificationManagerService.this.mMetricsLogger.write(new LogMaker(147).setType(4).setPackageName(str).setSubtype(z ? 1 : 0));
            NotificationChannelLogger notificationChannelLogger = NotificationManagerService.this.mNotificationChannelLogger;
            notificationChannelLogger.getClass();
            ((NotificationChannelLoggerImpl) notificationChannelLogger).mUiEventLogger.log(z ? NotificationChannelLogger.NotificationChannelEvent.APP_NOTIFICATIONS_UNBLOCKED : NotificationChannelLogger.NotificationChannelEvent.APP_NOTIFICATIONS_BLOCKED, i, str);
            NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
            notificationManagerService2.getClass();
            boolean isCallingUidSystem = NotificationManagerService.isCallingUidSystem();
            if (!isCallingUidSystem) {
                str2 = "NOTI_PANEL";
            } else if (!isCallingUidSystem) {
                return;
            } else {
                str2 = "SETTINGS";
            }
            notificationManagerService2.mNotiPermissionHistoryList.add(NotificationManagerService.makeTime() + " category = " + str2 + ", pkg = " + str + ", enabled= " + z);
            while (notificationManagerService2.mNotiPermissionHistoryList.size() > 100) {
                notificationManagerService2.mNotiPermissionHistoryList.remove(0);
            }
        }

        public final void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) {
            setNotificationsEnabledForPackage(str, i, z);
        }

        public final void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) {
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
                        if (notificationRecord != null && ((userId = notificationRecord.sbn.getUserId()) == checkServiceTokenLocked.userid || userId == -1 || NotificationManagerService.this.mUserProfiles.isCurrentProfile(userId))) {
                            arrayList.add(notificationRecord);
                            if (!notificationRecord.mStats.hasSeen()) {
                                if (NotificationManagerService.DBG) {
                                    Slog.d("NotificationService", "Marking notification as seen " + strArr[i]);
                                }
                                NotificationManagerService.this.reportSeen(notificationRecord);
                                notificationRecord.mStats.setSeen();
                                if (notificationRecord.mTextChanged) {
                                    notificationRecord.setInterruptive(true);
                                }
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

        public final void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                ManagedServices.ManagedServiceInfo checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                if (i == 1) {
                    notificationListeners.mLightTrimListeners.add(checkServiceTokenLocked);
                } else {
                    notificationListeners.mLightTrimListeners.remove(checkServiceTokenLocked);
                }
            }
        }

        public final void setPrivateNotificationsAllowed(boolean z) {
            if (NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") != 0) {
                throw new SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            if (z != notificationManagerService.mLockScreenAllowSecureNotifications) {
                notificationManagerService.mLockScreenAllowSecureNotifications = z;
                if (android.app.Flags.keyguardPrivateNotifications()) {
                    NotificationManagerService.this.getContext().sendBroadcast(new Intent("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED").putExtra("android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED", NotificationManagerService.this.mLockScreenAllowSecureNotifications), "android.permission.STATUS_BAR_SERVICE");
                }
                NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public final void setReminderEnabled(int i, boolean z, List list) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            if (z) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    try {
                        NotificationManagerService.this.mPreferencesHelper.setReminderEnabled(NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i), str, true);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.d("NotificationService", "setReminderEnabledtoList NameNotFoundException");
                    }
                }
            } else {
                NotificationManagerService.this.mPreferencesHelper.removeAllReminderSetting(i);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setReminderEnabledForPackage(String str, int i, boolean z) {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            NotificationManagerService.this.mPreferencesHelper.setReminderEnabled(i, str, z);
            NotificationReminder notificationReminder = NotificationManagerService.this.mNotificationReminder;
            notificationReminder.getClass();
            Log.d("NotificationReminder", "setReminderAppEnabled - pkg:" + str + " uid:" + i + " enabled:" + z);
            if (z) {
                notificationReminder.sendMessage(1002);
            } else {
                notificationReminder.sendMessage(1003);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setRestoreBlockListForSS(List list) {
            enforceSystemOrSystemUI("setRestoreBlockListForSS");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            if (list == null) {
                preferencesHelper.getClass();
                Slog.d("NotificationPrefHelper", "restore block list is null");
                return;
            }
            ((ArrayList) preferencesHelper.mBlockList).clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ((ArrayList) preferencesHelper.mBlockList).add(((String) list.get(i)).toString());
                Slog.d("NotificationPrefHelper", "setRestoreBlockListForSS package= " + ((String) list.get(i)));
            }
        }

        public final void setShowBadge(String str, int i, boolean z) {
            boolean z2;
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = preferencesHelper.getOrCreatePackagePreferencesLocked(i, str);
                    if (orCreatePackagePreferencesLocked.showBadge != z) {
                        orCreatePackagePreferencesLocked.showBadge = z;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z2) {
                preferencesHelper.updateConfig();
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void setToastRateLimitingEnabled(boolean z) {
            setToastRateLimitingEnabled_enforcePermission();
            synchronized (NotificationManagerService.this.mToastQueue) {
                int callingUid = Binder.getCallingUid();
                int userId = UserHandle.getUserId(callingUid);
                if (z) {
                    ((ArraySet) NotificationManagerService.this.mToastRateLimitingDisabledUids).remove(Integer.valueOf(callingUid));
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
                    ((ArraySet) NotificationManagerService.this.mToastRateLimitingDisabledUids).add(Integer.valueOf(callingUid));
                }
            }
        }

        public final boolean setWearableAppList(int i, List list) {
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    int size = preferencesHelper.mPackagePreferences.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i2);
                        if (UserHandle.getUserId(packagePreferences.uid) == i) {
                            packagePreferences.muteByWearable = -1;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                try {
                    int packageUidAsUser = NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, i);
                    Log.d("NotificationService", "NMS : setWearableAppList pkg=" + str + " / userid=" + i + " / uid=" + packageUidAsUser);
                    NotificationManagerService.this.mPreferencesHelper.setWearableAppMuted(packageUidAsUser, 1, str);
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            }
            return true;
        }

        public final void setZenMode(int i, Uri uri, String str, boolean z) {
            enforceSystemOrSystemUI("INotificationManager.setZenMode");
            enforceUserOriginOnlyFromSystem("setZenMode", z);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String nameForUid = NotificationManagerService.this.getContext().getPackageManager().getNameForUid(Binder.getCallingUid());
            try {
                NotificationManagerService.this.mZenModeHelper.setManualZenMode(i, uri, computeZenOrigin(z), "(pkg-" + nameForUid + ")" + str, null, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean shouldHideSilentStatusIcons(String str) {
            NotificationManagerService.this.checkCallerIsSameApp(str);
            NotificationManagerService.this.getClass();
            if (!NotificationManagerService.isCallerSystemOrPhone()) {
                NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
                if (str != null) {
                    synchronized (NotificationManagerService.this.mNotificationLock) {
                        try {
                            Iterator it = ((ArrayList) notificationListeners.getServices()).iterator();
                            while (it.hasNext()) {
                                if (str.equals(((ManagedServices.ManagedServiceInfo) it.next()).component.getPackageName())) {
                                }
                            }
                        } finally {
                        }
                    }
                } else {
                    notificationListeners.getClass();
                }
                throw new SecurityException("Only available for notification listeners");
            }
            return NotificationManagerService.this.mPreferencesHelper.mHideSilentStatusBarIcons;
        }

        public final void silenceNotificationSound() {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            ((AnonymousClass2) NotificationManagerService.this.mNotificationDelegate).clearEffects();
        }

        public final void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.snoozeNotificationInt(callingUid, iNotificationListener, str, -1L, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.snoozeNotificationInt(callingUid, iNotificationListener, str, j, null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) {
            Slog.e("NotificationService", "startEdgeLighting");
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallFromPackage(str);
            EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "startEdgeLighting");
            EdgeLightingClientManager edgeLightingClientManager = edgeLightingManager.mEdgeLightingClientManager;
            if (edgeLightingClientManager.isAvailableEdgeLighting(7)) {
                edgeLightingClientManager.startEdgeLightingInternal(str, semEdgeLightingInfo, 0);
            } else {
                Slog.d("EdgeLightingClientManager", "startEdgeLighting : edge lighting is disable");
            }
        }

        public final void stopEdgeLighting(String str, IBinder iBinder) {
            Slog.e("NotificationService", "stopEdgeLighting");
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallFromPackage(str);
            EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "stopEdgeLighting");
            EdgeLightingClientManager edgeLightingClientManager = edgeLightingManager.mEdgeLightingClientManager;
            if (edgeLightingClientManager.isAvailableEdgeLighting(7)) {
                edgeLightingClientManager.stopEdgeLightingInternal(0, str);
            } else {
                Slog.d("EdgeLightingClientManager", "stopEdgeLighting : edge lighting is disable");
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x004d A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:4:0x0016, B:6:0x001a, B:9:0x0040, B:10:0x0047, B:12:0x004d, B:15:0x0055, B:25:0x0061, B:26:0x0069, B:30:0x006b, B:32:0x0073, B:33:0x0082, B:34:0x0086, B:36:0x0021), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0061 A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:4:0x0016, B:6:0x001a, B:9:0x0040, B:10:0x0047, B:12:0x004d, B:15:0x0055, B:25:0x0061, B:26:0x0069, B:30:0x006b, B:32:0x0073, B:33:0x0082, B:34:0x0086, B:36:0x0021), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x006b A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:4:0x0016, B:6:0x001a, B:9:0x0040, B:10:0x0047, B:12:0x004d, B:15:0x0055, B:25:0x0061, B:26:0x0069, B:30:0x006b, B:32:0x0073, B:33:0x0082, B:34:0x0086, B:36:0x0021), top: B:3:0x0016 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void unbindEdgeLightingService(android.os.IBinder r6, java.lang.String r7) {
            /*
                r5 = this;
                com.android.server.notification.NotificationManagerService r5 = com.android.server.notification.NotificationManagerService.this
                com.android.server.notification.edgelighting.EdgeLightingManager r5 = r5.mEdgeLightingManager
                com.android.server.notification.edgelighting.EdgeLightingManager$SecurityPolicy r0 = r5.mSecurityPolicy
                r0.enforceCallingPermissionFromHost()
                com.android.server.notification.edgelighting.EdgeLightingManager$SecurityPolicy r0 = r5.mSecurityPolicy
                r0.enforceCallFromPackage(r7)
                com.android.server.notification.edgelighting.EdgeLightingClientManager r5 = r5.mEdgeLightingClientManager
                java.lang.String r0 = "unbind : pkg = "
                java.util.ArrayList r1 = r5.mHosts
                monitor-enter(r1)
                boolean r2 = com.android.server.notification.edgelighting.EdgeLightingHistory.IS_DEV_DEBUG     // Catch: java.lang.Throwable -> L1f
                if (r2 != 0) goto L21
                boolean r2 = com.android.server.notification.edgelighting.EdgeLightingClientManager.DEBUG     // Catch: java.lang.Throwable -> L1f
                if (r2 == 0) goto L40
                goto L21
            L1f:
                r5 = move-exception
                goto L88
            L21:
                java.lang.String r2 = "EdgeLightingClientManager"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
                r3.<init>(r0)     // Catch: java.lang.Throwable -> L1f
                r3.append(r7)     // Catch: java.lang.Throwable -> L1f
                java.lang.String r0 = ", mHosts = "
                r3.append(r0)     // Catch: java.lang.Throwable -> L1f
                java.util.ArrayList r0 = r5.mHosts     // Catch: java.lang.Throwable -> L1f
                int r0 = r0.size()     // Catch: java.lang.Throwable -> L1f
                r3.append(r0)     // Catch: java.lang.Throwable -> L1f
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L1f
                android.util.Slog.d(r2, r0)     // Catch: java.lang.Throwable -> L1f
            L40:
                java.util.ArrayList r0 = r5.mHosts     // Catch: java.lang.Throwable -> L1f
                java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L1f
                r2 = 0
            L47:
                boolean r3 = r0.hasNext()     // Catch: java.lang.Throwable -> L1f
                if (r3 == 0) goto L5f
                java.lang.Object r3 = r0.next()     // Catch: java.lang.Throwable -> L1f
                com.android.server.notification.edgelighting.EdgeLightingClientManager$EdgeLightingHost r3 = (com.android.server.notification.edgelighting.EdgeLightingClientManager.EdgeLightingHost) r3     // Catch: java.lang.Throwable -> L1f
                if (r3 == 0) goto L47
                android.os.IBinder r4 = r3.token     // Catch: java.lang.Throwable -> L1f
                boolean r4 = r6.equals(r4)     // Catch: java.lang.Throwable -> L1f
                if (r4 == 0) goto L47
                r2 = r3
                goto L47
            L5f:
                if (r2 != 0) goto L6b
                java.lang.String r5 = "EdgeLightingClientManager"
                java.lang.String r6 = "unbind : cannot find the matched host"
                android.util.Slog.w(r5, r6)     // Catch: java.lang.Throwable -> L1f
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L1f
                goto L87
            L6b:
                java.util.ArrayList r0 = r5.mHosts     // Catch: java.lang.Throwable -> L1f
                boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L1f
                if (r0 != 0) goto L82
                java.util.ArrayList r5 = r5.mHosts     // Catch: java.lang.Throwable -> L1f
                r5.remove(r2)     // Catch: java.lang.Throwable -> L1f
                com.android.server.notification.edgelighting.EdgeLightingHistory r5 = com.android.server.notification.edgelighting.EdgeLightingHistory.getInstance()     // Catch: java.lang.Throwable -> L1f
                java.lang.String r0 = "unbind."
                r5.updateHostHistory(r7, r0)     // Catch: java.lang.Throwable -> L1f
            L82:
                r5 = 0
                r6.unlinkToDeath(r2, r5)     // Catch: java.lang.Throwable -> L1f
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L1f
            L87:
                return
            L88:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L1f
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.unbindEdgeLightingService(android.os.IBinder, java.lang.String):void");
        }

        public final void unlockAllNotificationChannels() {
            NotificationManagerService.this.getClass();
            NotificationManagerService.checkCallerIsSystem();
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    int size = preferencesHelper.mPackagePreferences.size();
                    for (int i = 0; i < size; i++) {
                        Iterator it = ((PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i)).channels.values().iterator();
                        while (it.hasNext()) {
                            ((NotificationChannel) it.next()).unlockFields(4);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void unlockNotificationChannel(String str, int i, String str2) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            Objects.requireNonNull(str2);
            synchronized (preferencesHelper.mLock) {
                NotificationChannel notificationChannel = (NotificationChannel) preferencesHelper.getOrCreatePackagePreferencesLocked(i, str).channels.get(str2);
                if (notificationChannel == null || notificationChannel.isDeleted()) {
                    throw new IllegalArgumentException("Channel does not exist");
                }
                notificationChannel.unlockFields(4);
            }
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void unregisterCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) {
            unregisterCallNotificationEventListener_enforcePermission();
            synchronized (NotificationManagerService.this.mCallNotificationEventCallbacks) {
                try {
                    int identifier = userHandle.getIdentifier() != -2 ? userHandle.getIdentifier() : NotificationManagerService.this.mAmi.getCurrentUserId();
                    ArrayMap arrayMap = (ArrayMap) NotificationManagerService.this.mCallNotificationEventCallbacks.get(str);
                    if (arrayMap == null) {
                        return;
                    }
                    RemoteCallbackList remoteCallbackList = (RemoteCallbackList) arrayMap.get(Integer.valueOf(identifier));
                    if (remoteCallbackList == null) {
                        return;
                    }
                    if (!remoteCallbackList.unregister(iCallNotificationEventCallback)) {
                        Log.e("NotificationService", "unregisterCallNotificationEventListener listener not found for: " + str + " " + userHandle + " " + iCallNotificationEventCallback);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterEdgeLightingListener(IBinder iBinder, String str) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallFromPackage(str);
            EdgeLightingManager.SecurityPolicy.m735$$Nest$menforceCallingPermission(securityPolicy, "unregisterListener");
            edgeLightingManager.mEdgeLightingClientManager.mEdgeLightingListenerManager.unregister(iBinder, str);
        }

        public final void unregisterListener(INotificationListener iNotificationListener, int i) {
            ServiceConnection serviceConnection;
            NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            notificationListeners.checkNotNull(iNotificationListener);
            ManagedServices.ManagedServiceInfo removeServiceImpl = notificationListeners.removeServiceImpl(iNotificationListener, i);
            if (removeServiceImpl == null || (serviceConnection = removeServiceImpl.connection) == null || ManagedServices.this != notificationListeners) {
                return;
            }
            notificationListeners.unbindService(serviceConnection, removeServiceImpl.component, removeServiceImpl.userid);
        }

        public final void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.this.unsnoozeNotificationInt(str, NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener), false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) {
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

        public final boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) {
            validateAutomaticZenRule(str, automaticZenRule);
            enforcePolicyAccess(Binder.getCallingUid(), "updateAutomaticZenRule");
            enforceUserOriginOnlyFromSystem("updateAutomaticZenRule", z);
            ZenModeHelper zenModeHelper = NotificationManagerService.this.mZenModeHelper;
            int computeZenOrigin = computeZenOrigin(z);
            int callingUid = Binder.getCallingUid();
            zenModeHelper.getClass();
            ZenModeHelper.requirePublicOrigin(computeZenOrigin, "updateAutomaticZenRule");
            if (str == null) {
                throw new IllegalArgumentException("ruleId cannot be null");
            }
            synchronized (zenModeHelper.mConfigLock) {
                try {
                    if (zenModeHelper.mConfig == null) {
                        return false;
                    }
                    if (ZenModeHelper.DEBUG) {
                        Log.d("ZenModeHelper", "updateAutomaticZenRule zenRule=" + automaticZenRule + " reason=updateAutomaticZenRule");
                    }
                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) zenModeHelper.mConfig.automaticRules.get(str);
                    if (zenRule == null || !zenModeHelper.canManageAutomaticZenRule(zenRule)) {
                        throw new SecurityException("Cannot update rules not owned by your condition provider");
                    }
                    ZenModeConfig copy = zenModeHelper.mConfig.copy();
                    ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                    Objects.requireNonNull(zenRule2);
                    if (!android.app.Flags.modesApi() && zenRule2.enabled != automaticZenRule.isEnabled()) {
                        zenModeHelper.dispatchOnAutomaticRuleStatusChanged(zenModeHelper.mConfig.user, automaticZenRule.isEnabled() ? 1 : 2, zenRule2.getPkg(), str);
                    }
                    boolean populateZenRule = zenModeHelper.populateZenRule(zenRule2.pkg, automaticZenRule, zenRule2, computeZenOrigin, false);
                    if (!android.app.Flags.modesApi() || populateZenRule) {
                        return zenModeHelper.setConfigLocked(copy, computeZenOrigin, "updateAutomaticZenRule", zenRule2.component, true, callingUid);
                    }
                    return true;
                } finally {
                }
            }
        }

        public final void updateCancelEvent(int i, String str, boolean z) {
            enforcePolicyAccess(Binder.getCallingUid(), "updateCancelEvent");
            NotificationManagerService.this.mHistoryManager.updateCancelEvent(i, str, z);
        }

        public final void updateEdgeLightingPackageList(String str, List list) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            edgeLightingManager.mSecurityPolicy.enforceCallingPermissionFromHost();
            edgeLightingManager.mSecurityPolicy.enforceCallFromPackage(str);
            EdgeLightingClientManager edgeLightingClientManager = edgeLightingManager.mEdgeLightingClientManager;
            synchronized (edgeLightingClientManager.mEdgeLightingList) {
                try {
                    int size = ((ArrayList) edgeLightingClientManager.mEdgeLightingList).size();
                    ((ArrayList) edgeLightingClientManager.mEdgeLightingList).clear();
                    if (list != null) {
                        ((ArrayList) edgeLightingClientManager.mEdgeLightingList).addAll(list);
                    }
                    if (size == 0 && ((ArrayList) edgeLightingClientManager.mEdgeLightingList).size() > 0) {
                        edgeLightingClientManager.mEdgeLightingListenerManager.startEdgeLighting();
                    } else if (size != 0 && ((ArrayList) edgeLightingClientManager.mEdgeLightingList).size() == 0) {
                        edgeLightingClientManager.mEdgeLightingListenerManager.stopEdgeLighting();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) {
            EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
            if (edgeLightingPolicy == null) {
                edgeLightingManager.getClass();
                Slog.d(EdgeLightingManager.TAG, "updateEdgeLightingPolicy : policy is null");
                return;
            }
            EdgeLightingManager.SecurityPolicy securityPolicy = edgeLightingManager.mSecurityPolicy;
            securityPolicy.enforceCallingPermissionFromHost();
            securityPolicy.enforceCallFromPackage(str);
            EdgeLightingPolicyManager edgeLightingPolicyManager = edgeLightingManager.mEdgeLightingPolicyManager;
            EdgeLightingPolicyRepository edgeLightingPolicyRepository = edgeLightingPolicyManager.mWhitePolicy;
            synchronized (edgeLightingPolicyRepository.mRepository) {
                edgeLightingPolicyRepository.mRepository.clear();
            }
            EdgeLightingPolicyRepository edgeLightingPolicyRepository2 = edgeLightingPolicyManager.mBlackPolicy;
            synchronized (edgeLightingPolicyRepository2.mRepository) {
                edgeLightingPolicyRepository2.mRepository.clear();
            }
            edgeLightingPolicyManager.mPriorityPolicy.clear();
            edgeLightingPolicyManager.mPolicyType = edgeLightingPolicy.getPolicyType();
            edgeLightingPolicyManager.mPolicyVersion = edgeLightingPolicy.getPolicyVersion();
            Iterator it = edgeLightingPolicy.getEdgeLightingPolicyInfoList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                EdgeLightingPolicyInfo edgeLightingPolicyInfo = (EdgeLightingPolicyInfo) it.next();
                int i = edgeLightingPolicyInfo.category;
                if (i == 1) {
                    edgeLightingPolicyRepository.updatePolicy(edgeLightingPolicyInfo);
                } else if (i == 2) {
                    edgeLightingPolicyRepository2.updatePolicy(edgeLightingPolicyInfo);
                }
            }
            String stringForUser = Settings.Secure.getStringForUser(edgeLightingPolicyManager.mContext.getContentResolver(), "edge_lighting_recommend_app_list", -2);
            if (stringForUser != null) {
                for (String str2 : stringForUser.split(",")) {
                    edgeLightingPolicyManager.mPriorityPolicy.add(str2);
                }
            }
            edgeLightingManager.mEdgeLightingClientManager.mIsConnectedMode = (edgeLightingPolicy.getPolicyType() & 8) == 0;
        }

        public final void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) {
            NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            Objects.requireNonNull(notificationChannel);
            NotificationManagerService.this.updateNotificationChannelInt(str, i, notificationChannel, false);
        }

        public final void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) {
            Objects.requireNonNull(notificationChannel);
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            int uidForPackageAndUser = getUidForPackageAndUser(str, userHandle);
            String id = notificationChannel.getId();
            preferencesHelper.getClass();
            NotificationChannel conversationNotificationChannel = preferencesHelper.getConversationNotificationChannel(str, uidForPackageAndUser, id, null, true, true);
            final int callingUid = Binder.getCallingUid();
            final Uri sound = notificationChannel.getSound();
            Uri sound2 = conversationNotificationChannel != null ? conversationNotificationChannel.getSound() : null;
            if (sound != null && !Objects.equals(sound2, sound)) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        NotificationManagerService.AnonymousClass16 anonymousClass16 = NotificationManagerService.AnonymousClass16.this;
                        int i = callingUid;
                        Uri uri = sound;
                        UriGrantsManagerInternal uriGrantsManagerInternal = NotificationManagerService.this.mUgmInternal;
                        ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal).checkGrantUriPermission(i, null, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
                    }
                });
            }
            NotificationManagerService.this.updateNotificationChannelInt(str, getUidForPackageAndUser(str, userHandle), notificationChannel, true);
        }

        public final void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) {
            enforceSystemOrSystemUI("Caller not system or systemui");
            NotificationManagerService.this.createNotificationChannelGroup(str, i, notificationChannelGroup, false, false);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) {
            Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            NotificationManagerService.this.createNotificationChannelGroup(str, getUidForPackageAndUser(str, userHandle), notificationChannelGroup, false, true);
            NotificationManagerService.this.handleSavePolicyFile();
        }

        public final void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) {
            NotificationManagerService.m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService.this, str);
            int callingUid = Binder.getCallingUid();
            if (NotificationManagerService.this.mPackageManagerClient.checkPermission("com.samsung.android.permission.SEM_UPDATE_NOTIFICATION_CHANNELS", str) != 0) {
                throw new SecurityException("Requires SEM_UPDATE_NOTIFICATION_CHANNELS permission");
            }
            List list = parceledListSlice.getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationChannel notificationChannel = (NotificationChannel) list.get(i);
                Objects.requireNonNull(notificationChannel, "channel in list is null");
                notificationChannel.lockFields(512);
                Log.d("NotificationService", "updateNotificationChannels from " + str + " channel:" + notificationChannel);
                NotificationManagerService.this.updateNotificationChannelInt(str, callingUid, notificationChannel, false);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x002b, code lost:
        
            if (r6.this$0.isCallerSystemOrSystemUi() != false) goto L24;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void validateAutomaticZenRule(java.lang.String r7, android.app.AutomaticZenRule r8) {
            /*
                r6 = this;
                java.lang.String r0 = "automaticZenRule is null"
                java.util.Objects.requireNonNull(r8, r0)
                java.lang.String r0 = r8.getName()
                java.lang.String r1 = "Name is null"
                java.util.Objects.requireNonNull(r0, r1)
                r8.validate()
                boolean r0 = android.app.Flags.modesApi()
                java.lang.String r1 = "Rule must have a ConditionProviderService and/or configuration activity"
                if (r0 == 0) goto L41
                if (r7 == 0) goto L2e
                boolean r0 = com.android.server.notification.ZenModeHelper.DEBUG
                java.lang.String r0 = "implicit_"
                boolean r7 = r7.startsWith(r0)
                if (r7 == 0) goto L2e
                com.android.server.notification.NotificationManagerService r7 = com.android.server.notification.NotificationManagerService.this
                boolean r7 = r7.isCallerSystemOrSystemUi()
                if (r7 == 0) goto L2e
                goto L54
            L2e:
                android.content.ComponentName r7 = r8.getOwner()
                if (r7 != 0) goto L54
                android.content.ComponentName r7 = r8.getConfigurationActivity()
                if (r7 == 0) goto L3b
                goto L54
            L3b:
                java.lang.NullPointerException r6 = new java.lang.NullPointerException
                r6.<init>(r1)
                throw r6
            L41:
                android.content.ComponentName r7 = r8.getOwner()
                if (r7 != 0) goto L54
                android.content.ComponentName r7 = r8.getConfigurationActivity()
                if (r7 == 0) goto L4e
                goto L54
            L4e:
                java.lang.NullPointerException r6 = new java.lang.NullPointerException
                r6.<init>(r1)
                throw r6
            L54:
                android.net.Uri r7 = r8.getConditionId()
                java.lang.String r0 = "ConditionId is null"
                java.util.Objects.requireNonNull(r7, r0)
                boolean r7 = android.app.Flags.modesApi()
                if (r7 == 0) goto Lcb
                com.android.server.notification.NotificationManagerService r7 = com.android.server.notification.NotificationManagerService.this
                boolean r7 = r7.isCallerSystemOrSystemUi()
                if (r7 == 0) goto L6c
                return
            L6c:
                int r1 = android.os.Binder.getCallingUid()
                int r2 = android.os.UserHandle.getUserId(r1)
                int r7 = r8.getType()
                r0 = 7
                if (r7 != r0) goto L95
                com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda5 r7 = new com.android.server.notification.NotificationManagerService$16$$ExternalSyntheticLambda5
                r7.<init>()
                java.lang.Object r6 = android.os.Binder.withCleanCallingIdentity(r7)
                java.lang.Boolean r6 = (java.lang.Boolean) r6
                boolean r6 = r6.booleanValue()
                if (r6 == 0) goto L8d
                goto Lcb
            L8d:
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                java.lang.String r7 = "Only Device Owners can use AutomaticZenRules with TYPE_MANAGED"
                r6.<init>(r7)
                throw r6
            L95:
                int r7 = r8.getType()
                r8 = 3
                if (r7 != r8) goto Lcb
                com.android.server.notification.NotificationManagerService r7 = com.android.server.notification.NotificationManagerService.this
                android.content.Context r7 = r7.getContext()
                android.content.res.Resources r7 = r7.getResources()
                r8 = 17039408(0x1040030, float:2.4244706E-38)
                java.lang.String r5 = r7.getString(r8)
                boolean r7 = android.text.TextUtils.isEmpty(r5)
                if (r7 != 0) goto Lc3
                com.android.server.notification.NotificationManagerService r6 = com.android.server.notification.NotificationManagerService.this
                android.content.pm.PackageManagerInternal r6 = r6.mPackageManagerInternal
                r0 = r6
                com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r0 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r0
                r3 = 0
                boolean r6 = r0.isSameApp(r1, r2, r3, r5)
                if (r6 == 0) goto Lc3
                goto Lcb
            Lc3:
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                java.lang.String r7 = "Only the 'Wellbeing' package can use AutomaticZenRules with TYPE_BEDTIME"
                r6.<init>(r7)
                throw r6
            Lcb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass16.validateAutomaticZenRule(java.lang.String, android.app.AutomaticZenRule):void");
        }

        public final void verifyPrivilegedListener(INotificationListener iNotificationListener, UserHandle userHandle, boolean z) {
            ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                checkServiceTokenLocked = NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.getClass();
            if (!notificationManagerService.hasCompanionDevice(checkServiceTokenLocked.userid, checkServiceTokenLocked.component.getPackageName(), null)) {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    if (z) {
                        try {
                            NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                            IInterface iInterface = checkServiceTokenLocked.service;
                            if (iInterface == null) {
                                notificationAssistants.getClass();
                            } else if (notificationAssistants.getServiceFromTokenLocked(iInterface) != null) {
                            }
                        } finally {
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$17, reason: invalid class name */
    public final class AnonymousClass17 implements NotificationManagerInternal {
        public AnonymousClass17() {
        }

        public final NotificationChannel getNotificationChannel(int i, String str, String str2) {
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            preferencesHelper.getClass();
            Objects.requireNonNull(str);
            return preferencesHelper.getConversationNotificationChannel(str, i, str2, null, true, false);
        }

        public final NotificationChannelGroup getNotificationChannelGroup(int i, String str, String str2) {
            NotificationChannel notificationChannel;
            NotificationChannelGroup notificationChannelGroup;
            PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked == null || (notificationChannel = (NotificationChannel) packagePreferencesLocked.channels.get(str2)) == null || notificationChannel.isDeleted() || notificationChannel.getGroup() == null || (notificationChannelGroup = (NotificationChannelGroup) packagePreferencesLocked.groups.get(notificationChannel.getGroup())) == null) {
                        return null;
                    }
                    return notificationChannelGroup;
                } finally {
                }
            }
        }

        public final void onConversationRemoved(int i, String str, Set set) {
            int i2;
            int i3;
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.getClass();
            NotificationManagerService.checkCallerIsSystem();
            Preconditions.checkStringNotEmpty(str);
            NotificationHistoryManager notificationHistoryManager = notificationManagerService.mHistoryManager;
            synchronized (notificationHistoryManager.mLock) {
                try {
                    int userId = UserHandle.getUserId(i);
                    NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = notificationHistoryManager.getUserHistoryAndInitializeIfNeededLocked(userId);
                    if (userHistoryAndInitializeIfNeededLocked == null) {
                        Slog.w("NotificationHistory", "Attempted to remove conversation for locked/gone/disabled user " + userId);
                    } else {
                        userHistoryAndInitializeIfNeededLocked.mFileWriteHandler.post(userHistoryAndInitializeIfNeededLocked.new RemoveConversationRunnable(str, set));
                    }
                } finally {
                }
            }
            PreferencesHelper preferencesHelper = notificationManagerService.mPreferencesHelper;
            preferencesHelper.getClass();
            ArrayList arrayList = new ArrayList();
            synchronized (preferencesHelper.mLock) {
                try {
                    PreferencesHelper.PackagePreferences packagePreferencesLocked = preferencesHelper.getPackagePreferencesLocked(i, str);
                    if (packagePreferencesLocked != null) {
                        int size = packagePreferencesLocked.channels.size();
                        int i4 = 0;
                        while (i4 < size) {
                            NotificationChannel notificationChannel = (NotificationChannel) packagePreferencesLocked.channels.valueAt(i4);
                            if (notificationChannel.getConversationId() == null || !set.contains(notificationChannel.getConversationId())) {
                                i2 = i4;
                                i3 = size;
                            } else {
                                notificationChannel.setDeleted(true);
                                notificationChannel.setDeletedTimeMs(System.currentTimeMillis());
                                LogMaker channelLog = PreferencesHelper.getChannelLog(notificationChannel, str);
                                channelLog.setType(2);
                                MetricsLogger.action(channelLog);
                                NotificationChannelLogger notificationChannelLogger = preferencesHelper.mNotificationChannelLogger;
                                notificationChannelLogger.getClass();
                                NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent = NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_CREATED;
                                i2 = i4;
                                i3 = size;
                                ((NotificationChannelLoggerImpl) notificationChannelLogger).logNotificationChannel(notificationChannel.getConversationId() != null ? NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_CONVERSATION_DELETED : NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_DELETED, notificationChannel, i, str, NotificationChannelLogger.getLoggingImportance(notificationChannel, notificationChannel.getImportance()), 0);
                                arrayList.add(notificationChannel.getId());
                            }
                            i4 = i2 + 1;
                            size = i3;
                        }
                        if (!arrayList.isEmpty() && preferencesHelper.mCurrentUserHasChannelsBypassingDnd) {
                            preferencesHelper.updateCurrentUserHasChannelsBypassingDnd(1000, true);
                        }
                    }
                } finally {
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                notificationManagerService.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, str, (String) it.next(), UserHandle.getUserId(i), 20);
            }
            notificationManagerService.handleSavePolicyFile();
        }

        public final void removeBitmaps() {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    Iterator it = NotificationManagerService.this.mNotificationList.iterator();
                    while (it.hasNext()) {
                        NotificationRecord notificationRecord = (NotificationRecord) it.next();
                        long postTime = notificationRecord.sbn.getPostTime();
                        if (System.currentTimeMillis() - postTime > (NotificationManagerService.this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.DEBUG_SHORT_BITMAP_DURATION) ? Duration.ofSeconds(5L).toMillis() : NotificationManagerService.BITMAP_DURATION.toMillis())) {
                            NotificationManagerService.m720$$Nest$mremoveBitmapAndRepost(NotificationManagerService.this, notificationRecord);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeFlagFromNotificationLocked(int i, int i2, int i3, String str) {
            int i4;
            int i5;
            boolean z;
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            synchronized (notificationManagerService.mNotificationLock) {
                try {
                    int size = notificationManagerService.mNotificationList.size();
                    i5 = 0;
                    for (int i6 = 0; i6 < size; i6++) {
                        NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationList.get(i6);
                        if (notificationRecord.sbn.getPackageName().equals(str) && notificationRecord.sbn.getUserId() == i2) {
                            i5++;
                        }
                    }
                    int size2 = notificationManagerService.mEnqueuedNotifications.size();
                    for (int i7 = 0; i7 < size2; i7++) {
                        NotificationRecord notificationRecord2 = (NotificationRecord) notificationManagerService.mEnqueuedNotifications.get(i7);
                        if (notificationRecord2.sbn.getPackageName().equals(str) && notificationRecord2.sbn.getUserId() == i2) {
                            i5++;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (i5 > 50) {
                NotificationManagerService.this.mUsageStats.registerOverCountQuota(str);
                z = true;
            } else {
                z = false;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i, "removeFlagFromNotificationLocked pkg = ", str, " notificationId = ", ", flag = "), i3, "NotificationService");
            if (z) {
                NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(i, i2, str, null);
                if (findNotificationLocked != null) {
                    if (NotificationManagerService.DBG) {
                        String str2 = i3 == 64 ? "FGS" : "UIJ";
                        Slog.d("NotificationService", XmlUtils$$ExternalSyntheticOutline0.m("Remove ", str2, " flag not allow. Cancel ", str2, " notification"));
                    }
                    NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked);
                    NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, false, 8, -1, -1, true, null, SystemClock.elapsedRealtime());
                    return;
                }
                return;
            }
            ArrayList arrayList = NotificationManagerService.this.mEnqueuedNotifications;
            ArrayList arrayList2 = new ArrayList();
            int size3 = arrayList.size();
            for (int i8 = 0; i8 < size3; i8++) {
                NotificationRecord notificationRecord3 = (NotificationRecord) arrayList.get(i8);
                if (NotificationManagerService.notificationMatchesUserId(i2, notificationRecord3, false) && notificationRecord3.sbn.getId() == i && TextUtils.equals(notificationRecord3.sbn.getTag(), null) && notificationRecord3.sbn.getPackageName().equals(str)) {
                    arrayList2.add(notificationRecord3);
                }
            }
            for (i4 = 0; i4 < arrayList2.size(); i4++) {
                NotificationRecord notificationRecord4 = (NotificationRecord) arrayList2.get(i4);
                if (notificationRecord4 != null) {
                    StatusBarNotification statusBarNotification = notificationRecord4.sbn;
                    statusBarNotification.getNotification().flags = notificationRecord4.mOriginalFlags & (~i3);
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("removeFlag from mEnqueuedNotifications , flag = "), statusBarNotification.getNotification().flags, "NotificationService");
                }
            }
            NotificationRecord findNotificationByListLocked = NotificationManagerService.findNotificationByListLocked(NotificationManagerService.this.mNotificationList, str, null, i, i2);
            if (findNotificationByListLocked != null) {
                StatusBarNotification statusBarNotification2 = findNotificationByListLocked.sbn;
                statusBarNotification2.getNotification().flags = (~i3) & findNotificationByListLocked.mOriginalFlags;
                NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                notificationManagerService2.mRankingHelper.sort(notificationManagerService2.mNotificationList);
                NotificationManagerService.this.mListeners.notifyPostedLocked(findNotificationByListLocked, findNotificationByListLocked);
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("removeFlag from mNotificationList , flag = "), statusBarNotification2.getNotification().flags, "NotificationService");
            }
        }

        public final void removeForegroundServiceFlagFromNotification(int i, int i2, String str) {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.getClass();
            NotificationManagerService.checkCallerIsSystem();
            notificationManagerService.mHandler.post(new NotificationManagerService$17$$ExternalSyntheticLambda0(this, str, i, i2, 1));
        }

        public final void removeUserInitiatedJobFlagFromNotification(int i, int i2, String str) {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.getClass();
            NotificationManagerService.checkCallerIsSystem();
            notificationManagerService.mHandler.post(new NotificationManagerService$17$$ExternalSyntheticLambda0(this, str, i, i2, 0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$18, reason: invalid class name */
    public final class AnonymousClass18 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NotificationManagerService this$0;
        public final /* synthetic */ Bundle val$extra;
        public final /* synthetic */ int val$id;
        public final /* synthetic */ String val$pkg;
        public final /* synthetic */ int val$userId;

        public /* synthetic */ AnonymousClass18(NotificationManagerService notificationManagerService, String str, int i, Bundle bundle, int i2, int i3) {
            this.$r8$classId = i3;
            this.this$0 = notificationManagerService;
            this.val$pkg = str;
            this.val$id = i;
            this.val$extra = bundle;
            this.val$userId = i2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    NotificationListeners notificationListeners = this.this$0.mListeners;
                    String str = this.val$pkg;
                    int i = this.val$id;
                    Bundle bundle = this.val$extra;
                    int i2 = this.val$userId;
                    synchronized (NotificationManagerService.this.mNotificationList) {
                        try {
                            Iterator it = ((ArrayList) notificationListeners.getServices()).iterator();
                            while (it.hasNext()) {
                                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                                if (NotificationManagerService.EDGE_NOTIFICATION_COMPONENT.equals(managedServiceInfo.component) && managedServiceInfo.userid == i2) {
                                    NotificationManagerService.this.mHandler.post(new NotificationListeners.AnonymousClass1(notificationListeners, managedServiceInfo, str, i, bundle, 1));
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    NotificationListeners notificationListeners2 = this.this$0.mListeners;
                    String str2 = this.val$pkg;
                    int i3 = this.val$id;
                    Bundle bundle2 = this.val$extra;
                    int i4 = this.val$userId;
                    synchronized (NotificationManagerService.this.mNotificationList) {
                        try {
                            Iterator it2 = ((ArrayList) notificationListeners2.getServices()).iterator();
                            while (it2.hasNext()) {
                                ManagedServices.ManagedServiceInfo managedServiceInfo2 = (ManagedServices.ManagedServiceInfo) it2.next();
                                if (NotificationManagerService.EDGE_NOTIFICATION_COMPONENT.equals(managedServiceInfo2.component) && managedServiceInfo2.userid == i4) {
                                    NotificationManagerService.this.mHandler.post(new NotificationListeners.AnonymousClass1(notificationListeners2, managedServiceInfo2, str2, i3, bundle2, 0));
                                }
                            }
                        } finally {
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 implements NotificationDelegate {
        public /* synthetic */ AnonymousClass2() {
        }

        public void clearEffects() {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "clearEffects");
                    }
                    NotificationAttentionHelper notificationAttentionHelper = NotificationManagerService.this.mAttentionHelper;
                    notificationAttentionHelper.clearSoundLocked();
                    notificationAttentionHelper.clearVibrateLocked();
                    notificationAttentionHelper.mLights.clear();
                    notificationAttentionHelper.updateLightsLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void clearInlineReplyUriPermissions(String str) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    InlineReplyUriRecord inlineReplyUriRecord = (InlineReplyUriRecord) NotificationManagerService.this.mInlineReplyRecordsByKey.get(str);
                    if (inlineReplyUriRecord != null) {
                        NotificationManagerService notificationManagerService = NotificationManagerService.this;
                        IBinder iBinder = inlineReplyUriRecord.mPermissionOwner;
                        int identifier = inlineReplyUriRecord.mUser.getIdentifier();
                        if (UserManager.isHeadlessSystemUserMode() && identifier == -1) {
                            identifier = ActivityManager.getCurrentUser();
                        } else if (identifier == -1) {
                            identifier = 0;
                        }
                        notificationManagerService.destroyPermissionOwner(identifier, iBinder, "INLINE_REPLY: " + inlineReplyUriRecord.mKey);
                        NotificationManagerService.this.mInlineReplyRecordsByKey.remove(str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public NotificationRecord getNotificationByKey(String str) {
            NotificationRecord notificationRecord;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
            }
            return notificationRecord;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void grantInlineReplyUriPermission(java.lang.String r11, android.net.Uri r12, android.os.UserHandle r13, java.lang.String r14, int r15) {
            /*
                r10 = this;
                java.lang.String r0 = "Cannot grant uri permission to unknown UID: "
                java.lang.String r1 = "INLINE_REPLY:"
                com.android.server.notification.NotificationManagerService r2 = com.android.server.notification.NotificationManagerService.this
                java.lang.Object r2 = r2.mNotificationLock
                monitor-enter(r2)
                com.android.server.notification.NotificationManagerService r3 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38
                android.util.ArrayMap r3 = r3.mInlineReplyRecordsByKey     // Catch: java.lang.Throwable -> L38
                java.lang.Object r3 = r3.get(r11)     // Catch: java.lang.Throwable -> L38
                com.android.server.notification.InlineReplyUriRecord r3 = (com.android.server.notification.InlineReplyUriRecord) r3     // Catch: java.lang.Throwable -> L38
                if (r3 != 0) goto L3a
                com.android.server.notification.InlineReplyUriRecord r3 = new com.android.server.notification.InlineReplyUriRecord     // Catch: java.lang.Throwable -> L38
                com.android.server.notification.NotificationManagerService r4 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38
                com.android.server.uri.UriGrantsManagerInternal r4 = r4.mUgmInternal     // Catch: java.lang.Throwable -> L38
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
                r5.<init>(r1)     // Catch: java.lang.Throwable -> L38
                r5.append(r11)     // Catch: java.lang.Throwable -> L38
                java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L38
                com.android.server.uri.UriGrantsManagerService$LocalService r4 = (com.android.server.uri.UriGrantsManagerService.LocalService) r4     // Catch: java.lang.Throwable -> L38
                android.os.IBinder r1 = r4.newUriPermissionOwner(r1)     // Catch: java.lang.Throwable -> L38
                r3.<init>(r1, r13, r14, r11)     // Catch: java.lang.Throwable -> L38
                com.android.server.notification.NotificationManagerService r13 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38
                android.util.ArrayMap r13 = r13.mInlineReplyRecordsByKey     // Catch: java.lang.Throwable -> L38
                r13.put(r11, r3)     // Catch: java.lang.Throwable -> L38
                goto L3a
            L38:
                r10 = move-exception
                goto L9e
            L3a:
                android.os.IBinder r5 = r3.mPermissionOwner     // Catch: java.lang.Throwable -> L38
                android.os.UserHandle r11 = r3.mUser     // Catch: java.lang.Throwable -> L38
                int r11 = r11.getIdentifier()     // Catch: java.lang.Throwable -> L38
                boolean r13 = android.os.UserManager.isHeadlessSystemUserMode()     // Catch: java.lang.Throwable -> L38
                r14 = 0
                r1 = -1
                if (r13 == 0) goto L52
                if (r11 != r1) goto L52
                int r11 = android.app.ActivityManager.getCurrentUser()     // Catch: java.lang.Throwable -> L38
            L50:
                r9 = r11
                goto L55
            L52:
                if (r11 != r1) goto L50
                r9 = r14
            L55:
                int r11 = android.os.UserHandle.getUserId(r15)     // Catch: java.lang.Throwable -> L38
                if (r11 == r9) goto L85
                com.android.server.notification.NotificationManagerService r11 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                android.content.pm.IPackageManager r11 = r11.mPackageManager     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                java.lang.String[] r11 = r11.getPackagesForUid(r15)     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                if (r11 != 0) goto L79
                java.lang.String r11 = "NotificationService"
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                r13.<init>(r0)     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                r13.append(r15)     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                java.lang.String r13 = r13.toString()     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                android.util.Log.e(r11, r13)     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                goto L85
            L77:
                r11 = move-exception
                goto L87
            L79:
                r11 = r11[r14]     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                com.android.server.notification.NotificationManagerService r13 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                android.content.pm.IPackageManager r13 = r13.mPackageManager     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
                r0 = 0
                int r15 = r13.getPackageUid(r11, r0, r9)     // Catch: java.lang.Throwable -> L38 android.os.RemoteException -> L77
            L85:
                r7 = r15
                goto L8f
            L87:
                java.lang.String r13 = "NotificationService"
                java.lang.String r14 = "Cannot talk to package manager"
                android.util.Log.e(r13, r14, r11)     // Catch: java.lang.Throwable -> L38
                goto L85
            L8f:
                android.util.ArraySet r11 = r3.mUris     // Catch: java.lang.Throwable -> L38
                r11.add(r12)     // Catch: java.lang.Throwable -> L38
                com.android.server.notification.NotificationManagerService r4 = com.android.server.notification.NotificationManagerService.this     // Catch: java.lang.Throwable -> L38
                java.lang.String r8 = r3.mPackageName     // Catch: java.lang.Throwable -> L38
                r6 = r12
                r4.grantUriPermission(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L38
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L38
                return
            L9e:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L38
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass2.grantInlineReplyUriPermission(java.lang.String, android.net.Uri, android.os.UserHandle, java.lang.String, int):void");
        }

        public boolean isActivityStartAllowed(int i, String str, Collection collection) {
            Preconditions.checkArgument(!r7.isEmpty());
            Iterator it = ((ArrayList) collection).iterator();
            while (it.hasNext()) {
                if (((IBinder) it.next()) != NotificationManagerService.ALLOWLIST_TOKEN) {
                    return true;
                }
            }
            String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Indirect notification activity start (trampoline) from ", str);
            if ((NotificationManagerService.this.mRoleObserver == null || !NotificationManagerService.this.mRoleObserver.isUidExemptFromTrampolineRestrictions(i)) ? CompatChanges.isChangeEnabled(167676448L, i) : CompatChanges.isChangeEnabled(227752274L, i)) {
                Slog.e("NotificationService", m + " blocked");
                return false;
            }
            Slog.w("NotificationService", m + ", this should be avoided for performance reasons");
            return true;
        }

        public void onBubbleMetadataFlagChanged(String str, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        Notification.BubbleMetadata bubbleMetadata = notificationRecord.sbn.getNotification().getBubbleMetadata();
                        if (bubbleMetadata == null) {
                            return;
                        }
                        if (i != bubbleMetadata.getFlags()) {
                            if (((bubbleMetadata.getFlags() ^ i) & 2) != 0) {
                                NotificationManagerService.this.mAttentionHelper.clearEffectsLocked(str);
                            }
                            bubbleMetadata.setFlags(i);
                            notificationRecord.sbn.getNotification().flags |= 8;
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            WorkerHandler workerHandler = notificationManagerService.mHandler;
                            int identifier = notificationRecord.sbn.getUser().getIdentifier();
                            NotificationManagerService.this.mPostNotificationTrackerFactory.getClass();
                            workerHandler.post(notificationManagerService.new EnqueueNotificationRunnable(identifier, notificationRecord, true, new PostNotificationTracker(null)));
                        }
                        NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                        SmartAlertController smartAlertController = notificationManagerService2.mSmartAlertController;
                        if (smartAlertController != null) {
                            smartAlertController.mHandler.post(smartAlertController.new AnonymousClass2(notificationManagerService2.mNotificationList));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0154 A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:7:0x001d, B:9:0x002a, B:10:0x003b, B:13:0x0040, B:19:0x00ac, B:20:0x00ce, B:22:0x0154, B:23:0x0191, B:26:0x00b8, B:27:0x00c2, B:28:0x00cc), top: B:6:0x001d }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onNotificationActionClick(int r32, int r33, java.lang.String r34, int r35, android.app.Notification.Action r36, com.android.internal.statusbar.NotificationVisibility r37, boolean r38) {
            /*
                Method dump skipped, instructions count: 405
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass2.onNotificationActionClick(int, int, java.lang.String, int, android.app.Notification$Action, com.android.internal.statusbar.NotificationVisibility, boolean):void");
        }

        public void onNotificationBubbleChanged(String str, boolean z, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        if (z) {
                            notificationRecord.sbn.getNotification().flags |= 8;
                            notificationRecord.mFlagBubbleRemoved = false;
                            if (notificationRecord.sbn.getNotification().getBubbleMetadata() != null) {
                                notificationRecord.sbn.getNotification().getBubbleMetadata().setFlags(i);
                            }
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            WorkerHandler workerHandler = notificationManagerService.mHandler;
                            int identifier = notificationRecord.sbn.getUser().getIdentifier();
                            NotificationManagerService.this.mPostNotificationTrackerFactory.getClass();
                            workerHandler.post(notificationManagerService.new EnqueueNotificationRunnable(identifier, notificationRecord, true, new PostNotificationTracker(null)));
                        } else {
                            notificationRecord.sbn.getNotification().flags &= -4097;
                            notificationRecord.mFlagBubbleRemoved = true;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onNotificationClear(int i, int i2, String str, int i3, String str2, int i4, int i5, NotificationVisibility notificationVisibility) {
            String str3;
            int i6;
            String str4;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str2);
                    if (notificationRecord != null) {
                        notificationRecord.mStats.setDismissalSurface(i4);
                        notificationRecord.mStats.setDismissalSentiment(i5);
                        str3 = notificationRecord.sbn.getTag();
                        i6 = notificationRecord.sbn.getId();
                    } else {
                        str3 = null;
                        i6 = 0;
                    }
                    str4 = str3;
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            notificationManagerService.mHandler.scheduleCancelNotification(notificationManagerService.new CancelNotificationRunnable(i, i2, str, str4, i6, 0, 8192, true, i3, 2, notificationVisibility.rank, notificationVisibility.count, null, SystemClock.elapsedRealtime()), 0);
            notificationVisibility.recycle();
        }

        public void onNotificationClick(int i, int i2, String str, NotificationVisibility notificationVisibility) {
            DeviceIdleManager deviceIdleManager = NotificationManagerService.this.mDeviceIdleManager;
            if (deviceIdleManager != null) {
                deviceIdleManager.endIdle("notification interaction");
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        Slog.w("NotificationService", "No notification with key: " + str);
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    MetricsLogger.action(notificationRecord.getLogMaker().setCategory(128).setType(4).addTaggedData(798, Integer.valueOf(notificationVisibility.rank)).addTaggedData(1395, Integer.valueOf(notificationVisibility.count)));
                    ((NotificationRecordLoggerImpl) NotificationManagerService.this.mNotificationRecordLogger).log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_CLICKED, notificationRecord);
                    EventLog.writeEvent(27520, str, Integer.valueOf((int) (currentTimeMillis - notificationRecord.mCreationTimeMs)), Integer.valueOf((int) (currentTimeMillis - notificationRecord.mUpdateTimeMs)), Integer.valueOf(notificationRecord.getExposureMs(currentTimeMillis)), Integer.valueOf(notificationVisibility.rank), Integer.valueOf(notificationVisibility.count));
                    StatusBarNotification statusBarNotification = notificationRecord.sbn;
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    notificationManagerService.mHandler.scheduleCancelNotification(notificationManagerService.new CancelNotificationRunnable(i, i2, statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), 16, 36928, false, notificationRecord.sbn.getUserId(), 1, notificationVisibility.rank, notificationVisibility.count, null, SystemClock.elapsedRealtime()), 0);
                    NotificationManagerService.this.mHistoryManager.updateCancelEvent(statusBarNotification.getNormalizedUserId(), str, false);
                    notificationVisibility.recycle();
                    NotificationManagerService.this.reportUserInteraction(notificationRecord);
                    NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                    notificationAssistants.getClass();
                    notificationAssistants.notifyAssistantLocked(notificationRecord.sbn, notificationRecord.getNotificationType(), new NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0(notificationAssistants, notificationRecord.sbn.getKey(), 0));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onNotificationDataUpdateFromPDC(List list) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    if (NotificationManagerService.this.mNotificationHighlightCore != null) {
                        Log.e("NotificationService", "Update most contacts. size= " + list.size());
                        NotificationManagerService.this.mNotificationHighlightCore.getClass();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onNotificationDirectReplied(String str) {
            String packageName;
            DeviceIdleManager deviceIdleManager = NotificationManagerService.this.mDeviceIdleManager;
            if (deviceIdleManager != null) {
                deviceIdleManager.endIdle("notification interaction");
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    packageName = notificationRecord != null ? notificationRecord.sbn.getPackageName() : null;
                } finally {
                }
            }
            int packageImportanceWithIdentity = (!android.app.Flags.lifetimeExtensionRefactor() || packageName == null) ? 0 : NotificationManagerService.this.getPackageImportanceWithIdentity(packageName);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord2 != null) {
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord2, notificationRecord2.sbn.getPackageName(), packageImportanceWithIdentity);
                        }
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            notificationRecord2.sbn.getNotification().flags |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
                        }
                        notificationRecord2.mStats.setDirectReplied();
                        NotificationManagerService.this.mMetricsLogger.write(notificationRecord2.getLogMaker().setCategory(1590).setType(4));
                        ((NotificationRecordLoggerImpl) NotificationManagerService.this.mNotificationRecordLogger).log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_DIRECT_REPLIED, notificationRecord2);
                        NotificationManagerService.this.reportUserInteraction(notificationRecord2);
                        NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                        notificationAssistants.getClass();
                        notificationAssistants.notifyAssistantLocked(notificationRecord2.sbn, notificationRecord2.getNotificationType(), new NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0(notificationAssistants, notificationRecord2.sbn.getKey(), 2));
                    }
                } finally {
                }
            }
        }

        public void onNotificationError(int i, int i2, final int i3, final int i4, final int i5, int i6, final String str, final String str2, final String str3) {
            boolean z;
            boolean z2;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(i3, i6, str, str2);
                    z = (findNotificationLocked == null || (findNotificationLocked.sbn.getNotification().flags & 64) == 0) ? false : true;
                    z2 = (findNotificationLocked == null || (findNotificationLocked.sbn.getNotification().flags & 32768) == 0) ? false : true;
                } catch (Throwable th) {
                    throw th;
                }
            }
            NotificationManagerService.this.cancelNotification(i, i2, str, str2, i3, 0, false, i6, 4, null);
            if (z || z2) {
                final int i7 = z ? 3 : 6;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$2$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        NotificationManagerService.AnonymousClass2 anonymousClass2 = NotificationManagerService.AnonymousClass2.this;
                        int i8 = i4;
                        int i9 = i5;
                        String str4 = str;
                        String str5 = str2;
                        int i10 = i3;
                        String str6 = str3;
                        int i11 = i7;
                        IActivityManager iActivityManager = NotificationManagerService.this.mAm;
                        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i10, "Bad notification(tag=", str5, ", id=", ") posted from package ");
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i8, str4, ", crashing app(uid=", ", pid=", m);
                        m.append(i9);
                        m.append("): ");
                        m.append(str6);
                        iActivityManager.crashApplicationWithType(i8, i9, str4, -1, m.toString(), true, i11);
                    }
                });
            }
        }

        public void onNotificationExpansionChanged(String str, final boolean z, final boolean z2, int i) {
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        NotificationUsageStats.SingleNotificationStats singleNotificationStats = notificationRecord.stats;
                        singleNotificationStats.isExpanded = z2;
                        singleNotificationStats.updateVisiblyExpandedStats();
                        if (notificationRecord.stats.posttimeToFirstVisibleExpansionMs >= 0) {
                            NotificationManagerService.this.logSmartSuggestionsVisible(notificationRecord, i);
                        }
                        if (z) {
                            MetricsLogger.action(notificationRecord.getLogMaker().setCategory(128).setType(z2 ? 3 : 14));
                            ((NotificationRecordLoggerImpl) NotificationManagerService.this.mNotificationRecordLogger).log(z ? z2 ? NotificationRecordLogger.NotificationEvent.NOTIFICATION_DETAIL_OPEN_USER : NotificationRecordLogger.NotificationEvent.NOTIFICATION_DETAIL_CLOSE_USER : z2 ? NotificationRecordLogger.NotificationEvent.NOTIFICATION_DETAIL_OPEN_SYSTEM : NotificationRecordLogger.NotificationEvent.NOTIFICATION_DETAIL_CLOSE_SYSTEM, notificationRecord);
                        }
                        if (z2 && z) {
                            notificationRecord.mStats.setExpanded();
                            NotificationManagerService.this.reportUserInteraction(notificationRecord);
                            NotificationManagerService notificationManagerService = NotificationManagerService.this;
                            synchronized (notificationManagerService) {
                                if (notificationManagerService.canSendLoggingData(notificationRecord)) {
                                    CollectionContract$Notification$Log orCreateNotificationLogLocked = notificationManagerService.getOrCreateNotificationLogLocked(notificationRecord);
                                    if (orCreateNotificationLogLocked.firstExpandedTimeMs == -1) {
                                        orCreateNotificationLogLocked.firstExpandedTimeMs = System.currentTimeMillis();
                                    }
                                }
                            }
                        }
                        final NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                        StatusBarNotification statusBarNotification = notificationRecord.sbn;
                        int notificationType = notificationRecord.getNotificationType();
                        notificationAssistants.getClass();
                        final String key = statusBarNotification.getKey();
                        notificationAssistants.notifyAssistantLocked(statusBarNotification, notificationType, new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                NotificationManagerService.NotificationAssistants notificationAssistants2 = NotificationManagerService.NotificationAssistants.this;
                                String str2 = key;
                                boolean z3 = z;
                                boolean z4 = z2;
                                INotificationListener iNotificationListener = (INotificationListener) obj;
                                notificationAssistants2.getClass();
                                try {
                                    iNotificationListener.onNotificationExpansionChanged(str2, z3, z4);
                                } catch (RemoteException e) {
                                    Slog.e(notificationAssistants2.TAG, "unable to notify assistant (expanded): " + iNotificationListener, e);
                                }
                            }
                        });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onNotificationFeedbackReceived(String str, Bundle bundle) {
            DeviceIdleManager deviceIdleManager = NotificationManagerService.this.mDeviceIdleManager;
            if (deviceIdleManager != null) {
                deviceIdleManager.endIdle("notification interaction");
            }
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        NotificationManagerService.this.mAssistants.notifyAssistantFeedbackReceived(notificationRecord, bundle);
                        return;
                    }
                    if (NotificationManagerService.DBG) {
                        Slog.w("NotificationService", "No notification with key: " + str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) {
            String packageName;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    packageName = notificationRecord != null ? notificationRecord.sbn.getPackageName() : null;
                } finally {
                }
            }
            int packageImportanceWithIdentity = (!android.app.Flags.lifetimeExtensionRefactor() || packageName == null) ? 0 : NotificationManagerService.this.getPackageImportanceWithIdentity(packageName);
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord notificationRecord2 = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord2 != null) {
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord2, notificationRecord2.sbn.getPackageName(), packageImportanceWithIdentity);
                        }
                        notificationRecord2.sbn.getNotification().flags |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
                        notificationRecord2.mStats.setSmartReplied();
                        NotificationManagerService.this.mMetricsLogger.write(notificationRecord2.getLogMaker().setCategory(1383).setSubtype(i).addTaggedData(1600, Integer.valueOf(notificationRecord2.mSuggestionsGeneratedByAssistant ? 1 : 0)).addTaggedData(1629, Integer.valueOf(i2)).addTaggedData(1647, Integer.valueOf(notificationRecord2.mEditChoicesBeforeSending ? 1 : 0)).addTaggedData(1648, Integer.valueOf(z ? 1 : 0)));
                        ((NotificationRecordLoggerImpl) NotificationManagerService.this.mNotificationRecordLogger).log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLIED, notificationRecord2);
                        NotificationManagerService.this.reportUserInteraction(notificationRecord2);
                        NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                        StatusBarNotification statusBarNotification = notificationRecord2.sbn;
                        int notificationType = notificationRecord2.getNotificationType();
                        boolean z2 = notificationRecord2.mSuggestionsGeneratedByAssistant;
                        notificationAssistants.getClass();
                        notificationAssistants.notifyAssistantLocked(statusBarNotification, notificationType, new NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda1(notificationAssistants, statusBarNotification.getKey(), charSequence, z2, 0));
                    }
                } finally {
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x008c, code lost:
        
            if (r5.stats.posttimeToFirstVisibleExpansionMs >= 0) goto L35;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] r13, com.android.internal.statusbar.NotificationVisibility[] r14) {
            /*
                Method dump skipped, instructions count: 214
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass2.onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[], com.android.internal.statusbar.NotificationVisibility[]):void");
        }

        public void onPanelHidden() {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            MetricsLogger.hidden(notificationManagerService.getContext(), 127);
            NotificationRecordLogger notificationRecordLogger = notificationManagerService.mNotificationRecordLogger;
            ((NotificationRecordLoggerImpl) notificationRecordLogger).mUiEventLogger.log(NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_CLOSE);
            EventLog.writeEvent(27501, new Object[0]);
            NotificationAssistants notificationAssistants = notificationManagerService.mAssistants;
            Iterator it = ((ArrayList) notificationAssistants.getServices()).iterator();
            while (it.hasNext()) {
                NotificationManagerService.this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda7(2, notificationAssistants, (ManagedServices.ManagedServiceInfo) it.next()));
            }
        }

        public void onPanelRevealed(boolean z, int i) {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            MetricsLogger.visible(notificationManagerService.getContext(), 127);
            MetricsLogger.histogram(notificationManagerService.getContext(), "note_load", i);
            NotificationRecordLogger notificationRecordLogger = notificationManagerService.mNotificationRecordLogger;
            ((NotificationRecordLoggerImpl) notificationRecordLogger).mUiEventLogger.log(NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN);
            EventLog.writeEvent(27500, i);
            if (z) {
                clearEffects();
            }
            NotificationAssistants notificationAssistants = notificationManagerService.mAssistants;
            Iterator it = ((ArrayList) notificationAssistants.getServices()).iterator();
            while (it.hasNext()) {
                NotificationManagerService.this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda16(i, 3, notificationAssistants, (ManagedServices.ManagedServiceInfo) it.next()));
            }
        }

        public void prepareForPossibleShutdown() {
            NotificationHistoryDatabase notificationHistoryDatabase;
            NotificationHistoryManager notificationHistoryManager = NotificationManagerService.this.mHistoryManager;
            synchronized (notificationHistoryManager.mLock) {
                try {
                    int size = notificationHistoryManager.mUserState.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = notificationHistoryManager.mUserState.keyAt(i);
                        if (notificationHistoryManager.mUserUnlockedStates.get(keyAt) && (notificationHistoryDatabase = (NotificationHistoryDatabase) notificationHistoryManager.mUserState.get(keyAt)) != null) {
                            notificationHistoryDatabase.mFileWriteHandler.post(notificationHistoryDatabase.mWriteBufferRunnable);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void updateAutogroupSummary(int i, String str, GroupHelper.NotificationAttributes notificationAttributes) {
            boolean z = str != null && NotificationManagerService.this.mActivityManager.getPackageImportance(str) == 100;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.updateAutobundledSummaryLocked(i, str, notificationAttributes, z);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$22, reason: invalid class name */
    public final class AnonymousClass22 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ Object val$r;

        public /* synthetic */ AnonymousClass22(int i, Object obj, Object obj2) {
            this.$r8$classId = i;
            this.this$0 = obj;
            this.val$r = obj2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    GroupHelper groupHelper = ((NotificationManagerService) this.this$0).mGroupHelper;
                    StatusBarNotification statusBarNotification = ((NotificationRecord) this.val$r).sbn;
                    groupHelper.getClass();
                    try {
                        groupHelper.maybeUngroup(statusBarNotification, true, statusBarNotification.getUserId());
                        break;
                    } catch (Exception e) {
                        Slog.e("GroupHelper", "Error processing canceled notification", e);
                        return;
                    }
                default:
                    GroupHelper groupHelper2 = ((NotificationManagerService) ((NotificationListeners.AnonymousClass1) this.this$0).this$1).mGroupHelper;
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) this.val$r;
                    groupHelper2.getClass();
                    try {
                        groupHelper2.maybeUngroup(statusBarNotification2, true, statusBarNotification2.getUserId());
                        break;
                    } catch (Exception e2) {
                        Slog.e("GroupHelper", "Error processing canceled notification", e2);
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NotificationManagerService this$0;

        public /* synthetic */ AnonymousClass5(NotificationManagerService notificationManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = notificationManagerService;
        }

        private final void onReceive$com$android$server$notification$NotificationManagerService$8(Context context, Intent intent) {
            boolean z;
            boolean z2;
            String schemeSpecificPart;
            int applicationEnabledSetting;
            boolean z3;
            String[] strArr;
            boolean z4;
            int[] iArr;
            boolean z5;
            String[] stringArrayExtra;
            int[] intArrayExtra;
            int i;
            int[] iArr2;
            boolean z6;
            boolean z7;
            Bundle bundle;
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                z = false;
                z2 = false;
            } else {
                z = action.equals("android.intent.action.PACKAGE_REMOVED");
                if (z || action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                    z2 = false;
                } else {
                    z2 = action.equals("android.intent.action.PACKAGE_CHANGED");
                    if (!z2 && !action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE") && !action.equals("android.intent.action.PACKAGES_SUSPENDED") && !action.equals("android.intent.action.PACKAGES_UNSUSPENDED") && !action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                        return;
                    }
                }
            }
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            boolean z8 = true;
            boolean z9 = z && !intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            if (NotificationManagerService.DBG) {
                Slog.i("NotificationService", "action=" + action + " removing=" + z9);
            }
            if (action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                String[] stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z5 = false;
                z4 = false;
                iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                strArr = stringArrayExtra2;
                z3 = true;
            } else if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                String[] stringArrayExtra3 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z4 = false;
                iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                z5 = true;
                strArr = stringArrayExtra3;
                z3 = false;
            } else if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                String[] stringArrayExtra4 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z5 = false;
                iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                z4 = true;
                strArr = stringArrayExtra4;
                z3 = false;
            } else if (action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                if ((intent.getIntExtra("android.intent.extra.distraction_restrictions", 0) & 2) != 0) {
                    stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    z4 = false;
                    z5 = true;
                } else {
                    stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    z5 = false;
                    z4 = true;
                }
                iArr = intArrayExtra;
                strArr = stringArrayExtra;
                z3 = false;
            } else {
                Uri data = intent.getData();
                if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                    return;
                }
                if (z2) {
                    try {
                        applicationEnabledSetting = this.this$0.mPackageManager.getApplicationEnabledSetting(schemeSpecificPart, intExtra != -1 ? intExtra : 0);
                    } catch (RemoteException unused) {
                    } catch (IllegalArgumentException e) {
                        if (NotificationManagerService.DBG) {
                            Slog.i("NotificationService", "Exception trying to look up app enabled setting", e);
                        }
                    }
                    if (applicationEnabledSetting == 1 || applicationEnabledSetting == 0) {
                        z3 = false;
                        strArr = new String[]{schemeSpecificPart};
                        z4 = false;
                        iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                        z5 = false;
                    }
                }
                z3 = true;
                strArr = new String[]{schemeSpecificPart};
                z4 = false;
                iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                z5 = false;
            }
            if (strArr == null || strArr.length <= 0) {
                i = 0;
                iArr2 = iArr;
                z6 = z9;
                z7 = true;
            } else {
                if (z3) {
                    int length = strArr.length;
                    int i2 = 0;
                    while (i2 < length) {
                        this.this$0.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, strArr[i2], null, intExtra, 5);
                        i2++;
                        z8 = z8;
                        iArr = iArr;
                        z9 = z9;
                    }
                    iArr2 = iArr;
                    z6 = z9;
                    z7 = z8;
                } else {
                    iArr2 = iArr;
                    z6 = z9;
                    z7 = true;
                    if (z5 && iArr2 != null && iArr2.length > 0) {
                        NotificationManagerService notificationManagerService = this.this$0;
                        synchronized (notificationManagerService.mNotificationLock) {
                            try {
                                Set set = (Set) Arrays.stream(iArr2).boxed().collect(Collectors.toSet());
                                List asList = Arrays.asList(strArr);
                                ArrayList arrayList = new ArrayList();
                                int size = notificationManagerService.mNotificationList.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationList.get(i3);
                                    if (asList.contains(notificationRecord.sbn.getPackageName()) && set.contains(Integer.valueOf(notificationRecord.sbn.getUid()))) {
                                        notificationRecord.mHidden = true;
                                        arrayList.add(notificationRecord);
                                    }
                                }
                                NotificationListeners notificationListeners = notificationManagerService.mListeners;
                                notificationListeners.getClass();
                                if (arrayList.size() != 0) {
                                    notificationListeners.notifyRankingUpdateLocked(arrayList);
                                    int size2 = arrayList.size();
                                    for (int i4 = 0; i4 < size2; i4++) {
                                        NotificationRecord notificationRecord2 = (NotificationRecord) arrayList.get(i4);
                                        NotificationManagerService.this.mListeners.notifyRemovedLocked(notificationRecord2, 14, notificationRecord2.mStats);
                                    }
                                }
                            } finally {
                            }
                        }
                    } else if (z4 && iArr2 != null && iArr2.length > 0) {
                        NotificationManagerService notificationManagerService2 = this.this$0;
                        synchronized (notificationManagerService2.mNotificationLock) {
                            try {
                                Set set2 = (Set) Arrays.stream(iArr2).boxed().collect(Collectors.toSet());
                                List asList2 = Arrays.asList(strArr);
                                ArrayList arrayList2 = new ArrayList();
                                int size3 = notificationManagerService2.mNotificationList.size();
                                for (int i5 = 0; i5 < size3; i5++) {
                                    NotificationRecord notificationRecord3 = (NotificationRecord) notificationManagerService2.mNotificationList.get(i5);
                                    if (asList2.contains(notificationRecord3.sbn.getPackageName()) && set2.contains(Integer.valueOf(notificationRecord3.sbn.getUid()))) {
                                        notificationRecord3.mHidden = false;
                                        arrayList2.add(notificationRecord3);
                                    }
                                }
                                NotificationListeners notificationListeners2 = notificationManagerService2.mListeners;
                                notificationListeners2.getClass();
                                if (arrayList2.size() != 0) {
                                    notificationListeners2.notifyRankingUpdateLocked(arrayList2);
                                    int size4 = arrayList2.size();
                                    for (int i6 = 0; i6 < size4; i6++) {
                                        NotificationRecord notificationRecord4 = (NotificationRecord) arrayList2.get(i6);
                                        notificationListeners2.notifyPostedLocked(notificationRecord4, notificationRecord4, false);
                                    }
                                }
                                i = 0;
                            } finally {
                            }
                        }
                    }
                }
                i = 0;
            }
            if (action.equals("android.intent.action.PACKAGE_ADDED") || action.equals("android.intent.action.PACKAGE_CHANGED")) {
                Uri data2 = intent.getData();
                String schemeSpecificPart2 = data2 != null ? data2.getSchemeSpecificPart() : null;
                if (intExtra != -1) {
                    i = intExtra;
                }
                if (schemeSpecificPart2 != null) {
                    try {
                        ApplicationInfo applicationInfo = this.this$0.mPackageManager.getApplicationInfo(schemeSpecificPart2, 128L, i);
                        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && "user".equals(bundle.getString("com.samsung.android.notification.listener.autobind", "default"))) {
                            Slog.i("NotificationService", "Notification listener autobind, pkg = " + schemeSpecificPart2);
                            NotificationManagerService notificationManagerService3 = this.this$0;
                            notificationManagerService3.getClass();
                            try {
                                if (ActivityManager.checkComponentPermission("com.samsung.android.permission.SEM_AUTO_BIND_NOTIFICATION_LISTENER_SERVICE", notificationManagerService3.getContext().getPackageManager().getPackageUidAsUser(schemeSpecificPart2, i), -1, z7) == 0) {
                                    Iterator it = this.this$0.mListeners.queryPackageForServices(786432, i, schemeSpecificPart2).iterator();
                                    while (it.hasNext()) {
                                        ComponentName componentName = (ComponentName) it.next();
                                        try {
                                            NotificationManagerService notificationManagerService4 = this.this$0;
                                            notificationManagerService4.mNotificationListenerFrom = "PACKAGE_CHANGED";
                                            notificationManagerService4.getBinderService().setNotificationListenerAccessGrantedForUser(componentName, i, z7, z7);
                                        } catch (RemoteException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            } catch (PackageManager.NameNotFoundException unused2) {
                            }
                        }
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            WorkerHandler workerHandler = this.this$0.mHandler;
            workerHandler.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = Boolean.valueOf(z6);
            obtain.argi1 = intExtra;
            obtain.arg2 = strArr;
            obtain.arg3 = iArr2;
            workerHandler.sendMessage(Message.obtain(workerHandler, 8, obtain));
        }

        private final void onReceive$com$android$server$notification$NotificationManagerService$9(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.USER_STOPPED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra >= 0) {
                    this.this$0.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, null, null, intExtra, 6);
                    return;
                }
                return;
            }
            if (NotificationManagerService.privateSpaceFlagsEnabled() ? action.equals("android.intent.action.PROFILE_UNAVAILABLE") : action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra2 >= 0) {
                    this.this$0.cancelAllNotificationsInt(NotificationManagerService.MY_UID, NotificationManagerService.MY_PID, 0, null, null, intExtra2, 15);
                    SnoozeHelper snoozeHelper = this.this$0.mSnoozeHelper;
                    synchronized (snoozeHelper.mLock) {
                        try {
                            for (int size = snoozeHelper.mSnoozedNotifications.size() - 1; size >= 0; size--) {
                                NotificationRecord notificationRecord = (NotificationRecord) snoozeHelper.mSnoozedNotifications.valueAt(size);
                                if (notificationRecord.sbn.getUserId() == intExtra2) {
                                    snoozeHelper.mSnoozedNotifications.removeAt(size);
                                    String trimmedString = SnoozeHelper.getTrimmedString(notificationRecord.sbn.getKey());
                                    snoozeHelper.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                                    snoozeHelper.mPersistedSnoozedNotifications.remove(trimmedString);
                                    snoozeHelper.mAm.cancel(snoozeHelper.createPendingIntent(notificationRecord.sbn.getKey()));
                                    MetricsLogger.action(notificationRecord.getLogMaker().setCategory(FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION).setType(5));
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
                return;
            }
            if (action.equals("android.intent.action.USER_SWITCHED")) {
                Slog.d("NotificationService", "Receiving  ACTION_USER_SWITCHED");
                Flags.useSsmUserSwitchSignal();
                int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                this.this$0.mUserProfiles.updateCache(context);
                if (!this.this$0.mUserProfiles.isProfileUser(context, intExtra3)) {
                    this.this$0.mSettingsObserver.update(null);
                    this.this$0.mConditionProviders.onUserSwitched(intExtra3);
                    this.this$0.mListeners.onUserSwitched(intExtra3);
                    this.this$0.mZenModeHelper.loadConfigForUser(intExtra3, "onUserSwitched");
                    this.this$0.mPreferencesHelper.syncChannelsBypassingDnd();
                }
                this.this$0.mAssistants.onUserSwitched(intExtra3);
                EdgeLightingManager edgeLightingManager = this.this$0.mEdgeLightingManager;
                edgeLightingManager.mUserId = intExtra3;
                EdgeLightingClientManager edgeLightingClientManager = edgeLightingManager.mEdgeLightingClientManager;
                edgeLightingClientManager.getClass();
                Slog.d("EdgeLightingClientManager", "onSwitchUser : " + intExtra3);
                edgeLightingClientManager.createEdgeLightingService(UserHandle.SEM_OWNER);
                EdgeLightingClientManager.EdgeLightingSettingObserver edgeLightingSettingObserver = edgeLightingClientManager.mEdgeLightingSettingObserver;
                edgeLightingSettingObserver.mLastEnabled = Settings.System.getIntForUser(EdgeLightingClientManager.this.mContext.getContentResolver(), "edge_lighting", edgeLightingSettingObserver.mDefaultValue, -2) == 1;
                edgeLightingManager.mEdgeLightingPolicyManager.mUserId = intExtra3;
                edgeLightingManager.updateCurrentProfilesCache();
                Slog.d("NotificationService", "ACTION_USER_SWITCHED: " + intExtra3);
                if (NmRune.NM_SUPPORT_NOTIFICATION_INSIGNIFICANT) {
                    NotificationHighlightCore notificationHighlightCore = this.this$0.mNotificationHighlightCore;
                    notificationHighlightCore.getClass();
                    notificationHighlightCore.mContext.createContextAsUser(UserHandle.of(intExtra3), 0);
                    notificationHighlightCore.mPrivacyConverstionEnabled = Settings.System.getIntForUser(notificationHighlightCore.mContext.getContentResolver(), "noti_intelligence_priority_conversation", 0, -2) == 1;
                    notificationHighlightCore.mAutoGroupingEnabled = Settings.System.getIntForUser(notificationHighlightCore.mContext.getContentResolver(), "noti_auto_more_grouping", 0, -2) == 1;
                    notificationHighlightCore.mContext.getContentResolver().unregisterContentObserver(notificationHighlightCore.mSettingsObserver);
                    notificationHighlightCore.mContext.getContentResolver().registerContentObserver(notificationHighlightCore.PRIVACY_CONVERSATION_URI, true, notificationHighlightCore.mSettingsObserver, -2);
                    notificationHighlightCore.mContext.getContentResolver().registerContentObserver(notificationHighlightCore.AUTO_GROUPING_URI, true, notificationHighlightCore.mSettingsObserver, -2);
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intExtra3, "onSwitchUser:", " mPrivacyConverstionEnabled=");
                    m.append(notificationHighlightCore.mPrivacyConverstionEnabled);
                    m.append(" mAutoGroupingEnabled=");
                    FlashNotificationsController$$ExternalSyntheticOutline0.m("NotificationHighlightCore", m, notificationHighlightCore.mAutoGroupingEnabled);
                    return;
                }
                return;
            }
            if (action.equals("android.intent.action.USER_ADDED")) {
                int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (intExtra4 != -10000) {
                    this.this$0.mUserProfiles.updateCache(context);
                    if (!this.this$0.mUserProfiles.isProfileUser(context, intExtra4)) {
                        this.this$0.allowDefaultApprovedServices(intExtra4);
                    }
                    this.this$0.mHistoryManager.mSettingsObserver.update(intExtra4, null);
                    this.this$0.mArchive.updateHistoryEnabled(intExtra4, true);
                    this.this$0.mHistoryManager.mSettingsObserver.update(intExtra4, null);
                    this.this$0.mSettingsObserver.update(intExtra4, null);
                }
                this.this$0.mEdgeLightingManager.updateCurrentProfilesCache();
                return;
            }
            if (action.equals("android.intent.action.USER_REMOVED")) {
                int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                this.this$0.mUserProfiles.updateCache(context);
                ZenModeHelper zenModeHelper = this.this$0.mZenModeHelper;
                zenModeHelper.getClass();
                if (intExtra5 >= 0) {
                    if (ZenModeHelper.DEBUG) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra5, "onUserRemoved u=", "ZenModeHelper");
                    }
                    synchronized (zenModeHelper.mConfigsArrayLock) {
                        zenModeHelper.mConfigs.remove(intExtra5);
                    }
                }
                PreferencesHelper preferencesHelper = this.this$0.mPreferencesHelper;
                synchronized (preferencesHelper.mLock) {
                    try {
                        for (int size2 = preferencesHelper.mPackagePreferences.size() - 1; size2 >= 0; size2--) {
                            if (UserHandle.getUserId(((PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(size2)).uid) == intExtra5) {
                                preferencesHelper.mPackagePreferences.removeAt(size2);
                            }
                        }
                    } finally {
                    }
                }
                this.this$0.mListeners.onUserRemoved(intExtra5);
                this.this$0.mConditionProviders.onUserRemoved(intExtra5);
                this.this$0.mAssistants.onUserRemoved(intExtra5);
                NotificationHistoryManager notificationHistoryManager = this.this$0.mHistoryManager;
                synchronized (notificationHistoryManager.mLock) {
                    notificationHistoryManager.mUserPendingPackageRemovals.remove(intExtra5);
                    notificationHistoryManager.mHistoryEnabled.put(intExtra5, false);
                    notificationHistoryManager.mUserPendingHistoryDisables.put(intExtra5, false);
                    notificationHistoryManager.onUserStopped(intExtra5);
                }
                this.this$0.mPreferencesHelper.syncChannelsBypassingDnd();
                this.this$0.handleSavePolicyFile();
                this.this$0.mEdgeLightingManager.updateCurrentProfilesCache();
                return;
            }
            if (action.equals("android.intent.action.USER_UNLOCKED")) {
                int intExtra6 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                this.this$0.mUserProfiles.updateCache(context);
                NotificationAssistants notificationAssistants = this.this$0.mAssistants;
                if (notificationAssistants.DEBUG) {
                    Slog.d(notificationAssistants.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(intExtra6, "onUserUnlocked u="));
                }
                notificationAssistants.rebindServices(intExtra6, true);
                if (this.this$0.mUserProfiles.isProfileUser(context, intExtra6)) {
                    return;
                }
                ConditionProviders conditionProviders = this.this$0.mConditionProviders;
                if (conditionProviders.DEBUG) {
                    Slog.d(conditionProviders.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(intExtra6, "onUserUnlocked u="));
                }
                conditionProviders.rebindServices(intExtra6, false);
                NotificationListeners notificationListeners = this.this$0.mListeners;
                if (notificationListeners.DEBUG) {
                    Slog.d(notificationListeners.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(intExtra6, "onUserUnlocked u="));
                }
                notificationListeners.rebindServices(intExtra6, false);
                if (android.app.Flags.modesApi()) {
                    return;
                }
                this.this$0.mZenModeHelper.loadConfigForUser(intExtra6, "onUserUnlocked");
                return;
            }
            if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                NotificationManagerService notificationManagerService = this.this$0;
                if (notificationManagerService.mSemGoodCatchManager == null) {
                    notificationManagerService.mSemGoodCatchManager = new SemGoodCatchManager(this.this$0.getContext(), "AccessibilityManagerService", new String[]{"toast"}, this.this$0.mGoodCatchStateListener);
                    Log.d("NotificationService", "SemGoodCatchManager is created");
                }
                NotificationManagerService notificationManagerService2 = this.this$0;
                if (notificationManagerService2.mNotiSemGoodCatchManager == null) {
                    notificationManagerService2.mNotiSemGoodCatchManager = new SemGoodCatchManager(this.this$0.getContext(), "NotificationManagerService", new String[]{"noti_blocked"}, this.this$0.mNotiGoodCatchStateListener);
                    Log.d("NotificationService", "mNotiSemGoodCatchManager is created");
                    return;
                }
                return;
            }
            if ("samsung.intent.action.PHONE_STATE".equals(action)) {
                if (TelephonyManager.EXTRA_STATE_IDLE != intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) {
                    this.this$0.mCMCinCallState = true;
                }
            } else if ("android.intent.action.DATE_CHANGED".equals(action)) {
                Log.d("NotificationService", "ACTION_DATE_CHANGED");
                NotificationManagerService notificationManagerService3 = this.this$0;
                notificationManagerService3.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda1(notificationManagerService3, 1));
            } else if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
                Log.d("NotificationService", "ACTION_SOFT_RESET");
                try {
                    this.this$0.getBinderService().resetDefaultAllowOngoingActivity();
                    this.this$0.handleSavePolicyFile();
                } catch (RemoteException e) {
                    Log.d("NotificationService", "ACTION_SOFT_RESET - getBinderService exception");
                    e.printStackTrace();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:133:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:191:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r26, android.content.Intent r27) {
            /*
                Method dump skipped, instructions count: 1380
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.AnonymousClass5.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Archive {
        public final int mBufferSize;
        public final Object mBufferLock = new Object();
        public final LinkedList mBuffer = new LinkedList();
        public final SparseArray mEnabled = new SparseArray();

        public Archive(int i) {
            this.mBufferSize = i;
        }

        public final void dumpImpl(PrintWriter printWriter, DumpFilter dumpFilter) {
            synchronized (this.mBufferLock) {
                try {
                    Iterator descendingIterator = this.mBuffer.descendingIterator();
                    int i = 0;
                    while (true) {
                        if (!descendingIterator.hasNext()) {
                            break;
                        }
                        StatusBarNotification statusBarNotification = (StatusBarNotification) ((Pair) descendingIterator.next()).first;
                        if (dumpFilter.matches(statusBarNotification)) {
                            printWriter.println("    " + statusBarNotification);
                            i++;
                            if (i >= 5) {
                                if (descendingIterator.hasNext()) {
                                    printWriter.println("    ...");
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final StatusBarNotification[] getArray(UserManager userManager, int i, boolean z) {
            StatusBarNotification[] statusBarNotificationArr;
            ArrayList arrayList = new ArrayList();
            arrayList.add(-1);
            Binder.withCleanCallingIdentity(new NotificationManagerService$$ExternalSyntheticLambda10(2, userManager, arrayList));
            synchronized (this.mBufferLock) {
                if (i == 0) {
                    try {
                        i = this.mBufferSize;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator descendingIterator = this.mBuffer.descendingIterator();
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

        public final String toString() {
            StringBuilder sb = new StringBuilder("Archive (");
            int size = this.mBuffer.size();
            sb.append(size);
            sb.append(" notification");
            sb.append(size == 1 ? ")" : "s)");
            return sb.toString();
        }

        public final void updateHistoryEnabled(int i, boolean z) {
            this.mEnabled.put(i, Boolean.valueOf(z));
            if (z) {
                return;
            }
            synchronized (this.mBufferLock) {
                try {
                    for (int size = this.mBuffer.size() - 1; size >= 0; size--) {
                        if (i == ((StatusBarNotification) ((Pair) this.mBuffer.get(size)).first).getNormalizedUserId()) {
                            this.mBuffer.remove(size);
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CancelNotificationRunnable implements Runnable {
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
        public final void run() {
            ManagedServices.ManagedServiceInfo managedServiceInfo = this.mListener;
            String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
            boolean z = NotificationManagerService.DBG;
            EventLogTags.writeNotificationCancel(this.mCallingUid, this.mCallingPid, this.mPkg, this.mId, this.mTag, this.mUserId, this.mMustHaveFlags, this.mMustNotHaveFlags, this.mReason, shortString);
            int packageImportanceWithIdentity = android.app.Flags.lifetimeExtensionRefactor() ? NotificationManagerService.this.getPackageImportanceWithIdentity(this.mPkg) : 0;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationRecord findNotificationLocked = NotificationManagerService.this.findNotificationLocked(this.mId, this.mUserId, this.mPkg, this.mTag);
                    if (findNotificationLocked != null) {
                        if (this.mReason == 1) {
                            NotificationManagerService.this.mUsageStats.registerClickedByUser(findNotificationLocked);
                        }
                        if ((this.mReason == 10 && findNotificationLocked.sbn.getNotification().isBubbleNotification()) || (this.mReason == 1 && findNotificationLocked.mAllowBubble && findNotificationLocked.mFlagBubbleRemoved)) {
                            ((AnonymousClass2) NotificationManagerService.this.mNotificationDelegate).onBubbleMetadataFlagChanged(findNotificationLocked.sbn.getKey(), (findNotificationLocked.sbn.getNotification().getBubbleMetadata() != null ? findNotificationLocked.sbn.getNotification().getBubbleMetadata().getFlags() : 0) | 2);
                            return;
                        }
                        int i = findNotificationLocked.sbn.getNotification().flags;
                        int i2 = this.mMustHaveFlags;
                        if ((i & i2) != i2) {
                            return;
                        }
                        if ((findNotificationLocked.sbn.getNotification().flags & this.mMustNotHaveFlags) != 0) {
                            if (android.app.Flags.lifetimeExtensionRefactor()) {
                                NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(findNotificationLocked, this.mPkg, packageImportanceWithIdentity);
                            }
                            return;
                        }
                        NotificationManagerService$$ExternalSyntheticLambda5 notificationManagerService$$ExternalSyntheticLambda5 = new NotificationManagerService$$ExternalSyntheticLambda5(this);
                        NotificationManagerService notificationManagerService = NotificationManagerService.this;
                        if (!notificationManagerService.mFoldState) {
                            if (notificationManagerService.mPreferencesHelper.mAllowList.contains(this.mPkg) && NotificationManagerService.this.mActivityManager.getPackageImportance(this.mPkg) == 100) {
                                String str = this.mUserId + "|" + this.mPkg + "|" + this.mId + "|" + this.mTag + "|" + this.mCallingUid;
                                Slog.d("NotificationService", "updateCancelEvent key = " + str);
                                NotificationManagerService.this.mHistoryManager.updateCancelEvent(this.mUserId, str, false);
                            }
                        }
                        NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, this.mSendDelete, this.mReason, this.mRank, this.mCount, NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked), shortString, this.mCancellationElapsedTimeMs);
                        NotificationManagerService.m697$$Nest$mcancelGroupSummaryLocked(NotificationManagerService.this, findNotificationLocked, shortString, this.mSendDelete, this.mCancellationElapsedTimeMs);
                        NotificationManagerService.this.cancelGroupChildrenLocked(findNotificationLocked, this.mCallingUid, this.mCallingPid, shortString, this.mSendDelete, notificationManagerService$$ExternalSyntheticLambda5, this.mReason, this.mCancellationElapsedTimeMs);
                        NotificationManagerService.this.mAttentionHelper.updateLightsLocked();
                        NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                        ShortcutHelper shortcutHelper = notificationManagerService2.mShortcutHelper;
                        if (shortcutHelper != null) {
                            shortcutHelper.maybeListenForShortcutChangesForBubbles(findNotificationLocked, true, notificationManagerService2.mHandler);
                        }
                    } else if (this.mReason != 18) {
                        if (NotificationManagerService.this.mSnoozeHelper.cancel(this.mUserId, this.mId, this.mPkg, this.mTag)) {
                            NotificationManagerService.this.handleSavePolicyFile();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DumpFilter {
        public boolean criticalPriority;
        public boolean filtered;
        public boolean normalPriority;
        public String pkgFilter;
        public boolean proto;
        public boolean redact;
        public boolean rvStats;
        public long since;
        public boolean stats;
        public boolean zen;

        public final boolean matches(ComponentName componentName) {
            if (this.filtered && !this.zen) {
                return componentName != null && matches(componentName.getPackageName());
            }
            return true;
        }

        public final boolean matches(StatusBarNotification statusBarNotification) {
            if (this.filtered && !this.zen) {
                return statusBarNotification != null && (matches(statusBarNotification.getPackageName()) || matches(statusBarNotification.getOpPkg()));
            }
            return true;
        }

        public final boolean matches(String str) {
            if (this.filtered && !this.zen) {
                return str != null && str.toLowerCase().contains(this.pkgFilter);
            }
            return true;
        }

        public final String toString() {
            if (this.stats) {
                return "stats";
            }
            if (this.zen) {
                return "zen";
            }
            return "'" + this.pkgFilter + '\'';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EdgeLightingLocalService extends EdgeManagerInternal {
        public EdgeLightingLocalService() {
        }

        public final boolean hideForNotification(StatusBarNotification statusBarNotification) {
            Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.hideForNotification(statusBarNotification);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void hideForWakeLock(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.getClass();
                boolean z = EdgeLightingHistory.IS_DEV_DEBUG;
                String str2 = EdgeLightingManager.TAG;
                if (z || EdgeLightingManager.DEBUG) {
                    Slog.d(str2, "hideForWakeLock packageName=" + str);
                }
                Slog.d(str2, "hideForWakeLock is no more used");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void hideForWakeLockByWindow(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.hideForWakeLockByWindow(callingUid, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setSuppressed(int i) {
            NotificationManagerService.this.mEdgeLightingManager.mEdgeLightingPolicyManager.mSuppressed = i;
        }

        public final boolean showForNotification(StatusBarNotification statusBarNotification, Bundle bundle) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForNotification(statusBarNotification, bundle);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void showForResumedActivity(ComponentName componentName) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                EdgeLightingPolicyManager edgeLightingPolicyManager = NotificationManagerService.this.mEdgeLightingManager.mEdgeLightingPolicyManager;
                if (componentName != null) {
                    edgeLightingPolicyManager.mResumedComponent = componentName;
                    edgeLightingPolicyManager.mResumedComponentTime = System.currentTimeMillis();
                } else {
                    edgeLightingPolicyManager.getClass();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean showForToast(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForToast(callingUid, str, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean showForWakeLock(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                EdgeLightingManager edgeLightingManager = NotificationManagerService.this.mEdgeLightingManager;
                edgeLightingManager.getClass();
                if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingManager.DEBUG) {
                    Slog.d(EdgeLightingManager.TAG, "showForWakeLock : packageName = " + str);
                }
                return edgeLightingManager.showForWakeLockInternal(4, i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean showForWakeLockByWindow(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeLockByWindow(callingUid, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean showForWakeUp(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return NotificationManagerService.this.mEdgeLightingManager.showForWakeUp(str, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean showForWakeUpByWindow(String str, String str2, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.getClass();
                if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingManager.DEBUG) {
                    Slog.d(EdgeLightingManager.TAG, "showForWakeUpByWindow is not supported : packageName = " + str);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void statusBarDisabled(int i, int i2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NotificationManagerService.this.mEdgeLightingManager.statusBarDisabled(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnqueueNotificationRunnable implements Runnable {
        public final boolean isAppForeground;
        public final PostNotificationTracker mTracker;
        public final NotificationRecord r;
        public final int userId;

        public EnqueueNotificationRunnable(int i, NotificationRecord notificationRecord, boolean z, PostNotificationTracker postNotificationTracker) {
            this.userId = i;
            this.r = notificationRecord;
            this.isAppForeground = z;
            this.mTracker = (PostNotificationTracker) Preconditions.checkNotNull(postNotificationTracker);
        }

        /* JADX WARN: Code restructure failed: missing block: B:62:0x018c, code lost:
        
            if (android.util.Log.isLoggable("DownloadManager", 2) != false) goto L49;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean enqueueNotification() {
            /*
                Method dump skipped, instructions count: 587
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.EnqueueNotificationRunnable.enqueueNotification():boolean");
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                if (enqueueNotification()) {
                }
            } finally {
                this.mTracker.cancel();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface FlagChecker {
        boolean apply(int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationAssistants extends ManagedServices {
        public final Set mAllowedAdjustments;
        public ComponentName mDefaultFromConfig;
        public final Object mLock;

        /* renamed from: -$$Nest$monNotificationEnqueuedLocked, reason: not valid java name */
        public static void m729$$Nest$monNotificationEnqueuedLocked(NotificationAssistants notificationAssistants, NotificationRecord notificationRecord) {
            notificationAssistants.getClass();
            boolean isLoggable = Log.isLoggable("notification_assistant", 2);
            String str = notificationAssistants.TAG;
            if (isLoggable) {
                Slog.v(str, "onNotificationEnqueuedLocked() called with: r = [" + notificationRecord + "]");
            }
            StatusBarNotification statusBarNotification = notificationRecord.sbn;
            Iterator it = ((ArrayList) notificationAssistants.getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                int notificationType = notificationRecord.getNotificationType();
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.isVisibleToListener(statusBarNotification, notificationType, managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.sbn.getUserId())) {
                    INotificationListener iNotificationListener = managedServiceInfo.service;
                    StatusBarNotificationHolder statusBarNotificationHolder = new StatusBarNotificationHolder(notificationManagerService.mListeners.mLightTrimListeners.contains(managedServiceInfo) ? statusBarNotification.cloneLight() : statusBarNotification.clone());
                    if (isLoggable) {
                        try {
                            Slog.v(str, "calling onNotificationEnqueuedWithChannel " + statusBarNotificationHolder);
                        } catch (RemoteException e) {
                            Slog.e(str, "unable to notify assistant (enqueued): " + iNotificationListener, e);
                        }
                    }
                    iNotificationListener.onNotificationEnqueuedWithChannel(statusBarNotificationHolder, notificationRecord.mChannel, notificationManagerService.makeRankingUpdateLocked(managedServiceInfo));
                }
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
        public final void addApprovedList(int i, String str, String str2, boolean z) {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(":");
                if (split.length > 1) {
                    Slog.d(this.TAG, "More than one approved assistants");
                    str = split[0];
                }
            }
            super.addApprovedList(i, str, str2, z);
        }

        @Override // com.android.server.notification.ManagedServices
        public final boolean allowRebindForParentUser() {
            return false;
        }

        @Override // com.android.server.notification.ManagedServices
        public final IInterface asInterface(IBinder iBinder) {
            return INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void clearApprovedList() {
            try {
                NotificationManagerService.this.getBinderService().setNotificationAssistantAccessGranted((ComponentName) null, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void ensureFilters(ServiceInfo serviceInfo, int i) {
        }

        @Override // com.android.server.notification.ManagedServices
        public final ManagedServices.Config getConfig() {
            ManagedServices.Config config = new ManagedServices.Config();
            config.caption = "notification assistant";
            config.serviceInterface = "android.service.notification.NotificationAssistantService";
            config.xmlTag = "enabled_assistants";
            config.secureSettingName = "enabled_notification_assistant";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_ASSISTANT_SERVICE";
            config.settingsAction = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";
            config.clientLabel = R.string.shutdown_confirm;
            return config;
        }

        @Override // com.android.server.notification.ManagedServices
        public final String getRequiredPermission() {
            return "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE";
        }

        @Override // com.android.server.notification.ManagedServices
        public final void loadDefaultsFromConfig() {
            loadDefaultsFromConfig(true);
        }

        public final void loadDefaultsFromConfig(boolean z) {
            ArraySet arraySet = new ArraySet();
            arraySet.addAll(Arrays.asList(this.mContext.getResources().getString(R.string.date_picker_day_of_week_typeface).split(":")));
            for (int i = 0; i < arraySet.size(); i++) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString((String) arraySet.valueAt(i));
                String str = (String) arraySet.valueAt(i);
                if (unflattenFromString != null) {
                    str = unflattenFromString.getPackageName();
                }
                if (!TextUtils.isEmpty(str) && queryPackageForServices(786432, 0, str).contains(unflattenFromString)) {
                    if (!z) {
                        this.mDefaultFromConfig = unflattenFromString;
                    } else if (unflattenFromString != null) {
                        addDefaultComponentOrPackage(unflattenFromString.flattenToString());
                    }
                }
            }
        }

        public final void notifyAssistantFeedbackReceived(NotificationRecord notificationRecord, Bundle bundle) {
            StatusBarNotification statusBarNotification = notificationRecord.sbn;
            Iterator it = ((ArrayList) getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                int notificationType = notificationRecord.getNotificationType();
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.isVisibleToListener(statusBarNotification, notificationType, managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.sbn.getUserId())) {
                    INotificationListener iNotificationListener = managedServiceInfo.service;
                    try {
                        iNotificationListener.onNotificationFeedbackReceived(statusBarNotification.getKey(), notificationManagerService.makeRankingUpdateLocked(managedServiceInfo), bundle);
                    } catch (RemoteException e) {
                        Slog.e(this.TAG, "unable to notify assistant (feedback): " + iNotificationListener, e);
                    }
                }
            }
        }

        public final void notifyAssistantLocked(StatusBarNotification statusBarNotification, int i, BiConsumer biConsumer) {
            StatusBarNotification statusBarNotification2;
            StatusBarNotification statusBarNotification3;
            boolean isLoggable = Log.isLoggable("notification_assistant", 2);
            String str = this.TAG;
            if (isLoggable) {
                Slog.v(str, "notifyAssistantLocked() called with: sbn = [" + statusBarNotification + "], sameUserOnly = [true], callback = [" + biConsumer + "]");
            }
            Iterator it = ((ArrayList) getServices()).iterator();
            StatusBarNotification statusBarNotification4 = null;
            StatusBarNotification statusBarNotification5 = null;
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                boolean z = notificationManagerService.isVisibleToListener(statusBarNotification, i, managedServiceInfo) && managedServiceInfo.isSameUser(statusBarNotification.getUserId());
                if (isLoggable) {
                    Slog.v(str, "notifyAssistantLocked info=" + managedServiceInfo + " snbVisible=" + z);
                }
                if (z) {
                    INotificationListener iNotificationListener = managedServiceInfo.service;
                    if (notificationManagerService.mListeners.mLightTrimListeners.contains(managedServiceInfo)) {
                        if (statusBarNotification4 == null) {
                            statusBarNotification4 = statusBarNotification.cloneLight();
                        }
                        statusBarNotification2 = statusBarNotification5;
                        statusBarNotification3 = statusBarNotification4;
                    } else {
                        if (statusBarNotification5 == null) {
                            statusBarNotification5 = statusBarNotification.clone();
                        }
                        statusBarNotification2 = statusBarNotification5;
                        statusBarNotification3 = statusBarNotification4;
                        statusBarNotification4 = statusBarNotification2;
                    }
                    notificationManagerService.mHandler.post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8(biConsumer, iNotificationListener, new StatusBarNotificationHolder(statusBarNotification4), 2));
                    statusBarNotification4 = statusBarNotification3;
                    statusBarNotification5 = statusBarNotification2;
                }
            }
        }

        public final void notifyAssistantVisibilityChangedLocked(NotificationRecord notificationRecord, final boolean z) {
            final String key = notificationRecord.sbn.getKey();
            if (NotificationManagerService.DBG) {
                Slog.d(this.TAG, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("notifyAssistantVisibilityChangedLocked: ", key));
            }
            notifyAssistantLocked(notificationRecord.sbn, notificationRecord.getNotificationType(), new BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NotificationManagerService.NotificationAssistants notificationAssistants = NotificationManagerService.NotificationAssistants.this;
                    String str = key;
                    boolean z2 = z;
                    INotificationListener iNotificationListener = (INotificationListener) obj;
                    notificationAssistants.getClass();
                    try {
                        iNotificationListener.onNotificationVisibilityChanged(str, z2);
                    } catch (RemoteException e) {
                        Slog.e(notificationAssistants.TAG, "unable to notify assistant (visible): " + iNotificationListener, e);
                    }
                }
            });
        }

        public final void onNotificationsSeenLocked(ArrayList arrayList) {
            NotificationManagerService notificationManagerService;
            Iterator it = ((ArrayList) getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                Iterator it2 = arrayList.iterator();
                while (true) {
                    boolean hasNext = it2.hasNext();
                    notificationManagerService = NotificationManagerService.this;
                    if (!hasNext) {
                        break;
                    }
                    NotificationRecord notificationRecord = (NotificationRecord) it2.next();
                    if (notificationManagerService.isVisibleToListener(notificationRecord.sbn, notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.sbn.getUserId())) {
                        arrayList2.add(notificationRecord.sbn.getKey());
                    }
                }
                if (!arrayList2.isEmpty()) {
                    notificationManagerService.mHandler.post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8(this, managedServiceInfo, arrayList2, 1));
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            notificationListeners.getClass();
            notificationListeners.checkNotNull(managedServiceInfo.service);
            IInterface iInterface = managedServiceInfo.service;
            notificationListeners.getClass();
            if (!(iInterface instanceof INotificationListener)) {
                throw new IllegalArgumentException();
            }
            if (notificationListeners.registerServiceImpl(managedServiceInfo) != null) {
                notificationListeners.onServiceAdded(managedServiceInfo);
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            ServiceConnection serviceConnection;
            NotificationListeners notificationListeners = NotificationManagerService.this.mListeners;
            IInterface iInterface = managedServiceInfo.service;
            int i = managedServiceInfo.userid;
            notificationListeners.checkNotNull(iInterface);
            ManagedServices.ManagedServiceInfo removeServiceImpl = notificationListeners.removeServiceImpl(iInterface, i);
            if (removeServiceImpl == null || (serviceConnection = removeServiceImpl.connection) == null || ManagedServices.this != notificationListeners) {
                return;
            }
            notificationListeners.unbindService(serviceConnection, removeServiceImpl.component, removeServiceImpl.userid);
        }

        public final void resetDefaultAssistantsIfNecessary() {
            Iterator it = this.mUm.getAliveUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                Boolean bool = (Boolean) this.mIsUserChanged.get(Integer.valueOf(identifier));
                if (bool == null || !bool.booleanValue()) {
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    if (!notificationManagerService.isNASMigrationDone(identifier)) {
                        this.mDefaultComponents.clear();
                        this.mDefaultPackages.clear();
                        loadDefaultsFromConfig(true);
                        notificationManagerService.setNASMigrationDone(identifier);
                    }
                    Slog.d(this.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "Approving default notification assistant for user "));
                    notificationManagerService.setDefaultAssistantForUser(identifier);
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void setPackageOrComponentEnabled(int i, String str, boolean z, boolean z2, boolean z3) {
            if (z2) {
                List allowedComponents = getAllowedComponents(i);
                if (!((ArrayList) allowedComponents).isEmpty()) {
                    ComponentName componentName = (ComponentName) CollectionUtils.firstOrNull(allowedComponents);
                    if (componentName.flattenToString().equals(str)) {
                        return;
                    } else {
                        NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, false, z3);
                    }
                }
            }
            super.setPackageOrComponentEnabled(i, str, true, z2, z3);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void upgradeUserSet() {
            for (Integer num : this.mApproved.keySet()) {
                num.intValue();
                ArraySet arraySet = (ArraySet) this.mUserSetServices.get(num);
                this.mIsUserChanged.put(num, Boolean.valueOf(arraySet != null && arraySet.size() > 0));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationListeners extends ManagedServices {
        public final boolean mIsHeadlessSystemUserMode;
        public final ArraySet mLightTrimListeners;
        public final ArrayMap mRequestedNotificationListeners;
        public int mSystemUIUid;
        public final ArrayList mTrustListenerUpdateHistory;
        public final ArraySet mTrustedListenerUids;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.notification.NotificationManagerService$NotificationListeners$1, reason: invalid class name */
        public final class AnonymousClass1 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ Object this$1;
            public final Object val$extra;
            public final int val$id;
            public final Object val$info;
            public final String val$pkg;

            public /* synthetic */ AnonymousClass1(NotificationListeners notificationListeners, ManagedServices.ManagedServiceInfo managedServiceInfo, String str, int i, Bundle bundle, int i2) {
                this.$r8$classId = i2;
                this.this$1 = notificationListeners;
                this.val$info = managedServiceInfo;
                this.val$pkg = str;
                this.val$id = i;
                this.val$extra = bundle;
            }

            public AnonymousClass1(NotificationManagerService notificationManagerService, String str, String str2, int i, PostNotificationTracker postNotificationTracker) {
                this.$r8$classId = 2;
                this.this$1 = notificationManagerService;
                this.val$pkg = str;
                this.val$info = str2;
                this.val$id = i;
                this.val$extra = (PostNotificationTracker) Preconditions.checkNotNull(postNotificationTracker);
            }

            /* JADX WARN: Code restructure failed: missing block: B:48:0x00b8, code lost:
            
                if (((com.android.server.notification.NotificationManagerService) r19.this$1).isRecordBlockedLocked(r14) != false) goto L36;
             */
            /* JADX WARN: Removed duplicated region for block: B:103:0x01fd A[Catch: all -> 0x0269, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x0269, blocks: (B:103:0x01fd, B:105:0x0206, B:107:0x020f, B:109:0x0225, B:112:0x0232, B:114:0x023f, B:116:0x0246, B:120:0x0257, B:123:0x0261, B:129:0x0312, B:131:0x031c, B:135:0x0343, B:147:0x0369, B:158:0x039c, B:185:0x040c, B:188:0x0418, B:194:0x0432, B:197:0x043c, B:203:0x044f, B:205:0x045f, B:208:0x046d, B:217:0x0499, B:225:0x04bf, B:228:0x04cb, B:236:0x0505, B:250:0x054a, B:254:0x0563, B:258:0x0577, B:263:0x0585, B:275:0x05af, B:285:0x05d0, B:290:0x05e4, B:292:0x05f6, B:298:0x061f, B:308:0x065b, B:312:0x0667, B:357:0x0695, B:359:0x06a0, B:363:0x06b5, B:374:0x02e1, B:379:0x02f4), top: B:101:0x01fb }] */
            /* JADX WARN: Removed duplicated region for block: B:129:0x0312 A[Catch: all -> 0x0269, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x0269, blocks: (B:103:0x01fd, B:105:0x0206, B:107:0x020f, B:109:0x0225, B:112:0x0232, B:114:0x023f, B:116:0x0246, B:120:0x0257, B:123:0x0261, B:129:0x0312, B:131:0x031c, B:135:0x0343, B:147:0x0369, B:158:0x039c, B:185:0x040c, B:188:0x0418, B:194:0x0432, B:197:0x043c, B:203:0x044f, B:205:0x045f, B:208:0x046d, B:217:0x0499, B:225:0x04bf, B:228:0x04cb, B:236:0x0505, B:250:0x054a, B:254:0x0563, B:258:0x0577, B:263:0x0585, B:275:0x05af, B:285:0x05d0, B:290:0x05e4, B:292:0x05f6, B:298:0x061f, B:308:0x065b, B:312:0x0667, B:357:0x0695, B:359:0x06a0, B:363:0x06b5, B:374:0x02e1, B:379:0x02f4), top: B:101:0x01fb }] */
            /* JADX WARN: Removed duplicated region for block: B:140:0x0358  */
            /* JADX WARN: Removed duplicated region for block: B:151:0x0376  */
            /* JADX WARN: Removed duplicated region for block: B:185:0x040c A[Catch: all -> 0x0269, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x0269, blocks: (B:103:0x01fd, B:105:0x0206, B:107:0x020f, B:109:0x0225, B:112:0x0232, B:114:0x023f, B:116:0x0246, B:120:0x0257, B:123:0x0261, B:129:0x0312, B:131:0x031c, B:135:0x0343, B:147:0x0369, B:158:0x039c, B:185:0x040c, B:188:0x0418, B:194:0x0432, B:197:0x043c, B:203:0x044f, B:205:0x045f, B:208:0x046d, B:217:0x0499, B:225:0x04bf, B:228:0x04cb, B:236:0x0505, B:250:0x054a, B:254:0x0563, B:258:0x0577, B:263:0x0585, B:275:0x05af, B:285:0x05d0, B:290:0x05e4, B:292:0x05f6, B:298:0x061f, B:308:0x065b, B:312:0x0667, B:357:0x0695, B:359:0x06a0, B:363:0x06b5, B:374:0x02e1, B:379:0x02f4), top: B:101:0x01fb }] */
            /* JADX WARN: Removed duplicated region for block: B:301:0x0637  */
            /* JADX WARN: Removed duplicated region for block: B:319:0x06dc A[Catch: all -> 0x06e6, TRY_LEAVE, TryCatch #3 {all -> 0x06e6, blocks: (B:127:0x02fb, B:137:0x0352, B:142:0x035c, B:182:0x03e3, B:317:0x06d4, B:319:0x06dc, B:353:0x067c, B:361:0x06af, B:365:0x06bc, B:368:0x0272, B:372:0x02d7, B:377:0x02ea), top: B:367:0x0272 }] */
            /* JADX WARN: Removed duplicated region for block: B:325:0x0707 A[Catch: all -> 0x06e3, TRY_LEAVE, TryCatch #1 {all -> 0x06e3, blocks: (B:322:0x06df, B:323:0x06ea, B:325:0x0707), top: B:321:0x06df }] */
            /* JADX WARN: Removed duplicated region for block: B:329:0x0720 A[Catch: all -> 0x0083, TryCatch #2 {all -> 0x0083, blocks: (B:9:0x0054, B:11:0x0061, B:15:0x0079, B:13:0x0087, B:16:0x008a, B:35:0x00df, B:37:0x00ec, B:41:0x0104, B:39:0x010e, B:42:0x0111, B:51:0x074a, B:53:0x0757, B:55:0x076f, B:57:0x0772, B:58:0x077b, B:75:0x015b, B:77:0x0168, B:81:0x0180, B:79:0x018a, B:82:0x018d, B:327:0x0713, B:329:0x0720, B:333:0x0738, B:331:0x0742, B:334:0x0745), top: B:4:0x0033 }] */
            /* JADX WARN: Removed duplicated region for block: B:336:0x0745 A[EDGE_INSN: B:336:0x0745->B:334:0x0745 BREAK  A[LOOP:4: B:328:0x071e->B:331:0x0742], SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:342:0x06e9  */
            /* JADX WARN: Removed duplicated region for block: B:353:0x067c A[Catch: all -> 0x06e6, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x06e6, blocks: (B:127:0x02fb, B:137:0x0352, B:142:0x035c, B:182:0x03e3, B:317:0x06d4, B:319:0x06dc, B:353:0x067c, B:361:0x06af, B:365:0x06bc, B:368:0x0272, B:372:0x02d7, B:377:0x02ea), top: B:367:0x0272 }] */
            /* JADX WARN: Removed duplicated region for block: B:366:0x026e  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x0757 A[Catch: all -> 0x0083, TryCatch #2 {all -> 0x0083, blocks: (B:9:0x0054, B:11:0x0061, B:15:0x0079, B:13:0x0087, B:16:0x008a, B:35:0x00df, B:37:0x00ec, B:41:0x0104, B:39:0x010e, B:42:0x0111, B:51:0x074a, B:53:0x0757, B:55:0x076f, B:57:0x0772, B:58:0x077b, B:75:0x015b, B:77:0x0168, B:81:0x0180, B:79:0x018a, B:82:0x018d, B:327:0x0713, B:329:0x0720, B:333:0x0738, B:331:0x0742, B:334:0x0745), top: B:4:0x0033 }] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean postNotification() {
                /*
                    Method dump skipped, instructions count: 1918
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.NotificationListeners.AnonymousClass1.postNotification():boolean");
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r4v5, types: [com.android.server.notification.NotificationManagerService$PostNotificationTracker] */
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    switch (this.$r8$classId) {
                        case 0:
                            INotificationListener iNotificationListener = ((ManagedServices.ManagedServiceInfo) this.val$info).service;
                            try {
                                iNotificationListener.onEdgeNotificationRemoved(this.val$pkg, this.val$id, (Bundle) this.val$extra);
                                return;
                            } catch (RemoteException e) {
                                Log.e(((NotificationListeners) this.this$1).TAG, "unable to notify listener (posted): " + iNotificationListener, e);
                                return;
                            }
                        case 1:
                            INotificationListener iNotificationListener2 = ((ManagedServices.ManagedServiceInfo) this.val$info).service;
                            try {
                                iNotificationListener2.onEdgeNotificationPosted(this.val$pkg, this.val$id, (Bundle) this.val$extra);
                                return;
                            } catch (RemoteException e2) {
                                Log.e(((NotificationListeners) this.this$1).TAG, "unable to notify listener (posted): " + iNotificationListener2, e2);
                                return;
                            }
                        default:
                            try {
                                if (postNotification()) {
                                    return;
                                }
                            } catch (Exception e3) {
                                Slog.e("NotificationService", "Error posting", e3);
                            }
                            return;
                    }
                } finally {
                    ((PostNotificationTracker) this.val$extra).cancel();
                }
                ((PostNotificationTracker) this.val$extra).cancel();
            }
        }

        public NotificationListeners(Context context, Object obj, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager, boolean z) {
            super(context, obj, userProfiles, iPackageManager);
            this.mLightTrimListeners = new ArraySet();
            this.mTrustedListenerUids = new ArraySet();
            this.mTrustListenerUpdateHistory = new ArrayList();
            this.mSystemUIUid = -1;
            this.mRequestedNotificationListeners = new ArrayMap();
            this.mIsHeadlessSystemUserMode = z;
        }

        public static int getTypesFromStringList(String str) {
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

        public static boolean hasSensitiveContent(NotificationRecord notificationRecord) {
            if (notificationRecord == null || !com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                return false;
            }
            return notificationRecord.mSensitiveContent;
        }

        @Override // com.android.server.notification.ManagedServices
        public final boolean allowRebindForParentUser() {
            return true;
        }

        @Override // com.android.server.notification.ManagedServices
        public final IInterface asInterface(IBinder iBinder) {
            return INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void dump(PrintWriter printWriter, DumpFilter dumpFilter) {
            super.dump(printWriter, dumpFilter);
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "\n  Trusted Listener Uids:", "    mSystemUIUid="), this.mSystemUIUid, printWriter, "    mTrustedListenerUids=");
            m.append(this.mTrustedListenerUids);
            printWriter.println(m.toString());
            printWriter.println("    mTrustListenerUpdateHistory=" + this.mTrustListenerUpdateHistory);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void ensureFilters(ServiceInfo serviceInfo, int i) {
            int typesFromStringList;
            String obj;
            Pair create = Pair.create(serviceInfo.getComponentName(), Integer.valueOf(i));
            synchronized (this.mRequestedNotificationListeners) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final int getBindFlags() {
            return 83886337;
        }

        @Override // com.android.server.notification.ManagedServices
        public final ManagedServices.Config getConfig() {
            ManagedServices.Config config = new ManagedServices.Config();
            config.caption = "notification listener";
            config.serviceInterface = "android.service.notification.NotificationListenerService";
            config.xmlTag = "enabled_listeners";
            config.secureSettingName = "enabled_notification_listeners";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
            config.settingsAction = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
            config.clientLabel = R.string.shortcut_restore_unknown_issue;
            return config;
        }

        public final NotificationListenerFilter getNotificationListenerFilter(Pair pair) {
            NotificationListenerFilter notificationListenerFilter;
            synchronized (this.mRequestedNotificationListeners) {
                notificationListenerFilter = (NotificationListenerFilter) this.mRequestedNotificationListeners.get(pair);
            }
            return notificationListenerFilter;
        }

        @Override // com.android.server.notification.ManagedServices
        public final String getRequiredPermission() {
            return null;
        }

        public final boolean hasAllowedListener(int i, String str) {
            if (str == null) {
                return false;
            }
            List allowedComponents = getAllowedComponents(i);
            int i2 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) allowedComponents;
                if (i2 >= arrayList.size()) {
                    return false;
                }
                if (((ComponentName) arrayList.get(i2)).getPackageName().equals(str)) {
                    return true;
                }
                i2++;
            }
        }

        public final boolean isAppTrustedNotificationListenerService(int i, String str) {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            if (!com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    Slog.e(this.TAG, "Failed to check trusted status of listener", e);
                }
                if (notificationManagerService.mPackageManager.checkUidPermission("android.permission.RECEIVE_SENSITIVE_NOTIFICATIONS", i) != 0 && !notificationManagerService.mPackageManagerInternal.isPlatformSigned(str) && notificationManagerService.mAppOps.noteOpNoThrow(148, i, str, (String) null, (String) null) != 0 && !str.equals(Constants.SYSTEMUI_PACKAGE_NAME)) {
                    List arrayList = new ArrayList();
                    if (notificationManagerService.mCompanionManager == null) {
                        notificationManagerService.mCompanionManager = ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService("companiondevice"));
                    }
                    ICompanionDeviceManager iCompanionDeviceManager = notificationManagerService.mCompanionManager;
                    if (iCompanionDeviceManager != null) {
                        arrayList = iCompanionDeviceManager.getAllAssociationsForUser(UserHandle.getUserId(i));
                    }
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        AssociationInfo associationInfo = (AssociationInfo) arrayList.get(i2);
                        if (!associationInfo.isRevoked() && str.equals(associationInfo.getPackageName()) && associationInfo.getUserId() == UserHandle.getUserId(i)) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                return true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isUidTrusted(int i) {
            boolean z;
            synchronized (this.mTrustedListenerUids) {
                try {
                    z = !com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() || this.mTrustedListenerUids.contains(Integer.valueOf(i));
                } finally {
                }
            }
            return z;
        }

        @Override // com.android.server.notification.ManagedServices
        public final void loadDefaultsFromConfig() {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(this.mContext.getResources().getString(R.string.decline_remote_bugreport_action), ":");
            m.append(this.mContext.getResources().getString(R.string.face_acquired_mouth_covering_detected));
            String sb = m.toString();
            if (sb != null) {
                String[] split = sb.split(":");
                for (int i = 0; i < split.length; i++) {
                    if (!TextUtils.isEmpty(split[i])) {
                        ArraySet queryPackageForServices = queryPackageForServices(this.mIsHeadlessSystemUserMode ? 4980736 : 786432, 0, split[i]);
                        for (int i2 = 0; i2 < queryPackageForServices.size(); i2++) {
                            addDefaultComponentOrPackage(((ComponentName) queryPackageForServices.valueAt(i2)).flattenToString());
                        }
                    }
                }
            }
        }

        public final void notifyNotificationChannelChanged(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            if (notificationChannel == null) {
                return;
            }
            Iterator it = ((ArrayList) getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                if (managedServiceInfo.enabledAndUserMatches(UserHandle.getCallingUserId()) && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, UserHandle.getCallingUserId())) {
                    BackgroundThread.getHandler().post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3(this, managedServiceInfo, str, userHandle, notificationChannel, i));
                }
            }
        }

        public final void notifyNotificationChannelGroupChanged(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
            if (notificationChannelGroup == null) {
                return;
            }
            Iterator it = ((ArrayList) getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                if (managedServiceInfo.enabledAndUserMatches(UserHandle.getCallingUserId()) && NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, UserHandle.getCallingUserId())) {
                    BackgroundThread.getHandler().post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3(this, managedServiceInfo, str, userHandle, notificationChannelGroup, i));
                }
            }
        }

        public final void notifyPosted(ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) {
            String str = this.TAG;
            try {
                managedServiceInfo.service.onNotificationPosted(new StatusBarNotificationHolder(statusBarNotification), notificationRankingUpdate);
            } catch (DeadObjectException e) {
                Slog.wtf(str, "unable to notify listener (posted): " + managedServiceInfo, e);
            } catch (RemoteException e2) {
                Slog.e(str, "unable to notify listener (posted): " + managedServiceInfo, e2);
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

        public final void notifyRankingUpdateLocked(List list) {
            boolean z = list != null && ((ArrayList) list).size() > 0;
            Iterator it = ((ArrayList) getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                if (managedServiceInfo.isEnabledForCurrentProfiles()) {
                    int currentUser = ActivityManager.getCurrentUser();
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    if (notificationManagerService.isInteractionVisibleToListener(managedServiceInfo, currentUser)) {
                        if (z && managedServiceInfo.targetSdkVersion >= 28) {
                            Iterator it2 = ((ArrayList) list).iterator();
                            while (it2.hasNext()) {
                                NotificationRecord notificationRecord = (NotificationRecord) it2.next();
                                if (notificationManagerService.isVisibleToListener(notificationRecord.sbn, notificationRecord.getNotificationType(), managedServiceInfo)) {
                                    break;
                                }
                            }
                        }
                        if (!z) {
                            notificationManagerService.mHandler.post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8(this, managedServiceInfo, notificationManagerService.makeRankingUpdateLocked(managedServiceInfo), 0));
                        }
                    }
                }
            }
        }

        public final void notifyRemoved(ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) {
            String str = this.TAG;
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
                Slog.wtf(str, "unable to notify listener (removed): " + managedServiceInfo, e);
            } catch (RemoteException e2) {
                Slog.e(str, "unable to notify listener (removed): " + managedServiceInfo, e2);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0061, code lost:
        
            if (r2.targetSdkVersion < 28) goto L49;
         */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x009b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void notifyRemovedLocked(com.android.server.notification.NotificationRecord r19, int r20, android.service.notification.NotificationStats r21) {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                android.service.notification.StatusBarNotification r0 = r8.sbn
                android.os.UserHandle r0 = r0.getUser()
                int r0 = r0.getIdentifier()
                com.android.server.notification.NotificationManagerService r10 = com.android.server.notification.NotificationManagerService.this
                com.android.server.notification.NotificationManagerService$StrongAuthTracker r1 = r10.mStrongAuthTracker
                android.util.SparseBooleanArray r1 = r1.mUserInLockDownMode
                r11 = 0
                boolean r0 = r1.get(r0, r11)
                if (r0 == 0) goto L1e
                return
            L1e:
                android.service.notification.StatusBarNotification r12 = r8.sbn
                android.service.notification.StatusBarNotification r13 = r12.cloneLight()
                boolean r14 = hasSensitiveContent(r19)
                java.util.List r0 = r18.getServices()
                java.util.ArrayList r0 = (java.util.ArrayList) r0
                java.util.Iterator r15 = r0.iterator()
                r16 = 0
                r0 = r16
            L36:
                boolean r1 = r15.hasNext()
                if (r1 == 0) goto Lb8
                java.lang.Object r1 = r15.next()
                r2 = r1
                com.android.server.notification.ManagedServices$ManagedServiceInfo r2 = (com.android.server.notification.ManagedServices.ManagedServiceInfo) r2
                int r1 = r19.getNotificationType()
                boolean r1 = r10.isVisibleToListener(r12, r1, r2)
                if (r1 != 0) goto L4e
                goto L36
            L4e:
                boolean r1 = r8.mHidden
                r3 = 28
                r4 = 14
                if (r1 == 0) goto L5d
                if (r9 == r4) goto L5d
                int r1 = r2.targetSdkVersion
                if (r1 >= r3) goto L5d
                goto L36
            L5d:
                if (r9 != r4) goto L64
                int r1 = r2.targetSdkVersion
                if (r1 < r3) goto L64
                goto L36
            L64:
                boolean r1 = com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()
                if (r1 == 0) goto L76
                if (r14 == 0) goto L76
                int r1 = r2.uid
                boolean r1 = r7.isUidTrusted(r1)
                if (r1 != 0) goto L76
                r1 = 1
                goto L77
            L76:
                r1 = r11
            L77:
                if (r1 == 0) goto L7f
                if (r0 != 0) goto L7f
                android.service.notification.StatusBarNotification r0 = r7.redactStatusBarNotification(r12)
            L7f:
                r17 = r0
                com.android.server.notification.NotificationManagerService$NotificationAssistants r0 = r10.mAssistants
                android.os.IInterface r3 = r2.service
                if (r3 != 0) goto L8b
                r0.getClass()
                goto L94
            L8b:
                com.android.server.notification.ManagedServices$ManagedServiceInfo r0 = r0.getServiceFromTokenLocked(r3)
                if (r0 == 0) goto L94
                r5 = r21
                goto L96
            L94:
                r5 = r16
            L96:
                if (r1 == 0) goto L9b
                r3 = r17
                goto L9c
            L9b:
                r3 = r13
            L9c:
                android.service.notification.NotificationRankingUpdate r4 = r10.makeRankingUpdateLocked(r2)
                com.android.server.notification.NotificationManagerService$WorkerHandler r6 = r10.mHandler
                com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3 r1 = new com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3
                r0 = r1
                r11 = r1
                r1 = r18
                r9 = r6
                r6 = r20
                r0.<init>(r1, r2, r3, r4, r5, r6)
                r9.post(r11)
                r9 = r20
                r0 = r17
                r11 = 0
                goto L36
            Lb8:
                com.android.server.notification.NotificationManagerService$WorkerHandler r0 = r10.mHandler
                com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda7 r1 = new com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda7
                r2 = 3
                r1.<init>(r2, r7, r8)
                r0.post(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.NotificationListeners.notifyRemovedLocked(com.android.server.notification.NotificationRecord, int, android.service.notification.NotificationStats):void");
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
            super.onPackagesChanged(z, strArr, iArr);
            synchronized (this.mRequestedNotificationListeners) {
                if (z) {
                    for (int i = 0; i < strArr.length; i++) {
                        try {
                            String str = strArr[i];
                            int userId = UserHandle.getUserId(iArr[i]);
                            for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                                Pair pair = (Pair) this.mRequestedNotificationListeners.keyAt(size);
                                if (((Integer) pair.second).intValue() == userId && ((ComponentName) pair.first).getPackageName().equals(str)) {
                                    this.mRequestedNotificationListeners.removeAt(size);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
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
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            NotificationRankingUpdate makeRankingUpdateLocked;
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                NotificationManagerService.this.getClass();
                managedServiceInfo.isSystemUi = !NotificationManagerService.isCallerSystemOrPhone() && NotificationManagerService.this.getContext().checkPermission("android.permission.STATUS_BAR_SERVICE", -1, managedServiceInfo.uid) == 0;
            }
            INotificationListener iNotificationListener = managedServiceInfo.service;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                makeRankingUpdateLocked = NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, true);
            }
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && isAppTrustedNotificationListenerService(managedServiceInfo.uid, managedServiceInfo.component.getPackageName())) {
                synchronized (this.mTrustedListenerUids) {
                    this.mTrustedListenerUids.add(Integer.valueOf(managedServiceInfo.uid));
                    this.mTrustListenerUpdateHistory.add("onServiceAdded : add " + managedServiceInfo.uid);
                }
            }
            try {
                iNotificationListener.onListenerConnected(makeRankingUpdateLocked);
                if (managedServiceInfo.isSystem) {
                    try {
                        ApplicationInfo applicationInfo = NotificationManagerService.this.getContext().getPackageManager().getApplicationInfo(managedServiceInfo.component.getPackageName(), 128);
                        if (applicationInfo == null || applicationInfo.metaData == null) {
                            return;
                        }
                        Log.d(this.TAG, "Trim appInfo.metaData = " + applicationInfo.metaData);
                        if (applicationInfo.metaData.getInt("com.samsung.android.notification.listener.trim", 0) == 1) {
                            this.mLightTrimListeners.add(managedServiceInfo);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RemoteException unused) {
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, false);
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            boolean z = NotificationManagerService.DBG;
            if (notificationManagerService.removeDisabledHints(managedServiceInfo, 0)) {
                NotificationManagerService.m723$$Nest$mupdateListenerHintsLocked(NotificationManagerService.this);
                NotificationManagerService.m721$$Nest$mupdateEffectsSuppressorLocked(NotificationManagerService.this);
            }
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                synchronized (this.mTrustedListenerUids) {
                    try {
                        if (this.mSystemUIUid == -1) {
                            this.mSystemUIUid = NotificationManagerService.this.mPackageManagerInternal.getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 0L, ActivityManager.getCurrentUser());
                        }
                        if (this.mSystemUIUid != managedServiceInfo.uid) {
                            this.mTrustListenerUpdateHistory.add("onServiceRemovedLocked : remove " + managedServiceInfo.uid);
                            this.mTrustedListenerUids.remove(Integer.valueOf(managedServiceInfo.uid));
                        }
                    } finally {
                    }
                }
            }
            this.mLightTrimListeners.remove(managedServiceInfo);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void onUserRemoved(int i) {
            super.onUserRemoved(i);
            synchronized (this.mRequestedNotificationListeners) {
                try {
                    for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                        if (((Integer) ((Pair) this.mRequestedNotificationListeners.keyAt(size)).second).intValue() == i) {
                            this.mRequestedNotificationListeners.removeAt(size);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:55:0x0115, code lost:
        
            r11.add(new com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda15(r33, r5, r4, r9.makeRankingUpdateLocked(r5), 1));
         */
        /* JADX WARN: Removed duplicated region for block: B:104:0x012d  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00ba  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x017c  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x01b1 A[Catch: Exception -> 0x008f, TryCatch #0 {Exception -> 0x008f, blocks: (B:8:0x0028, B:10:0x002d, B:11:0x0032, B:12:0x0049, B:14:0x004f, B:19:0x006a, B:24:0x0077, B:26:0x0081, B:35:0x009e, B:37:0x00a8, B:39:0x00b4, B:43:0x00be, B:45:0x00cc, B:47:0x00f9, B:50:0x00ff, B:52:0x0107, B:55:0x0115, B:60:0x0136, B:64:0x0148, B:69:0x0154, B:71:0x015c, B:73:0x0167, B:74:0x0162, B:75:0x0177, B:78:0x017d, B:81:0x01a3, B:83:0x01b1, B:84:0x01d5, B:85:0x01b7, B:88:0x01c4, B:92:0x01cc, B:94:0x00d8, B:97:0x00e7, B:101:0x00ef), top: B:7:0x0028 }] */
        /* JADX WARN: Removed duplicated region for block: B:85:0x01b7 A[Catch: Exception -> 0x008f, TryCatch #0 {Exception -> 0x008f, blocks: (B:8:0x0028, B:10:0x002d, B:11:0x0032, B:12:0x0049, B:14:0x004f, B:19:0x006a, B:24:0x0077, B:26:0x0081, B:35:0x009e, B:37:0x00a8, B:39:0x00b4, B:43:0x00be, B:45:0x00cc, B:47:0x00f9, B:50:0x00ff, B:52:0x0107, B:55:0x0115, B:60:0x0136, B:64:0x0148, B:69:0x0154, B:71:0x015c, B:73:0x0167, B:74:0x0162, B:75:0x0177, B:78:0x017d, B:81:0x01a3, B:83:0x01b1, B:84:0x01d5, B:85:0x01b7, B:88:0x01c4, B:92:0x01cc, B:94:0x00d8, B:97:0x00e7, B:101:0x00ef), top: B:7:0x0028 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.util.List prepareNotifyPostedLocked(com.android.server.notification.NotificationRecord r34, com.android.server.notification.NotificationRecord r35, boolean r36) {
            /*
                Method dump skipped, instructions count: 512
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.NotificationListeners.prepareNotifyPostedLocked(com.android.server.notification.NotificationRecord, com.android.server.notification.NotificationRecord, boolean):java.util.List");
        }

        @Override // com.android.server.notification.ManagedServices
        public final void readExtraTag(TypedXmlPullParser typedXmlPullParser, String str) {
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

        public final StatusBarNotification redactStatusBarNotification(StatusBarNotification statusBarNotification) {
            String packageName;
            if (!com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                throw new RuntimeException("redactStatusBarNotification called while flag is off");
            }
            ApplicationInfo applicationInfo = (ApplicationInfo) statusBarNotification.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            if (applicationInfo != null) {
                packageName = applicationInfo.loadLabel(notificationManagerService.mPackageManagerClient).toString();
            } else {
                Slog.w(this.TAG, "StatusBarNotification " + statusBarNotification + " does not have ApplicationInfo. Did you pass in a 'cloneLight' notification?");
                packageName = statusBarNotification.getPackageName();
            }
            String string = this.mContext.getString(17042534);
            Notification notification = statusBarNotification.getNotification();
            Notification notification2 = new Notification();
            notification.cloneInto(notification2, false);
            Notification.Builder builder = new Notification.Builder(notificationManagerService.getContext(), notification2);
            builder.setContentTitle(packageName);
            builder.setContentText(string);
            builder.setSubText(null);
            builder.setActions(new Notification.Action[0]);
            if (notification.actions != null) {
                for (int i = 0; i < notification.actions.length; i++) {
                    Notification.Action build = new Notification.Action.Builder(notification.actions[i]).build();
                    build.title = this.mContext.getString(17042533);
                    builder.addAction(build);
                }
            }
            if (notification.isStyle(Notification.MessagingStyle.class)) {
                Person build2 = new Person.Builder().setName("").build();
                Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle(build2);
                messagingStyle.addMessage(new Notification.MessagingStyle.Message(string, System.currentTimeMillis(), build2));
                builder.setStyle(messagingStyle);
            }
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsBigTextStyle() && notification.isStyle(Notification.BigTextStyle.class)) {
                Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
                bigTextStyle.bigText(this.mContext.getString(17042534));
                bigTextStyle.setBigContentTitle("");
                bigTextStyle.setSummaryText("");
                builder.setStyle(bigTextStyle);
            }
            Notification build3 = builder.build();
            if (build3.extras.containsKey("android.title.big")) {
                build3.extras.putString("android.title.big", packageName);
            }
            build3.extras.remove("android.subText");
            build3.extras.remove("android.textLines");
            build3.extras.remove("android.largeIcon.big");
            return statusBarNotification.cloneShallow(build3);
        }

        @Override // com.android.server.notification.ManagedServices
        public final void setPackageOrComponentEnabled(int i, String str, boolean z, boolean z2, boolean z3) {
            super.setPackageOrComponentEnabled(i, str, true, z2, z3);
            String packageName = ManagedServices.getPackageName(str);
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                int packageUid = NotificationManagerService.this.mPackageManagerInternal.getPackageUid(packageName, 0L, i);
                if (!z2 && packageUid >= 0) {
                    synchronized (this.mTrustedListenerUids) {
                        try {
                            if (this.mSystemUIUid == -1) {
                                this.mSystemUIUid = NotificationManagerService.this.mPackageManagerInternal.getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 0L, ActivityManager.getCurrentUser());
                            }
                            if (this.mSystemUIUid != packageUid) {
                                this.mTrustListenerUpdateHistory.add("setPackageOrComponentEnabled : remove " + packageUid + " " + packageName);
                                this.mTrustedListenerUids.remove(Integer.valueOf(packageUid));
                            }
                        } finally {
                        }
                    }
                }
                if (z2 && packageUid >= 0 && isAppTrustedNotificationListenerService(packageUid, packageName)) {
                    synchronized (this.mTrustedListenerUids) {
                        this.mTrustedListenerUids.add(Integer.valueOf(packageUid));
                        this.mTrustListenerUpdateHistory.add("setPackageOrComponentEnabled : add " + packageUid);
                    }
                }
            }
            this.mContext.sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED").addFlags(1073741824), UserHandle.of(i), null);
        }

        public final void updateUriPermissionsForActiveNotificationsLocked(ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            try {
                Iterator it = notificationManagerService.mNotificationList.iterator();
                while (it.hasNext()) {
                    NotificationRecord notificationRecord = (NotificationRecord) it.next();
                    if (!z || notificationManagerService.isVisibleToListener(notificationRecord.sbn, notificationRecord.getNotificationType(), managedServiceInfo)) {
                        if (!notificationRecord.mHidden || managedServiceInfo.targetSdkVersion >= 28) {
                            int i = managedServiceInfo.userid;
                            if (i == -1) {
                                i = 0;
                            }
                            int i2 = i;
                            if (z) {
                                notificationManagerService.updateUriPermissions(notificationRecord, null, managedServiceInfo.component.getPackageName(), i2);
                            } else {
                                NotificationManagerService.this.updateUriPermissions(null, notificationRecord, managedServiceInfo.component.getPackageName(), i2, true);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Could not ");
                sb.append(z ? "grant" : "revoke");
                sb.append(" Uri permissions to ");
                sb.append(managedServiceInfo.component);
                Slog.e(this.TAG, sb.toString(), e);
            }
        }

        @Override // com.android.server.notification.ManagedServices
        public final void writeExtraXmlTags(TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.startTag((String) null, "request_listeners");
            synchronized (this.mRequestedNotificationListeners) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
            typedXmlSerializer.endTag((String) null, "request_listeners");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PostNotificationTracker {
        public final PowerManager.WakeLock mWakeLock;
        public final long mStartTime = SystemClock.elapsedRealtime();
        public boolean mOngoing = true;

        public PostNotificationTracker(PowerManager.WakeLock wakeLock) {
            this.mWakeLock = wakeLock;
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", "PostNotification: Started");
            }
        }

        public final void cancel() {
            if (!isOngoing()) {
                Log.wtfStack("NotificationService", "cancel() called on already-finished tracker");
                return;
            }
            this.mOngoing = false;
            if (this.mWakeLock != null) {
                Binder.withCleanCallingIdentity(new NotificationManagerService$12$$ExternalSyntheticLambda3(1, this));
            }
            if (NotificationManagerService.DBG) {
                Slog.d("NotificationService", TextUtils.formatSimple("PostNotification: Abandoned after %d ms", new Object[]{Long.valueOf(SystemClock.elapsedRealtime() - this.mStartTime)}));
            }
        }

        public boolean isOngoing() {
            return this.mOngoing;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PostNotificationTrackerFactory {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RankingHandlerWorker extends Handler implements RankingHandler {
        public RankingHandlerWorker(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean shouldIntercept;
            boolean z;
            NotificationRecord notificationRecord;
            InstanceId instanceId;
            int i = message.what;
            if (i != 1000) {
                if (i != 1001) {
                    return;
                }
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                if (notificationManagerService.mRankingHelper == null) {
                    return;
                }
                synchronized (notificationManagerService.mNotificationLock) {
                    try {
                        int size = notificationManagerService.mNotificationList.size();
                        ArrayMap arrayMap = new ArrayMap(size);
                        int i2 = 0;
                        while (i2 < size) {
                            NotificationRecord notificationRecord2 = (NotificationRecord) notificationManagerService.mNotificationList.get(i2);
                            ArrayMap arrayMap2 = arrayMap;
                            arrayMap2.put(notificationRecord2.sbn.getKey(), new NotificationRecordExtractorData(i2, notificationRecord2.mPackageVisibility, notificationRecord2.mShowBadge, notificationRecord2.mAllowBubble, notificationRecord2.sbn.getNotification().isBubbleNotification(), notificationRecord2.mChannel, notificationRecord2.sbn.getGroupKey(), notificationRecord2.mPeopleOverride, notificationRecord2.mSnoozeCriteria, Integer.valueOf(notificationRecord2.mUserSentiment), Integer.valueOf(notificationRecord2.mSuppressedVisualEffects), notificationRecord2.mSystemGeneratedSmartActions, notificationRecord2.mSmartReplies, notificationRecord2.mImportance, notificationRecord2.mRankingScore, notificationRecord2.isConversation(), notificationRecord2.mProposedImportance, notificationRecord2.mSensitiveContent));
                            notificationManagerService = notificationManagerService;
                            notificationManagerService.mRankingHelper.extractSignals(notificationRecord2);
                            i2++;
                            arrayMap = arrayMap2;
                            size = size;
                        }
                        int i3 = size;
                        ArrayMap arrayMap3 = arrayMap;
                        notificationManagerService.mRankingHelper.sort(notificationManagerService.mNotificationList);
                        for (int i4 = 0; i4 < i3; i4++) {
                            NotificationRecord notificationRecord3 = (NotificationRecord) notificationManagerService.mNotificationList.get(i4);
                            if (arrayMap3.containsKey(notificationRecord3.sbn.getKey())) {
                                if (((NotificationRecordExtractorData) arrayMap3.get(notificationRecord3.sbn.getKey())).hasDiffForRankingLocked(notificationRecord3, i4)) {
                                    notificationManagerService.mHandler.scheduleSendRankingUpdate();
                                }
                                if (notificationRecord3.mPendingLogUpdate) {
                                    if (((NotificationRecordExtractorData) arrayMap3.get(notificationRecord3.sbn.getKey())).hasDiffForLoggingLocked(notificationRecord3, i4)) {
                                        NotificationRecordLogger notificationRecordLogger = notificationManagerService.mNotificationRecordLogger;
                                        String groupKey = notificationRecord3.sbn.getGroupKey();
                                        if (groupKey != null && (notificationRecord = (NotificationRecord) notificationManagerService.mSummaryByGroupKey.get(groupKey)) != null) {
                                            instanceId = notificationRecord.sbn.getInstanceId();
                                            ((NotificationRecordLoggerImpl) notificationRecordLogger).getClass();
                                            NotificationRecordLoggerImpl.writeNotificationReportedAtom(new NotificationRecordLogger.NotificationReported(new NotificationRecordLogger.NotificationRecordPair(notificationRecord3, null), NotificationRecordLogger.NotificationReportedEvent.NOTIFICATION_ADJUSTED, i4, 0, instanceId));
                                        }
                                        instanceId = null;
                                        ((NotificationRecordLoggerImpl) notificationRecordLogger).getClass();
                                        NotificationRecordLoggerImpl.writeNotificationReportedAtom(new NotificationRecordLogger.NotificationReported(new NotificationRecordLogger.NotificationRecordPair(notificationRecord3, null), NotificationRecordLogger.NotificationReportedEvent.NOTIFICATION_ADJUSTED, i4, 0, instanceId));
                                    }
                                    notificationRecord3.mPendingLogUpdate = false;
                                }
                            }
                        }
                    } finally {
                    }
                }
                return;
            }
            NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
            notificationManagerService2.getClass();
            Object obj = message.obj;
            if (obj instanceof RankingReconsideration) {
                RankingReconsideration rankingReconsideration = (RankingReconsideration) obj;
                rankingReconsideration.run();
                synchronized (notificationManagerService2.mNotificationLock) {
                    try {
                        NotificationRecord notificationRecord4 = (NotificationRecord) notificationManagerService2.mNotificationsByKey.get(rankingReconsideration.mKey);
                        if (notificationRecord4 == null) {
                            return;
                        }
                        int binarySearch = Collections.binarySearch(notificationManagerService2.mNotificationList, notificationRecord4, notificationManagerService2.mRankingHelper.mFinalComparator);
                        boolean z2 = notificationRecord4.mIntercept;
                        int i5 = notificationRecord4.mPackageVisibility;
                        boolean z3 = notificationRecord4.mIsInterruptive;
                        rankingReconsideration.applyChangesLocked(notificationRecord4);
                        ZenModeHelper zenModeHelper = notificationManagerService2.mZenModeHelper;
                        synchronized (zenModeHelper.mConfigLock) {
                            shouldIntercept = zenModeHelper.mFiltering.shouldIntercept(zenModeHelper.mZenMode, zenModeHelper.mConsolidatedPolicy, notificationRecord4);
                        }
                        notificationRecord4.mIntercept = shouldIntercept;
                        notificationRecord4.mInterceptSet = true;
                        if (shouldIntercept) {
                            notificationRecord4.mSuppressedVisualEffects = notificationManagerService2.mZenModeHelper.mConsolidatedPolicy.copy().suppressedVisualEffects;
                            z = false;
                        } else {
                            z = false;
                            notificationRecord4.mSuppressedVisualEffects = 0;
                        }
                        notificationManagerService2.mRankingHelper.sort(notificationManagerService2.mNotificationList);
                        boolean z4 = binarySearch != Collections.binarySearch(notificationManagerService2.mNotificationList, notificationRecord4, notificationManagerService2.mRankingHelper.mFinalComparator) ? true : z;
                        boolean z5 = notificationRecord4.mIntercept;
                        boolean z6 = z2 != z5 ? true : z;
                        boolean z7 = i5 != notificationRecord4.mPackageVisibility ? true : z;
                        boolean z8 = (!notificationRecord4.mAllowBubble || z3 == notificationRecord4.mIsInterruptive) ? z : true;
                        if (z4 || z6 || z7 || z8) {
                            z = true;
                        }
                        if (z2 && !z5 && ((int) (System.currentTimeMillis() - notificationRecord4.mUpdateTimeMs)) <= 2000) {
                            notificationManagerService2.mAttentionHelper.buzzBeepBlinkLocked(notificationRecord4, new NotificationAttentionHelper.Signals(notificationManagerService2.mListenerHints, notificationManagerService2.mUserProfiles.isCurrentProfile(notificationRecord4.sbn.getUserId())));
                            LocalLog localLog = ZenLog.STATE_CHANGES;
                            ZenLog.append(21, notificationRecord4.sbn.getKey());
                        }
                        if (z) {
                            notificationManagerService2.mHandler.scheduleSendRankingUpdate();
                        }
                    } finally {
                    }
                }
            }
        }

        public final void requestSort() {
            Flags.notificationReduceMessagequeueUsage();
            removeMessages(1001);
            Message obtain = Message.obtain();
            obtain.what = 1001;
            sendMessage(obtain);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RoleObserver implements OnRoleHoldersChangedListener {
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

        public final int getUidForPackage(int i, String str) {
            try {
                return this.mPm.getPackageUid(str, 131072L, i);
            } catch (RemoteException unused) {
                Slog.e("NotificationService", "role manager has bad default " + str + " " + i);
                return -1;
            }
        }

        public boolean isApprovedPackageForRoleForUser(String str, String str2, int i) {
            return ((ArraySet) ((ArrayMap) this.mNonBlockableDefaultApps.get(str)).get(Integer.valueOf(i))).contains(str2);
        }

        public boolean isUidExemptFromTrampolineRestrictions(int i) {
            return this.mTrampolineExemptUids.contains(Integer.valueOf(i));
        }

        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            int i = 0;
            while (true) {
                String[] strArr = NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES;
                if (i >= strArr.length) {
                    break;
                }
                if (strArr[i].equals(str)) {
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
                            arraySet4.add(new Pair(str3, Integer.valueOf(getUidForPackage(userHandle.getIdentifier(), str3))));
                        }
                    }
                    arrayMap.put(Integer.valueOf(userHandle.getIdentifier()), arraySet);
                    this.mNonBlockableDefaultApps.put(str, arrayMap);
                    NotificationManagerService.this.mPreferencesHelper.updateDefaultApps(userHandle.getIdentifier(), arraySet3, arraySet4);
                } else {
                    i++;
                }
            }
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
                Integer num = (Integer) arraySet.valueAt(i);
                if (!ArrayUtils.contains(userHandleArr, UserHandle.of(UserHandle.getUserId(num.intValue())))) {
                    arraySet2.add(num);
                }
            }
            for (UserHandle userHandle : userHandleArr) {
                for (String str : this.mRm.getRoleHoldersAsUser("android.app.role.BROWSER", userHandle)) {
                    int uidForPackage = getUidForPackage(userHandle.getIdentifier(), str);
                    if (uidForPackage != -1) {
                        arraySet2.add(Integer.valueOf(uidForPackage));
                    } else {
                        BootReceiver$$ExternalSyntheticOutline0.m("Bad uid (-1) for browser package ", str, "NotificationService");
                    }
                }
            }
            this.mTrampolineExemptUids = arraySet2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri ALERTONCALL_MODE_URI;
        public final Uri DISABLE_HUN_FOR_CALLUI_URI;
        public final Uri FLOATING_NOTI_PACKAGE_LIST_URI;
        public final Uri LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS;
        public final Uri LOCK_SCREEN_SHOW_NOTIFICATIONS;
        public final Uri NOTIFICATION_BADGING_URI;
        public final Uri NOTIFICATION_BUBBLES_URI;
        public final Uri NOTIFICATION_HISTORY_ENABLED;
        public final Uri NOTIFICATION_RATE_LIMIT_URI;
        public final Uri NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI;
        public final Uri NOTIFICATION_VIBRATION_SEP_URI;
        public final Uri SHOW_NOTIFICATION_SNOOZE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.NOTIFICATION_BADGING_URI = Settings.Secure.getUriFor("notification_badging");
            this.NOTIFICATION_BUBBLES_URI = Settings.Secure.getUriFor("notification_bubbles");
            this.NOTIFICATION_RATE_LIMIT_URI = Settings.Global.getUriFor("max_notification_enqueue_rate");
            this.NOTIFICATION_HISTORY_ENABLED = Settings.Secure.getUriFor("notification_history_enabled");
            this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI = Settings.Global.getUriFor("qs_media_controls");
            this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
            this.LOCK_SCREEN_SHOW_NOTIFICATIONS = Settings.Secure.getUriFor("lock_screen_show_notifications");
            this.NOTIFICATION_VIBRATION_SEP_URI = Settings.System.getUriFor("notification_vibration_sep_index");
            this.DISABLE_HUN_FOR_CALLUI_URI = Settings.System.getUriFor("disable_hun_for_callui");
            this.ALERTONCALL_MODE_URI = Settings.System.getUriFor("alertoncall_mode");
            this.FLOATING_NOTI_PACKAGE_LIST_URI = Settings.Secure.getUriFor("floating_noti_package_list");
            this.SHOW_NOTIFICATION_SNOOZE = Settings.Secure.getUriFor("show_notification_snooze");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            update(uri);
        }

        public final void update(int i, Uri uri) {
            ContentResolver contentResolver = NotificationManagerService.this.getContext().getContentResolver();
            if (uri == null || this.NOTIFICATION_HISTORY_ENABLED.equals(uri)) {
                NotificationManagerService.this.mArchive.updateHistoryEnabled(i, Settings.Secure.getIntForUser(contentResolver, "notification_history_enabled", 1, i) == 1);
            }
        }

        public final void update(Uri uri) {
            boolean z;
            ContentResolver contentResolver = NotificationManagerService.this.getContext().getContentResolver();
            if (uri == null || this.NOTIFICATION_RATE_LIMIT_URI.equals(uri)) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                notificationManagerService.mMaxPackageEnqueueRate = Settings.Global.getFloat(contentResolver, "max_notification_enqueue_rate", notificationManagerService.mMaxPackageEnqueueRate);
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
                Iterator it = NotificationManagerService.this.mUm.getUsers().iterator();
                while (it.hasNext()) {
                    update(((UserInfo) it.next()).id, uri);
                }
            }
            if (uri == null || this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI.equals(uri)) {
                NotificationManagerService.this.mPreferencesHelper.updateMediaNotificationFilteringEnabled();
            }
            if (uri == null || this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS.equals(uri)) {
                PreferencesHelper preferencesHelper = NotificationManagerService.this.mPreferencesHelper;
                if (preferencesHelper.mLockScreenPrivateNotifications == null) {
                    preferencesHelper.mLockScreenPrivateNotifications = new SparseBooleanArray();
                }
                if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    int currentUser = ActivityManager.getCurrentUser();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    int intForUser = Settings.Secure.getIntForUser(preferencesHelper.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 1, currentUser);
                    z = preferencesHelper.mLockScreenPrivateNotificationsValue != intForUser;
                    preferencesHelper.mLockScreenPrivateNotificationsValue = intForUser;
                } else {
                    z = false;
                }
                for (int i = 0; i < preferencesHelper.mLockScreenPrivateNotifications.size(); i++) {
                    int keyAt = preferencesHelper.mLockScreenPrivateNotifications.keyAt(i);
                    boolean z2 = preferencesHelper.mLockScreenPrivateNotifications.get(keyAt);
                    boolean z3 = Settings.Secure.getIntForUser(preferencesHelper.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 1, keyAt) != 0;
                    preferencesHelper.mLockScreenPrivateNotifications.put(keyAt, z3);
                    z |= z2 != z3;
                }
                if (z) {
                    preferencesHelper.updateConfig();
                }
            }
            if (uri == null || this.LOCK_SCREEN_SHOW_NOTIFICATIONS.equals(uri)) {
                PreferencesHelper preferencesHelper2 = NotificationManagerService.this.mPreferencesHelper;
                if (preferencesHelper2.mLockScreenShowNotifications == null) {
                    preferencesHelper2.mLockScreenShowNotifications = new SparseBooleanArray();
                }
                boolean z4 = false;
                for (int i2 = 0; i2 < preferencesHelper2.mLockScreenShowNotifications.size(); i2++) {
                    int keyAt2 = preferencesHelper2.mLockScreenShowNotifications.keyAt(i2);
                    boolean z5 = preferencesHelper2.mLockScreenShowNotifications.get(keyAt2);
                    boolean z6 = Settings.Secure.getIntForUser(preferencesHelper2.mContext.getContentResolver(), "lock_screen_show_notifications", 1, keyAt2) != 0;
                    preferencesHelper2.mLockScreenShowNotifications.put(keyAt2, z6);
                    z4 |= z5 != z6;
                }
                if (z4) {
                    preferencesHelper2.updateConfig();
                }
            }
            if (NmRune.NM_POLICY_VIB_PICKER_CONCEPT && (uri == null || this.NOTIFICATION_VIBRATION_SEP_URI.equals(uri))) {
                try {
                    NotificationManagerService.this.mAttentionHelper.mVibrationIndex = Settings.System.getIntForUser(contentResolver, "notification_vibration_sep_index", -2);
                } catch (Settings.SettingNotFoundException e) {
                    Slog.w("NotificationService", "Failed to find VibrationIndex Settings", e);
                }
            }
            if (uri == null || this.DISABLE_HUN_FOR_CALLUI_URI.equals(uri)) {
                NotificationManagerService.this.mIsDisableHunByCall = Settings.System.getIntForUser(contentResolver, "disable_hun_for_callui", 0, -2) == 1;
            }
            if (uri == null || this.ALERTONCALL_MODE_URI.equals(uri)) {
                NotificationManagerService.this.mAttentionHelper.mIsEnableAlertByCall = Settings.System.getIntForUser(contentResolver, "alertoncall_mode", 1, -2) == 1;
            }
            if (NotificationManagerService.this.mMultiStarEnable && (uri == null || this.FLOATING_NOTI_PACKAGE_LIST_URI.equals(uri))) {
                String stringForUser = Settings.Secure.getStringForUser(NotificationManagerService.this.getContext().getContentResolver(), "floating_noti_package_list", 0);
                ArrayList arrayList = new ArrayList();
                NotificationManagerService.this.mFloatingPackageList = null;
                if (stringForUser != null) {
                    for (String str : stringForUser.split(":")) {
                        if (!"".equals(str)) {
                            arrayList.add(str);
                        }
                    }
                }
                if (arrayList.size() > 0) {
                    NotificationManagerService.this.mFloatingPackageList = arrayList;
                }
            }
            if (this.SHOW_NOTIFICATION_SNOOZE.equals(uri) && Settings.Secure.getIntForUser(contentResolver, "show_notification_snooze", 0, -2) == 0) {
                NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                synchronized (notificationManagerService2.mNotificationLock) {
                    notificationManagerService2.mSnoozeHelper.repostAll(notificationManagerService2.mUserProfiles.getCurrentProfileIds());
                    notificationManagerService2.handleSavePolicyFile();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShowNotificationPermissionPromptRunnable implements Runnable {
        public final String mPkgName;
        public final PermissionPolicyService.Internal mPpi;
        public final int mTaskId;
        public final int mUserId;

        public ShowNotificationPermissionPromptRunnable(String str, int i, int i2, PermissionPolicyService.Internal internal) {
            this.mPkgName = str;
            this.mUserId = i;
            this.mTaskId = i2;
            this.mPpi = internal;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ShowNotificationPermissionPromptRunnable)) {
                return false;
            }
            ShowNotificationPermissionPromptRunnable showNotificationPermissionPromptRunnable = (ShowNotificationPermissionPromptRunnable) obj;
            return Objects.equals(this.mPkgName, showNotificationPermissionPromptRunnable.mPkgName) && this.mUserId == showNotificationPermissionPromptRunnable.mUserId && this.mTaskId == showNotificationPermissionPromptRunnable.mTaskId;
        }

        public final int hashCode() {
            return Objects.hash(this.mPkgName, Integer.valueOf(this.mUserId), Integer.valueOf(this.mTaskId));
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mPpi.showNotificationPromptIfNeeded(this.mPkgName, this.mUserId, this.mTaskId, null);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SnoozeNotificationRunnable implements Runnable {
        public final long mDuration;
        public final String mKey;
        public final String mSnoozeCriterionId;

        public SnoozeNotificationRunnable(String str, long j, String str2) {
            this.mKey = str;
            this.mDuration = j;
            this.mSnoozeCriterionId = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            NotificationRecord notificationRecord;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                try {
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    String str = this.mKey;
                    NotificationRecord findNotificationByKeyLocked = notificationManagerService.findNotificationByKeyLocked(str);
                    if (findNotificationByKeyLocked == null) {
                        SnoozeHelper snoozeHelper = notificationManagerService.mSnoozeHelper;
                        synchronized (snoozeHelper.mLock) {
                            notificationRecord = (NotificationRecord) snoozeHelper.mSnoozedNotifications.get(str);
                        }
                        findNotificationByKeyLocked = notificationRecord;
                    }
                    if (findNotificationByKeyLocked != null) {
                        snoozeLocked(findNotificationByKeyLocked);
                    }
                } finally {
                }
            }
        }

        public final void snoozeLocked(NotificationRecord notificationRecord) {
            ArrayList arrayList = new ArrayList();
            if (notificationRecord.sbn.isGroup()) {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                String packageName = notificationRecord.sbn.getPackageName();
                String groupKey = notificationRecord.sbn.getGroupKey();
                int userId = notificationRecord.sbn.getUserId();
                SnoozeHelper snoozeHelper = notificationManagerService.mSnoozeHelper;
                snoozeHelper.getClass();
                ArrayList arrayList2 = new ArrayList();
                synchronized (snoozeHelper.mLock) {
                    for (int i = 0; i < snoozeHelper.mSnoozedNotifications.size(); i++) {
                        try {
                            NotificationRecord notificationRecord2 = (NotificationRecord) snoozeHelper.mSnoozedNotifications.valueAt(i);
                            if (notificationRecord2.sbn.getPackageName().equals(packageName) && notificationRecord2.sbn.getUserId() == userId && Objects.equals(notificationRecord2.sbn.getGroup(), groupKey)) {
                                arrayList2.add(notificationRecord2);
                            }
                        } finally {
                        }
                    }
                }
                arrayList2.addAll(notificationManagerService.findGroupNotificationsLocked(userId, packageName, groupKey));
                if (notificationRecord.sbn.getNotification().isGroupSummary()) {
                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                        if (!this.mKey.equals(((NotificationRecord) arrayList2.get(i2)).sbn.getKey())) {
                            arrayList.add((NotificationRecord) arrayList2.get(i2));
                        }
                    }
                } else if (NotificationManagerService.this.mSummaryByGroupKey.containsKey(notificationRecord.sbn.getGroupKey()) && arrayList2.size() == 2) {
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        if (!this.mKey.equals(((NotificationRecord) arrayList2.get(i3)).sbn.getKey())) {
                            arrayList.add((NotificationRecord) arrayList2.get(i3));
                        }
                    }
                }
            }
            arrayList.add(notificationRecord);
            SnoozeHelper snoozeHelper2 = NotificationManagerService.this.mSnoozeHelper;
            int size = arrayList.size();
            synchronized (snoozeHelper2.mLock) {
                if (snoozeHelper2.mSnoozedNotifications.size() + size <= 500 && snoozeHelper2.mPersistedSnoozedNotifications.size() + snoozeHelper2.mPersistedSnoozedNotificationsWithContext.size() + size <= 500) {
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        NotificationRecord notificationRecord3 = (NotificationRecord) arrayList.get(i4);
                        MetricsLogger.action(notificationRecord3.getLogMaker().setCategory(FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION).setType(2).addTaggedData(1139, Long.valueOf(this.mDuration)).addTaggedData(FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_APPLIED, Integer.valueOf(this.mSnoozeCriterionId == null ? 0 : 1)));
                        ((NotificationRecordLoggerImpl) NotificationManagerService.this.mNotificationRecordLogger).log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SNOOZED, notificationRecord3);
                        NotificationManagerService.this.reportUserInteraction(notificationRecord3);
                        NotificationManagerService.this.cancelNotificationLocked(notificationRecord3, false, 18, -1, -1, NotificationManagerService.this.removeFromNotificationListsLocked(notificationRecord3), null, SystemClock.elapsedRealtime());
                        NotificationManagerService.this.mAttentionHelper.updateLightsLocked();
                        notificationRecord3.sbn.getNotification().semFlags |= 128;
                        if (!notificationRecord3.sbn.getNotification().isGroupSummary() || !"ranker_group".equals(notificationRecord3.sbn.getNotification().getGroup())) {
                            String str = this.mSnoozeCriterionId;
                            if (str != null) {
                                NotificationAssistants notificationAssistants = NotificationManagerService.this.mAssistants;
                                notificationAssistants.getClass();
                                notificationAssistants.notifyAssistantLocked(notificationRecord3.sbn, notificationRecord3.getNotificationType(), new NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0(notificationAssistants, str, 1));
                                SnoozeHelper snoozeHelper3 = NotificationManagerService.this.mSnoozeHelper;
                                String str2 = this.mSnoozeCriterionId;
                                if (str2 != null) {
                                    synchronized (snoozeHelper3.mLock) {
                                        snoozeHelper3.mPersistedSnoozedNotificationsWithContext.put(SnoozeHelper.getTrimmedString(notificationRecord3.sbn.getKey()), SnoozeHelper.getTrimmedString(str2));
                                    }
                                }
                                snoozeHelper3.snooze(notificationRecord3);
                            } else {
                                SnoozeHelper snoozeHelper4 = NotificationManagerService.this.mSnoozeHelper;
                                long j = this.mDuration;
                                snoozeHelper4.getClass();
                                String key = notificationRecord3.sbn.getKey();
                                snoozeHelper4.snooze(notificationRecord3);
                                new SnoozeHelper$$ExternalSyntheticLambda0(snoozeHelper4, key, System.currentTimeMillis() + j).run();
                                Long valueOf = Long.valueOf(System.currentTimeMillis() + j);
                                synchronized (snoozeHelper4.mLock) {
                                    snoozeHelper4.mPersistedSnoozedNotifications.put(SnoozeHelper.getTrimmedString(key), valueOf);
                                }
                            }
                            notificationRecord3.mStats.setSnoozed();
                            NotificationManagerService.this.handleSavePolicyFile();
                        }
                    }
                    return;
                }
                Log.w("NotificationService", "Cannot snooze " + notificationRecord.sbn.getKey() + ": too many snoozed notifications");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {
        }

        public final int onPullAtom(int i, List list) {
            ZenModeConfig zenModeConfig;
            Set<Pair> set;
            int i2;
            boolean z;
            int i3;
            if (i != 10084) {
                switch (i) {
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                        break;
                    default:
                        throw new UnsupportedOperationException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId="));
                }
            }
            NotificationManagerService notificationManagerService = NotificationManagerService.this;
            if (i != 10084) {
                switch (i) {
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                        PreferencesHelper preferencesHelper = notificationManagerService.mPreferencesHelper;
                        ArrayMap allUsersNotificationPermissions = notificationManagerService.getAllUsersNotificationPermissions();
                        if (allUsersNotificationPermissions != null) {
                            preferencesHelper.getClass();
                            set = allUsersNotificationPermissions.keySet();
                        } else {
                            set = null;
                        }
                        synchronized (preferencesHelper.mLock) {
                            int i4 = 0;
                            i2 = 0;
                            while (true) {
                                try {
                                    int i5 = 3;
                                    if (i4 < preferencesHelper.mPackagePreferences.size() && i2 <= 1000) {
                                        i2++;
                                        PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i4);
                                        Pair pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
                                        if (allUsersNotificationPermissions == null || !set.contains(pair)) {
                                            z = false;
                                            i3 = -1000;
                                        } else {
                                            Pair pair2 = (Pair) allUsersNotificationPermissions.get(pair);
                                            if (!((Boolean) pair2.first).booleanValue()) {
                                                i5 = 0;
                                            }
                                            boolean booleanValue = ((Boolean) pair2.second).booleanValue();
                                            set.remove(pair);
                                            i3 = i5;
                                            z = booleanValue;
                                        }
                                        int fsiState = preferencesHelper.getFsiState(packagePreferences.pkg, packagePreferences.uid, preferencesHelper.mPermissionHelper.hasRequestedPermission(packagePreferences.uid, packagePreferences.pkg));
                                        list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, packagePreferences.uid, i3, packagePreferences.visibility, packagePreferences.lockedAppFields, z, fsiState, preferencesHelper.isFsiPermissionUserSet(packagePreferences.pkg, packagePreferences.uid, fsiState, preferencesHelper.mPm.getPermissionFlags("android.permission.USE_FULL_SCREEN_INTENT", packagePreferences.pkg, UserHandle.getUserHandleForUid(packagePreferences.uid)))));
                                        i4++;
                                    }
                                } finally {
                                }
                            }
                        }
                        if (allUsersNotificationPermissions != null) {
                            for (Pair pair3 : set) {
                                if (i2 > 1000) {
                                    break;
                                } else {
                                    i2++;
                                    list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, ((Integer) pair3.first).intValue(), ((Boolean) ((Pair) allUsersNotificationPermissions.get(pair3)).first).booleanValue() ? 3 : 0, -1000, 0, ((Boolean) ((Pair) allUsersNotificationPermissions.get(pair3)).second).booleanValue(), 0, false));
                                }
                            }
                            break;
                        }
                        break;
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                        PreferencesHelper preferencesHelper2 = notificationManagerService.mPreferencesHelper;
                        synchronized (preferencesHelper2.mLock) {
                            int i6 = 0;
                            for (int i7 = 0; i7 < preferencesHelper2.mPackagePreferences.size() && i6 <= 2000; i7++) {
                                try {
                                    PreferencesHelper.PackagePreferences packagePreferences2 = (PreferencesHelper.PackagePreferences) preferencesHelper2.mPackagePreferences.valueAt(i7);
                                    for (NotificationChannel notificationChannel : packagePreferences2.channels.values()) {
                                        i6++;
                                        if (i6 > 2000) {
                                            break;
                                        }
                                        list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES, packagePreferences2.uid, notificationChannel.getId(), notificationChannel.getName().toString(), notificationChannel.getDescription(), notificationChannel.getImportance(), notificationChannel.getUserLockedFields(), notificationChannel.isDeleted(), notificationChannel.getConversationId() != null, notificationChannel.isDemoted(), notificationChannel.isImportantConversation()));
                                    }
                                } finally {
                                }
                            }
                        }
                        break;
                    case FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                        PreferencesHelper preferencesHelper3 = notificationManagerService.mPreferencesHelper;
                        synchronized (preferencesHelper3.mLock) {
                            int i8 = 0;
                            for (int i9 = 0; i9 < preferencesHelper3.mPackagePreferences.size() && i8 <= 1000; i9++) {
                                try {
                                    PreferencesHelper.PackagePreferences packagePreferences3 = (PreferencesHelper.PackagePreferences) preferencesHelper3.mPackagePreferences.valueAt(i9);
                                    for (NotificationChannelGroup notificationChannelGroup : packagePreferences3.groups.values()) {
                                        i8++;
                                        if (i8 > 1000) {
                                            break;
                                        }
                                        list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES, packagePreferences3.uid, notificationChannelGroup.getId(), notificationChannelGroup.getName().toString(), notificationChannelGroup.getDescription(), notificationChannelGroup.isBlocked(), notificationChannelGroup.getUserLockedFields()));
                                    }
                                } finally {
                                }
                            }
                        }
                        break;
                    default:
                        notificationManagerService.getClass();
                        break;
                }
            } else {
                ZenModeHelper zenModeHelper = notificationManagerService.mZenModeHelper;
                synchronized (zenModeHelper.mConfigsArrayLock) {
                    try {
                        int size = zenModeHelper.mConfigs.size();
                        for (int i10 = 0; i10 < size; i10++) {
                            int keyAt = zenModeHelper.mConfigs.keyAt(i10);
                            ZenModeConfig zenModeConfig2 = (ZenModeConfig) zenModeHelper.mConfigs.valueAt(i10);
                            list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.DND_MODE_RULE, keyAt, zenModeConfig2.isManualActive(), zenModeConfig2.areChannelsBypassingDnd, -1, "", 1000, zenModeConfig2.getZenPolicy().toProto(), 0, 0, 0, -1));
                            if (zenModeConfig2.isManualActive()) {
                                zenModeConfig = zenModeConfig2;
                                zenModeHelper.ruleToProtoLocked(keyAt, zenModeConfig.manualRule, true, list);
                            } else {
                                zenModeConfig = zenModeConfig2;
                            }
                            Iterator it = zenModeConfig.automaticRules.values().iterator();
                            while (it.hasNext()) {
                                zenModeHelper.ruleToProtoLocked(keyAt, (ZenModeConfig.ZenRule) it.next(), false, list);
                            }
                        }
                    } finally {
                    }
                }
            }
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatusBarNotificationHolder extends IStatusBarNotificationHolder.Stub {
        public StatusBarNotification mValue;

        public StatusBarNotificationHolder(StatusBarNotification statusBarNotification) {
            this.mValue = statusBarNotification;
        }

        public final StatusBarNotification get() {
            StatusBarNotification statusBarNotification = this.mValue;
            this.mValue = null;
            return statusBarNotification;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public final SparseBooleanArray mUserInLockDownMode;

        public StrongAuthTracker(Context context) {
            super(context);
            this.mUserInLockDownMode = new SparseBooleanArray();
        }

        public final synchronized void onStrongAuthRequiredChanged(int i) {
            try {
                boolean z = (getStrongAuthForUser(i) & 32) != 0;
                if (z == this.mUserInLockDownMode.get(i, false)) {
                    return;
                }
                if (z) {
                    NotificationManagerService.m698$$Nest$mcancelNotificationsWhenEnterLockDownMode(NotificationManagerService.this, i);
                }
                this.mUserInLockDownMode.put(i, z);
                if (!z) {
                    NotificationManagerService.m717$$Nest$mpostNotificationsWhenExitLockDownMode(NotificationManagerService.this, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrimCache {
        public final StatusBarNotification heavy;
        public StatusBarNotification sbnClone;
        public StatusBarNotification sbnCloneLight;

        public TrimCache(StatusBarNotification statusBarNotification) {
            this.heavy = statusBarNotification;
        }

        public final StatusBarNotification ForListener(ManagedServices.ManagedServiceInfo managedServiceInfo) {
            if (NotificationManagerService.this.mListeners.mLightTrimListeners.contains(managedServiceInfo)) {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(8:110|(4:111|112|b2|128)|(3:141|142|(1:144)(2:145|(8:147|148|149|150|131|132|(3:134|135|136)(1:138)|137)))|130|131|132|(0)(0)|137) */
        /* JADX WARN: Removed duplicated region for block: B:134:0x0151 A[Catch: NameNotFoundException -> 0x015b, TRY_LEAVE, TryCatch #9 {NameNotFoundException -> 0x015b, blocks: (B:132:0x0145, B:134:0x0151), top: B:131:0x0145 }] */
        /* JADX WARN: Removed duplicated region for block: B:138:0x015b A[SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 600
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.WorkerHandler.handleMessage(android.os.Message):void");
        }

        public final void scheduleCancelNotification(CancelNotificationRunnable cancelNotificationRunnable, int i) {
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                sendMessageDelayed(Message.obtain(this, cancelNotificationRunnable), i);
                return;
            }
            Flags.notificationReduceMessagequeueUsage();
            if (hasCallbacks(cancelNotificationRunnable)) {
                return;
            }
            sendMessage(Message.obtain(this, cancelNotificationRunnable));
        }

        public final void scheduleSendRankingUpdate() {
            Flags.notificationReduceMessagequeueUsage();
            if (hasMessages(4)) {
                return;
            }
            sendMessage(Message.obtain(this, 4));
        }
    }

    /* renamed from: -$$Nest$mapplyAdjustmentLocked, reason: not valid java name */
    public static void m695$$Nest$mapplyAdjustmentLocked(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, Adjustment adjustment, boolean z) {
        boolean contains;
        notificationManagerService.getClass();
        if (adjustment.getSignals() != null) {
            Bundle signals = adjustment.getSignals();
            Bundle.setDefusable(signals, true);
            ArrayList arrayList = new ArrayList();
            for (String str : signals.keySet()) {
                NotificationAssistants notificationAssistants = notificationManagerService.mAssistants;
                synchronized (notificationAssistants.mLock) {
                    contains = ((ArraySet) notificationAssistants.mAllowedAdjustments).contains(str);
                }
                if (!contains) {
                    arrayList.add(str);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                signals.remove((String) it.next());
            }
            synchronized (notificationRecord.mAdjustments) {
                ((ArrayList) notificationRecord.mAdjustments).add(adjustment);
            }
            if (adjustment.getSignals().containsKey("key_sensitive_content")) {
                FrameworkStatsLog.write(FrameworkStatsLog.SENSITIVE_NOTIFICATION_REDACTION, z, adjustment.getSignals().getBoolean("key_sensitive_content"), (int) (System.currentTimeMillis() - notificationRecord.mCreationTimeMs));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0045, code lost:
    
        if (notificationMatchesUserId(r25, r1, false) == false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
    
        if (r18.mUserProfiles.isCurrentProfile(r1.sbn.getUserId()) != false) goto L16;
     */
    /* renamed from: -$$Nest$mcancelAllNotificationsByListLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m696$$Nest$mcancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService r18, java.util.ArrayList r19, java.lang.String r20, boolean r21, java.lang.String r22, com.android.server.notification.NotificationManagerService.FlagChecker r23, boolean r24, int r25, boolean r26, int r27, java.lang.String r28, boolean r29, long r30) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.m696$$Nest$mcancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService, java.util.ArrayList, java.lang.String, boolean, java.lang.String, com.android.server.notification.NotificationManagerService$FlagChecker, boolean, int, boolean, int, java.lang.String, boolean, long):void");
    }

    /* renamed from: -$$Nest$mcancelGroupSummaryLocked, reason: not valid java name */
    public static void m697$$Nest$mcancelGroupSummaryLocked(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, String str, boolean z, long j) {
        notificationManagerService.getClass();
        if (notificationRecord.sbn.getNotification().isGroupChild()) {
            String packageName = notificationRecord.sbn.getPackageName();
            if (packageName == null) {
                if (DBG) {
                    Slog.e("NotificationService", "No package for group summary: " + notificationRecord.sbn.getKey());
                    return;
                }
                return;
            }
            ArrayList arrayList = (ArrayList) notificationManagerService.findGroupNotificationsLocked(notificationRecord.sbn.getUserId(), packageName, notificationRecord.sbn.getGroupKey());
            if (arrayList.size() == 1) {
                NotificationRecord notificationRecord2 = (NotificationRecord) arrayList.get(0);
                if (notificationRecord2.sbn.getNotification().isGroupSummary() && notificationRecord2.sbn.getGroupKey().equals(notificationRecord.sbn.getGroupKey())) {
                    notificationManagerService.cancelNotificationLocked(notificationRecord2, z, 24, -1, -1, notificationManagerService.removeFromNotificationListsLocked(notificationRecord2), str, j);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcancelNotificationsWhenEnterLockDownMode, reason: not valid java name */
    public static void m698$$Nest$mcancelNotificationsWhenEnterLockDownMode(NotificationManagerService notificationManagerService, int i) {
        synchronized (notificationManagerService.mNotificationLock) {
            try {
                int size = notificationManagerService.mNotificationList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationList.get(i2);
                    if (notificationRecord.sbn.getUser().getIdentifier() == i) {
                        notificationManagerService.mListeners.notifyRemovedLocked(notificationRecord, 23, notificationRecord.mStats);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mcheckCallerIsSystemOrSameApp, reason: not valid java name */
    public static void m699$$Nest$mcheckCallerIsSystemOrSameApp(NotificationManagerService notificationManagerService, String str) {
        notificationManagerService.getClass();
        if (isCallerSystemOrPhone()) {
            return;
        }
        notificationManagerService.checkCallerIsSameApp(str);
    }

    /* renamed from: -$$Nest$mcheckCallerIsSystemUI, reason: not valid java name */
    public static boolean m700$$Nest$mcheckCallerIsSystemUI(NotificationManagerService notificationManagerService) {
        notificationManagerService.getClass();
        try {
            String nameForUid = notificationManagerService.getContext().getPackageManager().getNameForUid(Binder.getCallingUid());
            Slog.d("NotificationService", "checkCallerIsSystemUI() caller " + nameForUid);
            int lastIndexOf = nameForUid.lastIndexOf(":");
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            Slog.d("NotificationService", "isCallerSystemUI caller " + nameForUid);
            if (nameForUid != null) {
                return nameForUid.equals("android.uid.systemui");
            }
            return false;
        } catch (Exception e) {
            Slog.d("NotificationService", "The exception occurs " + e.getMessage());
            return false;
        }
    }

    /* renamed from: -$$Nest$mdumpJson, reason: not valid java name */
    public static void m701$$Nest$mdumpJson(NotificationManagerService notificationManagerService, PrintWriter printWriter, DumpFilter dumpFilter, ArrayMap arrayMap) {
        notificationManagerService.getClass();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("service", "Notification Manager");
            notificationManagerService.mPreferencesHelper.getClass();
            jSONObject.put("bans", PreferencesHelper.dumpBansJson(dumpFilter, arrayMap));
            jSONObject.put("ranking", notificationManagerService.mPreferencesHelper.dumpJson(dumpFilter, arrayMap));
            jSONObject.put("stats", notificationManagerService.mUsageStats.dumpJson(dumpFilter));
            jSONObject.put("channels", notificationManagerService.mPreferencesHelper.dumpChannelsJson(dumpFilter));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        printWriter.println(jSONObject);
    }

    /* renamed from: -$$Nest$mdumpProto, reason: not valid java name */
    public static void m702$$Nest$mdumpProto(NotificationManagerService notificationManagerService, FileDescriptor fileDescriptor, DumpFilter dumpFilter, ArrayMap arrayMap) {
        notificationManagerService.getClass();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (notificationManagerService.mNotificationLock) {
            try {
                int size = notificationManagerService.mNotificationList.size();
                for (int i = 0; i < size; i++) {
                    NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationList.get(i);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.sbn)) {
                        notificationRecord.dump(1, protoOutputStream);
                    }
                }
                int size2 = notificationManagerService.mEnqueuedNotifications.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NotificationRecord notificationRecord2 = (NotificationRecord) notificationManagerService.mEnqueuedNotifications.get(i2);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord2.sbn)) {
                        notificationRecord2.dump(0, protoOutputStream);
                    }
                }
                ArrayList arrayList = (ArrayList) notificationManagerService.mSnoozeHelper.getSnoozed();
                int size3 = arrayList.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    NotificationRecord notificationRecord3 = (NotificationRecord) arrayList.get(i3);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord3.sbn)) {
                        notificationRecord3.dump(2, protoOutputStream);
                    }
                }
                long start = protoOutputStream.start(1146756268034L);
                notificationManagerService.mZenModeHelper.dump(protoOutputStream);
                Iterator it = ((ArrayList) notificationManagerService.mEffectsSuppressors).iterator();
                while (it.hasNext()) {
                    ((ComponentName) it.next()).dumpDebug(protoOutputStream, 2246267895812L);
                }
                protoOutputStream.end(start);
                long start2 = protoOutputStream.start(1146756268035L);
                notificationManagerService.mListeners.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start2);
                protoOutputStream.write(1120986464260L, notificationManagerService.mListenerHints);
                for (int i4 = 0; i4 < notificationManagerService.mListenersDisablingEffects.size(); i4++) {
                    long start3 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, notificationManagerService.mListenersDisablingEffects.keyAt(i4));
                    ArraySet arraySet = (ArraySet) notificationManagerService.mListenersDisablingEffects.valueAt(i4);
                    for (int i5 = 0; i5 < arraySet.size(); i5++) {
                        ((ComponentName) arraySet.valueAt(i5)).dumpDebug(protoOutputStream, 2246267895811L);
                    }
                    protoOutputStream.end(start3);
                }
                long start4 = protoOutputStream.start(1146756268038L);
                notificationManagerService.mAssistants.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start4);
                long start5 = protoOutputStream.start(1146756268039L);
                notificationManagerService.mConditionProviders.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start5);
                long start6 = protoOutputStream.start(1146756268040L);
                for (NotificationSignalExtractor notificationSignalExtractor : notificationManagerService.mRankingHelper.mSignalExtractors) {
                    protoOutputStream.write(2237677961217L, notificationSignalExtractor.getClass().getSimpleName());
                }
                PreferencesHelper preferencesHelper = notificationManagerService.mPreferencesHelper;
                synchronized (preferencesHelper.mLock) {
                    PreferencesHelper.dumpPackagePreferencesLocked(protoOutputStream, 2246267895810L, dumpFilter, preferencesHelper.mPackagePreferences, arrayMap);
                    PreferencesHelper.dumpPackagePreferencesLocked(protoOutputStream, 2246267895811L, dumpFilter, preferencesHelper.mRestoredWithoutUids, null);
                }
                protoOutputStream.end(start6);
            } catch (Throwable th) {
                throw th;
            }
        }
        protoOutputStream.flush();
    }

    /* renamed from: -$$Nest$mdumpRemoteViewStats, reason: not valid java name */
    public static void m703$$Nest$mdumpRemoteViewStats(NotificationManagerService notificationManagerService, PrintWriter printWriter, DumpFilter dumpFilter) {
        PulledStats remoteViewStats = notificationManagerService.mUsageStats.remoteViewStats(dumpFilter.since);
        printWriter.print("  Packages with undecordated notifications (");
        printWriter.print(remoteViewStats.mTimePeriodStartMs);
        printWriter.print(" - ");
        printWriter.print(remoteViewStats.mTimePeriodEndMs);
        printWriter.println("):");
        if (((ArrayList) remoteViewStats.mUndecoratedPackageNames).size() == 0) {
            printWriter.println("    none");
            return;
        }
        Iterator it = ((ArrayList) remoteViewStats.mUndecoratedPackageNames).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!dumpFilter.filtered || str.equals(dumpFilter.pkgFilter)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "    ", str);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleDurationReached, reason: not valid java name */
    public static void m704$$Nest$mhandleDurationReached(NotificationManagerService notificationManagerService, ToastRecord toastRecord) {
        if (DBG) {
            notificationManagerService.getClass();
            Slog.d("NotificationService", "Timeout pkg=" + toastRecord.pkg + " token=" + toastRecord.token);
        }
        synchronized (notificationManagerService.mToastQueue) {
            try {
                int indexOfToastLocked = notificationManagerService.indexOfToastLocked(toastRecord.token, toastRecord.pkg);
                if (indexOfToastLocked >= 0) {
                    notificationManagerService.cancelToastLocked(indexOfToastLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleGroupedNotificationLocked, reason: not valid java name */
    public static void m705$$Nest$mhandleGroupedNotificationLocked(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, NotificationRecord notificationRecord2, int i, int i2) {
        NotificationRecord notificationRecord3;
        notificationManagerService.getClass();
        StatusBarNotification statusBarNotification = notificationRecord.sbn;
        Notification notification = statusBarNotification.getNotification();
        if (notification.isGroupSummary() && !statusBarNotification.isAppGroup()) {
            notification.flags &= -513;
        }
        String groupKey = statusBarNotification.getGroupKey();
        boolean isGroupSummary = notification.isGroupSummary();
        Notification notification2 = notificationRecord2 != null ? notificationRecord2.sbn.getNotification() : null;
        String groupKey2 = notificationRecord2 != null ? notificationRecord2.sbn.getGroupKey() : null;
        boolean z = notificationRecord2 != null && notification2.isGroupSummary();
        if (z && (notificationRecord3 = (NotificationRecord) notificationManagerService.mSummaryByGroupKey.remove(groupKey2)) != notificationRecord2) {
            Slog.w("NotificationService", "Removed summary didn't match old notification: old=" + notificationRecord2.sbn.getKey() + ", removed=" + (notificationRecord3 != null ? notificationRecord3.sbn.getKey() : "<null>"));
        }
        if (isGroupSummary) {
            notificationManagerService.mSummaryByGroupKey.put(groupKey, notificationRecord);
        }
        NotificationManagerService$$ExternalSyntheticLambda13 notificationManagerService$$ExternalSyntheticLambda13 = new NotificationManagerService$$ExternalSyntheticLambda13();
        if (z) {
            if (isGroupSummary && groupKey2.equals(groupKey)) {
                return;
            }
            notificationManagerService.cancelGroupChildrenLocked(notificationRecord2, i, i2, null, false, notificationManagerService$$ExternalSyntheticLambda13, 8, SystemClock.elapsedRealtime());
        }
    }

    /* renamed from: -$$Nest$mhandleKillTokenTimeout, reason: not valid java name */
    public static void m706$$Nest$mhandleKillTokenTimeout(NotificationManagerService notificationManagerService, ToastRecord toastRecord) {
        if (DBG) {
            notificationManagerService.getClass();
            Slog.d("NotificationService", "Kill Token Timeout token=" + toastRecord.windowToken);
        }
        synchronized (notificationManagerService.mToastQueue) {
            Binder binder = toastRecord.windowToken;
            int i = toastRecord.displayId;
            notificationManagerService.mHandler.removeCallbacksAndMessages(binder);
            notificationManagerService.mWindowManagerInternal.removeWindowToken(binder, true, i);
        }
    }

    /* renamed from: -$$Nest$mhandleListenerHintsChanged, reason: not valid java name */
    public static void m707$$Nest$mhandleListenerHintsChanged(NotificationManagerService notificationManagerService, int i) {
        synchronized (notificationManagerService.mNotificationLock) {
            NotificationListeners notificationListeners = notificationManagerService.mListeners;
            Iterator it = ((ArrayList) notificationListeners.getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                if (managedServiceInfo.isEnabledForCurrentProfiles()) {
                    int currentUser = ActivityManager.getCurrentUser();
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    if (notificationManagerService2.isInteractionVisibleToListener(managedServiceInfo, currentUser)) {
                        notificationManagerService2.mHandler.post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9(notificationListeners, managedServiceInfo, i, 0));
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleListenerInterruptionFilterChanged, reason: not valid java name */
    public static void m708$$Nest$mhandleListenerInterruptionFilterChanged(NotificationManagerService notificationManagerService, int i) {
        synchronized (notificationManagerService.mNotificationLock) {
            NotificationListeners notificationListeners = notificationManagerService.mListeners;
            Iterator it = ((ArrayList) notificationListeners.getServices()).iterator();
            while (it.hasNext()) {
                ManagedServices.ManagedServiceInfo managedServiceInfo = (ManagedServices.ManagedServiceInfo) it.next();
                if (managedServiceInfo.isEnabledForCurrentProfiles()) {
                    int currentUser = ActivityManager.getCurrentUser();
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    if (notificationManagerService2.isInteractionVisibleToListener(managedServiceInfo, currentUser)) {
                        notificationManagerService2.mHandler.post(new NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9(notificationListeners, managedServiceInfo, i, 1));
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleSendRankingUpdate, reason: not valid java name */
    public static void m709$$Nest$mhandleSendRankingUpdate(NotificationManagerService notificationManagerService) {
        synchronized (notificationManagerService.mNotificationLock) {
            notificationManagerService.mListeners.notifyRankingUpdateLocked(null);
        }
    }

    /* renamed from: -$$Nest$mhasFollowedNotification, reason: not valid java name */
    public static boolean m710$$Nest$mhasFollowedNotification(NotificationManagerService notificationManagerService, String str) {
        for (String str2 : notificationManagerService.mNotificationsByKey.keySet()) {
            if (str2 != null && str2.contains(str) && ((NotificationRecord) notificationManagerService.mNotificationsByKey.get(str2)).mImportance >= 4) {
                if (((int) (System.currentTimeMillis() - ((NotificationRecord) notificationManagerService.mNotificationsByKey.get(str2)).mCreationTimeMs)) < 500 && ((NotificationRecord) notificationManagerService.mNotificationsByKey.get(str2)).sbn.getNotification().fullScreenIntent == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$misCallerSystemOrSystemUiOrShell, reason: not valid java name */
    public static boolean m711$$Nest$misCallerSystemOrSystemUiOrShell(NotificationManagerService notificationManagerService) {
        notificationManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return true;
        }
        return notificationManagerService.isCallerSystemOrSystemUi();
    }

    /* renamed from: -$$Nest$misNeedDeletePrevHistory, reason: not valid java name */
    public static boolean m712$$Nest$misNeedDeletePrevHistory(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
        notificationManagerService.getClass();
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(notificationRecord.sbn.getPackageContext(notificationManagerService.getContext()), notificationRecord.sbn.getNotification());
        Notification.Builder recoverBuilder2 = Notification.Builder.recoverBuilder(notificationRecord2.sbn.getPackageContext(notificationManagerService.getContext()), notificationRecord2.sbn.getNotification());
        if (!(recoverBuilder.getStyle() instanceof Notification.MessagingStyle) || !(recoverBuilder2.getStyle() instanceof Notification.MessagingStyle)) {
            return false;
        }
        Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) recoverBuilder.getStyle();
        Notification.MessagingStyle messagingStyle2 = (Notification.MessagingStyle) recoverBuilder2.getStyle();
        List<Notification.MessagingStyle.Message> messages = messagingStyle.getMessages();
        List<Notification.MessagingStyle.Message> messages2 = messagingStyle2.getMessages();
        if (messages == null || messages.size() <= 0 || messages2 == null || messages2.size() <= 0) {
            return false;
        }
        Notification.MessagingStyle.Message message = messages.get(messages.size() - 1);
        Notification.MessagingStyle.Message message2 = messages2.get(messages2.size() - 1);
        if ((message.getDataUri() == null && message.getTimestamp() == message2.getTimestamp()) || message2.getDataUri() == null) {
            return false;
        }
        Slog.d("NotificationService", "isNeedDeletePrevHistory  newMs.getDataUri() = " + message2.getDataUri() + ", oldMs.getDataUri() = " + message.getDataUri() + ", newMs.getTimestamp() = " + message2.getTimestamp() + ", oldMs.getTimestamp() = " + message.getTimestamp());
        return !message2.getDataUri().equals(message.getDataUri()) && message.getTimestamp() == message2.getTimestamp();
    }

    /* renamed from: -$$Nest$misNeedSaveHistory, reason: not valid java name */
    public static boolean m713$$Nest$misNeedSaveHistory(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, NotificationRecord notificationRecord2, boolean z) {
        notificationManagerService.getClass();
        if (notificationRecord2.sbn.isGroup() && notificationRecord2.sbn.getNotification().isGroupSummary()) {
            return false;
        }
        if (notificationRecord != null) {
            Notification notification = notificationRecord.sbn.getNotification();
            Notification notification2 = notificationRecord2.sbn.getNotification();
            if (notification.extras == null || notification2.extras == null || (notificationRecord2.sbn.getNotification().flags & 64) != 0) {
                return false;
            }
            if (!z && (notification2.flags & 8) != 0) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: -$$Nest$mmakeNotiListenerHistory, reason: not valid java name */
    public static void m714$$Nest$mmakeNotiListenerHistory(NotificationManagerService notificationManagerService, String str, boolean z) {
        ArrayList arrayList = notificationManagerService.mNotiListenerHistoryList;
        StringBuilder sb = new StringBuilder();
        sb.append(makeTime());
        sb.append(" From = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, notificationManagerService.mNotificationListenerFrom, ", pkg = ", str, ", enabled= ");
        sb.append(z);
        arrayList.add(sb.toString());
        notificationManagerService.mNotificationListenerFrom = "";
        while (notificationManagerService.mNotiListenerHistoryList.size() > 100) {
            notificationManagerService.mNotiListenerHistoryList.remove(0);
        }
    }

    /* renamed from: -$$Nest$mmaybeNotifySystemUiListenerLifetimeExtendedListLocked, reason: not valid java name */
    public static void m715$$Nest$mmaybeNotifySystemUiListenerLifetimeExtendedListLocked(NotificationManagerService notificationManagerService, List list, int i) {
        notificationManagerService.getClass();
        for (int size = list.size() - 1; size >= 0; size--) {
            NotificationRecord notificationRecord = (NotificationRecord) list.get(size);
            notificationManagerService.maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord, notificationRecord.sbn.getPackageName(), i);
        }
    }

    /* renamed from: -$$Nest$mnotifyListenersPostedAndLogLocked, reason: not valid java name */
    public static void m716$$Nest$mnotifyListenersPostedAndLogLocked(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord, NotificationRecord notificationRecord2, PostNotificationTracker postNotificationTracker, NotificationRecordLogger.NotificationReported notificationReported) {
        notificationManagerService.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda15(notificationManagerService, notificationManagerService.mListeners.prepareNotifyPostedLocked(notificationRecord, notificationRecord2, true), postNotificationTracker, notificationReported, 0));
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.callstyleCallbackApi() && notificationRecord.sbn.getNotification().isStyle(Notification.CallStyle.class)) {
            synchronized (notificationManagerService.mCallNotificationEventCallbacks) {
                try {
                    ArrayMap arrayMap = (ArrayMap) notificationManagerService.mCallNotificationEventCallbacks.get(notificationRecord.sbn.getPackageName());
                    if (arrayMap == null) {
                        return;
                    }
                    if (notificationRecord.sbn.getUser().equals(UserHandle.ALL)) {
                        Iterator it = arrayMap.values().iterator();
                        while (it.hasNext()) {
                            broadcastToCallNotificationEventCallbacks((RemoteCallbackList) it.next(), notificationRecord, true);
                        }
                    } else {
                        broadcastToCallNotificationEventCallbacks((RemoteCallbackList) arrayMap.get(Integer.valueOf(notificationRecord.sbn.getUser().getIdentifier())), notificationRecord, true);
                        broadcastToCallNotificationEventCallbacks((RemoteCallbackList) arrayMap.get(-1), notificationRecord, true);
                    }
                } finally {
                }
            }
        }
    }

    /* renamed from: -$$Nest$mpostNotificationsWhenExitLockDownMode, reason: not valid java name */
    public static void m717$$Nest$mpostNotificationsWhenExitLockDownMode(NotificationManagerService notificationManagerService, int i) {
        synchronized (notificationManagerService.mNotificationLock) {
            try {
                int size = notificationManagerService.mNotificationList.size();
                long j = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationList.get(i2);
                    if (notificationRecord.sbn.getUser().getIdentifier() == i) {
                        notificationManagerService.mHandler.postDelayed(new NotificationManagerService$$ExternalSyntheticLambda7(1, notificationManagerService, notificationRecord), j);
                        j += 20;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mpostedNotificationLog, reason: not valid java name */
    public static void m718$$Nest$mpostedNotificationLog(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord) {
        synchronized (notificationManagerService) {
            if (notificationManagerService.canSendLoggingData(notificationRecord)) {
                notificationManagerService.getOrCreateNotificationLogLocked(notificationRecord);
            }
        }
    }

    /* renamed from: -$$Nest$mreceiveFollowedNotification, reason: not valid java name */
    public static void m719$$Nest$mreceiveFollowedNotification(NotificationManagerService notificationManagerService, String str) {
        ConcurrentList concurrentList = notificationManagerService.mDelayedWakelockList;
        Iterator it = concurrentList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2.contains(str)) {
                Slog.d("NotificationService", "     WAKELOCK received by notification after Delayed Wakelock : ".concat(str2));
                notificationManagerService.mHandler.removeCallbacksAndMessages(str2);
                concurrentList.remove(str2);
            }
        }
        ConcurrentList concurrentList2 = notificationManagerService.mDelayedWakeUpList;
        Iterator it2 = concurrentList2.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            if (str3.contains(str)) {
                Slog.d("NotificationService", "     WAKEUP received by notification after Delayed Wakelock : ".concat(str3));
                notificationManagerService.mHandler.removeCallbacksAndMessages(str3);
                concurrentList2.remove(str3);
            }
        }
    }

    /* renamed from: -$$Nest$mremoveBitmapAndRepost, reason: not valid java name */
    public static void m720$$Nest$mremoveBitmapAndRepost(NotificationManagerService notificationManagerService, NotificationRecord notificationRecord) {
        notificationManagerService.getClass();
        Notification notification = notificationRecord.sbn.getNotification();
        if (notification.isStyle(Notification.BigPictureStyle.class)) {
            if ((!notification.extras.containsKey("android.picture") || notification.extras.getParcelable("android.picture") == null) && (!notification.extras.containsKey("android.pictureIcon") || notification.extras.getParcelable("android.pictureIcon") == null)) {
                return;
            }
            notificationRecord.sbn.getNotification().extras.putParcelable("android.picture", null);
            notificationRecord.sbn.getNotification().extras.putParcelable("android.pictureIcon", null);
            notificationRecord.sbn.getNotification().flags |= 8;
            notificationManagerService.enqueueNotificationInternal(notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getOpPkg(), notificationRecord.sbn.getUid(), notificationRecord.sbn.getInitialPid(), notificationRecord.sbn.getTag(), notificationRecord.sbn.getId(), notificationRecord.sbn.getNotification(), notificationRecord.sbn.getUserId(), true, false);
        }
    }

    /* renamed from: -$$Nest$mupdateEffectsSuppressorLocked, reason: not valid java name */
    public static void m721$$Nest$mupdateEffectsSuppressorLocked(NotificationManagerService notificationManagerService) {
        int calculateHints = notificationManagerService.calculateHints();
        long j = (calculateHints & 1) != 0 ? 3L : 0L;
        if ((calculateHints & 2) != 0) {
            j |= 1;
        }
        if ((calculateHints & 4) != 0) {
            j |= 2;
        }
        if (j == notificationManagerService.mZenModeHelper.mSuppressedEffects) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int size = notificationManagerService.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            Iterator it = ((ArraySet) notificationManagerService.mListenersDisablingEffects.valueAt(size)).iterator();
            while (it.hasNext()) {
                arrayList.add((ComponentName) it.next());
            }
        }
        List list = notificationManagerService.mEffectsSuppressors;
        LocalLog localLog = ZenLog.STATE_CHANGES;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("suppressed effects:", j, ",");
        m.append(ZenLog.componentListToString(list));
        m.append("->");
        m.append(ZenLog.componentListToString(arrayList));
        ZenLog.append(14, m.toString());
        notificationManagerService.mEffectsSuppressors = arrayList;
        ZenModeHelper zenModeHelper = notificationManagerService.mZenModeHelper;
        if (zenModeHelper.mSuppressedEffects != j) {
            zenModeHelper.mSuppressedEffects = j;
            zenModeHelper.applyRestrictions();
        }
        notificationManagerService.sendRegisteredOnlyBroadcast(new Intent("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED"));
    }

    /* renamed from: -$$Nest$mupdateInterruptionFilterLocked, reason: not valid java name */
    public static void m722$$Nest$mupdateInterruptionFilterLocked(NotificationManagerService notificationManagerService) {
        int zenModeToInterruptionFilter = NotificationManager.zenModeToInterruptionFilter(notificationManagerService.mZenModeHelper.mZenMode);
        if (zenModeToInterruptionFilter == notificationManagerService.mInterruptionFilter) {
            return;
        }
        notificationManagerService.mInterruptionFilter = zenModeToInterruptionFilter;
        Flags.notificationReduceMessagequeueUsage();
        notificationManagerService.mHandler.removeMessages(6);
        notificationManagerService.mHandler.obtainMessage(6, zenModeToInterruptionFilter, 0).sendToTarget();
    }

    /* renamed from: -$$Nest$mupdateListenerHintsLocked, reason: not valid java name */
    public static void m723$$Nest$mupdateListenerHintsLocked(NotificationManagerService notificationManagerService) {
        int calculateHints = notificationManagerService.calculateHints();
        int i = notificationManagerService.mListenerHints;
        if (calculateHints == i) {
            return;
        }
        int size = ((ArrayList) notificationManagerService.mEffectsSuppressors).size();
        LocalLog localLog = ZenLog.STATE_CHANGES;
        StringBuilder sb = new StringBuilder();
        sb.append(i != 0 ? i != 1 ? i != 2 ? i != 4 ? Integer.toString(i) : "disable_call_effects" : "disable_notification_effects" : "disable_effects" : "none");
        sb.append("->");
        sb.append(calculateHints != 0 ? calculateHints != 1 ? calculateHints != 2 ? calculateHints != 4 ? Integer.toString(calculateHints) : "disable_call_effects" : "disable_notification_effects" : "disable_effects" : "none");
        sb.append(",listeners=");
        sb.append(size);
        ZenLog.append(15, sb.toString());
        notificationManagerService.mListenerHints = calculateHints;
        Flags.notificationReduceMessagequeueUsage();
        notificationManagerService.mHandler.removeMessages(5);
        notificationManagerService.mHandler.obtainMessage(5, calculateHints, 0).sendToTarget();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x00a2 -> B:18:0x00c1). Please report as a decompilation issue!!! */
    /* renamed from: -$$Nest$mwriteConversationAppPolicyJson, reason: not valid java name */
    public static void m724$$Nest$mwriteConversationAppPolicyJson(NotificationManagerService notificationManagerService, FileOutputStream fileOutputStream) {
        OutputStreamWriter outputStreamWriter;
        notificationManagerService.getClass();
        Slog.d("NotificationService", "writeConversationAppPolicyJson");
        JsonWriter jsonWriter = null;
        try {
            try {
                outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                try {
                    try {
                        JsonWriter jsonWriter2 = new JsonWriter(outputStreamWriter);
                        try {
                            jsonWriter2.setIndent(" ");
                            jsonWriter2.beginObject();
                            jsonWriter2.name("policy_version").value(notificationManagerService.mConversationAppPolicyVersion);
                            jsonWriter2.name("appList");
                            jsonWriter2.beginArray();
                            int size = notificationManagerService.mConversationAppList.size();
                            for (int i = 0; i < size; i++) {
                                jsonWriter2.value(notificationManagerService.mConversationAppList.get(i).toString());
                            }
                            jsonWriter2.endArray();
                            jsonWriter2.name("historyAppList");
                            jsonWriter2.beginArray();
                            int size2 = notificationManagerService.mConversationHistoryAppList.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                jsonWriter2.value(notificationManagerService.mConversationHistoryAppList.get(i2).toString());
                            }
                            jsonWriter2.endArray();
                            jsonWriter2.name("maxNotiLimitPolicy");
                            jsonWriter2.beginArray();
                            jsonWriter2.value(notificationManagerService.mIsMaxNotiLimitEnabled);
                            jsonWriter2.value(notificationManagerService.mMaxNotiLimitCount);
                            jsonWriter2.endArray();
                            jsonWriter2.endObject();
                            try {
                                jsonWriter2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            outputStreamWriter.close();
                        } catch (IOException e2) {
                            e = e2;
                            jsonWriter = jsonWriter2;
                            e.printStackTrace();
                            if (jsonWriter != null) {
                                try {
                                    jsonWriter.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (outputStreamWriter != null) {
                                outputStreamWriter.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            jsonWriter = jsonWriter2;
                            if (jsonWriter != null) {
                                try {
                                    jsonWriter.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (outputStreamWriter == null) {
                                throw th;
                            }
                            try {
                                outputStreamWriter.close();
                                throw th;
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e6) {
                    e = e6;
                }
            } catch (IOException e7) {
                e = e7;
                outputStreamWriter = null;
            } catch (Throwable th3) {
                th = th3;
                outputStreamWriter = null;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v17, types: [int] */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [android.util.JsonWriter] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.util.JsonWriter] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.OutputStreamWriter, java.io.Writer] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x008e -> B:19:0x00ad). Please report as a decompilation issue!!! */
    /* renamed from: -$$Nest$mwriteOngoingDismissExceptionPolicyJson, reason: not valid java name */
    public static void m725$$Nest$mwriteOngoingDismissExceptionPolicyJson(NotificationManagerService notificationManagerService, FileOutputStream fileOutputStream) {
        notificationManagerService.getClass();
        ?? r1 = "writeOngoingDismissExceptionPolicyJson";
        Slog.d("NotificationService", "writeOngoingDismissExceptionPolicyJson");
        ?? r0 = 0;
        r0 = 0;
        r0 = 0;
        r0 = 0;
        r0 = 0;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                r1 = new OutputStreamWriter(fileOutputStream, "UTF-8");
                try {
                    JsonWriter jsonWriter = new JsonWriter(r1);
                    try {
                        jsonWriter.setIndent(" ");
                        jsonWriter.beginObject();
                        jsonWriter.name("policy_version").value(notificationManagerService.mOngoingDismissExceptionPolicyVersion);
                        jsonWriter.name("keyList");
                        jsonWriter.beginArray();
                        int size = notificationManagerService.mOngoingDismissExceptionKeyList.size();
                        for (int i = 0; i < size; i++) {
                            jsonWriter.value(((String) notificationManagerService.mOngoingDismissExceptionKeyList.get(i)).toString());
                        }
                        jsonWriter.endArray();
                        jsonWriter.name("limitNotificationAppList");
                        jsonWriter.beginArray();
                        r0 = notificationManagerService.mLimitNotificationsForOverflowAppList.size();
                        for (int i2 = 0; i2 < r0; i2++) {
                            jsonWriter.value(((String) notificationManagerService.mLimitNotificationsForOverflowAppList.get(i2)).toString());
                        }
                        jsonWriter.endArray();
                        jsonWriter.endObject();
                        try {
                            jsonWriter.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        r1.close();
                    } catch (IOException e3) {
                        e = e3;
                        r0 = jsonWriter;
                        e.printStackTrace();
                        if (r0 != 0) {
                            try {
                                r0.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (r1 != 0) {
                            r1.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        r0 = jsonWriter;
                        if (r0 != 0) {
                            try {
                                r0.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (r1 == 0) {
                            throw th;
                        }
                        try {
                            r1.close();
                            throw th;
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            throw th;
                        }
                    }
                } catch (IOException e7) {
                    e = e7;
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

    /* renamed from: -$$Nest$mwritePolicyXml, reason: not valid java name */
    public static void m726$$Nest$mwritePolicyXml(NotificationManagerService notificationManagerService, OutputStream outputStream, boolean z, int i) {
        TypedXmlSerializer resolveSerializer;
        long j;
        notificationManagerService.getClass();
        if (z) {
            resolveSerializer = Xml.newFastSerializer();
            resolveSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        } else {
            resolveSerializer = Xml.resolveSerializer(outputStream);
        }
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, "notification-policy");
        resolveSerializer.attributeInt((String) null, "version", 1);
        ZenModeHelper zenModeHelper = notificationManagerService.mZenModeHelper;
        synchronized (zenModeHelper.mConfigsArrayLock) {
            try {
                int size = zenModeHelper.mConfigs.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (!z || zenModeHelper.mConfigs.keyAt(i2) == i) {
                        ((ZenModeConfig) zenModeHelper.mConfigs.valueAt(i2)).writeXml(resolveSerializer, (Integer) null, z);
                    }
                }
            } finally {
            }
        }
        PreferencesHelper preferencesHelper = notificationManagerService.mPreferencesHelper;
        preferencesHelper.getClass();
        resolveSerializer.startTag((String) null, "ranking");
        resolveSerializer.attributeInt((String) null, "version", preferencesHelper.XML_VERSION);
        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
            resolveSerializer.attributeInt((String) null, "hide_content_version", 1);
        }
        if (preferencesHelper.mHideSilentStatusBarIcons) {
            resolveSerializer.startTag((String) null, "silent_status_icons");
            resolveSerializer.attributeBoolean((String) null, "hide_gentle", preferencesHelper.mHideSilentStatusBarIcons);
            resolveSerializer.endTag((String) null, "silent_status_icons");
        }
        ArrayMap arrayMap = new ArrayMap();
        if (z) {
            arrayMap = preferencesHelper.mPermissionHelper.getNotificationPermissionValues(i);
        }
        synchronized (preferencesHelper.mLock) {
            try {
                int size2 = preferencesHelper.mPackagePreferences.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i3);
                    if (!z || UserHandle.getUserId(packagePreferences.uid) == i) {
                        preferencesHelper.writePackageXml(packagePreferences, resolveSerializer, arrayMap, z);
                    }
                }
                Flags.persistIncompleteRestoreData();
                if (!z) {
                    int size3 = preferencesHelper.mRestoredWithoutUids.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        preferencesHelper.writePackageXml((PreferencesHelper.PackagePreferences) preferencesHelper.mRestoredWithoutUids.valueAt(i4), resolveSerializer, arrayMap, false);
                    }
                }
            } finally {
            }
        }
        if (!arrayMap.isEmpty()) {
            for (Pair pair : arrayMap.keySet()) {
                resolveSerializer.startTag((String) null, "package");
                resolveSerializer.attribute((String) null, "name", (String) pair.second);
                resolveSerializer.attributeInt((String) null, "importance", ((Boolean) ((Pair) arrayMap.get(pair)).first).booleanValue() ? 3 : 0);
                resolveSerializer.endTag((String) null, "package");
            }
        }
        resolveSerializer.endTag((String) null, "ranking");
        notificationManagerService.mListeners.writeXml(i, resolveSerializer, z);
        notificationManagerService.mAssistants.writeXml(i, resolveSerializer, z);
        SnoozeHelper snoozeHelper = notificationManagerService.mSnoozeHelper;
        synchronized (snoozeHelper.mLock) {
            long currentTimeMillis = System.currentTimeMillis();
            resolveSerializer.startTag((String) null, "snoozed-notifications");
            ArrayMap arrayMap2 = snoozeHelper.mPersistedSnoozedNotifications;
            int i5 = 0;
            while (i5 < arrayMap2.size()) {
                String str = (String) arrayMap2.keyAt(i5);
                Object valueAt = arrayMap2.valueAt(i5);
                resolveSerializer.startTag((String) null, "notification");
                Long l = (Long) valueAt;
                if (l.longValue() < currentTimeMillis) {
                    j = currentTimeMillis;
                } else {
                    j = currentTimeMillis;
                    resolveSerializer.attributeLong((String) null, "time", l.longValue());
                }
                resolveSerializer.attributeInt((String) null, "version", 1);
                resolveSerializer.attribute((String) null, "key", str);
                resolveSerializer.endTag((String) null, "notification");
                i5++;
                currentTimeMillis = j;
            }
            ArrayMap arrayMap3 = snoozeHelper.mPersistedSnoozedNotificationsWithContext;
            for (int i6 = 0; i6 < arrayMap3.size(); i6++) {
                String str2 = (String) arrayMap3.keyAt(i6);
                Object valueAt2 = arrayMap3.valueAt(i6);
                resolveSerializer.startTag((String) null, "context");
                resolveSerializer.attribute((String) null, "id", (String) valueAt2);
                resolveSerializer.attributeInt((String) null, "version", 1);
                resolveSerializer.attribute((String) null, "key", str2);
                resolveSerializer.endTag((String) null, "context");
            }
            resolveSerializer.endTag((String) null, "snoozed-notifications");
        }
        notificationManagerService.mConditionProviders.writeXml(i, resolveSerializer, z);
        if (!z || i == 0) {
            resolveSerializer.startTag((String) null, "allow-secure-notifications-on-lockscreen");
            resolveSerializer.attributeBoolean((String) null, "value", notificationManagerService.mLockScreenAllowSecureNotifications);
            resolveSerializer.endTag((String) null, "allow-secure-notifications-on-lockscreen");
        }
        resolveSerializer.endTag((String) null, "notification-policy");
        resolveSerializer.endDocument();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x0069 -> B:15:0x0088). Please report as a decompilation issue!!! */
    /* renamed from: -$$Nest$mwriteScpmNotificationPoliciesJson, reason: not valid java name */
    public static void m727$$Nest$mwriteScpmNotificationPoliciesJson(NotificationManagerService notificationManagerService, FileOutputStream fileOutputStream) {
        OutputStreamWriter outputStreamWriter;
        notificationManagerService.getClass();
        Slog.d("NotificationService", "writeScpmNotificationPoliciesJson");
        JsonWriter jsonWriter = null;
        try {
            try {
                outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                try {
                    try {
                        JsonWriter jsonWriter2 = new JsonWriter(outputStreamWriter);
                        try {
                            jsonWriter2.setIndent(" ");
                            jsonWriter2.beginObject();
                            jsonWriter2.name("policy_version").value(notificationManagerService.mScpmNotificationPoliciesVersion);
                            jsonWriter2.name("ongoingActivityAllowList");
                            jsonWriter2.beginArray();
                            int size = notificationManagerService.mOngoingActivityAppList.size();
                            for (int i = 0; i < size; i++) {
                                jsonWriter2.value(((String) notificationManagerService.mOngoingActivityAppList.get(i)).toString());
                            }
                            jsonWriter2.endArray();
                            jsonWriter2.endObject();
                            try {
                                jsonWriter2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            outputStreamWriter.close();
                        } catch (IOException e2) {
                            e = e2;
                            jsonWriter = jsonWriter2;
                            e.printStackTrace();
                            if (jsonWriter != null) {
                                try {
                                    jsonWriter.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (outputStreamWriter != null) {
                                outputStreamWriter.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            jsonWriter = jsonWriter2;
                            if (jsonWriter != null) {
                                try {
                                    jsonWriter.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (outputStreamWriter == null) {
                                throw th;
                            }
                            try {
                                outputStreamWriter.close();
                                throw th;
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e6) {
                    e = e6;
                }
            } catch (IOException e7) {
                e = e7;
                outputStreamWriter = null;
            } catch (Throwable th3) {
                th = th3;
                outputStreamWriter = null;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
    }

    static {
        SystemProperties.getBoolean("debug.child_notifs", true);
        DEBUG_INTERRUPTIVENESS = SystemProperties.getBoolean("debug.notification.interruptiveness", false);
        HEALTH_KEY_LIST = Arrays.asList("14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a", "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6");
        BITMAP_DURATION = Duration.ofHours(24L);
        ALLOWED_ADJUSTMENTS = new String[]{"key_people", "key_snooze_criteria", "key_user_sentiment", "key_contextual_actions", "key_text_replies", "key_importance", "key_importance_proposal", "key_sensitive_content", "key_ranking_score", "key_not_conversation"};
        NON_BLOCKABLE_DEFAULT_ROLES = new String[]{"android.app.role.DIALER", "android.app.role.EMERGENCY"};
        TOAST_RATE_LIMITS = new MultiRateLimiter.RateLimit[]{new MultiRateLimiter.RateLimit(3, Duration.ofSeconds(20L)), new MultiRateLimiter.RateLimit(5, Duration.ofSeconds(42L)), new MultiRateLimiter.RateLimit(6, Duration.ofSeconds(68L))};
        ACTION_NOTIFICATION_TIMEOUT = "NotificationManagerService.TIMEOUT";
        POST_WAKE_LOCK_TIMEOUT = Duration.ofSeconds(30L);
        Duration.ofDays(3L).toMillis();
        Duration.ofDays(14L).toMillis();
        MY_UID = Process.myUid();
        MY_PID = Process.myPid();
        ALLOWLIST_TOKEN = new Binder();
        EDGE_NOTIFICATION_COMPONENT = new ComponentName("com.samsung.android.service.peoplestripe", "com.samsung.android.service.peoplestripe.PeopleNotiListenerService");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NotificationManagerService(android.content.Context r4) {
        /*
            r3 = this;
            com.android.server.notification.NotificationRecordLoggerImpl r0 = new com.android.server.notification.NotificationRecordLoggerImpl
            r0.<init>()
            com.android.internal.logging.UiEventLoggerImpl r1 = new com.android.internal.logging.UiEventLoggerImpl
            r1.<init>()
            r0.mUiEventLogger = r1
            com.android.internal.logging.InstanceIdSequence r1 = new com.android.internal.logging.InstanceIdSequence
            r2 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r2)
            r3.<init>(r4, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.<init>(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r0v21, types: [com.android.server.notification.NotificationManagerService$1] */
    /* JADX WARN: Type inference failed for: r0v43, types: [com.android.server.notification.NotificationManagerService$25] */
    /* JADX WARN: Type inference failed for: r0v44, types: [com.android.server.notification.NotificationManagerService$26] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.notification.NotificationManagerService$26] */
    public NotificationManagerService(Context context, NotificationRecordLogger notificationRecordLogger, InstanceIdSequence instanceIdSequence) {
        super(context);
        this.mForegroundToken = new Binder();
        this.mRankingThread = new HandlerThread("ranker", 10);
        this.mListenersDisablingEffects = new SparseArray();
        this.mEffectsSuppressors = new ArrayList();
        this.mInterruptionFilter = 0;
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
        this.mUserProfiles = new ManagedServices.UserProfiles();
        this.mLockScreenAllowSecureNotifications = true;
        this.mCallNotificationEventCallbacks = new ArrayMap();
        this.mMaxPackageEnqueueRate = 5.0f;
        this.mSavePolicyFile = new AnonymousClass11(4, this);
        this.mMsgPkgsAllowedAsConvos = new HashSet();
        this.mDelayedWakelockList = new ConcurrentList(new ArrayList());
        this.mDelayedWakeUpList = new ConcurrentList(new ArrayList());
        this.mIsFactoryBinary = FactoryTest.isFactoryBinary();
        this.mSaveConversationPackagePolicyFile = new AnonymousClass11(2, this);
        this.mConversationAppList = new ArrayList();
        this.mConversationHistoryAppList = new ArrayList();
        this.mConversationAppPolicyVersion = 0L;
        this.mIsMaxNotiLimitEnabled = true;
        this.mMaxNotiLimitCount = 300;
        this.mSaveOngoingDismissExceptionPolicyFile = new AnonymousClass11(3, this);
        this.mOngoingDismissExceptionKeyList = new ArrayList();
        this.mOngoingDismissExceptionPolicyVersion = 0L;
        this.mLimitNotificationsForOverflowAppList = new ArrayList();
        this.mSaveScpmNotificationPoliciesFile = new AnonymousClass11(5, this);
        this.mScpmNotificationPoliciesVersion = 0L;
        this.mOngoingActivityAppList = new ArrayList();
        this.mOngoingActivitySettingValue = new ArrayMap();
        this.mApplicationPolicyService = null;
        this.mIsDisableHunByCall = false;
        this.mFloatingPackageList = new ArrayList();
        this.mMultiStarEnable = false;
        this.mSmartPopupEnable = false;
        this.mIsInterruptivePostNotif = false;
        this.mNeedDeletePrevHistory = false;
        this.mFoldState = false;
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.server.notification.NotificationManagerService.1
            public final void onFoldStateChanged(boolean z) {
                Slog.d("NotificationService", "mFoldState = " + NotificationManagerService.this.mFoldState + ", isFolded = " + z);
                NotificationManagerService.this.mFoldState = z;
            }

            public final void onTableModeChanged(boolean z) {
            }
        };
        this.mNotificationDelegate = new AnonymousClass2();
        this.mNotificationManagerPrivate = new AnonymousClass2();
        this.mSecIntentReceiver = new AnonymousClass5(this, 4);
        this.mLocaleChangeReceiver = new AnonymousClass5(this, 0);
        this.mRestoreReceiver = new AnonymousClass5(this, 5);
        this.mNotificationTimeoutReceiver = new AnonymousClass5(this, 6);
        this.mPackageIntentReceiver = new AnonymousClass5(this, 2);
        this.mIntentReceiver = new AnonymousClass5(this, 3);
        this.mScpmIntentReceiver = new AnonymousClass5(this, 1);
        this.mService = new AnonymousClass16();
        this.mNotiPermissionHistoryList = new ArrayList();
        this.mNotiListenerHistoryList = new ArrayList();
        this.mNotificationListenerFrom = "";
        this.mInternalService = new AnonymousClass17();
        this.mShortcutListener = new AnonymousClass2();
        this.mTimeoutPendingIntent = new ArrayMap();
        this.mHighDataSizeNotificaitonList = new ArrayList();
        this.dayTime = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ");
        this.mEnqueueLogs = new ArrayMap();
        this.mCancelLogs = new ArrayList();
        this.mAllowedPackage = new HashSet();
        this.mIsRuneStoneSupported = false;
        this.mIsRuneStoneEnabled = false;
        this.mStateContentObserver = new ContentObserver() { // from class: com.android.server.notification.NotificationManagerService.25
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (uri == null) {
                    return;
                }
                NotificationManagerService.this.mIsRuneStoneEnabled = "true".equalsIgnoreCase(uri.getQueryParameter("enabled"));
                AnyMotionDetector$$ExternalSyntheticOutline0.m("NotificationService", new StringBuilder("RuneStone State change mIsRuneStoneEnabled = "), NotificationManagerService.this.mIsRuneStoneEnabled);
            }
        };
        this.mGoodCatchViToastOn = false;
        final int i = 0;
        this.mGoodCatchStateListener = new SemGoodCatchManager.OnStateChangeListener(this) { // from class: com.android.server.notification.NotificationManagerService.26
            public final /* synthetic */ NotificationManagerService this$0;

            {
                this.this$0 = this;
            }

            public final void onStart(String str) {
                switch (i) {
                    case 0:
                        DualAppManagerService$$ExternalSyntheticOutline0.m("onStart(), ", str, "NotificationService");
                        this.this$0.mGoodCatchViToastOn = true;
                        break;
                    default:
                        Log.d("NotificationService", "onStart(), " + str);
                        if ("noti_blocked".equals(str)) {
                            this.this$0.mGoodCatchNotiBlockedOn = true;
                            break;
                        }
                        break;
                }
            }

            public final void onStop(String str) {
                switch (i) {
                    case 0:
                        DualAppManagerService$$ExternalSyntheticOutline0.m("onStop(),", str, "NotificationService");
                        this.this$0.mGoodCatchViToastOn = false;
                        break;
                    default:
                        Log.d("NotificationService", "onStop()," + str);
                        if ("noti_blocked".equals(str)) {
                            this.this$0.mGoodCatchNotiBlockedOn = false;
                            break;
                        }
                        break;
                }
            }
        };
        this.mGoodCatchNotiBlockedOn = false;
        final int i2 = 1;
        this.mNotiGoodCatchStateListener = new SemGoodCatchManager.OnStateChangeListener(this) { // from class: com.android.server.notification.NotificationManagerService.26
            public final /* synthetic */ NotificationManagerService this$0;

            {
                this.this$0 = this;
            }

            public final void onStart(String str) {
                switch (i2) {
                    case 0:
                        DualAppManagerService$$ExternalSyntheticOutline0.m("onStart(), ", str, "NotificationService");
                        this.this$0.mGoodCatchViToastOn = true;
                        break;
                    default:
                        Log.d("NotificationService", "onStart(), " + str);
                        if ("noti_blocked".equals(str)) {
                            this.this$0.mGoodCatchNotiBlockedOn = true;
                            break;
                        }
                        break;
                }
            }

            public final void onStop(String str) {
                switch (i2) {
                    case 0:
                        DualAppManagerService$$ExternalSyntheticOutline0.m("onStop(),", str, "NotificationService");
                        this.this$0.mGoodCatchViToastOn = false;
                        break;
                    default:
                        Log.d("NotificationService", "onStop()," + str);
                        if ("noti_blocked".equals(str)) {
                            this.this$0.mGoodCatchNotiBlockedOn = false;
                            break;
                        }
                        break;
                }
            }
        };
        this.mNotificationRecordLogger = notificationRecordLogger;
        this.mNotificationInstanceIdSequence = instanceIdSequence;
        Notification.processAllowlistToken = ALLOWLIST_TOKEN;
    }

    public static void addAutoGroupAdjustment(NotificationRecord notificationRecord, String str) {
        Adjustment adjustment = new Adjustment(notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getKey(), AccountManagerService$$ExternalSyntheticOutline0.m142m("key_group_key", str), "", notificationRecord.sbn.getUserId());
        synchronized (notificationRecord.mAdjustments) {
            ((ArrayList) notificationRecord.mAdjustments).add(adjustment);
        }
    }

    public static void broadcastToCallNotificationEventCallbacks(RemoteCallbackList remoteCallbackList, NotificationRecord notificationRecord, boolean z) {
        if (remoteCallbackList != null) {
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                if (z) {
                    try {
                        remoteCallbackList.getBroadcastItem(i).onCallNotificationPosted(notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getUser());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    remoteCallbackList.getBroadcastItem(i).onCallNotificationRemoved(notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getUser());
                }
            }
            remoteCallbackList.finishBroadcast();
        }
    }

    public static void checkCallerIsSystem() {
        if (isCallerSystemOrPhone()) {
            return;
        }
        throw new SecurityException("Disallowed call for uid " + Binder.getCallingUid());
    }

    public static void checkCallerIsSystemOrShell() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return;
        }
        checkCallerIsSystem();
    }

    public static List findGroupNotificationByListLocked(ArrayList arrayList, String str, String str2, int i) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i2);
            if (notificationMatchesUserId(i, notificationRecord, false) && notificationRecord.sbn.getGroupKey().equals(str2) && notificationRecord.sbn.getPackageName().equals(str)) {
                arrayList2.add(notificationRecord);
            }
        }
        return arrayList2;
    }

    public static NotificationRecord findNotificationByListLocked(String str, ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(((NotificationRecord) arrayList.get(i)).sbn.getKey())) {
                return (NotificationRecord) arrayList.get(i);
            }
        }
        return null;
    }

    public static NotificationRecord findNotificationByListLocked(ArrayList arrayList, String str, String str2, int i, int i2) {
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i3);
            if (notificationMatchesUserId(i2, notificationRecord, (notificationRecord.getFlags() & 1792) != 0) && notificationRecord.sbn.getId() == i && TextUtils.equals(notificationRecord.sbn.getTag(), str2) && notificationRecord.sbn.getPackageName().equals(str)) {
                return notificationRecord;
            }
        }
        return null;
    }

    public static Uri getHistoryDataUri(Context context, NotificationRecord notificationRecord) {
        List<Notification.MessagingStyle.Message> messages;
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notificationRecord.sbn.getNotification());
        if (!(recoverBuilder.getStyle() instanceof Notification.MessagingStyle) || (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) == null || messages.size() <= 0) {
            return null;
        }
        Notification.MessagingStyle.Message message = messages.get(messages.size() - 1);
        if (message.getDataUri() == null || message.getDataMimeType() == null || !message.getDataMimeType().startsWith("image/")) {
            return null;
        }
        return message.getDataUri();
    }

    public static String getHistoryText(Context context, Notification notification) {
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

    public static boolean isCallerSystemOrPhone() {
        return isUidSystemOrPhone(Binder.getCallingUid());
    }

    public static boolean isCallingUidSystem() {
        return Binder.getCallingUid() == 1000;
    }

    public static boolean isNotificationRecent(long j) {
        return com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags.rapidClearNotificationsByListenerAppOpEnabled() && System.currentTimeMillis() - j < 5000;
    }

    public static boolean isUidSystemOrPhone(int i) {
        int appId = UserHandle.getAppId(i);
        return appId == 1000 || appId == 1001 || i == 0;
    }

    public static String makeTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
    }

    public static boolean notificationMatchesUserId(int i, NotificationRecord notificationRecord, boolean z) {
        return z ? notificationRecord.sbn.getUserId() == i : i == -1 || notificationRecord.sbn.getUserId() == -1 || notificationRecord.sbn.getUserId() == i;
    }

    public static boolean privateSpaceFlagsEnabled() {
        return com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    public final void addAutogroupKeyLocked(String str, boolean z) {
        NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str);
        if (notificationRecord != null && notificationRecord.sbn.getOverrideGroupKey() == null) {
            addAutoGroupAdjustment(notificationRecord, "ranker_group");
            EventLog.writeEvent(27533, str);
            if (!android.app.Flags.checkAutogroupBeforePost() || z) {
                ((RankingHandlerWorker) this.mRankingHandler).requestSort();
            }
        }
    }

    public final void addDisabledHint(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        if (this.mListenersDisablingEffects.indexOfKey(i) < 0) {
            this.mListenersDisablingEffects.put(i, new ArraySet());
        }
        ((ArraySet) this.mListenersDisablingEffects.get(i)).add(managedServiceInfo.component);
    }

    public void addEnqueuedNotification(NotificationRecord notificationRecord) {
        synchronized (this.mNotificationLock) {
            this.mEnqueuedNotifications.add(notificationRecord);
        }
    }

    public void addNotification(NotificationRecord notificationRecord) {
        synchronized (this.mNotificationLock) {
            try {
                this.mNotificationList.add(notificationRecord);
                this.mNotificationsByKey.put(notificationRecord.sbn.getKey(), notificationRecord);
                if (notificationRecord.sbn.isGroup()) {
                    this.mSummaryByGroupKey.put(notificationRecord.sbn.getGroupKey(), notificationRecord);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void allowDefaultApprovedServices(int i) {
        ArraySet arraySet;
        NotificationListeners notificationListeners = this.mListeners;
        synchronized (notificationListeners.mDefaultsLock) {
            arraySet = new ArraySet(notificationListeners.mDefaultComponents);
        }
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            ComponentName componentName = (ComponentName) arraySet.valueAt(i2);
            try {
                this.mNotificationListenerFrom = "DEFAULT_LISTENER";
                getBinderService().setNotificationListenerAccessGrantedForUser(componentName, i, true, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        allowDndPackages(i);
        setDefaultAssistantForUser(i);
    }

    public void allowDndPackages(int i) {
        ArraySet arraySet;
        ConditionProviders conditionProviders = this.mConditionProviders;
        synchronized (conditionProviders.mDefaultsLock) {
            arraySet = new ArraySet(conditionProviders.mDefaultPackages);
        }
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            try {
                getBinderService().setNotificationPolicyAccessGrantedForUser((String) arraySet.valueAt(i2), i, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (isDNDMigrationDone(i)) {
            return;
        }
        setDNDMigrationDone(i);
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

    public final boolean canSendLoggingData(NotificationRecord notificationRecord) {
        Bundle bundle;
        if (this.mIsRuneStoneSupported && this.mIsRuneStoneEnabled) {
            String packageName = notificationRecord.sbn.getPackageName();
            if (((HashSet) this.mAllowedPackage).contains(packageName)) {
                return true;
            }
            try {
                ApplicationInfo applicationInfo = getContext().getPackageManager().getApplicationInfo(packageName, 128);
                if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && "runestone".equals(bundle.getString("com.samsung.android.notification.logging", "default"))) {
                    Slog.i("NotificationService", "Notification listener logging, pkg = " + packageName);
                    ((HashSet) this.mAllowedPackage).add(packageName);
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
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

    public final void cancelAllLocked(final int i, final int i2, final int i3, final int i4, final ManagedServices.ManagedServiceInfo managedServiceInfo, final boolean z, final int i5) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.24
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    ManagedServices.ManagedServiceInfo managedServiceInfo2 = managedServiceInfo;
                    String shortString = managedServiceInfo2 == null ? null : managedServiceInfo2.component.toShortString();
                    EventLog.writeEvent(2752, Integer.valueOf(i), Integer.valueOf(i2), null, Integer.valueOf(i3), 0, 0, Integer.valueOf(i4), shortString);
                    int i6 = i5;
                    int i7 = i4;
                    NotificationManagerService$23$$ExternalSyntheticLambda0 notificationManagerService$23$$ExternalSyntheticLambda0 = new NotificationManagerService$23$$ExternalSyntheticLambda0(i6, i7);
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    NotificationManagerService.m696$$Nest$mcancelAllNotificationsByListLocked(notificationManagerService, notificationManagerService.mNotificationList, null, false, null, notificationManagerService$23$$ExternalSyntheticLambda0, z, i3, true, i7, shortString, true, elapsedRealtime);
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    NotificationManagerService.m696$$Nest$mcancelAllNotificationsByListLocked(notificationManagerService2, notificationManagerService2.mEnqueuedNotifications, null, false, null, notificationManagerService$23$$ExternalSyntheticLambda0, z, i3, true, i4, shortString, false, elapsedRealtime);
                    NotificationManagerService.this.mSnoozeHelper.cancel(i3, z);
                }
            }
        });
    }

    public final void cancelAllNotificationsInt(final int i, final int i2, final int i3, final String str, final String str2, final int i4, final int i5) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mHandler.post(new Runnable() { // from class: com.android.server.notification.NotificationManagerService.23
            public final /* synthetic */ int val$mustHaveFlags;

            @Override // java.lang.Runnable
            public final void run() {
                int i6 = i;
                int i7 = i2;
                EventLog.writeEvent(2752, Integer.valueOf(i6), Integer.valueOf(i7), str, Integer.valueOf(i4), 0, Integer.valueOf(i3), Integer.valueOf(i5), null);
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService$23$$ExternalSyntheticLambda0 notificationManagerService$23$$ExternalSyntheticLambda0 = new NotificationManagerService$23$$ExternalSyntheticLambda0(i3);
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    NotificationManagerService.m696$$Nest$mcancelAllNotificationsByListLocked(notificationManagerService, notificationManagerService.mNotificationList, str, true, str2, notificationManagerService$23$$ExternalSyntheticLambda0, false, i4, false, i5, null, true, elapsedRealtime);
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    NotificationManagerService.m696$$Nest$mcancelAllNotificationsByListLocked(notificationManagerService2, notificationManagerService2.mEnqueuedNotifications, str, true, str2, notificationManagerService$23$$ExternalSyntheticLambda0, false, i4, false, i5, null, false, elapsedRealtime);
                    NotificationManagerService.this.mSnoozeHelper.cancel(i4, str);
                }
            }
        });
    }

    public final void cancelGroupChildrenByListLocked(ArrayList arrayList, NotificationRecord notificationRecord, int i, int i2, String str, boolean z, boolean z2, FlagChecker flagChecker, int i3, long j) {
        String packageName = notificationRecord.sbn.getPackageName();
        int userId = notificationRecord.sbn.getUserId();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationRecord notificationRecord2 = (NotificationRecord) arrayList.get(size);
            StatusBarNotification statusBarNotification = notificationRecord2.sbn;
            if ((NmRune.NM_SUPPORT_NOTIFICATION_INSIGNIFICANT && (statusBarNotification.getNotification().semFlags & 262144) != 0) || !statusBarNotification.isGroup() || statusBarNotification.getNotification().isGroupSummary() || !notificationRecord2.sbn.getGroupKey().equals(notificationRecord.sbn.getGroupKey()) || !flagChecker.apply(notificationRecord2.getFlags()) || (notificationRecord2.mChannel.isImportantConversation() && i3 == 2)) {
            }
            EventLogTags.writeNotificationCancel(i, i2, packageName, statusBarNotification.getId(), statusBarNotification.getTag(), userId, 0, 0, 12, str);
            arrayList.remove(size);
            this.mNotificationsByKey.remove(notificationRecord2.sbn.getKey());
            cancelNotificationLocked(notificationRecord2, z, 12, -1, -1, z2, str, j);
        }
    }

    public final void cancelGroupChildrenLocked(NotificationRecord notificationRecord, int i, int i2, String str, boolean z, FlagChecker flagChecker, int i3, long j) {
        if (notificationRecord.sbn.getNotification().isGroupSummary()) {
            if (notificationRecord.sbn.getPackageName() != null) {
                cancelGroupChildrenByListLocked(this.mNotificationList, notificationRecord, i, i2, str, z, true, flagChecker, i3, j);
                cancelGroupChildrenByListLocked(this.mEnqueuedNotifications, notificationRecord, i, i2, str, z, false, flagChecker, i3, j);
            } else if (DBG) {
                Slog.e("NotificationService", "No package for group summary: " + notificationRecord.sbn.getKey());
            }
        }
    }

    public final void cancelNotification(int i, int i2, String str, String str2, int i3, int i4, boolean z, int i5, int i6, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        this.mHandler.scheduleCancelNotification(new CancelNotificationRunnable(i, i2, str, str2, i3, 0, i4, z, i5, i6, -1, -1, managedServiceInfo, SystemClock.elapsedRealtime()), 0);
    }

    public final void cancelNotificationInternal(int i, int i2, int i3, int i4, int i5, String str, String str2, String str3) {
        int i6;
        int handleIncomingUser = ActivityManager.handleIncomingUser(i2, i, i4, true, false, "cancelNotificationWithTag", str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (Objects.equals(str, "com.google.android.googlequicksearchbox") && str3 != null) {
            try {
                if (str3.equals("oc")) {
                    try {
                        this.mTimeToLeaveHelper.deleteDocument(i3, str, str3);
                        this.mTimeToLeaveHelper.searchDocument();
                    } catch (Exception e) {
                        Slog.d("NotificationService", "Couldn't delete TTL data : " + e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        try {
            i6 = resolveNotificationUid(str2, str, i, handleIncomingUser);
        } catch (PackageManager.NameNotFoundException unused) {
            i6 = -1;
        }
        if (i6 == -1) {
            Slog.w("NotificationService", str2 + ":" + i + " trying to cancel notification for nonexistent pkg " + str + " in user " + handleIncomingUser);
            return;
        }
        if (!Objects.equals(str, str2)) {
            synchronized (this.mNotificationLock) {
                try {
                    NotificationRecord findNotificationLocked = findNotificationLocked(i3, handleIncomingUser, str, str3);
                    if (findNotificationLocked != null && !Objects.equals(str2, findNotificationLocked.sbn.getOpPkg())) {
                        throw new SecurityException(str2 + " does not have permission to cancel a notification they did not post " + str3 + " " + i3);
                    }
                } finally {
                }
            }
        }
        Flags.traceCancelEvents();
        cancelNotification(i6, i2, str, str3, i3, i5, false, handleIncomingUser, 8, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void cancelNotificationLocked(com.android.server.notification.NotificationRecord r19, boolean r20, int r21, int r22, int r23, boolean r24, java.lang.String r25, long r26) {
        /*
            Method dump skipped, instructions count: 776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.cancelNotificationLocked(com.android.server.notification.NotificationRecord, boolean, int, int, int, boolean, java.lang.String, long):void");
    }

    public void cancelScheduledTimeoutLocked(NotificationRecord notificationRecord) {
        PendingIntent notificationTimeoutPendingIntent = getNotificationTimeoutPendingIntent(notificationRecord, 268435456);
        if (notificationTimeoutPendingIntent != null) {
            this.mAlarmManager.cancel(notificationTimeoutPendingIntent);
        }
    }

    public final void cancelToastLocked(int i) {
        ToastRecord toastRecord = (ToastRecord) this.mToastQueue.get(i);
        toastRecord.hide();
        if (i == 0) {
            this.mIsCurrentToastShown = false;
        }
        ToastRecord toastRecord2 = (ToastRecord) this.mToastQueue.remove(i);
        this.mHandler.removeCallbacksAndMessages(toastRecord2);
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 7, toastRecord2), 11000L);
        keepProcessAliveForToastIfNeededLocked(toastRecord.pid);
        if (this.mToastQueue.size() > 0) {
            showNextToastLocked(toastRecord2 instanceof TextToastRecord);
        }
    }

    public final synchronized void canceledNotificationLog(NotificationRecord notificationRecord, int i) {
        try {
            if (canSendLoggingData(notificationRecord)) {
                CollectionContract$Notification$Log orCreateNotificationLogLocked = getOrCreateNotificationLogLocked(notificationRecord);
                orCreateNotificationLogLocked.cancelReason = i;
                orCreateNotificationLogLocked.canceledTimeMs = System.currentTimeMillis();
                this.mEnqueueLogs.remove(notificationRecord.sbn.getPackageName() + "|" + notificationRecord.sbn.getId());
                ((ArrayList) this.mCancelLogs).add(orCreateNotificationLogLocked);
                if (((ArrayList) this.mCancelLogs).size() == 10) {
                    ArrayList arrayList = new ArrayList(this.mCancelLogs);
                    if (this.mIsRuneStoneEnabled) {
                        BackgroundThread.getHandler().post(new NotificationManagerService$$ExternalSyntheticLambda7(0, this, arrayList));
                    }
                    ((ArrayList) this.mCancelLogs).clear();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void checkCallerIsSameApp(int i, int i2, String str) {
        if ((i != 0 || !"root".equals(str)) && !((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).isSameApp(i, i2, 0L, str)) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Package ", str, " is not owned by uid "));
        }
    }

    public final void checkCallerIsSameApp(String str) {
        checkCallerIsSameApp(Binder.getCallingUid(), UserHandle.getCallingUserId(), str);
    }

    public final void checkCallerIsSystemOrSystemUiOrShell(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0 || isCallerSystemOrPhone()) {
            return;
        }
        getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b9, code lost:
    
        if (findNotificationByListLocked(r19.sbn.getKey(), r14.mEnqueuedNotifications) != null) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkDisqualifyingFeatures(int r15, int r16, int r17, java.lang.String r18, com.android.server.notification.NotificationRecord r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 963
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.checkDisqualifyingFeatures(int, int, int, java.lang.String, com.android.server.notification.NotificationRecord, boolean, boolean):boolean");
    }

    public final boolean checkNotificationHistoryData(NotificationRecord notificationRecord, NotificationHistory.HistoricalNotification historicalNotification) {
        String findConversationSender;
        CharSequence label;
        if (historicalNotification.getPackage() == null || historicalNotification.getChannelId() == null || historicalNotification.getChannelName() == null) {
            Slog.d("NotificationService", "History data has null value, can not be saved " + historicalNotification);
            return false;
        }
        if (Notification.MessagingStyle.class.equals(notificationRecord.sbn.getNotification().getNotificationStyle())) {
            NotificationHistory readFilteredNotificationHistoryForPackage = this.mHistoryManager.readFilteredNotificationHistoryForPackage(historicalNotification.getUserId(), 30, historicalNotification.getPackage(), historicalNotification.getSbnKey());
            boolean z = notificationRecord.sbn.getNotification().extras.getBoolean("android.isGroupConversation");
            if (z) {
                findConversationSender = findConversationSender(readFilteredNotificationHistoryForPackage, notificationRecord);
            } else {
                ShortcutInfo shortcutInfo = notificationRecord.mShortcutInfo;
                findConversationSender = (shortcutInfo == null || (label = shortcutInfo.getLabel()) == null || TextUtils.isEmpty(label)) ? findConversationSender(readFilteredNotificationHistoryForPackage, notificationRecord) : label.toString();
            }
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(notificationRecord.sbn.getPackageContext(getContext()), notificationRecord.sbn.getNotification());
            String name = recoverBuilder.getStyle() instanceof Notification.MessagingStyle ? ((Notification.MessagingStyle) recoverBuilder.getStyle()).getUser().getName() : "";
            String charSequence = name != null ? name.toString() : "";
            if (historicalNotification.getType() != 1) {
                if (z) {
                    if (historicalNotification.getTitle() != null && charSequence.equals(historicalNotification.getTitle())) {
                        Log.d("NotificationService", historicalNotification.getSbnKey() + " : " + historicalNotification.getPostedTimeMs() + " is duplicated infomation in Group conversation. will not be saved!");
                        return false;
                    }
                } else if (TextUtils.isEmpty(findConversationSender) || (historicalNotification.getTitle() != null && !findConversationSender.equals(historicalNotification.getTitle()))) {
                    Log.d("NotificationService", historicalNotification.getSbnKey() + " : " + historicalNotification.getPostedTimeMs() + " is duplicated infomation. will not be saved!");
                    return false;
                }
            }
        }
        return true;
    }

    public final void checkNotificationListenerAccess$1() {
        if (isCallerSystemOrPhone()) {
            return;
        }
        getContext().enforceCallingPermission("android.permission.MANAGE_NOTIFICATION_LISTENERS", "Caller must hold android.permission.MANAGE_NOTIFICATION_LISTENERS");
    }

    public final boolean checkUseFullScreenIntentPermission(AttributionSource attributionSource, ApplicationInfo applicationInfo, boolean z) {
        if (applicationInfo.targetSdkVersion < 29) {
            return true;
        }
        return (z ? this.mPermissionManager.checkPermissionForDataDelivery("android.permission.USE_FULL_SCREEN_INTENT", attributionSource, (String) null) : this.mPermissionManager.checkPermissionForPreflight("android.permission.USE_FULL_SCREEN_INTENT", attributionSource)) == 0;
    }

    public void clearAutogroupSummaryLocked(int i, String str) {
        NotificationRecord findNotificationByKeyLocked;
        ArrayMap arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(i));
        if (arrayMap == null || !arrayMap.containsKey(str) || (findNotificationByKeyLocked = findNotificationByKeyLocked((String) arrayMap.remove(str))) == null) {
            return;
        }
        StatusBarNotification statusBarNotification = findNotificationByKeyLocked.sbn;
        cancelNotification(MY_UID, MY_PID, str, statusBarNotification.getTag(), statusBarNotification.getId(), 0, false, i, 16, null);
    }

    public void clearNotifications() {
        synchronized (this.mNotificationLock) {
            this.mEnqueuedNotifications.clear();
            this.mNotificationList.clear();
            this.mNotificationsByKey.clear();
            this.mSummaryByGroupKey.clear();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0099 A[Catch: all -> 0x004e, TryCatch #2 {all -> 0x004e, blocks: (B:6:0x0039, B:10:0x004a, B:11:0x0053, B:13:0x0062, B:15:0x006b, B:16:0x0081, B:18:0x008b, B:19:0x0093, B:21:0x0099, B:25:0x00bf, B:28:0x00c8, B:30:0x00cd, B:31:0x00d2, B:34:0x00ec, B:59:0x00d0), top: B:5:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0121  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createNotificationChannelGroup(java.lang.String r27, int r28, android.app.NotificationChannelGroup r29, boolean r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.createNotificationChannelGroup(java.lang.String, int, android.app.NotificationChannelGroup, boolean, boolean):void");
    }

    public final Notification createReviewPermissionsNotification() {
        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS_FOR_REVIEW");
        Intent intent2 = new Intent("REVIEW_NOTIF_ACTION_REMIND");
        Intent intent3 = new Intent("REVIEW_NOTIF_ACTION_DISMISS");
        Intent intent4 = new Intent("REVIEW_NOTIF_ACTION_CANCELED");
        Notification.Action build = new Notification.Action.Builder((Icon) null, getContext().getResources().getString(17042616), PendingIntent.getBroadcast(getContext(), 0, intent2, 201326592)).build();
        return new Notification.Builder(getContext(), SystemNotificationChannels.SYSTEM_CHANGES).setSmallIcon(17304445).setContentTitle(getContext().getResources().getString(17042618)).setContentText(getContext().getResources().getString(17042617)).setContentIntent(PendingIntent.getActivity(getContext(), 0, intent, 201326592)).setStyle(new Notification.BigTextStyle()).setFlag(32, true).setAutoCancel(true).addAction(build).addAction(new Notification.Action.Builder((Icon) null, getContext().getResources().getString(17042615), PendingIntent.getBroadcast(getContext(), 0, intent3, 201326592)).build()).setDeleteIntent(PendingIntent.getBroadcast(getContext(), 0, intent4, 201326592)).build();
    }

    public final void destroyPermissionOwner(int i, IBinder iBinder, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (DBG) {
                Slog.d("NotificationService", str + ": destroying owner");
            }
            ((UriGrantsManagerService.LocalService) this.mUgmInternal).revokeUriPermissionFromOwner(iBinder, null, -1, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x007c, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x04c7, code lost:
    
        throw r12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpImpl(java.io.PrintWriter r13, com.android.server.notification.NotificationManagerService.DumpFilter r14, android.util.ArrayMap r15) {
        /*
            Method dump skipped, instructions count: 1224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.dumpImpl(java.io.PrintWriter, com.android.server.notification.NotificationManagerService$DumpFilter, android.util.ArrayMap):void");
    }

    public final void dumpNotificationRecords(PrintWriter printWriter, DumpFilter dumpFilter) {
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                if (size > 0) {
                    printWriter.println("  Notification List:");
                    for (int i = 0; i < size; i++) {
                        NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i);
                        if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.sbn)) {
                            getContext();
                            notificationRecord.dump(printWriter, "    ", dumpFilter.redact);
                        }
                    }
                    printWriter.println("  ");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enqueueNotificationInternal(final String str, String str2, final int i, int i2, String str3, int i3, Notification notification, int i4, boolean z, boolean z2) {
        PostNotificationTracker postNotificationTracker = (PostNotificationTracker) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda9
            public final Object getOrThrow() {
                NotificationManagerService notificationManagerService = NotificationManagerService.this;
                String str4 = str;
                int i5 = i;
                PowerManager.WakeLock newWakeLock = notificationManagerService.mPowerManager.newWakeLock(1, "NotificationManagerService:post:" + str4);
                newWakeLock.setWorkSource(new WorkSource(i5, str4));
                newWakeLock.acquire(NotificationManagerService.POST_WAKE_LOCK_TIMEOUT.toMillis());
                notificationManagerService.mPostNotificationTrackerFactory.getClass();
                return new NotificationManagerService.PostNotificationTracker(newWakeLock);
            }
        });
        try {
            if (enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, z, postNotificationTracker, z2)) {
            }
        } finally {
            postNotificationTracker.cancel();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0290, code lost:
    
        if (r26.mPackageManager.hasSystemFeature("android.hardware.type.automotive", 0) == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x02a6, code lost:
    
        if ("car_emergency".equals(r8.category) != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x02b0, code lost:
    
        if ("car_warning".equals(r8.category) != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x02ba, code lost:
    
        if ("car_information".equals(r8.category) == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x02bc, code lost:
    
        getContext().enforceCallingPermission("android.permission.SEND_CATEGORY_CAR_NOTIFICATIONS", "Notification category " + r8.category + " restricted");
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02e3, code lost:
    
        if (com.samsung.android.knox.SemPersonaManager.isNotificationSanitizePolicyForSF(getContext(), r7, r27) == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x02e5, code lost:
    
        r1 = getContext().createApplicationContext(getContext().getPackageManager().getApplicationInfoAsUser(r27, 8192, r7), 4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x02fc, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0582  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0641  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0655 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0656  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x09a7  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0919  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0934  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0941  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x094d  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0968  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x096a  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0643  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x05fe  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x05eb  */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [int] */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean enqueueNotificationInternal(java.lang.String r27, java.lang.String r28, int r29, int r30, java.lang.String r31, int r32, android.app.Notification r33, int r34, boolean r35, com.android.server.notification.NotificationManagerService.PostNotificationTracker r36, boolean r37) {
        /*
            Method dump skipped, instructions count: 2629
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.enqueueNotificationInternal(java.lang.String, java.lang.String, int, int, java.lang.String, int, android.app.Notification, int, boolean, com.android.server.notification.NotificationManagerService$PostNotificationTracker, boolean):boolean");
    }

    public final String findConversationSender(NotificationHistory notificationHistory, NotificationRecord notificationRecord) {
        StatusBarNotification statusBarNotification = notificationRecord.sbn;
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(statusBarNotification.getPackageContext(getContext()), statusBarNotification.getNotification());
        for (NotificationHistory.HistoricalNotification historicalNotification : notificationHistory.getNotificationsToWrite()) {
            int type = historicalNotification.getType();
            String title = historicalNotification.getTitle();
            if (type != 1 && title != null && !TextUtils.isEmpty(title) && !title.equals("")) {
                return title;
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

    public final List findGroupNotificationsLocked(int i, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(findGroupNotificationByListLocked(this.mNotificationList, str, str2, i));
        arrayList.addAll(findGroupNotificationByListLocked(this.mEnqueuedNotifications, str, str2, i));
        return arrayList;
    }

    public final NotificationRecord findNotificationByKeyLocked(String str) {
        NotificationRecord findNotificationByListLocked = findNotificationByListLocked(str, this.mNotificationList);
        if (findNotificationByListLocked != null) {
            return findNotificationByListLocked;
        }
        NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(str, this.mEnqueuedNotifications);
        if (findNotificationByListLocked2 != null) {
            return findNotificationByListLocked2;
        }
        return null;
    }

    public final NotificationRecord findNotificationLocked(int i, int i2, String str, String str2) {
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

    /* JADX WARN: Removed duplicated region for block: B:113:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0258  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fixNotification(android.app.Notification r8, java.lang.String r9, java.lang.String r10, int r11, int r12, int r13, android.app.ActivityManagerInternal.ServiceNotificationPolicy r14, boolean r15) throws android.content.pm.PackageManager.NameNotFoundException, android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 733
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.fixNotification(android.app.Notification, java.lang.String, java.lang.String, int, int, int, android.app.ActivityManagerInternal$ServiceNotificationPolicy, boolean):void");
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

    public ComponentName getApprovedAssistant(int i) {
        checkCallerIsSystemOrShell();
        return (ComponentName) CollectionUtils.firstOrNull(this.mAssistants.getAllowedComponents(i));
    }

    public INotificationManager getBinderService() {
        return INotificationManager.Stub.asInterface(this.mService);
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
        return charSequence == null ? getContext().getResources().getString(R.string.shortcut_restore_not_supported) : String.valueOf(charSequence);
    }

    public NotificationManagerInternal getInternalService() {
        return this.mInternalService;
    }

    public final int getNotificationCount(int i, int i2, String str, String str2) {
        int i3;
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    NotificationRecord notificationRecord = (NotificationRecord) this.mNotificationList.get(i4);
                    if (notificationRecord.sbn.getPackageName().equals(str) && notificationRecord.sbn.getUserId() == i && (notificationRecord.sbn.getId() != i2 || !TextUtils.equals(notificationRecord.sbn.getTag(), str2))) {
                        i3++;
                    }
                }
                int size2 = this.mEnqueuedNotifications.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    NotificationRecord notificationRecord2 = (NotificationRecord) this.mEnqueuedNotifications.get(i5);
                    if (notificationRecord2.sbn.getPackageName().equals(str) && notificationRecord2.sbn.getUserId() == i) {
                        i3++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    public NotificationRecord getNotificationRecord(String str) {
        NotificationRecord notificationRecord;
        synchronized (this.mNotificationLock) {
            notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str);
        }
        return notificationRecord;
    }

    public int getNotificationRecordCount() {
        int size;
        synchronized (this.mNotificationLock) {
            try {
                size = this.mNotificationList.size() + this.mNotificationsByKey.size() + this.mSummaryByGroupKey.size() + this.mEnqueuedNotifications.size();
                Iterator it = this.mNotificationList.iterator();
                while (it.hasNext()) {
                    NotificationRecord notificationRecord = (NotificationRecord) it.next();
                    if (this.mNotificationsByKey.containsKey(notificationRecord.sbn.getKey())) {
                        size--;
                    }
                    if (notificationRecord.sbn.isGroup() && notificationRecord.sbn.getNotification().isGroupSummary()) {
                        size--;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return size;
    }

    public final PendingIntent getNotificationTimeoutPendingIntent(NotificationRecord notificationRecord, int i) {
        return PendingIntent.getBroadcast(getContext(), 1, new Intent(ACTION_NOTIFICATION_TIMEOUT).setPackage("android").setData(new Uri.Builder().scheme("timeout").appendPath(notificationRecord.sbn.getKey()).build()).addFlags(268435456).putExtra("key", notificationRecord.sbn.getKey()), i | 67108864);
    }

    public final int getNumNotificationChannelsForPackage(String str, int i, boolean z) {
        return this.mPreferencesHelper.getNotificationChannels(i, str, z).getList().size();
    }

    public final CollectionContract$Notification$Log getOrCreateNotificationLogLocked(NotificationRecord notificationRecord) {
        String str = notificationRecord.sbn.getPackageName() + "|" + notificationRecord.sbn.getId();
        CollectionContract$Notification$Log collectionContract$Notification$Log = (CollectionContract$Notification$Log) this.mEnqueueLogs.get(str);
        if (collectionContract$Notification$Log != null) {
            return collectionContract$Notification$Log;
        }
        CollectionContract$Notification$Log collectionContract$Notification$Log2 = new CollectionContract$Notification$Log();
        collectionContract$Notification$Log2.enqueuedTimeMs = -1L;
        collectionContract$Notification$Log2.canceledTimeMs = -1L;
        collectionContract$Notification$Log2.firstExpandedTimeMs = -1L;
        collectionContract$Notification$Log2.firstShownTimeMs = -1L;
        collectionContract$Notification$Log2.pkg = notificationRecord.sbn.getPackageName();
        collectionContract$Notification$Log2.id = notificationRecord.sbn.getId();
        collectionContract$Notification$Log2.category = notificationRecord.sbn.getNotification().category;
        collectionContract$Notification$Log2.channelId = notificationRecord.mChannel.getId();
        collectionContract$Notification$Log2.tag = notificationRecord.sbn.getTag();
        collectionContract$Notification$Log2.enqueuedTimeMs = System.currentTimeMillis();
        this.mEnqueueLogs.put(str, collectionContract$Notification$Log2);
        return collectionContract$Notification$Log2;
    }

    public final int getPackageImportanceWithIdentity(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getPackageImportance(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle getScpmBundle(String str, Uri uri, int i) {
        Bundle bundle = new Bundle();
        if (i != 0) {
            if (i != 1 && i != 2) {
                return null;
            }
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, str);
            return i == 1 ? getContext().getContentResolver().call(uri, "getLastError", "android", bundle) : getContext().getContentResolver().call(uri, "getStatus", "android", bundle);
        }
        bundle.putString("packageName", "android");
        bundle.putString("appId", "zcbtj3qbt1");
        bundle.putString("version", "0.0.0");
        bundle.putString("receiverPackageName", "android");
        return getContext().getContentResolver().call(uri, "register", "android", bundle);
    }

    public ShortcutHelper getShortcutHelper() {
        return this.mShortcutHelper;
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

    public void handleSavePolicyFile() {
        Handler handler = IoThread.getHandler();
        AnonymousClass11 anonymousClass11 = this.mSavePolicyFile;
        if (handler.hasCallbacks(anonymousClass11)) {
            return;
        }
        IoThread.getHandler().postDelayed(anonymousClass11, 250L);
    }

    public final boolean hasCompanionDevice(int i, String str, Set set) {
        if (this.mCompanionManager == null) {
            this.mCompanionManager = ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService("companiondevice"));
        }
        if (this.mCompanionManager == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (AssociationInfo associationInfo : this.mCompanionManager.getAssociations(str, i)) {
                    if (set == null || set.contains(associationInfo.getDeviceProfile())) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
            } catch (RemoteException e) {
                Slog.e("NotificationService", "Cannot reach companion device service", e);
            } catch (SecurityException unused) {
            } catch (Exception e2) {
                Slog.e("NotificationService", "Cannot verify caller pkg=" + str + ", userId=" + i, e2);
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int indexOfNotificationLocked(String str) {
        int size = this.mNotificationList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(((NotificationRecord) this.mNotificationList.get(i)).sbn.getKey())) {
                return i;
            }
        }
        return -1;
    }

    public final int indexOfToastLocked(IBinder iBinder, String str) {
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

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v49 ??, still in use, count: 1, list:
          (r0v49 ?? I:com.android.server.notification.RankingHelper) from 0x0182: IPUT 
          (r0v49 ?? I:com.android.server.notification.RankingHelper)
          (r27v0 'this' ?? I:com.android.server.notification.NotificationManagerService A[IMMUTABLE_TYPE, THIS])
         com.android.server.notification.NotificationManagerService.mRankingHelper com.android.server.notification.RankingHelper
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public void init(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v49 ??, still in use, count: 1, list:
          (r0v49 ?? I:com.android.server.notification.RankingHelper) from 0x0182: IPUT 
          (r0v49 ?? I:com.android.server.notification.RankingHelper)
          (r27v0 'this' ?? I:com.android.server.notification.NotificationManagerService A[IMMUTABLE_TYPE, THIS])
         com.android.server.notification.NotificationManagerService.mRankingHelper com.android.server.notification.RankingHelper
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r28v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
    
        if (r4.mTelecomManager.isInSelfManagedCall(r5, android.os.UserHandle.ALL) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isCallNotification(java.lang.String r5) {
        /*
            r4 = this;
            long r0 = android.os.Binder.clearCallingIdentity()
            android.content.pm.PackageManager r2 = r4.mPackageManagerClient     // Catch: java.lang.Throwable -> L28
            java.lang.String r3 = "android.software.telecom"
            boolean r2 = r2.hasSystemFeature(r3)     // Catch: java.lang.Throwable -> L28
            r3 = 0
            if (r2 == 0) goto L33
            android.telecom.TelecomManager r2 = r4.mTelecomManager     // Catch: java.lang.Throwable -> L28
            if (r2 == 0) goto L33
            boolean r2 = r2.isInManagedCall()     // Catch: java.lang.Throwable -> L28 java.lang.IllegalStateException -> L2f
            if (r2 != 0) goto L2a
            boolean r2 = r4.mCMCinCallState     // Catch: java.lang.Throwable -> L28 java.lang.IllegalStateException -> L2f
            if (r2 != 0) goto L2a
            android.telecom.TelecomManager r4 = r4.mTelecomManager     // Catch: java.lang.Throwable -> L28 java.lang.IllegalStateException -> L2f
            android.os.UserHandle r2 = android.os.UserHandle.ALL     // Catch: java.lang.Throwable -> L28 java.lang.IllegalStateException -> L2f
            boolean r4 = r4.isInSelfManagedCall(r5, r2)     // Catch: java.lang.Throwable -> L28 java.lang.IllegalStateException -> L2f
            if (r4 == 0) goto L2b
            goto L2a
        L28:
            r4 = move-exception
            goto L37
        L2a:
            r3 = 1
        L2b:
            android.os.Binder.restoreCallingIdentity(r0)
            return r3
        L2f:
            android.os.Binder.restoreCallingIdentity(r0)
            return r3
        L33:
            android.os.Binder.restoreCallingIdentity(r0)
            return r3
        L37:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.isCallNotification(java.lang.String):boolean");
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
            if (applicationInfo != null) {
                return applicationInfo.isInstantApp();
            }
            throw new SecurityException("Unknown package " + str);
        } catch (RemoteException e) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown uid "), e);
        }
    }

    public boolean isCallerSystemOrSystemUi() {
        return isCallerSystemOrPhone() || getContext().checkCallingPermission("android.permission.STATUS_BAR_SERVICE") == 0;
    }

    public boolean isDNDMigrationDone(int i) {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "dnd_settings_migrated", 0, i) == 1;
    }

    public boolean isInteractionVisibleToListener(ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        boolean z;
        IInterface iInterface = managedServiceInfo.service;
        synchronized (this.mNotificationLock) {
            NotificationAssistants notificationAssistants = this.mAssistants;
            if (iInterface == null) {
                notificationAssistants.getClass();
            } else if (notificationAssistants.getServiceFromTokenLocked(iInterface) != null) {
                z = true;
            }
            z = false;
        }
        return !z || managedServiceInfo.isSameUser(i);
    }

    public boolean isNASMigrationDone(int i) {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "nas_settings_updated", 0, i) == 1;
    }

    public final boolean isPackageInForegroundForToast(int i) {
        VisibleActivityProcessTracker visibleActivityProcessTracker = ActivityTaskManagerService.this.mVisibleActivityProcessTracker;
        visibleActivityProcessTracker.getClass();
        synchronized (visibleActivityProcessTracker.mProcMap) {
            try {
                for (int size = visibleActivityProcessTracker.mProcMap.size() - 1; size >= 0; size--) {
                    WindowProcessController windowProcessController = (WindowProcessController) visibleActivityProcessTracker.mProcMap.keyAt(size);
                    if (windowProcessController.mUid == i && windowProcessController.hasResumedActivity()) {
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isPackagePausedOrSuspended(int i, String str) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        PackageStateInternal packageStateInternal = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).getPackageStateInternal(str);
        return isPackageSuspendedForUser(str, i) | (((packageStateInternal == null ? 0 : packageStateInternal.getUserStateOrDefault(identifier).getDistractionFlags()) & 2) != 0);
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

    public final boolean isProfileUser(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        return privateSpaceFlagsEnabled() ? userInfo.isProfile() && this.mUmInternal.getProfileParentId(userInfo.id) != userInfo.id : userInfo.isManagedProfile() || userInfo.isCloneProfile();
    }

    public final boolean isRecordBlockedLocked(NotificationRecord notificationRecord) {
        return this.mPreferencesHelper.isGroupBlocked(notificationRecord.sbn.getUid(), notificationRecord.sbn.getPackageName(), notificationRecord.mChannel.getGroup()) || notificationRecord.mImportance == 0;
    }

    public final boolean isScpmAvailable() {
        return getContext().getPackageManager().resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    public boolean isVisibleToListener(StatusBarNotification statusBarNotification, int i, ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (managedServiceInfo == null || !managedServiceInfo.enabledAndUserMatches(statusBarNotification.getUserId()) || !isInteractionVisibleToListener(managedServiceInfo, statusBarNotification.getUserId())) {
            return false;
        }
        NotificationListenerFilter notificationListenerFilter = this.mListeners.getNotificationListenerFilter(managedServiceInfo.mKey);
        if (notificationListenerFilter != null) {
            return notificationListenerFilter.isTypeAllowed(i) && notificationListenerFilter.isPackageAllowed(new VersionedPackage(statusBarNotification.getPackageName(), statusBarNotification.getUid()));
        }
        return true;
    }

    public boolean isVisuallyInterruptive(NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
        Notification.Builder recoverBuilder;
        Notification.Builder recoverBuilder2;
        boolean isGroup = notificationRecord2.sbn.isGroup();
        boolean z = DEBUG_INTERRUPTIVENESS;
        if (isGroup && notificationRecord2.sbn.getNotification().isGroupSummary()) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is not interruptive: summary");
            }
            return false;
        }
        if (notificationRecord == null) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: new notification");
            }
            return true;
        }
        Notification notification = notificationRecord.sbn.getNotification();
        Notification notification2 = notificationRecord2.sbn.getNotification();
        if (notification.extras == null || notification2.extras == null) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is not interruptive: no extras");
            }
            return false;
        }
        if (android.app.Flags.sortSectionByTime()) {
            if (notificationRecord2.sbn.getNotification().isFgsOrUij()) {
                if (z) {
                    Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is not interruptive: FGS/UIJ");
                }
                return false;
            }
        } else if ((notificationRecord2.sbn.getNotification().flags & 64) != 0) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is not interruptive: foreground service");
            }
            return false;
        }
        String valueOf = String.valueOf(notification.extras.get("android.title"));
        String valueOf2 = String.valueOf(notification2.extras.get("android.title"));
        if (!valueOf.equals(valueOf2)) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: changed title");
                Slog.v("NotificationService", "INTERRUPTIVENESS: ".concat(String.format("   old title: %s (%s@0x%08x)", valueOf, valueOf.getClass(), Integer.valueOf(valueOf.hashCode()))));
                Slog.v("NotificationService", "INTERRUPTIVENESS: ".concat(String.format("   new title: %s (%s@0x%08x)", valueOf2, valueOf2.getClass(), Integer.valueOf(valueOf2.hashCode()))));
            }
            return true;
        }
        String valueOf3 = String.valueOf(notification.extras.get("android.text"));
        String valueOf4 = String.valueOf(notification2.extras.get("android.text"));
        if (!valueOf3.equals(valueOf4)) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: changed text");
                Slog.v("NotificationService", "INTERRUPTIVENESS: ".concat(String.format("   old text: %s (%s@0x%08x)", valueOf3, valueOf3.getClass(), Integer.valueOf(valueOf3.hashCode()))));
                Slog.v("NotificationService", "INTERRUPTIVENESS: ".concat(String.format("   new text: %s (%s@0x%08x)", valueOf4, valueOf4.getClass(), Integer.valueOf(valueOf4.hashCode()))));
            }
            return true;
        }
        if (notification.hasCompletedProgress() != notification2.hasCompletedProgress()) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: completed progress");
            }
            return true;
        }
        if (Notification.areIconsDifferent(notification, notification2)) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: icons differ");
            }
            return true;
        }
        if (notificationRecord2.mAllowBubble) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is not interruptive: bubble");
            }
            return false;
        }
        if (Notification.areActionsVisiblyDifferent(notification, notification2)) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: changed actions");
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
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: styles differ");
            }
            return true;
        }
        if (Notification.areRemoteViewsChanged(recoverBuilder, recoverBuilder2)) {
            if (z) {
                Slog.v("NotificationService", "INTERRUPTIVENESS: " + notificationRecord2.sbn.getKey() + " is interruptive: remoteviews differ");
            }
            return true;
        }
        return false;
    }

    public final void keepProcessAliveForToastIfNeededLocked(int i) {
        ArrayList arrayList = this.mToastQueue;
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            ToastRecord toastRecord = (ToastRecord) arrayList.get(i3);
            if (toastRecord.pid == i && (toastRecord instanceof CustomToastRecord)) {
                i2++;
            }
        }
        try {
            this.mAm.setProcessImportant(this.mForegroundToken, i, i2 > 0, "toast");
        } catch (RemoteException unused) {
        }
    }

    public final void loadDefaultConversationHistoryPackageList() {
        Slog.d("NotificationService", "loadDefaultConversationHistoryPackageList");
        String string = getContext().getResources().getString(R.string.config_wlan_network_service_class);
        if (string != null) {
            this.mConversationHistoryAppList.clear();
            this.mConversationHistoryAppList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultConversationHistoryPackageList - size = " + this.mConversationHistoryAppList.size());
        }
    }

    public final void loadDefaultConversationPackageList() {
        Slog.d("NotificationService", "loadDefaultConversationPackageList");
        this.mConversationAppPolicyVersion = 1L;
        String string = getContext().getResources().getString(R.string.config_wlan_data_service_package);
        if (string != null) {
            this.mConversationAppList.clear();
            this.mConversationAppList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultConversationPackageList - size = " + this.mConversationAppList.size());
        }
        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
            this.mPreferencesHelper.setHideContentAllowList(this.mConversationAppList);
        }
    }

    public final void loadDefaultLimitNotificationsForOverflowAppList() {
        Slog.d("NotificationService", "loadDefaultLimitNotificationsForOverflowAppList");
        String string = getContext().getResources().getString(R.string.emergency_call_dialog_number_for_display);
        if (string != null) {
            this.mLimitNotificationsForOverflowAppList.clear();
            this.mLimitNotificationsForOverflowAppList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultLimitNotificationsForOverflowAppList - size = " + this.mLimitNotificationsForOverflowAppList.size());
        }
    }

    public final void loadDefaultOngoingActivitySupportAppList() {
        Slog.d("NotificationService", "loadDefaultOngoingActivitySupportAppList");
        String string = getContext().getResources().getString(R.string.config_wlan_network_service_package);
        if (string != null) {
            this.mOngoingActivityAppList.clear();
            this.mOngoingActivityAppList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultOngoingActivitySupportAppList - size = " + this.mOngoingActivityAppList.size());
        }
    }

    public final void loadDefaultOngoingDismissExceptionKeyList() {
        Slog.d("NotificationService", "loadDefaultOngoingDismissExceptionKeyList");
        this.mOngoingDismissExceptionPolicyVersion = 1L;
        String string = getContext().getResources().getString(R.string.ext_media_checking_notification_message);
        if (string != null) {
            this.mOngoingDismissExceptionKeyList.clear();
            this.mOngoingDismissExceptionKeyList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
            Slog.d("NotificationService", "loadDefaultOngoingDismissExceptionKeyList - size = " + this.mOngoingDismissExceptionKeyList.size());
        }
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
                        try {
                            fileInputStream = this.mPolicyFile.openRead();
                            readPolicyXml(-1, fileInputStream, false);
                            if (this.mPackageManagerClient.hasSystemFeature("android.hardware.type.watch")) {
                                resetDefaultDndIfNecessary();
                            }
                        } catch (NumberFormatException e) {
                            Log.wtf("NotificationService", "Unable to parse notification policy", e);
                        } catch (Exception e2) {
                            Log.wtf("NotificationService", "Fail to parse notification policy", e2);
                        }
                    } catch (FileNotFoundException e3) {
                        Log.wtf("NotificationService", "Can not find notification policy", e3);
                        this.mListeners.loadDefaultsFromConfig();
                        this.mConditionProviders.loadDefaultsFromConfig();
                        this.mAssistants.loadDefaultsFromConfig(true);
                        allowDefaultApprovedServices(0);
                    } catch (XmlPullParserException e4) {
                        Log.wtf("NotificationService", "Unable to parse notification policy", e4);
                    }
                } catch (IOException e5) {
                    Log.wtf("NotificationService", "Unable to read notification policy", e5);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    public void logSmartSuggestionsVisible(NotificationRecord notificationRecord, int i) {
        if ((notificationRecord.mNumberOfSmartRepliesAdded > 0 || notificationRecord.mNumberOfSmartActionsAdded > 0) && !notificationRecord.mHasSeenSmartReplies) {
            notificationRecord.mHasSeenSmartReplies = true;
            this.mMetricsLogger.write(notificationRecord.getLogMaker().setCategory(1382).addTaggedData(1384, Integer.valueOf(notificationRecord.mNumberOfSmartRepliesAdded)).addTaggedData(1599, Integer.valueOf(notificationRecord.mNumberOfSmartActionsAdded)).addTaggedData(1600, Integer.valueOf(notificationRecord.mSuggestionsGeneratedByAssistant ? 1 : 0)).addTaggedData(1629, Integer.valueOf(i)).addTaggedData(1647, Integer.valueOf(notificationRecord.mEditChoicesBeforeSending ? 1 : 0)));
            ((NotificationRecordLoggerImpl) this.mNotificationRecordLogger).log(NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLY_VISIBLE, notificationRecord);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo r41) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.makeRankingUpdateLocked(com.android.server.notification.ManagedServices$ManagedServiceInfo):android.service.notification.NotificationRankingUpdate");
    }

    public final void maybeNotifySystemUiListenerLifetimeExtendedLocked(NotificationRecord notificationRecord, String str, int i) {
        if ((notificationRecord.sbn.getNotification().flags & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) > 0) {
            boolean z = str != null && i == 100;
            notificationRecord.mPostSilently = true;
            notificationRecord.sbn.getNotification().flags |= 8;
            WorkerHandler workerHandler = this.mHandler;
            int identifier = notificationRecord.sbn.getUser().getIdentifier();
            this.mPostNotificationTrackerFactory.getClass();
            workerHandler.post(new EnqueueNotificationRunnable(identifier, notificationRecord, z, new PostNotificationTracker(null)));
        }
    }

    public final void maybeRecordInterruptionLocked(NotificationRecord notificationRecord) {
        CharSequence label;
        if (notificationRecord.mIsInterruptive && !notificationRecord.mRecordedInterruption) {
            UsageStatsManagerInternal usageStatsManagerInternal = this.mAppUsageStats;
            String packageName = notificationRecord.sbn.getPackageName();
            String id = notificationRecord.mChannel.getId();
            int userId = notificationRecord.sbn.getUserId();
            if (userId == -1) {
                userId = 0;
            }
            UsageStatsService.LocalService localService = (UsageStatsService.LocalService) usageStatsManagerInternal;
            localService.getClass();
            if (packageName == null || id == null) {
                Slog.w("UsageStatsService", "Event reported without a package name or a channel ID");
            } else {
                UsageEvents.Event event = new UsageEvents.Event(12, SystemClock.elapsedRealtime());
                event.mPackage = packageName.intern();
                event.mNotificationChannelId = id.intern();
                UsageStatsService.this.reportEventOrAddToQueue(userId, event);
            }
        }
        if (this.mIsInterruptivePostNotif) {
            Trace.traceBegin(524288L, "notifHistoryAddItem");
            try {
                if (notificationRecord.sbn.getNotification().getSmallIcon() != null) {
                    Uri historyDataUri = getHistoryDataUri(notificationRecord.sbn.getPackageContext(getContext()), notificationRecord);
                    NotificationHistory.HistoricalNotification.Builder when = new NotificationHistory.HistoricalNotification.Builder().setPackage(notificationRecord.sbn.getPackageName()).setUid(notificationRecord.sbn.getUid()).setUserId(notificationRecord.sbn.getNormalizedUserId()).setChannelId(notificationRecord.mChannel.getId()).setChannelName(notificationRecord.mChannel.getName().toString()).setPostedTimeMs(System.currentTimeMillis()).setTitle(getHistoryTitle(notificationRecord.sbn.getPackageContext(getContext()), notificationRecord.sbn.getNotification())).setText(getHistoryText(notificationRecord.sbn.getPackageContext(getContext()), notificationRecord.sbn.getNotification())).setIcon(notificationRecord.sbn.getNotification().getSmallIcon()).setUri(historyDataUri).setType(historyDataUri == null ? 0 : 2).setSbnKey(notificationRecord.sbn.getKey()).setWhen(notificationRecord.sbn.getNotification().when);
                    ShortcutInfo shortcutInfo = notificationRecord.mShortcutInfo;
                    NotificationHistory.HistoricalNotification build = when.setExtraTitle((shortcutInfo == null || (label = shortcutInfo.getLabel()) == null || TextUtils.isEmpty(label)) ? "" : label.toString()).build();
                    if (this.mNeedDeletePrevHistory) {
                        this.mHistoryManager.updateNotificationItems(notificationRecord.sbn.getNormalizedUserId(), build);
                    }
                    if (checkNotificationHistoryData(notificationRecord, build)) {
                        NotificationHistoryManager notificationHistoryManager = this.mHistoryManager;
                        notificationHistoryManager.getClass();
                        Binder.withCleanCallingIdentity(new NotificationHistoryManager$$ExternalSyntheticLambda0(notificationHistoryManager, build));
                    }
                }
                Trace.traceEnd(524288L);
                notificationRecord.mRecordedInterruption = true;
                this.mIsInterruptivePostNotif = false;
                this.mNeedDeletePrevHistory = false;
            } catch (Throwable th) {
                Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    public final void maybeRegisterMessageSent(NotificationRecord notificationRecord) {
        boolean z;
        boolean z2;
        boolean z3;
        if (notificationRecord.isConversation()) {
            if (notificationRecord.mShortcutInfo == null) {
                PreferencesHelper preferencesHelper = this.mPreferencesHelper;
                String packageName = notificationRecord.sbn.getPackageName();
                int uid = notificationRecord.sbn.getUid();
                synchronized (preferencesHelper.mLock) {
                    PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = preferencesHelper.getOrCreatePackagePreferencesLocked(uid, packageName);
                    z = !orCreatePackagePreferencesLocked.hasSentInvalidMessage;
                    orCreatePackagePreferencesLocked.hasSentInvalidMessage = true;
                }
                if (z) {
                    handleSavePolicyFile();
                    return;
                }
                return;
            }
            PreferencesHelper preferencesHelper2 = this.mPreferencesHelper;
            String packageName2 = notificationRecord.sbn.getPackageName();
            int uid2 = notificationRecord.sbn.getUid();
            synchronized (preferencesHelper2.mLock) {
                PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked2 = preferencesHelper2.getOrCreatePackagePreferencesLocked(uid2, packageName2);
                z2 = !orCreatePackagePreferencesLocked2.hasSentValidMessage;
                orCreatePackagePreferencesLocked2.hasSentValidMessage = true;
            }
            if (z2) {
                handleSavePolicyFile();
                return;
            }
            if (notificationRecord.sbn.getNotification().getBubbleMetadata() != null) {
                PreferencesHelper preferencesHelper3 = this.mPreferencesHelper;
                String packageName3 = notificationRecord.sbn.getPackageName();
                int uid3 = notificationRecord.sbn.getUid();
                synchronized (preferencesHelper3.mLock) {
                    PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked3 = preferencesHelper3.getOrCreatePackagePreferencesLocked(uid3, packageName3);
                    z3 = !orCreatePackagePreferencesLocked3.hasSentValidBubble;
                    orCreatePackagePreferencesLocked3.hasSentValidBubble = true;
                }
                if (z3) {
                    handleSavePolicyFile();
                }
            }
        }
    }

    public final void maybeReportForegroundServiceUpdate(NotificationRecord notificationRecord) {
        if ((notificationRecord.getFlags() & 64) != 0) {
            StatusBarNotification statusBarNotification = notificationRecord.sbn;
            this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda8(this, true, statusBarNotification.getNotification(), statusBarNotification.getId(), statusBarNotification.getPackageName(), statusBarNotification.getUser().getIdentifier()));
        }
    }

    public final void notifyCallNotificationEventListenerOnRemoved(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification().isStyle(Notification.CallStyle.class)) {
            synchronized (this.mCallNotificationEventCallbacks) {
                try {
                    ArrayMap arrayMap = (ArrayMap) this.mCallNotificationEventCallbacks.get(notificationRecord.sbn.getPackageName());
                    if (arrayMap == null) {
                        return;
                    }
                    if (notificationRecord.sbn.getUser().equals(UserHandle.ALL)) {
                        Iterator it = arrayMap.values().iterator();
                        while (it.hasNext()) {
                            broadcastToCallNotificationEventCallbacks((RemoteCallbackList) it.next(), notificationRecord, false);
                        }
                    } else {
                        broadcastToCallNotificationEventCallbacks((RemoteCallbackList) arrayMap.get(Integer.valueOf(notificationRecord.sbn.getUser().getIdentifier())), notificationRecord, false);
                        broadcastToCallNotificationEventCallbacks((RemoteCallbackList) arrayMap.get(-1), notificationRecord, false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyZenPolicy() {
        if (this.mZenModeHelper == null) {
            Slog.e("NotificationService", "notifyZenPolicy : mZenModeHelper is null");
        } else {
            this.mHandler.post(new AnonymousClass11(0, this));
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        onBootPhase(i, Looper.getMainLooper());
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBootPhase(int r18, android.os.Looper r19) {
        /*
            Method dump skipped, instructions count: 1388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.onBootPhase(int, android.os.Looper):void");
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Context context = getContext();
        NotificationManagerService$$ExternalSyntheticLambda5 notificationManagerService$$ExternalSyntheticLambda5 = new NotificationManagerService$$ExternalSyntheticLambda5(this);
        ManagedServices.UserProfiles userProfiles = this.mUserProfiles;
        SnoozeHelper snoozeHelper = new SnoozeHelper(context, notificationManagerService$$ExternalSyntheticLambda5, userProfiles);
        File file = new File(Environment.getDataDirectory(), "system");
        this.mRankingThread.start();
        WorkerHandler workerHandler = new WorkerHandler(Looper.myLooper());
        this.mShowReviewPermissionsNotification = getContext().getResources().getBoolean(R.bool.config_powerDecoupleInteractiveModeFromDisplay);
        RankingHandler rankingHandlerWorker = new RankingHandlerWorker(this.mRankingThread.getLooper());
        IPackageManager packageManager = AppGlobals.getPackageManager();
        PackageManager packageManager2 = getContext().getPackageManager();
        LightsManager lightsManager = (LightsManager) getLocalService(LightsManager.class);
        Context context2 = getContext();
        Object obj = this.mNotificationLock;
        NotificationListeners notificationListeners = new NotificationListeners(context2, obj, this.mUserProfiles, AppGlobals.getPackageManager(), UserManager.isHeadlessSystemUserMode());
        NotificationAssistants notificationAssistants = new NotificationAssistants(getContext(), obj, this.mUserProfiles, AppGlobals.getPackageManager());
        ConditionProviders conditionProviders = new ConditionProviders(getContext(), userProfiles, AppGlobals.getPackageManager());
        NotificationUsageStats notificationUsageStats = new NotificationUsageStats(getContext());
        AtomicFile atomicFile = new AtomicFile(new File(file, "notification_policy.xml"), "notification-policy");
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService("activity");
        this.mAutoGroupAtCount = 4;
        GroupHelper groupHelper = new GroupHelper(getContext(), getContext().getPackageManager(), this.mAutoGroupAtCount, new AnonymousClass2());
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
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        MultiRateLimiter.Builder builder = new MultiRateLimiter.Builder(getContext(), null);
        MultiRateLimiter.RateLimit[] rateLimitArr = TOAST_RATE_LIMITS;
        int length = rateLimitArr.length;
        int i = 0;
        while (i < length) {
            StatsManager statsManager2 = statsManager;
            MultiRateLimiter.RateLimit rateLimit = rateLimitArr[i];
            builder.addRateLimit(rateLimit.mLimit, rateLimit.mWindowSize);
            i++;
            statsManager = statsManager2;
            notificationHistoryManager = notificationHistoryManager;
        }
        NotificationHistoryManager notificationHistoryManager2 = notificationHistoryManager;
        MultiRateLimiter multiRateLimiter = new MultiRateLimiter(builder.mQuotaTrackers);
        getContext();
        PermissionHelper permissionHelper = new PermissionHelper(AppGlobals.getPackageManager(), AppGlobals.getPermissionManager());
        UsageStatsManagerInternal usageStatsManagerInternal2 = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        TelecomManager telecomManager = (TelecomManager) getContext().getSystemService(TelecomManager.class);
        NotificationChannelLoggerImpl notificationChannelLoggerImpl = new NotificationChannelLoggerImpl();
        notificationChannelLoggerImpl.mUiEventLogger = new UiEventLoggerImpl();
        init(workerHandler, rankingHandlerWorker, packageManager, packageManager2, lightsManager, notificationListeners, notificationAssistants, conditionProviders, null, snoozeHelper, notificationUsageStats, atomicFile, activityManager, groupHelper, service, activityTaskManagerInternal, usageStatsManagerInternal, devicePolicyManagerInternal, service2, uriGrantsManagerInternal, appOpsManager, userManager, notificationHistoryManager2, statsManager, activityManagerInternal, multiRateLimiter, permissionHelper, usageStatsManagerInternal2, telecomManager, notificationChannelLoggerImpl, SystemUiSystemPropertiesFlags.getResolver(), (PermissionManager) getContext().getSystemService(PermissionManager.class), (PowerManager) getContext().getSystemService(PowerManager.class), new AnonymousClass14(), new EdgeLightingManager(getContext()), new AtomicFile(new File(file, "notification_conversation_apps_policy.json"), "notification-conversation-apps-policy"), new AtomicFile(new File(file, "notification_ongoing_dismiss_exception_policy.json"), "notification-ongoing-dismiss-exception-policy"), new AtomicFile(new File(file, "notification_scpm_policies.json"), "notification-scpm-policies"));
        publishBinderService("notification", this.mService, false, 5);
        publishLocalService(NotificationManagerInternal.class, this.mInternalService);
        publishLocalService(EdgeManagerInternal.class, new EdgeLightingLocalService());
        this.mNotificationReminder = new NotificationReminder(getContext(), Looper.myLooper(), this.mPreferencesHelper, this.mAlarmManager);
        this.mSmartAlertController = new SmartAlertController(getContext());
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda3(this, targetUser, 1));
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Flags.useSsmUserSwitchSignal();
        Slog.d("NotificationService", "onUserSwitching - !Flags.useSsmUserSwitchSignal");
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        this.mHandler.post(new NotificationManagerService$$ExternalSyntheticLambda3(this, targetUser, 0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public final void readConversationAppPolicyJson(FileInputStream fileInputStream, boolean z) {
        BufferedReader bufferedReader;
        boolean z2;
        ?? r1;
        Slog.d("NotificationService", "readConversationAppPolicyJson");
        StringBuilder sb = new StringBuilder();
        ?? r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else {
                            sb.append(readLine);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        r4 = bufferedReader;
                        e.printStackTrace();
                        if (r4 != 0) {
                            try {
                                try {
                                    r4.close();
                                } finally {
                                }
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                r4.close();
                            }
                        }
                        if (r4 != 0) {
                            r4.close();
                        }
                    } catch (JSONException e4) {
                        e = e4;
                        r4 = bufferedReader;
                        e.printStackTrace();
                        if (r4 != 0) {
                            try {
                                try {
                                    r4.close();
                                } finally {
                                }
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                r4.close();
                            }
                        }
                        if (r4 != 0) {
                            r4.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        r4 = bufferedReader;
                        try {
                            if (r4 != 0) {
                                try {
                                    try {
                                        r4.close();
                                    } catch (Throwable th2) {
                                        try {
                                            r4.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                        throw th2;
                                    }
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                    r4.close();
                                    throw th;
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
                Slog.d("NotificationService", "readConversationAppPolicyJson - current version = " + this.mConversationAppPolicyVersion + " new version = " + j);
                if (j > this.mConversationAppPolicyVersion) {
                    this.mConversationAppPolicyVersion = j;
                    JSONArray jSONArray = jSONObject.getJSONArray("appList");
                    this.mConversationAppList.clear();
                    int length = jSONArray.length();
                    int i = 0;
                    while (true) {
                        z2 = DBG;
                        if (i >= length) {
                            break;
                        }
                        this.mConversationAppList.add(jSONArray.get(i).toString());
                        if (z2) {
                            Slog.d("NotificationService", "readConversationAppPolicyJson - packageList = " + jSONArray.get(i).toString());
                        }
                        i++;
                    }
                    Slog.d("NotificationService", "readConversationAppPolicyJson - size = " + length);
                    r4 = 1;
                    if (jSONObject.has("historyAppList")) {
                        JSONArray jSONArray2 = jSONObject.getJSONArray("historyAppList");
                        int length2 = jSONArray2.length();
                        this.mConversationHistoryAppList.clear();
                        for (int i2 = 0; i2 < length2; i2++) {
                            this.mConversationHistoryAppList.add(jSONArray2.get(i2).toString());
                            if (z2) {
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
                        if (NmRune.NM_SUPPORT_NOTIFICATION_INSIGNIFICANT) {
                            this.mNotificationHighlightCore.setConversationList(this.mConversationAppList);
                        }
                    }
                    if (z || r4 != 0) {
                        Slog.d("NotificationService", "handleSaveConversationPackagePolicyFile");
                        Handler handler = IoThread.getHandler();
                        AnonymousClass11 anonymousClass11 = this.mSaveConversationPackagePolicyFile;
                        if (!handler.hasCallbacks(anonymousClass11)) {
                            IoThread.getHandler().postDelayed(anonymousClass11, 250L);
                        }
                    }
                }
            } catch (IOException e9) {
                e = e9;
            } catch (JSONException e10) {
                e = e10;
            }
            try {
                try {
                    bufferedReader.close();
                    bufferedReader.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                    bufferedReader.close();
                }
            } catch (Throwable th3) {
                try {
                    bufferedReader.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
                throw th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v18, types: [java.lang.String] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:124:0x0148 -> B:39:0x01a9). Please report as a decompilation issue!!! */
    public final void readOngoingDismissExceptionPolicyJson(FileInputStream fileInputStream, boolean z) {
        BufferedReader bufferedReader;
        boolean z2;
        Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson");
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else {
                            sb.append(readLine);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader;
                        e.printStackTrace();
                        if (bufferedReader2 != null) {
                            try {
                                try {
                                    bufferedReader2.close();
                                } catch (Throwable th) {
                                    try {
                                        bufferedReader2.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                    throw th;
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                bufferedReader2.close();
                            }
                        }
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                    } catch (JSONException e5) {
                        e = e5;
                        bufferedReader2 = bufferedReader;
                        e.printStackTrace();
                        if (bufferedReader2 != null) {
                            try {
                                try {
                                    bufferedReader2.close();
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            } catch (IOException e6) {
                                e6.printStackTrace();
                                bufferedReader2.close();
                            }
                        }
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader2 = bufferedReader;
                        try {
                            if (bufferedReader2 != null) {
                                try {
                                    try {
                                        bufferedReader2.close();
                                    } finally {
                                        try {
                                            bufferedReader2.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                    bufferedReader2.close();
                                    throw th;
                                }
                            }
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                        throw th;
                    }
                }
                JSONObject jSONObject = new JSONObject(sb.toString());
                long j = jSONObject.getLong("policy_version");
                Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - current version = " + this.mOngoingDismissExceptionPolicyVersion + " new version = " + j);
                if (j > this.mOngoingDismissExceptionPolicyVersion) {
                    this.mOngoingDismissExceptionPolicyVersion = j;
                    JSONArray jSONArray = jSONObject.getJSONArray("keyList");
                    this.mOngoingDismissExceptionKeyList.clear();
                    int length = jSONArray.length();
                    boolean z3 = false;
                    int i = 0;
                    while (true) {
                        z2 = DBG;
                        if (i >= length) {
                            break;
                        }
                        this.mOngoingDismissExceptionKeyList.add(jSONArray.get(i).toString());
                        if (z2) {
                            Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - packageList = " + jSONArray.get(i).toString());
                        }
                        i++;
                    }
                    Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - size = " + length);
                    if (jSONObject.has("limitNotificationAppList")) {
                        JSONArray jSONArray2 = jSONObject.getJSONArray("limitNotificationAppList");
                        this.mLimitNotificationsForOverflowAppList.clear();
                        int length2 = jSONArray2.length();
                        int i2 = 0;
                        BufferedReader bufferedReader3 = length;
                        while (i2 < length2) {
                            ?? r3 = this.mLimitNotificationsForOverflowAppList;
                            r3.add(jSONArray2.get(i2).toString());
                            if (z2) {
                                r3 = "readOngoingDismissExceptionPolicyJson - limit notification List = " + jSONArray2.get(i2).toString();
                                Slog.d("NotificationService", (String) r3);
                            }
                            i2++;
                            bufferedReader3 = r3;
                        }
                        Slog.d("NotificationService", "readOngoingDismissExceptionPolicyJson - limit notification size = " + length2);
                        bufferedReader2 = bufferedReader3;
                    } else {
                        loadDefaultLimitNotificationsForOverflowAppList();
                        z3 = true;
                        bufferedReader2 = length;
                    }
                    if (z || z3) {
                        Slog.d("NotificationService", "handleSaveOngoingDismissExceptionPolicyFile");
                        Handler handler = IoThread.getHandler();
                        AnonymousClass11 anonymousClass11 = this.mSaveOngoingDismissExceptionPolicyFile;
                        if (!handler.hasCallbacks(anonymousClass11)) {
                            IoThread.getHandler().postDelayed(anonymousClass11, 250L);
                        }
                    }
                }
            } catch (IOException e10) {
                e = e10;
            } catch (JSONException e11) {
                e = e11;
            }
            try {
                try {
                    bufferedReader.close();
                    bufferedReader.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                    bufferedReader.close();
                }
            } catch (Throwable th4) {
                try {
                    bufferedReader.close();
                } catch (IOException e13) {
                    e13.printStackTrace();
                }
                throw th4;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0576 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0487  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:245:? -> B:241:0x03fd). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPolicyXml(int r26, java.io.InputStream r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 1475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.readPolicyXml(int, java.io.InputStream, boolean):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v19 */
    public final void readScpmNotificationPoliciesJson(FileInputStream fileInputStream, boolean z) {
        BufferedReader bufferedReader;
        boolean hasNext;
        Slog.d("NotificationService", "readScpmNotificationPoliciesJson");
        Boolean bool = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        String str = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
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
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            try {
                                if (bufferedReader2 != null) {
                                    try {
                                        bufferedReader2.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                        bufferedReader2.close();
                                    }
                                }
                                if (bufferedReader2 != null) {
                                    bufferedReader2.close();
                                }
                            } finally {
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            try {
                                if (bufferedReader2 != null) {
                                    try {
                                        bufferedReader2.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        bufferedReader2.close();
                                    }
                                }
                                if (bufferedReader2 != null) {
                                    bufferedReader2.close();
                                }
                            } finally {
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader2 = bufferedReader;
                            try {
                                try {
                                    if (bufferedReader2 != null) {
                                        try {
                                            bufferedReader2.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            bufferedReader2.close();
                                            throw th;
                                        }
                                    }
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                                throw th;
                            } finally {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                        }
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    long j = jSONObject.getLong("policy_version");
                    Slog.d("NotificationService", "readScpmNotificationPoliciesJson - current version = " + this.mScpmNotificationPoliciesVersion + " new version = " + j);
                    if (j > this.mScpmNotificationPoliciesVersion) {
                        this.mScpmNotificationPoliciesVersion = j;
                        JSONArray jSONArray = jSONObject.getJSONArray("ongoingActivityAllowList");
                        this.mOngoingActivityAppList.clear();
                        int length = jSONArray.length();
                        for (int i = 0; i < length; i++) {
                            this.mOngoingActivityAppList.add(jSONArray.get(i).toString());
                            if (DBG) {
                                Slog.d("NotificationService", "readScpmNotificationPoliciesJson - packageList = " + jSONArray.get(i).toString());
                            }
                        }
                        Slog.d("NotificationService", "readScpmNotificationPoliciesJson - size = " + length);
                        if (j == 1) {
                            String string = getContext().getResources().getString(R.string.config_wlan_network_service_package);
                            ArrayList arrayList = new ArrayList();
                            str = ":";
                            List list = arrayList;
                            if (string != null) {
                                arrayList.clear();
                                List list2 = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
                                Slog.d("NotificationService", "readScpmNotificationPoliciesJson default size = " + list2.size());
                                list = list2;
                            }
                            if (string != null && !list.equals(this.mOngoingActivityAppList)) {
                                Slog.d("NotificationService", "readScpmNotificationPoliciesJson list is different");
                                bool = Boolean.TRUE;
                                this.mOngoingActivityAppList.clear();
                                this.mOngoingActivityAppList = (List) Arrays.stream(string.split(":")).collect(Collectors.toList());
                            }
                        }
                        boolean z2 = str;
                        if (bool.booleanValue() || z) {
                            Slog.d("NotificationService", "readScpmNotificationPoliciesJson list is changed - needToUpdate = " + bool + " updateFile = " + z);
                            this.mPreferencesHelper.resetDefaultAllowOngoingActivityExceptGivenApps(this.mOngoingActivityAppList);
                            Iterator it = this.mOngoingActivityAppList.iterator();
                            while (true) {
                                hasNext = it.hasNext();
                                if (!hasNext) {
                                    break;
                                }
                                String str2 = (String) it.next();
                                int packageUid = this.mPackageManagerInternal.getPackageUid(str2, 0L, ActivityManager.getCurrentUser());
                                if (packageUid > 0) {
                                    int ongoingActivityAllowedState = this.mPreferencesHelper.getOngoingActivityAllowedState(packageUid, str2);
                                    Slog.d("NotificationService", "readScpmNotificationPoliciesJson - update pkg = " + str2 + " state = " + ongoingActivityAllowedState);
                                    if (ongoingActivityAllowedState < 0) {
                                        this.mPreferencesHelper.setAllowOngoingActivityState(packageUid, 1, str2);
                                    }
                                }
                            }
                            handleSavePolicyFile();
                            z2 = hasNext;
                        }
                        if (z) {
                            this.mPreferencesHelper.setOngoingActivityAppList(this.mOngoingActivityAppList);
                            this.mOngoingActivitySettingValue.clear();
                            Iterator it2 = this.mOngoingActivityAppList.iterator();
                            while (true) {
                                z2 = it2.hasNext();
                                if (z2 == 0) {
                                    break;
                                }
                                String str3 = (String) it2.next();
                                int packageUid2 = this.mPackageManagerInternal.getPackageUid(str3, 0L, ActivityManager.getCurrentUser());
                                if (packageUid2 > 0) {
                                    boolean z3 = this.mPreferencesHelper.getOngoingActivityAllowedState(packageUid2, str3) > 0;
                                    this.mOngoingActivitySettingValue.put(str3, Boolean.valueOf(z3));
                                    Slog.d("NotificationService", "readScpmNotificationPoliciesJson - installed pkg = " + str3 + " isOn = " + z3);
                                } else {
                                    this.mOngoingActivitySettingValue.put(str3, Boolean.TRUE);
                                    Slog.d("NotificationService", "readScpmNotificationPoliciesJson - not installed pkg = " + str3);
                                }
                            }
                        }
                        bufferedReader2 = z2;
                        if (bool.booleanValue() || z) {
                            Slog.d("NotificationService", "handleSaveScpmNotificationPoliciesFile");
                            Handler handler = IoThread.getHandler();
                            AnonymousClass11 anonymousClass11 = this.mSaveScpmNotificationPoliciesFile;
                            bufferedReader2 = z2;
                            if (!handler.hasCallbacks(anonymousClass11)) {
                                IoThread.getHandler().postDelayed(anonymousClass11, 250L);
                                bufferedReader2 = z2;
                            }
                        }
                    }
                } catch (IOException e8) {
                    e = e8;
                } catch (JSONException e9) {
                    e = e9;
                }
            } catch (IOException e10) {
                e10.printStackTrace();
            }
            try {
                try {
                    bufferedReader.close();
                    bufferedReader.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                    bufferedReader.close();
                }
            } catch (Throwable th2) {
                try {
                    bufferedReader.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final void registerConversationAppPolicyScpm() {
        Slog.d("NotificationService", "registerConversationAppPolicyScpm");
        if (isScpmAvailable()) {
            try {
                Bundle scpmBundle = getScpmBundle(null, Uri.parse("content://com.samsung.android.scpm.policy/"), 0);
                if (scpmBundle != null) {
                    int i = scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1);
                    String string = scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                    int i2 = scpmBundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                    String string2 = scpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                    if (i == 1) {
                        SharedPreferences.Editor edit = getContext().getSharedPreferences("conversation_app_ploicy_pref", 0).edit();
                        edit.putString("ConversationAppPloicyToken", string);
                        edit.commit();
                        Slog.d("NotificationService", "registerConversationAppPolicyScpm - success");
                    } else {
                        Slog.e("NotificationService", "failed to registerConversationAppPolicyScpm: rcode = " + i2 + ", rmsg = " + string2);
                    }
                }
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("cannot registerConversationAppPolicyScpm : "), "NotificationService");
            }
        }
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

    public final boolean removeFromNotificationListsLocked(NotificationRecord notificationRecord) {
        boolean z;
        NotificationRecord findNotificationByListLocked = findNotificationByListLocked(notificationRecord.sbn.getKey(), this.mNotificationList);
        if (findNotificationByListLocked != null) {
            this.mNotificationList.remove(findNotificationByListLocked);
            this.mNotificationsByKey.remove(findNotificationByListLocked.sbn.getKey());
            z = true;
        } else {
            z = false;
        }
        while (true) {
            NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(notificationRecord.sbn.getKey(), this.mEnqueuedNotifications);
            if (findNotificationByListLocked2 == null) {
                return z;
            }
            this.mEnqueuedNotifications.remove(findNotificationByListLocked2);
        }
    }

    public final boolean removeRemoteView(String str, String str2, int i, RemoteViews remoteViews) {
        if (remoteViews == null) {
            return false;
        }
        int estimateMemoryUsage = remoteViews.estimateMemoryUsage();
        if (estimateMemoryUsage > this.mWarnRemoteViewsSizeBytes && estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("RemoteViews too large on pkg: ", str, " tag: ", str2, " id: "), i, " this might be stripped in a future release", "NotificationService");
        }
        if (estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            return false;
        }
        this.mUsageStats.registerImageRemoved(str);
        StringBuilder sb = new StringBuilder("Removed too large RemoteViews (");
        sb.append(estimateMemoryUsage);
        sb.append(" bytes) on pkg: ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str, " tag: ", str2, " id: ");
        HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, i, "NotificationService");
        return true;
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

    public final void reportSeen(NotificationRecord notificationRecord) {
        if (!Objects.equals(notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getOpPkg())) {
            return;
        }
        UsageStatsManagerInternal usageStatsManagerInternal = this.mAppUsageStats;
        String packageName = notificationRecord.sbn.getPackageName();
        int userId = notificationRecord.sbn.getUserId();
        if (userId == -1) {
            userId = 0;
        }
        usageStatsManagerInternal.reportEvent(userId, 10, packageName);
    }

    public final void reportUserInteraction(NotificationRecord notificationRecord) {
        UsageStatsManagerInternal usageStatsManagerInternal = this.mAppUsageStats;
        String packageName = notificationRecord.sbn.getPackageName();
        int userId = notificationRecord.sbn.getUserId();
        if (userId == -1) {
            userId = 0;
        }
        usageStatsManagerInternal.reportEvent(userId, 7, packageName);
        Flags.politeNotifications();
    }

    public void resetAssistantUserSet(int i) {
        checkCallerIsSystemOrShell();
        this.mAssistants.mIsUserChanged.put(Integer.valueOf(i), Boolean.FALSE);
        handleSavePolicyFile();
    }

    public void resetDefaultDndIfNecessary() {
        Iterator it;
        boolean z;
        Iterator it2;
        String[] strArr;
        boolean z2;
        ArraySet arraySet;
        Iterator it3 = this.mUm.getAliveUsers().iterator();
        boolean z3 = false;
        while (it3.hasNext()) {
            int identifier = ((UserInfo) it3.next()).getUserHandle().getIdentifier();
            if (!isDNDMigrationDone(identifier)) {
                ConditionProviders conditionProviders = this.mConditionProviders;
                String string = conditionProviders.mContext.getResources().getString(R.string.days);
                if (string != null) {
                    String[] split = string.split(":");
                    int i = 0;
                    z = false;
                    while (i < split.length) {
                        if (TextUtils.isEmpty(split[i])) {
                            it2 = it3;
                            strArr = split;
                        } else {
                            String str = split[i];
                            synchronized (conditionProviders.mApproved) {
                                try {
                                    ArrayMap arrayMap = (ArrayMap) conditionProviders.mApproved.get(Integer.valueOf(identifier));
                                    if (arrayMap != null) {
                                        int size = arrayMap.size();
                                        int i2 = 0;
                                        z2 = false;
                                        while (i2 < size) {
                                            ArraySet arraySet2 = (ArraySet) arrayMap.valueAt(i2);
                                            Iterator it4 = it3;
                                            int size2 = arraySet2.size() - 1;
                                            while (size2 >= 0) {
                                                String[] strArr2 = split;
                                                String str2 = (String) arraySet2.valueAt(size2);
                                                ArrayMap arrayMap2 = arrayMap;
                                                if (TextUtils.equals(str, ManagedServices.getPackageName(str2))) {
                                                    arraySet2.removeAt(size2);
                                                    if (conditionProviders.DEBUG) {
                                                        String str3 = conditionProviders.TAG;
                                                        StringBuilder sb = new StringBuilder();
                                                        arraySet = arraySet2;
                                                        sb.append("Removing ");
                                                        sb.append(str2);
                                                        sb.append(" from approved list; ");
                                                        sb.append("remove from config");
                                                        Slog.v(str3, sb.toString());
                                                    } else {
                                                        arraySet = arraySet2;
                                                    }
                                                    z2 = true;
                                                } else {
                                                    arraySet = arraySet2;
                                                }
                                                size2--;
                                                arrayMap = arrayMap2;
                                                split = strArr2;
                                                arraySet2 = arraySet;
                                            }
                                            i2++;
                                            it3 = it4;
                                        }
                                        it2 = it3;
                                        strArr = split;
                                    } else {
                                        it2 = it3;
                                        strArr = split;
                                        z2 = false;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            z |= z2;
                        }
                        i++;
                        it3 = it2;
                        split = strArr;
                    }
                    it = it3;
                } else {
                    it = it3;
                    z = false;
                }
                z3 |= z;
                ConditionProviders conditionProviders2 = this.mConditionProviders;
                synchronized (conditionProviders2.mDefaultsLock) {
                    conditionProviders2.mDefaultComponents.clear();
                    conditionProviders2.mDefaultPackages.clear();
                }
                conditionProviders2.loadDefaultsFromConfig();
                allowDndPackages(identifier);
                it3 = it;
            }
        }
        if (z3) {
            handleSavePolicyFile();
        }
    }

    public int resolveNotificationUid(String str, String str2, int i, int i2) throws PackageManager.NameNotFoundException {
        if (i2 == -1) {
            i2 = 0;
        }
        try {
            checkCallerIsSameApp(i, i2, str2);
            if (!TextUtils.equals(str, str2)) {
                checkCallerIsSameApp(i, i2, str);
            }
            return i;
        } catch (SecurityException unused) {
            int packageUidAsUser = this.mPackageManagerClient.getPackageUidAsUser(str2, i2);
            if ((isUidSystemOrPhone(i) && str != null && "android".equals(str)) || this.mPreferencesHelper.isDelegateAllowed(packageUidAsUser, i, str2, str)) {
                return packageUidAsUser;
            }
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "Caller ", str, ":", " cannot post for pkg ");
            m.append(str2);
            m.append(" in user ");
            m.append(i2);
            throw new SecurityException(m.toString());
        }
    }

    public final void revokeUriPermission(IBinder iBinder, Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((UriGrantsManagerService.LocalService) this.mUgmInternal).revokeUriPermissionFromOwner(iBinder, ContentProvider.getUriWithoutUserId(uri), 1, userIdFromUri, str, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void scheduleTimeoutLocked(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification().getTimeoutAfter() > 0) {
            PendingIntent notificationTimeoutPendingIntent = getNotificationTimeoutPendingIntent(notificationRecord, 134217728);
            PendingIntent pendingIntent = (PendingIntent) this.mTimeoutPendingIntent.get(notificationRecord.sbn.getKey());
            if (pendingIntent != null) {
                this.mTimeoutPendingIntent.remove(notificationRecord.sbn.getKey());
                this.mAlarmManager.cancel(pendingIntent);
            }
            this.mTimeoutPendingIntent.put(notificationRecord.sbn.getKey(), notificationTimeoutPendingIntent);
            this.mAlarmManager.setExactAndAllowWhileIdle(2, notificationRecord.sbn.getNotification().getTimeoutAfter() + SystemClock.elapsedRealtime(), notificationTimeoutPendingIntent);
        }
    }

    public final void sendRegisteredOnlyBroadcast(Intent intent) {
        int[] profileIds = this.mUmInternal.getProfileIds(this.mAmi.getCurrentUserId(), true);
        Intent addFlags = new Intent(intent).addFlags(1073741824);
        for (int i : profileIds) {
            getContext().sendBroadcastAsUser(addFlags, UserHandle.of(i), null);
        }
        for (int i2 : profileIds) {
            Iterator it = ((ArrayList) this.mConditionProviders.getAllowedPackages(i2)).iterator();
            while (it.hasNext()) {
                getContext().sendBroadcastAsUser(new Intent(intent).setPackage((String) it.next()).setFlags(67108864), UserHandle.of(i2));
            }
        }
    }

    public void setAttentionHelper(NotificationAttentionHelper notificationAttentionHelper) {
        this.mAttentionHelper = notificationAttentionHelper;
    }

    public void setDNDMigrationDone(int i) {
        Settings.Secure.putIntForUser(getContext().getContentResolver(), "dnd_settings_migrated", 1, i);
    }

    public final void setDefaultAssistantForUser(int i) {
        ArraySet arraySet;
        NotificationAssistants notificationAssistants = this.mAssistants;
        synchronized (notificationAssistants.mDefaultsLock) {
            arraySet = new ArraySet(notificationAssistants.mDefaultComponents);
        }
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            ComponentName componentName = (ComponentName) arraySet.valueAt(i2);
            ArraySet queryPackageForServices = this.mAssistants.queryPackageForServices(786432, i, null);
            if (componentName != null && queryPackageForServices.contains(componentName)) {
                setNotificationAssistantAccessGrantedForUserInternal(componentName, i, true, false);
                return;
            }
        }
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mHandler = workerHandler;
    }

    public void setIsTelevision(boolean z) {
        this.mIsTelevision = z;
    }

    public void setLockPatternUtils(LockPatternUtils lockPatternUtils) {
        this.mLockUtils = lockPatternUtils;
    }

    public void setNASMigrationDone(int i) {
        for (int i2 : this.mUm.getProfileIds(i, false)) {
            Settings.Secure.putIntForUser(getContext().getContentResolver(), "nas_settings_updated", 1, i2);
        }
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
                } else {
                    if (z) {
                        NotificationManagerService$$ExternalSyntheticLambda6 notificationManagerService$$ExternalSyntheticLambda6 = this.mAllowedManagedServicePackages;
                        String packageName = componentName.getPackageName();
                        Integer valueOf = Integer.valueOf(i2);
                        this.mAssistants.getClass();
                        if (notificationManagerService$$ExternalSyntheticLambda6.f$0.canUseManagedServices(packageName, valueOf, "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE")) {
                        }
                    }
                    this.mConditionProviders.setPackageOrComponentEnabled(i2, componentName.flattenToString(), false, z, true);
                    this.mAssistants.setPackageOrComponentEnabled(i2, componentName.flattenToString(), true, z, z2);
                    getContext().sendBroadcastAsUser(new Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), UserHandle.of(i2), null);
                    handleSavePolicyFile();
                }
            }
        }
    }

    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    public void setRankingHelper(RankingHelper rankingHelper) {
        this.mRankingHelper = rankingHelper;
    }

    public void setShortcutHelper(ShortcutHelper shortcutHelper) {
        this.mShortcutHelper = shortcutHelper;
    }

    public void setStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        this.mStrongAuthTracker = strongAuthTracker;
    }

    public void setTelecomManager(TelecomManager telecomManager) {
        this.mTelecomManager = telecomManager;
    }

    public void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x003d, code lost:
    
        if (r18.mPackageManager.checkPermission("android.permission.UNLIMITED_TOASTS", r8, r4) == 0) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0093 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showNextToastLocked(boolean r19) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.showNextToastLocked(boolean):void");
    }

    public final void snoozeNotificationInt(int i, INotificationListener iNotificationListener, String str, long j, String str2) {
        synchronized (this.mNotificationLock) {
            try {
                ManagedServices.ManagedServiceInfo checkServiceTokenLocked = this.mListeners.checkServiceTokenLocked(iNotificationListener);
                String packageName = checkServiceTokenLocked.component.getPackageName();
                String shortString = checkServiceTokenLocked.component.toShortString();
                if ((j > 0 || str2 != null) && str != null) {
                    NotificationRecord findNotificationByKeyLocked = findNotificationByKeyLocked(str);
                    if (findNotificationByKeyLocked == null) {
                        SnoozeHelper snoozeHelper = this.mSnoozeHelper;
                        synchronized (snoozeHelper.mLock) {
                            findNotificationByKeyLocked = (NotificationRecord) snoozeHelper.mSnoozedNotifications.get(str);
                        }
                    }
                    if (findNotificationByKeyLocked == null) {
                        return;
                    }
                    if (checkServiceTokenLocked.enabledAndUserMatches(findNotificationByKeyLocked.sbn.getNormalizedUserId())) {
                        long j2 = findNotificationByKeyLocked.mUpdateTimeMs;
                        if (DBG) {
                            Slog.d("NotificationService", String.format("snooze event(%s, %d, %s, %s)", str, Long.valueOf(j), str2, shortString));
                        }
                        this.mHandler.post(new SnoozeNotificationRunnable(str, j, str2));
                        if (isNotificationRecent(j2)) {
                            this.mAppOps.noteOpNoThrow(142, i, packageName, (String) null, (String) null);
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void unsnoozeNotificationInt(String str, ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
        String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
        if (DBG) {
            Slog.d("NotificationService", XmlUtils$$ExternalSyntheticOutline0.m("unsnooze event(", str, ", ", shortString, ")"));
        }
        SnoozeHelper snoozeHelper = this.mSnoozeHelper;
        synchronized (snoozeHelper.mLock) {
            try {
                NotificationRecord notificationRecord = (NotificationRecord) snoozeHelper.mSnoozedNotifications.get(str);
                if (notificationRecord != null) {
                    notificationRecord.sbn.getUserId();
                    snoozeHelper.repost(str, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        handleSavePolicyFile();
    }

    public final void updateAutobundledSummaryLocked(int i, String str, GroupHelper.NotificationAttributes notificationAttributes, boolean z) {
        String str2;
        NotificationRecord notificationRecord;
        ArrayMap arrayMap = (ArrayMap) this.mAutobundledSummaries.get(Integer.valueOf(i));
        if (arrayMap == null || (str2 = (String) arrayMap.get(str)) == null || (notificationRecord = (NotificationRecord) this.mNotificationsByKey.get(str2)) == null) {
            return;
        }
        int i2 = notificationRecord.sbn.getNotification().flags;
        boolean sameAs = notificationAttributes.icon.sameAs(notificationRecord.sbn.getNotification().getSmallIcon());
        int i3 = notificationAttributes.visibility;
        int i4 = notificationAttributes.iconColor;
        boolean z2 = (sameAs && i4 == notificationRecord.sbn.getNotification().color && i3 == notificationRecord.sbn.getNotification().visibility) ? false : true;
        int i5 = notificationAttributes.flags;
        if (i2 != i5 || z2) {
            Notification notification = notificationRecord.sbn.getNotification();
            if (i5 != -1) {
                i2 = i5;
            }
            notification.flags = i2;
            notificationRecord.sbn.getNotification().setSmallIcon(notificationAttributes.icon);
            notificationRecord.sbn.getNotification().color = i4;
            notificationRecord.sbn.getNotification().visibility = i3;
            WorkerHandler workerHandler = this.mHandler;
            this.mPostNotificationTrackerFactory.getClass();
            workerHandler.post(new EnqueueNotificationRunnable(i, notificationRecord, z, new PostNotificationTracker(null)));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00c9, code lost:
    
        if (r18.getImportance() == 0) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x010b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateNotificationChannelInt(java.lang.String r16, int r17, android.app.NotificationChannel r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationManagerService.updateNotificationChannelInt(java.lang.String, int, android.app.NotificationChannel, boolean):void");
    }

    public void updateUriPermissions(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, String str, int i) {
        updateUriPermissions(notificationRecord, notificationRecord2, str, i, false);
    }

    public void updateUriPermissions(NotificationRecord notificationRecord, NotificationRecord notificationRecord2, String str, int i, boolean z) {
        IBinder iBinder;
        int i2;
        String key = (notificationRecord != null ? notificationRecord.sbn : notificationRecord2.sbn).getKey();
        boolean z2 = DBG;
        if (z2) {
            Slog.d("NotificationService", key + ": updating permissions");
        }
        ArraySet arraySet = notificationRecord != null ? notificationRecord.mGrantableUris : null;
        ArraySet arraySet2 = notificationRecord2 != null ? notificationRecord2.mGrantableUris : null;
        if (arraySet == null && arraySet2 == null) {
            return;
        }
        IBinder iBinder2 = notificationRecord != null ? notificationRecord.permissionOwner : null;
        if (notificationRecord2 != null && iBinder2 == null) {
            iBinder2 = notificationRecord2.permissionOwner;
        }
        if (arraySet != null && iBinder2 == null) {
            if (z2) {
                Slog.d("NotificationService", key + ": creating owner");
            }
            iBinder2 = ((UriGrantsManagerService.LocalService) this.mUgmInternal).newUriPermissionOwner(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("NOTIF:", key));
        }
        if (arraySet != null || iBinder2 == null || z) {
            iBinder = iBinder2;
        } else {
            destroyPermissionOwner(UserHandle.getUserId(notificationRecord2.sbn.getUid()), iBinder2, key);
            iBinder = null;
        }
        if (arraySet != null && iBinder != null) {
            int i3 = 0;
            while (i3 < arraySet.size()) {
                Uri uri = (Uri) arraySet.valueAt(i3);
                if (arraySet2 == null || !arraySet2.contains(uri)) {
                    if (z2) {
                        Slog.d("NotificationService", key + ": granting " + uri);
                    }
                    i2 = i3;
                    grantUriPermission(iBinder, uri, notificationRecord.sbn.getUid(), str, i);
                } else {
                    i2 = i3;
                }
                i3 = i2 + 1;
            }
        }
        if (arraySet2 != null && iBinder != null) {
            for (int i4 = 0; i4 < arraySet2.size(); i4++) {
                Uri uri2 = (Uri) arraySet2.valueAt(i4);
                if (arraySet == null || !arraySet.contains(uri2)) {
                    if (z2) {
                        Slog.d("NotificationService", key + ": revoking " + uri2);
                    }
                    if (z) {
                        revokeUriPermission(iBinder, uri2, UserHandle.getUserId(notificationRecord2.sbn.getUid()), str, i);
                    } else {
                        revokeUriPermission(iBinder, uri2, UserHandle.getUserId(notificationRecord2.sbn.getUid()), null, -1);
                    }
                }
            }
        }
        if (notificationRecord != null) {
            notificationRecord.permissionOwner = iBinder;
        }
    }
}
