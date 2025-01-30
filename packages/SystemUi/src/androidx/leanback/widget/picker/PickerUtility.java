package androidx.leanback.widget.picker;

import android.content.res.Resources;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PickerUtility {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DateConstant {
        public final Locale locale;
        public final String[] months;

        public DateConstant(Locale locale, Resources resources) {
            this.locale = locale;
            this.months = DateFormatSymbols.getInstance(locale).getShortMonths();
            Calendar calendar = Calendar.getInstance(locale);
            PickerUtility.createStringIntArrays(calendar.getMinimum(5), calendar.getMaximum(5));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TimeConstant {
        public final String[] ampm;
        public final String[] hours24;
        public final Locale locale;
        public final String[] minutes;

        public TimeConstant(Locale locale, Resources resources) {
            this.locale = locale;
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            PickerUtility.createStringIntArrays(1, 12);
            this.hours24 = PickerUtility.createStringIntArrays(0, 23);
            this.minutes = PickerUtility.createStringIntArrays(0, 59);
            this.ampm = dateFormatSymbols.getAmPmStrings();
        }
    }

    private PickerUtility() {
    }

    public static String[] createStringIntArrays(int i, int i2) {
        String[] strArr = new String[(i2 - i) + 1];
        for (int i3 = i; i3 <= i2; i3++) {
            strArr[i3 - i] = String.format("%02d", Integer.valueOf(i3));
        }
        return strArr;
    }

    public static Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar calendar2 = Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }
}
