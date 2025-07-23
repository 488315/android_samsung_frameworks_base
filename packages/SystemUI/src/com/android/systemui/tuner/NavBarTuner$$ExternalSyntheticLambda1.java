package com.android.systemui.tuner;

import androidx.preference.Preference;
import com.android.systemui.Dependency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
