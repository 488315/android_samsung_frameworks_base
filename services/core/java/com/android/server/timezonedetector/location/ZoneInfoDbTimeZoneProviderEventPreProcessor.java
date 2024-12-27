package com.android.server.timezonedetector.location;

import android.service.timezone.TimeZoneProviderEvent;
import android.service.timezone.TimeZoneProviderStatus;
import android.util.Log;
import android.util.Slog;
import com.android.i18n.timezone.ZoneInfoDb;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ZoneInfoDbTimeZoneProviderEventPreProcessor implements TimeZoneProviderEventPreProcessor {
    @Override // com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor
    public final TimeZoneProviderEvent preProcess(TimeZoneProviderEvent timeZoneProviderEvent) {
        if (timeZoneProviderEvent.getSuggestion() != null && !timeZoneProviderEvent.getSuggestion().getTimeZoneIds().isEmpty()) {
            for (String str : timeZoneProviderEvent.getSuggestion().getTimeZoneIds()) {
                if (!ZoneInfoDb.getInstance().hasTimeZone(str)) {
                    String str2 = "event=" + timeZoneProviderEvent + " has unsupported zone(" + str + ")";
                    int i = LocationTimeZoneManagerService.$r8$clinit;
                    if (Log.isLoggable("LocationTZDetector", 4)) {
                        Slog.i("LocationTZDetector", str2);
                    }
                    return TimeZoneProviderEvent.createUncertainEvent(timeZoneProviderEvent.getCreationElapsedMillis(), new TimeZoneProviderStatus.Builder(timeZoneProviderEvent.getTimeZoneProviderStatus()).setTimeZoneResolutionOperationStatus(3).build());
                }
            }
        }
        return timeZoneProviderEvent;
    }
}
