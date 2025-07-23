package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MainSwitchPreference extends TwoStatePreference implements CompoundButton.OnCheckedChangeListener {
    public final List mSwitchChangeListeners;

    public MainSwitchPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        final MainSwitchBar mainSwitchBar = (MainSwitchBar) preferenceViewHolder.itemView.requireViewById(R.id.settingslib_main_switch_bar);
        CharSequence charSequence = this.mTitle;
        TextView textView = mainSwitchBar.mTextView;
        if (textView != null) {
            textView.setText(charSequence);
        }
        CharSequence summary = getSummary();
        TextView textView2 = mainSwitchBar.mSummaryView;
        if (textView2 != null) {
            textView2.setText(summary);
            mainSwitchBar.mSummaryView.setVisibility(TextUtils.isEmpty(summary) ? 8 : 0);
        }
        mainSwitchBar.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.MainSwitchPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainSwitchPreference mainSwitchPreference = MainSwitchPreference.this;
                MainSwitchBar mainSwitchBar2 = mainSwitchBar;
                boolean z = mainSwitchPreference.mChecked;
                if (mainSwitchPreference.callChangeListener(Boolean.valueOf(z))) {
                    return;
                }
                boolean z2 = !z;
                CompoundButton compoundButton = mainSwitchBar2.mSwitch;
                if (compoundButton != null) {
                    compoundButton.setChecked(z2);
                }
                mainSwitchBar2.mFrameView.setActivated(z2);
            }
        });
        ((ArrayList) mainSwitchBar.mSwitchChangeListeners).clear();
        boolean z = this.mChecked;
        CompoundButton compoundButton = mainSwitchBar.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
        mainSwitchBar.mFrameView.setActivated(z);
        if (!((ArrayList) mainSwitchBar.mSwitchChangeListeners).contains(this)) {
            ((ArrayList) mainSwitchBar.mSwitchChangeListeners).add(this);
        }
        if (this.mVisible) {
            mainSwitchBar.setVisibility(0);
            mainSwitchBar.mSwitch.setOnCheckedChangeListener(mainSwitchBar);
        } else if (mainSwitchBar.getVisibility() == 0) {
            mainSwitchBar.setVisibility(8);
            mainSwitchBar.mSwitch.setOnCheckedChangeListener(null);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        setChecked(z);
        ArrayList arrayList = (ArrayList) this.mSwitchChangeListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((CompoundButton.OnCheckedChangeListener) obj).onCheckedChanged(compoundButton, z);
        }
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        this.mLayoutResId = SettingsThemeHelper.isExpressiveTheme(context) ? R.layout.settingslib_expressive_main_switch_layout : R.layout.settingslib_main_switch_layout;
    }
}
