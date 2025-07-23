package androidx.room;

import android.content.Intent;
import androidx.room.support.AutoCloser;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class InvalidationTracker {
    public AutoCloser autoCloser;
    public final RoomDatabase database;
    public final TriggerBasedInvalidationTracker implementation;
    public MultiInstanceInvalidationClient multiInstanceInvalidationClient;
    public Intent multiInstanceInvalidationIntent;
    public final Map observerMap;
    public final ReentrantLock observerMapLock;
    public final InvalidationTracker$$ExternalSyntheticLambda0 onRefreshCompleted;
    public final InvalidationTracker$$ExternalSyntheticLambda0 onRefreshScheduled;
    public final String[] tableNames;
    public final Object trackerLock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Observer {
        public final String[] tables;

        public Observer(String[] strArr) {
            this.tables = strArr;
        }

        public abstract void onInvalidated(Set set);

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Observer(java.lang.String r3, java.lang.String... r4) {
            /*
                r2 = this;
                kotlin.jvm.internal.SpreadBuilder r0 = new kotlin.jvm.internal.SpreadBuilder
                r1 = 2
                r0.<init>(r1)
                r0.add(r3)
                r0.addSpread(r4)
                java.util.ArrayList r3 = r0.list
                int r3 = r3.size()
                java.lang.String[] r3 = new java.lang.String[r3]
                java.util.ArrayList r4 = r0.list
                java.lang.Object[] r3 = r4.toArray(r3)
                java.lang.String[] r3 = (java.lang.String[]) r3
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.InvalidationTracker.Observer.<init>(java.lang.String, java.lang.String[]):void");
        }
    }

    static {
        new Companion(null);
    }

    public InvalidationTracker(RoomDatabase roomDatabase, Map<String, String> map, Map<String, Set<String>> map2, String... strArr) {
        this.database = roomDatabase;
        this.tableNames = strArr;
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = new TriggerBasedInvalidationTracker(roomDatabase, map, map2, strArr, roomDatabase.useTempTrackingTable, new InvalidationTracker$implementation$1(this));
        this.implementation = triggerBasedInvalidationTracker;
        this.observerMap = new LinkedHashMap();
        this.observerMapLock = new ReentrantLock();
        this.onRefreshScheduled = new InvalidationTracker$$ExternalSyntheticLambda0(this, 0);
        this.onRefreshCompleted = new InvalidationTracker$$ExternalSyntheticLambda0(this, 1);
        new InvalidationLiveDataContainer(roomDatabase);
        this.trackerLock = new Object();
        triggerBasedInvalidationTracker.onAllowRefresh = new InvalidationTracker$$ExternalSyntheticLambda0(this, 2);
    }

    public final Flow createFlow(String[] strArr) {
        Flow flow;
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = this.implementation;
        Pair validateTableNames$room_runtime_release = triggerBasedInvalidationTracker.validateTableNames$room_runtime_release(strArr);
        final String[] strArr2 = (String[]) validateTableNames$room_runtime_release.component1();
        SafeFlow safeFlow = new SafeFlow(new TriggerBasedInvalidationTracker$createFlow$1(triggerBasedInvalidationTracker, (int[]) validateTableNames$room_runtime_release.component2(), true, strArr2, null));
        MultiInstanceInvalidationClient multiInstanceInvalidationClient = this.multiInstanceInvalidationClient;
        if (multiInstanceInvalidationClient != null) {
            final SharedFlowImpl sharedFlowImpl = multiInstanceInvalidationClient.invalidatedTables;
            flow = new Flow() { // from class: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ String[] $resolvedTableNames$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, String[] strArr) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$resolvedTableNames$inlined = strArr;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                        /*
                            r9 = this;
                            boolean r0 = r11 instanceof androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r11
                            androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2$1 r0 = (androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2$1 r0 = new androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2$1
                            r0.<init>(r11)
                        L18:
                            java.lang.Object r11 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r11)
                            goto L79
                        L27:
                            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                            r9.<init>(r10)
                            throw r9
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r11)
                            java.util.Set r10 = (java.util.Set) r10
                            kotlin.collections.builders.SetBuilder r11 = new kotlin.collections.builders.SetBuilder
                            r11.<init>()
                            java.lang.String[] r2 = r9.$resolvedTableNames$inlined
                            int r4 = r2.length
                            r5 = 0
                        L3d:
                            if (r5 >= r4) goto L61
                            r6 = r2[r5]
                            r7 = r10
                            java.lang.Iterable r7 = (java.lang.Iterable) r7
                            java.util.Iterator r7 = r7.iterator()
                        L48:
                            boolean r8 = r7.hasNext()
                            if (r8 == 0) goto L5e
                            java.lang.Object r8 = r7.next()
                            java.lang.String r8 = (java.lang.String) r8
                            boolean r8 = kotlin.text.StringsKt__StringsJVMKt.equals(r6, r8, r3)
                            if (r8 == 0) goto L48
                            r11.add(r6)
                            goto L48
                        L5e:
                            int r5 = r5 + 1
                            goto L3d
                        L61:
                            kotlin.collections.builders.SetBuilder r10 = r11.build()
                            boolean r11 = r10.isEmpty()
                            if (r11 == 0) goto L6c
                            r10 = 0
                        L6c:
                            if (r10 == 0) goto L79
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                            java.lang.Object r9 = r9.emit(r10, r0)
                            if (r9 != r1) goto L79
                            return r1
                        L79:
                            kotlin.Unit r9 = kotlin.Unit.INSTANCE
                            return r9
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, strArr2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        } else {
            flow = null;
        }
        return flow != null ? FlowKt.merge(safeFlow, flow) : safeFlow;
    }

    public final void refreshAsync() {
        TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = this.implementation;
        if (triggerBasedInvalidationTracker.pendingRefresh.compareAndSet(false, true)) {
            this.onRefreshScheduled.invoke();
            ContextScope contextScope = triggerBasedInvalidationTracker.database.coroutineScope;
            if (contextScope == null) {
                contextScope = null;
            }
            BuildersKt.launch$default(contextScope, new CoroutineName("Room Invalidation Tracker Refresh"), null, new TriggerBasedInvalidationTracker$refreshInvalidationAsync$3(triggerBasedInvalidationTracker, this.onRefreshCompleted, null), 2);
        }
    }

    public final Object sync$room_runtime_release(SuspendLambda suspendLambda) {
        RoomDatabase roomDatabase = this.database;
        if (roomDatabase.inCompatibilityMode$room_runtime_release() && !roomDatabase.isOpenInternal()) {
            return Unit.INSTANCE;
        }
        Object syncTriggers$room_runtime_release = this.implementation.syncTriggers$room_runtime_release(suspendLambda);
        return syncTriggers$room_runtime_release == CoroutineSingletons.COROUTINE_SUSPENDED ? syncTriggers$room_runtime_release : Unit.INSTANCE;
    }

    public InvalidationTracker(RoomDatabase roomDatabase, String... strArr) {
        this(roomDatabase, MapsKt__MapsKt.emptyMap(), MapsKt__MapsKt.emptyMap(), (String[]) Arrays.copyOf(strArr, strArr.length));
    }
}
