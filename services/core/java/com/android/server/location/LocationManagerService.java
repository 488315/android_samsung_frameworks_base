package com.android.server.location;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.location.GeofenceHardwareService;
import android.location.Criteria;
import android.location.Geofence;
import android.location.GnssCapabilities;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementRequest;
import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssNmeaListener;
import android.location.IGnssStatusListener;
import android.location.ILocationListener;
import android.location.ILocationManager;
import android.location.INSLocationManager;
import android.location.LastLocationRequest;
import android.location.Location;
import android.location.LocationConstants;
import android.location.LocationManager;
import android.location.LocationManagerInternal;
import android.location.LocationProvider;
import android.location.LocationRequest;
import android.location.LocationTime;
import android.location.flags.Flags;
import android.location.provider.ForwardGeocodeRequest;
import android.location.provider.IGeocodeCallback;
import android.location.provider.IProviderRequestListener;
import android.location.provider.ProviderProperties;
import android.location.provider.ReverseGeocodeRequest;
import android.location.util.identity.CallerIdentity;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PackageTagsList;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.server.DeviceIdleInternal;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.geofence.GeofenceManager;
import com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda4;
import com.android.server.location.geofence.GeofenceManager.GeofenceRegistration;
import com.android.server.location.geofence.GeofenceProxy;
import com.android.server.location.geofence.GeofenceProxy.GeofenceProxyServiceConnection;
import com.android.server.location.gnss.GnssAntennaInfoProvider;
import com.android.server.location.gnss.GnssAntennaInfoProvider.AntennaInfoListenerRegistration;
import com.android.server.location.gnss.GnssConfiguration;
import com.android.server.location.gnss.GnssLocationProvider;
import com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda12;
import com.android.server.location.gnss.GnssLocationProviderSec;
import com.android.server.location.gnss.GnssManagerService;
import com.android.server.location.gnss.GnssManagerService$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.GnssNmeaProvider;
import com.android.server.location.gnss.GnssSatelliteBlocklistHelper;
import com.android.server.location.gnss.GnssStatusProvider;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssHalStatus;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.gnss.sec.SLocationProxy;
import com.android.server.location.injector.EmergencyHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener;
import com.android.server.location.injector.SettingsHelper$UserSettingChangedListener;
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
import com.android.server.location.injector.UserInfoHelper$UserListener;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda0;
import com.android.server.location.nsflp.NSLocationMonitor;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.nsflp.NSPermissionHelper;
import com.android.server.location.provider.AbstractLocationProvider;
import com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda0;
import com.android.server.location.provider.LocationProviderManager;
import com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda1;
import com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda12;
import com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda19;
import com.android.server.location.provider.MockLocationProvider;
import com.android.server.location.provider.MockableLocationProvider;
import com.android.server.location.provider.PassiveLocationProvider;
import com.android.server.location.provider.PassiveLocationProviderManager;
import com.android.server.location.provider.StationaryThrottlingLocationProvider;
import com.android.server.location.provider.proxy.ProxyGeocodeProvider;
import com.android.server.location.provider.proxy.ProxyLocationProvider;
import com.android.server.location.settings.LocationSettings;
import com.android.server.location.settings.LocationUserSettings;
import com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda1;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.servicewatcher.ServiceWatcherImpl;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.location.ISLocationManager;
import com.samsung.android.location.ISLocationSystemCallV1;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationManagerService extends ILocationManager.Stub
        implements LocationProviderManager.StateChangedListener {
    public static ISLocationManager mISLocationManager;
    public ActivityManager mActivityManager;
    public final CarrierConfig mCarrierConfig;
    public final Context mContext;
    public ILocationListener mDeprecatedGnssBatchingListener;
    public String mExtraLocationControllerPackage;
    public boolean mExtraLocationControllerPackageEnabled;
    public ProxyGeocodeProvider mGeocodeProvider;
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final LocationManagerService mService;
        public final SystemInjector mSystemInjector;
        public final LifecycleUserInfoHelper mUserInfoHelper;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class LifecycleUserInfoHelper {
            public IActivityManager mActivityManager;
            public ActivityManagerInternal mActivityManagerInternal;
            public final Context mContext;
            public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
            public UserManager mUserManager;
            public UserManagerInternal mUserManagerInternal;

            public LifecycleUserInfoHelper(Context context) {
                this.mContext = context;
            }

            public final ActivityManagerInternal getActivityManagerInternal() {
                synchronized (this) {
                    try {
                        if (this.mActivityManagerInternal == null) {
                            this.mActivityManagerInternal =
                                    (ActivityManagerInternal)
                                            LocalServices.getService(ActivityManagerInternal.class);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return this.mActivityManagerInternal;
            }

            public final int getCurrentUserId() {
                ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
                if (activityManagerInternal == null) {
                    return -10000;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return activityManagerInternal.getCurrentUserId();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final int[] getRunningUserIds() {
                synchronized (this) {
                    try {
                        if (this.mActivityManager == null) {
                            this.mActivityManager = ActivityManager.getService();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                IActivityManager iActivityManager = this.mActivityManager;
                if (iActivityManager == null) {
                    return new int[0];
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        return iActivityManager.getRunningUserIds();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            public final UserManager getUserManager() {
                synchronized (this) {
                    try {
                        if (this.mUserManager == null) {
                            this.mUserManager =
                                    (UserManager) this.mContext.getSystemService(UserManager.class);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return this.mUserManager;
            }

            public final boolean isVisibleUserId(int i) {
                boolean isUserVisible;
                synchronized (this) {
                    Preconditions.checkState(this.mUserManagerInternal != null);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (this) {
                        isUserVisible = this.mUserManagerInternal.isUserVisible(i);
                    }
                    return isUserVisible;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public Lifecycle(Context context) {
            super(context);
            LifecycleUserInfoHelper lifecycleUserInfoHelper = new LifecycleUserInfoHelper(context);
            this.mUserInfoHelper = lifecycleUserInfoHelper;
            SystemInjector systemInjector = new SystemInjector(context, lifecycleUserInfoHelper);
            this.mSystemInjector = systemInjector;
            this.mService = new LocationManagerService(context, systemInjector);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            GnssHalStatus gnssHalStatus;
            GnssHalStatus gnssHalStatus2;
            if (i == 500) {
                SystemInjector systemInjector = this.mSystemInjector;
                synchronized (systemInjector) {
                    try {
                        final LifecycleUserInfoHelper lifecycleUserInfoHelper =
                                systemInjector.mUserInfoHelper;
                        synchronized (lifecycleUserInfoHelper) {
                            UserManagerInternal userManagerInternal =
                                    (UserManagerInternal)
                                            LocalServices.getService(UserManagerInternal.class);
                            Objects.requireNonNull(userManagerInternal);
                            lifecycleUserInfoHelper.mUserManagerInternal = userManagerInternal;
                            userManagerInternal.addUserVisibilityListener(
                                    new UserManagerInternal
                                            .UserVisibilityListener() { // from class:
                                                                        // com.android.server.location.injector.SystemUserInfoHelper$$ExternalSyntheticLambda0
                                        @Override // com.android.server.pm.UserManagerInternal.UserVisibilityListener
                                        public final void onUserVisibilityChanged(
                                                int i2, boolean z) {
                                            LocationManagerService.Lifecycle.LifecycleUserInfoHelper
                                                    lifecycleUserInfoHelper2 =
                                                            LocationManagerService.Lifecycle
                                                                    .LifecycleUserInfoHelper.this;
                                            lifecycleUserInfoHelper2.getClass();
                                            StringBuilder sb = new StringBuilder("visibility of u");
                                            sb.append(i2);
                                            sb.append(" changed to ");
                                            VpnManagerService$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    z ? "visible" : "invisible",
                                                    "LocationManagerService");
                                            LocationEventLog locationEventLog =
                                                    LocationEventLog.EVENT_LOG;
                                            locationEventLog.getClass();
                                            locationEventLog.addLog$1(
                                                    new LocationEventLog.LocationEnabledEvent(
                                                            i2, 2, z));
                                            Iterator it =
                                                    lifecycleUserInfoHelper2.mListeners.iterator();
                                            while (it.hasNext()) {
                                                ((UserInfoHelper$UserListener) it.next())
                                                        .onUserChanged(i2, 4);
                                            }
                                        }
                                    });
                        }
                        systemInjector.mAppOpsHelper.onSystemReady();
                        final SystemLocationPermissionsHelper systemLocationPermissionsHelper =
                                systemInjector.mLocationPermissionsHelper;
                        if (!systemLocationPermissionsHelper.mInited) {
                            systemLocationPermissionsHelper
                                    .mContext
                                    .getPackageManager()
                                    .addOnPermissionsChangeListener(
                                            new PackageManager
                                                    .OnPermissionsChangedListener() { // from class:
                                                // com.android.server.location.injector.SystemLocationPermissionsHelper$$ExternalSyntheticLambda0
                                                public final void onPermissionsChanged(
                                                        final int i2) {
                                                    final SystemLocationPermissionsHelper
                                                            systemLocationPermissionsHelper2 =
                                                                    SystemLocationPermissionsHelper
                                                                            .this;
                                                    systemLocationPermissionsHelper2.getClass();
                                                    LocationServiceThread.getHandler()
                                                            .post(
                                                                    new Runnable() { // from class:
                                                                        // com.android.server.location.injector.SystemLocationPermissionsHelper$$ExternalSyntheticLambda1
                                                                        @Override // java.lang.Runnable
                                                                        public final void run() {
                                                                            SystemLocationPermissionsHelper
                                                                                    systemLocationPermissionsHelper3 =
                                                                                            SystemLocationPermissionsHelper
                                                                                                    .this;
                                                                            int i3 = i2;
                                                                            NSPermissionHelper
                                                                                    nSPermissionHelper =
                                                                                            systemLocationPermissionsHelper3
                                                                                                    .mNSPermissionHelper;
                                                                            if (nSPermissionHelper
                                                                                    != null) {
                                                                                nSPermissionHelper
                                                                                        .mNSConnectionHelper
                                                                                        .onStateUpdated(
                                                                                                LocationConstants
                                                                                                        .STATE_TYPE
                                                                                                        .PERMISSIONS_CHANGED,
                                                                                                SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                i3,
                                                                                                                "uid"));
                                                                            }
                                                                            Iterator it =
                                                                                    systemLocationPermissionsHelper3
                                                                                            .mListeners
                                                                                            .iterator();
                                                                            while (it.hasNext()) {
                                                                                ((LocationPermissionsHelper$LocationPermissionsListener)
                                                                                                it
                                                                                                        .next())
                                                                                        .onLocationPermissionsChanged(
                                                                                                i3);
                                                                            }
                                                                            systemLocationPermissionsHelper3
                                                                                    .mSLocationConsumer
                                                                                    .accept(
                                                                                            Integer
                                                                                                    .valueOf(
                                                                                                            i3));
                                                                        }
                                                                    });
                                                }
                                            });
                            systemLocationPermissionsHelper.mInited = true;
                        }
                        systemInjector.mSettingsHelper.onSystemReady();
                        final SystemAppForegroundHelper systemAppForegroundHelper =
                                systemInjector.mAppForegroundHelper;
                        if (systemAppForegroundHelper.mActivityManager == null) {
                            ActivityManager activityManager =
                                    (ActivityManager)
                                            systemAppForegroundHelper.mContext.getSystemService(
                                                    ActivityManager.class);
                            Objects.requireNonNull(activityManager);
                            systemAppForegroundHelper.mActivityManager = activityManager;
                            activityManager.addOnUidImportanceListener(
                                    new ActivityManager
                                            .OnUidImportanceListener() { // from class:
                                                                         // com.android.server.location.injector.SystemAppForegroundHelper$$ExternalSyntheticLambda0
                                        public final void onUidImportance(final int i2, int i3) {
                                            final SystemAppForegroundHelper
                                                    systemAppForegroundHelper2 =
                                                            SystemAppForegroundHelper.this;
                                            systemAppForegroundHelper2.getClass();
                                            final boolean z = i3 <= 125;
                                            LocationServiceThread.getHandler()
                                                    .post(
                                                            new Runnable() { // from class:
                                                                             // com.android.server.location.injector.SystemAppForegroundHelper$$ExternalSyntheticLambda1
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    SystemAppForegroundHelper
                                                                            systemAppForegroundHelper3 =
                                                                                    SystemAppForegroundHelper
                                                                                            .this;
                                                                    int i4 = i2;
                                                                    boolean z2 = z;
                                                                    NSPermissionHelper
                                                                            nSPermissionHelper =
                                                                                    systemAppForegroundHelper3
                                                                                            .mNSPermissionHelper;
                                                                    if (nSPermissionHelper
                                                                            != null) {
                                                                        Bundle bundle =
                                                                                new Bundle();
                                                                        bundle.putInt("uid", i4);
                                                                        bundle.putBoolean(
                                                                                "foreground", z2);
                                                                        nSPermissionHelper
                                                                                .mNSConnectionHelper
                                                                                .onStateUpdated(
                                                                                        LocationConstants
                                                                                                .STATE_TYPE
                                                                                                .FOREGROUND_CHANGED,
                                                                                        bundle);
                                                                    }
                                                                    Iterator it =
                                                                            systemAppForegroundHelper3
                                                                                    .mListeners
                                                                                    .iterator();
                                                                    while (it.hasNext()) {
                                                                        ((AppForegroundHelper$AppForegroundListener)
                                                                                        it.next())
                                                                                .onAppForegroundChanged(
                                                                                        i4, z2);
                                                                    }
                                                                }
                                                            });
                                        }
                                    },
                                    125);
                        }
                        SystemLocationPowerSaveModeHelper systemLocationPowerSaveModeHelper =
                                systemInjector.mLocationPowerSaveModeHelper;
                        if (!systemLocationPowerSaveModeHelper.mReady) {
                            ((PowerManagerInternal)
                                            LocalServices.getService(PowerManagerInternal.class))
                                    .registerLowPowerModeObserver(
                                            1, systemLocationPowerSaveModeHelper);
                            PowerManager powerManager =
                                    (PowerManager)
                                            systemLocationPowerSaveModeHelper.mContext
                                                    .getSystemService(PowerManager.class);
                            Objects.requireNonNull(powerManager);
                            systemLocationPowerSaveModeHelper.mLocationPowerSaveMode =
                                    powerManager.getLocationPowerSaveMode();
                            systemLocationPowerSaveModeHelper.mReady = true;
                        }
                        final SystemScreenInteractiveHelper systemScreenInteractiveHelper =
                                systemInjector.mScreenInteractiveHelper;
                        if (!systemScreenInteractiveHelper.mReady) {
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("android.intent.action.SCREEN_OFF");
                            intentFilter.addAction("android.intent.action.SCREEN_ON");
                            systemScreenInteractiveHelper.mContext.registerReceiverAsUser(
                                    new BroadcastReceiver() { // from class:
                                                              // com.android.server.location.injector.SystemScreenInteractiveHelper.1
                                        public AnonymousClass1() {}

                                        @Override // android.content.BroadcastReceiver
                                        public final void onReceive(
                                                Context context, Intent intent) {
                                            boolean z;
                                            if ("android.intent.action.SCREEN_ON"
                                                    .equals(intent.getAction())) {
                                                z = true;
                                            } else if (!"android.intent.action.SCREEN_OFF"
                                                    .equals(intent.getAction())) {
                                                return;
                                            } else {
                                                z = false;
                                            }
                                            SystemScreenInteractiveHelper
                                                    systemScreenInteractiveHelper2 =
                                                            SystemScreenInteractiveHelper.this;
                                            if (z
                                                    == systemScreenInteractiveHelper2
                                                            .mIsInteractive) {
                                                return;
                                            }
                                            systemScreenInteractiveHelper2.mIsInteractive = z;
                                            AccessibilityManagerService$$ExternalSyntheticOutline0
                                                    .m(
                                                            "screen interactive is now ",
                                                            "LocationManagerService",
                                                            z);
                                            Iterator it =
                                                    systemScreenInteractiveHelper2.mListeners
                                                            .iterator();
                                            while (it.hasNext()) {
                                                LocationProviderManager locationProviderManager =
                                                        ((LocationProviderManager$$ExternalSyntheticLambda1)
                                                                        it.next())
                                                                .f$0;
                                                SystemLocationPowerSaveModeHelper
                                                        systemLocationPowerSaveModeHelper2 =
                                                                locationProviderManager
                                                                        .mLocationPowerSaveModeHelper;
                                                Preconditions.checkState(
                                                        systemLocationPowerSaveModeHelper2.mReady);
                                                int i2 =
                                                        systemLocationPowerSaveModeHelper2
                                                                .mLocationPowerSaveMode;
                                                if (i2 != 1) {
                                                    if (i2 != 2 && i2 != 4) {}
                                                    locationProviderManager.updateRegistrations(
                                                            new LocationProviderManager$$ExternalSyntheticLambda12(
                                                                    2));
                                                } else if ("gps"
                                                        .equals(locationProviderManager.mName)) {
                                                    locationProviderManager.updateRegistrations(
                                                            new LocationProviderManager$$ExternalSyntheticLambda12(
                                                                    2));
                                                }
                                            }
                                        }
                                    },
                                    UserHandle.ALL,
                                    intentFilter,
                                    null,
                                    FgThread.getHandler());
                            systemScreenInteractiveHelper.mReady = true;
                        }
                        SystemDeviceStationaryHelper systemDeviceStationaryHelper =
                                systemInjector.mDeviceStationaryHelper;
                        systemDeviceStationaryHelper.getClass();
                        DeviceIdleInternal deviceIdleInternal =
                                (DeviceIdleInternal)
                                        LocalServices.getService(DeviceIdleInternal.class);
                        Objects.requireNonNull(deviceIdleInternal);
                        systemDeviceStationaryHelper.mDeviceIdle = deviceIdleInternal;
                        SystemDeviceIdleHelper systemDeviceIdleHelper =
                                systemInjector.mDeviceIdleHelper;
                        synchronized (systemDeviceIdleHelper) {
                            systemDeviceIdleHelper.mSystemReady = true;
                            PowerManager powerManager2 =
                                    (PowerManager)
                                            systemDeviceIdleHelper.mContext.getSystemService(
                                                    PowerManager.class);
                            Objects.requireNonNull(powerManager2);
                            systemDeviceIdleHelper.mPowerManager = powerManager2;
                            systemDeviceIdleHelper.onRegistrationStateChanged();
                        }
                        SystemEmergencyHelper systemEmergencyHelper =
                                systemInjector.mEmergencyCallHelper;
                        if (systemEmergencyHelper != null) {
                            systemEmergencyHelper.onSystemReady();
                        }
                        systemInjector.mSystemReady = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                final LocationManagerService locationManagerService = this.mService;
                locationManagerService.getClass();
                if (Build.IS_DEBUGGABLE) {
                    AppOpsManager appOpsManager =
                            (AppOpsManager)
                                    locationManagerService.mContext.getSystemService(
                                            AppOpsManager.class);
                    Objects.requireNonNull(appOpsManager);
                    appOpsManager.startWatchingNoted(
                            new int[] {1, 0},
                            new AppOpsManager
                                    .OnOpNotedListener() { // from class:
                                                           // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda1
                                public final void onOpNoted(
                                        String str,
                                        int i2,
                                        String str2,
                                        String str3,
                                        int i3,
                                        int i4) {
                                    LocationManagerService locationManagerService2 =
                                            LocationManagerService.this;
                                    locationManagerService2.getClass();
                                    if (locationManagerService2.isLocationEnabledForUser(
                                            UserHandle.getUserId(i2))) {
                                        return;
                                    }
                                    Log.w(
                                            "LocationManagerService",
                                            "location noteOp with location off - "
                                                    + CallerIdentity.forTest(i2, 0, str2, str3));
                                }
                            });
                }
                final NSLocationMonitor nSLocationMonitor =
                        locationManagerService.mNSLocationMonitor;
                PackageManager packageManager = nSLocationMonitor.mContext.getPackageManager();
                nSLocationMonitor.mPackageManager = packageManager;
                if (packageManager.hasSystemFeature("com.sec.feature.nsflp")) {
                    int semGetSystemFeatureLevel =
                            nSLocationMonitor.mPackageManager.semGetSystemFeatureLevel(
                                    "com.sec.feature.nsflp");
                    nSLocationMonitor.mNSConnectionHelper.mHasNsflpFeature = true;
                    Log.i(
                            "NSLocationMonitor",
                            "NS-FLP Feature available, nsFlpFeatureLevel = "
                                    + semGetSystemFeatureLevel);
                    Log.i("NSLocationMonitor", "Try to bind NSMonitorService");
                    nSLocationMonitor.mContext.bindService(
                            new Intent()
                                    .setComponent(
                                            new ComponentName(
                                                    "com.sec.location.nsflp2",
                                                    "com.sec.location.nsflp2.nsmonitor.NSMonitorService")),
                            nSLocationMonitor.mMonitorServiceConnection,
                            67108865);
                    nSLocationMonitor.mLocationPowerSaveModeHelper.mListeners.add(
                            nSLocationMonitor.mLocationPowerSaveModeChangedListener);
                    nSLocationMonitor.mDeviceIdleHelper.addListener(
                            nSLocationMonitor.mDeviceIdleListener);
                    nSLocationMonitor.mDeviceStationaryHelper.addListener(
                            nSLocationMonitor.mStationaryListener);
                    nSLocationMonitor.mContext.registerReceiver(
                            new BroadcastReceiver() { // from class:
                                                      // com.android.server.location.nsflp.NSLocationMonitor.1
                                @Override // android.content.BroadcastReceiver
                                public final void onReceive(Context context, Intent intent) {
                                    if (intent == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    Log.i(
                                            "NSLocationMonitor",
                                            "Broadcast receiver, action=" + action);
                                    if ("com.samsung.intent.action.BIG_DATA_INFO".equals(action)
                                            && intent.getIntExtra(
                                                            LauncherConfigurationInternal
                                                                    .KEY_FEATURE_INT,
                                                            0)
                                                    == 2) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                "bigdataInfo",
                                                intent.getStringExtra("bigdata_info"));
                                        NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                                                LocationConstants.STATE_TYPE.CP_CRASH, bundle);
                                    }
                                }
                            },
                            new IntentFilter("com.samsung.intent.action.BIG_DATA_INFO"),
                            null,
                            nSLocationMonitor.mHandler);
                } else {
                    Log.e("NSLocationMonitor", "Not binding the MonitorService");
                    nSLocationMonitor.mNSConnectionHelper.mHasNsflpFeature = false;
                }
                ((SystemInjector) locationManagerService.mInjector)
                        .mAppOpsHelper.mMockLocationListeners.add(
                                new LocationManagerService$$ExternalSyntheticLambda2(
                                        locationManagerService));
                locationManagerService.mActivityManager =
                        (ActivityManager)
                                locationManagerService.mContext.getSystemService("activity");
                locationManagerService.mPackageManager =
                        locationManagerService.mContext.getPackageManager();
                new PackageMonitor() { // from class:
                                       // com.android.server.location.LocationManagerService.1
                    public final void onPackageDisappeared(String str, int i2) {
                        synchronized (LocationManagerService.this.mLock) {
                            LocationManagerService locationManagerService2 =
                                    LocationManagerService.this;
                            if (!locationManagerService2.mMockLocationRecord.isEmpty()) {
                                locationManagerService2.recoverRealProviderLocked(str);
                            }
                        }
                    }
                }.register(
                        locationManagerService.mContext,
                        (UserHandle) null,
                        locationManagerService.mHandler);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.samsung.android.location.mock.delete");
                locationManagerService.mContext.registerReceiverAsUser(
                        new BroadcastReceiver() { // from class:
                                                  // com.android.server.location.LocationManagerService.2
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context, Intent intent) {
                                if ("com.samsung.android.location.mock.delete"
                                        .equals(intent.getAction())) {
                                    synchronized (LocationManagerService.this.mLock) {
                                        try {
                                            LocationManagerService.this.isMockLocationNotified =
                                                    false;
                                            String stringExtra =
                                                    intent.getStringExtra(
                                                            "android.intent.extra.PACKAGE_NAME");
                                            if (stringExtra != null
                                                    && !LocationManagerService.this
                                                            .mMockLocationRecord.isEmpty()) {
                                                LocationManagerService.this
                                                        .recoverRealProviderLocked(stringExtra);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            }
                        },
                        UserHandle.ALL,
                        intentFilter2,
                        null,
                        locationManagerService.mHandler);
                return;
            }
            if (i == 600) {
                final LocationManagerService locationManagerService2 = this.mService;
                if (locationManagerService2.mCarrierConfig.isChinaCarrier()) {
                    ProxyLocationProvider create =
                            ProxyLocationProvider.create(
                                    locationManagerService2.mContext,
                                    "network",
                                    "com.android.location.service.v3.NetworkLocationProvider",
                                    R.bool.config_enableStylusPointerIcon,
                                    R.string.eventTypeBirthday,
                                    locationManagerService2.mInjector);
                    if (create != null) {
                        locationManagerService2.addLocationProviderManager(
                                new LocationProviderManager(
                                        locationManagerService2.mContext,
                                        locationManagerService2.mInjector,
                                        "network",
                                        locationManagerService2.mPassiveManager),
                                create);
                    } else {
                        Log.w("LocationManagerService", "no network location provider found");
                    }
                    Preconditions.checkState(
                            !locationManagerService2
                                    .mContext
                                    .getPackageManager()
                                    .queryIntentServicesAsUser(
                                            new Intent(
                                                    "com.android.location.service.FusedLocationProvider"),
                                            1572864,
                                            0)
                                    .isEmpty(),
                            "Unable to find a direct boot aware fused location provider");
                    ProxyLocationProvider create2 =
                            ProxyLocationProvider.create(
                                    locationManagerService2.mContext,
                                    "fused",
                                    "com.android.location.service.FusedLocationProvider",
                                    R.bool.config_enableStylusPointerIcon,
                                    R.string.duration_minutes_relative,
                                    locationManagerService2.mInjector);
                    if (create2 != null) {
                        locationManagerService2.addLocationProviderManager(
                                new LocationProviderManager(
                                        locationManagerService2.mContext,
                                        locationManagerService2.mInjector,
                                        "fused",
                                        locationManagerService2.mPassiveManager),
                                create2);
                    } else {
                        Log.wtf("LocationManagerService", "no fused location provider found");
                    }
                    ProxyGeocodeProvider proxyGeocodeProvider =
                            new ProxyGeocodeProvider(
                                    locationManagerService2.mContext,
                                    locationManagerService2.mInjector,
                                    0);
                    ServiceWatcherImpl serviceWatcherImpl = proxyGeocodeProvider.mServiceWatcher;
                    boolean checkServiceResolves = serviceWatcherImpl.checkServiceResolves();
                    if (checkServiceResolves) {
                        serviceWatcherImpl.register();
                    }
                    if (!checkServiceResolves) {
                        proxyGeocodeProvider = null;
                    }
                    locationManagerService2.mGeocodeProvider = proxyGeocodeProvider;
                    if (proxyGeocodeProvider == null) {
                        Log.e("LocationManagerService", "no geocoder provider found");
                    }
                    String string =
                            locationManagerService2
                                    .mContext
                                    .getResources()
                                    .getString(R.string.eventTypeBirthday);
                    try {
                        SystemAppOpsHelper systemAppOpsHelper =
                                ((SystemInjector) locationManagerService2.mInjector).mAppOpsHelper;
                        int packageUid =
                                locationManagerService2.mPackageManager.getPackageUid(string, 0);
                        Preconditions.checkState(systemAppOpsHelper.mAppOps != null);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (systemAppOpsHelper.mAppOps.noteOpNoThrow(24, packageUid, string)
                                    != 0) {
                                systemAppOpsHelper.mAppOps.setMode(24, packageUid, string, 0);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th2) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th2;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                "NameNotFoundException package: ",
                                string,
                                "LocationManagerService");
                    }
                } else {
                    ProxyLocationProvider create3 =
                            ProxyLocationProvider.create(
                                    locationManagerService2.mContext,
                                    "network",
                                    "com.android.location.service.v3.NetworkLocationProvider",
                                    R.bool.config_enableServerNotificationEffectsForAutomotive,
                                    R.string.eventTypeAnniversary,
                                    locationManagerService2.mInjector);
                    if (create3 != null) {
                        locationManagerService2.addLocationProviderManager(
                                new LocationProviderManager(
                                        locationManagerService2.mContext,
                                        locationManagerService2.mInjector,
                                        "network",
                                        locationManagerService2.mPassiveManager),
                                create3);
                    } else {
                        Log.w("LocationManagerService", "no network location provider found");
                    }
                    Preconditions.checkState(
                            !locationManagerService2
                                    .mContext
                                    .getPackageManager()
                                    .queryIntentServicesAsUser(
                                            new Intent(
                                                    "com.android.location.service.FusedLocationProvider"),
                                            1572864,
                                            0)
                                    .isEmpty(),
                            "Unable to find a direct boot aware fused location provider");
                    ProxyLocationProvider create4 =
                            ProxyLocationProvider.create(
                                    locationManagerService2.mContext,
                                    "fused",
                                    "com.android.location.service.FusedLocationProvider",
                                    R.bool.config_enableLockBeforeUnlockScreen,
                                    R.string.duration_minutes_relative,
                                    locationManagerService2.mInjector);
                    if (create4 != null) {
                        locationManagerService2.addLocationProviderManager(
                                new LocationProviderManager(
                                        locationManagerService2.mContext,
                                        locationManagerService2.mInjector,
                                        "fused",
                                        locationManagerService2.mPassiveManager),
                                create4);
                    } else {
                        Log.wtf("LocationManagerService", "no fused location provider found");
                    }
                    ProxyGeocodeProvider proxyGeocodeProvider2 =
                            new ProxyGeocodeProvider(
                                    locationManagerService2.mContext,
                                    locationManagerService2.mInjector);
                    ServiceWatcherImpl serviceWatcherImpl2 = proxyGeocodeProvider2.mServiceWatcher;
                    boolean checkServiceResolves2 = serviceWatcherImpl2.checkServiceResolves();
                    if (checkServiceResolves2) {
                        serviceWatcherImpl2.register();
                    }
                    if (!checkServiceResolves2) {
                        proxyGeocodeProvider2 = null;
                    }
                    locationManagerService2.mGeocodeProvider = proxyGeocodeProvider2;
                    if (proxyGeocodeProvider2 == null) {
                        Log.e("LocationManagerService", "no geocoder provider found");
                    }
                }
                boolean hasSystemFeature =
                        locationManagerService2
                                .mContext
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.location");
                GnssVendorConfig.getInstance().getClass();
                if (GnssVendorConfig.isLsiGnss()) {
                    gnssHalStatus = new GnssHalStatus();
                    gnssHalStatus.triggerCheckingHalStatus(5000L);
                } else {
                    gnssHalStatus = null;
                }
                boolean isSupported = GnssNative.isSupported();
                if (gnssHalStatus != null) {
                    gnssHalStatus.updateHalStatusChecked();
                }
                if (hasSystemFeature && isSupported) {
                    GnssNative create5 =
                            GnssNative.create(
                                    locationManagerService2.mContext,
                                    locationManagerService2.mInjector,
                                    new GnssConfiguration(locationManagerService2.mContext));
                    GnssVendorConfig.getInstance().getClass();
                    if (GnssVendorConfig.isLsiGnss()) {
                        gnssHalStatus2 = new GnssHalStatus();
                        gnssHalStatus2.triggerCheckingHalStatus(15000L);
                    } else {
                        gnssHalStatus2 = null;
                    }
                    locationManagerService2.mGnssManagerService =
                            new GnssManagerService(
                                    locationManagerService2.mContext,
                                    locationManagerService2.mInjector,
                                    create5);
                    if (gnssHalStatus2 != null) {
                        gnssHalStatus2.updateHalStatusChecked();
                    }
                    final GnssLocationProviderSec gnssLocationProviderSec =
                            locationManagerService2.mGnssManagerService.mGnssLocationProvider;
                    synchronized (gnssLocationProviderSec) {
                        gnssLocationProviderSec.mContext.registerReceiverAsUser(
                                new GnssLocationProvider.AnonymousClass2(
                                        gnssLocationProviderSec, 0),
                                UserHandle.ALL,
                                new IntentFilter("android.intent.action.ACTION_SHUTDOWN"),
                                null,
                                gnssLocationProviderSec.mHandler);
                        ContentResolver contentResolver =
                                gnssLocationProviderSec.mContext.getContentResolver();
                        Uri uriFor = Settings.Secure.getUriFor("location_mode");
                        final Handler handler = gnssLocationProviderSec.mHandler;
                        contentResolver.registerContentObserver(
                                uriFor,
                                true,
                                new ContentObserver(
                                        handler) { // from class:
                                                   // com.android.server.location.gnss.GnssLocationProvider.3
                                    public final /* synthetic */ GnssLocationProvider this$0;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public AnonymousClass3(
                                            final GnssLocationProviderSec gnssLocationProviderSec2,
                                            final Handler handler2) {
                                        super(handler2);
                                        r1 = gnssLocationProviderSec2;
                                    }

                                    @Override // android.database.ContentObserver
                                    public final void onChange(boolean z) {
                                        r1.updateEnabled();
                                    }
                                },
                                -1);
                        gnssLocationProviderSec2.mHandler.post(
                                new GnssLocationProvider$$ExternalSyntheticLambda0(
                                        gnssLocationProviderSec2, 1));
                        Handler handler2 = gnssLocationProviderSec2.mHandler;
                        GnssSatelliteBlocklistHelper gnssSatelliteBlocklistHelper =
                                gnssLocationProviderSec2.mGnssSatelliteBlocklistHelper;
                        Objects.requireNonNull(gnssSatelliteBlocklistHelper);
                        handler2.post(
                                new GnssLocationProvider$$ExternalSyntheticLambda12(
                                        0, gnssSatelliteBlocklistHelper));
                    }
                    AbstractLocationProvider create6 =
                            !locationManagerService2
                                            .mContext
                                            .getResources()
                                            .getBoolean(R.bool.config_voice_data_sms_auto_fallback)
                                    ? ProxyLocationProvider.create(
                                            locationManagerService2.mContext,
                                            "gps",
                                            "android.location.provider.action.GNSS_PROVIDER",
                                            R.bool.config_enableLockBeforeUnlockScreen,
                                            R.string.duration_years_shortest,
                                            locationManagerService2.mInjector)
                                    : null;
                    if (create6 == null) {
                        create6 = locationManagerService2.mGnssManagerService.mGnssLocationProvider;
                    } else {
                        locationManagerService2.addLocationProviderManager(
                                new LocationProviderManager(
                                        locationManagerService2.mContext,
                                        locationManagerService2.mInjector,
                                        "gps_hardware",
                                        null,
                                        Collections.singletonList(
                                                "android.permission.LOCATION_HARDWARE")),
                                locationManagerService2.mGnssManagerService.mGnssLocationProvider);
                    }
                    locationManagerService2.addLocationProviderManager(
                            new LocationProviderManager(
                                    locationManagerService2.mContext,
                                    locationManagerService2.mInjector,
                                    "gps",
                                    locationManagerService2.mPassiveManager),
                            create6);
                }
                HardwareActivityRecognitionProxy hardwareActivityRecognitionProxy =
                        new HardwareActivityRecognitionProxy(
                                locationManagerService2.mContext,
                                locationManagerService2.mInjector);
                ServiceWatcherImpl serviceWatcherImpl3 =
                        hardwareActivityRecognitionProxy.mServiceWatcher;
                boolean checkServiceResolves3 = serviceWatcherImpl3.checkServiceResolves();
                if (checkServiceResolves3) {
                    serviceWatcherImpl3.register();
                }
                if (!checkServiceResolves3) {
                    hardwareActivityRecognitionProxy = null;
                }
                if (hardwareActivityRecognitionProxy == null) {
                    Log.e("LocationManagerService", "unable to bind ActivityRecognitionProxy");
                }
                if (locationManagerService2.mGnssManagerService != null) {
                    Context context = locationManagerService2.mContext;
                    GeofenceProxy geofenceProxy =
                            new GeofenceProxy(
                                    context,
                                    locationManagerService2.mGnssManagerService.mGnssGeofenceProxy);
                    ServiceWatcherImpl serviceWatcherImpl4 = geofenceProxy.mServiceWatcher;
                    boolean checkServiceResolves4 = serviceWatcherImpl4.checkServiceResolves();
                    if (checkServiceResolves4) {
                        serviceWatcherImpl4.register();
                        context.bindServiceAsUser(
                                new Intent(context, (Class<?>) GeofenceHardwareService.class),
                                geofenceProxy.new GeofenceProxyServiceConnection(),
                                1,
                                UserHandle.SYSTEM);
                    }
                    if ((checkServiceResolves4 ? geofenceProxy : null) == null) {
                        Log.e("LocationManagerService", "unable to bind to GeofenceProxy");
                    }
                }
                for (String str :
                        locationManagerService2.mContext.getResources().getStringArray(17236329)) {
                    String[] split = str.split(",");
                    locationManagerService2
                            .getOrAddLocationProviderManager(split[0].trim())
                            .setMockProvider(
                                    new MockLocationProvider(
                                            new ProviderProperties.Builder()
                                                    .setHasNetworkRequirement(
                                                            Boolean.parseBoolean(split[1]))
                                                    .setHasSatelliteRequirement(
                                                            Boolean.parseBoolean(split[2]))
                                                    .setHasCellRequirement(
                                                            Boolean.parseBoolean(split[3]))
                                                    .setHasMonetaryCost(
                                                            Boolean.parseBoolean(split[4]))
                                                    .setHasAltitudeSupport(
                                                            Boolean.parseBoolean(split[5]))
                                                    .setHasSpeedSupport(
                                                            Boolean.parseBoolean(split[6]))
                                                    .setHasBearingSupport(
                                                            Boolean.parseBoolean(split[7]))
                                                    .setPowerUsage(Integer.parseInt(split[8]))
                                                    .setAccuracy(Integer.parseInt(split[9]))
                                                    .build(),
                                            CallerIdentity.fromContext(
                                                    locationManagerService2.mContext),
                                            Collections.emptySet()));
                }
                ISLocationManager asInterface =
                        ISLocationManager.Stub.asInterface(
                                ServiceManager.getService("sec_location"));
                if (asInterface == null) {
                    Log.e("LocationManagerService", "sLocation is null");
                    return;
                }
                LocationManagerService.mISLocationManager = asInterface;
                try {
                    asInterface.setSystemCallV1(
                            new ISLocationSystemCallV1
                                    .Stub() { // from class:
                                              // com.android.server.location.LocationManagerService.3
                                public final boolean isProviderEnabledForUser(String str2, int i2) {
                                    return LocationManagerService.this.mLocalService
                                            .isProviderEnabledForUser(str2, i2);
                                }

                                public final boolean isUidForeground(int i2) {
                                    return ((SystemInjector) LocationManagerService.this.mInjector)
                                            .mAppForegroundHelper.isAppForeground(i2);
                                }
                            });
                } catch (Throwable th3) {
                    Log.e("LocationManagerService", th3.toString());
                }
                if (locationManagerService2.mGnssManagerService != null) {
                    GnssManagerService gnssManagerService =
                            locationManagerService2.mGnssManagerService;
                    SLocationProxy sLocationProxy = gnssManagerService.mSLocationProxy;
                    sLocationProxy.getClass();
                    sLocationProxy.mSLocationService =
                            ISLocationManager.Stub.asInterface(
                                    ServiceManager.getService("sec_location"));
                    GnssLocationProviderSec gnssLocationProviderSec2 =
                            gnssManagerService.mGnssLocationProvider;
                    if (gnssLocationProviderSec2 != null) {
                        gnssLocationProviderSec2.mSvCallback =
                                new GnssManagerService$$ExternalSyntheticLambda0(sLocationProxy);
                    }
                    GnssStatusProvider gnssStatusProvider = gnssManagerService.mGnssStatusProvider;
                    if (gnssStatusProvider != null) {
                        gnssStatusProvider.mOnStatusChanged =
                                new GnssManagerService$$ExternalSyntheticLambda0(sLocationProxy);
                    }
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("location", this.mService);
            LocationManager.invalidateLocalLocationEnabledCaches();
            LocationManager.disableLocalLocationEnabledCaches();
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            LifecycleUserInfoHelper lifecycleUserInfoHelper = this.mUserInfoHelper;
            lifecycleUserInfoHelper.getClass();
            Log.d("LocationManagerService", "u" + userIdentifier + " started");
            Iterator it = lifecycleUserInfoHelper.mListeners.iterator();
            while (it.hasNext()) {
                ((UserInfoHelper$UserListener) it.next()).onUserChanged(userIdentifier, 2);
            }
            ISLocationManager iSLocationManager = LocationManagerService.mISLocationManager;
            LocationManagerService locationManagerService = this.mService;
            locationManagerService.logLocationEnabledState();
            locationManagerService.logEmergencyState();
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            LifecycleUserInfoHelper lifecycleUserInfoHelper = this.mUserInfoHelper;
            lifecycleUserInfoHelper.getClass();
            Log.d("LocationManagerService", "u" + userIdentifier + " stopped");
            Iterator it = lifecycleUserInfoHelper.mListeners.iterator();
            while (it.hasNext()) {
                ((UserInfoHelper$UserListener) it.next()).onUserChanged(userIdentifier, 3);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(
                SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            int userIdentifier = targetUser.getUserIdentifier();
            int userIdentifier2 = targetUser2.getUserIdentifier();
            LifecycleUserInfoHelper lifecycleUserInfoHelper = this.mUserInfoHelper;
            UserManager userManager = lifecycleUserInfoHelper.getUserManager();
            Preconditions.checkState(userManager != null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int[] enabledProfileIds = userManager.getEnabledProfileIds(userIdentifier);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                UserManager userManager2 = lifecycleUserInfoHelper.getUserManager();
                Preconditions.checkState(userManager2 != null);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int[] enabledProfileIds2 = userManager2.getEnabledProfileIds(userIdentifier2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Log.d(
                            "LocationManagerService",
                            "current user changed from u"
                                    + Arrays.toString(enabledProfileIds)
                                    + " to u"
                                    + Arrays.toString(enabledProfileIds2));
                    LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                    locationEventLog.getClass();
                    locationEventLog.addLog$1(
                            new LocationEventLog.UserSwitchedEvent(
                                    userIdentifier, userIdentifier2));
                    Iterator it = lifecycleUserInfoHelper.mListeners.iterator();
                    while (it.hasNext()) {
                        UserInfoHelper$UserListener userInfoHelper$UserListener =
                                (UserInfoHelper$UserListener) it.next();
                        for (int i : enabledProfileIds) {
                            userInfoHelper$UserListener.onUserChanged(i, 1);
                        }
                    }
                    Iterator it2 = lifecycleUserInfoHelper.mListeners.iterator();
                    while (it2.hasNext()) {
                        UserInfoHelper$UserListener userInfoHelper$UserListener2 =
                                (UserInfoHelper$UserListener) it2.next();
                        for (int i2 : enabledProfileIds2) {
                            userInfoHelper$UserListener2.onUserChanged(i2, 1);
                        }
                    }
                } finally {
                }
            } finally {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends LocationManagerInternal {
        public LocalService() {}

        public final void addProviderEnabledListener(
                String str,
                LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            LocationProviderManager locationProviderManager =
                    LocationManagerService.this.getLocationProviderManager(str);
            Objects.requireNonNull(locationProviderManager);
            locationProviderManager.addEnabledListener(providerEnabledListener);
        }

        public final LocationTime getGnssTimeMillis() {
            Location lastLocationUnsafe;
            LocationProviderManager locationProviderManager =
                    LocationManagerService.this.getLocationProviderManager("gps");
            if (locationProviderManager == null
                    || (lastLocationUnsafe =
                                    locationProviderManager.getLastLocationUnsafe(
                                            -1, 2, false, Long.MAX_VALUE))
                            == null) {
                return null;
            }
            return new LocationTime(
                    lastLocationUnsafe.getTime(), lastLocationUnsafe.getElapsedRealtimeNanos());
        }

        public final boolean isProvider(String str, CallerIdentity callerIdentity) {
            Iterator it = LocationManagerService.this.mProviderManagers.iterator();
            while (it.hasNext()) {
                LocationProviderManager locationProviderManager =
                        (LocationProviderManager) it.next();
                if (str == null || str.equals(locationProviderManager.mName)) {
                    if (callerIdentity.equals(
                                    ((AbstractLocationProvider.InternalState)
                                                    locationProviderManager.mProvider.mInternalState
                                                            .get())
                                            .state
                                            .identity)
                            && locationProviderManager.isVisibleToCaller()) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final boolean isProviderEnabledForUser(String str, int i) {
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            false,
                            "isProviderEnabledForUser",
                            null);
            LocationProviderManager locationProviderManager =
                    LocationManagerService.this.getLocationProviderManager(str);
            if (locationProviderManager == null) {
                return false;
            }
            return locationProviderManager.isEnabled(handleIncomingUser);
        }

        public final void removeProviderEnabledListener(
                String str,
                LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            LocationProviderManager locationProviderManager =
                    LocationManagerService.this.getLocationProviderManager(str);
            Objects.requireNonNull(locationProviderManager);
            locationProviderManager.removeEnabledListener(providerEnabledListener);
        }

        public final void setLocationPackageTagsListener(
                LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener) {
            synchronized (LocationManagerService.this.mLock) {
                try {
                    LocationManagerService.this.mLocationTagsChangedListener =
                            locationPackageTagsListener;
                    if (locationPackageTagsListener != null) {
                        ArraySet arraySet =
                                new ArraySet(LocationManagerService.this.mProviderManagers.size());
                        Iterator it = LocationManagerService.this.mProviderManagers.iterator();
                        while (it.hasNext()) {
                            CallerIdentity callerIdentity =
                                    ((AbstractLocationProvider.InternalState)
                                                    ((LocationProviderManager) it.next())
                                                            .mProvider.mInternalState.get())
                                            .state
                                            .identity;
                            if (callerIdentity != null) {
                                arraySet.add(Integer.valueOf(callerIdentity.getUid()));
                            }
                        }
                        Iterator it2 = arraySet.iterator();
                        while (it2.hasNext()) {
                            int intValue = ((Integer) it2.next()).intValue();
                            PackageTagsList calculateAppOpsLocationSourceTags =
                                    LocationManagerService.this.calculateAppOpsLocationSourceTags(
                                            intValue);
                            if (!calculateAppOpsLocationSourceTags.isEmpty()) {
                                LocationManagerService.this.mHandler.post(
                                        new LocationManagerService$$ExternalSyntheticLambda12(
                                                locationPackageTagsListener,
                                                intValue,
                                                calculateAppOpsLocationSourceTags,
                                                2));
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessListener implements ActivityManager.SemProcessListener {
        public ProcessListener() {}

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {}

        public final void onProcessDied(int i, final int i2) {
            if (LocationManagerService.this.mMockLocationRecord.isEmpty()) {
                return;
            }
            LocationManagerService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.location.LocationManagerService$ProcessListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LocationManagerService.ProcessListener processListener =
                                    LocationManagerService.ProcessListener.this;
                            int i3 = i2;
                            synchronized (LocationManagerService.this.mLock) {
                                LocationManagerService locationManagerService =
                                        LocationManagerService.this;
                                String[] packagesForUid =
                                        locationManagerService.mPackageManager.getPackagesForUid(
                                                i3);
                                if (packagesForUid != null) {
                                    for (String str : packagesForUid) {
                                        locationManagerService.recoverRealProviderLocked(str);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemInjector implements Injector {
        public final SystemAlarmHelper mAlarmHelper;
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
        public final SystemPackageResetHelper mPackageResetHelper;
        public final SystemScreenInteractiveHelper mScreenInteractiveHelper;
        public final SystemSettingsHelper mSettingsHelper;
        public boolean mSystemReady;
        public final Lifecycle.LifecycleUserInfoHelper mUserInfoHelper;

        public SystemInjector(
                Context context, Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper) {
            this.mContext = context;
            this.mUserInfoHelper = lifecycleUserInfoHelper;
            this.mLocationSettings = new LocationSettings(context);
            this.mAlarmHelper = new SystemAlarmHelper(context);
            SystemAppOpsHelper systemAppOpsHelper = new SystemAppOpsHelper(context);
            this.mAppOpsHelper = systemAppOpsHelper;
            SystemLocationPermissionsHelper systemLocationPermissionsHelper =
                    new SystemLocationPermissionsHelper(context, systemAppOpsHelper);
            this.mLocationPermissionsHelper = systemLocationPermissionsHelper;
            this.mSettingsHelper = new SystemSettingsHelper(context);
            SystemAppForegroundHelper systemAppForegroundHelper =
                    new SystemAppForegroundHelper(context);
            this.mAppForegroundHelper = systemAppForegroundHelper;
            this.mLocationPowerSaveModeHelper = new SystemLocationPowerSaveModeHelper(context);
            this.mScreenInteractiveHelper = new SystemScreenInteractiveHelper(context);
            this.mDeviceStationaryHelper = new SystemDeviceStationaryHelper();
            this.mDeviceIdleHelper = new SystemDeviceIdleHelper(context);
            LocationUsageLogger locationUsageLogger = new LocationUsageLogger();
            locationUsageLogger.mLastApiUsageLogHour = 0L;
            locationUsageLogger.mApiUsageLogHourlyCount = 0;
            this.mLocationUsageLogger = locationUsageLogger;
            this.mPackageResetHelper = new SystemPackageResetHelper(context);
            NSConnectionHelper nSConnectionHelper = new NSConnectionHelper();
            Log.i("NSConnectionHelper", "constructor");
            this.mNSConnectionHelper = nSConnectionHelper;
            NSPermissionHelper nSPermissionHelper =
                    new NSPermissionHelper(lifecycleUserInfoHelper, nSConnectionHelper);
            this.mNSPermissionHelper = nSPermissionHelper;
            this.mNSLocationProviderHelper =
                    new NSLocationProviderHelper(context, nSPermissionHelper, nSConnectionHelper);
            systemAppForegroundHelper.mNSPermissionHelper = nSPermissionHelper;
            systemLocationPermissionsHelper.mNSPermissionHelper = nSPermissionHelper;
            systemLocationPermissionsHelper.mSLocationConsumer =
                    new LocationManagerService$SystemInjector$$ExternalSyntheticLambda0();
        }

        public final synchronized SystemEmergencyHelper getEmergencyHelper() {
            try {
                if (this.mEmergencyCallHelper == null) {
                    SystemEmergencyHelper systemEmergencyHelper =
                            new SystemEmergencyHelper(this.mContext);
                    this.mEmergencyCallHelper = systemEmergencyHelper;
                    if (this.mSystemReady) {
                        systemEmergencyHelper.onSystemReady();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.mEmergencyCallHelper;
        }
    }

    public LocationManagerService(Context context, SystemInjector systemInjector) {
        int i;
        this.mContext = context.createAttributionContext("LocationService");
        this.mInjector = systemInjector;
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        LocalServices.addService(LocationManagerInternal.class, localService);
        try {
            i = Integer.parseInt(SystemProperties.get("persist.sys.gps.dds.subId", "0"));
        } catch (NumberFormatException unused) {
            Log.w("LocationManagerService", "Sim slot property has wrong value, set 0");
            i = 0;
        }
        String string =
                SemCarrierFeature.getInstance()
                        .getString(i, "CarrierFeature_GPS_ConfigAgpsSetting", "", false);
        string =
                (string == null || string.length() == 0)
                        ? SystemProperties.get("ro.csc.sales_code")
                        : string;
        CarrierConfig carrierConfig = CarrierConfig.getInstance();
        this.mCarrierConfig = carrierConfig;
        carrierConfig.mSalesCode = string;
        carrierConfig.mCarrier = carrierConfig.getCarrier();
        this.mGeofenceManager = new GeofenceManager(this.mContext, systemInjector);
        ((SystemInjector) this.mInjector)
                .mLocationSettings.mUserSettingsListeners.add(
                        new LocationSettings.LocationUserSettingsListener() { // from class:
                            // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda3
                            @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
                            public final void onLocationUserSettingsChanged(
                                    int i2,
                                    LocationUserSettings locationUserSettings,
                                    LocationUserSettings locationUserSettings2) {
                                LocationManagerService locationManagerService =
                                        LocationManagerService.this;
                                locationManagerService.getClass();
                                boolean z = locationUserSettings.mAdasGnssLocationEnabled;
                                boolean z2 = locationUserSettings2.mAdasGnssLocationEnabled;
                                if (z != z2) {
                                    Log.d(
                                            "LocationManagerService",
                                            "[u" + i2 + "] adas gnss location enabled = " + z2);
                                    LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                                    locationEventLog.getClass();
                                    locationEventLog.addLog$1(
                                            new LocationEventLog.LocationEnabledEvent(i2, 1, z2));
                                    locationManagerService.mContext.sendBroadcastAsUser(
                                            new Intent(
                                                            "android.location.action.ADAS_GNSS_ENABLED_CHANGED")
                                                    .putExtra(
                                                            "android.location.extra.ADAS_GNSS_ENABLED",
                                                            z2)
                                                    .addFlags(1073741824)
                                                    .addFlags(268435456),
                                            UserHandle.of(i2));
                                }
                            }
                        });
        ((SystemInjector) this.mInjector)
                .mSettingsHelper.mLocationMode.addListener(
                        new SettingsHelper$UserSettingChangedListener() { // from class:
                                                                          // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda4
                            @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                            public final void onSettingChanged(int i2) {
                                LocationManagerService locationManagerService =
                                        LocationManagerService.this;
                                boolean isLocationEnabled =
                                        ((LocationManagerService.SystemInjector)
                                                        locationManagerService.mInjector)
                                                .mSettingsHelper.isLocationEnabled(i2);
                                LocationManager.invalidateLocalLocationEnabledCaches();
                                Log.d(
                                        "LocationManagerService",
                                        "[u" + i2 + "] location enabled = " + isLocationEnabled);
                                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                                locationEventLog.getClass();
                                locationEventLog.addLog$1(
                                        new LocationEventLog.LocationEnabledEvent(
                                                i2, 0, isLocationEnabled));
                                locationManagerService.logLocationEnabledState();
                                locationManagerService.mContext.sendBroadcastAsUser(
                                        new Intent("android.location.MODE_CHANGED")
                                                .putExtra(
                                                        "android.location.extra.LOCATION_ENABLED",
                                                        isLocationEnabled)
                                                .addFlags(1073741824)
                                                .addFlags(268435456),
                                        UserHandle.of(i2));
                                locationManagerService.refreshAppOpsRestrictions(i2);
                            }
                        });
        final int i2 = 0;
        ((SystemInjector) this.mInjector)
                .mSettingsHelper.mAdasPackageAllowlist.mListeners.add(
                        new SettingsHelper$GlobalSettingChangedListener(
                                this) { // from class:
                                        // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda5
                            public final /* synthetic */ LocationManagerService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                            public final void onSettingChanged() {
                                switch (i2) {
                                    case 0:
                                        this.f$0.refreshAppOpsRestrictions(-1);
                                        break;
                                    default:
                                        this.f$0.refreshAppOpsRestrictions(-1);
                                        break;
                                }
                            }
                        });
        final int i3 = 1;
        ((SystemInjector) this.mInjector)
                .mSettingsHelper.mIgnoreSettingsPackageAllowlist.mListeners.add(
                        new SettingsHelper$GlobalSettingChangedListener(
                                this) { // from class:
                                        // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda5
                            public final /* synthetic */ LocationManagerService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                            public final void onSettingChanged() {
                                switch (i3) {
                                    case 0:
                                        this.f$0.refreshAppOpsRestrictions(-1);
                                        break;
                                    default:
                                        this.f$0.refreshAppOpsRestrictions(-1);
                                        break;
                                }
                            }
                        });
        ((SystemInjector) this.mInjector)
                .mUserInfoHelper.mListeners.add(
                        new UserInfoHelper$UserListener() { // from class:
                                                            // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda7
                            @Override // com.android.server.location.injector.UserInfoHelper$UserListener
                            public final void onUserChanged(int i4, int i5) {
                                LocationManagerService locationManagerService =
                                        LocationManagerService.this;
                                if (i5 == 2) {
                                    locationManagerService.refreshAppOpsRestrictions(i4);
                                } else {
                                    locationManagerService.getClass();
                                }
                            }
                        });
        ((SystemInjector) this.mInjector)
                .getEmergencyHelper()
                .mListeners
                .add(
                        new EmergencyHelper
                                .EmergencyStateChangedListener() { // from class:
                                                                   // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda8
                            @Override // com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener
                            public final void onStateChanged() {
                                LocationManagerService.this.logEmergencyState();
                            }
                        });
        PassiveLocationProviderManager passiveLocationProviderManager =
                new PassiveLocationProviderManager(this.mContext, systemInjector, "passive", null);
        this.mPassiveManager = passiveLocationProviderManager;
        AbstractLocationProvider passiveLocationProvider =
                new PassiveLocationProvider(
                        ConcurrentUtils.DIRECT_EXECUTOR,
                        CallerIdentity.fromContext(this.mContext),
                        PassiveLocationProvider.PROPERTIES,
                        Collections.emptySet());
        passiveLocationProvider.setState(
                new AbstractLocationProvider$$ExternalSyntheticLambda0(true));
        addLocationProviderManager(passiveLocationProviderManager, passiveLocationProvider);
        LegacyPermissionManagerService.Internal internal =
                (LegacyPermissionManagerService.Internal)
                        LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        if (carrierConfig.isChinaCarrier()) {
            final int i4 = 0;
            LegacyPermissionManagerInternal$PackagesProvider
                    legacyPermissionManagerInternal$PackagesProvider =
                            new LegacyPermissionManagerInternal$PackagesProvider(
                                    this) { // from class:
                                            // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda9
                                public final /* synthetic */ LocationManagerService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                                public final String[] getPackages(int i5) {
                                    switch (i4) {
                                        case 0:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array.wfcOperatorErrorAlertMessages);
                                        case 1:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .vendor_required_attestation_certificates);
                                        default:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .vendor_required_apps_managed_user);
                                    }
                                }
                            };
            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                    LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
            synchronized (defaultPermissionGrantPolicy.mLock) {
                defaultPermissionGrantPolicy.mLocationPackagesProvider =
                        legacyPermissionManagerInternal$PackagesProvider;
            }
        } else {
            final int i5 = 1;
            LegacyPermissionManagerInternal$PackagesProvider
                    legacyPermissionManagerInternal$PackagesProvider2 =
                            new LegacyPermissionManagerInternal$PackagesProvider(
                                    this) { // from class:
                                            // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda9
                                public final /* synthetic */ LocationManagerService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                                public final String[] getPackages(int i52) {
                                    switch (i5) {
                                        case 0:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array.wfcOperatorErrorAlertMessages);
                                        case 1:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .vendor_required_attestation_certificates);
                                        default:
                                            return this.f$0
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .vendor_required_apps_managed_user);
                                    }
                                }
                            };
            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy2 =
                    LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
            synchronized (defaultPermissionGrantPolicy2.mLock) {
                defaultPermissionGrantPolicy2.mLocationPackagesProvider =
                        legacyPermissionManagerInternal$PackagesProvider2;
            }
        }
        final int i6 = 2;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider3 =
                        new LegacyPermissionManagerInternal$PackagesProvider(
                                this) { // from class:
                                        // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda9
                            public final /* synthetic */ LocationManagerService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                            public final String[] getPackages(int i52) {
                                switch (i6) {
                                    case 0:
                                        return this.f$0
                                                .mContext
                                                .getResources()
                                                .getStringArray(
                                                        R.array.wfcOperatorErrorAlertMessages);
                                    case 1:
                                        return this.f$0
                                                .mContext
                                                .getResources()
                                                .getStringArray(
                                                        R.array
                                                                .vendor_required_attestation_certificates);
                                    default:
                                        return this.f$0
                                                .mContext
                                                .getResources()
                                                .getStringArray(
                                                        R.array.vendor_required_apps_managed_user);
                                }
                            }
                        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy3 =
                LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy3.mLock) {
            defaultPermissionGrantPolicy3.mLocationExtraPackagesProvider =
                    legacyPermissionManagerInternal$PackagesProvider3;
        }
        this.mNSLocationMonitor = new NSLocationMonitor(this.mContext, systemInjector);
    }

    public final void addGnssAntennaInfoListener(
            IGnssAntennaInfoListener iGnssAntennaInfoListener,
            String str,
            String str2,
            String str3) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            CallerIdentity fromBinder =
                    CallerIdentity.fromBinder(gnssManagerService.mContext, str, str2, str3);
            GnssAntennaInfoProvider gnssAntennaInfoProvider =
                    gnssManagerService.mGnssAntennaInfoProvider;
            gnssAntennaInfoProvider.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                gnssAntennaInfoProvider.putRegistration(
                        iGnssAntennaInfoListener.asBinder(),
                        gnssAntennaInfoProvider
                        .new AntennaInfoListenerRegistration(fromBinder, iGnssAntennaInfoListener));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void addGnssMeasurementsListener(
            GnssMeasurementRequest gnssMeasurementRequest,
            IGnssMeasurementsListener iGnssMeasurementsListener,
            String str,
            String str2,
            String str3) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_FINE_LOCATION", null);
            if (gnssMeasurementRequest.isCorrelationVectorOutputsEnabled()) {
                gnssManagerService.mContext.enforceCallingOrSelfPermission(
                        "android.permission.LOCATION_HARDWARE", null);
            }
            gnssManagerService.mGnssMeasurementsProvider.addListener(
                    gnssMeasurementRequest,
                    CallerIdentity.fromBinder(gnssManagerService.mContext, str, str2, str3),
                    iGnssMeasurementsListener);
        }
    }

    public final void addGnssNavigationMessageListener(
            IGnssNavigationMessageListener iGnssNavigationMessageListener,
            String str,
            String str2,
            String str3) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_FINE_LOCATION", null);
            gnssManagerService.mGnssNavigationMessageProvider.addListener(
                    null,
                    CallerIdentity.fromBinder(gnssManagerService.mContext, str, str2, str3),
                    iGnssNavigationMessageListener);
        }
    }

    public void addLocationProviderManager(
            LocationProviderManager locationProviderManager,
            AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mProviderManagers) {
            try {
                Preconditions.checkState(
                        getLocationProviderManager(locationProviderManager.mName) == null);
                locationProviderManager.startManager(this);
                if (abstractLocationProvider != null) {
                    if (locationProviderManager != this.mPassiveManager) {
                        if (Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "location_enable_stationary_throttle",
                                        !this.mContext
                                                        .getPackageManager()
                                                        .hasSystemFeature(
                                                                "android.hardware.type.watch")
                                                ? 1
                                                : 0)
                                != 0) {
                            abstractLocationProvider =
                                    new StationaryThrottlingLocationProvider(
                                            locationProviderManager.mName,
                                            (SystemInjector) this.mInjector,
                                            abstractLocationProvider);
                        }
                    }
                    locationProviderManager.setRealProvider(abstractLocationProvider);
                }
                this.mProviderManagers.add(locationProviderManager);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addProviderRequestListener(
            IProviderRequestListener iProviderRequestListener) {
        addProviderRequestListener_enforcePermission();
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (locationProviderManager.isVisibleToCaller()) {
                locationProviderManager.mProviderRequestListeners.add(iProviderRequestListener);
            }
        }
    }

    public final void addTestProvider(
            String str,
            ProviderProperties providerProperties,
            List list,
            String str2,
            String str3) {
        CallerIdentity fromBinderUnsafe = CallerIdentity.fromBinderUnsafe(str2, str3);
        if (((SystemInjector) this.mInjector).mAppOpsHelper.noteOp(fromBinderUnsafe)) {
            NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
            nSLocationMonitor.getClass();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isRegister", true);
            bundle.putString("provider", str);
            bundle.putString("packageName", str2);
            nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.MOCK_PROVIDER_CHANGED, bundle);
            getOrAddLocationProviderManager(str)
                    .setMockProvider(
                            new MockLocationProvider(
                                    providerProperties, fromBinderUnsafe, new ArraySet(list)));
            synchronized (this.mLock) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final PackageTagsList calculateAppOpsLocationSourceTags(int i) {
        PackageTagsList.Builder builder = new PackageTagsList.Builder();
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            AbstractLocationProvider.State state =
                    ((AbstractLocationProvider.InternalState)
                                    locationProviderManager.mProvider.mInternalState.get())
                            .state;
            CallerIdentity callerIdentity = state.identity;
            if (callerIdentity != null && callerIdentity.getUid() == i) {
                builder.add(state.identity.getPackageName(), state.extraAttributionTags);
                if (state.extraAttributionTags.isEmpty()
                        || state.identity.getAttributionTag() != null) {
                    builder.add(
                            state.identity.getPackageName(), state.identity.getAttributionTag());
                } else {
                    Log.e(
                            "LocationManagerService",
                            locationProviderManager.mName
                                    + " provider has specified a null attribution tag and a"
                                    + " non-empty set of extra attribution tags - dropping the null"
                                    + " attribution tag");
                }
            }
        }
        return builder.build();
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

    public final void createNotificationForMockLocation(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Resources resources = this.mContext.getResources();
                NotificationManager notificationManager =
                        (NotificationManager) this.mContext.getSystemService("notification");
                notificationManager.createNotificationChannel(
                        new NotificationChannel(
                                "Mock Location Reminder",
                                resources.getString(R.string.region_picker_section_all),
                                4));
                Intent intent = new Intent("com.samsung.android.location.mock.delete");
                intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
                PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 64);
                CharSequence applicationLabel =
                        this.mPackageManager.getApplicationLabel(packageInfo.applicationInfo);
                Drawable applicationIcon =
                        this.mPackageManager.getApplicationIcon(packageInfo.applicationInfo);
                Bitmap createBitmap =
                        Bitmap.createBitmap(
                                applicationIcon.getIntrinsicWidth(),
                                applicationIcon.getIntrinsicHeight(),
                                Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                applicationIcon.setBounds(
                        0,
                        0,
                        applicationIcon.getIntrinsicWidth(),
                        applicationIcon.getIntrinsicHeight());
                applicationIcon.draw(canvas);
                String string = resources.getString(R.string.region_picker_section_all);
                String string2 = resources.getString(R.string.redo, applicationLabel);
                notificationManager.notify(
                        0,
                        new Notification.Builder(this.mContext, "Mock Location Reminder")
                                .setContentTitle(string)
                                .setContentText(string2)
                                .setSmallIcon(R.drawable.iconfactory_adaptive_icon_drawable_wrapper)
                                .setLargeIcon(createBitmap)
                                .setAutoCancel(true)
                                .setStyle(new Notification.BigTextStyle().bigText(string2))
                                .setActions(
                                        new Notification.Action.Builder(
                                                        (Icon) null,
                                                        resources.getString(
                                                                R.string
                                                                        .reduce_bright_colors_feature_name),
                                                        PendingIntent.getBroadcast(
                                                                this.mContext,
                                                                0,
                                                                intent,
                                                                1275068416))
                                                .build())
                                .build());
                this.mActivityManager.semRegisterProcessListener(this.mProcessListener);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("LocationManagerService", "missing package: " + str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ArrayMap arrayMap;
        ArrayMap arrayMap2;
        if (DumpUtils.checkDumpPermission(this.mContext, "LocationManagerService", printWriter)) {
            final IndentingPrintWriter indentingPrintWriter =
                    new IndentingPrintWriter(printWriter, "  ");
            if (strArr.length > 0) {
                LocationProviderManager locationProviderManager =
                        getLocationProviderManager(strArr[0]);
                if (locationProviderManager != null) {
                    indentingPrintWriter.println("Provider:");
                    indentingPrintWriter.increaseIndent();
                    locationProviderManager.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Event Log:");
                    indentingPrintWriter.increaseIndent();
                    LocationEventLog.EVENT_LOG.iterate(
                            locationProviderManager.mName,
                            new Consumer() { // from class:
                                             // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    indentingPrintWriter.println((String) obj);
                                }
                            });
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
            Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper =
                    ((SystemInjector) this.mInjector).mUserInfoHelper;
            int[] runningUserIds = lifecycleUserInfoHelper.getRunningUserIds();
            if (runningUserIds.length > 1) {
                indentingPrintWriter.println("running users: u" + Arrays.toString(runningUserIds));
            }
            ActivityManagerInternal activityManagerInternal =
                    lifecycleUserInfoHelper.getActivityManagerInternal();
            if (activityManagerInternal != null) {
                int[] currentProfileIds = activityManagerInternal.getCurrentProfileIds();
                indentingPrintWriter.println(
                        "current users: u" + Arrays.toString(currentProfileIds));
                UserManager userManager = lifecycleUserInfoHelper.getUserManager();
                if (userManager != null) {
                    for (int i : currentProfileIds) {
                        if (userManager.hasUserRestrictionForUser(
                                "no_share_location", UserHandle.of(i))) {
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("u" + i + " restricted");
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Location Settings:");
            indentingPrintWriter.increaseIndent();
            SystemSettingsHelper systemSettingsHelper =
                    ((SystemInjector) this.mInjector).mSettingsHelper;
            systemSettingsHelper.getClass();
            try {
                int[] runningUserIds2 = ActivityManager.getService().getRunningUserIds();
                indentingPrintWriter.print("Location Setting: ");
                indentingPrintWriter.increaseIndent();
                if (runningUserIds2.length > 1) {
                    indentingPrintWriter.println();
                    for (int i2 : runningUserIds2) {
                        indentingPrintWriter.print("[u");
                        indentingPrintWriter.print(i2);
                        indentingPrintWriter.print("] ");
                        indentingPrintWriter.println(systemSettingsHelper.isLocationEnabled(i2));
                    }
                } else {
                    indentingPrintWriter.println(
                            systemSettingsHelper.isLocationEnabled(runningUserIds2[0]));
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Location Allow/Deny Packages:");
                indentingPrintWriter.increaseIndent();
                int length = runningUserIds2.length;
                SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting =
                        systemSettingsHelper.mLocationPackageWhitelist;
                SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting2 =
                        systemSettingsHelper.mLocationPackageBlacklist;
                if (length > 1) {
                    for (int i3 : runningUserIds2) {
                        List<String> valueForUser =
                                stringListCachedSecureSetting2.getValueForUser(i3);
                        if (!valueForUser.isEmpty()) {
                            indentingPrintWriter.print("user ");
                            indentingPrintWriter.print(i3);
                            indentingPrintWriter.println(":");
                            indentingPrintWriter.increaseIndent();
                            for (String str : valueForUser) {
                                indentingPrintWriter.print("[deny] ");
                                indentingPrintWriter.println(str);
                            }
                            for (String str2 : stringListCachedSecureSetting.getValueForUser(i3)) {
                                indentingPrintWriter.print("[allow] ");
                                indentingPrintWriter.println(str2);
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                } else {
                    for (String str3 :
                            stringListCachedSecureSetting2.getValueForUser(runningUserIds2[0])) {
                        indentingPrintWriter.print("[deny] ");
                        indentingPrintWriter.println(str3);
                    }
                    for (String str4 :
                            stringListCachedSecureSetting.getValueForUser(runningUserIds2[0])) {
                        indentingPrintWriter.print("[allow] ");
                        indentingPrintWriter.println(str4);
                    }
                }
                indentingPrintWriter.decreaseIndent();
                ArraySet arraySet =
                        (ArraySet)
                                systemSettingsHelper.mBackgroundThrottlePackageWhitelist.getValue();
                if (!arraySet.isEmpty()) {
                    indentingPrintWriter.println("Throttling Allow Packages:");
                    indentingPrintWriter.increaseIndent();
                    Iterator it = arraySet.iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println((String) it.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (!systemSettingsHelper.mBackgroundThrottlePackageAllowlistByNsflp.isEmpty()) {
                    indentingPrintWriter.println("Throttling Allow Packages by nsflp:");
                    indentingPrintWriter.increaseIndent();
                    Iterator it2 =
                            systemSettingsHelper.mBackgroundThrottlePackageAllowlistByNsflp
                                    .iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.println((String) it2.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                PackageTagsList value =
                        systemSettingsHelper.mIgnoreSettingsPackageAllowlist.getValue();
                if (!value.isEmpty()) {
                    indentingPrintWriter.println("Emergency Bypass Allow Packages:");
                    indentingPrintWriter.increaseIndent();
                    value.dump(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                PackageTagsList value2 = systemSettingsHelper.mAdasPackageAllowlist.getValue();
                if (!value2.isEmpty()) {
                    indentingPrintWriter.println("ADAS Bypass Allow Packages:");
                    indentingPrintWriter.increaseIndent();
                    value2.dump(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                LocationSettings locationSettings =
                        ((SystemInjector) this.mInjector).mLocationSettings;
                locationSettings.getClass();
                try {
                    int[] runningUserIds3 = ActivityManager.getService().getRunningUserIds();
                    if (locationSettings
                            .mContext
                            .getPackageManager()
                            .hasSystemFeature("android.hardware.type.automotive")) {
                        indentingPrintWriter.print("ADAS Location Setting: ");
                        indentingPrintWriter.increaseIndent();
                        if (runningUserIds3.length > 1) {
                            indentingPrintWriter.println();
                            for (int i4 : runningUserIds3) {
                                indentingPrintWriter.print("[u");
                                indentingPrintWriter.print(i4);
                                indentingPrintWriter.print("] ");
                                indentingPrintWriter.println(
                                        locationSettings.getUserSettings(i4)
                                                .mAdasGnssLocationEnabled);
                            }
                        } else {
                            indentingPrintWriter.println(
                                    locationSettings.getUserSettings(runningUserIds3[0])
                                            .mAdasGnssLocationEnabled);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    synchronized (this.mLock) {
                        try {
                            if (this.mExtraLocationControllerPackage != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Location Controller Extra Package: ");
                                sb.append(this.mExtraLocationControllerPackage);
                                sb.append(
                                        this.mExtraLocationControllerPackageEnabled
                                                ? " [enabled]"
                                                : " [disabled]");
                                indentingPrintWriter.println(sb.toString());
                            }
                        } finally {
                        }
                    }
                    indentingPrintWriter.println("Location Providers:");
                    indentingPrintWriter.increaseIndent();
                    Iterator it3 = this.mProviderManagers.iterator();
                    while (it3.hasNext()) {
                        ((LocationProviderManager) it3.next())
                                .dump(fileDescriptor, indentingPrintWriter, strArr);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Historical Aggregate Location Provider Data:");
                    indentingPrintWriter.increaseIndent();
                    LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                    synchronized (locationEventLog.mAggregateStats) {
                        try {
                            arrayMap = new ArrayMap(locationEventLog.mAggregateStats);
                            for (int i5 = 0; i5 < arrayMap.size(); i5++) {
                                arrayMap.setValueAt(
                                        i5, new ArrayMap((ArrayMap) arrayMap.valueAt(i5)));
                            }
                        } finally {
                        }
                    }
                    for (int i6 = 0; i6 < arrayMap.size(); i6++) {
                        indentingPrintWriter.print((String) arrayMap.keyAt(i6));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        ArrayMap arrayMap3 = (ArrayMap) arrayMap.valueAt(i6);
                        for (int i7 = 0; i7 < arrayMap3.size(); i7++) {
                            indentingPrintWriter.print(arrayMap3.keyAt(i7));
                            indentingPrintWriter.print(": ");
                            ((LocationEventLog.AggregateStats) arrayMap3.valueAt(i7))
                                    .updateTotals();
                            indentingPrintWriter.println(arrayMap3.valueAt(i7));
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println(
                            "Historical Aggregate Gnss Measurement Provider Data:");
                    indentingPrintWriter.increaseIndent();
                    LocationEventLog locationEventLog2 = LocationEventLog.EVENT_LOG;
                    synchronized (locationEventLog2.mGnssMeasAggregateStats) {
                        arrayMap2 = new ArrayMap(locationEventLog2.mGnssMeasAggregateStats);
                    }
                    for (int i8 = 0; i8 < arrayMap2.size(); i8++) {
                        indentingPrintWriter.print(arrayMap2.keyAt(i8));
                        indentingPrintWriter.print(": ");
                        ((LocationEventLog.GnssMeasurementAggregateStats) arrayMap2.valueAt(i8))
                                .updateTotals();
                        indentingPrintWriter.println(arrayMap2.valueAt(i8));
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
                    LocationEventLog.EVENT_LOG.iterate(
                            (String) null,
                            new Consumer() { // from class:
                                             // com.android.server.location.LocationManagerService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    indentingPrintWriter.println((String) obj);
                                }
                            });
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Mock Location Record:");
                    for (Map.Entry entry : this.mMockLocationRecord.entrySet()) {
                        indentingPrintWriter.println(
                                "    "
                                        + ((String) entry.getKey())
                                        + ": "
                                        + ((String) entry.getValue()));
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    public final void flushGnssBatch() {
        flushGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            try {
                ILocationListener iLocationListener = this.mDeprecatedGnssBatchingListener;
                if (iLocationListener != null) {
                    requestListenerFlush("gps", iLocationListener, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forwardGeocode(
            ForwardGeocodeRequest forwardGeocodeRequest, IGeocodeCallback iGeocodeCallback) {
        Preconditions.checkArgument(
                CallerIdentity.fromBinder(
                                        this.mContext,
                                        forwardGeocodeRequest.getCallingPackage(),
                                        forwardGeocodeRequest.getCallingAttributionTag())
                                .getUid()
                        == forwardGeocodeRequest.getCallingUid());
        ProxyGeocodeProvider proxyGeocodeProvider = this.mGeocodeProvider;
        if (proxyGeocodeProvider != null) {
            proxyGeocodeProvider.mServiceWatcher.runOnBinder(
                    new ProxyGeocodeProvider.AnonymousClass1(
                            forwardGeocodeRequest, iGeocodeCallback));
        } else {
            try {
                iGeocodeCallback.onError((String) null);
            } catch (RemoteException unused) {
            }
        }
    }

    public final PackageTagsList getAdasAllowlist() {
        return ((SystemInjector) this.mInjector).mSettingsHelper.mAdasPackageAllowlist.getValue();
    }

    public final List getAllProviders() {
        ArrayList arrayList = new ArrayList(this.mProviderManagers.size());
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (locationProviderManager.isVisibleToCaller()) {
                arrayList.add(locationProviderManager.mName);
            }
        }
        return arrayList;
    }

    public final String[] getBackgroundThrottlingWhitelist() {
        return (String[])
                ((ArraySet)
                                ((SystemInjector) this.mInjector)
                                        .mSettingsHelper.mBackgroundThrottlePackageWhitelist
                                                .getValue())
                        .toArray(new String[0]);
    }

    public final String getBestProvider(Criteria criteria, boolean z) {
        List providers;
        synchronized (this.mLock) {
            try {
                providers = getProviders(criteria, z);
                if (providers.isEmpty()) {
                    providers = getProviders(null, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (providers.isEmpty()) {
            return null;
        }
        return providers.contains("fused")
                ? "fused"
                : providers.contains("gps")
                        ? "gps"
                        : providers.contains("network") ? "network" : (String) providers.get(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ICancellationSignal getCurrentLocation(
            java.lang.String r10,
            android.location.LocationRequest r11,
            final android.location.ILocationCallback r12,
            java.lang.String r13,
            java.lang.String r14,
            java.lang.String r15) {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext
            android.location.util.identity.CallerIdentity r4 = android.location.util.identity.CallerIdentity.fromBinder(r0, r13, r14, r15)
            android.content.Context r13 = r9.mContext
            int r15 = r4.getUid()
            int r0 = r4.getPid()
            int r13 = com.android.server.location.LocationPermissions.getPermissionLevel(r13, r15, r0)
            boolean r15 = android.location.flags.Flags.enableLocationBypass()
            r0 = 2
            r7 = 1
            if (r15 == 0) goto L32
            if (r13 != 0) goto L39
            android.content.Context r15 = r9.mContext
            java.lang.String r1 = "android.permission.LOCATION_BYPASS"
            int r15 = r15.checkCallingPermission(r1)
            if (r15 == 0) goto L30
            int r15 = r4.getUid()
            com.android.server.location.LocationPermissions.enforceLocationPermission(r15, r13, r7)
            goto L39
        L30:
            r6 = r0
            goto L3a
        L32:
            int r15 = r4.getUid()
            com.android.server.location.LocationPermissions.enforceLocationPermission(r15, r13, r7)
        L39:
            r6 = r13
        L3a:
            int r13 = r4.getPid()
            int r15 = android.os.Process.myPid()
            r8 = 0
            if (r13 != r15) goto L4a
            if (r14 == 0) goto L48
            goto L4a
        L48:
            r13 = r8
            goto L4b
        L4a:
            r13 = r7
        L4b:
            com.android.internal.util.Preconditions.checkState(r13)
            android.location.LocationRequest r11 = r9.validateLocationRequest(r10, r11, r4)
            com.android.server.location.provider.LocationProviderManager r9 = r9.getLocationProviderManager(r10)
            if (r9 == 0) goto L5a
            r13 = r7
            goto L5b
        L5a:
            r13 = r8
        L5b:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "provider \""
            r14.<init>(r15)
            r14.append(r10)
            java.lang.String r10 = "\" does not exist"
            r14.append(r10)
            java.lang.String r10 = r14.toString()
            com.android.internal.util.Preconditions.checkArgument(r13, r10)
            r9.getClass()
            long r13 = r11.getDurationMillis()
            r1 = 30000(0x7530, double:1.4822E-319)
            int r10 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r10 <= 0) goto L8e
            android.location.LocationRequest$Builder r10 = new android.location.LocationRequest$Builder
            r10.<init>(r11)
            android.location.LocationRequest$Builder r10 = r10.setDurationMillis(r1)
            android.location.LocationRequest r10 = r10.build()
            r3 = r10
            goto L8f
        L8e:
            r3 = r11
        L8f:
            com.android.server.location.provider.LocationProviderManager$GetCurrentLocationListenerRegistration r10 = new com.android.server.location.provider.LocationProviderManager$GetCurrentLocationListenerRegistration
            com.android.server.location.provider.LocationProviderManager$GetCurrentLocationTransport r5 = new com.android.server.location.provider.LocationProviderManager$GetCurrentLocationTransport
            r5.<init>(r12)
            r1 = r10
            r2 = r9
            r1.<init>(r3, r4, r5, r6)
            java.lang.Object r11 = r9.mMultiplexerLock
            monitor-enter(r11)
            int r13 = r9.mState     // Catch: java.lang.Throwable -> Ld3
            if (r13 == r0) goto La3
            goto La4
        La3:
            r7 = r8
        La4:
            com.android.internal.util.Preconditions.checkState(r7)     // Catch: java.lang.Throwable -> Ld3
            long r13 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> Ld3
            android.os.IBinder r15 = r12.asBinder()     // Catch: java.lang.Throwable -> Ld5
            r9.putRegistration(r15, r10)     // Catch: java.lang.Throwable -> Ld5
            boolean r15 = r10.mActive     // Catch: java.lang.Throwable -> Ld5
            if (r15 != 0) goto Lbe
            r15 = 0
            com.android.internal.listeners.ListenerExecutor$ListenerOperation r15 = r10.acceptLocationChange(r15)     // Catch: java.lang.Throwable -> Ld5
            r10.executeOperation(r15)     // Catch: java.lang.Throwable -> Ld5
        Lbe:
            android.os.Binder.restoreCallingIdentity(r13)     // Catch: java.lang.Throwable -> Ld3
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Ld3
            android.os.ICancellationSignal r11 = android.os.CancellationSignal.createTransport()
            android.os.CancellationSignal r13 = android.os.CancellationSignal.fromTransport(r11)
            com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda17 r14 = new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda17
            r14.<init>()
            r13.setOnCancelListener(r14)
            return r11
        Ld3:
            r9 = move-exception
            goto Lda
        Ld5:
            r9 = move-exception
            android.os.Binder.restoreCallingIdentity(r13)     // Catch: java.lang.Throwable -> Ld3
            throw r9     // Catch: java.lang.Throwable -> Ld3
        Lda:
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Ld3
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.LocationManagerService.getCurrentLocation(java.lang.String,"
                    + " android.location.LocationRequest, android.location.ILocationCallback,"
                    + " java.lang.String, java.lang.String,"
                    + " java.lang.String):android.os.ICancellationSignal");
    }

    public final String getExtraLocationControllerPackage() {
        String str;
        synchronized (this.mLock) {
            str = this.mExtraLocationControllerPackage;
        }
        return str;
    }

    public final List getGPSUsingApps() {
        if (Binder.getCallingUid() != 1000) {
            return null;
        }
        NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
        nSLocationMonitor.getClass();
        Log.i("NSLocationMonitor", "getGPSUsingApps() called");
        INSLocationManager iNSLocationManager = nSLocationMonitor.mMonitorService;
        if (iNSLocationManager == null) {
            Log.w("NSLocationMonitor", "NSMonitorService is not connected, return null");
            return null;
        }
        try {
            return iNSLocationManager.getGPSUsingApps();
        } catch (Exception e) {
            Log.e(
                    "NSLocationMonitor",
                    "failed to getGPSUsingApps due to  " + e.toString() + ", return null");
            return null;
        }
    }

    public final List getGnssAntennaInfos() {
        if (this.mGnssManagerService == null) {
            return null;
        }
        return this.mGnssManagerService.mGnssAntennaInfoProvider.mAntennaInfos;
    }

    public final int getGnssBatchSize() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.mGnssLocationProvider.getBatchSize();
    }

    public final GnssCapabilities getGnssCapabilities() {
        return this.mGnssManagerService == null
                ? new GnssCapabilities.Builder().build()
                : this.mGnssManagerService.mGnssNative.mCapabilities;
    }

    public final String getGnssHardwareModelName() {
        return this.mGnssManagerService == null
                ? ""
                : this.mGnssManagerService.mGnssNative.mHardwareModelName;
    }

    public final LocationTime getGnssTimeMillis() {
        return this.mLocalService.getGnssTimeMillis();
    }

    public final int getGnssYearOfHardware() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.mGnssNative.mHardwareYear;
    }

    public final PackageTagsList getIgnoreSettingsAllowlist() {
        return ((SystemInjector) this.mInjector)
                .mSettingsHelper.mIgnoreSettingsPackageAllowlist.getValue();
    }

    public final Location getLastLocation(
            String str, LastLocationRequest lastLocationRequest, String str2, String str3) {
        boolean z;
        CallerIdentity fromBinder = CallerIdentity.fromBinder(this.mContext, str2, str3);
        int permissionLevel =
                LocationPermissions.getPermissionLevel(
                        this.mContext, fromBinder.getUid(), fromBinder.getPid());
        if (!Flags.enableLocationBypass()) {
            LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        } else if (permissionLevel == 0) {
            if (this.mContext.checkCallingPermission("android.permission.LOCATION_BYPASS") != 0) {
                LocationPermissions.enforceLocationPermission(
                        fromBinder.getUid(), permissionLevel, 1);
            } else {
                permissionLevel = 2;
            }
        }
        boolean z2 = false;
        Preconditions.checkArgument(
                (fromBinder.getPid() == Process.myPid() && str3 == null) ? false : true);
        LastLocationRequest build = new LastLocationRequest.Builder(lastLocationRequest).build();
        boolean isProvider = this.mLocalService.isProvider(null, fromBinder);
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.UPDATE_APP_OPS_STATS",
                    "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext
                    .getPackageManager()
                    .hasSystemFeature("android.hardware.type.automotive")) {
                throw new IllegalArgumentException(
                        "adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new IllegalArgumentException(
                        "adas gnss bypass requests are only allowed on the \"gps\" provider");
            }
            if (!isProvider) {
                LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
            }
        }
        if (build.isLocationSettingsIgnored() && !isProvider) {
            LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        }
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            return null;
        }
        LastLocationRequest.Builder builder = new LastLocationRequest.Builder(build);
        boolean isLocationSettingsIgnored = build.isLocationSettingsIgnored();
        SystemSettingsHelper systemSettingsHelper = locationProviderManager.mSettingsHelper;
        if (isLocationSettingsIgnored) {
            if (!systemSettingsHelper
                            .mIgnoreSettingsPackageAllowlist
                            .getValue()
                            .contains(fromBinder.getPackageName(), fromBinder.getAttributionTag())
                    && !locationProviderManager.mLocationManagerInternal.isProvider(
                            (String) null, fromBinder)) {
                isLocationSettingsIgnored = false;
            }
            builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
        }
        boolean isAdasGnssBypass = build.isAdasGnssBypass();
        if (isAdasGnssBypass) {
            if ("gps".equals(locationProviderManager.mName)) {
                int userId = fromBinder.getUserId();
                ActivityManagerInternal activityManagerInternal =
                        locationProviderManager.mUserHelper.getActivityManagerInternal();
                if (activityManagerInternal != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        z = activityManagerInternal.isCurrentProfile(userId);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } else {
                    z = false;
                }
                if (z
                        && locationProviderManager.mLocationSettings.getUserSettings(
                                        fromBinder.getUserId())
                                .mAdasGnssLocationEnabled
                        && systemSettingsHelper
                                .mAdasPackageAllowlist
                                .getValue()
                                .contains(
                                        fromBinder.getPackageName(),
                                        fromBinder.getAttributionTag())) {
                    z2 = isAdasGnssBypass;
                }
            } else {
                Log.e(
                        "LocationManagerService",
                        "adas gnss bypass request received in non-gps provider");
            }
            builder.setAdasGnssBypass(z2);
        }
        LastLocationRequest build2 = builder.build();
        if (!locationProviderManager.isActive(build2.isBypass(), fromBinder)) {
            return null;
        }
        Location lastLocationUnsafe =
                locationProviderManager.getLastLocationUnsafe(
                        fromBinder.getUserId(), permissionLevel, build2.isBypass(), Long.MAX_VALUE);
        if (permissionLevel == 1) {
            lastLocationUnsafe =
                    lastLocationUnsafe != null
                            ? locationProviderManager.mLocationFudger.createCoarse(
                                    lastLocationUnsafe)
                            : null;
        } else if (permissionLevel != 2) {
            throw new AssertionError();
        }
        if (lastLocationUnsafe != null) {
            if (!locationProviderManager.mAppOpsHelper.noteOpNoThrow(
                    (Flags.enableLocationBypass()
                                    && !locationProviderManager.mLocationPermissionsHelper
                                            .hasLocationPermissions(permissionLevel, fromBinder)
                                    && locationProviderManager.mEmergencyHelper.isInEmergency(0L)
                                    && locationProviderManager.mContext.checkPermission(
                                                    "android.permission.LOCATION_BYPASS",
                                                    fromBinder.getPid(),
                                                    fromBinder.getUid())
                                            == 0)
                            ? 147
                            : LocationPermissions.asAppOp(permissionLevel),
                    fromBinder)) {
                return null;
            }
            if (fromBinder.getPid() == Process.myPid()) {
                return new Location(lastLocationUnsafe);
            }
        }
        return lastLocationUnsafe;
    }

    public final LocationProviderManager getLocationProviderManager(String str) {
        if (str == null) {
            return null;
        }
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (str.equals(locationProviderManager.mName)) {
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
            try {
                Iterator it = this.mProviderManagers.iterator();
                while (it.hasNext()) {
                    LocationProviderManager locationProviderManager =
                            (LocationProviderManager) it.next();
                    if (str.equals(locationProviderManager.mName)) {
                        return locationProviderManager;
                    }
                }
                LocationProviderManager locationProviderManager2 =
                        new LocationProviderManager(
                                this.mContext, this.mInjector, str, this.mPassiveManager);
                addLocationProviderManager(locationProviderManager2, null);
                return locationProviderManager2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getProviderPackages(String str) {
        CallerIdentity callerIdentity;
        getProviderPackages_enforcePermission();
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager != null
                && (callerIdentity =
                                ((AbstractLocationProvider.InternalState)
                                                locationProviderManager.mProvider.mInternalState
                                                        .get())
                                        .state
                                        .identity)
                        != null) {
            return Collections.singletonList(callerIdentity.getPackageName());
        }
        return Collections.emptyList();
    }

    public final ProviderProperties getProviderProperties(String str) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(
                locationProviderManager != null, "provider \"" + str + "\" does not exist");
        return ((AbstractLocationProvider.InternalState)
                        locationProviderManager.mProvider.mInternalState.get())
                .state
                .properties;
    }

    public final List getProviders(Criteria criteria, boolean z) {
        ArrayList arrayList;
        if (LocationPermissions.getPermissionLevel(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid())
                < 1) {
            return Collections.emptyList();
        }
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList(this.mProviderManagers.size());
                Iterator it = this.mProviderManagers.iterator();
                while (it.hasNext()) {
                    LocationProviderManager locationProviderManager =
                            (LocationProviderManager) it.next();
                    if (locationProviderManager.isVisibleToCaller()) {
                        String str = locationProviderManager.mName;
                        if (!z
                                || locationProviderManager.isEnabled(
                                        UserHandle.getCallingUserId())) {
                            if (criteria == null
                                    || LocationProvider.propertiesMeetCriteria(
                                            str,
                                            ((AbstractLocationProvider.InternalState)
                                                            locationProviderManager.mProvider
                                                                    .mInternalState.get())
                                                    .state
                                                    .properties,
                                            criteria)) {
                                arrayList.add(str);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int handleShellCommand(
            ParcelFileDescriptor parcelFileDescriptor,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3,
            String[] strArr) {
        return new LocationShellCommand(this.mContext, this)
                .exec(
                        this,
                        parcelFileDescriptor.getFileDescriptor(),
                        parcelFileDescriptor2.getFileDescriptor(),
                        parcelFileDescriptor3.getFileDescriptor(),
                        strArr);
    }

    public final boolean hasProvider(String str) {
        return getLocationProviderManager(str) != null;
    }

    public final void injectGnssMeasurementCorrections(
            GnssMeasurementCorrections gnssMeasurementCorrections) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.LOCATION_HARDWARE", null);
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_FINE_LOCATION", null);
            if (gnssManagerService.mGnssNative.injectMeasurementCorrections(
                    gnssMeasurementCorrections)) {
                return;
            }
            Log.w("GnssManager", "failed to inject GNSS measurement corrections");
        }
    }

    public final void injectLocation(Location location) {
        injectLocation_enforcePermission();
        Preconditions.checkArgument(location.isComplete());
        int callingUserId = UserHandle.getCallingUserId();
        LocationProviderManager locationProviderManager =
                getLocationProviderManager(location.getProvider());
        if (locationProviderManager == null || !locationProviderManager.isEnabled(callingUserId)) {
            return;
        }
        synchronized (locationProviderManager.mMultiplexerLock) {
            try {
                Preconditions.checkState(locationProviderManager.mState != 2);
                if (locationProviderManager.getLastLocationUnsafe(
                                callingUserId, 2, false, Long.MAX_VALUE)
                        == null) {
                    locationProviderManager.setLastLocation(callingUserId, location);
                }
            } finally {
            }
        }
    }

    public final boolean isAdasGnssLocationEnabledForUser(int i) {
        return ((SystemInjector) this.mInjector)
                .mLocationSettings.getUserSettings(
                                ActivityManager.handleIncomingUser(
                                        Binder.getCallingPid(),
                                        Binder.getCallingUid(),
                                        i,
                                        false,
                                        false,
                                        "isAdasGnssLocationEnabledForUser",
                                        null))
                        .mAdasGnssLocationEnabled;
    }

    public final boolean isAutomotiveGnssSuspended() {
        isAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException(
                    "isAutomotiveGnssSuspended only allowed on automotive devices");
        }
        boolean z = false;
        if (this.mGnssManagerService == null) {
            return false;
        }
        GnssLocationProviderSec gnssLocationProviderSec =
                this.mGnssManagerService.mGnssLocationProvider;
        synchronized (gnssLocationProviderSec.mLock) {
            try {
                if (gnssLocationProviderSec.mAutomotiveSuspend
                        && !gnssLocationProviderSec.mGpsEnabled) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isExtraLocationControllerPackageEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z =
                        this.mExtraLocationControllerPackageEnabled
                                && this.mExtraLocationControllerPackage != null;
            } finally {
            }
        }
        return z;
    }

    public final boolean isGeocodeAvailable() {
        return this.mGeocodeProvider != null;
    }

    public final boolean isLocationEnabledForUser(int i) {
        return ((SystemInjector) this.mInjector)
                .mSettingsHelper.isLocationEnabled(
                        ActivityManager.handleIncomingUser(
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                i,
                                false,
                                false,
                                "isLocationEnabledForUser",
                                null));
    }

    public final boolean isProviderEnabledForUser(String str, int i) {
        return this.mLocalService.isProviderEnabledForUser(str, i);
    }

    public final boolean isProviderPackage(String str, String str2, String str3) {
        isProviderPackage_enforcePermission();
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            if (str == null || str.equals(locationProviderManager.mName)) {
                CallerIdentity callerIdentity =
                        ((AbstractLocationProvider.InternalState)
                                        locationProviderManager.mProvider.mInternalState.get())
                                .state
                                .identity;
                if (callerIdentity != null
                        && callerIdentity.getPackageName().equals(str2)
                        && (str3 == null
                                || Objects.equals(callerIdentity.getAttributionTag(), str3))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void logEmergencyState() {
        boolean isInEmergency =
                ((SystemInjector) this.mInjector)
                        .getEmergencyHelper()
                        .isInEmergency(Long.MIN_VALUE);
        synchronized (((SystemInjector) this.mInjector).mLocationUsageLogger) {
            FrameworkStatsLog.write(FrameworkStatsLog.EMERGENCY_STATE_CHANGED, isInEmergency);
        }
    }

    public final void logLocationEnabledState() {
        boolean z = false;
        for (int i : ((SystemInjector) this.mInjector).mUserInfoHelper.getRunningUserIds()) {
            z = ((SystemInjector) this.mInjector).mSettingsHelper.isLocationEnabled(i);
            if (z) {
                break;
            }
        }
        synchronized (((SystemInjector) this.mInjector).mLocationUsageLogger) {
            FrameworkStatsLog.write(FrameworkStatsLog.LOCATION_ENABLED_STATE_CHANGED, z);
        }
    }

    public final void notifyNSFLP(Message message) {
        Handler handler;
        Bundle data;
        String string;
        Handler handler2;
        NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
        nSLocationMonitor.getClass();
        if (message == null) {
            Log.w("NSLocationMonitor", "onMessageUpdated, message is null");
            return;
        }
        if (message.what != 200) {
            NSConnectionHelper nSConnectionHelper = nSLocationMonitor.mNSConnectionHelper;
            if (nSConnectionHelper.mHasNsflpFeature
                    && (handler = nSConnectionHelper.mHandler) != null) {
                handler.post(
                        new NSConnectionHelper$$ExternalSyntheticLambda0(
                                nSConnectionHelper, message, 3));
                return;
            }
            return;
        }
        NSLocationProviderHelper nSLocationProviderHelper =
                ((SystemInjector) nSLocationMonitor.mInjector).mNSLocationProviderHelper;
        nSLocationProviderHelper.getClass();
        try {
            if (message.arg1 == 59
                    && (data = message.getData()) != null
                    && (string = data.getString("interfaceName")) != null
                    && "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                            .equals(string)) {
                int i = data.getInt("uid");
                int i2 = data.getInt("pid");
                nSLocationProviderHelper.updateUidProcState(i, data);
                data.putInt(
                        "permissionLevel",
                        LocationPermissions.getPermissionLevel(
                                nSLocationProviderHelper.mContext, i, i2));
                NSConnectionHelper nSConnectionHelper2 =
                        nSLocationProviderHelper.mNSConnectionHelper;
                if (nSConnectionHelper2.mHasNsflpFeature
                        && (handler2 = nSConnectionHelper2.mHandler) != null) {
                    handler2.post(
                            new NSConnectionHelper$$ExternalSyntheticLambda0(
                                    nSConnectionHelper2, message, 3));
                }
            }
        } catch (Exception e) {
            Log.w("NSLocationProviderHelper", "Failed to update GmsApiRequest, " + e.toString());
        }
    }

    public final void onFreezeStateChanged(boolean z, int i) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        Log.w("LocationManagerService", "onFreezeStateChanged, uid[" + i + "]=" + z);
        SystemLocationPermissionsHelper systemLocationPermissionsHelper =
                ((SystemInjector) this.mInjector).mLocationPermissionsHelper;
        if (systemLocationPermissionsHelper.mFreezedUids.contains(Integer.valueOf(i)) == z) {
            return;
        }
        if (z) {
            systemLocationPermissionsHelper.mFreezedUids.add(Integer.valueOf(i));
        } else {
            systemLocationPermissionsHelper.mFreezedUids.remove(Integer.valueOf(i));
        }
        NSPermissionHelper nSPermissionHelper = systemLocationPermissionsHelper.mNSPermissionHelper;
        if (nSPermissionHelper != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("enabled", z);
            bundle.putInt("uid", i);
            nSPermissionHelper.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.FREEZE_STATE_CHANGED, bundle);
        }
        Iterator it = systemLocationPermissionsHelper.mListeners.iterator();
        while (it.hasNext()) {
            ((LocationPermissionsHelper$LocationPermissionsListener) it.next())
                    .onLocationPermissionsChanged(i);
        }
    }

    public final void recoverRealProviderLocked(String str) {
        AbstractLocationProvider abstractLocationProvider;
        Iterator it = this.mMockLocationRecord.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String str2 = (String) entry.getKey();
            String str3 = (String) entry.getValue();
            if (str3.equals(str)) {
                Log.d(
                        "LocationManagerService",
                        "remove mock location. package: " + str3 + "provider name: " + str2);
                LocationProviderManager locationProviderManager = getLocationProviderManager(str2);
                if (locationProviderManager == null) {
                    return;
                }
                locationProviderManager.setMockProvider(null);
                MockableLocationProvider mockableLocationProvider =
                        locationProviderManager.mProvider;
                synchronized (mockableLocationProvider.mOwnerLock) {
                    abstractLocationProvider = mockableLocationProvider.mProvider;
                }
                if (abstractLocationProvider == null) {
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void refreshAppOpsRestrictions(int i) {
        PackageTagsList packageTagsList;
        if (i == -1) {
            for (int i2 : ((SystemInjector) this.mInjector).mUserInfoHelper.getRunningUserIds()) {
                refreshAppOpsRestrictions(i2);
            }
            return;
        }
        Preconditions.checkArgument(i >= 0);
        boolean isLocationEnabled =
                ((SystemInjector) this.mInjector).mSettingsHelper.isLocationEnabled(i);
        if (isLocationEnabled) {
            packageTagsList = null;
        } else {
            PackageTagsList.Builder builder = new PackageTagsList.Builder();
            Iterator it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                CallerIdentity callerIdentity =
                        ((AbstractLocationProvider.InternalState)
                                        ((LocationProviderManager) it.next())
                                                .mProvider.mInternalState.get())
                                .state
                                .identity;
                if (callerIdentity != null) {
                    builder.add(
                            callerIdentity.getPackageName(), callerIdentity.getAttributionTag());
                }
            }
            builder.add(
                    ((SystemInjector) this.mInjector)
                            .mSettingsHelper.mIgnoreSettingsPackageAllowlist.getValue());
            builder.add(
                    ((SystemInjector) this.mInjector)
                            .mSettingsHelper.mAdasPackageAllowlist.getValue());
            packageTagsList = builder.build();
        }
        AppOpsManager appOpsManager =
                (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        Objects.requireNonNull(appOpsManager);
        boolean z = true ^ isLocationEnabled;
        appOpsManager.setUserRestrictionForUser(0, z, this, packageTagsList, i);
        appOpsManager.setUserRestrictionForUser(1, z, this, packageTagsList, i);
    }

    public final void registerGnssNmeaCallback(
            IGnssNmeaListener iGnssNmeaListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_FINE_LOCATION", null);
            CallerIdentity fromBinder =
                    CallerIdentity.fromBinder(gnssManagerService.mContext, str, str2, str3);
            GnssNmeaProvider gnssNmeaProvider = gnssManagerService.mGnssNmeaProvider;
            gnssNmeaProvider.addListener(null, fromBinder, iGnssNmeaListener);
            Log.i(
                    "GnssNmeaProvider",
                    "addListener: adding NMEA listener("
                            + Integer.toHexString(System.identityHashCode(iGnssNmeaListener))
                            + ") from "
                            + fromBinder.getPackageName());
            if (gnssNmeaProvider.mNmeaAllowed == 0) {
                gnssNmeaProvider.mNmeaAllowed = 1;
                gnssNmeaProvider.onGnssNmeaListenerStatusChanged(1);
            }
        }
    }

    public final void registerGnssStatusCallback(
            IGnssStatusListener iGnssStatusListener, String str, String str2, String str3) {
        if (this.mGnssManagerService != null) {
            GnssManagerService gnssManagerService = this.mGnssManagerService;
            gnssManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_FINE_LOCATION", null);
            gnssManagerService.mGnssStatusProvider.addListener(
                    null,
                    CallerIdentity.fromBinder(gnssManagerService.mContext, str, str2, str3),
                    iGnssStatusListener);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void registerLocationListener(
            java.lang.String r10,
            android.location.LocationRequest r11,
            android.location.ILocationListener r12,
            java.lang.String r13,
            java.lang.String r14,
            java.lang.String r15) {
        /*
            r9 = this;
            java.lang.Class<android.app.ActivityManagerInternal> r0 = android.app.ActivityManagerInternal.class
            java.lang.Object r0 = com.android.server.LocalServices.getService(r0)
            android.app.ActivityManagerInternal r0 = (android.app.ActivityManagerInternal) r0
            if (r0 == 0) goto L16
            int r1 = android.os.Binder.getCallingUid()
            int r2 = android.os.Binder.getCallingPid()
            r3 = 3
            r0.logFgsApiBegin(r3, r1, r2)
        L16:
            android.content.Context r0 = r9.mContext
            android.location.util.identity.CallerIdentity r13 = android.location.util.identity.CallerIdentity.fromBinder(r0, r13, r14, r15)
            android.content.Context r15 = r9.mContext
            int r0 = r13.getUid()
            int r1 = r13.getPid()
            int r15 = com.android.server.location.LocationPermissions.getPermissionLevel(r15, r0, r1)
            boolean r0 = android.location.flags.Flags.enableLocationBypass()
            r7 = 2
            r8 = 1
            if (r0 == 0) goto L48
            if (r15 != 0) goto L4f
            android.content.Context r0 = r9.mContext
            java.lang.String r1 = "android.permission.LOCATION_BYPASS"
            int r0 = r0.checkCallingPermission(r1)
            if (r0 == 0) goto L46
            int r0 = r13.getUid()
            com.android.server.location.LocationPermissions.enforceLocationPermission(r0, r15, r8)
            goto L4f
        L46:
            r6 = r7
            goto L50
        L48:
            int r0 = r13.getUid()
            com.android.server.location.LocationPermissions.enforceLocationPermission(r0, r15, r8)
        L4f:
            r6 = r15
        L50:
            int r15 = r13.getPid()
            int r0 = android.os.Process.myPid()
            if (r15 != r0) goto L69
            if (r14 != 0) goto L69
            java.lang.String r14 = "LocationManagerService"
            java.lang.String r15 = "system location request with no attribution tag"
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            android.util.Log.w(r14, r15, r0)
        L69:
            android.location.LocationRequest r3 = r9.validateLocationRequest(r10, r11, r13)
            com.android.server.location.provider.LocationProviderManager r9 = r9.getLocationProviderManager(r10)
            r11 = 0
            if (r9 == 0) goto L76
            r14 = r8
            goto L77
        L76:
            r14 = r11
        L77:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            java.lang.String r0 = "provider \""
            r15.<init>(r0)
            r15.append(r10)
            java.lang.String r10 = "\" does not exist"
            r15.append(r10)
            java.lang.String r10 = r15.toString()
            com.android.internal.util.Preconditions.checkArgument(r14, r10)
            r9.getClass()
            com.android.server.location.provider.LocationProviderManager$LocationListenerRegistration r10 = new com.android.server.location.provider.LocationProviderManager$LocationListenerRegistration
            com.android.server.location.provider.LocationProviderManager$LocationListenerTransport r5 = new com.android.server.location.provider.LocationProviderManager$LocationListenerTransport
            r5.<init>(r12)
            r1 = r10
            r2 = r9
            r4 = r13
            r1.<init>(r3, r4, r5, r6)
            java.lang.Object r14 = r9.mMultiplexerLock
            monitor-enter(r14)
            int r15 = r9.mState     // Catch: java.lang.Throwable -> Ld0
            if (r15 == r7) goto La6
            goto La7
        La6:
            r8 = r11
        La7:
            com.android.internal.util.Preconditions.checkState(r8)     // Catch: java.lang.Throwable -> Ld0
            long r0 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> Ld0
            int r11 = r13.getUid()     // Catch: java.lang.Throwable -> Ld2
            r15 = 1000(0x3e8, float:1.401E-42)
            if (r11 == r15) goto Lc4
            com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda35 r11 = new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda35     // Catch: java.lang.Throwable -> Ld2
            r15 = 3
            r11.<init>(r15, r13)     // Catch: java.lang.Throwable -> Ld2
            int r11 = r9.getRegistrationCountWith(r11)     // Catch: java.lang.Throwable -> Ld2
            r13 = 30
            if (r11 >= r13) goto Lcb
        Lc4:
            android.os.IBinder r11 = r12.asBinder()     // Catch: java.lang.Throwable -> Ld2
            r9.putRegistration(r11, r10)     // Catch: java.lang.Throwable -> Ld2
        Lcb:
            android.os.Binder.restoreCallingIdentity(r0)     // Catch: java.lang.Throwable -> Ld0
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Ld0
            return
        Ld0:
            r9 = move-exception
            goto Ld7
        Ld2:
            r9 = move-exception
            android.os.Binder.restoreCallingIdentity(r0)     // Catch: java.lang.Throwable -> Ld0
            throw r9     // Catch: java.lang.Throwable -> Ld0
        Ld7:
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Ld0
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.LocationManagerService.registerLocationListener(java.lang.String,"
                    + " android.location.LocationRequest, android.location.ILocationListener,"
                    + " java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void registerLocationPendingIntent(
            java.lang.String r17,
            android.location.LocationRequest r18,
            android.app.PendingIntent r19,
            java.lang.String r20,
            java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.LocationManagerService.registerLocationPendingIntent(java.lang.String,"
                    + " android.location.LocationRequest, android.app.PendingIntent,"
                    + " java.lang.String, java.lang.String):void");
    }

    public final void removeGeofence(PendingIntent pendingIntent) {
        GeofenceManager geofenceManager = this.mGeofenceManager;
        geofenceManager.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            geofenceManager.removeRegistrationIf(
                    new GeofenceManager$$ExternalSyntheticLambda4(0, pendingIntent));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeGnssAntennaInfoListener(
            IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        if (this.mGnssManagerService != null) {
            GnssAntennaInfoProvider gnssAntennaInfoProvider =
                    this.mGnssManagerService.mGnssAntennaInfoProvider;
            gnssAntennaInfoProvider.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                gnssAntennaInfoProvider.removeRegistration(iGnssAntennaInfoListener.asBinder());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void removeGnssMeasurementsListener(
            IGnssMeasurementsListener iGnssMeasurementsListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.mGnssMeasurementsProvider.removeListener(
                    iGnssMeasurementsListener);
        }
    }

    public final void removeGnssNavigationMessageListener(
            IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.mGnssNavigationMessageProvider.removeListener(
                    iGnssNavigationMessageListener);
        }
    }

    public final void removeLocationProviderManager(
            LocationProviderManager locationProviderManager) {
        synchronized (this.mProviderManagers) {
            Preconditions.checkArgument(this.mProviderManagers.remove(locationProviderManager));
            locationProviderManager.setMockProvider(null);
            locationProviderManager.setRealProvider(null);
            locationProviderManager.stopManager();
        }
    }

    public final void removeProviderRequestListener(
            IProviderRequestListener iProviderRequestListener) {
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            ((LocationProviderManager) it.next())
                    .mProviderRequestListeners.remove(iProviderRequestListener);
        }
    }

    public final void removeTestProvider(String str, String str2, String str3) {
        AbstractLocationProvider abstractLocationProvider;
        if (((SystemInjector) this.mInjector)
                .mAppOpsHelper.noteOp(CallerIdentity.fromBinderUnsafe(str2, str3))) {
            synchronized (this.mLock) {
                try {
                    LocationProviderManager locationProviderManager =
                            getLocationProviderManager(str);
                    if (locationProviderManager == null) {
                        return;
                    }
                    NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
                    nSLocationMonitor.getClass();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isRegister", false);
                    bundle.putString("provider", str);
                    bundle.putString("packageName", str2);
                    nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                            LocationConstants.STATE_TYPE.MOCK_PROVIDER_CHANGED, bundle);
                    locationProviderManager.setMockProvider(null);
                    MockableLocationProvider mockableLocationProvider =
                            locationProviderManager.mProvider;
                    synchronized (mockableLocationProvider.mOwnerLock) {
                        abstractLocationProvider = mockableLocationProvider.mProvider;
                    }
                    if (abstractLocationProvider == null) {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void requestGeofence(
            Geofence geofence, PendingIntent pendingIntent, String str, String str2) {
        GeofenceManager geofenceManager = this.mGeofenceManager;
        LocationPermissions.enforceLocationPermission(
                Binder.getCallingUid(),
                LocationPermissions.getPermissionLevel(
                        geofenceManager.mContext, Binder.getCallingUid(), Binder.getCallingPid()),
                2);
        CallerIdentity fromBinder =
                CallerIdentity.fromBinder(
                        geofenceManager.mContext,
                        str,
                        str2,
                        AppOpsManager.toReceiverId(pendingIntent));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            geofenceManager.putRegistration(
                    new GeofenceManager.GeofenceKey(pendingIntent, geofence),
                    geofenceManager.new GeofenceRegistration(geofence, fromBinder, pendingIntent));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestListenerFlush(String str, ILocationListener iLocationListener, int i) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(
                locationProviderManager != null, "provider \"" + str + "\" does not exist");
        Objects.requireNonNull(iLocationListener);
        ILocationListener iLocationListener2 = iLocationListener;
        locationProviderManager.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (locationProviderManager.updateRegistration(
                    new LocationProviderManager$$ExternalSyntheticLambda19(i, 2),
                    iLocationListener2.asBinder())) {
            } else {
                throw new IllegalArgumentException("unregistered listener cannot be flushed");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestPendingIntentFlush(String str, PendingIntent pendingIntent, int i) {
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        Preconditions.checkArgument(
                locationProviderManager != null, "provider \"" + str + "\" does not exist");
        Objects.requireNonNull(pendingIntent);
        locationProviderManager.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (locationProviderManager.updateRegistration(
                    new LocationProviderManager$$ExternalSyntheticLambda19(i, 1), pendingIntent)) {
            } else {
                throw new IllegalArgumentException("unregistered pending intent cannot be flushed");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reverseGeocode(
            ReverseGeocodeRequest reverseGeocodeRequest, IGeocodeCallback iGeocodeCallback) {
        Preconditions.checkArgument(
                CallerIdentity.fromBinder(
                                        this.mContext,
                                        reverseGeocodeRequest.getCallingPackage(),
                                        reverseGeocodeRequest.getCallingAttributionTag())
                                .getUid()
                        == reverseGeocodeRequest.getCallingUid());
        ProxyGeocodeProvider proxyGeocodeProvider = this.mGeocodeProvider;
        if (proxyGeocodeProvider != null) {
            proxyGeocodeProvider.mServiceWatcher.runOnBinder(
                    new ProxyGeocodeProvider.AnonymousClass1(
                            reverseGeocodeRequest, iGeocodeCallback));
        } else {
            try {
                iGeocodeCallback.onError((String) null);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void sendExtraCommand(String str, String str2, Bundle bundle) {
        LocationPermissions.enforceLocationPermission(
                Binder.getCallingUid(),
                LocationPermissions.getPermissionLevel(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid()),
                1);
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", null);
        Objects.requireNonNull(str);
        LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager != null) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            Objects.requireNonNull(str2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                locationProviderManager.mProvider.mController.sendExtraCommand(
                        callingUid, str2, bundle, callingPid);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
                nSLocationMonitor.getClass();
                Bundle bundle2 = new Bundle();
                int callingPid2 = Binder.getCallingPid();
                int callingUid2 = Binder.getCallingUid();
                bundle2.putString("provider", str);
                bundle2.putString("command", str2);
                bundle2.putInt("pid", callingPid2);
                bundle2.putInt("uid", callingUid2);
                nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                        LocationConstants.STATE_TYPE.SEND_EXTRA_COMMAND, bundle2);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        ((SystemInjector) this.mInjector).mLocationUsageLogger.logLocationApiUsage(0, str);
        ((SystemInjector) this.mInjector).mLocationUsageLogger.logLocationApiUsage(1, str);
    }

    public final void setAdasGnssLocationEnabledForUser(boolean z, int i) {
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        false,
                        "setAdasGnssLocationEnabledForUser",
                        null);
        LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        final LocationSettings.LocationUserSettingsStore userSettingsStore =
                ((SystemInjector) this.mInjector)
                        .mLocationSettings.getUserSettingsStore(handleIncomingUser);
        synchronized (userSettingsStore) {
            userSettingsStore.initializeCache();
            final LocationUserSettings locationUserSettings = userSettingsStore.mCache;
            final LocationUserSettings filterSettings =
                    userSettingsStore.filterSettings(
                            z == locationUserSettings.mAdasGnssLocationEnabled
                                    ? locationUserSettings
                                    : new LocationUserSettings(z));
            if (locationUserSettings.equals(filterSettings)) {
                return;
            }
            userSettingsStore.mCache = filterSettings;
            Preconditions.checkState(true);
            BackgroundThread.getExecutor()
                    .execute(
                            new SettingsStore$$ExternalSyntheticLambda1(
                                    userSettingsStore, filterSettings, 1));
            LocationServiceThread.getExecutor()
                    .execute(
                            new Runnable() { // from class:
                                             // com.android.server.location.settings.LocationSettings$LocationUserSettingsStore$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LocationSettings.LocationUserSettingsStore
                                            locationUserSettingsStore =
                                                    LocationSettings.LocationUserSettingsStore.this;
                                    LocationUserSettings locationUserSettings2 =
                                            locationUserSettings;
                                    LocationUserSettings locationUserSettings3 = filterSettings;
                                    Iterator it =
                                            LocationSettings.this.mUserSettingsListeners.iterator();
                                    while (it.hasNext()) {
                                        ((LocationSettings.LocationUserSettingsListener) it.next())
                                                .onLocationUserSettingsChanged(
                                                        locationUserSettingsStore.mUserId,
                                                        locationUserSettings2,
                                                        locationUserSettings3);
                                    }
                                }
                            });
        }
    }

    public final void setAutomotiveGnssSuspended(boolean z) {
        setAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException(
                    "setAutomotiveGnssSuspended only allowed on automotive devices");
        }
        if (this.mGnssManagerService != null) {
            GnssLocationProviderSec gnssLocationProviderSec =
                    this.mGnssManagerService.mGnssLocationProvider;
            synchronized (gnssLocationProviderSec.mLock) {
                gnssLocationProviderSec.mAutomotiveSuspend = z;
            }
            gnssLocationProviderSec.mHandler.post(
                    new GnssLocationProvider$$ExternalSyntheticLambda0(gnssLocationProviderSec, 4));
        }
    }

    public final void setExtraLocationControllerPackage(String str) {
        setExtraLocationControllerPackage_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackage = str;
        }
    }

    public final void setExtraLocationControllerPackageEnabled(boolean z) {
        setExtraLocationControllerPackageEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackageEnabled = z;
        }
    }

    public final void setLocationEnabledForUser(boolean z, int i) {
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        false,
                        "setLocationEnabledForUser",
                        null);
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.WRITE_SECURE_SETTINGS", null);
        LocationManager.invalidateLocalLocationEnabledCaches();
        SystemSettingsHelper systemSettingsHelper =
                ((SystemInjector) this.mInjector).mSettingsHelper;
        systemSettingsHelper.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(
                    systemSettingsHelper.mContext.getContentResolver(),
                    "location_mode",
                    z ? 3 : 0,
                    handleIncomingUser);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            NSLocationMonitor nSLocationMonitor = this.mNSLocationMonitor;
            nSLocationMonitor.getClass();
            Bundle bundle = new Bundle();
            bundle.putInt("pid", Binder.getCallingPid());
            bundle.putInt("uid", Binder.getCallingUid());
            bundle.putBoolean("enabled", z);
            nSLocationMonitor.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.SET_LOCATION_ENABLED, bundle);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setTestProviderEnabled(String str, boolean z, String str2, String str3) {
        if (((SystemInjector) this.mInjector)
                .mAppOpsHelper.noteOp(CallerIdentity.fromBinderUnsafe(str2, str3))) {
            LocationProviderManager locationProviderManager = getLocationProviderManager(str);
            if (locationProviderManager == null) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "provider doesn't exist: ", str));
            }
            synchronized (locationProviderManager.mMultiplexerLock) {
                if (!locationProviderManager.mProvider.isMock()) {
                    throw new IllegalArgumentException(
                            locationProviderManager.mName + " provider is not a test provider");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MockableLocationProvider mockableLocationProvider =
                            locationProviderManager.mProvider;
                    synchronized (mockableLocationProvider.mOwnerLock) {
                        Preconditions.checkState(mockableLocationProvider.isMock());
                        MockLocationProvider mockLocationProvider =
                                mockableLocationProvider.mMockProvider;
                        mockLocationProvider.getClass();
                        mockLocationProvider.setState(
                                new AbstractLocationProvider$$ExternalSyntheticLambda0(z));
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public final void setTestProviderLocation(
            String str, Location location, String str2, String str3) {
        if (((SystemInjector) this.mInjector)
                .mAppOpsHelper.noteOp(CallerIdentity.fromBinderUnsafe(str2, str3))) {
            Preconditions.checkArgument(
                    location.isComplete(),
                    "incomplete location object, missing timestamp or accuracy?");
            LocationProviderManager locationProviderManager = getLocationProviderManager(str);
            if (locationProviderManager == null) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "provider doesn't exist: ", str));
            }
            synchronized (locationProviderManager.mMultiplexerLock) {
                try {
                    if (!locationProviderManager.mProvider.isMock()) {
                        throw new IllegalArgumentException(
                                locationProviderManager.mName + " provider is not a test provider");
                    }
                    String provider = location.getProvider();
                    if (!TextUtils.isEmpty(provider)
                            && !locationProviderManager.mName.equals(provider)) {
                        EventLog.writeEvent(
                                1397638484,
                                "33091107",
                                Integer.valueOf(Binder.getCallingUid()),
                                locationProviderManager.mName + "!=" + provider);
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        locationProviderManager.mProvider.setMockProviderLocation(location);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void startGnssBatch(
            long j, ILocationListener iLocationListener, String str, String str2, String str3) {
        startGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        long millis = TimeUnit.NANOSECONDS.toMillis(j);
        synchronized (this.mDeprecatedGnssBatchingLock) {
            stopGnssBatch();
            registerLocationListener(
                    "gps",
                    new LocationRequest.Builder(millis)
                            .setMaxUpdateDelayMillis(
                                    millis
                                            * this.mGnssManagerService.mGnssLocationProvider
                                                    .getBatchSize())
                            .setHiddenFromAppOps(true)
                            .build(),
                    iLocationListener,
                    str,
                    str2,
                    str3);
            this.mDeprecatedGnssBatchingListener = iLocationListener;
        }
    }

    public final void stopGnssBatch() {
        stopGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            try {
                ILocationListener iLocationListener = this.mDeprecatedGnssBatchingListener;
                if (iLocationListener != null) {
                    this.mDeprecatedGnssBatchingListener = null;
                    unregisterLocationListener(iLocationListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterGnssNmeaCallback(IGnssNmeaListener iGnssNmeaListener) {
        boolean isEmpty;
        if (this.mGnssManagerService != null) {
            GnssNmeaProvider gnssNmeaProvider = this.mGnssManagerService.mGnssNmeaProvider;
            gnssNmeaProvider.removeListener(iGnssNmeaListener);
            Log.i(
                    "GnssNmeaProvider",
                    "removeListener: removing NMEA listener("
                            + Integer.toHexString(System.identityHashCode(iGnssNmeaListener))
                            + ")");
            synchronized (gnssNmeaProvider.mMultiplexerLock) {
                isEmpty = gnssNmeaProvider.mRegistrations.isEmpty();
            }
            if (isEmpty && gnssNmeaProvider.mNmeaAllowed == 1) {
                gnssNmeaProvider.mNmeaAllowed = 0;
                gnssNmeaProvider.onGnssNmeaListenerStatusChanged(0);
            }
        }
    }

    public final void unregisterGnssStatusCallback(IGnssStatusListener iGnssStatusListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.mGnssStatusProvider.removeListener(iGnssStatusListener);
        }
    }

    public final void unregisterLocationListener(ILocationListener iLocationListener) {
        ActivityManagerInternal activityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiEnd(3, Binder.getCallingUid(), Binder.getCallingPid());
        }
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            synchronized (locationProviderManager.mMultiplexerLock) {
                try {
                    Preconditions.checkState(locationProviderManager.mState != 2);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        locationProviderManager.removeRegistration(iLocationListener.asBinder());
                    } finally {
                    }
                } finally {
                }
            }
        }
    }

    public final void unregisterLocationPendingIntent(PendingIntent pendingIntent) {
        Iterator it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager = (LocationProviderManager) it.next();
            synchronized (locationProviderManager.mMultiplexerLock) {
                try {
                    Preconditions.checkState(locationProviderManager.mState != 2);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        locationProviderManager.removeRegistration(pendingIntent);
                    } finally {
                    }
                } finally {
                }
            }
        }
    }

    public final LocationRequest validateLocationRequest(
            String str, LocationRequest locationRequest, CallerIdentity callerIdentity) {
        if (!locationRequest.getWorkSource().isEmpty()) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.UPDATE_DEVICE_STATS",
                    "setting a work source requires android.permission.UPDATE_DEVICE_STATS");
        }
        LocationRequest.Builder builder = new LocationRequest.Builder(locationRequest);
        if (!CompatChanges.isChangeEnabled(168936375L, Binder.getCallingUid())
                && this.mContext.checkCallingPermission("android.permission.LOCATION_HARDWARE")
                        != 0) {
            builder.setLowPower(false);
        }
        WorkSource workSource = new WorkSource(locationRequest.getWorkSource());
        if (workSource.size() <= 0 || workSource.getPackageName(0) != null) {
            List workChains = workSource.getWorkChains();
            if (workChains != null
                    && !workChains.isEmpty()
                    && ((WorkSource.WorkChain) workChains.get(0)).getAttributionTag() == null) {
                Log.w(
                        "LocationManagerService",
                        "received (and ignoring) illegal worksource with no attribution tag");
                workSource.clear();
            }
        } else {
            Log.w(
                    "LocationManagerService",
                    "received (and ignoring) illegal worksource with no package name");
            workSource.clear();
        }
        if (workSource.isEmpty()) {
            callerIdentity.addToWorkSource(workSource);
        }
        builder.setWorkSource(workSource);
        LocationRequest build = builder.build();
        boolean isProvider = this.mLocalService.isProvider(null, callerIdentity);
        if (build.isLowPower()
                && CompatChanges.isChangeEnabled(168936375L, callerIdentity.getUid())) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.LOCATION_HARDWARE",
                    "low power request requires android.permission.LOCATION_HARDWARE");
        }
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.UPDATE_APP_OPS_STATS",
                    "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext
                    .getPackageManager()
                    .hasSystemFeature("android.hardware.type.automotive")) {
                throw new IllegalArgumentException(
                        "adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new IllegalArgumentException(
                        "adas gnss bypass requests are only allowed on the \"gps\" provider");
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
}
