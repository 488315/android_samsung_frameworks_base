package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import kotlin.jvm.internal.Intrinsics;

public final class CarrierInfoUtil {
    public final CommonUtil commonUtil;

    public CarrierInfoUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    public final String getCarrierLogoPolicy(int i) {
        if (this.commonUtil.supportTSS20()) {
            String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigPolicyDisplayOpLogo", "", false);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String string2 = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigPolicyDisplayOpLogo", "");
        Intrinsics.checkNotNull(string2);
        return string2;
    }
}
