package com.google.android.material.textfield;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PasswordToggleEndIconDelegate extends EndIconDelegate {
    public EditText editText;
    public final int iconResId;
    public final PasswordToggleEndIconDelegate$$ExternalSyntheticLambda0 onIconClickListener;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.material.textfield.PasswordToggleEndIconDelegate$$ExternalSyntheticLambda0] */
    public PasswordToggleEndIconDelegate(EndCompoundLayout endCompoundLayout, int i) {
        super(endCompoundLayout);
        this.iconResId = R.drawable.design_password_eye;
        this.onIconClickListener = new View.OnClickListener() { // from class: com.google.android.material.textfield.PasswordToggleEndIconDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PasswordToggleEndIconDelegate passwordToggleEndIconDelegate = PasswordToggleEndIconDelegate.this;
                EditText editText = passwordToggleEndIconDelegate.editText;
                if (editText == null) {
                    return;
                }
                int selectionEnd = editText.getSelectionEnd();
                EditText editText2 = passwordToggleEndIconDelegate.editText;
                if (editText2 != null && (editText2.getTransformationMethod() instanceof PasswordTransformationMethod)) {
                    passwordToggleEndIconDelegate.editText.setTransformationMethod(null);
                } else {
                    passwordToggleEndIconDelegate.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (selectionEnd >= 0) {
                    passwordToggleEndIconDelegate.editText.setSelection(selectionEnd);
                }
                passwordToggleEndIconDelegate.refreshIconState();
            }
        };
        if (i != 0) {
            this.iconResId = i;
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void beforeEditTextChanged() {
        refreshIconState();
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconContentDescriptionResId() {
        return R.string.password_toggle_content_description;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconDrawableResId() {
        return this.iconResId;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final boolean isIconCheckable() {
        return true;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final boolean isIconChecked() {
        EditText editText = this.editText;
        return !(editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod));
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onEditTextAttached(EditText editText) {
        this.editText = editText;
        refreshIconState();
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        EditText editText = this.editText;
        if (editText != null && (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224)) {
            this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void tearDown() {
        EditText editText = this.editText;
        if (editText != null) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
