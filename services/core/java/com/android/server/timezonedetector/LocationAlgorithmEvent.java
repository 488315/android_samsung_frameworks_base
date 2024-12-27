package com.android.server.timezonedetector;

import android.app.time.LocationTimeZoneAlgorithmStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
