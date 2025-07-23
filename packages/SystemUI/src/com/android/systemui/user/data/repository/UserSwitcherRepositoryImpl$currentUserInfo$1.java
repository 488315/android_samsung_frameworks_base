package com.android.systemui.user.data.repository;

import android.graphics.drawable.Drawable;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserSwitcherRepositoryImpl$currentUserInfo$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$currentUserInfo$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSwitcherRepositoryImpl$currentUserInfo$1 userSwitcherRepositoryImpl$currentUserInfo$1 = new UserSwitcherRepositoryImpl$currentUserInfo$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$currentUserInfo$1.L$0 = obj;
        return userSwitcherRepositoryImpl$currentUserInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$currentUserInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                    final /* synthetic */ Drawable $picture;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    int label;
                    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ProducerScope producerScope, Drawable drawable, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.$picture = drawable;
                        this.this$0 = userSwitcherRepositoryImpl;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.$$this$conflatedCallbackFlow, this.$picture, this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        SendChannel sendChannel;
                        ChannelExt channelExt;
                        Drawable drawable;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ChannelExt channelExt2 = ChannelExt.INSTANCE;
                            sendChannel = this.$$this$conflatedCallbackFlow;
                            Drawable drawable2 = this.$picture;
                            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                            this.L$0 = channelExt2;
                            this.L$1 = sendChannel;
                            this.L$2 = drawable2;
                            this.label = 1;
                            int i2 = UserSwitcherRepositoryImpl.$r8$clinit;
                            userSwitcherRepositoryImpl.getClass();
                            Object withContext = BuildersKt.withContext(userSwitcherRepositoryImpl.bgDispatcher, new UserSwitcherRepositoryImpl$isGuestUser$2(userSwitcherRepositoryImpl, null), this);
                            if (withContext == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            obj = withContext;
                            channelExt = channelExt2;
                            drawable = drawable2;
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            drawable = (Drawable) this.L$2;
                            sendChannel = (SendChannel) this.L$1;
                            channelExt = (ChannelExt) this.L$0;
                            ResultKt.throwOnFailure(obj);
                        }
                        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, new Pair(drawable, obj), "UserSwitcherRepositoryImpl");
                        return Unit.INSTANCE;
                    }
                }

                @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
                public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                    ProducerScope producerScope2 = ProducerScope.this;
                    CoroutineTracingKt.launchTraced$default(producerScope2, null, null, new AnonymousClass1(producerScope2, drawable, userSwitcherRepositoryImpl, null), 7);
                }
            };
            ((UserInfoControllerImpl) this.this$0.userInfoController).addCallback(onUserInfoChangedListener);
            UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0 userSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0 = new UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0(this.this$0, onUserInfoChangedListener, 0);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, userSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
