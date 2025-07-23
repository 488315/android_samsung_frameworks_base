package com.android.internal.os;

import android.util.ArrayMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class RpmStats {
    public Map<String, PowerStatePlatformSleepState> mPlatformLowPowerStats = new ArrayMap();
    public Map<String, PowerStateSubsystem> mSubsystemLowPowerStats = new ArrayMap();

    public PowerStatePlatformSleepState getAndUpdatePlatformState(String str, long j, int i) {
        PowerStatePlatformSleepState powerStatePlatformSleepState = this.mPlatformLowPowerStats.get(str);
        if (powerStatePlatformSleepState == null) {
            powerStatePlatformSleepState = new PowerStatePlatformSleepState();
            this.mPlatformLowPowerStats.put(str, powerStatePlatformSleepState);
        }
        powerStatePlatformSleepState.mTimeMs = j;
        powerStatePlatformSleepState.mCount = i;
        return powerStatePlatformSleepState;
    }

    public PowerStateSubsystem getSubsystem(String str) {
        PowerStateSubsystem powerStateSubsystem = this.mSubsystemLowPowerStats.get(str);
        if (powerStateSubsystem != null) {
            return powerStateSubsystem;
        }
        PowerStateSubsystem powerStateSubsystem2 = new PowerStateSubsystem();
        this.mSubsystemLowPowerStats.put(str, powerStateSubsystem2);
        return powerStateSubsystem2;
    }

    public static class PowerStateElement {
        public int mCount;
        public long mTimeMs;

        private PowerStateElement(long j, int i) {
            this.mTimeMs = j;
            this.mCount = i;
        }
    }

    public static class PowerStatePlatformSleepState {
        public int mCount;
        public long mTimeMs;
        public Map<String, PowerStateElement> mVoters = new ArrayMap();

        public void putVoter(String str, long j, int i) {
            PowerStateElement powerStateElement = this.mVoters.get(str);
            if (powerStateElement == null) {
                this.mVoters.put(str, new PowerStateElement(j, i));
            } else {
                powerStateElement.mTimeMs = j;
                powerStateElement.mCount = i;
            }
        }
    }

    public static class PowerStateSubsystem {
        public Map<String, PowerStateElement> mStates = new ArrayMap();

        public void putState(String str, long j, int i) {
            PowerStateElement powerStateElement = this.mStates.get(str);
            if (powerStateElement == null) {
                this.mStates.put(str, new PowerStateElement(j, i));
            } else {
                powerStateElement.mTimeMs = j;
                powerStateElement.mCount = i;
            }
        }
    }
}
