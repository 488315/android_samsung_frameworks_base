package com.google.android.setupcompat.internal;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.FooterButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterButtonPartnerConfig {
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
    public final int partnerTheme;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
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
        public PartnerConfig buttonTextStyleConfig = null;
        public PartnerConfig buttonRadiusConfig = null;
        public PartnerConfig buttonRippleColorAlphaConfig = null;

        public Builder(FooterButton footerButton) {
            if (footerButton != null) {
                this.partnerTheme = footerButton.theme;
            }
        }

        public final FooterButtonPartnerConfig build() {
            return new FooterButtonPartnerConfig(this.partnerTheme, this.buttonBackgroundConfig, this.buttonDisableAlphaConfig, this.buttonDisableBackgroundConfig, this.buttonDisableTextColorConfig, this.buttonIconConfig, this.buttonTextColorConfig, this.buttonMarginStartConfig, this.buttonTextSizeConfig, this.buttonMinHeight, this.buttonTextTypeFaceConfig, this.buttonTextStyleConfig, this.buttonRadiusConfig, this.buttonRippleColorAlphaConfig, 0);
        }
    }

    public /* synthetic */ FooterButtonPartnerConfig(int i, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, PartnerConfig partnerConfig10, PartnerConfig partnerConfig11, PartnerConfig partnerConfig12, PartnerConfig partnerConfig13, int i2) {
        this(i, partnerConfig, partnerConfig2, partnerConfig3, partnerConfig4, partnerConfig5, partnerConfig6, partnerConfig7, partnerConfig8, partnerConfig9, partnerConfig10, partnerConfig11, partnerConfig12, partnerConfig13);
    }

    private FooterButtonPartnerConfig(int i, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, PartnerConfig partnerConfig10, PartnerConfig partnerConfig11, PartnerConfig partnerConfig12, PartnerConfig partnerConfig13) {
        this.partnerTheme = i;
        this.buttonTextColorConfig = partnerConfig6;
        this.buttonMarginStartConfig = partnerConfig7;
        this.buttonTextSizeConfig = partnerConfig8;
        this.buttonMinHeightConfig = partnerConfig9;
        this.buttonTextTypeFaceConfig = partnerConfig10;
        this.buttonTextStyleConfig = partnerConfig11;
        this.buttonBackgroundConfig = partnerConfig;
        this.buttonDisableAlphaConfig = partnerConfig2;
        this.buttonDisableBackgroundConfig = partnerConfig3;
        this.buttonDisableTextColorConfig = partnerConfig4;
        this.buttonRadiusConfig = partnerConfig12;
        this.buttonIconConfig = partnerConfig5;
        this.buttonRippleColorAlphaConfig = partnerConfig13;
    }
}
