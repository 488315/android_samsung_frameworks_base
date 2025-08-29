package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.DemuxKt;
import com.android.systemui.kairos.internal.DemuxKt$demuxMap$1;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.store.ConcurrentHashMapK;
import com.android.systemui.kairos.internal.store.MapHolder;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public abstract class GroupByKt {
    public static final GroupedEvents groupByKey(final EventsInit eventsInit, Integer num) {
        final Function1 function1 = new Function1() { // from class: com.android.systemui.kairos.GroupByKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return (EventsImpl) EventsKt.getInit(eventsInit).connect((EvalScope) obj);
            }
        };
        final DemuxKt$demuxMap$1 demuxKt$demuxMap$1 = new Function3() { // from class: com.android.systemui.kairos.internal.DemuxKt$demuxMap$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                return MapHolder.m2584boximpl((Map) obj2);
            }
        };
        return new GroupedEvents(DemuxKt.DemuxImpl(num, new EventsImpl() { // from class: com.android.systemui.kairos.internal.PullNodesKt$mapImpl$$inlined$EventsImplCheap$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((EventsImpl) function1.mo781invoke(evalScope)).activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, demuxKt$demuxMap$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        }, new ConcurrentHashMapK.Factory()));
    }
}
