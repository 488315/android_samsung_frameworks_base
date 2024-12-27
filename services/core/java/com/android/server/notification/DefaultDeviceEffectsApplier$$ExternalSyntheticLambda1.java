package com.android.server.notification;

import android.util.Slog;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DefaultDeviceEffectsApplier$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DefaultDeviceEffectsApplier f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ DefaultDeviceEffectsApplier$$ExternalSyntheticLambda1(
            DefaultDeviceEffectsApplier defaultDeviceEffectsApplier, boolean z) {
        this.f$0 = defaultDeviceEffectsApplier;
        this.f$1 = z;
    }

    public final void runOrThrow() {
        DefaultDeviceEffectsApplier defaultDeviceEffectsApplier = this.f$0;
        boolean z = this.f$1;
        defaultDeviceEffectsApplier.getClass();
        try {
            defaultDeviceEffectsApplier.mUiModeManager.setAttentionModeThemeOverlay(
                    z ? 1001 : 1000);
        } catch (Exception e) {
            Slog.e("DeviceEffectsApplier", "Could not change wallpaper override", e);
        }
    }
}
