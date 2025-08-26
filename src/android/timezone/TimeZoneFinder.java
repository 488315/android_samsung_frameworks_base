package android.timezone;

import java.util.Objects;

/* loaded from: classes4.dex */
public final class TimeZoneFinder {
    private static TimeZoneFinder sInstance;
    private static final Object sLock = new Object();
    private final com.android.i18n.timezone.TimeZoneFinder mDelegate;

    public static TimeZoneFinder getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new TimeZoneFinder(com.android.i18n.timezone.TimeZoneFinder.getInstance());
            }
        }
        return sInstance;
    }

    private TimeZoneFinder(com.android.i18n.timezone.TimeZoneFinder timeZoneFinder) {
        this.mDelegate = (com.android.i18n.timezone.TimeZoneFinder) Objects.requireNonNull(timeZoneFinder);
    }

    public String getIanaVersion() {
        return this.mDelegate.getIanaVersion();
    }

    public CountryTimeZones lookupCountryTimeZones(String str) {
        com.android.i18n.timezone.CountryTimeZones countryTimeZonesLookupCountryTimeZones = this.mDelegate.lookupCountryTimeZones(str);
        if (countryTimeZonesLookupCountryTimeZones == null) {
            return null;
        }
        return new CountryTimeZones(countryTimeZonesLookupCountryTimeZones);
    }
}
