package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CalendarView;
import com.android.internal.R;
import java.util.Locale;

/* loaded from: classes5.dex */
class CalendarViewLegacyDelegate extends CalendarView.AbstractCalendarViewDelegate {
    private static final int ADJUSTMENT_SCROLL_DURATION = 500;
    private static final int DAYS_PER_WEEK = 7;
    private static final int DEFAULT_DATE_TEXT_SIZE = 14;
    private static final int DEFAULT_SHOWN_WEEK_COUNT = 6;
    private static final boolean DEFAULT_SHOW_WEEK_NUMBER = true;
    private static final int DEFAULT_WEEK_DAY_TEXT_APPEARANCE_RES_ID = -1;
    private static final int GOTO_SCROLL_DURATION = 1000;
    private static final long MILLIS_IN_DAY = 86400000;
    private static final long MILLIS_IN_WEEK = 604800000;
    private static final int SCROLL_CHANGE_DELAY = 40;
    private static final int SCROLL_HYST_WEEKS = 2;
    private static final int UNSCALED_BOTTOM_BUFFER = 20;
    private static final int UNSCALED_LIST_SCROLL_TOP_OFFSET = 2;
    private static final int UNSCALED_SELECTED_DATE_VERTICAL_BAR_WIDTH = 6;
    private static final int UNSCALED_WEEK_MIN_VISIBLE_HEIGHT = 12;
    private static final int UNSCALED_WEEK_SEPARATOR_LINE_WIDTH = 1;
    private WeeksAdapter mAdapter;
    private int mBottomBuffer;
    private int mCurrentMonthDisplayed;
    private int mCurrentScrollState;
    private int mDateTextAppearanceResId;
    private int mDateTextSize;
    private ViewGroup mDayNamesHeader;
    private String[] mDayNamesLong;
    private String[] mDayNamesShort;
    private int mDaysPerWeek;
    private Calendar mFirstDayOfMonth;
    private int mFirstDayOfWeek;
    private int mFocusedMonthDateColor;
    private float mFriction;
    private boolean mIsScrollingUp;
    private int mListScrollTopOffset;
    private ListView mListView;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private TextView mMonthName;
    private CalendarView.OnDateChangeListener mOnDateChangeListener;
    private long mPreviousScrollPosition;
    private int mPreviousScrollState;
    private ScrollStateRunnable mScrollStateChangedRunnable;
    private Drawable mSelectedDateVerticalBar;
    private final int mSelectedDateVerticalBarWidth;
    private int mSelectedWeekBackgroundColor;
    private boolean mShowWeekNumber;
    private int mShownWeekCount;
    private Calendar mTempDate;
    private int mUnfocusedMonthDateColor;
    private float mVelocityScale;
    private int mWeekDayTextAppearanceResId;
    private int mWeekMinVisibleHeight;
    private int mWeekNumberColor;
    private int mWeekSeparatorLineColor;
    private final int mWeekSeparatorLineWidth;

    CalendarViewLegacyDelegate(CalendarView calendarView, Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(calendarView, context);
        this.mListScrollTopOffset = 2;
        this.mWeekMinVisibleHeight = 12;
        this.mBottomBuffer = 20;
        this.mDaysPerWeek = 7;
        this.mFriction = 0.05f;
        this.mVelocityScale = 0.333f;
        this.mCurrentMonthDisplayed = -1;
        this.mIsScrollingUp = false;
        this.mPreviousScrollState = 0;
        this.mCurrentScrollState = 0;
        this.mScrollStateChangedRunnable = new ScrollStateRunnable();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarView, i, i2);
        this.mShowWeekNumber = typedArrayObtainStyledAttributes.getBoolean(1, true);
        this.mFirstDayOfWeek = typedArrayObtainStyledAttributes.getInt(0, Calendar.getInstance().getFirstDayOfWeek());
        if (!CalendarView.parseDate(typedArrayObtainStyledAttributes.getString(2), this.mMinDate)) {
            CalendarView.parseDate("01/01/1900", this.mMinDate);
        }
        if (!CalendarView.parseDate(typedArrayObtainStyledAttributes.getString(3), this.mMaxDate)) {
            CalendarView.parseDate("01/01/2100", this.mMaxDate);
        }
        if (this.mMaxDate.before(this.mMinDate)) {
            throw new IllegalArgumentException("Max date cannot be before min date.");
        }
        this.mShownWeekCount = typedArrayObtainStyledAttributes.getInt(4, 6);
        this.mSelectedWeekBackgroundColor = typedArrayObtainStyledAttributes.getColor(5, 0);
        this.mFocusedMonthDateColor = typedArrayObtainStyledAttributes.getColor(6, 0);
        this.mUnfocusedMonthDateColor = typedArrayObtainStyledAttributes.getColor(7, 0);
        this.mWeekSeparatorLineColor = typedArrayObtainStyledAttributes.getColor(9, 0);
        this.mWeekNumberColor = typedArrayObtainStyledAttributes.getColor(8, 0);
        this.mSelectedDateVerticalBar = typedArrayObtainStyledAttributes.getDrawable(10);
        this.mDateTextAppearanceResId = typedArrayObtainStyledAttributes.getResourceId(12, 16973894);
        updateDateTextSize();
        this.mWeekDayTextAppearanceResId = typedArrayObtainStyledAttributes.getResourceId(11, -1);
        typedArrayObtainStyledAttributes.recycle();
        DisplayMetrics displayMetrics = this.mDelegator.getResources().getDisplayMetrics();
        this.mWeekMinVisibleHeight = (int) TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mListScrollTopOffset = (int) TypedValue.applyDimension(1, 2.0f, displayMetrics);
        this.mBottomBuffer = (int) TypedValue.applyDimension(1, 20.0f, displayMetrics);
        this.mSelectedDateVerticalBarWidth = (int) TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mWeekSeparatorLineWidth = (int) TypedValue.applyDimension(1, 1.0f, displayMetrics);
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.calendar_view, (ViewGroup) null, false);
        this.mDelegator.addView(viewInflate);
        this.mListView = (ListView) this.mDelegator.findViewById(16908298);
        this.mDayNamesHeader = (ViewGroup) viewInflate.findViewById(R.id.day_names);
        this.mMonthName = (TextView) viewInflate.findViewById(R.id.month_name);
        setUpHeader();
        setUpListView();
        setUpAdapter();
        this.mTempDate.setTimeInMillis(System.currentTimeMillis());
        if (this.mTempDate.before(this.mMinDate)) {
            goTo(this.mMinDate, false, true, true);
        } else if (this.mMaxDate.before(this.mTempDate)) {
            goTo(this.mMaxDate, false, true, true);
        } else {
            goTo(this.mTempDate, false, true, true);
        }
        this.mDelegator.invalidate();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setShownWeekCount(int i) {
        if (this.mShownWeekCount != i) {
            this.mShownWeekCount = i;
            this.mDelegator.invalidate();
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getShownWeekCount() {
        return this.mShownWeekCount;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedWeekBackgroundColor(int i) {
        if (this.mSelectedWeekBackgroundColor != i) {
            this.mSelectedWeekBackgroundColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                WeekView weekView = (WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasSelectedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getSelectedWeekBackgroundColor() {
        return this.mSelectedWeekBackgroundColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setFocusedMonthDateColor(int i) {
        if (this.mFocusedMonthDateColor != i) {
            this.mFocusedMonthDateColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                WeekView weekView = (WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasFocusedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getFocusedMonthDateColor() {
        return this.mFocusedMonthDateColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setUnfocusedMonthDateColor(int i) {
        if (this.mUnfocusedMonthDateColor != i) {
            this.mUnfocusedMonthDateColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                WeekView weekView = (WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasUnfocusedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getUnfocusedMonthDateColor() {
        return this.mUnfocusedMonthDateColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setWeekNumberColor(int i) {
        if (this.mWeekNumberColor != i) {
            this.mWeekNumberColor = i;
            if (this.mShowWeekNumber) {
                invalidateAllWeekViews();
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getWeekNumberColor() {
        return this.mWeekNumberColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setWeekSeparatorLineColor(int i) {
        if (this.mWeekSeparatorLineColor != i) {
            this.mWeekSeparatorLineColor = i;
            invalidateAllWeekViews();
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getWeekSeparatorLineColor() {
        return this.mWeekSeparatorLineColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedDateVerticalBar(int i) {
        setSelectedDateVerticalBar(this.mDelegator.getContext().getDrawable(i));
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedDateVerticalBar(Drawable drawable) {
        if (this.mSelectedDateVerticalBar != drawable) {
            this.mSelectedDateVerticalBar = drawable;
            int childCount = this.mListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                WeekView weekView = (WeekView) this.mListView.getChildAt(i);
                if (weekView.mHasSelectedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public Drawable getSelectedDateVerticalBar() {
        return this.mSelectedDateVerticalBar;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setWeekDayTextAppearance(int i) throws Resources.NotFoundException {
        if (this.mWeekDayTextAppearanceResId != i) {
            this.mWeekDayTextAppearanceResId = i;
            setUpHeader();
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getWeekDayTextAppearance() {
        return this.mWeekDayTextAppearanceResId;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDateTextAppearance(int i) throws Resources.NotFoundException {
        if (this.mDateTextAppearanceResId != i) {
            this.mDateTextAppearanceResId = i;
            updateDateTextSize();
            invalidateAllWeekViews();
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getDateTextAppearance() {
        return this.mDateTextAppearanceResId;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mMinDate)) {
            return;
        }
        this.mMinDate.setTimeInMillis(j);
        Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.before(this.mMinDate)) {
            this.mAdapter.setSelectedDay(this.mMinDate);
        }
        this.mAdapter.init();
        if (calendar.before(this.mMinDate)) {
            setDate(this.mTempDate.getTimeInMillis());
        } else {
            goTo(calendar, false, true, false);
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mMaxDate)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(j);
        this.mAdapter.init();
        Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.after(this.mMaxDate)) {
            setDate(this.mMaxDate.getTimeInMillis());
        } else {
            goTo(calendar, false, true, false);
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setShowWeekNumber(boolean z) throws Resources.NotFoundException {
        if (this.mShowWeekNumber == z) {
            return;
        }
        this.mShowWeekNumber = z;
        this.mAdapter.notifyDataSetChanged();
        setUpHeader();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public boolean getShowWeekNumber() {
        return this.mShowWeekNumber;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setFirstDayOfWeek(int i) throws Resources.NotFoundException {
        if (this.mFirstDayOfWeek == i) {
            return;
        }
        this.mFirstDayOfWeek = i;
        this.mAdapter.init();
        this.mAdapter.notifyDataSetChanged();
        setUpHeader();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j) {
        setDate(j, false, false);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j, boolean z, boolean z2) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mAdapter.mSelectedDate)) {
            return;
        }
        goTo(this.mTempDate, z, true, z2);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getDate() {
        return this.mAdapter.mSelectedDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setOnDateChangeListener(CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public boolean getBoundsForDate(long j, Rect rect) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int count = this.mListView.getCount();
        for (int i = 0; i < count; i++) {
            WeekView weekView = (WeekView) this.mListView.getChildAt(i);
            if (weekView.getBoundsForDate(calendar, rect)) {
                int[] iArr = new int[2];
                int[] iArr2 = new int[2];
                weekView.getLocationOnScreen(iArr);
                this.mDelegator.getLocationOnScreen(iArr2);
                int i2 = iArr[1] - iArr2[1];
                rect.top += i2;
                rect.bottom += i2;
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void onConfigurationChanged(Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate
    protected void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = getCalendarForLocale(this.mTempDate, locale);
        this.mFirstDayOfMonth = getCalendarForLocale(this.mFirstDayOfMonth, locale);
        this.mMinDate = getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, locale);
    }

    private void updateDateTextSize() throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = this.mDelegator.getContext().obtainStyledAttributes(this.mDateTextAppearanceResId, R.styleable.TextAppearance);
        this.mDateTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 14);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void invalidateAllWeekViews() {
        int childCount = this.mListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mListView.getChildAt(i).invalidate();
        }
    }

    private static Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar calendar2 = Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }

    private static boolean isSameDate(Calendar calendar, Calendar calendar2) {
        return calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1);
    }

    private void setUpAdapter() {
        if (this.mAdapter == null) {
            WeeksAdapter weeksAdapter = new WeeksAdapter(this.mContext);
            this.mAdapter = weeksAdapter;
            weeksAdapter.registerDataSetObserver(new DataSetObserver() { // from class: android.widget.CalendarViewLegacyDelegate.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    if (CalendarViewLegacyDelegate.this.mOnDateChangeListener != null) {
                        Calendar selectedDay = CalendarViewLegacyDelegate.this.mAdapter.getSelectedDay();
                        CalendarViewLegacyDelegate.this.mOnDateChangeListener.onSelectedDayChange(CalendarViewLegacyDelegate.this.mDelegator, selectedDay.get(1), selectedDay.get(2), selectedDay.get(5));
                    }
                }
            });
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void setUpHeader() throws Resources.NotFoundException {
        int i = this.mDaysPerWeek;
        this.mDayNamesShort = new String[i];
        this.mDayNamesLong = new String[i];
        int i2 = this.mFirstDayOfWeek;
        int i3 = i + i2;
        while (i2 < i3) {
            int i4 = i2 > 7 ? i2 - 7 : i2;
            this.mDayNamesShort[i2 - this.mFirstDayOfWeek] = DateUtils.getDayOfWeekString(i4, 50);
            this.mDayNamesLong[i2 - this.mFirstDayOfWeek] = DateUtils.getDayOfWeekString(i4, 10);
            i2++;
        }
        TextView textView = (TextView) this.mDayNamesHeader.getChildAt(0);
        if (this.mShowWeekNumber) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        int childCount = this.mDayNamesHeader.getChildCount();
        for (int i5 = 1; i5 < childCount; i5++) {
            TextView textView2 = (TextView) this.mDayNamesHeader.getChildAt(i5);
            int i6 = this.mWeekDayTextAppearanceResId;
            if (i6 > -1) {
                textView2.setTextAppearance(i6);
            }
            if (i5 < this.mDaysPerWeek + 1) {
                int i7 = i5 - 1;
                textView2.lambda$setTextAsync$0(this.mDayNamesShort[i7]);
                textView2.setContentDescription(this.mDayNamesLong[i7]);
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
        }
        this.mDayNamesHeader.invalidate();
    }

    private void setUpListView() {
        this.mListView.setDivider(null);
        this.mListView.setItemsCanFocus(true);
        this.mListView.setVerticalScrollBarEnabled(false);
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: android.widget.CalendarViewLegacyDelegate.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i) {
                CalendarViewLegacyDelegate.this.onScrollStateChanged(absListView, i);
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                CalendarViewLegacyDelegate.this.onScroll(absListView, i, i2, i3);
            }
        });
        this.mListView.setFriction(this.mFriction);
        this.mListView.setVelocityScale(this.mVelocityScale);
    }

    private void goTo(Calendar calendar, boolean z, boolean z2, boolean z3) {
        if (calendar.before(this.mMinDate) || calendar.after(this.mMaxDate)) {
            throw new IllegalArgumentException("timeInMillis must be between the values of getMinDate() and getMaxDate()");
        }
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        View childAt = this.mListView.getChildAt(0);
        if (childAt != null && childAt.getTop() < 0) {
            firstVisiblePosition++;
        }
        int i = this.mShownWeekCount + firstVisiblePosition;
        int i2 = i - 1;
        if (childAt != null && childAt.getTop() > this.mBottomBuffer) {
            i2 = i - 2;
        }
        if (z2) {
            this.mAdapter.setSelectedDay(calendar);
        }
        int weeksSinceMinDate = getWeeksSinceMinDate(calendar);
        if (weeksSinceMinDate >= firstVisiblePosition && weeksSinceMinDate <= i2 && !z3) {
            if (z2) {
                setMonthDisplayed(calendar);
                return;
            }
            return;
        }
        this.mFirstDayOfMonth.setTimeInMillis(calendar.getTimeInMillis());
        this.mFirstDayOfMonth.set(5, 1);
        setMonthDisplayed(this.mFirstDayOfMonth);
        int weeksSinceMinDate2 = this.mFirstDayOfMonth.before(this.mMinDate) ? 0 : getWeeksSinceMinDate(this.mFirstDayOfMonth);
        this.mPreviousScrollState = 2;
        if (z) {
            this.mListView.smoothScrollToPositionFromTop(weeksSinceMinDate2, this.mListScrollTopOffset, 1000);
        } else {
            this.mListView.setSelectionFromTop(weeksSinceMinDate2, this.mListScrollTopOffset);
            onScrollStateChanged(this.mListView, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.mScrollStateChangedRunnable.doScrollStateChange(absListView, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        int monthOfLastWeekDay;
        WeekView weekView = (WeekView) absListView.getChildAt(0);
        if (weekView == null) {
            return;
        }
        long firstVisiblePosition = (absListView.getFirstVisiblePosition() * weekView.getHeight()) - weekView.getBottom();
        long j = this.mPreviousScrollPosition;
        if (firstVisiblePosition < j) {
            this.mIsScrollingUp = true;
        } else if (firstVisiblePosition <= j) {
            return;
        } else {
            this.mIsScrollingUp = false;
        }
        int i4 = weekView.getBottom() < this.mWeekMinVisibleHeight ? 1 : 0;
        if (this.mIsScrollingUp) {
            weekView = (WeekView) absListView.getChildAt(i4 + 2);
        } else if (i4 != 0) {
            weekView = (WeekView) absListView.getChildAt(i4);
        }
        if (weekView != null) {
            if (this.mIsScrollingUp) {
                monthOfLastWeekDay = weekView.getMonthOfFirstWeekDay();
            } else {
                monthOfLastWeekDay = weekView.getMonthOfLastWeekDay();
            }
            int i5 = this.mCurrentMonthDisplayed;
            int i6 = (i5 == 11 && monthOfLastWeekDay == 0) ? 1 : (i5 == 0 && monthOfLastWeekDay == 11) ? -1 : monthOfLastWeekDay - i5;
            boolean z = this.mIsScrollingUp;
            if ((!z && i6 > 0) || (z && i6 < 0)) {
                Calendar firstDay = weekView.getFirstDay();
                if (this.mIsScrollingUp) {
                    firstDay.add(5, -7);
                } else {
                    firstDay.add(5, 7);
                }
                setMonthDisplayed(firstDay);
            }
        }
        this.mPreviousScrollPosition = firstVisiblePosition;
        this.mPreviousScrollState = this.mCurrentScrollState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMonthDisplayed(Calendar calendar) {
        int i = calendar.get(2);
        this.mCurrentMonthDisplayed = i;
        this.mAdapter.setFocusMonth(i);
        long timeInMillis = calendar.getTimeInMillis();
        this.mMonthName.lambda$setTextAsync$0(DateUtils.formatDateRange(this.mContext, timeInMillis, timeInMillis, 52));
        this.mMonthName.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWeeksSinceMinDate(Calendar calendar) {
        if (calendar.before(this.mMinDate)) {
            throw new IllegalArgumentException("fromDate: " + this.mMinDate.getTime() + " does not precede toDate: " + calendar.getTime());
        }
        return (int) ((((calendar.getTimeInMillis() + calendar.getTimeZone().getOffset(calendar.getTimeInMillis())) - (this.mMinDate.getTimeInMillis() + this.mMinDate.getTimeZone().getOffset(this.mMinDate.getTimeInMillis()))) + ((this.mMinDate.get(7) - this.mFirstDayOfWeek) * 86400000)) / 604800000);
    }

    private class ScrollStateRunnable implements Runnable {
        private int mNewState;
        private AbsListView mView;

        private ScrollStateRunnable() {
        }

        public void doScrollStateChange(AbsListView absListView, int i) {
            this.mView = absListView;
            this.mNewState = i;
            CalendarViewLegacyDelegate.this.mDelegator.removeCallbacks(this);
            CalendarViewLegacyDelegate.this.mDelegator.postDelayed(this, 40L);
        }

        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            CalendarViewLegacyDelegate.this.mCurrentScrollState = this.mNewState;
            if (this.mNewState == 0 && CalendarViewLegacyDelegate.this.mPreviousScrollState != 0) {
                View childAt = this.mView.getChildAt(0);
                if (childAt == null) {
                    return;
                }
                int bottom = childAt.getBottom() - CalendarViewLegacyDelegate.this.mListScrollTopOffset;
                if (bottom > CalendarViewLegacyDelegate.this.mListScrollTopOffset) {
                    if (CalendarViewLegacyDelegate.this.mIsScrollingUp) {
                        this.mView.smoothScrollBy(bottom - childAt.getHeight(), 500);
                    } else {
                        this.mView.smoothScrollBy(bottom, 500);
                    }
                }
            }
            CalendarViewLegacyDelegate.this.mPreviousScrollState = this.mNewState;
        }
    }

    private class WeeksAdapter extends BaseAdapter implements View.OnTouchListener {
        private int mFocusedMonth;
        private GestureDetector mGestureDetector;
        private final Calendar mSelectedDate = Calendar.getInstance();
        private int mSelectedWeek;
        private int mTotalWeekCount;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public WeeksAdapter(Context context) {
            CalendarViewLegacyDelegate.this.mContext = context;
            this.mGestureDetector = new GestureDetector(CalendarViewLegacyDelegate.this.mContext, new CalendarGestureListener(this));
            init();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            this.mSelectedWeek = CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            CalendarViewLegacyDelegate calendarViewLegacyDelegate = CalendarViewLegacyDelegate.this;
            this.mTotalWeekCount = calendarViewLegacyDelegate.getWeeksSinceMinDate(calendarViewLegacyDelegate.mMaxDate);
            if (CalendarViewLegacyDelegate.this.mMinDate.get(7) != CalendarViewLegacyDelegate.this.mFirstDayOfWeek || CalendarViewLegacyDelegate.this.mMaxDate.get(7) != CalendarViewLegacyDelegate.this.mFirstDayOfWeek) {
                this.mTotalWeekCount++;
            }
            notifyDataSetChanged();
        }

        public void setSelectedDay(Calendar calendar) {
            if (calendar.get(6) == this.mSelectedDate.get(6) && calendar.get(1) == this.mSelectedDate.get(1)) {
                return;
            }
            this.mSelectedDate.setTimeInMillis(calendar.getTimeInMillis());
            this.mSelectedWeek = CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            this.mFocusedMonth = this.mSelectedDate.get(2);
            notifyDataSetChanged();
        }

        public Calendar getSelectedDay() {
            return this.mSelectedDate;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mTotalWeekCount;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            WeekView weekView;
            if (view != null) {
                weekView = (WeekView) view;
            } else {
                CalendarViewLegacyDelegate calendarViewLegacyDelegate = CalendarViewLegacyDelegate.this;
                weekView = calendarViewLegacyDelegate.new WeekView(calendarViewLegacyDelegate.mContext);
                weekView.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
                weekView.setClickable(true);
                weekView.setOnTouchListener(this);
            }
            weekView.init(i, this.mSelectedWeek == i ? this.mSelectedDate.get(7) : -1, this.mFocusedMonth);
            return weekView;
        }

        public void setFocusMonth(int i) {
            if (this.mFocusedMonth == i) {
                return;
            }
            this.mFocusedMonth = i;
            notifyDataSetChanged();
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!CalendarViewLegacyDelegate.this.mListView.isEnabled() || !this.mGestureDetector.onTouchEvent(motionEvent)) {
                return false;
            }
            if (((WeekView) view).getDayFromLocation(motionEvent.getX(), CalendarViewLegacyDelegate.this.mTempDate) && !CalendarViewLegacyDelegate.this.mTempDate.before(CalendarViewLegacyDelegate.this.mMinDate) && !CalendarViewLegacyDelegate.this.mTempDate.after(CalendarViewLegacyDelegate.this.mMaxDate)) {
                onDateTapped(CalendarViewLegacyDelegate.this.mTempDate);
            }
            return true;
        }

        private void onDateTapped(Calendar calendar) {
            setSelectedDay(calendar);
            CalendarViewLegacyDelegate.this.setMonthDisplayed(calendar);
        }

        class CalendarGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            CalendarGestureListener(WeeksAdapter weeksAdapter) {
            }
        }
    }

    private class WeekView extends View {
        private String[] mDayNumbers;
        private final Paint mDrawPaint;
        private Calendar mFirstDay;
        private boolean[] mFocusDay;
        private boolean mHasFocusedDay;
        private boolean mHasSelectedDay;
        private boolean mHasUnfocusedDay;
        private int mHeight;
        private int mLastWeekDayMonth;
        private final Paint mMonthNumDrawPaint;
        private int mMonthOfFirstWeekDay;
        private int mNumCells;
        private int mSelectedDay;
        private int mSelectedLeft;
        private int mSelectedRight;
        private final Rect mTempRect;
        private int mWeek;
        private int mWidth;

        public WeekView(Context context) {
            super(context);
            this.mTempRect = new Rect();
            this.mDrawPaint = new Paint();
            this.mMonthNumDrawPaint = new Paint();
            this.mMonthOfFirstWeekDay = -1;
            this.mLastWeekDayMonth = -1;
            this.mWeek = -1;
            this.mHasSelectedDay = false;
            this.mSelectedDay = -1;
            this.mSelectedLeft = -1;
            this.mSelectedRight = -1;
            initializePaints();
        }

        public void init(int i, int i2, int i3) {
            int i4;
            this.mSelectedDay = i2;
            this.mHasSelectedDay = i2 != -1;
            this.mNumCells = CalendarViewLegacyDelegate.this.mShowWeekNumber ? CalendarViewLegacyDelegate.this.mDaysPerWeek + 1 : CalendarViewLegacyDelegate.this.mDaysPerWeek;
            this.mWeek = i;
            CalendarViewLegacyDelegate.this.mTempDate.setTimeInMillis(CalendarViewLegacyDelegate.this.mMinDate.getTimeInMillis());
            CalendarViewLegacyDelegate.this.mTempDate.add(3, this.mWeek);
            CalendarViewLegacyDelegate.this.mTempDate.setFirstDayOfWeek(CalendarViewLegacyDelegate.this.mFirstDayOfWeek);
            int i5 = this.mNumCells;
            this.mDayNumbers = new String[i5];
            this.mFocusDay = new boolean[i5];
            if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                this.mDayNumbers[0] = String.format(Locale.getDefault(), "%d", Integer.valueOf(CalendarViewLegacyDelegate.this.mTempDate.get(3)));
                i4 = 1;
            } else {
                i4 = 0;
            }
            CalendarViewLegacyDelegate.this.mTempDate.add(5, CalendarViewLegacyDelegate.this.mFirstDayOfWeek - CalendarViewLegacyDelegate.this.mTempDate.get(7));
            this.mFirstDay = (Calendar) CalendarViewLegacyDelegate.this.mTempDate.clone();
            this.mMonthOfFirstWeekDay = CalendarViewLegacyDelegate.this.mTempDate.get(2);
            this.mHasUnfocusedDay = true;
            while (i4 < this.mNumCells) {
                boolean z = CalendarViewLegacyDelegate.this.mTempDate.get(2) == i3;
                this.mFocusDay[i4] = z;
                this.mHasFocusedDay |= z;
                this.mHasUnfocusedDay = (!z) & this.mHasUnfocusedDay;
                if (CalendarViewLegacyDelegate.this.mTempDate.before(CalendarViewLegacyDelegate.this.mMinDate) || CalendarViewLegacyDelegate.this.mTempDate.after(CalendarViewLegacyDelegate.this.mMaxDate)) {
                    this.mDayNumbers[i4] = "";
                } else {
                    this.mDayNumbers[i4] = String.format(Locale.getDefault(), "%d", Integer.valueOf(CalendarViewLegacyDelegate.this.mTempDate.get(5)));
                }
                CalendarViewLegacyDelegate.this.mTempDate.add(5, 1);
                i4++;
            }
            if (CalendarViewLegacyDelegate.this.mTempDate.get(5) == 1) {
                CalendarViewLegacyDelegate.this.mTempDate.add(5, -1);
            }
            this.mLastWeekDayMonth = CalendarViewLegacyDelegate.this.mTempDate.get(2);
            updateSelectionPositions();
        }

        private void initializePaints() {
            this.mDrawPaint.setFakeBoldText(false);
            this.mDrawPaint.setAntiAlias(true);
            this.mDrawPaint.setStyle(Paint.Style.FILL);
            this.mMonthNumDrawPaint.setFakeBoldText(true);
            this.mMonthNumDrawPaint.setAntiAlias(true);
            this.mMonthNumDrawPaint.setStyle(Paint.Style.FILL);
            this.mMonthNumDrawPaint.setTextAlign(Paint.Align.CENTER);
            this.mMonthNumDrawPaint.setTextSize(CalendarViewLegacyDelegate.this.mDateTextSize);
        }

        public int getMonthOfFirstWeekDay() {
            return this.mMonthOfFirstWeekDay;
        }

        public int getMonthOfLastWeekDay() {
            return this.mLastWeekDayMonth;
        }

        public Calendar getFirstDay() {
            return this.mFirstDay;
        }

        public boolean getDayFromLocation(float f, Calendar calendar) {
            int i;
            int i2;
            int i3;
            boolean zIsLayoutRtl = isLayoutRtl();
            if (zIsLayoutRtl) {
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    int i4 = this.mWidth;
                    i3 = i4 - (i4 / this.mNumCells);
                } else {
                    i3 = this.mWidth;
                }
                i2 = i3;
                i = 0;
            } else {
                i = CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0;
                i2 = this.mWidth;
            }
            float f2 = i;
            if (f < f2 || f > i2) {
                calendar.clear();
                return false;
            }
            int i5 = (int) (((f - f2) * CalendarViewLegacyDelegate.this.mDaysPerWeek) / (i2 - i));
            if (zIsLayoutRtl) {
                i5 = (CalendarViewLegacyDelegate.this.mDaysPerWeek - 1) - i5;
            }
            calendar.setTimeInMillis(this.mFirstDay.getTimeInMillis());
            calendar.add(5, i5);
            return true;
        }

        public boolean getBoundsForDate(Calendar calendar, Rect rect) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(this.mFirstDay.getTime());
            int i = 0;
            while (i < CalendarViewLegacyDelegate.this.mDaysPerWeek) {
                if (calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5)) {
                    int i2 = this.mWidth / this.mNumCells;
                    if (isLayoutRtl()) {
                        rect.left = (CalendarViewLegacyDelegate.this.mShowWeekNumber ? (this.mNumCells - i) - 2 : (this.mNumCells - i) - 1) * i2;
                    } else {
                        if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                            i++;
                        }
                        rect.left = i * i2;
                    }
                    rect.top = 0;
                    rect.right = rect.left + i2;
                    rect.bottom = getHeight();
                    return true;
                }
                calendar2.add(5, 1);
                i++;
            }
            return false;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            drawBackground(canvas);
            drawWeekNumbersAndDates(canvas);
            drawWeekSeparators(canvas);
            drawSelectedDateVerticalBars(canvas);
        }

        private void drawBackground(Canvas canvas) {
            int i;
            if (this.mHasSelectedDay) {
                this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mSelectedWeekBackgroundColor);
                this.mTempRect.top = CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
                this.mTempRect.bottom = this.mHeight;
                boolean zIsLayoutRtl = isLayoutRtl();
                if (zIsLayoutRtl) {
                    this.mTempRect.left = 0;
                    this.mTempRect.right = this.mSelectedLeft - 2;
                } else {
                    this.mTempRect.left = CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0;
                    this.mTempRect.right = this.mSelectedLeft - 2;
                }
                canvas.drawRect(this.mTempRect, this.mDrawPaint);
                if (zIsLayoutRtl) {
                    this.mTempRect.left = this.mSelectedRight + 3;
                    Rect rect = this.mTempRect;
                    if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                        int i2 = this.mWidth;
                        i = i2 - (i2 / this.mNumCells);
                    } else {
                        i = this.mWidth;
                    }
                    rect.right = i;
                } else {
                    this.mTempRect.left = this.mSelectedRight + 3;
                    this.mTempRect.right = this.mWidth;
                }
                canvas.drawRect(this.mTempRect, this.mDrawPaint);
            }
        }

        private void drawWeekNumbersAndDates(Canvas canvas) {
            int textSize = ((int) ((this.mHeight + this.mDrawPaint.getTextSize()) / 2.0f)) - CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
            int i = this.mNumCells;
            int i2 = i * 2;
            this.mDrawPaint.setTextAlign(Paint.Align.CENTER);
            this.mDrawPaint.setTextSize(CalendarViewLegacyDelegate.this.mDateTextSize);
            int i3 = 0;
            if (isLayoutRtl()) {
                int i4 = 0;
                while (true) {
                    int i5 = i - 1;
                    if (i4 >= i5) {
                        break;
                    }
                    this.mMonthNumDrawPaint.setColor(this.mFocusDay[i4] ? CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor);
                    canvas.drawText(this.mDayNumbers[i5 - i4], (((i4 * 2) + 1) * this.mWidth) / i2, textSize, this.mMonthNumDrawPaint);
                    i4++;
                }
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekNumberColor);
                    int i6 = this.mWidth;
                    canvas.drawText(this.mDayNumbers[0], i6 - (i6 / i2), textSize, this.mDrawPaint);
                    return;
                }
                return;
            }
            if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekNumberColor);
                canvas.drawText(this.mDayNumbers[0], this.mWidth / i2, textSize, this.mDrawPaint);
                i3 = 1;
            }
            while (i3 < i) {
                this.mMonthNumDrawPaint.setColor(this.mFocusDay[i3] ? CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor);
                canvas.drawText(this.mDayNumbers[i3], (((i3 * 2) + 1) * this.mWidth) / i2, textSize, this.mMonthNumDrawPaint);
                i3++;
            }
        }

        private void drawWeekSeparators(Canvas canvas) {
            int i;
            int firstVisiblePosition = CalendarViewLegacyDelegate.this.mListView.getFirstVisiblePosition();
            if (CalendarViewLegacyDelegate.this.mListView.getChildAt(0).getTop() < 0) {
                firstVisiblePosition++;
            }
            if (firstVisiblePosition == this.mWeek) {
                return;
            }
            this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekSeparatorLineColor);
            this.mDrawPaint.setStrokeWidth(CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth);
            if (isLayoutRtl()) {
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    int i2 = this.mWidth;
                    i = i2 - (i2 / this.mNumCells);
                } else {
                    i = this.mWidth;
                }
            } else {
                f = CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0.0f;
                i = this.mWidth;
            }
            canvas.drawLine(f, 0.0f, i, 0.0f, this.mDrawPaint);
        }

        private void drawSelectedDateVerticalBars(Canvas canvas) {
            if (this.mHasSelectedDay) {
                CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedLeft - (CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedLeft + (CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), this.mHeight);
                CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
                CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedRight - (CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedRight + (CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), this.mHeight);
                CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
            }
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            this.mWidth = i;
            updateSelectionPositions();
        }

        private void updateSelectionPositions() {
            if (this.mHasSelectedDay) {
                boolean zIsLayoutRtl = isLayoutRtl();
                int i = this.mSelectedDay - CalendarViewLegacyDelegate.this.mFirstDayOfWeek;
                if (i < 0) {
                    i += 7;
                }
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber && !zIsLayoutRtl) {
                    i++;
                }
                if (zIsLayoutRtl) {
                    this.mSelectedLeft = (((CalendarViewLegacyDelegate.this.mDaysPerWeek - 1) - i) * this.mWidth) / this.mNumCells;
                } else {
                    this.mSelectedLeft = (i * this.mWidth) / this.mNumCells;
                }
                this.mSelectedRight = this.mSelectedLeft + (this.mWidth / this.mNumCells);
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            this.mHeight = ((CalendarViewLegacyDelegate.this.mListView.getHeight() - CalendarViewLegacyDelegate.this.mListView.getPaddingTop()) - CalendarViewLegacyDelegate.this.mListView.getPaddingBottom()) / CalendarViewLegacyDelegate.this.mShownWeekCount;
            setMeasuredDimension(View.MeasureSpec.getSize(i), this.mHeight);
        }
    }
}
