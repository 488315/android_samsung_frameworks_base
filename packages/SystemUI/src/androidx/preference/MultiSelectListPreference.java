package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import com.android.systemui.R;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MultiSelectListPreference extends DialogPreference {
    public final CharSequence[] mEntries;
    public final CharSequence[] mEntryValues;
    public final Set mValues;

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mValues = new HashSet();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MultiSelectListPreference, i, i2);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(2);
        this.mEntries = textArray == null ? obtainStyledAttributes.getTextArray(0) : textArray;
        CharSequence[] textArray2 = obtainStyledAttributes.getTextArray(3);
        this.mEntryValues = textArray2 == null ? obtainStyledAttributes.getTextArray(1) : textArray2;
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        CharSequence[] textArray = typedArray.getTextArray(i);
        HashSet hashSet = new HashSet();
        for (CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValues(savedState.mValues);
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.mPersistent) {
            return absSavedState;
        }
        SavedState savedState = new SavedState(absSavedState);
        savedState.mValues = this.mValues;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj) {
        Set<String> set = (Set) obj;
        if (shouldPersist()) {
            set = this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
        }
        setValues(set);
    }

    public final void setValues(Set set) {
        ((HashSet) this.mValues).clear();
        this.mValues.addAll(set);
        if (shouldPersist()) {
            if (!set.equals(shouldPersist() ? this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, null) : null)) {
                SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
                editor.putStringSet(this.mKey, set);
                if (!this.mPreferenceManager.mNoCommit) {
                    editor.apply();
                }
            }
        }
        notifyChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.preference.MultiSelectListPreference.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Set mValues;

        public SavedState(Parcel parcel) {
            super(parcel);
            int readInt = parcel.readInt();
            this.mValues = new HashSet();
            String[] strArr = new String[readInt];
            parcel.readStringArray(strArr);
            Collections.addAll(this.mValues, strArr);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mValues.size());
            Set set = this.mValues;
            parcel.writeStringArray((String[]) set.toArray(new String[set.size()]));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, android.R.attr.dialogPreferenceStyle));
    }

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }
}
