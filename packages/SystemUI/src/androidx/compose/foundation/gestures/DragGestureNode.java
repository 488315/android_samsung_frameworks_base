package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.Interaction;
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
import kotlin.coroutines.jvm.internal.ContinuationImpl;
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
            public final Object mo781invoke(Object obj) {
                return (Boolean) this.this$0.canDrag.mo781invoke((PointerInputChange) obj);
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$processDragCancel(DragGestureNode dragGestureNode, ContinuationImpl continuationImpl) {
        DragGestureNode$processDragCancel$1 dragGestureNode$processDragCancel$1;
        dragGestureNode.getClass();
        if (continuationImpl instanceof DragGestureNode$processDragCancel$1) {
            dragGestureNode$processDragCancel$1 = (DragGestureNode$processDragCancel$1) continuationImpl;
            int i = dragGestureNode$processDragCancel$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureNode$processDragCancel$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureNode$processDragCancel$1 = new DragGestureNode$processDragCancel$1(dragGestureNode, continuationImpl);
            }
        }
        Object obj = dragGestureNode$processDragCancel$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureNode$processDragCancel$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            DragInteraction$Start dragInteraction$Start = dragGestureNode.dragInteraction;
            if (dragInteraction$Start != null) {
                MutableInteractionSource mutableInteractionSource = dragGestureNode.interactionSource;
                if (mutableInteractionSource != null) {
                    DragInteraction$Cancel dragInteraction$Cancel = new DragInteraction$Cancel(dragInteraction$Start);
                    dragGestureNode$processDragCancel$1.L$0 = dragGestureNode;
                    dragGestureNode$processDragCancel$1.label = 1;
                    if (mutableInteractionSource.emit(dragInteraction$Cancel, dragGestureNode$processDragCancel$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            Velocity.Companion.getClass();
            dragGestureNode.mo64onDragStoppedTH1AsA0(0L);
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        dragGestureNode = (DragGestureNode) dragGestureNode$processDragCancel$1.L$0;
        ResultKt.throwOnFailure(obj);
        dragGestureNode.dragInteraction = null;
        Velocity.Companion.getClass();
        dragGestureNode.mo64onDragStoppedTH1AsA0(0L);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$processDragStart(DragGestureNode dragGestureNode, DragEvent.DragStarted dragStarted, ContinuationImpl continuationImpl) {
        DragGestureNode$processDragStart$1 dragGestureNode$processDragStart$1;
        MutableInteractionSource mutableInteractionSource;
        DragGestureNode dragGestureNode2;
        Interaction interaction;
        DragInteraction$Start dragInteraction$Start;
        dragGestureNode.getClass();
        if (continuationImpl instanceof DragGestureNode$processDragStart$1) {
            dragGestureNode$processDragStart$1 = (DragGestureNode$processDragStart$1) continuationImpl;
            int i = dragGestureNode$processDragStart$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureNode$processDragStart$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureNode$processDragStart$1 = new DragGestureNode$processDragStart$1(dragGestureNode, continuationImpl);
            }
        }
        Object obj = dragGestureNode$processDragStart$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureNode$processDragStart$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            DragInteraction$Start dragInteraction$Start2 = dragGestureNode.dragInteraction;
            if (dragInteraction$Start2 != null && (mutableInteractionSource = dragGestureNode.interactionSource) != null) {
                DragInteraction$Cancel dragInteraction$Cancel = new DragInteraction$Cancel(dragInteraction$Start2);
                dragGestureNode$processDragStart$1.L$0 = dragGestureNode;
                dragGestureNode$processDragStart$1.L$1 = dragStarted;
                dragGestureNode$processDragStart$1.label = 1;
                if (mutableInteractionSource.emit(dragInteraction$Cancel, dragGestureNode$processDragStart$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            dragGestureNode.dragInteraction = dragInteraction$Start;
            dragGestureNode.mo63onDragStartedk4lQ0M(dragStarted.startPoint);
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            interaction = (DragInteraction$Start) dragGestureNode$processDragStart$1.L$2;
            dragStarted = (DragEvent.DragStarted) dragGestureNode$processDragStart$1.L$1;
            dragGestureNode2 = (DragGestureNode) dragGestureNode$processDragStart$1.L$0;
            ResultKt.throwOnFailure(obj);
            dragInteraction$Start = interaction;
            dragGestureNode = dragGestureNode2;
            dragGestureNode.dragInteraction = dragInteraction$Start;
            dragGestureNode.mo63onDragStartedk4lQ0M(dragStarted.startPoint);
            return Unit.INSTANCE;
        }
        dragStarted = (DragEvent.DragStarted) dragGestureNode$processDragStart$1.L$1;
        dragGestureNode = (DragGestureNode) dragGestureNode$processDragStart$1.L$0;
        ResultKt.throwOnFailure(obj);
        Interaction interaction2 = new Interaction() { // from class: androidx.compose.foundation.interaction.DragInteraction$Start
        };
        MutableInteractionSource mutableInteractionSource2 = dragGestureNode.interactionSource;
        dragInteraction$Start = interaction2;
        if (mutableInteractionSource2 != null) {
            dragGestureNode$processDragStart$1.L$0 = dragGestureNode;
            dragGestureNode$processDragStart$1.L$1 = dragStarted;
            dragGestureNode$processDragStart$1.L$2 = interaction2;
            dragGestureNode$processDragStart$1.label = 2;
            if (mutableInteractionSource2.emit(interaction2, dragGestureNode$processDragStart$1) != coroutineSingletons) {
                dragGestureNode2 = dragGestureNode;
                interaction = interaction2;
                dragInteraction$Start = interaction;
                dragGestureNode = dragGestureNode2;
            }
            return coroutineSingletons;
        }
        dragGestureNode.dragInteraction = dragInteraction$Start;
        dragGestureNode.mo63onDragStartedk4lQ0M(dragStarted.startPoint);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$processDragStop(DragGestureNode dragGestureNode, DragEvent.DragStopped dragStopped, ContinuationImpl continuationImpl) {
        DragGestureNode$processDragStop$1 dragGestureNode$processDragStop$1;
        dragGestureNode.getClass();
        if (continuationImpl instanceof DragGestureNode$processDragStop$1) {
            dragGestureNode$processDragStop$1 = (DragGestureNode$processDragStop$1) continuationImpl;
            int i = dragGestureNode$processDragStop$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureNode$processDragStop$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureNode$processDragStop$1 = new DragGestureNode$processDragStop$1(dragGestureNode, continuationImpl);
            }
        }
        Object obj = dragGestureNode$processDragStop$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureNode$processDragStop$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            DragInteraction$Start dragInteraction$Start = dragGestureNode.dragInteraction;
            if (dragInteraction$Start != null) {
                MutableInteractionSource mutableInteractionSource = dragGestureNode.interactionSource;
                if (mutableInteractionSource != null) {
                    DragInteraction$Stop dragInteraction$Stop = new DragInteraction$Stop(dragInteraction$Start);
                    dragGestureNode$processDragStop$1.L$0 = dragGestureNode;
                    dragGestureNode$processDragStop$1.L$1 = dragStopped;
                    dragGestureNode$processDragStop$1.label = 1;
                    if (mutableInteractionSource.emit(dragInteraction$Stop, dragGestureNode$processDragStop$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            dragGestureNode.mo64onDragStoppedTH1AsA0(dragStopped.velocity);
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        dragStopped = (DragEvent.DragStopped) dragGestureNode$processDragStop$1.L$1;
        dragGestureNode = (DragGestureNode) dragGestureNode$processDragStop$1.L$0;
        ResultKt.throwOnFailure(obj);
        dragGestureNode.dragInteraction = null;
        dragGestureNode.mo64onDragStoppedTH1AsA0(dragStopped.velocity);
        return Unit.INSTANCE;
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
    public abstract void mo63onDragStartedk4lQ0M(long j);

    /* renamed from: onDragStopped-TH1AsA0 */
    public abstract void mo64onDragStoppedTH1AsA0(long j);

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        if (this.enabled && this.pointerInputNode == null) {
            SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1

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
                                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new DragGestureDetectorKt.AnonymousClass9(function02, new Ref$LongRef(), orientation, function3, function2, function0, function1, null), this);
                                if (objAwaitEachGesture != obj2) {
                                    objAwaitEachGesture = Unit.INSTANCE;
                                }
                                if (objAwaitEachGesture == obj2) {
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
                                ChannelResult.m3476boximpl(bufferedChannel.mo3475trySendJP2dKIU(DragEvent.DragCancelled.INSTANCE));
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
                    final DragGestureNode dragGestureNode = this.this$0;
                    Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(this.this$0, pointerInputScope, new Function3() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDragStart$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            PointerInputChange pointerInputChange = (PointerInputChange) obj;
                            PointerInputChange pointerInputChange2 = (PointerInputChange) obj2;
                            long j2 = ((Offset) obj3).packedValue;
                            if (((Boolean) dragGestureNode.canDrag.mo781invoke(pointerInputChange)).booleanValue()) {
                                DragGestureNode dragGestureNode2 = dragGestureNode;
                                if (!dragGestureNode2.isListeningForEvents) {
                                    if (dragGestureNode2.channel == null) {
                                        dragGestureNode2.channel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
                                    }
                                    DragGestureNode dragGestureNode3 = dragGestureNode;
                                    dragGestureNode3.isListeningForEvents = true;
                                    BuildersKt.launch$default(dragGestureNode3.getCoroutineScope(), null, null, new DragGestureNode$startListeningForEvents$1(dragGestureNode3, null), 3);
                                }
                                VelocityTrackerKt.addPointerInputChange(velocityTracker, pointerInputChange);
                                long jM402minusMKHz9U = Offset.m402minusMKHz9U(pointerInputChange2.position, j2);
                                BufferedChannel bufferedChannel = dragGestureNode.channel;
                                if (bufferedChannel != null) {
                                    ChannelResult.m3476boximpl(bufferedChannel.mo3475trySendJP2dKIU(new DragEvent.DragStarted(jM402minusMKHz9U, null)));
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
                        public final Object mo781invoke(Object obj) {
                            VelocityTrackerKt.addPointerInputChange(velocityTracker, (PointerInputChange) obj);
                            float maximumFlingVelocity = pointerInputScope.getViewConfiguration().getMaximumFlingVelocity();
                            long jM602calculateVelocityAH228Gc = velocityTracker.m602calculateVelocityAH228Gc(VelocityKt.Velocity(maximumFlingVelocity, maximumFlingVelocity));
                            velocityTracker.resetTracking();
                            BufferedChannel bufferedChannel = dragGestureNode.channel;
                            if (bufferedChannel != null) {
                                Function3 function3 = DraggableKt.NoOpOnDragStarted;
                                ChannelResult.m3476boximpl(bufferedChannel.mo3475trySendJP2dKIU(new DragEvent.DragStopped(VelocityKt.Velocity(Float.isNaN(Velocity.m880getXimpl(jM602calculateVelocityAH228Gc)) ? 0.0f : Velocity.m880getXimpl(jM602calculateVelocityAH228Gc), Float.isNaN(Velocity.m881getYimpl(jM602calculateVelocityAH228Gc)) ? 0.0f : Velocity.m881getYimpl(jM602calculateVelocityAH228Gc)), null)));
                            }
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDragCancel$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BufferedChannel bufferedChannel = dragGestureNode.channel;
                            if (bufferedChannel != null) {
                                ChannelResult.m3476boximpl(bufferedChannel.mo3475trySendJP2dKIU(DragEvent.DragCancelled.INSTANCE));
                            }
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$shouldAwaitTouchSlop$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return Boolean.valueOf(!dragGestureNode.startDragImmediately());
                        }
                    }, new Function2() { // from class: androidx.compose.foundation.gestures.DragGestureNode$initializePointerInputNode$1$onDrag$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            long j2 = ((Offset) obj2).packedValue;
                            VelocityTrackerKt.addPointerInputChange(velocityTracker, (PointerInputChange) obj);
                            BufferedChannel bufferedChannel = dragGestureNode.channel;
                            if (bufferedChannel != null) {
                                ChannelResult.m3476boximpl(bufferedChannel.mo3475trySendJP2dKIU(new DragEvent.DragDelta(j2, null)));
                            }
                            return Unit.INSTANCE;
                        }
                    }, null), continuation);
                    return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
                }
            });
            delegate(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode);
            this.pointerInputNode = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode;
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
