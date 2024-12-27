package com.android.server.location.countrydetector;

import android.location.Country;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationBasedCountryDetector extends CountryDetectorBase {
    public List mEnabledProviders;
    public List mLocationListeners;
    public LocationManager mLocationManager;
    public Thread mQueryThread;
    public Timer mTimer;

    @Override // com.android.server.location.countrydetector.CountryDetectorBase
    public final synchronized Country detectCountry() {
        try {
            if (this.mLocationListeners != null) {
                throw new IllegalStateException();
            }
            if (this.mEnabledProviders == null) {
                this.mEnabledProviders = this.mLocationManager.getProviders(true);
            }
            List list = this.mEnabledProviders;
            int size = list.size();
            if (size > 0) {
                this.mLocationListeners = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    String str = (String) list.get(i);
                    if ("passive".equals(str)) {
                        LocationListener locationListener =
                                new LocationListener() { // from class:
                                                         // com.android.server.location.countrydetector.LocationBasedCountryDetector.1
                                    @Override // android.location.LocationListener
                                    public final void onLocationChanged(Location location) {
                                        if (location != null) {
                                            LocationBasedCountryDetector.this.stop();
                                            LocationBasedCountryDetector.this.queryCountryCode(
                                                    location);
                                        }
                                    }

                                    @Override // android.location.LocationListener
                                    public final void onProviderDisabled(String str2) {}

                                    @Override // android.location.LocationListener
                                    public final void onProviderEnabled(String str2) {}

                                    @Override // android.location.LocationListener
                                    public final void onStatusChanged(
                                            String str2, int i2, Bundle bundle) {}
                                };
                        ((ArrayList) this.mLocationListeners).add(locationListener);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            this.mLocationManager.requestLocationUpdates(
                                    str,
                                    0L,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    locationListener);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                }
                Timer timer = new Timer();
                this.mTimer = timer;
                timer.schedule(
                        new TimerTask() { // from class:
                                          // com.android.server.location.countrydetector.LocationBasedCountryDetector.2
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                LocationBasedCountryDetector locationBasedCountryDetector =
                                        LocationBasedCountryDetector.this;
                                locationBasedCountryDetector.mTimer = null;
                                locationBasedCountryDetector.stop();
                                LocationBasedCountryDetector locationBasedCountryDetector2 =
                                        LocationBasedCountryDetector.this;
                                locationBasedCountryDetector2.queryCountryCode(
                                        locationBasedCountryDetector2.getLastKnownLocation());
                            }
                        },
                        300000L);
            } else {
                queryCountryCode(getLastKnownLocation());
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return this.mDetectedCountry;
    }

    public final Location getLastKnownLocation() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator<String> it = this.mLocationManager.getAllProviders().iterator();
            Location location = null;
            while (it.hasNext()) {
                Location lastKnownLocation = this.mLocationManager.getLastKnownLocation(it.next());
                if (lastKnownLocation != null
                        && (location == null
                                || location.getElapsedRealtimeNanos()
                                        < lastKnownLocation.getElapsedRealtimeNanos())) {
                    location = lastKnownLocation;
                }
            }
            return location;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void queryCountryCode(final Location location) {
        if (this.mQueryThread != null) {
            return;
        }
        Thread thread =
                new Thread(
                        new Runnable() { // from class:
                            // com.android.server.location.countrydetector.LocationBasedCountryDetector.3
                            /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
                            /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
                            /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    r9 = this;
                                    android.location.Location r0 = r2
                                    r1 = 0
                                    if (r0 != 0) goto Lf
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r9 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    android.location.CountryListener r9 = r9.mListener
                                    if (r9 == 0) goto Le
                                    r9.onCountryDetected(r1)
                                Le:
                                    return
                                Lf:
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r2 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    r2.getClass()
                                    android.location.Geocoder r3 = new android.location.Geocoder
                                    android.content.Context r2 = r2.mContext
                                    r3.<init>(r2)
                                    double r4 = r0.getLatitude()     // Catch: java.io.IOException -> L3c
                                    double r6 = r0.getLongitude()     // Catch: java.io.IOException -> L3c
                                    r8 = 1
                                    java.util.List r0 = r3.getFromLocation(r4, r6, r8)     // Catch: java.io.IOException -> L3c
                                    if (r0 == 0) goto L43
                                    int r2 = r0.size()     // Catch: java.io.IOException -> L3c
                                    if (r2 <= 0) goto L43
                                    r2 = 0
                                    java.lang.Object r0 = r0.get(r2)     // Catch: java.io.IOException -> L3c
                                    android.location.Address r0 = (android.location.Address) r0     // Catch: java.io.IOException -> L3c
                                    java.lang.String r0 = r0.getCountryCode()     // Catch: java.io.IOException -> L3c
                                    goto L44
                                L3c:
                                    java.lang.String r0 = "LocationBasedCountryDetector"
                                    java.lang.String r2 = "Exception occurs when getting country from location"
                                    android.util.Slog.w(r0, r2)
                                L43:
                                    r0 = r1
                                L44:
                                    if (r0 == 0) goto L51
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r2 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    android.location.Country r3 = new android.location.Country
                                    r4 = 1
                                    r3.<init>(r0, r4)
                                    r2.mDetectedCountry = r3
                                    goto L55
                                L51:
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r0 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    r0.mDetectedCountry = r1
                                L55:
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r0 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    android.location.Country r2 = r0.mDetectedCountry
                                    android.location.CountryListener r0 = r0.mListener
                                    if (r0 == 0) goto L60
                                    r0.onCountryDetected(r2)
                                L60:
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector r9 = com.android.server.location.countrydetector.LocationBasedCountryDetector.this
                                    r9.mQueryThread = r1
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.location.countrydetector.LocationBasedCountryDetector.AnonymousClass3.run():void");
                            }
                        });
        this.mQueryThread = thread;
        thread.start();
    }

    public final synchronized void stop() {
        try {
            List list = this.mLocationListeners;
            if (list != null) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    LocationListener locationListener = (LocationListener) it.next();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mLocationManager.removeUpdates(locationListener);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                this.mLocationListeners = null;
            }
            Timer timer = this.mTimer;
            if (timer != null) {
                timer.cancel();
                this.mTimer = null;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }
}
