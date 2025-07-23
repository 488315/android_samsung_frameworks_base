package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.MathUtils;
import android.util.StateSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.flags.Flags;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes5.dex */
class SimpleMonthView extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String MONTH_YEAR_FORMAT = "MMMMy";
    private static final int SELECTED_HIGHLIGHT_ALPHA = 176;
    private int mActivatedDay;
    private final Calendar mCalendar;
    private int mCellWidth;
    private final NumberFormat mDayFormatter;
    private int mDayHeight;
    private final Paint mDayHighlightPaint;
    private final Paint mDayHighlightSelectorPaint;
    private int mDayOfWeekHeight;
    private final String[] mDayOfWeekLabels;
    private final TextPaint mDayOfWeekPaint;
    private int mDayOfWeekStart;
    private final TextPaint mDayPaint;
    private final Paint mDaySelectorPaint;
    private int mDaySelectorRadius;
    private ColorStateList mDayTextColor;
    private int mDaysInMonth;
    private final int mDesiredCellWidth;
    private final int mDesiredDayHeight;
    private final int mDesiredDayOfWeekHeight;
    private final int mDesiredDaySelectorRadius;
    private final int mDesiredMonthHeight;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mHighlightedDay;
    private boolean mIsTouchHighlighted;
    private final Locale mLocale;
    private int mMonth;
    private int mMonthHeight;
    private final TextPaint mMonthPaint;
    private String mMonthYearLabel;
    private OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private int mPreviouslyHighlightedDay;
    private int mToday;
    private final MonthViewTouchHelper mTouchHelper;
    private int mWeekStart;
    private int mYear;

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar);
    }

    private static boolean isValidDayOfWeek(int i) {
        return i >= 1 && i <= 7;
    }

    private static boolean isValidMonth(int i) {
        return i >= 0 && i <= 11;
    }

    public SimpleMonthView(Context context) {
        this(context, null);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMonthPaint = new TextPaint();
        this.mDayOfWeekPaint = new TextPaint();
        this.mDayPaint = new TextPaint();
        this.mDaySelectorPaint = new Paint();
        this.mDayHighlightPaint = new Paint();
        this.mDayHighlightSelectorPaint = new Paint();
        this.mDayOfWeekLabels = new String[7];
        this.mActivatedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mHighlightedDay = -1;
        this.mPreviouslyHighlightedDay = -1;
        this.mIsTouchHighlighted = false;
        Resources resources = context.getResources();
        this.mDesiredMonthHeight = resources.getDimensionPixelSize(R.dimen.date_picker_month_height);
        this.mDesiredDayOfWeekHeight = resources.getDimensionPixelSize(R.dimen.date_picker_day_of_week_height);
        this.mDesiredDayHeight = resources.getDimensionPixelSize(R.dimen.date_picker_day_height);
        this.mDesiredCellWidth = resources.getDimensionPixelSize(R.dimen.date_picker_day_width);
        this.mDesiredDaySelectorRadius = resources.getDimensionPixelSize(R.dimen.date_picker_day_selector_radius);
        MonthViewTouchHelper monthViewTouchHelper = new MonthViewTouchHelper(this);
        this.mTouchHelper = monthViewTouchHelper;
        setAccessibilityDelegate(monthViewTouchHelper);
        setImportantForAccessibility(1);
        Locale locale = resources.getConfiguration().locale;
        this.mLocale = locale;
        this.mCalendar = Calendar.getInstance(locale);
        this.mDayFormatter = NumberFormat.getIntegerInstance(locale);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        initPaints(resources);
    }

    private void updateMonthYearLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern(this.mLocale, MONTH_YEAR_FORMAT), this.mLocale);
        simpleDateFormat.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        this.mMonthYearLabel = simpleDateFormat.format(this.mCalendar.getTime());
    }

    private void updateDayOfWeekLabels() {
        String[] weekdays = DateFormatSymbols.getInstance(this.mLocale).getWeekdays(0, 2);
        for (int i = 0; i < 7; i++) {
            this.mDayOfWeekLabels[i] = weekdays[(((this.mWeekStart + i) - 1) % 7) + 1];
        }
    }

    private ColorStateList applyTextAppearance(Paint paint, int i) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.TextAppearance, 0, i);
        String string = obtainStyledAttributes.getString(12);
        if (string != null) {
            paint.setTypeface(Typeface.create(string, 0));
        }
        paint.setTextSize(obtainStyledAttributes.getDimensionPixelSize(0, (int) paint.getTextSize()));
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        if (colorStateList != null) {
            paint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        }
        obtainStyledAttributes.recycle();
        return colorStateList;
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setMonthTextAppearance(int i) {
        applyTextAppearance(this.mMonthPaint, i);
        invalidate();
    }

    public void setDayOfWeekTextAppearance(int i) {
        applyTextAppearance(this.mDayOfWeekPaint, i);
        invalidate();
    }

    public void setDayTextAppearance(int i) {
        ColorStateList applyTextAppearance = applyTextAppearance(this.mDayPaint, i);
        if (applyTextAppearance != null) {
            this.mDayTextColor = applyTextAppearance;
        }
        invalidate();
    }

    private void initPaints(Resources resources) {
        String string = resources.getString(R.string.date_picker_month_typeface);
        String string2 = resources.getString(R.string.date_picker_day_of_week_typeface);
        String string3 = resources.getString(R.string.date_picker_day_typeface);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.date_picker_month_text_size);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.date_picker_day_of_week_text_size);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.date_picker_day_text_size);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize(dimensionPixelSize);
        this.mMonthPaint.setTypeface(Typeface.create(string, 0));
        this.mMonthPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthPaint.setStyle(Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize(dimensionPixelSize2);
        this.mDayOfWeekPaint.setTypeface(Typeface.create(string2, 0));
        this.mDayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightSelectorPaint.setAntiAlias(true);
        this.mDayHighlightSelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize(dimensionPixelSize3);
        this.mDayPaint.setTypeface(Typeface.create(string3, 0));
        this.mDayPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPaint.setStyle(Paint.Style.FILL);
    }

    void setMonthTextColor(ColorStateList colorStateList) {
        this.mMonthPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayOfWeekTextColor(ColorStateList colorStateList) {
        this.mDayOfWeekPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayTextColor(ColorStateList colorStateList) {
        this.mDayTextColor = colorStateList;
        invalidate();
    }

    void setDaySelectorColor(ColorStateList colorStateList) {
        int colorForState = colorStateList.getColorForState(StateSet.get(40), 0);
        this.mDaySelectorPaint.setColor(colorForState);
        this.mDayHighlightSelectorPaint.setColor(colorForState);
        this.mDayHighlightSelectorPaint.setAlpha(176);
        invalidate();
    }

    void setDayHighlightColor(ColorStateList colorStateList) {
        this.mDayHighlightPaint.setColor(colorStateList.getColorForState(StateSet.get(24), 0));
        invalidate();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
    
        if (r6 != 3) goto L18;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            float r0 = r6.getX()
            r1 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r1
            int r0 = (int) r0
            float r2 = r6.getY()
            float r2 = r2 + r1
            int r1 = (int) r2
            int r6 = r6.getAction()
            r2 = 0
            r3 = 1
            if (r6 == 0) goto L2f
            if (r6 == r3) goto L1f
            r4 = 2
            if (r6 == r4) goto L2f
            r0 = 3
            if (r6 == r0) goto L26
            goto L45
        L1f:
            int r6 = r5.getDayAtLocation(r0, r1)
            r5.onDayClicked(r6)
        L26:
            r6 = -1
            r5.mHighlightedDay = r6
            r5.mIsTouchHighlighted = r2
            r5.invalidate()
            goto L45
        L2f:
            int r0 = r5.getDayAtLocation(r0, r1)
            r5.mIsTouchHighlighted = r3
            int r1 = r5.mHighlightedDay
            if (r1 == r0) goto L40
            r5.mHighlightedDay = r0
            r5.mPreviouslyHighlightedDay = r0
            r5.invalidate()
        L40:
            if (r6 != 0) goto L45
            if (r0 >= 0) goto L45
            return r2
        L45:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SimpleMonthView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2;
        int keyCode = keyEvent.getKeyCode();
        boolean z = false;
        if (keyCode != 61) {
            if (keyCode != 66 && keyCode != 160) {
                switch (keyCode) {
                    case 19:
                        if (keyEvent.hasNoModifiers()) {
                            ensureFocusedDay();
                            int i3 = this.mHighlightedDay;
                            if (i3 > 7) {
                                this.mHighlightedDay = i3 - 7;
                                z = true;
                                break;
                            }
                        }
                        break;
                    case 20:
                        if (keyEvent.hasNoModifiers()) {
                            ensureFocusedDay();
                            int i4 = this.mHighlightedDay;
                            if (i4 <= this.mDaysInMonth - 7) {
                                this.mHighlightedDay = i4 + 7;
                                z = true;
                                break;
                            }
                        }
                        break;
                    case 21:
                        if (keyEvent.hasNoModifiers()) {
                            z = moveOneDay(isLayoutRtl());
                            break;
                        }
                        break;
                    case 22:
                        if (keyEvent.hasNoModifiers()) {
                            z = moveOneDay(!isLayoutRtl());
                            break;
                        }
                        break;
                }
                return true;
            }
            int i5 = this.mHighlightedDay;
            if (i5 != -1) {
                onDayClicked(i5);
                return true;
            }
        } else {
            if (keyEvent.hasNoModifiers()) {
                i2 = 2;
            } else {
                i2 = keyEvent.hasModifiers(1) ? 1 : 0;
            }
            if (i2 != 0) {
                ViewParent parent = getParent();
                View view = this;
                do {
                    view = view.focusSearch(i2);
                    if (view == null || view == this) {
                        break;
                    }
                } while (view.getParent() == parent);
                if (view != null) {
                    view.requestFocus();
                    return true;
                }
            }
        }
        if (z) {
            invalidate();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private boolean moveOneDay(boolean z) {
        int i;
        int i2;
        ensureFocusedDay();
        if (z) {
            if (isLastDayOfWeek(this.mHighlightedDay) || (i2 = this.mHighlightedDay) >= this.mDaysInMonth) {
                return false;
            }
            this.mHighlightedDay = i2 + 1;
            return true;
        }
        if (isFirstDayOfWeek(this.mHighlightedDay) || (i = this.mHighlightedDay) <= 1) {
            return false;
        }
        this.mHighlightedDay = i - 1;
        return true;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            int findDayOffset = findDayOffset();
            if (i == 17) {
                this.mHighlightedDay = Math.min(this.mDaysInMonth, ((findClosestRow(rect) + 1) * 7) - findDayOffset);
            } else if (i == 33) {
                int findClosestColumn = findClosestColumn(rect);
                int i2 = this.mDaysInMonth;
                int i3 = (findClosestColumn - findDayOffset) + (((findDayOffset + i2) / 7) * 7);
                int i4 = i3 + 1;
                if (i4 > i2) {
                    i4 = i3 - 6;
                }
                this.mHighlightedDay = i4;
            } else if (i == 66) {
                int findClosestRow = findClosestRow(rect);
                this.mHighlightedDay = findClosestRow != 0 ? 1 + ((findClosestRow * 7) - findDayOffset) : 1;
            } else if (i == 130) {
                int findClosestColumn2 = findClosestColumn(rect) - findDayOffset;
                int i5 = findClosestColumn2 + 1;
                if (i5 < 1) {
                    i5 = findClosestColumn2 + 8;
                }
                this.mHighlightedDay = i5;
            }
            ensureFocusedDay();
            invalidate();
        }
        super.onFocusChanged(z, i, rect);
    }

    private int findClosestRow(Rect rect) {
        if (rect == null) {
            return 3;
        }
        if (this.mDayHeight == 0) {
            return 0;
        }
        int centerY = rect.centerY();
        TextPaint textPaint = this.mDayPaint;
        int i = this.mMonthHeight + this.mDayOfWeekHeight;
        int round = Math.round(((int) (centerY - ((i + (r3 / 2)) - ((textPaint.ascent() + textPaint.descent()) / 2.0f)))) / this.mDayHeight);
        int findDayOffset = findDayOffset() + this.mDaysInMonth;
        return MathUtils.constrain(round, 0, (findDayOffset / 7) - (findDayOffset % 7 == 0 ? 1 : 0));
    }

    private int findClosestColumn(Rect rect) {
        if (rect == null) {
            return 3;
        }
        if (this.mCellWidth == 0) {
            return 0;
        }
        int constrain = MathUtils.constrain((rect.centerX() - this.mPaddingLeft) / this.mCellWidth, 0, 6);
        return isLayoutRtl() ? 6 - constrain : constrain;
    }

    @Override // android.view.View
    public void getFocusedRect(Rect rect) {
        int i = this.mHighlightedDay;
        if (i > 0) {
            getBoundsForDay(i, rect);
        } else {
            super.getFocusedRect(rect);
        }
    }

    @Override // android.view.View
    protected void onFocusLost() {
        if (!this.mIsTouchHighlighted) {
            this.mPreviouslyHighlightedDay = this.mHighlightedDay;
            this.mHighlightedDay = -1;
            invalidate();
        }
        super.onFocusLost();
    }

    private void ensureFocusedDay() {
        if (this.mHighlightedDay != -1) {
            return;
        }
        int i = this.mPreviouslyHighlightedDay;
        if (i != -1) {
            this.mHighlightedDay = i;
            return;
        }
        int i2 = this.mActivatedDay;
        if (i2 != -1) {
            this.mHighlightedDay = i2;
        } else {
            this.mHighlightedDay = 1;
        }
    }

    private boolean isFirstDayOfWeek(int i) {
        return ((findDayOffset() + i) - 1) % 7 == 0;
    }

    private boolean isLastDayOfWeek(int i) {
        return (findDayOffset() + i) % 7 == 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        drawMonth(canvas);
        drawDaysOfWeek(canvas);
        drawDays(canvas);
        canvas.translate(-r0, -r1);
    }

    private void drawMonth(Canvas canvas) {
        canvas.drawText(this.mMonthYearLabel, this.mPaddedWidth / 2.0f, (this.mMonthHeight - (this.mMonthPaint.ascent() + this.mMonthPaint.descent())) / 2.0f, this.mMonthPaint);
    }

    public String getMonthYearLabel() {
        return this.mMonthYearLabel;
    }

    private void drawDaysOfWeek(Canvas canvas) {
        TextPaint textPaint = this.mDayOfWeekPaint;
        int i = this.mMonthHeight;
        int i2 = this.mDayOfWeekHeight;
        int i3 = this.mCellWidth;
        float ascent = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int i4 = i + (i2 / 2);
        for (int i5 = 0; i5 < 7; i5++) {
            int i6 = (i3 * i5) + (i3 / 2);
            if (isLayoutRtl()) {
                i6 = this.mPaddedWidth - i6;
            }
            canvas.drawText(this.mDayOfWeekLabels[i5], i6, i4 - ascent, textPaint);
        }
    }

    private void drawDays(Canvas canvas) {
        int i;
        int colorForState;
        Paint paint;
        TextPaint textPaint = this.mDayPaint;
        int i2 = this.mMonthHeight + this.mDayOfWeekHeight;
        int i3 = this.mDayHeight;
        int i4 = this.mCellWidth;
        float ascent = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int i5 = i2 + (i3 / 2);
        int findDayOffset = findDayOffset();
        int i6 = 1;
        while (i6 <= this.mDaysInMonth) {
            int i7 = (i4 * findDayOffset) + (i4 / 2);
            if (isLayoutRtl()) {
                i7 = this.mPaddedWidth - i7;
            }
            boolean isDayEnabled = isDayEnabled(i6);
            int i8 = isDayEnabled ? 8 : 0;
            boolean z = this.mActivatedDay == i6;
            boolean z2 = this.mHighlightedDay == i6;
            if (z) {
                i8 |= 32;
                if (z2) {
                    paint = this.mDayHighlightSelectorPaint;
                } else {
                    paint = this.mDaySelectorPaint;
                }
                canvas.drawCircle(i7, i5, this.mDaySelectorRadius, paint);
            } else if (z2) {
                i8 |= 16;
                if (isDayEnabled) {
                    canvas.drawCircle(i7, i5, this.mDaySelectorRadius, this.mDayHighlightPaint);
                }
            }
            if (this.mToday == i6 && !z) {
                colorForState = this.mDaySelectorPaint.getColor();
                i = 0;
            } else {
                i = 0;
                colorForState = this.mDayTextColor.getColorForState(StateSet.get(i8), 0);
            }
            textPaint.setColor(colorForState);
            canvas.drawText(this.mDayFormatter.format(i6), i7, i5 - ascent, textPaint);
            findDayOffset++;
            if (findDayOffset == 7) {
                i5 += i3;
                findDayOffset = i;
            }
            i6++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDayEnabled(int i) {
        return i >= this.mEnabledDayStart && i <= this.mEnabledDayEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDayOfMonth(int i) {
        return i >= 1 && i <= this.mDaysInMonth;
    }

    public void setSelectedDay(int i) {
        this.mActivatedDay = i;
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    public void setFirstDayOfWeek(int i) {
        if (isValidDayOfWeek(i)) {
            this.mWeekStart = i;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    void setMonthParams(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mActivatedDay = i;
        if (isValidMonth(i2)) {
            this.mMonth = i2;
        }
        this.mYear = i3;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (isValidDayOfWeek(i4)) {
            this.mWeekStart = i4;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        Calendar calendar = Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = getDaysInMonth(this.mMonth, this.mYear);
        int i7 = 0;
        while (true) {
            int i8 = this.mDaysInMonth;
            if (i7 < i8) {
                i7++;
                if (sameDay(i7, calendar)) {
                    this.mToday = i7;
                }
            } else {
                int constrain = MathUtils.constrain(i5, 1, i8);
                this.mEnabledDayStart = constrain;
                this.mEnabledDayEnd = MathUtils.constrain(i6, constrain, this.mDaysInMonth);
                updateMonthYearLabel();
                updateDayOfWeekLabels();
                this.mTouchHelper.invalidateRoot();
                invalidate();
                return;
            }
        }
    }

    private static int getDaysInMonth(int i, int i2) {
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

    private boolean sameDay(int i, Calendar calendar) {
        return this.mYear == calendar.get(1) && this.mMonth == calendar.get(2) && i == calendar.get(5);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize((this.mDesiredCellWidth * 7) + getPaddingStart() + getPaddingEnd(), i), resolveSize((this.mDesiredDayHeight * 6) + this.mDesiredDayOfWeekHeight + this.mDesiredMonthHeight + getPaddingTop() + getPaddingBottom(), i2));
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        requestLayout();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            int i5 = i3 - i;
            int i6 = i4 - i2;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            int i7 = (i5 - paddingRight) - paddingLeft;
            int i8 = (i6 - paddingBottom) - paddingTop;
            if (i7 == this.mPaddedWidth || i8 == this.mPaddedHeight) {
                return;
            }
            this.mPaddedWidth = i7;
            this.mPaddedHeight = i8;
            float measuredHeight = i8 / ((getMeasuredHeight() - paddingTop) - paddingBottom);
            int i9 = this.mPaddedWidth / 7;
            this.mMonthHeight = (int) (this.mDesiredMonthHeight * measuredHeight);
            this.mDayOfWeekHeight = (int) (this.mDesiredDayOfWeekHeight * measuredHeight);
            this.mDayHeight = (int) (this.mDesiredDayHeight * measuredHeight);
            this.mCellWidth = i9;
            this.mDaySelectorRadius = Math.min(this.mDesiredDaySelectorRadius, Math.min((i9 / 2) + Math.min(paddingLeft, paddingRight), (this.mDayHeight / 2) + paddingBottom));
            this.mTouchHelper.invalidateRoot();
        }
    }

    private int findDayOffset() {
        int i = this.mDayOfWeekStart;
        int i2 = this.mWeekStart;
        int i3 = i - i2;
        return i < i2 ? i3 + 7 : i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDayAtLocation(int i, int i2) {
        int i3;
        int paddingTop;
        int paddingLeft = i - getPaddingLeft();
        if (paddingLeft < 0 || paddingLeft >= this.mPaddedWidth || (paddingTop = i2 - getPaddingTop()) < (i3 = this.mMonthHeight + this.mDayOfWeekHeight) || paddingTop >= this.mPaddedHeight) {
            return -1;
        }
        if (isLayoutRtl()) {
            paddingLeft = this.mPaddedWidth - paddingLeft;
        }
        int findDayOffset = ((((paddingLeft * 7) / this.mPaddedWidth) + (((paddingTop - i3) / this.mDayHeight) * 7)) + 1) - findDayOffset();
        if (isValidDayOfMonth(findDayOffset)) {
            return findDayOffset;
        }
        return -1;
    }

    public boolean getBoundsForDay(int i, Rect rect) {
        int paddingLeft;
        if (!isValidDayOfMonth(i)) {
            return false;
        }
        int findDayOffset = (i - 1) + findDayOffset();
        int i2 = findDayOffset % 7;
        int i3 = this.mCellWidth;
        if (isLayoutRtl()) {
            paddingLeft = (getWidth() - getPaddingRight()) - ((i2 + 1) * i3);
        } else {
            paddingLeft = getPaddingLeft() + (i2 * i3);
        }
        int i4 = this.mDayHeight;
        int paddingTop = getPaddingTop() + this.mMonthHeight + this.mDayOfWeekHeight + ((findDayOffset / 7) * i4);
        rect.set(paddingLeft, paddingTop, i3 + paddingLeft, i4 + paddingTop);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDayClicked(int i) {
        if (!isValidDayOfMonth(i) || !isDayEnabled(i)) {
            return false;
        }
        if (this.mOnDayClickListener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.mYear, this.mMonth, i);
            this.mOnDayClickListener.onDayClick(this, calendar);
        }
        this.mTouchHelper.sendEventForVirtualView(i, 1);
        return true;
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (!isEnabled()) {
            return null;
        }
        if (motionEvent.isFromSource(8194) && getDayAtLocation((int) (motionEvent.getX() + 0.5f), (int) (motionEvent.getY() + 0.5f)) >= 0) {
            return PointerIcon.getSystemIcon(getContext(), Flags.enableArrowIconOnHoverWhenClickable() ? 1000 : 1002);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private class MonthViewTouchHelper extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar;
        private final Rect mTempRect;

        public MonthViewTouchHelper(View view) {
            super(view);
            this.mTempRect = new Rect();
            this.mTempCalendar = Calendar.getInstance();
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int dayAtLocation = SimpleMonthView.this.getDayAtLocation((int) (f + 0.5f), (int) (f2 + 0.5f));
            if (dayAtLocation != -1) {
                return dayAtLocation;
            }
            return Integer.MIN_VALUE;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(IntArray intArray) {
            for (int i = 1; i <= SimpleMonthView.this.mDaysInMonth; i++) {
                intArray.add(i);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getDayDescription(i));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo) {
            if (!SimpleMonthView.this.getBoundsForDay(i, this.mTempRect)) {
                this.mTempRect.setEmpty();
                accessibilityNodeInfo.setContentDescription("");
                accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
                accessibilityNodeInfo.setVisibleToUser(false);
                return;
            }
            accessibilityNodeInfo.setText(getDayText(i));
            accessibilityNodeInfo.setContentDescription(getDayDescription(i));
            if (i == SimpleMonthView.this.mToday) {
                accessibilityNodeInfo.setStateDescription(RelativeDateTimeFormatter.getInstance().format(RelativeDateTimeFormatter.Direction.THIS, RelativeDateTimeFormatter.AbsoluteUnit.DAY));
            }
            if (i == SimpleMonthView.this.mActivatedDay) {
                accessibilityNodeInfo.setSelected(true);
            }
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            boolean isDayEnabled = SimpleMonthView.this.isDayEnabled(i);
            if (isDayEnabled) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            }
            accessibilityNodeInfo.setEnabled(isDayEnabled);
            accessibilityNodeInfo.setClickable(true);
            if (i == SimpleMonthView.this.mActivatedDay) {
                accessibilityNodeInfo.setChecked(true);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            return SimpleMonthView.this.onDayClicked(i);
        }

        private CharSequence getDayDescription(int i) {
            if (SimpleMonthView.this.isValidDayOfMonth(i)) {
                this.mTempCalendar.set(SimpleMonthView.this.mYear, SimpleMonthView.this.mMonth, i);
                return DateFormat.format(DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
            }
            return "";
        }

        private CharSequence getDayText(int i) {
            if (SimpleMonthView.this.isValidDayOfMonth(i)) {
                return SimpleMonthView.this.mDayFormatter.format(i);
            }
            return null;
        }
    }
}
