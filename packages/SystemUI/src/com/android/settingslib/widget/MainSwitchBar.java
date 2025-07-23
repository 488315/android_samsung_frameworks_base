package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.preference.R$styleable;
import com.android.systemui.R;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MainSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final View mFrameView;
    public final TextView mSummaryView;
    public final CompoundButton mSwitch;
    public final List mSwitchChangeListeners;
    public final TextView mTextView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends View.BaseSavedState {
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
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mVisible, "}");
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
        this.mFrameView.setActivated(z);
        ArrayList arrayList = (ArrayList) this.mSwitchChangeListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((CompoundButton.OnCheckedChangeListener) obj).onCheckedChanged(this.mSwitch, z);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setChecked(savedState.mChecked);
        boolean z = savedState.mChecked;
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
        this.mFrameView.setActivated(z);
        this.mFrameView.setActivated(savedState.mChecked);
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

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        TextView textView;
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        this.mFrameView.setEnabled(z);
        this.mFrameView.setActivated(this.mSwitch.isChecked());
        if (!SettingsThemeHelper.isExpressiveTheme(getContext()) || (textView = this.mSummaryView) == null) {
            return;
        }
        textView.setEnabled(z);
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
        boolean isExpressiveTheme = SettingsThemeHelper.isExpressiveTheme(context);
        LayoutInflater.from(context).inflate(isExpressiveTheme ? R.layout.settingslib_expressive_main_switch_bar : R.layout.settingslib_main_switch_bar, this);
        setFocusable(true);
        setClickable(true);
        this.mFrameView = findViewById(R.id.frame);
        this.mTextView = (TextView) findViewById(R.id.switch_text);
        if (isExpressiveTheme) {
            this.mSummaryView = (TextView) findViewById(R.id.switch_summary);
        }
        CompoundButton compoundButton = (CompoundButton) findViewById(android.R.id.switch_widget);
        this.mSwitch = compoundButton;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                MainSwitchBar mainSwitchBar = MainSwitchBar.this;
                int i3 = MainSwitchBar.$r8$clinit;
                CompoundButton compoundButton3 = mainSwitchBar.mSwitch;
                if (compoundButton3 != null) {
                    compoundButton3.setChecked(z);
                }
                mainSwitchBar.mFrameView.setActivated(z);
            }
        };
        if (!arrayList.contains(onCheckedChangeListener)) {
            arrayList.add(onCheckedChangeListener);
        }
        if (compoundButton.getVisibility() == 0) {
            compoundButton.setOnCheckedChangeListener(this);
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            CharSequence text = obtainStyledAttributes.getText(4);
            TextView textView = this.mTextView;
            if (textView != null) {
                textView.setText(text);
            }
            if (isExpressiveTheme) {
                CharSequence text2 = obtainStyledAttributes.getText(7);
                TextView textView2 = this.mSummaryView;
                if (textView2 != null) {
                    textView2.setText(text2);
                    this.mSummaryView.setVisibility(TextUtils.isEmpty(text2) ? 8 : 0);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }
}
