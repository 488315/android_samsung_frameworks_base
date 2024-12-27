package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
