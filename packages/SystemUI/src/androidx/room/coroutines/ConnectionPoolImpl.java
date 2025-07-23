package androidx.room.coroutines;

import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConnectionPoolImpl implements ConnectionPool {
    public final Pool readers;
    public final long timeout;
    public final Pool writers;
    public final ThreadLocal threadLocal = new ThreadLocal();
    public final AtomicBoolean _isClosed = new AtomicBoolean(false);

    public ConnectionPoolImpl(final SQLiteDriver sQLiteDriver, final String str) {
        Duration.Companion companion = Duration.Companion;
        this.timeout = DurationKt.toDuration(30, DurationUnit.SECONDS);
        final int i = 2;
        Pool pool = new Pool(1, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        SQLiteConnection open = sQLiteDriver.open(str);
                        SQLite.execSQL(open, "PRAGMA query_only = 1");
                        break;
                }
                return sQLiteDriver.open(str);
            }
        });
        this.readers = pool;
        this.writers = pool;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        if (this._isClosed.compareAndSet(false, true)) {
            Pool pool = this.readers;
            pool.channel.close(null);
            for (ConnectionWithLock connectionWithLock : pool.connections) {
                if (connectionWithLock != null) {
                    connectionWithLock.close();
                }
            }
            Pool pool2 = this.writers;
            pool2.channel.close(null);
            for (ConnectionWithLock connectionWithLock2 : pool2.connections) {
                if (connectionWithLock2 != null) {
                    connectionWithLock2.close();
                }
            }
        }
    }

    public final void throwTimeoutException(boolean z) {
        String str = z ? "reader" : "writer";
        StringBuilder sb = new StringBuilder();
        sb.append("Timed out attempting to acquire a " + str + " connection.");
        sb.append("\n\nWriter pool:\n");
        this.writers.dump(sb);
        sb.append("Reader pool:");
        sb.append('\n');
        this.readers.dump(sb);
        SQLite.throwSQLiteException(5, sb.toString());
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x013d A[Catch: all -> 0x015b, TryCatch #6 {all -> 0x015b, blocks: (B:63:0x0128, B:65:0x013d, B:69:0x0157, B:70:0x0161, B:74:0x016b, B:78:0x01b4, B:79:0x01bb, B:80:0x01bc, B:81:0x01bd, B:82:0x01c0), top: B:62:0x0128 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01bd A[Catch: all -> 0x015b, TryCatch #6 {all -> 0x015b, blocks: (B:63:0x0128, B:65:0x013d, B:69:0x0157, B:70:0x0161, B:74:0x016b, B:78:0x01b4, B:79:0x01bb, B:80:0x01bc, B:81:0x01bd, B:82:0x01c0), top: B:62:0x0128 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x007c  */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [T, androidx.room.coroutines.PooledConnectionImpl, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v5 */
    @Override // androidx.room.coroutines.ConnectionPool
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object useConnection(boolean r17, kotlin.jvm.functions.Function2 r18, kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.ConnectionPoolImpl.useConnection(boolean, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public ConnectionPoolImpl(final SQLiteDriver sQLiteDriver, final String str, int i, int i2) {
        final int i3 = 0;
        Duration.Companion companion = Duration.Companion;
        this.timeout = DurationKt.toDuration(30, DurationUnit.SECONDS);
        if (i <= 0) {
            throw new IllegalArgumentException("Maximum number of readers must be greater than 0");
        }
        if (i2 > 0) {
            this.readers = new Pool(i, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i3) {
                        case 0:
                            SQLiteConnection open = sQLiteDriver.open(str);
                            SQLite.execSQL(open, "PRAGMA query_only = 1");
                            break;
                    }
                    return sQLiteDriver.open(str);
                }
            });
            final int i4 = 1;
            this.writers = new Pool(i2, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i4) {
                        case 0:
                            SQLiteConnection open = sQLiteDriver.open(str);
                            SQLite.execSQL(open, "PRAGMA query_only = 1");
                            break;
                    }
                    return sQLiteDriver.open(str);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Maximum number of writers must be greater than 0");
    }
}
