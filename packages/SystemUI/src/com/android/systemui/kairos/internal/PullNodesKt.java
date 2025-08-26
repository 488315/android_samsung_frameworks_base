package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public abstract class PullNodesKt {
    public static final PullNodesKt$special$$inlined$EventsImplCheap$1 neverImpl = new EventsImpl() { // from class: com.android.systemui.kairos.internal.PullNodesKt$special$$inlined$EventsImplCheap$1
        @Override // com.android.systemui.kairos.internal.EventsImpl
        public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
            return null;
        }
    };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.kairos.internal.PullNodesKt$cached$$inlined$EventsImplCheap$1] */
    public static final PullNodesKt$cached$$inlined$EventsImplCheap$1 cached(final EventsImpl eventsImpl) {
        final TransactionCache transactionCache = new TransactionCache();
        return new EventsImpl() { // from class: com.android.systemui.kairos.internal.PullNodesKt$cached$$inlined$EventsImplCheap$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = eventsImpl.activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new CachedNode(transactionCache, nodeConnection.directUpstream), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
    }
}
