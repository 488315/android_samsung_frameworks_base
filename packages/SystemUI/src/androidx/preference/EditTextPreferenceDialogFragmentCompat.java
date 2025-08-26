package androidx.preference;

import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* loaded from: classes.dex */
public class EditTextPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    public EditText mEditText;
    public CharSequence mText;
    public final AnonymousClass1 mShowSoftInputRunnable = new Runnable() { // from class: androidx.preference.EditTextPreferenceDialogFragmentCompat.1
        @Override // java.lang.Runnable
        public final void run() {
            EditTextPreferenceDialogFragmentCompat editTextPreferenceDialogFragmentCompat = EditTextPreferenceDialogFragmentCompat.this;
            long j = editTextPreferenceDialogFragmentCompat.mShowRequestTime;
            if (j == -1 || j + 1000 <= SystemClock.currentThreadTimeMillis()) {
                return;
            }
            EditText editText = editTextPreferenceDialogFragmentCompat.mEditText;
            if (editText == null || !editText.isFocused()) {
                editTextPreferenceDialogFragmentCompat.mShowRequestTime = -1L;
            } else if (((InputMethodManager) editTextPreferenceDialogFragmentCompat.mEditText.getContext().getSystemService("input_method")).showSoftInput(editTextPreferenceDialogFragmentCompat.mEditText, 0)) {
                editTextPreferenceDialogFragmentCompat.mShowRequestTime = -1L;
            } else {
                editTextPreferenceDialogFragmentCompat.mEditText.removeCallbacks(editTextPreferenceDialogFragmentCompat.mShowSoftInputRunnable);
                editTextPreferenceDialogFragmentCompat.mEditText.postDelayed(editTextPreferenceDialogFragmentCompat.mShowSoftInputRunnable, 50L);
            }
        }
    };
    public long mShowRequestTime = -1;

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(R.id.edit);
        this.mEditText = editText;
        if (editText == null) {
            throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
        }
        editText.requestFocus();
        this.mEditText.setText(this.mText);
        EditText editText2 = this.mEditText;
        editText2.setSelection(editText2.getText().length());
        ((EditTextPreference) getPreference()).getClass();
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.mText = ((EditTextPreference) getPreference()).mText;
        } else {
            this.mText = bundle.getCharSequence("EditTextPreferenceDialogFragment.text");
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onDialogClosed(boolean z) {
        if (z) {
            String string = this.mEditText.getText().toString();
            EditTextPreference editTextPreference = (EditTextPreference) getPreference();
            if (editTextPreference.callChangeListener(string)) {
                editTextPreference.setText(string);
            }
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequence("EditTextPreferenceDialogFragment.text", this.mText);
    }
}
