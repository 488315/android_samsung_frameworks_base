package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt;
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public AnchoredDraggableNode(AnchoredDraggableState<T> anchoredDraggableState, Orientation orientation, boolean z, Boolean bool, MutableInteractionSource mutableInteractionSource, OverscrollEffect overscrollEffect, Boolean bool2, FlingBehavior flingBehavior) {
        super(AnchoredDraggableKt.AlwaysDrag, z, mutableInteractionSource, orientation);
        this.state = anchoredDraggableState;
        this.orientation = orientation;
        this.reverseDirection = bool;
        this.overscrollEffect = overscrollEffect;
        this.startDragImmediately = bool2;
        this.flingBehavior = flingBehavior;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$fling(androidx.compose.foundation.gestures.AnchoredDraggableNode r6, float r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$1
            if (r0 == 0) goto L16
            r0 = r8
            androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$1 r0 = (androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$1 r0 = new androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            if (r2 == 0) goto L3d
            r6 = 1
            if (r2 == r6) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r6 = r0.L$0
            kotlin.jvm.internal.Ref$FloatRef r6 = (kotlin.jvm.internal.Ref$FloatRef) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L60
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            kotlin.ResultKt.throwOnFailure(r8)
            return r8
        L3d:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.foundation.gestures.AnchoredDraggableState r8 = r6.state
            r8.getClass()
            kotlin.jvm.internal.Ref$FloatRef r8 = new kotlin.jvm.internal.Ref$FloatRef
            r8.<init>()
            r8.element = r7
            androidx.compose.foundation.gestures.AnchoredDraggableState r2 = r6.state
            androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$2 r4 = new androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$2
            r5 = 0
            r4.<init>(r6, r8, r7, r5)
            r0.L$0 = r8
            r0.label = r3
            java.lang.Object r6 = androidx.compose.foundation.gestures.AnchoredDraggableState.anchoredDrag$default(r2, r4, r0)
            if (r6 != r1) goto L5f
            return r1
        L5f:
            r6 = r8
        L60:
            float r6 = r6.element
            java.lang.Float r7 = new java.lang.Float
            r7.<init>(r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AnchoredDraggableNode.access$fling(androidx.compose.foundation.gestures.AnchoredDraggableNode, float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: access$toOffset-tuRUvjQ, reason: not valid java name */
    public static final long m61access$toOffsettuRUvjQ(AnchoredDraggableNode anchoredDraggableNode, float f) {
        Orientation orientation = anchoredDraggableNode.orientation;
        float f2 = orientation == Orientation.Horizontal ? f : 0.0f;
        if (orientation != Orientation.Vertical) {
            f = 0.0f;
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(f2) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final Object drag(Function2 function2, Continuation continuation) {
        Object anchoredDrag$default = AnchoredDraggableState.anchoredDrag$default(this.state, new AnchoredDraggableNode$drag$2(function2, this, null), (ContinuationImpl) continuation);
        return anchoredDrag$default == CoroutineSingletons.COROUTINE_SUSPENDED ? anchoredDrag$default : Unit.INSTANCE;
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
    public final void mo63onDragStoppedTH1AsA0(long j) {
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
                    return Float.valueOf(Density.this.mo57toPx0680j_4(125));
                }
            };
            SnapLayoutInfoProvider snapLayoutInfoProvider = new SnapLayoutInfoProvider() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$AnchoredDraggableLayoutInfoProvider$1
                @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                public final float calculateApproachOffset(float f, float f2) {
                    return 0.0f;
                }

                /* JADX WARN: Code restructure failed: missing block: B:25:0x009a, code lost:
                
                    if (r4 != false) goto L31;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:26:0x00a1, code lost:
                
                    r9 = r10;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:27:0x00a3, code lost:
                
                    r9 = r7;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:29:0x009f, code lost:
                
                    if (r4 != false) goto L30;
                 */
                @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final float calculateSnapOffset(float r10) {
                    /*
                        r9 = this;
                        androidx.compose.foundation.gestures.AnchoredDraggableState r0 = r1
                        float r1 = r0.requireOffset()
                        androidx.compose.foundation.gestures.DraggableAnchors r2 = r0.getAnchors()
                        kotlin.jvm.functions.Function1 r3 = androidx.compose.foundation.gestures.AnchoredDraggableKt.AlwaysDrag
                        boolean r3 = java.lang.Float.isNaN(r1)
                        if (r3 != 0) goto Lb6
                        float r3 = java.lang.Math.abs(r10)
                        r4 = 0
                        int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                        r5 = 0
                        r6 = 1
                        if (r3 <= 0) goto L1f
                        r3 = r6
                        goto L20
                    L1f:
                        r3 = r5
                    L20:
                        if (r3 == 0) goto L28
                        int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
                        if (r4 <= 0) goto L28
                        r4 = r6
                        goto L29
                    L28:
                        r4 = r5
                    L29:
                        if (r3 != 0) goto L36
                        androidx.compose.foundation.gestures.DefaultDraggableAnchors r2 = (androidx.compose.foundation.gestures.DefaultDraggableAnchors) r2
                        java.lang.Object r9 = r2.closestAnchor(r1)
                        r9.getClass()
                        goto La4
                    L36:
                        float r10 = java.lang.Math.abs(r10)
                        kotlin.jvm.functions.Function0 r3 = r3
                        java.lang.Object r3 = r3.invoke()
                        java.lang.Number r3 = (java.lang.Number) r3
                        float r3 = r3.floatValue()
                        float r3 = java.lang.Math.abs(r3)
                        int r10 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
                        if (r10 < 0) goto L58
                        androidx.compose.foundation.gestures.DefaultDraggableAnchors r2 = (androidx.compose.foundation.gestures.DefaultDraggableAnchors) r2
                        java.lang.Object r9 = r2.closestAnchor(r1, r4)
                        r9.getClass()
                        goto La4
                    L58:
                        androidx.compose.foundation.gestures.DefaultDraggableAnchors r2 = (androidx.compose.foundation.gestures.DefaultDraggableAnchors) r2
                        java.lang.Object r10 = r2.closestAnchor(r1, r5)
                        r10.getClass()
                        float r3 = r2.positionOf(r10)
                        java.lang.Object r7 = r2.closestAnchor(r1, r6)
                        r7.getClass()
                        float r2 = r2.positionOf(r7)
                        float r8 = r3 - r2
                        float r8 = java.lang.Math.abs(r8)
                        java.lang.Float r8 = java.lang.Float.valueOf(r8)
                        kotlin.jvm.functions.Function1 r9 = r2
                        java.lang.Object r9 = r9.mo779invoke(r8)
                        java.lang.Number r9 = (java.lang.Number) r9
                        float r9 = r9.floatValue()
                        float r9 = java.lang.Math.abs(r9)
                        if (r4 == 0) goto L8d
                        goto L8e
                    L8d:
                        r3 = r2
                    L8e:
                        float r3 = r3 - r1
                        float r2 = java.lang.Math.abs(r3)
                        int r9 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
                        if (r9 < 0) goto L98
                        r5 = r6
                    L98:
                        if (r5 != r6) goto L9d
                        if (r4 == 0) goto La1
                        goto La3
                    L9d:
                        if (r5 != 0) goto Lb0
                        if (r4 == 0) goto La3
                    La1:
                        r9 = r10
                        goto La4
                    La3:
                        r9 = r7
                    La4:
                        androidx.compose.foundation.gestures.DraggableAnchors r10 = r0.getAnchors()
                        androidx.compose.foundation.gestures.DefaultDraggableAnchors r10 = (androidx.compose.foundation.gestures.DefaultDraggableAnchors) r10
                        float r9 = r10.positionOf(r9)
                        float r9 = r9 - r1
                        return r9
                    Lb0:
                        kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
                        r9.<init>()
                        throw r9
                    Lb6:
                        java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
                        java.lang.String r10 = "The offset provided to computeTarget must not be NaN."
                        r9.<init>(r10)
                        throw r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AnchoredDraggableKt$AnchoredDraggableLayoutInfoProvider$1.calculateSnapOffset(float):float");
                }
            };
            float f = SnapFlingBehaviorKt.MinFlingVelocityDp;
            flingBehavior = new SnapFlingBehavior(snapLayoutInfoProvider, decayAnimationSpec, tweenSpec);
        }
        this.resolvedFlingBehavior = flingBehavior;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStarted-k-4lQ0M, reason: not valid java name */
    public final void mo62onDragStartedk4lQ0M(long j) {
    }
}
