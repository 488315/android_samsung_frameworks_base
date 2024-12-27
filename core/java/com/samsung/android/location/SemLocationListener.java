package com.samsung.android.location;

import android.location.Location;

public interface SemLocationListener {
    default void onLocationChanged(Location location) {}
}
