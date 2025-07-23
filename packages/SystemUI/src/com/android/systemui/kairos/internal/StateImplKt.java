package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableArrayMapK;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StateImplKt {
    public static final StateImpl mapStateImpl(final Function1 function1, String str, String str2, Function2 function2) {
        DerivedMap derivedMap = new DerivedMap(function1, function2);
        final StateImplKt$$ExternalSyntheticLambda1 stateImplKt$$ExternalSyntheticLambda1 = new StateImplKt$$ExternalSyntheticLambda1(function2, 2);
        return new StateImpl(str, str2, PullNodesKt.cached(new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxKt.DemuxImpl(1, new FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1(FilterNodeKt$filterPresentImpl$1.INSTANCE, PullNodesKt.cached(new StateImplKt$calm$$inlined$filterImpl$2(new StateImplKt$calm$$inlined$filterImpl$1(derivedMap), PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$mapStateImpl$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = ((StateImpl) function1.mo779invoke(evalScope)).changes.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        })))), new SingletonMapK.Factory()), Unit.INSTANCE)), derivedMap);
    }

    public static final StateImpl zipStateList(String str, final int i, Init init) {
        final StateImpl stateImpl;
        final Init init2 = new Init(null, new StateImplKt$$ExternalSyntheticLambda6(init, 0));
        final MutableArrayMapK.Factory factory = new MutableArrayMapK.Factory();
        if (i == 0) {
            stateImpl = new StateImpl(null, str, PullNodesKt.neverImpl, new StateSource(factory.create(0)));
        } else {
            final DerivedZipped derivedZipped = new DerivedZipped(i, init2, factory);
            final MuxLifecycle switchDeferredImpl = MuxDeferredKt.switchDeferredImpl(new StateImplKt$$ExternalSyntheticLambda6(init2, 1), new StateImplKt$$ExternalSyntheticLambda12(0), factory);
            final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    EvalScope evalScope = (EvalScope) obj;
                    MapK mapK = (MapK) obj2;
                    ((Integer) obj3).getClass();
                    MutableMapK create = MutableArrayMapK.Factory.this.create(Integer.valueOf(i));
                    for (Map.Entry entry : (Iterable) init2.connect(evalScope)) {
                        Object key = entry.getKey();
                        ((MutableArrayMapK) create).put(key, mapK.containsKey(key) ? ((PullNode) MapsKt__MapsKt.getValue(key, mapK)).getPushEvent(evalScope) : ((StateImpl) entry.getValue()).store.getCurrentWithEpoch(evalScope).getFirst());
                    }
                    DerivedZipped derivedZipped2 = derivedZipped;
                    derivedZipped2.getCurrentWithEpoch(evalScope);
                    long epoch = evalScope.getEpoch();
                    derivedZipped2.cache = create;
                    long j = epoch + 1;
                    derivedZipped2.validatedEpoch = j;
                    derivedZipped2.invalidatedEpoch = j;
                    return create;
                }
            };
            stateImpl = new StateImpl(null, str, PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$zipStates$$inlined$mapImpl$1
                @Override // com.android.systemui.kairos.internal.EventsImpl
                public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                    ActivationResult activate = switchDeferredImpl.activate(evalScope, schedulable);
                    if (activate == null) {
                        return null;
                    }
                    NodeConnection nodeConnection = activate.connection;
                    return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
                }
            }), derivedZipped);
        }
        final StateImplKt$$ExternalSyntheticLambda0 stateImplKt$$ExternalSyntheticLambda0 = new StateImplKt$$ExternalSyntheticLambda0(1);
        return new StateImpl(null, str, new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$zipStateList$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = stateImpl.changes.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        }, new DerivedMap(new StateImplKt$$ExternalSyntheticLambda6(stateImpl, 2), new StateImplKt$$ExternalSyntheticLambda8()));
    }
}
