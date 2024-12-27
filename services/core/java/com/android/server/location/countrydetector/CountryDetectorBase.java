package com.android.server.location.countrydetector;

import android.content.Context;
import android.location.Country;
import android.location.CountryListener;
import android.os.Handler;

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
