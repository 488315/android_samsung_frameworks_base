package com.android.systemui.kairos.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda12;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda2;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda7;
import com.android.systemui.kairos.BuildScope$toStateFlow$2;
import com.android.systemui.kairos.CoalescingMutableEvents;
import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.DeferredValueKt;
import com.android.systemui.kairos.EmptyEvents;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.FilterKt;
import com.android.systemui.kairos.GroupByKt;
import com.android.systemui.kairos.GroupedEvents;
import com.android.systemui.kairos.LocalNetwork;
import com.android.systemui.kairos.MergeKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda1;
import com.android.systemui.kairos.StateScope;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda5;
import com.android.systemui.kairos.SwitchKt;
import com.android.systemui.kairos.Transactional;
import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.MutableArrayMapK;
import com.android.systemui.kairos.internal.util.UtilKt;
import com.android.systemui.kairos.util.Maybe;
import com.android.systemui.kairos.util.These;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.Unconfined;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes2.dex */
public final class BuildScopeImpl implements EvalScope, StateScope, BuildScope {
    public final CoroutineScope coroutineScope;
    public final Lazy kairosNetwork$delegate = LazyKt__LazyJVMKt.lazy(new BuildScopeImpl$$ExternalSyntheticLambda5(this, 3));
    public final StateScopeImpl stateScope;

    public BuildScopeImpl(StateScopeImpl stateScopeImpl, CoroutineScope coroutineScope) {
        this.stateScope = stateScopeImpl;
        this.coroutineScope = coroutineScope;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.Object] */
    public static Events buildEvents$default(final BuildScopeImpl buildScopeImpl, Function1 function1, final Function2 function2) {
        buildScopeImpl.getClass();
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final CoalescingMutableEvents coalescingMutableEvents = new CoalescingMutableEvents(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("buildEvents[", null, "]"), new BuildScopeImpl$$ExternalSyntheticLambda17(), buildScopeImpl.getNetwork(), new BuildScopeImpl$$ExternalSyntheticLambda18(), null, 16, null);
        final ContextScope contextScopeChildScope$default = UtilKt.childScope$default(buildScopeImpl.coroutineScope);
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        ?? Mo781invoke = function1.mo781invoke(new InputNode(new Function1(contextScopeChildScope$default, buildScopeImpl, function2, ref$ObjectRef2, coalescingMutableEvents) { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda14
            public final /* synthetic */ ContextScope f$1;
            public final /* synthetic */ BuildScopeImpl f$3;
            public final /* synthetic */ SuspendLambda f$4;
            public final /* synthetic */ Ref$ObjectRef f$5;
            public final /* synthetic */ CoalescingMutableEvents f$6;

            /* JADX WARN: Multi-variable type inference failed */
            {
                this.f$4 = (SuspendLambda) function2;
                this.f$5 = ref$ObjectRef2;
                this.f$6 = coalescingMutableEvents;
            }

            /* JADX WARN: Type inference failed for: r4v0, types: [kotlin.coroutines.jvm.internal.SuspendLambda, kotlin.jvm.functions.Function2] */
            /* JADX WARN: Type inference failed for: r8v3, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                Ref$ObjectRef ref$ObjectRef3 = this.f$0;
                if (ref$ObjectRef3.element != 0) {
                    throw new IllegalStateException("[null] already activated".toString());
                }
                ContextScope contextScope = this.f$1;
                BuildScopeImpl$buildEvents$inputNode$1$2 buildScopeImpl$buildEvents$inputNode$1$2 = new BuildScopeImpl$buildEvents$inputNode$1$2(evalScope, this.f$3, contextScope, this.f$4, this.f$5, this.f$6, null);
                CoroutineStart coroutineStart = CoroutineStart.UNDISPATCHED;
                EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
                Unconfined unconfined = Dispatchers.Unconfined;
                unconfined.getClass();
                ref$ObjectRef3.element = BuildersKt.launch(contextScope, CoroutineContext.DefaultImpls.plus(unconfined, emptyCoroutineContext), coroutineStart, buildScopeImpl$buildEvents$inputNode$1$2);
                return Unit.INSTANCE;
            }
        }, new BuildScopeImpl$$ExternalSyntheticLambda5(ref$ObjectRef, 1)));
        ref$ObjectRef2.element = Mo781invoke;
        Events events = (Events) (Mo781invoke != 0 ? (Pair) Mo781invoke : null).getFirst();
        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
        EventsInit eventsInitMergeLeft = MergeKt.mergeLeft(coalescingMutableEvents, stateScopeImpl.getEndSignal());
        stateScopeImpl.getClass();
        return eventsInitMergeLeft == EventsKt.emptyEvents ? events : SwitchKt.switchEvents(stateScopeImpl.holdState(stateScopeImpl.nextOnly(EventsKt.mapCheap(eventsInitMergeLeft, new StateScope$DefaultImpls$$ExternalSyntheticLambda5(0))), events));
    }

    public final Pair applyLatestSpec(EventsInit eventsInit, Function1 function1) {
        EventsInit eventsInitMapCheap = EventsKt.mapCheap(eventsInit, new BuildScope$DefaultImpls$$ExternalSyntheticLambda0(1));
        Pair pair = new Pair(Unit.INSTANCE, function1);
        Pair pairApplyLatestSpecForKey = applyLatestSpecForKey(eventsInitMapCheap, DeferredValueKt.deferredOf(Collections.singletonMap(pair.getFirst(), pair.getSecond())), 1);
        return new Pair(FilterKt.filterPresent(EventsKt.map((Events) pairApplyLatestSpecForKey.component1(), new BuildScope$DefaultImpls$$ExternalSyntheticLambda0(2))), new DeferredValue(deferAsync(new BuildScopeImpl$$ExternalSyntheticLambda0(new BuildScope$DefaultImpls$$ExternalSyntheticLambda12((DeferredValue) pairApplyLatestSpecForKey.component2(), 1), this, 2))));
    }

    public final Pair applyLatestSpecForKey(final EventsInit eventsInit, final DeferredValue deferredValue, Integer num) {
        final GroupedEvents groupedEventsGroupByKey = GroupByKt.groupByKey(eventsInit, num);
        Lazy lazyDeferAsync = deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Map map = (Map) deferredValue.unwrapped.getValue();
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                for (Map.Entry entry : map.entrySet()) {
                    Object key = entry.getKey();
                    Object key2 = entry.getKey();
                    linkedHashMap.put(key, ((Function1) entry.getValue()).mo781invoke(this.childBuildScope(groupedEventsGroupByKey.get(key2))));
                }
                return linkedHashMap;
            }
        });
        final ContextScope contextScopeChildScope$default = UtilKt.childScope$default(this.coroutineScope);
        final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Object objM2590boximpl;
                Map map = (Map) obj2;
                ((Integer) obj3).getClass();
                BuildScopeImpl buildScopeImpl = new BuildScopeImpl(new StateScopeImpl((EvalScope) obj, this.f$0.stateScope.endSignalLazy), contextScopeChildScope$default);
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                for (Map.Entry entry : map.entrySet()) {
                    Object key = entry.getKey();
                    Object key2 = entry.getKey();
                    Maybe maybe = (Maybe) entry.getValue();
                    if (maybe instanceof Maybe.Present) {
                        Maybe.Companion companion = Maybe.Companion;
                        Function1 function1 = (Function1) ((Maybe.Present) maybe).value;
                        EventsInit eventsInitSwitchEvents = groupedEventsGroupByKey.get(key2);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        EmptyEvents emptyEvents = EventsKt.emptyEvents;
                        if (eventsInitSwitchEvents != emptyEvents) {
                            eventsInitSwitchEvents = SwitchKt.switchEvents(stateScopeImpl.holdState(EventsKt.mapCheap(stateScopeImpl.nextOnly(eventsInitSwitchEvents), new StateScope$DefaultImpls$$ExternalSyntheticLambda0(eventsInitSwitchEvents, 1)), emptyEvents));
                        }
                        Object objMo781invoke = function1.mo781invoke(buildScopeImpl.childBuildScope(eventsInitSwitchEvents));
                        companion.getClass();
                        objM2590boximpl = Maybe.Present.m2590boximpl(objMo781invoke);
                    } else {
                        if (!(maybe instanceof Maybe.Absent)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        objM2590boximpl = Maybe.Absent.INSTANCE;
                    }
                    linkedHashMap.put(key, objM2590boximpl);
                }
                return linkedHashMap;
            }
        };
        EventsInit eventsInit2 = new EventsInit(new Init("applyLatestForKey", new InitKt$constInit$1(PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$applyLatestSpecForKey$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(eventsInit).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }))));
        BuildScope.DefaultImpls.observe$default(this, eventsInit2, null, 3);
        return new Pair(eventsInit2, new DeferredValue(lazyDeferAsync));
    }

    public final BuildScopeImpl childBuildScope(EventsInit eventsInit) {
        final ContextScope contextScopeChildScope$default = UtilKt.childScope$default(this.coroutineScope);
        StateScopeImpl stateScopeImpl = this.stateScope;
        stateScopeImpl.getClass();
        BuildScopeImpl buildScopeImpl = new BuildScopeImpl(new StateScopeImpl(stateScopeImpl.evalScope, LazyKt__LazyJVMKt.lazy(new StateScopeImpl$$ExternalSyntheticLambda3(eventsInit, stateScopeImpl, 0))), contextScopeChildScope$default);
        Output output = new Output(null, null, new Function2() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$childBuildScope$lambda$30$$inlined$OneShot$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                JobImpl jobImpl = (JobImpl) JobKt.getJob(contextScopeChildScope$default.getCoroutineContext());
                Unit unit = Unit.INSTANCE;
                jobImpl.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
                return unit;
            }
        }, 3, null);
        output.result = Unit.INSTANCE;
        buildScopeImpl.scheduleOutput(output);
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, (Events) buildScopeImpl.stateScope.endSignalOnce$delegate.getValue(), new BuildScopeImpl$$ExternalSyntheticLambda11(contextScopeChildScope$default, 1), 1);
        return buildScopeImpl;
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final void deferAction(Function0 function0) {
        this.stateScope.deferAction(function0);
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final Lazy deferAsync(Function0 function0) {
        return this.stateScope.evalScope.deferAsync(function0);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue deferredTransactionScope(StateScope$DefaultImpls$$ExternalSyntheticLambda3 stateScope$DefaultImpls$$ExternalSyntheticLambda3) {
        return this.stateScope.evalScope.deferredTransactionScope(stateScope$DefaultImpls$$ExternalSyntheticLambda3);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getCompactor() {
        return this.stateScope.evalScope.getCompactor();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final long getEpoch() {
        return this.stateScope.evalScope.getEpoch();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Network getNetwork() {
        return this.stateScope.evalScope.getNetwork();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Object getNetworkId() {
        return this.stateScope.evalScope.getNetworkId();
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Events getNow() {
        return this.stateScope.evalScope.getNow();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getScheduler() {
        return this.stateScope.evalScope.getScheduler();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final TransactionStore getTransactionStore() {
        return this.stateScope.evalScope.getTransactionStore();
    }

    public final StateInit mapLatestBuild(State state, Function2 function2) {
        StateInit stateInit = new StateInit(new Init("map", new StateKt$$ExternalSyntheticLambda1(state, new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(3, function2), 2)));
        Pair pairApplyLatestSpec = applyLatestSpec(StateKt.getChanges(stateInit), new BuildScope$DefaultImpls$$ExternalSyntheticLambda12(stateInit, 0));
        return this.stateScope.holdStateDeferred((Events) pairApplyLatestSpec.component1(), (DeferredValue) pairApplyLatestSpec.component2());
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [T, kotlinx.coroutines.DisposableHandle] */
    public final BuildScopeImpl$observe$handle$1 observe(final Events events, CoroutineContext coroutineContext, final Function2 function2) {
        final AtomicReference atomicReference = new AtomicReference(null);
        final ContextScope contextScopeChildScope$default = UtilKt.childScope$default(this.coroutineScope);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        BuildScopeImpl$observe$handle$1 buildScopeImpl$observe$handle$1 = new BuildScopeImpl$observe$handle$1(ref$ObjectRef, atomicReference, this);
        ref$ObjectRef.element = JobKt.getJob(contextScopeChildScope$default.coroutineContext).invokeOnCompletion(new BuildScopeImpl$$ExternalSyntheticLambda4(buildScopeImpl$observe$handle$1, 0));
        final LocalNetwork localNetwork = new LocalNetwork(getNetwork(), contextScopeChildScope$default, this.stateScope.getEndSignal());
        final Output output = new Output(coroutineContext, new BuildScopeImpl$$ExternalSyntheticLambda5(atomicReference, 0), new Function2() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                EvalScope evalScope = (EvalScope) obj;
                if (atomicReference.get() instanceof Maybe.Present) {
                    function2.invoke(new BuildScopeImpl$observe$outputNode$2$scope$1(evalScope, contextScopeChildScope$default, localNetwork), obj2);
                }
                return Unit.INSTANCE;
            }
        });
        deferAction(new Function0() { // from class: com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AtomicReference atomicReference2 = atomicReference;
                BuildScopeImpl buildScopeImpl = this;
                Events eventsSwitchEvents = events;
                Output output2 = output;
                ContextScope contextScope = contextScopeChildScope$default;
                if (atomicReference2.get() != null) {
                    return Unit.INSTANCE;
                }
                Events endSignal = buildScopeImpl.stateScope.getEndSignal();
                StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                stateScopeImpl.getClass();
                if (endSignal != EventsKt.emptyEvents) {
                    eventsSwitchEvents = SwitchKt.switchEvents(stateScopeImpl.holdState(stateScopeImpl.nextOnly(EventsKt.mapCheap(endSignal, new StateScope$DefaultImpls$$ExternalSyntheticLambda5(0))), eventsSwitchEvents));
                }
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(eventsSwitchEvents).connect(buildScopeImpl.stateScope.evalScope)).activate(buildScopeImpl.stateScope.evalScope, output2.schedulable);
                if (activationResultActivate != null) {
                    NodeConnection nodeConnection = activationResultActivate.connection;
                    boolean z = activationResultActivate.needsEval;
                    output2.upstream = nodeConnection;
                    Maybe.Companion.getClass();
                    if (!atomicReference2.compareAndSet(null, Maybe.Present.m2590boximpl(output2))) {
                        buildScopeImpl.scheduleDeactivation(output2);
                    } else if (z) {
                        EvalScope evalScope = buildScopeImpl.stateScope.evalScope;
                        NodeConnection nodeConnection2 = output2.upstream;
                        if (nodeConnection2 == null) {
                            throw new IllegalStateException("output scheduled with null upstream");
                        }
                        output2.result = nodeConnection2.directUpstream.getPushEvent(evalScope);
                        evalScope.scheduleOutput(output2);
                    }
                } else {
                    CoroutineScopeKt.cancel(contextScope, null);
                }
                return Unit.INSTANCE;
            }
        });
        return buildScopeImpl$observe$handle$1;
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(State state) {
        return this.stateScope.evalScope.sample(state);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue sampleDeferred(State state) {
        return this.stateScope.evalScope.sampleDeferred(state);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void schedule(StateSource stateSource) {
        this.stateScope.schedule(stateSource);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(Output output) {
        this.stateScope.scheduleDeactivation(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleMuxMover(MuxDeferredNode muxDeferredNode) {
        this.stateScope.scheduleMuxMover(muxDeferredNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleOutput(Output output) {
        this.stateScope.scheduleOutput(output);
    }

    public final BuildScope$toStateFlow$2 toStateFlow(State state) {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(sampleDeferred(state));
        BuildScope.DefaultImpls.observe$default(this, StateKt.getChanges(state), new BuildScope$DefaultImpls$$ExternalSyntheticLambda2(stateFlowImplMutableStateFlow, 1), 1);
        return new BuildScope$toStateFlow$2(stateFlowImplMutableStateFlow);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(PushNode pushNode) {
        this.stateScope.scheduleDeactivation(pushNode);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(Transactional transactional) {
        return this.stateScope.evalScope.sample(transactional);
    }

    public final BuildScopeImpl$observe$handle$1 observe(State state, Function2 function2) {
        final EventsInit map = EventsKt.map(getNow(), new BuildScope$DefaultImpls$$ExternalSyntheticLambda2(state, 0));
        final EventsInit changes = StateKt.getChanges(state);
        final BuildScope$DefaultImpls$$ExternalSyntheticLambda3 buildScope$DefaultImpls$$ExternalSyntheticLambda3 = new BuildScope$DefaultImpls$$ExternalSyntheticLambda3();
        final MuxDeferredKt$mergeNodes$storage$1 muxDeferredKt$mergeNodes$storage$1 = MuxDeferredKt$mergeNodes$storage$1.INSTANCE;
        EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.MergeKt$mergeWith$$inlined$mergeNodes$default$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(map).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxDeferredKt$mergeNodes$storage$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
        final MuxDeferredKt$mergeNodes$storage$2 muxDeferredKt$mergeNodes$storage$2 = MuxDeferredKt$mergeNodes$storage$2.INSTANCE;
        return BuildScope.DefaultImpls.observe$default(this, new EventsInit(new Init(null, new InitKt$constInit$1(PullNodesKt.cached(new MuxDeferredKt$mergeNodes$$inlined$mapImpl$2(new Function3() { // from class: com.android.systemui.kairos.MergeKt$mergeWith$$inlined$mergeNodes$default$3
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                EvalScope evalScope = (EvalScope) obj;
                These these = (These) obj2;
                ((Number) obj3).intValue();
                if (these instanceof These.First) {
                    return ((These.First) these).value;
                }
                if (these instanceof These.Second) {
                    return ((These.Second) these).value;
                }
                if (!(these instanceof These.Both)) {
                    throw new NoWhenBranchMatchedException();
                }
                These.Both both = (These.Both) these;
                return buildScope$DefaultImpls$$ExternalSyntheticLambda3.invoke(evalScope, both.first, both.second);
            }
        }, PullNodesKt.cached(new MuxDeferredKt$mergeNodes$$inlined$mapImpl$1(MuxDeferredKt$mergeNodes$merged$4.INSTANCE, MuxDeferredKt.switchDeferredImpl(new MuxDeferredKt$mergeNodes$switchNode$1(MuxDeferredKt.asIterableWithIndex(Arrays.asList(eventsImpl, new EventsImpl() { // from class: com.android.systemui.kairos.MergeKt$mergeWith$$inlined$mergeNodes$default$2
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(changes).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxDeferredKt$mergeNodes$storage$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }))), MuxDeferredKt$mergeNodes$switchNode$2.INSTANCE, new MutableArrayMapK.Factory())))))))), new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(1, function2), 1);
    }
}
