package androidx.room;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.room.concurrent.CloseBarrier;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$checkInvalidatedTables(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, PooledConnection pooledConnection, ContinuationImpl continuationImpl) {
        TriggerBasedInvalidationTracker$checkInvalidatedTables$1 triggerBasedInvalidationTracker$checkInvalidatedTables$1;
        triggerBasedInvalidationTracker.getClass();
        if (continuationImpl instanceof TriggerBasedInvalidationTracker$checkInvalidatedTables$1) {
            triggerBasedInvalidationTracker$checkInvalidatedTables$1 = (TriggerBasedInvalidationTracker$checkInvalidatedTables$1) continuationImpl;
            int i = triggerBasedInvalidationTracker$checkInvalidatedTables$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                triggerBasedInvalidationTracker$checkInvalidatedTables$1.label = i - Integer.MIN_VALUE;
            } else {
                triggerBasedInvalidationTracker$checkInvalidatedTables$1 = new TriggerBasedInvalidationTracker$checkInvalidatedTables$1(triggerBasedInvalidationTracker, continuationImpl);
            }
        }
        Object objUsePrepared = triggerBasedInvalidationTracker$checkInvalidatedTables$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = triggerBasedInvalidationTracker$checkInvalidatedTables$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objUsePrepared);
            TriggerBasedInvalidationTracker$$ExternalSyntheticLambda1 triggerBasedInvalidationTracker$$ExternalSyntheticLambda1 = new TriggerBasedInvalidationTracker$$ExternalSyntheticLambda1();
            triggerBasedInvalidationTracker$checkInvalidatedTables$1.L$0 = pooledConnection;
            triggerBasedInvalidationTracker$checkInvalidatedTables$1.label = 1;
            objUsePrepared = pooledConnection.usePrepared("SELECT * FROM room_table_modification_log WHERE invalidated = 1", triggerBasedInvalidationTracker$$ExternalSyntheticLambda1, triggerBasedInvalidationTracker$checkInvalidatedTables$1);
            if (objUsePrepared != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Set set = (Set) triggerBasedInvalidationTracker$checkInvalidatedTables$1.L$0;
            ResultKt.throwOnFailure(objUsePrepared);
            return set;
        }
        pooledConnection = (PooledConnection) triggerBasedInvalidationTracker$checkInvalidatedTables$1.L$0;
        ResultKt.throwOnFailure(objUsePrepared);
        Set set2 = (Set) objUsePrepared;
        if (!set2.isEmpty()) {
            triggerBasedInvalidationTracker$checkInvalidatedTables$1.L$0 = set2;
            triggerBasedInvalidationTracker$checkInvalidatedTables$1.label = 2;
            if (TransactorKt.execSQL(pooledConnection, "UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1", triggerBasedInvalidationTracker$checkInvalidatedTables$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return set2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$notifyInvalidation(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, ContinuationImpl continuationImpl) throws Throwable {
        TriggerBasedInvalidationTracker$notifyInvalidation$1 triggerBasedInvalidationTracker$notifyInvalidation$1;
        CloseBarrier closeBarrier;
        CloseBarrier closeBarrier2;
        Object value;
        int[] iArr;
        triggerBasedInvalidationTracker.getClass();
        if (continuationImpl instanceof TriggerBasedInvalidationTracker$notifyInvalidation$1) {
            triggerBasedInvalidationTracker$notifyInvalidation$1 = (TriggerBasedInvalidationTracker$notifyInvalidation$1) continuationImpl;
            int i = triggerBasedInvalidationTracker$notifyInvalidation$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                triggerBasedInvalidationTracker$notifyInvalidation$1.label = i - Integer.MIN_VALUE;
            } else {
                triggerBasedInvalidationTracker$notifyInvalidation$1 = new TriggerBasedInvalidationTracker$notifyInvalidation$1(triggerBasedInvalidationTracker, continuationImpl);
            }
        }
        Object objUseConnection$room_runtime_release = triggerBasedInvalidationTracker$notifyInvalidation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = triggerBasedInvalidationTracker$notifyInvalidation$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objUseConnection$room_runtime_release);
            RoomDatabase roomDatabase = triggerBasedInvalidationTracker.database;
            closeBarrier = roomDatabase.closeBarrier;
            if (!closeBarrier.block$room_runtime_release()) {
                return EmptySet.INSTANCE;
            }
            try {
                if (!triggerBasedInvalidationTracker.pendingRefresh.compareAndSet(true, false)) {
                    EmptySet emptySet = EmptySet.INSTANCE;
                    closeBarrier.unblock$room_runtime_release();
                    return emptySet;
                }
                if (!((Boolean) triggerBasedInvalidationTracker.onAllowRefresh.invoke()).booleanValue()) {
                    EmptySet emptySet2 = EmptySet.INSTANCE;
                    closeBarrier.unblock$room_runtime_release();
                    return emptySet2;
                }
                TriggerBasedInvalidationTracker$notifyInvalidation$2$invalidatedTableIds$1 triggerBasedInvalidationTracker$notifyInvalidation$2$invalidatedTableIds$1 = new TriggerBasedInvalidationTracker$notifyInvalidation$2$invalidatedTableIds$1(triggerBasedInvalidationTracker, null);
                triggerBasedInvalidationTracker$notifyInvalidation$1.L$0 = triggerBasedInvalidationTracker;
                triggerBasedInvalidationTracker$notifyInvalidation$1.L$1 = closeBarrier;
                triggerBasedInvalidationTracker$notifyInvalidation$1.label = 1;
                objUseConnection$room_runtime_release = roomDatabase.useConnection$room_runtime_release(false, triggerBasedInvalidationTracker$notifyInvalidation$2$invalidatedTableIds$1, triggerBasedInvalidationTracker$notifyInvalidation$1);
                if (objUseConnection$room_runtime_release == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (Throwable th) {
                th = th;
                closeBarrier2 = closeBarrier;
                closeBarrier2.unblock$room_runtime_release();
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            closeBarrier2 = (CloseBarrier) triggerBasedInvalidationTracker$notifyInvalidation$1.L$1;
            TriggerBasedInvalidationTracker triggerBasedInvalidationTracker2 = (TriggerBasedInvalidationTracker) triggerBasedInvalidationTracker$notifyInvalidation$1.L$0;
            try {
                ResultKt.throwOnFailure(objUseConnection$room_runtime_release);
                closeBarrier = closeBarrier2;
                triggerBasedInvalidationTracker = triggerBasedInvalidationTracker2;
            } catch (Throwable th2) {
                th = th2;
                closeBarrier2.unblock$room_runtime_release();
                throw th;
            }
        }
        Set set = (Set) objUseConnection$room_runtime_release;
        if (!set.isEmpty()) {
            ObservedTableVersions observedTableVersions = triggerBasedInvalidationTracker.observedTableVersions;
            try {
                observedTableVersions.getClass();
                if (!set.isEmpty()) {
                    StateFlowImpl stateFlowImpl = observedTableVersions.versions;
                    do {
                        value = stateFlowImpl.getValue();
                        int[] iArr2 = (int[]) value;
                        int length = iArr2.length;
                        iArr = new int[length];
                        for (int i3 = 0; i3 < length; i3++) {
                            iArr[i3] = set.contains(Integer.valueOf(i3)) ? iArr2[i3] + 1 : iArr2[i3];
                        }
                    } while (!stateFlowImpl.compareAndSet(value, iArr));
                }
                triggerBasedInvalidationTracker.onInvalidatedTablesIds.mo781invoke(set);
            } catch (Throwable th3) {
                th = th3;
                closeBarrier2 = closeBarrier;
                closeBarrier2.unblock$room_runtime_release();
                throw th;
            }
        }
        closeBarrier.unblock$room_runtime_release();
        return set;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x008b, code lost:
    
        if (androidx.room.TransactorKt.execSQL(r1, r3, r4) == r5) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00f1, code lost:
    
        if (androidx.room.TransactorKt.execSQL(r10, r3, r4) == r5) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00f3, code lost:
    
        return r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00f1 -> B:28:0x00f4). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$startTrackingTable(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Transactor transactor, int i, ContinuationImpl continuationImpl) {
        TriggerBasedInvalidationTracker$startTrackingTable$1 triggerBasedInvalidationTracker$startTrackingTable$1;
        String[] strArr;
        PooledConnection pooledConnection;
        int i2;
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker2;
        int length;
        String str;
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker3 = triggerBasedInvalidationTracker;
        PooledConnection pooledConnection2 = transactor;
        int i3 = i;
        triggerBasedInvalidationTracker3.getClass();
        if (continuationImpl instanceof TriggerBasedInvalidationTracker$startTrackingTable$1) {
            triggerBasedInvalidationTracker$startTrackingTable$1 = (TriggerBasedInvalidationTracker$startTrackingTable$1) continuationImpl;
            int i4 = triggerBasedInvalidationTracker$startTrackingTable$1.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                triggerBasedInvalidationTracker$startTrackingTable$1.label = i4 - Integer.MIN_VALUE;
            } else {
                triggerBasedInvalidationTracker$startTrackingTable$1 = new TriggerBasedInvalidationTracker$startTrackingTable$1(triggerBasedInvalidationTracker3, continuationImpl);
            }
        }
        Object obj = triggerBasedInvalidationTracker$startTrackingTable$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = triggerBasedInvalidationTracker$startTrackingTable$1.label;
        boolean z = true;
        if (i5 == 0) {
            ResultKt.throwOnFailure(obj);
            String str2 = "INSERT OR IGNORE INTO room_table_modification_log VALUES(" + i3 + ", 0)";
            triggerBasedInvalidationTracker$startTrackingTable$1.L$0 = triggerBasedInvalidationTracker3;
            triggerBasedInvalidationTracker$startTrackingTable$1.L$1 = pooledConnection2;
            triggerBasedInvalidationTracker$startTrackingTable$1.I$0 = i3;
            triggerBasedInvalidationTracker$startTrackingTable$1.label = 1;
        } else if (i5 == 1) {
            int i6 = triggerBasedInvalidationTracker$startTrackingTable$1.I$0;
            pooledConnection2 = (PooledConnection) triggerBasedInvalidationTracker$startTrackingTable$1.L$1;
            TriggerBasedInvalidationTracker triggerBasedInvalidationTracker4 = (TriggerBasedInvalidationTracker) triggerBasedInvalidationTracker$startTrackingTable$1.L$0;
            ResultKt.throwOnFailure(obj);
            i3 = i6;
            triggerBasedInvalidationTracker3 = triggerBasedInvalidationTracker4;
        } else {
            if (i5 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = triggerBasedInvalidationTracker$startTrackingTable$1.I$2;
            i2 = triggerBasedInvalidationTracker$startTrackingTable$1.I$1;
            i3 = triggerBasedInvalidationTracker$startTrackingTable$1.I$0;
            strArr = (String[]) triggerBasedInvalidationTracker$startTrackingTable$1.L$3;
            str = (String) triggerBasedInvalidationTracker$startTrackingTable$1.L$2;
            pooledConnection = (PooledConnection) triggerBasedInvalidationTracker$startTrackingTable$1.L$1;
            triggerBasedInvalidationTracker2 = (TriggerBasedInvalidationTracker) triggerBasedInvalidationTracker$startTrackingTable$1.L$0;
            ResultKt.throwOnFailure(obj);
            boolean z2 = true;
            i2++;
            z = z2;
            if (i2 < length) {
                return Unit.INSTANCE;
            }
            String str3 = strArr[i2];
            String str4 = triggerBasedInvalidationTracker2.useTempTable ? "TEMP" : "";
            Companion.getClass();
            z2 = z;
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("CREATE ", str4, " TRIGGER IF NOT EXISTS `", "room_table_modification_trigger_" + str + '_' + str3, "` AFTER ");
            MoveResult$$ExternalSyntheticOutline0.m(sbM, str3, " ON `", str, "` BEGIN UPDATE room_table_modification_log SET invalidated = 1 WHERE table_id = ");
            String strM = ReorderTile$$ExternalSyntheticOutline0.m(i3, " AND invalidated = 0; END", sbM);
            triggerBasedInvalidationTracker$startTrackingTable$1.L$0 = triggerBasedInvalidationTracker2;
            triggerBasedInvalidationTracker$startTrackingTable$1.L$1 = pooledConnection;
            triggerBasedInvalidationTracker$startTrackingTable$1.L$2 = str;
            triggerBasedInvalidationTracker$startTrackingTable$1.L$3 = strArr;
            triggerBasedInvalidationTracker$startTrackingTable$1.I$0 = i3;
            triggerBasedInvalidationTracker$startTrackingTable$1.I$1 = i2;
            triggerBasedInvalidationTracker$startTrackingTable$1.I$2 = length;
            triggerBasedInvalidationTracker$startTrackingTable$1.label = 2;
        }
        String str5 = triggerBasedInvalidationTracker3.tablesNames[i3];
        strArr = TRIGGERS;
        pooledConnection = pooledConnection2;
        i2 = 0;
        triggerBasedInvalidationTracker2 = triggerBasedInvalidationTracker3;
        length = strArr.length;
        str = str5;
        if (i2 < length) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.room.PooledConnection] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0097 -> B:19:0x009a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$stopTrackingTable(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Transactor transactor, int i, ContinuationImpl continuationImpl) {
        TriggerBasedInvalidationTracker$stopTrackingTable$1 triggerBasedInvalidationTracker$stopTrackingTable$1;
        String str;
        int length;
        String[] strArr;
        Transactor transactor2;
        int i2;
        triggerBasedInvalidationTracker.getClass();
        if (continuationImpl instanceof TriggerBasedInvalidationTracker$stopTrackingTable$1) {
            triggerBasedInvalidationTracker$stopTrackingTable$1 = (TriggerBasedInvalidationTracker$stopTrackingTable$1) continuationImpl;
            int i3 = triggerBasedInvalidationTracker$stopTrackingTable$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                triggerBasedInvalidationTracker$stopTrackingTable$1.label = i3 - Integer.MIN_VALUE;
            } else {
                triggerBasedInvalidationTracker$stopTrackingTable$1 = new TriggerBasedInvalidationTracker$stopTrackingTable$1(triggerBasedInvalidationTracker, continuationImpl);
            }
        }
        Object obj = triggerBasedInvalidationTracker$stopTrackingTable$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = triggerBasedInvalidationTracker$stopTrackingTable$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            String str2 = triggerBasedInvalidationTracker.tablesNames[i];
            String[] strArr2 = TRIGGERS;
            str = str2;
            length = strArr2.length;
            strArr = strArr2;
            transactor2 = transactor;
            i2 = 0;
            if (i2 < length) {
            }
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = triggerBasedInvalidationTracker$stopTrackingTable$1.I$1;
            i2 = triggerBasedInvalidationTracker$stopTrackingTable$1.I$0;
            String[] strArr3 = (String[]) triggerBasedInvalidationTracker$stopTrackingTable$1.L$2;
            str = (String) triggerBasedInvalidationTracker$stopTrackingTable$1.L$1;
            ?? r4 = (PooledConnection) triggerBasedInvalidationTracker$stopTrackingTable$1.L$0;
            ResultKt.throwOnFailure(obj);
            strArr = strArr3;
            transactor2 = r4;
            i2++;
            if (i2 < length) {
                String str3 = strArr[i2];
                Companion.getClass();
                String str4 = "DROP TRIGGER IF EXISTS `" + ("room_table_modification_trigger_" + str + '_' + str3) + '`';
                triggerBasedInvalidationTracker$stopTrackingTable$1.L$0 = transactor2;
                triggerBasedInvalidationTracker$stopTrackingTable$1.L$1 = str;
                triggerBasedInvalidationTracker$stopTrackingTable$1.L$2 = strArr;
                triggerBasedInvalidationTracker$stopTrackingTable$1.I$0 = i2;
                triggerBasedInvalidationTracker$stopTrackingTable$1.I$1 = length;
                triggerBasedInvalidationTracker$stopTrackingTable$1.label = 1;
                if (TransactorKt.execSQL(transactor2, str4, triggerBasedInvalidationTracker$stopTrackingTable$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i2++;
                if (i2 < length) {
                    return Unit.INSTANCE;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object syncTriggers$room_runtime_release(ContinuationImpl continuationImpl) throws Throwable {
        TriggerBasedInvalidationTracker$syncTriggers$1 triggerBasedInvalidationTracker$syncTriggers$1;
        CloseBarrier closeBarrier;
        if (continuationImpl instanceof TriggerBasedInvalidationTracker$syncTriggers$1) {
            triggerBasedInvalidationTracker$syncTriggers$1 = (TriggerBasedInvalidationTracker$syncTriggers$1) continuationImpl;
            int i = triggerBasedInvalidationTracker$syncTriggers$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                triggerBasedInvalidationTracker$syncTriggers$1.label = i - Integer.MIN_VALUE;
            } else {
                triggerBasedInvalidationTracker$syncTriggers$1 = new TriggerBasedInvalidationTracker$syncTriggers$1(this, continuationImpl);
            }
        }
        Object obj = triggerBasedInvalidationTracker$syncTriggers$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = triggerBasedInvalidationTracker$syncTriggers$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            RoomDatabase roomDatabase = this.database;
            CloseBarrier closeBarrier2 = roomDatabase.closeBarrier;
            if (closeBarrier2.block$room_runtime_release()) {
                try {
                    TriggerBasedInvalidationTracker$syncTriggers$2$1 triggerBasedInvalidationTracker$syncTriggers$2$1 = new TriggerBasedInvalidationTracker$syncTriggers$2$1(this, null);
                    triggerBasedInvalidationTracker$syncTriggers$1.L$0 = closeBarrier2;
                    triggerBasedInvalidationTracker$syncTriggers$1.label = 1;
                    if (roomDatabase.useConnection$room_runtime_release(false, triggerBasedInvalidationTracker$syncTriggers$2$1, triggerBasedInvalidationTracker$syncTriggers$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    closeBarrier = closeBarrier2;
                    closeBarrier.unblock$room_runtime_release();
                } catch (Throwable th) {
                    th = th;
                    closeBarrier = closeBarrier2;
                    closeBarrier.unblock$room_runtime_release();
                    throw th;
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            closeBarrier = (CloseBarrier) triggerBasedInvalidationTracker$syncTriggers$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                closeBarrier.unblock$room_runtime_release();
            } catch (Throwable th2) {
                th = th2;
                closeBarrier.unblock$room_runtime_release();
                throw th;
            }
        }
        return Unit.INSTANCE;
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
