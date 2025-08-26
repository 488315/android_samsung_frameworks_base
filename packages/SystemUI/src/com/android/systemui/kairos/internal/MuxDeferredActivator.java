package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MutableMapK;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class MuxDeferredActivator implements MuxActivator {
    public final Function1 getPatches;
    public final Function1 getStorage;
    public final String name;
    public final MutableMapK.Factory storeFactory;

    public MuxDeferredActivator(String str, Function1 function1, MutableMapK.Factory factory, Function1 function12) {
        this.name = str;
        this.getStorage = function1;
        this.storeFactory = factory;
        this.getPatches = function12;
    }

    @Override // com.android.systemui.kairos.internal.MuxActivator
    public final Pair activate(final EvalScope evalScope, MuxLifecycle muxLifecycle) {
        String str = this.name;
        MutableMapK.Factory factory = this.storeFactory;
        final MuxDeferredNode muxDeferredNode = new MuxDeferredNode(str, muxLifecycle, this, factory);
        MuxKt.initializeUpstream(muxDeferredNode, evalScope, this.getStorage, factory);
        MuxKt.initializeDepth(muxDeferredNode);
        DepthTracker depthTracker = muxDeferredNode.depthTracker;
        depthTracker.setIsIndirectRoot(true);
        depthTracker.reset();
        MutableMapK mutableMapK = muxDeferredNode.upstreamData;
        if (mutableMapK == null) {
            mutableMapK = null;
        }
        if (!mutableMapK.isEmpty()) {
            depthTracker.schedule(evalScope.getScheduler(), muxDeferredNode);
        }
        return new Pair(muxDeferredNode, new Function0() { // from class: com.android.systemui.kairos.internal.MuxDeferredActivator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MuxDeferredActivator muxDeferredActivator = this.f$0;
                EvalScope evalScope2 = evalScope;
                MuxDeferredNode muxDeferredNode2 = muxDeferredNode;
                ActivationResult activationResultActivate = ((EventsImpl) muxDeferredActivator.getPatches.mo781invoke(evalScope2)).activate(evalScope2, muxDeferredNode2.schedulable);
                if (activationResultActivate == null) {
                    muxDeferredNode2.depthTracker.setIsIndirectRoot(false);
                    return Unit.INSTANCE;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                boolean z = activationResultActivate.needsEval;
                muxDeferredNode2.patches = nodeConnection;
                if (!nodeConnection.schedulerUpstream.getDepthTracker().snapshotIsDirect && (muxDeferredNode2.depthTracker.setIsIndirectRoot(false) | muxDeferredNode2.depthTracker.addIndirectUpstream(nodeConnection.schedulerUpstream.getDepthTracker().snapshotIndirectDepth, null) | DepthTracker.updateIndirectRoots$default(muxDeferredNode2.depthTracker, CollectionsKt___CollectionsKt.toSet(nodeConnection.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, null, 6))) {
                    muxDeferredNode2.depthTracker.schedule(evalScope2.getScheduler(), muxDeferredNode2);
                }
                if (z) {
                    muxDeferredNode2.patchData = (Iterable) nodeConnection.directUpstream.getPushEvent(evalScope2);
                    evalScope2.scheduleMuxMover(muxDeferredNode2);
                }
                return Unit.INSTANCE;
            }
        });
    }
}
