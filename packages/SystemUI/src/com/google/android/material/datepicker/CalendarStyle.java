package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;

/* loaded from: classes4.dex */
public final class CalendarStyle {
    public final CalendarItemStyle invalidDay;
    public final CalendarItemStyle todayYear;
    public final CalendarItemStyle year;

    public CalendarStyle(Context context) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveTypedValueOrThrow(context, MaterialCalendar.class.getCanonicalName(), R.attr.materialCalendarStyle).data, R$styleable.MaterialCalendar);
        CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(4, 0), context);
        this.invalidDay = CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(2, 0), context);
        CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(3, 0), context);
        CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(5, 0), context);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, 7);
        this.year = CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(9, 0), context);
        CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(8, 0), context);
        this.todayYear = CalendarItemStyle.create(typedArrayObtainStyledAttributes.getResourceId(10, 0), context);
        new Paint().setColor(colorStateList.getDefaultColor());
        typedArrayObtainStyledAttributes.recycle();
    }
}
