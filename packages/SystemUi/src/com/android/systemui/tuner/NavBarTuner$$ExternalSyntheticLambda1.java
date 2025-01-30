package com.android.systemui.tuner;

import androidx.preference.Preference;
import com.android.systemui.Dependency;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda1 implements Preference.OnPreferenceChangeListener {
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int[][] iArr = NavBarTuner.ICONS;
        String str = (String) obj;
        if ("default".equals(str)) {
            str = null;
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue("sysui_nav_bar", str);
        return true;
    }
}
