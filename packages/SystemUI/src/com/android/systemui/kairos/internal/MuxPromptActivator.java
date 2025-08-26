package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.MuxPromptNode;
import com.android.systemui.kairos.internal.MuxPromptNode.PatchNode;
import com.android.systemui.kairos.internal.store.MutableMapK;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class MuxPromptActivator implements MuxActivator {
    public final Function1 getPatches;
    public final Function1 getStorage;
    public final String name;
    public final MutableMapK.Factory storeFactory;

    public MuxPromptActivator(String str, Function1 function1, MutableMapK.Factory factory, Function1 function12) {
        this.name = str;
        this.getStorage = function1;
        this.storeFactory = factory;
        this.getPatches = function12;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0095  */
    @Override // com.android.systemui.kairos.internal.MuxActivator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair activate(EvalScope evalScope, MuxLifecycle muxLifecycle) {
        MuxPromptNode muxPromptNode = new MuxPromptNode(this.name, muxLifecycle, this, this.storeFactory);
        MuxKt.initializeUpstream(muxPromptNode, evalScope, this.getStorage, this.storeFactory);
        MuxPromptNode.PatchNode patchNode = muxPromptNode.new PatchNode();
        ActivationResult activationResultActivate = ((EventsImpl) this.getPatches.mo781invoke(evalScope)).activate(evalScope, patchNode.schedulable);
        if (activationResultActivate != null) {
            NodeConnection nodeConnection = activationResultActivate.connection;
            boolean z = activationResultActivate.needsEval;
            patchNode.upstream = nodeConnection;
            muxPromptNode.patches = patchNode;
            if (z) {
                muxPromptNode.patchData = (Iterable) nodeConnection.directUpstream.getPushEvent(evalScope);
            }
        }
        MuxKt.initializeDepth(muxPromptNode);
        MuxPromptNode.PatchNode patchNode2 = muxPromptNode.patches;
        if (patchNode2 != null) {
            NodeConnection nodeConnection2 = patchNode2.upstream;
            if (nodeConnection2 == null) {
                nodeConnection2 = null;
            }
            if (nodeConnection2 != null) {
                if (nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                    muxPromptNode.depthTracker.addDirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotDirectDepth, null);
                } else {
                    muxPromptNode.depthTracker.addIndirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIndirectDepth, null);
                    DepthTracker.updateIndirectRoots$default(muxPromptNode.depthTracker, CollectionsKt___CollectionsKt.toSet(nodeConnection2.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, null, 2);
                }
            }
        }
        muxPromptNode.depthTracker.reset();
        if (muxPromptNode.patchData != null) {
            muxPromptNode.depthTracker.schedule(evalScope.getScheduler(), muxPromptNode);
        } else {
            MutableMapK mutableMapK = muxPromptNode.upstreamData;
            if (mutableMapK == null) {
                mutableMapK = null;
            }
            if (!mutableMapK.isEmpty()) {
            }
        }
        if (muxPromptNode.patches == null) {
            MutableMapK mutableMapK2 = muxPromptNode.switchedIn;
            if (mutableMapK2 == null) {
                mutableMapK2 = null;
            }
            if (mutableMapK2.isEmpty()) {
                return null;
            }
        }
        return new Pair(muxPromptNode, null);
    }
}
