package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.app.SynchronousUserSwitchObserver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WakefulnessSessionObserver {
    protected static final int OFF_REASON_POWER_BUTTON = 2;
    protected static final int OVERRIDE_OUTCOME_CANCEL_POWER_BUTTON = 5;
    protected static final int OVERRIDE_OUTCOME_CANCEL_USER_INTERACTION = 4;
    protected static final int OVERRIDE_OUTCOME_TIMEOUT_SUCCESS = 1;
    protected static final int OVERRIDE_OUTCOME_TIMEOUT_USER_INITIATED_REVERT = 2;
    public final WakefulnessSessionObserver$Injector$$ExternalSyntheticLambda0 mClock;
    public final Context mContext;
    public final int mOverrideTimeoutMs;
    public int mScreenOffTimeoutMs;
    protected WakefulnessSessionFrameworkStatsLogger mWakefulnessSessionFrameworkStatsLogger;
    protected final SparseArray mPowerGroups = new SparseArray();
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserSwitchObserver extends SynchronousUserSwitchObserver {
        public UserSwitchObserver() {
        }

        public final void onUserSwitching(int i) {
            WakefulnessSessionObserver wakefulnessSessionObserver = WakefulnessSessionObserver.this;
            wakefulnessSessionObserver.updateSettingScreenOffTimeout(wakefulnessSessionObserver.mContext);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakefulnessSessionFrameworkStatsLogger {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class WakefulnessSessionPowerGroup {
        public int mCurrentWakefulness;
        public long mInteractiveStateOnStartTimestamp;
        public final int mPowerGroupId;
        public long mSendOverrideTimeoutLogTimestamp;
        public long mTimeoutOffTimestamp;
        public int mTimeoutOverrideReleaseReason;
        public boolean mIsInteractive = false;
        public int mTimeoutOverrideWakeLockCounter = 0;
        protected int mCurrentUserActivityEvent = 0;
        protected long mCurrentUserActivityTimestamp = -1;
        protected int mPrevUserActivityEvent = 0;
        protected long mPrevUserActivityTimestamp = -1;

        public WakefulnessSessionPowerGroup(int i) {
            this.mPowerGroupId = i;
        }

        public boolean isInOverrideTimeout() {
            boolean z;
            synchronized (WakefulnessSessionObserver.this.mLock) {
                z = this.mTimeoutOverrideWakeLockCounter > 0;
            }
            return z;
        }
    }

    /* renamed from: -$$Nest$mgetScreenOffTimeout, reason: not valid java name */
    public static int m827$$Nest$mgetScreenOffTimeout(WakefulnessSessionObserver wakefulnessSessionObserver) {
        int i;
        synchronized (wakefulnessSessionObserver.mLock) {
            i = wakefulnessSessionObserver.mScreenOffTimeoutMs;
        }
        return i;
    }

    public WakefulnessSessionObserver(Context context) {
        this.mOverrideTimeoutMs = 0;
        new Injector();
        this.mContext = context;
        this.mWakefulnessSessionFrameworkStatsLogger = new WakefulnessSessionFrameworkStatsLogger();
        this.mClock = new WakefulnessSessionObserver$Injector$$ExternalSyntheticLambda0();
        updateSettingScreenOffTimeout(context);
        try {
            ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver(), "WakefulnessSessionObserver");
        } catch (RemoteException unused) {
        }
        this.mOverrideTimeoutMs = this.mContext.getResources().getInteger(R.integer.config_windowOutsetBottom);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, new ContentObserver(new Handler(this.mContext.getMainLooper())) { // from class: com.android.server.power.WakefulnessSessionObserver.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                WakefulnessSessionObserver wakefulnessSessionObserver = WakefulnessSessionObserver.this;
                wakefulnessSessionObserver.updateSettingScreenOffTimeout(wakefulnessSessionObserver.mContext);
            }
        }, -1);
        this.mPowerGroups.append(0, new WakefulnessSessionPowerGroup(0));
    }

    public final void notifyUserActivity(int i, int i2, long j) {
        if (!this.mPowerGroups.contains(i)) {
            this.mPowerGroups.append(i, new WakefulnessSessionPowerGroup(i));
        }
        WakefulnessSessionPowerGroup wakefulnessSessionPowerGroup = (WakefulnessSessionPowerGroup) this.mPowerGroups.get(i);
        int i3 = wakefulnessSessionPowerGroup.mCurrentUserActivityEvent;
        if (i2 == i3) {
            return;
        }
        wakefulnessSessionPowerGroup.mPrevUserActivityEvent = i3;
        wakefulnessSessionPowerGroup.mCurrentUserActivityEvent = i2;
        wakefulnessSessionPowerGroup.mPrevUserActivityTimestamp = wakefulnessSessionPowerGroup.mCurrentUserActivityTimestamp;
        wakefulnessSessionPowerGroup.mCurrentUserActivityTimestamp = j;
    }

    public final void updateSettingScreenOffTimeout(Context context) {
        synchronized (this.mLock) {
            this.mScreenOffTimeoutMs = Settings.System.getIntForUser(context.getContentResolver(), "screen_off_timeout", 15000, -2);
        }
    }
}
