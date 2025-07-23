package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.MuxNode;
import com.android.systemui.kairos.internal.MuxNode.BranchNode;
import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Triple;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MuxKt {
    public static final void initializeDepth(MuxNode muxNode) {
        MutableMapK mutableMapK = muxNode.switchedIn;
        if (mutableMapK == null) {
            mutableMapK = null;
        }
        Iterator it = mutableMapK.entrySet().iterator();
        while (it.hasNext()) {
            NodeConnection nodeConnection = ((MuxNode.BranchNode) ((Map.Entry) it.next()).getValue()).upstream;
            if (nodeConnection == null) {
                nodeConnection = null;
            }
            if (nodeConnection.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                muxNode.depthTracker.addDirectUpstream(nodeConnection.schedulerUpstream.getDepthTracker().snapshotDirectDepth, null);
            } else {
                muxNode.depthTracker.addIndirectUpstream(nodeConnection.schedulerUpstream.getDepthTracker().snapshotIndirectDepth, null);
                DepthTracker.updateIndirectRoots$default(muxNode.depthTracker, CollectionsKt___CollectionsKt.toSet(nodeConnection.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, null, 2);
            }
        }
    }

    public static final void initializeUpstream(MuxNode muxNode, EvalScope evalScope, Function1 function1, MutableMapK.Factory factory) {
        Iterable iterable = (Iterable) function1.mo779invoke(evalScope);
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        Iterator it = iterable.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            EventsImpl eventsImpl = (EventsImpl) entry.getValue();
            MuxNode.BranchNode branchNode = muxNode.new BranchNode(key);
            ActivationResult activate = eventsImpl.activate(evalScope, branchNode.schedulable);
            if (activate != null) {
                NodeConnection nodeConnection = activate.connection;
                branchNode.upstream = nodeConnection;
                r2 = new Triple(key, branchNode, activate.needsEval ? nodeConnection.directUpstream : null);
            }
            createListBuilder.add(r2);
        }
        ListBuilder build = createListBuilder.build();
        muxNode.switchedIn = factory.create(Integer.valueOf(build.getSize()));
        muxNode.upstreamData = factory.create(Integer.valueOf(build.getSize()));
        ListIterator listIterator = build.listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                return;
            }
            Triple triple = (Triple) itr.next();
            if (triple != null) {
                Object component1 = triple.component1();
                MuxNode.BranchNode branchNode2 = (MuxNode.BranchNode) triple.component2();
                PullNode pullNode = (PullNode) triple.component3();
                MutableMapK mutableMapK = muxNode.switchedIn;
                if (mutableMapK == null) {
                    mutableMapK = null;
                }
                mutableMapK.put(component1, branchNode2);
                if (pullNode != null) {
                    MutableMapK mutableMapK2 = muxNode.upstreamData;
                    if (mutableMapK2 == null) {
                        mutableMapK2 = null;
                    }
                    mutableMapK2.put(component1, pullNode);
                }
            }
        }
    }
}
