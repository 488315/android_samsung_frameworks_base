package androidx.room.coroutines;

import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.driver.AndroidSQLiteConnection;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class AndroidSQLiteDriverConnectionPool implements ConnectionPool {
    public final Lazy androidConnection$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.room.coroutines.AndroidSQLiteDriverConnectionPool$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidSQLiteDriverConnectionPool androidSQLiteDriverConnectionPool = this.f$0;
            return new AndroidSQLiteDriverPooledConnection((AndroidSQLiteConnection) androidSQLiteDriverConnectionPool.driver.open(androidSQLiteDriverConnectionPool.fileName));
        }
    });
    public final SQLiteDriver driver;
    public final String fileName;

    public AndroidSQLiteDriverConnectionPool(SQLiteDriver sQLiteDriver, String str) {
        this.driver = sQLiteDriver;
        this.fileName = str;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        ((AndroidSQLiteDriverPooledConnection) this.androidConnection$delegate.getValue()).delegate.close();
    }

    @Override // androidx.room.coroutines.ConnectionPool
    public final Object useConnection(boolean z, Function2 function2, ContinuationImpl continuationImpl) {
        return function2.invoke((AndroidSQLiteDriverPooledConnection) this.androidConnection$delegate.getValue(), continuationImpl);
    }
}
