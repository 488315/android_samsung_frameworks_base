package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.builders.SetBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DownstreamSet {
    public final HashSet outputs = new HashSet();
    public final List stateWriters = new ArrayList();
    public final HashSet muxMovers = new HashSet();
    public final HashSet nodes = new HashSet();

    public final void add(Schedulable schedulable) {
        if (schedulable instanceof Schedulable.S) {
            ((ArrayList) this.stateWriters).add(((Schedulable.S) schedulable).state);
        } else if (schedulable instanceof Schedulable.M) {
            this.muxMovers.add(((Schedulable.M) schedulable).muxMover);
        } else if (schedulable instanceof Schedulable.N) {
            this.nodes.add(((Schedulable.N) schedulable).node);
        } else {
            if (!(schedulable instanceof Schedulable.O)) {
                throw new NoWhenBranchMatchedException();
            }
            this.outputs.add(((Schedulable.O) schedulable).output);
        }
    }

    public final void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2) {
        Iterator it = this.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).adjustIndirectUpstream(schedulerImpl, i, i2, setBuilder, setBuilder2);
        }
        Iterator it2 = this.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            DepthTracker depthTracker = muxDeferredNode.depthTracker;
            if (depthTracker.updateIndirectRoots(setBuilder2, setBuilder, muxDeferredNode) | depthTracker.addIndirectUpstream(i2, Integer.valueOf(i))) {
                depthTracker.schedule(schedulerImpl, muxDeferredNode);
            }
        }
    }

    public final void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        Iterator it = this.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).moveDirectUpstreamToIndirect(i, i2, schedulerImpl, setBuilder);
        }
        Iterator it2 = this.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            DepthTracker depthTracker = muxDeferredNode.depthTracker;
            if (depthTracker.setIsIndirectRoot(false) | DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, setBuilder, null, muxDeferredNode, 2) | depthTracker.addIndirectUpstream(i2, null)) {
                depthTracker.schedule(schedulerImpl, muxDeferredNode);
            }
        }
    }

    public final void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        Iterator it = this.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).moveIndirectUpstreamToDirect(i, i2, schedulerImpl, setBuilder);
        }
        Iterator it2 = this.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            boolean updateIndirectRoots$default = DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, null, setBuilder, null, 5);
            DepthTracker depthTracker = muxDeferredNode.depthTracker;
            if (updateIndirectRoots$default | depthTracker.removeIndirectUpstream(i) | depthTracker.setIsIndirectRoot(true)) {
                depthTracker.schedule(schedulerImpl, muxDeferredNode);
            }
        }
    }

    public final void remove(Schedulable schedulable) {
        if (schedulable instanceof Schedulable.S) {
            throw new IllegalStateException("WTF: latches are never removed");
        }
        if (schedulable instanceof Schedulable.M) {
            this.muxMovers.remove(((Schedulable.M) schedulable).muxMover);
        } else if (schedulable instanceof Schedulable.N) {
            this.nodes.remove(((Schedulable.N) schedulable).node);
        } else {
            if (!(schedulable instanceof Schedulable.O)) {
                throw new NoWhenBranchMatchedException();
            }
            this.outputs.remove(((Schedulable.O) schedulable).output);
        }
    }

    public final void removeDirectUpstream(SchedulerImpl schedulerImpl, int i) {
        Iterator it = this.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).removeDirectUpstream(schedulerImpl, i);
        }
        Iterator it2 = this.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            DepthTracker depthTracker = muxDeferredNode.depthTracker;
            if (depthTracker.setIsIndirectRoot(false) | depthTracker.removeIndirectUpstream(0)) {
                depthTracker.schedule(schedulerImpl, muxDeferredNode);
            }
            muxDeferredNode.patches = null;
        }
        Iterator it3 = this.outputs.iterator();
        while (it3.hasNext()) {
            ((Output) it3.next()).onDeath.invoke();
        }
    }

    public final void removeIndirectUpstream(SchedulerImpl schedulerImpl, int i, SetBuilder setBuilder) {
        Iterator it = this.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).removeIndirectUpstream(schedulerImpl, i, setBuilder);
        }
        Iterator it2 = this.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            boolean updateIndirectRoots$default = DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, null, setBuilder, null, 5);
            DepthTracker depthTracker = muxDeferredNode.depthTracker;
            if (updateIndirectRoots$default | depthTracker.removeIndirectUpstream(i)) {
                depthTracker.schedule(schedulerImpl, muxDeferredNode);
            }
            muxDeferredNode.patches = null;
        }
        Iterator it3 = this.outputs.iterator();
        while (it3.hasNext()) {
            ((Output) it3.next()).onDeath.invoke();
        }
    }
}
