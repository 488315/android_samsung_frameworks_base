package android.widget;

import java.util.Calendar;

/* loaded from: classes5.dex */
interface DatePickerController {
    Calendar getSelectedDay();

    void onYearSelected(int i);

    void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener);

    void tryVibrate();
}
