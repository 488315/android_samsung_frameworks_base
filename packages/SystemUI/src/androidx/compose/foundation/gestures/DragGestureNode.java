package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.input.pointer.util.VelocityTrackerKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$LongRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DragGestureNode extends DelegatingNode implements PointerInputModifierNode {
    public Function1 canDrag;
    public BufferedChannel channel;
    public DragInteraction$Start dragInteraction;
    public boolean enabled;
    public MutableInteractionSource interactionSource;
    public boolean isListeningForEvents;
    public Orientation orientationLock;
    public SuspendingPointerInputModifierNode pointerInputNode;

    public DragGestureNode(Function1 function1, boolean z, MutableInteractionSource mutableInteractionSource, Orientation orientation) {
        this.orientationLock = orientation;
        this.canDrag = function1;
        this.enabled = z;
        this.interactionSource = mutableInteractionSource;
        new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureNode$_canDrag$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return (Boolean) DragGestureNode.this.canDrag.mo779invoke((PointerInputChange) obj);
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$processDragCancel(androidx.compose.foundation.gestures.DragGestureNode r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5.getClass()
            boolean r0 = r6 instanceof androidx.compose.foundation.gestures.DragGestureNode$processDragCancel$1
            if (r0 == 0) goto L16
            r0 = r6
            androidx.compose.foundation.gestures.DragGestureNode$processDragCancel$1 r0 = (androidx.compose.foundation.gestures.DragGestureNode$processDragCancel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.gestures.DragGestureNode$processDragCancel$1 r0 = new androidx.compose.foundation.gestures.DragGestureNode$processDragCancel$1
            r0.<init>(r5, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.gestures.DragGestureNode r5 = (androidx.compose.foundation.gestures.DragGestureNode) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L51
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.compose.foundation.interaction.DragInteraction$Start r6 = r5.dragInteraction
            if (r6 == 0) goto L54
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r5.interactionSource
            if (r2 == 0) goto L51
            androidx.compose.foundation.interaction.DragInteraction$Cancel r4 = new androidx.compose.foundation.interaction.DragInteraction$Cancel
            r4.<init>(r6)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r2.emit(r4, r0)
            if (r6 != r1) goto L51
            return r1
        L51:
            r6 = 0
            r5.dragInteraction = r6
        L54:
            androidx.compose.ui.unit.Velocity$Companion r6 = androidx.compose.ui.unit.Velocity.Companion
            r6.getClass()
            r0 = 0
            r5.mo63onDragStoppedTH1AsA0(r0)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureNode.access$processDragCancel(androidx.compose.foundation.gestures.DragGestureNode, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
    
        if (r2.emit(r5, r0) == r1) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$processDragStart(androidx.compose.foundation.gestures.DragGestureNode r6, androidx.compose.foundation.gestures.DragEvent.DragStarted r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof androidx.compose.foundation.gestures.DragGestureNode$processDragStart$1
            if (r0 == 0) goto L16
            r0 = r8
            androidx.compose.foundation.gestures.DragGestureNode$processDragStart$1 r0 = (androidx.compose.foundation.gestures.DragGestureNode$processDragStart$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.gestures.DragGestureNode$processDragStart$1 r0 = new androidx.compose.foundation.gestures.DragGestureNode$processDragStart$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L4e
            if (r2 == r4) goto L41
            if (r2 != r3) goto L39
            java.lang.Object r6 = r0.L$2
            androidx.compose.foundation.interaction.DragInteraction$Start r6 = (androidx.compose.foundation.interaction.DragInteraction$Start) r6
            java.lang.Object r7 = r0.L$1
            androidx.compose.foundation.gestures.DragEvent$DragStarted r7 = (androidx.compose.foundation.gestures.DragEvent.DragStarted) r7
            java.lang.Object r0 = r0.L$0
            androidx.compose.foundation.gestures.DragGestureNode r0 = (androidx.compose.foundation.gestures.DragGestureNode) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L85
        L39:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L41:
            java.lang.Object r6 = r0.L$1
            r7 = r6
            androidx.compose.foundation.gestures.DragEvent$DragStarted r7 = (androidx.compose.foundation.gestures.DragEvent.DragStarted) r7
            java.lang.Object r6 = r0.L$0
            androidx.compose.foundation.gestures.DragGestureNode r6 = (androidx.compose.foundation.gestures.DragGestureNode) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L4e:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.foundation.interaction.DragInteraction$Start r8 = r6.dragInteraction
            if (r8 == 0) goto L6b
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r6.interactionSource
            if (r2 == 0) goto L6b
            androidx.compose.foundation.interaction.DragInteraction$Cancel r5 = new androidx.compose.foundation.interaction.DragInteraction$Cancel
            r5.<init>(r8)
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r4
            java.lang.Object r8 = r2.emit(r5, r0)
            if (r8 != r1) goto L6b
            goto L82
        L6b:
            androidx.compose.foundation.interaction.DragInteraction$Start r8 = new androidx.compose.foundation.interaction.DragInteraction$Start
            r8.<init>()
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r6.interactionSource
            if (r2 == 0) goto L87
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r8
            r0.label = r3
            java.lang.Object r0 = r2.emit(r8, r0)
            if (r0 != r1) goto L83
        L82:
            return r1
        L83:
            r0 = r6
            r6 = r8
        L85:
            r8 = r6
            r6 = r0
        L87:
            r6.dragInteraction = r8
            long r7 = r7.startPoint
            r6.mo62onDragStartedk4lQ0M(r7)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureNode.access$processDragStart(androidx.compose.foundation.gestures.DragGestureNode, androidx.compose.foundation.gestures.DragEvent$DragStarted, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$processDragStop(androidx.compose.foundation.gestures.DragGestureNode r5, androidx.compose.foundation.gestures.DragEvent.DragStopped r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof androidx.compose.foundation.gestures.DragGestureNode$processDragStop$1
            if (r0 == 0) goto L16
            r0 = r7
            androidx.compose.foundation.gestures.DragGestureNode$processDragStop$1 r0 = (androidx.compose.foundation.gestures.DragGestureNode$processDragStop$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.gestures.DragGestureNode$processDragStop$1 r0 = new androidx.compose.foundation.gestures.DragGestureNode$processDragStop$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r5 = r0.L$1
            r6 = r5
            androidx.compose.foundation.gestures.DragEvent$DragStopped r6 = (androidx.compose.foundation.gestures.DragEvent.DragStopped) r6
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.gestures.DragGestureNode r5 = (androidx.compose.foundation.gestures.DragGestureNode) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L58
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.foundation.interaction.DragInteraction$Start r7 = r5.dragInteraction
            if (r7 == 0) goto L5b
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r5.interactionSource
            if (r2 == 0) goto L58
            androidx.compose.foundation.interaction.DragInteraction$Stop r4 = new androidx.compose.foundation.interaction.DragInteraction$Stop
            r4.<init>(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = r2.emit(r4, r0)
            if (r7 != r1) goto L58
            return r1
        L58:
            r7 = 0
            r5.dragInteraction = r7
        L5b:
            long r6 = r6.velocity
            r5.mo63onDragStoppedTH1AsA0(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureNode.access$processDragStop(androidx.compose.foundation.gestures.DragGestureNode, androidx.compose.foundation.gestures.DragEvent$DragStopped, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void disposeInteractionSource$1() {
        DragInteraction$Start dragInteraction$Start = this.dragInteraction;
        if (dragInteraction$Start != null) {
            MutableInteractionSource mutableInteractionSource = this.interactionSource;
            if (mutableInteractionSource != null) {
                mutableInteractionSource.tryEmit(new DragInteraction$Cancel(dragInteraction$Start));
            }
            this.dragInteraction = null;
        }
    }

    public abstract Object drag(Function2 function2, Continuation continuation);

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode = this.pointerInputNode;
        if (suspendingPointerInputModifierNode != null) {
            ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).onCancelPointerInput();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.isListeningForEvents = false;
        disposeInteractionSource$1();
    }

    /* renamed from: onDragStarted-k-4lQ0M */
    public abstract void mo62onDragStartedk4lQ0M(long j);

    /* renamed from: onDragStopped-TH1AsA0 */
    public abstract void mo63onDragStoppedTH1AsA0(long j);

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        if (this.enabled && this.pointerInputNode == null) {
            SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Function2 $onDrag;
                    final /* synthetic */ Function0 $onDragCancel;
                    final /* synthetic */ Function1 $onDragEnd;
                    final /* synthetic */ Function3 $onDragStart;
                    final /* synthetic */ Function0 $shouldAwaitTouchSlop;
                    final /* synthetic */ PointerInputScope $this_SuspendingPointerInputModifierNode;
                    private /* synthetic */ Object L$0;
                    int label;
                    final /* synthetic */ DragGestureNode this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(DragGestureNode dragGestureNode, PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = dragGestureNode;
                        this.$this_SuspendingPointerInputModifierNode = pointerInputScope;
                        this.$onDragStart = function3;
                        this.$onDragEnd = function1;
                        this.$onDragCancel = function0;
                        this.$shouldAwaitTouchSlop = function02;
                        this.$onDrag = function2;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$this_SuspendingPointerInputModifierNode, this.$onDragStart, this.$onDragEnd, this.$onDragCancel, this.$shouldAwaitTouchSlop, this.$onDrag, continuation);
                        anonymousClass1.L$0 = obj;
                        return anonymousClass1;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.CoroutineScope] */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        ?? r1 = this.label;
                        try {
                            if (r1 == 0) {
                                ResultKt.throwOnFailure(obj);
                                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                Orientation orientation = this.this$0.orientationLock;
                                PointerInputScope pointerInputScope = this.$this_SuspendingPointerInputModifierNode;
                                Function3 function3 = this.$onDragStart;
                                Function1 function1 = this.$onDragEnd;
                                Function0 function0 = this.$onDragCancel;
                                Function0 function02 = this.$shouldAwaitTouchSlop;
                                Function2 function2 = this.$onDrag;
                                this.L$0 = coroutineScope;
                                this.label = 1;
                                float f = DragGestureDetectorKt.mouseToTouchSlopRatio;
                                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new DragGestureDetectorKt$detectDragGestures$9(function02, new Ref$LongRef(), orientation, function3, function2, function0, function1, null), this);
                                if (awaitEachGesture != obj2) {
                                    awaitEachGesture = Unit.INSTANCE;
                                }
                                if (awaitEachGesture == obj2) {
                                    return obj2;
                                }
                            } else {
                                if (r1 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                        } catch (CancellationException e) {
                            BufferedChannel bufferedChannel = this.this$0.channel;
                            if (bufferedChannel != null) {
                                ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(DragEvent.DragCancelled.INSTANCE));
                            }
                            if (!CoroutineScopeKt.isActive(r1)) {
                                throw e;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                public final Object invoke(final PointerInputScope pointerInputScope, Continuation continuation) {
                    final VelocityTracker velocityTracker = new VelocityTracker();
                    final DragGestureNode dragGestureNode = DragGestureNode.this;
                    Object coroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(DragGestureNode.this, pointerInputScope, new Function3() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDragStart$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            PointerInputChange pointerInputChange = (PointerInputChange) obj;
                            PointerInputChange pointerInputChange2 = (PointerInputChange) obj2;
                            long j2 = ((Offset) obj3).packedValue;
                            if (((Boolean) DragGestureNode.this.canDrag.mo779invoke(pointerInputChange)).booleanValue()) {
                                DragGestureNode dragGestureNode2 = DragGestureNode.this;
                                if (!dragGestureNode2.isListeningForEvents) {
                                    if (dragGestureNode2.channel == null) {
                                        dragGestureNode2.channel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
                                    }
                                    DragGestureNode dragGestureNode3 = DragGestureNode.this;
                                    dragGestureNode3.isListeningForEvents = true;
                                    BuildersKt.launch$default(dragGestureNode3.getCoroutineScope(), null, null, new DragGestureNode$startListeningForEvents$1(dragGestureNode3, null), 3);
                                }
                                VelocityTrackerKt.addPointerInputChange(velocityTracker, pointerInputChange);
                                long m400minusMKHz9U = Offset.m400minusMKHz9U(pointerInputChange2.position, j2);
                                BufferedChannel bufferedChannel = DragGestureNode.this.channel;
                                if (bufferedChannel != null) {
                                    ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(new DragEvent.DragStarted(m400minusMKHz9U, null)));
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDragEnd$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            VelocityTrackerKt.addPointerInputChange(VelocityTracker.this, (PointerInputChange) obj);
                            float maximumFlingVelocity = pointerInputScope.getViewConfiguration().getMaximumFlingVelocity();
                            long m600calculateVelocityAH228Gc = VelocityTracker.this.m600calculateVelocityAH228Gc(VelocityKt.Velocity(maximumFlingVelocity, maximumFlingVelocity));
                            VelocityTracker.this.resetTracking();
                            BufferedChannel bufferedChannel = dragGestureNode.channel;
                            if (bufferedChannel != null) {
                                Function3 function3 = DraggableKt.NoOpOnDragStarted;
                                ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(new DragEvent.DragStopped(VelocityKt.Velocity(Float.isNaN(Velocity.m878getXimpl(m600calculateVelocityAH228Gc)) ? 0.0f : Velocity.m878getXimpl(m600calculateVelocityAH228Gc), Float.isNaN(Velocity.m879getYimpl(m600calculateVelocityAH228Gc)) ? 0.0f : Velocity.m879getYimpl(m600calculateVelocityAH228Gc)), null)));
                            }
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDragCancel$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BufferedChannel bufferedChannel = DragGestureNode.this.channel;
                            if (bufferedChannel != null) {
                                ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(DragEvent.DragCancelled.INSTANCE));
                            }
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$shouldAwaitTouchSlop$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return Boolean.valueOf(!DragGestureNode.this.startDragImmediately());
                        }
                    }, new Function2() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDrag$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            long j2 = ((Offset) obj2).packedValue;
                            VelocityTrackerKt.addPointerInputChange(VelocityTracker.this, (PointerInputChange) obj);
                            BufferedChannel bufferedChannel = dragGestureNode.channel;
                            if (bufferedChannel != null) {
                                ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(new DragEvent.DragDelta(j2, null)));
                            }
                            return Unit.INSTANCE;
                        }
                    }, null), continuation);
                    return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
                }
            });
            delegate(SuspendingPointerInputModifierNode);
            this.pointerInputNode = SuspendingPointerInputModifierNode;
        }
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode = this.pointerInputNode;
        if (suspendingPointerInputModifierNode != null) {
            ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
        }
    }

    public abstract boolean startDragImmediately();

    public final void update(Function1 function1, boolean z, MutableInteractionSource mutableInteractionSource, Orientation orientation, boolean z2) {
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode;
        this.canDrag = function1;
        boolean z3 = true;
        if (this.enabled != z) {
            this.enabled = z;
            if (!z) {
                disposeInteractionSource$1();
                SuspendingPointerInputModifierNode suspendingPointerInputModifierNode2 = this.pointerInputNode;
                if (suspendingPointerInputModifierNode2 != null) {
                    undelegate(suspendingPointerInputModifierNode2);
                }
                this.pointerInputNode = null;
            }
            z2 = true;
        }
        if (!Intrinsics.areEqual(this.interactionSource, mutableInteractionSource)) {
            disposeInteractionSource$1();
            this.interactionSource = mutableInteractionSource;
        }
        if (this.orientationLock != orientation) {
            this.orientationLock = orientation;
        } else {
            z3 = z2;
        }
        if (!z3 || (suspendingPointerInputModifierNode = this.pointerInputNode) == null) {
            return;
        }
        ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).resetPointerInputHandler();
    }
}
