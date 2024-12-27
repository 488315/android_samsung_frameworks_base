package android.database.sqlite;

import android.os.ParcelFileDescriptor;

/* loaded from: classes.dex */
public final class SQLiteStatement extends SQLiteProgram {
    SQLiteStatement(SQLiteDatabase db, String sql, Object[] bindArgs) {
        super(db, sql, bindArgs, null);
    }

    public void execute() {
        acquireReference();
        try {
            try {
                getSession().execute(getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public int executeUpdateDelete() {
        acquireReference();
        try {
            try {
                return getSession()
                        .executeForChangedRowCount(
                                getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public long executeInsert() {
        acquireReference();
        try {
            try {
                return getSession()
                        .executeForLastInsertedRowId(
                                getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public long simpleQueryForLong() {
        acquireReference();
        try {
            try {
                return getSession()
                        .executeForLong(getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public String simpleQueryForString() {
        acquireReference();
        try {
            try {
                return getSession()
                        .executeForString(getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public String simpleQueryForIntegrityCheck() {
        acquireReference();
        try {
            return getSession().executeForString(getSql(), getBindArgs(), 6, null);
        } catch (SQLiteDatabaseCorruptException e) {
            return "false";
        } finally {
            releaseReference();
        }
    }

    public ParcelFileDescriptor simpleQueryForBlobFileDescriptor() {
        acquireReference();
        try {
            try {
                return getSession()
                        .executeForBlobFileDescriptor(
                                getSql(), getBindArgs(), getConnectionFlags(), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public String toString() {
        return "SQLiteProgram: " + getSql();
    }
}
