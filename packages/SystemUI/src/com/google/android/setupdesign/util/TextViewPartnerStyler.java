package com.google.android.setupdesign.util;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TextViewPartnerStyler {
    private TextViewPartnerStyler() {
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void applyPartnerCustomizationStyle(android.widget.TextView r8, com.google.android.setupdesign.util.TextViewPartnerStyler.TextPartnerConfigs r9) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.util.TextViewPartnerStyler.applyPartnerCustomizationStyle(android.widget.TextView, com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs):void");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
