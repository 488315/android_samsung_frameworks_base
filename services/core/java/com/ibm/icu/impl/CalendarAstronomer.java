package com.ibm.icu.impl;

import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CalendarAstronomer {
    public transient double eclipObliquity;
    public long fGmtOffset;
    public double fLatitude;
    public transient double julianDay;
    public transient double siderealT0;
    public transient double sunLongitude;
    public long time;

    public static final double normalize(double d, double d2) {
        return d - (Math.floor(d / d2) * d2);
    }

    public final double getJulianDay() {
        if (this.julianDay == Double.MIN_VALUE) {
            this.julianDay = (this.time - (-210866760000000L)) / 8.64E7d;
        }
        return this.julianDay;
    }

    public final long getSunRiseSet(boolean z) {
        double asin;
        double d;
        double d2;
        long j = this.time;
        long j2 = this.fGmtOffset;
        setTime(
                ((z ? -6L : 6L) * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS)
                        + ((((j + j2)
                                                / BackupManagerConstants
                                                        .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                        * BackupManagerConstants
                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                - j2)
                        + 43200000);
        double d3 = this.fLatitude;
        double tan = Math.tan(d3);
        int i = 0;
        while (true) {
            double d4 = 6.283185307179586d;
            if (this.sunLongitude == Double.MIN_VALUE) {
                double normalize =
                        normalize(
                                (normalize(
                                                        (getJulianDay() - 2447891.5d)
                                                                * 0.017202791632524146d,
                                                        6.283185307179586d)
                                                + 4.87650757829735d)
                                        - 4.935239984568769d,
                                6.283185307179586d);
                double d5 = normalize;
                while (true) {
                    double sin = (d5 - (Math.sin(d5) * 0.016713d)) - normalize;
                    d5 -= sin / (1.0d - (Math.cos(d5) * 0.016713d));
                    if (Math.abs(sin) <= 1.0E-5d) {
                        break;
                    }
                    d4 = 6.283185307179586d;
                }
                this.sunLongitude =
                        new double[] {
                                    normalize(
                                            (Math.atan(
                                                                    Math.sqrt(1.033994144130859d)
                                                                            * Math.tan(d5 / 2.0d))
                                                            * 2.0d)
                                                    + 4.935239984568769d,
                                            d4),
                                    normalize
                                }
                                [0];
            }
            double d6 = this.sunLongitude;
            if (this.eclipObliquity == Double.MIN_VALUE) {
                double julianDay = (getJulianDay() - 2451545.0d) / 36525.0d;
                this.eclipObliquity =
                        ((5.027777777777778E-7d * julianDay * julianDay * julianDay)
                                        + ((23.439292d - (0.013004166666666666d * julianDay))
                                                - ((1.6666666666666665E-7d * julianDay)
                                                        * julianDay)))
                                * 0.017453292519943295d;
            }
            double d7 = this.eclipObliquity;
            double sin2 = Math.sin(d7);
            double cos = Math.cos(d7);
            double sin3 = Math.sin(d6);
            double cos2 = Math.cos(d6);
            double sin4 = Math.sin(0.0d);
            double cos3 = Math.cos(0.0d);
            double d8 = tan;
            double atan2 = Math.atan2((sin3 * cos) - (Math.tan(0.0d) * sin2), cos2);
            asin = Math.asin((cos3 * sin2 * sin3) + (sin4 * cos));
            double acos = Math.acos(Math.tan(asin) * (-d8));
            if (z) {
                acos = 6.283185307179586d - acos;
            }
            double d9 = ((acos + atan2) * 24.0d) / 6.283185307179586d;
            if (this.siderealT0 == Double.MIN_VALUE) {
                double floor = ((Math.floor(getJulianDay() - 0.5d) + 0.5d) - 2451545.0d) / 36525.0d;
                d = d8;
                d2 = 24.0d;
                this.siderealT0 =
                        normalize(
                                (2.5862E-5d * floor * floor)
                                        + (2400.051336d * floor)
                                        + 6.697374558d,
                                24.0d);
            } else {
                d = d8;
                d2 = 24.0d;
            }
            double normalize2 = normalize((d9 - this.siderealT0) * 0.9972695663d, d2);
            long j3 = this.time;
            long j4 =
                    ((((j3 + j2) / BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                            * BackupManagerConstants
                                                    .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                    - j2)
                            + ((long) (normalize2 * 3600000.0d));
            long j5 = j4 - j3;
            setTime(j4);
            i++;
            if (i >= 5 || Math.abs(j5) <= 5000) {
                break;
            }
            tan = d;
        }
        double cos4 = Math.cos(asin);
        long asin2 =
                (long)
                        ((((Math.asin(
                                                                Math.sin(0.014541501551199421d)
                                                                        / Math.sin(
                                                                                Math.acos(
                                                                                        Math.sin(d3)
                                                                                                / cos4)))
                                                        * 240.0d)
                                                * 57.29577951308232d)
                                        / cos4)
                                * 1000.0d);
        long j6 = this.time;
        if (z) {
            asin2 = -asin2;
        }
        long j7 = j6 + asin2;
        setTime(j);
        return j7;
    }

    public final void setTime(long j) {
        this.time = j;
        this.julianDay = Double.MIN_VALUE;
        this.sunLongitude = Double.MIN_VALUE;
        this.eclipObliquity = Double.MIN_VALUE;
        this.siderealT0 = Double.MIN_VALUE;
    }
}
