package com.android.systemui.qs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda5(List list, int i) {
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
