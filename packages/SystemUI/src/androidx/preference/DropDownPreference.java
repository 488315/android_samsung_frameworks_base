package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SeslArrayAdapter;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class DropDownPreference extends ListPreference {
    public final SeslArrayAdapter mAdapter;
    public final AnonymousClass1 mItemSelectedListener;
    public AppCompatSpinner mSpinner;

    public DropDownPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        super.notifyChanged();
        SeslArrayAdapter seslArrayAdapter = this.mAdapter;
        if (seslArrayAdapter != null) {
            seslArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int length;
        AppCompatSpinner appCompatSpinner = (AppCompatSpinner) preferenceViewHolder.itemView.findViewById(R.id.spinner);
        this.mSpinner = appCompatSpinner;
        appCompatSpinner.setSoundEffectsEnabled(false);
        if (!this.mAdapter.equals(this.mSpinner.getAdapter())) {
            this.mSpinner.setAdapter((SpinnerAdapter) this.mAdapter);
        }
        this.mSpinner.setOnItemSelectedListener(this.mItemSelectedListener);
        AppCompatSpinner appCompatSpinner2 = this.mSpinner;
        String str = this.mValue;
        CharSequence[] charSequenceArr = this.mEntryValues;
        if (str == null || charSequenceArr == null) {
            length = -1;
        } else {
            length = charSequenceArr.length - 1;
            while (length >= 0) {
                if (TextUtils.equals(charSequenceArr[length].toString(), str)) {
                    break;
                } else {
                    length--;
                }
            }
            length = -1;
        }
        appCompatSpinner2.setSelection(length);
        super.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void onClick() {
        this.mSpinner.performClick();
    }

    @Override // androidx.preference.ListPreference
    public final void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        updateEntries();
    }

    public final void updateEntries() {
        this.mAdapter.clear();
        CharSequence[] charSequenceArr = this.mEntries;
        if (charSequenceArr != null) {
            for (CharSequence charSequence : charSequenceArr) {
                this.mAdapter.add(charSequence.toString());
            }
        }
    }

    public DropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.dropdownPreferenceStyle);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.preference.DropDownPreference$1] */
    public DropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: androidx.preference.DropDownPreference.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i3, long j) {
                if (i3 >= 0) {
                    String string = DropDownPreference.this.mEntryValues[i3].toString();
                    if (string.equals(DropDownPreference.this.mValue) || !DropDownPreference.this.callChangeListener(string)) {
                        return;
                    }
                    DropDownPreference.this.setValue(string);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
            }
        };
        this.mAdapter = new SeslArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item);
        updateEntries();
    }
}
