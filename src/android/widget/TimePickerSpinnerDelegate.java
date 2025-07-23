package android.widget;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import com.android.internal.R;
import java.util.Calendar;

/* loaded from: classes5.dex */
class TimePickerSpinnerDelegate extends TimePicker.AbstractTimePickerDelegate {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;
    private final Button mAmPmButton;
    private final NumberPicker mAmPmSpinner;
    private final EditText mAmPmSpinnerInput;
    private final String[] mAmPmStrings;
    private final TextView mDivider;
    private char mHourFormat;
    private final NumberPicker mHourSpinner;
    private final EditText mHourSpinnerInput;
    private boolean mHourWithTwoDigit;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsEnabled;
    private final NumberPicker mMinuteSpinner;
    private final EditText mMinuteSpinnerInput;
    private final Calendar mTempCalendar;

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean validateInput() {
        return true;
    }

    public TimePickerSpinnerDelegate(TimePicker timePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(timePicker, context);
        this.mIsEnabled = true;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(13, R.layout.time_picker_legacy);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(this.mContext).inflate(resourceId, (ViewGroup) this.mDelegator, true).setSaveFromParentEnabled(false);
        NumberPicker numberPicker = (NumberPicker) timePicker.findViewById(R.id.hour);
        this.mHourSpinner = numberPicker;
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.1
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker2, int i3, int i4) {
                TimePickerSpinnerDelegate.this.updateInputState();
                if (!TimePickerSpinnerDelegate.this.is24Hour() && ((i3 == 11 && i4 == 12) || (i3 == 12 && i4 == 11))) {
                    TimePickerSpinnerDelegate.this.mIsAm = !r2.mIsAm;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        EditText editText = (EditText) numberPicker.findViewById(R.id.numberpicker_input);
        this.mHourSpinnerInput = editText;
        editText.setImeOptions(5);
        TextView textView = (TextView) this.mDelegator.findViewById(R.id.divider);
        this.mDivider = textView;
        if (textView != null) {
            setDividerText();
        }
        NumberPicker numberPicker2 = (NumberPicker) this.mDelegator.findViewById(R.id.minute);
        this.mMinuteSpinner = numberPicker2;
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(59);
        numberPicker2.setOnLongPressUpdateInterval(100L);
        numberPicker2.setFormatter(NumberPicker.getTwoDigitFormatter());
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.2
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker3, int i3, int i4) {
                TimePickerSpinnerDelegate.this.updateInputState();
                int minValue = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMinValue();
                int maxValue = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMaxValue();
                if (i3 == maxValue && i4 == minValue) {
                    int value = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() + 1;
                    if (!TimePickerSpinnerDelegate.this.is24Hour() && value == 12) {
                        TimePickerSpinnerDelegate.this.mIsAm = !r3.mIsAm;
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(value);
                } else if (i3 == minValue && i4 == maxValue) {
                    int value2 = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() - 1;
                    if (!TimePickerSpinnerDelegate.this.is24Hour() && value2 == 11) {
                        TimePickerSpinnerDelegate.this.mIsAm = !r3.mIsAm;
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(value2);
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        EditText editText2 = (EditText) numberPicker2.findViewById(R.id.numberpicker_input);
        this.mMinuteSpinnerInput = editText2;
        editText2.setImeOptions(5);
        String[] amPmStrings = TimePicker.getAmPmStrings(context);
        this.mAmPmStrings = amPmStrings;
        View findViewById = this.mDelegator.findViewById(R.id.amPm);
        if (findViewById instanceof Button) {
            this.mAmPmSpinner = null;
            this.mAmPmSpinnerInput = null;
            Button button = (Button) findViewById;
            this.mAmPmButton = button;
            button.setOnClickListener(new View.OnClickListener() { // from class: android.widget.TimePickerSpinnerDelegate.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.requestFocus();
                    TimePickerSpinnerDelegate.this.mIsAm = !r2.mIsAm;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
        } else {
            this.mAmPmButton = null;
            NumberPicker numberPicker3 = (NumberPicker) findViewById;
            this.mAmPmSpinner = numberPicker3;
            numberPicker3.setMinValue(0);
            numberPicker3.setMaxValue(1);
            numberPicker3.setDisplayedValues(amPmStrings);
            numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.4
                @Override // android.widget.NumberPicker.OnValueChangeListener
                public void onValueChange(NumberPicker numberPicker4, int i3, int i4) {
                    TimePickerSpinnerDelegate.this.updateInputState();
                    numberPicker4.requestFocus();
                    TimePickerSpinnerDelegate.this.mIsAm = !r1.mIsAm;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
            EditText editText3 = (EditText) numberPicker3.findViewById(R.id.numberpicker_input);
            this.mAmPmSpinnerInput = editText3;
            editText3.setImeOptions(6);
        }
        if (isAmPmAtStart()) {
            ViewGroup viewGroup = (ViewGroup) timePicker.findViewById(R.id.timePickerLayout);
            viewGroup.removeView(findViewById);
            viewGroup.addView(findViewById, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
            int marginStart = marginLayoutParams.getMarginStart();
            int marginEnd = marginLayoutParams.getMarginEnd();
            if (marginStart != marginEnd) {
                marginLayoutParams.setMarginStart(marginEnd);
                marginLayoutParams.setMarginEnd(marginStart);
            }
        }
        getHourFormatData();
        updateHourControl();
        updateMinuteControl();
        updateAmPmControl();
        Calendar calendar = Calendar.getInstance(this.mLocale);
        this.mTempCalendar = calendar;
        setHour(calendar.get(11));
        setMinute(calendar.get(12));
        if (!isEnabled()) {
            setEnabled(false);
        }
        setContentDescriptions();
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    private void getHourFormatData() {
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24HourView ? "Hm" : "hm");
        int length = bestDateTimePattern.length();
        this.mHourWithTwoDigit = false;
        for (int i = 0; i < length; i++) {
            char charAt = bestDateTimePattern.charAt(i);
            if (charAt == 'H' || charAt == 'h' || charAt == 'K' || charAt == 'k') {
                this.mHourFormat = charAt;
                int i2 = i + 1;
                if (i2 >= length || charAt != bestDateTimePattern.charAt(i2)) {
                    return;
                }
                this.mHourWithTwoDigit = true;
                return;
            }
        }
    }

    private boolean isAmPmAtStart() {
        return DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith(FullBackup.APK_TREE_TOKEN);
    }

    private void setDividerText() {
        String substring;
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24HourView ? "Hm" : "hm");
        int lastIndexOf = bestDateTimePattern.lastIndexOf(72);
        if (lastIndexOf == -1) {
            lastIndexOf = bestDateTimePattern.lastIndexOf(104);
        }
        if (lastIndexOf == -1) {
            substring = ":";
        } else {
            int i = lastIndexOf + 1;
            int indexOf = bestDateTimePattern.indexOf(109, i);
            if (indexOf == -1) {
                substring = Character.toString(bestDateTimePattern.charAt(i));
            } else {
                substring = bestDateTimePattern.substring(i, indexOf);
            }
        }
        this.mDivider.lambda$setTextAsync$0(substring);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setDate(int i, int i2) {
        setCurrentHour(i, false);
        setCurrentMinute(i2, false);
        onTimeChanged();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setHour(int i) {
        setCurrentHour(i, true);
    }

    private void setCurrentHour(int i, boolean z) {
        if (i == getHour()) {
            return;
        }
        resetAutofilledValue();
        if (!is24Hour()) {
            if (i >= 12) {
                this.mIsAm = false;
                if (i > 12) {
                    i -= 12;
                }
            } else {
                this.mIsAm = true;
                if (i == 0) {
                    i = 12;
                }
            }
            updateAmPmControl();
        }
        this.mHourSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getHour() {
        int value = this.mHourSpinner.getValue();
        if (is24Hour()) {
            return value;
        }
        if (this.mIsAm) {
            return value % 12;
        }
        return (value % 12) + 12;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setMinute(int i) {
        setCurrentMinute(i, true);
    }

    private void setCurrentMinute(int i, boolean z) {
        if (i == getMinute()) {
            return;
        }
        resetAutofilledValue();
        this.mMinuteSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getMinute() {
        return this.mMinuteSpinner.getValue();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setIs24Hour(boolean z) {
        if (this.mIs24HourView == z) {
            return;
        }
        int hour = getHour();
        this.mIs24HourView = z;
        getHourFormatData();
        updateHourControl();
        setCurrentHour(hour, false);
        updateMinuteControl();
        updateAmPmControl();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean is24Hour() {
        return this.mIs24HourView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setEnabled(boolean z) {
        this.mMinuteSpinner.setEnabled(z);
        TextView textView = this.mDivider;
        if (textView != null) {
            textView.setEnabled(z);
        }
        this.mHourSpinner.setEnabled(z);
        NumberPicker numberPicker = this.mAmPmSpinner;
        if (numberPicker != null) {
            numberPicker.setEnabled(z);
        } else {
            this.mAmPmButton.setEnabled(z);
        }
        this.mIsEnabled = z;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, getHour(), getMinute(), is24Hour());
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof TimePicker.AbstractTimePickerDelegate.SavedState) {
            TimePicker.AbstractTimePickerDelegate.SavedState savedState = (TimePicker.AbstractTimePickerDelegate.SavedState) parcelable;
            setHour(savedState.getHour());
            setMinute(savedState.getMinute());
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i = this.mIs24HourView ? 129 : 65;
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i));
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getHourView() {
        return this.mHourSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getMinuteView() {
        return this.mMinuteSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getAmView() {
        return this.mAmPmSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getPmView() {
        return this.mAmPmSpinnerInput;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputState() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            if (this.mHourSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mHourSpinnerInput, 0);
                this.mHourSpinnerInput.clearFocus();
            } else {
                if (this.mMinuteSpinnerInput.hasFocus()) {
                    inputMethodManager.hideSoftInputFromView(this.mMinuteSpinnerInput, 0);
                    this.mMinuteSpinnerInput.clearFocus();
                    return;
                }
                EditText editText = this.mAmPmSpinnerInput;
                if (editText == null || !editText.hasFocus()) {
                    return;
                }
                inputMethodManager.hideSoftInputFromView(this.mAmPmSpinnerInput, 0);
                this.mAmPmSpinnerInput.clearFocus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAmPmControl() {
        if (is24Hour()) {
            NumberPicker numberPicker = this.mAmPmSpinner;
            if (numberPicker != null) {
                numberPicker.setVisibility(8);
            } else {
                this.mAmPmButton.setVisibility(8);
            }
        } else {
            int i = !this.mIsAm ? 1 : 0;
            NumberPicker numberPicker2 = this.mAmPmSpinner;
            if (numberPicker2 != null) {
                numberPicker2.setValue(i);
                this.mAmPmSpinner.setVisibility(0);
            } else {
                this.mAmPmButton.lambda$setTextAsync$0(this.mAmPmStrings[i]);
                this.mAmPmButton.setVisibility(0);
            }
        }
        this.mDelegator.sendAccessibilityEvent(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    private void updateHourControl() {
        if (is24Hour()) {
            if (this.mHourFormat == 'k') {
                this.mHourSpinner.setMinValue(1);
                this.mHourSpinner.setMaxValue(24);
            } else {
                this.mHourSpinner.setMinValue(0);
                this.mHourSpinner.setMaxValue(23);
            }
        } else if (this.mHourFormat == 'K') {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(11);
        } else {
            this.mHourSpinner.setMinValue(1);
            this.mHourSpinner.setMaxValue(12);
        }
        this.mHourSpinner.setFormatter(this.mHourWithTwoDigit ? NumberPicker.getTwoDigitFormatter() : null);
    }

    private void updateMinuteControl() {
        if (is24Hour()) {
            this.mMinuteSpinnerInput.setImeOptions(6);
        } else {
            this.mMinuteSpinnerInput.setImeOptions(5);
        }
    }

    private void setContentDescriptions() {
        trySetContentDescription(this.mMinuteSpinner, R.id.increment, R.string.time_picker_increment_minute_button);
        trySetContentDescription(this.mMinuteSpinner, R.id.decrement, R.string.time_picker_decrement_minute_button);
        trySetContentDescription(this.mHourSpinner, R.id.increment, R.string.time_picker_increment_hour_button);
        trySetContentDescription(this.mHourSpinner, R.id.decrement, R.string.time_picker_decrement_hour_button);
        NumberPicker numberPicker = this.mAmPmSpinner;
        if (numberPicker != null) {
            trySetContentDescription(numberPicker, R.id.increment, R.string.time_picker_increment_set_pm_button);
            trySetContentDescription(this.mAmPmSpinner, R.id.decrement, R.string.time_picker_decrement_set_am_button);
        }
    }

    private void trySetContentDescription(View view, int i, int i2) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            findViewById.setContentDescription(this.mContext.getString(i2));
        }
    }
}
