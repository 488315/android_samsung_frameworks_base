package android.text.format;

import android.app.blob.XmlTags;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DateTimePatternGenerator;
import android.icu.util.ULocale;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import com.android.internal.content.NativeLibraryHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class DateFormat {

    @Deprecated
    public static final char AM_PM = 'a';

    @Deprecated
    public static final char CAPITAL_AM_PM = 'A';

    @Deprecated
    public static final char DATE = 'd';

    @Deprecated
    public static final char DAY = 'E';
    static final long DISALLOW_DUPLICATE_FIELD_IN_SKELETON = 170233598;

    @Deprecated
    public static final char HOUR = 'h';

    @Deprecated
    public static final char HOUR_OF_DAY = 'k';

    @Deprecated
    public static final char MINUTE = 'm';

    @Deprecated
    public static final char MONTH = 'M';

    @Deprecated
    public static final char QUOTE = '\'';

    @Deprecated
    public static final char SECONDS = 's';

    @Deprecated
    public static final char STANDALONE_MONTH = 'L';

    @Deprecated
    public static final char TIME_ZONE = 'z';

    @Deprecated
    public static final char YEAR = 'y';
    private static boolean sIs24Hour;
    private static Locale sIs24HourLocale;
    private static final Object sLocaleLock = new Object();

    public static boolean is24HourFormat(Context context) {
        return is24HourFormat(context, context.getUserId());
    }

    public static boolean is24HourFormat(Context context, int i) {
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), Settings.System.TIME_12_24, i);
        if (stringForUser != null) {
            return stringForUser.equals("24");
        }
        return is24HourLocale(context.getResources().getConfiguration().locale);
    }

    public static boolean is24HourLocale(Locale locale) {
        Object obj = sLocaleLock;
        synchronized (obj) {
            Locale locale2 = sIs24HourLocale;
            if (locale2 != null && locale2.equals(locale)) {
                return sIs24Hour;
            }
            java.text.DateFormat timeInstance = java.text.DateFormat.getTimeInstance(1, locale);
            boolean zHasDesignator = timeInstance instanceof SimpleDateFormat ? hasDesignator(((SimpleDateFormat) timeInstance).toPattern(), 'H') : false;
            synchronized (obj) {
                sIs24HourLocale = locale;
                sIs24Hour = zHasDesignator;
            }
            return zHasDesignator;
        }
    }

    public static String getBestDateTimePattern(Locale locale, String str) {
        ULocale uLocaleForLocale = ULocale.forLocale(locale);
        return getCompatibleEnglishPattern(uLocaleForLocale, DateTimePatternGenerator.getInstance(uLocaleForLocale).getBestPattern(str, 0, !CompatChanges.isChangeEnabled(DISALLOW_DUPLICATE_FIELD_IN_SKELETON)));
    }

    public static java.text.DateFormat getTimeFormat(Context context) {
        return new SimpleDateFormat(getTimeFormatString(context), context.getResources().getConfiguration().locale);
    }

    public static String getTimeFormatString(Context context) {
        return getTimeFormatString(context, context.getUserId());
    }

    public static String getTimeFormatString(Context context, int i) {
        ULocale uLocaleForLocale = ULocale.forLocale(context.getResources().getConfiguration().locale);
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocaleForLocale);
        return getCompatibleEnglishPattern(uLocaleForLocale, is24HourFormat(context, i) ? dateTimePatternGenerator.getBestPattern("Hm") : dateTimePatternGenerator.getBestPattern("hm"));
    }

    public static java.text.DateFormat getDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(3, context.getResources().getConfiguration().locale);
    }

    public static java.text.DateFormat getLongDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(1, context.getResources().getConfiguration().locale);
    }

    public static java.text.DateFormat getMediumDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(2, context.getResources().getConfiguration().locale);
    }

    public static char[] getDateFormatOrder(Context context) {
        return getDateFormatOrder(getDateFormatString(context));
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static char[] getDateFormatOrder(String str) {
        char[] cArr = new char[3];
        int i = 0;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == 'd' || cCharAt == 'L' || cCharAt == 'M' || cCharAt == 'y') {
                if (cCharAt == 'd' && !z) {
                    cArr[i2] = DATE;
                    i2++;
                    z = true;
                } else if ((cCharAt == 'L' || cCharAt == 'M') && !z2) {
                    cArr[i2] = MONTH;
                    i2++;
                    z2 = true;
                } else if (cCharAt == 'y' && !z3) {
                    cArr[i2] = 'y';
                    i2++;
                    z3 = true;
                }
            } else if (cCharAt == 'G') {
                continue;
            } else {
                if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                    throw new IllegalArgumentException("Bad pattern character '" + cCharAt + "' in " + str);
                }
                if (cCharAt != '\'') {
                    continue;
                } else if (i < str.length() - 1) {
                    int i3 = i + 1;
                    if (str.charAt(i3) == '\'') {
                        i = i3;
                    } else {
                        int iIndexOf = str.indexOf(39, i + 1);
                        if (iIndexOf == -1) {
                            throw new IllegalArgumentException("Bad quoting in " + str);
                        }
                        i = iIndexOf + 1;
                    }
                }
            }
            i++;
        }
        return cArr;
    }

    private static String getDateFormatString(Context context) {
        java.text.DateFormat dateInstance = java.text.DateFormat.getDateInstance(3, context.getResources().getConfiguration().locale);
        if (dateInstance instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) dateInstance).toPattern();
        }
        throw new AssertionError("!(df instanceof SimpleDateFormat)");
    }

    public static CharSequence format(CharSequence charSequence, long j) {
        return format(charSequence, new Date(j));
    }

    public static CharSequence format(CharSequence charSequence, Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return format(charSequence, gregorianCalendar);
    }

    public static boolean hasSeconds(CharSequence charSequence) {
        return hasDesignator(charSequence, 's');
    }

    public static boolean hasDesignator(CharSequence charSequence, char c) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt == '\'') {
                z = !z;
            } else if (!z && cCharAt == c) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CharSequence format(CharSequence charSequence, Calendar calendar) {
        int i;
        String dayOfWeekString;
        int length;
        int length2;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        DateFormatSymbols icuDateFormatSymbols = getIcuDateFormatSymbols(Locale.getDefault());
        String[] amPmStrings = icuDateFormatSymbols.getAmPmStrings();
        int length3 = charSequence.length();
        int i2 = 0;
        while (i2 < length3) {
            char cCharAt = spannableStringBuilder.charAt(i2);
            if (cCharAt == '\'') {
                length2 = appendQuotedText(spannableStringBuilder, i2);
                length = spannableStringBuilder.length();
            } else {
                int i3 = 1;
                while (true) {
                    i = i2 + i3;
                    if (i < length3 && spannableStringBuilder.charAt(i) == cCharAt) {
                        i3++;
                    }
                }
                if (cCharAt == 'A') {
                    dayOfWeekString = amPmStrings[calendar.get(9)];
                    if (dayOfWeekString == null) {
                        spannableStringBuilder.replace(i2, i, (CharSequence) dayOfWeekString);
                        length2 = dayOfWeekString.length();
                        length = spannableStringBuilder.length();
                    } else {
                        length = length3;
                        length2 = i3;
                    }
                } else if (cCharAt == 'E') {
                    dayOfWeekString = getDayOfWeekString(icuDateFormatSymbols, calendar.get(7), i3, cCharAt);
                    if (dayOfWeekString == null) {
                    }
                } else if (cCharAt == 'H') {
                    dayOfWeekString = zeroPad(calendar.get(11), i3);
                    if (dayOfWeekString == null) {
                    }
                } else {
                    if (cCharAt != 'a') {
                        if (cCharAt == 'h') {
                            int i4 = calendar.get(10);
                            dayOfWeekString = zeroPad((cCharAt == 'h' && i4 == 0) ? 12 : i4, i3);
                        } else if (cCharAt != 'k') {
                            if (cCharAt == 'm') {
                                dayOfWeekString = zeroPad(calendar.get(12), i3);
                            } else if (cCharAt == 's') {
                                dayOfWeekString = zeroPad(calendar.get(13), i3);
                            } else if (cCharAt != 'c') {
                                if (cCharAt == 'd') {
                                    dayOfWeekString = zeroPad(calendar.get(5), i3);
                                } else if (cCharAt == 'y') {
                                    dayOfWeekString = getYearString(calendar.get(1), i3);
                                } else if (cCharAt != 'z') {
                                    switch (cCharAt) {
                                        case 'K':
                                            break;
                                        case 'L':
                                        case 'M':
                                            dayOfWeekString = getMonthString(icuDateFormatSymbols, calendar.get(2), i3, cCharAt);
                                            break;
                                        default:
                                            dayOfWeekString = null;
                                            break;
                                    }
                                } else {
                                    dayOfWeekString = getTimeZoneString(calendar, i3);
                                }
                            }
                        }
                    }
                    if (dayOfWeekString == null) {
                    }
                }
            }
            i2 += length2;
            length3 = length;
        }
        if (charSequence instanceof Spanned) {
            return new SpannedString(spannableStringBuilder);
        }
        return spannableStringBuilder.toString();
    }

    private static String getDayOfWeekString(DateFormatSymbols dateFormatSymbols, int i, int i2, int i3) {
        int i4 = 1;
        int i5 = i3 == 99 ? 1 : 0;
        if (i2 == 5) {
            i4 = 2;
        } else if (i2 != 4) {
            i4 = 0;
        }
        return dateFormatSymbols.getWeekdays(i5, i4)[i];
    }

    private static String getMonthString(DateFormatSymbols dateFormatSymbols, int i, int i2, int i3) {
        int i4 = i3 == 76 ? 1 : 0;
        if (i2 == 5) {
            return dateFormatSymbols.getMonths(i4, 2)[i];
        }
        if (i2 == 4) {
            return dateFormatSymbols.getMonths(i4, 1)[i];
        }
        if (i2 == 3) {
            return dateFormatSymbols.getMonths(i4, 0)[i];
        }
        return zeroPad(i + 1, i2);
    }

    private static String getTimeZoneString(Calendar calendar, int i) {
        TimeZone timeZone = calendar.getTimeZone();
        if (i < 2) {
            return formatZoneOffset(calendar.get(16) + calendar.get(15), i);
        }
        return timeZone.getDisplayName(calendar.get(16) != 0, 0);
    }

    private static String formatZoneOffset(int i, int i2) {
        int i3 = i / 1000;
        StringBuilder sb = new StringBuilder();
        if (i3 < 0) {
            sb.insert(0, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            i3 = -i3;
        } else {
            sb.insert(0, "+");
        }
        sb.append(zeroPad(i3 / 3600, 2));
        sb.append(zeroPad((i3 % 3600) / 60, 2));
        return sb.toString();
    }

    private static String getYearString(int i, int i2) {
        if (i2 <= 2) {
            return zeroPad(i % 100, 2);
        }
        return String.format(Locale.getDefault(), "%d", Integer.valueOf(i));
    }

    public static int appendQuotedText(SpannableStringBuilder spannableStringBuilder, int i) {
        int length = spannableStringBuilder.length();
        int i2 = i + 1;
        if (i2 < length && spannableStringBuilder.charAt(i2) == '\'') {
            spannableStringBuilder.delete(i, i2);
            return 1;
        }
        spannableStringBuilder.delete(i, i2);
        int i3 = length - 1;
        int i4 = 0;
        while (i < i3) {
            if (spannableStringBuilder.charAt(i) == '\'') {
                int i5 = i + 1;
                if (i5 < i3 && spannableStringBuilder.charAt(i5) == '\'') {
                    spannableStringBuilder.delete(i, i5);
                    i3--;
                    i4++;
                    i = i5;
                } else {
                    spannableStringBuilder.delete(i, i5);
                    return i4;
                }
            } else {
                i++;
                i4++;
            }
        }
        return i4;
    }

    private static String zeroPad(int i, int i2) {
        return String.format(Locale.getDefault(), "%0" + i2 + XmlTags.ATTR_DESCRIPTION, Integer.valueOf(i));
    }

    public static DateFormatSymbols getIcuDateFormatSymbols(Locale locale) {
        return new DateFormatSymbols((Class<? extends android.icu.util.Calendar>) android.icu.util.GregorianCalendar.class, locale);
    }

    private static String getCompatibleEnglishPattern(ULocale uLocale, String str) {
        String country;
        return (str == null || uLocale == null || !"en".equals(uLocale.getLanguage()) || !((country = uLocale.getCountry()) == null || country.isEmpty() || "US".equals(country))) ? str : str.replace((char) 8239, ' ');
    }
}
