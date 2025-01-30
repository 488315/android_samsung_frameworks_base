package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import androidx.preference.PreferenceViewHolder;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;
import com.android.systemui.R;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PrimarySwitchPreference extends RestrictedPreference {
    public boolean mChecked;
    public boolean mCheckedSet;
    public boolean mEnableSwitch;
    public Switch mSwitch;

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEnableSwitch = true;
    }

    public Boolean getCheckedState() {
        if (this.mCheckedSet) {
            return Boolean.valueOf(this.mChecked);
        }
        return null;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_primary_switch;
    }

    public boolean isSwitchEnabled() {
        return this.mEnableSwitch;
    }

    @Override // com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Switch r2 = (Switch) preferenceViewHolder.findViewById(R.id.switchWidget);
        this.mSwitch = r2;
        if (r2 != null) {
            r2.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PrimarySwitchPreference primarySwitchPreference = PrimarySwitchPreference.this;
                    Switch r6 = primarySwitchPreference.mSwitch;
                    if (r6 == null || r6.isEnabled()) {
                        boolean z = !primarySwitchPreference.mChecked;
                        if (primarySwitchPreference.callChangeListener(Boolean.valueOf(z))) {
                            String str = primarySwitchPreference.mKey;
                            Switch r1 = primarySwitchPreference.mSwitch;
                            InteractionJankMonitor interactionJankMonitor = SettingsJankMonitor.jankMonitor;
                            InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(57, r1);
                            if (str != null) {
                                withView.setTag(str);
                            }
                            if (SettingsJankMonitor.jankMonitor.begin(withView)) {
                                SettingsJankMonitor.scheduledExecutorService.schedule(new Runnable() { // from class: com.android.settingslib.core.instrumentation.SettingsJankMonitor$detectToggleJank$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SettingsJankMonitor.jankMonitor.end(57);
                                    }
                                }, 300L, TimeUnit.MILLISECONDS);
                            }
                            primarySwitchPreference.setChecked(z);
                            primarySwitchPreference.persistBoolean(z);
                        }
                    }
                }
            });
            this.mSwitch.setOnTouchListener(new PrimarySwitchPreference$$ExternalSyntheticLambda1());
            this.mSwitch.setContentDescription(this.mTitle);
            this.mSwitch.setChecked(this.mChecked);
            this.mSwitch.setEnabled(this.mEnableSwitch);
        }
    }

    public final void setChecked(boolean z) {
        if ((this.mChecked != z) || !this.mCheckedSet) {
            this.mChecked = z;
            this.mCheckedSet = true;
            Switch r2 = this.mSwitch;
            if (r2 != null) {
                r2.setChecked(z);
            }
        }
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return false;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context) {
        super(context);
        this.mEnableSwitch = true;
    }
}
