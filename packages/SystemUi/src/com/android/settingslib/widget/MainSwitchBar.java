package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MainSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Drawable mBackgroundDisabled;
    public final Drawable mBackgroundOff;
    public final Drawable mBackgroundOn;
    public final View mFrameView;
    public final Switch mSwitch;
    public final List mSwitchChangeListeners;
    public final TextView mTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.android.settingslib.widget.MainSwitchBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, 0);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mChecked;
        public boolean mVisible;

        public /* synthetic */ SavedState(Parcel parcel, int i) {
            this(parcel);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MainSwitchBar.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" checked=");
            sb.append(this.mChecked);
            sb.append(" visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.mVisible, "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.mChecked));
            parcel.writeValue(Boolean.valueOf(this.mVisible));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mChecked = ((Boolean) parcel.readValue(null)).booleanValue();
            this.mVisible = ((Boolean) parcel.readValue(null)).booleanValue();
        }
    }

    public MainSwitchBar(Context context) {
        this(context, null);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        setBackground(z);
        int size = ((ArrayList) this.mSwitchChangeListeners).size();
        for (int i = 0; i < size; i++) {
            ((OnMainSwitchChangeListener) ((ArrayList) this.mSwitchChangeListeners).get(i)).onSwitchChanged(z);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setChecked(savedState.mChecked);
        boolean z = savedState.mChecked;
        Switch r1 = this.mSwitch;
        if (r1 != null) {
            r1.setChecked(z);
        }
        setBackground(z);
        setBackground(savedState.mChecked);
        setVisibility(savedState.mVisible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.mVisible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mChecked = this.mSwitch.isChecked();
        savedState.mVisible = getVisibility() == 0;
        return savedState;
    }

    @Override // android.view.View
    public final boolean performClick() {
        this.mSwitch.performClick();
        return super.performClick();
    }

    public final void setBackground(boolean z) {
        this.mFrameView.setBackground(z ? this.mBackgroundOn : this.mBackgroundOff);
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        if (z) {
            this.mFrameView.setBackground(this.mSwitch.isChecked() ? this.mBackgroundOn : this.mBackgroundOff);
        } else {
            this.mFrameView.setBackground(this.mBackgroundDisabled);
        }
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ArrayList arrayList = new ArrayList();
        this.mSwitchChangeListeners = arrayList;
        LayoutInflater.from(context).inflate(R.layout.settingslib_main_switch_bar, this);
        setFocusable(true);
        setClickable(true);
        this.mFrameView = findViewById(R.id.frame);
        TextView textView = (TextView) findViewById(R.id.switch_text);
        this.mTextView = textView;
        Switch r0 = (Switch) findViewById(android.R.id.switch_widget);
        this.mSwitch = r0;
        this.mBackgroundOn = getContext().getDrawable(R.drawable.settingslib_switch_bar_bg_on);
        this.mBackgroundOff = getContext().getDrawable(R.drawable.settingslib_switch_bar_bg_off);
        this.mBackgroundDisabled = getContext().getDrawable(R.drawable.settingslib_switch_bar_bg_disabled);
        OnMainSwitchChangeListener onMainSwitchChangeListener = new OnMainSwitchChangeListener() { // from class: com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0
            @Override // com.android.settingslib.widget.OnMainSwitchChangeListener
            public final void onSwitchChanged(boolean z) {
                int i3 = MainSwitchBar.$r8$clinit;
                MainSwitchBar mainSwitchBar = MainSwitchBar.this;
                Switch r02 = mainSwitchBar.mSwitch;
                if (r02 != null) {
                    r02.setChecked(z);
                }
                mainSwitchBar.setBackground(z);
            }
        };
        if (!arrayList.contains(onMainSwitchChangeListener)) {
            arrayList.add(onMainSwitchChangeListener);
        }
        if (r0.getVisibility() == 0) {
            r0.setOnCheckedChangeListener(this);
        }
        boolean isChecked = r0.isChecked();
        if (r0 != null) {
            r0.setChecked(isChecked);
        }
        setBackground(isChecked);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.preference.R$styleable.Preference, 0, 0);
            CharSequence text = obtainStyledAttributes.getText(4);
            if (textView != null) {
                textView.setText(text);
            }
            obtainStyledAttributes.recycle();
        }
        setBackground(r0.isChecked());
    }
}
