package androidx.compose.runtime.snapshots;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSet;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DerivedSnapshotState;
import androidx.compose.runtime.DerivedState;
import androidx.compose.runtime.DerivedStateObserver;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.SnapshotMutationPolicy;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.collection.ScopeMap;
import androidx.compose.runtime.internal.Thread_jvmKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
            Collection collectionPlus;
            Collection collection = (Set) obj;
            SnapshotStateObserver snapshotStateObserver = this.this$0;
            do {
                obj3 = snapshotStateObserver.pendingChanges.get();
                if (obj3 == null) {
                    collectionPlus = collection;
                } else if (obj3 instanceof Set) {
                    collectionPlus = Arrays.asList(obj3, collection);
                } else {
                    if (!(obj3 instanceof List)) {
                        ComposerKt.composeRuntimeError("Unexpected notification");
                        throw new KotlinNothingValueException();
                    }
                    collectionPlus = CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(collection), (Collection) obj3);
                }
            } while (!snapshotStateObserver.pendingChanges.compareAndSet(obj3, collectionPlus));
            if (SnapshotStateObserver.access$drainChanges(this.this$0)) {
                final SnapshotStateObserver snapshotStateObserver2 = this.this$0;
                snapshotStateObserver2.getClass();
                snapshotStateObserver2.onChangedExecutor.mo781invoke(new Function0() { // from class: androidx.compose.runtime.snapshots.SnapshotStateObserver$sendNotifications$1
                    {
                        super(0);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
                    @Override // kotlin.jvm.functions.Function0
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke() {
                        boolean z;
                        SnapshotStateObserver snapshotStateObserver3;
                        SnapshotStateObserver snapshotStateObserver4;
                        boolean z2;
                        int i;
                        int i2;
                        boolean z3 = true;
                        while (true) {
                            SnapshotStateObserver snapshotStateObserver5 = snapshotStateObserver2;
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
                                                                            observedScopeMap.onChanged.mo781invoke(objArr2[(i5 << 3) + i8]);
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
                                                                if (i5 == length) {
                                                                    break;
                                                                }
                                                                i5++;
                                                                z3 = z2;
                                                                snapshotStateObserver5 = snapshotStateObserver4;
                                                            }
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
                            if (!SnapshotStateObserver.access$drainChanges(snapshotStateObserver2)) {
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
        public final Object mo781invoke(Object obj) {
            SnapshotStateObserver snapshotStateObserver = this.this$0;
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
                SnapshotStateObserver.ObservedScopeMap observedScopeMap = this.this$0;
                observedScopeMap.deriveStateScopeCount--;
            }

            @Override // androidx.compose.runtime.DerivedStateObserver
            public final void start() {
                this.this$0.deriveStateScopeCount++;
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
            MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
            boolean z2 = true;
            try {
                mutableVectorDerivedStateObservers.add(snapshotStateObserver$ObservedScopeMap$derivedStateObserver$1);
                Snapshot.Companion.getClass();
                Snapshot.Companion.observe(function1, function0);
                mutableVectorDerivedStateObservers.removeAt(mutableVectorDerivedStateObservers.size - 1);
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
                mutableVectorDerivedStateObservers.removeAt(mutableVectorDerivedStateObservers.size - 1);
                throw th;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x022a  */
        /* JADX WARN: Removed duplicated region for block: B:116:0x026e A[PHI: r21
          0x026e: PHI (r21v12 boolean) = (r21v11 boolean), (r21v13 boolean) binds: [B:107:0x0246, B:115:0x026c] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:137:0x02fd  */
        /* JADX WARN: Removed duplicated region for block: B:176:0x03c1 A[PHI: r27
          0x03c1: PHI (r27v10 boolean) = (r27v9 boolean), (r27v11 boolean) binds: [B:165:0x0390, B:175:0x03bf] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:178:0x03ca  */
        /* JADX WARN: Removed duplicated region for block: B:213:0x047e A[PHI: r3
          0x047e: PHI (r3v15 boolean) = (r3v14 boolean), (r3v16 boolean) binds: [B:204:0x0456, B:212:0x047c] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:217:0x048d  */
        /* JADX WARN: Removed duplicated region for block: B:220:0x0497  */
        /* JADX WARN: Removed duplicated region for block: B:235:0x04db A[EDGE_INSN: B:235:0x04db->B:319:0x04e5 BREAK  A[LOOP:18: B:225:0x04a9->B:236:0x04dd], PHI: r3
          0x04db: PHI (r3v6 boolean) = (r3v5 boolean), (r3v7 boolean) binds: [B:226:0x04b3, B:234:0x04d9] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:316:0x04e5 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x013b A[PHI: r21
          0x013b: PHI (r21v28 boolean) = (r21v27 boolean), (r21v29 boolean) binds: [B:47:0x0110, B:56:0x0139] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0209 A[PHI: r21
          0x0209: PHI (r21v18 boolean) = (r21v17 boolean), (r21v19 boolean) binds: [B:84:0x01e1, B:92:0x0207] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean recordInvalidation(Set set) {
            char c;
            long j;
            int i;
            boolean z;
            Iterator it;
            MutableScatterMap mutableScatterMap;
            Object obj;
            int i2;
            Object obj2;
            Iterator it2;
            MutableScatterMap mutableScatterMap2;
            int i3;
            long j2;
            int i4;
            int i5;
            Object[] objArr;
            int i6;
            MutableScatterMap mutableScatterMap3;
            int i7;
            MutableVector mutableVector;
            ObservedScopeMap observedScopeMap;
            int i8;
            Object[] objArr2;
            int i9;
            MutableScatterMap mutableScatterMap4;
            int i10;
            MutableVector mutableVector2;
            MutableObjectIntMap mutableObjectIntMap;
            long[] jArr;
            Object[] objArr3;
            int i11;
            long[] jArr2;
            Object[] objArr4;
            int i12;
            int i13;
            long j3;
            int i14;
            int i15;
            int i16;
            Object obj3;
            Object obj4;
            Object[] objArr5;
            int i17;
            long j4;
            Object[] objArr6;
            int i18;
            int i19;
            long j5;
            HashMap map = this.recordedDerivedStateValues;
            boolean z2 = set instanceof ScatterSetWrapper;
            MutableVector mutableVector3 = this.statesToReread;
            int i20 = 8;
            MutableScatterMap mutableScatterMap5 = this.dependencyToDerivedStates;
            MutableScatterMap mutableScatterMap6 = this.valueToScopes;
            MutableScatterSet mutableScatterSet = this.invalidated;
            if (z2) {
                ScatterSet scatterSet = ((ScatterSetWrapper) set).set;
                Object[] objArr7 = scatterSet.elements;
                long[] jArr3 = scatterSet.metadata;
                int length = jArr3.length - 2;
                if (length >= 0) {
                    int i21 = 0;
                    c = 7;
                    z = false;
                    j = -9187201950435737472L;
                    while (true) {
                        long j6 = jArr3[i21];
                        i = 1;
                        if ((((~j6) << 7) & j6 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i22 = 8 - ((~(i21 - length)) >>> 31);
                            int i23 = 0;
                            while (i23 < i22) {
                                if ((j6 & 255) < 128) {
                                    int i24 = i20;
                                    Object obj5 = objArr7[(i21 << 3) + i23];
                                    if (obj5 instanceof StateObjectImpl) {
                                        int i25 = ReaderKind.$r8$clinit;
                                        jArr2 = jArr3;
                                        if (!((StateObjectImpl) obj5).m351isReadInh_f27i8$runtime_release(2)) {
                                            objArr4 = objArr7;
                                            i12 = length;
                                            i13 = i21;
                                            j3 = j6;
                                            i14 = i22;
                                            i15 = i23;
                                        }
                                        i16 = 8;
                                    } else {
                                        jArr2 = jArr3;
                                    }
                                    if (!mutableScatterMap5.containsKey(obj5) || (obj4 = mutableScatterMap5.get(obj5)) == null) {
                                        objArr4 = objArr7;
                                    } else {
                                        if (obj4 instanceof MutableScatterSet) {
                                            MutableScatterSet mutableScatterSet2 = (MutableScatterSet) obj4;
                                            Object[] objArr8 = mutableScatterSet2.elements;
                                            long[] jArr4 = mutableScatterSet2.metadata;
                                            objArr4 = objArr7;
                                            int length2 = jArr4.length - 2;
                                            if (length2 >= 0) {
                                                j3 = j6;
                                                int i26 = 0;
                                                Object[] objArr9 = objArr8;
                                                while (true) {
                                                    long j7 = jArr4[i26];
                                                    i12 = length;
                                                    i13 = i21;
                                                    if ((((~j7) << 7) & j7 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                        int i27 = 8 - ((~(i26 - length2)) >>> 31);
                                                        int i28 = 0;
                                                        while (i28 < i27) {
                                                            if ((j7 & 255) < 128) {
                                                                i17 = i28;
                                                                DerivedState derivedState = (DerivedState) objArr9[(i26 << 3) + i28];
                                                                j4 = j7;
                                                                Object obj6 = map.get(derivedState);
                                                                SnapshotMutationPolicy policy = derivedState.getPolicy();
                                                                if (policy == null) {
                                                                    policy = SnapshotStateKt.structuralEqualityPolicy();
                                                                }
                                                                objArr6 = objArr9;
                                                                if (policy.equivalent(derivedState.getCurrentRecord().result, obj6)) {
                                                                    i18 = i22;
                                                                    i19 = i23;
                                                                    mutableVector3.add(derivedState);
                                                                } else {
                                                                    Object obj7 = mutableScatterMap6.get(derivedState);
                                                                    if (obj7 != null) {
                                                                        if (obj7 instanceof MutableScatterSet) {
                                                                            MutableScatterSet mutableScatterSet3 = (MutableScatterSet) obj7;
                                                                            Object[] objArr10 = mutableScatterSet3.elements;
                                                                            long[] jArr5 = mutableScatterSet3.metadata;
                                                                            int length3 = jArr5.length - 2;
                                                                            if (length3 >= 0) {
                                                                                int i29 = 0;
                                                                                while (true) {
                                                                                    long j8 = jArr5[i29];
                                                                                    i18 = i22;
                                                                                    i19 = i23;
                                                                                    if ((((~j8) << 7) & j8 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                                        int i30 = 8 - ((~(i29 - length3)) >>> 31);
                                                                                        for (int i31 = 0; i31 < i30; i31++) {
                                                                                            if ((j8 & 255) < 128) {
                                                                                                j5 = j8;
                                                                                                mutableScatterSet.add(objArr10[(i29 << 3) + i31]);
                                                                                                z = true;
                                                                                            } else {
                                                                                                j5 = j8;
                                                                                            }
                                                                                            j8 = j5 >> i24;
                                                                                        }
                                                                                        if (i30 != i24) {
                                                                                            break;
                                                                                        }
                                                                                        if (i29 == length3) {
                                                                                            break;
                                                                                        }
                                                                                        i29++;
                                                                                        i22 = i18;
                                                                                        i23 = i19;
                                                                                        i24 = 8;
                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            i18 = i22;
                                                                            i19 = i23;
                                                                            mutableScatterSet.add(obj7);
                                                                            z = true;
                                                                        }
                                                                    }
                                                                }
                                                                j7 = j4 >> 8;
                                                                i24 = 8;
                                                                i28 = i17 + 1;
                                                                objArr9 = objArr6;
                                                                i22 = i18;
                                                                i23 = i19;
                                                            } else {
                                                                i17 = i28;
                                                                j4 = j7;
                                                                objArr6 = objArr9;
                                                            }
                                                            i18 = i22;
                                                            i19 = i23;
                                                            j7 = j4 >> 8;
                                                            i24 = 8;
                                                            i28 = i17 + 1;
                                                            objArr9 = objArr6;
                                                            i22 = i18;
                                                            i23 = i19;
                                                        }
                                                        objArr5 = objArr9;
                                                        i14 = i22;
                                                        i15 = i23;
                                                        if (i27 != i24) {
                                                            break;
                                                        }
                                                    } else {
                                                        objArr5 = objArr9;
                                                        i14 = i22;
                                                        i15 = i23;
                                                    }
                                                    if (i26 == length2) {
                                                        break;
                                                    }
                                                    i26++;
                                                    length = i12;
                                                    i21 = i13;
                                                    objArr9 = objArr5;
                                                    i22 = i14;
                                                    i23 = i15;
                                                    i24 = 8;
                                                }
                                            }
                                        } else {
                                            objArr4 = objArr7;
                                            i12 = length;
                                            i13 = i21;
                                            j3 = j6;
                                            i14 = i22;
                                            i15 = i23;
                                            DerivedState derivedState2 = (DerivedState) obj4;
                                            Object obj8 = map.get(derivedState2);
                                            SnapshotMutationPolicy policy2 = derivedState2.getPolicy();
                                            if (policy2 == null) {
                                                policy2 = SnapshotStateKt.structuralEqualityPolicy();
                                            }
                                            if (policy2.equivalent(derivedState2.getCurrentRecord().result, obj8)) {
                                                mutableVector3.add(derivedState2);
                                            } else {
                                                Object obj9 = mutableScatterMap6.get(derivedState2);
                                                if (obj9 != null) {
                                                    if (obj9 instanceof MutableScatterSet) {
                                                        MutableScatterSet mutableScatterSet4 = (MutableScatterSet) obj9;
                                                        Object[] objArr11 = mutableScatterSet4.elements;
                                                        long[] jArr6 = mutableScatterSet4.metadata;
                                                        int length4 = jArr6.length - 2;
                                                        if (length4 >= 0) {
                                                            int i32 = 0;
                                                            while (true) {
                                                                long j9 = jArr6[i32];
                                                                if ((((~j9) << 7) & j9 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                    int i33 = 8 - ((~(i32 - length4)) >>> 31);
                                                                    for (int i34 = 0; i34 < i33; i34++) {
                                                                        if ((j9 & 255) < 128) {
                                                                            mutableScatterSet.add(objArr11[(i32 << 3) + i34]);
                                                                            z = true;
                                                                        }
                                                                        j9 >>= 8;
                                                                    }
                                                                    if (i33 != 8) {
                                                                        break;
                                                                    }
                                                                    if (i32 == length4) {
                                                                        break;
                                                                    }
                                                                    i32++;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        mutableScatterSet.add(obj9);
                                                        z = true;
                                                    }
                                                }
                                            }
                                        }
                                        obj3 = mutableScatterMap6.get(obj5);
                                        if (obj3 != null) {
                                            if (obj3 instanceof MutableScatterSet) {
                                                MutableScatterSet mutableScatterSet5 = (MutableScatterSet) obj3;
                                                Object[] objArr12 = mutableScatterSet5.elements;
                                                long[] jArr7 = mutableScatterSet5.metadata;
                                                int length5 = jArr7.length - 2;
                                                if (length5 >= 0) {
                                                    int i35 = 0;
                                                    while (true) {
                                                        long j10 = jArr7[i35];
                                                        if ((((~j10) << 7) & j10 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                            int i36 = 8 - ((~(i35 - length5)) >>> 31);
                                                            for (int i37 = 0; i37 < i36; i37++) {
                                                                if ((j10 & 255) < 128) {
                                                                    mutableScatterSet.add(objArr12[(i35 << 3) + i37]);
                                                                    z = true;
                                                                }
                                                                j10 >>= 8;
                                                            }
                                                            if (i36 != 8) {
                                                                break;
                                                            }
                                                            if (i35 == length5) {
                                                                break;
                                                            }
                                                            i35++;
                                                        }
                                                    }
                                                }
                                            } else {
                                                mutableScatterSet.add(obj3);
                                                z = true;
                                            }
                                        }
                                        i16 = 8;
                                    }
                                    i12 = length;
                                    i13 = i21;
                                    j3 = j6;
                                    i14 = i22;
                                    i15 = i23;
                                    obj3 = mutableScatterMap6.get(obj5);
                                    if (obj3 != null) {
                                    }
                                    i16 = 8;
                                } else {
                                    jArr2 = jArr3;
                                    objArr4 = objArr7;
                                    i12 = length;
                                    i13 = i21;
                                    j3 = j6;
                                    i14 = i22;
                                    i15 = i23;
                                    i16 = i20;
                                }
                                j6 = j3 >> i16;
                                objArr7 = objArr4;
                                i20 = i16;
                                length = i12;
                                i21 = i13;
                                i22 = i14;
                                i23 = i15 + 1;
                                jArr3 = jArr2;
                            }
                            jArr = jArr3;
                            objArr3 = objArr7;
                            int i38 = length;
                            int i39 = i21;
                            if (i22 != i20) {
                                break;
                            }
                            length = i38;
                            i11 = i39;
                        } else {
                            jArr = jArr3;
                            objArr3 = objArr7;
                            i11 = i21;
                        }
                        if (i11 == length) {
                            break;
                        }
                        i21 = i11 + 1;
                        objArr7 = objArr3;
                        jArr3 = jArr;
                        i20 = 8;
                    }
                } else {
                    c = 7;
                    j = -9187201950435737472L;
                    i = 1;
                    z = false;
                }
            } else {
                c = 7;
                j = -9187201950435737472L;
                i = 1;
                Iterator it3 = set.iterator();
                boolean z3 = false;
                while (it3.hasNext()) {
                    Object next = it3.next();
                    if (next instanceof StateObjectImpl) {
                        int i40 = ReaderKind.$r8$clinit;
                        if (!((StateObjectImpl) next).m351isReadInh_f27i8$runtime_release(2)) {
                            it = it3;
                            mutableScatterMap = mutableScatterMap5;
                        } else if (!mutableScatterMap5.containsKey(next) || (obj2 = mutableScatterMap5.get(next)) == null) {
                            it = it3;
                            mutableScatterMap = mutableScatterMap5;
                            obj = mutableScatterMap6.get(next);
                            if (obj != null) {
                                if (obj instanceof MutableScatterSet) {
                                    MutableScatterSet mutableScatterSet6 = (MutableScatterSet) obj;
                                    Object[] objArr13 = mutableScatterSet6.elements;
                                    long[] jArr8 = mutableScatterSet6.metadata;
                                    int length6 = jArr8.length - 2;
                                    if (length6 >= 0) {
                                        while (true) {
                                            long j11 = jArr8[i2];
                                            if ((((~j11) << 7) & j11 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                int i41 = 8 - ((~(i2 - length6)) >>> 31);
                                                for (int i42 = 0; i42 < i41; i42++) {
                                                    if ((j11 & 255) < 128) {
                                                        mutableScatterSet.add(objArr13[(i2 << 3) + i42]);
                                                        z3 = true;
                                                    }
                                                    j11 >>= 8;
                                                }
                                                if (i41 == 8) {
                                                    i2 = i2 != length6 ? i2 + 1 : 0;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    mutableScatterSet.add(obj);
                                    z3 = true;
                                }
                            }
                        } else {
                            if (obj2 instanceof MutableScatterSet) {
                                MutableScatterSet mutableScatterSet7 = (MutableScatterSet) obj2;
                                Object[] objArr14 = mutableScatterSet7.elements;
                                long[] jArr9 = mutableScatterSet7.metadata;
                                int length7 = jArr9.length - 2;
                                if (length7 >= 0) {
                                    int i43 = 0;
                                    while (true) {
                                        long j12 = jArr9[i43];
                                        long[] jArr10 = jArr9;
                                        Object[] objArr15 = objArr14;
                                        if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i44 = 8 - ((~(i43 - length7)) >>> 31);
                                            int i45 = 0;
                                            while (i45 < i44) {
                                                if ((j12 & 255) < 128) {
                                                    it2 = it3;
                                                    DerivedState derivedState3 = (DerivedState) objArr15[(i43 << 3) + i45];
                                                    boolean z4 = z3;
                                                    Object obj10 = map.get(derivedState3);
                                                    SnapshotMutationPolicy policy3 = derivedState3.getPolicy();
                                                    if (policy3 == null) {
                                                        policy3 = SnapshotStateKt.structuralEqualityPolicy();
                                                    }
                                                    mutableScatterMap2 = mutableScatterMap5;
                                                    SnapshotMutationPolicy snapshotMutationPolicy = policy3;
                                                    i3 = i45;
                                                    if (snapshotMutationPolicy.equivalent(derivedState3.getCurrentRecord().result, obj10)) {
                                                        j2 = j12;
                                                        mutableVector3.add(derivedState3);
                                                    } else {
                                                        Object obj11 = mutableScatterMap6.get(derivedState3);
                                                        if (obj11 == null) {
                                                            j2 = j12;
                                                        } else if (obj11 instanceof MutableScatterSet) {
                                                            MutableScatterSet mutableScatterSet8 = (MutableScatterSet) obj11;
                                                            Object[] objArr16 = mutableScatterSet8.elements;
                                                            long[] jArr11 = mutableScatterSet8.metadata;
                                                            int length8 = jArr11.length - 2;
                                                            if (length8 >= 0) {
                                                                j2 = j12;
                                                                int i46 = 0;
                                                                while (true) {
                                                                    long j13 = jArr11[i46];
                                                                    long[] jArr12 = jArr11;
                                                                    if ((((~j13) << 7) & j13 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                        int i47 = 8 - ((~(i46 - length8)) >>> 31);
                                                                        for (int i48 = 0; i48 < i47; i48 = i4 + 1) {
                                                                            if ((j13 & 255) < 128) {
                                                                                i4 = i48;
                                                                                mutableScatterSet.add(objArr16[(i46 << 3) + i48]);
                                                                                z4 = true;
                                                                            } else {
                                                                                i4 = i48;
                                                                            }
                                                                            j13 >>= 8;
                                                                        }
                                                                        if (i47 != 8) {
                                                                            break;
                                                                        }
                                                                        if (i46 == length8) {
                                                                            break;
                                                                        }
                                                                        i46++;
                                                                        jArr11 = jArr12;
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            j2 = j12;
                                                            mutableScatterSet.add(obj11);
                                                            z3 = true;
                                                        }
                                                    }
                                                    z3 = z4;
                                                } else {
                                                    it2 = it3;
                                                    mutableScatterMap2 = mutableScatterMap5;
                                                    i3 = i45;
                                                    j2 = j12;
                                                }
                                                j12 = j2 >> 8;
                                                i45 = i3 + 1;
                                                it3 = it2;
                                                mutableScatterMap5 = mutableScatterMap2;
                                            }
                                            it = it3;
                                            mutableScatterMap = mutableScatterMap5;
                                            if (i44 != 8) {
                                                break;
                                            }
                                        } else {
                                            it = it3;
                                            mutableScatterMap = mutableScatterMap5;
                                        }
                                        if (i43 == length7) {
                                            break;
                                        }
                                        i43++;
                                        it3 = it;
                                        objArr14 = objArr15;
                                        jArr9 = jArr10;
                                        mutableScatterMap5 = mutableScatterMap;
                                    }
                                }
                            } else {
                                it = it3;
                                mutableScatterMap = mutableScatterMap5;
                                DerivedState derivedState4 = (DerivedState) obj2;
                                Object obj12 = map.get(derivedState4);
                                SnapshotMutationPolicy policy4 = derivedState4.getPolicy();
                                if (policy4 == null) {
                                    policy4 = SnapshotStateKt.structuralEqualityPolicy();
                                }
                                if (policy4.equivalent(derivedState4.getCurrentRecord().result, obj12)) {
                                    mutableVector3.add(derivedState4);
                                } else {
                                    Object obj13 = mutableScatterMap6.get(derivedState4);
                                    if (obj13 != null) {
                                        if (obj13 instanceof MutableScatterSet) {
                                            MutableScatterSet mutableScatterSet9 = (MutableScatterSet) obj13;
                                            Object[] objArr17 = mutableScatterSet9.elements;
                                            long[] jArr13 = mutableScatterSet9.metadata;
                                            int length9 = jArr13.length - 2;
                                            if (length9 >= 0) {
                                                int i49 = 0;
                                                while (true) {
                                                    long j14 = jArr13[i49];
                                                    if ((((~j14) << 7) & j14 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                        int i50 = 8 - ((~(i49 - length9)) >>> 31);
                                                        for (int i51 = 0; i51 < i50; i51++) {
                                                            if ((j14 & 255) < 128) {
                                                                mutableScatterSet.add(objArr17[(i49 << 3) + i51]);
                                                                z3 = true;
                                                            }
                                                            j14 >>= 8;
                                                        }
                                                        if (i50 != 8) {
                                                            break;
                                                        }
                                                        if (i49 == length9) {
                                                            break;
                                                        }
                                                        i49++;
                                                    }
                                                }
                                            }
                                        } else {
                                            mutableScatterSet.add(obj13);
                                            z3 = true;
                                        }
                                    }
                                }
                            }
                            obj = mutableScatterMap6.get(next);
                            if (obj != null) {
                            }
                        }
                    }
                    it3 = it;
                    mutableScatterMap5 = mutableScatterMap;
                }
                z = z3;
            }
            int i52 = mutableVector3.size;
            if (i52 != 0) {
                Object[] objArr18 = mutableVector3.content;
                int i53 = 0;
                while (i53 < i52) {
                    DerivedState derivedState5 = (DerivedState) objArr18[i53];
                    int iHashCode = Long.hashCode(SnapshotKt.currentSnapshot().getSnapshotId());
                    Object obj14 = mutableScatterMap6.get(derivedState5);
                    if (obj14 != null) {
                        boolean z5 = obj14 instanceof MutableScatterSet;
                        MutableScatterMap mutableScatterMap7 = this.scopeToValues;
                        if (z5) {
                            MutableScatterSet mutableScatterSet10 = (MutableScatterSet) obj14;
                            Object[] objArr19 = mutableScatterSet10.elements;
                            long[] jArr14 = mutableScatterSet10.metadata;
                            int length10 = jArr14.length - 2;
                            if (length10 >= 0) {
                                int i54 = 0;
                                while (true) {
                                    long j15 = jArr14[i54];
                                    if ((((~j15) << c) & j15 & j) != j) {
                                        int i55 = 8 - ((~(i54 - length10)) >>> 31);
                                        int i56 = 0;
                                        while (i56 < i55) {
                                            if ((j15 & 255) < 128) {
                                                i8 = i52;
                                                Object obj15 = objArr19[(i54 << 3) + i56];
                                                MutableObjectIntMap mutableObjectIntMap2 = (MutableObjectIntMap) mutableScatterMap7.get(obj15);
                                                objArr2 = objArr18;
                                                if (mutableObjectIntMap2 == null) {
                                                    i9 = i53;
                                                    mutableScatterMap4 = mutableScatterMap6;
                                                    i10 = i;
                                                    mutableVector2 = mutableVector3;
                                                    mutableObjectIntMap = new MutableObjectIntMap(0, i10, null);
                                                    mutableScatterMap7.set(obj15, mutableObjectIntMap);
                                                    Unit unit = Unit.INSTANCE;
                                                } else {
                                                    i9 = i53;
                                                    mutableScatterMap4 = mutableScatterMap6;
                                                    i10 = i;
                                                    mutableVector2 = mutableVector3;
                                                    mutableObjectIntMap = mutableObjectIntMap2;
                                                }
                                                recordRead(derivedState5, iHashCode, obj15, mutableObjectIntMap);
                                            } else {
                                                i8 = i52;
                                                objArr2 = objArr18;
                                                i9 = i53;
                                                mutableScatterMap4 = mutableScatterMap6;
                                                i10 = i;
                                                mutableVector2 = mutableVector3;
                                            }
                                            j15 >>= 8;
                                            i56 += i10;
                                            mutableVector3 = mutableVector2;
                                            i52 = i8;
                                            objArr18 = objArr2;
                                            mutableScatterMap6 = mutableScatterMap4;
                                            i = i10;
                                            i53 = i9;
                                        }
                                        i5 = i52;
                                        objArr = objArr18;
                                        i6 = i53;
                                        mutableScatterMap3 = mutableScatterMap6;
                                        i7 = i;
                                        mutableVector = mutableVector3;
                                        observedScopeMap = this;
                                        if (i55 != 8) {
                                            break;
                                        }
                                    } else {
                                        i5 = i52;
                                        objArr = objArr18;
                                        i6 = i53;
                                        mutableScatterMap3 = mutableScatterMap6;
                                        i7 = i;
                                        mutableVector = mutableVector3;
                                        observedScopeMap = this;
                                    }
                                    if (i54 == length10) {
                                        break;
                                    }
                                    i54 += i7;
                                    mutableVector3 = mutableVector;
                                    i52 = i5;
                                    objArr18 = objArr;
                                    mutableScatterMap6 = mutableScatterMap3;
                                    i = i7;
                                    i53 = i6;
                                }
                            } else {
                                i5 = i52;
                                objArr = objArr18;
                                i6 = i53;
                                mutableScatterMap3 = mutableScatterMap6;
                                i7 = i;
                                mutableVector = mutableVector3;
                            }
                        } else {
                            i5 = i52;
                            objArr = objArr18;
                            i6 = i53;
                            mutableScatterMap3 = mutableScatterMap6;
                            i7 = i;
                            mutableVector = mutableVector3;
                            MutableObjectIntMap mutableObjectIntMap3 = (MutableObjectIntMap) mutableScatterMap7.get(obj14);
                            if (mutableObjectIntMap3 == null) {
                                mutableObjectIntMap3 = new MutableObjectIntMap(0, i7, null);
                                mutableScatterMap7.set(obj14, mutableObjectIntMap3);
                                Unit unit2 = Unit.INSTANCE;
                            }
                            recordRead(derivedState5, iHashCode, obj14, mutableObjectIntMap3);
                        }
                    } else {
                        i5 = i52;
                        objArr = objArr18;
                        i6 = i53;
                        mutableScatterMap3 = mutableScatterMap6;
                        i7 = i;
                        mutableVector = mutableVector3;
                    }
                    mutableVector3 = mutableVector;
                    objArr18 = objArr;
                    mutableScatterMap6 = mutableScatterMap3;
                    i = i7;
                    i53 = i6 + 1;
                    i52 = i5;
                }
                mutableVector3.clear();
            }
            return z;
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x008d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void recordRead(Object obj, int i, Object obj2, MutableObjectIntMap mutableObjectIntMap) {
            int i2;
            if (this.deriveStateScopeCount > 0) {
                return;
            }
            int iFindIndex = mutableObjectIntMap.findIndex(obj);
            if (iFindIndex < 0) {
                iFindIndex = ~iFindIndex;
                i2 = -1;
            } else {
                i2 = mutableObjectIntMap.values[iFindIndex];
            }
            mutableObjectIntMap.keys[iFindIndex] = obj;
            mutableObjectIntMap.values[iFindIndex] = i;
            if ((obj instanceof DerivedState) && i2 != i) {
                DerivedSnapshotState.ResultRecord currentRecord = ((DerivedState) obj).getCurrentRecord();
                this.recordedDerivedStateValues.put(obj, currentRecord.result);
                MutableObjectIntMap mutableObjectIntMap2 = currentRecord.dependencies;
                MutableScatterMap mutableScatterMap = this.dependencyToDerivedStates;
                ScopeMap.m350removeScopeimpl(mutableScatterMap, obj);
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
                                        ((StateObjectImpl) stateObject).m352recordReadInh_f27i8$runtime_release(2);
                                    }
                                    ScopeMap.m348addimpl(mutableScatterMap, stateObject, obj);
                                }
                                j >>= 8;
                            }
                            if (i4 != 8) {
                                break;
                            } else if (i3 == length) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                }
            }
            if (i2 == -1) {
                if (obj instanceof StateObjectImpl) {
                    int i7 = ReaderKind.$r8$clinit;
                    ((StateObjectImpl) obj).m352recordReadInh_f27i8$runtime_release(2);
                }
                ScopeMap.m348addimpl(this.valueToScopes, obj, obj2);
            }
        }

        public final void removeObservation(Object obj, Object obj2) {
            MutableScatterMap mutableScatterMap = this.valueToScopes;
            ScopeMap.m349removeimpl(mutableScatterMap, obj2, obj);
            if (!(obj2 instanceof DerivedState) || mutableScatterMap.containsKey(obj2)) {
                return;
            }
            ScopeMap.m350removeScopeimpl(this.dependencyToDerivedStates, obj2);
            this.recordedDerivedStateValues.remove(obj2);
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x009d  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00a8  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void removeScopeIf(Function1 function1) {
            long[] jArr;
            long[] jArr2;
            long j;
            char c;
            long j2;
            int i;
            long j3;
            MutableScatterMap mutableScatterMap = this.scopeToValues;
            long[] jArr3 = mutableScatterMap.metadata;
            int length = jArr3.length - 2;
            if (length < 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                long j4 = jArr3[i2];
                char c2 = 7;
                long j5 = -9187201950435737472L;
                if ((((~j4) << 7) & j4 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8;
                    int i4 = 8 - ((~(i2 - length)) >>> 31);
                    int i5 = 0;
                    while (i5 < i4) {
                        if ((j4 & 255) < 128) {
                            int i6 = (i2 << 3) + i5;
                            c = c2;
                            Object obj = mutableScatterMap.keys[i6];
                            j2 = j5;
                            MutableObjectIntMap mutableObjectIntMap = (MutableObjectIntMap) mutableScatterMap.values[i6];
                            Boolean bool = (Boolean) function1.mo781invoke(obj);
                            if (bool.booleanValue()) {
                                Object[] objArr = mutableObjectIntMap.keys;
                                int[] iArr = mutableObjectIntMap.values;
                                long[] jArr4 = mutableObjectIntMap.metadata;
                                int i7 = i3;
                                int length2 = jArr4.length - 2;
                                if (length2 >= 0) {
                                    jArr2 = jArr3;
                                    j = j4;
                                    int i8 = 0;
                                    while (true) {
                                        long j6 = jArr4[i8];
                                        long[] jArr5 = jArr4;
                                        if ((((~j6) << c) & j6 & j2) != j2) {
                                            int i9 = 8 - ((~(i8 - length2)) >>> 31);
                                            for (int i10 = 0; i10 < i9; i10++) {
                                                if ((j6 & 255) < 128) {
                                                    int i11 = (i8 << 3) + i10;
                                                    j3 = j6;
                                                    Object obj2 = objArr[i11];
                                                    int i12 = iArr[i11];
                                                    removeObservation(obj, obj2);
                                                } else {
                                                    j3 = j6;
                                                }
                                                j6 = j3 >> i7;
                                            }
                                            if (i9 != i7) {
                                                break;
                                            }
                                            if (i8 == length2) {
                                                break;
                                            }
                                            i8++;
                                            jArr4 = jArr5;
                                            i7 = 8;
                                        }
                                    }
                                } else {
                                    jArr2 = jArr3;
                                    j = j4;
                                }
                                if (bool.booleanValue()) {
                                    mutableScatterMap.removeValueAt(i6);
                                }
                                i = 8;
                            }
                        } else {
                            jArr2 = jArr3;
                            j = j4;
                            c = c2;
                            j2 = j5;
                            i = i3;
                        }
                        i5++;
                        i3 = i;
                        j4 = j >> i;
                        c2 = c;
                        j5 = j2;
                        jArr3 = jArr2;
                    }
                    jArr = jArr3;
                    if (i4 != i3) {
                        return;
                    }
                } else {
                    jArr = jArr3;
                }
                if (i2 == length) {
                    return;
                }
                i2++;
                jArr3 = jArr;
            }
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
            List listSubList = null;
            if (obj != null) {
                if (obj instanceof Set) {
                    set = (Set) obj;
                } else {
                    if (!(obj instanceof List)) {
                        ComposerKt.composeRuntimeError("Unexpected notification");
                        throw new KotlinNothingValueException();
                    }
                    List list2 = (List) obj;
                    Set set3 = (Set) list2.get(0);
                    if (list2.size() == 2) {
                        listSubList = list2.get(1);
                    } else if (list2.size() > 2) {
                        listSubList = list2.subList(1, list2.size());
                    }
                    set = set3;
                    list = listSubList;
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
                        z2 = ((ObservedScopeMap) objArr[i2]).recordInvalidation(set2) || z2;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void clear(Object obj) {
        Object obj2;
        boolean z;
        int i;
        Object obj3 = this.observedScopeMapsLock;
        synchronized (obj3) {
            try {
                MutableVector mutableVector = this.observedScopeMaps;
                int i2 = mutableVector.size;
                int i3 = 0;
                int i4 = 0;
                while (i3 < i2) {
                    ObservedScopeMap observedScopeMap = (ObservedScopeMap) mutableVector.content[i3];
                    MutableObjectIntMap mutableObjectIntMap = (MutableObjectIntMap) observedScopeMap.scopeToValues.remove(obj);
                    if (mutableObjectIntMap == null) {
                        obj2 = obj3;
                        z = true;
                    } else {
                        Object[] objArr = mutableObjectIntMap.keys;
                        int[] iArr = mutableObjectIntMap.values;
                        long[] jArr = mutableObjectIntMap.metadata;
                        int length = jArr.length - 2;
                        if (length >= 0) {
                            int i5 = 0;
                            while (true) {
                                long j = jArr[i5];
                                obj2 = obj3;
                                z = true;
                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                    int i6 = 8;
                                    int i7 = 8 - ((~(i5 - length)) >>> 31);
                                    int i8 = 0;
                                    while (i8 < i7) {
                                        if ((j & 255) < 128) {
                                            int i9 = (i5 << 3) + i8;
                                            i = i6;
                                            Object obj4 = objArr[i9];
                                            int i10 = iArr[i9];
                                            observedScopeMap.removeObservation(obj, obj4);
                                        } else {
                                            i = i6;
                                        }
                                        j >>= i;
                                        i8++;
                                        i6 = i;
                                    }
                                    if (i7 == i6) {
                                        if (i5 != length) {
                                            i5++;
                                            obj3 = obj2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    try {
                        if (!(observedScopeMap.scopeToValues._size != 0 ? z : false)) {
                            i4++;
                        } else if (i4 > 0) {
                            Object[] objArr2 = mutableVector.content;
                            objArr2[i3 - i4] = objArr2[i3];
                        }
                        i3++;
                        obj3 = obj2;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
                obj2 = obj3;
                int i11 = i2 - i4;
                Arrays.fill(mutableVector.content, i11, i2, (Object) null);
                mutableVector.size = i11;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                obj2 = obj3;
            }
        }
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
            StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Detected multithreaded access to SnapshotStateObserver: previousThreadId=", j, "), currentThread={id=");
            sbM.append(Thread_jvmKt.currentThreadId());
            sbM.append(", name=");
            sbM.append(Thread.currentThread().getName());
            sbM.append("}. Note that observation on multiple threads in layout/draw is not supported. Make sure your measure/layout/draw for each Owner (AndroidComposeView) is executed on the same thread.");
            PreconditionsKt.throwIllegalArgumentException(sbM.toString());
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
