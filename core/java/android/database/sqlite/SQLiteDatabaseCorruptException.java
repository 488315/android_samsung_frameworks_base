package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteDatabaseCorruptException extends SQLiteException {
    public static final int SQLITE_CORRUPT = 11;
    public static final int SQLITE_CORRUPT_EXTRA = 1035;
    public static final int SQLITE_CORRUPT_WAL = 1291;
    public static final int SQLITE_NOTADB = 26;
    private int mCorruptCode;

    public SQLiteDatabaseCorruptException() {
        this.mCorruptCode = 11;
    }

    public SQLiteDatabaseCorruptException(String error) {
        super(error);
        this.mCorruptCode = 11;
        this.mCorruptCode = parseCode(11, error);
    }

    public int getCorruptCode() {
        return this.mCorruptCode;
    }

    public static boolean isCorruptException(Throwable th) {
        while (th != null) {
            if (th instanceof SQLiteDatabaseCorruptException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }
}
