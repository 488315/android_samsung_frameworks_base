package androidx.concurrent.futures;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public final boolean setException(Throwable th) {
        throw null;
    }
}
