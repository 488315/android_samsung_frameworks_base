package com.google.android.setupcompat.template;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Window;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SystemNavBarMixin implements Mixin {
    final boolean applyPartnerResources;
    public final TemplateLayout templateLayout;
    final boolean useFullDynamicColor;
    public final Window windowOfActivity;

    public SystemNavBarMixin(TemplateLayout templateLayout, Window window) {
        this.templateLayout = templateLayout;
        this.windowOfActivity = window;
        boolean z = templateLayout instanceof PartnerCustomizationLayout;
        boolean z2 = false;
        this.applyPartnerResources = z && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        if (z && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor()) {
            z2 = true;
        }
        this.useFullDynamicColor = z2;
    }

    public final void applyPartnerCustomizations(AttributeSet attributeSet, int i) {
        TemplateLayout templateLayout = this.templateLayout;
        TypedArray obtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucSystemNavBarMixin, i, 0);
        boolean z = true;
        setSystemNavBarBackground(obtainStyledAttributes.getColor(1, 0));
        Window window = this.windowOfActivity;
        if (window != null && (window.getDecorView().getSystemUiVisibility() & 16) != 16) {
            z = false;
        }
        boolean z2 = obtainStyledAttributes.getBoolean(0, z);
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context = templateLayout.getContext();
                z2 = PartnerConfigHelper.get(context).getBoolean(context, PartnerConfig.CONFIG_LIGHT_NAVIGATION_BAR, false);
            }
            if (z2) {
                this.windowOfActivity.getDecorView().setSystemUiVisibility(this.windowOfActivity.getDecorView().getSystemUiVisibility() | 16);
            } else {
                this.windowOfActivity.getDecorView().setSystemUiVisibility(this.windowOfActivity.getDecorView().getSystemUiVisibility() & (-17));
            }
        }
        TypedArray obtainStyledAttributes2 = templateLayout.getContext().obtainStyledAttributes(new int[]{R.attr.navigationBarDividerColor});
        int color = obtainStyledAttributes.getColor(2, obtainStyledAttributes2.getColor(0, 0));
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context2 = templateLayout.getContext();
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context2);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_NAVIGATION_BAR_DIVIDER_COLOR;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    color = PartnerConfigHelper.get(context2).getColor(context2, partnerConfig);
                }
            }
            this.windowOfActivity.setNavigationBarDividerColor(color);
        }
        obtainStyledAttributes2.recycle();
        obtainStyledAttributes.recycle();
    }

    public final void setSystemNavBarBackground(int i) {
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources && !this.useFullDynamicColor) {
                Context context = this.templateLayout.getContext();
                i = PartnerConfigHelper.get(context).getColor(context, PartnerConfig.CONFIG_NAVIGATION_BAR_BG_COLOR);
            }
            this.windowOfActivity.setNavigationBarColor(i);
        }
    }
}
