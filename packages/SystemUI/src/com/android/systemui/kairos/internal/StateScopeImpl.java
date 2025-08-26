package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.DeferredValueKt;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.EventsLoop;
import com.android.systemui.kairos.IncrementalInit;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateScope;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda5;
import com.android.systemui.kairos.SwitchKt;
import com.android.systemui.kairos.TransactionScopeKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.Transactional;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import com.android.systemui.kairos.util.Maybe;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes2.dex */
public final class StateScopeImpl implements EvalScope, StateScope {
    public final Lazy endSignalLazy;
    public final Lazy endSignalOnce$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.kairos.internal.StateScopeImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            StateScopeImpl stateScopeImpl = this.f$0;
            Events endSignal = stateScopeImpl.getEndSignal();
            if (endSignal == EventsKt.emptyEvents) {
                return endSignal;
            }
            EventsLoop eventsLoop = new EventsLoop();
            eventsLoop.setLoopback(SwitchKt.switchEvents(stateScopeImpl.holdStateInternalDeferred(EventsKt.mapCheap(eventsLoop, new StateScopeImpl$$ExternalSyntheticLambda1(1)), "StateScope.endSignal", new CompletableLazy(endSignal, null, 2, null))));
            return eventsLoop;
        }
    });
    public final EvalScope evalScope;

    public StateScopeImpl(EvalScope evalScope, Lazy lazy) {
        this.evalScope = evalScope;
        this.endSignalLazy = lazy;
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final void deferAction(Function0 function0) {
        this.evalScope.deferAction(function0);
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final Lazy deferAsync(Function0 function0) {
        return this.evalScope.deferAsync(function0);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue deferredTransactionScope(StateScope$DefaultImpls$$ExternalSyntheticLambda3 stateScope$DefaultImpls$$ExternalSyntheticLambda3) {
        return this.evalScope.deferredTransactionScope(stateScope$DefaultImpls$$ExternalSyntheticLambda3);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.android.systemui.kairos.State, com.android.systemui.kairos.StateInit] */
    public final State foldState(Events events, Object obj, final Function3 function3) {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? HoldState = holdState(EventsKt.map(events, new Function2() { // from class: com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                TransactionScope transactionScope = (TransactionScope) obj2;
                T t = ref$ObjectRef.element;
                return function3.invoke(transactionScope, obj3, transactionScope.sample(t == 0 ? null : (State) t));
            }
        }), obj);
        ref$ObjectRef.element = HoldState;
        return HoldState;
    }

    public final IncrementalInit foldStateMapIncrementally(final Events events, DeferredValue deferredValue) {
        EvalScope evalScope = this.evalScope;
        final StateSource stateSource = new StateSource(deferredValue.unwrapped);
        final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$maybeChanges$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                Pair pairAccess$applyPatchCalm = IncrementalImplKt.access$applyPatchCalm((Map) stateSource.getCurrentWithEpoch((EvalScope) obj).component1(), (Map) obj2);
                if (pairAccess$applyPatchCalm == null) {
                    return Maybe.Absent.INSTANCE;
                }
                Maybe.Companion.getClass();
                return Maybe.Present.m2590boximpl(pairAccess$applyPatchCalm);
            }
        };
        final PullNodesKt$cached$$inlined$EventsImplCheap$1 pullNodesKt$cached$$inlined$EventsImplCheap$1Cached = PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateScopeImpl$foldStateMapIncrementally$$inlined$activatedIncremental$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(events).connect(evalScope2)).activate(evalScope2, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        });
        final FilterNodeKt$filterPresentImpl$1 filterNodeKt$filterPresentImpl$1 = FilterNodeKt$filterPresentImpl$1.INSTANCE;
        DemuxImpl DemuxImpl = DemuxKt.DemuxImpl(1, new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$$inlined$filterPresentImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                ActivationResult activationResultActivate = pullNodesKt$cached$$inlined$EventsImplCheap$1Cached.activate(evalScope2, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, filterNodeKt$filterPresentImpl$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }, new SingletonMapK.Factory());
        Unit unit = Unit.INSTANCE;
        final DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1 demuxImpl$eventsForKey$$inlined$EventsImplCheap$1 = new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxImpl, unit);
        final IncrementalImplKt$activatedIncremental$changes$2 incrementalImplKt$activatedIncremental$changes$2 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$changes$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                return (Map) ((Pair) obj2).component2();
            }
        };
        final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                ActivationResult activationResultActivate = demuxImpl$eventsForKey$$inlined$EventsImplCheap$1.activate(evalScope2, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, incrementalImplKt$activatedIncremental$changes$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
        final IncrementalImplKt$activatedIncremental$patches$2 incrementalImplKt$activatedIncremental$patches$2 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$patches$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                return (Map) ((Pair) obj2).component1();
            }
        };
        EventsImpl eventsImpl2 = new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$$inlined$mapImpl$2
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                ActivationResult activationResultActivate = demuxImpl$eventsForKey$$inlined$EventsImplCheap$1.activate(evalScope2, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, incrementalImplKt$activatedIncremental$patches$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
        Output output = new Output(null, null, new Function2() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$activatedIncremental$$inlined$OneShot$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                EvalScope evalScope2 = (EvalScope) obj;
                StateSource stateSource2 = stateSource;
                ActivationResult activationResultActivate = eventsImpl.activate(evalScope2, new Schedulable.S(stateSource2));
                if (activationResultActivate != null) {
                    stateSource2.upstreamConnection = activationResultActivate.connection;
                    if (activationResultActivate.needsEval) {
                        evalScope2.schedule(stateSource2);
                    }
                }
                return Unit.INSTANCE;
            }
        }, 3, null);
        output.result = unit;
        evalScope.scheduleOutput(output);
        return new IncrementalInit(new Init("foldStateMapIncrementally", new InitKt$constInit$1(new IncrementalImpl("foldStateMapIncrementally", "foldStateMapIncrementally", eventsImpl, eventsImpl2, stateSource))));
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getCompactor() {
        return this.evalScope.getCompactor();
    }

    public final Events getEndSignal() {
        return (Events) this.endSignalLazy.getValue();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final long getEpoch() {
        return this.evalScope.getEpoch();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Network getNetwork() {
        return this.evalScope.getNetwork();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Object getNetworkId() {
        return this.evalScope.getNetworkId();
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Events getNow() {
        return this.evalScope.getNow();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getScheduler() {
        return this.evalScope.getScheduler();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final TransactionStore getTransactionStore() {
        return this.evalScope.getTransactionStore();
    }

    public final StateInit holdState(Events events, Object obj) {
        return holdStateDeferred(events, DeferredValueKt.deferredOf(obj));
    }

    public final StateInit holdStateDeferred(Events events, DeferredValue deferredValue) {
        Lazy lazy = this.endSignalOnce$delegate;
        if (((Events) lazy.getValue()) != EventsKt.emptyEvents) {
            events = SwitchKt.switchEvents(holdStateInternalDeferred(EventsKt.mapCheap((Events) lazy.getValue(), new StateScopeImpl$$ExternalSyntheticLambda1(0)), "holdStateDeferred", new CompletableLazy(events, null, 2, null)));
        }
        return holdStateInternalDeferred(events, "holdStateDeferred", deferredValue.unwrapped);
    }

    public final StateInit holdStateInternalDeferred(final Events events, String str, Lazy lazy) {
        EvalScope evalScope = this.evalScope;
        StateSource stateSource = new StateSource(lazy);
        final StateImplKt$activatedStateSource$$inlined$filterImpl$1 stateImplKt$activatedStateSource$$inlined$filterImpl$1 = new StateImplKt$activatedStateSource$$inlined$filterImpl$1(stateSource);
        DemuxImpl DemuxImpl = DemuxKt.DemuxImpl(1, new FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1(FilterNodeKt$filterPresentImpl$1.INSTANCE, PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateScopeImpl$holdStateInternalDeferred$$inlined$activatedStateSource$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(events).connect(evalScope2)).activate(evalScope2, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, stateImplKt$activatedStateSource$$inlined$filterImpl$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        })), new SingletonMapK.Factory());
        Unit unit = Unit.INSTANCE;
        DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1 demuxImpl$eventsForKey$$inlined$EventsImplCheap$1 = new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxImpl, unit);
        Output output = new Output(null, null, new StateImplKt$activatedStateSource$$inlined$OneShot$1(demuxImpl$eventsForKey$$inlined$EventsImplCheap$1, stateSource), 3, null);
        output.result = unit;
        evalScope.scheduleOutput(output);
        return new StateInit(new Init(str, new InitKt$constInit$1(new StateImpl(str, str, demuxImpl$eventsForKey$$inlined$EventsImplCheap$1, stateSource))));
    }

    public final Events nextOnly(EventsInit eventsInit) {
        if (eventsInit == EventsKt.emptyEvents) {
            return eventsInit;
        }
        EventsLoop eventsLoop = new EventsLoop();
        eventsLoop.setLoopback(SwitchKt.switchEvents(holdState(EventsKt.mapCheap(eventsLoop, new StateScope$DefaultImpls$$ExternalSyntheticLambda5(2)), eventsInit)));
        return eventsLoop;
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(State state) {
        return this.evalScope.sample(state);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue sampleDeferred(State state) {
        return this.evalScope.sampleDeferred(state);
    }

    public final StateInit sampleTransactionals(StateInit stateInit) {
        return holdStateDeferred(EventsKt.map(StateKt.getChanges(stateInit), new TransactionScopeKt$$ExternalSyntheticLambda0()), this.evalScope.deferredTransactionScope(new StateScope$DefaultImpls$$ExternalSyntheticLambda3(stateInit, 0)));
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void schedule(StateSource stateSource) {
        this.evalScope.schedule(stateSource);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(Output output) {
        this.evalScope.scheduleDeactivation(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleMuxMover(MuxDeferredNode muxDeferredNode) {
        this.evalScope.scheduleMuxMover(muxDeferredNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleOutput(Output output) {
        this.evalScope.scheduleOutput(output);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(Transactional transactional) {
        return this.evalScope.sample(transactional);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(PushNode pushNode) {
        this.evalScope.scheduleDeactivation(pushNode);
    }
}
