package com.android.systemui.statusbar;

import android.app.ActivityOptions;
import android.app.Flags;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.database.ExecutorContentObserver;
import android.net.Uri;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$notifStateChangedListener$1;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
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
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationLockscreenUserManagerImpl implements Dumpable, NotificationLockscreenUserManager, StatusBarStateController.StateListener {
    public static final Uri SHOW_LOCKSCREEN = Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS);
    public static final Uri SHOW_PRIVATE_LOCKSCREEN = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
    public final AnonymousClass2 mAllUsersReceiver;
    public final Executor mBackgroundExecutor;
    public final AnonymousClass3 mBaseBroadcastReceiver;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NotificationClickNotifier mClickNotifier;
    public final Lazy mCommonNotifCollectionLazy;
    public final Context mContext;
    public final SparseArray mCurrentManagedProfiles;
    public final SparseArray mCurrentProfiles;
    public int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final KeyguardManager mKeyguardManager;
    public final AnonymousClass1 mKeyguardReceiver;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public final Collection mLockScreenUris;
    public AnonymousClass5 mLockscreenSettingsObserver;
    public final Executor mMainExecutor;
    public final Lazy mOverviewProxyServiceLazy;
    public NotificationPresenter mPresenter;
    public final SecureSettings mSecureSettings;
    public final Lazy mSettingsHelperLazy;
    public boolean mShowLockscreenNotifications;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final Lazy mVisibilityProviderLazy;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            NotificationVisibility obtain;
            String action = intent.getAction();
            if (Objects.equals(action, "android.intent.action.USER_REMOVED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra != -1) {
                    Iterator it = ((ArrayList) NotificationLockscreenUserManagerImpl.this.mListeners).iterator();
                    while (it.hasNext()) {
                        ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserRemoved(intExtra);
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
                        int i = intExtra2;
                        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = NotificationLockscreenUserManagerImpl.this;
                        Uri uri3 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
                        notificationLockscreenUserManagerImpl3.mLockscreenSettingsObserver.onChange(false, notificationLockscreenUserManagerImpl3.mLockScreenUris, 0, UserHandle.of(i));
                        notificationLockscreenUserManagerImpl3.updateDpcSettings(i);
                        if (Flags.keyguardPrivateNotifications()) {
                            notificationLockscreenUserManagerImpl3.mKeyguardAllowingNotifications = notificationLockscreenUserManagerImpl3.mKeyguardManager.getPrivateNotificationsAllowed();
                        }
                    }
                });
                return;
            }
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = NotificationLockscreenUserManagerImpl.this;
            Uri uri3 = NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN;
            notificationLockscreenUserManagerImpl3.getClass();
            if (!(com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) ? !(Objects.equals(action, "android.intent.action.MANAGED_PROFILE_AVAILABLE") || Objects.equals(action, "android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) : !(Objects.equals(action, "android.intent.action.PROFILE_AVAILABLE") || Objects.equals(action, "android.intent.action.PROFILE_UNAVAILABLE"))) {
                NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                return;
            }
            if (Objects.equals(action, "android.intent.action.USER_UNLOCKED")) {
                if (Flags.keyguardPrivateNotifications() || ((OverviewProxyService) NotificationLockscreenUserManagerImpl.this.mOverviewProxyServiceLazy.get()).mOverviewProxy != null) {
                    return;
                }
                ((OverviewProxyService) NotificationLockscreenUserManagerImpl.this.mOverviewProxyServiceLazy.get()).startConnectionToCurrentUser();
                return;
            }
            if (Objects.equals(action, "com.android.systemui.statusbar.work_challenge_unlocked_notification_action")) {
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
                    NotificationVisibilityProviderImpl notificationVisibilityProviderImpl = (NotificationVisibilityProviderImpl) ((NotificationVisibilityProvider) NotificationLockscreenUserManagerImpl.this.mVisibilityProviderLazy.get());
                    NotificationEntry entry = ((NotifPipeline) notificationVisibilityProviderImpl.notifCollection).mNotifCollection.getEntry(stringExtra);
                    if (entry != null) {
                        obtain = notificationVisibilityProviderImpl.obtain(entry);
                    } else {
                        com.android.systemui.Flags.notificationsLiveDataStoreRefactor();
                        obtain = NotificationVisibility.obtain(stringExtra, -1, ((Number) ((NotifLiveDataStoreImpl) notificationVisibilityProviderImpl.notifDataStore).activeNotifCount.atomicValue.get()).intValue(), false);
                    }
                    NotificationLockscreenUserManagerImpl.this.mClickNotifier.onNotificationClick(stringExtra, obtain);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$1] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$2] */
    public NotificationLockscreenUserManagerImpl(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, Lazy lazy3, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Executor executor, Executor executor2, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlagsClassic featureFlagsClassic, Lazy lazy4) {
        ArrayList arrayList = new ArrayList();
        this.mLockScreenUris = arrayList;
        this.mKeyguardReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.1
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
        this.mAllUsersReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.2
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
        this.mBaseBroadcastReceiver = new AnonymousClass3();
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.4
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
                    ((BaseHeadsUpManager) statusBarNotificationPresenter.mHeadsUpManager).mUser = notificationLockscreenUserManagerImpl.mCurrentUserId;
                    statusBarNotificationPresenter.mCommandQueue.animateCollapsePanels();
                    statusBarNotificationPresenter.mMediaManager.clearCurrentMediaNotification();
                } else {
                    Log.w("LockscreenUserManager", "user switch before setup with presenter", new Exception());
                }
                Iterator it = ((ArrayList) notificationLockscreenUserManagerImpl.mListeners).iterator();
                while (it.hasNext()) {
                    ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserChanged(notificationLockscreenUserManagerImpl.mCurrentUserId);
                }
            }
        };
        this.mCurrentProfiles = new SparseArray();
        this.mCurrentManagedProfiles = new SparseArray();
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
        this.mOverviewProxyServiceLazy = lazy3;
        statusBarStateController.addCallback(this);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardManager = keyguardManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSecureSettings = secureSettings;
        this.mKeyguardStateController = keyguardStateController;
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            this.mSettingsHelperLazy = lazy4;
        }
        arrayList.add(SHOW_LOCKSCREEN);
        arrayList.add(SHOW_PRIVATE_LOCKSCREEN);
        dumpManager.registerDumpable(this);
        if (Flags.keyguardPrivateNotifications()) {
            init$11$1();
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

    public final void init$11$1() {
        this.mLockscreenSettingsObserver = new AnonymousClass5(this.mBackgroundExecutor);
        new ExecutorContentObserver(this.mMainExecutor) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.6
            public final void onChange(boolean z) {
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                ((DeviceProvisionedControllerImpl) NotificationLockscreenUserManagerImpl.this.mDeviceProvisionedController).deviceProvisioned.get();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(SHOW_LOCKSCREEN, false, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(SHOW_PRIVATE_LOCKSCREEN, true, this.mLockscreenSettingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        Executor executor = this.mBackgroundExecutor;
        UserHandle userHandle = UserHandle.ALL;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        broadcastDispatcher.registerReceiver(this.mAllUsersReceiver, intentFilter, executor, userHandle);
        if (Flags.keyguardPrivateNotifications()) {
            broadcastDispatcher.registerReceiver(this.mKeyguardReceiver, new IntentFilter("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED"), this.mBackgroundExecutor, userHandle);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            intentFilter2.addAction("android.intent.action.PROFILE_AVAILABLE");
            intentFilter2.addAction("android.intent.action.PROFILE_UNAVAILABLE");
        }
        broadcastDispatcher.registerReceiver(this.mBaseBroadcastReceiver, intentFilter2, null, userHandle);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        this.mContext.registerReceiver(this.mBaseBroadcastReceiver, intentFilter3, "com.android.systemui.permission.SELF", null, 2);
        UserTracker.Callback callback = this.mUserChangedCallback;
        Executor executor2 = this.mMainExecutor;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        userTrackerImpl.addCallback(callback, executor2);
        this.mCurrentUserId = userTrackerImpl.getUserId();
        updateCurrentProfilesCache();
        this.mBackgroundExecutor.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1(this, 0));
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

    public final boolean needsRedaction(NotificationEntry notificationEntry) {
        NotificationListenerService.Ranking ranking;
        int userId = notificationEntry.mSbn.getUserId();
        boolean z = !userAllowsPrivateNotificationsInPublic(this.mCurrentUserId);
        boolean contains = this.mCurrentManagedProfiles.contains(userId);
        boolean z2 = !userAllowsPrivateNotificationsInPublic(userId);
        boolean z3 = Flags.redactSensitiveContentNotificationsOnLockscreen() && (ranking = notificationEntry.mRanking) != null && ranking.hasSensitiveContent();
        boolean z4 = (!contains && z) || z2 || (z && isLockscreenPublicMode(this.mCurrentUserId) && SemPersonaManager.isSecureFolderId(userId));
        boolean z5 = (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && ((SettingsHelper) this.mSettingsHelperLazy.get()).isAllowPrivateNotificationsWhenUnsecure(userId)) || notificationEntry.mSbn.getNotification().visibility == 0;
        boolean packageHasVisibilityOverride = packageHasVisibilityOverride(notificationEntry.mSbn.getKey());
        if (!Flags.keyguardPrivateNotifications()) {
            if (packageHasVisibilityOverride || z3) {
                return true;
            }
            return z5 && z4;
        }
        if (!this.mKeyguardAllowingNotifications || z3 || packageHasVisibilityOverride) {
            return true;
        }
        return z5 && z4;
    }

    public final void notifyNotificationStateChanged() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            this.mMainExecutor.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1(this, 2));
            return;
        }
        Iterator it = this.mNotifStateChangedListeners.iterator();
        while (it.hasNext()) {
            Iterator<E> it2 = ((NotifUiAdjustmentProvider$notifStateChangedListener$1) it.next()).this$0.dirtyListeners.iterator();
            while (it2.hasNext()) {
                ((Runnable) it2.next()).run();
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

    public void setLockscreenPublicMode(boolean z, int i) {
        this.mLockscreenPublicMode.put(i, z);
    }

    public final void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            try {
                this.mCurrentProfiles.clear();
                this.mCurrentManagedProfiles.clear();
                if (this.mUserManager != null) {
                    for (UserInfo userInfo : android.multiuser.Flags.supportCommunalProfile() ? this.mUserManager.getProfilesIncludingCommunal(this.mCurrentUserId) : this.mUserManager.getProfiles(this.mCurrentUserId)) {
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
        this.mMainExecutor.execute(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1(this, 1));
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
        boolean z = Flags.keyguardPrivateNotifications() ? this.mUsersUsersAllowingNotifications.get(this.mCurrentUserId) : this.mUsersUsersAllowingNotifications.get(this.mCurrentUserId) && this.mKeyguardAllowingNotifications;
        boolean z2 = this.mUsersDpcAllowingNotifications.get(this.mCurrentUserId, true);
        boolean z3 = this.mShowLockscreenNotifications;
        boolean z4 = z && z2;
        this.mShowLockscreenNotifications = z4;
        return z3 != z4;
    }

    public final void updatePublicMode() {
        boolean z = this.mState != 0 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        SparseArray sparseArray = this.mCurrentProfiles;
        SparseBooleanArray clone = this.mLockscreenPublicMode.clone();
        SparseBooleanArray clone2 = this.mUsersWithSeparateWorkChallenge.clone();
        this.mUsersWithSeparateWorkChallenge.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            final int i = ((UserInfo) sparseArray.valueAt(size)).id;
            boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                    return Boolean.valueOf(notificationLockscreenUserManagerImpl.mLockPatternUtils.isSeparateProfileChallengeEnabled(i));
                }
            })).booleanValue();
            setLockscreenPublicMode((z || i == this.mCurrentUserId || !booleanValue || !this.mLockPatternUtils.isSecure(i)) ? z : z || this.mKeyguardManager.isDeviceLocked(i), i);
            this.mUsersWithSeparateWorkChallenge.put(i, booleanValue);
        }
        if (this.mLockscreenPublicMode.equals(clone) && this.mUsersWithSeparateWorkChallenge.equals(clone2)) {
            return;
        }
        notifyNotificationStateChanged();
    }

    public final boolean updateUserShowSettings(int i) {
        boolean z = this.mUsersUsersAllowingNotifications.get(i);
        boolean z2 = this.mSecureSettings.getIntForUser(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 1, i) != 0;
        this.mUsersUsersAllowingNotifications.put(i, z2);
        if (Flags.keyguardPrivateNotifications()) {
            return z2 != z;
        }
        boolean z3 = this.mKeyguardAllowingNotifications;
        boolean privateNotificationsAllowed = this.mKeyguardManager.getPrivateNotificationsAllowed();
        this.mKeyguardAllowingNotifications = privateNotificationsAllowed;
        return z2 != z || (z3 != privateNotificationsAllowed);
    }

    public final boolean userAllowsNotificationsInPublic(int i) {
        if (i == -1 || this.mCurrentManagedProfiles.contains(i)) {
            i = this.mCurrentUserId;
        }
        if (this.mUsersUsersAllowingNotifications.indexOfKey(i) < 0) {
            Log.wtf("LockscreenUserManager", "Asking for show notifs setting too early", new Throwable());
            updateUserShowSettings(i);
        }
        if (this.mUsersDpcAllowingNotifications.indexOfKey(i) < 0) {
            Log.wtf("LockscreenUserManager", "Asking for show notifs dpm override too early", new Throwable());
            updateDpcSettings(i);
        }
        return Flags.keyguardPrivateNotifications() ? this.mUsersUsersAllowingNotifications.get(i) && this.mUsersDpcAllowingNotifications.get(i) : this.mUsersUsersAllowingNotifications.get(i) && this.mUsersDpcAllowingNotifications.get(i) && this.mKeyguardAllowingNotifications;
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
            return Flags.keyguardPrivateNotifications() ? this.mUsersUsersAllowingPrivateNotifications.get(i) && this.mUsersDpcAllowingPrivateNotifications.get(i) && this.mKeyguardAllowingNotifications : this.mUsersUsersAllowingPrivateNotifications.get(i) && this.mUsersDpcAllowingPrivateNotifications.get(i);
        }
        Log.i("LockscreenUserManager", "Asking for redact notifs dpm override too early", new Throwable());
        return false;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$5, reason: invalid class name */
    public final class AnonymousClass5 extends ExecutorContentObserver {
        public AnonymousClass5(Executor executor) {
            super(executor);
        }

        public final void onChange(boolean z, Collection collection, int i) {
            List users = NotificationLockscreenUserManagerImpl.this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                onChange(z, collection, i, ((UserInfo) users.get(size)).getUserHandle());
            }
        }

        public final void onChange(boolean z, Collection collection, int i, UserHandle userHandle) {
            Iterator it = collection.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                Uri uri = (Uri) it.next();
                if (NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN.equals(uri)) {
                    z2 |= NotificationLockscreenUserManagerImpl.this.updateUserShowSettings(userHandle.getIdentifier());
                } else if (NotificationLockscreenUserManagerImpl.SHOW_PRIVATE_LOCKSCREEN.equals(uri)) {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                    int identifier = userHandle.getIdentifier();
                    boolean z3 = notificationLockscreenUserManagerImpl.mUsersUsersAllowingPrivateNotifications.get(identifier);
                    boolean z4 = notificationLockscreenUserManagerImpl.mSecureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, identifier) != 0;
                    notificationLockscreenUserManagerImpl.mUsersUsersAllowingPrivateNotifications.put(identifier, z4);
                    z2 |= z4 != z3;
                }
            }
            if (NotificationLockscreenUserManagerImpl.this.mCurrentUserId == userHandle.getIdentifier()) {
                z2 |= NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
            }
            if (z2) {
                NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
            }
        }
    }
}
