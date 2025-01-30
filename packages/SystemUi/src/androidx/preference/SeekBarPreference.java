package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.Preference;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeekBarPreference extends Preference {
    public final boolean mAdjustable;
    public int mMax;
    public int mMin;
    public SeslSeekBar mSeekBar;
    public final C04201 mSeekBarChangeListener;
    public int mSeekBarIncrement;
    public final ViewOnKeyListenerC04212 mSeekBarKeyListener;
    public int mSeekBarValue;
    public boolean mTrackingTouch;
    public final boolean mUpdatesContinuously;

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.preference.SeekBarPreference$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.preference.SeekBarPreference$2] */
    public SeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSeekBarChangeListener = new SeslSeekBar.OnSeekBarChangeListener() { // from class: androidx.preference.SeekBarPreference.1
            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeslSeekBar seslSeekBar, int i3, boolean z) {
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                if (z && (seekBarPreference.mUpdatesContinuously || !seekBarPreference.mTrackingTouch)) {
                    SeekBarPreference.access$000(seekBarPreference, seslSeekBar);
                }
                seekBarPreference.getClass();
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                seekBarPreference.mTrackingTouch = true;
                seekBarPreference.getClass();
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                seekBarPreference.mTrackingTouch = false;
                if (seslSeekBar.getProgress() + seekBarPreference.mMin != seekBarPreference.mSeekBarValue) {
                    SeekBarPreference.access$000(seekBarPreference, seslSeekBar);
                }
            }
        };
        this.mSeekBarKeyListener = new View.OnKeyListener() { // from class: androidx.preference.SeekBarPreference.2
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i3, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                if ((!seekBarPreference.mAdjustable && (i3 == 21 || i3 == 22)) || i3 == 23 || i3 == 66) {
                    return false;
                }
                SeslSeekBar seslSeekBar = seekBarPreference.mSeekBar;
                if (seslSeekBar != null) {
                    return seslSeekBar.onKeyDown(i3, keyEvent);
                }
                Log.e("SeekBarPreference", "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarPreference, i, i2);
        this.mMin = obtainStyledAttributes.getInt(3, 0);
        int i3 = obtainStyledAttributes.getInt(1, 100);
        int i4 = this.mMin;
        i3 = i3 < i4 ? i4 : i3;
        if (i3 != this.mMax) {
            this.mMax = i3;
            notifyChanged();
        }
        int i5 = obtainStyledAttributes.getInt(4, 0);
        if (i5 != this.mSeekBarIncrement) {
            this.mSeekBarIncrement = Math.min(this.mMax - this.mMin, Math.abs(i5));
            notifyChanged();
        }
        this.mAdjustable = obtainStyledAttributes.getBoolean(2, true);
        obtainStyledAttributes.getBoolean(5, false);
        this.mUpdatesContinuously = obtainStyledAttributes.getBoolean(6, false);
        obtainStyledAttributes.recycle();
    }

    public static void access$000(SeekBarPreference seekBarPreference, SeslSeekBar seslSeekBar) {
        int progress = seslSeekBar.getProgress() + seekBarPreference.mMin;
        if (progress != seekBarPreference.mSeekBarValue) {
            if (seekBarPreference.callChangeListener(Integer.valueOf(progress))) {
                seekBarPreference.setValueInternal(progress, false);
            } else {
                seslSeekBar.setProgress(seekBarPreference.mSeekBarValue - seekBarPreference.mMin);
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSeekBarKeyListener);
        SeslSeekBar seslSeekBar = (SeslSeekBar) preferenceViewHolder.findViewById(R.id.seekbar);
        this.mSeekBar = seslSeekBar;
        if (seslSeekBar == null) {
            Log.e("SeekBarPreference", "SeekBar view is null in onBindViewHolder.");
            return;
        }
        seslSeekBar.mOnSeekBarChangeListener = this.mSeekBarChangeListener;
        seslSeekBar.setMax(this.mMax - this.mMin);
        int i = this.mSeekBarIncrement;
        if (i != 0) {
            SeslSeekBar seslSeekBar2 = this.mSeekBar;
            seslSeekBar2.getClass();
            if (i < 0) {
                i = -i;
            }
            seslSeekBar2.mKeyProgressIncrement = i;
        } else {
            this.mSeekBarIncrement = this.mSeekBar.mKeyProgressIncrement;
        }
        this.mSeekBar.setProgress(this.mSeekBarValue - this.mMin);
        this.mSeekBar.setEnabled(isEnabled());
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSeekBarValue = savedState.mSeekBarValue;
        this.mMin = savedState.mMin;
        this.mMax = savedState.mMax;
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.mPersistent) {
            return absSavedState;
        }
        SavedState savedState = new SavedState(absSavedState);
        savedState.mSeekBarValue = this.mSeekBarValue;
        savedState.mMin = this.mMin;
        savedState.mMax = this.mMax;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        int intValue = ((Integer) obj).intValue();
        if (shouldPersist()) {
            intValue = this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, intValue);
        }
        setValueInternal(intValue, true);
    }

    public final void setValueInternal(int i, boolean z) {
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.mMax;
        if (i > i3) {
            i = i3;
        }
        if (i != this.mSeekBarValue) {
            this.mSeekBarValue = i;
            if (shouldPersist()) {
                int i4 = ~i;
                if (shouldPersist()) {
                    i4 = this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, i4);
                }
                if (i != i4) {
                    SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
                    editor.putInt(this.mKey, i);
                    if (!this.mPreferenceManager.mNoCommit) {
                        editor.apply();
                    }
                }
            }
            if (z) {
                notifyChanged();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.preference.SeekBarPreference.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int mMax;
        public int mMin;
        public int mSeekBarValue;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mSeekBarValue = parcel.readInt();
            this.mMin = parcel.readInt();
            this.mMax = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSeekBarValue);
            parcel.writeInt(this.mMin);
            parcel.writeInt(this.mMax);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }
}
