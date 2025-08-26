package androidx.room;

import android.content.Intent;
import androidx.room.support.AutoCloser;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.internal.ContextScope;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract class Observer {
        public final String[] tables;

        public Observer(String[] strArr) {
            this.tables = strArr;
        }

        public abstract void onInvalidated(Set set);

        public Observer(String str, String... strArr) {
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(str);
            spreadBuilder.addSpread(strArr);
            this((String[]) spreadBuilder.list.toArray(new String[spreadBuilder.list.size()]));
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
        Pair pairValidateTableNames$room_runtime_release = triggerBasedInvalidationTracker.validateTableNames$room_runtime_release(strArr);
        final String[] strArr2 = (String[]) pairValidateTableNames$room_runtime_release.component1();
        SafeFlow safeFlow = new SafeFlow(new TriggerBasedInvalidationTracker$createFlow$1(triggerBasedInvalidationTracker, (int[]) pairValidateTableNames$room_runtime_release.component2(), true, strArr2, null));
        MultiInstanceInvalidationClient multiInstanceInvalidationClient = this.multiInstanceInvalidationClient;
        if (multiInstanceInvalidationClient != null) {
            final SharedFlowImpl sharedFlowImpl = multiInstanceInvalidationClient.invalidatedTables;
            flow = new Flow() { // from class: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1

                /* renamed from: androidx.room.MultiInstanceInvalidationClient$createFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ String[] $resolvedTableNames$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            Set set = (Set) obj;
                            SetBuilder setBuilder = new SetBuilder();
                            for (String str : this.$resolvedTableNames$inlined) {
                                Iterator it = set.iterator();
                                while (it.hasNext()) {
                                    if (StringsKt__StringsJVMKt.equals(str, (String) it.next(), true)) {
                                        setBuilder.add(str);
                                    }
                                }
                            }
                            SetBuilder setBuilderBuild = setBuilder.build();
                            if (setBuilderBuild.isEmpty()) {
                                setBuilderBuild = null;
                            }
                            if (setBuilderBuild != null) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(setBuilderBuild, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = sharedFlowImpl.collect(new AnonymousClass2(flowCollector, strArr2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

    public final Object sync$room_runtime_release(SuspendLambda suspendLambda) throws Throwable {
        RoomDatabase roomDatabase = this.database;
        if (roomDatabase.inCompatibilityMode$room_runtime_release() && !roomDatabase.isOpenInternal()) {
            return Unit.INSTANCE;
        }
        Object objSyncTriggers$room_runtime_release = this.implementation.syncTriggers$room_runtime_release(suspendLambda);
        return objSyncTriggers$room_runtime_release == CoroutineSingletons.COROUTINE_SUSPENDED ? objSyncTriggers$room_runtime_release : Unit.INSTANCE;
    }

    public InvalidationTracker(RoomDatabase roomDatabase, String... strArr) {
        this(roomDatabase, MapsKt__MapsKt.emptyMap(), MapsKt__MapsKt.emptyMap(), (String[]) Arrays.copyOf(strArr, strArr.length));
    }
}
