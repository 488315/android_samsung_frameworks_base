package com.android.systemui.qs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda2(List list, int i) {
        this.$r8$classId = i;
        this.f$0 = list;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean contains;
        switch (this.$r8$classId) {
            case 0:
                contains = this.f$0.contains(((Map.Entry) obj).getKey());
                break;
            default:
                contains = this.f$0.contains(((Map.Entry) obj).getKey());
                break;
        }
        return !contains;
    }
}
