package com.android.systemui.tuner;

import androidx.preference.Preference;
import com.android.systemui.Dependency;

public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda1 implements Preference.OnPreferenceChangeListener {
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int[][] iArr = NavBarTuner.ICONS;
        String str = (String) obj;
        if ("default".equals(str)) {
            str = null;
        }
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).setValue("sysui_nav_bar", str);
        return true;
    }
}
