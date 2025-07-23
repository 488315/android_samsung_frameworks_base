package android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.widget.CalendarView;
import android.widget.DayPickerView;

/* loaded from: classes5.dex */
class CalendarViewMaterialDelegate extends CalendarView.AbstractCalendarViewDelegate {
    private final DayPickerView mDayPickerView;
    private CalendarView.OnDateChangeListener mOnDateChangeListener;
    private final DayPickerView.OnDaySelectedListener mOnDaySelectedListener;

    public CalendarViewMaterialDelegate(CalendarView calendarView, Context context, AttributeSet attributeSet, int i, int i2) {
        super(calendarView, context);
        DayPickerView.OnDaySelectedListener onDaySelectedListener = new DayPickerView.OnDaySelectedListener() { // from class: android.widget.CalendarViewMaterialDelegate.1
            @Override // android.widget.DayPickerView.OnDaySelectedListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                if (CalendarViewMaterialDelegate.this.mOnDateChangeListener != null) {
                    CalendarViewMaterialDelegate.this.mOnDateChangeListener.onSelectedDayChange(CalendarViewMaterialDelegate.this.mDelegator, calendar.get(1), calendar.get(2), calendar.get(5));
                }
            }
        };
        this.mOnDaySelectedListener = onDaySelectedListener;
        DayPickerView dayPickerView = new DayPickerView(context, attributeSet, i, i2);
        this.mDayPickerView = dayPickerView;
        dayPickerView.setOnDaySelectedListener(onDaySelectedListener);
        calendarView.addView(dayPickerView);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setWeekDayTextAppearance(int i) {
        this.mDayPickerView.setDayOfWeekTextAppearance(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getWeekDayTextAppearance() {
        return this.mDayPickerView.getDayOfWeekTextAppearance();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDateTextAppearance(int i) {
        this.mDayPickerView.setDayTextAppearance(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getDateTextAppearance() {
        return this.mDayPickerView.getDayTextAppearance();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMinDate(long j) {
        this.mDayPickerView.setMinDate(j);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMinDate() {
        return this.mDayPickerView.getMinDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMaxDate(long j) {
        this.mDayPickerView.setMaxDate(j);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMaxDate() {
        return this.mDayPickerView.getMaxDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setFirstDayOfWeek(int i) {
        this.mDayPickerView.setFirstDayOfWeek(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getFirstDayOfWeek() {
        return this.mDayPickerView.getFirstDayOfWeek();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j) {
        this.mDayPickerView.setDate(j, true);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j, boolean z, boolean z2) {
        this.mDayPickerView.setDate(j, z);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getDate() {
        return this.mDayPickerView.getDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setOnDateChangeListener(CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public boolean getBoundsForDate(long j, Rect rect) {
        if (!this.mDayPickerView.getBoundsForDate(j, rect)) {
            return false;
        }
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        this.mDayPickerView.getLocationOnScreen(iArr);
        this.mDelegator.getLocationOnScreen(iArr2);
        int i = iArr[1] - iArr2[1];
        rect.top += i;
        rect.bottom += i;
        return true;
    }
}
