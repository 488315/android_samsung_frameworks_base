package androidx.datastore.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Final extends State {
    public final Throwable finalException;

    public Final(Throwable th) {
        super(Integer.MAX_VALUE, null);
        this.finalException = th;
    }
}
