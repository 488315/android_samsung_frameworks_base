package com.samsung.android.wifi;

import android.os.SystemProperties;

public class SemOpBrandingLoader {
    private static SemOpBrandingLoader sInstance = null;
    private SemVendor mVendor = getSalesCode();

    private SemOpBrandingLoader() {}

    private SemVendor getSalesCode() {
        return getVendor(SystemProperties.get("ro.csc.sales_code"));
    }

    private SemVendor getVendor(String value) {
        if (value != null) {
            try {
                return SemVendor.valueOf(value);
            } catch (IllegalArgumentException e) {
            }
        }
        return SemVendor.Unknown;
    }

    public static synchronized SemOpBrandingLoader getInstance() {
        SemOpBrandingLoader semOpBrandingLoader;
        synchronized (SemOpBrandingLoader.class) {
            if (sInstance == null) {
                sInstance = new SemOpBrandingLoader();
            }
            semOpBrandingLoader = sInstance;
        }
        return semOpBrandingLoader;
    }

    public SemVendor getOpBranding() {
        return this.mVendor;
    }

    public enum SemVendor {
        SKT(1),
        KTT(1),
        LGU(1),
        KOO(1),
        VZW(2),
        ATT(2),
        TMB(2),
        MTR(2),
        TMK(2),
        ATO(5),
        CHA(5),
        AIO(5),
        SingTel(5),
        AIS(5),
        CMCC(3),
        DCM(4),
        Unknown(5);

        public static final int COUNTRY_CHINA = 3;
        public static final int COUNTRY_JAPAN = 4;
        public static final int COUNTRY_KOREA = 1;
        public static final int COUNTRY_OTHERS = 5;
        public static final int COUNTRY_USA = 2;
        private final int country;

        SemVendor(int country) {
            this.country = country;
        }

        public int getCountry() {
            return this.country;
        }
    }
}
