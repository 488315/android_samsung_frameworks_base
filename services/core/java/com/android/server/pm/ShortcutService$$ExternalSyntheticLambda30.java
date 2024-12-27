package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.util.ArraySet;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda30 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda30(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((ArraySet) obj2).contains(((ShortcutInfo) obj).getId());
            case 1:
                return ((ArraySet) obj2).contains(((ShortcutInfo) obj).getId());
            default:
                return ((String) obj2).equals(((ShortcutInfo) obj).getId());
        }
    }
}
