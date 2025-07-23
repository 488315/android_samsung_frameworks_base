package com.google.android.material.datepicker;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MonthAdapter extends BaseAdapter {
    public final CalendarConstraints calendarConstraints;
    public CalendarStyle calendarStyle;
    public final DayViewDecorator dayViewDecorator;
    public final Month month;
    public final Collection previouslySelectedDates;
    public static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendarOf(null).getMaximum(4);
    public static final int MAXIMUM_GRID_CELLS = (UtcDates.getUtcCalendarOf(null).getMaximum(7) + UtcDates.getUtcCalendarOf(null).getMaximum(5)) - 1;

    public MonthAdapter(Month month, DateSelector dateSelector, CalendarConstraints calendarConstraints, DayViewDecorator dayViewDecorator) {
        this.month = month;
        this.calendarConstraints = calendarConstraints;
        this.dayViewDecorator = dayViewDecorator;
        this.previouslySelectedDates = dateSelector.getSelectedDays();
    }

    public final int firstPositionInMonth() {
        Month month = this.month;
        int i = this.calendarConstraints.firstDayOfWeek;
        int i2 = month.firstOfMonth.get(7);
        if (i <= 0) {
            i = month.firstOfMonth.getFirstDayOfWeek();
        }
        int i3 = i2 - i;
        return i3 < 0 ? i3 + month.daysInWeek : i3;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i / this.month.daysInWeek;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0065  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View getView(int r6, android.view.View r7, android.view.ViewGroup r8) {
        /*
            r5 = this;
            android.content.Context r0 = r8.getContext()
            com.google.android.material.datepicker.CalendarStyle r1 = r5.calendarStyle
            if (r1 != 0) goto Lf
            com.google.android.material.datepicker.CalendarStyle r1 = new com.google.android.material.datepicker.CalendarStyle
            r1.<init>(r0)
            r5.calendarStyle = r1
        Lf:
            r0 = r7
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r7 != 0) goto L1f
            r7 = 2131559012(0x7f0d0264, float:1.8743356E38)
            android.view.View r7 = com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(r8, r7, r8, r1)
            r0 = r7
            android.widget.TextView r0 = (android.widget.TextView) r0
        L1f:
            int r7 = r5.firstPositionInMonth()
            int r7 = r6 - r7
            if (r7 < 0) goto L55
            com.google.android.material.datepicker.Month r8 = r5.month
            int r2 = r8.daysInMonth
            if (r7 < r2) goto L2e
            goto L55
        L2e:
            r2 = 1
            int r7 = r7 + r2
            r0.setTag(r8)
            android.content.res.Resources r8 = r0.getResources()
            android.content.res.Configuration r8 = r8.getConfiguration()
            java.util.Locale r8 = r8.locale
            java.lang.Integer r3 = java.lang.Integer.valueOf(r7)
            java.lang.Object[] r3 = new java.lang.Object[]{r3}
            java.lang.String r4 = "%d"
            java.lang.String r8 = java.lang.String.format(r8, r4, r3)
            r0.setText(r8)
            r0.setVisibility(r1)
            r0.setEnabled(r2)
            goto L5e
        L55:
            r7 = 8
            r0.setVisibility(r7)
            r0.setEnabled(r1)
            r7 = -1
        L5e:
            java.lang.Long r6 = r5.getItem(r6)
            if (r6 != 0) goto L65
            return r0
        L65:
            long r1 = r6.longValue()
            r5.updateSelectedState(r0, r1, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.MonthAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return true;
    }

    public boolean isEndOfRange(long j) {
        throw null;
    }

    public boolean isStartOfRange(long j) {
        throw null;
    }

    public final int lastPositionInMonth() {
        return (firstPositionInMonth() + this.month.daysInMonth) - 1;
    }

    public final void updateSelectedState(TextView textView, long j, int i) {
        if (textView == null) {
            return;
        }
        Context context = textView.getContext();
        boolean z = UtcDates.getTodayCalendar().getTimeInMillis() == j;
        boolean isStartOfRange = isStartOfRange(j);
        boolean isEndOfRange = isEndOfRange(j);
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf.setTimeInMillis(j);
        String format = todayCalendar.get(1) == utcCalendarOf.get(1) ? UtcDates.getAndroidFormat("MMMMEEEEd", Locale.getDefault()).format(new Date(j)) : UtcDates.getAndroidFormat("yMMMMEEEEd", Locale.getDefault()).format(new Date(j));
        if (z) {
            format = String.format(context.getString(R.string.mtrl_picker_today_description), format);
        }
        if (isStartOfRange) {
            format = String.format(context.getString(R.string.mtrl_picker_start_date_description), format);
        } else if (isEndOfRange) {
            format = String.format(context.getString(R.string.mtrl_picker_end_date_description), format);
        }
        textView.setContentDescription(format);
        if (j >= ((DateValidatorPointForward) this.calendarConstraints.validator).point) {
            textView.setEnabled(true);
            throw null;
        }
        textView.setEnabled(false);
        CalendarItemStyle calendarItemStyle = this.calendarStyle.invalidDay;
        DayViewDecorator dayViewDecorator = this.dayViewDecorator;
        if (dayViewDecorator == null || i == -1) {
            calendarItemStyle.styleItem(textView);
            return;
        }
        int i2 = this.month.year;
        dayViewDecorator.getClass();
        calendarItemStyle.styleItem(textView);
        this.dayViewDecorator.getClass();
        this.dayViewDecorator.getClass();
        this.dayViewDecorator.getClass();
        this.dayViewDecorator.getClass();
        textView.setCompoundDrawables(null, null, null, null);
        this.dayViewDecorator.getClass();
        textView.setContentDescription(format);
    }

    @Override // android.widget.Adapter
    public final Long getItem(int i) {
        if (i < firstPositionInMonth() || i > lastPositionInMonth()) {
            return null;
        }
        Month month = this.month;
        int firstPositionInMonth = (i - firstPositionInMonth()) + 1;
        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
        dayCopy.set(5, firstPositionInMonth);
        return Long.valueOf(dayCopy.getTimeInMillis());
    }
}
