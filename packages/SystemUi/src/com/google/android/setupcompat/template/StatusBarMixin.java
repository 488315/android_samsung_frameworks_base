package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.view.StatusBarBackgroundLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarMixin implements Mixin {
    public final View decorView;
    public final LinearLayout linearLayout;
    public final PartnerCustomizationLayout partnerCustomizationLayout;
    public final StatusBarBackgroundLayout statusBarLayout;

    public StatusBarMixin(PartnerCustomizationLayout partnerCustomizationLayout, Window window, AttributeSet attributeSet, int i) {
        this.partnerCustomizationLayout = partnerCustomizationLayout;
        View findManagedViewById = partnerCustomizationLayout.findManagedViewById(R.id.suc_layout_status);
        if (findManagedViewById == null) {
            throw new NullPointerException("sucLayoutStatus cannot be null in StatusBarMixin");
        }
        if (findManagedViewById instanceof StatusBarBackgroundLayout) {
            this.statusBarLayout = (StatusBarBackgroundLayout) findManagedViewById;
        } else {
            this.linearLayout = (LinearLayout) findManagedViewById;
        }
        View decorView = window.getDecorView();
        this.decorView = decorView;
        window.setStatusBarColor(0);
        TypedArray obtainStyledAttributes = partnerCustomizationLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucStatusBarMixin, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, (decorView.getSystemUiVisibility() & 8192) == 8192);
        if (partnerCustomizationLayout.shouldApplyPartnerResource()) {
            Context context = partnerCustomizationLayout.getContext();
            z = PartnerConfigHelper.get(context).getBoolean(context, PartnerConfig.CONFIG_LIGHT_STATUS_BAR, false);
        }
        if (z) {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 8192);
        } else {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
        }
        setStatusBarBackground(obtainStyledAttributes.getDrawable(1));
        obtainStyledAttributes.recycle();
    }

    public final void setStatusBarBackground(Drawable drawable) {
        PartnerCustomizationLayout partnerCustomizationLayout = this.partnerCustomizationLayout;
        if (partnerCustomizationLayout.shouldApplyPartnerResource() && !partnerCustomizationLayout.useFullDynamicColor()) {
            Context context = partnerCustomizationLayout.getContext();
            drawable = PartnerConfigHelper.get(context).getDrawable(context, PartnerConfig.CONFIG_STATUS_BAR_BACKGROUND);
        }
        StatusBarBackgroundLayout statusBarBackgroundLayout = this.statusBarLayout;
        if (statusBarBackgroundLayout == null) {
            this.linearLayout.setBackgroundDrawable(drawable);
            return;
        }
        statusBarBackgroundLayout.statusBarBackground = drawable;
        statusBarBackgroundLayout.setWillNotDraw(drawable == null);
        statusBarBackgroundLayout.setFitsSystemWindows(drawable != null);
        statusBarBackgroundLayout.invalidate();
    }
}
