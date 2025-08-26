package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.view.RichTextView;

/* loaded from: classes4.dex */
public final class TextViewPartnerStyler {
    private TextViewPartnerStyler() {
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void applyPartnerCustomizationStyle(TextView textView, TextPartnerConfigs textPartnerConfigs) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        String string;
        boolean z;
        PartnerConfig partnerConfig;
        PartnerConfig partnerConfig2;
        Typeface typefaceCreate;
        PartnerConfig partnerConfig3;
        boolean zShouldApplyDynamicColor;
        int color;
        View viewFindViewById;
        int color2;
        Context context = textView.getContext();
        PartnerConfig partnerConfig4 = textPartnerConfigs.textColorConfig;
        if (partnerConfig4 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig4) && (color2 = PartnerConfigHelper.get(context).getColor(context, partnerConfig4)) != 0) {
            textView.setTextColor(color2);
        }
        Typeface typefaceCreate2 = null;
        PartnerConfig partnerConfig5 = textPartnerConfigs.textLinkedColorConfig;
        if (partnerConfig5 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig5)) {
            Context context2 = textView.getContext();
            try {
                Logger logger = PartnerCustomizationLayout.LOG;
                Activity activityLookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context2);
                TemplateLayout templateLayout = (activityLookupActivityFromContext == null || (viewFindViewById = activityLookupActivityFromContext.findViewById(R.id.suc_layout_status)) == null) ? null : (TemplateLayout) viewFindViewById.getParent();
                zShouldApplyDynamicColor = templateLayout instanceof GlifLayout ? ((GlifLayout) templateLayout).shouldApplyDynamicColor() : PartnerConfigHelper.isSetupWizardFullDynamicColorEnabled(activityLookupActivityFromContext);
            } catch (ClassCastException | IllegalArgumentException unused) {
                zShouldApplyDynamicColor = false;
            }
            if (!zShouldApplyDynamicColor && (color = PartnerConfigHelper.get(context).getColor(context, partnerConfig5)) != 0) {
                textView.setLinkTextColor(color);
            }
        }
        PartnerConfig partnerConfig6 = textPartnerConfigs.textSizeConfig;
        if (partnerConfig6 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig6)) {
            float dimension = PartnerConfigHelper.get(context).getDimension(context, partnerConfig6, 0.0f);
            if (dimension > 0.0f) {
                textView.setTextSize(0, dimension);
            }
        }
        PartnerConfig partnerConfig7 = textPartnerConfigs.textFontVariationSettingsConfig;
        if (partnerConfig7 != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig7)) {
            string = PartnerConfigHelper.get(context).getString(context, partnerConfig7);
            if ((string == null || TextUtils.isEmpty(string)) ? false : true) {
                z = true;
            }
            partnerConfig = textPartnerConfigs.textFontFamilyConfig;
            if (partnerConfig != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig) && !z) {
                typefaceCreate2 = Typeface.create(PartnerConfigHelper.get(context).getString(context, partnerConfig), 0);
            }
            if (PartnerConfigHelper.isFontWeightEnabled(context) && (partnerConfig3 = textPartnerConfigs.textFontWeightConfig) != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig3) && !z) {
                int integer = PartnerConfigHelper.get(context).getInteger(context, partnerConfig3, 400);
                if (typefaceCreate2 == null) {
                    typefaceCreate2 = textView.getTypeface();
                }
                typefaceCreate2 = Typeface.create(typefaceCreate2, integer, false);
            }
            if (typefaceCreate2 != null) {
                textView.setTypeface(typefaceCreate2);
            }
            if (string == null && !TextUtils.isEmpty(string)) {
                try {
                    textView.setFontVariationSettings(string);
                } catch (Exception e) {
                    KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to set font variation settings: "), "TextViewPartnerStyler");
                }
            }
            if ((textView instanceof RichTextView) && (partnerConfig2 = textPartnerConfigs.textLinkFontFamilyConfig) != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig2) && (typefaceCreate = Typeface.create(PartnerConfigHelper.get(context).getString(context, partnerConfig2), 0)) != null) {
                RichTextView.setSpanTypeface(typefaceCreate);
            }
            applyPartnerCustomizationVerticalMargins(textView, textPartnerConfigs);
            textView.setGravity(textPartnerConfigs.textGravity);
        }
        string = null;
        z = false;
        partnerConfig = textPartnerConfigs.textFontFamilyConfig;
        if (partnerConfig != null) {
            typefaceCreate2 = Typeface.create(PartnerConfigHelper.get(context).getString(context, partnerConfig), 0);
        }
        if (PartnerConfigHelper.isFontWeightEnabled(context)) {
            int integer2 = PartnerConfigHelper.get(context).getInteger(context, partnerConfig3, 400);
            if (typefaceCreate2 == null) {
            }
            typefaceCreate2 = Typeface.create(typefaceCreate2, integer2, false);
        }
        if (typefaceCreate2 != null) {
        }
        if (string == null && !TextUtils.isEmpty(string)) {
        }
        if (textView instanceof RichTextView) {
            RichTextView.setSpanTypeface(typefaceCreate);
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

    public class TextPartnerConfigs {
        public final PartnerConfig textColorConfig;
        public final PartnerConfig textFontFamilyConfig;
        public final PartnerConfig textFontVariationSettingsConfig;
        public final PartnerConfig textFontWeightConfig;
        public final int textGravity;
        public final PartnerConfig textLinkFontFamilyConfig;
        public final PartnerConfig textLinkedColorConfig;
        public final PartnerConfig textMarginBottomConfig;
        public final PartnerConfig textMarginTopConfig;
        public final PartnerConfig textSizeConfig;

        public TextPartnerConfigs(PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, int i) {
            this.textFontVariationSettingsConfig = null;
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textFontWeightConfig = partnerConfig5;
            this.textLinkFontFamilyConfig = partnerConfig6;
            this.textMarginTopConfig = partnerConfig7;
            this.textMarginBottomConfig = partnerConfig8;
            this.textGravity = i;
        }

        public TextPartnerConfigs(PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, int i) {
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textFontWeightConfig = partnerConfig5;
            this.textLinkFontFamilyConfig = partnerConfig6;
            this.textMarginTopConfig = partnerConfig7;
            this.textMarginBottomConfig = partnerConfig8;
            this.textFontVariationSettingsConfig = partnerConfig9;
            this.textGravity = i;
        }
    }
}
