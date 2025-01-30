package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class YearGridAdapter extends RecyclerView.Adapter {
    public final MaterialCalendar materialCalendar;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public YearGridAdapter(MaterialCalendar<?> materialCalendar) {
        this.materialCalendar = materialCalendar;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.materialCalendar.calendarConstraints.yearSpan;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MaterialCalendar materialCalendar = this.materialCalendar;
        int i2 = materialCalendar.calendarConstraints.start.year + i;
        TextView textView = ((ViewHolder) viewHolder).textView;
        String string = textView.getContext().getString(R.string.mtrl_picker_navigate_to_year_description);
        textView.setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(i2)));
        textView.setContentDescription(String.format(string, Integer.valueOf(i2)));
        CalendarStyle calendarStyle = materialCalendar.calendarStyle;
        if (UtcDates.getTodayCalendar().get(1) == i2) {
            CalendarItemStyle calendarItemStyle = calendarStyle.todayYear;
        } else {
            CalendarItemStyle calendarItemStyle2 = calendarStyle.year;
        }
        throw null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new ViewHolder((TextView) LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.mtrl_calendar_year, (ViewGroup) recyclerView, false));
    }
}
