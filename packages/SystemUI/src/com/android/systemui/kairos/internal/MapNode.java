package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
