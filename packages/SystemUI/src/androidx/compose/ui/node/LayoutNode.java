package androidx.compose.ui.node;

import androidx.compose.runtime.ComposeNodeLifecycleCallback;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.runtime.tooling.CompositionErrorContext;
import androidx.compose.runtime.tooling.CompositionErrorContextImpl;
import androidx.compose.runtime.tooling.CompositionErrorContextKt;
import androidx.compose.ui.CombinedModifier;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.LayoutNodeSubcompositionsState;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Remeasurement;
import androidx.compose.ui.node.LookaheadPassDelegate;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.JvmActuals_jvmKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final class LayoutNode implements ComposeNodeLifecycleCallback, Remeasurement, OwnerScope, ComposeUiNode, Owner.OnLayoutCompletedListener {
    public final MutableVectorWithMutationTracking _foldedChildren;
    public LayoutNode _foldedParent;
    public NodeCoordinator _innerLayerCoordinator;
    public Modifier _modifier;
    public SemanticsConfiguration _semanticsConfiguration;
    public MutableVector _unfoldedChildren;
    public final MutableVector _zSortedChildren;
    public boolean canMultiMeasure;
    public CompositionLocalMap compositionLocalMap;
    public Density density;
    public int depth;
    public boolean forceUseOldLayers;
    public boolean ignoreRemeasureRequests;
    public boolean innerLayerCoordinatorIsDirty;
    public AndroidViewHolder interopViewFactoryHolder;
    public IntrinsicsPolicy intrinsicsPolicy;
    public UsageByParent intrinsicsUsageByParent;
    public boolean isCurrentlyCalculatingSemanticsConfiguration;
    public boolean isDeactivated;
    public boolean isSemanticsInvalidated;
    public final boolean isVirtual;
    public boolean isVirtualLookaheadRoot;
    public long lastSize;
    public final LayoutNodeLayoutDelegate layoutDelegate;
    public LayoutDirection layoutDirection;
    public LayoutNode lookaheadRoot;
    public MeasurePolicy measurePolicy;
    public boolean needsOnPositionedDispatch;
    public final NodeChain nodes;
    public long offsetFromRoot;
    public Function1 onAttach;
    public Function1 onDetach;
    public long outerToInnerOffset;
    public boolean outerToInnerOffsetDirty;
    public AndroidComposeView owner;
    public Modifier pendingModifier;
    public UsageByParent previousIntrinsicsUsageByParent;
    public int semanticsId;
    public LayoutNodeSubcompositionsState subcompositionsState;
    public boolean unfoldedVirtualChildrenListDirty;
    public ViewConfiguration viewConfiguration;
    public int virtualChildrenCount;
    public boolean zSortedChildrenInvalidated;
    public static final Companion Companion = new Companion(null);
    public static final LayoutNode$Companion$ErrorMeasurePolicy$1 ErrorMeasurePolicy = new NoIntrinsicsMeasurePolicy() { // from class: androidx.compose.ui.node.LayoutNode$Companion$ErrorMeasurePolicy$1
        @Override // androidx.compose.ui.layout.MeasurePolicy
        /* renamed from: measure-3p2s80s */
        public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
            throw new IllegalStateException("Undefined measure and it is required");
        }
    };
    public static final Function0 Constructor = new Function0() { // from class: androidx.compose.ui.node.LayoutNode$Companion$Constructor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new LayoutNode(false, 0, 3, null);
        }
    };
    public static final LayoutNode$Companion$DummyViewConfiguration$1 DummyViewConfiguration = new ViewConfiguration() { // from class: androidx.compose.ui.node.LayoutNode$Companion$DummyViewConfiguration$1
        @Override // androidx.compose.ui.platform.ViewConfiguration
        public final long getDoubleTapTimeoutMillis() {
            return 300L;
        }

        @Override // androidx.compose.ui.platform.ViewConfiguration
        public final long getLongPressTimeoutMillis() {
            return 400L;
        }

        @Override // androidx.compose.ui.platform.ViewConfiguration
        /* renamed from: getMinimumTouchTargetSize-MYxV2XQ, reason: not valid java name */
        public final long mo645getMinimumTouchTargetSizeMYxV2XQ() {
            DpSize.Companion.getClass();
            return 0L;
        }

        @Override // androidx.compose.ui.platform.ViewConfiguration
        public final float getTouchSlop() {
            return 16.0f;
        }
    };
    public static final LayoutNode$$ExternalSyntheticLambda0 ZComparator = new LayoutNode$$ExternalSyntheticLambda0();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class LayoutState {
        public static final /* synthetic */ LayoutState[] $VALUES;
        public static final LayoutState Idle;
        public static final LayoutState LayingOut;
        public static final LayoutState LookaheadLayingOut;
        public static final LayoutState LookaheadMeasuring;
        public static final LayoutState Measuring;

        static {
            LayoutState layoutState = new LayoutState("Measuring", 0);
            Measuring = layoutState;
            LayoutState layoutState2 = new LayoutState("LookaheadMeasuring", 1);
            LookaheadMeasuring = layoutState2;
            LayoutState layoutState3 = new LayoutState("LayingOut", 2);
            LayingOut = layoutState3;
            LayoutState layoutState4 = new LayoutState("LookaheadLayingOut", 3);
            LookaheadLayingOut = layoutState4;
            LayoutState layoutState5 = new LayoutState("Idle", 4);
            Idle = layoutState5;
            LayoutState[] layoutStateArr = {layoutState, layoutState2, layoutState3, layoutState4, layoutState5};
            $VALUES = layoutStateArr;
            EnumEntriesKt.enumEntries(layoutStateArr);
        }

        private LayoutState(String str, int i) {
        }

        public static LayoutState valueOf(String str) {
            return (LayoutState) Enum.valueOf(LayoutState.class, str);
        }

        public static LayoutState[] values() {
            return (LayoutState[]) $VALUES.clone();
        }
    }

    public abstract class NoIntrinsicsMeasurePolicy implements MeasurePolicy {
        public final String error;

        public NoIntrinsicsMeasurePolicy(String str) {
            this.error = str;
        }

        @Override // androidx.compose.ui.layout.MeasurePolicy
        public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
            throw new IllegalStateException(this.error.toString());
        }

        @Override // androidx.compose.ui.layout.MeasurePolicy
        public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
            throw new IllegalStateException(this.error.toString());
        }

        @Override // androidx.compose.ui.layout.MeasurePolicy
        public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
            throw new IllegalStateException(this.error.toString());
        }

        @Override // androidx.compose.ui.layout.MeasurePolicy
        public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
            throw new IllegalStateException(this.error.toString());
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class UsageByParent {
        public static final /* synthetic */ UsageByParent[] $VALUES;
        public static final UsageByParent InLayoutBlock;
        public static final UsageByParent InMeasureBlock;
        public static final UsageByParent NotUsed;

        static {
            UsageByParent usageByParent = new UsageByParent("InMeasureBlock", 0);
            InMeasureBlock = usageByParent;
            UsageByParent usageByParent2 = new UsageByParent("InLayoutBlock", 1);
            InLayoutBlock = usageByParent2;
            UsageByParent usageByParent3 = new UsageByParent("NotUsed", 2);
            NotUsed = usageByParent3;
            UsageByParent[] usageByParentArr = {usageByParent, usageByParent2, usageByParent3};
            $VALUES = usageByParentArr;
            EnumEntriesKt.enumEntries(usageByParentArr);
        }

        private UsageByParent(String str, int i) {
        }

        public static UsageByParent valueOf(String str) {
            return (UsageByParent) Enum.valueOf(UsageByParent.class, str);
        }

        public static UsageByParent[] values() {
            return (UsageByParent[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutState.values().length];
            try {
                iArr[LayoutState.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LayoutNode() {
        this(false, 0, 3, null);
    }

    private final String exceptionMessageForParentingOrOwnership(LayoutNode layoutNode) {
        StringBuilder sb = new StringBuilder("Cannot insert ");
        sb.append(layoutNode);
        sb.append(" because it already has a parent or an owner. This tree: ");
        sb.append(debugTreeToString(0));
        sb.append(" Other tree: ");
        LayoutNode layoutNode2 = layoutNode._foldedParent;
        sb.append(layoutNode2 != null ? layoutNode2.debugTreeToString(0) : null);
        return sb.toString();
    }

    /* renamed from: remeasure-_Sx5XlM$ui_release$default, reason: not valid java name */
    public static boolean m641remeasure_Sx5XlM$ui_release$default(LayoutNode layoutNode) {
        MeasurePassDelegate measurePassDelegate = layoutNode.layoutDelegate.measurePassDelegate;
        return layoutNode.m644remeasure_Sx5XlM$ui_release(measurePassDelegate.measuredOnce ? Constraints.m815boximpl(measurePassDelegate.measurementConstraints) : null);
    }

    public static void requestLookaheadRemeasure$ui_release$default(LayoutNode layoutNode, boolean z, int i) {
        LayoutNode parent$ui_release;
        if ((i & 1) != 0) {
            z = false;
        }
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 4) != 0;
        if (layoutNode.lookaheadRoot == null) {
            InlineClassHelperKt.throwIllegalStateException("Lookahead measure cannot be requested on a node that is not a part of the LookaheadScope");
        }
        AndroidComposeView androidComposeView = layoutNode.owner;
        if (androidComposeView == null || layoutNode.ignoreRemeasureRequests || layoutNode.isVirtual) {
            return;
        }
        androidComposeView.onRequestMeasure(layoutNode, true, z, z2);
        if (z3) {
            LookaheadPassDelegate lookaheadPassDelegate = layoutNode.layoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = lookaheadPassDelegate.layoutNodeLayoutDelegate;
            LayoutNode parent$ui_release2 = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
            UsageByParent usageByParent = layoutNodeLayoutDelegate.layoutNode.intrinsicsUsageByParent;
            if (parent$ui_release2 == null || usageByParent == UsageByParent.NotUsed) {
                return;
            }
            while (parent$ui_release2.intrinsicsUsageByParent == usageByParent && (parent$ui_release = parent$ui_release2.getParent$ui_release()) != null) {
                parent$ui_release2 = parent$ui_release;
            }
            int i2 = LookaheadPassDelegate.WhenMappings.$EnumSwitchMapping$1[usageByParent.ordinal()];
            if (i2 == 1) {
                if (parent$ui_release2.lookaheadRoot != null) {
                    requestLookaheadRemeasure$ui_release$default(parent$ui_release2, z, 6);
                    return;
                } else {
                    requestRemeasure$ui_release$default(parent$ui_release2, z, 6);
                    return;
                }
            }
            if (i2 != 2) {
                throw new IllegalStateException("Intrinsics isn't used by the parent");
            }
            if (parent$ui_release2.lookaheadRoot != null) {
                parent$ui_release2.requestLookaheadRelayout$ui_release(z);
            } else {
                parent$ui_release2.requestRelayout$ui_release(z);
            }
        }
    }

    public static void requestRemeasure$ui_release$default(LayoutNode layoutNode, boolean z, int i) {
        AndroidComposeView androidComposeView;
        LayoutNode parent$ui_release;
        if ((i & 1) != 0) {
            z = false;
        }
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 4) != 0;
        if (layoutNode.ignoreRemeasureRequests || layoutNode.isVirtual || (androidComposeView = layoutNode.owner) == null) {
            return;
        }
        Owner.Companion companion = Owner.Companion;
        androidComposeView.onRequestMeasure(layoutNode, false, z, z2);
        if (z3) {
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate.measurePassDelegate.layoutNodeLayoutDelegate;
            LayoutNode parent$ui_release2 = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
            UsageByParent usageByParent = layoutNodeLayoutDelegate.layoutNode.intrinsicsUsageByParent;
            if (parent$ui_release2 == null || usageByParent == UsageByParent.NotUsed) {
                return;
            }
            while (parent$ui_release2.intrinsicsUsageByParent == usageByParent && (parent$ui_release = parent$ui_release2.getParent$ui_release()) != null) {
                parent$ui_release2 = parent$ui_release;
            }
            int i2 = MeasurePassDelegate.WhenMappings.$EnumSwitchMapping$1[usageByParent.ordinal()];
            if (i2 == 1) {
                requestRemeasure$ui_release$default(parent$ui_release2, z, 6);
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException("Intrinsics isn't used by the parent");
                }
                parent$ui_release2.requestRelayout$ui_release(z);
            }
        }
    }

    public static void rescheduleRemeasureOrRelayout$ui_release(LayoutNode layoutNode) {
        int i = WhenMappings.$EnumSwitchMapping$0[layoutNode.layoutDelegate.layoutState.ordinal()];
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
        if (i != 1) {
            throw new IllegalStateException("Unexpected state " + layoutNodeLayoutDelegate.layoutState);
        }
        if (layoutNodeLayoutDelegate.lookaheadMeasurePending) {
            requestLookaheadRemeasure$ui_release$default(layoutNode, true, 6);
            return;
        }
        if (layoutNodeLayoutDelegate.lookaheadLayoutPending) {
            layoutNode.requestLookaheadRelayout$ui_release(true);
        }
        if (layoutNode.getMeasurePending$ui_release()) {
            requestRemeasure$ui_release$default(layoutNode, true, 6);
        } else if (layoutNode.getLayoutPending$ui_release()) {
            layoutNode.requestRelayout$ui_release(true);
        }
    }

    public final void applyModifier(Modifier modifier) {
        MutableVector mutableVector;
        boolean z;
        NodeChain nodeChain;
        NodeChainKt$SentinelHead$1 nodeChainKt$SentinelHead$1;
        this._modifier = modifier;
        NodeChain nodeChain2 = this.nodes;
        Modifier.Node node = nodeChain2.head;
        NodeChainKt$SentinelHead$1 nodeChainKt$SentinelHead$12 = NodeChainKt.SentinelHead;
        if (node == nodeChainKt$SentinelHead$12) {
            InlineClassHelperKt.throwIllegalStateException("padChain called on already padded chain");
        }
        Modifier.Node node2 = nodeChain2.head;
        node2.parent = nodeChainKt$SentinelHead$12;
        nodeChainKt$SentinelHead$12.child = node2;
        MutableVector mutableVector2 = nodeChain2.current;
        int i = mutableVector2 != null ? mutableVector2.size : 0;
        final MutableVector mutableVector3 = nodeChain2.buffer;
        if (mutableVector3 == null) {
            mutableVector3 = new MutableVector(new Modifier.Element[16], 0);
        }
        int i2 = mutableVector3.size;
        if (i2 < 16) {
            i2 = 16;
        }
        MutableVector mutableVector4 = new MutableVector(new Modifier[i2], 0);
        mutableVector4.add(modifier);
        Function1 function1 = null;
        while (true) {
            int i3 = mutableVector4.size;
            if (i3 == 0) {
                break;
            }
            Modifier modifier2 = (Modifier) mutableVector4.removeAt(i3 - 1);
            if (modifier2 instanceof CombinedModifier) {
                CombinedModifier combinedModifier = (CombinedModifier) modifier2;
                mutableVector4.add(combinedModifier.inner);
                mutableVector4.add(combinedModifier.outer);
            } else if (modifier2 instanceof Modifier.Element) {
                mutableVector3.add(modifier2);
            } else {
                if (function1 == null) {
                    function1 = new Function1() { // from class: androidx.compose.ui.node.NodeChainKt$fillVector$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            mutableVector3.add((Modifier.Element) obj);
                            return Boolean.TRUE;
                        }
                    };
                }
                modifier2.all(function1);
                function1 = function1;
            }
        }
        int i4 = mutableVector3.size;
        boolean z2 = true;
        Modifier.Node node3 = nodeChain2.tail;
        LayoutNode layoutNode = nodeChain2.layoutNode;
        if (i4 == i) {
            Modifier.Node node4 = nodeChainKt$SentinelHead$12.child;
            int i5 = 0;
            while (true) {
                if (node4 == null || i5 >= i) {
                    break;
                }
                if (mutableVector2 == null) {
                    throw AndroidAutofill$$ExternalSyntheticOutline0.m("expected prior modifier list to be non-empty");
                }
                Modifier.Element element = (Modifier.Element) mutableVector2.content[i5];
                Modifier.Element element2 = (Modifier.Element) mutableVector3.content[i5];
                boolean z3 = Intrinsics.areEqual(element, element2) ? 2 : element.getClass() == element2.getClass();
                if (!z3) {
                    node4 = node4.parent;
                    break;
                }
                if (z3) {
                    NodeChain.updateNode(element, element2, node4);
                }
                node4 = node4.child;
                i5++;
            }
            if (i5 >= i) {
                mutableVector = mutableVector3;
                nodeChain2 = nodeChain2;
                nodeChain = nodeChain2;
                nodeChainKt$SentinelHead$1 = nodeChainKt$SentinelHead$12;
                z2 = false;
            } else {
                if (mutableVector2 == null) {
                    throw AndroidAutofill$$ExternalSyntheticOutline0.m("expected prior modifier list to be non-empty");
                }
                if (node4 == null) {
                    throw AndroidAutofill$$ExternalSyntheticOutline0.m("structuralUpdate requires a non-null tail");
                }
                mutableVector = mutableVector3;
                Modifier.Node node5 = node4;
                nodeChain = nodeChain2;
                nodeChain.structuralUpdate(i5, mutableVector2, mutableVector, node5, !(layoutNode.pendingModifier != null));
                nodeChainKt$SentinelHead$1 = nodeChainKt$SentinelHead$12;
            }
        } else {
            mutableVector = mutableVector3;
            if (layoutNode.pendingModifier != null && i == 0) {
                Modifier.Node nodeCreateAndInsertNodeAsChild = nodeChainKt$SentinelHead$12;
                for (int i6 = 0; i6 < mutableVector.size; i6++) {
                    nodeCreateAndInsertNodeAsChild = NodeChain.createAndInsertNodeAsChild((Modifier.Element) mutableVector.content[i6], nodeCreateAndInsertNodeAsChild);
                }
                Modifier.Node node6 = node3.parent;
                int i7 = 0;
                while (node6 != null && node6 != NodeChainKt.SentinelHead) {
                    int i8 = i7 | node6.kindSet;
                    node6.aggregateChildKindSet = i8;
                    node6 = node6.parent;
                    i7 = i8;
                }
                nodeChain = nodeChain2;
                nodeChainKt$SentinelHead$1 = nodeChainKt$SentinelHead$12;
            } else if (i4 != 0) {
                if (mutableVector2 == null) {
                    z = false;
                    mutableVector2 = new MutableVector(new Modifier.Element[16], 0);
                } else {
                    z = false;
                }
                if (layoutNode.pendingModifier != null) {
                    z = true;
                }
                boolean z4 = !z;
                nodeChain = nodeChain2;
                nodeChainKt$SentinelHead$1 = nodeChainKt$SentinelHead$12;
                nodeChain.structuralUpdate(0, mutableVector2, mutableVector, nodeChainKt$SentinelHead$1, z4);
            } else {
                if (mutableVector2 == null) {
                    throw AndroidAutofill$$ExternalSyntheticOutline0.m("expected prior modifier list to be non-empty");
                }
                Modifier.Node node7 = nodeChainKt$SentinelHead$12.child;
                for (int i9 = 0; node7 != null && i9 < mutableVector2.size; i9++) {
                    node7 = NodeChain.detachAndRemoveNode(node7).child;
                }
                LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                InnerNodeCoordinator innerNodeCoordinator = parent$ui_release != null ? parent$ui_release.nodes.innerCoordinator : null;
                InnerNodeCoordinator innerNodeCoordinator2 = nodeChain2.innerCoordinator;
                innerNodeCoordinator2.wrappedBy = innerNodeCoordinator;
                nodeChain2.outerCoordinator = innerNodeCoordinator2;
                nodeChain = nodeChain2;
                nodeChainKt$SentinelHead$1 = nodeChainKt$SentinelHead$12;
                z2 = false;
            }
        }
        nodeChain.current = mutableVector;
        if (mutableVector2 != null) {
            mutableVector2.clear();
        } else {
            mutableVector2 = null;
        }
        nodeChain.buffer = mutableVector2;
        NodeChainKt$SentinelHead$1 nodeChainKt$SentinelHead$13 = NodeChainKt.SentinelHead;
        if (nodeChainKt$SentinelHead$1 != nodeChainKt$SentinelHead$13) {
            InlineClassHelperKt.throwIllegalStateException("trimChain called on already trimmed chain");
        }
        Modifier.Node node8 = nodeChainKt$SentinelHead$13.child;
        if (node8 != null) {
            node3 = node8;
        }
        node3.parent = null;
        nodeChainKt$SentinelHead$13.child = null;
        nodeChainKt$SentinelHead$13.aggregateChildKindSet = -1;
        nodeChainKt$SentinelHead$13.coordinator = null;
        if (node3 == nodeChainKt$SentinelHead$13) {
            InlineClassHelperKt.throwIllegalStateException("trimChain did not update the head");
        }
        nodeChain.head = node3;
        if (z2) {
            nodeChain.syncCoordinators();
        }
        this.layoutDelegate.updateParentData();
        if (this.lookaheadRoot == null && nodeChain.m665hasH91voCI$ui_release(512)) {
            setLookaheadRoot(this);
        }
    }

    public final void attach$ui_release(AndroidComposeView androidComposeView) {
        LayoutNode layoutNode;
        if (!(this.owner == null)) {
            InlineClassHelperKt.throwIllegalStateException("Cannot attach " + this + " as it already is attached.  Tree: " + debugTreeToString(0));
        }
        LayoutNode layoutNode2 = this._foldedParent;
        if (layoutNode2 != null && !Intrinsics.areEqual(layoutNode2.owner, androidComposeView)) {
            StringBuilder sb = new StringBuilder("Attaching to a different owner(");
            sb.append(androidComposeView);
            sb.append(") than the parent's owner(");
            LayoutNode parent$ui_release = getParent$ui_release();
            sb.append(parent$ui_release != null ? parent$ui_release.owner : null);
            sb.append("). This tree: ");
            sb.append(debugTreeToString(0));
            sb.append(" Parent tree: ");
            LayoutNode layoutNode3 = this._foldedParent;
            sb.append(layoutNode3 != null ? layoutNode3.debugTreeToString(0) : null);
            InlineClassHelperKt.throwIllegalStateException(sb.toString());
        }
        LayoutNode parent$ui_release2 = getParent$ui_release();
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutDelegate;
        if (parent$ui_release2 == null) {
            layoutNodeLayoutDelegate.measurePassDelegate.isPlaced = true;
            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate != null) {
                lookaheadPassDelegate._placedState = LookaheadPassDelegate.PlacedState.IsPlacedInLookahead;
            }
        }
        NodeChain nodeChain = this.nodes;
        nodeChain.outerCoordinator.wrappedBy = parent$ui_release2 != null ? parent$ui_release2.nodes.innerCoordinator : null;
        this.owner = androidComposeView;
        this.depth = (parent$ui_release2 != null ? parent$ui_release2.depth : -1) + 1;
        Modifier modifier = this.pendingModifier;
        if (modifier != null) {
            applyModifier(modifier);
        }
        this.pendingModifier = null;
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        if (nodeChain.m665hasH91voCI$ui_release(8)) {
            invalidateSemantics$ui_release();
        }
        androidComposeView.layoutNodes.set(this.semanticsId, this);
        if (this.isVirtualLookaheadRoot) {
            setLookaheadRoot(this);
        } else {
            LayoutNode layoutNode4 = this._foldedParent;
            if (layoutNode4 == null || (layoutNode = layoutNode4.lookaheadRoot) == null) {
                layoutNode = this.lookaheadRoot;
            }
            setLookaheadRoot(layoutNode);
            if (this.lookaheadRoot == null && nodeChain.m665hasH91voCI$ui_release(512)) {
                setLookaheadRoot(this);
            }
        }
        if (!this.isDeactivated) {
            for (Modifier.Node node = nodeChain.head; node != null; node = node.child) {
                node.markAsAttached$ui_release();
            }
        }
        MutableVector mutableVector = this._foldedChildren.vector;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((LayoutNode) objArr[i2]).attach$ui_release(androidComposeView);
        }
        if (!this.isDeactivated) {
            nodeChain.runAttachLifecycle();
        }
        invalidateMeasurements$ui_release();
        if (parent$ui_release2 != null) {
            parent$ui_release2.invalidateMeasurements$ui_release();
        }
        NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator.wrapped;
        for (NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator; !Intrinsics.areEqual(nodeCoordinator2, nodeCoordinator) && nodeCoordinator2 != null; nodeCoordinator2 = nodeCoordinator2.wrapped) {
            nodeCoordinator2.updateLayerBlock(nodeCoordinator2.layerBlock, true);
            OwnedLayer ownedLayer = nodeCoordinator2.layer;
            if (ownedLayer != null) {
                ownedLayer.invalidate();
            }
        }
        Function1 function1 = this.onAttach;
        if (function1 != null) {
            function1.mo781invoke(androidComposeView);
        }
        layoutNodeLayoutDelegate.updateParentData();
        boolean z2 = ComposeUiFlags.isRectTrackingEnabled;
    }

    public final void clearSubtreeIntrinsicsUsage$ui_release() {
        this.previousIntrinsicsUsageByParent = this.intrinsicsUsageByParent;
        this.intrinsicsUsageByParent = UsageByParent.NotUsed;
        MutableVector mutableVector = get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i2];
            if (layoutNode.intrinsicsUsageByParent != UsageByParent.NotUsed) {
                layoutNode.clearSubtreeIntrinsicsUsage$ui_release();
            }
        }
    }

    public final void clearSubtreePlacementIntrinsicsUsage() {
        this.previousIntrinsicsUsageByParent = this.intrinsicsUsageByParent;
        this.intrinsicsUsageByParent = UsageByParent.NotUsed;
        MutableVector mutableVector = get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i2];
            if (layoutNode.intrinsicsUsageByParent == UsageByParent.InLayoutBlock) {
                layoutNode.clearSubtreePlacementIntrinsicsUsage();
            }
        }
    }

    public final String debugTreeToString(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
        sb.append("|-");
        sb.append(toString());
        sb.append('\n');
        MutableVector mutableVector = get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i3 = mutableVector.size;
        for (int i4 = 0; i4 < i3; i4++) {
            sb.append(((LayoutNode) objArr[i4]).debugTreeToString(i + 1));
        }
        String string = sb.toString();
        return i == 0 ? string.substring(0, string.length() - 1) : string;
    }

    public final void detach$ui_release() {
        LookaheadAlignmentLines lookaheadAlignmentLines;
        AndroidComposeView androidComposeView = this.owner;
        if (androidComposeView == null) {
            StringBuilder sb = new StringBuilder("Cannot detach node that is already detached!  Tree: ");
            LayoutNode parent$ui_release = getParent$ui_release();
            sb.append(parent$ui_release != null ? parent$ui_release.debugTreeToString(0) : null);
            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck(sb.toString());
            throw new KotlinNothingValueException();
        }
        LayoutNode parent$ui_release2 = getParent$ui_release();
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutDelegate;
        if (parent$ui_release2 != null) {
            parent$ui_release2.invalidateLayer$ui_release();
            parent$ui_release2.invalidateMeasurements$ui_release();
            MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate.measurePassDelegate;
            UsageByParent usageByParent = UsageByParent.NotUsed;
            measurePassDelegate.measuredByParent = usageByParent;
            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate != null) {
                lookaheadPassDelegate.measuredByParent = usageByParent;
            }
        }
        LayoutNodeAlignmentLines layoutNodeAlignmentLines = layoutNodeLayoutDelegate.measurePassDelegate.alignmentLines;
        layoutNodeAlignmentLines.dirty = true;
        layoutNodeAlignmentLines.usedDuringParentMeasurement = false;
        layoutNodeAlignmentLines.previousUsedDuringParentLayout = false;
        layoutNodeAlignmentLines.usedDuringParentLayout = false;
        layoutNodeAlignmentLines.usedByModifierMeasurement = false;
        layoutNodeAlignmentLines.usedByModifierLayout = false;
        layoutNodeAlignmentLines.queryOwner = null;
        LookaheadPassDelegate lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        if (lookaheadPassDelegate2 != null && (lookaheadAlignmentLines = lookaheadPassDelegate2.alignmentLines) != null) {
            lookaheadAlignmentLines.dirty = true;
            lookaheadAlignmentLines.usedDuringParentMeasurement = false;
            lookaheadAlignmentLines.previousUsedDuringParentLayout = false;
            lookaheadAlignmentLines.usedDuringParentLayout = false;
            lookaheadAlignmentLines.usedByModifierMeasurement = false;
            lookaheadAlignmentLines.usedByModifierLayout = false;
            lookaheadAlignmentLines.queryOwner = null;
        }
        Function1 function1 = this.onDetach;
        if (function1 != null) {
            function1.mo781invoke(androidComposeView);
        }
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        NodeChain nodeChain = this.nodes;
        if (nodeChain.m665hasH91voCI$ui_release(8)) {
            invalidateSemantics$ui_release();
        }
        nodeChain.runDetachLifecycle$ui_release();
        this.ignoreRemeasureRequests = true;
        MutableVector mutableVector = this._foldedChildren.vector;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((LayoutNode) objArr[i2]).detach$ui_release();
        }
        this.ignoreRemeasureRequests = false;
        for (Modifier.Node node = nodeChain.tail; node != null; node = node.parent) {
            if (node.isAttached) {
                node.markAsDetached$ui_release();
            }
        }
        androidComposeView.layoutNodes.remove(this.semanticsId);
        MeasureAndLayoutDelegate measureAndLayoutDelegate = androidComposeView.measureAndLayoutDelegate;
        DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = measureAndLayoutDelegate.relayoutNodes;
        depthSortedSetsForDifferentPasses.lookaheadSet.remove(this);
        depthSortedSetsForDifferentPasses.set.remove(this);
        measureAndLayoutDelegate.onPositionedDispatcher.layoutNodes.remove(this);
        androidComposeView.observationClearRequested = true;
        if (ComposeUiFlags.isRectTrackingEnabled) {
            androidComposeView.rectManager.remove(this);
        }
        this.owner = null;
        setLookaheadRoot(null);
        this.depth = 0;
        MeasurePassDelegate measurePassDelegate2 = layoutNodeLayoutDelegate.measurePassDelegate;
        measurePassDelegate2.placeOrder = Integer.MAX_VALUE;
        measurePassDelegate2.previousPlaceOrder = Integer.MAX_VALUE;
        measurePassDelegate2.isPlaced = false;
        LookaheadPassDelegate lookaheadPassDelegate3 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        if (lookaheadPassDelegate3 != null) {
            lookaheadPassDelegate3.placeOrder = Integer.MAX_VALUE;
            lookaheadPassDelegate3.previousPlaceOrder = Integer.MAX_VALUE;
            lookaheadPassDelegate3._placedState = LookaheadPassDelegate.PlacedState.IsNotPlaced;
        }
    }

    public final void draw$ui_release(Canvas canvas, GraphicsLayer graphicsLayer) {
        try {
            this.nodes.outerCoordinator.draw(canvas, graphicsLayer);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            this.rethrowWithComposeStackTrace(th);
            throw null;
        }
    }

    public final void forceRemeasure() {
        if (this.lookaheadRoot != null) {
            requestLookaheadRemeasure$ui_release$default(this, false, 5);
        } else {
            requestRemeasure$ui_release$default(this, false, 5);
        }
        MeasurePassDelegate measurePassDelegate = this.layoutDelegate.measurePassDelegate;
        Constraints constraintsM815boximpl = measurePassDelegate.measuredOnce ? Constraints.m815boximpl(measurePassDelegate.measurementConstraints) : null;
        if (constraintsM815boximpl != null) {
            AndroidComposeView androidComposeView = this.owner;
            if (androidComposeView != null) {
                androidComposeView.m696measureAndLayout0kLqBqw(this, constraintsM815boximpl.value);
                return;
            }
            return;
        }
        AndroidComposeView androidComposeView2 = this.owner;
        if (androidComposeView2 != null) {
            Owner.Companion companion = Owner.Companion;
            androidComposeView2.measureAndLayout(true);
        }
    }

    public final List getChildLookaheadMeasurables$ui_release() {
        LookaheadPassDelegate lookaheadPassDelegate = this.layoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = lookaheadPassDelegate.layoutNodeLayoutDelegate;
        layoutNodeLayoutDelegate.layoutNode.getChildren$ui_release();
        boolean z = lookaheadPassDelegate.childDelegatesDirty;
        MutableVector mutableVector = lookaheadPassDelegate._childDelegates;
        if (!z) {
            return mutableVector.asMutableList();
        }
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        MutableVector mutableVector2 = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector2.content;
        int i = mutableVector2.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (mutableVector.size <= i2) {
                LookaheadPassDelegate lookaheadPassDelegate2 = layoutNode2.layoutDelegate.lookaheadPassDelegate;
                lookaheadPassDelegate2.getClass();
                mutableVector.add(lookaheadPassDelegate2);
            } else {
                LookaheadPassDelegate lookaheadPassDelegate3 = layoutNode2.layoutDelegate.lookaheadPassDelegate;
                lookaheadPassDelegate3.getClass();
                Object[] objArr2 = mutableVector.content;
                Object obj = objArr2[i2];
                objArr2[i2] = lookaheadPassDelegate3;
            }
        }
        mutableVector.removeRange(layoutNode.getChildren$ui_release().size(), mutableVector.size);
        lookaheadPassDelegate.childDelegatesDirty = false;
        return mutableVector.asMutableList();
    }

    public final List getChildMeasurables$ui_release() {
        return this.layoutDelegate.measurePassDelegate.getChildDelegates$ui_release();
    }

    public final List getChildren$ui_release() {
        return get_children$ui_release().asMutableList();
    }

    public final List getFoldedChildren$ui_release() {
        return this._foldedChildren.vector.asMutableList();
    }

    public final boolean getLayoutPending$ui_release() {
        return this.layoutDelegate.measurePassDelegate.layoutPending;
    }

    public final boolean getMeasurePending$ui_release() {
        return this.layoutDelegate.measurePassDelegate.measurePending;
    }

    public final UsageByParent getMeasuredByParent$ui_release() {
        return this.layoutDelegate.measurePassDelegate.measuredByParent;
    }

    public final UsageByParent getMeasuredByParentInLookahead$ui_release() {
        UsageByParent usageByParent;
        LookaheadPassDelegate lookaheadPassDelegate = this.layoutDelegate.lookaheadPassDelegate;
        return (lookaheadPassDelegate == null || (usageByParent = lookaheadPassDelegate.measuredByParent) == null) ? UsageByParent.NotUsed : usageByParent;
    }

    public final IntrinsicsPolicy getOrCreateIntrinsicsPolicy() {
        IntrinsicsPolicy intrinsicsPolicy = this.intrinsicsPolicy;
        if (intrinsicsPolicy != null) {
            return intrinsicsPolicy;
        }
        IntrinsicsPolicy intrinsicsPolicy2 = new IntrinsicsPolicy(this, this.measurePolicy);
        this.intrinsicsPolicy = intrinsicsPolicy2;
        return intrinsicsPolicy2;
    }

    public final LayoutNode getParent$ui_release() {
        LayoutNode layoutNode = this._foldedParent;
        while (layoutNode != null && layoutNode.isVirtual) {
            layoutNode = layoutNode._foldedParent;
        }
        return layoutNode;
    }

    public final int getPlaceOrder$ui_release() {
        return this.layoutDelegate.measurePassDelegate.placeOrder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.semantics.SemanticsConfiguration] */
    public final SemanticsConfiguration getSemanticsConfiguration() {
        if (!isAttached() || this.isDeactivated || !this.nodes.m665hasH91voCI$ui_release(8)) {
            return null;
        }
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        if (this._semanticsConfiguration == null) {
            this.isCurrentlyCalculatingSemanticsConfiguration = true;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = new SemanticsConfiguration();
            OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) LayoutNodeKt.requireOwner(this)).snapshotObserver;
            ownerSnapshotObserver.observeReads$ui_release(this, ownerSnapshotObserver.onCommitAffectingSemantics, new Function0() { // from class: androidx.compose.ui.node.LayoutNode$calculateSemanticsConfiguration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v0 */
                /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.ui.Modifier$Node] */
                /* JADX WARN: Type inference failed for: r2v10 */
                /* JADX WARN: Type inference failed for: r2v11 */
                /* JADX WARN: Type inference failed for: r2v3 */
                /* JADX WARN: Type inference failed for: r2v4, types: [androidx.compose.ui.Modifier$Node] */
                /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Object] */
                /* JADX WARN: Type inference failed for: r2v6 */
                /* JADX WARN: Type inference failed for: r2v7 */
                /* JADX WARN: Type inference failed for: r2v8 */
                /* JADX WARN: Type inference failed for: r2v9 */
                /* JADX WARN: Type inference failed for: r3v0 */
                /* JADX WARN: Type inference failed for: r3v1 */
                /* JADX WARN: Type inference failed for: r3v10 */
                /* JADX WARN: Type inference failed for: r3v11 */
                /* JADX WARN: Type inference failed for: r3v2 */
                /* JADX WARN: Type inference failed for: r3v3, types: [androidx.compose.runtime.collection.MutableVector] */
                /* JADX WARN: Type inference failed for: r3v4 */
                /* JADX WARN: Type inference failed for: r3v5 */
                /* JADX WARN: Type inference failed for: r3v6, types: [androidx.compose.runtime.collection.MutableVector] */
                /* JADX WARN: Type inference failed for: r3v8 */
                /* JADX WARN: Type inference failed for: r3v9 */
                /* JADX WARN: Type inference failed for: r4v7, types: [T, androidx.compose.ui.semantics.SemanticsConfiguration] */
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    NodeChain nodeChain = this.this$0.nodes;
                    Ref$ObjectRef<SemanticsConfiguration> ref$ObjectRef2 = ref$ObjectRef;
                    if ((nodeChain.head.aggregateChildKindSet & 8) != 0) {
                        for (Modifier.Node node = nodeChain.tail; node != null; node = node.parent) {
                            if ((node.kindSet & 8) != 0) {
                                DelegatingNode delegatingNodeAccess$pop = node;
                                ?? mutableVector = 0;
                                while (delegatingNodeAccess$pop != 0) {
                                    if (delegatingNodeAccess$pop instanceof SemanticsModifierNode) {
                                        SemanticsModifierNode semanticsModifierNode = (SemanticsModifierNode) delegatingNodeAccess$pop;
                                        if (semanticsModifierNode.getShouldClearDescendantSemantics()) {
                                            ?? semanticsConfiguration = new SemanticsConfiguration();
                                            ref$ObjectRef2.element = semanticsConfiguration;
                                            semanticsConfiguration.isClearingSemantics = true;
                                        }
                                        if (semanticsModifierNode.getShouldMergeDescendantSemantics()) {
                                            ref$ObjectRef2.element.isMergingSemanticsOfDescendants = true;
                                        }
                                        semanticsModifierNode.applySemantics(ref$ObjectRef2.element);
                                    } else if ((delegatingNodeAccess$pop.kindSet & 8) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                        Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                        int i = 0;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                        while (node2 != null) {
                                            if ((node2.kindSet & 8) != 0) {
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
                        }
                    }
                    return Unit.INSTANCE;
                }
            });
            this.isCurrentlyCalculatingSemanticsConfiguration = false;
            this._semanticsConfiguration = (SemanticsConfiguration) ref$ObjectRef.element;
        }
        return this._semanticsConfiguration;
    }

    public final MutableVector getZSortedChildren() {
        boolean z = this.zSortedChildrenInvalidated;
        MutableVector mutableVector = this._zSortedChildren;
        if (z) {
            mutableVector.clear();
            mutableVector.addAll(mutableVector.size, get_children$ui_release());
            mutableVector.sortWith(ZComparator);
            this.zSortedChildrenInvalidated = false;
        }
        return mutableVector;
    }

    public final MutableVector get_children$ui_release() {
        updateChildrenIfDirty$ui_release();
        if (this.virtualChildrenCount == 0) {
            return this._foldedChildren.vector;
        }
        MutableVector mutableVector = this._unfoldedChildren;
        mutableVector.getClass();
        return mutableVector;
    }

    /* renamed from: hitTest-6fMxITs$ui_release, reason: not valid java name */
    public final void m642hitTest6fMxITs$ui_release(long j, HitTestResult hitTestResult, int i, boolean z) {
        NodeChain nodeChain = this.nodes;
        NodeCoordinator nodeCoordinator = nodeChain.outerCoordinator;
        NodeCoordinator.Companion companion = NodeCoordinator.Companion;
        long jM669fromParentPosition8S9VItk = nodeCoordinator.m669fromParentPosition8S9VItk(j, true);
        NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator;
        NodeCoordinator.Companion.getClass();
        nodeCoordinator2.m674hitTestqzLsGqo(NodeCoordinator.PointerInputSource, jM669fromParentPosition8S9VItk, hitTestResult, i, z);
    }

    /* renamed from: hitTestSemantics-6fMxITs$ui_release, reason: not valid java name */
    public final void m643hitTestSemantics6fMxITs$ui_release(long j, HitTestResult hitTestResult, boolean z) {
        NodeChain nodeChain = this.nodes;
        NodeCoordinator nodeCoordinator = nodeChain.outerCoordinator;
        NodeCoordinator.Companion companion = NodeCoordinator.Companion;
        long jM669fromParentPosition8S9VItk = nodeCoordinator.m669fromParentPosition8S9VItk(j, true);
        NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator;
        NodeCoordinator.Companion.getClass();
        NodeCoordinator$Companion$SemanticsSource$1 nodeCoordinator$Companion$SemanticsSource$1 = NodeCoordinator.SemanticsSource;
        PointerType.Companion.getClass();
        nodeCoordinator2.m674hitTestqzLsGqo(nodeCoordinator$Companion$SemanticsSource$1, jM669fromParentPosition8S9VItk, hitTestResult, PointerType.Touch, z);
    }

    public final void insertAt$ui_release(int i, LayoutNode layoutNode) {
        if (layoutNode._foldedParent != null && layoutNode.owner != null) {
            InlineClassHelperKt.throwIllegalStateException(exceptionMessageForParentingOrOwnership(layoutNode));
        }
        layoutNode._foldedParent = this;
        MutableVectorWithMutationTracking mutableVectorWithMutationTracking = this._foldedChildren;
        mutableVectorWithMutationTracking.vector.add(i, layoutNode);
        mutableVectorWithMutationTracking.onVectorMutated.invoke();
        onZSortedChildrenInvalidated$ui_release();
        if (layoutNode.isVirtual) {
            this.virtualChildrenCount++;
        }
        invalidateUnfoldedVirtualChildren();
        AndroidComposeView androidComposeView = this.owner;
        if (androidComposeView != null) {
            layoutNode.attach$ui_release(androidComposeView);
        }
        if (layoutNode.layoutDelegate.childrenAccessingCoordinatesDuringPlacement > 0) {
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutDelegate;
            layoutNodeLayoutDelegate.setChildrenAccessingCoordinatesDuringPlacement(layoutNodeLayoutDelegate.childrenAccessingCoordinatesDuringPlacement + 1);
        }
    }

    public final void invalidateLayer$ui_release() {
        if (this.innerLayerCoordinatorIsDirty) {
            NodeChain nodeChain = this.nodes;
            NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator;
            NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator.wrappedBy;
            this._innerLayerCoordinator = null;
            while (true) {
                if (Intrinsics.areEqual(nodeCoordinator, nodeCoordinator2)) {
                    break;
                }
                if ((nodeCoordinator != null ? nodeCoordinator.layer : null) != null) {
                    this._innerLayerCoordinator = nodeCoordinator;
                    break;
                }
                nodeCoordinator = nodeCoordinator != null ? nodeCoordinator.wrappedBy : null;
            }
        }
        NodeCoordinator nodeCoordinator3 = this._innerLayerCoordinator;
        if (nodeCoordinator3 != null && nodeCoordinator3.layer == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("layer was not set");
        }
        if (nodeCoordinator3 != null) {
            nodeCoordinator3.invalidateLayer();
            return;
        }
        LayoutNode parent$ui_release = getParent$ui_release();
        if (parent$ui_release != null) {
            parent$ui_release.invalidateLayer$ui_release();
        }
    }

    public final void invalidateLayers$ui_release() {
        NodeChain nodeChain = this.nodes;
        InnerNodeCoordinator innerNodeCoordinator = nodeChain.innerCoordinator;
        for (NodeCoordinator nodeCoordinator = nodeChain.outerCoordinator; nodeCoordinator != innerNodeCoordinator; nodeCoordinator = nodeCoordinator.wrapped) {
            OwnedLayer ownedLayer = ((LayoutModifierNodeCoordinator) nodeCoordinator).layer;
            if (ownedLayer != null) {
                ownedLayer.invalidate();
            }
        }
        OwnedLayer ownedLayer2 = nodeChain.innerCoordinator.layer;
        if (ownedLayer2 != null) {
            ownedLayer2.invalidate();
        }
    }

    public final void invalidateMeasurements$ui_release() {
        if (this.isVirtual) {
            LayoutNode parent$ui_release = getParent$ui_release();
            if (parent$ui_release != null) {
                parent$ui_release.invalidateMeasurements$ui_release();
                return;
            }
            return;
        }
        this.outerToInnerOffsetDirty = true;
        if (this.lookaheadRoot != null) {
            requestLookaheadRemeasure$ui_release$default(this, false, 7);
        } else {
            requestRemeasure$ui_release$default(this, false, 7);
        }
    }

    public final void invalidateSemantics$ui_release() {
        if (this.isCurrentlyCalculatingSemanticsConfiguration) {
            return;
        }
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        this._semanticsConfiguration = null;
        ((AndroidComposeView) LayoutNodeKt.requireOwner(this)).onSemanticsChange();
    }

    public final void invalidateUnfoldedVirtualChildren() {
        LayoutNode layoutNode;
        if (this.virtualChildrenCount > 0) {
            this.unfoldedVirtualChildrenListDirty = true;
        }
        if (!this.isVirtual || (layoutNode = this._foldedParent) == null) {
            return;
        }
        layoutNode.invalidateUnfoldedVirtualChildren();
    }

    public final boolean isAttached() {
        return this.owner != null;
    }

    public final boolean isPlaced() {
        return this.layoutDelegate.measurePassDelegate.isPlaced;
    }

    public final Boolean isPlacedInLookahead() {
        LookaheadPassDelegate lookaheadPassDelegate = this.layoutDelegate.lookaheadPassDelegate;
        if (lookaheadPassDelegate != null) {
            return Boolean.valueOf(lookaheadPassDelegate.isPlaced());
        }
        return null;
    }

    @Override // androidx.compose.ui.node.OwnerScope
    public final boolean isValidOwnerScope() {
        return isAttached();
    }

    public final void lookaheadReplace$ui_release() {
        LayoutNode parent$ui_release;
        if (this.intrinsicsUsageByParent == UsageByParent.NotUsed) {
            clearSubtreePlacementIntrinsicsUsage();
        }
        LookaheadPassDelegate lookaheadPassDelegate = this.layoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        try {
            lookaheadPassDelegate.relayoutWithoutParentInProgress = true;
            if (!lookaheadPassDelegate.placedOnce) {
                InlineClassHelperKt.throwIllegalStateException("replace() called on item that was not placed");
            }
            lookaheadPassDelegate.onNodePlacedCalled = false;
            boolean zIsPlaced = lookaheadPassDelegate.isPlaced();
            lookaheadPassDelegate.m655placeSelfMLgxB_4$1(lookaheadPassDelegate.lastPosition, lookaheadPassDelegate.lastExplicitLayer, lookaheadPassDelegate.lastLayerBlock);
            if (zIsPlaced && !lookaheadPassDelegate.onNodePlacedCalled && (parent$ui_release = lookaheadPassDelegate.layoutNodeLayoutDelegate.layoutNode.getParent$ui_release()) != null) {
                parent$ui_release.requestLookaheadRelayout$ui_release(false);
            }
            lookaheadPassDelegate.relayoutWithoutParentInProgress = false;
        } catch (Throwable th) {
            lookaheadPassDelegate.relayoutWithoutParentInProgress = false;
            throw th;
        }
    }

    public final void move$ui_release(int i, int i2, int i3) {
        if (i == i2) {
            return;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i > i2 ? i + i4 : i;
            int i6 = i > i2 ? i2 + i4 : (i2 + i3) - 2;
            MutableVectorWithMutationTracking mutableVectorWithMutationTracking = this._foldedChildren;
            Object objRemoveAt = mutableVectorWithMutationTracking.vector.removeAt(i5);
            Function0 function0 = mutableVectorWithMutationTracking.onVectorMutated;
            function0.invoke();
            mutableVectorWithMutationTracking.vector.add(i6, (LayoutNode) objRemoveAt);
            function0.invoke();
        }
        onZSortedChildrenInvalidated$ui_release();
        invalidateUnfoldedVirtualChildren();
        invalidateMeasurements$ui_release();
    }

    public final void onChildRemoved(LayoutNode layoutNode) {
        if (layoutNode.layoutDelegate.childrenAccessingCoordinatesDuringPlacement > 0) {
            this.layoutDelegate.setChildrenAccessingCoordinatesDuringPlacement(r0.childrenAccessingCoordinatesDuringPlacement - 1);
        }
        if (this.owner != null) {
            layoutNode.detach$ui_release();
        }
        layoutNode._foldedParent = null;
        layoutNode.nodes.outerCoordinator.wrappedBy = null;
        if (layoutNode.isVirtual) {
            this.virtualChildrenCount--;
            MutableVector mutableVector = layoutNode._foldedChildren.vector;
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                ((LayoutNode) objArr[i2]).nodes.outerCoordinator.wrappedBy = null;
            }
        }
        invalidateUnfoldedVirtualChildren();
        onZSortedChildrenInvalidated$ui_release();
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onDeactivate() {
        AndroidViewHolder androidViewHolder = this.interopViewFactoryHolder;
        if (androidViewHolder != null) {
            androidViewHolder.onDeactivate();
        }
        LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = this.subcompositionsState;
        if (layoutNodeSubcompositionsState != null) {
            layoutNodeSubcompositionsState.markActiveNodesAsReused(true);
        }
        this.isDeactivated = true;
        NodeChain nodeChain = this.nodes;
        for (Modifier.Node node = nodeChain.tail; node != null; node = node.parent) {
            if (node.isAttached) {
                node.reset$ui_release();
            }
        }
        nodeChain.runDetachLifecycle$ui_release();
        for (Modifier.Node node2 = nodeChain.tail; node2 != null; node2 = node2.parent) {
            if (node2.isAttached) {
                node2.markAsDetached$ui_release();
            }
        }
        if (isAttached()) {
            boolean z = ComposeUiFlags.isRectTrackingEnabled;
            invalidateSemantics$ui_release();
        }
        AndroidComposeView androidComposeView = this.owner;
        if (androidComposeView == null || !ComposeUiFlags.isRectTrackingEnabled) {
            return;
        }
        androidComposeView.rectManager.remove(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v2, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    @Override // androidx.compose.ui.node.Owner.OnLayoutCompletedListener
    public final void onLayoutComplete() {
        Modifier.Node node;
        NodeChain nodeChain = this.nodes;
        InnerNodeCoordinator innerNodeCoordinator = nodeChain.innerCoordinator;
        boolean zM683getIncludeSelfInTraversalH91voCI = NodeKindKt.m683getIncludeSelfInTraversalH91voCI(128);
        if (zM683getIncludeSelfInTraversalH91voCI) {
            node = innerNodeCoordinator.tail;
        } else {
            node = innerNodeCoordinator.tail.parent;
            if (node == null) {
                return;
            }
        }
        NodeCoordinator.Companion companion = NodeCoordinator.Companion;
        for (Modifier.Node nodeHeadNode = innerNodeCoordinator.headNode(zM683getIncludeSelfInTraversalH91voCI); nodeHeadNode != null && (nodeHeadNode.aggregateChildKindSet & 128) != 0; nodeHeadNode = nodeHeadNode.child) {
            if ((nodeHeadNode.kindSet & 128) != 0) {
                DelegatingNode delegatingNodeAccess$pop = nodeHeadNode;
                ?? mutableVector = 0;
                while (delegatingNodeAccess$pop != 0) {
                    if (delegatingNodeAccess$pop instanceof LayoutAwareModifierNode) {
                        ((LayoutAwareModifierNode) delegatingNodeAccess$pop).onPlaced(nodeChain.innerCoordinator);
                    } else if ((delegatingNodeAccess$pop.kindSet & 128) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                        Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                        int i = 0;
                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                        mutableVector = mutableVector;
                        while (node2 != null) {
                            if ((node2.kindSet & 128) != 0) {
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
            if (nodeHeadNode == node) {
                return;
            }
        }
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onRelease() {
        AndroidViewHolder androidViewHolder = this.interopViewFactoryHolder;
        if (androidViewHolder != null) {
            androidViewHolder.onRelease();
        }
        LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = this.subcompositionsState;
        if (layoutNodeSubcompositionsState != null) {
            layoutNodeSubcompositionsState.onRelease();
        }
        NodeChain nodeChain = this.nodes;
        NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator.wrapped;
        for (NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator; !Intrinsics.areEqual(nodeCoordinator2, nodeCoordinator) && nodeCoordinator2 != null; nodeCoordinator2 = nodeCoordinator2.wrapped) {
            nodeCoordinator2.released = true;
            ((NodeCoordinator$invalidateParentLayer$1) nodeCoordinator2.invalidateParentLayer).invoke();
            if (nodeCoordinator2.layer != null) {
                if (nodeCoordinator2.explicitLayer != null) {
                    nodeCoordinator2.explicitLayer = null;
                }
                nodeCoordinator2.updateLayerBlock(null, false);
                nodeCoordinator2.layoutNode.requestRelayout$ui_release(false);
            }
        }
    }

    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onReuse() {
        if (!isAttached()) {
            InlineClassHelperKt.throwIllegalArgumentException("onReuse is only expected on attached node");
        }
        AndroidViewHolder androidViewHolder = this.interopViewFactoryHolder;
        if (androidViewHolder != null) {
            androidViewHolder.onReuse();
        }
        LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = this.subcompositionsState;
        if (layoutNodeSubcompositionsState != null) {
            layoutNodeSubcompositionsState.markActiveNodesAsReused(false);
        }
        this.isCurrentlyCalculatingSemanticsConfiguration = false;
        boolean z = this.isDeactivated;
        NodeChain nodeChain = this.nodes;
        if (z) {
            this.isDeactivated = false;
            boolean z2 = ComposeUiFlags.isRectTrackingEnabled;
            invalidateSemantics$ui_release();
        } else {
            for (Modifier.Node node = nodeChain.tail; node != null; node = node.parent) {
                if (node.isAttached) {
                    node.reset$ui_release();
                }
            }
            nodeChain.runDetachLifecycle$ui_release();
            for (Modifier.Node node2 = nodeChain.tail; node2 != null; node2 = node2.parent) {
                if (node2.isAttached) {
                    node2.markAsDetached$ui_release();
                }
            }
        }
        int i = this.semanticsId;
        this.semanticsId = SemanticsModifierKt.lastIdentifier.addAndGet(1);
        AndroidComposeView androidComposeView = this.owner;
        if (androidComposeView != null) {
            androidComposeView.layoutNodes.remove(i);
            androidComposeView.layoutNodes.set(this.semanticsId, this);
        }
        for (Modifier.Node node3 = nodeChain.head; node3 != null; node3 = node3.child) {
            node3.markAsAttached$ui_release();
        }
        nodeChain.runAttachLifecycle();
        boolean z3 = ComposeUiFlags.isRectTrackingEnabled;
        rescheduleRemeasureOrRelayout$ui_release(this);
    }

    public final void onZSortedChildrenInvalidated$ui_release() {
        if (!this.isVirtual) {
            this.zSortedChildrenInvalidated = true;
            return;
        }
        LayoutNode parent$ui_release = getParent$ui_release();
        if (parent$ui_release != null) {
            parent$ui_release.onZSortedChildrenInvalidated$ui_release();
        }
    }

    /* renamed from: remeasure-_Sx5XlM$ui_release, reason: not valid java name */
    public final boolean m644remeasure_Sx5XlM$ui_release(Constraints constraints) {
        if (constraints == null) {
            return false;
        }
        if (this.intrinsicsUsageByParent == UsageByParent.NotUsed) {
            clearSubtreeIntrinsicsUsage$ui_release();
        }
        return this.layoutDelegate.measurePassDelegate.m664remeasureBRTryo0(constraints.value);
    }

    public final void removeAll$ui_release() {
        MutableVectorWithMutationTracking mutableVectorWithMutationTracking = this._foldedChildren;
        int i = mutableVectorWithMutationTracking.vector.size;
        while (true) {
            i--;
            if (-1 >= i) {
                mutableVectorWithMutationTracking.vector.clear();
                mutableVectorWithMutationTracking.onVectorMutated.invoke();
                return;
            }
            onChildRemoved((LayoutNode) mutableVectorWithMutationTracking.vector.content[i]);
        }
    }

    public final void removeAt$ui_release(int i, int i2) {
        if (i2 < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("count (" + i2 + ") must be greater than 0");
        }
        int i3 = (i2 + i) - 1;
        if (i > i3) {
            return;
        }
        while (true) {
            MutableVectorWithMutationTracking mutableVectorWithMutationTracking = this._foldedChildren;
            onChildRemoved((LayoutNode) mutableVectorWithMutationTracking.vector.content[i3]);
            Object objRemoveAt = mutableVectorWithMutationTracking.vector.removeAt(i3);
            mutableVectorWithMutationTracking.onVectorMutated.invoke();
            if (i3 == i) {
                return;
            } else {
                i3--;
            }
        }
    }

    public final void replace$ui_release() {
        LayoutNode parent$ui_release;
        if (this.intrinsicsUsageByParent == UsageByParent.NotUsed) {
            clearSubtreePlacementIntrinsicsUsage();
        }
        MeasurePassDelegate measurePassDelegate = this.layoutDelegate.measurePassDelegate;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = measurePassDelegate.layoutNodeLayoutDelegate;
        try {
            measurePassDelegate.relayoutWithoutParentInProgress = true;
            if (!measurePassDelegate.placedOnce) {
                InlineClassHelperKt.throwIllegalStateException("replace called on unplaced item");
            }
            boolean z = measurePassDelegate.isPlaced;
            measurePassDelegate.m662placeOuterCoordinatorMLgxB_4(measurePassDelegate.lastPosition, measurePassDelegate.lastZIndex, measurePassDelegate.lastLayerBlock, measurePassDelegate.lastExplicitLayer);
            if (z && !measurePassDelegate.onNodePlacedCalled && (parent$ui_release = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release()) != null) {
                parent$ui_release.requestRelayout$ui_release(false);
            }
        } finally {
        }
    }

    public final void requestLookaheadRelayout$ui_release(boolean z) {
        AndroidComposeView androidComposeView;
        if (this.isVirtual || (androidComposeView = this.owner) == null) {
            return;
        }
        androidComposeView.onRequestRelayout(this, true, z);
    }

    public final void requestRelayout$ui_release(boolean z) {
        AndroidComposeView androidComposeView;
        this.outerToInnerOffsetDirty = true;
        if (this.isVirtual || (androidComposeView = this.owner) == null) {
            return;
        }
        Owner.Companion companion = Owner.Companion;
        androidComposeView.onRequestRelayout(this, false, z);
    }

    public final void resetSubtreeIntrinsicsUsage$ui_release() {
        MutableVector mutableVector = get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i2];
            UsageByParent usageByParent = layoutNode.previousIntrinsicsUsageByParent;
            layoutNode.intrinsicsUsageByParent = usageByParent;
            if (usageByParent != UsageByParent.NotUsed) {
                layoutNode.resetSubtreeIntrinsicsUsage$ui_release();
            }
        }
    }

    public final void rethrowWithComposeStackTrace(Throwable th) throws Throwable {
        CompositionLocalMap compositionLocalMap = this.compositionLocalMap;
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionErrorContextKt.LocalCompositionErrorContext;
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMap = (PersistentCompositionLocalHashMap) compositionLocalMap;
        persistentCompositionLocalHashMap.getClass();
        CompositionErrorContext compositionErrorContext = (CompositionErrorContext) CompositionLocalMapKt.read(persistentCompositionLocalHashMap, staticProvidableCompositionLocal);
        if (compositionErrorContext == null) {
            throw th;
        }
        ((CompositionErrorContextImpl) compositionErrorContext).attachComposeStackTrace(this, th);
        throw th;
    }

    public final void setDensity$1(Density density) {
        if (Intrinsics.areEqual(this.density, density)) {
            return;
        }
        this.density = density;
        invalidateMeasurements$ui_release();
        LayoutNode parent$ui_release = getParent$ui_release();
        if (parent$ui_release != null) {
            parent$ui_release.invalidateLayer$ui_release();
        }
        invalidateLayers$ui_release();
        for (Modifier.Node node = this.nodes.head; node != null; node = node.child) {
            node.onDensityChange();
        }
    }

    public final void setLookaheadRoot(LayoutNode layoutNode) {
        if (Intrinsics.areEqual(layoutNode, this.lookaheadRoot)) {
            return;
        }
        this.lookaheadRoot = layoutNode;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutDelegate;
        if (layoutNode != null) {
            if (layoutNodeLayoutDelegate.lookaheadPassDelegate == null) {
                layoutNodeLayoutDelegate.lookaheadPassDelegate = new LookaheadPassDelegate(layoutNodeLayoutDelegate);
            }
            NodeChain nodeChain = this.nodes;
            NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator.wrapped;
            for (NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator; !Intrinsics.areEqual(nodeCoordinator2, nodeCoordinator) && nodeCoordinator2 != null; nodeCoordinator2 = nodeCoordinator2.wrapped) {
                nodeCoordinator2.ensureLookaheadDelegateCreated();
            }
        } else {
            layoutNodeLayoutDelegate.lookaheadPassDelegate = null;
            layoutNodeLayoutDelegate.lookaheadLayoutPending = false;
            layoutNodeLayoutDelegate.lookaheadMeasurePending = false;
        }
        invalidateMeasurements$ui_release();
    }

    public final void setMeasurePolicy(MeasurePolicy measurePolicy) {
        if (Intrinsics.areEqual(this.measurePolicy, measurePolicy)) {
            return;
        }
        this.measurePolicy = measurePolicy;
        IntrinsicsPolicy intrinsicsPolicy = this.intrinsicsPolicy;
        if (intrinsicsPolicy != null) {
            ((SnapshotMutableStateImpl) intrinsicsPolicy.measurePolicyState$delegate).setValue(measurePolicy);
        }
        invalidateMeasurements$ui_release();
    }

    public final void setModifier(Modifier modifier) {
        if (this.isVirtual && this._modifier != Modifier.Companion) {
            InlineClassHelperKt.throwIllegalArgumentException("Modifiers are not supported on virtual LayoutNodes");
        }
        if (this.isDeactivated) {
            InlineClassHelperKt.throwIllegalArgumentException("modifier is updated when deactivated");
        }
        if (!isAttached()) {
            this.pendingModifier = modifier;
            return;
        }
        applyModifier(modifier);
        if (this.isSemanticsInvalidated) {
            invalidateSemantics$ui_release();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public final void setViewConfiguration(ViewConfiguration viewConfiguration) {
        if (Intrinsics.areEqual(this.viewConfiguration, viewConfiguration)) {
            return;
        }
        this.viewConfiguration = viewConfiguration;
        Modifier.Node node = this.nodes.head;
        if ((node.aggregateChildKindSet & 16) != 0) {
            while (node != null) {
                if ((node.kindSet & 16) != 0) {
                    DelegatingNode delegatingNodeAccess$pop = node;
                    ?? mutableVector = 0;
                    while (delegatingNodeAccess$pop != 0) {
                        if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                            ((PointerInputModifierNode) delegatingNodeAccess$pop).onViewConfigurationChange();
                        } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                            Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                            int i = 0;
                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                            mutableVector = mutableVector;
                            while (node2 != null) {
                                if ((node2.kindSet & 16) != 0) {
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
                if ((node.aggregateChildKindSet & 16) == 0) {
                    return;
                } else {
                    node = node.child;
                }
            }
        }
    }

    public final String toString() {
        return JvmActuals_jvmKt.simpleIdentityToString(this) + " children: " + getChildren$ui_release().size() + " measurePolicy: " + this.measurePolicy;
    }

    public final void updateChildrenIfDirty$ui_release() {
        if (this.virtualChildrenCount <= 0 || !this.unfoldedVirtualChildrenListDirty) {
            return;
        }
        this.unfoldedVirtualChildrenListDirty = false;
        MutableVector mutableVector = this._unfoldedChildren;
        if (mutableVector == null) {
            mutableVector = new MutableVector(new LayoutNode[16], 0);
            this._unfoldedChildren = mutableVector;
        }
        mutableVector.clear();
        MutableVector mutableVector2 = this._foldedChildren.vector;
        Object[] objArr = mutableVector2.content;
        int i = mutableVector2.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i2];
            if (layoutNode.isVirtual) {
                mutableVector.addAll(mutableVector.size, layoutNode.get_children$ui_release());
            } else {
                mutableVector.add(layoutNode);
            }
        }
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutDelegate;
        layoutNodeLayoutDelegate.measurePassDelegate.childDelegatesDirty = true;
        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        if (lookaheadPassDelegate != null) {
            lookaheadPassDelegate.childDelegatesDirty = true;
        }
    }

    public LayoutNode(boolean z, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? SemanticsModifierKt.lastIdentifier.addAndGet(1) : i);
    }

    public LayoutNode(boolean z, int i) {
        this.isVirtual = z;
        this.semanticsId = i;
        IntOffset.Companion.getClass();
        long j = IntOffset.Max;
        this.offsetFromRoot = j;
        IntSize.Companion.getClass();
        this.lastSize = 0L;
        this.outerToInnerOffset = j;
        this.outerToInnerOffsetDirty = true;
        this._foldedChildren = new MutableVectorWithMutationTracking(new MutableVector(new LayoutNode[16], 0), new Function0() { // from class: androidx.compose.ui.node.LayoutNode$_foldedChildren$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.this$0.layoutDelegate;
                layoutNodeLayoutDelegate.measurePassDelegate.childDelegatesDirty = true;
                LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                if (lookaheadPassDelegate != null) {
                    lookaheadPassDelegate.childDelegatesDirty = true;
                }
                return Unit.INSTANCE;
            }
        });
        this._zSortedChildren = new MutableVector(new LayoutNode[16], 0);
        this.zSortedChildrenInvalidated = true;
        this.measurePolicy = ErrorMeasurePolicy;
        this.density = LayoutNodeKt.DefaultDensity;
        this.layoutDirection = LayoutDirection.Ltr;
        this.viewConfiguration = DummyViewConfiguration;
        CompositionLocalMap.Companion.getClass();
        this.compositionLocalMap = CompositionLocalMap.Companion.Empty;
        UsageByParent usageByParent = UsageByParent.NotUsed;
        this.intrinsicsUsageByParent = usageByParent;
        this.previousIntrinsicsUsageByParent = usageByParent;
        this.nodes = new NodeChain(this);
        this.layoutDelegate = new LayoutNodeLayoutDelegate(this);
        this.innerLayerCoordinatorIsDirty = true;
        this._modifier = Modifier.Companion;
    }
}
