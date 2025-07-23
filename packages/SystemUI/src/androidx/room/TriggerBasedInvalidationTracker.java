package androidx.room;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TriggerBasedInvalidationTracker {
    public static final Companion Companion = new Companion(null);
    public static final String[] TRIGGERS = {"INSERT", "UPDATE", "DELETE"};
    public final RoomDatabase database;
    public final ObservedTableStates observedTableStates;
    public final ObservedTableVersions observedTableVersions;
    public final Function1 onInvalidatedTablesIds;
    public final Map shadowTablesMap;
    public final String[] tablesNames;
    public final boolean useTempTable;
    public final Map viewTables;
    public final AtomicBoolean pendingRefresh = new AtomicBoolean(false);
    public Function0 onAllowRefresh = new TriggerBasedInvalidationTracker$$ExternalSyntheticLambda0();
    public final Map tableIdLookup = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public TriggerBasedInvalidationTracker(RoomDatabase roomDatabase, Map<String, String> map, Map<String, ? extends Set<String>> map2, String[] strArr, boolean z, Function1 function1) {
        this.database = roomDatabase;
        this.shadowTablesMap = map;
        this.viewTables = map2;
        this.useTempTable = z;
        this.onInvalidatedTablesIds = function1;
        int length = strArr.length;
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            Locale locale = Locale.ROOT;
            String lowerCase = str.toLowerCase(locale);
            this.tableIdLookup.put(lowerCase, Integer.valueOf(i));
            String str2 = (String) this.shadowTablesMap.get(strArr[i]);
            String lowerCase2 = str2 != null ? str2.toLowerCase(locale) : null;
            if (lowerCase2 != null) {
                lowerCase = lowerCase2;
            }
            strArr2[i] = lowerCase;
        }
        this.tablesNames = strArr2;
        for (Map.Entry entry : this.shadowTablesMap.entrySet()) {
            String str3 = (String) entry.getValue();
            Locale locale2 = Locale.ROOT;
            String lowerCase3 = str3.toLowerCase(locale2);
            if (this.tableIdLookup.containsKey(lowerCase3)) {
                String lowerCase4 = ((String) entry.getKey()).toLowerCase(locale2);
                Map map3 = this.tableIdLookup;
                map3.put(lowerCase4, MapsKt__MapsKt.getValue(lowerCase3, map3));
            }
        }
        this.observedTableStates = new ObservedTableStates(this.tablesNames.length);
        this.observedTableVersions = new ObservedTableVersions(this.tablesNames.length);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
    
        if (r4 == r6) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$checkInvalidatedTables(androidx.room.TriggerBasedInvalidationTracker r4, androidx.room.PooledConnection r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4.getClass()
            boolean r0 = r6 instanceof androidx.room.TriggerBasedInvalidationTracker$checkInvalidatedTables$1
            if (r0 == 0) goto L16
            r0 = r6
            androidx.room.TriggerBasedInvalidationTracker$checkInvalidatedTables$1 r0 = (androidx.room.TriggerBasedInvalidationTracker$checkInvalidatedTables$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.room.TriggerBasedInvalidationTracker$checkInvalidatedTables$1 r0 = new androidx.room.TriggerBasedInvalidationTracker$checkInvalidatedTables$1
            r0.<init>(r4, r6)
        L1b:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L41
            if (r1 == r3) goto L39
            if (r1 != r2) goto L31
            java.lang.Object r5 = r0.L$0
            java.util.Set r5 = (java.util.Set) r5
            kotlin.ResultKt.throwOnFailure(r4)
            return r5
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            java.lang.Object r5 = r0.L$0
            androidx.room.PooledConnection r5 = (androidx.room.PooledConnection) r5
            kotlin.ResultKt.throwOnFailure(r4)
            goto L56
        L41:
            kotlin.ResultKt.throwOnFailure(r4)
            androidx.room.TriggerBasedInvalidationTracker$$ExternalSyntheticLambda1 r4 = new androidx.room.TriggerBasedInvalidationTracker$$ExternalSyntheticLambda1
            r4.<init>()
            r0.L$0 = r5
            r0.label = r3
            java.lang.String r1 = "SELECT * FROM room_table_modification_log WHERE invalidated = 1"
            java.lang.Object r4 = r5.usePrepared(r1, r4, r0)
            if (r4 != r6) goto L56
            goto L6d
        L56:
            java.util.Set r4 = (java.util.Set) r4
            r1 = r4
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L6e
            r0.L$0 = r4
            r0.label = r2
            java.lang.String r1 = "UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1"
            java.lang.Object r5 = androidx.room.TransactorKt.execSQL(r5, r1, r0)
            if (r5 != r6) goto L6e
        L6d:
            return r6
        L6e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker.access$checkInvalidatedTables(androidx.room.TriggerBasedInvalidationTracker, androidx.room.PooledConnection, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0090 A[Catch: all -> 0x00cc, TRY_LEAVE, TryCatch #2 {all -> 0x00cc, blocks: (B:13:0x0085, B:15:0x0090, B:20:0x00c6, B:54:0x004d, B:56:0x0055, B:59:0x005e, B:61:0x006c, B:64:0x0072), top: B:53:0x004d }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$notifyInvalidation(androidx.room.TriggerBasedInvalidationTracker r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker.access$notifyInvalidation(androidx.room.TriggerBasedInvalidationTracker, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00f1, code lost:
    
        if (androidx.room.TransactorKt.execSQL(r10, r3, r4) == r5) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00f3, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008b, code lost:
    
        if (androidx.room.TransactorKt.execSQL(r1, r3, r4) == r5) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x00f1 -> B:11:0x00f4). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$startTrackingTable(androidx.room.TriggerBasedInvalidationTracker r17, androidx.room.Transactor r18, int r19, kotlin.coroutines.jvm.internal.ContinuationImpl r20) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker.access$startTrackingTable(androidx.room.TriggerBasedInvalidationTracker, androidx.room.Transactor, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.room.PooledConnection] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0097 -> B:10:0x009a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$stopTrackingTable(androidx.room.TriggerBasedInvalidationTracker r8, androidx.room.Transactor r9, int r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r8.getClass()
            boolean r0 = r11 instanceof androidx.room.TriggerBasedInvalidationTracker$stopTrackingTable$1
            if (r0 == 0) goto L16
            r0 = r11
            androidx.room.TriggerBasedInvalidationTracker$stopTrackingTable$1 r0 = (androidx.room.TriggerBasedInvalidationTracker$stopTrackingTable$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.room.TriggerBasedInvalidationTracker$stopTrackingTable$1 r0 = new androidx.room.TriggerBasedInvalidationTracker$stopTrackingTable$1
            r0.<init>(r8, r11)
        L1b:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L44
            if (r2 != r3) goto L3c
            int r8 = r0.I$1
            int r9 = r0.I$0
            java.lang.Object r10 = r0.L$2
            java.lang.String[] r10 = (java.lang.String[]) r10
            java.lang.Object r2 = r0.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r0.L$0
            androidx.room.PooledConnection r4 = (androidx.room.PooledConnection) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r11 = r10
            r10 = r4
            goto L9a
        L3c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L44:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.String[] r8 = r8.tablesNames
            r8 = r8[r10]
            java.lang.String[] r10 = androidx.room.TriggerBasedInvalidationTracker.TRIGGERS
            int r11 = r10.length
            r2 = 0
            r7 = r2
            r2 = r8
            r8 = r11
            r11 = r10
            r10 = r9
            r9 = r7
        L55:
            if (r9 >= r8) goto L9c
            r4 = r11[r9]
            androidx.room.TriggerBasedInvalidationTracker$Companion r5 = androidx.room.TriggerBasedInvalidationTracker.Companion
            r5.getClass()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "room_table_modification_trigger_"
            r5.<init>(r6)
            r5.append(r2)
            r6 = 95
            r5.append(r6)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "DROP TRIGGER IF EXISTS `"
            r5.<init>(r6)
            r5.append(r4)
            r4 = 96
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            r0.L$0 = r10
            r0.L$1 = r2
            r0.L$2 = r11
            r0.I$0 = r9
            r0.I$1 = r8
            r0.label = r3
            java.lang.Object r4 = androidx.room.TransactorKt.execSQL(r10, r4, r0)
            if (r4 != r1) goto L9a
            return r1
        L9a:
            int r9 = r9 + r3
            goto L55
        L9c:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker.access$stopTrackingTable(androidx.room.TriggerBasedInvalidationTracker, androidx.room.Transactor, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object syncTriggers$room_runtime_release(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof androidx.room.TriggerBasedInvalidationTracker$syncTriggers$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.room.TriggerBasedInvalidationTracker$syncTriggers$1 r0 = (androidx.room.TriggerBasedInvalidationTracker$syncTriggers$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.room.TriggerBasedInvalidationTracker$syncTriggers$1 r0 = new androidx.room.TriggerBasedInvalidationTracker$syncTriggers$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r6 = r0.L$0
            androidx.room.concurrent.CloseBarrier r6 = (androidx.room.concurrent.CloseBarrier) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L2b
            goto L55
        L2b:
            r7 = move-exception
            goto L5d
        L2d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L35:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.room.RoomDatabase r7 = r6.database
            androidx.room.concurrent.CloseBarrier r2 = r7.closeBarrier
            boolean r4 = r2.block$room_runtime_release()
            if (r4 == 0) goto L61
            androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1 r4 = new androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1     // Catch: java.lang.Throwable -> L5b
            r5 = 0
            r4.<init>(r6, r5)     // Catch: java.lang.Throwable -> L5b
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L5b
            r0.label = r3     // Catch: java.lang.Throwable -> L5b
            r6 = 0
            java.lang.Object r6 = r7.useConnection$room_runtime_release(r6, r4, r0)     // Catch: java.lang.Throwable -> L5b
            if (r6 != r1) goto L54
            return r1
        L54:
            r6 = r2
        L55:
            r6.unblock$room_runtime_release()
            goto L61
        L59:
            r6 = r2
            goto L5d
        L5b:
            r7 = move-exception
            goto L59
        L5d:
            r6.unblock$room_runtime_release()
            throw r7
        L61:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker.syncTriggers$room_runtime_release(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Pair validateTableNames$room_runtime_release(String[] strArr) {
        SetBuilder setBuilder = new SetBuilder();
        for (String str : strArr) {
            Set set = (Set) this.viewTables.get(str.toLowerCase(Locale.ROOT));
            if (set != null) {
                setBuilder.addAll(set);
            } else {
                setBuilder.add(str);
            }
        }
        String[] strArr2 = (String[]) setBuilder.build().toArray(new String[0]);
        int length = strArr2.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            String str2 = strArr2[i];
            Integer num = (Integer) ((LinkedHashMap) this.tableIdLookup).get(str2.toLowerCase(Locale.ROOT));
            if (num == null) {
                throw new IllegalArgumentException("There is no table with name ".concat(str2));
            }
            iArr[i] = num.intValue();
        }
        return new Pair(strArr2, iArr);
    }
}
