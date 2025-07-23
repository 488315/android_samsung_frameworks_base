package com.android.systemui.statusbar;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.database.ExecutorContentObserver;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda5;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.UseElapsedRealtimeForCreationTime;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.SemPersonaManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationLockscreenUserManagerImpl implements Dumpable, NotificationLockscreenUserManager, StatusBarStateController.StateListener {
    public final AnonymousClass2 mAllUsersReceiver;
    public final Executor mBackgroundExecutor;
    public final AnonymousClass3 mBaseBroadcastReceiver;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NotificationClickNotifier mClickNotifier;
    public final Lazy mCommonNotifCollectionLazy;
    protected final AtomicBoolean mConnectedToWifi;
    public final Context mContext;
    public final SparseArray mCurrentManagedProfiles;
    public final SparseArray mCurrentProfiles;
    public int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final KeyguardManager mKeyguardManager;
    public final AnonymousClass1 mKeyguardReceiver;
    public final KeyguardStateController mKeyguardStateController;
    protected final AtomicLong mLastLockTime;
    protected final AtomicLong mLastWifiConnectionTime;
    public final LockPatternUtils mLockPatternUtils;
    public final Collection mLockScreenUris;
    protected final AtomicBoolean mLocked;
    public final AnonymousClass5 mLockscreenSettingsObserver;
    public final Executor mMainExecutor;
    public final AtomicLong mOtpRedactionRequiredLockTimeMs;
    public NotificationPresenter mPresenter;
    public final AtomicBoolean mRedactOtpOnWifi;
    public final SecureSettings mSecureSettings;
    public final Lazy mSettingsHelperLazy;
    public boolean mShowLockscreenNotifications;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final Lazy mVisibilityProviderLazy;
    public static final Uri SHOW_LOCKSCREEN = Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS);
    public static final Uri SHOW_PRIVATE_LOCKSCREEN = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
    public static final Uri REDACT_OTP_ON_WIFI = Settings.Secure.getUriFor("redact_otp_on_wifi");
    public static final Uri OTP_REDACTION_LOCK_TIME = Settings.Secure.getUriFor("otp_redaction_lock_time");
    public static final long DEFAULT_LOCK_TIME_FOR_SENSITIVE_REDACTION_MS = TimeUnit.MINUTES.toMillis(10);
    public final Object mLock = new Object();
    public final SparseBooleanArray mLockscreenPublicMode = new SparseBooleanArray();
    public final SparseBooleanArray mUsersWithSeparateWorkChallenge = new SparseBooleanArray();
    public final SparseBooleanArray mUsersAllowingPrivateNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersAllowingNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersDpcAllowingNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersUsersAllowingNotifications = new SparseBooleanArray();
    public boolean mKeyguardAllowingNotifications = true;
    public final SparseBooleanArray mUsersDpcAllowingPrivateNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersUsersAllowingPrivateNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersInLockdownLatestResult = new SparseBooleanArray();
    public final SparseBooleanArray mShouldHideNotifsLatestResult = new SparseBooleanArray();
    public final List mListeners = new ArrayList();
    public int mState = 0;
    public final ListenerSet mNotifStateChangedListeners = new ListenerSet();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3, reason: invalid class name */
    public class AnonymousClass3 extends BroadcastReceiver {
        public AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Objects.equals(action, "android.intent.action.USER_REMOVED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra != -1) {
                    ArrayList arrayList = (ArrayList) NotificationLockscreenUserManagerImpl.this.mListeners;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((NotificationLockscreenUserManager.UserChangedListener) obj).onUserRemoved(intExtra);
                    }
                }
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                Uri uri = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                notificationLockscreenUserManagerImpl.updateCurrentProfilesCache();
                return;
            }
            if (Objects.equals(action, "android.intent.action.USER_ADDED")) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                Uri uri2 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                notificationLockscreenUserManagerImpl2.updateCurrentProfilesCache();
                final int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                NotificationLockscreenUserManagerImpl.this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationLockscreenUserManagerImpl.AnonymousClass3 anonymousClass3 = NotificationLockscreenUserManagerImpl.AnonymousClass3.this;
                        int i2 = intExtra2;
                        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = NotificationLockscreenUserManagerImpl.this;
                        Uri uri3 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                        notificationLockscreenUserManagerImpl3.mLockscreenSettingsObserver.onChange(false, notificationLockscreenUserManagerImpl3.mLockScreenUris, 0, UserHandle.of(i2));
                        notificationLockscreenUserManagerImpl3.updateDpcSettings(i2);
                        notificationLockscreenUserManagerImpl3.mKeyguardAllowingNotifications = notificationLockscreenUserManagerImpl3.mKeyguardManager.getPrivateNotificationsAllowed();
                    }
                });
                return;
            }
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = NotificationLockscreenUserManagerImpl.this;
            Uri uri3 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
            notificationLockscreenUserManagerImpl3.getClass();
            if (Objects.equals(action, "android.intent.action.PROFILE_AVAILABLE") || Objects.equals(action, "android.intent.action.PROFILE_UNAVAILABLE")) {
                NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                return;
            }
            if (!Objects.equals(action, "android.intent.action.USER_UNLOCKED") && Objects.equals(action, "com.android.systemui.statusbar.work_challenge_unlocked_notification_action")) {
                IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT");
                String stringExtra = intent.getStringExtra("android.intent.extra.INDEX");
                if (intentSender != null) {
                    try {
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        NotificationLockscreenUserManagerImpl.this.mContext.startIntentSender(intentSender, null, 0, 0, 0, makeBasic.toBundle());
                    } catch (IntentSender.SendIntentException unused) {
                    }
                }
                if (stringExtra != null) {
                    NotificationLockscreenUserManagerImpl.this.mClickNotifier.onNotificationClick(stringExtra, ((NotificationVisibilityProviderImpl) ((NotificationVisibilityProvider) NotificationLockscreenUserManagerImpl.this.mVisibilityProviderLazy.get())).obtain(stringExtra));
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$1] */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$2] */
    public NotificationLockscreenUserManagerImpl(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, Lazy lazy3, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Executor executor, Executor executor2, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlagsClassic featureFlagsClassic, Lazy lazy4, final Lazy lazy5, final Lazy lazy6, final CoroutineScope coroutineScope, Lazy lazy7) {
        ArrayList arrayList = new ArrayList();
        this.mLockScreenUris = arrayList;
        ?? r10 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED".equals(intent.getAction())) {
                    NotificationLockscreenUserManagerImpl.this.mKeyguardAllowingNotifications = intent.getBooleanExtra("android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED", false);
                    if (NotificationLockscreenUserManagerImpl.this.mCurrentUserId == getSendingUserId() && NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting()) {
                        NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
                    }
                }
            }
        };
        this.mKeyguardReceiver = r10;
        ?? r11 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean updateDpcSettings;
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction())) {
                    int sendingUserId = getSendingUserId();
                    if (sendingUserId == -1) {
                        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                        int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
                        List users = notificationLockscreenUserManagerImpl.mUserManager.getUsers();
                        updateDpcSettings = false;
                        for (int size = users.size() - 1; size >= 0; size--) {
                            updateDpcSettings |= NotificationLockscreenUserManagerImpl.this.updateDpcSettings(((UserInfo) users.get(size)).id);
                        }
                        sendingUserId = i;
                    } else {
                        updateDpcSettings = NotificationLockscreenUserManagerImpl.this.updateDpcSettings(sendingUserId);
                    }
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                    if (notificationLockscreenUserManagerImpl2.mCurrentUserId == sendingUserId) {
                        updateDpcSettings |= notificationLockscreenUserManagerImpl2.updateLockscreenNotificationSetting();
                    }
                    if (updateDpcSettings) {
                        NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
                    }
                }
            }
        };
        this.mAllUsersReceiver = r11;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mBaseBroadcastReceiver = anonymousClass3;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.4
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanging(int i) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                notificationLockscreenUserManagerImpl.mCurrentUserId = i;
                notificationLockscreenUserManagerImpl.updateCurrentProfilesCache();
                notificationLockscreenUserManagerImpl.updateLockscreenNotificationSetting();
                notificationLockscreenUserManagerImpl.updatePublicMode();
                NotificationPresenter notificationPresenter = notificationLockscreenUserManagerImpl.mPresenter;
                if (notificationPresenter != null) {
                    StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) notificationPresenter;
                    ((HeadsUpManagerImpl) statusBarNotificationPresenter.mHeadsUpManager).mUser = notificationLockscreenUserManagerImpl.mCurrentUserId;
                    statusBarNotificationPresenter.mCommandQueue.animateCollapsePanels();
                    NotificationMediaManager notificationMediaManager = statusBarNotificationPresenter.mMediaManager;
                    notificationMediaManager.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda5(notificationMediaManager));
                } else {
                    Log.w("LockscreenUserManager", "user switch before setup with presenter", new Exception());
                }
                ArrayList arrayList2 = (ArrayList) notificationLockscreenUserManagerImpl.mListeners;
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList2.get(i2);
                    i2++;
                    ((NotificationLockscreenUserManager.UserChangedListener) obj).onUserChanged(notificationLockscreenUserManagerImpl.mCurrentUserId);
                }
            }
        };
        this.mUserChangedCallback = callback;
        this.mCurrentProfiles = new SparseArray();
        this.mCurrentManagedProfiles = new SparseArray();
        this.mLastLockTime = new AtomicLong(-1L);
        this.mLocked = new AtomicBoolean(true);
        this.mLastWifiConnectionTime = new AtomicLong(-1L);
        this.mConnectedToWifi = new AtomicBoolean(false);
        this.mRedactOtpOnWifi = new AtomicBoolean(true);
        this.mOtpRedactionRequiredLockTimeMs = new AtomicLong(DEFAULT_LOCK_TIME_FOR_SENSITIVE_REDACTION_MS);
        this.mCurrentUserId = 0;
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mCurrentUserId = ((UserTrackerImpl) userTracker).getUserId();
        this.mVisibilityProviderLazy = lazy;
        this.mCommonNotifCollectionLazy = lazy2;
        this.mClickNotifier = notificationClickNotifier;
        statusBarStateController.addCallback(this);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardManager = keyguardManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSecureSettings = secureSettings;
        this.mKeyguardStateController = keyguardStateController;
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            this.mSettingsHelperLazy = lazy7;
        }
        Uri uri = SHOW_LOCKSCREEN;
        arrayList.add(uri);
        Uri uri2 = SHOW_PRIVATE_LOCKSCREEN;
        arrayList.add(uri2);
        Uri uri3 = REDACT_OTP_ON_WIFI;
        arrayList.add(uri3);
        Uri uri4 = OTP_REDACTION_LOCK_TIME;
        arrayList.add(uri4);
        dumpManager.registerDumpable(this);
        this.mLockscreenSettingsObserver = new AnonymousClass5(executor2);
        new ExecutorContentObserver(executor) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.6
            public final void onChange(boolean z) {
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                ((DeviceProvisionedControllerImpl) NotificationLockscreenUserManagerImpl.this.mDeviceProvisionedController).deviceProvisioned.get();
            }
        };
        context.getContentResolver().registerContentObserver(uri, false, this.mLockscreenSettingsObserver, -1);
        context.getContentResolver().registerContentObserver(uri2, true, this.mLockscreenSettingsObserver, -1);
        secureSettings.registerContentObserverAsync(uri3, (ContentObserver) this.mLockscreenSettingsObserver);
        secureSettings.registerContentObserverAsync(uri4, (ContentObserver) this.mLockscreenSettingsObserver);
        IntentFilter intentFilter = new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        UserHandle userHandle = UserHandle.ALL;
        broadcastDispatcher.registerReceiver(r11, intentFilter, executor2, userHandle);
        broadcastDispatcher.registerReceiver(r10, new IntentFilter("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED"), executor2, userHandle);
        IntentFilter intentFilter2 = new IntentFilter();
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter2, "android.intent.action.USER_ADDED", "android.intent.action.USER_REMOVED", "android.intent.action.USER_UNLOCKED", "android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter2.addAction("android.intent.action.PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.PROFILE_UNAVAILABLE");
        broadcastDispatcher.registerReceiver(anonymousClass3, intentFilter2, null, userHandle);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        context.registerReceiver(anonymousClass3, intentFilter3, "com.android.systemui.permission.SELF", null, 2);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, executor);
        this.mCurrentUserId = userTrackerImpl.getUserId();
        updateCurrentProfilesCache();
        executor2.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda5(this, 0));
        int i = LockscreenOtpRedaction.$r8$clinit;
        if (coroutineScope != null) {
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                    CoroutineScope coroutineScope2 = coroutineScope;
                    Lazy lazy8 = lazy5;
                    final Lazy lazy9 = lazy6;
                    Uri uri5 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                    JavaAdapterKt.collectFlow(coroutineScope2, ((KeyguardInteractor) lazy8.get()).isKeyguardDismissible, new Consumer() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                            Boolean bool = (Boolean) obj;
                            Uri uri6 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                            notificationLockscreenUserManagerImpl2.getClass();
                            if (!bool.booleanValue()) {
                                notificationLockscreenUserManagerImpl2.mLastLockTime.set(System.currentTimeMillis());
                            }
                            notificationLockscreenUserManagerImpl2.mLocked.set(!bool.booleanValue());
                            notificationLockscreenUserManagerImpl2.notifyNotificationStateChanged();
                        }
                    });
                    JavaAdapterKt.collectFlow(coroutineScope2, ((WifiRepository) lazy9.get()).getWifiNetwork(), new Consumer() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            String str;
                            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                            Lazy lazy10 = lazy9;
                            boolean z = notificationLockscreenUserManagerImpl2.mConnectedToWifi.get();
                            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) ((WifiRepository) lazy10.get()).getWifiNetwork().getValue();
                            boolean z2 = (!(wifiNetworkModel instanceof WifiNetworkModel.Active) || (str = ((WifiNetworkModel.Active) wifiNetworkModel).ssid) == null || Intrinsics.areEqual(str, "<unknown ssid>")) ? false : true;
                            if (z != z2) {
                                notificationLockscreenUserManagerImpl2.mLastWifiConnectionTime.set(System.currentTimeMillis());
                                notificationLockscreenUserManagerImpl2.mConnectedToWifi.set(z2);
                                notificationLockscreenUserManagerImpl2.notifyNotificationStateChanged();
                            }
                        }
                    });
                }
            });
        }
    }

    public final void addUserChangedListener(NotificationLockscreenUserManager.UserChangedListener userChangedListener) {
        ((ArrayList) this.mListeners).add(userChangedListener);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("NotificationLockscreenUserManager state:");
        printWriter.print("  mCurrentUserId=");
        printWriter.println(this.mCurrentUserId);
        printWriter.print("  mShowLockscreenNotifications=");
        printWriter.println(this.mShowLockscreenNotifications);
        printWriter.print("  mCurrentProfiles=");
        synchronized (this.mLock) {
            try {
                for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size += -1) {
                    printWriter.print("" + ((UserInfo) this.mCurrentProfiles.valueAt(size)).id + " ");
                }
            } finally {
            }
        }
        printWriter.println();
        printWriter.print("  mCurrentManagedProfiles=");
        synchronized (this.mLock) {
            try {
                for (int size2 = this.mCurrentManagedProfiles.size() - 1; size2 >= 0; size2 += -1) {
                    printWriter.print("" + ((UserInfo) this.mCurrentManagedProfiles.valueAt(size2)).id + " ");
                }
            } finally {
            }
        }
        printWriter.println();
        printWriter.print("  mLockscreenPublicMode=");
        printWriter.println(this.mLockscreenPublicMode);
        printWriter.print("  mUsersWithSeparateWorkChallenge=");
        printWriter.println(this.mUsersWithSeparateWorkChallenge);
        printWriter.print("  mUsersAllowingPrivateNotifications=");
        printWriter.println(this.mUsersAllowingPrivateNotifications);
        printWriter.print("  mUsersAllowingNotifications=");
        printWriter.println(this.mUsersAllowingNotifications);
        printWriter.print("  mUsersInLockdownLatestResult=");
        printWriter.println(this.mUsersInLockdownLatestResult);
        printWriter.print("  mShouldHideNotifsLatestResult=");
        printWriter.println(this.mShouldHideNotifsLatestResult);
        printWriter.print("  mUsersDpcAllowingNotifications=");
        printWriter.println(this.mUsersDpcAllowingNotifications);
        printWriter.print("  mUsersUsersAllowingNotifications=");
        printWriter.println(this.mUsersUsersAllowingNotifications);
        printWriter.print("  mKeyguardAllowingNotifications=");
        printWriter.println(this.mKeyguardAllowingNotifications);
        printWriter.print("  mUsersDpcAllowingPrivateNotifications=");
        printWriter.println(this.mUsersDpcAllowingPrivateNotifications);
        printWriter.print("  mUsersUsersAllowingPrivateNotifications=");
        printWriter.println(this.mUsersUsersAllowingPrivateNotifications);
    }

    public final int getRedactionType(NotificationEntry notificationEntry) {
        int i;
        boolean z;
        int userId = notificationEntry.mSbn.getUserId();
        boolean userAllowsPrivateNotificationsInPublic = userAllowsPrivateNotificationsInPublic(this.mCurrentUserId);
        boolean z2 = ((this.mCurrentManagedProfiles.contains(userId) || userAllowsPrivateNotificationsInPublic) && userAllowsPrivateNotificationsInPublic(userId) && (userAllowsPrivateNotificationsInPublic || !isLockscreenPublicMode(this.mCurrentUserId) || !SemPersonaManager.isSecureFolderId(userId))) ? false : true;
        boolean z3 = (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && ((SettingsHelper) this.mSettingsHelperLazy.get()).isAllowPrivateNotificationsWhenUnsecure(userId)) || notificationEntry.mSbn.getNotification().visibility == 0;
        boolean packageHasVisibilityOverride = packageHasVisibilityOverride(notificationEntry.mSbn.getKey());
        if (packageHasVisibilityOverrideToShowContent(notificationEntry.mSbn.getKey())) {
            return 0;
        }
        if (packageHasVisibilityOverride || ((z3 && z2) || !this.mKeyguardAllowingNotifications)) {
            return 1;
        }
        int i2 = LockscreenOtpRedaction.$r8$clinit;
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        if (ranking == null || !ranking.hasSensitiveContent()) {
            i = 0;
            z = false;
        } else {
            long when = notificationEntry.mSbn.getNotification().getWhen();
            long when2 = notificationEntry.mSbn.getNotification().getWhen();
            int i3 = UseElapsedRealtimeForCreationTime.$r8$clinit;
            long min = Math.min(when2, System.currentTimeMillis() - (SystemClock.uptimeMillis() - notificationEntry.mCreationTime));
            boolean z4 = this.mLocked.get();
            long j = this.mLastLockTime.get();
            boolean z5 = this.mConnectedToWifi.get();
            long j2 = this.mLastWifiConnectionTime.get();
            if (this.mRedactOtpOnWifi.get()) {
                z = z4;
            } else {
                z = z5 ? false : z4;
                if (min < j2) {
                    z = false;
                }
            }
            if (min < this.mOtpRedactionRequiredLockTimeMs.get() + j) {
                z = false;
            }
            int min2 = (int) Math.min(2147483647L, Math.max(-2147483648L, when - min));
            i = 0;
            int min3 = (int) Math.min(2147483647L, Math.max(-2147483648L, j - min));
            int min4 = (int) Math.min(2147483647L, Math.max(-2147483648L, j2 - min));
            StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
            newBuilder.setAtomId(1032);
            newBuilder.writeBoolean(z);
            newBuilder.writeInt(min2);
            newBuilder.writeBoolean(z4);
            newBuilder.writeInt(min3);
            newBuilder.writeBoolean(z5);
            newBuilder.writeInt(min4);
            newBuilder.usePooledBuffer();
            StatsLog.write(newBuilder.build());
        }
        if (z) {
            return 2;
        }
        return i;
    }

    public final boolean isAnyProfilePublicMode() {
        synchronized (this.mLock) {
            try {
                for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size--) {
                    if (isLockscreenPublicMode(((UserInfo) this.mCurrentProfiles.valueAt(size)).id)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isCurrentProfile(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mCurrentProfiles.get(i) == null) {
                        z = false;
                    }
                } finally {
                }
            }
            z = true;
        }
        return z;
    }

    public final boolean isLockscreenPublicMode(int i) {
        return i == -1 ? this.mLockscreenPublicMode.get(this.mCurrentUserId, false) : this.mLockscreenPublicMode.get(i, false);
    }

    public final boolean isProfileAvailable(int i) {
        boolean isUserRunning;
        synchronized (this.mLock) {
            isUserRunning = this.mUserManager.isUserRunning(i);
        }
        return isUserRunning;
    }

    public final void notifyNotificationStateChanged() {
        boolean isCurrentThread = Looper.getMainLooper().isCurrentThread();
        ListenerSet listenerSet = this.mNotifStateChangedListeners;
        if (isCurrentThread) {
            Iterator it = listenerSet.iterator();
            while (it.hasNext()) {
                ((NotificationLockscreenUserManager.NotificationStateChangedListener) it.next()).onNotificationStateChanged();
            }
        } else {
            Iterator it2 = listenerSet.iterator();
            while (it2.hasNext()) {
                NotificationLockscreenUserManager.NotificationStateChangedListener notificationStateChangedListener = (NotificationLockscreenUserManager.NotificationStateChangedListener) it2.next();
                Executor executor = this.mMainExecutor;
                Objects.requireNonNull(notificationStateChangedListener);
                executor.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda5(notificationStateChangedListener, 2));
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        this.mState = i;
        updatePublicMode();
    }

    public final boolean packageHasVisibilityOverride(String str) {
        Lazy lazy = this.mCommonNotifCollectionLazy;
        if (lazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
            return true;
        }
        NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) lazy.get())).mNotifCollection.getEntry(str);
        return entry != null && entry.mRanking.getLockscreenVisibilityOverride() == 0;
    }

    public final boolean packageHasVisibilityOverrideToShowContent(String str) {
        Lazy lazy = this.mCommonNotifCollectionLazy;
        if (lazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
            return true;
        }
        NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) lazy.get())).mNotifCollection.getEntry(str);
        return entry != null && entry.mRanking.getLockscreenVisibilityOverride() == 1;
    }

    public void setLockscreenPublicMode(boolean z, int i) {
        this.mLockscreenPublicMode.put(i, z);
    }

    public final void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            try {
                this.mCurrentProfiles.clear();
                this.mCurrentManagedProfiles.clear();
                UserManager userManager = this.mUserManager;
                if (userManager != null) {
                    for (UserInfo userInfo : userManager.getProfiles(this.mCurrentUserId)) {
                        this.mCurrentProfiles.put(userInfo.id, userInfo);
                        if ("android.os.usertype.profile.MANAGED".equals(userInfo.userType)) {
                            this.mCurrentManagedProfiles.put(userInfo.id, userInfo);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mMainExecutor.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda5(this, 1));
    }

    public final boolean updateDpcSettings(int i) {
        boolean z = this.mUsersDpcAllowingNotifications.get(i);
        boolean z2 = this.mUsersDpcAllowingPrivateNotifications.get(i);
        int keyguardDisabledFeatures = this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i);
        boolean z3 = (keyguardDisabledFeatures & 4) == 0;
        boolean z4 = (keyguardDisabledFeatures & 8) == 0;
        this.mUsersDpcAllowingNotifications.put(i, z3);
        this.mUsersDpcAllowingPrivateNotifications.put(i, z4);
        return (z == z3 && z2 == z4) ? false : true;
    }

    public final boolean updateLockscreenNotificationSetting() {
        boolean z = this.mUsersUsersAllowingNotifications.get(this.mCurrentUserId);
        boolean z2 = this.mUsersDpcAllowingNotifications.get(this.mCurrentUserId, true);
        boolean z3 = this.mShowLockscreenNotifications;
        boolean z4 = z && z2;
        this.mShowLockscreenNotifications = z4;
        return z3 != z4;
    }

    public final void updatePublicMode() {
        boolean z = this.mState != 0 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        int i = SceneContainerFlag.$r8$clinit;
        SparseArray sparseArray = this.mCurrentProfiles;
        SparseBooleanArray clone = this.mLockscreenPublicMode.clone();
        SparseBooleanArray clone2 = this.mUsersWithSeparateWorkChallenge.clone();
        this.mUsersWithSeparateWorkChallenge.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            final int i2 = ((UserInfo) sparseArray.valueAt(size)).id;
            boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                    return Boolean.valueOf(notificationLockscreenUserManagerImpl.mLockPatternUtils.isSeparateProfileChallengeEnabled(i2));
                }
            })).booleanValue();
            setLockscreenPublicMode((z || i2 == this.mCurrentUserId || !booleanValue || !this.mLockPatternUtils.isSecure(i2)) ? z : z || this.mKeyguardManager.isDeviceLocked(i2), i2);
            this.mUsersWithSeparateWorkChallenge.put(i2, booleanValue);
        }
        if (this.mLockscreenPublicMode.equals(clone) && this.mUsersWithSeparateWorkChallenge.equals(clone2)) {
            return;
        }
        notifyNotificationStateChanged();
    }

    public final boolean userAllowsNotificationsInPublic(int i) {
        if (i == -1 || this.mCurrentManagedProfiles.contains(i)) {
            i = this.mCurrentUserId;
        }
        if (this.mUsersUsersAllowingNotifications.indexOfKey(i) < 0) {
            Log.wtf("LockscreenUserManager", "Asking for show notifs setting too early", new Throwable());
            this.mUsersUsersAllowingNotifications.get(i);
            this.mUsersUsersAllowingNotifications.put(i, this.mSecureSettings.getIntForUser(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 1, i) != 0);
        }
        if (this.mUsersDpcAllowingNotifications.indexOfKey(i) < 0) {
            Log.wtf("LockscreenUserManager", "Asking for show notifs dpm override too early", new Throwable());
            updateDpcSettings(i);
        }
        return this.mUsersUsersAllowingNotifications.get(i) && this.mUsersDpcAllowingNotifications.get(i);
    }

    public final boolean userAllowsPrivateNotificationsInPublic(int i) {
        if (i == -1) {
            i = this.mCurrentUserId;
        }
        if (this.mUsersUsersAllowingPrivateNotifications.indexOfKey(i) < 0) {
            Log.i("LockscreenUserManager", "Asking for redact notifs setting too early", new Throwable());
            return false;
        }
        if (this.mUsersDpcAllowingPrivateNotifications.indexOfKey(i) >= 0) {
            return this.mUsersUsersAllowingPrivateNotifications.get(i) && this.mUsersDpcAllowingPrivateNotifications.get(i) && this.mKeyguardAllowingNotifications;
        }
        Log.i("LockscreenUserManager", "Asking for redact notifs dpm override too early", new Throwable());
        return false;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$5, reason: invalid class name */
    public class AnonymousClass5 extends ExecutorContentObserver {
        public AnonymousClass5(Executor executor) {
            super(executor);
        }

        public final void onChange(boolean z, Collection collection, int i) {
            List users = NotificationLockscreenUserManagerImpl.this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                onChange(z, collection, i, ((UserInfo) users.get(size)).getUserHandle());
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x00bf, code lost:
        
            if (r3 != r5) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
        
            r2 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x003d, code lost:
        
            r12 = r12 | r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0093, code lost:
        
            if (r1 != r3) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
        
            if (r4 != r3) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0039, code lost:
        
            if (r4 != r3) goto L15;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onChange(boolean r10, java.util.Collection r11, int r12, android.os.UserHandle r13) {
            /*
                r9 = this;
                java.util.Iterator r10 = r11.iterator()
                r11 = 0
                r12 = r11
            L6:
                boolean r0 = r10.hasNext()
                if (r0 == 0) goto Lc3
                java.lang.Object r0 = r10.next()
                android.net.Uri r0 = (android.net.Uri) r0
                android.net.Uri r1 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN
                boolean r1 = r1.equals(r0)
                r2 = 1
                if (r1 == 0) goto L3f
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                int r1 = r13.getIdentifier()
                android.util.SparseBooleanArray r3 = r0.mUsersUsersAllowingNotifications
                boolean r3 = r3.get(r1)
                com.android.systemui.util.settings.SecureSettings r4 = r0.mSecureSettings
                java.lang.String r5 = "lock_screen_show_notifications"
                int r4 = r4.getIntForUser(r5, r2, r1)
                if (r4 == 0) goto L33
                r4 = r2
                goto L34
            L33:
                r4 = r11
            L34:
                android.util.SparseBooleanArray r0 = r0.mUsersUsersAllowingNotifications
                r0.put(r1, r4)
                if (r4 == r3) goto L3c
                goto L3d
            L3c:
                r2 = r11
            L3d:
                r12 = r12 | r2
                goto L6
            L3f:
                android.net.Uri r1 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.SHOW_PRIVATE_LOCKSCREEN
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L68
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                int r1 = r13.getIdentifier()
                android.util.SparseBooleanArray r3 = r0.mUsersUsersAllowingPrivateNotifications
                boolean r3 = r3.get(r1)
                com.android.systemui.util.settings.SecureSettings r4 = r0.mSecureSettings
                java.lang.String r5 = "lock_screen_allow_private_notifications"
                int r4 = r4.getIntForUser(r5, r11, r1)
                if (r4 == 0) goto L5f
                r4 = r2
                goto L60
            L5f:
                r4 = r11
            L60:
                android.util.SparseBooleanArray r0 = r0.mUsersUsersAllowingPrivateNotifications
                r0.put(r1, r4)
                if (r4 == r3) goto L3c
                goto L3d
            L68:
                android.net.Uri r1 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.REDACT_OTP_ON_WIFI
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L96
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                java.util.concurrent.atomic.AtomicBoolean r1 = r0.mRedactOtpOnWifi
                boolean r1 = r1.get()
                android.os.UserHandle r3 = android.os.Process.myUserHandle()
                int r3 = r3.getIdentifier()
                com.android.systemui.util.settings.SecureSettings r4 = r0.mSecureSettings
                java.lang.String r5 = "redact_otp_on_wifi"
                int r3 = r4.getIntForUser(r5, r11, r3)
                if (r3 == 0) goto L8d
                r3 = r2
                goto L8e
            L8d:
                r3 = r11
            L8e:
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.mRedactOtpOnWifi
                r0.set(r3)
                if (r1 == r3) goto L3c
                goto L3d
            L96:
                android.net.Uri r1 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.OTP_REDACTION_LOCK_TIME
                boolean r0 = r1.equals(r0)
                if (r0 == 0) goto L6
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                java.util.concurrent.atomic.AtomicLong r1 = r0.mOtpRedactionRequiredLockTimeMs
                long r3 = r1.get()
                android.os.UserHandle r1 = android.os.Process.myUserHandle()
                int r1 = r1.getIdentifier()
                long r5 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.DEFAULT_LOCK_TIME_FOR_SENSITIVE_REDACTION_MS
                com.android.systemui.util.settings.SecureSettings r7 = r0.mSecureSettings
                java.lang.String r8 = "otp_redaction_lock_time"
                long r5 = r7.getLongForUser(r8, r5, r1)
                java.util.concurrent.atomic.AtomicLong r0 = r0.mOtpRedactionRequiredLockTimeMs
                r0.set(r5)
                int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r0 == 0) goto L3c
                goto L3d
            Lc3:
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r10 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                int r10 = r10.mCurrentUserId
                int r11 = r13.getIdentifier()
                if (r10 != r11) goto Ld4
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r10 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                boolean r10 = r10.updateLockscreenNotificationSetting()
                r12 = r12 | r10
            Ld4:
                if (r12 == 0) goto Ldb
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r9 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                r9.notifyNotificationStateChanged()
            Ldb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.AnonymousClass5.onChange(boolean, java.util.Collection, int, android.os.UserHandle):void");
        }
    }
}
