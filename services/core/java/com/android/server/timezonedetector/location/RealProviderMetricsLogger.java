package com.android.server.timezonedetector.location;

import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes3.dex */
public final class RealProviderMetricsLogger
    implements LocationTimeZoneProvider.ProviderMetricsLogger {
  public final int mProviderIndex;

  public static int metricsProviderState(int i) {
    switch (i) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      case 5:
        return 5;
      case 6:
        return 6;
      default:
        return 0;
    }
  }

  public RealProviderMetricsLogger(int i) {
    this.mProviderIndex = i;
  }

  @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderMetricsLogger
  public void onProviderStateChanged(int i) {
    FrameworkStatsLog.write(
        FrameworkStatsLog.LOCATION_TIME_ZONE_PROVIDER_STATE_CHANGED,
        this.mProviderIndex,
        metricsProviderState(i));
  }
}
