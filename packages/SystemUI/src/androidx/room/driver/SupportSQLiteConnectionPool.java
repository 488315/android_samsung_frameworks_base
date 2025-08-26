package androidx.room.driver;

import androidx.room.coroutines.ConnectionPool;
import java.io.IOException;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class SupportSQLiteConnectionPool implements ConnectionPool {
    public final SupportSQLiteDriver supportDriver;

    public SupportSQLiteConnectionPool(SupportSQLiteDriver supportSQLiteDriver) {
        this.supportDriver = supportSQLiteDriver;
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws IOException {
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
