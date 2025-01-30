package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class ChipTextInputComboView extends FrameLayout implements Checkable {
    public final Chip chip;
    public final EditText editText;
    public final TextFormatter watcher;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TextFormatter extends TextWatcherAdapter {
        private TextFormatter() {
        }

        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView chipTextInputComboView = ChipTextInputComboView.this;
                chipTextInputComboView.chip.setText(ChipTextInputComboView.access$100(chipTextInputComboView, "00"));
            } else {
                ChipTextInputComboView chipTextInputComboView2 = ChipTextInputComboView.this;
                chipTextInputComboView2.chip.setText(ChipTextInputComboView.access$100(chipTextInputComboView2, editable));
            }
        }
    }

    public ChipTextInputComboView(Context context) {
        this(context, null);
    }

    public static String access$100(ChipTextInputComboView chipTextInputComboView, CharSequence charSequence) {
        Resources resources = chipTextInputComboView.getResources();
        Parcelable.Creator<TimeModel> creator = TimeModel.CREATOR;
        return String.format(resources.getConfiguration().locale, "%02d", Integer.valueOf(Integer.parseInt(String.valueOf(charSequence))));
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.chip.isChecked();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.editText.setImeHintLocales(getContext().getResources().getConfiguration().getLocales());
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        this.chip.setChecked(z);
        this.editText.setVisibility(z ? 0 : 4);
        this.chip.setVisibility(z ? 8 : 0);
        if (isChecked()) {
            EditText editText = this.editText;
            editText.requestFocus();
            editText.post(new Runnable() { // from class: com.google.android.material.internal.ViewUtils.1
                public final /* synthetic */ View val$view;

                public RunnableC43081(View editText2) {
                    r1 = editText2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    ((InputMethodManager) r1.getContext().getSystemService("input_method")).showSoftInput(r1, 1);
                }
            });
            if (TextUtils.isEmpty(this.editText.getText())) {
                return;
            }
            EditText editText2 = this.editText;
            editText2.setSelection(editText2.getText().length());
        }
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.chip.setOnClickListener(onClickListener);
    }

    @Override // android.view.View
    public final void setTag(int i, Object obj) {
        this.chip.setTag(i, obj);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        this.chip.toggle();
    }

    public ChipTextInputComboView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChipTextInputComboView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater from = LayoutInflater.from(context);
        Chip chip = (Chip) from.inflate(R.layout.material_time_chip, (ViewGroup) this, false);
        this.chip = chip;
        chip.accessibilityClassName = "android.view.View";
        TextInputLayout textInputLayout = (TextInputLayout) from.inflate(R.layout.material_time_input, (ViewGroup) this, false);
        EditText editText = textInputLayout.editText;
        this.editText = editText;
        editText.setVisibility(4);
        TextFormatter textFormatter = new TextFormatter();
        this.watcher = textFormatter;
        editText.addTextChangedListener(textFormatter);
        editText.setImeHintLocales(getContext().getResources().getConfiguration().getLocales());
        addView(chip);
        addView(textInputLayout);
        TextView textView = (TextView) findViewById(R.id.material_label);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        editText.setId(ViewCompat.Api17Impl.generateViewId());
        ViewCompat.Api17Impl.setLabelFor(textView, editText.getId());
        editText.setSaveEnabled(false);
        editText.setLongClickable(false);
    }
}
