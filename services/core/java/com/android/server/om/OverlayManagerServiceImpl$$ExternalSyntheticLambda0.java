package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.os.FabricatedOverlayInfo;
import android.util.ArrayMap;

import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerServiceImpl$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OverlayManagerServiceImpl$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                OverlayInfo overlayInfo = (OverlayInfo) obj;
                return (((ArrayMap) obj2).containsKey(overlayInfo.packageName)
                                || OverlayInfoExt.isOverlayInfoExt(overlayInfo))
                        ? false
                        : true;
            case 1:
                OverlayInfo overlayInfo2 = (OverlayInfo) obj;
                return ((String) obj2).equals(overlayInfo2.packageName)
                        && !OverlayInfoExt.isOverlayInfoExt(overlayInfo2);
            default:
                return !((Set) obj2).contains(((FabricatedOverlayInfo) obj).path);
        }
    }
}
