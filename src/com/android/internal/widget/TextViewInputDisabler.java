package com.android.internal.widget;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class TextViewInputDisabler {
    private InputFilter[] mDefaultFilters;
    private InputFilter[] mNoInputFilters = {new InputFilter(this) { // from class: com.android.internal.widget.TextViewInputDisabler.1
        @Override // android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            return "";
        }
    }};
    private TextView mTextView;

    public TextViewInputDisabler(TextView textView) {
        this.mTextView = textView;
        this.mDefaultFilters = textView.getFilters();
    }

    public void setInputEnabled(boolean z) {
        this.mTextView.setFilters(z ? this.mDefaultFilters : this.mNoInputFilters);
    }
}
