package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.MuxDeferredKt;
import com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodes$merged$6;
import com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodesLeft$merged$2;
import com.android.systemui.kairos.internal.MuxLifecycle;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.store.ArrayMapK;
import com.android.systemui.kairos.internal.store.ArrayMapK$entries$1;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableArrayMapK;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MergeKt {
    public static final EventsInit merge(Events... eventsArr) {
        final Iterable asIterable = ArraysKt___ArraysKt.asIterable(eventsArr);
        final MuxLifecycle switchDeferredImpl = MuxDeferredKt.switchDeferredImpl(new Function1() { // from class: com.android.systemui.kairos.MergeKt$merge$$inlined$mergeNodes$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                Iterable iterable = asIterable;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add((EventsImpl) EventsKt.getInit((Events) it.next()).connect(evalScope));
                }
                return MuxDeferredKt.asIterableWithIndex(arrayList);
            }
        }, new Function1() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodes$switchNode$4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return PullNodesKt.neverImpl;
            }
        }, new MutableArrayMapK.Factory());
        final MuxDeferredKt$mergeNodes$merged$6 muxDeferredKt$mergeNodes$merged$6 = new Function3() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodes$merged$6
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                EvalScope evalScope = (EvalScope) obj;
                ((Number) obj3).intValue();
                ArrayMapK arrayMapK = (ArrayMapK) ((MapK) obj2);
                ArrayList arrayList = new ArrayList(arrayMapK.getSize());
                Iterator it = ((ArrayMapK$entries$1) arrayMapK.getEntries()).iterator();
                while (it.hasNext()) {
                    arrayList.add(((PullNode) ((Map.Entry) it.next()).getValue()).getPushEvent(evalScope));
                }
                return arrayList;
            }
        };
        return new EventsInit(new Init(null, new InitKt$constInit$1(PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodes$$inlined$mapImpl$3
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = switchDeferredImpl.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        }))));
    }

    public static final EventsInit mergeLeft(Events... eventsArr) {
        final Iterable asIterable = ArraysKt___ArraysKt.asIterable(eventsArr);
        final MuxLifecycle switchDeferredImpl = MuxDeferredKt.switchDeferredImpl(new Function1() { // from class: com.android.systemui.kairos.MergeKt$mergeLeft$$inlined$mergeNodesLeft$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                Iterable iterable = asIterable;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add((EventsImpl) EventsKt.getInit((Events) it.next()).connect(evalScope));
                }
                return MuxDeferredKt.asIterableWithIndex(arrayList);
            }
        }, new Function1() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodesLeft$switchNode$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return PullNodesKt.neverImpl;
            }
        }, new MutableArrayMapK.Factory());
        final MuxDeferredKt$mergeNodesLeft$merged$2 muxDeferredKt$mergeNodesLeft$merged$2 = new Function3() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodesLeft$merged$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                return ((PullNode) CollectionsKt___CollectionsKt.first(((ArrayMapK) ((MapK) obj2)).getValues())).getPushEvent((EvalScope) obj);
            }
        };
        return new EventsInit(new Init(null, new InitKt$constInit$1(PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$mergeNodesLeft$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = switchDeferredImpl.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        }))));
    }
}
