package com.google.android.material.datepicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

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
        int firstDayOfWeek = this.calendarConstraints.firstDayOfWeek;
        int i = month.firstOfMonth.get(7);
        if (firstDayOfWeek <= 0) {
            firstDayOfWeek = month.firstOfMonth.getFirstDayOfWeek();
        }
        int i2 = i - firstDayOfWeek;
        return i2 < 0 ? i2 + month.daysInWeek : i2;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i / this.month.daysInWeek;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0055  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View getView(int i, View view, ViewGroup viewGroup) {
        int i2;
        Context context = viewGroup.getContext();
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.mtrl_calendar_day, viewGroup, false);
        }
        int iFirstPositionInMonth = i - firstPositionInMonth();
        if (iFirstPositionInMonth >= 0) {
            Month month = this.month;
            if (iFirstPositionInMonth >= month.daysInMonth) {
                textView.setVisibility(8);
                textView.setEnabled(false);
                i2 = -1;
            } else {
                i2 = iFirstPositionInMonth + 1;
                textView.setTag(month);
                textView.setText(String.format(textView.getResources().getConfiguration().locale, "%d", Integer.valueOf(i2)));
                textView.setVisibility(0);
                textView.setEnabled(true);
            }
        }
        Long item = getItem(i);
        if (item == null) {
            return textView;
        }
        updateSelectedState(textView, item.longValue(), i2);
        return textView;
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
        boolean zIsStartOfRange = isStartOfRange(j);
        boolean zIsEndOfRange = isEndOfRange(j);
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf.setTimeInMillis(j);
        String str = todayCalendar.get(1) == utcCalendarOf.get(1) ? UtcDates.getAndroidFormat("MMMMEEEEd", Locale.getDefault()).format(new Date(j)) : UtcDates.getAndroidFormat("yMMMMEEEEd", Locale.getDefault()).format(new Date(j));
        if (z) {
            str = String.format(context.getString(R.string.mtrl_picker_today_description), str);
        }
        if (zIsStartOfRange) {
            str = String.format(context.getString(R.string.mtrl_picker_start_date_description), str);
        } else if (zIsEndOfRange) {
            str = String.format(context.getString(R.string.mtrl_picker_end_date_description), str);
        }
        textView.setContentDescription(str);
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
        textView.setContentDescription(str);
    }

    @Override // android.widget.Adapter
    public final Long getItem(int i) {
        if (i < firstPositionInMonth() || i > lastPositionInMonth()) {
            return null;
        }
        Month month = this.month;
        int iFirstPositionInMonth = (i - firstPositionInMonth()) + 1;
        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
        dayCopy.set(5, iFirstPositionInMonth);
        return Long.valueOf(dayCopy.getTimeInMillis());
    }
}
