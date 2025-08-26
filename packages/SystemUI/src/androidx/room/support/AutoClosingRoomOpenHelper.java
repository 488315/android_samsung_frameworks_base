package androidx.room.support;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import androidx.room.DelegatingOpenHelper;
import androidx.room.support.AutoClosingRoomOpenHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes.dex */
public final class AutoClosingRoomOpenHelper implements SupportSQLiteOpenHelper, DelegatingOpenHelper {
    public final AutoCloser autoCloser;
    public final AutoClosingSupportSQLiteDatabase autoClosingDb;
    public final SupportSQLiteOpenHelper delegate;

    public final class AutoClosingSupportSQLiteStatement implements SupportSQLiteStatement {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final AutoCloser autoCloser;
        public final String sql;
        public int[] bindingTypes = new int[0];
        public long[] longBindings = new long[0];
        public double[] doubleBindings = new double[0];
        public String[] stringBindings = new String[0];
        public byte[][] blobBindings = new byte[0][];

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public AutoClosingSupportSQLiteStatement(String str, AutoCloser autoCloser) {
            this.sql = str;
            this.autoCloser = autoCloser;
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public final void bindBlob(int i, byte[] bArr) {
            ensureCapacity(4, i);
            this.bindingTypes[i] = 4;
            this.blobBindings[i] = bArr;
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public final void bindDouble(double d, int i) {
            ensureCapacity(2, i);
            this.bindingTypes[i] = 2;
            this.doubleBindings[i] = d;
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public final void bindLong(int i, long j) {
            ensureCapacity(1, i);
            this.bindingTypes[i] = 1;
            this.longBindings[i] = j;
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public final void bindNull(int i) {
            ensureCapacity(5, i);
            this.bindingTypes[i] = 5;
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public final void bindString(int i, String str) {
            ensureCapacity(3, i);
            this.bindingTypes[i] = 3;
            this.stringBindings[i] = str;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            this.bindingTypes = new int[0];
            this.longBindings = new long[0];
            this.doubleBindings = new double[0];
            this.stringBindings = new String[0];
            this.blobBindings = new byte[0][];
        }

        public final void ensureCapacity(int i, int i2) {
            int i3 = i2 + 1;
            int[] iArr = this.bindingTypes;
            if (iArr.length < i3) {
                this.bindingTypes = Arrays.copyOf(iArr, i3);
            }
            if (i == 1) {
                long[] jArr = this.longBindings;
                if (jArr.length < i3) {
                    this.longBindings = Arrays.copyOf(jArr, i3);
                    return;
                }
                return;
            }
            if (i == 2) {
                double[] dArr = this.doubleBindings;
                if (dArr.length < i3) {
                    this.doubleBindings = Arrays.copyOf(dArr, i3);
                    return;
                }
                return;
            }
            if (i == 3) {
                String[] strArr = this.stringBindings;
                if (strArr.length < i3) {
                    this.stringBindings = (String[]) Arrays.copyOf(strArr, i3);
                    return;
                }
                return;
            }
            if (i != 4) {
                return;
            }
            byte[][] bArr = this.blobBindings;
            if (bArr.length < i3) {
                this.blobBindings = (byte[][]) Arrays.copyOf(bArr, i3);
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public final void execute() {
            final AutoClosingRoomOpenHelper$AutoClosingSupportSQLiteStatement$$ExternalSyntheticLambda0 autoClosingRoomOpenHelper$AutoClosingSupportSQLiteStatement$$ExternalSyntheticLambda0 = new AutoClosingRoomOpenHelper$AutoClosingSupportSQLiteStatement$$ExternalSyntheticLambda0();
            this.autoCloser.executeRefCountingFunction(new Function1() { // from class: androidx.room.support.AutoClosingRoomOpenHelper$AutoClosingSupportSQLiteStatement$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    AutoClosingRoomOpenHelper.AutoClosingSupportSQLiteStatement autoClosingSupportSQLiteStatement = this.f$0;
                    SupportSQLiteStatement supportSQLiteStatementCompileStatement = ((SupportSQLiteDatabase) obj).compileStatement(autoClosingSupportSQLiteStatement.sql);
                    int length = autoClosingSupportSQLiteStatement.bindingTypes.length;
                    for (int i = 1; i < length; i++) {
                        int i2 = autoClosingSupportSQLiteStatement.bindingTypes[i];
                        if (i2 == 1) {
                            supportSQLiteStatementCompileStatement.bindLong(i, autoClosingSupportSQLiteStatement.longBindings[i]);
                        } else if (i2 == 2) {
                            supportSQLiteStatementCompileStatement.bindDouble(autoClosingSupportSQLiteStatement.doubleBindings[i], i);
                        } else if (i2 == 3) {
                            String str = autoClosingSupportSQLiteStatement.stringBindings[i];
                            str.getClass();
                            supportSQLiteStatementCompileStatement.bindString(i, str);
                        } else if (i2 == 4) {
                            byte[] bArr = autoClosingSupportSQLiteStatement.blobBindings[i];
                            bArr.getClass();
                            supportSQLiteStatementCompileStatement.bindBlob(i, bArr);
                        } else if (i2 == 5) {
                            supportSQLiteStatementCompileStatement.bindNull(i);
                        }
                    }
                    return autoClosingRoomOpenHelper$AutoClosingSupportSQLiteStatement$$ExternalSyntheticLambda0.mo781invoke(supportSQLiteStatementCompileStatement);
                }
            });
        }
    }

    public final class KeepAliveCursor implements Cursor {
        public final AutoCloser autoCloser;
        public final Cursor delegate;

        public KeepAliveCursor(Cursor cursor, AutoCloser autoCloser) {
            this.delegate = cursor;
            this.autoCloser = autoCloser;
        }

        @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            this.delegate.close();
            this.autoCloser.decrementCountAndScheduleClose();
        }

        @Override // android.database.Cursor
        public final void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
            this.delegate.copyStringToBuffer(i, charArrayBuffer);
        }

        @Override // android.database.Cursor
        public final void deactivate() {
            this.delegate.deactivate();
        }

        @Override // android.database.Cursor
        public final byte[] getBlob(int i) {
            return this.delegate.getBlob(i);
        }

        @Override // android.database.Cursor
        public final int getColumnCount() {
            return this.delegate.getColumnCount();
        }

        @Override // android.database.Cursor
        public final int getColumnIndex(String str) {
            return this.delegate.getColumnIndex(str);
        }

        @Override // android.database.Cursor
        public final int getColumnIndexOrThrow(String str) {
            return this.delegate.getColumnIndexOrThrow(str);
        }

        @Override // android.database.Cursor
        public final String getColumnName(int i) {
            return this.delegate.getColumnName(i);
        }

        @Override // android.database.Cursor
        public final String[] getColumnNames() {
            return this.delegate.getColumnNames();
        }

        @Override // android.database.Cursor
        public final int getCount() {
            return this.delegate.getCount();
        }

        @Override // android.database.Cursor
        public final double getDouble(int i) {
            return this.delegate.getDouble(i);
        }

        @Override // android.database.Cursor
        public final Bundle getExtras() {
            return this.delegate.getExtras();
        }

        @Override // android.database.Cursor
        public final float getFloat(int i) {
            return this.delegate.getFloat(i);
        }

        @Override // android.database.Cursor
        public final int getInt(int i) {
            return this.delegate.getInt(i);
        }

        @Override // android.database.Cursor
        public final long getLong(int i) {
            return this.delegate.getLong(i);
        }

        @Override // android.database.Cursor
        public final Uri getNotificationUri() {
            return this.delegate.getNotificationUri();
        }

        @Override // android.database.Cursor
        public final int getPosition() {
            return this.delegate.getPosition();
        }

        @Override // android.database.Cursor
        public final short getShort(int i) {
            return this.delegate.getShort(i);
        }

        @Override // android.database.Cursor
        public final String getString(int i) {
            return this.delegate.getString(i);
        }

        @Override // android.database.Cursor
        public final int getType(int i) {
            return this.delegate.getType(i);
        }

        @Override // android.database.Cursor
        public final boolean getWantsAllOnMoveCalls() {
            return this.delegate.getWantsAllOnMoveCalls();
        }

        @Override // android.database.Cursor
        public final boolean isAfterLast() {
            return this.delegate.isAfterLast();
        }

        @Override // android.database.Cursor
        public final boolean isBeforeFirst() {
            return this.delegate.isBeforeFirst();
        }

        @Override // android.database.Cursor
        public final boolean isClosed() {
            return this.delegate.isClosed();
        }

        @Override // android.database.Cursor
        public final boolean isFirst() {
            return this.delegate.isFirst();
        }

        @Override // android.database.Cursor
        public final boolean isLast() {
            return this.delegate.isLast();
        }

        @Override // android.database.Cursor
        public final boolean isNull(int i) {
            return this.delegate.isNull(i);
        }

        @Override // android.database.Cursor
        public final boolean move(int i) {
            return this.delegate.move(i);
        }

        @Override // android.database.Cursor
        public final boolean moveToFirst() {
            return this.delegate.moveToFirst();
        }

        @Override // android.database.Cursor
        public final boolean moveToLast() {
            return this.delegate.moveToLast();
        }

        @Override // android.database.Cursor
        public final boolean moveToNext() {
            return this.delegate.moveToNext();
        }

        @Override // android.database.Cursor
        public final boolean moveToPosition(int i) {
            return this.delegate.moveToPosition(i);
        }

        @Override // android.database.Cursor
        public final boolean moveToPrevious() {
            return this.delegate.moveToPrevious();
        }

        @Override // android.database.Cursor
        public final void registerContentObserver(ContentObserver contentObserver) {
            this.delegate.registerContentObserver(contentObserver);
        }

        @Override // android.database.Cursor
        public final void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.delegate.registerDataSetObserver(dataSetObserver);
        }

        @Override // android.database.Cursor
        public final boolean requery() {
            return this.delegate.requery();
        }

        @Override // android.database.Cursor
        public final Bundle respond(Bundle bundle) {
            return this.delegate.respond(bundle);
        }

        @Override // android.database.Cursor
        public final void setExtras(Bundle bundle) {
            this.delegate.setExtras(bundle);
        }

        @Override // android.database.Cursor
        public final void setNotificationUri(ContentResolver contentResolver, Uri uri) {
            this.delegate.setNotificationUri(contentResolver, uri);
        }

        @Override // android.database.Cursor
        public final void unregisterContentObserver(ContentObserver contentObserver) {
            this.delegate.unregisterContentObserver(contentObserver);
        }

        @Override // android.database.Cursor
        public final void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.delegate.unregisterDataSetObserver(dataSetObserver);
        }
    }

    public AutoClosingRoomOpenHelper(SupportSQLiteOpenHelper supportSQLiteOpenHelper, AutoCloser autoCloser) {
        this.delegate = supportSQLiteOpenHelper;
        this.autoCloser = autoCloser;
        this.autoClosingDb = new AutoClosingSupportSQLiteDatabase(autoCloser);
        autoCloser.getClass();
        if (supportSQLiteOpenHelper instanceof AutoClosingRoomOpenHelper) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        autoCloser.delegateOpenHelper = supportSQLiteOpenHelper;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.autoClosingDb.close();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final String getDatabaseName() {
        return this.delegate.getDatabaseName();
    }

    @Override // androidx.room.DelegatingOpenHelper
    public final SupportSQLiteOpenHelper getDelegate() {
        return this.delegate;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final SupportSQLiteDatabase getWritableDatabase() {
        AutoCloser autoCloser = this.autoClosingDb.autoCloser;
        autoCloser.getClass();
        try {
            autoCloser.incrementCountAndEnsureDbIsOpen();
            autoCloser.decrementCountAndScheduleClose();
            return this.autoClosingDb;
        } catch (Throwable th) {
            autoCloser.decrementCountAndScheduleClose();
            throw th;
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final void setWriteAheadLoggingEnabled(boolean z) {
        this.delegate.setWriteAheadLoggingEnabled(z);
    }

    public final class AutoClosingSupportSQLiteDatabase implements SupportSQLiteDatabase {
        public final AutoCloser autoCloser;

        public AutoClosingSupportSQLiteDatabase(AutoCloser autoCloser) {
            this.autoCloser = autoCloser;
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final void beginTransaction() {
            try {
                this.autoCloser.incrementCountAndEnsureDbIsOpen().beginTransaction();
            } catch (Throwable th) {
                this.autoCloser.decrementCountAndScheduleClose();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final void beginTransactionNonExclusive() {
            try {
                this.autoCloser.incrementCountAndEnsureDbIsOpen().beginTransactionNonExclusive();
            } catch (Throwable th) {
                this.autoCloser.decrementCountAndScheduleClose();
                throw th;
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            AutoCloser autoCloser = this.autoCloser;
            synchronized (autoCloser.lock) {
                try {
                    autoCloser.manuallyClosed = true;
                    StandaloneCoroutine standaloneCoroutine = autoCloser.autoCloseJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    autoCloser.autoCloseJob = null;
                    SupportSQLiteDatabase supportSQLiteDatabase = autoCloser.delegateDatabase;
                    if (supportSQLiteDatabase != null) {
                        supportSQLiteDatabase.close();
                    }
                    autoCloser.delegateDatabase = null;
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final SupportSQLiteStatement compileStatement(String str) {
            return new AutoClosingSupportSQLiteStatement(str, this.autoCloser);
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final void endTransaction() {
            try {
                SupportSQLiteDatabase supportSQLiteDatabase = this.autoCloser.delegateDatabase;
                supportSQLiteDatabase.getClass();
                supportSQLiteDatabase.endTransaction();
            } finally {
                this.autoCloser.decrementCountAndScheduleClose();
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final void execSQL(String str) {
            AutoCloser autoCloser = this.autoCloser;
            autoCloser.getClass();
            try {
                autoCloser.incrementCountAndEnsureDbIsOpen().execSQL(str);
                Unit unit = Unit.INSTANCE;
            } finally {
                autoCloser.decrementCountAndScheduleClose();
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final boolean inTransaction() {
            AutoCloser autoCloser = this.autoCloser;
            if (autoCloser.delegateDatabase == null) {
                return false;
            }
            return ((Boolean) autoCloser.executeRefCountingFunction(AutoClosingRoomOpenHelper$AutoClosingSupportSQLiteDatabase$inTransaction$1.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final boolean isOpen() {
            SupportSQLiteDatabase supportSQLiteDatabase = this.autoCloser.delegateDatabase;
            if (supportSQLiteDatabase != null) {
                return supportSQLiteDatabase.isOpen();
            }
            return false;
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final Cursor query() {
            try {
                return new KeepAliveCursor(this.autoCloser.incrementCountAndEnsureDbIsOpen().query(), this.autoCloser);
            } catch (Throwable th) {
                this.autoCloser.decrementCountAndScheduleClose();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final void setTransactionSuccessful() {
            SupportSQLiteDatabase supportSQLiteDatabase = this.autoCloser.delegateDatabase;
            supportSQLiteDatabase.getClass();
            supportSQLiteDatabase.setTransactionSuccessful();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public final Cursor query(SupportSQLiteQuery supportSQLiteQuery) {
            try {
                return new KeepAliveCursor(this.autoCloser.incrementCountAndEnsureDbIsOpen().query(supportSQLiteQuery), this.autoCloser);
            } catch (Throwable th) {
                this.autoCloser.decrementCountAndScheduleClose();
                throw th;
            }
        }
    }
}
