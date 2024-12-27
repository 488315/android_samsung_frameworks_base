package com.android.server.timezonedetector;

import android.app.time.LocationTimeZoneAlgorithmStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LocationAlgorithmEvent {
    public final LocationTimeZoneAlgorithmStatus mAlgorithmStatus;
    public ArrayList mDebugInfo;
    public final GeolocationTimeZoneSuggestion mSuggestion;

    public LocationAlgorithmEvent(
            LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus,
            GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion) {
        this.mAlgorithmStatus = locationTimeZoneAlgorithmStatus;
        this.mSuggestion = geolocationTimeZoneSuggestion;
    }

    public final void addDebugInfo(String... strArr) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList();
        }
        this.mDebugInfo.addAll(Arrays.asList(strArr));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || LocationAlgorithmEvent.class != obj.getClass()) {
            return false;
        }
        LocationAlgorithmEvent locationAlgorithmEvent = (LocationAlgorithmEvent) obj;
        return this.mAlgorithmStatus.equals(locationAlgorithmEvent.mAlgorithmStatus)
                && Objects.equals(this.mSuggestion, locationAlgorithmEvent.mSuggestion);
    }

    public final int hashCode() {
        return Objects.hash(this.mAlgorithmStatus, this.mSuggestion);
    }

    public final String toString() {
        return "LocationAlgorithmEvent{mAlgorithmStatus="
                + this.mAlgorithmStatus
                + ", mSuggestion="
                + this.mSuggestion
                + ", mDebugInfo="
                + this.mDebugInfo
                + '}';
    }
}
