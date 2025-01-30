package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IconMixin implements Mixin {
    public final int originalHeight;
    public final ImageView.ScaleType originalScaleType;
    public final TemplateLayout templateLayout;

    public IconMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        ImageView view;
        ImageView view2;
        this.templateLayout = templateLayout;
        Context context = templateLayout.getContext();
        ImageView view3 = getView();
        if (view3 != null) {
            this.originalHeight = view3.getLayoutParams().height;
            this.originalScaleType = view3.getScaleType();
        } else {
            this.originalHeight = 0;
            this.originalScaleType = null;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIconMixin, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0 && (view2 = getView()) != null) {
            view2.setImageResource(resourceId);
            view2.setVisibility(resourceId != 0 ? 0 : 8);
            int visibility = view2.getVisibility();
            if (((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)) != null) {
                ((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)).setVisibility(visibility);
            }
        }
        boolean z = obtainStyledAttributes.getBoolean(2, false);
        ImageView view4 = getView();
        if (view4 != null) {
            ViewGroup.LayoutParams layoutParams = view4.getLayoutParams();
            layoutParams.height = z ? view4.getMaxHeight() : this.originalHeight;
            view4.setLayoutParams(layoutParams);
            view4.setScaleType(z ? ImageView.ScaleType.FIT_CENTER : this.originalScaleType);
        }
        int color = obtainStyledAttributes.getColor(1, 0);
        if (color != 0 && (view = getView()) != null) {
            view.setColorFilter(color);
        }
        obtainStyledAttributes.recycle();
    }

    public final ImageView getView() {
        return (ImageView) this.templateLayout.findManagedViewById(R.id.sud_layout_icon);
    }
}
