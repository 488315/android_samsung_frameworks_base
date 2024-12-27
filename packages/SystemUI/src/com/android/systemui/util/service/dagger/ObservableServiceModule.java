package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import com.android.systemui.R;

public class ObservableServiceModule {
    public static final String BASE_RECONNECT_DELAY_MS = "base_reconnect_attempts";
    public static final String DUMPSYS_NAME = "dumpsys_name";
    public static final String MAX_RECONNECT_ATTEMPTS = "max_reconnect_attempts";
    public static final String MIN_CONNECTION_DURATION_MS = "min_connection_duration_ms";
    public static final String OBSERVER = "observer";
    public static final String SERVICE_CONNECTION = "service_connection";

    public static int provideBaseReconnectDelayMs(Resources resources) {
        return resources.getInteger(R.integer.config_communalSourceReconnectBaseDelay);
    }

    public static int providesMaxReconnectAttempts(Resources resources) {
        return resources.getInteger(R.integer.config_communalSourceMaxReconnectAttempts);
    }

    public static int providesMinConnectionDuration(Resources resources) {
        return resources.getInteger(R.integer.config_connectionMinDuration);
    }
}
