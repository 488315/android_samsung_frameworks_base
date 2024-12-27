package com.android.server.timezonedetector.location;

import java.time.Duration;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TimeZoneProviderRequest {
    public static final TimeZoneProviderRequest STOP_UPDATES =
            new TimeZoneProviderRequest(false, null, null);
    public final Duration mEventFilteringAgeThreshold;
    public final Duration mInitializationTimeout;
    public final boolean mSendUpdates;

    public TimeZoneProviderRequest(boolean z, Duration duration, Duration duration2) {
        this.mSendUpdates = z;
        this.mInitializationTimeout = duration;
        this.mEventFilteringAgeThreshold = duration2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TimeZoneProviderRequest.class != obj.getClass()) {
            return false;
        }
        TimeZoneProviderRequest timeZoneProviderRequest = (TimeZoneProviderRequest) obj;
        return this.mSendUpdates == timeZoneProviderRequest.mSendUpdates
                && Objects.equals(
                        this.mInitializationTimeout, timeZoneProviderRequest.mInitializationTimeout)
                && Objects.equals(
                        this.mEventFilteringAgeThreshold,
                        timeZoneProviderRequest.mEventFilteringAgeThreshold);
    }

    public final int hashCode() {
        return Objects.hash(
                Boolean.valueOf(this.mSendUpdates),
                this.mInitializationTimeout,
                this.mEventFilteringAgeThreshold);
    }

    public final String toString() {
        return "TimeZoneProviderRequest{mSendUpdates="
                + this.mSendUpdates
                + ", mInitializationTimeout="
                + this.mInitializationTimeout
                + ", mEventFilteringAgeThreshold="
                + this.mEventFilteringAgeThreshold
                + "}";
    }
}
