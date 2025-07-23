package io.reactivex.internal.functions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ObjectHelper {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BiObjectPredicate {
    }

    static {
        new BiObjectPredicate();
    }

    private ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static void requireNonNull(Object obj, String str) {
        if (obj == null) {
            throw new NullPointerException(str);
        }
    }
}
