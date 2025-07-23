package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MutableMapK;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0093, code lost:
    
        if (r3.isEmpty() == false) goto L26;
     */
    @Override // com.android.systemui.kairos.internal.MuxActivator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.Pair activate(com.android.systemui.kairos.internal.EvalScope r4, com.android.systemui.kairos.internal.MuxLifecycle r5) {
        /*
            r3 = this;
            com.android.systemui.kairos.internal.MuxPromptNode r0 = new com.android.systemui.kairos.internal.MuxPromptNode
            java.lang.String r1 = r3.name
            com.android.systemui.kairos.internal.store.MutableMapK$Factory r2 = r3.storeFactory
            r0.<init>(r1, r5, r3, r2)
            kotlin.jvm.functions.Function1 r5 = r3.getStorage
            com.android.systemui.kairos.internal.store.MutableMapK$Factory r1 = r3.storeFactory
            com.android.systemui.kairos.internal.MuxKt.initializeUpstream(r0, r4, r5, r1)
            com.android.systemui.kairos.internal.MuxPromptNode$PatchNode r5 = new com.android.systemui.kairos.internal.MuxPromptNode$PatchNode
            r5.<init>()
            kotlin.jvm.functions.Function1 r3 = r3.getPatches
            java.lang.Object r3 = r3.mo779invoke(r4)
            com.android.systemui.kairos.internal.EventsImpl r3 = (com.android.systemui.kairos.internal.EventsImpl) r3
            com.android.systemui.kairos.internal.Schedulable$N r1 = r5.schedulable
            com.android.systemui.kairos.internal.ActivationResult r3 = r3.activate(r4, r1)
            if (r3 == 0) goto L39
            com.android.systemui.kairos.internal.NodeConnection r1 = r3.connection
            boolean r3 = r3.needsEval
            r5.upstream = r1
            r0.patches = r5
            if (r3 == 0) goto L39
            com.android.systemui.kairos.internal.PullNode r3 = r1.directUpstream
            java.lang.Object r3 = r3.getPushEvent(r4)
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            r0.patchData = r3
        L39:
            com.android.systemui.kairos.internal.MuxKt.initializeDepth(r0)
            com.android.systemui.kairos.internal.MuxPromptNode$PatchNode r3 = r0.patches
            r5 = 0
            if (r3 == 0) goto L80
            com.android.systemui.kairos.internal.NodeConnection r3 = r3.upstream
            if (r3 == 0) goto L46
            goto L47
        L46:
            r3 = r5
        L47:
            if (r3 == 0) goto L80
            com.android.systemui.kairos.internal.PushNode r1 = r3.schedulerUpstream
            com.android.systemui.kairos.internal.DepthTracker r1 = r1.getDepthTracker()
            boolean r1 = r1.snapshotIsDirect
            if (r1 == 0) goto L61
            com.android.systemui.kairos.internal.DepthTracker r1 = r0.depthTracker
            com.android.systemui.kairos.internal.PushNode r3 = r3.schedulerUpstream
            com.android.systemui.kairos.internal.DepthTracker r3 = r3.getDepthTracker()
            int r3 = r3.snapshotDirectDepth
            r1.addDirectUpstream(r3, r5)
            goto L80
        L61:
            com.android.systemui.kairos.internal.DepthTracker r1 = r0.depthTracker
            com.android.systemui.kairos.internal.PushNode r2 = r3.schedulerUpstream
            com.android.systemui.kairos.internal.DepthTracker r2 = r2.getDepthTracker()
            int r2 = r2.snapshotIndirectDepth
            r1.addIndirectUpstream(r2, r5)
            com.android.systemui.kairos.internal.DepthTracker r1 = r0.depthTracker
            com.android.systemui.kairos.internal.PushNode r3 = r3.schedulerUpstream
            com.android.systemui.kairos.internal.DepthTracker r3 = r3.getDepthTracker()
            java.util.HashSet r3 = r3._snapshotIndirectRoots
            java.util.Set r3 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r3)
            r2 = 2
            com.android.systemui.kairos.internal.DepthTracker.updateIndirectRoots$default(r1, r3, r5, r5, r2)
        L80:
            com.android.systemui.kairos.internal.DepthTracker r3 = r0.depthTracker
            r3.reset()
            java.lang.Iterable r3 = r0.patchData
            if (r3 != 0) goto L95
            com.android.systemui.kairos.internal.store.MutableMapK r3 = r0.upstreamData
            if (r3 == 0) goto L8e
            goto L8f
        L8e:
            r3 = r5
        L8f:
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L9e
        L95:
            com.android.systemui.kairos.internal.DepthTracker r3 = r0.depthTracker
            com.android.systemui.kairos.internal.SchedulerImpl r4 = r4.getScheduler()
            r3.schedule(r4, r0)
        L9e:
            com.android.systemui.kairos.internal.MuxPromptNode$PatchNode r3 = r0.patches
            if (r3 != 0) goto Laf
            com.android.systemui.kairos.internal.store.MutableMapK r3 = r0.switchedIn
            if (r3 == 0) goto La7
            goto La8
        La7:
            r3 = r5
        La8:
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto Laf
            return r5
        Laf:
            kotlin.Pair r3 = new kotlin.Pair
            r3.<init>(r0, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.MuxPromptActivator.activate(com.android.systemui.kairos.internal.EvalScope, com.android.systemui.kairos.internal.MuxLifecycle):kotlin.Pair");
    }
}
