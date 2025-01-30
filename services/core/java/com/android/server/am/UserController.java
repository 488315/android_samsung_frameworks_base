package com.android.server.am;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.BroadcastOptions;
import android.app.IStopUserCallback;
import android.app.IUserSwitchObserver;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.appwidget.AppWidgetManagerInternal;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.IPackageManager;
import android.content.pm.PackagePartitions;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IProgressListener;
import android.os.IRemoteCallback;
import android.os.IUserManager;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.FactoryResetter;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.sdp.SdpManagerImpl;
import com.android.server.am.UserController;
import com.android.server.am.UserState;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class UserController implements Handler.Callback {
    public SimpleDateFormat formatter;
    public volatile boolean mAllowUserUnlocking;
    public volatile boolean mBootCompleted;
    public final SparseIntArray mCompletedEventTypes;
    public volatile ArraySet mCurWaitingUserSwitchCallbacks;
    public int[] mCurrentProfileIds;
    public volatile int mCurrentUserId;
    public boolean mDelayUserDataLocking;
    public final Handler mHandler;
    public boolean mInitialized;
    public final Injector mInjector;
    public boolean mIsBroadcastSentForSystemUserStarted;
    public boolean mIsBroadcastSentForSystemUserStarting;
    public final ArrayList mLastActiveUsers;
    public volatile long mLastUserUnlockingUptime;
    public final Object mLock;
    public final LockPatternUtils mLockPatternUtils;
    public int mMaxRunningUsers;
    public final List mPendingUserStarts;
    public PersonaManagerInternal mPersonaManagerInternal;
    public int[] mStartedUserArray;
    public final SparseArray mStartedUsers;
    public int mStopUserOnSwitch;
    public String mSwitchingFromSystemUserMessage;
    public String mSwitchingToSystemUserMessage;
    public volatile int mTargetUserId;
    public ArraySet mTimeoutUserSwitchCallbacks;
    public final Handler mUiHandler;
    public final UserManagerInternal.UserLifecycleListener mUserLifecycleListener;
    public final ArrayList mUserLru;
    public final SparseIntArray mUserProfileGroupIds;
    public final RemoteCallbackList mUserSwitchObservers;
    public boolean mUserSwitchUiEnabled;

    public UserController(ActivityManagerService activityManagerService) {
        this(new Injector(activityManagerService));
    }

    public UserController(Injector injector) {
        this.mLock = new Object();
        this.mCurrentUserId = 0;
        this.mTargetUserId = -10000;
        SparseArray sparseArray = new SparseArray();
        this.mStartedUsers = sparseArray;
        ArrayList arrayList = new ArrayList();
        this.mUserLru = arrayList;
        this.mStartedUserArray = new int[]{0};
        this.mCurrentProfileIds = new int[0];
        this.mUserProfileGroupIds = new SparseIntArray();
        this.mUserSwitchObservers = new RemoteCallbackList();
        this.mUserSwitchUiEnabled = true;
        this.mLastActiveUsers = new ArrayList();
        this.mCompletedEventTypes = new SparseIntArray();
        this.mStopUserOnSwitch = -1;
        this.mLastUserUnlockingUptime = 0L;
        this.mPendingUserStarts = new ArrayList();
        this.mUserLifecycleListener = new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.am.UserController.1
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserCreated(UserInfo userInfo, Object obj) {
                UserController.this.onUserAdded(userInfo);
            }

            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserRemoved(UserInfo userInfo) {
                UserController.this.onUserRemoved(userInfo.id);
            }
        };
        this.formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.mInjector = injector;
        this.mHandler = injector.getHandler(this);
        this.mUiHandler = injector.getUiHandler(this);
        UserState userState = new UserState(UserHandle.SYSTEM);
        userState.mUnlockProgress.addListener(new UserProgressListener());
        sparseArray.put(0, userState);
        arrayList.add(0);
        this.mLockPatternUtils = injector.getLockPatternUtils();
        updateStartedUserArrayLU();
    }

    public void setInitialConfig(boolean z, int i, boolean z2) {
        synchronized (this.mLock) {
            this.mUserSwitchUiEnabled = z;
            this.mMaxRunningUsers = i;
            this.mDelayUserDataLocking = z2;
            this.mInitialized = true;
        }
    }

    public final boolean isUserSwitchUiEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserSwitchUiEnabled;
        }
        return z;
    }

    public int getMaxRunningUsers() {
        int i;
        synchronized (this.mLock) {
            i = this.mMaxRunningUsers;
        }
        return i;
    }

    public void setStopUserOnSwitch(int i) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == -1) {
            throw new SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS permission to call setStopUserOnSwitch()");
        }
        synchronized (this.mLock) {
            Slogf.m96i("ActivityManager", "setStopUserOnSwitch(): %d -> %d", Integer.valueOf(this.mStopUserOnSwitch), Integer.valueOf(i));
            this.mStopUserOnSwitch = i;
        }
    }

    public final boolean shouldStopUserOnSwitch() {
        synchronized (this.mLock) {
            int i = this.mStopUserOnSwitch;
            if (i != -1) {
                boolean z = i == 1;
                Slogf.m96i("ActivityManager", "shouldStopUserOnSwitch(): returning overridden value (%b)", Boolean.valueOf(z));
                return z;
            }
            int i2 = SystemProperties.getInt("fw.stop_bg_users_on_switch", -1);
            if (i2 == -1) {
                return this.mDelayUserDataLocking;
            }
            return i2 == 1;
        }
    }

    public void finishUserSwitch(final UserState userState) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$finishUserSwitch$0(userState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserSwitch$0(UserState userState) {
        finishUserBoot(userState);
        startProfiles();
        synchronized (this.mLock) {
            stopRunningUsersLU(this.mMaxRunningUsers);
        }
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

    public final void stopRunningUsersLU(int i) {
        List runningUsersLU = getRunningUsersLU();
        Iterator it = runningUsersLU.iterator();
        while (runningUsersLU.size() > i && it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() != 0 && num.intValue() != this.mCurrentUserId && stopUsersLU(num.intValue(), false, true, null, null) == 0) {
                it.remove();
            }
        }
    }

    public boolean canStartMoreUsers() {
        boolean z;
        synchronized (this.mLock) {
            z = getRunningUsersLU().size() < this.mMaxRunningUsers;
        }
        return z;
    }

    public final void finishUserBoot(UserState userState) {
        finishUserBoot(userState, null);
    }

    public final void finishUserBoot(UserState userState, IIntentReceiver iIntentReceiver) {
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30078, identifier);
        showEventLog(identifier, userState.state, 0, "finishUserBoot", "NULL");
        Slog.d("ActivityManager", "Finishing user boot " + identifier);
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(identifier) != userState) {
                return;
            }
            if (userState.setState(0, 1)) {
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(identifier, 4, 0);
                this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                if (identifier == 0 && !this.mInjector.isRuntimeRestarted() && !this.mInjector.isFirstBootOrUpgrade()) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    FrameworkStatsLog.write(240, 12, elapsedRealtime);
                    if (elapsedRealtime > 120000) {
                        Slogf.wtf("SystemServerTiming", "finishUserBoot took too long. elapsedTimeMs=" + elapsedRealtime);
                    }
                }
                if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                    Handler handler = this.mHandler;
                    handler.sendMessage(handler.obtainMessage(110, identifier, 0));
                    if (this.mAllowUserUnlocking) {
                        sendLockedBootCompletedBroadcast(iIntentReceiver, identifier);
                    }
                }
                showEventLog(identifier, userState.state, 1, "finishUserBoot", "send LOCKED BOOT COMPLETED");
            }
            if (this.mInjector.getUserManager().isProfile(identifier)) {
                UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(identifier);
                if (profileParent != null && isUserRunning(profileParent.id, 4)) {
                    Slogf.m86d("ActivityManager", "User " + identifier + " (parent " + profileParent.id + "): attempting unlock because parent is unlocked");
                    if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                        maybeUnlockDualDARUser(identifier, profileParent.id);
                    } else {
                        maybeUnlockUser(identifier);
                    }
                } else {
                    Slogf.m86d("ActivityManager", "User " + identifier + " (parent " + (profileParent == null ? "<null>" : String.valueOf(profileParent.id)) + "): delaying unlock because parent is locked");
                }
            } else {
                maybeUnlockUser(identifier);
            }
            showEventLog(identifier, userState.state, 2, "finishUserBoot", "NULL");
        }
    }

    public final boolean maybeUnlockDualDARUser(int i, int i2) {
        KeyguardManager keyguardManager = this.mInjector.getKeyguardManager();
        if (fetchOuterLayerKey(i) != null) {
            DDLog.m33d("ActivityManager", "Trying to unlock DualDAR user after userStart " + i, new Object[0]);
            VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            if (StateMachine.getCurrentState(i) == State.DEVICE_UNLOCK_DATA_UNLOCK) {
                DDLog.m33d("ActivityManager", "Password2Auth has already been completed for: " + i, new Object[0]);
                verifyCredentialResponse = VerifyCredentialResponse.OK;
            } else if (!keyguardManager.isDeviceSecure(i)) {
                DDLog.m33d("ActivityManager", "Do Password2Auth with null credential for: " + i, new Object[0]);
                verifyCredentialResponse = onPassword2Auth(i);
            }
            if (verifyCredentialResponse == VerifyCredentialResponse.OK && !DualDarManager.isOnDeviceOwner(i)) {
                DDLog.m33d("ActivityManager", "fetch outer layer key and unlock DualDAR user " + i, new Object[0]);
                return maybeUnlockUser(i, null);
            }
            DDLog.m34e("ActivityManager", "Default Authentication Failure by DualDAR client", new Object[0]);
            return false;
        }
        if (!keyguardManager.isDeviceLocked(i2)) {
            DDLog.m34e("ActivityManager", "This should theoretically never happen. Failed to unlock DualDAR user: " + i + " something went wrong while fetching OLK event though user0 is unlocked.", new Object[0]);
        }
        return false;
    }

    public final byte[] fetchOuterLayerKey(int i) {
        DDLog.m33d("ActivityManager", "fetchOuterLayerKey()", new Object[0]);
        try {
            return DualDARController.getInstance(this.mInjector.getContext()).fetchOuterLayerKey(i);
        } catch (Exception e) {
            DDLog.m34e("ActivityManager", "Exception in fetchOuterLayerKey() : " + e.toString(), new Object[0]);
            return null;
        }
    }

    public final VerifyCredentialResponse onPassword2Auth(int i) {
        DDLog.m33d("ActivityManager", "onPassword2Auth()", new Object[0]);
        if (!SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            DDLog.m34e("ActivityManager", "User is not DualDAR eligible. so no need to verify DualDAR Passwords" + i, new Object[0]);
            return VerifyCredentialResponse.OK;
        }
        if (!DualDARController.getInstance(this.mInjector.getContext()).onPassword2Auth(i, (byte[]) null)) {
            DDLog.m34e("ActivityManager", "Authentication Failure by dual dar client", new Object[0]);
            return VerifyCredentialResponse.ERROR;
        }
        DDLog.m33d("ActivityManager", "onPassword2Auth completed successfully", new Object[0]);
        StateMachine.processEvent(i, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
        return VerifyCredentialResponse.OK;
    }

    public final void sendLockedBootCompletedBroadcast(IIntentReceiver iIntentReceiver, int i) {
        Intent intent = new Intent("android.intent.action.LOCKED_BOOT_COMPLETED", (Uri) null);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(-1996488704);
        this.mInjector.broadcastIntent(intent, null, iIntentReceiver, 0, null, null, new String[]{"android.permission.RECEIVE_BOOT_COMPLETED"}, -1, getTemporaryAppAllowlistBroadcastOptions(202).toBundle(), true, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), i);
    }

    public final boolean finishUserUnlocking(final UserState userState) {
        int i;
        final int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30070, identifier);
        this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(identifier, 5, 1);
        showEventLog(identifier, userState.state, 0, "finishUserUnlocking", "NULL");
        Slog.d("ActivityManager", "UserController event: finishUserUnlocking(" + identifier + ")");
        if (MaintenanceModeManager.isInMaintenanceMode() && 77 != identifier) {
            Slog.i("ActivityManager", "Do not unlock user " + identifier + " in Maintenance mode");
            return false;
        }
        if (!StorageManager.isUserKeyUnlocked(identifier)) {
            SystemProperties.set("dev.boot." + identifier + ".user_unlocked", "FAIL-finishUserUnlocking");
            Slog.i("ActivityManager", "!@Boot: StorageManager Unlocked FAIL, finishUserUnlocking");
            return false;
        }
        showEventLog(identifier, userState.state, 1, "finishUserUnlocking", "OK StorageManager.isUserKeyUnlocked");
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(identifier) == userState && (i = userState.state) == 1) {
                showEventLog(identifier, i, 1, "finishUserUnlocking", "mUnlockProgress.start()");
                userState.mUnlockProgress.start();
                userState.mUnlockProgress.setProgress(5, this.mInjector.getContext().getString(R.string.call_notification_decline_action));
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserController.this.lambda$finishUserUnlocking$1(identifier, userState);
                    }
                });
                showEventLog(identifier, userState.state, 2, "finishUserUnlocking", "NULL");
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlocking$1(int i, UserState userState) {
        if (!StorageManager.isUserKeyUnlocked(i)) {
            Slogf.m102w("ActivityManager", "User key got locked unexpectedly, leaving user locked.");
            return;
        }
        showEventLog(i, userState.state, 1, "finishUserUnlocking", "Start getUserManager().onBeforeUnlockUser");
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("UM.onBeforeUnlockUser-" + i);
        this.mInjector.getUserManager().onBeforeUnlockUser(i);
        timingsTraceAndSlog.traceEnd();
        showEventLog(i, userState.state, 1, "finishUserUnlocking", "End getUserManager().onBeforeUnlockUser");
        synchronized (this.mLock) {
            if (userState.setState(1, 2)) {
                this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                userState.mUnlockProgress.setProgress(20);
                this.mLastUserUnlockingUptime = SystemClock.uptimeMillis();
                this.mHandler.obtainMessage(100, i, 0, userState).sendToTarget();
                showEventLog(i, userState.state, 1, "finishUserUnlocking", "sendToTarget USER_UNLOCK_MSG");
            }
        }
    }

    public final void finishUserUnlocked(final UserState userState) {
        int i;
        int i2;
        UserInfo profileParent;
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30071, identifier);
        showEventLog(identifier, userState.state, 0, "finishUserUnlocked", "NULL");
        showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "Try StorageManager.isUserKeyUnlocked");
        Slog.d("ActivityManager", "UserController event: finishUserUnlocked(" + identifier + ")");
        if (!StorageManager.isUserKeyUnlocked(identifier)) {
            SystemProperties.set("dev.boot." + identifier + ".user_unlocked", "FAIL-finishUserUnlocked");
            Slog.i("ActivityManager", "!@Boot: StorageManager Unlocked FAIL, finishUserUnlocked");
            return;
        }
        showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "OK StorageManager.isUserKeyUnlocked");
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) != userState) {
                return;
            }
            if (userState.setState(2, 3)) {
                this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                userState.mUnlockProgress.finish();
                if (identifier == 0) {
                    this.mInjector.startPersistentApps(262144);
                }
                this.mInjector.installEncryptionUnawareProviders(identifier);
                if (this.mInjector.getUserManager().isPreCreated(identifier)) {
                    i = 1342177280;
                    i2 = identifier;
                } else {
                    Intent intent = new Intent("android.intent.action.USER_UNLOCKED");
                    intent.putExtra("android.intent.extra.user_handle", identifier);
                    intent.addFlags(1342177280);
                    this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), identifier);
                    i2 = identifier;
                    i = 1342177280;
                    showEventLog(identifier, userState.state, 1, "finishUserUnlocked", "send USER UNLOCKED");
                }
                UserInfo userInfo = getUserInfo(i2);
                if (userInfo.isProfile() && (profileParent = this.mInjector.getUserManager().getProfileParent(i2)) != null) {
                    broadcastProfileAccessibleStateChanged(i2, profileParent.id, "android.intent.action.PROFILE_ACCESSIBLE");
                    if (userInfo.isManagedProfile() || userInfo.isCloneProfile()) {
                        Intent intent2 = new Intent("android.intent.action.MANAGED_PROFILE_UNLOCKED");
                        intent2.putExtra("android.intent.extra.USER", UserHandle.of(i2));
                        intent2.addFlags(i);
                        this.mInjector.broadcastIntent(intent2, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), profileParent.id);
                    }
                }
                UserInfo userInfo2 = getUserInfo(i2);
                final boolean isCscVerChanged = this.mInjector.mService.mExt.isCscVerChanged();
                if (!Objects.equals(userInfo2.lastLoggedInFingerprint, PackagePartitions.FINGERPRINT) || SystemProperties.getBoolean("persist.pm.mock-upgrade", false) || isCscVerChanged) {
                    this.mInjector.sendPreBootBroadcast(i2, userInfo2.isManagedProfile() || userInfo2.isCloneProfile(), new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserController.this.lambda$finishUserUnlocked$2(isCscVerChanged, userState);
                        }
                    });
                    showEventLog(i2, userState.state, 1, "finishUserUnlocked", "OK sendPreBootBroadcast");
                } else {
                    finishUserUnlockedCompleted(userState);
                }
                showEventLog(i2, userState.state, 2, "finishUserUnlocked", "NULL");
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserController.lambda$finishUserUnlocked$3();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlocked$2(boolean z, UserState userState) {
        if (z) {
            this.mInjector.mService.mExt.updatePreBootCscFile();
        }
        finishUserUnlockedCompleted(userState);
    }

    public static /* synthetic */ void lambda$finishUserUnlocked$3() {
        MARsPolicyManager.getInstance().postInit(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x011f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void finishUserUnlockedCompleted(UserState userState) {
        UserInfo userInfo;
        int i;
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30072, identifier);
        showEventLog(identifier, userState.state, 2, "finishUserUnlockedCompleted", "NULL");
        Slog.d("ActivityManager", "UserController event: finishUserUnlockedCompleted(" + identifier + ")");
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) != userState) {
                return;
            }
            final UserInfo userInfo2 = getUserInfo(identifier);
            if (userInfo2 == null) {
                return;
            }
            if (!StorageManager.isUserKeyUnlocked(identifier)) {
                SystemProperties.set("dev.boot." + identifier + ".user_unlocked", "FAIL-finishUserUnlockedCompleted");
                Slog.i("ActivityManager", "!@Boot: StorageManager Unlocked FAIL, finishUserUnlockedCompleted");
                return;
            }
            showEventLog(identifier, userState.state, 1, "finishUserUnlockedCompleted", "OK StorageManager.isUserKeyUnlocked");
            this.mInjector.getUserManager().onUserLoggedIn(identifier);
            final Runnable runnable = new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    UserController.this.lambda$finishUserUnlockedCompleted$4(userInfo2);
                }
            };
            if (!userInfo2.isInitialized()) {
                Slogf.m86d("ActivityManager", "Initializing user #" + identifier);
                if (userInfo2.preCreated) {
                    runnable.run();
                } else if (identifier != 0) {
                    Intent intent = new Intent("android.intent.action.USER_INITIALIZE");
                    intent.addFlags(285212672);
                    userInfo = userInfo2;
                    i = identifier;
                    this.mInjector.broadcastIntent(intent, null, new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.2
                        public void performReceive(Intent intent2, int i2, String str, Bundle bundle, boolean z, boolean z2, int i3) {
                            runnable.run();
                        }
                    }, 0, null, null, null, -1, null, true, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), i);
                    if (!userInfo.preCreated) {
                        Slogf.m94i("ActivityManager", "Stopping pre-created user " + userInfo.toFullString());
                        stopUser(userInfo.id, true, false, null, null);
                        return;
                    }
                    this.mInjector.startUserWidgets(i);
                    this.mHandler.obtainMessage(105, i, 0).sendToTarget();
                    Slogf.m94i("ActivityManager", "Posting BOOT_COMPLETED user #" + i);
                    if (i == 0 && !this.mInjector.isRuntimeRestarted() && !this.mInjector.isFirstBootOrUpgrade()) {
                        FrameworkStatsLog.write(240, 13, SystemClock.elapsedRealtime());
                    }
                    final Intent intent2 = new Intent("android.intent.action.BOOT_COMPLETED", (Uri) null);
                    intent2.putExtra("android.intent.extra.user_handle", i);
                    intent2.addFlags(-1996488704);
                    final int callingUid = Binder.getCallingUid();
                    final int callingPid = Binder.getCallingPid();
                    final int i2 = i;
                    FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserController.this.lambda$finishUserUnlockedCompleted$5(intent2, i2, callingUid, callingPid);
                        }
                    });
                    showEventLog(i, userState.state, 2, "finishUserUnlockedCompleted", "send BOOT COMPLETED and complete NULL");
                    return;
                }
            }
            userInfo = userInfo2;
            i = identifier;
            if (!userInfo.preCreated) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlockedCompleted$4(UserInfo userInfo) {
        this.mInjector.getUserManager().makeInitialized(userInfo.id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlockedCompleted$5(Intent intent, final int i, int i2, int i3) {
        this.mInjector.broadcastIntent(intent, null, new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.3
            public void performReceive(Intent intent2, int i4, String str, Bundle bundle, boolean z, boolean z2, int i5) {
                Slogf.m94i("ActivityManager", "Finished processing BOOT_COMPLETED for u" + i);
                UserController.this.mBootCompleted = true;
            }
        }, 0, null, null, new String[]{"android.permission.RECEIVE_BOOT_COMPLETED"}, -1, getTemporaryAppAllowlistBroadcastOptions(200).toBundle(), true, false, ActivityManagerService.MY_PID, 1000, i2, i3, i);
    }

    /* renamed from: com.android.server.am.UserController$4 */
    public class C06224 implements UserState.KeyEvictedCallback {
        public final /* synthetic */ int val$userStartMode;

        public C06224(int i) {
            this.val$userStartMode = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$keyEvicted$0(int i, int i2) {
            UserController.this.startUser(i, i2);
        }

        @Override // com.android.server.am.UserState.KeyEvictedCallback
        public void keyEvicted(final int i) {
            Handler handler = UserController.this.mHandler;
            final int i2 = this.val$userStartMode;
            handler.post(new Runnable() { // from class: com.android.server.am.UserController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserController.C06224.this.lambda$keyEvicted$0(i, i2);
                }
            });
        }
    }

    public int restartUser(int i, int i2) {
        return stopUser(i, true, false, null, new C06224(i2));
    }

    public boolean stopProfile(int i) {
        boolean z;
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == -1) {
            throw new SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS_FULL permission to stop a profile");
        }
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null || !userInfo.isProfile()) {
            throw new IllegalArgumentException("User " + i + " is not a profile");
        }
        enforceShellRestriction("no_debugging_features", i);
        synchronized (this.mLock) {
            z = stopUsersLU(i, true, false, null, null) == 0;
        }
        return z;
    }

    public int stopUser(int i, boolean z, boolean z2, IStopUserCallback iStopUserCallback, UserState.KeyEvictedCallback keyEvictedCallback) {
        int stopUsersLU;
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "stopUser");
        Preconditions.checkArgument(i >= 0, "Invalid user id %d", new Object[]{Integer.valueOf(i)});
        enforceShellRestriction("no_debugging_features", i);
        synchronized (this.mLock) {
            stopUsersLU = stopUsersLU(i, z, z2, iStopUserCallback, keyEvictedCallback);
        }
        return stopUsersLU;
    }

    public final int stopUsersLU(int i, boolean z, boolean z2, IStopUserCallback iStopUserCallback, UserState.KeyEvictedCallback keyEvictedCallback) {
        if (i == 0) {
            return -3;
        }
        if (isCurrentUserLU(i)) {
            return -2;
        }
        int[] usersToStopLU = getUsersToStopLU(i);
        for (int i2 : usersToStopLU) {
            if (i2 == 0 || isCurrentUserLU(i2)) {
                if (!z) {
                    return -4;
                }
                Slogf.m94i("ActivityManager", "Force stop user " + i + ". Related users will not be stopped");
                stopSingleUserLU(i, z2, iStopUserCallback, keyEvictedCallback);
                return 0;
            }
        }
        int length = usersToStopLU.length;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = usersToStopLU[i3];
            UserState.KeyEvictedCallback keyEvictedCallback2 = null;
            IStopUserCallback iStopUserCallback2 = i4 == i ? iStopUserCallback : null;
            if (i4 == i) {
                keyEvictedCallback2 = keyEvictedCallback;
            }
            stopSingleUserLU(i4, z2, iStopUserCallback2, keyEvictedCallback2);
        }
        return 0;
    }

    public final void stopSingleUserLU(final int i, final boolean z, final IStopUserCallback iStopUserCallback, UserState.KeyEvictedCallback keyEvictedCallback) {
        ArrayList arrayList;
        Slogf.m94i("ActivityManager", "stopSingleUserLU userId=" + i);
        final UserState userState = (UserState) this.mStartedUsers.get(i);
        if (userState == null) {
            if (this.mDelayUserDataLocking) {
                if (z && keyEvictedCallback != null) {
                    Slogf.wtf("ActivityManager", "allowDelayedLocking set with KeyEvictedCallback, ignore it and lock user:" + i, new RuntimeException());
                    z = false;
                }
                if (!z && this.mLastActiveUsers.remove(Integer.valueOf(i))) {
                    if (keyEvictedCallback != null) {
                        arrayList = new ArrayList(1);
                        arrayList.add(keyEvictedCallback);
                    } else {
                        arrayList = null;
                    }
                    dispatchUserLocking(i, arrayList);
                }
            }
            if (iStopUserCallback != null) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserController.lambda$stopSingleUserLU$6(iStopUserCallback, i);
                    }
                });
                return;
            }
            return;
        }
        logUserJourneyBegin(i, 5);
        if (iStopUserCallback != null) {
            userState.mStopCallbacks.add(iStopUserCallback);
        }
        if (keyEvictedCallback != null) {
            userState.mKeyEvictedCallbacks.add(keyEvictedCallback);
        }
        UserInfo userInfo = getUserInfo(i);
        this.mInjector.mService.mActivityTaskManager.mExt.stopUser(i, userInfo == null || !userInfo.isEnabled());
        int i2 = userState.state;
        if (i2 == 4 || i2 == 5) {
            return;
        }
        userState.setState(4);
        UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        userManagerInternal.setUserState(i, userState.state);
        userManagerInternal.unassignUserFromDisplayOnStop(i);
        updateStartedUserArrayLU();
        final Runnable runnable = new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$stopSingleUserLU$8(i, userState, z);
            }
        };
        if (this.mInjector.getUserManager().isPreCreated(i)) {
            runnable.run();
        } else {
            this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    UserController.this.lambda$stopSingleUserLU$9(i, runnable);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$stopSingleUserLU$6(IStopUserCallback iStopUserCallback, int i) {
        try {
            iStopUserCallback.userStopped(i);
        } catch (RemoteException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSingleUserLU$8(final int i, final UserState userState, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$stopSingleUserLU$7(i, userState, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSingleUserLU$9(int i, final Runnable runnable) {
        Intent intent = new Intent("android.intent.action.USER_STOPPING");
        intent.addFlags(1073741824);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.putExtra("android.intent.extra.SHUTDOWN_USERSPACE_ONLY", true);
        IIntentReceiver iIntentReceiver = new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.5
            public void performReceive(Intent intent2, int i2, String str, Bundle bundle, boolean z, boolean z2, int i3) {
                runnable.run();
            }
        };
        this.mInjector.clearBroadcastQueueForUser(i);
        this.mInjector.broadcastIntent(intent, null, iIntentReceiver, 0, null, null, new String[]{"android.permission.INTERACT_ACROSS_USERS"}, -1, null, true, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), -1);
    }

    /* renamed from: finishUserStopping, reason: merged with bridge method [inline-methods] */
    public final void lambda$stopSingleUserLU$7(int i, final UserState userState, final boolean z) {
        EventLog.writeEvent(30073, i);
        synchronized (this.mLock) {
            if (userState.state != 4) {
                UserJourneyLogger.UserJourneySession logUserJourneyFinishWithError = this.mInjector.getUserJourneyLogger().logUserJourneyFinishWithError(-1, getUserInfo(i), 5, 3);
                if (logUserJourneyFinishWithError != null) {
                    this.mHandler.removeMessages(200, logUserJourneyFinishWithError);
                } else {
                    this.mInjector.getUserJourneyLogger().logUserJourneyFinishWithError(-1, getUserInfo(i), 5, 0);
                }
                return;
            }
            userState.setState(5);
            this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
            this.mInjector.batteryStatsServiceNoteEvent(16391, Integer.toString(i), i);
            this.mInjector.getSystemServiceManager().onUserStopping(i);
            final Runnable runnable = new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    UserController.this.lambda$finishUserStopping$11(userState, z);
                }
            };
            if (this.mInjector.getUserManager().isPreCreated(i)) {
                runnable.run();
                return;
            }
            this.mInjector.broadcastIntent(new Intent("android.intent.action.ACTION_SHUTDOWN"), null, new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.6
                public void performReceive(Intent intent, int i2, String str, Bundle bundle, boolean z2, boolean z3, int i3) {
                    runnable.run();
                }
            }, 0, null, null, null, -1, null, true, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserStopping$11(final UserState userState, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$finishUserStopping$10(userState, z);
            }
        });
    }

    /* renamed from: finishUserStopped, reason: merged with bridge method [inline-methods] */
    public void lambda$finishUserStopping$10(UserState userState, boolean z) {
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z2;
        boolean z3;
        int i;
        int identifier = userState.mHandle.getIdentifier();
        EventLog.writeEvent(30074, identifier);
        UserInfo userInfo = getUserInfo(identifier);
        synchronized (this.mLock) {
            arrayList = new ArrayList(userState.mStopCallbacks);
            arrayList2 = new ArrayList(userState.mKeyEvictedCallbacks);
            z2 = false;
            z3 = true;
            if (this.mStartedUsers.get(identifier) == userState && userState.state == 5) {
                Slogf.m94i("ActivityManager", "Removing user state from UserController.mStartedUsers for user #" + identifier + " as a result of user being stopped");
                this.mStartedUsers.remove(identifier);
                this.mUserLru.remove(Integer.valueOf(identifier));
                updateStartedUserArrayLU();
                if (z && !arrayList2.isEmpty()) {
                    Slogf.wtf("ActivityManager", "Delayed locking enabled while KeyEvictedCallbacks not empty, userId:" + identifier + " callbacks:" + arrayList2);
                    z = false;
                }
                i = updateUserToLockLU(identifier, z);
                if (i == -10000) {
                    z3 = false;
                    z2 = true;
                } else {
                    z2 = true;
                }
            }
            i = identifier;
        }
        if (z2) {
            Slogf.m94i("ActivityManager", "Removing user state from UserManager.mUserStates for user #" + identifier + " as a result of user being stopped");
            this.mInjector.getUserManagerInternal().removeUserState(identifier);
            this.mInjector.activityManagerOnUserStopped(identifier);
            forceStopUser(identifier, "finish user");
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            IStopUserCallback iStopUserCallback = (IStopUserCallback) it.next();
            if (z2) {
                try {
                    iStopUserCallback.userStopped(identifier);
                } catch (RemoteException unused) {
                }
            } else {
                iStopUserCallback.userStopAborted(identifier);
            }
        }
        if (z2) {
            this.mInjector.systemServiceManagerOnUserStopped(identifier);
            this.mInjector.taskSupervisorRemoveUser(identifier);
            if (userInfo.isEphemeral() && !userInfo.preCreated) {
                this.mInjector.getUserManager().removeUserEvenWhenDisallowed(identifier);
            }
            UserJourneyLogger.UserJourneySession logUserJourneyFinish = this.mInjector.getUserJourneyLogger().logUserJourneyFinish(-1, userInfo, 5);
            if (logUserJourneyFinish != null) {
                this.mHandler.removeMessages(200, logUserJourneyFinish);
            }
            if (z3) {
                dispatchUserLocking(i, arrayList2);
            }
            resumePendingUserStarts(identifier);
        } else {
            UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(identifier, 5);
            if (finishAndClearIncompleteUserJourney != null) {
                this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
            }
        }
        if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
            DualDARController.getInstance(this.mInjector.getContext()).onUserStopped(identifier);
        }
    }

    public final void resumePendingUserStarts(int i) {
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList();
            for (final PendingUserStart pendingUserStart : this.mPendingUserStarts) {
                if (pendingUserStart.userId == i) {
                    Slogf.m94i("ActivityManager", "resumePendingUserStart for" + pendingUserStart);
                    this.mHandler.post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserController.this.lambda$resumePendingUserStarts$12(pendingUserStart);
                        }
                    });
                    arrayList.add(pendingUserStart);
                }
            }
            this.mPendingUserStarts.removeAll(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resumePendingUserStarts$12(PendingUserStart pendingUserStart) {
        startUser(pendingUserStart.userId, pendingUserStart.userStartMode, pendingUserStart.unlockListener);
    }

    public final void dispatchUserLocking(final int i, final List list) {
        FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$dispatchUserLocking$13(i, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchUserLocking$13(int i, List list) {
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(i) != null) {
                Slogf.m102w("ActivityManager", "User was restarted, skipping key eviction");
                return;
            }
            try {
                Slogf.m94i("ActivityManager", "Locking CE storage for user #" + i);
                this.mInjector.getStorageManager().lockUserKey(i);
                if (list == null) {
                    return;
                }
                for (int i2 = 0; i2 < list.size(); i2++) {
                    ((UserState.KeyEvictedCallback) list.get(i2)).keyEvicted(i);
                }
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public final int updateUserToLockLU(int i, boolean z) {
        if (!this.mDelayUserDataLocking || !z || getUserInfo(i).isEphemeral() || hasUserRestriction("no_run_in_background", i)) {
            return i;
        }
        this.mLastActiveUsers.remove(Integer.valueOf(i));
        this.mLastActiveUsers.add(0, Integer.valueOf(i));
        if (this.mStartedUsers.size() + this.mLastActiveUsers.size() > this.mMaxRunningUsers) {
            int intValue = ((Integer) this.mLastActiveUsers.get(r4.size() - 1)).intValue();
            this.mLastActiveUsers.remove(r2.size() - 1);
            Slogf.m94i("ActivityManager", "finishUserStopped, stopping user:" + i + " lock user:" + intValue);
            return intValue;
        }
        Slogf.m94i("ActivityManager", "finishUserStopped, user:" + i + ", skip locking");
        return -10000;
    }

    public final int[] getUsersToStopLU(int i) {
        int size = this.mStartedUsers.size();
        IntArray intArray = new IntArray();
        intArray.add(i);
        int i2 = this.mUserProfileGroupIds.get(i, -10000);
        for (int i3 = 0; i3 < size; i3++) {
            int identifier = ((UserState) this.mStartedUsers.valueAt(i3)).mHandle.getIdentifier();
            boolean z = i2 != -10000 && i2 == this.mUserProfileGroupIds.get(identifier, -10000);
            boolean z2 = identifier == i;
            if (z && !z2) {
                intArray.add(identifier);
            }
        }
        return intArray.toArray();
    }

    public final void forceStopUser(int i, String str) {
        UserInfo profileParent;
        this.mInjector.activityManagerForceStopPackage(i, str);
        if (this.mInjector.getUserManager().isPreCreated(i)) {
            return;
        }
        Intent intent = new Intent("android.intent.action.USER_STOPPED");
        intent.addFlags(1342177280);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), -1);
        if (!getUserInfo(i).isProfile() || (profileParent = this.mInjector.getUserManager().getProfileParent(i)) == null) {
            return;
        }
        broadcastProfileAccessibleStateChanged(i, profileParent.id, "android.intent.action.PROFILE_INACCESSIBLE");
    }

    public final void stopGuestOrEphemeralUserIfBackground(int i) {
        int i2;
        synchronized (this.mLock) {
            UserState userState = (UserState) this.mStartedUsers.get(i);
            if (i != 0 && i != this.mCurrentUserId && userState != null && (i2 = userState.state) != 4 && i2 != 5) {
                UserInfo userInfo = getUserInfo(i);
                if (userInfo.isEphemeral()) {
                    ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).onEphemeralUserStop(i);
                }
                if (userInfo.isGuest() || userInfo.isEphemeral()) {
                    synchronized (this.mLock) {
                        stopUsersLU(i, true, false, null, null);
                    }
                }
            }
        }
    }

    public void scheduleStartProfiles() {
        FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$scheduleStartProfiles$14();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleStartProfiles$14() {
        if (this.mHandler.hasMessages(40)) {
            return;
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(40), 1000L);
    }

    public final void startProfiles() {
        int currentUserId = getCurrentUserId();
        int i = 0;
        List<UserInfo> profiles = this.mInjector.getUserManager().getProfiles(currentUserId, false);
        ArrayList arrayList = new ArrayList(profiles.size());
        for (UserInfo userInfo : profiles) {
            if ((userInfo.flags & 16) == 16 && userInfo.id != currentUserId && shouldStartWithParent(userInfo)) {
                arrayList.add(userInfo);
            }
        }
        int size = arrayList.size();
        while (i < size && i < getMaxRunningUsers() - 1) {
            startUser(((UserInfo) arrayList.get(i)).id, 3);
            i++;
        }
        if (i < size) {
            Slogf.m102w("ActivityManager", "More profiles than MAX_RUNNING_USERS");
        }
    }

    public final boolean shouldStartWithParent(UserInfo userInfo) {
        UserProperties userProperties = getUserProperties(userInfo.id);
        return userProperties != null && userProperties.getStartWithParent() && (!userInfo.isQuietModeEnabled() || (((DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class)).isKeepProfilesRunningEnabled() && !SemPersonaManager.isSecureFolderId(userInfo.id)));
    }

    public boolean startProfile(int i, boolean z, IProgressListener iProgressListener) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == -1) {
            throw new SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS_FULL permission to start a profile");
        }
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null || !userInfo.isProfile()) {
            throw new IllegalArgumentException("User " + i + " is not a profile");
        }
        if (!userInfo.isEnabled() && !z) {
            Slogf.m105w("ActivityManager", "Cannot start disabled profile #%d", Integer.valueOf(i));
            return false;
        }
        return startUserNoChecks(i, 0, 3, iProgressListener);
    }

    public boolean startUser(int i, int i2) {
        return startUser(i, i2, null);
    }

    public boolean startUser(int i, int i2, IProgressListener iProgressListener) {
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "startUser");
        return startUserNoChecks(i, 0, i2, iProgressListener);
    }

    public boolean startUserVisibleOnDisplay(int i, int i2, IProgressListener iProgressListener) {
        checkCallingHasOneOfThosePermissions("startUserOnDisplay", "android.permission.MANAGE_USERS", "android.permission.INTERACT_ACROSS_USERS");
        try {
            return startUserNoChecks(i, i2, 3, iProgressListener);
        } catch (RuntimeException e) {
            Slogf.m92e("ActivityManager", "startUserOnSecondaryDisplay(%d, %d) failed: %s", Integer.valueOf(i), Integer.valueOf(i2), e);
            return false;
        }
    }

    public final boolean startUserNoChecks(int i, int i2, int i3, IProgressListener iProgressListener) {
        String str;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        if (SemPersonaManager.isKnoxId(i) && PersonaServiceHelper.shouldBlockUserStart(this.mInjector.getContext(), i)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("UserController.startUser-");
        sb.append(i);
        if (i2 == 0) {
            str = "";
        } else {
            str = "-display-" + i2;
        }
        sb.append(str);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x035c A[Catch: all -> 0x038f, TryCatch #5 {all -> 0x038f, blocks: (B:10:0x0054, B:12:0x005f, B:14:0x0065, B:16:0x006f, B:19:0x0074, B:21:0x0078, B:22:0x007b, B:26:0x0083, B:28:0x0088, B:29:0x0098, B:31:0x00a6, B:35:0x00c3, B:37:0x00c9, B:42:0x0111, B:44:0x0129, B:47:0x0153, B:48:0x015b, B:56:0x01cd, B:57:0x01d2, B:59:0x01d7, B:60:0x01eb, B:62:0x01f3, B:63:0x01fc, B:67:0x0206, B:69:0x021e, B:71:0x0234, B:72:0x0254, B:74:0x025c, B:75:0x0274, B:79:0x0279, B:81:0x02a9, B:83:0x02ad, B:84:0x02cd, B:86:0x02ed, B:88:0x02f3, B:90:0x02f9, B:91:0x0306, B:93:0x030e, B:94:0x033e, B:98:0x0345, B:103:0x0357, B:105:0x035c, B:110:0x0378, B:111:0x0369, B:112:0x0354, B:118:0x027f, B:121:0x0283, B:122:0x029a, B:126:0x029f, B:130:0x02a6, B:135:0x023c, B:136:0x023d, B:137:0x0248, B:144:0x038b, B:154:0x038e, B:155:0x00ed, B:157:0x00f1, B:124:0x029b, B:125:0x029e, B:139:0x0249, B:140:0x0253, B:65:0x01fd, B:66:0x0205, B:50:0x015c, B:52:0x0166, B:53:0x01ba, B:54:0x01ca, B:145:0x0185, B:147:0x018a, B:148:0x01b2, B:77:0x0275, B:78:0x0278), top: B:9:0x0054, inners: #0, #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0369 A[Catch: all -> 0x038f, TryCatch #5 {all -> 0x038f, blocks: (B:10:0x0054, B:12:0x005f, B:14:0x0065, B:16:0x006f, B:19:0x0074, B:21:0x0078, B:22:0x007b, B:26:0x0083, B:28:0x0088, B:29:0x0098, B:31:0x00a6, B:35:0x00c3, B:37:0x00c9, B:42:0x0111, B:44:0x0129, B:47:0x0153, B:48:0x015b, B:56:0x01cd, B:57:0x01d2, B:59:0x01d7, B:60:0x01eb, B:62:0x01f3, B:63:0x01fc, B:67:0x0206, B:69:0x021e, B:71:0x0234, B:72:0x0254, B:74:0x025c, B:75:0x0274, B:79:0x0279, B:81:0x02a9, B:83:0x02ad, B:84:0x02cd, B:86:0x02ed, B:88:0x02f3, B:90:0x02f9, B:91:0x0306, B:93:0x030e, B:94:0x033e, B:98:0x0345, B:103:0x0357, B:105:0x035c, B:110:0x0378, B:111:0x0369, B:112:0x0354, B:118:0x027f, B:121:0x0283, B:122:0x029a, B:126:0x029f, B:130:0x02a6, B:135:0x023c, B:136:0x023d, B:137:0x0248, B:144:0x038b, B:154:0x038e, B:155:0x00ed, B:157:0x00f1, B:124:0x029b, B:125:0x029e, B:139:0x0249, B:140:0x0253, B:65:0x01fd, B:66:0x0205, B:50:0x015c, B:52:0x0166, B:53:0x01ba, B:54:0x01ca, B:145:0x0185, B:147:0x018a, B:148:0x01b2, B:77:0x0275, B:78:0x0278), top: B:9:0x0054, inners: #0, #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x025c A[Catch: all -> 0x038f, TryCatch #5 {all -> 0x038f, blocks: (B:10:0x0054, B:12:0x005f, B:14:0x0065, B:16:0x006f, B:19:0x0074, B:21:0x0078, B:22:0x007b, B:26:0x0083, B:28:0x0088, B:29:0x0098, B:31:0x00a6, B:35:0x00c3, B:37:0x00c9, B:42:0x0111, B:44:0x0129, B:47:0x0153, B:48:0x015b, B:56:0x01cd, B:57:0x01d2, B:59:0x01d7, B:60:0x01eb, B:62:0x01f3, B:63:0x01fc, B:67:0x0206, B:69:0x021e, B:71:0x0234, B:72:0x0254, B:74:0x025c, B:75:0x0274, B:79:0x0279, B:81:0x02a9, B:83:0x02ad, B:84:0x02cd, B:86:0x02ed, B:88:0x02f3, B:90:0x02f9, B:91:0x0306, B:93:0x030e, B:94:0x033e, B:98:0x0345, B:103:0x0357, B:105:0x035c, B:110:0x0378, B:111:0x0369, B:112:0x0354, B:118:0x027f, B:121:0x0283, B:122:0x029a, B:126:0x029f, B:130:0x02a6, B:135:0x023c, B:136:0x023d, B:137:0x0248, B:144:0x038b, B:154:0x038e, B:155:0x00ed, B:157:0x00f1, B:124:0x029b, B:125:0x029e, B:139:0x0249, B:140:0x0253, B:65:0x01fd, B:66:0x0205, B:50:0x015c, B:52:0x0166, B:53:0x01ba, B:54:0x01ca, B:145:0x0185, B:147:0x018a, B:148:0x01b2, B:77:0x0275, B:78:0x0278), top: B:9:0x0054, inners: #0, #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ad A[Catch: all -> 0x038f, TryCatch #5 {all -> 0x038f, blocks: (B:10:0x0054, B:12:0x005f, B:14:0x0065, B:16:0x006f, B:19:0x0074, B:21:0x0078, B:22:0x007b, B:26:0x0083, B:28:0x0088, B:29:0x0098, B:31:0x00a6, B:35:0x00c3, B:37:0x00c9, B:42:0x0111, B:44:0x0129, B:47:0x0153, B:48:0x015b, B:56:0x01cd, B:57:0x01d2, B:59:0x01d7, B:60:0x01eb, B:62:0x01f3, B:63:0x01fc, B:67:0x0206, B:69:0x021e, B:71:0x0234, B:72:0x0254, B:74:0x025c, B:75:0x0274, B:79:0x0279, B:81:0x02a9, B:83:0x02ad, B:84:0x02cd, B:86:0x02ed, B:88:0x02f3, B:90:0x02f9, B:91:0x0306, B:93:0x030e, B:94:0x033e, B:98:0x0345, B:103:0x0357, B:105:0x035c, B:110:0x0378, B:111:0x0369, B:112:0x0354, B:118:0x027f, B:121:0x0283, B:122:0x029a, B:126:0x029f, B:130:0x02a6, B:135:0x023c, B:136:0x023d, B:137:0x0248, B:144:0x038b, B:154:0x038e, B:155:0x00ed, B:157:0x00f1, B:124:0x029b, B:125:0x029e, B:139:0x0249, B:140:0x0253, B:65:0x01fd, B:66:0x0205, B:50:0x015c, B:52:0x0166, B:53:0x01ba, B:54:0x01ca, B:145:0x0185, B:147:0x018a, B:148:0x01b2, B:77:0x0275, B:78:0x0278), top: B:9:0x0054, inners: #0, #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x030e A[Catch: all -> 0x038f, TryCatch #5 {all -> 0x038f, blocks: (B:10:0x0054, B:12:0x005f, B:14:0x0065, B:16:0x006f, B:19:0x0074, B:21:0x0078, B:22:0x007b, B:26:0x0083, B:28:0x0088, B:29:0x0098, B:31:0x00a6, B:35:0x00c3, B:37:0x00c9, B:42:0x0111, B:44:0x0129, B:47:0x0153, B:48:0x015b, B:56:0x01cd, B:57:0x01d2, B:59:0x01d7, B:60:0x01eb, B:62:0x01f3, B:63:0x01fc, B:67:0x0206, B:69:0x021e, B:71:0x0234, B:72:0x0254, B:74:0x025c, B:75:0x0274, B:79:0x0279, B:81:0x02a9, B:83:0x02ad, B:84:0x02cd, B:86:0x02ed, B:88:0x02f3, B:90:0x02f9, B:91:0x0306, B:93:0x030e, B:94:0x033e, B:98:0x0345, B:103:0x0357, B:105:0x035c, B:110:0x0378, B:111:0x0369, B:112:0x0354, B:118:0x027f, B:121:0x0283, B:122:0x029a, B:126:0x029f, B:130:0x02a6, B:135:0x023c, B:136:0x023d, B:137:0x0248, B:144:0x038b, B:154:0x038e, B:155:0x00ed, B:157:0x00f1, B:124:0x029b, B:125:0x029e, B:139:0x0249, B:140:0x0253, B:65:0x01fd, B:66:0x0205, B:50:0x015c, B:52:0x0166, B:53:0x01ba, B:54:0x01ca, B:145:0x0185, B:147:0x018a, B:148:0x01b2, B:77:0x0275, B:78:0x0278), top: B:9:0x0054, inners: #0, #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0342  */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startUserInternal(int i, int i2, int i3, IProgressListener iProgressListener, TimingsTraceAndSlog timingsTraceAndSlog) {
        boolean z;
        boolean z2;
        int i4;
        boolean z3;
        boolean z4;
        ?? r8 = i3 == 1 ? 1 : 0;
        boolean z5 = i2 != 0;
        if (z5) {
            Preconditions.checkArgument(r8 ^ 1, "Cannot start user %d in foreground AND on secondary display (%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        }
        EventLog.writeEvent(30076, Integer.valueOf(i), Integer.valueOf((int) r8), Integer.valueOf(i2));
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            timingsTraceAndSlog.traceBegin("getStartedUserState");
            int currentUserId = getCurrentUserId();
            if (currentUserId == i) {
                UserState startedUserState = getStartedUserState(i);
                if (startedUserState == null) {
                    Slogf.wtf("ActivityManager", "Current user has no UserState");
                } else if (i != 0 || startedUserState.state != 0) {
                    if (startedUserState.state == 3) {
                        notifyFinished(i, iProgressListener);
                    }
                    timingsTraceAndSlog.traceEnd();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (r8 != 0) {
                timingsTraceAndSlog.traceBegin("clearAllLockedTasks");
                this.mInjector.clearAllLockedTasks("startUser");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("getUserInfo");
            UserInfo userInfo = getUserInfo(i);
            timingsTraceAndSlog.traceEnd();
            if (userInfo == null) {
                Slogf.m102w("ActivityManager", "No user info for user #" + i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if (r8 != 0 && userInfo.isProfile()) {
                Slogf.m102w("ActivityManager", "Cannot switch to User #" + i + ": not a full user");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if ((r8 != 0 || z5) && userInfo.preCreated) {
                Slogf.m102w("ActivityManager", "Cannot start pre-created user #" + i + " in foreground or on secondary display");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            timingsTraceAndSlog.traceBegin("assignUserToDisplayOnStart");
            int assignUserToDisplayOnStart = this.mInjector.getUserManagerInternal().assignUserToDisplayOnStart(i, userInfo.profileGroupId, i3, i2);
            timingsTraceAndSlog.traceEnd();
            if (assignUserToDisplayOnStart == -1) {
                Slogf.m92e("ActivityManager", "%s user(%d) / display (%d) assignment failed: %s", UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userAssignmentResultToString(assignUserToDisplayOnStart));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            timingsTraceAndSlog.traceBegin("updateStartedUserArrayStarting");
            synchronized (this.mLock) {
                UserState userState = (UserState) this.mStartedUsers.get(i);
                if (userState == null) {
                    userState = new UserState(UserHandle.of(i));
                    userState.mUnlockProgress.addListener(new UserProgressListener());
                    this.mStartedUsers.put(i, userState);
                    updateStartedUserArrayLU();
                    z = true;
                    z2 = true;
                } else {
                    if (userState.state == 5) {
                        Slogf.m94i("ActivityManager", "User #" + i + " is shutting down - will start after full shutdown");
                        this.mPendingUserStarts.add(new PendingUserStart(i, i3, iProgressListener));
                        timingsTraceAndSlog.traceEnd();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                    z = false;
                    z2 = false;
                }
                Integer valueOf = Integer.valueOf(i);
                boolean z6 = z;
                this.mUserLru.remove(valueOf);
                this.mUserLru.add(valueOf);
                if (iProgressListener != null) {
                    userState.mUnlockProgress.addListener(iProgressListener);
                }
                timingsTraceAndSlog.traceEnd();
                if (z2) {
                    timingsTraceAndSlog.traceBegin("setUserState");
                    this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("updateConfigurationAndProfileIds");
                if (r8 == 0) {
                    Integer valueOf2 = Integer.valueOf(this.mCurrentUserId);
                    updateProfileRelatedCaches();
                    synchronized (this.mLock) {
                        this.mUserLru.remove(valueOf2);
                        this.mUserLru.add(valueOf2);
                    }
                    timingsTraceAndSlog.traceEnd();
                    i4 = userState.state;
                    if (i4 != 4) {
                    }
                    if (userState.state != 0) {
                    }
                    this.mInjector.mService.mActivityTaskManager.mExt.startUser(i, r8, z3);
                    this.mInjector.mService.mActivityTaskManager.mMultiTaskingController.startUser(i, r8, z3);
                    if (SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                        DualDARController.getInstance(this.mInjector.getContext()).onUserStart(i);
                    }
                    timingsTraceAndSlog.traceBegin("sendMessages");
                    if (r8 != 0) {
                    }
                    if (userInfo.preCreated) {
                    }
                    if (i != 0) {
                    }
                    if (!z3) {
                    }
                    sendUserStartedBroadcast(i, callingUid, callingPid);
                    timingsTraceAndSlog.traceEnd();
                    if (r8 == 0) {
                    }
                    if (!z3) {
                    }
                    timingsTraceAndSlog.traceBegin("sendRestartBroadcast");
                    sendUserStartingBroadcast(i, callingUid, callingPid);
                    timingsTraceAndSlog.traceEnd();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                this.mInjector.reportGlobalUsageEvent(16);
                synchronized (this.mLock) {
                    this.mCurrentUserId = i;
                    this.mTargetUserId = -10000;
                    z4 = this.mUserSwitchUiEnabled;
                }
                this.mInjector.updateUserConfiguration();
                updateProfileRelatedCaches();
                this.mInjector.getWindowManager().setCurrentUser(i);
                this.mInjector.reportCurWakefulnessUsageEvent();
                if (z4) {
                    this.mInjector.getWindowManager().setSwitchingUser(true);
                    if (this.mInjector.getKeyguardManager().isDeviceSecure(i)) {
                        this.mInjector.lockDeviceNowAndWaitForKeyguardShown();
                    }
                }
                timingsTraceAndSlog.traceEnd();
                i4 = userState.state;
                if (i4 != 4) {
                    timingsTraceAndSlog.traceBegin("updateStateStopping");
                    userState.setState(userState.lastState);
                    this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                    synchronized (this.mLock) {
                        updateStartedUserArrayLU();
                    }
                    timingsTraceAndSlog.traceEnd();
                    z3 = true;
                } else if (i4 == 5) {
                    timingsTraceAndSlog.traceBegin("updateStateShutdown");
                    userState.setState(0);
                    this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                    synchronized (this.mLock) {
                        updateStartedUserArrayLU();
                    }
                    timingsTraceAndSlog.traceEnd();
                    z3 = true;
                } else {
                    z3 = z6;
                }
                if (userState.state != 0) {
                    timingsTraceAndSlog.traceBegin("updateStateBooting");
                    this.mInjector.getUserManager().onBeforeStartUser(i);
                    Handler handler = this.mHandler;
                    handler.sendMessage(handler.obtainMessage(50, i, 0));
                    timingsTraceAndSlog.traceEnd();
                }
                this.mInjector.mService.mActivityTaskManager.mExt.startUser(i, r8, z3);
                this.mInjector.mService.mActivityTaskManager.mMultiTaskingController.startUser(i, r8, z3);
                if (SemPersonaManager.isDarDualEncryptionEnabled(i) && SemPersonaManager.isDarDualEncrypted(i) && userInfo.isInitialized()) {
                    DualDARController.getInstance(this.mInjector.getContext()).onUserStart(i);
                }
                timingsTraceAndSlog.traceBegin("sendMessages");
                if (r8 != 0) {
                    Handler handler2 = this.mHandler;
                    handler2.sendMessage(handler2.obtainMessage(60, i, currentUserId));
                    this.mHandler.removeMessages(10);
                    this.mHandler.removeMessages(30);
                    Handler handler3 = this.mHandler;
                    handler3.sendMessage(handler3.obtainMessage(10, currentUserId, i, userState));
                    Handler handler4 = this.mHandler;
                    handler4.sendMessageDelayed(handler4.obtainMessage(30, currentUserId, i, userState), getUserSwitchTimeoutMs());
                }
                if (userInfo.preCreated) {
                    z3 = false;
                }
                boolean z7 = i != 0 && this.mInjector.isHeadlessSystemUserMode();
                if (!z3 || z7) {
                    sendUserStartedBroadcast(i, callingUid, callingPid);
                }
                timingsTraceAndSlog.traceEnd();
                if (r8 == 0) {
                    timingsTraceAndSlog.traceBegin("moveUserToForeground");
                    moveUserToForeground(userState, i);
                    timingsTraceAndSlog.traceEnd();
                } else {
                    timingsTraceAndSlog.traceBegin("finishUserBoot");
                    finishUserBoot(userState);
                    timingsTraceAndSlog.traceEnd();
                }
                if (!z3 || z7) {
                    timingsTraceAndSlog.traceBegin("sendRestartBroadcast");
                    sendUserStartingBroadcast(i, callingUid, callingPid);
                    timingsTraceAndSlog.traceEnd();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void startUserInForeground(int i) {
        if (startUser(i, 1)) {
            return;
        }
        this.mInjector.getWindowManager().setSwitchingUser(false);
        this.mTargetUserId = -10000;
        dismissUserSwitchDialog(null);
    }

    public boolean unlockUser(int i, IProgressListener iProgressListener) {
        showEventLog(i, -1, 0, "unlockUser", "NULL");
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "unlockUser");
        EventLog.writeEvent(30077, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            showEventLog(i, -1, 1, "unlockUser", "call maybeUnlockUser");
            return maybeUnlockUser(i, iProgressListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void notifyFinished(int i, IProgressListener iProgressListener) {
        if (iProgressListener == null) {
            return;
        }
        try {
            iProgressListener.onFinished(i, (Bundle) null);
        } catch (RemoteException unused) {
        }
    }

    public final boolean maybeUnlockUser(int i) {
        showEventLog(i, -1, 0, "maybeUnlockUser", "NULL and no exit");
        return maybeUnlockUser(i, null);
    }

    public final boolean maybeUnlockUser(int i, IProgressListener iProgressListener) {
        UserState userState;
        int size;
        int[] iArr;
        showEventLog(i, -1, 0, "maybeUnlockUser", "NULL");
        if (!this.mAllowUserUnlocking) {
            Slogf.m96i("ActivityManager", "Not unlocking user %d yet because boot hasn't completed", Integer.valueOf(i));
            notifyFinished(i, iProgressListener);
            return false;
        }
        if (!StorageManager.isUserKeyUnlocked(i)) {
            this.mLockPatternUtils.unlockUserKeyIfUnsecured(i);
        }
        synchronized (this.mLock) {
            userState = (UserState) this.mStartedUsers.get(i);
            if (userState != null) {
                userState.mUnlockProgress.addListener(iProgressListener);
            }
        }
        if (userState == null) {
            notifyFinished(i, iProgressListener);
            showEventLog(i, -1, 2, "unlockUserCleared", "NULL and return");
            return false;
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("finishUserUnlocking-" + i);
        boolean finishUserUnlocking = finishUserUnlocking(userState);
        timingsTraceAndSlog.traceEnd();
        if (!finishUserUnlocking) {
            notifyFinished(i, iProgressListener);
            return false;
        }
        synchronized (this.mLock) {
            size = this.mStartedUsers.size();
            iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.mStartedUsers.keyAt(i2);
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = iArr[i3];
            UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(i4);
            if (profileParent != null && profileParent.id == i && i4 != i) {
                Slogf.m86d("ActivityManager", "User " + i4 + " (parent " + profileParent.id + "): attempting unlock because parent was just unlocked");
                maybeUnlockUser(i4);
            }
        }
        showEventLog(i, userState.state, 2, "unlockUserCleared", "NULL");
        return true;
    }

    public boolean switchUser(int i) {
        enforceShellRestriction("no_debugging_features", i);
        EventLog.writeEvent(30075, i);
        int currentUserId = getCurrentUserId();
        UserInfo userInfo = getUserInfo(i);
        if (i == currentUserId) {
            Slogf.m94i("ActivityManager", "user #" + i + " is already the current user");
            return true;
        }
        if (userInfo == null) {
            Slogf.m102w("ActivityManager", "No user info for user #" + i);
            return false;
        }
        if (!userInfo.supportsSwitchTo()) {
            Slogf.m102w("ActivityManager", "Cannot switch to User #" + i + ": not supported");
            return false;
        }
        if (FactoryResetter.isFactoryResetting()) {
            Slogf.m102w("ActivityManager", "Cannot switch to User #" + i + ": factory reset in progress");
            return false;
        }
        if (getDexMode() != 0) {
            Slog.w("ActivityManager", "Cannot switch to User #" + i + ": in dex mode");
            this.mInjector.mService.mActivityTaskManager.mMultiTaskingController.showCanNotSwitchUserToast();
            return false;
        }
        if (MaintenanceModeManager.isInMaintenanceMode() && 77 != i) {
            Slog.i("ActivityManager", "Cannot switch to User #" + i + " in Maintenance mode");
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mInitialized) {
                Slogf.m90e("ActivityManager", "Cannot switch to User #" + i + ": UserController not ready yet");
                return false;
            }
            this.mTargetUserId = i;
            boolean z = this.mUserSwitchUiEnabled;
            if (z) {
                Pair pair = new Pair(getUserInfo(currentUserId), userInfo);
                this.mUiHandler.removeMessages(1000);
                Handler handler = this.mUiHandler;
                handler.sendMessage(handler.obtainMessage(1000, pair));
            } else {
                sendStartUserSwitchFgMessage(i);
            }
            return true;
        }
    }

    public final void sendStartUserSwitchFgMessage(int i) {
        this.mHandler.removeMessages(120);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(120, i, 0));
    }

    public final void dismissUserSwitchDialog(Runnable runnable) {
        this.mInjector.dismissUserSwitchingDialog(runnable);
    }

    public void dismissUserSwitchingDialog(int i) {
        this.mInjector.dismissUserSwitchingDialog(null);
    }

    public final void showUserSwitchDialog(final Pair pair) {
        this.mInjector.showUserSwitchingDialog((UserInfo) pair.first, (UserInfo) pair.second, getSwitchingFromSystemUserMessageUnchecked(), getSwitchingToSystemUserMessageUnchecked(), new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$showUserSwitchDialog$15(pair);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUserSwitchDialog$15(Pair pair) {
        sendStartUserSwitchFgMessage(((UserInfo) pair.second).id);
    }

    public final void dispatchForegroundProfileChanged(int i) {
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mUserSwitchObservers.getBroadcastItem(i2).onForegroundProfileSwitch(i);
            } catch (RemoteException unused) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
    }

    public void dispatchUserSwitchComplete(int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("dispatchUserSwitchComplete-" + i2);
        this.mInjector.getWindowManager().setSwitchingUser(false);
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
    }

    public final void dispatchLockedBootComplete(int i) {
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mUserSwitchObservers.getBroadcastItem(i2).onLockedBootComplete(i);
            } catch (RemoteException unused) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f A[Catch: all -> 0x0018, TryCatch #0 {all -> 0x0018, blocks: (B:15:0x000f, B:9:0x001d, B:11:0x001f, B:12:0x0028), top: B:14:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001d A[Catch: all -> 0x0018, DONT_GENERATE, TryCatch #0 {all -> 0x0018, blocks: (B:15:0x000f, B:9:0x001d, B:11:0x001f, B:12:0x0028), top: B:14:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void stopUserOnSwitchIfEnforced(int i) {
        boolean z;
        if (i == 0) {
            return;
        }
        boolean hasUserRestriction = hasUserRestriction("no_run_in_background", i);
        synchronized (this.mLock) {
            if (!hasUserRestriction) {
                try {
                    if (!shouldStopUserOnSwitch()) {
                        z = false;
                        if (z) {
                            return;
                        }
                        stopUsersLU(i, false, true, null, null);
                        return;
                    }
                } finally {
                }
            }
            z = true;
            if (z) {
            }
        }
    }

    public final void timeoutUserSwitch(UserState userState, int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("ActivityManager");
        timingsTraceAndSlog.traceBegin("timeoutUserSwitch-" + i + "-to-" + i2);
        synchronized (this.mLock) {
            Slogf.m90e("ActivityManager", "User switch timeout: from " + i + " to " + i2);
            this.mTimeoutUserSwitchCallbacks = this.mCurWaitingUserSwitchCallbacks;
            this.mHandler.removeMessages(90);
            sendContinueUserSwitchLU(userState, i, i2);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(90, i, i2), 5000L);
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void timeoutUserSwitchCallbacks(int i, int i2) {
        synchronized (this.mLock) {
            ArraySet arraySet = this.mTimeoutUserSwitchCallbacks;
            if (arraySet != null && !arraySet.isEmpty()) {
                Slogf.wtf("ActivityManager", "User switch timeout: from " + i + " to " + i2 + ". Observers that didn't respond: " + this.mTimeoutUserSwitchCallbacks);
                this.mTimeoutUserSwitchCallbacks = null;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.server.am.UserController] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r3v10, types: [android.util.TimingsTraceLog] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v3, types: [long] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.lang.String] */
    public void dispatchUserSwitch(final UserState userState, final int i, final int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog;
        UserController userController;
        ?? r2;
        final long j;
        final AtomicInteger atomicInteger;
        ArraySet arraySet;
        int i3;
        ?? r4;
        UserController userController2 = this;
        int i4 = i2;
        TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
        timingsTraceAndSlog2.traceBegin("dispatchUserSwitch-" + i + "-to-" + i4);
        EventLog.writeEvent(30079, Integer.valueOf(i), Integer.valueOf(i2));
        int beginBroadcast = userController2.mUserSwitchObservers.beginBroadcast();
        if (beginBroadcast > 0) {
            int i5 = 0;
            for (int i6 = 0; i6 < beginBroadcast; i6++) {
                String str = "#" + i6 + " " + userController2.mUserSwitchObservers.getBroadcastCookie(i6);
                StringBuilder sb = new StringBuilder();
                r4 = "onBeforeUserSwitching-";
                sb.append("onBeforeUserSwitching-");
                sb.append(str);
                timingsTraceAndSlog2.traceBegin(sb.toString());
                try {
                    userController2.mUserSwitchObservers.getBroadcastItem(i6).onBeforeUserSwitching(i4);
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    timingsTraceAndSlog2.traceEnd();
                    throw th;
                }
                timingsTraceAndSlog2.traceEnd();
            }
            final ArraySet arraySet2 = new ArraySet();
            synchronized (userController2.mLock) {
                r2 = 1;
                userState.switching = true;
                userController2.mCurWaitingUserSwitchCallbacks = arraySet2;
            }
            AtomicInteger atomicInteger2 = new AtomicInteger(beginBroadcast);
            long userSwitchTimeoutMs = getUserSwitchTimeoutMs();
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            int i7 = r4;
            while (true) {
                int i8 = i5;
                if (i8 >= beginBroadcast) {
                    timingsTraceAndSlog = timingsTraceAndSlog2;
                    userController = userController2;
                    break;
                }
                final ?? elapsedRealtime2 = SystemClock.elapsedRealtime();
                try {
                    final String str2 = "#" + i8 + " " + userController2.mUserSwitchObservers.getBroadcastCookie(i8);
                    synchronized (userController2.mLock) {
                        try {
                            arraySet2.add(str2);
                        } finally {
                            th = th;
                            while (true) {
                                try {
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                        }
                    }
                    j = userSwitchTimeoutMs;
                    atomicInteger = atomicInteger2;
                    arraySet = arraySet2;
                    i3 = beginBroadcast;
                    TimingsTraceAndSlog timingsTraceAndSlog3 = timingsTraceAndSlog2;
                    try {
                        IRemoteCallback.Stub stub = new IRemoteCallback.Stub() { // from class: com.android.server.am.UserController.7
                            public void sendResult(Bundle bundle) {
                                synchronized (UserController.this.mLock) {
                                    long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
                                    if (elapsedRealtime3 > 500) {
                                        Slogf.m102w("ActivityManager", "User switch slowed down by observer " + str2 + ": result took " + elapsedRealtime3 + " ms to process.");
                                    }
                                    long elapsedRealtime4 = SystemClock.elapsedRealtime() - elapsedRealtime;
                                    if (elapsedRealtime4 > j) {
                                        Slogf.m90e("ActivityManager", "User switch timeout: observer " + str2 + "'s result was received " + elapsedRealtime4 + " ms after dispatchUserSwitch.");
                                    }
                                    TimingsTraceAndSlog timingsTraceAndSlog4 = new TimingsTraceAndSlog("ActivityManager");
                                    timingsTraceAndSlog4.traceBegin("onUserSwitchingReply-" + str2);
                                    arraySet2.remove(str2);
                                    if (atomicInteger.decrementAndGet() == 0 && arraySet2 == UserController.this.mCurWaitingUserSwitchCallbacks) {
                                        UserController.this.sendContinueUserSwitchLU(userState, i, i2);
                                    }
                                    timingsTraceAndSlog4.traceEnd();
                                }
                            }
                        };
                        timingsTraceAndSlog3.traceBegin("onUserSwitching-" + str2);
                        r2 = this;
                        elapsedRealtime2 = timingsTraceAndSlog3;
                        try {
                            i7 = i8;
                            try {
                                i8 = i2;
                                try {
                                    r2.mUserSwitchObservers.getBroadcastItem(i7).onUserSwitching(i8, stub);
                                    elapsedRealtime2.traceEnd();
                                } catch (RemoteException unused2) {
                                    continue;
                                }
                            } catch (RemoteException unused3) {
                                i8 = i2;
                            }
                        } catch (RemoteException unused4) {
                            i8 = i2;
                            r2 = r2;
                            elapsedRealtime2 = elapsedRealtime2;
                            i7 = i8;
                            i5 = i7 + 1;
                            userController2 = r2;
                            timingsTraceAndSlog2 = elapsedRealtime2;
                            i4 = i8;
                            userSwitchTimeoutMs = j;
                            atomicInteger2 = atomicInteger;
                            arraySet2 = arraySet;
                            beginBroadcast = i3;
                            r2 = r2;
                            i7 = i7;
                        }
                    } catch (RemoteException unused5) {
                        r2 = this;
                        i8 = i2;
                        elapsedRealtime2 = timingsTraceAndSlog3;
                    }
                } catch (RemoteException unused6) {
                    i7 = i8;
                    j = userSwitchTimeoutMs;
                    atomicInteger = atomicInteger2;
                    arraySet = arraySet2;
                    i3 = beginBroadcast;
                    elapsedRealtime2 = timingsTraceAndSlog2;
                    i8 = i4;
                    r2 = userController2;
                }
                i5 = i7 + 1;
                userController2 = r2;
                timingsTraceAndSlog2 = elapsedRealtime2;
                i4 = i8;
                userSwitchTimeoutMs = j;
                atomicInteger2 = atomicInteger;
                arraySet2 = arraySet;
                beginBroadcast = i3;
                r2 = r2;
                i7 = i7;
            }
        } else {
            timingsTraceAndSlog = timingsTraceAndSlog2;
            userController = userController2;
            synchronized (userController.mLock) {
                sendContinueUserSwitchLU(userState, i, i2);
            }
        }
        userController.mUserSwitchObservers.finishBroadcast();
        timingsTraceAndSlog.traceEnd();
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

    public void continueUserSwitch(UserState userState, int i, int i2) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("continueUserSwitch-" + i + "-to-" + i2);
        EventLog.writeEvent(30080, Integer.valueOf(i), Integer.valueOf(i2));
        this.mHandler.removeMessages(130);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(130, i, i2));
        userState.switching = false;
        stopGuestOrEphemeralUserIfBackground(i);
        stopUserOnSwitchIfEnforced(i);
        timingsTraceAndSlog.traceEnd();
    }

    public void completeUserSwitch(final int i, final int i2) {
        final boolean isUserSwitchUiEnabled = isUserSwitchUiEnabled();
        boolean z = isUserSwitchUiEnabled && !this.mInjector.getKeyguardManager().isDeviceSecure(i2);
        final Injector injector = this.mInjector;
        Objects.requireNonNull(injector);
        await(z, new Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UserController.Injector.this.dismissKeyguard((Runnable) obj);
            }
        }, new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$completeUserSwitch$17(isUserSwitchUiEnabled, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$completeUserSwitch$17(boolean z, final int i, final int i2) {
        await(z, new Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UserController.this.dismissUserSwitchDialog((Runnable) obj);
            }
        }, new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                UserController.this.lambda$completeUserSwitch$16(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$completeUserSwitch$16(int i, int i2) {
        this.mHandler.removeMessages(80);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(80, i, i2));
    }

    public final void await(boolean z, Consumer consumer, Runnable runnable) {
        if (z) {
            consumer.accept(runnable);
        } else {
            runnable.run();
        }
    }

    public final void moveUserToForeground(UserState userState, int i) {
        if (this.mInjector.taskSupervisorSwitchUser(i, userState)) {
            this.mInjector.startHomeActivity(i, "moveUserToForeground");
        } else {
            this.mInjector.taskSupervisorResumeFocusedStackTopActivity();
        }
        EventLogTags.writeAmSwitchUser(i);
    }

    public void sendUserStartedBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                if (this.mIsBroadcastSentForSystemUserStarted) {
                    return;
                } else {
                    this.mIsBroadcastSentForSystemUserStarted = true;
                }
            }
        }
        Intent intent = new Intent("android.intent.action.USER_STARTED");
        intent.addFlags(1342177280);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, i2, i3, i);
    }

    public void sendUserStartingBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                if (this.mIsBroadcastSentForSystemUserStarting) {
                    return;
                } else {
                    this.mIsBroadcastSentForSystemUserStarting = true;
                }
            }
        }
        Intent intent = new Intent("android.intent.action.USER_STARTING");
        intent.addFlags(1073741824);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, new IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.8
            public void performReceive(Intent intent2, int i4, String str, Bundle bundle, boolean z, boolean z2, int i5) {
            }
        }, 0, null, null, new String[]{"android.permission.INTERACT_ACROSS_USERS"}, -1, null, true, false, ActivityManagerService.MY_PID, 1000, i2, i3, -1);
    }

    public void sendUserSwitchBroadcasts(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = "android.intent.extra.USER";
        String str2 = "android.intent.extra.user_handle";
        int i3 = 1342177280;
        if (i >= 0) {
            try {
                List profiles = this.mInjector.getUserManager().getProfiles(i, false);
                int size = profiles.size();
                int i4 = 0;
                while (i4 < size) {
                    int i5 = ((UserInfo) profiles.get(i4)).id;
                    Intent intent = new Intent("android.intent.action.USER_BACKGROUND");
                    intent.addFlags(i3);
                    intent.putExtra(str2, i5);
                    intent.putExtra(str, UserHandle.of(i5));
                    this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, callingUid, callingPid, i5);
                    i4++;
                    size = size;
                    str2 = str2;
                    str = str;
                    i3 = 1342177280;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        String str3 = str2;
        String str4 = str;
        if (i2 >= 0) {
            boolean z = false;
            List profiles2 = this.mInjector.getUserManager().getProfiles(i2, false);
            int size2 = profiles2.size();
            int i6 = 0;
            while (i6 < size2) {
                int i7 = ((UserInfo) profiles2.get(i6)).id;
                Intent intent2 = new Intent("android.intent.action.USER_FOREGROUND");
                intent2.addFlags(1342177280);
                String str5 = str3;
                intent2.putExtra(str5, i7);
                String str6 = str4;
                intent2.putExtra(str6, UserHandle.of(i7));
                this.mInjector.broadcastIntent(intent2, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, callingUid, callingPid, i7);
                i6++;
                size2 = size2;
                z = z;
                str4 = str6;
                str3 = str5;
            }
            Intent intent3 = new Intent("android.intent.action.USER_SWITCHED");
            intent3.addFlags(1342177280);
            intent3.putExtra(str3, i2);
            intent3.putExtra(str4, UserHandle.of(i2));
            Injector injector = this.mInjector;
            String[] strArr = new String[1];
            strArr[z ? 1 : 0] = "android.permission.MANAGE_USERS";
            injector.broadcastIntent(intent3, null, null, 0, null, null, strArr, -1, null, false, false, ActivityManagerService.MY_PID, 1000, callingUid, callingPid, -1);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void broadcastProfileAccessibleStateChanged(int i, int i2, String str) {
        Intent intent = new Intent(str);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(i));
        intent.addFlags(1342177280);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, ActivityManagerService.MY_PID, 1000, Binder.getCallingUid(), Binder.getCallingPid(), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int handleIncomingUser(int i, int i2, int i3, boolean z, int i4, String str, String str2) {
        int i5;
        int userId = UserHandle.getUserId(i2);
        if (userId == i3) {
            return i3;
        }
        if ((SemDualAppManager.isDualAppId(userId) && i3 == 0) || (SemDualAppManager.isDualAppId(i3) && userId == 0)) {
            return i3;
        }
        int unsafeConvertIncomingUser = unsafeConvertIncomingUser(i3);
        if (i2 != 0 && i2 != 1000 && i2 != 5250) {
            boolean isSameProfileGroup = isSameProfileGroup(userId, unsafeConvertIncomingUser);
            boolean z2 = true;
            if (this.mInjector.isCallerRecents(i2) && isSameProfileGroup) {
                i5 = 1;
            } else {
                if (this.mInjector.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2, -1, true) != 0) {
                    if (i4 != 2) {
                        if (!canInteractWithAcrossProfilesPermission(i4, isSameProfileGroup, i, i2, str2)) {
                            if (this.mInjector.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS", i, i2, -1, true) == 0) {
                                if (i4 == 0 || i4 == 3) {
                                    i5 = 1;
                                    z2 = true;
                                } else {
                                    i5 = 1;
                                    if (i4 != 1) {
                                        throw new IllegalArgumentException("Unknown mode: " + i4);
                                    }
                                    z2 = isSameProfileGroup;
                                }
                            }
                        }
                    }
                    z2 = false;
                    i5 = 1;
                }
                i5 = 1;
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
                    if (i4 != 2) {
                        if (i4 == 0 || i4 == 3 || (i4 == i5 && isSameProfileGroup)) {
                            sb.append(" or ");
                            sb.append("android.permission.INTERACT_ACROSS_USERS");
                        }
                        if (isSameProfileGroup && i4 == 3) {
                            sb.append(" or ");
                            sb.append("android.permission.INTERACT_ACROSS_PROFILES");
                        }
                    }
                    String sb2 = sb.toString();
                    Slogf.m102w("ActivityManager", sb2);
                    throw new SecurityException(sb2);
                }
                if (!z) {
                    ensureNotSpecialUser(userId);
                }
                if (i2 == 2000 || userId < 0 || !hasUserRestriction("no_debugging_features", userId)) {
                    return userId;
                }
                throw new SecurityException("Shell does not have permission to access user " + userId + "\n " + Debug.getCallers(3));
            }
        }
        userId = unsafeConvertIncomingUser;
        if (!z) {
        }
        if (i2 == 2000) {
        }
        return userId;
    }

    public final boolean canInteractWithAcrossProfilesPermission(int i, boolean z, int i2, int i3, String str) {
        if (i == 3 && z) {
            return this.mInjector.checkPermissionForPreflight("android.permission.INTERACT_ACROSS_PROFILES", i2, i3, str);
        }
        return false;
    }

    public int unsafeConvertIncomingUser(int i) {
        return (i == -2 || i == -3) ? getCurrentUserId() : i;
    }

    public void ensureNotSpecialUser(int i) {
        if (i >= 0) {
            return;
        }
        throw new IllegalArgumentException("Call does not support special user #" + i);
    }

    public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) {
        Objects.requireNonNull(str, "Observer name cannot be null");
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "registerUserSwitchObserver");
        this.mUserSwitchObservers.register(iUserSwitchObserver, str);
    }

    public void sendForegroundProfileChanged(int i) {
        this.mHandler.removeMessages(70);
        this.mHandler.obtainMessage(70, i, 0).sendToTarget();
    }

    public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) {
        this.mUserSwitchObservers.unregister(iUserSwitchObserver);
    }

    public UserState getStartedUserState(int i) {
        UserState userState;
        synchronized (this.mLock) {
            userState = (UserState) this.mStartedUsers.get(i);
        }
        return userState;
    }

    public boolean hasStartedUserState(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mStartedUsers.get(i) != null;
        }
        return z;
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

    public void setAllowUserUnlocking(boolean z) {
        this.mAllowUserUnlocking = z;
    }

    public void onBootComplete(IIntentReceiver iIntentReceiver) {
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
            if (!this.mInjector.isHeadlessSystemUserMode()) {
                finishUserBoot(userState, iIntentReceiver);
            } else {
                sendLockedBootCompletedBroadcast(iIntentReceiver, keyAt);
                maybeUnlockUser(keyAt);
            }
        }
        showEventLog(0, -1, 2, "onBootComplete", "NULL");
    }

    public void onSystemReady() {
        this.mInjector.getUserManagerInternal().addUserLifecycleListener(this.mUserLifecycleListener);
        updateProfileRelatedCaches();
        this.mInjector.reportCurWakefulnessUsageEvent();
    }

    public void onSystemUserStarting() {
        if (this.mInjector.isHeadlessSystemUserMode()) {
            return;
        }
        this.mInjector.onUserStarting(0);
        this.mInjector.onSystemUserVisibilityChanged(true);
    }

    public final void updateProfileRelatedCaches() {
        List profiles = this.mInjector.getUserManager().getProfiles(getCurrentUserId(), false);
        int size = profiles.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((UserInfo) profiles.get(i)).id;
        }
        List users = this.mInjector.getUserManager().getUsers(false);
        synchronized (this.mLock) {
            this.mCurrentProfileIds = iArr;
            this.mUserProfileGroupIds.clear();
            for (int i2 = 0; i2 < users.size(); i2++) {
                UserInfo userInfo = (UserInfo) users.get(i2);
                int i3 = userInfo.profileGroupId;
                if (i3 != -10000) {
                    this.mUserProfileGroupIds.put(userInfo.id, i3);
                }
            }
        }
    }

    public int[] getStartedUserArray() {
        int[] iArr;
        synchronized (this.mLock) {
            iArr = this.mStartedUserArray;
        }
        return iArr;
    }

    public boolean isUserRunning(int i, int i2) {
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
                return StorageManager.isUserKeyUnlocked(i);
            }
            return false;
        }
        if ((i2 & 4) != 0) {
            int i5 = startedUserState.state;
            if (i5 == 3) {
                return true;
            }
            if (i5 == 4 || i5 == 5) {
                return StorageManager.isUserKeyUnlocked(i);
            }
            return false;
        }
        int i6 = startedUserState.state;
        return (i6 == 4 || i6 == 5) ? false : true;
    }

    public boolean isSystemUserStarted() {
        synchronized (this.mLock) {
            UserState userState = (UserState) this.mStartedUsers.get(0);
            if (userState == null) {
                return false;
            }
            int i = userState.state;
            return i == 1 || i == 2 || i == 3;
        }
    }

    public final void checkGetCurrentUserPermissions() {
        if (this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0 || this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) {
            return;
        }
        String str = "Permission Denial: getCurrentUser() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " requires android.permission.INTERACT_ACROSS_USERS";
        Slogf.m102w("ActivityManager", str);
        throw new SecurityException(str);
    }

    public UserInfo getCurrentUser() {
        UserInfo currentUserLU;
        checkGetCurrentUserPermissions();
        if (this.mTargetUserId == -10000) {
            return getUserInfo(this.mCurrentUserId);
        }
        synchronized (this.mLock) {
            currentUserLU = getCurrentUserLU();
        }
        return currentUserLU;
    }

    public int getCurrentUserIdChecked() {
        checkGetCurrentUserPermissions();
        if (this.mTargetUserId == -10000) {
            return this.mCurrentUserId;
        }
        return getCurrentOrTargetUserId();
    }

    public final UserInfo getCurrentUserLU() {
        return getUserInfo(getCurrentOrTargetUserIdLU());
    }

    public int getCurrentOrTargetUserId() {
        int currentOrTargetUserIdLU;
        synchronized (this.mLock) {
            currentOrTargetUserIdLU = getCurrentOrTargetUserIdLU();
        }
        return currentOrTargetUserIdLU;
    }

    public final int getCurrentOrTargetUserIdLU() {
        return this.mTargetUserId != -10000 ? this.mTargetUserId : this.mCurrentUserId;
    }

    public Pair getCurrentAndTargetUserIds() {
        Pair pair;
        synchronized (this.mLock) {
            pair = new Pair(Integer.valueOf(this.mCurrentUserId), Integer.valueOf(this.mTargetUserId));
        }
        return pair;
    }

    public int getCurrentUserId() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        return i;
    }

    public final boolean isCurrentUserLU(int i) {
        return i == getCurrentOrTargetUserIdLU();
    }

    public int[] getUsers() {
        UserManagerService userManager = this.mInjector.getUserManager();
        return userManager != null ? userManager.getUserIds() : new int[]{0};
    }

    public final UserInfo getUserInfo(int i) {
        return this.mInjector.getUserManager().getUserInfo(i);
    }

    public final UserProperties getUserProperties(int i) {
        return this.mInjector.getUserManagerInternal().getUserProperties(i);
    }

    public int[] getUserIds() {
        return this.mInjector.getUserManager().getUserIds();
    }

    public int[] expandUserId(int i) {
        if (i != -1) {
            return new int[]{i};
        }
        return getUsers();
    }

    public boolean exists(int i) {
        return this.mInjector.getUserManager().exists(i);
    }

    public final void checkCallingPermission(String str, String str2) {
        checkCallingHasOneOfThosePermissions(str2, str);
    }

    public final void checkCallingHasOneOfThosePermissions(String str, String... strArr) {
        if (Binder.getCallingUid() == 2000 && MaintenanceModeManager.isInMaintenanceModeFromProperty()) {
            throw new SecurityException("Cannot control users : unauthorized");
        }
        for (String str2 : strArr) {
            if (this.mInjector.checkCallingPermission(str2) == 0) {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Permission denial: ");
        sb.append(str);
        sb.append("() from pid=");
        sb.append(Binder.getCallingPid());
        sb.append(", uid=");
        sb.append(Binder.getCallingUid());
        sb.append(" requires ");
        sb.append(strArr.length == 1 ? strArr[0] : "one of " + Arrays.toString(strArr));
        String sb2 = sb.toString();
        Slogf.m102w("ActivityManager", sb2);
        throw new SecurityException(sb2);
    }

    public final void enforceShellRestriction(String str, int i) {
        if (Binder.getCallingUid() == 2000) {
            if (i < 0 || hasUserRestriction(str, i)) {
                throw new SecurityException("Shell does not have permission to access user " + i);
            }
        }
    }

    public boolean hasUserRestriction(String str, int i) {
        return this.mInjector.getUserManager().hasUserRestriction(str, i);
    }

    public boolean isSameProfileGroup(int i, int i2) {
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

    public boolean isUserOrItsParentRunning(int i) {
        synchronized (this.mLock) {
            if (isUserRunning(i, 0)) {
                return true;
            }
            int i2 = this.mUserProfileGroupIds.get(i, -10000);
            if (i2 == -10000) {
                return false;
            }
            return isUserRunning(i2, 0);
        }
    }

    public boolean isCurrentProfile(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = ArrayUtils.contains(this.mCurrentProfileIds, i);
        }
        return contains;
    }

    public int[] getCurrentProfileIds() {
        int[] iArr;
        synchronized (this.mLock) {
            iArr = this.mCurrentProfileIds;
        }
        return iArr;
    }

    public final void onUserAdded(UserInfo userInfo) {
        if (!userInfo.isProfile() || userInfo.isCloneProfile()) {
            return;
        }
        synchronized (this.mLock) {
            if (userInfo.profileGroupId == this.mCurrentUserId) {
                this.mCurrentProfileIds = ArrayUtils.appendInt(this.mCurrentProfileIds, userInfo.id);
            }
            this.mUserProfileGroupIds.put(userInfo.id, userInfo.profileGroupId);
        }
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            for (int size = this.mUserProfileGroupIds.size() - 1; size >= 0; size--) {
                if (this.mUserProfileGroupIds.keyAt(size) == i || this.mUserProfileGroupIds.valueAt(size) == i) {
                    this.mUserProfileGroupIds.removeAt(size);
                }
            }
            this.mCurrentProfileIds = ArrayUtils.removeInt(this.mCurrentProfileIds, i);
        }
    }

    public boolean shouldConfirmCredentials(int i) {
        UserProperties userProperties;
        if (getStartedUserState(i) == null || (userProperties = getUserProperties(i)) == null || !userProperties.isCredentialShareableWithParent()) {
            return false;
        }
        if (getPersonaManagerInternal().isKnoxId(i)) {
            return getPersonaManagerInternal().shouldConfirmCredentials(i);
        }
        if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            KeyguardManager keyguardManager = this.mInjector.getKeyguardManager();
            return keyguardManager.isDeviceLocked(i) && keyguardManager.isDeviceSecure(i);
        }
        return isUserRunning(i, 2);
    }

    public final PersonaManagerInternal getPersonaManagerInternal() {
        if (this.mPersonaManagerInternal == null) {
            this.mPersonaManagerInternal = (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
        }
        return this.mPersonaManagerInternal;
    }

    public void setSwitchingFromSystemUserMessage(String str) {
        synchronized (this.mLock) {
            this.mSwitchingFromSystemUserMessage = str;
        }
    }

    public void setSwitchingToSystemUserMessage(String str) {
        synchronized (this.mLock) {
            this.mSwitchingToSystemUserMessage = str;
        }
    }

    public String getSwitchingFromSystemUserMessage() {
        checkHasManageUsersPermission("getSwitchingFromSystemUserMessage()");
        return getSwitchingFromSystemUserMessageUnchecked();
    }

    public String getSwitchingToSystemUserMessage() {
        checkHasManageUsersPermission("getSwitchingToSystemUserMessage()");
        return getSwitchingToSystemUserMessageUnchecked();
    }

    public final String getSwitchingFromSystemUserMessageUnchecked() {
        String str;
        synchronized (this.mLock) {
            str = this.mSwitchingFromSystemUserMessage;
        }
        return str;
    }

    public final String getSwitchingToSystemUserMessageUnchecked() {
        String str;
        synchronized (this.mLock) {
            str = this.mSwitchingToSystemUserMessage;
        }
        return str;
    }

    public final void checkHasManageUsersPermission(String str) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") != -1) {
            return;
        }
        throw new SecurityException("You need MANAGE_USERS permission to call " + str);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            long start = protoOutputStream.start(j);
            int i = 0;
            for (int i2 = 0; i2 < this.mStartedUsers.size(); i2++) {
                UserState userState = (UserState) this.mStartedUsers.valueAt(i2);
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, userState.mHandle.getIdentifier());
                userState.dumpDebug(protoOutputStream, 1146756268034L);
                protoOutputStream.end(start2);
            }
            int i3 = 0;
            while (true) {
                int[] iArr = this.mStartedUserArray;
                if (i3 >= iArr.length) {
                    break;
                }
                protoOutputStream.write(2220498092034L, iArr[i3]);
                i3++;
            }
            for (int i4 = 0; i4 < this.mUserLru.size(); i4++) {
                protoOutputStream.write(2220498092035L, ((Integer) this.mUserLru.get(i4)).intValue());
            }
            if (this.mUserProfileGroupIds.size() > 0) {
                for (int i5 = 0; i5 < this.mUserProfileGroupIds.size(); i5++) {
                    long start3 = protoOutputStream.start(2246267895812L);
                    protoOutputStream.write(1120986464257L, this.mUserProfileGroupIds.keyAt(i5));
                    protoOutputStream.write(1120986464258L, this.mUserProfileGroupIds.valueAt(i5));
                    protoOutputStream.end(start3);
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
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("  mStartedUsers:");
            for (int i = 0; i < this.mStartedUsers.size(); i++) {
                UserState userState = (UserState) this.mStartedUsers.valueAt(i);
                printWriter.print("    User #");
                printWriter.print(userState.mHandle.getIdentifier());
                printWriter.print(": ");
                userState.dump("", printWriter);
            }
            printWriter.print("  mStartedUserArray: [");
            for (int i2 = 0; i2 < this.mStartedUserArray.length; i2++) {
                if (i2 > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(this.mStartedUserArray[i2]);
            }
            printWriter.println("]");
            printWriter.print("  mUserLru: [");
            for (int i3 = 0; i3 < this.mUserLru.size(); i3++) {
                if (i3 > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(this.mUserLru.get(i3));
            }
            printWriter.println("]");
            if (this.mUserProfileGroupIds.size() > 0) {
                printWriter.println("  mUserProfileGroupIds:");
                for (int i4 = 0; i4 < this.mUserProfileGroupIds.size(); i4++) {
                    printWriter.print("    User #");
                    printWriter.print(this.mUserProfileGroupIds.keyAt(i4));
                    printWriter.print(" -> profile #");
                    printWriter.println(this.mUserProfileGroupIds.valueAt(i4));
                }
            }
            printWriter.println("  mCurrentProfileIds:" + Arrays.toString(this.mCurrentProfileIds));
            printWriter.println("  mCurrentUserId:" + this.mCurrentUserId);
            printWriter.println("  mTargetUserId:" + this.mTargetUserId);
            printWriter.println("  mLastActiveUsers:" + this.mLastActiveUsers);
            printWriter.println("  mDelayUserDataLocking:" + this.mDelayUserDataLocking);
            printWriter.println("  mAllowUserUnlocking:" + this.mAllowUserUnlocking);
            printWriter.println("  shouldStopUserOnSwitch():" + shouldStopUserOnSwitch());
            printWriter.println("  mStopUserOnSwitch:" + this.mStopUserOnSwitch);
            printWriter.println("  mMaxRunningUsers:" + this.mMaxRunningUsers);
            printWriter.println("  mUserSwitchUiEnabled:" + this.mUserSwitchUiEnabled);
            printWriter.println("  mInitialized:" + this.mInitialized);
            printWriter.println("  mIsBroadcastSentForSystemUserStarted:" + this.mIsBroadcastSentForSystemUserStarted);
            printWriter.println("  mIsBroadcastSentForSystemUserStarting:" + this.mIsBroadcastSentForSystemUserStarting);
            if (this.mSwitchingFromSystemUserMessage != null) {
                printWriter.println("  mSwitchingFromSystemUserMessage: " + this.mSwitchingFromSystemUserMessage);
            }
            if (this.mSwitchingToSystemUserMessage != null) {
                printWriter.println("  mSwitchingToSystemUserMessage: " + this.mSwitchingToSystemUserMessage);
            }
            printWriter.println("  mLastUserUnlockingUptime: " + this.mLastUserUnlockingUptime);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 10:
                dispatchUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                break;
            case 20:
                continueUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                break;
            case 30:
                timeoutUserSwitch((UserState) message.obj, message.arg1, message.arg2);
                break;
            case 40:
                startProfiles();
                break;
            case 50:
                this.mInjector.batteryStatsServiceNoteEvent(32775, Integer.toString(message.arg1), message.arg1);
                logUserJourneyBegin(message.arg1, 3);
                this.mInjector.onUserStarting(message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 1, 5000);
                this.mInjector.getUserJourneyLogger().logUserJourneyFinish(-1, getUserInfo(message.arg1), 3);
                if (this.mInjector.getSdpManager() != null) {
                    this.mInjector.getSdpManager().onStartUser(message.arg1);
                    break;
                }
                break;
            case 60:
                this.mInjector.batteryStatsServiceNoteEvent(16392, Integer.toString(message.arg2), message.arg2);
                this.mInjector.batteryStatsServiceNoteEvent(32776, Integer.toString(message.arg1), message.arg1);
                this.mInjector.getSystemServiceManager().onUserSwitching(message.arg2, message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 4, 5000);
                break;
            case 70:
                dispatchForegroundProfileChanged(message.arg1);
                break;
            case 80:
                dispatchUserSwitchComplete(message.arg1, message.arg2);
                UserJourneyLogger.UserJourneySession logUserSwitchJourneyFinish = this.mInjector.getUserJourneyLogger().logUserSwitchJourneyFinish(message.arg1, getUserInfo(message.arg2));
                if (logUserSwitchJourneyFinish != null) {
                    this.mHandler.removeMessages(200, logUserSwitchJourneyFinish);
                    break;
                }
                break;
            case 90:
                timeoutUserSwitchCallbacks(message.arg1, message.arg2);
                break;
            case 100:
                final int i = message.arg1;
                showEventLog(i, -1, 0, "SYSTEM_USER_UNLOCK_MSG", "NULL");
                this.mInjector.getSystemServiceManager().onUserUnlocking(i);
                showEventLog(i, -1, 1, "SYSTEM_USER_UNLOCK_MSG", "Done mSystemServiceManager.onUserUnlocking");
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserController.this.lambda$handleMessage$18(i);
                    }
                });
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 5, 2);
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 6, 1);
                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("finishUserUnlocked-" + i);
                finishUserUnlocked((UserState) message.obj);
                timingsTraceAndSlog.traceEnd();
                showEventLog(i, -1, 2, "SYSTEM_USER_UNLOCK_MSG", "NULL");
                break;
            case 105:
                this.mInjector.getSystemServiceManager().onUserUnlocked(message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 2, this.mCurrentUserId != message.arg1 ? 1000 : 5000);
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 6, 2);
                break;
            case 110:
                dispatchLockedBootComplete(message.arg1);
                break;
            case 120:
                logUserJourneyBegin(message.arg1, 2);
                startUserInForeground(message.arg1);
                break;
            case 130:
                completeUserSwitch(message.arg1, message.arg2);
                break;
            case 140:
                reportOnUserCompletedEvent((Integer) message.obj);
                break;
            case 200:
                this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(message.arg1, message.arg2);
                this.mHandler.removeMessages(200, message.obj);
                break;
            case 1000:
                Pair pair = (Pair) message.obj;
                logUserJourneyBegin(((UserInfo) pair.second).id, 1);
                showUserSwitchDialog(pair);
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleMessage$18(int i) {
        this.mInjector.loadUserRecents(i);
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

    public void reportOnUserCompletedEvent(Integer num) {
        int i;
        int i2;
        this.mHandler.removeEqualMessages(140, num);
        synchronized (this.mCompletedEventTypes) {
            i = 0;
            i2 = this.mCompletedEventTypes.get(num.intValue(), 0);
            this.mCompletedEventTypes.delete(num.intValue());
        }
        synchronized (this.mLock) {
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
        }
        Slogf.m96i("ActivityManager", "reportOnUserCompletedEvent(%d): stored=%s, eligible=%s", num, Integer.toBinaryString(i2), Integer.toBinaryString(i));
        this.mInjector.systemServiceManagerOnUserCompletedEvent(num.intValue(), i2 & i);
    }

    public final void logUserJourneyBegin(int i, int i2) {
        UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(i, i2);
        if (finishAndClearIncompleteUserJourney != null) {
            this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
        }
        UserJourneyLogger.UserJourneySession logUserJourneyBegin = this.mInjector.getUserJourneyLogger().logUserJourneyBegin(i, i2);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(200, i, i2, logUserJourneyBegin), 90000L);
    }

    public final BroadcastOptions getTemporaryAppAllowlistBroadcastOptions(int i) {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        long bootTimeTempAllowListDuration = activityManagerInternal != null ? activityManagerInternal.getBootTimeTempAllowListDuration() : 10000L;
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, i, "");
        return makeBasic;
    }

    public static int getUserSwitchTimeoutMs() {
        String str = SystemProperties.get("debug.usercontroller.user_switch_timeout_ms");
        if (TextUtils.isEmpty(str)) {
            return 3000;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return 3000;
        }
    }

    public long getLastUserUnlockingUptime() {
        return this.mLastUserUnlockingUptime;
    }

    public class UserProgressListener extends IProgressListener.Stub {
        public volatile long mUnlockStarted;

        public UserProgressListener() {
        }

        public void onStarted(int i, Bundle bundle) {
            Slogf.m86d("ActivityManager", "Started unlocking user " + i);
            this.mUnlockStarted = SystemClock.uptimeMillis();
        }

        public void onProgress(int i, int i2, Bundle bundle) {
            Slogf.m86d("ActivityManager", "Unlocking user " + i + " progress " + i2);
        }

        public void onFinished(int i, Bundle bundle) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mUnlockStarted;
            if (i == 0) {
                new TimingsTraceAndSlog().logDuration("SystemUserUnlock", uptimeMillis);
                return;
            }
            new TimingsTraceAndSlog().logDuration("User" + i + "Unlock", uptimeMillis);
        }
    }

    public class PendingUserStart {
        public final IProgressListener unlockListener;
        public final int userId;
        public final int userStartMode;

        public PendingUserStart(int i, int i2, IProgressListener iProgressListener) {
            this.userId = i;
            this.userStartMode = i2;
            this.unlockListener = iProgressListener;
        }

        public String toString() {
            return "PendingUserStart{userId=" + this.userId + ", userStartMode=" + UserManagerInternal.userStartModeToString(this.userStartMode) + ", unlockListener=" + this.unlockListener + '}';
        }
    }

    class Injector {
        public Handler mHandler;
        public final ActivityManagerService mService;
        public UserManagerService mUserManager;
        public UserManagerInternal mUserManagerInternal;
        public UserSwitchingDialog mUserSwitchingDialog;
        public final Object mUserSwitchingDialogLock = new Object();

        public SdpManagerImpl getSdpManager() {
            return null;
        }

        public Injector(ActivityManagerService activityManagerService) {
            this.mService = activityManagerService;
        }

        public Handler getHandler(Handler.Callback callback) {
            Handler handler = new Handler(this.mService.mHandlerThread.getLooper(), callback);
            this.mHandler = handler;
            return handler;
        }

        public Handler getUiHandler(Handler.Callback callback) {
            return new Handler(this.mService.mUiHandler.getLooper(), callback);
        }

        public UserJourneyLogger getUserJourneyLogger() {
            return getUserManager().getUserJourneyLogger();
        }

        public Context getContext() {
            return this.mService.mContext;
        }

        public LockPatternUtils getLockPatternUtils() {
            return new LockPatternUtils(getContext());
        }

        public int broadcastIntent(Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3, int i4, int i5, int i6, int i7) {
            int broadcastIntentLocked;
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if (intExtra == -10000) {
                intExtra = i7;
            }
            EventLog.writeEvent(30081, Integer.valueOf(intExtra), intent.getAction());
            ActivityManagerService activityManagerService = this.mService;
            boolean z3 = activityManagerService.mEnableModernQueue ? false : z;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    broadcastIntentLocked = this.mService.broadcastIntentLocked(null, null, null, intent, str, iIntentReceiver, i, str2, bundle, strArr, null, null, i2, bundle2, z3, z2, i3, i4, i5, i6, i7);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            return broadcastIntentLocked;
        }

        public int checkCallingPermission(String str) {
            return this.mService.checkCallingPermission(str);
        }

        public WindowManagerService getWindowManager() {
            return this.mService.mWindowManager;
        }

        public ActivityTaskManagerInternal getActivityTaskManagerInternal() {
            return this.mService.mAtmInternal;
        }

        public void activityManagerOnUserStopped(int i) {
            ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).onUserStopped(i);
        }

        public void systemServiceManagerOnUserStopped(int i) {
            getSystemServiceManager().onUserStopped(i);
            if (getSdpManager() != null) {
                getSdpManager().onCleanupUser(i);
            }
        }

        public void systemServiceManagerOnUserCompletedEvent(int i, int i2) {
            getSystemServiceManager().onUserCompletedEvent(i, i2);
        }

        public UserManagerService getUserManager() {
            if (this.mUserManager == null) {
                this.mUserManager = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
            }
            return this.mUserManager;
        }

        public UserManagerInternal getUserManagerInternal() {
            if (this.mUserManagerInternal == null) {
                this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            return this.mUserManagerInternal;
        }

        public KeyguardManager getKeyguardManager() {
            return (KeyguardManager) this.mService.mContext.getSystemService(KeyguardManager.class);
        }

        public void batteryStatsServiceNoteEvent(int i, String str, int i2) {
            this.mService.mBatteryStatsService.noteEvent(i, str, i2);
        }

        public boolean isRuntimeRestarted() {
            return getSystemServiceManager().isRuntimeRestarted();
        }

        public SystemServiceManager getSystemServiceManager() {
            return this.mService.mSystemServiceManager;
        }

        public boolean isFirstBootOrUpgrade() {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            try {
                if (!packageManager.isFirstBoot()) {
                    if (!packageManager.isDeviceUpgrading()) {
                        return false;
                    }
                }
                return true;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void sendPreBootBroadcast(int i, boolean z, final Runnable runnable) {
            EventLog.writeEvent(30081, Integer.valueOf(i), "android.intent.action.PRE_BOOT_COMPLETED");
            new PreBootBroadcaster(this.mService, i, null, z) { // from class: com.android.server.am.UserController.Injector.1
                @Override // com.android.server.am.PreBootBroadcaster
                public void onFinished() {
                    runnable.run();
                }
            }.sendNext();
        }

        public void activityManagerForceStopPackage(int i, String str) {
            ActivityManagerService activityManagerService = this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    this.mService.forceStopPackageLocked(null, -1, false, false, true, false, false, i, str);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public int checkComponentPermission(String str, int i, int i2, int i3, boolean z) {
            return ActivityManagerService.checkComponentPermission(str, i, i2, i3, z);
        }

        public boolean checkPermissionForPreflight(String str, int i, int i2, String str2) {
            return PermissionChecker.checkPermissionForPreflight(getContext(), str, i, i2, str2) == 0;
        }

        public void startHomeActivity(int i, String str) {
            this.mService.mAtmInternal.startHomeActivity(i, str);
        }

        public void startUserWidgets(final int i) {
            final AppWidgetManagerInternal appWidgetManagerInternal = (AppWidgetManagerInternal) LocalServices.getService(AppWidgetManagerInternal.class);
            if (appWidgetManagerInternal != null) {
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.am.UserController$Injector$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        appWidgetManagerInternal.unlockUser(i);
                    }
                });
            }
        }

        public void updateUserConfiguration() {
            this.mService.mAtmInternal.updateUserConfiguration();
        }

        public void clearBroadcastQueueForUser(int i) {
            ActivityManagerService activityManagerService = this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    this.mService.clearBroadcastQueueForUserLocked(i);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public void loadUserRecents(int i) {
            this.mService.mAtmInternal.loadRecentTasksForUser(i);
        }

        public void startPersistentApps(int i) {
            this.mService.startPersistentApps(i);
        }

        public void installEncryptionUnawareProviders(int i) {
            this.mService.mCpHelper.installEncryptionUnawareProviders(i);
        }

        public void dismissUserSwitchingDialog(Runnable runnable) {
            synchronized (this.mUserSwitchingDialogLock) {
                UserSwitchingDialog userSwitchingDialog = this.mUserSwitchingDialog;
                if (userSwitchingDialog != null) {
                    userSwitchingDialog.dismiss(runnable);
                    this.mUserSwitchingDialog = null;
                } else if (runnable != null) {
                    runnable.run();
                }
            }
        }

        public void showUserSwitchingDialog(UserInfo userInfo, UserInfo userInfo2, String str, String str2, Runnable runnable) {
            if (this.mService.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                Slogf.m102w("ActivityManager", "Showing user switch dialog on UserController, it could cause a race condition if it's shown by CarSystemUI as well");
            }
            synchronized (this.mUserSwitchingDialogLock) {
                dismissUserSwitchingDialog(null);
                UserSwitchingDialog userSwitchingDialog = new UserSwitchingDialog(this.mService.mContext, userInfo, userInfo2, str, str2, getWindowManager());
                this.mUserSwitchingDialog = userSwitchingDialog;
                userSwitchingDialog.show(runnable);
            }
        }

        public void reportGlobalUsageEvent(int i) {
            this.mService.reportGlobalUsageEvent(i);
        }

        public void reportCurWakefulnessUsageEvent() {
            this.mService.reportCurWakefulnessUsageEvent();
        }

        public void taskSupervisorRemoveUser(int i) {
            this.mService.mAtmInternal.removeUser(i);
        }

        public boolean taskSupervisorSwitchUser(int i, UserState userState) {
            return this.mService.mAtmInternal.switchUser(i, userState);
        }

        public void taskSupervisorResumeFocusedStackTopActivity() {
            this.mService.mAtmInternal.resumeTopActivities(false);
        }

        public void clearAllLockedTasks(String str) {
            this.mService.mAtmInternal.clearLockedTasks(str);
        }

        public boolean isCallerRecents(int i) {
            return this.mService.mAtmInternal.isCallerRecents(i);
        }

        public IStorageManager getStorageManager() {
            return IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
        }

        public void dismissKeyguard(final Runnable runnable) {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            final Runnable runnable2 = new Runnable() { // from class: com.android.server.am.UserController$Injector$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserController.Injector.lambda$dismissKeyguard$1(atomicBoolean, runnable);
                }
            };
            this.mHandler.postDelayed(runnable2, 2000L);
            getWindowManager().dismissKeyguard(new IKeyguardDismissCallback.Stub() { // from class: com.android.server.am.UserController.Injector.2
                public void onDismissError() {
                    Injector.this.mHandler.post(runnable2);
                }

                public void onDismissSucceeded() {
                    Injector.this.mHandler.post(runnable2);
                }

                public void onDismissCancelled() {
                    Injector.this.mHandler.post(runnable2);
                }
            }, null);
        }

        public static /* synthetic */ void lambda$dismissKeyguard$1(AtomicBoolean atomicBoolean, Runnable runnable) {
            if (atomicBoolean.getAndSet(false)) {
                runnable.run();
            }
        }

        public boolean isHeadlessSystemUserMode() {
            return UserManager.isHeadlessSystemUserMode();
        }

        public void onUserStarting(int i) {
            getSystemServiceManager().onUserStarting(TimingsTraceAndSlog.newAsyncLog(), i);
        }

        public void onSystemUserVisibilityChanged(boolean z) {
            getUserManagerInternal().onSystemUserVisibilityChanged(z);
        }

        public void lockDeviceNowAndWaitForKeyguardShown() {
            if (getWindowManager().isKeyguardLocked()) {
                return;
            }
            TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
            timingsTraceAndSlog.traceBegin("lockDeviceNowAndWaitForKeyguardShown");
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ActivityTaskManagerInternal.ScreenObserver screenObserver = new ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.am.UserController.Injector.3
                @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                public void onAwakeStateChanged(boolean z) {
                }

                @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                public void onKeyguardStateChanged(boolean z) {
                    if (z) {
                        countDownLatch.countDown();
                    }
                }
            };
            getActivityTaskManagerInternal().registerScreenObserver(screenObserver);
            getWindowManager().lockDeviceNow();
            try {
                try {
                    if (countDownLatch.await(20L, TimeUnit.SECONDS)) {
                    } else {
                        throw new RuntimeException("Keyguard is not shown in 20 seconds");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                getActivityTaskManagerInternal().unregisterScreenObserver(screenObserver);
                timingsTraceAndSlog.traceEnd();
            }
        }
    }

    public final void showEventLog(int i, int i2, int i3, String str, String str2) {
        EventLogTags.writeBootProgressAmsState(i, i2, i3, str, str2);
        Slog.d("ActivityManager", "!@AM_BOOT_PROGRESS , uid : " + i + ", state:  " + i2 + ", progress : " + i3 + ", step : " + str + ", description : " + str2);
    }

    public final int getDexMode() {
        return this.mInjector.mService.mActivityTaskManager.mMultiTaskingController.getDexMode();
    }
}
