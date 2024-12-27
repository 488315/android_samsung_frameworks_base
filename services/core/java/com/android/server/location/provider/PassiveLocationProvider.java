package com.android.server.location.provider;

import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.os.Bundle;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class PassiveLocationProvider extends AbstractLocationProvider {
    public static final ProviderProperties PROPERTIES =
            new ProviderProperties.Builder().setPowerUsage(1).setAccuracy(1).build();

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {}

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onExtraCommand(int i, String str, Bundle bundle, int i2) {}

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onFlush(
            LocationProviderManager$Registration$$ExternalSyntheticLambda0
                    locationProviderManager$Registration$$ExternalSyntheticLambda0) {
        locationProviderManager$Registration$$ExternalSyntheticLambda0.run();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onSetRequest(ProviderRequest providerRequest) {}
}
