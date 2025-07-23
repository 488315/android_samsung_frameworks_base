package androidx.room;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.arch.core.executor.ArchTaskExecutor$$ExternalSyntheticLambda0;
import androidx.room.concurrent.CloseBarrier;
import androidx.room.migration.Migration;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RoomDatabase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean allowMainThreadQueries;
    public RoomConnectionManager connectionManager;
    public ContextScope coroutineScope;
    public Executor internalQueryExecutor;
    public InvalidationTracker internalTracker;
    public TransactionExecutor internalTransactionExecutor;
    public CoroutineContext transactionContext;
    public final CloseBarrier closeBarrier = new CloseBarrier(new RoomDatabase$closeBarrier$1(this));
    public final ThreadLocal suspendingTransactionId = new ThreadLocal();
    public final Map typeConverters = new LinkedHashMap();
    public boolean useTempTrackingTable = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class JournalMode {
        public static final /* synthetic */ JournalMode[] $VALUES;
        public static final JournalMode AUTOMATIC;
        public static final JournalMode TRUNCATE;
        public static final JournalMode WRITE_AHEAD_LOGGING;

        static {
            JournalMode journalMode = new JournalMode("AUTOMATIC", 0);
            AUTOMATIC = journalMode;
            JournalMode journalMode2 = new JournalMode("TRUNCATE", 1);
            TRUNCATE = journalMode2;
            JournalMode journalMode3 = new JournalMode("WRITE_AHEAD_LOGGING", 2);
            WRITE_AHEAD_LOGGING = journalMode3;
            JournalMode[] journalModeArr = {journalMode, journalMode2, journalMode3};
            $VALUES = journalModeArr;
            EnumEntriesKt.enumEntries(journalModeArr);
        }

        private JournalMode(String str, int i) {
        }

        public static JournalMode valueOf(String str) {
            return (JournalMode) Enum.valueOf(JournalMode.class, str);
        }

        public static JournalMode[] values() {
            return (JournalMode[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MigrationContainer {
        public final Map migrations = new LinkedHashMap();

        public final void addMigration(Migration migration) {
            int i = migration.startVersion;
            Map map = this.migrations;
            Integer valueOf = Integer.valueOf(i);
            LinkedHashMap linkedHashMap = (LinkedHashMap) map;
            Object obj = linkedHashMap.get(valueOf);
            if (obj == null) {
                obj = new TreeMap();
                linkedHashMap.put(valueOf, obj);
            }
            TreeMap treeMap = (TreeMap) obj;
            int i2 = migration.endVersion;
            if (treeMap.containsKey(Integer.valueOf(i2))) {
                Log.w("ROOM", "Overriding migration " + treeMap.get(Integer.valueOf(i2)) + " with " + migration);
            }
            treeMap.put(Integer.valueOf(i2), migration);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class PrepackagedDatabaseCallback {
    }

    static {
        new Companion(null);
    }

    public abstract InvalidationTracker createInvalidationTracker();

    public RoomOpenDelegate createOpenDelegate() {
        throw new NotImplementedError(null, 1, null);
    }

    public List getAutoMigrations() {
        return EmptyList.INSTANCE;
    }

    public Set getRequiredAutoMigrationSpecs() {
        return EmptySet.INSTANCE;
    }

    public Map getRequiredTypeConverters() {
        return MapsKt__MapsKt.emptyMap();
    }

    public final boolean inCompatibilityMode$room_runtime_release() {
        RoomConnectionManager roomConnectionManager = this.connectionManager;
        if (roomConnectionManager == null) {
            roomConnectionManager = null;
        }
        return roomConnectionManager.getSupportOpenHelper$room_runtime_release() != null;
    }

    public final boolean inTransaction() {
        if (!isOpenInternal()) {
            return false;
        }
        RoomConnectionManager roomConnectionManager = this.connectionManager;
        if (roomConnectionManager == null) {
            roomConnectionManager = null;
        }
        SupportSQLiteOpenHelper supportOpenHelper$room_runtime_release = roomConnectionManager.getSupportOpenHelper$room_runtime_release();
        if (supportOpenHelper$room_runtime_release != null) {
            return supportOpenHelper$room_runtime_release.getWritableDatabase().inTransaction();
        }
        throw new IllegalStateException("Cannot return a SupportSQLiteOpenHelper since no SupportSQLiteOpenHelper.Factory was configured with Room.");
    }

    public final void internalInitInvalidationTracker(SQLiteConnection sQLiteConnection) {
        InvalidationTracker invalidationTracker = this.internalTracker;
        if (invalidationTracker == null) {
            invalidationTracker = null;
        }
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = invalidationTracker.implementation;
        triggerBasedInvalidationTracker.getClass();
        SQLiteStatement prepare = sQLiteConnection.prepare("PRAGMA query_only");
        try {
            prepare.step();
            boolean z = prepare.getBoolean();
            prepare.close();
            if (!z) {
                SQLite.execSQL(sQLiteConnection, "PRAGMA temp_store = MEMORY");
                SQLite.execSQL(sQLiteConnection, "PRAGMA recursive_triggers = 1");
                SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS room_table_modification_log");
                if (triggerBasedInvalidationTracker.useTempTable) {
                    SQLite.execSQL(sQLiteConnection, "CREATE TEMP TABLE IF NOT EXISTS room_table_modification_log (table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)");
                } else {
                    SQLite.execSQL(sQLiteConnection, StringsKt__StringsJVMKt.replace$default("CREATE TEMP TABLE IF NOT EXISTS room_table_modification_log (table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)", "TEMP", ""));
                }
                ObservedTableStates observedTableStates = triggerBasedInvalidationTracker.observedTableStates;
                ReentrantLock reentrantLock = observedTableStates.lock;
                reentrantLock.lock();
                try {
                    observedTableStates.needsSync = true;
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            synchronized (invalidationTracker.trackerLock) {
                try {
                    MultiInstanceInvalidationClient multiInstanceInvalidationClient = invalidationTracker.multiInstanceInvalidationClient;
                    if (multiInstanceInvalidationClient != null) {
                        Intent intent = invalidationTracker.multiInstanceInvalidationIntent;
                        if (intent == null) {
                            throw new IllegalStateException("Required value was null.");
                        }
                        multiInstanceInvalidationClient.start(intent);
                        Unit unit2 = Unit.INSTANCE;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
        }
    }

    public final boolean isOpenInternal() {
        RoomConnectionManager roomConnectionManager = this.connectionManager;
        if (roomConnectionManager == null) {
            roomConnectionManager = null;
        }
        SupportSQLiteDatabase supportSQLiteDatabase = roomConnectionManager.supportDatabase;
        if (supportSQLiteDatabase != null) {
            return supportSQLiteDatabase.isOpen();
        }
        return false;
    }

    public final Object useConnection$room_runtime_release(boolean z, Function2 function2, ContinuationImpl continuationImpl) {
        RoomConnectionManager roomConnectionManager = this.connectionManager;
        if (roomConnectionManager == null) {
            roomConnectionManager = null;
        }
        return roomConnectionManager.connectionPool.useConnection(z, function2, continuationImpl);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public boolean allowDestructiveMigrationForAllTables;
        public boolean allowDestructiveMigrationOnDowngrade;
        public final long autoCloseTimeout;
        public final List autoMigrationSpecs;
        public final List callbacks;
        public final Context context;
        public final Function0 factory;
        public final boolean inMemoryTrackingTableMode;
        public final JournalMode journalMode;
        public final KClass klass;
        public final MigrationContainer migrationContainer;
        public final Set migrationStartAndEndVersions;
        public final Set migrationsNotRequiredFrom;
        public final String name;
        public ArchTaskExecutor$$ExternalSyntheticLambda0 queryExecutor;
        public boolean requireMigration;
        public ArchTaskExecutor$$ExternalSyntheticLambda0 transactionExecutor;
        public final List typeConverters;

        public Builder(KClass kClass, String str, Function0 function0, Context context) {
            this.callbacks = new ArrayList();
            this.typeConverters = new ArrayList();
            this.journalMode = JournalMode.AUTOMATIC;
            this.autoCloseTimeout = -1L;
            this.migrationContainer = new MigrationContainer();
            this.migrationsNotRequiredFrom = new LinkedHashSet();
            this.migrationStartAndEndVersions = new LinkedHashSet();
            this.autoMigrationSpecs = new ArrayList();
            this.requireMigration = true;
            this.inMemoryTrackingTableMode = true;
            this.klass = kClass;
            this.context = context;
            this.name = str;
            this.factory = function0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:81:0x0264, code lost:
        
            throw new java.lang.IllegalArgumentException("Unexpected auto migration specs found. Annotate AutoMigrationSpec implementation with @ProvidedAutoMigrationSpec annotation or remove this spec from the builder.");
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:168:0x04ce  */
        /* JADX WARN: Removed duplicated region for block: B:171:0x04d4  */
        /* JADX WARN: Removed duplicated region for block: B:177:0x04f2  */
        /* JADX WARN: Removed duplicated region for block: B:186:0x050d  */
        /* JADX WARN: Removed duplicated region for block: B:195:0x04dd  */
        /* JADX WARN: Type inference failed for: r10v10 */
        /* JADX WARN: Type inference failed for: r10v12, types: [androidx.room.RoomConnectionManager] */
        /* JADX WARN: Type inference failed for: r10v20, types: [androidx.room.RoomConnectionManager] */
        /* JADX WARN: Type inference failed for: r10v28, types: [androidx.room.InvalidationTracker] */
        /* JADX WARN: Type inference failed for: r10v29 */
        /* JADX WARN: Type inference failed for: r10v31, types: [kotlinx.coroutines.CoroutineScope] */
        /* JADX WARN: Type inference failed for: r10v33, types: [androidx.room.InvalidationTracker, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r10v34 */
        /* JADX WARN: Type inference failed for: r10v35 */
        /* JADX WARN: Type inference failed for: r10v37 */
        /* JADX WARN: Type inference failed for: r10v39 */
        /* JADX WARN: Type inference failed for: r10v41, types: [kotlinx.coroutines.internal.ContextScope] */
        /* JADX WARN: Type inference failed for: r10v42 */
        /* JADX WARN: Type inference failed for: r10v44, types: [kotlinx.coroutines.internal.ContextScope] */
        /* JADX WARN: Type inference failed for: r10v45 */
        /* JADX WARN: Type inference failed for: r10v6, types: [java.util.concurrent.Executor] */
        /* JADX WARN: Type inference failed for: r10v63 */
        /* JADX WARN: Type inference failed for: r10v64 */
        /* JADX WARN: Type inference failed for: r10v65 */
        /* JADX WARN: Type inference failed for: r10v66 */
        /* JADX WARN: Type inference failed for: r10v67 */
        /* JADX WARN: Type inference failed for: r10v68 */
        /* JADX WARN: Type inference failed for: r10v69 */
        /* JADX WARN: Type inference failed for: r10v70 */
        /* JADX WARN: Type inference failed for: r10v71 */
        /* JADX WARN: Type inference failed for: r10v8, types: [java.util.concurrent.Executor] */
        /* JADX WARN: Type inference failed for: r10v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.room.RoomDatabase build() {
            /*
                Method dump skipped, instructions count: 1428
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomDatabase.Builder.build():androidx.room.RoomDatabase");
        }

        public Builder(Context context, Class<RoomDatabase> cls, String str) {
            this.callbacks = new ArrayList();
            this.typeConverters = new ArrayList();
            this.journalMode = JournalMode.AUTOMATIC;
            this.autoCloseTimeout = -1L;
            this.migrationContainer = new MigrationContainer();
            this.migrationsNotRequiredFrom = new LinkedHashSet();
            this.migrationStartAndEndVersions = new LinkedHashSet();
            this.autoMigrationSpecs = new ArrayList();
            this.requireMigration = true;
            this.inMemoryTrackingTableMode = true;
            this.klass = Reflection.getOrCreateKotlinClass(cls);
            this.context = context;
            this.name = str;
            this.factory = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Callback {
        public void onCreate() {
        }

        public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }
}
