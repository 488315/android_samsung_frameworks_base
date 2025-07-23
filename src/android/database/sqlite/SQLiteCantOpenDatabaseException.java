package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteCantOpenDatabaseException extends SQLiteException {
    public static final int errCode = 14;
    private int mErrorCode;

    public SQLiteCantOpenDatabaseException() {
        this.mErrorCode = 14;
    }

    public SQLiteCantOpenDatabaseException(String str) {
        super(str);
        this.mErrorCode = parseCode(14, str);
    }

    public boolean semIsWrongPasswordException() {
        return this.mErrorCode == 3342;
    }

    public SQLiteCantOpenDatabaseException(String str, Throwable th) {
        super(str, th);
    }
}
