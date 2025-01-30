package com.android.server.location.provider;

import android.content.Context;
import android.location.LocationResult;
import android.location.provider.ProviderRequest;
import android.os.Binder;
import com.android.internal.util.Preconditions;
import com.android.server.location.injector.Injector;
import java.util.Collection;

/* loaded from: classes2.dex */
public class PassiveLocationProviderManager extends LocationProviderManager {
  @Override // com.android.server.location.provider.LocationProviderManager
  public long calculateRequestDelayMillis(long j, Collection collection) {
    return 0L;
  }

  public PassiveLocationProviderManager(Context context, Injector injector) {
    super(context, injector, "passive", null);
  }

  @Override // com.android.server.location.provider.LocationProviderManager
  public void setRealProvider(AbstractLocationProvider abstractLocationProvider) {
    Preconditions.checkArgument(abstractLocationProvider instanceof PassiveLocationProvider);
    super.setRealProvider(abstractLocationProvider);
  }

  @Override // com.android.server.location.provider.LocationProviderManager
  public void setMockProvider(MockLocationProvider mockLocationProvider) {
    if (mockLocationProvider != null) {
      throw new IllegalArgumentException("Cannot mock the passive provider");
    }
  }

  public void updateLocation(LocationResult locationResult) {
    synchronized (this.mMultiplexerLock) {
      PassiveLocationProvider passiveLocationProvider =
          (PassiveLocationProvider) this.mProvider.getProvider();
      Preconditions.checkState(passiveLocationProvider != null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        passiveLocationProvider.updateLocation(locationResult);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  @Override // com.android.server.location.provider.LocationProviderManager,
            // com.android.server.location.listeners.ListenerMultiplexer
  public ProviderRequest mergeRegistrations(Collection collection) {
    return new ProviderRequest.Builder().setIntervalMillis(0L).build();
  }

  @Override // com.android.server.location.provider.LocationProviderManager,
            // com.android.server.location.listeners.ListenerMultiplexer
  public String getServiceState() {
    return this.mProvider.getCurrentRequest().isActive() ? "registered" : "unregistered";
  }
}
