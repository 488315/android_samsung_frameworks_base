package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.EventsLoop;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.SwitchKt;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.kairos.Transactional;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final class EvalScopeImpl implements EvalScope, NetworkScope, DeferScope, TransactionScope {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final /* synthetic */ NetworkScope $$delegate_0;
    public final /* synthetic */ DeferScope $$delegate_1;
    public final Lazy now$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.kairos.internal.EvalScopeImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            EvalScopeImpl evalScopeImpl = this.f$0;
            KProperty[] kPropertyArr = EvalScopeImpl.$$delegatedProperties;
            final EventsLoop eventsLoop = new EventsLoop();
            StateSource stateSource = new StateSource((Lazy) new CompletableLazy(new EventsInit(new Init("now", new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.EvalScopeImpl$now_delegate$lambda$7$$inlined$EventsImplCheap$1
                @Override // com.android.systemui.kairos.internal.EventsImpl
                public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                    AlwaysNode alwaysNode = AlwaysNode.INSTANCE;
                    return new ActivationResult(new NodeConnection(alwaysNode, alwaysNode), true);
                }
            }))), null, 2, null));
            final StateImplKt$activatedStateSource$$inlined$filterImpl$1 stateImplKt$activatedStateSource$$inlined$filterImpl$1 = new StateImplKt$activatedStateSource$$inlined$filterImpl$1(stateSource);
            DemuxImpl DemuxImpl = DemuxKt.DemuxImpl(1, new FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1(FilterNodeKt$filterPresentImpl$1.INSTANCE, PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.EvalScopeImpl$now_delegate$lambda$7$$inlined$activatedStateSource$1
                @Override // com.android.systemui.kairos.internal.EventsImpl
                public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                    KProperty kProperty = EvalScopeImpl.$$delegatedProperties[0];
                    EventsLoop eventsLoop2 = eventsLoop;
                    eventsLoop2.getClass();
                    ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(EventsKt.mapCheap(eventsLoop2, new Function2() { // from class: com.android.systemui.kairos.internal.EvalScopeImpl$now$2$1$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return EventsKt.emptyEvents;
                        }
                    })).connect(evalScope)).activate(evalScope, schedulable);
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
            evalScopeImpl.scheduleOutput(output);
            EventsInit eventsInitSwitchEvents = SwitchKt.switchEvents(new StateInit(new Init("now", new InitKt$constInit$1(new StateImpl("now", "now", demuxImpl$eventsForKey$$inlined$EventsImplCheap$1, stateSource)))));
            KProperty[] kPropertyArr2 = EvalScopeImpl.$$delegatedProperties;
            KProperty kProperty = kPropertyArr2[0];
            eventsLoop.setLoopback(eventsInitSwitchEvents);
            KProperty kProperty2 = kPropertyArr2[0];
            return eventsLoop;
        }
    });

    static {
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(EvalScopeImpl.class, "result", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference0Impl};
    }

    public EvalScopeImpl(NetworkScope networkScope, DeferScope deferScope) {
        this.$$delegate_0 = networkScope;
        this.$$delegate_1 = deferScope;
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final void deferAction(Function0 function0) {
        this.$$delegate_1.deferAction(function0);
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final Lazy deferAsync(Function0 function0) {
        return this.$$delegate_1.deferAsync(function0);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue deferredTransactionScope(StateScope$DefaultImpls$$ExternalSyntheticLambda3 stateScope$DefaultImpls$$ExternalSyntheticLambda3) {
        return new DeferredValue(this.$$delegate_1.deferAsync(new EvalScopeImpl$$ExternalSyntheticLambda1(stateScope$DefaultImpls$$ExternalSyntheticLambda3, this, 2)));
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getCompactor() {
        return this.$$delegate_0.getCompactor();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final long getEpoch() {
        return this.$$delegate_0.getEpoch();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Network getNetwork() {
        return this.$$delegate_0.getNetwork();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Object getNetworkId() {
        return this.$$delegate_0.getNetworkId();
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Events getNow() {
        return (Events) this.now$delegate.getValue();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getScheduler() {
        return this.$$delegate_0.getScheduler();
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final TransactionStore getTransactionStore() {
        return this.$$delegate_0.getTransactionStore();
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(Transactional transactional) {
        return new DeferredValue(this.$$delegate_1.deferAsync(new EvalScopeImpl$$ExternalSyntheticLambda1(this, transactional))).unwrapped.getValue();
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue sampleDeferred(State state) {
        return new DeferredValue(this.$$delegate_1.deferAsync(new EvalScopeImpl$$ExternalSyntheticLambda1(state, this, 0)));
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void schedule(StateSource stateSource) {
        this.$$delegate_0.schedule(stateSource);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(Output output) {
        this.$$delegate_0.scheduleDeactivation(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleMuxMover(MuxDeferredNode muxDeferredNode) {
        this.$$delegate_0.scheduleMuxMover(muxDeferredNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleOutput(Output output) {
        this.$$delegate_0.scheduleOutput(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(PushNode pushNode) {
        this.$$delegate_0.scheduleDeactivation(pushNode);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(State state) {
        return sampleDeferred(state).unwrapped.getValue();
    }
}
