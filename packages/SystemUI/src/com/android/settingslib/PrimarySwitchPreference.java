package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.preference.PreferenceViewHolder;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;
import com.android.systemui.R;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class PrimarySwitchPreference extends RestrictedPreference {
    public boolean mChecked;
    public boolean mCheckedSet;
    public boolean mEnableSwitch;
    public CompoundButton mSwitch;

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
        View viewFindViewById = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        if (viewFindViewById instanceof LinearLayout) {
            ((LinearLayout) viewFindViewById).setGravity(8388629);
        }
        CompoundButton compoundButton = (CompoundButton) preferenceViewHolder.findViewById(R.id.switchWidget);
        this.mSwitch = compoundButton;
        if (compoundButton != null) {
            compoundButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PrimarySwitchPreference primarySwitchPreference = this.f$0;
                    CompoundButton compoundButton2 = primarySwitchPreference.mSwitch;
                    if (compoundButton2 == null || compoundButton2.isEnabled()) {
                        boolean z = !primarySwitchPreference.mChecked;
                        if (primarySwitchPreference.callChangeListener(Boolean.valueOf(z))) {
                            String str = primarySwitchPreference.mKey;
                            CompoundButton compoundButton3 = primarySwitchPreference.mSwitch;
                            InteractionJankMonitor interactionJankMonitor = SettingsJankMonitor.jankMonitor;
                            InteractionJankMonitor.Configuration.Builder builderWithView = InteractionJankMonitor.Configuration.Builder.withView(57, compoundButton3);
                            if (str != null) {
                                builderWithView.setTag(str);
                            }
                            if (SettingsJankMonitor.jankMonitor.begin(builderWithView)) {
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
        if (this.mChecked == z && this.mCheckedSet) {
            return;
        }
        this.mChecked = z;
        this.mCheckedSet = true;
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
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
