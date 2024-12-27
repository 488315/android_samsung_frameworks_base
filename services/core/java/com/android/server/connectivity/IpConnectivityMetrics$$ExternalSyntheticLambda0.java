package com.android.server.connectivity;

import android.content.Context;
import android.provider.Settings;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
