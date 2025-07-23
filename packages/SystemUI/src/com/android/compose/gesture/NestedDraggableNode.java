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
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Removed duplicated region for block: B:24:0x0038  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
        /* renamed from: flingWithOverscroll-QWom1Mo, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object m935flingWithOverscrollQWom1Mo(long r9, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
            /*
                r8 = this;
                boolean r0 = r11 instanceof com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$1
                if (r0 == 0) goto L14
                r0 = r11
                com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$1 r0 = (com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L14
                int r1 = r1 - r2
                r0.label = r1
            L12:
                r6 = r0
                goto L1a
            L14:
                com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$1 r0 = new com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$1
                r0.<init>(r8, r11)
                goto L12
            L1a:
                java.lang.Object r11 = r6.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r6.label
                r2 = 1
                if (r1 == 0) goto L38
                if (r1 != r2) goto L30
                java.lang.Object r8 = r6.L$0
                kotlinx.coroutines.CompletableDeferred r8 = (kotlinx.coroutines.CompletableDeferred) r8
                kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L2d
                goto L59
            L2d:
                r0 = move-exception
                r9 = r0
                goto L6e
            L30:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L38:
                kotlin.ResultKt.throwOnFailure(r11)
                kotlinx.coroutines.CompletableDeferredImpl r11 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default()
                com.android.compose.gesture.NestedDraggableNode r1 = com.android.compose.gesture.NestedDraggableNode.this     // Catch: java.lang.Throwable -> L6b
                r3 = r2
                androidx.compose.foundation.OverscrollEffect r2 = r8.overscrollEffect     // Catch: java.lang.Throwable -> L6b
                com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$2 r5 = new com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$2     // Catch: java.lang.Throwable -> L6b
                r4 = 0
                r5.<init>(r1, r8, r11, r4)     // Catch: java.lang.Throwable -> L6b
                r6.L$0 = r11     // Catch: java.lang.Throwable -> L6b
                r6.label = r3     // Catch: java.lang.Throwable -> L6b
                r3 = r9
                java.lang.Object r8 = r1.m929flingWithOverscrollxgHb9do(r2, r3, r5, r6)     // Catch: java.lang.Throwable -> L6b
                if (r8 != r0) goto L56
                return r0
            L56:
                r7 = r11
                r11 = r8
                r8 = r7
            L59:
                androidx.compose.ui.unit.Velocity r11 = (androidx.compose.ui.unit.Velocity) r11     // Catch: java.lang.Throwable -> L2d
                long r9 = r11.packedValue     // Catch: java.lang.Throwable -> L2d
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                kotlinx.coroutines.CompletableDeferredImpl r8 = (kotlinx.coroutines.CompletableDeferredImpl) r8
                r8.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r11)
                androidx.compose.ui.unit.Velocity r8 = androidx.compose.ui.unit.Velocity.m876boximpl(r9)
                return r8
            L69:
                r8 = r11
                goto L6e
            L6b:
                r0 = move-exception
                r9 = r0
                goto L69
            L6e:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                kotlinx.coroutines.CompletableDeferredImpl r8 = (kotlinx.coroutines.CompletableDeferredImpl) r8
                r8.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r10)
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.NestedDraggableNode.NestedScrollController.m935flingWithOverscrollQWom1Mo(long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
        }
    }

    public NestedDraggableNode(NestedDraggable nestedDraggable, Orientation orientation, OverscrollEffect overscrollEffect, boolean z) {
        this.draggable = nestedDraggable;
        this.orientation = orientation;
        this.overscrollEffect = overscrollEffect;
        this.nestedDragsEnabled = z;
        NestedScrollDispatcher nestedScrollDispatcher = new NestedScrollDispatcher();
        this.nestedScrollDispatcher = nestedScrollDispatcher;
        SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$trackWheelScroll$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = NestedDraggableNode.this;
                nestedDraggableNode.getClass();
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$trackWheelScroll$3(nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (awaitEachGesture != coroutineSingletons) {
                    awaitEachGesture = Unit.INSTANCE;
                }
                return awaitEachGesture == coroutineSingletons ? awaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(SuspendingPointerInputModifierNode);
        this.trackWheelScroll = SuspendingPointerInputModifierNode;
        SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode2 = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$trackDownPositionDelegate$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = NestedDraggableNode.this;
                nestedDraggableNode.getClass();
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$trackDownPosition$2(nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (awaitEachGesture != coroutineSingletons) {
                    awaitEachGesture = Unit.INSTANCE;
                }
                return awaitEachGesture == coroutineSingletons ? awaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(SuspendingPointerInputModifierNode2);
        this.trackDownPositionDelegate = SuspendingPointerInputModifierNode2;
        SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode3 = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: com.android.compose.gesture.NestedDraggableNode$detectDragsDelegate$1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                NestedDraggableNode nestedDraggableNode = NestedDraggableNode.this;
                nestedDraggableNode.getClass();
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new NestedDraggableNode$detectDrags$2(new VelocityTracker(), nestedDraggableNode, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (awaitEachGesture != coroutineSingletons) {
                    awaitEachGesture = Unit.INSTANCE;
                }
                return awaitEachGesture == coroutineSingletons ? awaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(SuspendingPointerInputModifierNode3);
        this.detectDragsDelegate = SuspendingPointerInputModifierNode3;
        this.pointersDown = new LinkedHashMap();
        delegate(new NestedScrollNode(this, nestedScrollDispatcher));
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006c, code lost:
    
        if (r13 == r0) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0029  */
    /* renamed from: access$flingWithNestedScroll-TK7Wm2c, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m927access$flingWithNestedScrollTK7Wm2c(com.android.compose.gesture.NestedDraggableNode r9, long r10, kotlin.jvm.functions.Function2 r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            r9.getClass()
            boolean r0 = r13 instanceof com.android.compose.gesture.NestedDraggableNode$flingWithNestedScroll$1
            if (r0 == 0) goto L17
            r0 = r13
            com.android.compose.gesture.NestedDraggableNode$flingWithNestedScroll$1 r0 = (com.android.compose.gesture.NestedDraggableNode$flingWithNestedScroll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L17
            int r1 = r1 - r2
            r0.label = r1
        L15:
            r6 = r0
            goto L1d
        L17:
            com.android.compose.gesture.NestedDraggableNode$flingWithNestedScroll$1 r0 = new com.android.compose.gesture.NestedDraggableNode$flingWithNestedScroll$1
            r0.<init>(r9, r13)
            goto L15
        L1d:
            java.lang.Object r13 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L5b
            if (r1 == r5) goto L4c
            if (r1 == r4) goto L40
            if (r1 != r3) goto L38
            long r9 = r6.J$1
            long r11 = r6.J$0
            kotlin.ResultKt.throwOnFailure(r13)
            goto Lab
        L38:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L40:
            long r9 = r6.J$1
            long r11 = r6.J$0
            java.lang.Object r1 = r6.L$0
            com.android.compose.gesture.NestedDraggableNode r1 = (com.android.compose.gesture.NestedDraggableNode) r1
            kotlin.ResultKt.throwOnFailure(r13)
            goto L8f
        L4c:
            long r10 = r6.J$0
            java.lang.Object r9 = r6.L$1
            r12 = r9
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            java.lang.Object r9 = r6.L$0
            com.android.compose.gesture.NestedDraggableNode r9 = (com.android.compose.gesture.NestedDraggableNode) r9
            kotlin.ResultKt.throwOnFailure(r13)
            goto L6f
        L5b:
            kotlin.ResultKt.throwOnFailure(r13)
            r6.L$0 = r9
            r6.L$1 = r12
            r6.J$0 = r10
            r6.label = r5
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r13 = r9.nestedScrollDispatcher
            java.lang.Object r13 = r13.m582dispatchPreFlingQWom1Mo(r10, r6)
            if (r13 != r0) goto L6f
            goto La9
        L6f:
            androidx.compose.ui.unit.Velocity r13 = (androidx.compose.ui.unit.Velocity) r13
            long r7 = r13.packedValue
            long r10 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r10, r7)
            androidx.compose.ui.unit.Velocity r13 = androidx.compose.ui.unit.Velocity.m876boximpl(r10)
            r6.L$0 = r9
            r6.L$1 = r2
            r6.J$0 = r7
            r6.J$1 = r10
            r6.label = r4
            java.lang.Object r13 = r12.invoke(r13, r6)
            if (r13 != r0) goto L8c
            goto La9
        L8c:
            r1 = r9
            r9 = r10
            r11 = r7
        L8f:
            androidx.compose.ui.unit.Velocity r13 = (androidx.compose.ui.unit.Velocity) r13
            long r4 = r13.packedValue
            long r9 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r9, r4)
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r1 = r1.nestedScrollDispatcher
            r6.L$0 = r2
            r6.J$0 = r11
            r6.J$1 = r4
            r6.label = r3
            r2 = r4
            r4 = r9
            java.lang.Object r13 = r1.m580dispatchPostFlingRZ2iAVY(r2, r4, r6)
            if (r13 != r0) goto Laa
        La9:
            return r0
        Laa:
            r9 = r2
        Lab:
            androidx.compose.ui.unit.Velocity r13 = (androidx.compose.ui.unit.Velocity) r13
            long r0 = r13.packedValue
            long r9 = androidx.compose.ui.unit.Velocity.m881plusAH228Gc(r11, r9)
            long r9 = androidx.compose.ui.unit.Velocity.m881plusAH228Gc(r9, r0)
            androidx.compose.ui.unit.Velocity r9 = androidx.compose.ui.unit.Velocity.m876boximpl(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.NestedDraggableNode.m927access$flingWithNestedScrollTK7Wm2c(com.android.compose.gesture.NestedDraggableNode, long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        if (r8.mo19applyToFlingBMRW4eQ(r5, r7, r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* renamed from: flingWithOverscroll-xgHb9do, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m929flingWithOverscrollxgHb9do(androidx.compose.foundation.OverscrollEffect r8, long r9, kotlin.jvm.functions.Function2 r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            r7 = this;
            boolean r0 = r12 instanceof com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$2
            if (r0 == 0) goto L13
            r0 = r12
            com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$2 r0 = (com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$2 r0 = new com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$2
            r0.<init>(r7, r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r12)
            return r12
        L2a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L32:
            long r9 = r0.J$0
            kotlin.ResultKt.throwOnFailure(r12)
            goto L56
        L38:
            kotlin.ResultKt.throwOnFailure(r12)
            float r12 = r7.m931toFloatTH1AsA0$1(r9)
            long r5 = r7.m934toVelocityadjELrA$1(r12)
            if (r8 == 0) goto L5b
            com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$3 r7 = new com.android.compose.gesture.NestedDraggableNode$flingWithOverscroll$3
            r12 = 0
            r7.<init>(r11, r12)
            r0.J$0 = r9
            r0.label = r4
            java.lang.Object r7 = r8.mo19applyToFlingBMRW4eQ(r5, r7, r0)
            if (r7 != r1) goto L56
            goto L67
        L56:
            androidx.compose.ui.unit.Velocity r7 = androidx.compose.ui.unit.Velocity.m876boximpl(r9)
            return r7
        L5b:
            androidx.compose.ui.unit.Velocity r7 = androidx.compose.ui.unit.Velocity.m876boximpl(r5)
            r0.label = r3
            java.lang.Object r7 = r11.invoke(r7, r0)
            if (r7 != r1) goto L68
        L67:
            return r1
        L68:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.NestedDraggableNode.m929flingWithOverscrollxgHb9do(androidx.compose.foundation.OverscrollEffect, long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
            this.nestedScrollController = new NestedScrollController(this.overscrollEffect, ((DraggableHandler) this.draggable).m918onDragStartedw4f02Oo(offset.packedValue, f, i, pointerType));
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
    public final long mo78onPostScrollDzOQY0M(int i, long j, long j2) {
        NestedScrollSource.Companion.getClass();
        if (i == NestedScrollSource.SideEffect) {
            if (this.nestedScrollController != null) {
                throw new IllegalStateException("Check failed.");
            }
            Offset.Companion.getClass();
            return 0L;
        }
        float m932toFloatk4lQ0M$1 = m932toFloatk4lQ0M$1(j2);
        if (m932toFloatk4lQ0M$1 == 0.0f) {
            Offset.Companion.getClass();
            return 0L;
        }
        final float signum = Math.signum(m932toFloatk4lQ0M$1);
        maybeCreateNewController(signum, new Function0(signum) { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DraggableHandler draggableHandler = (DraggableHandler) NestedDraggableNode.this.draggable;
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
            return m930scrollWithOverscroll8S9VItk(j2, new NestedDraggableNode$$ExternalSyntheticLambda3(this, nestedScrollController, 0));
        }
        this.ignoreNextDrag = false;
        return j2;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreFling-QWom1Mo */
    public final Object mo288onPreFlingQWom1Mo(long j, Continuation continuation) {
        NestedScrollController nestedScrollController = this.nestedScrollController;
        if (nestedScrollController == null) {
            Velocity.Companion.getClass();
            return Velocity.m876boximpl(0L);
        }
        this.nestedScrollController = null;
        Object awaitInternal = BuildersKt.async$default(this.nestedScrollDispatcher.getCoroutineScope(), null, new NestedDraggableNode$onPreFling$2(nestedScrollController, j, null), 3).awaitInternal(continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return awaitInternal;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreScroll-OzD1aCk */
    public final long mo175onPreScrollOzD1aCk(final int i, long j) {
        final float signum = Math.signum(m932toFloatk4lQ0M$1(j));
        maybeCreateNewController(signum, new Function0(i, this, signum) { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda2
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
            return m930scrollWithOverscroll8S9VItk(j, new NestedDraggableNode$$ExternalSyntheticLambda3(this, nestedScrollController, 0));
        }
        this.ignoreNextDrag = false;
        return j;
    }

    /* renamed from: scrollWithOverscroll-8S9VItk, reason: not valid java name */
    public final long m930scrollWithOverscroll8S9VItk(long j, final Function1 function1) {
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        if (overscrollEffect == null) {
            return ((Offset) function1.mo779invoke(Offset.m393boximpl(j))).packedValue;
        }
        NestedScrollSource.Companion.getClass();
        return overscrollEffect.mo20applyToScrollRhakbz0(NestedScrollSource.UserInput, j, new Function1() { // from class: com.android.compose.gesture.NestedDraggableNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return (Offset) Function1.this.mo779invoke((Offset) obj);
            }
        });
    }

    /* renamed from: toFloat-TH1AsA0$1, reason: not valid java name */
    public float m931toFloatTH1AsA0$1(long j) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            return Velocity.m878getXimpl(j);
        }
        if (i == 2) {
            return Velocity.m879getYimpl(j);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* renamed from: toFloat-k-4lQ0M$1, reason: not valid java name */
    public float m932toFloatk4lQ0M$1(long j) {
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
    public long m933toOffsettuRUvjQ$1(float f) {
        int i = OrientationAware$WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return floatToRawIntBits;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        long floatToRawIntBits2 = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
        Offset.Companion companion2 = Offset.Companion;
        return floatToRawIntBits2;
    }

    /* renamed from: toVelocity-adjELrA$1, reason: not valid java name */
    public long m934toVelocityadjELrA$1(float f) {
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
