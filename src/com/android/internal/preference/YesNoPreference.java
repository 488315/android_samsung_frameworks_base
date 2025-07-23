package com.android.internal.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;

/* loaded from: classes5.dex */
public class YesNoPreference extends DialogPreference {
    private boolean mWasPositiveResult;

    public YesNoPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public YesNoPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public YesNoPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842896);
    }

    public YesNoPreference(Context context) {
        this(context, null);
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (callChangeListener(Boolean.valueOf(z))) {
            setValue(z);
        }
    }

    public void setValue(boolean z) {
        this.mWasPositiveResult = z;
        persistBoolean(z);
        notifyDependencyChange(!z);
    }

    public boolean getValue() {
        return this.mWasPositiveResult;
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Boolean.valueOf(typedArray.getBoolean(i, false));
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        boolean booleanValue;
        if (z) {
            booleanValue = getPersistedBoolean(this.mWasPositiveResult);
        } else {
            booleanValue = ((Boolean) obj).booleanValue();
        }
        setValue(booleanValue);
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return !this.mWasPositiveResult || super.shouldDisableDependents();
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.wasPositiveResult = getValue();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.wasPositiveResult);
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.preference.YesNoPreference.SavedState.1
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
        boolean wasPositiveResult;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.wasPositiveResult = parcel.readInt() == 1;
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.wasPositiveResult ? 1 : 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
