package androidx.datastore.core;

/* loaded from: classes.dex */
public final class Data extends State {
    public final int hashCode;
    public final Object value;

    public Data(Object obj, int i, int i2) {
        super(i2, null);
        this.value = obj;
        this.hashCode = i;
    }
}
