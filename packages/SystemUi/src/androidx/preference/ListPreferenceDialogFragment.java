package androidx.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ListPreferenceDialogFragment extends PreferenceDialogFragment {
    public int mClickedDialogEntryIndex;
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;

    @Deprecated
    public ListPreferenceDialogFragment() {
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mClickedDialogEntryIndex = bundle.getInt("ListPreferenceDialogFragment.index", 0);
            this.mEntries = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entries");
            this.mEntryValues = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entryValues");
            return;
        }
        ListPreference listPreference = (ListPreference) getPreference();
        if (listPreference.mEntries == null || listPreference.mEntryValues == null) {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        this.mClickedDialogEntryIndex = listPreference.findIndexOfValue(listPreference.mValue);
        this.mEntries = listPreference.mEntries;
        this.mEntryValues = listPreference.mEntryValues;
    }

    @Override // androidx.preference.PreferenceDialogFragment
    public void onDialogClosed(boolean z) {
        int i;
        ListPreference listPreference = (ListPreference) getPreference();
        if (!z || (i = this.mClickedDialogEntryIndex) < 0) {
            return;
        }
        String charSequence = this.mEntryValues[i].toString();
        if (listPreference.callChangeListener(charSequence)) {
            listPreference.setValue(charSequence);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragment
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setSingleChoiceItems(this.mEntries, this.mClickedDialogEntryIndex, new DialogInterface.OnClickListener() { // from class: androidx.preference.ListPreferenceDialogFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ListPreferenceDialogFragment listPreferenceDialogFragment = ListPreferenceDialogFragment.this;
                listPreferenceDialogFragment.mClickedDialogEntryIndex = i;
                listPreferenceDialogFragment.onClick(dialogInterface, -1);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null);
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("ListPreferenceDialogFragment.index", this.mClickedDialogEntryIndex);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entries", this.mEntries);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entryValues", this.mEntryValues);
    }
}
