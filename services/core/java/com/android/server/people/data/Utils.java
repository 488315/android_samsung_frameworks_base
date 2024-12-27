package com.android.server.people.data;

import android.content.Context;
import android.location.Country;
import android.location.CountryDetector;

import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Utils {
    public static String getCurrentCountryIso(Context context) {
        Country detectCountry;
        CountryDetector countryDetector =
                (CountryDetector) context.getSystemService("country_detector");
        String countryIso =
                (countryDetector == null
                                || (detectCountry = countryDetector.detectCountry()) == null)
                        ? null
                        : detectCountry.getCountryIso();
        return countryIso == null ? Locale.getDefault().getCountry() : countryIso;
    }
}
