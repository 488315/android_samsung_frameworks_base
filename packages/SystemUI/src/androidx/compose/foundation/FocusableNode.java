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
import androidx.compose.ui.focus.Focusability;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ContextScope;

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

    final class TraverseKey {
        public /* synthetic */ TraverseKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private TraverseKey() {
        }
    }

    /* renamed from: androidx.compose.foundation.FocusableNode$emitWithFallback$1, reason: invalid class name and case insensitive filesystem */
    final class C06811 extends SuspendLambda implements Function2 {
        final /* synthetic */ DisposableHandle $handler;
        final /* synthetic */ Interaction $interaction;
        final /* synthetic */ MutableInteractionSource $this_emitWithFallback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06811(MutableInteractionSource mutableInteractionSource, Interaction interaction, DisposableHandle disposableHandle, Continuation continuation) {
            super(2, continuation);
            this.$this_emitWithFallback = mutableInteractionSource;
            this.$interaction = interaction;
            this.$handler = disposableHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C06811(this.$this_emitWithFallback, this.$interaction, this.$handler, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06811) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableInteractionSource mutableInteractionSource = this.$this_emitWithFallback;
                Interaction interaction = this.$interaction;
                this.label = 1;
                if (mutableInteractionSource.emit(interaction, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            DisposableHandle disposableHandle = this.$handler;
            if (disposableHandle != null) {
                disposableHandle.dispose();
            }
            return Unit.INSTANCE;
        }
    }

    public /* synthetic */ FocusableNode(MutableInteractionSource mutableInteractionSource, int i, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, i, function1);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        boolean zIsFocused = ((FocusTargetNode) this.focusTargetNode).getFocusState().isFocused();
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Focused;
        KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[4];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.valueOf(zIsFocused));
        if (this.requestFocus == null) {
            this.requestFocus = new Function0() { // from class: androidx.compose.foundation.FocusableNode.applySemantics.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(FocusTargetModifierNode.m379requestFocus3ESFkO8$default(FocusableNode.this.focusTargetNode));
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
        BuildersKt.launch$default(getCoroutineScope(), null, null, new C06811(mutableInteractionSource, interaction, job != null ? job.invokeOnCompletion(new Function1() { // from class: androidx.compose.foundation.FocusableNode$emitWithFallback$handler$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                mutableInteractionSource.tryEmit(interaction);
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
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
            loop0: while (true) {
                if (layoutNodeRequireLayoutNode == null) {
                    traversableNode = null;
                    break;
                }
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 262144) != 0) {
                    while (node != null) {
                        if ((node.kindSet & 262144) != 0) {
                            ?? mutableVector = 0;
                            DelegatingNode delegatingNodeAccess$pop = node;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof TraversableNode) {
                                    traversableNode = (TraversableNode) delegatingNodeAccess$pop;
                                    if (Intrinsics.areEqual(traverseKey, traversableNode.getTraverseKey())) {
                                        break loop0;
                                    }
                                } else if ((delegatingNodeAccess$pop.kindSet & 262144) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                    int i = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node2 != null) {
                                        if ((node2.kindSet & 262144) != 0) {
                                            i++;
                                            mutableVector = mutableVector;
                                            if (i == 1) {
                                                delegatingNodeAccess$pop = node2;
                                            } else {
                                                if (mutableVector == 0) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector.add(node2);
                                            }
                                        }
                                        node2 = node2.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                        node = node.parent;
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
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
    public FocusableNode(MutableInteractionSource mutableInteractionSource, int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            Focusability.Companion.getClass();
            i = Focusability.Always;
        }
        this(mutableInteractionSource, i, (i2 & 4) != 0 ? null : function1, null);
    }

    private FocusableNode(MutableInteractionSource mutableInteractionSource, int i, Function1 function1) {
        this.interactionSource = mutableInteractionSource;
        this.onFocusChange = function1;
        FocusTargetNode focusTargetNode = new FocusTargetNode(i, new FocusableNode$focusTargetNode$1(this), null, 4, null);
        delegate(focusTargetNode);
        this.focusTargetNode = focusTargetNode;
    }
}
