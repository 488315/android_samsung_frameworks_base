package com.android.systemui.kairos;

import com.android.systemui.kairos.util.MapPatchKt;
import com.android.systemui.kairos.util.WithPrev;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class SwitchKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                TransactionScope transactionScope = (TransactionScope) obj;
                WithPrev withPrev = (WithPrev) obj2;
                Map mapMapPatchFromFullDiff = MapPatchKt.mapPatchFromFullDiff((Map) transactionScope.sample((Incremental) withPrev.previousValue), (Map) transactionScope.sample((Incremental) withPrev.newValue));
                if (((HashMap) mapMapPatchFromFullDiff).isEmpty()) {
                    return null;
                }
                return mapMapPatchFromFullDiff;
            default:
                LinkedHashMap linkedHashMap = new LinkedHashMap((Map) obj);
                linkedHashMap.putAll((Map) obj2);
                return linkedHashMap;
        }
    }
}
