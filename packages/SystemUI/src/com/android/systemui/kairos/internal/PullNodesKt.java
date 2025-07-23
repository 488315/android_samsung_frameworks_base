package com.android.systemui.kairos.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                ActivationResult activate = EventsImpl.this.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new CachedNode(transactionCache, nodeConnection.directUpstream), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        };
    }
}
