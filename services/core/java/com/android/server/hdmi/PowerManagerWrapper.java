package com.android.server.hdmi;

import android.content.Context;
import android.os.PowerManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PowerManagerWrapper {
    public final PowerManager mPowerManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
