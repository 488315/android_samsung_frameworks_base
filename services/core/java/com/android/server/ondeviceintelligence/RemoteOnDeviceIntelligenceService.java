package com.android.server.ondeviceintelligence;

import android.provider.Settings;

import com.android.internal.infra.ServiceConnector;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class RemoteOnDeviceIntelligenceService extends ServiceConnector.Impl {
    public static final long LONG_TIMEOUT = TimeUnit.HOURS.toMillis(4);

    public final long getAutoDisconnectTimeoutMs() {
        return Settings.Secure.getLongForUser(
                ((ServiceConnector.Impl) this).mContext.getContentResolver(),
                "on_device_intelligence_unbind_timeout_ms",
                TimeUnit.SECONDS.toMillis(30L),
                ((ServiceConnector.Impl) this).mContext.getUserId());
    }

    public final long getRequestTimeoutMs() {
        return LONG_TIMEOUT;
    }
}
