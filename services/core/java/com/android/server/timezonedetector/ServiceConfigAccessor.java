package com.android.server.timezonedetector;

import android.app.time.TimeZoneConfiguration;
import java.time.Duration;

/* loaded from: classes3.dex */
public interface ServiceConfigAccessor {
  void addConfigurationInternalChangeListener(StateChangeListener stateChangeListener);

  void addLocationTimeZoneManagerConfigListener(StateChangeListener stateChangeListener);

  ConfigurationInternal getConfigurationInternal(int i);

  ConfigurationInternal getCurrentUserConfigurationInternal();

  Duration getLocationTimeZoneProviderEventFilteringAgeThreshold();

  Duration getLocationTimeZoneProviderInitializationTimeout();

  Duration getLocationTimeZoneProviderInitializationTimeoutFuzz();

  Duration getLocationTimeZoneUncertaintyDelay();

  String getPrimaryLocationTimeZoneProviderMode();

  String getPrimaryLocationTimeZoneProviderPackageName();

  boolean getRecordStateChangesForTests();

  String getSecondaryLocationTimeZoneProviderMode();

  String getSecondaryLocationTimeZoneProviderPackageName();

  boolean isGeoTimeZoneDetectionFeatureSupported();

  boolean isGeoTimeZoneDetectionFeatureSupportedInConfig();

  boolean isGeolocationFallbackEnabled();

  boolean isTestPrimaryLocationTimeZoneProvider();

  boolean isTestSecondaryLocationTimeZoneProvider();

  void removeConfigurationInternalChangeListener(StateChangeListener stateChangeListener);

  void resetVolatileTestConfig();

  void setRecordStateChangesForTests(boolean z);

  void setTestPrimaryLocationTimeZoneProviderPackageName(String str);

  void setTestSecondaryLocationTimeZoneProviderPackageName(String str);

  boolean updateConfiguration(int i, TimeZoneConfiguration timeZoneConfiguration, boolean z);
}
