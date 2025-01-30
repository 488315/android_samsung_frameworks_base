package io.reactivex.internal.functions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ObjectHelper {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
