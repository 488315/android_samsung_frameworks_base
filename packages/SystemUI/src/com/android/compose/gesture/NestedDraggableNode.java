package com.android.compose.gesture;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollNode;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import com.android.compose.animation.scene.DraggableHandler;
import com.android.compose.animation.scene.SwipeToSceneKt;
import com.android.compose.gesture.NestedDraggable;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;

/* loaded from: classes.dex */
public final class NestedDraggableNode extends DelegatingNode implements PointerInputModifierNode, NestedScrollConnection, CompositionLocalConsumerModifierNode {
    public final SuspendingPointerInputModifierNode detectDragsDelegate;
    public NestedDraggable draggable;
    public boolean ignoreNextDrag;
    public boolean lastEventWasScrollWheel;
    public Offset lastFirstDown;
    public boolean nestedDragsEnabled;
    public NestedScrollController nestedScrollController;
    public final NestedScrollDispatcher nestedScrollDispatcher;
    public Orientation orientation;
    public OverscrollEffect overscrollEffect;
    public final LinkedHashMap pointersDown;
    public final SuspendingPointerInputModifierNode trackDownPositionDelegate;
    public final SuspendingPointerInputModifierNode trackWheelScroll;

    public final class NestedScrollController {
        public final NestedDraggable.Controller controller;
        public final OverscrollEffect overscrollEffect;

        public NestedScrollController(OverscrollEffect overscrollEffect, NestedDraggable.Controller controller) {
            this.overscrollEffect = overscrollEffect;
            this.controller = controller;
        }

        public final void ensureOnDragStoppedIsCalled() {
            BuildersKt.launch$default(NestedDraggableNode.this.nestedScrollDispatcher.getCoroutineScope(), null, null, new NestedDraggableNode$NestedScrollController$ensureOnDragStoppedIsCalled$1(this, null), 3);
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        /* renamed from: flingWithOverscroll-QWom1Mo, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object m937flingWithOverscrollQWom1Mo(long j, ContinuationImpl continuationImpl) {
            NestedDraggableNode$NestedScrollController$flingWithOverscroll$1 nestedDraggableNode$NestedScrollController$flingWithOverscroll$1;
            Throwable th;
            CompletableDeferred completableDeferred;
            if (continuationImpl instanceof NestedDraggableNode$NestedScrollController$flingWithOverscroll$1) {
                nestedDraggableNode$NestedScrollController$flingWithOverscroll$1 = (NestedDraggableNode$NestedScrollController$flingWithOverscroll$1) continuationImpl;
                int i = nestedDraggableNode$NestedScrollController$flingWithOverscroll$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    nestedDraggableNode$NestedScrollController$flingWithOverscroll$1.label = i - Integer.MIN_VALUE;
                } else {
                    nestedDraggableNode$NestedScrollController$flingWithOverscroll$1 = new NestedDraggableNode$NestedScrollController$flingWithOverscroll$1(this, continuationImpl);
                }
            }
            NestedDraggableNode$NestedScrollController$flingWithOverscroll$1 nestedDraggableNode$NestedScrollController$flingWithOverscroll$12 = nestedDraggableNode$NestedScrollController$flingWithOverscroll$1;
            Object obj = nestedDraggableNode$NestedScrollController$flingWithOverscroll$12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = nestedDraggableNode$NestedScrollController$flingWithOverscroll$12.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
                try {
                    NestedDraggableNode nestedDraggableNode = NestedDraggableNode.this;
                    OverscrollEffect overscrollEffect = this.overscrollEffect;
                    NestedDraggableNode$NestedScrollController$flingWithOverscroll$2 nestedDraggableNode$NestedScrollController$flingWithOverscroll$2 = new NestedDraggableNode$NestedScrollController$flingWithOverscroll$2(nestedDraggableNode, this, completableDeferredImplCompletableDeferred$default, null);
                    nestedDraggableNode$NestedScrollController$flingWithOverscroll$12.L$0 = completableDeferredImplCompletableDeferred$default;
                    nestedDraggableNode$NestedScrollController$flingWithOverscroll$12.label = 1;
                    Object objM931flingWithOverscrollxgHb9do = nestedDraggableNode.m931flingWithOverscrollxgHb9do(overscrollEffect, j, nestedDraggableNode$NestedScrollController$flingWithOverscroll$2, nestedDraggableNode$NestedScrollController$flingWithOverscroll$12);
                    if (objM931flingWithOverscrollxgHb9do == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = objM931flingWithOverscrollxgHb9do;
                    completableDeferred = completableDeferredImplCompletableDeferred$default;
                } catch (Throwable th2) {
                    th = th2;
                    completableDeferred = completableDeferredImplCompletableDeferred$default;
                    ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                    throw th;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                completableDeferred = (CompletableDeferred) nestedDraggableNode$NestedScrollController$flingWithOverscroll$12.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Throwable th3) {
                    th = th3;
                    ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                    throw th;
                }
            }
            long j2 = ((Velocity) obj).packedValue;
            ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
            return Velocity.m878boximpl(j2);
        }
    }

    public NestedDraggableNode(NestedDraggable nestedDraggable, Orientation orientation, OverscrollEffect overscrollEffect, boolean z) {
        this.draggable = nestedDraggable;
        this.orientation = orientation;
        this.overscrollEffect = overscrollEffect;
        this.nestedDragsEnabled = z;
        NestedScrollDispatcher nestedScrollDispatcher = new NestedScrollDispatcher();
        this.nestedScrollDispatcher = nestedScrollDispatcher;
        SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$trackWheelScroll$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = this.this$0;
                nestedDraggableNode.getClass();
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$trackWheelScroll$3(nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (objAwaitEachGesture != coroutineSingletons) {
                    objAwaitEachGesture = Unit.INSTANCE;
                }
                return objAwaitEachGesture == coroutineSingletons ? objAwaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode);
        this.trackWheelScroll = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode;
        SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode2 = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$trackDownPositionDelegate$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = this.this$0;
                nestedDraggableNode.getClass();
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$trackDownPosition$2(nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (objAwaitEachGesture != coroutineSingletons) {
                    objAwaitEachGesture = Unit.INSTANCE;
                }
                return objAwaitEachGesture == coroutineSingletons ? objAwaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode2);
        this.trackDownPositionDelegate = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode2;
        SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode3 = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$detectDragsDelegate$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = this.this$0;
                nestedDraggableNode.getClass();
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$detectDrags$2(new VelocityTracker(), nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (objAwaitEachGesture != coroutineSingletons) {
                    objAwaitEachGesture = Unit.INSTANCE;
                }
                return objAwaitEachGesture == coroutineSingletons ? objAwaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode3);
        this.detectDragsDelegate = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode3;
        this.pointersDown = new LinkedHashMap();
        delegate(new NestedScrollNode(this, nestedScrollDispatcher));
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /* renamed from: access$flingWithNestedScroll-TK7Wm2c, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m929access$flingWithNestedScrollTK7Wm2c(NestedDraggableNode nestedDraggableNode, long j, Function2 function2, ContinuationImpl continuationImpl) {
        NestedDraggableNode$flingWithNestedScroll$1 nestedDraggableNode$flingWithNestedScroll$1;
        NestedDraggableNode nestedDraggableNode2;
        long j2;
        long j3;
        long j4;
        nestedDraggableNode.getClass();
        if (continuationImpl instanceof NestedDraggableNode$flingWithNestedScroll$1) {
            nestedDraggableNode$flingWithNestedScroll$1 = (NestedDraggableNode$flingWithNestedScroll$1) continuationImpl;
            int i = nestedDraggableNode$flingWithNestedScroll$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedDraggableNode$flingWithNestedScroll$1.label = i - Integer.MIN_VALUE;
            } else {
                nestedDraggableNode$flingWithNestedScroll$1 = new NestedDraggableNode$flingWithNestedScroll$1(nestedDraggableNode, continuationImpl);
            }
        }
        NestedDraggableNode$flingWithNestedScroll$1 nestedDraggableNode$flingWithNestedScroll$12 = nestedDraggableNode$flingWithNestedScroll$1;
        Object objM584dispatchPreFlingQWom1Mo = nestedDraggableNode$flingWithNestedScroll$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedDraggableNode$flingWithNestedScroll$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objM584dispatchPreFlingQWom1Mo);
            nestedDraggableNode$flingWithNestedScroll$12.L$0 = nestedDraggableNode;
            nestedDraggableNode$flingWithNestedScroll$12.L$1 = function2;
            nestedDraggableNode$flingWithNestedScroll$12.J$0 = j;
            nestedDraggableNode$flingWithNestedScroll$12.label = 1;
            objM584dispatchPreFlingQWom1Mo = nestedDraggableNode.nestedScrollDispatcher.m584dispatchPreFlingQWom1Mo(j, nestedDraggableNode$flingWithNestedScroll$12);
            if (objM584dispatchPreFlingQWom1Mo != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j4 = nestedDraggableNode$flingWithNestedScroll$12.J$1;
                j3 = nestedDraggableNode$flingWithNestedScroll$12.J$0;
                ResultKt.throwOnFailure(objM584dispatchPreFlingQWom1Mo);
                return Velocity.m878boximpl(Velocity.m883plusAH228Gc(Velocity.m883plusAH228Gc(j3, j4), ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue));
            }
            j2 = nestedDraggableNode$flingWithNestedScroll$12.J$1;
            j3 = nestedDraggableNode$flingWithNestedScroll$12.J$0;
            nestedDraggableNode2 = (NestedDraggableNode) nestedDraggableNode$flingWithNestedScroll$12.L$0;
            ResultKt.throwOnFailure(objM584dispatchPreFlingQWom1Mo);
            long j5 = ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue;
            long jM882minusAH228Gc = Velocity.m882minusAH228Gc(j2, j5);
            NestedScrollDispatcher nestedScrollDispatcher = nestedDraggableNode2.nestedScrollDispatcher;
            nestedDraggableNode$flingWithNestedScroll$12.L$0 = null;
            nestedDraggableNode$flingWithNestedScroll$12.J$0 = j3;
            nestedDraggableNode$flingWithNestedScroll$12.J$1 = j5;
            nestedDraggableNode$flingWithNestedScroll$12.label = 3;
            objM584dispatchPreFlingQWom1Mo = nestedScrollDispatcher.m582dispatchPostFlingRZ2iAVY(j5, jM882minusAH228Gc, nestedDraggableNode$flingWithNestedScroll$12);
            if (objM584dispatchPreFlingQWom1Mo != coroutineSingletons) {
                j4 = j5;
                return Velocity.m878boximpl(Velocity.m883plusAH228Gc(Velocity.m883plusAH228Gc(j3, j4), ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue));
            }
            return coroutineSingletons;
        }
        j = nestedDraggableNode$flingWithNestedScroll$12.J$0;
        function2 = (Function2) nestedDraggableNode$flingWithNestedScroll$12.L$1;
        nestedDraggableNode = (NestedDraggableNode) nestedDraggableNode$flingWithNestedScroll$12.L$0;
        ResultKt.throwOnFailure(objM584dispatchPreFlingQWom1Mo);
        long j6 = ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue;
        long jM882minusAH228Gc2 = Velocity.m882minusAH228Gc(j, j6);
        Velocity velocityM878boximpl = Velocity.m878boximpl(jM882minusAH228Gc2);
        nestedDraggableNode$flingWithNestedScroll$12.L$0 = nestedDraggableNode;
        nestedDraggableNode$flingWithNestedScroll$12.L$1 = null;
        nestedDraggableNode$flingWithNestedScroll$12.J$0 = j6;
        nestedDraggableNode$flingWithNestedScroll$12.J$1 = jM882minusAH228Gc2;
        nestedDraggableNode$flingWithNestedScroll$12.label = 2;
        objM584dispatchPreFlingQWom1Mo = function2.invoke(velocityM878boximpl, nestedDraggableNode$flingWithNestedScroll$12);
        if (objM584dispatchPreFlingQWom1Mo != coroutineSingletons) {
            nestedDraggableNode2 = nestedDraggableNode;
            j2 = jM882minusAH228Gc2;
            j3 = j6;
            long j52 = ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue;
            long jM882minusAH228Gc3 = Velocity.m882minusAH228Gc(j2, j52);
            NestedScrollDispatcher nestedScrollDispatcher2 = nestedDraggableNode2.nestedScrollDispatcher;
            nestedDraggableNode$flingWithNestedScroll$12.L$0 = null;
            nestedDraggableNode$flingWithNestedScroll$12.J$0 = j3;
            nestedDraggableNode$flingWithNestedScroll$12.J$1 = j52;
            nestedDraggableNode$flingWithNestedScroll$12.label = 3;
            objM584dispatchPreFlingQWom1Mo = nestedScrollDispatcher2.m582dispatchPostFlingRZ2iAVY(j52, jM882minusAH228Gc3, nestedDraggableNode$flingWithNestedScroll$12);
            if (objM584dispatchPreFlingQWom1Mo != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
    
        if (r8.mo19applyToFlingBMRW4eQ(r5, r7, r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: flingWithOverscroll-xgHb9do, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m931flingWithOverscrollxgHb9do(OverscrollEffect overscrollEffect, long j, Function2 function2, ContinuationImpl continuationImpl) {
        NestedDraggableNode$flingWithOverscroll$2 nestedDraggableNode$flingWithOverscroll$2;
        if (continuationImpl instanceof NestedDraggableNode$flingWithOverscroll$2) {
            nestedDraggableNode$flingWithOverscroll$2 = (NestedDraggableNode$flingWithOverscroll$2) continuationImpl;
            int i = nestedDraggableNode$flingWithOverscroll$2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedDraggableNode$flingWithOverscroll$2.label = i - Integer.MIN_VALUE;
            } else {
                nestedDraggableNode$flingWithOverscroll$2 = new NestedDraggableNode$flingWithOverscroll$2(this, continuationImpl);
            }
        }
        Object obj = nestedDraggableNode$flingWithOverscroll$2.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedDraggableNode$flingWithOverscroll$2.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            long jM936toVelocityadjELrA$1 = m936toVelocityadjELrA$1(m933toFloatTH1AsA0$1(j));
            if (overscrollEffect != null) {
                Function2 nestedDraggableNode$flingWithOverscroll$3 = new NestedDraggableNode$flingWithOverscroll$3(function2, null);
                nestedDraggableNode$flingWithOverscroll$2.J$0 = j;
                nestedDraggableNode$flingWithOverscroll$2.label = 1;
            } else {
                Object objM878boximpl = Velocity.m878boximpl(jM936toVelocityadjELrA$1);
                nestedDraggableNode$flingWithOverscroll$2.label = 2;
                Object objInvoke = function2.invoke(objM878boximpl, nestedDraggableNode$flingWithOverscroll$2);
                if (objInvoke != obj2) {
                    return objInvoke;
                }
            }
            return obj2;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        j = nestedDraggableNode$flingWithOverscroll$2.J$0;
        ResultKt.throwOnFailure(obj);
        return Velocity.m878boximpl(j);
    }

    public final void maybeCreateNewController(float f, Function0 function0) {
        if (this.nestedDragsEnabled && this.nestedScrollController == null && !this.lastEventWasScrollWheel && this.lastFirstDown != null && ((Boolean) function0.invoke()).booleanValue()) {
            int size = this.pointersDown.size();
            int i = size < 1 ? 1 : size;
            Map.Entry entry = (Map.Entry) CollectionsKt___CollectionsKt.firstOrNull(this.pointersDown.entrySet());
            PointerType pointerType = entry != null ? (PointerType) entry.getValue() : null;
            Offset offset = this.lastFirstDown;
            if (offset == null) {
                throw new IllegalStateException("Required value was null.");
            }
            this.nestedScrollController = new NestedScrollController(this.overscrollEffect, ((DraggableHandler) this.draggable).m920onDragStartedw4f02Oo(offset.packedValue, f, i, pointerType));
        }
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        ((SuspendingPointerInputModifierNodeImpl) this.trackWheelScroll).onCancelPointerInput();
        ((SuspendingPointerInputModifierNodeImpl) this.trackDownPositionDelegate).onCancelPointerInput();
        ((SuspendingPointerInputModifierNodeImpl) this.detectDragsDelegate).onCancelPointerInput();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        NestedScrollController nestedScrollController = this.nestedScrollController;
        if (nestedScrollController != null) {
            nestedScrollController.ensureOnDragStoppedIsCalled();
        }
        this.nestedScrollController = null;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        ((SuspendingPointerInputModifierNodeImpl) this.trackWheelScroll).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
        ((SuspendingPointerInputModifierNodeImpl) this.trackDownPositionDelegate).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
        ((SuspendingPointerInputModifierNodeImpl) this.detectDragsDelegate).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M */
    public final long mo79onPostScrollDzOQY0M(int i, long j, long j2) {
        NestedScrollSource.Companion.getClass();
        if (i == NestedScrollSource.SideEffect) {
            if (this.nestedScrollController != null) {
                throw new IllegalStateException("Check failed.");
            }
            Offset.Companion.getClass();
            return 0L;
        }
        float fM934toFloatk4lQ0M$1 = m934toFloatk4lQ0M$1(j2);
        if (fM934toFloatk4lQ0M$1 == 0.0f) {
            Offset.Companion.getClass();
            return 0L;
        }
        final float fSignum = Math.signum(fM934toFloatk4lQ0M$1);
        maybeCreateNewController(fSignum, new Function0(fSignum) { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DraggableHandler draggableHandler = (DraggableHandler) this.f$0.draggable;
                return Boolean.valueOf(SwipeToSceneKt.enabled(draggableHandler, draggableHandler.layoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout()));
            }
        });
        NestedScrollController nestedScrollController = this.nestedScrollController;
        if (nestedScrollController == null) {
            Offset.Companion.getClass();
            return 0L;
        }
        if (!nestedScrollController.controller.isReadyToDrag()) {
            this.ignoreNextDrag = true;
            return j2;
        }
        if (!this.ignoreNextDrag) {
            return m932scrollWithOverscroll8S9VItk(j2, new NestedDraggableNode$$ExternalSyntheticLambda3(this, nestedScrollController, 0));
        }
        this.ignoreNextDrag = false;
        return j2;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreFling-QWom1Mo */
    public final Object mo289onPreFlingQWom1Mo(long j, Continuation continuation) {
        NestedScrollController nestedScrollController = this.nestedScrollController;
        if (nestedScrollController == null) {
            Velocity.Companion.getClass();
            return Velocity.m878boximpl(0L);
        }
        this.nestedScrollController = null;
        Object objAwaitInternal = BuildersKt.async$default(this.nestedScrollDispatcher.getCoroutineScope(), null, new NestedDraggableNode$onPreFling$2(nestedScrollController, j, null), 3).awaitInternal(continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objAwaitInternal;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreScroll-OzD1aCk */
    public final long mo176onPreScrollOzD1aCk(final int i, long j) {
        final float fSignum = Math.signum(m934toFloatk4lQ0M$1(j));
        maybeCreateNewController(fSignum, new Function0(i, this, fSignum) { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda2
            public final /* synthetic */ int f$0;
            public final /* synthetic */ NestedDraggableNode f$1;

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NestedScrollSource.Companion.getClass();
                if (this.f$0 == NestedScrollSource.UserInput) {
                    this.f$1.draggable.getClass();
                }
                return Boolean.FALSE;
            }
        });
        NestedScrollController nestedScrollController = this.nestedScrollController;
        if (nestedScrollController == null) {
            Offset.Companion.getClass();
            return 0L;
        }
        if (!nestedScrollController.controller.isReadyToDrag()) {
            this.ignoreNextDrag = true;
            return j;
        }
        if (!this.ignoreNextDrag) {
            return m932scrollWithOverscroll8S9VItk(j, new NestedDraggableNode$$ExternalSyntheticLambda3(this, nestedScrollController, 0));
        }
        this.ignoreNextDrag = false;
        return j;
    }

    /* renamed from: scrollWithOverscroll-8S9VItk, reason: not valid java name */
    public final long m932scrollWithOverscroll8S9VItk(long j, final Function1 function1) {
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        if (overscrollEffect == null) {
            return ((Offset) function1.mo781invoke(Offset.m395boximpl(j))).packedValue;
        }
        NestedScrollSource.Companion.getClass();
        return overscrollEffect.mo20applyToScrollRhakbz0(NestedScrollSource.UserInput, j, new Function1() { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return (Offset) function1.mo781invoke((Offset) obj);
            }
        });
    }

    /* renamed from: toFloat-TH1AsA0$1, reason: not valid java name */
    public float m933toFloatTH1AsA0$1(long j) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            return Velocity.m880getXimpl(j);
        }
        if (i == 2) {
            return Velocity.m881getYimpl(j);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* renamed from: toFloat-k-4lQ0M$1, reason: not valid java name */
    public float m934toFloatk4lQ0M$1(long j) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            return Float.intBitsToFloat((int) (j >> 32));
        }
        if (i == 2) {
            return Float.intBitsToFloat((int) (j & 4294967295L));
        }
        throw new NoWhenBranchMatchedException();
    }

    /* renamed from: toOffset-tuRUvjQ$1, reason: not valid java name */
    public long m935toOffsettuRUvjQ$1(float f) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return jFloatToRawIntBits;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
        Offset.Companion companion2 = Offset.Companion;
        return jFloatToRawIntBits2;
    }

    /* renamed from: toVelocity-adjELrA$1, reason: not valid java name */
    public long m936toVelocityadjELrA$1(float f) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            return VelocityKt.Velocity(f, 0.0f);
        }
        if (i == 2) {
            return VelocityKt.Velocity(0.0f, f);
        }
        throw new NoWhenBranchMatchedException();
    }
}
