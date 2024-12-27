package com.android.server.timedetector;

import android.app.time.UnixEpochTime;
import android.app.timedetector.TimeSuggestionHelper;

import java.util.Objects;

public final class GnssTimeSuggestion {
    public final TimeSuggestionHelper mTimeSuggestionHelper;

    public GnssTimeSuggestion(UnixEpochTime unixEpochTime) {
        this.mTimeSuggestionHelper =
                new TimeSuggestionHelper(GnssTimeSuggestion.class, unixEpochTime);
    }

    public GnssTimeSuggestion(TimeSuggestionHelper timeSuggestionHelper) {
        Objects.requireNonNull(timeSuggestionHelper);
        this.mTimeSuggestionHelper = timeSuggestionHelper;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || GnssTimeSuggestion.class != obj.getClass()) {
            return false;
        }
        return this.mTimeSuggestionHelper.handleEquals(
                ((GnssTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public final int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public final String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }
}
