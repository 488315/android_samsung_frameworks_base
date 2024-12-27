package com.android.systemui.qs.tiles;

import android.provider.DeviceConfig;
import java.util.function.Supplier;

public final /* synthetic */ class MicrophoneToggleTile$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Boolean.valueOf(DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true));
    }
}
