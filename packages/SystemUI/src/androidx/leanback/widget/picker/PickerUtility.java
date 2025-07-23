package androidx.leanback.widget.picker;

import android.content.res.Resources;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PickerUtility {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DateConstant {
        public final Locale locale;
        public final String[] months;

        public DateConstant(Locale locale, Resources resources) {
            this.locale = locale;
            this.months = DateFormatSymbols.getInstance(locale).getShortMonths();
            Calendar calendar = Calendar.getInstance(locale);
            int minimum = calendar.getMinimum(5);
            int maximum = calendar.getMaximum(5);
            String[] strArr = new String[(maximum - minimum) + 1];
            for (int i = minimum; i <= maximum; i++) {
                strArr[i - minimum] = String.format("%02d", Integer.valueOf(i));
            }
        }
    }

    private PickerUtility() {
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
