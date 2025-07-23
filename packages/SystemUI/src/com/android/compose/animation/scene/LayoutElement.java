package com.android.compose.animation.scene;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LayoutElement extends ModifierNodeElement<LayoutNode> {
    public final SceneTransitionLayoutImpl layoutImpl;
    public final TransitionState transitionState;

    public LayoutElement(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState transitionState) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.transitionState = transitionState;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LayoutNode(this.layoutImpl, this.transitionState);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LayoutElement)) {
            return false;
        }
        LayoutElement layoutElement = (LayoutElement) obj;
        return Intrinsics.areEqual(this.layoutImpl, layoutElement.layoutImpl) && Intrinsics.areEqual(this.transitionState, layoutElement.transitionState);
    }

    public final int hashCode() {
        return this.transitionState.hashCode() + (this.layoutImpl.hashCode() * 31);
    }

    public final String toString() {
        return "LayoutElement(layoutImpl=" + this.layoutImpl + ", transitionState=" + this.transitionState + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        LayoutNode layoutNode = (LayoutNode) node;
        layoutNode.layoutImpl = this.layoutImpl;
        layoutNode.transitionState = this.transitionState;
    }
}
