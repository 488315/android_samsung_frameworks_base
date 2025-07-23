package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.GridLayoutManager;
import androidx.leanback.widget.VerticalGridView;
import androidx.leanback.widget.picker.Picker;
import androidx.leanback.widget.picker.PickerUtility;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DatePicker extends Picker {
    public static final int[] DATE_FIELDS = {5, 2, 1};
    public final int mColDayIndex;
    public final int mColMonthIndex;
    public final int mColYearIndex;
    public final PickerUtility.DateConstant mConstant;
    public final Calendar mCurrentDate;
    public final DateFormat mDateFormat;
    public final String mDatePickerFormat;
    public final PickerColumn mDayColumn;
    public final Calendar mMaxDate;
    public final Calendar mMinDate;
    public final PickerColumn mMonthColumn;
    public final Calendar mTempDate;
    public final PickerColumn mYearColumn;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.leanback.widget.picker.DatePicker$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public final /* synthetic */ boolean val$animation;

        public AnonymousClass1(boolean z) {
            this.val$animation = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z;
            boolean z2;
            DatePicker datePicker = DatePicker.this;
            boolean z3 = this.val$animation;
            int[] iArr = {datePicker.mColDayIndex, datePicker.mColMonthIndex, datePicker.mColYearIndex};
            boolean z4 = true;
            boolean z5 = true;
            for (int i = 2; i >= 0; i--) {
                int i2 = iArr[i];
                if (i2 >= 0) {
                    int i3 = DatePicker.DATE_FIELDS[i];
                    ArrayList arrayList = datePicker.mColumns;
                    PickerColumn pickerColumn = arrayList == null ? null : (PickerColumn) arrayList.get(i2);
                    if (z4) {
                        int i4 = datePicker.mMinDate.get(i3);
                        if (i4 != pickerColumn.mMinValue) {
                            pickerColumn.mMinValue = i4;
                            z = true;
                        }
                        z = false;
                    } else {
                        int actualMinimum = datePicker.mCurrentDate.getActualMinimum(i3);
                        if (actualMinimum != pickerColumn.mMinValue) {
                            pickerColumn.mMinValue = actualMinimum;
                            z = true;
                        }
                        z = false;
                    }
                    if (z5) {
                        int i5 = datePicker.mMaxDate.get(i3);
                        if (i5 != pickerColumn.mMaxValue) {
                            pickerColumn.mMaxValue = i5;
                            z2 = true;
                        }
                        z2 = false;
                    } else {
                        int actualMaximum = datePicker.mCurrentDate.getActualMaximum(i3);
                        if (actualMaximum != pickerColumn.mMaxValue) {
                            pickerColumn.mMaxValue = actualMaximum;
                            z2 = true;
                        }
                        z2 = false;
                    }
                    boolean z6 = z | z2;
                    z4 &= datePicker.mCurrentDate.get(i3) == datePicker.mMinDate.get(i3);
                    z5 &= datePicker.mCurrentDate.get(i3) == datePicker.mMaxDate.get(i3);
                    if (z6) {
                        datePicker.setColumnAt(iArr[i], pickerColumn);
                    }
                    int i6 = iArr[i];
                    int i7 = datePicker.mCurrentDate.get(i3);
                    PickerColumn pickerColumn2 = (PickerColumn) datePicker.mColumns.get(i6);
                    if (pickerColumn2.mCurrentValue != i7) {
                        pickerColumn2.mCurrentValue = i7;
                        VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) datePicker.mColumnViews).get(i6);
                        if (verticalGridView != null) {
                            int i8 = i7 - ((PickerColumn) datePicker.mColumns.get(i6)).mMinValue;
                            if (z3) {
                                verticalGridView.mLayoutManager.setSelection(i8, true);
                            } else {
                                verticalGridView.mLayoutManager.setSelection(i8, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.datePickerStyle);
    }

    @Override // androidx.leanback.widget.picker.Picker
    public final void onColumnValueChanged(int i, int i2) {
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        ArrayList arrayList = this.mColumns;
        int i3 = (arrayList == null ? null : (PickerColumn) arrayList.get(i)).mCurrentValue;
        if (i == this.mColDayIndex) {
            this.mTempDate.add(5, i2 - i3);
        } else if (i == this.mColMonthIndex) {
            this.mTempDate.add(2, i2 - i3);
        } else {
            if (i != this.mColYearIndex) {
                throw new IllegalArgumentException();
            }
            this.mTempDate.add(1, i2 - i3);
        }
        int i4 = this.mTempDate.get(1);
        int i5 = this.mTempDate.get(2);
        int i6 = this.mTempDate.get(5);
        if (this.mCurrentDate.get(1) == i4 && this.mCurrentDate.get(2) == i6 && this.mCurrentDate.get(5) == i5) {
            return;
        }
        this.mCurrentDate.set(i4, i5, i6);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
        post(new AnonymousClass1(false));
    }

    public final boolean parseDate(String str, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (ParseException unused) {
            Log.w("DatePicker", "Date: " + str + " not in format: MM/dd/yyyy");
            return false;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2 = 6;
        int i3 = 1;
        this.mDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        PickerUtility.DateConstant dateConstant = new PickerUtility.DateConstant(Locale.getDefault(), getContext().getResources());
        this.mConstant = dateConstant;
        this.mTempDate = PickerUtility.getCalendarForLocale(this.mTempDate, dateConstant.locale);
        this.mMinDate = PickerUtility.getCalendarForLocale(this.mMinDate, this.mConstant.locale);
        this.mMaxDate = PickerUtility.getCalendarForLocale(this.mMaxDate, this.mConstant.locale);
        this.mCurrentDate = PickerUtility.getCalendarForLocale(this.mCurrentDate, this.mConstant.locale);
        PickerColumn pickerColumn = this.mMonthColumn;
        if (pickerColumn != null) {
            pickerColumn.mStaticLabels = this.mConstant.months;
            setColumnAt(this.mColMonthIndex, pickerColumn);
        }
        int[] iArr = R$styleable.lbDatePicker;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        try {
            String string = obtainStyledAttributes.getString(0);
            String string2 = obtainStyledAttributes.getString(1);
            String string3 = obtainStyledAttributes.getString(2);
            obtainStyledAttributes.recycle();
            this.mTempDate.clear();
            if (TextUtils.isEmpty(string)) {
                this.mTempDate.set(1900, 0, 1);
            } else if (!parseDate(string, this.mTempDate)) {
                this.mTempDate.set(1900, 0, 1);
            }
            this.mMinDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            this.mTempDate.clear();
            if (TextUtils.isEmpty(string2)) {
                this.mTempDate.set(2100, 0, 1);
            } else if (!parseDate(string2, this.mTempDate)) {
                this.mTempDate.set(2100, 0, 1);
            }
            this.mMaxDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            string3 = TextUtils.isEmpty(string3) ? new String(android.text.format.DateFormat.getDateFormatOrder(context)) : string3;
            string3 = TextUtils.isEmpty(string3) ? new String(android.text.format.DateFormat.getDateFormatOrder(getContext())) : string3;
            if (TextUtils.equals(this.mDatePickerFormat, string3)) {
                return;
            }
            this.mDatePickerFormat = string3;
            String bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(this.mConstant.locale, string3);
            String str = TextUtils.isEmpty(bestDateTimePattern) ? "MM/dd/yyyy" : bestDateTimePattern;
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            char[] cArr = {'Y', 'y', 'M', 'm', 'D', 'd'};
            int i4 = 0;
            int i5 = 0;
            char c = 0;
            while (i4 < str.length()) {
                char charAt = str.charAt(i4);
                int i6 = i3;
                if (charAt != ' ') {
                    if (charAt != '\'') {
                        if (i5 == 0) {
                            int i7 = 0;
                            while (true) {
                                if (i7 >= i2) {
                                    sb.append(charAt);
                                    break;
                                } else if (charAt != cArr[i7]) {
                                    i7++;
                                    i2 = 6;
                                } else if (charAt != c) {
                                    arrayList.add(sb.toString());
                                    sb.setLength(0);
                                }
                            }
                        } else {
                            sb.append(charAt);
                        }
                        c = charAt;
                    } else if (i5 == 0) {
                        sb.setLength(0);
                        i5 = i6;
                    } else {
                        i5 = 0;
                    }
                }
                i4++;
                i3 = i6;
                i2 = 6;
            }
            int i8 = i3;
            arrayList.add(sb.toString());
            if (arrayList.size() != string3.length() + 1) {
                throw new IllegalStateException("Separators size: " + arrayList.size() + " must equal the size of datePickerFormat: " + string3.length() + " + 1");
            }
            ((ArrayList) this.mSeparators).clear();
            ((ArrayList) this.mSeparators).addAll(arrayList);
            this.mDayColumn = null;
            this.mMonthColumn = null;
            this.mYearColumn = null;
            this.mColMonthIndex = -1;
            this.mColDayIndex = -1;
            this.mColYearIndex = -1;
            String upperCase = string3.toUpperCase(this.mConstant.locale);
            ArrayList arrayList2 = new ArrayList(3);
            for (int i9 = 0; i9 < upperCase.length(); i9++) {
                char charAt2 = upperCase.charAt(i9);
                if (charAt2 == 'D') {
                    if (this.mDayColumn != null) {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                    PickerColumn pickerColumn2 = new PickerColumn();
                    this.mDayColumn = pickerColumn2;
                    arrayList2.add(pickerColumn2);
                    this.mDayColumn.mLabelFormat = "%02d";
                    this.mColDayIndex = i9;
                } else if (charAt2 != 'M') {
                    if (charAt2 != 'Y') {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                    if (this.mYearColumn != null) {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                    PickerColumn pickerColumn3 = new PickerColumn();
                    this.mYearColumn = pickerColumn3;
                    arrayList2.add(pickerColumn3);
                    this.mColYearIndex = i9;
                    this.mYearColumn.mLabelFormat = "%d";
                } else {
                    if (this.mMonthColumn != null) {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                    PickerColumn pickerColumn4 = new PickerColumn();
                    this.mMonthColumn = pickerColumn4;
                    arrayList2.add(pickerColumn4);
                    this.mMonthColumn.mStaticLabels = this.mConstant.months;
                    this.mColMonthIndex = i9;
                }
            }
            if (((ArrayList) this.mSeparators).size() == 0) {
                throw new IllegalStateException("Separators size is: " + ((ArrayList) this.mSeparators).size() + ". At least one separator must be provided");
            }
            if (((ArrayList) this.mSeparators).size() == i8) {
                CharSequence charSequence = (CharSequence) ((ArrayList) this.mSeparators).get(0);
                ((ArrayList) this.mSeparators).clear();
                ((ArrayList) this.mSeparators).add("");
                for (int i10 = 0; i10 < arrayList2.size() - 1; i10++) {
                    ((ArrayList) this.mSeparators).add(charSequence);
                }
                ((ArrayList) this.mSeparators).add("");
            } else if (((ArrayList) this.mSeparators).size() != arrayList2.size() + 1) {
                throw new IllegalStateException("Separators size: " + ((ArrayList) this.mSeparators).size() + " mustequal the size of columns: " + arrayList2.size() + " + 1");
            }
            ((ArrayList) this.mColumnViews).clear();
            this.mPickerView.removeAllViews();
            this.mColumns = new ArrayList(arrayList2);
            if (this.mSelectedColumn > r3.size() - 1) {
                this.mSelectedColumn = this.mColumns.size() - 1;
            }
            LayoutInflater from = LayoutInflater.from(getContext());
            ArrayList arrayList3 = this.mColumns;
            int size = arrayList3 == null ? 0 : arrayList3.size();
            if (!TextUtils.isEmpty((CharSequence) ((ArrayList) this.mSeparators).get(0))) {
                TextView textView = (TextView) from.inflate(R.layout.lb_picker_separator, this.mPickerView, false);
                textView.setText((CharSequence) ((ArrayList) this.mSeparators).get(0));
                this.mPickerView.addView(textView);
            }
            int i11 = 0;
            while (i11 < size) {
                VerticalGridView verticalGridView = (VerticalGridView) from.inflate(R.layout.lb_picker_column, this.mPickerView, false);
                updateColumnSize(verticalGridView);
                verticalGridView.mLayoutManager.mWindowAlignment.mMainAxis.mWindowAlignment = 0;
                verticalGridView.requestLayout();
                verticalGridView.mHasFixedSize = false;
                verticalGridView.setFocusable(isActivated());
                RecyclerView.Recycler recycler = verticalGridView.mRecycler;
                recycler.mRequestedCacheMax = 0;
                recycler.updateViewCacheSize();
                ((ArrayList) this.mColumnViews).add(verticalGridView);
                this.mPickerView.addView(verticalGridView);
                int i12 = i11 + 1;
                if (!TextUtils.isEmpty((CharSequence) ((ArrayList) this.mSeparators).get(i12))) {
                    TextView textView2 = (TextView) from.inflate(R.layout.lb_picker_separator, this.mPickerView, false);
                    textView2.setText((CharSequence) ((ArrayList) this.mSeparators).get(i12));
                    this.mPickerView.addView(textView2);
                }
                verticalGridView.setAdapter(new Picker.PickerScrollArrayAdapter(this.mPickerItemLayoutId, this.mPickerItemTextViewId, i11));
                Picker.AnonymousClass1 anonymousClass1 = this.mColumnChangeListener;
                GridLayoutManager gridLayoutManager = verticalGridView.mLayoutManager;
                if (anonymousClass1 == null) {
                    gridLayoutManager.mChildViewHolderSelectedListeners = null;
                } else {
                    ArrayList arrayList4 = gridLayoutManager.mChildViewHolderSelectedListeners;
                    if (arrayList4 == null) {
                        gridLayoutManager.mChildViewHolderSelectedListeners = new ArrayList();
                    } else {
                        arrayList4.clear();
                    }
                    gridLayoutManager.mChildViewHolderSelectedListeners.add(anonymousClass1);
                }
                i11 = i12;
            }
            post(new AnonymousClass1(false));
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
