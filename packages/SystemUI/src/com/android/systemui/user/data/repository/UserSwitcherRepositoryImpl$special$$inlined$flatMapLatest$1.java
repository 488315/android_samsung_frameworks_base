package com.android.systemui.user.data.repository;

import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl) {
        super(3, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 = new UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userSwitcherRepositoryImpl.currentUserName, userSwitcherRepositoryImpl.currentUserInfo, new UserSwitcherRepositoryImpl$userSwitcherStatus$1$1(null));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(UserSwitcherStatusModel.Disabled.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
