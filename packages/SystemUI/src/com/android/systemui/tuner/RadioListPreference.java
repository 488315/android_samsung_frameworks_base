package com.android.systemui.tuner;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import com.android.systemui.Dependency;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.tuner.CustomListPreference;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class RadioListPreference extends CustomListPreference {
    public DialogInterface.OnClickListener mOnClickListener;
    public CharSequence mSummary;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RadioFragment extends TunerPreferenceFragment {
        public RadioListPreference mListPref;

        @Override // androidx.preference.PreferenceFragment
        public final void onCreatePreferences(String str) {
            PreferenceManager preferenceManager = this.mPreferenceManager;
            PreferenceScreen preferenceScreen = new PreferenceScreen(preferenceManager.mContext, null);
            preferenceScreen.onAttachedToHierarchy(preferenceManager);
            setPreferenceScreen(preferenceScreen);
            if (this.mListPref != null) {
                update$3$1();
            }
        }

        @Override // androidx.preference.PreferenceFragment, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public final boolean onPreferenceTreeClick(Preference preference) {
            this.mListPref.mOnClickListener.onClick(null, Integer.parseInt(preference.mKey));
            return true;
        }

        public final void update$3$1() {
            Context context = this.mPreferenceManager.mContext;
            RadioListPreference radioListPreference = this.mListPref;
            CharSequence[] charSequenceArr = radioListPreference.mEntries;
            CharSequence[] charSequenceArr2 = radioListPreference.mEntryValues;
            String str = radioListPreference.mValue;
            for (int i = 0; i < charSequenceArr.length; i++) {
                CharSequence charSequence = charSequenceArr[i];
                SelectablePreference selectablePreference = new SelectablePreference(context);
                this.mPreferenceManager.mPreferenceScreen.addPreference(selectablePreference);
                selectablePreference.setTitle(charSequence);
                selectablePreference.setChecked(Objects.equals(str, charSequenceArr2[i]));
                selectablePreference.setKey(String.valueOf(i));
            }
        }
    }

    public RadioListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final CharSequence getSummary() {
        CharSequence charSequence = this.mSummary;
        return (charSequence == null || charSequence.toString().contains("%s")) ? super.getSummary() : this.mSummary;
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    public final Dialog onDialogCreated(Dialog dialog) {
        final Dialog dialog2 = new Dialog(this.mContext, R.style.Theme.DeviceDefault.Settings);
        Toolbar toolbar = (Toolbar) dialog2.findViewById(R.id.action_bar_container);
        View view = new View(this.mContext);
        view.setId(com.android.systemui.R.id.content);
        dialog2.setContentView(view);
        toolbar.setTitle(this.mTitle);
        TypedArray obtainStyledAttributes = dialog2.getContext().obtainStyledAttributes(new int[]{R.attr.homeAsUpIndicator});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.RadioListPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                dialog2.dismiss();
            }
        });
        RadioFragment radioFragment = new RadioFragment();
        radioFragment.mListPref = this;
        if (radioFragment.mPreferenceManager != null) {
            radioFragment.update$3$1();
        }
        ((FragmentService) Dependency.sDependency.getDependencyInner(FragmentService.class)).getFragmentHostManager(view).mFragments.getFragmentManager().beginTransaction().add(R.id.content, radioFragment).commit();
        return dialog2;
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    public final void onDialogStateRestored(Dialog dialog) {
        RadioFragment radioFragment = (RadioFragment) ((FragmentService) Dependency.sDependency.getDependencyInner(FragmentService.class)).getFragmentHostManager(dialog.findViewById(com.android.systemui.R.id.content)).mFragments.getFragmentManager().findFragmentById(com.android.systemui.R.id.content);
        if (radioFragment != null) {
            radioFragment.mListPref = this;
            if (radioFragment.mPreferenceManager != null) {
                radioFragment.update$3$1();
            }
        }
    }

    @Override // com.android.systemui.tuner.CustomListPreference
    public final void onPrepareDialogBuilder(CustomListPreference.CustomListPreferenceDialogFragment.AnonymousClass2 anonymousClass2) {
        this.mOnClickListener = anonymousClass2;
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        this.mSummary = charSequence;
    }
}
