package com.android.server.location.countrydetector;

import android.content.Context;
import android.location.Country;
import android.location.CountryListener;
import android.os.Handler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CountryDetectorBase {
    public final Context mContext;
    public Country mDetectedCountry;
    public final Handler mHandler = new Handler();
    public CountryListener mListener;

    public CountryDetectorBase(Context context) {
        this.mContext = context.createAttributionContext("CountryDetector");
    }

    public abstract Country detectCountry();

    public void setCountryListener(CountryListener countryListener) {
        this.mListener = countryListener;
    }
}
