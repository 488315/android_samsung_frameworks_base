package com.samsung.android.sume.core.types;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public enum Status {
    OK(0),
    ERROR_NOT_SUPPORTED(-1),
    ERROR_PROCESS_FAILED(-2),
    ERROR_DECODE_FAILED(-3),
    ERROR_ENCODE_FAILED(-4);

    private final int value;

    Status(int i) {
        this.value = i;
    }

    public static Status from(final int i) {
        return (Status) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.types.Status$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Status.lambda$from$0(i, (Status) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.types.Status$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Status.lambda$from$1(i);
            }
        });
    }

    static /* synthetic */ boolean lambda$from$0(int i, Status status) {
        return status.value == i;
    }

    static /* synthetic */ InvalidParameterException lambda$from$1(int i) {
        return new InvalidParameterException("invalid Status value: " + i);
    }
}
