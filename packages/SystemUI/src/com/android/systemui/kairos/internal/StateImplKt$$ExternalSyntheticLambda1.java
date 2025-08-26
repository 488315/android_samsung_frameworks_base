package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda8;
import com.android.systemui.kairos.internal.store.MutableArrayMapK;
import com.android.systemui.kairos.util.These;
import java.util.Arrays;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateImplKt$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StateImplKt$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EvalScope evalScope = (EvalScope) obj;
        switch (this.$r8$classId) {
            case 0:
                final StateImpl stateImpl = (StateImpl) obj2;
                ((Integer) obj3).getClass();
                final MuxDeferredKt$mergeNodes$storage$1 muxDeferredKt$mergeNodes$storage$1 = MuxDeferredKt$mergeNodes$storage$1.INSTANCE;
                final StateImplKt$flattenStateImpl$$inlined$mapImpl$1 stateImplKt$flattenStateImpl$$inlined$mapImpl$1 = (StateImplKt$flattenStateImpl$$inlined$mapImpl$1) this.f$0;
                EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$lambda$15$$inlined$mergeNodes$default$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                        ActivationResult activationResultActivate = stateImplKt$flattenStateImpl$$inlined$mapImpl$1.activate(evalScope2, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxDeferredKt$mergeNodes$storage$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                };
                final MuxDeferredKt$mergeNodes$storage$2 muxDeferredKt$mergeNodes$storage$2 = MuxDeferredKt$mergeNodes$storage$2.INSTANCE;
                return PullNodesKt.cached(new MuxDeferredKt$mergeNodes$$inlined$mapImpl$2(new Function3() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$lambda$15$$inlined$mergeNodes$default$3
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                        These these = (These) obj5;
                        ((Number) obj6).intValue();
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
                        both.getClass();
                        return both.second;
                    }
                }, PullNodesKt.cached(new MuxDeferredKt$mergeNodes$$inlined$mapImpl$1(MuxDeferredKt$mergeNodes$merged$4.INSTANCE, MuxDeferredKt.switchDeferredImpl(new MuxDeferredKt$mergeNodes$switchNode$1(MuxDeferredKt.asIterableWithIndex(Arrays.asList(eventsImpl, new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$lambda$15$$inlined$mergeNodes$default$2
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope2, Schedulable schedulable) {
                        ActivationResult activationResultActivate = stateImpl.changes.activate(evalScope2, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxDeferredKt$mergeNodes$storage$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                }))), MuxDeferredKt$mergeNodes$switchNode$2.INSTANCE, new MutableArrayMapK.Factory())))));
            case 1:
                ((Integer) obj3).getClass();
                return ((StateKt$$ExternalSyntheticLambda8) this.f$0).invoke(evalScope, obj2);
            default:
                ((Integer) obj3).intValue();
                return ((Function2) this.f$0).invoke(evalScope, obj2);
        }
    }
}
