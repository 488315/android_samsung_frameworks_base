package com.android.server.om;

import android.content.om.OverlayInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerService$$ExternalSyntheticLambda4
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ OverlayManagerService$$ExternalSyntheticLambda4(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        switch (i) {
            case 0:
                OverlayInfo overlayInfo = (OverlayInfo) obj;
                if (!overlayInfo.isFabricated || !str.equals(overlayInfo.packageName)) {}
                break;
            default:
                String str2 = (String) obj;
                if (str2 == null || !str2.startsWith(str)) {}
                break;
        }
        return false;
    }
}
