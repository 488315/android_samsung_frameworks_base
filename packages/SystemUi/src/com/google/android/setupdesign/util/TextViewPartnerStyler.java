package com.google.android.setupdesign.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.view.RichTextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TextViewPartnerStyler {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TextPartnerConfigs {
        public final PartnerConfig textColorConfig;
        public final PartnerConfig textFontFamilyConfig;
        public final int textGravity;
        public final PartnerConfig textLinkFontFamilyConfig;
        public final PartnerConfig textLinkedColorConfig;
        public final PartnerConfig textMarginBottomConfig;
        public final PartnerConfig textMarginTopConfig;
        public final PartnerConfig textSizeConfig;

        public TextPartnerConfigs(PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, int i) {
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textLinkFontFamilyConfig = partnerConfig5;
            this.textMarginTopConfig = partnerConfig6;
            this.textMarginBottomConfig = partnerConfig7;
            this.textGravity = i;
        }
    }

    private TextViewPartnerStyler() {
    }

    public static void applyPartnerCustomizationStyle(TextView textView, TextPartnerConfigs textPartnerConfigs) {
        PartnerConfig partnerConfig;
        Typeface create;
        Typeface create2;
        boolean z;
        int color;
        TemplateLayout findLayoutFromActivity;
        int color2;
        Context context = textView.getContext();
        PartnerConfig partnerConfig2 = textPartnerConfigs.textColorConfig;
        if (partnerConfig2 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig2) && (color2 = PartnerConfigHelper.get(context).getColor(context, partnerConfig2)) != 0) {
            textView.setTextColor(color2);
        }
        PartnerConfig partnerConfig3 = textPartnerConfigs.textLinkedColorConfig;
        if (partnerConfig3 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig3)) {
            Context context2 = textView.getContext();
            try {
                findLayoutFromActivity = PartnerStyleHelper.findLayoutFromActivity(PartnerCustomizationLayout.lookupActivityFromContext(context2));
            } catch (ClassCastException | IllegalArgumentException unused) {
            }
            if (findLayoutFromActivity instanceof GlifLayout) {
                z = ((GlifLayout) findLayoutFromActivity).shouldApplyDynamicColor();
                if (!z && (color = PartnerConfigHelper.get(context).getColor(context, partnerConfig3)) != 0) {
                    textView.setLinkTextColor(color);
                }
            }
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(new int[]{R.attr.sucFullDynamicColor});
            boolean hasValue = obtainStyledAttributes.hasValue(0);
            obtainStyledAttributes.recycle();
            z = hasValue;
            if (!z) {
                textView.setLinkTextColor(color);
            }
        }
        PartnerConfig partnerConfig4 = textPartnerConfigs.textSizeConfig;
        if (partnerConfig4 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig4)) {
            float dimension = PartnerConfigHelper.get(context).getDimension(context, partnerConfig4, 0.0f);
            if (dimension > 0.0f) {
                textView.setTextSize(0, dimension);
            }
        }
        PartnerConfig partnerConfig5 = textPartnerConfigs.textFontFamilyConfig;
        if (partnerConfig5 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig5) && (create2 = Typeface.create(PartnerConfigHelper.get(context).getString(context, partnerConfig5), 0)) != null) {
            textView.setTypeface(create2);
        }
        if ((textView instanceof RichTextView) && (partnerConfig = textPartnerConfigs.textLinkFontFamilyConfig) != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig) && (create = Typeface.create(PartnerConfigHelper.get(context).getString(context, partnerConfig), 0)) != null) {
            RichTextView.setSpanTypeface(create);
        }
        applyPartnerCustomizationVerticalMargins(textView, textPartnerConfigs);
        textView.setGravity(textPartnerConfigs.textGravity);
    }

    public static void applyPartnerCustomizationVerticalMargins(TextView textView, TextPartnerConfigs textPartnerConfigs) {
        PartnerConfig partnerConfig = textPartnerConfigs.textMarginTopConfig;
        PartnerConfig partnerConfig2 = textPartnerConfigs.textMarginBottomConfig;
        if (partnerConfig == null && partnerConfig2 == null) {
            return;
        }
        Context context = textView.getContext();
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.setMargins(layoutParams2.leftMargin, (partnerConfig == null || !PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) ? layoutParams2.topMargin : (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f), layoutParams2.rightMargin, (partnerConfig2 == null || !PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig2)) ? layoutParams2.bottomMargin : (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f));
            textView.setLayoutParams(layoutParams);
        }
    }
}
