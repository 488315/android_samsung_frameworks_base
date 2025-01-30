package com.android.server.timezonedetector;

/* loaded from: classes3.dex */
public interface TimeZoneDetectorInternal {
  MetricsTimeZoneDetectorState generateMetricsState();

  void handleLocationAlgorithmEvent(LocationAlgorithmEvent locationAlgorithmEvent);
}
