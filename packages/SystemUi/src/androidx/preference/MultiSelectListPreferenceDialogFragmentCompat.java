package androidx.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MultiSelectListPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;
    public final Set mNewValues = new HashSet();
    public boolean mPreferenceChanged;

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            ((HashSet) this.mNewValues).clear();
            this.mNewValues.addAll(bundle.getStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values"));
            this.mPreferenceChanged = bundle.getBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", false);
            this.mEntries = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries");
            this.mEntryValues = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues");
            return;
        }
        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) getPreference();
        if (multiSelectListPreference.mEntries == null || multiSelectListPreference.mEntryValues == null) {
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        ((HashSet) this.mNewValues).clear();
        this.mNewValues.addAll(multiSelectListPreference.mValues);
        this.mPreferenceChanged = false;
        this.mEntries = multiSelectListPreference.mEntries;
        this.mEntryValues = multiSelectListPreference.mEntryValues;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onDialogClosed(boolean z) {
        if (z && this.mPreferenceChanged) {
            MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) getPreference();
            if (multiSelectListPreference.callChangeListener(this.mNewValues)) {
                multiSelectListPreference.setValues(this.mNewValues);
            }
        }
        this.mPreferenceChanged = false;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        int length = this.mEntryValues.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = ((HashSet) this.mNewValues).contains(this.mEntryValues[i].toString());
        }
        CharSequence[] charSequenceArr = this.mEntries;
        DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() { // from class: androidx.preference.MultiSelectListPreferenceDialogFragmentCompat.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                if (z) {
                    MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat = MultiSelectListPreferenceDialogFragmentCompat.this;
                    multiSelectListPreferenceDialogFragmentCompat.mPreferenceChanged |= ((HashSet) multiSelectListPreferenceDialogFragmentCompat.mNewValues).add(multiSelectListPreferenceDialogFragmentCompat.mEntryValues[i2].toString());
                    return;
                }
                MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat2 = MultiSelectListPreferenceDialogFragmentCompat.this;
                multiSelectListPreferenceDialogFragmentCompat2.mPreferenceChanged |= ((HashSet) multiSelectListPreferenceDialogFragmentCompat2.mNewValues).remove(multiSelectListPreferenceDialogFragmentCompat2.mEntryValues[i2].toString());
            }
        };
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mItems = charSequenceArr;
        alertParams.mOnCheckboxClickListener = onMultiChoiceClickListener;
        alertParams.mCheckedItems = zArr;
        alertParams.mIsMultiChoice = true;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values", new ArrayList<>(this.mNewValues));
        bundle.putBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", this.mPreferenceChanged);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries", this.mEntries);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues", this.mEntryValues);
    }
}
