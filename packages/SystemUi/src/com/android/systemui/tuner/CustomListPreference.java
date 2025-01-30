package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.preference.ListPreference;
import androidx.preference.ListPreferenceDialogFragment;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CustomListPreference extends ListPreference {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CustomListPreferenceDialogFragment extends ListPreferenceDialogFragment {
        public int mClickedDialogEntryIndex;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.tuner.CustomListPreference$CustomListPreferenceDialogFragment$1 */
        class DialogInterfaceOnClickListenerC34871 implements DialogInterface.OnClickListener {
            public DialogInterfaceOnClickListenerC34871() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CustomListPreferenceDialogFragment customListPreferenceDialogFragment = CustomListPreferenceDialogFragment.this;
                customListPreferenceDialogFragment.onClick(customListPreferenceDialogFragment.getDialog(), -1);
                customListPreferenceDialogFragment.getDialog().dismiss();
            }
        }

        @Override // android.app.DialogFragment, android.app.Fragment
        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            getCustomizablePreference().onDialogStateRestored(getDialog());
        }

        @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            if (bundle != null) {
                this.mClickedDialogEntryIndex = bundle.getInt("settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX", this.mClickedDialogEntryIndex);
            }
            return getCustomizablePreference().onDialogCreated(onCreateDialog);
        }

        @Override // androidx.preference.ListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment
        public final void onDialogClosed(boolean z) {
            CharSequence[] charSequenceArr;
            getCustomizablePreference().onDialogClosed(z);
            CustomListPreference customizablePreference = getCustomizablePreference();
            CustomListPreference customizablePreference2 = getCustomizablePreference();
            int i = this.mClickedDialogEntryIndex;
            String charSequence = (i < 0 || (charSequenceArr = customizablePreference2.mEntryValues) == null) ? null : charSequenceArr[i].toString();
            if (z && charSequence != null && customizablePreference.callChangeListener(charSequence)) {
                customizablePreference.setValue(charSequence);
            }
        }

        @Override // androidx.preference.ListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment
        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
            this.mClickedDialogEntryIndex = getCustomizablePreference().findIndexOfValue(getCustomizablePreference().mValue);
            getCustomizablePreference().onPrepareDialogBuilder(new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.CustomListPreference.CustomListPreferenceDialogFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CustomListPreferenceDialogFragment customListPreferenceDialogFragment = CustomListPreferenceDialogFragment.this;
                    customListPreferenceDialogFragment.mClickedDialogEntryIndex = i;
                    customListPreferenceDialogFragment.getCustomizablePreference().getClass();
                    CustomListPreferenceDialogFragment customListPreferenceDialogFragment2 = CustomListPreferenceDialogFragment.this;
                    customListPreferenceDialogFragment2.onClick(customListPreferenceDialogFragment2.getDialog(), -1);
                    customListPreferenceDialogFragment2.getDialog().dismiss();
                }
            });
            getCustomizablePreference().getClass();
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

    public void onDialogClosed(boolean z) {
    }

    public Dialog onDialogCreated(Dialog dialog) {
        return dialog;
    }

    public void onDialogStateRestored(Dialog dialog) {
    }

    public void onPrepareDialogBuilder(DialogInterface.OnClickListener onClickListener) {
    }
}
