package com.android.systemui.user.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.data.repository.UserRepositoryImpl$observeSelectedUser$1", m277f = "UserRepository.kt", m278l = {269}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserRepositoryImpl$observeSelectedUser$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$observeSelectedUser$1(UserRepositoryImpl userRepositoryImpl, Continuation<? super UserRepositoryImpl$observeSelectedUser$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    public static final void invokeSuspend$send(ProducerScope producerScope, UserRepositoryImpl userRepositoryImpl) {
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, ((UserTrackerImpl) userRepositoryImpl.tracker).getUserInfo(), "UserRepository");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$observeSelectedUser$1 userRepositoryImpl$observeSelectedUser$1 = new UserRepositoryImpl$observeSelectedUser$1(this.this$0, continuation);
        userRepositoryImpl$observeSelectedUser$1.L$0 = obj;
        return userRepositoryImpl$observeSelectedUser$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$observeSelectedUser$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.user.data.repository.UserRepositoryImpl$observeSelectedUser$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final UserRepositoryImpl userRepositoryImpl = this.this$0;
            final ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$observeSelectedUser$1$callback$1
                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onProfilesChanged(List list) {
                    UserRepositoryImpl$observeSelectedUser$1.invokeSuspend$send(ProducerScope.this, userRepositoryImpl);
                }

                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onUserChanging(int i2) {
                    UserRepositoryImpl$observeSelectedUser$1.invokeSuspend$send(ProducerScope.this, userRepositoryImpl);
                }
            };
            UserRepositoryImpl userRepositoryImpl2 = this.this$0;
            ((UserTrackerImpl) userRepositoryImpl2.tracker).addCallback(r1, ExecutorsKt.asExecutor(userRepositoryImpl2.mainDispatcher));
            invokeSuspend$send(producerScope, this.this$0);
            final UserRepositoryImpl userRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$observeSelectedUser$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((UserTrackerImpl) UserRepositoryImpl.this.tracker).removeCallback(r1);
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
