package com.google.android.material.textfield;

import com.google.android.material.internal.CheckableImageButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomEndIconDelegate extends EndIconDelegate {
    public CustomEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        EndCompoundLayout endCompoundLayout = this.endLayout;
        endCompoundLayout.endIconOnLongClickListener = null;
        CheckableImageButton checkableImageButton = endCompoundLayout.endIconView;
        checkableImageButton.setOnLongClickListener(null);
        IconHelper.setIconClickable(checkableImageButton, null);
    }
}
