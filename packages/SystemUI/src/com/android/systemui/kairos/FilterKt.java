package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1;
import com.android.systemui.kairos.internal.DemuxKt;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1;
import com.android.systemui.kairos.internal.FilterNodeKt$filterPresentImpl$1;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import com.android.systemui.kairos.util.Maybe;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public abstract class FilterKt {
    public static final EventsInit filter(final Events events, final Function2 function2) {
        final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.FilterKt$filter$$inlined$filterImpl$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                if (((Boolean) function2.invoke((EvalScope) obj, obj2)).booleanValue()) {
                    Maybe.Companion.getClass();
                    return Maybe.Present.m2590boximpl(obj2);
                }
                Maybe.Companion.getClass();
                return Maybe.Companion.absent;
            }
        };
        return new EventsInit(new Init(null, new InitKt$constInit$1(new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxKt.DemuxImpl(1, new FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1(FilterNodeKt$filterPresentImpl$1.INSTANCE, PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.FilterKt$filter$$inlined$filterImpl$2
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(events).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        })), new SingletonMapK.Factory()), Unit.INSTANCE))));
    }

    public static final EventsInit filterPresent(final EventsInit eventsInit) {
        final FilterNodeKt$filterPresentImpl$1 filterNodeKt$filterPresentImpl$1 = FilterNodeKt$filterPresentImpl$1.INSTANCE;
        return new EventsInit(new Init(null, new InitKt$constInit$1(new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxKt.DemuxImpl(1, new EventsImpl() { // from class: com.android.systemui.kairos.FilterKt$filterPresent$$inlined$filterPresentImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) EventsKt.getInit(eventsInit).connect(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, filterNodeKt$filterPresentImpl$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }, new SingletonMapK.Factory()), Unit.INSTANCE))));
    }
}
