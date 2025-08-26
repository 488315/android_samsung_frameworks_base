package com.android.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class ContainerElement extends ModifierNodeElement<ContainerNode> {
    public final ContainerState state;

    public ContainerElement(ContainerState containerState) {
        this.state = containerState;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ContainerNode(this.state);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ContainerElement) && Intrinsics.areEqual(this.state, ((ContainerElement) obj).state);
    }

    public final int hashCode() {
        return this.state.hashCode();
    }

    public final String toString() {
        return "ContainerElement(state=" + this.state + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((ContainerNode) node).state = this.state;
    }
}
