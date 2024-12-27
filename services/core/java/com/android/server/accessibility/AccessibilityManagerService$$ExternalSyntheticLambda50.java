package com.android.server.accessibility;

import java.util.Set;
import java.util.function.Predicate;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda50
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Set f$0;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda50(int i, Set set) {
        this.$r8$classId = i;
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Set set = this.f$0;
        switch (i) {
        }
        return !set.contains((String) obj);
    }
}
