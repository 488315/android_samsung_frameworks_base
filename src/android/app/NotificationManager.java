package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AutomaticZenRule;
import android.app.ICallNotificationEventCallback;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Person;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IpcDataCache;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.RateEstimator;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LruCache;
import android.util.NtpTrustedTime;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.notification.NotificationChannelGroupsHelper;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.time.Instant;
import java.time.InstantSource;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class NotificationManager {
    public static final String ACTION_APP_BLOCK_STATE_CHANGED = "android.app.action.APP_BLOCK_STATE_CHANGED";
    public static final String ACTION_AUTOMATIC_ZEN_RULE = "android.app.action.AUTOMATIC_ZEN_RULE";
    public static final String ACTION_AUTOMATIC_ZEN_RULE_STATUS_CHANGED = "android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED";

    @SystemApi
    public static final String ACTION_CLOSE_NOTIFICATION_HANDLER_PANEL = "android.app.action.CLOSE_NOTIFICATION_HANDLER_PANEL";
    public static final String ACTION_CONSOLIDATED_NOTIFICATION_POLICY_CHANGED = "android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED";
    public static final String ACTION_EFFECTS_SUPPRESSOR_CHANGED = "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED_INTERNAL = "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL";
    public static final String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED";
    public static final String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String ACTION_NOTIFICATION_LISTENER_ENABLED_CHANGED = "android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";

    @SystemApi
    public static final String ACTION_OPEN_NOTIFICATION_HANDLER_PANEL = "android.app.action.OPEN_NOTIFICATION_HANDLER_PANEL";

    @SystemApi
    public static final String ACTION_TOGGLE_NOTIFICATION_HANDLER_PANEL = "android.app.action.TOGGLE_NOTIFICATION_HANDLER_PANEL";
    public static final int AUTOMATIC_RULE_STATUS_ACTIVATED = 4;
    public static final int AUTOMATIC_RULE_STATUS_DEACTIVATED = 5;
    public static final int AUTOMATIC_RULE_STATUS_DISABLED = 2;
    public static final int AUTOMATIC_RULE_STATUS_ENABLED = 1;
    public static final int AUTOMATIC_RULE_STATUS_REMOVED = 3;
    public static final int AUTOMATIC_RULE_STATUS_UNKNOWN = -1;
    public static final int BUBBLE_PREFERENCE_ALL = 1;
    public static final int BUBBLE_PREFERENCE_NONE = 0;
    public static final int BUBBLE_PREFERENCE_SELECTED = 2;
    private static final long DELAY_FOR_OVERFLOW = 10000;
    private static final long DELAY_FOR_OVERFLOW_SUMMARY = 200;
    public static final String EXTRA_AUTOMATIC_RULE_ID = "android.app.extra.AUTOMATIC_RULE_ID";
    public static final String EXTRA_AUTOMATIC_ZEN_RULE_ID = "android.app.extra.AUTOMATIC_ZEN_RULE_ID";
    public static final String EXTRA_AUTOMATIC_ZEN_RULE_STATUS = "android.app.extra.AUTOMATIC_ZEN_RULE_STATUS";
    public static final String EXTRA_BLOCKED_STATE = "android.app.extra.BLOCKED_STATE";
    public static final String EXTRA_NOTIFICATION_CHANNEL_GROUP_ID = "android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID";
    public static final String EXTRA_NOTIFICATION_CHANNEL_ID = "android.app.extra.NOTIFICATION_CHANNEL_ID";
    public static final String EXTRA_NOTIFICATION_POLICY = "android.app.extra.NOTIFICATION_POLICY";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    private static final int KNOWN_STATUS_CANCELLED = 2;
    private static final int KNOWN_STATUS_ENQUEUED = 1;
    private static final float MAX_NOTIFICATION_UNNECESSARY_CANCEL_RATE = 5.0f;
    private static final float MAX_NOTIFICATION_UPDATE_RATE = 5.0f;
    public static int MAX_SERVICE_COMPONENT_NAME_LENGTH = 500;
    public static final String META_DATA_AUTOMATIC_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    private static final String NOTIFICATION_CHANNELS_CACHE_API = "getNotificationChannels";
    private static final int NOTIFICATION_CHANNELS_CACHE_SIZE = 10;
    private static final String NOTIFICATION_CHANNEL_GROUPS_CACHE_API = "getNotificationChannelGroups";
    private static final int NOTIFICATION_CHANNEL_GROUPS_CACHE_SIZE = 10;
    public static final int SEM_NOTIFICATION_HISTORY_TYPE_GENERAL = 0;
    public static final int SEM_NOTIFICATION_HISTORY_TYPE_IMAGE = 2;
    public static final int SEM_NOTIFICATION_HISTORY_TYPE_REPLY = 1;
    public static final int SEM_ZEN_MODE_ALARMS = 3;
    public static final int SEM_ZEN_MODE_IMPORTANT_INTERRUPTIONS = 1;
    public static final int SEM_ZEN_MODE_NO_INTERRUPTIONS = 2;
    public static final int SEM_ZEN_MODE_OFF = 0;
    public static final int SEM_ZEN_MODE_UNKNOWN = -1;
    public static final long SET_LISTENER_ACCESS_GRANTED_IS_USER_AWARE = 302563478;
    private static String TAG = "NotificationManager";
    public static final int VISIBILITY_NO_OVERRIDE = -1000;
    private static boolean localLOGV = false;
    private static INotificationManager sService;
    private List<String> mBlockedChannelsForOverflowNoti;
    private final Map<CallNotificationEventListener, CallNotificationEventCallbackStub> mCallNotificationEventCallbacks;
    private final InstantSource mClock;
    private final Context mContext;
    private EdgeNotificationManager mEdgeNotificationManager;
    private Handler mHandler;
    private final LruCache<NotificationKey, Integer> mKnownNotifications;
    final Object mNMLock;
    private final IpcDataCache<String, Map<String, NotificationChannelGroup>> mNotificationChannelGroupsCache;
    private final IpcDataCache.QueryHandler<String, Map<String, NotificationChannelGroup>> mNotificationChannelGroupsQueryHandler;
    private final IpcDataCache<NotificationChannelQuery, List<NotificationChannel>> mNotificationChannelListCache;
    private final IpcDataCache.QueryHandler<NotificationChannelQuery, List<NotificationChannel>> mNotificationChannelListQueryHandler;
    private ConcurrentHashMap<String, Long> mOverflowChildUpdateTimeMap;
    private ConcurrentHashMap<String, Long> mOverflowNotiUpdateTimeMap;
    private ReNotifyRunnable mReNotifyRunnable;
    private final Object mThrottleLock;
    private final RateLimiter mUnnecessaryCancelRateLimiter;
    private final RateLimiter mUpdateRateLimiter;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutomaticZenRuleStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BubblePreference {
    }

    @SystemApi
    public interface CallNotificationEventListener {
        void onCallNotificationPosted(String str, UserHandle userHandle);

        void onCallNotificationRemoved(String str, UserHandle userHandle);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Importance {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InterruptionFilter {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemZenMode {
    }

    public static int zenModeFromInterruptionFilter(int i, int i2) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i != 4) {
            return i2;
        }
        return 3;
    }

    public static int zenModeToInterruptionFilter(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i != 2) {
            return i != 3 ? 0 : 4;
        }
        return 3;
    }

    private final class ReNotifyRunnable implements Runnable {
        private int mId;
        private Notification mNotification;
        private String mPkg;
        private String mTag;
        private long mUpdateTime;
        private UserHandle mUser;

        public ReNotifyRunnable(String str, String str2, int i, Notification notification, UserHandle userHandle, long j) {
            this.mPkg = str;
            this.mTag = str2;
            this.mId = i;
            this.mNotification = notification;
            this.mUser = userHandle;
            this.mUpdateTime = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (NotificationManager.this.mNMLock) {
                INotificationManager service = NotificationManager.getService();
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    String str = this.mUser.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mPkg + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mId + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mTag;
                    String str2 = this.mUser.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mPkg + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mNotification.getGroup();
                    if (this.mNotification.isGroupSummary() && NotificationManager.this.mOverflowChildUpdateTimeMap.containsKey(str2)) {
                        long longValue = currentTimeMillis - ((Long) NotificationManager.this.mOverflowChildUpdateTimeMap.get(str2)).longValue();
                        if (longValue > 400) {
                            Slog.d(NotificationManager.TAG, "summary is blocked by limit notification for overflow diffTime=" + longValue);
                            return;
                        }
                    } else {
                        NotificationManager.this.mOverflowChildUpdateTimeMap.put(str2, Long.valueOf(this.mUpdateTime));
                    }
                    NotificationManager.this.mOverflowNotiUpdateTimeMap.put(str, Long.valueOf(this.mUpdateTime));
                    Slog.d(NotificationManager.TAG, "received notification posted with delay. pkg=" + this.mPkg + " update lastUpdateTime=" + this.mUpdateTime);
                    service.enqueueNotificationWithTag(this.mPkg, NotificationManager.this.mContext.getOpPackageName(), this.mTag, this.mId, NotificationManager.this.fixNotification(this.mNotification), this.mUser.getIdentifier());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public static INotificationManager getService() {
        INotificationManager iNotificationManager = sService;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        sService = asInterface;
        return asInterface;
    }

    protected INotificationManager service() {
        return getService();
    }

    public NotificationManager(Context context) {
        this(context, SystemClock.elapsedRealtimeClock());
    }

    public NotificationManager(Context context, InstantSource instantSource) {
        this.mCallNotificationEventCallbacks = new HashMap();
        this.mUpdateRateLimiter = new RateLimiter("notify (update)", "notifications.value_client_throttled_notify_update", 5.0f);
        this.mUnnecessaryCancelRateLimiter = new RateLimiter("cancel (dupe)", "notifications.value_client_throttled_cancel_duplicate", 5.0f);
        this.mKnownNotifications = new LruCache<>(100);
        this.mThrottleLock = new Object();
        this.mBlockedChannelsForOverflowNoti = null;
        this.mOverflowNotiUpdateTimeMap = new ConcurrentHashMap<>();
        this.mOverflowChildUpdateTimeMap = new ConcurrentHashMap<>();
        this.mNMLock = new Object();
        this.mReNotifyRunnable = null;
        this.mHandler = new Handler(Looper.getMainLooper());
        IpcDataCache.QueryHandler<NotificationChannelQuery, List<NotificationChannel>> queryHandler = new IpcDataCache.QueryHandler<NotificationChannelQuery, List<NotificationChannel>>() { // from class: android.app.NotificationManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public List<NotificationChannel> apply(NotificationChannelQuery notificationChannelQuery) {
                try {
                    return NotificationManager.this.service().getNotificationChannels(notificationChannelQuery.callingPkg, notificationChannelQuery.targetPkg, notificationChannelQuery.userId).getList();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(NotificationChannelQuery notificationChannelQuery) {
                if (Flags.nmBinderPerfCacheChannels()) {
                    return false;
                }
                Log.wtf(NotificationManager.TAG, "shouldBypassCache called when nm_binder_perf_cache_channels off");
                return true;
            }
        };
        this.mNotificationChannelListQueryHandler = queryHandler;
        this.mNotificationChannelListCache = new IpcDataCache<>(10, "system_server", NOTIFICATION_CHANNELS_CACHE_API, NOTIFICATION_CHANNELS_CACHE_API, queryHandler);
        IpcDataCache.QueryHandler<String, Map<String, NotificationChannelGroup>> queryHandler2 = new IpcDataCache.QueryHandler<String, Map<String, NotificationChannelGroup>>() { // from class: android.app.NotificationManager.2
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Map<String, NotificationChannelGroup> apply(String str) {
                INotificationManager service = NotificationManager.this.service();
                ArrayMap arrayMap = new ArrayMap();
                try {
                    ParceledListSlice notificationChannelGroupsWithoutChannels = service.getNotificationChannelGroupsWithoutChannels(str);
                    if (notificationChannelGroupsWithoutChannels != null) {
                        for (NotificationChannelGroup notificationChannelGroup : notificationChannelGroupsWithoutChannels.getList()) {
                            arrayMap.put(notificationChannelGroup.getId(), notificationChannelGroup);
                        }
                    }
                    return arrayMap;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(String str) {
                if (Flags.nmBinderPerfCacheChannels()) {
                    return false;
                }
                Log.wtf(NotificationManager.TAG, "shouldBypassCache called when nm_binder_perf_cache_channels off");
                return true;
            }
        };
        this.mNotificationChannelGroupsQueryHandler = queryHandler2;
        this.mNotificationChannelGroupsCache = new IpcDataCache<>(10, "system_server", NOTIFICATION_CHANNEL_GROUPS_CACHE_API, NOTIFICATION_CHANNEL_GROUPS_CACHE_API, queryHandler2);
        this.mContext = context;
        this.mClock = instantSource;
    }

    public static NotificationManager from(Context context) {
        return (NotificationManager) context.getSystemService("notification");
    }

    public void notify(int i, Notification notification) {
        notify(null, i, notification);
    }

    public void notify(String str, int i, Notification notification) {
        notifyAsUser(str, i, notification, this.mContext.getUser());
    }

    public void notifyAsPackage(String str, String str2, int i, Notification notification) {
        INotificationManager service = service();
        String packageName = this.mContext.getPackageName();
        if (discardNotify(this.mContext.getUser(), str, str2, i, notification)) {
            return;
        }
        try {
            Slog.i(TAG, packageName + ": notify(" + i + ", " + str2 + ", " + notification + ") as package");
            service.enqueueNotificationWithTag(str, packageName, str2, i, fixNotification(notification), this.mContext.getUser().getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAsUser(String str, int i, Notification notification, UserHandle userHandle) {
        if (this.mEdgeNotificationManager != null) {
            if (notification.extras != null) {
                if (notification.extras.getBoolean("samsung.people.notify_to_edge")) {
                    this.mEdgeNotificationManager.postEdgeNotification(i, notification.extras);
                    return;
                } else if (notification.extras.getBoolean("samsung.people.cancel_to_edge")) {
                    this.mEdgeNotificationManager.removeEdgeNotification(i, notification.extras);
                    return;
                }
            }
            this.mEdgeNotificationManager.postEdgeNotificationByNormal(i, notification);
        }
        INotificationManager service = service();
        String packageName = this.mContext.getPackageName();
        if (discardNotify(userHandle, packageName, str, i, notification)) {
            return;
        }
        try {
            Slog.i(TAG, packageName + ": notify(" + i + ", " + str + ", " + notification + ") as user");
            if (this.mBlockedChannelsForOverflowNoti == null) {
                try {
                    this.mBlockedChannelsForOverflowNoti = service.getBlockInfoOfNotificationsForOverflow(this.mContext.getPackageName());
                    Slog.d(TAG, "BOOTING pkg =" + packageName + " mBlockedChannelsForOverflowNoti=" + this.mBlockedChannelsForOverflowNoti);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            List<String> list = this.mBlockedChannelsForOverflowNoti;
            if (list != null && !list.isEmpty()) {
                String str2 = userHandle.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + packageName + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + i + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + str;
                String str3 = userHandle.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + packageName + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + notification.getGroup();
                long currentTimeMillis = System.currentTimeMillis();
                if (this.mOverflowNotiUpdateTimeMap.containsKey(str2)) {
                    if (this.mHandler.hasCallbacks(this.mReNotifyRunnable)) {
                        this.mHandler.removeCallbacks(this.mReNotifyRunnable);
                    }
                    long longValue = currentTimeMillis - this.mOverflowNotiUpdateTimeMap.get(str2).longValue();
                    if (longValue < 10000) {
                        this.mReNotifyRunnable = new ReNotifyRunnable(packageName, str, i, notification, userHandle, currentTimeMillis);
                        if (notification.isGroupSummary()) {
                            this.mHandler.postDelayed(this.mReNotifyRunnable, 10200 - longValue);
                            return;
                        } else {
                            this.mHandler.postDelayed(this.mReNotifyRunnable, 10000 - longValue);
                            return;
                        }
                    }
                    Slog.d(TAG, "The time to post with delay has passed. pkg =" + packageName);
                    if (!notification.isGroupSummary()) {
                        this.mOverflowChildUpdateTimeMap.put(str3, Long.valueOf(currentTimeMillis));
                    }
                    this.mOverflowNotiUpdateTimeMap.put(str2, Long.valueOf(currentTimeMillis));
                } else {
                    Slog.d(TAG, "received first notification to check overflow. pkg =" + packageName);
                    if (!notification.isGroupSummary()) {
                        this.mOverflowChildUpdateTimeMap.put(str3, Long.valueOf(currentTimeMillis));
                    }
                    this.mOverflowNotiUpdateTimeMap.put(str2, Long.valueOf(currentTimeMillis));
                }
            }
            service.enqueueNotificationWithTag(packageName, this.mContext.getOpPackageName(), str, i, fixNotification(notification), userHandle.getIdentifier());
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private boolean discardNotify(UserHandle userHandle, String str, String str2, int i, Notification notification) {
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationClassification() && NotificationChannel.SYSTEM_RESERVED_IDS.contains(notification.getChannelId())) {
            return true;
        }
        if (!Flags.nmBinderPerfThrottleNotify()) {
            return false;
        }
        NotificationKey notificationKey = new NotificationKey(userHandle, str, str2, i);
        synchronized (this.mThrottleLock) {
            Integer num = this.mKnownNotifications.get(notificationKey);
            if (num != null && num.intValue() == 1 && !notification.hasCompletedProgress()) {
                if (this.mUpdateRateLimiter.eventExceedsRate()) {
                    this.mUpdateRateLimiter.recordRejected(notificationKey);
                    return true;
                }
                this.mUpdateRateLimiter.recordAccepted();
            }
            this.mKnownNotifications.put(notificationKey, 1);
            return false;
        }
    }

    private static final class NotificationKey extends Record {
        private final int id;
        private final String pkg;
        private final String tag;
        private final UserHandle user;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof NotificationKey)) {
                return false;
            }
            NotificationKey notificationKey = (NotificationKey) obj;
            return this.id == notificationKey.id && Objects.equals(this.user, notificationKey.user) && Objects.equals(this.pkg, notificationKey.pkg) && Objects.equals(this.tag, notificationKey.tag);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.user, this.pkg, this.tag, Integer.valueOf(this.id)};
        }

        private NotificationKey(UserHandle user, String pkg, String tag, int id) {
            this.user = user;
            this.pkg = pkg;
            this.tag = tag;
            this.id = id;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.id, this.user, this.pkg, this.tag);
        }

        public int id() {
            return this.id;
        }

        public String pkg() {
            return this.pkg;
        }

        public String tag() {
            return this.tag;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), NotificationKey.class, "user;pkg;tag;id");
        }

        public UserHandle user() {
            return this.user;
        }
    }

    private class RateLimiter {
        private static final Duration RATE_LIMITER_LOG_INTERVAL = Duration.ofSeconds(1);
        private final String mCounterName;
        private final RateEstimator mInputRateEstimator;
        private final float mLimitRate;
        private Instant mLogSilencedUntil;
        private final String mName;
        private final RateEstimator mOutputRateEstimator;

        private RateLimiter(String str, String str2, float f) {
            this.mInputRateEstimator = new RateEstimator();
            this.mOutputRateEstimator = new RateEstimator();
            this.mName = str;
            this.mCounterName = str2;
            this.mLimitRate = f;
        }

        boolean eventExceedsRate() {
            long millis = NotificationManager.this.mClock.millis();
            this.mInputRateEstimator.update(millis);
            return this.mOutputRateEstimator.getRate(millis) > this.mLimitRate;
        }

        void recordAccepted() {
            this.mOutputRateEstimator.update(NotificationManager.this.mClock.millis());
        }

        void recordRejected(NotificationKey notificationKey) {
            Instant instant = NotificationManager.this.mClock.instant();
            Instant instant2 = this.mLogSilencedUntil;
            if (instant2 == null || !instant.isBefore(instant2)) {
                if (Flags.nmBinderPerfLogNmThrottling()) {
                    try {
                        NotificationManager.this.service().incrementCounter(this.mCounterName);
                    } catch (RemoteException e) {
                        Slog.w(NotificationManager.TAG, "Ignoring error while trying to log " + this.mCounterName, e);
                    }
                }
                long epochMilli = instant.toEpochMilli();
                Slog.w(NotificationManager.TAG, TextUtils.formatSimple("Shedding %s of %s, rate limit (%s) exceeded: input %s, output would be %s", this.mName, notificationKey, Float.valueOf(this.mLimitRate), Float.valueOf(this.mInputRateEstimator.getRate(epochMilli)), Float.valueOf(this.mOutputRateEstimator.getRate(epochMilli))));
                this.mLogSilencedUntil = instant.plus((TemporalAmount) RATE_LIMITER_LOG_INTERVAL);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Notification fixNotification(Notification notification) {
        String packageName = this.mContext.getPackageName();
        Notification.addFieldsFromContext(this.mContext, notification);
        if (notification.sound != null) {
            notification.sound = notification.sound.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                notification.sound.checkFileUriExposed("Notification.sound");
            }
        }
        fixLegacySmallIcon(notification, packageName);
        if (this.mContext.getApplicationInfo().targetSdkVersion > 22 && notification.getSmallIcon() == null) {
            throw new IllegalArgumentException("Invalid notification (no valid small icon): " + notification);
        }
        notification.reduceImageSizes(this.mContext);
        return Notification.Builder.maybeCloneStrippedForDelivery(notification);
    }

    private void fixLegacySmallIcon(Notification notification, String str) {
        if (notification.getSmallIcon() != null || notification.icon == 0) {
            return;
        }
        notification.setSmallIcon(Icon.createWithResource(str, notification.icon));
    }

    public void cancel(int i) {
        cancel(null, i);
    }

    public void cancel(String str, int i) {
        cancelAsUser(str, i, this.mContext.getUser());
    }

    public void cancelAsPackage(String str, String str2, int i) {
        if (discardCancel(this.mContext.getUser(), str, str2, i)) {
            return;
        }
        try {
            service().cancelNotificationWithTag(str, this.mContext.getOpPackageName(), str2, i, this.mContext.getUser().getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelAsUser(String str, int i, UserHandle userHandle) {
        String packageName = this.mContext.getPackageName();
        if (discardCancel(userHandle, packageName, str, i)) {
            return;
        }
        INotificationManager service = service();
        if (localLOGV) {
            Log.v(TAG, packageName + ": cancel(" + i + NavigationBarInflaterView.KEY_CODE_END);
        }
        try {
            service.cancelNotificationWithTag(packageName, this.mContext.getOpPackageName(), str, i, userHandle.getIdentifier());
            EdgeNotificationManager edgeNotificationManager = this.mEdgeNotificationManager;
            if (edgeNotificationManager != null) {
                edgeNotificationManager.removeEdgeNotificationByNormal(i);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean discardCancel(UserHandle userHandle, String str, String str2, int i) {
        if (!Flags.nmBinderPerfThrottleNotify()) {
            return false;
        }
        NotificationKey notificationKey = new NotificationKey(userHandle, str, str2, i);
        synchronized (this.mThrottleLock) {
            Integer num = this.mKnownNotifications.get(notificationKey);
            if (num != null && num.intValue() == 2) {
                if (this.mUnnecessaryCancelRateLimiter.eventExceedsRate()) {
                    this.mUnnecessaryCancelRateLimiter.recordRejected(notificationKey);
                    return true;
                }
                this.mUnnecessaryCancelRateLimiter.recordAccepted();
            }
            this.mKnownNotifications.put(notificationKey, 2);
            return false;
        }
    }

    public void cancelAll() {
        String packageName = this.mContext.getPackageName();
        UserHandle user = this.mContext.getUser();
        if (Flags.nmBinderPerfThrottleNotify()) {
            synchronized (this.mThrottleLock) {
                for (NotificationKey notificationKey : this.mKnownNotifications.snapshot().keySet()) {
                    if (notificationKey.pkg.equals(packageName) && notificationKey.user.equals(user)) {
                        this.mKnownNotifications.put(notificationKey, 2);
                    }
                }
            }
        }
        INotificationManager service = service();
        if (localLOGV) {
            Log.v(TAG, packageName + ": cancelAll()");
        }
        try {
            service.cancelAllNotifications(packageName, this.mContext.getUserId());
            EdgeNotificationManager edgeNotificationManager = this.mEdgeNotificationManager;
            if (edgeNotificationManager != null) {
                edgeNotificationManager.removeEdgeNotificationAllByNormal();
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationDelegate(String str) {
        INotificationManager service = service();
        String packageName = this.mContext.getPackageName();
        if (localLOGV) {
            Log.v(TAG, packageName + ": setNotificationDelegate()");
        }
        try {
            service.setNotificationDelegate(packageName, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getNotificationDelegate() {
        try {
            return service().getNotificationDelegate(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canNotifyAsPackage(String str) {
        try {
            return service().canNotifyAsPackage(this.mContext.getPackageName(), str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canUseFullScreenIntent() {
        try {
            return service().canUseFullScreenIntent(this.mContext.getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canPostPromotedNotifications() {
        try {
            return service().canBePromoted(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCanPostPromotedNotifications(String str, int i, boolean z) {
        try {
            service().setCanBePromoted(str, i, z, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannelGroup(NotificationChannelGroup notificationChannelGroup) {
        createNotificationChannelGroups(Arrays.asList(notificationChannelGroup));
    }

    public void createNotificationChannelGroups(List<NotificationChannelGroup> list) {
        try {
            service().createNotificationChannelGroups(this.mContext.getPackageName(), new ParceledListSlice(list));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannel(NotificationChannel notificationChannel) {
        createNotificationChannels(Arrays.asList(notificationChannel));
    }

    public void createNotificationChannels(List<NotificationChannel> list) {
        try {
            service().createNotificationChannels(this.mContext.getPackageName(), new ParceledListSlice(list));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NotificationChannel getNotificationChannel(String str) {
        if (Flags.nmBinderPerfCacheChannels()) {
            return getChannelFromList(str, this.mNotificationChannelListCache.query(new NotificationChannelQuery(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId())));
        }
        try {
            return service().getNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NotificationChannel getNotificationChannel(String str, String str2) {
        if (Flags.nmBinderPerfCacheChannels()) {
            return getConversationChannelFromList(str, str2, this.mNotificationChannelListCache.query(new NotificationChannelQuery(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId())));
        }
        try {
            return service().getConversationNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), str, true, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<NotificationChannel> getNotificationChannels() {
        if (Flags.nmBinderPerfCacheChannels()) {
            List<NotificationChannel> query = this.mNotificationChannelListCache.query(new NotificationChannelQuery(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId()));
            ArrayList arrayList = new ArrayList();
            if (query != null) {
                Iterator<NotificationChannel> it = query.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().copy());
                }
            }
            return arrayList;
        }
        try {
            return service().getNotificationChannels(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static NotificationChannel getChannelFromList(String str, List<NotificationChannel> list) {
        if (list == null) {
            return null;
        }
        if (str == null) {
            str = NotificationChannel.DEFAULT_CHANNEL_ID;
        }
        for (NotificationChannel notificationChannel : list) {
            if (str.equals(notificationChannel.getId())) {
                return notificationChannel.copy();
            }
        }
        return null;
    }

    private static NotificationChannel getConversationChannelFromList(String str, String str2, List<NotificationChannel> list) {
        if (list == null) {
            return null;
        }
        if (str == null) {
            str = NotificationChannel.DEFAULT_CHANNEL_ID;
        }
        if (str2 == null) {
            return getChannelFromList(str, list);
        }
        NotificationChannel notificationChannel = null;
        for (NotificationChannel notificationChannel2 : list) {
            if (str2.equals(notificationChannel2.getConversationId()) && str.equals(notificationChannel2.getParentChannelId())) {
                return notificationChannel2.copy();
            }
            if (str.equals(notificationChannel2.getId())) {
                notificationChannel = notificationChannel2;
            }
        }
        if (notificationChannel != null) {
            return notificationChannel.copy();
        }
        return null;
    }

    public void deleteNotificationChannel(String str) {
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationClassification() && NotificationChannel.SYSTEM_RESERVED_IDS.contains(str)) {
            return;
        }
        try {
            service().deleteNotificationChannel(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NotificationChannelGroup getNotificationChannelGroup(String str) {
        if (Flags.nmBinderPerfCacheChannels()) {
            String packageName = this.mContext.getPackageName();
            NotificationChannelGroup groupWithChannels = NotificationChannelGroupsHelper.getGroupWithChannels(str, this.mNotificationChannelListCache.query(new NotificationChannelQuery(packageName, packageName, this.mContext.getUserId())), this.mNotificationChannelGroupsCache.query(packageName), false);
            if (groupWithChannels != null) {
                return groupWithChannels.m473clone();
            }
            return null;
        }
        try {
            return service().getNotificationChannelGroup(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        if (Flags.nmBinderPerfCacheChannels()) {
            String packageName = this.mContext.getPackageName();
            List<NotificationChannelGroup> groupsWithChannels = NotificationChannelGroupsHelper.getGroupsWithChannels(this.mNotificationChannelListCache.query(new NotificationChannelQuery(packageName, packageName, this.mContext.getUserId())), this.mNotificationChannelGroupsCache.query(packageName), NotificationChannelGroupsHelper.Params.forAllGroups());
            ArrayList arrayList = new ArrayList();
            Iterator<NotificationChannelGroup> it = groupsWithChannels.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().m473clone());
            }
            return arrayList;
        }
        try {
            ParceledListSlice notificationChannelGroups = service().getNotificationChannelGroups(this.mContext.getPackageName());
            if (notificationChannelGroups != null) {
                return notificationChannelGroups.getList();
            }
            return new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannelGroup(String str) {
        try {
            service().deleteNotificationChannelGroup(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateNotificationChannel(String str, int i, NotificationChannel notificationChannel) {
        try {
            service().updateNotificationChannelForPackage(str, i, notificationChannel);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class NotificationChannelQuery extends Record {
        private final String callingPkg;
        private final String targetPkg;
        private final int userId;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof NotificationChannelQuery)) {
                return false;
            }
            NotificationChannelQuery notificationChannelQuery = (NotificationChannelQuery) obj;
            return this.userId == notificationChannelQuery.userId && Objects.equals(this.callingPkg, notificationChannelQuery.callingPkg) && Objects.equals(this.targetPkg, notificationChannelQuery.targetPkg);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.callingPkg, this.targetPkg, Integer.valueOf(this.userId)};
        }

        private NotificationChannelQuery(String callingPkg, String targetPkg, int userId) {
            this.callingPkg = callingPkg;
            this.targetPkg = targetPkg;
            this.userId = userId;
        }

        public String callingPkg() {
            return this.callingPkg;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.userId, this.callingPkg, this.targetPkg);
        }

        public String targetPkg() {
            return this.targetPkg;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), NotificationChannelQuery.class, "callingPkg;targetPkg;userId");
        }

        public int userId() {
            return this.userId;
        }
    }

    public static void invalidateNotificationChannelCache() {
        if (Flags.nmBinderPerfCacheChannels()) {
            IpcDataCache.invalidateCache("system_server", NOTIFICATION_CHANNELS_CACHE_API);
        } else {
            Log.wtf(TAG, "invalidateNotificationChannelCache called without flag");
        }
    }

    public static void invalidateNotificationChannelGroupCache() {
        if (Flags.nmBinderPerfCacheChannels()) {
            IpcDataCache.invalidateCache("system_server", NOTIFICATION_CHANNEL_GROUPS_CACHE_API);
        } else {
            Log.wtf(TAG, "invalidateNotificationChannelGroupCache called without flag");
        }
    }

    public void setChannelCachesToTestMode() {
        this.mNotificationChannelListCache.testPropertyName();
        this.mNotificationChannelGroupsCache.testPropertyName();
    }

    public ComponentName getEffectsSuppressor() {
        try {
            return service().getEffectsSuppressor();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(Bundle bundle) {
        try {
            return service().matchesCallFilter(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cleanUpCallersAfter(long j) {
        try {
            service().cleanUpCallersAfter(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSystemConditionProviderEnabled(String str) {
        try {
            return service().isSystemConditionProviderEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setZenMode(int i, Uri uri, String str) {
        setZenMode(i, uri, str, false);
    }

    public void setZenMode(int i, Uri uri, String str, boolean z) {
        try {
            service().setZenMode(i, uri, str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getZenMode() {
        try {
            return service().getZenMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ZenModeConfig getZenModeConfig() {
        try {
            return service().getZenModeConfig();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Policy getConsolidatedNotificationPolicy() {
        try {
            return service().getConsolidatedNotificationPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRuleInstanceCount(ComponentName componentName) {
        try {
            return service().getRuleInstanceCount(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areAutomaticZenRulesUserManaged() {
        if (Flags.modesUi()) {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (!packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH) && !packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE) && !packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, AutomaticZenRule> getAutomaticZenRules() {
        INotificationManager service = service();
        try {
            HashMap hashMap = new HashMap();
            ParceledListSlice automaticZenRules = service.getAutomaticZenRules();
            if (automaticZenRules != null) {
                for (AutomaticZenRule.AzrWithId azrWithId : automaticZenRules.getList()) {
                    hashMap.put(azrWithId.mId, azrWithId.mRule);
                }
            }
            return hashMap;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AutomaticZenRule getAutomaticZenRule(String str) {
        try {
            return service().getAutomaticZenRule(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String addAutomaticZenRule(AutomaticZenRule automaticZenRule) {
        return addAutomaticZenRule(automaticZenRule, false);
    }

    public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, boolean z) {
        try {
            return service().addAutomaticZenRule(automaticZenRule, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule) {
        return updateAutomaticZenRule(str, automaticZenRule, false);
    }

    public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) {
        try {
            return service().updateAutomaticZenRule(str, automaticZenRule, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAutomaticZenRuleState(String str) {
        try {
            return service().getAutomaticZenRuleState(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutomaticZenRuleState(String str, Condition condition) {
        try {
            service().setAutomaticZenRuleState(str, condition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRule(String str) {
        return removeAutomaticZenRule(str, false);
    }

    public boolean removeAutomaticZenRule(String str, boolean z) {
        try {
            return service().removeAutomaticZenRule(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRules(String str) {
        return removeAutomaticZenRules(str, false);
    }

    public boolean removeAutomaticZenRules(String str, boolean z) {
        try {
            return service().removeAutomaticZenRules(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getImportance() {
        try {
            return service().getPackageImportance(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsEnabled() {
        if (Flags.nmBinderPerfPermissionCheck()) {
            return this.mContext.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == 0;
        }
        try {
            return service().areNotificationsEnabled(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean areBubblesAllowed() {
        try {
            return service().areBubblesAllowed(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areBubblesEnabled() {
        try {
            return service().areBubblesEnabled(this.mContext.getUser());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBubblePreference() {
        try {
            return service().getBubblePreferenceForPackage(this.mContext.getPackageName(), Binder.getCallingUid());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void silenceNotificationSound() {
        try {
            service().silenceNotificationSound();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsPaused() {
        try {
            return service().isPackagePaused(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGranted() {
        try {
            return service().isNotificationPolicyAccessGranted(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerAccessGranted(ComponentName componentName) {
        try {
            return service().isNotificationListenerAccessGranted(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isNotificationAssistantAccessGranted(ComponentName componentName) {
        try {
            return service().isNotificationAssistantAccessGranted(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideSilentStatusBarIcons() {
        try {
            return service().shouldHideSilentStatusIcons(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getAllowedAssistantAdjustments() {
        try {
            return service().getAllowedAssistantAdjustments(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allowAssistantAdjustment(String str) {
        try {
            service().allowAssistantAdjustment(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disallowAssistantAdjustment(String str) {
        try {
            service().disallowAssistantAdjustment(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGrantedForPackage(String str) {
        try {
            return service().isNotificationPolicyAccessGrantedForPackage(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAssistantAdjustmentKeyTypeState(int i, boolean z) {
        try {
            service().setAssistantAdjustmentKeyTypeState(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getEnabledNotificationListenerPackages() {
        try {
            return service().getEnabledNotificationListenerPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Policy getNotificationPolicy() {
        try {
            return service().getNotificationPolicy(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicy(Policy policy) {
        setNotificationPolicy(policy, false);
    }

    public void setNotificationPolicy(Policy policy, boolean z) {
        checkRequired(RuntimeManifestUtils.TAG_POLICY, policy);
        try {
            service().setNotificationPolicy(this.mContext.getOpPackageName(), policy, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicyAccessGranted(String str, boolean z) {
        try {
            service().setNotificationPolicyAccessGranted(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z) {
        setNotificationListenerAccessGranted(componentName, z, true);
    }

    public ZenPolicy getDefaultZenPolicy() {
        try {
            return service().getDefaultZenPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) {
        try {
            service().setManualZenRuleDeviceEffects(zenDeviceEffects);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) {
        INotificationManager service = service();
        try {
            if (CompatChanges.isChangeEnabled(SET_LISTENER_ACCESS_GRANTED_IS_USER_AWARE)) {
                service.setNotificationListenerAccessGrantedForUser(componentName, this.mContext.getUserId(), z, z2);
            } else {
                service.setNotificationListenerAccessGranted(componentName, z, z2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z) {
        try {
            service().setNotificationListenerAccessGrantedForUser(componentName, i, z, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) {
        try {
            service().setNotificationAssistantAccessGranted(componentName, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<ComponentName> getEnabledNotificationListeners() {
        return getEnabledNotificationListeners(this.mContext.getUserId());
    }

    public List<ComponentName> getEnabledNotificationListeners(int i) {
        try {
            return service().getEnabledNotificationListeners(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public ComponentName getAllowedNotificationAssistant() {
        try {
            return service().getAllowedNotificationAssistant();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasEnabledNotificationListener(String str, UserHandle userHandle) {
        try {
            return service().hasEnabledNotificationListener(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static void checkRequired(String str, Object obj) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException(str + " is required");
    }

    public void setToastRateLimitingEnabled(boolean z) {
        try {
            service().setToastRateLimitingEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOngoingActivityAllowed(String str, int i) {
        try {
            return getService().isOngoingActivityAllowed(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowOngoingActivity(String str, int i, boolean z) {
        try {
            getService().setAllowOngoingActivity(str, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getAllowedOngoingActivityAppList() {
        try {
            return getService().getAllowedOngoingActivityAppList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class Policy implements Parcelable {
        public static final int CONVERSATION_SENDERS_ANYONE = 1;
        public static final int CONVERSATION_SENDERS_IMPORTANT = 2;
        public static final int CONVERSATION_SENDERS_NONE = 3;
        public static final int CONVERSATION_SENDERS_UNSET = -1;
        public static final int PRIORITY_CATEGORY_ALARMS = 32;
        public static final int PRIORITY_CATEGORY_CALLS = 8;
        public static final int PRIORITY_CATEGORY_CONVERSATIONS = 256;
        public static final int PRIORITY_CATEGORY_EVENTS = 2;
        public static final int PRIORITY_CATEGORY_MEDIA = 64;
        public static final int PRIORITY_CATEGORY_MESSAGES = 4;
        public static final int PRIORITY_CATEGORY_REMINDERS = 1;
        public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
        public static final int PRIORITY_CATEGORY_SYSTEM = 128;
        public static final int PRIORITY_SENDERS_ANY = 0;
        public static final int PRIORITY_SENDERS_CONTACTS = 1;
        public static final int PRIORITY_SENDERS_STARRED = 2;
        public static final int SELECTED_APPS_ALLOWED = 0;
        public static final int SELECTED_APPS_ALLOWED_UNSET = -1;
        public static final int SELECTED_APPS_DISALLOWED = 1;
        public static final int SELECTED_CONTACTS_ALLOWED = 0;
        public static final int SELECTED_CONTACTS_ALLOWED_UNSET = -1;
        public static final int SELECTED_CONTACTS_DISALLOWED = 1;
        public static final int STATE_HAS_PRIORITY_CHANNELS = 1;
        public static final int STATE_PRIORITY_CHANNELS_BLOCKED = 2;
        public static final int STATE_UNSET = -1;
        public static final int SUPPRESSED_EFFECTS_UNSET = -1;
        public static final int SUPPRESSED_EFFECT_AMBIENT = 128;
        public static final int SUPPRESSED_EFFECT_BADGE = 64;
        public static final int SUPPRESSED_EFFECT_FULL_SCREEN_INTENT = 4;
        public static final int SUPPRESSED_EFFECT_LIGHTS = 8;
        public static final int SUPPRESSED_EFFECT_NOTIFICATION_LIST = 256;
        public static final int SUPPRESSED_EFFECT_PEEK = 16;

        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;

        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
        public static final int SUPPRESSED_EFFECT_STATUS_BAR = 32;
        public final int appBypassDndFlag;
        public final int exceptionContactsFlag;
        private List<String> mAppBypassDndList;
        private List<String> mExceptionContacts;
        public final int priorityCallSenders;
        public final int priorityCategories;
        public final int priorityConversationSenders;
        public final int priorityMessageSenders;
        public final int state;
        public final int suppressedVisualEffects;
        public static final int[] ALL_PRIORITY_CATEGORIES = {32, 64, 128, 1, 2, 4, 8, 16, 256};
        private static final int[] ALL_SUPPRESSED_EFFECTS = {1, 2, 4, 8, 16, 32, 64, 128, 256};
        private static final int[] SCREEN_OFF_SUPPRESSED_EFFECTS = {1, 4, 8, 128};
        private static final int[] SCREEN_ON_SUPPRESSED_EFFECTS = {2, 16, 32, 64, 256};
        public static final Parcelable.Creator<Policy> CREATOR = new Parcelable.Creator<Policy>() { // from class: android.app.NotificationManager.Policy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Policy createFromParcel(Parcel parcel) {
                return new Policy(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Policy[] newArray(int i) {
                return new Policy[i];
            }
        };

        @Retention(RetentionPolicy.SOURCE)
        public @interface ConversationSenders {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PrioritySenders {
        }

        public static int policyState(boolean z, boolean z2) {
            return !z2 ? (z ? 1 : 0) | 2 : z ? 1 : 0;
        }

        private boolean suppressedVisualEffectsEqual(int i, int i2) {
            if (i == i2) {
                return true;
            }
            if ((i & 2) != 0) {
                i |= 16;
            }
            if ((i & 1) != 0) {
                i |= 140;
            }
            if ((i2 & 2) != 0) {
                i2 |= 16;
            }
            if ((i2 & 1) != 0) {
                i2 |= 140;
            }
            int i3 = i & 2;
            if (i3 != (i2 & 2)) {
                if (((i3 != 0 ? i2 : i) & 16) == 0) {
                    return false;
                }
            }
            int i4 = i & 1;
            if (i4 != (i2 & 1)) {
                int i5 = i4 != 0 ? i2 : i;
                if ((i5 & 4) == 0 || (i5 & 8) == 0 || (i5 & 128) == 0) {
                    return false;
                }
            }
            return (i & (-4)) == (i2 & (-4));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Policy(int i, int i2, int i3) {
            this(i, i2, i3, -1, -1, -1);
        }

        public Policy(int i, int i2, int i3, int i4) {
            this(i, i2, i3, i4, -1, -1);
        }

        public Policy(int i, int i2, int i3, int i4, int i5) {
            this(i, i2, i3, i4, -1, i5);
        }

        public Policy(int i, int i2, int i3, int i4, int i5, int i6) {
            this(i, i2, i3, i4, i5, i6, new ArrayList());
        }

        public Policy(int i, int i2, int i3, int i4, int i5, int i6, List<String> list) {
            this.priorityCategories = i;
            this.priorityCallSenders = i2;
            this.priorityMessageSenders = i3;
            this.suppressedVisualEffects = i4;
            this.state = i5;
            this.priorityConversationSenders = i6;
            this.exceptionContactsFlag = -1;
            this.mExceptionContacts = list == null ? new ArrayList<>() : list;
            this.appBypassDndFlag = -1;
            this.mAppBypassDndList = new ArrayList();
        }

        public Policy(int i, int i2, int i3, int i4, int i5, int i6, List<String> list, List<String> list2) {
            this.priorityCategories = i;
            this.priorityCallSenders = i2;
            this.priorityMessageSenders = i3;
            this.suppressedVisualEffects = i4;
            this.state = i5;
            this.priorityConversationSenders = i6;
            this.exceptionContactsFlag = -1;
            this.mExceptionContacts = list == null ? new ArrayList<>() : list;
            this.appBypassDndFlag = -1;
            this.mAppBypassDndList = list2 == null ? new ArrayList<>() : list2;
        }

        public Policy(int i, int i2, int i3, int i4, int i5, int i6, int i7, List<String> list, int i8, List<String> list2) {
            this.priorityCategories = i;
            this.priorityCallSenders = i2;
            this.priorityMessageSenders = i3;
            this.suppressedVisualEffects = i4;
            this.state = i5;
            this.priorityConversationSenders = i6;
            this.exceptionContactsFlag = i7;
            this.mExceptionContacts = list == null ? new ArrayList<>() : list;
            this.appBypassDndFlag = i8;
            this.mAppBypassDndList = list2 == null ? new ArrayList<>() : list2;
        }

        public Policy(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createStringArrayList(), parcel.readInt(), parcel.createStringArrayList());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.priorityCategories);
            parcel.writeInt(this.priorityCallSenders);
            parcel.writeInt(this.priorityMessageSenders);
            parcel.writeInt(this.suppressedVisualEffects);
            parcel.writeInt(this.state);
            parcel.writeInt(this.priorityConversationSenders);
            parcel.writeInt(this.exceptionContactsFlag);
            parcel.writeStringList(this.mExceptionContacts);
            parcel.writeInt(this.appBypassDndFlag);
            parcel.writeStringList(this.mAppBypassDndList);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.priorityCategories), Integer.valueOf(this.priorityCallSenders), Integer.valueOf(this.priorityMessageSenders), Integer.valueOf(this.suppressedVisualEffects), Integer.valueOf(this.state), Integer.valueOf(this.priorityConversationSenders));
        }

        public boolean equals(Object obj) {
            List<String> list;
            List<String> list2;
            if (!(obj instanceof Policy)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Policy policy = (Policy) obj;
            return policy.priorityCategories == this.priorityCategories && policy.priorityCallSenders == this.priorityCallSenders && policy.priorityMessageSenders == this.priorityMessageSenders && suppressedVisualEffectsEqual(this.suppressedVisualEffects, policy.suppressedVisualEffects) && policy.state == this.state && policy.priorityConversationSenders == this.priorityConversationSenders && policy.exceptionContactsFlag == this.exceptionContactsFlag && (list = policy.mExceptionContacts) != null && list.equals(this.mExceptionContacts) && policy.appBypassDndFlag == this.appBypassDndFlag && (list2 = policy.mAppBypassDndList) != null && list2.equals(this.mAppBypassDndList);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("NotificationManager.Policy[priorityCategories=");
            sb.append(priorityCategoriesToString(this.priorityCategories));
            sb.append(",priorityCallSenders=");
            sb.append(prioritySendersToString(this.priorityCallSenders));
            sb.append(",priorityMessageSenders=");
            sb.append(prioritySendersToString(this.priorityMessageSenders));
            sb.append(",priorityConvSenders=");
            sb.append(conversationSendersToString(this.priorityConversationSenders));
            sb.append(",exceptionContactsFlag=");
            sb.append(exceptionContactsFlagToString(this.exceptionContactsFlag));
            sb.append(",mExceptionContacts=");
            sb.append(this.mExceptionContacts);
            sb.append(",appBypassDndFlag=");
            sb.append(appBypassDndFlagToString(this.appBypassDndFlag));
            sb.append(",mAppBypassDndList=");
            sb.append(this.mAppBypassDndList);
            sb.append(",suppressedVisualEffects=");
            sb.append(suppressedEffectsToString(this.suppressedVisualEffects));
            sb.append(",hasPriorityChannels=");
            int i = this.state;
            String str = "true";
            sb.append(i == -1 ? "unset" : (i & 1) != 0 ? "true" : "false");
            sb.append(",allowPriorityChannels=");
            if (this.state == -1) {
                str = "unset";
            } else if (!allowPriorityChannels()) {
                str = "false";
            }
            sb.append(str);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            bitwiseToProtoEnum(protoOutputStream, 2259152797697L, this.priorityCategories);
            protoOutputStream.write(1159641169922L, this.priorityCallSenders);
            protoOutputStream.write(1159641169923L, this.priorityMessageSenders);
            bitwiseToProtoEnum(protoOutputStream, 2259152797700L, this.suppressedVisualEffects);
            protoOutputStream.end(start);
        }

        private static void bitwiseToProtoEnum(ProtoOutputStream protoOutputStream, long j, int i) {
            int i2 = 1;
            while (i > 0) {
                if ((i & 1) == 1) {
                    protoOutputStream.write(j, i2);
                }
                i2++;
                i >>>= 1;
            }
        }

        public static int getAllSuppressedVisualEffects() {
            int i = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = ALL_SUPPRESSED_EFFECTS;
                if (i >= iArr.length) {
                    return i2;
                }
                i2 |= iArr[i];
                i++;
            }
        }

        public static boolean areAllVisualEffectsSuppressed(int i) {
            int i2 = 0;
            while (true) {
                int[] iArr = ALL_SUPPRESSED_EFFECTS;
                if (i2 >= iArr.length) {
                    return true;
                }
                if ((iArr[i2] & i) == 0) {
                    return false;
                }
                i2++;
            }
        }

        public static boolean secAreAllVisualEffectsSuppressed(int i) {
            int i2 = 0;
            while (true) {
                int[] iArr = ALL_SUPPRESSED_EFFECTS;
                if (i2 >= iArr.length) {
                    return true;
                }
                int i3 = iArr[i2];
                if (i3 != 128 && i3 != 1 && i3 != 2 && ((supportLedIndicator() || i3 != 8) && (i3 & i) == 0)) {
                    return false;
                }
                i2++;
            }
        }

        public static boolean secAreAnyScreenOffEffectsSuppressed(int i) {
            int i2 = 0;
            while (true) {
                int[] iArr = SCREEN_OFF_SUPPRESSED_EFFECTS;
                if (i2 >= iArr.length) {
                    return false;
                }
                int i3 = iArr[i2];
                if (i3 != 128 && i3 != 1 && ((supportLedIndicator() || i3 != 8) && (i3 & i) != 0)) {
                    return true;
                }
                i2++;
            }
        }

        public static boolean secAreAnyScreenOnEffectsSuppressed(int i) {
            int i2 = 0;
            while (true) {
                int[] iArr = SCREEN_ON_SUPPRESSED_EFFECTS;
                if (i2 >= iArr.length) {
                    return false;
                }
                int i3 = iArr[i2];
                if (i3 != 2 && (i3 & i) != 0) {
                    return true;
                }
                i2++;
            }
        }

        private static boolean supportLedIndicator() {
            return new File("/sys/class/sec/led/led_blink").isFile();
        }

        private static int toggleEffects(int i, int[] iArr, boolean z) {
            for (int i2 : iArr) {
                i = z ? i | i2 : i & (~i2);
            }
            return i;
        }

        public static String suppressedEffectsToString(int i) {
            if (i <= 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            while (true) {
                int[] iArr = ALL_SUPPRESSED_EFFECTS;
                if (i2 >= iArr.length) {
                    break;
                }
                int i3 = iArr[i2];
                if ((i & i3) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(effectToString(i3));
                }
                i &= ~i3;
                i2++;
            }
            if (i != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("UNKNOWN_");
                sb.append(i);
            }
            return sb.toString();
        }

        public static String priorityCategoriesToString(int i) {
            if (i == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            while (true) {
                int[] iArr = ALL_PRIORITY_CATEGORIES;
                if (i2 >= iArr.length) {
                    break;
                }
                int i3 = iArr[i2];
                if ((i & i3) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(priorityCategoryToString(i3));
                }
                i &= ~i3;
                i2++;
            }
            if (i != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("PRIORITY_CATEGORY_UNKNOWN_");
                sb.append(i);
            }
            return sb.toString();
        }

        private static String effectToString(int i) {
            if (i == -1) {
                return "SUPPRESSED_EFFECTS_UNSET";
            }
            if (i == 4) {
                return "SUPPRESSED_EFFECT_FULL_SCREEN_INTENT";
            }
            if (i == 8) {
                return "SUPPRESSED_EFFECT_LIGHTS";
            }
            if (i == 16) {
                return "SUPPRESSED_EFFECT_PEEK";
            }
            if (i == 32) {
                return "SUPPRESSED_EFFECT_STATUS_BAR";
            }
            if (i == 64) {
                return "SUPPRESSED_EFFECT_BADGE";
            }
            if (i == 128) {
                return "SUPPRESSED_EFFECT_AMBIENT";
            }
            if (i == 256) {
                return "SUPPRESSED_EFFECT_NOTIFICATION_LIST";
            }
            if (i == 1) {
                return "SUPPRESSED_EFFECT_SCREEN_OFF";
            }
            if (i == 2) {
                return "SUPPRESSED_EFFECT_SCREEN_ON";
            }
            return "UNKNOWN_" + i;
        }

        private static String priorityCategoryToString(int i) {
            if (i == 1) {
                return "PRIORITY_CATEGORY_REMINDERS";
            }
            if (i == 2) {
                return "PRIORITY_CATEGORY_EVENTS";
            }
            if (i == 4) {
                return "PRIORITY_CATEGORY_MESSAGES";
            }
            if (i == 8) {
                return "PRIORITY_CATEGORY_CALLS";
            }
            if (i == 16) {
                return "PRIORITY_CATEGORY_REPEAT_CALLERS";
            }
            if (i == 32) {
                return "PRIORITY_CATEGORY_ALARMS";
            }
            if (i == 64) {
                return "PRIORITY_CATEGORY_MEDIA";
            }
            if (i == 128) {
                return "PRIORITY_CATEGORY_SYSTEM";
            }
            if (i == 256) {
                return "PRIORITY_CATEGORY_CONVERSATIONS";
            }
            return "PRIORITY_CATEGORY_UNKNOWN_" + i;
        }

        public static String prioritySendersToString(int i) {
            if (i == 0) {
                return "PRIORITY_SENDERS_ANY";
            }
            if (i == 1) {
                return "PRIORITY_SENDERS_CONTACTS";
            }
            if (i == 2) {
                return "PRIORITY_SENDERS_STARRED";
            }
            return "PRIORITY_SENDERS_UNKNOWN_" + i;
        }

        public static String conversationSendersToString(int i) {
            if (i == -1) {
                return "unset";
            }
            if (i == 1) {
                return "anyone";
            }
            if (i == 2) {
                return "important";
            }
            if (i == 3) {
                return "none";
            }
            return "invalidConversationType{" + i + "}";
        }

        public static String exceptionContactsFlagToString(int i) {
            if (i == 0) {
                return "SELECTED_CONTACTS_ALLOWED";
            }
            if (i == 1) {
                return "SELECTED_CONTACTS_DISALLOWED";
            }
            return "SELECTED_CONTACTS_UNKNOWN_" + i;
        }

        public static String appBypassDndFlagToString(int i) {
            if (i == 0) {
                return "SELECTED_APPS_ALLOWED";
            }
            if (i == 1) {
                return "SELECTED_APPS_DISALLOWED";
            }
            return "SELECTED_APPS_UNKNOWN_" + i;
        }

        public boolean allowAlarms() {
            return (this.priorityCategories & 32) != 0;
        }

        public boolean allowMedia() {
            return (this.priorityCategories & 64) != 0;
        }

        public boolean allowSystem() {
            return (this.priorityCategories & 128) != 0;
        }

        public boolean allowRepeatCallers() {
            return (this.priorityCategories & 16) != 0;
        }

        public boolean allowCalls() {
            return (this.priorityCategories & 8) != 0;
        }

        public boolean allowConversations() {
            return (this.priorityCategories & 256) != 0;
        }

        public boolean allowMessages() {
            return (this.priorityCategories & 4) != 0;
        }

        public boolean allowEvents() {
            return (this.priorityCategories & 2) != 0;
        }

        public boolean allowReminders() {
            return (this.priorityCategories & 1) != 0;
        }

        public int allowCallsFrom() {
            return this.priorityCallSenders;
        }

        public int allowMessagesFrom() {
            return this.priorityMessageSenders;
        }

        public int allowConversationsFrom() {
            return this.priorityConversationSenders;
        }

        public boolean showFullScreenIntents() {
            return (this.suppressedVisualEffects & 4) == 0;
        }

        public boolean showLights() {
            return (this.suppressedVisualEffects & 8) == 0;
        }

        public boolean showPeeking() {
            return (this.suppressedVisualEffects & 16) == 0;
        }

        public boolean showStatusBarIcons() {
            return (this.suppressedVisualEffects & 32) == 0;
        }

        public boolean showAmbient() {
            return (this.suppressedVisualEffects & 128) == 0;
        }

        public boolean showBadges() {
            return (this.suppressedVisualEffects & 64) == 0;
        }

        public boolean showInNotificationList() {
            return (this.suppressedVisualEffects & 256) == 0;
        }

        public boolean allowPriorityChannels() {
            int i = this.state;
            return i == -1 || (i & 2) == 0;
        }

        public boolean hasPriorityChannels() {
            return (this.state & 1) != 0;
        }

        public Policy copy() {
            Parcel obtain = Parcel.obtain();
            try {
                writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                return new Policy(obtain);
            } finally {
                obtain.recycle();
            }
        }

        public void setExceptionContacts(List<String> list) {
            if (this.mExceptionContacts == null) {
                this.mExceptionContacts = new ArrayList();
            }
            this.mExceptionContacts.clear();
            this.mExceptionContacts.addAll(list);
        }

        public List<String> getExceptionContacts() {
            return this.mExceptionContacts;
        }

        public void addAppBypassDnd(String str, int i, boolean z) {
            boolean z2;
            Log.d(NotificationManager.TAG, "add bypass dnd app - pkg=" + str + " uid=" + i + " allow=" + z);
            if (str == null || str.isEmpty()) {
                return;
            }
            if (this.mAppBypassDndList == null) {
                this.mAppBypassDndList = new ArrayList();
            }
            String str2 = str + ":" + i;
            Iterator<String> it = this.mAppBypassDndList.iterator();
            synchronized (ZenModeConfig.ZenConfigLock) {
                z2 = false;
                while (it.hasNext()) {
                    if (str2.equals(it.next())) {
                        if (!z) {
                            it.remove();
                        }
                        z2 = true;
                    }
                }
            }
            if (!z2 && z) {
                synchronized (ZenModeConfig.ZenConfigLock) {
                    this.mAppBypassDndList.add(str2);
                }
            }
            for (String str3 : this.mAppBypassDndList) {
                Log.d(NotificationManager.TAG, "addAppBypassDnd app=" + str3);
            }
        }

        public void setAppBypassDndList(List<String> list) {
            Log.d(NotificationManager.TAG, "set bypass dnd app list");
            if (this.mAppBypassDndList == null) {
                this.mAppBypassDndList = new ArrayList();
            }
            this.mAppBypassDndList.addAll(list);
        }

        public List<String> getAppBypassDndList() {
            return this.mAppBypassDndList;
        }
    }

    public StatusBarNotification[] getActiveNotifications() {
        try {
            ParceledListSlice appActiveNotifications = service().getAppActiveNotifications(this.mContext.getPackageName(), this.mContext.getUserId());
            if (appActiveNotifications != null) {
                List list = appActiveNotifications.getList();
                return (StatusBarNotification[]) list.toArray(new StatusBarNotification[list.size()]);
            }
            return new StatusBarNotification[0];
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final int getCurrentInterruptionFilter() {
        try {
            return zenModeToInterruptionFilter(service().getZenMode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void setInterruptionFilter(int i) {
        setInterruptionFilter(i, false);
    }

    public final void setInterruptionFilter(int i, boolean z) {
        try {
            service().setInterruptionFilter(this.mContext.getOpPackageName(), i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(Uri uri) {
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        arrayList.add(new Person.Builder().setUri(uri.toString()).build());
        bundle.putParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, arrayList);
        return matchesCallFilter(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallNotificationEventCallbackStub extends ICallNotificationEventCallback.Stub {
        final Executor mExecutor;
        final CallNotificationEventListener mListener;
        final String mPackageName;
        final UserHandle mUserHandle;

        CallNotificationEventCallbackStub(String str, UserHandle userHandle, Executor executor, CallNotificationEventListener callNotificationEventListener) {
            this.mPackageName = str;
            this.mUserHandle = userHandle;
            this.mExecutor = executor;
            this.mListener = callNotificationEventListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallNotificationPosted$0(String str, UserHandle userHandle) {
            this.mListener.onCallNotificationPosted(str, userHandle);
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationPosted(final String str, final UserHandle userHandle) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.NotificationManager$CallNotificationEventCallbackStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManager.CallNotificationEventCallbackStub.this.lambda$onCallNotificationPosted$0(str, userHandle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallNotificationRemoved$1(String str, UserHandle userHandle) {
            this.mListener.onCallNotificationRemoved(str, userHandle);
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationRemoved(final String str, final UserHandle userHandle) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.NotificationManager$CallNotificationEventCallbackStub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationManager.CallNotificationEventCallbackStub.this.lambda$onCallNotificationRemoved$1(str, userHandle);
                }
            });
        }
    }

    @SystemApi
    public void registerCallNotificationEventListener(String str, UserHandle userHandle, Executor executor, CallNotificationEventListener callNotificationEventListener) {
        checkRequired("packageName", str);
        checkRequired("userHandle", userHandle);
        checkRequired("executor", executor);
        checkRequired("listener", callNotificationEventListener);
        INotificationManager service = service();
        try {
            synchronized (this.mCallNotificationEventCallbacks) {
                CallNotificationEventCallbackStub callNotificationEventCallbackStub = new CallNotificationEventCallbackStub(str, userHandle, executor, callNotificationEventListener);
                this.mCallNotificationEventCallbacks.put(callNotificationEventListener, callNotificationEventCallbackStub);
                service.registerCallNotificationEventListener(str, userHandle, callNotificationEventCallbackStub);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterCallNotificationEventListener(CallNotificationEventListener callNotificationEventListener) {
        checkRequired("listener", callNotificationEventListener);
        INotificationManager service = service();
        try {
            synchronized (this.mCallNotificationEventCallbacks) {
                CallNotificationEventCallbackStub remove = this.mCallNotificationEventCallbacks.remove(callNotificationEventListener);
                if (remove != null) {
                    service.unregisterCallNotificationEventListener(remove.mPackageName, remove.mUserHandle, remove);
                }
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getUnsupportedAdjustmentTypes() {
        try {
            return new HashSet(service().getUnsupportedAdjustmentTypes());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semAreNotificationsEnabledForPackage(String str, int i) {
        try {
            return getService().areNotificationsEnabledForPackage(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semBindNotificationListener(ComponentName componentName, int i) {
        try {
            getService().registerNotificationListener(componentName, i, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semUnBindNotificationListener(ComponentName componentName, int i) {
        try {
            getService().registerNotificationListener(componentName, i, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semUpdateNotificationChannels(List<NotificationChannel> list) {
        try {
            getService().updateNotificationChannels(this.mContext.getPackageName(), new ParceledListSlice(list));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetZenMode(int i) {
        Uri countdownConditionId;
        INotificationManager service = getService();
        if (i != 0) {
            try {
                long j = Settings.Secure.getLong(this.mContext.getContentResolver(), "zen_duration_end_time", 0L);
                if (j > 0) {
                    countdownConditionId = ZenModeConfig.toCountdownConditionId(j, false);
                    service.setZenMode(i, countdownConditionId, "called by SEP API", false);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        countdownConditionId = null;
        service.setZenMode(i, countdownConditionId, "called by SEP API", false);
    }

    private static class EdgeNotificationManager {
        private static final String EXTRA_SAMSUNG_NOTIFICATION_PENDINGINTENT = "samsung.notification.pendingIntent";
        private static final String EXTRA_SAMSUNG_NOTIFICATION_REMOVE_ALL = "samsung.notification.remove_all";
        private static final String EXTRA_SAMSUNG_NOTIFICATION_TYPE = "samsung.notification.type";
        private static final String EXTRA_SAMSUNG_NOTIFICATION_WHEN = "samsung.notification.when";
        private static final String EXTRA_SAMSUNG_PEOPLE_PENDINGINTENT = "samsung.people.pendingIntents";
        private static final String EXTRA_SAMSUNG_PEOPLE_SUBCATEGORY = "samsung.people.subcategory";
        private static final String EXTRA_SAMSUNG_PEOPLE_SUBTITLES = "samsung.people.subTitles";
        private static final String EXTRA_SAMSUNG_PEOPLE_TIMESTAMPS = "samsung.people.timestamps";
        private static final String EXTRA_SAMSUNG_PEOPLE_TITLES = "samsung.people.titles";
        private static final String EXTRA_SAMSUNG_PEOPLE_URIS = "samsung.people.uris";
        private static final String TAG = "NotificationManager.EdgeNotificationManager";
        private Context mContext;

        public EdgeNotificationManager(Context context) {
            this.mContext = context;
        }

        public void removeEdgeNotification(int i, Bundle bundle) {
            Log.i(TAG, "removeEdgeNotification:" + i);
            if (bundle != null && bundle.getString(EXTRA_SAMSUNG_NOTIFICATION_TYPE) != null) {
                throw new IllegalArgumentException("The bundle has wrong value.");
            }
            removeEdgeNotificationInternal(i, bundle);
        }

        public void postEdgeNotification(int i, Bundle bundle) {
            Log.i(TAG, "postEdgeNotification:" + i);
            if (bundle == null || bundle.getString(EXTRA_SAMSUNG_NOTIFICATION_TYPE) != null) {
                throw new IllegalArgumentException("The bundle is null");
            }
            postEdgeNotificationInternal(i, bundle);
        }

        public void postEdgeNotificationByNormal(int i, Notification notification) {
            if (notification.extras == null || notification.extras.getStringArrayList(EXTRA_SAMSUNG_PEOPLE_URIS) == null) {
                return;
            }
            Log.i(TAG, "postEdgeNotificationByNormal");
            Bundle bundle = new Bundle(notification.extras);
            bundle.putString(EXTRA_SAMSUNG_NOTIFICATION_TYPE, "normal");
            bundle.putParcelable(EXTRA_SAMSUNG_NOTIFICATION_PENDINGINTENT, notification.contentIntent);
            bundle.putLong(EXTRA_SAMSUNG_NOTIFICATION_WHEN, notification.when);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_URIS);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_TITLES);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_SUBTITLES);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_PENDINGINTENT);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_TIMESTAMPS);
            notification.extras.remove(EXTRA_SAMSUNG_PEOPLE_SUBCATEGORY);
            postEdgeNotificationInternal(i, bundle);
        }

        public void removeEdgeNotificationByNormal(int i) {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_SAMSUNG_NOTIFICATION_TYPE, "normal");
            removeEdgeNotificationInternal(i, bundle);
        }

        public void removeEdgeNotificationAllByNormal() {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_SAMSUNG_NOTIFICATION_TYPE, "normal");
            bundle.putBoolean(EXTRA_SAMSUNG_NOTIFICATION_REMOVE_ALL, true);
            removeEdgeNotificationInternal(0, bundle);
        }

        private void postEdgeNotificationInternal(int i, Bundle bundle) {
            try {
                NotificationManager.getService().enqueueEdgeNotification(this.mContext.getPackageName(), this.mContext.getOpPackageName(), i, bundle, UserHandle.myUserId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private void removeEdgeNotificationInternal(int i, Bundle bundle) {
            try {
                NotificationManager.getService().removeEdgeNotification(this.mContext.getPackageName(), i, bundle, UserHandle.myUserId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setWearableAppList(int i, List<String> list) {
        try {
            return getService().setWearableAppList(i, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addWearableAppToList(int i, String str) {
        try {
            return getService().addWearableAppToList(i, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeWearableAppFromList(int i, String str) {
        try {
            return getService().removeWearableAppFromList(i, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean requestListenerHintsForWearable(int i) {
        try {
            return getService().requestListenerHintsForWearable(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getWearableAppList(int i) {
        try {
            return getService().getWearableAppList(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Bundle> semGetNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) {
        try {
            return getService().getNotificationHistoryDataForPackage(str, str2, i, str3, str4, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semAddReplyHistory(int i, String str, String str2, int i2, String str3, String str4) {
        try {
            getService().addReplyHistory(i, str, str2, i2, str3, str4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateCancelEvent(int i, String str, boolean z) {
        try {
            getService().updateCancelEvent(i, str, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
