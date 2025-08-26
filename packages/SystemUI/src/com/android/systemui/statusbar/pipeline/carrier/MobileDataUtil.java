package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MobileDataUtil {
    public final CommonUtil commonUtil;

    public MobileDataUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    public final String get5gIconConfig(int i) {
        CommonUtil commonUtil = this.commonUtil;
        if (!commonUtil.supportTSS20()) {
            String str = commonUtil.countryISO;
            if (!Intrinsics.areEqual(str, "US") && !Intrinsics.areEqual(str, "CA")) {
                String string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBranding5GIcon", "");
                string.getClass();
                return string;
            }
        }
        return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBranding5GIcon", "", false);
    }
}
