package com.android.server.locksettings;

import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.IStrongAuthTracker;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.backup.BackupManagerConstants;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes2.dex */
public class LockSettingsStrongAuth {
  public static final boolean DEBUG;
  protected static final String NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG =
      "LockSettingsPrimaryAuth.nonStrongBiometricIdleTimeoutForUser";
  protected static final String NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG =
      "LockSettingsPrimaryAuth.nonStrongBiometricTimeoutForUser";
  protected static final String STRONG_AUTH_TIMEOUT_ALARM_TAG =
      "LockSettingsStrongAuth.timeoutForUser";
  public final AlarmManager mAlarmManager;
  public final Context mContext;
  public final boolean mDefaultIsNonStrongBiometricAllowed;
  public final int mDefaultStrongAuthFlags;
  protected final Handler mHandler;
  public final Injector mInjector;
  protected final SparseBooleanArray mIsNonStrongBiometricAllowedForUser;
  public final LockPatternUtils mLockPatternUtils;
  protected final ArrayMap mNonStrongBiometricIdleTimeoutAlarmListener;
  protected final ArrayMap mNonStrongBiometricTimeoutAlarmListener;
  protected final SparseIntArray mStrongAuthForUser;
  protected final ArrayMap mStrongAuthTimeoutAlarmListenerForUser;
  public final RemoteCallbackList mTrackers;

  static {
    DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("LockSettingsStrongAuth", 3);
  }

  public LockSettingsStrongAuth(Context context) {
    this(context, new Injector());
  }

  public LockSettingsStrongAuth(Context context, Injector injector) {
    this.mTrackers = new RemoteCallbackList();
    this.mStrongAuthForUser = new SparseIntArray();
    this.mIsNonStrongBiometricAllowedForUser = new SparseBooleanArray();
    this.mStrongAuthTimeoutAlarmListenerForUser = new ArrayMap();
    this.mNonStrongBiometricTimeoutAlarmListener = new ArrayMap();
    this.mNonStrongBiometricIdleTimeoutAlarmListener = new ArrayMap();
    this.mDefaultIsNonStrongBiometricAllowed = true;
    this.mHandler =
        new Handler(
            Looper
                .getMainLooper()) { // from class:
                                    // com.android.server.locksettings.LockSettingsStrongAuth.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            switch (message.what) {
              case 1:
                LockSettingsStrongAuth.this.handleRequireStrongAuth(message.arg1, message.arg2);
                break;
              case 2:
                LockSettingsStrongAuth.this.handleAddStrongAuthTracker(
                    (IStrongAuthTracker) message.obj);
                break;
              case 3:
                LockSettingsStrongAuth.this.handleRemoveStrongAuthTracker(
                    (IStrongAuthTracker) message.obj);
                break;
              case 4:
                LockSettingsStrongAuth.this.handleRemoveUser(message.arg1);
                break;
              case 5:
                LockSettingsStrongAuth.this.handleScheduleStrongAuthTimeout(message.arg1);
                break;
              case 6:
                LockSettingsStrongAuth.this.handleNoLongerRequireStrongAuth(
                    message.arg1, message.arg2);
                break;
              case 7:
                LockSettingsStrongAuth.this.handleScheduleNonStrongBiometricTimeout(message.arg1);
                break;
              case 8:
                LockSettingsStrongAuth.this.handleStrongBiometricUnlock(message.arg1);
                break;
              case 9:
                LockSettingsStrongAuth.this.handleScheduleNonStrongBiometricIdleTimeout(
                    message.arg1);
                break;
              case 10:
                LockSettingsStrongAuth.this.handleRefreshStrongAuthTimeout(message.arg1);
                break;
            }
          }
        };
    this.mContext = context;
    this.mInjector = injector;
    this.mDefaultStrongAuthFlags = injector.getDefaultStrongAuthFlags(context);
    this.mAlarmManager = injector.getAlarmManager(context);
    this.mLockPatternUtils = injector.getLockPatternUtils(context);
  }

  public class Injector {
    public AlarmManager getAlarmManager(Context context) {
      return (AlarmManager) context.getSystemService(AlarmManager.class);
    }

    public int getDefaultStrongAuthFlags(Context context) {
      return LockPatternUtils.StrongAuthTracker.getDefaultFlags(context);
    }

    public long getNextAlarmTimeMs(long j) {
      return SystemClock.elapsedRealtime() + j;
    }

    public long getElapsedRealtimeMs() {
      return SystemClock.elapsedRealtime();
    }

    public LockPatternUtils getLockPatternUtils(Context context) {
      return new LockPatternUtils(context);
    }
  }

  public final void handleAddStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
    this.mTrackers.register(iStrongAuthTracker);
    for (int i = 0; i < this.mStrongAuthForUser.size(); i++) {
      try {
        iStrongAuthTracker.onStrongAuthRequiredChanged(
            this.mStrongAuthForUser.valueAt(i), this.mStrongAuthForUser.keyAt(i));
      } catch (RemoteException e) {
        Slog.e("LockSettingsStrongAuth", "Exception while adding StrongAuthTracker.", e);
      }
    }
    for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
      try {
        iStrongAuthTracker.onIsNonStrongBiometricAllowedChanged(
            this.mIsNonStrongBiometricAllowedForUser.valueAt(i2),
            this.mIsNonStrongBiometricAllowedForUser.keyAt(i2));
      } catch (RemoteException e2) {
        Slog.e(
            "LockSettingsStrongAuth",
            "Exception while adding StrongAuthTracker: IsNonStrongBiometricAllowedChanged.",
            e2);
      }
    }
  }

  public final void handleRemoveStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
    this.mTrackers.unregister(iStrongAuthTracker);
  }

  public final void handleRequireStrongAuth(int i, int i2) {
    if (i2 == -1) {
      for (int i3 = 0; i3 < this.mStrongAuthForUser.size(); i3++) {
        handleRequireStrongAuthOneUser(i, this.mStrongAuthForUser.keyAt(i3));
      }
      return;
    }
    handleRequireStrongAuthOneUser(i, i2);
  }

  public final void handleRequireStrongAuthOneUser(int i, int i2) {
    int i3 = this.mStrongAuthForUser.get(i2, this.mDefaultStrongAuthFlags);
    int i4 = i == 0 ? 0 : i | i3;
    if (i3 != i4) {
      this.mStrongAuthForUser.put(i2, i4);
      notifyStrongAuthTrackers(i4, i2);
    }
  }

  public final void handleNoLongerRequireStrongAuth(int i, int i2) {
    if (i2 == -1) {
      for (int i3 = 0; i3 < this.mStrongAuthForUser.size(); i3++) {
        handleNoLongerRequireStrongAuthOneUser(i, this.mStrongAuthForUser.keyAt(i3));
      }
      return;
    }
    handleNoLongerRequireStrongAuthOneUser(i, i2);
  }

  public final void handleNoLongerRequireStrongAuthOneUser(int i, int i2) {
    int i3 = this.mStrongAuthForUser.get(i2, this.mDefaultStrongAuthFlags);
    int i4 = (~i) & i3;
    if (i3 != i4) {
      this.mStrongAuthForUser.put(i2, i4);
      notifyStrongAuthTrackers(i4, i2);
    }
  }

  public final void handleRemoveUser(int i) {
    int indexOfKey = this.mStrongAuthForUser.indexOfKey(i);
    if (indexOfKey >= 0) {
      this.mStrongAuthForUser.removeAt(indexOfKey);
      notifyStrongAuthTrackers(this.mDefaultStrongAuthFlags, i);
    }
    int indexOfKey2 = this.mIsNonStrongBiometricAllowedForUser.indexOfKey(i);
    if (indexOfKey2 >= 0) {
      this.mIsNonStrongBiometricAllowedForUser.removeAt(indexOfKey2);
      notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(true, i);
    }
  }

  public final void rescheduleStrongAuthTimeoutAlarm(long j, int i) {
    StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener =
        (StrongAuthTimeoutAlarmListener)
            this.mStrongAuthTimeoutAlarmListenerForUser.get(Integer.valueOf(i));
    if (strongAuthTimeoutAlarmListener != null) {
      this.mAlarmManager.cancel(strongAuthTimeoutAlarmListener);
      strongAuthTimeoutAlarmListener.setLatestStrongAuthTime(j);
    } else {
      strongAuthTimeoutAlarmListener = new StrongAuthTimeoutAlarmListener(j, i);
      this.mStrongAuthTimeoutAlarmListenerForUser.put(
          Integer.valueOf(i), strongAuthTimeoutAlarmListener);
    }
    long requiredStrongAuthTimeout = j + getRequiredStrongAuthTimeout(i);
    updateStrongAuthTimeoutInfo("lockscreen.strong_bio_timeout", requiredStrongAuthTimeout, i);
    this.mAlarmManager.setExact(
        2,
        requiredStrongAuthTimeout,
        STRONG_AUTH_TIMEOUT_ALARM_TAG,
        strongAuthTimeoutAlarmListener,
        this.mHandler);
  }

  public final void handleScheduleStrongAuthTimeout(int i) {
    if (DEBUG) {
      Slog.d("LockSettingsStrongAuth", "handleScheduleStrongAuthTimeout for userId=" + i);
    }
    rescheduleStrongAuthTimeoutAlarm(this.mInjector.getElapsedRealtimeMs(), i);
    cancelNonStrongBiometricAlarmListener(i);
    cancelNonStrongBiometricIdleAlarmListener(i);
    setIsNonStrongBiometricAllowed(true, i);
  }

  public final void handleRefreshStrongAuthTimeout(int i) {
    StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener =
        (StrongAuthTimeoutAlarmListener)
            this.mStrongAuthTimeoutAlarmListenerForUser.get(Integer.valueOf(i));
    if (strongAuthTimeoutAlarmListener != null) {
      rescheduleStrongAuthTimeoutAlarm(strongAuthTimeoutAlarmListener.getLatestStrongAuthTime(), i);
    }
  }

  public final void handleScheduleNonStrongBiometricTimeout(int i) {
    boolean z = DEBUG;
    if (z) {
      Slog.d("LockSettingsStrongAuth", "handleScheduleNonStrongBiometricTimeout for userId=" + i);
    }
    long requiredNonStrongBiometricTimeout = getRequiredNonStrongBiometricTimeout();
    if (((NonStrongBiometricTimeoutAlarmListener)
            this.mNonStrongBiometricTimeoutAlarmListener.get(Integer.valueOf(i)))
        == null) {
      if (z) {
        Slog.d(
            "LockSettingsStrongAuth",
            "Schedule a new alarm for non-strong biometric fallback timeout");
      }
      NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener =
          new NonStrongBiometricTimeoutAlarmListener(i);
      this.mNonStrongBiometricTimeoutAlarmListener.put(
          Integer.valueOf(i), nonStrongBiometricTimeoutAlarmListener);
      updateStrongAuthTimeoutInfo(
          "lockscreen.non_strong_bio_timeout", requiredNonStrongBiometricTimeout, i);
      this.mAlarmManager.setExact(
          2,
          requiredNonStrongBiometricTimeout,
          NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG,
          nonStrongBiometricTimeoutAlarmListener,
          this.mHandler);
    } else if (z) {
      Slog.d(
          "LockSettingsStrongAuth",
          "There is an existing alarm for non-strong biometric fallback timeout, so do not"
              + " re-schedule");
    }
    cancelNonStrongBiometricIdleAlarmListener(i);
  }

  public final void handleStrongBiometricUnlock(int i) {
    if (DEBUG) {
      Slog.d("LockSettingsStrongAuth", "handleStrongBiometricUnlock for userId=" + i);
    }
    cancelNonStrongBiometricAlarmListener(i);
    cancelNonStrongBiometricIdleAlarmListener(i);
    setIsNonStrongBiometricAllowed(true, i);
  }

  public final void cancelNonStrongBiometricAlarmListener(int i) {
    boolean z = DEBUG;
    if (z) {
      Slog.d("LockSettingsStrongAuth", "cancelNonStrongBiometricAlarmListener for userId=" + i);
    }
    NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener =
        (NonStrongBiometricTimeoutAlarmListener)
            this.mNonStrongBiometricTimeoutAlarmListener.get(Integer.valueOf(i));
    if (nonStrongBiometricTimeoutAlarmListener != null) {
      if (z) {
        Slog.d("LockSettingsStrongAuth", "Cancel alarm for non-strong biometric fallback timeout");
      }
      updateStrongAuthTimeoutInfo("lockscreen.non_strong_bio_timeout", 0L, i);
      this.mAlarmManager.cancel(nonStrongBiometricTimeoutAlarmListener);
      this.mNonStrongBiometricTimeoutAlarmListener.remove(Integer.valueOf(i));
    }
  }

  public final void cancelNonStrongBiometricIdleAlarmListener(int i) {
    boolean z = DEBUG;
    if (z) {
      Slog.d("LockSettingsStrongAuth", "cancelNonStrongBiometricIdleAlarmListener for userId=" + i);
    }
    NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener =
        (NonStrongBiometricIdleTimeoutAlarmListener)
            this.mNonStrongBiometricIdleTimeoutAlarmListener.get(Integer.valueOf(i));
    if (nonStrongBiometricIdleTimeoutAlarmListener != null) {
      if (z) {
        Slog.d("LockSettingsStrongAuth", "Cancel alarm for non-strong biometric idle timeout");
      }
      updateStrongAuthTimeoutInfo("lockscreen.non_strong_bio_idle_timeout", 0L, i);
      this.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener);
    }
  }

  public void setIsNonStrongBiometricAllowed(boolean z, int i) {
    if (DEBUG) {
      Slog.d(
          "LockSettingsStrongAuth",
          "setIsNonStrongBiometricAllowed for allowed=" + z + ", userId=" + i);
    }
    if (i == -1) {
      for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
        setIsNonStrongBiometricAllowedOneUser(
            z, this.mIsNonStrongBiometricAllowedForUser.keyAt(i2));
      }
      return;
    }
    setIsNonStrongBiometricAllowedOneUser(z, i);
  }

  public final void setIsNonStrongBiometricAllowedOneUser(boolean z, int i) {
    boolean z2 = DEBUG;
    if (z2) {
      Slog.d(
          "LockSettingsStrongAuth",
          "setIsNonStrongBiometricAllowedOneUser for allowed=" + z + ", userId=" + i);
    }
    boolean z3 = this.mIsNonStrongBiometricAllowedForUser.get(i, true);
    if (z != z3) {
      if (z2) {
        Slog.d(
            "LockSettingsStrongAuth",
            "mIsNonStrongBiometricAllowedForUser value changed: oldValue=" + z3 + ", allowed=" + z);
      }
      this.mIsNonStrongBiometricAllowedForUser.put(i, z);
      notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(z, i);
    }
  }

  public final void handleScheduleNonStrongBiometricIdleTimeout(int i) {
    boolean z = DEBUG;
    if (z) {
      Slog.d(
          "LockSettingsStrongAuth", "handleScheduleNonStrongBiometricIdleTimeout for userId=" + i);
    }
    long requiredNonStrongBiometricIdleTimeout = getRequiredNonStrongBiometricIdleTimeout();
    NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener =
        (NonStrongBiometricIdleTimeoutAlarmListener)
            this.mNonStrongBiometricIdleTimeoutAlarmListener.get(Integer.valueOf(i));
    if (nonStrongBiometricIdleTimeoutAlarmListener != null) {
      if (z) {
        Slog.d(
            "LockSettingsStrongAuth",
            "Cancel existing alarm for non-strong biometric idle timeout");
      }
      this.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener);
    } else {
      nonStrongBiometricIdleTimeoutAlarmListener =
          new NonStrongBiometricIdleTimeoutAlarmListener(i);
      this.mNonStrongBiometricIdleTimeoutAlarmListener.put(
          Integer.valueOf(i), nonStrongBiometricIdleTimeoutAlarmListener);
    }
    NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener2 =
        nonStrongBiometricIdleTimeoutAlarmListener;
    if (z) {
      Slog.d(
          "LockSettingsStrongAuth", "Schedule a new alarm for non-strong biometric idle timeout");
    }
    updateStrongAuthTimeoutInfo(
        "lockscreen.non_strong_bio_idle_timeout", requiredNonStrongBiometricIdleTimeout, i);
    this.mAlarmManager.setExact(
        2,
        requiredNonStrongBiometricIdleTimeout,
        NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG,
        nonStrongBiometricIdleTimeoutAlarmListener2,
        this.mHandler);
  }

  public final void notifyStrongAuthTrackers(int i, int i2) {
    int beginBroadcast = this.mTrackers.beginBroadcast();
    while (beginBroadcast > 0) {
      beginBroadcast--;
      try {
        try {
          this.mTrackers.getBroadcastItem(beginBroadcast).onStrongAuthRequiredChanged(i, i2);
        } catch (RemoteException e) {
          Slog.e("LockSettingsStrongAuth", "Exception while notifying StrongAuthTracker.", e);
        }
      } finally {
        this.mTrackers.finishBroadcast();
      }
    }
  }

  public final void notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(boolean z, int i) {
    if (DEBUG) {
      Slog.d(
          "LockSettingsStrongAuth",
          "notifyStrongAuthTrackersForIsNonStrongBiometricAllowed for allowed="
              + z
              + ", userId="
              + i);
    }
    int beginBroadcast = this.mTrackers.beginBroadcast();
    while (beginBroadcast > 0) {
      beginBroadcast--;
      try {
        try {
          this.mTrackers
              .getBroadcastItem(beginBroadcast)
              .onIsNonStrongBiometricAllowedChanged(z, i);
        } catch (RemoteException e) {
          Slog.e(
              "LockSettingsStrongAuth",
              "Exception while notifying StrongAuthTracker: IsNonStrongBiometricAllowedChanged.",
              e);
        }
      } finally {
        this.mTrackers.finishBroadcast();
      }
    }
  }

  public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
    this.mHandler.obtainMessage(2, iStrongAuthTracker).sendToTarget();
  }

  public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
    this.mHandler.obtainMessage(3, iStrongAuthTracker).sendToTarget();
  }

  public void removeUser(int i) {
    this.mHandler.obtainMessage(4, i, 0).sendToTarget();
  }

  public void requireStrongAuth(int i, int i2) {
    if (i2 == -1 || i2 >= 0) {
      this.mHandler.obtainMessage(1, i, i2).sendToTarget();
      return;
    }
    throw new IllegalArgumentException("userId must be an explicit user id or USER_ALL");
  }

  public void noLongerRequireStrongAuth(int i, int i2) {
    if (i2 == -1 || i2 >= 0) {
      this.mHandler.obtainMessage(6, i, i2).sendToTarget();
      return;
    }
    throw new IllegalArgumentException("userId must be an explicit user id or USER_ALL");
  }

  public void reportUnlock(int i) {
    requireStrongAuth(0, i);
  }

  public void reportSuccessfulStrongAuthUnlock(int i) {
    this.mHandler.obtainMessage(5, i, 0).sendToTarget();
  }

  public void refreshStrongAuthTimeout(int i) {
    this.mHandler.obtainMessage(10, i, 0).sendToTarget();
  }

  public void reportSuccessfulBiometricUnlock(boolean z, int i) {
    if (DEBUG) {
      Slog.d(
          "LockSettingsStrongAuth",
          "reportSuccessfulBiometricUnlock for isStrongBiometric=" + z + ", userId=" + i);
    }
    if (z) {
      this.mHandler.obtainMessage(8, i, 0).sendToTarget();
    } else {
      this.mHandler.obtainMessage(7, i, 0).sendToTarget();
    }
  }

  public void scheduleNonStrongBiometricIdleTimeout(int i) {
    if (DEBUG) {
      Slog.d("LockSettingsStrongAuth", "scheduleNonStrongBiometricIdleTimeout for userId=" + i);
    }
    this.mHandler.obtainMessage(9, i, 0).sendToTarget();
  }

  public class StrongAuthTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
    public long mLatestStrongAuthTime;
    public final int mUserId;

    public StrongAuthTimeoutAlarmListener(long j, int i) {
      this.mLatestStrongAuthTime = j;
      this.mUserId = i;
    }

    public void setLatestStrongAuthTime(long j) {
      this.mLatestStrongAuthTime = j;
    }

    public long getLatestStrongAuthTime() {
      return this.mLatestStrongAuthTime;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      LockSettingsStrongAuth.this.requireStrongAuth(16, this.mUserId);
    }
  }

  public class NonStrongBiometricTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
    public final int mUserId;

    public NonStrongBiometricTimeoutAlarmListener(int i) {
      this.mUserId = i;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      LockSettingsStrongAuth.this.requireStrongAuth(128, this.mUserId);
    }
  }

  public class NonStrongBiometricIdleTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
    public final int mUserId;

    public NonStrongBiometricIdleTimeoutAlarmListener(int i) {
      this.mUserId = i;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      LockSettingsStrongAuth.this.setIsNonStrongBiometricAllowed(false, this.mUserId);
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("PrimaryAuthFlags state:");
    indentingPrintWriter.increaseIndent();
    for (int i = 0; i < this.mStrongAuthForUser.size(); i++) {
      indentingPrintWriter.println(
          "userId="
              + this.mStrongAuthForUser.keyAt(i)
              + ", primaryAuthFlags="
              + Integer.toHexString(this.mStrongAuthForUser.valueAt(i)));
    }
    indentingPrintWriter.println();
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println("NonStrongBiometricAllowed state:");
    indentingPrintWriter.increaseIndent();
    for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
      indentingPrintWriter.println(
          "userId="
              + this.mIsNonStrongBiometricAllowedForUser.keyAt(i2)
              + ", allowed="
              + this.mIsNonStrongBiometricAllowedForUser.valueAt(i2));
    }
    indentingPrintWriter.println();
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println("strong auth timeout state:");
    indentingPrintWriter.increaseIndent();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    long biometricStrongAuthTimeout =
        this.mLockPatternUtils.getBiometricStrongAuthTimeout("lockscreen.strong_bio_timeout", 0);
    if (biometricStrongAuthTimeout != 0) {
      indentingPrintWriter.println(
          "strong biometric timeout:"
              + simpleDateFormat.format(new Date(biometricStrongAuthTimeout)));
    }
    long biometricStrongAuthTimeout2 =
        this.mLockPatternUtils.getBiometricStrongAuthTimeout(
            "lockscreen.non_strong_bio_timeout", 0);
    if (biometricStrongAuthTimeout2 != 0) {
      indentingPrintWriter.println(
          "non strong biometric timeout:"
              + simpleDateFormat.format(new Date(biometricStrongAuthTimeout2)));
    }
    long biometricStrongAuthTimeout3 =
        this.mLockPatternUtils.getBiometricStrongAuthTimeout(
            "lockscreen.non_strong_bio_idle_timeout", 0);
    if (biometricStrongAuthTimeout3 != 0) {
      indentingPrintWriter.println(
          "non strong biometric idle timeout:"
              + simpleDateFormat.format(new Date(biometricStrongAuthTimeout3)));
    }
    indentingPrintWriter.println();
    indentingPrintWriter.decreaseIndent();
  }

  public final long getRequiredStrongAuthTimeout(int i) {
    DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.mContext.getSystemService("device_policy");
    long j = SystemProperties.getLong("persist.lock.strong_auth_timeout", 0L);
    return (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j == 0)
        ? devicePolicyManager.getRequiredStrongAuthTimeout(null, i)
        : j;
  }

  public final long getRequiredNonStrongBiometricTimeout() {
    long j = SystemProperties.getLong("persist.lock.non_strong_biometric_timeout", 0L);
    Injector injector = this.mInjector;
    if (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j == 0) {
      j = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
    }
    return injector.getNextAlarmTimeMs(j);
  }

  public final long getRequiredNonStrongBiometricIdleTimeout() {
    long j = SystemProperties.getLong("persist.lock.non_strong_biometric_idle_timeout", 0L);
    Injector injector = this.mInjector;
    if (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j == 0) {
      j = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
    }
    return injector.getNextAlarmTimeMs(j);
  }

  public final void updateStrongAuthTimeoutInfo(String str, long j, int i) {
    this.mLockPatternUtils.setBiometricStrongAuthTimeout(
        str, j != 0 ? (System.currentTimeMillis() + j) - SystemClock.elapsedRealtime() : 0L, i);
  }
}
