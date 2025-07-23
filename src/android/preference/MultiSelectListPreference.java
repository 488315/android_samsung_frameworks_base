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
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* loaded from: classes3.dex */
public class MultiSelectListPreference extends DialogPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mNewValues;
    private boolean mPreferenceChanged;
    private Set<String> mValues;

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mValues = new HashSet();
        this.mNewValues = new HashSet();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MultiSelectListPreference, i, i2);
        this.mEntries = obtainStyledAttributes.getTextArray(0);
        this.mEntryValues = obtainStyledAttributes.getTextArray(1);
        obtainStyledAttributes.recycle();
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
    }

    public void setEntries(int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntryValues(CharSequence[] charSequenceArr) {
        this.mEntryValues = charSequenceArr;
    }

    public void setEntryValues(int i) {
        setEntryValues(getContext().getResources().getTextArray(i));
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public void setValues(Set<String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        persistStringSet(set);
    }

    public Set<String> getValues() {
        return this.mValues;
    }

    public int findIndexOfValue(String str) {
        CharSequence[] charSequenceArr;
        if (str == null || (charSequenceArr = this.mEntryValues) == null) {
            return -1;
        }
        for (int length = charSequenceArr.length - 1; length >= 0; length--) {
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
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        builder.setMultiChoiceItems(this.mEntries, getSelectedItems(), new DialogInterface.OnMultiChoiceClickListener() { // from class: android.preference.MultiSelectListPreference.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                if (z) {
                    MultiSelectListPreference multiSelectListPreference = MultiSelectListPreference.this;
                    multiSelectListPreference.mPreferenceChanged = MultiSelectListPreference.this.mNewValues.add(MultiSelectListPreference.this.mEntryValues[i].toString()) | multiSelectListPreference.mPreferenceChanged;
                } else {
                    MultiSelectListPreference multiSelectListPreference2 = MultiSelectListPreference.this;
                    multiSelectListPreference2.mPreferenceChanged = MultiSelectListPreference.this.mNewValues.remove(MultiSelectListPreference.this.mEntryValues[i].toString()) | multiSelectListPreference2.mPreferenceChanged;
                }
            }
        });
        this.mNewValues.clear();
        this.mNewValues.addAll(this.mValues);
    }

    private boolean[] getSelectedItems() {
        CharSequence[] charSequenceArr = this.mEntryValues;
        int length = charSequenceArr.length;
        Set<String> set = this.mValues;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = set.contains(charSequenceArr[i].toString());
        }
        return zArr;
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z && this.mPreferenceChanged) {
            Set<String> set = this.mNewValues;
            if (callChangeListener(set)) {
                setValues(set);
            }
        }
        this.mPreferenceChanged = false;
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        CharSequence[] textArray = typedArray.getTextArray(i);
        HashSet hashSet = new HashSet();
        for (CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        setValues(z ? getPersistedStringSet(this.mValues) : (Set) obj);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.values = getValues();
        return savedState;
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.preference.MultiSelectListPreference.SavedState.1
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
        Set<String> values;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.values = new HashSet();
            for (String str : parcel.readStringArray()) {
                this.values.add(str);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeStringArray((String[]) this.values.toArray(new String[0]));
        }
    }
}
