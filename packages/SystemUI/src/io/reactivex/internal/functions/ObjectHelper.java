package io.reactivex.internal.functions;

/* loaded from: classes4.dex */
public final class ObjectHelper {
    public static final /* synthetic */ int $r8$clinit = 0;

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
