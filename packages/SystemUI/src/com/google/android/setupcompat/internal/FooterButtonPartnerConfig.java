package com.google.android.setupcompat.internal;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.FooterButton;

/* loaded from: classes4.dex */
public class FooterButtonPartnerConfig {
    public final PartnerConfig buttonBackgroundConfig;
    public final PartnerConfig buttonDisableAlphaConfig;
    public final PartnerConfig buttonDisableBackgroundConfig;
    public final PartnerConfig buttonDisableTextColorConfig;
    public final PartnerConfig buttonIconConfig;
    public final PartnerConfig buttonMarginStartConfig;
    public final PartnerConfig buttonMinHeightConfig;
    public final PartnerConfig buttonRadiusConfig;
    public final PartnerConfig buttonRippleColorAlphaConfig;
    public final PartnerConfig buttonTextColorConfig;
    public final PartnerConfig buttonTextSizeConfig;
    public final PartnerConfig buttonTextStyleConfig;
    public final PartnerConfig buttonTextTypeFaceConfig;
    public final PartnerConfig buttonTextWeightConfig;
    public final int partnerTheme;

    public class Builder {
        public int partnerTheme;
        public PartnerConfig buttonBackgroundConfig = null;
        public PartnerConfig buttonDisableAlphaConfig = null;
        public PartnerConfig buttonDisableBackgroundConfig = null;
        public PartnerConfig buttonDisableTextColorConfig = null;
        public PartnerConfig buttonIconConfig = null;
        public PartnerConfig buttonTextColorConfig = null;
        public PartnerConfig buttonMarginStartConfig = null;
        public PartnerConfig buttonTextSizeConfig = null;
        public PartnerConfig buttonMinHeight = null;
        public PartnerConfig buttonTextTypeFaceConfig = null;
        public PartnerConfig buttonTextWeightConfig = null;
        public PartnerConfig buttonTextStyleConfig = null;
        public PartnerConfig buttonRadiusConfig = null;
        public PartnerConfig buttonRippleColorAlphaConfig = null;

        public Builder(FooterButton footerButton) {
            if (footerButton != null) {
                this.partnerTheme = footerButton.theme;
            }
        }

        public final FooterButtonPartnerConfig build() {
            return new FooterButtonPartnerConfig(this.partnerTheme, this.buttonBackgroundConfig, this.buttonDisableAlphaConfig, this.buttonDisableBackgroundConfig, this.buttonDisableTextColorConfig, this.buttonIconConfig, this.buttonTextColorConfig, this.buttonMarginStartConfig, this.buttonTextSizeConfig, this.buttonMinHeight, this.buttonTextTypeFaceConfig, this.buttonTextWeightConfig, this.buttonTextStyleConfig, this.buttonRadiusConfig, this.buttonRippleColorAlphaConfig, 0);
        }
    }

    public /* synthetic */ FooterButtonPartnerConfig(int i, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, PartnerConfig partnerConfig10, PartnerConfig partnerConfig11, PartnerConfig partnerConfig12, PartnerConfig partnerConfig13, PartnerConfig partnerConfig14, int i2) {
        this(i, partnerConfig, partnerConfig2, partnerConfig3, partnerConfig4, partnerConfig5, partnerConfig6, partnerConfig7, partnerConfig8, partnerConfig9, partnerConfig10, partnerConfig11, partnerConfig12, partnerConfig13, partnerConfig14);
    }

    private FooterButtonPartnerConfig(int i, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, PartnerConfig partnerConfig10, PartnerConfig partnerConfig11, PartnerConfig partnerConfig12, PartnerConfig partnerConfig13, PartnerConfig partnerConfig14) {
        this.partnerTheme = i;
        this.buttonTextColorConfig = partnerConfig6;
        this.buttonMarginStartConfig = partnerConfig7;
        this.buttonTextSizeConfig = partnerConfig8;
        this.buttonMinHeightConfig = partnerConfig9;
        this.buttonTextTypeFaceConfig = partnerConfig10;
        this.buttonTextWeightConfig = partnerConfig11;
        this.buttonTextStyleConfig = partnerConfig12;
        this.buttonBackgroundConfig = partnerConfig;
        this.buttonDisableAlphaConfig = partnerConfig2;
        this.buttonDisableBackgroundConfig = partnerConfig3;
        this.buttonDisableTextColorConfig = partnerConfig4;
        this.buttonRadiusConfig = partnerConfig13;
        this.buttonIconConfig = partnerConfig5;
        this.buttonRippleColorAlphaConfig = partnerConfig14;
    }
}
