package androidx.room;

import android.content.Context;
import android.content.Intent;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DatabaseConfiguration {
    public final boolean allowDestructiveMigrationForAllTables;
    public final boolean allowDestructiveMigrationOnDowngrade;
    public final boolean allowMainThreadQueries;
    public final List autoMigrationSpecs;
    public final List callbacks;
    public final Context context;
    public final String copyFromAssetPath;
    public final File copyFromFile;
    public final Callable copyFromInputStream;
    public final RoomDatabase.JournalMode journalMode;
    public final RoomDatabase.MigrationContainer migrationContainer;
    public final Set migrationNotRequiredFrom;
    public final boolean multiInstanceInvalidation;
    public final Intent multiInstanceInvalidationServiceIntent;
    public final String name;
    public final RoomDatabase.PrepackagedDatabaseCallback prepackagedDatabaseCallback;
    public final CoroutineContext queryCoroutineContext;
    public final Executor queryExecutor;
    public final boolean requireMigration;
    public final SQLiteDriver sqliteDriver;
    public final SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory;
    public final Executor transactionExecutor;
    public final List typeConverters;
    public boolean useTempTrackingTable;

    public DatabaseConfiguration(Context context, String str, SupportSQLiteOpenHelper.Factory factory, RoomDatabase.MigrationContainer migrationContainer, List<? extends RoomDatabase.Callback> list, boolean z, RoomDatabase.JournalMode journalMode, Executor executor, Executor executor2, Intent intent, boolean z2, boolean z3, Set<Integer> set, String str2, File file, Callable<InputStream> callable, RoomDatabase.PrepackagedDatabaseCallback prepackagedDatabaseCallback, List<? extends Object> list2, List<Object> list3, boolean z4, SQLiteDriver sQLiteDriver, CoroutineContext coroutineContext) {
        this.context = context;
        this.name = str;
        this.sqliteOpenHelperFactory = factory;
        this.migrationContainer = migrationContainer;
        this.callbacks = list;
        this.allowMainThreadQueries = z;
        this.journalMode = journalMode;
        this.queryExecutor = executor;
        this.transactionExecutor = executor2;
        this.multiInstanceInvalidationServiceIntent = intent;
        this.requireMigration = z2;
        this.allowDestructiveMigrationOnDowngrade = z3;
        this.migrationNotRequiredFrom = set;
        this.copyFromAssetPath = str2;
        this.copyFromFile = file;
        this.copyFromInputStream = callable;
        this.prepackagedDatabaseCallback = prepackagedDatabaseCallback;
        this.typeConverters = list2;
        this.autoMigrationSpecs = list3;
        this.allowDestructiveMigrationForAllTables = z4;
        this.sqliteDriver = sQLiteDriver;
        this.queryCoroutineContext = coroutineContext;
        this.multiInstanceInvalidation = intent != null;
        this.useTempTrackingTable = true;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r24, java.lang.String r25, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r26, androidx.room.RoomDatabase.MigrationContainer r27, java.util.List<? extends androidx.room.RoomDatabase.Callback> r28, boolean r29, androidx.room.RoomDatabase.JournalMode r30, java.util.concurrent.Executor r31, boolean r32, java.util.Set<java.lang.Integer> r33) {
        /*
            r23 = this;
            kotlin.collections.EmptyList r18 = kotlin.collections.EmptyList.INSTANCE
            r21 = 0
            r22 = 0
            r10 = 0
            r12 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r20 = 0
            r9 = r31
            r19 = r18
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r11 = r32
            r13 = r33
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, boolean, java.util.Set):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            kotlin.collections.EmptyList r20 = kotlin.collections.EmptyList.INSTANCE
            r23 = 0
            r24 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r22 = 0
            r21 = r20
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38, java.lang.String r39, java.io.File r40) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            kotlin.collections.EmptyList r20 = kotlin.collections.EmptyList.INSTANCE
            r23 = 0
            r24 = 0
            r18 = 0
            r19 = 0
            r22 = 0
            r21 = r20
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r16 = r39
            r17 = r40
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set, java.lang.String, java.io.File):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38, java.lang.String r39, java.io.File r40, java.util.concurrent.Callable<java.io.InputStream> r41) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            kotlin.collections.EmptyList r20 = kotlin.collections.EmptyList.INSTANCE
            r23 = 0
            r24 = 0
            r19 = 0
            r22 = 0
            r21 = r20
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r16 = r39
            r17 = r40
            r18 = r41
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set, java.lang.String, java.io.File, java.util.concurrent.Callable):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38, java.lang.String r39, java.io.File r40, java.util.concurrent.Callable<java.io.InputStream> r41, androidx.room.RoomDatabase.PrepackagedDatabaseCallback r42) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            kotlin.collections.EmptyList r20 = kotlin.collections.EmptyList.INSTANCE
            r23 = 0
            r24 = 0
            r22 = 0
            r21 = r20
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r16 = r39
            r17 = r40
            r18 = r41
            r19 = r42
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set, java.lang.String, java.io.File, java.util.concurrent.Callable, androidx.room.RoomDatabase$PrepackagedDatabaseCallback):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38, java.lang.String r39, java.io.File r40, java.util.concurrent.Callable<java.io.InputStream> r41, androidx.room.RoomDatabase.PrepackagedDatabaseCallback r42, java.util.List<? extends java.lang.Object> r43) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            kotlin.collections.EmptyList r21 = kotlin.collections.EmptyList.INSTANCE
            r23 = 0
            r24 = 0
            r22 = 0
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r16 = r39
            r17 = r40
            r18 = r41
            r19 = r42
            r20 = r43
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set, java.lang.String, java.io.File, java.util.concurrent.Callable, androidx.room.RoomDatabase$PrepackagedDatabaseCallback, java.util.List):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DatabaseConfiguration(android.content.Context r26, java.lang.String r27, androidx.sqlite.db.SupportSQLiteOpenHelper.Factory r28, androidx.room.RoomDatabase.MigrationContainer r29, java.util.List<? extends androidx.room.RoomDatabase.Callback> r30, boolean r31, androidx.room.RoomDatabase.JournalMode r32, java.util.concurrent.Executor r33, java.util.concurrent.Executor r34, boolean r35, boolean r36, boolean r37, java.util.Set<java.lang.Integer> r38, java.lang.String r39, java.io.File r40, java.util.concurrent.Callable<java.io.InputStream> r41, androidx.room.RoomDatabase.PrepackagedDatabaseCallback r42, java.util.List<? extends java.lang.Object> r43, java.util.List<java.lang.Object> r44) {
        /*
            r25 = this;
            if (r35 == 0) goto Ld
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<androidx.room.MultiInstanceInvalidationService> r1 = androidx.room.MultiInstanceInvalidationService.class
            r3 = r26
            r0.<init>(r3, r1)
        Lb:
            r12 = r0
            goto L11
        Ld:
            r3 = r26
            r0 = 0
            goto Lb
        L11:
            r23 = 0
            r24 = 0
            r19 = 0
            r22 = 0
            r2 = r25
            r4 = r27
            r5 = r28
            r6 = r29
            r7 = r30
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r13 = r36
            r14 = r37
            r15 = r38
            r16 = r39
            r17 = r40
            r18 = r41
            r20 = r43
            r21 = r44
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.DatabaseConfiguration.<init>(android.content.Context, java.lang.String, androidx.sqlite.db.SupportSQLiteOpenHelper$Factory, androidx.room.RoomDatabase$MigrationContainer, java.util.List, boolean, androidx.room.RoomDatabase$JournalMode, java.util.concurrent.Executor, java.util.concurrent.Executor, boolean, boolean, boolean, java.util.Set, java.lang.String, java.io.File, java.util.concurrent.Callable, androidx.room.RoomDatabase$PrepackagedDatabaseCallback, java.util.List, java.util.List):void");
    }

    public DatabaseConfiguration(Context context, String str, SupportSQLiteOpenHelper.Factory factory, RoomDatabase.MigrationContainer migrationContainer, List<? extends RoomDatabase.Callback> list, boolean z, RoomDatabase.JournalMode journalMode, Executor executor, Executor executor2, Intent intent, boolean z2, boolean z3, Set<Integer> set, String str2, File file, Callable<InputStream> callable, RoomDatabase.PrepackagedDatabaseCallback prepackagedDatabaseCallback, List<? extends Object> list2, List<Object> list3) {
        this(context, str, factory, migrationContainer, list, z, journalMode, executor, executor2, intent, z2, z3, set, str2, file, callable, null, list2, list3, false, null, null);
    }

    public DatabaseConfiguration(Context context, String str, SupportSQLiteOpenHelper.Factory factory, RoomDatabase.MigrationContainer migrationContainer, List<? extends RoomDatabase.Callback> list, boolean z, RoomDatabase.JournalMode journalMode, Executor executor, Executor executor2, Intent intent, boolean z2, boolean z3, Set<Integer> set, String str2, File file, Callable<InputStream> callable, RoomDatabase.PrepackagedDatabaseCallback prepackagedDatabaseCallback, List<? extends Object> list2, List<Object> list3, boolean z4) {
        this(context, str, factory, migrationContainer, list, z, journalMode, executor, executor2, intent, z2, z3, set, str2, file, callable, null, list2, list3, z4, null, null);
    }
}
