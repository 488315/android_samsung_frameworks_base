package android.widget;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadialTimePickerView;
import android.widget.RelativeLayout;
import android.widget.TextInputTimePickerView;
import android.widget.TimePicker;
import com.android.internal.R;
import com.android.internal.widget.NumericTextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

/* loaded from: classes5.dex */
class TimePickerClockDelegate extends TimePicker.AbstractTimePickerDelegate {
    private static final int AM = 0;
    private static final long DELAY_COMMIT_MILLIS = 2000;
    private static final int FROM_EXTERNAL_API = 0;
    private static final int FROM_INPUT_PICKER = 2;
    private static final int FROM_RADIAL_PICKER = 1;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int HOUR_INDEX = 0;
    private static final int MINUTE_INDEX = 1;
    private static final int PM = 1;
    private boolean mAllowAutoAdvance;
    private final RadioButton mAmLabel;
    private final View mAmPmLayout;
    private final View.OnClickListener mClickListener;
    private final Runnable mCommitHour;
    private final Runnable mCommitMinute;
    private int mCurrentHour;
    private int mCurrentMinute;
    private final NumericTextView.OnValueChangedListener mDigitEnteredListener;
    private final View.OnFocusChangeListener mFocusListener;
    private boolean mHourFormatShowLeadingZero;
    private boolean mHourFormatStartsAtZero;
    private final NumericTextView mHourView;
    private boolean mIs24Hour;
    private boolean mIsAmPmAtLeft;
    private boolean mIsAmPmAtTop;
    private boolean mIsEnabled;
    private final NumericTextView mMinuteView;
    private final RadialTimePickerView.OnValueSelectedListener mOnValueSelectedListener;
    private final TextInputTimePickerView.OnValueTypedListener mOnValueTypedListener;
    private final RadioButton mPmLabel;
    private boolean mRadialPickerModeEnabled;
    private final View mRadialTimePickerHeader;
    private final ImageButton mRadialTimePickerModeButton;
    private final String mRadialTimePickerModeEnabledDescription;
    private final RadialTimePickerView mRadialTimePickerView;
    private final String mSelectHours;
    private final String mSelectMinutes;
    private final TextView mSeparatorView;
    private final Calendar mTempCalendar;
    private final View mTextInputPickerHeader;
    private final String mTextInputPickerModeEnabledDescription;
    private final TextInputTimePickerView mTextInputPickerView;
    private static final int[] ATTRS_TEXT_COLOR = {16842904};
    private static final int[] ATTRS_DISABLED_ALPHA = {16842803};

    @Retention(RetentionPolicy.SOURCE)
    private @interface ChangeSource {
    }

    private int multiplyAlphaComponent(int i, float f) {
        return (16777215 & i) | (((int) ((((i >> 24) & 255) * f) + 0.5f)) << 24);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getBaseline() {
        return -1;
    }

    public TimePickerClockDelegate(TimePicker timePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(timePicker, context);
        RadialTimePickerView.OnValueSelectedListener onValueSelectedListener;
        ColorStateList colorStateList;
        this.mRadialPickerModeEnabled = true;
        this.mIsEnabled = true;
        this.mIsAmPmAtLeft = false;
        this.mIsAmPmAtTop = false;
        RadialTimePickerView.OnValueSelectedListener onValueSelectedListener2 = new RadialTimePickerView.OnValueSelectedListener() { // from class: android.widget.TimePickerClockDelegate.2
            @Override // android.widget.RadialTimePickerView.OnValueSelectedListener
            public void onValueSelected(int i3, int i4, boolean z) {
                if (i3 == 0) {
                    boolean z2 = TimePickerClockDelegate.this.getHour() != i4;
                    if (TimePickerClockDelegate.this.mAllowAutoAdvance && z) {
                        r1 = true;
                    }
                    TimePickerClockDelegate.this.setHourInternal(i4, 1, !r1, true);
                    if (r1) {
                        TimePickerClockDelegate.this.setCurrentItemShowing(1, true);
                    }
                    r1 = z2;
                } else if (i3 == 1) {
                    r1 = TimePickerClockDelegate.this.getMinute() != i4;
                    TimePickerClockDelegate.this.setMinuteInternal(i4, 1, true);
                }
                if (TimePickerClockDelegate.this.mOnTimeChangedListener == null || !r1) {
                    return;
                }
                TimePickerClockDelegate.this.mOnTimeChangedListener.onTimeChanged(TimePickerClockDelegate.this.mDelegator, TimePickerClockDelegate.this.getHour(), TimePickerClockDelegate.this.getMinute());
            }
        };
        this.mOnValueSelectedListener = onValueSelectedListener2;
        TextInputTimePickerView.OnValueTypedListener onValueTypedListener = new TextInputTimePickerView.OnValueTypedListener() { // from class: android.widget.TimePickerClockDelegate.3
            @Override // android.widget.TextInputTimePickerView.OnValueTypedListener
            public void onValueChanged(int i3, int i4) {
                if (i3 == 0) {
                    TimePickerClockDelegate.this.setHourInternal(i4, 2, false, true);
                } else if (i3 == 1) {
                    TimePickerClockDelegate.this.setMinuteInternal(i4, 2, true);
                } else {
                    if (i3 != 2) {
                        return;
                    }
                    TimePickerClockDelegate.this.setAmOrPm(i4);
                }
            }
        };
        this.mOnValueTypedListener = onValueTypedListener;
        NumericTextView.OnValueChangedListener onValueChangedListener = new NumericTextView.OnValueChangedListener() { // from class: android.widget.TimePickerClockDelegate.4
            @Override // com.android.internal.widget.NumericTextView.OnValueChangedListener
            public void onValueChanged(NumericTextView numericTextView, int i3, boolean z, boolean z2) {
                Runnable runnable;
                NumericTextView numericTextView2 = null;
                if (numericTextView == TimePickerClockDelegate.this.mHourView) {
                    runnable = TimePickerClockDelegate.this.mCommitHour;
                    if (numericTextView.isFocused()) {
                        numericTextView2 = TimePickerClockDelegate.this.mMinuteView;
                    }
                } else if (numericTextView != TimePickerClockDelegate.this.mMinuteView) {
                    return;
                } else {
                    runnable = TimePickerClockDelegate.this.mCommitMinute;
                }
                numericTextView.removeCallbacks(runnable);
                if (z) {
                    if (z2) {
                        runnable.run();
                        if (numericTextView2 != null) {
                            numericTextView2.requestFocus();
                            return;
                        }
                        return;
                    }
                    numericTextView.postDelayed(runnable, TimePickerClockDelegate.DELAY_COMMIT_MILLIS);
                }
            }
        };
        this.mDigitEnteredListener = onValueChangedListener;
        this.mCommitHour = new Runnable() { // from class: android.widget.TimePickerClockDelegate.5
            @Override // java.lang.Runnable
            public void run() {
                TimePickerClockDelegate timePickerClockDelegate = TimePickerClockDelegate.this;
                timePickerClockDelegate.setHour(timePickerClockDelegate.mHourView.getValue());
            }
        };
        this.mCommitMinute = new Runnable() { // from class: android.widget.TimePickerClockDelegate.6
            @Override // java.lang.Runnable
            public void run() {
                TimePickerClockDelegate timePickerClockDelegate = TimePickerClockDelegate.this;
                timePickerClockDelegate.setMinute(timePickerClockDelegate.mMinuteView.getValue());
            }
        };
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() { // from class: android.widget.TimePickerClockDelegate.7
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    switch (view.getId()) {
                        case R.id.am_label /* 16908821 */:
                            TimePickerClockDelegate.this.setAmOrPm(0);
                            break;
                        case R.id.hours /* 16909189 */:
                            TimePickerClockDelegate.this.setCurrentItemShowing(0, true);
                            break;
                        case R.id.minutes /* 16909375 */:
                            TimePickerClockDelegate.this.setCurrentItemShowing(1, true);
                            break;
                        case R.id.pm_label /* 16909534 */:
                            TimePickerClockDelegate.this.setAmOrPm(1);
                            break;
                        default:
                            return;
                    }
                    TimePickerClockDelegate.this.tryVibrate();
                }
            }
        };
        this.mFocusListener = onFocusChangeListener;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: android.widget.TimePickerClockDelegate.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.am_label /* 16908821 */:
                        TimePickerClockDelegate.this.setAmOrPm(0);
                        break;
                    case R.id.hours /* 16909189 */:
                        TimePickerClockDelegate.this.setCurrentItemShowing(0, true);
                        break;
                    case R.id.minutes /* 16909375 */:
                        TimePickerClockDelegate.this.setCurrentItemShowing(1, true);
                        break;
                    case R.id.pm_label /* 16909534 */:
                        TimePickerClockDelegate.this.setAmOrPm(1);
                        break;
                    default:
                        return;
                }
                TimePickerClockDelegate.this.tryVibrate();
            }
        };
        this.mClickListener = onClickListener;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, i2);
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = this.mContext.getResources();
        this.mSelectHours = resources.getString(R.string.select_hours);
        this.mSelectMinutes = resources.getString(R.string.select_minutes);
        View inflate = layoutInflater.inflate(obtainStyledAttributes.getResourceId(12, R.layout.time_picker_material), timePicker);
        inflate.setSaveFromParentEnabled(false);
        View findViewById = inflate.findViewById(R.id.time_header);
        this.mRadialTimePickerHeader = findViewById;
        findViewById.setOnTouchListener(new NearestTouchDelegate());
        NumericTextView numericTextView = (NumericTextView) inflate.findViewById(R.id.hours);
        this.mHourView = numericTextView;
        numericTextView.setOnClickListener(onClickListener);
        numericTextView.setOnFocusChangeListener(onFocusChangeListener);
        numericTextView.setOnDigitEnteredListener(onValueChangedListener);
        numericTextView.setAccessibilityDelegate(new ClickActionDelegate(context, R.string.select_hours));
        numericTextView.setAccessibilityLiveRegion(1);
        TextView textView = (TextView) inflate.findViewById(R.id.separator);
        this.mSeparatorView = textView;
        NumericTextView numericTextView2 = (NumericTextView) inflate.findViewById(R.id.minutes);
        this.mMinuteView = numericTextView2;
        numericTextView2.setOnClickListener(onClickListener);
        numericTextView2.setOnFocusChangeListener(onFocusChangeListener);
        numericTextView2.setOnDigitEnteredListener(onValueChangedListener);
        numericTextView2.setAccessibilityDelegate(new ClickActionDelegate(context, R.string.select_minutes));
        numericTextView2.setAccessibilityLiveRegion(1);
        numericTextView2.setRange(0, 59);
        View findViewById2 = inflate.findViewById(R.id.ampm_layout);
        this.mAmPmLayout = findViewById2;
        findViewById2.setOnTouchListener(new NearestTouchDelegate());
        String[] amPmStrings = TimePicker.getAmPmStrings(context);
        RadioButton radioButton = (RadioButton) findViewById2.findViewById(R.id.am_label);
        this.mAmLabel = radioButton;
        radioButton.lambda$setTextAsync$0(obtainVerbatim(amPmStrings[0]));
        radioButton.setOnClickListener(onClickListener);
        ensureMinimumTextWidth(radioButton);
        RadioButton radioButton2 = (RadioButton) findViewById2.findViewById(R.id.pm_label);
        this.mPmLabel = radioButton2;
        radioButton2.lambda$setTextAsync$0(obtainVerbatim(amPmStrings[1]));
        radioButton2.setOnClickListener(onClickListener);
        ensureMinimumTextWidth(radioButton2);
        int resourceId = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId != 0) {
            onValueSelectedListener = onValueSelectedListener2;
            TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            ColorStateList applyLegacyColorFixes = applyLegacyColorFixes(obtainStyledAttributes2.getColorStateList(0));
            obtainStyledAttributes2.recycle();
            colorStateList = applyLegacyColorFixes;
        } else {
            onValueSelectedListener = onValueSelectedListener2;
            colorStateList = null;
        }
        colorStateList = colorStateList == null ? obtainStyledAttributes.getColorStateList(11) : colorStateList;
        View findViewById3 = inflate.findViewById(R.id.input_header);
        this.mTextInputPickerHeader = findViewById3;
        if (colorStateList != null) {
            numericTextView.setTextColor(colorStateList);
            textView.setTextColor(colorStateList);
            numericTextView2.setTextColor(colorStateList);
            radioButton.setTextColor(colorStateList);
            radioButton2.setTextColor(colorStateList);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(0)) {
            findViewById.setBackground(obtainStyledAttributes.getDrawable(0));
            findViewById3.setBackground(obtainStyledAttributes.getDrawable(0));
        }
        obtainStyledAttributes.recycle();
        RadialTimePickerView radialTimePickerView = (RadialTimePickerView) inflate.findViewById(R.id.radial_picker);
        this.mRadialTimePickerView = radialTimePickerView;
        radialTimePickerView.applyAttributes(attributeSet, i, i2);
        radialTimePickerView.setOnValueSelectedListener(onValueSelectedListener);
        TextInputTimePickerView textInputTimePickerView = (TextInputTimePickerView) inflate.findViewById(R.id.input_mode);
        this.mTextInputPickerView = textInputTimePickerView;
        textInputTimePickerView.setListener(onValueTypedListener);
        ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.toggle_mode);
        this.mRadialTimePickerModeButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: android.widget.TimePickerClockDelegate.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TimePickerClockDelegate.this.toggleRadialPickerMode();
            }
        });
        this.mRadialTimePickerModeEnabledDescription = context.getResources().getString(R.string.time_picker_radial_mode_description);
        this.mTextInputPickerModeEnabledDescription = context.getResources().getString(R.string.time_picker_text_input_mode_description);
        this.mAllowAutoAdvance = true;
        updateHourFormat();
        Calendar calendar = Calendar.getInstance(this.mLocale);
        this.mTempCalendar = calendar;
        initialize(calendar.get(11), calendar.get(12), this.mIs24Hour, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleRadialPickerMode() {
        if (this.mRadialPickerModeEnabled) {
            this.mRadialTimePickerView.setVisibility(8);
            this.mRadialTimePickerHeader.setVisibility(8);
            this.mTextInputPickerHeader.setVisibility(0);
            this.mTextInputPickerView.setVisibility(0);
            this.mRadialTimePickerModeButton.setImageResource(R.drawable.btn_clock_material);
            this.mRadialTimePickerModeButton.setContentDescription(this.mRadialTimePickerModeEnabledDescription);
            this.mRadialPickerModeEnabled = false;
            return;
        }
        this.mRadialTimePickerView.setVisibility(0);
        this.mRadialTimePickerHeader.setVisibility(0);
        this.mTextInputPickerHeader.setVisibility(8);
        this.mTextInputPickerView.setVisibility(8);
        this.mRadialTimePickerModeButton.setImageResource(R.drawable.btn_keyboard_key_material);
        this.mRadialTimePickerModeButton.setContentDescription(this.mTextInputPickerModeEnabledDescription);
        updateTextInputPicker();
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
        }
        this.mRadialPickerModeEnabled = true;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean validateInput() {
        return this.mTextInputPickerView.validateInput();
    }

    private static void ensureMinimumTextWidth(TextView textView) {
        textView.measure(0, 0);
        int measuredWidth = textView.getMeasuredWidth();
        textView.setMinWidth(measuredWidth);
        textView.setMinimumWidth(measuredWidth);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0071 A[LOOP:1: B:29:0x006d->B:31:0x0071, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateHourFormat() {
        /*
            r9 = this;
            java.util.Locale r0 = r9.mLocale
            boolean r1 = r9.mIs24Hour
            if (r1 == 0) goto L9
            java.lang.String r1 = "Hm"
            goto Lb
        L9:
            java.lang.String r1 = "hm"
        Lb:
            java.lang.String r0 = android.text.format.DateFormat.getBestDateTimePattern(r0, r1)
            int r1 = r0.length()
            r2 = 0
            r3 = r2
        L15:
            r4 = 72
            r5 = 75
            r6 = 1
            if (r3 >= r1) goto L3d
            char r7 = r0.charAt(r3)
            if (r7 == r4) goto L30
            r8 = 104(0x68, float:1.46E-43)
            if (r7 == r8) goto L30
            if (r7 == r5) goto L30
            r8 = 107(0x6b, float:1.5E-43)
            if (r7 != r8) goto L2d
            goto L30
        L2d:
            int r3 = r3 + 1
            goto L15
        L30:
            int r3 = r3 + r6
            if (r3 >= r1) goto L3b
            char r0 = r0.charAt(r3)
            if (r7 != r0) goto L3b
            r0 = r6
            goto L3f
        L3b:
            r0 = r2
            goto L3f
        L3d:
            r0 = r2
            r7 = r0
        L3f:
            r9.mHourFormatShowLeadingZero = r0
            if (r7 == r5) goto L48
            if (r7 != r4) goto L46
            goto L48
        L46:
            r0 = r2
            goto L49
        L48:
            r0 = r6
        L49:
            r9.mHourFormatStartsAtZero = r0
            r0 = r0 ^ r6
            boolean r1 = r9.mIs24Hour
            if (r1 == 0) goto L53
            r1 = 23
            goto L55
        L53:
            r1 = 11
        L55:
            int r1 = r1 + r0
            com.android.internal.widget.NumericTextView r3 = r9.mHourView
            r3.setRange(r0, r1)
            com.android.internal.widget.NumericTextView r0 = r9.mHourView
            boolean r1 = r9.mHourFormatShowLeadingZero
            r0.setShowLeadingZeroes(r1)
            java.util.Locale r0 = r9.mLocale
            android.icu.text.DecimalFormatSymbols r0 = android.icu.text.DecimalFormatSymbols.getInstance(r0)
            java.lang.String[] r0 = r0.getDigitStrings()
            r1 = r2
        L6d:
            r3 = 10
            if (r2 >= r3) goto L7e
            r3 = r0[r2]
            int r3 = r3.length()
            int r1 = java.lang.Math.max(r1, r3)
            int r2 = r2 + 1
            goto L6d
        L7e:
            android.widget.TextInputTimePickerView r9 = r9.mTextInputPickerView
            int r1 = r1 * 2
            r9.setHourFormat(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TimePickerClockDelegate.updateHourFormat():void");
    }

    static final CharSequence obtainVerbatim(String str) {
        return new SpannableStringBuilder().append(str, new TtsSpan.VerbatimBuilder(str).build(), 0);
    }

    private ColorStateList applyLegacyColorFixes(ColorStateList colorStateList) {
        int multiplyAlphaComponent;
        int i;
        if (colorStateList == null || colorStateList.hasState(16843518)) {
            return colorStateList;
        }
        if (colorStateList.hasState(16842913)) {
            i = colorStateList.getColorForState(StateSet.get(10), 0);
            multiplyAlphaComponent = colorStateList.getColorForState(StateSet.get(8), 0);
        } else {
            int defaultColor = colorStateList.getDefaultColor();
            TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(ATTRS_DISABLED_ALPHA);
            float f = obtainStyledAttributes.getFloat(0, 0.3f);
            obtainStyledAttributes.recycle();
            multiplyAlphaComponent = multiplyAlphaComponent(defaultColor, f);
            i = defaultColor;
        }
        if (i == 0 || multiplyAlphaComponent == 0) {
            return null;
        }
        return new ColorStateList(new int[][]{new int[]{16843518}, new int[0]}, new int[]{i, multiplyAlphaComponent});
    }

    private static class ClickActionDelegate extends View.AccessibilityDelegate {
        private final AccessibilityNodeInfo.AccessibilityAction mClickAction;

        public ClickActionDelegate(Context context, int i) {
            this.mClickAction = new AccessibilityNodeInfo.AccessibilityAction(16, context.getString(i));
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(this.mClickAction);
        }
    }

    private void initialize(int i, int i2, boolean z, int i3) {
        this.mCurrentHour = i;
        this.mCurrentMinute = i2;
        this.mIs24Hour = z;
        updateUI(i3);
    }

    private void updateUI(int i) {
        updateHeaderAmPm();
        updateHeaderHour(this.mCurrentHour, false);
        updateHeaderSeparator();
        updateHeaderMinute(this.mCurrentMinute, false);
        updateRadialPicker(i);
        updateTextInputPicker();
        this.mDelegator.invalidate();
    }

    private void updateTextInputPicker() {
        this.mTextInputPickerView.updateTextInputValues(getLocalizedHour(this.mCurrentHour), this.mCurrentMinute, this.mCurrentHour < 12 ? 0 : 1, this.mIs24Hour, this.mHourFormatStartsAtZero);
    }

    private void updateRadialPicker(int i) {
        this.mRadialTimePickerView.initialize(this.mCurrentHour, this.mCurrentMinute, this.mIs24Hour);
        setCurrentItemShowing(i, false);
    }

    private void updateHeaderAmPm() {
        if (this.mIs24Hour) {
            this.mAmPmLayout.setVisibility(8);
        } else {
            setAmPmStart(DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith(FullBackup.APK_TREE_TOKEN));
            updateAmPmLabelStates(this.mCurrentHour < 12 ? 0 : 1);
        }
    }

    private void setAmPmStart(boolean z) {
        int rule;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mAmPmLayout.getLayoutParams();
        if (layoutParams.getRule(1) != 0 || layoutParams.getRule(0) != 0) {
            int i = (int) (this.mContext.getResources().getDisplayMetrics().density * 8.0f);
            boolean z2 = TextUtils.getLayoutDirectionFromLocale(this.mLocale) == 0 ? z : !z;
            if (z2) {
                layoutParams.removeRule(1);
                layoutParams.addRule(0, this.mHourView.getId());
            } else {
                layoutParams.removeRule(0);
                layoutParams.addRule(1, this.mMinuteView.getId());
            }
            if (z) {
                layoutParams.setMarginStart(0);
                layoutParams.setMarginEnd(i);
            } else {
                layoutParams.setMarginStart(i);
                layoutParams.setMarginEnd(0);
            }
            this.mIsAmPmAtLeft = z2;
        } else if (layoutParams.getRule(3) != 0 || layoutParams.getRule(2) != 0) {
            if (this.mIsAmPmAtTop == z) {
                return;
            }
            if (z) {
                rule = layoutParams.getRule(3);
                layoutParams.removeRule(3);
                layoutParams.addRule(2, rule);
            } else {
                rule = layoutParams.getRule(2);
                layoutParams.removeRule(2);
                layoutParams.addRule(3, rule);
            }
            View findViewById = this.mRadialTimePickerHeader.findViewById(rule);
            findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingBottom(), findViewById.getPaddingRight(), findViewById.getPaddingTop());
            this.mIsAmPmAtTop = z;
        }
        this.mAmPmLayout.setLayoutParams(layoutParams);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setDate(int i, int i2) {
        setHourInternal(i, 0, true, false);
        setMinuteInternal(i2, 0, false);
        onTimeChanged();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setHour(int i) {
        setHourInternal(i, 0, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHourInternal(int i, int i2, boolean z, boolean z2) {
        if (this.mCurrentHour == i) {
            return;
        }
        resetAutofilledValue();
        this.mCurrentHour = i;
        updateHeaderHour(i, z);
        updateHeaderAmPm();
        if (i2 != 1) {
            this.mRadialTimePickerView.setCurrentHour(i);
            this.mRadialTimePickerView.setAmOrPm(i < 12 ? 0 : 1);
        }
        if (i2 != 2) {
            updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (z2) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getHour() {
        int currentHour = this.mRadialTimePickerView.getCurrentHour();
        if (this.mIs24Hour) {
            return currentHour;
        }
        if (this.mRadialTimePickerView.getAmOrPm() == 1) {
            return (currentHour % 12) + 12;
        }
        return currentHour % 12;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setMinute(int i) {
        setMinuteInternal(i, 0, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMinuteInternal(int i, int i2, boolean z) {
        if (this.mCurrentMinute == i) {
            return;
        }
        resetAutofilledValue();
        this.mCurrentMinute = i;
        updateHeaderMinute(i, true);
        if (i2 != 1) {
            this.mRadialTimePickerView.setCurrentMinute(i);
        }
        if (i2 != 2) {
            updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getMinute() {
        return this.mRadialTimePickerView.getCurrentMinute();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setIs24Hour(boolean z) {
        if (this.mIs24Hour != z) {
            this.mIs24Hour = z;
            this.mCurrentHour = getHour();
            updateHourFormat();
            updateUI(this.mRadialTimePickerView.getCurrentItemShowing());
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean is24Hour() {
        return this.mIs24Hour;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setEnabled(boolean z) {
        this.mHourView.setEnabled(z);
        this.mMinuteView.setEnabled(z);
        this.mAmLabel.setEnabled(z);
        this.mPmLabel.setEnabled(z);
        this.mRadialTimePickerView.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, getHour(), getMinute(), is24Hour(), getCurrentItemShowing());
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof TimePicker.AbstractTimePickerDelegate.SavedState) {
            TimePicker.AbstractTimePickerDelegate.SavedState savedState = (TimePicker.AbstractTimePickerDelegate.SavedState) parcelable;
            initialize(savedState.getHour(), savedState.getMinute(), savedState.is24HourMode(), savedState.getCurrentItemShowing());
            this.mRadialTimePickerView.invalidate();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i = this.mIs24Hour ? 129 : 65;
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        String formatDateTime = DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i);
        String str = this.mRadialTimePickerView.getCurrentItemShowing() == 0 ? this.mSelectHours : this.mSelectMinutes;
        accessibilityEvent.getText().add(formatDateTime + " " + str);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getHourView() {
        return this.mHourView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getMinuteView() {
        return this.mMinuteView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getAmView() {
        return this.mAmLabel;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public View getPmView() {
        return this.mPmLabel;
    }

    private int getCurrentItemShowing() {
        return this.mRadialTimePickerView.getCurrentItemShowing();
    }

    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryVibrate() {
        this.mDelegator.performHapticFeedback(4);
    }

    private void updateAmPmLabelStates(int i) {
        boolean z = i == 0;
        this.mAmLabel.setActivated(z);
        this.mAmLabel.setChecked(z);
        boolean z2 = i == 1;
        this.mPmLabel.setActivated(z2);
        this.mPmLabel.setChecked(z2);
    }

    private int getLocalizedHour(int i) {
        boolean z = this.mIs24Hour;
        if (!z) {
            i %= 12;
        }
        return (this.mHourFormatStartsAtZero || i != 0) ? i : z ? 24 : 12;
    }

    private void updateHeaderHour(int i, boolean z) {
        this.mHourView.setValue(getLocalizedHour(i));
    }

    private void updateHeaderMinute(int i, boolean z) {
        this.mMinuteView.setValue(i);
    }

    private void updateHeaderSeparator() {
        String hourMinSeparatorFromPattern = getHourMinSeparatorFromPattern(DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24Hour ? "Hm" : "hm"));
        this.mSeparatorView.lambda$setTextAsync$0(hourMinSeparatorFromPattern);
        this.mTextInputPickerView.updateSeparator(hourMinSeparatorFromPattern);
    }

    private static String getHourMinSeparatorFromPattern(String str) {
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                if (charAt == '\'') {
                    if (z) {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str.substring(i));
                        return spannableStringBuilder.subSequence(0, DateFormat.appendQuotedText(spannableStringBuilder, 0)).toString();
                    }
                } else if (charAt == 'H' || charAt == 'K' || charAt == 'h' || charAt == 'k') {
                    z = true;
                } else if (z) {
                    return Character.toString(str.charAt(i));
                }
            }
        }
        return ":";
    }

    private static int lastIndexOfAny(String str, char[] cArr) {
        if (cArr.length <= 0) {
            return -1;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            for (char c : cArr) {
                if (charAt == c) {
                    return length;
                }
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentItemShowing(int i, boolean z) {
        this.mRadialTimePickerView.setCurrentItemShowing(i, z);
        this.mHourView.setActivated(i == 0);
        this.mMinuteView.setActivated(i == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAmOrPm(int i) {
        updateAmPmLabelStates(i);
        if (this.mRadialTimePickerView.setAmOrPm(i)) {
            this.mCurrentHour = getHour();
            updateTextInputPicker();
            if (this.mOnTimeChangedListener != null) {
                this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
            }
        }
    }

    private static class NearestTouchDelegate implements View.OnTouchListener {
        private View mInitialTouchTarget;

        private NearestTouchDelegate() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                if (view instanceof ViewGroup) {
                    this.mInitialTouchTarget = findNearestChild((ViewGroup) view, (int) motionEvent.getX(), (int) motionEvent.getY());
                } else {
                    this.mInitialTouchTarget = null;
                }
            }
            View view2 = this.mInitialTouchTarget;
            if (view2 == null) {
                return false;
            }
            float scrollX = view.getScrollX() - view2.getLeft();
            float scrollY = view.getScrollY() - view2.getTop();
            motionEvent.offsetLocation(scrollX, scrollY);
            boolean dispatchTouchEvent = view2.dispatchTouchEvent(motionEvent);
            motionEvent.offsetLocation(-scrollX, -scrollY);
            if (actionMasked != 1 && actionMasked != 3) {
                return dispatchTouchEvent;
            }
            this.mInitialTouchTarget = null;
            return dispatchTouchEvent;
        }

        private View findNearestChild(ViewGroup viewGroup, int i, int i2) {
            int childCount = viewGroup.getChildCount();
            View view = null;
            int i3 = Integer.MAX_VALUE;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = viewGroup.getChildAt(i4);
                int left = i - (childAt.getLeft() + (childAt.getWidth() / 2));
                int top = i2 - (childAt.getTop() + (childAt.getHeight() / 2));
                int i5 = (left * left) + (top * top);
                if (i3 > i5) {
                    view = childAt;
                    i3 = i5;
                }
            }
            return view;
        }
    }
}
