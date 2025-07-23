package android.os;

import android.os.PowerManager;
import android.view.KeyEvent;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class PowerManagerInternal {
    public static final int BOOST_DISPLAY_UPDATE_IMMINENT = 1;
    public static final int BOOST_INTERACTION = 0;
    public static final int MODE_DEVICE_IDLE = 8;
    public static final int MODE_DISPLAY_CHANGE = 17;
    public static final int MODE_DISPLAY_INACTIVE = 9;
    public static final int MODE_DOUBLE_TAP_TO_WAKE = 0;
    public static final int MODE_EXPENSIVE_RENDERING = 6;
    public static final int MODE_FIXED_PERFORMANCE = 3;
    public static final int MODE_INTERACTIVE = 7;
    public static final int MODE_LAUNCH = 5;
    public static final int MODE_LOW_POWER = 1;
    public static final int MODE_SUSTAINED_PERFORMANCE = 2;
    public static final int MODE_VR = 4;
    public static final int USER_ACTIVITY_STATE_ACTIVE = 1;
    public static final int USER_ACTIVITY_STATE_INACTIVE = 0;
    public static final int USER_ACTIVITY_STATE_TIMEOUT = 3000;
    public static final int WAKEFULNESS_ASLEEP = 0;
    public static final int WAKEFULNESS_AWAKE = 1;
    public static final int WAKEFULNESS_DOZING = 3;
    public static final int WAKEFULNESS_DREAMING = 2;

    public interface LowPowerModeListener {
        int getServiceType();

        void onLowPowerModeChanged(PowerSaveState powerSaveState);
    }

    public interface UserActivityStateListener {
        void onChanged(int i);
    }

    public static boolean isInteractive(int i) {
        return i == 1 || i == 2;
    }

    public static int wakefulnessToProtoEnum(int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return i;
                }
            }
        }
        return i2;
    }

    public abstract void disableWakeUpPrevention(IBinder iBinder, boolean z);

    public abstract void enableWakeUpPrevention(IBinder iBinder);

    public abstract void finishUidChanges();

    public abstract PowerManager.SleepData getLastGoToSleep();

    public abstract int getLastUserActivityState();

    public abstract PowerManager.WakeData getLastWakeup();

    public abstract PowerSaveState getLowPowerState(int i);

    public abstract boolean interceptPowerKeyDown(KeyEvent keyEvent);

    public abstract boolean isAmbientDisplaySuppressed();

    public abstract boolean isInternalDisplayOff();

    public abstract boolean isProximityPositive();

    public abstract void nap(long j, boolean z);

    public abstract void registerLowPowerModeObserver(LowPowerModeListener lowPowerModeListener);

    public abstract void registerUserActivityStateListener(UserActivityStateListener userActivityStateListener);

    public abstract boolean setDeviceIdleMode(boolean z);

    public abstract void setDeviceIdleTempWhitelist(int[] iArr);

    public abstract void setDeviceIdleWhitelist(int[] iArr);

    public abstract void setDevicePostured(boolean z);

    public abstract void setDozeOverrideFromDreamManager(int i, int i2, float f, int i3, boolean z);

    public abstract void setDozeOverrideFromDreamManager(int i, int i2, float f, int i3, boolean z, int i4, boolean z2);

    public abstract void setDrawWakeLockOverrideFromSidekick(boolean z);

    public abstract void setGoToSleepPrevention(boolean z);

    public abstract void setHbmBlock(boolean z);

    public abstract boolean setLightDeviceIdleMode(boolean z);

    public abstract void setLowPowerStandbyActive(boolean z);

    public abstract void setLowPowerStandbyAllowlist(int[] iArr);

    public abstract void setMaximumScreenOffTimeoutFromDeviceAdmin(int i, long j);

    public abstract void setMaximumScreenOffTimeoutFromKnox(int i, long j);

    public abstract void setPowerBoost(int i, int i2);

    public abstract void setPowerMode(int i, boolean z);

    public abstract void setScreenDimDurationOverrideFromSqd(boolean z);

    public abstract void setScreenDimDurationOverrideFromWindowManager(long j);

    public abstract void setUserActivityTimeoutForDexOverrideFromWindowManager(long j);

    public abstract void setUserActivityTimeoutOverrideFromWindowManager(long j);

    public abstract void setUserInactiveOverrideFromWindowManager();

    public abstract void setWakeLockBlackListEnableDisable(int i, boolean z);

    public abstract int setWakeLockEnableDisable(int i, boolean z);

    public abstract void startUidChanges();

    public abstract void uidActive(int i);

    public abstract void uidGone(int i);

    public abstract void uidIdle(int i);

    public abstract void unregisterUserActivityStateListener(UserActivityStateListener userActivityStateListener);

    public abstract void updateDualViewMode(boolean z);

    public abstract void updateProfileActivityTimeFromKnox(int i, long j);

    public abstract void updateSettings();

    public abstract void updateUidProcState(int i, int i2);

    public abstract boolean wasDeviceIdleFor(long j);

    public static String wakefulnessToString(int i) {
        if (i == 0) {
            return "Asleep";
        }
        if (i == 1) {
            return "Awake";
        }
        if (i == 2) {
            return "Dreaming";
        }
        if (i == 3) {
            return "Dozing";
        }
        return Integer.toString(i);
    }

    public void registerLowPowerModeObserver(final int i, final Consumer<PowerSaveState> consumer) {
        registerLowPowerModeObserver(new LowPowerModeListener(this) { // from class: android.os.PowerManagerInternal.1
            @Override // android.os.PowerManagerInternal.LowPowerModeListener
            public int getServiceType() {
                return i;
            }

            @Override // android.os.PowerManagerInternal.LowPowerModeListener
            public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                consumer.accept(powerSaveState);
            }
        });
    }
}
