package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.spinner.R$styleable;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SettingsSpinnerPreference extends Preference implements Preference.OnPreferenceClickListener {
    public final AnonymousClass2 mOnSelectedListener;
    public int mPosition;
    public boolean mShouldPerformClick;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.widget.SettingsSpinnerPreference$2] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                SettingsSpinnerPreference settingsSpinnerPreference = SettingsSpinnerPreference.this;
                if (settingsSpinnerPreference.mPosition == i2) {
                    return;
                }
                settingsSpinnerPreference.mPosition = i2;
                settingsSpinnerPreference.getClass();
                int i3 = SettingsSpinnerPreference.this.mPosition;
                throw null;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
                SettingsSpinnerPreference.this.getClass();
            }
        };
        initAttributes$1(context, attributeSet, i);
        this.mOnClickListener = this;
    }

    public final void initAttributes$1(Context context, AttributeSet attributeSet, int i) {
        int i2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SettingsSpinnerPreference, i, 0);
        try {
            int integer = obtainStyledAttributes.getInteger(0, 0);
            if (integer != 2) {
                i2 = R.layout.settings_expressive_spinner_preference_outlined;
                if (integer != 3 && integer != 4) {
                    i2 = integer != 5 ? R.layout.settings_spinner_preference : R.layout.settings_expressive_spinner_preference_full_outlined;
                }
            } else {
                i2 = R.layout.settings_expressive_spinner_preference_full;
            }
            obtainStyledAttributes.close();
            this.mLayoutResId = i2;
        } catch (Throwable th) {
            if (obtainStyledAttributes != null) {
                try {
                    obtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R.id.spinner);
        if (spinner == null) {
            return;
        }
        spinner.setAdapter((SpinnerAdapter) null);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
        spinner.setLongClickable(false);
        spinner.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.1
            @Override // android.view.View.AccessibilityDelegate
            public final void sendAccessibilityEvent(View view, int i) {
                if (i == 4) {
                    return;
                }
                super.sendAccessibilityEvent(view, i);
            }
        });
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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.widget.SettingsSpinnerPreference$2] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                SettingsSpinnerPreference settingsSpinnerPreference = SettingsSpinnerPreference.this;
                if (settingsSpinnerPreference.mPosition == i2) {
                    return;
                }
                settingsSpinnerPreference.mPosition = i2;
                settingsSpinnerPreference.getClass();
                int i3 = SettingsSpinnerPreference.this.mPosition;
                throw null;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
                SettingsSpinnerPreference.this.getClass();
            }
        };
        initAttributes$1(context, attributeSet, 0);
        this.mOnClickListener = this;
    }

    public SettingsSpinnerPreference(Context context) {
        this(context, null);
        initAttributes$1(context, null, 0);
    }
}
