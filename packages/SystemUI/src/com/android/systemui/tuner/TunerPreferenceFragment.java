package com.android.systemui.tuner;

import android.os.Bundle;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceFragment;
import com.android.systemui.tuner.CustomListPreference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class TunerPreferenceFragment extends PreferenceFragment {
    @Override // androidx.preference.PreferenceFragment, androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public final void onDisplayPreferenceDialog(DialogPreference dialogPreference) {
        CustomListPreference.CustomListPreferenceDialogFragment customListPreferenceDialogFragment;
        if (dialogPreference instanceof CustomListPreference) {
            String str = dialogPreference.mKey;
            customListPreferenceDialogFragment = new CustomListPreference.CustomListPreferenceDialogFragment();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            customListPreferenceDialogFragment.setArguments(bundle);
        } else {
            super.onDisplayPreferenceDialog(dialogPreference);
            customListPreferenceDialogFragment = null;
        }
        customListPreferenceDialogFragment.setTargetFragment(this, 0);
        customListPreferenceDialogFragment.show(getFragmentManager(), "dialog_preference");
    }
}
