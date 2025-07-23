package android.widget;

import android.content.Context;
import android.os.LocaleList;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class TextInputTimePickerView extends RelativeLayout {
    private static final int AM = 0;
    public static final int AMPM = 2;
    public static final int HOURS = 0;
    public static final int MINUTES = 1;
    private static final int PM = 1;
    private final Spinner mAmPmSpinner;
    private final TextView mErrorLabel;
    private boolean mErrorShowing;
    private final EditText mHourEditText;
    private boolean mHourFormatStartsAtZero;
    private final TextView mHourLabel;
    private final TextView mInputSeparatorView;
    private boolean mIs24Hour;
    private OnValueTypedListener mListener;
    private final EditText mMinuteEditText;
    private final TextView mMinuteLabel;
    private boolean mTimeSet;

    interface OnValueTypedListener {
        void onValueChanged(int i, int i2);
    }

    public TextInputTimePickerView(Context context) {
        this(context, null);
    }

    public TextInputTimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputTimePickerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TextInputTimePickerView(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        inflate(context, R.layout.time_picker_text_input_material, this);
        EditText editText = (EditText) findViewById(R.id.input_hour);
        this.mHourEditText = editText;
        EditText editText2 = (EditText) findViewById(R.id.input_minute);
        this.mMinuteEditText = editText2;
        this.mInputSeparatorView = (TextView) findViewById(R.id.input_separator);
        this.mErrorLabel = (TextView) findViewById(R.id.label_error);
        this.mHourLabel = (TextView) findViewById(R.id.label_hour);
        this.mMinuteLabel = (TextView) findViewById(R.id.label_minute);
        editText.addTextChangedListener(new TextWatcher() { // from class: android.widget.TextInputTimePickerView.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!TextInputTimePickerView.this.parseAndSetHourInternal(editable.toString()) || editable.length() <= 1 || ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).isEnabled()) {
                    return;
                }
                TextInputTimePickerView.this.mMinuteEditText.requestFocus();
            }
        });
        editText2.addTextChangedListener(new TextWatcher() { // from class: android.widget.TextInputTimePickerView.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                TextInputTimePickerView.this.parseAndSetMinuteInternal(editable.toString());
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.am_pm_spinner);
        this.mAmPmSpinner = spinner;
        String[] amPmStrings = TimePicker.getAmPmStrings(context);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, 17367049);
        arrayAdapter.add(TimePickerClockDelegate.obtainVerbatim(amPmStrings[0]));
        arrayAdapter.add(TimePickerClockDelegate.obtainVerbatim(amPmStrings[1]));
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: android.widget.TextInputTimePickerView.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i3, long j) {
                if (i3 == 0) {
                    TextInputTimePickerView.this.mListener.onValueChanged(2, 0);
                } else {
                    TextInputTimePickerView.this.mListener.onValueChanged(2, 1);
                }
            }
        });
    }

    void setListener(OnValueTypedListener onValueTypedListener) {
        this.mListener = onValueTypedListener;
    }

    void setHourFormat(int i) {
        this.mHourEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
        this.mMinuteEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
        LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
        this.mHourEditText.setImeHintLocales(locales);
        this.mMinuteEditText.setImeHintLocales(locales);
    }

    boolean validateInput() {
        String editable;
        String editable2;
        if (TextUtils.isEmpty(this.mHourEditText.getText())) {
            editable = this.mHourEditText.getHint().toString();
        } else {
            editable = this.mHourEditText.getText().toString();
        }
        if (TextUtils.isEmpty(this.mMinuteEditText.getText())) {
            editable2 = this.mMinuteEditText.getHint().toString();
        } else {
            editable2 = this.mMinuteEditText.getText().toString();
        }
        boolean z = parseAndSetHourInternal(editable) && parseAndSetMinuteInternal(editable2);
        setError(!z);
        return z;
    }

    void updateSeparator(String str) {
        this.mInputSeparatorView.lambda$setTextAsync$0(str);
    }

    private void setError(boolean z) {
        this.mErrorShowing = z;
        this.mErrorLabel.setVisibility(z ? 0 : 4);
        this.mHourLabel.setVisibility(z ? 4 : 0);
        this.mMinuteLabel.setVisibility(z ? 4 : 0);
    }

    private void setTimeSet(boolean z) {
        this.mTimeSet = this.mTimeSet || z;
    }

    private boolean isTimeSet() {
        return this.mTimeSet;
    }

    void updateTextInputValues(int i, int i2, int i3, boolean z, boolean z2) {
        this.mIs24Hour = z;
        this.mHourFormatStartsAtZero = z2;
        this.mAmPmSpinner.setVisibility(z ? 4 : 0);
        if (i3 == 0) {
            this.mAmPmSpinner.setSelection(0);
        } else {
            this.mAmPmSpinner.setSelection(1);
        }
        if (isTimeSet()) {
            this.mHourEditText.lambda$setTextAsync$0(String.format("%d", Integer.valueOf(i)));
            this.mMinuteEditText.lambda$setTextAsync$0(String.format("%02d", Integer.valueOf(i2)));
        } else {
            this.mHourEditText.setHint(String.format("%d", Integer.valueOf(i)));
            this.mMinuteEditText.setHint(String.format("%02d", Integer.valueOf(i2)));
        }
        if (this.mErrorShowing) {
            validateInput();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseAndSetHourInternal(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            if (!isValidLocalizedHour(parseInt)) {
                int i = !this.mHourFormatStartsAtZero ? 1 : 0;
                this.mListener.onValueChanged(0, getHourOfDayFromLocalizedHour(MathUtils.constrain(parseInt, i, this.mIs24Hour ? 23 : i + 11)));
                return false;
            }
            this.mListener.onValueChanged(0, getHourOfDayFromLocalizedHour(parseInt));
            setTimeSet(true);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseAndSetMinuteInternal(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt >= 0 && parseInt <= 59) {
                this.mListener.onValueChanged(1, parseInt);
                setTimeSet(true);
                return true;
            }
            this.mListener.onValueChanged(1, MathUtils.constrain(parseInt, 0, 59));
        } catch (NumberFormatException unused) {
        }
        return false;
    }

    private boolean isValidLocalizedHour(int i) {
        int i2 = !this.mHourFormatStartsAtZero ? 1 : 0;
        return i >= i2 && i <= (this.mIs24Hour ? 23 : 11) + i2;
    }

    private int getHourOfDayFromLocalizedHour(int i) {
        if (this.mIs24Hour) {
            if (this.mHourFormatStartsAtZero || i != 24) {
                return i;
            }
            return 0;
        }
        if (!this.mHourFormatStartsAtZero && i == 12) {
            i = 0;
        }
        return this.mAmPmSpinner.getSelectedItemPosition() == 1 ? i + 12 : i;
    }
}
