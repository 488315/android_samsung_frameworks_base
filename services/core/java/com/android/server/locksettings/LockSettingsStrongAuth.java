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
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class LockSettingsStrongAuth {
    public static final boolean DEBUG;
    protected static final String NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG = "LockSettingsPrimaryAuth.nonStrongBiometricIdleTimeoutForUser";
    protected static final String NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG = "LockSettingsPrimaryAuth.nonStrongBiometricTimeoutForUser";
    protected static final String STRONG_AUTH_TIMEOUT_ALARM_TAG = "LockSettingsStrongAuth.timeoutForUser";
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final int mDefaultStrongAuthFlags;
    public final Injector mInjector;
    public final LockPatternUtils mLockPatternUtils;
    public final RemoteCallbackList mTrackers = new RemoteCallbackList();
    protected final SparseIntArray mStrongAuthForUser = new SparseIntArray();
    protected final SparseBooleanArray mIsNonStrongBiometricAllowedForUser = new SparseBooleanArray();
    protected final ArrayMap mStrongAuthTimeoutAlarmListenerForUser = new ArrayMap();
    protected final ArrayMap mNonStrongBiometricTimeoutAlarmListener = new ArrayMap();
    protected final ArrayMap mNonStrongBiometricIdleTimeoutAlarmListener = new ArrayMap();
    protected final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.locksettings.LockSettingsStrongAuth.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            LockSettingsStrongAuth lockSettingsStrongAuth = LockSettingsStrongAuth.this;
            switch (i) {
                case 1:
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    if (i3 == -1) {
                        for (int i4 = 0; i4 < lockSettingsStrongAuth.mStrongAuthForUser.size(); i4++) {
                            int keyAt = lockSettingsStrongAuth.mStrongAuthForUser.keyAt(i4);
                            int i5 = lockSettingsStrongAuth.mStrongAuthForUser.get(keyAt, lockSettingsStrongAuth.mDefaultStrongAuthFlags);
                            int i6 = i2 == 0 ? 0 : i5 | i2;
                            if (i5 != i6) {
                                lockSettingsStrongAuth.mStrongAuthForUser.put(keyAt, i6);
                                lockSettingsStrongAuth.notifyStrongAuthTrackers(i6, keyAt);
                            }
                        }
                        break;
                    } else {
                        boolean z = LockSettingsStrongAuth.DEBUG;
                        int i7 = lockSettingsStrongAuth.mStrongAuthForUser.get(i3, lockSettingsStrongAuth.mDefaultStrongAuthFlags);
                        r8 = i2 != 0 ? i7 | i2 : 0;
                        if (i7 != r8) {
                            lockSettingsStrongAuth.mStrongAuthForUser.put(i3, r8);
                            lockSettingsStrongAuth.notifyStrongAuthTrackers(r8, i3);
                            break;
                        }
                    }
                    break;
                case 2:
                    IStrongAuthTracker iStrongAuthTracker = (IStrongAuthTracker) message.obj;
                    lockSettingsStrongAuth.mTrackers.register(iStrongAuthTracker);
                    for (int i8 = 0; i8 < lockSettingsStrongAuth.mStrongAuthForUser.size(); i8++) {
                        try {
                            iStrongAuthTracker.onStrongAuthRequiredChanged(lockSettingsStrongAuth.mStrongAuthForUser.valueAt(i8), lockSettingsStrongAuth.mStrongAuthForUser.keyAt(i8));
                        } catch (RemoteException e) {
                            Slog.e("LockSettingsStrongAuth", "Exception while adding StrongAuthTracker.", e);
                        }
                    }
                    while (r8 < lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.size()) {
                        try {
                            iStrongAuthTracker.onIsNonStrongBiometricAllowedChanged(lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.valueAt(r8), lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.keyAt(r8));
                        } catch (RemoteException e2) {
                            Slog.e("LockSettingsStrongAuth", "Exception while adding StrongAuthTracker: IsNonStrongBiometricAllowedChanged.", e2);
                        }
                        r8++;
                    }
                    break;
                case 3:
                    lockSettingsStrongAuth.mTrackers.unregister((IStrongAuthTracker) message.obj);
                    break;
                case 4:
                    int i9 = message.arg1;
                    int indexOfKey = lockSettingsStrongAuth.mStrongAuthForUser.indexOfKey(i9);
                    if (indexOfKey >= 0) {
                        lockSettingsStrongAuth.mStrongAuthForUser.removeAt(indexOfKey);
                        lockSettingsStrongAuth.notifyStrongAuthTrackers(lockSettingsStrongAuth.mDefaultStrongAuthFlags, i9);
                    }
                    int indexOfKey2 = lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.indexOfKey(i9);
                    if (indexOfKey2 >= 0) {
                        lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.removeAt(indexOfKey2);
                        lockSettingsStrongAuth.notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(i9, true);
                        break;
                    }
                    break;
                case 5:
                    int i10 = message.arg1;
                    if (LockSettingsStrongAuth.DEBUG) {
                        lockSettingsStrongAuth.getClass();
                        Slog.d("LockSettingsStrongAuth", "handleScheduleStrongAuthTimeout for userId=" + i10);
                    }
                    lockSettingsStrongAuth.rescheduleStrongAuthTimeoutAlarm(i10, lockSettingsStrongAuth.mInjector.getElapsedRealtimeMs());
                    lockSettingsStrongAuth.cancelNonStrongBiometricAlarmListener(i10);
                    lockSettingsStrongAuth.cancelNonStrongBiometricIdleAlarmListener(i10);
                    lockSettingsStrongAuth.setIsNonStrongBiometricAllowed(true, i10);
                    break;
                case 6:
                    int i11 = message.arg1;
                    int i12 = message.arg2;
                    if (i12 == -1) {
                        while (r8 < lockSettingsStrongAuth.mStrongAuthForUser.size()) {
                            int keyAt2 = lockSettingsStrongAuth.mStrongAuthForUser.keyAt(r8);
                            int i13 = lockSettingsStrongAuth.mStrongAuthForUser.get(keyAt2, lockSettingsStrongAuth.mDefaultStrongAuthFlags);
                            int i14 = (~i11) & i13;
                            if (i13 != i14) {
                                lockSettingsStrongAuth.mStrongAuthForUser.put(keyAt2, i14);
                                lockSettingsStrongAuth.notifyStrongAuthTrackers(i14, keyAt2);
                            }
                            r8++;
                        }
                        break;
                    } else {
                        boolean z2 = LockSettingsStrongAuth.DEBUG;
                        int i15 = lockSettingsStrongAuth.mStrongAuthForUser.get(i12, lockSettingsStrongAuth.mDefaultStrongAuthFlags);
                        int i16 = (~i11) & i15;
                        if (i15 != i16) {
                            lockSettingsStrongAuth.mStrongAuthForUser.put(i12, i16);
                            lockSettingsStrongAuth.notifyStrongAuthTrackers(i16, i12);
                            break;
                        }
                    }
                    break;
                case 7:
                    int i17 = message.arg1;
                    boolean z3 = LockSettingsStrongAuth.DEBUG;
                    lockSettingsStrongAuth.getClass();
                    boolean z4 = LockSettingsStrongAuth.DEBUG;
                    if (z4) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(i17, "handleScheduleNonStrongBiometricTimeout for userId=", "LockSettingsStrongAuth");
                    }
                    long j = SystemProperties.getLong("persist.lock.non_strong_biometric_timeout", 0L);
                    if (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j == 0) {
                        j = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                    }
                    long nextAlarmTimeMs = lockSettingsStrongAuth.mInjector.getNextAlarmTimeMs(j);
                    if (((NonStrongBiometricTimeoutAlarmListener) lockSettingsStrongAuth.mNonStrongBiometricTimeoutAlarmListener.get(Integer.valueOf(i17))) == null) {
                        if (z4) {
                            Slog.d("LockSettingsStrongAuth", "Schedule a new alarm for non-strong biometric fallback timeout");
                        }
                        NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener = lockSettingsStrongAuth.new NonStrongBiometricTimeoutAlarmListener(i17);
                        lockSettingsStrongAuth.mNonStrongBiometricTimeoutAlarmListener.put(Integer.valueOf(i17), nonStrongBiometricTimeoutAlarmListener);
                        lockSettingsStrongAuth.updateStrongAuthTimeoutInfo(i17, "lockscreen.non_strong_bio_timeout", nextAlarmTimeMs);
                        lockSettingsStrongAuth.mAlarmManager.setExact(2, nextAlarmTimeMs, LockSettingsStrongAuth.NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG, nonStrongBiometricTimeoutAlarmListener, lockSettingsStrongAuth.mHandler);
                    } else if (z4) {
                        Slog.d("LockSettingsStrongAuth", "There is an existing alarm for non-strong biometric fallback timeout, so do not re-schedule");
                    }
                    lockSettingsStrongAuth.cancelNonStrongBiometricIdleAlarmListener(i17);
                    break;
                case 8:
                    int i18 = message.arg1;
                    if (LockSettingsStrongAuth.DEBUG) {
                        lockSettingsStrongAuth.getClass();
                        Slog.d("LockSettingsStrongAuth", "handleStrongBiometricUnlock for userId=" + i18);
                    }
                    lockSettingsStrongAuth.cancelNonStrongBiometricAlarmListener(i18);
                    lockSettingsStrongAuth.cancelNonStrongBiometricIdleAlarmListener(i18);
                    lockSettingsStrongAuth.setIsNonStrongBiometricAllowed(true, i18);
                    break;
                case 9:
                    int i19 = message.arg1;
                    boolean z5 = LockSettingsStrongAuth.DEBUG;
                    lockSettingsStrongAuth.getClass();
                    boolean z6 = LockSettingsStrongAuth.DEBUG;
                    if (z6) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(i19, "handleScheduleNonStrongBiometricIdleTimeout for userId=", "LockSettingsStrongAuth");
                    }
                    long j2 = SystemProperties.getLong("persist.lock.non_strong_biometric_idle_timeout", 0L);
                    if (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j2 == 0) {
                        j2 = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
                    }
                    long nextAlarmTimeMs2 = lockSettingsStrongAuth.mInjector.getNextAlarmTimeMs(j2);
                    NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener = (NonStrongBiometricIdleTimeoutAlarmListener) lockSettingsStrongAuth.mNonStrongBiometricIdleTimeoutAlarmListener.get(Integer.valueOf(i19));
                    if (nonStrongBiometricIdleTimeoutAlarmListener != null) {
                        if (z6) {
                            Slog.d("LockSettingsStrongAuth", "Cancel existing alarm for non-strong biometric idle timeout");
                        }
                        lockSettingsStrongAuth.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener);
                    } else {
                        nonStrongBiometricIdleTimeoutAlarmListener = lockSettingsStrongAuth.new NonStrongBiometricIdleTimeoutAlarmListener(i19);
                        lockSettingsStrongAuth.mNonStrongBiometricIdleTimeoutAlarmListener.put(Integer.valueOf(i19), nonStrongBiometricIdleTimeoutAlarmListener);
                    }
                    NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener2 = nonStrongBiometricIdleTimeoutAlarmListener;
                    if (z6) {
                        Slog.d("LockSettingsStrongAuth", "Schedule a new alarm for non-strong biometric idle timeout");
                    }
                    lockSettingsStrongAuth.updateStrongAuthTimeoutInfo(i19, "lockscreen.non_strong_bio_idle_timeout", nextAlarmTimeMs2);
                    lockSettingsStrongAuth.mAlarmManager.setExact(2, nextAlarmTimeMs2, LockSettingsStrongAuth.NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG, nonStrongBiometricIdleTimeoutAlarmListener2, lockSettingsStrongAuth.mHandler);
                    break;
                case 10:
                    int i20 = message.arg1;
                    StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener = (StrongAuthTimeoutAlarmListener) lockSettingsStrongAuth.mStrongAuthTimeoutAlarmListenerForUser.get(Integer.valueOf(i20));
                    if (strongAuthTimeoutAlarmListener != null) {
                        lockSettingsStrongAuth.rescheduleStrongAuthTimeoutAlarm(i20, strongAuthTimeoutAlarmListener.mLatestStrongAuthTime);
                        break;
                    }
                    break;
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
        public AlarmManager getAlarmManager(Context context) {
            return (AlarmManager) context.getSystemService(AlarmManager.class);
        }

        public int getDefaultStrongAuthFlags(Context context) {
            return LockPatternUtils.StrongAuthTracker.getDefaultFlags(context);
        }

        public long getElapsedRealtimeMs() {
            return SystemClock.elapsedRealtime();
        }

        public LockPatternUtils getLockPatternUtils(Context context) {
            return new LockPatternUtils(context);
        }

        public long getNextAlarmTimeMs(long j) {
            return SystemClock.elapsedRealtime() + j;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NonStrongBiometricIdleTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
        public final int mUserId;

        public NonStrongBiometricIdleTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            LockSettingsStrongAuth.this.setIsNonStrongBiometricAllowed(false, this.mUserId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NonStrongBiometricTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
        public final int mUserId;

        public NonStrongBiometricTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            LockSettingsStrongAuth.this.requireStrongAuth(128, this.mUserId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class StrongAuthTimeoutAlarmListener implements AlarmManager.OnAlarmListener {
        public long mLatestStrongAuthTime;
        public final int mUserId;

        public StrongAuthTimeoutAlarmListener(long j, int i) {
            this.mLatestStrongAuthTime = j;
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            LockSettingsStrongAuth.this.requireStrongAuth(16, this.mUserId);
        }
    }

    static {
        DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("LockSettingsStrongAuth", 3);
    }

    public LockSettingsStrongAuth(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mDefaultStrongAuthFlags = injector.getDefaultStrongAuthFlags(context);
        this.mAlarmManager = injector.getAlarmManager(context);
        this.mLockPatternUtils = injector.getLockPatternUtils(context);
    }

    public final void cancelNonStrongBiometricAlarmListener(int i) {
        boolean z = DEBUG;
        if (z) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "cancelNonStrongBiometricAlarmListener for userId=", "LockSettingsStrongAuth");
        }
        NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener = (NonStrongBiometricTimeoutAlarmListener) this.mNonStrongBiometricTimeoutAlarmListener.get(Integer.valueOf(i));
        if (nonStrongBiometricTimeoutAlarmListener != null) {
            if (z) {
                Slog.d("LockSettingsStrongAuth", "Cancel alarm for non-strong biometric fallback timeout");
            }
            updateStrongAuthTimeoutInfo(i, "lockscreen.non_strong_bio_timeout", 0L);
            this.mAlarmManager.cancel(nonStrongBiometricTimeoutAlarmListener);
            this.mNonStrongBiometricTimeoutAlarmListener.remove(Integer.valueOf(i));
        }
    }

    public final void cancelNonStrongBiometricIdleAlarmListener(int i) {
        boolean z = DEBUG;
        if (z) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "cancelNonStrongBiometricIdleAlarmListener for userId=", "LockSettingsStrongAuth");
        }
        NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener = (NonStrongBiometricIdleTimeoutAlarmListener) this.mNonStrongBiometricIdleTimeoutAlarmListener.get(Integer.valueOf(i));
        if (nonStrongBiometricIdleTimeoutAlarmListener != null) {
            if (z) {
                Slog.d("LockSettingsStrongAuth", "Cancel alarm for non-strong biometric idle timeout");
            }
            updateStrongAuthTimeoutInfo(i, "lockscreen.non_strong_bio_idle_timeout", 0L);
            this.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener);
        }
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

    public final void notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(int i, boolean z) {
        if (DEBUG) {
            Slog.d("LockSettingsStrongAuth", "notifyStrongAuthTrackersForIsNonStrongBiometricAllowed for allowed=" + z + ", userId=" + i);
        }
        int beginBroadcast = this.mTrackers.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                try {
                    this.mTrackers.getBroadcastItem(beginBroadcast).onIsNonStrongBiometricAllowedChanged(z, i);
                } catch (RemoteException e) {
                    Slog.e("LockSettingsStrongAuth", "Exception while notifying StrongAuthTracker: IsNonStrongBiometricAllowedChanged.", e);
                }
            } finally {
                this.mTrackers.finishBroadcast();
            }
        }
    }

    public final void requireStrongAuth(int i, int i2) {
        if (i2 != -1 && i2 < 0) {
            throw new IllegalArgumentException("userId must be an explicit user id or USER_ALL");
        }
        this.mHandler.obtainMessage(1, i, i2).sendToTarget();
    }

    public final void rescheduleStrongAuthTimeoutAlarm(int i, long j) {
        StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener = (StrongAuthTimeoutAlarmListener) this.mStrongAuthTimeoutAlarmListenerForUser.get(Integer.valueOf(i));
        if (strongAuthTimeoutAlarmListener != null) {
            this.mAlarmManager.cancel(strongAuthTimeoutAlarmListener);
            strongAuthTimeoutAlarmListener.mLatestStrongAuthTime = j;
        } else {
            strongAuthTimeoutAlarmListener = new StrongAuthTimeoutAlarmListener(j, i);
            this.mStrongAuthTimeoutAlarmListenerForUser.put(Integer.valueOf(i), strongAuthTimeoutAlarmListener);
        }
        StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener2 = strongAuthTimeoutAlarmListener;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        long j2 = SystemProperties.getLong("persist.lock.strong_auth_timeout", 0L);
        if (!SystemProperties.get("ro.build.tags", "").equals("test-keys") || j2 == 0) {
            j2 = devicePolicyManager.getRequiredStrongAuthTimeout(null, i);
        }
        long j3 = j + j2;
        updateStrongAuthTimeoutInfo(i, "lockscreen.strong_bio_timeout", j3);
        this.mAlarmManager.setExact(2, j3, STRONG_AUTH_TIMEOUT_ALARM_TAG, strongAuthTimeoutAlarmListener2, this.mHandler);
    }

    public void setIsNonStrongBiometricAllowed(boolean z, int i) {
        if (DEBUG) {
            Slog.d("LockSettingsStrongAuth", "setIsNonStrongBiometricAllowed for allowed=" + z + ", userId=" + i);
        }
        if (i != -1) {
            setIsNonStrongBiometricAllowedOneUser(i, z);
            return;
        }
        for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
            setIsNonStrongBiometricAllowedOneUser(this.mIsNonStrongBiometricAllowedForUser.keyAt(i2), z);
        }
    }

    public final void setIsNonStrongBiometricAllowedOneUser(int i, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            Slog.d("LockSettingsStrongAuth", "setIsNonStrongBiometricAllowedOneUser for allowed=" + z + ", userId=" + i);
        }
        boolean z3 = this.mIsNonStrongBiometricAllowedForUser.get(i, true);
        if (z != z3) {
            if (z2) {
                Slog.d("LockSettingsStrongAuth", "mIsNonStrongBiometricAllowedForUser value changed: oldValue=" + z3 + ", allowed=" + z);
            }
            this.mIsNonStrongBiometricAllowedForUser.put(i, z);
            notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(i, z);
        }
    }

    public final void updateStrongAuthTimeoutInfo(int i, String str, long j) {
        this.mLockPatternUtils.setBiometricStrongAuthTimeout(str, j != 0 ? (System.currentTimeMillis() + j) - SystemClock.elapsedRealtime() : 0L, i);
    }
}
