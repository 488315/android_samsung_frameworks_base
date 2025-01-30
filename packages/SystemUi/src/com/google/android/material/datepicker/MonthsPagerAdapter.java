package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MonthsPagerAdapter extends RecyclerView.Adapter {
    public final CalendarConstraints calendarConstraints;
    public final int itemHeight;
    public final MaterialCalendar.OnDayClickListener onDayClickListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final MaterialCalendarGridView monthGrid;
        public final TextView monthTitle;

        public ViewHolder(LinearLayout linearLayout, boolean z) {
            super(linearLayout);
            TextView textView = (TextView) linearLayout.findViewById(R.id.month_title);
            this.monthTitle = textView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            new ViewCompat.C01584(R.id.tag_accessibility_heading, Boolean.class, 28).set(textView, Boolean.TRUE);
            this.monthGrid = (MaterialCalendarGridView) linearLayout.findViewById(R.id.month_grid);
            if (z) {
                return;
            }
            textView.setVisibility(8);
        }
    }

    public MonthsPagerAdapter(Context context, DateSelector dateSelector, CalendarConstraints calendarConstraints, MaterialCalendar.OnDayClickListener onDayClickListener) {
        Month month = calendarConstraints.start;
        Month month2 = calendarConstraints.end;
        Month month3 = calendarConstraints.openAt;
        if (month.firstOfMonth.compareTo(month3.firstOfMonth) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        }
        if (month3.firstOfMonth.compareTo(month2.firstOfMonth) > 0) {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        int i = MonthAdapter.MAXIMUM_WEEKS;
        Object obj = MaterialCalendar.MONTHS_VIEW_GROUP_TAG;
        this.itemHeight = (context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) * i) + (MaterialDatePicker.isFullscreen(context) ? context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) : 0);
        this.calendarConstraints = calendarConstraints;
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
            MonthAdapter monthAdapter = new MonthAdapter(monthsLater, null, calendarConstraints);
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
                    adapter.updateSelectedState((TextView) materialCalendarGridView.getChildAt((materialCalendarGridView.getAdapter().firstPositionInMonth() + (dayCopy.get(5) - 1)) - materialCalendarGridView.getFirstVisiblePosition()), longValue);
                }
            }
        }
        materialCalendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.google.android.material.datepicker.MonthsPagerAdapter.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                MonthAdapter adapter2 = materialCalendarGridView.getAdapter();
                if (i2 >= adapter2.firstPositionInMonth() && i2 <= (adapter2.firstPositionInMonth() + adapter2.month.daysInMonth) + (-1)) {
                    if (materialCalendarGridView.getAdapter().getItem(i2).longValue() >= ((DateValidatorPointForward) MaterialCalendar.this.calendarConstraints.validator).point) {
                        throw null;
                    }
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.mtrl_calendar_month_labeled, (ViewGroup) recyclerView, false);
        if (!MaterialDatePicker.isFullscreen(recyclerView.getContext())) {
            return new ViewHolder(linearLayout, false);
        }
        linearLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
        return new ViewHolder(linearLayout, true);
    }
}
