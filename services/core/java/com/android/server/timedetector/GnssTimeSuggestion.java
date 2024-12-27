package com.android.server.timedetector;

import android.app.time.UnixEpochTime;
import android.app.timedetector.TimeSuggestionHelper;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GnssTimeSuggestion {
    public final TimeSuggestionHelper mTimeSuggestionHelper;

    public GnssTimeSuggestion(UnixEpochTime unixEpochTime) {
        this.mTimeSuggestionHelper = new TimeSuggestionHelper(GnssTimeSuggestion.class, unixEpochTime);
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
        return this.mTimeSuggestionHelper.handleEquals(((GnssTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public final int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public final String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }
}
