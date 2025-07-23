package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;

@Deprecated
/* loaded from: classes3.dex */
public abstract class TwoStatePreference extends Preference {
    boolean mChecked;
    private boolean mCheckedSet;
    private boolean mDisableDependentsState;
    private CharSequence mSummaryOff;
    private CharSequence mSummaryOn;

    public TwoStatePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoStatePreference(Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onClick() {
        super.onClick();
        boolean z = !isChecked();
        if (callChangeListener(Boolean.valueOf(z))) {
            setChecked(z);
        }
    }

    public void setChecked(boolean z) {
        boolean z2 = this.mChecked != z;
        if (z2 || !this.mCheckedSet) {
            this.mChecked = z;
            this.mCheckedSet = true;
            persistBoolean(z);
            if (z2) {
                notifyDependencyChange(shouldDisableDependents());
                notifyChanged();
            }
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return (this.mDisableDependentsState ? this.mChecked : !this.mChecked) || super.shouldDisableDependents();
    }

    public void setSummaryOn(CharSequence charSequence) {
        this.mSummaryOn = charSequence;
        if (isChecked()) {
            notifyChanged();
        }
    }

    public void setSummaryOn(int i) {
        setSummaryOn(getContext().getString(i));
    }

    public CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public void setSummaryOff(CharSequence charSequence) {
        this.mSummaryOff = charSequence;
        if (isChecked()) {
            return;
        }
        notifyChanged();
    }

    public void setSummaryOff(int i) {
        setSummaryOff(getContext().getString(i));
    }

    public CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    public boolean getDisableDependentsState() {
        return this.mDisableDependentsState;
    }

    public void setDisableDependentsState(boolean z) {
        this.mDisableDependentsState = z;
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Boolean.valueOf(typedArray.getBoolean(i, false));
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        boolean booleanValue;
        if (z) {
            booleanValue = getPersistedBoolean(this.mChecked);
        } else {
            booleanValue = ((Boolean) obj).booleanValue();
        }
        setChecked(booleanValue);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void syncSummaryView(android.view.View r4) {
        /*
            r3 = this;
            r0 = 16908304(0x1020010, float:2.3877274E-38)
            android.view.View r4 = r4.findViewById(r0)
            android.widget.TextView r4 = (android.widget.TextView) r4
            if (r4 == 0) goto L50
            boolean r0 = r3.mChecked
            r1 = 0
            if (r0 == 0) goto L1f
            java.lang.CharSequence r0 = r3.mSummaryOn
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L1f
            java.lang.CharSequence r0 = r3.mSummaryOn
            r4.lambda$setTextAsync$0(r0)
        L1d:
            r0 = r1
            goto L32
        L1f:
            boolean r0 = r3.mChecked
            if (r0 != 0) goto L31
            java.lang.CharSequence r0 = r3.mSummaryOff
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L31
            java.lang.CharSequence r0 = r3.mSummaryOff
            r4.lambda$setTextAsync$0(r0)
            goto L1d
        L31:
            r0 = 1
        L32:
            if (r0 == 0) goto L42
            java.lang.CharSequence r3 = r3.getSummary()
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L42
            r4.lambda$setTextAsync$0(r3)
            r0 = r1
        L42:
            if (r0 != 0) goto L45
            goto L47
        L45:
            r1 = 8
        L47:
            int r3 = r4.getVisibility()
            if (r1 == r3) goto L50
            r4.setVisibility(r1)
        L50:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.TwoStatePreference.syncSummaryView(android.view.View):void");
    }

    @Override // android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.checked = isChecked();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.preference.TwoStatePreference.SavedState.1
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
        boolean checked;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.checked = parcel.readInt() == 1;
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
