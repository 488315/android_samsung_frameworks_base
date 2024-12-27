package com.android.server;

import android.provider.DeviceConfig;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda6
        implements FunctionalUtils.ThrowingSupplier {
    public final Object getOrThrow() {
        return Integer.valueOf(
                DeviceConfig.getInt(
                        "telephony", "phone_state_listener_per_pid_registration_limit", 50));
    }
}
