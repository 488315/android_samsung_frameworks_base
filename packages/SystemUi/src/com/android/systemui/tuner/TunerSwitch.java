package com.android.systemui.tuner;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.util.AttributeSet;
import androidx.preference.SwitchPreference;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R$styleable;
import com.android.systemui.tuner.TunerService;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, this.mKey.split(","));
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
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
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
            Settings.Secure.putString(this.mContext.getContentResolver(), str, z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        }
    }
}
