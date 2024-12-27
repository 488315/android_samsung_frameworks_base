package com.android.server.location.geofence;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Geofence;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.WorkSource;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.SettingsHelper$UserSettingChangedListener;
import com.android.server.location.injector.SystemLocationPermissionsHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.injector.UserInfoHelper$UserListener;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.RemovableListenerRegistration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public final class GeofenceManager extends ListenerMultiplexer implements LocationListener {
    public final Context mContext;
    public Location mLastLocation;
    public final GeofenceManager$$ExternalSyntheticLambda2 mLocationEnabledChangedListener;
    public LocationManager mLocationManager;
    public final GeofenceManager$$ExternalSyntheticLambda2 mLocationPackageBlacklistChangedListener;
    public final SystemLocationPermissionsHelper mLocationPermissionsHelper;
    public final LocationUsageLogger mLocationUsageLogger;
    public final SystemSettingsHelper mSettingsHelper;
    public final LocationManagerService.Lifecycle.LifecycleUserInfoHelper mUserInfoHelper;
    public final Object mLock = new Object();
    public final GeofenceManager$$ExternalSyntheticLambda1 mUserChangedListener =
            new UserInfoHelper$UserListener() { // from class:
                                                // com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda1
                @Override // com.android.server.location.injector.UserInfoHelper$UserListener
                public final void onUserChanged(int i, int i2) {
                    GeofenceManager geofenceManager = GeofenceManager.this;
                    geofenceManager.getClass();
                    if (i2 == 1 || i2 == 4) {
                        geofenceManager.updateRegistrations(
                                new GeofenceManager$$ExternalSyntheticLambda5(i, 3));
                    }
                }
            };
    public final AnonymousClass1 mLocationPermissionsListener =
            new LocationPermissionsHelper$LocationPermissionsListener() { // from class:
                                                                          // com.android.server.location.geofence.GeofenceManager.1
                @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                public final void onLocationPermissionsChanged(int i) {
                    GeofenceManager geofenceManager = GeofenceManager.this;
                    geofenceManager.getClass();
                    geofenceManager.updateRegistrations(
                            new GeofenceManager$$ExternalSyntheticLambda5(i, 1));
                }

                @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                public final void onLocationPermissionsChanged(String str) {
                    GeofenceManager geofenceManager = GeofenceManager.this;
                    geofenceManager.getClass();
                    geofenceManager.updateRegistrations(
                            new GeofenceManager$$ExternalSyntheticLambda4(1, str));
                }
            };

    public final class GeofenceKey {
        public final Geofence mGeofence;
        public final PendingIntent mPendingIntent;

        public GeofenceKey(PendingIntent pendingIntent, Geofence geofence) {
            Objects.requireNonNull(pendingIntent);
            this.mPendingIntent = pendingIntent;
            Objects.requireNonNull(geofence);
            this.mGeofence = geofence;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof GeofenceKey)) {
                return false;
            }
            GeofenceKey geofenceKey = (GeofenceKey) obj;
            return this.mPendingIntent.equals(geofenceKey.mPendingIntent)
                    && this.mGeofence.equals(geofenceKey.mGeofence);
        }

        public final int hashCode() {
            return this.mPendingIntent.hashCode();
        }
    }

    public final class GeofenceRegistration extends RemovableListenerRegistration
            implements PendingIntent.CancelListener {
        public Location mCachedLocation;
        public float mCachedLocationDistanceM;
        public final Location mCenter;
        public final Geofence mGeofence;
        public int mGeofenceState;
        public final CallerIdentity mIdentity;
        public boolean mPermitted;
        public final PowerManager.WakeLock mWakeLock;

        public GeofenceRegistration(
                Geofence geofence, CallerIdentity callerIdentity, PendingIntent pendingIntent) {
            super(ConcurrentUtils.DIRECT_EXECUTOR, pendingIntent);
            this.mGeofence = geofence;
            this.mIdentity = callerIdentity;
            Location location = new Location("");
            this.mCenter = location;
            location.setLatitude(geofence.getLatitude());
            location.setLongitude(geofence.getLongitude());
            PowerManager powerManager =
                    (PowerManager)
                            GeofenceManager.this.mContext.getSystemService(PowerManager.class);
            Objects.requireNonNull(powerManager);
            PowerManager.WakeLock newWakeLock =
                    powerManager.newWakeLock(
                            1, "GeofenceManager:" + callerIdentity.getPackageName());
            this.mWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(true);
            newWakeLock.setWorkSource(callerIdentity.addToWorkSource((WorkSource) null));
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final ListenerMultiplexer getOwner() {
            return GeofenceManager.this;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final String getTag() {
            throw null;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onActive() {
            Location lastLocation = GeofenceManager.this.getLastLocation();
            if (lastLocation != null) {
                executeOperation(onLocationChanged(lastLocation));
            }
        }

        public final void onCanceled(PendingIntent pendingIntent) {
            if (Log.isLoggable("GeofenceManager", 3)) {
                Log.d("GeofenceManager", "pending intent registration " + this + " canceled");
            }
            remove();
        }

        public final ListenerExecutor.ListenerOperation onLocationChanged(Location location) {
            if (this.mGeofence.isExpired()) {
                remove();
                return null;
            }
            this.mCachedLocation = location;
            this.mCachedLocationDistanceM = this.mCenter.distanceTo(location);
            int i = this.mGeofenceState;
            if (this.mCachedLocationDistanceM
                    <= Math.max(this.mGeofence.getRadius(), location.getAccuracy())) {
                this.mGeofenceState = 1;
                if (i != 1) {
                    final int i2 = 0;
                    return new ListenerExecutor.ListenerOperation(
                            this) { // from class:
                                    // com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda0
                        public final /* synthetic */ GeofenceManager.GeofenceRegistration f$0;

                        {
                            this.f$0 = this;
                        }

                        public final void operate(Object obj) {
                            int i3 = i2;
                            GeofenceManager.GeofenceRegistration geofenceRegistration = this.f$0;
                            PendingIntent pendingIntent = (PendingIntent) obj;
                            switch (i3) {
                                case 0:
                                    geofenceRegistration.sendIntent(pendingIntent, true);
                                    break;
                                default:
                                    geofenceRegistration.sendIntent(pendingIntent, false);
                                    break;
                            }
                        }
                    };
                }
            } else {
                this.mGeofenceState = 2;
                if (i == 1) {
                    final int i3 = 1;
                    return new ListenerExecutor.ListenerOperation(
                            this) { // from class:
                                    // com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda0
                        public final /* synthetic */ GeofenceManager.GeofenceRegistration f$0;

                        {
                            this.f$0 = this;
                        }

                        public final void operate(Object obj) {
                            int i32 = i3;
                            GeofenceManager.GeofenceRegistration geofenceRegistration = this.f$0;
                            PendingIntent pendingIntent = (PendingIntent) obj;
                            switch (i32) {
                                case 0:
                                    geofenceRegistration.sendIntent(pendingIntent, true);
                                    break;
                                default:
                                    geofenceRegistration.sendIntent(pendingIntent, false);
                                    break;
                            }
                        }
                    };
                }
            }
            return null;
        }

        public final boolean onLocationPermissionsChanged$1$1() {
            boolean hasLocationPermissions =
                    GeofenceManager.this.mLocationPermissionsHelper.hasLocationPermissions(
                            2, this.mIdentity);
            if (hasLocationPermissions == this.mPermitted) {
                return false;
            }
            this.mPermitted = hasLocationPermissions;
            return true;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onOperationFailure(
                ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
            if (!(exc instanceof PendingIntent.CanceledException)) {
                throw new AssertionError(exc);
            }
            Log.w("GeofenceManager", "registration " + this + " removed", exc);
            remove();
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            if (!((GeofenceKey) obj)
                    .mPendingIntent.addCancelListener(ConcurrentUtils.DIRECT_EXECUTOR, this)) {
                remove();
            }
            this.mGeofenceState = 0;
            this.mPermitted =
                    GeofenceManager.this.mLocationPermissionsHelper.hasLocationPermissions(
                            2, this.mIdentity);
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onUnregister() {
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            ((GeofenceKey) obj).mPendingIntent.removeCancelListener(this);
            this.mKey = null;
        }

        public final void sendIntent(PendingIntent pendingIntent, boolean z) {
            Intent putExtra = new Intent().putExtra("entering", z);
            this.mWakeLock.acquire(30000L);
            try {
                Context context = GeofenceManager.this.mContext;
                PendingIntent.OnFinished onFinished =
                        new PendingIntent
                                .OnFinished() { // from class:
                                                // com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda2
                            @Override // android.app.PendingIntent.OnFinished
                            public final void onSendFinished(
                                    PendingIntent pendingIntent2,
                                    Intent intent,
                                    int i,
                                    String str,
                                    Bundle bundle) {
                                GeofenceManager.GeofenceRegistration.this.mWakeLock.release();
                            }
                        };
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setDontSendToRestrictedApps(true);
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                pendingIntent.send(
                        context, 0, putExtra, onFinished, null, null, makeBasic.toBundle());
            } catch (PendingIntent.CanceledException unused) {
                this.mWakeLock.release();
                GeofenceManager.this.removeRegistration(
                        new GeofenceKey(pendingIntent, this.mGeofence), this);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIdentity);
            ArraySet arraySet = new ArraySet(1);
            if (!this.mPermitted) {
                arraySet.add("na");
            }
            if (!arraySet.isEmpty()) {
                sb.append(" ");
                sb.append(arraySet);
            }
            sb.append(" ");
            sb.append(this.mGeofence);
            return sb.toString();
        }
    }

    public GeofenceManager(Context context, LocationManagerService.SystemInjector systemInjector) {
        final int i = 0;
        this.mLocationEnabledChangedListener =
                new SettingsHelper$UserSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda2
                    public final /* synthetic */ GeofenceManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                    public final void onSettingChanged(int i2) {
                        int i3 = i;
                        GeofenceManager geofenceManager = this.f$0;
                        geofenceManager.getClass();
                        switch (i3) {
                            case 0:
                                geofenceManager.updateRegistrations(
                                        new GeofenceManager$$ExternalSyntheticLambda5(i2, 0));
                                break;
                            default:
                                geofenceManager.updateRegistrations(
                                        new GeofenceManager$$ExternalSyntheticLambda5(i2, 2));
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mLocationPackageBlacklistChangedListener =
                new SettingsHelper$UserSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda2
                    public final /* synthetic */ GeofenceManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                    public final void onSettingChanged(int i22) {
                        int i3 = i2;
                        GeofenceManager geofenceManager = this.f$0;
                        geofenceManager.getClass();
                        switch (i3) {
                            case 0:
                                geofenceManager.updateRegistrations(
                                        new GeofenceManager$$ExternalSyntheticLambda5(i22, 0));
                                break;
                            default:
                                geofenceManager.updateRegistrations(
                                        new GeofenceManager$$ExternalSyntheticLambda5(i22, 2));
                                break;
                        }
                    }
                };
        this.mContext = context.createAttributionContext("GeofencingService");
        this.mUserInfoHelper = systemInjector.mUserInfoHelper;
        this.mSettingsHelper = systemInjector.mSettingsHelper;
        this.mLocationPermissionsHelper = systemInjector.mLocationPermissionsHelper;
        this.mLocationUsageLogger = systemInjector.mLocationUsageLogger;
    }

    public final Location getLastLocation() {
        Location location;
        synchronized (this.mLock) {
            location = this.mLastLocation;
        }
        if (location == null) {
            location = getLocationManager().getLastLocation();
        }
        if (location == null || location.getElapsedRealtimeAgeMillis() <= 300000) {
            return location;
        }
        return null;
    }

    public final LocationManager getLocationManager() {
        LocationManager locationManager;
        synchronized (this.mLock) {
            try {
                if (this.mLocationManager == null) {
                    LocationManager locationManager2 =
                            (LocationManager) this.mContext.getSystemService(LocationManager.class);
                    Objects.requireNonNull(locationManager2);
                    LocationManager locationManager3 = locationManager2;
                    this.mLocationManager = locationManager2;
                }
                locationManager = this.mLocationManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return locationManager;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean isActive(RemovableListenerRegistration removableListenerRegistration) {
        GeofenceRegistration geofenceRegistration =
                (GeofenceRegistration) removableListenerRegistration;
        if (geofenceRegistration.mPermitted) {
            CallerIdentity callerIdentity = geofenceRegistration.mIdentity;
            if (!callerIdentity.isSystemServer()
                    ? this.mSettingsHelper.isLocationEnabled(callerIdentity.getUserId())
                            && this.mUserInfoHelper.isVisibleUserId(callerIdentity.getUserId())
                            && !this.mSettingsHelper.isLocationPackageBlacklisted(
                                    callerIdentity.getUserId(), callerIdentity.getPackageName())
                    : this.mSettingsHelper.isLocationEnabled(
                            this.mUserInfoHelper.getCurrentUserId())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final Object mergeRegistrations(Collection collection) {
        Location lastLocation = getLastLocation();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator it = ((ArrayList) collection).iterator();
        WorkSource workSource = null;
        double d = Double.MAX_VALUE;
        while (it.hasNext()) {
            GeofenceRegistration geofenceRegistration = (GeofenceRegistration) it.next();
            if (!geofenceRegistration.mGeofence.isExpired(elapsedRealtime)) {
                workSource = geofenceRegistration.mIdentity.addToWorkSource(workSource);
                if (lastLocation != null) {
                    if (!lastLocation.equals(geofenceRegistration.mCachedLocation)) {
                        geofenceRegistration.mCachedLocation = lastLocation;
                        geofenceRegistration.mCachedLocationDistanceM =
                                geofenceRegistration.mCenter.distanceTo(lastLocation);
                    }
                    double abs =
                            Math.abs(
                                    geofenceRegistration.mGeofence.getRadius()
                                            - geofenceRegistration.mCachedLocationDistanceM);
                    if (abs < d) {
                        d = abs;
                    }
                }
            }
        }
        return new LocationRequest.Builder(
                        Double.compare(d, Double.MAX_VALUE) < 0
                                ? (long)
                                        Math.min(
                                                7200000.0d,
                                                Math.max(
                                                        this.mSettingsHelper
                                                                .getBackgroundThrottleProximityAlertIntervalMs(),
                                                        (d * 1000.0d) / 100.0d))
                                : this.mSettingsHelper
                                        .getBackgroundThrottleProximityAlertIntervalMs())
                .setMinUpdateIntervalMillis(0L)
                .setHiddenFromAppOps(true)
                .setWorkSource(workSource)
                .build();
    }

    @Override // android.location.LocationListener
    public final void onLocationChanged(final Location location) {
        synchronized (this.mLock) {
            this.mLastLocation = location;
        }
        deliverToListeners(
                new Function() { // from class:
                                 // com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((GeofenceManager.GeofenceRegistration) obj)
                                .onLocationChanged(location);
                    }
                });
        updateService();
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegister() {
        LocationManagerService.Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper =
                this.mUserInfoHelper;
        lifecycleUserInfoHelper.mListeners.add(this.mUserChangedListener);
        SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
        systemSettingsHelper.mLocationMode.addListener(this.mLocationEnabledChangedListener);
        SystemSettingsHelper systemSettingsHelper2 = this.mSettingsHelper;
        GeofenceManager$$ExternalSyntheticLambda2 geofenceManager$$ExternalSyntheticLambda2 =
                this.mLocationPackageBlacklistChangedListener;
        systemSettingsHelper2.mLocationPackageBlacklist.addListener(
                geofenceManager$$ExternalSyntheticLambda2);
        systemSettingsHelper2.mLocationPackageWhitelist.addListener(
                geofenceManager$$ExternalSyntheticLambda2);
        SystemLocationPermissionsHelper systemLocationPermissionsHelper =
                this.mLocationPermissionsHelper;
        systemLocationPermissionsHelper.mListeners.add(this.mLocationPermissionsListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        GeofenceRegistration geofenceRegistration =
                (GeofenceRegistration) removableListenerRegistration;
        this.mLocationUsageLogger.logLocationApiUsage(
                1,
                4,
                geofenceRegistration.mIdentity.getPackageName(),
                geofenceRegistration.mIdentity.getAttributionTag(),
                null,
                null,
                false,
                true,
                geofenceRegistration.mGeofence,
                true);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationRemoved(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        GeofenceRegistration geofenceRegistration =
                (GeofenceRegistration) removableListenerRegistration;
        this.mLocationUsageLogger.logLocationApiUsage(
                1,
                4,
                geofenceRegistration.mIdentity.getPackageName(),
                geofenceRegistration.mIdentity.getAttributionTag(),
                null,
                null,
                false,
                true,
                geofenceRegistration.mGeofence,
                true);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onUnregister() {
        LocationManagerService.Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper =
                this.mUserInfoHelper;
        lifecycleUserInfoHelper.mListeners.remove(this.mUserChangedListener);
        SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
        systemSettingsHelper.mLocationMode.removeListener(this.mLocationEnabledChangedListener);
        SystemSettingsHelper systemSettingsHelper2 = this.mSettingsHelper;
        GeofenceManager$$ExternalSyntheticLambda2 geofenceManager$$ExternalSyntheticLambda2 =
                this.mLocationPackageBlacklistChangedListener;
        systemSettingsHelper2.mLocationPackageBlacklist.removeListener(
                geofenceManager$$ExternalSyntheticLambda2);
        systemSettingsHelper2.mLocationPackageWhitelist.removeListener(
                geofenceManager$$ExternalSyntheticLambda2);
        SystemLocationPermissionsHelper systemLocationPermissionsHelper =
                this.mLocationPermissionsHelper;
        systemLocationPermissionsHelper.mListeners.remove(this.mLocationPermissionsListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean registerWithService(Collection collection, Object obj) {
        getLocationManager()
                .requestLocationUpdates(
                        "fused", (LocationRequest) obj, LocationServiceThread.getExecutor(), this);
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        synchronized (this.mLock) {
            getLocationManager().removeUpdates(this);
            this.mLastLocation = null;
        }
    }
}
