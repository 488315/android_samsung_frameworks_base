package com.samsung.android.sume.core.types;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public enum Vendor {
    NONE,
    QCOM,
    SLSI;

    public static Vendor[] all() {
        return (Vendor[]) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.types.Vendor$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Vendor.lambda$all$0((Vendor) obj);
            }
        }).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.types.Vendor$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Vendor.lambda$all$1(i);
            }
        });
    }

    static /* synthetic */ boolean lambda$all$0(Vendor e) {
        return e != NONE;
    }

    static /* synthetic */ Vendor[] lambda$all$1(int x$0) {
        return new Vendor[x$0];
    }
}
