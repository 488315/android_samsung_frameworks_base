package com.android.p038wm.shell.compatui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p038wm.shell.C3767R;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class LetterboxEduDialogActionLayout extends FrameLayout {
    public LetterboxEduDialogActionLayout(Context context) {
        this(context, null);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3767R.styleable.LetterboxEduDialogActionLayout, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        String string = obtainStyledAttributes.getString(1);
        obtainStyledAttributes.recycle();
        View inflate = FrameLayout.inflate(getContext(), R.layout.letterbox_education_dialog_action_layout, this);
        ((ImageView) inflate.findViewById(R.id.letterbox_education_dialog_action_icon)).setImageResource(resourceId);
        ((TextView) inflate.findViewById(R.id.letterbox_education_dialog_action_text)).setText(string);
    }
}
