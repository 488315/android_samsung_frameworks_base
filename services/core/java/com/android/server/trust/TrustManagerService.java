package com.android.server.trust;

import android.R;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.trust.ITrustListener;
import android.app.trust.ITrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.SensorProperties;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.location.ISignificantPlaceProvider;
import android.hardware.location.ISignificantPlaceProviderManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IProgressListener;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.security.Flags;
import android.security.KeyStoreAuthorization;
import android.service.trust.ITrustAgentService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$ServiceListener;
import com.android.server.servicewatcher.ServiceWatcherImpl;
import com.android.server.utils.Slogf;

import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TrustManagerService extends SystemService {
    public static final boolean DEBUG;
    public static final Intent TRUST_AGENT_INTENT;
    public final ArraySet mActiveAgents;
    public final ActivityManager mActivityManager;
    public final Object mAlarmLock;
    public final AlarmManager mAlarmManager;
    public final TrustArchive mArchive;
    public final Context mContext;
    public int mCurrentUser;
    public final SparseBooleanArray mDeviceLockedForUser;
    public FaceManager mFaceManager;
    public FingerprintManager mFingerprintManager;
    public final AnonymousClass3 mHandler;
    public final SparseArray mIdleTrustableTimeoutAlarmListenerForUser;
    public volatile boolean mIsInSignificantPlace;
    public boolean mIsUnlockedByFP;
    public final KeyStoreAuthorization mKeyStoreAuthorization;
    public final SparseBooleanArray mLastActiveUnlockRunningState;
    public final LockPatternUtils mLockPatternUtils;
    final PackageMonitor mPackageMonitor;
    public PersonaManagerInternal mPersonaManagerInternal;
    public final Receiver mReceiver;
    public final AnonymousClass2 mService;
    public ServiceWatcherImpl mSignificantPlaceServiceWatcher;
    public final StrongAuthTracker mStrongAuthTracker;
    public boolean mTrustAgentsCanRun;
    public final ArrayList mTrustListeners;
    public final ArrayMap mTrustTimeoutAlarmListenerForUser;
    public final SparseBooleanArray mTrustUsuallyManagedForUser;
    public final SparseArray mTrustableTimeoutAlarmListenerForUser;
    public final UserManager mUserManager;
    public final SparseArray mUserTrustState;
    public final SparseBooleanArray mUsersUnlockedByBiometric;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.trust.TrustManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends ITrustManager.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2() {}

        public static String dumpBool(boolean z) {
            return z ? "1" : "0";
        }

        public final void clearAllBiometricRecognized(
                BiometricSourceType biometricSourceType, int i) {
            TrustManagerService trustManagerService;
            enforceReportPermission();
            synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
                TrustManagerService.this.mUsersUnlockedByBiometric.clear();
                trustManagerService = TrustManagerService.this;
                trustManagerService.mIsUnlockedByFP = false;
            }
            Message obtainMessage = trustManagerService.mHandler.obtainMessage(14, -1, 0);
            if (i >= 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("except", i);
                obtainMessage.setData(bundle);
            }
            obtainMessage.sendToTarget();
        }

        public final void dump(
                FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    TrustManagerService.this.mContext, "TrustManagerService", printWriter)) {
                if (TrustManagerService.this.isSafeMode()) {
                    printWriter.println("disabled because the system is in safe mode.");
                    return;
                }
                TrustManagerService trustManagerService = TrustManagerService.this;
                if (!trustManagerService.mTrustAgentsCanRun) {
                    printWriter.println("disabled because the third-party apps can't run yet.");
                } else {
                    final List aliveUsers = trustManagerService.mUserManager.getAliveUsers();
                    runWithScissors(
                            new Runnable() { // from class:
                                // com.android.server.trust.TrustManagerService.2.1
                                /* JADX WARN: Removed duplicated region for block: B:69:0x02cb  */
                                /* JADX WARN: Removed duplicated region for block: B:75:0x02e4  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final void run() {
                                    /*
                                        Method dump skipped, instructions count: 874
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.server.trust.TrustManagerService.AnonymousClass2.AnonymousClass1.run():void");
                                }
                            },
                            1500L);
                }
            }
        }

        public final void enforceReportPermission() {
            TrustManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "reporting trust events");
        }

        public final boolean isActiveUnlockRunning(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TrustManagerService.this.aggregateIsActiveUnlockRunning(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDeviceLocked(int i, int i2) {
            if (i2 != 0) {
                return false;
            }
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            ITrustManager.Stub.getCallingPid(),
                            ITrustManager.Stub.getCallingUid(),
                            i,
                            false,
                            true,
                            "isDeviceLocked",
                            null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                        handleIncomingUser)) {
                    handleIncomingUser =
                            TrustManagerService.this.resolveProfileParent(handleIncomingUser);
                }
                boolean isDeviceLockedInner =
                        TrustManagerService.this.isDeviceLockedInner(handleIncomingUser);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return isDeviceLockedInner;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean isDeviceSecure(int i, int i2) {
            if (i2 != 0) {
                return false;
            }
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            ITrustManager.Stub.getCallingPid(),
                            ITrustManager.Stub.getCallingUid(),
                            i,
                            false,
                            true,
                            "isDeviceSecure",
                            null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                        handleIncomingUser)) {
                    handleIncomingUser =
                            TrustManagerService.this.resolveProfileParent(handleIncomingUser);
                }
                boolean isSecure =
                        TrustManagerService.this.mLockPatternUtils.isSecure(handleIncomingUser);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return isSecure;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean isInSignificantPlace() {
            if (Flags.significantPlaces()) {
                TrustManagerService.this.mSignificantPlaceServiceWatcher.runOnBinder(
                        new TrustManagerService$2$$ExternalSyntheticLambda0());
                return TrustManagerService.this.mIsInSignificantPlace;
            }
            Log.d(
                    "TrustManagerService",
                    "isInSignificantPlace calling uid : " + Binder.getCallingUid());
            return 1
                    == Settings.Secure.getInt(
                            TrustManagerService.this.mContext.getContentResolver(),
                            "in_trusted_location",
                            0);
        }

        public final boolean isTrustUsuallyManaged(int i) {
            isTrustUsuallyManaged_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TrustManagerService.this.isTrustUsuallyManagedInternal(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerTrustListener(ITrustListener iTrustListener) {
            TrustManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.TRUST_LISTENER", "register trust listener");
            obtainMessage(1, iTrustListener).sendToTarget();
        }

        public final void reportEnabledTrustAgentsChanged(int i) {
            enforceReportPermission();
            obtainMessage(4, i, 0).sendToTarget();
        }

        public final void reportKeyguardShowingChanged() {
            enforceReportPermission();
            removeMessages(6);
            sendEmptyMessage(6);
            runWithScissors(new TrustManagerService$$ExternalSyntheticLambda0(1), 0L);
        }

        public final void reportUnlockAttempt(boolean z, int i) {
            if (TrustManagerService.DEBUG) {
                Slogf.d(
                        "TrustManagerService",
                        "reportUnlockAttempt(authenticated=%s, userId=%s)",
                        Boolean.valueOf(z),
                        Integer.valueOf(i));
            }
            enforceReportPermission();
            obtainMessage(3, z ? 1 : 0, i).sendToTarget();
        }

        public final void reportUnlockLockout(int i, int i2) {
            enforceReportPermission();
            obtainMessage(13, i, i2).sendToTarget();
        }

        public final void reportUserMayRequestUnlock(int i) {
            enforceReportPermission();
            obtainMessage(18, i, 0).sendToTarget();
        }

        public final void reportUserRequestedUnlock(int i, boolean z) {
            enforceReportPermission();
            obtainMessage(16, i, z ? 1 : 0).sendToTarget();
        }

        public final void setDeviceLockedForUser(int i, boolean z) {
            enforceReportPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (DualDarManager.isOnDeviceOwner(i)) {
                    synchronized (TrustManagerService.this.mDeviceLockedForUser) {
                        TrustManagerService.this.mDeviceLockedForUser.put(i, z);
                    }
                    DDLog.d(
                            "TrustManagerService",
                            "setDeviceLocked for DualDAR DO. locked state : " + z,
                            new Object[0]);
                    TrustManagerService.this.handleDualDARDeviceLockedChangedForUser(i, z);
                } else if (i == 0 && !z) {
                    DDLog.d(
                            "TrustManagerService",
                            "setDeviceLocked false and update DualDAR user state",
                            new Object[0]);
                    TrustManagerService.this.updateDualDARStateAndUnlockIfNeeded(i);
                }
                if (TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)
                        && TrustManagerService.this.mLockPatternUtils.isSecure(i)) {
                    synchronized (TrustManagerService.this.mDeviceLockedForUser) {
                        TrustManagerService.this.mDeviceLockedForUser.put(i, z);
                    }
                    TrustManagerService.this.notifyKeystoreOfDeviceLockState(i, z);
                    if (z) {
                        try {
                            ActivityManager.getService().notifyLockedProfile(i);
                        } catch (RemoteException unused) {
                        }
                    }
                    Intent intent = new Intent("android.intent.action.DEVICE_LOCKED_CHANGED");
                    intent.addFlags(1073741824);
                    intent.putExtra("android.intent.extra.user_handle", i);
                    TrustManagerService.this.mContext.sendBroadcastAsUser(
                            intent, UserHandle.SYSTEM, "android.permission.TRUST_LISTENER", null);
                    if (TrustManagerService.this.getPersonaManagerInternal() != null) {
                        TrustManagerService.this
                                .getPersonaManagerInternal()
                                .onDeviceLockedChanged(i);
                    } else {
                        Log.e(
                                "TrustManagerService",
                                "onDeviceLockedChanged() - Service is not yet ready...");
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unlockedByBiometricForUser(
                int i, BiometricSourceType biometricSourceType) {
            TrustManagerService trustManagerService;
            enforceReportPermission();
            synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
                TrustManagerService.this.mUsersUnlockedByBiometric.put(i, true);
                trustManagerService = TrustManagerService.this;
                trustManagerService.mIsUnlockedByFP =
                        biometricSourceType == BiometricSourceType.FINGERPRINT;
            }
            obtainMessage(
                            14,
                            i,
                            !trustManagerService
                                            .getContext()
                                            .getPackageManager()
                                            .hasSystemFeature("android.hardware.type.automotive")
                                    ? 1
                                    : 0)
                    .sendToTarget();
            obtainMessage(17, Integer.valueOf(i)).sendToTarget();
        }

        public final void unregisterTrustListener(ITrustListener iTrustListener) {
            TrustManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.TRUST_LISTENER", "register trust listener");
            obtainMessage(2, iTrustListener).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AgentInfo {
        public TrustAgentWrapper agent;
        public ComponentName component;
        public CharSequence label;
        public SettingsAttrs settings;
        public int userId;

        public final boolean equals(Object obj) {
            if (!(obj instanceof AgentInfo)) {
                return false;
            }
            AgentInfo agentInfo = (AgentInfo) obj;
            return this.component.equals(agentInfo.component) && this.userId == agentInfo.userId;
        }

        public final int hashCode() {
            return (this.component.hashCode() * 31) + this.userId;
        }

        public final String toString() {
            return String.format(
                    "AgentInfo{label=%s, component=%s, userId=%s}",
                    this.label, this.component, Integer.valueOf(this.userId));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        public static int getUserId(Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -100);
            if (intExtra > 0) {
                return intExtra;
            }
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    intExtra,
                    "EXTRA_USER_HANDLE missing or invalid, value=",
                    "TrustManagerService");
            return -100;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int userId;
            TrustManagerService trustManagerService;
            String action = intent.getAction();
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                TrustManagerService.this.refreshAgentList(getSendingUserId());
                TrustManagerService trustManagerService2 = TrustManagerService.this;
                boolean z = false;
                for (int i = 0; i < trustManagerService2.mActiveAgents.size(); i++) {
                    TrustAgentWrapper trustAgentWrapper =
                            ((AgentInfo) trustManagerService2.mActiveAgents.valueAt(i)).agent;
                    if (trustAgentWrapper.mTrustAgentService != null) {
                        trustAgentWrapper.updateDevicePolicyFeatures();
                        z = true;
                    }
                }
                if (z) {
                    TrustArchive trustArchive = trustManagerService2.mArchive;
                    trustArchive.getClass();
                    trustArchive.addEvent(new TrustArchive.Event(7, -1, null, null, 0L, 0, false));
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action)
                    || "android.intent.action.USER_STARTED".equals(action)) {
                int userId2 = getUserId(intent);
                if (userId2 > 0) {
                    TrustManagerService.this.maybeEnableFactoryTrustAgents(userId2);
                    return;
                }
                return;
            }
            if (!"android.intent.action.USER_REMOVED".equals(action)
                    || (userId = getUserId(intent)) <= 0) {
                return;
            }
            synchronized (TrustManagerService.this.mDeviceLockedForUser) {
                TrustManagerService.this.mDeviceLockedForUser.delete(userId);
            }
            synchronized (TrustManagerService.this.mTrustUsuallyManagedForUser) {
                TrustManagerService.this.mTrustUsuallyManagedForUser.delete(userId);
            }
            synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
                TrustManagerService.this.mUsersUnlockedByBiometric.delete(userId);
                trustManagerService = TrustManagerService.this;
                trustManagerService.mIsUnlockedByFP = false;
            }
            trustManagerService.refreshAgentList(userId);
            TrustManagerService.this.refreshDeviceLockedForUser(userId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsAttrs {
        public boolean canUnlockProfile;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public final SparseBooleanArray mStartFromSuccessfulUnlock;

        public StrongAuthTracker(Context context, Looper looper) {
            super(context, looper);
            this.mStartFromSuccessfulUnlock = new SparseBooleanArray();
        }

        public final boolean canAgentsRunForUser(int i) {
            return this.mStartFromSuccessfulUnlock.get(i) || isTrustAllowedForUser(i);
        }

        public final void cancelPendingAlarm(TrustTimeoutAlarmListener trustTimeoutAlarmListener) {
            if (trustTimeoutAlarmListener == null || !trustTimeoutAlarmListener.mIsQueued) {
                return;
            }
            trustTimeoutAlarmListener.mIsQueued = false;
            TrustManagerService.this.mAlarmManager.cancel(trustTimeoutAlarmListener);
        }

        public final void onStrongAuthRequiredChanged(int i) {
            this.mStartFromSuccessfulUnlock.delete(i);
            if (TrustManagerService.DEBUG) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "onStrongAuthRequiredChanged(", ") -> trustAllowed=");
                m.append(isTrustAllowedForUser(i));
                m.append(" agentsCanRun=");
                m.append(canAgentsRunForUser(i));
                Log.i("TrustManagerService", m.toString());
            }
            if (!isTrustAllowedForUser(i)) {
                cancelPendingAlarm(
                        (TrustTimeoutAlarmListener)
                                TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(
                                        Integer.valueOf(i)));
                cancelPendingAlarm(
                        (TrustTimeoutAlarmListener)
                                TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(
                                        i));
                cancelPendingAlarm(
                        (TrustTimeoutAlarmListener)
                                TrustManagerService.this.mIdleTrustableTimeoutAlarmListenerForUser
                                        .get(i));
            }
            TrustManagerService.this.refreshAgentList(i);
            TrustManagerService.this.updateTrust(i);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class TimeoutType {
        public static final /* synthetic */ TimeoutType[] $VALUES;
        public static final TimeoutType TRUSTABLE;
        public static final TimeoutType TRUSTED;

        static {
            TimeoutType timeoutType = new TimeoutType("TRUSTED", 0);
            TRUSTED = timeoutType;
            TimeoutType timeoutType2 = new TimeoutType("TRUSTABLE", 1);
            TRUSTABLE = timeoutType2;
            $VALUES = new TimeoutType[] {timeoutType, timeoutType2};
        }

        public static TimeoutType valueOf(String str) {
            return (TimeoutType) Enum.valueOf(TimeoutType.class, str);
        }

        public static TimeoutType[] values() {
            return (TimeoutType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class TrustState {
        public static final /* synthetic */ TrustState[] $VALUES;
        public static final TrustState TRUSTABLE;
        public static final TrustState TRUSTED;
        public static final TrustState UNTRUSTED;

        static {
            TrustState trustState = new TrustState("UNTRUSTED", 0);
            UNTRUSTED = trustState;
            TrustState trustState2 = new TrustState("TRUSTABLE", 1);
            TRUSTABLE = trustState2;
            TrustState trustState3 = new TrustState("TRUSTED", 2);
            TRUSTED = trustState3;
            $VALUES = new TrustState[] {trustState, trustState2, trustState3};
        }

        public static TrustState valueOf(String str) {
            return (TrustState) Enum.valueOf(TrustState.class, str);
        }

        public static TrustState[] values() {
            return (TrustState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class TrustTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
        public boolean mIsQueued = false;
        public final int mUserId;

        public TrustTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        public abstract void handleAlarm();

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            this.mIsQueued = false;
            handleAlarm();
            if (TrustManagerService.this.mStrongAuthTracker.isTrustAllowedForUser(this.mUserId)) {
                if (TrustManagerService.DEBUG) {
                    Slogf.d("TrustManagerService", "Revoking all trust because of trust timeout");
                }
                TrustManagerService trustManagerService = TrustManagerService.this;
                LockPatternUtils lockPatternUtils = trustManagerService.mLockPatternUtils;
                StrongAuthTracker strongAuthTracker = trustManagerService.mStrongAuthTracker;
                lockPatternUtils.requireStrongAuth(256, this.mUserId);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrustableTimeoutAlarmListener extends TrustTimeoutAlarmListener {
        public TrustableTimeoutAlarmListener(int i) {
            super(i);
        }

        @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
        public final void handleAlarm() {
            TrustManagerService.this.cancelBothTrustableAlarms(this.mUserId);
            TrustedTimeoutAlarmListener trustedTimeoutAlarmListener =
                    (TrustedTimeoutAlarmListener)
                            TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(
                                    Integer.valueOf(this.mUserId));
            if (trustedTimeoutAlarmListener == null || !trustedTimeoutAlarmListener.mIsQueued) {
                return;
            }
            synchronized (TrustManagerService.this.mAlarmLock) {
                Iterator it = TrustManagerService.this.mActiveAgents.iterator();
                while (it.hasNext()) {
                    ((AgentInfo) it.next()).agent.mTrustable = false;
                }
                TrustManagerService.this.updateTrust(this.mUserId);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrustedTimeoutAlarmListener extends TrustTimeoutAlarmListener {
        public TrustedTimeoutAlarmListener(int i) {
            super(i);
        }

        public final void disableNonrenewableTrustWhileRenewableTrustIsPresent() {
            synchronized (TrustManagerService.this.mUserTrustState) {
                try {
                    if (TrustManagerService.this.mUserTrustState.get(this.mUserId)
                            == TrustState.TRUSTED) {
                        TrustManagerService.this.mUserTrustState.put(
                                this.mUserId, TrustState.TRUSTABLE);
                        TrustManagerService.this.updateTrust(this.mUserId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
        public final void handleAlarm() {
            TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
                    (TrustableTimeoutAlarmListener)
                            TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(
                                    this.mUserId);
            if (trustableTimeoutAlarmListener == null || !trustableTimeoutAlarmListener.mIsQueued) {
                return;
            }
            synchronized (TrustManagerService.this.mAlarmLock) {
                disableNonrenewableTrustWhileRenewableTrustIsPresent();
            }
        }
    }

    static {
        DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("TrustManagerService", 2);
        TRUST_AGENT_INTENT = new Intent("android.service.trust.TrustAgentService");
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.trust.TrustManagerService$3] */
    public TrustManagerService(Context context) {
        super(context);
        this.mActiveAgents = new ArraySet();
        this.mLastActiveUnlockRunningState = new SparseBooleanArray();
        this.mTrustListeners = new ArrayList();
        this.mReceiver = new Receiver();
        TrustArchive trustArchive = new TrustArchive();
        trustArchive.mEvents = new ArrayDeque();
        this.mArchive = trustArchive;
        this.mUserTrustState = new SparseArray();
        this.mDeviceLockedForUser = new SparseBooleanArray();
        this.mTrustUsuallyManagedForUser = new SparseBooleanArray();
        this.mUsersUnlockedByBiometric = new SparseBooleanArray();
        this.mIsUnlockedByFP = false;
        this.mTrustTimeoutAlarmListenerForUser = new ArrayMap();
        this.mTrustableTimeoutAlarmListenerForUser = new SparseArray();
        this.mIdleTrustableTimeoutAlarmListenerForUser = new SparseArray();
        this.mAlarmLock = new Object();
        this.mTrustAgentsCanRun = false;
        this.mCurrentUser = 0;
        this.mIsInSignificantPlace = false;
        this.mService = new AnonymousClass2();
        this.mPackageMonitor =
                new PackageMonitor() { // from class: com.android.server.trust.TrustManagerService.4
                    public final void onPackageAdded(String str, int i) {
                        TrustManagerService.this.checkNewAgentsForUser(UserHandle.getUserId(i));
                    }

                    public final boolean onPackageChanged(String str, int i, String[] strArr) {
                        TrustManagerService.this.checkNewAgentsForUser(UserHandle.getUserId(i));
                        return true;
                    }

                    public final void onPackageDisappeared(String str, int i) {
                        TrustManagerService trustManagerService = TrustManagerService.this;
                        boolean z = false;
                        for (int size = trustManagerService.mActiveAgents.size() - 1;
                                size >= 0;
                                size--) {
                            AgentInfo agentInfo =
                                    (AgentInfo) trustManagerService.mActiveAgents.valueAt(size);
                            if (str.equals(agentInfo.component.getPackageName())) {
                                Log.i(
                                        "TrustManagerService",
                                        "Resetting agent "
                                                + agentInfo.component.flattenToShortString());
                                if (agentInfo.agent.isManagingTrust()) {
                                    z = true;
                                }
                                agentInfo.agent.destroy();
                                trustManagerService.mActiveAgents.removeAt(size);
                            }
                        }
                        if (z) {
                            trustManagerService.updateTrustAll();
                        }
                    }

                    public final void onSomePackagesChanged() {
                        TrustManagerService.this.refreshAgentList(-1);
                    }
                };
        this.mContext = context;
        this.mHandler =
                new Handler(Looper.myLooper()) { // from class:
                    // com.android.server.trust.TrustManagerService.3
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        SparseBooleanArray clone;
                        boolean z = TrustManagerService.DEBUG;
                        if (z) {
                            Slogf.d(
                                    "TrustManagerService",
                                    "handler: %s",
                                    Integer.valueOf(message.what));
                        }
                        int i = 0;
                        switch (message.what) {
                            case 1:
                                TrustManagerService trustManagerService = TrustManagerService.this;
                                ITrustListener iTrustListener = (ITrustListener) message.obj;
                                for (int i2 = 0;
                                        i2 < trustManagerService.mTrustListeners.size();
                                        i2++) {
                                    if (((ITrustListener)
                                                            trustManagerService.mTrustListeners.get(
                                                                    i2))
                                                    .asBinder()
                                            == iTrustListener.asBinder()) {
                                        return;
                                    }
                                }
                                trustManagerService.mTrustListeners.add(iTrustListener);
                                int size = trustManagerService.mLastActiveUnlockRunningState.size();
                                while (i < size) {
                                    int keyAt =
                                            trustManagerService.mLastActiveUnlockRunningState.keyAt(
                                                    i);
                                    try {
                                        iTrustListener.onIsActiveUnlockRunningChanged(
                                                trustManagerService.aggregateIsActiveUnlockRunning(
                                                        keyAt),
                                                keyAt);
                                    } catch (DeadObjectException unused) {
                                        Slogf.d(
                                                "TrustManagerService",
                                                "TrustListener dead while trying to notify Active"
                                                    + " Unlock running state");
                                    } catch (RemoteException e) {
                                        Slogf.e(
                                                "TrustManagerService",
                                                "Exception while notifying TrustListener.",
                                                e);
                                    }
                                    i++;
                                }
                                trustManagerService.updateTrustAll();
                                return;
                            case 2:
                                TrustManagerService trustManagerService2 = TrustManagerService.this;
                                ITrustListener iTrustListener2 = (ITrustListener) message.obj;
                                while (i < trustManagerService2.mTrustListeners.size()) {
                                    if (((ITrustListener)
                                                            trustManagerService2.mTrustListeners
                                                                    .get(i))
                                                    .asBinder()
                                            == iTrustListener2.asBinder()) {
                                        trustManagerService2.mTrustListeners.remove(i);
                                        return;
                                    }
                                    i++;
                                }
                                return;
                            case 3:
                                TrustManagerService trustManagerService3 = TrustManagerService.this;
                                boolean z2 = message.arg1 != 0;
                                int i3 = message.arg2;
                                if (z) {
                                    trustManagerService3.getClass();
                                    Slogf.d(
                                            "TrustManagerService",
                                            "dispatchUnlockAttempt(successful=%s, userId=%s)",
                                            Boolean.valueOf(z2),
                                            Integer.valueOf(i3));
                                }
                                if (z2) {
                                    StrongAuthTracker strongAuthTracker =
                                            trustManagerService3.mStrongAuthTracker;
                                    if (i3 < 0) {
                                        strongAuthTracker.getClass();
                                        throw new IllegalArgumentException(
                                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                        i3, "userId must be a valid user: "));
                                    }
                                    boolean canAgentsRunForUser =
                                            strongAuthTracker.canAgentsRunForUser(i3);
                                    strongAuthTracker.mStartFromSuccessfulUnlock.put(i3, true);
                                    if (z) {
                                        StringBuilder m =
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        i3,
                                                        "allowTrustFromUnlock(",
                                                        ") -> trustAllowed=");
                                        m.append(strongAuthTracker.isTrustAllowedForUser(i3));
                                        m.append(" agentsCanRun=");
                                        m.append(strongAuthTracker.canAgentsRunForUser(i3));
                                        Log.i("TrustManagerService", m.toString());
                                    }
                                    if (strongAuthTracker.canAgentsRunForUser(i3)
                                            != canAgentsRunForUser) {
                                        TrustManagerService.this.refreshAgentList(i3);
                                    }
                                    trustManagerService3.updateTrust(i3, 0, true, null);
                                    trustManagerService3
                                            .mHandler
                                            .obtainMessage(17, Integer.valueOf(i3))
                                            .sendToTarget();
                                }
                                while (i < trustManagerService3.mActiveAgents.size()) {
                                    AgentInfo agentInfo =
                                            (AgentInfo)
                                                    trustManagerService3.mActiveAgents.valueAt(i);
                                    if (agentInfo.userId == i3) {
                                        TrustAgentWrapper trustAgentWrapper = agentInfo.agent;
                                        trustAgentWrapper.getClass();
                                        try {
                                            ITrustAgentService iTrustAgentService =
                                                    trustAgentWrapper.mTrustAgentService;
                                            if (iTrustAgentService != null) {
                                                iTrustAgentService.onUnlockAttempt(z2);
                                            } else {
                                                trustAgentWrapper.mPendingSuccessfulUnlock = z2;
                                            }
                                        } catch (RemoteException e2) {
                                            TrustAgentWrapper.onError(e2);
                                        }
                                    }
                                    i++;
                                }
                                return;
                            case 4:
                                TrustManagerService.this.refreshAgentList(-1);
                                TrustManagerService.this.refreshDeviceLockedForUser(-1);
                                TrustManagerService trustManagerService4 = TrustManagerService.this;
                                int i4 = message.arg1;
                                if (z) {
                                    trustManagerService4.getClass();
                                    Log.i(
                                            "TrustManagerService",
                                            "onEnabledTrustAgentsChanged(" + i4 + ")");
                                }
                                while (i < trustManagerService4.mTrustListeners.size()) {
                                    try {
                                        ((ITrustListener)
                                                        trustManagerService4.mTrustListeners.get(i))
                                                .onEnabledTrustAgentsChanged(i4);
                                    } catch (DeadObjectException unused2) {
                                        Slogf.d(
                                                "TrustManagerService",
                                                "Removing dead TrustListener.");
                                        trustManagerService4.mTrustListeners.remove(i);
                                        i--;
                                    } catch (RemoteException e3) {
                                        Slogf.e(
                                                "TrustManagerService",
                                                "Exception while notifying TrustListener.",
                                                e3);
                                    }
                                    i++;
                                }
                                return;
                            case 5:
                            default:
                                return;
                            case 6:
                                TrustManagerService trustManagerService5 = TrustManagerService.this;
                                for (int i5 = 0;
                                        i5 < trustManagerService5.mActiveAgents.size();
                                        i5++) {
                                    AgentInfo agentInfo2 =
                                            (AgentInfo)
                                                    trustManagerService5.mActiveAgents.valueAt(i5);
                                    if (agentInfo2.userId == trustManagerService5.mCurrentUser) {
                                        TrustAgentWrapper trustAgentWrapper2 = agentInfo2.agent;
                                        if (trustAgentWrapper2.mWaitingForTrustableDowngrade) {
                                            trustAgentWrapper2.mWaitingForTrustableDowngrade =
                                                    false;
                                            trustAgentWrapper2.mTrusted = false;
                                            trustAgentWrapper2.mTrustable = true;
                                            trustAgentWrapper2.mTrustManagerService.updateTrust(
                                                    trustAgentWrapper2.mUserId);
                                        }
                                    }
                                }
                                TrustManagerService trustManagerService6 = TrustManagerService.this;
                                trustManagerService6.refreshDeviceLockedForUser(
                                        trustManagerService6.mCurrentUser);
                                return;
                            case 7:
                            case 8:
                            case 11:
                                TrustManagerService.this.refreshAgentList(message.arg1);
                                if (message.what == 11) {
                                    TrustManagerService.this.updateDualDARStateAndUnlockIfNeeded(
                                            message.arg1);
                                    return;
                                }
                                return;
                            case 9:
                                TrustManagerService trustManagerService7 = TrustManagerService.this;
                                trustManagerService7.mCurrentUser = message.arg1;
                                trustManagerService7.refreshDeviceLockedForUser(-1);
                                return;
                            case 10:
                                synchronized (
                                        TrustManagerService.this.mTrustUsuallyManagedForUser) {
                                    clone =
                                            TrustManagerService.this.mTrustUsuallyManagedForUser
                                                    .clone();
                                }
                                while (i < clone.size()) {
                                    int keyAt2 = clone.keyAt(i);
                                    boolean valueAt = clone.valueAt(i);
                                    if (valueAt
                                            != TrustManagerService.this.mLockPatternUtils
                                                    .isTrustUsuallyManaged(keyAt2)) {
                                        TrustManagerService.this.mLockPatternUtils
                                                .setTrustUsuallyManaged(valueAt, keyAt2);
                                    }
                                    i++;
                                }
                                return;
                            case 12:
                                TrustManagerService.this.setDeviceLockedForUser(message.arg1, true);
                                return;
                            case 13:
                                TrustManagerService trustManagerService8 = TrustManagerService.this;
                                int i6 = message.arg1;
                                int i7 = message.arg2;
                                while (i < trustManagerService8.mActiveAgents.size()) {
                                    AgentInfo agentInfo3 =
                                            (AgentInfo)
                                                    trustManagerService8.mActiveAgents.valueAt(i);
                                    if (agentInfo3.userId == i7) {
                                        TrustAgentWrapper trustAgentWrapper3 = agentInfo3.agent;
                                        trustAgentWrapper3.getClass();
                                        try {
                                            ITrustAgentService iTrustAgentService2 =
                                                    trustAgentWrapper3.mTrustAgentService;
                                            if (iTrustAgentService2 != null) {
                                                iTrustAgentService2.onUnlockLockout(i6);
                                            }
                                        } catch (RemoteException e4) {
                                            TrustAgentWrapper.onError(e4);
                                        }
                                    }
                                    i++;
                                }
                                return;
                            case 14:
                                if (message.arg2 == 1) {
                                    TrustManagerService.this.updateTrust(
                                            message.arg1, 0, true, null);
                                }
                                TrustManagerService.this.refreshDeviceLockedForUser(
                                        message.arg1, message.getData().getInt("except", -10000));
                                return;
                            case 15:
                                boolean z3 = message.arg1 == 1;
                                TimeoutType timeoutType =
                                        message.arg2 == 1
                                                ? TimeoutType.TRUSTABLE
                                                : TimeoutType.TRUSTED;
                                TrustManagerService trustManagerService9 = TrustManagerService.this;
                                int i8 = trustManagerService9.mCurrentUser;
                                if (z) {
                                    Slogf.d(
                                            "TrustManagerService",
                                            "handleScheduleTrustTimeout(shouldOverride=%s,"
                                                + " timeoutType=%s)",
                                            Boolean.valueOf(z3),
                                            timeoutType);
                                }
                                if (timeoutType == TimeoutType.TRUSTABLE) {
                                    trustManagerService9.handleScheduleTrustableTimeouts(
                                            i8, z3, false);
                                    return;
                                }
                                if (z) {
                                    Slogf.d(
                                            "TrustManagerService",
                                            "handleScheduleTrustedTimeout(userId=%s,"
                                                + " shouldOverride=%s)",
                                            Integer.valueOf(i8),
                                            Boolean.valueOf(z3));
                                }
                                long elapsedRealtime =
                                        SystemClock.elapsedRealtime()
                                                + BackupManagerConstants
                                                        .DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
                                TrustedTimeoutAlarmListener trustedTimeoutAlarmListener =
                                        (TrustedTimeoutAlarmListener)
                                                trustManagerService9
                                                        .mTrustTimeoutAlarmListenerForUser.get(
                                                        Integer.valueOf(i8));
                                if (trustedTimeoutAlarmListener == null) {
                                    trustedTimeoutAlarmListener =
                                            trustManagerService9
                                            .new TrustedTimeoutAlarmListener(i8);
                                    trustManagerService9.mTrustTimeoutAlarmListenerForUser.put(
                                            Integer.valueOf(i8), trustedTimeoutAlarmListener);
                                } else {
                                    if (!z3 && trustedTimeoutAlarmListener.mIsQueued) {
                                        if (z) {
                                            Slogf.d(
                                                    "TrustManagerService",
                                                    "Found existing trust timeout alarm."
                                                        + " Skipping.");
                                            return;
                                        }
                                        return;
                                    }
                                    trustManagerService9.mAlarmManager.cancel(
                                            trustedTimeoutAlarmListener);
                                }
                                if (z) {
                                    Slogf.d(
                                            "TrustManagerService",
                                            "\tSetting up trust timeout alarm triggering at"
                                                + " elapsedRealTime=%s",
                                            Long.valueOf(elapsedRealtime));
                                }
                                trustedTimeoutAlarmListener.mIsQueued = true;
                                trustManagerService9.mAlarmManager.setExact(
                                        2,
                                        elapsedRealtime,
                                        "TrustManagerService.trustTimeoutForUser",
                                        trustedTimeoutAlarmListener,
                                        trustManagerService9.mHandler);
                                return;
                            case 16:
                                TrustManagerService trustManagerService10 =
                                        TrustManagerService.this;
                                int i9 = message.arg1;
                                boolean z4 = message.arg2 != 0;
                                if (z) {
                                    trustManagerService10.getClass();
                                    Slogf.d(
                                            "TrustManagerService",
                                            "dispatchUserRequestedUnlock(user=%s,"
                                                + " dismissKeyguard=%s)",
                                            Integer.valueOf(i9),
                                            Boolean.valueOf(z4));
                                }
                                while (i < trustManagerService10.mActiveAgents.size()) {
                                    AgentInfo agentInfo4 =
                                            (AgentInfo)
                                                    trustManagerService10.mActiveAgents.valueAt(i);
                                    if (agentInfo4.userId == i9) {
                                        TrustAgentWrapper trustAgentWrapper4 = agentInfo4.agent;
                                        trustAgentWrapper4.getClass();
                                        try {
                                            ITrustAgentService iTrustAgentService3 =
                                                    trustAgentWrapper4.mTrustAgentService;
                                            if (iTrustAgentService3 != null) {
                                                iTrustAgentService3.onUserRequestedUnlock(z4);
                                            }
                                        } catch (RemoteException e5) {
                                            TrustAgentWrapper.onError(e5);
                                        }
                                    }
                                    i++;
                                }
                                return;
                            case 17:
                                if (z) {
                                    Slogf.d(
                                            "TrustManagerService",
                                            "REFRESH_TRUSTABLE_TIMERS_AFTER_AUTH userId=%s",
                                            Integer.valueOf(message.arg1));
                                }
                                TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
                                        (TrustableTimeoutAlarmListener)
                                                TrustManagerService.this
                                                        .mTrustableTimeoutAlarmListenerForUser.get(
                                                        message.arg1);
                                if (z) {
                                    if (trustableTimeoutAlarmListener != null) {
                                        Slogf.d(
                                                "TrustManagerService",
                                                "REFRESH_TRUSTABLE_TIMERS_AFTER_AUTH trustable"
                                                    + " alarm isQueued=%s",
                                                Boolean.valueOf(
                                                        trustableTimeoutAlarmListener.mIsQueued));
                                    } else {
                                        Slogf.d(
                                                "TrustManagerService",
                                                "REFRESH_TRUSTABLE_TIMERS_AFTER_AUTH no trustable"
                                                    + " alarm");
                                    }
                                }
                                if (trustableTimeoutAlarmListener == null
                                        || !trustableTimeoutAlarmListener.mIsQueued) {
                                    return;
                                }
                                TrustManagerService trustManagerService11 =
                                        TrustManagerService.this;
                                int i10 = message.arg1;
                                if (z) {
                                    trustManagerService11.getClass();
                                    Slogf.d(
                                            "TrustManagerService",
                                            "refreshTrustableTimers(userId=%s)",
                                            Integer.valueOf(i10));
                                }
                                trustManagerService11.handleScheduleTrustableTimeouts(
                                        i10, true, true);
                                return;
                            case 18:
                                TrustManagerService trustManagerService12 =
                                        TrustManagerService.this;
                                int i11 = message.arg1;
                                if (z) {
                                    trustManagerService12.getClass();
                                    Slogf.d(
                                            "TrustManagerService",
                                            "dispatchUserMayRequestUnlock(user=%s)",
                                            Integer.valueOf(i11));
                                }
                                while (i < trustManagerService12.mActiveAgents.size()) {
                                    AgentInfo agentInfo5 =
                                            (AgentInfo)
                                                    trustManagerService12.mActiveAgents.valueAt(i);
                                    if (agentInfo5.userId == i11) {
                                        TrustAgentWrapper trustAgentWrapper5 = agentInfo5.agent;
                                        trustAgentWrapper5.getClass();
                                        try {
                                            ITrustAgentService iTrustAgentService4 =
                                                    trustAgentWrapper5.mTrustAgentService;
                                            if (iTrustAgentService4 != null) {
                                                iTrustAgentService4.onUserMayRequestUnlock();
                                            }
                                        } catch (RemoteException e6) {
                                            TrustAgentWrapper.onError(e6);
                                        }
                                    }
                                    i++;
                                }
                                return;
                        }
                    }
                };
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mKeyStoreAuthorization = KeyStoreAuthorization.getInstance();
        this.mStrongAuthTracker = new StrongAuthTracker(context, Looper.myLooper());
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
    }

    public static ComponentName getComponentName(ResolveInfo resolveInfo) {
        if (resolveInfo == null || resolveInfo.serviceInfo == null) {
            return null;
        }
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name);
    }

    public static List resolveAllowedTrustAgents(PackageManager packageManager, int i) {
        List<ResolveInfo> queryIntentServicesAsUser =
                packageManager.queryIntentServicesAsUser(TRUST_AGENT_INTENT, 786560, i);
        ArrayList arrayList = new ArrayList(queryIntentServicesAsUser.size());
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null && serviceInfo.applicationInfo != null) {
                if (packageManager.checkPermission(
                                "android.permission.PROVIDE_TRUST_AGENT", serviceInfo.packageName)
                        != 0) {
                    Log.w(
                            "TrustManagerService",
                            "Skipping agent "
                                    + getComponentName(resolveInfo)
                                    + " because package does not have permission"
                                    + " android.permission.PROVIDE_TRUST_AGENT.");
                } else {
                    arrayList.add(resolveInfo);
                }
            }
        }
        return arrayList;
    }

    public final boolean aggregateIsActiveUnlockRunning(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return false;
        }
        synchronized (this.mUserTrustState) {
            try {
                TrustState trustState = (TrustState) this.mUserTrustState.get(i);
                if (trustState != TrustState.TRUSTED && trustState != TrustState.TRUSTABLE) {
                    return false;
                }
                for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
                    AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
                    if (agentInfo.userId == i) {
                        TrustAgentWrapper trustAgentWrapper = agentInfo.agent;
                        if (trustAgentWrapper.mWaitingForTrustableDowngrade) {
                            return true;
                        }
                        if (trustAgentWrapper.mTrustable
                                && trustAgentWrapper.mManagingTrust
                                && !trustAgentWrapper.mTrustDisabledByDpm) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean aggregateIsTrustManaged(int i) {
        boolean isTrustAllowedForUser = this.mStrongAuthTracker.isTrustAllowedForUser(i);
        boolean z = DEBUG;
        if (!isTrustAllowedForUser) {
            if (z) {
                Slogf.d(
                        "TrustManagerService",
                        "trust not managed due to trust not being allowed for userId=%s",
                        Integer.valueOf(i));
            }
            return false;
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
            if (agentInfo.userId == i && agentInfo.agent.isManagingTrust()) {
                if (!z) {
                    return true;
                }
                Slogf.d("TrustManagerService", "trust managed for userId=%s", Integer.valueOf(i));
                return true;
            }
        }
        if (z) {
            Slogf.d("TrustManagerService", "trust not managed for userId=%s", Integer.valueOf(i));
        }
        return false;
    }

    public final boolean aggregateIsTrusted(int i) {
        boolean z = DEBUG;
        if (z) {
            Slogf.d("TrustManagerService", "aggregateIsTrusted(userId=%s)", Integer.valueOf(i));
        }
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            if (z) {
                Slogf.d(
                        "TrustManagerService",
                        "not trusted because trust not allowed for userId=%s",
                        Integer.valueOf(i));
            }
            return false;
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
            if (agentInfo.userId == i && agentInfo.agent.isTrusted()) {
                if (!z) {
                    return true;
                }
                Slogf.d("TrustManagerService", "trusted by %s", agentInfo);
                return true;
            }
        }
        return false;
    }

    public final void cancelBothTrustableAlarms(int i) {
        TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
                (TrustableTimeoutAlarmListener)
                        this.mIdleTrustableTimeoutAlarmListenerForUser.get(i);
        TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 =
                (TrustableTimeoutAlarmListener) this.mTrustableTimeoutAlarmListenerForUser.get(i);
        if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.mIsQueued) {
            trustableTimeoutAlarmListener.mIsQueued = false;
            this.mAlarmManager.cancel(trustableTimeoutAlarmListener);
        }
        if (trustableTimeoutAlarmListener2 == null || !trustableTimeoutAlarmListener2.mIsQueued) {
            return;
        }
        trustableTimeoutAlarmListener2.mIsQueued = false;
        this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
    }

    public final void checkNewAgentsForUser(int i) {
        if (Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "known_trust_agents_initialized", 0, i)
                == 0) {
            ArrayList arrayList =
                    (ArrayList) resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
            ArraySet arraySet = new ArraySet(arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arraySet.add(getComponentName((ResolveInfo) it.next()));
            }
            this.mLockPatternUtils.setKnownTrustAgents(arraySet, i);
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "known_trust_agents_initialized", 1, i);
            return;
        }
        List knownTrustAgents = this.mLockPatternUtils.getKnownTrustAgents(i);
        ArrayList arrayList2 =
                (ArrayList) resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
        ArraySet arraySet2 = new ArraySet(arrayList2.size());
        ArraySet arraySet3 = new ArraySet(arrayList2.size());
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it2.next();
            ComponentName componentName = getComponentName(resolveInfo);
            if (!knownTrustAgents.contains(componentName)) {
                arraySet2.add(componentName);
                if ((resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0) {
                    arraySet3.add(componentName);
                }
            }
        }
        if (arraySet2.isEmpty()) {
            return;
        }
        ArraySet arraySet4 = new ArraySet(knownTrustAgents);
        arraySet4.addAll(arraySet2);
        this.mLockPatternUtils.setKnownTrustAgents(arraySet4, i);
        String string =
                this.mContext.getResources().getString(R.string.device_policy_manager_service);
        if ((TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string)) == null
                && !arraySet3.isEmpty()) {
            ArraySet arraySet5 = new ArraySet((Collection) arraySet3);
            arraySet5.addAll(this.mLockPatternUtils.getEnabledTrustAgents(i));
            this.mLockPatternUtils.setEnabledTrustAgents(arraySet5, i);
        }
    }

    public final PersonaManagerInternal getPersonaManagerInternal() {
        if (this.mPersonaManagerInternal == null) {
            this.mPersonaManagerInternal =
                    (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
        }
        return this.mPersonaManagerInternal;
    }

    public final TrustState getUserTrustStateInner(int i) {
        TrustState trustState;
        synchronized (this.mUserTrustState) {
            trustState = (TrustState) this.mUserTrustState.get(i, TrustState.UNTRUSTED);
        }
        return trustState;
    }

    public final void handleDualDARDeviceLockedChangedForUser(int i, boolean z) {
        int dualDARUser;
        if (PersonaServiceHelper.isDualDAREnabled()) {
            DDLog.d(
                    "TrustManagerService",
                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                            i,
                            "handleDualDARDeviceLockedChangedForUser() - userId : ",
                            ", locked : ",
                            z),
                    new Object[0]);
            if (i != 0 || (dualDARUser = PersonaServiceHelper.getDualDARUser()) == -1) {
                return;
            }
            if (z) {
                StateMachine.processEvent(dualDARUser, Event.DEVICE_LOCKED);
            } else {
                updateDualDARStateAndUnlockIfNeeded(i);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleScheduleTrustableTimeouts(int r20, boolean r21, boolean r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            java.lang.String r2 = "TrustManagerService"
            boolean r3 = com.android.server.trust.TrustManagerService.DEBUG
            if (r3 == 0) goto L1c
            java.lang.Integer r4 = java.lang.Integer.valueOf(r20)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r21)
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r5}
            java.lang.String r5 = "setUpIdleTimeout(userId=%s, overrideIdleTimeout=%s)"
            com.android.server.utils.Slogf.d(r2, r5, r4)
        L1c:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r6 = 28800000(0x1b77400, double:1.42290906E-316)
            long r10 = r4 + r6
            android.util.SparseArray r4 = r0.mIdleTrustableTimeoutAlarmListenerForUser
            java.lang.Object r4 = r4.get(r1)
            com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener r4 = (com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener) r4
            android.content.Context r5 = r0.mContext
            java.lang.String r6 = "android.permission.SCHEDULE_EXACT_ALARM"
            r7 = 0
            r5.enforceCallingOrSelfPermission(r6, r7)
            com.android.server.trust.TrustManagerService$3 r5 = r0.mHandler
            r15 = 1
            if (r4 == 0) goto L4f
            if (r21 != 0) goto L48
            boolean r8 = r4.mIsQueued
            if (r8 == 0) goto L48
            if (r3 == 0) goto L74
            java.lang.String r4 = "Found existing trustable timeout alarm. Skipping."
            com.android.server.utils.Slogf.d(r2, r4)
            goto L74
        L48:
            android.app.AlarmManager r8 = r0.mAlarmManager
            r8.cancel(r4)
        L4d:
            r13 = r4
            goto L5a
        L4f:
            com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener r4 = new com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener
            r4.<init>(r1)
            android.util.SparseArray r8 = r0.mIdleTrustableTimeoutAlarmListenerForUser
            r8.put(r1, r4)
            goto L4d
        L5a:
            if (r3 == 0) goto L69
            java.lang.Long r4 = java.lang.Long.valueOf(r10)
            java.lang.Object[] r4 = new java.lang.Object[]{r4}
            java.lang.String r8 = "\tSetting up trustable idle timeout alarm triggering at elapsedRealTime=%s"
            com.android.server.utils.Slogf.d(r2, r8, r4)
        L69:
            r13.mIsQueued = r15
            android.app.AlarmManager r8 = r0.mAlarmManager
            r9 = 2
            java.lang.String r12 = "TrustManagerService.trustTimeoutForUser"
            r14 = r5
            r8.setExact(r9, r10, r12, r13, r14)
        L74:
            if (r3 == 0) goto L88
            java.lang.Integer r4 = java.lang.Integer.valueOf(r20)
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r22)
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r8}
            java.lang.String r8 = "setUpHardTimeout(userId=%s, overrideHardTimeout=%s)"
            com.android.server.utils.Slogf.i(r2, r8, r4)
        L88:
            android.content.Context r4 = r0.mContext
            r4.enforceCallingOrSelfPermission(r6, r7)
            android.util.SparseArray r4 = r0.mTrustableTimeoutAlarmListenerForUser
            java.lang.Object r4 = r4.get(r1)
            com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener r4 = (com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener) r4
            if (r4 == 0) goto L9d
            boolean r6 = r4.mIsQueued
            if (r6 == 0) goto L9d
            if (r22 == 0) goto Ld7
        L9d:
            long r6 = android.os.SystemClock.elapsedRealtime()
            r8 = 86400000(0x5265c00, double:4.2687272E-316)
            long r6 = r6 + r8
            if (r4 != 0) goto Lb2
            com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener r4 = new com.android.server.trust.TrustManagerService$TrustableTimeoutAlarmListener
            r4.<init>(r1)
            android.util.SparseArray r8 = r0.mTrustableTimeoutAlarmListenerForUser
            r8.put(r1, r4)
            goto Lb9
        Lb2:
            if (r22 == 0) goto Lb9
            android.app.AlarmManager r1 = r0.mAlarmManager
            r1.cancel(r4)
        Lb9:
            if (r3 == 0) goto Lc8
            java.lang.Long r1 = java.lang.Long.valueOf(r6)
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            java.lang.String r3 = "\tSetting up trustable hard timeout alarm triggering at elapsedRealTime=%s"
            com.android.server.utils.Slogf.d(r2, r3, r1)
        Lc8:
            r4.mIsQueued = r15
            android.app.AlarmManager r12 = r0.mAlarmManager
            r13 = 2
            java.lang.String r16 = "TrustManagerService.trustTimeoutForUser"
            r14 = r6
            r17 = r4
            r18 = r5
            r12.setExact(r13, r14, r16, r17, r18)
        Ld7:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.trust.TrustManagerService.handleScheduleTrustableTimeouts(int,"
                    + " boolean, boolean):void");
    }

    public final boolean isDeviceLockedInner(int i) {
        boolean z;
        synchronized (this.mDeviceLockedForUser) {
            z = this.mDeviceLockedForUser.get(i, true);
        }
        return z;
    }

    public final boolean isTrustUsuallyManagedInternal(int i) {
        synchronized (this.mTrustUsuallyManagedForUser) {
            try {
                int indexOfKey = this.mTrustUsuallyManagedForUser.indexOfKey(i);
                if (indexOfKey >= 0) {
                    return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey);
                }
                boolean isTrustUsuallyManaged = this.mLockPatternUtils.isTrustUsuallyManaged(i);
                synchronized (this.mTrustUsuallyManagedForUser) {
                    try {
                        int indexOfKey2 = this.mTrustUsuallyManagedForUser.indexOfKey(i);
                        if (indexOfKey2 >= 0) {
                            return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey2);
                        }
                        this.mTrustUsuallyManagedForUser.put(i, isTrustUsuallyManaged);
                        return isTrustUsuallyManaged;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final void maybeEnableFactoryTrustAgents(int i) {
        if (Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "trust_agents_initialized", 0, i)
                != 0) {
            return;
        }
        List resolveAllowedTrustAgents =
                resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
        String string =
                this.mContext.getResources().getString(R.string.device_policy_manager_service);
        ComponentName unflattenFromString =
                TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
        boolean z = unflattenFromString != null;
        ArraySet arraySet = new ArraySet();
        if (z) {
            arraySet.add(unflattenFromString);
            Log.i(
                    "TrustManagerService",
                    "Enabling " + unflattenFromString + " because it is a default agent.");
        } else {
            Iterator it = ((ArrayList) resolveAllowedTrustAgents).iterator();
            while (it.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                ComponentName componentName = getComponentName(resolveInfo);
                if ((resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0) {
                    arraySet.add(componentName);
                } else {
                    Log.i(
                            "TrustManagerService",
                            "Leaving agent "
                                    + componentName
                                    + " disabled because package is not a system package.");
                }
            }
        }
        if (!arraySet.isEmpty()) {
            ArraySet arraySet2 = new ArraySet((Collection) arraySet);
            arraySet2.addAll(this.mLockPatternUtils.getEnabledTrustAgents(i));
            this.mLockPatternUtils.setEnabledTrustAgents(arraySet2, i);
        }
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(), "trust_agents_initialized", 1, i);
    }

    public final void notifyKeystoreOfDeviceLockState(int i, boolean z) {
        if (!z) {
            this.mKeyStoreAuthorization.onDeviceUnlocked(i, (byte[]) null);
            return;
        }
        boolean z2 = false;
        if (!Flags.fixUnlockedDeviceRequiredKeysV2()) {
            KeyStoreAuthorization keyStoreAuthorization = this.mKeyStoreAuthorization;
            BiometricManager biometricManager =
                    (BiometricManager) this.mContext.getSystemService(BiometricManager.class);
            keyStoreAuthorization.onDeviceLocked(
                    i,
                    biometricManager == null
                            ? new long[0]
                            : biometricManager.getAuthenticatorIds(i),
                    false);
            return;
        }
        int resolveProfileParent =
                this.mLockPatternUtils.isProfileWithUnifiedChallenge(i)
                        ? resolveProfileParent(i)
                        : i;
        KeyStoreAuthorization keyStoreAuthorization2 = this.mKeyStoreAuthorization;
        BiometricManager biometricManager2 =
                (BiometricManager) this.mContext.getSystemService(BiometricManager.class);
        long[] authenticatorIds =
                biometricManager2 == null
                        ? new long[0]
                        : biometricManager2.getAuthenticatorIds(resolveProfileParent);
        if (this.mStrongAuthTracker.isBiometricAllowedForUser(false, resolveProfileParent)) {
            int keyguardDisabledFeatures =
                    this.mLockPatternUtils
                            .getDevicePolicyManager()
                            .getKeyguardDisabledFeatures(null, resolveProfileParent);
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if (fingerprintManager != null
                    && (keyguardDisabledFeatures & 32) == 0
                    && fingerprintManager.hasEnrolledTemplates(resolveProfileParent)) {
                SensorProperties sensorProperties =
                        (SensorProperties) this.mFingerprintManager.getSensorProperties().get(0);
                if (sensorProperties.getSensorStrength() == 1
                        || sensorProperties.getSensorStrength() == 0) {
                    Slogf.i(
                            "TrustManagerService",
                            "User is unlockable by non-strong fingerprint auth");
                    z2 = true;
                    keyStoreAuthorization2.onDeviceLocked(i, authenticatorIds, z2);
                }
            }
            FaceManager faceManager = this.mFaceManager;
            if (faceManager != null
                    && (keyguardDisabledFeatures & 128) == 0
                    && faceManager.hasEnrolledTemplates(resolveProfileParent)) {
                SensorProperties sensorProperties2 =
                        (SensorProperties) this.mFaceManager.getSensorProperties().get(0);
                if (sensorProperties2.getSensorStrength() == 1
                        || sensorProperties2.getSensorStrength() == 0) {
                    Slogf.i("TrustManagerService", "User is unlockable by non-strong face auth");
                    z2 = true;
                    keyStoreAuthorization2.onDeviceLocked(i, authenticatorIds, z2);
                }
            }
        }
        if (getUserTrustStateInner(resolveProfileParent) == TrustState.TRUSTABLE
                || (getContext()
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.type.automotive")
                        && isTrustUsuallyManagedInternal(resolveProfileParent))) {
            Slogf.i("TrustManagerService", "User is unlockable by trust agent");
            z2 = true;
        }
        keyStoreAuthorization2.onDeviceLocked(i, authenticatorIds, z2);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (isSafeMode()) {
            return;
        }
        if (i != 500) {
            if (i != 600) {
                if (i == 1000) {
                    maybeEnableFactoryTrustAgents(0);
                    return;
                }
                return;
            }
            this.mTrustAgentsCanRun = true;
            refreshAgentList(-1);
            refreshDeviceLockedForUser(-1);
            if (Flags.significantPlaces()) {
                Context context = this.mContext;
                ServiceWatcherImpl serviceWatcherImpl =
                        new ServiceWatcherImpl(
                                context,
                                FgThread.getHandler(),
                                "TrustManagerService",
                                new CurrentUserServiceSupplier(
                                        context,
                                        "com.android.trust.provider.SignificantPlaceProvider.BIND",
                                        null,
                                        null,
                                        null,
                                        true),
                                new ServiceWatcher$ServiceListener() { // from class:
                                                                       // com.android.server.trust.TrustManagerService.1
                                    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
                                    public final void onBind(
                                            IBinder iBinder,
                                            CurrentUserServiceSupplier.BoundServiceInfo
                                                    boundServiceInfo) {
                                        ISignificantPlaceProvider.Stub.asInterface(iBinder)
                                                .setSignificantPlaceProviderManager(
                                                        new ISignificantPlaceProviderManager
                                                                .Stub() { // from class:
                                                                          // com.android.server.trust.TrustManagerService.1.1
                                                            public final void setInSignificantPlace(
                                                                    boolean z) {
                                                                TrustManagerService.this
                                                                                .mIsInSignificantPlace =
                                                                        z;
                                                            }
                                                        });
                                    }

                                    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
                                    public final void onUnbind() {
                                        TrustManagerService.this.mIsInSignificantPlace = false;
                                    }
                                },
                                null);
                this.mSignificantPlaceServiceWatcher = serviceWatcherImpl;
                serviceWatcherImpl.register();
                return;
            }
            return;
        }
        Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            checkNewAgentsForUser(((UserInfo) it.next()).id);
        }
        PackageMonitor packageMonitor = this.mPackageMonitor;
        Context context2 = this.mContext;
        Looper looper = getLooper();
        UserHandle userHandle = UserHandle.ALL;
        packageMonitor.register(context2, looper, userHandle, true);
        Context context3 = this.mContext;
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        context3.registerReceiverAsUser(receiver, userHandle, intentFilter, null, null);
        this.mLockPatternUtils.registerStrongAuthTracker(this.mStrongAuthTracker);
        this.mFingerprintManager =
                (FingerprintManager) this.mContext.getSystemService(FingerprintManager.class);
        this.mFaceManager = (FaceManager) this.mContext.getSystemService(FaceManager.class);
    }

    public final VerifyCredentialResponse onPassword2Auth(int i) {
        DDLog.d("TrustManagerService", "onPassword2Auth()", new Object[0]);
        if (!SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            DDLog.e(
                    "TrustManagerService",
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i,
                            "User is not DualDAR eligible. so no need to verify DualDAR Passwords"),
                    new Object[0]);
            return VerifyCredentialResponse.OK;
        }
        if (!DualDARController.getInstance(this.mContext).onPassword2Auth(i, (byte[]) null)) {
            DDLog.e(
                    "TrustManagerService",
                    "Authentication Failure by dual dar client",
                    new Object[0]);
            return VerifyCredentialResponse.ERROR;
        }
        DDLog.d("TrustManagerService", "onPassword2Auth completed successfully", new Object[0]);
        StateMachine.processEvent(i, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
        return VerifyCredentialResponse.OK;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("trust", this.mService);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        obtainMessage(7, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        obtainMessage(8, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        obtainMessage(12, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        obtainMessage(9, targetUser2.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        obtainMessage(11, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x02d5, code lost:

       if (r4 == null) goto L164;
    */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x02b7, code lost:

       r4 = r23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x02b4, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02c6, code lost:

       if (r4 == null) goto L164;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02b2, code lost:

       if (r4 == null) goto L164;
    */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x021b, code lost:

       if ("trust-agent".equals(r4.getName()) != false) goto L113;
    */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x022c, code lost:

       r0 = r0.obtainAttributes(r8, com.android.internal.R.styleable.TrustAgent);
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x023b, code lost:

       r23 = r0.getString(2);
       r6 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x023e, code lost:

       r8 = r8.getAttributeBooleanValue("http://schemas.android.com/apk/prv/res/android", "unlockProfile", false);
    */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0242, code lost:

       r0.recycle();
    */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0245, code lost:

       r4.close();
       r4 = r23;
       r0 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0253, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0250, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x024d, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x025e, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x025f, code lost:

       r8 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x025a, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x025b, code lost:

       r8 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0256, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0257, code lost:

       r8 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x021d, code lost:

       com.android.server.utils.Slogf.w("TrustManagerService", "Meta-data does not start with trust-agent tag");
    */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0222, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x022a, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0228, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0226, code lost:

       r0 = e;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v140, types: [java.util.List] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshAgentList(int r25) {
        /*
            Method dump skipped, instructions count: 1072
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.trust.TrustManagerService.refreshAgentList(int):void");
    }

    public final void refreshDeviceLockedForUser(int i) {
        refreshDeviceLockedForUser(i, -10000);
    }

    public final void refreshDeviceLockedForUser(int i, int i2) {
        List list;
        boolean z;
        boolean z2;
        boolean z3;
        if (i != -1 && i < 0) {
            Log.e(
                    "TrustManagerService",
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i,
                            "refreshDeviceLockedForUser(userId=",
                            "): Invalid user handle, must be USER_ALL or a specific user."),
                    new Throwable("here"));
            i = -1;
        }
        if (i == -1) {
            list = this.mUserManager.getAliveUsers();
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.mUserManager.getUserInfo(i));
            list = arrayList;
        }
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        for (int i3 = 0; i3 < list.size(); i3++) {
            UserInfo userInfo = (UserInfo) list.get(i3);
            if (userInfo != null
                    && !userInfo.partial
                    && userInfo.isEnabled()
                    && !userInfo.guestToRemove) {
                int i4 = userInfo.id;
                boolean isSecure = this.mLockPatternUtils.isSecure(i4);
                if (userInfo.supportsSwitchToByUser()) {
                    boolean aggregateIsTrusted =
                            Flags.fixUnlockedDeviceRequiredKeysV2()
                                    ? getUserTrustStateInner(i4) == TrustState.TRUSTED
                                    : aggregateIsTrusted(i4);
                    if (this.mCurrentUser == i4) {
                        synchronized (this.mUsersUnlockedByBiometric) {
                            z = this.mUsersUnlockedByBiometric.get(i4, false);
                        }
                        try {
                            z3 = windowManagerService.isKeyguardLocked();
                        } catch (RemoteException e) {
                            Log.w("TrustManagerService", "Unable to check keyguard lock state", e);
                            z3 = true;
                        }
                        z2 = i2 == i4;
                    } else {
                        z = false;
                        z2 = false;
                        z3 = true;
                    }
                    boolean z4 = isSecure && z3 && !aggregateIsTrusted && !z;
                    if (!z4 || !z2) {
                        setDeviceLockedForUser(i4, z4);
                        if (z4) {
                            if (getPersonaManagerInternal() != null) {
                                getPersonaManagerInternal().onDeviceLockedChanged(i4);
                            } else {
                                Log.e(
                                        "TrustManagerService",
                                        "onDeviceLockedChanged() - Service is not yet ready...");
                            }
                        }
                    }
                } else if (userInfo.isProfile()
                        && !isSecure
                        && !this.mLockPatternUtils.isProfileWithUnifiedChallenge(i4)) {
                    setDeviceLockedForUser(i4, false);
                }
            }
        }
    }

    public final int resolveProfileParent(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo profileParent = this.mUserManager.getProfileParent(i);
            return profileParent != null ? profileParent.getUserHandle().getIdentifier() : i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDeviceLockedForUser(int i, boolean z) {
        int i2;
        boolean z2;
        synchronized (this.mDeviceLockedForUser) {
            try {
                z2 = isDeviceLockedInner(i) != z;
                this.mDeviceLockedForUser.put(i, z);
                if (z2) {
                    Log.d("TrustManagerService", "User " + i + ", setDeviceLockedForUser " + z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z2 && DualDarManager.isOnDeviceOwner(i)) {
            Log.d(
                    "TrustManagerService",
                    "setDeviceLockedForUser - locked state is not changed though");
            handleDualDARDeviceLockedChangedForUser(i, z);
        }
        if (z2 || this.mIsUnlockedByFP) {
            handleDualDARDeviceLockedChangedForUser(i, z);
            for (int i3 = 0; i3 < this.mActiveAgents.size(); i3++) {
                AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i3);
                if (agentInfo.userId == i) {
                    if (z) {
                        TrustAgentWrapper trustAgentWrapper = agentInfo.agent;
                        trustAgentWrapper.mWithinSecurityLockdownWindow = false;
                        try {
                            ITrustAgentService iTrustAgentService =
                                    trustAgentWrapper.mTrustAgentService;
                            if (iTrustAgentService != null) {
                                iTrustAgentService.onDeviceLocked();
                            }
                        } catch (RemoteException e) {
                            TrustAgentWrapper.onError(e);
                        }
                    } else {
                        TrustAgentWrapper trustAgentWrapper2 = agentInfo.agent;
                        trustAgentWrapper2.getClass();
                        try {
                            ITrustAgentService iTrustAgentService2 =
                                    trustAgentWrapper2.mTrustAgentService;
                            if (iTrustAgentService2 != null) {
                                iTrustAgentService2.onDeviceUnlocked();
                            }
                        } catch (RemoteException e2) {
                            TrustAgentWrapper.onError(e2);
                        }
                    }
                }
            }
            notifyKeystoreOfDeviceLockState(i, z);
            for (int i4 : this.mUserManager.getEnabledProfileIds(i)) {
                if (this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(i4)) {
                    notifyKeystoreOfDeviceLockState(i4, z);
                }
            }
        }
    }

    public final void updateDualDARStateAndUnlockIfNeeded(int i) {
        int dualDARUser;
        DDLog.d(
                "TrustManagerService",
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "updateDualDARStateAndUnlockIfNeeded() - userId : "),
                new Object[0]);
        if (PersonaServiceHelper.isDualDAREnabled()) {
            if (!((SystemServiceManager) LocalServices.getService(SystemServiceManager.class))
                            .isBootCompleted()
                    || !StorageManager.isCeStorageUnlocked(i)) {
                if (!DualDarManager.isOnDeviceOwner(i)) {
                    return;
                } else {
                    DDLog.d(
                            "TrustManagerService",
                            "Update dualdar state early for DO",
                            new Object[0]);
                }
            }
            if (i != 0 || (dualDARUser = PersonaServiceHelper.getDualDARUser()) == -1) {
                return;
            }
            StateMachine.processEvent(dualDARUser, Event.DEVICE_AUTH_SUCCESS);
            KeyguardManager keyguardManager =
                    (KeyguardManager) this.mContext.getSystemService("keyguard");
            DDLog.d(
                    "TrustManagerService",
                    "Trying to unlock DualDAR user (TMS), isSecure? "
                            + keyguardManager.isDeviceSecure(dualDARUser),
                    new Object[0]);
            if (DualDarManager.isOnDeviceOwner(i)) {
                DDLog.d("TrustManagerService", "DualDAR on DO is enabled", new Object[0]);
                if (keyguardManager.isDeviceSecure(dualDARUser)) {
                    return;
                }
                if (!DualDarManager.getInstance(this.mContext).isInnerLayerUnlocked(i)
                        || SemPersonaManager.isDarDualEncrypted(i)) {
                    if (onPassword2Auth(dualDARUser) == VerifyCredentialResponse.OK) {
                        DDLog.d(
                                "TrustManagerService",
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        i, "Inner layer for user ", " authenticated"),
                                new Object[0]);
                        return;
                    } else {
                        DDLog.e(
                                "TrustManagerService",
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i, "Failed in inner layer authentication for user "),
                                new Object[0]);
                        return;
                    }
                }
                return;
            }
            if (keyguardManager.isDeviceSecure(dualDARUser)
                    || this.mUserManager.isUserUnlockingOrUnlocked(dualDARUser)
                    || keyguardManager.isDeviceLocked(0)) {
                return;
            }
            DDLog.d(
                    "TrustManagerService",
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            dualDARUser, "Trying to unlock DualDAR user "),
                    new Object[0]);
            if (onPassword2Auth(dualDARUser) != VerifyCredentialResponse.OK) {
                DDLog.e(
                        "TrustManagerService",
                        "Default Authentication Failure by DualDAR client",
                        new Object[0]);
                return;
            }
            try {
                DDLog.d(
                        "TrustManagerService",
                        "fetch outer layer key and unlock DualDAR user " + dualDARUser,
                        new Object[0]);
                DDLog.d("TrustManagerService", "fetchOuterLayerKey()", new Object[0]);
                ActivityManager.getService()
                        .unlockUser(
                                dualDARUser,
                                (byte[]) null,
                                DualDARController.getInstance(this.mContext)
                                        .fetchOuterLayerKey(dualDARUser),
                                (IProgressListener) null);
            } catch (Exception e) {
                e.printStackTrace();
                DDLog.e(
                        "TrustManagerService",
                        "Failed to unlock DualDAR user !" + dualDARUser,
                        new Object[0]);
            }
        }
    }

    public final void updateTrust(int i) {
        updateTrust(i, 0, false, null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:0|1|(1:3)|4|(1:6)|7|(5:10|11|13|14|8)|20|21|(3:25|8c|30)|36|(1:38)|39|(1:(1:42))(3:208|(4:211|(2:213|(9:219|(1:221)|222|44|45|46|47|48|11c))(1:227)|224|209)|228)|43|44|45|46|47|48|11c|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0119, code lost:

       r3 = false;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0296 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0165 A[Catch: all -> 0x018a, TryCatch #6 {all -> 0x018a, blocks: (B:51:0x011d, B:54:0x012a, B:58:0x0139, B:64:0x0146, B:68:0x015a, B:71:0x0161, B:73:0x0165, B:76:0x0191, B:83:0x01aa, B:84:0x01b6, B:85:0x01bb, B:196:0x01a5), top: B:50:0x011d }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01aa A[Catch: all -> 0x018a, TryCatch #6 {all -> 0x018a, blocks: (B:51:0x011d, B:54:0x012a, B:58:0x0139, B:64:0x0146, B:68:0x015a, B:71:0x0161, B:73:0x0165, B:76:0x0191, B:83:0x01aa, B:84:0x01b6, B:85:0x01bb, B:196:0x01a5), top: B:50:0x011d }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01eb  */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTrust(
            int r24, int r25, boolean r26, com.android.internal.infra.AndroidFuture r27) {
        /*
            Method dump skipped, instructions count: 829
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.trust.TrustManagerService.updateTrust(int, int, boolean,"
                    + " com.android.internal.infra.AndroidFuture):void");
    }

    public final void updateTrustAll() {
        Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            updateTrust(((UserInfo) it.next()).id);
        }
    }

    public void waitForIdle() {
        runWithScissors(new TrustManagerService$$ExternalSyntheticLambda0(0), 0L);
    }
}
