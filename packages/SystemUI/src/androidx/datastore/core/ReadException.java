package androidx.datastore.core;

/* loaded from: classes.dex */
public final class ReadException extends State {
    public final Throwable readException;

    public ReadException(Throwable th, int i) {
        super(i, null);
        this.readException = th;
    }
}
