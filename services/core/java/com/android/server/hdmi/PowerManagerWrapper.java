package com.android.server.hdmi;

import android.content.Context;
import android.os.PowerManager;

public final class PowerManagerWrapper {
    public final PowerManager mPowerManager;

    public final class DefaultWakeLockWrapper {
        public final PowerManager.WakeLock mWakeLock;

        public DefaultWakeLockWrapper(PowerManager.WakeLock wakeLock) {
            this.mWakeLock = wakeLock;
        }
    }

    public PowerManagerWrapper(Context context) {
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    }
}
