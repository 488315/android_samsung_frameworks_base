package com.android.systemui.user.data.repository;

import android.content.Context;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.model.SelectionStatus;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class UserRepositoryImpl$selectedUser$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<SelectionStatus> $currentSelectionStatus;
    final /* synthetic */ UserRepositoryImpl $this_run;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$selectedUser$1$1(UserRepositoryImpl userRepositoryImpl, Ref$ObjectRef<SelectionStatus> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.$this_run = userRepositoryImpl;
        this.$currentSelectionStatus = ref$ObjectRef;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void invokeSuspend$send(Ref$ObjectRef ref$ObjectRef, ProducerScope producerScope, UserRepositoryImpl userRepositoryImpl, SelectionStatus selectionStatus) {
        ref$ObjectRef.element = selectionStatus;
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, new SelectedUserModel(((UserTrackerImpl) userRepositoryImpl.tracker).getUserInfo(), selectionStatus), "UserRepository");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$selectedUser$1$1 userRepositoryImpl$selectedUser$1$1 = new UserRepositoryImpl$selectedUser$1$1(this.$this_run, this.$currentSelectionStatus, continuation);
        userRepositoryImpl$selectedUser$1$1.L$0 = obj;
        return userRepositoryImpl$selectedUser$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$selectedUser$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.user.data.repository.UserRepositoryImpl$selectedUser$1$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef<SelectionStatus> ref$ObjectRef = this.$currentSelectionStatus;
            final UserRepositoryImpl userRepositoryImpl = this.$this_run;
            final ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$selectedUser$1$1$callback$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onProfilesChanged(List list) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    UserRepositoryImpl$selectedUser$1$1.invokeSuspend$send(ref$ObjectRef2, producerScope, userRepositoryImpl, (SelectionStatus) ref$ObjectRef2.element);
                }

                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onUserChanged(int i2, Context context) {
                    SelectionStatus selectionStatus = SelectionStatus.SELECTION_COMPLETE;
                    UserRepositoryImpl$selectedUser$1$1.invokeSuspend$send(ref$ObjectRef, producerScope, userRepositoryImpl, selectionStatus);
                }

                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onUserChanging(int i2) {
                    SelectionStatus selectionStatus = SelectionStatus.SELECTION_IN_PROGRESS;
                    UserRepositoryImpl$selectedUser$1$1.invokeSuspend$send(ref$ObjectRef, producerScope, userRepositoryImpl, selectionStatus);
                }
            };
            UserRepositoryImpl userRepositoryImpl2 = this.$this_run;
            ((UserTrackerImpl) userRepositoryImpl2.tracker).addCallback(r1, ExecutorsKt.asExecutor(userRepositoryImpl2.mainDispatcher));
            Ref$ObjectRef<SelectionStatus> ref$ObjectRef2 = this.$currentSelectionStatus;
            invokeSuspend$send(ref$ObjectRef2, producerScope, this.$this_run, ref$ObjectRef2.element);
            final UserRepositoryImpl userRepositoryImpl3 = this.$this_run;
            Function0 function0 = new Function0() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$selectedUser$1$1.1
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
