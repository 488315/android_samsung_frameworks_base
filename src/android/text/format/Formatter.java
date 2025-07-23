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
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(0, indexOf) + str.substring(indexOf + str2.length(), str.length());
    }

    private static String formatMeasureShort(Locale locale, NumberFormat numberFormat, float f, MeasureUnit measureUnit) {
        return MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.SHORT, numberFormat).format(new Measure(Float.valueOf(f), measureUnit));
    }

    private static String formatRoundedBytesResult(Context context, RoundedBytesResult roundedBytesResult) {
        Locale localeFromContext = localeFromContext(context);
        NumberFormat numberFormatter = getNumberFormatter(localeFromContext, roundedBytesResult.fractionDigits);
        if (roundedBytesResult.units == MeasureUnit.BYTE) {
            return context.getString(R.string.fileSizeSuffix, numberFormatter.format(roundedBytesResult.value), getByteSuffixOverride(context.getResources()));
        }
        return formatMeasureShort(localeFromContext, numberFormatter, roundedBytesResult.value, roundedBytesResult.units);
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

        /* JADX WARN: Code restructure failed: missing block: B:45:0x007c, code lost:
        
            if ((r20 & 1) != 0) goto L43;
         */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0087  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static android.text.format.Formatter.RoundedBytesResult roundBytes(long r18, int r20) {
            /*
                r0 = r18
                r2 = r20 & 8
                if (r2 == 0) goto L9
                r2 = 1024(0x400, float:1.435E-42)
                goto Lb
            L9:
                r2 = 1000(0x3e8, float:1.401E-42)
            Lb:
                r3 = 0
                int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
                r6 = 0
                r7 = 1
                if (r5 >= 0) goto L15
                r5 = r7
                goto L16
            L15:
                r5 = r6
            L16:
                if (r5 == 0) goto L19
                long r0 = -r0
            L19:
                float r0 = (float) r0
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.BYTE
                r8 = 1147207680(0x44610000, float:900.0)
                int r9 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                r10 = 1
                if (r9 <= 0) goto L2a
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.KILOBYTE
                long r12 = (long) r2
                float r9 = (float) r2
                float r0 = r0 / r9
                goto L2b
            L2a:
                r12 = r10
            L2b:
                int r9 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r9 <= 0) goto L35
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.MEGABYTE
                long r14 = (long) r2
                long r12 = r12 * r14
                float r9 = (float) r2
                float r0 = r0 / r9
            L35:
                int r9 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r9 <= 0) goto L3f
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.GIGABYTE
                long r14 = (long) r2
                long r12 = r12 * r14
                float r9 = (float) r2
                float r0 = r0 / r9
            L3f:
                int r9 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r9 <= 0) goto L49
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.TERABYTE
                long r14 = (long) r2
                long r12 = r12 * r14
                float r9 = (float) r2
                float r0 = r0 / r9
            L49:
                int r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r8 <= 0) goto L53
                android.icu.util.MeasureUnit r1 = android.icu.util.MeasureUnit.PETABYTE
                long r8 = (long) r2
                long r12 = r12 * r8
                float r2 = (float) r2
                float r0 = r0 / r2
            L53:
                r14 = r1
                int r1 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
                r2 = 2
                if (r1 == 0) goto L7e
                r1 = 1120403456(0x42c80000, float:100.0)
                int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r1 < 0) goto L60
                goto L7e
            L60:
                r1 = 1065353216(0x3f800000, float:1.0)
                int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                r8 = 100
                if (r1 >= 0) goto L6b
            L68:
                r15 = r2
                r7 = r8
                goto L7f
            L6b:
                r1 = 1092616192(0x41200000, float:10.0)
                int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r1 >= 0) goto L7a
                r1 = r20 & 1
                if (r1 == 0) goto L68
                r1 = 10
                r15 = r7
                r7 = r1
                goto L7f
            L7a:
                r1 = r20 & 1
                if (r1 == 0) goto L68
            L7e:
                r15 = r6
            L7f:
                if (r5 == 0) goto L82
                float r0 = -r0
            L82:
                r1 = r20 & 2
                if (r1 != 0) goto L87
                goto L92
            L87:
                float r1 = (float) r7
                float r1 = r1 * r0
                int r1 = java.lang.Math.round(r1)
                long r1 = (long) r1
                long r1 = r1 * r12
                long r3 = (long) r7
                long r3 = r1 / r3
            L92:
                r16 = r3
                android.text.format.Formatter$RoundedBytesResult r12 = new android.text.format.Formatter$RoundedBytesResult
                r13 = r0
                r12.<init>(r13, r14, r15, r16)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.text.format.Formatter.RoundedBytesResult.roundBytes(long, int):android.text.format.Formatter$RoundedBytesResult");
        }
    }

    public static BytesResult formatBytes(Resources resources, long j, int i) {
        RoundedBytesResult roundBytes = RoundedBytesResult.roundBytes(j, i);
        Locale locale = resources.getConfiguration().getLocales().get(0);
        NumberFormat numberFormatter = getNumberFormatter(locale, roundBytes.fractionDigits);
        String format = numberFormatter.format(roundBytes.value);
        String charSequence = SPACES_AND_CONTROLS.trim(deleteFirstFromString(formatMeasureShort(locale, numberFormatter, roundBytes.value, roundBytes.units), format)).toString();
        return new BytesResult(format, roundBytes.units == MeasureUnit.BYTE ? getByteSuffixOverride(resources) : charSequence, charSequence, roundBytes.roundedBytes);
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
