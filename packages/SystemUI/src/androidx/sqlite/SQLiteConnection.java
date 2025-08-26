package androidx.sqlite;

/* loaded from: classes.dex */
public interface SQLiteConnection extends AutoCloseable {
    SQLiteStatement prepare(String str);
}
