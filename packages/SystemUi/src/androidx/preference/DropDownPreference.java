package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.AppCompatSpinner;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DropDownPreference extends ListPreference {
    public final ArrayAdapter mAdapter;
    public final C03961 mItemSelectedListener;
    public AppCompatSpinner mSpinner;

    public DropDownPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        super.notifyChanged();
        ArrayAdapter arrayAdapter = this.mAdapter;
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        AppCompatSpinner appCompatSpinner = (AppCompatSpinner) preferenceViewHolder.itemView.findViewById(R.id.spinner);
        this.mSpinner = appCompatSpinner;
        appCompatSpinner.setSoundEffectsEnabled(false);
        this.mSpinner.setDropDownHorizontalOffset(this.mContext.getResources().getDimensionPixelOffset(R.dimen.sesl_list_dropdown_item_start_padding));
        if (!this.mAdapter.equals(this.mSpinner.getAdapter())) {
            this.mSpinner.setAdapter((SpinnerAdapter) this.mAdapter);
        }
        this.mSpinner.setOnItemSelectedListener(this.mItemSelectedListener);
        AppCompatSpinner appCompatSpinner2 = this.mSpinner;
        String str = this.mValue;
        CharSequence[] charSequenceArr = this.mEntryValues;
        int i = -1;
        if (str != null && charSequenceArr != null) {
            int length = charSequenceArr.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                if (TextUtils.equals(charSequenceArr[length].toString(), str)) {
                    i = length;
                    break;
                }
                length--;
            }
        }
        appCompatSpinner2.setSelection(i);
        super.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void onClick() {
        this.mSpinner.performClick();
    }

    @Override // androidx.preference.ListPreference
    public final void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        this.mAdapter.clear();
        CharSequence[] charSequenceArr2 = this.mEntries;
        if (charSequenceArr2 != null) {
            for (CharSequence charSequence : charSequenceArr2) {
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

    /* JADX WARN: Type inference failed for: r3v1, types: [androidx.preference.DropDownPreference$1] */
    public DropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: androidx.preference.DropDownPreference.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i3, long j) {
                if (i3 >= 0) {
                    String charSequence = DropDownPreference.this.mEntryValues[i3].toString();
                    if (charSequence.equals(DropDownPreference.this.mValue) || !DropDownPreference.this.callChangeListener(charSequence)) {
                        return;
                    }
                    DropDownPreference.this.setValue(charSequence);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
            }
        };
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item);
        this.mAdapter = arrayAdapter;
        arrayAdapter.clear();
        CharSequence[] charSequenceArr = this.mEntries;
        if (charSequenceArr != null) {
            for (CharSequence charSequence : charSequenceArr) {
                this.mAdapter.add(charSequence.toString());
            }
        }
    }
}
