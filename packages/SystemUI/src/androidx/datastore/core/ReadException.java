package androidx.datastore.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ReadException extends State {
    public final Throwable readException;

    public ReadException(Throwable th, int i) {
        super(i, null);
        this.readException = th;
    }
}
