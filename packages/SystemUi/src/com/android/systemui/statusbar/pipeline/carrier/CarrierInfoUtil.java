package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CarrierInfoUtil {
    public final CommonUtil commonUtil;

    public CarrierInfoUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    public final String getCarrierLogoPolicy(int i) {
        return this.commonUtil.supportTSS20() ? SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigPolicyDisplayOpLogo", "", false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigPolicyDisplayOpLogo", "");
    }
}
