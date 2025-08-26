package com.android.systemui.shared.condition;

import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class Evaluator {
    public static final Evaluator INSTANCE = new Evaluator();

    private Evaluator() {
    }

    public static Boolean threeValuedAndOrOr(Collection collection, boolean z) {
        Iterator it = collection.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            Boolean bool = (Boolean) it.next();
            if (bool == null) {
                z2 = true;
            } else if (bool.equals(Boolean.valueOf(z))) {
                return Boolean.valueOf(z);
            }
        }
        if (z2) {
            return null;
        }
        return Boolean.valueOf(!z);
    }
}
