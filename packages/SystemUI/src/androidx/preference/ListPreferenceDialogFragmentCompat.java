package androidx.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ListPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    public int mClickedDialogEntryIndex;
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
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

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onDialogClosed(boolean z) {
        int i;
        if (!z || (i = this.mClickedDialogEntryIndex) < 0) {
            return;
        }
        String charSequence = this.mEntryValues[i].toString();
        ListPreference listPreference = (ListPreference) getPreference();
        if (listPreference.callChangeListener(charSequence)) {
            listPreference.setValue(charSequence);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        CharSequence[] charSequenceArr = this.mEntries;
        int i = this.mClickedDialogEntryIndex;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: androidx.preference.ListPreferenceDialogFragmentCompat.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                ListPreferenceDialogFragmentCompat listPreferenceDialogFragmentCompat = ListPreferenceDialogFragmentCompat.this;
                listPreferenceDialogFragmentCompat.mClickedDialogEntryIndex = i2;
                listPreferenceDialogFragmentCompat.mWhichButtonClicked = -1;
                dialogInterface.dismiss();
            }
        };
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mItems = charSequenceArr;
        alertParams.mOnClickListener = onClickListener;
        alertParams.mCheckedItem = i;
        alertParams.mIsSingleChoice = true;
        builder.setPositiveButton(null, null);
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("ListPreferenceDialogFragment.index", this.mClickedDialogEntryIndex);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entries", this.mEntries);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entryValues", this.mEntryValues);
    }
}
