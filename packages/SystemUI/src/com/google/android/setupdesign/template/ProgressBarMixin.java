package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import com.android.systemui.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ProgressBarMixin implements Mixin {
    public ColorStateList color;
    public final boolean isGlifExpressiveEnabled;
    public final TemplateLayout templateLayout;
    public final boolean useBottomProgressBar;

    public ProgressBarMixin(TemplateLayout templateLayout) {
        this(templateLayout, null, 0);
    }

    public View getProgressBar() {
        if (peekProgressBar() == null) {
            boolean z = this.isGlifExpressiveEnabled;
            TemplateLayout templateLayout = this.templateLayout;
            if (z) {
                ViewStub viewStub = (ViewStub) templateLayout.findManagedViewById(R.id.sud_glif_progress_indicator_stub);
                if (viewStub != null) {
                    viewStub.inflate();
                }
            } else if (!this.useBottomProgressBar) {
                ViewStub viewStub2 = (ViewStub) templateLayout.findManagedViewById(R.id.sud_layout_progress_stub);
                if (viewStub2 != null) {
                    viewStub2.inflate();
                }
                ColorStateList colorStateList = this.color;
                this.color = colorStateList;
                ProgressBar peekProgressBar = peekProgressBar();
                if (peekProgressBar != null) {
                    peekProgressBar.setIndeterminateTintList(colorStateList);
                    peekProgressBar.setProgressBackgroundTintList(colorStateList);
                }
            }
        }
        return peekProgressBar();
    }

    public final ProgressBar peekProgressBar() {
        boolean z = this.isGlifExpressiveEnabled;
        TemplateLayout templateLayout = this.templateLayout;
        if (z) {
            return (LinearProgressIndicator) templateLayout.findManagedViewById(R.id.sud_layout_progress_indicator);
        }
        return (ProgressBar) templateLayout.findManagedViewById(this.useBottomProgressBar ? R.id.sud_glif_progress_bar : R.id.sud_layout_progress);
    }

    public ProgressBarMixin(TemplateLayout templateLayout, boolean z) {
        this.templateLayout = templateLayout;
        this.useBottomProgressBar = z;
        this.isGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(templateLayout.getContext());
    }

    public ProgressBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudProgressBarMixin, i, 0);
            r0 = obtainStyledAttributes.hasValue(0) ? obtainStyledAttributes.getBoolean(0, false) : false;
            obtainStyledAttributes.recycle();
            ProgressBar peekProgressBar = peekProgressBar();
            if (peekProgressBar != null) {
                peekProgressBar.setVisibility(this.useBottomProgressBar ? 4 : 8);
            }
        }
        this.useBottomProgressBar = r0;
        this.isGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(templateLayout.getContext());
    }
}
