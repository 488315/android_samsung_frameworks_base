package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

final class CustomTileServiceInteractor$createDestructionJob$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileServiceInteractor this$0;

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor$createDestructionJob$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CustomTileServiceInteractor this$0;

        public AnonymousClass1(CustomTileServiceInteractor customTileServiceInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileServiceInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                final CustomTileServiceInteractor customTileServiceInteractor = this.this$0;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor.createDestructionJob.1.1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((CustomTileUserActionInteractor) CustomTileServiceInteractor.this.userActionInteractor.get()).revokeToken(true);
                        CustomTileServiceInteractor customTileServiceInteractor2 = CustomTileServiceInteractor.this;
                        customTileServiceInteractor2.tileServices.freeService(customTileServiceInteractor2.tileReceivingInterface, customTileServiceInteractor2.getTileServiceManager());
                        CustomTileServiceInteractor.this.destructionJob = null;
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

    public CustomTileServiceInteractor$createDestructionJob$1(CustomTileServiceInteractor customTileServiceInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileServiceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTileServiceInteractor$createDestructionJob$1 customTileServiceInteractor$createDestructionJob$1 = new CustomTileServiceInteractor$createDestructionJob$1(this.this$0, continuation);
        customTileServiceInteractor$createDestructionJob$1.L$0 = obj;
        return customTileServiceInteractor$createDestructionJob$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileServiceInteractor$createDestructionJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        BufferOverflow bufferOverflow = BufferOverflow.SUSPEND;
        CoroutineStart coroutineStart = CoroutineStart.DEFAULT;
        AbstractCoroutine producerCoroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, emptyCoroutineContext), ChannelKt.Channel$default(0, bufferOverflow, null, 4));
        producerCoroutine.start(coroutineStart, producerCoroutine, anonymousClass1);
        return Unit.INSTANCE;
    }
}
