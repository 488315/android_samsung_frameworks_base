package com.android.server.timezonedetector;

import com.android.i18n.timezone.TimeZoneFinder;

import java.util.function.Function;

public final class TimeZoneCanonicalizer implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        String findCanonicalTimeZoneId =
                TimeZoneFinder.getInstance().getCountryZonesFinder().findCanonicalTimeZoneId(str);
        return findCanonicalTimeZoneId == null ? str : findCanonicalTimeZoneId;
    }
}
