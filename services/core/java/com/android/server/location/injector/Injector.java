package com.android.server.location.injector;

import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.nsflp.NSPermissionHelper;
import com.android.server.location.settings.LocationSettings;

/* loaded from: classes2.dex */
public interface Injector {
  AlarmHelper getAlarmHelper();

  AppForegroundHelper getAppForegroundHelper();

  AppOpsHelper getAppOpsHelper();

  DeviceIdleHelper getDeviceIdleHelper();

  DeviceStationaryHelper getDeviceStationaryHelper();

  EmergencyHelper getEmergencyHelper();

  LocationPermissionsHelper getLocationPermissionsHelper();

  LocationPowerSaveModeHelper getLocationPowerSaveModeHelper();

  LocationSettings getLocationSettings();

  LocationUsageLogger getLocationUsageLogger();

  NSConnectionHelper getNSConnectionHelper();

  NSLocationProviderHelper getNSLocationProviderHelper();

  NSPermissionHelper getNSPermissionHelper();

  PackageResetHelper getPackageResetHelper();

  ScreenInteractiveHelper getScreenInteractiveHelper();

  SettingsHelper getSettingsHelper();

  UserInfoHelper getUserInfoHelper();
}
