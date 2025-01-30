package com.android.systemui.tuner;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda4 implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ ListPreference f$2;
    public final /* synthetic */ Preference f$3;
    public final /* synthetic */ ListPreference f$4;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda4(ListPreference listPreference, ListPreference listPreference2, Preference preference, NavBarTuner navBarTuner, String str) {
        this.f$0 = navBarTuner;
        this.f$1 = str;
        this.f$2 = listPreference;
        this.f$3 = preference;
        this.f$4 = listPreference2;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        final String str = this.f$1;
        final ListPreference listPreference = this.f$2;
        final Preference preference2 = this.f$3;
        final ListPreference listPreference2 = this.f$4;
        final NavBarTuner navBarTuner = this.f$0;
        navBarTuner.mHandler.post(new Runnable() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                NavBarTuner navBarTuner2 = navBarTuner;
                String str2 = str;
                ListPreference listPreference3 = listPreference;
                Preference preference3 = preference2;
                ListPreference listPreference4 = listPreference2;
                int[][] iArr = NavBarTuner.ICONS;
                navBarTuner2.getClass();
                NavBarTuner.setValue(str2, listPreference3, preference3, listPreference4);
                navBarTuner2.updateSummary(listPreference4);
            }
        });
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public void onPreferenceClick(Preference preference) {
        final Preference preference2 = this.f$3;
        final String str = this.f$1;
        final ListPreference listPreference = this.f$2;
        final ListPreference listPreference2 = this.f$4;
        int[][] iArr = NavBarTuner.ICONS;
        final NavBarTuner navBarTuner = this.f$0;
        navBarTuner.getClass();
        final EditText editText = new EditText(navBarTuner.getContext());
        new AlertDialog.Builder(navBarTuner.getContext()).setTitle(preference.getTitle()).setView(editText).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                int i2;
                NavBarTuner navBarTuner2 = NavBarTuner.this;
                EditText editText2 = editText;
                Preference preference3 = preference2;
                String str2 = str;
                ListPreference listPreference3 = listPreference;
                ListPreference listPreference4 = listPreference2;
                int[][] iArr2 = NavBarTuner.ICONS;
                navBarTuner2.getClass();
                try {
                    i2 = Integer.parseInt(editText2.getText().toString());
                } catch (Exception unused) {
                    i2 = 66;
                }
                preference3.setSummary(i2 + "");
                NavBarTuner.setValue(str2, listPreference3, preference3, listPreference4);
            }
        }).show();
    }
}
