package androidx.datastore.core;

/* loaded from: classes.dex */
public final class Final extends State {
    public final Throwable finalException;

    public Final(Throwable th) {
        super(Integer.MAX_VALUE, null);
        this.finalException = th;
    }
}
