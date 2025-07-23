package androidx.room;

import androidx.room.RoomDatabase;
import androidx.room.RoomOpenDelegate;
import androidx.room.driver.SupportSQLiteConnection;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.SQLiteStatement;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BaseRoomConnectionManager {
    public boolean isConfigured;
    public boolean isInitializing;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DriverWrapper implements SQLiteDriver {
        public final SQLiteDriver actual;

        public DriverWrapper(SQLiteDriver sQLiteDriver) {
            this.actual = sQLiteDriver;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00a5 A[Catch: all -> 0x00a6, TRY_ENTER, TryCatch #2 {all -> 0x00a6, blocks: (B:45:0x00a5, B:46:0x00a8, B:47:0x00ab), top: B:43:0x00a3 }] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00a8 A[Catch: all -> 0x00a6, TryCatch #2 {all -> 0x00a6, blocks: (B:45:0x00a5, B:46:0x00a8, B:47:0x00ab), top: B:43:0x00a3 }] */
        @Override // androidx.sqlite.SQLiteDriver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.sqlite.SQLiteConnection open(java.lang.String r9) {
            /*
                r8 = this;
                androidx.room.BaseRoomConnectionManager r0 = androidx.room.BaseRoomConnectionManager.this
                java.lang.String r9 = r0.resolveFileName$room_runtime_release(r9)
                androidx.room.concurrent.ExclusiveLock r1 = new androidx.room.concurrent.ExclusiveLock
                boolean r2 = r0.isConfigured
                r3 = 1
                r4 = 0
                if (r2 != 0) goto L1c
                boolean r2 = r0.isInitializing
                if (r2 != 0) goto L1c
                java.lang.String r2 = ":memory:"
                boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r2)
                if (r2 != 0) goto L1c
                r2 = r3
                goto L1d
            L1c:
                r2 = r4
            L1d:
                r1.<init>(r9, r2)
                androidx.room.BaseRoomConnectionManager$DriverWrapper$openLocked$2 r2 = new androidx.room.BaseRoomConnectionManager$DriverWrapper$openLocked$2
                r2.<init>()
                java.util.concurrent.locks.ReentrantLock r5 = r1.threadLock
                r5.lock()
                r5 = 0
                androidx.room.concurrent.FileLock r6 = r1.fileLock
                if (r6 == 0) goto L37
                r6.lock()     // Catch: java.lang.Throwable -> L33
                goto L37
            L33:
                r8 = move-exception
                r3 = r4
                goto La3
            L37:
                boolean r7 = r0.isInitializing     // Catch: java.lang.Throwable -> L8f
                if (r7 != 0) goto L87
                androidx.sqlite.SQLiteDriver r8 = r8.actual     // Catch: java.lang.Throwable -> L8f
                androidx.sqlite.SQLiteConnection r8 = r8.open(r9)     // Catch: java.lang.Throwable -> L8f
                boolean r9 = r0.isConfigured     // Catch: java.lang.Throwable -> L8f
                if (r9 != 0) goto L51
                r0.isInitializing = r3     // Catch: java.lang.Throwable -> L4d
                androidx.room.BaseRoomConnectionManager.access$configureDatabase(r0, r8)     // Catch: java.lang.Throwable -> L4d
                r0.isInitializing = r4     // Catch: java.lang.Throwable -> L8f
                goto L70
            L4d:
                r8 = move-exception
                r0.isInitializing = r4     // Catch: java.lang.Throwable -> L8f
                throw r8     // Catch: java.lang.Throwable -> L8f
            L51:
                androidx.room.DatabaseConfiguration r9 = r0.getConfiguration()     // Catch: java.lang.Throwable -> L8f
                androidx.room.RoomDatabase$JournalMode r9 = r9.journalMode     // Catch: java.lang.Throwable -> L8f
                androidx.room.RoomDatabase$JournalMode r4 = androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING     // Catch: java.lang.Throwable -> L8f
                if (r9 != r4) goto L61
                java.lang.String r9 = "PRAGMA synchronous = NORMAL"
                androidx.sqlite.SQLite.execSQL(r8, r9)     // Catch: java.lang.Throwable -> L8f
                goto L66
            L61:
                java.lang.String r9 = "PRAGMA synchronous = FULL"
                androidx.sqlite.SQLite.execSQL(r8, r9)     // Catch: java.lang.Throwable -> L8f
            L66:
                androidx.room.BaseRoomConnectionManager.configureBusyTimeout(r8)     // Catch: java.lang.Throwable -> L8f
                androidx.room.RoomOpenDelegate r9 = r0.getOpenDelegate()     // Catch: java.lang.Throwable -> L8f
                r9.onOpen(r8)     // Catch: java.lang.Throwable -> L8f
            L70:
                if (r6 == 0) goto L81
                java.nio.channels.FileChannel r9 = r6.lockChannel     // Catch: java.lang.Throwable -> La2
                if (r9 != 0) goto L77
                goto L81
            L77:
                r9.close()     // Catch: java.lang.Throwable -> L7d
                r6.lockChannel = r5     // Catch: java.lang.Throwable -> La2
                goto L81
            L7d:
                r8 = move-exception
                r6.lockChannel = r5     // Catch: java.lang.Throwable -> La2
                throw r8     // Catch: java.lang.Throwable -> La2
            L81:
                java.util.concurrent.locks.ReentrantLock r9 = r1.threadLock
                r9.unlock()
                return r8
            L87:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L8f
                java.lang.String r9 = "Recursive database initialization detected. Did you try to use the database instance during initialization? Maybe in one of the callbacks?"
                r8.<init>(r9)     // Catch: java.lang.Throwable -> L8f
                throw r8     // Catch: java.lang.Throwable -> L8f
            L8f:
                r8 = move-exception
                if (r6 == 0) goto La1
                java.nio.channels.FileChannel r9 = r6.lockChannel     // Catch: java.lang.Throwable -> La2
                if (r9 != 0) goto L97
                goto La1
            L97:
                r9.close()     // Catch: java.lang.Throwable -> L9d
                r6.lockChannel = r5     // Catch: java.lang.Throwable -> La2
                goto La1
            L9d:
                r8 = move-exception
                r6.lockChannel = r5     // Catch: java.lang.Throwable -> La2
                throw r8     // Catch: java.lang.Throwable -> La2
            La1:
                throw r8     // Catch: java.lang.Throwable -> La2
            La2:
                r8 = move-exception
            La3:
                if (r3 == 0) goto La8
                throw r8     // Catch: java.lang.Throwable -> La6
            La6:
                r8 = move-exception
                goto Lac
            La8:
                r2.mo779invoke(r8)     // Catch: java.lang.Throwable -> La6
                throw r5     // Catch: java.lang.Throwable -> La6
            Lac:
                java.util.concurrent.locks.ReentrantLock r9 = r1.threadLock
                r9.unlock()
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.BaseRoomConnectionManager.DriverWrapper.open(java.lang.String):androidx.sqlite.SQLiteConnection");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RoomDatabase.JournalMode.values().length];
            try {
                iArr[RoomDatabase.JournalMode.TRUNCATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public static final void access$configureDatabase(BaseRoomConnectionManager baseRoomConnectionManager, SQLiteConnection sQLiteConnection) {
        Object failure;
        RoomDatabase.JournalMode journalMode = baseRoomConnectionManager.getConfiguration().journalMode;
        RoomDatabase.JournalMode journalMode2 = RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING;
        if (journalMode == journalMode2) {
            SQLite.execSQL(sQLiteConnection, "PRAGMA journal_mode = WAL");
        } else {
            SQLite.execSQL(sQLiteConnection, "PRAGMA journal_mode = TRUNCATE");
        }
        if (baseRoomConnectionManager.getConfiguration().journalMode == journalMode2) {
            SQLite.execSQL(sQLiteConnection, "PRAGMA synchronous = NORMAL");
        } else {
            SQLite.execSQL(sQLiteConnection, "PRAGMA synchronous = FULL");
        }
        configureBusyTimeout(sQLiteConnection);
        SQLiteStatement prepare = sQLiteConnection.prepare("PRAGMA user_version");
        try {
            prepare.step();
            int i = (int) prepare.getLong(0);
            prepare.close();
            if (i != baseRoomConnectionManager.getOpenDelegate().version) {
                SQLite.execSQL(sQLiteConnection, "BEGIN EXCLUSIVE TRANSACTION");
                try {
                    int i2 = Result.$r8$clinit;
                    if (i == 0) {
                        baseRoomConnectionManager.onCreate(sQLiteConnection);
                    } else {
                        baseRoomConnectionManager.onMigrate(sQLiteConnection, i, baseRoomConnectionManager.getOpenDelegate().version);
                    }
                    SQLite.execSQL(sQLiteConnection, "PRAGMA user_version = " + baseRoomConnectionManager.getOpenDelegate().version);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                if (!(failure instanceof Result.Failure)) {
                    SQLite.execSQL(sQLiteConnection, "END TRANSACTION");
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    SQLite.execSQL(sQLiteConnection, "ROLLBACK TRANSACTION");
                    throw m3422exceptionOrNullimpl;
                }
            }
            baseRoomConnectionManager.onOpen(sQLiteConnection);
        } finally {
        }
    }

    public static void configureBusyTimeout(SQLiteConnection sQLiteConnection) {
        SQLiteStatement prepare = sQLiteConnection.prepare("PRAGMA busy_timeout");
        try {
            prepare.step();
            long j = prepare.getLong(0);
            prepare.close();
            if (j < 3000) {
                SQLite.execSQL(sQLiteConnection, "PRAGMA busy_timeout = 3000");
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(prepare, th);
                throw th2;
            }
        }
    }

    public abstract List getCallbacks();

    public abstract DatabaseConfiguration getConfiguration();

    public abstract RoomOpenDelegate getOpenDelegate();

    public final void onCreate(SQLiteConnection sQLiteConnection) {
        SQLiteStatement prepare = sQLiteConnection.prepare("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z = false;
            if (prepare.step()) {
                if (prepare.getLong(0) == 0) {
                    z = true;
                }
            }
            prepare.close();
            getOpenDelegate().createAllTables(sQLiteConnection);
            if (!z) {
                RoomOpenDelegate.ValidationResult onValidateSchema = getOpenDelegate().onValidateSchema(sQLiteConnection);
                if (!onValidateSchema.isValid) {
                    throw new IllegalStateException(("Pre-packaged database has an invalid schema: " + onValidateSchema.expectedFoundMsg).toString());
                }
            }
            updateIdentity(sQLiteConnection);
            getOpenDelegate().onCreate();
            for (RoomDatabase.Callback callback : getCallbacks()) {
                callback.getClass();
                if (sQLiteConnection instanceof SupportSQLiteConnection) {
                    callback.onCreate();
                }
            }
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x009d A[EDGE_INSN: B:125:0x009d->B:109:0x009d BREAK  A[LOOP:4: B:87:0x0019->B:110:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMigrate(androidx.sqlite.SQLiteConnection r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.BaseRoomConnectionManager.onMigrate(androidx.sqlite.SQLiteConnection, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onOpen(androidx.sqlite.SQLiteConnection r10) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.BaseRoomConnectionManager.onOpen(androidx.sqlite.SQLiteConnection):void");
    }

    public final void updateIdentity(SQLiteConnection sQLiteConnection) {
        SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        String str = getOpenDelegate().identityHash;
        int i = RoomMasterTable.$r8$clinit;
        SQLite.execSQL(sQLiteConnection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '" + str + "')");
    }

    public String resolveFileName$room_runtime_release(String str) {
        return str;
    }
}
