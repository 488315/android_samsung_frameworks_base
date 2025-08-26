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

/* loaded from: classes.dex */
final class NestedDraggableNode$onDragStopped$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NestedDraggable.Controller $controller;
    final /* synthetic */ long $velocity;
    Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NestedDraggable.Controller $controller;
        final /* synthetic */ CompletableDeferred $flingCompletable;
        /* synthetic */ long J$0;
        int label;
        final /* synthetic */ NestedDraggableNode this$0;

        /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00451 extends SuspendLambda implements Function2 {
            final /* synthetic */ NestedDraggable.Controller $controller;
            final /* synthetic */ CompletableDeferred $flingCompletable;
            /* synthetic */ long J$0;
            Object L$0;
            int label;
            final /* synthetic */ NestedDraggableNode this$0;

            /* renamed from: com.android.compose.gesture.NestedDraggableNode$onDragStopped$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00461 extends SuspendLambda implements Function1 {
                final /* synthetic */ CompletableDeferred $flingCompletable;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00461(CompletableDeferred completableDeferred, Continuation continuation) {
                    super(1, continuation);
                    this.$flingCompletable = completableDeferred;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Continuation continuation) {
                    return new C00461(this.$flingCompletable, continuation);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return ((C00461) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
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
            public C00451(NestedDraggableNode nestedDraggableNode, NestedDraggable.Controller controller, CompletableDeferred completableDeferred, Continuation continuation) {
                super(2, continuation);
                this.this$0 = nestedDraggableNode;
                this.$controller = controller;
                this.$flingCompletable = completableDeferred;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00451 c00451 = new C00451(this.this$0, this.$controller, this.$flingCompletable, continuation);
                c00451.J$0 = ((Velocity) obj).packedValue;
                return c00451;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00451) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    float fM933toFloatTH1AsA0$1 = nestedDraggableNode2.m933toFloatTH1AsA0$1(j);
                    C00461 c00461 = new C00461(this.$flingCompletable, null);
                    this.L$0 = nestedDraggableNode2;
                    this.label = 1;
                    Object objOnDragStopped = controller.onDragStopped(fM933toFloatTH1AsA0$1, c00461, this);
                    if (objOnDragStopped == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = objOnDragStopped;
                    nestedDraggableNode = nestedDraggableNode2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    nestedDraggableNode = (NestedDraggableNode) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                return Velocity.m878boximpl(nestedDraggableNode.m936toVelocityadjELrA$1(((Number) obj).floatValue()));
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
            return ((AnonymousClass1) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            C00451 c00451 = new C00451(nestedDraggableNode, this.$controller, this.$flingCompletable, null);
            this.label = 1;
            Object objM929access$flingWithNestedScrollTK7Wm2c = NestedDraggableNode.m929access$flingWithNestedScrollTK7Wm2c(nestedDraggableNode, j, c00451, this);
            return objM929access$flingWithNestedScrollTK7Wm2c == coroutineSingletons ? coroutineSingletons : objM929access$flingWithNestedScrollTK7Wm2c;
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
    public final Object invokeSuspend(Object obj) throws Throwable {
        Throwable th;
        CompletableDeferred completableDeferred;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            try {
                NestedDraggableNode nestedDraggableNode = this.this$0;
                long j = this.$velocity;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(nestedDraggableNode, this.$controller, completableDeferredImplCompletableDeferred$default, null);
                this.L$0 = completableDeferredImplCompletableDeferred$default;
                this.label = 1;
                Object objM931flingWithOverscrollxgHb9do = nestedDraggableNode.m931flingWithOverscrollxgHb9do(nestedDraggableNode.overscrollEffect, j, anonymousClass1, this);
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
