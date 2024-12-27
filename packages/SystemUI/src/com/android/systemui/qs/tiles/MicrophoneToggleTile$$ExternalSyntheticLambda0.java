package com.android.systemui.qs.tiles;

import android.provider.DeviceConfig;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MicrophoneToggleTile$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Boolean.valueOf(DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true));
    }
}
