package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.Schedulable;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class EventsKt {
    public static final EmptyEvents emptyEvents;

    static {
        Events.Companion.getClass();
        emptyEvents = Events.empty;
    }

    public static final Init getInit(Events events) {
        if (events instanceof EmptyEvents) {
            return new Init("EmptyEvents", new InitKt$constInit$1(PullNodesKt.neverImpl));
        }
        if (events instanceof EventsInit) {
            return ((EventsInit) events).init;
        }
        if (events instanceof EventsLoop) {
            return ((EventsLoop) events).init;
        }
        if (events instanceof CoalescingMutableEvents) {
            CoalescingMutableEvents coalescingMutableEvents = (CoalescingMutableEvents) events;
            String str = coalescingMutableEvents.name;
            final InputNode inputNode = coalescingMutableEvents.impl;
            return new Init(str, new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.InputsKt$activated$$inlined$EventsImplCheap$1
                @Override // com.android.systemui.kairos.internal.EventsImpl
                public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                    InputNode inputNode2 = inputNode;
                    DownstreamSet downstreamSet = inputNode2.downstreamSet;
                    boolean zIsEmpty = GraphKt.isEmpty(downstreamSet);
                    downstreamSet.add(schedulable);
                    if (zIsEmpty && !inputNode2.activated.getAndSet(true)) {
                        inputNode2.activate.mo781invoke(evalScope);
                    }
                    return new ActivationResult(new NodeConnection(inputNode2, inputNode2), inputNode2.transactionCache.epoch == evalScope.getEpoch());
                }
            }));
        }
        if (!(events instanceof MutableEvents)) {
            throw new NoWhenBranchMatchedException();
        }
        MutableEvents mutableEvents = (MutableEvents) events;
        mutableEvents.getClass();
        final InputNode inputNode2 = mutableEvents.impl;
        return new Init(null, new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.InputsKt$activated$$inlined$EventsImplCheap$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                InputNode inputNode22 = inputNode2;
                DownstreamSet downstreamSet = inputNode22.downstreamSet;
                boolean zIsEmpty = GraphKt.isEmpty(downstreamSet);
                downstreamSet.add(schedulable);
                if (zIsEmpty && !inputNode22.activated.getAndSet(true)) {
                    inputNode22.activate.mo781invoke(evalScope);
                }
                return new ActivationResult(new NodeConnection(inputNode22, inputNode22), inputNode22.transactionCache.epoch == evalScope.getEpoch());
            }
        }));
    }

    public static final EventsInit map(final Events events, Function2 function2) {
        final EventsKt$$ExternalSyntheticLambda1 eventsKt$$ExternalSyntheticLambda1 = new EventsKt$$ExternalSyntheticLambda1(1, function2);
        return new EventsInit(new Init(null, new InitKt$constInit$1(PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.EventsKt$map$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(events).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, eventsKt$$ExternalSyntheticLambda1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }))));
    }

    public static final EventsInit mapCheap(final Events events, Function2 function2) {
        final EventsKt$$ExternalSyntheticLambda1 eventsKt$$ExternalSyntheticLambda1 = new EventsKt$$ExternalSyntheticLambda1(0, function2);
        return new EventsInit(new Init(null, new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.EventsKt$mapCheap$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(events).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, eventsKt$$ExternalSyntheticLambda1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        })));
    }

    public static final EventsInit mapNotNull(Events events, Function2 function2) {
        return FilterKt.filterPresent(map(events, new EventsKt$$ExternalSyntheticLambda0(0, function2)));
    }
}
