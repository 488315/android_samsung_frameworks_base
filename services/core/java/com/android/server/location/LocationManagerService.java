package com.android.server.location;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Criteria;
import android.location.GeocoderParams;
import android.location.Geofence;
import android.location.GnssCapabilities;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementRequest;
import android.location.IGeocodeListener;
import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssNmeaListener;
import android.location.IGnssStatusListener;
import android.location.IGpsGeofenceHardware;
import android.location.ILocationCallback;
import android.location.ILocationListener;
import android.location.ILocationManager;
import android.location.LastLocationRequest;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationManagerInternal;
import android.location.LocationProvider;
import android.location.LocationRequest;
import android.location.LocationTime;
import android.location.provider.IProviderRequestListener;
import android.location.provider.ProviderProperties;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.Message;
import android.os.PackageTagsList;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WorkSource;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.location.LocationManagerService;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.geofence.GeofenceManager;
import com.android.server.location.geofence.GeofenceProxy;
import com.android.server.location.gnss.GnssConfiguration;
import com.android.server.location.gnss.GnssManagerService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssHalStatus;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.injector.AlarmHelper;
import com.android.server.location.injector.AppForegroundHelper;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.injector.DeviceIdleHelper;
import com.android.server.location.injector.DeviceStationaryHelper;
import com.android.server.location.injector.EmergencyHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper;
import com.android.server.location.injector.LocationPowerSaveModeHelper;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.PackageResetHelper;
import com.android.server.location.injector.ScreenInteractiveHelper;
import com.android.server.location.injector.SettingsHelper;
import com.android.server.location.injector.SystemAlarmHelper;
import com.android.server.location.injector.SystemAppForegroundHelper;
import com.android.server.location.injector.SystemAppOpsHelper;
import com.android.server.location.injector.SystemDeviceIdleHelper;
import com.android.server.location.injector.SystemDeviceStationaryHelper;
import com.android.server.location.injector.SystemEmergencyHelper;
import com.android.server.location.injector.SystemLocationPermissionsHelper;
import com.android.server.location.injector.SystemLocationPowerSaveModeHelper;
import com.android.server.location.injector.SystemPackageResetHelper;
import com.android.server.location.injector.SystemScreenInteractiveHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.injector.SystemUserInfoHelper;
import com.android.server.location.injector.UserInfoHelper;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSLocationMonitor;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.nsflp.NSPermissionHelper;
import com.android.server.location.provider.AbstractLocationProvider;
import com.android.server.location.provider.LocationProviderManager;
import com.android.server.location.provider.MockLocationProvider;
import com.android.server.location.provider.PassiveLocationProvider;
import com.android.server.location.provider.PassiveLocationProviderManager;
import com.android.server.location.provider.StationaryThrottlingLocationProvider;
import com.android.server.location.provider.proxy.ProxyLocationProvider;
import com.android.server.location.settings.LocationSettings;
import com.android.server.location.settings.LocationUserSettings;
import com.android.server.pm.permission.LegacyPermissionManagerInternal;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.location.ISLocationLMSHook;
import com.samsung.android.location.ISLocationManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class LocationManagerService extends ILocationManager.Stub implements LocationProviderManager.StateChangedListener {
    public static ISLocationManager mISLocationManager;
    public ActivityManager mActivityManager;
    public CarrierConfig mCarrierConfig;
    public final Context mContext;
    public ILocationListener mDeprecatedGnssBatchingListener;
    public String mExtraLocationControllerPackage;
    public boolean mExtraLocationControllerPackageEnabled;
    public GeocoderProxy mGeocodeProvider;
    public final GeofenceManager mGeofenceManager;
    public final Injector mInjector;
    public final LocalService mLocalService;
    public LocationManagerInternal.LocationPackageTagsListener mLocationTagsChangedListener;
    public final NSLocationMonitor mNSLocationMonitor;
    public PackageManager mPackageManager;
    public final PassiveLocationProviderManager mPassiveManager;
    public final Object mLock = new Object();
    public volatile GnssManagerService mGnssManagerService = null;
    public final Object mDeprecatedGnssBatchingLock = new Object();
    public final CopyOnWriteArrayList mProviderManagers = new CopyOnWriteArrayList();
    public final HashMap mMockLocationRecord = new HashMap();
    public boolean isMockLocationNotified = false;
    public final ProcessListener mProcessListener = new ProcessListener();
    public final Handler mHandler = LocationServiceThread.getHandler();

    public class Lifecycle extends SystemService {
        public final LocationManagerService mService;
        public final SystemInjector mSystemInjector;
        public final LifecycleUserInfoHelper mUserInfoHelper;

        public Lifecycle(Context context) {
            super(context);
            LifecycleUserInfoHelper lifecycleUserInfoHelper = new LifecycleUserInfoHelper(context);
            this.mUserInfoHelper = lifecycleUserInfoHelper;
            SystemInjector systemInjector = new SystemInjector(context, lifecycleUserInfoHelper);
            this.mSystemInjector = systemInjector;
            this.mService = new LocationManagerService(context, systemInjector);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("location", this.mService);
            LocationManager.invalidateLocalLocationEnabledCaches();
            LocationManager.disableLocalLocationEnabledCaches();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mSystemInjector.onSystemReady();
                this.mService.onSystemReady();
            } else if (i == 600) {
                this.mService.onSystemThirdPartyAppsCanStart();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            this.mUserInfoHelper.onUserStarted(targetUser.getUserIdentifier());
            this.mService.logLocationEnabledState();
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            this.mUserInfoHelper.onCurrentUserChanged(targetUser.getUserIdentifier(), targetUser2.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(SystemService.TargetUser targetUser) {
            this.mUserInfoHelper.onUserStopped(targetUser.getUserIdentifier());
        }

        public class LifecycleUserInfoHelper extends SystemUserInfoHelper {
            public LifecycleUserInfoHelper(Context context) {
                super(context);
            }

            public void onUserStarted(int i) {
                dispatchOnUserStarted(i);
            }

            public void onUserStopped(int i) {
                dispatchOnUserStopped(i);
            }

            public void onCurrentUserChanged(int i, int i2) {
                dispatchOnCurrentUserChanged(i, i2);
            }
        }
    }

    public LocationManagerService(Context context, Injector injector) {
        int i;
        this.mContext = context.createAttributionContext("LocationService");
        this.mInjector = injector;
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        LocalServices.addService(LocationManagerInternal.class, localService);
        try {
            i = Integer.parseInt(SystemProperties.get("persist.sys.gps.dds.subId", "0"));
        } catch (NumberFormatException unused) {
            Log.w("LocationManagerService", "Sim slot property has wrong value, set 0");
            i = 0;
        }
        String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_GPS_ConfigAgpsSetting", "", false);
        string = (string == null || string.length() == 0) ? SystemProperties.get("ro.csc.sales_code") : string;
        CarrierConfig carrierConfig = CarrierConfig.getInstance();
        this.mCarrierConfig = carrierConfig;
        carrierConfig.setSalesCode(string);
        this.mGeofenceManager = new GeofenceManager(this.mContext, injector);
        this.mInjector.getLocationSettings().registerLocationUserSettingsListener(new LocationSettings.LocationUserSettingsListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda0
            @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
            public final void onLocationUserSettingsChanged(int i2, LocationUserSettings locationUserSettings, LocationUserSettings locationUserSettings2) {
                LocationManagerService.this.onLocationUserSettingsChanged(i2, locationUserSettings, locationUserSettings2);
            }
        });
        this.mInjector.getSettingsHelper().addOnLocationEnabledChangedListener(new SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda1
            @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
            public final void onSettingChanged(int i2) {
                LocationManagerService.this.onLocationModeChanged(i2);
            }
        });
        this.mInjector.getSettingsHelper().addAdasAllowlistChangedListener(new SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda2
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                LocationManagerService.this.lambda$new$0();
            }
        });
        this.mInjector.getSettingsHelper().addIgnoreSettingsAllowlistChangedListener(new SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda3
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                LocationManagerService.this.lambda$new$1();
            }
        });
        this.mInjector.getUserInfoHelper().addListener(new UserInfoHelper.UserListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda4
            @Override // com.android.server.location.injector.UserInfoHelper.UserListener
            public final void onUserChanged(int i2, int i3) {
                LocationManagerService.this.lambda$new$2(i2, i3);
            }
        });
        PassiveLocationProviderManager passiveLocationProviderManager = new PassiveLocationProviderManager(this.mContext, injector);
        this.mPassiveManager = passiveLocationProviderManager;
        addLocationProviderManager(passiveLocationProviderManager, new PassiveLocationProvider(this.mContext));
        LegacyPermissionManagerInternal legacyPermissionManagerInternal = (LegacyPermissionManagerInternal) LocalServices.getService(LegacyPermissionManagerInternal.class);
        if (this.mCarrierConfig.isChinaCarrier()) {
            legacyPermissionManagerInternal.setLocationPackagesProvider(new LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda5
                @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
                public final String[] getPackages(int i2) {
                    String[] lambda$new$3;
                    lambda$new$3 = LocationManagerService.this.lambda$new$3(i2);
                    return lambda$new$3;
                }
            });
        } else {
            legacyPermissionManagerInternal.setLocationPackagesProvider(new LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda6
                @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
                public final String[] getPackages(int i2) {
                    String[] lambda$new$4;
                    lambda$new$4 = LocationManagerService.this.lambda$new$4(i2);
                    return lambda$new$4;
                }
            });
        }
        legacyPermissionManagerInternal.setLocationExtraPackagesProvider(new LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda7
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final String[] getPackages(int i2) {
                String[] lambda$new$5;
                lambda$new$5 = LocationManagerService.this.lambda$new$5(i2);
                return lambda$new$5;
            }
        });
        this.mNSLocationMonitor = new NSLocationMonitor(this.mContext, injector);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        refreshAppOpsRestrictions(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        refreshAppOpsRestrictions(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(int i, int i2) {
        if (i2 == 2) {
            refreshAppOpsRestrictions(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String[] lambda$new$3(int i) {
        return this.mContext.getResources().getStringArray(R.array.vendor_disallowed_apps_managed_profile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String[] lambda$new$4(int i) {
        return this.mContext.getResources().getStringArray(R.array.vendor_disallowed_apps_managed_device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String[] lambda$new$5(int i) {
        return this.mContext.getResources().getStringArray(R.array.vendor_cross_profile_apps);
    }

    public LocationProviderManager getLocationProviderManager(String str) {
        if (str == null) {
            return null;
        }
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (str.equals(locationProviderManager.getName())) {
                if (locationProviderManager.isVisibleToCaller()) {
                    return locationProviderManager;
                }
                return null;
            }
        }
        return null;
    }

    public final LocationProviderManager getOrAddLocationProviderManager(String str) {
        synchronized (this.mProviderManagers) {
            Iterator it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
                if (str.equals(locationProviderManager.getName())) {
                    return locationProviderManager;
                }
            }
            LocationProviderManager locationProviderManager2 = new LocationProviderManager(this.mContext, this.mInjector, str, this.mPassiveManager);
            addLocationProviderManager(locationProviderManager2, null);
            return locationProviderManager2;
        }
    }

    public void addLocationProviderManager(LocationProviderManager locationProviderManager, AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mProviderManagers) {
            Preconditions.checkState(getLocationProviderManager(locationProviderManager.getName()) == null);
            locationProviderManager.startManager(this);
            if (abstractLocationProvider != null) {
                if (locationProviderManager != this.mPassiveManager) {
                    if (Settings.Global.getInt(this.mContext.getContentResolver(), "location_enable_stationary_throttle", 1) != 0) {
                        abstractLocationProvider = new StationaryThrottlingLocationProvider(locationProviderManager.getName(), this.mInjector, abstractLocationProvider);
                    }
                }
                locationProviderManager.setRealProvider(abstractLocationProvider);
            }
            this.mProviderManagers.add(locationProviderManager);
        }
    }

    public final void removeLocationProviderManager(LocationProviderManager locationProviderManager) {
        synchronized (this.mProviderManagers) {
            Preconditions.checkArgument(this.mProviderManagers.remove(locationProviderManager));
            locationProviderManager.setMockProvider(null);
            locationProviderManager.setRealProvider(null);
            locationProviderManager.stopManager();
        }
    }

    public void onSystemReady() {
        if (Build.IS_DEBUGGABLE) {
            AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            Objects.requireNonNull(appOpsManager);
            appOpsManager.startWatchingNoted(new int[]{1, 0}, new AppOpsManager.OnOpNotedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda9
                public final void onOpNoted(String str, int i, String str2, String str3, int i2, int i3) {
                    LocationManagerService.this.lambda$onSystemReady$6(str, i, str2, str3, i2, i3);
                }
            });
        }
        this.mNSLocationMonitor.onSystemReady();
        this.mInjector.getAppOpsHelper().addMockLocationListener(new AppOpsHelper.MockLocationAppOpListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda10
            @Override // com.android.server.location.injector.AppOpsHelper.MockLocationAppOpListener
            public final void onMockLocationAppOpsChanged() {
                LocationManagerService.this.onMockLocationAppOpChanged();
            }
        });
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
        this.mPackageManager = this.mContext.getPackageManager();
        new PackageMonitor() { // from class: com.android.server.location.LocationManagerService.1
            public void onPackageDisappeared(String str, int i) {
                synchronized (LocationManagerService.this.mLock) {
                    LocationManagerService.this.onPackageRemovedLocked(str);
                }
            }
        }.register(this.mContext, (UserHandle) null, true, this.mHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.location.mock.delete");
        this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.location.LocationManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.samsung.android.location.mock.delete".equals(intent.getAction())) {
                    synchronized (LocationManagerService.this.mLock) {
                        LocationManagerService.this.isMockLocationNotified = false;
                        String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                        if (stringExtra != null && !LocationManagerService.this.mMockLocationRecord.isEmpty()) {
                            LocationManagerService.this.recoverRealProviderLocked(stringExtra);
                        }
                    }
                }
            }
        }, UserHandle.ALL, intentFilter, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$6(String str, int i, String str2, String str3, int i2, int i3) {
        if (isLocationEnabledForUser(UserHandle.getUserId(i))) {
            return;
        }
        Log.w("LocationManagerService", "location noteOp with location off - " + CallerIdentity.forTest(i, 0, str2, str3));
    }

    public void onSystemThirdPartyAppsCanStart() {
        GnssHalStatus gnssHalStatus;
        GnssHalStatus gnssHalStatus2;
        if (this.mCarrierConfig.isChinaCarrier()) {
            initializeProviders_chn();
        } else {
            ProxyLocationProvider create = ProxyLocationProvider.create(this.mContext, "network", "com.android.location.service.v3.NetworkLocationProvider", R.bool.config_earcFeatureEnabled_allowed, R.string.duration_years_shortest_future, this.mInjector);
            if (create != null) {
                addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "network", this.mPassiveManager), create);
            } else {
                Log.w("LocationManagerService", "no network location provider found");
            }
            Preconditions.checkState(!this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent("com.android.location.service.FusedLocationProvider"), 1572864, 0).isEmpty(), "Unable to find a direct boot aware fused location provider");
            ProxyLocationProvider create2 = ProxyLocationProvider.create(this.mContext, "fused", "com.android.location.service.FusedLocationProvider", R.bool.config_dreamsActivatedOnDockByDefault, R.string.display_manager_overlay_display_name, this.mInjector);
            if (create2 != null) {
                addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "fused", this.mPassiveManager), create2);
            } else {
                Log.wtf("LocationManagerService", "no fused location provider found");
            }
            GeocoderProxy createAndRegister = GeocoderProxy.createAndRegister(this.mContext, this.mInjector);
            this.mGeocodeProvider = createAndRegister;
            if (createAndRegister == null) {
                Log.e("LocationManagerService", "no geocoder provider found");
            }
        }
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.hardware.location");
        if (GnssVendorConfig.getInstance().isLsiGnss()) {
            gnssHalStatus = new GnssHalStatus();
            gnssHalStatus.triggerCheckingHalStatus(5000L);
        } else {
            gnssHalStatus = null;
        }
        boolean isSupported = GnssNative.isSupported();
        if (gnssHalStatus != null) {
            gnssHalStatus.updateHalStatusChecked(true);
        }
        if (hasSystemFeature && isSupported) {
            GnssNative create3 = GnssNative.create(this.mContext, this.mInjector, new GnssConfiguration(this.mContext));
            if (GnssVendorConfig.getInstance().isLsiGnss()) {
                gnssHalStatus2 = new GnssHalStatus();
                gnssHalStatus2.triggerCheckingHalStatus(15000L);
            } else {
                gnssHalStatus2 = null;
            }
            this.mGnssManagerService = new GnssManagerService(this.mContext, this.mInjector, create3);
            if (gnssHalStatus2 != null) {
                gnssHalStatus2.updateHalStatusChecked(true);
            }
            this.mGnssManagerService.onSystemReady();
            AbstractLocationProvider create4 = this.mContext.getResources().getBoolean(R.bool.config_supportSpeakerNearUltrasound) ? null : ProxyLocationProvider.create(this.mContext, "gps", "android.location.provider.action.GNSS_PROVIDER", R.bool.config_supportSpeakerNearUltrasound, R.string.done_accessibility_shortcut_menu_button, this.mInjector);
            if (create4 == null) {
                create4 = this.mGnssManagerService.getGnssLocationProvider();
            } else {
                addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "gps_hardware", this.mPassiveManager, Collections.singletonList("android.permission.LOCATION_HARDWARE")), this.mGnssManagerService.getGnssLocationProvider());
            }
            addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "gps", this.mPassiveManager), create4);
        }
        if (HardwareActivityRecognitionProxy.createAndRegister(this.mContext, this.mInjector) == null) {
            Log.e("LocationManagerService", "unable to bind ActivityRecognitionProxy");
        }
        if (this.mGnssManagerService != null && GeofenceProxy.createAndBind(this.mContext, this.mGnssManagerService.getGnssGeofenceProxy(), this.mInjector) == null) {
            Log.e("LocationManagerService", "unable to bind to GeofenceProxy");
        }
        for (String str : this.mContext.getResources().getStringArray(17236316)) {
            String[] split = str.split(",");
            getOrAddLocationProviderManager(split[0].trim()).setMockProvider(new MockLocationProvider(new ProviderProperties.Builder().setHasNetworkRequirement(Boolean.parseBoolean(split[1])).setHasSatelliteRequirement(Boolean.parseBoolean(split[2])).setHasCellRequirement(Boolean.parseBoolean(split[3])).setHasMonetaryCost(Boolean.parseBoolean(split[4])).setHasAltitudeSupport(Boolean.parseBoolean(split[5])).setHasSpeedSupport(Boolean.parseBoolean(split[6])).setHasBearingSupport(Boolean.parseBoolean(split[7])).setPowerUsage(Integer.parseInt(split[8])).setAccuracy(Integer.parseInt(split[9])).build(), CallerIdentity.fromContext(this.mContext), Collections.emptySet()));
        }
        setSLocation();
    }

    public final void initializeProviders_chn() {
        ProxyLocationProvider create = ProxyLocationProvider.create(this.mContext, "network", "com.android.location.service.v3.NetworkLocationProvider", R.bool.config_earcFeatureEnabled_default, R.string.dynamic_mode_notification_channel_name, this.mInjector);
        if (create != null) {
            addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "network", this.mPassiveManager), create);
        } else {
            Log.w("LocationManagerService", "no network location provider found");
        }
        Preconditions.checkState(!this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent("com.android.location.service.FusedLocationProvider"), 1572864, 0).isEmpty(), "Unable to find a direct boot aware fused location provider");
        ProxyLocationProvider create2 = ProxyLocationProvider.create(this.mContext, "fused", "com.android.location.service.FusedLocationProvider", R.bool.config_earcFeatureEnabled_default, R.string.display_manager_overlay_display_name, this.mInjector);
        if (create2 != null) {
            addLocationProviderManager(new LocationProviderManager(this.mContext, this.mInjector, "fused", this.mPassiveManager), create2);
        } else {
            Log.wtf("LocationManagerService", "no fused location provider found");
        }
        GeocoderProxy createAndRegister = GeocoderProxy.createAndRegister(this.mContext, R.bool.config_earcFeatureEnabled_default, R.string.display_manager_overlay_display_title, this.mInjector);
        this.mGeocodeProvider = createAndRegister;
        if (createAndRegister == null) {
            Log.e("LocationManagerService", "no geocoder provider found");
        }
        String string = this.mContext.getResources().getString(R.string.dynamic_mode_notification_channel_name);
        try {
            this.mInjector.getAppOpsHelper().setSystemAlertWindowOpIfNeed(string, this.mPackageManager.getPackageUid(string, 0));
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("LocationManagerService", "NameNotFoundException package: " + string);
        }
        if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 34) {
            DeviceConfig.setProperty("privacy", "location_indicators_small_enabled", "true", true);
        }
    }

    public final void setSLocation() {
        ISLocationManager asInterface = ISLocationManager.Stub.asInterface(ServiceManager.getService("sec_location"));
        if (asInterface == null) {
            Log.e("LocationManagerService", "sLocation is null");
            return;
        }
        mISLocationManager = asInterface;
        try {
            asInterface.setSLocationLMSHook(new ISLocationLMSHook.Stub() { // from class: com.android.server.location.LocationManagerService.3
                public boolean isProviderEnabledForUser(String str, int i) {
                    return LocationManagerService.this.isProviderEnabledForUser(str, i);
                }

                public IGpsGeofenceHardware getHWGeofence() {
                    if (LocationManagerService.this.mGnssManagerService == null) {
                        return null;
                    }
                    IGpsGeofenceHardware gnssGeofenceProxy = LocationManagerService.this.mGnssManagerService.getGnssGeofenceProxy();
                    LocationManagerService.this.mGnssManagerService.enableSLocation();
                    return gnssGeofenceProxy;
                }

                public void updateRequestInfo(boolean z, int i, int i2, String str, String str2, boolean z2, int i3) {
                    LocationManagerService.this.mInjector.getNSLocationProviderHelper().updateSLocationRequestInfo(z, i, i2, str, str2, z2, i3);
                }

                public boolean isUidForeground(int i) {
                    return LocationManagerService.this.mInjector.getAppForegroundHelper().isAppForeground(i);
                }
            });
        } catch (RemoteException e) {
            Log.e("LocationManagerService", e.toString());
        }
    }

    public final void onLocationUserSettingsChanged(int i, LocationUserSettings locationUserSettings, LocationUserSettings locationUserSettings2) {
        if (locationUserSettings.isAdasGnssLocationEnabled() != locationUserSettings2.isAdasGnssLocationEnabled()) {
            boolean isAdasGnssLocationEnabled = locationUserSettings2.isAdasGnssLocationEnabled();
            Log.d("LocationManagerService", "[u" + i + "] adas gnss location enabled = " + isAdasGnssLocationEnabled);
            LocationEventLog.EVENT_LOG.logAdasLocationEnabled(i, isAdasGnssLocationEnabled);
            this.mContext.sendBroadcastAsUser(new Intent("android.location.action.ADAS_GNSS_ENABLED_CHANGED").putExtra("android.location.extra.ADAS_GNSS_ENABLED", isAdasGnssLocationEnabled).addFlags(1073741824).addFlags(268435456), UserHandle.of(i));
        }
    }

    public final void onLocationModeChanged(int i) {
        boolean isLocationEnabled = this.mInjector.getSettingsHelper().isLocationEnabled(i);
        LocationManager.invalidateLocalLocationEnabledCaches();
        Log.d("LocationManagerService", "[u" + i + "] location enabled = " + isLocationEnabled);
        LocationEventLog.EVENT_LOG.logLocationEnabled(i, isLocationEnabled);
        logLocationEnabledState();
        this.mContext.sendBroadcastAsUser(new Intent("android.location.MODE_CHANGED").putExtra("android.location.extra.LOCATION_ENABLED", isLocationEnabled).addFlags(1073741824).addFlags(268435456), UserHandle.of(i));
        refreshAppOpsRestrictions(i);
    }

    public final void logLocationEnabledState() {
        boolean z = false;
        for (int i : this.mInjector.getUserInfoHelper().getRunningUserIds()) {
            z = this.mInjector.getSettingsHelper().isLocationEnabled(i);
            if (z) {
                break;
            }
        }
        this.mInjector.getLocationUsageLogger().logLocationEnabledStateChanged(z);
    }

    public int getGnssYearOfHardware() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.getGnssYearOfHardware();
    }

    public String getGnssHardwareModelName() {
        return this.mGnssManagerService == null ? "" : this.mGnssManagerService.getGnssHardwareModelName();
    }

    public int getGnssBatchSize() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.getGnssBatchSize();
    }

    public void startGnssBatch(long j, ILocationListener iLocationListener, String str, String str2, String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", null);
        if (this.mGnssManagerService == null) {
            return;
        }
        long millis = TimeUnit.NANOSECONDS.toMillis(j);
        synchronized (this.mDeprecatedGnssBatchingLock) {
            stopGnssBatch();
            registerLocationListener("gps", new LocationRequest.Builder(millis).setMaxUpdateDelayMillis(millis * this.mGnssManagerService.getGnssBatchSize()).setHiddenFromAppOps(true).build(), iLocationListener, str, str2, str3);
            this.mDeprecatedGnssBatchingListener = iLocationListener;
        }
    }

    public void flushGnssBatch() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", null);
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            ILocationListener iLocationListener = this.mDeprecatedGnssBatchingListener;
            if (iLocationListener != null) {
                requestListenerFlush("gps", iLocationListener, 0);
            }
        }
    }

    public void stopGnssBatch() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", null);
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            ILocationListener iLocationListener = this.mDeprecatedGnssBatchingListener;
            if (iLocationListener != null) {
                this.mDeprecatedGnssBatchingListener = null;
                unregisterLocationListener(iLocationListener);
            }
        }
    }

    public boolean hasProvider(String str) {
        return getLocationProviderManager(str) != null;
    }

    public List getAllProviders() {
        ArrayList arrayList = new ArrayList(this.mProviderManagers.size());
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (locationProviderManager.isVisibleToCaller()) {
                arrayList.add(locationProviderManager.getName());
            }
        }
        return arrayList;
    }

    public List getProviders(Criteria criteria, boolean z) {
        ArrayList arrayList;
        if (!LocationPermissions.checkCallingOrSelfLocationPermission(this.mContext, 1)) {
            return Collections.emptyList();
        }
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mProviderManagers.size());
            Iterator it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
                if (locationProviderManager.isVisibleToCaller()) {
                    String name = locationProviderManager.getName();
                    if (!z || locationProviderManager.isEnabled(UserHandle.getCallingUserId())) {
                        if (criteria == null || LocationProvider.propertiesMeetCriteria(name, locationProviderManager.getProperties(), criteria)) {
                            arrayList.add(name);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public String getBestProvider(Criteria criteria, boolean z) {
        List providers;
        synchronized (this.mLock) {
            providers = getProviders(criteria, z);
            if (providers.isEmpty()) {
                providers = getProviders(null, z);
            }
        }
        if (providers.isEmpty()) {
            return null;
        }
        return providers.contains("fused") ? "fused" : providers.contains("gps") ? "gps" : providers.contains("network") ? "network" : (String) providers.get(0);
    }

    public String[] getBackgroundThrottlingWhitelist() {
        return (String[]) this.mInjector.getSettingsHelper().getBackgroundThrottlePackageWhitelist().toArray(new String[0]);
    }

    public PackageTagsList getIgnoreSettingsAllowlist() {
        return this.mInjector.getSettingsHelper().getIgnoreSettingsAllowlist();
    }

    public PackageTagsList getAdasAllowlist() {
        return this.mInjector.getSettingsHelper().getAdasAllowlist();
    }

    public ICancellationSignal getCurrentLocation(String str, LocationRequest locationRequest, ILocationCallback iLocationCallback, String str2, String str3, String str4) {
        CallerIdentity fromBinder = CallerIdentity.fromBinder(this.mContext, str2, str3, str4);
        int permissionLevel = LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        Preconditions.checkState((fromBinder.getPid() == Process.myPid() && str3 == null) ? false : true);
        LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        return locationProviderManager.getCurrentLocation(validateLocationRequest, fromBinder, permissionLevel, iLocationCallback);
    }

    public void registerLocationListener(String str, LocationRequest locationRequest, ILocationListener iLocationListener, String str2, String str3, String str4) {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiBegin(3, Binder.getCallingUid(), Binder.getCallingPid());
        }
        CallerIdentity fromBinder = CallerIdentity.fromBinder(this.mContext, str2, str3, str4);
        int permissionLevel = LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        if (fromBinder.getPid() == Process.myPid() && str3 == null) {
            Log.w("LocationManagerService", "system location request with no attribution tag", new IllegalArgumentException());
        }
        LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        locationProviderManager.registerLocationRequest(validateLocationRequest, fromBinder, permissionLevel, iLocationListener);
    }

    public void registerLocationPendingIntent(String str, LocationRequest locationRequest, PendingIntent pendingIntent, String str2, String str3) {
        CallerIdentity fromBinder = CallerIdentity.fromBinder(this.mContext, str2, str3, AppOpsManager.toReceiverId(pendingIntent));
        int permissionLevel = LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        Preconditions.checkArgument((fromBinder.getPid() == Process.myPid() && str3 == null) ? false : true);
        if (CompatChanges.isChangeEnabled(169887240L, fromBinder.getUid())) {
            if (locationRequest.isLowPower() || locationRequest.isHiddenFromAppOps() || locationRequest.isLocationSettingsIgnored() || !locationRequest.getWorkSource().isEmpty()) {
                throw new SecurityException("PendingIntent location requests may not use system APIs: " + locationRequest);
            }
        }
        LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        locationProviderManager.registerLocationRequest(validateLocationRequest, fromBinder, permissionLevel, pendingIntent);
    }

    public final LocationRequest validateLocationRequest(String str, LocationRequest locationRequest, CallerIdentity callerIdentity) {
        if (!locationRequest.getWorkSource().isEmpty()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", "setting a work source requires android.permission.UPDATE_DEVICE_STATS");
        }
        LocationRequest.Builder builder = new LocationRequest.Builder(locationRequest);
        if (!CompatChanges.isChangeEnabled(168936375L, Binder.getCallingUid()) && this.mContext.checkCallingPermission("android.permission.LOCATION_HARDWARE") != 0) {
            builder.setLowPower(false);
        }
        WorkSource workSource = new WorkSource(locationRequest.getWorkSource());
        if (workSource.size() > 0 && workSource.getPackageName(0) == null) {
            Log.w("LocationManagerService", "received (and ignoring) illegal worksource with no package name");
            workSource.clear();
        } else {
            List workChains = workSource.getWorkChains();
            if (workChains != null && !workChains.isEmpty() && ((WorkSource.WorkChain) workChains.get(0)).getAttributionTag() == null) {
                Log.w("LocationManagerService", "received (and ignoring) illegal worksource with no attribution tag");
                workSource.clear();
            }
        }
        if (workSource.isEmpty()) {
            callerIdentity.addToWorkSource(workSource);
        }
        builder.setWorkSource(workSource);
        LocationRequest build = builder.build();
        boolean isProvider = this.mLocalService.isProvider(null, callerIdentity);
        if (build.isLowPower() && CompatChanges.isChangeEnabled(168936375L, callerIdentity.getUid())) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", "low power request requires android.permission.LOCATION_HARDWARE");
        }
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_APP_OPS_STATS", "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                throw new IllegalArgumentException("adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new IllegalArgumentException("adas gnss bypass requests are only allowed on the \"gps\" provider");
            }
            if (!isProvider) {
                LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
            }
        }
        if (build.isLocationSettingsIgnored() && !isProvider) {
            LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        }
        return build;
    }

    public void requestListenerFlush(String str, ILocationListener iLocationListener, int i) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        Objects.requireNonNull(iLocationListener);
        locationProviderManager.flush(iLocationListener, i);
    }

    public void requestPendingIntentFlush(String str, PendingIntent pendingIntent, int i) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        Objects.requireNonNull(pendingIntent);
        locationProviderManager.flush(pendingIntent, i);
    }

    public void unregisterLocationListener(ILocationListener iLocationListener) {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiEnd(3, Binder.getCallingUid(), Binder.getCallingPid());
        }
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            ((LocationProviderManager) it.next()).unregisterLocationRequest(iLocationListener);
        }
    }

    public void unregisterLocationPendingIntent(PendingIntent pendingIntent) {
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            ((LocationProviderManager) it.next()).unregisterLocationRequest(pendingIntent);
        }
    }

    public Location getLastLocation(String str, LastLocationRequest lastLocationRequest, String str2, String str3) {
        CallerIdentity fromBinder = CallerIdentity.fromBinder(this.mContext, str2, str3);
        int permissionLevel = LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        boolean z = true;
        LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        if (fromBinder.getPid() == Process.myPid() && str3 == null) {
            z = false;
        }
        Preconditions.checkArgument(z);
        LastLocationRequest validateLastLocationRequest = validateLastLocationRequest(str, lastLocationRequest, fromBinder);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            return null;
        }
        return locationProviderManager.getLastLocation(validateLastLocationRequest, fromBinder, permissionLevel);
    }

    public final LastLocationRequest validateLastLocationRequest(String str, LastLocationRequest lastLocationRequest, CallerIdentity callerIdentity) {
        LastLocationRequest build = new LastLocationRequest.Builder(lastLocationRequest).build();
        boolean isProvider = this.mLocalService.isProvider(null, callerIdentity);
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_APP_OPS_STATS", "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                throw new IllegalArgumentException("adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new IllegalArgumentException("adas gnss bypass requests are only allowed on the \"gps\" provider");
            }
            if (!isProvider) {
                LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
            }
        }
        if (build.isLocationSettingsIgnored() && !isProvider) {
            LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        }
        return build;
    }

    public LocationTime getGnssTimeMillis() {
        return this.mLocalService.getGnssTimeMillis();
    }

    public void injectLocation(Location location) {
        super.injectLocation_enforcePermission();
        Preconditions.checkArgument(location.isComplete());
        int callingUserId = UserHandle.getCallingUserId();
        LocationProviderManager locationProviderManager = getLocationProviderManager(location.getProvider());
        if (locationProviderManager == null || !locationProviderManager.isEnabled(callingUserId)) {
            return;
        }
        locationProviderManager.injectLastLocation(location, callingUserId);
    }

    public void requestGeofence(Geofence geofence, PendingIntent pendingIntent, String str, String str2) {
        this.mGeofenceManager.addGeofence(geofence, pendingIntent, str, str2);
    }

    public void removeGeofence(PendingIntent pendingIntent) {
        this.mGeofenceManager.removeGeofence(pendingIntent);
    }

    public void registerGnssStatusCallback(IGnssStatusListener iGnssStatusListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.registerGnssStatusCallback(iGnssStatusListener, str, str2, str3);
        }
    }

    public void unregisterGnssStatusCallback(IGnssStatusListener iGnssStatusListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.unregisterGnssStatusCallback(iGnssStatusListener);
        }
    }

    public void registerGnssNmeaCallback(IGnssNmeaListener iGnssNmeaListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.registerGnssNmeaCallback(iGnssNmeaListener, str, str2, str3);
        }
    }

    public void unregisterGnssNmeaCallback(IGnssNmeaListener iGnssNmeaListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.unregisterGnssNmeaCallback(iGnssNmeaListener);
        }
    }

    public void addGnssMeasurementsListener(GnssMeasurementRequest gnssMeasurementRequest, IGnssMeasurementsListener iGnssMeasurementsListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssMeasurementsListener(gnssMeasurementRequest, iGnssMeasurementsListener, str, str2, str3);
        }
    }

    public void removeGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssMeasurementsListener(iGnssMeasurementsListener);
        }
    }

    public void addGnssAntennaInfoListener(IGnssAntennaInfoListener iGnssAntennaInfoListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssAntennaInfoListener(iGnssAntennaInfoListener, str, str2, str3);
        }
    }

    public void removeGnssAntennaInfoListener(IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssAntennaInfoListener(iGnssAntennaInfoListener);
        }
    }

    public void addProviderRequestListener(IProviderRequestListener iProviderRequestListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", null);
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (locationProviderManager.isVisibleToCaller()) {
                locationProviderManager.addProviderRequestListener(iProviderRequestListener);
            }
        }
    }

    public void removeProviderRequestListener(IProviderRequestListener iProviderRequestListener) {
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            ((LocationProviderManager) it.next()).removeProviderRequestListener(iProviderRequestListener);
        }
    }

    public void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.injectGnssMeasurementCorrections(gnssMeasurementCorrections);
        }
    }

    public GnssCapabilities getGnssCapabilities() {
        return this.mGnssManagerService == null ? new GnssCapabilities.Builder().build() : this.mGnssManagerService.getGnssCapabilities();
    }

    public List getGnssAntennaInfos() {
        if (this.mGnssManagerService == null) {
            return null;
        }
        return this.mGnssManagerService.getGnssAntennaInfos();
    }

    public void addGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssNavigationMessageListener(iGnssNavigationMessageListener, str, str2, str3);
        }
    }

    public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssNavigationMessageListener(iGnssNavigationMessageListener);
        }
    }

    public void sendExtraCommand(String str, String str2, Bundle bundle) {
        LocationPermissions.enforceCallingOrSelfLocationPermission(this.mContext, 1);
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", null);
        Objects.requireNonNull(str);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager != null) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            Objects.requireNonNull(str2);
            locationProviderManager.sendExtraCommand(callingUid, callingPid, str2, bundle);
            this.mNSLocationMonitor.sendExtraCommandInfo(str, str2);
        }
        this.mInjector.getLocationUsageLogger().logLocationApiUsage(0, 5, str);
        this.mInjector.getLocationUsageLogger().logLocationApiUsage(1, 5, str);
    }

    public ProviderProperties getProviderProperties(String str) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        return locationProviderManager.getProperties();
    }

    public boolean isProviderPackage(String str, String str2, String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DEVICE_CONFIG", null);
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (str == null || str.equals(locationProviderManager.getName())) {
                CallerIdentity providerIdentity = locationProviderManager.getProviderIdentity();
                if (providerIdentity != null && providerIdentity.getPackageName().equals(str2) && (str3 == null || Objects.equals(providerIdentity.getAttributionTag(), str3))) {
                    return true;
                }
            }
        }
        return false;
    }

    public List getProviderPackages(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DEVICE_CONFIG", null);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            return Collections.emptyList();
        }
        CallerIdentity providerIdentity = locationProviderManager.getProviderIdentity();
        if (providerIdentity == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(providerIdentity.getPackageName());
    }

    public void setExtraLocationControllerPackage(String str) {
        super.setExtraLocationControllerPackage_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackage = str;
        }
    }

    public String getExtraLocationControllerPackage() {
        String str;
        synchronized (this.mLock) {
            str = this.mExtraLocationControllerPackage;
        }
        return str;
    }

    public void setExtraLocationControllerPackageEnabled(boolean z) {
        super.setExtraLocationControllerPackageEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackageEnabled = z;
        }
    }

    public boolean isExtraLocationControllerPackageEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mExtraLocationControllerPackageEnabled && this.mExtraLocationControllerPackage != null;
        }
        return z;
    }

    public void setLocationEnabledForUser(boolean z, int i) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "setLocationEnabledForUser", null);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS", null);
        LocationManager.invalidateLocalLocationEnabledCaches();
        this.mInjector.getSettingsHelper().setLocationEnabled(z, handleIncomingUser);
        this.mNSLocationMonitor.setLocationEnabled(z);
    }

    public boolean isLocationEnabledForUser(int i) {
        return this.mInjector.getSettingsHelper().isLocationEnabled(ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "isLocationEnabledForUser", null));
    }

    public void setAdasGnssLocationEnabledForUser(final boolean z, int i) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "setAdasGnssLocationEnabledForUser", null);
        LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        this.mInjector.getLocationSettings().updateUserSettings(handleIncomingUser, new Function() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                LocationUserSettings lambda$setAdasGnssLocationEnabledForUser$7;
                lambda$setAdasGnssLocationEnabledForUser$7 = LocationManagerService.lambda$setAdasGnssLocationEnabledForUser$7(z, (LocationUserSettings) obj);
                return lambda$setAdasGnssLocationEnabledForUser$7;
            }
        });
    }

    public static /* synthetic */ LocationUserSettings lambda$setAdasGnssLocationEnabledForUser$7(boolean z, LocationUserSettings locationUserSettings) {
        return locationUserSettings.withAdasGnssLocationEnabled(z);
    }

    public boolean isAdasGnssLocationEnabledForUser(int i) {
        return this.mInjector.getLocationSettings().getUserSettings(ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "isAdasGnssLocationEnabledForUser", null)).isAdasGnssLocationEnabled();
    }

    public boolean isProviderEnabledForUser(String str, int i) {
        return this.mLocalService.isProviderEnabledForUser(str, i);
    }

    public void setAutomotiveGnssSuspended(boolean z) {
        super.setAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("setAutomotiveGnssSuspended only allowed on automotive devices");
        }
        this.mGnssManagerService.setAutomotiveGnssSuspended(z);
    }

    public boolean isAutomotiveGnssSuspended() {
        super.isAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("isAutomotiveGnssSuspended only allowed on automotive devices");
        }
        return this.mGnssManagerService.isAutomotiveGnssSuspended();
    }

    public boolean geocoderIsPresent() {
        return this.mGeocodeProvider != null;
    }

    public void getFromLocation(double d, double d2, int i, GeocoderParams geocoderParams, IGeocodeListener iGeocodeListener) {
        Preconditions.checkArgument(CallerIdentity.fromBinder(this.mContext, geocoderParams.getClientPackage(), geocoderParams.getClientAttributionTag()).getUid() == geocoderParams.getClientUid());
        GeocoderProxy geocoderProxy = this.mGeocodeProvider;
        if (geocoderProxy != null) {
            geocoderProxy.getFromLocation(d, d2, i, geocoderParams, iGeocodeListener);
        } else {
            try {
                iGeocodeListener.onResults((String) null, Collections.emptyList());
            } catch (RemoteException unused) {
            }
        }
    }

    public void getFromLocationName(String str, double d, double d2, double d3, double d4, int i, GeocoderParams geocoderParams, IGeocodeListener iGeocodeListener) {
        Preconditions.checkArgument(CallerIdentity.fromBinder(this.mContext, geocoderParams.getClientPackage(), geocoderParams.getClientAttributionTag()).getUid() == geocoderParams.getClientUid());
        GeocoderProxy geocoderProxy = this.mGeocodeProvider;
        if (geocoderProxy != null) {
            geocoderProxy.getFromLocationName(str, d, d2, d3, d4, i, geocoderParams, iGeocodeListener);
        } else {
            try {
                iGeocodeListener.onResults((String) null, Collections.emptyList());
            } catch (RemoteException unused) {
            }
        }
    }

    public class ProcessListener implements ActivityManager.SemProcessListener {
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
        }

        public ProcessListener() {
        }

        public void onProcessDied(int i, final int i2) {
            if (LocationManagerService.this.mMockLocationRecord.isEmpty()) {
                return;
            }
            LocationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.location.LocationManagerService$ProcessListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocationManagerService.ProcessListener.this.lambda$onProcessDied$0(i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessDied$0(int i) {
            synchronized (LocationManagerService.this.mLock) {
                LocationManagerService.this.onProcessDiedLocked(i);
            }
        }
    }

    public final void recoverRealProviderLocked(String str) {
        Iterator it = this.mMockLocationRecord.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String str2 = (String) entry.getKey();
            String str3 = (String) entry.getValue();
            if (str3.equals(str)) {
                Log.d("LocationManagerService", "remove mock location. package: " + str3 + "provider name: " + str2);
                LocationProviderManager locationProviderManager = getLocationProviderManager(str2);
                if (locationProviderManager == null) {
                    return;
                }
                locationProviderManager.setMockProvider(null);
                if (!locationProviderManager.hasProvider()) {
                    removeLocationProviderManager(locationProviderManager);
                }
                it.remove();
            }
        }
        if (this.mMockLocationRecord.isEmpty()) {
            this.isMockLocationNotified = false;
            cancelNotificationForMockLocation();
        }
    }

    public final void onProcessDiedLocked(int i) {
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                recoverRealProviderLocked(str);
            }
        }
    }

    public final void onPackageRemovedLocked(String str) {
        if (this.mMockLocationRecord.isEmpty()) {
            return;
        }
        recoverRealProviderLocked(str);
    }

    public final void onMockLocationAppOpChanged() {
        synchronized (this.mLock) {
            if (!this.mMockLocationRecord.isEmpty()) {
                String str = (String) ((Map.Entry) this.mMockLocationRecord.entrySet().iterator().next()).getValue();
                try {
                    if (!this.mInjector.getAppOpsHelper().checkMockLocationAccess(str, this.mPackageManager.getPackageUid(str, 0))) {
                        recoverRealProviderLocked(str);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("LocationManagerService", "missing package: " + str);
                }
            }
        }
    }

    public final void createNotificationForMockLocation(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Resources resources = this.mContext.getResources();
                NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                notificationManager.createNotificationChannel(new NotificationChannel("Mock Location Reminder", resources.getString(R.string.permlab_startForegroundServicesFromBackground), 4));
                Intent intent = new Intent("com.samsung.android.location.mock.delete");
                intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
                PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 64);
                CharSequence applicationLabel = this.mPackageManager.getApplicationLabel(packageInfo.applicationInfo);
                Drawable applicationIcon = this.mPackageManager.getApplicationIcon(packageInfo.applicationInfo);
                Bitmap createBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                applicationIcon.setBounds(0, 0, applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight());
                applicationIcon.draw(canvas);
                String string = resources.getString(R.string.permlab_startForegroundServicesFromBackground);
                String string2 = resources.getString(R.string.permlab_setWallpaperHints, applicationLabel);
                notificationManager.notify(0, new Notification.Builder(this.mContext, "Mock Location Reminder").setContentTitle(string).setContentText(string2).setSmallIcon(R.drawable.ic_settings).setLargeIcon(createBitmap).setAutoCancel(true).setStyle(new Notification.BigTextStyle().bigText(string2)).setActions(new Notification.Action.Builder((Icon) null, resources.getString(R.string.permlab_sim_communication), PendingIntent.getBroadcast(this.mContext, 0, intent, 1275068416)).build()).build());
                this.mActivityManager.semRegisterProcessListener(this.mProcessListener);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("LocationManagerService", "missing package: " + str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void cancelNotificationForMockLocation() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManager) this.mContext.getSystemService("notification")).cancel(0);
            this.mActivityManager.semUnregisterProcessListener(this.mProcessListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addTestProvider(String str, ProviderProperties providerProperties, List list, String str2, String str3) {
        CallerIdentity fromBinderUnsafe = CallerIdentity.fromBinderUnsafe(str2, str3);
        if (this.mInjector.getAppOpsHelper().noteOp(58, fromBinderUnsafe)) {
            this.mNSLocationMonitor.updateTestProvider(true, str, str2);
            getOrAddLocationProviderManager(str).setMockProvider(new MockLocationProvider(providerProperties, fromBinderUnsafe, new ArraySet(list)));
            synchronized (this.mLock) {
                if ("gps".equals(str)) {
                    this.mMockLocationRecord.put("gps", str2);
                } else if ("fused".equals(str)) {
                    this.mMockLocationRecord.put("fused", str2);
                } else if ("network".equals(str)) {
                    this.mMockLocationRecord.put("network", str2);
                }
                if (!this.isMockLocationNotified && !this.mMockLocationRecord.isEmpty()) {
                    createNotificationForMockLocation(str2);
                    this.isMockLocationNotified = true;
                }
            }
        }
    }

    public void removeTestProvider(String str, String str2, String str3) {
        if (this.mInjector.getAppOpsHelper().noteOp(58, CallerIdentity.fromBinderUnsafe(str2, str3))) {
            synchronized (this.mLock) {
                LocationProviderManager locationProviderManager = getLocationProviderManager(str);
                if (locationProviderManager == null) {
                    return;
                }
                this.mNSLocationMonitor.updateTestProvider(false, str, str2);
                locationProviderManager.setMockProvider(null);
                if (!locationProviderManager.hasProvider()) {
                    removeLocationProviderManager(locationProviderManager);
                }
                if ("gps".equals(str)) {
                    this.mMockLocationRecord.remove("gps");
                } else if ("fused".equals(str)) {
                    this.mMockLocationRecord.remove("fused");
                } else if ("network".equals(str)) {
                    this.mMockLocationRecord.remove("network");
                }
                if (this.mMockLocationRecord.isEmpty()) {
                    this.isMockLocationNotified = false;
                    cancelNotificationForMockLocation();
                }
            }
        }
    }

    public void setTestProviderLocation(String str, Location location, String str2, String str3) {
        if (this.mInjector.getAppOpsHelper().noteOp(58, CallerIdentity.fromBinderUnsafe(str2, str3))) {
            Preconditions.checkArgument(location.isComplete(), "incomplete location object, missing timestamp or accuracy?");
            LocationProviderManager locationProviderManager = getLocationProviderManager(str);
            if (locationProviderManager == null) {
                throw new IllegalArgumentException("provider doesn't exist: " + str);
            }
            locationProviderManager.setMockProviderLocation(location);
        }
    }

    public void setTestProviderEnabled(String str, boolean z, String str2, String str3) {
        if (this.mInjector.getAppOpsHelper().noteOp(58, CallerIdentity.fromBinderUnsafe(str2, str3))) {
            LocationProviderManager locationProviderManager = getLocationProviderManager(str);
            if (locationProviderManager == null) {
                throw new IllegalArgumentException("provider doesn't exist: " + str);
            }
            locationProviderManager.setMockProviderAllowed(z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
        return new LocationShellCommand(this.mContext, this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "LocationManagerService", printWriter)) {
            final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            if (strArr.length > 0) {
                LocationProviderManager locationProviderManager = getLocationProviderManager(strArr[0]);
                if (locationProviderManager != null) {
                    indentingPrintWriter.println("Provider:");
                    indentingPrintWriter.increaseIndent();
                    locationProviderManager.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Event Log:");
                    indentingPrintWriter.increaseIndent();
                    LocationEventLog.EVENT_LOG.iterate(new Consumer() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            indentingPrintWriter.println((String) obj);
                        }
                    }, locationProviderManager.getName());
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                if ("--gnssmetrics".equals(strArr[0])) {
                    if (this.mGnssManagerService != null) {
                        this.mGnssManagerService.dump(fileDescriptor, indentingPrintWriter, strArr);
                        return;
                    }
                    return;
                }
            }
            indentingPrintWriter.println("Location Manager State:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("User Info:");
            indentingPrintWriter.increaseIndent();
            this.mInjector.getUserInfoHelper().dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Location Settings:");
            indentingPrintWriter.increaseIndent();
            this.mInjector.getSettingsHelper().dump(fileDescriptor, indentingPrintWriter, strArr);
            this.mInjector.getLocationSettings().dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            synchronized (this.mLock) {
                if (this.mExtraLocationControllerPackage != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Location Controller Extra Package: ");
                    sb.append(this.mExtraLocationControllerPackage);
                    sb.append(this.mExtraLocationControllerPackageEnabled ? " [enabled]" : " [disabled]");
                    indentingPrintWriter.println(sb.toString());
                }
            }
            indentingPrintWriter.println("Location Providers:");
            indentingPrintWriter.increaseIndent();
            Iterator it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                ((LocationProviderManager) it.next()).dump(fileDescriptor, indentingPrintWriter, strArr);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Historical Aggregate Location Provider Data:");
            indentingPrintWriter.increaseIndent();
            ArrayMap copyAggregateStats = LocationEventLog.EVENT_LOG.copyAggregateStats();
            for (int i = 0; i < copyAggregateStats.size(); i++) {
                indentingPrintWriter.print((String) copyAggregateStats.keyAt(i));
                indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                indentingPrintWriter.increaseIndent();
                ArrayMap arrayMap = (ArrayMap) copyAggregateStats.valueAt(i);
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    indentingPrintWriter.print(arrayMap.keyAt(i2));
                    indentingPrintWriter.print(": ");
                    ((LocationEventLog.AggregateStats) arrayMap.valueAt(i2)).updateTotals();
                    indentingPrintWriter.println(arrayMap.valueAt(i2));
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
            if (this.mGnssManagerService != null) {
                indentingPrintWriter.println("GNSS Manager:");
                indentingPrintWriter.increaseIndent();
                this.mGnssManagerService.dump(fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println("Geofence Manager:");
            indentingPrintWriter.increaseIndent();
            this.mGeofenceManager.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Event Log:");
            indentingPrintWriter.increaseIndent();
            LocationEventLog.EVENT_LOG.iterate(new Consumer() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    indentingPrintWriter.println((String) obj);
                }
            });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Mock Location Record:");
            for (Map.Entry entry : this.mMockLocationRecord.entrySet()) {
                indentingPrintWriter.println("    " + ((String) entry.getKey()) + ": " + ((String) entry.getValue()));
            }
        }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.StateChangedListener
    public void onStateChanged(String str, AbstractLocationProvider.State state, AbstractLocationProvider.State state2) {
        if (!Objects.equals(state.identity, state2.identity)) {
            refreshAppOpsRestrictions(-1);
        }
        if (state.extraAttributionTags.equals(state2.extraAttributionTags) && Objects.equals(state.identity, state2.identity)) {
            return;
        }
        synchronized (this.mLock) {
            final LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener = this.mLocationTagsChangedListener;
            if (locationPackageTagsListener != null) {
                CallerIdentity callerIdentity = state.identity;
                final int uid = callerIdentity != null ? callerIdentity.getUid() : -1;
                CallerIdentity callerIdentity2 = state2.identity;
                final int uid2 = callerIdentity2 != null ? callerIdentity2.getUid() : -1;
                if (uid != -1) {
                    final PackageTagsList calculateAppOpsLocationSourceTags = calculateAppOpsLocationSourceTags(uid);
                    this.mHandler.post(new Runnable() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            locationPackageTagsListener.onLocationPackageTagsChanged(uid, calculateAppOpsLocationSourceTags);
                        }
                    });
                }
                if (uid2 != -1 && uid2 != uid) {
                    final PackageTagsList calculateAppOpsLocationSourceTags2 = calculateAppOpsLocationSourceTags(uid2);
                    this.mHandler.post(new Runnable() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            locationPackageTagsListener.onLocationPackageTagsChanged(uid2, calculateAppOpsLocationSourceTags2);
                        }
                    });
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void refreshAppOpsRestrictions(int i) {
        PackageTagsList packageTagsList;
        if (i == -1) {
            for (int i2 : this.mInjector.getUserInfoHelper().getRunningUserIds()) {
                refreshAppOpsRestrictions(i2);
            }
            return;
        }
        Preconditions.checkArgument(i >= 0);
        boolean isLocationEnabled = this.mInjector.getSettingsHelper().isLocationEnabled(i);
        if (isLocationEnabled) {
            packageTagsList = null;
        } else {
            PackageTagsList.Builder builder = new PackageTagsList.Builder();
            Iterator it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                CallerIdentity providerIdentity = ((LocationProviderManager) it.next()).getProviderIdentity();
                if (providerIdentity != null) {
                    builder.add(providerIdentity.getPackageName(), providerIdentity.getAttributionTag());
                }
            }
            builder.add(this.mInjector.getSettingsHelper().getIgnoreSettingsAllowlist());
            builder.add(this.mInjector.getSettingsHelper().getAdasAllowlist());
            packageTagsList = builder.build();
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        Objects.requireNonNull(appOpsManager);
        appOpsManager.setUserRestrictionForUser(0, !isLocationEnabled, this, packageTagsList, i);
        appOpsManager.setUserRestrictionForUser(1, !isLocationEnabled, this, packageTagsList, i);
    }

    public PackageTagsList calculateAppOpsLocationSourceTags(int i) {
        PackageTagsList.Builder builder = new PackageTagsList.Builder();
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            AbstractLocationProvider.State state = locationProviderManager.getState();
            CallerIdentity callerIdentity = state.identity;
            if (callerIdentity != null && callerIdentity.getUid() == i) {
                builder.add(state.identity.getPackageName(), state.extraAttributionTags);
                if (state.extraAttributionTags.isEmpty() || state.identity.getAttributionTag() != null) {
                    builder.add(state.identity.getPackageName(), state.identity.getAttributionTag());
                } else {
                    Log.e("LocationManagerService", locationProviderManager.getName() + " provider has specified a null attribution tag and a non-empty set of extra attribution tags - dropping the null attribution tag");
                }
            }
        }
        return builder.build();
    }

    public class LocalService extends LocationManagerInternal {
        public LocalService() {
        }

        public boolean isProviderEnabledForUser(String str, int i) {
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "isProviderEnabledForUser", null);
            LocationProviderManager locationProviderManager = LocationManagerService.this.getLocationProviderManager(str);
            if (locationProviderManager == null) {
                return false;
            }
            return locationProviderManager.isEnabled(handleIncomingUser);
        }

        public void addProviderEnabledListener(String str, LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            LocationProviderManager locationProviderManager = LocationManagerService.this.getLocationProviderManager(str);
            Objects.requireNonNull(locationProviderManager);
            locationProviderManager.addEnabledListener(providerEnabledListener);
        }

        public void removeProviderEnabledListener(String str, LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            LocationProviderManager locationProviderManager = LocationManagerService.this.getLocationProviderManager(str);
            Objects.requireNonNull(locationProviderManager);
            locationProviderManager.removeEnabledListener(providerEnabledListener);
        }

        public boolean isProvider(String str, CallerIdentity callerIdentity) {
            Iterator it = LocationManagerService.this.mProviderManagers.iterator();
            while (it.hasNext()) {
                LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
                if (str == null || str.equals(locationProviderManager.getName())) {
                    if (callerIdentity.equals(locationProviderManager.getProviderIdentity()) && locationProviderManager.isVisibleToCaller()) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void sendNiResponse(int i, int i2) {
            if (LocationManagerService.this.mGnssManagerService != null) {
                LocationManagerService.this.mGnssManagerService.sendNiResponse(i, i2);
            }
        }

        public LocationTime getGnssTimeMillis() {
            Location lastLocationUnsafe;
            LocationProviderManager locationProviderManager = LocationManagerService.this.getLocationProviderManager("gps");
            if (locationProviderManager == null || (lastLocationUnsafe = locationProviderManager.getLastLocationUnsafe(-1, 2, false, Long.MAX_VALUE)) == null) {
                return null;
            }
            return new LocationTime(lastLocationUnsafe.getTime(), lastLocationUnsafe.getElapsedRealtimeNanos());
        }

        public void setLocationPackageTagsListener(final LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener) {
            synchronized (LocationManagerService.this.mLock) {
                LocationManagerService.this.mLocationTagsChangedListener = locationPackageTagsListener;
                if (locationPackageTagsListener != null) {
                    ArraySet arraySet = new ArraySet(LocationManagerService.this.mProviderManagers.size());
                    Iterator it = LocationManagerService.this.mProviderManagers.iterator();
                    while (it.hasNext()) {
                        CallerIdentity providerIdentity = ((LocationProviderManager) it.next()).getProviderIdentity();
                        if (providerIdentity != null) {
                            arraySet.add(Integer.valueOf(providerIdentity.getUid()));
                        }
                    }
                    Iterator it2 = arraySet.iterator();
                    while (it2.hasNext()) {
                        final int intValue = ((Integer) it2.next()).intValue();
                        final PackageTagsList calculateAppOpsLocationSourceTags = LocationManagerService.this.calculateAppOpsLocationSourceTags(intValue);
                        if (!calculateAppOpsLocationSourceTags.isEmpty()) {
                            LocationManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.location.LocationManagerService$LocalService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    locationPackageTagsListener.onLocationPackageTagsChanged(intValue, calculateAppOpsLocationSourceTags);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    public final class SystemInjector implements Injector {
        public final AlarmHelper mAlarmHelper;
        public final SystemAppForegroundHelper mAppForegroundHelper;
        public final SystemAppOpsHelper mAppOpsHelper;
        public final Context mContext;
        public final SystemDeviceIdleHelper mDeviceIdleHelper;
        public final SystemDeviceStationaryHelper mDeviceStationaryHelper;
        public SystemEmergencyHelper mEmergencyCallHelper;
        public final SystemLocationPermissionsHelper mLocationPermissionsHelper;
        public final SystemLocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
        public final LocationSettings mLocationSettings;
        public final LocationUsageLogger mLocationUsageLogger;
        public final NSConnectionHelper mNSConnectionHelper;
        public final NSLocationProviderHelper mNSLocationProviderHelper;
        public final NSPermissionHelper mNSPermissionHelper;
        public final PackageResetHelper mPackageResetHelper;
        public final SystemScreenInteractiveHelper mScreenInteractiveHelper;
        public final SystemSettingsHelper mSettingsHelper;
        public boolean mSystemReady;
        public final SystemUserInfoHelper mUserInfoHelper;

        public SystemInjector(Context context, SystemUserInfoHelper systemUserInfoHelper) {
            this.mContext = context;
            this.mUserInfoHelper = systemUserInfoHelper;
            this.mLocationSettings = new LocationSettings(context);
            this.mAlarmHelper = new SystemAlarmHelper(context);
            SystemAppOpsHelper systemAppOpsHelper = new SystemAppOpsHelper(context);
            this.mAppOpsHelper = systemAppOpsHelper;
            SystemLocationPermissionsHelper systemLocationPermissionsHelper = new SystemLocationPermissionsHelper(context, systemAppOpsHelper);
            this.mLocationPermissionsHelper = systemLocationPermissionsHelper;
            this.mSettingsHelper = new SystemSettingsHelper(context);
            SystemAppForegroundHelper systemAppForegroundHelper = new SystemAppForegroundHelper(context);
            this.mAppForegroundHelper = systemAppForegroundHelper;
            this.mLocationPowerSaveModeHelper = new SystemLocationPowerSaveModeHelper(context);
            this.mScreenInteractiveHelper = new SystemScreenInteractiveHelper(context);
            this.mDeviceStationaryHelper = new SystemDeviceStationaryHelper();
            this.mDeviceIdleHelper = new SystemDeviceIdleHelper(context);
            this.mLocationUsageLogger = new LocationUsageLogger();
            this.mPackageResetHelper = new SystemPackageResetHelper(context);
            NSConnectionHelper nSConnectionHelper = new NSConnectionHelper();
            this.mNSConnectionHelper = nSConnectionHelper;
            NSPermissionHelper nSPermissionHelper = new NSPermissionHelper(systemUserInfoHelper, nSConnectionHelper);
            this.mNSPermissionHelper = nSPermissionHelper;
            this.mNSLocationProviderHelper = new NSLocationProviderHelper(context, nSPermissionHelper, nSConnectionHelper);
            systemAppForegroundHelper.setNSPermissionHelper(nSPermissionHelper);
            systemLocationPermissionsHelper.setNSPermissionHelper(nSPermissionHelper);
        }

        public synchronized void onSystemReady() {
            this.mUserInfoHelper.onSystemReady();
            this.mAppOpsHelper.onSystemReady();
            this.mLocationPermissionsHelper.onSystemReady();
            this.mSettingsHelper.onSystemReady();
            this.mAppForegroundHelper.onSystemReady();
            this.mLocationPowerSaveModeHelper.onSystemReady();
            this.mScreenInteractiveHelper.onSystemReady();
            this.mDeviceStationaryHelper.onSystemReady();
            this.mDeviceIdleHelper.onSystemReady();
            SystemEmergencyHelper systemEmergencyHelper = this.mEmergencyCallHelper;
            if (systemEmergencyHelper != null) {
                systemEmergencyHelper.onSystemReady();
            }
            this.mSystemReady = true;
        }

        @Override // com.android.server.location.injector.Injector
        public UserInfoHelper getUserInfoHelper() {
            return this.mUserInfoHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public LocationSettings getLocationSettings() {
            return this.mLocationSettings;
        }

        @Override // com.android.server.location.injector.Injector
        public AlarmHelper getAlarmHelper() {
            return this.mAlarmHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public AppOpsHelper getAppOpsHelper() {
            return this.mAppOpsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public LocationPermissionsHelper getLocationPermissionsHelper() {
            return this.mLocationPermissionsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public SettingsHelper getSettingsHelper() {
            return this.mSettingsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public AppForegroundHelper getAppForegroundHelper() {
            return this.mAppForegroundHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public LocationPowerSaveModeHelper getLocationPowerSaveModeHelper() {
            return this.mLocationPowerSaveModeHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public ScreenInteractiveHelper getScreenInteractiveHelper() {
            return this.mScreenInteractiveHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public DeviceStationaryHelper getDeviceStationaryHelper() {
            return this.mDeviceStationaryHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public DeviceIdleHelper getDeviceIdleHelper() {
            return this.mDeviceIdleHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public synchronized EmergencyHelper getEmergencyHelper() {
            if (this.mEmergencyCallHelper == null) {
                SystemEmergencyHelper systemEmergencyHelper = new SystemEmergencyHelper(this.mContext);
                this.mEmergencyCallHelper = systemEmergencyHelper;
                if (this.mSystemReady) {
                    systemEmergencyHelper.onSystemReady();
                }
            }
            return this.mEmergencyCallHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public LocationUsageLogger getLocationUsageLogger() {
            return this.mLocationUsageLogger;
        }

        @Override // com.android.server.location.injector.Injector
        public PackageResetHelper getPackageResetHelper() {
            return this.mPackageResetHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public NSPermissionHelper getNSPermissionHelper() {
            return this.mNSPermissionHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public NSLocationProviderHelper getNSLocationProviderHelper() {
            return this.mNSLocationProviderHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public NSConnectionHelper getNSConnectionHelper() {
            return this.mNSConnectionHelper;
        }
    }

    public Map getGPSUsingApps() {
        return this.mNSLocationMonitor.getGPSUsingApps();
    }

    public void notifyNSFLP(Message message) {
        this.mNSLocationMonitor.onMessageUpdated(message);
    }

    public void onFreezeStateChanged(boolean z, int i) {
        Log.w("LocationManagerService", "onFreezeStateChanged, uid[" + i + "]=" + z);
        this.mInjector.getLocationPermissionsHelper().onFreezeStateChanged(z, i);
    }
}
