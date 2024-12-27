package com.android.server.om;

import android.content.om.OverlayInfoExt;

import java.util.Objects;
import java.util.function.Predicate;

public final /* synthetic */ class OverlayManagerServiceExt$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Objects.nonNull((OverlayInfoExt) obj);
            case 1:
                return Objects.nonNull((String) obj);
            default:
                return Objects.nonNull(obj);
        }
    }
}
