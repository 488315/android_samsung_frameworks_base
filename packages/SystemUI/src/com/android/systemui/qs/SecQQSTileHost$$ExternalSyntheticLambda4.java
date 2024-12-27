package com.android.systemui.qs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SecQQSTileHost$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ SecQQSTileHost$$ExternalSyntheticLambda4(List list, int i) {
        this.$r8$classId = i;
        this.f$0 = list;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        List list = this.f$0;
        Map.Entry entry = (Map.Entry) obj;
        switch (i) {
        }
        return !list.contains(entry.getKey());
    }
}
