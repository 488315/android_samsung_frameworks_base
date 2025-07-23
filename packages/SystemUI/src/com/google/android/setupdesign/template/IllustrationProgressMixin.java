package com.google.android.setupdesign.template;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class IllustrationProgressMixin implements Mixin {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ProgressConfig {
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_DEFAULT(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_DEFAULT),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_ACCOUNT(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_ACCOUNT),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_CONNECTION(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_CONNECTION),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_UPDATE(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_UPDATE);

        private final PartnerConfig config;

        ProgressConfig(PartnerConfig partnerConfig) {
            if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.ILLUSTRATION) {
                throw new IllegalArgumentException("Illustration progress only allow illustration resource");
            }
            this.config = partnerConfig;
        }
    }

    static {
        new Logger((Class<?>) IllustrationProgressMixin.class);
    }

    public IllustrationProgressMixin(GlifLayout glifLayout) {
        ProgressConfig[] progressConfigArr = ProgressConfig.$VALUES;
        glifLayout.getContext();
    }
}
