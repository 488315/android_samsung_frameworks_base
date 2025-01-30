package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileDataUtil {
    public final CommonUtil commonUtil;

    public MobileDataUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    public final String get5gIconConfig(int i) {
        return this.commonUtil.supportTSS20() ? SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBranding5GIcon", "", false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBranding5GIcon", "");
    }

    public final String getLteWideBandConfig(int i) {
        return this.commonUtil.supportTSS20() ? SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingLTEWideBandIcon", "", false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingLTEWideBandIcon", "");
    }
}
