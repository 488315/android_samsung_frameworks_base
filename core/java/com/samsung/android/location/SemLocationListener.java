package com.samsung.android.location;

import android.location.Address;
import android.location.Location;

/* loaded from: classes5.dex */
public interface SemLocationListener {
    @Deprecated(forRemoval = true, since = "13.0")
    void onLocationAvailable(Location[] locationArr);

    void onLocationChanged(Location location, Address address);
}
