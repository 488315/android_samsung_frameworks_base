package androidx.room.driver;

import androidx.room.coroutines.ConnectionPool;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SupportSQLiteConnectionPool implements ConnectionPool {
    public final SupportSQLiteDriver supportDriver;

    public SupportSQLiteConnectionPool(SupportSQLiteDriver supportSQLiteDriver) {
        this.supportDriver = supportSQLiteDriver;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.supportDriver.openHelper.close();
    }

    @Override // androidx.room.coroutines.ConnectionPool
    public final Object useConnection(boolean z, Function2 function2, ContinuationImpl continuationImpl) {
        this.supportDriver.openHelper.getDatabaseName();
        SupportSQLiteDriver supportSQLiteDriver = this.supportDriver;
        supportSQLiteDriver.getClass();
        return function2.invoke(new SupportSQLitePooledConnection(new SupportSQLiteConnection(supportSQLiteDriver.openHelper.getWritableDatabase())), continuationImpl);
    }
}
