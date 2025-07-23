package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.drm.DrmInfoRequest;
import android.icu.text.DateFormatSymbols;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
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
public class TimePicker extends FrameLayout {
    private static final String LOG_TAG = "TimePicker";
    public static final int MODE_CLOCK = 2;
    public static final int MODE_SPINNER = 1;
    private final TimePickerDelegate mDelegate;
    private final int mMode;

    public interface OnTimeChangedListener {
        void onTimeChanged(TimePicker timePicker, int i, int i2);
    }

    interface TimePickerDelegate {
        void autofill(AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        View getAmView();

        AutofillValue getAutofillValue();

        int getBaseline();

        int getHour();

        View getHourView();

        int getMinute();

        View getMinuteView();

        View getPmView();

        boolean is24Hour();

        boolean isEnabled();

        void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(Parcelable parcelable);

        Parcelable onSaveInstanceState(Parcelable parcelable);

        void setAutoFillChangeListener(OnTimeChangedListener onTimeChangedListener);

        void setDate(int i, int i2);

        void setEnabled(boolean z);

        void setHour(int i);

        void setIs24Hour(boolean z);

        void setMinute(int i);

        void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener);

        boolean validateInput();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimePickerMode {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<TimePicker> {
        private int m24HourId;
        private int mHourId;
        private int mMinuteId;
        private boolean mPropertiesMapped = false;
        private int mTimePickerModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.m24HourId = propertyMapper.mapBoolean("24Hour", 0);
            this.mHourId = propertyMapper.mapInt(DrmInfoRequest.SEM_HOUR, 0);
            this.mMinuteId = propertyMapper.mapInt(DrmInfoRequest.SEM_MINUTE, 0);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(1, "spinner");
            sparseArray.put(2, "clock");
            this.mTimePickerModeId = propertyMapper.mapIntEnum("timePickerMode", 16843956, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(TimePicker timePicker, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.m24HourId, timePicker.is24HourView());
            propertyReader.readInt(this.mHourId, timePicker.getHour());
            propertyReader.readInt(this.mMinuteId, timePicker.getMinute());
            propertyReader.readIntEnum(this.mTimePickerModeId, timePicker.getMode());
        }
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TimePicker(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TimePicker, attributeSet, obtainStyledAttributes, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(10, false);
        int i3 = obtainStyledAttributes.getInt(8, 1);
        obtainStyledAttributes.recycle();
        if (i3 == 2 && z) {
            this.mMode = context.getResources().getInteger(R.integer.time_picker_mode);
        } else {
            this.mMode = i3;
        }
        if (this.mMode == 2) {
            this.mDelegate = new TimePickerClockDelegate(this, context, attributeSet, i, i2);
        } else {
            this.mDelegate = new TimePickerSpinnerDelegate(this, context, attributeSet, i, i2);
        }
        this.mDelegate.setAutoFillChangeListener(new OnTimeChangedListener() { // from class: android.widget.TimePicker$$ExternalSyntheticLambda0
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public final void onTimeChanged(TimePicker timePicker, int i4, int i5) {
                TimePicker.this.lambda$new$0(context, timePicker, i4, i5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Context context, TimePicker timePicker, int i, int i2) {
        AutofillManager autofillManager = (AutofillManager) context.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public void setHour(int i) {
        this.mDelegate.setHour(MathUtils.constrain(i, 0, 23));
    }

    public int getHour() {
        return this.mDelegate.getHour();
    }

    public void setMinute(int i) {
        this.mDelegate.setMinute(MathUtils.constrain(i, 0, 59));
    }

    public int getMinute() {
        return this.mDelegate.getMinute();
    }

    @Deprecated
    public void setCurrentHour(Integer num) {
        setHour(num.intValue());
    }

    @Deprecated
    public Integer getCurrentHour() {
        return Integer.valueOf(getHour());
    }

    @Deprecated
    public void setCurrentMinute(Integer num) {
        setMinute(num.intValue());
    }

    @Deprecated
    public Integer getCurrentMinute() {
        return Integer.valueOf(getMinute());
    }

    public void setIs24HourView(Boolean bool) {
        if (bool == null) {
            return;
        }
        this.mDelegate.setIs24Hour(bool.booleanValue());
    }

    public boolean is24HourView() {
        return this.mDelegate.is24Hour();
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.view.View
    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    public boolean validateInput() {
        return this.mDelegate.validateInput();
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

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TimePicker.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    public View getHourView() {
        return this.mDelegate.getHourView();
    }

    public View getMinuteView() {
        return this.mDelegate.getMinuteView();
    }

    public View getAmView() {
        return this.mDelegate.getAmView();
    }

    public View getPmView() {
        return this.mDelegate.getPmView();
    }

    static String[] getAmPmStrings(Context context) {
        DateFormatSymbols icuDateFormatSymbols = DateFormat.getIcuDateFormatSymbols(context.getResources().getConfiguration().locale);
        String[] amPmStrings = icuDateFormatSymbols.getAmPmStrings();
        String[] ampmNarrowStrings = icuDateFormatSymbols.getAmpmNarrowStrings();
        return new String[]{amPmStrings[0].length() > 4 ? ampmNarrowStrings[0] : amPmStrings[0], amPmStrings[1].length() > 4 ? ampmNarrowStrings[1] : amPmStrings[1]};
    }

    static abstract class AbstractTimePickerDelegate implements TimePickerDelegate {
        protected OnTimeChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected final Context mContext;
        protected final TimePicker mDelegator;
        protected final Locale mLocale;
        protected OnTimeChangedListener mOnTimeChangedListener;

        public AbstractTimePickerDelegate(TimePicker timePicker, Context context) {
            this.mDelegator = timePicker;
            this.mContext = context;
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
            this.mOnTimeChangedListener = onTimeChangedListener;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public void setAutoFillChangeListener(OnTimeChangedListener onTimeChangedListener) {
            this.mAutoFillChangeListener = onTimeChangedListener;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                Log.w(TimePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            Calendar calendar = Calendar.getInstance(this.mLocale);
            calendar.setTimeInMillis(dateValue);
            setDate(calendar.get(11), calendar.get(12));
            this.mAutofilledValue = dateValue;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public final AutofillValue getAutofillValue() {
            long j = this.mAutofilledValue;
            if (j != 0) {
                return AutofillValue.forDate(j);
            }
            Calendar calendar = Calendar.getInstance(this.mLocale);
            calendar.set(11, getHour());
            calendar.set(12, getMinute());
            return AutofillValue.forDate(calendar.getTimeInMillis());
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        protected static class SavedState extends View.BaseSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.TimePicker.AbstractTimePickerDelegate.SavedState.1
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
            private final int mCurrentItemShowing;
            private final int mHour;
            private final boolean mIs24HourMode;
            private final int mMinute;

            public SavedState(Parcelable parcelable, int i, int i2, boolean z) {
                this(parcelable, i, i2, z, 0);
            }

            public SavedState(Parcelable parcelable, int i, int i2, boolean z, int i3) {
                super(parcelable);
                this.mHour = i;
                this.mMinute = i2;
                this.mIs24HourMode = z;
                this.mCurrentItemShowing = i3;
            }

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mHour = parcel.readInt();
                this.mMinute = parcel.readInt();
                this.mIs24HourMode = parcel.readInt() == 1;
                this.mCurrentItemShowing = parcel.readInt();
            }

            public int getHour() {
                return this.mHour;
            }

            public int getMinute() {
                return this.mMinute;
            }

            public boolean is24HourMode() {
                return this.mIs24HourMode;
            }

            public int getCurrentItemShowing() {
                return this.mCurrentItemShowing;
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mHour);
                parcel.writeInt(this.mMinute);
                parcel.writeInt(this.mIs24HourMode ? 1 : 0);
                parcel.writeInt(this.mCurrentItemShowing);
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
