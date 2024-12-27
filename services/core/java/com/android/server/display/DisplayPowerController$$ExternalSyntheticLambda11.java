package com.android.server.display;

import android.os.SystemClock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda11
        implements DisplayPowerController.Clock {
    public final /* synthetic */ int $r8$classId;

    public final long uptimeMillis() {
        int i = this.$r8$classId;
        return SystemClock.uptimeMillis();
    }
}
