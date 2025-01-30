package com.android.systemui.tuner;

import androidx.preference.PreferenceFragment;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class OtherPrefs extends PreferenceFragment {
    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(R.xml.other_settings);
    }
}
