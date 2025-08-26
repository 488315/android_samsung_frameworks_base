package com.android.systemui.security.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes2.dex */
final class SecurityRepositoryImpl$security$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ SecurityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityRepositoryImpl$security$1(SecurityRepositoryImpl securityRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = securityRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r6v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope producerScope, SecurityRepositoryImpl securityRepositoryImpl, ContinuationImpl continuationImpl) {
        SecurityRepositoryImpl$security$1$updateState$1 securityRepositoryImpl$security$1$updateState$1;
        ChannelExt channelExt;
        ProducerScope producerScope2;
        if (continuationImpl instanceof SecurityRepositoryImpl$security$1$updateState$1) {
            securityRepositoryImpl$security$1$updateState$1 = (SecurityRepositoryImpl$security$1$updateState$1) continuationImpl;
            int i = securityRepositoryImpl$security$1$updateState$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                securityRepositoryImpl$security$1$updateState$1.label = i - Integer.MIN_VALUE;
            } else {
                securityRepositoryImpl$security$1$updateState$1 = new SecurityRepositoryImpl$security$1$updateState$1(continuationImpl);
            }
        }
        Object obj = securityRepositoryImpl$security$1$updateState$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = securityRepositoryImpl$security$1$updateState$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelExt channelExt2 = ChannelExt.INSTANCE;
            SecurityModel.Companion companion = SecurityModel.Companion;
            SecurityController securityController = securityRepositoryImpl.securityController;
            securityRepositoryImpl$security$1$updateState$1.L$0 = channelExt2;
            securityRepositoryImpl$security$1$updateState$1.L$1 = producerScope;
            securityRepositoryImpl$security$1$updateState$1.label = 1;
            companion.getClass();
            Object objCreate = SecurityModel.Companion.create(securityController, securityRepositoryImpl.bgDispatcher, securityRepositoryImpl$security$1$updateState$1);
            if (objCreate == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objCreate;
            channelExt = channelExt2;
            producerScope2 = producerScope;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r6 = (SendChannel) securityRepositoryImpl$security$1$updateState$1.L$1;
            channelExt = (ChannelExt) securityRepositoryImpl$security$1$updateState$1.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope2 = r6;
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, obj, "SecurityRepositoryImpl");
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecurityRepositoryImpl$security$1 securityRepositoryImpl$security$1 = new SecurityRepositoryImpl$security$1(this.this$0, continuation);
        securityRepositoryImpl$security$1.L$0 = obj;
        return securityRepositoryImpl$security$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecurityRepositoryImpl$security$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r5) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        final SecurityController.SecurityControllerCallback securityControllerCallback;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope2 = (ProducerScope) this.L$0;
            final SecurityRepositoryImpl securityRepositoryImpl = this.this$0;
            securityControllerCallback = new SecurityController.SecurityControllerCallback() { // from class: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1

                /* renamed from: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                    int label;
                    final /* synthetic */ SecurityRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ProducerScope producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.this$0 = securityRepositoryImpl;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ProducerScope producerScope = this.$$this$conflatedCallbackFlow;
                            SecurityRepositoryImpl securityRepositoryImpl = this.this$0;
                            this.label = 1;
                            if (SecurityRepositoryImpl$security$1.invokeSuspend$updateState(producerScope, securityRepositoryImpl, this) == coroutineSingletons) {
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

                @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
                public final void onStateChanged() {
                    ProducerScope producerScope3 = producerScope2;
                    CoroutineTracingKt.launchTraced$default(producerScope3, null, null, new AnonymousClass1(producerScope3, securityRepositoryImpl, null), 7);
                }
            };
            ((SecurityControllerImpl) this.this$0.securityController).addCallback(securityControllerCallback);
            SecurityRepositoryImpl securityRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope2;
            this.L$1 = securityControllerCallback;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope2, securityRepositoryImpl2, this) != coroutineSingletons) {
                producerScope = producerScope2;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        securityControllerCallback = (SecurityController.SecurityControllerCallback) this.L$1;
        producerScope = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        final SecurityRepositoryImpl securityRepositoryImpl3 = this.this$0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ((SecurityControllerImpl) securityRepositoryImpl3.securityController).removeCallback(securityControllerCallback);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
