package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MainSwitchPreference extends TwoStatePreference implements OnMainSwitchChangeListener {
    public MainSwitchBar mMainSwitchBar;
    public final List mSwitchChangeListeners;

    public MainSwitchPreference(Context context) {
        super(context);
        this.mSwitchChangeListeners = new ArrayList();
        init(context, null);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        this.mLayoutResId = R.layout.settingslib_main_switch_layout;
        this.mSwitchChangeListeners.add(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.preference.R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes.getText(4));
            setIconSpaceReserved(obtainStyledAttributes.getBoolean(15, true));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        MainSwitchBar mainSwitchBar = (MainSwitchBar) preferenceViewHolder.findViewById(R.id.settingslib_main_switch_bar);
        this.mMainSwitchBar = mainSwitchBar;
        mainSwitchBar.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.MainSwitchPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainSwitchPreference mainSwitchPreference = MainSwitchPreference.this;
                mainSwitchPreference.callChangeListener(Boolean.valueOf(mainSwitchPreference.mChecked));
            }
        });
        setIconSpaceReserved(this.mIconSpaceReserved);
        setChecked(this.mChecked);
        MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
        if (mainSwitchBar2 != null) {
            CharSequence charSequence = this.mTitle;
            TextView textView = mainSwitchBar2.mTextView;
            if (textView != null) {
                textView.setText(charSequence);
            }
            MainSwitchBar mainSwitchBar3 = this.mMainSwitchBar;
            mainSwitchBar3.setVisibility(0);
            mainSwitchBar3.mSwitch.setOnCheckedChangeListener(mainSwitchBar3);
        }
        Iterator it = ((ArrayList) this.mSwitchChangeListeners).iterator();
        while (it.hasNext()) {
            OnMainSwitchChangeListener onMainSwitchChangeListener = (OnMainSwitchChangeListener) it.next();
            MainSwitchBar mainSwitchBar4 = this.mMainSwitchBar;
            if (!((ArrayList) mainSwitchBar4.mSwitchChangeListeners).contains(onMainSwitchChangeListener)) {
                ((ArrayList) mainSwitchBar4.mSwitchChangeListeners).add(onMainSwitchChangeListener);
            }
        }
    }

    @Override // com.android.settingslib.widget.OnMainSwitchChangeListener
    public final void onSwitchChanged(boolean z) {
        super.setChecked(z);
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar == null || mainSwitchBar.mSwitch.isChecked() == z) {
            return;
        }
        MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
        Switch r0 = mainSwitchBar2.mSwitch;
        if (r0 != null) {
            r0.setChecked(z);
        }
        mainSwitchBar2.setBackground(z);
    }

    public final void setIconSpaceReserved(boolean z) {
        if (this.mIconSpaceReserved != z) {
            this.mIconSpaceReserved = z;
            notifyChanged();
        }
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        TextView textView;
        super.setTitle(charSequence);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar == null || (textView = mainSwitchBar.mTextView) == null) {
            return;
        }
        textView.setText(charSequence);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSwitchChangeListeners = new ArrayList();
        init(context, attributeSet);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSwitchChangeListeners = new ArrayList();
        init(context, attributeSet);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        init(context, attributeSet);
    }
}
