package android.database.sqlite;

import android.database.DatabaseUtils;
import android.os.CancellationSignal;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SQLiteProgram extends SQLiteClosable {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final Object[] mBindArgs;
    private final String[] mColumnNames;
    private final SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    private final String mSql;

    @Deprecated
    public final int getUniqueId() {
        return -1;
    }

    SQLiteProgram(SQLiteDatabase sQLiteDatabase, String str, Object[] objArr, CancellationSignal cancellationSignal) {
        boolean z;
        this.mDatabase = sQLiteDatabase;
        String trim = str.trim();
        this.mSql = trim;
        int sqlStatementType = DatabaseUtils.getSqlStatementType(trim);
        if (sqlStatementType == 4 || sqlStatementType == 5 || sqlStatementType == 6) {
            this.mReadOnly = false;
            this.mColumnNames = EMPTY_STRING_ARRAY;
            this.mNumParameters = 0;
        } else {
            boolean z2 = sqlStatementType == 1;
            try {
                SQLiteStatementInfo sQLiteStatementInfo = new SQLiteStatementInfo();
                sQLiteDatabase.getThreadSession().prepare(trim, sQLiteDatabase.getThreadDefaultConnectionFlags(z2), cancellationSignal, sQLiteStatementInfo);
                if (sqlStatementType != 7 && sqlStatementType != 8) {
                    z = sQLiteStatementInfo.readOnly;
                    this.mReadOnly = z;
                    this.mColumnNames = sQLiteStatementInfo.columnNames;
                    this.mNumParameters = sQLiteStatementInfo.numParameters;
                }
                z = false;
                this.mReadOnly = z;
                this.mColumnNames = sQLiteStatementInfo.columnNames;
                this.mNumParameters = sQLiteStatementInfo.numParameters;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption(e.getCorruptCode());
                throw e;
            }
        }
        if (objArr != null && objArr.length > this.mNumParameters) {
            throw new IllegalArgumentException("Too many bind arguments.  " + objArr.length + " arguments were provided but the statement needs " + this.mNumParameters + " arguments.");
        }
        int i = this.mNumParameters;
        if (i != 0) {
            Object[] objArr2 = new Object[i];
            this.mBindArgs = objArr2;
            if (objArr != null) {
                System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
            }
        } else {
            this.mBindArgs = null;
        }
        if (sqlStatementType == 7) {
            SQLitePragma.checkAndSetSpecialPragma(sQLiteDatabase, trim, cancellationSignal);
        }
    }

    final SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    final String getSql() {
        return this.mSql;
    }

    final Object[] getBindArgs() {
        return this.mBindArgs;
    }

    final String[] getColumnNames() {
        return this.mColumnNames;
    }

    protected final SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    protected final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    protected final void onCorruption() {
        this.mDatabase.onCorruption();
    }

    protected final void onCorruption(int i) {
        this.mDatabase.onCorruption(i);
    }

    public void bindNull(int i) {
        bind(i, null);
    }

    public void bindLong(int i, long j) {
        bind(i, Long.valueOf(j));
    }

    public void bindDouble(int i, double d) {
        bind(i, Double.valueOf(d));
    }

    public void bindString(int i, String str) {
        if (str == null) {
            throw new IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, str);
    }

    public void bindBlob(int i, byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, bArr);
    }

    public void clearBindings() {
        Object[] objArr = this.mBindArgs;
        if (objArr != null) {
            Arrays.fill(objArr, (Object) null);
        }
    }

    public void bindAllArgsAsStrings(String[] strArr) {
        if (strArr != null) {
            for (int length = strArr.length; length != 0; length--) {
                bindString(length, strArr[length - 1]);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        clearBindings();
    }

    private void bind(int i, Object obj) {
        if (i < 1 || i > this.mNumParameters) {
            throw new IllegalArgumentException("Cannot bind argument at index " + i + " because the index is out of range.  The statement has " + this.mNumParameters + " parameters.");
        }
        this.mBindArgs[i - 1] = obj;
    }
}
