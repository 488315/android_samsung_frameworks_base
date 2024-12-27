package android.database.sqlite;

public class SQLiteCantOpenDatabaseException extends SQLiteException {
    public static final int errCode = 14;
    private int mErrorCode;

    public SQLiteCantOpenDatabaseException() {
        this.mErrorCode = 14;
    }

    public SQLiteCantOpenDatabaseException(String error) {
        super(error);
        this.mErrorCode = parseCode(14, error);
    }

    public boolean semIsWrongPasswordException() {
        return this.mErrorCode == 3342;
    }

    public SQLiteCantOpenDatabaseException(String error, Throwable cause) {
        super(error, cause);
    }
}
