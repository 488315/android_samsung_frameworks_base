package androidx.compose.ui.focus;

import android.os.Trace;
import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.input.InputMode;
import androidx.compose.ui.input.InputModeManager;
import androidx.compose.ui.input.InputModeManagerImpl;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.CompositionLocalsKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final class FocusTargetNode extends Modifier.Node implements CompositionLocalConsumerModifierNode, FocusTargetModifierNode, ObserverModifierNode, ModifierLocalModifierNode {
    public FocusStateImpl committedFocusState;
    public final int focusability;
    public boolean isProcessingCustomEnter;
    public boolean isProcessingCustomExit;
    public final Function1 onDispatchEventsCompleted;
    public final Function2 onFocusChange;

    public final class FocusTargetElement extends ModifierNodeElement<FocusTargetNode> {
        public static final FocusTargetElement INSTANCE = new FocusTargetElement();

        private FocusTargetElement() {
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final Modifier.Node create() {
            return new FocusTargetNode(0, null, null, 7, null);
        }

        public final boolean equals(Object obj) {
            return obj == this;
        }

        public final int hashCode() {
            return 1739042953;
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final /* bridge */ /* synthetic */ void update(Modifier.Node node) {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CustomDestinationResult.values().length];
            try {
                iArr[CustomDestinationResult.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CustomDestinationResult.Redirected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CustomDestinationResult.Cancelled.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CustomDestinationResult.RedirectCancelled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FocusStateImpl.values().length];
            try {
                iArr2[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[FocusStateImpl.Captured.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[FocusStateImpl.ActiveParent.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public /* synthetic */ FocusTargetNode(int i, Function2 function2, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, function2, function1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x006f, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean initializeFocusState$hasActiveChild(FocusTargetNode focusTargetNode) {
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitSubtreeIf called on an unattached node");
        }
        MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node);
        } else {
            mutableVector.add(node2);
        }
        while (true) {
            int i = mutableVector.size;
            if (i == 0) {
                return false;
            }
            Modifier.Node node3 = (Modifier.Node) mutableVector.removeAt(i - 1);
            if ((node3.aggregateChildKindSet & 1024) != 0) {
                for (Modifier.Node node4 = node3; node4 != null; node4 = node4.child) {
                    if ((node4.kindSet & 1024) != 0) {
                        Modifier.Node nodeAccess$pop = node4;
                        MutableVector mutableVector2 = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
                                if (focusTargetNode2.isInitialized$ui_release()) {
                                    int i2 = WhenMappings.$EnumSwitchMapping$1[focusTargetNode2.getFocusState().ordinal()];
                                    if (i2 == 1 || i2 == 2 || i2 == 3) {
                                        break;
                                    }
                                    if (i2 != 4) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                }
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop).delegate; node5 != null; node5 = node5.child) {
                                    if ((node5.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            nodeAccess$pop = node5;
                                        } else {
                                            if (mutableVector2 == null) {
                                                mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector2.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector2.add(node5);
                                        }
                                    }
                                }
                                if (i3 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                        }
                    }
                }
            }
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node3);
        }
    }

    public static final boolean initializeFocusState$isInActiveSubTree(FocusTargetNode focusTargetNode) {
        NodeChain nodeChain;
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node = focusTargetNode.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        while (true) {
            if (layoutNodeRequireLayoutNode == null) {
                break;
            }
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node != null) {
                    if ((node.kindSet & 1024) != 0) {
                        Modifier.Node nodeAccess$pop = node;
                        MutableVector mutableVector = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
                                if (focusTargetNode2.isInitialized$ui_release()) {
                                    int i = WhenMappings.$EnumSwitchMapping$1[focusTargetNode2.getFocusState().ordinal()];
                                    if (i != 1 && i != 2) {
                                        if (i == 3) {
                                            return true;
                                        }
                                        if (i != 4) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                    }
                                }
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                    if ((node2.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            nodeAccess$pop = node2;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector.add(node2);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node = node.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [androidx.compose.runtime.collection.MutableVector] */
    public final void dispatchFocusCallbacks$ui_release() {
        NodeChain nodeChain;
        Function2 function2;
        FocusStateImpl focusStateImpl = this.committedFocusState;
        if (focusStateImpl == null) {
            focusStateImpl = FocusStateImpl.Inactive;
        }
        FocusStateImpl focusState = getFocusState();
        if (focusStateImpl != focusState && (function2 = this.onFocusChange) != null) {
            function2.invoke(focusStateImpl, focusState);
        }
        Modifier.Node node = this.node;
        if (!node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = this.node;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
        loop0: while (layoutNodeRequireLayoutNode != null) {
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 5120) != 0) {
                while (node2 != null) {
                    int i = node2.kindSet;
                    if ((i & 5120) != 0) {
                        if (node2 != node && (i & 1024) != 0) {
                            break loop0;
                        }
                        if ((i & 4096) != 0) {
                            DelegatingNode delegatingNodeAccess$pop = node2;
                            ?? mutableVector = 0;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof FocusEventModifierNode) {
                                    FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) delegatingNodeAccess$pop;
                                    focusEventModifierNode.onFocusEvent(FocusEventModifierNodeKt.getFocusState(focusEventModifierNode));
                                } else if ((delegatingNodeAccess$pop.kindSet & 4096) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node3 = delegatingNodeAccess$pop.delegate;
                                    int i2 = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node3 != null) {
                                        if ((node3.kindSet & 4096) != 0) {
                                            i2++;
                                            mutableVector = mutableVector;
                                            if (i2 == 1) {
                                                delegatingNodeAccess$pop = node3;
                                            } else {
                                                if (mutableVector == 0) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector.add(node3);
                                            }
                                        }
                                        node3 = node3.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                    }
                    node2 = node2.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node2 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        Function1 function1 = this.onDispatchEventsCompleted;
        if (function1 != null) {
            function1.mo781invoke(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [androidx.compose.runtime.collection.MutableVector] */
    public final FocusPropertiesImpl fetchFocusProperties$ui_release() {
        boolean z;
        NodeChain nodeChain;
        FocusPropertiesImpl focusPropertiesImpl = new FocusPropertiesImpl();
        int i = Focusability.Always;
        int i2 = this.focusability;
        if (i2 == i) {
            z = true;
        } else if (i2 == 0) {
            int i3 = ((InputMode) ((SnapshotMutableStateImpl) ((InputModeManagerImpl) ((InputModeManager) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, CompositionLocalsKt.LocalInputModeManager))).inputMode$delegate).getValue()).value;
            InputMode.Companion.getClass();
            z = !(i3 == InputMode.Touch);
        } else {
            if (i2 != Focusability.Never) {
                throw new IllegalStateException("Unknown Focusability");
            }
            z = false;
        }
        focusPropertiesImpl.canFocus = z;
        Modifier.Node node = this.node;
        if (!node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = this.node;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
        loop0: while (layoutNodeRequireLayoutNode != null) {
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 3072) != 0) {
                while (node2 != null) {
                    int i4 = node2.kindSet;
                    if ((i4 & 3072) != 0) {
                        if (node2 != node && (i4 & 1024) != 0) {
                            break loop0;
                        }
                        if ((i4 & 2048) != 0) {
                            DelegatingNode delegatingNodeAccess$pop = node2;
                            ?? mutableVector = 0;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof FocusPropertiesModifierNode) {
                                    ((FocusPropertiesModifierNode) delegatingNodeAccess$pop).applyFocusProperties(focusPropertiesImpl);
                                } else if ((delegatingNodeAccess$pop.kindSet & 2048) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node3 = delegatingNodeAccess$pop.delegate;
                                    int i5 = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node3 != null) {
                                        if ((node3.kindSet & 2048) != 0) {
                                            i5++;
                                            mutableVector = mutableVector;
                                            if (i5 == 1) {
                                                delegatingNodeAccess$pop = node3;
                                            } else {
                                                if (mutableVector == 0) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector.add(node3);
                                            }
                                        }
                                        node3 = node3.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i5 == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                    }
                    node2 = node2.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node2 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        return focusPropertiesImpl;
    }

    public final FocusStateImpl getFocusState() {
        NodeChain nodeChain;
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(this);
            if (focusTransactionManager != null) {
                if (ComposeUiFlags.isTrackFocusEnabled) {
                    throw new IllegalStateException("uncommittedFocusState must not be accessed when isTrackFocusEnabled is on");
                }
                FocusStateImpl focusStateImpl = (FocusStateImpl) focusTransactionManager.states.get(this);
                if (focusStateImpl != null) {
                    return focusStateImpl;
                }
            }
            FocusStateImpl focusStateImpl2 = this.committedFocusState;
            return focusStateImpl2 == null ? FocusStateImpl.Inactive : focusStateImpl2;
        }
        if (!this.isAttached) {
            return FocusStateImpl.Inactive;
        }
        FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
        FocusTargetNode focusTargetNode = focusOwnerImpl.activeFocusTargetNode;
        if (focusTargetNode == null) {
            return FocusStateImpl.Inactive;
        }
        if (this == focusTargetNode) {
            focusOwnerImpl.getClass();
            return FocusStateImpl.Active;
        }
        if (focusTargetNode.isAttached) {
            if (!focusTargetNode.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = focusTargetNode.node.parent;
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
            while (layoutNodeRequireLayoutNode != null) {
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    while (node != null) {
                        if ((node.kindSet & 1024) != 0) {
                            Modifier.Node nodeAccess$pop = node;
                            MutableVector mutableVector = null;
                            while (nodeAccess$pop != null) {
                                if (nodeAccess$pop instanceof FocusTargetNode) {
                                    if (this == ((FocusTargetNode) nodeAccess$pop)) {
                                        return FocusStateImpl.ActiveParent;
                                    }
                                } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                    int i = 0;
                                    for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                        if ((node2.kindSet & 1024) != 0) {
                                            i++;
                                            if (i == 1) {
                                                nodeAccess$pop = node2;
                                            } else {
                                                if (mutableVector == null) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop != null) {
                                                    mutableVector.add(nodeAccess$pop);
                                                    nodeAccess$pop = null;
                                                }
                                                mutableVector.add(node2);
                                            }
                                        }
                                    }
                                    if (i == 1) {
                                    }
                                }
                                nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                        node = node.parent;
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
            }
        }
        return FocusStateImpl.Inactive;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    public final void initializeFocusState$ui_release(FocusStateImpl focusStateImpl) {
        if (isInitialized$ui_release()) {
            throw new IllegalStateException("Re-initializing focus target node.");
        }
        if (ComposeUiFlags.isTrackFocusEnabled) {
            return;
        }
        FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(this);
        try {
            if (focusTransactionManagerRequireTransactionManager.ongoingTransaction) {
                FocusTransactionManager.access$cancelTransaction(focusTransactionManagerRequireTransactionManager);
            }
            focusTransactionManagerRequireTransactionManager.ongoingTransaction = true;
            if (focusStateImpl == null) {
                focusStateImpl = (initializeFocusState$isInActiveSubTree(this) && initializeFocusState$hasActiveChild(this)) ? FocusStateImpl.ActiveParent : FocusStateImpl.Inactive;
            }
            setFocusState(focusStateImpl);
            Unit unit = Unit.INSTANCE;
            FocusTransactionManager.access$commitTransaction(focusTransactionManagerRequireTransactionManager);
        } catch (Throwable th) {
            FocusTransactionManager.access$commitTransaction(focusTransactionManagerRequireTransactionManager);
            throw th;
        }
    }

    public final void invalidateFocus$ui_release() {
        if (!isInitialized$ui_release()) {
            initializeFocusState$ui_release(null);
        }
        int i = WhenMappings.$EnumSwitchMapping$1[getFocusState().ordinal()];
        if (i == 1 || i == 2) {
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.ui.focus.FocusTargetNode$invalidateFocus$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Type inference failed for: r1v2, types: [T, androidx.compose.ui.focus.FocusPropertiesImpl] */
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ref$ObjectRef.element = this.fetchFocusProperties$ui_release();
                    return Unit.INSTANCE;
                }
            });
            T t = ref$ObjectRef.element;
            if ((t != 0 ? (FocusProperties) t : null).getCanFocus()) {
                return;
            }
            FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
            focusOwnerImpl.getClass();
            FocusDirection.Companion.getClass();
            focusOwnerImpl.m372clearFocusI7lrPNg(FocusDirection.Exit, true, true);
        }
    }

    public final boolean isInitialized$ui_release() {
        return ComposeUiFlags.isTrackFocusEnabled || this.committedFocusState != null;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            return;
        }
        FocusTargetNodeKt.invalidateFocusTarget(this);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        int i = WhenMappings.$EnumSwitchMapping$1[getFocusState().ordinal()];
        if (i == 1 || i == 2) {
            FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
            FocusDirection.Companion.getClass();
            focusOwnerImpl.m372clearFocusI7lrPNg(FocusDirection.Exit, true, false);
            if (ComposeUiFlags.isTrackFocusEnabled) {
                FocusInvalidationManager focusInvalidationManager = focusOwnerImpl.focusInvalidationManager;
                if (!focusInvalidationManager.isInvalidationScheduled) {
                    focusInvalidationManager.onRequestApplyChangesListener.mo781invoke(new FocusInvalidationManager$setUpOnRequestApplyChangesListener$1(focusInvalidationManager));
                    focusInvalidationManager.isInvalidationScheduled = true;
                }
            } else {
                FocusTargetNodeKt.invalidateFocusTarget(this);
            }
        } else if (i == 3 && !ComposeUiFlags.isTrackFocusEnabled) {
            FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(this);
            try {
                if (focusTransactionManagerRequireTransactionManager.ongoingTransaction) {
                    FocusTransactionManager.access$cancelTransaction(focusTransactionManagerRequireTransactionManager);
                }
                focusTransactionManagerRequireTransactionManager.ongoingTransaction = true;
                setFocusState(FocusStateImpl.Inactive);
                Unit unit = Unit.INSTANCE;
                FocusTransactionManager.access$commitTransaction(focusTransactionManagerRequireTransactionManager);
            } catch (Throwable th) {
                FocusTransactionManager.access$commitTransaction(focusTransactionManagerRequireTransactionManager);
                throw th;
            }
        }
        this.committedFocusState = null;
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            invalidateFocus$ui_release();
            return;
        }
        FocusStateImpl focusState = getFocusState();
        invalidateFocus$ui_release();
        if (focusState != getFocusState()) {
            dispatchFocusCallbacks$ui_release();
        }
    }

    /* renamed from: requestFocus-3ESFkO8, reason: not valid java name */
    public final boolean m380requestFocus3ESFkO8(int i) {
        Trace.beginSection("FocusTransactions:requestFocus");
        try {
            boolean zPerformRequestFocus = false;
            if (!fetchFocusProperties$ui_release().canFocus) {
                return false;
            }
            if (ComposeUiFlags.isTrackFocusEnabled) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[FocusTransactionsKt.m383performCustomRequestFocusMxy_nc0(this, i).ordinal()];
                if (i2 == 1) {
                    zPerformRequestFocus = FocusTransactionsKt.performRequestFocus(this);
                } else if (i2 == 2) {
                    zPerformRequestFocus = true;
                } else if (i2 != 3 && i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
            } else {
                FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(this);
                Function0 function0 = new Function0() { // from class: androidx.compose.ui.focus.FocusTargetNode$requestFocus$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        FocusTargetNode focusTargetNode = this.this$0;
                        if (focusTargetNode.node.isAttached) {
                            focusTargetNode.dispatchFocusCallbacks$ui_release();
                        }
                        return Unit.INSTANCE;
                    }
                };
                try {
                    if (focusTransactionManagerRequireTransactionManager.ongoingTransaction) {
                        FocusTransactionManager.access$cancelTransaction(focusTransactionManagerRequireTransactionManager);
                    }
                    focusTransactionManagerRequireTransactionManager.ongoingTransaction = true;
                    focusTransactionManagerRequireTransactionManager.cancellationListener.add(function0);
                    int i3 = WhenMappings.$EnumSwitchMapping$0[FocusTransactionsKt.m383performCustomRequestFocusMxy_nc0(this, i).ordinal()];
                    if (i3 == 1) {
                        zPerformRequestFocus = FocusTransactionsKt.performRequestFocus(this);
                    } else if (i3 == 2) {
                        zPerformRequestFocus = true;
                    } else if (i3 != 3 && i3 != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                } finally {
                    FocusTransactionManager.access$commitTransaction(focusTransactionManagerRequireTransactionManager);
                }
            }
            return zPerformRequestFocus;
        } finally {
            Trace.endSection();
        }
    }

    public final void setFocusState(FocusStateImpl focusStateImpl) {
        boolean z = ComposeUiFlags.isTrackFocusEnabled;
        if (z) {
            return;
        }
        FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(this);
        focusTransactionManagerRequireTransactionManager.getClass();
        if (z) {
            return;
        }
        MutableScatterMap mutableScatterMap = focusTransactionManagerRequireTransactionManager.states;
        FocusStateImpl focusStateImpl2 = (FocusStateImpl) mutableScatterMap.get(this);
        if (focusStateImpl2 == null) {
            focusStateImpl2 = FocusStateImpl.Inactive;
        }
        if (focusStateImpl2 != focusStateImpl) {
            focusTransactionManagerRequireTransactionManager.generation++;
        }
        if (focusStateImpl == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("requires a non-null focus state");
        }
        mutableScatterMap.set(this, focusStateImpl);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FocusTargetNode(int i, Function2 function2, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 1) != 0) {
            Focusability.Companion.getClass();
            i = Focusability.Always;
        }
        this(i, (i2 & 2) != 0 ? null : function2, (i2 & 4) != 0 ? null : function1, null);
    }

    private FocusTargetNode(int i, Function2 function2, Function1 function1) {
        this.onFocusChange = function2;
        this.onDispatchEventsCompleted = function1;
        this.focusability = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7, types: [androidx.compose.runtime.collection.MutableVector] */
    public final void dispatchFocusCallbacks$ui_release(FocusStateImpl focusStateImpl, FocusStateImpl focusStateImpl2) {
        NodeChain nodeChain;
        Function2 function2;
        FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
        FocusTargetNode focusTargetNode = focusOwnerImpl.activeFocusTargetNode;
        if (!Intrinsics.areEqual(focusStateImpl, focusStateImpl2) && (function2 = this.onFocusChange) != null) {
            function2.invoke(focusStateImpl, focusStateImpl2);
        }
        Modifier.Node node = this.node;
        if (!node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = this.node;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
        loop0: while (layoutNodeRequireLayoutNode != null) {
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 5120) != 0) {
                while (node2 != null) {
                    int i = node2.kindSet;
                    if ((i & 5120) != 0) {
                        if (node2 != node && (i & 1024) != 0) {
                            break loop0;
                        }
                        if ((i & 4096) != 0) {
                            DelegatingNode delegatingNodeAccess$pop = node2;
                            ?? mutableVector = 0;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof FocusEventModifierNode) {
                                    FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) delegatingNodeAccess$pop;
                                    if (focusTargetNode == focusOwnerImpl.activeFocusTargetNode) {
                                        focusEventModifierNode.onFocusEvent(focusStateImpl2);
                                    }
                                } else if ((delegatingNodeAccess$pop.kindSet & 4096) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node3 = delegatingNodeAccess$pop.delegate;
                                    int i2 = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node3 != null) {
                                        if ((node3.kindSet & 4096) != 0) {
                                            i2++;
                                            mutableVector = mutableVector;
                                            if (i2 == 1) {
                                                delegatingNodeAccess$pop = node3;
                                            } else {
                                                if (mutableVector == 0) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector.add(node3);
                                            }
                                        }
                                        node3 = node3.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                    }
                    node2 = node2.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node2 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        Function1 function1 = this.onDispatchEventsCompleted;
        if (function1 != null) {
            function1.mo781invoke(this);
        }
    }
}
