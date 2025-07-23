package com.android.systemui.kairos;

import com.android.systemui.kairos.util.MapPatchKt;
import com.android.systemui.kairos.util.WithPrev;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SwitchKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                TransactionScope transactionScope = (TransactionScope) obj;
                WithPrev withPrev = (WithPrev) obj2;
                Map mapPatchFromFullDiff = MapPatchKt.mapPatchFromFullDiff((Map) transactionScope.sample((Incremental) withPrev.previousValue), (Map) transactionScope.sample((Incremental) withPrev.newValue));
                if (((HashMap) mapPatchFromFullDiff).isEmpty()) {
                    return null;
                }
                return mapPatchFromFullDiff;
            default:
                LinkedHashMap linkedHashMap = new LinkedHashMap((Map) obj);
                linkedHashMap.putAll((Map) obj2);
                return linkedHashMap;
        }
    }
}
