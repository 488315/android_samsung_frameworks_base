package androidx.compose.ui.layout;

import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.ui.layout.LayoutNodeSubcompositionsState;
import androidx.compose.ui.layout.SubcomposeLayoutState;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class SubcomposeLayoutState {
    public LayoutNodeSubcompositionsState _state;
    public final Function2 setCompositionContext;
    public final Function2 setMeasurePolicy;
    public final Function2 setRoot;
    public final SubcomposeSlotReusePolicy slotReusePolicy;

    public SubcomposeLayoutState(SubcomposeSlotReusePolicy subcomposeSlotReusePolicy) {
        this.slotReusePolicy = subcomposeSlotReusePolicy;
        this.setRoot = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutState$setRoot$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LayoutNode layoutNode = (LayoutNode) obj;
                SubcomposeLayoutState subcomposeLayoutState = this.this$0;
                LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = layoutNode.subcompositionsState;
                if (layoutNodeSubcompositionsState == null) {
                    layoutNodeSubcompositionsState = new LayoutNodeSubcompositionsState(layoutNode, subcomposeLayoutState.slotReusePolicy);
                    layoutNode.subcompositionsState = layoutNodeSubcompositionsState;
                }
                subcomposeLayoutState._state = layoutNodeSubcompositionsState;
                this.this$0.getState().makeSureStateIsConsistent();
                LayoutNodeSubcompositionsState state = this.this$0.getState();
                SubcomposeSlotReusePolicy subcomposeSlotReusePolicy2 = this.this$0.slotReusePolicy;
                if (state.slotReusePolicy != subcomposeSlotReusePolicy2) {
                    state.slotReusePolicy = subcomposeSlotReusePolicy2;
                    state.markActiveNodesAsReused(false);
                    LayoutNode.requestRemeasure$ui_release$default(state.root, false, 7);
                }
                return Unit.INSTANCE;
            }
        };
        this.setCompositionContext = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutState$setCompositionContext$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                this.this$0.getState().compositionContext = (CompositionContext) obj2;
                return Unit.INSTANCE;
            }
        };
        this.setMeasurePolicy = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutState$setMeasurePolicy$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                final Function2 function2 = (Function2) obj2;
                final LayoutNodeSubcompositionsState state = this.this$0.getState();
                ((LayoutNode) obj).setMeasurePolicy(new LayoutNode.NoIntrinsicsMeasurePolicy(state.NoIntrinsicsMessage) { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                        final LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = state;
                        layoutNodeSubcompositionsState.scope.layoutDirection = measureScope.getLayoutDirection();
                        float density = measureScope.getDensity();
                        LayoutNodeSubcompositionsState.Scope scope = layoutNodeSubcompositionsState.scope;
                        scope.density = density;
                        scope.fontScale = measureScope.getFontScale();
                        boolean zIsLookingAhead = measureScope.isLookingAhead();
                        Function2 function22 = function2;
                        if (zIsLookingAhead || layoutNodeSubcompositionsState.root.lookaheadRoot == null) {
                            layoutNodeSubcompositionsState.currentIndex = 0;
                            final MeasureResult measureResult = (MeasureResult) function22.invoke(scope, Constraints.m815boximpl(j));
                            final int i = layoutNodeSubcompositionsState.currentIndex;
                            return new MeasureResult() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1$measure-3p2s80s$$inlined$createMeasureResult$2
                                @Override // androidx.compose.ui.layout.MeasureResult
                                public final Map getAlignmentLines() {
                                    return measureResult.getAlignmentLines();
                                }

                                @Override // androidx.compose.ui.layout.MeasureResult
                                public final int getHeight() {
                                    return measureResult.getHeight();
                                }

                                @Override // androidx.compose.ui.layout.MeasureResult
                                public final Function1 getRulers() {
                                    return measureResult.getRulers();
                                }

                                @Override // androidx.compose.ui.layout.MeasureResult
                                public final int getWidth() {
                                    return measureResult.getWidth();
                                }

                                @Override // androidx.compose.ui.layout.MeasureResult
                                public final void placeChildren() {
                                    LayoutNodeSubcompositionsState layoutNodeSubcompositionsState2 = layoutNodeSubcompositionsState;
                                    layoutNodeSubcompositionsState2.currentIndex = i;
                                    measureResult.placeChildren();
                                    layoutNodeSubcompositionsState2.disposeOrReuseStartingFromIndex(layoutNodeSubcompositionsState2.currentIndex);
                                }
                            };
                        }
                        layoutNodeSubcompositionsState.currentApproachIndex = 0;
                        final MeasureResult measureResult2 = (MeasureResult) function22.invoke(layoutNodeSubcompositionsState.approachMeasureScope, Constraints.m815boximpl(j));
                        final int i2 = layoutNodeSubcompositionsState.currentApproachIndex;
                        return new MeasureResult() { // from class: androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1$measure-3p2s80s$$inlined$createMeasureResult$1
                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final Map getAlignmentLines() {
                                return measureResult2.getAlignmentLines();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final int getHeight() {
                                return measureResult2.getHeight();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final Function1 getRulers() {
                                return measureResult2.getRulers();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final int getWidth() {
                                return measureResult2.getWidth();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final void placeChildren() {
                                LayoutNodeSubcompositionsState layoutNodeSubcompositionsState2 = layoutNodeSubcompositionsState;
                                layoutNodeSubcompositionsState2.currentApproachIndex = i2;
                                measureResult2.placeChildren();
                                MutableScatterMap mutableScatterMap = layoutNodeSubcompositionsState2.approachPrecomposeSlotHandleMap;
                                long[] jArr = mutableScatterMap.metadata;
                                int length = jArr.length - 2;
                                if (length < 0) {
                                    return;
                                }
                                int i3 = 0;
                                while (true) {
                                    long j2 = jArr[i3];
                                    if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i4 = 8 - ((~(i3 - length)) >>> 31);
                                        for (int i5 = 0; i5 < i4; i5++) {
                                            if ((255 & j2) < 128) {
                                                int i6 = (i3 << 3) + i5;
                                                Object obj3 = mutableScatterMap.keys[i6];
                                                SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = (SubcomposeLayoutState.PrecomposedSlotHandle) mutableScatterMap.values[i6];
                                                int iIndexOf = layoutNodeSubcompositionsState2.approachComposedSlotIds.indexOf(obj3);
                                                if (iIndexOf < 0 || iIndexOf >= layoutNodeSubcompositionsState2.currentApproachIndex) {
                                                    precomposedSlotHandle.dispose();
                                                    mutableScatterMap.removeValueAt(i6);
                                                }
                                            }
                                            j2 >>= 8;
                                        }
                                        if (i4 != 8) {
                                            return;
                                        }
                                    }
                                    if (i3 == length) {
                                        return;
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                        };
                    }
                });
                return Unit.INSTANCE;
            }
        };
    }

    public final LayoutNodeSubcompositionsState getState() {
        LayoutNodeSubcompositionsState layoutNodeSubcompositionsState = this._state;
        if (layoutNodeSubcompositionsState != null) {
            return layoutNodeSubcompositionsState;
        }
        throw new IllegalArgumentException("SubcomposeLayoutState is not attached to SubcomposeLayout");
    }

    public SubcomposeLayoutState() {
        this(NoOpSubcomposeSlotReusePolicy.INSTANCE);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SubcomposeLayoutState(int i) {
        this(new FixedCountSubcomposeSlotReusePolicy(i));
        SubcomposeLayoutKt$ReusedSlotId$1 subcomposeLayoutKt$ReusedSlotId$1 = SubcomposeLayoutKt.ReusedSlotId;
    }

    public interface PrecomposedSlotHandle {
        void dispose();

        default int getPlaceablesCount() {
            return 0;
        }

        /* renamed from: getSize-YEO4UFw */
        default long mo622getSizeYEO4UFw(int i) {
            IntSize.Companion.getClass();
            return 0L;
        }

        default void traverseDescendants(Function1 function1) {
        }

        /* renamed from: premeasure-0kLqBqw */
        default void mo623premeasure0kLqBqw(int i, long j) {
        }
    }
}
