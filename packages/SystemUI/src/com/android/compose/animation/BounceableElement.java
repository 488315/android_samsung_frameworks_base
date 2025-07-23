package com.android.compose.animation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BounceableElement extends ModifierNodeElement<BounceableNode> {
    public final boolean bounceEnd;
    public final Bounceable bounceable;
    public final Bounceable nextBounceable;
    public final Orientation orientation;
    public final Bounceable previousBounceable;

    public BounceableElement(Bounceable bounceable, Bounceable bounceable2, Bounceable bounceable3, Orientation orientation, boolean z) {
        this.bounceable = bounceable;
        this.previousBounceable = bounceable2;
        this.nextBounceable = bounceable3;
        this.orientation = orientation;
        this.bounceEnd = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BounceableNode(this.bounceable, this.previousBounceable, this.nextBounceable, this.orientation, this.bounceEnd);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BounceableElement)) {
            return false;
        }
        BounceableElement bounceableElement = (BounceableElement) obj;
        return Intrinsics.areEqual(this.bounceable, bounceableElement.bounceable) && Intrinsics.areEqual(this.previousBounceable, bounceableElement.previousBounceable) && Intrinsics.areEqual(this.nextBounceable, bounceableElement.nextBounceable) && this.orientation == bounceableElement.orientation && this.bounceEnd == bounceableElement.bounceEnd;
    }

    public final int hashCode() {
        int hashCode = this.bounceable.hashCode() * 31;
        Bounceable bounceable = this.previousBounceable;
        int hashCode2 = (hashCode + (bounceable == null ? 0 : bounceable.hashCode())) * 31;
        Bounceable bounceable2 = this.nextBounceable;
        return Boolean.hashCode(this.bounceEnd) + ((this.orientation.hashCode() + ((hashCode2 + (bounceable2 != null ? bounceable2.hashCode() : 0)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BounceableElement(bounceable=");
        sb.append(this.bounceable);
        sb.append(", previousBounceable=");
        sb.append(this.previousBounceable);
        sb.append(", nextBounceable=");
        sb.append(this.nextBounceable);
        sb.append(", orientation=");
        sb.append(this.orientation);
        sb.append(", bounceEnd=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.bounceEnd, ")");
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BounceableNode bounceableNode = (BounceableNode) node;
        bounceableNode.bounceable = this.bounceable;
        bounceableNode.previousBounceable = this.previousBounceable;
        bounceableNode.nextBounceable = this.nextBounceable;
        bounceableNode.orientation = this.orientation;
        bounceableNode.bounceEnd = this.bounceEnd;
    }
}
