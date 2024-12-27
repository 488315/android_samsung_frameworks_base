package com.android.server.om;

import android.content.om.OverlayInfo;

import java.util.List;
import java.util.function.Function;

public final /* synthetic */ class OverlayManagerService$1$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((List) obj).stream();
            default:
                return ((OverlayInfo) obj).getOverlayName();
        }
    }
}
