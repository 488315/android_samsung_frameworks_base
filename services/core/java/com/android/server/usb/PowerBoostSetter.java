package com.android.server.usb;

import android.os.PowerManagerInternal;
import android.util.Log;

import com.android.server.LocalServices;

import java.time.Instant;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerBoostSetter {
    public PowerManagerInternal mPowerManagerInternal;
    public Instant mPreviousTimeout = null;

    public PowerBoostSetter() {
        this.mPowerManagerInternal = null;
        this.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
    }

    public final void boostPower() {
        if (this.mPowerManagerInternal == null) {
            this.mPowerManagerInternal =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        }
        if (this.mPowerManagerInternal == null) {
            Log.w("PowerBoostSetter", "PowerManagerInternal null");
        } else if (this.mPreviousTimeout == null
                || Instant.now().isAfter(this.mPreviousTimeout.plusMillis(7500L))) {
            this.mPreviousTimeout = Instant.now();
            this.mPowerManagerInternal.setPowerBoost(0, 15000);
        }
    }
}
