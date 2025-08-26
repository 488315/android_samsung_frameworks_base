package com.android.systemui.kairos.internal;

import kotlin.collections.builders.SetBuilder;

/* loaded from: classes2.dex */
public interface SchedulableNode {
    void adjustDirectUpstream(SchedulerImpl schedulerImpl, int i, int i2);

    void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2);

    void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder);

    void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder);

    void removeDirectUpstream(SchedulerImpl schedulerImpl, int i);

    void removeIndirectUpstream(SchedulerImpl schedulerImpl, int i, SetBuilder setBuilder);

    void schedule(EvalScope evalScope);
}
