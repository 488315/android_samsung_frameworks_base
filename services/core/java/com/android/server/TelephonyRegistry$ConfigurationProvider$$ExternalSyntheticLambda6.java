package com.android.server;

import android.provider.DeviceConfig;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda6
        implements FunctionalUtils.ThrowingSupplier {
    public final Object getOrThrow() {
        return Integer.valueOf(
                DeviceConfig.getInt(
                        "telephony", "phone_state_listener_per_pid_registration_limit", 50));
    }
}
