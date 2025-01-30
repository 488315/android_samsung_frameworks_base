package androidx.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MultiSelectListPreferenceDialogFragment extends PreferenceDialogFragment {
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;
    public final Set mNewValues = new HashSet();
    public boolean mPreferenceChanged;

    @Deprecated
    public MultiSelectListPreferenceDialogFragment() {
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            ((HashSet) this.mNewValues).clear();
            this.mNewValues.addAll(bundle.getStringArrayList("MultiSelectListPreferenceDialogFragment.values"));
            this.mPreferenceChanged = bundle.getBoolean("MultiSelectListPreferenceDialogFragment.changed", false);
            this.mEntries = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragment.entries");
            this.mEntryValues = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragment.entryValues");
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

    @Override // androidx.preference.PreferenceDialogFragment
    public final void onDialogClosed(boolean z) {
        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) getPreference();
        if (z && this.mPreferenceChanged) {
            Set set = this.mNewValues;
            if (multiSelectListPreference.callChangeListener(set)) {
                multiSelectListPreference.setValues(set);
            }
        }
        this.mPreferenceChanged = false;
    }

    @Override // androidx.preference.PreferenceDialogFragment
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        int length = this.mEntryValues.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = ((HashSet) this.mNewValues).contains(this.mEntryValues[i].toString());
        }
        builder.setMultiChoiceItems(this.mEntries, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: androidx.preference.MultiSelectListPreferenceDialogFragment.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                if (z) {
                    MultiSelectListPreferenceDialogFragment multiSelectListPreferenceDialogFragment = MultiSelectListPreferenceDialogFragment.this;
                    multiSelectListPreferenceDialogFragment.mPreferenceChanged |= ((HashSet) multiSelectListPreferenceDialogFragment.mNewValues).add(multiSelectListPreferenceDialogFragment.mEntryValues[i2].toString());
                    return;
                }
                MultiSelectListPreferenceDialogFragment multiSelectListPreferenceDialogFragment2 = MultiSelectListPreferenceDialogFragment.this;
                multiSelectListPreferenceDialogFragment2.mPreferenceChanged |= ((HashSet) multiSelectListPreferenceDialogFragment2.mNewValues).remove(multiSelectListPreferenceDialogFragment2.mEntryValues[i2].toString());
            }
        });
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList("MultiSelectListPreferenceDialogFragment.values", new ArrayList<>(this.mNewValues));
        bundle.putBoolean("MultiSelectListPreferenceDialogFragment.changed", this.mPreferenceChanged);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragment.entries", this.mEntries);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragment.entryValues", this.mEntryValues);
    }
}
