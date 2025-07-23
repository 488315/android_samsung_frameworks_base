package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.preference.ListPreference;
import androidx.preference.ListPreferenceDialogFragment;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CustomListPreference extends ListPreference {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomListPreferenceDialogFragment extends ListPreferenceDialogFragment {
        public int mClickedDialogEntryIndex;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.tuner.CustomListPreference$CustomListPreferenceDialogFragment$2, reason: invalid class name */
        public class AnonymousClass2 implements DialogInterface.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CustomListPreferenceDialogFragment customListPreferenceDialogFragment = CustomListPreferenceDialogFragment.this;
                customListPreferenceDialogFragment.mClickedDialogEntryIndex = i;
                ((CustomListPreference) customListPreferenceDialogFragment.getPreference()).getClass();
                CustomListPreferenceDialogFragment customListPreferenceDialogFragment2 = CustomListPreferenceDialogFragment.this;
                customListPreferenceDialogFragment2.getDialog();
                customListPreferenceDialogFragment2.mWhichButtonClicked = -1;
                customListPreferenceDialogFragment2.getDialog().dismiss();
            }
        }

        @Override // android.app.DialogFragment, android.app.Fragment
        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            ((CustomListPreference) getPreference()).onDialogStateRestored(getDialog());
        }

        @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            if (bundle != null) {
                this.mClickedDialogEntryIndex = bundle.getInt("settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX", this.mClickedDialogEntryIndex);
            }
            return ((CustomListPreference) getPreference()).onDialogCreated(onCreateDialog);
        }

        @Override // androidx.preference.ListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment
        public final void onDialogClosed(boolean z) {
            CharSequence[] charSequenceArr;
            ((CustomListPreference) getPreference()).getClass();
            CustomListPreference customListPreference = (CustomListPreference) getPreference();
            CustomListPreference customListPreference2 = (CustomListPreference) getPreference();
            int i = this.mClickedDialogEntryIndex;
            String charSequence = (i < 0 || (charSequenceArr = customListPreference2.mEntryValues) == null) ? null : charSequenceArr[i].toString();
            if (z && charSequence != null && customListPreference.callChangeListener(charSequence)) {
                customListPreference.setValue(charSequence);
            }
        }

        @Override // androidx.preference.ListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment
        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
            this.mClickedDialogEntryIndex = ((CustomListPreference) getPreference()).findIndexOfValue(((CustomListPreference) getPreference()).mValue);
            ((CustomListPreference) getPreference()).onPrepareDialogBuilder(new AnonymousClass2());
            ((CustomListPreference) getPreference()).getClass();
        }

        @Override // androidx.preference.ListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putInt("settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX", this.mClickedDialogEntryIndex);
        }
    }

    public CustomListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public Dialog onDialogCreated(Dialog dialog) {
        return dialog;
    }

    public void onDialogStateRestored(Dialog dialog) {
    }

    public void onPrepareDialogBuilder(CustomListPreferenceDialogFragment.AnonymousClass2 anonymousClass2) {
    }
}
