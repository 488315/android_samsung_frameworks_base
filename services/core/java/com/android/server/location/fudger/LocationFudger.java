package com.android.server.location.fudger;

import android.location.Location;
import android.location.LocationResult;

import java.time.Clock;
import java.util.Random;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationFudger {
    static final long OFFSET_UPDATE_INTERVAL_MS = 3600000;
    public static final double OLD_WEIGHT = Math.sqrt(0.9991d);
    public final float mAccuracyM;
    public Location mCachedCoarseLocation;
    public LocationResult mCachedCoarseLocationResult;
    public Location mCachedFineLocation;
    public LocationResult mCachedFineLocationResult;
    public final Clock mClock;
    public double mLatitudeOffsetM = nextRandomOffset();
    public double mLongitudeOffsetM = nextRandomOffset();
    public long mNextUpdateRealtimeMs;
    public final Random mRandom;

    public LocationFudger(float f, Clock clock, Random random) {
        this.mClock = clock;
        this.mRandom = random;
        this.mAccuracyM = Math.max(f, 200.0f);
        this.mNextUpdateRealtimeMs = clock.millis() + 3600000;
    }

    public static double wrapLatitude(double d) {
        if (d > 89.999990990991d) {
            d = 89.999990990991d;
        }
        if (d < -89.999990990991d) {
            return -89.999990990991d;
        }
        return d;
    }

    public static double wrapLongitude(double d) {
        double d2 = d % 360.0d;
        if (d2 >= 180.0d) {
            d2 -= 360.0d;
        }
        return d2 < -180.0d ? d2 + 360.0d : d2;
    }

    public final Location createCoarse(Location location) {
        synchronized (this) {
            if (location != this.mCachedFineLocation && location != this.mCachedCoarseLocation) {
                synchronized (this) {
                    long millis = this.mClock.millis();
                    if (millis >= this.mNextUpdateRealtimeMs) {
                        double d = OLD_WEIGHT;
                        this.mLatitudeOffsetM =
                                (nextRandomOffset() * 0.03d) + (this.mLatitudeOffsetM * d);
                        this.mLongitudeOffsetM =
                                (nextRandomOffset() * 0.03d) + (d * this.mLongitudeOffsetM);
                        this.mNextUpdateRealtimeMs = millis + 3600000;
                    }
                }
                Location location2 = new Location(location);
                location2.removeBearing();
                location2.removeSpeed();
                location2.removeAltitude();
                location2.setExtras(null);
                double wrapLatitude = wrapLatitude(location2.getLatitude());
                double wrapLongitude =
                        wrapLongitude(
                                        (this.mLongitudeOffsetM / 111000.0d)
                                                / Math.cos(Math.toRadians(wrapLatitude)))
                                + wrapLongitude(location2.getLongitude());
                double wrapLatitude2 =
                        wrapLatitude(this.mLatitudeOffsetM / 111000.0d) + wrapLatitude;
                double wrapLatitude3 =
                        wrapLatitude(
                                Math.round(wrapLatitude2 / r1) * (this.mAccuracyM / 111000.0d));
                double wrapLongitude2 =
                        wrapLongitude(
                                Math.round(wrapLongitude / r3)
                                        * ((this.mAccuracyM / 111000.0d)
                                                / Math.cos(Math.toRadians(wrapLatitude3))));
                location2.setLatitude(wrapLatitude3);
                location2.setLongitude(wrapLongitude2);
                location2.setAccuracy(Math.max(this.mAccuracyM, location2.getAccuracy()));
                synchronized (this) {
                    this.mCachedFineLocation = location;
                    this.mCachedCoarseLocation = location2;
                }
                return location2;
            }
            return this.mCachedCoarseLocation;
        }
    }

    public final double nextRandomOffset() {
        return (this.mAccuracyM / 4.0d) * this.mRandom.nextGaussian();
    }
}
