package com.android.compose.ui.graphics;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class DrawInContainerElement extends ModifierNodeElement<DrawInContainerNode> {
    public final Function2 clipPath;
    public final Function0 enabled;
    public final ContainerState state;
    public final float zIndex;

    public DrawInContainerElement(ContainerState containerState, Function0 function0, float f, Function2 function2) {
        this.state = containerState;
        this.enabled = function0;
        this.zIndex = f;
        this.clipPath = function2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new DrawInContainerNode(this.state, this.enabled, this.zIndex, this.clipPath);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DrawInContainerElement)) {
            return false;
        }
        DrawInContainerElement drawInContainerElement = (DrawInContainerElement) obj;
        return Intrinsics.areEqual(this.state, drawInContainerElement.state) && Intrinsics.areEqual(this.enabled, drawInContainerElement.enabled) && Float.compare(this.zIndex, drawInContainerElement.zIndex) == 0 && Intrinsics.areEqual(this.clipPath, drawInContainerElement.clipPath);
    }

    public final int hashCode() {
        return this.clipPath.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.zIndex, (this.enabled.hashCode() + (this.state.hashCode() * 31)) * 31, 31);
    }

    public final String toString() {
        return "DrawInContainerElement(state=" + this.state + ", enabled=" + this.enabled + ", zIndex=" + this.zIndex + ", clipPath=" + this.clipPath + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        DrawInContainerNode drawInContainerNode = (DrawInContainerNode) node;
        drawInContainerNode.state = this.state;
        drawInContainerNode.enabled = this.enabled;
        ((SnapshotMutableFloatStateImpl) drawInContainerNode.zIndex$delegate).setFloatValue(this.zIndex);
        drawInContainerNode.clipPath = this.clipPath;
    }
}
