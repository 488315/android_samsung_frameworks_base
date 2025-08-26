package android.preference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import com.android.internal.R;
import java.util.Arrays;

@Deprecated
/* loaded from: classes3.dex */
public class MultiCheckPreference extends DialogPreference {
    private CharSequence[] mEntries;
    private String[] mEntryValues;
    private boolean[] mOrigValues;
    private boolean[] mSetValues;
    private String mSummary;

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPreference, i, i2);
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(0);
        this.mEntries = textArray;
        if (textArray != null) {
            setEntries(textArray);
        }
        setEntryValuesCS(typedArrayObtainStyledAttributes.getTextArray(1));
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.Preference, 0, 0);
        this.mSummary = typedArrayObtainStyledAttributes2.getString(7);
        typedArrayObtainStyledAttributes2.recycle();
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiCheckPreference(Context context) {
        this(context, null);
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        this.mSetValues = new boolean[charSequenceArr.length];
        this.mOrigValues = new boolean[charSequenceArr.length];
    }

    public void setEntries(int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntryValues(String[] strArr) {
        this.mEntryValues = strArr;
        Arrays.fill(this.mSetValues, false);
        Arrays.fill(this.mOrigValues, false);
    }

    public void setEntryValues(int i) {
        setEntryValuesCS(getContext().getResources().getTextArray(i));
    }

    private void setEntryValuesCS(CharSequence[] charSequenceArr) {
        setValues(null);
        if (charSequenceArr != null) {
            this.mEntryValues = new String[charSequenceArr.length];
            for (int i = 0; i < charSequenceArr.length; i++) {
                this.mEntryValues[i] = charSequenceArr[i].toString();
            }
        }
    }

    public String[] getEntryValues() {
        return this.mEntryValues;
    }

    public boolean getValue(int i) {
        return this.mSetValues[i];
    }

    public void setValue(int i, boolean z) {
        this.mSetValues[i] = z;
    }

    public void setValues(boolean[] zArr) {
        boolean[] zArr2 = this.mSetValues;
        if (zArr2 != null) {
            Arrays.fill(zArr2, false);
            Arrays.fill(this.mOrigValues, false);
            if (zArr != null) {
                boolean[] zArr3 = this.mSetValues;
                System.arraycopy(zArr, 0, zArr3, 0, zArr.length < zArr3.length ? zArr.length : zArr3.length);
            }
        }
    }

    @Override // android.preference.Preference
    public CharSequence getSummary() {
        String str = this.mSummary;
        return str == null ? super.getSummary() : str;
    }

    @Override // android.preference.Preference
    public void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        if (charSequence == null && this.mSummary != null) {
            this.mSummary = null;
        } else {
            if (charSequence == null || charSequence.equals(this.mSummary)) {
                return;
            }
            this.mSummary = charSequence.toString();
        }
    }

    public boolean[] getValues() {
        return this.mSetValues;
    }

    public int findIndexOfValue(String str) {
        String[] strArr;
        if (str == null || (strArr = this.mEntryValues) == null) {
            return -1;
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            if (this.mEntryValues[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    @Override // android.preference.DialogPreference
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries == null || this.mEntryValues == null) {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        boolean[] zArr = this.mSetValues;
        this.mOrigValues = Arrays.copyOf(zArr, zArr.length);
        builder.setMultiChoiceItems(this.mEntries, this.mSetValues, new DialogInterface.OnMultiChoiceClickListener() { // from class: android.preference.MultiCheckPreference.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                MultiCheckPreference.this.mSetValues[i] = z;
            }
        });
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z && callChangeListener(getValues())) {
            return;
        }
        boolean[] zArr = this.mOrigValues;
        boolean[] zArr2 = this.mSetValues;
        System.arraycopy(zArr, 0, zArr2, 0, zArr2.length);
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return parcelableOnSaveInstanceState;
        }
        SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
        savedState.values = getValues();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValues(savedState.values);
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.preference.MultiCheckPreference.SavedState.1
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
        boolean[] values;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.values = parcel.createBooleanArray();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBooleanArray(this.values);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
