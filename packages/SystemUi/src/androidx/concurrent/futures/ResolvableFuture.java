package androidx.concurrent.futures;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResolvableFuture extends AbstractResolvableFuture {
    private ResolvableFuture() {
    }

    public static ResolvableFuture create() {
        return new ResolvableFuture();
    }

    public final boolean set(Object obj) {
        if (obj == null) {
            obj = AbstractResolvableFuture.NULL;
        }
        if (!AbstractResolvableFuture.ATOMIC_HELPER.casValue(this, null, obj)) {
            return false;
        }
        AbstractResolvableFuture.complete(this);
        return true;
    }
}
