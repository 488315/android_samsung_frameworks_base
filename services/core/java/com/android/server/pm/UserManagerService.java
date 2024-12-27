package com.android.server.pm;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.IStopUserCallback;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.LauncherUserInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackagePartitions;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.UserProperties;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.display.AmbientDisplayConfiguration;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IProgressListener;
import android.os.IUserManager;
import android.os.IUserRestrictionsListener;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.service.voice.VoiceInteractionManagerInternal;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.SetScreenLockDialogActivity;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.BundleUtils;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.am.UserState;
import com.android.server.knox.ContainerDependencyWrapper;
import com.android.server.locksettings.LockSettingsService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerShellCommand;
import com.android.server.pm.ResilientAtomicFile;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.UserTypeDetails;
import com.android.server.pm.UserTypeFactory;
import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.core.pm.multiuser.MultiUserSupportsHelper;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.multiuser.MultiUserManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda3;
import com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda8;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

public final class UserManagerService extends IUserManager.Stub {
    static final int MAX_RECENTLY_REMOVED_IDS_SIZE = 100;
    static final int MAX_USER_ID = 21473;
    static final int MIN_USER_ID = 10;
    public static Context sContext;
    public static UserManagerService sInstance;
    public ActivityManagerInternal mAmInternal;
    public IAppOpsService mAppOpsService;
    public final Object mAppRestrictionsLock;
    public final RestrictionsSet mAppliedUserRestrictions;
    public final RestrictionsSet mBaseUserRestrictions;
    public int mBootUser;
    public final CountDownLatch mBootUserLatch;
    public final RestrictionsSet mCachedEffectiveUserRestrictions;
    public final AnonymousClass1 mConfigurationChangeReceiver;
    public final Context mContext;
    public IDarManagerService mDarManagerService;
    public final AnonymousClass1 mDeviceInactivityBroadcastReceiver;
    public DevicePolicyManagerInternal mDevicePolicyManagerInternal;
    public final RestrictionsSet mDevicePolicyUserRestrictions;
    public final AnonymousClass1 mDisableQuietModeCallback;
    public boolean mForceEphemeralUsers;
    public final Bundle mGuestRestrictions;
    public final MainHandler mHandler;
    public final ThreadPoolExecutor mInternalExecutor;
    public boolean mIsDeviceInactivityBroadcastReceiverRegistered;
    public boolean mIsDeviceManaged;
    public final SparseBooleanArray mIsUserManaged;
    public UserManagerService$$ExternalSyntheticLambda3 mKeyguardLockedStateListener;
    public final Configuration mLastConfiguration;
    public final LocalService mLocalService;
    public final LockPatternUtils mLockPatternUtils;
    public int mNextSerialNumber;
    public final AtomicReference mOwnerName;
    public final TypedValue mOwnerNameTypedValue;
    public final Object mPackagesLock;
    public final PackageManagerService mPm;
    public PackageManagerInternal mPmInternal;
    public final SettingsObserver mPrivateSpaceAutoLockSettingsObserver;
    public PrivateSpaceAutoLockTimer mPrivateSpaceAutoLockTimer;
    public final LinkedList mRecentlyRemovedIds;
    public final SparseBooleanArray mRemovingUserIds;
    public final Object mRestrictionsLock;
    public final UserSystemPackageInstaller mSystemPackageInstaller;
    public final boolean mUpdatingSystemUserMode;
    public final UserDataPreparer mUserDataPreparer;
    public int[] mUserIds;
    public int[] mUserIdsIncludingPreCreated;
    public final UserJourneyLogger mUserJourneyLogger;
    public final ArrayList mUserLifecycleListeners;
    public final File mUserListFile;
    public final IBinder mUserRestrictionToken;
    public final ArrayList mUserRestrictionsListeners;
    public final WatchedUserStates mUserStates;
    public int mUserTypeVersion;
    public final ArrayMap mUserTypes;
    public int mUserVersion;
    public final UserVisibilityMediator mUserVisibilityMediator;
    public final SparseArray mUsers;
    public final File mUsersDir;
    public final Object mUsersLock;
    public final PersonaManagerService sPersonaManager;
    public static final String USER_INFO_DIR = AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("system"), File.separator, "users");
    public static final long PRIVATE_SPACE_AUTO_LOCK_INACTIVITY_ALARM_WINDOW_MS = TimeUnit.SECONDS.toMillis(55);

    /* renamed from: com.android.server.pm.UserManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        public AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                int[] runningUserIds = ActivityManager.getService().getRunningUserIds();
                synchronized (UserManagerService.this.mRestrictionsLock) {
                    for (int i : runningUserIds) {
                        try {
                            UserManagerService userManagerService = UserManagerService.this;
                            userManagerService.updateUserRestrictionsInternalLR(i, null);
                            userManagerService.scheduleWriteUser(i);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            } catch (RemoteException unused) {
                Slog.w("UserManagerService", "Unable to access ActivityManagerService");
            }
        }
    }

    /* renamed from: com.android.server.pm.UserManagerService$7, reason: invalid class name */
    public final class AnonymousClass7 extends IIntentReceiver.Stub {
        public final /* synthetic */ int val$userId;

        public AnonymousClass7(int i) {
            this.val$userId = i;
        }

        public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            final int i3 = this.val$userId;
            new Thread(new Runnable() { // from class: com.android.server.pm.UserManagerService$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManagerService.AnonymousClass7 anonymousClass7 = UserManagerService.AnonymousClass7.this;
                    int i4 = i3;
                    UserManagerService.this.getActivityManagerInternal().onUserRemoved(i4);
                    UserManagerService.this.removeUserState(i4);
                }
            }).start();
        }
    }

    public final class DisableQuietModeUserUnlockedCallback extends IProgressListener.Stub {
        public final IntentSender mTarget;

        public DisableQuietModeUserUnlockedCallback(IntentSender intentSender) {
            Objects.requireNonNull(intentSender);
            this.mTarget = intentSender;
        }

        public final void onFinished(int i, Bundle bundle) {
            UserManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.UserManagerService$DisableQuietModeUserUnlockedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManagerService.DisableQuietModeUserUnlockedCallback disableQuietModeUserUnlockedCallback = UserManagerService.DisableQuietModeUserUnlockedCallback.this;
                    disableQuietModeUserUnlockedCallback.getClass();
                    try {
                        UserManagerService.this.mContext.startIntentSender(disableQuietModeUserUnlockedCallback.mTarget, null, 0, 0, 0, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                    } catch (IntentSender.SendIntentException e) {
                        Slog.e("UserManagerService", "Failed to start the target in the callback", e);
                    }
                }
            });
        }

        public final void onProgress(int i, int i2, Bundle bundle) {
        }

        public final void onStarted(int i, Bundle bundle) {
        }
    }

    public final class LifeCycle extends SystemService {
        public UserManagerService mUms;

        public LifeCycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBootPhase(int r11) {
            /*
                Method dump skipped, instructions count: 312
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.LifeCycle.onBootPhase(int):void");
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? userManagerService = UserManagerService.getInstance();
            this.mUms = userManagerService;
            publishBinderService("user", userManagerService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.startRealtime = SystemClock.elapsedRealtime();
                        if (targetUser.getUserIdentifier() == 0 && targetUser.isFull()) {
                            UserManagerService userManagerService = this.mUms;
                            userManagerService.getClass();
                            userDataLU.mLastEnteredForegroundTimeMillis = System.currentTimeMillis();
                            userManagerService.scheduleWriteUser(userDataLU.info.id);
                        }
                        if (MaintenanceModeUtils.isMaintenanceModeUser(userDataLU.info)) {
                            MaintenanceModeManager maintenanceModeManager = this.mUms.mPm.mCustomInjector.getMaintenanceModeManager();
                            maintenanceModeManager.getClass();
                            maintenanceModeManager.mHandler.post(new MaintenanceModeManager$$ExternalSyntheticLambda3(2, maintenanceModeManager));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.startRealtime = 0L;
                        userDataLU.unlockRealtime = 0L;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    UserData userDataLU = this.mUms.getUserDataLU(targetUser2.getUserIdentifier());
                    if (userDataLU != null) {
                        UserManagerService userManagerService = this.mUms;
                        userManagerService.getClass();
                        userDataLU.mLastEnteredForegroundTimeMillis = System.currentTimeMillis();
                        userManagerService.scheduleWriteUser(userDataLU.info.id);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocked(SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null && MaintenanceModeUtils.isMaintenanceModeUser(userDataLU.info)) {
                        MaintenanceModeManager maintenanceModeManager = this.mUms.mPm.mCustomInjector.getMaintenanceModeManager();
                        maintenanceModeManager.getClass();
                        maintenanceModeManager.mHandler.post(new MaintenanceModeManager$$ExternalSyntheticLambda3(3, maintenanceModeManager));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            boolean z;
            synchronized (this.mUms.mUsersLock) {
                try {
                    UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.unlockRealtime = SystemClock.elapsedRealtime();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (targetUser.getUserIdentifier() == 0 && UserManager.isCommunalProfileEnabled()) {
                int communalProfileIdUnchecked = this.mUms.getCommunalProfileIdUnchecked();
                if (communalProfileIdUnchecked == -10000) {
                    Slogf.w("UserManagerService", "Cannot start Communal Profile because there isn't one");
                    return;
                }
                Slogf.d("UserManagerService", "Starting the Communal Profile");
                try {
                    z = ActivityManager.getService().startProfile(communalProfileIdUnchecked);
                } catch (RemoteException e) {
                    e.rethrowAsRuntimeException();
                    z = false;
                }
                if (z) {
                    return;
                }
                Slogf.wtf("UserManagerService", "Failed to start communal profile userId=%d", Integer.valueOf(communalProfileIdUnchecked));
            }
        }
    }

    public final class LocalService extends UserManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void addMaintenanceModeLifecycleListener(LockSettingsService.AnonymousClass1 anonymousClass1) {
            MaintenanceModeManager maintenanceModeManager = UserManagerService.this.mPm.mCustomInjector.getMaintenanceModeManager();
            synchronized (maintenanceModeManager.mLifecycleListeners) {
                try {
                    if (!maintenanceModeManager.mLifecycleListeners.contains(anonymousClass1)) {
                        maintenanceModeManager.logDebugInfoAsync("addLifecycleListener: " + anonymousClass1);
                        maintenanceModeManager.mLifecycleListeners.add(anonymousClass1);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void addUserLifecycleListener(UserManagerInternal.UserLifecycleListener userLifecycleListener) {
            synchronized (UserManagerService.this.mUserLifecycleListeners) {
                UserManagerService.this.mUserLifecycleListeners.add(userLifecycleListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void addUserRestrictionsListener(UserManagerInternal.UserRestrictionsListener userRestrictionsListener) {
            synchronized (UserManagerService.this.mUserRestrictionsListeners) {
                UserManagerService.this.mUserRestrictionsListeners.add(userRestrictionsListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void addUserVisibilityListener(UserManagerInternal.UserVisibilityListener userVisibilityListener) {
            UserVisibilityMediator userVisibilityMediator = UserManagerService.this.mUserVisibilityMediator;
            if (UserVisibilityMediator.DBG) {
                userVisibilityMediator.getClass();
                Slogf.d("UserVisibilityMediator", "adding listener %s", userVisibilityListener);
            }
            synchronized (userVisibilityMediator.mLock) {
                userVisibilityMediator.mListeners.add(userVisibilityListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int assignUserToDisplayOnStart(int r17, int r18, int r19, int r20) {
            /*
                Method dump skipped, instructions count: 387
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.LocalService.assignUserToDisplayOnStart(int, int, int, int):int");
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean clearAttributes(int i, int i2) {
            UserData userData;
            boolean z = false;
            if (i2 <= 0) {
                return false;
            }
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    int size = UserManagerService.this.mUsers.size();
                    int i3 = 0;
                    while (true) {
                        userData = null;
                        if (i3 >= size) {
                            break;
                        }
                        UserInfo userInfo = ((UserData) UserManagerService.this.mUsers.valueAt(i3)).info;
                        if (userInfo.id == i) {
                            if ((userInfo.getAttributes() & i2) > 0) {
                                userData = new UserData();
                                userInfo.setAttributes((~i2) & userInfo.getAttributes());
                                userData.userProperties = UserManagerService.this.getUserPropertiesInternal(i);
                                userData.info = userInfo;
                            }
                            z = true;
                        } else {
                            i3++;
                        }
                    }
                    if (userData != null) {
                        UserManagerService.this.writeUserLP(userData);
                    }
                } finally {
                }
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final UserInfo createUserEvenWhenDisallowed(String str, String str2, int i, String[] strArr, Object obj) {
            return UserManagerService.this.createUserInternalUnchecked(str, str2, i, -10000, false, strArr, obj);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean exists(int i) {
            return UserManagerService.this.getUserInfoNoChecks(i) != null;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getAttributes(int i) {
            int attributes;
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    UserInfo userInfoLU = UserManagerService.this.getUserInfoLU(i);
                    attributes = userInfoLU != null ? userInfoLU.getAttributes() : -1;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return attributes;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getBootUser(boolean z) {
            UserManagerService userManagerService = UserManagerService.this;
            if (z) {
                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("wait-boot-user");
                try {
                    if (userManagerService.mBootUserLatch.getCount() != 0) {
                        Slogf.d("UserManagerService", "Sleeping for boot user to be set. Max sleep for Time: %d", 300000L);
                    }
                    if (!userManagerService.mBootUserLatch.await(300000L, TimeUnit.MILLISECONDS)) {
                        Slogf.w("UserManagerService", "Boot user not set. Timeout: %d", 300000L);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Slogf.w("UserManagerService", e, "InterruptedException during wait for boot user.", new Object[0]);
                }
                timingsTraceAndSlog.traceEnd();
            }
            return userManagerService.getBootUserUnchecked();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getCommunalProfileId() {
            return UserManagerService.this.getCommunalProfileIdUnchecked();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final LauncherUserInfo getLauncherUserInfo(int i) {
            UserInfo userInfoLU;
            synchronized (UserManagerService.this.mUsersLock) {
                userInfoLU = UserManagerService.this.getUserInfoLU(i);
            }
            if (userInfoLU != null) {
                return new LauncherUserInfo.Builder(UserManagerService.this.getUserTypeDetails(userInfoLU).mName, userInfoLU.serialNumber).build();
            }
            return null;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getMainDisplayAssignedToUser(int i) {
            return UserManagerService.this.mUserVisibilityMediator.getMainDisplayAssignedToUser(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getMainUserId() {
            return UserManagerService.this.getMainUserIdUnchecked();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int[] getProfileIds(int i, boolean z) {
            int[] array;
            synchronized (UserManagerService.this.mUsersLock) {
                array = UserManagerService.this.getProfileIdsLU(null, z, false, i).toArray();
            }
            return array;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getProfileParentId(int i) {
            return UserManagerService.this.getProfileParentIdUnchecked(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int getUserAssignedToDisplay(int i) {
            return UserManagerService.this.mUserVisibilityMediator.getUserAssignedToDisplay(i, true);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int[] getUserIds() {
            return UserManagerService.this.getUserIds();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final UserInfo getUserInfo(int i) {
            UserData userData;
            synchronized (UserManagerService.this.mUsersLock) {
                userData = (UserData) UserManagerService.this.mUsers.get(i);
            }
            if (userData == null) {
                return null;
            }
            return userData.info;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final UserInfo[] getUserInfos() {
            UserInfo[] userInfoArr;
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    int size = UserManagerService.this.mUsers.size();
                    userInfoArr = new UserInfo[size];
                    for (int i = 0; i < size; i++) {
                        userInfoArr[i] = ((UserData) UserManagerService.this.mUsers.valueAt(i)).info;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return userInfoArr;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final UserProperties getUserProperties(int i) {
            UserProperties userPropertiesInternal = UserManagerService.this.getUserPropertiesInternal(i);
            if (userPropertiesInternal == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "A null UserProperties was returned for user ", "UserManagerService");
            }
            return userPropertiesInternal;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean getUserRestriction(int i, String str) {
            return UserManagerService.this.getUserRestrictions(i).getBoolean(str);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final int[] getUserTypesForStatsd(int[] iArr) {
            if (iArr == null) {
                return null;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length];
            for (int i = 0; i < length; i++) {
                UserInfo userInfo = getUserInfo(iArr[i]);
                UserManagerService userManagerService = UserManagerService.this;
                if (userInfo == null) {
                    UserJourneyLogger userJourneyLogger = userManagerService.mUserJourneyLogger;
                    iArr2[i] = 0;
                } else {
                    UserJourneyLogger userJourneyLogger2 = userManagerService.mUserJourneyLogger;
                    iArr2[i] = UserJourneyLogger.getUserTypeForStatsd(userInfo.userType);
                }
            }
            return iArr2;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final List getUsers(boolean z) {
            return UserManagerService.this.getUsersInternal(true, z, true);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean hasUserRestriction(String str, int i) {
            if (UserRestrictionsUtils.isValidRestriction(str)) {
                return UserManagerService.this.getEffectiveUserRestrictions(i).getBoolean(str);
            }
            return false;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isDeviceManaged() {
            boolean z;
            synchronized (UserManagerService.this.mUsersLock) {
                z = UserManagerService.this.mIsDeviceManaged;
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x003b, code lost:
        
            return false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0058, code lost:
        
            android.util.Slog.w("UserManagerService", r8 + " for disabled profile " + r7 + " from " + r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x007d, code lost:
        
            android.util.Slog.w("UserManagerService", r8 + " for another profile " + r7 + " from " + r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
        
            return false;
         */
        @Override // com.android.server.pm.UserManagerInternal
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isProfileAccessible(int r6, int r7, java.lang.String r8, boolean r9) {
            /*
                r5 = this;
                r0 = 1
                if (r7 != r6) goto L4
                return r0
            L4:
                com.android.server.pm.UserManagerService r1 = com.android.server.pm.UserManagerService.this
                java.lang.Object r1 = r1.mUsersLock
                monitor-enter(r1)
                com.android.server.pm.UserManagerService r2 = com.android.server.pm.UserManagerService.this     // Catch: java.lang.Throwable -> L35
                android.content.pm.UserInfo r2 = r2.getUserInfoLU(r6)     // Catch: java.lang.Throwable -> L35
                r3 = 0
                if (r2 == 0) goto L7b
                boolean r4 = r2.isProfile()     // Catch: java.lang.Throwable -> L35
                if (r4 == 0) goto L19
                goto L7b
            L19:
                com.android.server.pm.UserManagerService r5 = com.android.server.pm.UserManagerService.this     // Catch: java.lang.Throwable -> L35
                android.content.pm.UserInfo r5 = r5.getUserInfoLU(r7)     // Catch: java.lang.Throwable -> L35
                if (r5 == 0) goto L56
                boolean r4 = r5.isEnabled()     // Catch: java.lang.Throwable -> L35
                if (r4 != 0) goto L28
                goto L56
            L28:
                int r5 = r5.profileGroupId     // Catch: java.lang.Throwable -> L35
                r6 = -10000(0xffffffffffffd8f0, float:NaN)
                if (r5 == r6) goto L38
                int r6 = r2.profileGroupId     // Catch: java.lang.Throwable -> L35
                if (r5 == r6) goto L33
                goto L38
            L33:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
                return r0
            L35:
                r5 = move-exception
                goto Lc2
            L38:
                if (r9 != 0) goto L3c
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
                return r3
            L3c:
                java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L35
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
                r6.<init>()     // Catch: java.lang.Throwable -> L35
                r6.append(r8)     // Catch: java.lang.Throwable -> L35
                java.lang.String r8 = " for unrelated profile "
                r6.append(r8)     // Catch: java.lang.Throwable -> L35
                r6.append(r7)     // Catch: java.lang.Throwable -> L35
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L35
                r5.<init>(r6)     // Catch: java.lang.Throwable -> L35
                throw r5     // Catch: java.lang.Throwable -> L35
            L56:
                if (r9 == 0) goto L79
                java.lang.String r5 = "UserManagerService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
                r9.<init>()     // Catch: java.lang.Throwable -> L35
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                java.lang.String r8 = " for disabled profile "
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                java.lang.String r7 = " from "
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                r9.append(r6)     // Catch: java.lang.Throwable -> L35
                java.lang.String r6 = r9.toString()     // Catch: java.lang.Throwable -> L35
                android.util.Slog.w(r5, r6)     // Catch: java.lang.Throwable -> L35
            L79:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
                return r3
            L7b:
                if (r9 != 0) goto La0
                java.lang.String r5 = "UserManagerService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
                r9.<init>()     // Catch: java.lang.Throwable -> L35
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                java.lang.String r8 = " for another profile "
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                java.lang.String r7 = " from "
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                r9.append(r6)     // Catch: java.lang.Throwable -> L35
                java.lang.String r6 = r9.toString()     // Catch: java.lang.Throwable -> L35
                android.util.Slog.w(r5, r6)     // Catch: java.lang.Throwable -> L35
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
                return r3
            La0:
                java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L35
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
                r9.<init>()     // Catch: java.lang.Throwable -> L35
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                java.lang.String r8 = " for another profile "
                r9.append(r8)     // Catch: java.lang.Throwable -> L35
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                java.lang.String r7 = " from "
                r9.append(r7)     // Catch: java.lang.Throwable -> L35
                r9.append(r6)     // Catch: java.lang.Throwable -> L35
                java.lang.String r6 = r9.toString()     // Catch: java.lang.Throwable -> L35
                r5.<init>(r6)     // Catch: java.lang.Throwable -> L35
                throw r5     // Catch: java.lang.Throwable -> L35
            Lc2:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.LocalService.isProfileAccessible(int, int, java.lang.String, boolean):boolean");
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserInitialized(int i) {
            UserInfo userInfo = getUserInfo(i);
            return (userInfo == null || (userInfo.flags & 16) == 0) ? false : true;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserManaged(int i) {
            boolean z;
            synchronized (UserManagerService.this.mUsersLock) {
                z = UserManagerService.this.mIsUserManaged.get(i);
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserRunning(int i) {
            int i2;
            synchronized (UserManagerService.this.mUserStates) {
                i2 = UserManagerService.this.mUserStates.get(i);
            }
            return (i2 == -1 || i2 == 4 || i2 == 5) ? false : true;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserUnlocked(int i) {
            int i2;
            if (SemPersonaManager.isDarDualEncrypted(i)) {
                return false;
            }
            synchronized (UserManagerService.this.mUserStates) {
                i2 = UserManagerService.this.mUserStates.get(i);
            }
            return (i2 == 4 || i2 == 5) ? StorageManager.isCeStorageUnlocked(i) : i2 == 3;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserUnlockingOrUnlocked(int i) {
            int i2;
            synchronized (UserManagerService.this.mUserStates) {
                i2 = UserManagerService.this.mUserStates.get(i);
            }
            return (i2 == 4 || i2 == 5) ? StorageManager.isCeStorageUnlocked(i) : i2 == 2 || i2 == 3;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserVisible(int i) {
            return UserManagerService.this.mUserVisibilityMediator.isUserVisible(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean isUserVisible(int i, int i2) {
            return UserManagerService.this.mUserVisibilityMediator.isUserVisible(i, i2);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void onEphemeralUserStop(int i) {
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    UserInfo userInfoLU = UserManagerService.this.getUserInfoLU(i);
                    if (userInfoLU != null && userInfoLU.isEphemeral()) {
                        userInfoLU.flags |= 64;
                        if (userInfoLU.isGuest()) {
                            userInfoLU.guestToRemove = true;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void onSystemUserVisibilityChanged() {
            UserVisibilityMediator userVisibilityMediator = UserManagerService.this.mUserVisibilityMediator;
            userVisibilityMediator.dispatchVisibilityChanged(userVisibilityMediator.mListeners, 0, true);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean removeUserEvenWhenDisallowed(int i) {
            return UserManagerService.this.removeUserWithProfilesUnchecked(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void removeUserLifecycleListener(PackageManagerShellCommand.AnonymousClass4 anonymousClass4) {
            synchronized (UserManagerService.this.mUserLifecycleListeners) {
                UserManagerService.this.mUserLifecycleListeners.remove(anonymousClass4);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void removeUserState(int i) {
            synchronized (UserManagerService.this.mUserStates) {
                UserManagerService.this.mUserStates.states.delete(i);
                UserManager.invalidateIsUserUnlockedCache();
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean setAttributes(int i, int i2) {
            UserData userData;
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, i2, "setAttributes, user ID: ", ", attribute: ", "UserManagerService");
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    int size = UserManagerService.this.mUsers.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            userData = null;
                            break;
                        }
                        UserInfo userInfo = ((UserData) UserManagerService.this.mUsers.valueAt(i3)).info;
                        if (userInfo.id == i) {
                            userData = new UserData();
                            userInfo.setAttributes(i2 | userInfo.getAttributes());
                            userData.userProperties = UserManagerService.this.getUserPropertiesInternal(i);
                            userData.info = userInfo;
                            break;
                        }
                        i3++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (userData != null) {
                UserManagerService.this.writeUserLP(userData);
                return true;
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setAttributes: user not found: ", "UserManagerService");
            return false;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setDefaultCrossProfileIntentFilters(int i, int i2) {
            UserManagerService userManagerService = UserManagerService.this;
            userManagerService.setDefaultCrossProfileIntentFilters(i2, userManagerService.getUserTypeDetailsNoChecks(i2), userManagerService.getEffectiveUserRestrictions(i2), i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setDeviceManaged(boolean z) {
            synchronized (UserManagerService.this.mUsersLock) {
                UserManagerService.this.mIsDeviceManaged = z;
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setDevicePolicyUserRestrictions(Bundle bundle, RestrictionsSet restrictionsSet) {
            UserManagerService userManagerService = UserManagerService.this;
            synchronized (userManagerService.mRestrictionsLock) {
                try {
                    IntArray userIds = userManagerService.mDevicePolicyUserRestrictions.getUserIds();
                    userManagerService.mCachedEffectiveUserRestrictions.mUserRestrictions.clear();
                    userManagerService.mDevicePolicyUserRestrictions.mUserRestrictions.clear();
                    userManagerService.mDevicePolicyUserRestrictions.updateRestrictions(-1, bundle);
                    IntArray userIds2 = restrictionsSet.getUserIds();
                    for (int i = 0; i < userIds2.size(); i++) {
                        int i2 = userIds2.get(i);
                        userManagerService.mDevicePolicyUserRestrictions.updateRestrictions(i2, restrictionsSet.getRestrictions(i2));
                        userIds.add(i2);
                    }
                    userManagerService.mCachedEffectiveUserRestrictions.mUserRestrictions.clear();
                    userManagerService.mHandler.post(userManagerService.new AnonymousClass5());
                    for (int i3 = 0; i3 < userIds.size(); i3++) {
                        if (userIds.get(i3) != -1) {
                            int i4 = userIds.get(i3);
                            userManagerService.updateUserRestrictionsInternalLR(i4, null);
                            userManagerService.scheduleWriteUser(i4);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean setDualDarInfo(int i, int i2) {
            boolean z;
            synchronized (UserManagerService.this.mUsersLock) {
                try {
                    UserData userData = (UserData) UserManagerService.this.mUsers.get(i);
                    z = false;
                    if (userData != null && (100663296 & i2) != 0) {
                        if ((67108864 & i2) != 0) {
                            Log.d("UserManagerService", "Set DUAL_DAR flag as custom crypto for user " + i);
                        } else if ((33554432 & i2) != 0) {
                            Log.d("UserManagerService", "Set DUAL_DAR flag as samsung crypto for user " + i);
                        } else {
                            i2 = 0;
                        }
                        if (i2 != 0) {
                            UserInfo userInfo = userData.info;
                            userInfo.flags = i2 | userInfo.flags;
                            UserManagerService.this.writeUserLP(userData);
                            z = true;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setForceEphemeralUsers(boolean z) {
            synchronized (UserManagerService.this.mUsersLock) {
                UserManagerService.this.mForceEphemeralUsers = z;
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setUserIcon(int i, Bitmap bitmap) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UserManagerService.this.mPackagesLock) {
                    UserData userDataNoChecks = UserManagerService.this.getUserDataNoChecks(i);
                    if (userDataNoChecks != null) {
                        UserInfo userInfo = userDataNoChecks.info;
                        if (!userInfo.partial) {
                            UserManagerService.m780$$Nest$mwriteBitmapLP(UserManagerService.this, userInfo, bitmap);
                            UserManagerService.this.writeUserLP(userDataNoChecks);
                            UserManagerService userManagerService = UserManagerService.this;
                            userManagerService.getClass();
                            Intent intent = new Intent("android.intent.action.USER_INFO_CHANGED");
                            intent.putExtra("android.intent.extra.user_handle", i);
                            intent.addFlags(1073741824);
                            userManagerService.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                            return;
                        }
                    }
                    Slog.w("UserManagerService", "setUserIcon: unknown user #" + i);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setUserManaged(int i, boolean z) {
            synchronized (UserManagerService.this.mUsersLock) {
                UserManagerService.this.mIsUserManaged.put(i, z);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setUserRestriction(int i, String str, boolean z) {
            UserManagerService.this.setUserRestrictionInner(i, str, z);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void setUserState(int i, int i2) {
            synchronized (UserManagerService.this.mUserStates) {
                UserManagerService.this.mUserStates.states.put(i, i2);
                UserManager.invalidateIsUserUnlockedCache();
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final boolean shouldIgnorePrepareStorageErrors(int i) {
            boolean z;
            synchronized (UserManagerService.this.mUsersLock) {
                UserData userData = (UserData) UserManagerService.this.mUsers.get(i);
                z = userData != null && userData.mIgnorePrepareStorageErrors;
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public final void unassignUserFromDisplayOnStop(int i) {
            IntArray visibleUsers;
            IntArray visibleUsers2;
            UserVisibilityMediator userVisibilityMediator = UserManagerService.this.mUserVisibilityMediator;
            if (UserVisibilityMediator.DBG) {
                userVisibilityMediator.getClass();
                Slogf.d("UserVisibilityMediator", "unassignUserFromDisplayOnStop(%d)", Integer.valueOf(i));
            }
            synchronized (userVisibilityMediator.mLock) {
                visibleUsers = userVisibilityMediator.getVisibleUsers();
                userVisibilityMediator.unassignUserFromAllDisplaysOnStopLocked(i);
                visibleUsers2 = userVisibilityMediator.getVisibleUsers();
            }
            userVisibilityMediator.dispatchVisibilityChanged(visibleUsers, visibleUsers2);
        }
    }

    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                removeMessages(2);
                synchronized (UserManagerService.this.mPackagesLock) {
                    UserManagerService.this.writeUserListLP();
                }
                return;
            }
            removeMessages(1, message.obj);
            synchronized (UserManagerService.this.mPackagesLock) {
                try {
                    int intValue = ((Integer) message.obj).intValue();
                    UserData userDataNoChecks = UserManagerService.this.getUserDataNoChecks(intValue);
                    if (userDataNoChecks != null) {
                        UserManagerService.this.writeUserLP(userDataNoChecks);
                    } else {
                        Slog.i("UserManagerService", "handle(WRITE_USER_MSG): no data for user " + intValue + ", it was probably removed before handler could handle it");
                    }
                } finally {
                }
            }
        }
    }

    public final class PrivateSpaceAutoLockTimer implements AlarmManager.OnAlarmListener {
        public final int mUserId;

        public PrivateSpaceAutoLockTimer(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            PowerManager powerManager = (PowerManager) UserManagerService.this.mContext.getSystemService(PowerManager.class);
            if (powerManager == null || powerManager.isInteractive()) {
                SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("Device is interactive, skipping auto-lock for profile user "), this.mUserId, "UserManagerService");
                return;
            }
            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("Auto-locking private space with user-id "), this.mUserId, "UserManagerService");
            UserManagerService userManagerService = UserManagerService.this;
            userManagerService.setQuietModeEnabledAsync(this.mUserId, true, null, userManagerService.mContext.getPackageName());
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(MainHandler mainHandler) {
            super(mainHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (UserManagerService.isAutoLockForPrivateSpaceEnabled() && TextUtils.equals(uri.getLastPathSegment(), "private_space_auto_lock")) {
                int intForUser = Settings.Secure.getIntForUser(UserManagerService.this.mContext.getContentResolver(), "private_space_auto_lock", 2, UserManagerService.this.getMainUserIdUnchecked());
                HermesService$3$$ExternalSyntheticOutline0.m(intForUser, "Auto-lock settings changed to ", "UserManagerService");
                UserManagerService.this.setOrUpdateAutoLockPreferenceForPrivateProfile(intForUser);
            }
        }
    }

    class UserData {
        public String account;
        public UserInfo info;
        public boolean mIgnorePrepareStorageErrors;
        public long mLastEnteredForegroundTimeMillis;
        public long mLastRequestQuietModeEnabledMillis;
        public boolean persistSeedData;
        public String seedAccountName;
        public PersistableBundle seedAccountOptions;
        public String seedAccountType;
        public long startRealtime;
        public long unlockRealtime;
        public UserProperties userProperties;
    }

    public final class WatchedUserStates {
        public final SparseIntArray states = new SparseIntArray();

        public WatchedUserStates() {
            UserManager.invalidateIsUserUnlockedCache();
        }

        public final int get(int i) {
            if (this.states.indexOfKey(i) >= 0) {
                return this.states.get(i);
            }
            return -1;
        }

        public final String toString() {
            return this.states.toString();
        }
    }

    public static int $r8$lambda$yIybRUvnMFarqB2JHfYXkMUsauk(UserManagerService userManagerService, int i, List list) {
        boolean z;
        if (i == 10152) {
            ArrayList arrayList = (ArrayList) userManagerService.getUsersInternal(true, true, true);
            int size = arrayList.size();
            if (size > 1) {
                for (int i2 = 0; i2 < size; i2++) {
                    UserInfo userInfo = (UserInfo) arrayList.get(i2);
                    int userTypeForStatsd = UserJourneyLogger.getUserTypeForStatsd(userInfo.userType);
                    String str = userTypeForStatsd == 0 ? userInfo.userType : null;
                    synchronized (userManagerService.mUserStates) {
                        z = userManagerService.mUserStates.get(userInfo.id) == 3;
                    }
                    list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.USER_INFO, userInfo.id, userTypeForStatsd, str, userInfo.flags, userInfo.creationTime, userInfo.lastLoggedInTime, z));
                }
            }
        } else {
            userManagerService.getClass();
            if (i != 10160) {
                Slogf.e("UserManagerService", "Unexpected atom tag: %d", Integer.valueOf(i));
                return 1;
            }
            if (UserManager.getMaxSupportedUsers() > 1) {
                list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.MULTI_USER_INFO, UserManager.getMaxSupportedUsers(), userManagerService.isUserSwitcherEnabled(-1), UserManager.supportsMultipleUsers() && !userManagerService.hasUserRestriction("no_add_user", -1)));
            }
        }
        return 0;
    }

    /* renamed from: -$$Nest$mwriteBitmapLP, reason: not valid java name */
    public static void m780$$Nest$mwriteBitmapLP(UserManagerService userManagerService, UserInfo userInfo, Bitmap bitmap) {
        userManagerService.getClass();
        try {
            File file = new File(userManagerService.mUsersDir, Integer.toString(userInfo.id));
            File file2 = new File(file, "photo.png");
            File file3 = new File(file, "photo.png.tmp");
            if (!file.exists()) {
                file.mkdir();
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            if (bitmap.compress(compressFormat, 100, fileOutputStream) && file3.renameTo(file2) && SELinux.restorecon(file2)) {
                userInfo.iconPath = file2.getAbsolutePath();
            }
            try {
                fileOutputStream.close();
            } catch (IOException unused) {
            }
            file3.delete();
        } catch (FileNotFoundException e) {
            Slog.w("UserManagerService", "Error setting photo for user ", e);
        }
    }

    public UserManagerService(Context context) {
        this(context, null, null, new Object(), context.getCacheDir(), null, null);
    }

    public UserManagerService(Context context, PackageManagerService packageManagerService, UserDataPreparer userDataPreparer, Object obj, File file, SparseArray sparseArray, PersonaManagerService personaManagerService) {
        String str;
        int i;
        UserInfo earliestCreatedFullUser;
        UserManagerService userManagerService = this;
        boolean z = false;
        userManagerService.mUsersLock = LockGuard.installNewLock(2, false);
        userManagerService.mRestrictionsLock = new Object();
        userManagerService.mAppRestrictionsLock = new Object();
        userManagerService.mUserRestrictionToken = new Binder();
        userManagerService.mDarManagerService = null;
        boolean z2 = 1;
        userManagerService.mBootUserLatch = new CountDownLatch(1);
        userManagerService.mBaseUserRestrictions = new RestrictionsSet();
        userManagerService.mCachedEffectiveUserRestrictions = new RestrictionsSet();
        userManagerService.mAppliedUserRestrictions = new RestrictionsSet();
        userManagerService.mDevicePolicyUserRestrictions = new RestrictionsSet();
        userManagerService.mGuestRestrictions = new Bundle();
        userManagerService.mRemovingUserIds = new SparseBooleanArray();
        userManagerService.mRecentlyRemovedIds = new LinkedList();
        userManagerService.mUserVersion = 0;
        userManagerService.mUserTypeVersion = 0;
        userManagerService.mIsUserManaged = new SparseBooleanArray();
        userManagerService.mUserRestrictionsListeners = new ArrayList();
        userManagerService.mUserLifecycleListeners = new ArrayList();
        userManagerService.mUserJourneyLogger = new UserJourneyLogger();
        final int i2 = 0;
        userManagerService.mDisableQuietModeCallback = new BroadcastReceiver(userManagerService) { // from class: com.android.server.pm.UserManagerService.1
            public final /* synthetic */ UserManagerService this$0;

            {
                this.this$0 = userManagerService;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        if ("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK".equals(intent.getAction())) {
                            IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
                            this.this$0.setQuietModeEnabledAsync(intent.getIntExtra("android.intent.extra.USER_ID", -10000), false, intentSender, intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
                            break;
                        }
                        break;
                    case 1:
                        if (UserManagerService.isAutoLockForPrivateSpaceEnabled()) {
                            if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                                    Slog.d("UserManagerService", "SCREEN_ON broadcast received, removing pending alarms to auto-lock private space");
                                    this.this$0.cancelPendingAutoLockAlarms();
                                    break;
                                }
                            } else {
                                this.this$0.maybeScheduleAlarmToAutoLockPrivateSpace();
                                break;
                            }
                        }
                        break;
                    default:
                        if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                            this.this$0.invalidateOwnerNameIfNecessary(context2.getResources(), false);
                            break;
                        }
                        break;
                }
            }
        };
        userManagerService.mIsDeviceInactivityBroadcastReceiverRegistered = false;
        final int i3 = 1;
        userManagerService.mDeviceInactivityBroadcastReceiver = new BroadcastReceiver(userManagerService) { // from class: com.android.server.pm.UserManagerService.1
            public final /* synthetic */ UserManagerService this$0;

            {
                this.this$0 = userManagerService;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i3) {
                    case 0:
                        if ("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK".equals(intent.getAction())) {
                            IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
                            this.this$0.setQuietModeEnabledAsync(intent.getIntExtra("android.intent.extra.USER_ID", -10000), false, intentSender, intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
                            break;
                        }
                        break;
                    case 1:
                        if (UserManagerService.isAutoLockForPrivateSpaceEnabled()) {
                            if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                                    Slog.d("UserManagerService", "SCREEN_ON broadcast received, removing pending alarms to auto-lock private space");
                                    this.this$0.cancelPendingAutoLockAlarms();
                                    break;
                                }
                            } else {
                                this.this$0.maybeScheduleAlarmToAutoLockPrivateSpace();
                                break;
                            }
                        }
                        break;
                    default:
                        if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                            this.this$0.invalidateOwnerNameIfNecessary(context2.getResources(), false);
                            break;
                        }
                        break;
                }
            }
        };
        userManagerService.mOwnerName = new AtomicReference();
        userManagerService.mOwnerNameTypedValue = new TypedValue();
        userManagerService.mLastConfiguration = new Configuration();
        final int i4 = 2;
        userManagerService.mConfigurationChangeReceiver = new BroadcastReceiver(userManagerService) { // from class: com.android.server.pm.UserManagerService.1
            public final /* synthetic */ UserManagerService this$0;

            {
                this.this$0 = userManagerService;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i4) {
                    case 0:
                        if ("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK".equals(intent.getAction())) {
                            IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
                            this.this$0.setQuietModeEnabledAsync(intent.getIntExtra("android.intent.extra.USER_ID", -10000), false, intentSender, intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
                            break;
                        }
                        break;
                    case 1:
                        if (UserManagerService.isAutoLockForPrivateSpaceEnabled()) {
                            if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                                    Slog.d("UserManagerService", "SCREEN_ON broadcast received, removing pending alarms to auto-lock private space");
                                    this.this$0.cancelPendingAutoLockAlarms();
                                    break;
                                }
                            } else {
                                this.this$0.maybeScheduleAlarmToAutoLockPrivateSpace();
                                break;
                            }
                        }
                        break;
                    default:
                        if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                            this.this$0.invalidateOwnerNameIfNecessary(context2.getResources(), false);
                            break;
                        }
                        break;
                }
            }
        };
        userManagerService.mUserStates = new WatchedUserStates();
        userManagerService.mBootUser = -10000;
        userManagerService.mContext = context;
        userManagerService.mPm = packageManagerService;
        userManagerService.mPackagesLock = obj;
        userManagerService.mUsers = sparseArray != null ? sparseArray : new SparseArray();
        MainHandler mainHandler = userManagerService.new MainHandler(packageManagerService.mCustomInjector.getUserManagerHandler().getLooper());
        userManagerService.mHandler = mainHandler;
        userManagerService.mInternalExecutor = new ThreadPoolExecutor(0, 1, 24L, TimeUnit.HOURS, new LinkedBlockingQueue());
        userManagerService.mUserVisibilityMediator = new UserVisibilityMediator(UserManager.isVisibleBackgroundUsersEnabled(), UserManager.isVisibleBackgroundUsersOnDefaultDisplayEnabled(), mainHandler);
        userManagerService.mUserDataPreparer = userDataPreparer;
        ArrayMap defaultBuilders = UserTypeFactory.getDefaultBuilders();
        XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
        try {
            UserTypeFactory.customizeBuilders(defaultBuilders, xml);
            if (xml != null) {
                xml.close();
            }
            ArrayMap arrayMap = new ArrayMap(defaultBuilders.size());
            int i5 = 0;
            while (i5 < defaultBuilders.size()) {
                String str2 = (String) defaultBuilders.keyAt(i5);
                UserTypeDetails.Builder builder = (UserTypeDetails.Builder) defaultBuilders.valueAt(i5);
                Preconditions.checkArgument(builder.mName != null ? z2 : z, "Cannot create a UserTypeDetails with no name.");
                int i6 = builder.mBaseType;
                Preconditions.checkArgument((i6 == 1024 || i6 == 4096 || i6 == 2048 || i6 == 3072) ? z2 : z, "UserTypeDetails " + builder.mName + " has invalid baseType: " + builder.mBaseType);
                Preconditions.checkArgument((builder.mDefaultUserInfoPropertyFlags & 7312) == 0 ? z2 : z, "UserTypeDetails " + builder.mName + " has invalid flags: " + Integer.toHexString(builder.mDefaultUserInfoPropertyFlags));
                Preconditions.checkArgument(((2048 & builder.mBaseType) != 0 ? z2 : z) == ((builder.mDefaultUserInfoPropertyFlags & z2) != 0 ? z2 : z) ? z2 : z, "UserTypeDetails " + builder.mName + " cannot be SYSTEM xor PRIMARY.");
                Preconditions.checkArgument(((builder.mDefaultUserInfoPropertyFlags & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) == 0 || builder.mMaxAllowed == z2) ? z2 : z, "UserTypeDetails " + builder.mName + " must not sanction more than one MainUser.");
                if (builder.mIconBadge != 0) {
                    int[] iArr = builder.mBadgeLabels;
                    Preconditions.checkArgument((iArr == null || iArr.length == 0) ? z : z2, "UserTypeDetails " + builder.mName + " has badge but no badgeLabels.");
                    int[] iArr2 = builder.mBadgeColors;
                    Preconditions.checkArgument((iArr2 == null || iArr2.length == 0) ? z : z2, "UserTypeDetails " + builder.mName + " has badge but no badgeColors.");
                }
                if ((builder.mBaseType & 4096) == 0) {
                    List list = builder.mDefaultCrossProfileIntentFilters;
                    Preconditions.checkArgument((list == null || list.isEmpty()) ? z2 : z, "UserTypeDetails %s has a non empty defaultCrossProfileIntentFilters", new Object[]{builder.mName});
                }
                String str3 = builder.mName;
                boolean z3 = builder.mEnabled != 0 ? z2 : z;
                int i7 = builder.mMaxAllowed;
                int i8 = builder.mBaseType;
                int i9 = builder.mDefaultUserInfoPropertyFlags;
                int[] iArr3 = builder.mLabels;
                int i10 = builder.mMaxAllowedPerParent;
                int i11 = builder.mIconBadge;
                ArrayMap arrayMap2 = defaultBuilders;
                int i12 = builder.mBadgePlain;
                int i13 = builder.mBadgeNoBackground;
                int i14 = builder.mStatusBarIcon;
                int i15 = i5;
                int[] iArr4 = builder.mBadgeLabels;
                ArrayMap arrayMap3 = arrayMap;
                int[] iArr5 = builder.mBadgeColors;
                int[] iArr6 = builder.mDarkThemeBadgeColors;
                int[] iArr7 = iArr6 == null ? iArr5 : iArr6;
                Bundle bundle = builder.mDefaultRestrictions;
                Bundle bundle2 = builder.mDefaultSecureSettings;
                List list2 = builder.mDefaultCrossProfileIntentFilters;
                int i16 = builder.mAccessibilityString;
                if (builder.mDefaultUserProperties == null) {
                    builder.mDefaultUserProperties = new UserProperties.Builder().build();
                }
                arrayMap3.put(str2, new UserTypeDetails(str3, z3, i7, i8, i9, iArr3, i10, i11, i12, i13, i14, iArr4, iArr5, iArr7, bundle, bundle2, list2, i16, builder.mDefaultUserProperties));
                i5 = i15 + 1;
                defaultBuilders = arrayMap2;
                arrayMap = arrayMap3;
                z = false;
                z2 = 1;
                userManagerService = this;
            }
            UserManagerService userManagerService2 = userManagerService;
            ArrayMap arrayMap4 = arrayMap;
            userManagerService2.mUserTypes = arrayMap4;
            userManagerService2.invalidateOwnerNameIfNecessary(context.getResources(), true);
            sContext = context;
            userManagerService2.sPersonaManager = personaManagerService;
            synchronized (userManagerService2.mPackagesLock) {
                File file2 = new File(file, USER_INFO_DIR);
                userManagerService2.mUsersDir = file2;
                file2.mkdirs();
                new File(file2, String.valueOf(0)).mkdirs();
                FileUtils.setPermissions(file2.toString(), 509, -1, -1);
                userManagerService2.mUserListFile = new File(file2, "userlist.xml");
                initDefaultGuestRestrictions();
                readUserListLP();
                sInstance = userManagerService2;
            }
            userManagerService2.mSystemPackageInstaller = new UserSystemPackageInstaller(userManagerService2, arrayMap4);
            LocalService localService = userManagerService2.new LocalService();
            userManagerService2.mLocalService = localService;
            LocalServices.addService(UserManagerInternal.class, localService);
            userManagerService2.mLockPatternUtils = new LockPatternUtils(userManagerService2.mContext);
            userManagerService2.mUserStates.states.put(0, 0);
            UserManager.invalidateIsUserUnlockedCache();
            userManagerService2.mPrivateSpaceAutoLockSettingsObserver = userManagerService2.new SettingsObserver(userManagerService2.mHandler);
            if (Build.isDebuggable() && !TextUtils.isEmpty(SystemProperties.get("persist.debug.user_mode_emulation"))) {
                boolean isDefaultHeadlessSystemUserMode = isDefaultHeadlessSystemUserMode();
                synchronized (userManagerService2.mPackagesLock) {
                    synchronized (userManagerService2.mUsersLock) {
                        UserData userData = (UserData) userManagerService2.mUsers.get(0);
                        if (userData == null) {
                            Slogf.wtf("UserManagerService", "emulateSystemUserModeIfNeeded(): no system user data");
                            return;
                        }
                        int mainUserIdUnchecked = getMainUserIdUnchecked();
                        UserInfo userInfo = userData.info;
                        int i17 = userInfo.flags;
                        if (isDefaultHeadlessSystemUserMode) {
                            str = "android.os.usertype.system.HEADLESS";
                            i = i17 & (-17409);
                        } else {
                            str = "android.os.usertype.full.SYSTEM";
                            i = i17 | 17408;
                        }
                        if (userInfo.userType.equals(str)) {
                            Slogf.d("UserManagerService", "emulateSystemUserModeIfNeeded(): system user type is already %s, returning", str);
                            return;
                        }
                        Slogf.i("UserManagerService", "Persisting emulated system user data: type changed from %s to %s, flags changed from %s to %s", userData.info.userType, str, UserInfo.flagsToString(i17), UserInfo.flagsToString(i));
                        UserInfo userInfo2 = userData.info;
                        userInfo2.userType = str;
                        userInfo2.flags = i;
                        userManagerService2.writeUserLP(userData);
                        UserData userDataNoChecks = userManagerService2.getUserDataNoChecks(mainUserIdUnchecked);
                        if (isDefaultHeadlessSystemUserMode) {
                            if ((userDataNoChecks == null || (userDataNoChecks.info.flags & 2048) != 0) && Resources.getSystem().getBoolean(R.bool.config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled) && (earliestCreatedFullUser = getEarliestCreatedFullUser()) != null) {
                                Slogf.i("UserManagerService", "Designating user " + earliestCreatedFullUser.id + " to be Main");
                                earliestCreatedFullUser.flags = earliestCreatedFullUser.flags | EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                                userManagerService2.writeUserLP(userManagerService2.getUserDataNoChecks(earliestCreatedFullUser.id));
                            }
                        } else if (userDataNoChecks == null || (userDataNoChecks.info.flags & 2048) != 0) {
                            Slogf.i("UserManagerService", "Designated user 0 to be Main");
                        } else {
                            Slogf.i("UserManagerService", "Transferring Main to user 0 from " + userDataNoChecks.info.id);
                            UserInfo userInfo3 = userDataNoChecks.info;
                            userInfo3.flags = userInfo3.flags & (-16385);
                            userManagerService2.writeUserLP(userDataNoChecks);
                        }
                        userManagerService2.mUpdatingSystemUserMode = true;
                    }
                }
            }
        } finally {
        }
    }

    public static final void checkCreateUsersPermission(int i) {
        if (((-38701) & i) == 0) {
            if (!hasManageUsersOrPermission("android.permission.CREATE_USERS")) {
                throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "You either need MANAGE_USERS or CREATE_USERS permission to create an user with flags: "));
            }
        } else if (!hasManageUsersPermission()) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "You need MANAGE_USERS permission to create an user  with flags: "));
        }
    }

    public static final void checkCreateUsersPermission(String str) {
        if (!hasManageUsersOrPermission("android.permission.CREATE_USERS")) {
            throw new SecurityException("You either need MANAGE_USERS or CREATE_USERS permission to: ".concat(str));
        }
    }

    public static final void checkManageUserAndAcrossUsersFullPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || callingUid == 0) {
            return;
        }
        if (!hasPermissionGranted(callingUid, "android.permission.MANAGE_USERS") || !hasPermissionGranted(callingUid, "android.permission.INTERACT_ACROSS_USERS_FULL")) {
            throw new SecurityException("You need MANAGE_USERS and INTERACT_ACROSS_USERS_FULL permission to: ".concat(str));
        }
    }

    public static final void checkManageUsersPermission(String str) {
        if (!hasManageUsersPermission()) {
            throw new SecurityException("You need MANAGE_USERS permission to: ".concat(str));
        }
    }

    public static final void checkQueryOrCreateUsersPermission(String str) {
        if (!hasQueryOrCreateUsersPermission()) {
            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("You either need MANAGE_USERS, CREATE_USERS, or QUERY_USERS permission to: ", str));
        }
    }

    public static void checkSystemOrRoot(String str) {
        int callingUid = Binder.getCallingUid();
        if (!UserHandle.isSameApp(callingUid, 1000) && callingUid != 0) {
            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Only system may: ", str));
        }
    }

    public static boolean checkUserTypeConsistency(int i) {
        int i2 = i & 4620;
        if ((i2 & (i2 - 1)) == 0) {
            int i3 = i & 5120;
            if ((i3 & (i3 - 1)) == 0) {
                int i4 = i & 6144;
                if ((i4 & (i4 - 1)) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void dumpTimeAgo(PrintWriter printWriter, StringBuilder sb, long j, long j2) {
        if (j2 == 0) {
            printWriter.println("<unknown>");
            return;
        }
        sb.setLength(0);
        TimeUtils.formatDuration(j - j2, sb);
        sb.append(" ago");
        printWriter.println(sb);
    }

    public static UserManagerService getInstance() {
        UserManagerService userManagerService;
        synchronized (UserManagerService.class) {
            userManagerService = sInstance;
        }
        return userManagerService;
    }

    public static final boolean hasManageUsersOrPermission(String str) {
        int callingUid = Binder.getCallingUid();
        return hasManageUsersPermission(callingUid) || hasPermissionGranted(callingUid, str);
    }

    public static final boolean hasManageUsersPermission() {
        return hasManageUsersPermission(Binder.getCallingUid());
    }

    public static boolean hasManageUsersPermission(int i) {
        if (i == 2000 && MaintenanceModeManager.isInMaintenanceModeFromProperty()) {
            throw new SecurityException("Cannot manage users : unauthorized");
        }
        return UserHandle.isSameApp(i, 1000) || i == 0 || hasPermissionGranted(i, "android.permission.MANAGE_USERS");
    }

    public static boolean hasPermissionGranted(int i, String str) {
        if (i == 2000 && MaintenanceModeManager.isInMaintenanceModeFromProperty()) {
            throw new SecurityException("Cannot manage users : unauthorized");
        }
        return ActivityManager.checkComponentPermission(str, i, -1, true) == 0;
    }

    public static final boolean hasQueryOrCreateUsersPermission() {
        return hasManageUsersOrPermission("android.permission.CREATE_USERS") || hasPermissionGranted(Binder.getCallingUid(), "android.permission.QUERY_USERS");
    }

    public static boolean isAutoLockForPrivateSpaceEnabled() {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.supportAutolockForPrivateSpace() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    public static boolean isCreationOverrideEnabled() {
        return Build.isDebuggable() && SystemProperties.getBoolean("debug.user.creation_override", false);
    }

    public static boolean isDefaultHeadlessSystemUserMode() {
        if (!Build.isDebuggable()) {
            return RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER;
        }
        String str = SystemProperties.get("persist.debug.user_mode_emulation");
        if (!TextUtils.isEmpty(str)) {
            if ("headless".equals(str)) {
                return true;
            }
            if ("full".equals(str)) {
                return false;
            }
            if (!"default".equals(str)) {
                Slogf.e("UserManagerService", "isDefaultHeadlessSystemUserMode(): ignoring invalid valued of property %s: %s", "persist.debug.user_mode_emulation", str);
            }
        }
        return RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER;
    }

    public static boolean isUserTypeEnabled(UserTypeDetails userTypeDetails) {
        return userTypeDetails.mEnabled || isCreationOverrideEnabled();
    }

    public static String packageToRestrictionsFileName(String str) {
        return XmlUtils$$ExternalSyntheticOutline0.m("res_", str, ".xml");
    }

    public static Bundle readApplicationRestrictionsLAr(AtomicFile atomicFile) {
        TypedXmlPullParser resolvePullParser;
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        if (!atomicFile.getBaseFile().exists()) {
            return bundle;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                resolvePullParser = Xml.resolvePullParser(fileInputStream);
                XmlUtils.nextElement(resolvePullParser);
            } catch (IOException | XmlPullParserException e) {
                Slog.w("UserManagerService", "Error parsing " + atomicFile.getBaseFile(), e);
            }
            if (resolvePullParser.getEventType() == 2) {
                while (resolvePullParser.next() != 1) {
                    readEntry(bundle, arrayList, resolvePullParser);
                }
                return bundle;
            }
            Slog.e("UserManagerService", "Unable to read restrictions file " + atomicFile.getBaseFile());
            return bundle;
        } finally {
            IoUtils.closeQuietly((AutoCloseable) null);
        }
    }

    public static void readEntry(Bundle bundle, ArrayList arrayList, TypedXmlPullParser typedXmlPullParser) {
        if (typedXmlPullParser.getEventType() == 2 && typedXmlPullParser.getName().equals("entry")) {
            String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "key");
            String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "type");
            int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "m", -1);
            if (attributeInt != -1) {
                arrayList.clear();
                while (attributeInt > 0) {
                    int next = typedXmlPullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && typedXmlPullParser.getName().equals("value")) {
                        arrayList.add(typedXmlPullParser.nextText().trim());
                        attributeInt--;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if ("B".equals(attributeValue2)) {
                Bundle bundle2 = new Bundle();
                int depth = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    readEntry(bundle2, arrayList, typedXmlPullParser);
                }
                bundle.putBundle(attributeValue, bundle2);
                return;
            }
            if (!"BA".equals(attributeValue2)) {
                String trim = typedXmlPullParser.nextText().trim();
                if ("b".equals(attributeValue2)) {
                    bundle.putBoolean(attributeValue, Boolean.parseBoolean(trim));
                    return;
                } else if ("i".equals(attributeValue2)) {
                    bundle.putInt(attributeValue, Integer.parseInt(trim));
                    return;
                } else {
                    bundle.putString(attributeValue, trim);
                    return;
                }
            }
            int depth2 = typedXmlPullParser.getDepth();
            ArrayList arrayList2 = new ArrayList();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                Bundle bundle3 = new Bundle();
                int depth3 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                    readEntry(bundle3, arrayList, typedXmlPullParser);
                }
                arrayList2.add(bundle3);
            }
            bundle.putParcelableArray(attributeValue, (Parcelable[]) arrayList2.toArray(new Bundle[arrayList2.size()]));
        }
    }

    public static void throwCheckedUserOperationException(int i, String str) {
        Slog.e("UserManagerService", str);
        throw new UserManager.CheckedUserOperationException(str, i);
    }

    public static String truncateString(int i, String str) {
        return (str == null || str.length() <= i) ? str : str.substring(0, i);
    }

    public static void writeApplicationRestrictionsLAr(Bundle bundle, AtomicFile atomicFile) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((String) null, "restrictions");
                writeBundle(bundle, resolveSerializer);
                resolveSerializer.endTag((String) null, "restrictions");
                resolveSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
            } catch (Exception e) {
                e = e;
                fileOutputStream = startWrite;
                atomicFile.failWrite(fileOutputStream);
                Slog.e("UserManagerService", "Error writing application restrictions list", e);
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static void writeBundle(Bundle bundle, TypedXmlSerializer typedXmlSerializer) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            typedXmlSerializer.startTag((String) null, "entry");
            typedXmlSerializer.attribute((String) null, "key", str);
            if (obj instanceof Boolean) {
                typedXmlSerializer.attribute((String) null, "type", "b");
                typedXmlSerializer.text(obj.toString());
            } else if (obj instanceof Integer) {
                typedXmlSerializer.attribute((String) null, "type", "i");
                typedXmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof String)) {
                typedXmlSerializer.attribute((String) null, "type", "s");
                typedXmlSerializer.text(obj != null ? (String) obj : "");
            } else if (obj instanceof Bundle) {
                typedXmlSerializer.attribute((String) null, "type", "B");
                writeBundle((Bundle) obj, typedXmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    typedXmlSerializer.attribute((String) null, "type", "BA");
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof Bundle)) {
                            throw new IllegalArgumentException("bundle-array can only hold Bundles");
                        }
                        typedXmlSerializer.startTag((String) null, "entry");
                        typedXmlSerializer.attribute((String) null, "type", "B");
                        writeBundle((Bundle) parcelable, typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "entry");
                        i++;
                    }
                } else {
                    typedXmlSerializer.attribute((String) null, "type", "sa");
                    String[] strArr = (String[]) obj;
                    typedXmlSerializer.attributeInt((String) null, "m", strArr.length);
                    int length2 = strArr.length;
                    while (i < length2) {
                        String str2 = strArr[i];
                        typedXmlSerializer.startTag((String) null, "value");
                        if (str2 == null) {
                            str2 = "";
                        }
                        typedXmlSerializer.text(str2);
                        typedXmlSerializer.endTag((String) null, "value");
                        i++;
                    }
                }
            }
            typedXmlSerializer.endTag((String) null, "entry");
        }
    }

    public void addRemovingUserId(int i) {
        synchronized (this.mUsersLock) {
            addRemovingUserIdLocked(i);
        }
    }

    public final void addRemovingUserIdLocked(int i) {
        this.mRemovingUserIds.put(i, true);
        this.mRecentlyRemovedIds.add(Integer.valueOf(i));
        if (this.mRecentlyRemovedIds.size() > 100) {
            this.mRecentlyRemovedIds.removeFirst();
        }
    }

    public final void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Non-system caller");
        }
        this.mLocalService.addUserRestrictionsListener(new UserManagerService$$ExternalSyntheticLambda1(iUserRestrictionsListener));
    }

    public final void applyDefaultUserSettings(UserTypeDetails userTypeDetails, int i) {
        Bundle clone = BundleUtils.clone(null);
        Bundle clone2 = BundleUtils.clone(userTypeDetails.mDefaultSecureSettings);
        if (clone.isEmpty() && clone2.isEmpty()) {
            return;
        }
        int size = clone.size();
        String[] strArr = (String[]) clone.keySet().toArray(new String[size]);
        for (int i2 = 0; i2 < size; i2++) {
            String str = strArr[i2];
            if (!Settings.System.putStringForUser(this.mContext.getContentResolver(), str, clone.getString(str), i)) {
                BootReceiver$$ExternalSyntheticOutline0.m("Failed to insert default system setting: ", str, "UserManagerService");
            }
        }
        int size2 = clone2.size();
        String[] strArr2 = (String[]) clone2.keySet().toArray(new String[size2]);
        for (int i3 = 0; i3 < size2; i3++) {
            String str2 = strArr2[i3];
            if (!Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str2, clone2.getString(str2), i)) {
                BootReceiver$$ExternalSyntheticOutline0.m("Failed to insert default secure setting: ", str2, "UserManagerService");
            }
        }
    }

    public void autoLockPrivateSpace() {
        int privateProfileUserId = getPrivateProfileUserId();
        if (privateProfileUserId != -10000) {
            HermesService$3$$ExternalSyntheticOutline0.m(privateProfileUserId, "Auto-locking private space with user-id ", "UserManagerService");
            setQuietModeEnabledAsync(privateProfileUserId, true, null, this.mContext.getPackageName());
        }
    }

    public final void broadcastProfileAvailabilityChanges(UserInfo userInfo, UserHandle userHandle, boolean z, boolean z2) {
        Intent intent = new Intent();
        intent.setAction(z2 ? z ? "android.intent.action.MANAGED_PROFILE_UNAVAILABLE" : "android.intent.action.MANAGED_PROFILE_AVAILABLE" : z ? "android.intent.action.PROFILE_UNAVAILABLE" : "android.intent.action.PROFILE_AVAILABLE");
        intent.putExtra("android.intent.extra.QUIET_MODE", z);
        intent.putExtra("android.intent.extra.USER", userInfo.getUserHandle());
        intent.putExtra("android.intent.extra.user_handle", userInfo.getUserHandle().getIdentifier());
        if (userInfo.isManagedProfile()) {
            if (this.mDevicePolicyManagerInternal == null) {
                this.mDevicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
            }
            this.mDevicePolicyManagerInternal.broadcastIntentToManifestReceivers(intent, userHandle, true);
        }
        intent.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent, userHandle, null, new BroadcastOptions().setDeferralPolicy(2).setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey(z2 ? "android.intent.action.MANAGED_PROFILE_AVAILABLE" : "android.intent.action.PROFILE_AVAILABLE", String.valueOf(userInfo.getUserHandle().getIdentifier())).toBundle());
    }

    public final boolean canAddMoreManagedProfiles(int i, boolean z) {
        return canAddMoreProfilesToUser("android.os.usertype.profile.MANAGED", i, z);
    }

    public final boolean canAddMoreProfilesToUser(int i, int i2, String str) {
        checkManageUsersPermission("check if more managed profiles can be added.");
        if (((UserTypeDetails) this.mUserTypes.get(str)) == null || ActivityManager.isLowRamDeviceStatic() || !this.mContext.getPackageManager().hasSystemFeature("android.software.managed_users")) {
            return false;
        }
        synchronized (this.mUsersLock) {
            UserInfo userInfoLU = getUserInfoLU(i);
            if (userInfoLU != null && userInfoLU.canHaveProfile()) {
                if (UserManager.isUserTypeCloneProfile(str) && canAddMoreProfilesToUser(str, i, false)) {
                    return true;
                }
                return PersonaServiceHelper.canAddMoreManagedProfiles(i2, false, getProfiles(i, true));
            }
            return false;
        }
    }

    public final boolean canAddMoreProfilesToUser(String str, int i, boolean z) {
        return getRemainingCreatableProfileCount(i, str, z) > 0 || isCreationOverrideEnabled();
    }

    public final boolean canAddMoreUsersOfType(UserTypeDetails userTypeDetails) {
        if (!isUserTypeEnabled(userTypeDetails)) {
            return false;
        }
        int i = userTypeDetails.mMaxAllowed;
        if (i == -1) {
            return true;
        }
        return getNumberOfUsersOfType(userTypeDetails.mName) < i || isCreationOverrideEnabled();
    }

    public final boolean canAddMoreUsersOfType(String str) {
        checkCreateUsersPermission("check if more users can be added.");
        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(str);
        return userTypeDetails != null && canAddMoreUsersOfType(userTypeDetails);
    }

    public final boolean canAddPrivateProfile(int i) {
        checkCreateUsersPermission("canHaveRestrictedProfile");
        UserInfo userInfo = getUserInfo(i);
        return (!isUserTypeEnabled("android.os.usertype.profile.PRIVATE") || !canAddMoreProfilesToUser("android.os.usertype.profile.PRIVATE", i, false) || userInfo == null || !userInfo.isMain() || this.mPm.hasSystemFeature("android.hardware.type.embedded", 0) || this.mPm.hasSystemFeature("android.hardware.type.watch", 0) || this.mPm.hasSystemFeature("android.software.leanback", 0) || this.mPm.hasSystemFeature("android.hardware.type.automotive", 0) || hasUserRestriction("no_add_private_profile", i)) ? false : true;
    }

    public final boolean canHaveRestrictedProfile(int i) {
        checkManageUsersPermission("canHaveRestrictedProfile");
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                boolean z = false;
                if (userInfoLU != null && userInfoLU.canHaveProfile()) {
                    if (!userInfoLU.isAdmin()) {
                        return false;
                    }
                    if (!this.mIsDeviceManaged && !this.mIsUserManaged.get(i)) {
                        z = true;
                    }
                    return z;
                }
                return false;
            } finally {
            }
        }
    }

    public final void cancelPendingAutoLockAlarms() {
        PrivateSpaceAutoLockTimer privateSpaceAutoLockTimer;
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        if (alarmManager == null || (privateSpaceAutoLockTimer = this.mPrivateSpaceAutoLockTimer) == null) {
            return;
        }
        alarmManager.cancel(privateSpaceAutoLockTimer);
    }

    public final void checkManageOrInteractPermissionIfCallerInOtherProfileGroup(int i, String str) {
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId != i && !isSameProfileGroupNoChecks(callingUserId, i) && !hasManageUsersPermission() && !hasPermissionGranted(Binder.getCallingUid(), "android.permission.INTERACT_ACROSS_USERS")) {
            throw new SecurityException("You need INTERACT_ACROSS_USERS or MANAGE_USERS permission to: check ".concat(str));
        }
    }

    public final void checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(int i, String str) {
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId != i && !isSameProfileGroupNoChecks(callingUserId, i) && !hasManageUsersOrPermission("android.permission.QUERY_USERS") && !hasPermissionGranted(Binder.getCallingUid(), "android.permission.INTERACT_ACROSS_USERS")) {
            throw new SecurityException("You need INTERACT_ACROSS_USERS, MANAGE_USERS, or QUERY_USERS permission to: check ".concat(str));
        }
    }

    public final void cleanUpMaintenanceModeUserDebris(UserData userData) {
        markMaintenanceModeUserForDeletion(userData);
        this.mPm.mCustomInjector.getMaintenanceModeManager().reboot("Failed to enable");
    }

    public final void clearSeedAccountData(int i) {
        checkManageUsersPermission("Cannot clear seed account information");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    return;
                }
                userDataLU.seedAccountName = null;
                userDataLU.seedAccountType = null;
                userDataLU.seedAccountOptions = null;
                userDataLU.persistSeedData = false;
                writeUserLP(userDataLU);
            }
        }
    }

    public final Bundle computeEffectiveUserRestrictionsLR(int i) {
        Bundle bundle = (Bundle) this.mBaseUserRestrictions.mUserRestrictions.get(i);
        Set set = UserRestrictionsUtils.USER_RESTRICTIONS;
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = (Bundle) this.mDevicePolicyUserRestrictions.mUserRestrictions.get(-1);
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        Bundle bundle3 = (Bundle) this.mDevicePolicyUserRestrictions.mUserRestrictions.get(i);
        if (bundle3 == null) {
            bundle3 = new Bundle();
        }
        if (bundle2.isEmpty() && bundle3.isEmpty()) {
            return bundle;
        }
        Bundle clone = BundleUtils.clone(bundle);
        UserRestrictionsUtils.merge(clone, bundle2);
        UserRestrictionsUtils.merge(clone, bundle3);
        return clone;
    }

    public final UserInfo convertPreCreatedUserIfPossible(int i, final Object obj, String str, String str2) {
        UserData preCreatedUserLU;
        synchronized (this.mUsersLock) {
            preCreatedUserLU = getPreCreatedUserLU(str);
        }
        if (preCreatedUserLU == null) {
            return null;
        }
        synchronized (this.mUserStates) {
            try {
                if (this.mUserStates.states.get(preCreatedUserLU.info.id, -10000) != -10000) {
                    Slog.w("UserManagerService", "Cannot reuse pre-created user " + preCreatedUserLU.info.id + " because it didn't stop yet");
                    return null;
                }
                final UserInfo userInfo = preCreatedUserLU.info;
                int i2 = userInfo.flags | i;
                if (!checkUserTypeConsistency(i2)) {
                    StringBuilder sb = new StringBuilder("Cannot reuse pre-created user ");
                    AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(userInfo.id, " of type ", str, " because flags are inconsistent. Flags (", sb);
                    BatteryService$$ExternalSyntheticOutline0.m(i, sb, "); preCreatedUserFlags ( ");
                    sb.append(Integer.toHexString(userInfo.flags));
                    sb.append(").");
                    Slog.wtf("UserManagerService", sb.toString());
                    return null;
                }
                StringBuilder sb2 = new StringBuilder("Reusing pre-created user ");
                AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(userInfo.id, " of type ", str, " and bestowing on it flags ", sb2);
                sb2.append(UserInfo.flagsToString(i));
                Slog.i("UserManagerService", sb2.toString());
                userInfo.name = str2;
                userInfo.flags = i2;
                userInfo.preCreated = false;
                userInfo.convertedFromPreCreated = true;
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis <= 946080000000L) {
                    currentTimeMillis = 0;
                }
                userInfo.creationTime = currentTimeMillis;
                synchronized (this.mPackagesLock) {
                    writeUserLP(preCreatedUserLU);
                    writeUserListLP();
                }
                updateUserIds();
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda9
                    public final void runOrThrow() {
                        UserManagerService userManagerService = UserManagerService.this;
                        UserInfo userInfo2 = userInfo;
                        Object obj2 = obj;
                        userManagerService.mPm.onNewUserCreated(userInfo2.id, true);
                        userManagerService.dispatchUserAdded(userInfo2, obj2);
                        VoiceInteractionManagerInternal voiceInteractionManagerInternal = (VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class);
                        if (voiceInteractionManagerInternal != null) {
                            voiceInteractionManagerInternal.onPreCreatedUserConversion(userInfo2.id);
                        }
                    }
                });
                return userInfo;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UserInfo createProfileForUserEvenWhenDisallowedWithThrow(String str, String str2, int i, int i2, String[] strArr) {
        checkCreateUsersPermission(i);
        try {
            return createUserInternalUnchecked(str, str2, i, i2, false, strArr, null);
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final UserInfo createProfileForUserWithThrow(String str, String str2, int i, int i2, String[] strArr) {
        checkCreateUsersPermission(i);
        try {
            return createUserInternal(str, str2, i, i2, strArr);
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final UserInfo createRestrictedProfileWithThrow(String str, int i) {
        checkCreateUsersPermission("setupRestrictedProfile");
        UserInfo createProfileForUserWithThrow = createProfileForUserWithThrow(str, "android.os.usertype.full.RESTRICTED", 0, i, null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            setUserRestriction("no_modify_accounts", true, createProfileForUserWithThrow.id);
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "location_mode", 0, createProfileForUserWithThrow.id);
            setUserRestriction("no_share_location", true, createProfileForUserWithThrow.id);
            return createProfileForUserWithThrow;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final UserInfo createUserInternal(String str, String str2, int i, int i2, String[] strArr) {
        enforceUserRestriction(UserHandle.getCallingUserId(), UserManager.isUserTypeCloneProfile(str2) ? "no_add_clone_profile" : UserManager.isUserTypeManagedProfile(str2) ? "no_add_managed_profile" : UserManager.isUserTypePrivateProfile(str2) ? "no_add_private_profile" : "no_add_user", "Cannot add user");
        boolean z = (i & 32) != 0;
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        if (!UserManager.supportsMultipleUsers() || z || (enterpriseDeviceManager.getMultiUserManager().isUserCreationAllowed(true) && enterpriseDeviceManager.getMultiUserManager().multipleUsersAllowed(true))) {
            return createUserInternalUnchecked(str, str2, i, i2, false, strArr, null);
        }
        Log.d("UserManagerService", "MultiUserManager policy blocked to create user");
        throwCheckedUserOperationException(1, "Cannot create user due to Knox security policy.");
        throw null;
    }

    public final UserInfo createUserInternalUnchecked(String str, String str2, int i, int i2, boolean z, String[] strArr, Object obj) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("createUser-" + i);
        this.mUserJourneyLogger.logUserJourneyBegin(-1, 4);
        boolean isUserTypeMaintenanceMode = MaintenanceModeUtils.isUserTypeMaintenanceMode(str2);
        if (isUserTypeMaintenanceMode) {
            MaintenanceModeManager maintenanceModeManager = this.mPm.mCustomInjector.getMaintenanceModeManager();
            if (!(maintenanceModeManager.mIsBeingCreated.get() ? false : maintenanceModeManager.mIsBeingCreated.compareAndSet(false, true))) {
                throwCheckedUserOperationException(1, "Maintenance mode user is already being created.");
                throw null;
            }
        }
        try {
            UserInfo createUserInternalUncheckedNoTracing = createUserInternalUncheckedNoTracing(str, str2, i, i2, z, strArr, timingsTraceAndSlog, obj);
            if (createUserInternalUncheckedNoTracing != null) {
                UserJourneyLogger userJourneyLogger = this.mUserJourneyLogger;
                int currentUserId = getCurrentUserId();
                synchronized (userJourneyLogger.mLock) {
                    try {
                        int userJourneyKey = UserJourneyLogger.getUserJourneyKey(-1, 4);
                        UserJourneyLogger.UserJourneySession userJourneySession = (UserJourneyLogger.UserJourneySession) userJourneyLogger.mUserIdToUserJourneyMap.get(userJourneyKey);
                        if (userJourneySession != null) {
                            userJourneyLogger.logUserLifecycleEventOccurred(userJourneySession, createUserInternalUncheckedNoTracing.id, 3, 2, -1);
                            userJourneyLogger.logUserLifecycleJourneyReported(userJourneySession, 4, currentUserId, createUserInternalUncheckedNoTracing.id, UserJourneyLogger.getUserTypeForStatsd(createUserInternalUncheckedNoTracing.userType), createUserInternalUncheckedNoTracing.flags, -1);
                            userJourneyLogger.mUserIdToUserJourneyMap.remove(userJourneyKey);
                        }
                    } finally {
                    }
                }
            } else {
                this.mUserJourneyLogger.logNullUserJourneyError(4, getCurrentUserId(), -1, i, str2);
            }
            timingsTraceAndSlog.traceEnd();
            if (isUserTypeMaintenanceMode) {
                this.mPm.mCustomInjector.getMaintenanceModeManager().mIsBeingCreated.set(false);
            }
            return createUserInternalUncheckedNoTracing;
        } finally {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x01f7, code lost:
    
        if (r23 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01f9, code lost:
    
        if (r11 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01ff, code lost:
    
        if (isUserLimitReached() == false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0201, code lost:
    
        if (r17 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0203, code lost:
    
        if (r18 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0205, code lost:
    
        if (r28 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0207, code lost:
    
        if (r24 != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0209, code lost:
    
        if (r5 == false) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x020c, code lost:
    
        throwCheckedUserOperationException(6, "Cannot add user. Maximum user limit is reached.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0213, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0265, code lost:
    
        if (r18 == false) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0267, code lost:
    
        r4 = ((java.util.ArrayList) getUsers(true, true, true)).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0276, code lost:
    
        if (r4.hasNext() == false) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0278, code lost:
    
        r6 = (android.content.pm.UserInfo) r4.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0282, code lost:
    
        if (r6.isBMode() == false) goto L449;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0286, code lost:
    
        if (r6 != null) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0289, code lost:
    
        throwCheckedUserOperationException(6, "Cannot add Bmode user. There already exists one.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0290, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0285, code lost:
    
        r6 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x007b, code lost:
    
        if (com.samsung.android.core.pm.mm.MaintenanceModeUtils.hasSystemFeature() != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.UserInfo createUserInternalUncheckedNoTracing(java.lang.String r35, java.lang.String r36, int r37, int r38, boolean r39, java.lang.String[] r40, com.android.server.utils.TimingsTraceAndSlog r41, java.lang.Object r42) {
        /*
            Method dump skipped, instructions count: 1700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.createUserInternalUncheckedNoTracing(java.lang.String, java.lang.String, int, int, boolean, java.lang.String[], com.android.server.utils.TimingsTraceAndSlog, java.lang.Object):android.content.pm.UserInfo");
    }

    public final UserHandle createUserWithAttributes(String str, String str2, int i, Bitmap bitmap, String str3, String str4, PersistableBundle persistableBundle) {
        checkCreateUsersPermission(i);
        if (someUserHasAccountNoChecks(str3, str4)) {
            throw new ServiceSpecificException(7);
        }
        try {
            UserInfo createUserInternal = createUserInternal(str, str2, i, -10000, null);
            if (bitmap != null) {
                this.mLocalService.setUserIcon(createUserInternal.id, bitmap);
            }
            setSeedAccountDataNoChecks(createUserInternal.id, str3, str4, persistableBundle, true);
            return createUserInternal.getUserHandle();
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final UserInfo createUserWithThrow(String str, String str2, int i) {
        checkCreateUsersPermission(i);
        try {
            return createUserInternal(str, str2, i, -10000, null);
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final void dispatchUserAdded(UserInfo userInfo, Object obj) {
        synchronized (this.mUserLifecycleListeners) {
            for (int i = 0; i < this.mUserLifecycleListeners.size(); i++) {
                try {
                    ((UserManagerInternal.UserLifecycleListener) this.mUserLifecycleListeners.get(i)).onUserCreated(userInfo, obj);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Intent intent = new Intent("android.intent.action.USER_ADDED");
        intent.addFlags(16777216);
        intent.addFlags(67108864);
        intent.putExtra("android.intent.extra.user_handle", userInfo.id);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(userInfo.id));
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.MANAGE_USERS");
        MetricsLogger.count(this.mContext, userInfo.isGuest() ? "users_guest_created" : userInfo.isDemo() ? "users_demo_created" : "users_user_created", 1);
        if (!userInfo.isProfile()) {
            if (Settings.Global.getString(this.mContext.getContentResolver(), "user_switcher_enabled") == null) {
                Settings.Global.putInt(this.mContext.getContentResolver(), "user_switcher_enabled", 1);
                return;
            }
            return;
        }
        int i2 = userInfo.profileGroupId;
        int i3 = userInfo.id;
        Intent intent2 = new Intent("android.intent.action.PROFILE_ADDED");
        UserHandle of = UserHandle.of(i2);
        intent2.putExtra("android.intent.extra.USER", UserHandle.of(i3));
        intent2.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent2, of, null);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Object obj;
        int i;
        Object obj2;
        int i2;
        Object obj3;
        int i3;
        int i4;
        if (!DumpUtils.checkDumpPermission(this.mContext, "UserManagerService", printWriter)) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i5 = 0;
        int i6 = Settings.Secure.getInt(this.mContext.getContentResolver(), "klm_eula_shown", 0);
        StringBuilder sb = new StringBuilder();
        if (strArr != null && strArr.length > 0) {
            String str = strArr[0];
            str.getClass();
            if (str.equals("--visibility-mediator")) {
                this.mUserVisibilityMediator.dump(printWriter, strArr);
                return;
            } else if (str.equals("--user")) {
                dumpUser(printWriter, UserHandle.parseUserArg(strArr[1]), sb, currentTimeMillis, elapsedRealtime);
                return;
            }
        }
        int currentUserId = getCurrentUserId();
        printWriter.print("Current user: ");
        if (currentUserId != -10000) {
            printWriter.println(currentUserId);
        } else {
            printWriter.println("N/A");
        }
        printWriter.println();
        Object obj4 = this.mPackagesLock;
        synchronized (obj4) {
            try {
                try {
                    Object obj5 = this.mUsersLock;
                    try {
                        synchronized (obj5) {
                            try {
                                printWriter.println("Users:");
                                int i7 = 0;
                                while (i7 < this.mUsers.size()) {
                                    UserData userData = (UserData) this.mUsers.valueAt(i7);
                                    if (userData == null) {
                                        i = i7;
                                        obj2 = obj5;
                                        i2 = currentUserId;
                                        obj3 = obj4;
                                        i3 = i5;
                                        i4 = i6;
                                    } else {
                                        i = i7;
                                        obj2 = obj5;
                                        i2 = currentUserId;
                                        obj3 = obj4;
                                        i3 = i5;
                                        i4 = i6;
                                        dumpUserLocked(printWriter, userData, sb, currentTimeMillis, elapsedRealtime);
                                    }
                                    i7 = i + 1;
                                    obj5 = obj2;
                                    currentUserId = i2;
                                    obj4 = obj3;
                                    i5 = i3;
                                    i6 = i4;
                                }
                                int i8 = currentUserId;
                                obj = obj4;
                                int i9 = i5;
                                int i10 = i6;
                                printWriter.println();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("  agree Knox Privacy Policy: ");
                                sb2.append(i10 == 1 ? "true" : "false");
                                printWriter.println(sb2.toString());
                                printWriter.println();
                                printWriter.println("Device properties:");
                                printWriter.println("  Device policy global restrictions:");
                                synchronized (this.mRestrictionsLock) {
                                    UserRestrictionsUtils.dumpRestrictions(printWriter, "    ", this.mDevicePolicyUserRestrictions.getRestrictions(-1));
                                }
                                printWriter.println("  Guest restrictions:");
                                synchronized (this.mGuestRestrictions) {
                                    UserRestrictionsUtils.dumpRestrictions(printWriter, "    ", this.mGuestRestrictions);
                                }
                                synchronized (this.mUsersLock) {
                                    try {
                                        printWriter.println();
                                        printWriter.println("  Device managed: " + this.mIsDeviceManaged);
                                        if (this.mRemovingUserIds.size() > 0) {
                                            printWriter.println();
                                            printWriter.println("  Recently removed userIds: " + this.mRecentlyRemovedIds);
                                        }
                                    } finally {
                                    }
                                }
                                synchronized (this.mUserStates) {
                                    try {
                                        printWriter.print("  Started users state: [");
                                        int size = this.mUserStates.states.size();
                                        for (int i11 = i9; i11 < size; i11++) {
                                            int keyAt = this.mUserStates.states.keyAt(i11);
                                            int valueAt = this.mUserStates.states.valueAt(i11);
                                            printWriter.print(keyAt);
                                            printWriter.print('=');
                                            printWriter.print(UserState.stateToString(valueAt));
                                            if (i11 != size - 1) {
                                                printWriter.print(", ");
                                            }
                                        }
                                        printWriter.println(']');
                                    } finally {
                                    }
                                }
                                synchronized (this.mUsersLock) {
                                    printWriter.print("  Cached user IDs: ");
                                    printWriter.println(Arrays.toString(this.mUserIds));
                                    printWriter.print("  Cached user IDs (including pre-created): ");
                                    printWriter.println(Arrays.toString(this.mUserIdsIncludingPreCreated));
                                }
                                printWriter.println();
                                this.mUserVisibilityMediator.dump(printWriter, strArr);
                                printWriter.println();
                                printWriter.println();
                                printWriter.print("  Max users: " + UserManager.getMaxSupportedUsers());
                                printWriter.println(" (limit reached: " + isUserLimitReached() + ")");
                                StringBuilder sb3 = new StringBuilder("  Supports switchable users: ");
                                sb3.append(UserManager.supportsMultipleUsers());
                                printWriter.println(sb3.toString());
                                printWriter.println("  All guests ephemeral: " + Resources.getSystem().getBoolean(R.bool.config_intrusiveNotificationLed));
                                printWriter.println("  Force ephemeral users: " + this.mForceEphemeralUsers);
                                boolean isHeadlessSystemUserMode = isHeadlessSystemUserMode();
                                printWriter.println("  Is headless-system mode: " + isHeadlessSystemUserMode);
                                if (isHeadlessSystemUserMode != RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER) {
                                    printWriter.println("  (differs from the current default build value)");
                                }
                                if (!TextUtils.isEmpty(SystemProperties.get("persist.debug.user_mode_emulation"))) {
                                    printWriter.println("  (emulated by 'cmd user set-system-user-mode-emulation')");
                                    if (this.mUpdatingSystemUserMode) {
                                        printWriter.println("  (and being updated after boot)");
                                    }
                                }
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  User version: "), this.mUserVersion, printWriter, "  Owner name: "), (String) this.mOwnerName.get(), printWriter);
                                synchronized (this.mUsersLock) {
                                    printWriter.println("  Boot user: " + this.mBootUser);
                                }
                                printWriter.println("Can add private profile: " + canAddPrivateProfile(i8));
                                printWriter.println();
                                printWriter.println("Number of listeners for");
                                synchronized (this.mUserRestrictionsListeners) {
                                    printWriter.println("  restrictions: " + this.mUserRestrictionsListeners.size());
                                }
                                synchronized (this.mUserLifecycleListeners) {
                                    printWriter.println("  user lifecycle events: " + this.mUserLifecycleListeners.size());
                                }
                                printWriter.println();
                                StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("User types version: "), this.mUserTypeVersion, printWriter, "User types (");
                                m.append(this.mUserTypes.size());
                                m.append(" types):");
                                printWriter.println(m.toString());
                                for (int i12 = i9; i12 < this.mUserTypes.size(); i12++) {
                                    ProxyManager$$ExternalSyntheticOutline0.m(printWriter, (String) this.mUserTypes.keyAt(i12), ": ", new StringBuilder("    "));
                                    UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.valueAt(i12);
                                    userTypeDetails.getClass();
                                    printWriter.print("        ");
                                    printWriter.print("mName: ");
                                    ProcessList$$ExternalSyntheticOutline0.m(printWriter, userTypeDetails.mName, "        ", "mBaseType: ");
                                    printWriter.println(UserInfo.flagsToString(userTypeDetails.mBaseType));
                                    printWriter.print("        ");
                                    printWriter.print("mEnabled: ");
                                    AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "        ", "mMaxAllowed: ", userTypeDetails.mEnabled);
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mMaxAllowed, printWriter, "        ", "mMaxAllowedPerParent: ");
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mMaxAllowedPerParent, printWriter, "        ", "mDefaultUserInfoFlags: ");
                                    printWriter.println(UserInfo.flagsToString(userTypeDetails.mDefaultUserInfoPropertyFlags));
                                    userTypeDetails.mDefaultUserProperties.println(printWriter, "        ");
                                    if ((userTypeDetails.mBaseType & 2048) != 0) {
                                        printWriter.print("        ");
                                        printWriter.println("config_defaultFirstUserRestrictions: ");
                                        try {
                                            Bundle bundle = new Bundle();
                                            String[] stringArray = Resources.getSystem().getStringArray(R.array.config_sfps_enroll_stage_thresholds);
                                            int length = stringArray.length;
                                            for (int i13 = i9; i13 < length; i13++) {
                                                String str2 = stringArray[i13];
                                                if (UserRestrictionsUtils.isValidRestriction(str2)) {
                                                    bundle.putBoolean(str2, true);
                                                }
                                            }
                                            UserRestrictionsUtils.dumpRestrictions(printWriter, "            ", bundle);
                                        } catch (Resources.NotFoundException unused) {
                                            printWriter.print("            ");
                                            printWriter.println("none - resource not found");
                                        }
                                    } else {
                                        printWriter.print("        ");
                                        printWriter.println("mDefaultRestrictions: ");
                                        UserRestrictionsUtils.dumpRestrictions(printWriter, "            ", userTypeDetails.mDefaultRestrictions);
                                    }
                                    printWriter.print("        ");
                                    printWriter.print("mIconBadge: ");
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mIconBadge, printWriter, "        ", "mBadgePlain: ");
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mBadgePlain, printWriter, "        ", "mBadgeNoBackground: ");
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mBadgeNoBackground, printWriter, "        ", "mStatusBarIcon: ");
                                    BroadcastStats$$ExternalSyntheticOutline0.m(userTypeDetails.mStatusBarIcon, printWriter, "        ", "mBadgeLabels.length: ");
                                    Object obj6 = "0(null)";
                                    int[] iArr = userTypeDetails.mBadgeLabels;
                                    printWriter.println(iArr != null ? Integer.valueOf(iArr.length) : "0(null)");
                                    printWriter.print("        ");
                                    printWriter.print("mBadgeColors.length: ");
                                    int[] iArr2 = userTypeDetails.mBadgeColors;
                                    printWriter.println(iArr2 != null ? Integer.valueOf(iArr2.length) : "0(null)");
                                    printWriter.print("        ");
                                    printWriter.print("mDarkThemeBadgeColors.length: ");
                                    int[] iArr3 = userTypeDetails.mDarkThemeBadgeColors;
                                    printWriter.println(iArr3 != null ? Integer.valueOf(iArr3.length) : "0(null)");
                                    printWriter.print("        ");
                                    printWriter.print("mLabels.length: ");
                                    int[] iArr4 = userTypeDetails.mLabels;
                                    if (iArr4 != null) {
                                        obj6 = Integer.valueOf(iArr4.length);
                                    }
                                    printWriter.println(obj6);
                                }
                                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
                                try {
                                    indentingPrintWriter.println();
                                    this.mSystemPackageInstaller.dump(indentingPrintWriter);
                                    indentingPrintWriter.close();
                                } finally {
                                }
                            } catch (Throwable th) {
                                th = th;
                                Object obj7 = obj5;
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    obj = obj4;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                throw th;
            }
        }
    }

    public final void dumpUser(PrintWriter printWriter, int i, StringBuilder sb, long j, long j2) {
        int i2;
        if (i == -2) {
            i2 = getCurrentUserId();
            printWriter.print("Current user: ");
            if (i2 == -10000) {
                printWriter.println("Cannot determine current user");
                return;
            }
        } else {
            i2 = i;
        }
        synchronized (this.mUsersLock) {
            try {
                UserData userData = (UserData) this.mUsers.get(i2);
                if (userData != null) {
                    dumpUserLocked(printWriter, userData, sb, j, j2);
                    return;
                }
                printWriter.println("User " + i2 + " not found");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpUserLocked(PrintWriter printWriter, UserData userData, StringBuilder sb, long j, long j2) {
        int i;
        UserInfo userInfo = userData.info;
        int i2 = userInfo.id;
        if (Build.IS_DEBUGGABLE) {
            printWriter.print("  ");
            printWriter.print(userInfo);
        } else {
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "  ", "UserInfo{");
            m.append(userInfo.id);
            m.append(":xxx:");
            m.append(Integer.toHexString(userInfo.flags));
            m.append("}");
            printWriter.print(m.toString());
        }
        printWriter.print(" serialNo=");
        printWriter.print(userInfo.serialNumber);
        printWriter.print(" isPrimary=");
        printWriter.print(userInfo.isPrimary());
        int i3 = userInfo.profileGroupId;
        if (i3 != userInfo.id && i3 != -10000) {
            printWriter.print(" parentId=");
            printWriter.print(userInfo.profileGroupId);
        }
        if (this.mRemovingUserIds.get(i2)) {
            printWriter.print(" <removing> ");
        }
        if (userInfo.partial) {
            printWriter.print(" <partial>");
        }
        if (userInfo.preCreated) {
            printWriter.print(" <pre-created>");
        }
        if (userInfo.convertedFromPreCreated) {
            printWriter.print(" <converted>");
        }
        printWriter.println();
        printWriter.print("    Type: ");
        printWriter.println(userInfo.userType);
        printWriter.print("    Flags: ");
        printWriter.print(userInfo.flags);
        printWriter.print(" (");
        printWriter.print(UserInfo.flagsToString(userInfo.flags));
        printWriter.println(")");
        printWriter.print("    State: ");
        synchronized (this.mUserStates) {
            i = this.mUserStates.get(i2);
        }
        printWriter.println(UserState.stateToString(i));
        printWriter.print("    Created: ");
        dumpTimeAgo(printWriter, sb, j, userInfo.creationTime);
        printWriter.print("    Last logged in: ");
        dumpTimeAgo(printWriter, sb, j, userInfo.lastLoggedInTime);
        printWriter.print("    Last logged in fingerprint: ");
        printWriter.println(userInfo.lastLoggedInFingerprint);
        printWriter.print("    Start time: ");
        dumpTimeAgo(printWriter, sb, j2, userData.startRealtime);
        printWriter.print("    Unlock time: ");
        dumpTimeAgo(printWriter, sb, j2, userData.unlockRealtime);
        printWriter.print("    Last entered foreground: ");
        dumpTimeAgo(printWriter, sb, j, userData.mLastEnteredForegroundTimeMillis);
        printWriter.print("    Has profile owner: ");
        printWriter.println(this.mIsUserManaged.get(i2));
        printWriter.println("    Restrictions:");
        synchronized (this.mRestrictionsLock) {
            UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mBaseUserRestrictions.getRestrictions(userInfo.id));
            printWriter.println("    Device policy restrictions:");
            UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mDevicePolicyUserRestrictions.getRestrictions(userInfo.id));
            printWriter.println("    Effective restrictions:");
            UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mCachedEffectiveUserRestrictions.getRestrictions(userInfo.id));
        }
        if (userData.account != null) {
            printWriter.print("    Account name: " + userData.account);
            printWriter.println();
        }
        if (userData.seedAccountName != null) {
            printWriter.print("    Seed account name: " + userData.seedAccountName);
            printWriter.println();
            if (userData.seedAccountType != null) {
                printWriter.print("         account type: " + userData.seedAccountType);
                printWriter.println();
            }
            if (userData.seedAccountOptions != null) {
                printWriter.print("         account options exist");
                printWriter.println();
            }
        }
        UserProperties userProperties = userData.userProperties;
        if (userProperties != null) {
            userProperties.println(printWriter, "    ");
        }
        printWriter.println("    Ignore errors preparing storage: " + userData.mIgnorePrepareStorageErrors);
        if (SemPersonaManager.isDoEnabled(i2)) {
            printWriter.print("    KNOX attributes: ");
            printWriter.print(Integer.toHexString(userInfo.getAttributes()));
            if (userInfo.isPremiumContainer()) {
                printWriter.print(" <PREMIUM> ");
            }
            printWriter.println();
        }
        if (userInfo.isManagedProfile()) {
            printWriter.print("    KNOX flags: ");
            printWriter.println();
            printWriter.print("    KNOX attributes: ");
            printWriter.print(Integer.toHexString(userInfo.getAttributes()));
            if (userInfo.isPremiumContainer()) {
                printWriter.print(" <PREMIUM> ");
            }
            if (userInfo.isSuperLocked()) {
                if (userInfo.isAdminLocked()) {
                    printWriter.print(" <admin locked> ");
                }
                if (userInfo.isLicenseLocked()) {
                    printWriter.print(" <license expired> ");
                }
                if (userInfo.isDeviceCompromised()) {
                    printWriter.print(" <device compromise detected> ");
                }
            }
            printWriter.println();
        }
        int i4 = userInfo.flags;
        if ((100663296 & i4) != 0) {
            if ((i4 & 67108864) != 0) {
                printWriter.print(" <DUALDAR CUSTOM CRYPTO>");
            }
            if ((userInfo.flags & 33554432) != 0) {
                printWriter.print(" <DUALDAR NATIVE CRYPTO>");
            }
            State currentState = StateMachine.getCurrentState(userInfo.id);
            if (currentState != null) {
                printWriter.print(" - " + currentState.toString());
            }
        }
    }

    public final void enforceUserRestriction(int i, String str, String str2) {
        if (hasUserRestriction(str, i)) {
            String str3 = str2.concat(": ") + str + " is enabled.";
            Slog.w("UserManagerService", str3);
            throw new UserManager.CheckedUserOperationException(str3, 1);
        }
    }

    public final void evictCredentialEncryptionKey(int i) {
        checkManageUsersPermission("evict CE key");
        IActivityManager iActivityManager = ActivityManagerNative.getDefault();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                iActivityManager.restartUserInBackground(i, isProfileUnchecked(i) ? 3 : 2);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void fallbackToSingleUserLP() {
        PersonaManagerService personaManagerService = this.sPersonaManager;
        if (personaManagerService != null) {
            personaManagerService.onUserRemoved(-1);
        }
        String str = isDefaultHeadlessSystemUserMode() ? "android.os.usertype.system.HEADLESS" : "android.os.usertype.full.SYSTEM";
        UserData putUserInfo = putUserInfo(new UserInfo(0, (String) null, (String) null, ((UserTypeDetails) this.mUserTypes.get(str)).getDefaultUserInfoFlags() | 16, str));
        putUserInfo.userProperties = new UserProperties(((UserTypeDetails) this.mUserTypes.get(putUserInfo.info.userType)).mDefaultUserProperties);
        this.mNextSerialNumber = 10;
        this.mUserVersion = 11;
        this.mUserTypeVersion = UserTypeFactory.getUserTypeVersion();
        Bundle bundle = new Bundle();
        try {
            for (String str2 : this.mContext.getResources().getStringArray(R.array.config_sfps_enroll_stage_thresholds)) {
                if (UserRestrictionsUtils.isValidRestriction(str2)) {
                    bundle.putBoolean(str2, true);
                }
            }
        } catch (Resources.NotFoundException e) {
            Slog.e("UserManagerService", "Couldn't find resource: config_defaultFirstUserRestrictions", e);
        }
        if (!bundle.isEmpty()) {
            synchronized (this.mRestrictionsLock) {
                this.mBaseUserRestrictions.updateRestrictions(0, bundle);
            }
        }
        initDefaultGuestRestrictions();
        writeUserLP(putUserInfo);
        writeUserListLP();
    }

    public final ActivityManagerInternal getActivityManagerInternal() {
        if (this.mAmInternal == null) {
            this.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        }
        return this.mAmInternal;
    }

    public final int getAliveUsersExcludingGuestsCountLU() {
        int size = this.mUsers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            UserInfo userInfo = ((UserData) this.mUsers.valueAt(i2)).info;
            if (!this.mRemovingUserIds.get(userInfo.id) && !userInfo.isGuest() && !userInfo.preCreated && !userInfo.isCloneProfile() && !userInfo.isBMode() && !userInfo.isManagedProfile()) {
                i++;
            }
        }
        return i;
    }

    public final Bundle getApplicationRestrictions(String str) {
        return getApplicationRestrictionsForUser(str, UserHandle.getCallingUserId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x002d, code lost:
    
        if (android.os.UserHandle.isSameApp(r0, r3) == false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle getApplicationRestrictionsForUser(java.lang.String r6, int r7) {
        /*
            r5 = this;
            int r0 = android.os.UserHandle.getCallingUserId()
            if (r0 != r7) goto L2f
            int r0 = android.os.Binder.getCallingUid()
            long r1 = android.os.Binder.clearCallingIdentity()
            android.content.Context r3 = r5.mContext     // Catch: java.lang.Throwable -> L20 android.content.pm.PackageManager.NameNotFoundException -> L25
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: java.lang.Throwable -> L20 android.content.pm.PackageManager.NameNotFoundException -> L25
            r4 = 4194304(0x400000, float:5.877472E-39)
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo(r6, r4)     // Catch: java.lang.Throwable -> L20 android.content.pm.PackageManager.NameNotFoundException -> L25
            int r3 = r3.uid     // Catch: java.lang.Throwable -> L20 android.content.pm.PackageManager.NameNotFoundException -> L25
            android.os.Binder.restoreCallingIdentity(r1)
            goto L29
        L20:
            r5 = move-exception
            android.os.Binder.restoreCallingIdentity(r1)
            throw r5
        L25:
            android.os.Binder.restoreCallingIdentity(r1)
            r3 = -1
        L29:
            boolean r0 = android.os.UserHandle.isSameApp(r0, r3)
            if (r0 != 0) goto L40
        L2f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "get application restrictions for other user/app "
            r0.<init>(r1)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            checkSystemOrRoot(r0)
        L40:
            com.android.server.pm.PersonaManagerService r0 = r5.sPersonaManager
            int r0 = r0.getAppSeparationId()
            if (r0 != r7) goto L49
            r7 = 0
        L49:
            java.lang.Object r5 = r5.mAppRestrictionsLock
            monitor-enter(r5)
            android.util.AtomicFile r0 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> L64
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L64
            java.io.File r7 = android.os.Environment.getUserSystemDirectory(r7)     // Catch: java.lang.Throwable -> L64
            java.lang.String r6 = packageToRestrictionsFileName(r6)     // Catch: java.lang.Throwable -> L64
            r1.<init>(r7, r6)     // Catch: java.lang.Throwable -> L64
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L64
            android.os.Bundle r6 = readApplicationRestrictionsLAr(r0)     // Catch: java.lang.Throwable -> L64
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L64
            return r6
        L64:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L64
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.getApplicationRestrictionsForUser(java.lang.String, int):android.os.Bundle");
    }

    public final int getBootUser() {
        checkCreateUsersPermission("Get boot user");
        try {
            return getBootUserUnchecked();
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final int getBootUserUnchecked() {
        synchronized (this.mUsersLock) {
            try {
                int i = this.mBootUser;
                if (i != -10000) {
                    UserData userData = (UserData) this.mUsers.get(i);
                    if (userData != null && userData.info.supportsSwitchToByUser()) {
                        Slogf.i("UserManagerService", "Using provided boot user: %d", Integer.valueOf(this.mBootUser));
                        return this.mBootUser;
                    }
                    Slogf.w("UserManagerService", "Provided boot user cannot be switched to: %d", Integer.valueOf(this.mBootUser));
                }
                if (!isHeadlessSystemUserMode()) {
                    return 0;
                }
                int previousFullUserToEnterForeground = getPreviousFullUserToEnterForeground();
                if (previousFullUserToEnterForeground != -10000) {
                    Slogf.i("UserManagerService", "Boot user is previous user %d", Integer.valueOf(previousFullUserToEnterForeground));
                    return previousFullUserToEnterForeground;
                }
                synchronized (this.mUsersLock) {
                    try {
                        int size = this.mUsers.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            UserData userData2 = (UserData) this.mUsers.valueAt(i2);
                            if (userData2.info.supportsSwitchToByUser()) {
                                int i3 = userData2.info.id;
                                Slogf.i("UserManagerService", "Boot user is first switchable user %d", Integer.valueOf(i3));
                                return i3;
                            }
                        }
                        throw new UserManager.CheckedUserOperationException("No switchable users found", 1);
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final int getCommunalProfileId() {
        checkQueryOrCreateUsersPermission("get communal profile user id");
        return getCommunalProfileIdUnchecked();
    }

    public final int getCommunalProfileIdUnchecked() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i)).info;
                    if (userInfo.isCommunalProfile() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo.id;
                    }
                }
                return -10000;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getCredentialOwnerProfile(int i) {
        checkManageUsersPermission("get the credential owner");
        if (!this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            synchronized (this.mUsersLock) {
                try {
                    UserInfo profileParentLU = getProfileParentLU(i);
                    if (profileParentLU != null) {
                        return profileParentLU.id;
                    }
                } finally {
                }
            }
        }
        return i;
    }

    public Pair getCurrentAndTargetUserIds() {
        ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal != null) {
            return activityManagerInternal.getCurrentAndTargetUserIds();
        }
        Slog.w("UserManagerService", "getCurrentAndTargetUserId() called too early, ActivityManagerInternal is not set yet");
        return new Pair(-10000, -10000);
    }

    public int getCurrentUserId() {
        ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal != null) {
            return activityManagerInternal.getCurrentUserId();
        }
        Slog.w("UserManagerService", "getCurrentUserId() called too early, ActivityManagerInternal is not set yet");
        return -10000;
    }

    public final IDarManagerService getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return this.mDarManagerService;
    }

    public final Bundle getDefaultGuestRestrictions() {
        Bundle bundle;
        checkManageUsersPermission("getDefaultGuestRestrictions");
        synchronized (this.mGuestRestrictions) {
            bundle = new Bundle(this.mGuestRestrictions);
        }
        return bundle;
    }

    public final UserInfo getEarliestCreatedFullUser() {
        List usersInternal = getUsersInternal(true, true, true);
        UserInfo userInfo = null;
        long j = Long.MAX_VALUE;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) usersInternal;
            if (i >= arrayList.size()) {
                return userInfo;
            }
            UserInfo userInfo2 = (UserInfo) arrayList.get(i);
            if (userInfo2.isFull() && userInfo2.isAdmin()) {
                long j2 = userInfo2.creationTime;
                if (j2 >= 0 && j2 < j) {
                    userInfo = userInfo2;
                    j = j2;
                }
            }
            i++;
        }
    }

    public final Bundle getEffectiveUserRestrictions(int i) {
        Bundle restrictions;
        synchronized (this.mRestrictionsLock) {
            try {
                restrictions = this.mCachedEffectiveUserRestrictions.getRestrictions(i);
                if (restrictions == null) {
                    restrictions = computeEffectiveUserRestrictionsLR(i);
                    this.mCachedEffectiveUserRestrictions.updateRestrictions(i, restrictions);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return restrictions;
    }

    public int getFreeProfileBadgeLU(int i, String str) {
        ArraySet arraySet = new ArraySet();
        int size = this.mUsers.size();
        for (int i2 = 0; i2 < size; i2++) {
            UserInfo userInfo = ((UserData) this.mUsers.valueAt(i2)).info;
            if (userInfo.userType.equals(str) && userInfo.profileGroupId == i && !this.mRemovingUserIds.get(userInfo.id)) {
                arraySet.add(Integer.valueOf(userInfo.profileBadge));
            }
        }
        int maxUsersOfTypePerParent = getMaxUsersOfTypePerParent(str);
        if (maxUsersOfTypePerParent == -1) {
            maxUsersOfTypePerParent = Integer.MAX_VALUE;
        }
        for (int i3 = 0; i3 < maxUsersOfTypePerParent; i3++) {
            if (!arraySet.contains(Integer.valueOf(i3))) {
                return i3;
            }
        }
        return 0;
    }

    public final List getGuestUsers() {
        checkManageUsersPermission("getGuestUsers");
        ArrayList arrayList = new ArrayList();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i)).info;
                    if (userInfo.isGuest() && !userInfo.guestToRemove && !userInfo.preCreated && !this.mRemovingUserIds.get(userInfo.id)) {
                        arrayList.add(userInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final int getMainDisplayIdAssignedToUser() {
        return this.mUserVisibilityMediator.getMainDisplayAssignedToUser(UserHandle.getUserId(Binder.getCallingUid()));
    }

    public final int getMainUserId() {
        checkQueryOrCreateUsersPermission("get main user id");
        return getMainUserIdUnchecked();
    }

    public final int getMainUserIdUnchecked() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i)).info;
                    if (userInfo.isMain() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo.id;
                    }
                }
                return -10000;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getMaxUsersOfTypePerParent(String str) {
        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(str);
        if (userTypeDetails == null) {
            return 0;
        }
        boolean z = Build.IS_DEBUGGABLE;
        int i = userTypeDetails.mMaxAllowedPerParent;
        return (z && UserManager.isUserTypeManagedProfile(userTypeDetails.mName)) ? SystemProperties.getInt("persist.sys.max_profiles", i) : i;
    }

    public int getNextAvailableId() {
        return getNextAvailableId(false, false);
    }

    public final int getNextAvailableId(boolean z, boolean z2) {
        synchronized (this.mUsersLock) {
            try {
                int scanNextAvailableIdLocked = scanNextAvailableIdLocked(z, z2);
                if (scanNextAvailableIdLocked >= 0) {
                    return scanNextAvailableIdLocked;
                }
                if (this.mRemovingUserIds.size() > 0) {
                    Slog.i("UserManagerService", "All available IDs are used. Recycling LRU ids.");
                    this.mRemovingUserIds.clear();
                    Iterator it = this.mRecentlyRemovedIds.iterator();
                    while (it.hasNext()) {
                        this.mRemovingUserIds.put(((Integer) it.next()).intValue(), true);
                    }
                    scanNextAvailableIdLocked = scanNextAvailableIdLocked(z, z2);
                }
                UserManager.invalidateStaticUserProperties();
                UserManager.invalidateUserPropertiesCache();
                if (scanNextAvailableIdLocked >= 0) {
                    return scanNextAvailableIdLocked;
                }
                throw new IllegalStateException("No user id available!");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getNumberOfUsersOfType(String str) {
        int i;
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i2)).info;
                    if (userInfo.userType.equals(str) && !userInfo.guestToRemove && !this.mRemovingUserIds.get(userInfo.id) && !userInfo.preCreated) {
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final UserData getPreCreatedUserLU(String str) {
        int size = this.mUsers.size();
        for (int i = 0; i < size; i++) {
            UserData userData = (UserData) this.mUsers.valueAt(i);
            UserInfo userInfo = userData.info;
            if (userInfo.preCreated && !userInfo.partial && userInfo.userType.equals(str)) {
                if (userData.info.isInitialized()) {
                    return userData;
                }
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("found pre-created user of type ", str, ", but it's not initialized yet: ");
                m.append(userData.info.toFullString());
                Slog.w("UserManagerService", m.toString());
            }
        }
        return null;
    }

    public final String[] getPreInstallableSystemPackages(String str) {
        checkCreateUsersPermission("getPreInstallableSystemPackages");
        Set installablePackagesForUserType = this.mSystemPackageInstaller.getInstallablePackagesForUserType(str);
        if (installablePackagesForUserType == null) {
            return null;
        }
        ArraySet arraySet = (ArraySet) installablePackagesForUserType;
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    public final int getPreviousFullUserToEnterForeground() {
        int i;
        checkQueryOrCreateUsersPermission("get previous user");
        int currentUserId = getCurrentUserId();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                i = -10000;
                long j = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    UserData userData = (UserData) this.mUsers.valueAt(i2);
                    UserInfo userInfo = userData.info;
                    int i3 = userInfo.id;
                    if (i3 != currentUserId && userInfo.isFull()) {
                        UserInfo userInfo2 = userData.info;
                        if (!userInfo2.partial && userInfo2.isEnabled() && !this.mRemovingUserIds.get(i3)) {
                            long j2 = userData.mLastEnteredForegroundTimeMillis;
                            if (j2 > j) {
                                j = j2;
                                i = i3;
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final UserInfo getPrimaryUser() {
        checkManageUsersPermission("query users");
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i)).info;
                    if (userInfo.isPrimary() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getPrivateProfileUserId() {
        synchronized (this.mUsersLock) {
            try {
                for (int i : getUserIds()) {
                    UserInfo userInfoLU = getUserInfoLU(i);
                    if (userInfoLU != null && userInfoLU.isPrivateProfile()) {
                        return userInfoLU.id;
                    }
                }
                return -10000;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getProfileAccessibilityLabelResId(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getProfileAccessibilityLabelResId");
        UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null) {
            return 0;
        }
        return userTypeDetails.mAccessibilityString;
    }

    public final int[] getProfileIds(int i, boolean z) {
        return getProfileIds(null, z, false, i);
    }

    public final int[] getProfileIds(String str, boolean z, boolean z2, int i) {
        long clearCallingIdentity;
        int[] array;
        if (i != UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("getting profiles related to user " + i);
        }
        this.sPersonaManager.getClass();
        if (PersonaManagerService.workTabSupportLauncherUids.contains(Integer.valueOf(Binder.getCallingUid()))) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mUsersLock) {
                    try {
                        IntArray profileIdsLU = getProfileIdsLU(str, z, z2, i);
                        int i2 = 0;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= profileIdsLU.size()) {
                                break;
                            }
                            if (profileIdsLU.get(i3) >= 150) {
                                i2 = profileIdsLU.get(i3);
                                break;
                            }
                            i3++;
                        }
                        if (i2 >= 150) {
                            profileIdsLU.remove(profileIdsLU.indexOf(i2));
                            return profileIdsLU.toArray();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
            }
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mUsersLock) {
                array = getProfileIdsLU(str, z, z2, i).toArray();
            }
            return array;
        } finally {
        }
    }

    public final int[] getProfileIdsExcludingHidden(int i, boolean z) {
        return getProfileIds(null, z, true, i);
    }

    public final IntArray getProfileIdsLU(String str, boolean z, boolean z2, int i) {
        int i2;
        UserInfo userInfoLU = getUserInfoLU(i);
        IntArray intArray = new IntArray(this.mUsers.size());
        if (userInfoLU == null) {
            return intArray;
        }
        int size = this.mUsers.size();
        for (int i3 = 0; i3 < size; i3++) {
            UserInfo userInfo = ((UserData) this.mUsers.valueAt(i3)).info;
            if ((userInfoLU.id == userInfo.id || ((i2 = userInfoLU.profileGroupId) != -10000 && i2 == userInfo.profileGroupId)) && ((!z || userInfo.isEnabled()) && !this.mRemovingUserIds.get(userInfo.id) && !userInfo.partial && (str == null || str.equals(userInfo.userType)))) {
                if (z2) {
                    UserProperties userPropertiesCopy = getUserPropertiesCopy(userInfo.id);
                    if (Flags.allowPrivateProfile() && android.multiuser.Flags.enableHidingProfiles() && android.multiuser.Flags.enablePrivateSpaceFeatures() && userPropertiesCopy.getProfileApiVisibility() == 1) {
                    }
                }
                intArray.add(userInfo.id);
            }
        }
        return intArray;
    }

    public final int getProfileLabelResId(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getProfileLabelResId");
        UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null) {
            return 0;
        }
        int i2 = userInfoNoChecks.profileBadge;
        int[] iArr = userTypeDetails.mLabels;
        if (iArr == null || iArr.length == 0 || i2 < 0) {
            return 0;
        }
        return iArr[Math.min(i2, iArr.length - 1)];
    }

    public final UserInfo getProfileParent(int i) {
        UserInfo profileParentLU;
        if (!hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            throw new SecurityException("You need MANAGE_USERS or INTERACT_ACROSS_USERS permission to get the profile parent");
        }
        synchronized (this.mUsersLock) {
            profileParentLU = getProfileParentLU(i);
        }
        return profileParentLU;
    }

    public final int getProfileParentId(int i) {
        checkManageUsersPermission("get the profile parent");
        return getProfileParentIdUnchecked(i);
    }

    public final int getProfileParentIdUnchecked(int i) {
        synchronized (this.mUsersLock) {
            try {
                UserInfo profileParentLU = getProfileParentLU(i);
                if (profileParentLU == null) {
                    return i;
                }
                return profileParentLU.id;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UserInfo getProfileParentLU(int i) {
        int i2;
        UserInfo userInfoLU = getUserInfoLU(i);
        if (userInfoLU == null || (i2 = userInfoLU.profileGroupId) == i || i2 == -10000) {
            return null;
        }
        return getUserInfoLU(i2);
    }

    public final String getProfileType(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getProfileType");
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null) {
                    return userInfoLU.isProfile() ? userInfoLU.userType : "";
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getProfiles(int i, boolean z) {
        boolean hasManageUsersOrPermission;
        List profilesLU;
        if (i != UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("getting profiles related to user " + i);
            hasManageUsersOrPermission = true;
        } else {
            hasManageUsersOrPermission = hasManageUsersOrPermission("android.permission.CREATE_USERS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mUsersLock) {
                profilesLU = getProfilesLU(i, z, hasManageUsersOrPermission);
            }
            return profilesLU;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getProfilesLU(int i, boolean z, boolean z2) {
        UserInfo userWithName;
        IntArray profileIdsLU = getProfileIdsLU(null, z, false, i);
        ArrayList arrayList = new ArrayList(profileIdsLU.size());
        for (int i2 = 0; i2 < profileIdsLU.size(); i2++) {
            UserInfo userInfo = ((UserData) this.mUsers.get(profileIdsLU.get(i2))).info;
            if (z2) {
                userWithName = userWithName(userInfo);
            } else {
                userWithName = new UserInfo(userInfo);
                userWithName.name = null;
                userWithName.iconPath = null;
            }
            arrayList.add(userWithName);
        }
        return arrayList;
    }

    public final int getRemainingCreatableProfileCount(int i, String str, boolean z) {
        checkQueryOrCreateUsersPermission("get the remaining number of profiles that can be added to the given user.");
        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(str);
        if (userTypeDetails == null || !isUserTypeEnabled(userTypeDetails)) {
            return 0;
        }
        boolean isUserTypeManagedProfile = UserManager.isUserTypeManagedProfile(userTypeDetails.mName);
        boolean isUserTypeCloneProfile = UserManager.isUserTypeCloneProfile(str);
        boolean isUserTypePrivateProfile = UserManager.isUserTypePrivateProfile(str);
        if (isUserTypeManagedProfile && !this.mContext.getPackageManager().hasSystemFeature("android.software.managed_users")) {
            return 0;
        }
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null && userInfoLU.canHaveProfile()) {
                    int length = getProfileIds(str, false, false, i).length;
                    Iterator it = ((ArrayList) getProfiles(i, false)).iterator();
                    while (it.hasNext()) {
                        if (SemPersonaManager.isSecureFolderId(((UserInfo) it.next()).id)) {
                            length--;
                        }
                    }
                    int i2 = 1;
                    int i3 = (length <= 0 || !z) ? 0 : 1;
                    int aliveUsersExcludingGuestsCountLU = getAliveUsersExcludingGuestsCountLU() - i3;
                    int maxSupportedUsers = UserManager.getMaxSupportedUsers() - aliveUsersExcludingGuestsCountLU;
                    if (maxSupportedUsers > 0 || ((!isUserTypeManagedProfile && !isUserTypeCloneProfile && !isUserTypePrivateProfile) || aliveUsersExcludingGuestsCountLU != 1)) {
                        i2 = maxSupportedUsers;
                    }
                    boolean z2 = Build.IS_DEBUGGABLE;
                    int i4 = userTypeDetails.mMaxAllowedPerParent;
                    if (z2 && UserManager.isUserTypeManagedProfile(userTypeDetails.mName)) {
                        i4 = SystemProperties.getInt("persist.sys.max_profiles", i4);
                    }
                    if (i4 != -1) {
                        i2 = Math.min(i2, i4 - (length - i3));
                    }
                    if (i2 <= 0) {
                        return 0;
                    }
                    int i5 = userTypeDetails.mMaxAllowed;
                    if (i5 != -1) {
                        i2 = Math.min(i2, i5 - (getNumberOfUsersOfType(str) - i3));
                    }
                    return Math.max(0, i2);
                }
                return 0;
            } finally {
            }
        }
    }

    public final int getRemainingCreatableProfileCount(String str, int i) {
        return getRemainingCreatableProfileCount(i, str, false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getRemainingCreatableUserCount(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = "get the remaining number of users that can be added."
            checkQueryOrCreateUsersPermission(r0)
            android.util.ArrayMap r0 = r8.mUserTypes
            java.lang.Object r0 = r0.get(r9)
            com.android.server.pm.UserTypeDetails r0 = (com.android.server.pm.UserTypeDetails) r0
            r1 = 0
            if (r0 == 0) goto L7b
            boolean r2 = isUserTypeEnabled(r0)
            if (r2 != 0) goto L18
            goto L7b
        L18:
            java.lang.Object r2 = r8.mUsersLock
            monitor-enter(r2)
            int r3 = r8.getAliveUsersExcludingGuestsCountLU()     // Catch: java.lang.Throwable -> L35
            boolean r4 = android.os.UserManager.isUserTypeGuest(r9)     // Catch: java.lang.Throwable -> L35
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != 0) goto L37
            boolean r4 = android.os.UserManager.isUserTypeDemo(r9)     // Catch: java.lang.Throwable -> L35
            if (r4 == 0) goto L2f
            goto L37
        L2f:
            int r4 = android.os.UserManager.getMaxSupportedUsers()     // Catch: java.lang.Throwable -> L35
            int r4 = r4 - r3
            goto L38
        L35:
            r8 = move-exception
            goto L79
        L37:
            r4 = r5
        L38:
            java.lang.String r6 = r0.mName     // Catch: java.lang.Throwable -> L35
            boolean r6 = android.os.UserManager.isUserTypeManagedProfile(r6)     // Catch: java.lang.Throwable -> L35
            if (r6 == 0) goto L5f
            android.content.Context r6 = r8.mContext     // Catch: java.lang.Throwable -> L35
            android.content.pm.PackageManager r6 = r6.getPackageManager()     // Catch: java.lang.Throwable -> L35
            java.lang.String r7 = "android.software.managed_users"
            boolean r6 = r6.hasSystemFeature(r7)     // Catch: java.lang.Throwable -> L35
            if (r6 != 0) goto L50
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L35
            return r1
        L50:
            r6 = 1
            if (r4 > 0) goto L55
            r7 = r6
            goto L56
        L55:
            r7 = r1
        L56:
            if (r3 != r6) goto L5a
            r3 = r6
            goto L5b
        L5a:
            r3 = r1
        L5b:
            r3 = r3 & r7
            if (r3 == 0) goto L5f
            r4 = r6
        L5f:
            if (r4 > 0) goto L63
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L35
            return r1
        L63:
            int r0 = r0.mMaxAllowed     // Catch: java.lang.Throwable -> L35
            r3 = -1
            if (r0 != r3) goto L69
            goto L6f
        L69:
            int r8 = r8.getNumberOfUsersOfType(r9)     // Catch: java.lang.Throwable -> L35
            int r5 = r0 - r8
        L6f:
            int r8 = java.lang.Math.min(r4, r5)     // Catch: java.lang.Throwable -> L35
            int r8 = java.lang.Math.max(r1, r8)     // Catch: java.lang.Throwable -> L35
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L35
            return r8
        L79:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L35
            throw r8
        L7b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.getRemainingCreatableUserCount(java.lang.String):int");
    }

    public final String getSeedAccountName(int i) {
        String str;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            UserData userDataLU = getUserDataLU(i);
            str = userDataLU == null ? null : userDataLU.seedAccountName;
        }
        return str;
    }

    public final PersistableBundle getSeedAccountOptions(int i) {
        PersistableBundle persistableBundle;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            UserData userDataLU = getUserDataLU(i);
            persistableBundle = userDataLU == null ? null : userDataLU.seedAccountOptions;
        }
        return persistableBundle;
    }

    public final String getSeedAccountType(int i) {
        String str;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            UserData userDataLU = getUserDataLU(i);
            str = userDataLU == null ? null : userDataLU.seedAccountType;
        }
        return str;
    }

    public final String getUserAccount(int i) {
        String str;
        checkManageUserAndAcrossUsersFullPermission("get user account");
        synchronized (this.mUsersLock) {
            str = ((UserData) this.mUsers.get(i)).account;
        }
        return str;
    }

    public final int getUserBadgeColorResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeColorResId");
        UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested badge dark color for non-badged user ", "UserManagerService");
            return 0;
        }
        int i2 = userInfoNoChecks.profileBadge;
        int[] iArr = userTypeDetails.mBadgeColors;
        if (iArr == null || iArr.length == 0 || i2 < 0) {
            return 0;
        }
        return iArr[Math.min(i2, iArr.length - 1)];
    }

    public final int getUserBadgeDarkColorResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeDarkColorResId");
        UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        int i2 = 0;
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested badge color for non-badged user ", "UserManagerService");
            return 0;
        }
        int i3 = userInfoNoChecks.profileBadge;
        int[] iArr = userTypeDetails.mDarkThemeBadgeColors;
        if (iArr != null && iArr.length != 0 && i3 >= 0) {
            return iArr[Math.min(i3, iArr.length - 1)];
        }
        int[] iArr2 = userTypeDetails.mBadgeColors;
        if (iArr2 != null && iArr2.length != 0 && i3 >= 0) {
            i2 = iArr2[Math.min(i3, iArr2.length - 1)];
        }
        return i2;
    }

    public final int getUserBadgeLabelResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeLabelResId");
        UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested badge label for non-badged user ", "UserManagerService");
            return 0;
        }
        int i2 = userInfoNoChecks.profileBadge;
        int[] iArr = userTypeDetails.mBadgeLabels;
        if (iArr == null || iArr.length == 0 || i2 < 0) {
            return 0;
        }
        return iArr[Math.min(i2, iArr.length - 1)];
    }

    public final int getUserBadgeNoBackgroundResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeNoBackgroundResId");
        UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge()) {
            return userTypeDetailsNoChecks.mBadgeNoBackground;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested badge (no background) for non-badged user ", "UserManagerService");
        return 0;
    }

    public final int getUserBadgeResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeResId");
        UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge()) {
            return userTypeDetailsNoChecks.mBadgePlain;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested badge for non-badged user ", "UserManagerService");
        return 0;
    }

    public final long getUserCreationTime(int i) {
        UserInfo userInfoLU;
        int callingUserId = UserHandle.getCallingUserId();
        synchronized (this.mUsersLock) {
            try {
                if (callingUserId == i) {
                    userInfoLU = getUserInfoLU(i);
                } else {
                    UserInfo profileParentLU = getProfileParentLU(i);
                    userInfoLU = (profileParentLU == null || profileParentLU.id != callingUserId) ? null : getUserInfoLU(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (userInfoLU != null) {
            return userInfoLU.creationTime;
        }
        throw new SecurityException("userId can only be the calling user or a profile associated with this user");
    }

    public final UserData getUserDataLU(int i) {
        UserData userData = (UserData) this.mUsers.get(i);
        if (userData == null || !userData.info.partial || this.mRemovingUserIds.get(i)) {
            return userData;
        }
        return null;
    }

    public final UserData getUserDataNoChecks(int i) {
        UserData userData;
        synchronized (this.mUsersLock) {
            userData = (UserData) this.mUsers.get(i);
        }
        return userData;
    }

    public final ResilientAtomicFile getUserFile(final int i) {
        return new ResilientAtomicFile(new File(this.mUsersDir, NandswapManager$$ExternalSyntheticOutline0.m(i, ".xml")), new File(this.mUsersDir, NandswapManager$$ExternalSyntheticOutline0.m(i, ".xml.backup")), new File(this.mUsersDir, NandswapManager$$ExternalSyntheticOutline0.m(i, ".xml.reservecopy")), 505, "user info", new ResilientAtomicFile.ReadEventLogger() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda4
            @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
            public final void logEvent(int i2, String str) {
                UserManagerService userManagerService = UserManagerService.this;
                userManagerService.getClass();
                Slog.e("UserManagerService", str);
                int i3 = i;
                if (userManagerService.getUserDataNoChecks(i3) != null) {
                    userManagerService.scheduleWriteUser(i3);
                }
            }
        });
    }

    public final int getUserHandle(int i) {
        synchronized (this.mUsersLock) {
            try {
                for (int i2 : this.mUserIds) {
                    UserInfo userInfoLU = getUserInfoLU(i2);
                    if (userInfoLU != null && userInfoLU.serialNumber == i) {
                        return i2;
                    }
                }
                return -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor getUserIcon(int i) {
        int i2;
        if (!hasManageUsersOrPermission("android.permission.GET_ACCOUNTS_PRIVILEGED")) {
            throw new SecurityException("You need MANAGE_USERS or GET_ACCOUNTS_PRIVILEGED permissions to: get user icon");
        }
        synchronized (this.mPackagesLock) {
            try {
                UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
                if (userInfoNoChecks != null && !userInfoNoChecks.partial) {
                    UserInfo userInfoNoChecks2 = getUserInfoNoChecks(UserHandle.getCallingUserId());
                    if (userInfoNoChecks2.id != userInfoNoChecks.id && ((!android.multiuser.Flags.supportCommunalProfile() || !userInfoNoChecks.isCommunalProfile()) && ((i2 = userInfoNoChecks2.profileGroupId) == -10000 || i2 != userInfoNoChecks.profileGroupId))) {
                        checkManageUsersPermission("get the icon of a user who is not related");
                    }
                    String str = userInfoNoChecks.iconPath;
                    if (str == null) {
                        return null;
                    }
                    try {
                        return ParcelFileDescriptor.open(new File(str), 268435456);
                    } catch (FileNotFoundException e) {
                        Slog.e("UserManagerService", "Couldn't find icon file", e);
                        return null;
                    }
                }
                Slog.w("UserManagerService", "getUserIcon: unknown user #" + i);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getUserIconBadgeResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserIconBadgeResId");
        UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge()) {
            return userTypeDetailsNoChecks.mIconBadge;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Requested icon badge for non-badged user ", "UserManagerService");
        return 0;
    }

    public final int[] getUserIds() {
        int[] iArr;
        synchronized (this.mUsersLock) {
            iArr = this.mUserIds;
        }
        return iArr;
    }

    public final int[] getUserIdsIncludingPreCreated() {
        int[] iArr;
        synchronized (this.mUsersLock) {
            iArr = this.mUserIdsIncludingPreCreated;
        }
        return iArr;
    }

    public final UserInfo getUserInfo(int i) {
        UserInfo userWithName;
        checkQueryOrCreateUsersPermission("query user");
        if (UserManager.isVirtualUserId(i)) {
            return new UserInfo(i, (String) null, (String) null, Integer.MIN_VALUE);
        }
        synchronized (this.mUsersLock) {
            userWithName = userWithName(getUserInfoLU(i));
        }
        return userWithName;
    }

    public final UserInfo getUserInfoLU(int i) {
        UserData userData = (UserData) this.mUsers.get(i);
        if (userData != null && userData.info.partial && !this.mRemovingUserIds.get(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "getUserInfo: unknown user #", "UserManagerService");
            return null;
        }
        if (userData != null) {
            return userData.info;
        }
        return null;
    }

    public final UserInfo getUserInfoNoChecks(int i) {
        UserInfo userInfo;
        synchronized (this.mUsersLock) {
            try {
                UserData userData = (UserData) this.mUsers.get(i);
                userInfo = userData != null ? userData.info : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return userInfo;
    }

    public final ResilientAtomicFile getUserListFile() {
        return new ResilientAtomicFile(this.mUserListFile, new File(this.mUserListFile.getParent(), this.mUserListFile.getName() + ".backup"), new File(this.mUserListFile.getParent(), this.mUserListFile.getName() + ".reservecopy"), 505, "user list", new UserManagerService$$ExternalSyntheticLambda1(this));
    }

    public final String getUserName() {
        String str;
        int callingUid = Binder.getCallingUid();
        if (!hasQueryOrCreateUsersPermission() && !hasPermissionGranted(callingUid, "android.permission.GET_ACCOUNTS_PRIVILEGED")) {
            throw new SecurityException("You need MANAGE_USERS, CREATE_USERS, QUERY_USERS, or GET_ACCOUNTS_PRIVILEGED permissions to: get user name");
        }
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mUsersLock) {
            try {
                UserInfo userWithName = userWithName(getUserInfoLU(userId));
                return (userWithName == null || (str = userWithName.name) == null) ? "" : str;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UserProperties getUserPropertiesCopy(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserProperties");
        UserProperties userPropertiesInternal = getUserPropertiesInternal(i);
        if (userPropertiesInternal != null) {
            return new UserProperties(userPropertiesInternal, Binder.getCallingUid() == 1000, hasManageUsersPermission(), hasManageUsersOrPermission("android.permission.QUERY_USERS"));
        }
        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Cannot access properties for user "));
    }

    public final UserProperties getUserPropertiesInternal(int i) {
        synchronized (this.mUsersLock) {
            try {
                if (UserManager.isVirtualUserId(i) && this.mLockPatternUtils.getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(i)) {
                    i = 0;
                }
                UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    return null;
                }
                return userDataLU.userProperties;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getUserRemovabilityLocked(int i, String str) {
        String formatSimple = TextUtils.formatSimple("User %d can not be %s, ", new Object[]{Integer.valueOf(i), str});
        if (i == 0) {
            Slog.e("UserManagerService", formatSimple + "system user cannot be removed.");
            return -4;
        }
        UserData userData = (UserData) this.mUsers.get(i);
        if (userData == null) {
            Slog.e("UserManagerService", formatSimple + "invalid user id provided.");
            return -3;
        }
        if (userData.info.isMain() && Resources.getSystem().getBoolean(R.bool.config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled)) {
            Slog.e("UserManagerService", formatSimple + "main user cannot be removed when it's a permanent admin user.");
            return -5;
        }
        if (!this.mRemovingUserIds.get(i)) {
            return 3;
        }
        Slog.w("UserManagerService", formatSimple + "it is already scheduled for removal.");
        return 2;
    }

    public final String getUserRemovalRestriction(int i) {
        UserInfo userInfoLU;
        synchronized (this.mUsersLock) {
            userInfoLU = getUserInfoLU(i);
        }
        return (userInfoLU == null || !userInfoLU.isManagedProfile()) ? "no_remove_user" : "no_remove_managed_profile";
    }

    public final int getUserRestrictionSource(String str, int i) {
        List userRestrictionSources = getUserRestrictionSources(str, i);
        int i2 = 0;
        for (int size = userRestrictionSources.size() - 1; size >= 0; size--) {
            i2 |= ((UserManager.EnforcingUser) userRestrictionSources.get(size)).getUserRestrictionSource();
        }
        return i2;
    }

    public final List getUserRestrictionSources(String str, int i) {
        if (!hasManageUsersOrPermission("android.permission.QUERY_USERS")) {
            throw new SecurityException("You either need MANAGE_USERS or QUERY_USERS permission to: ".concat("call getUserRestrictionSources."));
        }
        if (!hasUserRestriction(str, i)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        if (hasBaseUserRestriction(str, i)) {
            arrayList.add(new UserManager.EnforcingUser(-10000, 1));
        }
        if (this.mDevicePolicyManagerInternal == null) {
            this.mDevicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = this.mDevicePolicyManagerInternal;
        if (devicePolicyManagerInternal != null) {
            arrayList.addAll(devicePolicyManagerInternal.getUserRestrictionSources(str, i));
        }
        return arrayList;
    }

    public final Bundle getUserRestrictions(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserRestrictions");
        return BundleUtils.clone(getEffectiveUserRestrictions(i));
    }

    public final int getUserSerialNumber(int i) {
        int i2;
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                i2 = userInfoLU != null ? userInfoLU.serialNumber : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final long getUserStartRealtime() {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mUsersLock) {
            try {
                UserData userDataLU = getUserDataLU(userId);
                if (userDataLU == null) {
                    return 0L;
                }
                return userDataLU.startRealtime;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getUserStatusBarIconResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserStatusBarIconResId");
        UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge()) {
            return userTypeDetailsNoChecks.mStatusBarIcon;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Requested status bar icon for non-badged user ", "UserManagerService");
        return 0;
    }

    public final int getUserSwitchability(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserSwitchability");
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("getUserSwitchability-" + i);
        timingsTraceAndSlog.traceBegin("TM.isInCall");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
            int i2 = (!com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMappingForPublicApis() ? !(telecomManager == null || !telecomManager.isInCall()) : this.mContext.getPackageManager().hasSystemFeature("android.software.telecom") && telecomManager != null && telecomManager.isInCall()) ? 0 : 1;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("hasUserRestriction-DISALLOW_USER_SWITCH");
            if (this.mLocalService.hasUserRestriction("no_user_switch", i)) {
                i2 |= 2;
            }
            timingsTraceAndSlog.traceEnd();
            if (!isHeadlessSystemUserMode()) {
                timingsTraceAndSlog.traceBegin("getInt-ALLOW_USER_SWITCHING_WHEN_SYSTEM_USER_LOCKED");
                boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "allow_user_switching_when_system_user_locked", 0) != 0;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("isUserUnlocked-USER_SYSTEM");
                boolean isUserUnlocked = this.mLocalService.isUserUnlocked(0);
                timingsTraceAndSlog.traceEnd();
                if (!z && !isUserUnlocked) {
                    i2 |= 4;
                }
            }
            timingsTraceAndSlog.traceEnd();
            return i2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final UserTypeDetails getUserTypeDetails(UserInfo userInfo) {
        String str = userInfo != null ? userInfo.userType : null;
        if (str != null) {
            return (UserTypeDetails) this.mUserTypes.get(str);
        }
        return null;
    }

    public final UserTypeDetails getUserTypeDetailsNoChecks(int i) {
        String str;
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                str = userInfoLU != null ? userInfoLU.userType : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str != null) {
            return (UserTypeDetails) this.mUserTypes.get(str);
        }
        return null;
    }

    public final long getUserUnlockRealtime() {
        synchronized (this.mUsersLock) {
            try {
                UserData userDataLU = getUserDataLU(UserHandle.getUserId(Binder.getCallingUid()));
                if (userDataLU == null) {
                    return 0L;
                }
                return userDataLU.unlockRealtime;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getUsers(boolean z, boolean z2, boolean z3) {
        checkCreateUsersPermission("query users");
        return getUsersInternal(z, z2, z3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
    
        if (r7.mRemovingUserIds.get(r4.id) != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getUsersInternal(boolean r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mUsersLock
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L28
            android.util.SparseArray r2 = r7.mUsers     // Catch: java.lang.Throwable -> L28
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L28
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L28
            android.util.SparseArray r2 = r7.mUsers     // Catch: java.lang.Throwable -> L28
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L28
            r3 = 0
        L15:
            if (r3 >= r2) goto L47
            android.util.SparseArray r4 = r7.mUsers     // Catch: java.lang.Throwable -> L28
            java.lang.Object r4 = r4.valueAt(r3)     // Catch: java.lang.Throwable -> L28
            com.android.server.pm.UserManagerService$UserData r4 = (com.android.server.pm.UserManagerService.UserData) r4     // Catch: java.lang.Throwable -> L28
            android.content.pm.UserInfo r4 = r4.info     // Catch: java.lang.Throwable -> L28
            if (r8 == 0) goto L2a
            boolean r5 = r4.partial     // Catch: java.lang.Throwable -> L28
            if (r5 != 0) goto L44
            goto L2a
        L28:
            r7 = move-exception
            goto L49
        L2a:
            if (r9 == 0) goto L36
            android.util.SparseBooleanArray r5 = r7.mRemovingUserIds     // Catch: java.lang.Throwable -> L28
            int r6 = r4.id     // Catch: java.lang.Throwable -> L28
            boolean r5 = r5.get(r6)     // Catch: java.lang.Throwable -> L28
            if (r5 != 0) goto L44
        L36:
            if (r10 == 0) goto L3d
            boolean r5 = r4.preCreated     // Catch: java.lang.Throwable -> L28
            if (r5 == 0) goto L3d
            goto L44
        L3d:
            android.content.pm.UserInfo r4 = r7.userWithName(r4)     // Catch: java.lang.Throwable -> L28
            r1.add(r4)     // Catch: java.lang.Throwable -> L28
        L44:
            int r3 = r3 + 1
            goto L15
        L47:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            return r1
        L49:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.getUsersInternal(boolean, boolean, boolean):java.util.List");
    }

    public final int[] getVisibleUsers() {
        if (!hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            throw new SecurityException("Caller needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to get list of visible users");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mUserVisibilityMediator.getVisibleUsers().toArray();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasBadge(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "hasBadge");
        UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        return userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge();
    }

    public final boolean hasBaseUserRestriction(String str, int i) {
        checkCreateUsersPermission("hasBaseUserRestriction");
        boolean z = false;
        if (!UserRestrictionsUtils.isValidRestriction(str)) {
            return false;
        }
        synchronized (this.mRestrictionsLock) {
            try {
                Bundle restrictions = this.mBaseUserRestrictions.getRestrictions(i);
                if (restrictions != null && restrictions.getBoolean(str, false)) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean hasRestrictedProfiles(int i) {
        checkManageUsersPermission("hasRestrictedProfiles");
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i2)).info;
                    if (i != userInfo.id && userInfo.restrictedProfileParentId == i) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasUserRestriction(String str, int i) {
        if (!userExists(i)) {
            return false;
        }
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "hasUserRestriction");
        return this.mLocalService.hasUserRestriction(str, i);
    }

    public final boolean hasUserRestrictionOnAnyUser(String str) {
        if (!UserRestrictionsUtils.isValidRestriction(str)) {
            return false;
        }
        List users = getUsers(true, true, true);
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) users;
            if (i >= arrayList.size()) {
                return false;
            }
            if (getEffectiveUserRestrictions(((UserInfo) arrayList.get(i)).id).getBoolean(str)) {
                return true;
            }
            i++;
        }
    }

    public final void initDefaultGuestRestrictions() {
        synchronized (this.mGuestRestrictions) {
            try {
                if (this.mGuestRestrictions.isEmpty()) {
                    UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get("android.os.usertype.full.GUEST");
                    if (userTypeDetails == null) {
                        Slog.wtf("UserManagerService", "Can't set default guest restrictions: type doesn't exist.");
                        return;
                    }
                    UserRestrictionsUtils.merge(this.mGuestRestrictions, userTypeDetails.mDefaultRestrictions);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void invalidateOwnerNameIfNecessary(Resources resources, boolean z) {
        int updateFrom = this.mLastConfiguration.updateFrom(resources.getConfiguration());
        if (z || (this.mOwnerNameTypedValue.changingConfigurations & updateFrom) != 0) {
            resources.getValue(R.string.sms_short_code_confirm_deny, this.mOwnerNameTypedValue, true);
            CharSequence coerceToString = this.mOwnerNameTypedValue.coerceToString();
            this.mOwnerName.set(coerceToString != null ? coerceToString.toString() : null);
        }
    }

    public final boolean isAdminUser(int i) {
        boolean z;
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId != i && !isSameProfileGroupNoChecks(callingUserId, i)) {
            checkQueryOrCreateUsersPermission("isAdminUser");
        }
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.isAdmin();
            } finally {
            }
        }
        return z;
    }

    public final boolean isCrossProfileIntentFilterAccessible(int i, int i2, boolean z) {
        UserProperties userPropertiesInternal = getUserPropertiesInternal(i);
        int crossProfileIntentFilterAccessControl = userPropertiesInternal != null ? userPropertiesInternal.getCrossProfileIntentFilterAccessControl() : 0;
        UserProperties userPropertiesInternal2 = getUserPropertiesInternal(i2);
        int max = Math.max(crossProfileIntentFilterAccessControl, userPropertiesInternal2 != null ? userPropertiesInternal2.getCrossProfileIntentFilterAccessControl() : 0);
        if (10 == max) {
            boolean z2 = PackageManagerServiceUtils.DEBUG;
            if (!PackageManagerServiceUtils.isSystemOrRoot(Binder.getCallingUid())) {
                return false;
            }
        }
        if (20 != max) {
            return true;
        }
        if (z) {
            boolean z3 = PackageManagerServiceUtils.DEBUG;
            if (PackageManagerServiceUtils.isSystemOrRoot(Binder.getCallingUid())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentUserOrRunningProfileOfCurrentUser(int i) {
        int currentUserId = getCurrentUserId();
        if (currentUserId == i) {
            return true;
        }
        if (isProfileUnchecked(i) && getProfileParentIdUnchecked(i) == currentUserId) {
            return isUserRunning(i);
        }
        return false;
    }

    public final boolean isDemoUser(int i) {
        if (UserHandle.getCallingUserId() != i && !hasManageUsersPermission()) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "You need MANAGE_USERS permission to query if u=", " is a demo user"));
        }
        boolean z = false;
        if (SystemProperties.getBoolean("ro.boot.arc_demo_mode", false)) {
            return true;
        }
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null && userInfoLU.isDemo()) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isForegroundUserAdmin() {
        synchronized (this.mUsersLock) {
            try {
                int currentUserId = getCurrentUserId();
                boolean z = false;
                if (currentUserId == -10000) {
                    return false;
                }
                UserInfo userInfoLU = getUserInfoLU(currentUserId);
                if (userInfoLU != null && userInfoLU.isAdmin()) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    public final boolean isHeadlessSystemUserMode() {
        boolean z;
        synchronized (this.mUsersLock) {
            z = !((UserData) this.mUsers.get(0)).info.isFull();
        }
        return z;
    }

    public final boolean isPreCreated(int i) {
        boolean z;
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isPreCreated");
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.preCreated;
            } finally {
            }
        }
        return z;
    }

    public final boolean isProfileUnchecked(int i) {
        boolean z;
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.isProfile();
            } finally {
            }
        }
        return z;
    }

    public final boolean isQuietModeEnabled(int i) {
        UserInfo userInfoLU;
        synchronized (this.mPackagesLock) {
            try {
                synchronized (this.mUsersLock) {
                    userInfoLU = getUserInfoLU(i);
                }
                if (userInfoLU != null && userInfoLU.isProfile()) {
                    return userInfoLU.isQuietModeEnabled();
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isRestricted(int i) {
        boolean isRestricted;
        if (i != UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("query isRestricted for user " + i);
        }
        synchronized (this.mUsersLock) {
            UserInfo userInfoLU = getUserInfoLU(i);
            isRestricted = userInfoLU == null ? false : userInfoLU.isRestricted();
        }
        return isRestricted;
    }

    public final boolean isSameProfileGroup(int i, int i2) {
        if (i == i2) {
            return true;
        }
        if (hasManageUsersOrPermission("android.permission.QUERY_USERS")) {
            return isSameProfileGroupNoChecks(i, i2);
        }
        throw new SecurityException("You either need MANAGE_USERS or QUERY_USERS permission to: ".concat("check if in the same profile group"));
    }

    public final boolean isSameProfileGroupNoChecks(int i, int i2) {
        int i3;
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null && userInfoLU.profileGroupId != -10000) {
                    UserInfo userInfoLU2 = getUserInfoLU(i2);
                    if (userInfoLU2 != null && (i3 = userInfoLU2.profileGroupId) != -10000) {
                        return userInfoLU.profileGroupId == i3;
                    }
                    return false;
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) {
        UserManager userManager;
        String str3;
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Non-system caller");
        }
        Context context = this.mContext;
        Set set = UserRestrictionsUtils.USER_RESTRICTIONS;
        Objects.requireNonNull(str);
        userManager = (UserManager) context.getSystemService(UserManager.class);
        switch (str) {
            case "adb_wifi_enabled":
            case "adb_enabled":
                if (!"0".equals(str2)) {
                    str3 = "no_debugging_features";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "location_mode":
                if (userManager.hasUserRestriction("no_config_location", UserHandle.of(i)) && i2 != 1000) {
                    return true;
                }
                if (!String.valueOf(0).equals(str2)) {
                    str3 = "no_share_location";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "doze_enabled":
            case "doze_always_on":
            case "doze_pulse_on_long_press":
            case "doze_pulse_on_double_tap":
            case "doze_pulse_on_pick_up":
                if (!"0".equals(str2)) {
                    str3 = "no_ambient_display";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "verifier_verify_adb_installs":
                if (!"1".equals(str2)) {
                    str3 = "ensure_verify_apps";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "screen_brightness_mode":
            case "screen_brightness":
                if (i2 != 1000) {
                    str3 = "no_config_brightness";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "always_on_vpn_app":
            case "always_on_vpn_lockdown_whitelist":
            case "always_on_vpn_lockdown":
                int appId = UserHandle.getAppId(i2);
                if (appId != 1000 && appId != 0) {
                    str3 = "no_config_vpn";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "preferred_network_mode":
                str3 = "no_config_mobile_networks";
                return userManager.hasUserRestriction(str3, UserHandle.of(i));
            case "safe_boot_disallowed":
                if (!"1".equals(str2)) {
                    str3 = "no_safe_boot";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "screen_off_timeout":
                if (i2 != 1000) {
                    str3 = "no_config_screen_timeout";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "private_dns_mode":
            case "private_dns_specifier":
                if (i2 != 1000) {
                    str3 = "disallow_config_private_dns";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "airplane_mode_on":
                if (!"0".equals(str2)) {
                    str3 = "no_airplane_mode";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "auto_time_zone":
            case "auto_time":
                if (i2 != 1000) {
                    str3 = "no_config_date_time";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            case "install_non_market_apps":
                if (!"0".equals(str2)) {
                    str3 = "no_install_unknown_sources";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
            default:
                if (str.startsWith("data_roaming") && !"0".equals(str2)) {
                    str3 = "no_data_roaming";
                    return userManager.hasUserRestriction(str3, UserHandle.of(i));
                }
                break;
        }
        return false;
    }

    public final boolean isUserForeground(int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId == i || hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            return i == getCurrentUserId();
        }
        throw new SecurityException(DualAppManagerService$$ExternalSyntheticOutline0.m(callingUserId, i, "Caller from user ", " needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to check if another user (", ") is running in the foreground"));
    }

    public final boolean isUserLimitReached() {
        int aliveUsersExcludingGuestsCountLU;
        synchronized (this.mUsersLock) {
            aliveUsersExcludingGuestsCountLU = getAliveUsersExcludingGuestsCountLU();
        }
        return aliveUsersExcludingGuestsCountLU >= UserManager.getMaxSupportedUsers() && !isCreationOverrideEnabled();
    }

    public final boolean isUserNameSet(int i) {
        boolean z;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (!hasQueryOrCreateUsersPermission() && (userId != i || !hasPermissionGranted(callingUid, "android.permission.GET_ACCOUNTS_PRIVILEGED"))) {
            throw new SecurityException("You need MANAGE_USERS, CREATE_USERS, QUERY_USERS, or GET_ACCOUNTS_PRIVILEGED permissions to: get whether user name is set");
        }
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                z = (userInfoLU == null || userInfoLU.name == null) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public final boolean isUserOfType(int i, String str) {
        String str2;
        checkQueryOrCreateUsersPermission("check user type");
        if (str != null) {
            synchronized (this.mUsersLock) {
                try {
                    UserInfo userInfoLU = getUserInfoLU(i);
                    str2 = userInfoLU != null ? userInfoLU.userType : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUserRunning(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserRunning");
        return this.mLocalService.isUserRunning(i);
    }

    public boolean isUserSwitcherEnabled(int i) {
        return UserManager.supportsMultipleUsers() && !hasUserRestriction("no_user_switch", i) && !UserManager.isDeviceInDemoMode(this.mContext) && (Settings.Global.getInt(this.mContext.getContentResolver(), "user_switcher_enabled", MultiUserSupportsHelper.IS_TABLET ? 1 : 0) != 0) == true;
    }

    public final boolean isUserSwitcherEnabled(boolean z, int i) {
        if (!isUserSwitcherEnabled(i)) {
            return false;
        }
        if (!z && hasUserRestriction("no_add_user", i)) {
            Iterator it = ((ArrayList) getUsers(true, true, true)).iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                if (((UserInfo) it.next()).supportsSwitchToByUser()) {
                    if (!z2) {
                        z2 = true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public final boolean isUserTypeEnabled(String str) {
        checkCreateUsersPermission("check if user type is enabled.");
        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(str);
        return userTypeDetails != null && isUserTypeEnabled(userTypeDetails);
    }

    public final boolean isUserUnlocked(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserUnlocked");
        return this.mLocalService.isUserUnlocked(i);
    }

    public final boolean isUserUnlockingOrUnlocked(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserUnlockingOrUnlocked");
        return this.mLocalService.isUserUnlockingOrUnlocked(i);
    }

    public final boolean isUserVisible(int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId == i || hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            return this.mUserVisibilityMediator.isUserVisible(i);
        }
        throw new SecurityException(DualAppManagerService$$ExternalSyntheticOutline0.m(callingUserId, i, "Caller from user ", " needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to check if another user (", ") is visible"));
    }

    public final void makeInitialized(int i) {
        boolean z;
        checkManageUsersPermission("makeInitialized");
        synchronized (this.mUsersLock) {
            try {
                UserData userData = (UserData) this.mUsers.get(i);
                if (userData != null) {
                    UserInfo userInfo = userData.info;
                    if (!userInfo.partial) {
                        int i2 = userInfo.flags;
                        if ((i2 & 16) == 0) {
                            userInfo.flags = i2 | 16;
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            scheduleWriteUser(i);
                            return;
                        }
                        return;
                    }
                }
                Slog.w("UserManagerService", "makeInitialized: unknown user #" + i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean markGuestForDeletion(int i) {
        checkManageUsersPermission("Only the system can remove users");
        if (getUserRestrictions(UserHandle.getCallingUserId()).getBoolean("no_remove_user", false)) {
            Slog.w("UserManagerService", "Cannot remove user. DISALLOW_REMOVE_USER is enabled.");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mPackagesLock) {
                synchronized (this.mUsersLock) {
                    UserData userData = (UserData) this.mUsers.get(i);
                    if (i != 0 && userData != null && !this.mRemovingUserIds.get(i)) {
                        if (!userData.info.isGuest()) {
                            return false;
                        }
                        UserInfo userInfo = userData.info;
                        userInfo.guestToRemove = true;
                        userInfo.flags |= 64;
                        writeUserLP(userData);
                        return true;
                    }
                    return false;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void markMaintenanceModeUserForDeletion(UserData userData) {
        if (userData == null || userData.info == null) {
            return;
        }
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                addRemovingUserIdLocked(77);
            }
            UserInfo userInfo = userData.info;
            userInfo.partial = true;
            userInfo.flags |= 64;
            writeUserLP(userData);
        }
    }

    public void maybeScheduleAlarmToAutoLockPrivateSpace() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "private_space_auto_lock", 2, getMainUserIdUnchecked());
        if (intForUser != 1) {
            Slogf.d("UserManagerService", "Not scheduling auto-lock on inactivity,preference is set to %d", Integer.valueOf(intForUser));
            return;
        }
        int privateProfileUserId = getPrivateProfileUserId();
        if (privateProfileUserId != -10000) {
            if (isQuietModeEnabled(privateProfileUserId)) {
                Slogf.d("UserManagerService", "Not scheduling auto-lock alarm for %d, quiet mode already enabled", Integer.valueOf(privateProfileUserId));
            } else {
                scheduleAlarmToAutoLockPrivateSpace(privateProfileUserId, 300000L);
            }
        }
    }

    public final void onBeforeStartUser(int i) {
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return;
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("onBeforeStartUser-" + i);
        boolean equals = PackagePartitions.FINGERPRINT.equals(userInfo.lastLoggedInFingerprint) ^ true;
        timingsTraceAndSlog.traceBegin("prepareUserData");
        this.mUserDataPreparer.prepareUserData(1, userInfo);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("reconcileAppsData");
        if (this.mPmInternal == null) {
            this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        this.mPmInternal.reconcileAppsData(i, 1, equals);
        timingsTraceAndSlog.traceEnd();
        if (i != 0) {
            timingsTraceAndSlog.traceBegin("applyUserRestrictions");
            synchronized (this.mRestrictionsLock) {
                updateUserRestrictionsInternalLR(i, null);
                scheduleWriteUser(i);
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void onBeforeUnlockUser(int i) {
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return;
        }
        boolean z = !PackagePartitions.FINGERPRINT.equals(userInfo.lastLoggedInFingerprint);
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("prepareUserData-" + i);
        this.mUserDataPreparer.prepareUserData(2, userInfo);
        timingsTraceAndSlog.traceEnd();
        if (userInfo.isManagedProfile() && (userInfo.flags & 100663296) > 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Apply policies on CE storage for dualdar user ", "UserManagerService");
            if (!DualDARController.getInstance(this.mContext).handleBeforeUnlockUser(i)) {
                Log.e("UserManagerService", "To set DualDAR Policy on CE storage was failed.");
                synchronized (this.mPackagesLock) {
                    try {
                        UserData userDataNoChecks = getUserDataNoChecks(i);
                        if (userDataNoChecks == null) {
                            Log.e("UserManagerService", "Failed to remove invalid enterprise user - Couldn't get UserData");
                            return;
                        }
                        UserInfo userInfo2 = userDataNoChecks.info;
                        userInfo2.partial = true;
                        userInfo2.flags |= 64;
                        writeUserLP(userDataNoChecks);
                        removeUser(i);
                        return;
                    } finally {
                    }
                }
            }
        }
        ((StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class)).markCeStoragePrepared(i);
        timingsTraceAndSlog.traceBegin("reconcileAppsData-" + i);
        if (this.mPmInternal == null) {
            this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        this.mPmInternal.reconcileAppsData(i, 2, z);
        timingsTraceAndSlog.traceEnd();
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new UserManagerServiceShellCommand(this, this.mSystemPackageInstaller, this.mLockPatternUtils, this.mContext).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onUserLoggedIn(int i) {
        UserData userDataNoChecks = getUserDataNoChecks(i);
        if (userDataNoChecks == null || userDataNoChecks.info.partial) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "userForeground: unknown user #", "UserManagerService");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > 946080000000L) {
            userDataNoChecks.info.lastLoggedInTime = currentTimeMillis;
        }
        userDataNoChecks.info.lastLoggedInFingerprint = PackagePartitions.FINGERPRINT;
        scheduleWriteUser(i);
    }

    public final UserInfo preCreateUserWithThrow(String str) {
        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(str);
        boolean z = false;
        int defaultUserInfoFlags = userTypeDetails != null ? userTypeDetails.getDefaultUserInfoFlags() : 0;
        checkCreateUsersPermission(defaultUserInfoFlags);
        if (userTypeDetails != null && !userTypeDetails.isProfile() && !userTypeDetails.mName.equals("android.os.usertype.full.RESTRICTED")) {
            z = true;
        }
        Preconditions.checkArgument(z, "cannot pre-create user of type " + str);
        Slog.i("UserManagerService", "Pre-creating user of type " + str);
        try {
            return createUserInternalUnchecked(null, str, defaultUserInfoFlags, -10000, true, null, null);
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public UserData putUserInfo(UserInfo userInfo) {
        UserData userData = new UserData();
        userData.info = userInfo;
        synchronized (this.mUsersLock) {
            this.mUsers.put(userInfo.id, userData);
        }
        updateUserIds();
        return userData;
    }

    public final UserData readUserLP(int i, int i2) {
        FileInputStream openRead;
        ResilientAtomicFile userFile = getUserFile(i);
        FileInputStream fileInputStream = null;
        try {
            try {
                openRead = userFile.openRead();
            } catch (Exception e) {
                e = e;
            }
            try {
                if (openRead != null) {
                    UserData readUserLP = readUserLP(i, openRead, i2);
                    userFile.close();
                    return readUserLP;
                }
                Slog.e("UserManagerService", "User info not found, returning null, user id: " + i);
                userFile.close();
                return null;
            } catch (Exception e2) {
                e = e2;
                fileInputStream = openRead;
                Slog.e("UserManagerService", "Error reading user info, user id: " + i);
                userFile.failRead(fileInputStream, e);
                UserData readUserLP2 = readUserLP(i, i2);
                userFile.close();
                return readUserLP2;
            }
        } catch (Throwable th) {
            try {
                userFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0126, code lost:
    
        r43 = r5;
        r44 = r6;
        r45 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0306, code lost:
    
        r1 = r11;
        r27 = r12;
        r4 = r15;
        r49 = r21;
        r47 = r23;
        r8 = r2;
        r15 = r2;
        r0 = r2;
        r46 = r29;
        r3 = r31;
        r21 = r32;
        r59 = r35;
        r6 = r36;
        r5 = r37;
        r2 = r38;
        r25 = r41;
        r20 = r43;
        r22 = r44;
        r7 = r45;
        r23 = r10;
        r24 = r13;
        r10 = r16;
        r17 = r33;
        r16 = r34;
        r18 = r14;
        r13 = r18;
        r19 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.server.pm.UserManagerService.UserData readUserLP(int r57, java.io.InputStream r58, int r59) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 1059
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.readUserLP(int, java.io.InputStream, int):com.android.server.pm.UserManagerService$UserData");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0104, code lost:
    
        if (r3.getName().equals("restrictions") == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0106, code lost:
    
        r7 = r13.mGuestRestrictions;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0108, code lost:
    
        monitor-enter(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0109, code lost:
    
        com.android.server.pm.UserRestrictionsUtils.readRestrictions(r3, r13.mGuestRestrictions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
    
        monitor-exit(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readUserListLP() {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.readUserListLP():void");
    }

    public final void reconcileUsers(String str) {
        UserDataPreparer userDataPreparer = this.mUserDataPreparer;
        List users = getUsers(true, true, false);
        userDataPreparer.getClass();
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, FileUtils.listFilesOrEmpty(Environment.getDataUserDeDirectory(str)));
        Collections.addAll(arrayList, FileUtils.listFilesOrEmpty(Environment.getDataUserCeDirectory(str)));
        Collections.addAll(arrayList, FileUtils.listFilesOrEmpty(Environment.getDataSystemDeDirectory()));
        Collections.addAll(arrayList, FileUtils.listFilesOrEmpty(Environment.getDataSystemCeDirectory()));
        Collections.addAll(arrayList, FileUtils.listFilesOrEmpty(Environment.getDataMiscCeDirectory()));
        userDataPreparer.reconcileUsers(str, users, arrayList);
    }

    public final boolean removeUser(int i) {
        UserData userData;
        Slog.i("UserManagerService", "removeUser u" + i);
        checkCreateUsersPermission("Only the system can remove users");
        if (i == 77) {
            synchronized (this.mUsersLock) {
                userData = (UserData) this.mUsers.get(i);
            }
            if (userData != null && MaintenanceModeUtils.isMaintenanceModeUser(userData.info)) {
                final MaintenanceModeManager maintenanceModeManager = this.mPm.mCustomInjector.getMaintenanceModeManager();
                maintenanceModeManager.getClass();
                int callingUid = Binder.getCallingUid();
                if (!UserHandle.isSameApp(callingUid, 1000)) {
                    return false;
                }
                maintenanceModeManager.mHandler.post(new MaintenanceModeManager$$ExternalSyntheticLambda8(Binder.getCallingPid(), callingUid));
                markMaintenanceModeUserForDeletion(userData);
                maintenanceModeManager.notifyOtherPackages("com.samsung.android.intent.action.NOTIFY_POSTPROCESSING_MAINTENANCE_MODE", "com.samsung.android.intent.action.RESPONSE_POSTPROCESSING_MAINTENANCE_MODE");
                maintenanceModeManager.waitForOtherPackages();
                try {
                    FileUtils.deleteContentsAndDir(MaintenanceModeManager.LOG_DIR);
                } catch (Exception unused) {
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).removeUser(77);
                    } catch (Exception e) {
                        Log.i("MaintenanceMode", "Unable to notify AppOpsService of removing user.", e);
                    }
                    CompletableFuture.runAsync(new MaintenanceModeManager$$ExternalSyntheticLambda3(1, maintenanceModeManager)).orTimeout(60000L, TimeUnit.MILLISECONDS).whenCompleteAsync(new BiConsumer() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda5
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            MaintenanceModeManager maintenanceModeManager2 = MaintenanceModeManager.this;
                            Throwable th = (Throwable) obj2;
                            if (th != null) {
                                maintenanceModeManager2.getClass();
                                maintenanceModeManager2.logDebugInfoAsync("Got exception: " + th.toString());
                            }
                            MaintenanceModeManager$$ExternalSyntheticLambda3 maintenanceModeManager$$ExternalSyntheticLambda3 = maintenanceModeManager2.mExitRunnable;
                            Handler handler = maintenanceModeManager2.mHandler;
                            handler.removeCallbacks(maintenanceModeManager$$ExternalSyntheticLambda3);
                            handler.post(maintenanceModeManager2.mExitRunnable);
                        }
                    });
                    maintenanceModeManager.mHandler.postDelayed(maintenanceModeManager.mExitRunnable, 65000L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        String userRemovalRestriction = getUserRemovalRestriction(i);
        if (!getUserRestrictions(UserHandle.getCallingUserId()).getBoolean(userRemovalRestriction, false)) {
            return removeUserWithProfilesUnchecked(i);
        }
        PinnerService$$ExternalSyntheticOutline0.m("Cannot remove user. ", userRemovalRestriction, " is enabled.", "UserManagerService");
        return false;
    }

    public final boolean removeUserEvenWhenDisallowed(int i) {
        checkCreateUsersPermission("Only the system can remove users");
        return removeUserWithProfilesUnchecked(i);
    }

    public void removeUserInfo(int i) {
        synchronized (this.mUsersLock) {
            this.mUsers.remove(i);
        }
    }

    public final void removeUserState(int i) {
        UserData userData;
        HermesService$3$$ExternalSyntheticOutline0.m(i, "Removing user state of user ", "UserManagerService");
        this.mLockPatternUtils.removeUser(i);
        try {
            ((StorageManager) this.mContext.getSystemService(StorageManager.class)).destroyUserStorageKeys(i);
        } catch (IllegalStateException e) {
            Slog.i("UserManagerService", "Destroying storage keys for user " + i + " failed, continuing anyway", e);
        }
        this.mPm.cleanUpUser(this, i);
        UserDataPreparer userDataPreparer = this.mUserDataPreparer;
        PackageManagerTracedLock packageManagerTracedLock = userDataPreparer.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            Iterator it = ((StorageManager) userDataPreparer.mContext.getSystemService(StorageManager.class)).getWritablePrivateVolumes().iterator();
            while (it.hasNext()) {
                String fsUuid = ((VolumeInfo) it.next()).getFsUuid();
                if (fsUuid != null) {
                    userDataPreparer.destroyUserDataLI(i, 3, fsUuid);
                }
            }
            userDataPreparer.destroyUserDataLI(i, 3, null);
            packageManagerTracedLock.close();
            synchronized (this.mUsersLock) {
                userData = (UserData) this.mUsers.get(i);
            }
            UserInfo userInfo = userData != null ? userData.info : null;
            if (userInfo != null && (userInfo.flags & 100663296) != 0) {
                DualDARController.getInstance(this.mContext).onUserRemoved(i);
            }
            if (userInfo != null && SemPersonaManager.getUCMDAREncryption()) {
                try {
                    int removeODESettingsForWPC = IUniversalCredentialManager.Stub.asInterface(ServiceManager.getService("knox_ucsm_policy")).removeODESettingsForWPC();
                    if (removeODESettingsForWPC != 0) {
                        Log.e("UserManagerService", " UniversalCredentialManager: removeODESettingsForWPC failed with code " + removeODESettingsForWPC);
                    }
                } catch (Exception e2) {
                    RCPManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder(" disableUcmWpc "), "UserManagerService");
                }
            }
            synchronized (this.mUsersLock) {
                this.mUsers.remove(i);
                this.mIsUserManaged.delete(i);
            }
            synchronized (this.mUserStates) {
                this.mUserStates.states.delete(i);
                UserManager.invalidateIsUserUnlockedCache();
            }
            synchronized (this.mRestrictionsLock) {
                RestrictionsSet restrictionsSet = this.mBaseUserRestrictions;
                restrictionsSet.mUserRestrictions.contains(i);
                restrictionsSet.mUserRestrictions.remove(i);
                RestrictionsSet restrictionsSet2 = this.mAppliedUserRestrictions;
                restrictionsSet2.mUserRestrictions.contains(i);
                restrictionsSet2.mUserRestrictions.remove(i);
                RestrictionsSet restrictionsSet3 = this.mCachedEffectiveUserRestrictions;
                restrictionsSet3.mUserRestrictions.contains(i);
                restrictionsSet3.mUserRestrictions.remove(i);
                RestrictionsSet restrictionsSet4 = this.mDevicePolicyUserRestrictions;
                boolean contains = restrictionsSet4.mUserRestrictions.contains(i);
                restrictionsSet4.mUserRestrictions.remove(i);
                if (contains) {
                    this.mCachedEffectiveUserRestrictions.mUserRestrictions.clear();
                    this.mHandler.post(new AnonymousClass5());
                }
            }
            synchronized (this.mPackagesLock) {
                writeUserListLP();
            }
            ResilientAtomicFile userFile = getUserFile(i);
            userFile.mFile.delete();
            userFile.mTemporaryBackup.delete();
            userFile.mReserveCopy.delete();
            updateUserIds();
            if (userInfo == null || !userInfo.isDualAppProfile()) {
                return;
            }
            synchronized (this.mUsers) {
                if (i == 99) {
                    for (int i2 = 95; i2 <= 99; i2++) {
                        try {
                            this.mRemovingUserIds.delete(i2);
                        } finally {
                        }
                    }
                }
            }
        } finally {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        }
    }

    public final boolean removeUserUnchecked(int i) {
        PersonaManagerService personaManagerService = this.sPersonaManager;
        if (personaManagerService != null) {
            personaManagerService.onUserRemoved(i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mPackagesLock) {
                synchronized (this.mUsersLock) {
                    int userRemovabilityLocked = getUserRemovabilityLocked(i, "removed");
                    if (userRemovabilityLocked != 3) {
                        return UserManager.isRemoveResultSuccessful(userRemovabilityLocked);
                    }
                    final UserData userData = (UserData) this.mUsers.get(i);
                    if (!userData.info.isManagedProfile() && !userData.info.isCloneProfile() && !userData.info.isUserTypeAppSeparation() && (!MultiUserManager.getInstance(this.mContext).isUserRemovalAllowed(true) || !MultiUserManager.getInstance(this.mContext).multipleUsersAllowed(true))) {
                        return false;
                    }
                    Slog.i("UserManagerService", "Removing user " + i);
                    addRemovingUserIdLocked(i);
                    UserInfo userInfo = userData.info;
                    userInfo.partial = true;
                    userInfo.flags |= 64;
                    writeUserLP(userData);
                    this.mUserJourneyLogger.logUserJourneyBegin(i, 6);
                    UserJourneyLogger userJourneyLogger = this.mUserJourneyLogger;
                    long j = userData.info.creationTime;
                    userJourneyLogger.getClass();
                    long nextLong = ThreadLocalRandom.current().nextLong(1L, Long.MAX_VALUE);
                    synchronized (userJourneyLogger.mLock) {
                        userJourneyLogger.mUserIdToUserJourneyMap.append(UserJourneyLogger.getUserJourneyKey(i, 9), new UserJourneyLogger.UserJourneySession(nextLong, 9, j));
                    }
                    try {
                        this.mAppOpsService.removeUser(i);
                    } catch (RemoteException e) {
                        Slog.w("UserManagerService", "Unable to notify AppOpsService of removing user.", e);
                    }
                    UserInfo userInfo2 = userData.info;
                    if (userInfo2.profileGroupId != -10000 && userInfo2.isProfile()) {
                        UserInfo userInfo3 = userData.info;
                        sendProfileRemovedBroadcast(userInfo3.profileGroupId, userInfo3.id, userInfo3.userType);
                        try {
                            if (SemPersonaManager.isSecureFolderId(i)) {
                                ApplicationPolicy applicationPolicy = EnterpriseDeviceManager.getInstance(this.mContext).getApplicationPolicy();
                                applicationPolicy.changeApplicationIcon("com.samsung.knox.securefolder", (byte[]) null);
                                applicationPolicy.changeApplicationName("com.samsung.knox.securefolder", (String) null);
                            }
                        } catch (Exception e2) {
                            RCPManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), "UserManagerService");
                        }
                        Slog.i("UserManagerService", "resetDefaultIconandName");
                    }
                    if (PMRune.UM_BMODE && userData.info.isBMode()) {
                        synchronized (this.mUsersLock) {
                            if (getAliveUsersExcludingGuestsCountLU() == 1) {
                                int configMaxMultiUsers = MultiUserSupportsHelper.getConfigMaxMultiUsers();
                                SystemProperties.set("persist.sys.show_multiuserui", Boolean.toString(MultiUserSupportsHelper.getConfigStatusMultiUser()));
                                SystemProperties.set("persist.sys.max_users", Integer.toString(configMaxMultiUsers));
                                Slog.d("BmodeMigrationUtils", "Disabling multi user due to BMODE");
                            }
                        }
                    }
                    try {
                        return ActivityManager.getService().stopUserWithCallback(i, new IStopUserCallback.Stub() { // from class: com.android.server.pm.UserManagerService.6
                            public final void userStopAborted(int i2) {
                                int currentUserId = UserManagerService.this.getCurrentUserId();
                                UserManagerService.this.mUserJourneyLogger.logUserJourneyFinishWithError(currentUserId, userData.info, 6, 3);
                                UserManagerService.this.mUserJourneyLogger.logDelayedUserJourneyFinishWithError(currentUserId, userData.info, 3);
                            }

                            public final void userStopped(int i2) {
                                UserInfo userInfoLU;
                                UserManagerService userManagerService = UserManagerService.this;
                                userManagerService.getClass();
                                Slog.i("UserManagerService", "finishRemoveUser " + i2);
                                synchronized (userManagerService.mUsersLock) {
                                    userInfoLU = userManagerService.getUserInfoLU(i2);
                                }
                                if (userInfoLU == null || !userInfoLU.preCreated) {
                                    synchronized (userManagerService.mUserLifecycleListeners) {
                                        for (int i3 = 0; i3 < userManagerService.mUserLifecycleListeners.size(); i3++) {
                                            try {
                                                ((UserManagerInternal.UserLifecycleListener) userManagerService.mUserLifecycleListeners.get(i3)).onUserRemoved(userInfoLU);
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                    }
                                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                                    try {
                                        Intent intent = new Intent("android.intent.action.USER_REMOVED");
                                        intent.addFlags(16777216);
                                        intent.putExtra("android.intent.extra.user_handle", i2);
                                        intent.putExtra("android.intent.extra.USER", UserHandle.of(i2));
                                        userManagerService.getActivityManagerInternal().broadcastIntentWithCallback(intent, userManagerService.new AnonymousClass7(i2), new String[]{"android.permission.MANAGE_USERS"}, -1, (int[]) null, (BiFunction) null, (Bundle) null);
                                    } finally {
                                        Binder.restoreCallingIdentity(clearCallingIdentity2);
                                    }
                                } else {
                                    Slog.i("UserManagerService", "Removing a pre-created user with user id: " + i2);
                                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).onUserStopped(i2);
                                    userManagerService.removeUserState(i2);
                                }
                                int currentUserId = UserManagerService.this.getCurrentUserId();
                                UserManagerService.this.mUserJourneyLogger.logUserJourneyFinishWithError(currentUserId, userData.info, 6, -1);
                                UserManagerService.this.mUserJourneyLogger.logDelayedUserJourneyFinishWithError(currentUserId, userData.info, -1);
                            }
                        }) == 0;
                    } catch (RemoteException e3) {
                        Slog.w("UserManagerService", "Failed to stop user during removal.", e3);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int removeUserWhenPossible(int i, boolean z) {
        Slog.i("UserManagerService", "removeUserWhenPossible u" + i);
        checkCreateUsersPermission("Only the system can remove users");
        if (!z) {
            String userRemovalRestriction = getUserRemovalRestriction(i);
            if (getUserRestrictions(UserHandle.getCallingUserId()).getBoolean(userRemovalRestriction, false)) {
                PinnerService$$ExternalSyntheticOutline0.m("Cannot remove user. ", userRemovalRestriction, " is enabled.", "UserManagerService");
                return -2;
            }
        }
        Slog.i("UserManagerService", "Attempting to immediately remove user " + i);
        if (removeUserWithProfilesUnchecked(i)) {
            return 0;
        }
        Slog.i("UserManagerService", TextUtils.formatSimple("Unable to immediately remove user %d. Now trying to set it ephemeral.", new Object[]{Integer.valueOf(i)}));
        return setUserEphemeralUnchecked(i);
    }

    public final boolean removeUserWithProfilesUnchecked(int i) {
        synchronized (this.mUsersLock) {
            try {
                int userRemovabilityLocked = getUserRemovabilityLocked(i, "removed");
                if (userRemovabilityLocked != 3) {
                    return UserManager.isRemoveResultSuccessful(userRemovabilityLocked);
                }
                boolean isProfile = ((UserData) this.mUsers.get(i)).info.isProfile();
                IntArray intArray = null;
                if (!isProfile) {
                    intArray = getProfileIdsLU(null, false, false, i);
                }
                if (!isProfile) {
                    Pair currentAndTargetUserIds = getCurrentAndTargetUserIds();
                    if (i == ((Integer) currentAndTargetUserIds.first).intValue()) {
                        Slog.w("UserManagerService", "Current user cannot be removed.");
                        return false;
                    }
                    if (i == ((Integer) currentAndTargetUserIds.second).intValue()) {
                        Slog.w("UserManagerService", "Target user of an ongoing user switch cannot be removed.");
                        return false;
                    }
                    for (int size = intArray.size() - 1; size >= 0; size--) {
                        int i2 = intArray.get(size);
                        if (i2 != i) {
                            Slog.i("UserManagerService", "removing profile:" + i2 + " associated with user:" + i);
                            if (removeUserUnchecked(i2)) {
                                continue;
                            } else {
                                Slog.i("UserManagerService", DualAppManagerService$$ExternalSyntheticOutline0.m(i2, i, "Unable to immediately remove profile ", "associated with user ", ". User is set as ephemeral and will be removed on user switch or reboot."));
                                synchronized (this.mPackagesLock) {
                                    UserData userDataNoChecks = getUserDataNoChecks(i);
                                    userDataNoChecks.info.flags |= 256;
                                    writeUserLP(userDataNoChecks);
                                }
                            }
                        }
                    }
                }
                return removeUserUnchecked(i);
            } finally {
            }
        }
    }

    public final boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender, int i2) {
        UserInfo userInfo;
        UserProperties userPropertiesInternal;
        ShortcutServiceInternal shortcutServiceInternal;
        Objects.requireNonNull(str);
        if (z && intentSender != null) {
            throw new IllegalArgumentException("target should only be specified when we are disabling quiet mode.");
        }
        int i3 = SemPersonaManager.getUCMDAREncryption() ? i2 | 2 : i2;
        boolean z2 = (i3 & 2) != 0;
        boolean z3 = (i3 & 1) != 0;
        if (z2 && z3) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "invalid flags: "));
        }
        int callingUid = Binder.getCallingUid();
        boolean z4 = intentSender != null;
        if (this.mPm.snapshotComputer().getPackageUid(str, 0L, UserHandle.getUserId(callingUid)) != callingUid) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Specified package ", str, " does not match the calling uid "));
        }
        if (!hasManageUsersPermission()) {
            if (z4) {
                throw new SecurityException("MANAGE_USERS permission is required to start intent after disabling quiet mode.");
            }
            if (z2) {
                throw new SecurityException("MANAGE_USERS permission is required to disable quiet mode without credentials.");
            }
            if (!isSameProfileGroupNoChecks(UserHandle.getUserId(callingUid), i)) {
                throw new SecurityException("MANAGE_USERS permission is required to modify quiet mode for a different profile group.");
            }
            if (!hasPermissionGranted(callingUid, "android.permission.MODIFY_QUIET_MODE") && ((shortcutServiceInternal = (ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class)) == null || !shortcutServiceInternal.isForegroundDefaultLauncher(str, callingUid))) {
                throw new SecurityException("Can't modify quiet mode, caller is neither foreground default launcher nor has MANAGE_USERS/MODIFY_QUIET_MODE permission");
            }
        }
        if (z3) {
            if (this.mPmInternal == null) {
                this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            }
            if (str.equals(this.mPmInternal.getSystemUiServiceComponent().getPackageName())) {
                throw new SecurityException("SystemUI is not allowed to set QUIET_MODE_DISABLE_ONLY_IF_CREDENTIAL_NOT_REQUIRED");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z2) {
            try {
                synchronized (this.mUsersLock) {
                    userInfo = getUserInfo(i);
                }
                if (userInfo == null) {
                    throw new IllegalArgumentException("Invalid user. Can't find user details for userId " + i);
                }
                if (!userInfo.isManagedProfile()) {
                    throw new IllegalArgumentException("Invalid flags: " + i3 + ". Can't skip credential check for the user");
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (z) {
            setQuietModeEnabled(i, true, intentSender, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && (userPropertiesInternal = getUserPropertiesInternal(i)) != null && userPropertiesInternal.isAuthAlwaysRequiredToDisableQuietMode()) {
            if (z3) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
            int profileParentId = getProfileParentId(i);
            if (keyguardManager != null && keyguardManager.isDeviceSecure(profileParentId)) {
                showConfirmCredentialToDisableQuietMode(i, intentSender, str);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if (keyguardManager != null && !keyguardManager.isDeviceSecure(profileParentId) && android.multiuser.Flags.showSetScreenLockDialog() && Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, i) == 1) {
                Intent createBaseIntent = SetScreenLockDialogActivity.createBaseIntent(1);
                createBaseIntent.putExtra("origin_user_id", i);
                this.mContext.startActivityAsUser(createBaseIntent, UserHandle.of(profileParentId));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            Slog.w("UserManagerService", "Allowing profile unlock even when device credentials are not set for user " + i);
        }
        boolean isManagedProfileWithUnifiedChallenge = this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(i);
        if (isManagedProfileWithUnifiedChallenge && (!((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(UserManagerService.this.getProfileParentIdUnchecked(i)) || z3)) {
            this.mLockPatternUtils.tryUnlockWithCachedUnifiedChallenge(i);
        }
        if (z2 || !this.mLockPatternUtils.isSecure(i) || (isManagedProfileWithUnifiedChallenge && StorageManager.isCeStorageUnlocked(i))) {
            setQuietModeEnabled(i, false, intentSender, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        if (z3) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        showConfirmCredentialToDisableQuietMode(i, intentSender, str);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    public final void revokeUserAdmin(int i) {
        checkManageUserAndAcrossUsersFullPermission("revoke admin privileges");
        this.mUserJourneyLogger.logUserJourneyBegin(i, 8);
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    this.mUserJourneyLogger.logNullUserJourneyError(8, getCurrentUserId(), i, -1, "");
                    return;
                }
                if (!userDataLU.info.isAdmin()) {
                    this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 8, 6);
                    return;
                }
                userDataLU.info.flags ^= 2;
                writeUserLP(userDataLU);
                this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 8, -1);
            }
        }
    }

    public final int scanNextAvailableIdLocked(boolean z, boolean z2) {
        int i = z ? 150 : 10;
        if (z2) {
            i = 95;
        }
        while (i < MAX_USER_ID) {
            if (i != 77) {
                if (z && i > 160) {
                    return -1;
                }
                if (z2) {
                    if (!SemDualAppManager.isDualAppId(i)) {
                        return -1;
                    }
                } else if (SemDualAppManager.isDualAppId(i)) {
                    continue;
                }
                if (this.mUsers.indexOfKey(i) < 0 && !this.mRemovingUserIds.get(i)) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public void scheduleAlarmToAutoLockPrivateSpace(int i, long j) {
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        if (alarmManager == null) {
            Slog.e("UserManagerService", "AlarmManager not available, cannot schedule auto-lock alarm");
            return;
        }
        cancelPendingAutoLockAlarms();
        PrivateSpaceAutoLockTimer privateSpaceAutoLockTimer = this.mPrivateSpaceAutoLockTimer;
        if (privateSpaceAutoLockTimer == null || privateSpaceAutoLockTimer.mUserId != i) {
            this.mPrivateSpaceAutoLockTimer = new PrivateSpaceAutoLockTimer(i);
        }
        alarmManager.setWindow(2, SystemClock.elapsedRealtime() + j, PRIVATE_SPACE_AUTO_LOCK_INACTIVITY_ALARM_WINDOW_MS, "PrivateSpaceAutoLockTimer", (Executor) new HandlerExecutor(this.mHandler), (AlarmManager.OnAlarmListener) this.mPrivateSpaceAutoLockTimer);
    }

    public final void scheduleWriteUser(int i) {
        if (this.mHandler.hasMessages(1, Integer.valueOf(i))) {
            return;
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, Integer.valueOf(i)), 2000L);
    }

    public final void sendProfileRemovedBroadcast(int i, int i2, String str) {
        if (Objects.equals(str, "android.os.usertype.profile.MANAGED")) {
            Intent intent = new Intent(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
            intent.putExtra("android.intent.extra.USER", UserHandle.of(i2));
            intent.putExtra("android.intent.extra.user_handle", i2);
            UserHandle of = UserHandle.of(i);
            if (this.mDevicePolicyManagerInternal == null) {
                this.mDevicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
            }
            this.mDevicePolicyManagerInternal.broadcastIntentToManifestReceivers(intent, of, false);
            intent.addFlags(1342177280);
            this.mContext.sendBroadcastAsUser(intent, of, null);
        }
        Intent intent2 = new Intent("android.intent.action.PROFILE_REMOVED");
        UserHandle of2 = UserHandle.of(i);
        intent2.putExtra("android.intent.extra.USER", UserHandle.of(i2));
        intent2.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent2, of2, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b9, code lost:
    
        if (r0 == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0084, code lost:
    
        if (r7.mContext.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", r8) == 0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setApplicationRestrictions(java.lang.String r8, android.os.Bundle r9, int r10) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserManagerService.setApplicationRestrictions(java.lang.String, android.os.Bundle, int):void");
    }

    public final void setBootUser(int i) {
        checkCreateUsersPermission("Set boot user");
        synchronized (this.mUsersLock) {
            Slogf.i("UserManagerService", "setBootUser %d", Integer.valueOf(i));
            this.mBootUser = i;
        }
        this.mBootUserLatch.countDown();
    }

    public final void setContainerInfo() {
        int i;
        String str = "";
        synchronized (this.mUsersLock) {
            for (0; i < this.mUsers.size(); i + 1) {
                try {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i)).info;
                    i = (userInfo.isManagedProfile() || userInfo.isSecureFolder()) ? 0 : i + 1;
                    str = str + Integer.toString(userInfo.id) + "," + Integer.toString(userInfo.flags) + ":";
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
        }
        try {
            SystemProperties.set("persist.sys.knox.userinfo", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setDefaultCrossProfileIntentFilters(int i, UserTypeDetails userTypeDetails, Bundle bundle, int i2) {
        if (userTypeDetails == null || !userTypeDetails.isProfile()) {
            return;
        }
        if ((userTypeDetails.mDefaultCrossProfileIntentFilters != null ? new ArrayList(userTypeDetails.mDefaultCrossProfileIntentFilters) : Collections.emptyList()).isEmpty()) {
            return;
        }
        boolean z = bundle.getBoolean("no_sharing_into_profile", false);
        int size = (userTypeDetails.mDefaultCrossProfileIntentFilters != null ? new ArrayList(userTypeDetails.mDefaultCrossProfileIntentFilters) : Collections.emptyList()).size();
        for (int i3 = 0; i3 < size; i3++) {
            DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter = (DefaultCrossProfileIntentFilter) (userTypeDetails.mDefaultCrossProfileIntentFilters != null ? new ArrayList(userTypeDetails.mDefaultCrossProfileIntentFilters) : Collections.emptyList()).get(i3);
            if (!z || !defaultCrossProfileIntentFilter.letsPersonalDataIntoProfile) {
                if (defaultCrossProfileIntentFilter.direction == 0) {
                    PackageManagerService packageManagerService = this.mPm;
                    packageManagerService.addCrossProfileIntentFilter(packageManagerService.snapshotComputer(), defaultCrossProfileIntentFilter.filter, this.mContext.getOpPackageName(), i, i2, defaultCrossProfileIntentFilter.flags);
                } else {
                    PackageManagerService packageManagerService2 = this.mPm;
                    packageManagerService2.addCrossProfileIntentFilter(packageManagerService2.snapshotComputer(), defaultCrossProfileIntentFilter.filter, this.mContext.getOpPackageName(), i2, i, defaultCrossProfileIntentFilter.flags);
                }
            }
        }
    }

    public final void setDefaultGuestRestrictions(Bundle bundle) {
        checkManageUsersPermission("setDefaultGuestRestrictions");
        List guestUsers = getGuestUsers();
        synchronized (this.mRestrictionsLock) {
            int i = 0;
            while (true) {
                try {
                    ArrayList arrayList = (ArrayList) guestUsers;
                    if (i < arrayList.size()) {
                        updateUserRestrictionsInternalLR(((UserInfo) arrayList.get(i)).id, bundle);
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        synchronized (this.mGuestRestrictions) {
            this.mGuestRestrictions.clear();
            this.mGuestRestrictions.putAll(bundle);
        }
        synchronized (this.mPackagesLock) {
            writeUserListLP();
        }
    }

    public void setOrUpdateAutoLockPreferenceForPrivateProfile(int i) {
        if (getPrivateProfileUserId() == -10000) {
            Slog.e("UserManagerService", "Auto-lock preference updated but private space user not found");
            return;
        }
        if (i == 1) {
            if (!this.mIsDeviceInactivityBroadcastReceiverRegistered) {
                Slog.i("UserManagerService", "Registering device inactivity broadcast receivers");
                this.mContext.registerReceiver(this.mDeviceInactivityBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"), null, this.mHandler);
                this.mContext.registerReceiver(this.mDeviceInactivityBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_ON"), null, this.mHandler);
                this.mIsDeviceInactivityBroadcastReceiverRegistered = true;
            }
        } else if (this.mIsDeviceInactivityBroadcastReceiverRegistered) {
            Slog.i("UserManagerService", "Removing device inactivity broadcast receivers");
            cancelPendingAutoLockAlarms();
            this.mContext.unregisterReceiver(this.mDeviceInactivityBroadcastReceiver);
            this.mIsDeviceInactivityBroadcastReceiverRegistered = false;
        }
        if (i != 0) {
            try {
                KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
                Slog.i("UserManagerService", "Removing keyguard locked state listener");
                keyguardManager.removeKeyguardLockedStateListener(this.mKeyguardLockedStateListener);
                return;
            } catch (Exception e) {
                Slog.e("UserManagerService", "Error adding keyguard locked state listener ", e);
                return;
            }
        }
        this.mKeyguardLockedStateListener = new KeyguardManager.KeyguardLockedStateListener() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda3
            @Override // android.app.KeyguardManager.KeyguardLockedStateListener
            public final void onKeyguardLockedStateChanged(boolean z) {
                UserManagerService.this.tryAutoLockingPrivateSpaceOnKeyguardChanged(z);
            }
        };
        try {
            KeyguardManager keyguardManager2 = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
            Slog.i("UserManagerService", "Adding keyguard locked state listener");
            keyguardManager2.addKeyguardLockedStateListener(new HandlerExecutor(this.mHandler), this.mKeyguardLockedStateListener);
        } catch (Exception e2) {
            Slog.e("UserManagerService", "Error adding keyguard locked listener ", e2);
        }
    }

    public final void setQuietModeEnabled(int i, boolean z, IntentSender intentSender, String str) {
        UserData userDataLU;
        synchronized (this.mUsersLock) {
            try {
                UserInfo userInfoLU = getUserInfoLU(i);
                UserInfo profileParentLU = getProfileParentLU(i);
                if (userInfoLU == null || !userInfoLU.isProfile()) {
                    throw new IllegalArgumentException("User " + i + " is not a profile");
                }
                if (userInfoLU.isQuietModeEnabled() == z) {
                    Slog.i("UserManagerService", "Quiet mode is already " + z);
                    return;
                }
                userInfoLU.flags ^= 128;
                UserData userDataLU2 = getUserDataLU(userInfoLU.id);
                synchronized (this.mPackagesLock) {
                    writeUserLP(userDataLU2);
                }
                try {
                    if (z) {
                        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
                            ActivityManager.getService().stopUserWithDelayedLocking(i, (IStopUserCallback) null);
                        } else {
                            ActivityManager.getService().stopUserWithCallback(i, (IStopUserCallback) null);
                        }
                        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).killForegroundAppsForUser(i);
                    } else {
                        ActivityManager.getService().startProfileWithListener(i, intentSender != null ? new DisableQuietModeUserUnlockedCallback(intentSender) : null);
                    }
                } catch (RemoteException e) {
                    e.rethrowAsRuntimeException();
                }
                Slogf.i("UserManagerService", "requestQuietModeEnabled called by package %s, with enableQuietMode %b.", str, Boolean.valueOf(z));
                synchronized (this.mUsersLock) {
                    userDataLU = getUserDataLU(i);
                }
                if (userDataLU != null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = userDataLU.mLastRequestQuietModeEnabledMillis;
                    if (j == 0) {
                        j = userDataLU.info.creationTime;
                    }
                    DevicePolicyEventLogger.createEvent(55).setInt(UserJourneyLogger.getUserTypeForStatsd(userDataLU.info.userType)).setStrings(new String[]{str}).setBoolean(z).setTimePeriod(currentTimeMillis - j).write();
                    userDataLU.mLastRequestQuietModeEnabledMillis = currentTimeMillis;
                }
                if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
                    broadcastProfileAvailabilityChanges(userInfoLU, profileParentLU.getUserHandle(), z, false);
                }
                if (userInfoLU.isManagedProfile()) {
                    broadcastProfileAvailabilityChanges(userInfoLU, profileParentLU.getUserHandle(), z, true);
                }
            } finally {
            }
        }
    }

    public void setQuietModeEnabledAsync(final int i, final boolean z, final IntentSender intentSender, final String str) {
        if (!android.multiuser.Flags.moveQuietModeOperationsToSeparateThread()) {
            final int i2 = 1;
            BackgroundThread.getHandler().post(new Runnable(this) { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda5
                public final /* synthetic */ UserManagerService f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            this.f$0.setQuietModeEnabled(i, z, intentSender, str);
                            break;
                        default:
                            this.f$0.setQuietModeEnabled(i, z, intentSender, str);
                            break;
                    }
                }
            });
        } else {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Calling setQuietModeEnabled for user ", " on a separate thread", "UserManagerService");
            final int i3 = 0;
            this.mInternalExecutor.execute(new Runnable(this) { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda5
                public final /* synthetic */ UserManagerService f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            this.f$0.setQuietModeEnabled(i, z, intentSender, str);
                            break;
                        default:
                            this.f$0.setQuietModeEnabled(i, z, intentSender, str);
                            break;
                    }
                }
            });
        }
    }

    public final void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) {
        checkManageUsersPermission("set user seed account data");
        setSeedAccountDataNoChecks(i, str, str2, persistableBundle, z);
    }

    public final void setSeedAccountDataNoChecks(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) {
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    Slog.e("UserManagerService", "No such user for settings seed data u=" + i);
                    return;
                }
                userDataLU.seedAccountName = truncateString(500, str);
                userDataLU.seedAccountType = truncateString(500, str2);
                if (persistableBundle != null && persistableBundle.isBundleContentsWithinLengthLimit(1000)) {
                    userDataLU.seedAccountOptions = persistableBundle;
                }
                userDataLU.persistSeedData = z;
                if (z) {
                    writeUserLP(userDataLU);
                }
            }
        }
    }

    public final void setUserAccount(int i, String str) {
        checkManageUserAndAcrossUsersFullPermission("set user account");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userData = (UserData) this.mUsers.get(i);
                if (userData == null) {
                    Slog.e("UserManagerService", "User not found for setting user account: u" + i);
                } else {
                    if (Objects.equals(userData.account, str)) {
                        userData = null;
                    } else {
                        userData.account = str;
                    }
                    if (userData != null) {
                        writeUserLP(userData);
                    }
                }
            }
        }
    }

    public final void setUserAdmin(int i) {
        checkManageUserAndAcrossUsersFullPermission("set user admin");
        this.mUserJourneyLogger.logUserJourneyBegin(i, 7);
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    this.mUserJourneyLogger.logNullUserJourneyError(7, getCurrentUserId(), i, -1, "");
                    return;
                }
                if (userDataLU.info.isAdmin()) {
                    this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 7, 5);
                    return;
                }
                userDataLU.info.flags ^= 2;
                writeUserLP(userDataLU);
                this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 7, -1);
            }
        }
    }

    public final void setUserEnabled(int i) {
        UserInfo userInfoLU;
        boolean z;
        checkManageUsersPermission("enable user");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                try {
                    userInfoLU = getUserInfoLU(i);
                    if (userInfoLU == null || userInfoLU.isEnabled()) {
                        z = false;
                    } else {
                        userInfoLU.flags ^= 64;
                        writeUserLP(getUserDataLU(userInfoLU.id));
                        z = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (z && userInfoLU != null && userInfoLU.isProfile()) {
            int i2 = userInfoLU.profileGroupId;
            int i3 = userInfoLU.id;
            Intent intent = new Intent("android.intent.action.PROFILE_ADDED");
            UserHandle of = UserHandle.of(i2);
            intent.putExtra("android.intent.extra.USER", UserHandle.of(i3));
            intent.addFlags(1342177280);
            this.mContext.sendBroadcastAsUser(intent, of, null);
        }
    }

    public final boolean setUserEphemeral(int i, boolean z) {
        checkCreateUsersPermission("update ephemeral user flag");
        if (z) {
            return UserManager.isRemoveResultSuccessful(setUserEphemeralUnchecked(i));
        }
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                UserData userData = (UserData) this.mUsers.get(i);
                if (userData != null) {
                    if (userData.info.isEphemeral()) {
                        UserInfo userInfo = userData.info;
                        int i2 = userInfo.flags;
                        if ((i2 & 8192) != 0) {
                            Slog.e("UserManagerService", TextUtils.formatSimple("User %d can not be changed to non-ephemeral because it was set ephemeral on create.", new Object[]{Integer.valueOf(i)}));
                        } else {
                            userInfo.flags = i2 & (-257);
                            writeUserLP(userData);
                        }
                    }
                    return true;
                }
                Slog.e("UserManagerService", TextUtils.formatSimple("Cannot set user %d non-ephemeral, invalid user id provided.", new Object[]{Integer.valueOf(i)}));
                return false;
            }
        }
    }

    public final int setUserEphemeralUnchecked(int i) {
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                int userRemovabilityLocked = getUserRemovabilityLocked(i, "set as ephemeral");
                if (userRemovabilityLocked != 3) {
                    return userRemovabilityLocked;
                }
                UserData userData = (UserData) this.mUsers.get(i);
                userData.info.flags |= 256;
                writeUserLP(userData);
                Slog.i("UserManagerService", TextUtils.formatSimple("User %d is set ephemeral and will be removed on user switch or reboot.", new Object[]{Integer.valueOf(i)}));
                return 1;
            }
        }
    }

    public final void setUserIcon(int i, Bitmap bitmap) {
        try {
            checkManageUsersPermission("update users");
            enforceUserRestriction(i, "no_set_user_icon", "Cannot set user icon");
            this.mLocalService.setUserIcon(i, bitmap);
        } catch (UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    public final void setUserName(int i, String str) {
        String str2;
        checkManageUsersPermission("rename users");
        synchronized (this.mPackagesLock) {
            try {
                UserData userDataNoChecks = getUserDataNoChecks(i);
                if (userDataNoChecks != null) {
                    UserInfo userInfo = userDataNoChecks.info;
                    if (!userInfo.partial) {
                        if (Objects.equals(str, userInfo.name)) {
                            Integer valueOf = Integer.valueOf(i);
                            if (str == null) {
                                str2 = null;
                            } else {
                                str2 = str.length() + "_chars";
                            }
                            Slogf.i("UserManagerService", "setUserName: ignoring for user #%d as it didn't change (%s)", valueOf, str2);
                            String str3 = userDataNoChecks.info.name;
                            return;
                        }
                        if (str == null) {
                            Slogf.i("UserManagerService", "setUserName: resetting name of user #%d", Integer.valueOf(i));
                        } else {
                            Slogf.i("UserManagerService", "setUserName: setting name of user #%d to %s", Integer.valueOf(i), str.length() + "_chars");
                        }
                        userDataNoChecks.info.name = str;
                        writeUserLP(userDataNoChecks);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            Intent intent = new Intent("android.intent.action.USER_INFO_CHANGED");
                            intent.putExtra("android.intent.extra.user_handle", i);
                            intent.addFlags(1073741824);
                            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                            return;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                Slogf.w("UserManagerService", "setUserName: unknown user #%d", Integer.valueOf(i));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUserRestriction(final String str, final boolean z, final int i) {
        IRestrictionPolicy asInterface;
        checkManageUsersPermission("setUserRestriction");
        if (UserRestrictionsUtils.isValidRestriction(str)) {
            if (!userExists(i)) {
                Slogf.w("UserManagerService", "Cannot set user restriction %s. User with id %d does not exist", str, Integer.valueOf(i));
                return;
            }
            synchronized (this.mRestrictionsLock) {
                Bundle clone = BundleUtils.clone(this.mBaseUserRestrictions.getRestrictions(i));
                clone.putBoolean(str, z);
                updateUserRestrictionsInternalLR(i, clone);
                if (UserHandle.getUserId(Binder.getCallingUid()) == 0 && (asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"))) != null) {
                    try {
                        asInterface.updateUserRestrictionsByKC(str, z);
                    } catch (RemoteException e) {
                        Log.d("UserManagerService", "Failed talking with IRestrictionPolicy: ", e);
                    }
                }
            }
            if (i == 0) {
                final String callers = Debug.getCallers(1, 10);
                final int callingUid = Binder.getCallingUid();
                this.mHandler.post(new Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str2 = str;
                        boolean z2 = z;
                        int i2 = i;
                        int i3 = callingUid;
                        String str3 = callers;
                        StringBuilder sb = new StringBuilder("Updated restriction ");
                        sb.append(str2);
                        sb.append("(");
                        sb.append(z2);
                        sb.append(") for User ");
                        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ", callingUid: ", ", stack: ", sb);
                        sb.append(str3);
                        PmLog.logDebugInfo(sb.toString());
                    }
                });
            }
        }
    }

    public void setUserRestrictionInner(int i, String str, boolean z) {
        if (!UserRestrictionsUtils.isValidRestriction(str)) {
            BootReceiver$$ExternalSyntheticOutline0.m("Setting invalid restriction ", str, "UserManagerService");
            return;
        }
        synchronized (this.mRestrictionsLock) {
            Bundle clone = BundleUtils.clone(this.mDevicePolicyUserRestrictions.getRestrictions(i));
            clone.putBoolean(str, z);
            if (this.mDevicePolicyUserRestrictions.updateRestrictions(i, clone)) {
                if (i == -1) {
                    this.mCachedEffectiveUserRestrictions.mUserRestrictions.clear();
                    this.mHandler.post(new AnonymousClass5());
                } else {
                    updateUserRestrictionsInternalLR(i, null);
                    scheduleWriteUser(i);
                }
            }
        }
    }

    public final void showConfirmCredentialToDisableQuietMode(int i, IntentSender intentSender, String str) {
        int i2;
        if (android.app.admin.flags.Flags.quietModeCredentialBugFix() && (!android.multiuser.Flags.restrictQuietModeCredentialBugFixToManagedProfiles() || getUserInfo(i).isManagedProfile())) {
            synchronized (this.mUserStates) {
                i2 = this.mUserStates.get(i);
            }
            if (i2 != -1) {
                BootReceiver$$ExternalSyntheticOutline0.m(i, "showConfirmCredentialToDisableQuietMode() called too early, managed user ", " is still alive.", "UserManagerService");
                return;
            }
        }
        Intent createConfirmDeviceCredentialIntent = ((KeyguardManager) this.mContext.getSystemService("keyguard")).createConfirmDeviceCredentialIntent(null, null, i);
        if (createConfirmDeviceCredentialIntent == null) {
            return;
        }
        Intent intent = new Intent("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK");
        if (intentSender != null) {
            intent.putExtra("android.intent.extra.INTENT", intentSender);
        }
        intent.putExtra("android.intent.extra.USER_ID", i);
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.addFlags(268435456);
        createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", PendingIntent.getBroadcast(this.mContext, 0, intent, 1409286144).getIntentSender());
        createConfirmDeviceCredentialIntent.setFlags(276824064);
        if (android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.usePrivateSpaceIconInBiometricPrompt() && getUserInfo(i).isPrivateProfile()) {
            createConfirmDeviceCredentialIntent.putExtra("custom_logo_res_id", 17304493);
            createConfirmDeviceCredentialIntent.putExtra("custom_logo_description", this.mContext.getString(17042483));
        }
        this.mContext.startActivityAsUser(createConfirmDeviceCredentialIntent, UserHandle.of(getProfileParentIdUnchecked(i)));
    }

    public final boolean someUserHasAccount(String str, String str2) {
        checkCreateUsersPermission("check seed account information");
        return someUserHasAccountNoChecks(str, str2);
    }

    public final boolean someUserHasAccountNoChecks(final String str, final String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        final Account account = new Account(str, str2);
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda7
            public final Object getOrThrow() {
                UserManagerService userManagerService = UserManagerService.this;
                return Boolean.valueOf(AccountManager.get(userManagerService.mContext).someUserHasAccount(account) || userManagerService.someUserHasSeedAccountNoChecks(str, str2));
            }
        })).booleanValue();
    }

    public final boolean someUserHasSeedAccount(String str, String str2) {
        checkManageUsersPermission("check seed account information");
        return someUserHasSeedAccountNoChecks(str, str2);
    }

    public final boolean someUserHasSeedAccountNoChecks(String str, String str2) {
        String str3;
        String str4;
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    UserData userData = (UserData) this.mUsers.valueAt(i);
                    if (!userData.info.isInitialized() && !this.mRemovingUserIds.get(userData.info.id) && (str3 = userData.seedAccountName) != null && str3.equals(str) && (str4 = userData.seedAccountType) != null && str4.equals(str2)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void tryAutoLockingPrivateSpaceOnKeyguardChanged(boolean z) {
        if (isAutoLockForPrivateSpaceEnabled()) {
            boolean z2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "private_space_auto_lock", 2, getMainUserIdUnchecked()) == 0;
            if (z && z2) {
                autoLockPrivateSpace();
            }
        }
    }

    public final void updateUserIds() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    UserInfo userInfo = ((UserData) this.mUsers.valueAt(i3)).info;
                    if (!userInfo.partial) {
                        i2++;
                        if (!userInfo.preCreated) {
                            i++;
                        }
                    }
                }
                int[] iArr = new int[i];
                int[] iArr2 = new int[i2];
                int i4 = 0;
                int i5 = 0;
                for (int i6 = 0; i6 < size; i6++) {
                    UserInfo userInfo2 = ((UserData) this.mUsers.valueAt(i6)).info;
                    if (!userInfo2.partial) {
                        int keyAt = this.mUsers.keyAt(i6);
                        int i7 = i4 + 1;
                        iArr2[i4] = keyAt;
                        if (!userInfo2.preCreated) {
                            iArr[i5] = keyAt;
                            i5++;
                        }
                        i4 = i7;
                    }
                }
                this.mUserIds = iArr;
                this.mUserIdsIncludingPreCreated = iArr2;
                UserPackage.setValidUserIds(iArr);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean updateUserInfo(int i, Bundle bundle) {
        UserData userData;
        boolean z;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "updateUserInfo is called for user ", "UserManagerService");
        ContainerDependencyWrapper.checkCallerPermissionFor(sContext, "UserManagerService", "updateUserInfo");
        boolean z2 = false;
        if (getProfileParentLU(i) == null) {
            Log.d("UserManagerService", "updateUserInfoFlags userid is not knox workspace..");
        } else if (bundle != null) {
            synchronized (this.mUsersLock) {
                try {
                    userData = (UserData) this.mUsers.get(i);
                    if (userData != null) {
                        UserInfo userInfo = userData.info;
                        if (userInfo != null) {
                            int i2 = bundle.getInt("flags", 0);
                            bundle.getInt("fotaUpgradeVersion", 8);
                            int i3 = bundle.getInt("update-flags", 0);
                            if (i2 > 0) {
                                userInfo.flags = i2 | userInfo.flags;
                                Log.d("UserManagerService", "updateUserInfo flags is updated");
                                z = true;
                            } else {
                                z = false;
                            }
                            if (i3 > 0) {
                                userInfo.flags = i3;
                                Log.d("UserManagerService", "updateUserInfo flags is updated");
                                z = true;
                            }
                            int i4 = bundle.getInt("attributes", 0);
                            if (i4 > 0) {
                                userInfo.setAttributes(userInfo.getAttributes() | i4);
                                Log.d("UserManagerService", "updateUserInfo attributes is updated");
                                z = true;
                            }
                            String string = bundle.getString("name");
                            if (string != null && string.length() > 0) {
                                userInfo.name = string;
                                Log.d("UserManagerService", "updateUserInfo attributes is updated");
                                z = true;
                            }
                            Log.d("UserManagerService", "updateUserInfoFlags needUpdate - " + z);
                            if (z) {
                                this.mUsers.put(userData.info.id, userData);
                                Log.d("UserManagerService", "updateUserInfoFlags updated user cache");
                            }
                        } else {
                            Log.d("UserManagerService", "updateUserInfoFlags user info is null");
                        }
                    } else {
                        Log.d("UserManagerService", "updateUserInfoFlags user data is null");
                    }
                    z = false;
                } finally {
                }
            }
            if (z) {
                synchronized (this.mPackagesLock) {
                    writeUserLP(userData);
                    writeUserListLP();
                    Log.d("UserManagerService", "updateUserInfoFlags updated user xml");
                }
                z2 = true;
            }
        } else {
            Log.d("UserManagerService", "updateUserInfoFlags bundle data is null");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("updateUserInfoFlags status - ", "UserManagerService", z2);
        return z2;
    }

    public final void updateUserRestrictionsInternalLR(final int i, Bundle bundle) {
        Bundle restrictions = this.mAppliedUserRestrictions.getRestrictions(i);
        Set set = UserRestrictionsUtils.USER_RESTRICTIONS;
        if (restrictions == null) {
            restrictions = new Bundle();
        }
        if (bundle != null) {
            Preconditions.checkState(this.mBaseUserRestrictions.getRestrictions(i) != bundle);
            Preconditions.checkState(this.mCachedEffectiveUserRestrictions.getRestrictions(i) != bundle);
            if (this.mBaseUserRestrictions.updateRestrictions(i, new Bundle(bundle))) {
                scheduleWriteUser(i);
            }
        }
        final Bundle computeEffectiveUserRestrictionsLR = computeEffectiveUserRestrictionsLR(i);
        this.mCachedEffectiveUserRestrictions.updateRestrictions(i, new Bundle(computeEffectiveUserRestrictionsLR));
        if (this.mAppOpsService != null) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManagerService userManagerService = UserManagerService.this;
                    Bundle bundle2 = computeEffectiveUserRestrictionsLR;
                    int i2 = i;
                    userManagerService.getClass();
                    try {
                        userManagerService.mAppOpsService.setUserRestrictions(bundle2, userManagerService.mUserRestrictionToken, i2);
                    } catch (RemoteException unused) {
                        Slog.w("UserManagerService", "Unable to notify AppOpsService of UserRestrictions");
                    }
                }
            });
        }
        if (!UserRestrictionsUtils.areEqual(computeEffectiveUserRestrictionsLR, restrictions)) {
            final Bundle bundle2 = new Bundle(computeEffectiveUserRestrictionsLR);
            final Bundle bundle3 = new Bundle(restrictions);
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.UserManagerService.4
                @Override // java.lang.Runnable
                public final void run() {
                    int size;
                    UserManagerInternal.UserRestrictionsListener[] userRestrictionsListenerArr;
                    char c;
                    Context context = UserManagerService.this.mContext;
                    int i2 = i;
                    Bundle bundle4 = bundle2;
                    Bundle bundle5 = bundle3;
                    Iterator it = ((ArraySet) UserRestrictionsUtils.USER_RESTRICTIONS).iterator();
                    while (true) {
                        int i3 = 0;
                        if (!it.hasNext()) {
                            synchronized (UserManagerService.this.mUserRestrictionsListeners) {
                                size = UserManagerService.this.mUserRestrictionsListeners.size();
                                userRestrictionsListenerArr = new UserManagerInternal.UserRestrictionsListener[size];
                                UserManagerService.this.mUserRestrictionsListeners.toArray(userRestrictionsListenerArr);
                            }
                            while (i3 < size) {
                                userRestrictionsListenerArr[i3].onUserRestrictionsChanged(i, bundle2, bundle3);
                                i3++;
                            }
                            UserManagerService.this.mContext.sendBroadcastAsUser(new Intent("android.os.action.USER_RESTRICTIONS_CHANGED").setFlags(1073741824), UserHandle.of(i), null, BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).toBundle());
                            return;
                        }
                        String str = (String) it.next();
                        boolean z = bundle4.getBoolean(str);
                        if (z != bundle5.getBoolean(str)) {
                            ContentResolver contentResolver = context.getContentResolver();
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                switch (str.hashCode()) {
                                    case -1475388515:
                                        if (str.equals("no_ambient_display")) {
                                            c = '\t';
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1387500078:
                                        if (str.equals("no_control_apps")) {
                                            c = '\n';
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1315771401:
                                        if (str.equals("ensure_verify_apps")) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1145953970:
                                        if (str.equals("no_install_unknown_sources_globally")) {
                                            c = 4;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1082175374:
                                        if (str.equals("no_airplane_mode")) {
                                            c = '\b';
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -6578707:
                                        if (str.equals("no_uninstall_apps")) {
                                            c = 11;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 387189153:
                                        if (str.equals("no_install_unknown_sources")) {
                                            c = 5;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 721128150:
                                        if (str.equals("no_run_in_background")) {
                                            c = 6;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 928851522:
                                        if (str.equals("no_data_roaming")) {
                                            c = 0;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 995816019:
                                        if (str.equals("no_share_location")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1095593830:
                                        if (str.equals("no_safe_boot")) {
                                            c = 7;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1760762284:
                                        if (str.equals("no_debugging_features")) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    default:
                                        c = 65535;
                                        break;
                                }
                                switch (c) {
                                    case 0:
                                        if (z) {
                                            List<SubscriptionInfo> activeSubscriptionInfoList = ((SubscriptionManager) context.getSystemService(SubscriptionManager.class)).getActiveSubscriptionInfoList();
                                            if (activeSubscriptionInfoList != null) {
                                                Iterator<SubscriptionInfo> it2 = activeSubscriptionInfoList.iterator();
                                                while (it2.hasNext()) {
                                                    Settings.Global.putStringForUser(contentResolver, "data_roaming" + it2.next().getSubscriptionId(), "0", i2);
                                                }
                                            }
                                            Settings.Global.putStringForUser(contentResolver, "data_roaming", "0", i2);
                                        }
                                        break;
                                    case 1:
                                        if (z) {
                                            Settings.Secure.putIntForUser(contentResolver, "location_mode", 0, i2);
                                        }
                                        break;
                                    case 2:
                                        if (z && i2 == 0) {
                                            Settings.Global.putStringForUser(contentResolver, "adb_enabled", "0", i2);
                                            Settings.Global.putStringForUser(contentResolver, "adb_wifi_enabled", "0", i2);
                                        }
                                        break;
                                    case 3:
                                        if (z) {
                                            Settings.Global.putStringForUser(context.getContentResolver(), "verifier_verify_adb_installs", "1", i2);
                                        }
                                        break;
                                    case 4:
                                        if (!z && !UserManager.get(context).hasUserRestriction("no_install_unknown_sources", UserHandle.of(i2))) {
                                            i3 = 1;
                                        }
                                        Settings.Secure.putIntForUser(contentResolver, "install_non_market_apps", i3, i2);
                                        break;
                                    case 5:
                                        if (!z && !UserManager.get(context).hasUserRestriction("no_install_unknown_sources_globally", UserHandle.of(i2))) {
                                            i3 = 1;
                                        }
                                        Settings.Secure.putIntForUser(contentResolver, "install_non_market_apps", i3, i2);
                                        break;
                                    case 6:
                                        if (z && !((ActivityManager) context.getSystemService(ActivityManager.class)).isProfileForeground(UserHandle.of(i2)) && i2 != 0) {
                                            try {
                                                ActivityManager.getService().stopUserExceptCertainProfiles(i2, false, (IStopUserCallback) null);
                                            } catch (RemoteException e) {
                                                throw e.rethrowAsRuntimeException();
                                            }
                                        }
                                        break;
                                    case 7:
                                        Settings.Global.putInt(context.getContentResolver(), "safe_boot_disallowed", z ? 1 : 0);
                                        break;
                                    case '\b':
                                        if (z && Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1) {
                                            Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 0);
                                            Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
                                            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                                            context.sendBroadcastAsUser(intent, UserHandle.ALL);
                                        }
                                        break;
                                    case '\t':
                                        if (z) {
                                            new AmbientDisplayConfiguration(context).disableDozeSettings(i2);
                                        }
                                        break;
                                    case '\n':
                                    case 11:
                                        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                                        PackageManagerService packageManagerService = PackageManagerService.this;
                                        Computer snapshotComputer = packageManagerService.snapshotComputer();
                                        packageManagerService.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(snapshotComputer, snapshotComputer.getAllAvailablePackageNames(), new PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0(), i2);
                                        PackageManagerService packageManagerService2 = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).mService;
                                        Computer snapshotComputer2 = packageManagerService2.snapshotComputer();
                                        packageManagerService2.mDistractingPackageHelper.removeDistractingPackageRestrictions(snapshotComputer2, snapshotComputer2.getAllAvailablePackageNames(), i2);
                                        PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
                                        PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
                                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                                        synchronized (packageManagerTracedLock) {
                                            try {
                                                PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i2);
                                            } catch (Throwable th) {
                                                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                                                throw th;
                                            }
                                        }
                                        break;
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                }
            });
        }
        this.mAppliedUserRestrictions.updateRestrictions(i, new Bundle(computeEffectiveUserRestrictionsLR));
    }

    public void upgradeIfNecessaryLP(int i, int i2) {
        UserInfo earliestCreatedFullUser;
        Slog.i("UserManagerService", "Upgrading users from userVersion " + i + " to 11");
        ArraySet arraySet = new ArraySet();
        int i3 = this.mUserVersion;
        int i4 = this.mUserTypeVersion;
        if (i < 1) {
            UserData userDataNoChecks = getUserDataNoChecks(0);
            if ("Primary".equals(userDataNoChecks.info.name)) {
                userDataNoChecks.info.name = this.mContext.getResources().getString(R.string.sms_short_code_confirm_deny);
                arraySet.add(Integer.valueOf(userDataNoChecks.info.id));
            }
            i = 1;
        }
        if (i < 2) {
            UserInfo userInfo = getUserDataNoChecks(0).info;
            int i5 = userInfo.flags;
            if ((i5 & 16) == 0) {
                userInfo.flags = i5 | 16;
                arraySet.add(Integer.valueOf(userInfo.id));
            }
            i = 2;
        }
        if (i < 4) {
            i = 4;
        }
        if (i < 5) {
            initDefaultGuestRestrictions();
            i = 5;
        }
        if (i < 6) {
            synchronized (this.mUsersLock) {
                for (int i6 = 0; i6 < this.mUsers.size(); i6++) {
                    try {
                        UserData userData = (UserData) this.mUsers.valueAt(i6);
                        if (userData.info.isRestricted()) {
                            UserInfo userInfo2 = userData.info;
                            if (userInfo2.restrictedProfileParentId == -10000) {
                                userInfo2.restrictedProfileParentId = 0;
                                arraySet.add(Integer.valueOf(userInfo2.id));
                            }
                        }
                    } finally {
                    }
                }
            }
            i = 6;
        }
        if (i < 7) {
            synchronized (this.mRestrictionsLock) {
                try {
                    RestrictionsSet restrictionsSet = this.mDevicePolicyUserRestrictions;
                    boolean z = false;
                    for (int i7 = 0; i7 < restrictionsSet.mUserRestrictions.size(); i7++) {
                        Bundle bundle = (Bundle) restrictionsSet.mUserRestrictions.valueAt(i7);
                        Set set = UserRestrictionsUtils.USER_RESTRICTIONS;
                        if (bundle != null && bundle.getBoolean("ensure_verify_apps")) {
                            bundle.remove("ensure_verify_apps");
                            z = true;
                        }
                    }
                    if (z) {
                        Bundle bundle2 = (Bundle) this.mDevicePolicyUserRestrictions.mUserRestrictions.get(-1);
                        Set set2 = UserRestrictionsUtils.USER_RESTRICTIONS;
                        if (bundle2 == null) {
                            bundle2 = new Bundle();
                        }
                        bundle2.putBoolean("ensure_verify_apps", true);
                    }
                } finally {
                }
            }
            List guestUsers = getGuestUsers();
            int i8 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) guestUsers;
                if (i8 >= arrayList.size()) {
                    break;
                }
                UserInfo userInfo3 = (UserInfo) arrayList.get(i8);
                if (userInfo3 != null && !hasUserRestriction("no_config_wifi", userInfo3.id)) {
                    setUserRestriction("no_config_wifi", true, userInfo3.id);
                }
                i8++;
            }
            i = 7;
        }
        if (i < 8) {
            synchronized (this.mUsersLock) {
                try {
                    UserData userData2 = (UserData) this.mUsers.get(0);
                    userData2.info.flags |= 2048;
                    if (!isDefaultHeadlessSystemUserMode()) {
                        userData2.info.flags |= 1024;
                    }
                    arraySet.add(Integer.valueOf(userData2.info.id));
                    for (int i9 = 1; i9 < this.mUsers.size(); i9++) {
                        UserInfo userInfo4 = ((UserData) this.mUsers.valueAt(i9)).info;
                        int i10 = userInfo4.flags;
                        if ((i10 & 32) == 0) {
                            userInfo4.flags = i10 | 1024;
                            arraySet.add(Integer.valueOf(userInfo4.id));
                        }
                    }
                } finally {
                }
            }
            i = 8;
        }
        if (i < 9) {
            synchronized (this.mUsersLock) {
                for (int i11 = 0; i11 < this.mUsers.size(); i11++) {
                    try {
                        UserData userData3 = (UserData) this.mUsers.valueAt(i11);
                        UserInfo userInfo5 = userData3.info;
                        int i12 = userInfo5.flags;
                        if ((i12 & 2048) == 0) {
                            try {
                                userInfo5.userType = UserInfo.getDefaultUserType(i12);
                            } catch (IllegalArgumentException e) {
                                throw new IllegalStateException("Cannot upgrade user with flags " + Integer.toHexString(i12) + " because it doesn't correspond to a valid user type.", e);
                            }
                        } else if ((i12 & 1024) != 0) {
                            userInfo5.userType = "android.os.usertype.full.SYSTEM";
                        } else {
                            userInfo5.userType = "android.os.usertype.system.HEADLESS";
                        }
                        UserTypeDetails userTypeDetails = (UserTypeDetails) this.mUserTypes.get(userData3.info.userType);
                        if (userTypeDetails == null) {
                            throw new IllegalStateException("Cannot upgrade user with flags " + Integer.toHexString(i12) + " because " + userData3.info.userType + " isn't defined on this device!");
                        }
                        UserInfo userInfo6 = userData3.info;
                        userInfo6.flags = userTypeDetails.getDefaultUserInfoFlags() | userInfo6.flags;
                        arraySet.add(Integer.valueOf(userData3.info.id));
                    } finally {
                    }
                }
            }
            i = 9;
        }
        if (i < 10) {
            synchronized (this.mUsersLock) {
                for (int i13 = 0; i13 < this.mUsers.size(); i13++) {
                    try {
                        UserData userData4 = (UserData) this.mUsers.valueAt(i13);
                        UserTypeDetails userTypeDetails2 = (UserTypeDetails) this.mUserTypes.get(userData4.info.userType);
                        if (userTypeDetails2 == null) {
                            throw new IllegalStateException("Cannot upgrade user because " + userData4.info.userType + " isn't defined on this device!");
                        }
                        userData4.userProperties = new UserProperties(userTypeDetails2.mDefaultUserProperties);
                        arraySet.add(Integer.valueOf(userData4.info.id));
                    } finally {
                    }
                }
            }
            i = 10;
        }
        if (i < 11) {
            if (!isHeadlessSystemUserMode()) {
                synchronized (this.mUsersLock) {
                    UserInfo userInfo7 = ((UserData) this.mUsers.get(0)).info;
                    userInfo7.flags |= EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                    arraySet.add(Integer.valueOf(userInfo7.id));
                }
            } else if (Resources.getSystem().getBoolean(R.bool.config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled) && (earliestCreatedFullUser = getEarliestCreatedFullUser()) != null) {
                earliestCreatedFullUser.flags |= EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                arraySet.add(Integer.valueOf(earliestCreatedFullUser.id));
            }
            i = 11;
        }
        int userTypeVersion = UserTypeFactory.getUserTypeVersion();
        if (userTypeVersion > i2) {
            synchronized (this.mUsersLock) {
                XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
                try {
                    List parseUserUpgrades = UserTypeFactory.parseUserUpgrades(UserTypeFactory.getDefaultBuilders(), xml);
                    if (xml != null) {
                        xml.close();
                    }
                    upgradeUserTypesLU(parseUserUpgrades, this.mUserTypes, i2, arraySet);
                } finally {
                }
            }
        }
        if (i < 11) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("User version "), this.mUserVersion, " didn't upgrade as expected to 11", "UserManagerService");
            return;
        }
        if (i > 11) {
            Slog.wtf("UserManagerService", "Upgraded user version " + this.mUserVersion + " is higher the SDK's one of 11. Someone forgot to update USER_VERSION?");
        }
        this.mUserVersion = i;
        this.mUserTypeVersion = userTypeVersion;
        if (i3 < i || i4 < userTypeVersion) {
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                UserData userDataNoChecks2 = getUserDataNoChecks(((Integer) it.next()).intValue());
                if (userDataNoChecks2 != null) {
                    writeUserLP(userDataNoChecks2);
                }
            }
            writeUserListLP();
        }
    }

    public void upgradeProfileToTypeLU(UserInfo userInfo, UserTypeDetails userTypeDetails) {
        Slog.i("UserManagerService", "Upgrading user " + userInfo.id + " from " + userInfo.userType + " to " + userTypeDetails.mName);
        if (!userInfo.isProfile()) {
            throw new IllegalStateException(AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("Can only upgrade profile types. "), userInfo.userType, " is not a profile type."));
        }
        if (!canAddMoreProfilesToUser(userTypeDetails.mName, userInfo.profileGroupId, false)) {
            StringBuilder sb = new StringBuilder("Exceeded maximum profiles of type ");
            sb.append(userTypeDetails.mName);
            sb.append(" for user ");
            sb.append(userInfo.id);
            sb.append(". Maximum allowed= ");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, userTypeDetails.mMaxAllowedPerParent, "UserManagerService");
        }
        UserTypeDetails userTypeDetails2 = (UserTypeDetails) this.mUserTypes.get(userInfo.userType);
        int defaultUserInfoFlags = userTypeDetails2 != null ? userTypeDetails2.getDefaultUserInfoFlags() : 4096;
        userInfo.userType = userTypeDetails.mName;
        userInfo.flags = (defaultUserInfoFlags ^ userInfo.flags) | userTypeDetails.getDefaultUserInfoFlags();
        synchronized (this.mRestrictionsLock) {
            try {
                if (!BundleUtils.isEmpty(BundleUtils.clone(userTypeDetails.mDefaultRestrictions))) {
                    Bundle clone = BundleUtils.clone(this.mBaseUserRestrictions.getRestrictions(userInfo.id));
                    UserRestrictionsUtils.merge(clone, BundleUtils.clone(userTypeDetails.mDefaultRestrictions));
                    updateUserRestrictionsInternalLR(userInfo.id, clone);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        userInfo.profileBadge = getFreeProfileBadgeLU(userInfo.profileGroupId, userInfo.userType);
    }

    public final void upgradeUserTypesLU(List list, ArrayMap arrayMap, int i, Set set) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserTypeFactory.UserTypeUpgrade userTypeUpgrade = (UserTypeFactory.UserTypeUpgrade) it.next();
            if (i <= userTypeUpgrade.mUpToVersion) {
                for (int i2 = 0; i2 < this.mUsers.size(); i2++) {
                    UserData userData = (UserData) this.mUsers.valueAt(i2);
                    if (userTypeUpgrade.mFromType.equals(userData.info.userType)) {
                        String str = userTypeUpgrade.mToType;
                        UserTypeDetails userTypeDetails = (UserTypeDetails) arrayMap.get(str);
                        if (userTypeDetails == null) {
                            throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Upgrade destination user type not defined: ", str));
                        }
                        upgradeProfileToTypeLU(userData.info, userTypeDetails);
                        ((ArraySet) set).add(Integer.valueOf(userData.info.id));
                    }
                }
            }
        }
    }

    public boolean userExists(int i) {
        synchronized (this.mUsersLock) {
            try {
                for (int i2 : this.mUserIds) {
                    if (i2 == i) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UserInfo userWithName(UserInfo userInfo) {
        if (userInfo != null && userInfo.name == null) {
            String string = userInfo.id == 0 ? (String) this.mOwnerName.get() : userInfo.isMain() ? (String) this.mOwnerName.get() : userInfo.isGuest() ? this.mContext.getString(R.string.mime_type_spreadsheet_ext) : null;
            if (string != null) {
                UserInfo userInfo2 = new UserInfo(userInfo);
                userInfo2.name = string;
                return userInfo2;
            }
        }
        return userInfo;
    }

    public final void writeUserLP(UserData userData) {
        FileOutputStream fileOutputStream;
        ResilientAtomicFile userFile = getUserFile(userData.info.id);
        try {
            try {
                fileOutputStream = userFile.startWrite();
                try {
                    writeUserLP(userData, fileOutputStream);
                    userFile.finishWrite(fileOutputStream);
                } catch (Exception e) {
                    e = e;
                    Slog.e("UserManagerService", "Error writing user info " + userData.info.id, e);
                    userFile.failWrite(fileOutputStream);
                    userFile.close();
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
            }
            userFile.close();
        } catch (Throwable th) {
            try {
                userFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void writeUserLP(UserData userData, OutputStream outputStream) throws IOException, XmlPullParserException {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        UserInfo userInfo = userData.info;
        resolveSerializer.startTag((String) null, "user");
        resolveSerializer.attributeInt((String) null, "id", userInfo.id);
        resolveSerializer.attributeInt((String) null, "serialNumber", userInfo.serialNumber);
        resolveSerializer.attributeInt((String) null, "flags", userInfo.flags);
        resolveSerializer.attribute((String) null, "attributes", Integer.toString(userInfo.getAttributes()));
        resolveSerializer.attribute((String) null, "type", userInfo.userType);
        resolveSerializer.attributeLong((String) null, "created", userInfo.creationTime);
        resolveSerializer.attributeLong((String) null, "lastLoggedIn", userInfo.lastLoggedInTime);
        String str = userInfo.lastLoggedInFingerprint;
        if (str != null) {
            resolveSerializer.attribute((String) null, "lastLoggedInFingerprint", str);
        }
        resolveSerializer.attributeLong((String) null, "lastEnteredForeground", userData.mLastEnteredForegroundTimeMillis);
        String str2 = userInfo.iconPath;
        if (str2 != null) {
            resolveSerializer.attribute((String) null, KnoxCustomManagerService.ICON, str2);
        }
        if (userInfo.partial) {
            resolveSerializer.attributeBoolean((String) null, "partial", true);
        }
        if (userInfo.preCreated) {
            resolveSerializer.attributeBoolean((String) null, "preCreated", true);
        }
        if (userInfo.convertedFromPreCreated) {
            resolveSerializer.attributeBoolean((String) null, "convertedFromPreCreated", true);
        }
        if (userInfo.guestToRemove) {
            resolveSerializer.attributeBoolean((String) null, "guestToRemove", true);
        }
        int i = userInfo.profileGroupId;
        if (i != -10000) {
            resolveSerializer.attributeInt((String) null, "profileGroupId", i);
        }
        resolveSerializer.attributeInt((String) null, "profileBadge", userInfo.profileBadge);
        int i2 = userInfo.restrictedProfileParentId;
        if (i2 != -10000) {
            resolveSerializer.attributeInt((String) null, "restrictedProfileParentId", i2);
        }
        if (userData.persistSeedData) {
            String str3 = userData.seedAccountName;
            if (str3 != null) {
                resolveSerializer.attribute((String) null, "seedAccountName", truncateString(500, str3));
            }
            String str4 = userData.seedAccountType;
            if (str4 != null) {
                resolveSerializer.attribute((String) null, "seedAccountType", truncateString(500, str4));
            }
        }
        if (userInfo.name != null) {
            resolveSerializer.startTag((String) null, "name");
            resolveSerializer.text(truncateString(100, userInfo.name));
            resolveSerializer.endTag((String) null, "name");
        }
        synchronized (this.mRestrictionsLock) {
            UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mBaseUserRestrictions.getRestrictions(userInfo.id), "restrictions");
            if (!android.multiuser.Flags.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly()) {
                UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(-1), "device_policy_global_restrictions");
            } else if (userInfo.id == 0) {
                UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(-1), "device_policy_global_restrictions");
                resolveSerializer.startTag((String) null, "guestRestrictions");
                synchronized (this.mGuestRestrictions) {
                    UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mGuestRestrictions, "restrictions");
                }
                resolveSerializer.endTag((String) null, "guestRestrictions");
            }
            UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(userInfo.id), "device_policy_local_restrictions");
        }
        if (userData.account != null) {
            resolveSerializer.startTag((String) null, "account");
            resolveSerializer.text(userData.account);
            resolveSerializer.endTag((String) null, "account");
        }
        if (userData.persistSeedData && userData.seedAccountOptions != null) {
            resolveSerializer.startTag((String) null, "seedAccountOptions");
            userData.seedAccountOptions.saveToXml(resolveSerializer);
            resolveSerializer.endTag((String) null, "seedAccountOptions");
        }
        if (userData.userProperties != null) {
            resolveSerializer.startTag((String) null, "userProperties");
            userData.userProperties.writeToXml(resolveSerializer);
            resolveSerializer.endTag((String) null, "userProperties");
        }
        if (userData.mLastRequestQuietModeEnabledMillis != 0) {
            resolveSerializer.startTag((String) null, "lastRequestQuietModeEnabledCall");
            resolveSerializer.text(String.valueOf(userData.mLastRequestQuietModeEnabledMillis));
            resolveSerializer.endTag((String) null, "lastRequestQuietModeEnabledCall");
        }
        resolveSerializer.startTag((String) null, "ignorePrepareStorageErrors");
        resolveSerializer.text(String.valueOf(userData.mIgnorePrepareStorageErrors));
        resolveSerializer.endTag((String) null, "ignorePrepareStorageErrors");
        resolveSerializer.endTag((String) null, "user");
        resolveSerializer.endDocument();
    }

    public final void writeUserListLP() {
        FileOutputStream startWrite;
        int size;
        int[] iArr;
        int i;
        setContainerInfo();
        ResilientAtomicFile userListFile = getUserListFile();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                startWrite = userListFile.startWrite();
            } catch (Exception e) {
                e = e;
            }
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((String) null, "users");
                resolveSerializer.attributeInt((String) null, "nextSerialNumber", this.mNextSerialNumber);
                resolveSerializer.attributeInt((String) null, "version", this.mUserVersion);
                resolveSerializer.attributeInt((String) null, "userTypeConfigVersion", this.mUserTypeVersion);
                if (!android.multiuser.Flags.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly()) {
                    resolveSerializer.startTag((String) null, "guestRestrictions");
                    synchronized (this.mGuestRestrictions) {
                        UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mGuestRestrictions, "restrictions");
                    }
                    resolveSerializer.endTag((String) null, "guestRestrictions");
                }
                synchronized (this.mUsersLock) {
                    try {
                        size = this.mUsers.size();
                        iArr = new int[size];
                        for (int i2 = 0; i2 < size; i2++) {
                            iArr[i2] = ((UserData) this.mUsers.valueAt(i2)).info.id;
                        }
                    } finally {
                    }
                }
                for (i = 0; i < size; i++) {
                    int i3 = iArr[i];
                    resolveSerializer.startTag((String) null, "user");
                    resolveSerializer.attributeInt((String) null, "id", i3);
                    resolveSerializer.endTag((String) null, "user");
                }
                resolveSerializer.endTag((String) null, "users");
                resolveSerializer.endDocument();
                userListFile.finishWrite(startWrite);
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = startWrite;
                Slog.e("UserManagerService", "Error writing user list", e);
                userListFile.failWrite(fileOutputStream);
                userListFile.close();
            }
            userListFile.close();
        } catch (Throwable th) {
            try {
                userListFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
