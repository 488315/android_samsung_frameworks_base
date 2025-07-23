package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LookaheadLayoutCoordinates;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LookaheadDelegate extends LookaheadCapablePlaceable implements Measurable {
    public MeasureResult _measureResult;
    public final MutableObjectIntMap cachedAlignmentLinesMap;
    public final NodeCoordinator coordinator;
    public final LookaheadLayoutCoordinates lookaheadLayoutCoordinates;
    public Map oldAlignmentLines;
    public long position;

    public LookaheadDelegate(NodeCoordinator nodeCoordinator) {
        this.coordinator = nodeCoordinator;
        IntOffset.Companion.getClass();
        this.position = 0L;
        this.lookaheadLayoutCoordinates = new LookaheadLayoutCoordinates(this);
        this.cachedAlignmentLinesMap = ObjectIntMapKt.mutableObjectIntMapOf();
    }

    public static final void access$set_measureResult(LookaheadDelegate lookaheadDelegate, MeasureResult measureResult) {
        Unit unit;
        Map map;
        if (measureResult != null) {
            IntSize.Companion companion = IntSize.Companion;
            lookaheadDelegate.m624setMeasuredSizeozmzZPI((measureResult.getHeight() & 4294967295L) | (measureResult.getWidth() << 32));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            IntSize.Companion.getClass();
            lookaheadDelegate.m624setMeasuredSizeozmzZPI(0L);
        }
        if (!Intrinsics.areEqual(lookaheadDelegate._measureResult, measureResult) && measureResult != null && ((((map = lookaheadDelegate.oldAlignmentLines) != null && !map.isEmpty()) || !measureResult.getAlignmentLines().isEmpty()) && !Intrinsics.areEqual(measureResult.getAlignmentLines(), lookaheadDelegate.oldAlignmentLines))) {
            LookaheadPassDelegate lookaheadPassDelegate = lookaheadDelegate.coordinator.layoutNode.layoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            lookaheadPassDelegate.alignmentLines.onAlignmentsChanged();
            Map map2 = lookaheadDelegate.oldAlignmentLines;
            if (map2 == null) {
                map2 = new LinkedHashMap();
                lookaheadDelegate.oldAlignmentLines = map2;
            }
            map2.clear();
            map2.putAll(measureResult.getAlignmentLines());
        }
        lookaheadDelegate._measureResult = measureResult;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LookaheadCapablePlaceable getChild() {
        NodeCoordinator nodeCoordinator = this.coordinator.wrapped;
        if (nodeCoordinator != null) {
            return nodeCoordinator.getLookaheadDelegate();
        }
        return null;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LayoutCoordinates getCoordinates() {
        return this.lookaheadLayoutCoordinates;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.coordinator.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.coordinator.getFontScale();
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final boolean getHasMeasureResult() {
        return this._measureResult != null;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final LayoutDirection getLayoutDirection() {
        return this.coordinator.layoutNode.layoutDirection;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.node.MeasureScopeWithLayoutNode
    public final LayoutNode getLayoutNode() {
        return this.coordinator.layoutNode;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final MeasureResult getMeasureResult$ui_release() {
        MeasureResult measureResult = this._measureResult;
        if (measureResult != null) {
            return measureResult;
        }
        throw AndroidAutofill$$ExternalSyntheticOutline0.m("LookaheadDelegate has not been measured yet when measureResult is requested.");
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final LookaheadCapablePlaceable getParent() {
        NodeCoordinator nodeCoordinator = this.coordinator.wrappedBy;
        if (nodeCoordinator != null) {
            return nodeCoordinator.getLookaheadDelegate();
        }
        return null;
    }

    @Override // androidx.compose.ui.layout.Measured, androidx.compose.ui.layout.IntrinsicMeasurable
    public final Object getParentData() {
        return this.coordinator.getParentData();
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    /* renamed from: getPosition-nOcc-ac */
    public final long mo649getPositionnOccac() {
        return this.position;
    }

    /* renamed from: getSize-YbymL2g$ui_release, reason: not valid java name */
    public final long m650getSizeYbymL2g$ui_release() {
        long j = (this.width << 32) | (this.height & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.layout.IntrinsicMeasureScope
    public final boolean isLookingAhead() {
        return true;
    }

    public int maxIntrinsicHeight(int i) {
        NodeCoordinator nodeCoordinator = this.coordinator.wrapped;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.maxIntrinsicHeight(i);
    }

    public int maxIntrinsicWidth(int i) {
        NodeCoordinator nodeCoordinator = this.coordinator.wrapped;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.maxIntrinsicWidth(i);
    }

    public int minIntrinsicHeight(int i) {
        NodeCoordinator nodeCoordinator = this.coordinator.wrapped;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.minIntrinsicHeight(i);
    }

    public int minIntrinsicWidth(int i) {
        NodeCoordinator nodeCoordinator = this.coordinator.wrapped;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.minIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo609placeAtf8xVGno(long j, float f, Function1 function1) {
        m651placeSelfgyyYBs(j);
        if (this.isShallowPlacing) {
            return;
        }
        placeChildren();
    }

    public void placeChildren() {
        getMeasureResult$ui_release().placeChildren();
    }

    /* renamed from: placeSelf--gyyYBs, reason: not valid java name */
    public final void m651placeSelfgyyYBs(long j) {
        if (!IntOffset.m849equalsimpl0(this.position, j)) {
            this.position = j;
            NodeCoordinator nodeCoordinator = this.coordinator;
            LookaheadPassDelegate lookaheadPassDelegate = nodeCoordinator.layoutNode.layoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate != null) {
                lookaheadPassDelegate.notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
            }
            LookaheadCapablePlaceable.invalidateAlignmentLinesFromPositionChange(nodeCoordinator);
        }
        if (this.isPlacingForAlignment) {
            return;
        }
        captureRulers(new PlaceableResult(getMeasureResult$ui_release(), this));
    }

    /* renamed from: positionIn-iSbpLlY$ui_release, reason: not valid java name */
    public final long m652positionIniSbpLlY$ui_release(LookaheadDelegate lookaheadDelegate, boolean z) {
        IntOffset.Companion.getClass();
        long j = 0;
        while (!this.equals(lookaheadDelegate)) {
            if (!this.isPlacedUnderMotionFrameOfReference || !z) {
                j = IntOffset.m851plusqkQi6aY(j, this.position);
            }
            NodeCoordinator nodeCoordinator = this.coordinator.wrappedBy;
            nodeCoordinator.getClass();
            this = nodeCoordinator.getLookaheadDelegate();
            this.getClass();
        }
        return j;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final void replace$ui_release() {
        mo609placeAtf8xVGno(this.position, 0.0f, (Function1) null);
    }
}
