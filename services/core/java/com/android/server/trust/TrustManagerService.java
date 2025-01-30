package com.android.server.trust;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.ITrustListener;
import android.app.trust.ITrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
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
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.security.Authorization;
import android.service.trust.GrantTrustResult;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
import com.android.internal.content.PackageMonitor;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.DumpUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class TrustManagerService extends SystemService {
  public static final boolean DEBUG;
  public static final boolean ENABLE_ACTIVE_UNLOCK_FLAG;
  public static final Intent TRUST_AGENT_INTENT;
  public static final boolean isChina;
  public final ArraySet mActiveAgents;
  public final ActivityManager mActivityManager;
  public final Object mAlarmLock;
  public AlarmManager mAlarmManager;
  public final TrustArchive mArchive;
  public final Context mContext;
  public int mCurrentUser;
  public final SparseBooleanArray mDeviceLockedForUser;
  public final Handler mHandler;
  public final SparseArray mIdleTrustableTimeoutAlarmListenerForUser;
  public boolean mIsUnlockedByFP;
  public final SparseBooleanArray mLastActiveUnlockRunningState;
  public final LockPatternUtils mLockPatternUtils;
  public final PackageMonitor mPackageMonitor;
  public PersonaManagerInternal mPersonaManagerInternal;
  public final Receiver mReceiver;
  public final IBinder mService;
  public final SettingsObserver mSettingsObserver;
  public final StrongAuthTracker mStrongAuthTracker;
  public boolean mTrustAgentsCanRun;
  public final ArrayList mTrustListeners;
  public final ArrayMap mTrustTimeoutAlarmListenerForUser;
  public final SparseBooleanArray mTrustUsuallyManagedForUser;
  public final SparseArray mTrustableTimeoutAlarmListenerForUser;
  public final SparseBooleanArray mUserIsTrusted;
  public final UserManager mUserManager;
  public final SparseArray mUserTrustState;
  public final SparseBooleanArray mUsersUnlockedByBiometric;
  public VirtualDeviceManagerInternal mVirtualDeviceManager;

  enum TimeoutType {
    TRUSTED,
    TRUSTABLE
  }

  enum TrustState {
    UNTRUSTED,
    TRUSTABLE,
    TRUSTED
  }

  static {
    DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("TrustManagerService", 2);
    TRUST_AGENT_INTENT = new Intent("android.service.trust.TrustAgentService");
    isChina = "CHINA".equalsIgnoreCase(SemSystemProperties.getCountryCode());
    ENABLE_ACTIVE_UNLOCK_FLAG = SystemProperties.getBoolean("fw.enable_active_unlock_flag", true);
  }

  public class Injector {
    public final LockPatternUtils mLockPatternUtils;
    public final Looper mLooper;

    public Injector(LockPatternUtils lockPatternUtils, Looper looper) {
      this.mLockPatternUtils = lockPatternUtils;
      this.mLooper = looper;
    }

    public LockPatternUtils getLockPatternUtils() {
      return this.mLockPatternUtils;
    }

    public Looper getLooper() {
      return this.mLooper;
    }
  }

  public TrustManagerService(Context context) {
    this(context, new Injector(new LockPatternUtils(context), Looper.myLooper()));
  }

  public TrustManagerService(Context context, Injector injector) {
    super(context);
    this.mActiveAgents = new ArraySet();
    this.mLastActiveUnlockRunningState = new SparseBooleanArray();
    this.mTrustListeners = new ArrayList();
    this.mReceiver = new Receiver();
    this.mArchive = new TrustArchive();
    this.mUserIsTrusted = new SparseBooleanArray();
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
    this.mService = new ITrustManagerStubC26951();
    this.mPackageMonitor =
        new PackageMonitor() { // from class: com.android.server.trust.TrustManagerService.3
          public void onSomePackagesChanged() {
            TrustManagerService.this.refreshAgentList(-1);
          }

          public void onPackageAdded(String str, int i) {
            TrustManagerService.this.checkNewAgentsForUser(UserHandle.getUserId(i));
          }

          public boolean onPackageChanged(String str, int i, String[] strArr) {
            TrustManagerService.this.checkNewAgentsForUser(UserHandle.getUserId(i));
            return true;
          }

          public void onPackageDisappeared(String str, int i) {
            TrustManagerService.this.removeAgentsOfPackage(str);
          }
        };
    this.mContext = context;
    Handler createHandler = createHandler(injector.getLooper());
    this.mHandler = createHandler;
    this.mUserManager = (UserManager) context.getSystemService("user");
    this.mActivityManager = (ActivityManager) context.getSystemService("activity");
    this.mLockPatternUtils = injector.getLockPatternUtils();
    this.mStrongAuthTracker = new StrongAuthTracker(context, injector.getLooper());
    this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
    this.mSettingsObserver = new SettingsObserver(createHandler);
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("trust", this.mService);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (isSafeMode()) {
      return;
    }
    if (i == 500) {
      checkNewAgents();
      this.mPackageMonitor.register(this.mContext, this.mHandler.getLooper(), UserHandle.ALL, true);
      this.mReceiver.register(this.mContext);
      this.mLockPatternUtils.registerStrongAuthTracker(this.mStrongAuthTracker);
      return;
    }
    if (i == 600) {
      this.mTrustAgentsCanRun = true;
      refreshAgentList(-1);
      refreshDeviceLockedForUser(-1);
    } else if (i == 1000) {
      maybeEnableFactoryTrustAgents(0);
    }
  }

  public final class SettingsObserver extends ContentObserver {
    public final Uri LOCK_SCREEN_WHEN_TRUST_LOST;
    public final Uri TRUST_AGENTS_EXTEND_UNLOCK;
    public final ContentResolver mContentResolver;
    public final boolean mIsAutomotive;
    public boolean mLockWhenTrustLost;
    public boolean mTrustAgentsNonrenewableTrust;

    public SettingsObserver(Handler handler) {
      super(handler);
      this.TRUST_AGENTS_EXTEND_UNLOCK = Settings.Secure.getUriFor("trust_agents_extend_unlock");
      this.LOCK_SCREEN_WHEN_TRUST_LOST = Settings.Secure.getUriFor("lock_screen_when_trust_lost");
      this.mIsAutomotive =
          TrustManagerService.this
              .getContext()
              .getPackageManager()
              .hasSystemFeature("android.hardware.type.automotive");
      this.mContentResolver = TrustManagerService.this.getContext().getContentResolver();
      updateContentObserver();
    }

    public void updateContentObserver() {
      this.mContentResolver.unregisterContentObserver(this);
      this.mContentResolver.registerContentObserver(
          this.TRUST_AGENTS_EXTEND_UNLOCK, false, this, TrustManagerService.this.mCurrentUser);
      this.mContentResolver.registerContentObserver(
          this.LOCK_SCREEN_WHEN_TRUST_LOST, false, this, TrustManagerService.this.mCurrentUser);
      onChange(true, this.TRUST_AGENTS_EXTEND_UNLOCK);
      onChange(true, this.LOCK_SCREEN_WHEN_TRUST_LOST);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (this.TRUST_AGENTS_EXTEND_UNLOCK.equals(uri)) {
        this.mTrustAgentsNonrenewableTrust =
            Settings.Secure.getIntForUser(
                    this.mContentResolver,
                    "trust_agents_extend_unlock",
                    !this.mIsAutomotive ? 1 : 0,
                    TrustManagerService.this.mCurrentUser)
                != 0;
      } else if (this.LOCK_SCREEN_WHEN_TRUST_LOST.equals(uri)) {
        this.mLockWhenTrustLost =
            Settings.Secure.getIntForUser(
                    this.mContentResolver,
                    "lock_screen_when_trust_lost",
                    0,
                    TrustManagerService.this.mCurrentUser)
                != 0;
      }
    }

    public boolean getTrustAgentsNonrenewableTrust() {
      return this.mTrustAgentsNonrenewableTrust;
    }

    public boolean getLockWhenTrustLost() {
      return this.mLockWhenTrustLost;
    }
  }

  public final void maybeLockScreen(int i) {
    if (i == this.mCurrentUser && this.mSettingsObserver.getLockWhenTrustLost()) {
      if (DEBUG) {
        Slog.d("TrustManagerService", "Locking device because trust was lost");
      }
      try {
        WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
      } catch (RemoteException unused) {
        Slog.e("TrustManagerService", "Error locking screen when trust was lost");
      }
      TrustedTimeoutAlarmListener trustedTimeoutAlarmListener =
          (TrustedTimeoutAlarmListener)
              this.mTrustTimeoutAlarmListenerForUser.get(Integer.valueOf(i));
      if (trustedTimeoutAlarmListener == null
          || !this.mSettingsObserver.getTrustAgentsNonrenewableTrust()) {
        return;
      }
      this.mAlarmManager.cancel(trustedTimeoutAlarmListener);
      trustedTimeoutAlarmListener.setQueued(false);
    }
  }

  public final void scheduleTrustTimeout(boolean z, boolean z2) {
    this.mHandler.obtainMessage(15, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
  }

  public final void handleScheduleTrustTimeout(boolean z, TimeoutType timeoutType) {
    int i = this.mCurrentUser;
    if (timeoutType == TimeoutType.TRUSTABLE) {
      handleScheduleTrustableTimeouts(i, z, false);
    } else {
      handleScheduleTrustedTimeout(i, z);
    }
  }

  public final void refreshTrustableTimers(int i) {
    handleScheduleTrustableTimeouts(i, true, true);
  }

  public final void cancelBothTrustableAlarms(int i) {
    TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
        (TrustableTimeoutAlarmListener) this.mIdleTrustableTimeoutAlarmListenerForUser.get(i);
    TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 =
        (TrustableTimeoutAlarmListener) this.mTrustableTimeoutAlarmListenerForUser.get(i);
    if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.isQueued()) {
      trustableTimeoutAlarmListener.setQueued(false);
      this.mAlarmManager.cancel(trustableTimeoutAlarmListener);
    }
    if (trustableTimeoutAlarmListener2 == null || !trustableTimeoutAlarmListener2.isQueued()) {
      return;
    }
    trustableTimeoutAlarmListener2.setQueued(false);
    this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
  }

  public final void handleScheduleTrustedTimeout(int i, boolean z) {
    TrustedTimeoutAlarmListener trustedTimeoutAlarmListener;
    long elapsedRealtime =
        SystemClock.elapsedRealtime()
            + BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
    TrustedTimeoutAlarmListener trustedTimeoutAlarmListener2 =
        (TrustedTimeoutAlarmListener)
            this.mTrustTimeoutAlarmListenerForUser.get(Integer.valueOf(i));
    if (trustedTimeoutAlarmListener2 != null) {
      if (!z && trustedTimeoutAlarmListener2.isQueued()) {
        if (DEBUG) {
          Slog.d("TrustManagerService", "Found existing trust timeout alarm. Skipping.");
          return;
        }
        return;
      }
      this.mAlarmManager.cancel(trustedTimeoutAlarmListener2);
      trustedTimeoutAlarmListener = trustedTimeoutAlarmListener2;
    } else {
      TrustedTimeoutAlarmListener trustedTimeoutAlarmListener3 = new TrustedTimeoutAlarmListener(i);
      this.mTrustTimeoutAlarmListenerForUser.put(Integer.valueOf(i), trustedTimeoutAlarmListener3);
      trustedTimeoutAlarmListener = trustedTimeoutAlarmListener3;
    }
    if (DEBUG) {
      Slog.d("TrustManagerService", "\tSetting up trust timeout alarm");
    }
    trustedTimeoutAlarmListener.setQueued(true);
    this.mAlarmManager.setExact(
        2,
        elapsedRealtime,
        "TrustManagerService.trustTimeoutForUser",
        trustedTimeoutAlarmListener,
        this.mHandler);
  }

  public final void handleScheduleTrustableTimeouts(int i, boolean z, boolean z2) {
    setUpIdleTimeout(i, z);
    setUpHardTimeout(i, z2);
  }

  public final void setUpIdleTimeout(int i, boolean z) {
    TrustableTimeoutAlarmListener trustableTimeoutAlarmListener;
    long elapsedRealtime = SystemClock.elapsedRealtime() + 28800000;
    TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 =
        (TrustableTimeoutAlarmListener) this.mIdleTrustableTimeoutAlarmListenerForUser.get(i);
    this.mContext.enforceCallingOrSelfPermission("android.permission.SCHEDULE_EXACT_ALARM", null);
    if (trustableTimeoutAlarmListener2 != null) {
      if (!z && trustableTimeoutAlarmListener2.isQueued()) {
        if (DEBUG) {
          Slog.d("TrustManagerService", "Found existing trustable timeout alarm. Skipping.");
          return;
        }
        return;
      }
      this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
      trustableTimeoutAlarmListener = trustableTimeoutAlarmListener2;
    } else {
      TrustableTimeoutAlarmListener trustableTimeoutAlarmListener3 =
          new TrustableTimeoutAlarmListener(i);
      this.mIdleTrustableTimeoutAlarmListenerForUser.put(i, trustableTimeoutAlarmListener3);
      trustableTimeoutAlarmListener = trustableTimeoutAlarmListener3;
    }
    if (DEBUG) {
      Slog.d("TrustManagerService", "\tSetting up trustable idle timeout alarm");
    }
    trustableTimeoutAlarmListener.setQueued(true);
    this.mAlarmManager.setExact(
        2,
        elapsedRealtime,
        "TrustManagerService.trustTimeoutForUser",
        trustableTimeoutAlarmListener,
        this.mHandler);
  }

  public final void setUpHardTimeout(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission("android.permission.SCHEDULE_EXACT_ALARM", null);
    TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
        (TrustableTimeoutAlarmListener) this.mTrustableTimeoutAlarmListenerForUser.get(i);
    if (trustableTimeoutAlarmListener == null || !trustableTimeoutAlarmListener.isQueued() || z) {
      long elapsedRealtime =
          SystemClock.elapsedRealtime()
              + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
      if (trustableTimeoutAlarmListener == null) {
        trustableTimeoutAlarmListener = new TrustableTimeoutAlarmListener(i);
        this.mTrustableTimeoutAlarmListenerForUser.put(i, trustableTimeoutAlarmListener);
      } else if (z) {
        this.mAlarmManager.cancel(trustableTimeoutAlarmListener);
      }
      TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 = trustableTimeoutAlarmListener;
      if (DEBUG) {
        Slog.d("TrustManagerService", "\tSetting up trustable hard timeout alarm");
      }
      trustableTimeoutAlarmListener2.setQueued(true);
      this.mAlarmManager.setExact(
          2,
          elapsedRealtime,
          "TrustManagerService.trustTimeoutForUser",
          trustableTimeoutAlarmListener2,
          this.mHandler);
    }
  }

  public final class AgentInfo {
    public TrustAgentWrapper agent;
    public ComponentName component;
    public Drawable icon;
    public CharSequence label;
    public SettingsAttrs settings;
    public int userId;

    public AgentInfo() {}

    public boolean equals(Object obj) {
      if (!(obj instanceof AgentInfo)) {
        return false;
      }
      AgentInfo agentInfo = (AgentInfo) obj;
      return this.component.equals(agentInfo.component) && this.userId == agentInfo.userId;
    }

    public int hashCode() {
      return (this.component.hashCode() * 31) + this.userId;
    }
  }

  public final void updateTrustAll() {
    Iterator it = this.mUserManager.getAliveUsers().iterator();
    while (it.hasNext()) {
      updateTrust(((UserInfo) it.next()).id, 0);
    }
  }

  public void updateTrust(int i, int i2) {
    updateTrust(i, i2, null);
  }

  public void updateTrust(int i, int i2, AndroidFuture androidFuture) {
    updateTrust(i, i2, false, androidFuture);
  }

  public final void updateTrust(int i, int i2, boolean z, AndroidFuture androidFuture) {
    if (ENABLE_ACTIVE_UNLOCK_FLAG) {
      updateTrustWithRenewableUnlock(i, i2, z, androidFuture);
    } else {
      updateTrustWithNonrenewableTrust(i, i2, z);
    }
  }

  public final void updateTrustWithNonrenewableTrust(int i, int i2, boolean z) {
    boolean z2;
    boolean aggregateIsTrustManaged = aggregateIsTrustManaged(i);
    dispatchOnTrustManagedChanged(aggregateIsTrustManaged, i);
    if (this.mStrongAuthTracker.isTrustAllowedForUser(i)
        && isTrustUsuallyManagedInternal(i) != aggregateIsTrustManaged) {
      updateTrustUsuallyManaged(i, aggregateIsTrustManaged);
    }
    boolean aggregateIsTrusted = aggregateIsTrusted(i);
    boolean z3 = true;
    try {
      z2 = WindowManagerGlobal.getWindowManagerService().isKeyguardLocked();
    } catch (RemoteException unused) {
      z2 = true;
    }
    synchronized (this.mUserIsTrusted) {
      if (this.mSettingsObserver.getTrustAgentsNonrenewableTrust()) {
        aggregateIsTrusted =
            aggregateIsTrusted
                && !(z2 && !z && (this.mUserIsTrusted.get(i) != aggregateIsTrusted))
                && i == this.mCurrentUser;
        if (DEBUG) {
          StringBuilder sb = new StringBuilder();
          sb.append("Extend unlock setting trusted as ");
          sb.append(Boolean.toString(aggregateIsTrusted));
          sb.append(" && ");
          sb.append(Boolean.toString(!z2));
          sb.append(" && ");
          sb.append(Boolean.toString(i == this.mCurrentUser));
          Slog.d("TrustManagerService", sb.toString());
        }
      }
      if (this.mUserIsTrusted.get(i) == aggregateIsTrusted) {
        z3 = false;
      }
      this.mUserIsTrusted.put(i, aggregateIsTrusted);
    }
    dispatchOnTrustChanged(aggregateIsTrusted, false, i, i2, getTrustGrantedMessages(i));
    if (z3) {
      refreshDeviceLockedForUser(i);
      if (!aggregateIsTrusted) {
        maybeLockScreen(i);
      } else {
        scheduleTrustTimeout(false, false);
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:32:0x0077  */
  /* JADX WARN: Removed duplicated region for block: B:46:0x009d  */
  /* JADX WARN: Removed duplicated region for block: B:48:0x00b6  */
  /* JADX WARN: Removed duplicated region for block: B:54:0x00d7  */
  /* JADX WARN: Removed duplicated region for block: B:64:0x00ee  */
  /* JADX WARN: Removed duplicated region for block: B:72:0x0107  */
  /* JADX WARN: Removed duplicated region for block: B:77:0x00b8  */
  /* JADX WARN: Removed duplicated region for block: B:84:0x0079  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateTrustWithRenewableUnlock(
      int i, int i2, boolean z, AndroidFuture androidFuture) {
    boolean z2;
    boolean z3;
    boolean z4;
    boolean z5;
    boolean z6;
    boolean aggregateIsTrustManaged = aggregateIsTrustManaged(i);
    dispatchOnTrustManagedChanged(aggregateIsTrustManaged, i);
    if (this.mStrongAuthTracker.isTrustAllowedForUser(i)
        && isTrustUsuallyManagedInternal(i) != aggregateIsTrustManaged) {
      updateTrustUsuallyManaged(i, aggregateIsTrustManaged);
    }
    boolean aggregateIsTrusted = aggregateIsTrusted(i);
    boolean aggregateIsTrustable = aggregateIsTrustable(i);
    try {
      z2 = !WindowManagerGlobal.getWindowManagerService().isKeyguardLocked();
    } catch (RemoteException unused) {
      z2 = false;
    }
    synchronized (this.mUserTrustState) {
      Object obj = this.mUserTrustState.get(i);
      TrustState trustState = TrustState.TRUSTED;
      boolean z7 = obj == trustState;
      Object obj2 = this.mUserTrustState.get(i);
      TrustState trustState2 = TrustState.TRUSTABLE;
      boolean z8 = obj2 == trustState2;
      boolean hasSystemFeature =
          getContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive");
      boolean z9 = z8 && (i2 & 4) != 0;
      if (!z2 && !z && !z9 && !hasSystemFeature) {
        z3 = false;
        boolean z10 = i != this.mCurrentUser;
        if (aggregateIsTrusted || !z7) {
          if (!aggregateIsTrusted && z3 && z10) {
            trustState2 = trustState;
          } else if (aggregateIsTrustable || ((!z7 && !z8) || !z10)) {
            trustState2 = TrustState.UNTRUSTED;
          }
          this.mUserTrustState.put(i, trustState2);
          z4 = DEBUG;
          if (z4) {
            Slog.d("TrustManagerService", "pendingTrustState: " + trustState2);
          }
          z5 = trustState2 != trustState;
          z6 = z2 && z5;
          maybeActiveUnlockRunningChanged(i);
          dispatchOnTrustChanged(z5, z6, i, i2, getTrustGrantedMessages(i));
          if (z5 != z7) {
            refreshDeviceLockedForUser(i);
            if (!z5) {
              maybeLockScreen(i);
            } else {
              boolean z11 = (i2 & 4) != 0;
              scheduleTrustTimeout(z11, z11);
            }
          }
          if (z6 && androidFuture != null) {
            if (z4) {
              Slog.d("TrustManagerService", "calling back with UNLOCKED_BY_GRANT");
            }
            androidFuture.complete(new GrantTrustResult(1));
          }
          if ((!z7 || z8) && trustState2 == TrustState.UNTRUSTED) {
            if (z4) {
              Slog.d("TrustManagerService", "Trust was revoked, destroy trustable alarms");
            }
            cancelBothTrustableAlarms(i);
          }
          return;
        }
        return;
      }
      z3 = true;
      if (i != this.mCurrentUser) {}
      if (aggregateIsTrusted) {}
      if (!aggregateIsTrusted) {}
      if (aggregateIsTrustable) {}
      trustState2 = TrustState.UNTRUSTED;
      this.mUserTrustState.put(i, trustState2);
      z4 = DEBUG;
      if (z4) {}
      if (trustState2 != trustState) {}
      if (z2) {}
      maybeActiveUnlockRunningChanged(i);
      dispatchOnTrustChanged(z5, z6, i, i2, getTrustGrantedMessages(i));
      if (z5 != z7) {}
      if (z6) {
        if (z4) {}
        androidFuture.complete(new GrantTrustResult(1));
      }
      if (z7) {}
      if (z4) {}
      cancelBothTrustableAlarms(i);
    }
  }

  public final void updateTrustUsuallyManaged(int i, boolean z) {
    synchronized (this.mTrustUsuallyManagedForUser) {
      this.mTrustUsuallyManagedForUser.put(i, z);
    }
    this.mHandler.removeMessages(10);
    Handler handler = this.mHandler;
    handler.sendMessageDelayed(handler.obtainMessage(10), 120000L);
  }

  public long addEscrowToken(byte[] bArr, int i) {
    return this.mLockPatternUtils.addEscrowToken(
        bArr,
        i,
        new LockPatternUtils
            .EscrowTokenStateChangeCallback() { // from class:
                                                // com.android.server.trust.TrustManagerService$$ExternalSyntheticLambda0
          public final void onEscrowTokenActivated(long j, int i2) {
            TrustManagerService.this.lambda$addEscrowToken$0(j, i2);
          }
        });
  }

  public boolean removeEscrowToken(long j, int i) {
    return this.mLockPatternUtils.removeEscrowToken(j, i);
  }

  public boolean isEscrowTokenActive(long j, int i) {
    return this.mLockPatternUtils.isEscrowTokenActive(j, i);
  }

  public void unlockUserWithToken(long j, byte[] bArr, int i) {
    this.mLockPatternUtils.unlockUserWithToken(j, bArr, i);
  }

  public void lockUser(int i) {
    this.mLockPatternUtils.requireStrongAuth(256, i);
    try {
      WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
    } catch (RemoteException unused) {
      Slog.e("TrustManagerService", "Error locking screen when called from trust agent");
    }
  }

  public void showKeyguardErrorMessage(CharSequence charSequence) {
    dispatchOnTrustError(charSequence);
  }

  public void refreshAgentList(int i) {
    List arrayList;
    Iterator it;
    int strongAuthForUser;
    int i2 = i;
    if (DEBUG) {
      Slog.d("TrustManagerService", "refreshAgentList(" + i2 + ")");
    }
    if (this.mTrustAgentsCanRun) {
      if (i2 != -1 && i2 < 0) {
        Log.e(
            "TrustManagerService",
            "refreshAgentList(userId="
                + i2
                + "): Invalid user handle, must be USER_ALL or a specific user.",
            new Throwable("here"));
        i2 = -1;
      }
      PackageManager packageManager = this.mContext.getPackageManager();
      if (i2 == -1) {
        arrayList = this.mUserManager.getAliveUsers();
      } else {
        arrayList = new ArrayList();
        arrayList.add(this.mUserManager.getUserInfo(i2));
      }
      LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
      ArraySet arraySet = new ArraySet();
      arraySet.addAll(this.mActiveAgents);
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        UserInfo userInfo = (UserInfo) it2.next();
        if (userInfo != null
            && !userInfo.partial
            && userInfo.isEnabled()
            && !userInfo.guestToRemove) {
          if (!userInfo.supportsSwitchToByUser()) {
            if (DEBUG) {
              Slog.d(
                  "TrustManagerService",
                  "refreshAgentList: skipping user " + userInfo.id + ": switchToByUser=false");
            }
          } else if (!this.mActivityManager.isUserRunning(userInfo.id)) {
            if (DEBUG) {
              Slog.d(
                  "TrustManagerService",
                  "refreshAgentList: skipping user " + userInfo.id + ": user not started");
            }
          } else if (!lockPatternUtils.isSecure(userInfo.id)) {
            if (DEBUG) {
              Slog.d(
                  "TrustManagerService",
                  "refreshAgentList: skipping user " + userInfo.id + ": no secure credential");
            }
          } else {
            DevicePolicyManager devicePolicyManager = lockPatternUtils.getDevicePolicyManager();
            boolean z =
                (devicePolicyManager.getKeyguardDisabledFeatures(null, userInfo.id) & 16) != 0;
            List enabledTrustAgents = lockPatternUtils.getEnabledTrustAgents(userInfo.id);
            if (enabledTrustAgents.isEmpty()) {
              if (DEBUG) {
                Slog.d(
                    "TrustManagerService",
                    "refreshAgentList: skipping user "
                        + userInfo.id
                        + ": no agents enabled by user");
              }
            } else {
              Iterator it3 = resolveAllowedTrustAgents(packageManager, userInfo.id).iterator();
              while (it3.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo) it3.next();
                ComponentName componentName = getComponentName(resolveInfo);
                Iterator it4 = it2;
                if (!enabledTrustAgents.contains(componentName)) {
                  if (DEBUG) {
                    Slog.d(
                        "TrustManagerService",
                        "refreshAgentList: skipping "
                            + componentName.flattenToShortString()
                            + " u"
                            + userInfo.id
                            + ": not enabled by user");
                  }
                  it2 = it4;
                } else {
                  LockPatternUtils lockPatternUtils2 = lockPatternUtils;
                  if (z) {
                    it = it3;
                    List trustAgentConfiguration =
                        devicePolicyManager.getTrustAgentConfiguration(
                            null, componentName, userInfo.id);
                    if (trustAgentConfiguration == null || trustAgentConfiguration.isEmpty()) {
                      if (DEBUG) {
                        Slog.d(
                            "TrustManagerService",
                            "refreshAgentList: skipping "
                                + componentName.flattenToShortString()
                                + " u"
                                + userInfo.id
                                + ": not allowed by DPM");
                      }
                      lockPatternUtils = lockPatternUtils2;
                      it2 = it4;
                      it3 = it;
                    }
                  } else {
                    it = it3;
                  }
                  AgentInfo agentInfo = new AgentInfo();
                  agentInfo.component = componentName;
                  agentInfo.userId = userInfo.id;
                  if (!this.mActiveAgents.contains(agentInfo)) {
                    agentInfo.label = resolveInfo.loadLabel(packageManager);
                    agentInfo.icon = resolveInfo.loadIcon(packageManager);
                    agentInfo.settings = getSettingsAttrs(packageManager, resolveInfo);
                  } else {
                    agentInfo =
                        (AgentInfo)
                            this.mActiveAgents.valueAt(this.mActiveAgents.indexOf(agentInfo));
                  }
                  SettingsAttrs settingsAttrs = agentInfo.settings;
                  boolean z2 =
                      settingsAttrs != null
                          && resolveInfo.serviceInfo.directBootAware
                          && settingsAttrs.canUnlockProfile;
                  if (z2 && DEBUG) {
                    Slog.d(
                        "TrustManagerService",
                        "refreshAgentList: trustagent "
                            + componentName
                            + "of user "
                            + userInfo.id
                            + "can unlock user profile.");
                  }
                  if (!this.mUserManager.isUserUnlockingOrUnlocked(userInfo.id) && !z2) {
                    if (DEBUG) {
                      Slog.d(
                          "TrustManagerService",
                          "refreshAgentList: skipping user "
                              + userInfo.id
                              + "'s trust agent "
                              + componentName
                              + ": FBE still locked and  the agent cannot unlock user profile.");
                    }
                  } else {
                    if (!this.mStrongAuthTracker.canAgentsRunForUser(userInfo.id)
                        && (strongAuthForUser =
                                this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id))
                            != 8) {
                      if (strongAuthForUser != 1 || !z2) {
                        if (DEBUG) {
                          Slog.d(
                              "TrustManagerService",
                              "refreshAgentList: skipping user "
                                  + userInfo.id
                                  + ": prevented by StrongAuthTracker = 0x"
                                  + Integer.toHexString(
                                      this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id)));
                        }
                      }
                    }
                    if (agentInfo.agent == null) {
                      agentInfo.agent =
                          new TrustAgentWrapper(
                              this.mContext,
                              this,
                              new Intent().setComponent(componentName),
                              userInfo.getUserHandle());
                    }
                    if (!this.mActiveAgents.contains(agentInfo)) {
                      this.mActiveAgents.add(agentInfo);
                    } else {
                      arraySet.remove(agentInfo);
                    }
                    lockPatternUtils = lockPatternUtils2;
                    it2 = it4;
                    it3 = it;
                  }
                  lockPatternUtils = lockPatternUtils2;
                  it2 = it4;
                  it3 = it;
                }
              }
            }
          }
        }
      }
      boolean z3 = false;
      for (int i3 = 0; i3 < arraySet.size(); i3++) {
        AgentInfo agentInfo2 = (AgentInfo) arraySet.valueAt(i3);
        if (i2 == -1 || i2 == agentInfo2.userId) {
          if (agentInfo2.agent.isManagingTrust()) {
            z3 = true;
          }
          agentInfo2.agent.destroy();
          this.mActiveAgents.remove(agentInfo2);
        }
      }
      if (z3) {
        if (i2 == -1) {
          updateTrustAll();
        } else {
          updateTrust(i2, 0);
        }
      }
    }
  }

  public boolean isDeviceLockedInner(int i) {
    boolean z;
    synchronized (this.mDeviceLockedForUser) {
      z = this.mDeviceLockedForUser.get(i, true);
    }
    return z;
  }

  public final void maybeActiveUnlockRunningChanged(int i) {
    boolean z = this.mLastActiveUnlockRunningState.get(i);
    boolean aggregateIsActiveUnlockRunning = aggregateIsActiveUnlockRunning(i);
    if (z == aggregateIsActiveUnlockRunning) {
      return;
    }
    this.mLastActiveUnlockRunningState.put(i, aggregateIsActiveUnlockRunning);
    for (int i2 = 0; i2 < this.mTrustListeners.size(); i2++) {
      notifyListenerIsActiveUnlockRunning(
          (ITrustListener) this.mTrustListeners.get(i2), aggregateIsActiveUnlockRunning, i);
    }
  }

  public final void refreshDeviceLockedForUser(int i) {
    refreshDeviceLockedForUser(i, -10000);
  }

  public final void refreshDeviceLockedForUser(int i, int i2) {
    List list;
    boolean z;
    boolean z2;
    boolean z3;
    boolean z4;
    if (i != -1 && i < 0) {
      Log.e(
          "TrustManagerService",
          "refreshDeviceLockedForUser(userId="
              + i
              + "): Invalid user handle, must be USER_ALL or a specific user.",
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
        if (!userInfo.supportsSwitchToByUser()) {
          if (userInfo.isManagedProfile() && !isSecure) {
            setDeviceLockedForUser(i4, false);
          }
        } else {
          if (this.mCurrentUser == i4) {
            if (ENABLE_ACTIVE_UNLOCK_FLAG) {
              synchronized (this.mUserTrustState) {
                z2 = this.mUserTrustState.get(i4) == TrustState.TRUSTED;
              }
            } else {
              synchronized (this.mUserIsTrusted) {
                z2 = this.mUserIsTrusted.get(i4);
              }
            }
            synchronized (this.mUsersUnlockedByBiometric) {
              z = this.mUsersUnlockedByBiometric.get(i4, false);
            }
            try {
              z4 = windowManagerService.isKeyguardLocked();
            } catch (RemoteException e) {
              Log.w("TrustManagerService", "Unable to check keyguard lock state", e);
              z4 = true;
            }
            z3 = i2 == i4;
          } else {
            z = false;
            z2 = false;
            z3 = false;
            z4 = true;
          }
          boolean z5 = isSecure && z4 && !z2 && !z;
          if (!z5 || !z3) {
            setDeviceLockedForUser(i4, z5);
            if (z5) {
              if (getPersonaManagerInternal() != null) {
                getPersonaManagerInternal().onDeviceLockedChanged(i4);
              } else {
                Log.e(
                    "TrustManagerService", "onDeviceLockedChanged() - Service is not yet ready...");
              }
            }
          }
        }
      }
    }
  }

  public final void setDeviceLockedForUser(int i, boolean z) {
    int i2;
    boolean z2;
    synchronized (this.mDeviceLockedForUser) {
      z2 = isDeviceLockedInner(i) != z;
      this.mDeviceLockedForUser.put(i, z);
      if (z2) {
        Log.d("TrustManagerService", "User " + i + ", setDeviceLockedForUser " + z);
      }
    }
    if (!z2 && DualDarManager.isOnDeviceOwner(i)) {
      Log.d("TrustManagerService", "setDeviceLockedForUser - locked state is not changed though");
      handleDualDARDeviceLockedChangedForUser(i, z);
    }
    if (z2 || this.mIsUnlockedByFP) {
      handleDualDARDeviceLockedChangedForUser(i, z);
      dispatchDeviceLocked(i, z);
      Authorization.onLockScreenEvent(z, i, (byte[]) null, getBiometricSids(i));
      for (int i3 : this.mUserManager.getEnabledProfileIds(i)) {
        if (this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(i3)) {
          Authorization.onLockScreenEvent(z, i3, (byte[]) null, getBiometricSids(i3));
        }
      }
    }
  }

  public final void dispatchDeviceLocked(int i, boolean z) {
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i) {
        if (z) {
          agentInfo.agent.onDeviceLocked();
        } else {
          agentInfo.agent.onDeviceUnlocked();
        }
      }
    }
  }

  /* renamed from: dispatchEscrowTokenActivatedLocked, reason: merged with bridge method [inline-methods] */
  public final void lambda$addEscrowToken$0(long j, int i) {
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i) {
        agentInfo.agent.onEscrowTokenActivated(j, i);
      }
    }
  }

  public void updateDevicePolicyFeatures() {
    boolean z = false;
    for (int i = 0; i < this.mActiveAgents.size(); i++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i);
      if (agentInfo.agent.isConnected()) {
        agentInfo.agent.updateDevicePolicyFeatures();
        z = true;
      }
    }
    if (z) {
      this.mArchive.logDevicePolicyChanged();
    }
  }

  public final void removeAgentsOfPackage(String str) {
    boolean z = false;
    for (int size = this.mActiveAgents.size() - 1; size >= 0; size--) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(size);
      if (str.equals(agentInfo.component.getPackageName())) {
        Log.i(
            "TrustManagerService", "Resetting agent " + agentInfo.component.flattenToShortString());
        if (agentInfo.agent.isManagingTrust()) {
          z = true;
        }
        agentInfo.agent.destroy();
        this.mActiveAgents.removeAt(size);
      }
    }
    if (z) {
      updateTrustAll();
    }
  }

  public void resetAgent(ComponentName componentName, int i) {
    boolean z = false;
    for (int size = this.mActiveAgents.size() - 1; size >= 0; size--) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(size);
      if (componentName.equals(agentInfo.component) && i == agentInfo.userId) {
        Log.i(
            "TrustManagerService", "Resetting agent " + agentInfo.component.flattenToShortString());
        if (agentInfo.agent.isManagingTrust()) {
          z = true;
        }
        agentInfo.agent.destroy();
        this.mActiveAgents.removeAt(size);
      }
    }
    if (z) {
      updateTrust(i, 0);
    }
    refreshAgentList(i);
  }

  /* JADX WARN: Code restructure failed: missing block: B:44:0x0096, code lost:

     if (r1 == null) goto L59;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x0089, code lost:

     r1.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x0090, code lost:

     if (r1 == null) goto L59;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x0087, code lost:

     if (r1 == null) goto L59;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final SettingsAttrs getSettingsAttrs(
      PackageManager packageManager, ResolveInfo resolveInfo) {
    ServiceInfo serviceInfo;
    XmlResourceParser xmlResourceParser;
    String str;
    int next;
    XmlResourceParser xmlResourceParser2 = null;
    if (resolveInfo == null
        || (serviceInfo = resolveInfo.serviceInfo) == null
        || serviceInfo.metaData == null) {
      return null;
    }
    boolean z = false;
    try {
      xmlResourceParser =
          serviceInfo.loadXmlMetaData(packageManager, "android.service.trust.trustagent");
      try {
        try {
        } catch (Throwable th) {
          th = th;
          xmlResourceParser2 = xmlResourceParser;
          if (xmlResourceParser2 != null) {
            xmlResourceParser2.close();
          }
          throw th;
        }
      } catch (PackageManager.NameNotFoundException e) {
        e = e;
        str = null;
      } catch (IOException e2) {
        e = e2;
        str = null;
      } catch (XmlPullParserException e3) {
        e = e3;
        str = null;
      }
    } catch (PackageManager.NameNotFoundException e4) {
      e = e4;
      xmlResourceParser = null;
      str = null;
    } catch (IOException e5) {
      e = e5;
      xmlResourceParser = null;
      str = null;
    } catch (XmlPullParserException e6) {
      e = e6;
      xmlResourceParser = null;
      str = null;
    } catch (Throwable th2) {
      th = th2;
    }
    if (xmlResourceParser == null) {
      Slog.w("TrustManagerService", "Can't find android.service.trust.trustagent meta-data");
      if (xmlResourceParser != null) {
        xmlResourceParser.close();
      }
      return null;
    }
    Resources resourcesForApplication =
        packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo);
    AttributeSet asAttributeSet = Xml.asAttributeSet(xmlResourceParser);
    do {
      next = xmlResourceParser.next();
      if (next == 1) {
        break;
      }
    } while (next != 2);
    if (!"trust-agent".equals(xmlResourceParser.getName())) {
      Slog.w("TrustManagerService", "Meta-data does not start with trust-agent tag");
      xmlResourceParser.close();
      return null;
    }
    TypedArray obtainAttributes =
        resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.TrustAgent);
    str = obtainAttributes.getString(2);
    try {
      z =
          asAttributeSet.getAttributeBooleanValue(
              "http://schemas.android.com/apk/prv/res/android", "unlockProfile", false);
      obtainAttributes.recycle();
      xmlResourceParser.close();
      e = null;
    } catch (PackageManager.NameNotFoundException e7) {
      e = e7;
    } catch (IOException e8) {
      e = e8;
    } catch (XmlPullParserException e9) {
      e = e9;
    }
    if (e != null) {
      Slog.w("TrustManagerService", "Error parsing : " + resolveInfo.serviceInfo.packageName, e);
      return null;
    }
    if (str == null) {
      return null;
    }
    if (str.indexOf(47) < 0) {
      str = resolveInfo.serviceInfo.packageName + "/" + str;
    }
    return new SettingsAttrs(ComponentName.unflattenFromString(str), z);
  }

  public final ComponentName getComponentName(ResolveInfo resolveInfo) {
    if (resolveInfo == null || resolveInfo.serviceInfo == null) {
      return null;
    }
    ServiceInfo serviceInfo = resolveInfo.serviceInfo;
    return new ComponentName(serviceInfo.packageName, serviceInfo.name);
  }

  public final void maybeEnableFactoryTrustAgents(int i) {
    if (Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(), "trust_agents_initialized", 0, i)
        != 0) {
      return;
    }
    List<ResolveInfo> resolveAllowedTrustAgents =
        resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
    ComponentName defaultFactoryTrustAgent = getDefaultFactoryTrustAgent(this.mContext);
    boolean z = defaultFactoryTrustAgent != null;
    ArraySet arraySet = new ArraySet();
    if (z) {
      arraySet.add(defaultFactoryTrustAgent);
      Log.i(
          "TrustManagerService",
          "Enabling " + defaultFactoryTrustAgent + " because it is a default agent.");
    } else {
      for (ResolveInfo resolveInfo : resolveAllowedTrustAgents) {
        ComponentName componentName = getComponentName(resolveInfo);
        if (isSystemTrustAgent(resolveInfo)) {
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
    enableNewAgents(arraySet, i);
    Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(), "trust_agents_initialized", 1, i);
  }

  public final void checkNewAgents() {
    Iterator it = this.mUserManager.getAliveUsers().iterator();
    while (it.hasNext()) {
      checkNewAgentsForUser(((UserInfo) it.next()).id);
    }
  }

  public final void checkNewAgentsForUser(int i) {
    if (Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(), "known_trust_agents_initialized", 0, i)
        == 0) {
      initializeKnownAgents(i);
      return;
    }
    List knownTrustAgents = this.mLockPatternUtils.getKnownTrustAgents(i);
    List<ResolveInfo> resolveAllowedTrustAgents =
        resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
    ArraySet arraySet = new ArraySet(resolveAllowedTrustAgents.size());
    ArraySet arraySet2 = new ArraySet(resolveAllowedTrustAgents.size());
    for (ResolveInfo resolveInfo : resolveAllowedTrustAgents) {
      ComponentName componentName = getComponentName(resolveInfo);
      if (!knownTrustAgents.contains(componentName)) {
        arraySet.add(componentName);
        if (isSystemTrustAgent(resolveInfo)) {
          arraySet2.add(componentName);
        }
      }
    }
    if (arraySet.isEmpty()) {
      return;
    }
    ArraySet arraySet3 = new ArraySet(knownTrustAgents);
    arraySet3.addAll(arraySet);
    this.mLockPatternUtils.setKnownTrustAgents(arraySet3, i);
    if (getDefaultFactoryTrustAgent(this.mContext) != null) {
      return;
    }
    enableNewAgents(arraySet2, i);
  }

  public final void enableNewAgents(Collection collection, int i) {
    if (collection.isEmpty()) {
      return;
    }
    ArraySet arraySet = new ArraySet(collection);
    arraySet.addAll(this.mLockPatternUtils.getEnabledTrustAgents(i));
    this.mLockPatternUtils.setEnabledTrustAgents(arraySet, i);
  }

  public final void initializeKnownAgents(int i) {
    List<ResolveInfo> resolveAllowedTrustAgents =
        resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
    ArraySet arraySet = new ArraySet(resolveAllowedTrustAgents.size());
    for (ResolveInfo resolveInfo : resolveAllowedTrustAgents) {
      if (!getComponentName(resolveInfo)
              .flattenToShortString()
              .contains("com.google.android.gms/.auth.trustagent.GoogleTrustAgent")
          || !isChina) {
        arraySet.add(getComponentName(resolveInfo));
      }
    }
    this.mLockPatternUtils.setKnownTrustAgents(arraySet, i);
    Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(), "known_trust_agents_initialized", 1, i);
  }

  public static ComponentName getDefaultFactoryTrustAgent(Context context) {
    String string = context.getResources().getString(android.R.string.default_audio_route_id);
    if (TextUtils.isEmpty(string)) {
      return null;
    }
    return ComponentName.unflattenFromString(string);
  }

  public final List resolveAllowedTrustAgents(PackageManager packageManager, int i) {
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

  public static boolean isSystemTrustAgent(ResolveInfo resolveInfo) {
    return (resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0;
  }

  public final boolean aggregateIsTrusted(int i) {
    if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
      return false;
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i && agentInfo.agent.isTrusted()) {
        return true;
      }
    }
    return false;
  }

  public final boolean aggregateIsTrustable(int i) {
    if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
      return false;
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i && agentInfo.agent.isTrustable()) {
        return true;
      }
    }
    return false;
  }

  public final boolean aggregateIsActiveUnlockRunning(int i) {
    if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
      return false;
    }
    synchronized (this.mUserTrustState) {
      TrustState trustState = (TrustState) this.mUserTrustState.get(i);
      if (trustState != TrustState.TRUSTED && trustState != TrustState.TRUSTABLE) {
        return false;
      }
      for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
        AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
        if (agentInfo.userId == i && agentInfo.agent.isTrustableOrWaitingForDowngrade()) {
          return true;
        }
      }
      return false;
    }
  }

  public final void dispatchTrustableDowngrade() {
    for (int i = 0; i < this.mActiveAgents.size(); i++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i);
      if (agentInfo.userId == this.mCurrentUser) {
        agentInfo.agent.downgradeToTrustable();
      }
    }
  }

  public final List getTrustGrantedMessages(int i) {
    if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
      return new ArrayList();
    }
    ArrayList arrayList = new ArrayList();
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i
          && agentInfo.agent.isTrusted()
          && agentInfo.agent.shouldDisplayTrustGrantedMessage()
          && agentInfo.agent.getMessage() != null) {
        arrayList.add(agentInfo.agent.getMessage().toString());
      }
    }
    return arrayList;
  }

  public final boolean aggregateIsTrustManaged(int i) {
    if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
      return false;
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i && agentInfo.agent.isTrustableOrWaitingForDowngrade()) {
        return true;
      }
    }
    return false;
  }

  public final void dispatchUnlockAttempt(boolean z, int i) {
    if (z) {
      this.mStrongAuthTracker.allowTrustFromUnlock(i);
      updateTrust(i, 0, true, null);
      this.mHandler.obtainMessage(17, Integer.valueOf(i)).sendToTarget();
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i) {
        agentInfo.agent.onUnlockAttempt(z);
      }
    }
  }

  public final void dispatchUserRequestedUnlock(int i, boolean z) {
    if (DEBUG) {
      Slog.d(
          "TrustManagerService",
          "dispatchUserRequestedUnlock(user=" + i + ", dismissKeyguard=" + z + ")");
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i) {
        agentInfo.agent.onUserRequestedUnlock(z);
      }
    }
  }

  public final void dispatchUserMayRequestUnlock(int i) {
    if (DEBUG) {
      Slog.d("TrustManagerService", "dispatchUserMayRequestUnlock(user=" + i + ")");
    }
    for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i2);
      if (agentInfo.userId == i) {
        agentInfo.agent.onUserMayRequestUnlock();
      }
    }
  }

  public final void dispatchUnlockLockout(int i, int i2) {
    for (int i3 = 0; i3 < this.mActiveAgents.size(); i3++) {
      AgentInfo agentInfo = (AgentInfo) this.mActiveAgents.valueAt(i3);
      if (agentInfo.userId == i2) {
        agentInfo.agent.onUnlockLockout(i);
      }
    }
  }

  public final void notifyListenerIsActiveUnlockRunningInitialState(ITrustListener iTrustListener) {
    int size = this.mLastActiveUnlockRunningState.size();
    for (int i = 0; i < size; i++) {
      int keyAt = this.mLastActiveUnlockRunningState.keyAt(i);
      notifyListenerIsActiveUnlockRunning(
          iTrustListener, aggregateIsActiveUnlockRunning(keyAt), keyAt);
    }
  }

  public final void notifyListenerIsActiveUnlockRunning(
      ITrustListener iTrustListener, boolean z, int i) {
    try {
      iTrustListener.onIsActiveUnlockRunningChanged(z, i);
    } catch (DeadObjectException unused) {
      Slog.d(
          "TrustManagerService",
          "TrustListener dead while trying to notify Active Unlock running state");
    } catch (RemoteException e) {
      Slog.e("TrustManagerService", "Exception while notifying TrustListener.", e);
    }
  }

  public final void addListener(ITrustListener iTrustListener) {
    for (int i = 0; i < this.mTrustListeners.size(); i++) {
      if (((ITrustListener) this.mTrustListeners.get(i)).asBinder() == iTrustListener.asBinder()) {
        return;
      }
    }
    this.mTrustListeners.add(iTrustListener);
    notifyListenerIsActiveUnlockRunningInitialState(iTrustListener);
    updateTrustAll();
  }

  public final void removeListener(ITrustListener iTrustListener) {
    for (int i = 0; i < this.mTrustListeners.size(); i++) {
      if (((ITrustListener) this.mTrustListeners.get(i)).asBinder() == iTrustListener.asBinder()) {
        this.mTrustListeners.remove(i);
        return;
      }
    }
  }

  public final void dispatchOnTrustChanged(boolean z, boolean z2, int i, int i2, List list) {
    if (DEBUG) {
      Log.i(
          "TrustManagerService",
          "onTrustChanged(" + z + ", " + z2 + ", " + i + ", 0x" + Integer.toHexString(i2) + ")");
    }
    int i3 = 0;
    if (!z) {
      i2 = 0;
    }
    while (i3 < this.mTrustListeners.size()) {
      try {
        ((ITrustListener) this.mTrustListeners.get(i3)).onTrustChanged(z, z2, i, i2, list);
      } catch (DeadObjectException unused) {
        Slog.d("TrustManagerService", "Removing dead TrustListener.");
        this.mTrustListeners.remove(i3);
        i3--;
      } catch (RemoteException e) {
        Slog.e("TrustManagerService", "Exception while notifying TrustListener.", e);
      }
      i3++;
    }
  }

  public final void dispatchOnEnabledTrustAgentsChanged(int i) {
    if (DEBUG) {
      Log.i("TrustManagerService", "onEnabledTrustAgentsChanged(" + i + ")");
    }
    int i2 = 0;
    while (i2 < this.mTrustListeners.size()) {
      try {
        ((ITrustListener) this.mTrustListeners.get(i2)).onEnabledTrustAgentsChanged(i);
      } catch (DeadObjectException unused) {
        Slog.d("TrustManagerService", "Removing dead TrustListener.");
        this.mTrustListeners.remove(i2);
        i2--;
      } catch (RemoteException e) {
        Slog.e("TrustManagerService", "Exception while notifying TrustListener.", e);
      }
      i2++;
    }
  }

  public final void dispatchOnTrustManagedChanged(boolean z, int i) {
    if (DEBUG) {
      Log.i("TrustManagerService", "onTrustManagedChanged(" + z + ", " + i + ")");
    }
    int i2 = 0;
    while (i2 < this.mTrustListeners.size()) {
      try {
        ((ITrustListener) this.mTrustListeners.get(i2)).onTrustManagedChanged(z, i);
      } catch (DeadObjectException unused) {
        Slog.d("TrustManagerService", "Removing dead TrustListener.");
        this.mTrustListeners.remove(i2);
        i2--;
      } catch (RemoteException e) {
        Slog.e("TrustManagerService", "Exception while notifying TrustListener.", e);
      }
      i2++;
    }
  }

  public final void dispatchOnTrustError(CharSequence charSequence) {
    if (DEBUG) {
      Log.i("TrustManagerService", "onTrustError(" + ((Object) charSequence) + ")");
    }
    int i = 0;
    while (i < this.mTrustListeners.size()) {
      try {
        ((ITrustListener) this.mTrustListeners.get(i)).onTrustError(charSequence);
      } catch (DeadObjectException unused) {
        Slog.d("TrustManagerService", "Removing dead TrustListener.");
        this.mTrustListeners.remove(i);
        i--;
      } catch (RemoteException e) {
        Slog.e("TrustManagerService", "Exception while notifying TrustListener.", e);
      }
      i++;
    }
  }

  public final long[] getBiometricSids(int i) {
    BiometricManager biometricManager =
        (BiometricManager) this.mContext.getSystemService(BiometricManager.class);
    if (biometricManager == null) {
      return null;
    }
    return biometricManager.getAuthenticatorIds(i);
  }

  @Override // com.android.server.SystemService
  public void onUserStarting(SystemService.TargetUser targetUser) {
    this.mHandler.obtainMessage(7, targetUser.getUserIdentifier(), 0, null).sendToTarget();
  }

  @Override // com.android.server.SystemService
  public void onUserStopped(SystemService.TargetUser targetUser) {
    this.mHandler.obtainMessage(8, targetUser.getUserIdentifier(), 0, null).sendToTarget();
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    this.mHandler.obtainMessage(9, targetUser2.getUserIdentifier(), 0, null).sendToTarget();
  }

  @Override // com.android.server.SystemService
  public void onUserUnlocking(SystemService.TargetUser targetUser) {
    this.mHandler.obtainMessage(11, targetUser.getUserIdentifier(), 0, null).sendToTarget();
  }

  @Override // com.android.server.SystemService
  public void onUserStopping(SystemService.TargetUser targetUser) {
    this.mHandler.obtainMessage(12, targetUser.getUserIdentifier(), 0, null).sendToTarget();
  }

  /* renamed from: com.android.server.trust.TrustManagerService$1 */
  public class ITrustManagerStubC26951 extends ITrustManager.Stub {
    public static /* synthetic */ void lambda$reportKeyguardShowingChanged$0() {}

    public final String dumpBool(boolean z) {
      return z ? "1" : "0";
    }

    public ITrustManagerStubC26951() {}

    public void reportUnlockAttempt(boolean z, int i) {
      enforceReportPermission();
      TrustManagerService.this.mHandler.obtainMessage(3, z ? 1 : 0, i).sendToTarget();
    }

    public void reportUserRequestedUnlock(int i, boolean z) {
      enforceReportPermission();
      TrustManagerService.this.mHandler.obtainMessage(16, i, z ? 1 : 0).sendToTarget();
    }

    public void reportUserMayRequestUnlock(int i) {
      enforceReportPermission();
      TrustManagerService.this.mHandler.obtainMessage(18, i, 0).sendToTarget();
    }

    public void reportUnlockLockout(int i, int i2) {
      enforceReportPermission();
      TrustManagerService.this.mHandler.obtainMessage(13, i, i2).sendToTarget();
    }

    public void reportEnabledTrustAgentsChanged(int i) {
      enforceReportPermission();
      TrustManagerService.this.mHandler.obtainMessage(4, i, 0).sendToTarget();
    }

    public void reportKeyguardShowingChanged() {
      enforceReportPermission();
      TrustManagerService.this.mHandler.removeMessages(6);
      TrustManagerService.this.mHandler.sendEmptyMessage(6);
      TrustManagerService.this.mHandler.runWithScissors(
          new Runnable() { // from class:
                           // com.android.server.trust.TrustManagerService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              TrustManagerService.ITrustManagerStubC26951.lambda$reportKeyguardShowingChanged$0();
            }
          },
          0L);
    }

    public void registerTrustListener(ITrustListener iTrustListener) {
      enforceListenerPermission();
      TrustManagerService.this.mHandler.obtainMessage(1, iTrustListener).sendToTarget();
    }

    public void unregisterTrustListener(ITrustListener iTrustListener) {
      enforceListenerPermission();
      TrustManagerService.this.mHandler.obtainMessage(2, iTrustListener).sendToTarget();
    }

    public final boolean isAppOrDisplayOnAnyVirtualDevice(int i, int i2) {
      if (UserHandle.isCore(i)) {
        return false;
      }
      if (TrustManagerService.this.mVirtualDeviceManager == null) {
        TrustManagerService.this.mVirtualDeviceManager =
            (VirtualDeviceManagerInternal)
                LocalServices.getService(VirtualDeviceManagerInternal.class);
        if (TrustManagerService.this.mVirtualDeviceManager == null) {
          return false;
        }
      }
      if (i2 == -1) {
        if (TrustManagerService.this.mVirtualDeviceManager.isAppRunningOnAnyVirtualDevice(i)) {
          return true;
        }
      } else if (i2 != 0
          && TrustManagerService.this.mVirtualDeviceManager.isDisplayOwnedByAnyVirtualDevice(i2)) {
        return true;
      }
      return false;
    }

    public boolean isDeviceLocked(int i, int i2) {
      int callingUid = ITrustManager.Stub.getCallingUid();
      if (isAppOrDisplayOnAnyVirtualDevice(callingUid, i2)) {
        return false;
      }
      int handleIncomingUser =
          ActivityManager.handleIncomingUser(
              ITrustManager.Stub.getCallingPid(),
              callingUid,
              i,
              false,
              true,
              "isDeviceLocked",
              null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
            handleIncomingUser)) {
          handleIncomingUser = TrustManagerService.this.resolveProfileParent(handleIncomingUser);
        }
        return TrustManagerService.this.isDeviceLockedInner(handleIncomingUser);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isDeviceSecure(int i, int i2) {
      int callingUid = ITrustManager.Stub.getCallingUid();
      if (isAppOrDisplayOnAnyVirtualDevice(callingUid, i2)) {
        return false;
      }
      int handleIncomingUser =
          ActivityManager.handleIncomingUser(
              ITrustManager.Stub.getCallingPid(),
              callingUid,
              i,
              false,
              true,
              "isDeviceSecure",
              null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
            handleIncomingUser)) {
          handleIncomingUser = TrustManagerService.this.resolveProfileParent(handleIncomingUser);
        }
        return TrustManagerService.this.mLockPatternUtils.isSecure(handleIncomingUser);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final void enforceReportPermission() {
      TrustManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "reporting trust events");
    }

    public final void enforceListenerPermission() {
      TrustManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.TRUST_LISTENER", "register trust listener");
    }

    public void dump(
        FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          TrustManagerService.this.mContext, "TrustManagerService", printWriter)) {
        if (TrustManagerService.this.isSafeMode()) {
          printWriter.println("disabled because the system is in safe mode.");
        } else if (!TrustManagerService.this.mTrustAgentsCanRun) {
          printWriter.println("disabled because the third-party apps can't run yet.");
        } else {
          final List aliveUsers = TrustManagerService.this.mUserManager.getAliveUsers();
          TrustManagerService.this.mHandler.runWithScissors(
              new Runnable() { // from class: com.android.server.trust.TrustManagerService.1.1
                @Override // java.lang.Runnable
                public void run() {
                  printWriter.println("Trust manager state:");
                  for (UserInfo userInfo : aliveUsers) {
                    ITrustManagerStubC26951 iTrustManagerStubC26951 = ITrustManagerStubC26951.this;
                    iTrustManagerStubC26951.dumpUser(
                        printWriter,
                        userInfo,
                        userInfo.id == TrustManagerService.this.mCurrentUser);
                  }
                }
              },
              1500L);
        }
      }
    }

    public final void dumpUser(PrintWriter printWriter, UserInfo userInfo, boolean z) {
      printWriter.printf(
          " User \"%s\" (id=%d, flags=%#x)",
          userInfo.name, Integer.valueOf(userInfo.id), Integer.valueOf(userInfo.flags));
      if (!userInfo.supportsSwitchToByUser()) {
        printWriter.println("(managed profile)");
        printWriter.println("   disabled because switching to this user is not possible.");
        return;
      }
      if (z) {
        printWriter.print(" (current)");
      }
      printWriter.print(
          ": trusted=" + dumpBool(TrustManagerService.this.aggregateIsTrusted(userInfo.id)));
      printWriter.print(
          ", trustManaged="
              + dumpBool(TrustManagerService.this.aggregateIsTrustManaged(userInfo.id)));
      printWriter.print(
          ", deviceLocked=" + dumpBool(TrustManagerService.this.isDeviceLockedInner(userInfo.id)));
      printWriter.print(
          ", isActiveUnlockRunning="
              + dumpBool(TrustManagerService.this.aggregateIsActiveUnlockRunning(userInfo.id)));
      printWriter.print(
          ", strongAuthRequired="
              + dumpHex(
                  TrustManagerService.this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id)));
      printWriter.println();
      printWriter.println("   Enabled agents:");
      ArraySet arraySet = new ArraySet();
      Iterator it = TrustManagerService.this.mActiveAgents.iterator();
      boolean z2 = false;
      while (true) {
        boolean z3 = z2;
        while (it.hasNext()) {
          AgentInfo agentInfo = (AgentInfo) it.next();
          if (agentInfo.userId == userInfo.id) {
            boolean isTrusted = agentInfo.agent.isTrusted();
            printWriter.print("    ");
            printWriter.println(agentInfo.component.flattenToShortString());
            printWriter.print("     bound=" + dumpBool(agentInfo.agent.isBound()));
            printWriter.print(", connected=" + dumpBool(agentInfo.agent.isConnected()));
            printWriter.print(", managingTrust=" + dumpBool(agentInfo.agent.isManagingTrust()));
            printWriter.print(", trusted=" + dumpBool(isTrusted));
            printWriter.println();
            if (isTrusted) {
              printWriter.println(
                  "      message=\"" + ((Object) agentInfo.agent.getMessage()) + "\"");
            }
            if (!agentInfo.agent.isConnected()) {
              printWriter.println(
                  "      restartScheduledAt="
                      + TrustArchive.formatDuration(
                          agentInfo.agent.getScheduledRestartUptimeMillis()
                              - SystemClock.uptimeMillis()));
            }
            if (!arraySet.add(TrustArchive.getSimpleName(agentInfo.component))) {
              break;
            }
          }
        }
        printWriter.println("   Events:");
        TrustManagerService.this.mArchive.dump(printWriter, 50, userInfo.id, "    ", z3);
        printWriter.println();
        return;
        z2 = true;
      }
    }

    public final String dumpHex(int i) {
      return "0x" + Integer.toHexString(i);
    }

    public void setDeviceLockedForUser(int i, boolean z) {
      enforceReportPermission();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (DualDarManager.isOnDeviceOwner(i)) {
          synchronized (TrustManagerService.this.mDeviceLockedForUser) {
            TrustManagerService.this.mDeviceLockedForUser.put(i, z);
          }
          DDLog.m33d(
              "TrustManagerService",
              "setDeviceLocked for DualDAR DO. locked state : " + z,
              new Object[0]);
          TrustManagerService.this.handleDualDARDeviceLockedChangedForUser(i, z);
        } else if (i == 0 && !z) {
          DDLog.m33d(
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
          Authorization.onLockScreenEvent(
              z, i, (byte[]) null, TrustManagerService.this.getBiometricSids(i));
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
            TrustManagerService.this.getPersonaManagerInternal().onDeviceLockedChanged(i);
          } else {
            Log.e("TrustManagerService", "onDeviceLockedChanged() - Service is not yet ready...");
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isTrustUsuallyManaged(int i) {
      super.isTrustUsuallyManaged_enforcePermission();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return TrustManagerService.this.isTrustUsuallyManagedInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) {
      enforceReportPermission();
      synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
        boolean z = true;
        TrustManagerService.this.mUsersUnlockedByBiometric.put(i, true);
        TrustManagerService trustManagerService = TrustManagerService.this;
        if (biometricSourceType != BiometricSourceType.FINGERPRINT) {
          z = false;
        }
        trustManagerService.mIsUnlockedByFP = z;
      }
      TrustManagerService.this
          .mHandler
          .obtainMessage(
              14,
              i,
              TrustManagerService.this.mSettingsObserver.getTrustAgentsNonrenewableTrust() ? 1 : 0)
          .sendToTarget();
      TrustManagerService.this.mHandler.obtainMessage(17, Integer.valueOf(i)).sendToTarget();
    }

    public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) {
      enforceReportPermission();
      synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
        TrustManagerService.this.mUsersUnlockedByBiometric.clear();
        TrustManagerService.this.mIsUnlockedByFP = false;
      }
      Message obtainMessage = TrustManagerService.this.mHandler.obtainMessage(14, -1, 0);
      if (i >= 0) {
        Bundle bundle = new Bundle();
        bundle.putInt("except", i);
        obtainMessage.setData(bundle);
      }
      obtainMessage.sendToTarget();
    }

    public boolean isActiveUnlockRunning(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return TrustManagerService.this.aggregateIsActiveUnlockRunning(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final boolean isTrustUsuallyManagedInternal(int i) {
    synchronized (this.mTrustUsuallyManagedForUser) {
      int indexOfKey = this.mTrustUsuallyManagedForUser.indexOfKey(i);
      if (indexOfKey >= 0) {
        return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey);
      }
      boolean isTrustUsuallyManaged = this.mLockPatternUtils.isTrustUsuallyManaged(i);
      synchronized (this.mTrustUsuallyManagedForUser) {
        int indexOfKey2 = this.mTrustUsuallyManagedForUser.indexOfKey(i);
        if (indexOfKey2 >= 0) {
          return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey2);
        }
        this.mTrustUsuallyManagedForUser.put(i, isTrustUsuallyManaged);
        return isTrustUsuallyManaged;
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

  public final Handler createHandler(Looper looper) {
    return new Handler(looper) { // from class: com.android.server.trust.TrustManagerService.2
      @Override // android.os.Handler
      public void handleMessage(Message message) {
        SparseBooleanArray clone;
        switch (message.what) {
          case 1:
            TrustManagerService.this.addListener((ITrustListener) message.obj);
            return;
          case 2:
            TrustManagerService.this.removeListener((ITrustListener) message.obj);
            return;
          case 3:
            TrustManagerService.this.dispatchUnlockAttempt(message.arg1 != 0, message.arg2);
            return;
          case 4:
            TrustManagerService.this.refreshAgentList(-1);
            TrustManagerService.this.refreshDeviceLockedForUser(-1);
            TrustManagerService.this.dispatchOnEnabledTrustAgentsChanged(message.arg1);
            return;
          case 5:
          default:
            return;
          case 6:
            TrustManagerService.this.dispatchTrustableDowngrade();
            TrustManagerService trustManagerService = TrustManagerService.this;
            trustManagerService.refreshDeviceLockedForUser(trustManagerService.mCurrentUser);
            return;
          case 7:
          case 8:
          case 11:
            TrustManagerService.this.refreshAgentList(message.arg1);
            if (message.what == 11) {
              TrustManagerService.this.updateDualDARStateAndUnlockIfNeeded(message.arg1);
              return;
            }
            return;
          case 9:
            TrustManagerService.this.mCurrentUser = message.arg1;
            TrustManagerService.this.mSettingsObserver.updateContentObserver();
            TrustManagerService.this.refreshDeviceLockedForUser(-1);
            return;
          case 10:
            synchronized (TrustManagerService.this.mTrustUsuallyManagedForUser) {
              clone = TrustManagerService.this.mTrustUsuallyManagedForUser.clone();
            }
            for (int i = 0; i < clone.size(); i++) {
              int keyAt = clone.keyAt(i);
              boolean valueAt = clone.valueAt(i);
              if (valueAt
                  != TrustManagerService.this.mLockPatternUtils.isTrustUsuallyManaged(keyAt)) {
                TrustManagerService.this.mLockPatternUtils.setTrustUsuallyManaged(valueAt, keyAt);
              }
            }
            return;
          case 12:
            TrustManagerService.this.setDeviceLockedForUser(message.arg1, true);
            return;
          case 13:
            TrustManagerService.this.dispatchUnlockLockout(message.arg1, message.arg2);
            return;
          case 14:
            if (message.arg2 == 1) {
              TrustManagerService.this.updateTrust(message.arg1, 0, true, null);
            }
            TrustManagerService.this.refreshDeviceLockedForUser(
                message.arg1, message.getData().getInt("except", -10000));
            return;
          case 15:
            TrustManagerService.this.handleScheduleTrustTimeout(
                message.arg1 == 1, message.arg2 == 1 ? TimeoutType.TRUSTABLE : TimeoutType.TRUSTED);
            return;
          case 16:
            TrustManagerService.this.dispatchUserRequestedUnlock(message.arg1, message.arg2 != 0);
            return;
          case 17:
            TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
                (TrustableTimeoutAlarmListener)
                    TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(
                        message.arg1);
            if (trustableTimeoutAlarmListener == null
                || !trustableTimeoutAlarmListener.isQueued()) {
              return;
            }
            TrustManagerService.this.refreshTrustableTimers(message.arg1);
            return;
          case 18:
            TrustManagerService.this.dispatchUserMayRequestUnlock(message.arg1);
            return;
        }
      }
    };
  }

  public class SettingsAttrs {
    public boolean canUnlockProfile;
    public ComponentName componentName;

    public SettingsAttrs(ComponentName componentName, boolean z) {
      this.componentName = componentName;
      this.canUnlockProfile = z;
    }
  }

  public class Receiver extends BroadcastReceiver {
    public Receiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      int userId;
      String action = intent.getAction();
      if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
        TrustManagerService.this.refreshAgentList(getSendingUserId());
        TrustManagerService.this.updateDevicePolicyFeatures();
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
      synchronized (TrustManagerService.this.mUserIsTrusted) {
        TrustManagerService.this.mUserIsTrusted.delete(userId);
      }
      synchronized (TrustManagerService.this.mDeviceLockedForUser) {
        TrustManagerService.this.mDeviceLockedForUser.delete(userId);
      }
      synchronized (TrustManagerService.this.mTrustUsuallyManagedForUser) {
        TrustManagerService.this.mTrustUsuallyManagedForUser.delete(userId);
      }
      synchronized (TrustManagerService.this.mUsersUnlockedByBiometric) {
        TrustManagerService.this.mUsersUnlockedByBiometric.delete(userId);
        TrustManagerService.this.mIsUnlockedByFP = false;
      }
      TrustManagerService.this.refreshAgentList(userId);
      TrustManagerService.this.refreshDeviceLockedForUser(userId);
    }

    public final int getUserId(Intent intent) {
      int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -100);
      if (intExtra > 0) {
        return intExtra;
      }
      Log.w("TrustManagerService", "EXTRA_USER_HANDLE missing or invalid, value=" + intExtra);
      return -100;
    }

    public void register(Context context) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
      intentFilter.addAction("android.intent.action.USER_ADDED");
      intentFilter.addAction("android.intent.action.USER_REMOVED");
      intentFilter.addAction("android.intent.action.USER_STARTED");
      context.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, null);
    }
  }

  public class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
    public SparseBooleanArray mStartFromSuccessfulUnlock;

    public StrongAuthTracker(Context context, Looper looper) {
      super(context, looper);
      this.mStartFromSuccessfulUnlock = new SparseBooleanArray();
    }

    public void onStrongAuthRequiredChanged(int i) {
      this.mStartFromSuccessfulUnlock.delete(i);
      if (TrustManagerService.DEBUG) {
        Log.i(
            "TrustManagerService",
            "onStrongAuthRequiredChanged("
                + i
                + ") -> trustAllowed="
                + isTrustAllowedForUser(i)
                + " agentsCanRun="
                + canAgentsRunForUser(i));
      }
      if (!isTrustAllowedForUser(i)) {
        cancelPendingAlarm(
            (TrustTimeoutAlarmListener)
                TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(Integer.valueOf(i)));
        cancelPendingAlarm(
            (TrustTimeoutAlarmListener)
                TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(i));
        cancelPendingAlarm(
            (TrustTimeoutAlarmListener)
                TrustManagerService.this.mIdleTrustableTimeoutAlarmListenerForUser.get(i));
      }
      TrustManagerService.this.refreshAgentList(i);
      TrustManagerService.this.updateTrust(i, 0);
    }

    public final void cancelPendingAlarm(TrustTimeoutAlarmListener trustTimeoutAlarmListener) {
      if (trustTimeoutAlarmListener == null || !trustTimeoutAlarmListener.isQueued()) {
        return;
      }
      trustTimeoutAlarmListener.setQueued(false);
      TrustManagerService.this.mAlarmManager.cancel(trustTimeoutAlarmListener);
    }

    public boolean canAgentsRunForUser(int i) {
      return this.mStartFromSuccessfulUnlock.get(i) || super.isTrustAllowedForUser(i);
    }

    public void allowTrustFromUnlock(int i) {
      if (i < 0) {
        throw new IllegalArgumentException("userId must be a valid user: " + i);
      }
      boolean canAgentsRunForUser = canAgentsRunForUser(i);
      this.mStartFromSuccessfulUnlock.put(i, true);
      if (TrustManagerService.DEBUG) {
        Log.i(
            "TrustManagerService",
            "allowTrustFromUnlock("
                + i
                + ") -> trustAllowed="
                + isTrustAllowedForUser(i)
                + " agentsCanRun="
                + canAgentsRunForUser(i));
      }
      if (canAgentsRunForUser(i) != canAgentsRunForUser) {
        TrustManagerService.this.refreshAgentList(i);
      }
    }
  }

  public abstract class TrustTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
    public boolean mIsQueued = false;
    public final int mUserId;

    public abstract void handleAlarm();

    public TrustTimeoutAlarmListener(int i) {
      this.mUserId = i;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      this.mIsQueued = false;
      handleAlarm();
      if (TrustManagerService.this.mStrongAuthTracker.isTrustAllowedForUser(this.mUserId)) {
        if (TrustManagerService.DEBUG) {
          Slog.d("TrustManagerService", "Revoking all trust because of trust timeout");
        }
        LockPatternUtils lockPatternUtils = TrustManagerService.this.mLockPatternUtils;
        StrongAuthTracker unused = TrustManagerService.this.mStrongAuthTracker;
        lockPatternUtils.requireStrongAuth(256, this.mUserId);
      }
      TrustManagerService.this.maybeLockScreen(this.mUserId);
    }

    public boolean isQueued() {
      return this.mIsQueued;
    }

    public void setQueued(boolean z) {
      this.mIsQueued = z;
    }
  }

  public class TrustedTimeoutAlarmListener extends TrustTimeoutAlarmListener {
    public TrustedTimeoutAlarmListener(int i) {
      super(i);
    }

    @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
    public void handleAlarm() {
      if (TrustManagerService.ENABLE_ACTIVE_UNLOCK_FLAG) {
        TrustableTimeoutAlarmListener trustableTimeoutAlarmListener =
            (TrustableTimeoutAlarmListener)
                TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(this.mUserId);
        if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.isQueued()) {
          synchronized (TrustManagerService.this.mAlarmLock) {
            disableNonrenewableTrustWhileRenewableTrustIsPresent();
          }
        }
      }
    }

    public final void disableNonrenewableTrustWhileRenewableTrustIsPresent() {
      synchronized (TrustManagerService.this.mUserTrustState) {
        if (TrustManagerService.this.mUserTrustState.get(this.mUserId) == TrustState.TRUSTED) {
          TrustManagerService.this.mUserTrustState.put(this.mUserId, TrustState.TRUSTABLE);
          TrustManagerService.this.updateTrust(this.mUserId, 0);
        }
      }
    }
  }

  public class TrustableTimeoutAlarmListener extends TrustTimeoutAlarmListener {
    public TrustableTimeoutAlarmListener(int i) {
      super(i);
    }

    @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
    public void handleAlarm() {
      if (TrustManagerService.ENABLE_ACTIVE_UNLOCK_FLAG) {
        TrustManagerService.this.cancelBothTrustableAlarms(this.mUserId);
        TrustedTimeoutAlarmListener trustedTimeoutAlarmListener =
            (TrustedTimeoutAlarmListener)
                TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(
                    Integer.valueOf(this.mUserId));
        if (trustedTimeoutAlarmListener != null && trustedTimeoutAlarmListener.isQueued()) {
          synchronized (TrustManagerService.this.mAlarmLock) {
            disableRenewableTrustWhileNonrenewableTrustIsPresent();
          }
        }
      }
    }

    public final void disableRenewableTrustWhileNonrenewableTrustIsPresent() {
      Iterator it = TrustManagerService.this.mActiveAgents.iterator();
      while (it.hasNext()) {
        ((AgentInfo) it.next()).agent.setUntrustable();
      }
      TrustManagerService.this.updateTrust(this.mUserId, 0);
    }
  }

  public final PersonaManagerInternal getPersonaManagerInternal() {
    if (this.mPersonaManagerInternal == null) {
      this.mPersonaManagerInternal =
          (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
    }
    return this.mPersonaManagerInternal;
  }

  public final void handleDualDARDeviceLockedChangedForUser(int i, boolean z) {
    int dualDARUser;
    if (PersonaServiceHelper.isDualDAREnabled()) {
      DDLog.m33d(
          "TrustManagerService",
          "handleDualDARDeviceLockedChangedForUser() - userId : " + i + ", locked : " + z,
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

  public final void updateDualDARStateAndUnlockIfNeeded(int i) {
    int dualDARUser;
    DDLog.m33d(
        "TrustManagerService",
        "updateDualDARStateAndUnlockIfNeeded() - userId : " + i,
        new Object[0]);
    if (PersonaServiceHelper.isDualDAREnabled()) {
      if (!((SystemServiceManager) LocalServices.getService(SystemServiceManager.class))
              .isBootCompleted()
          || !StorageManager.isUserKeyUnlocked(i)) {
        if (!DualDarManager.isOnDeviceOwner(i)) {
          return;
        } else {
          DDLog.m33d("TrustManagerService", "Update dualdar state early for DO", new Object[0]);
        }
      }
      if (i != 0 || (dualDARUser = PersonaServiceHelper.getDualDARUser()) == -1) {
        return;
      }
      StateMachine.processEvent(dualDARUser, Event.DEVICE_AUTH_SUCCESS);
      KeyguardManager keyguardManager =
          (KeyguardManager) this.mContext.getSystemService("keyguard");
      DDLog.m33d(
          "TrustManagerService",
          "Trying to unlock DualDAR user (TMS), isSecure? "
              + keyguardManager.isDeviceSecure(dualDARUser),
          new Object[0]);
      if (DualDarManager.isOnDeviceOwner(i)) {
        DDLog.m33d("TrustManagerService", "DualDAR on DO is enabled", new Object[0]);
        if (keyguardManager.isDeviceSecure(dualDARUser)) {
          return;
        }
        if (!DualDarManager.getInstance(this.mContext).isInnerLayerUnlocked(i)
            || SemPersonaManager.isDarDualEncrypted(i)) {
          if (onPassword2Auth(dualDARUser) == VerifyCredentialResponse.OK) {
            DDLog.m33d(
                "TrustManagerService",
                "Inner layer for user " + i + " authenticated",
                new Object[0]);
            return;
          }
          DDLog.m34e(
              "TrustManagerService",
              "Failed in inner layer authentication for user " + i,
              new Object[0]);
          return;
        }
        return;
      }
      if (keyguardManager.isDeviceSecure(dualDARUser)
          || this.mUserManager.isUserUnlockingOrUnlocked(dualDARUser)
          || keyguardManager.isDeviceLocked(0)) {
        return;
      }
      DDLog.m33d(
          "TrustManagerService", "Trying to unlock DualDAR user " + dualDARUser, new Object[0]);
      if (onPassword2Auth(dualDARUser) == VerifyCredentialResponse.OK) {
        try {
          DDLog.m33d(
              "TrustManagerService",
              "fetch outer layer key and unlock DualDAR user " + dualDARUser,
              new Object[0]);
          ActivityManager.getService()
              .unlockUser(
                  dualDARUser,
                  (byte[]) null,
                  fetchOuterLayerKey(dualDARUser),
                  (IProgressListener) null);
          return;
        } catch (Exception e) {
          e.printStackTrace();
          DDLog.m34e(
              "TrustManagerService",
              "Failed to unlock DualDAR user !" + dualDARUser,
              new Object[0]);
          return;
        }
      }
      DDLog.m34e(
          "TrustManagerService", "Default Authentication Failure by DualDAR client", new Object[0]);
    }
  }

  public final byte[] fetchOuterLayerKey(int i) {
    DDLog.m33d("TrustManagerService", "fetchOuterLayerKey()", new Object[0]);
    return DualDARController.getInstance(this.mContext).fetchOuterLayerKey(i);
  }

  public final VerifyCredentialResponse onPassword2Auth(int i) {
    DDLog.m33d("TrustManagerService", "onPassword2Auth()", new Object[0]);
    if (!SemPersonaManager.isDarDualEncryptionEnabled(i)) {
      DDLog.m34e(
          "TrustManagerService",
          "User is not DualDAR eligible. so no need to verify DualDAR Passwords" + i,
          new Object[0]);
      return VerifyCredentialResponse.OK;
    }
    if (!DualDARController.getInstance(this.mContext).onPassword2Auth(i, (byte[]) null)) {
      DDLog.m34e("TrustManagerService", "Authentication Failure by dual dar client", new Object[0]);
      return VerifyCredentialResponse.ERROR;
    }
    DDLog.m33d("TrustManagerService", "onPassword2Auth completed successfully", new Object[0]);
    StateMachine.processEvent(i, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
    return VerifyCredentialResponse.OK;
  }
}
