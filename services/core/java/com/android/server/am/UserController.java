package com.android.server.am;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.BroadcastOptions;
import android.app.IStopUserCallback;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.IPackageManager;
import android.content.pm.PackagePartitions;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IProgressListener;
import android.os.IUserManager;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ObjectUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.ProgressReporter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FactoryResetter;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.UserController;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerServiceExt;
import com.android.server.wm.DexController;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UserController implements Handler.Callback {
    public volatile boolean mAllowUserUnlocking;
    public final SparseIntArray mCompletedEventTypes;
    public volatile ArraySet mCurWaitingUserSwitchCallbacks;
    public int mCurrentDexMode;
    public int[] mCurrentProfileIds;
    public boolean mDelayUserDataLocking;
    public final Handler mHandler;
    public boolean mInitialized;
    public final Injector mInjector;
    public boolean mIsBroadcastSentForSystemUserStarted;
    public boolean mIsBroadcastSentForSystemUserStarting;
    public final ArrayList mLastActiveUsersForDelayedLocking;
    public volatile long mLastUserUnlockingUptime;
    public final LockPatternUtils mLockPatternUtils;
    public int mMaxRunningUsers;
    public final List mPendingUserStarts;
    public PersonaManagerInternal mPersonaManagerInternal;
    public int[] mStartedUserArray;
    public final SparseArray mStartedUsers;
    public int mStopUserOnSwitch;
    public String mSwitchingFromSystemUserMessage;
    public String mSwitchingToSystemUserMessage;
    public ArraySet mTimeoutUserSwitchCallbacks;
    public final Handler mUiHandler;
    public final AnonymousClass1 mUserLifecycleListener;
    public final ArrayList mUserLru;
    public final SparseIntArray mUserProfileGroupIds;
    public final RemoteCallbackList mUserSwitchObservers;
    public boolean mUserSwitchUiEnabled;
    public int mBackgroundUserScheduledStopTimeSecs = -1;
    public final Object mLock = new Object();
    public volatile int mCurrentUserId = 0;
    public volatile int mTargetUserId = -10000;
    public final ArrayDeque mPendingTargetUserIds = new ArrayDeque();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.UserController$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public final /* synthetic */ int val$userStartMode;

        public AnonymousClass4(int i) {
            this.val$userStartMode = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.UserController$5, reason: invalid class name */
    public final class AnonymousClass5 extends IIntentReceiver.Stub {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object val$finishUserStoppingAsync;
        public final /* synthetic */ int val$userId;

        public AnonymousClass5(int i, UserController$$ExternalSyntheticLambda1 userController$$ExternalSyntheticLambda1) {
            this.val$userId = i;
            this.val$finishUserStoppingAsync = userController$$ExternalSyntheticLambda1;
        }

        public AnonymousClass5(int i, UserController$$ExternalSyntheticLambda1 userController$$ExternalSyntheticLambda1, byte b) {
            this.val$userId = i;
            this.val$finishUserStoppingAsync = userController$$ExternalSyntheticLambda1;
        }

        public AnonymousClass5(UserController userController, int i) {
            this.val$finishUserStoppingAsync = userController;
            this.val$userId = i;
        }

        public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            switch (this.$r8$classId) {
                case 0:
                    UserController.asyncTraceEnd(this.val$userId, AmFmBandRange$$ExternalSyntheticOutline0.m(this.val$userId, new StringBuilder("broadcast-ACTION_USER_STOPPING-"), "-[stopUser]"));
                    ((Runnable) this.val$finishUserStoppingAsync).run();
                    break;
                case 1:
                    UserController.asyncTraceEnd(this.val$userId, AmFmBandRange$$ExternalSyntheticOutline0.m(this.val$userId, new StringBuilder("broadcast-ACTION_SHUTDOWN-"), "-[stopUser]"));
                    ((Runnable) this.val$finishUserStoppingAsync).run();
                    break;
                default:
                    Slogf.i("ActivityManager", "Finished processing BOOT_COMPLETED for u" + this.val$userId);
                    ((UserController) this.val$finishUserStoppingAsync).getClass();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.UserController$8, reason: invalid class name */
    public final class AnonymousClass8 extends IIntentReceiver.Stub {
        public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public PowerManagerInternal mPowerManagerInternal;
        public final ActivityManagerService mService;
        public UserManagerService mUserManager;
        public UserManagerInternal mUserManagerInternal;
        public UserSwitchingDialog mUserSwitchingDialog;
        public final Object mUserSwitchingDialogLock = new Object();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.am.UserController$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 extends IIntentReceiver.Stub {
            public final PreBootBroadcaster$1 mHandler;
            public int mIndex = 0;
            public final Intent mIntent;
            public final ProgressReporter mProgress;
            public final boolean mQuiet;
            public final ActivityManagerService mService;
            public final List mTargets;
            public final int mUserId;
            public final /* synthetic */ Runnable val$onFinish;

            /* JADX WARN: Type inference failed for: r5v2, types: [com.android.server.am.PreBootBroadcaster$1] */
            public AnonymousClass1(ActivityManagerService activityManagerService, int i, boolean z, UserController$$ExternalSyntheticLambda18 userController$$ExternalSyntheticLambda18) {
                this.val$onFinish = userController$$ExternalSyntheticLambda18;
                final Looper looper = UiThread.get().getLooper();
                this.mHandler = new Handler(looper) { // from class: com.android.server.am.PreBootBroadcaster$1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        UserController.Injector.AnonymousClass1 anonymousClass1 = UserController.Injector.AnonymousClass1.this;
                        Context context = anonymousClass1.mService.mContext;
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
                        int i2 = message.arg1;
                        int i3 = message.arg2;
                        int i4 = message.what;
                        if (i4 != 1) {
                            if (i4 != 2) {
                                return;
                            }
                            notificationManager.cancelAsUser("PreBootBroadcaster", 13, UserHandle.of(anonymousClass1.mUserId));
                        } else {
                            CharSequence text = context.getText(R.string.capability_title_canRetrieveWindowContent);
                            Intent intent = new Intent();
                            intent.setClassName(KnoxCustomManagerService.SETTING_PKG_NAME, "com.android.settings.HelpTrampoline");
                            intent.putExtra("android.intent.extra.TEXT", "help_url_upgrading");
                            notificationManager.notifyAsUser("PreBootBroadcaster", 13, new Notification.Builder(anonymousClass1.mService.mContext, SystemNotificationChannels.UPDATES).setSmallIcon(17304445).setWhen(0L).setOngoing(true).setTicker(text).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(text).setContentIntent(context.getPackageManager().resolveActivity(intent, 0) != null ? PendingIntent.getActivity(context, 0, intent, 67108864) : null).setVisibility(1).setProgress(i2, i3, false).build(), UserHandle.of(anonymousClass1.mUserId));
                        }
                    }
                };
                this.mService = activityManagerService;
                this.mUserId = i;
                this.mProgress = null;
                this.mQuiet = z;
                Intent intent = new Intent("android.intent.action.PRE_BOOT_COMPLETED");
                this.mIntent = intent;
                intent.addFlags(33554688);
                this.mTargets = activityManagerService.mContext.getPackageManager().queryBroadcastReceiversAsUser(intent, 1048576, UserHandle.of(i));
            }

            public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
                sendNext();
            }

            public final void sendNext() {
                if (this.mIndex >= this.mTargets.size()) {
                    obtainMessage(2).sendToTarget();
                    this.val$onFinish.run();
                    return;
                }
                if (!this.mService.isUserRunning(this.mUserId, 0)) {
                    CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(new StringBuilder("User "), this.mUserId, " is no longer running; skipping remaining receivers", "PreBootBroadcaster");
                    obtainMessage(2).sendToTarget();
                    this.val$onFinish.run();
                    return;
                }
                if (!this.mQuiet) {
                    obtainMessage(1, this.mTargets.size(), this.mIndex).sendToTarget();
                }
                List list = this.mTargets;
                int i = this.mIndex;
                this.mIndex = i + 1;
                ResolveInfo resolveInfo = (ResolveInfo) list.get(i);
                ComponentName componentName = resolveInfo.activityInfo.getComponentName();
                if (this.mProgress != null) {
                    this.mProgress.setProgress(this.mIndex, this.mTargets.size(), this.mService.mContext.getString(R.string.capability_title_canCaptureFingerprintGestures, resolveInfo.activityInfo.loadLabel(this.mService.mContext.getPackageManager())));
                }
                StringBuilder sb = new StringBuilder("Pre-boot of ");
                sb.append(componentName.toShortString());
                sb.append(" for user ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(sb, this.mUserId, "PreBootBroadcaster");
                EventLog.writeEvent(30045, Integer.valueOf(this.mUserId), componentName.getPackageName());
                this.mIntent.setComponent(componentName);
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                long bootTimeTempAllowListDuration = activityManagerInternal != null ? activityManagerInternal.getBootTimeTempAllowListDuration() : 10000L;
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, 201, "");
                ActivityManagerService activityManagerService = this.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        this.mService.broadcastIntentLocked(this.mIntent, this, null, makeBasic.toBundle(), true, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), this.mUserId);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }

        public Injector(ActivityManagerService activityManagerService) {
            this.mService = activityManagerService;
        }

        public final void broadcastIntent(Intent intent, IIntentReceiver iIntentReceiver, String[] strArr, Bundle bundle, int i, int i2, int i3, int i4) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if (intExtra == -10000) {
                intExtra = i4;
            }
            EventLog.writeEvent(30081, Integer.valueOf(intExtra), intent.getAction());
            TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
            ActivityManagerService activityManagerService = this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    timingsTraceAndSlog.traceBegin("broadcastIntent-" + i4 + PackageManagerShellCommandDataLoader.STDIN_PATH + intent.getAction());
                    this.mService.broadcastIntentLocked(intent, iIntentReceiver, strArr, bundle, false, i, i2, i3, i4);
                    timingsTraceAndSlog.traceEnd();
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public final void clearAllLockedTasks() {
            ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) this.mService.mAtmInternal;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mLockTaskController.clearLockedTasks("startUser");
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void dismissUserSwitchingDialog(Runnable runnable) {
            synchronized (this.mUserSwitchingDialogLock) {
                try {
                    UserSwitchingDialog userSwitchingDialog = this.mUserSwitchingDialog;
                    if (userSwitchingDialog != null) {
                        userSwitchingDialog.dismiss(runnable);
                        this.mUserSwitchingDialog = null;
                    } else if (runnable != null) {
                        runnable.run();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final UserManagerService getUserManager() {
            if (this.mUserManager == null) {
                this.mUserManager = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
            }
            return this.mUserManager;
        }

        public final UserManagerInternal getUserManagerInternal() {
            if (this.mUserManagerInternal == null) {
                this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            return this.mUserManagerInternal;
        }

        public final void onUserStarting(int i) {
            SystemServiceManager systemServiceManager = this.mService.mSystemServiceManager;
            TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
            SystemService.TargetUser newTargetUser = systemServiceManager.newTargetUser(i);
            synchronized (systemServiceManager.mTargetUsers) {
                if (i == 0) {
                    try {
                        if (systemServiceManager.mTargetUsers.contains(i)) {
                            Slog.e("SystemServiceManager", "Skipping starting system user twice");
                        }
                    } finally {
                    }
                }
                systemServiceManager.mTargetUsers.put(i, newTargetUser);
                EventLog.writeEvent(30082, i);
                systemServiceManager.onUser(newAsyncLog, "Start", null, newTargetUser, null);
            }
        }

        public final void setPerformancePowerMode(boolean z) {
            Slogf.i("ActivityManager", "Setting power mode MODE_FIXED_PERFORMANCE to " + z);
            if (this.mPowerManagerInternal == null) {
                this.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
            }
            this.mPowerManagerInternal.setPowerMode(3, z);
        }

        public final void updateUserConfiguration() {
            ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) this.mService.mAtmInternal;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Configuration configuration = new Configuration(ActivityTaskManagerService.this.getGlobalConfiguration());
                    int currentUserId = ActivityTaskManagerService.this.mAmInternal.getCurrentUserId();
                    Settings.System.adjustConfigurationForUser(ActivityTaskManagerService.this.mContext.getContentResolver(), configuration, currentUserId, Settings.System.canWrite(ActivityTaskManagerService.this.mContext));
                    ActivityTaskManagerService.this.updateConfigurationLocked(configuration, false, false, currentUserId);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingUserStart {
        public final IProgressListener unlockListener;
        public final int userId;
        public final int userStartMode;

        public PendingUserStart(int i, int i2, IProgressListener iProgressListener) {
            this.userId = i;
            this.userStartMode = i2;
            this.unlockListener = iProgressListener;
        }

        public final String toString() {
            return "PendingUserStart{userId=" + this.userId + ", userStartMode=" + UserManagerInternal.userStartModeToString(this.userStartMode) + ", unlockListener=" + this.unlockListener + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserProgressListener extends IProgressListener.Stub {
        public volatile long mUnlockStarted;

        public final void onFinished(int i, Bundle bundle) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mUnlockStarted;
            if (i == 0) {
                new TimingsTraceAndSlog().logDuration("SystemUserUnlock", uptimeMillis);
            } else {
                new TimingsTraceAndSlog().logDuration(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User", "Unlock"), uptimeMillis);
            }
        }

        public final void onProgress(int i, int i2, Bundle bundle) {
            Slogf.d("ActivityManager", "Unlocking user " + i + " progress " + i2);
        }

        public final void onStarted(int i, Bundle bundle) {
            Slogf.d("ActivityManager", "Started unlocking user " + i);
            this.mUnlockStarted = SystemClock.uptimeMillis();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.am.UserController$1] */
    public UserController(Injector injector) {
        SparseArray sparseArray = new SparseArray();
        this.mStartedUsers = sparseArray;
        ArrayList arrayList = new ArrayList();
        this.mUserLru = arrayList;
        this.mStartedUserArray = new int[]{0};
        this.mCurrentProfileIds = new int[0];
        this.mUserProfileGroupIds = new SparseIntArray();
        this.mUserSwitchObservers = new RemoteCallbackList();
        this.mUserSwitchUiEnabled = true;
        this.mLastActiveUsersForDelayedLocking = new ArrayList();
        this.mCompletedEventTypes = new SparseIntArray();
        this.mStopUserOnSwitch = -1;
        this.mLastUserUnlockingUptime = 0L;
        this.mCurrentDexMode = 0;
        this.mPendingUserStarts = new ArrayList();
        this.mUserLifecycleListener = new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.am.UserController.1
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public final void onUserCreated(UserInfo userInfo, Object obj) {
                UserController userController = UserController.this;
                userController.getClass();
                if (userInfo.isProfile()) {
                    synchronized (userController.mLock) {
                        try {
                            if (userInfo.profileGroupId == userController.mCurrentUserId) {
                                userController.mCurrentProfileIds = ArrayUtils.appendInt(userController.mCurrentProfileIds, userInfo.id);
                            }
                            int i = userInfo.profileGroupId;
                            if (i != -10000) {
                                userController.mUserProfileGroupIds.put(userInfo.id, i);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }

            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public final void onUserRemoved(UserInfo userInfo) {
                UserController userController = UserController.this;
                int i = userInfo.id;
                synchronized (userController.mLock) {
                    try {
                        for (int size = userController.mUserProfileGroupIds.size() - 1; size >= 0; size--) {
                            if (userController.mUserProfileGroupIds.keyAt(size) != i && userController.mUserProfileGroupIds.valueAt(size) != i) {
                            }
                            userController.mUserProfileGroupIds.removeAt(size);
                        }
                        userController.mCurrentProfileIds = ArrayUtils.removeInt(userController.mCurrentProfileIds, i);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.mInjector = injector;
        injector.getClass();
        this.mHandler = new Handler(injector.mService.mHandlerThread.getLooper(), this);
        this.mUiHandler = new Handler(injector.mService.mUiHandler.getLooper(), this);
        UserState userState = new UserState(UserHandle.SYSTEM);
        userState.mUnlockProgress.addListener(new UserProgressListener());
        sparseArray.put(0, userState);
        arrayList.add(0);
        this.mLockPatternUtils = new LockPatternUtils(injector.mService.mContext);
        updateStartedUserArrayLU();
    }

    public static void asyncTraceBegin(int i, String str) {
        Slogf.d("ActivityManager", "%s - asyncTraceBegin(%d)", str, Integer.valueOf(i));
        Trace.asyncTraceBegin(64L, str, i);
    }

    public static void asyncTraceEnd(int i, String str) {
        Trace.asyncTraceEnd(64L, str, i);
        Slogf.d("ActivityManager", "%s - asyncTraceEnd(%d)", str, Integer.valueOf(i));
    }

    public static void showEventLog(int i, int i2, int i3, String str, String str2) {
        EventLogTags.writeBootProgressAmsState(i, i2, i3, str, str2);
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "!@AM_BOOT_PROGRESS , uid : ", ", state:  ", ", progress : ");
        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i3, ", step : ", str, ", description : ", m);
        BootReceiver$$ExternalSyntheticOutline0.m(m, str2, "ActivityManager");
    }

    public final void addUserToUserLru(int i) {
        synchronized (this.mLock) {
            try {
                Integer valueOf = Integer.valueOf(i);
                this.mUserLru.remove(valueOf);
                this.mUserLru.add(valueOf);
                int i2 = this.mUserProfileGroupIds.get(i, -10000);
                Integer valueOf2 = Integer.valueOf(i2);
                if (i2 != -10000 && !valueOf2.equals(valueOf) && this.mUserLru.remove(valueOf2)) {
                    this.mUserLru.add(valueOf2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void broadcastProfileAccessibleStateChanged(int i, int i2, String str) {
        Intent intent = new Intent(str);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(i));
        intent.addFlags(1342177280);
        this.mInjector.broadcastIntent(intent, null, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), i2);
    }

    public final boolean canDelayDataLockingForUser(int i) {
        if (!com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() || !android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            return this.mDelayUserDataLocking;
        }
        UserProperties userProperties = this.mInjector.getUserManagerInternal().getUserProperties(i);
        return this.mDelayUserDataLocking || (userProperties != null && userProperties.getAllowStoppingUserWithDelayedLocking());
    }

    public final void checkCallingHasOneOfThosePermissions(String str, String... strArr) {
        if (Binder.getCallingUid() == 2000 && MaintenanceModeManager.isInMaintenanceModeFromProperty()) {
            throw new SecurityException("Cannot control users : unauthorized");
        }
        for (String str2 : strArr) {
            if (this.mInjector.mService.checkCallingPermission(str2) == 0) {
                return;
            }
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Permission denial: ", str, "() from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        m.append(" requires ");
        m.append(strArr.length == 1 ? strArr[0] : "one of " + Arrays.toString(strArr));
        String sb = m.toString();
        Slogf.w("ActivityManager", sb);
        throw new SecurityException(sb);
    }

    public final void checkGetCurrentUserPermissions() {
        if (this.mInjector.mService.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0 || this.mInjector.mService.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) {
            return;
        }
        String str = "Permission Denial: getCurrentUser() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " requires android.permission.INTERACT_ACROSS_USERS";
        Slogf.w("ActivityManager", str);
        throw new SecurityException(str);
    }

    public final void checkHasManageUsersPermission(String str) {
        if (this.mInjector.mService.checkCallingPermission("android.permission.MANAGE_USERS") == -1) {
            throw new SecurityException("You need MANAGE_USERS permission to call ".concat(str));
        }
    }

    public void completeUserSwitch(int i, int i2) {
        boolean z;
        UserController$$ExternalSyntheticLambda6 userController$$ExternalSyntheticLambda6 = new UserController$$ExternalSyntheticLambda6(i, i2, 0, this);
        synchronized (this.mLock) {
            z = this.mUserSwitchUiEnabled;
        }
        if (!z) {
            userController$$ExternalSyntheticLambda6.run();
            return;
        }
        if (((KeyguardManager) this.mInjector.mService.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(i2)) {
            UserController$$ExternalSyntheticLambda7 userController$$ExternalSyntheticLambda7 = new UserController$$ExternalSyntheticLambda7(this, userController$$ExternalSyntheticLambda6, 0);
            final Injector injector = this.mInjector;
            Objects.requireNonNull(injector);
            final int i3 = 0;
            runWithTimeout(new Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i4 = i3;
                    final UserController.Injector injector2 = injector;
                    final Runnable runnable = (Runnable) obj;
                    switch (i4) {
                        case 0:
                            ActivityManagerService activityManagerService = injector2.mService;
                            if (!activityManagerService.mWindowManager.isKeyguardLocked()) {
                                activityManagerService.mAtmInternal.registerScreenObserver(new ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.am.UserController.Injector.2
                                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                                    public final void onAwakeStateChanged(boolean z2) {
                                    }

                                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                                    public final void onKeyguardStateChanged(boolean z2) {
                                        if (z2) {
                                            ActivityTaskManagerService.this.mScreenObservers.remove(this);
                                            runnable.run();
                                        }
                                    }
                                });
                                activityManagerService.mWindowManager.lockNow(null);
                                break;
                            } else {
                                runnable.run();
                                break;
                            }
                        default:
                            injector2.mService.mWindowManager.dismissKeyguard(new IKeyguardDismissCallback.Stub() { // from class: com.android.server.am.UserController.Injector.3
                                public final void onDismissCancelled() {
                                    runnable.run();
                                }

                                public final void onDismissError() {
                                    runnable.run();
                                }

                                public final void onDismissSucceeded() {
                                    runnable.run();
                                }
                            }, null);
                            break;
                    }
                }
            }, 20000, userController$$ExternalSyntheticLambda7, new UserController$$ExternalSyntheticLambda16(0), "showKeyguard");
            return;
        }
        UserController$$ExternalSyntheticLambda7 userController$$ExternalSyntheticLambda72 = new UserController$$ExternalSyntheticLambda7(this, userController$$ExternalSyntheticLambda6, 2);
        final Injector injector2 = this.mInjector;
        Objects.requireNonNull(injector2);
        final int i4 = 1;
        runWithTimeout(new Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i42 = i4;
                final UserController.Injector injector22 = injector2;
                final Runnable runnable = (Runnable) obj;
                switch (i42) {
                    case 0:
                        ActivityManagerService activityManagerService = injector22.mService;
                        if (!activityManagerService.mWindowManager.isKeyguardLocked()) {
                            activityManagerService.mAtmInternal.registerScreenObserver(new ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.am.UserController.Injector.2
                                @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                                public final void onAwakeStateChanged(boolean z2) {
                                }

                                @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                                public final void onKeyguardStateChanged(boolean z2) {
                                    if (z2) {
                                        ActivityTaskManagerService.this.mScreenObservers.remove(this);
                                        runnable.run();
                                    }
                                }
                            });
                            activityManagerService.mWindowManager.lockNow(null);
                            break;
                        } else {
                            runnable.run();
                            break;
                        }
                    default:
                        injector22.mService.mWindowManager.dismissKeyguard(new IKeyguardDismissCallback.Stub() { // from class: com.android.server.am.UserController.Injector.3
                            public final void onDismissCancelled() {
                                runnable.run();
                            }

                            public final void onDismissError() {
                                runnable.run();
                            }

                            public final void onDismissSucceeded() {
                                runnable.run();
                            }
                        }, null);
                        break;
                }
            }
        }, 2000, userController$$ExternalSyntheticLambda72, userController$$ExternalSyntheticLambda72, "dismissKeyguard");
    }

    public void continueUserSwitch(UserState userState, int i, int i2) {
        int i3;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("continueUserSwitch-" + i + "-to-" + i2);
        EventLog.writeEvent(30080, Integer.valueOf(i), Integer.valueOf(i2));
        this.mHandler.removeMessages(130);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(130, i, i2));
        userState.switching = false;
        synchronized (this.mLock) {
            UserState userState2 = (UserState) this.mStartedUsers.get(i);
            if (i != 0 && i != this.mCurrentUserId && userState2 != null && (i3 = userState2.state) != 4 && i3 != 5) {
                UserInfo userInfo = getUserInfo(i);
                if (userInfo.isEphemeral()) {
                    ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).onEphemeralUserStop(i);
                }
                if (userInfo.isGuest() || userInfo.isEphemeral()) {
                    Slogf.i("ActivityManager", "Stopping background guest or ephemeral user " + i);
                    synchronized (this.mLock) {
                        stopUsersLU(i, true, false, null, null);
                    }
                }
            }
        }
        if (i != 0) {
            boolean hasUserRestriction = hasUserRestriction("no_run_in_background", i);
            synchronized (this.mLock) {
                if (!hasUserRestriction) {
                    if (!shouldStopUserOnSwitch()) {
                        ArrayList arrayList = (ArrayList) this.mInjector.getUserManager().getProfiles(i, false);
                        int size = arrayList.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            int i5 = ((UserInfo) arrayList.get(i4)).id;
                            if (hasUserRestriction("no_run_in_background", i5)) {
                                Slogf.i("ActivityManager", "Stopping profile %d on user switch", Integer.valueOf(i5));
                                synchronized (this.mLock) {
                                    stopUsersLU(i5, false, false, null, null);
                                }
                            }
                        }
                    }
                }
                Slogf.i("ActivityManager", "Stopping user %d and its profiles on user switch", Integer.valueOf(i));
                stopUsersLU(i, true, false, null, null);
            }
        }
        scheduleStopOfBackgroundUser(i);
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b1  */
    /* JADX WARN: Type inference failed for: r18v0, types: [long] */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v3 */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5, types: [java.util.concurrent.atomic.AtomicInteger] */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchUserSwitch(final com.android.server.am.UserState r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.UserController.dispatchUserSwitch(com.android.server.am.UserState, int, int):void");
    }

    public void dispatchUserSwitchComplete(int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("dispatchUserSwitchComplete-" + i2);
        this.mInjector.mService.mWindowManager.setSwitchingUser(false);
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i3 = 0; i3 < beginBroadcast; i3++) {
            try {
                timingsTraceAndSlog.traceBegin("onUserSwitchComplete-" + i2 + " #" + i3 + " " + this.mUserSwitchObservers.getBroadcastCookie(i3));
                this.mUserSwitchObservers.getBroadcastItem(i3).onUserSwitchComplete(i2);
                timingsTraceAndSlog.traceEnd();
            } catch (RemoteException unused) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
        timingsTraceAndSlog.traceBegin("sendUserSwitchBroadcasts-" + i + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        sendUserSwitchBroadcasts(i, i2);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceEnd();
        endUserSwitch();
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(1146756268046L);
                int i = 0;
                for (int i2 = 0; i2 < this.mStartedUsers.size(); i2++) {
                    UserState userState = (UserState) this.mStartedUsers.valueAt(i2);
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, userState.mHandle.getIdentifier());
                    long start3 = protoOutputStream.start(1146756268034L);
                    int i3 = userState.state;
                    if (i3 != 0) {
                        int i4 = 1;
                        if (i3 != 1) {
                            i4 = 2;
                            if (i3 != 2) {
                                i4 = 3;
                                if (i3 != 3) {
                                    i4 = 4;
                                    if (i3 != 4) {
                                        i4 = 5;
                                        if (i3 != 5) {
                                        }
                                    }
                                }
                            }
                        }
                        i3 = i4;
                    } else {
                        i3 = 0;
                    }
                    protoOutputStream.write(1159641169921L, i3);
                    protoOutputStream.write(1133871366146L, userState.switching);
                    protoOutputStream.end(start3);
                    protoOutputStream.end(start2);
                }
                int i5 = 0;
                while (true) {
                    int[] iArr = this.mStartedUserArray;
                    if (i5 >= iArr.length) {
                        break;
                    }
                    protoOutputStream.write(2220498092034L, iArr[i5]);
                    i5++;
                }
                for (int i6 = 0; i6 < this.mUserLru.size(); i6++) {
                    protoOutputStream.write(2220498092035L, ((Integer) this.mUserLru.get(i6)).intValue());
                }
                if (this.mUserProfileGroupIds.size() > 0) {
                    for (int i7 = 0; i7 < this.mUserProfileGroupIds.size(); i7++) {
                        long start4 = protoOutputStream.start(2246267895812L);
                        protoOutputStream.write(1120986464257L, this.mUserProfileGroupIds.keyAt(i7));
                        protoOutputStream.write(1120986464258L, this.mUserProfileGroupIds.valueAt(i7));
                        protoOutputStream.end(start4);
                    }
                }
                protoOutputStream.write(1120986464261L, this.mCurrentUserId);
                while (true) {
                    int[] iArr2 = this.mCurrentProfileIds;
                    if (i < iArr2.length) {
                        protoOutputStream.write(2220498092038L, iArr2[i]);
                        i++;
                    } else {
                        protoOutputStream.end(start);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void endUserSwitch() {
        int intValue;
        if (android.multiuser.Flags.setPowerModeDuringUserSwitch()) {
            this.mInjector.setPerformancePowerMode(false);
        }
        synchronized (this.mLock) {
            intValue = ((Integer) ObjectUtils.getOrElse((Integer) this.mPendingTargetUserIds.poll(), -10000)).intValue();
            this.mTargetUserId = -10000;
        }
        if (intValue != -10000) {
            switchUser(intValue);
        }
    }

    public final void enforceShellRestriction(int i) {
        if (Binder.getCallingUid() == 2000) {
            if (i < 0 || hasUserRestriction("no_debugging_features", i)) {
                throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Shell does not have permission to access user "));
            }
        }
    }

    public final void finishUserBoot(UserState userState, ActivityManagerService.AnonymousClass12 anonymousClass12) {
        byte[] bArr;
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30078, identifier);
        showEventLog(identifier, userState.state, 0, "finishUserBoot", "NULL");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(identifier, "Finishing user boot ", "ActivityManager");
        synchronized (this.mLock) {
            try {
                if (this.mStartedUsers.get(identifier) != userState) {
                    return;
                }
                if (userState.setState(0, 1)) {
                    this.mInjector.getUserManager().mUserJourneyLogger.logUserLifecycleEvent(identifier, 4, 0);
                    this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                    if (identifier == 0) {
                        Injector injector = this.mInjector;
                        if (!injector.mService.mSystemServiceManager.mRuntimeRestarted) {
                            injector.getClass();
                            IPackageManager packageManager = AppGlobals.getPackageManager();
                            try {
                                if (!packageManager.isFirstBoot()) {
                                    if (!packageManager.isDeviceUpgrading()) {
                                        long elapsedRealtime = SystemClock.elapsedRealtime();
                                        FrameworkStatsLog.write(240, 12, elapsedRealtime);
                                        if (elapsedRealtime > 120000) {
                                            Slogf.wtf("SystemServerTiming", "finishUserBoot took too long. elapsedTimeMs=" + elapsedRealtime);
                                        }
                                    }
                                }
                            } catch (RemoteException e) {
                                throw e.rethrowFromSystemServer();
                            }
                        }
                    }
                    if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                        Handler handler = this.mHandler;
                        handler.sendMessage(handler.obtainMessage(110, identifier, 0));
                        if (this.mAllowUserUnlocking) {
                            sendLockedBootCompletedBroadcast(anonymousClass12, identifier);
                        }
                    }
                    showEventLog(identifier, userState.state, 1, "finishUserBoot", "send LOCKED BOOT COMPLETED");
                }
                UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(identifier);
                if (profileParent == null) {
                    maybeUnlockUser(identifier);
                } else if (isUserRunning(profileParent.id, 4)) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(identifier, "User ", " (parent ");
                    m.append(profileParent.id);
                    m.append("): attempting unlock because parent is unlocked");
                    Slogf.d("ActivityManager", m.toString());
                    if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                        DualDarUserController dualDarUserController = DualDarUserController.getInstance(this.mInjector);
                        int i = profileParent.id;
                        Injector injector2 = dualDarUserController.mInjector;
                        KeyguardManager keyguardManager = (KeyguardManager) injector2.mService.mContext.getSystemService(KeyguardManager.class);
                        DDLog.d("DualDAR::DualDarUserController", "fetchOuterLayerKey()", new Object[0]);
                        try {
                            bArr = DualDARController.getInstance(injector2.mService.mContext).fetchOuterLayerKey(identifier);
                        } catch (Exception e2) {
                            DDLog.e("DualDAR::DualDarUserController", "Exception in fetchOuterLayerKey() : " + e2.toString(), new Object[0]);
                            bArr = null;
                        }
                        if (bArr != null) {
                            DDLog.d("DualDAR::DualDarUserController", VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "Trying to unlock DualDAR user after userStart "), new Object[0]);
                            VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
                            if (StateMachine.getCurrentState(identifier) == State.DEVICE_UNLOCK_DATA_UNLOCK) {
                                DDLog.d("DualDAR::DualDarUserController", VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "Password2Auth has already been completed for: "), new Object[0]);
                                verifyCredentialResponse = VerifyCredentialResponse.OK;
                            } else if (!keyguardManager.isDeviceSecure(identifier)) {
                                DDLog.d("DualDAR::DualDarUserController", VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "Do Password2Auth with null credential for: "), new Object[0]);
                                DDLog.d("DualDAR::DualDarUserController", "onPassword2Auth()", new Object[0]);
                                if (!SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                                    DDLog.e("DualDAR::DualDarUserController", VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "User is not DualDAR eligible. so no need to verify DualDAR Passwords"), new Object[0]);
                                    verifyCredentialResponse = VerifyCredentialResponse.OK;
                                } else if (DualDARController.getInstance(injector2.mService.mContext).onPassword2Auth(identifier, (byte[]) null)) {
                                    DDLog.d("DualDAR::DualDarUserController", "onPassword2Auth completed successfully", new Object[0]);
                                    StateMachine.processEvent(identifier, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
                                    verifyCredentialResponse = VerifyCredentialResponse.OK;
                                } else {
                                    DDLog.e("DualDAR::DualDarUserController", "Authentication Failure by dual dar client", new Object[0]);
                                }
                            }
                            if (verifyCredentialResponse != VerifyCredentialResponse.OK || DualDarManager.isOnDeviceOwner(identifier)) {
                                DDLog.e("DualDAR::DualDarUserController", "Default Authentication Failure by DualDAR client", new Object[0]);
                            } else {
                                DDLog.d("DualDAR::DualDarUserController", VibrationParam$1$$ExternalSyntheticOutline0.m(identifier, "fetch outer layer key and unlock DualDAR user "), new Object[0]);
                                maybeUnlockUser(identifier);
                            }
                        } else {
                            if (!keyguardManager.isDeviceLocked(i)) {
                                DDLog.e("DualDAR::DualDarUserController", BinaryTransparencyService$$ExternalSyntheticOutline0.m(identifier, "This should theoretically never happen. Failed to unlock DualDAR user: ", " something went wrong while fetching OLK event though user0 is unlocked."), new Object[0]);
                            }
                            DDLog.i("DualDAR::DualDarUserController", "Device is still locked. Do not unlock DualDAR user, yet", new Object[0]);
                        }
                    } else {
                        maybeUnlockUser(identifier);
                    }
                } else {
                    StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(identifier, "User ", " (parent ");
                    m2.append(profileParent.id);
                    m2.append("): delaying unlock because parent is locked");
                    Slogf.d("ActivityManager", m2.toString());
                }
                showEventLog(identifier, userState.state, 2, "finishUserBoot", "NULL");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void finishUserStopped(UserState userState, boolean z) {
        ArrayList arrayList;
        ArrayList arrayList2;
        int i;
        boolean z2;
        boolean z3;
        TimingsTraceAndSlog timingsTraceAndSlog;
        ArrayList arrayList3;
        ArrayList arrayList4;
        TimingsTraceAndSlog timingsTraceAndSlog2;
        UserInfo profileParent;
        boolean z4;
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30074, identifier);
        UserInfo userInfo = getUserInfo(identifier);
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList(userState.mStopCallbacks);
                arrayList2 = new ArrayList(userState.mKeyEvictedCallbacks);
                if (this.mStartedUsers.get(identifier) == userState && userState.state == 5) {
                    Slogf.i("ActivityManager", "Removing user state from UserController.mStartedUsers for user #" + identifier + " as a result of user being stopped");
                    this.mStartedUsers.remove(identifier);
                    this.mUserLru.remove(Integer.valueOf(identifier));
                    updateStartedUserArrayLU();
                    if (!z || arrayList2.isEmpty()) {
                        z4 = z;
                    } else {
                        Slogf.wtf("ActivityManager", "Delayed locking enabled while KeyEvictedCallbacks not empty, userId:" + identifier + " callbacks:" + arrayList2);
                        z4 = false;
                    }
                    i = updateUserToLockLU(identifier, z4);
                    if (i == -10000) {
                        z3 = false;
                        z2 = true;
                    } else {
                        z2 = true;
                        z3 = true;
                    }
                }
                i = identifier;
                z2 = false;
                z3 = true;
            } finally {
            }
        }
        TimingsTraceAndSlog timingsTraceAndSlog3 = new TimingsTraceAndSlog();
        if (z2) {
            Slogf.i("ActivityManager", "Removing user state from UserManager.mUserStates for user #" + identifier + " as a result of user being stopped");
            this.mInjector.getUserManagerInternal().removeUserState(identifier);
            this.mInjector.getClass();
            ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).onUserStopped(identifier);
            timingsTraceAndSlog3.traceBegin("stopPackagesOfStoppedUser-" + identifier + "-[stopUser]");
            Injector injector = this.mInjector;
            ActivityManagerService activityManagerService = injector.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    try {
                        timingsTraceAndSlog = timingsTraceAndSlog3;
                        arrayList3 = arrayList2;
                        arrayList4 = arrayList;
                        injector.mService.forceStopPackageLocked(null, -1, false, false, true, false, false, identifier, "finish user");
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                            Intent intent = new Intent("android.intent.action.USER_STOPPED");
                            intent.addFlags(1342177280);
                            intent.putExtra("android.intent.extra.user_handle", identifier);
                            this.mInjector.broadcastIntent(intent, null, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), -1);
                            UserInfo userInfo2 = getUserInfo(identifier);
                            if (userInfo2 != null && userInfo2.isProfile() && (profileParent = this.mInjector.getUserManager().getProfileParent(identifier)) != null) {
                                broadcastProfileAccessibleStateChanged(identifier, profileParent.id, "android.intent.action.PROFILE_INACCESSIBLE");
                            }
                        }
                        timingsTraceAndSlog.traceEnd();
                    } catch (Throwable th) {
                        th = th;
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } else {
            timingsTraceAndSlog = timingsTraceAndSlog3;
            arrayList3 = arrayList2;
            arrayList4 = arrayList;
        }
        Iterator it = arrayList4.iterator();
        while (it.hasNext()) {
            IStopUserCallback iStopUserCallback = (IStopUserCallback) it.next();
            if (z2) {
                try {
                    timingsTraceAndSlog2 = timingsTraceAndSlog;
                    try {
                        timingsTraceAndSlog2.traceBegin("stopCallbacks.userStopped-" + identifier + "-[stopUser]");
                        iStopUserCallback.userStopped(identifier);
                        timingsTraceAndSlog2.traceEnd();
                    } catch (RemoteException unused) {
                    }
                } catch (RemoteException unused2) {
                    timingsTraceAndSlog2 = timingsTraceAndSlog;
                }
            } else {
                timingsTraceAndSlog2 = timingsTraceAndSlog;
                timingsTraceAndSlog2.traceBegin("stopCallbacks.userStopAborted-" + identifier + "-[stopUser]");
                iStopUserCallback.userStopAborted(identifier);
                timingsTraceAndSlog2.traceEnd();
            }
            timingsTraceAndSlog = timingsTraceAndSlog2;
        }
        TimingsTraceAndSlog timingsTraceAndSlog4 = timingsTraceAndSlog;
        if (z2) {
            timingsTraceAndSlog4.traceBegin("systemServiceManagerOnUserStopped-" + identifier + "-[stopUser]");
            SystemServiceManager systemServiceManager = this.mInjector.mService.mSystemServiceManager;
            systemServiceManager.getClass();
            EventLog.writeEvent(30087, identifier);
            systemServiceManager.onUser(identifier, "Cleanup");
            synchronized (systemServiceManager.mTargetUsers) {
                systemServiceManager.mTargetUsers.remove(identifier);
            }
            timingsTraceAndSlog4.traceEnd();
            timingsTraceAndSlog4.traceBegin("taskSupervisorRemoveUser-" + identifier + "-[stopUser]");
            ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) this.mInjector.mService.mAtmInternal;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRootWindowContainer.mUserRootTaskInFront.delete(identifier);
                    ActivityTaskManagerService.this.mPackageConfigPersister.removeUser(identifier);
                } catch (Throwable th3) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th3;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            timingsTraceAndSlog4.traceEnd();
            if (userInfo.isEphemeral() && !userInfo.preCreated) {
                this.mInjector.getUserManager().removeUserEvenWhenDisallowed(identifier);
            }
            UserJourneyLogger.UserJourneySession logUserJourneyFinishWithError = this.mInjector.getUserManager().mUserJourneyLogger.logUserJourneyFinishWithError(-1, userInfo, 5, -1);
            if (logUserJourneyFinishWithError != null) {
                this.mHandler.removeMessages(200, logUserJourneyFinishWithError);
            }
            if (z3) {
                FgThread.getHandler().post(new UserController$$ExternalSyntheticLambda2(this, i, arrayList3, 1));
            }
            resumePendingUserStarts(identifier);
        } else {
            UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserManager().mUserJourneyLogger.finishAndClearIncompleteUserJourney(identifier, 5);
            if (finishAndClearIncompleteUserJourney != null) {
                this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
            }
        }
        if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
            DualDARController.getInstance(DualDarUserController.getInstance(this.mInjector).mInjector.mService.mContext).onUserStopped(identifier);
        }
    }

    public final void finishUserUnlockedCompleted(UserState userState) {
        final int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30072, identifier);
        showEventLog(identifier, userState.state, 2, "finishUserUnlockedCompleted", "NULL");
        DeviceIdleController$$ExternalSyntheticOutline0.m(identifier, "UserController event: finishUserUnlockedCompleted(", ")", "ActivityManager");
        synchronized (this.mLock) {
            try {
                if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) != userState) {
                    return;
                }
                UserInfo userInfo = getUserInfo(identifier);
                if (userInfo == null) {
                    return;
                }
                if (!StorageManager.isCeStorageUnlocked(identifier)) {
                    SystemProperties.set("dev.boot." + identifier + ".user_unlocked", "FAIL-finishUserUnlockedCompleted");
                    Slog.i("ActivityManager", "!@Boot: StorageManager Unlocked FAIL, finishUserUnlockedCompleted");
                    return;
                }
                showEventLog(identifier, userState.state, 1, "finishUserUnlockedCompleted", "OK StorageManager.isUserKeyUnlocked");
                this.mInjector.getUserManager().onUserLoggedIn(identifier);
                final UserController$$ExternalSyntheticLambda3 userController$$ExternalSyntheticLambda3 = new UserController$$ExternalSyntheticLambda3(this, userInfo, 1);
                if (!userInfo.isInitialized()) {
                    Slogf.d("ActivityManager", "Initializing user #" + identifier);
                    if (userInfo.preCreated) {
                        userController$$ExternalSyntheticLambda3.run();
                    } else if (identifier != 0) {
                        this.mInjector.broadcastIntent(BatteryService$$ExternalSyntheticOutline0.m(285212672, "android.intent.action.USER_INITIALIZE"), new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.2
                            public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
                                userController$$ExternalSyntheticLambda3.run();
                            }
                        }, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), identifier);
                    }
                }
                if (userInfo.preCreated) {
                    Slogf.i("ActivityManager", "Stopping pre-created user " + userInfo.toFullString());
                    stopUser(userInfo.id, true, false, null, null);
                    return;
                }
                this.mInjector.getClass();
                AppWidgetManagerInternal appWidgetManagerInternal = (AppWidgetManagerInternal) LocalServices.getService(AppWidgetManagerInternal.class);
                if (appWidgetManagerInternal != null) {
                    FgThread.getHandler().post(new UserController$$ExternalSyntheticLambda5(identifier, 2, appWidgetManagerInternal));
                }
                this.mHandler.obtainMessage(105, identifier, 0).sendToTarget();
                if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && userInfo.isPrivateProfile()) {
                    Slogf.i("ActivityManager", "Skipping BOOT_COMPLETED for private profile user #" + identifier);
                    return;
                }
                Slogf.i("ActivityManager", "Posting BOOT_COMPLETED user #" + identifier);
                if (identifier == 0) {
                    Injector injector = this.mInjector;
                    if (!injector.mService.mSystemServiceManager.mRuntimeRestarted) {
                        injector.getClass();
                        IPackageManager packageManager = AppGlobals.getPackageManager();
                        try {
                            if (!packageManager.isFirstBoot()) {
                                if (!packageManager.isDeviceUpgrading()) {
                                    FrameworkStatsLog.write(240, 13, SystemClock.elapsedRealtime());
                                }
                            }
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
                final Intent intent = new Intent("android.intent.action.BOOT_COMPLETED", (Uri) null);
                intent.putExtra("android.intent.extra.user_handle", identifier);
                intent.addFlags(-1996488704);
                final int callingUid = Binder.getCallingUid();
                final int callingPid = Binder.getCallingPid();
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserController userController = UserController.this;
                        Intent intent2 = intent;
                        int i = identifier;
                        int i2 = callingUid;
                        int i3 = callingPid;
                        UserController.Injector injector2 = userController.mInjector;
                        UserController.AnonymousClass5 anonymousClass5 = new UserController.AnonymousClass5(userController, i);
                        String[] strArr = {"android.permission.RECEIVE_BOOT_COMPLETED"};
                        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                        long bootTimeTempAllowListDuration = activityManagerInternal != null ? activityManagerInternal.getBootTimeTempAllowListDuration() : 10000L;
                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                        makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, 200, "");
                        injector2.broadcastIntent(intent2, anonymousClass5, strArr, makeBasic.toBundle(), ActivityManagerService.MY_PID, i2, i3, i);
                    }
                });
                showEventLog(identifier, userState.state, 2, "finishUserUnlockedCompleted", "send BOOT COMPLETED and complete NULL");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getCurrentOrTargetUserIdLU() {
        return this.mTargetUserId != -10000 ? this.mTargetUserId : this.mCurrentUserId;
    }

    public final int[] getCurrentProfileIds() {
        int[] iArr;
        synchronized (this.mLock) {
            iArr = this.mCurrentProfileIds;
        }
        return iArr;
    }

    public final UserInfo getCurrentUser() {
        UserInfo userInfo;
        checkGetCurrentUserPermissions();
        if (this.mTargetUserId == -10000) {
            return getUserInfo(this.mCurrentUserId);
        }
        synchronized (this.mLock) {
            userInfo = getUserInfo(getCurrentOrTargetUserIdLU());
        }
        return userInfo;
    }

    public final int getCurrentUserId() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        return i;
    }

    public List getRunningUsersLU() {
        int i;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mUserLru.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            UserState userState = (UserState) this.mStartedUsers.get(num.intValue());
            if (userState != null && (i = userState.state) != 4 && i != 5) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public final UserState getStartedUserState(int i) {
        UserState userState;
        synchronized (this.mLock) {
            userState = (UserState) this.mStartedUsers.get(i);
        }
        return userState;
    }

    public final UserInfo getUserInfo(int i) {
        return this.mInjector.getUserManager().getUserInfo(i);
    }

    public final int[] getUsers() {
        UserManagerService userManager = this.mInjector.getUserManager();
        return userManager != null ? userManager.getUserIds() : new int[]{0};
    }

    public final int handleIncomingUser(int i, int i2, int i3, boolean z, int i4, String str, String str2) {
        int i5;
        String str3;
        String str4;
        int userId = UserHandle.getUserId(i2);
        if (userId == i3) {
            return i3;
        }
        if ((SemDualAppManager.isDualAppId(userId) && i3 == 0) || (SemDualAppManager.isDualAppId(i3) && userId == 0)) {
            return i3;
        }
        int currentUserId = (i3 == -2 || i3 == -3) ? getCurrentUserId() : i3;
        if (i2 != 0 && i2 != 1000 && i2 != 5250) {
            boolean isSameProfileGroup = isSameProfileGroup(userId, currentUserId);
            boolean z2 = true;
            if (this.mInjector.mService.mAtmInternal.isCallerRecents(i2) && isSameProfileGroup) {
                i5 = 2;
                str4 = "android.permission.INTERACT_ACROSS_PROFILES";
            } else {
                this.mInjector.getClass();
                i5 = 2;
                if (ActivityManagerService.checkComponentPermission(i, i2, "android.permission.INTERACT_ACROSS_USERS_FULL", 0, -1, true) == 0) {
                    str4 = "android.permission.INTERACT_ACROSS_PROFILES";
                } else if (i4 == 2) {
                    z2 = false;
                    str4 = "android.permission.INTERACT_ACROSS_PROFILES";
                } else {
                    if (i4 == 3 && isSameProfileGroup) {
                        str3 = "android.permission.INTERACT_ACROSS_PROFILES";
                        if (PermissionChecker.checkPermissionForPreflight(this.mInjector.mService.mContext, str3, i, i2, str2) == 0) {
                            str4 = str3;
                        }
                    } else {
                        str3 = "android.permission.INTERACT_ACROSS_PROFILES";
                    }
                    this.mInjector.getClass();
                    str4 = str3;
                    if (ActivityManagerService.checkComponentPermission(i, i2, "android.permission.INTERACT_ACROSS_USERS", 0, -1, true) != 0) {
                        z2 = false;
                    } else if (i4 != 0 && i4 != 3) {
                        if (i4 != 1) {
                            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i4, "Unknown mode: "));
                        }
                        z2 = isSameProfileGroup;
                    }
                }
                z2 = true;
            }
            if (!z2) {
                if (i3 != -3) {
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("Permission Denial: ");
                    sb.append(str);
                    if (str2 != null) {
                        sb.append(" from ");
                        sb.append(str2);
                    }
                    sb.append(" asks to run as user ");
                    sb.append(i3);
                    sb.append(" but is calling from uid ");
                    UserHandle.formatUid(sb, i2);
                    sb.append("; this requires ");
                    sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
                    if (i4 != i5) {
                        if (i4 == 0 || i4 == 3 || (i4 == 1 && isSameProfileGroup)) {
                            sb.append(" or ");
                            sb.append("android.permission.INTERACT_ACROSS_USERS");
                        }
                        if (isSameProfileGroup && i4 == 3) {
                            sb.append(" or ");
                            sb.append(str4);
                        }
                    }
                    String sb2 = sb.toString();
                    Slogf.w("ActivityManager", sb2);
                    throw new SecurityException(sb2);
                }
                if (z && userId < 0) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(userId, "Call does not support special user #"));
                }
                if (i2 == 2000 || userId < 0 || !hasUserRestriction("no_debugging_features", userId)) {
                    return userId;
                }
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userId, "Shell does not have permission to access user ", "\n ");
                m.append(Debug.getCallers(3));
                throw new SecurityException(m.toString());
            }
        }
        userId = currentUserId;
        if (z) {
        }
        if (i2 == 2000) {
        }
        return userId;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r8v18, types: [com.android.server.am.UserController$$ExternalSyntheticLambda18] */
    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        FileInputStream fileInputStream;
        UserInfo profileParent;
        String str;
        String str2;
        int i = 0;
        switch (message.what) {
            case 10:
                dispatchUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                return false;
            case 20:
                continueUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                return false;
            case 30:
                timeoutUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                return false;
            case 40:
                startProfiles();
                return false;
            case 50:
                this.mInjector.mService.mBatteryStatsService.noteEvent(32775, Integer.toString(message.arg1), message.arg1);
                logUserJourneyBegin(message.arg1, 3);
                this.mInjector.onUserStarting(message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 1, 5000);
                this.mInjector.getUserManager().mUserJourneyLogger.logUserJourneyFinishWithError(-1, getUserInfo(message.arg1), 3, -1);
                this.mInjector.getClass();
                return false;
            case 60:
                this.mInjector.mService.mBatteryStatsService.noteEvent(16392, Integer.toString(message.arg2), message.arg2);
                this.mInjector.mService.mBatteryStatsService.noteEvent(32776, Integer.toString(message.arg1), message.arg1);
                this.mInjector.mService.mSystemServiceManager.onUserSwitching(message.arg2, message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 4, 5000);
                return false;
            case 70:
                int i2 = message.arg1;
                int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        this.mUserSwitchObservers.getBroadcastItem(i3).onForegroundProfileSwitch(i2);
                    } catch (RemoteException unused) {
                    }
                }
                this.mUserSwitchObservers.finishBroadcast();
                return false;
            case 80:
                dispatchUserSwitchComplete(message.arg1, message.arg2);
                UserJourneyLogger.UserJourneySession logUserSwitchJourneyFinish = this.mInjector.getUserManager().mUserJourneyLogger.logUserSwitchJourneyFinish(message.arg1, getUserInfo(message.arg2));
                if (logUserSwitchJourneyFinish != null) {
                    this.mHandler.removeMessages(200, logUserSwitchJourneyFinish);
                }
                return false;
            case 90:
                int i4 = message.arg1;
                int i5 = message.arg2;
                synchronized (this.mLock) {
                    try {
                        ArraySet arraySet = this.mTimeoutUserSwitchCallbacks;
                        if (arraySet != null && !arraySet.isEmpty()) {
                            Slogf.wtf("ActivityManager", "User switch timeout: from " + i4 + " to " + i5 + ". Observers that didn't respond: " + this.mTimeoutUserSwitchCallbacks);
                            this.mTimeoutUserSwitchCallbacks = null;
                        }
                    } finally {
                    }
                }
                return false;
            case 100:
                int i6 = message.arg1;
                showEventLog(i6, -1, 0, "SYSTEM_USER_UNLOCK_MSG", "NULL");
                SystemServiceManager systemServiceManager = this.mInjector.mService.mSystemServiceManager;
                systemServiceManager.getClass();
                EventLog.writeEvent(30084, i6);
                systemServiceManager.onUser(i6, "Unlocking");
                showEventLog(i6, -1, 1, "SYSTEM_USER_UNLOCK_MSG", "Done mSystemServiceManager.onUserUnlocking");
                FgThread.getHandler().post(new UserController$$ExternalSyntheticLambda5(i6, 0, this));
                this.mInjector.getUserManager().mUserJourneyLogger.logUserLifecycleEvent(message.arg1, 5, 2);
                this.mInjector.getUserManager().mUserJourneyLogger.logUserLifecycleEvent(message.arg1, 6, 1);
                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("finishUserUnlocked-" + i6);
                final UserState userState = (UserState) message.obj;
                int identifier = userState.mHandle.getIdentifier();
                EventLog.writeEvent(30071, identifier);
                showEventLog(identifier, userState.state, 0, "finishUserUnlocked", "NULL");
                showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "Try StorageManager.isUserKeyUnlocked");
                Slog.d("ActivityManager", "UserController event: finishUserUnlocked(" + identifier + ")");
                if (StorageManager.isCeStorageUnlocked(identifier)) {
                    showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "OK StorageManager.isUserKeyUnlocked");
                    synchronized (this.mLock) {
                        try {
                            if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) == userState) {
                                if (userState.setState(2, 3)) {
                                    this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                                    userState.mUnlockProgress.finish();
                                    if (identifier == 0) {
                                        this.mInjector.mService.startPersistentApps(262144);
                                    }
                                    final ContentProviderHelper contentProviderHelper = this.mInjector.mService.mCpHelper;
                                    ActivityManagerProcLock activityManagerProcLock = contentProviderHelper.mService.mProcLock;
                                    ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerProcLock) {
                                        try {
                                            ArrayMap map = contentProviderHelper.mService.mProcessList.mProcessNames.getMap();
                                            int size = map.size();
                                            int i7 = 0;
                                            while (i7 < size) {
                                                SparseArray sparseArray = (SparseArray) map.valueAt(i7);
                                                int size2 = sparseArray.size();
                                                for (int i8 = i; i8 < size2; i8++) {
                                                    final ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i8);
                                                    if (processRecord.userId == identifier && processRecord.mThread != null && !processRecord.mUnlocked) {
                                                        processRecord.mPkgList.forEachPackage(new Consumer() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda4
                                                            /* JADX WARN: Removed duplicated region for block: B:23:0x0056 A[Catch: RemoteException -> 0x009b, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x009b, blocks: (B:3:0x0009, B:5:0x001a, B:7:0x0022, B:9:0x0029, B:11:0x0036, B:15:0x003e, B:17:0x0044, B:21:0x004c, B:23:0x0056, B:31:0x006c, B:34:0x0084), top: B:2:0x0009 }] */
                                                            /* JADX WARN: Removed duplicated region for block: B:28:0x0066 A[ADDED_TO_REGION] */
                                                            @Override // java.util.function.Consumer
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                            */
                                                            public final void accept(java.lang.Object r13) {
                                                                /*
                                                                    r12 = this;
                                                                    com.android.server.am.ContentProviderHelper r0 = com.android.server.am.ContentProviderHelper.this
                                                                    com.android.server.am.ProcessRecord r12 = r2
                                                                    java.lang.String r13 = (java.lang.String) r13
                                                                    r0.getClass()
                                                                    android.content.pm.IPackageManager r1 = android.app.AppGlobals.getPackageManager()     // Catch: android.os.RemoteException -> L9b
                                                                    int r2 = r12.userId     // Catch: android.os.RemoteException -> L9b
                                                                    r3 = 262152(0x40008, double:1.295203E-318)
                                                                    android.content.pm.PackageInfo r13 = r1.getPackageInfo(r13, r3, r2)     // Catch: android.os.RemoteException -> L9b
                                                                    android.app.IApplicationThread r1 = r12.mThread     // Catch: android.os.RemoteException -> L9b
                                                                    if (r13 == 0) goto L9b
                                                                    android.content.pm.ProviderInfo[] r2 = r13.providers     // Catch: android.os.RemoteException -> L9b
                                                                    boolean r2 = com.android.internal.util.ArrayUtils.isEmpty(r2)     // Catch: android.os.RemoteException -> L9b
                                                                    if (r2 != 0) goto L9b
                                                                    android.content.pm.ProviderInfo[] r13 = r13.providers     // Catch: android.os.RemoteException -> L9b
                                                                    int r2 = r13.length     // Catch: android.os.RemoteException -> L9b
                                                                    r3 = 0
                                                                    r4 = r3
                                                                L27:
                                                                    if (r4 >= r2) goto L9b
                                                                    r5 = r13[r4]     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r6 = r5.processName     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r7 = r12.processName     // Catch: android.os.RemoteException -> L9b
                                                                    boolean r6 = java.util.Objects.equals(r6, r7)     // Catch: android.os.RemoteException -> L9b
                                                                    r7 = 1
                                                                    if (r6 != 0) goto L3d
                                                                    boolean r6 = r5.multiprocess     // Catch: android.os.RemoteException -> L9b
                                                                    if (r6 == 0) goto L3b
                                                                    goto L3d
                                                                L3b:
                                                                    r6 = r3
                                                                    goto L3e
                                                                L3d:
                                                                    r6 = r7
                                                                L3e:
                                                                    boolean r8 = r0.isSingletonOrSystemUserOnly(r5)     // Catch: android.os.RemoteException -> L9b
                                                                    if (r8 == 0) goto L4b
                                                                    int r8 = r12.userId     // Catch: android.os.RemoteException -> L9b
                                                                    if (r8 != 0) goto L49
                                                                    goto L4b
                                                                L49:
                                                                    r8 = r3
                                                                    goto L4c
                                                                L4b:
                                                                    r8 = r7
                                                                L4c:
                                                                    android.content.pm.ApplicationInfo r9 = r5.applicationInfo     // Catch: android.os.RemoteException -> L9b
                                                                    boolean r9 = r9.isInstantApp()     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r10 = r5.splitName     // Catch: android.os.RemoteException -> L9b
                                                                    if (r10 == 0) goto L62
                                                                    android.content.pm.ApplicationInfo r11 = r5.applicationInfo     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String[] r11 = r11.splitNames     // Catch: android.os.RemoteException -> L9b
                                                                    boolean r10 = com.android.internal.util.ArrayUtils.contains(r11, r10)     // Catch: android.os.RemoteException -> L9b
                                                                    if (r10 == 0) goto L61
                                                                    goto L62
                                                                L61:
                                                                    r7 = r3
                                                                L62:
                                                                    java.lang.String r10 = "ContentProviderHelper"
                                                                    if (r6 == 0) goto L84
                                                                    if (r8 == 0) goto L84
                                                                    if (r9 == 0) goto L6c
                                                                    if (r7 == 0) goto L84
                                                                L6c:
                                                                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L9b
                                                                    r6.<init>()     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r7 = "Installing "
                                                                    r6.append(r7)     // Catch: android.os.RemoteException -> L9b
                                                                    r6.append(r5)     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r6 = r6.toString()     // Catch: android.os.RemoteException -> L9b
                                                                    android.util.Log.v(r10, r6)     // Catch: android.os.RemoteException -> L9b
                                                                    r1.scheduleInstallProvider(r5)     // Catch: android.os.RemoteException -> L9b
                                                                    goto L98
                                                                L84:
                                                                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L9b
                                                                    r6.<init>()     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r7 = "Skipping "
                                                                    r6.append(r7)     // Catch: android.os.RemoteException -> L9b
                                                                    r6.append(r5)     // Catch: android.os.RemoteException -> L9b
                                                                    java.lang.String r5 = r6.toString()     // Catch: android.os.RemoteException -> L9b
                                                                    android.util.Log.v(r10, r5)     // Catch: android.os.RemoteException -> L9b
                                                                L98:
                                                                    int r4 = r4 + 1
                                                                    goto L27
                                                                L9b:
                                                                    return
                                                                */
                                                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda4.accept(java.lang.Object):void");
                                                            }
                                                        });
                                                    }
                                                }
                                                i7++;
                                                i = 0;
                                            }
                                        } finally {
                                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        }
                                    }
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                                        Intent intent = new Intent("android.intent.action.USER_UNLOCKED");
                                        intent.putExtra("android.intent.extra.user_handle", identifier);
                                        intent.addFlags(1342177280);
                                        this.mInjector.broadcastIntent(intent, null, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), identifier);
                                        showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "send USER UNLOCKED");
                                    }
                                    UserInfo userInfo = getUserInfo(identifier);
                                    if (userInfo.isProfile() && (profileParent = this.mInjector.getUserManager().getProfileParent(identifier)) != null) {
                                        broadcastProfileAccessibleStateChanged(identifier, profileParent.id, "android.intent.action.PROFILE_ACCESSIBLE");
                                        if (userInfo.isManagedProfile() || userInfo.isCloneProfile()) {
                                            Intent intent2 = new Intent("android.intent.action.MANAGED_PROFILE_UNLOCKED");
                                            intent2.putExtra("android.intent.extra.USER", UserHandle.of(identifier));
                                            intent2.addFlags(1342177280);
                                            this.mInjector.broadcastIntent(intent2, null, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), profileParent.id);
                                        }
                                    }
                                    UserInfo userInfo2 = getUserInfo(identifier);
                                    this.mInjector.mService.mExt.getClass();
                                    String str3 = "DUMMY";
                                    String str4 = ActivityManagerServiceExt.CSC_VERSION;
                                    if ("DUMMY".equals(str4)) {
                                        Slog.wtf("ActivityManagerServiceExt", "csc version of property is wrong", new RuntimeException());
                                    }
                                    try {
                                        fileInputStream = new FileInputStream(ActivityManagerServiceExt.PRE_BOOT_CSC_FILE);
                                    } catch (FileNotFoundException unused2) {
                                    } catch (IOException e) {
                                        Slog.w("ActivityManagerServiceExt", "Failure reading pre boot csc", e);
                                    }
                                    try {
                                        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(fileInputStream, 1024));
                                        try {
                                            str3 = dataInputStream.readUTF();
                                            dataInputStream.close();
                                            fileInputStream.close();
                                            final boolean z = !str4.equals(str3);
                                            if (!Objects.equals(userInfo2.lastLoggedInFingerprint, PackagePartitions.FINGERPRINT) || SystemProperties.getBoolean("persist.pm.mock-upgrade", false) || z) {
                                                boolean z2 = userInfo2.isManagedProfile() || userInfo2.isCloneProfile();
                                                Injector injector = this.mInjector;
                                                ?? r8 = new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda18
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        UserController userController = UserController.this;
                                                        boolean z3 = z;
                                                        UserState userState2 = userState;
                                                        if (z3) {
                                                            userController.mInjector.mService.mExt.getClass();
                                                            try {
                                                                FileOutputStream fileOutputStream = new FileOutputStream(ActivityManagerServiceExt.PRE_BOOT_CSC_FILE);
                                                                try {
                                                                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream, 1024));
                                                                    try {
                                                                        dataOutputStream.writeUTF(ActivityManagerServiceExt.CSC_VERSION);
                                                                        dataOutputStream.flush();
                                                                        dataOutputStream.close();
                                                                        fileOutputStream.close();
                                                                    } finally {
                                                                    }
                                                                } finally {
                                                                }
                                                            } catch (IOException e2) {
                                                                Slog.w("ActivityManagerServiceExt", "Failure writing last done pre-boot receivers", e2);
                                                                ActivityManagerServiceExt.PRE_BOOT_CSC_FILE.delete();
                                                            }
                                                        }
                                                        userController.finishUserUnlockedCompleted(userState2);
                                                    }
                                                };
                                                injector.getClass();
                                                EventLog.writeEvent(30081, Integer.valueOf(identifier), "android.intent.action.PRE_BOOT_COMPLETED");
                                                new Injector.AnonymousClass1(injector.mService, identifier, z2, r8).sendNext();
                                                showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "OK sendPreBootBroadcast");
                                            } else {
                                                finishUserUnlockedCompleted(userState);
                                            }
                                            showEventLog(identifier, userState.state, 2, "finishUserUnlocked", "NULL");
                                            FgThread.getHandler().post(new UserController$$ExternalSyntheticLambda16(1));
                                        } finally {
                                        }
                                    } finally {
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                } else {
                    SystemProperties.set("dev.boot." + identifier + ".user_unlocked", "FAIL-finishUserUnlocked");
                    Slog.i("ActivityManager", "!@Boot: StorageManager Unlocked FAIL, finishUserUnlocked");
                    if (!this.mLockPatternUtils.isSecure(identifier)) {
                        this.mInjector.mService.mExt.getClass();
                        SystemProperties.set("dumpstate.process", "unlockfail");
                        SystemProperties.set("bugreport.mode", "booting_delay");
                        SystemProperties.set("ctl.start", "bugreportm");
                    }
                }
                timingsTraceAndSlog.traceEnd();
                showEventLog(i6, -1, 2, "SYSTEM_USER_UNLOCK_MSG", "NULL");
                return false;
            case 105:
                SystemServiceManager systemServiceManager2 = this.mInjector.mService.mSystemServiceManager;
                int i9 = message.arg1;
                systemServiceManager2.getClass();
                EventLog.writeEvent(30085, i9);
                systemServiceManager2.onUser(i9, "Unlocked");
                scheduleOnUserCompletedEvent(message.arg1, 2, this.mCurrentUserId != message.arg1 ? 1000 : 5000);
                this.mInjector.getUserManager().mUserJourneyLogger.logUserLifecycleEvent(message.arg1, 6, 2);
                return false;
            case 110:
                int i10 = message.arg1;
                int beginBroadcast2 = this.mUserSwitchObservers.beginBroadcast();
                for (int i11 = 0; i11 < beginBroadcast2; i11++) {
                    try {
                        this.mUserSwitchObservers.getBroadcastItem(i11).onLockedBootComplete(i10);
                    } catch (RemoteException unused3) {
                    }
                }
                this.mUserSwitchObservers.finishBroadcast();
                return false;
            case 120:
                logUserJourneyBegin(message.arg1, 2);
                int i12 = message.arg1;
                if (android.multiuser.Flags.setPowerModeDuringUserSwitch()) {
                    this.mInjector.setPerformancePowerMode(true);
                }
                if (!startUser(i12, 1)) {
                    this.mInjector.mService.mWindowManager.setSwitchingUser(false);
                    this.mUiHandler.post(new UserController$$ExternalSyntheticLambda7(this, new UserController$$ExternalSyntheticLambda11(this, 0)));
                }
                return false;
            case 130:
                completeUserSwitch(message.arg1, message.arg2);
                return false;
            case 140:
                reportOnUserCompletedEvent((Integer) message.obj);
                return false;
            case 150:
                processScheduledStopOfBackgroundUser((Integer) message.obj);
                return false;
            case 200:
                this.mInjector.getUserManager().mUserJourneyLogger.finishAndClearIncompleteUserJourney(message.arg1, message.arg2);
                this.mHandler.removeMessages(200, message.obj);
                return false;
            case 1000:
                Pair pair = (Pair) message.obj;
                logUserJourneyBegin(((UserInfo) pair.second).id, 1);
                Injector injector2 = this.mInjector;
                UserInfo userInfo3 = (UserInfo) pair.first;
                UserInfo userInfo4 = (UserInfo) pair.second;
                synchronized (this.mLock) {
                    str = this.mSwitchingFromSystemUserMessage;
                }
                synchronized (this.mLock) {
                    str2 = this.mSwitchingToSystemUserMessage;
                }
                UserController$$ExternalSyntheticLambda3 userController$$ExternalSyntheticLambda3 = new UserController$$ExternalSyntheticLambda3(this, pair, 2);
                if (injector2.mService.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                    Slogf.w("ActivityManager", "Showing user switch dialog on UserController, it could cause a race condition if it's shown by CarSystemUI as well");
                }
                synchronized (injector2.mUserSwitchingDialogLock) {
                    injector2.dismissUserSwitchingDialog(null);
                    ActivityManagerService activityManagerService = injector2.mService;
                    UserSwitchingDialog userSwitchingDialog = new UserSwitchingDialog(activityManagerService.mContext, userInfo3, userInfo4, str, str2, activityManagerService.mWindowManager);
                    injector2.mUserSwitchingDialog = userSwitchingDialog;
                    userSwitchingDialog.show(userController$$ExternalSyntheticLambda3);
                }
                return false;
            default:
                return false;
        }
    }

    public final boolean hasStartedUserState(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mStartedUsers.get(i) != null;
        }
        return z;
    }

    public final boolean hasUserRestriction(String str, int i) {
        return this.mInjector.getUserManager().hasUserRestriction(str, i);
    }

    public final boolean isSameProfileGroup(int i, int i2) {
        boolean z = true;
        if (i == i2) {
            return true;
        }
        synchronized (this.mLock) {
            int i3 = this.mUserProfileGroupIds.get(i, -10000);
            int i4 = this.mUserProfileGroupIds.get(i2, -10000);
            if (i3 == -10000 || i3 != i4) {
                z = false;
            }
        }
        return z;
    }

    public final boolean isSystemUserStarted() {
        synchronized (this.mLock) {
            try {
                UserState userState = (UserState) this.mStartedUsers.get(0);
                if (userState == null) {
                    return false;
                }
                int i = userState.state;
                return i == 1 || i == 2 || i == 3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isUserOrItsParentRunning(int i) {
        synchronized (this.mLock) {
            try {
                if (isUserRunning(i, 0)) {
                    return true;
                }
                int i2 = this.mUserProfileGroupIds.get(i, -10000);
                if (i2 == -10000) {
                    return false;
                }
                return isUserRunning(i2, 0);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isUserRunning(int i, int i2) {
        UserState startedUserState = getStartedUserState(i);
        if (startedUserState == null) {
            return false;
        }
        if ((i2 & 1) != 0) {
            return true;
        }
        if ((i2 & 2) != 0) {
            int i3 = startedUserState.state;
            return i3 == 0 || i3 == 1;
        }
        if ((i2 & 8) != 0) {
            int i4 = startedUserState.state;
            if (i4 == 2 || i4 == 3) {
                return true;
            }
            if (i4 == 4 || i4 == 5) {
                return StorageManager.isCeStorageUnlocked(i);
            }
            return false;
        }
        if ((i2 & 4) == 0) {
            int i5 = startedUserState.state;
            return (i5 == 4 || i5 == 5) ? false : true;
        }
        int i6 = startedUserState.state;
        if (i6 == 3) {
            return true;
        }
        if (i6 == 4 || i6 == 5) {
            return StorageManager.isCeStorageUnlocked(i);
        }
        return false;
    }

    public final void logUserJourneyBegin(int i, int i2) {
        UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserManager().mUserJourneyLogger.finishAndClearIncompleteUserJourney(i, i2);
        if (finishAndClearIncompleteUserJourney != null) {
            this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
        }
        UserJourneyLogger.UserJourneySession logUserJourneyBegin = this.mInjector.getUserManager().mUserJourneyLogger.logUserJourneyBegin(i, i2);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(200, i, i2, logUserJourneyBegin), 90000L);
    }

    public final void maybeUnlockUser(int i) {
        showEventLog(i, -1, 0, "maybeUnlockUser", "NULL and no exit");
        maybeUnlockUser(i, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0179  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean maybeUnlockUser(int r12, android.os.IProgressListener r13) {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.UserController.maybeUnlockUser(int, android.os.IProgressListener):boolean");
    }

    public final void moveUserToForeground(int i, UserState userState) {
        boolean switchUser;
        ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) this.mInjector.mService.mAtmInternal;
        WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                switchUser = ActivityTaskManagerService.this.mRootWindowContainer.switchUser(i, userState);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (switchUser) {
            this.mInjector.mService.mAtmInternal.startHomeActivity(i, "moveUserToForeground");
        } else {
            this.mInjector.mService.mAtmInternal.resumeTopActivities(false);
        }
        EventLog.writeEvent(30041, i);
    }

    public final void onBootComplete(ActivityManagerService.AnonymousClass12 anonymousClass12) {
        SparseArray clone;
        showEventLog(0, -1, 0, "onBootComplete", "NULL");
        setAllowUserUnlocking(true);
        synchronized (this.mLock) {
            clone = this.mStartedUsers.clone();
        }
        Preconditions.checkArgument(clone.keyAt(0) == 0);
        for (int i = 0; i < clone.size(); i++) {
            int keyAt = clone.keyAt(i);
            UserState userState = (UserState) clone.valueAt(i);
            this.mInjector.getClass();
            if (UserManager.isHeadlessSystemUserMode()) {
                sendLockedBootCompletedBroadcast(anonymousClass12, keyAt);
                maybeUnlockUser(keyAt);
            } else {
                finishUserBoot(userState, anonymousClass12);
            }
        }
        showEventLog(0, -1, 2, "onBootComplete", "NULL");
    }

    public void processScheduledStopOfBackgroundUser(Integer num) {
        int intValue = num.intValue();
        Slogf.d("ActivityManager", "Considering stopping background user %d due to inactivity", num);
        synchronized (this.mLock) {
            try {
                if (getCurrentOrTargetUserIdLU() == intValue) {
                    return;
                }
                if (this.mPendingTargetUserIds.contains(num)) {
                    return;
                }
                Slogf.i("ActivityManager", "Stopping background user %d due to inactivity", num);
                stopUsersLU(intValue, true, true, null, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void reportOnUserCompletedEvent(Integer num) {
        int i;
        int i2;
        SystemService.TargetUser targetUser;
        this.mHandler.removeEqualMessages(140, num);
        synchronized (this.mCompletedEventTypes) {
            i = 0;
            i2 = this.mCompletedEventTypes.get(num.intValue(), 0);
            this.mCompletedEventTypes.delete(num.intValue());
        }
        synchronized (this.mLock) {
            try {
                UserState userState = (UserState) this.mStartedUsers.get(num.intValue());
                if (userState != null && userState.state != 5) {
                    i = 1;
                }
                if (userState != null && userState.state == 3) {
                    i |= 2;
                }
                if (num.intValue() == this.mCurrentUserId) {
                    i |= 4;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slogf.i("ActivityManager", "reportOnUserCompletedEvent(%d): stored=%s, eligible=%s", num, Integer.toBinaryString(i2), Integer.toBinaryString(i));
        int i3 = i2 & i;
        Injector injector = this.mInjector;
        int intValue = num.intValue();
        SystemServiceManager systemServiceManager = injector.mService.mSystemServiceManager;
        systemServiceManager.getClass();
        EventLog.writeEvent(30088, num, Integer.valueOf(i3));
        if (i3 == 0 || (targetUser = systemServiceManager.getTargetUser(intValue)) == null) {
            return;
        }
        systemServiceManager.onUser(TimingsTraceAndSlog.newAsyncLog(), "CompletedEvent", null, targetUser, new SystemService.UserCompletedEventType(i3));
    }

    public final void resumePendingUserStarts(int i) {
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = new ArrayList();
                Iterator it = ((ArrayList) this.mPendingUserStarts).iterator();
                while (it.hasNext()) {
                    PendingUserStart pendingUserStart = (PendingUserStart) it.next();
                    if (pendingUserStart.userId == i) {
                        Slogf.i("ActivityManager", "resumePendingUserStart for" + pendingUserStart);
                        this.mHandler.post(new UserController$$ExternalSyntheticLambda3(this, pendingUserStart, 3));
                        arrayList.add(pendingUserStart);
                    }
                }
                ((ArrayList) this.mPendingUserStarts).removeAll(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void runWithTimeout(Consumer consumer, final int i, final Runnable runnable, final Runnable runnable2, final String str) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        asyncTraceBegin(0, str);
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                AtomicInteger atomicInteger2 = atomicInteger;
                String str2 = str;
                int i2 = i;
                Runnable runnable3 = runnable2;
                if (atomicInteger2.compareAndSet(0, 1)) {
                    UserController.asyncTraceEnd(0, str2);
                    Slogf.w("ActivityManager", "Timeout: %s did not finish in %d ms", str2, Integer.valueOf(i2));
                    runnable3.run();
                }
            }
        }, i);
        consumer.accept(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                AtomicInteger atomicInteger2 = atomicInteger;
                String str2 = str;
                Runnable runnable3 = runnable;
                if (atomicInteger2.compareAndSet(0, 2)) {
                    UserController.asyncTraceEnd(0, str2);
                    runnable3.run();
                }
            }
        });
    }

    public void scheduleOnUserCompletedEvent(int i, int i2, int i3) {
        if (i2 != 0) {
            synchronized (this.mCompletedEventTypes) {
                SparseIntArray sparseIntArray = this.mCompletedEventTypes;
                sparseIntArray.put(i, i2 | sparseIntArray.get(i, 0));
            }
        }
        Integer valueOf = Integer.valueOf(i);
        this.mHandler.removeEqualMessages(140, valueOf);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(140, valueOf), i3);
    }

    public final void scheduleStopOfBackgroundUser(int i) {
        int i2;
        if (android.multiuser.Flags.scheduleStopOfBackgroundUser() && (i2 = this.mBackgroundUserScheduledStopTimeSecs) > 0 && !UserManager.isVisibleBackgroundUsersEnabled() && i != 0) {
            if (i == this.mInjector.getUserManagerInternal().getMainUserId()) {
                Slogf.i("ActivityManager", "Exempting user %d from being stopped due to inactivity by virtue of it being the main user", Integer.valueOf(i));
                return;
            }
            Slogf.d("ActivityManager", "Scheduling to stop user %d in %d seconds", Integer.valueOf(i), Integer.valueOf(i2));
            Integer valueOf = Integer.valueOf(i);
            this.mHandler.removeEqualMessages(150, valueOf);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(150, valueOf), i2 * 1000);
        }
    }

    public final void sendContinueUserSwitchLU(UserState userState, int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("ActivityManager");
        timingsTraceAndSlog.traceBegin("sendContinueUserSwitchLU-" + i + "-to-" + i2);
        this.mCurWaitingUserSwitchCallbacks = null;
        this.mHandler.removeMessages(30);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(20, i, i2, userState));
        timingsTraceAndSlog.traceEnd();
    }

    public final void sendLockedBootCompletedBroadcast(ActivityManagerService.AnonymousClass12 anonymousClass12, int i) {
        UserInfo userInfo;
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && (userInfo = getUserInfo(i)) != null && userInfo.isPrivateProfile()) {
            Slogf.i("ActivityManager", "Skipping LOCKED_BOOT_COMPLETED for private profile user #" + i);
            return;
        }
        Intent intent = new Intent("android.intent.action.LOCKED_BOOT_COMPLETED", (Uri) null);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(-1996488704);
        Injector injector = this.mInjector;
        String[] strArr = {"android.permission.RECEIVE_BOOT_COMPLETED"};
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        long bootTimeTempAllowListDuration = activityManagerInternal != null ? activityManagerInternal.getBootTimeTempAllowListDuration() : 10000L;
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, 202, "");
        injector.broadcastIntent(intent, anonymousClass12, strArr, makeBasic.toBundle(), ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), i);
    }

    public final void sendUserStartedBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                try {
                    if (this.mIsBroadcastSentForSystemUserStarted) {
                        return;
                    } else {
                        this.mIsBroadcastSentForSystemUserStarted = true;
                    }
                } finally {
                }
            }
        }
        Intent intent = new Intent("android.intent.action.USER_STARTED");
        intent.addFlags(1342177280);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, null, null, ActivityManagerService.MY_PID, i2, i3, i);
    }

    public final void sendUserStartingBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                try {
                    if (this.mIsBroadcastSentForSystemUserStarting) {
                        return;
                    } else {
                        this.mIsBroadcastSentForSystemUserStarting = true;
                    }
                } finally {
                }
            }
        }
        Intent intent = new Intent("android.intent.action.USER_STARTING");
        intent.addFlags(1073741824);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, new AnonymousClass8(), new String[]{"android.permission.INTERACT_ACROSS_USERS"}, null, ActivityManagerService.MY_PID, i2, i3, -1);
    }

    public final void sendUserSwitchBroadcasts(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = "android.intent.extra.USER";
        String str2 = "android.intent.extra.user_handle";
        int i3 = 1342177280;
        if (i >= 0) {
            try {
                ArrayList arrayList = (ArrayList) this.mInjector.getUserManager().getProfiles(i, false);
                int size = arrayList.size();
                int i4 = 0;
                while (i4 < size) {
                    int i5 = ((UserInfo) arrayList.get(i4)).id;
                    Intent intent = new Intent("android.intent.action.USER_BACKGROUND");
                    intent.addFlags(i3);
                    intent.putExtra(str2, i5);
                    intent.putExtra(str, UserHandle.of(i5));
                    this.mInjector.broadcastIntent(intent, null, null, null, ActivityManagerService.MY_PID, callingUid, callingPid, i5);
                    i4++;
                    str = str;
                    size = size;
                    str2 = str2;
                    i3 = 1342177280;
                    arrayList = arrayList;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        String str3 = str2;
        String str4 = str;
        if (i2 >= 0) {
            ArrayList arrayList2 = (ArrayList) this.mInjector.getUserManager().getProfiles(i2, false);
            int size2 = arrayList2.size();
            int i6 = 0;
            while (i6 < size2) {
                int i7 = ((UserInfo) arrayList2.get(i6)).id;
                Intent intent2 = new Intent("android.intent.action.USER_FOREGROUND");
                intent2.addFlags(1342177280);
                String str5 = str3;
                intent2.putExtra(str5, i7);
                intent2.putExtra(str4, UserHandle.of(i7));
                this.mInjector.broadcastIntent(intent2, null, null, null, ActivityManagerService.MY_PID, callingUid, callingPid, i7);
                i6++;
                size2 = size2;
                arrayList2 = arrayList2;
                str3 = str5;
            }
            Intent intent3 = new Intent("android.intent.action.USER_SWITCHED");
            intent3.addFlags(1342177280);
            intent3.putExtra(str3, i2);
            intent3.putExtra(str4, UserHandle.of(i2));
            this.mInjector.broadcastIntent(intent3, null, new String[]{"android.permission.MANAGE_USERS"}, null, ActivityManagerService.MY_PID, callingUid, callingPid, -1);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void setAllowUserUnlocking(boolean z) {
        this.mAllowUserUnlocking = z;
    }

    public final boolean shouldStopUserOnSwitch() {
        synchronized (this.mLock) {
            try {
                int i = this.mStopUserOnSwitch;
                if (i == -1) {
                    int i2 = SystemProperties.getInt("fw.stop_bg_users_on_switch", -1);
                    return i2 == -1 ? this.mDelayUserDataLocking : i2 == 1;
                }
                boolean z = i == 1;
                Slogf.i("ActivityManager", "shouldStopUserOnSwitch(): returning overridden value (%b)", Boolean.valueOf(z));
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean startProfile(int i, boolean z, IProgressListener iProgressListener) {
        if (this.mInjector.mService.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.mService.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == -1) {
            throw new SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS_FULL permission to start a profile");
        }
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null || !userInfo.isProfile()) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " is not a profile"));
        }
        if (userInfo.isEnabled() || z) {
            return startUserNoChecks(i, 0, 3, iProgressListener);
        }
        Slogf.w("ActivityManager", "Cannot start disabled profile #%d", Integer.valueOf(i));
        return false;
    }

    public final void startProfiles() {
        int i;
        int i2;
        UserProperties userProperties;
        int currentUserId = getCurrentUserId();
        int i3 = 0;
        ArrayList arrayList = (ArrayList) this.mInjector.getUserManager().getProfiles(currentUserId, false);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if ((userInfo.flags & 16) == 16 && (i2 = userInfo.id) != currentUserId && (userProperties = this.mInjector.getUserManagerInternal().getUserProperties(i2)) != null && userProperties.getStartWithParent() && !userInfo.isQuietModeEnabled()) {
                arrayList2.add(userInfo);
            }
        }
        int size = arrayList2.size();
        while (i3 < size) {
            synchronized (this.mLock) {
                i = this.mMaxRunningUsers;
            }
            if (i3 >= i - 1) {
                break;
            }
            startUser(((UserInfo) arrayList2.get(i3)).id, 3);
            i3++;
        }
        if (i3 < size) {
            Slogf.w("ActivityManager", "More profiles than MAX_RUNNING_USERS");
        }
    }

    public boolean startUser(int i, int i2) {
        checkCallingHasOneOfThosePermissions("startUser", "android.permission.INTERACT_ACROSS_USERS_FULL");
        return startUserNoChecks(i, 0, i2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0382 A[Catch: all -> 0x0075, TryCatch #4 {all -> 0x0075, blocks: (B:10:0x0057, B:12:0x0065, B:14:0x006b, B:15:0x0097, B:17:0x009c, B:18:0x00aa, B:20:0x00b9, B:25:0x00d1, B:27:0x00d7, B:32:0x0115, B:34:0x012d, B:37:0x0151, B:38:0x0159, B:44:0x01b8, B:46:0x01c1, B:48:0x01ce, B:49:0x01d3, B:51:0x01d8, B:52:0x01ec, B:54:0x01f4, B:55:0x01ff, B:59:0x0205, B:62:0x0225, B:64:0x022a, B:67:0x0243, B:69:0x0249, B:70:0x024c, B:72:0x0254, B:73:0x026c, B:77:0x0271, B:79:0x029e, B:81:0x02a2, B:82:0x02c2, B:84:0x02ee, B:86:0x02f4, B:88:0x02fa, B:89:0x030d, B:91:0x0315, B:97:0x034c, B:95:0x0354, B:100:0x0357, B:104:0x035e, B:110:0x037d, B:112:0x0382, B:117:0x03a0, B:118:0x038f, B:120:0x037a, B:126:0x0278, B:128:0x027b, B:129:0x0292, B:133:0x0297, B:137:0x029d, B:141:0x0237, B:142:0x0238, B:153:0x03b2, B:154:0x00f6, B:156:0x00fa, B:161:0x007c, B:164:0x0081, B:170:0x008a, B:173:0x008d, B:40:0x015a, B:42:0x0165, B:43:0x01b7, B:143:0x0186, B:145:0x018a, B:146:0x01af, B:57:0x0200, B:58:0x0204, B:75:0x026d, B:76:0x0270, B:131:0x0293, B:132:0x0296), top: B:9:0x0057, inners: #0, #3, #5, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x038f A[Catch: all -> 0x0075, TryCatch #4 {all -> 0x0075, blocks: (B:10:0x0057, B:12:0x0065, B:14:0x006b, B:15:0x0097, B:17:0x009c, B:18:0x00aa, B:20:0x00b9, B:25:0x00d1, B:27:0x00d7, B:32:0x0115, B:34:0x012d, B:37:0x0151, B:38:0x0159, B:44:0x01b8, B:46:0x01c1, B:48:0x01ce, B:49:0x01d3, B:51:0x01d8, B:52:0x01ec, B:54:0x01f4, B:55:0x01ff, B:59:0x0205, B:62:0x0225, B:64:0x022a, B:67:0x0243, B:69:0x0249, B:70:0x024c, B:72:0x0254, B:73:0x026c, B:77:0x0271, B:79:0x029e, B:81:0x02a2, B:82:0x02c2, B:84:0x02ee, B:86:0x02f4, B:88:0x02fa, B:89:0x030d, B:91:0x0315, B:97:0x034c, B:95:0x0354, B:100:0x0357, B:104:0x035e, B:110:0x037d, B:112:0x0382, B:117:0x03a0, B:118:0x038f, B:120:0x037a, B:126:0x0278, B:128:0x027b, B:129:0x0292, B:133:0x0297, B:137:0x029d, B:141:0x0237, B:142:0x0238, B:153:0x03b2, B:154:0x00f6, B:156:0x00fa, B:161:0x007c, B:164:0x0081, B:170:0x008a, B:173:0x008d, B:40:0x015a, B:42:0x0165, B:43:0x01b7, B:143:0x0186, B:145:0x018a, B:146:0x01af, B:57:0x0200, B:58:0x0204, B:75:0x026d, B:76:0x0270, B:131:0x0293, B:132:0x0296), top: B:9:0x0057, inners: #0, #3, #5, #6 }] */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startUserInternal(final int r20, int r21, int r22, android.os.IProgressListener r23, com.android.server.utils.TimingsTraceAndSlog r24) {
        /*
            Method dump skipped, instructions count: 951
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.UserController.startUserInternal(int, int, int, android.os.IProgressListener, com.android.server.utils.TimingsTraceAndSlog):boolean");
    }

    public final boolean startUserNoChecks(int i, int i2, int i3, IProgressListener iProgressListener) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        if (SemPersonaManager.isKnoxId(i)) {
            Context context = this.mInjector.mService.mContext;
            List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("PersonaServiceHelper", "shouldBlockUserStart() " + i);
                UserInfo userInfo = PersonaServiceHelper.getUserManager().getUserInfo(i);
                if (userInfo != null && userInfo.id != 0 && userInfo.isManagedProfile() && userInfo.isDeviceCompromised()) {
                    Log.w("PersonaServiceHelper", "blocking when device compromised : " + i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        StringBuilder sb = new StringBuilder("UserController.startUser-");
        sb.append(i);
        sb.append(i2 == 0 ? "" : VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "-display-"));
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(i3 == 1 ? "fg" : "bg");
        sb.append("-start-mode-");
        sb.append(i3);
        timingsTraceAndSlog.traceBegin(sb.toString());
        try {
            return startUserInternal(i, i2, i3, iProgressListener, timingsTraceAndSlog);
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    public final void stopExcessRunningUsersLU(int i, ArraySet arraySet) {
        List runningUsersLU = getRunningUsersLU();
        Iterator it = runningUsersLU.iterator();
        while (runningUsersLU.size() > i && it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() != 0 && num.intValue() != this.mCurrentUserId && !arraySet.contains(num)) {
                Slogf.i("ActivityManager", "Too many running users (%d). Attempting to stop user %d", Integer.valueOf(runningUsersLU.size()), num);
                if (stopUsersLU(num.intValue(), false, true, null, null) == 0) {
                    it.remove();
                }
            }
        }
    }

    public final int stopUser(int i, boolean z, boolean z2, IStopUserCallback iStopUserCallback, AnonymousClass4 anonymousClass4) {
        int stopUsersLU;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        StringBuilder sb = new StringBuilder("UserController");
        sb.append(z ? "-stopProfileRegardlessOfParent" : "");
        sb.append(z2 ? "-allowDelayedLocking" : "");
        timingsTraceAndSlog.traceBegin(ActiveServices$$ExternalSyntheticOutline0.m(i, iStopUserCallback != null ? "-withStopUserCallback" : "", PackageManagerShellCommandDataLoader.STDIN_PATH, "-[stopUser]", sb));
        try {
            checkCallingHasOneOfThosePermissions("stopUser", "android.permission.INTERACT_ACROSS_USERS_FULL");
            Preconditions.checkArgument(i >= 0, "Invalid user id %d", new Object[]{Integer.valueOf(i)});
            enforceShellRestriction(i);
            synchronized (this.mLock) {
                stopUsersLU = stopUsersLU(i, z, z2, iStopUserCallback, anonymousClass4);
            }
            return stopUsersLU;
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int stopUsersLU(int i, boolean z, boolean z2, IStopUserCallback iStopUserCallback, AnonymousClass4 anonymousClass4) {
        boolean z3;
        ArrayList arrayList;
        int i2;
        if (i == 0) {
            return -3;
        }
        if (i == getCurrentOrTargetUserIdLU()) {
            return -2;
        }
        if (!z && (i2 = this.mUserProfileGroupIds.get(i, -10000)) != -10000 && i2 != i && (i2 == 0 || i2 == getCurrentOrTargetUserIdLU())) {
            return -4;
        }
        int size = this.mStartedUsers.size();
        IntArray intArray = new IntArray();
        intArray.add(i);
        int i3 = this.mUserProfileGroupIds.get(i, -10000);
        int i4 = 1;
        if (i3 == i) {
            for (int i5 = 0; i5 < size; i5++) {
                int identifier = ((UserState) this.mStartedUsers.valueAt(i5)).mHandle.getIdentifier();
                boolean z4 = i3 != -10000 && i3 == this.mUserProfileGroupIds.get(identifier, -10000);
                boolean z5 = identifier == i;
                if (z4 && !z5) {
                    intArray.add(identifier);
                }
            }
        }
        int[] array = intArray.toArray();
        for (int i6 : array) {
            if (i6 == 0 || i6 == getCurrentOrTargetUserIdLU()) {
                Slogf.e("ActivityManager", "Cannot stop user %d because it is related to user %d. ", Integer.valueOf(i), Integer.valueOf(i6));
                return -4;
            }
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        int length = array.length;
        int i7 = 0;
        while (i7 < length) {
            final int i8 = array[i7];
            timingsTraceAndSlog.traceBegin("stopSingleUserLU-" + i8 + "-[stopUser]");
            IStopUserCallback iStopUserCallback2 = i8 == i ? iStopUserCallback : null;
            AnonymousClass4 anonymousClass42 = i8 == i ? anonymousClass4 : null;
            Slogf.i("ActivityManager", "stopSingleUserLU userId=" + i8);
            if (android.multiuser.Flags.scheduleStopOfBackgroundUser()) {
                this.mHandler.removeEqualMessages(150, Integer.valueOf(i8));
            }
            UserState userState = (UserState) this.mStartedUsers.get(i8);
            if (userState == null) {
                if (canDelayDataLockingForUser(i8)) {
                    if (!z2 || anonymousClass42 == null) {
                        z3 = z2;
                    } else {
                        Slogf.wtf("ActivityManager", VibrationParam$1$$ExternalSyntheticOutline0.m(i8, "allowDelayedLocking set with KeyEvictedCallback, ignore it and lock user:"), new RuntimeException());
                        z3 = false;
                    }
                    if (!z3 && this.mLastActiveUsersForDelayedLocking.remove(Integer.valueOf(i8))) {
                        if (anonymousClass42 != null) {
                            arrayList = new ArrayList(i4);
                            arrayList.add(anonymousClass42);
                        } else {
                            arrayList = null;
                        }
                        FgThread.getHandler().post(new UserController$$ExternalSyntheticLambda2(this, i8, arrayList, 1));
                    }
                }
                if (iStopUserCallback2 != null) {
                    this.mHandler.post(new UserController$$ExternalSyntheticLambda5(i8, 1, iStopUserCallback2));
                }
            } else {
                logUserJourneyBegin(i8, 5);
                if (iStopUserCallback2 != null) {
                    userState.mStopCallbacks.add(iStopUserCallback2);
                }
                if (anonymousClass42 != null) {
                    userState.mKeyEvictedCallbacks.add(anonymousClass42);
                }
                UserInfo userInfo = getUserInfo(i8);
                final boolean z6 = (userInfo == null || !userInfo.isEnabled()) ? i4 : 0;
                final ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mInjector.mService.mActivityTaskManager.mExt;
                activityTaskManagerServiceExt.mService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTaskManagerServiceExt activityTaskManagerServiceExt2 = ActivityTaskManagerServiceExt.this;
                        int i9 = i8;
                        boolean z7 = z6;
                        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerServiceExt2.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                activityTaskManagerServiceExt2.mStartedUserIds.remove(Integer.valueOf(i9));
                                MultiWindowEnableController multiWindowEnableController = activityTaskManagerServiceExt2.mService.mMultiWindowEnableController;
                                if (z7) {
                                    multiWindowEnableController.mMWOffRequesters.remove(i9);
                                    multiWindowEnableController.mMWOffRequestersLog.remove(i9);
                                } else {
                                    multiWindowEnableController.getClass();
                                }
                                activityTaskManagerServiceExt2.mCoreStateController.stopUserLocked(i9, z7);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
                int i9 = userState.state;
                if (i9 != 4 && i9 != 5) {
                    userState.setState(4);
                    UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
                    TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
                    timingsTraceAndSlog2.traceBegin("setUserState-STATE_STOPPING-" + i8 + "-[stopUser]");
                    userManagerInternal.setUserState(i8, userState.state);
                    timingsTraceAndSlog2.traceEnd();
                    timingsTraceAndSlog2.traceBegin("unassignUserFromDisplayOnStop-" + i8 + "-[stopUser]");
                    userManagerInternal.unassignUserFromDisplayOnStop(i8);
                    timingsTraceAndSlog2.traceEnd();
                    updateStartedUserArrayLU();
                    UserController$$ExternalSyntheticLambda1 userController$$ExternalSyntheticLambda1 = new UserController$$ExternalSyntheticLambda1(this, i8, userState, z2, 0);
                    if (this.mInjector.getUserManager().isPreCreated(i8)) {
                        userController$$ExternalSyntheticLambda1.run();
                    } else {
                        this.mHandler.post(new UserController$$ExternalSyntheticLambda2(this, i8, userController$$ExternalSyntheticLambda1, 0));
                    }
                }
            }
            timingsTraceAndSlog.traceEnd();
            i7++;
            i4 = 1;
        }
        return 0;
    }

    public final boolean switchUser(int i) {
        enforceShellRestriction(i);
        EventLog.writeEvent(30075, i);
        int currentUserId = getCurrentUserId();
        UserInfo userInfo = getUserInfo(i);
        if (MaintenanceModeManager.isInMaintenanceMode() && 77 != i) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "Cannot switch to User #", " in Maintenance mode", "ActivityManager");
            return false;
        }
        synchronized (this.mLock) {
            if (i == currentUserId) {
                try {
                    if (this.mTargetUserId == -10000) {
                        Slogf.i("ActivityManager", "user #" + i + " is already the current user");
                        return true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (userInfo == null) {
                Slogf.w("ActivityManager", "No user info for user #" + i);
                return false;
            }
            if (!userInfo.supportsSwitchTo()) {
                Slogf.w("ActivityManager", "Cannot switch to User #" + i + ": not supported");
                return false;
            }
            if (FactoryResetter.sFactoryResetting.get()) {
                Slogf.w("ActivityManager", "Cannot switch to User #" + i + ": factory reset in progress");
                return false;
            }
            if (this.mCurrentDexMode != 0) {
                Slog.w("ActivityManager", "Cannot switch to User #" + i + ": in dex mode");
                DexController.H h = this.mInjector.mService.mActivityTaskManager.mMultiTaskingController.mAtm.mDexController.mH;
                h.sendMessage(h.obtainMessage(10));
                return false;
            }
            if (!this.mInitialized) {
                Slogf.e("ActivityManager", "Cannot switch to User #" + i + ": UserController not ready yet");
                return false;
            }
            if (this.mTargetUserId != -10000) {
                Slogf.w("ActivityManager", "There is already an ongoing user switch to User #" + this.mTargetUserId + ". User #" + i + " will be added to the queue.");
                this.mPendingTargetUserIds.offer(Integer.valueOf(i));
                return true;
            }
            this.mTargetUserId = i;
            boolean z = this.mUserSwitchUiEnabled;
            if (z) {
                Pair pair = new Pair(getUserInfo(currentUserId), userInfo);
                this.mUiHandler.removeMessages(1000);
                Handler handler = this.mUiHandler;
                handler.sendMessage(handler.obtainMessage(1000, pair));
            } else {
                this.mHandler.removeMessages(120);
                Handler handler2 = this.mHandler;
                handler2.sendMessage(handler2.obtainMessage(120, i, 0));
            }
            return true;
        }
    }

    public final void timeoutUserSwitch(UserState userState, int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("ActivityManager");
        timingsTraceAndSlog.traceBegin("timeoutUserSwitch-" + i + "-to-" + i2);
        synchronized (this.mLock) {
            Slogf.e("ActivityManager", "User switch timeout: from " + i + " to " + i2);
            this.mTimeoutUserSwitchCallbacks = this.mCurWaitingUserSwitchCallbacks;
            this.mHandler.removeMessages(90);
            sendContinueUserSwitchLU(userState, i, i2);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(90, i, i2), 5000L);
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final boolean unlockUser(int i, IProgressListener iProgressListener) {
        showEventLog(i, -1, 0, "unlockUser", "NULL");
        checkCallingHasOneOfThosePermissions("unlockUser", "android.permission.INTERACT_ACROSS_USERS_FULL");
        EventLog.writeEvent(30077, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            showEventLog(i, -1, 1, "unlockUser", "call maybeUnlockUser");
            return maybeUnlockUser(i, iProgressListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateProfileRelatedCaches() {
        int i = 0;
        ArrayList arrayList = (ArrayList) this.mInjector.getUserManager().getProfiles(getCurrentUserId(), false);
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((UserInfo) arrayList.get(i2)).id;
        }
        List users = this.mInjector.getUserManager().getUsers(true, false, true);
        synchronized (this.mLock) {
            try {
                this.mCurrentProfileIds = iArr;
                this.mUserProfileGroupIds.clear();
                while (true) {
                    ArrayList arrayList2 = (ArrayList) users;
                    if (i < arrayList2.size()) {
                        UserInfo userInfo = (UserInfo) arrayList2.get(i);
                        int i3 = userInfo.profileGroupId;
                        if (i3 != -10000) {
                            this.mUserProfileGroupIds.put(userInfo.id, i3);
                        }
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateStartedUserArrayLU() {
        int i = 0;
        for (int i2 = 0; i2 < this.mStartedUsers.size(); i2++) {
            int i3 = ((UserState) this.mStartedUsers.valueAt(i2)).state;
            if (i3 != 4 && i3 != 5) {
                i++;
            }
        }
        this.mStartedUserArray = new int[i];
        int i4 = 0;
        for (int i5 = 0; i5 < this.mStartedUsers.size(); i5++) {
            int i6 = ((UserState) this.mStartedUsers.valueAt(i5)).state;
            if (i6 != 4 && i6 != 5) {
                this.mStartedUserArray[i4] = this.mStartedUsers.keyAt(i5);
                i4++;
            }
        }
    }

    public final int updateUserToLockLU(int i, boolean z) {
        if (!canDelayDataLockingForUser(i) || !z || getUserInfo(i).isEphemeral() || hasUserRestriction("no_run_in_background", i)) {
            return i;
        }
        if (this.mDelayUserDataLocking) {
            this.mLastActiveUsersForDelayedLocking.remove(Integer.valueOf(i));
            this.mLastActiveUsersForDelayedLocking.add(0, Integer.valueOf(i));
            if (this.mLastActiveUsersForDelayedLocking.size() + this.mStartedUsers.size() > this.mMaxRunningUsers) {
                int intValue = ((Integer) this.mLastActiveUsersForDelayedLocking.get(r6.size() - 1)).intValue();
                this.mLastActiveUsersForDelayedLocking.remove(r4.size() - 1);
                Slogf.i("ActivityManager", "finishUserStopped: should stop user " + i + " but should lock user " + intValue);
                return intValue;
            }
        }
        Slogf.i("ActivityManager", "finishUserStopped: should stop user " + i + " but without any locking");
        return -10000;
    }
}
