package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.icu.text.DateFormat;
import android.icu.util.TimeZone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MonthAdapter extends BaseAdapter {
    public final CalendarConstraints calendarConstraints;
    public CalendarStyle calendarStyle;
    public final Month month;
    public Collection previouslySelectedDates;
    public static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendarOf(null).getMaximum(4);
    public static final int MAXIMUM_GRID_CELLS = (UtcDates.getUtcCalendarOf(null).getMaximum(7) + UtcDates.getUtcCalendarOf(null).getMaximum(5)) - 1;

    public MonthAdapter(Month month, DateSelector dateSelector, CalendarConstraints calendarConstraints) {
        this.month = month;
        this.calendarConstraints = calendarConstraints;
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x00c7  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View getView(int i, View view, ViewGroup viewGroup) {
        Long item;
        Context context = viewGroup.getContext();
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_day, viewGroup, false);
        }
        int firstPositionInMonth = i - firstPositionInMonth();
        if (firstPositionInMonth >= 0) {
            Month month = this.month;
            if (firstPositionInMonth < month.daysInMonth) {
                int i2 = firstPositionInMonth + 1;
                textView.setTag(month);
                textView.setText(String.format(textView.getResources().getConfiguration().locale, "%d", Integer.valueOf(i2)));
                Calendar dayCopy = UtcDates.getDayCopy(this.month.firstOfMonth);
                dayCopy.set(5, i2);
                long timeInMillis = dayCopy.getTimeInMillis();
                if (this.month.year == Month.current().year) {
                    DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton("MMMEd", Locale.getDefault());
                    instanceForSkeleton.setTimeZone(TimeZone.getTimeZone("UTC"));
                    textView.setContentDescription(instanceForSkeleton.format(new Date(timeInMillis)));
                } else {
                    DateFormat instanceForSkeleton2 = DateFormat.getInstanceForSkeleton("yMMMEd", Locale.getDefault());
                    instanceForSkeleton2.setTimeZone(TimeZone.getTimeZone("UTC"));
                    textView.setContentDescription(instanceForSkeleton2.format(new Date(timeInMillis)));
                }
                textView.setVisibility(0);
                textView.setEnabled(true);
                item = getItem(i);
                if (item != null) {
                    updateSelectedState(textView, item.longValue());
                }
                return textView;
            }
        }
        textView.setVisibility(8);
        textView.setEnabled(false);
        item = getItem(i);
        if (item != null) {
        }
        return textView;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return true;
    }

    public final void updateSelectedState(TextView textView, long j) {
        if (textView == null) {
            return;
        }
        if (j >= ((DateValidatorPointForward) this.calendarConstraints.validator).point) {
            textView.setEnabled(true);
            throw null;
        }
        textView.setEnabled(false);
        CalendarItemStyle calendarItemStyle = this.calendarStyle.invalidDay;
        calendarItemStyle.getClass();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable();
        ShapeAppearanceModel shapeAppearanceModel = calendarItemStyle.itemShape;
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.setFillColor(calendarItemStyle.backgroundColor);
        materialShapeDrawable.drawableState.strokeWidth = calendarItemStyle.strokeWidth;
        materialShapeDrawable.invalidateSelf();
        materialShapeDrawable.setStrokeColor(calendarItemStyle.strokeColor);
        ColorStateList colorStateList = calendarItemStyle.textColor;
        textView.setTextColor(colorStateList);
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList.withAlpha(30), materialShapeDrawable, materialShapeDrawable2);
        Rect rect = calendarItemStyle.insets;
        InsetDrawable insetDrawable = new InsetDrawable((Drawable) rippleDrawable, rect.left, rect.top, rect.right, rect.bottom);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(textView, insetDrawable);
    }

    @Override // android.widget.Adapter
    public final Long getItem(int i) {
        if (i < firstPositionInMonth()) {
            return null;
        }
        int firstPositionInMonth = firstPositionInMonth();
        Month month = this.month;
        if (i > (firstPositionInMonth + month.daysInMonth) - 1) {
            return null;
        }
        int firstPositionInMonth2 = (i - firstPositionInMonth()) + 1;
        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
        dayCopy.set(5, firstPositionInMonth2);
        return Long.valueOf(dayCopy.getTimeInMillis());
    }
}
