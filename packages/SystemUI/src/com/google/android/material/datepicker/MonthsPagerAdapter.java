package com.google.android.material.datepicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MonthsPagerAdapter extends RecyclerView.Adapter {
    public final CalendarConstraints calendarConstraints;
    public final DayViewDecorator dayViewDecorator;
    public final int itemHeight;
    public final MaterialCalendar.OnDayClickListener onDayClickListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final MaterialCalendarGridView monthGrid;
        public final TextView monthTitle;

        public ViewHolder(LinearLayout linearLayout, boolean z) {
            super(linearLayout);
            TextView textView = (TextView) linearLayout.findViewById(R.id.month_title);
            this.monthTitle = textView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            new ViewCompat.AnonymousClass4(R.id.tag_accessibility_heading, Boolean.class, 28).set(textView, Boolean.TRUE);
            this.monthGrid = (MaterialCalendarGridView) linearLayout.findViewById(R.id.month_grid);
            if (z) {
                return;
            }
            textView.setVisibility(8);
        }
    }

    public MonthsPagerAdapter(Context context, DateSelector dateSelector, CalendarConstraints calendarConstraints, DayViewDecorator dayViewDecorator, MaterialCalendar.OnDayClickListener onDayClickListener) {
        Month month = calendarConstraints.start;
        Month month2 = calendarConstraints.end;
        Month month3 = calendarConstraints.openAt;
        if (month.firstOfMonth.compareTo(month3.firstOfMonth) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        }
        if (month3.firstOfMonth.compareTo(month2.firstOfMonth) > 0) {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        this.itemHeight = (context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) * MonthAdapter.MAXIMUM_WEEKS) + (MaterialDatePicker.readMaterialCalendarStyleBoolean(android.R.attr.windowFullscreen, context) ? context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) : 0);
        this.calendarConstraints = calendarConstraints;
        this.dayViewDecorator = dayViewDecorator;
        this.onDayClickListener = onDayClickListener;
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.calendarConstraints.monthSpan;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return this.calendarConstraints.start.monthsLater(i).firstOfMonth.getTimeInMillis();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        CalendarConstraints calendarConstraints = this.calendarConstraints;
        Month monthsLater = calendarConstraints.start.monthsLater(i);
        viewHolder2.monthTitle.setText(monthsLater.getLongName());
        final MaterialCalendarGridView materialCalendarGridView = (MaterialCalendarGridView) viewHolder2.monthGrid.findViewById(R.id.month_grid);
        if (materialCalendarGridView.getAdapter() == null || !monthsLater.equals(materialCalendarGridView.getAdapter().month)) {
            MonthAdapter monthAdapter = new MonthAdapter(monthsLater, null, calendarConstraints, this.dayViewDecorator);
            materialCalendarGridView.setNumColumns(monthsLater.daysInWeek);
            materialCalendarGridView.setAdapter((ListAdapter) monthAdapter);
        } else {
            materialCalendarGridView.invalidate();
            MonthAdapter adapter = materialCalendarGridView.getAdapter();
            Iterator it = adapter.previouslySelectedDates.iterator();
            while (it.hasNext()) {
                long longValue = ((Long) it.next()).longValue();
                if (Month.create(longValue).equals(adapter.month)) {
                    Calendar dayCopy = UtcDates.getDayCopy(adapter.month.firstOfMonth);
                    dayCopy.setTimeInMillis(longValue);
                    int i2 = dayCopy.get(5);
                    adapter.updateSelectedState((TextView) materialCalendarGridView.getChildAt((materialCalendarGridView.getAdapter().firstPositionInMonth() + (i2 - 1)) - materialCalendarGridView.getFirstVisiblePosition()), longValue, i2);
                }
            }
        }
        materialCalendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.google.android.material.datepicker.MonthsPagerAdapter.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                MonthAdapter adapter2 = materialCalendarGridView.getAdapter();
                if (i3 < adapter2.firstPositionInMonth() || i3 > adapter2.lastPositionInMonth()) {
                    return;
                }
                if (materialCalendarGridView.getAdapter().getItem(i3).longValue() >= ((DateValidatorPointForward) MaterialCalendar.this.calendarConstraints.validator).point) {
                    throw null;
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (!MaterialDatePicker.readMaterialCalendarStyleBoolean(android.R.attr.windowFullscreen, viewGroup.getContext())) {
            return new ViewHolder(linearLayout, false);
        }
        linearLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
        return new ViewHolder(linearLayout, true);
    }
}
