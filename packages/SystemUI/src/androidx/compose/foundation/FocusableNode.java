package androidx.compose.foundation;

import androidx.compose.foundation.FocusedBoundsObserverNode;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusTargetModifierNode;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.PinnableContainer;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.GlobalPositionAwareModifierNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FocusableNode extends DelegatingNode implements SemanticsModifierNode, GlobalPositionAwareModifierNode, CompositionLocalConsumerModifierNode, ObserverModifierNode, TraversableNode {
    public static final TraverseKey TraverseKey = new TraverseKey(null);
    public final FocusTargetModifierNode focusTargetNode;
    public FocusInteraction$Focus focusedInteraction;
    public NodeCoordinator globalLayoutCoordinates;
    public MutableInteractionSource interactionSource;
    public final Function1 onFocusChange;
    public PinnableContainer.PinnedHandle pinnedHandle;
    public Function0 requestFocus;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class TraverseKey {
        public /* synthetic */ TraverseKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private TraverseKey() {
        }
    }

    public /* synthetic */ FocusableNode(MutableInteractionSource mutableInteractionSource, int i, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, i, function1);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        boolean isFocused = ((FocusTargetNode) this.focusTargetNode).getFocusState().isFocused();
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Focused;
        KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[4];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.valueOf(isFocused));
        if (this.requestFocus == null) {
            this.requestFocus = new Function0() { // from class: androidx.compose.foundation.FocusableNode$applySemantics$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(FocusTargetModifierNode.m377requestFocus3ESFkO8$default(FocusableNode.this.focusTargetNode));
                }
            };
        }
        Function0 function0 = this.requestFocus;
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.RequestFocus, new AccessibilityAction(null, function0));
    }

    public final void emitWithFallback(final MutableInteractionSource mutableInteractionSource, final Interaction interaction) {
        if (!this.isAttached) {
            mutableInteractionSource.tryEmit(interaction);
            return;
        }
        Job job = (Job) ((ContextScope) getCoroutineScope()).coroutineContext.get(Job.Key);
        BuildersKt.launch$default(getCoroutineScope(), null, null, new FocusableNode$emitWithFallback$1(mutableInteractionSource, interaction, job != null ? job.invokeOnCompletion(new Function1() { // from class: androidx.compose.foundation.FocusableNode$emitWithFallback$handler$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                MutableInteractionSource.this.tryEmit(interaction);
                return Unit.INSTANCE;
            }
        }) : null, null), 3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public final FocusedBoundsObserverNode getFocusedBoundsObserver() {
        TraversableNode traversableNode;
        NodeChain nodeChain;
        if (this.isAttached) {
            FocusedBoundsObserverNode.TraverseKey traverseKey = FocusedBoundsObserverNode.TraverseKey;
            if (!this.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = this.node.parent;
            LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
            loop0: while (true) {
                if (requireLayoutNode == null) {
                    traversableNode = null;
                    break;
                }
                if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 262144) != 0) {
                    while (node != null) {
                        if ((node.kindSet & 262144) != 0) {
                            ?? r5 = 0;
                            DelegatingNode delegatingNode = node;
                            while (delegatingNode != 0) {
                                if (delegatingNode instanceof TraversableNode) {
                                    traversableNode = (TraversableNode) delegatingNode;
                                    if (Intrinsics.areEqual(traverseKey, traversableNode.getTraverseKey())) {
                                        break loop0;
                                    }
                                } else if ((delegatingNode.kindSet & 262144) != 0 && (delegatingNode instanceof DelegatingNode)) {
                                    Modifier.Node node2 = delegatingNode.delegate;
                                    int i = 0;
                                    delegatingNode = delegatingNode;
                                    r5 = r5;
                                    while (node2 != null) {
                                        if ((node2.kindSet & 262144) != 0) {
                                            i++;
                                            r5 = r5;
                                            if (i == 1) {
                                                delegatingNode = node2;
                                            } else {
                                                if (r5 == 0) {
                                                    r5 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNode != 0) {
                                                    r5.add(delegatingNode);
                                                    delegatingNode = 0;
                                                }
                                                r5.add(node2);
                                            }
                                        }
                                        node2 = node2.child;
                                        delegatingNode = delegatingNode;
                                        r5 = r5;
                                    }
                                    if (i == 1) {
                                    }
                                }
                                delegatingNode = DelegatableNodeKt.access$pop(r5);
                            }
                        }
                        node = node.parent;
                    }
                }
                requireLayoutNode = requireLayoutNode.getParent$ui_release();
                node = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
            }
            if (traversableNode instanceof FocusedBoundsObserverNode) {
                return (FocusedBoundsObserverNode) traversableNode;
            }
        }
        return null;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return TraverseKey;
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        FocusedBoundsObserverNode focusedBoundsObserver;
        this.globalLayoutCoordinates = nodeCoordinator;
        if (((FocusTargetNode) this.focusTargetNode).getFocusState().isFocused()) {
            if (!nodeCoordinator.getTail().isAttached) {
                FocusedBoundsObserverNode focusedBoundsObserver2 = getFocusedBoundsObserver();
                if (focusedBoundsObserver2 != null) {
                    focusedBoundsObserver2.onFocusBoundsChanged(null);
                    return;
                }
                return;
            }
            NodeCoordinator nodeCoordinator2 = this.globalLayoutCoordinates;
            if (nodeCoordinator2 == null || !nodeCoordinator2.getTail().isAttached || (focusedBoundsObserver = getFocusedBoundsObserver()) == null) {
                return;
            }
            focusedBoundsObserver.onFocusBoundsChanged(this.globalLayoutCoordinates);
        }
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ObserverModifierNodeKt.observeReads(this, new FocusableNode$retrievePinnableContainer$1(ref$ObjectRef, this));
        PinnableContainer pinnableContainer = (PinnableContainer) ref$ObjectRef.element;
        if (((FocusTargetNode) this.focusTargetNode).getFocusState().isFocused()) {
            PinnableContainer.PinnedHandle pinnedHandle = this.pinnedHandle;
            if (pinnedHandle != null) {
                pinnedHandle.release();
            }
            this.pinnedHandle = pinnableContainer != null ? pinnableContainer.pin() : null;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onReset() {
        PinnableContainer.PinnedHandle pinnedHandle = this.pinnedHandle;
        if (pinnedHandle != null) {
            pinnedHandle.release();
        }
        this.pinnedHandle = null;
    }

    public final void update(MutableInteractionSource mutableInteractionSource) {
        FocusInteraction$Focus focusInteraction$Focus;
        if (Intrinsics.areEqual(this.interactionSource, mutableInteractionSource)) {
            return;
        }
        MutableInteractionSource mutableInteractionSource2 = this.interactionSource;
        if (mutableInteractionSource2 != null && (focusInteraction$Focus = this.focusedInteraction) != null) {
            mutableInteractionSource2.tryEmit(new FocusInteraction$Unfocus(focusInteraction$Focus));
        }
        this.focusedInteraction = null;
        this.interactionSource = mutableInteractionSource;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FocusableNode(androidx.compose.foundation.interaction.MutableInteractionSource r1, int r2, kotlin.jvm.functions.Function1 r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r0 = this;
            r5 = r4 & 2
            if (r5 == 0) goto Lb
            androidx.compose.ui.focus.Focusability$Companion r2 = androidx.compose.ui.focus.Focusability.Companion
            r2.getClass()
            int r2 = androidx.compose.ui.focus.Focusability.Always
        Lb:
            r4 = r4 & 4
            r5 = 0
            if (r4 == 0) goto L11
            r3 = r5
        L11:
            r0.<init>(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.FocusableNode.<init>(androidx.compose.foundation.interaction.MutableInteractionSource, int, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private FocusableNode(MutableInteractionSource mutableInteractionSource, int i, Function1 function1) {
        this.interactionSource = mutableInteractionSource;
        this.onFocusChange = function1;
        FocusTargetNode focusTargetNode = new FocusTargetNode(i, new FocusableNode$focusTargetNode$1(this), null, 4, null);
        delegate(focusTargetNode);
        this.focusTargetNode = focusTargetNode;
    }
}
