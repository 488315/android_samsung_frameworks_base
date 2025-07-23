package com.android.systemui.kairos.internal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NodeConnection {
    public final PullNode directUpstream;
    public final PushNode schedulerUpstream;

    public NodeConnection(PullNode pullNode, PushNode pushNode) {
        this.directUpstream = pullNode;
        this.schedulerUpstream = pushNode;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NodeConnection)) {
            return false;
        }
        NodeConnection nodeConnection = (NodeConnection) obj;
        return Intrinsics.areEqual(this.directUpstream, nodeConnection.directUpstream) && Intrinsics.areEqual(this.schedulerUpstream, nodeConnection.schedulerUpstream);
    }

    public final int hashCode() {
        return this.schedulerUpstream.hashCode() + (this.directUpstream.hashCode() * 31);
    }

    public final String toString() {
        return "NodeConnection(directUpstream=" + this.directUpstream + ", schedulerUpstream=" + this.schedulerUpstream + ")";
    }
}
