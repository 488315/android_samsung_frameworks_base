package androidx.appcompat.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import java.util.Calendar;

/* loaded from: classes.dex */
final class TwilightManager {
    private static TwilightManager sInstance;
    private final Context mContext;
    private final LocationManager mLocationManager;
    private final TwilightState mTwilightState = new TwilightState();

    private static class TwilightState {
        boolean isNight;
        long nextUpdate;
    }

    TwilightManager(Context context, LocationManager locationManager) {
        this.mContext = context;
        this.mLocationManager = locationManager;
    }

    static TwilightManager getInstance(Context context) {
        if (sInstance == null) {
            Context applicationContext = context.getApplicationContext();
            sInstance = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return sInstance;
    }

    final boolean isNight() {
        Location location;
        long j;
        Location location2;
        TwilightState twilightState = this.mTwilightState;
        if (twilightState.nextUpdate > System.currentTimeMillis()) {
            return twilightState.isNight;
        }
        Context context = this.mContext;
        int checkSelfPermission = PermissionChecker.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
        LocationManager locationManager = this.mLocationManager;
        Location location3 = null;
        if (checkSelfPermission == 0) {
            try {
            } catch (Exception e) {
                Log.d("TwilightManager", "Failed to get last known location", e);
            }
            if (locationManager.isProviderEnabled("network")) {
                location2 = locationManager.getLastKnownLocation("network");
                location = location2;
            }
            location2 = null;
            location = location2;
        } else {
            location = null;
        }
        if (PermissionChecker.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            try {
                if (locationManager.isProviderEnabled("gps")) {
                    location3 = locationManager.getLastKnownLocation("gps");
                }
            } catch (Exception e2) {
                Log.d("TwilightManager", "Failed to get last known location", e2);
            }
        }
        if (location3 == null || location == null ? location3 != null : location3.getTime() > location.getTime()) {
            location = location3;
        }
        if (location == null) {
            Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
            int i = Calendar.getInstance().get(11);
            return i < 6 || i >= 22;
        }
        long currentTimeMillis = System.currentTimeMillis();
        TwilightCalculator twilightCalculator = TwilightCalculator.getInstance();
        twilightCalculator.calculateTwilight(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        twilightCalculator.calculateTwilight(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = twilightCalculator.state == 1;
        long j2 = twilightCalculator.sunrise;
        long j3 = twilightCalculator.sunset;
        twilightCalculator.calculateTwilight(currentTimeMillis + 86400000, location.getLatitude(), location.getLongitude());
        long j4 = twilightCalculator.sunrise;
        if (j2 == -1 || j3 == -1) {
            j = 43200000 + currentTimeMillis;
        } else {
            j = (currentTimeMillis > j3 ? j4 + 0 : currentTimeMillis > j2 ? j3 + 0 : j2 + 0) + 60000;
        }
        twilightState.isNight = z;
        twilightState.nextUpdate = j;
        return z;
    }
}
