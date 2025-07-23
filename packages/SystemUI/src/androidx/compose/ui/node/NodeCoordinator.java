package androidx.compose.ui.node;

import androidx.collection.MutableLongList;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.collection.internal.RuntimeHelpersKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsLayerScopeKt;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.LookaheadLayoutCoordinates;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.TouchBoundsExpansion;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NodeCoordinator extends LookaheadCapablePlaceable implements Measurable, LayoutCoordinates, OwnerScope {
    public Function2 _drawBlock;
    public MeasureResult _measureResult;
    public MutableRect _rectCache;
    public Canvas drawBlockCanvas;
    public GraphicsLayer drawBlockParentLayer;
    public GraphicsLayer explicitLayer;
    public boolean forceMeasureWithLookaheadConstraints;
    public boolean forcePlaceWithLookaheadOffset;
    public final Function0 invalidateParentLayer;
    public boolean isClipping;
    public float lastLayerAlpha = 0.8f;
    public boolean lastLayerDrawingWasSkipped;
    public OwnedLayer layer;
    public Function1 layerBlock;
    public Density layerDensity;
    public LayoutDirection layerLayoutDirection;
    public LayerPositionalProperties layerPositionalProperties;
    public final LayoutNode layoutNode;
    public MutableObjectIntMap oldAlignmentLines;
    public long position;
    public boolean released;
    public NodeCoordinator wrapped;
    public NodeCoordinator wrappedBy;
    public float zIndex;
    public static final Companion Companion = new Companion(null);
    public static final Function1 onCommitAffectingLayerParams = new Function1() { // from class: androidx.compose.ui.node.NodeCoordinator$Companion$onCommitAffectingLayerParams$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            NodeCoordinator nodeCoordinator = (NodeCoordinator) obj;
            if (nodeCoordinator.isValidOwnerScope() && nodeCoordinator.updateLayerParameters(true)) {
                LayoutNode layoutNode = nodeCoordinator.layoutNode;
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
                if (layoutNodeLayoutDelegate.childrenAccessingCoordinatesDuringPlacement > 0) {
                    if (layoutNodeLayoutDelegate.coordinatesAccessedDuringModifierPlacement || layoutNodeLayoutDelegate.coordinatesAccessedDuringPlacement) {
                        layoutNode.requestRelayout$ui_release(false);
                    }
                    layoutNodeLayoutDelegate.measurePassDelegate.notifyChildrenUsingCoordinatesWhilePlacing();
                }
                AndroidComposeView androidComposeView = (AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode);
                androidComposeView.rectManager.onLayoutLayerPositionalPropertiesChanged(layoutNode);
                androidComposeView.measureAndLayoutDelegate.onPositionedDispatcher.layoutNodes.add(layoutNode);
                layoutNode.needsOnPositionedDispatch = true;
                androidComposeView.scheduleMeasureAndLayout(null);
            }
            return Unit.INSTANCE;
        }
    };
    public static final Function1 onCommitAffectingLayer = new Function1() { // from class: androidx.compose.ui.node.NodeCoordinator$Companion$onCommitAffectingLayer$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            OwnedLayer ownedLayer = ((NodeCoordinator) obj).layer;
            if (ownedLayer != null) {
                ownedLayer.invalidate();
            }
            return Unit.INSTANCE;
        }
    };
    public static final ReusableGraphicsLayerScope graphicsLayerScope = new ReusableGraphicsLayerScope();
    public static final LayerPositionalProperties tmpLayerPositionalProperties = new LayerPositionalProperties();
    public static final float[] tmpMatrix = Matrix.m481constructorimpl$default();
    public static final NodeCoordinator$Companion$PointerInputSource$1 PointerInputSource = new HitTestSource() { // from class: androidx.compose.ui.node.NodeCoordinator$Companion$PointerInputSource$1
        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        /* renamed from: childHitTest-qzLsGqo, reason: not valid java name */
        public final void mo678childHitTestqzLsGqo(LayoutNode layoutNode, long j, HitTestResult hitTestResult, int i, boolean z) {
            layoutNode.m640hitTest6fMxITs$ui_release(j, hitTestResult, i, z);
        }

        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        /* renamed from: entityType-OLwlOKw, reason: not valid java name */
        public final int mo679entityTypeOLwlOKw() {
            return 16;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0 */
        /* JADX WARN: Type inference failed for: r0v1 */
        /* JADX WARN: Type inference failed for: r0v10 */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v2 */
        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.runtime.collection.MutableVector] */
        /* JADX WARN: Type inference failed for: r0v4 */
        /* JADX WARN: Type inference failed for: r0v5 */
        /* JADX WARN: Type inference failed for: r0v6, types: [androidx.compose.runtime.collection.MutableVector] */
        /* JADX WARN: Type inference failed for: r0v8 */
        /* JADX WARN: Type inference failed for: r0v9 */
        /* JADX WARN: Type inference failed for: r8v0, types: [androidx.compose.ui.Modifier$Node] */
        /* JADX WARN: Type inference failed for: r8v1, types: [androidx.compose.ui.Modifier$Node] */
        /* JADX WARN: Type inference failed for: r8v10 */
        /* JADX WARN: Type inference failed for: r8v11 */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v4, types: [androidx.compose.ui.Modifier$Node] */
        /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r8v6 */
        /* JADX WARN: Type inference failed for: r8v7 */
        /* JADX WARN: Type inference failed for: r8v8 */
        /* JADX WARN: Type inference failed for: r8v9 */
        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        public final boolean interceptOutOfBoundsChildEvents(Modifier.Node node) {
            ?? r0 = 0;
            while (node != 0) {
                if (node instanceof PointerInputModifierNode) {
                    ((PointerInputModifierNode) node).interceptOutOfBoundsChildEvents();
                } else if ((node.kindSet & 16) != 0 && (node instanceof DelegatingNode)) {
                    Modifier.Node node2 = node.delegate;
                    int i = 0;
                    r0 = r0;
                    node = node;
                    while (node2 != null) {
                        if ((node2.kindSet & 16) != 0) {
                            i++;
                            r0 = r0;
                            if (i == 1) {
                                node = node2;
                            } else {
                                if (r0 == 0) {
                                    r0 = new MutableVector(new Modifier.Node[16], 0);
                                }
                                if (node != 0) {
                                    r0.add(node);
                                    node = 0;
                                }
                                r0.add(node2);
                            }
                        }
                        node2 = node2.child;
                        r0 = r0;
                        node = node;
                    }
                    if (i == 1) {
                    }
                }
                node = DelegatableNodeKt.access$pop(r0);
            }
            return false;
        }

        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        public final boolean shouldHitTestChildren(LayoutNode layoutNode) {
            return true;
        }
    };
    public static final NodeCoordinator$Companion$SemanticsSource$1 SemanticsSource = new HitTestSource() { // from class: androidx.compose.ui.node.NodeCoordinator$Companion$SemanticsSource$1
        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        /* renamed from: childHitTest-qzLsGqo */
        public final void mo678childHitTestqzLsGqo(LayoutNode layoutNode, long j, HitTestResult hitTestResult, int i, boolean z) {
            layoutNode.m641hitTestSemantics6fMxITs$ui_release(j, hitTestResult, z);
        }

        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        /* renamed from: entityType-OLwlOKw */
        public final int mo679entityTypeOLwlOKw() {
            return 8;
        }

        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        public final boolean interceptOutOfBoundsChildEvents(Modifier.Node node) {
            return false;
        }

        @Override // androidx.compose.ui.node.NodeCoordinator.HitTestSource
        public final boolean shouldHitTestChildren(LayoutNode layoutNode) {
            SemanticsConfiguration semanticsConfiguration = layoutNode.getSemanticsConfiguration();
            boolean z = false;
            if (semanticsConfiguration != null && semanticsConfiguration.isClearingSemantics) {
                z = true;
            }
            return !z;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface HitTestSource {
        /* renamed from: childHitTest-qzLsGqo */
        void mo678childHitTestqzLsGqo(LayoutNode layoutNode, long j, HitTestResult hitTestResult, int i, boolean z);

        /* renamed from: entityType-OLwlOKw */
        int mo679entityTypeOLwlOKw();

        boolean interceptOutOfBoundsChildEvents(Modifier.Node node);

        boolean shouldHitTestChildren(LayoutNode layoutNode);
    }

    public NodeCoordinator(LayoutNode layoutNode) {
        this.layoutNode = layoutNode;
        this.layerDensity = layoutNode.density;
        this.layerLayoutDirection = layoutNode.layoutDirection;
        IntOffset.Companion.getClass();
        this.position = 0L;
        this.invalidateParentLayer = new NodeCoordinator$invalidateParentLayer$1(this);
    }

    public static NodeCoordinator toCoordinator(LayoutCoordinates layoutCoordinates) {
        NodeCoordinator nodeCoordinator;
        LookaheadLayoutCoordinates lookaheadLayoutCoordinates = layoutCoordinates instanceof LookaheadLayoutCoordinates ? (LookaheadLayoutCoordinates) layoutCoordinates : null;
        return (lookaheadLayoutCoordinates == null || (nodeCoordinator = lookaheadLayoutCoordinates.lookaheadDelegate.coordinator) == null) ? (NodeCoordinator) layoutCoordinates : nodeCoordinator;
    }

    public final void ancestorToLocal(NodeCoordinator nodeCoordinator, MutableRect mutableRect, boolean z) {
        if (nodeCoordinator == this) {
            return;
        }
        NodeCoordinator nodeCoordinator2 = this.wrappedBy;
        if (nodeCoordinator2 != null) {
            nodeCoordinator2.ancestorToLocal(nodeCoordinator, mutableRect, z);
        }
        long j = this.position;
        IntOffset.Companion companion = IntOffset.Companion;
        float f = (int) (j >> 32);
        mutableRect.left -= f;
        mutableRect.right -= f;
        float f2 = (int) (j & 4294967295L);
        mutableRect.top -= f2;
        mutableRect.bottom -= f2;
        OwnedLayer ownedLayer = this.layer;
        if (ownedLayer != null) {
            ownedLayer.mapBounds(mutableRect, true);
            if (this.isClipping && z) {
                long j2 = this.measuredSize;
                mutableRect.intersect(0.0f, 0.0f, (int) (j2 >> 32), (int) (j2 & 4294967295L));
            }
        }
    }

    /* renamed from: ancestorToLocal-S_NoaFU, reason: not valid java name */
    public final long m664ancestorToLocalS_NoaFU(NodeCoordinator nodeCoordinator, long j, boolean z) {
        if (nodeCoordinator == this) {
            return j;
        }
        NodeCoordinator nodeCoordinator2 = this.wrappedBy;
        return (nodeCoordinator2 == null || Intrinsics.areEqual(nodeCoordinator, nodeCoordinator2)) ? m667fromParentPosition8S9VItk(j, z) : m667fromParentPosition8S9VItk(nodeCoordinator2.m664ancestorToLocalS_NoaFU(nodeCoordinator, j, z), z);
    }

    /* renamed from: calculateMinimumTouchTargetPadding-E7KxVPU, reason: not valid java name */
    public final long m665calculateMinimumTouchTargetPaddingE7KxVPU(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - getMeasuredWidth();
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - getMeasuredHeight();
        float max = Math.max(0.0f, intBitsToFloat / 2.0f);
        float max2 = Math.max(0.0f, intBitsToFloat2 / 2.0f);
        long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: distanceInMinimumTouchTarget-tz77jQw, reason: not valid java name */
    public final float m666distanceInMinimumTouchTargettz77jQw(long j, long j2) {
        if (getMeasuredWidth() >= Float.intBitsToFloat((int) (j2 >> 32)) && getMeasuredHeight() >= Float.intBitsToFloat((int) (j2 & 4294967295L))) {
            return Float.POSITIVE_INFINITY;
        }
        long m665calculateMinimumTouchTargetPaddingE7KxVPU = m665calculateMinimumTouchTargetPaddingE7KxVPU(j2);
        float intBitsToFloat = Float.intBitsToFloat((int) (m665calculateMinimumTouchTargetPaddingE7KxVPU >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (m665calculateMinimumTouchTargetPaddingE7KxVPU & 4294967295L));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (j >> 32));
        float max = Math.max(0.0f, intBitsToFloat3 < 0.0f ? -intBitsToFloat3 : intBitsToFloat3 - getMeasuredWidth());
        long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(Math.max(0.0f, Float.intBitsToFloat((int) (j & 4294967295L)) < 0.0f ? -r9 : r9 - getMeasuredHeight())) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        if (intBitsToFloat > 0.0f || intBitsToFloat2 > 0.0f) {
            int i = (int) (floatToRawIntBits >> 32);
            if (Float.intBitsToFloat(i) <= intBitsToFloat) {
                int i2 = (int) (floatToRawIntBits & 4294967295L);
                if (Float.intBitsToFloat(i2) <= intBitsToFloat2) {
                    float intBitsToFloat4 = Float.intBitsToFloat(i);
                    float intBitsToFloat5 = Float.intBitsToFloat(i2);
                    return (intBitsToFloat5 * intBitsToFloat5) + (intBitsToFloat4 * intBitsToFloat4);
                }
            }
        }
        return Float.POSITIVE_INFINITY;
    }

    public final void draw(Canvas canvas, GraphicsLayer graphicsLayer) {
        OwnedLayer ownedLayer = this.layer;
        if (ownedLayer != null) {
            ownedLayer.drawLayer(canvas, graphicsLayer);
            return;
        }
        long j = this.position;
        IntOffset.Companion companion = IntOffset.Companion;
        float f = (int) (j >> 32);
        float f2 = (int) (j & 4294967295L);
        canvas.translate(f, f2);
        drawContainedDrawModifiers(canvas, graphicsLayer);
        canvas.translate(-f, -f2);
    }

    public final void drawContainedDrawModifiers(Canvas canvas, GraphicsLayer graphicsLayer) {
        NodeCoordinator nodeCoordinator;
        Canvas canvas2;
        GraphicsLayer graphicsLayer2;
        Modifier.Node m669headH91voCI = m669headH91voCI(4);
        if (m669headH91voCI == null) {
            performDraw(canvas, graphicsLayer);
            return;
        }
        LayoutNode layoutNode = this.layoutNode;
        layoutNode.getClass();
        LayoutNodeDrawScope layoutNodeDrawScope = ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).sharedDrawScope;
        long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(this.measuredSize);
        layoutNodeDrawScope.getClass();
        MutableVector mutableVector = null;
        while (m669headH91voCI != null) {
            if (m669headH91voCI instanceof DrawModifierNode) {
                nodeCoordinator = this;
                canvas2 = canvas;
                graphicsLayer2 = graphicsLayer;
                layoutNodeDrawScope.m644drawDirecteZhPAX0$ui_release(canvas2, m864toSizeozmzZPI, nodeCoordinator, (DrawModifierNode) m669headH91voCI, graphicsLayer2);
            } else {
                nodeCoordinator = this;
                canvas2 = canvas;
                graphicsLayer2 = graphicsLayer;
                if ((m669headH91voCI.kindSet & 4) != 0 && (m669headH91voCI instanceof DelegatingNode)) {
                    int i = 0;
                    for (Modifier.Node node = ((DelegatingNode) m669headH91voCI).delegate; node != null; node = node.child) {
                        if ((node.kindSet & 4) != 0) {
                            i++;
                            if (i == 1) {
                                m669headH91voCI = node;
                            } else {
                                if (mutableVector == null) {
                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                }
                                if (m669headH91voCI != null) {
                                    mutableVector.add(m669headH91voCI);
                                    m669headH91voCI = null;
                                }
                                mutableVector.add(node);
                            }
                        }
                    }
                    if (i == 1) {
                        canvas = canvas2;
                        this = nodeCoordinator;
                        graphicsLayer = graphicsLayer2;
                    }
                }
            }
            m669headH91voCI = DelegatableNodeKt.access$pop(mutableVector);
            canvas = canvas2;
            this = nodeCoordinator;
            graphicsLayer = graphicsLayer2;
        }
    }

    public abstract void ensureLookaheadDelegateCreated();

    public final NodeCoordinator findCommonAncestor$ui_release(NodeCoordinator nodeCoordinator) {
        LayoutNode layoutNode = nodeCoordinator.layoutNode;
        LayoutNode layoutNode2 = this.layoutNode;
        if (layoutNode == layoutNode2) {
            Modifier.Node tail = nodeCoordinator.getTail();
            Modifier.Node tail2 = getTail();
            if (!tail2.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitLocalAncestors called on an unattached node");
            }
            for (Modifier.Node node = tail2.node.parent; node != null; node = node.parent) {
                if ((node.kindSet & 2) != 0 && node == tail) {
                    return nodeCoordinator;
                }
            }
            return this;
        }
        while (layoutNode.depth > layoutNode2.depth) {
            layoutNode = layoutNode.getParent$ui_release();
            layoutNode.getClass();
        }
        LayoutNode layoutNode3 = layoutNode2;
        while (layoutNode3.depth > layoutNode.depth) {
            layoutNode3 = layoutNode3.getParent$ui_release();
            layoutNode3.getClass();
        }
        while (layoutNode != layoutNode3) {
            layoutNode = layoutNode.getParent$ui_release();
            layoutNode3 = layoutNode3.getParent$ui_release();
            if (layoutNode == null || layoutNode3 == null) {
                throw new IllegalArgumentException("layouts are not part of the same hierarchy");
            }
        }
        if (layoutNode3 != layoutNode2) {
            if (layoutNode != nodeCoordinator.layoutNode) {
                return layoutNode.nodes.innerCoordinator;
            }
            return nodeCoordinator;
        }
        return this;
    }

    /* renamed from: fromParentPosition-8S9VItk, reason: not valid java name */
    public final long m667fromParentPosition8S9VItk(long j, boolean z) {
        if (z || !this.isPlacedUnderMotionFrameOfReference) {
            long j2 = this.position;
            float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
            IntOffset.Companion companion = IntOffset.Companion;
            j = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L)) - ((int) (j2 & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat - ((int) (j2 >> 32))) << 32);
            Offset.Companion companion2 = Offset.Companion;
        }
        OwnedLayer ownedLayer = this.layer;
        return ownedLayer != null ? ownedLayer.mo685mapOffset8S9VItk(j, true) : j;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LookaheadCapablePlaceable getChild() {
        return this.wrapped;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.layoutNode.density.getDensity();
    }

    public final Function2 getDrawBlock() {
        Function2 function2 = this._drawBlock;
        if (function2 != null) {
            return function2;
        }
        final Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.NodeCoordinator$drawBlock$drawBlockCallToDrawModifiers$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NodeCoordinator nodeCoordinator = NodeCoordinator.this;
                Canvas canvas = nodeCoordinator.drawBlockCanvas;
                canvas.getClass();
                nodeCoordinator.drawContainedDrawModifiers(canvas, NodeCoordinator.this.drawBlockParentLayer);
                return Unit.INSTANCE;
            }
        };
        Function2 function22 = new Function2() { // from class: androidx.compose.ui.node.NodeCoordinator$drawBlock$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Canvas canvas = (Canvas) obj;
                GraphicsLayer graphicsLayer = (GraphicsLayer) obj2;
                if (NodeCoordinator.this.layoutNode.isPlaced()) {
                    NodeCoordinator nodeCoordinator = NodeCoordinator.this;
                    nodeCoordinator.drawBlockCanvas = canvas;
                    nodeCoordinator.drawBlockParentLayer = graphicsLayer;
                    ((AndroidComposeView) LayoutNodeKt.requireOwner(nodeCoordinator.layoutNode)).snapshotObserver.observeReads$ui_release(NodeCoordinator.this, NodeCoordinator.onCommitAffectingLayer, function0);
                    NodeCoordinator.this.lastLayerDrawingWasSkipped = false;
                } else {
                    NodeCoordinator.this.lastLayerDrawingWasSkipped = true;
                }
                return Unit.INSTANCE;
            }
        };
        this._drawBlock = function22;
        return function22;
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.layoutNode.density.getFontScale();
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final boolean getHasMeasureResult() {
        return this._measureResult != null;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final LayoutDirection getLayoutDirection() {
        return this.layoutNode.layoutDirection;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.node.MeasureScopeWithLayoutNode
    public final LayoutNode getLayoutNode() {
        return this.layoutNode;
    }

    public abstract LookaheadDelegate getLookaheadDelegate();

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final MeasureResult getMeasureResult$ui_release() {
        MeasureResult measureResult = this._measureResult;
        if (measureResult != null) {
            return measureResult;
        }
        throw new IllegalStateException("Asking for measurement result of unmeasured layout modifier");
    }

    /* renamed from: getMinimumTouchTargetSize-NH-jbRc, reason: not valid java name */
    public final long m668getMinimumTouchTargetSizeNHjbRc() {
        return this.layerDensity.mo58toSizeXkaWNTQ(this.layoutNode.viewConfiguration.mo643getMinimumTouchTargetSizeMYxV2XQ());
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LookaheadCapablePlaceable getParent() {
        return this.wrappedBy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v5, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v9 */
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
    @Override // androidx.compose.ui.layout.Measured, androidx.compose.ui.layout.IntrinsicMeasurable
    public final Object getParentData() {
        LayoutNode layoutNode = this.layoutNode;
        if (!layoutNode.nodes.m663hasH91voCI$ui_release(64)) {
            return null;
        }
        getTail();
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        for (Modifier.Node node = layoutNode.nodes.tail; node != null; node = node.parent) {
            if ((node.kindSet & 64) != 0) {
                DelegatingNode delegatingNode = node;
                ?? r5 = 0;
                while (delegatingNode != 0) {
                    if (delegatingNode instanceof ParentDataModifierNode) {
                        ref$ObjectRef.element = ((ParentDataModifierNode) delegatingNode).modifyParentData(layoutNode.density, ref$ObjectRef.element);
                    } else if ((delegatingNode.kindSet & 64) != 0 && (delegatingNode instanceof DelegatingNode)) {
                        Modifier.Node node2 = delegatingNode.delegate;
                        int i = 0;
                        delegatingNode = delegatingNode;
                        r5 = r5;
                        while (node2 != null) {
                            if ((node2.kindSet & 64) != 0) {
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
        }
        return ref$ObjectRef.element;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final LayoutCoordinates getParentLayoutCoordinates$1() {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        onCoordinatesUsed$ui_release();
        return this.layoutNode.nodes.outerCoordinator.wrappedBy;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    /* renamed from: getPosition-nOcc-ac */
    public final long mo649getPositionnOccac() {
        return this.position;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: getSize-YbymL2g */
    public final long mo610getSizeYbymL2g() {
        return this.measuredSize;
    }

    public abstract Modifier.Node getTail();

    /* renamed from: head-H91voCI, reason: not valid java name */
    public final Modifier.Node m669headH91voCI(int i) {
        boolean m681getIncludeSelfInTraversalH91voCI = NodeKindKt.m681getIncludeSelfInTraversalH91voCI(i);
        Modifier.Node tail = getTail();
        if (!m681getIncludeSelfInTraversalH91voCI && (tail = tail.parent) == null) {
            return null;
        }
        for (Modifier.Node headNode = headNode(m681getIncludeSelfInTraversalH91voCI); headNode != null && (headNode.aggregateChildKindSet & i) != 0; headNode = headNode.child) {
            if ((headNode.kindSet & i) != 0) {
                return headNode;
            }
            if (headNode == tail) {
                return null;
            }
        }
        return null;
    }

    public final Modifier.Node headNode(boolean z) {
        Modifier.Node tail;
        NodeChain nodeChain = this.layoutNode.nodes;
        if (nodeChain.outerCoordinator == this) {
            return nodeChain.head;
        }
        if (!z) {
            NodeCoordinator nodeCoordinator = this.wrappedBy;
            if (nodeCoordinator != null) {
                return nodeCoordinator.getTail();
            }
            return null;
        }
        NodeCoordinator nodeCoordinator2 = this.wrappedBy;
        if (nodeCoordinator2 == null || (tail = nodeCoordinator2.getTail()) == null) {
            return null;
        }
        return tail.child;
    }

    /* renamed from: hit-5ShdDok, reason: not valid java name */
    public final void m670hit5ShdDok(Modifier.Node node, HitTestSource hitTestSource, long j, HitTestResult hitTestResult, int i, boolean z) {
        if (node == null) {
            mo638hitTestChildqzLsGqo(hitTestSource, j, hitTestResult, i, z);
            return;
        }
        int i2 = hitTestResult.hitDepth;
        hitTestResult.removeNodesInRange(i2 + 1, hitTestResult.values._size);
        hitTestResult.hitDepth++;
        hitTestResult.values.add(node);
        hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(-1.0f, z, false));
        m670hit5ShdDok(NodeCoordinatorKt.m680access$nextUntilhw7D004(node, hitTestSource.mo679entityTypeOLwlOKw()), hitTestSource, j, hitTestResult, i, z);
        hitTestResult.hitDepth = i2;
    }

    /* renamed from: hitNear-Fh5PU_I, reason: not valid java name */
    public final void m671hitNearFh5PU_I(Modifier.Node node, HitTestSource hitTestSource, long j, HitTestResult hitTestResult, int i, boolean z, float f) {
        if (node == null) {
            mo638hitTestChildqzLsGqo(hitTestSource, j, hitTestResult, i, z);
            return;
        }
        int i2 = hitTestResult.hitDepth;
        hitTestResult.removeNodesInRange(i2 + 1, hitTestResult.values._size);
        hitTestResult.hitDepth++;
        hitTestResult.values.add(node);
        hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(f, z, false));
        m673outOfBoundsHit8NAm7pk(NodeCoordinatorKt.m680access$nextUntilhw7D004(node, hitTestSource.mo679entityTypeOLwlOKw()), hitTestSource, j, hitTestResult, i, z, f, true);
        hitTestResult.hitDepth = i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d2, code lost:
    
        if (androidx.compose.ui.node.DistanceAndFlags.m633compareTo9YPOF3E(r18.m637findBestHitDistancefn2tFes(), androidx.compose.ui.node.HitTestResultKt.DistanceAndFlags(r2, r7, false)) > 0) goto L39;
     */
    /* renamed from: hitTest-qzLsGqo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m672hitTestqzLsGqo(androidx.compose.ui.node.NodeCoordinator.HitTestSource r15, long r16, androidx.compose.ui.node.HitTestResult r18, int r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.NodeCoordinator.m672hitTestqzLsGqo(androidx.compose.ui.node.NodeCoordinator$HitTestSource, long, androidx.compose.ui.node.HitTestResult, int, boolean):void");
    }

    /* renamed from: hitTestChild-qzLsGqo */
    public void mo638hitTestChildqzLsGqo(HitTestSource hitTestSource, long j, HitTestResult hitTestResult, int i, boolean z) {
        NodeCoordinator nodeCoordinator = this.wrapped;
        if (nodeCoordinator != null) {
            nodeCoordinator.m672hitTestqzLsGqo(hitTestSource, nodeCoordinator.m667fromParentPosition8S9VItk(j, true), hitTestResult, i, z);
        }
    }

    public final void invalidateLayer() {
        OwnedLayer ownedLayer = this.layer;
        if (ownedLayer != null) {
            ownedLayer.invalidate();
            return;
        }
        NodeCoordinator nodeCoordinator = this.wrappedBy;
        if (nodeCoordinator != null) {
            nodeCoordinator.invalidateLayer();
        }
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final boolean isAttached() {
        return getTail().isAttached;
    }

    public final boolean isTransparent() {
        if (this.layer != null && this.lastLayerAlpha <= 0.0f) {
            return true;
        }
        NodeCoordinator nodeCoordinator = this.wrappedBy;
        if (nodeCoordinator != null) {
            return nodeCoordinator.isTransparent();
        }
        return false;
    }

    @Override // androidx.compose.ui.node.OwnerScope
    public final boolean isValidOwnerScope() {
        return (this.layer == null || this.released || !this.layoutNode.isAttached()) ? false : true;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final Rect localBoundingBoxOf(LayoutCoordinates layoutCoordinates, boolean z) {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        if (!layoutCoordinates.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinates " + layoutCoordinates + " is not attached!");
        }
        NodeCoordinator coordinator = toCoordinator(layoutCoordinates);
        coordinator.onCoordinatesUsed$ui_release();
        NodeCoordinator findCommonAncestor$ui_release = findCommonAncestor$ui_release(coordinator);
        MutableRect mutableRect = this._rectCache;
        if (mutableRect == null) {
            mutableRect = new MutableRect(0.0f, 0.0f, 0.0f, 0.0f);
            this._rectCache = mutableRect;
        }
        mutableRect.left = 0.0f;
        mutableRect.top = 0.0f;
        mutableRect.right = (int) (layoutCoordinates.mo610getSizeYbymL2g() >> 32);
        mutableRect.bottom = (int) (layoutCoordinates.mo610getSizeYbymL2g() & 4294967295L);
        while (coordinator != findCommonAncestor$ui_release) {
            coordinator.rectInParent$ui_release(mutableRect, z, false);
            if (mutableRect.isEmpty()) {
                Rect.Companion.getClass();
                return Rect.Zero;
            }
            coordinator = coordinator.wrappedBy;
            coordinator.getClass();
        }
        ancestorToLocal(findCommonAncestor$ui_release, mutableRect, z);
        return new Rect(mutableRect.left, mutableRect.top, mutableRect.right, mutableRect.bottom);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localPositionOf-R5De75A */
    public final long mo611localPositionOfR5De75A(LayoutCoordinates layoutCoordinates, long j) {
        return mo612localPositionOfS_NoaFU(layoutCoordinates, j, true);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localPositionOf-S_NoaFU */
    public final long mo612localPositionOfS_NoaFU(LayoutCoordinates layoutCoordinates, long j, boolean z) {
        if (layoutCoordinates instanceof LookaheadLayoutCoordinates) {
            ((LookaheadLayoutCoordinates) layoutCoordinates).lookaheadDelegate.coordinator.onCoordinatesUsed$ui_release();
            Offset.Companion companion = Offset.Companion;
            return ((LookaheadLayoutCoordinates) layoutCoordinates).mo612localPositionOfS_NoaFU(this, j ^ (-9223372034707292160L), z) ^ (-9223372034707292160L);
        }
        NodeCoordinator coordinator = toCoordinator(layoutCoordinates);
        coordinator.onCoordinatesUsed$ui_release();
        NodeCoordinator findCommonAncestor$ui_release = findCommonAncestor$ui_release(coordinator);
        while (coordinator != findCommonAncestor$ui_release) {
            OwnedLayer ownedLayer = coordinator.layer;
            if (ownedLayer != null) {
                j = ownedLayer.mo685mapOffset8S9VItk(j, false);
            }
            if (z || !coordinator.isPlacedUnderMotionFrameOfReference) {
                j = IntOffsetKt.m853plusNvtHpc(j, coordinator.position);
            }
            coordinator = coordinator.wrappedBy;
            coordinator.getClass();
        }
        return m664ancestorToLocalS_NoaFU(findCommonAncestor$ui_release, j, z);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToRoot-MK-Hz9U */
    public final long mo613localToRootMKHz9U(long j) {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        onCoordinatesUsed$ui_release();
        while (this != null) {
            OwnedLayer ownedLayer = this.layer;
            if (ownedLayer != null) {
                j = ownedLayer.mo685mapOffset8S9VItk(j, false);
            }
            j = IntOffsetKt.m853plusNvtHpc(j, this.position);
            this = this.wrappedBy;
        }
        return j;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToScreen-MK-Hz9U */
    public final long mo614localToScreenMKHz9U(long j) {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        return ((AndroidComposeView) LayoutNodeKt.requireOwner(this.layoutNode)).m693localToScreenMKHz9U(mo613localToRootMKHz9U(j));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToWindow-MK-Hz9U */
    public final long mo615localToWindowMKHz9U(long j) {
        long mo613localToRootMKHz9U = mo613localToRootMKHz9U(j);
        AndroidComposeView androidComposeView = (AndroidComposeView) LayoutNodeKt.requireOwner(this.layoutNode);
        androidComposeView.recalculateWindowPosition();
        return Matrix.m482mapMKHz9U(mo613localToRootMKHz9U, androidComposeView.viewToWindowMatrix);
    }

    public final void onAttach() {
        if (this.layer != null || this.layerBlock == null) {
            return;
        }
        OwnedLayer createLayer$default = Owner.createLayer$default(LayoutNodeKt.requireOwner(this.layoutNode), getDrawBlock(), this.invalidateParentLayer, this.explicitLayer, false, 8);
        createLayer$default.mo687resizeozmzZPI(this.measuredSize);
        createLayer$default.mo686movegyyYBs(this.position);
        createLayer$default.invalidate();
        this.layer = createLayer$default;
    }

    public final void onCoordinatesUsed$ui_release() {
        this.layoutNode.layoutDelegate.onCoordinatesUsed();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v2, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public final void onMeasured() {
        Modifier.Node node;
        Modifier.Node headNode = headNode(NodeKindKt.m681getIncludeSelfInTraversalH91voCI(128));
        if (headNode == null || (headNode.node.aggregateChildKindSet & 128) == 0) {
            return;
        }
        Snapshot.Companion.getClass();
        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
        Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
        try {
            boolean m681getIncludeSelfInTraversalH91voCI = NodeKindKt.m681getIncludeSelfInTraversalH91voCI(128);
            if (m681getIncludeSelfInTraversalH91voCI) {
                node = getTail();
            } else {
                node = getTail().parent;
                if (node == null) {
                    Unit unit = Unit.INSTANCE;
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                }
            }
            for (Modifier.Node headNode2 = headNode(m681getIncludeSelfInTraversalH91voCI); headNode2 != null && (headNode2.aggregateChildKindSet & 128) != 0; headNode2 = headNode2.child) {
                if ((headNode2.kindSet & 128) != 0) {
                    ?? r9 = 0;
                    DelegatingNode delegatingNode = headNode2;
                    while (delegatingNode != 0) {
                        if (delegatingNode instanceof LayoutAwareModifierNode) {
                            ((LayoutAwareModifierNode) delegatingNode).mo49onRemeasuredozmzZPI(this.measuredSize);
                        } else if ((delegatingNode.kindSet & 128) != 0 && (delegatingNode instanceof DelegatingNode)) {
                            Modifier.Node node2 = delegatingNode.delegate;
                            int i = 0;
                            delegatingNode = delegatingNode;
                            r9 = r9;
                            while (node2 != null) {
                                if ((node2.kindSet & 128) != 0) {
                                    i++;
                                    r9 = r9;
                                    if (i == 1) {
                                        delegatingNode = node2;
                                    } else {
                                        if (r9 == 0) {
                                            r9 = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (delegatingNode != 0) {
                                            r9.add(delegatingNode);
                                            delegatingNode = 0;
                                        }
                                        r9.add(node2);
                                    }
                                }
                                node2 = node2.child;
                                delegatingNode = delegatingNode;
                                r9 = r9;
                            }
                            if (i == 1) {
                            }
                        }
                        delegatingNode = DelegatableNodeKt.access$pop(r9);
                    }
                }
                if (headNode2 == node) {
                    break;
                }
            }
            Unit unit2 = Unit.INSTANCE;
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
        } catch (Throwable th) {
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
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
    public final void onPlaced() {
        boolean m681getIncludeSelfInTraversalH91voCI = NodeKindKt.m681getIncludeSelfInTraversalH91voCI(128);
        Modifier.Node tail = getTail();
        if (!m681getIncludeSelfInTraversalH91voCI && (tail = tail.parent) == null) {
            return;
        }
        for (Modifier.Node headNode = headNode(m681getIncludeSelfInTraversalH91voCI); headNode != null && (headNode.aggregateChildKindSet & 128) != 0; headNode = headNode.child) {
            if ((headNode.kindSet & 128) != 0) {
                DelegatingNode delegatingNode = headNode;
                ?? r5 = 0;
                while (delegatingNode != 0) {
                    if (delegatingNode instanceof LayoutAwareModifierNode) {
                        ((LayoutAwareModifierNode) delegatingNode).onPlaced(this);
                    } else if ((delegatingNode.kindSet & 128) != 0 && (delegatingNode instanceof DelegatingNode)) {
                        Modifier.Node node = delegatingNode.delegate;
                        int i = 0;
                        delegatingNode = delegatingNode;
                        r5 = r5;
                        while (node != null) {
                            if ((node.kindSet & 128) != 0) {
                                i++;
                                r5 = r5;
                                if (i == 1) {
                                    delegatingNode = node;
                                } else {
                                    if (r5 == 0) {
                                        r5 = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNode != 0) {
                                        r5.add(delegatingNode);
                                        delegatingNode = 0;
                                    }
                                    r5.add(node);
                                }
                            }
                            node = node.child;
                            delegatingNode = delegatingNode;
                            r5 = r5;
                        }
                        if (i == 1) {
                        }
                    }
                    delegatingNode = DelegatableNodeKt.access$pop(r5);
                }
            }
            if (headNode == tail) {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v42 */
    /* renamed from: outOfBoundsHit-8NAm7pk, reason: not valid java name */
    public final void m673outOfBoundsHit8NAm7pk(final Modifier.Node node, final HitTestSource hitTestSource, final long j, final HitTestResult hitTestResult, final int i, final boolean z, final float f, final boolean z2) {
        Modifier.Node access$pop;
        if (node == null) {
            mo638hitTestChildqzLsGqo(hitTestSource, j, hitTestResult, i, z);
            return;
        }
        final int i2 = i;
        PointerType.Companion.getClass();
        if (i2 == PointerType.Stylus || i2 == PointerType.Eraser) {
            DelegatingNode delegatingNode = node;
            MutableVector mutableVector = null;
            while (true) {
                if (delegatingNode == 0) {
                    break;
                }
                if (delegatingNode instanceof PointerInputModifierNode) {
                    long mo212getTouchBoundsExpansionRZrCHBk = ((PointerInputModifierNode) delegatingNode).mo212getTouchBoundsExpansionRZrCHBk();
                    int i3 = (int) (j >> 32);
                    float intBitsToFloat = Float.intBitsToFloat(i3);
                    LayoutNode layoutNode = this.layoutNode;
                    LayoutDirection layoutDirection = layoutNode.layoutDirection;
                    TouchBoundsExpansion.Companion companion = TouchBoundsExpansion.Companion;
                    long j2 = Long.MIN_VALUE & mo212getTouchBoundsExpansionRZrCHBk;
                    if (intBitsToFloat >= (-((j2 == 0 || layoutDirection == LayoutDirection.Ltr) ? TouchBoundsExpansion.Companion.access$unpack(TouchBoundsExpansion.Companion, mo212getTouchBoundsExpansionRZrCHBk, 0) : TouchBoundsExpansion.Companion.access$unpack(TouchBoundsExpansion.Companion, mo212getTouchBoundsExpansionRZrCHBk, 2)))) {
                        if (Float.intBitsToFloat(i3) < getMeasuredWidth() + ((j2 == 0 || layoutNode.layoutDirection == LayoutDirection.Ltr) ? TouchBoundsExpansion.Companion.access$unpack(TouchBoundsExpansion.Companion, mo212getTouchBoundsExpansionRZrCHBk, 2) : TouchBoundsExpansion.Companion.access$unpack(TouchBoundsExpansion.Companion, mo212getTouchBoundsExpansionRZrCHBk, 0))) {
                            int i4 = (int) (j & 4294967295L);
                            float intBitsToFloat2 = Float.intBitsToFloat(i4);
                            TouchBoundsExpansion.Companion companion2 = TouchBoundsExpansion.Companion;
                            if (intBitsToFloat2 >= (-TouchBoundsExpansion.Companion.access$unpack(companion2, mo212getTouchBoundsExpansionRZrCHBk, 1))) {
                                if (Float.intBitsToFloat(i4) < TouchBoundsExpansion.Companion.access$unpack(companion2, mo212getTouchBoundsExpansionRZrCHBk, 3) + getMeasuredHeight()) {
                                    Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.NodeCoordinator$outOfBoundsHit$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            NodeCoordinator nodeCoordinator = NodeCoordinator.this;
                                            Modifier.Node m680access$nextUntilhw7D004 = NodeCoordinatorKt.m680access$nextUntilhw7D004(node, hitTestSource.mo679entityTypeOLwlOKw());
                                            NodeCoordinator.HitTestSource hitTestSource2 = hitTestSource;
                                            long j3 = j;
                                            HitTestResult hitTestResult2 = hitTestResult;
                                            int i5 = i2;
                                            boolean z3 = z;
                                            float f2 = f;
                                            boolean z4 = z2;
                                            NodeCoordinator.Companion companion3 = NodeCoordinator.Companion;
                                            nodeCoordinator.m673outOfBoundsHit8NAm7pk(m680access$nextUntilhw7D004, hitTestSource2, j3, hitTestResult2, i5, z3, f2, z4);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    int i5 = hitTestResult.hitDepth;
                                    int i6 = hitTestResult.values._size;
                                    if (i5 == i6 - 1) {
                                        hitTestResult.removeNodesInRange(i5 + 1, i6);
                                        hitTestResult.hitDepth++;
                                        hitTestResult.values.add(node);
                                        hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(0.0f, z, true));
                                        function0.invoke();
                                        hitTestResult.hitDepth = i5;
                                        return;
                                    }
                                    long m637findBestHitDistancefn2tFes = hitTestResult.m637findBestHitDistancefn2tFes();
                                    int i7 = hitTestResult.hitDepth;
                                    if (!DistanceAndFlags.m635isInExpandedBoundsimpl(m637findBestHitDistancefn2tFes)) {
                                        if (DistanceAndFlags.m634getDistanceimpl(m637findBestHitDistancefn2tFes) > 0.0f) {
                                            int i8 = hitTestResult.hitDepth;
                                            hitTestResult.removeNodesInRange(i8 + 1, hitTestResult.values._size);
                                            hitTestResult.hitDepth++;
                                            hitTestResult.values.add(node);
                                            hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(0.0f, z, true));
                                            function0.invoke();
                                            hitTestResult.hitDepth = i8;
                                            return;
                                        }
                                        return;
                                    }
                                    int i9 = hitTestResult.values._size;
                                    int i10 = i9 - 1;
                                    hitTestResult.hitDepth = i10;
                                    hitTestResult.removeNodesInRange(i9, i9);
                                    hitTestResult.hitDepth++;
                                    hitTestResult.values.add(node);
                                    hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(0.0f, z, true));
                                    function0.invoke();
                                    hitTestResult.hitDepth = i10;
                                    if (DistanceAndFlags.m634getDistanceimpl(hitTestResult.m637findBestHitDistancefn2tFes()) < 0.0f) {
                                        hitTestResult.removeNodesInRange(i7 + 1, hitTestResult.hitDepth + 1);
                                    }
                                    hitTestResult.hitDepth = i7;
                                    return;
                                }
                            }
                        }
                    }
                } else {
                    if ((delegatingNode.kindSet & 16) != 0 && (delegatingNode instanceof DelegatingNode)) {
                        Modifier.Node node2 = delegatingNode.delegate;
                        int i11 = 0;
                        access$pop = delegatingNode;
                        mutableVector = mutableVector;
                        while (node2 != null) {
                            if ((node2.kindSet & 16) != 0) {
                                i11++;
                                mutableVector = mutableVector;
                                if (i11 == 1) {
                                    access$pop = node2;
                                } else {
                                    if (mutableVector == null) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (access$pop != null) {
                                        mutableVector.add(access$pop);
                                        access$pop = null;
                                    }
                                    mutableVector.add(node2);
                                }
                            }
                            node2 = node2.child;
                            access$pop = access$pop;
                            mutableVector = mutableVector;
                        }
                        if (i11 == 1) {
                            i2 = i;
                            delegatingNode = access$pop;
                            mutableVector = mutableVector;
                        }
                    }
                    access$pop = DelegatableNodeKt.access$pop(mutableVector);
                    i2 = i;
                    delegatingNode = access$pop;
                    mutableVector = mutableVector;
                }
            }
        }
        if (z2) {
            m671hitNearFh5PU_I(node, hitTestSource, j, hitTestResult, i, z, f);
            return;
        }
        if (!hitTestSource.interceptOutOfBoundsChildEvents(node)) {
            m673outOfBoundsHit8NAm7pk(NodeCoordinatorKt.m680access$nextUntilhw7D004(node, hitTestSource.mo679entityTypeOLwlOKw()), hitTestSource, j, hitTestResult, i, z, f, false);
            return;
        }
        Function0 function02 = new Function0() { // from class: androidx.compose.ui.node.NodeCoordinator$speculativeHit$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NodeCoordinator nodeCoordinator = NodeCoordinator.this;
                Modifier.Node m680access$nextUntilhw7D004 = NodeCoordinatorKt.m680access$nextUntilhw7D004(node, hitTestSource.mo679entityTypeOLwlOKw());
                NodeCoordinator.HitTestSource hitTestSource2 = hitTestSource;
                long j3 = j;
                HitTestResult hitTestResult2 = hitTestResult;
                int i12 = i;
                boolean z3 = z;
                float f2 = f;
                NodeCoordinator.Companion companion3 = NodeCoordinator.Companion;
                nodeCoordinator.m673outOfBoundsHit8NAm7pk(m680access$nextUntilhw7D004, hitTestSource2, j3, hitTestResult2, i12, z3, f2, false);
                return Unit.INSTANCE;
            }
        };
        int i12 = hitTestResult.hitDepth;
        int i13 = hitTestResult.values._size;
        if (i12 != i13 - 1) {
            long m637findBestHitDistancefn2tFes2 = hitTestResult.m637findBestHitDistancefn2tFes();
            int i14 = hitTestResult.hitDepth;
            int i15 = hitTestResult.values._size;
            int i16 = i15 - 1;
            hitTestResult.hitDepth = i16;
            hitTestResult.removeNodesInRange(i15, i15);
            hitTestResult.hitDepth++;
            hitTestResult.values.add(node);
            hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(f, z, false));
            function02.invoke();
            hitTestResult.hitDepth = i16;
            long m637findBestHitDistancefn2tFes3 = hitTestResult.m637findBestHitDistancefn2tFes();
            if (hitTestResult.hitDepth + 1 >= hitTestResult.values._size - 1 || DistanceAndFlags.m633compareTo9YPOF3E(m637findBestHitDistancefn2tFes2, m637findBestHitDistancefn2tFes3) <= 0) {
                hitTestResult.removeNodesInRange(hitTestResult.hitDepth + 1, hitTestResult.values._size);
            } else {
                hitTestResult.removeNodesInRange(i14 + 1, DistanceAndFlags.m635isInExpandedBoundsimpl(m637findBestHitDistancefn2tFes3) ? hitTestResult.hitDepth + 2 : hitTestResult.hitDepth + 1);
            }
            hitTestResult.hitDepth = i14;
            return;
        }
        int i17 = i12 + 1;
        hitTestResult.removeNodesInRange(i17, i13);
        hitTestResult.hitDepth++;
        hitTestResult.values.add(node);
        hitTestResult.distanceFromEdgeAndFlags.add(HitTestResultKt.DistanceAndFlags(f, z, false));
        function02.invoke();
        hitTestResult.hitDepth = i12;
        if (i17 == hitTestResult.values._size - 1 || DistanceAndFlags.m635isInExpandedBoundsimpl(hitTestResult.m637findBestHitDistancefn2tFes())) {
            int i18 = hitTestResult.hitDepth;
            int i19 = i18 + 1;
            hitTestResult.values.removeAt(i19);
            MutableLongList mutableLongList = hitTestResult.distanceFromEdgeAndFlags;
            if (i19 >= 0) {
                int i20 = mutableLongList._size;
                if (i19 < i20) {
                    long[] jArr = mutableLongList.content;
                    long j3 = jArr[i19];
                    if (i19 != i20 - 1) {
                        ArraysKt___ArraysJvmKt.copyInto(jArr, jArr, i19, i18 + 2, i20);
                    }
                    mutableLongList._size--;
                    return;
                }
            } else {
                mutableLongList.getClass();
            }
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
            throw null;
        }
    }

    public void performDraw(Canvas canvas, GraphicsLayer graphicsLayer) {
        NodeCoordinator nodeCoordinator = this.wrapped;
        if (nodeCoordinator != null) {
            nodeCoordinator.draw(canvas, graphicsLayer);
        }
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public void mo609placeAtf8xVGno(long j, float f, Function1 function1) {
        if (!this.forcePlaceWithLookaheadOffset) {
            m674placeSelfMLgxB_4(j, f, function1, null);
            return;
        }
        LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
        lookaheadDelegate.getClass();
        m674placeSelfMLgxB_4(lookaheadDelegate.position, f, function1, null);
    }

    /* renamed from: placeSelf-MLgxB_4, reason: not valid java name */
    public final void m674placeSelfMLgxB_4(long j, float f, Function1 function1, GraphicsLayer graphicsLayer) {
        LayoutNode layoutNode = this.layoutNode;
        if (graphicsLayer != null) {
            if (function1 != null) {
                InlineClassHelperKt.throwIllegalArgumentException("both ways to create layers shouldn't be used together");
            }
            if (this.explicitLayer != graphicsLayer) {
                this.explicitLayer = null;
                updateLayerBlock(null, false);
                this.explicitLayer = graphicsLayer;
            }
            if (this.layer == null) {
                Owner requireOwner = LayoutNodeKt.requireOwner(layoutNode);
                Function2 drawBlock = getDrawBlock();
                Function0 function0 = this.invalidateParentLayer;
                OwnedLayer createLayer$default = Owner.createLayer$default(requireOwner, drawBlock, function0, graphicsLayer, false, 8);
                createLayer$default.mo687resizeozmzZPI(this.measuredSize);
                createLayer$default.mo686movegyyYBs(j);
                this.layer = createLayer$default;
                layoutNode.innerLayerCoordinatorIsDirty = true;
                ((NodeCoordinator$invalidateParentLayer$1) function0).invoke();
            }
        } else {
            if (this.explicitLayer != null) {
                this.explicitLayer = null;
                updateLayerBlock(null, false);
            }
            updateLayerBlock(function1, false);
        }
        if (!IntOffset.m849equalsimpl0(this.position, j)) {
            this.position = j;
            layoutNode.layoutDelegate.measurePassDelegate.notifyChildrenUsingCoordinatesWhilePlacing();
            OwnedLayer ownedLayer = this.layer;
            if (ownedLayer != null) {
                ownedLayer.mo686movegyyYBs(j);
            } else {
                NodeCoordinator nodeCoordinator = this.wrappedBy;
                if (nodeCoordinator != null) {
                    nodeCoordinator.invalidateLayer();
                }
            }
            LookaheadCapablePlaceable.invalidateAlignmentLinesFromPositionChange(this);
            AndroidComposeView androidComposeView = layoutNode.owner;
            if (androidComposeView != null) {
                androidComposeView.onLayoutChange(layoutNode);
            }
        }
        this.zIndex = f;
        if (this.isPlacingForAlignment) {
            return;
        }
        captureRulers(new PlaceableResult(getMeasureResult$ui_release(), this));
    }

    public final void rectInParent$ui_release(MutableRect mutableRect, boolean z, boolean z2) {
        OwnedLayer ownedLayer = this.layer;
        if (ownedLayer != null) {
            if (this.isClipping) {
                if (z2) {
                    long m668getMinimumTouchTargetSizeNHjbRc = m668getMinimumTouchTargetSizeNHjbRc();
                    float intBitsToFloat = Float.intBitsToFloat((int) (m668getMinimumTouchTargetSizeNHjbRc >> 32)) / 2.0f;
                    float intBitsToFloat2 = Float.intBitsToFloat((int) (m668getMinimumTouchTargetSizeNHjbRc & 4294967295L)) / 2.0f;
                    long j = this.measuredSize;
                    mutableRect.intersect(-intBitsToFloat, -intBitsToFloat2, ((int) (j >> 32)) + intBitsToFloat, ((int) (j & 4294967295L)) + intBitsToFloat2);
                } else if (z) {
                    long j2 = this.measuredSize;
                    mutableRect.intersect(0.0f, 0.0f, (int) (j2 >> 32), (int) (j2 & 4294967295L));
                }
                if (mutableRect.isEmpty()) {
                    return;
                }
            }
            ownedLayer.mapBounds(mutableRect, false);
        }
        long j3 = this.position;
        IntOffset.Companion companion = IntOffset.Companion;
        float f = (int) (j3 >> 32);
        mutableRect.left += f;
        mutableRect.right += f;
        float f2 = (int) (j3 & 4294967295L);
        mutableRect.top += f2;
        mutableRect.bottom += f2;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final void replace$ui_release() {
        GraphicsLayer graphicsLayer = this.explicitLayer;
        if (graphicsLayer != null) {
            mo623placeAtf8xVGno(this.position, this.zIndex, graphicsLayer);
        } else {
            mo609placeAtf8xVGno(this.position, this.zIndex, this.layerBlock);
        }
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: screenToLocal-MK-Hz9U */
    public final long mo616screenToLocalMKHz9U(long j) {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        return mo612localPositionOfS_NoaFU(LayoutCoordinatesKt.findRootCoordinates(this), ((AndroidComposeView) LayoutNodeKt.requireOwner(this.layoutNode)).m696screenToLocalMKHz9U(j), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v5, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public final void setMeasureResult$ui_release(MeasureResult measureResult) {
        NodeCoordinator nodeCoordinator;
        boolean z;
        boolean z2;
        boolean z3 = true;
        MeasureResult measureResult2 = this._measureResult;
        if (measureResult != measureResult2) {
            this._measureResult = measureResult;
            LayoutNode layoutNode = this.layoutNode;
            int i = 0;
            if (measureResult2 == null || measureResult.getWidth() != measureResult2.getWidth() || measureResult.getHeight() != measureResult2.getHeight()) {
                int width = measureResult.getWidth();
                int height = measureResult.getHeight();
                OwnedLayer ownedLayer = this.layer;
                if (ownedLayer != null) {
                    IntSize.Companion companion = IntSize.Companion;
                    ownedLayer.mo687resizeozmzZPI((width << 32) | (height & 4294967295L));
                } else if (layoutNode.isPlaced() && (nodeCoordinator = this.wrappedBy) != null) {
                    nodeCoordinator.invalidateLayer();
                }
                long j = (height & 4294967295L) | (width << 32);
                IntSize.Companion companion2 = IntSize.Companion;
                m624setMeasuredSizeozmzZPI(j);
                if (this.layerBlock != null) {
                    updateLayerParameters(false);
                }
                boolean m681getIncludeSelfInTraversalH91voCI = NodeKindKt.m681getIncludeSelfInTraversalH91voCI(4);
                Modifier.Node tail = getTail();
                if (m681getIncludeSelfInTraversalH91voCI || (tail = tail.parent) != null) {
                    for (Modifier.Node headNode = headNode(m681getIncludeSelfInTraversalH91voCI); headNode != null && (headNode.aggregateChildKindSet & 4) != 0; headNode = headNode.child) {
                        if ((headNode.kindSet & 4) != 0) {
                            DelegatingNode delegatingNode = headNode;
                            ?? r10 = 0;
                            while (delegatingNode != 0) {
                                if (delegatingNode instanceof DrawModifierNode) {
                                    ((DrawModifierNode) delegatingNode).onMeasureResultChanged();
                                } else if ((delegatingNode.kindSet & 4) != 0 && (delegatingNode instanceof DelegatingNode)) {
                                    Modifier.Node node = delegatingNode.delegate;
                                    int i2 = 0;
                                    delegatingNode = delegatingNode;
                                    r10 = r10;
                                    while (node != null) {
                                        if ((node.kindSet & 4) != 0) {
                                            i2++;
                                            r10 = r10;
                                            if (i2 == 1) {
                                                delegatingNode = node;
                                            } else {
                                                if (r10 == 0) {
                                                    r10 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNode != 0) {
                                                    r10.add(delegatingNode);
                                                    delegatingNode = 0;
                                                }
                                                r10.add(node);
                                            }
                                        }
                                        node = node.child;
                                        delegatingNode = delegatingNode;
                                        r10 = r10;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                delegatingNode = DelegatableNodeKt.access$pop(r10);
                            }
                        }
                        if (headNode == tail) {
                            break;
                        }
                    }
                }
                AndroidComposeView androidComposeView = layoutNode.owner;
                if (androidComposeView != null) {
                    androidComposeView.onLayoutChange(layoutNode);
                }
            }
            MutableObjectIntMap mutableObjectIntMap = this.oldAlignmentLines;
            if ((mutableObjectIntMap == null || mutableObjectIntMap._size == 0) && measureResult.getAlignmentLines().isEmpty()) {
                return;
            }
            MutableObjectIntMap mutableObjectIntMap2 = this.oldAlignmentLines;
            Map alignmentLines = measureResult.getAlignmentLines();
            if (mutableObjectIntMap2 != null && mutableObjectIntMap2._size == alignmentLines.size()) {
                Object[] objArr = mutableObjectIntMap2.keys;
                int[] iArr = mutableObjectIntMap2.values;
                long[] jArr = mutableObjectIntMap2.metadata;
                int length = jArr.length - 2;
                if (length < 0) {
                    return;
                }
                int i3 = 0;
                loop0: while (true) {
                    long j2 = jArr[i3];
                    if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i4 = 8 - ((~(i3 - length)) >>> 31);
                        int i5 = i;
                        while (i5 < i4) {
                            if ((j2 & 255) < 128) {
                                int i6 = (i3 << 3) + i5;
                                Object obj = objArr[i6];
                                z2 = z3;
                                int i7 = iArr[i6];
                                Integer num = (Integer) alignmentLines.get((AlignmentLine) obj);
                                if (num == null || num.intValue() != i7) {
                                    break loop0;
                                }
                            } else {
                                z2 = z3;
                            }
                            j2 >>= 8;
                            i5++;
                            z3 = z2;
                        }
                        z = z3;
                        if (i4 != 8) {
                            return;
                        }
                    } else {
                        z = z3;
                    }
                    if (i3 == length) {
                        return;
                    }
                    i3++;
                    z3 = z;
                    i = 0;
                }
            }
            layoutNode.layoutDelegate.measurePassDelegate.alignmentLines.onAlignmentsChanged();
            MutableObjectIntMap mutableObjectIntMap3 = this.oldAlignmentLines;
            if (mutableObjectIntMap3 == null) {
                mutableObjectIntMap3 = ObjectIntMapKt.mutableObjectIntMapOf();
                this.oldAlignmentLines = mutableObjectIntMap3;
            }
            mutableObjectIntMap3.clear();
            for (Map.Entry entry : measureResult.getAlignmentLines().entrySet()) {
                mutableObjectIntMap3.set(((Number) entry.getValue()).intValue(), entry.getKey());
            }
        }
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: transformFrom-EL8BTi8 */
    public final void mo617transformFromEL8BTi8(LayoutCoordinates layoutCoordinates, float[] fArr) {
        NodeCoordinator coordinator = toCoordinator(layoutCoordinates);
        coordinator.onCoordinatesUsed$ui_release();
        NodeCoordinator findCommonAncestor$ui_release = findCommonAncestor$ui_release(coordinator);
        Matrix.m484resetimpl(fArr);
        coordinator.m676transformToAncestorEL8BTi8(findCommonAncestor$ui_release, fArr);
        m675transformFromAncestorEL8BTi8(findCommonAncestor$ui_release, fArr);
    }

    /* renamed from: transformFromAncestor-EL8BTi8, reason: not valid java name */
    public final void m675transformFromAncestorEL8BTi8(NodeCoordinator nodeCoordinator, float[] fArr) {
        if (Intrinsics.areEqual(nodeCoordinator, this)) {
            return;
        }
        NodeCoordinator nodeCoordinator2 = this.wrappedBy;
        nodeCoordinator2.getClass();
        nodeCoordinator2.m675transformFromAncestorEL8BTi8(nodeCoordinator, fArr);
        long j = this.position;
        IntOffset.Companion.getClass();
        if (!IntOffset.m849equalsimpl0(j, 0L)) {
            float[] fArr2 = tmpMatrix;
            Matrix.m484resetimpl(fArr2);
            long j2 = this.position;
            Matrix.m488translateimpl(-((int) (j2 >> 32)), -((int) (j2 & 4294967295L)), fArr2);
            Matrix.m487timesAssign58bKbWc(fArr, fArr2);
        }
        OwnedLayer ownedLayer = this.layer;
        if (ownedLayer != null) {
            ownedLayer.mo683inverseTransform58bKbWc(fArr);
        }
    }

    /* renamed from: transformToAncestor-EL8BTi8, reason: not valid java name */
    public final void m676transformToAncestorEL8BTi8(NodeCoordinator nodeCoordinator, float[] fArr) {
        while (!this.equals(nodeCoordinator)) {
            OwnedLayer ownedLayer = this.layer;
            if (ownedLayer != null) {
                ownedLayer.mo688transform58bKbWc(fArr);
            }
            long j = this.position;
            IntOffset.Companion.getClass();
            if (!IntOffset.m849equalsimpl0(j, 0L)) {
                float[] fArr2 = tmpMatrix;
                Matrix.m484resetimpl(fArr2);
                Matrix.m488translateimpl((int) (j >> 32), (int) (j & 4294967295L), fArr2);
                Matrix.m487timesAssign58bKbWc(fArr, fArr2);
            }
            this = this.wrappedBy;
            this.getClass();
        }
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: transformToScreen-58bKbWc */
    public final void mo618transformToScreen58bKbWc(float[] fArr) {
        Owner requireOwner = LayoutNodeKt.requireOwner(this.layoutNode);
        m676transformToAncestorEL8BTi8(toCoordinator(LayoutCoordinatesKt.findRootCoordinates(this)), fArr);
        ((AndroidComposeView) ((MatrixPositionCalculator) requireOwner)).m692localToScreen58bKbWc(fArr);
    }

    public final void updateLayerBlock(Function1 function1, boolean z) {
        AndroidComposeView androidComposeView;
        if (function1 != null && this.explicitLayer != null) {
            InlineClassHelperKt.throwIllegalArgumentException("layerBlock can't be provided when explicitLayer is provided");
        }
        LayoutNode layoutNode = this.layoutNode;
        boolean z2 = (!z && this.layerBlock == function1 && Intrinsics.areEqual(this.layerDensity, layoutNode.density) && this.layerLayoutDirection == layoutNode.layoutDirection) ? false : true;
        this.layerDensity = layoutNode.density;
        this.layerLayoutDirection = layoutNode.layoutDirection;
        boolean isAttached = layoutNode.isAttached();
        Function0 function0 = this.invalidateParentLayer;
        if (!isAttached || function1 == null) {
            this.layerBlock = null;
            OwnedLayer ownedLayer = this.layer;
            if (ownedLayer != null) {
                ownedLayer.destroy();
                layoutNode.innerLayerCoordinatorIsDirty = true;
                ((NodeCoordinator$invalidateParentLayer$1) function0).invoke();
                if (getTail().isAttached && layoutNode.isPlaced() && (androidComposeView = layoutNode.owner) != null) {
                    androidComposeView.onLayoutChange(layoutNode);
                }
            }
            this.layer = null;
            this.lastLayerDrawingWasSkipped = false;
            return;
        }
        this.layerBlock = function1;
        if (this.layer != null) {
            if (z2 && updateLayerParameters(true)) {
                ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).rectManager.onLayoutLayerPositionalPropertiesChanged(layoutNode);
                return;
            }
            return;
        }
        OwnedLayer createLayer$default = Owner.createLayer$default(LayoutNodeKt.requireOwner(layoutNode), getDrawBlock(), function0, null, layoutNode.forceUseOldLayers, 4);
        createLayer$default.mo687resizeozmzZPI(this.measuredSize);
        createLayer$default.mo686movegyyYBs(this.position);
        this.layer = createLayer$default;
        updateLayerParameters(true);
        layoutNode.innerLayerCoordinatorIsDirty = true;
        ((NodeCoordinator$invalidateParentLayer$1) function0).invoke();
    }

    public final boolean updateLayerParameters(boolean z) {
        AndroidComposeView androidComposeView;
        boolean z2 = false;
        if (this.explicitLayer == null) {
            OwnedLayer ownedLayer = this.layer;
            if (ownedLayer != null) {
                final Function1 function1 = this.layerBlock;
                if (function1 == null) {
                    throw AndroidAutofill$$ExternalSyntheticOutline0.m("updateLayerParameters requires a non-null layerBlock");
                }
                ReusableGraphicsLayerScope reusableGraphicsLayerScope = graphicsLayerScope;
                reusableGraphicsLayerScope.setScaleX(1.0f);
                reusableGraphicsLayerScope.setScaleY(1.0f);
                reusableGraphicsLayerScope.setAlpha(1.0f);
                reusableGraphicsLayerScope.setTranslationX(0.0f);
                reusableGraphicsLayerScope.setTranslationY(0.0f);
                reusableGraphicsLayerScope.setShadowElevation(0.0f);
                long j = GraphicsLayerScopeKt.DefaultShadowColor;
                reusableGraphicsLayerScope.m493setAmbientShadowColor8_81llA(j);
                reusableGraphicsLayerScope.m495setSpotShadowColor8_81llA(j);
                if (reusableGraphicsLayerScope.rotationX != 0.0f) {
                    reusableGraphicsLayerScope.mutatedFields |= 256;
                    reusableGraphicsLayerScope.rotationX = 0.0f;
                }
                if (reusableGraphicsLayerScope.rotationY != 0.0f) {
                    reusableGraphicsLayerScope.mutatedFields |= 512;
                    reusableGraphicsLayerScope.rotationY = 0.0f;
                }
                reusableGraphicsLayerScope.setRotationZ(0.0f);
                if (reusableGraphicsLayerScope.cameraDistance != 8.0f) {
                    reusableGraphicsLayerScope.mutatedFields |= 2048;
                    reusableGraphicsLayerScope.cameraDistance = 8.0f;
                }
                TransformOrigin.Companion.getClass();
                reusableGraphicsLayerScope.m496setTransformOrigin__ExYCQ(TransformOrigin.Center);
                reusableGraphicsLayerScope.setShape(RectangleShapeKt.RectangleShape);
                reusableGraphicsLayerScope.setClip(false);
                reusableGraphicsLayerScope.setRenderEffect(null);
                if (!Intrinsics.areEqual(reusableGraphicsLayerScope.colorFilter, (Object) null)) {
                    reusableGraphicsLayerScope.mutatedFields |= 262144;
                    reusableGraphicsLayerScope.colorFilter = null;
                }
                BlendMode.Companion.getClass();
                int i = BlendMode.SrcOver;
                if (reusableGraphicsLayerScope.blendMode != i) {
                    reusableGraphicsLayerScope.mutatedFields |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    reusableGraphicsLayerScope.blendMode = i;
                }
                CompositingStrategy.Companion.getClass();
                reusableGraphicsLayerScope.m494setCompositingStrategyaDBOjCE(0);
                Size.Companion.getClass();
                reusableGraphicsLayerScope.size = Size.Unspecified;
                reusableGraphicsLayerScope.outline = null;
                reusableGraphicsLayerScope.mutatedFields = 0;
                LayoutNode layoutNode = this.layoutNode;
                reusableGraphicsLayerScope.graphicsDensity = layoutNode.density;
                reusableGraphicsLayerScope.layoutDirection = layoutNode.layoutDirection;
                reusableGraphicsLayerScope.size = IntSizeKt.m864toSizeozmzZPI(this.measuredSize);
                ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).snapshotObserver.observeReads$ui_release(this, onCommitAffectingLayerParams, new Function0() { // from class: androidx.compose.ui.node.NodeCoordinator$updateLayerParameters$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Function1 function12 = Function1.this;
                        ReusableGraphicsLayerScope reusableGraphicsLayerScope2 = NodeCoordinator.graphicsLayerScope;
                        function12.mo779invoke(reusableGraphicsLayerScope2);
                        reusableGraphicsLayerScope2.outline = reusableGraphicsLayerScope2.shape.mo40createOutlinePq9zytI(reusableGraphicsLayerScope2.size, reusableGraphicsLayerScope2.layoutDirection, reusableGraphicsLayerScope2.graphicsDensity);
                        return Unit.INSTANCE;
                    }
                });
                LayerPositionalProperties layerPositionalProperties = this.layerPositionalProperties;
                if (layerPositionalProperties == null) {
                    layerPositionalProperties = new LayerPositionalProperties();
                    this.layerPositionalProperties = layerPositionalProperties;
                }
                LayerPositionalProperties layerPositionalProperties2 = tmpLayerPositionalProperties;
                layerPositionalProperties2.getClass();
                layerPositionalProperties2.scaleX = layerPositionalProperties.scaleX;
                layerPositionalProperties2.scaleY = layerPositionalProperties.scaleY;
                layerPositionalProperties2.translationX = layerPositionalProperties.translationX;
                layerPositionalProperties2.translationY = layerPositionalProperties.translationY;
                layerPositionalProperties2.rotationX = layerPositionalProperties.rotationX;
                layerPositionalProperties2.rotationY = layerPositionalProperties.rotationY;
                layerPositionalProperties2.rotationZ = layerPositionalProperties.rotationZ;
                layerPositionalProperties2.cameraDistance = layerPositionalProperties.cameraDistance;
                layerPositionalProperties2.transformOrigin = layerPositionalProperties.transformOrigin;
                layerPositionalProperties.scaleX = reusableGraphicsLayerScope.scaleX;
                layerPositionalProperties.scaleY = reusableGraphicsLayerScope.scaleY;
                layerPositionalProperties.translationX = reusableGraphicsLayerScope.translationX;
                layerPositionalProperties.translationY = reusableGraphicsLayerScope.translationY;
                layerPositionalProperties.rotationX = reusableGraphicsLayerScope.rotationX;
                layerPositionalProperties.rotationY = reusableGraphicsLayerScope.rotationY;
                layerPositionalProperties.rotationZ = reusableGraphicsLayerScope.rotationZ;
                layerPositionalProperties.cameraDistance = reusableGraphicsLayerScope.cameraDistance;
                layerPositionalProperties.transformOrigin = reusableGraphicsLayerScope.transformOrigin;
                ownedLayer.updateLayerProperties(reusableGraphicsLayerScope);
                boolean z3 = this.isClipping;
                this.isClipping = reusableGraphicsLayerScope.clip;
                this.lastLayerAlpha = reusableGraphicsLayerScope.alpha;
                if (layerPositionalProperties2.scaleX == layerPositionalProperties.scaleX && layerPositionalProperties2.scaleY == layerPositionalProperties.scaleY && layerPositionalProperties2.translationX == layerPositionalProperties.translationX && layerPositionalProperties2.translationY == layerPositionalProperties.translationY && layerPositionalProperties2.rotationX == layerPositionalProperties.rotationX && layerPositionalProperties2.rotationY == layerPositionalProperties.rotationY && layerPositionalProperties2.rotationZ == layerPositionalProperties.rotationZ && layerPositionalProperties2.cameraDistance == layerPositionalProperties.cameraDistance && TransformOrigin.m502equalsimpl0(layerPositionalProperties2.transformOrigin, layerPositionalProperties.transformOrigin)) {
                    z2 = true;
                }
                boolean z4 = !z2;
                if (z && ((!z2 || z3 != this.isClipping) && (androidComposeView = layoutNode.owner) != null)) {
                    androidComposeView.onLayoutChange(layoutNode);
                }
                return z4;
            }
            if (this.layerBlock != null) {
                InlineClassHelperKt.throwIllegalStateException("null layer with a non-null layerBlock");
                return false;
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: windowToLocal-MK-Hz9U */
    public final long mo619windowToLocalMKHz9U(long j) {
        if (!getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        LayoutCoordinates findRootCoordinates = LayoutCoordinatesKt.findRootCoordinates(this);
        AndroidComposeView androidComposeView = (AndroidComposeView) LayoutNodeKt.requireOwner(this.layoutNode);
        androidComposeView.recalculateWindowPosition();
        return mo612localPositionOfS_NoaFU(findRootCoordinates, Offset.m400minusMKHz9U(Matrix.m482mapMKHz9U(j, androidComposeView.windowToViewMatrix), LayoutCoordinatesKt.positionInRoot(findRootCoordinates)), true);
    }

    /* renamed from: withinLayerBounds-k-4lQ0M, reason: not valid java name */
    public final boolean m677withinLayerBoundsk4lQ0M(long j) {
        if ((((9187343241974906880L ^ (j & 9187343241974906880L)) - 4294967297L) & (-9223372034707292160L)) != 0) {
            return false;
        }
        OwnedLayer ownedLayer = this.layer;
        return ownedLayer == null || !this.isClipping || ownedLayer.mo684isInLayerk4lQ0M(j);
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public void mo623placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        if (this.forcePlaceWithLookaheadOffset) {
            LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
            lookaheadDelegate.getClass();
            m674placeSelfMLgxB_4(lookaheadDelegate.position, f, null, graphicsLayer);
            return;
        }
        m674placeSelfMLgxB_4(j, f, null, graphicsLayer);
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LayoutCoordinates getCoordinates() {
        return this;
    }
}
