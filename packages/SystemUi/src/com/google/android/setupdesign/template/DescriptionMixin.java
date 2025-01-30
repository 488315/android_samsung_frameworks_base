package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DescriptionMixin implements Mixin {
    public final TemplateLayout templateLayout;

    public DescriptionMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        TextView textView;
        TextView textView2;
        this.templateLayout = templateLayout;
        TypedArray obtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudDescriptionMixin, i, 0);
        CharSequence text = obtainStyledAttributes.getText(0);
        if (text != null && (textView2 = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle)) != null) {
            textView2.setText(text);
            TextView textView3 = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
        }
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(1);
        if (colorStateList != null && (textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle)) != null) {
            textView.setTextColor(colorStateList);
        }
        obtainStyledAttributes.recycle();
    }
}
