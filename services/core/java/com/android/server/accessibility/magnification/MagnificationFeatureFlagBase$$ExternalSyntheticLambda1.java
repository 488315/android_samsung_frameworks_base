package com.android.server.accessibility.magnification;

import android.provider.DeviceConfig;

import java.util.concurrent.Executor;

public final /* synthetic */ class MagnificationFeatureFlagBase$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ MagnificationFeatureFlagBase f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ DeviceConfig.OnPropertiesChangedListener f$2;

    public /* synthetic */ MagnificationFeatureFlagBase$$ExternalSyntheticLambda1(
            MagnificationFeatureFlagBase magnificationFeatureFlagBase,
            Executor executor,
            MagnificationFeatureFlagBase$$ExternalSyntheticLambda0
                    magnificationFeatureFlagBase$$ExternalSyntheticLambda0) {
        this.f$0 = magnificationFeatureFlagBase;
        this.f$1 = executor;
        this.f$2 = magnificationFeatureFlagBase$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MagnificationFeatureFlagBase magnificationFeatureFlagBase = this.f$0;
        DeviceConfig.addOnPropertiesChangedListener(
                magnificationFeatureFlagBase.getNamespace(), this.f$1, this.f$2);
    }
}
