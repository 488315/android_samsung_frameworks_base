package androidx.compose.ui.node;

import androidx.collection.MutableScatterMap;
import androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.BuildDrawCacheParams;
import androidx.compose.ui.draw.DrawModifier;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusOrder;
import androidx.compose.ui.focus.FocusProperties;
import androidx.compose.ui.focus.FocusPropertiesModifierNode;
import androidx.compose.ui.focus.FocusRequesterModifierNode;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputModifier;
import androidx.compose.ui.input.pointer.PointerInteropFilter;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutModifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifier;
import androidx.compose.ui.layout.ParentDataModifier;
import androidx.compose.ui.layout.RemeasurementModifier;
import androidx.compose.ui.modifier.BackwardsCompatLocalMap;
import androidx.compose.ui.modifier.EmptyMap;
import androidx.compose.ui.modifier.ModifierLocalConsumer;
import androidx.compose.ui.modifier.ModifierLocalManager;
import androidx.compose.ui.modifier.ModifierLocalMap;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.modifier.ModifierLocalProvider;
import androidx.compose.ui.modifier.ModifierLocalReadScope;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifier;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.HashSet;
import kotlin.Function;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.SafeContinuation;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class BackwardsCompatNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode, SemanticsModifierNode, PointerInputModifierNode, ModifierLocalModifierNode, ModifierLocalReadScope, ParentDataModifierNode, LayoutAwareModifierNode, GlobalPositionAwareModifierNode, FocusEventModifierNode, FocusPropertiesModifierNode, FocusRequesterModifierNode, OwnerScope, BuildDrawCacheParams {
    public BackwardsCompatLocalMap _providedValues;
    public Modifier.Element element;
    public LayoutCoordinates lastOnPlacedCoordinates;
    public final HashSet readValues;

    public BackwardsCompatNode(Modifier.Element element) {
        this.kindSet = NodeKindKt.calculateNodeKindSetFrom(element);
        this.element = element;
        this.readValues = new HashSet();
    }

    @Override // androidx.compose.ui.focus.FocusPropertiesModifierNode
    public final void applyFocusProperties(FocusProperties focusProperties) {
        Modifier.Element element = this.element;
        InlineClassHelperKt.throwIllegalStateException("applyFocusProperties called on wrong node");
        if (element != null) {
            throw new ClassCastException();
        }
        new FocusOrder(focusProperties);
        throw null;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        int i;
        SemanticsConfiguration semanticsConfiguration = ((SemanticsModifier) this.element).getSemanticsConfiguration();
        SemanticsConfiguration semanticsConfiguration2 = (SemanticsConfiguration) semanticsPropertyReceiver;
        semanticsConfiguration2.getClass();
        if (semanticsConfiguration.isMergingSemanticsOfDescendants) {
            semanticsConfiguration2.isMergingSemanticsOfDescendants = true;
        }
        if (semanticsConfiguration.isClearingSemantics) {
            semanticsConfiguration2.isClearingSemantics = true;
        }
        MutableScatterMap mutableScatterMap = semanticsConfiguration.props;
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i2 = 0;
        while (true) {
            long j = jArr[i2];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i3 = 8;
                int i4 = 8 - ((~(i2 - length)) >>> 31);
                int i5 = 0;
                while (i5 < i4) {
                    if ((255 & j) < 128) {
                        int i6 = (i2 << 3) + i5;
                        Object obj = objArr[i6];
                        Object obj2 = objArr2[i6];
                        SemanticsPropertyKey semanticsPropertyKey = (SemanticsPropertyKey) obj;
                        if (!semanticsConfiguration2.props.contains(semanticsPropertyKey)) {
                            semanticsConfiguration2.props.set(semanticsPropertyKey, obj2);
                        } else if (obj2 instanceof AccessibilityAction) {
                            AccessibilityAction accessibilityAction = (AccessibilityAction) semanticsConfiguration2.props.get(semanticsPropertyKey);
                            MutableScatterMap mutableScatterMap2 = semanticsConfiguration2.props;
                            i = i3;
                            String str = accessibilityAction.label;
                            if (str == null) {
                                str = ((AccessibilityAction) obj2).label;
                            }
                            Function function = accessibilityAction.action;
                            if (function == null) {
                                function = ((AccessibilityAction) obj2).action;
                            }
                            mutableScatterMap2.set(semanticsPropertyKey, new AccessibilityAction(str, function));
                        }
                        i = i3;
                    } else {
                        i = i3;
                    }
                    j >>= i;
                    i5++;
                    i3 = i;
                }
                if (i4 != i3) {
                    return;
                }
            }
            if (i2 == length) {
                return;
            } else {
                i2++;
            }
        }
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        ((DrawModifier) this.element).draw(layoutNodeDrawScope);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r1v9 */
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
    @Override // androidx.compose.ui.modifier.ModifierLocalModifierNode, androidx.compose.ui.modifier.ModifierLocalReadScope
    public final Object getCurrent(ProvidableModifierLocal providableModifierLocal) {
        NodeChain nodeChain;
        this.readValues.add(providableModifierLocal);
        if (!this.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node = this.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(this);
        while (layoutNodeRequireLayoutNode != null) {
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 32) != 0) {
                while (node != null) {
                    if ((node.kindSet & 32) != 0) {
                        DelegatingNode delegatingNodeAccess$pop = node;
                        ?? mutableVector = 0;
                        while (delegatingNodeAccess$pop != 0) {
                            if (delegatingNodeAccess$pop instanceof ModifierLocalModifierNode) {
                                ModifierLocalModifierNode modifierLocalModifierNode = (ModifierLocalModifierNode) delegatingNodeAccess$pop;
                                if (modifierLocalModifierNode.getProvidedValues().contains$ui_release(providableModifierLocal)) {
                                    return modifierLocalModifierNode.getProvidedValues().get$ui_release(providableModifierLocal);
                                }
                            } else if ((delegatingNodeAccess$pop.kindSet & 32) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                int i = 0;
                                delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                mutableVector = mutableVector;
                                while (node2 != null) {
                                    if ((node2.kindSet & 32) != 0) {
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
        return providableModifierLocal.defaultFactory.invoke();
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final Density getDensity() {
        return DelegatableNodeKt.requireLayoutNode(this).density;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final LayoutDirection getLayoutDirection() {
        return DelegatableNodeKt.requireLayoutNode(this).layoutDirection;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalModifierNode
    public final ModifierLocalMap getProvidedValues() {
        BackwardsCompatLocalMap backwardsCompatLocalMap = this._providedValues;
        return backwardsCompatLocalMap != null ? backwardsCompatLocalMap : EmptyMap.INSTANCE;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    /* renamed from: getSize-NH-jbRc */
    public final long mo361getSizeNHjbRc() {
        return IntSizeKt.m866toSizeozmzZPI(DelegatableNodeKt.m634requireCoordinator64DMado(this, 128).measuredSize);
    }

    public final void initializeModifier(boolean z) {
        if (!this.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("initializeModifier called on unattached node");
        }
        Modifier.Element element = this.element;
        if ((this.kindSet & 32) != 0) {
            if (element instanceof ModifierLocalConsumer) {
                ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).registerOnEndApplyChangesListener(new Function0() { // from class: androidx.compose.ui.node.BackwardsCompatNode.initializeModifier.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BackwardsCompatNode.this.updateModifierLocalConsumer();
                        return Unit.INSTANCE;
                    }
                });
            }
            if (element instanceof ModifierLocalProvider) {
                ModifierLocalProvider modifierLocalProvider = (ModifierLocalProvider) element;
                BackwardsCompatLocalMap backwardsCompatLocalMap = this._providedValues;
                if (backwardsCompatLocalMap == null || !backwardsCompatLocalMap.contains$ui_release(modifierLocalProvider.getKey())) {
                    this._providedValues = new BackwardsCompatLocalMap(modifierLocalProvider);
                    BackwardsCompatNodeKt$DetachedModifierLocalReadScope$1 backwardsCompatNodeKt$DetachedModifierLocalReadScope$1 = BackwardsCompatNodeKt.DetachedModifierLocalReadScope;
                    if (DelegatableNodeKt.requireLayoutNode(this).nodes.tail.attachHasBeenRun) {
                        ModifierLocalManager modifierLocalManager = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).modifierLocalManager;
                        ProvidableModifierLocal key = modifierLocalProvider.getKey();
                        modifierLocalManager.inserted.add(this);
                        modifierLocalManager.insertedLocal.add(key);
                        modifierLocalManager.invalidate();
                    }
                } else {
                    backwardsCompatLocalMap.element = modifierLocalProvider;
                    ModifierLocalManager modifierLocalManager2 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).modifierLocalManager;
                    ProvidableModifierLocal key2 = modifierLocalProvider.getKey();
                    modifierLocalManager2.inserted.add(this);
                    modifierLocalManager2.insertedLocal.add(key2);
                    modifierLocalManager2.invalidate();
                }
            }
        }
        if ((this.kindSet & 4) != 0 && !z) {
            DelegatableNodeKt.m634requireCoordinator64DMado(this, 2).invalidateLayer();
        }
        if ((this.kindSet & 2) != 0) {
            BackwardsCompatNodeKt$DetachedModifierLocalReadScope$1 backwardsCompatNodeKt$DetachedModifierLocalReadScope$12 = BackwardsCompatNodeKt.DetachedModifierLocalReadScope;
            if (DelegatableNodeKt.requireLayoutNode(this).nodes.tail.attachHasBeenRun) {
                NodeCoordinator nodeCoordinator = this.coordinator;
                nodeCoordinator.getClass();
                ((LayoutModifierNodeCoordinator) nodeCoordinator).setLayoutModifierNode$ui_release(this);
                OwnedLayer ownedLayer = nodeCoordinator.layer;
                if (ownedLayer != null) {
                    ownedLayer.invalidate();
                }
            }
            if (!z) {
                DelegatableNodeKt.m634requireCoordinator64DMado(this, 2).invalidateLayer();
                DelegatableNodeKt.requireLayoutNode(this).invalidateMeasurements$ui_release();
            }
        }
        if (element instanceof RemeasurementModifier) {
            ((RemeasurementModifier) element).onRemeasurementAvailable(DelegatableNodeKt.requireLayoutNode(this));
        }
        if ((this.kindSet & 256) != 0 && (element instanceof OnGloballyPositionedModifier)) {
            BackwardsCompatNodeKt$DetachedModifierLocalReadScope$1 backwardsCompatNodeKt$DetachedModifierLocalReadScope$13 = BackwardsCompatNodeKt.DetachedModifierLocalReadScope;
            if (DelegatableNodeKt.requireLayoutNode(this).nodes.tail.attachHasBeenRun) {
                DelegatableNodeKt.requireLayoutNode(this).invalidateMeasurements$ui_release();
            }
        }
        int i = this.kindSet;
        if ((i & 16) != 0 && (element instanceof PointerInputModifier)) {
            ((PointerInteropFilter) ((PointerInputModifier) element)).pointerInputFilter.layoutCoordinates = this.coordinator;
        }
        if ((i & 8) != 0) {
            ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).onSemanticsChange();
        }
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void interceptOutOfBoundsChildEvents() {
        ((PointerInteropFilter) ((PointerInputModifier) this.element)).pointerInputFilter.getClass();
    }

    @Override // androidx.compose.ui.node.OwnerScope
    public final boolean isValidOwnerScope() {
        return this.isAttached;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return ((LayoutModifier) this.element).maxIntrinsicHeight(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return ((LayoutModifier) this.element).maxIntrinsicWidth(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        return ((LayoutModifier) this.element).mo109measure3p2s80s(measureScope, measurable, j);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return ((LayoutModifier) this.element).minIntrinsicHeight(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return ((LayoutModifier) this.element).minIntrinsicWidth(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.ParentDataModifierNode
    public final Object modifyParentData(Density density, Object obj) {
        return ((ParentDataModifier) this.element).modifyParentData();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        initializeModifier(true);
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        ((PointerInteropFilter) ((PointerInputModifier) this.element)).pointerInputFilter.onCancel();
    }

    @Override // androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.PointerInputModifierNode
    public final void onDensityChange() {
        if (this.element instanceof PointerInputModifier) {
            onCancelPointerInput();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        unInitializeModifier();
    }

    @Override // androidx.compose.ui.focus.FocusEventModifierNode
    public final void onFocusEvent(FocusStateImpl focusStateImpl) {
        Modifier.Element element = this.element;
        InlineClassHelperKt.throwIllegalStateException("onFocusEvent called on wrong node");
        element.getClass();
        throw new ClassCastException();
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        AwaitFirstLayoutModifier awaitFirstLayoutModifier = (AwaitFirstLayoutModifier) ((OnGloballyPositionedModifier) this.element);
        if (awaitFirstLayoutModifier.wasPositioned) {
            return;
        }
        awaitFirstLayoutModifier.wasPositioned = true;
        SafeContinuation safeContinuation = awaitFirstLayoutModifier.continuation;
        if (safeContinuation != null) {
            int i = Result.$r8$clinit;
            safeContinuation.resumeWith(Unit.INSTANCE);
        }
        awaitFirstLayoutModifier.continuation = null;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void onMeasureResultChanged() {
        DrawModifierNodeKt.invalidateDraw(this);
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        this.lastOnPlacedCoordinates = layoutCoordinates;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        ((PointerInteropFilter) ((PointerInputModifier) this.element)).pointerInputFilter.m596onPointerEventH0pRuoY(pointerEvent, pointerEventPass);
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final boolean sharePointerInputWithSiblings() {
        ((PointerInteropFilter) ((PointerInputModifier) this.element)).pointerInputFilter.getClass();
        return true;
    }

    public final String toString() {
        return this.element.toString();
    }

    public final void unInitializeModifier() {
        if (!this.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("unInitializeModifier called on unattached node");
        }
        Modifier.Element element = this.element;
        if ((this.kindSet & 32) != 0) {
            if (element instanceof ModifierLocalProvider) {
                ModifierLocalManager modifierLocalManager = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).modifierLocalManager;
                ProvidableModifierLocal key = ((ModifierLocalProvider) element).getKey();
                modifierLocalManager.removed.add(DelegatableNodeKt.requireLayoutNode(this));
                modifierLocalManager.removedLocal.add(key);
                modifierLocalManager.invalidate();
            }
            if (element instanceof ModifierLocalConsumer) {
                ((ModifierLocalConsumer) element).onModifierLocalsUpdated(BackwardsCompatNodeKt.DetachedModifierLocalReadScope);
            }
        }
        if ((this.kindSet & 8) != 0) {
            ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).onSemanticsChange();
        }
    }

    public final void updateModifierLocalConsumer() {
        if (this.isAttached) {
            this.readValues.clear();
            ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).snapshotObserver.observeReads$ui_release(this, BackwardsCompatNodeKt.updateModifierLocalConsumer, new Function0() { // from class: androidx.compose.ui.node.BackwardsCompatNode.updateModifierLocalConsumer.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BackwardsCompatNode backwardsCompatNode = BackwardsCompatNode.this;
                    ((ModifierLocalConsumer) backwardsCompatNode.element).onModifierLocalsUpdated(backwardsCompatNode);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI */
    public final void mo50onRemeasuredozmzZPI(long j) {
    }
}
