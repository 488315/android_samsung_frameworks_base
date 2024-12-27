package com.android.server.pm;

import android.provider.DeviceConfig;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda66
        implements FunctionalUtils.ThrowingSupplier {
    public final Object getOrThrow() {
        return Long.valueOf(
                DeviceConfig.getLong(
                        "package_manager_service",
                        "deferred_no_kill_post_delete_delay_ms_extended",
                        PackageManagerService.DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED));
    }
}
