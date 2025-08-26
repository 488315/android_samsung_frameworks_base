package androidx.room;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.ArchTaskExecutor$$ExternalSyntheticLambda0;
import androidx.room.concurrent.CloseBarrier;
import androidx.room.migration.Migration;
import androidx.room.support.AutoCloser;
import androidx.room.support.AutoClosingRoomOpenHelper;
import androidx.room.support.PrePackagedCopyOpenHelper;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.ContextScope;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    public class MigrationContainer {
        public final Map migrations = new LinkedHashMap();

        public final void addMigration(Migration migration) {
            int i = migration.startVersion;
            Map map = this.migrations;
            Integer numValueOf = Integer.valueOf(i);
            LinkedHashMap linkedHashMap = (LinkedHashMap) map;
            Object treeMap = linkedHashMap.get(numValueOf);
            if (treeMap == null) {
                treeMap = new TreeMap();
                linkedHashMap.put(numValueOf, treeMap);
            }
            TreeMap treeMap2 = (TreeMap) treeMap;
            int i2 = migration.endVersion;
            if (treeMap2.containsKey(Integer.valueOf(i2))) {
                Log.w("ROOM", "Overriding migration " + treeMap2.get(Integer.valueOf(i2)) + " with " + migration);
            }
            treeMap2.put(Integer.valueOf(i2), migration);
        }
    }

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

    public final void internalInitInvalidationTracker(SQLiteConnection sQLiteConnection) throws Exception {
        InvalidationTracker invalidationTracker = this.internalTracker;
        if (invalidationTracker == null) {
            invalidationTracker = null;
        }
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = invalidationTracker.implementation;
        triggerBasedInvalidationTracker.getClass();
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA query_only");
        try {
            sQLiteStatementPrepare.step();
            boolean z = sQLiteStatementPrepare.getBoolean();
            sQLiteStatementPrepare.close();
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

        /* JADX WARN: Code restructure failed: missing block: B:109:0x0264, code lost:
        
            throw new java.lang.IllegalArgumentException("Unexpected auto migration specs found. Annotate AutoMigrationSpec implementation with @ProvidedAutoMigrationSpec annotation or remove this spec from the builder.");
         */
        /* JADX WARN: Multi-variable type inference failed */
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
        */
        public final RoomDatabase build() {
            String name;
            boolean z;
            RoomOpenDelegate roomOpenDelegate;
            final RoomDatabase roomDatabase;
            RoomOpenDelegate roomOpenDelegateCreateOpenDelegate;
            Object delegate;
            Object delegate2;
            CoroutineContext coroutineContextPlus;
            boolean zContainsKey;
            ArchTaskExecutor$$ExternalSyntheticLambda0 archTaskExecutor$$ExternalSyntheticLambda0 = this.queryExecutor;
            if (archTaskExecutor$$ExternalSyntheticLambda0 == null && this.transactionExecutor == null) {
                ArchTaskExecutor$$ExternalSyntheticLambda0 archTaskExecutor$$ExternalSyntheticLambda02 = ArchTaskExecutor.sIOThreadExecutor;
                this.transactionExecutor = archTaskExecutor$$ExternalSyntheticLambda02;
                this.queryExecutor = archTaskExecutor$$ExternalSyntheticLambda02;
            } else if (archTaskExecutor$$ExternalSyntheticLambda0 != null && this.transactionExecutor == null) {
                this.transactionExecutor = archTaskExecutor$$ExternalSyntheticLambda0;
            } else if (archTaskExecutor$$ExternalSyntheticLambda0 == null) {
                this.queryExecutor = this.transactionExecutor;
            }
            Set set = this.migrationStartAndEndVersions;
            Set set2 = this.migrationsNotRequiredFrom;
            if (!set.isEmpty()) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    if (set2.contains(Integer.valueOf(iIntValue))) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue, "Inconsistency detected. A Migration was supplied to addMigration() that has a start or end version equal to a start version supplied to fallbackToDestructiveMigrationFrom(). Start version is: ").toString());
                    }
                }
            }
            FrameworkSQLiteOpenHelperFactory frameworkSQLiteOpenHelperFactory = new FrameworkSQLiteOpenHelperFactory();
            if (this.autoCloseTimeout > 0) {
                if (this.name != null) {
                    throw new IllegalArgumentException("Required value was null.");
                }
                throw new IllegalArgumentException("Cannot create auto-closing database for an in-memory database.");
            }
            Context context = this.context;
            List list = this.callbacks;
            JournalMode journalMode = this.journalMode;
            journalMode.getClass();
            if (journalMode == JournalMode.AUTOMATIC) {
                Object systemService = context.getSystemService("activity");
                ActivityManager activityManager = systemService instanceof ActivityManager ? (ActivityManager) systemService : null;
                journalMode = (activityManager == null || activityManager.isLowRamDevice()) ? JournalMode.TRUNCATE : JournalMode.WRITE_AHEAD_LOGGING;
            }
            JournalMode journalMode2 = journalMode;
            ArchTaskExecutor$$ExternalSyntheticLambda0 archTaskExecutor$$ExternalSyntheticLambda03 = this.queryExecutor;
            if (archTaskExecutor$$ExternalSyntheticLambda03 == null) {
                throw new IllegalArgumentException("Required value was null.");
            }
            ArchTaskExecutor$$ExternalSyntheticLambda0 archTaskExecutor$$ExternalSyntheticLambda04 = this.transactionExecutor;
            if (archTaskExecutor$$ExternalSyntheticLambda04 == null) {
                throw new IllegalArgumentException("Required value was null.");
            }
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(context, this.name, frameworkSQLiteOpenHelperFactory, this.migrationContainer, list, false, journalMode2, archTaskExecutor$$ExternalSyntheticLambda03, archTaskExecutor$$ExternalSyntheticLambda04, null, this.requireMigration, this.allowDestructiveMigrationOnDowngrade, this.migrationsNotRequiredFrom, null, null, null, null, this.typeConverters, this.autoMigrationSpecs, this.allowDestructiveMigrationForAllTables, null, null);
            databaseConfiguration.useTempTrackingTable = this.inMemoryTrackingTableMode;
            Function0 function0 = this.factory;
            if (function0 == null || (roomDatabase = (RoomDatabase) function0.invoke()) == null) {
                Class jClass = ((ClassBasedDeclarationContainer) this.klass).getJClass();
                Package r0 = jClass.getPackage();
                if (r0 == null || (name = r0.getName()) == null) {
                    name = "";
                }
                String canonicalName = jClass.getCanonicalName();
                canonicalName.getClass();
                if (name.length() != 0) {
                    canonicalName = canonicalName.substring(name.length() + 1);
                }
                String str = canonicalName.replace('.', '_') + "_Impl";
                try {
                    z = false;
                    Class[] clsArr = new Class[0];
                    roomOpenDelegate = null;
                    roomDatabase = (RoomDatabase) Class.forName(name.length() == 0 ? str : name + '.' + str, true, jClass.getClassLoader()).getDeclaredConstructor(null).newInstance(null);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Cannot find implementation for " + jClass.getCanonicalName() + ". " + str + " does not exist. Is Room annotation processor correctly configured?", e);
                } catch (IllegalAccessException e2) {
                    throw new RuntimeException("Cannot access the constructor " + jClass.getCanonicalName(), e2);
                } catch (InstantiationException e3) {
                    throw new RuntimeException("Failed to create an instance of " + jClass.getCanonicalName(), e3);
                }
            } else {
                z = false;
                roomOpenDelegate = null;
            }
            roomDatabase.getClass();
            roomDatabase.useTempTrackingTable = databaseConfiguration.useTempTrackingTable;
            try {
                roomOpenDelegateCreateOpenDelegate = roomDatabase.createOpenDelegate();
            } catch (NotImplementedError unused) {
                roomOpenDelegateCreateOpenDelegate = roomOpenDelegate;
            }
            roomDatabase.connectionManager = roomOpenDelegateCreateOpenDelegate == null ? new RoomConnectionManager(databaseConfiguration, new Function1() { // from class: androidx.room.RoomDatabase$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int i = RoomDatabase.$r8$clinit;
                    throw new NotImplementedError(null, 1, null);
                }
            }) : new RoomConnectionManager(databaseConfiguration, roomOpenDelegateCreateOpenDelegate);
            roomDatabase.internalTracker = roomDatabase.createInvalidationTracker();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Set requiredAutoMigrationSpecs = roomDatabase.getRequiredAutoMigrationSpecs();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(requiredAutoMigrationSpecs, 10));
            Iterator it2 = requiredAutoMigrationSpecs.iterator();
            while (it2.hasNext()) {
                arrayList.add(Reflection.getOrCreateKotlinClass((Class) it2.next()));
            }
            Set set3 = CollectionsKt___CollectionsKt.toSet(arrayList);
            int size = set3.size();
            boolean[] zArr = new boolean[size];
            Iterator it3 = set3.iterator();
            while (true) {
                int i = -1;
                if (!it3.hasNext()) {
                    int size2 = databaseConfiguration.autoMigrationSpecs.size() - 1;
                    if (size2 >= 0) {
                        while (true) {
                            int i2 = size2 - 1;
                            if (size2 >= size || !zArr[size2]) {
                                break;
                            }
                            if (i2 < 0) {
                                break;
                            }
                            size2 = i2;
                        }
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        linkedHashMap2.put(((ClassBasedDeclarationContainer) ((KClass) entry.getKey())).getJClass(), entry.getValue());
                    }
                    for (Migration migration : roomDatabase.getAutoMigrations()) {
                        int i3 = migration.startVersion;
                        MigrationContainer migrationContainer = databaseConfiguration.migrationContainer;
                        Map map = migrationContainer.migrations;
                        if (map.containsKey(Integer.valueOf(i3))) {
                            Map mapEmptyMap = (Map) ((LinkedHashMap) map).get(Integer.valueOf(i3));
                            if (mapEmptyMap == null) {
                                mapEmptyMap = MapsKt__MapsKt.emptyMap();
                            }
                            zContainsKey = mapEmptyMap.containsKey(Integer.valueOf(migration.endVersion));
                        } else {
                            zContainsKey = z;
                        }
                        if (!zContainsKey) {
                            migrationContainer.addMigration(migration);
                        }
                    }
                    Set<Map.Entry> setEntrySet = roomDatabase.getRequiredTypeConverters().entrySet();
                    int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
                    if (iMapCapacity < 16) {
                        iMapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap3 = new LinkedHashMap(iMapCapacity);
                    for (Map.Entry entry2 : setEntrySet) {
                        Class cls = (Class) entry2.getKey();
                        List list2 = (List) entry2.getValue();
                        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(cls);
                        List list3 = list2;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                        Iterator it4 = list3.iterator();
                        while (it4.hasNext()) {
                            arrayList2.add(Reflection.getOrCreateKotlinClass((Class) it4.next()));
                        }
                        Pair pair = new Pair(orCreateKotlinClass, arrayList2);
                        linkedHashMap3.put(pair.getFirst(), pair.getSecond());
                    }
                    boolean[] zArr2 = new boolean[linkedHashMap3.size()];
                    for (Map.Entry entry3 : linkedHashMap3.entrySet()) {
                        KClass kClass = (KClass) entry3.getKey();
                        for (KClass kClass2 : (List) entry3.getValue()) {
                            int size3 = databaseConfiguration.typeConverters.size() - 1;
                            if (size3 >= 0) {
                                while (true) {
                                    int i4 = size3 - 1;
                                    if (((ClassReference) kClass2).isInstance(databaseConfiguration.typeConverters.get(size3))) {
                                        zArr2[size3] = true;
                                        break;
                                    }
                                    if (i4 < 0) {
                                        break;
                                    }
                                    size3 = i4;
                                }
                                size3 = -1;
                            } else {
                                size3 = -1;
                            }
                            if (size3 < 0) {
                                throw new IllegalArgumentException(("A required type converter (" + ((ClassReference) kClass2).getQualifiedName() + ") for " + ((ClassReference) kClass).getQualifiedName() + " is missing in the database configuration.").toString());
                            }
                            roomDatabase.typeConverters.put(kClass2, databaseConfiguration.typeConverters.get(size3));
                        }
                    }
                    int size4 = databaseConfiguration.typeConverters.size() - 1;
                    if (size4 >= 0) {
                        while (true) {
                            int i5 = size4 - 1;
                            if (!zArr2[size4]) {
                                throw new IllegalArgumentException("Unexpected type converter " + databaseConfiguration.typeConverters.get(size4) + ". Annotate TypeConverter class with @ProvidedTypeConverter annotation or remove this converter from the builder.");
                            }
                            if (i5 < 0) {
                                break;
                            }
                            size4 = i5;
                        }
                    }
                    CoroutineContext coroutineContext = databaseConfiguration.queryCoroutineContext;
                    if (coroutineContext != null) {
                        CoroutineDispatcher coroutineDispatcher = (CoroutineDispatcher) coroutineContext.get(ContinuationInterceptor.Key);
                        Executor executorAsExecutor = ExecutorsKt.asExecutor(coroutineDispatcher);
                        roomDatabase.internalQueryExecutor = executorAsExecutor;
                        roomDatabase.internalTransactionExecutor = new TransactionExecutor(executorAsExecutor);
                        roomDatabase.coroutineScope = CoroutineScopeKt.CoroutineScope(coroutineContext.plus(new SupervisorJobImpl((Job) coroutineContext.get(Job.Key))));
                        if (roomDatabase.inCompatibilityMode$room_runtime_release()) {
                            ContextScope contextScope = roomDatabase.coroutineScope;
                            ?? r10 = contextScope;
                            if (contextScope == null) {
                                r10 = roomOpenDelegate;
                            }
                            coroutineContextPlus = r10.coroutineContext.plus(coroutineDispatcher.limitedParallelism(1));
                        } else {
                            ContextScope contextScope2 = roomDatabase.coroutineScope;
                            ?? r102 = contextScope2;
                            if (contextScope2 == null) {
                                r102 = roomOpenDelegate;
                            }
                            coroutineContextPlus = r102.coroutineContext;
                        }
                        roomDatabase.transactionContext = coroutineContextPlus;
                    } else {
                        roomDatabase.internalQueryExecutor = databaseConfiguration.queryExecutor;
                        roomDatabase.internalTransactionExecutor = new TransactionExecutor(databaseConfiguration.transactionExecutor);
                        Executor executor = roomDatabase.internalQueryExecutor;
                        ?? r103 = executor;
                        if (executor == null) {
                            r103 = roomOpenDelegate;
                        }
                        ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(ExecutorsKt.from(r103), SupervisorKt.SupervisorJob$default()));
                        roomDatabase.coroutineScope = contextScopeCoroutineScope;
                        TransactionExecutor transactionExecutor = roomDatabase.internalTransactionExecutor;
                        ?? r104 = transactionExecutor;
                        if (transactionExecutor == null) {
                            r104 = roomOpenDelegate;
                        }
                        roomDatabase.transactionContext = contextScopeCoroutineScope.coroutineContext.plus(ExecutorsKt.from(r104));
                    }
                    roomDatabase.allowMainThreadQueries = databaseConfiguration.allowMainThreadQueries;
                    RoomConnectionManager roomConnectionManager = roomDatabase.connectionManager;
                    ?? r105 = roomConnectionManager;
                    if (roomConnectionManager == null) {
                        r105 = roomOpenDelegate;
                    }
                    Object supportOpenHelper$room_runtime_release = r105.getSupportOpenHelper$room_runtime_release();
                    if (supportOpenHelper$room_runtime_release == null) {
                        delegate = roomOpenDelegate;
                        break;
                    }
                    delegate = supportOpenHelper$room_runtime_release;
                    while (!(delegate instanceof PrePackagedCopyOpenHelper)) {
                        if (!(delegate instanceof DelegatingOpenHelper)) {
                            delegate = roomOpenDelegate;
                            break;
                        }
                        delegate = ((DelegatingOpenHelper) delegate).getDelegate();
                    }
                    PrePackagedCopyOpenHelper prePackagedCopyOpenHelper = (PrePackagedCopyOpenHelper) delegate;
                    if (prePackagedCopyOpenHelper != null) {
                        prePackagedCopyOpenHelper.databaseConfiguration = databaseConfiguration;
                    }
                    RoomConnectionManager roomConnectionManager2 = roomDatabase.connectionManager;
                    ?? r106 = roomConnectionManager2;
                    if (roomConnectionManager2 == null) {
                        r106 = roomOpenDelegate;
                    }
                    Object supportOpenHelper$room_runtime_release2 = r106.getSupportOpenHelper$room_runtime_release();
                    if (supportOpenHelper$room_runtime_release2 == null) {
                        delegate2 = roomOpenDelegate;
                        break;
                    }
                    delegate2 = supportOpenHelper$room_runtime_release2;
                    while (!(delegate2 instanceof AutoClosingRoomOpenHelper)) {
                        if (!(delegate2 instanceof DelegatingOpenHelper)) {
                            delegate2 = roomOpenDelegate;
                            break;
                        }
                        delegate2 = ((DelegatingOpenHelper) delegate2).getDelegate();
                    }
                    AutoClosingRoomOpenHelper autoClosingRoomOpenHelper = (AutoClosingRoomOpenHelper) delegate2;
                    if (autoClosingRoomOpenHelper != null) {
                        AutoCloser autoCloser = autoClosingRoomOpenHelper.autoCloser;
                        ContextScope contextScope3 = roomDatabase.coroutineScope;
                        ?? r107 = contextScope3;
                        if (contextScope3 == null) {
                            r107 = roomOpenDelegate;
                        }
                        autoCloser.coroutineScope = r107;
                        InvalidationTracker invalidationTracker = roomDatabase.internalTracker;
                        ?? r108 = invalidationTracker;
                        if (invalidationTracker == null) {
                            r108 = roomOpenDelegate;
                        }
                        r108.autoCloser = autoCloser;
                        autoCloser.onAutoCloseCallback = new InvalidationTracker$setAutoCloser$1(r108);
                    }
                    Intent intent = databaseConfiguration.multiInstanceInvalidationServiceIntent;
                    if (intent != null) {
                        String str2 = databaseConfiguration.name;
                        if (str2 == null) {
                            throw new IllegalArgumentException("Required value was null.");
                        }
                        InvalidationTracker invalidationTracker2 = roomDatabase.internalTracker;
                        ?? r109 = invalidationTracker2;
                        if (invalidationTracker2 == null) {
                            r109 = roomOpenDelegate;
                        }
                        Context context2 = databaseConfiguration.context;
                        r109.multiInstanceInvalidationIntent = intent;
                        r109.multiInstanceInvalidationClient = new MultiInstanceInvalidationClient(context2, str2, r109);
                    }
                    return roomDatabase;
                }
                KClass kClass3 = (KClass) it3.next();
                int size5 = databaseConfiguration.autoMigrationSpecs.size() - 1;
                if (size5 >= 0) {
                    while (true) {
                        int i6 = size5 - 1;
                        if (((ClassReference) kClass3).isInstance(databaseConfiguration.autoMigrationSpecs.get(size5))) {
                            zArr[size5] = true;
                            i = size5;
                            break;
                        }
                        if (i6 < 0) {
                            break;
                        }
                        size5 = i6;
                    }
                }
                if (i < 0) {
                    throw new IllegalArgumentException(("A required auto migration spec (" + ((ClassReference) kClass3).getQualifiedName() + ") is missing in the database configuration.").toString());
                }
                linkedHashMap.put(kClass3, databaseConfiguration.autoMigrationSpecs.get(i));
            }
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

    public abstract class Callback {
        public void onCreate() {
        }

        public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }
}
