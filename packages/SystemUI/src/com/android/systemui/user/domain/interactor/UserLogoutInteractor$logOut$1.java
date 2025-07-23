package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserLogoutInteractor$logOut$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserLogoutInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserLogoutInteractor$logOut$1(UserLogoutInteractor userLogoutInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userLogoutInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserLogoutInteractor$logOut$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserLogoutInteractor$logOut$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003e, code lost:
    
        if (((com.android.systemui.user.data.repository.UserRepositoryImpl) r5).logOutSecondaryUser(r4) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0065, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0063, code lost:
    
        if (((com.android.systemui.user.data.repository.UserRepositoryImpl) r5).logOutToSystemUser(r4) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L66
        L19:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.user.domain.interactor.UserLogoutInteractor r5 = r4.this$0
            com.android.systemui.user.data.repository.UserRepository r5 = r5.userRepository
            com.android.systemui.user.data.repository.UserRepositoryImpl r5 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r5
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.isSecondaryUserLogoutEnabled
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L41
            com.android.systemui.user.domain.interactor.UserLogoutInteractor r5 = r4.this$0
            com.android.systemui.user.data.repository.UserRepository r5 = r5.userRepository
            r4.label = r3
            com.android.systemui.user.data.repository.UserRepositoryImpl r5 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r5
            java.lang.Object r4 = r5.logOutSecondaryUser(r4)
            if (r4 != r0) goto L66
            goto L65
        L41:
            com.android.systemui.user.domain.interactor.UserLogoutInteractor r5 = r4.this$0
            com.android.systemui.user.data.repository.UserRepository r5 = r5.userRepository
            com.android.systemui.user.data.repository.UserRepositoryImpl r5 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r5
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.isLogoutToSystemUserEnabled
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L66
            com.android.systemui.user.domain.interactor.UserLogoutInteractor r5 = r4.this$0
            com.android.systemui.user.data.repository.UserRepository r5 = r5.userRepository
            r4.label = r2
            com.android.systemui.user.data.repository.UserRepositoryImpl r5 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r5
            java.lang.Object r4 = r5.logOutToSystemUser(r4)
            if (r4 != r0) goto L66
        L65:
            return r0
        L66:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.UserLogoutInteractor$logOut$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
