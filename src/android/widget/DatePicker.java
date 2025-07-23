package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.drm.DrmInfoRequest;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes5.dex */
public class DatePicker extends FrameLayout {
    private static final String LOG_TAG = "DatePicker";
    public static final int MODE_CALENDAR = 2;
    public static final int MODE_SPINNER = 1;
    private final DatePickerDelegate mDelegate;
    private final int mMode;

    interface DatePickerDelegate {
        void autofill(AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        AutofillValue getAutofillValue();

        CalendarView getCalendarView();

        boolean getCalendarViewShown();

        int getDayOfMonth();

        int getFirstDayOfWeek();

        Calendar getMaxDate();

        Calendar getMinDate();

        int getMonth();

        boolean getSpinnersShown();

        int getYear();

        void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener);

        boolean isEnabled();

        void onConfigurationChanged(Configuration configuration);

        void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(Parcelable parcelable);

        Parcelable onSaveInstanceState(Parcelable parcelable);

        void setAutoFillChangeListener(OnDateChangedListener onDateChangedListener);

        void setCalendarViewShown(boolean z);

        void setEnabled(boolean z);

        void setFirstDayOfWeek(int i);

        void setMaxDate(long j);

        void setMinDate(long j);

        void setOnDateChangedListener(OnDateChangedListener onDateChangedListener);

        void setSpinnersShown(boolean z);

        void setValidationCallback(ValidationCallback validationCallback);

        void updateDate(int i, int i2, int i3);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatePickerMode {
    }

    public interface OnDateChangedListener {
        void onDateChanged(DatePicker datePicker, int i, int i2, int i3);
    }

    public interface ValidationCallback {
        void onValidationChanged(boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<DatePicker> {
        private int mCalendarViewShownId;
        private int mDatePickerModeId;
        private int mDayOfMonthId;
        private int mFirstDayOfWeekId;
        private int mMaxDateId;
        private int mMinDateId;
        private int mMonthId;
        private boolean mPropertiesMapped = false;
        private int mSpinnersShownId;
        private int mYearId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCalendarViewShownId = propertyMapper.mapBoolean("calendarViewShown", 16843596);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(1, "spinner");
            sparseArray.put(2, "calendar");
            this.mDatePickerModeId = propertyMapper.mapIntEnum("datePickerMode", 16843955, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mDayOfMonthId = propertyMapper.mapInt("dayOfMonth", 0);
            this.mFirstDayOfWeekId = propertyMapper.mapInt("firstDayOfWeek", 16843581);
            this.mMaxDateId = propertyMapper.mapLong("maxDate", 16843584);
            this.mMinDateId = propertyMapper.mapLong("minDate", 16843583);
            this.mMonthId = propertyMapper.mapInt(DrmInfoRequest.SEM_MONTH, 0);
            this.mSpinnersShownId = propertyMapper.mapBoolean("spinnersShown", 16843595);
            this.mYearId = propertyMapper.mapInt(DrmInfoRequest.SEM_YEAR, 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(DatePicker datePicker, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mCalendarViewShownId, datePicker.getCalendarViewShown());
            propertyReader.readIntEnum(this.mDatePickerModeId, datePicker.getMode());
            propertyReader.readInt(this.mDayOfMonthId, datePicker.getDayOfMonth());
            propertyReader.readInt(this.mFirstDayOfWeekId, datePicker.getFirstDayOfWeek());
            propertyReader.readLong(this.mMaxDateId, datePicker.getMaxDate());
            propertyReader.readLong(this.mMinDateId, datePicker.getMinDate());
            propertyReader.readInt(this.mMonthId, datePicker.getMonth());
            propertyReader.readBoolean(this.mSpinnersShownId, datePicker.getSpinnersShown());
            propertyReader.readInt(this.mYearId, datePicker.getYear());
        }
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DatePicker(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.DatePicker, attributeSet, obtainStyledAttributes, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(17, false);
        int i3 = obtainStyledAttributes.getInt(16, 1);
        int i4 = obtainStyledAttributes.getInt(3, 0);
        obtainStyledAttributes.recycle();
        if (i3 == 2 && z) {
            this.mMode = context.getResources().getInteger(R.integer.date_picker_mode);
        } else {
            this.mMode = i3;
        }
        if (this.mMode == 2) {
            this.mDelegate = createCalendarUIDelegate(context, attributeSet, i, i2);
        } else {
            this.mDelegate = createSpinnerUIDelegate(context, attributeSet, i, i2);
        }
        if (i4 != 0) {
            setFirstDayOfWeek(i4);
        }
        this.mDelegate.setAutoFillChangeListener(new OnDateChangedListener() { // from class: android.widget.DatePicker$$ExternalSyntheticLambda0
            @Override // android.widget.DatePicker.OnDateChangedListener
            public final void onDateChanged(DatePicker datePicker, int i5, int i6, int i7) {
                DatePicker.this.lambda$new$0(context, datePicker, i5, i6, i7);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Context context, DatePicker datePicker, int i, int i2, int i3) {
        AutofillManager autofillManager = (AutofillManager) context.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    private DatePickerDelegate createSpinnerUIDelegate(Context context, AttributeSet attributeSet, int i, int i2) {
        return new DatePickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    private DatePickerDelegate createCalendarUIDelegate(Context context, AttributeSet attributeSet, int i, int i2) {
        return new DatePickerCalendarDelegate(this, context, attributeSet, i, i2);
    }

    public int getMode() {
        return this.mMode;
    }

    public void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener) {
        this.mDelegate.init(i, i2, i3, onDateChangedListener);
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mDelegate.setOnDateChangedListener(onDateChangedListener);
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDelegate.updateDate(i, i2, i3);
    }

    public int getYear() {
        return this.mDelegate.getYear();
    }

    public int getMonth() {
        return this.mDelegate.getMonth();
    }

    public int getDayOfMonth() {
        return this.mDelegate.getDayOfMonth();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate().getTimeInMillis();
    }

    public void setMinDate(long j) {
        this.mDelegate.setMinDate(j);
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate().getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mDelegate.setMaxDate(j);
    }

    public void setValidationCallback(ValidationCallback validationCallback) {
        this.mDelegate.setValidationCallback(validationCallback);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mDelegate.isEnabled() == z) {
            return;
        }
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        this.mDelegate.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return DatePicker.class.getName();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("firstDayOfWeek must be between 1 and 7");
        }
        this.mDelegate.setFirstDayOfWeek(i);
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    @Deprecated
    public boolean getCalendarViewShown() {
        return this.mDelegate.getCalendarViewShown();
    }

    @Deprecated
    public CalendarView getCalendarView() {
        return this.mDelegate.getCalendarView();
    }

    @Deprecated
    public void setCalendarViewShown(boolean z) {
        this.mDelegate.setCalendarViewShown(z);
    }

    @Deprecated
    public boolean getSpinnersShown() {
        return this.mDelegate.getSpinnersShown();
    }

    @Deprecated
    public void setSpinnersShown(boolean z) {
        this.mDelegate.setSpinnersShown(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return this.mDelegate.onSaveInstanceState(super.onSaveInstanceState());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        this.mDelegate.onRestoreInstanceState(baseSavedState);
    }

    static abstract class AbstractDatePickerDelegate implements DatePickerDelegate {
        protected OnDateChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected Context mContext;
        protected Calendar mCurrentDate;
        protected Locale mCurrentLocale;
        protected DatePicker mDelegator;
        protected OnDateChangedListener mOnDateChangedListener;
        protected ValidationCallback mValidationCallback;

        protected void onLocaleChanged(Locale locale) {
        }

        public AbstractDatePickerDelegate(DatePicker datePicker, Context context) {
            this.mDelegator = datePicker;
            this.mContext = context;
            setCurrentLocale(Locale.getDefault());
        }

        protected void setCurrentLocale(Locale locale) {
            if (locale.equals(this.mCurrentLocale)) {
                return;
            }
            this.mCurrentLocale = locale;
            onLocaleChanged(locale);
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
            this.mOnDateChangedListener = onDateChangedListener;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setAutoFillChangeListener(OnDateChangedListener onDateChangedListener) {
            this.mAutoFillChangeListener = onDateChangedListener;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setValidationCallback(ValidationCallback validationCallback) {
            this.mValidationCallback = validationCallback;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                Log.w(DatePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            Calendar calendar = Calendar.getInstance(this.mCurrentLocale);
            calendar.setTimeInMillis(dateValue);
            updateDate(calendar.get(1), calendar.get(2), calendar.get(5));
            this.mAutofilledValue = dateValue;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public final AutofillValue getAutofillValue() {
            long j = this.mAutofilledValue;
            if (j == 0) {
                j = this.mCurrentDate.getTimeInMillis();
            }
            return AutofillValue.forDate(j);
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        protected void onValidationChanged(boolean z) {
            ValidationCallback validationCallback = this.mValidationCallback;
            if (validationCallback != null) {
                validationCallback.onValidationChanged(z);
            }
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.getText().add(getFormattedCurrentDate());
        }

        protected String getFormattedCurrentDate() {
            return DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 22);
        }

        static class SavedState extends View.BaseSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.DatePicker.AbstractDatePickerDelegate.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return new SavedState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            private final int mCurrentView;
            private final int mListPosition;
            private final int mListPositionOffset;
            private final long mMaxDate;
            private final long mMinDate;
            private final int mSelectedDay;
            private final int mSelectedMonth;
            private final int mSelectedYear;

            public SavedState(Parcelable parcelable, int i, int i2, int i3, long j, long j2) {
                this(parcelable, i, i2, i3, j, j2, 0, 0, 0);
            }

            public SavedState(Parcelable parcelable, int i, int i2, int i3, long j, long j2, int i4, int i5, int i6) {
                super(parcelable);
                this.mSelectedYear = i;
                this.mSelectedMonth = i2;
                this.mSelectedDay = i3;
                this.mMinDate = j;
                this.mMaxDate = j2;
                this.mCurrentView = i4;
                this.mListPosition = i5;
                this.mListPositionOffset = i6;
            }

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mSelectedYear = parcel.readInt();
                this.mSelectedMonth = parcel.readInt();
                this.mSelectedDay = parcel.readInt();
                this.mMinDate = parcel.readLong();
                this.mMaxDate = parcel.readLong();
                this.mCurrentView = parcel.readInt();
                this.mListPosition = parcel.readInt();
                this.mListPositionOffset = parcel.readInt();
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mSelectedYear);
                parcel.writeInt(this.mSelectedMonth);
                parcel.writeInt(this.mSelectedDay);
                parcel.writeLong(this.mMinDate);
                parcel.writeLong(this.mMaxDate);
                parcel.writeInt(this.mCurrentView);
                parcel.writeInt(this.mListPosition);
                parcel.writeInt(this.mListPositionOffset);
            }

            public int getSelectedDay() {
                return this.mSelectedDay;
            }

            public int getSelectedMonth() {
                return this.mSelectedMonth;
            }

            public int getSelectedYear() {
                return this.mSelectedYear;
            }

            public long getMinDate() {
                return this.mMinDate;
            }

            public long getMaxDate() {
                return this.mMaxDate;
            }

            public int getCurrentView() {
                return this.mCurrentView;
            }

            public int getListPosition() {
                return this.mListPosition;
            }

            public int getListPositionOffset() {
                return this.mListPositionOffset;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        viewStructure.setAutofillId(getAutofillId());
        onProvideAutofillStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (isEnabled()) {
            this.mDelegate.autofill(autofillValue);
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 4 : 0;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return this.mDelegate.getAutofillValue();
        }
        return null;
    }
}
