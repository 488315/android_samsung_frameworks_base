package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MaterialCalendar<S> extends PickerFragment {
    public CalendarConstraints calendarConstraints;
    public CalendarSelector calendarSelector;
    public CalendarStyle calendarStyle;
    public Month current;
    public View dayFrame;
    public RecyclerView recyclerView;
    public int themeResId;
    public View yearFrame;
    public RecyclerView yearSelector;
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.datepicker.MaterialCalendar$3 */
    public final class C42653 implements OnDayClickListener {
        public C42653() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum CalendarSelector {
        DAY,
        YEAR
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnDayClickListener {
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = this.mArguments;
        }
        this.themeResId = bundle.getInt("THEME_RES_ID_KEY");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(bundle.getParcelable("GRID_SELECTOR_KEY"));
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.current = (Month) bundle.getParcelable("CURRENT_MONTH_KEY");
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        final int i2;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(contextThemeWrapper);
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(contextThemeWrapper);
        Month month = this.calendarConstraints.start;
        if (MaterialDatePicker.isFullscreen(contextThemeWrapper)) {
            i = R.layout.mtrl_calendar_vertical;
            i2 = 1;
        } else {
            i = R.layout.mtrl_calendar_horizontal;
            i2 = 0;
        }
        View inflate = cloneInContext.inflate(i, viewGroup, false);
        Resources resources = requireContext().getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height);
        int i3 = MonthAdapter.MAXIMUM_WEEKS;
        inflate.setMinimumHeight(dimensionPixelOffset + dimensionPixelSize + (resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding) * (i3 - 1)) + (resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) * i3) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding));
        GridView gridView = (GridView) inflate.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(gridView, new AccessibilityDelegateCompat(this) { // from class: com.google.android.material.datepicker.MaterialCalendar.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
        int i4 = this.calendarConstraints.firstDayOfWeek;
        gridView.setAdapter((ListAdapter) (i4 > 0 ? new DaysOfWeekAdapter(i4) : new DaysOfWeekAdapter()));
        gridView.setNumColumns(month.daysInWeek);
        gridView.setEnabled(false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.mtrl_calendar_months);
        this.recyclerView.setLayoutManager(new SmoothCalendarLayoutManager(getContext(), i2, false) { // from class: com.google.android.material.datepicker.MaterialCalendar.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public final void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
                int i5 = i2;
                MaterialCalendar materialCalendar = MaterialCalendar.this;
                if (i5 == 0) {
                    iArr[0] = materialCalendar.recyclerView.getWidth();
                    iArr[1] = materialCalendar.recyclerView.getWidth();
                } else {
                    iArr[0] = materialCalendar.recyclerView.getHeight();
                    iArr[1] = materialCalendar.recyclerView.getHeight();
                }
            }
        });
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        final MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(contextThemeWrapper, null, this.calendarConstraints, new C42653());
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int integer = contextThemeWrapper.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView;
        if (recyclerView != null) {
            recyclerView.mHasFixedSize = true;
            recyclerView.setLayoutManager(new GridLayoutManager((Context) contextThemeWrapper, integer, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.google.android.material.datepicker.MaterialCalendar.4
                {
                    UtcDates.getUtcCalendarOf(null);
                    UtcDates.getUtcCalendarOf(null);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public final void onDraw(Canvas canvas, RecyclerView recyclerView2) {
                    if ((recyclerView2.mAdapter instanceof YearGridAdapter) && (recyclerView2.getLayoutManager$1() instanceof GridLayoutManager)) {
                        Object obj = MaterialCalendar.MONTHS_VIEW_GROUP_TAG;
                        MaterialCalendar.this.getClass();
                        throw null;
                    }
                }
            });
        }
        if (inflate.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            final MaterialButton materialButton = (MaterialButton) inflate.findViewById(R.id.month_navigation_fragment_toggle);
            materialButton.setTag(SELECTOR_TOGGLE_TAG);
            ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.5
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
                    AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
                    accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    accessibilityNodeInfo.setHintText(materialCalendar.dayFrame.getVisibility() == 0 ? materialCalendar.getResources().getString(R.string.mtrl_picker_toggle_to_year_selection) : materialCalendar.getResources().getString(R.string.mtrl_picker_toggle_to_day_selection));
                }
            });
            MaterialButton materialButton2 = (MaterialButton) inflate.findViewById(R.id.month_navigation_previous);
            materialButton2.setTag(NAVIGATION_PREV_TAG);
            MaterialButton materialButton3 = (MaterialButton) inflate.findViewById(R.id.month_navigation_next);
            materialButton3.setTag(NAVIGATION_NEXT_TAG);
            this.yearFrame = inflate.findViewById(R.id.mtrl_calendar_year_selector_frame);
            this.dayFrame = inflate.findViewById(R.id.mtrl_calendar_day_selector_frame);
            setSelector(CalendarSelector.DAY);
            materialButton.setText(this.current.getLongName());
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.6
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrollStateChanged(RecyclerView recyclerView2, int i5) {
                    if (i5 == 0) {
                        recyclerView2.announceForAccessibility(materialButton.getText());
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrolled(RecyclerView recyclerView2, int i5, int i6) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    int findFirstVisibleItemPosition = i5 < 0 ? ((LinearLayoutManager) materialCalendar.recyclerView.getLayoutManager$1()).findFirstVisibleItemPosition() : ((LinearLayoutManager) materialCalendar.recyclerView.getLayoutManager$1()).findLastVisibleItemPosition();
                    MonthsPagerAdapter monthsPagerAdapter2 = monthsPagerAdapter;
                    materialCalendar.current = monthsPagerAdapter2.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition);
                    materialButton.setText(monthsPagerAdapter2.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition).getLongName());
                }
            });
            materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    CalendarSelector calendarSelector = materialCalendar.calendarSelector;
                    CalendarSelector calendarSelector2 = CalendarSelector.YEAR;
                    if (calendarSelector == calendarSelector2) {
                        materialCalendar.setSelector(CalendarSelector.DAY);
                    } else if (calendarSelector == CalendarSelector.DAY) {
                        materialCalendar.setSelector(calendarSelector2);
                    }
                }
            });
            materialButton3.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int findFirstVisibleItemPosition = ((LinearLayoutManager) MaterialCalendar.this.recyclerView.getLayoutManager$1()).findFirstVisibleItemPosition() + 1;
                    if (findFirstVisibleItemPosition < MaterialCalendar.this.recyclerView.mAdapter.getItemCount()) {
                        MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition));
                    }
                }
            });
            materialButton2.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int findLastVisibleItemPosition = ((LinearLayoutManager) MaterialCalendar.this.recyclerView.getLayoutManager$1()).findLastVisibleItemPosition() - 1;
                    if (findLastVisibleItemPosition >= 0) {
                        MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.calendarConstraints.start.monthsLater(findLastVisibleItemPosition));
                    }
                }
            });
        }
        if (!MaterialDatePicker.isFullscreen(contextThemeWrapper)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        Month month2 = this.current;
        Month month3 = monthsPagerAdapter.calendarConstraints.start;
        if (!(month3.firstOfMonth instanceof GregorianCalendar)) {
            throw new IllegalArgumentException("Only Gregorian calendars are supported.");
        }
        recyclerView2.scrollToPosition((month2.month - month3.month) + ((month2.year - month3.year) * 12));
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.current);
    }

    public final void setCurrentMonth(Month month) {
        Month month2 = ((MonthsPagerAdapter) this.recyclerView.mAdapter).calendarConstraints.start;
        Calendar calendar = month2.firstOfMonth;
        if (!(calendar instanceof GregorianCalendar)) {
            throw new IllegalArgumentException("Only Gregorian calendars are supported.");
        }
        int i = month.year;
        int i2 = month2.year;
        int i3 = month.month;
        int i4 = month2.month;
        final int i5 = (i3 - i4) + ((i - i2) * 12);
        Month month3 = this.current;
        if (!(calendar instanceof GregorianCalendar)) {
            throw new IllegalArgumentException("Only Gregorian calendars are supported.");
        }
        int i6 = i5 - ((month3.month - i4) + ((month3.year - i2) * 12));
        boolean z = Math.abs(i6) > 3;
        boolean z2 = i6 > 0;
        this.current = month;
        if (z && z2) {
            this.recyclerView.scrollToPosition(i5 - 3);
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(i5);
                }
            });
        } else if (!z) {
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(i5);
                }
            });
        } else {
            this.recyclerView.scrollToPosition(i5 + 3);
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(i5);
                }
            });
        }
    }

    public final void setSelector(CalendarSelector calendarSelector) {
        this.calendarSelector = calendarSelector;
        if (calendarSelector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager$1().scrollToPosition(this.current.year - ((YearGridAdapter) this.yearSelector.mAdapter).materialCalendar.calendarConstraints.start.year);
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
            return;
        }
        if (calendarSelector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }
}
