package com.android.server.om;

import java.util.function.ToIntFunction;

public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda2
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((OverlayManagerSettings.SettingsItem) obj).mUserId;
    }
}
