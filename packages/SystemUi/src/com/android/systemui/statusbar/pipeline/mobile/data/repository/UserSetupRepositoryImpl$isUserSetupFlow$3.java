package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$isUserSetupFlow$3", m277f = "UserSetupRepository.kt", m278l = {70}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserSetupRepositoryImpl$isUserSetupFlow$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSetupRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSetupRepositoryImpl$isUserSetupFlow$3(UserSetupRepositoryImpl userSetupRepositoryImpl, Continuation<? super UserSetupRepositoryImpl$isUserSetupFlow$3> continuation) {
        super(2, continuation);
        this.this$0 = userSetupRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSetupRepositoryImpl$isUserSetupFlow$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSetupRepositoryImpl$isUserSetupFlow$3) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserSetupRepositoryImpl userSetupRepositoryImpl = this.this$0;
            this.label = 1;
            userSetupRepositoryImpl.getClass();
            obj = BuildersKt.withContext(userSetupRepositoryImpl.bgDispatcher, new UserSetupRepositoryImpl$fetchUserSetupState$2(userSetupRepositoryImpl, null), this);
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
