package android.app.admin;

import android.app.admin.SystemUpdatePolicy;
import android.util.Log;
import android.util.Pair;
import com.android.internal.content.NativeLibraryHelper;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FreezePeriod {
    static final int DAYS_IN_YEAR = 365;
    private static final int SENTINEL_YEAR = 2001;
    private static final String TAG = "FreezePeriod";
    private final MonthDay mEnd;
    private final int mEndDay;
    private final MonthDay mStart;
    private final int mStartDay;

    public FreezePeriod(MonthDay monthDay, MonthDay monthDay2) {
        this.mStart = monthDay;
        this.mStartDay = monthDay.atYear(2001).getDayOfYear();
        this.mEnd = monthDay2;
        this.mEndDay = monthDay2.atYear(2001).getDayOfYear();
    }

    public MonthDay getStart() {
        return this.mStart;
    }

    public MonthDay getEnd() {
        return this.mEnd;
    }

    private FreezePeriod(int i, int i2) {
        this.mStartDay = i;
        this.mStart = dayOfYearToMonthDay(i);
        this.mEndDay = i2;
        this.mEnd = dayOfYearToMonthDay(i2);
    }

    int getLength() {
        return (getEffectiveEndDay() - this.mStartDay) + 1;
    }

    boolean isWrapped() {
        return this.mEndDay < this.mStartDay;
    }

    int getEffectiveEndDay() {
        if (!isWrapped()) {
            return this.mEndDay;
        }
        return this.mEndDay + 365;
    }

    boolean contains(LocalDate localDate) {
        int iDayOfYearDisregardLeapYear = dayOfYearDisregardLeapYear(localDate);
        return !isWrapped() ? this.mStartDay <= iDayOfYearDisregardLeapYear && iDayOfYearDisregardLeapYear <= this.mEndDay : this.mStartDay <= iDayOfYearDisregardLeapYear || iDayOfYearDisregardLeapYear <= this.mEndDay;
    }

    boolean after(LocalDate localDate) {
        return this.mStartDay > dayOfYearDisregardLeapYear(localDate);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    Pair<LocalDate, LocalDate> toCurrentOrFutureRealDates(LocalDate localDate) {
        ?? IsWrapped;
        int iDayOfYearDisregardLeapYear = dayOfYearDisregardLeapYear(localDate);
        int i = 0;
        if (contains(localDate)) {
            if (this.mStartDay <= iDayOfYearDisregardLeapYear) {
                IsWrapped = isWrapped();
            } else {
                i = -1;
                IsWrapped = 0;
            }
        } else if (this.mStartDay > iDayOfYearDisregardLeapYear) {
            IsWrapped = isWrapped();
        } else {
            i = 1;
            IsWrapped = 1;
        }
        return new Pair<>(LocalDate.ofYearDay(2001, this.mStartDay).withYear(localDate.getYear() + i), LocalDate.ofYearDay(2001, this.mEndDay).withYear(localDate.getYear() + IsWrapped));
    }

    public String toString() {
        DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("MMM dd");
        return LocalDate.ofYearDay(2001, this.mStartDay).format(dateTimeFormatterOfPattern) + " - " + LocalDate.ofYearDay(2001, this.mEndDay).format(dateTimeFormatterOfPattern);
    }

    private static MonthDay dayOfYearToMonthDay(int i) {
        LocalDate localDateOfYearDay = LocalDate.ofYearDay(2001, i);
        return MonthDay.of(localDateOfYearDay.getMonth(), localDateOfYearDay.getDayOfMonth());
    }

    private static int dayOfYearDisregardLeapYear(LocalDate localDate) {
        return localDate.withYear(2001).getDayOfYear();
    }

    public static int distanceWithoutLeapYear(LocalDate localDate, LocalDate localDate2) {
        return (dayOfYearDisregardLeapYear(localDate) - dayOfYearDisregardLeapYear(localDate2)) + ((localDate.getYear() - localDate2.getYear()) * 365);
    }

    static List<FreezePeriod> canonicalizePeriods(List<FreezePeriod> list) {
        boolean[] zArr = new boolean[365];
        for (FreezePeriod freezePeriod : list) {
            for (int i = freezePeriod.mStartDay; i <= freezePeriod.getEffectiveEndDay(); i++) {
                zArr[(i - 1) % 365] = true;
            }
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < 365) {
            if (zArr[i2]) {
                int i3 = i2 + 1;
                while (i2 < 365 && zArr[i2]) {
                    i2++;
                }
                arrayList.add(new FreezePeriod(i3, i2));
            } else {
                i2++;
            }
        }
        int size = arrayList.size() - 1;
        if (size > 0 && ((FreezePeriod) arrayList.get(size)).mEndDay == 365 && ((FreezePeriod) arrayList.get(0)).mStartDay == 1) {
            arrayList.set(size, new FreezePeriod(((FreezePeriod) arrayList.get(size)).mStartDay, ((FreezePeriod) arrayList.get(0)).mEndDay));
            arrayList.remove(0);
        }
        return arrayList;
    }

    static void validatePeriods(List<FreezePeriod> list) {
        FreezePeriod freezePeriod;
        int i;
        List<FreezePeriod> listCanonicalizePeriods = canonicalizePeriods(list);
        if (listCanonicalizePeriods.size() != list.size()) {
            throw SystemUpdatePolicy.ValidationFailedException.duplicateOrOverlapPeriods();
        }
        for (int i2 = 0; i2 < listCanonicalizePeriods.size(); i2++) {
            FreezePeriod freezePeriod2 = listCanonicalizePeriods.get(i2);
            if (freezePeriod2.getLength() > 90) {
                throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooLong("Freeze period " + freezePeriod2 + " is too long: " + freezePeriod2.getLength() + " days");
            }
            if (i2 > 0) {
                freezePeriod = listCanonicalizePeriods.get(i2 - 1);
            } else {
                freezePeriod = listCanonicalizePeriods.get(listCanonicalizePeriods.size() - 1);
            }
            if (freezePeriod != freezePeriod2) {
                if (i2 == 0 && !freezePeriod.isWrapped()) {
                    i = freezePeriod2.mStartDay + (365 - freezePeriod.mEndDay);
                } else {
                    i = freezePeriod2.mStartDay - freezePeriod.mEndDay;
                }
                int i3 = i - 1;
                if (i3 < 60) {
                    throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooClose("Freeze periods " + freezePeriod + " and " + freezePeriod2 + " are too close together: " + i3 + " days apart");
                }
            }
        }
    }

    static void validateAgainstPreviousFreezePeriod(List<FreezePeriod> list, LocalDate localDate, LocalDate localDate2, LocalDate localDate3) {
        if (list.size() == 0 || localDate == null || localDate2 == null) {
            return;
        }
        if (localDate.isAfter(localDate3) || localDate2.isAfter(localDate3)) {
            Log.w(TAG, "Previous period (" + localDate + "," + localDate2 + ") is after current date " + localDate3);
        }
        List<FreezePeriod> listCanonicalizePeriods = canonicalizePeriods(list);
        FreezePeriod freezePeriod = listCanonicalizePeriods.get(0);
        for (FreezePeriod freezePeriod2 : listCanonicalizePeriods) {
            if (freezePeriod2.contains(localDate3) || freezePeriod2.mStartDay > dayOfYearDisregardLeapYear(localDate3)) {
                freezePeriod = freezePeriod2;
                break;
            }
        }
        Pair<LocalDate, LocalDate> currentOrFutureRealDates = freezePeriod.toCurrentOrFutureRealDates(localDate3);
        if (localDate3.isAfter(currentOrFutureRealDates.first)) {
            currentOrFutureRealDates = new Pair<>(localDate3, currentOrFutureRealDates.second);
        }
        if (currentOrFutureRealDates.first.isAfter(currentOrFutureRealDates.second)) {
            throw new IllegalStateException("Current freeze dates inverted: " + currentOrFutureRealDates.first + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + currentOrFutureRealDates.second);
        }
        String str = "Prev: " + localDate + "," + localDate2 + "; cur: " + currentOrFutureRealDates.first + "," + currentOrFutureRealDates.second;
        long jDistanceWithoutLeapYear = distanceWithoutLeapYear(currentOrFutureRealDates.first, localDate2) - 1;
        if (jDistanceWithoutLeapYear > 0) {
            if (jDistanceWithoutLeapYear >= 60) {
                return;
            }
            throw SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooClose("Previous freeze period too close to new period: " + jDistanceWithoutLeapYear + ", " + str);
        }
        long jDistanceWithoutLeapYear2 = distanceWithoutLeapYear(currentOrFutureRealDates.second, localDate) + 1;
        if (jDistanceWithoutLeapYear2 <= 90) {
            return;
        }
        throw SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooLong("Combined freeze period exceeds maximum days: " + jDistanceWithoutLeapYear2 + ", " + str);
    }
}
