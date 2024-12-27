package com.android.systemui.tuner;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.util.AttributeSet;
import androidx.preference.SwitchPreference;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.res.R$styleable;
import com.android.systemui.tuner.TunerService;

public class TunerSwitch extends SwitchPreference implements TunerService.Tunable {
    public final int mAction;
    public final boolean mDefault;

    public TunerSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.TunerSwitch);
        this.mDefault = obtainStyledAttributes.getBoolean(0, false);
        this.mAction = obtainStyledAttributes.getInt(1, -1);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        super.onAttached();
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).addTunable(this, this.mKey.split(","));
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        super.onClick();
        int i = this.mAction;
        if (i != -1) {
            MetricsLogger.action(this.mContext, i, this.mChecked);
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable(this);
        unregisterDependency();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        boolean z = this.mDefault;
        if (str2 != null) {
            try {
                z = Integer.parseInt(str2) != 0;
            } catch (NumberFormatException unused) {
            }
        }
        setChecked(z);
    }

    @Override // androidx.preference.Preference
    public final void persistBoolean(boolean z) {
        for (String str : this.mKey.split(",")) {
            Settings.Secure.putString(this.mContext.getContentResolver(), str, z ? "1" : "0");
        }
    }
}
