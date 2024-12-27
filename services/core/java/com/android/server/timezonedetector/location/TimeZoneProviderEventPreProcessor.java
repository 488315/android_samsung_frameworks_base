package com.android.server.timezonedetector.location;

import android.service.timezone.TimeZoneProviderEvent;

public interface TimeZoneProviderEventPreProcessor {
    TimeZoneProviderEvent preProcess(TimeZoneProviderEvent timeZoneProviderEvent);
}
