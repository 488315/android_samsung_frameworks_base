package androidx.compose.animation;

import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.Transition;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class EnterExitTransitionElement extends ModifierNodeElement<EnterExitTransitionModifierNode> {
    public final EnterTransition enter;
    public final ExitTransition exit;
    public final GraphicsLayerBlockForEnterExit graphicsLayerBlock;
    public final Function0 isEnabled;
    public final Transition.DeferredAnimation offsetAnimation;
    public final Transition.DeferredAnimation sizeAnimation;
    public final Transition.DeferredAnimation slideAnimation;
    public final Transition transition;

    public EnterExitTransitionElement(Transition<EnterExitState> transition, Transition.DeferredAnimation<IntSize, AnimationVector2D> deferredAnimation, Transition.DeferredAnimation<IntOffset, AnimationVector2D> deferredAnimation2, Transition.DeferredAnimation<IntOffset, AnimationVector2D> deferredAnimation3, EnterTransition enterTransition, ExitTransition exitTransition, Function0 function0, GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit) {
        this.transition = transition;
        this.sizeAnimation = deferredAnimation;
        this.offsetAnimation = deferredAnimation2;
        this.slideAnimation = deferredAnimation3;
        this.enter = enterTransition;
        this.exit = exitTransition;
        this.isEnabled = function0;
        this.graphicsLayerBlock = graphicsLayerBlockForEnterExit;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        EnterTransition enterTransition = this.enter;
        ExitTransition exitTransition = this.exit;
        return new EnterExitTransitionModifierNode(this.transition, this.sizeAnimation, this.offsetAnimation, this.slideAnimation, enterTransition, exitTransition, this.isEnabled, this.graphicsLayerBlock);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnterExitTransitionElement)) {
            return false;
        }
        EnterExitTransitionElement enterExitTransitionElement = (EnterExitTransitionElement) obj;
        return Intrinsics.areEqual(this.transition, enterExitTransitionElement.transition) && Intrinsics.areEqual(this.sizeAnimation, enterExitTransitionElement.sizeAnimation) && Intrinsics.areEqual(this.offsetAnimation, enterExitTransitionElement.offsetAnimation) && Intrinsics.areEqual(this.slideAnimation, enterExitTransitionElement.slideAnimation) && Intrinsics.areEqual(this.enter, enterExitTransitionElement.enter) && Intrinsics.areEqual(this.exit, enterExitTransitionElement.exit) && Intrinsics.areEqual(this.isEnabled, enterExitTransitionElement.isEnabled) && Intrinsics.areEqual(this.graphicsLayerBlock, enterExitTransitionElement.graphicsLayerBlock);
    }

    public final int hashCode() {
        int iHashCode = this.transition.hashCode() * 31;
        Transition.DeferredAnimation deferredAnimation = this.sizeAnimation;
        int iHashCode2 = (iHashCode + (deferredAnimation == null ? 0 : deferredAnimation.hashCode())) * 31;
        Transition.DeferredAnimation deferredAnimation2 = this.offsetAnimation;
        int iHashCode3 = (iHashCode2 + (deferredAnimation2 == null ? 0 : deferredAnimation2.hashCode())) * 31;
        Transition.DeferredAnimation deferredAnimation3 = this.slideAnimation;
        return this.graphicsLayerBlock.hashCode() + ((this.isEnabled.hashCode() + ((this.exit.hashCode() + ((this.enter.hashCode() + ((iHashCode3 + (deferredAnimation3 != null ? deferredAnimation3.hashCode() : 0)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "EnterExitTransitionElement(transition=" + this.transition + ", sizeAnimation=" + this.sizeAnimation + ", offsetAnimation=" + this.offsetAnimation + ", slideAnimation=" + this.slideAnimation + ", enter=" + this.enter + ", exit=" + this.exit + ", isEnabled=" + this.isEnabled + ", graphicsLayerBlock=" + this.graphicsLayerBlock + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        EnterExitTransitionModifierNode enterExitTransitionModifierNode = (EnterExitTransitionModifierNode) node;
        enterExitTransitionModifierNode.transition = this.transition;
        enterExitTransitionModifierNode.sizeAnimation = this.sizeAnimation;
        enterExitTransitionModifierNode.offsetAnimation = this.offsetAnimation;
        enterExitTransitionModifierNode.slideAnimation = this.slideAnimation;
        enterExitTransitionModifierNode.enter = this.enter;
        enterExitTransitionModifierNode.exit = this.exit;
        enterExitTransitionModifierNode.isEnabled = this.isEnabled;
        enterExitTransitionModifierNode.graphicsLayerBlock = this.graphicsLayerBlock;
    }
}
