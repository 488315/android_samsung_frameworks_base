package com.android.server.location.provider;

import android.location.Location;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Bundle;

import com.android.internal.util.ConcurrentUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MockLocationProvider extends AbstractLocationProvider {
    public Location mLocation;

    public MockLocationProvider(
            ProviderProperties providerProperties, CallerIdentity callerIdentity, Set set) {
        super(ConcurrentUtils.DIRECT_EXECUTOR, callerIdentity, providerProperties, set);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("last mock location=" + this.mLocation);
    }

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
