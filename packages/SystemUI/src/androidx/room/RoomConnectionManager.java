package androidx.room;

import android.content.Context;
import androidx.room.BaseRoomConnectionManager;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenDelegate;
import androidx.room.coroutines.AndroidSQLiteDriverConnectionPool;
import androidx.room.coroutines.ConnectionPool;
import androidx.room.coroutines.ConnectionPoolImpl;
import androidx.room.driver.SupportSQLiteConnection;
import androidx.room.driver.SupportSQLiteConnectionPool;
import androidx.room.driver.SupportSQLiteDriver;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteDatabase;
import androidx.sqlite.driver.AndroidSQLiteDriver;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class RoomConnectionManager extends BaseRoomConnectionManager {
    public final List callbacks;
    public final DatabaseConfiguration configuration;
    public final ConnectionPool connectionPool;
    public final RoomOpenDelegate openDelegate;
    public SupportSQLiteDatabase supportDatabase;

    public final class NoOpOpenDelegate extends RoomOpenDelegate {
        public NoOpOpenDelegate() {
            super(-1, "", "");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void createAllTables(SQLiteConnection sQLiteConnection) {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void dropAllTables(SQLiteConnection sQLiteConnection) {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void onCreate() {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void onOpen(SQLiteConnection sQLiteConnection) {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void onPostMigrate() {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final void onPreMigrate(SQLiteConnection sQLiteConnection) {
            throw new IllegalStateException("NOP delegate should never be called");
        }

        @Override // androidx.room.RoomOpenDelegate
        public final RoomOpenDelegate.ValidationResult onValidateSchema(SQLiteConnection sQLiteConnection) {
            throw new IllegalStateException("NOP delegate should never be called");
        }
    }

    public final class SupportOpenHelperCallback extends SupportSQLiteOpenHelper.Callback {
        public SupportOpenHelperCallback(int i) {
            super(i);
        }

        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
        public final void onCreate(FrameworkSQLiteDatabase frameworkSQLiteDatabase) {
            RoomConnectionManager.this.onCreate(new SupportSQLiteConnection(frameworkSQLiteDatabase));
        }

        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
        public final void onDowngrade(FrameworkSQLiteDatabase frameworkSQLiteDatabase, int i, int i2) {
            onUpgrade(frameworkSQLiteDatabase, i, i2);
        }

        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
        public final void onOpen(FrameworkSQLiteDatabase frameworkSQLiteDatabase) {
            SupportSQLiteConnection supportSQLiteConnection = new SupportSQLiteConnection(frameworkSQLiteDatabase);
            RoomConnectionManager roomConnectionManager = RoomConnectionManager.this;
            roomConnectionManager.onOpen(supportSQLiteConnection);
            roomConnectionManager.supportDatabase = frameworkSQLiteDatabase;
        }

        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
        public final void onUpgrade(FrameworkSQLiteDatabase frameworkSQLiteDatabase, int i, int i2) {
            RoomConnectionManager.this.onMigrate(new SupportSQLiteConnection(frameworkSQLiteDatabase), i, i2);
        }
    }

    public RoomConnectionManager(DatabaseConfiguration databaseConfiguration, RoomOpenDelegate roomOpenDelegate) {
        int i;
        ConnectionPool connectionPoolImpl;
        this.configuration = databaseConfiguration;
        this.openDelegate = roomOpenDelegate;
        List list = databaseConfiguration.callbacks;
        this.callbacks = list == null ? EmptyList.INSTANCE : list;
        String str = databaseConfiguration.name;
        SQLiteDriver sQLiteDriver = databaseConfiguration.sqliteDriver;
        if (sQLiteDriver == null) {
            SupportSQLiteOpenHelper.Factory factory = databaseConfiguration.sqliteOpenHelperFactory;
            if (factory == null) {
                throw new IllegalArgumentException("SQLiteManager was constructed with both null driver and open helper factory!");
            }
            SupportSQLiteOpenHelper.Configuration.Companion companion = SupportSQLiteOpenHelper.Configuration.Companion;
            Context context = databaseConfiguration.context;
            companion.getClass();
            SupportSQLiteOpenHelper.Configuration.Builder builder = new SupportSQLiteOpenHelper.Configuration.Builder(context);
            builder.name = str;
            SupportOpenHelperCallback supportOpenHelperCallback = new SupportOpenHelperCallback(roomOpenDelegate.version);
            builder.callback = supportOpenHelperCallback;
            this.connectionPool = new SupportSQLiteConnectionPool(new SupportSQLiteDriver(factory.create(new SupportSQLiteOpenHelper.Configuration(builder.context, builder.name, supportOpenHelperCallback, false, false))));
        } else {
            if (sQLiteDriver instanceof AndroidSQLiteDriver) {
                connectionPoolImpl = new AndroidSQLiteDriverConnectionPool(new BaseRoomConnectionManager.DriverWrapper(sQLiteDriver), str == null ? ":memory:" : str);
            } else if (str == null) {
                connectionPoolImpl = new ConnectionPoolImpl(new BaseRoomConnectionManager.DriverWrapper(sQLiteDriver), ":memory:");
            } else {
                BaseRoomConnectionManager.DriverWrapper driverWrapper = new BaseRoomConnectionManager.DriverWrapper(sQLiteDriver);
                int[] iArr = BaseRoomConnectionManager.WhenMappings.$EnumSwitchMapping$0;
                RoomDatabase.JournalMode journalMode = databaseConfiguration.journalMode;
                int i2 = iArr[journalMode.ordinal()];
                if (i2 == 1) {
                    i = 1;
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException(("Can't get max number of reader for journal mode '" + journalMode + '\'').toString());
                    }
                    i = 4;
                }
                int i3 = iArr[journalMode.ordinal()];
                if (i3 != 1 && i3 != 2) {
                    throw new IllegalStateException(("Can't get max number of writers for journal mode '" + journalMode + '\'').toString());
                }
                connectionPoolImpl = new ConnectionPoolImpl(driverWrapper, str, i, 1);
            }
            this.connectionPool = connectionPoolImpl;
        }
        boolean z = databaseConfiguration.journalMode == RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING;
        SupportSQLiteOpenHelper supportOpenHelper$room_runtime_release = getSupportOpenHelper$room_runtime_release();
        if (supportOpenHelper$room_runtime_release != null) {
            supportOpenHelper$room_runtime_release.setWriteAheadLoggingEnabled(z);
        }
    }

    @Override // androidx.room.BaseRoomConnectionManager
    public final List getCallbacks() {
        return this.callbacks;
    }

    @Override // androidx.room.BaseRoomConnectionManager
    public final DatabaseConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // androidx.room.BaseRoomConnectionManager
    public final RoomOpenDelegate getOpenDelegate() {
        return this.openDelegate;
    }

    public final SupportSQLiteOpenHelper getSupportOpenHelper$room_runtime_release() {
        SupportSQLiteDriver supportSQLiteDriver;
        ConnectionPool connectionPool = this.connectionPool;
        SupportSQLiteConnectionPool supportSQLiteConnectionPool = connectionPool instanceof SupportSQLiteConnectionPool ? (SupportSQLiteConnectionPool) connectionPool : null;
        if (supportSQLiteConnectionPool == null || (supportSQLiteDriver = supportSQLiteConnectionPool.supportDriver) == null) {
            return null;
        }
        return supportSQLiteDriver.openHelper;
    }

    @Override // androidx.room.BaseRoomConnectionManager
    public final String resolveFileName$room_runtime_release(String str) {
        if (Intrinsics.areEqual(str, ":memory:")) {
            return str;
        }
        String absolutePath = this.configuration.context.getDatabasePath(str).getAbsolutePath();
        absolutePath.getClass();
        return absolutePath;
    }

    public RoomConnectionManager(DatabaseConfiguration databaseConfiguration, Function1 function1) {
        this.configuration = databaseConfiguration;
        this.openDelegate = new NoOpOpenDelegate();
        List list = databaseConfiguration.callbacks;
        this.callbacks = list == null ? EmptyList.INSTANCE : list;
        final Function1 function12 = new Function1() { // from class: androidx.room.RoomConnectionManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                this.f$0.supportDatabase = (SupportSQLiteDatabase) obj;
                return Unit.INSTANCE;
            }
        };
        this.connectionPool = new SupportSQLiteConnectionPool(new SupportSQLiteDriver((SupportSQLiteOpenHelper) function1.mo781invoke(new DatabaseConfiguration(databaseConfiguration.context, databaseConfiguration.name, databaseConfiguration.sqliteOpenHelperFactory, databaseConfiguration.migrationContainer, CollectionsKt___CollectionsKt.plus(list == null ? EmptyList.INSTANCE : list, new RoomDatabase.Callback() { // from class: androidx.room.RoomConnectionManager$installOnOpenCallback$newCallbacks$1
            @Override // androidx.room.RoomDatabase.Callback
            public final void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                function12.mo781invoke(supportSQLiteDatabase);
            }
        }), databaseConfiguration.allowMainThreadQueries, databaseConfiguration.journalMode, databaseConfiguration.queryExecutor, databaseConfiguration.transactionExecutor, databaseConfiguration.multiInstanceInvalidationServiceIntent, databaseConfiguration.requireMigration, databaseConfiguration.allowDestructiveMigrationOnDowngrade, databaseConfiguration.migrationNotRequiredFrom, databaseConfiguration.copyFromAssetPath, databaseConfiguration.copyFromFile, databaseConfiguration.copyFromInputStream, databaseConfiguration.prepackagedDatabaseCallback, databaseConfiguration.typeConverters, databaseConfiguration.autoMigrationSpecs, databaseConfiguration.allowDestructiveMigrationForAllTables, databaseConfiguration.sqliteDriver, databaseConfiguration.queryCoroutineContext))));
        boolean z = databaseConfiguration.journalMode == RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING;
        SupportSQLiteOpenHelper supportOpenHelper$room_runtime_release = getSupportOpenHelper$room_runtime_release();
        if (supportOpenHelper$room_runtime_release != null) {
            supportOpenHelper$room_runtime_release.setWriteAheadLoggingEnabled(z);
        }
    }
}
