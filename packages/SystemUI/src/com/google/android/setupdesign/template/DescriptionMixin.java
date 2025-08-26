package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* loaded from: classes4.dex */
public class DescriptionMixin implements Mixin {
    public final TemplateLayout templateLayout;

    public DescriptionMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        TextView textView;
        TextView textView2;
        this.templateLayout = templateLayout;
        TypedArray typedArrayObtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudDescriptionMixin, i, 0);
        CharSequence text = typedArrayObtainStyledAttributes.getText(0);
        if (text != null && (textView2 = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle)) != null) {
            textView2.setText(text);
            TextView textView3 = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
        }
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(1);
        if (colorStateList != null && (textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle)) != null) {
            textView.setTextColor(colorStateList);
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
