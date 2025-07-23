package android.database;

/* loaded from: classes.dex */
public class SQLException extends RuntimeException {
    public SQLException() {
    }

    public SQLException(String str) {
        super(str);
    }

    public SQLException(String str, Throwable th) {
        super(str, th);
    }
}
