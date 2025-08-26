package com.android.systemui.user.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes3.dex */
final class UserSwitcherRepositoryImpl$currentUserName$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$currentUserName$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r5v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, ContinuationImpl continuationImpl) throws Throwable {
        UserSwitcherRepositoryImpl$currentUserName$1$updateState$1 userSwitcherRepositoryImpl$currentUserName$1$updateState$1;
        ChannelExt channelExt;
        ProducerScope producerScope2;
        if (continuationImpl instanceof UserSwitcherRepositoryImpl$currentUserName$1$updateState$1) {
            userSwitcherRepositoryImpl$currentUserName$1$updateState$1 = (UserSwitcherRepositoryImpl$currentUserName$1$updateState$1) continuationImpl;
            int i = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label = i - Integer.MIN_VALUE;
            } else {
                userSwitcherRepositoryImpl$currentUserName$1$updateState$1 = new UserSwitcherRepositoryImpl$currentUserName$1$updateState$1(continuationImpl);
            }
        }
        Object obj = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelExt channelExt2 = ChannelExt.INSTANCE;
            userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$0 = channelExt2;
            userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$1 = producerScope;
            userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label = 1;
            int i3 = UserSwitcherRepositoryImpl.$r8$clinit;
            userSwitcherRepositoryImpl.getClass();
            Object objWithContext = BuildersKt.withContext(userSwitcherRepositoryImpl.bgDispatcher, new UserSwitcherRepositoryImpl$getCurrentUser$2(userSwitcherRepositoryImpl, null), userSwitcherRepositoryImpl$currentUserName$1$updateState$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objWithContext;
            channelExt = channelExt2;
            producerScope2 = producerScope;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r5 = (SendChannel) userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$1;
            channelExt = (ChannelExt) userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope2 = r5;
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, obj, "UserSwitcherRepositoryImpl");
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSwitcherRepositoryImpl$currentUserName$1 userSwitcherRepositoryImpl$currentUserName$1 = new UserSwitcherRepositoryImpl$currentUserName$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$currentUserName$1.L$0 = obj;
        return userSwitcherRepositoryImpl$currentUserName$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$currentUserName$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005c, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r6) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        UserSwitcherController.UserSwitchCallback userSwitchCallback;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope2 = (ProducerScope) this.L$0;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            userSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1

                /* renamed from: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                    int label;
                    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.this$0 = userSwitcherRepositoryImpl;
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
                            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                            this.label = 1;
                            if (UserSwitcherRepositoryImpl$currentUserName$1.invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, this) == coroutineSingletons) {
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

                @Override // com.android.systemui.statusbar.policy.UserSwitcherController.UserSwitchCallback
                public final void onUserSwitched() {
                    ProducerScope producerScope3 = producerScope2;
                    CoroutineTracingKt.launchTraced$default(producerScope3, null, null, new AnonymousClass1(producerScope3, userSwitcherRepositoryImpl, null), 7);
                }
            };
            this.this$0.userSwitcherController.addUserSwitchCallback(userSwitchCallback);
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope2;
            this.L$1 = userSwitchCallback;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope2, userSwitcherRepositoryImpl2, this) != coroutineSingletons) {
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
        userSwitchCallback = (UserSwitcherController.UserSwitchCallback) this.L$1;
        producerScope = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0 userSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0 = new UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0(this.this$0, userSwitchCallback, 1);
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
