package com.android.wm.shell.compatui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wm.shell.R;

/* loaded from: classes3.dex */
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
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LetterboxEduDialogActionLayout, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        String string = typedArrayObtainStyledAttributes.getString(1);
        typedArrayObtainStyledAttributes.recycle();
        View viewInflate = FrameLayout.inflate(getContext(), com.android.systemui.R.layout.letterbox_education_dialog_action_layout, this);
        ((ImageView) viewInflate.findViewById(com.android.systemui.R.id.letterbox_education_dialog_action_icon)).setImageResource(resourceId);
        ((TextView) viewInflate.findViewById(com.android.systemui.R.id.letterbox_education_dialog_action_text)).setText(string);
    }
}
