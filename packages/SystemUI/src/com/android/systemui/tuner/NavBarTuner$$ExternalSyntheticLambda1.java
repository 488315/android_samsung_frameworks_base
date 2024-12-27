package com.android.systemui.tuner;

import androidx.preference.Preference;
import com.android.systemui.Dependency;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
