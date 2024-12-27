package com.android.systemui.qs;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((List) obj).remove((String) obj2);
            case 1:
                return ((List) obj).remove((String) obj2);
            default:
                return ((List) obj).removeAll((Collection) obj2);
        }
    }
}
