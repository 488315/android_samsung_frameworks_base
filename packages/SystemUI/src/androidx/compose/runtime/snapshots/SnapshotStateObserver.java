package androidx.compose.runtime.snapshots;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DerivedSnapshotState;
import androidx.compose.runtime.DerivedState;
import androidx.compose.runtime.DerivedStateObserver;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.collection.ScopeMap;
import androidx.compose.runtime.internal.Thread_jvmKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotStateObserver {
    public Snapshot$Companion$$ExternalSyntheticLambda0 applyUnsubscribe;
    public ObservedScopeMap currentMap;
    public final Function1 onChangedExecutor;
    public boolean sendingNotifications;
    public final AtomicReference pendingChanges = new AtomicReference(null);
    public final Function2 applyObserver = new Function2() { // from class: androidx.compose.runtime.snapshots.SnapshotStateObserver$applyObserver$1
        {
            super(2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Object obj3;
            Collection plus;
            Collection collection = (Set) obj;
            SnapshotStateObserver snapshotStateObserver = SnapshotStateObserver.this;
            do {
                obj3 = snapshotStateObserver.pendingChanges.get();
                if (obj3 == null) {
                    plus = collection;
                } else if (obj3 instanceof Set) {
                    plus = Arrays.asList(obj3, collection);
                } else {
                    if (!(obj3 instanceof List)) {
                        ComposerKt.composeRuntimeError("Unexpected notification");
                        throw new KotlinNothingValueException();
                    }
                    plus = CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(collection), (Collection) obj3);
                }
            } while (!snapshotStateObserver.pendingChanges.compareAndSet(obj3, plus));
            if (SnapshotStateObserver.access$drainChanges(SnapshotStateObserver.this)) {
                final SnapshotStateObserver snapshotStateObserver2 = SnapshotStateObserver.this;
                snapshotStateObserver2.getClass();
                snapshotStateObserver2.onChangedExecutor.mo779invoke(new Function0() { // from class: androidx.compose.runtime.snapshots.SnapshotStateObserver$sendNotifications$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        boolean z;
                        SnapshotStateObserver snapshotStateObserver3;
                        SnapshotStateObserver snapshotStateObserver4;
                        boolean z2;
                        int i;
                        int i2;
                        boolean z3 = true;
                        while (true) {
                            SnapshotStateObserver snapshotStateObserver5 = SnapshotStateObserver.this;
                            synchronized (snapshotStateObserver5.observedScopeMapsLock) {
                                try {
                                    if (snapshotStateObserver5.sendingNotifications) {
                                        z = z3;
                                    } else {
                                        snapshotStateObserver5.sendingNotifications = z3;
                                        try {
                                            MutableVector mutableVector = snapshotStateObserver5.observedScopeMaps;
                                            Object[] objArr = mutableVector.content;
                                            int i3 = mutableVector.size;
                                            int i4 = 0;
                                            while (i4 < i3) {
                                                try {
                                                    SnapshotStateObserver.ObservedScopeMap observedScopeMap = (SnapshotStateObserver.ObservedScopeMap) objArr[i4];
                                                    MutableScatterSet mutableScatterSet = observedScopeMap.invalidated;
                                                    Object[] objArr2 = mutableScatterSet.elements;
                                                    long[] jArr = mutableScatterSet.metadata;
                                                    int length = jArr.length - 2;
                                                    if (length >= 0) {
                                                        int i5 = 0;
                                                        while (true) {
                                                            long j = jArr[i5];
                                                            z2 = z3;
                                                            snapshotStateObserver4 = snapshotStateObserver5;
                                                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                int i6 = 8;
                                                                int i7 = 8 - ((~(i5 - length)) >>> 31);
                                                                int i8 = 0;
                                                                while (i8 < i7) {
                                                                    if ((j & 255) < 128) {
                                                                        i = i6;
                                                                        try {
                                                                            i2 = i8;
                                                                            observedScopeMap.onChanged.mo779invoke(objArr2[(i5 << 3) + i8]);
                                                                        } catch (Throwable th) {
                                                                            th = th;
                                                                            snapshotStateObserver3 = snapshotStateObserver4;
                                                                            snapshotStateObserver3.sendingNotifications = false;
                                                                            throw th;
                                                                        }
                                                                    } else {
                                                                        i = i6;
                                                                        i2 = i8;
                                                                    }
                                                                    j >>= i;
                                                                    i8 = i2 + 1;
                                                                    i6 = i;
                                                                }
                                                                if (i7 != i6) {
                                                                    break;
                                                                }
                                                            }
                                                            if (i5 == length) {
                                                                break;
                                                            }
                                                            i5++;
                                                            z3 = z2;
                                                            snapshotStateObserver5 = snapshotStateObserver4;
                                                        }
                                                    } else {
                                                        z2 = z3;
                                                        snapshotStateObserver4 = snapshotStateObserver5;
                                                    }
                                                    mutableScatterSet.clear();
                                                    i4++;
                                                    z3 = z2;
                                                    snapshotStateObserver5 = snapshotStateObserver4;
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    snapshotStateObserver4 = snapshotStateObserver5;
                                                }
                                            }
                                            z = z3;
                                            snapshotStateObserver5.sendingNotifications = false;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            snapshotStateObserver3 = snapshotStateObserver5;
                                        }
                                    }
                                    Unit unit = Unit.INSTANCE;
                                } catch (Throwable th4) {
                                    throw th4;
                                }
                            }
                            if (!SnapshotStateObserver.access$drainChanges(SnapshotStateObserver.this)) {
                                return Unit.INSTANCE;
                            }
                            z3 = z;
                        }
                    }
                });
            }
            return Unit.INSTANCE;
        }
    };
    public final Function1 readObserver = new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotStateObserver$readObserver$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            SnapshotStateObserver snapshotStateObserver = SnapshotStateObserver.this;
            snapshotStateObserver.getClass();
            synchronized (snapshotStateObserver.observedScopeMapsLock) {
                SnapshotStateObserver.ObservedScopeMap observedScopeMap = snapshotStateObserver.currentMap;
                observedScopeMap.getClass();
                Object obj2 = observedScopeMap.currentScope;
                obj2.getClass();
                int i = observedScopeMap.currentToken;
                MutableObjectIntMap mutableObjectIntMap = observedScopeMap.currentScopeReads;
                if (mutableObjectIntMap == null) {
                    mutableObjectIntMap = new MutableObjectIntMap(0, 1, null);
                    observedScopeMap.currentScopeReads = mutableObjectIntMap;
                    observedScopeMap.scopeToValues.set(obj2, mutableObjectIntMap);
                    Unit unit = Unit.INSTANCE;
                }
                observedScopeMap.recordRead(obj, i, obj2, mutableObjectIntMap);
            }
            return Unit.INSTANCE;
        }
    };
    public final MutableVector observedScopeMaps = new MutableVector(new ObservedScopeMap[16], 0);
    public final Object observedScopeMapsLock = new Object();
    public long currentMapThreadId = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ObservedScopeMap {
        public Object currentScope;
        public MutableObjectIntMap currentScopeReads;
        public int deriveStateScopeCount;
        public final Function1 onChanged;
        public int currentToken = -1;
        public final MutableScatterMap valueToScopes = ScatterMapKt.mutableScatterMapOf();
        public final MutableScatterMap scopeToValues = new MutableScatterMap(0, 1, null);
        public final MutableScatterSet invalidated = new MutableScatterSet(0, 1, null);
        public final MutableVector statesToReread = new MutableVector(new DerivedState[16], 0);
        public final SnapshotStateObserver$ObservedScopeMap$derivedStateObserver$1 derivedStateObserver = new DerivedStateObserver() { // from class: androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap$derivedStateObserver$1
            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void done() {
                SnapshotStateObserver.ObservedScopeMap observedScopeMap = SnapshotStateObserver.ObservedScopeMap.this;
                observedScopeMap.deriveStateScopeCount--;
            }

            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void start() {
                SnapshotStateObserver.ObservedScopeMap.this.deriveStateScopeCount++;
            }
        };
        public final MutableScatterMap dependencyToDerivedStates = ScatterMapKt.mutableScatterMapOf();
        public final HashMap recordedDerivedStateValues = new HashMap();

        /* JADX WARN: Type inference failed for: r4v6, types: [androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap$derivedStateObserver$1] */
        public ObservedScopeMap(Function1 function1) {
            this.onChanged = function1;
        }

        public final void observe(Object obj, Function1 function1, Function0 function0) {
            boolean z;
            int i;
            int i2;
            Object obj2 = this.currentScope;
            MutableObjectIntMap mutableObjectIntMap = this.currentScopeReads;
            int i3 = this.currentToken;
            this.currentScope = obj;
            this.currentScopeReads = (MutableObjectIntMap) this.scopeToValues.get(obj);
            if (this.currentToken == -1) {
                this.currentToken = Long.hashCode(SnapshotKt.currentSnapshot().getSnapshotId());
            }
            SnapshotStateObserver$ObservedScopeMap$derivedStateObserver$1 snapshotStateObserver$ObservedScopeMap$derivedStateObserver$1 = this.derivedStateObserver;
            MutableVector derivedStateObservers = SnapshotStateKt.derivedStateObservers();
            boolean z2 = true;
            try {
                derivedStateObservers.add(snapshotStateObserver$ObservedScopeMap$derivedStateObserver$1);
                Snapshot.Companion.getClass();
                Snapshot.Companion.observe(function1, function0);
                derivedStateObservers.removeAt(derivedStateObservers.size - 1);
                Object obj3 = this.currentScope;
                obj3.getClass();
                int i4 = this.currentToken;
                MutableObjectIntMap mutableObjectIntMap2 = this.currentScopeReads;
                if (mutableObjectIntMap2 != null) {
                    long[] jArr = mutableObjectIntMap2.metadata;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i5 = 0;
                        while (true) {
                            long j = jArr[i5];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i6 = 8;
                                int i7 = 8 - ((~(i5 - length)) >>> 31);
                                z = z2;
                                int i8 = 0;
                                while (i8 < i7) {
                                    if ((j & 255) < 128) {
                                        int i9 = (i5 << 3) + i8;
                                        i2 = i6;
                                        Object obj4 = mutableObjectIntMap2.keys[i9];
                                        i = i8;
                                        boolean z3 = mutableObjectIntMap2.values[i9] != i4 ? z : false;
                                        if (z3) {
                                            removeObservation(obj3, obj4);
                                        }
                                        if (z3) {
                                            mutableObjectIntMap2.removeValueAt(i9);
                                        }
                                    } else {
                                        i = i8;
                                        i2 = i6;
                                    }
                                    j >>= i2;
                                    i8 = i + 1;
                                    i6 = i2;
                                }
                                if (i7 != i6) {
                                    break;
                                }
                            } else {
                                z = z2;
                            }
                            if (i5 == length) {
                                break;
                            }
                            i5++;
                            z2 = z;
                        }
                    }
                }
                this.currentScope = obj2;
                this.currentScopeReads = mutableObjectIntMap;
                this.currentToken = i3;
            } catch (Throwable th) {
                derivedStateObservers.removeAt(derivedStateObservers.size - 1);
                throw th;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:269:0x0497  */
        /* JADX WARN: Removed duplicated region for block: B:294:0x04e5 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x022a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean recordInvalidation(java.util.Set r44) {
            /*
                Method dump skipped, instructions count: 1589
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.SnapshotStateObserver.ObservedScopeMap.recordInvalidation(java.util.Set):boolean");
        }

        public final void recordRead(Object obj, int i, Object obj2, MutableObjectIntMap mutableObjectIntMap) {
            int i2;
            if (this.deriveStateScopeCount > 0) {
                return;
            }
            int findIndex = mutableObjectIntMap.findIndex(obj);
            if (findIndex < 0) {
                findIndex = ~findIndex;
                i2 = -1;
            } else {
                i2 = mutableObjectIntMap.values[findIndex];
            }
            mutableObjectIntMap.keys[findIndex] = obj;
            mutableObjectIntMap.values[findIndex] = i;
            if ((obj instanceof DerivedState) && i2 != i) {
                DerivedSnapshotState.ResultRecord currentRecord = ((DerivedState) obj).getCurrentRecord();
                this.recordedDerivedStateValues.put(obj, currentRecord.result);
                MutableObjectIntMap mutableObjectIntMap2 = currentRecord.dependencies;
                MutableScatterMap mutableScatterMap = this.dependencyToDerivedStates;
                ScopeMap.m349removeScopeimpl(mutableScatterMap, obj);
                Object[] objArr = mutableObjectIntMap2.keys;
                long[] jArr = mutableObjectIntMap2.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    while (true) {
                        long j = jArr[i3];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8 - ((~(i3 - length)) >>> 31);
                            for (int i5 = 0; i5 < i4; i5++) {
                                if ((j & 255) < 128) {
                                    StateObject stateObject = (StateObject) objArr[(i3 << 3) + i5];
                                    if (stateObject instanceof StateObjectImpl) {
                                        int i6 = ReaderKind.$r8$clinit;
                                        ((StateObjectImpl) stateObject).m351recordReadInh_f27i8$runtime_release(2);
                                    }
                                    ScopeMap.m347addimpl(mutableScatterMap, stateObject, obj);
                                }
                                j >>= 8;
                            }
                            if (i4 != 8) {
                                break;
                            }
                        }
                        if (i3 == length) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
            }
            if (i2 == -1) {
                if (obj instanceof StateObjectImpl) {
                    int i7 = ReaderKind.$r8$clinit;
                    ((StateObjectImpl) obj).m351recordReadInh_f27i8$runtime_release(2);
                }
                ScopeMap.m347addimpl(this.valueToScopes, obj, obj2);
            }
        }

        public final void removeObservation(Object obj, Object obj2) {
            MutableScatterMap mutableScatterMap = this.valueToScopes;
            ScopeMap.m348removeimpl(mutableScatterMap, obj2, obj);
            if (!(obj2 instanceof DerivedState) || mutableScatterMap.containsKey(obj2)) {
                return;
            }
            ScopeMap.m349removeScopeimpl(this.dependencyToDerivedStates, obj2);
            this.recordedDerivedStateValues.remove(obj2);
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x00b2  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void removeScopeIf(kotlin.jvm.functions.Function1 r34) {
            /*
                Method dump skipped, instructions count: 225
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.SnapshotStateObserver.ObservedScopeMap.removeScopeIf(kotlin.jvm.functions.Function1):void");
        }
    }

    public SnapshotStateObserver(Function1 function1) {
        this.onChangedExecutor = function1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean access$drainChanges(SnapshotStateObserver snapshotStateObserver) {
        boolean z;
        Set set;
        synchronized (snapshotStateObserver.observedScopeMapsLock) {
            z = snapshotStateObserver.sendingNotifications;
        }
        if (z) {
            return false;
        }
        boolean z2 = false;
        while (true) {
            Object obj = snapshotStateObserver.pendingChanges.get();
            Set set2 = null;
            List list = null;
            List list2 = null;
            if (obj != null) {
                if (obj instanceof Set) {
                    set = (Set) obj;
                } else {
                    if (!(obj instanceof List)) {
                        ComposerKt.composeRuntimeError("Unexpected notification");
                        throw new KotlinNothingValueException();
                    }
                    List list3 = (List) obj;
                    Set set3 = (Set) list3.get(0);
                    if (list3.size() == 2) {
                        list2 = list3.get(1);
                    } else if (list3.size() > 2) {
                        list2 = list3.subList(1, list3.size());
                    }
                    set = set3;
                    list = list2;
                }
                if (snapshotStateObserver.pendingChanges.compareAndSet(obj, list)) {
                    set2 = set;
                } else {
                    continue;
                }
            }
            if (set2 == null) {
                return z2;
            }
            synchronized (snapshotStateObserver.observedScopeMapsLock) {
                try {
                    MutableVector mutableVector = snapshotStateObserver.observedScopeMaps;
                    Object[] objArr = mutableVector.content;
                    int i = mutableVector.size;
                    for (int i2 = 0; i2 < i; i2++) {
                        if (!((ObservedScopeMap) objArr[i2]).recordInvalidation(set2) && !z2) {
                            z2 = false;
                        }
                        z2 = true;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0074, code lost:
    
        if (r2 == r3) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void clear(java.lang.Object r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            java.lang.Object r3 = r0.observedScopeMapsLock
            monitor-enter(r3)
            androidx.compose.runtime.collection.MutableVector r0 = r0.observedScopeMaps     // Catch: java.lang.Throwable -> L9f
            int r4 = r0.size     // Catch: java.lang.Throwable -> L9f
            r6 = 0
            r7 = 0
        Ld:
            if (r6 >= r4) goto La3
            java.lang.Object[] r8 = r0.content     // Catch: java.lang.Throwable -> L9f
            r8 = r8[r6]     // Catch: java.lang.Throwable -> L9f
            androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap r8 = (androidx.compose.runtime.snapshots.SnapshotStateObserver.ObservedScopeMap) r8     // Catch: java.lang.Throwable -> L9f
            androidx.collection.MutableScatterMap r9 = r8.scopeToValues     // Catch: java.lang.Throwable -> L9f
            java.lang.Object r9 = r9.remove(r1)     // Catch: java.lang.Throwable -> L9f
            androidx.collection.MutableObjectIntMap r9 = (androidx.collection.MutableObjectIntMap) r9     // Catch: java.lang.Throwable -> L9f
            if (r9 != 0) goto L24
        L1f:
            r17 = r3
            r16 = 1
            goto L7d
        L24:
            java.lang.Object[] r10 = r9.keys     // Catch: java.lang.Throwable -> L9f
            int[] r11 = r9.values     // Catch: java.lang.Throwable -> L9f
            long[] r9 = r9.metadata     // Catch: java.lang.Throwable -> L9f
            int r12 = r9.length     // Catch: java.lang.Throwable -> L9f
            int r12 = r12 + (-2)
            if (r12 < 0) goto L1f
            r13 = 0
        L30:
            r14 = r9[r13]     // Catch: java.lang.Throwable -> L9f
            r17 = r3
            r16 = 1
            long r2 = ~r14
            r18 = 7
            long r2 = r2 << r18
            long r2 = r2 & r14
            r18 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r2 = r2 & r18
            int r2 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r2 == 0) goto L76
            int r2 = r13 - r12
            int r2 = ~r2
            int r2 = r2 >>> 31
            r3 = 8
            int r2 = 8 - r2
            r5 = 0
        L51:
            if (r5 >= r2) goto L74
            r18 = 255(0xff, double:1.26E-321)
            long r18 = r14 & r18
            r20 = 128(0x80, double:6.3E-322)
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 >= 0) goto L6b
            int r18 = r13 << 3
            int r18 = r18 + r5
            r19 = r3
            r3 = r10[r18]     // Catch: java.lang.Throwable -> L97
            r18 = r11[r18]     // Catch: java.lang.Throwable -> L97
            r8.removeObservation(r1, r3)     // Catch: java.lang.Throwable -> L97
            goto L6d
        L6b:
            r19 = r3
        L6d:
            long r14 = r14 >> r19
            int r5 = r5 + 1
            r3 = r19
            goto L51
        L74:
            if (r2 != r3) goto L7d
        L76:
            if (r13 == r12) goto L7d
            int r13 = r13 + 1
            r3 = r17
            goto L30
        L7d:
            androidx.collection.MutableScatterMap r2 = r8.scopeToValues     // Catch: java.lang.Throwable -> L97
            int r2 = r2._size     // Catch: java.lang.Throwable -> L97
            if (r2 == 0) goto L86
            r2 = r16
            goto L87
        L86:
            r2 = 0
        L87:
            if (r2 != 0) goto L8c
            int r7 = r7 + 1
            goto L99
        L8c:
            if (r7 <= 0) goto L99
            java.lang.Object[] r2 = r0.content     // Catch: java.lang.Throwable -> L97
            int r3 = r6 - r7
            r5 = r2[r6]     // Catch: java.lang.Throwable -> L97
            r2[r3] = r5     // Catch: java.lang.Throwable -> L97
            goto L99
        L97:
            r0 = move-exception
            goto Lb3
        L99:
            int r6 = r6 + 1
            r3 = r17
            goto Ld
        L9f:
            r0 = move-exception
            r17 = r3
            goto Lb3
        La3:
            r17 = r3
            java.lang.Object[] r1 = r0.content     // Catch: java.lang.Throwable -> L97
            int r2 = r4 - r7
            r3 = 0
            java.util.Arrays.fill(r1, r2, r4, r3)     // Catch: java.lang.Throwable -> L97
            r0.size = r2     // Catch: java.lang.Throwable -> L97
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L97
            monitor-exit(r17)
            return
        Lb3:
            monitor-exit(r17)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.SnapshotStateObserver.clear(java.lang.Object):void");
    }

    public final void clearIf(Function1 function1) {
        synchronized (this.observedScopeMapsLock) {
            try {
                MutableVector mutableVector = this.observedScopeMaps;
                int i = mutableVector.size;
                int i2 = 0;
                for (int i3 = 0; i3 < i; i3++) {
                    ObservedScopeMap observedScopeMap = (ObservedScopeMap) mutableVector.content[i3];
                    observedScopeMap.removeScopeIf(function1);
                    if (!(observedScopeMap.scopeToValues._size != 0)) {
                        i2++;
                    } else if (i2 > 0) {
                        Object[] objArr = mutableVector.content;
                        objArr[i3 - i2] = objArr[i3];
                    }
                }
                int i4 = i - i2;
                Arrays.fill(mutableVector.content, i4, i, (Object) null);
                mutableVector.size = i4;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void observeReads(Object obj, Function1 function1, Function0 function0) {
        Object obj2;
        ObservedScopeMap observedScopeMap;
        synchronized (this.observedScopeMapsLock) {
            MutableVector mutableVector = this.observedScopeMaps;
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    obj2 = null;
                    break;
                }
                obj2 = objArr[i2];
                if (((ObservedScopeMap) obj2).onChanged == function1) {
                    break;
                } else {
                    i2++;
                }
            }
            observedScopeMap = (ObservedScopeMap) obj2;
            if (observedScopeMap == null) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, function1);
                observedScopeMap = new ObservedScopeMap(function1);
                mutableVector.add(observedScopeMap);
            }
        }
        ObservedScopeMap observedScopeMap2 = this.currentMap;
        long j = this.currentMapThreadId;
        if (j != -1 && j != Thread_jvmKt.currentThreadId()) {
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Detected multithreaded access to SnapshotStateObserver: previousThreadId=", j, "), currentThread={id=");
            m.append(Thread_jvmKt.currentThreadId());
            m.append(", name=");
            m.append(Thread.currentThread().getName());
            m.append("}. Note that observation on multiple threads in layout/draw is not supported. Make sure your measure/layout/draw for each Owner (AndroidComposeView) is executed on the same thread.");
            PreconditionsKt.throwIllegalArgumentException(m.toString());
        }
        try {
            this.currentMap = observedScopeMap;
            this.currentMapThreadId = Thread_jvmKt.currentThreadId();
            observedScopeMap.observe(obj, this.readObserver, function0);
        } finally {
            this.currentMap = observedScopeMap2;
            this.currentMapThreadId = j;
        }
    }

    public final void clear() {
        synchronized (this.observedScopeMapsLock) {
            try {
                MutableVector mutableVector = this.observedScopeMaps;
                Object[] objArr = mutableVector.content;
                int i = mutableVector.size;
                for (int i2 = 0; i2 < i; i2++) {
                    ObservedScopeMap observedScopeMap = (ObservedScopeMap) objArr[i2];
                    observedScopeMap.valueToScopes.clear();
                    observedScopeMap.scopeToValues.clear();
                    observedScopeMap.dependencyToDerivedStates.clear();
                    observedScopeMap.recordedDerivedStateValues.clear();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
