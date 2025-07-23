package android.util;

import java.util.Calendar;

/* loaded from: classes4.dex */
public class MonthDisplayHelper {
    private Calendar mCalendar;
    private int mNumDaysInMonth;
    private int mNumDaysInPrevMonth;
    private int mOffset;
    private final int mWeekStartDay;

    public MonthDisplayHelper(int i, int i2, int i3) {
        if (i3 < 1 || i3 > 7) {
            throw new IllegalArgumentException();
        }
        this.mWeekStartDay = i3;
        Calendar calendar = Calendar.getInstance();
        this.mCalendar = calendar;
        calendar.set(1, i);
        this.mCalendar.set(2, i2);
        this.mCalendar.set(5, 1);
        this.mCalendar.set(11, 0);
        this.mCalendar.set(12, 0);
        this.mCalendar.set(13, 0);
        this.mCalendar.getTimeInMillis();
        recalculate();
    }

    public MonthDisplayHelper(int i, int i2) {
        this(i, i2, 1);
    }

    public int getYear() {
        return this.mCalendar.get(1);
    }

    public int getMonth() {
        return this.mCalendar.get(2);
    }

    public int getWeekStartDay() {
        return this.mWeekStartDay;
    }

    public int getFirstDayOfMonth() {
        return this.mCalendar.get(7);
    }

    public int getNumberOfDaysInMonth() {
        return this.mNumDaysInMonth;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int[] getDigitsForRow(int i) {
        if (i < 0 || i > 5) {
            throw new IllegalArgumentException("row " + i + " out of range (0-5)");
        }
        int[] iArr = new int[7];
        for (int i2 = 0; i2 < 7; i2++) {
            iArr[i2] = getDayAt(i, i2);
        }
        return iArr;
    }

    public int getDayAt(int i, int i2) {
        int i3;
        if (i == 0 && i2 < (i3 = this.mOffset)) {
            return ((this.mNumDaysInPrevMonth + i2) - i3) + 1;
        }
        int i4 = (((i * 7) + i2) - this.mOffset) + 1;
        int i5 = this.mNumDaysInMonth;
        return i4 > i5 ? i4 - i5 : i4;
    }

    public int getRowOf(int i) {
        return ((i + this.mOffset) - 1) / 7;
    }

    public int getColumnOf(int i) {
        return ((i + this.mOffset) - 1) % 7;
    }

    public void previousMonth() {
        this.mCalendar.add(2, -1);
        recalculate();
    }

    public void nextMonth() {
        this.mCalendar.add(2, 1);
        recalculate();
    }

    public boolean isWithinCurrentMonth(int i, int i2) {
        if (i < 0 || i2 < 0 || i > 5 || i2 > 6) {
            return false;
        }
        return (i != 0 || i2 >= this.mOffset) && (((i * 7) + i2) - this.mOffset) + 1 <= this.mNumDaysInMonth;
    }

    private void recalculate() {
        this.mNumDaysInMonth = this.mCalendar.getActualMaximum(5);
        this.mCalendar.add(2, -1);
        this.mNumDaysInPrevMonth = this.mCalendar.getActualMaximum(5);
        this.mCalendar.add(2, 1);
        int firstDayOfMonth = getFirstDayOfMonth() - this.mWeekStartDay;
        if (firstDayOfMonth < 0) {
            firstDayOfMonth += 7;
        }
        this.mOffset = firstDayOfMonth;
    }
}
