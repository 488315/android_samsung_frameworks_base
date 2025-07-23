package com.google.android.material.datepicker;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MaterialCalendar<S> extends PickerFragment {
    public CalendarConstraints calendarConstraints;
    public CalendarSelector calendarSelector;
    public CalendarStyle calendarStyle;
    public Month current;
    public View dayFrame;
    public DayViewDecorator dayViewDecorator;
    public View monthNext;
    public View monthPrev;
    public RecyclerView recyclerView;
    public int themeResId;
    public View yearFrame;
    public RecyclerView yearSelector;
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.datepicker.MaterialCalendar$3, reason: invalid class name */
    public class AnonymousClass3 implements OnDayClickListener {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum CalendarSelector {
        DAY,
        YEAR
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnDayClickListener {
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = this.mArguments;
        }
        this.themeResId = bundle.getInt("THEME_RES_ID_KEY");
        if (bundle.getParcelable("GRID_SELECTOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.dayViewDecorator = (DayViewDecorator) bundle.getParcelable("DAY_VIEW_DECORATOR_KEY");
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
        if (MaterialDatePicker.readMaterialCalendarStyleBoolean(R.attr.windowFullscreen, contextThemeWrapper)) {
            i = com.android.systemui.R.layout.mtrl_calendar_vertical;
            i2 = 1;
        } else {
            i = com.android.systemui.R.layout.mtrl_calendar_horizontal;
            i2 = 0;
        }
        View inflate = cloneInContext.inflate(i, viewGroup, false);
        Resources resources = requireContext().getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_calendar_navigation_bottom_padding) + resources.getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_calendar_navigation_height);
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_calendar_days_of_week_height);
        int i3 = MonthAdapter.MAXIMUM_WEEKS;
        inflate.setMinimumHeight(dimensionPixelOffset + dimensionPixelSize + (resources.getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_calendar_month_vertical_padding) * (i3 - 1)) + (resources.getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_calendar_day_height) * i3) + resources.getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_calendar_bottom_padding));
        GridView gridView = (GridView) inflate.findViewById(com.android.systemui.R.id.mtrl_calendar_days_of_week);
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
        this.recyclerView = (RecyclerView) inflate.findViewById(com.android.systemui.R.id.mtrl_calendar_months);
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
        final MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(contextThemeWrapper, null, this.calendarConstraints, this.dayViewDecorator, new AnonymousClass3());
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int integer = contextThemeWrapper.getResources().getInteger(com.android.systemui.R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(com.android.systemui.R.id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView;
        if (recyclerView != null) {
            recyclerView.mHasFixedSize = true;
            recyclerView.setLayoutManager(new GridLayoutManager((Context) contextThemeWrapper, integer, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.google.android.material.datepicker.MaterialCalendar.5
                {
                    UtcDates.getUtcCalendarOf(null);
                    UtcDates.getUtcCalendarOf(null);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public final void onDraw(Canvas canvas, RecyclerView recyclerView2, RecyclerView.State state) {
                    if ((recyclerView2.mAdapter instanceof YearGridAdapter) && (recyclerView2.getLayoutManager() instanceof GridLayoutManager)) {
                        MaterialCalendar.this.getClass();
                        throw null;
                    }
                }
            });
        }
        if (inflate.findViewById(com.android.systemui.R.id.month_navigation_fragment_toggle) != null) {
            final MaterialButton materialButton = (MaterialButton) inflate.findViewById(com.android.systemui.R.id.month_navigation_fragment_toggle);
            materialButton.setTag(SELECTOR_TOGGLE_TAG);
            ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.6
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    accessibilityNodeInfoCompat.mInfo.setHintText(materialCalendar.dayFrame.getVisibility() == 0 ? materialCalendar.getResources().getString(com.android.systemui.R.string.mtrl_picker_toggle_to_year_selection) : materialCalendar.getResources().getString(com.android.systemui.R.string.mtrl_picker_toggle_to_day_selection));
                }
            });
            View findViewById = inflate.findViewById(com.android.systemui.R.id.month_navigation_previous);
            this.monthPrev = findViewById;
            findViewById.setTag(NAVIGATION_PREV_TAG);
            View findViewById2 = inflate.findViewById(com.android.systemui.R.id.month_navigation_next);
            this.monthNext = findViewById2;
            findViewById2.setTag(NAVIGATION_NEXT_TAG);
            this.yearFrame = inflate.findViewById(com.android.systemui.R.id.mtrl_calendar_year_selector_frame);
            this.dayFrame = inflate.findViewById(com.android.systemui.R.id.mtrl_calendar_day_selector_frame);
            setSelector(CalendarSelector.DAY);
            materialButton.setText(this.current.getLongName());
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.7
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrollStateChanged(RecyclerView recyclerView2, int i5) {
                    if (i5 == 0) {
                        recyclerView2.announceForAccessibility(materialButton.getText());
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrolled(RecyclerView recyclerView2, int i5, int i6) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    int findFirstVisibleItemPosition = i5 < 0 ? ((LinearLayoutManager) materialCalendar.recyclerView.getLayoutManager()).findFirstVisibleItemPosition() : ((LinearLayoutManager) materialCalendar.recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    MonthsPagerAdapter monthsPagerAdapter2 = monthsPagerAdapter;
                    materialCalendar.current = monthsPagerAdapter2.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition);
                    materialButton.setText(monthsPagerAdapter2.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition).getLongName());
                }
            });
            materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.8
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
            this.monthNext.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int findFirstVisibleItemPosition = ((LinearLayoutManager) MaterialCalendar.this.recyclerView.getLayoutManager()).findFirstVisibleItemPosition() + 1;
                    if (findFirstVisibleItemPosition < MaterialCalendar.this.recyclerView.mAdapter.getItemCount()) {
                        MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.calendarConstraints.start.monthsLater(findFirstVisibleItemPosition));
                    }
                }
            });
            this.monthPrev.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int findLastVisibleItemPosition = ((LinearLayoutManager) MaterialCalendar.this.recyclerView.getLayoutManager()).findLastVisibleItemPosition() - 1;
                    if (findLastVisibleItemPosition >= 0) {
                        MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.calendarConstraints.start.monthsLater(findLastVisibleItemPosition));
                    }
                }
            });
        }
        if (!MaterialDatePicker.readMaterialCalendarStyleBoolean(R.attr.windowFullscreen, contextThemeWrapper)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(monthsPagerAdapter.calendarConstraints.start.monthsUntil(this.current));
        ViewCompat.setAccessibilityDelegate(this.recyclerView, new AccessibilityDelegateCompat(this) { // from class: com.google.android.material.datepicker.MaterialCalendar.4
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setScrollable(false);
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", this.dayViewDecorator);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.current);
    }

    public final void setCurrentMonth(Month month) {
        MonthsPagerAdapter monthsPagerAdapter = (MonthsPagerAdapter) this.recyclerView.mAdapter;
        final int monthsUntil = monthsPagerAdapter.calendarConstraints.start.monthsUntil(month);
        int monthsUntil2 = monthsUntil - monthsPagerAdapter.calendarConstraints.start.monthsUntil(this.current);
        boolean z = Math.abs(monthsUntil2) > 3;
        boolean z2 = monthsUntil2 > 0;
        this.current = month;
        if (z && z2) {
            this.recyclerView.scrollToPosition(monthsUntil - 3);
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.11
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                }
            });
        } else if (!z) {
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.11
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                }
            });
        } else {
            this.recyclerView.scrollToPosition(monthsUntil + 3);
            this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.11
                @Override // java.lang.Runnable
                public final void run() {
                    MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                }
            });
        }
    }

    public final void setSelector(CalendarSelector calendarSelector) {
        this.calendarSelector = calendarSelector;
        if (calendarSelector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(this.current.year - ((YearGridAdapter) this.yearSelector.mAdapter).materialCalendar.calendarConstraints.start.year);
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
            this.monthPrev.setVisibility(8);
            this.monthNext.setVisibility(8);
            return;
        }
        if (calendarSelector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            this.monthPrev.setVisibility(0);
            this.monthNext.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }
}
