package android.text.format;

import android.content.Context;
import android.content.res.Resources;
import android.icu.text.DecimalFormat;
import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.BidiFormatter;
import android.text.TextUtils;
import com.android.internal.R;
import com.android.net.module.util.Inet4AddressUtils;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class Formatter {
    public static final int FLAG_CALCULATE_ROUNDED = 2;
    public static final int FLAG_IEC_UNITS = 8;
    public static final int FLAG_SHORTER = 1;
    public static final int FLAG_SI_UNITS = 4;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final UnicodeSetSpanner SPACES_AND_CONTROLS = new UnicodeSetSpanner(new UnicodeSet("[[:Zs:][:Cf:]]").freeze());

    public static class BytesResult {
        public final long roundedBytes;
        public final String units;
        public final String unitsContentDescription;
        public final String value;

        public BytesResult(String str, String str2, String str3, long j) {
            this.value = str;
            this.units = str2;
            this.unitsContentDescription = str3;
            this.roundedBytes = j;
        }
    }

    private static Locale localeFromContext(Context context) {
        return context.getResources().getConfiguration().getLocales().get(0);
    }

    private static String bidiWrap(Context context, String str) {
        return TextUtils.getLayoutDirectionFromLocale(localeFromContext(context)) == 1 ? BidiFormatter.getInstance(true).unicodeWrap(str) : str;
    }

    public static String formatFileSize(Context context, long j) {
        return formatFileSize(context, j, 4);
    }

    public static String formatFileSize(Context context, long j, int i) {
        if (context == null) {
            return "";
        }
        return bidiWrap(context, formatRoundedBytesResult(context, RoundedBytesResult.roundBytes(j, i)));
    }

    public static String formatShortFileSize(Context context, long j) {
        return formatFileSize(context, j, 5);
    }

    private static String getByteSuffixOverride(Resources resources) {
        return resources.getString(R.string.byteShort);
    }

    private static NumberFormat getNumberFormatter(Locale locale, int i) {
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(i);
        numberFormat.setMaximumFractionDigits(i);
        numberFormat.setGroupingUsed(false);
        if (numberFormat instanceof DecimalFormat) {
            numberFormat.setRoundingMode(4);
        }
        return numberFormat;
    }

    private static String deleteFirstFromString(String str, String str2) {
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return str;
        }
        return str.substring(0, iIndexOf) + str.substring(iIndexOf + str2.length(), str.length());
    }

    private static String formatMeasureShort(Locale locale, NumberFormat numberFormat, float f, MeasureUnit measureUnit) {
        return MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.SHORT, numberFormat).format(new Measure(Float.valueOf(f), measureUnit));
    }

    private static String formatRoundedBytesResult(Context context, RoundedBytesResult roundedBytesResult) {
        Locale localeLocaleFromContext = localeFromContext(context);
        NumberFormat numberFormatter = getNumberFormatter(localeLocaleFromContext, roundedBytesResult.fractionDigits);
        if (roundedBytesResult.units == MeasureUnit.BYTE) {
            return context.getString(R.string.fileSizeSuffix, numberFormatter.format(roundedBytesResult.value), getByteSuffixOverride(context.getResources()));
        }
        return formatMeasureShort(localeLocaleFromContext, numberFormatter, roundedBytesResult.value, roundedBytesResult.units);
    }

    public static class RoundedBytesResult {
        public final int fractionDigits;
        public final long roundedBytes;
        public final MeasureUnit units;
        public final float value;

        private RoundedBytesResult(float f, MeasureUnit measureUnit, int i, long j) {
            this.value = f;
            this.units = measureUnit;
            this.fractionDigits = i;
            this.roundedBytes = j;
        }

        /* JADX WARN: Removed duplicated region for block: B:43:0x007e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static RoundedBytesResult roundBytes(long j, int i) {
            long j2;
            int i2;
            long j3 = j;
            int i3 = (i & 8) != 0 ? 1024 : 1000;
            int i4 = 1;
            boolean z = j3 < 0;
            if (z) {
                j3 = -j3;
            }
            float f = j3;
            MeasureUnit measureUnit = MeasureUnit.BYTE;
            if (f > 900.0f) {
                measureUnit = MeasureUnit.KILOBYTE;
                j2 = i3;
                f /= i3;
            } else {
                j2 = 1;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.MEGABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.GIGABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.TERABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.PETABYTE;
                j2 *= i3;
                f /= i3;
            }
            MeasureUnit measureUnit2 = measureUnit;
            if (j2 == 1 || f >= 100.0f) {
                i2 = 0;
            } else if (f >= 1.0f) {
                if (f < 10.0f) {
                    if ((i & 1) != 0) {
                        i2 = 1;
                        i4 = 10;
                    }
                } else if ((i & 1) != 0) {
                }
                i2 = 2;
                i4 = 100;
            } else {
                i2 = 2;
                i4 = 100;
            }
            if (z) {
                f = -f;
            }
            return new RoundedBytesResult(f, measureUnit2, i2, (i & 2) != 0 ? (Math.round(i4 * f) * j2) / i4 : 0L);
        }
    }

    public static BytesResult formatBytes(Resources resources, long j, int i) {
        RoundedBytesResult roundedBytesResultRoundBytes = RoundedBytesResult.roundBytes(j, i);
        Locale locale = resources.getConfiguration().getLocales().get(0);
        NumberFormat numberFormatter = getNumberFormatter(locale, roundedBytesResultRoundBytes.fractionDigits);
        String str = numberFormatter.format(roundedBytesResultRoundBytes.value);
        String string = SPACES_AND_CONTROLS.trim(deleteFirstFromString(formatMeasureShort(locale, numberFormatter, roundedBytesResultRoundBytes.value, roundedBytesResultRoundBytes.units), str)).toString();
        return new BytesResult(str, roundedBytesResultRoundBytes.units == MeasureUnit.BYTE ? getByteSuffixOverride(resources) : string, string, roundedBytesResultRoundBytes.roundedBytes);
    }

    @Deprecated
    public static String formatIpAddress(int i) {
        return Inet4AddressUtils.intToInet4AddressHTL(i).getHostAddress();
    }

    public static String formatShortElapsedTime(Context context, long j) {
        int i;
        int i2;
        int i3;
        long j2 = j / 1000;
        if (j2 >= 86400) {
            i = (int) (j2 / 86400);
            j2 -= SECONDS_PER_DAY * i;
        } else {
            i = 0;
        }
        if (j2 >= 3600) {
            i2 = (int) (j2 / 3600);
            j2 -= i2 * 3600;
        } else {
            i2 = 0;
        }
        if (j2 >= 60) {
            i3 = (int) (j2 / 60);
            j2 -= i3 * 60;
        } else {
            i3 = 0;
        }
        int i4 = (int) j2;
        MeasureFormat measureFormat = MeasureFormat.getInstance(localeFromContext(context), MeasureFormat.FormatWidth.SHORT);
        if (i >= 2 || (i > 0 && i2 == 0)) {
            return measureFormat.format(new Measure(Integer.valueOf(i + ((i2 + 12) / 24)), MeasureUnit.DAY));
        }
        if (i > 0) {
            return measureFormat.formatMeasures(new Measure(Integer.valueOf(i), MeasureUnit.DAY), new Measure(Integer.valueOf(i2), MeasureUnit.HOUR));
        }
        if (i2 >= 2 || (i2 > 0 && i3 == 0)) {
            return measureFormat.format(new Measure(Integer.valueOf(i2 + ((i3 + 30) / 60)), MeasureUnit.HOUR));
        }
        if (i2 > 0) {
            return measureFormat.formatMeasures(new Measure(Integer.valueOf(i2), MeasureUnit.HOUR), new Measure(Integer.valueOf(i3), MeasureUnit.MINUTE));
        }
        if (i3 >= 2 || (i3 > 0 && i4 == 0)) {
            return measureFormat.format(new Measure(Integer.valueOf(i3 + ((i4 + 30) / 60)), MeasureUnit.MINUTE));
        }
        if (i3 > 0) {
            return measureFormat.formatMeasures(new Measure(Integer.valueOf(i3), MeasureUnit.MINUTE), new Measure(Integer.valueOf(i4), MeasureUnit.SECOND));
        }
        return measureFormat.format(new Measure(Integer.valueOf(i4), MeasureUnit.SECOND));
    }

    public static String formatShortElapsedTimeRoundingUpToMinutes(Context context, long j) {
        long j2 = (j + 59999) / 60000;
        if (j2 == 0 || j2 == 1) {
            return MeasureFormat.getInstance(localeFromContext(context), MeasureFormat.FormatWidth.SHORT).format(new Measure(Long.valueOf(j2), MeasureUnit.MINUTE));
        }
        return formatShortElapsedTime(context, j2 * 60000);
    }
}
