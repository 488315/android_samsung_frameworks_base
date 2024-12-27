package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;

import java.util.function.Function;

public final /* synthetic */ class OverlayManagerServiceExt$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                return OverlayInfoExt.initFromInfo(overlayInfo);
            default:
                return OverlayInfoExt.isOverlayInfoExt(overlayInfo)
                        ? overlayInfo.targetPackageName
                        : "android";
        }
    }
}
