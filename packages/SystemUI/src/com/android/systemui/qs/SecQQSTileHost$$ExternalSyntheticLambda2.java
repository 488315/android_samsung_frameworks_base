package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.plugins.qs.QSTile;
import java.util.function.Predicate;

public final /* synthetic */ class SecQQSTileHost$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((QSTile) obj) instanceof Dumpable;
    }
}
