package com.android.systemui.security.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1", m277f = "SecurityRepository.kt", m278l = {51, 52}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class SecurityRepositoryImpl$security$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ SecurityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityRepositoryImpl$security$1(SecurityRepositoryImpl securityRepositoryImpl, Continuation<? super SecurityRepositoryImpl$security$1> continuation) {
        super(2, continuation);
        this.this$0 = securityRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r6v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation continuation) {
        SecurityRepositoryImpl$security$1$updateState$1 securityRepositoryImpl$security$1$updateState$1;
        int i;
        ChannelExt channelExt;
        ProducerScope producerScope2;
        if (continuation instanceof SecurityRepositoryImpl$security$1$updateState$1) {
            securityRepositoryImpl$security$1$updateState$1 = (SecurityRepositoryImpl$security$1$updateState$1) continuation;
            int i2 = securityRepositoryImpl$security$1$updateState$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                securityRepositoryImpl$security$1$updateState$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = securityRepositoryImpl$security$1$updateState$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = securityRepositoryImpl$security$1$updateState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelExt channelExt2 = ChannelExt.INSTANCE;
                    SecurityModel.Companion companion = SecurityModel.Companion;
                    SecurityController securityController = securityRepositoryImpl.securityController;
                    securityRepositoryImpl$security$1$updateState$1.L$0 = channelExt2;
                    securityRepositoryImpl$security$1$updateState$1.L$1 = producerScope;
                    securityRepositoryImpl$security$1$updateState$1.label = 1;
                    companion.getClass();
                    Object create = SecurityModel.Companion.create(securityController, securityRepositoryImpl.bgDispatcher, securityRepositoryImpl$security$1$updateState$1);
                    if (create == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = create;
                    channelExt = channelExt2;
                    producerScope2 = producerScope;
                } else {
                    if (i != 1) {
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
        }
        securityRepositoryImpl$security$1$updateState$1 = new SecurityRepositoryImpl$security$1$updateState$1(continuation);
        Object obj2 = securityRepositoryImpl$security$1$updateState$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = securityRepositoryImpl$security$1$updateState$1.label;
        if (i != 0) {
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, obj2, "SecurityRepositoryImpl");
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
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

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1$1", m277f = "SecurityRepository.kt", m278l = {48}, m279m = "invokeSuspend")
                /* renamed from: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1$1 */
                final class C23631 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                    int label;
                    final /* synthetic */ SecurityRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C23631(ProducerScope producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation<? super C23631> continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.this$0 = securityRepositoryImpl;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C23631(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C23631) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    SecurityRepositoryImpl securityRepositoryImpl2 = securityRepositoryImpl;
                    ProducerScope producerScope3 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope3, null, null, new C23631(producerScope3, securityRepositoryImpl2, null), 3);
                }
            };
            ((SecurityControllerImpl) this.this$0.securityController).addCallback(securityControllerCallback);
            SecurityRepositoryImpl securityRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope2;
            this.L$1 = securityControllerCallback;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope2, securityRepositoryImpl2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            producerScope = producerScope2;
        } else {
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
        }
        final SecurityRepositoryImpl securityRepositoryImpl3 = this.this$0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ((SecurityControllerImpl) SecurityRepositoryImpl.this.securityController).removeCallback(securityControllerCallback);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
