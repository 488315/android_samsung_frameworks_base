package com.samsung.android.location;

import android.location.Location;

public interface SemLocationBatchingListener {
    void onLocationAvailable(Location[] locationArr, boolean z);
}
