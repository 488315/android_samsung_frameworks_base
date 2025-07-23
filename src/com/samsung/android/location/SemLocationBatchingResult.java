package com.samsung.android.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemLocationBatchingResult {
    private boolean isFlushed;
    private List<Location> locations;

    private SemLocationBatchingResult() {
    }

    public static SemLocationBatchingResult extractResult(Intent intent) {
        if (intent == null) {
            return null;
        }
        SemLocationBatchingResult semLocationBatchingResult = new SemLocationBatchingResult();
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra(SemLocationManager.BATCHED_LOCATION);
        semLocationBatchingResult.locations = new ArrayList();
        for (Parcelable parcelable : parcelableArrayExtra) {
            semLocationBatchingResult.locations.add((Location) parcelable);
        }
        semLocationBatchingResult.isFlushed = intent.getBooleanExtra(SemLocationManager.FLUSH_COMPLETED, false);
        return semLocationBatchingResult;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public boolean isFlushed() {
        return this.isFlushed;
    }
}
