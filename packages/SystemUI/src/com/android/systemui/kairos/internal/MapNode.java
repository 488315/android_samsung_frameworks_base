package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class MapNode implements PullNode {
    public final Function3 transform;
    public final PullNode upstream;

    public MapNode(PullNode pullNode, Function3 function3) {
        this.upstream = pullNode;
        this.transform = function3;
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final Object getPushEvent(EvalScope evalScope) {
        return this.transform.invoke(evalScope, this.upstream.getPushEvent(evalScope), 0);
    }
}
