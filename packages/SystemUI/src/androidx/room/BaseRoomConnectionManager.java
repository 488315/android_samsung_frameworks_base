package androidx.room;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenDelegate;
import androidx.room.concurrent.ExclusiveLock;
import androidx.room.concurrent.FileLock;
import androidx.room.driver.SupportSQLiteConnection;
import androidx.room.migration.Migration;
import androidx.room.util.MigrationUtil;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class BaseRoomConnectionManager {
    public boolean isConfigured;
    public boolean isInitializing;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class DriverWrapper implements SQLiteDriver {
        public final SQLiteDriver actual;

        public DriverWrapper(SQLiteDriver sQLiteDriver) {
            this.actual = sQLiteDriver;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x003b A[Catch: all -> 0x008f, TRY_LEAVE, TryCatch #6 {all -> 0x008f, blocks: (B:16:0x0037, B:18:0x003b, B:21:0x004a, B:25:0x0051, B:27:0x005b, B:29:0x0066, B:28:0x0061, B:23:0x004e, B:24:0x0050, B:41:0x0087, B:42:0x008e, B:20:0x0045), top: B:63:0x0037, outer: #0, inners: #5 }] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0087 A[Catch: all -> 0x008f, TRY_ENTER, TryCatch #6 {all -> 0x008f, blocks: (B:16:0x0037, B:18:0x003b, B:21:0x004a, B:25:0x0051, B:27:0x005b, B:29:0x0066, B:28:0x0061, B:23:0x004e, B:24:0x0050, B:41:0x0087, B:42:0x008e, B:20:0x0045), top: B:63:0x0037, outer: #0, inners: #5 }] */
        @Override // androidx.sqlite.SQLiteDriver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final SQLiteConnection open(String str) {
            FileChannel fileChannel;
            FileChannel fileChannel2;
            BaseRoomConnectionManager baseRoomConnectionManager = BaseRoomConnectionManager.this;
            final String strResolveFileName$room_runtime_release = baseRoomConnectionManager.resolveFileName$room_runtime_release(str);
            boolean z = true;
            ExclusiveLock exclusiveLock = new ExclusiveLock(strResolveFileName$room_runtime_release, (baseRoomConnectionManager.isConfigured || baseRoomConnectionManager.isInitializing || Intrinsics.areEqual(strResolveFileName$room_runtime_release, ":memory:")) ? false : true);
            Function1 function1 = new Function1() { // from class: androidx.room.BaseRoomConnectionManager$DriverWrapper$openLocked$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Unable to open database '"), strResolveFileName$room_runtime_release, "'. Was a proper path / name used in Room's database builder?"), (Throwable) obj);
                }
            };
            exclusiveLock.threadLock.lock();
            FileLock fileLock = exclusiveLock.fileLock;
            if (fileLock != null) {
                try {
                    fileLock.lock();
                    try {
                        try {
                            if (!baseRoomConnectionManager.isInitializing) {
                                throw new IllegalStateException("Recursive database initialization detected. Did you try to use the database instance during initialization? Maybe in one of the callbacks?");
                            }
                            SQLiteConnection sQLiteConnectionOpen = this.actual.open(strResolveFileName$room_runtime_release);
                            if (baseRoomConnectionManager.isConfigured) {
                                if (baseRoomConnectionManager.getConfiguration().journalMode == RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING) {
                                    SQLite.execSQL(sQLiteConnectionOpen, "PRAGMA synchronous = NORMAL");
                                } else {
                                    SQLite.execSQL(sQLiteConnectionOpen, "PRAGMA synchronous = FULL");
                                }
                                BaseRoomConnectionManager.configureBusyTimeout(sQLiteConnectionOpen);
                                baseRoomConnectionManager.getOpenDelegate().onOpen(sQLiteConnectionOpen);
                            } else {
                                try {
                                    baseRoomConnectionManager.isInitializing = true;
                                    BaseRoomConnectionManager.access$configureDatabase(baseRoomConnectionManager, sQLiteConnectionOpen);
                                    baseRoomConnectionManager.isInitializing = false;
                                } catch (Throwable th) {
                                    baseRoomConnectionManager.isInitializing = false;
                                    throw th;
                                }
                            }
                            if (fileLock != null && (fileChannel2 = fileLock.lockChannel) != null) {
                                try {
                                    fileChannel2.close();
                                    fileLock.lockChannel = null;
                                } finally {
                                }
                            }
                            return sQLiteConnectionOpen;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        if (fileLock != null && (fileChannel = fileLock.lockChannel) != null) {
                            try {
                                fileChannel.close();
                                fileLock.lockChannel = null;
                            } finally {
                            }
                        }
                        throw th3;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    z = false;
                }
            } else if (!baseRoomConnectionManager.isInitializing) {
            }
            try {
                if (z) {
                    throw th;
                }
                function1.mo781invoke(th);
                throw null;
            } finally {
                exclusiveLock.threadLock.unlock();
            }
        }
    }

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

    public static final void access$configureDatabase(BaseRoomConnectionManager baseRoomConnectionManager, SQLiteConnection sQLiteConnection) throws Exception {
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
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA user_version");
        try {
            sQLiteStatementPrepare.step();
            int i = (int) sQLiteStatementPrepare.getLong(0);
            sQLiteStatementPrepare.close();
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    SQLite.execSQL(sQLiteConnection, "ROLLBACK TRANSACTION");
                    throw thM3441exceptionOrNullimpl;
                }
            }
            baseRoomConnectionManager.onOpen(sQLiteConnection);
        } finally {
        }
    }

    public static void configureBusyTimeout(SQLiteConnection sQLiteConnection) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA busy_timeout");
        try {
            sQLiteStatementPrepare.step();
            long j = sQLiteStatementPrepare.getLong(0);
            sQLiteStatementPrepare.close();
            if (j < 3000) {
                SQLite.execSQL(sQLiteConnection, "PRAGMA busy_timeout = 3000");
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, th);
                throw th2;
            }
        }
    }

    public abstract List getCallbacks();

    public abstract DatabaseConfiguration getConfiguration();

    public abstract RoomOpenDelegate getOpenDelegate();

    public final void onCreate(SQLiteConnection sQLiteConnection) {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z = false;
            if (sQLiteStatementPrepare.step()) {
                if (sQLiteStatementPrepare.getLong(0) == 0) {
                    z = true;
                }
            }
            sQLiteStatementPrepare.close();
            getOpenDelegate().createAllTables(sQLiteConnection);
            if (!z) {
                RoomOpenDelegate.ValidationResult validationResultOnValidateSchema = getOpenDelegate().onValidateSchema(sQLiteConnection);
                if (!validationResultOnValidateSchema.isValid) {
                    throw new IllegalStateException(("Pre-packaged database has an invalid schema: " + validationResultOnValidateSchema.expectedFoundMsg).toString());
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

    /* JADX WARN: Removed duplicated region for block: B:122:0x009d A[EDGE_INSN: B:122:0x009d->B:39:0x009d BREAK  A[LOOP:4: B:9:0x0019->B:126:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMigrate(SQLiteConnection sQLiteConnection, int i, int i2) {
        Iterable<Migration> iterable;
        Pair pair;
        boolean z;
        RoomDatabase.MigrationContainer migrationContainer = getConfiguration().migrationContainer;
        if (i == i2) {
            iterable = EmptyList.INSTANCE;
        } else {
            boolean z2 = i2 > i;
            ArrayList arrayList = new ArrayList();
            int i3 = i;
            do {
                if (z2) {
                    if (i3 >= i2) {
                        iterable = arrayList;
                        break;
                    }
                    if (z2) {
                        TreeMap treeMap = (TreeMap) ((LinkedHashMap) migrationContainer.migrations).get(Integer.valueOf(i3));
                        if (treeMap != null) {
                            pair = new Pair(treeMap, treeMap.keySet());
                        }
                        if (pair != null) {
                        }
                    } else {
                        TreeMap treeMap2 = (TreeMap) ((LinkedHashMap) migrationContainer.migrations).get(Integer.valueOf(i3));
                        pair = treeMap2 == null ? null : new Pair(treeMap2, treeMap2.descendingKeySet());
                        if (pair != null) {
                            break;
                        }
                        Map map = (Map) pair.component1();
                        Iterator it = ((Iterable) pair.component2()).iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            if (z2) {
                                if (i3 + 1 <= iIntValue && iIntValue <= i2) {
                                    Object obj = map.get(Integer.valueOf(iIntValue));
                                    obj.getClass();
                                    arrayList.add(obj);
                                    z = true;
                                    i3 = iIntValue;
                                    break;
                                }
                            } else {
                                if (i2 <= iIntValue && iIntValue < i3) {
                                    Object obj2 = map.get(Integer.valueOf(iIntValue));
                                    obj2.getClass();
                                    arrayList.add(obj2);
                                    z = true;
                                    i3 = iIntValue;
                                    break;
                                    break;
                                }
                            }
                        }
                        z = false;
                    }
                } else {
                    if (i3 <= i2) {
                        iterable = arrayList;
                        break;
                    }
                    if (z2) {
                    }
                }
            } while (z);
            iterable = null;
        }
        if (iterable != null) {
            getOpenDelegate().onPreMigrate(sQLiteConnection);
            for (Migration migration : iterable) {
                migration.getClass();
                if (!(sQLiteConnection instanceof SupportSQLiteConnection)) {
                    throw new NotImplementedError("Migration functionality with a provided SQLiteDriver requires overriding the migrate(SQLiteConnection) function.");
                }
                migration.migrate(((SupportSQLiteConnection) sQLiteConnection).db);
            }
            RoomOpenDelegate.ValidationResult validationResultOnValidateSchema = getOpenDelegate().onValidateSchema(sQLiteConnection);
            if (!validationResultOnValidateSchema.isValid) {
                throw new IllegalStateException(("Migration didn't properly handle: " + validationResultOnValidateSchema.expectedFoundMsg).toString());
            }
            getOpenDelegate().onPostMigrate();
            updateIdentity(sQLiteConnection);
            return;
        }
        if (MigrationUtil.isMigrationRequired(getConfiguration(), i, i2)) {
            throw new IllegalStateException(("A migration from " + i + " to " + i2 + " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* functions.").toString());
        }
        if (getConfiguration().allowDestructiveMigrationForAllTables) {
            SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT name, type FROM sqlite_master WHERE type = 'table' OR type = 'view'");
            try {
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                while (sQLiteStatementPrepare.step()) {
                    String text = sQLiteStatementPrepare.getText(0);
                    if (!text.startsWith("sqlite_") && !text.equals("android_metadata")) {
                        listBuilderCreateListBuilder.add(new Pair(text, Boolean.valueOf(Intrinsics.areEqual(sQLiteStatementPrepare.getText(1), "view"))));
                    }
                }
                ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
                sQLiteStatementPrepare.close();
                ListIterator listIterator = listBuilderBuild.listIterator(0);
                while (true) {
                    ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
                    if (!itr.hasNext()) {
                        break;
                    }
                    Pair pair2 = (Pair) itr.next();
                    String str = (String) pair2.component1();
                    if (((Boolean) pair2.component2()).booleanValue()) {
                        SQLite.execSQL(sQLiteConnection, "DROP VIEW IF EXISTS " + str);
                    } else {
                        SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS " + str);
                    }
                }
            } finally {
            }
        } else {
            getOpenDelegate().dropAllTables(sQLiteConnection);
        }
        Iterator it2 = getCallbacks().iterator();
        while (it2.hasNext()) {
            ((RoomDatabase.Callback) it2.next()).getClass();
            if (sQLiteConnection instanceof SupportSQLiteConnection) {
                SupportSQLiteDatabase supportSQLiteDatabase = ((SupportSQLiteConnection) sQLiteConnection).db;
            }
        }
        getOpenDelegate().createAllTables(sQLiteConnection);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onOpen(SQLiteConnection sQLiteConnection) {
        boolean z;
        Object failure;
        RoomOpenDelegate.ValidationResult validationResultOnValidateSchema;
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name = 'room_master_table'");
        try {
            if (sQLiteStatementPrepare.step()) {
                z = sQLiteStatementPrepare.getLong(0) != 0;
            }
            sQLiteStatementPrepare.close();
            if (z) {
                sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT identity_hash FROM room_master_table WHERE id = 42 LIMIT 1");
                try {
                    String text = sQLiteStatementPrepare.step() ? sQLiteStatementPrepare.getText(0) : null;
                    sQLiteStatementPrepare.close();
                    if (!Intrinsics.areEqual(getOpenDelegate().identityHash, text) && !Intrinsics.areEqual(getOpenDelegate().legacyIdentityHash, text)) {
                        throw new IllegalStateException(("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number. Expected identity hash: " + getOpenDelegate().identityHash + ", found: " + text).toString());
                    }
                } finally {
                }
            } else {
                SQLite.execSQL(sQLiteConnection, "BEGIN EXCLUSIVE TRANSACTION");
                try {
                    int i = Result.$r8$clinit;
                    validationResultOnValidateSchema = getOpenDelegate().onValidateSchema(sQLiteConnection);
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                if (!validationResultOnValidateSchema.isValid) {
                    throw new IllegalStateException(("Pre-packaged database has an invalid schema: " + validationResultOnValidateSchema.expectedFoundMsg).toString());
                }
                getOpenDelegate().onPostMigrate();
                updateIdentity(sQLiteConnection);
                failure = Unit.INSTANCE;
                if (!(failure instanceof Result.Failure)) {
                    SQLite.execSQL(sQLiteConnection, "END TRANSACTION");
                }
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    SQLite.execSQL(sQLiteConnection, "ROLLBACK TRANSACTION");
                    throw thM3441exceptionOrNullimpl;
                }
                Result.m3440boximpl(failure);
            }
            getOpenDelegate().onOpen(sQLiteConnection);
            for (RoomDatabase.Callback callback : getCallbacks()) {
                callback.getClass();
                if (sQLiteConnection instanceof SupportSQLiteConnection) {
                    callback.onOpen(((SupportSQLiteConnection) sQLiteConnection).db);
                }
            }
            this.isConfigured = true;
        } finally {
            try {
                throw th;
            } finally {
            }
        }
    }

    public final void updateIdentity(SQLiteConnection sQLiteConnection) throws Exception {
        SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        String str = getOpenDelegate().identityHash;
        int i = RoomMasterTable.$r8$clinit;
        SQLite.execSQL(sQLiteConnection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '" + str + "')");
    }

    public String resolveFileName$room_runtime_release(String str) {
        return str;
    }
}
