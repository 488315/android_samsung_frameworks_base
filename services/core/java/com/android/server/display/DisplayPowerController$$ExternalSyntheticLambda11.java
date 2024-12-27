package com.android.server.display;

import android.os.SystemClock;

public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda11
        implements DisplayPowerController.Clock {
    public final /* synthetic */ int $r8$classId;

    public final long uptimeMillis() {
        int i = this.$r8$classId;
        return SystemClock.uptimeMillis();
    }
}
