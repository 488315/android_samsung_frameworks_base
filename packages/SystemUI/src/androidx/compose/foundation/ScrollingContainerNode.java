package androidx.compose.foundation;

import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollableDefaults;
import androidx.compose.foundation.gestures.ScrollableNode;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollingContainerNode extends DelegatingNode implements CompositionLocalConsumerModifierNode, ObserverModifierNode {
    public BringIntoViewSpec bringIntoViewSpec;
    public boolean enabled;
    public FlingBehavior flingBehavior;
    public MutableInteractionSource interactionSource;
    public OverscrollFactory localOverscrollFactory;
    public OverscrollEffect localOverscrollFactoryCreatedOverscrollEffect;
    public Orientation orientation;
    public DelegatableNode overscrollNode;
    public boolean reverseScrolling;
    public ScrollableNode scrollableNode;
    public boolean shouldReverseDirection;
    public ScrollableState state;
    public boolean useLocalOverscrollFactory;
    public OverscrollEffect userProvidedOverscrollEffect;

    public ScrollingContainerNode(ScrollableState scrollableState, Orientation orientation, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec, boolean z3, OverscrollEffect overscrollEffect) {
        this.state = scrollableState;
        this.orientation = orientation;
        this.enabled = z;
        this.reverseScrolling = z2;
        this.flingBehavior = flingBehavior;
        this.interactionSource = mutableInteractionSource;
        this.bringIntoViewSpec = bringIntoViewSpec;
        this.useLocalOverscrollFactory = z3;
        this.userProvidedOverscrollEffect = overscrollEffect;
    }

    public final void attachOverscrollNodeIfNeeded() {
        DelegatableNode delegatableNode = this.overscrollNode;
        if (delegatableNode != null) {
            if (((Modifier.Node) delegatableNode).node.isAttached) {
                return;
            }
            delegate(delegatableNode);
            return;
        }
        if (this.useLocalOverscrollFactory) {
            ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.foundation.ScrollingContainerNode$attachOverscrollNodeIfNeeded$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ScrollingContainerNode scrollingContainerNode = ScrollingContainerNode.this;
                    scrollingContainerNode.localOverscrollFactory = (OverscrollFactory) CompositionLocalConsumerModifierNodeKt.currentValueOf(scrollingContainerNode, OverscrollKt.LocalOverscrollFactory);
                    ScrollingContainerNode scrollingContainerNode2 = ScrollingContainerNode.this;
                    OverscrollFactory overscrollFactory = scrollingContainerNode2.localOverscrollFactory;
                    scrollingContainerNode2.localOverscrollFactoryCreatedOverscrollEffect = overscrollFactory != null ? overscrollFactory.createOverscrollEffect() : null;
                    return Unit.INSTANCE;
                }
            });
        }
        OverscrollEffect overscrollEffect = this.useLocalOverscrollFactory ? this.localOverscrollFactoryCreatedOverscrollEffect : this.userProvidedOverscrollEffect;
        if (overscrollEffect != null) {
            DelegatableNode node = overscrollEffect.getNode();
            if (((Modifier.Node) node).node.isAttached) {
                return;
            }
            delegate(node);
            this.overscrollNode = node;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.shouldReverseDirection = shouldReverseDirection();
        attachOverscrollNodeIfNeeded();
        if (this.scrollableNode == null) {
            ScrollableNode scrollableNode = new ScrollableNode(this.state, this.useLocalOverscrollFactory ? this.localOverscrollFactoryCreatedOverscrollEffect : this.userProvidedOverscrollEffect, this.flingBehavior, this.orientation, this.enabled, this.shouldReverseDirection, this.interactionSource, this.bringIntoViewSpec);
            delegate(scrollableNode);
            this.scrollableNode = scrollableNode;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        DelegatableNode delegatableNode = this.overscrollNode;
        if (delegatableNode != null) {
            undelegate(delegatableNode);
        }
    }

    @Override // androidx.compose.ui.node.DelegatableNode
    public final void onLayoutDirectionChange() {
        boolean shouldReverseDirection = shouldReverseDirection();
        if (this.shouldReverseDirection != shouldReverseDirection) {
            this.shouldReverseDirection = shouldReverseDirection;
            ScrollableState scrollableState = this.state;
            Orientation orientation = this.orientation;
            boolean z = this.useLocalOverscrollFactory;
            update(scrollableState, orientation, z, this.enabled, this.flingBehavior, this.interactionSource, this.bringIntoViewSpec, this.reverseScrolling, z ? this.localOverscrollFactoryCreatedOverscrollEffect : this.userProvidedOverscrollEffect);
        }
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        OverscrollFactory overscrollFactory = (OverscrollFactory) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, OverscrollKt.LocalOverscrollFactory);
        if (Intrinsics.areEqual(overscrollFactory, this.localOverscrollFactory)) {
            return;
        }
        this.localOverscrollFactory = overscrollFactory;
        this.localOverscrollFactoryCreatedOverscrollEffect = null;
        DelegatableNode delegatableNode = this.overscrollNode;
        if (delegatableNode != null) {
            undelegate(delegatableNode);
        }
        this.overscrollNode = null;
        attachOverscrollNodeIfNeeded();
        ScrollableNode scrollableNode = this.scrollableNode;
        if (scrollableNode != null) {
            scrollableNode.update(this.state, this.orientation, this.useLocalOverscrollFactory ? this.localOverscrollFactoryCreatedOverscrollEffect : this.userProvidedOverscrollEffect, this.enabled, this.shouldReverseDirection, this.flingBehavior, this.interactionSource, this.bringIntoViewSpec);
        }
    }

    public final boolean shouldReverseDirection() {
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        if (this.isAttached) {
            layoutDirection = DelegatableNodeKt.requireLayoutNode(this).layoutDirection;
        }
        ScrollableDefaults scrollableDefaults = ScrollableDefaults.INSTANCE;
        Orientation orientation = this.orientation;
        boolean z = this.reverseScrolling;
        scrollableDefaults.getClass();
        return (layoutDirection != LayoutDirection.Rtl || orientation == Orientation.Vertical) ? !z : z;
    }

    public final void update(ScrollableState scrollableState, Orientation orientation, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec, boolean z3, OverscrollEffect overscrollEffect) {
        boolean z4;
        this.state = scrollableState;
        this.orientation = orientation;
        boolean z5 = true;
        if (this.useLocalOverscrollFactory != z) {
            this.useLocalOverscrollFactory = z;
            z4 = true;
        } else {
            z4 = false;
        }
        if (Intrinsics.areEqual(this.userProvidedOverscrollEffect, overscrollEffect)) {
            z5 = false;
        } else {
            this.userProvidedOverscrollEffect = overscrollEffect;
        }
        if (z4 || (z5 && !z)) {
            DelegatableNode delegatableNode = this.overscrollNode;
            if (delegatableNode != null) {
                undelegate(delegatableNode);
            }
            this.overscrollNode = null;
            attachOverscrollNodeIfNeeded();
        }
        this.enabled = z2;
        this.reverseScrolling = z3;
        this.flingBehavior = flingBehavior;
        this.interactionSource = mutableInteractionSource;
        this.bringIntoViewSpec = bringIntoViewSpec;
        boolean shouldReverseDirection = shouldReverseDirection();
        this.shouldReverseDirection = shouldReverseDirection;
        ScrollableNode scrollableNode = this.scrollableNode;
        if (scrollableNode != null) {
            scrollableNode.update(scrollableState, orientation, this.useLocalOverscrollFactory ? this.localOverscrollFactoryCreatedOverscrollEffect : this.userProvidedOverscrollEffect, z2, shouldReverseDirection, flingBehavior, mutableInteractionSource, bringIntoViewSpec);
        }
    }
}
