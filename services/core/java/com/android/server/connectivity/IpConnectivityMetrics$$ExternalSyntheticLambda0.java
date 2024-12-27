package com.android.server.connectivity;

import android.content.Context;
import android.provider.Settings;

import java.util.function.ToIntFunction;

public final /* synthetic */ class IpConnectivityMetrics$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        int i =
                Settings.Global.getInt(
                        ((Context) obj).getContentResolver(),
                        "connectivity_metrics_buffer_size",
                        2000);
        if (i <= 0) {
            return 2000;
        }
        return Math.min(i, 20000);
    }
}
