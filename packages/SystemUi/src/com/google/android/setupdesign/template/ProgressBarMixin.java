package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.ProgressBar;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ProgressBarMixin implements Mixin {
    public ColorStateList color;
    public final TemplateLayout templateLayout;
    public final boolean useBottomProgressBar;

    public ProgressBarMixin(TemplateLayout templateLayout) {
        this(templateLayout, null, 0);
    }

    public final ProgressBar peekProgressBar() {
        return (ProgressBar) this.templateLayout.findManagedViewById(this.useBottomProgressBar ? R.id.sud_glif_progress_bar : R.id.sud_layout_progress);
    }

    public final void setShown(boolean z) {
        boolean z2 = this.useBottomProgressBar;
        if (!z) {
            ProgressBar peekProgressBar = peekProgressBar();
            if (peekProgressBar != null) {
                peekProgressBar.setVisibility(z2 ? 4 : 8);
                return;
            }
            return;
        }
        if (peekProgressBar() == null && !z2) {
            ViewStub viewStub = (ViewStub) this.templateLayout.findManagedViewById(R.id.sud_layout_progress_stub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            ColorStateList colorStateList = this.color;
            this.color = colorStateList;
            ProgressBar peekProgressBar2 = peekProgressBar();
            if (peekProgressBar2 != null) {
                peekProgressBar2.setIndeterminateTintList(colorStateList);
                peekProgressBar2.setProgressBackgroundTintList(colorStateList);
            }
        }
        ProgressBar peekProgressBar3 = peekProgressBar();
        if (peekProgressBar3 != null) {
            peekProgressBar3.setVisibility(0);
        }
    }

    public ProgressBarMixin(TemplateLayout templateLayout, boolean z) {
        this.templateLayout = templateLayout;
        this.useBottomProgressBar = z;
    }

    public ProgressBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
        boolean z = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudProgressBarMixin, i, 0);
            boolean z2 = obtainStyledAttributes.hasValue(0) ? obtainStyledAttributes.getBoolean(0, false) : false;
            obtainStyledAttributes.recycle();
            setShown(false);
            z = z2;
        }
        this.useBottomProgressBar = z;
    }
}
