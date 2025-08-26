package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.util.Calendar;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.DatePicker;
import android.widget.DayPickerView;
import android.widget.YearPickerView;
import com.android.internal.R;
import java.util.Locale;

/* loaded from: classes5.dex */
class DatePickerCalendarDelegate extends DatePicker.AbstractDatePickerDelegate {
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int UNINITIALIZED = -1;
    private static final int USE_LOCALE = 0;
    private static final int VIEW_MONTH_DAY = 0;
    private static final int VIEW_YEAR = 1;
    private ViewAnimator mAnimator;
    private ViewGroup mContainer;
    private int mCurrentView;
    private DayPickerView mDayPickerView;
    private int mFirstDayOfWeek;
    private TextView mHeaderMonthDay;
    private TextView mHeaderYear;
    private final Calendar mMaxDate;
    private final Calendar mMinDate;
    private DateFormat mMonthDayFormat;
    private final DayPickerView.OnDaySelectedListener mOnDaySelectedListener;
    private final View.OnClickListener mOnHeaderClickListener;
    private final YearPickerView.OnYearSelectedListener mOnYearSelectedListener;
    private final Calendar mTempDate;
    private DateFormat mYearFormat;
    private YearPickerView mYearPickerView;
    private static final int[] ATTRS_TEXT_COLOR = {16842904};
    private static final int[] ATTRS_DISABLED_ALPHA = {16842803};

    private int multiplyAlphaComponent(int i, float f) {
        return (16777215 & i) | (((int) ((((i >> 24) & 255) * f) + 0.5f)) << 24);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getCalendarViewShown() {
        return false;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getSpinnersShown() {
        return false;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setCalendarViewShown(boolean z) {
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setSpinnersShown(boolean z) {
    }

    public DatePickerCalendarDelegate(DatePicker datePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(datePicker, context);
        this.mCurrentView = -1;
        this.mFirstDayOfWeek = 0;
        DayPickerView.OnDaySelectedListener onDaySelectedListener = new DayPickerView.OnDaySelectedListener() { // from class: android.widget.DatePickerCalendarDelegate.1
            @Override // android.widget.DayPickerView.OnDaySelectedListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(calendar.getTimeInMillis());
                DatePickerCalendarDelegate.this.onDateChanged(true, true);
            }
        };
        this.mOnDaySelectedListener = onDaySelectedListener;
        YearPickerView.OnYearSelectedListener onYearSelectedListener = new YearPickerView.OnYearSelectedListener() { // from class: android.widget.DatePickerCalendarDelegate.2
            @Override // android.widget.YearPickerView.OnYearSelectedListener
            public void onYearChanged(YearPickerView yearPickerView, int i3) {
                int i4 = DatePickerCalendarDelegate.this.mCurrentDate.get(5);
                int daysInMonth = DatePickerCalendarDelegate.getDaysInMonth(DatePickerCalendarDelegate.this.mCurrentDate.get(2), i3);
                if (i4 > daysInMonth) {
                    DatePickerCalendarDelegate.this.mCurrentDate.set(5, daysInMonth);
                }
                DatePickerCalendarDelegate.this.mCurrentDate.set(1, i3);
                if (DatePickerCalendarDelegate.this.mCurrentDate.compareTo(DatePickerCalendarDelegate.this.mMinDate) < 0) {
                    DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(DatePickerCalendarDelegate.this.mMinDate.getTimeInMillis());
                } else if (DatePickerCalendarDelegate.this.mCurrentDate.compareTo(DatePickerCalendarDelegate.this.mMaxDate) > 0) {
                    DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(DatePickerCalendarDelegate.this.mMaxDate.getTimeInMillis());
                }
                DatePickerCalendarDelegate.this.onDateChanged(true, true);
                DatePickerCalendarDelegate.this.setCurrentView(0);
                DatePickerCalendarDelegate.this.mHeaderYear.requestFocus();
            }
        };
        this.mOnYearSelectedListener = onYearSelectedListener;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: android.widget.DatePickerCalendarDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        };
        this.mOnHeaderClickListener = onClickListener;
        Locale locale = this.mCurrentLocale;
        this.mCurrentDate = Calendar.getInstance(locale);
        this.mTempDate = Calendar.getInstance(locale);
        Calendar calendar = Calendar.getInstance(locale);
        this.mMinDate = calendar;
        Calendar calendar2 = Calendar.getInstance(locale);
        this.mMaxDate = calendar2;
        calendar.set(1900, 0, 1);
        calendar2.set(2100, 11, 31);
        this.mDelegator.getResources();
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, i, i2);
        ViewGroup viewGroup = (ViewGroup) ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(typedArrayObtainStyledAttributes.getResourceId(19, R.layout.date_picker_material), (ViewGroup) this.mDelegator, false);
        this.mContainer = viewGroup;
        viewGroup.setSaveFromParentEnabled(false);
        this.mDelegator.addView(this.mContainer);
        ViewGroup viewGroup2 = (ViewGroup) this.mContainer.findViewById(R.id.date_picker_header);
        TextView textView = (TextView) viewGroup2.findViewById(R.id.date_picker_header_year);
        this.mHeaderYear = textView;
        textView.setOnClickListener(onClickListener);
        this.mHeaderYear.setAccessibilityDelegate(new ClickActionDelegate(context, R.string.select_year));
        this.mHeaderYear.setAccessibilityLiveRegion(1);
        TextView textView2 = (TextView) viewGroup2.findViewById(R.id.date_picker_header_date);
        this.mHeaderMonthDay = textView2;
        textView2.setOnClickListener(onClickListener);
        this.mHeaderMonthDay.setAccessibilityDelegate(new ClickActionDelegate(context, R.string.select_day));
        this.mHeaderMonthDay.setAccessibilityLiveRegion(1);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(10, 0);
        ColorStateList colorStateList = null;
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            colorStateList = applyLegacyColorFixes(typedArrayObtainStyledAttributes2.getColorStateList(0));
            typedArrayObtainStyledAttributes2.recycle();
        }
        colorStateList = colorStateList == null ? typedArrayObtainStyledAttributes.getColorStateList(18) : colorStateList;
        if (colorStateList != null) {
            this.mHeaderYear.setTextColor(colorStateList);
            this.mHeaderMonthDay.setTextColor(colorStateList);
        }
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(0)) {
            viewGroup2.setBackground(typedArrayObtainStyledAttributes.getDrawable(0));
        }
        typedArrayObtainStyledAttributes.recycle();
        ViewAnimator viewAnimator = (ViewAnimator) this.mContainer.findViewById(R.id.animator);
        this.mAnimator = viewAnimator;
        DayPickerView dayPickerView = (DayPickerView) viewAnimator.findViewById(R.id.date_picker_day_picker);
        this.mDayPickerView = dayPickerView;
        dayPickerView.setFirstDayOfWeek(this.mFirstDayOfWeek);
        this.mDayPickerView.setMinDate(calendar.getTimeInMillis());
        this.mDayPickerView.setMaxDate(calendar2.getTimeInMillis());
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mDayPickerView.setOnDaySelectedListener(onDaySelectedListener);
        YearPickerView yearPickerView = (YearPickerView) this.mAnimator.findViewById(R.id.date_picker_year_picker);
        this.mYearPickerView = yearPickerView;
        yearPickerView.setRange(calendar, calendar2);
        this.mYearPickerView.setYear(this.mCurrentDate.get(1));
        this.mYearPickerView.setOnYearSelectedListener(onYearSelectedListener);
        onLocaleChanged(this.mCurrentLocale);
        setCurrentView(0);
    }

    private ColorStateList applyLegacyColorFixes(ColorStateList colorStateList) {
        int iMultiplyAlphaComponent;
        int colorForState;
        if (colorStateList == null || colorStateList.hasState(16843518)) {
            return colorStateList;
        }
        if (colorStateList.hasState(16842913)) {
            colorForState = colorStateList.getColorForState(StateSet.get(10), 0);
            iMultiplyAlphaComponent = colorStateList.getColorForState(StateSet.get(8), 0);
        } else {
            int defaultColor = colorStateList.getDefaultColor();
            TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(ATTRS_DISABLED_ALPHA);
            float f = typedArrayObtainStyledAttributes.getFloat(0, 0.3f);
            typedArrayObtainStyledAttributes.recycle();
            iMultiplyAlphaComponent = multiplyAlphaComponent(defaultColor, f);
            colorForState = defaultColor;
        }
        if (colorForState == 0 || iMultiplyAlphaComponent == 0) {
            return null;
        }
        return new ColorStateList(new int[][]{new int[]{16843518}, new int[0]}, new int[]{colorForState, iMultiplyAlphaComponent});
    }

    private static class ClickActionDelegate extends View.AccessibilityDelegate {
        private final AccessibilityNodeInfo.AccessibilityAction mClickAction;

        ClickActionDelegate(Context context, int i) {
            this.mClickAction = new AccessibilityNodeInfo.AccessibilityAction(16, context.getString(i));
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(this.mClickAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        tryVibrate();
        switch (view.getId()) {
            case R.id.date_picker_header_date /* 16909014 */:
                setCurrentView(0);
                break;
            case R.id.date_picker_header_year /* 16909015 */:
                setCurrentView(1);
                break;
        }
    }

    @Override // android.widget.DatePicker.AbstractDatePickerDelegate
    protected void onLocaleChanged(Locale locale) {
        if (this.mHeaderYear == null) {
            return;
        }
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton("EMMMd", locale);
        this.mMonthDayFormat = instanceForSkeleton;
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        this.mYearFormat = DateFormat.getInstanceForSkeleton("y", locale);
        onCurrentDateChanged();
    }

    private void onCurrentDateChanged() {
        if (this.mHeaderYear == null) {
            return;
        }
        this.mHeaderYear.lambda$setTextAsync$0(this.mYearFormat.format(this.mCurrentDate.getTime()));
        this.mHeaderMonthDay.lambda$setTextAsync$0(this.mMonthDayFormat.format(this.mCurrentDate.getTime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentView(int i) {
        if (i == 0) {
            this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
            if (this.mCurrentView != i) {
                this.mHeaderMonthDay.setActivated(true);
                this.mHeaderYear.setActivated(false);
                this.mAnimator.setDisplayedChild(0);
                this.mCurrentView = i;
                return;
            }
            return;
        }
        if (i != 1) {
            return;
        }
        this.mYearPickerView.setYear(this.mCurrentDate.get(1));
        this.mYearPickerView.post(new Runnable() { // from class: android.widget.DatePickerCalendarDelegate$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                this.f$0.lambda$setCurrentView$1();
            }
        });
        if (this.mCurrentView != i) {
            this.mHeaderMonthDay.setActivated(false);
            this.mHeaderYear.setActivated(true);
            this.mAnimator.setDisplayedChild(1);
            this.mCurrentView = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurrentView$1() throws Resources.NotFoundException {
        this.mYearPickerView.requestFocus();
        View selectedView = this.mYearPickerView.getSelectedView();
        if (selectedView != null) {
            selectedView.requestFocus();
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void init(int i, int i2, int i3, DatePicker.OnDateChangedListener onDateChangedListener) {
        setDate(i, i2, i3);
        onDateChanged(false, false);
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void updateDate(int i, int i2, int i3) {
        setDate(i, i2, i3);
        onDateChanged(false, true);
    }

    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        resetAutofilledValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDateChanged(boolean z, boolean z2) {
        int i = this.mCurrentDate.get(1);
        if (z2 && (this.mOnDateChangedListener != null || this.mAutoFillChangeListener != null)) {
            int i2 = this.mCurrentDate.get(2);
            int i3 = this.mCurrentDate.get(5);
            if (this.mOnDateChangedListener != null) {
                this.mOnDateChangedListener.onDateChanged(this.mDelegator, i, i2, i3);
            }
            if (this.mAutoFillChangeListener != null) {
                this.mAutoFillChangeListener.onDateChanged(this.mDelegator, i, i2, i3);
            }
        }
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mYearPickerView.setYear(i);
        onCurrentDateChanged();
        if (z) {
            tryVibrate();
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getYear() {
        return this.mCurrentDate.get(1);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getMonth() {
        return this.mCurrentDate.get(2);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getDayOfMonth() {
        return this.mCurrentDate.get(5);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.before(this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(j);
            onDateChanged(false, true);
        }
        this.mMinDate.setTimeInMillis(j);
        this.mDayPickerView.setMinDate(j);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public Calendar getMinDate() {
        return this.mMinDate;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.after(this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(j);
            onDateChanged(false, true);
        }
        this.mMaxDate.setTimeInMillis(j);
        this.mDayPickerView.setMaxDate(j);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public Calendar getMaxDate() {
        return this.mMaxDate;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setFirstDayOfWeek(int i) {
        this.mFirstDayOfWeek = i;
        this.mDayPickerView.setFirstDayOfWeek(i);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getFirstDayOfWeek() {
        int i = this.mFirstDayOfWeek;
        return i != 0 ? i : this.mCurrentDate.getFirstDayOfWeek();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setEnabled(boolean z) throws Resources.NotFoundException {
        this.mContainer.setEnabled(z);
        this.mDayPickerView.setEnabled(z);
        this.mYearPickerView.setEnabled(z);
        this.mHeaderYear.setEnabled(z);
        this.mHeaderMonthDay.setEnabled(z);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean isEnabled() {
        return this.mContainer.isEnabled();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public CalendarView getCalendarView() {
        throw new UnsupportedOperationException("Not supported by calendar-mode DatePicker");
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onConfigurationChanged(Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        int mostVisiblePosition;
        int firstPositionOffset;
        int i = this.mCurrentDate.get(1);
        int i2 = this.mCurrentDate.get(2);
        int i3 = this.mCurrentDate.get(5);
        int i4 = this.mCurrentView;
        if (i4 == 0) {
            mostVisiblePosition = this.mDayPickerView.getMostVisiblePosition();
            firstPositionOffset = -1;
        } else if (i4 == 1) {
            int firstVisiblePosition = this.mYearPickerView.getFirstVisiblePosition();
            firstPositionOffset = this.mYearPickerView.getFirstPositionOffset();
            mostVisiblePosition = firstVisiblePosition;
        } else {
            mostVisiblePosition = -1;
            firstPositionOffset = -1;
        }
        return new DatePicker.AbstractDatePickerDelegate.SavedState(parcelable, i, i2, i3, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mCurrentView, mostVisiblePosition, firstPositionOffset);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof DatePicker.AbstractDatePickerDelegate.SavedState) {
            DatePicker.AbstractDatePickerDelegate.SavedState savedState = (DatePicker.AbstractDatePickerDelegate.SavedState) parcelable;
            this.mCurrentDate.set(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
            this.mMinDate.setTimeInMillis(savedState.getMinDate());
            this.mMaxDate.setTimeInMillis(savedState.getMaxDate());
            onCurrentDateChanged();
            int currentView = savedState.getCurrentView();
            setCurrentView(currentView);
            int listPosition = savedState.getListPosition();
            if (listPosition != -1) {
                if (currentView == 0) {
                    this.mDayPickerView.setPosition(listPosition);
                } else if (currentView == 1) {
                    this.mYearPickerView.setSelectionFromTop(listPosition, savedState.getListPositionOffset());
                }
            }
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public CharSequence getAccessibilityClassName() {
        return DatePicker.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDaysInMonth(int i, int i2) {
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                if (i2 % 4 == 0) {
                    return (i2 % 100 != 0 || i2 % 400 == 0) ? 29 : 28;
                }
                return 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    private void tryVibrate() {
        this.mDelegator.performHapticFeedback(5);
    }
}
