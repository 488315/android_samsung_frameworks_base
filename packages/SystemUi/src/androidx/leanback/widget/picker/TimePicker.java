package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.picker.PickerUtility;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class TimePicker extends Picker {
    public PickerColumn mAmPmColumn;
    public int mColAmPmIndex;
    public int mColHourIndex;
    public int mColMinuteIndex;
    public final PickerUtility.TimeConstant mConstant;
    public int mCurrentAmPmIndex;
    public int mCurrentHour;
    public PickerColumn mHourColumn;
    public final boolean mIs24hFormat;
    public PickerColumn mMinuteColumn;
    public String mTimePickerFormat;

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.timePickerStyle);
    }

    public final String getBestHourMinutePattern() {
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mConstant.locale, this.mIs24hFormat ? "Hma" : "hma");
        return TextUtils.isEmpty(bestDateTimePattern) ? "h:mma" : bestDateTimePattern;
    }

    @Override // androidx.leanback.widget.picker.Picker
    public final void onColumnValueChanged(int i, int i2) {
        if (i == this.mColHourIndex) {
            this.mCurrentHour = i2;
        } else {
            if (i == this.mColMinuteIndex) {
                return;
            }
            if (i != this.mColAmPmIndex) {
                throw new IllegalArgumentException("Invalid column index.");
            }
            this.mCurrentAmPmIndex = i2;
        }
    }

    public TimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean z;
        PickerUtility.TimeConstant timeConstant = new PickerUtility.TimeConstant(Locale.getDefault(), context.getResources());
        this.mConstant = timeConstant;
        int[] iArr = R$styleable.lbTimePicker;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        try {
            boolean z2 = obtainStyledAttributes.getBoolean(0, DateFormat.is24HourFormat(context));
            this.mIs24hFormat = z2;
            boolean z3 = obtainStyledAttributes.getBoolean(3, true);
            obtainStyledAttributes.recycle();
            String bestHourMinutePattern = getBestHourMinutePattern();
            if (!TextUtils.equals(bestHourMinutePattern, this.mTimePickerFormat)) {
                this.mTimePickerFormat = bestHourMinutePattern;
                String bestHourMinutePattern2 = getBestHourMinutePattern();
                boolean z4 = TextUtils.getLayoutDirectionFromLocale(timeConstant.locale) == 1;
                boolean z5 = bestHourMinutePattern2.indexOf(97) < 0 || bestHourMinutePattern2.indexOf("a") > bestHourMinutePattern2.indexOf("m");
                String str = z4 ? "mh" : "hm";
                str = z2 ? str : z5 ? str.concat("a") : "a".concat(str);
                String bestHourMinutePattern3 = getBestHourMinutePattern();
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder();
                int i2 = 7;
                char[] cArr = {'H', 'h', 'K', 'k', 'm', 'M', 'a'};
                int i3 = 0;
                boolean z6 = false;
                char c = 0;
                while (i3 < bestHourMinutePattern3.length()) {
                    char charAt = bestHourMinutePattern3.charAt(i3);
                    if (charAt != ' ') {
                        if (charAt != '\'') {
                            if (z6) {
                                sb.append(charAt);
                            } else {
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= i2) {
                                        z = false;
                                        break;
                                    } else if (charAt == cArr[i4]) {
                                        z = true;
                                        break;
                                    } else {
                                        i4++;
                                        i2 = 7;
                                    }
                                }
                                if (!z) {
                                    sb.append(charAt);
                                } else if (charAt != c) {
                                    arrayList.add(sb.toString());
                                    sb.setLength(0);
                                }
                            }
                            c = charAt;
                        } else if (z6) {
                            z6 = false;
                        } else {
                            sb.setLength(0);
                            z6 = true;
                        }
                    }
                    i3++;
                    i2 = 7;
                    z6 = z6;
                }
                arrayList.add(sb.toString());
                if (arrayList.size() != str.length() + 1) {
                    throw new IllegalStateException("Separators size: " + arrayList.size() + " must equal the size of timeFieldsPattern: " + str.length() + " + 1");
                }
                ((ArrayList) this.mSeparators).clear();
                ((ArrayList) this.mSeparators).addAll(arrayList);
                String upperCase = str.toUpperCase(this.mConstant.locale);
                this.mAmPmColumn = null;
                this.mMinuteColumn = null;
                this.mHourColumn = null;
                this.mColAmPmIndex = -1;
                this.mColMinuteIndex = -1;
                this.mColHourIndex = -1;
                ArrayList arrayList2 = new ArrayList(3);
                for (int i5 = 0; i5 < upperCase.length(); i5++) {
                    char charAt2 = upperCase.charAt(i5);
                    if (charAt2 == 'A') {
                        PickerColumn pickerColumn = new PickerColumn();
                        this.mAmPmColumn = pickerColumn;
                        arrayList2.add(pickerColumn);
                        PickerColumn pickerColumn2 = this.mAmPmColumn;
                        pickerColumn2.mStaticLabels = this.mConstant.ampm;
                        this.mColAmPmIndex = i5;
                        if (pickerColumn2.mMinValue != 0) {
                            pickerColumn2.mMinValue = 0;
                        }
                        if (1 != pickerColumn2.mMaxValue) {
                            pickerColumn2.mMaxValue = 1;
                        }
                    } else if (charAt2 == 'H') {
                        PickerColumn pickerColumn3 = new PickerColumn();
                        this.mHourColumn = pickerColumn3;
                        arrayList2.add(pickerColumn3);
                        this.mHourColumn.mStaticLabels = this.mConstant.hours24;
                        this.mColHourIndex = i5;
                    } else {
                        if (charAt2 != 'M') {
                            throw new IllegalArgumentException("Invalid time picker format.");
                        }
                        PickerColumn pickerColumn4 = new PickerColumn();
                        this.mMinuteColumn = pickerColumn4;
                        arrayList2.add(pickerColumn4);
                        this.mMinuteColumn.mStaticLabels = this.mConstant.minutes;
                        this.mColMinuteIndex = i5;
                    }
                }
                setColumns(arrayList2);
            }
            PickerColumn pickerColumn5 = this.mHourColumn;
            boolean z7 = this.mIs24hFormat;
            int i6 = !z7 ? 1 : 0;
            if (i6 != pickerColumn5.mMinValue) {
                pickerColumn5.mMinValue = i6;
            }
            int i7 = z7 ? 23 : 12;
            if (i7 != pickerColumn5.mMaxValue) {
                pickerColumn5.mMaxValue = i7;
            }
            PickerColumn pickerColumn6 = this.mMinuteColumn;
            if (pickerColumn6.mMinValue != 0) {
                pickerColumn6.mMinValue = 0;
            }
            if (59 != pickerColumn6.mMaxValue) {
                pickerColumn6.mMaxValue = 59;
            }
            PickerColumn pickerColumn7 = this.mAmPmColumn;
            if (pickerColumn7 != null) {
                if (pickerColumn7.mMinValue != 0) {
                    pickerColumn7.mMinValue = 0;
                }
                if (1 != pickerColumn7.mMaxValue) {
                    pickerColumn7.mMaxValue = 1;
                }
            }
            if (z3) {
                Calendar calendarForLocale = PickerUtility.getCalendarForLocale(null, this.mConstant.locale);
                int i8 = calendarForLocale.get(11);
                if (i8 < 0 || i8 > 23) {
                    throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("hour: ", i8, " is not in [0-23] range in"));
                }
                this.mCurrentHour = i8;
                boolean z8 = this.mIs24hFormat;
                if (!z8) {
                    if (i8 >= 12) {
                        this.mCurrentAmPmIndex = 1;
                        if (i8 > 12) {
                            this.mCurrentHour = i8 - 12;
                        }
                    } else {
                        this.mCurrentAmPmIndex = 0;
                        if (i8 == 0) {
                            this.mCurrentHour = 12;
                        }
                    }
                    if (!z8) {
                        setColumnValue(this.mColAmPmIndex, this.mCurrentAmPmIndex, false);
                    }
                }
                setColumnValue(this.mColHourIndex, this.mCurrentHour, false);
                int i9 = calendarForLocale.get(12);
                if (i9 < 0 || i9 > 59) {
                    throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("minute: ", i9, " is not in [0-59] range."));
                }
                setColumnValue(this.mColMinuteIndex, i9, false);
                if (this.mIs24hFormat) {
                    return;
                }
                setColumnValue(this.mColAmPmIndex, this.mCurrentAmPmIndex, false);
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
