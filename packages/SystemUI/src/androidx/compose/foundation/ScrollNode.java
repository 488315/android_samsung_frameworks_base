package androidx.compose.foundation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.semantics.ScrollAxisRange;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public final class ScrollNode extends Modifier.Node implements LayoutModifierNode, SemanticsModifierNode {
    public boolean isVertical;
    public boolean reverseScrolling;
    public ScrollState state;

    public ScrollNode(ScrollState scrollState, boolean z, boolean z2) {
        this.state = scrollState;
        this.reverseScrolling = z;
        this.isVertical = z2;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        SemanticsPropertiesKt.setTraversalGroup(semanticsPropertyReceiver);
        ScrollAxisRange scrollAxisRange = new ScrollAxisRange(new Function0() { // from class: androidx.compose.foundation.ScrollNode$applySemantics$accessibilityScrollState$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(this.this$0.state.getValue());
            }
        }, new Function0() { // from class: androidx.compose.foundation.ScrollNode$applySemantics$accessibilityScrollState$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(this.this$0.state.getMaxValue());
            }
        }, this.reverseScrolling);
        if (this.isVertical) {
            SemanticsProperties.INSTANCE.getClass();
            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.VerticalScrollAxisRange;
            KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[11];
            semanticsPropertyKey.setValue(semanticsPropertyReceiver, scrollAxisRange);
            return;
        }
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.HorizontalScrollAxisRange;
        KProperty kProperty2 = SemanticsPropertiesKt.$$delegatedProperties[10];
        semanticsPropertyKey2.setValue(semanticsPropertyReceiver, scrollAxisRange);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!this.isVertical) {
            i = Integer.MAX_VALUE;
        }
        return intrinsicMeasurable.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (this.isVertical) {
            i = Integer.MAX_VALUE;
        }
        return intrinsicMeasurable.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        CheckScrollableContainerConstraintsKt.m32checkScrollableContainerConstraintsK40F9xA(this.isVertical ? Orientation.Vertical : Orientation.Horizontal, j);
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, this.isVertical ? Constraints.m823getMaxWidthimpl(j) : Integer.MAX_VALUE, 0, this.isVertical ? Integer.MAX_VALUE : Constraints.m822getMaxHeightimpl(j), 5));
        int i = placeableMo610measureBRTryo0.width;
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        if (i > iM823getMaxWidthimpl) {
            i = iM823getMaxWidthimpl;
        }
        int i2 = placeableMo610measureBRTryo0.height;
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        if (i2 > iM822getMaxHeightimpl) {
            i2 = iM822getMaxHeightimpl;
        }
        final int i3 = placeableMo610measureBRTryo0.height - i2;
        int i4 = placeableMo610measureBRTryo0.width - i;
        if (!this.isVertical) {
            i3 = i4;
        }
        ScrollState scrollState = this.state;
        ((SnapshotMutableIntStateImpl) scrollState._maxValueState).setIntValue(i3);
        Snapshot.Companion.getClass();
        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
        Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
        try {
            if (scrollState.getValue() > i3) {
                ((SnapshotMutableIntStateImpl) scrollState.value$delegate).setIntValue(i3);
            }
            Unit unit = Unit.INSTANCE;
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
            ((SnapshotMutableIntStateImpl) this.state.viewportSize$delegate).setIntValue(this.isVertical ? i2 : i);
            return measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.ScrollNode$measure$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                    int value = this.this$0.state.getValue();
                    int i5 = i3;
                    if (value < 0) {
                        value = 0;
                    }
                    if (value > i5) {
                        value = i5;
                    }
                    ScrollNode scrollNode = this.this$0;
                    final int i6 = scrollNode.reverseScrolling ? value - i5 : -value;
                    boolean z = scrollNode.isVertical;
                    final int i7 = z ? 0 : i6;
                    if (!z) {
                        i6 = 0;
                    }
                    final Placeable placeable = placeableMo610measureBRTryo0;
                    Function1 function1 = new Function1() { // from class: androidx.compose.foundation.ScrollNode$measure$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            Placeable.PlacementScope.placeRelativeWithLayer$default((Placeable.PlacementScope) obj2, placeable, i7, i6);
                            return Unit.INSTANCE;
                        }
                    };
                    placementScope.motionFrameOfReferencePlacement = true;
                    function1.mo781invoke(placementScope);
                    placementScope.motionFrameOfReferencePlacement = false;
                    return Unit.INSTANCE;
                }
            });
        } catch (Throwable th) {
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
            throw th;
        }
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!this.isVertical) {
            i = Integer.MAX_VALUE;
        }
        return intrinsicMeasurable.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (this.isVertical) {
            i = Integer.MAX_VALUE;
        }
        return intrinsicMeasurable.minIntrinsicWidth(i);
    }
}
