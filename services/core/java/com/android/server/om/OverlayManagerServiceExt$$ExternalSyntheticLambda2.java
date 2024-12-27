package com.android.server.om;

import android.content.om.OverlayInfoExt;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerServiceExt$$ExternalSyntheticLambda2 implements Predicate {
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
