package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SettingsSpinnerPreference extends Preference implements Preference.OnPreferenceClickListener {
    public final C09411 mOnSelectedListener;
    public int mPosition;
    public boolean mShouldPerformClick;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.widget.SettingsSpinnerPreference$1] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                SettingsSpinnerPreference settingsSpinnerPreference = SettingsSpinnerPreference.this;
                if (settingsSpinnerPreference.mPosition == i2) {
                    return;
                }
                settingsSpinnerPreference.mPosition = i2;
                settingsSpinnerPreference.getClass();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
                SettingsSpinnerPreference.this.getClass();
            }
        };
        this.mLayoutResId = R.layout.settings_spinner_preference;
        this.mOnClickListener = this;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R.id.spinner);
        spinner.setAdapter((SpinnerAdapter) null);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
        if (this.mShouldPerformClick) {
            this.mShouldPerformClick = false;
            spinner.performClick();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final void onPreferenceClick(Preference preference) {
        this.mShouldPerformClick = true;
        notifyChanged();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.widget.SettingsSpinnerPreference$1] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                SettingsSpinnerPreference settingsSpinnerPreference = SettingsSpinnerPreference.this;
                if (settingsSpinnerPreference.mPosition == i2) {
                    return;
                }
                settingsSpinnerPreference.mPosition = i2;
                settingsSpinnerPreference.getClass();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
                SettingsSpinnerPreference.this.getClass();
            }
        };
        this.mLayoutResId = R.layout.settings_spinner_preference;
        this.mOnClickListener = this;
    }

    public SettingsSpinnerPreference(Context context) {
        this(context, null);
    }
}
