package com.android.systemui.user.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.data.repository.UserRepositoryImpl$_userSwitcherSettings$3", m277f = "UserRepository.kt", m278l = {146}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserRepositoryImpl$_userSwitcherSettings$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$_userSwitcherSettings$3(UserRepositoryImpl userRepositoryImpl, Continuation<? super UserRepositoryImpl$_userSwitcherSettings$3> continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserRepositoryImpl$_userSwitcherSettings$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$_userSwitcherSettings$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserRepositoryImpl userRepositoryImpl = this.this$0;
            this.label = 1;
            int i2 = UserRepositoryImpl.$r8$clinit;
            userRepositoryImpl.getClass();
            obj = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$getSettings$2(userRepositoryImpl, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
