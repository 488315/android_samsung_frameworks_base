package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeaderMixin implements Mixin {
    public float headerAutoSizeLineExtraSpacingInPx;
    public int headerAutoSizeMaxLineOfMaxSize;
    public float headerAutoSizeMaxTextSizeInPx;
    public float headerAutoSizeMinTextSizeInPx;
    public final TemplateLayout templateLayout;
    boolean autoTextSizeEnabled = false;
    public final ArrayList titlePreDrawListeners = new ArrayList();

    public HeaderMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        TextView textView;
        TextView textView2;
        this.templateLayout = templateLayout;
        TypedArray obtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucHeaderMixin, i, 0);
        CharSequence text = obtainStyledAttributes.getText(4);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(5);
        obtainStyledAttributes.recycle();
        if (getTextView() != null) {
            getTextView().getTextSize();
            getTextView().getLineHeight();
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (text != null && (textView2 = getTextView()) != null) {
            if (this.autoTextSizeEnabled) {
                autoAdjustTextSize(textView2);
            }
            textView2.setText(text);
        }
        if (colorStateList == null || (textView = getTextView()) == null) {
            return;
        }
        textView.setTextColor(colorStateList);
    }

    public final void autoAdjustTextSize(final TextView textView) {
        if (textView == null) {
            return;
        }
        textView.setTextSize(0, this.headerAutoSizeMaxTextSizeInPx);
        textView.getTextSize();
        textView.setLineHeight(Math.round(this.headerAutoSizeLineExtraSpacingInPx + this.headerAutoSizeMaxTextSizeInPx));
        textView.getLineHeight();
        textView.setMaxLines(6);
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.setupdesign.template.HeaderMixin.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                textView.getViewTreeObserver().removeOnPreDrawListener(this);
                int lineCount = textView.getLineCount();
                HeaderMixin headerMixin = HeaderMixin.this;
                if (lineCount <= headerMixin.headerAutoSizeMaxLineOfMaxSize) {
                    return true;
                }
                textView.setTextSize(0, headerMixin.headerAutoSizeMinTextSizeInPx);
                TextView textView2 = textView;
                HeaderMixin headerMixin2 = HeaderMixin.this;
                textView2.setLineHeight(Math.round(headerMixin2.headerAutoSizeLineExtraSpacingInPx + headerMixin2.headerAutoSizeMinTextSizeInPx));
                textView.invalidate();
                return false;
            }
        };
        textView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        this.titlePreDrawListeners.add(onPreDrawListener);
    }

    public final TextView getTextView() {
        return (TextView) this.templateLayout.findManagedViewById(R.id.suc_layout_title);
    }

    public final void tryApplyPartnerCustomizationStyle() {
        TemplateLayout templateLayout = this.templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.suc_layout_title);
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            View findManagedViewById = templateLayout.findManagedViewById(R.id.sud_layout_header);
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById);
            if (textView != null) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_HEADER_TEXT_COLOR, null, PartnerConfig.CONFIG_HEADER_TEXT_SIZE, PartnerConfig.CONFIG_HEADER_FONT_FAMILY, null, PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_BOTTOM, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
            }
            ViewGroup viewGroup = (ViewGroup) findManagedViewById;
            if (viewGroup != null) {
                Context context = viewGroup.getContext();
                viewGroup.setBackgroundColor(PartnerConfigHelper.get(context).getColor(context, PartnerConfig.CONFIG_HEADER_AREA_BACKGROUND_COLOR));
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_CONTAINER_MARGIN_BOTTOM;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f));
                        viewGroup.setLayoutParams(layoutParams);
                    }
                }
            }
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (this.autoTextSizeEnabled) {
            autoAdjustTextSize(textView);
        }
    }

    public final void tryUpdateAutoTextSizeFlagWithPartnerConfig() {
        TemplateLayout templateLayout = this.templateLayout;
        Context context = templateLayout.getContext();
        if (!PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            this.autoTextSizeEnabled = false;
            return;
        }
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_ENABLED;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.autoTextSizeEnabled = PartnerConfigHelper.get(context).getBoolean(context, partnerConfig, this.autoTextSizeEnabled);
        }
        if (this.autoTextSizeEnabled) {
            Context context2 = templateLayout.getContext();
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_TEXT_SIZE;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                this.headerAutoSizeMaxTextSizeInPx = PartnerConfigHelper.get(context2).getDimension(context2, partnerConfig2, 0.0f);
            }
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MIN_TEXT_SIZE;
            if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                this.headerAutoSizeMinTextSizeInPx = PartnerConfigHelper.get(context2).getDimension(context2, partnerConfig3, 0.0f);
            }
            PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_LINE_SPACING_EXTRA;
            if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                this.headerAutoSizeLineExtraSpacingInPx = PartnerConfigHelper.get(context2).getDimension(context2, partnerConfig4, 0.0f);
            }
            PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_LINE_OF_MAX_SIZE;
            if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5)) {
                this.headerAutoSizeMaxLineOfMaxSize = PartnerConfigHelper.get(context2).getInteger(context2, partnerConfig5);
            }
            if (this.headerAutoSizeMaxLineOfMaxSize >= 1) {
                float f = this.headerAutoSizeMinTextSizeInPx;
                if (f > 0.0f && this.headerAutoSizeMaxTextSizeInPx >= f) {
                    return;
                }
            }
            Log.w("HeaderMixin", "Invalid configs, disable auto text size.");
            this.autoTextSizeEnabled = false;
        }
    }
}
