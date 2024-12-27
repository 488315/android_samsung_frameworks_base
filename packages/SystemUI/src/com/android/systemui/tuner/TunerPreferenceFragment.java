package com.android.systemui.tuner;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import com.android.systemui.tuner.CustomListPreference;

public abstract class TunerPreferenceFragment extends PreferenceFragment {
    @Override // androidx.preference.PreferenceFragment, androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public final void onDisplayPreferenceDialog(Preference preference) {
        CustomListPreference.CustomListPreferenceDialogFragment customListPreferenceDialogFragment;
        if (preference instanceof CustomListPreference) {
            String str = preference.mKey;
            customListPreferenceDialogFragment = new CustomListPreference.CustomListPreferenceDialogFragment();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            customListPreferenceDialogFragment.setArguments(bundle);
        } else {
            super.onDisplayPreferenceDialog(preference);
            customListPreferenceDialogFragment = null;
        }
        customListPreferenceDialogFragment.setTargetFragment(this, 0);
        customListPreferenceDialogFragment.show(getFragmentManager(), "dialog_preference");
    }
}
