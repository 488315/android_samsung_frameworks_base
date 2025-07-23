package com.android.compose.animation.scene;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.ElementNode;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ElementModifier extends ModifierNodeElement<ElementNode> {
    public final Content content;
    public final List currentTransitionStates;
    public final ElementKey key;
    public final SceneTransitionLayoutImpl layoutImpl;

    public ElementModifier(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, List<? extends List<? extends TransitionState>> list, Content content, ElementKey elementKey) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.currentTransitionStates = list;
        this.content = content;
        this.key = elementKey;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ElementNode(this.layoutImpl, this.currentTransitionStates, this.content, this.key);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ElementModifier)) {
            return false;
        }
        ElementModifier elementModifier = (ElementModifier) obj;
        return Intrinsics.areEqual(this.layoutImpl, elementModifier.layoutImpl) && Intrinsics.areEqual(this.currentTransitionStates, elementModifier.currentTransitionStates) && Intrinsics.areEqual(this.content, elementModifier.content) && Intrinsics.areEqual(this.key, elementModifier.key);
    }

    public final int hashCode() {
        return this.key.identity.hashCode() + ((this.content.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.currentTransitionStates, this.layoutImpl.hashCode() * 31, 31)) * 31);
    }

    public final String toString() {
        return "ElementModifier(layoutImpl=" + this.layoutImpl + ", currentTransitionStates=" + this.currentTransitionStates + ", content=" + this.content + ", key=" + this.key + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ElementNode elementNode = (ElementNode) node;
        List list = this.currentTransitionStates;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = elementNode.layoutImpl;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl2 = this.layoutImpl;
        if (Intrinsics.areEqual(sceneTransitionLayoutImpl2, sceneTransitionLayoutImpl)) {
            if (Intrinsics.areEqual(this.content, elementNode.content)) {
                elementNode.currentTransitionStates = list;
                elementNode.getStateInContent().nodes.remove(elementNode);
                Element element = elementNode._element;
                element.getClass();
                Element.State stateInContent = elementNode.getStateInContent();
                elementNode.key = this.key;
                elementNode.updateElementAndContentValues();
                elementNode.getStateInContent().nodes.add(elementNode);
                BuildersKt.launch$default(elementNode.getCoroutineScope(), null, null, new ElementNode$addNodeToContentState$1(elementNode, null), 3);
                ElementNode.Companion.access$maybePruneMaps(ElementNode.Companion, sceneTransitionLayoutImpl2, element, stateInContent);
                return;
            }
        }
        throw new IllegalStateException("Check failed.");
    }
}
