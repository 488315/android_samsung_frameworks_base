package com.google.android.material.textfield;

import com.google.android.material.internal.CheckableImageButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CustomEndIconDelegate extends EndIconDelegate {
    public CustomEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        CheckableImageButton checkableImageButton = this.endLayout.endIconView;
        checkableImageButton.setOnLongClickListener(null);
        IconHelper.setIconClickable(checkableImageButton);
    }
}
