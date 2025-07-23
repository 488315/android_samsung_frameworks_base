package com.android.compose.gesture;

import androidx.compose.ui.unit.Velocity;
import com.android.compose.gesture.NestedDraggable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableNode$onDragStopped$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NestedDraggable.Controller $controller;
    final /* synthetic */ long $velocity;
    Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NestedDraggable.Controller $controller;
        final /* synthetic */ CompletableDeferred $flingCompletable;
        /* synthetic */ long J$0;
        int label;
        final /* synthetic */ NestedDraggableNode this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00191 extends SuspendLambda implements Function2 {
            final /* synthetic */ NestedDraggable.Controller $controller;
            final /* synthetic */ CompletableDeferred $flingCompletable;
            /* synthetic */ long J$0;
            Object L$0;
            int label;
            final /* synthetic */ NestedDraggableNode this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00201 extends SuspendLambda implements Function1 {
                final /* synthetic */ CompletableDeferred $flingCompletable;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00201(CompletableDeferred completableDeferred, Continuation continuation) {
                    super(1, continuation);
                    this.$flingCompletable = completableDeferred;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Continuation continuation) {
                    return new C00201(this.$flingCompletable, continuation);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    return ((C00201) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CompletableDeferred completableDeferred = this.$flingCompletable;
                        this.label = 1;
                        if (((CompletableDeferredImpl) completableDeferred).awaitInternal(this) == coroutineSingletons) {
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00191(NestedDraggableNode nestedDraggableNode, NestedDraggable.Controller controller, CompletableDeferred completableDeferred, Continuation continuation) {
                super(2, continuation);
                this.this$0 = nestedDraggableNode;
                this.$controller = controller;
                this.$flingCompletable = completableDeferred;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00191 c00191 = new C00191(this.this$0, this.$controller, this.$flingCompletable, continuation);
                c00191.J$0 = ((Velocity) obj).packedValue;
                return c00191;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00191) create(Velocity.m876boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                NestedDraggableNode nestedDraggableNode;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    long j = this.J$0;
                    NestedDraggableNode nestedDraggableNode2 = this.this$0;
                    NestedDraggable.Controller controller = this.$controller;
                    float m931toFloatTH1AsA0$1 = nestedDraggableNode2.m931toFloatTH1AsA0$1(j);
                    C00201 c00201 = new C00201(this.$flingCompletable, null);
                    this.L$0 = nestedDraggableNode2;
                    this.label = 1;
                    Object onDragStopped = controller.onDragStopped(m931toFloatTH1AsA0$1, c00201, this);
                    if (onDragStopped == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = onDragStopped;
                    nestedDraggableNode = nestedDraggableNode2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    nestedDraggableNode = (NestedDraggableNode) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                return Velocity.m876boximpl(nestedDraggableNode.m934toVelocityadjELrA$1(((Number) obj).floatValue()));
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NestedDraggableNode nestedDraggableNode, NestedDraggable.Controller controller, CompletableDeferred completableDeferred, Continuation continuation) {
            super(2, continuation);
            this.this$0 = nestedDraggableNode;
            this.$controller = controller;
            this.$flingCompletable = completableDeferred;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$controller, this.$flingCompletable, continuation);
            anonymousClass1.J$0 = ((Velocity) obj).packedValue;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Velocity.m876boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            long j = this.J$0;
            NestedDraggableNode nestedDraggableNode = this.this$0;
            C00191 c00191 = new C00191(nestedDraggableNode, this.$controller, this.$flingCompletable, null);
            this.label = 1;
            Object m927access$flingWithNestedScrollTK7Wm2c = NestedDraggableNode.m927access$flingWithNestedScrollTK7Wm2c(nestedDraggableNode, j, c00191, this);
            return m927access$flingWithNestedScrollTK7Wm2c == coroutineSingletons ? coroutineSingletons : m927access$flingWithNestedScrollTK7Wm2c;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$onDragStopped$1(NestedDraggableNode nestedDraggableNode, long j, NestedDraggable.Controller controller, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nestedDraggableNode;
        this.$velocity = j;
        this.$controller = controller;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NestedDraggableNode$onDragStopped$1(this.this$0, this.$velocity, this.$controller, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$onDragStopped$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable th;
        CompletableDeferred completableDeferred;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CompletableDeferredImpl CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            try {
                NestedDraggableNode nestedDraggableNode = this.this$0;
                long j = this.$velocity;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(nestedDraggableNode, this.$controller, CompletableDeferred$default, null);
                this.L$0 = CompletableDeferred$default;
                this.label = 1;
                Object m929flingWithOverscrollxgHb9do = nestedDraggableNode.m929flingWithOverscrollxgHb9do(nestedDraggableNode.overscrollEffect, j, anonymousClass1, this);
                if (m929flingWithOverscrollxgHb9do == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = m929flingWithOverscrollxgHb9do;
                completableDeferred = CompletableDeferred$default;
            } catch (Throwable th2) {
                th = th2;
                completableDeferred = CompletableDeferred$default;
                ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            completableDeferred = (CompletableDeferred) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                throw th;
            }
        }
        ((Velocity) obj).getClass();
        Unit unit = Unit.INSTANCE;
        ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
        return unit;
    }
}
