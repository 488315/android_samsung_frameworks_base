package com.android.compose.gesture;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.input.pointer.util.VelocityTrackerKt;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import com.android.compose.animation.scene.DraggableHandler;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.gesture.NestedDraggableNode;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final class NestedDraggableNode$detectDrags$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ VelocityTracker $velocityTracker;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$detectDrags$2(VelocityTracker velocityTracker, NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(2, continuation);
        this.$velocityTracker = velocityTracker;
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$detectDrags$2 nestedDraggableNode$detectDrags$2 = new NestedDraggableNode$detectDrags$2(this.$velocityTracker, this.this$0, continuation);
        nestedDraggableNode$detectDrags$2.L$0 = obj;
        return nestedDraggableNode$detectDrags$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$detectDrags$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0173, code lost:
    
        if (r2 == r0) goto L76;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x019d  */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1, kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.compose.gesture.NestedDraggable$Controller] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        AwaitPointerEventScope awaitPointerEventScope;
        Object objAwaitFirstDown$default;
        AwaitPointerEventScope awaitPointerEventScope2;
        PointerInputChange pointerInputChange;
        final Ref$FloatRef ref$FloatRef;
        Object objM69awaitHorizontalTouchSlopOrCancellationjO51t88;
        Throwable th;
        final NestedDraggable.Controller controllerM920onDragStartedw4f02Oo;
        Object objM73horizontalDragjO51t88;
        boolean zBooleanValue;
        Object objM76verticalDragjO51t88;
        PointerInputChange pointerInputChange2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        ?? r2 = this.label;
        if (r2 == 0) {
            ResultKt.throwOnFailure(obj);
            awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            this.L$0 = awaitPointerEventScope;
            this.label = 1;
            objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
            if (objAwaitFirstDown$default != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (r2 != 1) {
            if (r2 != 2) {
                try {
                    if (r2 == 3) {
                        NestedDraggable.Controller controller = (NestedDraggable.Controller) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        controllerM920onDragStartedw4f02Oo = controller;
                        objM73horizontalDragjO51t88 = obj;
                        zBooleanValue = ((Boolean) objM73horizontalDragjO51t88).booleanValue();
                        if (zBooleanValue) {
                        }
                        return Unit.INSTANCE;
                    }
                    if (r2 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    NestedDraggable.Controller controller2 = (NestedDraggable.Controller) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    controllerM920onDragStartedw4f02Oo = controller2;
                    objM76verticalDragjO51t88 = obj;
                    zBooleanValue = ((Boolean) objM76verticalDragjO51t88).booleanValue();
                    if (zBooleanValue) {
                        float maximumFlingVelocity = ((ViewConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(this.this$0, CompositionLocalsKt.LocalViewConfiguration)).getMaximumFlingVelocity();
                        long jM602calculateVelocityAH228Gc = this.$velocityTracker.m602calculateVelocityAH228Gc(VelocityKt.Velocity(maximumFlingVelocity, maximumFlingVelocity));
                        NestedDraggableNode nestedDraggableNode = this.this$0;
                        BuildersKt.launch$default(nestedDraggableNode.nestedScrollDispatcher.getCoroutineScope(), null, null, new NestedDraggableNode$onDragStopped$1(nestedDraggableNode, jM602calculateVelocityAH228Gc, controllerM920onDragStartedw4f02Oo, null), 3);
                    } else {
                        NestedDraggableNode nestedDraggableNode2 = this.this$0;
                        Velocity.Companion.getClass();
                        BuildersKt.launch$default(nestedDraggableNode2.nestedScrollDispatcher.getCoroutineScope(), null, null, new NestedDraggableNode$onDragStopped$1(nestedDraggableNode2, 0L, controllerM920onDragStartedw4f02Oo, null), 3);
                    }
                    return Unit.INSTANCE;
                } catch (Throwable th2) {
                    th = th2;
                    NestedDraggableNode nestedDraggableNode3 = this.this$0;
                    Velocity.Companion.getClass();
                    BuildersKt.launch$default(nestedDraggableNode3.nestedScrollDispatcher.getCoroutineScope(), null, null, new NestedDraggableNode$onDragStopped$1(nestedDraggableNode3, 0L, r2, null), 3);
                    throw th;
                }
            }
            ref$FloatRef = (Ref$FloatRef) this.L$2;
            pointerInputChange = (PointerInputChange) this.L$1;
            awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            objM69awaitHorizontalTouchSlopOrCancellationjO51t88 = obj;
            pointerInputChange2 = (PointerInputChange) objM69awaitHorizontalTouchSlopOrCancellationjO51t88;
            if (pointerInputChange2 != null) {
                this.$velocityTracker.resetTracking();
                float fSignum = Math.signum(this.this$0.m934toFloatk4lQ0M$1(PointerEventKt.positionChangeInternal(pointerInputChange2, true)));
                boolean z = fSignum == 0.0f;
                NestedDraggableNode nestedDraggableNode4 = this.this$0;
                if (z) {
                    StringBuilder sb = new StringBuilder("sign is equal to 0 ");
                    sb.append("touchSlop " + ((ViewConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(nestedDraggableNode4, CompositionLocalsKt.LocalViewConfiguration)).getTouchSlop() + " ");
                    sb.append("down.position " + Offset.m405toStringimpl(pointerInputChange.position) + " ");
                    sb.append("drag.position " + Offset.m405toStringimpl(pointerInputChange2.position) + " ");
                    sb.append("drag.previousPosition " + Offset.m405toStringimpl(pointerInputChange2.previousPosition));
                    throw new IllegalStateException(sb.toString().toString());
                }
                if (nestedDraggableNode4.pointersDown.size() <= 0) {
                    throw new IllegalStateException("pointersDown is empty");
                }
                NestedDraggableNode nestedDraggableNode5 = this.this$0;
                NestedDraggable nestedDraggable = nestedDraggableNode5.draggable;
                long j = pointerInputChange.position;
                int size = nestedDraggableNode5.pointersDown.size();
                controllerM920onDragStartedw4f02Oo = ((DraggableHandler) nestedDraggable).m920onDragStartedw4f02Oo(j, fSignum, size < 1 ? 1 : size, PointerType.m599boximpl(pointerInputChange2.type));
                float f = ref$FloatRef.element;
                if (f != 0.0f) {
                    NestedDraggableNode nestedDraggableNode6 = this.this$0;
                    VelocityTracker velocityTracker = this.$velocityTracker;
                    nestedDraggableNode6.getClass();
                    VelocityTrackerKt.addPointerInputChange(velocityTracker, pointerInputChange2);
                    if (!controllerM920onDragStartedw4f02Oo.isReadyToDrag()) {
                        nestedDraggableNode6.ignoreNextDrag = true;
                    } else if (nestedDraggableNode6.ignoreNextDrag) {
                        nestedDraggableNode6.ignoreNextDrag = false;
                    } else {
                        nestedDraggableNode6.m932scrollWithOverscroll8S9VItk(nestedDraggableNode6.m935toOffsettuRUvjQ$1(f), new NestedDraggableNode$$ExternalSyntheticLambda3(nestedDraggableNode6, controllerM920onDragStartedw4f02Oo, 1));
                    }
                }
                NestedDraggableNode.NestedScrollController nestedScrollController = this.this$0.nestedScrollController;
                if (nestedScrollController != null) {
                    nestedScrollController.ensureOnDragStoppedIsCalled();
                }
                final NestedDraggableNode nestedDraggableNode7 = this.this$0;
                nestedDraggableNode7.nestedScrollController = null;
                try {
                    final VelocityTracker velocityTracker2 = this.$velocityTracker;
                    ?? r10 = new Function1() { // from class: com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                            long jPositionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange3, false);
                            NestedDraggableNode nestedDraggableNode8 = nestedDraggableNode7;
                            float fM934toFloatk4lQ0M$1 = nestedDraggableNode8.m934toFloatk4lQ0M$1(jPositionChangeInternal);
                            VelocityTrackerKt.addPointerInputChange(velocityTracker2, pointerInputChange3);
                            NestedDraggable.Controller controller3 = controllerM920onDragStartedw4f02Oo;
                            if (!controller3.isReadyToDrag()) {
                                nestedDraggableNode8.ignoreNextDrag = true;
                            } else if (nestedDraggableNode8.ignoreNextDrag) {
                                nestedDraggableNode8.ignoreNextDrag = false;
                            } else {
                                nestedDraggableNode8.m932scrollWithOverscroll8S9VItk(nestedDraggableNode8.m935toOffsettuRUvjQ$1(fM934toFloatk4lQ0M$1), new NestedDraggableNode$$ExternalSyntheticLambda3(nestedDraggableNode8, controller3, 1));
                            }
                            pointerInputChange3.consume();
                            return Unit.INSTANCE;
                        }
                    };
                    int i = WhenMappings.$EnumSwitchMapping$0[nestedDraggableNode7.orientation.ordinal()];
                    long j2 = pointerInputChange2.id;
                    if (i == 1) {
                        this.L$0 = controllerM920onDragStartedw4f02Oo;
                        this.L$1 = null;
                        this.L$2 = null;
                        this.label = 3;
                        objM73horizontalDragjO51t88 = DragGestureDetectorKt.m73horizontalDragjO51t88(awaitPointerEventScope2, j2, r10, this);
                    } else {
                        if (i != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        this.L$0 = controllerM920onDragStartedw4f02Oo;
                        this.L$1 = null;
                        this.L$2 = null;
                        this.label = 4;
                        objM76verticalDragjO51t88 = DragGestureDetectorKt.m76verticalDragjO51t88(awaitPointerEventScope2, j2, r10, this);
                        if (objM76verticalDragjO51t88 == coroutineSingletons) {
                        }
                        zBooleanValue = ((Boolean) objM76verticalDragjO51t88).booleanValue();
                        if (zBooleanValue) {
                        }
                    }
                    return coroutineSingletons;
                } catch (Throwable th3) {
                    th = th3;
                    r2 = controllerM920onDragStartedw4f02Oo;
                    NestedDraggableNode nestedDraggableNode32 = this.this$0;
                    Velocity.Companion.getClass();
                    BuildersKt.launch$default(nestedDraggableNode32.nestedScrollDispatcher.getCoroutineScope(), null, null, new NestedDraggableNode$onDragStopped$1(nestedDraggableNode32, 0L, r2, null), 3);
                    throw th;
                }
            }
            return Unit.INSTANCE;
        }
        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        objAwaitFirstDown$default = obj;
        awaitPointerEventScope2 = awaitPointerEventScope;
        pointerInputChange = (PointerInputChange) objAwaitFirstDown$default;
        ref$FloatRef = new Ref$FloatRef();
        final NestedDraggableNode nestedDraggableNode8 = this.this$0;
        ?? r13 = new Function2() { // from class: com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                float fFloatValue = ((Float) obj3).floatValue();
                if (((DraggableHandler) nestedDraggableNode8.draggable).layoutImpl.swipeDetector.detectSwipe(pointerInputChange3)) {
                    pointerInputChange3.consume();
                    ref$FloatRef.element = fFloatValue;
                }
                return Unit.INSTANCE;
            }
        };
        long j3 = pointerInputChange.id;
        this.L$0 = awaitPointerEventScope2;
        this.L$1 = pointerInputChange;
        this.L$2 = ref$FloatRef;
        this.label = 2;
        int i2 = WhenMappings.$EnumSwitchMapping$0[nestedDraggableNode8.orientation.ordinal()];
        if (i2 == 1) {
            objM69awaitHorizontalTouchSlopOrCancellationjO51t88 = DragGestureDetectorKt.m69awaitHorizontalTouchSlopOrCancellationjO51t88(awaitPointerEventScope2, j3, r13, this);
        } else {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            objM69awaitHorizontalTouchSlopOrCancellationjO51t88 = DragGestureDetectorKt.m71awaitVerticalTouchSlopOrCancellationjO51t88(awaitPointerEventScope2, j3, r13, this);
        }
        if (objM69awaitHorizontalTouchSlopOrCancellationjO51t88 != coroutineSingletons) {
            pointerInputChange2 = (PointerInputChange) objM69awaitHorizontalTouchSlopOrCancellationjO51t88;
            if (pointerInputChange2 != null) {
            }
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }
}
