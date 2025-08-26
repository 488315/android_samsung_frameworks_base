package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt;
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final class AnchoredDraggableNode<T> extends DragGestureNode {
    public Density density;
    public FlingBehavior flingBehavior;
    public Orientation orientation;
    public OverscrollEffect overscrollEffect;
    public FlingBehavior resolvedFlingBehavior;
    public Boolean reverseDirection;
    public Boolean startDragImmediately;
    public AnchoredDraggableState state;

    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableNode$drag$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function2 $forEachDelta;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AnchoredDraggableNode<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, AnchoredDraggableNode<Object> anchoredDraggableNode, Continuation continuation) {
            super(3, continuation);
            this.$forEachDelta = function2;
            this.this$0 = anchoredDraggableNode;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$forEachDelta, this.this$0, (Continuation) obj3);
            anonymousClass2.L$0 = (AnchoredDragScope) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
                Function2 function2 = this.$forEachDelta;
                final AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableNode.drag.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        AnchoredDraggableNode<Object> anchoredDraggableNode2 = anchoredDraggableNode;
                        long jM404timestuRUvjQ = Offset.m404timestuRUvjQ(anchoredDraggableNode2.isReverseDirection() ? -1.0f : 1.0f, ((DragEvent.DragDelta) obj2).delta);
                        float fIntBitsToFloat = Float.intBitsToFloat((int) (anchoredDraggableNode2.orientation == Orientation.Vertical ? jM404timestuRUvjQ & 4294967295L : jM404timestuRUvjQ >> 32));
                        AnchoredDraggableNode<Object> anchoredDraggableNode3 = anchoredDraggableNode;
                        OverscrollEffect overscrollEffect = anchoredDraggableNode3.overscrollEffect;
                        if (overscrollEffect == null) {
                            ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(anchoredDraggableNode3.state.newOffsetForDelta$foundation_release(fIntBitsToFloat), 0.0f);
                        } else {
                            long jM62access$toOffsettuRUvjQ = AnchoredDraggableNode.m62access$toOffsettuRUvjQ(anchoredDraggableNode3, fIntBitsToFloat);
                            NestedScrollSource.Companion.getClass();
                            int i2 = NestedScrollSource.UserInput;
                            final AnchoredDraggableNode<Object> anchoredDraggableNode4 = anchoredDraggableNode;
                            final AnchoredDragScope anchoredDragScope2 = anchoredDragScope;
                            overscrollEffect.mo20applyToScrollRhakbz0(i2, jM62access$toOffsettuRUvjQ, new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableNode.drag.2.1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj3) {
                                    long j = ((Offset) obj3).packedValue;
                                    AnchoredDraggableNode<Object> anchoredDraggableNode5 = anchoredDraggableNode4;
                                    float fNewOffsetForDelta$foundation_release = anchoredDraggableNode5.state.newOffsetForDelta$foundation_release(Float.intBitsToFloat((int) (anchoredDraggableNode5.orientation == Orientation.Vertical ? j & 4294967295L : j >> 32)));
                                    AnchoredDraggableNode<Object> anchoredDraggableNode6 = anchoredDraggableNode4;
                                    long jM62access$toOffsettuRUvjQ2 = AnchoredDraggableNode.m62access$toOffsettuRUvjQ(anchoredDraggableNode6, fNewOffsetForDelta$foundation_release - anchoredDraggableNode6.state.requireOffset());
                                    ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope2).dragTo(fNewOffsetForDelta$foundation_release, 0.0f);
                                    return Offset.m395boximpl(jM62access$toOffsettuRUvjQ2);
                                }
                            });
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (function2.invoke(function1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public AnchoredDraggableNode(AnchoredDraggableState<T> anchoredDraggableState, Orientation orientation, boolean z, Boolean bool, MutableInteractionSource mutableInteractionSource, OverscrollEffect overscrollEffect, Boolean bool2, FlingBehavior flingBehavior) {
        super(AnchoredDraggableKt.AlwaysDrag, z, mutableInteractionSource, orientation);
        this.state = anchoredDraggableState;
        this.orientation = orientation;
        this.reverseDirection = bool;
        this.overscrollEffect = overscrollEffect;
        this.startDragImmediately = bool2;
        this.flingBehavior = flingBehavior;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$fling(AnchoredDraggableNode anchoredDraggableNode, float f, ContinuationImpl continuationImpl) {
        AnchoredDraggableNode$fling$1 anchoredDraggableNode$fling$1;
        Ref$FloatRef ref$FloatRef;
        anchoredDraggableNode.getClass();
        if (continuationImpl instanceof AnchoredDraggableNode$fling$1) {
            anchoredDraggableNode$fling$1 = (AnchoredDraggableNode$fling$1) continuationImpl;
            int i = anchoredDraggableNode$fling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anchoredDraggableNode$fling$1.label = i - Integer.MIN_VALUE;
            } else {
                anchoredDraggableNode$fling$1 = new AnchoredDraggableNode$fling$1(anchoredDraggableNode, continuationImpl);
            }
        }
        Object obj = anchoredDraggableNode$fling$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anchoredDraggableNode$fling$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anchoredDraggableNode.state.getClass();
            Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            ref$FloatRef2.element = f;
            AnchoredDraggableState anchoredDraggableState = anchoredDraggableNode.state;
            AnchoredDraggableNode$fling$2 anchoredDraggableNode$fling$2 = new AnchoredDraggableNode$fling$2(anchoredDraggableNode, ref$FloatRef2, f, null);
            anchoredDraggableNode$fling$1.L$0 = ref$FloatRef2;
            anchoredDraggableNode$fling$1.label = 2;
            if (AnchoredDraggableState.anchoredDrag$default(anchoredDraggableState, anchoredDraggableNode$fling$2, anchoredDraggableNode$fling$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$FloatRef = ref$FloatRef2;
        } else {
            if (i2 == 1) {
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$FloatRef = (Ref$FloatRef) anchoredDraggableNode$fling$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new Float(ref$FloatRef.element);
    }

    /* renamed from: access$toOffset-tuRUvjQ, reason: not valid java name */
    public static final long m62access$toOffsettuRUvjQ(AnchoredDraggableNode anchoredDraggableNode, float f) {
        Orientation orientation = anchoredDraggableNode.orientation;
        float f2 = orientation == Orientation.Horizontal ? f : 0.0f;
        if (orientation != Orientation.Vertical) {
            f = 0.0f;
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(f2) << 32);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final Object drag(Function2 function2, Continuation continuation) {
        Object objAnchoredDrag$default = AnchoredDraggableState.anchoredDrag$default(this.state, new AnonymousClass2(function2, this, null), (ContinuationImpl) continuation);
        return objAnchoredDrag$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnchoredDrag$default : Unit.INSTANCE;
    }

    public final boolean isReverseDirection() {
        Boolean bool = this.reverseDirection;
        if (bool == null) {
            return DelegatableNodeKt.requireLayoutNode(this).layoutDirection == LayoutDirection.Rtl && this.orientation == Orientation.Horizontal;
        }
        bool.getClass();
        return bool.booleanValue();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        updateFlingBehavior(this.flingBehavior);
    }

    @Override // androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.PointerInputModifierNode
    public final void onDensityChange() {
        onCancelPointerInput();
        if (this.isAttached) {
            Density density = DelegatableNodeKt.requireLayoutNode(this).density;
            Density density2 = this.density;
            if (density2 == null || !density2.equals(density)) {
                this.density = density;
                updateFlingBehavior(this.flingBehavior);
            }
        }
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStopped-TH1AsA0, reason: not valid java name */
    public final void mo64onDragStoppedTH1AsA0(long j) {
        if (this.isAttached) {
            BuildersKt.launch$default(getCoroutineScope(), null, null, new AnchoredDraggableNode$onDragStopped$1(this, j, null), 3);
        }
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final boolean startDragImmediately() {
        Boolean bool = this.startDragImmediately;
        return bool != null ? bool.booleanValue() : ((SnapshotMutableStateImpl) this.state.dragTarget$delegate).getValue() != null;
    }

    public final void updateFlingBehavior(FlingBehavior flingBehavior) {
        if (flingBehavior == null) {
            AnchoredDraggableDefaults.INSTANCE.getClass();
            TweenSpec tweenSpec = AnchoredDraggableDefaults.SnapAnimationSpec;
            final Function1 function1 = AnchoredDraggableDefaults.PositionalThreshold;
            final Density density = DelegatableNodeKt.requireLayoutNode(this).density;
            this.density = density;
            final AnchoredDraggableState anchoredDraggableState = this.state;
            DecayAnimationSpec decayAnimationSpec = AnchoredDraggableKt.NoOpDecayAnimationSpec;
            final Function0 function0 = new Function0() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$anchoredDraggableFlingBehavior$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Dp.Companion companion = Dp.Companion;
                    return Float.valueOf(density.mo58toPx0680j_4(125));
                }
            };
            SnapLayoutInfoProvider snapLayoutInfoProvider = new SnapLayoutInfoProvider() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$AnchoredDraggableLayoutInfoProvider$1
                @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                public final float calculateApproachOffset(float f, float f2) {
                    return 0.0f;
                }

                /* JADX WARN: Removed duplicated region for block: B:30:0x00a1  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x00a3  */
                @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final float calculateSnapOffset(float f) {
                    Object objClosestAnchor;
                    AnchoredDraggableState anchoredDraggableState2 = anchoredDraggableState;
                    float fRequireOffset = anchoredDraggableState2.requireOffset();
                    DraggableAnchors anchors = anchoredDraggableState2.getAnchors();
                    Function1 function12 = AnchoredDraggableKt.AlwaysDrag;
                    if (Float.isNaN(fRequireOffset)) {
                        throw new IllegalArgumentException("The offset provided to computeTarget must not be NaN.");
                    }
                    boolean z = Math.abs(f) > 0.0f;
                    boolean z2 = z && f > 0.0f;
                    if (!z) {
                        objClosestAnchor = ((DefaultDraggableAnchors) anchors).closestAnchor(fRequireOffset);
                        objClosestAnchor.getClass();
                    } else if (Math.abs(f) >= Math.abs(((Number) function0.invoke()).floatValue())) {
                        objClosestAnchor = ((DefaultDraggableAnchors) anchors).closestAnchor(fRequireOffset, z2);
                        objClosestAnchor.getClass();
                    } else {
                        DefaultDraggableAnchors defaultDraggableAnchors = (DefaultDraggableAnchors) anchors;
                        Object objClosestAnchor2 = defaultDraggableAnchors.closestAnchor(fRequireOffset, false);
                        objClosestAnchor2.getClass();
                        float fPositionOf = defaultDraggableAnchors.positionOf(objClosestAnchor2);
                        Object objClosestAnchor3 = defaultDraggableAnchors.closestAnchor(fRequireOffset, true);
                        objClosestAnchor3.getClass();
                        float fPositionOf2 = defaultDraggableAnchors.positionOf(objClosestAnchor3);
                        float fAbs = Math.abs(((Number) function1.mo781invoke(Float.valueOf(Math.abs(fPositionOf - fPositionOf2)))).floatValue());
                        if (!z2) {
                            fPositionOf = fPositionOf2;
                        }
                        boolean z3 = Math.abs(fPositionOf - fRequireOffset) >= fAbs;
                        if (z3) {
                            objClosestAnchor = z2 ? objClosestAnchor3 : objClosestAnchor2;
                        } else {
                            if (z3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            if (z2) {
                            }
                        }
                    }
                    return ((DefaultDraggableAnchors) anchoredDraggableState2.getAnchors()).positionOf(objClosestAnchor) - fRequireOffset;
                }
            };
            float f = SnapFlingBehaviorKt.MinFlingVelocityDp;
            flingBehavior = new SnapFlingBehavior(snapLayoutInfoProvider, decayAnimationSpec, tweenSpec);
        }
        this.resolvedFlingBehavior = flingBehavior;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStarted-k-4lQ0M, reason: not valid java name */
    public final void mo63onDragStartedk4lQ0M(long j) {
    }
}
