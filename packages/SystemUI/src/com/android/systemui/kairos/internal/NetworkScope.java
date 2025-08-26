package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public interface NetworkScope {
    SchedulerImpl getCompactor();

    long getEpoch();

    Network getNetwork();

    Object getNetworkId();

    SchedulerImpl getScheduler();

    TransactionStore getTransactionStore();

    void schedule(StateSource stateSource);

    void scheduleDeactivation(Output output);

    void scheduleDeactivation(PushNode pushNode);

    void scheduleMuxMover(MuxDeferredNode muxDeferredNode);

    void scheduleOutput(Output output);
}
