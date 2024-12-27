package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileDataUtil {
    public final CommonUtil commonUtil;

    public MobileDataUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    public final String get5gIconConfig(int i) {
        if (this.commonUtil.supportTSS20()) {
            return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBranding5GIcon", "", false);
        }
        String string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBranding5GIcon", "");
        Intrinsics.checkNotNull(string);
        return string;
    }

    public final String getLteWideBandConfig(int i) {
        if (this.commonUtil.supportTSS20()) {
            String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingLTEWideBandIcon", "", false);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String string2 = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingLTEWideBandIcon", "");
        Intrinsics.checkNotNull(string2);
        return string2;
    }
}
