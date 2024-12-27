package com.android.server.twilight;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.SystemService;

import com.ibm.icu.impl.CalendarAstronomer;

import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TwilightService extends SystemService
        implements AlarmManager.OnAlarmListener, Handler.Callback, LocationListener {
    public AlarmManager mAlarmManager;
    public boolean mBootCompleted;
    public final Handler mHandler;
    public boolean mHasListeners;
    public Location mLastLocation;
    public TwilightState mLastTwilightState;
    public final ArrayMap mListeners;
    public LocationManager mLocationManager;
    public AnonymousClass2 mTimeChangedReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.twilight.TwilightService$1, reason: invalid class name */
    public final class AnonymousClass1 implements TwilightManager {
        public AnonymousClass1() {}

        public final TwilightState getLastTwilightState() {
            TwilightState twilightState;
            synchronized (TwilightService.this.mListeners) {
                twilightState = TwilightService.this.mLastTwilightState;
            }
            return twilightState;
        }

        public final void registerListener(TwilightListener twilightListener, Handler handler) {
            synchronized (TwilightService.this.mListeners) {
                try {
                    boolean isEmpty = TwilightService.this.mListeners.isEmpty();
                    TwilightService.this.mListeners.put(twilightListener, handler);
                    if (isEmpty && !TwilightService.this.mListeners.isEmpty()) {
                        TwilightService.this.mHandler.sendEmptyMessage(1);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterListener(TwilightListener twilightListener) {
            synchronized (TwilightService.this.mListeners) {
                try {
                    boolean isEmpty = TwilightService.this.mListeners.isEmpty();
                    TwilightService.this.mListeners.remove(twilightListener);
                    if (!isEmpty && TwilightService.this.mListeners.isEmpty()) {
                        TwilightService.this.mHandler.sendEmptyMessage(2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public TwilightService(Context context) {
        super(context.createAttributionContext("TwilightService"));
        this.mListeners = new ArrayMap();
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            if (!this.mHasListeners) {
                this.mHasListeners = true;
                if (this.mBootCompleted) {
                    startListening();
                }
            }
            return true;
        }
        if (i != 2) {
            return false;
        }
        if (this.mHasListeners) {
            this.mHasListeners = false;
            if (this.mBootCompleted) {
                Slog.d("TwilightService", "stopListening");
                if (this.mTimeChangedReceiver != null) {
                    getContext().unregisterReceiver(this.mTimeChangedReceiver);
                    this.mTimeChangedReceiver = null;
                }
                if (this.mLastTwilightState != null) {
                    this.mAlarmManager.cancel(this);
                }
                this.mLocationManager.removeUpdates(this);
                this.mLastLocation = null;
            }
        }
        return true;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public final void onAlarm() {
        Slog.d("TwilightService", "onAlarm");
        updateTwilightState();
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Context context = getContext();
            this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
            this.mLocationManager = (LocationManager) context.getSystemService("location");
            this.mBootCompleted = true;
            if (this.mHasListeners) {
                startListening();
            }
        }
    }

    @Override // android.location.LocationListener
    public final void onLocationChanged(Location location) {
        if (location != null) {
            Slog.d(
                    "TwilightService",
                    "onLocationChanged: provider="
                            + location.getProvider()
                            + " accuracy="
                            + location.getAccuracy()
                            + " time="
                            + location.getTime());
            this.mLastLocation = location;
            updateTwilightState();
        }
    }

    @Override // android.location.LocationListener
    public final void onProviderDisabled(String str) {}

    @Override // android.location.LocationListener
    public final void onProviderEnabled(String str) {}

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishLocalService(TwilightManager.class, new AnonymousClass1());
    }

    @Override // android.location.LocationListener
    public final void onStatusChanged(String str, int i, Bundle bundle) {}

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.twilight.TwilightService$2] */
    public final void startListening() {
        Slog.d("TwilightService", "startListening");
        this.mLocationManager.requestLocationUpdates(
                (LocationRequest) null, this, Looper.getMainLooper());
        if (this.mLocationManager.getLastLocation() == null) {
            if (this.mLocationManager.isProviderEnabled("network")) {
                this.mLocationManager.getCurrentLocation(
                        "network",
                        null,
                        getContext().getMainExecutor(),
                        new Consumer() { // from class:
                                         // com.android.server.twilight.TwilightService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                TwilightService.this.onLocationChanged((Location) obj);
                            }
                        });
            } else if (this.mLocationManager.isProviderEnabled("gps")) {
                this.mLocationManager.getCurrentLocation(
                        "gps",
                        null,
                        getContext().getMainExecutor(),
                        new Consumer() { // from class:
                                         // com.android.server.twilight.TwilightService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                TwilightService.this.onLocationChanged((Location) obj);
                            }
                        });
            }
        }
        if (this.mTimeChangedReceiver == null) {
            this.mTimeChangedReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.twilight.TwilightService.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Slog.d("TwilightService", "onReceive: " + intent);
                            TwilightService.this.updateTwilightState();
                        }
                    };
            IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            getContext().registerReceiver(this.mTimeChangedReceiver, intentFilter);
        }
        updateTwilightState();
    }

    public final void updateTwilightState() {
        final TwilightState twilightState;
        long currentTimeMillis = System.currentTimeMillis();
        Location location = this.mLastLocation;
        if (location == null) {
            location = this.mLocationManager.getLastLocation();
        }
        if (location == null) {
            twilightState = null;
        } else {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            long currentTimeMillis2 = System.currentTimeMillis();
            CalendarAstronomer calendarAstronomer = new CalendarAstronomer();
            calendarAstronomer.fLatitude = 0.0d;
            calendarAstronomer.fGmtOffset = 0L;
            calendarAstronomer.julianDay = Double.MIN_VALUE;
            calendarAstronomer.sunLongitude = Double.MIN_VALUE;
            calendarAstronomer.eclipObliquity = Double.MIN_VALUE;
            calendarAstronomer.siderealT0 = Double.MIN_VALUE;
            calendarAstronomer.time = currentTimeMillis2;
            double normalize =
                    CalendarAstronomer.normalize(
                                    (longitude * 0.017453292519943295d) + 3.141592653589793d,
                                    6.283185307179586d)
                            - 3.141592653589793d;
            calendarAstronomer.fLatitude =
                    CalendarAstronomer.normalize(
                                    (latitude * 0.017453292519943295d) + 3.141592653589793d,
                                    6.283185307179586d)
                            - 3.141592653589793d;
            calendarAstronomer.fGmtOffset =
                    (long) (((normalize * 24.0d) * 3600000.0d) / 6.283185307179586d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTimeMillis);
            calendar.set(11, 12);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendarAstronomer.setTime(calendar.getTimeInMillis());
            long sunRiseSet = calendarAstronomer.getSunRiseSet(true);
            long sunRiseSet2 = calendarAstronomer.getSunRiseSet(false);
            if (sunRiseSet2 < currentTimeMillis) {
                calendar.add(5, 1);
                calendarAstronomer.setTime(calendar.getTimeInMillis());
                sunRiseSet = calendarAstronomer.getSunRiseSet(true);
            } else if (sunRiseSet > currentTimeMillis) {
                calendar.add(5, -1);
                calendarAstronomer.setTime(calendar.getTimeInMillis());
                sunRiseSet2 = calendarAstronomer.getSunRiseSet(false);
            }
            twilightState = new TwilightState(sunRiseSet, sunRiseSet2);
        }
        synchronized (this.mListeners) {
            try {
                if (!Objects.equals(this.mLastTwilightState, twilightState)) {
                    this.mLastTwilightState = twilightState;
                    for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                        final TwilightListener twilightListener =
                                (TwilightListener) this.mListeners.keyAt(size);
                        ((Handler) this.mListeners.valueAt(size))
                                .post(
                                        new Runnable() { // from class:
                                                         // com.android.server.twilight.TwilightService$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TwilightListener.this.onTwilightStateChanged(
                                                        twilightState);
                                            }
                                        });
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (twilightState != null) {
            this.mAlarmManager.setExact(
                    1,
                    twilightState.isNight()
                            ? twilightState.mSunriseTimeMillis
                            : twilightState.mSunsetTimeMillis,
                    "TwilightService",
                    this,
                    this.mHandler);
        }
    }
}
